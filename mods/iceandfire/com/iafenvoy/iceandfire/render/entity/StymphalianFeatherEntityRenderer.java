package com.iafenvoy.iceandfire.render.entity;

import com.iafenvoy.iceandfire.entity.StymphalianFeatherEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;

public class StymphalianFeatherEntityRenderer extends ArrowRenderer<StymphalianFeatherEntity> {
   private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("iceandfire", "textures/entity/stymphalianbird/feather.png");

   public StymphalianFeatherEntityRenderer(Context context) {
      super(context);
   }

   public ResourceLocation getTexture(StymphalianFeatherEntity entity) {
      return TEXTURE;
   }
}
