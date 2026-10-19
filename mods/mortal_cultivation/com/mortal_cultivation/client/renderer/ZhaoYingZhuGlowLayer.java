package com.mortal_cultivation.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mortal_cultivation.client.model.ZhaoYingZhuModel;
import com.mortal_cultivation.entity.ZhaoYingZhuEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;
import software.bernie.geckolib.util.Color;

public final class ZhaoYingZhuGlowLayer extends GeoRenderLayer<ZhaoYingZhuEntity> {
   private static final float GLOW_STRENGTH = 0.55F;

   public ZhaoYingZhuGlowLayer(GeoRenderer<ZhaoYingZhuEntity> renderer) {
      super(renderer);
   }

   public void render(
      PoseStack poseStack,
      ZhaoYingZhuEntity orb,
      BakedGeoModel bakedModel,
      @Nullable RenderType renderType,
      MultiBufferSource bufferSource,
      @Nullable VertexConsumer buffer,
      float partialTick,
      int packedLight,
      int packedOverlay
   ) {
      float storedWeight = ZhaoYingZhuStoredTextureLayer.smoothStep(orb.getRuneAbsorptionProgress(partialTick));
      this.renderGlow(bakedModel, poseStack, bufferSource, orb, ZhaoYingZhuModel.BASE_TEXTURE, 1.0F - storedWeight, partialTick);
      this.renderGlow(bakedModel, poseStack, bufferSource, orb, ZhaoYingZhuModel.STORED_TEXTURE, storedWeight, partialTick);
   }

   private void renderGlow(
      BakedGeoModel bakedModel,
      PoseStack poseStack,
      MultiBufferSource bufferSource,
      ZhaoYingZhuEntity orb,
      ResourceLocation texture,
      float weight,
      float partialTick
   ) {
      if (!(weight <= 0.001F)) {
         RenderType glowType = RenderType.eyes(texture);
         float intensity = 0.55F * weight;
         int color = Color.ofRGBA(intensity, intensity, intensity, 1.0F).argbInt();
         this.getRenderer()
            .reRender(
               bakedModel, poseStack, bufferSource, orb, glowType, bufferSource.getBuffer(glowType), partialTick, 15728880, OverlayTexture.NO_OVERLAY, color
            );
      }
   }
}
