package dev.aika.smsn.neoforge.mixin.imb11.mru;

import dev.aika.smsn.SMSN;
import dev.imb11.mru.API;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = API.class, remap = false)
public abstract class APIMixin {
   @Inject(method = "getKofiSupporters()[Ljava/lang/String;", at = @At("HEAD"), cancellable = true)
   public void getKofiSupporters(CallbackInfoReturnable<String[]> cir) {
      if (!SMSN.CONFIG.isMruApi()) {
         cir.setReturnValue(new String[0]);
      }
   }
}
