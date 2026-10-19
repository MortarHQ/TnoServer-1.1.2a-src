package com.tensura_tno.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(targets = "nosferatu.eldergrove.EldergroveBiomeSpreader", remap = false)
public abstract class EldergroveTaintedBiomeNoSpreadMixin {
   @Inject(method = "setTaintedBiome", at = @At("HEAD"), cancellable = true, require = 0)
   private static void tensuraTno$disableTaintedBiomeConversion(CallbackInfoReturnable<Boolean> cir) {
      cir.setReturnValue(false);
   }
}
