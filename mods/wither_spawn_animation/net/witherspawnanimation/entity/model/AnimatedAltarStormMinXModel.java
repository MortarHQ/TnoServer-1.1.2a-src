package net.witherspawnanimation.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.witherspawnanimation.entity.AnimatedAltarStormMinXEntity;
import software.bernie.geckolib.model.GeoModel;

public class AnimatedAltarStormMinXModel extends GeoModel<AnimatedAltarStormMinXEntity> {
   public ResourceLocation getAnimationResource(AnimatedAltarStormMinXEntity entity) {
      return ResourceLocation.parse("wither_spawn_animation:animations/animated_altar_stromminx.animation.json");
   }

   public ResourceLocation getModelResource(AnimatedAltarStormMinXEntity entity) {
      return ResourceLocation.parse("wither_spawn_animation:geo/animated_altar_stromminx.geo.json");
   }

   public ResourceLocation getTextureResource(AnimatedAltarStormMinXEntity entity) {
      return ResourceLocation.parse("wither_spawn_animation:textures/entities/" + entity.getTexture() + ".png");
   }
}
