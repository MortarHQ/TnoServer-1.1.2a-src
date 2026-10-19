package com.benji.netherman.client.layer;

import com.benji.netherman.NetherExp;
import com.benji.netherman.common.entity.TraderEntity;
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
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class TraderHintLayer extends GeoRenderLayer<TraderEntity> {
   private static final ResourceLocation HINT_TEXTURE = NetherExp.location("textures/entity/trader_hint.png");
   private static final ResourceLocation WAIT_TEXTURE = NetherExp.location("textures/entity/trader_wait.png");

   public TraderHintLayer(GeoEntityRenderer<TraderEntity> entityRendererIn) {
      super(entityRendererIn);
   }

   public void render(
      PoseStack poseStack,
      TraderEntity animatable,
      BakedGeoModel bakedModel,
      RenderType renderType,
      MultiBufferSource bufferSource,
      VertexConsumer buffer,
      float partialTick,
      int packedLight,
      int packedOverlay
   ) {
      int tradeState = (Integer)animatable.getEntityData().get(TraderEntity.TRADE_STATE);
      boolean showHint = (Boolean)animatable.getEntityData().get(TraderEntity.SHOW_HINT);
      if (showHint || tradeState == 1) {
         ResourceLocation currentTexture = tradeState == 1 ? WAIT_TEXTURE : HINT_TEXTURE;
         poseStack.pushPose();
         poseStack.translate(0.0, 3.0, 0.0);
         poseStack.mulPose(Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation());
         poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
         poseStack.scale(0.03F, 0.03F, 0.03F);
         Matrix4f matrix4f = poseStack.last().pose();
         VertexConsumer vertexconsumer = bufferSource.getBuffer(RenderType.entityCutoutNoCull(currentTexture));
         float halfWidth = 14.0F;
         float height = 30.0F;
         int fullLight = 15728880;
         vertexconsumer.addVertex(matrix4f, -halfWidth, 0.0F, 0.0F)
            .setColor(255, 255, 255, 255)
            .setUv(1.0F, 1.0F)
            .setOverlay(OverlayTexture.NO_OVERLAY)
            .setLight(fullLight)
            .setNormal(0.0F, 1.0F, 0.0F);
         vertexconsumer.addVertex(matrix4f, -halfWidth, height, 0.0F)
            .setColor(255, 255, 255, 255)
            .setUv(1.0F, 0.0F)
            .setOverlay(OverlayTexture.NO_OVERLAY)
            .setLight(fullLight)
            .setNormal(0.0F, 1.0F, 0.0F);
         vertexconsumer.addVertex(matrix4f, halfWidth, height, 0.0F)
            .setColor(255, 255, 255, 255)
            .setUv(0.0F, 0.0F)
            .setOverlay(OverlayTexture.NO_OVERLAY)
            .setLight(fullLight)
            .setNormal(0.0F, 1.0F, 0.0F);
         vertexconsumer.addVertex(matrix4f, halfWidth, 0.0F, 0.0F)
            .setColor(255, 255, 255, 255)
            .setUv(0.0F, 1.0F)
            .setOverlay(OverlayTexture.NO_OVERLAY)
            .setLight(fullLight)
            .setNormal(0.0F, 1.0F, 0.0F);
         poseStack.popPose();
      }
   }
}
