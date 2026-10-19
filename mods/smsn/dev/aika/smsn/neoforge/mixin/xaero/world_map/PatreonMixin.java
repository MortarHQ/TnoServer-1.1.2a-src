package dev.aika.smsn.neoforge.mixin.xaero.world_map;

import dev.aika.smsn.SMSN;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xaero.map.patreon.Patreon;

@Mixin(value = Patreon.class, remap = false)
public abstract class PatreonMixin {
   @Inject(method = "checkPatreon()V", at = @At("HEAD"), cancellable = true)
   private static void onCheckPatreon(CallbackInfo ci) {
      if (!SMSN.CONFIG.isXaeroMapPatreonCheck()) {
         ci.cancel();
      }
   }
}
