package io.github.Memoires.mysticism.client.model;

import io.github.Memoires.mysticism.entity.projectile.SentryMissileProjectile;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class SentryMissileModel extends GeoModel<SentryMissileProjectile> {
   public ResourceLocation getModelResource(SentryMissileProjectile animatable) {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "geo/missile.geo.json");
   }

   public ResourceLocation getTextureResource(SentryMissileProjectile animatable) {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "textures/entity/skill/missile.png");
   }

   public ResourceLocation getAnimationResource(SentryMissileProjectile animatable) {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "animations/sentry/sentry_2.anim.json");
   }
}
