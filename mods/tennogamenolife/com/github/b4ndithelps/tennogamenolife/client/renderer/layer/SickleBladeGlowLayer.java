package com.github.b4ndithelps.tennogamenolife.client.renderer.layer;

import com.github.b4ndithelps.tennogamenolife.item.SickleItem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public final class SickleBladeGlowLayer extends GeoRenderLayer<SickleItem> {
   public SickleBladeGlowLayer(GeoRenderer<SickleItem> renderer) {
      super(renderer);
   }

   public void renderForBone(
      PoseStack poseStack,
      SickleItem animatable,
      GeoBone bone,
      RenderType renderType,
      MultiBufferSource bufferSource,
      VertexConsumer buffer,
      float partialTick,
      int packedLight,
      int packedOverlay
   ) {
      if (bone.getName().endsWith("_poly_mesh")) {
         RenderType glowRenderType = RenderType.entityTranslucentEmissive(this.getTextureResource(animatable));
         VertexConsumer glowBuffer = bufferSource.getBuffer(glowRenderType);
         this.getRenderer().renderCubesOfBone(poseStack, bone, glowBuffer, 15728880, packedOverlay, -1);
         bufferSource.getBuffer(renderType);
      }
   }
}
