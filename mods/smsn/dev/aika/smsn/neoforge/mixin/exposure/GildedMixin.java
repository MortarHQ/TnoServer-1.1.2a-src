package dev.aika.smsn.neoforge.mixin.exposure;

import dev.aika.smsn.SMSN;
import io.github.mortuusars.exposure.util.supporter.Gilded;
import io.github.mortuusars.exposure.util.supporter.Supporter;
import java.util.Collections;
import java.util.List;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = Gilded.class, remap = false)
public abstract class GildedMixin {
   @Shadow
   @Nullable
   private List<Supporter> gildedSupporters;

   @Inject(method = "query()Ljava/util/List;", at = @At("HEAD"), cancellable = true)
   public void query(CallbackInfoReturnable<List<Supporter>> cir) {
      if (!SMSN.CONFIG.isExposureGoldenCameraSkin()) {
         this.gildedSupporters = Collections.emptyList();
         cir.setReturnValue(this.gildedSupporters);
      }
   }
}
