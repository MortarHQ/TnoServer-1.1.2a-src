package com.finderfeed.raids_enhanced.content.entities.raid_blimp.navigation;

import com.finderfeed.raids_enhanced.content.entities.raid_blimp.RaidBlimp;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.control.MoveControl.Operation;
import net.minecraft.world.phys.Vec3;

public class RaidBlimpMoveControl extends FlyingMoveControl {
   private RaidBlimp blimp;

   public RaidBlimpMoveControl(RaidBlimp mob, int turnMax, boolean hoversInPlace) {
      super(mob, turnMax, hoversInPlace);
      this.blimp = mob;
   }

   public void tick() {
      RaidBlimpPathNavigation navigation = (RaidBlimpPathNavigation)this.blimp.getNavigation();
      if (navigation.isDone()) {
         this.operation = Operation.WAIT;
      }

      if (this.operation == Operation.MOVE_TO) {
         float speed = (float)(this.speedModifier * this.mob.getAttributeValue(Attributes.FLYING_SPEED));
         double d0 = this.wantedX - this.mob.getX();
         double d1 = this.wantedY - this.mob.getY();
         double d2 = this.wantedZ - this.mob.getZ();
         double d3 = d0 * d0 + d1 * d1 + d2 * d2;
         if (d3 >= 15.0) {
            float f = (float)(Mth.atan2(d2, d0) * 180.0 / (float) Math.PI) - 90.0F;
            this.mob.setYRot(this.rotlerp(this.mob.getYRot(), f, speed * 10.0F));
            this.mob.yBodyRot = this.mob.getYRot();
            double d4 = Math.sqrt(d0 * d0 + d2 * d2);
            float f2 = (float)(-(Mth.atan2(d1, d4) * 180.0 / (float) Math.PI));
            this.mob.setXRot(this.rotlerp(this.mob.getXRot(), f2, 90.0F));
         }

         Vec3 targetPos = new Vec3(this.wantedX, this.wantedY, this.wantedZ);
         Vec3 between = targetPos.subtract(this.mob.position());
         float distance = (float)between.length();
         if (distance > 6.0F) {
            if (this.mob instanceof RaidBlimp raidBlimp) {
               raidBlimp.isMoving = true;
            }

            speed = Mth.clamp(speed, 0.0F, distance);
            Vec3 deltaMovement = this.mob.getLookAngle().normalize().scale(speed);
            this.mob.setDeltaMovement(deltaMovement);
         } else {
            navigation.setFinishedMovingToPos(true);
            if (this.mob instanceof RaidBlimp raidBlimp) {
               raidBlimp.isMoving = false;
            }

            this.mob.setDeltaMovement(this.mob.getDeltaMovement().multiply(0.95, 0.95, 0.95));
         }
      } else if (this.mob instanceof RaidBlimp raidBlimp) {
         raidBlimp.isMoving = false;
      }
   }
}
