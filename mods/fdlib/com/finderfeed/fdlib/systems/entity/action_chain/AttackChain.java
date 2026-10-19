package com.finderfeed.fdlib.systems.entity.action_chain;

import com.mojang.datafixers.util.Pair;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;

public class AttackChain {
   private HashMap<String, AttackExecutor> registeredAttackExecutors = new HashMap<>();
   private List<Pair<Integer, AttackOptions<?>>> attackOptions = new ArrayList<>();
   private List<Pair<Supplier<Boolean>, String>> alwaysTryCastAttacks = new ArrayList<>();
   private Queue<String> chain = new ArrayDeque<>();
   private AttackInstance currentAttack = null;
   private Function<String, AttackAction> attackListener = inst -> AttackAction.PROCEED;
   private RandomSource source;

   public AttackChain(RandomSource source) {
      this.source = source;
   }

   public AttackChain registerInClassAttacks(Class<? extends Entity> entityClass) {
      for (Method method : entityClass.getDeclaredMethods()) {
         Attack attack = method.getAnnotation(Attack.class);
         if (attack != null) {
            method.setAccessible(true);
            this.registerAttack(attack.value(), instance -> {
               try {
                  return (Boolean)method.invoke(instance);
               } catch (IllegalAccessException e) {
                  throw new RuntimeException(e);
               } catch (InvocationTargetException e) {
                  throw new RuntimeException(e);
               }
            });
         }
      }

      return this;
   }

   public AttackChain addAlwaysTryCastAttack(Supplier<Boolean> condition, String attack) {
      this.alwaysTryCastAttacks.add(new Pair(condition, attack));
      return this;
   }

   public AttackChain attackListener(Function<String, AttackAction> listener) {
      this.attackListener = listener;
      return this;
   }

   public AttackChain registerAttack(String name, AttackExecutor executor) {
      this.registeredAttackExecutors.put(name, executor);
      return this;
   }

   public AttackChain addAttack(int priority, AttackOptions options) {
      this.attackOptions.add(new Pair(priority, options));
      this.attackOptions.sort(Comparator.comparingInt(Pair::getFirst));
      return this;
   }

   public AttackChain addAttack(int priority, String attack) {
      return this.addAttack(priority, AttackOptions.builder().addAttack(attack).build());
   }

   public void tick() {
      if (this.chain.isEmpty() && this.currentAttack == null) {
         this.buildQueue();
      } else if (this.currentAttack != null) {
         int previousStage = this.currentAttack.stage;
         if (this.currentAttack.attack.execute(this.currentAttack)) {
            this.currentAttack = null;
            this.pollAndExecuteAttack();
         } else if (this.currentAttack.stage == previousStage) {
            this.currentAttack.tick++;
         }
      } else {
         this.pollAndExecuteAttack();
      }
   }

   private boolean tryCastAlwaysAttack() {
      for (Pair<Supplier<Boolean>, String> attack : this.alwaysTryCastAttacks) {
         Supplier<Boolean> predicate = (Supplier<Boolean>)attack.getFirst();
         if (predicate.get()) {
            String attackName = (String)attack.getSecond();
            this.currentAttack = new AttackInstance(attackName, this.registeredAttackExecutors.get(attackName));
            return true;
         }
      }

      return false;
   }

   private void pollAndExecuteAttack() {
      while (this.currentAttack == null && !this.chain.isEmpty()) {
         String executorName = this.chain.peek();
         AttackAction attackAction = this.attackListener.apply(executorName);
         if (attackAction != AttackAction.WAIT) {
            if (attackAction == AttackAction.SKIP) {
               this.chain.poll();
               continue;
            } else if (attackAction == AttackAction.PROCEED) {
               if (this.tryCastAlwaysAttack()) {
                  return;
               }

               this.chain.poll();
               AttackExecutor executor = this.registeredAttackExecutors.get(executorName);
               if (executor == null) {
                  throw new RuntimeException("Attack not registered: " + executorName);
               }

               this.currentAttack = new AttackInstance(executorName, executor);
               if (this.currentAttack.attack.execute(this.currentAttack)) {
                  this.currentAttack = null;
               } else if (this.currentAttack.stage == 0) {
                  this.currentAttack.tick++;
               }
               continue;
            }
         }
         break;
      }
   }

   public void buildQueue() {
      this.chain.clear();
      List<AttackOptions> samePriority = new ArrayList<>();
      int currentValue = (Integer)this.attackOptions.get(0).getFirst();

      for (Pair<Integer, AttackOptions<?>> pair : this.attackOptions) {
         if ((Integer)pair.getFirst() == currentValue) {
            samePriority.add((AttackOptions)pair.getSecond());
         } else {
            this.addSamePriorityOptionsToQueue(samePriority);
            samePriority.add((AttackOptions)pair.getSecond());
            currentValue = (Integer)pair.getFirst();
         }
      }

      this.addSamePriorityOptionsToQueue(samePriority);
   }

   private void addSamePriorityOptionsToQueue(List<AttackOptions> samePriority) {
      while (!samePriority.isEmpty()) {
         int rndid = this.source.nextInt(samePriority.size());
         AttackOptions options = samePriority.get(rndid);
         this.addOptionsToQueue(options);
         samePriority.remove(rndid);
      }
   }

   private void addOptionsToQueue(AttackOptions options) {
      AttackOptions<?> pre = options.getPreAttackOptions();
      if (pre != null) {
         this.addOptionsToQueue(pre);
      }

      for (AttackOptions<?> next = options; next != null; next = next.getNextAttackOptions()) {
         for (AttackDefinition definition : next.getAttacks(this.source)) {
            if (definition.getExecutorName() != null) {
               this.chain.offer(definition.getExecutorName());
            } else {
               this.addOptionsToQueue(definition.getOptions());
            }
         }
      }
   }

   public void reset() {
      this.currentAttack = null;
      this.chain.clear();
   }

   public AttackInstance getCurrentAttack() {
      return this.currentAttack;
   }

   public void load(CompoundTag tag) {
      for (int index = 0; tag.contains("attack_" + index); index++) {
         this.chain.offer(tag.getString("attack_" + index));
      }

      if (tag.contains("currentAttack")) {
         String name = tag.getString("currentAttack");
         int tick = tag.getInt("currentAttackTick");
         int stage = tag.getInt("currentAttackStage");
         AttackExecutor executor = this.registeredAttackExecutors.get(name);
         AttackInstance instance = new AttackInstance(name, executor);
         instance.tick = tick;
         instance.stage = stage;
         this.currentAttack = instance;
      }
   }

   public void save(CompoundTag tag) {
      int index = 0;

      for (String s : this.chain) {
         tag.putString("attack_" + index++, s);
      }

      if (this.currentAttack != null) {
         tag.putString("currentAttack", this.currentAttack.name);
         tag.putInt("currentAttackTick", this.currentAttack.tick);
         tag.putInt("currentAttackStage", this.currentAttack.stage);
      }
   }
}
