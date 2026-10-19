package com.tno_tensura_extras.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.tno_tensura_extras.TensuraExtras;
import com.tno_tensura_extras.entity.DiabloDecoyEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public final class DiabloDecoyRenderer extends GeoEntityRenderer<DiabloDecoyEntity> {
   public DiabloDecoyRenderer(Context context) {
      super(context, new DiabloDecoyModel());
      this.shadowRadius = 0.5F;
      this.addRenderLayer(new DiabloDecoyRenderer.EyesLayer(this));
   }

   private static final class EyesLayer extends GeoRenderLayer<DiabloDecoyEntity> {
      private static final RenderType EYES = RenderType.eyes(TensuraExtras.id("textures/entity/diablo_eyes.png"));

      private EyesLayer(DiabloDecoyRenderer renderer) {
         super(renderer);
      }

      public void render(
         PoseStack poseStack,
         DiabloDecoyEntity entity,
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
