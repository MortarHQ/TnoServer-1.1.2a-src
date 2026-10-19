package net.witherspawnanimation.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.witherspawnanimation.entity.AnimatedAltarBygoneMinXEntity;
import net.witherspawnanimation.entity.layer.AnimatedAltarBygoneMinXLayer;
import net.witherspawnanimation.entity.model.AnimatedAltarBygoneMinXModel;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class AnimatedAltarBygoneMinXRenderer extends GeoEntityRenderer<AnimatedAltarBygoneMinXEntity> {
   public AnimatedAltarBygoneMinXRenderer(Context renderManager) {
      super(renderManager, new AnimatedAltarBygoneMinXModel());
      this.shadowRadius = 0.0F;
      this.addRenderLayer(new AnimatedAltarBygoneMinXLayer(this));
   }

   public RenderType getRenderType(AnimatedAltarBygoneMinXEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
      return RenderType.entityTranslucent(this.getTextureLocation(animatable));
   }

   public void preRender(
      PoseStack poseStack,
      AnimatedAltarBygoneMinXEntity entity,
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
