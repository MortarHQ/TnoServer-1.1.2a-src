package com.iafenvoy.uranus.client.model.util;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.Entity;

public class HideableLayer<T extends Entity, M extends EntityModel<T>, C extends RenderLayer<T, M>> extends RenderLayer<T, M> {
   final C layerRenderer;
   public boolean hidden = false;

   public HideableLayer(C layerRenderer, RenderLayerParent<T, M> entityRendererIn) {
      super(entityRendererIn);
      this.layerRenderer = layerRenderer;
   }

   public void render(
      PoseStack matrixStackIn,
      MultiBufferSource bufferIn,
      int packedLightIn,
      T entitylivingbaseIn,
      float limbSwing,
      float limbSwingAmount,
      float partialTicks,
      float ageInTicks,
      float netHeadYaw,
      float headPitch
   ) {
      if (!this.hidden) {
         this.layerRenderer
            .render(matrixStackIn, bufferIn, packedLightIn, entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch);
      }
   }
}
