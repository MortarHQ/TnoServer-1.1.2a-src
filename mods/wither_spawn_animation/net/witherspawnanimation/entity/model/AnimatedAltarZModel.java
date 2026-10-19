package net.witherspawnanimation.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.witherspawnanimation.entity.AnimatedAltarZEntity;
import software.bernie.geckolib.model.GeoModel;

public class AnimatedAltarZModel extends GeoModel<AnimatedAltarZEntity> {
   public ResourceLocation getAnimationResource(AnimatedAltarZEntity entity) {
      return ResourceLocation.parse("wither_spawn_animation:animations/animated_altarz.animation.json");
   }

   public ResourceLocation getModelResource(AnimatedAltarZEntity entity) {
      return ResourceLocation.parse("wither_spawn_animation:geo/animated_altarz.geo.json");
   }

   public ResourceLocation getTextureResource(AnimatedAltarZEntity entity) {
      return ResourceLocation.parse("wither_spawn_animation:textures/entities/" + entity.getTexture() + ".png");
   }
}
