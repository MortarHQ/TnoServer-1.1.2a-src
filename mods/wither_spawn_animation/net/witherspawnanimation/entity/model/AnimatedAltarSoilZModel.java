package net.witherspawnanimation.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.witherspawnanimation.entity.AnimatedAltarSoilZEntity;
import software.bernie.geckolib.model.GeoModel;

public class AnimatedAltarSoilZModel extends GeoModel<AnimatedAltarSoilZEntity> {
   public ResourceLocation getAnimationResource(AnimatedAltarSoilZEntity entity) {
      return ResourceLocation.parse("wither_spawn_animation:animations/animated_altarz.animation.json");
   }

   public ResourceLocation getModelResource(AnimatedAltarSoilZEntity entity) {
      return ResourceLocation.parse("wither_spawn_animation:geo/animated_altarz.geo.json");
   }

   public ResourceLocation getTextureResource(AnimatedAltarSoilZEntity entity) {
      return ResourceLocation.parse("wither_spawn_animation:textures/entities/" + entity.getTexture() + ".png");
   }
}
