package cn.leolezury.eternalstarlight.common.client.renderer.blockentity;

import cn.leolezury.eternalstarlight.common.block.entity.EclipseCoreBlockEntity;
import cn.leolezury.eternalstarlight.common.client.ESRenderType;
import cn.leolezury.eternalstarlight.common.client.handler.ESClientHandler;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;

public class EclipseCoreRenderer extends DuskLightRenderer<EclipseCoreBlockEntity> {
   public EclipseCoreRenderer(Context context) {
      super(context);
   }

   public void render(EclipseCoreBlockEntity blockEntity, float f, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int j) {
      super.render(blockEntity, f, poseStack, multiBufferSource, i, j);
      VertexConsumer vertexConsumer = ESClientHandler.AFTER_LEVEL_BUFFER_SOURCE.getBuffer(ESRenderType.ECLIPSE);
      Pose pose = poseStack.last();
      float height = 0.5F + 7.0F * blockEntity.getEclipseProgress(f);
      float size = 32.0F * blockEntity.getEclipseProgress(f);
      vertexConsumer.addVertex(pose, size + 1.0F, height, size + 1.0F)
         .setColor(1, 1, 1, 1)
         .setUv(0.0F, 0.0F)
         .setOverlay(OverlayTexture.NO_OVERLAY)
         .setLight(15728880)
         .setNormal(pose, 0.0F, 1.0F, 0.0F);
      vertexConsumer.addVertex(pose, size + 1.0F, height, -size)
         .setColor(1, 1, 1, 1)
         .setUv(0.0F, 1.0F)
         .setOverlay(OverlayTexture.NO_OVERLAY)
         .setLight(15728880)
         .setNormal(pose, 0.0F, 1.0F, 0.0F);
      vertexConsumer.addVertex(pose, -size, height, -size)
         .setColor(1, 1, 1, 1)
         .setUv(1.0F, 1.0F)
         .setOverlay(OverlayTexture.NO_OVERLAY)
         .setLight(15728880)
         .setNormal(pose, 0.0F, 1.0F, 0.0F);
      vertexConsumer.addVertex(pose, -size, height, size + 1.0F)
         .setColor(1, 1, 1, 1)
         .setUv(1.0F, 0.0F)
         .setOverlay(OverlayTexture.NO_OVERLAY)
         .setLight(15728880)
         .setNormal(pose, 0.0F, 1.0F, 0.0F);
   }

   public boolean shouldRenderOffScreen(EclipseCoreBlockEntity blockEntity) {
      return true;
   }

   @Override
   public int getViewDistance() {
      return 256;
   }
}
