package net.witherspawnanimation.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.witherspawnanimation.entity.AnimatedAltarStormSoilMinZEntity;
import net.witherspawnanimation.entity.layer.AnimatedAltarStormSoilMinZLayer;
import net.witherspawnanimation.entity.model.AnimatedAltarStormSoilMinZModel;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class AnimatedAltarStormSoilMinZRenderer extends GeoEntityRenderer<AnimatedAltarStormSoilMinZEntity> {
   public AnimatedAltarStormSoilMinZRenderer(Context renderManager) {
      super(renderManager, new AnimatedAltarStormSoilMinZModel());
      this.shadowRadius = 0.0F;
      this.addRenderLayer(new AnimatedAltarStormSoilMinZLayer(this));
   }

   public RenderType getRenderType(AnimatedAltarStormSoilMinZEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
      return RenderType.entityTranslucent(this.getTextureLocation(animatable));
   }

   public void preRender(
      PoseStack poseStack,
      AnimatedAltarStormSoilMinZEntity entity,
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
