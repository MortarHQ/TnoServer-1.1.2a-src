package com.faboslav.variantsandventures.common.client.render.entity.feature;

import com.faboslav.variantsandventures.common.VariantsAndVentures;
import com.faboslav.variantsandventures.common.client.model.ThicketEntityModel;
import com.faboslav.variantsandventures.common.entity.mob.ThicketEntity;
import com.faboslav.variantsandventures.common.init.VariantsAndVenturesModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;

public class ThicketOverlayFeatureRenderer<T extends ThicketEntity> extends RenderLayer<T, ThicketEntityModel<T>> {
   private static final ResourceLocation OVERLAY_TEXTURE = VariantsAndVentures.makeID("textures/entity/thicket/thicket_overlay.png");
   private final ThicketEntityModel model;

   public ThicketOverlayFeatureRenderer(RenderLayerParent<T, ThicketEntityModel<T>> context, EntityModelSet loader) {
      super(context);
      this.model = new ThicketEntityModel(loader.bakeLayer(VariantsAndVenturesModelLayers.THICKET_OUTER));
   }

   public void render(
      PoseStack poseStack,
      MultiBufferSource bufferSource,
      int packedLight,
      T thicket,
      float limbAngle,
      float limbDistance,
      float tickDelta,
      float animationProgress,
      float headYaw,
      float headPitch
   ) {
      coloredCutoutModelCopyLayerRender(
         this.getParentModel(),
         this.model,
         OVERLAY_TEXTURE,
         poseStack,
         bufferSource,
         packedLight,
         thicket,
         limbAngle,
         limbDistance,
         animationProgress,
         headYaw,
         headPitch,
         tickDelta,
         -1
      );
   }
}
