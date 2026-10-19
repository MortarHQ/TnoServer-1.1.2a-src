package com.github.L_Ender.cataclysm.client.animation;

import com.github.L_Ender.lionfishapi.client.model.AdvancedAnimations.AdvancedAnimationChannel;
import com.github.L_Ender.lionfishapi.client.model.AdvancedAnimations.AdvancedAnimationDefinition;
import com.github.L_Ender.lionfishapi.client.model.AdvancedAnimations.AdvancedKeyframe;
import com.github.L_Ender.lionfishapi.client.model.AdvancedAnimations.AdvancedKeyframeAnimations;
import com.github.L_Ender.lionfishapi.client.model.AdvancedAnimations.AdvancedAnimationChannel.Interpolations;
import com.github.L_Ender.lionfishapi.client.model.AdvancedAnimations.AdvancedAnimationChannel.Targets;
import com.github.L_Ender.lionfishapi.client.model.AdvancedAnimations.AdvancedAnimationDefinition.Builder;

public class Phantom_Halberd_Animation {
   public static final AdvancedAnimationDefinition ONE = Builder.withLength(1.25F)
      .addAnimation(
         "halberd",
         new AdvancedAnimationChannel(
            Targets.ROTATION,
            new AdvancedKeyframe[]{
               new AdvancedKeyframe(0.0F, AdvancedKeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new AdvancedKeyframe(0.0833F, AdvancedKeyframeAnimations.degreeVec(10.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new AdvancedKeyframe(0.125F, AdvancedKeyframeAnimations.degreeVec(7.505F, -0.4332F, 0.0F), Interpolations.CATMULLROM),
               new AdvancedKeyframe(0.3333F, AdvancedKeyframeAnimations.degreeVec(10.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "halberd",
         new AdvancedAnimationChannel(
            Targets.POSITION,
            new AdvancedKeyframe[]{
               new AdvancedKeyframe(0.0F, AdvancedKeyframeAnimations.posVec(0.0F, -73.0F, 0.0F), Interpolations.CATMULLROM),
               new AdvancedKeyframe(0.0417F, AdvancedKeyframeAnimations.posVec(0.0F, 1.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "halberd",
         new AdvancedAnimationChannel(
            Targets.SCALE,
            new AdvancedKeyframe[]{
               new AdvancedKeyframe(0.0F, AdvancedKeyframeAnimations.scaleVec(0.0, 0.0, 0.0), Interpolations.CATMULLROM),
               new AdvancedKeyframe(0.0833F, AdvancedKeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM)
            }
         )
      )
      .build();
   public static final AdvancedAnimationDefinition TWO = Builder.withLength(1.25F)
      .addAnimation(
         "halberd",
         new AdvancedAnimationChannel(
            Targets.ROTATION,
            new AdvancedKeyframe[]{
               new AdvancedKeyframe(0.0F, AdvancedKeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new AdvancedKeyframe(0.0833F, AdvancedKeyframeAnimations.degreeVec(10.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new AdvancedKeyframe(0.125F, AdvancedKeyframeAnimations.degreeVec(7.6778F, -1.231F, 0.0F), Interpolations.CATMULLROM),
               new AdvancedKeyframe(0.3333F, AdvancedKeyframeAnimations.degreeVec(10.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "halberd",
         new AdvancedAnimationChannel(
            Targets.POSITION,
            new AdvancedKeyframe[]{
               new AdvancedKeyframe(0.0F, AdvancedKeyframeAnimations.posVec(0.0F, -73.0F, 0.0F), Interpolations.CATMULLROM),
               new AdvancedKeyframe(0.0417F, AdvancedKeyframeAnimations.posVec(0.0F, 1.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "halberd",
         new AdvancedAnimationChannel(
            Targets.SCALE,
            new AdvancedKeyframe[]{
               new AdvancedKeyframe(0.0F, AdvancedKeyframeAnimations.scaleVec(0.0, 0.0, 0.0), Interpolations.CATMULLROM),
               new AdvancedKeyframe(0.0833F, AdvancedKeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM)
            }
         )
      )
      .build();
   public static final AdvancedAnimationDefinition THREE = Builder.withLength(1.25F)
      .addAnimation(
         "halberd",
         new AdvancedAnimationChannel(
            Targets.ROTATION,
            new AdvancedKeyframe[]{
               new AdvancedKeyframe(0.0F, AdvancedKeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new AdvancedKeyframe(0.0833F, AdvancedKeyframeAnimations.degreeVec(10.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new AdvancedKeyframe(0.125F, AdvancedKeyframeAnimations.degreeVec(9.5532F, -1.7581F, 0.0F), Interpolations.CATMULLROM),
               new AdvancedKeyframe(0.3333F, AdvancedKeyframeAnimations.degreeVec(10.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "halberd",
         new AdvancedAnimationChannel(
            Targets.POSITION,
            new AdvancedKeyframe[]{
               new AdvancedKeyframe(0.0F, AdvancedKeyframeAnimations.posVec(0.0F, -73.0F, 0.0F), Interpolations.CATMULLROM),
               new AdvancedKeyframe(0.0417F, AdvancedKeyframeAnimations.posVec(0.0F, 1.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "halberd",
         new AdvancedAnimationChannel(
            Targets.SCALE,
            new AdvancedKeyframe[]{
               new AdvancedKeyframe(0.0F, AdvancedKeyframeAnimations.scaleVec(0.0, 0.0, 0.0), Interpolations.CATMULLROM),
               new AdvancedKeyframe(0.0833F, AdvancedKeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "mid_root",
         new AdvancedAnimationChannel(
            Targets.ROTATION,
            new AdvancedKeyframe[]{new AdvancedKeyframe(0.0F, AdvancedKeyframeAnimations.degreeVec(0.0F, 180.0F, 0.0F), Interpolations.LINEAR)}
         )
      )
      .build();
   public static final AdvancedAnimationDefinition FOUR = Builder.withLength(1.25F)
      .addAnimation(
         "halberd",
         new AdvancedAnimationChannel(
            Targets.ROTATION,
            new AdvancedKeyframe[]{
               new AdvancedKeyframe(0.0F, AdvancedKeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new AdvancedKeyframe(0.0833F, AdvancedKeyframeAnimations.degreeVec(10.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new AdvancedKeyframe(0.125F, AdvancedKeyframeAnimations.degreeVec(8.6775F, -0.0082F, 0.0F), Interpolations.CATMULLROM),
               new AdvancedKeyframe(0.3333F, AdvancedKeyframeAnimations.degreeVec(10.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "halberd",
         new AdvancedAnimationChannel(
            Targets.POSITION,
            new AdvancedKeyframe[]{
               new AdvancedKeyframe(0.0F, AdvancedKeyframeAnimations.posVec(0.0F, -73.0F, 0.0F), Interpolations.CATMULLROM),
               new AdvancedKeyframe(0.0417F, AdvancedKeyframeAnimations.posVec(0.0F, 1.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "halberd",
         new AdvancedAnimationChannel(
            Targets.SCALE,
            new AdvancedKeyframe[]{
               new AdvancedKeyframe(0.0F, AdvancedKeyframeAnimations.scaleVec(0.0, 0.0, 0.0), Interpolations.CATMULLROM),
               new AdvancedKeyframe(0.0833F, AdvancedKeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "mid_root",
         new AdvancedAnimationChannel(
            Targets.ROTATION,
            new AdvancedKeyframe[]{new AdvancedKeyframe(0.0F, AdvancedKeyframeAnimations.degreeVec(0.0F, 270.0F, 0.0F), Interpolations.LINEAR)}
         )
      )
      .build();
}
