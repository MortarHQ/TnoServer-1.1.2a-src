package com.github.L_Ender.cataclysm.client.animation;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.animation.AnimationChannel.Interpolations;
import net.minecraft.client.animation.AnimationChannel.Targets;
import net.minecraft.client.animation.AnimationDefinition.Builder;

public class Storm_Serpent_Animation {
   public static final AnimationDefinition STORM_SERPENT = Builder.withLength(3.5F)
      .addAnimation(
         "everything",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(-35.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.5833F, KeyframeAnimations.degreeVec(-52.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.9583F, KeyframeAnimations.degreeVec(-87.1143F, 41.9283F, -24.7595F), Interpolations.CATMULLROM),
               new Keyframe(2.0833F, KeyframeAnimations.degreeVec(-67.1277F, 46.5585F, -10.7305F), Interpolations.CATMULLROM),
               new Keyframe(2.25F, KeyframeAnimations.degreeVec(53.3157F, 29.3719F, 116.0925F), Interpolations.CATMULLROM),
               new Keyframe(2.5833F, KeyframeAnimations.degreeVec(48.4194F, -36.159F, 127.0683F), Interpolations.CATMULLROM),
               new Keyframe(3.1667F, KeyframeAnimations.degreeVec(48.4194F, -36.159F, 114.5683F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "everything",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -171.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, -5.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.4583F, KeyframeAnimations.posVec(0.0F, -5.51F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.5833F, KeyframeAnimations.posVec(0.0F, -5.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.9583F, KeyframeAnimations.posVec(0.0F, -8.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(2.0833F, KeyframeAnimations.posVec(0.0F, -8.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(2.25F, KeyframeAnimations.posVec(0.0F, -20.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(2.5833F, KeyframeAnimations.posVec(0.0F, -20.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "head",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.375F, KeyframeAnimations.degreeVec(-14.0001F, -1.6381F, -21.8224F), Interpolations.CATMULLROM),
               new Keyframe(0.6667F, KeyframeAnimations.degreeVec(37.625F, -8.5011F, -15.5075F), Interpolations.CATMULLROM),
               new Keyframe(1.25F, KeyframeAnimations.degreeVec(26.531F, 10.2847F, -18.8656F), Interpolations.CATMULLROM),
               new Keyframe(1.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -22.5F), Interpolations.CATMULLROM),
               new Keyframe(1.875F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -22.5F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "upper",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(1.125F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.7083F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(2.2083F, KeyframeAnimations.degreeVec(-92.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(2.5417F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "teeth",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(1.125F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.5833F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(2.0417F, KeyframeAnimations.degreeVec(-85.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(2.875F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "lower",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(1.125F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.5F, KeyframeAnimations.degreeVec(12.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(2.2083F, KeyframeAnimations.degreeVec(52.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(2.5417F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "seg1",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.375F, KeyframeAnimations.degreeVec(-0.7795F, 19.494F, -19.3944F), Interpolations.CATMULLROM),
               new Keyframe(0.5417F, KeyframeAnimations.degreeVec(39.9948F, 3.2644F, -10.4242F), Interpolations.CATMULLROM),
               new Keyframe(1.875F, KeyframeAnimations.degreeVec(47.9811F, 15.7816F, -20.6362F), Interpolations.CATMULLROM),
               new Keyframe(2.0833F, KeyframeAnimations.degreeVec(21.2937F, 2.9796F, -8.6285F), Interpolations.CATMULLROM),
               new Keyframe(2.25F, KeyframeAnimations.degreeVec(40.9726F, 9.5663F, -2.9175F), Interpolations.CATMULLROM),
               new Keyframe(2.6667F, KeyframeAnimations.degreeVec(32.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(3.5F, KeyframeAnimations.degreeVec(25.069F, 11.2293F, 15.6801F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "seg2",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.4583F, KeyframeAnimations.degreeVec(16.9384F, -4.4638F, 14.335F), Interpolations.CATMULLROM),
               new Keyframe(0.5417F, KeyframeAnimations.degreeVec(25.8993F, -10.5904F, 12.043F), Interpolations.CATMULLROM),
               new Keyframe(1.875F, KeyframeAnimations.degreeVec(41.6747F, -13.5038F, 6.9571F), Interpolations.CATMULLROM),
               new Keyframe(2.0833F, KeyframeAnimations.degreeVec(12.8276F, -13.3824F, 6.4955F), Interpolations.CATMULLROM),
               new Keyframe(2.25F, KeyframeAnimations.degreeVec(11.4968F, -6.8273F, 7.1579F), Interpolations.CATMULLROM),
               new Keyframe(2.5417F, KeyframeAnimations.degreeVec(16.9384F, -4.4638F, 14.335F), Interpolations.CATMULLROM),
               new Keyframe(3.5F, KeyframeAnimations.degreeVec(20.9204F, 11.4832F, 30.0048F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "seg3",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.375F, KeyframeAnimations.degreeVec(-10.0F, 0.0F, 17.5F), Interpolations.CATMULLROM),
               new Keyframe(0.5417F, KeyframeAnimations.degreeVec(2.614F, -6.0726F, 26.3595F), Interpolations.CATMULLROM),
               new Keyframe(1.875F, KeyframeAnimations.degreeVec(29.8946F, -5.633F, -24.7042F), Interpolations.CATMULLROM),
               new Keyframe(2.0833F, KeyframeAnimations.degreeVec(-10.1725F, -7.4642F, -9.2259F), Interpolations.CATMULLROM),
               new Keyframe(2.25F, KeyframeAnimations.degreeVec(-11.0314F, -13.45F, -9.5601F), Interpolations.CATMULLROM),
               new Keyframe(2.4167F, KeyframeAnimations.degreeVec(-9.6658F, 2.5759F, 32.2822F), Interpolations.CATMULLROM),
               new Keyframe(3.5F, KeyframeAnimations.degreeVec(3.635F, 18.7165F, 50.0367F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "seg4",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.2917F, KeyframeAnimations.degreeVec(-5.259F, -6.7981F, 11.9838F), Interpolations.CATMULLROM),
               new Keyframe(0.5417F, KeyframeAnimations.degreeVec(24.7616F, -6.7466F, 21.2039F), Interpolations.CATMULLROM),
               new Keyframe(1.875F, KeyframeAnimations.degreeVec(29.5737F, -18.5074F, 1.5001F), Interpolations.CATMULLROM),
               new Keyframe(2.0833F, KeyframeAnimations.degreeVec(18.5066F, -18.8226F, 2.6154F), Interpolations.CATMULLROM),
               new Keyframe(2.25F, KeyframeAnimations.degreeVec(16.6705F, -2.9654F, 7.2477F), Interpolations.CATMULLROM),
               new Keyframe(3.5F, KeyframeAnimations.degreeVec(18.9528F, 2.958F, 38.8525F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "seg5",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.2083F, KeyframeAnimations.degreeVec(29.8747F, 0.3301F, -5.579F), Interpolations.CATMULLROM),
               new Keyframe(0.5417F, KeyframeAnimations.degreeVec(30.5514F, 10.171F, -16.3668F), Interpolations.CATMULLROM),
               new Keyframe(1.875F, KeyframeAnimations.degreeVec(1.1007F, 6.5168F, -20.5348F), Interpolations.CATMULLROM),
               new Keyframe(2.1667F, KeyframeAnimations.degreeVec(18.6798F, 7.3441F, -21.5864F), Interpolations.CATMULLROM),
               new Keyframe(2.25F, KeyframeAnimations.degreeVec(12.296F, 12.3478F, 1.0127F), Interpolations.CATMULLROM),
               new Keyframe(3.5F, KeyframeAnimations.degreeVec(20.9023F, 5.0449F, 11.6439F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "seg6",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.125F, KeyframeAnimations.degreeVec(-17.5F, 7.5F, -5.0F), Interpolations.CATMULLROM),
               new Keyframe(0.375F, KeyframeAnimations.degreeVec(15.0F, 7.5F, -5.0F), Interpolations.CATMULLROM),
               new Keyframe(0.5417F, KeyframeAnimations.degreeVec(15.0F, 7.5F, -5.0F), Interpolations.CATMULLROM),
               new Keyframe(1.875F, KeyframeAnimations.degreeVec(18.6739F, -49.3826F, -44.0673F), Interpolations.CATMULLROM),
               new Keyframe(2.25F, KeyframeAnimations.degreeVec(52.9729F, -56.0274F, -48.6311F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "seg6",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.25F, KeyframeAnimations.posVec(-10.443F, -0.7549F, -5.863F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "seg6",
         new AnimationChannel(
            Targets.SCALE,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR),
               new Keyframe(2.375F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR),
               new Keyframe(2.4167F, KeyframeAnimations.scaleVec(0.0, 0.0, 0.0), Interpolations.LINEAR)
            }
         )
      )
      .build();
   public static final AnimationDefinition STORM_SERPENT2 = Builder.withLength(3.5F)
      .addAnimation(
         "everything",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(-35.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.5833F, KeyframeAnimations.degreeVec(-52.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.9583F, KeyframeAnimations.degreeVec(-87.1143F, -41.9283F, 24.7595F), Interpolations.CATMULLROM),
               new Keyframe(2.0833F, KeyframeAnimations.degreeVec(-67.1277F, -46.5585F, 10.7305F), Interpolations.CATMULLROM),
               new Keyframe(2.25F, KeyframeAnimations.degreeVec(53.3157F, -29.3719F, -116.0925F), Interpolations.CATMULLROM),
               new Keyframe(2.5833F, KeyframeAnimations.degreeVec(48.4194F, 36.159F, -127.0683F), Interpolations.CATMULLROM),
               new Keyframe(3.1667F, KeyframeAnimations.degreeVec(48.4194F, 36.159F, -114.5683F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "everything",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -171.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, -5.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.4583F, KeyframeAnimations.posVec(0.0F, -5.51F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.5833F, KeyframeAnimations.posVec(0.0F, -5.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.9583F, KeyframeAnimations.posVec(0.0F, -8.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(2.0833F, KeyframeAnimations.posVec(0.0F, -8.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(2.25F, KeyframeAnimations.posVec(0.0F, -20.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(2.5833F, KeyframeAnimations.posVec(0.0F, -20.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "head",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.375F, KeyframeAnimations.degreeVec(-14.0001F, 1.6381F, 21.8224F), Interpolations.CATMULLROM),
               new Keyframe(0.6667F, KeyframeAnimations.degreeVec(37.625F, 8.5011F, 15.5075F), Interpolations.CATMULLROM),
               new Keyframe(1.25F, KeyframeAnimations.degreeVec(26.531F, -10.2847F, 18.8656F), Interpolations.CATMULLROM),
               new Keyframe(1.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 22.5F), Interpolations.CATMULLROM),
               new Keyframe(1.875F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 22.5F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "upper",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(1.125F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.7083F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(2.2083F, KeyframeAnimations.degreeVec(-92.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(2.5417F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "teeth",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(1.125F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.5833F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(2.0417F, KeyframeAnimations.degreeVec(-85.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(2.875F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "lower",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(1.125F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.5F, KeyframeAnimations.degreeVec(12.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(2.2083F, KeyframeAnimations.degreeVec(52.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(2.5417F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "seg1",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.375F, KeyframeAnimations.degreeVec(-0.7795F, -19.494F, 19.3944F), Interpolations.CATMULLROM),
               new Keyframe(0.5417F, KeyframeAnimations.degreeVec(39.9948F, -3.2644F, 10.4242F), Interpolations.CATMULLROM),
               new Keyframe(1.875F, KeyframeAnimations.degreeVec(47.9811F, -15.7816F, 20.6362F), Interpolations.CATMULLROM),
               new Keyframe(2.0833F, KeyframeAnimations.degreeVec(21.2937F, -2.9796F, 8.6285F), Interpolations.CATMULLROM),
               new Keyframe(2.25F, KeyframeAnimations.degreeVec(40.9726F, -9.5663F, 2.9175F), Interpolations.CATMULLROM),
               new Keyframe(2.6667F, KeyframeAnimations.degreeVec(32.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(3.5F, KeyframeAnimations.degreeVec(25.069F, -11.2293F, -15.6801F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "seg2",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.4583F, KeyframeAnimations.degreeVec(16.9384F, 4.4638F, -14.335F), Interpolations.CATMULLROM),
               new Keyframe(0.5417F, KeyframeAnimations.degreeVec(25.8993F, 10.5904F, -12.043F), Interpolations.CATMULLROM),
               new Keyframe(1.875F, KeyframeAnimations.degreeVec(41.6747F, 13.5038F, -6.9571F), Interpolations.CATMULLROM),
               new Keyframe(2.0833F, KeyframeAnimations.degreeVec(12.8276F, 13.3824F, -6.4955F), Interpolations.CATMULLROM),
               new Keyframe(2.25F, KeyframeAnimations.degreeVec(11.4968F, 6.8273F, -7.1579F), Interpolations.CATMULLROM),
               new Keyframe(2.5417F, KeyframeAnimations.degreeVec(16.9384F, 4.4638F, -14.335F), Interpolations.CATMULLROM),
               new Keyframe(3.5F, KeyframeAnimations.degreeVec(20.9204F, -11.4832F, -30.0048F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "seg3",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.375F, KeyframeAnimations.degreeVec(-10.0F, 0.0F, -17.5F), Interpolations.CATMULLROM),
               new Keyframe(0.5417F, KeyframeAnimations.degreeVec(2.614F, 6.0726F, -26.3595F), Interpolations.CATMULLROM),
               new Keyframe(1.875F, KeyframeAnimations.degreeVec(29.8946F, 5.633F, 24.7042F), Interpolations.CATMULLROM),
               new Keyframe(2.0833F, KeyframeAnimations.degreeVec(-10.1725F, 7.4642F, 9.2259F), Interpolations.CATMULLROM),
               new Keyframe(2.25F, KeyframeAnimations.degreeVec(-11.0314F, 13.45F, 9.5601F), Interpolations.CATMULLROM),
               new Keyframe(2.4167F, KeyframeAnimations.degreeVec(-9.6658F, -2.5759F, -32.2822F), Interpolations.CATMULLROM),
               new Keyframe(3.5F, KeyframeAnimations.degreeVec(3.635F, -18.7165F, -50.0367F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "seg4",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.2917F, KeyframeAnimations.degreeVec(-5.259F, 6.7981F, -11.9838F), Interpolations.CATMULLROM),
               new Keyframe(0.5417F, KeyframeAnimations.degreeVec(24.7616F, 6.7466F, -21.2039F), Interpolations.CATMULLROM),
               new Keyframe(1.875F, KeyframeAnimations.degreeVec(29.5737F, 18.5074F, -1.5001F), Interpolations.CATMULLROM),
               new Keyframe(2.0833F, KeyframeAnimations.degreeVec(18.5066F, 18.8226F, -2.6154F), Interpolations.CATMULLROM),
               new Keyframe(2.25F, KeyframeAnimations.degreeVec(16.6705F, 2.9654F, -7.2477F), Interpolations.CATMULLROM),
               new Keyframe(3.5F, KeyframeAnimations.degreeVec(18.9528F, -2.958F, -38.8525F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "seg5",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.2083F, KeyframeAnimations.degreeVec(29.8747F, -0.3301F, 5.579F), Interpolations.CATMULLROM),
               new Keyframe(0.5417F, KeyframeAnimations.degreeVec(30.5514F, -10.171F, 16.3668F), Interpolations.CATMULLROM),
               new Keyframe(1.875F, KeyframeAnimations.degreeVec(1.1007F, -6.5168F, 20.5348F), Interpolations.CATMULLROM),
               new Keyframe(2.1667F, KeyframeAnimations.degreeVec(18.6798F, -7.3441F, 21.5864F), Interpolations.CATMULLROM),
               new Keyframe(2.25F, KeyframeAnimations.degreeVec(12.296F, -12.3478F, -1.0127F), Interpolations.CATMULLROM),
               new Keyframe(3.5F, KeyframeAnimations.degreeVec(20.9023F, -5.0449F, -11.6439F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "seg6",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.125F, KeyframeAnimations.degreeVec(-17.5F, -7.5F, 5.0F), Interpolations.CATMULLROM),
               new Keyframe(0.375F, KeyframeAnimations.degreeVec(15.0F, -7.5F, 5.0F), Interpolations.CATMULLROM),
               new Keyframe(0.5417F, KeyframeAnimations.degreeVec(15.0F, -7.5F, 5.0F), Interpolations.CATMULLROM),
               new Keyframe(1.875F, KeyframeAnimations.degreeVec(18.6739F, 49.3826F, 44.0673F), Interpolations.CATMULLROM),
               new Keyframe(2.25F, KeyframeAnimations.degreeVec(52.9729F, 56.0274F, 48.6311F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "seg6",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.25F, KeyframeAnimations.posVec(10.443F, -0.7549F, -5.863F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "seg6",
         new AnimationChannel(
            Targets.SCALE,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR),
               new Keyframe(2.375F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR),
               new Keyframe(2.4167F, KeyframeAnimations.scaleVec(0.0, 0.0, 0.0), Interpolations.LINEAR)
            }
         )
      )
      .build();
}
