package dev.aika.smsn.neoforge.mixin.placebo;

import dev.aika.smsn.SMSN;
import dev.shadowsoffire.placebo.patreon.WingsManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = WingsManager.class, remap = false)
public abstract class WingsManagerMixin {
   @Inject(
      method = "init(Lnet/neoforged/fml/event/lifecycle/FMLClientSetupEvent;)V",
      at = @At(value = "INVOKE", target = "Ljava/lang/Thread;start()V"),
      cancellable = true
   )
   private static void init(CallbackInfo ci) {
      if (!SMSN.CONFIG.isPlaceboWings()) {
         ci.cancel();
      }
   }
}
