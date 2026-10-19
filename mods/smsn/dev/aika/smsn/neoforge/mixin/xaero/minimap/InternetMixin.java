package dev.aika.smsn.neoforge.mixin.xaero.minimap;

import dev.aika.smsn.SMSN;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xaero.common.IXaeroMinimap;
import xaero.common.misc.Internet;

@Mixin(value = Internet.class, remap = false)
public abstract class InternetMixin {
   @Inject(method = "checkModVersion(Lxaero/common/IXaeroMinimap;)V", at = @At("HEAD"), cancellable = true)
   private static void onCheckModVersion(IXaeroMinimap modMain, CallbackInfo ci) {
      if (!SMSN.CONFIG.isXaeroMapVersionCheck()) {
         ci.cancel();
      }
   }
}
