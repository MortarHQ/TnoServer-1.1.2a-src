package com.faboslav.variantsandventures.common.client.render.entity.feature;

import com.faboslav.variantsandventures.common.VariantsAndVentures;
import com.faboslav.variantsandventures.common.client.model.GelidEntityModel;
import com.faboslav.variantsandventures.common.entity.mob.GelidEntity;
import com.faboslav.variantsandventures.common.init.VariantsAndVenturesModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;

public class GelidOverlayFeatureRenderer<T extends GelidEntity> extends RenderLayer<T, GelidEntityModel<T>> {
   private static final ResourceLocation OVERLAY_TEXTURE = VariantsAndVentures.makeID("textures/entity/gelid/gelid_overlay.png");
   private final GelidEntityModel model;

   public GelidOverlayFeatureRenderer(RenderLayerParent<T, GelidEntityModel<T>> context, EntityModelSet loader) {
      super(context);
      this.model = new GelidEntityModel(loader.bakeLayer(VariantsAndVenturesModelLayers.GELID_OUTER));
   }

   public void render(
      PoseStack poseStack,
      MultiBufferSource bufferSource,
      int packedLight,
      T gelid,
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
         gelid,
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
