package com.benji.netherman.client.model;

import com.benji.netherman.NetherExp;
import com.benji.netherman.common.entity.TraderEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class TraderModel extends GeoModel<TraderEntity> {
   private static final ResourceLocation MODEL = NetherExp.location("geo/trader.geo.json");
   private static final ResourceLocation TEXTURE = NetherExp.location("textures/entity/trader.png");
   private static final ResourceLocation ANIMATION = NetherExp.location("animations/trader.animation.json");

   public ResourceLocation getModelResource(TraderEntity animatable) {
      return MODEL;
   }

   public ResourceLocation getTextureResource(TraderEntity animatable) {
      return TEXTURE;
   }

   public ResourceLocation getAnimationResource(TraderEntity animatable) {
      return ANIMATION;
   }
}
