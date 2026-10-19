package com.mortal_cultivation.client.model;

import com.mortal_cultivation.entity.SpiritMonkeyEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public final class SpiritMonkeyModel extends GeoModel<SpiritMonkeyEntity> {
   private static final ResourceLocation MODEL = ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "geo/spirit_monkey.geo.json");
   private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "textures/entity/spirit_monkey.png");
   private static final ResourceLocation ANIMATION = ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "animations/spirit_monkey.animation.json");

   public ResourceLocation getModelResource(SpiritMonkeyEntity animatable) {
      return MODEL;
   }

   public ResourceLocation getTextureResource(SpiritMonkeyEntity animatable) {
      return TEXTURE;
   }

   public ResourceLocation getAnimationResource(SpiritMonkeyEntity animatable) {
      return ANIMATION;
   }

   public void setCustomAnimations(SpiritMonkeyEntity monkey, long instanceId, AnimationState<SpiritMonkeyEntity> animationState) {
      if (!monkey.isSleeping() && !monkey.isEatingFruit() && !monkey.isTreeJumping() && !monkey.isHanging() && monkey.hurtTime <= 0 && !monkey.swinging) {
         for (AnimationController<?> controller : monkey.getAnimatableInstanceCache().getManagerForId(instanceId).getAnimationControllers().values()) {
            if (controller.isPlayingTriggeredAnimation()) {
               return;
            }
         }

         GeoBone head = this.getAnimationProcessor().getBone("head");
         EntityModelData modelData = (EntityModelData)animationState.getData(DataTickets.ENTITY_MODEL_DATA);
         if (head != null && modelData != null) {
            head.setRotX(head.getInitialSnapshot().getRotX() + Mth.clamp(modelData.headPitch(), -45.0F, 45.0F) * (float) (Math.PI / 180.0));
            head.setRotY(head.getInitialSnapshot().getRotY() + Mth.clamp(modelData.netHeadYaw(), -70.0F, 70.0F) * (float) (Math.PI / 180.0));
         }
      }
   }
}
