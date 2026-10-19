package com.iafenvoy.iceandfire.entity.ai;

import com.iafenvoy.iceandfire.entity.GhostEntity;
import com.iafenvoy.iceandfire.entity.util.dragon.DragonUtils;
import com.iafenvoy.uranus.animation.IAnimatedEntity;
import java.util.EnumSet;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.phys.Vec3;

public class GhostAIChargeGoal extends Goal {
   private final GhostEntity ghost;
   public boolean firstPhase = true;
   public Vec3 moveToPos = null;
   public Vec3 offsetOf = Vec3.ZERO;

   public GhostAIChargeGoal(GhostEntity ghost) {
      this.setFlags(EnumSet.of(Flag.MOVE));
      this.ghost = ghost;
   }

   public boolean canUse() {
      return this.ghost.getTarget() != null && !this.ghost.isCharging();
   }

   public boolean canContinueToUse() {
      return this.ghost.getTarget() != null && this.ghost.getTarget().isAlive();
   }

   public void start() {
      this.ghost.setCharging(true);
   }

   public void stop() {
      this.firstPhase = true;
      this.moveToPos = null;
      this.ghost.setCharging(false);
   }

   public void tick() {
      LivingEntity target = this.ghost.getTarget();
      if (target != null) {
         if (this.ghost.getAnimation() == IAnimatedEntity.NO_ANIMATION && this.ghost.distanceTo(target) < 1.4) {
            this.ghost.setAnimation(GhostEntity.ANIMATION_HIT);
         }

         if (this.firstPhase) {
            if (this.moveToPos == null) {
               BlockPos moveToPos = DragonUtils.getBlockInTargetsViewGhost(this.ghost, target);
               this.moveToPos = Vec3.atCenterOf(moveToPos);
            } else {
               this.ghost.getNavigation().moveTo(this.moveToPos.x + 0.5, this.moveToPos.y + 0.5, this.moveToPos.z + 0.5, 1.0);
               if (this.ghost.distanceToSqr(this.moveToPos.add(0.5, 0.5, 0.5)) < 9.0) {
                  if (this.ghost.getAnimation() == IAnimatedEntity.NO_ANIMATION) {
                     this.ghost.setAnimation(GhostEntity.ANIMATION_SCARE);
                  }

                  this.firstPhase = false;
                  this.moveToPos = null;
                  this.offsetOf = target.position().subtract(this.ghost.position()).normalize();
               }
            }
         } else {
            Vec3 fin = target.position();
            this.moveToPos = new Vec3(fin.x, target.getY() + target.getEyeHeight() / 2.0F, fin.z);
            this.ghost.getNavigation().moveTo(target, 1.2F);
            if (this.ghost.distanceToSqr(this.moveToPos.add(0.5, 0.5, 0.5)) < 3.0) {
               this.stop();
            }
         }
      }
   }
}
