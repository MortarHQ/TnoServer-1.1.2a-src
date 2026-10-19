package io.github.Memoires.mysticism.client.model;

import io.github.Memoires.mysticism.entity.monster.ShadowImpEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class ShadowImpEntityModel extends GeoModel<ShadowImpEntity> {
   public ResourceLocation getModelResource(ShadowImpEntity shadowImpEntity) {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "geo/shadow_imp.geo.json");
   }

   public ResourceLocation getTextureResource(ShadowImpEntity shadowImpEntity) {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "textures/entity/monster/shadow_imp.png");
   }

   public ResourceLocation getAnimationResource(ShadowImpEntity shadowImpEntity) {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "animations/shadow_imp.animation.json");
   }
}
