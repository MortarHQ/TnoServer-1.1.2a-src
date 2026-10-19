package net.jadenxgamer.netherexp.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.SplashRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SplashRenderer.class)
public class SplashRendererMixin {
   @Unique
   private static final int JNE_COLOR = -1558241;

   @WrapOperation(
      method = "render",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;drawCenteredString(Lnet/minecraft/client/gui/Font;Ljava/lang/String;III)V")
   )
   private void netherexp$renderJNEColorText(GuiGraphics instance, Font font, String text, int x, int y, int color, Operation<Void> original) {
      if (text != null && text.startsWith("[JNE]")) {
         int newColor = JNEConfigs.RED_SPLASH_TEXT.get() ? -1558241 : color;
         original.call(new Object[]{instance, font, text.substring(5), x, y, newColor});
      } else {
         original.call(new Object[]{instance, font, text, x, y, color});
      }
   }
}
