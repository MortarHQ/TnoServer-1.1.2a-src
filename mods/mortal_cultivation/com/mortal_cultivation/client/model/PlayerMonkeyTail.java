package com.mortal_cultivation.client.model;

import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.geckolib.util.RenderUtil;

public final class PlayerMonkeyTail implements GeoAnimatable {
   private static final RawAnimation IDLE = RawAnimation.begin().thenLoop("animation.monkey.idle");
   private static final RawAnimation WALK = RawAnimation.begin().thenLoop("animation.monkey.walk");
   private static final RawAnimation RUN = RawAnimation.begin().thenLoop("animation.monkey.run");
   private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
   private final long instanceId;
   private RawAnimation animation = IDLE;

   public PlayerMonkeyTail(long instanceId) {
      this.instanceId = instanceId;
   }

   public long getInstanceId() {
      return this.instanceId;
   }

   public void updateMovement(boolean moving, boolean sprinting) {
      this.animation = moving ? (sprinting ? RUN : WALK) : IDLE;
   }

   public void registerControllers(ControllerRegistrar controllers) {
      controllers.add(new AnimationController(this, "tail", 5, state -> state.setAndContinue(this.animation)));
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.cache;
   }

   public double getTick(Object object) {
      return RenderUtil.getCurrentTick();
   }
}
