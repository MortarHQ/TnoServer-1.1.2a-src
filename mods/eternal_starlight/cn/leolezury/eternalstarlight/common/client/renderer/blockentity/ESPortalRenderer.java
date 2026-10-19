package cn.leolezury.eternalstarlight.common.client.renderer.blockentity;

import cn.leolezury.eternalstarlight.common.block.ESPortalBlock;
import cn.leolezury.eternalstarlight.common.block.entity.ESPortalBlockEntity;
import cn.leolezury.eternalstarlight.common.client.ESRenderType;
import cn.leolezury.eternalstarlight.common.client.handler.ESClientHandler;
import cn.leolezury.eternalstarlight.common.config.ESConfig;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction.Axis;

public class ESPortalRenderer<T extends ESPortalBlockEntity> implements BlockEntityRenderer<T> {
   public ESPortalRenderer(Context context) {
   }

   public void render(T portal, float f, PoseStack stack, MultiBufferSource bufferSource, int light, int overlay) {
      if (ESConfig.INSTANCE.enablePortalShader && (Boolean)portal.getBlockState().getValue(ESPortalBlock.CENTER)) {
         VertexConsumer vertexConsumer = ESClientHandler.AFTER_LEVEL_BUFFER_SOURCE.getBuffer(ESRenderType.PORTAL);
         Pose pose = stack.last();
         float radius = 0.6F
            * ((Integer)portal.getBlockState().getValue(ESPortalBlock.SIZE)).intValue()
            * (
               Math.min(
                     portal.getClientSideTickCount()
                        + Minecraft.getInstance()
                           .getTimer()
                           .getGameTimeDeltaPartialTick(Minecraft.getInstance().level != null && Minecraft.getInstance().level.tickRateManager().runsNormally()),
                     60.0F
                  )
                  / 60.0F
            );
         if (portal.getBlockState().getValue(ESPortalBlock.AXIS) == Axis.X) {
            vertexConsumer.addVertex(pose, -radius, -radius, 0.5F)
               .setColor(1, 1, 1, 1)
               .setUv(0.0F, 0.0F)
               .setOverlay(OverlayTexture.NO_OVERLAY)
               .setLight(15728880)
               .setNormal(pose, 0.0F, 1.0F, 0.0F);
            vertexConsumer.addVertex(pose, -radius, 1.0F + radius, 0.5F)
               .setColor(1, 1, 1, 1)
               .setUv(0.0F, 1.0F)
               .setOverlay(OverlayTexture.NO_OVERLAY)
               .setLight(15728880)
               .setNormal(pose, 0.0F, 1.0F, 0.0F);
            vertexConsumer.addVertex(pose, 1.0F + radius, 1.0F + radius, 0.5F)
               .setColor(1, 1, 1, 1)
               .setUv(1.0F, 1.0F)
               .setOverlay(OverlayTexture.NO_OVERLAY)
               .setLight(15728880)
               .setNormal(pose, 0.0F, 1.0F, 0.0F);
            vertexConsumer.addVertex(pose, 1.0F + radius, -radius, 0.5F)
               .setColor(1, 1, 1, 1)
               .setUv(1.0F, 0.0F)
               .setOverlay(OverlayTexture.NO_OVERLAY)
               .setLight(15728880)
               .setNormal(pose, 0.0F, 1.0F, 0.0F);
         } else {
            vertexConsumer.addVertex(pose, 0.5F, -radius, -radius)
               .setColor(1, 1, 1, 1)
               .setUv(0.0F, 0.0F)
               .setOverlay(OverlayTexture.NO_OVERLAY)
               .setLight(15728880)
               .setNormal(pose, 0.0F, 1.0F, 0.0F);
            vertexConsumer.addVertex(pose, 0.5F, 1.0F + radius, -radius)
               .setColor(1, 1, 1, 1)
               .setUv(0.0F, 1.0F)
               .setOverlay(OverlayTexture.NO_OVERLAY)
               .setLight(15728880)
               .setNormal(pose, 0.0F, 1.0F, 0.0F);
            vertexConsumer.addVertex(pose, 0.5F, 1.0F + radius, 1.0F + radius)
               .setColor(1, 1, 1, 1)
               .setUv(1.0F, 1.0F)
               .setOverlay(OverlayTexture.NO_OVERLAY)
               .setLight(15728880)
               .setNormal(pose, 0.0F, 1.0F, 0.0F);
            vertexConsumer.addVertex(pose, 0.5F, -radius, 1.0F + radius)
               .setColor(1, 1, 1, 1)
               .setUv(1.0F, 0.0F)
               .setOverlay(OverlayTexture.NO_OVERLAY)
               .setLight(15728880)
               .setNormal(pose, 0.0F, 1.0F, 0.0F);
         }
      }
   }
}
