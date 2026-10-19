package com.tno_tensura_extras.mixin.client;

import com.tno_tensura_extras.client.TimeTravelClient;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {
   @Inject(
      method = "runTick(Z)V",
      at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/pipeline/RenderTarget;unbindWrite()V", shift = Shift.BEFORE, ordinal = 0)
   )
   private void tnoExtras$renderFinalOverlay(boolean renderLevel, CallbackInfo callbackInfo) {
      TimeTravelClient.renderFinalFrameOverlay();
   }
}
