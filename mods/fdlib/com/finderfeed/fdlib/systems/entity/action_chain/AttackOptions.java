package com.finderfeed.fdlib.systems.entity.action_chain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.WeightedRandomList;
import org.jetbrains.annotations.NotNull;

public abstract class AttackOptions<T extends AttackDefinition> implements Iterable<T> {
   protected List<T> definitions;
   protected AttackOptions<?> preAttackOptions = null;
   protected AttackOptions<?> nextAttackOptions = null;

   public AttackOptions(List<T> attackDefinitions, AttackOptions<?> pre, AttackOptions<?> next) {
      this.nextAttackOptions = next;
      this.preAttackOptions = pre;
      this.definitions = attackDefinitions;
   }

   public abstract Collection<T> getAttacks(RandomSource var1);

   public AttackOptions<?> getNextAttackOptions() {
      return this.nextAttackOptions;
   }

   public AttackOptions<?> getPreAttackOptions() {
      return this.preAttackOptions;
   }

   @NotNull
   @Override
   public Iterator<T> iterator() {
      return this.definitions.iterator();
   }

   public static AttackOptions.WeightedRandomAttackOptionsBuilder builder() {
      return new AttackOptions.WeightedRandomAttackOptionsBuilder();
   }

   public static AttackOptions.ChainedAttackOptionsBuilder chainOptionsBuilder() {
      return new AttackOptions.ChainedAttackOptionsBuilder();
   }

   public static class ChainAttackOptions extends AttackOptions<AttackDefinition> {
      public ChainAttackOptions(List<AttackDefinition> attackDefinitions, AttackOptions<?> pre, AttackOptions<?> next) {
         super(attackDefinitions, pre, next);
      }

      @Override
      public Collection<AttackDefinition> getAttacks(RandomSource source) {
         return this.definitions;
      }
   }

   public static class ChainedAttackOptionsBuilder {
      private List<AttackDefinition> attackDefinitions = new ArrayList<>();
      private AttackOptions<?> preAttackOptions = null;
      private AttackOptions<?> nextAttackOptions = null;

      public AttackOptions.ChainedAttackOptionsBuilder addAttack(String executorName) {
         this.attackDefinitions.add(new AttackDefinition(executorName));
         return this;
      }

      public AttackOptions.ChainedAttackOptionsBuilder addAttack(AttackOptions<?> options) {
         this.attackDefinitions.add(new AttackDefinition(options));
         return this;
      }

      public AttackOptions.ChainedAttackOptionsBuilder setNextAttack(AttackOptions<?> options) {
         this.nextAttackOptions = options;
         return this;
      }

      public AttackOptions.ChainedAttackOptionsBuilder setNextAttack(String executorName) {
         return this.setNextAttack(AttackOptions.chainOptionsBuilder().addAttack(executorName).build());
      }

      public AttackOptions.ChainedAttackOptionsBuilder setPreAttack(AttackOptions<?> options) {
         this.preAttackOptions = options;
         return this;
      }

      public AttackOptions.ChainedAttackOptionsBuilder setPreAttack(String executorName) {
         return this.setPreAttack(AttackOptions.chainOptionsBuilder().addAttack(executorName).build());
      }

      public AttackOptions.ChainAttackOptions build() {
         return new AttackOptions.ChainAttackOptions(this.attackDefinitions, this.preAttackOptions, this.nextAttackOptions);
      }
   }

   public static class WeightedRandomAttackOptions extends AttackOptions<WeightedAttackDefinition> {
      private WeightedRandomList<WeightedAttackDefinition> weightedRandomList;

      public WeightedRandomAttackOptions(List<WeightedAttackDefinition> attackDefinitions, AttackOptions pre, AttackOptions next) {
         super(attackDefinitions, pre, next);
         this.weightedRandomList = WeightedRandomList.create(attackDefinitions);
      }

      @Override
      public Collection<WeightedAttackDefinition> getAttacks(RandomSource source) {
         return List.of((WeightedAttackDefinition)this.weightedRandomList.getRandom(source).get());
      }
   }

   public static class WeightedRandomAttackOptionsBuilder {
      private List<WeightedAttackDefinition> attackDefinitions = new ArrayList<>();
      private AttackOptions<?> preAttackOptions = null;
      private AttackOptions<?> nextAttackOptions = null;

      public AttackOptions.WeightedRandomAttackOptionsBuilder addAttack(int weight, String executorName) {
         this.attackDefinitions.add(new WeightedAttackDefinition(executorName, weight));
         return this;
      }

      public AttackOptions.WeightedRandomAttackOptionsBuilder addAttack(String executorName) {
         return this.addAttack(1, executorName);
      }

      public AttackOptions.WeightedRandomAttackOptionsBuilder addAttack(int weight, AttackOptions<?> options) {
         this.attackDefinitions.add(new WeightedAttackDefinition(options, weight));
         return this;
      }

      public AttackOptions.WeightedRandomAttackOptionsBuilder addAttack(AttackOptions<?> options) {
         return this.addAttack(1, options);
      }

      public AttackOptions.WeightedRandomAttackOptionsBuilder setNextAttack(AttackOptions<?> options) {
         this.nextAttackOptions = options;
         return this;
      }

      public AttackOptions.WeightedRandomAttackOptionsBuilder setNextAttack(String executorName) {
         return this.setNextAttack(AttackOptions.builder().addAttack(executorName).build());
      }

      public AttackOptions.WeightedRandomAttackOptionsBuilder setPreAttack(AttackOptions<?> options) {
         this.preAttackOptions = options;
         return this;
      }

      public AttackOptions.WeightedRandomAttackOptionsBuilder setPreAttack(String executorName) {
         return this.setPreAttack(AttackOptions.builder().addAttack(executorName).build());
      }

      public AttackOptions<WeightedAttackDefinition> build() {
         return new AttackOptions.WeightedRandomAttackOptions(this.attackDefinitions, this.preAttackOptions, this.nextAttackOptions);
      }
   }
}
