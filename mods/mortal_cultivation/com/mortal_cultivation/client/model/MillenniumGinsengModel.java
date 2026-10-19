package com.mortal_cultivation.client.model;

import com.mortal_cultivation.entity.MillenniumGinsengEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public final class MillenniumGinsengModel extends GeoModel<MillenniumGinsengEntity> {
   private static final ResourceLocation MODEL = ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "geo/millennium_ginseng.geo.json");
   private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "textures/entity/millennium_ginseng.png");
   private static final ResourceLocation ANIMATION = ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "animations/millennium_ginseng.animation.json");

   public ResourceLocation getModelResource(MillenniumGinsengEntity animatable) {
      return MODEL;
   }

   public ResourceLocation getTextureResource(MillenniumGinsengEntity animatable) {
      return TEXTURE;
   }

   public ResourceLocation getAnimationResource(MillenniumGinsengEntity animatable) {
      return ANIMATION;
   }
}
