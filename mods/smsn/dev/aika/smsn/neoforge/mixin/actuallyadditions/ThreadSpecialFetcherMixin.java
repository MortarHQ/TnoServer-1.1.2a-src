package dev.aika.smsn.neoforge.mixin.actuallyadditions;

import de.ellpeck.actuallyadditions.mod.misc.special.ThreadSpecialFetcher;
import dev.aika.smsn.SMSN;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ThreadSpecialFetcher.class, remap = false)
public class ThreadSpecialFetcherMixin {
   @Inject(method = "run()V", at = @At("HEAD"), cancellable = true)
   public void run(CallbackInfo ci) {
      if (!SMSN.CONFIG.isActuallyAdditionsSpecialPeopleStuff()) {
         ci.cancel();
      }
   }
}
