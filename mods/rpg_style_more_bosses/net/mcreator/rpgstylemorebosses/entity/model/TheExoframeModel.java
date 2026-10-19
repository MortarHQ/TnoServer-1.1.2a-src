package net.mcreator.rpgstylemorebosses.entity.model;

import net.mcreator.rpgstylemorebosses.entity.TheExoframeEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class TheExoframeModel extends GeoModel<TheExoframeEntity> {
   public ResourceLocation getAnimationResource(TheExoframeEntity entity) {
      return ResourceLocation.parse("rpg_style_more_bosses:animations/theexoframe.animation.json");
   }

   public ResourceLocation getModelResource(TheExoframeEntity entity) {
      return ResourceLocation.parse("rpg_style_more_bosses:geo/theexoframe.geo.json");
   }

   public ResourceLocation getTextureResource(TheExoframeEntity entity) {
      return ResourceLocation.parse("rpg_style_more_bosses:textures/entities/" + entity.getTexture() + ".png");
   }
}
