package net.mcreator.rpgstylemorebosses.entity.model;

import net.mcreator.rpgstylemorebosses.entity.BeaconEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class BeaconModel extends GeoModel<BeaconEntity> {
   public ResourceLocation getAnimationResource(BeaconEntity entity) {
      return ResourceLocation.parse("rpg_style_more_bosses:animations/beacon.animation.json");
   }

   public ResourceLocation getModelResource(BeaconEntity entity) {
      return ResourceLocation.parse("rpg_style_more_bosses:geo/beacon.geo.json");
   }

   public ResourceLocation getTextureResource(BeaconEntity entity) {
      return ResourceLocation.parse("rpg_style_more_bosses:textures/entities/" + entity.getTexture() + ".png");
   }
}
