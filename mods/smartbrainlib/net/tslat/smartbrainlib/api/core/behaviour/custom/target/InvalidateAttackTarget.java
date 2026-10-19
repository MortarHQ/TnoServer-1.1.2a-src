package net.tslat.smartbrainlib.api.core.behaviour.custom.target;

import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.function.BiPredicate;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.player.Player;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.object.MemoryTest;
import net.tslat.smartbrainlib.util.BrainUtils;

public class InvalidateAttackTarget<E extends LivingEntity> extends ExtendedBehaviour<E> {
   private static final MemoryTest MEMORY_REQUIREMENTS = MemoryTest.builder(2)
      .hasMemory(MemoryModuleType.ATTACK_TARGET)
      .usesMemory(MemoryModuleType.LOOK_TARGET)
      .usesMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
   protected BiPredicate<E, LivingEntity> customPredicate = (entity, target) -> target instanceof Player player && player.getAbilities().invulnerable
      || entity.getAttributes().hasAttribute(Attributes.FOLLOW_RANGE)
         && entity.distanceToSqr(target) >= Mth.square(entity.getAttributeValue(Attributes.FOLLOW_RANGE));
   protected long pathfindingAttentionSpan = 200L;

   public InvalidateAttackTarget<E> invalidateIf(BiPredicate<E, LivingEntity> predicate) {
      this.customPredicate = predicate;
      return this;
   }

   public InvalidateAttackTarget<E> ignoreFailedPathfinding() {
      return this.stopTryingToPathAfter(0L);
   }

   public InvalidateAttackTarget<E> stopTryingToPathAfter(long ticks) {
      this.pathfindingAttentionSpan = ticks;
      return this;
   }

   @Override
   protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
      return MEMORY_REQUIREMENTS;
   }

   @Override
   protected void start(E entity) {
      LivingEntity target = BrainUtils.getTargetOfEntity(entity);
      if (target != null) {
         if (this.isTargetInvalid(entity, target)
            || !this.canAttack(entity, target)
            || this.isTiredOfPathing(entity)
            || this.customPredicate.test(entity, target)) {
            BrainUtils.clearMemory(entity, MemoryModuleType.ATTACK_TARGET);
            if (BrainUtils.getMemory(entity, MemoryModuleType.LOOK_TARGET) instanceof EntityTracker entityTracker && entityTracker.getEntity() == entity) {
               BrainUtils.clearMemory(entity, MemoryModuleType.LOOK_TARGET);
            }
         }
      }
   }

   protected boolean isTargetInvalid(E entity, LivingEntity target) {
      return entity.level() != target.level() ? true : target.isDeadOrDying() || target.isRemoved();
   }

   protected boolean canAttack(E entity, LivingEntity target) {
      return entity.canAttack(target);
   }

   protected boolean isTiredOfPathing(E entity) {
      if (this.pathfindingAttentionSpan <= 0L) {
         return false;
      }

      Long time = BrainUtils.getMemory(entity, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
      return time != null && entity.level().getGameTime() - time > this.pathfindingAttentionSpan;
   }
}
