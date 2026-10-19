package it.hurts.octostudios.immersiveui.mixin;

import it.hurts.octostudios.immersiveui.util.CommonCode;
import java.util.Random;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class GooeyMixin {
   @Shadow
   @Final
   private Minecraft minecraft;
   @Unique
   Random random = new Random();

   @Inject(method = "render(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/client/DeltaTracker;)V", at = @At("TAIL"))
   public void renderCode(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) {
      CommonCode.gooeyRenderCode(deltaTracker.getGameTimeDeltaPartialTick(true));
   }
}
