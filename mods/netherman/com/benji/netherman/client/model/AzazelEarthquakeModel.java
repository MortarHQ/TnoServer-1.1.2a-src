package com.benji.netherman.client.model;

import com.benji.netherman.NetherExp;
import com.benji.netherman.common.entity.AzazelEarthquakeEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class AzazelEarthquakeModel extends GeoModel<AzazelEarthquakeEntity> {
   public ResourceLocation getModelResource(AzazelEarthquakeEntity animatable) {
      return NetherExp.location("geo/empty.geo.json");
   }

   public ResourceLocation getTextureResource(AzazelEarthquakeEntity animatable) {
      return NetherExp.location("textures/entity/empty.png");
   }

   public ResourceLocation getAnimationResource(AzazelEarthquakeEntity animatable) {
      return NetherExp.location("animations/empty.animation.json");
   }
}
