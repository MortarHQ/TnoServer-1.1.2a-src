package com.github.L_Ender.cataclysm.client.animation;

import com.github.L_Ender.lionfishapi.client.model.AdvancedAnimations.AdvancedAnimationChannel;
import com.github.L_Ender.lionfishapi.client.model.AdvancedAnimations.AdvancedAnimationDefinition;
import com.github.L_Ender.lionfishapi.client.model.AdvancedAnimations.AdvancedKeyframe;
import com.github.L_Ender.lionfishapi.client.model.AdvancedAnimations.AdvancedKeyframeAnimations;
import com.github.L_Ender.lionfishapi.client.model.AdvancedAnimations.AdvancedAnimationChannel.Interpolations;
import com.github.L_Ender.lionfishapi.client.model.AdvancedAnimations.AdvancedAnimationChannel.Targets;
import com.github.L_Ender.lionfishapi.client.model.AdvancedAnimations.AdvancedAnimationDefinition.Builder;

public class Boss_Respawn_Spawner_Animation {
   public static final AdvancedAnimationDefinition SPAWNING = Builder.withLength(0.9167F)
      .addAnimation(
         "one",
         new AdvancedAnimationChannel(
            Targets.ROTATION,
            new AdvancedKeyframe[]{
               new AdvancedKeyframe(0.2917F, AdvancedKeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new AdvancedKeyframe(1.0F, AdvancedKeyframeAnimations.degreeVec(-20.0F, -3.5F, -20.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "one",
         new AdvancedAnimationChannel(
            Targets.POSITION,
            new AdvancedKeyframe[]{
               new AdvancedKeyframe(0.2917F, AdvancedKeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new AdvancedKeyframe(0.9167F, AdvancedKeyframeAnimations.posVec(-0.25F, 0.0F, 0.25F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "two",
         new AdvancedAnimationChannel(
            Targets.ROTATION,
            new AdvancedKeyframe[]{
               new AdvancedKeyframe(0.2917F, AdvancedKeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new AdvancedKeyframe(1.0F, AdvancedKeyframeAnimations.degreeVec(-20.0F, 3.5F, 20.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "two",
         new AdvancedAnimationChannel(
            Targets.POSITION,
            new AdvancedKeyframe[]{
               new AdvancedKeyframe(0.2917F, AdvancedKeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new AdvancedKeyframe(0.9167F, AdvancedKeyframeAnimations.posVec(0.25F, 0.0F, 0.25F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "three",
         new AdvancedAnimationChannel(
            Targets.ROTATION,
            new AdvancedKeyframe[]{
               new AdvancedKeyframe(0.2917F, AdvancedKeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new AdvancedKeyframe(1.0F, AdvancedKeyframeAnimations.degreeVec(20.0F, -3.5F, 20.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "three",
         new AdvancedAnimationChannel(
            Targets.POSITION,
            new AdvancedKeyframe[]{
               new AdvancedKeyframe(0.2917F, AdvancedKeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new AdvancedKeyframe(0.9167F, AdvancedKeyframeAnimations.posVec(0.25F, 0.0F, -0.25F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "four",
         new AdvancedAnimationChannel(
            Targets.ROTATION,
            new AdvancedKeyframe[]{
               new AdvancedKeyframe(0.2917F, AdvancedKeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new AdvancedKeyframe(1.0F, AdvancedKeyframeAnimations.degreeVec(20.0F, 3.5F, -20.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "four",
         new AdvancedAnimationChannel(
            Targets.POSITION,
            new AdvancedKeyframe[]{
               new AdvancedKeyframe(0.2917F, AdvancedKeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new AdvancedKeyframe(0.9167F, AdvancedKeyframeAnimations.posVec(-0.25F, 0.0F, -0.25F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "bone",
         new AdvancedAnimationChannel(
            Targets.ROTATION,
            new AdvancedKeyframe[]{
               new AdvancedKeyframe(0.2917F, AdvancedKeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new AdvancedKeyframe(0.625F, AdvancedKeyframeAnimations.degreeVec(0.0F, 126.67F, 0.0F), Interpolations.CATMULLROM),
               new AdvancedKeyframe(0.9167F, AdvancedKeyframeAnimations.degreeVec(0.0F, 180.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "bone",
         new AdvancedAnimationChannel(
            Targets.POSITION,
            new AdvancedKeyframe[]{
               new AdvancedKeyframe(0.2917F, AdvancedKeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new AdvancedKeyframe(1.0F, AdvancedKeyframeAnimations.posVec(0.0F, 5.5F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "bone2",
         new AdvancedAnimationChannel(
            Targets.POSITION,
            new AdvancedKeyframe[]{
               new AdvancedKeyframe(0.0F, AdvancedKeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new AdvancedKeyframe(0.0833F, AdvancedKeyframeAnimations.posVec(0.0F, 0.0F, -2.05F), Interpolations.LINEAR),
               new AdvancedKeyframe(0.2083F, AdvancedKeyframeAnimations.posVec(0.0F, 0.0F, -3.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "bone3",
         new AdvancedAnimationChannel(
            Targets.POSITION,
            new AdvancedKeyframe[]{
               new AdvancedKeyframe(0.0F, AdvancedKeyframeAnimations.posVec(0.0F, 0.0F, 1.0F), Interpolations.LINEAR),
               new AdvancedKeyframe(0.0833F, AdvancedKeyframeAnimations.posVec(0.0F, 0.0F, 2.05F), Interpolations.LINEAR),
               new AdvancedKeyframe(0.2083F, AdvancedKeyframeAnimations.posVec(0.0F, 0.0F, 2.0F), Interpolations.LINEAR)
            }
         )
      )
      .build();
}
