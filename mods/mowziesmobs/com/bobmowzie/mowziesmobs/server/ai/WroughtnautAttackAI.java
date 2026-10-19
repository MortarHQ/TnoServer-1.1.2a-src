package com.bobmowzie.mowziesmobs.server.ai;

import com.bobmowzie.mowziesmobs.server.entity.wroughtnaut.EntityWroughtnaut;
import com.ilexiconn.llibrary.server.animation.AnimationHandler;
import com.ilexiconn.llibrary.server.animation.IAnimatedEntity;
import java.util.EnumSet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;

public class WroughtnautAttackAI extends Goal {
   private final EntityWroughtnaut wroughtnaut;
   private int repath;
   private double targetX;
   private double targetY;
   private double targetZ;
   private int attacksSinceVertical;
   private int timeSinceStomp;

   public WroughtnautAttackAI(EntityWroughtnaut wroughtnaut) {
      this.wroughtnaut = wroughtnaut;
      this.setFlags(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.LOOK));
   }

   public boolean canUse() {
      LivingEntity target = this.wroughtnaut.getTarget();
      return target != null && target.isAlive() && this.wroughtnaut.isActive() && this.wroughtnaut.getAnimation() == IAnimatedEntity.NO_ANIMATION;
   }

   public void start() {
      this.repath = 0;
   }

   public void stop() {
      this.wroughtnaut.getNavigation().stop();
   }

   public void tick() {
      LivingEntity target = this.wroughtnaut.getTarget();
      if (target != null) {
         double dist = this.wroughtnaut.distanceToSqr(this.targetX, this.targetY, this.targetZ);
         this.wroughtnaut.getLookControl().setLookAt(target, 30.0F, 30.0F);
         if (--this.repath <= 0
               && (this.targetX == 0.0 && this.targetY == 0.0 && this.targetZ == 0.0 || target.distanceToSqr(this.targetX, this.targetY, this.targetZ) >= 1.0)
            || this.wroughtnaut.getNavigation().isDone()) {
            this.targetX = target.getX();
            this.targetY = target.getY();
            this.targetZ = target.getZ();
            this.repath = 4 + this.wroughtnaut.getRandom().nextInt(7);
            if (dist > 1024.0) {
               this.repath += 10;
            } else if (dist > 256.0) {
               this.repath += 5;
            }

            if (!this.wroughtnaut.getNavigation().moveTo(target, 0.2)) {
               this.repath += 15;
            }
         }

         dist = this.wroughtnaut.distanceToSqr(this.targetX, this.targetY, this.targetZ);
         if (target.getY() - this.wroughtnaut.getY() >= -1.0 && target.getY() - this.wroughtnaut.getY() <= 3.0) {
            boolean couldStomp = dist < 36.0 && this.timeSinceStomp > 200;
            if (dist < 12.25
               && this.wroughtnaut.getDotProductBodyFacingEntity(target) > 0.0
               && (!couldStomp || this.wroughtnaut.getRandom().nextFloat() < 0.667F)) {
               if (!(this.attacksSinceVertical > 3.0F + 2.0F * (1.0F - this.wroughtnaut.getHealthRatio()))
                  && !(this.wroughtnaut.getRandom().nextFloat() < 0.18F)) {
                  AnimationHandler.INSTANCE.sendAnimationMessage(this.wroughtnaut, EntityWroughtnaut.ATTACK_ANIMATION);
                  this.attacksSinceVertical++;
               } else {
                  AnimationHandler.INSTANCE.sendAnimationMessage(this.wroughtnaut, EntityWroughtnaut.VERTICAL_ATTACK_ANIMATION);
                  this.attacksSinceVertical = 0;
               }
            } else if (couldStomp) {
               AnimationHandler.INSTANCE.sendAnimationMessage(this.wroughtnaut, EntityWroughtnaut.STOMP_ATTACK_ANIMATION);
               this.timeSinceStomp = 0;
               this.attacksSinceVertical++;
            }
         }

         this.timeSinceStomp++;
      }
   }
}
