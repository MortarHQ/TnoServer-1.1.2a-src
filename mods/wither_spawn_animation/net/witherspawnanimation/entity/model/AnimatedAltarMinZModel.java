package net.witherspawnanimation.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.witherspawnanimation.entity.AnimatedAltarMinZEntity;
import software.bernie.geckolib.model.GeoModel;

public class AnimatedAltarMinZModel extends GeoModel<AnimatedAltarMinZEntity> {
   public ResourceLocation getAnimationResource(AnimatedAltarMinZEntity entity) {
      return ResourceLocation.parse("wither_spawn_animation:animations/animated_altarminz.animation.json");
   }

   public ResourceLocation getModelResource(AnimatedAltarMinZEntity entity) {
      return ResourceLocation.parse("wither_spawn_animation:geo/animated_altarminz.geo.json");
   }

   public ResourceLocation getTextureResource(AnimatedAltarMinZEntity entity) {
      return ResourceLocation.parse("wither_spawn_animation:textures/entities/" + entity.getTexture() + ".png");
   }
}
