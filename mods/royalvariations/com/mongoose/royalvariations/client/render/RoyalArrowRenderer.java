package com.mongoose.royalvariations.client.render;

import com.mongoose.royalvariations.RoyalVariations;
import com.mongoose.royalvariations.common.entities.projectile.RoyalArrow;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;

public class RoyalArrowRenderer extends ArrowRenderer<RoyalArrow> {
   public static final ResourceLocation ARROW_LOCATION = RoyalVariations.location("textures/entity/projectiles/royal_arrow.png");

   public RoyalArrowRenderer(Context p_173917_) {
      super(p_173917_);
   }

   public ResourceLocation getTextureLocation(RoyalArrow p_114482_) {
      return ARROW_LOCATION;
   }
}
