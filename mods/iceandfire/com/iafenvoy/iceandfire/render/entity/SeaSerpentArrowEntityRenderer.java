package com.iafenvoy.iceandfire.render.entity;

import com.iafenvoy.iceandfire.entity.SeaSerpentArrowEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;

public class SeaSerpentArrowEntityRenderer extends ArrowRenderer<SeaSerpentArrowEntity> {
   private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("iceandfire", "textures/entity/misc/sea_serpent_arrow.png");

   public SeaSerpentArrowEntityRenderer(Context context) {
      super(context);
   }

   public ResourceLocation getTexture(SeaSerpentArrowEntity entity) {
      return TEXTURE;
   }
}
