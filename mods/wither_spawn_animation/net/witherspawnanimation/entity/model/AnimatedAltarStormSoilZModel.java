package net.witherspawnanimation.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.witherspawnanimation.entity.AnimatedAltarStormSoilZEntity;
import software.bernie.geckolib.model.GeoModel;

public class AnimatedAltarStormSoilZModel extends GeoModel<AnimatedAltarStormSoilZEntity> {
   public ResourceLocation getAnimationResource(AnimatedAltarStormSoilZEntity entity) {
      return ResourceLocation.parse("wither_spawn_animation:animations/animated_altar_stromz.animation.json");
   }

   public ResourceLocation getModelResource(AnimatedAltarStormSoilZEntity entity) {
      return ResourceLocation.parse("wither_spawn_animation:geo/animated_altar_stromz.geo.json");
   }

   public ResourceLocation getTextureResource(AnimatedAltarStormSoilZEntity entity) {
      return ResourceLocation.parse("wither_spawn_animation:textures/entities/" + entity.getTexture() + ".png");
   }
}
