package com.iafenvoy.iceandfire.render.model;

import com.iafenvoy.iceandfire.entity.util.IFlapable;
import com.iafenvoy.uranus.client.model.basic.BasicModelPart;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;

public class IFChainBuffer {
   private int yawTimer;
   private float yawVariation;
   private int pitchTimer;
   private float pitchVariation;
   private float prevYawVariation;
   private float prevPitchVariation;

   public void resetRotations() {
      this.yawVariation = 0.0F;
      this.pitchVariation = 0.0F;
      this.prevYawVariation = 0.0F;
      this.prevPitchVariation = 0.0F;
   }

   private boolean compareDouble(double a, double b) {
      double c = a - b;
      return Math.abs(c - 1.0) <= 0.01;
   }

   public void calculateChainSwingBuffer(float maxAngle, int bufferTime, float angleDecrement, float divisor, LivingEntity entity) {
      this.prevYawVariation = this.yawVariation;
      if (!this.compareDouble(entity.yBodyRot, entity.yBodyRotO) && Mth.abs(this.yawVariation) < maxAngle) {
         this.yawVariation = this.yawVariation + Mth.clamp((entity.yBodyRotO - entity.yBodyRot) / divisor, -maxAngle, maxAngle);
      }

      if (this.yawVariation > angleDecrement) {
         if (this.yawTimer > bufferTime) {
            this.yawVariation -= angleDecrement;
            if (Mth.abs(this.yawVariation) < angleDecrement) {
               this.yawVariation = angleDecrement;
               this.yawTimer = 0;
            }
         } else {
            this.yawTimer++;
         }
      } else if (this.yawVariation < -1.0F * angleDecrement) {
         if (this.yawTimer > bufferTime) {
            this.yawVariation += angleDecrement;
            if (Mth.abs(this.yawVariation) < angleDecrement) {
               this.yawVariation = angleDecrement;
               this.yawTimer = 0;
            }
         } else {
            this.yawTimer++;
         }
      }
   }

   public void calculateChainPitchBuffer(float maxAngle, int bufferTime, float angleDecrement, float divisor, LivingEntity entity) {
      this.prevPitchVariation = entity.xRotO;
      this.pitchVariation = entity.getXRot();
   }

   public void calculateChainWaveBuffer(float maxAngle, int bufferTime, float angleDecrement, float divisor, LivingEntity entity) {
      this.prevPitchVariation = this.pitchVariation;
      if (!(Math.abs(entity.getXRot()) > maxAngle)) {
         if (!this.compareDouble(entity.getXRot(), entity.xRotO) && Mth.abs(this.pitchVariation) < maxAngle) {
            this.pitchVariation = this.pitchVariation + Mth.clamp((entity.xRotO - entity.getXRot()) / divisor, -maxAngle, maxAngle);
         }

         if (this.pitchVariation > angleDecrement) {
            if (this.pitchTimer > bufferTime) {
               this.pitchVariation -= angleDecrement;
               if (Mth.abs(this.pitchVariation) < angleDecrement) {
                  this.pitchVariation = 0.0F;
                  this.pitchTimer = 0;
               }
            } else {
               this.pitchTimer++;
            }
         } else if (this.pitchVariation < -1.0F * angleDecrement) {
            if (this.pitchTimer > bufferTime) {
               this.pitchVariation += angleDecrement;
               if (Mth.abs(this.pitchVariation) < angleDecrement) {
                  this.pitchVariation = 0.0F;
                  this.pitchTimer = 0;
               }
            } else {
               this.pitchTimer++;
            }
         }
      }
   }

   public void calculateChainFlapBuffer(float maxAngle, int bufferTime, float angleDecrement, float divisor, LivingEntity entity) {
      this.prevYawVariation = this.yawVariation;
      if (!this.compareDouble(entity.yBodyRot, entity.yBodyRotO) && Mth.abs(this.yawVariation) < maxAngle) {
         this.yawVariation = this.yawVariation + Mth.clamp((entity.yBodyRotO - entity.yBodyRot) / divisor, -maxAngle, maxAngle);
         if (entity instanceof IFlapable flap && Math.abs(entity.yBodyRotO - entity.yBodyRot) > 15.0) {
            flap.flapWings();
         }
      }

      if (this.yawVariation > angleDecrement) {
         if (this.yawTimer > bufferTime) {
            this.yawVariation -= angleDecrement;
            if (Mth.abs(this.yawVariation) < angleDecrement) {
               this.yawVariation = 0.0F;
               this.yawTimer = 0;
            }
         } else {
            this.yawTimer++;
         }
      } else if (this.yawVariation < -1.0F * angleDecrement) {
         if (this.yawTimer > bufferTime) {
            this.yawVariation += angleDecrement;
            if (Mth.abs(this.yawVariation) < angleDecrement) {
               this.yawVariation = 0.0F;
               this.yawTimer = 0;
            }
         } else {
            this.yawTimer++;
         }
      }
   }

   public void calculateChainFlapBufferHead(float maxAngle, int bufferTime, float angleDecrement, float divisor, LivingEntity entity) {
      this.prevYawVariation = this.yawVariation;
      if (!this.compareDouble(entity.yHeadRotO, entity.yHeadRot) && Mth.abs(this.yawVariation) < maxAngle) {
         this.yawVariation = this.yawVariation + Mth.clamp((entity.yHeadRot - entity.yHeadRotO) / divisor, -maxAngle, maxAngle);
         if (entity instanceof IFlapable flap && Math.abs(entity.yHeadRot - entity.yHeadRotO) > 15.0) {
            flap.flapWings();
         }
      }

      if (this.yawVariation > angleDecrement) {
         if (this.yawTimer > bufferTime) {
            this.yawVariation -= angleDecrement;
            if (Mth.abs(this.yawVariation) < angleDecrement) {
               this.yawVariation = 0.0F;
               this.yawTimer = 0;
            }
         } else {
            this.yawTimer++;
         }
      } else if (this.yawVariation < -1.0F * angleDecrement) {
         if (this.yawTimer > bufferTime) {
            this.yawVariation += angleDecrement;
            if (Mth.abs(this.yawVariation) < angleDecrement) {
               this.yawVariation = 0.0F;
               this.yawTimer = 0;
            }
         } else {
            this.yawTimer++;
         }
      }
   }

   public void calculateChainSwingBuffer(float maxAngle, int bufferTime, float angleDecrement, LivingEntity entity) {
      this.calculateChainSwingBuffer(maxAngle, bufferTime, angleDecrement, 1.0F, entity);
   }

   public void calculateChainWaveBuffer(float maxAngle, int bufferTime, float angleDecrement, LivingEntity entity) {
      this.calculateChainWaveBuffer(maxAngle, bufferTime, angleDecrement, 1.0F, entity);
   }

   public void calculateChainFlapBuffer(float maxAngle, int bufferTime, float angleDecrement, LivingEntity entity) {
      this.calculateChainFlapBuffer(maxAngle, bufferTime, angleDecrement, 1.0F, entity);
   }

   public void applyChainSwingBuffer(BasicModelPart... boxes) {
      float rotateAmount = (float) (Math.PI / 180.0) * Mth.lerp(this.getPartialTicks(), this.prevYawVariation, this.yawVariation) / boxes.length;

      for (BasicModelPart box : boxes) {
         box.rotateAngleY += rotateAmount;
      }
   }

   public void applyChainWaveBuffer(BasicModelPart... boxes) {
      float rotateAmount = (float) (Math.PI / 180.0) * Mth.lerp(this.getPartialTicks(), this.prevYawVariation, this.yawVariation) / boxes.length;

      for (BasicModelPart box : boxes) {
         box.rotateAngleX += rotateAmount;
      }
   }

   public void applyChainFlapBuffer(BasicModelPart... boxes) {
      float rotateAmount = (float) (Math.PI / 180.0) * Mth.lerp(this.getPartialTicks(), this.prevYawVariation, this.yawVariation) / boxes.length;

      for (BasicModelPart box : boxes) {
         box.rotateAngleZ += rotateAmount;
      }
   }

   public void applyChainFlapBufferReverse(BasicModelPart... boxes) {
      float rotateAmount = (float) (Math.PI / 180.0) * Mth.lerp(this.getPartialTicks(), this.prevYawVariation, this.yawVariation) / boxes.length;

      for (BasicModelPart box : boxes) {
         box.rotateAngleZ -= rotateAmount * 0.5F;
      }
   }

   public void applyChainSwingBufferReverse(BasicModelPart... boxes) {
      float rotateAmount = (float) (Math.PI / 180.0) * Mth.lerp(this.getPartialTicks(), this.prevYawVariation, this.yawVariation) / boxes.length;

      for (BasicModelPart box : boxes) {
         box.rotateAngleY -= rotateAmount;
      }
   }

   public void applyChainWaveBufferReverse(BasicModelPart... boxes) {
      float rotateAmount = (float) (Math.PI / 180.0) * Mth.lerp(this.getPartialTicks(), this.prevYawVariation, this.yawVariation) / boxes.length;

      for (BasicModelPart box : boxes) {
         box.rotateAngleX -= rotateAmount;
      }
   }

   private float getPartialTicks() {
      return Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(false);
   }
}
