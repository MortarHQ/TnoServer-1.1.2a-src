package it.hurts.sskirillss.relics.mixin;

import com.mojang.blaze3d.platform.Window;
import it.hurts.sskirillss.relics.client.screen.base.IAutoScaledScreen;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Window.class)
public class WindowMixin {
   @Inject(method = "getGuiScale", at = @At("HEAD"), cancellable = true)
   public void getGuiScale(CallbackInfoReturnable<Double> cir) {
      Minecraft MC = Minecraft.getInstance();
      Window window = (Window)this;
      if (MC.screen instanceof IAutoScaledScreen screen) {
         cir.setReturnValue((double)window.calculateScale(screen.getAutoScale(), MC.isEnforceUnicode()));
      }
   }

   @Inject(method = "getGuiScaledHeight", at = @At("HEAD"), cancellable = true)
   public void getGuiScaledHeight(CallbackInfoReturnable<Integer> cir) {
      Minecraft MC = Minecraft.getInstance();
      Window window = (Window)this;
      if (MC.screen instanceof IAutoScaledScreen screen) {
         double var8 = window.calculateScale(screen.getAutoScale(), MC.isEnforceUnicode());
         int height = (int)(window.getHeight() / var8);
         cir.setReturnValue(window.getHeight() / var8 > height ? height + 1 : height);
      }
   }

   @Inject(method = "getGuiScaledWidth", at = @At("HEAD"), cancellable = true)
   public void getGuiScaledWidth(CallbackInfoReturnable<Integer> cir) {
      Minecraft MC = Minecraft.getInstance();
      Window window = (Window)this;
      if (MC.screen instanceof IAutoScaledScreen screen) {
         double var8 = window.calculateScale(screen.getAutoScale(), MC.isEnforceUnicode());
         int width = (int)(window.getWidth() / var8);
         cir.setReturnValue(window.getWidth() / var8 > width ? width + 1 : width);
      }
   }
}
