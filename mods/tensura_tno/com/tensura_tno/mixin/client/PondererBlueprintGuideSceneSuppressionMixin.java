package com.tensura_tno.mixin.client;

import com.tensura_tno.util.PondererPaperFeatureAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "com.nododiiiii.ponderer.ponder.DynamicPonderPlugin", remap = false)
public abstract class PondererBlueprintGuideSceneSuppressionMixin {
   @Inject(method = "registerBlueprintGuideScene", at = @At("HEAD"), cancellable = true, remap = false, require = 0)
   private void tensuraTno$skipIfDisabled(CallbackInfo ci) {
      if (!PondererPaperFeatureAccess.isEnabled()) {
         ci.cancel();
      }
   }
}
