package com.github.b4ndithelps.tennogamenolife.client.renderer;

import com.github.b4ndithelps.tennogamenolife.client.model.AngelHalo;
import com.github.b4ndithelps.tennogamenolife.client.model.AngelHaloModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoObjectRenderer;

public final class AngelHaloRenderer extends GeoObjectRenderer<AngelHalo> {
   public AngelHaloRenderer() {
      super(new AngelHaloModel());
   }

   public long getInstanceId(AngelHalo animatable) {
      return animatable.getInstanceId();
   }

   public void preRender(
      PoseStack poseStack,
      AngelHalo animatable,
      BakedGeoModel model,
      MultiBufferSource bufferSource,
      VertexConsumer buffer,
      boolean isReRender,
      float partialTick,
      int packedLight,
      int packedOverlay,
      int colour
   ) {
      super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, colour);
      poseStack.translate(-0.5F, -0.51F, -0.5F);
   }

   public RenderType getRenderType(AngelHalo animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
      return RenderType.entityTranslucentEmissive(texture);
   }
}
