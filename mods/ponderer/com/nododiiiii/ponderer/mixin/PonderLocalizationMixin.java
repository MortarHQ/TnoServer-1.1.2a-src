package com.nododiiiii.ponderer.mixin;

import java.util.Map;
import net.createmod.ponder.foundation.PonderIndex;
import net.createmod.ponder.foundation.registration.PonderLocalization;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PonderLocalization.class)
public class PonderLocalizationMixin {
   @Shadow(remap = false)
   public Map<ResourceLocation, String> shared;
   @Shadow(remap = false)
   public Map<ResourceLocation, Map<String, String>> specific;

   @Inject(method = "getShared", at = @At("HEAD"), cancellable = true, remap = false)
   private void ponderer$getShared(ResourceLocation key, CallbackInfoReturnable<String> cir) {
      if (!PonderIndex.editingModeActive()) {
         if ("ponderer".equals(key.getNamespace())) {
            String val = this.shared.get(key);
            if (val != null) {
               cir.setReturnValue(val);
            }
         }
      }
   }

   @Inject(method = "getSpecific", at = @At("HEAD"), cancellable = true, remap = false)
   private void ponderer$getSpecific(ResourceLocation sceneId, String k, CallbackInfoReturnable<String> cir) {
      if (!PonderIndex.editingModeActive()) {
         if ("ponderer".equals(sceneId.getNamespace())) {
            Map<String, String> map = this.specific.get(sceneId);
            if (map != null) {
               String val = map.get(k);
               if (val != null) {
                  cir.setReturnValue(val);
               }
            }
         }
      }
   }
}
