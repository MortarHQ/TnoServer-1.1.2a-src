package net.mcreator.rpgstylemorebosses.entity.model;

import net.mcreator.rpgstylemorebosses.entity.TheExoframeVFX2Entity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class TheExoframeVFX2Model extends GeoModel<TheExoframeVFX2Entity> {
   public ResourceLocation getAnimationResource(TheExoframeVFX2Entity entity) {
      return ResourceLocation.parse("rpg_style_more_bosses:animations/vfx2.animation.json");
   }

   public ResourceLocation getModelResource(TheExoframeVFX2Entity entity) {
      return ResourceLocation.parse("rpg_style_more_bosses:geo/vfx2.geo.json");
   }

   public ResourceLocation getTextureResource(TheExoframeVFX2Entity entity) {
      return ResourceLocation.parse("rpg_style_more_bosses:textures/entities/" + entity.getTexture() + ".png");
   }
}
