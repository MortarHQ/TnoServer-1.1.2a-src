package io.github.Memoires.mysticism.client.model;

import io.github.Memoires.mysticism.entity.skill.ProviderHaloEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class ProviderHaloModel extends GeoModel<ProviderHaloEntity> {
   public ResourceLocation getModelResource(ProviderHaloEntity animatable) {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "geo/provider_halo.geo.json");
   }

   public ResourceLocation getTextureResource(ProviderHaloEntity animatable) {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "textures/entity/skill/provider_halo.png");
   }

   public ResourceLocation getAnimationResource(ProviderHaloEntity animatable) {
      return null;
   }
}
