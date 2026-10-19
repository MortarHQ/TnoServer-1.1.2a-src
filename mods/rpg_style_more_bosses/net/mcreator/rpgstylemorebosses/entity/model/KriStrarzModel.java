package net.mcreator.rpgstylemorebosses.entity.model;

import net.mcreator.rpgstylemorebosses.entity.KriStrarzEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class KriStrarzModel extends GeoModel<KriStrarzEntity> {
   public ResourceLocation getAnimationResource(KriStrarzEntity entity) {
      return ResourceLocation.parse("rpg_style_more_bosses:animations/kristrarz.animation.json");
   }

   public ResourceLocation getModelResource(KriStrarzEntity entity) {
      return ResourceLocation.parse("rpg_style_more_bosses:geo/kristrarz.geo.json");
   }

   public ResourceLocation getTextureResource(KriStrarzEntity entity) {
      return ResourceLocation.parse("rpg_style_more_bosses:textures/entities/" + entity.getTexture() + ".png");
   }
}
