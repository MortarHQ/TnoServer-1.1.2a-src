package net.witherspawnanimation.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.witherspawnanimation.entity.AnimatedAltarBygoneMinXEntity;
import software.bernie.geckolib.model.GeoModel;

public class AnimatedAltarBygoneMinXModel extends GeoModel<AnimatedAltarBygoneMinXEntity> {
   public ResourceLocation getAnimationResource(AnimatedAltarBygoneMinXEntity entity) {
      return ResourceLocation.parse("wither_spawn_animation:animations/animated_altarminx.animation.json");
   }

   public ResourceLocation getModelResource(AnimatedAltarBygoneMinXEntity entity) {
      return ResourceLocation.parse("wither_spawn_animation:geo/animated_altarminx.geo.json");
   }

   public ResourceLocation getTextureResource(AnimatedAltarBygoneMinXEntity entity) {
      return ResourceLocation.parse("wither_spawn_animation:textures/entities/" + entity.getTexture() + ".png");
   }
}
