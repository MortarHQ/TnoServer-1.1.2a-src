package dev.aika.smsn.neoforge.mixin.exposure;

import dev.aika.smsn.SMSN;
import io.github.mortuusars.exposure.util.supporter.Patreon;
import io.github.mortuusars.exposure.util.supporter.Supporter;
import io.github.mortuusars.exposure.util.supporter.Patreon.Tier;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = Patreon.class, remap = false)
public abstract class PatreonMixin {
   @Shadow
   @Nullable
   private Map<Tier, List<Supporter>> patrons;

   @Inject(method = "query()Ljava/util/Map;", at = @At("HEAD"), cancellable = true)
   public void query(CallbackInfoReturnable<Map<Tier, List<Supporter>>> cir) {
      if (!SMSN.CONFIG.isExposureGoldenCameraSkin()) {
         this.patrons = Collections.emptyMap();
         cir.setReturnValue(this.patrons);
      }
   }
}
