package com.benji.netherman.client.model;

import com.benji.netherman.NetherExp;
import com.benji.netherman.common.entity.BelieverVillagerEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class BelieverVillagerModel extends GeoModel<BelieverVillagerEntity> {
   public ResourceLocation getModelResource(BelieverVillagerEntity animatable) {
      return NetherExp.location("geo/believer_villager.geo.json");
   }

   public ResourceLocation getTextureResource(BelieverVillagerEntity animatable) {
      return NetherExp.location("textures/entity/believer_villager.png");
   }

   public ResourceLocation getAnimationResource(BelieverVillagerEntity animatable) {
      return NetherExp.location("animations/believer_villager.animation.json");
   }
}
