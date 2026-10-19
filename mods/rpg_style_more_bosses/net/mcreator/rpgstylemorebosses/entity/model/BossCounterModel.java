package net.mcreator.rpgstylemorebosses.entity.model;

import net.mcreator.rpgstylemorebosses.entity.BossCounterEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class BossCounterModel extends GeoModel<BossCounterEntity> {
   public ResourceLocation getAnimationResource(BossCounterEntity entity) {
      return ResourceLocation.parse("rpg_style_more_bosses:animations/beacon.animation.json");
   }

   public ResourceLocation getModelResource(BossCounterEntity entity) {
      return ResourceLocation.parse("rpg_style_more_bosses:geo/beacon.geo.json");
   }

   public ResourceLocation getTextureResource(BossCounterEntity entity) {
      return ResourceLocation.parse("rpg_style_more_bosses:textures/entities/" + entity.getTexture() + ".png");
   }
}
