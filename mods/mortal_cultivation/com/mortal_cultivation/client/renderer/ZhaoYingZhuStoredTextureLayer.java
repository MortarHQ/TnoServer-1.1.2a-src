package com.mortal_cultivation.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mortal_cultivation.client.model.ZhaoYingZhuModel;
import com.mortal_cultivation.entity.ZhaoYingZhuEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;
import software.bernie.geckolib.util.Color;

public final class ZhaoYingZhuStoredTextureLayer extends GeoRenderLayer<ZhaoYingZhuEntity> {
   public ZhaoYingZhuStoredTextureLayer(GeoRenderer<ZhaoYingZhuEntity> renderer) {
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
      if (!orb.isFilled()) {
         float progress = smoothStep(orb.getRuneAbsorptionProgress(partialTick));
         if (!(progress <= 0.001F)) {
            RenderType storedRenderType = RenderType.entityTranslucent(ZhaoYingZhuModel.STORED_TEXTURE, false);
            int color = Color.ofRGBA(1.0F, 1.0F, 1.0F, progress).argbInt();
            this.getRenderer()
               .reRender(
                  bakedModel,
                  poseStack,
                  bufferSource,
                  orb,
                  storedRenderType,
                  bufferSource.getBuffer(storedRenderType),
                  partialTick,
                  packedLight,
                  packedOverlay,
                  color
               );
         }
      }
   }

   static float smoothStep(float progress) {
      float clamped = Mth.clamp(progress, 0.0F, 1.0F);
      return clamped * clamped * (3.0F - 2.0F * clamped);
   }
}
