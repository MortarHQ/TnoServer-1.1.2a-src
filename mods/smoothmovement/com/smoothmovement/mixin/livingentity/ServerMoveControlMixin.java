package com.smoothmovement.mixin.livingentity;

import com.smoothmovement.config.CommonConfiguration;
import com.smoothmovement.time.ServerTime;
import net.minecraft.world.entity.ai.control.MoveControl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(MoveControl.class)
public class ServerMoveControlMixin {
   @ModifyVariable(method = "rotlerp", at = @At("HEAD"), ordinal = 2, argsOnly = true)
   private float adjustSteps(float steps) {
      return !((CommonConfiguration)CommonConfiguration.config.getCommonConfig()).enableLivingEntityLagAdjustedMovement
         ? steps
         : steps * ServerTime.slownessFactor;
   }

   @ModifyConstant(method = "tick", constant = @Constant(doubleValue = 2.5000003E-7F))
   private double adjustThreshold(double original) {
      return !((CommonConfiguration)CommonConfiguration.config.getCommonConfig()).enableLivingEntityLagAdjustedMovement
         ? original
         : 2.5000003E-7F * ServerTime.slownessFactor;
   }
}
