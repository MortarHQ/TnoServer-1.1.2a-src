package org.crypticdev.stextras.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import org.crypticdev.stextras.client.render.WispRadarRenderer;
import org.crypticdev.stextras.support.WispRadarHelper;
import org.joml.Matrix4f;
import org.joml.Matrix4fStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
   @Shadow
   private Minecraft minecraft;

   @Inject(
      method = "renderItemInHand(Lnet/minecraft/client/Camera;FLorg/joml/Matrix4f;)V",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/renderer/ScreenEffectRenderer;renderScreenEffect(Lnet/minecraft/client/Minecraft;Lcom/mojang/blaze3d/vertex/PoseStack;)V",
         shift = Shift.AFTER
      )
   )
   private void stextras$renderWispRadarAfterScreenEffect(Camera camera, float partialTick, Matrix4f viewMatrix, CallbackInfo ci) {
      if (!camera.isDetached() && WispRadarHelper.isRadarActive(this.minecraft.player)) {
         Matrix4fStack modelViewStack = RenderSystem.getModelViewStack();
         modelViewStack.pushMatrix();
         modelViewStack.mul(viewMatrix);
         RenderSystem.applyModelViewMatrix();
         WispRadarRenderer.renderRadar(camera);
         modelViewStack.popMatrix();
         RenderSystem.applyModelViewMatrix();
      }
   }
}
