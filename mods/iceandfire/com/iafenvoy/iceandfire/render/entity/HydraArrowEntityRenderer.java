package com.iafenvoy.iceandfire.render.entity;

import com.iafenvoy.iceandfire.entity.HydraArrowEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;

public class HydraArrowEntityRenderer extends ArrowRenderer<HydraArrowEntity> {
   private static final ResourceLocation TEXTURES = ResourceLocation.fromNamespaceAndPath("iceandfire", "textures/entity/misc/hydra_arrow.png");

   public HydraArrowEntityRenderer(Context context) {
      super(context);
   }

   public ResourceLocation getTexture(HydraArrowEntity entity) {
      return TEXTURES;
   }
}
