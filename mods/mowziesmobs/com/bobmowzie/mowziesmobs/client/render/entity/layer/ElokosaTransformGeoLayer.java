package com.bobmowzie.mowziesmobs.client.render.entity.layer;

import com.bobmowzie.mowziesmobs.MMCommon;
import com.bobmowzie.mowziesmobs.client.model.tools.geckolib.MowzieGeoModel;
import com.bobmowzie.mowziesmobs.server.entity.elokosa.EntityElokosa;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor.ARGB32;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class ElokosaTransformGeoLayer extends GeoRenderLayer<EntityElokosa> {
   public ElokosaTransformGeoLayer(GeoRenderer<EntityElokosa> entityRendererIn) {
      super(entityRendererIn);
   }

   protected ResourceLocation getTextureResource(EntityElokosa animatable) {
      return MMCommon.resource("textures/entity/elokosa_transforming.png");
   }

   public void render(
      PoseStack poseStack,
      EntityElokosa animatable,
      BakedGeoModel bakedModel,
      @Nullable RenderType renderType,
      MultiBufferSource bufferSource,
      @Nullable VertexConsumer buffer,
      float partialTick,
      int packedLight,
      int packedOverlay
   ) {
      RenderType renderTypeTranslucent = RenderType.entityTranslucent(this.getTextureResource(animatable));
      this.getRenderer()
         .reRender(
            this.getDefaultBakedModel(animatable),
            poseStack,
            bufferSource,
            animatable,
            renderTypeTranslucent,
            bufferSource.getBuffer(renderTypeTranslucent),
            partialTick,
            packedLight,
            packedOverlay,
            ARGB32.colorFromFloat(this.getOpacity(animatable, (MowzieGeoModel<EntityElokosa>)this.renderer.getGeoModel()), 1.0F, 1.0F, 1.0F)
         );
   }

   private float getOpacity(EntityElokosa entity, MowzieGeoModel<EntityElokosa> model) {
      float textureBlend = 0.0F;
      if (model.isInitialized()) {
         textureBlend = -model.getControllerValue("transformTextureController");
      }

      return textureBlend;
   }
}
