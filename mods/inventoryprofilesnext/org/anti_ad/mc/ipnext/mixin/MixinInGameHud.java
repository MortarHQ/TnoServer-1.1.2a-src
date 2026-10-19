package org.anti_ad.mc.ipnext.mixin;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import org.anti_ad.mc.common.gui.NativeContext;
import org.anti_ad.mc.ipnext.event.LockSlotsHandler;
import org.anti_ad.mc.ipnext.event.autorefill.AutoRefillHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class MixinInGameHud {
   @Inject(at = @At("HEAD"), method = "renderHotbar")
   protected void preRenderHotbar(GuiGraphics var1, DeltaTracker var2, CallbackInfo var3) {
      NativeContext var4 = new NativeContext(var1, null);
      LockSlotsHandler.INSTANCE.preRenderHud(var4);
      AutoRefillHandler.INSTANCE.preRenderHud(var4);
   }

   @Inject(at = @At("TAIL"), method = "renderHotbar")
   protected void postRenderHotbar(GuiGraphics var1, DeltaTracker var2, CallbackInfo var3) {
      NativeContext var4 = new NativeContext(var1, null);
      LockSlotsHandler.INSTANCE.postRenderHud(var4);
      AutoRefillHandler.INSTANCE.postRenderHud(var4);
   }
}
