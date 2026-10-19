package com.bobmowzie.mowziesmobs.server.entity;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.BodyRotationControl;

public class SmartBodyHelper extends BodyRotationControl {
   private static final float MAX_ROTATE = 75.0F;
   private static final int HISTORY_SIZE = 10;
   private final Mob entity;
   private int rotateTime;
   private float targetYawHead;
   private final double[] histPosX = new double[10];
   private final double[] histPosZ = new double[10];

   public SmartBodyHelper(Mob entity) {
      super(entity);
      this.entity = entity;
   }

   public void clientTick() {
      for (int i = this.histPosX.length - 1; i > 0; i--) {
         this.histPosX[i] = this.histPosX[i - 1];
         this.histPosZ[i] = this.histPosZ[i - 1];
      }

      this.histPosX[0] = this.entity.getX();
      this.histPosZ[0] = this.entity.getZ();
      double dx = this.delta(this.histPosX);
      double dz = this.delta(this.histPosZ);
      double distSq = dx * dx + dz * dz;
      if (distSq > 2.5E-7) {
         boolean isStrafing = false;
         if (this.entity instanceof MowzieEntity) {
            isStrafing = ((MowzieEntity)this.entity).isStrafing();
         }

         if (!isStrafing) {
            double moveAngle = (float)Mth.atan2(dz, dx) * (180.0F / (float)Math.PI) - 90.0F;
            this.entity.yBodyRot = (float)(this.entity.yBodyRot + Mth.wrapDegrees(moveAngle - this.entity.yBodyRot) * 0.6F);
            this.targetYawHead = this.entity.yHeadRot;
            this.rotateTime = 0;
         } else {
            super.clientTick();
         }
      } else if (this.entity.getPassengers().isEmpty() || !(this.entity.getPassengers().get(0) instanceof Mob)) {
         float limit = 75.0F;
         if (Math.abs(this.entity.yHeadRot - this.targetYawHead) > 15.0F) {
            this.rotateTime = 0;
            this.targetYawHead = this.entity.yHeadRot;
         } else {
            this.rotateTime++;
            int speed = 10;
            if (this.rotateTime > 10) {
               limit = Math.max(1.0F - (this.rotateTime - 10) / 10.0F, 0.0F) * 75.0F;
            }
         }

         this.entity.yBodyRot = approach(this.entity.yHeadRot, this.entity.yBodyRot, limit);
      }
   }

   private double delta(double[] arr) {
      return this.mean(arr, 0) - this.mean(arr, 5);
   }

   private double mean(double[] arr, int start) {
      double mean = 0.0;

      for (int i = 0; i < 5; i++) {
         mean += arr[i + start];
      }

      return mean / arr.length;
   }

   public static float approach(float target, float current, float limit) {
      float delta = Mth.wrapDegrees(current - target);
      if (delta < -limit) {
         delta = -limit;
      } else if (delta >= limit) {
         delta = limit;
      }

      return target + delta * 0.55F;
   }
}
