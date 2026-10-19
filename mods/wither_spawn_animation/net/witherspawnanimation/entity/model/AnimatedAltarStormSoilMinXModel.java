package net.witherspawnanimation.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.witherspawnanimation.entity.AnimatedAltarStormSoilMinXEntity;
import software.bernie.geckolib.model.GeoModel;

public class AnimatedAltarStormSoilMinXModel extends GeoModel<AnimatedAltarStormSoilMinXEntity> {
   public ResourceLocation getAnimationResource(AnimatedAltarStormSoilMinXEntity entity) {
      return ResourceLocation.parse("wither_spawn_animation:animations/animated_altar_stromminx.animation.json");
   }

   public ResourceLocation getModelResource(AnimatedAltarStormSoilMinXEntity entity) {
      return ResourceLocation.parse("wither_spawn_animation:geo/animated_altar_stromminx.geo.json");
   }

   public ResourceLocation getTextureResource(AnimatedAltarStormSoilMinXEntity entity) {
      return ResourceLocation.parse("wither_spawn_animation:textures/entities/" + entity.getTexture() + ".png");
   }
}
