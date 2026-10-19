package net.mcreator.rpgstylemorebosses.entity.model;

import net.mcreator.rpgstylemorebosses.entity.BeaconActiveEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class BeaconActiveModel extends GeoModel<BeaconActiveEntity> {
   public ResourceLocation getAnimationResource(BeaconActiveEntity entity) {
      return ResourceLocation.parse("rpg_style_more_bosses:animations/beacon.animation.json");
   }

   public ResourceLocation getModelResource(BeaconActiveEntity entity) {
      return ResourceLocation.parse("rpg_style_more_bosses:geo/beacon.geo.json");
   }

   public ResourceLocation getTextureResource(BeaconActiveEntity entity) {
      return ResourceLocation.parse("rpg_style_more_bosses:textures/entities/" + entity.getTexture() + ".png");
   }
}
