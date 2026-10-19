package net.witherspawnanimation.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.witherspawnanimation.entity.AnimatedAltarStormXEntity;
import software.bernie.geckolib.model.GeoModel;

public class AnimatedAltarStormXModel extends GeoModel<AnimatedAltarStormXEntity> {
   public ResourceLocation getAnimationResource(AnimatedAltarStormXEntity entity) {
      return ResourceLocation.parse("wither_spawn_animation:animations/animated_altar_stromx.animation.json");
   }

   public ResourceLocation getModelResource(AnimatedAltarStormXEntity entity) {
      return ResourceLocation.parse("wither_spawn_animation:geo/animated_altar_stromx.geo.json");
   }

   public ResourceLocation getTextureResource(AnimatedAltarStormXEntity entity) {
      return ResourceLocation.parse("wither_spawn_animation:textures/entities/" + entity.getTexture() + ".png");
   }
}
