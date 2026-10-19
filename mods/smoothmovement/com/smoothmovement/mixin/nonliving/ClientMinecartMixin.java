package com.smoothmovement.mixin.nonliving;

import com.mojang.datafixers.util.Pair;
import com.smoothmovement.SmoothMovement;
import com.smoothmovement.config.CommonConfiguration;
import com.smoothmovement.time.ClientLevelDeltaTime;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseRailBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.RailShape;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.extensions.IAbstractMinecartExtension;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractMinecart.class)
public abstract class ClientMinecartMixin extends Entity implements IAbstractMinecartExtension {
   @Shadow
   private boolean onRails;
   @Unique
   private int velocityLerpSteps = 3;
   @Shadow
   @Final
   private static Map<RailShape, Pair<Vec3i, Vec3i>> EXITS;
   @Shadow
   private int lerpSteps;
   @Shadow
   private double lerpX;
   @Shadow
   private double lerpZ;
   @Shadow
   private double lerpY;

   @Shadow
   protected abstract void moveAlongTrack(BlockPos var1, BlockState var2);

   public ClientMinecartMixin(EntityType<?> p_19870_, Level p_19871_) {
      super(p_19870_, p_19871_);
   }

   @Inject(
      method = "lerpTo",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/vehicle/AbstractMinecart;setDeltaMovement(Lnet/minecraft/world/phys/Vec3;)V"),
      cancellable = true
   )
   private void onPosLerp(double x, double y, double z, float p_20980_, float p_20981_, int steps, CallbackInfo ci) {
      if (this.level() instanceof ClientLevelDeltaTime deltaLevel
         && !(deltaLevel.getSlownessFactor() <= 1.0)
         && ((CommonConfiguration)CommonConfiguration.config.getCommonConfig()).enableMinecartSmoothing) {
         this.lerpSteps = 0;
         if (SmoothMovement.getDistanceSquared(x, y, z, this.getX(), this.getY(), this.getZ()) > 100.0) {
            this.setPos(x, y, z);
         } else {
            if (this.level().getBlockState(BlockPos.containing(x, y, z)).is(BlockTags.RAILS)) {
               this.lerpSteps = (int)Math.max(5L, Math.min(100L, Math.round(5.0 * deltaLevel.getSlownessFactor()))) * 2;
            }

            this.velocityLerpSteps = (int)Math.max(3L, Math.min(30L, Math.round(5.0 * deltaLevel.getSlownessFactor())));
            if (!this.isAlive()) {
               this.lerpX = this.getX();
               this.lerpZ = this.getZ();
               if (this.lerpY > this.getY()) {
                  this.lerpY = this.getY();
               }
            }

            ci.cancel();
         }
      }
   }

   @Inject(method = "lerpMotion", at = @At("HEAD"), cancellable = true)
   public void lerpMotion(double x, double y, double z, CallbackInfo ci) {
      if (this.level() instanceof ClientLevelDeltaTime deltaLevel
         && !(deltaLevel.getSlownessFactor() <= 1.0)
         && ((CommonConfiguration)CommonConfiguration.config.getCommonConfig()).enableMinecartSmoothing) {
         ci.cancel();
         if (this.getDeltaMovement().length() <= 0.01) {
            this.setDeltaMovement(x, y, z);
         } else {
            if (x == 0.0 && y == 0.0 && z == 0.0) {
               this.setDeltaMovement(0.0, 0.0, 0.0);
            }
         }
      }
   }

   @Inject(method = "tick", at = @At("HEAD"))
   private void onTick(CallbackInfo ci) {
      if (this.level().isClientSide && ((CommonConfiguration)CommonConfiguration.config.getCommonConfig()).enableMinecartSmoothing) {
         if (this.level() instanceof ClientLevelDeltaTime deltaLevel && !(deltaLevel.getSlownessFactor() <= 1.0)) {
            Vec3 diff = this.position()
               .subtract(new Vec3(this.lerpX, this.lerpY, this.lerpZ))
               .reverse()
               .multiply(1.0 / this.velocityLerpSteps, 1.0 / this.velocityLerpSteps, 1.0 / this.velocityLerpSteps);
            if (diff.x < 0.0 && this.getDeltaMovement().x >= 0.0 || diff.x > 0.0 && this.getDeltaMovement().x <= 0.0) {
               diff = new Vec3(0.0, diff.y, diff.z);
            }

            if (diff.y < 0.0 && this.getDeltaMovement().y >= 0.0 || diff.y > 0.0 && this.getDeltaMovement().y <= 0.0) {
               diff = new Vec3(diff.x, 0.0, diff.z);
            }

            if (diff.z < 0.0 && this.getDeltaMovement().z >= 0.0 || diff.z > 0.0 && this.getDeltaMovement().z <= 0.0) {
               diff = new Vec3(diff.x, diff.y, 0.0);
            }

            this.setDeltaMovement(
               new Vec3(
                  diff.x() * 0.3 + this.getDeltaMovement().x() * 0.7,
                  diff.y() * 0.3 + this.getDeltaMovement().y() * 0.7,
                  diff.z() * 0.3 + this.getDeltaMovement().z() * 0.7
               )
            );
            if (!this.isNoGravity()) {
               double d0 = this.isInWater() ? -0.005 : -0.04;
               this.setDeltaMovement(this.getDeltaMovement().add(0.0, d0, 0.0));
            }

            int k = Mth.floor(this.getX());
            int i = Mth.floor(this.getY());
            int j = Mth.floor(this.getZ());
            if (this.level().getBlockState(new BlockPos(k, i - 1, j)).is(BlockTags.RAILS)) {
               i--;
            }

            BlockPos blockpos = new BlockPos(k, i, j);
            BlockState blockstate = this.level().getBlockState(blockpos);
            this.onRails = BaseRailBlock.isRail(blockstate);
            if (this.canUseRail() && this.onRails) {
               this.moveAlongTrack(blockpos, blockstate);
            }

            this.updateInWaterStateAndDoFluidPushing();
         }
      }
   }
}
