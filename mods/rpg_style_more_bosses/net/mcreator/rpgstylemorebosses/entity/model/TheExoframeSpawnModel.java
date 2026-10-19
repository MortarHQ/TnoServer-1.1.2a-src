package net.mcreator.rpgstylemorebosses.entity.model;

import net.mcreator.rpgstylemorebosses.entity.TheExoframeSpawnEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class TheExoframeSpawnModel extends GeoModel<TheExoframeSpawnEntity> {
   public ResourceLocation getAnimationResource(TheExoframeSpawnEntity entity) {
      return ResourceLocation.parse("rpg_style_more_bosses:animations/theexoframe.animation.json");
   }

   public ResourceLocation getModelResource(TheExoframeSpawnEntity entity) {
      return ResourceLocation.parse("rpg_style_more_bosses:geo/theexoframe.geo.json");
   }

   public ResourceLocation getTextureResource(TheExoframeSpawnEntity entity) {
      return ResourceLocation.parse("rpg_style_more_bosses:textures/entities/" + entity.getTexture() + ".png");
   }
}
