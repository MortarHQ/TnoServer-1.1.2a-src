package com.smoothmovement.mixin.nonliving;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.smoothmovement.config.CommonConfiguration;
import com.smoothmovement.time.ServerTime;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(FallingBlockEntity.class)
public abstract class ServerFallingBlockMixin extends Entity {
   public ServerFallingBlockMixin(EntityType<?> p_19870_, Level p_19871_) {
      super(p_19870_, p_19871_);
   }

   @ModifyArg(
      method = "tick",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/world/entity/item/FallingBlockEntity;move(Lnet/minecraft/world/entity/MoverType;Lnet/minecraft/world/phys/Vec3;)V"
      )
   )
   private Vec3 changeVel(Vec3 org) {
      return !this.level().isClientSide && ((CommonConfiguration)CommonConfiguration.config.getCommonConfig()).enableFallingBlockLagAdjustedMovement
         ? org.scale(ServerTime.slownessFactor)
         : org;
   }

   @ModifyReturnValue(method = "getDefaultGravity", at = @At("RETURN"))
   private double changeGravity(double gravity) {
      return !this.level().isClientSide && ((CommonConfiguration)CommonConfiguration.config.getCommonConfig()).enableFallingBlockLagAdjustedMovement
         ? gravity * ServerTime.slownessFactor
         : gravity;
   }
}
