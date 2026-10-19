package com.iafenvoy.iceandfire.entity.ai;

import com.iafenvoy.iceandfire.entity.DragonBaseEntity;
import java.util.EnumSet;
import java.util.function.Predicate;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

public class DragonAITargetNonTamedGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {
   private final DragonBaseEntity dragon;

   public DragonAITargetNonTamedGoal(DragonBaseEntity entityIn, Class<T> classTarget, boolean checkSight, Predicate<LivingEntity> targetSelector) {
      super(entityIn, classTarget, 5, checkSight, false, targetSelector);
      this.setFlags(EnumSet.of(Flag.TARGET));
      this.dragon = entityIn;
   }

   public boolean canUse() {
      if (this.dragon.isTame()) {
         return false;
      } else if (this.dragon.lookingForRoostAIFlag) {
         return false;
      } else {
         boolean canUse = super.canUse();
         boolean isSleeping = this.dragon.isSleeping();
         if (canUse) {
            return isSleeping && this.target instanceof Player ? this.dragon.distanceToSqr(this.target) <= 16.0 : !isSleeping;
         } else {
            return false;
         }
      }
   }

   protected AABB getTargetSearchArea(double targetDistance) {
      return this.dragon.getBoundingBox().inflate(targetDistance, targetDistance, targetDistance);
   }

   protected double getFollowDistance() {
      AttributeInstance iattributeinstance = this.mob.getAttribute(Attributes.FOLLOW_RANGE);
      return iattributeinstance == null ? 128.0 : iattributeinstance.getValue();
   }
}
