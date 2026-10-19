package net.witherspawnanimation.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.witherspawnanimation.entity.AnimatedAltarSoilXEntity;
import software.bernie.geckolib.model.GeoModel;

public class AnimatedAltarSoilXModel extends GeoModel<AnimatedAltarSoilXEntity> {
   public ResourceLocation getAnimationResource(AnimatedAltarSoilXEntity entity) {
      return ResourceLocation.parse("wither_spawn_animation:animations/animated_altarx.animation.json");
   }

   public ResourceLocation getModelResource(AnimatedAltarSoilXEntity entity) {
      return ResourceLocation.parse("wither_spawn_animation:geo/animated_altarx.geo.json");
   }

   public ResourceLocation getTextureResource(AnimatedAltarSoilXEntity entity) {
      return ResourceLocation.parse("wither_spawn_animation:textures/entities/" + entity.getTexture() + ".png");
   }
}
