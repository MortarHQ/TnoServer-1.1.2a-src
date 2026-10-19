package net.tslat.smartbrainlib.api.core.behaviour.custom.target;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.player.Player;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.object.TriPredicate;
import net.tslat.smartbrainlib.util.BrainUtils;
import org.apache.logging.log4j.util.TriConsumer;

public class SetAdditionalAttackTargets<E extends Mob> extends ExtendedBehaviour<E> {
   private final List<MemoryModuleType<? extends LivingEntity>> targetingMemories = new ObjectArrayList();
   protected TriPredicate<E, MemoryModuleType<? extends LivingEntity>, LivingEntity> canAttackPredicate = (owner, memory, target) -> target.isAlive()
      && target instanceof Player player
      && !player.getAbilities().invulnerable;
   protected TriConsumer<E, MemoryModuleType<? extends LivingEntity>, LivingEntity> targetCallback = (owner, memory, target) -> {};
   protected boolean avoidDuplicateTargets = true;

   public SetAdditionalAttackTargets<E> attackablePredicate(TriPredicate<E, MemoryModuleType<? extends LivingEntity>, LivingEntity> predicate) {
      this.canAttackPredicate = predicate;
      return this;
   }

   public SetAdditionalAttackTargets<E> whenTargeting(TriConsumer<E, MemoryModuleType<? extends LivingEntity>, LivingEntity> callback) {
      this.targetCallback = callback;
      return this;
   }

   public SetAdditionalAttackTargets<E> withMemories(MemoryModuleType<? extends LivingEntity>... targetMemories) {
      this.targetingMemories.addAll(List.of(targetMemories));
      return this;
   }

   public SetAdditionalAttackTargets<E> allowDuplicateTargeting() {
      this.avoidDuplicateTargets = false;
      return this;
   }

   @Override
   protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
      return List.of();
   }

   protected boolean checkExtraStartConditions(ServerLevel level, E entity) {
      Brain<?> brain = entity.getBrain();

      for (MemoryModuleType<?> memory : this.targetingMemories) {
         if (!BrainUtils.hasMemory(brain, memory)) {
            return true;
         }
      }

      return BrainUtils.hasMemory(brain, MemoryModuleType.NEAREST_PLAYERS) || BrainUtils.hasMemory(brain, MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES);
   }

   protected void start(E entity) {
      Brain<?> brain = entity.getBrain();
      Set<LivingEntity> targetPool = new ObjectOpenHashSet();
      BrainUtils.withMemory(brain, MemoryModuleType.NEAREST_PLAYERS, targetPool::addAll);
      BrainUtils.withMemory(brain, MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES, memoryx -> memoryx.findAll(targetx -> true).forEach(targetPool::add));
      if (!targetPool.isEmpty()) {
         for (MemoryModuleType<? extends LivingEntity> memory : this.targetingMemories) {
            LivingEntity target = BrainUtils.getMemory(brain, memory);
            if (target == null) {
               LivingEntity newTarget = null;

               for (Iterator<LivingEntity> iterator = targetPool.iterator(); iterator.hasNext(); newTarget = null) {
                  newTarget = iterator.next();
                  if (this.canAttackPredicate.test(entity, memory, newTarget)) {
                     BrainUtils.setMemory(brain, memory, newTarget);
                     this.targetCallback.accept(entity, memory, newTarget);
                     iterator.remove();
                     break;
                  }
               }

               if (newTarget != null && !this.avoidDuplicateTargets) {
                  targetPool.add(newTarget);
               }
            }
         }
      }
   }
}
