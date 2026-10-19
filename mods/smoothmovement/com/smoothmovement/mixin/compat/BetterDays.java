package com.smoothmovement.mixin.compat;

import betterdays.client.TimeInterpolator;
import com.smoothmovement.config.CommonConfiguration;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = TimeInterpolator.class, remap = false)
public abstract class BetterDays {
   @Inject(method = "onRenderTickEvent", at = @At("HEAD"), cancellable = true)
   private static void cancelEvent(float renderTickTime, CallbackInfo ci) {
      if (((CommonConfiguration)CommonConfiguration.config.getCommonConfig()).enableSkySmoothing) {
         ci.cancel();
      }
   }

   @Inject(method = "onClientTickEvent", at = @At("HEAD"), cancellable = true)
   private static void cancelEventTick(Minecraft minecraft, CallbackInfo ci) {
      if (((CommonConfiguration)CommonConfiguration.config.getCommonConfig()).enableSkySmoothing) {
         ci.cancel();
      }
   }
}
