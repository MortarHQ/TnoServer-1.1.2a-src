package net.witherspawnanimation.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.witherspawnanimation.entity.AnimatedAltarSoilMinXEntity;
import software.bernie.geckolib.model.GeoModel;

public class AnimatedAltarSoilMinXModel extends GeoModel<AnimatedAltarSoilMinXEntity> {
   public ResourceLocation getAnimationResource(AnimatedAltarSoilMinXEntity entity) {
      return ResourceLocation.parse("wither_spawn_animation:animations/animated_altarminx.animation.json");
   }

   public ResourceLocation getModelResource(AnimatedAltarSoilMinXEntity entity) {
      return ResourceLocation.parse("wither_spawn_animation:geo/animated_altarminx.geo.json");
   }

   public ResourceLocation getTextureResource(AnimatedAltarSoilMinXEntity entity) {
      return ResourceLocation.parse("wither_spawn_animation:textures/entities/" + entity.getTexture() + ".png");
   }
}
