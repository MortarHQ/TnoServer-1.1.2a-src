package io.github.Memoires.mysticism.client.model;

import io.github.Memoires.mysticism.entity.skill.EnkiduEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class EnkiduEntityModel extends GeoModel<EnkiduEntity> {
   public ResourceLocation getModelResource(EnkiduEntity enkiduEntity) {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "geo/enkidu_chain.geo.json");
   }

   public ResourceLocation getTextureResource(EnkiduEntity enkiduEntity) {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "textures/entity/enkidu_chain.png");
   }

   public ResourceLocation getAnimationResource(EnkiduEntity enkiduEntity) {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "animations/enkidu.animation.json");
   }
}
