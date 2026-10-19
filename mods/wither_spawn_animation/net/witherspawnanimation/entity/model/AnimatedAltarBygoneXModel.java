package net.witherspawnanimation.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.witherspawnanimation.entity.AnimatedAltarBygoneXEntity;
import software.bernie.geckolib.model.GeoModel;

public class AnimatedAltarBygoneXModel extends GeoModel<AnimatedAltarBygoneXEntity> {
   public ResourceLocation getAnimationResource(AnimatedAltarBygoneXEntity entity) {
      return ResourceLocation.parse("wither_spawn_animation:animations/animated_altarx.animation.json");
   }

   public ResourceLocation getModelResource(AnimatedAltarBygoneXEntity entity) {
      return ResourceLocation.parse("wither_spawn_animation:geo/animated_altarx.geo.json");
   }

   public ResourceLocation getTextureResource(AnimatedAltarBygoneXEntity entity) {
      return ResourceLocation.parse("wither_spawn_animation:textures/entities/" + entity.getTexture() + ".png");
   }
}
