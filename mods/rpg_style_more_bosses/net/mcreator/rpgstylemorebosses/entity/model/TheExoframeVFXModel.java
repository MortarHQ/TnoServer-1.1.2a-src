package net.mcreator.rpgstylemorebosses.entity.model;

import net.mcreator.rpgstylemorebosses.entity.TheExoframeVFXEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class TheExoframeVFXModel extends GeoModel<TheExoframeVFXEntity> {
   public ResourceLocation getAnimationResource(TheExoframeVFXEntity entity) {
      return ResourceLocation.parse("rpg_style_more_bosses:animations/vfx1.animation.json");
   }

   public ResourceLocation getModelResource(TheExoframeVFXEntity entity) {
      return ResourceLocation.parse("rpg_style_more_bosses:geo/vfx1.geo.json");
   }

   public ResourceLocation getTextureResource(TheExoframeVFXEntity entity) {
      return ResourceLocation.parse("rpg_style_more_bosses:textures/entities/" + entity.getTexture() + ".png");
   }
}
