package com.github.b4ndithelps.tennogamenolife.client.model;

import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.geckolib.util.RenderUtil;

public final class AngelWings implements GeoAnimatable {
   private static final RawAnimation IDLE = RawAnimation.begin().thenLoop("animation.angel_wings.idle");
   private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
   private final long instanceId;
   private float godlySmiteChargeProgress;

   public AngelWings(long instanceId) {
      this.instanceId = instanceId;
   }

   public long getInstanceId() {
      return this.instanceId;
   }

   public void setGodlySmiteChargeProgress(float progress) {
      this.godlySmiteChargeProgress = progress;
   }

   public float getGodlySmiteChargeProgress() {
      return this.godlySmiteChargeProgress;
   }

   public void registerControllers(ControllerRegistrar controllers) {
      controllers.add(new AnimationController(this, "main", 0, state -> {
         state.getController().setAnimation(IDLE);
         return PlayState.CONTINUE;
      }));
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.cache;
   }

   public double getTick(Object object) {
      return RenderUtil.getCurrentTick();
   }
}
