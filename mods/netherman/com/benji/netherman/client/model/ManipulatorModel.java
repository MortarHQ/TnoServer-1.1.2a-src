package com.benji.netherman.client.model;

import com.benji.netherman.NetherExp;
import com.benji.netherman.common.entity.ManipulatorEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class ManipulatorModel extends GeoModel<ManipulatorEntity> {
   public ResourceLocation getModelResource(ManipulatorEntity animatable) {
      return switch (animatable.getHealthPhase()) {
         case 1 -> NetherExp.location("geo/manipulator_onehp.geo.json");
         case 2 -> NetherExp.location("geo/manipulator_zerohp.geo.json");
         default -> NetherExp.location("geo/manipulator.geo.json");
      };
   }

   public ResourceLocation getTextureResource(ManipulatorEntity animatable) {
      return NetherExp.location("textures/entity/manipulator.png");
   }

   public ResourceLocation getAnimationResource(ManipulatorEntity animatable) {
      return NetherExp.location("animations/manipulator.animation.json");
   }
}
