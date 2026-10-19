package net.crypticmc.tr_unique_monsters.mixin.tensura;

import io.github.manasmods.tensura.Tensura;
import net.crypticmc.tr_unique_monsters.registry.TRUniqueMobsRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Tensura.class, remap = false)
public class TensuraMixin {
   @Inject(
      method = "init()V",
      at = @At(value = "INVOKE", target = "Lio/github/manasmods/tensura/registry/TensuraRegistry;init()V", shift = Shift.AFTER),
      remap = false
   )
   private static void afterTensuraRegistryInit(CallbackInfo ci) {
      TRUniqueMobsRegistry.injectInit();
   }
}
