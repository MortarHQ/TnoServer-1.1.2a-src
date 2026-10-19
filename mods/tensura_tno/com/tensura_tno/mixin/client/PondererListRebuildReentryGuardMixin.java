package com.tensura_tno.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "com.nododiiiii.ponderer.ui.catnip.AbstractDeclarativeListScreen", remap = false)
public abstract class PondererListRebuildReentryGuardMixin {
   @Unique
   private boolean tensuraTno$rebuildInProgress = false;

   @Inject(method = "rebuildEntries(Ljava/lang/Double;)V", at = @At("HEAD"), cancellable = true, remap = false, require = 0)
   private void tensuraTno$skipIfAlreadyRebuilding(Double preservedScroll, CallbackInfo ci) {
      if (this.tensuraTno$rebuildInProgress) {
         ci.cancel();
      } else {
         this.tensuraTno$rebuildInProgress = true;
      }
   }

   @Inject(method = "rebuildEntries(Ljava/lang/Double;)V", at = @At("RETURN"), remap = false, require = 0)
   private void tensuraTno$clearFlagOnReturn(Double preservedScroll, CallbackInfo ci) {
      this.tensuraTno$rebuildInProgress = false;
   }
}
