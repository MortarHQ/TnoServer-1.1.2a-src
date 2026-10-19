package io.github.Memoires.mysticism.client.model;

import io.github.Memoires.mysticism.entity.projectile.DraconicMeteorProjectile;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class DraconicMeteorModel extends GeoModel<DraconicMeteorProjectile> {
   public ResourceLocation getModelResource(DraconicMeteorProjectile animatable) {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "geo/draconic_meteor.geo.json");
   }

   public ResourceLocation getTextureResource(DraconicMeteorProjectile animatable) {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "textures/entity/skill/draconic_meteor.png");
   }

   public ResourceLocation getAnimationResource(DraconicMeteorProjectile animatable) {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "animations/draconic_meteor.animation.json");
   }
}
