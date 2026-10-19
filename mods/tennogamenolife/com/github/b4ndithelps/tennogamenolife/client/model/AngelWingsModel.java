package com.github.b4ndithelps.tennogamenolife.client.model;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;

public final class AngelWingsModel extends GeoModel<AngelWings> {
   private static final float RIGHT_CHARGE_ROTATION = (float)Math.toRadians(-37.5);
   private static final float LEFT_CHARGE_ROTATION = (float)Math.toRadians(37.5);
   private static final ResourceLocation MODEL = ResourceLocation.fromNamespaceAndPath("tennogamenolife", "geo/angel_wings.geo.json");
   private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("tennogamenolife", "textures/entity/angel_wings.png");
   private static final ResourceLocation ANIMATION = ResourceLocation.fromNamespaceAndPath("tennogamenolife", "animations/angel_wings.animation.json");

   public ResourceLocation getModelResource(AngelWings animatable) {
      return MODEL;
   }

   public ResourceLocation getTextureResource(AngelWings animatable) {
      return TEXTURE;
   }

   public ResourceLocation getAnimationResource(AngelWings animatable) {
      return ANIMATION;
   }

   public void setCustomAnimations(AngelWings animatable, long instanceId, AnimationState<AngelWings> animationState) {
      super.setCustomAnimations(animatable, instanceId, animationState);
      float progress = animatable.getGodlySmiteChargeProgress();
      if (!(progress <= 0.0F)) {
         this.getBone("wing_right_root").ifPresent(bone -> bone.setRotZ(bone.getRotZ() + RIGHT_CHARGE_ROTATION * progress));
         this.getBone("wing_left_root").ifPresent(bone -> bone.setRotZ(bone.getRotZ() + LEFT_CHARGE_ROTATION * progress));
      }
   }
}
