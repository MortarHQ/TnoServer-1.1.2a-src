package net.unusual.block_factorys_bosses.client.animation.yeti;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.animation.AnimationChannel.Interpolations;
import net.minecraft.client.animation.AnimationChannel.Targets;
import net.minecraft.client.animation.AnimationDefinition.Builder;

public class yetiGetHit {
   public static final AnimationDefinition GET_HIT = Builder.withLength(1.38F)
      .looping()
      .addAnimation(
         "CTR_leg_left.002",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.08F, KeyframeAnimations.posVec(2.113F, 0.0F, -13.058F), Interpolations.LINEAR),
               new Keyframe(0.12F, KeyframeAnimations.posVec(2.742F, 0.0F, -6.235F), Interpolations.LINEAR),
               new Keyframe(0.17F, KeyframeAnimations.posVec(3.909F, 0.0F, 6.437F), Interpolations.LINEAR),
               new Keyframe(0.21F, KeyframeAnimations.posVec(4.537F, 0.0F, 13.261F), Interpolations.LINEAR),
               new Keyframe(0.58F, KeyframeAnimations.posVec(4.537F, 0.0F, 13.261F), Interpolations.LINEAR),
               new Keyframe(0.62F, KeyframeAnimations.posVec(4.403F, 0.0F, 11.803F), Interpolations.LINEAR),
               new Keyframe(0.67F, KeyframeAnimations.posVec(4.057F, 0.0F, 8.043F), Interpolations.LINEAR),
               new Keyframe(0.71F, KeyframeAnimations.posVec(3.583F, 0.0F, 2.902F), Interpolations.LINEAR),
               new Keyframe(0.75F, KeyframeAnimations.posVec(3.067F, 0.0F, -2.699F), Interpolations.LINEAR),
               new Keyframe(0.79F, KeyframeAnimations.posVec(2.594F, 0.0F, -7.84F), Interpolations.LINEAR),
               new Keyframe(0.83F, KeyframeAnimations.posVec(2.247F, 0.0F, -11.6F), Interpolations.LINEAR),
               new Keyframe(0.88F, KeyframeAnimations.posVec(2.113F, 0.0F, -13.058F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "CTR_hips",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(-0.727F, 0.028F, -4.943F), Interpolations.LINEAR),
               new Keyframe(0.04F, KeyframeAnimations.degreeVec(-15.443F, -6.231F, -6.002F), Interpolations.LINEAR),
               new Keyframe(0.08F, KeyframeAnimations.degreeVec(-26.873F, -11.286F, -6.818F), Interpolations.LINEAR),
               new Keyframe(0.12F, KeyframeAnimations.degreeVec(-33.137F, -14.656F, -7.257F), Interpolations.LINEAR),
               new Keyframe(0.17F, KeyframeAnimations.degreeVec(-35.053F, -15.878F, -7.289F), Interpolations.LINEAR),
               new Keyframe(0.21F, KeyframeAnimations.degreeVec(-35.448F, -15.763F, -7.245F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(-35.098F, -15.46F, -7.175F), Interpolations.LINEAR),
               new Keyframe(0.29F, KeyframeAnimations.degreeVec(-34.284F, -15.014F, -7.09F), Interpolations.LINEAR),
               new Keyframe(0.38F, KeyframeAnimations.degreeVec(-31.754F, -13.802F, -6.892F), Interpolations.LINEAR),
               new Keyframe(0.42F, KeyframeAnimations.degreeVec(-30.164F, -13.069F, -6.783F), Interpolations.LINEAR),
               new Keyframe(0.46F, KeyframeAnimations.degreeVec(-28.406F, -12.267F, -6.67F), Interpolations.LINEAR),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(-26.505F, -11.403F, -6.553F), Interpolations.LINEAR),
               new Keyframe(0.54F, KeyframeAnimations.degreeVec(-24.476F, -10.485F, -6.433F), Interpolations.LINEAR),
               new Keyframe(0.58F, KeyframeAnimations.degreeVec(-22.332F, -9.516F, -6.309F), Interpolations.LINEAR),
               new Keyframe(0.62F, KeyframeAnimations.degreeVec(-20.08F, -8.5F, -6.182F), Interpolations.LINEAR),
               new Keyframe(0.67F, KeyframeAnimations.degreeVec(-17.725F, -7.44F, -6.052F), Interpolations.LINEAR),
               new Keyframe(0.71F, KeyframeAnimations.degreeVec(-15.27F, -6.336F, -5.919F), Interpolations.LINEAR),
               new Keyframe(0.75F, KeyframeAnimations.degreeVec(-12.713F, -5.189F, -5.781F), Interpolations.LINEAR),
               new Keyframe(0.79F, KeyframeAnimations.degreeVec(-10.044F, -3.997F, -5.64F), Interpolations.LINEAR),
               new Keyframe(0.83F, KeyframeAnimations.degreeVec(-7.245F, -2.755F, -5.492F), Interpolations.LINEAR),
               new Keyframe(0.88F, KeyframeAnimations.degreeVec(-4.262F, -1.445F, -5.335F), Interpolations.LINEAR),
               new Keyframe(0.92F, KeyframeAnimations.degreeVec(-0.76F, 0.028F, -5.164F), Interpolations.LINEAR),
               new Keyframe(0.96F, KeyframeAnimations.degreeVec(2.136F, 1.06F, -4.943F), Interpolations.LINEAR),
               new Keyframe(1.0F, KeyframeAnimations.degreeVec(1.352F, 0.859F, -4.723F), Interpolations.LINEAR),
               new Keyframe(1.04F, KeyframeAnimations.degreeVec(-0.703F, 0.028F, -4.777F), Interpolations.LINEAR),
               new Keyframe(1.08F, KeyframeAnimations.degreeVec(-1.072F, -0.196F, -4.942F), Interpolations.LINEAR),
               new Keyframe(1.12F, KeyframeAnimations.degreeVec(-0.731F, 0.028F, -4.969F), Interpolations.LINEAR),
               new Keyframe(1.38F, KeyframeAnimations.degreeVec(-0.727F, 0.028F, -4.943F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "CTR_hips",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.04F, KeyframeAnimations.posVec(0.548F, 0.0F, 4.868F), Interpolations.LINEAR),
               new Keyframe(0.08F, KeyframeAnimations.posVec(1.017F, 0.0F, 10.082F), Interpolations.LINEAR),
               new Keyframe(0.12F, KeyframeAnimations.posVec(1.224F, 0.0F, 13.456F), Interpolations.LINEAR),
               new Keyframe(0.17F, KeyframeAnimations.posVec(1.216F, 0.0F, 14.892F), Interpolations.LINEAR),
               new Keyframe(0.21F, KeyframeAnimations.posVec(1.194F, 0.0F, 15.949F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.posVec(1.161F, 0.0F, 16.675F), Interpolations.LINEAR),
               new Keyframe(0.29F, KeyframeAnimations.posVec(1.12F, 0.0F, 17.108F), Interpolations.LINEAR),
               new Keyframe(0.33F, KeyframeAnimations.posVec(1.072F, 0.0F, 17.279F), Interpolations.LINEAR),
               new Keyframe(0.38F, KeyframeAnimations.posVec(1.019F, 0.0F, 17.214F), Interpolations.LINEAR),
               new Keyframe(0.42F, KeyframeAnimations.posVec(0.96F, 0.0F, 16.931F), Interpolations.LINEAR),
               new Keyframe(0.46F, KeyframeAnimations.posVec(0.897F, 0.0F, 16.448F), Interpolations.LINEAR),
               new Keyframe(0.5F, KeyframeAnimations.posVec(0.83F, 0.0F, 15.778F), Interpolations.LINEAR),
               new Keyframe(0.54F, KeyframeAnimations.posVec(0.759F, 0.0F, 14.933F), Interpolations.LINEAR),
               new Keyframe(0.58F, KeyframeAnimations.posVec(0.686F, 0.0F, 13.922F), Interpolations.LINEAR),
               new Keyframe(0.62F, KeyframeAnimations.posVec(0.609F, 0.0F, 12.751F), Interpolations.LINEAR),
               new Keyframe(0.67F, KeyframeAnimations.posVec(0.529F, 0.0F, 11.425F), Interpolations.LINEAR),
               new Keyframe(0.71F, KeyframeAnimations.posVec(0.447F, 0.0F, 9.947F), Interpolations.LINEAR),
               new Keyframe(0.75F, KeyframeAnimations.posVec(0.363F, 0.0F, 8.316F), Interpolations.LINEAR),
               new Keyframe(0.79F, KeyframeAnimations.posVec(0.276F, 0.0F, 6.527F), Interpolations.LINEAR),
               new Keyframe(0.83F, KeyframeAnimations.posVec(0.186F, 0.0F, 4.57F), Interpolations.LINEAR),
               new Keyframe(0.88F, KeyframeAnimations.posVec(0.094F, 0.0F, 2.417F), Interpolations.LINEAR),
               new Keyframe(0.92F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.96F, KeyframeAnimations.posVec(-0.055F, 0.0F, -2.061F), Interpolations.LINEAR),
               new Keyframe(1.0F, KeyframeAnimations.posVec(-0.061F, 0.0F, -2.786F), Interpolations.LINEAR),
               new Keyframe(1.04F, KeyframeAnimations.posVec(-0.04F, 0.0F, -1.867F), Interpolations.LINEAR),
               new Keyframe(1.08F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.12F, KeyframeAnimations.posVec(0.01F, 0.0F, 0.346F), Interpolations.LINEAR),
               new Keyframe(1.17F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "CTR_chest",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.692F, 4.888F, 0.106F), Interpolations.LINEAR),
               new Keyframe(0.04F, KeyframeAnimations.degreeVec(10.662F, 3.821F, -6.517F), Interpolations.LINEAR),
               new Keyframe(0.08F, KeyframeAnimations.degreeVec(21.434F, 2.278F, -10.491F), Interpolations.LINEAR),
               new Keyframe(0.12F, KeyframeAnimations.degreeVec(30.339F, 0.654F, -12.055F), Interpolations.LINEAR),
               new Keyframe(0.17F, KeyframeAnimations.degreeVec(34.62F, -0.948F, -11.174F), Interpolations.LINEAR),
               new Keyframe(0.21F, KeyframeAnimations.degreeVec(34.315F, -2.474F, -8.832F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(33.04F, -3.889F, -5.428F), Interpolations.LINEAR),
               new Keyframe(0.29F, KeyframeAnimations.degreeVec(30.962F, -5.169F, -1.353F), Interpolations.LINEAR),
               new Keyframe(0.33F, KeyframeAnimations.degreeVec(28.24F, -6.222F, 3.004F), Interpolations.LINEAR),
               new Keyframe(0.38F, KeyframeAnimations.degreeVec(25.052F, -6.966F, 7.25F), Interpolations.LINEAR),
               new Keyframe(0.42F, KeyframeAnimations.degreeVec(21.583F, -7.354F, 10.992F), Interpolations.LINEAR),
               new Keyframe(0.46F, KeyframeAnimations.degreeVec(18.007F, -7.374F, 13.83F), Interpolations.LINEAR),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(14.455F, -7.04F, 15.37F), Interpolations.LINEAR),
               new Keyframe(0.54F, KeyframeAnimations.degreeVec(10.991F, -6.36F, 15.743F), Interpolations.LINEAR),
               new Keyframe(0.58F, KeyframeAnimations.degreeVec(7.612F, -5.426F, 15.544F), Interpolations.LINEAR),
               new Keyframe(0.62F, KeyframeAnimations.degreeVec(4.292F, -4.377F, 14.95F), Interpolations.LINEAR),
               new Keyframe(0.67F, KeyframeAnimations.degreeVec(1.067F, -3.255F, 14.063F), Interpolations.LINEAR),
               new Keyframe(0.71F, KeyframeAnimations.degreeVec(-2.004F, -2.082F, 12.942F), Interpolations.LINEAR),
               new Keyframe(0.75F, KeyframeAnimations.degreeVec(-4.824F, -0.878F, 11.625F), Interpolations.LINEAR),
               new Keyframe(0.79F, KeyframeAnimations.degreeVec(-7.246F, 0.342F, 10.137F), Interpolations.LINEAR),
               new Keyframe(0.83F, KeyframeAnimations.degreeVec(-9.02F, 1.554F, 8.491F), Interpolations.LINEAR),
               new Keyframe(0.88F, KeyframeAnimations.degreeVec(-9.677F, 2.73F, 6.693F), Interpolations.LINEAR),
               new Keyframe(0.92F, KeyframeAnimations.degreeVec(-8.087F, 3.818F, 4.738F), Interpolations.LINEAR),
               new Keyframe(0.96F, KeyframeAnimations.degreeVec(1.16F, 4.706F, 2.599F), Interpolations.LINEAR),
               new Keyframe(1.0F, KeyframeAnimations.degreeVec(8.682F, 4.888F, 0.106F), Interpolations.LINEAR),
               new Keyframe(1.04F, KeyframeAnimations.degreeVec(7.169F, 4.268F, -1.966F), Interpolations.LINEAR),
               new Keyframe(1.08F, KeyframeAnimations.degreeVec(3.755F, 4.266F, -2.104F), Interpolations.LINEAR),
               new Keyframe(1.12F, KeyframeAnimations.degreeVec(0.408F, 4.55F, -1.12F), Interpolations.LINEAR),
               new Keyframe(1.17F, KeyframeAnimations.degreeVec(-0.507F, 4.888F, 0.106F), Interpolations.LINEAR),
               new Keyframe(1.21F, KeyframeAnimations.degreeVec(0.21F, 5.016F, 0.562F), Interpolations.LINEAR),
               new Keyframe(1.25F, KeyframeAnimations.degreeVec(0.746F, 4.952F, 0.333F), Interpolations.LINEAR),
               new Keyframe(1.29F, KeyframeAnimations.degreeVec(0.692F, 4.888F, 0.106F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "CTR_head",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.412F, -4.079F, -4.432F), Interpolations.LINEAR),
               new Keyframe(0.04F, KeyframeAnimations.degreeVec(29.125F, -4.46F, -8.178F), Interpolations.LINEAR),
               new Keyframe(0.08F, KeyframeAnimations.degreeVec(47.887F, -1.946F, -13.704F), Interpolations.LINEAR),
               new Keyframe(0.12F, KeyframeAnimations.degreeVec(53.033F, -0.759F, -18.04F), Interpolations.LINEAR),
               new Keyframe(0.17F, KeyframeAnimations.degreeVec(52.095F, -4.397F, -17.678F), Interpolations.LINEAR),
               new Keyframe(0.21F, KeyframeAnimations.degreeVec(53.728F, -11.696F, -14.288F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(54.33F, -19.857F, -11.635F), Interpolations.LINEAR),
               new Keyframe(0.29F, KeyframeAnimations.degreeVec(54.605F, -24.482F, -12.973F), Interpolations.LINEAR),
               new Keyframe(0.33F, KeyframeAnimations.degreeVec(55.277F, -25.066F, -18.069F), Interpolations.LINEAR),
               new Keyframe(0.38F, KeyframeAnimations.degreeVec(55.791F, -24.037F, -23.898F), Interpolations.LINEAR),
               new Keyframe(0.42F, KeyframeAnimations.degreeVec(55.742F, -21.403F, -29.606F), Interpolations.LINEAR),
               new Keyframe(0.46F, KeyframeAnimations.degreeVec(54.744F, -17.358F, -34.389F), Interpolations.LINEAR),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(52.583F, -12.251F, -37.611F), Interpolations.LINEAR),
               new Keyframe(0.54F, KeyframeAnimations.degreeVec(49.37F, -6.693F, -39.305F), Interpolations.LINEAR),
               new Keyframe(0.58F, KeyframeAnimations.degreeVec(45.389F, -1.265F, -40.036F), Interpolations.LINEAR),
               new Keyframe(0.62F, KeyframeAnimations.degreeVec(40.812F, 3.726F, -39.972F), Interpolations.LINEAR),
               new Keyframe(0.67F, KeyframeAnimations.degreeVec(35.799F, 7.929F, -39.148F), Interpolations.LINEAR),
               new Keyframe(0.71F, KeyframeAnimations.degreeVec(30.475F, 10.998F, -37.545F), Interpolations.LINEAR),
               new Keyframe(0.75F, KeyframeAnimations.degreeVec(25.018F, 12.312F, -34.916F), Interpolations.LINEAR),
               new Keyframe(0.79F, KeyframeAnimations.degreeVec(19.603F, 11.831F, -31.314F), Interpolations.LINEAR),
               new Keyframe(0.83F, KeyframeAnimations.degreeVec(14.091F, 9.926F, -27.034F), Interpolations.LINEAR),
               new Keyframe(0.88F, KeyframeAnimations.degreeVec(8.116F, 6.89F, -22.218F), Interpolations.LINEAR),
               new Keyframe(0.92F, KeyframeAnimations.degreeVec(0.601F, 3.133F, -16.753F), Interpolations.LINEAR),
               new Keyframe(0.96F, KeyframeAnimations.degreeVec(-11.526F, -1.12F, -10.196F), Interpolations.LINEAR),
               new Keyframe(1.0F, KeyframeAnimations.degreeVec(-9.734F, -3.252F, -3.069F), Interpolations.LINEAR),
               new Keyframe(1.04F, KeyframeAnimations.degreeVec(3.431F, -2.927F, -0.438F), Interpolations.LINEAR),
               new Keyframe(1.08F, KeyframeAnimations.degreeVec(6.579F, -2.905F, -0.677F), Interpolations.LINEAR),
               new Keyframe(1.12F, KeyframeAnimations.degreeVec(6.253F, -3.284F, -2.223F), Interpolations.LINEAR),
               new Keyframe(1.17F, KeyframeAnimations.degreeVec(3.583F, -4.026F, -4.198F), Interpolations.LINEAR),
               new Keyframe(1.21F, KeyframeAnimations.degreeVec(0.987F, -4.29F, -4.914F), Interpolations.LINEAR),
               new Keyframe(1.25F, KeyframeAnimations.degreeVec(0.402F, -4.185F, -4.636F), Interpolations.LINEAR),
               new Keyframe(1.29F, KeyframeAnimations.degreeVec(0.412F, -4.079F, -4.432F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "CTR_head",
         new AnimationChannel(Targets.POSITION, new Keyframe[]{new Keyframe(1.12F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)})
      )
      .addAnimation(
         "CTR_shoulde1r_left",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(-0.112F, -0.19F, -0.487F), Interpolations.LINEAR),
               new Keyframe(0.04F, KeyframeAnimations.posVec(0.113F, 3.196F, -6.147F), Interpolations.LINEAR),
               new Keyframe(0.08F, KeyframeAnimations.posVec(0.337F, 6.582F, -12.418F), Interpolations.LINEAR),
               new Keyframe(0.12F, KeyframeAnimations.posVec(-0.112F, -0.19F, -13.532F), Interpolations.LINEAR),
               new Keyframe(0.17F, KeyframeAnimations.posVec(-0.739F, 0.083F, -14.444F), Interpolations.LINEAR),
               new Keyframe(0.21F, KeyframeAnimations.posVec(-1.301F, 0.824F, -15.174F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.posVec(-1.798F, 1.917F, -15.742F), Interpolations.LINEAR),
               new Keyframe(0.29F, KeyframeAnimations.posVec(-2.23F, 3.243F, -16.17F), Interpolations.LINEAR),
               new Keyframe(0.33F, KeyframeAnimations.posVec(-2.597F, 4.686F, -16.476F), Interpolations.LINEAR),
               new Keyframe(0.38F, KeyframeAnimations.posVec(-2.897F, 6.129F, -16.681F), Interpolations.LINEAR),
               new Keyframe(0.42F, KeyframeAnimations.posVec(-3.132F, 7.455F, -16.806F), Interpolations.LINEAR),
               new Keyframe(0.46F, KeyframeAnimations.posVec(-3.3F, 8.548F, -16.87F), Interpolations.LINEAR),
               new Keyframe(0.5F, KeyframeAnimations.posVec(-3.401F, 9.289F, -16.893F), Interpolations.LINEAR),
               new Keyframe(0.54F, KeyframeAnimations.posVec(-3.435F, 9.562F, -16.897F), Interpolations.LINEAR),
               new Keyframe(0.58F, KeyframeAnimations.posVec(-3.333F, 9.557F, -16.883F), Interpolations.LINEAR),
               new Keyframe(0.62F, KeyframeAnimations.posVec(-3.065F, 9.526F, -16.788F), Interpolations.LINEAR),
               new Keyframe(0.67F, KeyframeAnimations.posVec(-2.681F, 9.44F, -16.531F), Interpolations.LINEAR),
               new Keyframe(0.71F, KeyframeAnimations.posVec(-2.235F, 9.273F, -16.03F), Interpolations.LINEAR),
               new Keyframe(0.75F, KeyframeAnimations.posVec(-1.779F, 8.998F, -15.203F), Interpolations.LINEAR),
               new Keyframe(0.79F, KeyframeAnimations.posVec(-1.366F, 8.588F, -13.97F), Interpolations.LINEAR),
               new Keyframe(0.83F, KeyframeAnimations.posVec(-1.049F, 8.015F, -12.25F), Interpolations.LINEAR),
               new Keyframe(0.88F, KeyframeAnimations.posVec(-0.861F, 5.84F, -9.315F), Interpolations.LINEAR),
               new Keyframe(0.92F, KeyframeAnimations.posVec(-0.765F, 1.917F, -5.441F), Interpolations.LINEAR),
               new Keyframe(0.96F, KeyframeAnimations.posVec(-0.704F, -1.841F, -2.05F), Interpolations.LINEAR),
               new Keyframe(1.0F, KeyframeAnimations.posVec(-0.621F, -3.519F, -0.565F), Interpolations.LINEAR),
               new Keyframe(1.04F, KeyframeAnimations.posVec(-0.478F, -2.999F, -0.52F), Interpolations.LINEAR),
               new Keyframe(1.08F, KeyframeAnimations.posVec(-0.31F, -1.854F, -0.497F), Interpolations.LINEAR),
               new Keyframe(1.12F, KeyframeAnimations.posVec(-0.171F, -0.71F, -0.488F), Interpolations.LINEAR),
               new Keyframe(1.17F, KeyframeAnimations.posVec(-0.112F, -0.19F, -0.487F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "CTR_upperarm_left",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(-1.675F, -2.702F, -0.074F), Interpolations.LINEAR),
               new Keyframe(0.04F, KeyframeAnimations.degreeVec(-10.522F, 2.922F, 7.651F), Interpolations.LINEAR),
               new Keyframe(0.08F, KeyframeAnimations.degreeVec(-18.774F, 3.661F, 8.338F), Interpolations.LINEAR),
               new Keyframe(0.12F, KeyframeAnimations.degreeVec(-16.744F, -8.225F, -8.051F), Interpolations.LINEAR),
               new Keyframe(0.17F, KeyframeAnimations.degreeVec(-18.274F, -12.216F, -11.568F), Interpolations.LINEAR),
               new Keyframe(0.21F, KeyframeAnimations.degreeVec(-18.863F, -14.993F, -9.249F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(-20.557F, -16.556F, -2.732F), Interpolations.LINEAR),
               new Keyframe(0.29F, KeyframeAnimations.degreeVec(-21.928F, -19.806F, 1.293F), Interpolations.LINEAR),
               new Keyframe(0.33F, KeyframeAnimations.degreeVec(-22.275F, -24.587F, 2.084F), Interpolations.LINEAR),
               new Keyframe(0.38F, KeyframeAnimations.degreeVec(-22.01F, -28.952F, 3.159F), Interpolations.LINEAR),
               new Keyframe(0.42F, KeyframeAnimations.degreeVec(-21.192F, -32.444F, 4.456F), Interpolations.LINEAR),
               new Keyframe(0.46F, KeyframeAnimations.degreeVec(-20.039F, -34.59F, 6.004F), Interpolations.LINEAR),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(-18.867F, -34.93F, 7.914F), Interpolations.LINEAR),
               new Keyframe(0.54F, KeyframeAnimations.degreeVec(-17.915F, -33.775F, 10.04F), Interpolations.LINEAR),
               new Keyframe(0.58F, KeyframeAnimations.degreeVec(-17.111F, -31.853F, 12.228F), Interpolations.LINEAR),
               new Keyframe(0.62F, KeyframeAnimations.degreeVec(-16.218F, -29.316F, 14.522F), Interpolations.LINEAR),
               new Keyframe(0.67F, KeyframeAnimations.degreeVec(-15.102F, -26.407F, 16.734F), Interpolations.LINEAR),
               new Keyframe(0.71F, KeyframeAnimations.degreeVec(-13.68F, -23.305F, 18.68F), Interpolations.LINEAR),
               new Keyframe(0.75F, KeyframeAnimations.degreeVec(-11.891F, -20.15F, 20.146F), Interpolations.LINEAR),
               new Keyframe(0.79F, KeyframeAnimations.degreeVec(-11.822F, -15.724F, 21.809F), Interpolations.LINEAR),
               new Keyframe(0.83F, KeyframeAnimations.degreeVec(-11.625F, -11.462F, 21.186F), Interpolations.LINEAR),
               new Keyframe(0.88F, KeyframeAnimations.degreeVec(-6.726F, -11.18F, 12.872F), Interpolations.LINEAR),
               new Keyframe(0.92F, KeyframeAnimations.degreeVec(-0.562F, -11.029F, 1.555F), Interpolations.LINEAR),
               new Keyframe(0.96F, KeyframeAnimations.degreeVec(-2.314F, -3.934F, -0.881F), Interpolations.LINEAR),
               new Keyframe(1.0F, KeyframeAnimations.degreeVec(-4.1F, 2.095F, -0.722F), Interpolations.LINEAR),
               new Keyframe(1.04F, KeyframeAnimations.degreeVec(-3.062F, 2.133F, -1.121F), Interpolations.LINEAR),
               new Keyframe(1.08F, KeyframeAnimations.degreeVec(-2.442F, 0.259F, -1.23F), Interpolations.LINEAR),
               new Keyframe(1.12F, KeyframeAnimations.degreeVec(-1.253F, -2.039F, -1.172F), Interpolations.LINEAR),
               new Keyframe(1.17F, KeyframeAnimations.degreeVec(-0.835F, -3.63F, -0.919F), Interpolations.LINEAR),
               new Keyframe(1.21F, KeyframeAnimations.degreeVec(-1.374F, -3.593F, -0.472F), Interpolations.LINEAR),
               new Keyframe(1.25F, KeyframeAnimations.degreeVec(-1.729F, -2.92F, -0.073F), Interpolations.LINEAR),
               new Keyframe(1.29F, KeyframeAnimations.degreeVec(-1.675F, -2.702F, -0.074F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "CTR_lowerarm_left",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.994F, -2.337F, -0.568F), Interpolations.LINEAR),
               new Keyframe(0.04F, KeyframeAnimations.degreeVec(15.741F, -24.159F, -11.569F), Interpolations.LINEAR),
               new Keyframe(0.08F, KeyframeAnimations.degreeVec(40.223F, -36.45F, -33.762F), Interpolations.LINEAR),
               new Keyframe(0.12F, KeyframeAnimations.degreeVec(17.985F, -26.058F, -13.489F), Interpolations.LINEAR),
               new Keyframe(0.21F, KeyframeAnimations.degreeVec(19.82F, -27.439F, -15.086F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(24.34F, -30.302F, -19.099F), Interpolations.LINEAR),
               new Keyframe(0.29F, KeyframeAnimations.degreeVec(26.778F, -31.585F, -21.302F), Interpolations.LINEAR),
               new Keyframe(0.33F, KeyframeAnimations.degreeVec(26.409F, -31.401F, -20.967F), Interpolations.LINEAR),
               new Keyframe(0.38F, KeyframeAnimations.degreeVec(25.359F, -30.859F, -20.017F), Interpolations.LINEAR),
               new Keyframe(0.42F, KeyframeAnimations.degreeVec(23.774F, -29.981F, -18.591F), Interpolations.LINEAR),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(20.249F, -27.742F, -15.461F), Interpolations.LINEAR),
               new Keyframe(0.54F, KeyframeAnimations.degreeVec(18.588F, -26.528F, -14.011F), Interpolations.LINEAR),
               new Keyframe(0.58F, KeyframeAnimations.degreeVec(17.174F, -25.4F, -12.79F), Interpolations.LINEAR),
               new Keyframe(0.62F, KeyframeAnimations.degreeVec(16.08F, -24.462F, -11.857F), Interpolations.LINEAR),
               new Keyframe(0.67F, KeyframeAnimations.degreeVec(14.93F, -23.409F, -10.885F), Interpolations.LINEAR),
               new Keyframe(0.71F, KeyframeAnimations.degreeVec(13.455F, -21.949F, -9.657F), Interpolations.LINEAR),
               new Keyframe(0.75F, KeyframeAnimations.degreeVec(11.476F, -19.772F, -8.044F), Interpolations.LINEAR),
               new Keyframe(0.79F, KeyframeAnimations.degreeVec(13.267F, -21.754F, -9.502F), Interpolations.LINEAR),
               new Keyframe(0.83F, KeyframeAnimations.degreeVec(15.588F, -24.021F, -11.44F), Interpolations.LINEAR),
               new Keyframe(0.88F, KeyframeAnimations.degreeVec(7.225F, -14.076F, -4.754F), Interpolations.LINEAR),
               new Keyframe(0.92F, KeyframeAnimations.degreeVec(-0.842F, 2.092F, 0.455F), Interpolations.LINEAR),
               new Keyframe(0.96F, KeyframeAnimations.degreeVec(1.842F, -4.221F, -1.078F), Interpolations.LINEAR),
               new Keyframe(1.0F, KeyframeAnimations.degreeVec(5.308F, -10.951F, -3.369F), Interpolations.LINEAR),
               new Keyframe(1.04F, KeyframeAnimations.degreeVec(3.089F, -6.817F, -1.867F), Interpolations.LINEAR),
               new Keyframe(1.08F, KeyframeAnimations.degreeVec(1.773F, -4.071F, -1.036F), Interpolations.LINEAR),
               new Keyframe(1.12F, KeyframeAnimations.degreeVec(0.106F, -0.256F, -0.059F), Interpolations.LINEAR),
               new Keyframe(1.17F, KeyframeAnimations.degreeVec(-0.284F, 0.694F, 0.156F), Interpolations.LINEAR),
               new Keyframe(1.21F, KeyframeAnimations.degreeVec(0.547F, -1.304F, -0.309F), Interpolations.LINEAR),
               new Keyframe(1.25F, KeyframeAnimations.degreeVec(1.088F, -2.551F, -0.624F), Interpolations.LINEAR),
               new Keyframe(1.29F, KeyframeAnimations.degreeVec(0.994F, -2.337F, -0.568F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "CTR_upperarm_left.001",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(3.204F, -22.398F, -1.822F), Interpolations.LINEAR),
               new Keyframe(0.04F, KeyframeAnimations.degreeVec(6.812F, -21.776F, -2.678F), Interpolations.LINEAR),
               new Keyframe(0.08F, KeyframeAnimations.degreeVec(9.532F, -15.172F, -2.512F), Interpolations.LINEAR),
               new Keyframe(0.12F, KeyframeAnimations.degreeVec(39.939F, 2.363F, 33.748F), Interpolations.LINEAR),
               new Keyframe(0.17F, KeyframeAnimations.degreeVec(10.623F, 5.538F, 4.866F), Interpolations.LINEAR),
               new Keyframe(0.21F, KeyframeAnimations.degreeVec(10.983F, 5.365F, 4.779F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(70.268F, 2.091F, 62.22F), Interpolations.LINEAR),
               new Keyframe(0.29F, KeyframeAnimations.degreeVec(141.923F, 2.131F, 133.075F), Interpolations.LINEAR),
               new Keyframe(0.33F, KeyframeAnimations.degreeVec(193.086F, -0.8F, 184.807F), Interpolations.LINEAR),
               new Keyframe(0.38F, KeyframeAnimations.degreeVec(190.293F, -2.967F, 182.539F), Interpolations.LINEAR),
               new Keyframe(0.42F, KeyframeAnimations.degreeVec(189.434F, -4.187F, 182.087F), Interpolations.LINEAR),
               new Keyframe(0.46F, KeyframeAnimations.degreeVec(189.314F, -4.161F, 182.173F), Interpolations.LINEAR),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(190.242F, -2.646F, 183.056F), Interpolations.LINEAR),
               new Keyframe(0.54F, KeyframeAnimations.degreeVec(195.33F, -0.103F, 187.956F), Interpolations.LINEAR),
               new Keyframe(0.58F, KeyframeAnimations.degreeVec(170.762F, 2.85F, 163.16F), Interpolations.LINEAR),
               new Keyframe(0.62F, KeyframeAnimations.degreeVec(182.841F, 6.065F, 174.948F), Interpolations.LINEAR),
               new Keyframe(0.67F, KeyframeAnimations.degreeVec(184.658F, 9.565F, 176.494F), Interpolations.LINEAR),
               new Keyframe(0.71F, KeyframeAnimations.degreeVec(185.321F, 13.223F, 176.989F), Interpolations.LINEAR),
               new Keyframe(0.75F, KeyframeAnimations.degreeVec(185.488F, 16.904F, 177.191F), Interpolations.LINEAR),
               new Keyframe(0.79F, KeyframeAnimations.degreeVec(186.5F, 21.971F, 177.143F), Interpolations.LINEAR),
               new Keyframe(0.83F, KeyframeAnimations.degreeVec(187.212F, 25.492F, 177.067F), Interpolations.LINEAR),
               new Keyframe(0.88F, KeyframeAnimations.degreeVec(184.57F, 21.364F, 177.657F), Interpolations.LINEAR),
               new Keyframe(0.92F, KeyframeAnimations.degreeVec(181.822F, 17.05F, 178.146F), Interpolations.LINEAR),
               new Keyframe(0.96F, KeyframeAnimations.degreeVec(183.625F, 28.321F, 178.072F), Interpolations.LINEAR),
               new Keyframe(1.0F, KeyframeAnimations.degreeVec(185.183F, 36.315F, 177.513F), Interpolations.LINEAR),
               new Keyframe(1.04F, KeyframeAnimations.degreeVec(184.285F, 32.6F, 177.86F), Interpolations.LINEAR),
               new Keyframe(1.08F, KeyframeAnimations.degreeVec(183.602F, 28.282F, 178.08F), Interpolations.LINEAR),
               new Keyframe(1.12F, KeyframeAnimations.degreeVec(182.868F, 24.962F, 178.273F), Interpolations.LINEAR),
               new Keyframe(1.17F, KeyframeAnimations.degreeVec(182.672F, 23.916F, 178.308F), Interpolations.LINEAR),
               new Keyframe(1.25F, KeyframeAnimations.degreeVec(183.229F, 25.945F, 178.168F), Interpolations.LINEAR),
               new Keyframe(1.38F, KeyframeAnimations.degreeVec(183.204F, 25.979F, 178.178F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "CTR_upperarm_left.001",
         new AnimationChannel(
            Targets.SCALE,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.787F), Interpolations.LINEAR),
               new Keyframe(0.04F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.544F), Interpolations.LINEAR),
               new Keyframe(0.08F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.405F), Interpolations.LINEAR),
               new Keyframe(0.12F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.363F), Interpolations.LINEAR),
               new Keyframe(0.17F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.346F), Interpolations.LINEAR),
               new Keyframe(0.21F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.282F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.174F), Interpolations.LINEAR),
               new Keyframe(0.29F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.114F), Interpolations.LINEAR),
               new Keyframe(0.42F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.097F), Interpolations.LINEAR),
               new Keyframe(0.5F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.107F), Interpolations.LINEAR),
               new Keyframe(0.67F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.184F), Interpolations.LINEAR),
               new Keyframe(0.79F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.303F), Interpolations.LINEAR),
               new Keyframe(0.83F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.378F), Interpolations.LINEAR),
               new Keyframe(0.88F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.537F), Interpolations.LINEAR),
               new Keyframe(0.92F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.727F), Interpolations.LINEAR),
               new Keyframe(0.96F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.794F), Interpolations.LINEAR),
               new Keyframe(1.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.815F), Interpolations.LINEAR),
               new Keyframe(1.04F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.818F), Interpolations.LINEAR),
               new Keyframe(1.08F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.796F), Interpolations.LINEAR),
               new Keyframe(1.17F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.799F), Interpolations.LINEAR),
               new Keyframe(1.21F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.786F), Interpolations.LINEAR),
               new Keyframe(1.38F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.787F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "CTR_shoulder_right",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.016F, -1.1F, -0.172F), Interpolations.LINEAR),
               new Keyframe(0.04F, KeyframeAnimations.posVec(0.064F, 2.559F, -2.709F), Interpolations.LINEAR),
               new Keyframe(0.08F, KeyframeAnimations.posVec(0.213F, 6.217F, -5.247F), Interpolations.LINEAR),
               new Keyframe(0.12F, KeyframeAnimations.posVec(0.458F, 5.682F, -5.076F), Interpolations.LINEAR),
               new Keyframe(0.17F, KeyframeAnimations.posVec(0.751F, 4.391F, -4.668F), Interpolations.LINEAR),
               new Keyframe(0.21F, KeyframeAnimations.posVec(1.034F, 2.823F, -4.182F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.posVec(1.246F, 1.453F, -3.774F), Interpolations.LINEAR),
               new Keyframe(0.29F, KeyframeAnimations.posVec(1.331F, 0.757F, -3.603F), Interpolations.LINEAR),
               new Keyframe(0.33F, KeyframeAnimations.posVec(1.266F, 0.591F, -3.709F), Interpolations.LINEAR),
               new Keyframe(0.38F, KeyframeAnimations.posVec(1.088F, 0.484F, -3.999F), Interpolations.LINEAR),
               new Keyframe(0.42F, KeyframeAnimations.posVec(0.823F, 0.424F, -4.433F), Interpolations.LINEAR),
               new Keyframe(0.46F, KeyframeAnimations.posVec(0.495F, 0.397F, -4.969F), Interpolations.LINEAR),
               new Keyframe(0.58F, KeyframeAnimations.posVec(-0.615F, 0.382F, -6.782F), Interpolations.LINEAR),
               new Keyframe(0.62F, KeyframeAnimations.posVec(-0.943F, 0.355F, -7.318F), Interpolations.LINEAR),
               new Keyframe(0.67F, KeyframeAnimations.posVec(-1.209F, 0.295F, -7.752F), Interpolations.LINEAR),
               new Keyframe(0.71F, KeyframeAnimations.posVec(-1.386F, 0.188F, -8.042F), Interpolations.LINEAR),
               new Keyframe(0.75F, KeyframeAnimations.posVec(-1.451F, 0.022F, -8.148F), Interpolations.LINEAR),
               new Keyframe(0.79F, KeyframeAnimations.posVec(-1.389F, -0.335F, -7.373F), Interpolations.LINEAR),
               new Keyframe(0.83F, KeyframeAnimations.posVec(-1.226F, -0.921F, -5.436F), Interpolations.LINEAR),
               new Keyframe(0.88F, KeyframeAnimations.posVec(-0.997F, -1.603F, -2.917F), Interpolations.LINEAR),
               new Keyframe(0.92F, KeyframeAnimations.posVec(-0.738F, -2.253F, -0.398F), Interpolations.LINEAR),
               new Keyframe(0.96F, KeyframeAnimations.posVec(-0.484F, -2.738F, 1.539F), Interpolations.LINEAR),
               new Keyframe(1.0F, KeyframeAnimations.posVec(-0.27F, -2.929F, 2.314F), Interpolations.LINEAR),
               new Keyframe(1.04F, KeyframeAnimations.posVec(-0.122F, -2.643F, 1.926F), Interpolations.LINEAR),
               new Keyframe(1.08F, KeyframeAnimations.posVec(-0.035F, -2.014F, 1.071F), Interpolations.LINEAR),
               new Keyframe(1.12F, KeyframeAnimations.posVec(0.006F, -1.386F, 0.216F), Interpolations.LINEAR),
               new Keyframe(1.17F, KeyframeAnimations.posVec(0.016F, -1.1F, -0.172F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "CTR_upperarm_right",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.839F, -2.513F, 0.452F), Interpolations.LINEAR),
               new Keyframe(0.04F, KeyframeAnimations.degreeVec(0.52F, 1.134F, 3.615F), Interpolations.LINEAR),
               new Keyframe(0.08F, KeyframeAnimations.degreeVec(-4.41F, 2.347F, 0.785F), Interpolations.LINEAR),
               new Keyframe(0.12F, KeyframeAnimations.degreeVec(-10.499F, 5.241F, 3.69F), Interpolations.LINEAR),
               new Keyframe(0.17F, KeyframeAnimations.degreeVec(-17.59F, 10.748F, 3.03F), Interpolations.LINEAR),
               new Keyframe(0.21F, KeyframeAnimations.degreeVec(-20.501F, 15.665F, 1.668F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(-18.328F, 14.461F, 1.656F), Interpolations.LINEAR),
               new Keyframe(0.29F, KeyframeAnimations.degreeVec(-17.279F, 11.59F, 0.4F), Interpolations.LINEAR),
               new Keyframe(0.33F, KeyframeAnimations.degreeVec(-17.519F, 7.566F, -2.037F), Interpolations.LINEAR),
               new Keyframe(0.38F, KeyframeAnimations.degreeVec(-17.562F, 3.592F, -4.657F), Interpolations.LINEAR),
               new Keyframe(0.42F, KeyframeAnimations.degreeVec(-16.208F, -2.245F, -6.357F), Interpolations.LINEAR),
               new Keyframe(0.46F, KeyframeAnimations.degreeVec(-13.622F, -9.885F, -7.173F), Interpolations.LINEAR),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(-11.042F, -16.366F, -8.091F), Interpolations.LINEAR),
               new Keyframe(0.54F, KeyframeAnimations.degreeVec(-8.808F, -20.417F, -9.283F), Interpolations.LINEAR),
               new Keyframe(0.58F, KeyframeAnimations.degreeVec(-7.017F, -22.326F, -10.764F), Interpolations.LINEAR),
               new Keyframe(0.62F, KeyframeAnimations.degreeVec(-5.752F, -22.437F, -12.371F), Interpolations.LINEAR),
               new Keyframe(0.67F, KeyframeAnimations.degreeVec(-4.865F, -21.368F, -13.811F), Interpolations.LINEAR),
               new Keyframe(0.71F, KeyframeAnimations.degreeVec(-4.131F, -19.727F, -14.874F), Interpolations.LINEAR),
               new Keyframe(0.75F, KeyframeAnimations.degreeVec(-3.501F, -17.688F, -15.456F), Interpolations.LINEAR),
               new Keyframe(0.79F, KeyframeAnimations.degreeVec(-2.844F, -15.196F, -14.371F), Interpolations.LINEAR),
               new Keyframe(0.83F, KeyframeAnimations.degreeVec(-1.901F, -12.283F, -10.778F), Interpolations.LINEAR),
               new Keyframe(0.88F, KeyframeAnimations.degreeVec(-0.483F, -9.205F, -4.998F), Interpolations.LINEAR),
               new Keyframe(0.92F, KeyframeAnimations.degreeVec(1.158F, -6.388F, 0.89F), Interpolations.LINEAR),
               new Keyframe(0.96F, KeyframeAnimations.degreeVec(1.066F, -4.694F, 0.469F), Interpolations.LINEAR),
               new Keyframe(1.0F, KeyframeAnimations.degreeVec(1.025F, -2.461F, 2.038F), Interpolations.LINEAR),
               new Keyframe(1.04F, KeyframeAnimations.degreeVec(1.645F, -0.442F, 5.053F), Interpolations.LINEAR),
               new Keyframe(1.08F, KeyframeAnimations.degreeVec(1.479F, -0.379F, 4.858F), Interpolations.LINEAR),
               new Keyframe(1.12F, KeyframeAnimations.degreeVec(1.258F, -1.173F, 2.801F), Interpolations.LINEAR),
               new Keyframe(1.17F, KeyframeAnimations.degreeVec(1.117F, -2.375F, 1.068F), Interpolations.LINEAR),
               new Keyframe(1.21F, KeyframeAnimations.degreeVec(0.916F, -2.902F, 0.447F), Interpolations.LINEAR),
               new Keyframe(1.25F, KeyframeAnimations.degreeVec(0.813F, -2.74F, 0.306F), Interpolations.LINEAR),
               new Keyframe(1.29F, KeyframeAnimations.degreeVec(0.839F, -2.513F, 0.452F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "CTR_lowerarm_right",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(-0.021F, -1.724F, -0.014F), Interpolations.LINEAR),
               new Keyframe(0.04F, KeyframeAnimations.degreeVec(0.009F, 0.633F, 0.005F), Interpolations.LINEAR),
               new Keyframe(0.08F, KeyframeAnimations.degreeVec(0.321F, 17.757F, 0.24F), Interpolations.LINEAR),
               new Keyframe(0.12F, KeyframeAnimations.degreeVec(0.481F, 23.006F, 0.377F), Interpolations.LINEAR),
               new Keyframe(0.17F, KeyframeAnimations.degreeVec(-2.843F, 25.089F, -1.552F), Interpolations.LINEAR),
               new Keyframe(0.21F, KeyframeAnimations.degreeVec(-9.715F, 20.073F, -5.025F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(-15.738F, 14.27F, -7.189F), Interpolations.LINEAR),
               new Keyframe(0.29F, KeyframeAnimations.degreeVec(-17.724F, 10.524F, -7.378F), Interpolations.LINEAR),
               new Keyframe(0.33F, KeyframeAnimations.degreeVec(-16.624F, 9.276F, -6.659F), Interpolations.LINEAR),
               new Keyframe(0.38F, KeyframeAnimations.degreeVec(-15.302F, 8.516F, -5.968F), Interpolations.LINEAR),
               new Keyframe(0.42F, KeyframeAnimations.degreeVec(-13.997F, 8.506F, -5.429F), Interpolations.LINEAR),
               new Keyframe(0.46F, KeyframeAnimations.degreeVec(-12.824F, 9.417F, -5.082F), Interpolations.LINEAR),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(-11.744F, 11.164F, -4.869F), Interpolations.LINEAR),
               new Keyframe(0.54F, KeyframeAnimations.degreeVec(-10.534F, 13.174F, -4.592F), Interpolations.LINEAR),
               new Keyframe(0.58F, KeyframeAnimations.degreeVec(-9.073F, 14.944F, -4.119F), Interpolations.LINEAR),
               new Keyframe(0.62F, KeyframeAnimations.degreeVec(-7.436F, 16.382F, -3.476F), Interpolations.LINEAR),
               new Keyframe(0.67F, KeyframeAnimations.degreeVec(-5.705F, 17.311F, -2.701F), Interpolations.LINEAR),
               new Keyframe(0.71F, KeyframeAnimations.degreeVec(-4.007F, 17.61F, -1.879F), Interpolations.LINEAR),
               new Keyframe(0.75F, KeyframeAnimations.degreeVec(-2.487F, 17.214F, -1.119F), Interpolations.LINEAR),
               new Keyframe(0.79F, KeyframeAnimations.degreeVec(-1.192F, 13.678F, -0.454F), Interpolations.LINEAR),
               new Keyframe(0.83F, KeyframeAnimations.degreeVec(-0.409F, 5.476F, -0.108F), Interpolations.LINEAR),
               new Keyframe(0.88F, KeyframeAnimations.degreeVec(-0.155F, -6.044F, -0.057F), Interpolations.LINEAR),
               new Keyframe(0.92F, KeyframeAnimations.degreeVec(-0.166F, -15.221F, -0.087F), Interpolations.LINEAR),
               new Keyframe(0.96F, KeyframeAnimations.degreeVec(-0.048F, -3.918F, -0.03F), Interpolations.LINEAR),
               new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.021F, 1.588F, 0.013F), Interpolations.LINEAR),
               new Keyframe(1.04F, KeyframeAnimations.degreeVec(-0.047F, -3.857F, -0.029F), Interpolations.LINEAR),
               new Keyframe(1.08F, KeyframeAnimations.degreeVec(-0.054F, -4.457F, -0.033F), Interpolations.LINEAR),
               new Keyframe(1.12F, KeyframeAnimations.degreeVec(-0.054F, -4.472F, -0.033F), Interpolations.LINEAR),
               new Keyframe(1.17F, KeyframeAnimations.degreeVec(-0.052F, -4.3F, -0.032F), Interpolations.LINEAR),
               new Keyframe(1.21F, KeyframeAnimations.degreeVec(-0.035F, -2.834F, -0.022F), Interpolations.LINEAR),
               new Keyframe(1.25F, KeyframeAnimations.degreeVec(-0.021F, -1.659F, -0.013F), Interpolations.LINEAR),
               new Keyframe(1.38F, KeyframeAnimations.degreeVec(-0.021F, -1.724F, -0.014F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "CTR_upperarm_right.001",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.168F, 17.864F, 2.048F), Interpolations.LINEAR),
               new Keyframe(0.04F, KeyframeAnimations.degreeVec(0.133F, 8.816F, 1.722F), Interpolations.LINEAR),
               new Keyframe(0.08F, KeyframeAnimations.degreeVec(0.806F, 3.78F, 1.588F), Interpolations.LINEAR),
               new Keyframe(0.12F, KeyframeAnimations.degreeVec(-0.628F, -7.042F, 3.371F), Interpolations.LINEAR),
               new Keyframe(0.17F, KeyframeAnimations.degreeVec(0.134F, -18.135F, 3.01F), Interpolations.LINEAR),
               new Keyframe(0.21F, KeyframeAnimations.degreeVec(-0.501F, -24.007F, 2.093F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(-1.709F, -21.206F, 0.693F), Interpolations.LINEAR),
               new Keyframe(0.29F, KeyframeAnimations.degreeVec(-2.309F, -17.425F, -0.028F), Interpolations.LINEAR),
               new Keyframe(0.33F, KeyframeAnimations.degreeVec(-2.262F, -13.335F, 0.032F), Interpolations.LINEAR),
               new Keyframe(0.38F, KeyframeAnimations.degreeVec(-2.144F, -9.834F, 0.224F), Interpolations.LINEAR),
               new Keyframe(0.42F, KeyframeAnimations.degreeVec(-2.116F, -4.742F, 0.693F), Interpolations.LINEAR),
               new Keyframe(0.46F, KeyframeAnimations.degreeVec(-1.218F, 2.216F, 0.408F), Interpolations.LINEAR),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(-1.049F, 8.526F, 0.872F), Interpolations.LINEAR),
               new Keyframe(0.54F, KeyframeAnimations.degreeVec(-0.765F, 13.226F, 1.198F), Interpolations.LINEAR),
               new Keyframe(0.58F, KeyframeAnimations.degreeVec(-0.476F, 16.326F, 1.487F), Interpolations.LINEAR),
               new Keyframe(0.62F, KeyframeAnimations.degreeVec(-0.188F, 17.766F, 1.749F), Interpolations.LINEAR),
               new Keyframe(0.67F, KeyframeAnimations.degreeVec(0.071F, 17.646F, 1.963F), Interpolations.LINEAR),
               new Keyframe(0.71F, KeyframeAnimations.degreeVec(0.26F, 16.569F, 2.097F), Interpolations.LINEAR),
               new Keyframe(0.75F, KeyframeAnimations.degreeVec(0.374F, 15.049F, 2.152F), Interpolations.LINEAR),
               new Keyframe(0.79F, KeyframeAnimations.degreeVec(0.365F, 13.044F, 2.083F), Interpolations.LINEAR),
               new Keyframe(0.83F, KeyframeAnimations.degreeVec(0.212F, 10.491F, 1.864F), Interpolations.LINEAR),
               new Keyframe(0.88F, KeyframeAnimations.degreeVec(-0.032F, 7.887F, 1.551F), Interpolations.LINEAR),
               new Keyframe(0.92F, KeyframeAnimations.degreeVec(-0.265F, 8.21F, 1.408F), Interpolations.LINEAR),
               new Keyframe(0.96F, KeyframeAnimations.degreeVec(0.164F, 21.262F, 2.101F), Interpolations.LINEAR),
               new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.485F, 26.928F, 2.452F), Interpolations.LINEAR),
               new Keyframe(1.04F, KeyframeAnimations.degreeVec(0.155F, 20.685F, 2.085F), Interpolations.LINEAR),
               new Keyframe(1.08F, KeyframeAnimations.degreeVec(0.069F, 16.773F, 1.943F), Interpolations.LINEAR),
               new Keyframe(1.12F, KeyframeAnimations.degreeVec(0.05F, 15.511F, 1.901F), Interpolations.LINEAR),
               new Keyframe(1.17F, KeyframeAnimations.degreeVec(0.064F, 16.171F, 1.927F), Interpolations.LINEAR),
               new Keyframe(1.21F, KeyframeAnimations.degreeVec(0.126F, 17.387F, 2.004F), Interpolations.LINEAR),
               new Keyframe(1.25F, KeyframeAnimations.degreeVec(0.173F, 18.036F, 2.055F), Interpolations.LINEAR),
               new Keyframe(1.29F, KeyframeAnimations.degreeVec(0.168F, 17.864F, 2.048F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "CTR_upperarm_right.001",
         new AnimationChannel(
            Targets.SCALE,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.674F), Interpolations.LINEAR),
               new Keyframe(0.04F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.545F), Interpolations.LINEAR),
               new Keyframe(0.08F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.238F), Interpolations.LINEAR),
               new Keyframe(0.12F, KeyframeAnimations.scaleVec(1.0, 1.0, 0.903F), Interpolations.LINEAR),
               new Keyframe(0.17F, KeyframeAnimations.scaleVec(1.0, 1.0, 0.642F), Interpolations.LINEAR),
               new Keyframe(0.21F, KeyframeAnimations.scaleVec(1.0, 1.0, 0.544F), Interpolations.LINEAR),
               new Keyframe(0.38F, KeyframeAnimations.scaleVec(1.0, 1.0, 0.598F), Interpolations.LINEAR),
               new Keyframe(0.42F, KeyframeAnimations.scaleVec(1.0, 1.0, 0.641F), Interpolations.LINEAR),
               new Keyframe(0.46F, KeyframeAnimations.scaleVec(1.0, 1.0, 0.737F), Interpolations.LINEAR),
               new Keyframe(0.5F, KeyframeAnimations.scaleVec(1.0, 1.0, 0.875), Interpolations.LINEAR),
               new Keyframe(0.54F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.029F), Interpolations.LINEAR),
               new Keyframe(0.58F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.165F), Interpolations.LINEAR),
               new Keyframe(0.62F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.241F), Interpolations.LINEAR),
               new Keyframe(0.75F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.342F), Interpolations.LINEAR),
               new Keyframe(0.79F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.395F), Interpolations.LINEAR),
               new Keyframe(0.88F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.596F), Interpolations.LINEAR),
               new Keyframe(0.92F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.718F), Interpolations.LINEAR),
               new Keyframe(0.96F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.745F), Interpolations.LINEAR),
               new Keyframe(1.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.749F), Interpolations.LINEAR),
               new Keyframe(1.04F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.737F), Interpolations.LINEAR),
               new Keyframe(1.08F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.694F), Interpolations.LINEAR),
               new Keyframe(1.12F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.678F), Interpolations.LINEAR),
               new Keyframe(1.21F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.681F), Interpolations.LINEAR),
               new Keyframe(1.38F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.674F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "CTR_leg_left",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(-177.066F, 52.262F, 182.934F), Interpolations.LINEAR),
               new Keyframe(0.04F, KeyframeAnimations.degreeVec(-175.34F, 52.274F, 184.66F), Interpolations.LINEAR),
               new Keyframe(0.08F, KeyframeAnimations.degreeVec(-173.132F, 53.188F, 186.868F), Interpolations.LINEAR),
               new Keyframe(0.12F, KeyframeAnimations.degreeVec(-172.771F, 41.782F, 187.229F), Interpolations.LINEAR),
               new Keyframe(0.17F, KeyframeAnimations.degreeVec(-175.079F, 18.559F, 184.921F), Interpolations.LINEAR),
               new Keyframe(0.21F, KeyframeAnimations.degreeVec(-176.481F, 7.149F, 183.519F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(-176.45F, 8.657F, 183.55F), Interpolations.LINEAR),
               new Keyframe(0.29F, KeyframeAnimations.degreeVec(-176.471F, 9.96F, 183.529F), Interpolations.LINEAR),
               new Keyframe(0.33F, KeyframeAnimations.degreeVec(-176.535F, 10.999F, 183.465F), Interpolations.LINEAR),
               new Keyframe(0.38F, KeyframeAnimations.degreeVec(-176.629F, 11.754F, 183.371F), Interpolations.LINEAR),
               new Keyframe(0.42F, KeyframeAnimations.degreeVec(-176.741F, 12.228F, 183.259F), Interpolations.LINEAR),
               new Keyframe(0.46F, KeyframeAnimations.degreeVec(-176.857F, 12.431F, 183.143F), Interpolations.LINEAR),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(-176.963F, 12.386F, 183.037F), Interpolations.LINEAR),
               new Keyframe(0.54F, KeyframeAnimations.degreeVec(-177.046F, 12.119F, 182.954F), Interpolations.LINEAR),
               new Keyframe(0.58F, KeyframeAnimations.degreeVec(-177.093F, 11.668F, 182.908F), Interpolations.LINEAR),
               new Keyframe(0.62F, KeyframeAnimations.degreeVec(-176.994F, 13.619F, 183.006F), Interpolations.LINEAR),
               new Keyframe(0.67F, KeyframeAnimations.degreeVec(-176.776F, 19.639F, 183.224F), Interpolations.LINEAR),
               new Keyframe(0.71F, KeyframeAnimations.degreeVec(-176.577F, 28.583F, 183.423F), Interpolations.LINEAR),
               new Keyframe(0.75F, KeyframeAnimations.degreeVec(-176.494F, 38.912F, 183.506F), Interpolations.LINEAR),
               new Keyframe(0.79F, KeyframeAnimations.degreeVec(-176.575F, 48.415F, 183.425F), Interpolations.LINEAR),
               new Keyframe(0.83F, KeyframeAnimations.degreeVec(-176.789F, 54.662F, 183.211F), Interpolations.LINEAR),
               new Keyframe(0.88F, KeyframeAnimations.degreeVec(-176.969F, 55.315F, 183.031F), Interpolations.LINEAR),
               new Keyframe(0.92F, KeyframeAnimations.degreeVec(-176.938F, 52.282F, 183.062F), Interpolations.LINEAR),
               new Keyframe(0.96F, KeyframeAnimations.degreeVec(-176.765F, 49.73F, 183.235F), Interpolations.LINEAR),
               new Keyframe(1.0F, KeyframeAnimations.degreeVec(-176.711F, 47.33F, 183.289F), Interpolations.LINEAR),
               new Keyframe(1.04F, KeyframeAnimations.degreeVec(-176.812F, 47.904F, 183.188F), Interpolations.LINEAR),
               new Keyframe(1.08F, KeyframeAnimations.degreeVec(-177.043F, 52.056F, 182.957F), Interpolations.LINEAR),
               new Keyframe(1.12F, KeyframeAnimations.degreeVec(-177.113F, 53.078F, 182.887F), Interpolations.LINEAR),
               new Keyframe(1.17F, KeyframeAnimations.degreeVec(-177.066F, 52.262F, 182.934F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "CTR_leg_left",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.878F, -1.018F, -0.451F), Interpolations.LINEAR),
               new Keyframe(0.04F, KeyframeAnimations.posVec(1.312F, -2.793F, -1.341F), Interpolations.LINEAR),
               new Keyframe(0.08F, KeyframeAnimations.posVec(1.501F, -5.642F, -2.726F), Interpolations.LINEAR),
               new Keyframe(0.12F, KeyframeAnimations.posVec(1.53F, -5.462F, -3.033F), Interpolations.LINEAR),
               new Keyframe(0.17F, KeyframeAnimations.posVec(1.377F, -4.508F, -3.869F), Interpolations.LINEAR),
               new Keyframe(0.21F, KeyframeAnimations.posVec(1.316F, -5.7F, -4.03F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.posVec(1.304F, -5.478F, -4.056F), Interpolations.LINEAR),
               new Keyframe(0.29F, KeyframeAnimations.posVec(1.293F, -5.301F, -4.064F), Interpolations.LINEAR),
               new Keyframe(0.33F, KeyframeAnimations.posVec(1.28F, -5.167F, -4.062F), Interpolations.LINEAR),
               new Keyframe(0.38F, KeyframeAnimations.posVec(1.268F, -5.077F, -4.057F), Interpolations.LINEAR),
               new Keyframe(0.42F, KeyframeAnimations.posVec(1.256F, -5.037F, -4.048F), Interpolations.LINEAR),
               new Keyframe(0.46F, KeyframeAnimations.posVec(1.249F, -5.056F, -4.036F), Interpolations.LINEAR),
               new Keyframe(0.5F, KeyframeAnimations.posVec(1.248F, -5.147F, -4.016F), Interpolations.LINEAR),
               new Keyframe(0.54F, KeyframeAnimations.posVec(1.257F, -5.325F, -3.982F), Interpolations.LINEAR),
               new Keyframe(0.58F, KeyframeAnimations.posVec(1.277F, -5.604F, -3.927F), Interpolations.LINEAR),
               new Keyframe(0.62F, KeyframeAnimations.posVec(1.262F, -5.349F, -3.925F), Interpolations.LINEAR),
               new Keyframe(0.67F, KeyframeAnimations.posVec(1.197F, -4.209F, -3.85F), Interpolations.LINEAR),
               new Keyframe(0.71F, KeyframeAnimations.posVec(1.131F, -2.856F, -3.318F), Interpolations.LINEAR),
               new Keyframe(0.75F, KeyframeAnimations.posVec(1.086F, -1.982F, -2.315F), Interpolations.LINEAR),
               new Keyframe(0.79F, KeyframeAnimations.posVec(1.048F, -1.782F, -1.274F), Interpolations.LINEAR),
               new Keyframe(0.83F, KeyframeAnimations.posVec(0.997F, -1.81F, -0.56F), Interpolations.LINEAR),
               new Keyframe(0.88F, KeyframeAnimations.posVec(0.934F, -1.508F, -0.307F), Interpolations.LINEAR),
               new Keyframe(0.92F, KeyframeAnimations.posVec(0.912F, -1.066F, -0.473F), Interpolations.LINEAR),
               new Keyframe(0.96F, KeyframeAnimations.posVec(0.943F, -0.893F, -0.731F), Interpolations.LINEAR),
               new Keyframe(1.0F, KeyframeAnimations.posVec(0.962F, -0.928F, -1.056F), Interpolations.LINEAR),
               new Keyframe(1.04F, KeyframeAnimations.posVec(0.942F, -0.971F, -1.0F), Interpolations.LINEAR),
               new Keyframe(1.08F, KeyframeAnimations.posVec(0.885F, -1.033F, -0.486F), Interpolations.LINEAR),
               new Keyframe(1.12F, KeyframeAnimations.posVec(0.868F, -1.045F, -0.354F), Interpolations.LINEAR),
               new Keyframe(1.17F, KeyframeAnimations.posVec(0.878F, -1.018F, -0.451F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "CTR_leg_right",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(-2.789F, 0.901F, 2.789F), Interpolations.LINEAR),
               new Keyframe(0.04F, KeyframeAnimations.degreeVec(-3.228F, 9.198F, 3.228F), Interpolations.LINEAR),
               new Keyframe(0.08F, KeyframeAnimations.degreeVec(-4.352F, 12.474F, 4.352F), Interpolations.LINEAR),
               new Keyframe(0.12F, KeyframeAnimations.degreeVec(-5.307F, 13.754F, 5.307F), Interpolations.LINEAR),
               new Keyframe(0.17F, KeyframeAnimations.degreeVec(-5.587F, 13.451F, 5.587F), Interpolations.LINEAR),
               new Keyframe(0.21F, KeyframeAnimations.degreeVec(-5.622F, 11.465F, 5.622F), Interpolations.LINEAR),
               new Keyframe(0.29F, KeyframeAnimations.degreeVec(-5.464F, 7.254F, 5.464F), Interpolations.LINEAR),
               new Keyframe(0.33F, KeyframeAnimations.degreeVec(-5.301F, 5.313F, 5.301F), Interpolations.LINEAR),
               new Keyframe(0.38F, KeyframeAnimations.degreeVec(-5.096F, 3.542F, 5.096F), Interpolations.LINEAR),
               new Keyframe(0.42F, KeyframeAnimations.degreeVec(-4.86F, 1.96F, 4.86F), Interpolations.LINEAR),
               new Keyframe(0.46F, KeyframeAnimations.degreeVec(-4.6F, 0.579F, 4.6F), Interpolations.LINEAR),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(-4.324F, -0.589F, 4.324F), Interpolations.LINEAR),
               new Keyframe(0.54F, KeyframeAnimations.degreeVec(-4.042F, -1.532F, 4.042F), Interpolations.LINEAR),
               new Keyframe(0.58F, KeyframeAnimations.degreeVec(-3.761F, -2.238F, 3.761F), Interpolations.LINEAR),
               new Keyframe(0.62F, KeyframeAnimations.degreeVec(-3.49F, -2.695F, 3.49F), Interpolations.LINEAR),
               new Keyframe(0.67F, KeyframeAnimations.degreeVec(-3.241F, -2.893F, 3.241F), Interpolations.LINEAR),
               new Keyframe(0.71F, KeyframeAnimations.degreeVec(-3.025F, -2.823F, 3.025F), Interpolations.LINEAR),
               new Keyframe(0.75F, KeyframeAnimations.degreeVec(-2.853F, -2.483F, 2.854F), Interpolations.LINEAR),
               new Keyframe(0.79F, KeyframeAnimations.degreeVec(-2.74F, -1.879F, 2.74F), Interpolations.LINEAR),
               new Keyframe(0.88F, KeyframeAnimations.degreeVec(-2.747F, 0.022F, 2.747F), Interpolations.LINEAR),
               new Keyframe(0.96F, KeyframeAnimations.degreeVec(-3.102F, 1.856F, 3.102F), Interpolations.LINEAR),
               new Keyframe(1.0F, KeyframeAnimations.degreeVec(-2.995F, 4.349F, 2.995F), Interpolations.LINEAR),
               new Keyframe(1.04F, KeyframeAnimations.degreeVec(-2.819F, 5.023F, 2.819F), Interpolations.LINEAR),
               new Keyframe(1.08F, KeyframeAnimations.degreeVec(-2.764F, 1.413F, 2.764F), Interpolations.LINEAR),
               new Keyframe(1.12F, KeyframeAnimations.degreeVec(-2.783F, 0.114F, 2.783F), Interpolations.LINEAR),
               new Keyframe(1.17F, KeyframeAnimations.degreeVec(-2.789F, 0.901F, 2.789F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "CTR_leg_right",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.698F, 0.987F, 0.254F), Interpolations.LINEAR),
               new Keyframe(0.04F, KeyframeAnimations.posVec(0.781F, 1.015F, -0.697F), Interpolations.LINEAR),
               new Keyframe(0.08F, KeyframeAnimations.posVec(0.914F, 0.518F, -1.116F), Interpolations.LINEAR),
               new Keyframe(0.12F, KeyframeAnimations.posVec(0.945F, -0.117F, -1.304F), Interpolations.LINEAR),
               new Keyframe(0.17F, KeyframeAnimations.posVec(0.909F, -0.439F, -1.308F), Interpolations.LINEAR),
               new Keyframe(0.21F, KeyframeAnimations.posVec(0.85F, -0.684F, -1.171F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.posVec(0.798F, -0.857F, -1.027F), Interpolations.LINEAR),
               new Keyframe(0.29F, KeyframeAnimations.posVec(0.756F, -0.959F, -0.884F), Interpolations.LINEAR),
               new Keyframe(0.33F, KeyframeAnimations.posVec(0.726F, -0.989F, -0.739F), Interpolations.LINEAR),
               new Keyframe(0.38F, KeyframeAnimations.posVec(0.708F, -0.952F, -0.589F), Interpolations.LINEAR),
               new Keyframe(0.42F, KeyframeAnimations.posVec(0.7F, -0.853F, -0.43F), Interpolations.LINEAR),
               new Keyframe(0.46F, KeyframeAnimations.posVec(0.7F, -0.7F, -0.263F), Interpolations.LINEAR),
               new Keyframe(0.5F, KeyframeAnimations.posVec(0.704F, -0.502F, -0.09F), Interpolations.LINEAR),
               new Keyframe(0.54F, KeyframeAnimations.posVec(0.711F, -0.268F, 0.086F), Interpolations.LINEAR),
               new Keyframe(0.62F, KeyframeAnimations.posVec(0.723F, 0.264F, 0.411F), Interpolations.LINEAR),
               new Keyframe(0.67F, KeyframeAnimations.posVec(0.727F, 0.536F, 0.542F), Interpolations.LINEAR),
               new Keyframe(0.71F, KeyframeAnimations.posVec(0.729F, 0.792F, 0.636F), Interpolations.LINEAR),
               new Keyframe(0.75F, KeyframeAnimations.posVec(0.73F, 1.014F, 0.681F), Interpolations.LINEAR),
               new Keyframe(0.79F, KeyframeAnimations.posVec(0.731F, 1.179F, 0.668F), Interpolations.LINEAR),
               new Keyframe(0.83F, KeyframeAnimations.posVec(0.732F, 1.261F, 0.589F), Interpolations.LINEAR),
               new Keyframe(0.88F, KeyframeAnimations.posVec(0.733F, 1.224F, 0.443F), Interpolations.LINEAR),
               new Keyframe(0.92F, KeyframeAnimations.posVec(0.735F, 1.029F, 0.263F), Interpolations.LINEAR),
               new Keyframe(0.96F, KeyframeAnimations.posVec(0.712F, 0.704F, 0.054F), Interpolations.LINEAR),
               new Keyframe(1.0F, KeyframeAnimations.posVec(0.634F, 0.443F, -0.296F), Interpolations.LINEAR),
               new Keyframe(1.04F, KeyframeAnimations.posVec(0.62F, 0.574F, -0.335F), Interpolations.LINEAR),
               new Keyframe(1.08F, KeyframeAnimations.posVec(0.688F, 0.972F, 0.187F), Interpolations.LINEAR),
               new Keyframe(1.12F, KeyframeAnimations.posVec(0.709F, 1.042F, 0.368F), Interpolations.LINEAR),
               new Keyframe(1.17F, KeyframeAnimations.posVec(0.698F, 0.987F, 0.254F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "CTR_leg_right.002",
         new AnimationChannel(Targets.POSITION, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.posVec(-2.707F, 0.0F, -2.155F), Interpolations.LINEAR)})
      )
      .build();
}
