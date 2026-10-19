package com.iafenvoy.iceandfire.entity.ai;

import com.iafenvoy.iceandfire.entity.AmphithereEntity;
import java.util.EnumSet;
import java.util.Objects;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.pathfinder.Path;

public class AmphithereAIAttackMeleeGoal extends Goal {
   protected final AmphithereEntity attacker;
   final double speedTowardsTarget;
   final boolean longMemory;
   private final boolean canPenalize = false;
   protected int attackTick;
   Path path;
   private int delayCounter;
   private double targetX;
   private double targetY;
   private double targetZ;
   private int failedPathFindingPenalty = 0;

   public AmphithereAIAttackMeleeGoal(AmphithereEntity amphithere, double speedIn, boolean useLongMemory) {
      this.attacker = amphithere;
      this.speedTowardsTarget = speedIn;
      this.longMemory = useLongMemory;
      this.setFlags(EnumSet.of(Flag.MOVE, Flag.TARGET));
   }

   public boolean canUse() {
      LivingEntity LivingEntity = this.attacker.getTarget();
      if (!this.attacker.canMove()) {
         return false;
      }

      if (LivingEntity == null) {
         return false;
      }

      if (!LivingEntity.isAlive()) {
         return false;
      }

      Objects.requireNonNull(this);
      this.path = this.attacker.getNavigation().createPath(LivingEntity, 0);
      return this.path != null
         ? true
         : this.getAttackReachSqr(LivingEntity) >= this.attacker.distanceToSqr(LivingEntity.getX(), LivingEntity.getBoundingBox().minY, LivingEntity.getZ());
   }

   public boolean canContinueToUse() {
      LivingEntity living = this.attacker.getTarget();
      if (living == null) {
         return false;
      } else if (!living.isAlive()) {
         return false;
      } else if (!this.longMemory) {
         return !this.attacker.getNavigation().isDone();
      } else {
         return !this.attacker.isWithinRestriction(living.blockPosition())
            ? false
            : !(living instanceof Player) || !living.isSpectator() && !((Player)living).isCreative();
      }
   }

   public void start() {
      if (this.attacker.isFlying()) {
         this.attacker.getMoveControl().setWantedPosition(this.targetX, this.targetY, this.targetZ, 0.1F);
      } else {
         this.attacker.getNavigation().moveTo(this.path, this.speedTowardsTarget);
      }

      this.delayCounter = 0;
   }

   public void stop() {
      LivingEntity LivingEntity = this.attacker.getTarget();
      if (LivingEntity instanceof Player && (LivingEntity.isSpectator() || ((Player)LivingEntity).isCreative())) {
         this.attacker.setTarget(null);
      }

      this.attacker.getNavigation().stop();
   }

   public void tick() {
      LivingEntity LivingEntity = this.attacker.getTarget();
      if (this.attacker.isFlying()) {
         assert LivingEntity != null;
         this.attacker.getMoveControl().setWantedPosition(LivingEntity.getX(), LivingEntity.getY() + LivingEntity.getEyeHeight(), LivingEntity.getZ(), 0.1);
      }

      this.attacker.getLookControl().setLookAt(LivingEntity, 30.0F, 30.0F);
      assert LivingEntity != null;
      double d0 = this.attacker.distanceToSqr(LivingEntity.getX(), LivingEntity.getBoundingBox().minY, LivingEntity.getZ());
      this.delayCounter--;
      if ((this.longMemory || this.attacker.getSensing().hasLineOfSight(LivingEntity))
         && this.delayCounter <= 0
         && (
            this.targetX == 0.0 && this.targetY == 0.0 && this.targetZ == 0.0
               || LivingEntity.distanceToSqr(this.targetX, this.targetY, this.targetZ) >= 1.0
               || this.attacker.getRandom().nextFloat() < 0.05F
         )) {
         this.targetX = LivingEntity.getX();
         this.targetY = LivingEntity.getBoundingBox().minY;
         this.targetZ = LivingEntity.getZ();
         this.delayCounter = 4 + this.attacker.getRandom().nextInt(7);
         Objects.requireNonNull(this);
         if (d0 > 1024.0) {
            this.delayCounter += 10;
         } else if (d0 > 256.0) {
            this.delayCounter += 5;
         }

         if (!this.attacker.getNavigation().moveTo(LivingEntity, this.speedTowardsTarget)) {
            this.delayCounter += 15;
         }
      }

      this.attackTick = Math.max(this.attackTick - 1, 0);
      this.checkAndPerformAttack(LivingEntity, d0);
   }

   protected void checkAndPerformAttack(LivingEntity enemy, double distToEnemySqr) {
      double d0 = this.getAttackReachSqr(enemy);
      if (distToEnemySqr <= d0) {
         this.attackTick = 20;
         this.attacker.swing(InteractionHand.MAIN_HAND);
         this.attacker.doHurtTarget(enemy);
      }
   }

   protected double getAttackReachSqr(LivingEntity attackTarget) {
      return this.attacker.getBbWidth() * 2.0F * this.attacker.getBbWidth() * 2.0F + attackTarget.getBbWidth();
   }
}
