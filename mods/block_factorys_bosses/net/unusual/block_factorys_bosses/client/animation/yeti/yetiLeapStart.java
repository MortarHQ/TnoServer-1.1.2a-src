package net.unusual.block_factorys_bosses.client.animation.yeti;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.animation.AnimationChannel.Interpolations;
import net.minecraft.client.animation.AnimationChannel.Targets;
import net.minecraft.client.animation.AnimationDefinition.Builder;

public class yetiLeapStart {
   public static final AnimationDefinition LEAP_START = Builder.withLength(0.54F)
      .looping()
      .addAnimation(
         "CTR_leg_left.002",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.46F, KeyframeAnimations.posVec(2.113F, 0.0F, -13.058F), Interpolations.LINEAR),
               new Keyframe(0.5F, KeyframeAnimations.posVec(2.045F, 0.723F, -13.054F), Interpolations.LINEAR),
               new Keyframe(0.54F, KeyframeAnimations.posVec(1.976F, 2.82F, -13.051F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "CTR_hips",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(-0.727F, 0.028F, -4.943F), Interpolations.LINEAR),
               new Keyframe(0.04F, KeyframeAnimations.degreeVec(-0.387F, 0.077F, -5.168F), Interpolations.LINEAR),
               new Keyframe(0.08F, KeyframeAnimations.degreeVec(0.923F, 0.21F, -5.088F), Interpolations.LINEAR),
               new Keyframe(0.12F, KeyframeAnimations.degreeVec(3.514F, 0.389F, -4.578F), Interpolations.LINEAR),
               new Keyframe(0.17F, KeyframeAnimations.degreeVec(7.463F, 0.559F, -3.418F), Interpolations.LINEAR),
               new Keyframe(0.21F, KeyframeAnimations.degreeVec(12.062F, 0.657F, -1.178F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(16.199F, 0.613F, 2.652F), Interpolations.LINEAR),
               new Keyframe(0.29F, KeyframeAnimations.degreeVec(18.964F, 0.434F, 5.809F), Interpolations.LINEAR),
               new Keyframe(0.33F, KeyframeAnimations.degreeVec(19.812F, 0.206F, 6.14F), Interpolations.LINEAR),
               new Keyframe(0.38F, KeyframeAnimations.degreeVec(18.825F, -0.092F, 5.032F), Interpolations.LINEAR),
               new Keyframe(0.42F, KeyframeAnimations.degreeVec(16.011F, -0.829F, 3.286F), Interpolations.LINEAR),
               new Keyframe(0.46F, KeyframeAnimations.degreeVec(11.498F, -2.26F, 1.177F), Interpolations.LINEAR),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(5.679F, -4.21F, -1.148F), Interpolations.LINEAR),
               new Keyframe(0.54F, KeyframeAnimations.degreeVec(-0.869F, -6.472F, -3.557F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "CTR_hips",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.04F, KeyframeAnimations.posVec(0.01F, -0.172F, 0.14F), Interpolations.LINEAR),
               new Keyframe(0.08F, KeyframeAnimations.posVec(0.046F, -0.745F, 0.621F), Interpolations.LINEAR),
               new Keyframe(0.12F, KeyframeAnimations.posVec(0.11F, -1.772F, 1.526F), Interpolations.LINEAR),
               new Keyframe(0.17F, KeyframeAnimations.posVec(0.193F, -3.111F, 2.818F), Interpolations.LINEAR),
               new Keyframe(0.21F, KeyframeAnimations.posVec(0.263F, -4.237F, 4.153F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.posVec(0.288F, -4.642F, 5.098F), Interpolations.LINEAR),
               new Keyframe(0.29F, KeyframeAnimations.posVec(0.141F, -3.498F, 5.688F), Interpolations.LINEAR),
               new Keyframe(0.33F, KeyframeAnimations.posVec(-0.239F, -0.046F, 6.07F), Interpolations.LINEAR),
               new Keyframe(0.38F, KeyframeAnimations.posVec(-0.758F, 5.143F, 6.217F), Interpolations.LINEAR),
               new Keyframe(0.42F, KeyframeAnimations.posVec(-1.323F, 10.959F, 6.126F), Interpolations.LINEAR),
               new Keyframe(0.46F, KeyframeAnimations.posVec(-1.843F, 16.481F, 5.83F), Interpolations.LINEAR),
               new Keyframe(0.5F, KeyframeAnimations.posVec(-2.222F, 21.281F, 5.398F), Interpolations.LINEAR),
               new Keyframe(0.54F, KeyframeAnimations.posVec(-2.369F, 25.268F, 4.917F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "CTR_chest",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.692F, 4.888F, 0.106F), Interpolations.LINEAR),
               new Keyframe(0.04F, KeyframeAnimations.degreeVec(2.461F, 5.113F, -0.613F), Interpolations.LINEAR),
               new Keyframe(0.08F, KeyframeAnimations.degreeVec(10.596F, 5.689F, -2.516F), Interpolations.LINEAR),
               new Keyframe(0.12F, KeyframeAnimations.degreeVec(18.432F, 6.461F, -5.218F), Interpolations.LINEAR),
               new Keyframe(0.17F, KeyframeAnimations.degreeVec(19.586F, 7.273F, -8.337F), Interpolations.LINEAR),
               new Keyframe(0.21F, KeyframeAnimations.degreeVec(17.86F, 7.98F, -11.475F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(14.299F, 8.456F, -14.226F), Interpolations.LINEAR),
               new Keyframe(0.29F, KeyframeAnimations.degreeVec(9.211F, 8.6F, -16.172F), Interpolations.LINEAR),
               new Keyframe(0.33F, KeyframeAnimations.degreeVec(3.185F, 8.336F, -16.898F), Interpolations.LINEAR),
               new Keyframe(0.38F, KeyframeAnimations.degreeVec(-2.94F, 7.708F, -16.141F), Interpolations.LINEAR),
               new Keyframe(0.42F, KeyframeAnimations.degreeVec(-8.283F, 6.79F, -14.374F), Interpolations.LINEAR),
               new Keyframe(0.46F, KeyframeAnimations.degreeVec(-11.981F, 5.663F, -12.283F), Interpolations.LINEAR),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(-13.388F, 4.438F, -10.545F), Interpolations.LINEAR),
               new Keyframe(0.54F, KeyframeAnimations.degreeVec(-13.64F, 3.23F, -9.816F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "CTR_head",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.412F, -4.079F, -4.432F), Interpolations.LINEAR),
               new Keyframe(0.04F, KeyframeAnimations.degreeVec(-3.767F, -3.895F, -3.632F), Interpolations.LINEAR),
               new Keyframe(0.08F, KeyframeAnimations.degreeVec(-17.225F, -3.345F, -0.756F), Interpolations.LINEAR),
               new Keyframe(0.12F, KeyframeAnimations.degreeVec(-30.107F, -1.974F, 2.925F), Interpolations.LINEAR),
               new Keyframe(0.17F, KeyframeAnimations.degreeVec(-33.893F, -0.885F, 6.679F), Interpolations.LINEAR),
               new Keyframe(0.21F, KeyframeAnimations.degreeVec(-32.543F, -0.434F, 11.153F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(-27.238F, -1.091F, 16.739F), Interpolations.LINEAR),
               new Keyframe(0.29F, KeyframeAnimations.degreeVec(-18.54F, -2.846F, 20.891F), Interpolations.LINEAR),
               new Keyframe(0.33F, KeyframeAnimations.degreeVec(-7.333F, -5.102F, 21.032F), Interpolations.LINEAR),
               new Keyframe(0.38F, KeyframeAnimations.degreeVec(4.587F, -7.318F, 18.188F), Interpolations.LINEAR),
               new Keyframe(0.42F, KeyframeAnimations.degreeVec(15.195F, -8.914F, 13.531F), Interpolations.LINEAR),
               new Keyframe(0.46F, KeyframeAnimations.degreeVec(23.934F, -9.437F, 8.069F), Interpolations.LINEAR),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(31.343F, -8.708F, 2.944F), Interpolations.LINEAR),
               new Keyframe(0.54F, KeyframeAnimations.degreeVec(38.456F, -7.164F, -1.091F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "CTR_shoulde1r_left",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.04F, KeyframeAnimations.degreeVec(-0.753F, -0.051F, -0.055F), Interpolations.LINEAR),
               new Keyframe(0.08F, KeyframeAnimations.degreeVec(-2.775F, -0.189F, -0.2F), Interpolations.LINEAR),
               new Keyframe(0.12F, KeyframeAnimations.degreeVec(-5.712F, -0.394F, -0.409F), Interpolations.LINEAR),
               new Keyframe(0.17F, KeyframeAnimations.degreeVec(-9.21F, -0.645F, -0.654F), Interpolations.LINEAR),
               new Keyframe(0.21F, KeyframeAnimations.degreeVec(-12.913F, -0.919F, -0.906F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(-16.466F, -1.197F, -1.138F), Interpolations.LINEAR),
               new Keyframe(0.29F, KeyframeAnimations.degreeVec(-19.592F, -1.46F, -1.327F), Interpolations.LINEAR),
               new Keyframe(0.33F, KeyframeAnimations.degreeVec(-22.308F, -1.707F, -1.474F), Interpolations.LINEAR),
               new Keyframe(0.38F, KeyframeAnimations.degreeVec(-24.711F, -1.938F, -1.584F), Interpolations.LINEAR),
               new Keyframe(0.42F, KeyframeAnimations.degreeVec(-26.897F, -2.155F, -1.663F), Interpolations.LINEAR),
               new Keyframe(0.46F, KeyframeAnimations.degreeVec(-28.959F, -2.358F, -1.715F), Interpolations.LINEAR),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(-31.521F, -2.593F, -1.753F), Interpolations.LINEAR),
               new Keyframe(0.54F, KeyframeAnimations.degreeVec(-34.482F, -2.844F, -1.784F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "CTR_shoulde1r_left",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(-0.112F, -0.19F, -0.487F), Interpolations.LINEAR),
               new Keyframe(0.04F, KeyframeAnimations.posVec(-0.005F, -0.233F, -0.687F), Interpolations.LINEAR),
               new Keyframe(0.08F, KeyframeAnimations.posVec(0.271F, -0.372F, -1.203F), Interpolations.LINEAR),
               new Keyframe(0.12F, KeyframeAnimations.posVec(0.649F, -0.622F, -1.91F), Interpolations.LINEAR),
               new Keyframe(0.17F, KeyframeAnimations.posVec(1.062F, -0.998F, -2.682F), Interpolations.LINEAR),
               new Keyframe(0.21F, KeyframeAnimations.posVec(1.442F, -1.514F, -3.393F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.posVec(1.723F, -2.185F, -3.918F), Interpolations.LINEAR),
               new Keyframe(0.29F, KeyframeAnimations.posVec(1.88F, -3.003F, -4.212F), Interpolations.LINEAR),
               new Keyframe(0.33F, KeyframeAnimations.posVec(2.062F, -3.862F, -4.553F), Interpolations.LINEAR),
               new Keyframe(0.38F, KeyframeAnimations.posVec(2.463F, -4.634F, -5.301F), Interpolations.LINEAR),
               new Keyframe(0.42F, KeyframeAnimations.posVec(3.273F, -5.192F, -6.815F), Interpolations.LINEAR),
               new Keyframe(0.46F, KeyframeAnimations.posVec(4.685F, -5.406F, -9.455F), Interpolations.LINEAR),
               new Keyframe(0.5F, KeyframeAnimations.posVec(6.856F, 1.46F, -16.866F), Interpolations.LINEAR),
               new Keyframe(0.54F, KeyframeAnimations.posVec(7.153F, 0.9F, -16.763F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "CTR_upperarm_left",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(-1.675F, -2.702F, -0.074F), Interpolations.LINEAR),
               new Keyframe(0.04F, KeyframeAnimations.degreeVec(-2.319F, 0.033F, 1.933F), Interpolations.LINEAR),
               new Keyframe(0.08F, KeyframeAnimations.degreeVec(-3.933F, 9.849F, 7.873F), Interpolations.LINEAR),
               new Keyframe(0.12F, KeyframeAnimations.degreeVec(-1.195F, 21.676F, 14.503F), Interpolations.LINEAR),
               new Keyframe(0.17F, KeyframeAnimations.degreeVec(6.156F, 31.037F, 22.743F), Interpolations.LINEAR),
               new Keyframe(0.21F, KeyframeAnimations.degreeVec(16.552F, 38.928F, 33.588F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(28.404F, 44.256F, 45.97F), Interpolations.LINEAR),
               new Keyframe(0.29F, KeyframeAnimations.degreeVec(34.054F, 44.321F, 50.702F), Interpolations.LINEAR),
               new Keyframe(0.33F, KeyframeAnimations.degreeVec(29.346F, 38.603F, 40.917F), Interpolations.LINEAR),
               new Keyframe(0.38F, KeyframeAnimations.degreeVec(22.837F, 28.632F, 24.383F), Interpolations.LINEAR),
               new Keyframe(0.42F, KeyframeAnimations.degreeVec(20.182F, 16.334F, 5.647F), Interpolations.LINEAR),
               new Keyframe(0.46F, KeyframeAnimations.degreeVec(24.008F, 3.72F, -26.019F), Interpolations.LINEAR),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(30.614F, -1.876F, -24.419F), Interpolations.LINEAR),
               new Keyframe(0.54F, KeyframeAnimations.degreeVec(38.715F, -4.684F, -25.249F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "CTR_lowerarm_left",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.994F, -2.337F, -0.568F), Interpolations.LINEAR),
               new Keyframe(0.04F, KeyframeAnimations.degreeVec(3.197F, -8.808F, -2.185F), Interpolations.LINEAR),
               new Keyframe(0.08F, KeyframeAnimations.degreeVec(15.531F, -29.842F, -13.194F), Interpolations.LINEAR),
               new Keyframe(0.12F, KeyframeAnimations.degreeVec(58.825F, -46.082F, -57.028F), Interpolations.LINEAR),
               new Keyframe(0.17F, KeyframeAnimations.degreeVec(128.945F, -45.624F, -128.996F), Interpolations.LINEAR),
               new Keyframe(0.21F, KeyframeAnimations.degreeVec(153.234F, -39.32F, -155.236F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(160.956F, -35.156F, -164.702F), Interpolations.LINEAR),
               new Keyframe(0.29F, KeyframeAnimations.degreeVec(163.888F, -35.668F, -169.072F), Interpolations.LINEAR),
               new Keyframe(0.33F, KeyframeAnimations.degreeVec(163.988F, -43.276F, -170.254F), Interpolations.LINEAR),
               new Keyframe(0.38F, KeyframeAnimations.degreeVec(179.445F, -60.236F, -186.468F), Interpolations.LINEAR),
               new Keyframe(0.42F, KeyframeAnimations.degreeVec(169.843F, -88.185F, -177.365F), Interpolations.LINEAR),
               new Keyframe(0.46F, KeyframeAnimations.degreeVec(169.214F, -146.264F, -177.117F), Interpolations.LINEAR),
               new Keyframe(0.54F, KeyframeAnimations.degreeVec(169.214F, -146.26F, -177.226F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "CTR_upperarm_left.001",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(3.204F, -22.398F, -1.822F), Interpolations.LINEAR),
               new Keyframe(0.04F, KeyframeAnimations.degreeVec(4.183F, -26.899F, -2.06F), Interpolations.LINEAR),
               new Keyframe(0.08F, KeyframeAnimations.degreeVec(8.03F, -43.264F, -3.33F), Interpolations.LINEAR),
               new Keyframe(0.12F, KeyframeAnimations.degreeVec(11.618F, -61.355F, -4.618F), Interpolations.LINEAR),
               new Keyframe(0.17F, KeyframeAnimations.degreeVec(12.255F, -74.546F, -4.743F), Interpolations.LINEAR),
               new Keyframe(0.21F, KeyframeAnimations.degreeVec(11.28F, -85.885F, -4.179F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(9.044F, -94.134F, -2.979F), Interpolations.LINEAR),
               new Keyframe(0.29F, KeyframeAnimations.degreeVec(6.107F, -94.337F, -1.368F), Interpolations.LINEAR),
               new Keyframe(0.33F, KeyframeAnimations.degreeVec(3.573F, -82.705F, -0.23F), Interpolations.LINEAR),
               new Keyframe(0.38F, KeyframeAnimations.degreeVec(1.837F, -63.206F, 0.192F), Interpolations.LINEAR),
               new Keyframe(0.42F, KeyframeAnimations.degreeVec(0.755F, -40.566F, 0.198F), Interpolations.LINEAR),
               new Keyframe(0.46F, KeyframeAnimations.degreeVec(-2.126F, -6.138F, -2.136F), Interpolations.LINEAR),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(-1.95F, -6.603F, -1.959F), Interpolations.LINEAR),
               new Keyframe(0.54F, KeyframeAnimations.degreeVec(-0.375F, -16.82F, -0.334F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "CTR_upperarm_left.001",
         new AnimationChannel(
            Targets.SCALE,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.787F), Interpolations.LINEAR),
               new Keyframe(0.04F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.762F), Interpolations.LINEAR),
               new Keyframe(0.08F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.698F), Interpolations.LINEAR),
               new Keyframe(0.12F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.663F), Interpolations.LINEAR),
               new Keyframe(0.17F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.634F), Interpolations.LINEAR),
               new Keyframe(0.21F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.601F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.552F), Interpolations.LINEAR),
               new Keyframe(0.29F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.443F), Interpolations.LINEAR),
               new Keyframe(0.33F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.265F), Interpolations.LINEAR),
               new Keyframe(0.38F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.105F), Interpolations.LINEAR),
               new Keyframe(0.42F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.042F), Interpolations.LINEAR),
               new Keyframe(0.46F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.252F), Interpolations.LINEAR),
               new Keyframe(0.5F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.162F), Interpolations.LINEAR),
               new Keyframe(0.54F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.136F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "CTR_shoulder_right",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.04F, KeyframeAnimations.degreeVec(-0.753F, -0.317F, 0.144F), Interpolations.LINEAR),
               new Keyframe(0.08F, KeyframeAnimations.degreeVec(-2.744F, -1.228F, 0.525F), Interpolations.LINEAR),
               new Keyframe(0.12F, KeyframeAnimations.degreeVec(-5.567F, -2.672F, 1.067F), Interpolations.LINEAR),
               new Keyframe(0.17F, KeyframeAnimations.degreeVec(-8.821F, -4.591F, 1.694F), Interpolations.LINEAR),
               new Keyframe(0.21F, KeyframeAnimations.degreeVec(-12.101F, -6.925F, 2.33F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(-15.003F, -9.614F, 2.899F), Interpolations.LINEAR),
               new Keyframe(0.29F, KeyframeAnimations.degreeVec(-17.225F, -12.58F, 3.342F), Interpolations.LINEAR),
               new Keyframe(0.33F, KeyframeAnimations.degreeVec(-18.873F, -15.679F, 3.665F), Interpolations.LINEAR),
               new Keyframe(0.38F, KeyframeAnimations.degreeVec(-20.154F, -18.747F, 3.895F), Interpolations.LINEAR),
               new Keyframe(0.42F, KeyframeAnimations.degreeVec(-21.291F, -21.745F, 4.058F), Interpolations.LINEAR),
               new Keyframe(0.46F, KeyframeAnimations.degreeVec(-22.57F, -25.132F, 4.2F), Interpolations.LINEAR),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(-24.046F, -28.916F, 4.344F), Interpolations.LINEAR),
               new Keyframe(0.54F, KeyframeAnimations.degreeVec(-24.797F, -30.813F, 4.414F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "CTR_shoulder_right",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.016F, -1.1F, -0.172F), Interpolations.LINEAR),
               new Keyframe(0.04F, KeyframeAnimations.posVec(0.006F, -1.112F, -0.263F), Interpolations.LINEAR),
               new Keyframe(0.08F, KeyframeAnimations.posVec(-0.029F, -1.154F, -0.494F), Interpolations.LINEAR),
               new Keyframe(0.12F, KeyframeAnimations.posVec(-0.094F, -1.232F, -0.805F), Interpolations.LINEAR),
               new Keyframe(0.17F, KeyframeAnimations.posVec(-0.197F, -1.353F, -1.136F), Interpolations.LINEAR),
               new Keyframe(0.21F, KeyframeAnimations.posVec(-0.343F, -1.526F, -1.427F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.posVec(-0.538F, -1.758F, -1.618F), Interpolations.LINEAR),
               new Keyframe(0.29F, KeyframeAnimations.posVec(-0.772F, -2.036F, -1.722F), Interpolations.LINEAR),
               new Keyframe(0.33F, KeyframeAnimations.posVec(-0.977F, -2.279F, -2.052F), Interpolations.LINEAR),
               new Keyframe(0.38F, KeyframeAnimations.posVec(-1.064F, -2.382F, -2.991F), Interpolations.LINEAR),
               new Keyframe(0.42F, KeyframeAnimations.posVec(1.537F, 3.488F, -9.379F), Interpolations.LINEAR),
               new Keyframe(0.46F, KeyframeAnimations.posVec(4.138F, 9.359F, -15.079F), Interpolations.LINEAR),
               new Keyframe(0.54F, KeyframeAnimations.posVec(3.322F, 7.687F, -13.971F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "CTR_upperarm_right",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.839F, -2.513F, 0.452F), Interpolations.LINEAR),
               new Keyframe(0.04F, KeyframeAnimations.degreeVec(1.325F, -2.415F, -0.865F), Interpolations.LINEAR),
               new Keyframe(0.08F, KeyframeAnimations.degreeVec(2.482F, -1.613F, -4.548F), Interpolations.LINEAR),
               new Keyframe(0.12F, KeyframeAnimations.degreeVec(5.306F, 1.229F, -5.732F), Interpolations.LINEAR),
               new Keyframe(0.17F, KeyframeAnimations.degreeVec(10.359F, 5.699F, -1.846F), Interpolations.LINEAR),
               new Keyframe(0.21F, KeyframeAnimations.degreeVec(17.049F, 9.632F, 6.432F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(23.674F, 12.032F, 15.451F), Interpolations.LINEAR),
               new Keyframe(0.29F, KeyframeAnimations.degreeVec(28.385F, 12.898F, 21.298F), Interpolations.LINEAR),
               new Keyframe(0.33F, KeyframeAnimations.degreeVec(31.974F, 11.733F, 25.92F), Interpolations.LINEAR),
               new Keyframe(0.38F, KeyframeAnimations.degreeVec(36.586F, 5.416F, 37.674F), Interpolations.LINEAR),
               new Keyframe(0.42F, KeyframeAnimations.degreeVec(37.271F, 6.689F, 35.281F), Interpolations.LINEAR),
               new Keyframe(0.46F, KeyframeAnimations.degreeVec(37.472F, 10.994F, 27.381F), Interpolations.LINEAR),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(32.682F, 8.33F, -0.88F), Interpolations.LINEAR),
               new Keyframe(0.54F, KeyframeAnimations.degreeVec(29.625F, 1.921F, -23.104F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "CTR_lowerarm_right",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(-0.021F, -1.724F, -0.014F), Interpolations.LINEAR),
               new Keyframe(0.04F, KeyframeAnimations.degreeVec(-0.456F, 3.323F, -0.112F), Interpolations.LINEAR),
               new Keyframe(0.08F, KeyframeAnimations.degreeVec(-3.201F, 23.963F, -1.647F), Interpolations.LINEAR),
               new Keyframe(0.12F, KeyframeAnimations.degreeVec(-31.283F, 45.374F, -27.725F), Interpolations.LINEAR),
               new Keyframe(0.17F, KeyframeAnimations.degreeVec(30.996F, 59.003F, 36.513F), Interpolations.LINEAR),
               new Keyframe(0.21F, KeyframeAnimations.degreeVec(25.107F, 62.226F, 32.056F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(43.708F, 58.375F, 51.401F), Interpolations.LINEAR),
               new Keyframe(0.29F, KeyframeAnimations.degreeVec(-37.11F, 42.934F, -29.245F), Interpolations.LINEAR),
               new Keyframe(0.33F, KeyframeAnimations.degreeVec(-9.201F, 24.215F, -1.63F), Interpolations.LINEAR),
               new Keyframe(0.38F, KeyframeAnimations.degreeVec(-2.023F, -15.545F, 4.406F), Interpolations.LINEAR),
               new Keyframe(0.42F, KeyframeAnimations.degreeVec(-1.17F, -24.704F, 5.527F), Interpolations.LINEAR),
               new Keyframe(0.46F, KeyframeAnimations.degreeVec(-0.876F, -27.754F, 6.309F), Interpolations.LINEAR),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(-1.362F, -14.399F, 6.642F), Interpolations.LINEAR),
               new Keyframe(0.54F, KeyframeAnimations.degreeVec(-2.775F, 10.903F, 5.925F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "CTR_upperarm_right.001",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.168F, 17.864F, 2.048F), Interpolations.LINEAR),
               new Keyframe(0.04F, KeyframeAnimations.degreeVec(0.3F, 20.841F, 2.212F), Interpolations.LINEAR),
               new Keyframe(0.08F, KeyframeAnimations.degreeVec(0.956F, 32.665F, 2.931F), Interpolations.LINEAR),
               new Keyframe(0.12F, KeyframeAnimations.degreeVec(1.315F, 42.139F, 3.332F), Interpolations.LINEAR),
               new Keyframe(0.17F, KeyframeAnimations.degreeVec(0.732F, 40.511F, 2.783F), Interpolations.LINEAR),
               new Keyframe(0.21F, KeyframeAnimations.degreeVec(-0.01F, 32.697F, 2.057F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(-0.458F, 25.046F, 1.603F), Interpolations.LINEAR),
               new Keyframe(0.29F, KeyframeAnimations.degreeVec(-0.664F, 20.661F, 1.385F), Interpolations.LINEAR),
               new Keyframe(0.33F, KeyframeAnimations.degreeVec(-0.791F, 15.362F, 1.214F), Interpolations.LINEAR),
               new Keyframe(0.38F, KeyframeAnimations.degreeVec(-0.132F, 0.488F, 0.258F), Interpolations.LINEAR),
               new Keyframe(0.42F, KeyframeAnimations.degreeVec(-1.646F, -5.693F, 1.724F), Interpolations.LINEAR),
               new Keyframe(0.46F, KeyframeAnimations.degreeVec(-1.542F, -6.681F, 1.603F), Interpolations.LINEAR),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(-9.697F, 16.459F, -6.1F), Interpolations.LINEAR),
               new Keyframe(0.54F, KeyframeAnimations.degreeVec(-18.7F, 36.754F, -15.027F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "CTR_upperarm_right.001",
         new AnimationChannel(
            Targets.SCALE,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.674F), Interpolations.LINEAR),
               new Keyframe(0.04F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.645F), Interpolations.LINEAR),
               new Keyframe(0.12F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.477F), Interpolations.LINEAR),
               new Keyframe(0.17F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.437F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.429F), Interpolations.LINEAR),
               new Keyframe(0.29F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.399F), Interpolations.LINEAR),
               new Keyframe(0.33F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.379F), Interpolations.LINEAR),
               new Keyframe(0.38F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.505F), Interpolations.LINEAR),
               new Keyframe(0.46F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.392F), Interpolations.LINEAR),
               new Keyframe(0.5F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.19F), Interpolations.LINEAR),
               new Keyframe(0.54F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.103F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "CTR_leg_left",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(-177.066F, 52.262F, 182.934F), Interpolations.LINEAR),
               new Keyframe(0.04F, KeyframeAnimations.degreeVec(-176.981F, 52.932F, 183.019F), Interpolations.LINEAR),
               new Keyframe(0.08F, KeyframeAnimations.degreeVec(-177.191F, 55.243F, 182.809F), Interpolations.LINEAR),
               new Keyframe(0.12F, KeyframeAnimations.degreeVec(-177.857F, 59.898F, 182.143F), Interpolations.LINEAR),
               new Keyframe(0.17F, KeyframeAnimations.degreeVec(-179.291F, 67.33F, 180.709F), Interpolations.LINEAR),
               new Keyframe(0.21F, KeyframeAnimations.degreeVec(-181.982F, 76.114F, 178.018F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(-186.389F, 82.945F, 173.611F), Interpolations.LINEAR),
               new Keyframe(0.29F, KeyframeAnimations.degreeVec(-190.046F, 86.011F, 169.954F), Interpolations.LINEAR),
               new Keyframe(0.33F, KeyframeAnimations.degreeVec(-189.979F, 86.208F, 170.021F), Interpolations.LINEAR),
               new Keyframe(0.38F, KeyframeAnimations.degreeVec(-188.097F, 84.51F, 171.903F), Interpolations.LINEAR),
               new Keyframe(0.42F, KeyframeAnimations.degreeVec(-185.69F, 81.466F, 174.31F), Interpolations.LINEAR),
               new Keyframe(0.46F, KeyframeAnimations.degreeVec(-182.992F, 76.844F, 177.008F), Interpolations.LINEAR),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(-179.963F, 71.181F, 180.037F), Interpolations.LINEAR),
               new Keyframe(0.54F, KeyframeAnimations.degreeVec(-177.147F, 62.258F, 182.853F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "CTR_leg_left",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.878F, -1.018F, -0.451F), Interpolations.LINEAR),
               new Keyframe(0.04F, KeyframeAnimations.posVec(0.887F, -0.907F, -0.304F), Interpolations.LINEAR),
               new Keyframe(0.08F, KeyframeAnimations.posVec(0.784F, -0.388F, 0.318F), Interpolations.LINEAR),
               new Keyframe(0.12F, KeyframeAnimations.posVec(0.489F, 0.45F, 1.665F), Interpolations.LINEAR),
               new Keyframe(0.17F, KeyframeAnimations.posVec(-0.147F, 1.12F, 3.91F), Interpolations.LINEAR),
               new Keyframe(0.21F, KeyframeAnimations.posVec(-1.291F, 0.891F, 6.532F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.posVec(-3.033F, -0.197F, 8.451F), Interpolations.LINEAR),
               new Keyframe(0.29F, KeyframeAnimations.posVec(-3.989F, -1.656F, 8.297F), Interpolations.LINEAR),
               new Keyframe(0.33F, KeyframeAnimations.posVec(-2.78F, -3.141F, 5.566F), Interpolations.LINEAR),
               new Keyframe(0.38F, KeyframeAnimations.posVec(-0.697F, -5.337F, 1.127F), Interpolations.LINEAR),
               new Keyframe(0.42F, KeyframeAnimations.posVec(1.237F, -8.58F, -3.833F), Interpolations.LINEAR),
               new Keyframe(0.46F, KeyframeAnimations.posVec(2.668F, -12.539F, -8.362F), Interpolations.LINEAR),
               new Keyframe(0.5F, KeyframeAnimations.posVec(3.617F, -16.633F, -11.209F), Interpolations.LINEAR),
               new Keyframe(0.54F, KeyframeAnimations.posVec(3.6F, -16.493F, -11.104F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "CTR_leg_right",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(-2.789F, 0.901F, 2.789F), Interpolations.LINEAR),
               new Keyframe(0.04F, KeyframeAnimations.degreeVec(-2.964F, 0.303F, 2.964F), Interpolations.LINEAR),
               new Keyframe(0.08F, KeyframeAnimations.degreeVec(-3.072F, -1.975F, 3.072F), Interpolations.LINEAR),
               new Keyframe(0.12F, KeyframeAnimations.degreeVec(-3.063F, -6.566F, 3.063F), Interpolations.LINEAR),
               new Keyframe(0.17F, KeyframeAnimations.degreeVec(-2.764F, -13.829F, 2.764F), Interpolations.LINEAR),
               new Keyframe(0.21F, KeyframeAnimations.degreeVec(-1.654F, -22.425F, 1.654F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(0.924F, -29.381F, -0.924F), Interpolations.LINEAR),
               new Keyframe(0.29F, KeyframeAnimations.degreeVec(3.529F, -34.098F, -3.529F), Interpolations.LINEAR),
               new Keyframe(0.33F, KeyframeAnimations.degreeVec(4.761F, -37.154F, -4.761F), Interpolations.LINEAR),
               new Keyframe(0.38F, KeyframeAnimations.degreeVec(5.014F, -37.332F, -5.014F), Interpolations.LINEAR),
               new Keyframe(0.42F, KeyframeAnimations.degreeVec(4.444F, -33.652F, -4.444F), Interpolations.LINEAR),
               new Keyframe(0.46F, KeyframeAnimations.degreeVec(3.386F, -26.383F, -3.386F), Interpolations.LINEAR),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(2.28F, -17.277F, -2.28F), Interpolations.LINEAR),
               new Keyframe(0.54F, KeyframeAnimations.degreeVec(0.852F, -7.871F, -0.852F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "CTR_leg_right",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.698F, 0.987F, 0.254F), Interpolations.LINEAR),
               new Keyframe(0.04F, KeyframeAnimations.posVec(0.782F, 1.215F, 0.406F), Interpolations.LINEAR),
               new Keyframe(0.08F, KeyframeAnimations.posVec(0.927F, 1.793F, 0.924F), Interpolations.LINEAR),
               new Keyframe(0.12F, KeyframeAnimations.posVec(1.114F, 2.648F, 2.01F), Interpolations.LINEAR),
               new Keyframe(0.17F, KeyframeAnimations.posVec(1.214F, 3.348F, 3.786F), Interpolations.LINEAR),
               new Keyframe(0.21F, KeyframeAnimations.posVec(0.902F, 3.192F, 5.659F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.posVec(-0.01F, 2.126F, 6.439F), Interpolations.LINEAR),
               new Keyframe(0.29F, KeyframeAnimations.posVec(-0.608F, 0.138F, 5.802F), Interpolations.LINEAR),
               new Keyframe(0.33F, KeyframeAnimations.posVec(-0.412F, -2.916F, 3.762F), Interpolations.LINEAR),
               new Keyframe(0.38F, KeyframeAnimations.posVec(0.224F, -6.446F, 0.338F), Interpolations.LINEAR),
               new Keyframe(0.42F, KeyframeAnimations.posVec(0.953F, -9.846F, -3.702F), Interpolations.LINEAR),
               new Keyframe(0.46F, KeyframeAnimations.posVec(1.386F, -12.963F, -7.318F), Interpolations.LINEAR),
               new Keyframe(0.54F, KeyframeAnimations.posVec(1.039F, -12.268F, -6.874F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "CTR_leg_right.002",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.5F, KeyframeAnimations.posVec(-2.707F, 0.0F, -2.155F), Interpolations.LINEAR),
               new Keyframe(0.54F, KeyframeAnimations.posVec(-2.884F, 0.86F, -2.239F), Interpolations.LINEAR)
            }
         )
      )
      .build();
}
