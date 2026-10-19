package net.witherspawnanimation.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.witherspawnanimation.entity.AnimatedAltarStormZEntity;
import software.bernie.geckolib.model.GeoModel;

public class AnimatedAltarStormZModel extends GeoModel<AnimatedAltarStormZEntity> {
   public ResourceLocation getAnimationResource(AnimatedAltarStormZEntity entity) {
      return ResourceLocation.parse("wither_spawn_animation:animations/animated_altar_stromz.animation.json");
   }

   public ResourceLocation getModelResource(AnimatedAltarStormZEntity entity) {
      return ResourceLocation.parse("wither_spawn_animation:geo/animated_altar_stromz.geo.json");
   }

   public ResourceLocation getTextureResource(AnimatedAltarStormZEntity entity) {
      return ResourceLocation.parse("wither_spawn_animation:textures/entities/" + entity.getTexture() + ".png");
   }
}
