package com.benji.netherman.client.model;

import com.benji.netherman.NetherExp;
import com.benji.netherman.common.entity.AzazelEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class AzazelModel extends GeoModel<AzazelEntity> {
   private static final ResourceLocation MODEL_NORMAL = NetherExp.location("geo/azazel.geo.json");
   private static final ResourceLocation MODEL_LOW_HP = NetherExp.location("geo/azazel_lowhp.geo.json");
   private static final ResourceLocation TEX_GOLD = NetherExp.location("textures/entity/azazel_gold.png");
   private static final ResourceLocation TEX_PRAY = NetherExp.location("textures/entity/azazel_pray.png");
   private static final ResourceLocation TEX_NORMAL = NetherExp.location("textures/entity/azazel.png");
   private static final ResourceLocation TEX_DAMAGED = NetherExp.location("textures/entity/azazel_damaged.png");

   public ResourceLocation getModelResource(AzazelEntity animatable) {
      return animatable.getEntityData().get(AzazelEntity.PHASE_STATE) == 2 ? MODEL_LOW_HP : MODEL_NORMAL;
   }

   public ResourceLocation getTextureResource(AzazelEntity animatable) {
      if ((Integer)animatable.getEntityData().get(AzazelEntity.ATTACK_STATE) == 13) {
         return TEX_GOLD;
      }

      if (!(Boolean)animatable.getEntityData().get(AzazelEntity.IS_AGGRO)) {
         return TEX_PRAY;
      }

      int phase = (Integer)animatable.getEntityData().get(AzazelEntity.PHASE_STATE);
      return phase >= 1 ? TEX_DAMAGED : TEX_NORMAL;
   }

   public ResourceLocation getAnimationResource(AzazelEntity animatable) {
      return NetherExp.location("animations/azazel.animation.json");
   }
}
