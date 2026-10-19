package com.iafenvoy.iceandfire.render.entity;

import com.iafenvoy.iceandfire.entity.AmphithereArrowEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;

public class AmphithereArrowEntityRenderer extends ArrowRenderer<AmphithereArrowEntity> {
   private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("iceandfire", "textures/entity/misc/amphithere_arrow.png");

   public AmphithereArrowEntityRenderer(Context context) {
      super(context);
   }

   public ResourceLocation getTexture(AmphithereArrowEntity entity) {
      return TEXTURE;
   }
}
