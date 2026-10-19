package com.mortal_cultivation.client.model;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public final class PlayerMonkeyTailModel extends GeoModel<PlayerMonkeyTail> {
   private static final ResourceLocation MODEL = ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "geo/player_monkey_tail.geo.json");
   public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "textures/entity/spirit_monkey.png");
   private static final ResourceLocation ANIMATION = ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "animations/player_monkey_tail.animation.json");

   public ResourceLocation getModelResource(PlayerMonkeyTail tail) {
      return MODEL;
   }

   public ResourceLocation getTextureResource(PlayerMonkeyTail tail) {
      return TEXTURE;
   }

   public ResourceLocation getAnimationResource(PlayerMonkeyTail tail) {
      return ANIMATION;
   }
}
