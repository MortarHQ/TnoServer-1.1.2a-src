package com.mongoose.royalvariations.common.entities.ai;

import com.mongoose.royalvariations.common.effects.RVEffects;
import com.mongoose.royalvariations.utils.MobUtil;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.phys.AABB;

public class ChosenVictimGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {
   public ChosenVictimGoal(Mob p_26064_, Class<T> p_26065_) {
      this(p_26064_, p_26065_, true, false);
   }

   public ChosenVictimGoal(Mob mob, Class<T> p_26054_, boolean p_26056_, boolean p_26057_) {
      super(
         mob,
         p_26054_,
         1,
         p_26056_,
         p_26057_,
         livingEntity -> !MobUtil.areAllies(mob, livingEntity)
            && MobUtil.notMonsterOrAnimal(livingEntity)
            && livingEntity.distanceTo(mob) <= 16.0F
            && livingEntity.hasEffect(RVEffects.CHOSEN_VICTIM)
      );
   }

   public boolean canUse() {
      this.findTarget();
      if (this.target != null && this.target.tickCount % 40 == 0) {
         MobEffectInstance instance = this.target.getEffect(RVEffects.CHOSEN_VICTIM);
         if (instance != null) {
            this.mob.addEffect(new MobEffectInstance(RVEffects.UNDEAD_RUSH, 60, instance.getAmplifier(), false, false));
            return true;
         }
      }

      return false;
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
