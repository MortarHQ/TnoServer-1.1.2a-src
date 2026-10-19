package net.mcreator.rpgstylemorebosses.entity.model;

import net.mcreator.rpgstylemorebosses.entity.ExoframeBeamEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class ExoframeBeamModel extends GeoModel<ExoframeBeamEntity> {
   public ResourceLocation getAnimationResource(ExoframeBeamEntity entity) {
      return ResourceLocation.parse("rpg_style_more_bosses:animations/beamattackvfx.animation.json");
   }

   public ResourceLocation getModelResource(ExoframeBeamEntity entity) {
      return ResourceLocation.parse("rpg_style_more_bosses:geo/beamattackvfx.geo.json");
   }

   public ResourceLocation getTextureResource(ExoframeBeamEntity entity) {
      return ResourceLocation.parse("rpg_style_more_bosses:textures/entities/" + entity.getTexture() + ".png");
   }
}
