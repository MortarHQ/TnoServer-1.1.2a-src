package net.unusual.block_factorys_bosses.mixins.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.GameRenderer;
import net.unusual.block_factorys_bosses.client.camera.renderer.CameraRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
   @Inject(method = "bobView", cancellable = true, at = @At("HEAD"))
   private void noBobView(PoseStack stack, float partialTick, CallbackInfo ci) {
      if (CameraRenderer.viewBobCancel-- > 0) {
         ci.cancel();
      }
   }
}
