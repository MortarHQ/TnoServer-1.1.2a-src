package io.github.Memoires.mysticism.client.model;

import io.github.Memoires.mysticism.entity.projectile.TailedBeastBombProjectile;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class TailedBeastBombProjectileModel extends GeoModel<TailedBeastBombProjectile> {
   public ResourceLocation getModelResource(TailedBeastBombProjectile tailedBeastBombProjectile) {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "geo/tailed_beast_bomb.geo.json");
   }

   public ResourceLocation getTextureResource(TailedBeastBombProjectile tailedBeastBombProjectile) {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "textures/entity/projectiles/tailed_beast_bomb.png");
   }

   public ResourceLocation getAnimationResource(TailedBeastBombProjectile tailedBeastBombProjectile) {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "animations/tailed_beast_bomb.animation.json");
   }
}
