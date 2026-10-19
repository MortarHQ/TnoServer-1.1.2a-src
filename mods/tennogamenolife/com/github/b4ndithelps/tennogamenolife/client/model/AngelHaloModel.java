package com.github.b4ndithelps.tennogamenolife.client.model;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public final class AngelHaloModel extends GeoModel<AngelHalo> {
   private static final ResourceLocation MODEL = ResourceLocation.fromNamespaceAndPath("tennogamenolife", "geo/angel_halo.geo.json");
   private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("tennogamenolife", "textures/entity/anime_head_halo.png");
   private static final ResourceLocation ANIMATION = ResourceLocation.fromNamespaceAndPath("tennogamenolife", "animations/angel_halo.animation.json");

   public ResourceLocation getModelResource(AngelHalo animatable) {
      return MODEL;
   }

   public ResourceLocation getTextureResource(AngelHalo animatable) {
      return TEXTURE;
   }

   public ResourceLocation getAnimationResource(AngelHalo animatable) {
      return ANIMATION;
   }
}
