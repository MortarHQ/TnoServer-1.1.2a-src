package net.mcreator.rpgstylemorebosses.entity.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.mcreator.rpgstylemorebosses.entity.TheExoframeVFX2Entity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class TheExoframeVFX2Layer extends GeoRenderLayer<TheExoframeVFX2Entity> {
   private static final ResourceLocation LAYER = ResourceLocation.parse("rpg_style_more_bosses:textures/entities/_100x100px.png");

   public TheExoframeVFX2Layer(GeoRenderer<TheExoframeVFX2Entity> entityRenderer) {
      super(entityRenderer);
   }

   public void render(
      PoseStack poseStack,
      TheExoframeVFX2Entity animatable,
      BakedGeoModel bakedModel,
      RenderType renderType,
      MultiBufferSource bufferSource,
      VertexConsumer buffer,
      float partialTick,
      int packedLight,
      int packedOverlay
   ) {
      RenderType glowRenderType = RenderType.eyes(LAYER);
      this.getRenderer()
         .reRender(
            this.getDefaultBakedModel(animatable),
            poseStack,
            bufferSource,
            animatable,
            glowRenderType,
            bufferSource.getBuffer(glowRenderType),
            partialTick,
            packedLight,
            OverlayTexture.NO_OVERLAY,
            -1
         );
   }
}
