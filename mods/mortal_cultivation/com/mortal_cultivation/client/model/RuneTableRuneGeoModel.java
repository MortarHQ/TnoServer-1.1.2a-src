package com.mortal_cultivation.client.model;

import com.mortal_cultivation.block.entity.RuneTableBlockEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public final class RuneTableRuneGeoModel extends GeoModel<RuneTableBlockEntity> {
   public ResourceLocation getModelResource(RuneTableBlockEntity animatable) {
      return ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "geo/block/ancient_stele_runes.geo.json");
   }

   public ResourceLocation getTextureResource(RuneTableBlockEntity animatable) {
      return ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "textures/block/ancient_stele_runes.png");
   }

   public ResourceLocation getAnimationResource(RuneTableBlockEntity animatable) {
      return ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "animations/block/ancient_stele_runes.animation.json");
   }
}
