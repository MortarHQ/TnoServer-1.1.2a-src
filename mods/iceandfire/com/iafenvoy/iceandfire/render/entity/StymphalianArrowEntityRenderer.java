package com.iafenvoy.iceandfire.render.entity;

import com.iafenvoy.iceandfire.entity.StymphalianArrowEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;

public class StymphalianArrowEntityRenderer extends ArrowRenderer<StymphalianArrowEntity> {
   private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("iceandfire", "textures/entity/misc/stymphalian_arrow.png");

   public StymphalianArrowEntityRenderer(Context context) {
      super(context);
   }

   public ResourceLocation getTexture(StymphalianArrowEntity entity) {
      return TEXTURE;
   }
}
