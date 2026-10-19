package com.tensura_tno.mixin.client;

import com.tensura_tno.compat.TrAddonCompat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(targets = "com.github.dominickwd04.traddon.TrAddon", remap = false)
public class TrAddonInitFixMixin {
   @Inject(method = "init", at = @At("HEAD"), cancellable = true, remap = false, require = 0)
   private static void tensuraTno$cancelPrematureInit(CallbackInfo ci) {
      if (!TrAddonCompat.allowInit) {
         ci.cancel();
      }
   }
}
