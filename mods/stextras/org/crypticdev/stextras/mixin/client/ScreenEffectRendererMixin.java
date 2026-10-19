package org.crypticdev.stextras.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ScreenEffectRenderer;
import net.minecraft.world.entity.player.Player;
import org.crypticdev.stextras.support.WispRadarHelper;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ScreenEffectRenderer.class)
public class ScreenEffectRendererMixin {
   private static final float WISP_BLACKOUT_ALPHA = 1.0F;

   @Inject(method = "renderScreenEffect", at = @At("HEAD"), cancellable = true)
   private static void stextras$renderWispBlackout(Minecraft minecraft, PoseStack poseStack, CallbackInfo ci) {
      Player player = minecraft.player;
      if (WispRadarHelper.isRadarActive(player)) {
         ci.cancel();
         RenderSystem.setShader(GameRenderer::getPositionColorShader);
         RenderSystem.disableDepthTest();
         RenderSystem.depthMask(false);
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         Matrix4f matrix4f = poseStack.last().pose();
         BufferBuilder bufferBuilder = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         bufferBuilder.addVertex(matrix4f, -1.0F, -1.0F, -0.5F).setColor(0.0F, 0.0F, 0.0F, 1.0F);
         bufferBuilder.addVertex(matrix4f, 1.0F, -1.0F, -0.5F).setColor(0.0F, 0.0F, 0.0F, 1.0F);
         bufferBuilder.addVertex(matrix4f, 1.0F, 1.0F, -0.5F).setColor(0.0F, 0.0F, 0.0F, 1.0F);
         bufferBuilder.addVertex(matrix4f, -1.0F, 1.0F, -0.5F).setColor(0.0F, 0.0F, 0.0F, 1.0F);
         BufferUploader.drawWithShader(bufferBuilder.buildOrThrow());
         RenderSystem.disableBlend();
         RenderSystem.depthMask(true);
         RenderSystem.enableDepthTest();
      }
   }
}
