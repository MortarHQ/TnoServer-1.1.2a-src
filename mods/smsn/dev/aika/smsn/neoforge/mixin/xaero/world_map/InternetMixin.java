package dev.aika.smsn.neoforge.mixin.xaero.world_map;

import dev.aika.smsn.SMSN;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xaero.map.misc.Internet;

@Mixin(value = Internet.class, remap = false)
public abstract class InternetMixin {
   @Inject(method = "checkModVersion()V", at = @At("HEAD"), cancellable = true)
   private static void onCheckModVersion(CallbackInfo ci) {
      if (!SMSN.CONFIG.isXaeroMapVersionCheck()) {
         ci.cancel();
      }
   }
}
