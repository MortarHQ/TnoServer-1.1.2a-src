package com.foxablazeultimate.client.render;

import com.foxablazeultimate.entity.HellSniperCircleEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public final class HellSniperCircleGlowLayer extends GeoRenderLayer<HellSniperCircleEntity> {
   private static final ResourceLocation GLOW_TEXTURE = ResourceLocation.fromNamespaceAndPath(
      "foxablazeultimate", "textures/entity/hell_sniper_magic_circle_glowmask.png"
   );

   public HellSniperCircleGlowLayer(GeoRenderer<HellSniperCircleEntity> renderer) {
      super(renderer);
   }

   public void render(
      PoseStack poseStack,
      HellSniperCircleEntity circle,
      BakedGeoModel bakedModel,
      RenderType renderType,
      MultiBufferSource bufferSource,
      VertexConsumer buffer,
      float partialTick,
      int packedLight,
      int packedOverlay
   ) {
      RenderType glow = FoxAblazeRenderTypes.additiveEmissive(GLOW_TEXTURE);
      this.getRenderer()
         .reRender(
            bakedModel,
            poseStack,
            bufferSource,
            circle,
            glow,
            bufferSource.getBuffer(glow),
            partialTick,
            15728880,
            packedOverlay,
            this.getRenderer().getRenderColor(circle, partialTick, packedLight).argbInt()
         );
   }
}
