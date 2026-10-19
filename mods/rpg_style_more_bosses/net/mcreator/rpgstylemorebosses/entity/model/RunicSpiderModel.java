package net.mcreator.rpgstylemorebosses.entity.model;

import net.mcreator.rpgstylemorebosses.entity.RunicSpiderEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class RunicSpiderModel extends GeoModel<RunicSpiderEntity> {
   public ResourceLocation getAnimationResource(RunicSpiderEntity entity) {
      return ResourceLocation.parse("rpg_style_more_bosses:animations/runicspider.animation.json");
   }

   public ResourceLocation getModelResource(RunicSpiderEntity entity) {
      return ResourceLocation.parse("rpg_style_more_bosses:geo/runicspider.geo.json");
   }

   public ResourceLocation getTextureResource(RunicSpiderEntity entity) {
      return ResourceLocation.parse("rpg_style_more_bosses:textures/entities/" + entity.getTexture() + ".png");
   }
}
