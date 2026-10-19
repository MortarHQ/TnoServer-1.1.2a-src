package com.chronodawn.entities.ai;

import com.chronodawn.entities.bosses.TimeGuardianEntity;
import java.util.EnumSet;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;

public class TimeGuardianRangedAttackGoal extends Goal {
   private final TimeGuardianEntity entity;
   private final double speedModifier;
   private final int attackIntervalMin;
   private final int attackIntervalMax;
   private final float attackRadius;
   private final float minAttackDistance;
   private final int cooldownTicks;
   private int attackTime = -1;
   private int seeTime;
   private boolean strafingClockwise;
   private boolean strafingBackwards;
   private int strafingTime = -1;

   public TimeGuardianRangedAttackGoal(
      TimeGuardianEntity entity, double speedModifier, int attackIntervalTicks, float attackRadius, float minAttackDistance, int cooldownTicks
   ) {
      this.entity = entity;
      this.speedModifier = speedModifier;
      this.attackIntervalMin = attackIntervalTicks;
      this.attackIntervalMax = attackIntervalTicks;
      this.attackRadius = attackRadius * attackRadius;
      this.minAttackDistance = minAttackDistance * minAttackDistance;
      this.cooldownTicks = cooldownTicks;
      this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
   }

   public boolean canUse() {
      LivingEntity target = this.entity.getTarget();
      if (target == null || !target.isAlive()) {
         return false;
      }

      if (this.entity.getRangedAttackCooldown() > 0) {
         return false;
      }

      double distanceSquared = this.entity.distanceToSqr(target);
      return distanceSquared >= this.minAttackDistance && distanceSquared <= this.attackRadius;
   }

   public boolean canContinueToUse() {
      LivingEntity target = this.entity.getTarget();
      if (target == null || !target.isAlive()) {
         return false;
      } else {
         return this.entity.getRangedAttackCooldown() > 0 ? false : this.canUse() || !this.entity.getNavigation().isDone();
      }
   }

   public void start() {
      super.start();
      this.entity.setAggressive(true);
      this.seeTime = 0;
      this.attackTime = -1;
   }

   public void stop() {
      super.stop();
      this.entity.setAggressive(false);
      this.seeTime = 0;
      this.attackTime = -1;
      this.entity.getNavigation().stop();
   }

   public boolean requiresUpdateEveryTick() {
      return true;
   }

   public void tick() {
      LivingEntity target = this.entity.getTarget();
      if (target != null) {
         double distanceSquared = this.entity.distanceToSqr(target.getX(), target.getY(), target.getZ());
         boolean canSee = this.entity.getSensing().hasLineOfSight(target);
         if (canSee) {
            this.seeTime++;
         } else {
            this.seeTime = 0;
         }

         if (distanceSquared > this.attackRadius) {
            this.entity.getNavigation().moveTo(target, this.speedModifier);
         } else {
            this.entity.getNavigation().stop();
         }

         this.entity.getLookControl().setLookAt(target, 30.0F, 30.0F);
         if (--this.attackTime == 0) {
            if (!canSee) {
               return;
            }

            float f = (float)Math.sqrt(distanceSquared) / this.attackRadius;
            float clampedDistance = Mth.clamp(f, 0.1F, 1.0F);
            this.entity.performRangedAttack(target, clampedDistance);
            this.entity.setRangedAttackCooldown(this.cooldownTicks);
            this.attackTime = Mth.floor(f * (this.attackIntervalMax - this.attackIntervalMin) + this.attackIntervalMin);
         } else if (this.attackTime < 0) {
            this.attackTime = Mth.floor(Mth.lerp(Math.sqrt(distanceSquared) / this.attackRadius, this.attackIntervalMin, this.attackIntervalMax));
         }
      }
   }
}
