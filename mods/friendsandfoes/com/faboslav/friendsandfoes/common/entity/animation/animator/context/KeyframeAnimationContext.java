package com.faboslav.friendsandfoes.common.entity.animation.animator.context;

import com.faboslav.friendsandfoes.common.entity.animation.AnimationState;

public final class KeyframeAnimationContext {
   private AnimationState animationState;
   private int initialTick = 0;
   private int currentTick = 0;

   public KeyframeAnimationContext() {
      this.animationState = new AnimationState();
   }

   public void setInitialTick(int initialTick) {
      this.initialTick = initialTick;
   }

   public void setCurrentTick(int currentTick) {
      this.currentTick = currentTick;
   }

   public boolean isRunning() {
      return this.initialTick != 0;
   }

   public boolean isAtLastKeyframe(int totalTicks) {
      return this.initialTick + totalTicks > this.currentTick;
   }

   public void setAnimationState(AnimationState animationState) {
      this.animationState = animationState;
   }

   public AnimationState getAnimationState() {
      return this.animationState;
   }
}
