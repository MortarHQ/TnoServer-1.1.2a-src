package com.cerbon.bosses_of_mass_destruction.entity.custom.gauntlet;

import com.cerbon.bosses_of_mass_destruction.entity.util.BaseEntity;
import com.cerbon.bosses_of_mass_destruction.entity.util.IEntityEventHandler;
import java.util.Map;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.animation.AnimationController.AnimationStateHandler;

public class AnimationHolder implements IEntityEventHandler {
   private final BaseEntity entity;
   private final Map<Byte, AnimationHolder.Animation> animationStatusFlags;
   private final Byte stopAttackByte;
   private final int transition;
   private AnimationHolder.Animation nextAnimation;
   private boolean doIdleAnimation = true;
   private final AnimationStateHandler<BaseEntity> attack = animationState -> {
      AnimationHolder.Animation animationData = this.nextAnimation;
      this.nextAnimation = null;
      if (animationData != null) {
         animationState.getController().forceAnimationReset();
         animationState.setAnimation(RawAnimation.begin().thenPlay(animationData.animationName).thenLoop(animationData.idleAnimationName));
      }

      if (this.doIdleAnimation) {
         animationState.setAnimation(RawAnimation.begin().thenLoop("idle"));
      }

      return PlayState.CONTINUE;
   };

   public AnimationHolder(BaseEntity entity, Map<Byte, AnimationHolder.Animation> animationStatusFlags, Byte stopAttackByte, int transition) {
      this.entity = entity;
      this.animationStatusFlags = animationStatusFlags;
      this.stopAttackByte = stopAttackByte;
      this.transition = transition;
   }

   public void registerControllers(ControllerRegistrar data) {
      data.add(new AnimationController(this.entity, this.transition, this.attack));
   }

   @Override
   public void handleEntityEvent(byte status) {
      if (this.animationStatusFlags.containsKey(status)) {
         this.doIdleAnimation = false;
         this.nextAnimation = this.animationStatusFlags.get(status);
      }

      if (status == this.stopAttackByte) {
         this.doIdleAnimation = true;
      }
   }

   public record Animation(String animationName, String idleAnimationName) {
   }
}
