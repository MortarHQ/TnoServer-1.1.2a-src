package net.unusual.block_factorys_bosses.client.animation.yeti;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.animation.AnimationChannel.Interpolations;
import net.minecraft.client.animation.AnimationChannel.Targets;
import net.minecraft.client.animation.AnimationDefinition.Builder;

public class yetiLeapSmash {
   public static final AnimationDefinition LEAP_SMASH = Builder.withLength(1.17F)
      .looping()
      .addAnimation(
         "CTR_leg_left.002",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(1.687F, 10.705F, -13.036F), Interpolations.LINEAR),
               new Keyframe(0.04F, KeyframeAnimations.posVec(1.9F, 2.94F, -13.047F), Interpolations.LINEAR),
               new Keyframe(0.08F, KeyframeAnimations.posVec(2.113F, 0.0F, -13.058F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "CTR_hips",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(-1.047F, -12.224F, -15.674F), Interpolations.LINEAR),
               new Keyframe(0.04F, KeyframeAnimations.degreeVec(8.389F, -12.131F, -15.629F), Interpolations.LINEAR),
               new Keyframe(0.08F, KeyframeAnimations.degreeVec(19.669F, -12.072F, -15.54F), Interpolations.LINEAR),
               new Keyframe(0.12F, KeyframeAnimations.degreeVec(31.812F, -12.053F, -15.408F), Interpolations.LINEAR),
               new Keyframe(0.17F, KeyframeAnimations.degreeVec(38.984F, -12.055F, -15.237F), Interpolations.LINEAR),
               new Keyframe(0.21F, KeyframeAnimations.degreeVec(42.727F, -12.057F, -15.03F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(43.385F, -12.06F, -14.791F), Interpolations.LINEAR),
               new Keyframe(0.29F, KeyframeAnimations.degreeVec(41.299F, -12.063F, -14.523F), Interpolations.LINEAR),
               new Keyframe(0.33F, KeyframeAnimations.degreeVec(36.934F, -12.066F, -14.229F), Interpolations.LINEAR),
               new Keyframe(0.38F, KeyframeAnimations.degreeVec(31.717F, -12.069F, -13.913F), Interpolations.LINEAR),
               new Keyframe(0.42F, KeyframeAnimations.degreeVec(29.328F, -12.073F, -13.58F), Interpolations.LINEAR),
               new Keyframe(0.46F, KeyframeAnimations.degreeVec(29.407F, -12.076F, -13.231F), Interpolations.LINEAR),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(30.79F, -12.079F, -12.871F), Interpolations.LINEAR),
               new Keyframe(0.54F, KeyframeAnimations.degreeVec(31.628F, -12.083F, -12.504F), Interpolations.LINEAR),
               new Keyframe(0.58F, KeyframeAnimations.degreeVec(31.176F, -11.933F, -12.084F), Interpolations.LINEAR),
               new Keyframe(0.62F, KeyframeAnimations.degreeVec(29.952F, -11.504F, -11.576F), Interpolations.LINEAR),
               new Keyframe(0.67F, KeyframeAnimations.degreeVec(28.084F, -10.839F, -10.999F), Interpolations.LINEAR),
               new Keyframe(0.71F, KeyframeAnimations.degreeVec(25.7F, -9.979F, -10.371F), Interpolations.LINEAR),
               new Keyframe(0.75F, KeyframeAnimations.degreeVec(22.925F, -8.966F, -9.71F), Interpolations.LINEAR),
               new Keyframe(0.79F, KeyframeAnimations.degreeVec(19.883F, -7.843F, -9.033F), Interpolations.LINEAR),
               new Keyframe(0.83F, KeyframeAnimations.degreeVec(16.693F, -6.654F, -8.357F), Interpolations.LINEAR),
               new Keyframe(0.88F, KeyframeAnimations.degreeVec(13.473F, -5.442F, -7.698F), Interpolations.LINEAR),
               new Keyframe(0.92F, KeyframeAnimations.degreeVec(10.336F, -4.25F, -7.071F), Interpolations.LINEAR),
               new Keyframe(0.96F, KeyframeAnimations.degreeVec(7.392F, -3.123F, -6.494F), Interpolations.LINEAR),
               new Keyframe(1.0F, KeyframeAnimations.degreeVec(4.749F, -2.104F, -5.983F), Interpolations.LINEAR),
               new Keyframe(1.04F, KeyframeAnimations.degreeVec(2.511F, -1.236F, -5.554F), Interpolations.LINEAR),
               new Keyframe(1.08F, KeyframeAnimations.degreeVec(0.783F, -0.562F, -5.226F), Interpolations.LINEAR),
               new Keyframe(1.12F, KeyframeAnimations.degreeVec(-0.332F, -0.127F, -5.016F), Interpolations.LINEAR),
               new Keyframe(1.17F, KeyframeAnimations.degreeVec(-0.727F, 0.028F, -4.943F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "CTR_hips",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(-3.993F, 22.435F, 6.248F), Interpolations.LINEAR),
               new Keyframe(0.04F, KeyframeAnimations.posVec(-3.974F, 16.544F, 6.787F), Interpolations.LINEAR),
               new Keyframe(0.08F, KeyframeAnimations.posVec(-3.937F, 10.312F, 7.336F), Interpolations.LINEAR),
               new Keyframe(0.12F, KeyframeAnimations.posVec(-3.918F, 4.88F, 7.765F), Interpolations.LINEAR),
               new Keyframe(0.17F, KeyframeAnimations.posVec(-3.918F, 1.119F, 7.765F), Interpolations.LINEAR),
               new Keyframe(0.21F, KeyframeAnimations.posVec(-3.918F, 0.199F, 7.765F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.posVec(-3.918F, 2.449F, 7.765F), Interpolations.LINEAR),
               new Keyframe(0.29F, KeyframeAnimations.posVec(-3.918F, 4.88F, 7.765F), Interpolations.LINEAR),
               new Keyframe(0.33F, KeyframeAnimations.posVec(-3.918F, 5.715F, 7.765F), Interpolations.LINEAR),
               new Keyframe(0.38F, KeyframeAnimations.posVec(-3.918F, 5.934F, 7.765F), Interpolations.LINEAR),
               new Keyframe(0.42F, KeyframeAnimations.posVec(-3.918F, 5.762F, 7.765F), Interpolations.LINEAR),
               new Keyframe(0.5F, KeyframeAnimations.posVec(-3.918F, 5.044F, 7.765F), Interpolations.LINEAR),
               new Keyframe(0.54F, KeyframeAnimations.posVec(-3.918F, 4.88F, 7.765F), Interpolations.LINEAR),
               new Keyframe(0.58F, KeyframeAnimations.posVec(-3.868F, 4.818F, 7.666F), Interpolations.LINEAR),
               new Keyframe(0.62F, KeyframeAnimations.posVec(-3.727F, 4.643F, 7.387F), Interpolations.LINEAR),
               new Keyframe(0.67F, KeyframeAnimations.posVec(-3.51F, 4.373F, 6.957F), Interpolations.LINEAR),
               new Keyframe(0.71F, KeyframeAnimations.posVec(-3.23F, 4.024F, 6.403F), Interpolations.LINEAR),
               new Keyframe(0.75F, KeyframeAnimations.posVec(-2.902F, 3.615F, 5.752F), Interpolations.LINEAR),
               new Keyframe(0.79F, KeyframeAnimations.posVec(-2.539F, 3.162F, 5.032F), Interpolations.LINEAR),
               new Keyframe(0.83F, KeyframeAnimations.posVec(-2.155F, 2.684F, 4.27F), Interpolations.LINEAR),
               new Keyframe(0.88F, KeyframeAnimations.posVec(-1.763F, 2.196F, 3.495F), Interpolations.LINEAR),
               new Keyframe(0.92F, KeyframeAnimations.posVec(-1.379F, 1.718F, 2.733F), Interpolations.LINEAR),
               new Keyframe(0.96F, KeyframeAnimations.posVec(-1.016F, 1.265F, 2.013F), Interpolations.LINEAR),
               new Keyframe(1.0F, KeyframeAnimations.posVec(-0.687F, 0.856F, 1.362F), Interpolations.LINEAR),
               new Keyframe(1.04F, KeyframeAnimations.posVec(-0.408F, 0.507F, 0.807F), Interpolations.LINEAR),
               new Keyframe(1.08F, KeyframeAnimations.posVec(-0.191F, 0.237F, 0.377F), Interpolations.LINEAR),
               new Keyframe(1.12F, KeyframeAnimations.posVec(-0.05F, 0.062F, 0.099F), Interpolations.LINEAR),
               new Keyframe(1.17F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "CTR_chest",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(-13.012F, 6.725F, -24.048F), Interpolations.LINEAR),
               new Keyframe(0.04F, KeyframeAnimations.degreeVec(-6.965F, 8.243F, -25.114F), Interpolations.LINEAR),
               new Keyframe(0.08F, KeyframeAnimations.degreeVec(3.994F, 9.696F, -27.03F), Interpolations.LINEAR),
               new Keyframe(0.12F, KeyframeAnimations.degreeVec(9.769F, 11.11F, -28.153F), Interpolations.LINEAR),
               new Keyframe(0.17F, KeyframeAnimations.degreeVec(2.779F, 12.39F, -28.153F), Interpolations.LINEAR),
               new Keyframe(0.21F, KeyframeAnimations.degreeVec(-3.893F, 13.309F, -27.647F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(-1.893F, 13.754F, -26.104F), Interpolations.LINEAR),
               new Keyframe(0.29F, KeyframeAnimations.degreeVec(2.905F, 13.595F, -22.831F), Interpolations.LINEAR),
               new Keyframe(0.33F, KeyframeAnimations.degreeVec(8.447F, 12.757F, -19.856F), Interpolations.LINEAR),
               new Keyframe(0.38F, KeyframeAnimations.degreeVec(12.844F, 11.909F, -18.186F), Interpolations.LINEAR),
               new Keyframe(0.42F, KeyframeAnimations.degreeVec(13.866F, 11.549F, -18.646F), Interpolations.LINEAR),
               new Keyframe(0.46F, KeyframeAnimations.degreeVec(12.354F, 11.349F, -22.47F), Interpolations.LINEAR),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(10.38F, 11.222F, -26.023F), Interpolations.LINEAR),
               new Keyframe(0.54F, KeyframeAnimations.degreeVec(8.846F, 11.11F, -28.153F), Interpolations.LINEAR),
               new Keyframe(0.58F, KeyframeAnimations.degreeVec(7.764F, 10.913F, -29.174F), Interpolations.LINEAR),
               new Keyframe(0.62F, KeyframeAnimations.degreeVec(6.85F, 10.673F, -29.14F), Interpolations.LINEAR),
               new Keyframe(0.67F, KeyframeAnimations.degreeVec(6.079F, 10.396F, -28.201F), Interpolations.LINEAR),
               new Keyframe(0.71F, KeyframeAnimations.degreeVec(5.421F, 10.071F, -26.506F), Interpolations.LINEAR),
               new Keyframe(0.75F, KeyframeAnimations.degreeVec(4.843F, 9.686F, -24.205F), Interpolations.LINEAR),
               new Keyframe(0.79F, KeyframeAnimations.degreeVec(4.315F, 9.232F, -21.449F), Interpolations.LINEAR),
               new Keyframe(0.83F, KeyframeAnimations.degreeVec(3.81F, 8.707F, -18.385F), Interpolations.LINEAR),
               new Keyframe(0.88F, KeyframeAnimations.degreeVec(3.309F, 8.121F, -15.162F), Interpolations.LINEAR),
               new Keyframe(0.92F, KeyframeAnimations.degreeVec(2.805F, 7.495F, -11.923F), Interpolations.LINEAR),
               new Keyframe(0.96F, KeyframeAnimations.degreeVec(2.302F, 6.859F, -8.81F), Interpolations.LINEAR),
               new Keyframe(1.0F, KeyframeAnimations.degreeVec(1.816F, 6.251F, -5.96F), Interpolations.LINEAR),
               new Keyframe(1.04F, KeyframeAnimations.degreeVec(1.376F, 5.71F, -3.509F), Interpolations.LINEAR),
               new Keyframe(1.08F, KeyframeAnimations.degreeVec(1.017F, 5.277F, -1.591F), Interpolations.LINEAR),
               new Keyframe(1.12F, KeyframeAnimations.degreeVec(0.778F, 4.991F, -0.341F), Interpolations.LINEAR),
               new Keyframe(1.17F, KeyframeAnimations.degreeVec(0.692F, 4.888F, 0.106F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "CTR_head",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(-4.823F, -3.373F, 2.075F), Interpolations.LINEAR),
               new Keyframe(0.04F, KeyframeAnimations.degreeVec(-21.312F, -5.645F, 3.01F), Interpolations.LINEAR),
               new Keyframe(0.08F, KeyframeAnimations.degreeVec(-45.525F, -7.068F, 6.471F), Interpolations.LINEAR),
               new Keyframe(0.12F, KeyframeAnimations.degreeVec(-64.648F, -9.105F, 9.521F), Interpolations.LINEAR),
               new Keyframe(0.17F, KeyframeAnimations.degreeVec(-50.077F, -13.335F, 8.881F), Interpolations.LINEAR),
               new Keyframe(0.21F, KeyframeAnimations.degreeVec(-23.205F, -16.513F, 6.947F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(-17.694F, -16.977F, 6.409F), Interpolations.LINEAR),
               new Keyframe(0.29F, KeyframeAnimations.degreeVec(-21.92F, -16.384F, 4.807F), Interpolations.LINEAR),
               new Keyframe(0.33F, KeyframeAnimations.degreeVec(-32.342F, -14.927F, 3.384F), Interpolations.LINEAR),
               new Keyframe(0.38F, KeyframeAnimations.degreeVec(-45.459F, -13.335F, 2.74F), Interpolations.LINEAR),
               new Keyframe(0.42F, KeyframeAnimations.degreeVec(-47.269F, -12.308F, 3.497F), Interpolations.LINEAR),
               new Keyframe(0.46F, KeyframeAnimations.degreeVec(-45.118F, -10.785F, 6.96F), Interpolations.LINEAR),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(-43.715F, -9.878F, 10.229F), Interpolations.LINEAR),
               new Keyframe(0.54F, KeyframeAnimations.degreeVec(-43.533F, -9.494F, 12.286F), Interpolations.LINEAR),
               new Keyframe(0.58F, KeyframeAnimations.degreeVec(-42.291F, -9.069F, 13.371F), Interpolations.LINEAR),
               new Keyframe(0.62F, KeyframeAnimations.degreeVec(-40.283F, -8.617F, 13.574F), Interpolations.LINEAR),
               new Keyframe(0.67F, KeyframeAnimations.degreeVec(-37.631F, -8.135F, 13.051F), Interpolations.LINEAR),
               new Keyframe(0.71F, KeyframeAnimations.degreeVec(-34.446F, -7.625F, 11.958F), Interpolations.LINEAR),
               new Keyframe(0.75F, KeyframeAnimations.degreeVec(-30.834F, -7.096F, 10.445F), Interpolations.LINEAR),
               new Keyframe(0.79F, KeyframeAnimations.degreeVec(-26.905F, -6.562F, 8.649F), Interpolations.LINEAR),
               new Keyframe(0.83F, KeyframeAnimations.degreeVec(-22.777F, -6.043F, 6.692F), Interpolations.LINEAR),
               new Keyframe(0.88F, KeyframeAnimations.degreeVec(-18.581F, -5.562F, 4.68F), Interpolations.LINEAR),
               new Keyframe(0.92F, KeyframeAnimations.degreeVec(-14.454F, -5.138F, 2.701F), Interpolations.LINEAR),
               new Keyframe(0.96F, KeyframeAnimations.degreeVec(-10.544F, -4.785F, 0.833F), Interpolations.LINEAR),
               new Keyframe(1.0F, KeyframeAnimations.degreeVec(-7.003F, -4.51F, -0.858F), Interpolations.LINEAR),
               new Keyframe(1.04F, KeyframeAnimations.degreeVec(-3.984F, -4.31F, -2.302F), Interpolations.LINEAR),
               new Keyframe(1.08F, KeyframeAnimations.degreeVec(-1.641F, -4.178F, -3.43F), Interpolations.LINEAR),
               new Keyframe(1.12F, KeyframeAnimations.degreeVec(-0.125F, -4.103F, -4.167F), Interpolations.LINEAR),
               new Keyframe(1.17F, KeyframeAnimations.degreeVec(0.412F, -4.079F, -4.432F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "CTR_shoulde1r_left",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(11.115F, -163.891F, 5.664F), Interpolations.LINEAR),
               new Keyframe(0.04F, KeyframeAnimations.degreeVec(9.642F, -163.823F, 1.75F), Interpolations.LINEAR),
               new Keyframe(0.12F, KeyframeAnimations.degreeVec(110.281F, -168.906F, 175.183F), Interpolations.LINEAR),
               new Keyframe(0.54F, KeyframeAnimations.degreeVec(110.281F, -168.906F, 175.183F), Interpolations.LINEAR),
               new Keyframe(0.58F, KeyframeAnimations.degreeVec(111.169F, -168.971F, 175.244F), Interpolations.LINEAR),
               new Keyframe(0.62F, KeyframeAnimations.degreeVec(113.669F, -169.153F, 175.417F), Interpolations.LINEAR),
               new Keyframe(0.67F, KeyframeAnimations.degreeVec(117.532F, -169.434F, 175.684F), Interpolations.LINEAR),
               new Keyframe(0.71F, KeyframeAnimations.degreeVec(122.51F, -169.795F, 176.028F), Interpolations.LINEAR),
               new Keyframe(0.75F, KeyframeAnimations.degreeVec(128.357F, -170.22F, 176.432F), Interpolations.LINEAR),
               new Keyframe(0.79F, KeyframeAnimations.degreeVec(134.822F, -170.69F, 176.879F), Interpolations.LINEAR),
               new Keyframe(0.83F, KeyframeAnimations.degreeVec(141.66F, -171.187F, 177.351F), Interpolations.LINEAR),
               new Keyframe(0.88F, KeyframeAnimations.degreeVec(148.622F, -171.693F, 177.832F), Interpolations.LINEAR),
               new Keyframe(0.92F, KeyframeAnimations.degreeVec(155.459F, -172.19F, 178.304F), Interpolations.LINEAR),
               new Keyframe(0.96F, KeyframeAnimations.degreeVec(161.925F, -172.66F, 178.751F), Interpolations.LINEAR),
               new Keyframe(1.0F, KeyframeAnimations.degreeVec(167.771F, -173.085F, 179.155F), Interpolations.LINEAR),
               new Keyframe(1.04F, KeyframeAnimations.degreeVec(172.749F, -173.447F, 179.499F), Interpolations.LINEAR),
               new Keyframe(1.08F, KeyframeAnimations.degreeVec(176.612F, -173.728F, 179.766F), Interpolations.LINEAR),
               new Keyframe(1.12F, KeyframeAnimations.degreeVec(179.112F, -173.91F, 179.939F), Interpolations.LINEAR),
               new Keyframe(1.17F, KeyframeAnimations.degreeVec(180.0F, -173.974F, 180.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "CTR_shoulde1r_left",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.04F, KeyframeAnimations.posVec(7.657F, -8.638F, -15.012F), Interpolations.LINEAR),
               new Keyframe(0.12F, KeyframeAnimations.posVec(7.657F, -13.915F, -15.012F), Interpolations.LINEAR),
               new Keyframe(0.17F, KeyframeAnimations.posVec(6.779F, -10.561F, -15.768F), Interpolations.LINEAR),
               new Keyframe(0.21F, KeyframeAnimations.posVec(5.9F, -7.207F, -16.524F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.posVec(6.129F, -8.133F, -16.342F), Interpolations.LINEAR),
               new Keyframe(0.29F, KeyframeAnimations.posVec(6.656F, -10.221F, -15.914F), Interpolations.LINEAR),
               new Keyframe(0.33F, KeyframeAnimations.posVec(7.245F, -12.493F, -15.413F), Interpolations.LINEAR),
               new Keyframe(0.38F, KeyframeAnimations.posVec(7.657F, -13.915F, -15.012F), Interpolations.LINEAR),
               new Keyframe(0.42F, KeyframeAnimations.posVec(7.79F, -14.239F, -14.849F), Interpolations.LINEAR),
               new Keyframe(0.46F, KeyframeAnimations.posVec(7.775F, -14.198F, -14.867F), Interpolations.LINEAR),
               new Keyframe(0.5F, KeyframeAnimations.posVec(7.701F, -14.021F, -14.958F), Interpolations.LINEAR),
               new Keyframe(0.54F, KeyframeAnimations.posVec(7.657F, -13.915F, -15.012F), Interpolations.LINEAR),
               new Keyframe(0.58F, KeyframeAnimations.posVec(7.571F, -13.568F, -14.963F), Interpolations.LINEAR),
               new Keyframe(0.62F, KeyframeAnimations.posVec(7.314F, -12.789F, -14.669F), Interpolations.LINEAR),
               new Keyframe(0.67F, KeyframeAnimations.posVec(6.888F, -11.971F, -13.91F), Interpolations.LINEAR),
               new Keyframe(0.71F, KeyframeAnimations.posVec(6.294F, -11.508F, -12.464F), Interpolations.LINEAR),
               new Keyframe(0.75F, KeyframeAnimations.posVec(5.544F, -11.406F, -10.227F), Interpolations.LINEAR),
               new Keyframe(0.79F, KeyframeAnimations.posVec(4.687F, -11.379F, -7.552F), Interpolations.LINEAR),
               new Keyframe(0.83F, KeyframeAnimations.posVec(3.786F, -11.377F, -4.906F), Interpolations.LINEAR),
               new Keyframe(0.88F, KeyframeAnimations.posVec(2.899F, -11.35F, -2.758F), Interpolations.LINEAR),
               new Keyframe(0.92F, KeyframeAnimations.posVec(2.087F, -11.249F, -1.575F), Interpolations.LINEAR),
               new Keyframe(0.96F, KeyframeAnimations.posVec(1.401F, -10.322F, -1.117F), Interpolations.LINEAR),
               new Keyframe(1.0F, KeyframeAnimations.posVec(0.847F, -8.244F, -0.81F), Interpolations.LINEAR),
               new Keyframe(1.04F, KeyframeAnimations.posVec(0.422F, -5.603F, -0.623F), Interpolations.LINEAR),
               new Keyframe(1.08F, KeyframeAnimations.posVec(0.123F, -2.988F, -0.528F), Interpolations.LINEAR),
               new Keyframe(1.12F, KeyframeAnimations.posVec(-0.054F, -0.987F, -0.492F), Interpolations.LINEAR),
               new Keyframe(1.17F, KeyframeAnimations.posVec(-0.112F, -0.19F, -0.487F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "CTR_upperarm_left",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(32.747F, 7.311F, 47.798F), Interpolations.LINEAR),
               new Keyframe(0.04F, KeyframeAnimations.degreeVec(43.815F, -1.233F, 65.928F), Interpolations.LINEAR),
               new Keyframe(0.08F, KeyframeAnimations.degreeVec(57.768F, 28.065F, 41.623F), Interpolations.LINEAR),
               new Keyframe(0.12F, KeyframeAnimations.degreeVec(-24.261F, 3.871F, -60.363F), Interpolations.LINEAR),
               new Keyframe(0.17F, KeyframeAnimations.degreeVec(-42.858F, -3.47F, -59.187F), Interpolations.LINEAR),
               new Keyframe(0.21F, KeyframeAnimations.degreeVec(-44.245F, -7.574F, -60.841F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(-43.683F, -10.077F, -62.063F), Interpolations.LINEAR),
               new Keyframe(0.29F, KeyframeAnimations.degreeVec(-38.877F, -12.169F, -63.97F), Interpolations.LINEAR),
               new Keyframe(0.33F, KeyframeAnimations.degreeVec(-32.992F, -12.258F, -65.668F), Interpolations.LINEAR),
               new Keyframe(0.38F, KeyframeAnimations.degreeVec(-26.921F, -10.92F, -66.375F), Interpolations.LINEAR),
               new Keyframe(0.42F, KeyframeAnimations.degreeVec(-23.991F, -8.691F, -65.652F), Interpolations.LINEAR),
               new Keyframe(0.46F, KeyframeAnimations.degreeVec(-24.483F, -3.351F, -61.682F), Interpolations.LINEAR),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(-26.313F, 1.075F, -58.72F), Interpolations.LINEAR),
               new Keyframe(0.54F, KeyframeAnimations.degreeVec(-26.089F, 3.874F, -57.561F), Interpolations.LINEAR),
               new Keyframe(0.58F, KeyframeAnimations.degreeVec(-20.643F, 10.522F, -52.564F), Interpolations.LINEAR),
               new Keyframe(0.62F, KeyframeAnimations.degreeVec(-6.616F, 20.963F, -36.985F), Interpolations.LINEAR),
               new Keyframe(0.67F, KeyframeAnimations.degreeVec(15.945F, 24.924F, -7.185F), Interpolations.LINEAR),
               new Keyframe(0.71F, KeyframeAnimations.degreeVec(30.689F, 18.482F, 16.617F), Interpolations.LINEAR),
               new Keyframe(0.75F, KeyframeAnimations.degreeVec(34.839F, 15.739F, 18.899F), Interpolations.LINEAR),
               new Keyframe(0.79F, KeyframeAnimations.degreeVec(34.333F, 16.099F, 12.727F), Interpolations.LINEAR),
               new Keyframe(0.83F, KeyframeAnimations.degreeVec(31.326F, 15.27F, 5.982F), Interpolations.LINEAR),
               new Keyframe(0.88F, KeyframeAnimations.degreeVec(26.891F, 13.088F, -1.099F), Interpolations.LINEAR),
               new Keyframe(0.92F, KeyframeAnimations.degreeVec(21.951F, 9.564F, -7.588F), Interpolations.LINEAR),
               new Keyframe(0.96F, KeyframeAnimations.degreeVec(17.268F, 5.328F, -11.246F), Interpolations.LINEAR),
               new Keyframe(1.0F, KeyframeAnimations.degreeVec(12.832F, 1.559F, -11.057F), Interpolations.LINEAR),
               new Keyframe(1.04F, KeyframeAnimations.degreeVec(8.262F, -0.978F, -8.117F), Interpolations.LINEAR),
               new Keyframe(1.08F, KeyframeAnimations.degreeVec(3.71F, -2.226F, -4.274F), Interpolations.LINEAR),
               new Keyframe(1.12F, KeyframeAnimations.degreeVec(-0.066F, -2.637F, -1.241F), Interpolations.LINEAR),
               new Keyframe(1.17F, KeyframeAnimations.degreeVec(-1.675F, -2.702F, -0.074F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "CTR_lowerarm_left",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(-10.786F, -28.162F, 2.289F), Interpolations.LINEAR),
               new Keyframe(0.04F, KeyframeAnimations.degreeVec(-10.786F, -12.195F, 2.289F), Interpolations.LINEAR),
               new Keyframe(0.08F, KeyframeAnimations.degreeVec(-27.667F, -16.555F, 8.66F), Interpolations.LINEAR),
               new Keyframe(0.12F, KeyframeAnimations.degreeVec(-10.786F, -59.422F, 2.289F), Interpolations.LINEAR),
               new Keyframe(0.17F, KeyframeAnimations.degreeVec(-10.786F, -64.001F, 2.289F), Interpolations.LINEAR),
               new Keyframe(0.21F, KeyframeAnimations.degreeVec(-10.786F, -63.21F, 2.289F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(-10.786F, -59.389F, 2.289F), Interpolations.LINEAR),
               new Keyframe(0.29F, KeyframeAnimations.degreeVec(-10.786F, -53.665F, 2.289F), Interpolations.LINEAR),
               new Keyframe(0.33F, KeyframeAnimations.degreeVec(-10.786F, -49.201F, 2.289F), Interpolations.LINEAR),
               new Keyframe(0.38F, KeyframeAnimations.degreeVec(-10.786F, -45.837F, 2.289F), Interpolations.LINEAR),
               new Keyframe(0.42F, KeyframeAnimations.degreeVec(-10.786F, -45.996F, 2.289F), Interpolations.LINEAR),
               new Keyframe(0.46F, KeyframeAnimations.degreeVec(-10.786F, -51.842F, 2.289F), Interpolations.LINEAR),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(-10.786F, -57.523F, 2.289F), Interpolations.LINEAR),
               new Keyframe(0.54F, KeyframeAnimations.degreeVec(-10.786F, -60.346F, 2.289F), Interpolations.LINEAR),
               new Keyframe(0.58F, KeyframeAnimations.degreeVec(-10.774F, -69.149F, 2.305F), Interpolations.LINEAR),
               new Keyframe(0.62F, KeyframeAnimations.degreeVec(-10.775F, -85.322F, 2.386F), Interpolations.LINEAR),
               new Keyframe(0.67F, KeyframeAnimations.degreeVec(-10.79F, -96.379F, 2.536F), Interpolations.LINEAR),
               new Keyframe(0.71F, KeyframeAnimations.degreeVec(-10.855F, -89.175F, 2.794F), Interpolations.LINEAR),
               new Keyframe(0.75F, KeyframeAnimations.degreeVec(-11.43F, -68.491F, 3.609F), Interpolations.LINEAR),
               new Keyframe(0.79F, KeyframeAnimations.degreeVec(-7.744F, -50.393F, 0.203F), Interpolations.LINEAR),
               new Keyframe(0.83F, KeyframeAnimations.degreeVec(-8.934F, -38.149F, 1.721F), Interpolations.LINEAR),
               new Keyframe(0.88F, KeyframeAnimations.degreeVec(-9.035F, -24.953F, 2.173F), Interpolations.LINEAR),
               new Keyframe(0.92F, KeyframeAnimations.degreeVec(-8.949F, -12.407F, 2.442F), Interpolations.LINEAR),
               new Keyframe(0.96F, KeyframeAnimations.degreeVec(-8.659F, -3.284F, 2.555F), Interpolations.LINEAR),
               new Keyframe(1.0F, KeyframeAnimations.degreeVec(-7.951F, 1.044F, 2.456F), Interpolations.LINEAR),
               new Keyframe(1.04F, KeyframeAnimations.degreeVec(-6.458F, 1.302F, 2.033F), Interpolations.LINEAR),
               new Keyframe(1.08F, KeyframeAnimations.degreeVec(-3.883F, -0.316F, 1.172F), Interpolations.LINEAR),
               new Keyframe(1.12F, KeyframeAnimations.degreeVec(-0.693F, -1.802F, 0.041F), Interpolations.LINEAR),
               new Keyframe(1.17F, KeyframeAnimations.degreeVec(0.994F, -2.337F, -0.568F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "CTR_lowerarm_left",
         new AnimationChannel(
            Targets.SCALE,
            new Keyframe[]{
               new Keyframe(0.04F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR),
               new Keyframe(0.08F, KeyframeAnimations.scaleVec(1.0, 2.092F, 1.0), Interpolations.LINEAR),
               new Keyframe(0.12F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "CTR_upperarm_left.001",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(-7.293F, 49.813F, 17.827F), Interpolations.LINEAR),
               new Keyframe(0.04F, KeyframeAnimations.degreeVec(-4.843F, 40.577F, 23.906F), Interpolations.LINEAR),
               new Keyframe(0.08F, KeyframeAnimations.degreeVec(-15.708F, -1.777F, -10.104F), Interpolations.LINEAR),
               new Keyframe(0.12F, KeyframeAnimations.degreeVec(2.606F, 16.471F, 2.732F), Interpolations.LINEAR),
               new Keyframe(0.17F, KeyframeAnimations.degreeVec(-2.372F, -5.556F, -2.382F), Interpolations.LINEAR),
               new Keyframe(0.21F, KeyframeAnimations.degreeVec(-0.231F, -18.926F, -0.168F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(-0.269F, -18.227F, -0.214F), Interpolations.LINEAR),
               new Keyframe(0.29F, KeyframeAnimations.degreeVec(-0.303F, -17.653F, -0.253F), Interpolations.LINEAR),
               new Keyframe(0.33F, KeyframeAnimations.degreeVec(-0.356F, -16.828F, -0.313F), Interpolations.LINEAR),
               new Keyframe(0.38F, KeyframeAnimations.degreeVec(-0.448F, -15.558F, -0.416F), Interpolations.LINEAR),
               new Keyframe(0.42F, KeyframeAnimations.degreeVec(-0.875F, -11.478F, -0.868F), Interpolations.LINEAR),
               new Keyframe(0.46F, KeyframeAnimations.degreeVec(-3.448F, -3.779F, -3.459F), Interpolations.LINEAR),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(5.324F, 7.476F, 5.355F), Interpolations.LINEAR),
               new Keyframe(0.54F, KeyframeAnimations.degreeVec(2.424F, 18.123F, 2.573F), Interpolations.LINEAR),
               new Keyframe(0.58F, KeyframeAnimations.degreeVec(2.138F, 21.731F, 2.32F), Interpolations.LINEAR),
               new Keyframe(0.62F, KeyframeAnimations.degreeVec(2.938F, 15.177F, 2.844F), Interpolations.LINEAR),
               new Keyframe(0.67F, KeyframeAnimations.degreeVec(0.184F, -10.69F, -1.182F), Interpolations.LINEAR),
               new Keyframe(0.71F, KeyframeAnimations.degreeVec(2.704F, -71.247F, 0.422F), Interpolations.LINEAR),
               new Keyframe(0.75F, KeyframeAnimations.degreeVec(1.688F, -79.766F, 0.618F), Interpolations.LINEAR),
               new Keyframe(0.79F, KeyframeAnimations.degreeVec(1.489F, -65.934F, 0.327F), Interpolations.LINEAR),
               new Keyframe(0.83F, KeyframeAnimations.degreeVec(1.447F, -54.786F, 0.116F), Interpolations.LINEAR),
               new Keyframe(0.88F, KeyframeAnimations.degreeVec(1.303F, -43.997F, -0.048F), Interpolations.LINEAR),
               new Keyframe(0.92F, KeyframeAnimations.degreeVec(1.076F, -33.642F, -0.213F), Interpolations.LINEAR),
               new Keyframe(0.96F, KeyframeAnimations.degreeVec(0.86F, -25.8F, -0.412F), Interpolations.LINEAR),
               new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.831F, -21.738F, -0.629F), Interpolations.LINEAR),
               new Keyframe(1.04F, KeyframeAnimations.degreeVec(1.151F, -20.718F, -0.865F), Interpolations.LINEAR),
               new Keyframe(1.08F, KeyframeAnimations.degreeVec(1.854F, -21.288F, -1.191F), Interpolations.LINEAR),
               new Keyframe(1.12F, KeyframeAnimations.degreeVec(2.74F, -22.076F, -1.6F), Interpolations.LINEAR),
               new Keyframe(1.17F, KeyframeAnimations.degreeVec(3.205F, -22.398F, -1.822F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "CTR_upperarm_left.001",
         new AnimationChannel(
            Targets.SCALE,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 2.146F), Interpolations.LINEAR),
               new Keyframe(0.04F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.672F), Interpolations.LINEAR),
               new Keyframe(0.08F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.042F), Interpolations.LINEAR),
               new Keyframe(0.12F, KeyframeAnimations.scaleVec(1.0, 1.0, 0.798F), Interpolations.LINEAR),
               new Keyframe(0.17F, KeyframeAnimations.scaleVec(1.0, 1.0, 0.878F), Interpolations.LINEAR),
               new Keyframe(0.21F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.094F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.085F), Interpolations.LINEAR),
               new Keyframe(0.29F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.056F), Interpolations.LINEAR),
               new Keyframe(0.38F, KeyframeAnimations.scaleVec(1.0, 1.0, 0.947F), Interpolations.LINEAR),
               new Keyframe(0.46F, KeyframeAnimations.scaleVec(1.0, 1.0, 0.79F), Interpolations.LINEAR),
               new Keyframe(0.5F, KeyframeAnimations.scaleVec(1.0, 1.0, 0.765F), Interpolations.LINEAR),
               new Keyframe(0.54F, KeyframeAnimations.scaleVec(1.0, 1.0, 0.804F), Interpolations.LINEAR),
               new Keyframe(0.58F, KeyframeAnimations.scaleVec(1.0, 1.0, 0.801F), Interpolations.LINEAR),
               new Keyframe(0.62F, KeyframeAnimations.scaleVec(1.0, 1.0, 0.675F), Interpolations.LINEAR),
               new Keyframe(0.67F, KeyframeAnimations.scaleVec(1.0, 1.0, 0.403F), Interpolations.LINEAR),
               new Keyframe(0.71F, KeyframeAnimations.scaleVec(1.0, 1.0, 0.281F), Interpolations.LINEAR),
               new Keyframe(0.75F, KeyframeAnimations.scaleVec(1.0, 1.0, 0.418F), Interpolations.LINEAR),
               new Keyframe(0.79F, KeyframeAnimations.scaleVec(1.0, 1.0, 0.609F), Interpolations.LINEAR),
               new Keyframe(0.83F, KeyframeAnimations.scaleVec(1.0, 1.0, 0.821F), Interpolations.LINEAR),
               new Keyframe(0.88F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.037F), Interpolations.LINEAR),
               new Keyframe(0.92F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.238F), Interpolations.LINEAR),
               new Keyframe(0.96F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.411F), Interpolations.LINEAR),
               new Keyframe(1.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.55F), Interpolations.LINEAR),
               new Keyframe(1.04F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.651F), Interpolations.LINEAR),
               new Keyframe(1.08F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.723F), Interpolations.LINEAR),
               new Keyframe(1.12F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.77F), Interpolations.LINEAR),
               new Keyframe(1.17F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.787F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "CTR_shoulder_right",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(-7.511F, 154.921F, 0.692F), Interpolations.LINEAR),
               new Keyframe(0.04F, KeyframeAnimations.degreeVec(-9.909F, 153.026F, 5.232F), Interpolations.LINEAR),
               new Keyframe(0.12F, KeyframeAnimations.degreeVec(116.48F, 220.704F, -167.725F), Interpolations.LINEAR),
               new Keyframe(0.54F, KeyframeAnimations.degreeVec(116.48F, 220.704F, -167.725F), Interpolations.LINEAR),
               new Keyframe(0.58F, KeyframeAnimations.degreeVec(117.289F, 220.186F, -167.881F), Interpolations.LINEAR),
               new Keyframe(0.62F, KeyframeAnimations.degreeVec(119.567F, 218.726F, -168.322F), Interpolations.LINEAR),
               new Keyframe(0.67F, KeyframeAnimations.degreeVec(123.086F, 216.471F, -169.002F), Interpolations.LINEAR),
               new Keyframe(0.71F, KeyframeAnimations.degreeVec(127.622F, 213.564F, -169.878F), Interpolations.LINEAR),
               new Keyframe(0.75F, KeyframeAnimations.degreeVec(132.948F, 210.151F, -170.907F), Interpolations.LINEAR),
               new Keyframe(0.79F, KeyframeAnimations.degreeVec(138.839F, 206.376F, -172.046F), Interpolations.LINEAR),
               new Keyframe(0.83F, KeyframeAnimations.degreeVec(145.069F, 202.384F, -173.25F), Interpolations.LINEAR),
               new Keyframe(0.88F, KeyframeAnimations.degreeVec(151.411F, 198.32F, -174.475F), Interpolations.LINEAR),
               new Keyframe(0.92F, KeyframeAnimations.degreeVec(157.641F, 194.328F, -175.679F), Interpolations.LINEAR),
               new Keyframe(0.96F, KeyframeAnimations.degreeVec(163.532F, 190.553F, -176.818F), Interpolations.LINEAR),
               new Keyframe(1.0F, KeyframeAnimations.degreeVec(168.858F, 187.14F, -177.847F), Interpolations.LINEAR),
               new Keyframe(1.04F, KeyframeAnimations.degreeVec(173.394F, 184.233F, -178.723F), Interpolations.LINEAR),
               new Keyframe(1.08F, KeyframeAnimations.degreeVec(176.913F, 181.978F, -179.404F), Interpolations.LINEAR),
               new Keyframe(1.12F, KeyframeAnimations.degreeVec(179.191F, 180.519F, -179.844F), Interpolations.LINEAR),
               new Keyframe(1.17F, KeyframeAnimations.degreeVec(180.0F, 180.0F, -180.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "CTR_shoulder_right",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.12F, KeyframeAnimations.posVec(-2.329F, -3.884F, -6.293F), Interpolations.LINEAR),
               new Keyframe(0.17F, KeyframeAnimations.posVec(-1.916F, 0.566F, -6.831F), Interpolations.LINEAR),
               new Keyframe(0.21F, KeyframeAnimations.posVec(-1.503F, 5.017F, -7.368F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.posVec(-1.65F, 3.095F, -7.159F), Interpolations.LINEAR),
               new Keyframe(0.29F, KeyframeAnimations.posVec(-1.979F, -0.765F, -6.711F), Interpolations.LINEAR),
               new Keyframe(0.33F, KeyframeAnimations.posVec(-2.329F, -3.884F, -6.293F), Interpolations.LINEAR),
               new Keyframe(0.38F, KeyframeAnimations.posVec(-2.44F, -4.276F, -6.157F), Interpolations.LINEAR),
               new Keyframe(0.42F, KeyframeAnimations.posVec(-2.329F, -3.884F, -6.293F), Interpolations.LINEAR),
               new Keyframe(0.46F, KeyframeAnimations.posVec(-2.262F, -3.737F, -6.397F), Interpolations.LINEAR),
               new Keyframe(0.54F, KeyframeAnimations.posVec(-2.329F, -3.884F, -6.293F), Interpolations.LINEAR),
               new Keyframe(0.58F, KeyframeAnimations.posVec(-2.323F, -3.876F, -6.266F), Interpolations.LINEAR),
               new Keyframe(0.62F, KeyframeAnimations.posVec(-2.278F, -3.823F, -6.131F), Interpolations.LINEAR),
               new Keyframe(0.67F, KeyframeAnimations.posVec(-2.155F, -3.678F, -5.809F), Interpolations.LINEAR),
               new Keyframe(0.71F, KeyframeAnimations.posVec(-1.918F, -3.396F, -5.219F), Interpolations.LINEAR),
               new Keyframe(0.75F, KeyframeAnimations.posVec(-1.221F, -1.515F, -4.339F), Interpolations.LINEAR),
               new Keyframe(0.79F, KeyframeAnimations.posVec(-0.579F, 0.182F, -3.372F), Interpolations.LINEAR),
               new Keyframe(0.83F, KeyframeAnimations.posVec(-0.402F, 0.138F, -2.533F), Interpolations.LINEAR),
               new Keyframe(0.88F, KeyframeAnimations.posVec(-0.264F, 0.021F, -1.851F), Interpolations.LINEAR),
               new Keyframe(0.92F, KeyframeAnimations.posVec(-0.16F, -0.15F, -1.311F), Interpolations.LINEAR),
               new Keyframe(0.96F, KeyframeAnimations.posVec(-0.086F, -0.352F, -0.898F), Interpolations.LINEAR),
               new Keyframe(1.0F, KeyframeAnimations.posVec(-0.036F, -0.565F, -0.594F), Interpolations.LINEAR),
               new Keyframe(1.04F, KeyframeAnimations.posVec(-0.006F, -0.768F, -0.386F), Interpolations.LINEAR),
               new Keyframe(1.08F, KeyframeAnimations.posVec(0.01F, -0.938F, -0.257F), Interpolations.LINEAR),
               new Keyframe(1.12F, KeyframeAnimations.posVec(0.016F, -1.056F, -0.191F), Interpolations.LINEAR),
               new Keyframe(1.17F, KeyframeAnimations.posVec(0.016F, -1.1F, -0.172F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "CTR_upperarm_right",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(2.213F, -22.553F, -42.635F), Interpolations.LINEAR),
               new Keyframe(0.04F, KeyframeAnimations.degreeVec(25.136F, -2.751F, -60.585F), Interpolations.LINEAR),
               new Keyframe(0.08F, KeyframeAnimations.degreeVec(28.541F, -25.894F, -19.804F), Interpolations.LINEAR),
               new Keyframe(0.12F, KeyframeAnimations.degreeVec(11.074F, 3.847F, 51.786F), Interpolations.LINEAR),
               new Keyframe(0.17F, KeyframeAnimations.degreeVec(3.606F, -0.425F, 36.056F), Interpolations.LINEAR),
               new Keyframe(0.21F, KeyframeAnimations.degreeVec(1.602F, -2.505F, 26.64F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(1.661F, -3.291F, 26.001F), Interpolations.LINEAR),
               new Keyframe(0.29F, KeyframeAnimations.degreeVec(2.742F, -1.907F, 28.076F), Interpolations.LINEAR),
               new Keyframe(0.33F, KeyframeAnimations.degreeVec(3.868F, 0.416F, 32.346F), Interpolations.LINEAR),
               new Keyframe(0.38F, KeyframeAnimations.degreeVec(5.286F, 2.717F, 37.02F), Interpolations.LINEAR),
               new Keyframe(0.42F, KeyframeAnimations.degreeVec(6.93F, 3.792F, 40.676F), Interpolations.LINEAR),
               new Keyframe(0.46F, KeyframeAnimations.degreeVec(10.038F, 3.879F, 47.174F), Interpolations.LINEAR),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(12.183F, 3.095F, 51.447F), Interpolations.LINEAR),
               new Keyframe(0.54F, KeyframeAnimations.degreeVec(13.967F, 2.455F, 54.078F), Interpolations.LINEAR),
               new Keyframe(0.58F, KeyframeAnimations.degreeVec(16.041F, 2.27F, 57.294F), Interpolations.LINEAR),
               new Keyframe(0.62F, KeyframeAnimations.degreeVec(17.963F, 2.369F, 60.466F), Interpolations.LINEAR),
               new Keyframe(0.67F, KeyframeAnimations.degreeVec(19.92F, 2.533F, 63.282F), Interpolations.LINEAR),
               new Keyframe(0.71F, KeyframeAnimations.degreeVec(22.035F, 2.46F, 65.303F), Interpolations.LINEAR),
               new Keyframe(0.75F, KeyframeAnimations.degreeVec(23.723F, 2.81F, 63.392F), Interpolations.LINEAR),
               new Keyframe(0.79F, KeyframeAnimations.degreeVec(25.767F, 2.353F, 60.776F), Interpolations.LINEAR),
               new Keyframe(0.83F, KeyframeAnimations.degreeVec(28.102F, 1.593F, 57.102F), Interpolations.LINEAR),
               new Keyframe(0.88F, KeyframeAnimations.degreeVec(29.078F, 3.022F, 47.69F), Interpolations.LINEAR),
               new Keyframe(0.92F, KeyframeAnimations.degreeVec(27.395F, 5.477F, 35.404F), Interpolations.LINEAR),
               new Keyframe(0.96F, KeyframeAnimations.degreeVec(22.869F, 7.095F, 22.685F), Interpolations.LINEAR),
               new Keyframe(1.0F, KeyframeAnimations.degreeVec(16.711F, 6.593F, 11.689F), Interpolations.LINEAR),
               new Keyframe(1.04F, KeyframeAnimations.degreeVec(10.551F, 4.17F, 4.161F), Interpolations.LINEAR),
               new Keyframe(1.08F, KeyframeAnimations.degreeVec(5.43F, 1.135F, 0.633F), Interpolations.LINEAR),
               new Keyframe(1.12F, KeyframeAnimations.degreeVec(1.937F, -1.156F, 0.114F), Interpolations.LINEAR),
               new Keyframe(1.17F, KeyframeAnimations.degreeVec(0.657F, -1.984F, 0.431F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "CTR_lowerarm_right",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(-0.363F, 24.579F, 9.391F), Interpolations.LINEAR),
               new Keyframe(0.04F, KeyframeAnimations.degreeVec(-0.363F, 8.197F, 9.391F), Interpolations.LINEAR),
               new Keyframe(0.08F, KeyframeAnimations.degreeVec(-7.379F, -20.068F, 16.975F), Interpolations.LINEAR),
               new Keyframe(0.12F, KeyframeAnimations.degreeVec(-0.363F, 26.202F, 9.391F), Interpolations.LINEAR),
               new Keyframe(0.17F, KeyframeAnimations.degreeVec(-0.363F, 52.486F, 9.391F), Interpolations.LINEAR),
               new Keyframe(0.21F, KeyframeAnimations.degreeVec(-0.363F, 63.064F, 9.391F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(-0.363F, 60.809F, 9.391F), Interpolations.LINEAR),
               new Keyframe(0.29F, KeyframeAnimations.degreeVec(-0.363F, 53.169F, 9.391F), Interpolations.LINEAR),
               new Keyframe(0.33F, KeyframeAnimations.degreeVec(-0.363F, 44.717F, 9.391F), Interpolations.LINEAR),
               new Keyframe(0.38F, KeyframeAnimations.degreeVec(-0.363F, 37.709F, 9.391F), Interpolations.LINEAR),
               new Keyframe(0.42F, KeyframeAnimations.degreeVec(-0.363F, 33.208F, 9.391F), Interpolations.LINEAR),
               new Keyframe(0.46F, KeyframeAnimations.degreeVec(-0.363F, 27.55F, 9.391F), Interpolations.LINEAR),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(-0.363F, 24.737F, 9.391F), Interpolations.LINEAR),
               new Keyframe(0.54F, KeyframeAnimations.degreeVec(-0.363F, 22.205F, 9.391F), Interpolations.LINEAR),
               new Keyframe(0.58F, KeyframeAnimations.degreeVec(-0.535F, 17.574F, 9.17F), Interpolations.LINEAR),
               new Keyframe(0.62F, KeyframeAnimations.degreeVec(-0.908F, 12.631F, 8.639F), Interpolations.LINEAR),
               new Keyframe(0.67F, KeyframeAnimations.degreeVec(-1.332F, 7.639F, 7.932F), Interpolations.LINEAR),
               new Keyframe(0.71F, KeyframeAnimations.degreeVec(-1.734F, 2.956F, 7.117F), Interpolations.LINEAR),
               new Keyframe(0.75F, KeyframeAnimations.degreeVec(-2.539F, 5.428F, 5.933F), Interpolations.LINEAR),
               new Keyframe(0.79F, KeyframeAnimations.degreeVec(-3.417F, 6.978F, 4.626F), Interpolations.LINEAR),
               new Keyframe(0.83F, KeyframeAnimations.degreeVec(-3.921F, 5.107F, 3.538F), Interpolations.LINEAR),
               new Keyframe(0.88F, KeyframeAnimations.degreeVec(-5.177F, 9.107F, 1.883F), Interpolations.LINEAR),
               new Keyframe(0.92F, KeyframeAnimations.degreeVec(-6.889F, 14.554F, -0.184F), Interpolations.LINEAR),
               new Keyframe(0.96F, KeyframeAnimations.degreeVec(-8.224F, 18.167F, -2.037F), Interpolations.LINEAR),
               new Keyframe(1.0F, KeyframeAnimations.degreeVec(-8.055F, 18.35F, -2.734F), Interpolations.LINEAR),
               new Keyframe(1.04F, KeyframeAnimations.degreeVec(-6.096F, 14.82F, -2.09F), Interpolations.LINEAR),
               new Keyframe(1.08F, KeyframeAnimations.degreeVec(-3.3F, 8.464F, -0.977F), Interpolations.LINEAR),
               new Keyframe(1.12F, KeyframeAnimations.degreeVec(-0.941F, 1.59F, -0.227F), Interpolations.LINEAR),
               new Keyframe(1.17F, KeyframeAnimations.degreeVec(-0.021F, -1.717F, -0.014F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "CTR_lowerarm_right",
         new AnimationChannel(
            Targets.SCALE,
            new Keyframe[]{
               new Keyframe(0.04F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR),
               new Keyframe(0.08F, KeyframeAnimations.scaleVec(1.606F, 1.097F, 1.199F), Interpolations.LINEAR),
               new Keyframe(0.12F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "CTR_upperarm_right.001",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(-25.116F, -39.862F, -24.931F), Interpolations.LINEAR),
               new Keyframe(0.04F, KeyframeAnimations.degreeVec(-12.65F, -32.136F, -26.485F), Interpolations.LINEAR),
               new Keyframe(0.08F, KeyframeAnimations.degreeVec(-16.575F, 13.626F, 13.814F), Interpolations.LINEAR),
               new Keyframe(0.12F, KeyframeAnimations.degreeVec(-1.051F, 19.527F, 1.045F), Interpolations.LINEAR),
               new Keyframe(0.17F, KeyframeAnimations.degreeVec(-1.075F, 26.106F, 1.067F), Interpolations.LINEAR),
               new Keyframe(0.21F, KeyframeAnimations.degreeVec(-1.083F, 29.243F, 1.074F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(-1.088F, 31.098F, 1.078F), Interpolations.LINEAR),
               new Keyframe(0.29F, KeyframeAnimations.degreeVec(-1.088F, 31.146F, 1.078F), Interpolations.LINEAR),
               new Keyframe(0.33F, KeyframeAnimations.degreeVec(-1.082F, 28.594F, 1.073F), Interpolations.LINEAR),
               new Keyframe(0.38F, KeyframeAnimations.degreeVec(-1.073F, 25.18F, 1.065F), Interpolations.LINEAR),
               new Keyframe(0.46F, KeyframeAnimations.degreeVec(-1.059F, 21.212F, 1.052F), Interpolations.LINEAR),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(-1.056F, 20.596F, 1.05F), Interpolations.LINEAR),
               new Keyframe(0.54F, KeyframeAnimations.degreeVec(-1.055F, 20.3F, 1.048F), Interpolations.LINEAR),
               new Keyframe(0.58F, KeyframeAnimations.degreeVec(-1.041F, 18.079F, 1.042F), Interpolations.LINEAR),
               new Keyframe(0.62F, KeyframeAnimations.degreeVec(-1.003F, 13.69F, 1.021F), Interpolations.LINEAR),
               new Keyframe(0.67F, KeyframeAnimations.degreeVec(-0.908F, 7.597F, 0.943F), Interpolations.LINEAR),
               new Keyframe(0.71F, KeyframeAnimations.degreeVec(-0.147F, 0.36F, 0.196F), Interpolations.LINEAR),
               new Keyframe(0.75F, KeyframeAnimations.degreeVec(-1.618F, -5.966F, 1.689F), Interpolations.LINEAR),
               new Keyframe(0.79F, KeyframeAnimations.degreeVec(-1.295F, -12.462F, 1.38F), Interpolations.LINEAR),
               new Keyframe(0.83F, KeyframeAnimations.degreeVec(-1.234F, -16.15F, 1.327F), Interpolations.LINEAR),
               new Keyframe(0.88F, KeyframeAnimations.degreeVec(-1.21F, -13.531F, 1.374F), Interpolations.LINEAR),
               new Keyframe(0.92F, KeyframeAnimations.degreeVec(-1.335F, -6.711F, 1.688F), Interpolations.LINEAR),
               new Keyframe(0.96F, KeyframeAnimations.degreeVec(0.008F, 2.063F, 0.719F), Interpolations.LINEAR),
               new Keyframe(1.0F, KeyframeAnimations.degreeVec(-0.013F, 10.41F, 1.307F), Interpolations.LINEAR),
               new Keyframe(1.04F, KeyframeAnimations.degreeVec(0.349F, 16.237F, 1.608F), Interpolations.LINEAR),
               new Keyframe(1.08F, KeyframeAnimations.degreeVec(0.662F, 18.642F, 1.861F), Interpolations.LINEAR),
               new Keyframe(1.12F, KeyframeAnimations.degreeVec(0.804F, 18.426F, 2.01F), Interpolations.LINEAR),
               new Keyframe(1.17F, KeyframeAnimations.degreeVec(0.816F, 17.828F, 2.045F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "CTR_upperarm_right.001",
         new AnimationChannel(
            Targets.SCALE,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 2.822F), Interpolations.LINEAR),
               new Keyframe(0.04F, KeyframeAnimations.scaleVec(1.0, 1.0, 2.502F), Interpolations.LINEAR),
               new Keyframe(0.08F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.913F), Interpolations.LINEAR),
               new Keyframe(0.12F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.536F), Interpolations.LINEAR),
               new Keyframe(0.17F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.417F), Interpolations.LINEAR),
               new Keyframe(0.21F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.39F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.404F), Interpolations.LINEAR),
               new Keyframe(0.33F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.471F), Interpolations.LINEAR),
               new Keyframe(0.42F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.511F), Interpolations.LINEAR),
               new Keyframe(0.46F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.539F), Interpolations.LINEAR),
               new Keyframe(0.58F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.589F), Interpolations.LINEAR),
               new Keyframe(0.67F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.549F), Interpolations.LINEAR),
               new Keyframe(0.71F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.522F), Interpolations.LINEAR),
               new Keyframe(0.75F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.477F), Interpolations.LINEAR),
               new Keyframe(0.79F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.482F), Interpolations.LINEAR),
               new Keyframe(0.83F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.517F), Interpolations.LINEAR),
               new Keyframe(0.88F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.509F), Interpolations.LINEAR),
               new Keyframe(0.96F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.469F), Interpolations.LINEAR),
               new Keyframe(1.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.479F), Interpolations.LINEAR),
               new Keyframe(1.04F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.52F), Interpolations.LINEAR),
               new Keyframe(1.12F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.646F), Interpolations.LINEAR),
               new Keyframe(1.17F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.674F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "CTR_leg_left",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(-172.317F, 62.345F, 187.683F), Interpolations.LINEAR),
               new Keyframe(0.04F, KeyframeAnimations.degreeVec(-172.016F, 73.43F, 187.984F), Interpolations.LINEAR),
               new Keyframe(0.08F, KeyframeAnimations.degreeVec(-172.186F, 89.352F, 187.814F), Interpolations.LINEAR),
               new Keyframe(0.12F, KeyframeAnimations.degreeVec(-171.812F, 110.133F, 188.188F), Interpolations.LINEAR),
               new Keyframe(0.17F, KeyframeAnimations.degreeVec(-172.517F, 121.73F, 187.483F), Interpolations.LINEAR),
               new Keyframe(0.21F, KeyframeAnimations.degreeVec(-172.904F, 127.273F, 187.096F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(-172.392F, 126.079F, 187.608F), Interpolations.LINEAR),
               new Keyframe(0.29F, KeyframeAnimations.degreeVec(-172.174F, 121.71F, 187.826F), Interpolations.LINEAR),
               new Keyframe(0.33F, KeyframeAnimations.degreeVec(-172.548F, 116.013F, 187.452F), Interpolations.LINEAR),
               new Keyframe(0.38F, KeyframeAnimations.degreeVec(-173.033F, 109.79F, 186.967F), Interpolations.LINEAR),
               new Keyframe(0.42F, KeyframeAnimations.degreeVec(-173.45F, 107.176F, 186.55F), Interpolations.LINEAR),
               new Keyframe(0.46F, KeyframeAnimations.degreeVec(-173.844F, 107.591F, 186.156F), Interpolations.LINEAR),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(-174.242F, 109.556F, 185.758F), Interpolations.LINEAR),
               new Keyframe(0.54F, KeyframeAnimations.degreeVec(-174.633F, 110.756F, 185.367F), Interpolations.LINEAR),
               new Keyframe(0.58F, KeyframeAnimations.degreeVec(-175.056F, 110.116F, 184.944F), Interpolations.LINEAR),
               new Keyframe(0.62F, KeyframeAnimations.degreeVec(-175.553F, 108.181F, 184.447F), Interpolations.LINEAR),
               new Keyframe(0.67F, KeyframeAnimations.degreeVec(-176.065F, 105.12F, 183.935F), Interpolations.LINEAR),
               new Keyframe(0.71F, KeyframeAnimations.degreeVec(-176.54F, 101.106F, 183.46F), Interpolations.LINEAR),
               new Keyframe(0.75F, KeyframeAnimations.degreeVec(-176.938F, 96.314F, 183.062F), Interpolations.LINEAR),
               new Keyframe(0.79F, KeyframeAnimations.degreeVec(-177.24F, 90.936F, 182.76F), Interpolations.LINEAR),
               new Keyframe(0.83F, KeyframeAnimations.degreeVec(-177.437F, 85.175F, 182.563F), Interpolations.LINEAR),
               new Keyframe(0.88F, KeyframeAnimations.degreeVec(-177.537F, 79.249F, 182.463F), Interpolations.LINEAR),
               new Keyframe(0.92F, KeyframeAnimations.degreeVec(-177.551F, 73.386F, 182.449F), Interpolations.LINEAR),
               new Keyframe(0.96F, KeyframeAnimations.degreeVec(-177.5F, 67.818F, 182.5F), Interpolations.LINEAR),
               new Keyframe(1.0F, KeyframeAnimations.degreeVec(-177.404F, 62.777F, 182.596F), Interpolations.LINEAR),
               new Keyframe(1.04F, KeyframeAnimations.degreeVec(-177.288F, 58.487F, 182.712F), Interpolations.LINEAR),
               new Keyframe(1.08F, KeyframeAnimations.degreeVec(-177.178F, 55.166F, 182.822F), Interpolations.LINEAR),
               new Keyframe(1.12F, KeyframeAnimations.degreeVec(-177.097F, 53.022F, 182.903F), Interpolations.LINEAR),
               new Keyframe(1.17F, KeyframeAnimations.degreeVec(-177.066F, 52.262F, 182.934F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "CTR_leg_left",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.65F, -7.886F, -8.657F), Interpolations.LINEAR),
               new Keyframe(0.04F, KeyframeAnimations.posVec(0.613F, -7.973F, -9.292F), Interpolations.LINEAR),
               new Keyframe(0.08F, KeyframeAnimations.posVec(0.62F, -7.76F, -9.323F), Interpolations.LINEAR),
               new Keyframe(0.12F, KeyframeAnimations.posVec(1.418F, -7.172F, -3.36F), Interpolations.LINEAR),
               new Keyframe(0.17F, KeyframeAnimations.posVec(1.543F, -7.59F, 0.313F), Interpolations.LINEAR),
               new Keyframe(0.21F, KeyframeAnimations.posVec(1.532F, -8.141F, 1.161F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.posVec(1.505F, -7.698F, -0.73F), Interpolations.LINEAR),
               new Keyframe(0.29F, KeyframeAnimations.posVec(1.436F, -7.258F, -2.861F), Interpolations.LINEAR),
               new Keyframe(0.33F, KeyframeAnimations.posVec(1.409F, -7.188F, -3.527F), Interpolations.LINEAR),
               new Keyframe(0.42F, KeyframeAnimations.posVec(1.409F, -7.188F, -3.527F), Interpolations.LINEAR),
               new Keyframe(0.46F, KeyframeAnimations.posVec(1.411F, -7.172F, -3.416F), Interpolations.LINEAR),
               new Keyframe(0.5F, KeyframeAnimations.posVec(1.414F, -7.123F, -2.939F), Interpolations.LINEAR),
               new Keyframe(0.54F, KeyframeAnimations.posVec(1.411F, -7.111F, -2.665F), Interpolations.LINEAR),
               new Keyframe(0.58F, KeyframeAnimations.posVec(1.402F, -7.082F, -2.471F), Interpolations.LINEAR),
               new Keyframe(0.62F, KeyframeAnimations.posVec(1.386F, -6.995F, -2.092F), Interpolations.LINEAR),
               new Keyframe(0.67F, KeyframeAnimations.posVec(1.355F, -6.821F, -1.583F), Interpolations.LINEAR),
               new Keyframe(0.71F, KeyframeAnimations.posVec(1.306F, -6.528F, -1.006F), Interpolations.LINEAR),
               new Keyframe(0.75F, KeyframeAnimations.posVec(1.241F, -6.097F, -0.434F), Interpolations.LINEAR),
               new Keyframe(0.79F, KeyframeAnimations.posVec(1.164F, -5.53F, 0.066F), Interpolations.LINEAR),
               new Keyframe(0.83F, KeyframeAnimations.posVec(1.082F, -4.853F, 0.435F), Interpolations.LINEAR),
               new Keyframe(0.88F, KeyframeAnimations.posVec(1.006F, -4.113F, 0.638F), Interpolations.LINEAR),
               new Keyframe(0.92F, KeyframeAnimations.posVec(0.943F, -3.37F, 0.668F), Interpolations.LINEAR),
               new Keyframe(0.96F, KeyframeAnimations.posVec(0.898F, -2.68F, 0.548F), Interpolations.LINEAR),
               new Keyframe(1.0F, KeyframeAnimations.posVec(0.874F, -2.088F, 0.324F), Interpolations.LINEAR),
               new Keyframe(1.04F, KeyframeAnimations.posVec(0.866F, -1.62F, 0.054F), Interpolations.LINEAR),
               new Keyframe(1.08F, KeyframeAnimations.posVec(0.869F, -1.286F, -0.2F), Interpolations.LINEAR),
               new Keyframe(1.12F, KeyframeAnimations.posVec(0.875F, -1.086F, -0.383F), Interpolations.LINEAR),
               new Keyframe(1.17F, KeyframeAnimations.posVec(0.878F, -1.018F, -0.451F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "CTR_leg_right",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(-3.575F, -5.344F, 3.575F), Interpolations.LINEAR),
               new Keyframe(0.04F, KeyframeAnimations.degreeVec(-4.297F, -16.748F, 4.297F), Interpolations.LINEAR),
               new Keyframe(0.08F, KeyframeAnimations.degreeVec(-5.362F, -30.802F, 5.362F), Interpolations.LINEAR),
               new Keyframe(0.12F, KeyframeAnimations.degreeVec(-6.175F, -43.973F, 6.175F), Interpolations.LINEAR),
               new Keyframe(0.17F, KeyframeAnimations.degreeVec(-7.133F, -52.21F, 7.134F), Interpolations.LINEAR),
               new Keyframe(0.21F, KeyframeAnimations.degreeVec(-7.577F, -56.64F, 7.577F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(-7.267F, -57.249F, 7.267F), Interpolations.LINEAR),
               new Keyframe(0.29F, KeyframeAnimations.degreeVec(-6.557F, -54.818F, 6.557F), Interpolations.LINEAR),
               new Keyframe(0.33F, KeyframeAnimations.degreeVec(-5.7F, -50.02F, 5.7F), Interpolations.LINEAR),
               new Keyframe(0.38F, KeyframeAnimations.degreeVec(-4.878F, -44.323F, 4.878F), Interpolations.LINEAR),
               new Keyframe(0.42F, KeyframeAnimations.degreeVec(-4.396F, -41.772F, 4.396F), Interpolations.LINEAR),
               new Keyframe(0.46F, KeyframeAnimations.degreeVec(-4.128F, -41.955F, 4.128F), Interpolations.LINEAR),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(-3.954F, -43.593F, 3.954F), Interpolations.LINEAR),
               new Keyframe(0.54F, KeyframeAnimations.degreeVec(-3.72F, -44.627F, 3.72F), Interpolations.LINEAR),
               new Keyframe(0.58F, KeyframeAnimations.degreeVec(-3.392F, -44.084F, 3.392F), Interpolations.LINEAR),
               new Keyframe(0.62F, KeyframeAnimations.degreeVec(-3.046F, -42.436F, 3.046F), Interpolations.LINEAR),
               new Keyframe(0.67F, KeyframeAnimations.degreeVec(-2.715F, -39.851F, 2.715F), Interpolations.LINEAR),
               new Keyframe(0.71F, KeyframeAnimations.degreeVec(-2.426F, -36.503F, 2.426F), Interpolations.LINEAR),
               new Keyframe(0.75F, KeyframeAnimations.degreeVec(-2.203F, -32.571F, 2.203F), Interpolations.LINEAR),
               new Keyframe(0.79F, KeyframeAnimations.degreeVec(-2.057F, -28.236F, 2.057F), Interpolations.LINEAR),
               new Keyframe(0.83F, KeyframeAnimations.degreeVec(-1.992F, -23.679F, 1.992F), Interpolations.LINEAR),
               new Keyframe(0.88F, KeyframeAnimations.degreeVec(-2.003F, -19.079F, 2.003F), Interpolations.LINEAR),
               new Keyframe(0.92F, KeyframeAnimations.degreeVec(-2.078F, -14.611F, 2.078F), Interpolations.LINEAR),
               new Keyframe(0.96F, KeyframeAnimations.degreeVec(-2.202F, -10.439F, 2.202F), Interpolations.LINEAR),
               new Keyframe(1.0F, KeyframeAnimations.degreeVec(-2.354F, -6.715F, 2.354F), Interpolations.LINEAR),
               new Keyframe(1.04F, KeyframeAnimations.degreeVec(-2.513F, -3.585F, 2.513F), Interpolations.LINEAR),
               new Keyframe(1.08F, KeyframeAnimations.degreeVec(-2.653F, -1.184F, 2.653F), Interpolations.LINEAR),
               new Keyframe(1.12F, KeyframeAnimations.degreeVec(-2.752F, 0.356F, 2.752F), Interpolations.LINEAR),
               new Keyframe(1.17F, KeyframeAnimations.degreeVec(-2.789F, 0.901F, 2.789F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "CTR_leg_right",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(-1.994F, -9.224F, -5.435F), Interpolations.LINEAR),
               new Keyframe(0.04F, KeyframeAnimations.posVec(-1.394F, -9.554F, -5.676F), Interpolations.LINEAR),
               new Keyframe(0.08F, KeyframeAnimations.posVec(0.077F, -5.152F, -1.023F), Interpolations.LINEAR),
               new Keyframe(0.12F, KeyframeAnimations.posVec(0.962F, -2.389F, 3.98F), Interpolations.LINEAR),
               new Keyframe(0.17F, KeyframeAnimations.posVec(1.797F, -2.161F, 7.979F), Interpolations.LINEAR),
               new Keyframe(0.21F, KeyframeAnimations.posVec(1.965F, -2.748F, 9.095F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.posVec(1.417F, -3.303F, 6.94F), Interpolations.LINEAR),
               new Keyframe(0.29F, KeyframeAnimations.posVec(0.859F, -3.52F, 4.464F), Interpolations.LINEAR),
               new Keyframe(0.33F, KeyframeAnimations.posVec(0.628F, -3.301F, 3.385F), Interpolations.LINEAR),
               new Keyframe(0.38F, KeyframeAnimations.posVec(0.508F, -2.956F, 2.79F), Interpolations.LINEAR),
               new Keyframe(0.42F, KeyframeAnimations.posVec(0.474F, -2.731F, 2.707F), Interpolations.LINEAR),
               new Keyframe(0.46F, KeyframeAnimations.posVec(0.483F, -2.603F, 2.966F), Interpolations.LINEAR),
               new Keyframe(0.5F, KeyframeAnimations.posVec(0.494F, -2.591F, 3.327F), Interpolations.LINEAR),
               new Keyframe(0.54F, KeyframeAnimations.posVec(0.467F, -2.63F, 3.472F), Interpolations.LINEAR),
               new Keyframe(0.58F, KeyframeAnimations.posVec(0.418F, -2.59F, 3.411F), Interpolations.LINEAR),
               new Keyframe(0.62F, KeyframeAnimations.posVec(0.384F, -2.422F, 3.357F), Interpolations.LINEAR),
               new Keyframe(0.67F, KeyframeAnimations.posVec(0.363F, -2.147F, 3.291F), Interpolations.LINEAR),
               new Keyframe(0.71F, KeyframeAnimations.posVec(0.355F, -1.791F, 3.193F), Interpolations.LINEAR),
               new Keyframe(0.75F, KeyframeAnimations.posVec(0.358F, -1.379F, 3.042F), Interpolations.LINEAR),
               new Keyframe(0.79F, KeyframeAnimations.posVec(0.373F, -0.944F, 2.827F), Interpolations.LINEAR),
               new Keyframe(0.83F, KeyframeAnimations.posVec(0.4F, -0.514F, 2.546F), Interpolations.LINEAR),
               new Keyframe(0.88F, KeyframeAnimations.posVec(0.437F, -0.119F, 2.207F), Interpolations.LINEAR),
               new Keyframe(0.92F, KeyframeAnimations.posVec(0.481F, 0.222F, 1.831F), Interpolations.LINEAR),
               new Keyframe(0.96F, KeyframeAnimations.posVec(0.53F, 0.495F, 1.443F), Interpolations.LINEAR),
               new Keyframe(1.0F, KeyframeAnimations.posVec(0.58F, 0.699F, 1.071F), Interpolations.LINEAR),
               new Keyframe(1.04F, KeyframeAnimations.posVec(0.626F, 0.839F, 0.743F), Interpolations.LINEAR),
               new Keyframe(1.08F, KeyframeAnimations.posVec(0.664F, 0.926F, 0.483F), Interpolations.LINEAR),
               new Keyframe(1.12F, KeyframeAnimations.posVec(0.689F, 0.973F, 0.314F), Interpolations.LINEAR),
               new Keyframe(1.17F, KeyframeAnimations.posVec(0.698F, 0.987F, 0.254F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "CTR_leg_right.002",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(-3.127F, 2.173F, -2.354F), Interpolations.LINEAR),
               new Keyframe(0.04F, KeyframeAnimations.posVec(-2.707F, 0.0F, -2.155F), Interpolations.LINEAR)
            }
         )
      )
      .build();
}
