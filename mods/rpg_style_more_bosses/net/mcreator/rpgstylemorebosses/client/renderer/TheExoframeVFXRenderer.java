package net.mcreator.rpgstylemorebosses.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.mcreator.rpgstylemorebosses.entity.TheExoframeVFXEntity;
import net.mcreator.rpgstylemorebosses.entity.layer.TheExoframeVFXLayer;
import net.mcreator.rpgstylemorebosses.entity.model.TheExoframeVFXModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class TheExoframeVFXRenderer extends GeoEntityRenderer<TheExoframeVFXEntity> {
   public TheExoframeVFXRenderer(Context renderManager) {
      super(renderManager, new TheExoframeVFXModel());
      this.shadowRadius = 0.0F;
      this.addRenderLayer(new TheExoframeVFXLayer(this));
   }

   public RenderType getRenderType(TheExoframeVFXEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
      return RenderType.entityTranslucent(this.getTextureLocation(animatable));
   }

   public void preRender(
      PoseStack poseStack,
      TheExoframeVFXEntity entity,
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
