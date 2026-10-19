package com.cerbon.bosses_of_mass_destruction.util;

import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimationController.AnimationStateHandler;

public class AnimationUtils {
   public static <T extends GeoAnimatable> AnimationStateHandler<T> createIdlePredicate(String animationName) {
      return animationState -> {
         animationState.getController().setAnimation(RawAnimation.begin().thenLoop(animationName));
         return PlayState.CONTINUE;
      };
   }
}
