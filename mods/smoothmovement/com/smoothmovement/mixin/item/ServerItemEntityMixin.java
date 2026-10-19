package com.smoothmovement.mixin.item;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.smoothmovement.config.CommonConfiguration;
import com.smoothmovement.time.ServerTime;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(ItemEntity.class)
public abstract class ServerItemEntityMixin extends Entity {
   public ServerItemEntityMixin(EntityType<?> p_19870_, Level p_19871_) {
      super(p_19870_, p_19871_);
   }

   @ModifyArg(
      method = "tick",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/world/entity/item/ItemEntity;move(Lnet/minecraft/world/entity/MoverType;Lnet/minecraft/world/phys/Vec3;)V"
      )
   )
   private Vec3 changeVel(Vec3 org) {
      return !this.level().isClientSide && ((CommonConfiguration)CommonConfiguration.config.getCommonConfig()).enableItemLagAdjustedMovement
         ? org.scale(ServerTime.slownessFactor)
         : org;
   }

   @ModifyReturnValue(method = "getDefaultGravity", at = @At("RETURN"))
   private double changeGravity(double gravity) {
      return !this.level().isClientSide && ((CommonConfiguration)CommonConfiguration.config.getCommonConfig()).enableItemLagAdjustedMovement
         ? gravity * ServerTime.slownessFactor
         : gravity;
   }

   @ModifyArg(
      method = "tick",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/world/phys/Vec3;multiply(DDD)Lnet/minecraft/world/phys/Vec3;", ordinal = 0),
      index = 0
   )
   private double changeVelX(double x) {
      return !this.level().isClientSide
            && ServerTime.hasLag()
            && ((CommonConfiguration)CommonConfiguration.config.getCommonConfig()).enableItemLagAdjustedMovement
         ? Math.pow(x, ServerTime.slownessFactor * 0.45)
         : x;
   }

   @ModifyArg(
      method = "tick",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/world/phys/Vec3;multiply(DDD)Lnet/minecraft/world/phys/Vec3;", ordinal = 0),
      index = 1
   )
   private double changeVelY(double y) {
      return !this.level().isClientSide
            && ServerTime.hasLag()
            && ((CommonConfiguration)CommonConfiguration.config.getCommonConfig()).enableItemLagAdjustedMovement
         ? Math.pow(y, ServerTime.slownessFactor * 0.45)
         : y;
   }

   @ModifyArg(
      method = "tick",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/world/phys/Vec3;multiply(DDD)Lnet/minecraft/world/phys/Vec3;", ordinal = 0),
      index = 2
   )
   private double changeVelZ(double z) {
      return !this.level().isClientSide
            && ServerTime.hasLag()
            && ((CommonConfiguration)CommonConfiguration.config.getCommonConfig()).enableItemLagAdjustedMovement
         ? Math.pow(z, ServerTime.slownessFactor * 0.45)
         : z;
   }
}
