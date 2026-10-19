package com.smoothmovement.mixin.player;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.smoothmovement.config.CommonConfiguration;
import com.smoothmovement.time.ServerTime;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = ServerGamePacketListenerImpl.class, priority = 1)
public class ServerGamePacketListenerImplMixin {
   @ModifyConstant(method = "handleMovePlayer", constant = @Constant(doubleValue = 0.0625), require = 0)
   private double scalePlayerMovement(double constant) {
      return !((CommonConfiguration)CommonConfiguration.config.getCommonConfig()).enableRubberbandingLagAdjustedMovement
         ? constant
         : ServerTime.slownessFactor * constant;
   }

   @WrapOperation(method = "handleMovePlayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/phys/Vec3;lengthSqr()D"), require = 0)
   private double scalePlayerMovement(Vec3 instance, Operation<Double> original) {
      return !((CommonConfiguration)CommonConfiguration.config.getCommonConfig()).enableRubberbandingLagAdjustedMovement
         ? instance.lengthSqr()
         : ServerTime.slownessFactor * instance.lengthSqr();
   }
}
