package net.witherspawnanimation.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.witherspawnanimation.entity.AnimatedAltarStormSoilXEntity;
import software.bernie.geckolib.model.GeoModel;

public class AnimatedAltarStormSoilXModel extends GeoModel<AnimatedAltarStormSoilXEntity> {
   public ResourceLocation getAnimationResource(AnimatedAltarStormSoilXEntity entity) {
      return ResourceLocation.parse("wither_spawn_animation:animations/animated_altar_stromx.animation.json");
   }

   public ResourceLocation getModelResource(AnimatedAltarStormSoilXEntity entity) {
      return ResourceLocation.parse("wither_spawn_animation:geo/animated_altar_stromx.geo.json");
   }

   public ResourceLocation getTextureResource(AnimatedAltarStormSoilXEntity entity) {
      return ResourceLocation.parse("wither_spawn_animation:textures/entities/" + entity.getTexture() + ".png");
   }
}
