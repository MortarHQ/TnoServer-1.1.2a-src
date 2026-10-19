package com.benji.netherman.client.model;

import com.benji.netherman.NetherExp;
import com.benji.netherman.common.entity.BlacksmithEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class BlacksmithModel extends GeoModel<BlacksmithEntity> {
   public ResourceLocation getModelResource(BlacksmithEntity animatable) {
      return NetherExp.location("geo/blacksmith.geo.json");
   }

   public ResourceLocation getTextureResource(BlacksmithEntity animatable) {
      return NetherExp.location("textures/entity/blacksmith.png");
   }

   public ResourceLocation getAnimationResource(BlacksmithEntity animatable) {
      return NetherExp.location("animations/blacksmith.animation.json");
   }
}
