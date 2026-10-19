package com.benji.netherman.client.model;

import com.benji.netherman.NetherExp;
import com.benji.netherman.common.entity.BellGuardianEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class BellGuardianModel extends GeoModel<BellGuardianEntity> {
   public ResourceLocation getModelResource(BellGuardianEntity animatable) {
      return NetherExp.location("geo/bell_guardian.geo.json");
   }

   public ResourceLocation getTextureResource(BellGuardianEntity animatable) {
      return NetherExp.location("textures/entity/bell_guardian.png");
   }

   public ResourceLocation getAnimationResource(BellGuardianEntity animatable) {
      return NetherExp.location("animations/bell_guardian.animation.json");
   }
}
