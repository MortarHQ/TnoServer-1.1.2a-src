package net.witherspawnanimation.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.witherspawnanimation.entity.AnimatedAltarSoilMinZEntity;
import software.bernie.geckolib.model.GeoModel;

public class AnimatedAltarSoilMinZModel extends GeoModel<AnimatedAltarSoilMinZEntity> {
   public ResourceLocation getAnimationResource(AnimatedAltarSoilMinZEntity entity) {
      return ResourceLocation.parse("wither_spawn_animation:animations/animated_altarminz.animation.json");
   }

   public ResourceLocation getModelResource(AnimatedAltarSoilMinZEntity entity) {
      return ResourceLocation.parse("wither_spawn_animation:geo/animated_altarminz.geo.json");
   }

   public ResourceLocation getTextureResource(AnimatedAltarSoilMinZEntity entity) {
      return ResourceLocation.parse("wither_spawn_animation:textures/entities/" + entity.getTexture() + ".png");
   }
}
