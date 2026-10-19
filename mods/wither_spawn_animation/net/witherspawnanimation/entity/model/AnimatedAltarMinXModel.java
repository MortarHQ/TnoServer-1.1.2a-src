package net.witherspawnanimation.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.witherspawnanimation.entity.AnimatedAltarMinXEntity;
import software.bernie.geckolib.model.GeoModel;

public class AnimatedAltarMinXModel extends GeoModel<AnimatedAltarMinXEntity> {
   public ResourceLocation getAnimationResource(AnimatedAltarMinXEntity entity) {
      return ResourceLocation.parse("wither_spawn_animation:animations/animated_altarminx.animation.json");
   }

   public ResourceLocation getModelResource(AnimatedAltarMinXEntity entity) {
      return ResourceLocation.parse("wither_spawn_animation:geo/animated_altarminx.geo.json");
   }

   public ResourceLocation getTextureResource(AnimatedAltarMinXEntity entity) {
      return ResourceLocation.parse("wither_spawn_animation:textures/entities/" + entity.getTexture() + ".png");
   }
}
