package com.tensura_tno.mixin.client;

import java.util.concurrent.ThreadLocalRandom;
import java.util.random.RandomGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Pseudo
@Mixin(targets = "com.blorb.morerelics.relics.WonderOfU", remap = false)
public class MoreRelicsWonderOfUMixin {
   @Redirect(
      method = "applyCalamity",
      at = @At(value = "INVOKE", target = "Ljava/util/random/RandomGenerator;getDefault()Ljava/util/random/RandomGenerator;"),
      remap = false,
      require = 0
   )
   private static RandomGenerator tensuraTno$useThreadLocalRandom() {
      return ThreadLocalRandom.current();
   }
}
