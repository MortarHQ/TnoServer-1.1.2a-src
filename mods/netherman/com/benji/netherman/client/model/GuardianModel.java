package com.benji.netherman.client.model;

import com.benji.netherman.NetherExp;
import com.benji.netherman.common.entity.GuardianEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class GuardianModel extends GeoModel<GuardianEntity> {
   private static final ResourceLocation MODEL = NetherExp.location("geo/guardian.geo.json");
   private static final ResourceLocation TEXTURE = NetherExp.location("textures/entity/guardian.png");
   private static final ResourceLocation ANIMATION = NetherExp.location("animations/guardian.animation.json");

   public ResourceLocation getModelResource(GuardianEntity animatable) {
      return MODEL;
   }

   public ResourceLocation getTextureResource(GuardianEntity animatable) {
      return TEXTURE;
   }

   public ResourceLocation getAnimationResource(GuardianEntity animatable) {
      return ANIMATION;
   }
}
