package org.crypticdev.stextras.mixin.tensura;

import io.github.manasmods.tensura.Tensura;
import org.crypticdev.stextras.registary.STExtraRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Tensura.class, remap = false)
public class TensuraMixin {
   @Inject(
      method = "init",
      at = @At(value = "INVOKE", target = "Lio/github/manasmods/tensura/registry/TensuraRegistry;init()V", shift = Shift.AFTER),
      remap = false
   )
   private static void stextras$afterTensuraRegistryInit(CallbackInfo ci) {
      STExtraRegistry.injectInit();
   }
}
