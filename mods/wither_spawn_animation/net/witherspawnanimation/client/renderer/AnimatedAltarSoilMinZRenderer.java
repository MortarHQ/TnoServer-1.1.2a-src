package net.witherspawnanimation.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.witherspawnanimation.entity.AnimatedAltarSoilMinZEntity;
import net.witherspawnanimation.entity.layer.AnimatedAltarSoilMinZLayer;
import net.witherspawnanimation.entity.model.AnimatedAltarSoilMinZModel;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class AnimatedAltarSoilMinZRenderer extends GeoEntityRenderer<AnimatedAltarSoilMinZEntity> {
   public AnimatedAltarSoilMinZRenderer(Context renderManager) {
      super(renderManager, new AnimatedAltarSoilMinZModel());
      this.shadowRadius = 0.0F;
      this.addRenderLayer(new AnimatedAltarSoilMinZLayer(this));
   }

   public RenderType getRenderType(AnimatedAltarSoilMinZEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
      return RenderType.entityTranslucent(this.getTextureLocation(animatable));
   }

   public void preRender(
      PoseStack poseStack,
      AnimatedAltarSoilMinZEntity entity,
      BakedGeoModel model,
      MultiBufferSource bufferSource,
      VertexConsumer buffer,
      boolean isReRender,
      float partialTick,
      int packedLight,
      int packedOverlay,
      int color
   ) {
      float scale = 1.0F;
      this.scaleHeight = scale;
      this.scaleWidth = scale;
      super.preRender(poseStack, entity, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, color);
   }
}
