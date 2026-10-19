package io.github.Memoires.mysticism.client.model;

import io.github.Memoires.mysticism.entity.projectile.ShadowBallProjectile;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class ShadowBallProjectileModel extends GeoModel<ShadowBallProjectile> {
   public ResourceLocation getModelResource(ShadowBallProjectile shadowBallProjectile) {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "geo/shadow_ball.geo.json");
   }

   public ResourceLocation getTextureResource(ShadowBallProjectile shadowBallProjectile) {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "textures/entity/projectiles/shadow_ball.png");
   }

   public ResourceLocation getAnimationResource(ShadowBallProjectile shadowBallProjectile) {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "animations/shadow_ball.animation.json");
   }
}
