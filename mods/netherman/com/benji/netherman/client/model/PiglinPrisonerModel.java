package com.benji.netherman.client.model;

import com.benji.netherman.NetherExp;
import com.benji.netherman.common.entity.PiglinPrisonerEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class PiglinPrisonerModel extends GeoModel<PiglinPrisonerEntity> {
   public ResourceLocation getModelResource(PiglinPrisonerEntity animatable) {
      return NetherExp.location("geo/piglin_prisoner.geo.json");
   }

   public ResourceLocation getTextureResource(PiglinPrisonerEntity animatable) {
      return NetherExp.location("textures/entity/piglin_prisoner.png");
   }

   public ResourceLocation getAnimationResource(PiglinPrisonerEntity animatable) {
      return NetherExp.location("animations/piglin_prisoner.animation.json");
   }
}
