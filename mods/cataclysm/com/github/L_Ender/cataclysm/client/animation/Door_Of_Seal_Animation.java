package com.github.L_Ender.cataclysm.client.animation;

import com.github.L_Ender.lionfishapi.client.model.AdvancedAnimations.AdvancedAnimationChannel;
import com.github.L_Ender.lionfishapi.client.model.AdvancedAnimations.AdvancedAnimationDefinition;
import com.github.L_Ender.lionfishapi.client.model.AdvancedAnimations.AdvancedKeyframe;
import com.github.L_Ender.lionfishapi.client.model.AdvancedAnimations.AdvancedKeyframeAnimations;
import com.github.L_Ender.lionfishapi.client.model.AdvancedAnimations.AdvancedAnimationChannel.Interpolations;
import com.github.L_Ender.lionfishapi.client.model.AdvancedAnimations.AdvancedAnimationChannel.Targets;
import com.github.L_Ender.lionfishapi.client.model.AdvancedAnimations.AdvancedAnimationDefinition.Builder;

public class Door_Of_Seal_Animation {
   public static final AdvancedAnimationDefinition OPEN = Builder.withLength(7.25F)
      .addAnimation(
         "left_door",
         new AdvancedAnimationChannel(
            Targets.ROTATION,
            new AdvancedKeyframe[]{
               new AdvancedKeyframe(0.0F, AdvancedKeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new AdvancedKeyframe(1.75F, AdvancedKeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new AdvancedKeyframe(2.5F, AdvancedKeyframeAnimations.degreeVec(0.0F, 5.0F, 0.0F), Interpolations.CATMULLROM),
               new AdvancedKeyframe(5.25F, AdvancedKeyframeAnimations.degreeVec(0.0F, 90.0F, 0.0F), Interpolations.CATMULLROM),
               new AdvancedKeyframe(6.0F, AdvancedKeyframeAnimations.degreeVec(0.0F, 95.0F, 0.0F), Interpolations.CATMULLROM),
               new AdvancedKeyframe(6.875F, AdvancedKeyframeAnimations.degreeVec(0.0F, 95.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "right_door",
         new AdvancedAnimationChannel(
            Targets.ROTATION,
            new AdvancedKeyframe[]{
               new AdvancedKeyframe(0.0F, AdvancedKeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new AdvancedKeyframe(1.75F, AdvancedKeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new AdvancedKeyframe(2.5F, AdvancedKeyframeAnimations.degreeVec(0.0F, -5.0F, 0.0F), Interpolations.CATMULLROM),
               new AdvancedKeyframe(5.25F, AdvancedKeyframeAnimations.degreeVec(0.0F, -90.0F, 0.0F), Interpolations.CATMULLROM),
               new AdvancedKeyframe(6.0F, AdvancedKeyframeAnimations.degreeVec(0.0F, -95.0F, 0.0F), Interpolations.CATMULLROM),
               new AdvancedKeyframe(6.875F, AdvancedKeyframeAnimations.degreeVec(0.0F, -95.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "lock",
         new AdvancedAnimationChannel(
            Targets.ROTATION,
            new AdvancedKeyframe[]{
               new AdvancedKeyframe(0.0F, AdvancedKeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new AdvancedKeyframe(0.1667F, AdvancedKeyframeAnimations.degreeVec(0.0F, 0.0F, -2.5F), Interpolations.CATMULLROM),
               new AdvancedKeyframe(0.3333F, AdvancedKeyframeAnimations.degreeVec(0.3898F, 0.3406F, 4.9743F), Interpolations.CATMULLROM),
               new AdvancedKeyframe(0.5F, AdvancedKeyframeAnimations.degreeVec(0.0F, 0.0F, -2.5F), Interpolations.CATMULLROM),
               new AdvancedKeyframe(0.6667F, AdvancedKeyframeAnimations.degreeVec(0.3898F, 0.3406F, 4.9743F), Interpolations.CATMULLROM),
               new AdvancedKeyframe(0.8333F, AdvancedKeyframeAnimations.degreeVec(0.0F, 0.0F, -2.5F), Interpolations.CATMULLROM),
               new AdvancedKeyframe(0.9167F, AdvancedKeyframeAnimations.degreeVec(0.3898F, 0.3406F, 4.9743F), Interpolations.CATMULLROM),
               new AdvancedKeyframe(1.0F, AdvancedKeyframeAnimations.degreeVec(0.0F, 0.0F, -2.5F), Interpolations.CATMULLROM),
               new AdvancedKeyframe(1.0833F, AdvancedKeyframeAnimations.degreeVec(0.3898F, 0.3406F, 4.9743F), Interpolations.CATMULLROM),
               new AdvancedKeyframe(1.1667F, AdvancedKeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new AdvancedKeyframe(1.2083F, AdvancedKeyframeAnimations.degreeVec(0.3898F, 0.3406F, 4.9743F), Interpolations.CATMULLROM),
               new AdvancedKeyframe(1.25F, AdvancedKeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new AdvancedKeyframe(1.2917F, AdvancedKeyframeAnimations.degreeVec(0.3898F, 0.3406F, 4.9743F), Interpolations.CATMULLROM),
               new AdvancedKeyframe(1.3333F, AdvancedKeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new AdvancedKeyframe(1.375F, AdvancedKeyframeAnimations.degreeVec(0.3898F, 0.3406F, 4.9743F), Interpolations.CATMULLROM),
               new AdvancedKeyframe(1.4167F, AdvancedKeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new AdvancedKeyframe(1.4583F, AdvancedKeyframeAnimations.degreeVec(0.3898F, 0.3406F, 4.9743F), Interpolations.CATMULLROM),
               new AdvancedKeyframe(1.5F, AdvancedKeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "lock",
         new AdvancedAnimationChannel(
            Targets.POSITION,
            new AdvancedKeyframe[]{
               new AdvancedKeyframe(0.0F, AdvancedKeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new AdvancedKeyframe(0.1667F, AdvancedKeyframeAnimations.posVec(0.0F, -1.0F, 0.0F), Interpolations.CATMULLROM),
               new AdvancedKeyframe(0.4583F, AdvancedKeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new AdvancedKeyframe(0.625F, AdvancedKeyframeAnimations.posVec(-1.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new AdvancedKeyframe(0.875F, AdvancedKeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new AdvancedKeyframe(1.1667F, AdvancedKeyframeAnimations.posVec(0.0F, -1.0F, 0.0F), Interpolations.CATMULLROM),
               new AdvancedKeyframe(1.5F, AdvancedKeyframeAnimations.posVec(0.0F, -1.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "lock",
         new AdvancedAnimationChannel(
            Targets.SCALE,
            new AdvancedKeyframe[]{
               new AdvancedKeyframe(0.0F, AdvancedKeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR),
               new AdvancedKeyframe(1.499F, AdvancedKeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR),
               new AdvancedKeyframe(1.5F, AdvancedKeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR),
               new AdvancedKeyframe(1.5823F, AdvancedKeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR),
               new AdvancedKeyframe(1.5833F, AdvancedKeyframeAnimations.scaleVec(0.0, 0.0, 0.0), Interpolations.LINEAR)
            }
         )
      )
      .build();
   public static final AdvancedAnimationDefinition OPEN_IDLE = Builder.withLength(0.0F)
      .looping()
      .addAnimation(
         "left_door",
         new AdvancedAnimationChannel(
            Targets.ROTATION,
            new AdvancedKeyframe[]{new AdvancedKeyframe(0.0F, AdvancedKeyframeAnimations.degreeVec(0.0F, 95.0F, 0.0F), Interpolations.CATMULLROM)}
         )
      )
      .addAnimation(
         "right_door",
         new AdvancedAnimationChannel(
            Targets.ROTATION,
            new AdvancedKeyframe[]{new AdvancedKeyframe(0.0F, AdvancedKeyframeAnimations.degreeVec(0.0F, -95.0F, 0.0F), Interpolations.CATMULLROM)}
         )
      )
      .addAnimation(
         "lock",
         new AdvancedAnimationChannel(
            Targets.SCALE, new AdvancedKeyframe[]{new AdvancedKeyframe(0.0F, AdvancedKeyframeAnimations.scaleVec(0.0, 0.0, 0.0), Interpolations.LINEAR)}
         )
      )
      .build();
}
