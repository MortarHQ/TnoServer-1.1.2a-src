package com.benji.netherman.client.model;

import com.benji.netherman.NetherExp;
import com.benji.netherman.common.entity.StatueEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class StatueModel extends GeoModel<StatueEntity> {
   private static final ResourceLocation MODEL = NetherExp.location("geo/statue_entity.geo.json");
   private static final ResourceLocation TEXTURE = NetherExp.location("textures/entity/statue_entity.png");
   private static final ResourceLocation ANIMATION = NetherExp.location("animations/statue_entity.animation.json");

   public ResourceLocation getModelResource(StatueEntity animatable) {
      return MODEL;
   }

   public ResourceLocation getTextureResource(StatueEntity animatable) {
      return TEXTURE;
   }

   public ResourceLocation getAnimationResource(StatueEntity animatable) {
      return ANIMATION;
   }
}
