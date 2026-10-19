package net.mcreator.rpgstylemorebosses.entity.model;

import net.mcreator.rpgstylemorebosses.entity.ExoframeTickEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class ExoframeTickModel extends GeoModel<ExoframeTickEntity> {
   public ResourceLocation getAnimationResource(ExoframeTickEntity entity) {
      return ResourceLocation.parse("rpg_style_more_bosses:animations/exoframetick.animation.json");
   }

   public ResourceLocation getModelResource(ExoframeTickEntity entity) {
      return ResourceLocation.parse("rpg_style_more_bosses:geo/exoframetick.geo.json");
   }

   public ResourceLocation getTextureResource(ExoframeTickEntity entity) {
      return ResourceLocation.parse("rpg_style_more_bosses:textures/entities/" + entity.getTexture() + ".png");
   }
}
