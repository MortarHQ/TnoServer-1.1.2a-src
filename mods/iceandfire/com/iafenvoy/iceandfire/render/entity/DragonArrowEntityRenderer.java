package com.iafenvoy.iceandfire.render.entity;

import com.iafenvoy.iceandfire.entity.DragonArrowEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;

public class DragonArrowEntityRenderer extends ArrowRenderer<DragonArrowEntity> {
   private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("iceandfire", "textures/entity/misc/dragonbone_arrow.png");

   public DragonArrowEntityRenderer(Context context) {
      super(context);
   }

   public ResourceLocation getTexture(DragonArrowEntity entity) {
      return TEXTURE;
   }
}
