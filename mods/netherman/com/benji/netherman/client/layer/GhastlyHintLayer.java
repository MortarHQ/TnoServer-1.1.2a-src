package com.benji.netherman.client.layer;

import com.benji.netherman.NetherExp;
import com.benji.netherman.common.entity.GhastlyEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class GhastlyHintLayer extends GeoRenderLayer<GhastlyEntity> {
   private static final ResourceLocation HINT_TEXTURE = NetherExp.location("textures/entity/ghastly_hint_sheet.png");

   public GhastlyHintLayer(GeoRenderer<GhastlyEntity> entityRendererIn) {
      super(entityRendererIn);
   }

   public void render(
      PoseStack poseStack,
      GhastlyEntity animatable,
      BakedGeoModel bakedModel,
      RenderType renderType,
      MultiBufferSource bufferSource,
      VertexConsumer buffer,
      float partialTick,
      int packedLight,
      int packedOverlay
   ) {
      if ((Boolean)animatable.getEntityData().get(GhastlyEntity.SHOW_HINT)) {
         int totalFrames = 8;
         int frame = animatable.tickCount / 3 % totalFrames;
         float vHeight = 1.0F / totalFrames;
         float minV = frame * vHeight;
         float maxV = minV + vHeight;
         poseStack.pushPose();
         poseStack.translate(0.0, 1.5, 0.0);
         poseStack.mulPose(Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation());
         poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
         Matrix4f matrix = poseStack.last().pose();
         VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.entityCutoutNoCull(HINT_TEXTURE));
         float halfWidth = 0.5F;
         float halfHeight = 0.5F;
         vertexConsumer.addVertex(matrix, -halfWidth, -halfHeight, 0.0F)
            .setColor(255, 255, 255, 255)
            .setUv(1.0F, maxV)
            .setOverlay(OverlayTexture.NO_OVERLAY)
            .setLight(15728880)
            .setNormal(0.0F, 1.0F, 0.0F);
         vertexConsumer.addVertex(matrix, halfWidth, -halfHeight, 0.0F)
            .setColor(255, 255, 255, 255)
            .setUv(0.0F, maxV)
            .setOverlay(OverlayTexture.NO_OVERLAY)
            .setLight(15728880)
            .setNormal(0.0F, 1.0F, 0.0F);
         vertexConsumer.addVertex(matrix, halfWidth, halfHeight, 0.0F)
            .setColor(255, 255, 255, 255)
            .setUv(0.0F, minV)
            .setOverlay(OverlayTexture.NO_OVERLAY)
            .setLight(15728880)
            .setNormal(0.0F, 1.0F, 0.0F);
         vertexConsumer.addVertex(matrix, -halfWidth, halfHeight, 0.0F)
            .setColor(255, 255, 255, 255)
            .setUv(1.0F, minV)
            .setOverlay(OverlayTexture.NO_OVERLAY)
            .setLight(15728880)
            .setNormal(0.0F, 1.0F, 0.0F);
         poseStack.popPose();
      }
   }
}
