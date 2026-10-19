package com.tensura_tno.mixin.client;

import com.tensura_tno.util.PondererScreenIds;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "dev.ftb.mods.ftbquests.client.FTBQuestsClientEventHandler", remap = false)
public class FTBQuestsHudSuppressOnPonderUIMixin {
   @Inject(
      method = "onScreenRender(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/client/DeltaTracker;)V",
      at = @At("HEAD"),
      cancellable = true,
      remap = false,
      require = 0
   )
   private void tensuraTno$skipHudOnPonderUI(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) {
      if (PondererScreenIds.isPonderUI(Minecraft.getInstance().screen)) {
         ci.cancel();
      }
   }
}
