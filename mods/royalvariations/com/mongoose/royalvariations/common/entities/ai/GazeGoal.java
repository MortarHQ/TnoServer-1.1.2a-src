package com.mongoose.royalvariations.common.entities.ai;

import com.mongoose.royalvariations.common.effects.RVEffects;
import com.mongoose.royalvariations.utils.MobUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.phys.AABB;

public class GazeGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {
   public GazeGoal(Mob p_26064_, Class<T> p_26065_) {
      this(p_26064_, p_26065_, false, false);
   }

   public GazeGoal(Mob mob, Class<T> p_26054_, boolean p_26056_, boolean p_26057_) {
      super(
         mob,
         p_26054_,
         10,
         p_26056_,
         p_26057_,
         livingEntity -> !MobUtil.areAllies(mob, livingEntity) && livingEntity.distanceTo(mob) <= 16.0F && livingEntity.hasEffect(RVEffects.PRESSING_GAZE)
      );
   }

   public boolean canContinueToUse() {
      return this.mob.getTarget() != null && this.mob.getTarget().isDeadOrDying() ? false : super.canContinueToUse();
   }

   protected AABB getTargetSearchArea(double p_26069_) {
      return this.mob.getBoundingBox().inflate(20.0);
   }

   protected void findTarget() {
      this.target = this.mob
         .level()
         .getNearestEntity(
            this.mob.level().getEntitiesOfClass(this.targetType, this.getTargetSearchArea(this.getFollowDistance())),
            this.targetConditions,
            this.mob,
            this.mob.getX(),
            this.mob.getEyeY(),
            this.mob.getZ()
         );
   }
}
