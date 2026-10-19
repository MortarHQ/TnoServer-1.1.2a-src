package com.benji.netherman.client.model;

import com.benji.netherman.NetherExp;
import com.benji.netherman.common.entity.BelieverEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class BelieverModel extends GeoModel<BelieverEntity> {
   private static final ResourceLocation MODEL = NetherExp.location("geo/believer.geo.json");
   private static final ResourceLocation ANIMATION = NetherExp.location("animations/believer.animation.json");
   private static final ResourceLocation TEX_NORMAL = NetherExp.location("textures/entity/believer.png");
   private static final ResourceLocation TEX_PRAY = NetherExp.location("textures/entity/believer_pray.png");

   public ResourceLocation getModelResource(BelieverEntity animatable) {
      return MODEL;
   }

   public ResourceLocation getTextureResource(BelieverEntity animatable) {
      return animatable.clientIsProtected ? TEX_PRAY : TEX_NORMAL;
   }

   public ResourceLocation getAnimationResource(BelieverEntity animatable) {
      return ANIMATION;
   }
}
