package com.tensura_tno.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(targets = "io.github.manasmods.tensura.util.client.RenderHelper", remap = false)
public abstract class TensuraFadeStateGuardMixin {
   @Inject(method = "handleFade(Lnet/minecraft/client/gui/GuiGraphics;Ljava/lang/Runnable;)Z", at = @At("RETURN"), remap = false, require = 0)
   private static void tensuraTno$resetRenderState(GuiGraphics graphics, Runnable runnable, CallbackInfoReturnable<Boolean> cir) {
      try {
         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
         RenderSystem.disableBlend();
      } catch (Throwable var4) {
      }
   }
}
