package net.witherspawnanimation.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.witherspawnanimation.entity.AnimatedAltarStormMinZEntity;
import software.bernie.geckolib.model.GeoModel;

public class AnimatedAltarStormMinZModel extends GeoModel<AnimatedAltarStormMinZEntity> {
   public ResourceLocation getAnimationResource(AnimatedAltarStormMinZEntity entity) {
      return ResourceLocation.parse("wither_spawn_animation:animations/animated_altar_stromminz.animation.json");
   }

   public ResourceLocation getModelResource(AnimatedAltarStormMinZEntity entity) {
      return ResourceLocation.parse("wither_spawn_animation:geo/animated_altar_stromminz.geo.json");
   }

   public ResourceLocation getTextureResource(AnimatedAltarStormMinZEntity entity) {
      return ResourceLocation.parse("wither_spawn_animation:textures/entities/" + entity.getTexture() + ".png");
   }
}
