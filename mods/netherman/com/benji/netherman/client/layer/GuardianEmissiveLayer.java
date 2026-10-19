package com.benji.netherman.client.layer;

import com.benji.netherman.common.entity.GuardianEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class GuardianEmissiveLayer extends GeoRenderLayer<GuardianEntity> {
   private final ResourceLocation emissiveTexture;

   public GuardianEmissiveLayer(GeoRenderer<GuardianEntity> entityRendererIn, ResourceLocation emissiveTexture) {
      super(entityRendererIn);
      this.emissiveTexture = emissiveTexture;
   }

   public void render(
      PoseStack poseStack,
      GuardianEntity animatable,
      BakedGeoModel bakedModel,
      RenderType renderType,
      MultiBufferSource bufferSource,
      VertexConsumer buffer,
      float partialTick,
      int packedLight,
      int packedOverlay
   ) {
      if (animatable.isBuffed()) {
         RenderType glowRenderType = RenderType.eyes(this.emissiveTexture);
         VertexConsumer glowBuffer = bufferSource.getBuffer(glowRenderType);
         this.getRenderer().reRender(bakedModel, poseStack, bufferSource, animatable, glowRenderType, glowBuffer, partialTick, 15728880, packedOverlay, -1);
      }
   }
}
