package org.crypticdev.stextras.mixin.tensura.client.screen;

import io.github.manasmods.tensura.util.client.ScreenHelper;
import org.crypticdev.stextras.client.screen.STPrestigeScreens;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ScreenHelper.class, remap = false)
public abstract class ScreenHelperMixin {
   private static final int STPRESTIGE = -5;

   @Inject(method = "openScreen(I)V", at = @At("HEAD"), cancellable = true)
   private static void stextras$openPrestigeScreen(int id, CallbackInfo ci) {
      if (id == -5) {
         ScreenHelper.openScreen(new STPrestigeScreens());
         ci.cancel();
      }
   }
}
