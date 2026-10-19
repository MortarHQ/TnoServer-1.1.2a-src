package com.github.L_Ender.cataclysm.client.animation;

import com.github.L_Ender.lionfishapi.client.model.AdvancedAnimations.AdvancedAnimationChannel;
import com.github.L_Ender.lionfishapi.client.model.AdvancedAnimations.AdvancedAnimationDefinition;
import com.github.L_Ender.lionfishapi.client.model.AdvancedAnimations.AdvancedKeyframe;
import com.github.L_Ender.lionfishapi.client.model.AdvancedAnimations.AdvancedKeyframeAnimations;
import com.github.L_Ender.lionfishapi.client.model.AdvancedAnimations.AdvancedAnimationChannel.Interpolations;
import com.github.L_Ender.lionfishapi.client.model.AdvancedAnimations.AdvancedAnimationChannel.Targets;
import com.github.L_Ender.lionfishapi.client.model.AdvancedAnimations.AdvancedAnimationDefinition.Builder;

public class Sandstorm_Animation {
   public static final AdvancedAnimationDefinition SPAWN = Builder.withLength(0.3333F)
      .addAnimation(
         "storm",
         new AdvancedAnimationChannel(
            Targets.ROTATION,
            new AdvancedKeyframe[]{
               new AdvancedKeyframe(0.0F, AdvancedKeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new AdvancedKeyframe(0.3333F, AdvancedKeyframeAnimations.degreeVec(0.0F, 360.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "storm",
         new AdvancedAnimationChannel(
            Targets.POSITION,
            new AdvancedKeyframe[]{
               new AdvancedKeyframe(0.0F, AdvancedKeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new AdvancedKeyframe(0.125F, AdvancedKeyframeAnimations.posVec(0.0F, 6.0F, 0.0F), Interpolations.CATMULLROM),
               new AdvancedKeyframe(0.3333F, AdvancedKeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "storm",
         new AdvancedAnimationChannel(
            Targets.SCALE,
            new AdvancedKeyframe[]{
               new AdvancedKeyframe(0.0F, AdvancedKeyframeAnimations.scaleVec(0.0, 0.0, 0.0), Interpolations.CATMULLROM),
               new AdvancedKeyframe(0.0833F, AdvancedKeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "storm2",
         new AdvancedAnimationChannel(
            Targets.ROTATION,
            new AdvancedKeyframe[]{
               new AdvancedKeyframe(0.0F, AdvancedKeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new AdvancedKeyframe(0.3333F, AdvancedKeyframeAnimations.degreeVec(0.0F, 360.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "storm2",
         new AdvancedAnimationChannel(
            Targets.POSITION,
            new AdvancedKeyframe[]{
               new AdvancedKeyframe(0.0F, AdvancedKeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new AdvancedKeyframe(0.1667F, AdvancedKeyframeAnimations.posVec(0.0F, 6.0F, 0.0F), Interpolations.CATMULLROM),
               new AdvancedKeyframe(0.3333F, AdvancedKeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "storm2",
         new AdvancedAnimationChannel(
            Targets.SCALE,
            new AdvancedKeyframe[]{
               new AdvancedKeyframe(0.0F, AdvancedKeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
               new AdvancedKeyframe(0.0833F, AdvancedKeyframeAnimations.scaleVec(0.0, 0.0, 0.0), Interpolations.CATMULLROM),
               new AdvancedKeyframe(0.1667F, AdvancedKeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "storm3",
         new AdvancedAnimationChannel(
            Targets.ROTATION,
            new AdvancedKeyframe[]{
               new AdvancedKeyframe(0.0F, AdvancedKeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new AdvancedKeyframe(0.3333F, AdvancedKeyframeAnimations.degreeVec(0.0F, 360.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "storm3",
         new AdvancedAnimationChannel(
            Targets.POSITION,
            new AdvancedKeyframe[]{
               new AdvancedKeyframe(0.0F, AdvancedKeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new AdvancedKeyframe(0.2083F, AdvancedKeyframeAnimations.posVec(0.0F, 6.0F, 0.0F), Interpolations.CATMULLROM),
               new AdvancedKeyframe(0.3333F, AdvancedKeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "storm3",
         new AdvancedAnimationChannel(
            Targets.SCALE,
            new AdvancedKeyframe[]{
               new AdvancedKeyframe(0.0F, AdvancedKeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
               new AdvancedKeyframe(0.1667F, AdvancedKeyframeAnimations.scaleVec(0.0, 1.0, 0.0), Interpolations.CATMULLROM),
               new AdvancedKeyframe(0.25F, AdvancedKeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "storm4",
         new AdvancedAnimationChannel(
            Targets.ROTATION,
            new AdvancedKeyframe[]{
               new AdvancedKeyframe(0.0F, AdvancedKeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new AdvancedKeyframe(0.3333F, AdvancedKeyframeAnimations.degreeVec(0.0F, 360.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "storm4",
         new AdvancedAnimationChannel(
            Targets.POSITION,
            new AdvancedKeyframe[]{
               new AdvancedKeyframe(0.0F, AdvancedKeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new AdvancedKeyframe(0.25F, AdvancedKeyframeAnimations.posVec(0.0F, 6.0F, 0.0F), Interpolations.CATMULLROM),
               new AdvancedKeyframe(0.3333F, AdvancedKeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "storm4",
         new AdvancedAnimationChannel(
            Targets.SCALE,
            new AdvancedKeyframe[]{
               new AdvancedKeyframe(0.0F, AdvancedKeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
               new AdvancedKeyframe(0.25F, AdvancedKeyframeAnimations.scaleVec(0.0, 0.0, 0.0), Interpolations.CATMULLROM),
               new AdvancedKeyframe(0.3333F, AdvancedKeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM)
            }
         )
      )
      .build();
   public static final AdvancedAnimationDefinition DESPAWN = Builder.withLength(0.3333F)
      .addAnimation(
         "storm",
         new AdvancedAnimationChannel(
            Targets.ROTATION,
            new AdvancedKeyframe[]{
               new AdvancedKeyframe(0.0F, AdvancedKeyframeAnimations.degreeVec(0.0F, 360.0F, 0.0F), Interpolations.CATMULLROM),
               new AdvancedKeyframe(0.3333F, AdvancedKeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "storm",
         new AdvancedAnimationChannel(
            Targets.POSITION,
            new AdvancedKeyframe[]{
               new AdvancedKeyframe(0.0F, AdvancedKeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new AdvancedKeyframe(0.2083F, AdvancedKeyframeAnimations.posVec(0.0F, 6.0F, 0.0F), Interpolations.CATMULLROM),
               new AdvancedKeyframe(0.3333F, AdvancedKeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "storm",
         new AdvancedAnimationChannel(
            Targets.SCALE,
            new AdvancedKeyframe[]{
               new AdvancedKeyframe(0.25F, AdvancedKeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
               new AdvancedKeyframe(0.3333F, AdvancedKeyframeAnimations.scaleVec(0.0, 0.0, 0.0), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "storm2",
         new AdvancedAnimationChannel(
            Targets.ROTATION,
            new AdvancedKeyframe[]{
               new AdvancedKeyframe(0.0F, AdvancedKeyframeAnimations.degreeVec(0.0F, 360.0F, 0.0F), Interpolations.CATMULLROM),
               new AdvancedKeyframe(0.3333F, AdvancedKeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "storm2",
         new AdvancedAnimationChannel(
            Targets.POSITION,
            new AdvancedKeyframe[]{
               new AdvancedKeyframe(0.0F, AdvancedKeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new AdvancedKeyframe(0.1667F, AdvancedKeyframeAnimations.posVec(0.0F, 6.0F, 0.0F), Interpolations.CATMULLROM),
               new AdvancedKeyframe(0.3333F, AdvancedKeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "storm2",
         new AdvancedAnimationChannel(
            Targets.SCALE,
            new AdvancedKeyframe[]{
               new AdvancedKeyframe(0.1667F, AdvancedKeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
               new AdvancedKeyframe(0.25F, AdvancedKeyframeAnimations.scaleVec(0.0, 0.0, 0.0), Interpolations.CATMULLROM),
               new AdvancedKeyframe(0.3333F, AdvancedKeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "storm3",
         new AdvancedAnimationChannel(
            Targets.ROTATION,
            new AdvancedKeyframe[]{
               new AdvancedKeyframe(0.0F, AdvancedKeyframeAnimations.degreeVec(0.0F, 360.0F, 0.0F), Interpolations.CATMULLROM),
               new AdvancedKeyframe(0.3333F, AdvancedKeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "storm3",
         new AdvancedAnimationChannel(
            Targets.POSITION,
            new AdvancedKeyframe[]{
               new AdvancedKeyframe(0.0F, AdvancedKeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new AdvancedKeyframe(0.125F, AdvancedKeyframeAnimations.posVec(0.0F, 6.0F, 0.0F), Interpolations.CATMULLROM),
               new AdvancedKeyframe(0.3333F, AdvancedKeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "storm3",
         new AdvancedAnimationChannel(
            Targets.SCALE,
            new AdvancedKeyframe[]{
               new AdvancedKeyframe(0.0833F, AdvancedKeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
               new AdvancedKeyframe(0.1667F, AdvancedKeyframeAnimations.scaleVec(0.0, 1.0, 0.0), Interpolations.CATMULLROM),
               new AdvancedKeyframe(0.3333F, AdvancedKeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "storm4",
         new AdvancedAnimationChannel(
            Targets.ROTATION,
            new AdvancedKeyframe[]{
               new AdvancedKeyframe(0.0F, AdvancedKeyframeAnimations.degreeVec(0.0F, 360.0F, 0.0F), Interpolations.CATMULLROM),
               new AdvancedKeyframe(0.3333F, AdvancedKeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "storm4",
         new AdvancedAnimationChannel(
            Targets.POSITION,
            new AdvancedKeyframe[]{
               new AdvancedKeyframe(0.0F, AdvancedKeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new AdvancedKeyframe(0.0833F, AdvancedKeyframeAnimations.posVec(0.0F, 6.0F, 0.0F), Interpolations.CATMULLROM),
               new AdvancedKeyframe(0.3333F, AdvancedKeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "storm4",
         new AdvancedAnimationChannel(
            Targets.SCALE,
            new AdvancedKeyframe[]{
               new AdvancedKeyframe(0.0F, AdvancedKeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
               new AdvancedKeyframe(0.0833F, AdvancedKeyframeAnimations.scaleVec(0.0, 0.0, 0.0), Interpolations.CATMULLROM),
               new AdvancedKeyframe(0.3333F, AdvancedKeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM)
            }
         )
      )
      .build();
}
