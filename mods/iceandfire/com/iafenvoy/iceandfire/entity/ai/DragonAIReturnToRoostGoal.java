package com.iafenvoy.iceandfire.entity.ai;

import com.iafenvoy.iceandfire.entity.DragonBaseEntity;
import com.iafenvoy.iceandfire.entity.util.dragon.DragonUtils;
import java.util.EnumSet;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.phys.Vec3;

public class DragonAIReturnToRoostGoal extends Goal {
   private final DragonBaseEntity dragon;

   public DragonAIReturnToRoostGoal(DragonBaseEntity entityIn, double movementSpeedIn) {
      this.dragon = entityIn;
      this.setFlags(EnumSet.of(Flag.MOVE));
   }

   public boolean canUse() {
      return this.dragon.canMove()
         && this.dragon.lookingForRoostAIFlag
         && (this.dragon.getTarget() == null || !this.dragon.getTarget().isAlive())
         && this.dragon.getRestrictCenter() != null
         && DragonUtils.isInHomeDimension(this.dragon)
         && this.dragon.getDistanceSquared(Vec3.atCenterOf(this.dragon.getRestrictCenter())) > this.dragon.getBbWidth() * this.dragon.getBbWidth();
   }

   public void tick() {
      if (this.dragon.getRestrictCenter() != null) {
         double dist = Math.sqrt(this.dragon.getDistanceSquared(Vec3.atCenterOf(this.dragon.getRestrictCenter())));
         double xDist = Math.abs(this.dragon.getX() - this.dragon.getRestrictCenter().getX() - 0.5);
         double zDist = Math.abs(this.dragon.getZ() - this.dragon.getRestrictCenter().getZ() - 0.5);
         double xzDist = Math.sqrt(xDist * xDist + zDist * zDist);
         if (dist < this.dragon.getBbWidth()) {
            this.dragon.setFlying(false);
            this.dragon.setHovering(false);
            this.dragon
               .getNavigation()
               .moveTo(this.dragon.getRestrictCenter().getX(), this.dragon.getRestrictCenter().getY(), this.dragon.getRestrictCenter().getZ(), 1.0);
         } else {
            double yAddition = 15 + this.dragon.getRandom().nextInt(3);
            if (xzDist < 40.0) {
               yAddition = 0.0;
               if (this.dragon.onGround()) {
                  this.dragon.setFlying(false);
                  this.dragon.setHovering(false);
                  this.dragon.flightManager.setFlightTarget(Vec3.upFromBottomCenterOf(this.dragon.getRestrictCenter(), yAddition));
                  this.dragon
                     .getNavigation()
                     .moveTo(this.dragon.getRestrictCenter().getX(), this.dragon.getRestrictCenter().getY(), this.dragon.getRestrictCenter().getZ(), 1.0);
                  return;
               }
            }

            if (!this.dragon.isFlying() && !this.dragon.isHovering() && xzDist > 40.0) {
               this.dragon.setHovering(true);
            }

            if (this.dragon.isFlying()) {
               this.dragon.flightManager.setFlightTarget(Vec3.upFromBottomCenterOf(this.dragon.getRestrictCenter(), yAddition));
               this.dragon
                  .getNavigation()
                  .moveTo(
                     this.dragon.getRestrictCenter().getX(), yAddition + this.dragon.getRestrictCenter().getY(), this.dragon.getRestrictCenter().getZ(), 1.0
                  );
            }

            this.dragon.flyTicks = 0;
         }
      }
   }

   public boolean canContinueToUse() {
      return this.canUse();
   }
}
