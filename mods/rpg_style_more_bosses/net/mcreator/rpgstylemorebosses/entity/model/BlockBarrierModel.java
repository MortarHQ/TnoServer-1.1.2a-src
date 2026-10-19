package net.mcreator.rpgstylemorebosses.entity.model;

import net.mcreator.rpgstylemorebosses.entity.BlockBarrierEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class BlockBarrierModel extends GeoModel<BlockBarrierEntity> {
   public ResourceLocation getAnimationResource(BlockBarrierEntity entity) {
      return ResourceLocation.parse("rpg_style_more_bosses:animations/beacon.animation.json");
   }

   public ResourceLocation getModelResource(BlockBarrierEntity entity) {
      return ResourceLocation.parse("rpg_style_more_bosses:geo/beacon.geo.json");
   }

   public ResourceLocation getTextureResource(BlockBarrierEntity entity) {
      return ResourceLocation.parse("rpg_style_more_bosses:textures/entities/" + entity.getTexture() + ".png");
   }
}
