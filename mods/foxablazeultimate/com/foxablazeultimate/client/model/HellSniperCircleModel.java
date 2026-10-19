package com.foxablazeultimate.client.model;

import com.foxablazeultimate.entity.HellSniperCircleEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.GeoModel;

public final class HellSniperCircleModel extends GeoModel<HellSniperCircleEntity> {
   private static final float LOOP_TICKS = 80.0F;
   private static final float FU1_FULL_TURN_TICKS = 55.0F;
   private static final float FU2_FULL_TURN_TICKS = 45.0F;
   private static final float FU1_SCALE_TICKS = 7.5F;
   private static final float FU2_SCALE_TICKS = 13.334F;
   private static final ResourceLocation MODEL = ResourceLocation.fromNamespaceAndPath("foxablazeultimate", "geo/hell_sniper.geo.json");
   private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("foxablazeultimate", "textures/entity/hell_sniper_magic_circle.png");
   private static final ResourceLocation ANIMATION = ResourceLocation.fromNamespaceAndPath("foxablazeultimate", "animations/hell_sniper.animation.json");

   public ResourceLocation getModelResource(HellSniperCircleEntity animatable) {
      return MODEL;
   }

   public ResourceLocation getTextureResource(HellSniperCircleEntity animatable) {
      return TEXTURE;
   }

   public ResourceLocation getAnimationResource(HellSniperCircleEntity animatable) {
      return ANIMATION;
   }

   public RenderType getRenderType(HellSniperCircleEntity animatable, ResourceLocation texture) {
      return RenderType.entityTranslucentEmissive(texture);
   }

   public void setCustomAnimations(HellSniperCircleEntity circle, long instanceId, AnimationState<HellSniperCircleEntity> animationState) {
      float globalAge = circle.getAnimationAge(animationState.getPartialTick());
      float appearanceDelay = circle.getAppearanceDelayTicks();
      GeoBone star = this.getAnimationProcessor().getBone("xing");
      GeoBone middle = this.getAnimationProcessor().getBone("zhong jian fa zheng");
      GeoBone secondary = this.getAnimationProcessor().getBone("fu fa zheng");
      GeoBone secondaryTwo = this.getAnimationProcessor().getBone("fu_fa_zheng2");
      if (star != null && middle != null && secondary != null && secondaryTwo != null) {
         if (globalAge < appearanceDelay) {
            hide(star, middle, secondary, secondaryTwo);
         } else {
            float age = globalAge - appearanceDelay;
            float appearanceTicks = Math.max(1.0F, circle.getAppearanceDurationTicks());
            float aimingTicks = Math.max(1.0F, circle.getAimingDurationTicks());
            if (age < appearanceTicks) {
               float progress = Mth.clamp(age / 2.5F, 0.0F, 1.0F);
               scale(star, progress, progress, progress);
               scale(middle, progress, progress, 1.0F);
               scale(secondary, 0.0F, 0.0F, 0.0F);
               scale(secondaryTwo, 0.0F, 0.0F, 0.0F);
               rotateZ(middle, 0.0F);
               rotateZ(secondary, 0.0F);
               rotateZ(secondaryTwo, 0.0F);
            } else if (circle.isInfiniteSniping() && globalAge < circle.getFormationAppearanceTicks()) {
               scale(star, 1.0F, 1.0F, 1.0F);
               scale(middle, 1.0F, 1.0F, 1.0F);
               scale(secondary, 0.0F, 0.0F, 0.0F);
               scale(secondaryTwo, 0.0F, 0.0F, 0.0F);
               rotateZ(middle, 0.0F);
               rotateZ(secondary, 0.0F);
               rotateZ(secondaryTwo, 0.0F);
            } else {
               scale(star, 1.0F, 1.0F, 1.0F);
               scale(middle, 1.0F, 1.0F, 1.0F);
               float local = circle.isInfiniteSniping() ? globalAge - circle.getFormationAppearanceTicks() : age - appearanceTicks;
               if (local < aimingTicks) {
                  rotateZ(middle, loopRotation(local));
                  scale(secondary, 0.0F, 0.9F, 1.0F);
                  scale(secondaryTwo, 0.0F, 1.0F, 1.0F);
                  rotateZ(secondary, 0.0F);
                  rotateZ(secondaryTwo, 0.0F);
               } else {
                  float firingAge = local - aimingTicks;
                  rotateZ(middle, loopRotation(firingAge));
                  float fu1Scale = Mth.clamp(firingAge / 7.5F, 0.0F, 1.0F) * 0.6F;
                  float fu2Scale = Mth.clamp(firingAge / 13.334F, 0.0F, 1.0F) * 0.4F;
                  scale(secondary, fu1Scale, fu1Scale, 1.0F);
                  scale(secondaryTwo, fu2Scale, fu2Scale, 1.0F);
                  rotateZ(secondary, secondaryRotation(firingAge, 55.0F));
                  rotateZ(secondaryTwo, secondaryRotation(firingAge, 45.0F));
               }
            }
         }
      }
   }

   private static float loopRotation(float ticks) {
      float wrapped = Mth.positiveModulo(ticks, 80.0F);
      return -360.0F * wrapped / 80.0F;
   }

   private static float secondaryRotation(float ticks, float firstTurnTicks) {
      float wrapped = Mth.positiveModulo(ticks, 80.0F);
      return wrapped <= firstTurnTicks ? -360.0F * wrapped / firstTurnTicks : -360.0F - 360.0F * (wrapped - firstTurnTicks) / (80.0F - firstTurnTicks);
   }

   private static void scale(GeoBone bone, float x, float y, float z) {
      bone.updateScale(x, y, z);
   }

   private static void rotateZ(GeoBone bone, float degrees) {
      bone.setRotZ(degrees * (float) (Math.PI / 180.0));
   }

   private static void hide(GeoBone star, GeoBone middle, GeoBone secondary, GeoBone secondaryTwo) {
      scale(star, 0.0F, 0.0F, 0.0F);
      scale(middle, 0.0F, 0.0F, 1.0F);
      scale(secondary, 0.0F, 0.0F, 0.0F);
      scale(secondaryTwo, 0.0F, 0.0F, 0.0F);
      rotateZ(middle, 0.0F);
      rotateZ(secondary, 0.0F);
      rotateZ(secondaryTwo, 0.0F);
   }
}
