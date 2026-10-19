package com.smoothmovement.mixin.livingentity;

import com.smoothmovement.config.CommonConfiguration;
import com.smoothmovement.time.ServerTime;
import net.minecraft.world.entity.ai.control.LookControl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LookControl.class)
public class ServerLookControlMixin {
   @Shadow
   protected float xMaxRotAngle;

   @ModifyVariable(method = "rotateTowards", at = @At("HEAD"), ordinal = 2)
   private float adjustSteps(float steps) {
      return steps != this.xMaxRotAngle && ((CommonConfiguration)CommonConfiguration.config.getCommonConfig()).enableLivingEntityLagAdjustedMovement
         ? steps * ServerTime.slownessFactor
         : steps;
   }
}
