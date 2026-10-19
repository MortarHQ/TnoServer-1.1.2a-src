package com.nododiiiii.ponderer.mixin;

import com.nododiiiii.ponderer.blueprint.BlueprintHandler;
import com.nododiiiii.ponderer.ui.CoordPickState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MouseHandler.class)
public class MinecraftCoordPickScrollMixin {
   @Inject(method = "onScroll", at = @At("HEAD"), cancellable = true)
   private void ponderer$interceptCoordPickScroll(long windowPointer, double xOffset, double yOffset, CallbackInfo ci) {
      Minecraft mc = Minecraft.getInstance();
      if (mc.player != null && mc.screen == null) {
         if (BlueprintHandler.INSTANCE != null && BlueprintHandler.INSTANCE.mouseScrolled(yOffset)) {
            ci.cancel();
         } else {
            if (CoordPickState.isActive() && CoordPickState.handleMouseScrolled(yOffset)) {
               ci.cancel();
            }
         }
      }
   }
}
