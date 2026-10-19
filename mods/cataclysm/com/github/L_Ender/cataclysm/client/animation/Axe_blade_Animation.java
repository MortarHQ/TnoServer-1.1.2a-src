package com.github.L_Ender.cataclysm.client.animation;

import com.github.L_Ender.lionfishapi.client.model.AdvancedAnimations.AdvancedAnimationChannel;
import com.github.L_Ender.lionfishapi.client.model.AdvancedAnimations.AdvancedAnimationDefinition;
import com.github.L_Ender.lionfishapi.client.model.AdvancedAnimations.AdvancedKeyframe;
import com.github.L_Ender.lionfishapi.client.model.AdvancedAnimations.AdvancedKeyframeAnimations;
import com.github.L_Ender.lionfishapi.client.model.AdvancedAnimations.AdvancedAnimationChannel.Interpolations;
import com.github.L_Ender.lionfishapi.client.model.AdvancedAnimations.AdvancedAnimationChannel.Targets;
import com.github.L_Ender.lionfishapi.client.model.AdvancedAnimations.AdvancedAnimationDefinition.Builder;

public class Axe_blade_Animation {
   public static final AdvancedAnimationDefinition IDLE = Builder.withLength(0.5F)
      .looping()
      .addAnimation(
         "blade",
         new AdvancedAnimationChannel(
            Targets.POSITION, new AdvancedKeyframe[]{new AdvancedKeyframe(0.0F, AdvancedKeyframeAnimations.posVec(0.2762F, 0.0F, 0.0F), Interpolations.LINEAR)}
         )
      )
      .addAnimation(
         "blade",
         new AdvancedAnimationChannel(
            Targets.SCALE, new AdvancedKeyframe[]{new AdvancedKeyframe(0.0F, AdvancedKeyframeAnimations.scaleVec(1.0, 1.0, 1.0102F), Interpolations.LINEAR)}
         )
      )
      .addAnimation(
         "vfx",
         new AdvancedAnimationChannel(
            Targets.SCALE, new AdvancedKeyframe[]{new AdvancedKeyframe(0.0F, AdvancedKeyframeAnimations.scaleVec(1.0, 1.0, 1.2628F), Interpolations.LINEAR)}
         )
      )
      .build();
}
