package com.chronodawn.entities.bosses;

import java.util.EnumSet;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.level.pathfinder.Path;

public class ExtendedMeleeAttackGoal extends Goal {
   private final PathfinderMob mob;
   private final double speedModifier;
   private final boolean followingTargetEvenIfNotSeen;
   private final double attackRange;
   private Path path;
   private double pathedTargetX;
   private double pathedTargetY;
   private double pathedTargetZ;
   private int ticksUntilNextPathRecalculation;
   private int ticksUntilNextAttack;
   private long lastCanUseCheck;

   public ExtendedMeleeAttackGoal(PathfinderMob mob, double speedModifier, boolean followingTargetEvenIfNotSeen, double attackRange) {
      this.mob = mob;
      this.speedModifier = speedModifier;
      this.followingTargetEvenIfNotSeen = followingTargetEvenIfNotSeen;
      this.attackRange = attackRange;
      this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
   }

   public boolean canUse() {
      long gameTime = this.mob.level().getGameTime();
      if (gameTime - this.lastCanUseCheck < 20L) {
         return false;
      }

      this.lastCanUseCheck = gameTime;
      LivingEntity target = this.mob.getTarget();
      if (target == null) {
         return false;
      }

      if (!target.isAlive()) {
         return false;
      }

      this.path = this.mob.getNavigation().createPath(target, 0);
      return this.path != null || this.getAttackReachSqr(target) >= this.mob.distanceToSqr(target.getX(), target.getY(), target.getZ());
   }

   public boolean canContinueToUse() {
      LivingEntity target = this.mob.getTarget();
      if (target == null) {
         return false;
      } else if (!target.isAlive()) {
         return false;
      } else {
         return !this.followingTargetEvenIfNotSeen ? !this.mob.getNavigation().isDone() : this.mob.isWithinRestriction(target.blockPosition());
      }
   }

   public void start() {
      this.mob.getNavigation().moveTo(this.path, this.speedModifier);
      this.mob.setAggressive(true);
      this.ticksUntilNextPathRecalculation = 0;
      this.ticksUntilNextAttack = 0;
   }

   public void stop() {
      LivingEntity target = this.mob.getTarget();
      if (!this.canContinueToUse()) {
         this.mob.setTarget(null);
      }

      this.mob.setAggressive(false);
      this.mob.getNavigation().stop();
   }

   public void tick() {
      LivingEntity target = this.mob.getTarget();
      if (target != null) {
         this.mob.getLookControl().setLookAt(target, 30.0F, 30.0F);
         double distanceSqr = this.mob.distanceToSqr(target.getX(), target.getY(), target.getZ());
         this.ticksUntilNextPathRecalculation = Math.max(this.ticksUntilNextPathRecalculation - 1, 0);
         if ((this.followingTargetEvenIfNotSeen || this.mob.getSensing().hasLineOfSight(target))
            && this.ticksUntilNextPathRecalculation <= 0
            && (
               this.pathedTargetX == 0.0 && this.pathedTargetY == 0.0 && this.pathedTargetZ == 0.0
                  || target.distanceToSqr(this.pathedTargetX, this.pathedTargetY, this.pathedTargetZ) >= 1.0
                  || this.mob.getRandom().nextFloat() < 0.05F
            )) {
            this.pathedTargetX = target.getX();
            this.pathedTargetY = target.getY();
            this.pathedTargetZ = target.getZ();
            this.ticksUntilNextPathRecalculation = 4 + this.mob.getRandom().nextInt(7);
            if (distanceSqr > 1024.0) {
               this.ticksUntilNextPathRecalculation += 10;
            } else if (distanceSqr > 256.0) {
               this.ticksUntilNextPathRecalculation += 5;
            }

            if (!this.mob.getNavigation().moveTo(target, this.speedModifier)) {
               this.ticksUntilNextPathRecalculation += 15;
            }

            this.ticksUntilNextPathRecalculation = this.adjustedTickDelay(this.ticksUntilNextPathRecalculation);
         }

         this.ticksUntilNextAttack = Math.max(this.ticksUntilNextAttack - 1, 0);
         this.checkAndPerformAttack(target, distanceSqr);
      }
   }

   protected void checkAndPerformAttack(LivingEntity target, double distanceSqr) {
      double attackReachSqr = this.getAttackReachSqr(target);
      if (distanceSqr <= attackReachSqr && this.ticksUntilNextAttack <= 0) {
         if (this.mob instanceof TimeGuardianEntity guardian && guardian.isInPostTeleportDelay()) {
            return;
         }

         this.resetAttackCooldown();
         this.mob.swing(InteractionHand.MAIN_HAND);
         this.mob.doHurtTarget(target);
      }
   }

   protected void resetAttackCooldown() {
      this.ticksUntilNextAttack = this.adjustedTickDelay(20);
   }

   protected double getAttackReachSqr(LivingEntity target) {
      return this.attackRange * this.attackRange;
   }
}
