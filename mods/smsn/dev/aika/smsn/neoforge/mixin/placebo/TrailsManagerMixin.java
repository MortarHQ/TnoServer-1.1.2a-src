package dev.aika.smsn.neoforge.mixin.placebo;

import dev.aika.smsn.SMSN;
import dev.shadowsoffire.placebo.patreon.TrailsManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = TrailsManager.class, remap = false)
public abstract class TrailsManagerMixin {
   @Inject(method = "init()V", at = @At("HEAD"), cancellable = true)
   private static void init(CallbackInfo ci) {
      if (!SMSN.CONFIG.isPlaceboTrails()) {
         ci.cancel();
      }
   }
}
