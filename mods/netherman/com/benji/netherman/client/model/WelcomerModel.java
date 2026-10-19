package com.benji.netherman.client.model;

import com.benji.netherman.NetherExp;
import com.benji.netherman.common.entity.WelcomerEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class WelcomerModel extends GeoModel<WelcomerEntity> {
   private static final ResourceLocation MODEL = NetherExp.location("geo/welcomer.geo.json");
   private static final ResourceLocation TEXTURE = NetherExp.location("textures/entity/welcomer.png");
   private static final ResourceLocation ANIMATION = NetherExp.location("animations/welcomer.animation.json");

   public ResourceLocation getModelResource(WelcomerEntity animatable) {
      return MODEL;
   }

   public ResourceLocation getTextureResource(WelcomerEntity animatable) {
      return TEXTURE;
   }

   public ResourceLocation getAnimationResource(WelcomerEntity animatable) {
      return ANIMATION;
   }
}
