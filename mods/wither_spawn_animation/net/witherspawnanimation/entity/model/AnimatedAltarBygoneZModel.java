package net.witherspawnanimation.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.witherspawnanimation.entity.AnimatedAltarBygoneZEntity;
import software.bernie.geckolib.model.GeoModel;

public class AnimatedAltarBygoneZModel extends GeoModel<AnimatedAltarBygoneZEntity> {
   public ResourceLocation getAnimationResource(AnimatedAltarBygoneZEntity entity) {
      return ResourceLocation.parse("wither_spawn_animation:animations/animated_altarz.animation.json");
   }

   public ResourceLocation getModelResource(AnimatedAltarBygoneZEntity entity) {
      return ResourceLocation.parse("wither_spawn_animation:geo/animated_altarz.geo.json");
   }

   public ResourceLocation getTextureResource(AnimatedAltarBygoneZEntity entity) {
      return ResourceLocation.parse("wither_spawn_animation:textures/entities/" + entity.getTexture() + ".png");
   }
}
