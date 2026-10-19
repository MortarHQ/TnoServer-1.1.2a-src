package com.benji.netherman.client.model;

import com.benji.netherman.NetherExp;
import com.benji.netherman.common.entity.VillagerPrisonerEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class VillagerPrisonerModel extends GeoModel<VillagerPrisonerEntity> {
   public ResourceLocation getModelResource(VillagerPrisonerEntity animatable) {
      return NetherExp.location("geo/villager_prisoner.geo.json");
   }

   public ResourceLocation getTextureResource(VillagerPrisonerEntity animatable) {
      return NetherExp.location("textures/entity/villager_prisoner.png");
   }

   public ResourceLocation getAnimationResource(VillagerPrisonerEntity animatable) {
      return NetherExp.location("animations/villager_prisoner.animation.json");
   }
}
