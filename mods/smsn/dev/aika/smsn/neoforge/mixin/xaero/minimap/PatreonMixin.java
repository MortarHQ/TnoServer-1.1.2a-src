package dev.aika.smsn.neoforge.mixin.xaero.minimap;

import dev.aika.smsn.SMSN;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xaero.common.IXaeroMinimap;
import xaero.common.patreon.Patreon;

@Mixin(value = Patreon.class, remap = false)
public abstract class PatreonMixin {
   @Inject(method = "checkPatreon(Lxaero/common/IXaeroMinimap;)V", at = @At("HEAD"), cancellable = true)
   private static void onCheckPatreon(IXaeroMinimap modMain, CallbackInfo ci) {
      if (!SMSN.CONFIG.isXaeroMapPatreonCheck()) {
         ci.cancel();
      }
   }
}
