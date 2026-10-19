package com.benji.netherman.client.layer;

import com.benji.netherman.NetherExp;
import com.benji.netherman.common.entity.DoctorEntity;
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

public class DoctorHintLayer extends GeoRenderLayer<DoctorEntity> {
   private static final ResourceLocation HINT_1 = NetherExp.location("textures/entity/doctor_hint.png");
   private static final ResourceLocation HINT_2 = NetherExp.location("textures/entity/doctoradditional_hint.png");
   private static final ResourceLocation HINT_3 = NetherExp.location("textures/entity/doctortrade_hint.png");

   public DoctorHintLayer(GeoEntityRenderer<DoctorEntity> entityRendererIn) {
      super(entityRendererIn);
   }

   public void render(
      PoseStack poseStack,
      DoctorEntity animatable,
      BakedGeoModel bakedModel,
      RenderType renderType,
      MultiBufferSource bufferSource,
      VertexConsumer buffer,
      float partialTick,
      int packedLight,
      int packedOverlay
   ) {
      int hintState = (Integer)animatable.getEntityData().get(DoctorEntity.HINT_STATE);
      if (hintState != 0) {
         ResourceLocation currentTexture = HINT_1;
         if (hintState == 2) {
            currentTexture = HINT_2;
         }

         if (hintState == 3) {
            currentTexture = HINT_3;
         }

         poseStack.pushPose();
         poseStack.translate(0.0, 2.5, 0.0);
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
