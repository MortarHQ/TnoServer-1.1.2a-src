package net.witherspawnanimation.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.witherspawnanimation.entity.AnimatedAltarBygoneMinZEntity;
import software.bernie.geckolib.model.GeoModel;

public class AnimatedAltarBygoneMinZModel extends GeoModel<AnimatedAltarBygoneMinZEntity> {
   public ResourceLocation getAnimationResource(AnimatedAltarBygoneMinZEntity entity) {
      return ResourceLocation.parse("wither_spawn_animation:animations/animated_altarminz.animation.json");
   }

   public ResourceLocation getModelResource(AnimatedAltarBygoneMinZEntity entity) {
      return ResourceLocation.parse("wither_spawn_animation:geo/animated_altarminz.geo.json");
   }

   public ResourceLocation getTextureResource(AnimatedAltarBygoneMinZEntity entity) {
      return ResourceLocation.parse("wither_spawn_animation:textures/entities/" + entity.getTexture() + ".png");
   }
}
