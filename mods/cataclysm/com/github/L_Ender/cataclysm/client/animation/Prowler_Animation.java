package com.github.L_Ender.cataclysm.client.animation;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.animation.AnimationChannel.Interpolations;
import net.minecraft.client.animation.AnimationChannel.Targets;
import net.minecraft.client.animation.AnimationDefinition.Builder;

public class Prowler_Animation {
   public static final AnimationDefinition MELEE = Builder.withLength(2.5F)
      .addAnimation(
         "upperbody",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.0833F, KeyframeAnimations.degreeVec(5.0F, -45.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.1667F, KeyframeAnimations.degreeVec(10.0F, -75.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(10.0F, -80.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.625F, KeyframeAnimations.degreeVec(10.0F, -80.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.7083F, KeyframeAnimations.degreeVec(6.1448F, -54.659F, -3.9992F), Interpolations.LINEAR),
               new Keyframe(0.7917F, KeyframeAnimations.degreeVec(5.2282F, -47.1956F, -2.821F), Interpolations.LINEAR),
               new Keyframe(0.875F, KeyframeAnimations.degreeVec(4.9978F, -44.7055F, -2.5006F), Interpolations.LINEAR),
               new Keyframe(1.0F, KeyframeAnimations.degreeVec(5.2305F, -47.2001F, -2.8206F), Interpolations.LINEAR),
               new Keyframe(1.625F, KeyframeAnimations.degreeVec(5.2305F, -47.2001F, -2.8206F), Interpolations.LINEAR),
               new Keyframe(2.25F, KeyframeAnimations.degreeVec(5.2305F, -47.2001F, -2.8206F), Interpolations.LINEAR),
               new Keyframe(2.5F, KeyframeAnimations.degreeVec(0.0F, 2.5F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "eye_blow",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, -1.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.75F, KeyframeAnimations.posVec(0.0F, -1.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.875F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "right_arm",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.0833F, KeyframeAnimations.degreeVec(-10.5202F, -3.3467F, 14.8053F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(-24.841F, -15.06F, 66.6238F), Interpolations.LINEAR),
               new Keyframe(0.4583F, KeyframeAnimations.degreeVec(-29.8408F, -15.06F, 66.6229F), Interpolations.LINEAR),
               new Keyframe(0.625F, KeyframeAnimations.degreeVec(-29.65F, -12.47F, 65.7F), Interpolations.LINEAR),
               new Keyframe(0.75F, KeyframeAnimations.degreeVec(-29.163F, -6.5612F, 62.661F), Interpolations.LINEAR),
               new Keyframe(0.875F, KeyframeAnimations.degreeVec(-26.5436F, -4.3755F, 61.4386F), Interpolations.LINEAR),
               new Keyframe(1.0F, KeyframeAnimations.degreeVec(-26.4801F, -2.1383F, 60.3209F), Interpolations.LINEAR),
               new Keyframe(2.125F, KeyframeAnimations.degreeVec(-26.4801F, -2.1383F, 60.3209F), Interpolations.LINEAR),
               new Keyframe(2.25F, KeyframeAnimations.degreeVec(-26.48F, -2.14F, 60.32F), Interpolations.LINEAR),
               new Keyframe(2.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "right_arm",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.6667F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.75F, KeyframeAnimations.posVec(-1.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.8333F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "right_arm2",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.0F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.125F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.25F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "right_joint",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.375F, KeyframeAnimations.degreeVec(-5.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.75F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.8333F, KeyframeAnimations.degreeVec(52.5F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.9167F, KeyframeAnimations.degreeVec(62.5F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.0F, KeyframeAnimations.degreeVec(65.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.0833F, KeyframeAnimations.degreeVec(64.9791F, -2.2656F, 1.0571F), Interpolations.LINEAR),
               new Keyframe(2.125F, KeyframeAnimations.degreeVec(64.9791F, -2.2656F, 1.0571F), Interpolations.LINEAR),
               new Keyframe(2.25F, KeyframeAnimations.degreeVec(64.98F, -2.27F, 1.06F), Interpolations.LINEAR),
               new Keyframe(2.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "saw",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.75F, KeyframeAnimations.degreeVec(-887.5F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.25F, KeyframeAnimations.degreeVec(-1067.5F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.125F, KeyframeAnimations.degreeVec(-5747.5F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .build();
   public static final AnimationDefinition STRONG_ATTACK = Builder.withLength(2.25F)
      .addAnimation(
         "roots",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(1.1667F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.25F, KeyframeAnimations.posVec(0.0F, -2.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.3333F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "upperbody",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.0833F, KeyframeAnimations.degreeVec(-5.019F, -4.9809F, 0.4369F), Interpolations.LINEAR),
               new Keyframe(0.1667F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(25.6288F, 21.9184F, 4.7966F), Interpolations.LINEAR),
               new Keyframe(0.3333F, KeyframeAnimations.degreeVec(35.6076F, 30.5855F, 10.365F), Interpolations.LINEAR),
               new Keyframe(0.4167F, KeyframeAnimations.degreeVec(39.0129F, 32.6066F, 12.0926F), Interpolations.LINEAR),
               new Keyframe(0.625F, KeyframeAnimations.degreeVec(39.0129F, 32.6066F, 12.0926F), Interpolations.LINEAR),
               new Keyframe(1.125F, KeyframeAnimations.degreeVec(37.6446F, 28.0644F, 9.5368F), Interpolations.LINEAR),
               new Keyframe(1.25F, KeyframeAnimations.degreeVec(-31.369F, -40.5497F, 16.175F), Interpolations.LINEAR),
               new Keyframe(1.375F, KeyframeAnimations.degreeVec(-45.8059F, -35.613F, 23.3561F), Interpolations.LINEAR),
               new Keyframe(1.5F, KeyframeAnimations.degreeVec(-47.1462F, -37.3349F, 25.6103F), Interpolations.LINEAR),
               new Keyframe(2.0F, KeyframeAnimations.degreeVec(-47.15F, -37.33F, 25.61F), Interpolations.LINEAR),
               new Keyframe(2.0833F, KeyframeAnimations.degreeVec(-52.15F, -37.33F, 25.61F), Interpolations.LINEAR),
               new Keyframe(2.1667F, KeyframeAnimations.degreeVec(-8.4326F, -13.9815F, 3.0092F), Interpolations.LINEAR),
               new Keyframe(2.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "eye_blow",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.875F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -7.5F), Interpolations.LINEAR),
               new Keyframe(2.1667F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -7.5F), Interpolations.LINEAR),
               new Keyframe(2.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "right_arm",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.4167F, KeyframeAnimations.degreeVec(23.6651F, -4.1864F, 9.0998F), Interpolations.LINEAR),
               new Keyframe(0.625F, KeyframeAnimations.degreeVec(24.2477F, -6.2797F, 13.6497F), Interpolations.LINEAR),
               new Keyframe(0.875F, KeyframeAnimations.degreeVec(24.25F, -6.28F, 13.65F), Interpolations.LINEAR),
               new Keyframe(1.125F, KeyframeAnimations.degreeVec(29.25F, -6.28F, 13.65F), Interpolations.LINEAR),
               new Keyframe(1.25F, KeyframeAnimations.degreeVec(-62.259F, 16.0208F, 25.0645F), Interpolations.LINEAR),
               new Keyframe(1.375F, KeyframeAnimations.degreeVec(-104.759F, 16.0208F, 25.0645F), Interpolations.LINEAR),
               new Keyframe(1.5F, KeyframeAnimations.degreeVec(-109.759F, 16.0208F, 25.0645F), Interpolations.LINEAR),
               new Keyframe(2.0F, KeyframeAnimations.degreeVec(-109.759F, 16.0208F, 25.0645F), Interpolations.LINEAR),
               new Keyframe(2.0833F, KeyframeAnimations.degreeVec(-114.759F, 16.0208F, 25.0645F), Interpolations.LINEAR),
               new Keyframe(2.1667F, KeyframeAnimations.degreeVec(-44.759F, 16.0208F, 25.0645F), Interpolations.LINEAR),
               new Keyframe(2.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "right_arm2",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.125F, KeyframeAnimations.degreeVec(2.5F, -1.5F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.625F, KeyframeAnimations.degreeVec(0.0F, -7.5F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.75F, KeyframeAnimations.degreeVec(2.5F, -7.5F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "right_joint",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.875F, KeyframeAnimations.degreeVec(50.0F, 7.5F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.125F, KeyframeAnimations.degreeVec(50.0F, 7.5F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.375F, KeyframeAnimations.degreeVec(55.0F, 7.5F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.625F, KeyframeAnimations.degreeVec(22.5F, 7.5F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.875F, KeyframeAnimations.degreeVec(20.0F, 7.5F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.0F, KeyframeAnimations.degreeVec(20.0F, 7.5F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.0833F, KeyframeAnimations.degreeVec(12.5F, 7.5F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.1667F, KeyframeAnimations.degreeVec(0.0F, 7.5F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "chainsaw",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -12.5F), Interpolations.LINEAR),
               new Keyframe(1.625F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -12.5F), Interpolations.LINEAR),
               new Keyframe(1.75F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "saw",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.3333F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.625F, KeyframeAnimations.degreeVec(180.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.25F, KeyframeAnimations.degreeVec(-180.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.25F, KeyframeAnimations.degreeVec(3240.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "pelvis",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.2917F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.375F, KeyframeAnimations.posVec(0.0F, -2.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.4583F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .build();
   public static final AnimationDefinition SPIN = Builder.withLength(2.5F)
      .addAnimation(
         "upperbody",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.0833F, KeyframeAnimations.degreeVec(0.0F, -37.5F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.1667F, KeyframeAnimations.degreeVec(0.0F, -50.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(0.0F, -52.5F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, -52.5F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.875F, KeyframeAnimations.degreeVec(0.0F, -52.5F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.9583F, KeyframeAnimations.degreeVec(0.0F, -62.5F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.0417F, KeyframeAnimations.degreeVec(0.0F, -67.5F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.125F, KeyframeAnimations.degreeVec(0.0F, -70.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.625F, KeyframeAnimations.degreeVec(0.0F, 367.5F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.75F, KeyframeAnimations.degreeVec(0.0F, 475.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.875F, KeyframeAnimations.degreeVec(0.0F, 507.5F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 510.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.0833F, KeyframeAnimations.degreeVec(0.0F, 512.5F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.1667F, KeyframeAnimations.degreeVec(0.0F, 445.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.25F, KeyframeAnimations.degreeVec(0.0F, 392.5F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.3333F, KeyframeAnimations.degreeVec(0.0F, 362.5F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.375F, KeyframeAnimations.degreeVec(0.0F, 360.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "eye_blow",
         new AnimationChannel(Targets.ROTATION, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)})
      )
      .addAnimation(
         "eye_blow",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, 0.99F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.375F, KeyframeAnimations.posVec(0.0F, -0.25F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.875F, KeyframeAnimations.posVec(0.0F, -0.25F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.25F, KeyframeAnimations.posVec(0.0F, 1.75F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.375F, KeyframeAnimations.posVec(0.0F, -0.75F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "right_arm",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.0833F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -5.0F), Interpolations.LINEAR),
               new Keyframe(0.1667F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -2.5F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 5.0F), Interpolations.LINEAR),
               new Keyframe(0.3333F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 45.0F), Interpolations.LINEAR),
               new Keyframe(0.4167F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 42.72F), Interpolations.LINEAR),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 48.05F), Interpolations.LINEAR),
               new Keyframe(2.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 50.0F), Interpolations.LINEAR),
               new Keyframe(2.3333F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 38.33F), Interpolations.LINEAR),
               new Keyframe(2.4167F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 14.17F), Interpolations.LINEAR),
               new Keyframe(2.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "right_joint",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.3333F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 10.0F), Interpolations.LINEAR),
               new Keyframe(0.4167F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 30.0F), Interpolations.LINEAR),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 40.0F), Interpolations.LINEAR),
               new Keyframe(1.125F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 40.0F), Interpolations.LINEAR),
               new Keyframe(2.0F, KeyframeAnimations.degreeVec(90.0F, 0.0F, 40.0F), Interpolations.LINEAR),
               new Keyframe(2.25F, KeyframeAnimations.degreeVec(90.0F, 0.0F, 40.0F), Interpolations.LINEAR),
               new Keyframe(2.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "chainsaw",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.125F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 40.0F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(-12.5F, 0.0F, 40.0F), Interpolations.LINEAR),
               new Keyframe(0.375F, KeyframeAnimations.degreeVec(-12.5F, 0.0F, 40.0F), Interpolations.LINEAR),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "saw",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.625F, KeyframeAnimations.degreeVec(540.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.5F, KeyframeAnimations.degreeVec(-4320.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .build();
   public static final AnimationDefinition PIERCE = Builder.withLength(4.0F)
      .addAnimation(
         "roots",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(1.1667F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.25F, KeyframeAnimations.posVec(0.0F, -2.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.3333F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(3.0833F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(3.1667F, KeyframeAnimations.posVec(0.0F, 0.0F, -2.0F), Interpolations.LINEAR),
               new Keyframe(3.25F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "upperbody",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.125F, KeyframeAnimations.degreeVec(0.0F, 5.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(0.0F, 22.5F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.375F, KeyframeAnimations.degreeVec(0.0F, 30.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 32.5F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.125F, KeyframeAnimations.degreeVec(0.0F, 47.5F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.2083F, KeyframeAnimations.degreeVec(0.0F, -30.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.2917F, KeyframeAnimations.degreeVec(0.0F, -37.5F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.375F, KeyframeAnimations.degreeVec(0.0F, -42.08F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.4583F, KeyframeAnimations.degreeVec(0.0F, -43.87F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.875F, KeyframeAnimations.degreeVec(0.0F, -43.87F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.5F, KeyframeAnimations.degreeVec(0.0F, -45.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.5833F, KeyframeAnimations.degreeVec(0.0F, -50.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.6667F, KeyframeAnimations.degreeVec(0.0F, -60.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.75F, KeyframeAnimations.degreeVec(0.0F, -80.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.8333F, KeyframeAnimations.degreeVec(0.0F, -120.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.9167F, KeyframeAnimations.degreeVec(0.0F, -115.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(3.0F, KeyframeAnimations.degreeVec(0.0F, -102.5F, 0.0F), Interpolations.LINEAR),
               new Keyframe(3.0833F, KeyframeAnimations.degreeVec(0.0F, -77.5F, 0.0F), Interpolations.LINEAR),
               new Keyframe(3.1667F, KeyframeAnimations.degreeVec(0.0F, -7.5F, 0.0F), Interpolations.LINEAR),
               new Keyframe(3.25F, KeyframeAnimations.degreeVec(0.0F, 72.5F, 0.0F), Interpolations.LINEAR),
               new Keyframe(3.3333F, KeyframeAnimations.degreeVec(0.0F, 77.5F, 0.0F), Interpolations.LINEAR),
               new Keyframe(3.4167F, KeyframeAnimations.degreeVec(0.0F, 80.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(3.75F, KeyframeAnimations.degreeVec(0.0F, 80.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(3.8333F, KeyframeAnimations.degreeVec(0.0F, 75.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(3.9167F, KeyframeAnimations.degreeVec(0.0F, 45.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(4.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "upperbody",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(3.0417F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(3.125F, KeyframeAnimations.posVec(0.0F, 0.0F, -2.0F), Interpolations.LINEAR),
               new Keyframe(3.2083F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "rocket_luncher",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.375F, KeyframeAnimations.degreeVec(360.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "rocket_luncher",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.375F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.4583F, KeyframeAnimations.posVec(0.0F, 0.0F, 2.0F), Interpolations.LINEAR),
               new Keyframe(0.5833F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.6667F, KeyframeAnimations.posVec(0.0F, 0.0F, 2.0F), Interpolations.LINEAR),
               new Keyframe(0.7917F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.875F, KeyframeAnimations.posVec(0.0F, 0.0F, 2.0F), Interpolations.LINEAR),
               new Keyframe(0.9583F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.5833F, KeyframeAnimations.posVec(0.0F, 0.0F, 2.0F), Interpolations.LINEAR),
               new Keyframe(1.7083F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.7917F, KeyframeAnimations.posVec(0.0F, 0.0F, 2.0F), Interpolations.LINEAR),
               new Keyframe(1.9167F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 2.0F), Interpolations.LINEAR),
               new Keyframe(2.0833F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "missile",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.375F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.4583F, KeyframeAnimations.posVec(0.0F, 0.0F, 2.0F), Interpolations.LINEAR),
               new Keyframe(0.5833F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.5833F, KeyframeAnimations.posVec(0.0F, 0.0F, 2.0F), Interpolations.LINEAR),
               new Keyframe(1.7083F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "missile2",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.5833F, KeyframeAnimations.posVec(0.0F, 0.0F, 2.0F), Interpolations.LINEAR),
               new Keyframe(0.7083F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.625F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.7083F, KeyframeAnimations.posVec(0.0F, 0.0F, 2.0F), Interpolations.LINEAR),
               new Keyframe(1.8333F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "missile3",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.625F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.7083F, KeyframeAnimations.posVec(0.0F, 0.0F, 2.0F), Interpolations.LINEAR),
               new Keyframe(0.8333F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.75F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.8333F, KeyframeAnimations.posVec(0.0F, 0.0F, 2.0F), Interpolations.LINEAR),
               new Keyframe(1.9583F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "right_arm",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.125F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(15.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.375F, KeyframeAnimations.degreeVec(35.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(45.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.5833F, KeyframeAnimations.degreeVec(50.17F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.0F, KeyframeAnimations.degreeVec(47.5F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.0833F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.1667F, KeyframeAnimations.degreeVec(-15.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.25F, KeyframeAnimations.degreeVec(-65.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.3333F, KeyframeAnimations.degreeVec(-70.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.8333F, KeyframeAnimations.degreeVec(-65.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(3.2083F, KeyframeAnimations.degreeVec(-70.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(3.75F, KeyframeAnimations.degreeVec(-70.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(3.8333F, KeyframeAnimations.degreeVec(-45.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(3.9167F, KeyframeAnimations.degreeVec(-15.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(4.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "right_arm2",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(3.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(3.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 32.5F), Interpolations.LINEAR),
               new Keyframe(3.75F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 32.5F), Interpolations.LINEAR),
               new Keyframe(3.8333F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 30.0F), Interpolations.LINEAR),
               new Keyframe(3.9167F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 10.0F), Interpolations.LINEAR),
               new Keyframe(4.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "right_joint",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.125F, KeyframeAnimations.degreeVec(-20.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(-19.3309F, 2.5148F, -0.7528F), Interpolations.LINEAR),
               new Keyframe(0.375F, KeyframeAnimations.degreeVec(0.0F, 5.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(15.0F, 15.0F, -5.0F), Interpolations.LINEAR),
               new Keyframe(0.625F, KeyframeAnimations.degreeVec(35.0F, 25.0F, -10.0F), Interpolations.LINEAR),
               new Keyframe(0.75F, KeyframeAnimations.degreeVec(40.0F, 25.0F, -15.0F), Interpolations.LINEAR),
               new Keyframe(1.5F, KeyframeAnimations.degreeVec(67.5F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(3.75F, KeyframeAnimations.degreeVec(67.5F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(3.8333F, KeyframeAnimations.degreeVec(45.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(3.9167F, KeyframeAnimations.degreeVec(10.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(4.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "chainsaw",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.75F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 22.5F), Interpolations.LINEAR),
               new Keyframe(2.375F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 42.5F), Interpolations.LINEAR),
               new Keyframe(2.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 42.5F), Interpolations.LINEAR),
               new Keyframe(2.625F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 55.0F), Interpolations.LINEAR),
               new Keyframe(2.75F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 70.0F), Interpolations.LINEAR),
               new Keyframe(2.875F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 110.0F), Interpolations.LINEAR),
               new Keyframe(3.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 90.0F), Interpolations.LINEAR),
               new Keyframe(3.75F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 90.0F), Interpolations.LINEAR),
               new Keyframe(3.8333F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 45.0F), Interpolations.LINEAR),
               new Keyframe(3.9167F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 10.0F), Interpolations.LINEAR),
               new Keyframe(4.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "saw",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(4.0F, KeyframeAnimations.degreeVec(-4352.5F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .build();
   public static final AnimationDefinition LASER = Builder.withLength(4.5F)
      .addAnimation(
         "upperbody",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.125F, KeyframeAnimations.degreeVec(-5.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(-25.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.375F, KeyframeAnimations.degreeVec(-55.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.5F, KeyframeAnimations.degreeVec(-35.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.5833F, KeyframeAnimations.degreeVec(-32.22F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.6667F, KeyframeAnimations.degreeVec(-30.87F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.75F, KeyframeAnimations.degreeVec(-36.52F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.8333F, KeyframeAnimations.degreeVec(-45.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.0F, KeyframeAnimations.degreeVec(-55.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.0833F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(4.125F, KeyframeAnimations.degreeVec(7.5F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(4.375F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "upperbody",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(2.25F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.375F, KeyframeAnimations.posVec(0.0F, 0.0F, 3.0F), Interpolations.LINEAR),
               new Keyframe(2.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.75F, KeyframeAnimations.posVec(0.0F, 0.0F, 3.0F), Interpolations.LINEAR),
               new Keyframe(3.125F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "eye_blow",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, 2.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.125F, KeyframeAnimations.posVec(0.0F, -0.75F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.875F, KeyframeAnimations.posVec(0.0F, -0.75F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.25F, KeyframeAnimations.posVec(0.0F, 2.25F, 0.0F), Interpolations.LINEAR),
               new Keyframe(4.125F, KeyframeAnimations.posVec(0.0F, 2.25F, 0.0F), Interpolations.LINEAR),
               new Keyframe(4.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "rocket_luncher",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.625F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.0F, KeyframeAnimations.degreeVec(-360.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "missile",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.0833F, KeyframeAnimations.posVec(0.0F, 0.0F, 2.0F), Interpolations.LINEAR),
               new Keyframe(1.2083F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "missile2",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(1.2083F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.2917F, KeyframeAnimations.posVec(0.0F, 0.0F, 2.0F), Interpolations.LINEAR),
               new Keyframe(1.4167F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "missile3",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(1.4167F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 2.0F), Interpolations.LINEAR),
               new Keyframe(1.625F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "right_arm",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(1.875F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.0F, KeyframeAnimations.degreeVec(13.63F, 12.14F, 5.93F), Interpolations.LINEAR),
               new Keyframe(2.125F, KeyframeAnimations.degreeVec(57.27F, 24.29F, 11.87F), Interpolations.LINEAR),
               new Keyframe(2.25F, KeyframeAnimations.degreeVec(63.4009F, 36.4313F, 17.8023F), Interpolations.LINEAR),
               new Keyframe(4.25F, KeyframeAnimations.degreeVec(63.4009F, 36.4313F, 17.8023F), Interpolations.LINEAR),
               new Keyframe(4.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "right_arm_joint",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.875F, KeyframeAnimations.degreeVec(-22.5F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(4.0F, KeyframeAnimations.degreeVec(-22.5F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(4.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "right_joint",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.875F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.25F, KeyframeAnimations.degreeVec(-20.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.875F, KeyframeAnimations.degreeVec(-20.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(3.125F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .build();
   public static final AnimationDefinition IDLE = Builder.withLength(1.0F)
      .looping()
      .addAnimation(
         "upperbody",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.0833F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.2083F, KeyframeAnimations.posVec(0.0F, -0.5F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.3333F, KeyframeAnimations.posVec(0.0F, -0.75F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, -0.75F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.6667F, KeyframeAnimations.posVec(0.0F, -0.75F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.7917F, KeyframeAnimations.posVec(0.0F, -0.5F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.9167F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "rocket_luncher",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.0417F, KeyframeAnimations.posVec(0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.0833F, KeyframeAnimations.posVec(0.5F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.125F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.1667F, KeyframeAnimations.posVec(0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.2083F, KeyframeAnimations.posVec(0.5F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.2917F, KeyframeAnimations.posVec(0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.3333F, KeyframeAnimations.posVec(0.5F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.375F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.4167F, KeyframeAnimations.posVec(0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.4583F, KeyframeAnimations.posVec(0.5F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.5F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.5417F, KeyframeAnimations.posVec(0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.5833F, KeyframeAnimations.posVec(0.5F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.625F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.6667F, KeyframeAnimations.posVec(0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.7083F, KeyframeAnimations.posVec(0.5F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.75F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.7917F, KeyframeAnimations.posVec(0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.8333F, KeyframeAnimations.posVec(0.5F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.875F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.9167F, KeyframeAnimations.posVec(0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.9583F, KeyframeAnimations.posVec(0.5F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "right_arm",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.0417F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.0833F, KeyframeAnimations.posVec(-0.5F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.125F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.1667F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.2083F, KeyframeAnimations.posVec(-0.5F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.2917F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.3333F, KeyframeAnimations.posVec(-0.5F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.375F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.4167F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.4583F, KeyframeAnimations.posVec(-0.5F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.5F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.5417F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.5833F, KeyframeAnimations.posVec(-0.5F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.625F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.6667F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.7083F, KeyframeAnimations.posVec(-0.5F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.75F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.7917F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.8333F, KeyframeAnimations.posVec(-0.5F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.875F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.9167F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.9583F, KeyframeAnimations.posVec(-0.5F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.0F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "saw",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.0F, KeyframeAnimations.degreeVec(-360.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "pipe2",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.0417F, KeyframeAnimations.posVec(0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.0833F, KeyframeAnimations.posVec(0.5F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.125F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.1667F, KeyframeAnimations.posVec(0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.2083F, KeyframeAnimations.posVec(0.5F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.2917F, KeyframeAnimations.posVec(0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.3333F, KeyframeAnimations.posVec(0.5F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.375F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.4167F, KeyframeAnimations.posVec(0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.4583F, KeyframeAnimations.posVec(0.5F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.5F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.5417F, KeyframeAnimations.posVec(0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.5833F, KeyframeAnimations.posVec(0.5F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.625F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.6667F, KeyframeAnimations.posVec(0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.7083F, KeyframeAnimations.posVec(0.5F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.75F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.7917F, KeyframeAnimations.posVec(0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.8333F, KeyframeAnimations.posVec(0.5F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.875F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.9167F, KeyframeAnimations.posVec(0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.9583F, KeyframeAnimations.posVec(0.5F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "pipe",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.0417F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.0833F, KeyframeAnimations.posVec(-0.5F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.125F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.1667F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.2083F, KeyframeAnimations.posVec(-0.5F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.2917F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.3333F, KeyframeAnimations.posVec(-0.5F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.375F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.4167F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.4583F, KeyframeAnimations.posVec(-0.5F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.5F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.5417F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.5833F, KeyframeAnimations.posVec(-0.5F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.625F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.6667F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.7083F, KeyframeAnimations.posVec(-0.5F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.75F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.7917F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.8333F, KeyframeAnimations.posVec(-0.5F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.875F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.9167F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.9583F, KeyframeAnimations.posVec(-0.5F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.0F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .build();
   public static final AnimationDefinition STUN = Builder.withLength(3.0F)
      .addAnimation(
         "upperbody",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.0833F, KeyframeAnimations.degreeVec(-20.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-42.5F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(-50.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.3333F, KeyframeAnimations.degreeVec(7.5F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.4167F, KeyframeAnimations.degreeVec(42.5F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(45.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.75F, KeyframeAnimations.degreeVec(45.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.7917F, KeyframeAnimations.degreeVec(47.5F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.8333F, KeyframeAnimations.degreeVec(52.5F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.875F, KeyframeAnimations.degreeVec(15.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.9167F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.9583F, KeyframeAnimations.degreeVec(2.5F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(3.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "upperbody",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.3333F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.375F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.4167F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.4583F, KeyframeAnimations.posVec(-0.75F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.5F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.5417F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.5833F, KeyframeAnimations.posVec(-0.75F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.625F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.6667F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.7083F, KeyframeAnimations.posVec(-0.75F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.75F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.7917F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.8333F, KeyframeAnimations.posVec(-0.75F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.875F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.9167F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.9583F, KeyframeAnimations.posVec(-0.75F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.0F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.0417F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.0833F, KeyframeAnimations.posVec(-0.75F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.125F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.1667F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.2083F, KeyframeAnimations.posVec(-0.75F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.25F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.2917F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.3333F, KeyframeAnimations.posVec(-0.75F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.375F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.4167F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.4583F, KeyframeAnimations.posVec(-0.75F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.5F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.5417F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.5833F, KeyframeAnimations.posVec(-0.75F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.625F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.6667F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.7083F, KeyframeAnimations.posVec(-0.75F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.75F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.7917F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.8333F, KeyframeAnimations.posVec(-0.75F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.875F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.9167F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.9583F, KeyframeAnimations.posVec(-0.75F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.0F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.0417F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.0833F, KeyframeAnimations.posVec(-0.75F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.125F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.1667F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.2083F, KeyframeAnimations.posVec(-0.75F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.25F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.2917F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.3333F, KeyframeAnimations.posVec(-0.75F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.375F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.4167F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.4583F, KeyframeAnimations.posVec(-0.75F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.5F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.5417F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.5833F, KeyframeAnimations.posVec(-0.75F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.625F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.6667F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.7083F, KeyframeAnimations.posVec(-0.75F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.75F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "rocket_luncher",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.375F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.4167F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.4583F, KeyframeAnimations.posVec(-0.75F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.5F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.5417F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.5833F, KeyframeAnimations.posVec(-0.75F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.625F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.6667F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.7083F, KeyframeAnimations.posVec(-0.75F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.75F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.7917F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.8333F, KeyframeAnimations.posVec(-0.75F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.875F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.9167F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.9583F, KeyframeAnimations.posVec(-0.75F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.0F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.0417F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.0833F, KeyframeAnimations.posVec(-0.75F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.125F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.1667F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.2083F, KeyframeAnimations.posVec(-0.75F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.25F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.2917F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.3333F, KeyframeAnimations.posVec(-0.75F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.375F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.4167F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.4583F, KeyframeAnimations.posVec(-0.75F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.5F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.5417F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.5833F, KeyframeAnimations.posVec(-0.75F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.625F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.6667F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.7083F, KeyframeAnimations.posVec(-0.75F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.75F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.7917F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.8333F, KeyframeAnimations.posVec(-0.75F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.875F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.9167F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.9583F, KeyframeAnimations.posVec(-0.75F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.0F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.0417F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.0833F, KeyframeAnimations.posVec(-0.75F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.125F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.1667F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.2083F, KeyframeAnimations.posVec(-0.75F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.25F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.2917F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.3333F, KeyframeAnimations.posVec(-0.75F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.375F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.4167F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.4583F, KeyframeAnimations.posVec(-0.75F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.5F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.5417F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.5833F, KeyframeAnimations.posVec(-0.75F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.625F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.6667F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.7083F, KeyframeAnimations.posVec(-0.75F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.75F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "right_arm",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.3333F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.4167F, KeyframeAnimations.degreeVec(-25.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(-32.5F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.5833F, KeyframeAnimations.degreeVec(-35.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.75F, KeyframeAnimations.degreeVec(-35.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(3.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "right_arm",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.3333F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.375F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.4167F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.4583F, KeyframeAnimations.posVec(-0.75F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.5F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.5417F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.5833F, KeyframeAnimations.posVec(-0.75F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.625F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.6667F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.7083F, KeyframeAnimations.posVec(-0.75F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.75F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.7917F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.8333F, KeyframeAnimations.posVec(-0.75F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.875F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.9167F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.9583F, KeyframeAnimations.posVec(-0.75F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.0F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.0417F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.0833F, KeyframeAnimations.posVec(-0.75F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.125F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.1667F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.2083F, KeyframeAnimations.posVec(-0.75F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.25F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.2917F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.3333F, KeyframeAnimations.posVec(-0.75F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.375F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.4167F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.4583F, KeyframeAnimations.posVec(-0.75F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.5F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.5417F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.5833F, KeyframeAnimations.posVec(-0.75F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.625F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.6667F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.7083F, KeyframeAnimations.posVec(-0.75F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.75F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.7917F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.8333F, KeyframeAnimations.posVec(-0.75F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.875F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.9167F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.9583F, KeyframeAnimations.posVec(-0.75F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.0F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.0417F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.0833F, KeyframeAnimations.posVec(-0.75F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.125F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.1667F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.2083F, KeyframeAnimations.posVec(-0.75F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.25F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.2917F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.3333F, KeyframeAnimations.posVec(-0.75F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.375F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.4167F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.4583F, KeyframeAnimations.posVec(-0.75F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.5F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.5417F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.5833F, KeyframeAnimations.posVec(-0.75F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.625F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.6667F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.7083F, KeyframeAnimations.posVec(-0.75F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.75F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .build();
   public static final AnimationDefinition DEATH = Builder.withLength(1.125F)
      .addAnimation(
         "upperbody",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.0833F, KeyframeAnimations.degreeVec(-15.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-40.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(-45.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-45.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.4167F, KeyframeAnimations.degreeVec(-42.5F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(-35.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.5833F, KeyframeAnimations.degreeVec(-12.5F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.6667F, KeyframeAnimations.degreeVec(27.5F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "eye_blow",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, 2.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.375F, KeyframeAnimations.posVec(0.0F, 1.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.625F, KeyframeAnimations.posVec(0.0F, -1.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "rocket_luncher",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.0833F, KeyframeAnimations.degreeVec(-5.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-20.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(-40.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-35.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.4167F, KeyframeAnimations.degreeVec(-17.5F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(32.5F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.5833F, KeyframeAnimations.degreeVec(50.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.6667F, KeyframeAnimations.degreeVec(55.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.75F, KeyframeAnimations.degreeVec(52.5F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.7917F, KeyframeAnimations.degreeVec(45.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.8333F, KeyframeAnimations.degreeVec(32.5F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.9167F, KeyframeAnimations.degreeVec(30.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.0F, KeyframeAnimations.degreeVec(42.5F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.0417F, KeyframeAnimations.degreeVec(45.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "right_arm",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.1667F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(-2.5F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-7.5F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.4167F, KeyframeAnimations.degreeVec(-37.5F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(-87.5F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.5833F, KeyframeAnimations.degreeVec(-95.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.6667F, KeyframeAnimations.degreeVec(-97.5F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.75F, KeyframeAnimations.degreeVec(-72.5F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.8333F, KeyframeAnimations.degreeVec(-20.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.9167F, KeyframeAnimations.degreeVec(-17.5F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .build();
}
