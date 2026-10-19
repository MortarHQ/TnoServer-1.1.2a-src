package com.bobmowzie.mowziesmobs.client.model.tools.geckolib;

import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.TestOnly;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animation.Animation;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.Animation.LoopType;
import software.bernie.geckolib.animation.AnimationController.AnimationStateHandler;
import software.bernie.geckolib.animation.AnimationController.State;
import software.bernie.geckolib.animation.AnimationProcessor.QueuedAnimation;
import software.bernie.geckolib.model.GeoModel;

public class MowzieAnimationController<T extends GeoAnimatable> extends AnimationController<T> {
   private double timingOffset;

   public MowzieAnimationController(T animatable, String name, int transitionLength, AnimationStateHandler<T> animationHandler, double timingOffset) {
      super(animatable, name, transitionLength, animationHandler);
      this.timingOffset = timingOffset;
   }

   public void playAnimation(T animatable, RawAnimation animation) {
      this.forceAnimationReset();
      this.setAnimation(animation);
      this.currentAnimation = (QueuedAnimation)this.animationQueue.poll();
      this.isJustStarting = true;
      this.adjustTick(animatable.getTick(animatable) + Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(false));
      this.transitionLength = 0.0;
   }

   protected double adjustTick(double tick) {
      if (this.shouldResetTick) {
         if (this.getAnimationState() == State.TRANSITIONING) {
            this.tickOffset = tick;
         } else if (this.getAnimationState() != State.STOPPED) {
            this.tickOffset = this.tickOffset + this.transitionLength;
         }

         this.shouldResetTick = false;
      }

      double adjustedTick = (Double)this.animationSpeedModifier.apply(this.animatable) * Math.max(tick - this.tickOffset, 0.0) + this.timingOffset;
      if (this.currentAnimation != null && this.currentAnimation.loopType() == LoopType.LOOP) {
         adjustedTick %= this.currentAnimation.animation().length();
      }

      if (adjustedTick == this.timingOffset) {
         this.isJustStarting = true;
      }

      return adjustedTick;
   }

   public void setLastModel(GeoModel<T> coreGeoModel) {
      this.lastModel = coreGeoModel;
   }

   @TestOnly
   public <E extends GeoEntity> void checkAndReloadAnims() {
      if (this.lastModel != null && this.getCurrentAnimation() != null && this.getCurrentAnimation().animation() != null) {
         String currentAnimationName = this.getCurrentAnimation().animation().name();
         Animation animation = this.lastModel.getAnimation(this.animatable, currentAnimationName);
         if (!animation.equals(this.getCurrentAnimation().animation())) {
            this.forceAnimationReset();
            this.currentAnimation = (QueuedAnimation)this.animationQueue.poll();
            this.isJustStarting = true;
            this.adjustTick(this.animatable.getTick(this.animatable) + Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(false));
            this.transitionLength = 0.0;
         }
      }
   }
}
