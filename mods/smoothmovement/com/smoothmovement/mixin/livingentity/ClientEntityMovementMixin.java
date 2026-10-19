package com.smoothmovement.mixin.livingentity;

import com.smoothmovement.config.CommonConfiguration;
import com.smoothmovement.time.ClientLevelDeltaTime;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class ClientEntityMovementMixin extends Entity {
   @Shadow
   protected int lerpSteps;
   @Shadow
   protected int lerpHeadSteps;
   @Unique
   private int defaultLerp = 3;
   @Shadow
   protected double lerpX;
   @Shadow
   protected double lerpZ;
   @Shadow
   protected double lerpY;

   @Shadow
   public abstract void remove(RemovalReason var1);

   @Shadow
   public abstract boolean isAlive();

   public ClientEntityMovementMixin(EntityType<?> p_19870_, Level p_19871_) {
      super(p_19870_, p_19871_);
   }

   @Inject(method = "<init>", at = @At("RETURN"))
   private void onInit(EntityType entityType, Level p_20967_, CallbackInfo ci) {
      this.defaultLerp = Math.max(3, entityType.updateInterval());
   }

   @Inject(method = "lerpTo", at = @At("TAIL"))
   private void onPosLerp(double x, double y, double z, float p_20980_, float p_20981_, int steps, CallbackInfo ci) {
      if (((CommonConfiguration)CommonConfiguration.config.getCommonConfig()).enableLivingEntitySmoothing) {
         this.lerpSteps = this.defaultLerp;
         if (this.level() instanceof ClientLevelDeltaTime deltaLevel) {
            this.lerpSteps = (int)Math.max(3L, Math.min(30L, Math.round(this.defaultLerp * deltaLevel.getSlownessFactor())));
         }

         if (!this.isAlive()) {
            this.lerpX = this.getX();
            this.lerpZ = this.getZ();
            if (this.lerpY > this.getY()) {
               this.lerpY = this.getY();
            }

            this.lerpSteps = 3;
         }
      }
   }

   @Inject(method = "lerpHeadTo", at = @At("TAIL"))
   private void onPosLerpHead(float p_21005_, int p_21006_, CallbackInfo ci) {
      if (((CommonConfiguration)CommonConfiguration.config.getCommonConfig()).enableLivingEntitySmoothing) {
         this.lerpHeadSteps = this.defaultLerp;
         if (this.level() instanceof ClientLevelDeltaTime deltaLevel) {
            this.lerpHeadSteps = (int)Math.max(3L, Math.min(30L, Math.round(this.defaultLerp * deltaLevel.getSlownessFactor())));
         }

         if (!this.isAlive()) {
            this.lerpHeadSteps = 3;
         }
      }
   }
}
