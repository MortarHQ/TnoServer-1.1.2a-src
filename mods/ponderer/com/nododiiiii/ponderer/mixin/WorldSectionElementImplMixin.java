package com.nododiiiii.ponderer.mixin;

import javax.annotation.Nullable;
import net.createmod.ponder.api.scene.Selection;
import net.createmod.ponder.foundation.element.WorldSectionElementImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldSectionElementImpl.class)
public class WorldSectionElementImplMixin {
   @Shadow(remap = false)
   @Nullable
   Selection section;

   @Inject(method = "erase", at = @At("HEAD"), cancellable = true, remap = false)
   private void ponderer$safeErase(Selection toErase, CallbackInfo ci) {
      if (this.section == null) {
         ci.cancel();
      }
   }

   @Inject(method = "add", at = @At("HEAD"), cancellable = true, remap = false)
   private void ponderer$safeAdd(Selection toAdd, CallbackInfo ci) {
      if (this.section == null) {
         ci.cancel();
      }
   }
}
