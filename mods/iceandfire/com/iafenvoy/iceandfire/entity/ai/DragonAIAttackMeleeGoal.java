package com.iafenvoy.iceandfire.entity.ai;

import com.iafenvoy.iceandfire.entity.DragonBaseEntity;
import com.iafenvoy.uranus.object.entity.pathfinding.raycoms.AdvancedPathNavigate;
import java.util.EnumSet;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.player.Player;

public class DragonAIAttackMeleeGoal extends Goal {
   protected final DragonBaseEntity dragon;
   private final boolean longMemory;
   private final double speedTowardsTarget;
   private int attackTick;
   private int delayCounter;
   private double targetX;
   private double targetY;
   private double targetZ;

   public DragonAIAttackMeleeGoal(DragonBaseEntity dragon, double speedIn, boolean useLongMemory) {
      this.dragon = dragon;
      this.longMemory = useLongMemory;
      this.speedTowardsTarget = speedIn;
      this.setFlags(EnumSet.of(Flag.MOVE));
   }

   public boolean canUse() {
      LivingEntity livingEntity = this.dragon.getTarget();
      if (!(this.dragon.getNavigation() instanceof AdvancedPathNavigate)) {
         return false;
      } else if (livingEntity == null) {
         return false;
      } else if (!livingEntity.isAlive()) {
         return false;
      } else if (this.dragon.canMove() && !this.dragon.isHovering() && !this.dragon.isFlying()) {
         ((AdvancedPathNavigate)this.dragon.getNavigation()).moveToLivingEntity(livingEntity, this.speedTowardsTarget);
         return true;
      } else {
         return false;
      }
   }

   public boolean canContinueToUse() {
      if (!(this.dragon.getNavigation() instanceof AdvancedPathNavigate)) {
         return false;
      } else {
         LivingEntity livingEntity = this.dragon.getTarget();
         if (livingEntity != null && !livingEntity.isAlive()) {
            this.stop();
            return false;
         } else {
            return livingEntity != null && livingEntity.isAlive() && !this.dragon.isFlying() && !this.dragon.isHovering();
         }
      }
   }

   public void start() {
      this.delayCounter = 0;
   }

   public void stop() {
      LivingEntity LivingEntity = this.dragon.getTarget();
      if (LivingEntity instanceof Player && (LivingEntity.isSpectator() || ((Player)LivingEntity).isCreative())) {
         this.dragon.setTarget(null);
      }

      this.dragon.getNavigation().stop();
   }

   public void tick() {
      LivingEntity entity = this.dragon.getTarget();
      if (this.delayCounter > 0) {
         this.delayCounter--;
      }

      if (entity != null) {
         if (this.dragon.getAnimation() == DragonBaseEntity.ANIMATION_SHAKEPREY) {
            this.stop();
            return;
         }

         ((AdvancedPathNavigate)this.dragon.getNavigation()).moveToLivingEntity(entity, this.speedTowardsTarget);
         double d0 = this.dragon.distanceToSqr(entity.getX(), entity.getBoundingBox().minY, entity.getZ());
         double d1 = this.getAttackReachSqr(entity);
         this.delayCounter--;
         if ((this.longMemory || this.dragon.getSensing().hasLineOfSight(entity))
            && this.delayCounter <= 0
            && (
               this.targetX == 0.0 && this.targetY == 0.0 && this.targetZ == 0.0
                  || entity.distanceToSqr(this.targetX, this.targetY, this.targetZ) >= 1.0
                  || this.dragon.getRandom().nextFloat() < 0.05F
            )) {
            this.targetX = entity.getX();
            this.targetY = entity.getBoundingBox().minY;
            this.targetZ = entity.getZ();
            this.delayCounter = 4 + this.dragon.getRandom().nextInt(7);
            if (d0 > 1024.0) {
               this.delayCounter += 10;
            } else if (d0 > 256.0) {
               this.delayCounter += 5;
            }

            if (this.dragon.canMove()) {
               this.delayCounter += 15;
            }
         }

         this.attackTick = Math.max(this.attackTick - 1, 0);
         if (d0 <= d1 && this.attackTick == 0) {
            this.attackTick = 20;
            this.dragon.swing(InteractionHand.MAIN_HAND);
            this.dragon.doHurtTarget(entity);
         }
      }
   }

   protected double getAttackReachSqr(LivingEntity attackTarget) {
      return this.dragon.getBbWidth() * 2.0F * this.dragon.getBbWidth() * 2.0F + attackTarget.getBbWidth();
   }
}
