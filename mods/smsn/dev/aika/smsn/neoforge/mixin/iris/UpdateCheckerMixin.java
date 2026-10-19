package dev.aika.smsn.neoforge.mixin.iris;

import dev.aika.smsn.SMSN;
import net.irisshaders.iris.UpdateChecker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = UpdateChecker.class, remap = false)
public abstract class UpdateCheckerMixin {
   @Inject(method = "checkForUpdates(Lnet/irisshaders/iris/config/IrisConfig;)V", at = @At("HEAD"), cancellable = true)
   public void checkForUpdates(CallbackInfo ci) {
      if (!SMSN.CONFIG.isIrisUpdateCheck()) {
         ci.cancel();
      }
   }
}
