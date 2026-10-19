package com.tensura_tno.mixin.client;

import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(targets = "net.unusual.block_factorys_bosses.client.camera.renderer.BlackRenderer", remap = false)
public abstract class BossesRiseBlackOverlayDisableMixin {
   @Inject(method = "onRenderGui", at = @At("HEAD"), cancellable = true, remap = true, require = 0)
   private static void tensuraTno$skipBlackOverlay(GuiGraphics graphics, CallbackInfo ci) {
      ci.cancel();
   }
}
