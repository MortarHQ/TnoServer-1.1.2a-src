package com.github.b4ndithelps.tennogamenolife.client.model;

import com.github.b4ndithelps.tennogamenolife.entity.projectile.BlackRodProjectile;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public final class BlackRodModel extends GeoModel<BlackRodProjectile> {
   private static final ResourceLocation MODEL = ResourceLocation.fromNamespaceAndPath("tennogamenolife", "geo/black_rod.geo.json");
   private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("tennogamenolife", "textures/entity/projectile/black_rod.png");
   private static final ResourceLocation ANIMATION = ResourceLocation.fromNamespaceAndPath("tennogamenolife", "animations/black_rod.animation.json");

   public ResourceLocation getModelResource(BlackRodProjectile animatable) {
      return MODEL;
   }

   public ResourceLocation getTextureResource(BlackRodProjectile animatable) {
      return TEXTURE;
   }

   public ResourceLocation getAnimationResource(BlackRodProjectile animatable) {
      return ANIMATION;
   }
}
