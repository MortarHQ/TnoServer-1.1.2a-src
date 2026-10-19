package com.tensura_tno.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(targets = "nosferatu.eldergrove.TaintedHeartBlock", remap = false)
public abstract class EldergroveTaintedHeartNoSpreadMixin {
   @Inject(method = {"onPlace", "tick", "randomTick"}, at = @At("HEAD"), cancellable = true, require = 0)
   private void tensuraTno$disableTaintTicks(CallbackInfo ci) {
      ci.cancel();
   }

   @Inject(method = "spreadTaint", at = @At("HEAD"), cancellable = true, require = 0)
   private static void tensuraTno$disableTaintSpread(CallbackInfo ci) {
      ci.cancel();
   }
}
