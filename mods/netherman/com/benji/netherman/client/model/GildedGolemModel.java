package com.benji.netherman.client.model;

import com.benji.netherman.NetherExp;
import com.benji.netherman.common.entity.GildedGolemEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class GildedGolemModel extends GeoModel<GildedGolemEntity> {
   private static final ResourceLocation MODEL_RESOURCE = NetherExp.location("geo/gilded_golem.geo.json");
   private static final ResourceLocation ANIMATION_RESOURCE = NetherExp.location("animations/gilded_golem.animation.json");
   private static final ResourceLocation TEX_NORMAL = NetherExp.location("textures/entity/gilded_golem.png");
   private static final ResourceLocation TEX_DAMAGED = NetherExp.location("textures/entity/gilded_golem_damaged.png");
   private static final ResourceLocation TEX_HEAL = NetherExp.location("textures/entity/gilded_golem_damaged_heal.png");

   public ResourceLocation getModelResource(GildedGolemEntity object) {
      return MODEL_RESOURCE;
   }

   public ResourceLocation getTextureResource(GildedGolemEntity object) {
      int state = (Integer)object.getEntityData().get(GildedGolemEntity.TEXTURE_STATE);
      if (state == 2) {
         return TEX_HEAL;
      } else {
         return state == 1 ? TEX_DAMAGED : TEX_NORMAL;
      }
   }

   public ResourceLocation getAnimationResource(GildedGolemEntity animatable) {
      return ANIMATION_RESOURCE;
   }
}
