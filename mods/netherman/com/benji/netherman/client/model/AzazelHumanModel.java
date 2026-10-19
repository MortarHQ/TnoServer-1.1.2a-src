package com.benji.netherman.client.model;

import com.benji.netherman.NetherExp;
import com.benji.netherman.common.entity.AzazelHumanEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class AzazelHumanModel extends GeoModel<AzazelHumanEntity> {
   public ResourceLocation getModelResource(AzazelHumanEntity animatable) {
      return animatable.getEntityData().get(AzazelHumanEntity.IS_PHASE_2)
         ? NetherExp.location("geo/azazel_human_lowhp.geo.json")
         : NetherExp.location("geo/azazel_human.geo.json");
   }

   public ResourceLocation getTextureResource(AzazelHumanEntity animatable) {
      return NetherExp.location("textures/entity/azazel_human.png");
   }

   public ResourceLocation getAnimationResource(AzazelHumanEntity animatable) {
      return NetherExp.location("animations/azazel_human.animation.json");
   }
}
