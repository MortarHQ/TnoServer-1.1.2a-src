package com.tno_tensura_extras.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.tno_tensura_extras.entity.DiabloEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public final class DiabloRenderer extends GeoEntityRenderer<DiabloEntity> {
   public DiabloRenderer(Context context) {
      super(context, new DiabloModel());
      this.shadowRadius = 0.5F;
      this.addRenderLayer(new DiabloRenderer.EyesLayer(this));
   }

   private static final class EyesLayer extends GeoRenderLayer<DiabloEntity> {
      private static final RenderType EYES = RenderType.eyes(ResourceLocation.fromNamespaceAndPath("tno_tensura_extras", "textures/entity/diablo_eyes.png"));

      private EyesLayer(DiabloRenderer renderer) {
         super(renderer);
      }

      public void render(
         PoseStack poseStack,
         DiabloEntity entity,
         BakedGeoModel model,
         RenderType renderType,
         MultiBufferSource buffers,
         VertexConsumer buffer,
         float partialTick,
         int packedLight,
         int packedOverlay
      ) {
         if (!entity.isInvisible()) {
            this.getRenderer().reRender(model, poseStack, buffers, entity, EYES, buffers.getBuffer(EYES), partialTick, 15728880, packedOverlay, -1);
         }
      }
   }
}
