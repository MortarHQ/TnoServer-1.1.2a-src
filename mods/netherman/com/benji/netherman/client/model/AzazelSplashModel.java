package com.benji.netherman.client.model;

import com.benji.netherman.NetherExp;
import com.benji.netherman.common.entity.AzazelSplashEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class AzazelSplashModel extends GeoModel<AzazelSplashEntity> {
   public ResourceLocation getModelResource(AzazelSplashEntity animatable) {
      return NetherExp.location("geo/splash.geo.json");
   }

   public ResourceLocation getTextureResource(AzazelSplashEntity animatable) {
      return NetherExp.location("textures/entity/splash.png");
   }

   public ResourceLocation getAnimationResource(AzazelSplashEntity animatable) {
      return NetherExp.location("animations/empty.animation.json");
   }
}
