package com.benji.netherman.client.model;

import com.benji.netherman.NetherExp;
import com.benji.netherman.common.entity.LaserEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class LaserModel extends GeoModel<LaserEntity> {
   public ResourceLocation getModelResource(LaserEntity animatable) {
      return NetherExp.location("geo/laser.geo.json");
   }

   public ResourceLocation getTextureResource(LaserEntity animatable) {
      return NetherExp.location("textures/entity/laser.png");
   }

   public ResourceLocation getAnimationResource(LaserEntity animatable) {
      return NetherExp.location("animations/laser.animation.json");
   }
}
