package com.mortal_cultivation.client.model;

import com.mortal_cultivation.entity.TaoistArrayEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class TaoistArrayModel extends GeoModel<TaoistArrayEntity> {
   public ResourceLocation getModelResource(TaoistArrayEntity entity) {
      return ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "geo/effects/taoist_array.geo.json");
   }

   public ResourceLocation getTextureResource(TaoistArrayEntity entity) {
      return ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "textures/effects/taoist_array/taoist_array.png");
   }

   public ResourceLocation getAnimationResource(TaoistArrayEntity entity) {
      return ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "animations/effects/taoist_array.animation.json");
   }
}
