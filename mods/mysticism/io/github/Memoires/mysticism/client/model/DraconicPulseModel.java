package io.github.Memoires.mysticism.client.model;

import io.github.Memoires.mysticism.entity.projectile.DraconicPulseProjectile;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class DraconicPulseModel extends GeoModel<DraconicPulseProjectile> {
   public ResourceLocation getModelResource(DraconicPulseProjectile animatable) {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "geo/draconic_pulse.geo.json");
   }

   public ResourceLocation getTextureResource(DraconicPulseProjectile animatable) {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "textures/entity/skill/draconic_pulse.png");
   }

   public ResourceLocation getAnimationResource(DraconicPulseProjectile animatable) {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "animations/draconic_pulse.animation.json");
   }
}
