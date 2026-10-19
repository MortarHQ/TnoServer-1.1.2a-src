package net.tslat.smartbrainlib.api.core.behaviour.custom.target;

import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.object.MemoryTest;
import net.tslat.smartbrainlib.util.BrainUtils;

public class SetAttackTarget<E extends LivingEntity> extends ExtendedBehaviour<E> {
   private static final MemoryTest MEMORY_REQUIREMENTS = MemoryTest.builder(2)
      .hasMemory(MemoryModuleType.NEAREST_ATTACKABLE)
      .noMemory(MemoryModuleType.ATTACK_TARGET);
   private static final MemoryTest CUSTOM_TARGETING_REQUIREMENTS = MemoryTest.builder(1).noMemory(MemoryModuleType.ATTACK_TARGET);
   protected final boolean usingNearestAttackable;
   protected Predicate<E> canAttackPredicate = entity -> true;
   protected Function<E, ? extends LivingEntity> targetFinder = entity -> BrainUtils.getMemory(entity, MemoryModuleType.NEAREST_ATTACKABLE);

   public SetAttackTarget() {
      this(true);
   }

   public SetAttackTarget(boolean usingNearestAttackable) {
      this.usingNearestAttackable = usingNearestAttackable;
   }

   public SetAttackTarget<E> attackPredicate(Predicate<E> predicate) {
      this.canAttackPredicate = predicate;
      return this;
   }

   public SetAttackTarget<E> targetFinder(Function<E, ? extends LivingEntity> targetFindingFunction) {
      this.targetFinder = targetFindingFunction;
      return this;
   }

   @Override
   protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
      return this.usingNearestAttackable ? MEMORY_REQUIREMENTS : CUSTOM_TARGETING_REQUIREMENTS;
   }

   @Override
   protected boolean checkExtraStartConditions(ServerLevel level, E entity) {
      return this.canAttackPredicate.test(entity);
   }

   @Override
   protected void start(E entity) {
      LivingEntity target = this.targetFinder.apply(entity);
      if (target == null) {
         BrainUtils.clearMemory(entity, MemoryModuleType.ATTACK_TARGET);
      } else {
         BrainUtils.setMemory(entity, MemoryModuleType.ATTACK_TARGET, target);
         BrainUtils.clearMemory(entity, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
      }
   }
}
