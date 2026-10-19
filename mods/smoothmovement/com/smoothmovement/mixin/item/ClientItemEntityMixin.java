package com.smoothmovement.mixin.item;

import com.smoothmovement.SmoothMovement;
import com.smoothmovement.config.CommonConfiguration;
import com.smoothmovement.time.ClientLevelDeltaTime;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public abstract class ClientItemEntityMixin extends Entity {
   @Unique
   private double lerpX = this.getX();
   @Unique
   private double lerpZ;
   @Unique
   private double lerpY = this.getY();
   @Unique
   private int positionDelayTicks;
   @Unique
   private float lerpYRot;
   @Unique
   private float lerpXRot;

   public ClientItemEntityMixin(EntityType<?> p_19870_, Level p_19871_) {
      super(p_19870_, p_19871_);
      this.lerpZ = this.getZ();
   }

   public void lerpTo(double x, double y, double z, float yRot, float xRot, int p_19901_) {
      if (!((CommonConfiguration)CommonConfiguration.config.getCommonConfig()).enableItemSmoothing) {
         super.lerpTo(x, y, z, yRot, xRot, p_19901_);
      } else {
         this.positionDelayTicks = 2;
         if (this.level() instanceof ClientLevelDeltaTime deltaLevel
            && SmoothMovement.getDistanceSquared(x, y, z, this.getX(), this.getY(), this.getZ())
               < SmoothMovement.getDistanceSquared(this.lerpX, this.lerpY, this.lerpZ, this.getX(), this.getY(), this.getZ())
            && deltaLevel.getSlownessFactor() > 1.001) {
            this.positionDelayTicks = (int)Mth.clamp(Math.round(21.0 * deltaLevel.getSlownessFactor()), 2L, 100L);
         }

         this.lerpX = x;
         this.lerpY = y;
         this.lerpZ = z;
         this.lerpYRot = yRot;
         this.lerpXRot = xRot;
         this.checkUpdatePos();
      }
   }

   public void lerpMotion(double x, double y, double z) {
      if (!((CommonConfiguration)CommonConfiguration.config.getCommonConfig()).enableItemSmoothing) {
         super.lerpMotion(x, y, z);
      } else {
         if (this.positionDelayTicks < 2
            || this.tickCount < 2
            || this.level() instanceof ClientLevelDeltaTime deltaLevel && deltaLevel.getSlownessFactor() < 1.1
            || !(this.level() instanceof ClientLevelDeltaTime)) {
            this.setDeltaMovement(x, y, z);
         }
      }
   }

   @Unique
   private void checkUpdatePos() {
      if (this.positionDelayTicks > 0 && --this.positionDelayTicks == 0) {
         if (SmoothMovement.getDistanceSquared(this.lerpX, this.lerpY, this.lerpZ, this.getX(), this.getY(), this.getZ()) > 1.0) {
            this.setPos(this.lerpX, this.lerpY, this.lerpZ);
         }

         this.setRot(this.lerpYRot, this.lerpXRot);
         this.lerpX = Double.POSITIVE_INFINITY;
         this.lerpY = Double.POSITIVE_INFINITY;
         this.lerpZ = Double.POSITIVE_INFINITY;
      }
   }

   @Inject(method = "tick", at = @At("HEAD"))
   private void checkUpdatePos(CallbackInfo ci) {
      if (this.level().isClientSide && ((CommonConfiguration)CommonConfiguration.config.getCommonConfig()).enableItemSmoothing) {
         this.checkUpdatePos();
      }
   }
}
