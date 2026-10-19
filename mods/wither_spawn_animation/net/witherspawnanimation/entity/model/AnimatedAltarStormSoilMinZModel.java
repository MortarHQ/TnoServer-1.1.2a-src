package net.witherspawnanimation.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.witherspawnanimation.entity.AnimatedAltarStormSoilMinZEntity;
import software.bernie.geckolib.model.GeoModel;

public class AnimatedAltarStormSoilMinZModel extends GeoModel<AnimatedAltarStormSoilMinZEntity> {
   public ResourceLocation getAnimationResource(AnimatedAltarStormSoilMinZEntity entity) {
      return ResourceLocation.parse("wither_spawn_animation:animations/animated_altar_stromminz.animation.json");
   }

   public ResourceLocation getModelResource(AnimatedAltarStormSoilMinZEntity entity) {
      return ResourceLocation.parse("wither_spawn_animation:geo/animated_altar_stromminz.geo.json");
   }

   public ResourceLocation getTextureResource(AnimatedAltarStormSoilMinZEntity entity) {
      return ResourceLocation.parse("wither_spawn_animation:textures/entities/" + entity.getTexture() + ".png");
   }
}
