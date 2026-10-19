package xaero.map.mixin;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xaero.map.core.XaeroWorldMapCore;

@Mixin(Gui.class)
public class MixinOptionalGui {
   @Inject(at = @At("HEAD"), method = "renderCrosshair", cancellable = true)
   public void onRenderCrosshair(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo info) {
      if (XaeroWorldMapCore.onRenderCrosshair(guiGraphics)) {
         info.cancel();
      }
   }
}
