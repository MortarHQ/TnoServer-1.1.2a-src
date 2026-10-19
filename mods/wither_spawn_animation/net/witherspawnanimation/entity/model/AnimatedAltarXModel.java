package net.witherspawnanimation.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.witherspawnanimation.entity.AnimatedAltarXEntity;
import software.bernie.geckolib.model.GeoModel;

public class AnimatedAltarXModel extends GeoModel<AnimatedAltarXEntity> {
   public ResourceLocation getAnimationResource(AnimatedAltarXEntity entity) {
      return ResourceLocation.parse("wither_spawn_animation:animations/animated_altarx.animation.json");
   }

   public ResourceLocation getModelResource(AnimatedAltarXEntity entity) {
      return ResourceLocation.parse("wither_spawn_animation:geo/animated_altarx.geo.json");
   }

   public ResourceLocation getTextureResource(AnimatedAltarXEntity entity) {
      return ResourceLocation.parse("wither_spawn_animation:textures/entities/" + entity.getTexture() + ".png");
   }
}
