package net.unusual.block_factorys_bosses.client.animation.yeti;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.animation.AnimationChannel.Interpolations;
import net.minecraft.client.animation.AnimationChannel.Targets;
import net.minecraft.client.animation.AnimationDefinition.Builder;

public class yetiSpawn {
   public static final AnimationDefinition SPAWN = Builder.withLength(3.25F)
      .looping()
      .addAnimation(
         "CTR_leg_left.002",
         new AnimationChannel(Targets.POSITION, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.posVec(2.113F, 0.0F, -13.058F), Interpolations.LINEAR)})
      )
      .addAnimation(
         "CTR_hips",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(-32.406F, -8.29F, -5.233F), Interpolations.LINEAR),
               new Keyframe(0.17F, KeyframeAnimations.degreeVec(-33.78F, 1.178F, -5.455F), Interpolations.LINEAR),
               new Keyframe(0.21F, KeyframeAnimations.degreeVec(-21.534F, 0.749F, -3.32F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.33F, KeyframeAnimations.degreeVec(3.57F, -0.065F, 0.064F), Interpolations.LINEAR),
               new Keyframe(0.38F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.42F, KeyframeAnimations.degreeVec(-1.804F, 0.015F, -0.025F), Interpolations.LINEAR),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(-1.455F, 0.016F, -0.013F), Interpolations.LINEAR),
               new Keyframe(0.54F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.58F, KeyframeAnimations.degreeVec(0.526F, -0.006F, 0.003F), Interpolations.LINEAR),
               new Keyframe(0.71F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.04F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.08F, KeyframeAnimations.degreeVec(-1.565F, -1.932F, -0.922F), Interpolations.LINEAR),
               new Keyframe(1.12F, KeyframeAnimations.degreeVec(-5.855F, -7.712F, -3.612F), Interpolations.LINEAR),
               new Keyframe(1.17F, KeyframeAnimations.degreeVec(-9.742F, -13.731F, -6.389F), Interpolations.LINEAR),
               new Keyframe(1.29F, KeyframeAnimations.degreeVec(-13.124F, -19.898F, -9.327F), Interpolations.LINEAR),
               new Keyframe(1.33F, KeyframeAnimations.degreeVec(-13.022F, -19.682F, -9.245F), Interpolations.LINEAR),
               new Keyframe(1.38F, KeyframeAnimations.degreeVec(-12.668F, -18.95F, -8.959F), Interpolations.LINEAR),
               new Keyframe(1.46F, KeyframeAnimations.degreeVec(-10.679F, -15.092F, -7.354F), Interpolations.LINEAR),
               new Keyframe(1.5F, KeyframeAnimations.degreeVec(-8.419F, -11.19F, -5.595F), Interpolations.LINEAR),
               new Keyframe(1.58F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.62F, KeyframeAnimations.degreeVec(2.063F, 2.027F, 1.618F), Interpolations.LINEAR),
               new Keyframe(1.67F, KeyframeAnimations.degreeVec(1.112F, 1.223F, 0.999F), Interpolations.LINEAR),
               new Keyframe(1.71F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.75F, KeyframeAnimations.degreeVec(-0.015F, -0.179F, -0.002F), Interpolations.LINEAR),
               new Keyframe(1.83F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "CTR_hips",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 252.133F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.17F, KeyframeAnimations.posVec(0.0F, 50.34F, -0.281F), Interpolations.LINEAR),
               new Keyframe(0.21F, KeyframeAnimations.posVec(0.0F, -12.935F, 0.804F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, -19.695F, 2.247F), Interpolations.LINEAR),
               new Keyframe(0.33F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.38F, KeyframeAnimations.posVec(0.0F, 2.979F, -0.398F), Interpolations.LINEAR),
               new Keyframe(0.42F, KeyframeAnimations.posVec(0.0F, 2.827F, -0.353F), Interpolations.LINEAR),
               new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.54F, KeyframeAnimations.posVec(0.0F, -0.289F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.62F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.04F, KeyframeAnimations.posVec(0.0F, 0.298F, -0.013F), Interpolations.LINEAR),
               new Keyframe(1.08F, KeyframeAnimations.posVec(0.0F, 1.067F, -0.047F), Interpolations.LINEAR),
               new Keyframe(1.12F, KeyframeAnimations.posVec(0.0F, 2.118F, -0.092F), Interpolations.LINEAR),
               new Keyframe(1.17F, KeyframeAnimations.posVec(0.0F, 3.264F, -0.142F), Interpolations.LINEAR),
               new Keyframe(1.29F, KeyframeAnimations.posVec(0.0F, 5.383F, -0.234F), Interpolations.LINEAR),
               new Keyframe(1.33F, KeyframeAnimations.posVec(0.0F, 5.151F, -0.224F), Interpolations.LINEAR),
               new Keyframe(1.38F, KeyframeAnimations.posVec(0.0F, 4.542F, -0.198F), Interpolations.LINEAR),
               new Keyframe(1.46F, KeyframeAnimations.posVec(0.0F, 2.691F, -0.117F), Interpolations.LINEAR),
               new Keyframe(1.5F, KeyframeAnimations.posVec(0.0F, 1.703F, -0.074F), Interpolations.LINEAR),
               new Keyframe(1.58F, KeyframeAnimations.posVec(0.0F, 0.231F, -0.01F), Interpolations.LINEAR),
               new Keyframe(1.62F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "CTR_chest",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(-13.913F, -6.425F, -7.873F), Interpolations.LINEAR),
               new Keyframe(0.17F, KeyframeAnimations.degreeVec(-10.482F, -6.417F, -7.906F), Interpolations.LINEAR),
               new Keyframe(0.21F, KeyframeAnimations.degreeVec(26.862F, -0.672F, -6.341F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(26.614F, -1.455F, -3.643F), Interpolations.LINEAR),
               new Keyframe(0.33F, KeyframeAnimations.degreeVec(23.994F, -5.49F, 1.72F), Interpolations.LINEAR),
               new Keyframe(0.38F, KeyframeAnimations.degreeVec(22.56F, -7.298F, 3.56F), Interpolations.LINEAR),
               new Keyframe(0.42F, KeyframeAnimations.degreeVec(21.926F, -8.05F, 4.271F), Interpolations.LINEAR),
               new Keyframe(1.04F, KeyframeAnimations.degreeVec(21.926F, -8.05F, 4.271F), Interpolations.LINEAR),
               new Keyframe(1.08F, KeyframeAnimations.degreeVec(19.565F, -7.936F, 2.891F), Interpolations.LINEAR),
               new Keyframe(1.12F, KeyframeAnimations.degreeVec(12.678F, -7.595F, -1.114F), Interpolations.LINEAR),
               new Keyframe(1.17F, KeyframeAnimations.degreeVec(3.383F, -7.12F, -6.473F), Interpolations.LINEAR),
               new Keyframe(1.29F, KeyframeAnimations.degreeVec(-14.592F, -6.202F, -16.689F), Interpolations.LINEAR),
               new Keyframe(1.33F, KeyframeAnimations.degreeVec(-15.619F, -6.151F, -17.267F), Interpolations.LINEAR),
               new Keyframe(1.38F, KeyframeAnimations.degreeVec(-14.861F, -6.191F, -16.893F), Interpolations.LINEAR),
               new Keyframe(1.46F, KeyframeAnimations.degreeVec(-8.296F, -6.679F, -13.475F), Interpolations.LINEAR),
               new Keyframe(1.5F, KeyframeAnimations.degreeVec(-2.005F, -7.241F, -9.993F), Interpolations.LINEAR),
               new Keyframe(1.58F, KeyframeAnimations.degreeVec(21.926F, -8.05F, 4.271F), Interpolations.LINEAR),
               new Keyframe(1.62F, KeyframeAnimations.degreeVec(24.844F, -8.197F, 7.078F), Interpolations.LINEAR),
               new Keyframe(1.67F, KeyframeAnimations.degreeVec(24.17F, -8.244F, 6.489F), Interpolations.LINEAR),
               new Keyframe(1.71F, KeyframeAnimations.degreeVec(22.989F, -8.148F, 5.283F), Interpolations.LINEAR),
               new Keyframe(1.75F, KeyframeAnimations.degreeVec(21.926F, -8.05F, 4.271F), Interpolations.LINEAR),
               new Keyframe(1.83F, KeyframeAnimations.degreeVec(21.677F, -8.507F, 4.84F), Interpolations.LINEAR),
               new Keyframe(1.92F, KeyframeAnimations.degreeVec(21.766F, -8.949F, 5.623F), Interpolations.LINEAR),
               new Keyframe(1.96F, KeyframeAnimations.degreeVec(21.926F, -8.05F, 4.271F), Interpolations.LINEAR),
               new Keyframe(2.04F, KeyframeAnimations.degreeVec(21.908F, -8.41F, 4.838F), Interpolations.LINEAR),
               new Keyframe(2.12F, KeyframeAnimations.degreeVec(21.96F, -6.827F, 2.345F), Interpolations.LINEAR),
               new Keyframe(2.17F, KeyframeAnimations.degreeVec(21.959F, -6.987F, 2.597F), Interpolations.LINEAR),
               new Keyframe(2.25F, KeyframeAnimations.degreeVec(21.937F, -7.78F, 3.845F), Interpolations.LINEAR),
               new Keyframe(2.38F, KeyframeAnimations.degreeVec(21.903F, -8.474F, 4.94F), Interpolations.LINEAR),
               new Keyframe(2.42F, KeyframeAnimations.degreeVec(21.902F, -8.517F, 5.006F), Interpolations.LINEAR),
               new Keyframe(2.5F, KeyframeAnimations.degreeVec(21.944F, -7.571F, 3.516F), Interpolations.LINEAR),
               new Keyframe(2.71F, KeyframeAnimations.degreeVec(21.944F, -7.571F, 3.516F), Interpolations.LINEAR),
               new Keyframe(2.75F, KeyframeAnimations.degreeVec(21.581F, -3.854F, 3.325F), Interpolations.LINEAR),
               new Keyframe(2.83F, KeyframeAnimations.degreeVec(18.521F, -1.397F, 2.302F), Interpolations.LINEAR),
               new Keyframe(2.96F, KeyframeAnimations.degreeVec(13.378F, -0.559F, 0.984F), Interpolations.LINEAR),
               new Keyframe(3.17F, KeyframeAnimations.degreeVec(3.054F, -0.036F, 0.061F), Interpolations.LINEAR),
               new Keyframe(3.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "CTR_chest",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(2.71F, KeyframeAnimations.posVec(0.365F, 0.408F, 1.364F), Interpolations.LINEAR),
               new Keyframe(2.75F, KeyframeAnimations.posVec(0.34F, 0.362F, 1.19F), Interpolations.LINEAR),
               new Keyframe(2.83F, KeyframeAnimations.posVec(0.213F, 0.292F, 0.939F), Interpolations.LINEAR),
               new Keyframe(2.96F, KeyframeAnimations.posVec(0.073F, 0.217F, 0.674F), Interpolations.LINEAR),
               new Keyframe(3.17F, KeyframeAnimations.posVec(0.002F, 0.069F, 0.179F), Interpolations.LINEAR),
               new Keyframe(3.25F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "CTR_head",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(5.581F, 5.765F, -2.366F), Interpolations.LINEAR),
               new Keyframe(0.17F, KeyframeAnimations.degreeVec(3.508F, 5.877F, 7.021F), Interpolations.LINEAR),
               new Keyframe(0.21F, KeyframeAnimations.degreeVec(46.133F, 3.08F, 4.559F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(47.161F, 2.356F, 2.13F), Interpolations.LINEAR),
               new Keyframe(0.33F, KeyframeAnimations.degreeVec(55.778F, 2.693F, -4.932F), Interpolations.LINEAR),
               new Keyframe(0.38F, KeyframeAnimations.degreeVec(55.202F, 2.822F, -7.126F), Interpolations.LINEAR),
               new Keyframe(0.42F, KeyframeAnimations.degreeVec(45.431F, 2.877F, -7.968F), Interpolations.LINEAR),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(27.403F, 2.921F, -7.991F), Interpolations.LINEAR),
               new Keyframe(0.54F, KeyframeAnimations.degreeVec(26.968F, 3.101F, -8.127F), Interpolations.LINEAR),
               new Keyframe(0.58F, KeyframeAnimations.degreeVec(28.308F, 3.165F, -8.178F), Interpolations.LINEAR),
               new Keyframe(0.62F, KeyframeAnimations.degreeVec(29.435F, 3.149F, -8.165F), Interpolations.LINEAR),
               new Keyframe(0.71F, KeyframeAnimations.degreeVec(29.828F, 3.101F, -8.127F), Interpolations.LINEAR),
               new Keyframe(1.0F, KeyframeAnimations.degreeVec(29.828F, 3.101F, -8.127F), Interpolations.LINEAR),
               new Keyframe(1.04F, KeyframeAnimations.degreeVec(29.757F, 3.101F, -8.127F), Interpolations.LINEAR),
               new Keyframe(1.08F, KeyframeAnimations.degreeVec(33.023F, 2.376F, -8.454F), Interpolations.LINEAR),
               new Keyframe(1.12F, KeyframeAnimations.degreeVec(42.665F, 0.875F, -10.207F), Interpolations.LINEAR),
               new Keyframe(1.17F, KeyframeAnimations.degreeVec(53.364F, 1.138F, -11.61F), Interpolations.LINEAR),
               new Keyframe(1.29F, KeyframeAnimations.degreeVec(43.005F, 3.218F, -8.311F), Interpolations.LINEAR),
               new Keyframe(1.33F, KeyframeAnimations.degreeVec(33.37F, 3.13F, -7.503F), Interpolations.LINEAR),
               new Keyframe(1.38F, KeyframeAnimations.degreeVec(27.88F, 3.004F, -7.109F), Interpolations.LINEAR),
               new Keyframe(1.46F, KeyframeAnimations.degreeVec(20.178F, 1.914F, -6.005F), Interpolations.LINEAR),
               new Keyframe(1.5F, KeyframeAnimations.degreeVec(12.579F, 1.485F, -4.682F), Interpolations.LINEAR),
               new Keyframe(1.58F, KeyframeAnimations.degreeVec(-16.944F, 1.769F, -5.31F), Interpolations.LINEAR),
               new Keyframe(1.62F, KeyframeAnimations.degreeVec(-20.58F, 1.917F, -5.599F), Interpolations.LINEAR),
               new Keyframe(1.67F, KeyframeAnimations.degreeVec(-18.304F, 1.915F, -6.712F), Interpolations.LINEAR),
               new Keyframe(1.71F, KeyframeAnimations.degreeVec(-15.849F, 2.562F, -9.033F), Interpolations.LINEAR),
               new Keyframe(1.75F, KeyframeAnimations.degreeVec(-15.499F, 3.193F, -8.743F), Interpolations.LINEAR),
               new Keyframe(1.83F, KeyframeAnimations.degreeVec(-18.955F, 2.856F, -8.134F), Interpolations.LINEAR),
               new Keyframe(1.92F, KeyframeAnimations.degreeVec(-21.465F, 3.145F, -9.748F), Interpolations.LINEAR),
               new Keyframe(1.96F, KeyframeAnimations.degreeVec(-21.569F, 2.795F, -7.479F), Interpolations.LINEAR),
               new Keyframe(2.04F, KeyframeAnimations.degreeVec(-21.573F, 3.101F, -8.798F), Interpolations.LINEAR),
               new Keyframe(2.12F, KeyframeAnimations.degreeVec(-21.581F, 3.443F, -6.569F), Interpolations.LINEAR),
               new Keyframe(2.17F, KeyframeAnimations.degreeVec(-21.574F, 3.101F, -6.144F), Interpolations.LINEAR),
               new Keyframe(2.25F, KeyframeAnimations.degreeVec(-21.581F, 3.447F, -8.354F), Interpolations.LINEAR),
               new Keyframe(2.38F, KeyframeAnimations.degreeVec(-21.572F, 3.1F, -8.919F), Interpolations.LINEAR),
               new Keyframe(2.42F, KeyframeAnimations.degreeVec(-21.582F, 3.465F, -9.768F), Interpolations.LINEAR),
               new Keyframe(2.5F, KeyframeAnimations.degreeVec(-21.573F, 3.101F, -7.232F), Interpolations.LINEAR),
               new Keyframe(2.71F, KeyframeAnimations.degreeVec(-21.573F, 3.101F, -7.232F), Interpolations.LINEAR),
               new Keyframe(2.75F, KeyframeAnimations.degreeVec(-21.142F, 0.189F, -5.064F), Interpolations.LINEAR),
               new Keyframe(2.83F, KeyframeAnimations.degreeVec(-18.206F, 0.299F, -2.559F), Interpolations.LINEAR),
               new Keyframe(2.96F, KeyframeAnimations.degreeVec(-13.216F, 0.952F, -0.789F), Interpolations.LINEAR),
               new Keyframe(3.17F, KeyframeAnimations.degreeVec(-3.037F, 0.279F, 0.064F), Interpolations.LINEAR),
               new Keyframe(3.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "CTR_head",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.17F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.21F, KeyframeAnimations.posVec(-0.245F, -1.257F, -2.263F), Interpolations.LINEAR),
               new Keyframe(2.71F, KeyframeAnimations.posVec(-0.245F, -1.257F, -2.263F), Interpolations.LINEAR),
               new Keyframe(2.75F, KeyframeAnimations.posVec(-0.239F, -1.228F, -2.21F), Interpolations.LINEAR),
               new Keyframe(2.83F, KeyframeAnimations.posVec(-0.2F, -1.028F, -1.85F), Interpolations.LINEAR),
               new Keyframe(2.96F, KeyframeAnimations.posVec(-0.106F, -0.543F, -0.977F), Interpolations.LINEAR),
               new Keyframe(3.17F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "CTR_shoulde1r_left",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(-0.892F, 3.013F, -0.342F), Interpolations.LINEAR),
               new Keyframe(0.17F, KeyframeAnimations.degreeVec(-13.371F, 0.812F, -0.333F), Interpolations.LINEAR),
               new Keyframe(2.5F, KeyframeAnimations.degreeVec(-13.371F, 0.812F, -0.333F), Interpolations.LINEAR),
               new Keyframe(2.71F, KeyframeAnimations.degreeVec(-10.27F, 0.624F, -0.256F), Interpolations.LINEAR),
               new Keyframe(2.75F, KeyframeAnimations.degreeVec(-9.14F, 0.555F, -0.228F), Interpolations.LINEAR),
               new Keyframe(2.83F, KeyframeAnimations.degreeVec(-6.685F, 0.406F, -0.166F), Interpolations.LINEAR),
               new Keyframe(2.96F, KeyframeAnimations.degreeVec(-3.101F, 0.188F, -0.077F), Interpolations.LINEAR),
               new Keyframe(3.17F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "CTR_shoulde1r_left",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.241F, -8.752F, -15.715F), Interpolations.LINEAR),
               new Keyframe(0.17F, KeyframeAnimations.posVec(0.235F, -8.529F, -15.314F), Interpolations.LINEAR),
               new Keyframe(0.21F, KeyframeAnimations.posVec(0.235F, -8.529F, -15.314F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.posVec(-1.765F, -24.754F, -8.242F), Interpolations.LINEAR),
               new Keyframe(0.33F, KeyframeAnimations.posVec(-0.284F, -12.736F, -13.481F), Interpolations.LINEAR),
               new Keyframe(0.38F, KeyframeAnimations.posVec(0.235F, -8.529F, -15.314F), Interpolations.LINEAR),
               new Keyframe(2.5F, KeyframeAnimations.posVec(0.235F, -8.529F, -15.314F), Interpolations.LINEAR),
               new Keyframe(2.71F, KeyframeAnimations.posVec(0.168F, -6.094F, -10.941F), Interpolations.LINEAR),
               new Keyframe(2.83F, KeyframeAnimations.posVec(0.093F, -3.377F, -6.063F), Interpolations.LINEAR),
               new Keyframe(2.96F, KeyframeAnimations.posVec(0.035F, -1.265F, -2.272F), Interpolations.LINEAR),
               new Keyframe(3.17F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "CTR_upperarm_left",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(-97.858F, -30.126F, -18.097F), Interpolations.LINEAR),
               new Keyframe(0.17F, KeyframeAnimations.degreeVec(-101.965F, -27.02F, -35.374F), Interpolations.LINEAR),
               new Keyframe(0.21F, KeyframeAnimations.degreeVec(22.247F, -3.445F, -51.45F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(23.398F, -1.226F, -59.701F), Interpolations.LINEAR),
               new Keyframe(0.33F, KeyframeAnimations.degreeVec(31.485F, -3.121F, -67.255F), Interpolations.LINEAR),
               new Keyframe(0.38F, KeyframeAnimations.degreeVec(35.788F, -3.393F, -70.208F), Interpolations.LINEAR),
               new Keyframe(0.42F, KeyframeAnimations.degreeVec(37.58F, -3.305F, -73.462F), Interpolations.LINEAR),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(36.432F, -4.083F, -68.973F), Interpolations.LINEAR),
               new Keyframe(0.54F, KeyframeAnimations.degreeVec(35.926F, -4.072F, -67.635F), Interpolations.LINEAR),
               new Keyframe(0.58F, KeyframeAnimations.degreeVec(35.823F, -4.015F, -67.51F), Interpolations.LINEAR),
               new Keyframe(0.62F, KeyframeAnimations.degreeVec(35.897F, -3.998F, -67.757F), Interpolations.LINEAR),
               new Keyframe(0.71F, KeyframeAnimations.degreeVec(36.007F, -4.02F, -67.992F), Interpolations.LINEAR),
               new Keyframe(1.0F, KeyframeAnimations.degreeVec(36.007F, -4.02F, -67.992F), Interpolations.LINEAR),
               new Keyframe(1.04F, KeyframeAnimations.degreeVec(36.094F, -3.964F, -68.348F), Interpolations.LINEAR),
               new Keyframe(1.08F, KeyframeAnimations.degreeVec(38.354F, -4.047F, -72.626F), Interpolations.LINEAR),
               new Keyframe(1.12F, KeyframeAnimations.degreeVec(43.93F, -3.756F, -81.917F), Interpolations.LINEAR),
               new Keyframe(1.17F, KeyframeAnimations.degreeVec(44.903F, -7.535F, -72.454F), Interpolations.LINEAR),
               new Keyframe(1.29F, KeyframeAnimations.degreeVec(34.493F, -13.419F, -58.789F), Interpolations.LINEAR),
               new Keyframe(1.33F, KeyframeAnimations.degreeVec(23.248F, -14.028F, -39.838F), Interpolations.LINEAR),
               new Keyframe(1.38F, KeyframeAnimations.degreeVec(16.086F, -12.424F, -32.7F), Interpolations.LINEAR),
               new Keyframe(1.46F, KeyframeAnimations.degreeVec(6.038F, -6.455F, -28.279F), Interpolations.LINEAR),
               new Keyframe(1.5F, KeyframeAnimations.degreeVec(2.376F, -3.847F, -28.114F), Interpolations.LINEAR),
               new Keyframe(1.58F, KeyframeAnimations.degreeVec(-5.13F, -13.064F, -39.979F), Interpolations.LINEAR),
               new Keyframe(1.62F, KeyframeAnimations.degreeVec(1.148F, -32.593F, -52.123F), Interpolations.LINEAR),
               new Keyframe(1.67F, KeyframeAnimations.degreeVec(-5.372F, -40.778F, -48.017F), Interpolations.LINEAR),
               new Keyframe(1.71F, KeyframeAnimations.degreeVec(-5.99F, -45.422F, -47.185F), Interpolations.LINEAR),
               new Keyframe(1.75F, KeyframeAnimations.degreeVec(-6.155F, -44.801F, -45.722F), Interpolations.LINEAR),
               new Keyframe(1.83F, KeyframeAnimations.degreeVec(-4.31F, -45.328F, -47.275F), Interpolations.LINEAR),
               new Keyframe(1.92F, KeyframeAnimations.degreeVec(-2.445F, -46.258F, -49.422F), Interpolations.LINEAR),
               new Keyframe(1.96F, KeyframeAnimations.degreeVec(-6.236F, -44.496F, -45.495F), Interpolations.LINEAR),
               new Keyframe(2.04F, KeyframeAnimations.degreeVec(-4.786F, -45.233F, -47.048F), Interpolations.LINEAR),
               new Keyframe(2.12F, KeyframeAnimations.degreeVec(-10.73F, -41.842F, -40.675F), Interpolations.LINEAR),
               new Keyframe(2.17F, KeyframeAnimations.degreeVec(-10.178F, -42.2F, -41.268F), Interpolations.LINEAR),
               new Keyframe(2.25F, KeyframeAnimations.degreeVec(-7.287F, -43.928F, -44.369F), Interpolations.LINEAR),
               new Keyframe(2.38F, KeyframeAnimations.degreeVec(-4.516F, -45.365F, -47.336F), Interpolations.LINEAR),
               new Keyframe(2.42F, KeyframeAnimations.degreeVec(-4.342F, -45.448F, -47.523F), Interpolations.LINEAR),
               new Keyframe(2.5F, KeyframeAnimations.degreeVec(-8.075F, -43.482F, -43.524F), Interpolations.LINEAR),
               new Keyframe(2.71F, KeyframeAnimations.degreeVec(0.141F, -33.644F, -42.983F), Interpolations.LINEAR),
               new Keyframe(2.75F, KeyframeAnimations.degreeVec(-1.086F, -31.421F, -40.288F), Interpolations.LINEAR),
               new Keyframe(2.83F, KeyframeAnimations.degreeVec(-3.861F, -15.19F, -13.55F), Interpolations.LINEAR),
               new Keyframe(2.96F, KeyframeAnimations.degreeVec(-1.925F, 1.443F, 7.651F), Interpolations.LINEAR),
               new Keyframe(3.17F, KeyframeAnimations.degreeVec(-1.07F, 2.918F, 1.095F), Interpolations.LINEAR),
               new Keyframe(3.25F, KeyframeAnimations.degreeVec(0.264F, 0.683F, -0.306F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "CTR_lowerarm_left",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.17F, KeyframeAnimations.degreeVec(-10.786F, 4.175F, 2.289F), Interpolations.LINEAR),
               new Keyframe(0.21F, KeyframeAnimations.degreeVec(-10.786F, -28.706F, 2.289F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(-10.786F, -43.138F, 2.289F), Interpolations.LINEAR),
               new Keyframe(0.33F, KeyframeAnimations.degreeVec(-10.786F, -22.464F, 2.289F), Interpolations.LINEAR),
               new Keyframe(0.38F, KeyframeAnimations.degreeVec(-10.786F, -10.306F, 2.289F), Interpolations.LINEAR),
               new Keyframe(0.42F, KeyframeAnimations.degreeVec(-10.786F, -0.473F, 2.289F), Interpolations.LINEAR),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(-10.786F, -12.895F, 2.289F), Interpolations.LINEAR),
               new Keyframe(0.54F, KeyframeAnimations.degreeVec(-10.786F, -18.149F, 2.289F), Interpolations.LINEAR),
               new Keyframe(0.58F, KeyframeAnimations.degreeVec(-10.786F, -19.12F, 2.289F), Interpolations.LINEAR),
               new Keyframe(0.62F, KeyframeAnimations.degreeVec(-10.786F, -18.289F, 2.289F), Interpolations.LINEAR),
               new Keyframe(0.71F, KeyframeAnimations.degreeVec(-10.786F, -17.175F, 2.289F), Interpolations.LINEAR),
               new Keyframe(1.0F, KeyframeAnimations.degreeVec(-10.786F, -17.175F, 2.289F), Interpolations.LINEAR),
               new Keyframe(1.04F, KeyframeAnimations.degreeVec(-10.786F, -16.176F, 2.289F), Interpolations.LINEAR),
               new Keyframe(1.08F, KeyframeAnimations.degreeVec(-10.786F, -1.199F, 2.289F), Interpolations.LINEAR),
               new Keyframe(1.12F, KeyframeAnimations.degreeVec(-10.786F, 33.404F, 2.289F), Interpolations.LINEAR),
               new Keyframe(1.29F, KeyframeAnimations.degreeVec(-10.786F, 33.406F, 2.289F), Interpolations.LINEAR),
               new Keyframe(1.33F, KeyframeAnimations.degreeVec(-10.786F, -2.46F, 2.289F), Interpolations.LINEAR),
               new Keyframe(1.38F, KeyframeAnimations.degreeVec(-10.786F, -19.84F, 2.289F), Interpolations.LINEAR),
               new Keyframe(1.46F, KeyframeAnimations.degreeVec(-10.786F, -45.346F, 2.289F), Interpolations.LINEAR),
               new Keyframe(1.5F, KeyframeAnimations.degreeVec(-10.786F, -55.782F, 2.289F), Interpolations.LINEAR),
               new Keyframe(1.58F, KeyframeAnimations.degreeVec(-10.786F, -63.329F, 2.289F), Interpolations.LINEAR),
               new Keyframe(1.62F, KeyframeAnimations.degreeVec(-10.786F, -31.879F, 2.289F), Interpolations.LINEAR),
               new Keyframe(1.67F, KeyframeAnimations.degreeVec(-10.786F, -28.198F, 2.289F), Interpolations.LINEAR),
               new Keyframe(1.71F, KeyframeAnimations.degreeVec(-10.786F, -23.135F, 2.289F), Interpolations.LINEAR),
               new Keyframe(1.75F, KeyframeAnimations.degreeVec(-10.786F, -22.623F, 2.289F), Interpolations.LINEAR),
               new Keyframe(1.83F, KeyframeAnimations.degreeVec(-10.786F, -21.109F, 2.289F), Interpolations.LINEAR),
               new Keyframe(1.92F, KeyframeAnimations.degreeVec(-10.786F, -19.726F, 2.289F), Interpolations.LINEAR),
               new Keyframe(1.96F, KeyframeAnimations.degreeVec(-10.786F, -22.788F, 2.289F), Interpolations.LINEAR),
               new Keyframe(2.04F, KeyframeAnimations.degreeVec(-10.786F, -21.623F, 2.289F), Interpolations.LINEAR),
               new Keyframe(2.12F, KeyframeAnimations.degreeVec(-10.786F, -26.592F, 2.289F), Interpolations.LINEAR),
               new Keyframe(2.17F, KeyframeAnimations.degreeVec(-10.786F, -26.109F, 2.289F), Interpolations.LINEAR),
               new Keyframe(2.25F, KeyframeAnimations.degreeVec(-10.786F, -23.65F, 2.289F), Interpolations.LINEAR),
               new Keyframe(2.38F, KeyframeAnimations.degreeVec(-10.786F, -21.409F, 2.289F), Interpolations.LINEAR),
               new Keyframe(2.42F, KeyframeAnimations.degreeVec(-10.786F, -21.273F, 2.289F), Interpolations.LINEAR),
               new Keyframe(2.5F, KeyframeAnimations.degreeVec(-10.786F, -24.307F, 2.289F), Interpolations.LINEAR),
               new Keyframe(2.71F, KeyframeAnimations.degreeVec(-8.693F, -33.045F, 1.215F), Interpolations.LINEAR),
               new Keyframe(2.75F, KeyframeAnimations.degreeVec(-6.654F, -37.965F, -0.291F), Interpolations.LINEAR),
               new Keyframe(2.83F, KeyframeAnimations.degreeVec(20.195F, -50.885F, -25.411F), Interpolations.LINEAR),
               new Keyframe(2.96F, KeyframeAnimations.degreeVec(1.703F, -27.423F, -4.272F), Interpolations.LINEAR),
               new Keyframe(3.17F, KeyframeAnimations.degreeVec(2.759F, -6.151F, -1.654F), Interpolations.LINEAR),
               new Keyframe(3.25F, KeyframeAnimations.degreeVec(-0.001F, 0.003F, 0.001F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "CTR_upperarm_left.001",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(15.668F, 53.211F, -14.02F), Interpolations.LINEAR),
               new Keyframe(0.17F, KeyframeAnimations.degreeVec(-3.234F, 45.875F, 6.744F), Interpolations.LINEAR),
               new Keyframe(0.21F, KeyframeAnimations.degreeVec(1.461F, 35.243F, 1.977F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(1.979F, 23.88F, 2.225F), Interpolations.LINEAR),
               new Keyframe(0.33F, KeyframeAnimations.degreeVec(2.074F, 22.395F, 2.293F), Interpolations.LINEAR),
               new Keyframe(0.38F, KeyframeAnimations.degreeVec(1.795F, 27.267F, 2.11F), Interpolations.LINEAR),
               new Keyframe(0.42F, KeyframeAnimations.degreeVec(1.632F, 30.86F, 2.031F), Interpolations.LINEAR),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(1.733F, 28.571F, 2.077F), Interpolations.LINEAR),
               new Keyframe(0.54F, KeyframeAnimations.degreeVec(1.821F, 26.751F, 2.125F), Interpolations.LINEAR),
               new Keyframe(0.58F, KeyframeAnimations.degreeVec(1.846F, 26.25F, 2.14F), Interpolations.LINEAR),
               new Keyframe(0.71F, KeyframeAnimations.degreeVec(1.814F, 26.888F, 2.121F), Interpolations.LINEAR),
               new Keyframe(1.0F, KeyframeAnimations.degreeVec(1.814F, 26.888F, 2.121F), Interpolations.LINEAR),
               new Keyframe(1.04F, KeyframeAnimations.degreeVec(1.807F, 27.016F, 2.117F), Interpolations.LINEAR),
               new Keyframe(1.08F, KeyframeAnimations.degreeVec(1.579F, 32.175F, 2.011F), Interpolations.LINEAR),
               new Keyframe(1.12F, KeyframeAnimations.degreeVec(1.154F, 44.225F, 1.967F), Interpolations.LINEAR),
               new Keyframe(1.17F, KeyframeAnimations.degreeVec(1.23F, 41.93F, 1.959F), Interpolations.LINEAR),
               new Keyframe(1.29F, KeyframeAnimations.degreeVec(1.241F, 41.603F, 1.958F), Interpolations.LINEAR),
               new Keyframe(1.33F, KeyframeAnimations.degreeVec(1.729F, 28.646F, 2.075F), Interpolations.LINEAR),
               new Keyframe(1.38F, KeyframeAnimations.degreeVec(2.035F, 22.982F, 2.265F), Interpolations.LINEAR),
               new Keyframe(1.46F, KeyframeAnimations.degreeVec(2.81F, 14.956F, 2.916F), Interpolations.LINEAR),
               new Keyframe(1.5F, KeyframeAnimations.degreeVec(4.373F, 9.048F, 4.417F), Interpolations.LINEAR),
               new Keyframe(1.58F, KeyframeAnimations.degreeVec(-14.775F, 0.202F, -14.78F), Interpolations.LINEAR),
               new Keyframe(1.62F, KeyframeAnimations.degreeVec(3.286F, 12.369F, 3.362F), Interpolations.LINEAR),
               new Keyframe(1.67F, KeyframeAnimations.degreeVec(4.312F, 9.18F, 4.357F), Interpolations.LINEAR),
               new Keyframe(1.71F, KeyframeAnimations.degreeVec(4.512F, 8.768F, 4.553F), Interpolations.LINEAR),
               new Keyframe(1.75F, KeyframeAnimations.degreeVec(4.604F, 8.594F, 4.644F), Interpolations.LINEAR),
               new Keyframe(1.83F, KeyframeAnimations.degreeVec(4.508F, 8.776F, 4.549F), Interpolations.LINEAR),
               new Keyframe(1.92F, KeyframeAnimations.degreeVec(4.287F, 9.234F, 4.333F), Interpolations.LINEAR),
               new Keyframe(1.96F, KeyframeAnimations.degreeVec(4.731F, 8.365F, 4.769F), Interpolations.LINEAR),
               new Keyframe(2.04F, KeyframeAnimations.degreeVec(4.541F, 8.712F, 4.581F), Interpolations.LINEAR),
               new Keyframe(2.12F, KeyframeAnimations.degreeVec(5.54F, 7.209F, 5.569F), Interpolations.LINEAR),
               new Keyframe(2.17F, KeyframeAnimations.degreeVec(5.417F, 7.358F, 5.447F), Interpolations.LINEAR),
               new Keyframe(2.25F, KeyframeAnimations.degreeVec(4.887F, 8.106F, 4.923F), Interpolations.LINEAR),
               new Keyframe(2.38F, KeyframeAnimations.degreeVec(4.507F, 8.776F, 4.549F), Interpolations.LINEAR),
               new Keyframe(2.42F, KeyframeAnimations.degreeVec(4.487F, 8.816F, 4.529F), Interpolations.LINEAR),
               new Keyframe(2.5F, KeyframeAnimations.degreeVec(5.015F, 7.908F, 5.049F), Interpolations.LINEAR),
               new Keyframe(2.71F, KeyframeAnimations.degreeVec(4.62F, 8.596F, 4.607F), Interpolations.LINEAR),
               new Keyframe(2.75F, KeyframeAnimations.degreeVec(3.692F, 10.831F, 3.752F), Interpolations.LINEAR),
               new Keyframe(2.83F, KeyframeAnimations.degreeVec(-0.114F, -13.045F, -1.086F), Interpolations.LINEAR),
               new Keyframe(2.96F, KeyframeAnimations.degreeVec(2.723F, -42.236F, -1.612F), Interpolations.LINEAR),
               new Keyframe(3.17F, KeyframeAnimations.degreeVec(2.814F, -28.116F, -2.102F), Interpolations.LINEAR),
               new Keyframe(3.25F, KeyframeAnimations.degreeVec(2.092F, -23.349F, -1.724F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "CTR_upperarm_left.001",
         new AnimationChannel(
            Targets.SCALE,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 7.497F), Interpolations.LINEAR),
               new Keyframe(0.17F, KeyframeAnimations.scaleVec(1.0, 1.0, 2.822F), Interpolations.LINEAR),
               new Keyframe(0.21F, KeyframeAnimations.scaleVec(1.0, 1.0, 2.32F), Interpolations.LINEAR),
               new Keyframe(0.33F, KeyframeAnimations.scaleVec(1.0, 1.0, 2.334F), Interpolations.LINEAR),
               new Keyframe(0.38F, KeyframeAnimations.scaleVec(1.0, 1.0, 2.357F), Interpolations.LINEAR),
               new Keyframe(0.42F, KeyframeAnimations.scaleVec(1.0, 1.0, 2.385F), Interpolations.LINEAR),
               new Keyframe(0.5F, KeyframeAnimations.scaleVec(1.0, 1.0, 2.343F), Interpolations.LINEAR),
               new Keyframe(0.58F, KeyframeAnimations.scaleVec(1.0, 1.0, 2.332F), Interpolations.LINEAR),
               new Keyframe(1.04F, KeyframeAnimations.scaleVec(1.0, 1.0, 2.338F), Interpolations.LINEAR),
               new Keyframe(1.08F, KeyframeAnimations.scaleVec(1.0, 1.0, 2.375), Interpolations.LINEAR),
               new Keyframe(1.12F, KeyframeAnimations.scaleVec(1.0, 1.0, 2.428F), Interpolations.LINEAR),
               new Keyframe(1.17F, KeyframeAnimations.scaleVec(1.0, 1.0, 2.283F), Interpolations.LINEAR),
               new Keyframe(1.29F, KeyframeAnimations.scaleVec(1.0, 1.0, 2.146F), Interpolations.LINEAR),
               new Keyframe(1.33F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.95F), Interpolations.LINEAR),
               new Keyframe(1.38F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.871F), Interpolations.LINEAR),
               new Keyframe(1.46F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.828F), Interpolations.LINEAR),
               new Keyframe(1.5F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.767F), Interpolations.LINEAR),
               new Keyframe(1.58F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.592F), Interpolations.LINEAR),
               new Keyframe(1.62F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.746F), Interpolations.LINEAR),
               new Keyframe(1.67F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.72F), Interpolations.LINEAR),
               new Keyframe(1.71F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.734F), Interpolations.LINEAR),
               new Keyframe(1.92F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.748F), Interpolations.LINEAR),
               new Keyframe(1.96F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.737F), Interpolations.LINEAR),
               new Keyframe(2.04F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.741F), Interpolations.LINEAR),
               new Keyframe(2.12F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.726F), Interpolations.LINEAR),
               new Keyframe(2.42F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.743F), Interpolations.LINEAR),
               new Keyframe(2.5F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.733F), Interpolations.LINEAR),
               new Keyframe(2.71F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.744F), Interpolations.LINEAR),
               new Keyframe(2.75F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.758F), Interpolations.LINEAR),
               new Keyframe(2.83F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.532F), Interpolations.LINEAR),
               new Keyframe(2.96F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.498F), Interpolations.LINEAR),
               new Keyframe(3.17F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.811F), Interpolations.LINEAR),
               new Keyframe(3.25F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.828F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "CTR_shoulder_right",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.21F, KeyframeAnimations.posVec(5.43F, -3.391F, -4.071F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.posVec(3.975F, -15.281F, -1.368F), Interpolations.LINEAR),
               new Keyframe(0.33F, KeyframeAnimations.posVec(5.43F, -3.391F, -4.071F), Interpolations.LINEAR),
               new Keyframe(2.5F, KeyframeAnimations.posVec(5.43F, -3.391F, -4.071F), Interpolations.LINEAR),
               new Keyframe(2.71F, KeyframeAnimations.posVec(3.879F, -2.423F, -2.909F), Interpolations.LINEAR),
               new Keyframe(2.83F, KeyframeAnimations.posVec(2.15F, -1.343F, -1.612F), Interpolations.LINEAR),
               new Keyframe(2.96F, KeyframeAnimations.posVec(0.805F, -0.503F, -0.604F), Interpolations.LINEAR),
               new Keyframe(3.17F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "CTR_upperarm_right",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(-127.475F, 13.729F, -4.205F), Interpolations.LINEAR),
               new Keyframe(0.17F, KeyframeAnimations.degreeVec(-142.984F, 15.484F, 8.549F), Interpolations.LINEAR),
               new Keyframe(0.21F, KeyframeAnimations.degreeVec(17.011F, 11.87F, 50.128F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(14.044F, 9.479F, 49.707F), Interpolations.LINEAR),
               new Keyframe(0.33F, KeyframeAnimations.degreeVec(7.942F, 6.248F, 41.68F), Interpolations.LINEAR),
               new Keyframe(0.38F, KeyframeAnimations.degreeVec(5.995F, 5.093F, 42.015F), Interpolations.LINEAR),
               new Keyframe(0.42F, KeyframeAnimations.degreeVec(5.256F, 4.663F, 41.565F), Interpolations.LINEAR),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(5.059F, 4.586F, 39.203F), Interpolations.LINEAR),
               new Keyframe(0.54F, KeyframeAnimations.degreeVec(5.02F, 4.573F, 38.721F), Interpolations.LINEAR),
               new Keyframe(0.62F, KeyframeAnimations.degreeVec(5.016F, 4.585F, 38.912F), Interpolations.LINEAR),
               new Keyframe(1.0F, KeyframeAnimations.degreeVec(5.022F, 4.584F, 38.963F), Interpolations.LINEAR),
               new Keyframe(1.04F, KeyframeAnimations.degreeVec(5.027F, 4.595F, 39.197F), Interpolations.LINEAR),
               new Keyframe(1.08F, KeyframeAnimations.degreeVec(4.636F, 3.994F, 38.96F), Interpolations.LINEAR),
               new Keyframe(1.12F, KeyframeAnimations.degreeVec(4.124F, 2.485F, 41.133F), Interpolations.LINEAR),
               new Keyframe(1.17F, KeyframeAnimations.degreeVec(4.739F, 2.412F, 50.041F), Interpolations.LINEAR),
               new Keyframe(1.29F, KeyframeAnimations.degreeVec(-2.495F, 7.075F, 36.077F), Interpolations.LINEAR),
               new Keyframe(1.33F, KeyframeAnimations.degreeVec(-10.898F, 3.635F, 16.679F), Interpolations.LINEAR),
               new Keyframe(1.38F, KeyframeAnimations.degreeVec(-17.218F, 0.493F, 11.053F), Interpolations.LINEAR),
               new Keyframe(1.46F, KeyframeAnimations.degreeVec(-27.626F, -6.412F, 9.915F), Interpolations.LINEAR),
               new Keyframe(1.5F, KeyframeAnimations.degreeVec(-32.178F, -10.37F, 12.828F), Interpolations.LINEAR),
               new Keyframe(1.58F, KeyframeAnimations.degreeVec(-42.751F, -4.925F, 20.65F), Interpolations.LINEAR),
               new Keyframe(1.62F, KeyframeAnimations.degreeVec(-40.585F, 24.014F, 24.841F), Interpolations.LINEAR),
               new Keyframe(1.67F, KeyframeAnimations.degreeVec(-46.703F, 27.457F, 19.725F), Interpolations.LINEAR),
               new Keyframe(1.71F, KeyframeAnimations.degreeVec(-45.615F, 28.337F, 18.867F), Interpolations.LINEAR),
               new Keyframe(1.75F, KeyframeAnimations.degreeVec(-44.53F, 30.51F, 19.177F), Interpolations.LINEAR),
               new Keyframe(1.83F, KeyframeAnimations.degreeVec(-45.308F, 29.991F, 18.463F), Interpolations.LINEAR),
               new Keyframe(1.92F, KeyframeAnimations.degreeVec(-46.252F, 28.554F, 17.694F), Interpolations.LINEAR),
               new Keyframe(1.96F, KeyframeAnimations.degreeVec(-44.566F, 30.813F, 19.25F), Interpolations.LINEAR),
               new Keyframe(2.04F, KeyframeAnimations.degreeVec(-45.285F, 29.84F, 18.609F), Interpolations.LINEAR),
               new Keyframe(2.12F, KeyframeAnimations.degreeVec(-41.941F, 34.067F, 21.634F), Interpolations.LINEAR),
               new Keyframe(2.17F, KeyframeAnimations.degreeVec(-42.303F, 33.646F, 21.303F), Interpolations.LINEAR),
               new Keyframe(2.25F, KeyframeAnimations.degreeVec(-44.009F, 31.541F, 19.75F), Interpolations.LINEAR),
               new Keyframe(2.38F, KeyframeAnimations.degreeVec(-45.412F, 29.663F, 18.496F), Interpolations.LINEAR),
               new Keyframe(2.42F, KeyframeAnimations.degreeVec(-45.495F, 29.549F, 18.423F), Interpolations.LINEAR),
               new Keyframe(2.5F, KeyframeAnimations.degreeVec(-43.571F, 32.099F, 20.146F), Interpolations.LINEAR),
               new Keyframe(2.71F, KeyframeAnimations.degreeVec(-31.509F, 25.126F, 26.029F), Interpolations.LINEAR),
               new Keyframe(2.75F, KeyframeAnimations.degreeVec(-23.538F, 21.752F, 28.955F), Interpolations.LINEAR),
               new Keyframe(2.83F, KeyframeAnimations.degreeVec(-12.027F, 16.523F, 32.432F), Interpolations.LINEAR),
               new Keyframe(2.96F, KeyframeAnimations.degreeVec(-1.129F, 8.132F, 35.059F), Interpolations.LINEAR),
               new Keyframe(3.17F, KeyframeAnimations.degreeVec(-0.702F, -0.455F, -1.199F), Interpolations.LINEAR),
               new Keyframe(3.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "CTR_lowerarm_right",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.17F, KeyframeAnimations.degreeVec(-0.363F, -14.389F, 9.391F), Interpolations.LINEAR),
               new Keyframe(0.21F, KeyframeAnimations.degreeVec(-0.363F, 11.064F, 9.391F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(-0.363F, 43.525F, 9.391F), Interpolations.LINEAR),
               new Keyframe(0.33F, KeyframeAnimations.degreeVec(-0.363F, 42.594F, 9.391F), Interpolations.LINEAR),
               new Keyframe(0.38F, KeyframeAnimations.degreeVec(-0.363F, 28.889F, 9.391F), Interpolations.LINEAR),
               new Keyframe(0.42F, KeyframeAnimations.degreeVec(-0.363F, 25.44F, 9.391F), Interpolations.LINEAR),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(-0.363F, 33.633F, 9.391F), Interpolations.LINEAR),
               new Keyframe(0.54F, KeyframeAnimations.degreeVec(-0.363F, 37.479F, 9.391F), Interpolations.LINEAR),
               new Keyframe(0.58F, KeyframeAnimations.degreeVec(-0.363F, 38.214F, 9.391F), Interpolations.LINEAR),
               new Keyframe(0.62F, KeyframeAnimations.degreeVec(-0.363F, 37.587F, 9.391F), Interpolations.LINEAR),
               new Keyframe(0.71F, KeyframeAnimations.degreeVec(-0.363F, 36.752F, 9.391F), Interpolations.LINEAR),
               new Keyframe(1.0F, KeyframeAnimations.degreeVec(-0.363F, 36.752F, 9.391F), Interpolations.LINEAR),
               new Keyframe(1.04F, KeyframeAnimations.degreeVec(-0.363F, 36.01F, 9.391F), Interpolations.LINEAR),
               new Keyframe(1.08F, KeyframeAnimations.degreeVec(-0.363F, 27.854F, 9.391F), Interpolations.LINEAR),
               new Keyframe(1.12F, KeyframeAnimations.degreeVec(-0.363F, 1.467F, 9.391F), Interpolations.LINEAR),
               new Keyframe(1.17F, KeyframeAnimations.degreeVec(-0.363F, -38.611F, 9.391F), Interpolations.LINEAR),
               new Keyframe(1.29F, KeyframeAnimations.degreeVec(-0.363F, -36.02F, 9.391F), Interpolations.LINEAR),
               new Keyframe(1.33F, KeyframeAnimations.degreeVec(-0.363F, 5.021F, 9.391F), Interpolations.LINEAR),
               new Keyframe(1.38F, KeyframeAnimations.degreeVec(-0.363F, 22.955F, 9.391F), Interpolations.LINEAR),
               new Keyframe(1.5F, KeyframeAnimations.degreeVec(-0.363F, 60.588F, 9.391F), Interpolations.LINEAR),
               new Keyframe(1.58F, KeyframeAnimations.degreeVec(-0.363F, 76.139F, 9.391F), Interpolations.LINEAR),
               new Keyframe(1.62F, KeyframeAnimations.degreeVec(-0.363F, 44.251F, 9.391F), Interpolations.LINEAR),
               new Keyframe(1.67F, KeyframeAnimations.degreeVec(-0.363F, 42.076F, 9.391F), Interpolations.LINEAR),
               new Keyframe(1.71F, KeyframeAnimations.degreeVec(-0.363F, 38.748F, 9.391F), Interpolations.LINEAR),
               new Keyframe(1.75F, KeyframeAnimations.degreeVec(-0.363F, 36.155F, 9.391F), Interpolations.LINEAR),
               new Keyframe(1.83F, KeyframeAnimations.degreeVec(-0.363F, 36.684F, 9.391F), Interpolations.LINEAR),
               new Keyframe(1.92F, KeyframeAnimations.degreeVec(-0.363F, 38.0F, 9.391F), Interpolations.LINEAR),
               new Keyframe(1.96F, KeyframeAnimations.degreeVec(-0.363F, 36.07F, 9.391F), Interpolations.LINEAR),
               new Keyframe(2.04F, KeyframeAnimations.degreeVec(-0.363F, 36.94F, 9.391F), Interpolations.LINEAR),
               new Keyframe(2.12F, KeyframeAnimations.degreeVec(-0.363F, 33.028F, 9.391F), Interpolations.LINEAR),
               new Keyframe(2.17F, KeyframeAnimations.degreeVec(-0.363F, 33.436F, 9.391F), Interpolations.LINEAR),
               new Keyframe(2.25F, KeyframeAnimations.degreeVec(-0.363F, 35.409F, 9.391F), Interpolations.LINEAR),
               new Keyframe(2.38F, KeyframeAnimations.degreeVec(-0.363F, 37.094F, 9.391F), Interpolations.LINEAR),
               new Keyframe(2.42F, KeyframeAnimations.degreeVec(-0.363F, 37.196F, 9.391F), Interpolations.LINEAR),
               new Keyframe(2.5F, KeyframeAnimations.degreeVec(-0.363F, 34.894F, 9.391F), Interpolations.LINEAR),
               new Keyframe(2.71F, KeyframeAnimations.degreeVec(-7.121F, 38.039F, 1.782F), Interpolations.LINEAR),
               new Keyframe(2.75F, KeyframeAnimations.degreeVec(-7.644F, 35.298F, 0.834F), Interpolations.LINEAR),
               new Keyframe(2.83F, KeyframeAnimations.degreeVec(-7.488F, 28.974F, -0.208F), Interpolations.LINEAR),
               new Keyframe(2.96F, KeyframeAnimations.degreeVec(-5.093F, 18.912F, -0.563F), Interpolations.LINEAR),
               new Keyframe(3.17F, KeyframeAnimations.degreeVec(0.08F, 5.608F, 0.052F), Interpolations.LINEAR),
               new Keyframe(3.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "CTR_upperarm_right.001",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(22.181F, -55.099F, 25.987F), Interpolations.LINEAR),
               new Keyframe(0.17F, KeyframeAnimations.degreeVec(7.069F, -49.255F, 10.041F), Interpolations.LINEAR),
               new Keyframe(0.21F, KeyframeAnimations.degreeVec(-1.199F, -34.384F, 1.21F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(-1.257F, -19.135F, 1.263F), Interpolations.LINEAR),
               new Keyframe(0.33F, KeyframeAnimations.degreeVec(-1.439F, -8.663F, 1.442F), Interpolations.LINEAR),
               new Keyframe(0.38F, KeyframeAnimations.degreeVec(-1.367F, -10.841F, 1.371F), Interpolations.LINEAR),
               new Keyframe(0.42F, KeyframeAnimations.degreeVec(-1.336F, -12.29F, 1.34F), Interpolations.LINEAR),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(-1.344F, -11.854F, 1.348F), Interpolations.LINEAR),
               new Keyframe(0.54F, KeyframeAnimations.degreeVec(-1.367F, -10.833F, 1.371F), Interpolations.LINEAR),
               new Keyframe(0.58F, KeyframeAnimations.degreeVec(-1.376F, -10.517F, 1.379F), Interpolations.LINEAR),
               new Keyframe(0.71F, KeyframeAnimations.degreeVec(-1.366F, -10.871F, 1.37F), Interpolations.LINEAR),
               new Keyframe(1.04F, KeyframeAnimations.degreeVec(-1.366F, -10.893F, 1.369F), Interpolations.LINEAR),
               new Keyframe(1.08F, KeyframeAnimations.degreeVec(-1.335F, -12.343F, 1.339F), Interpolations.LINEAR),
               new Keyframe(1.12F, KeyframeAnimations.degreeVec(-1.25F, -20.2F, 1.257F), Interpolations.LINEAR),
               new Keyframe(1.17F, KeyframeAnimations.degreeVec(-1.197F, -35.021F, 1.209F), Interpolations.LINEAR),
               new Keyframe(1.29F, KeyframeAnimations.degreeVec(-1.196F, -35.575F, 1.208F), Interpolations.LINEAR),
               new Keyframe(1.33F, KeyframeAnimations.degreeVec(-1.25F, -20.174F, 1.257F), Interpolations.LINEAR),
               new Keyframe(1.38F, KeyframeAnimations.degreeVec(-1.307F, -14.09F, 1.311F), Interpolations.LINEAR),
               new Keyframe(1.46F, KeyframeAnimations.degreeVec(-1.754F, -5.135F, 1.756F), Interpolations.LINEAR),
               new Keyframe(1.5F, KeyframeAnimations.degreeVec(-0.024F, 0.038F, 0.024F), Interpolations.LINEAR),
               new Keyframe(1.58F, KeyframeAnimations.degreeVec(-1.03F, 15.766F, 1.025F), Interpolations.LINEAR),
               new Keyframe(1.62F, KeyframeAnimations.degreeVec(0.868F, -0.772F, -0.868F), Interpolations.LINEAR),
               new Keyframe(1.67F, KeyframeAnimations.degreeVec(-0.685F, 2.675F, 0.684F), Interpolations.LINEAR),
               new Keyframe(1.71F, KeyframeAnimations.degreeVec(-0.595F, 1.946F, 0.595F), Interpolations.LINEAR),
               new Keyframe(1.75F, KeyframeAnimations.degreeVec(-0.324F, 0.708F, 0.324F), Interpolations.LINEAR),
               new Keyframe(1.83F, KeyframeAnimations.degreeVec(-0.297F, 0.63F, 0.297F), Interpolations.LINEAR),
               new Keyframe(1.92F, KeyframeAnimations.degreeVec(-0.459F, 1.202F, 0.459F), Interpolations.LINEAR),
               new Keyframe(1.96F, KeyframeAnimations.degreeVec(-0.232F, 0.457F, 0.232F), Interpolations.LINEAR),
               new Keyframe(2.04F, KeyframeAnimations.degreeVec(-0.353F, 0.801F, 0.353F), Interpolations.LINEAR),
               new Keyframe(2.12F, KeyframeAnimations.degreeVec(0.729F, -0.697F, -0.729F), Interpolations.LINEAR),
               new Keyframe(2.17F, KeyframeAnimations.degreeVec(0.504F, -0.547F, -0.503F), Interpolations.LINEAR),
               new Keyframe(2.25F, KeyframeAnimations.degreeVec(-0.114F, 0.199F, 0.114F), Interpolations.LINEAR),
               new Keyframe(2.38F, KeyframeAnimations.degreeVec(-0.372F, 0.863F, 0.372F), Interpolations.LINEAR),
               new Keyframe(2.42F, KeyframeAnimations.degreeVec(-0.383F, 0.904F, 0.383F), Interpolations.LINEAR),
               new Keyframe(2.5F, KeyframeAnimations.degreeVec(0.0F, 0.002F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.71F, KeyframeAnimations.degreeVec(-1.845F, -4.673F, 1.888F), Interpolations.LINEAR),
               new Keyframe(2.75F, KeyframeAnimations.degreeVec(-2.291F, -3.574F, 2.365F), Interpolations.LINEAR),
               new Keyframe(2.83F, KeyframeAnimations.degreeVec(-1.621F, -5.847F, 1.715F), Interpolations.LINEAR),
               new Keyframe(2.96F, KeyframeAnimations.degreeVec(-1.385F, -9.624F, 1.434F), Interpolations.LINEAR),
               new Keyframe(3.17F, KeyframeAnimations.degreeVec(0.496F, 22.384F, 2.403F), Interpolations.LINEAR),
               new Keyframe(3.25F, KeyframeAnimations.degreeVec(0.229F, 18.405F, 2.109F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "CTR_upperarm_right.001",
         new AnimationChannel(
            Targets.SCALE,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 7.574F), Interpolations.LINEAR),
               new Keyframe(0.17F, KeyframeAnimations.scaleVec(1.0, 1.0, 2.687F), Interpolations.LINEAR),
               new Keyframe(0.21F, KeyframeAnimations.scaleVec(1.0, 1.0, 2.062F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.scaleVec(1.0, 1.0, 2.066F), Interpolations.LINEAR),
               new Keyframe(0.33F, KeyframeAnimations.scaleVec(1.0, 1.0, 2.057F), Interpolations.LINEAR),
               new Keyframe(0.42F, KeyframeAnimations.scaleVec(1.0, 1.0, 2.078F), Interpolations.LINEAR),
               new Keyframe(0.54F, KeyframeAnimations.scaleVec(1.0, 1.0, 2.059F), Interpolations.LINEAR),
               new Keyframe(1.04F, KeyframeAnimations.scaleVec(1.0, 1.0, 2.06F), Interpolations.LINEAR),
               new Keyframe(1.08F, KeyframeAnimations.scaleVec(1.0, 1.0, 2.072F), Interpolations.LINEAR),
               new Keyframe(1.12F, KeyframeAnimations.scaleVec(1.0, 1.0, 2.147F), Interpolations.LINEAR),
               new Keyframe(1.17F, KeyframeAnimations.scaleVec(1.0, 1.0, 2.274F), Interpolations.LINEAR),
               new Keyframe(1.29F, KeyframeAnimations.scaleVec(1.0, 1.0, 2.045F), Interpolations.LINEAR),
               new Keyframe(1.33F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.791F), Interpolations.LINEAR),
               new Keyframe(1.38F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.702F), Interpolations.LINEAR),
               new Keyframe(1.46F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.644F), Interpolations.LINEAR),
               new Keyframe(1.5F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.646F), Interpolations.LINEAR),
               new Keyframe(1.58F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.422F), Interpolations.LINEAR),
               new Keyframe(1.62F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.644F), Interpolations.LINEAR),
               new Keyframe(1.75F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.655F), Interpolations.LINEAR),
               new Keyframe(2.04F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.653F), Interpolations.LINEAR),
               new Keyframe(2.42F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.652F), Interpolations.LINEAR),
               new Keyframe(2.71F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.661F), Interpolations.LINEAR),
               new Keyframe(2.75F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.689F), Interpolations.LINEAR),
               new Keyframe(2.83F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.767F), Interpolations.LINEAR),
               new Keyframe(2.96F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.938F), Interpolations.LINEAR),
               new Keyframe(3.17F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.642F), Interpolations.LINEAR),
               new Keyframe(3.25F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.66F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "CTR_leg_left",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(-174.603F, 26.087F, 185.395F), Interpolations.LINEAR),
               new Keyframe(0.17F, KeyframeAnimations.degreeVec(-175.107F, 22.741F, 184.893F), Interpolations.LINEAR),
               new Keyframe(0.21F, KeyframeAnimations.degreeVec(-180.072F, 28.264F, 179.927F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(-198.201F, 49.217F, 161.799F), Interpolations.LINEAR),
               new Keyframe(0.33F, KeyframeAnimations.degreeVec(-180.212F, 55.389F, 179.788F), Interpolations.LINEAR),
               new Keyframe(0.38F, KeyframeAnimations.degreeVec(-179.6F, 51.498F, 180.4F), Interpolations.LINEAR),
               new Keyframe(0.42F, KeyframeAnimations.degreeVec(-179.54F, 49.71F, 180.46F), Interpolations.LINEAR),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(-179.928F, 50.152F, 180.072F), Interpolations.LINEAR),
               new Keyframe(0.54F, KeyframeAnimations.degreeVec(-180.034F, 51.574F, 179.966F), Interpolations.LINEAR),
               new Keyframe(0.58F, KeyframeAnimations.degreeVec(-180.043F, 52.159F, 179.957F), Interpolations.LINEAR),
               new Keyframe(0.62F, KeyframeAnimations.degreeVec(-180.02F, 52.065F, 179.98F), Interpolations.LINEAR),
               new Keyframe(0.71F, KeyframeAnimations.degreeVec(-180.0F, 51.652F, 180.0F), Interpolations.LINEAR),
               new Keyframe(1.04F, KeyframeAnimations.degreeVec(-179.962F, 51.7F, 180.037F), Interpolations.LINEAR),
               new Keyframe(1.08F, KeyframeAnimations.degreeVec(-179.143F, 51.945F, 180.857F), Interpolations.LINEAR),
               new Keyframe(1.12F, KeyframeAnimations.degreeVec(-176.85F, 51.902F, 183.15F), Interpolations.LINEAR),
               new Keyframe(1.17F, KeyframeAnimations.degreeVec(-174.502F, 51.049F, 185.498F), Interpolations.LINEAR),
               new Keyframe(1.29F, KeyframeAnimations.degreeVec(-172.184F, 49.259F, 187.816F), Interpolations.LINEAR),
               new Keyframe(1.33F, KeyframeAnimations.degreeVec(-172.249F, 49.358F, 187.751F), Interpolations.LINEAR),
               new Keyframe(1.38F, KeyframeAnimations.degreeVec(-172.479F, 49.649F, 187.521F), Interpolations.LINEAR),
               new Keyframe(1.46F, KeyframeAnimations.degreeVec(-173.817F, 50.729F, 186.183F), Interpolations.LINEAR),
               new Keyframe(1.5F, KeyframeAnimations.degreeVec(-175.313F, 51.373F, 184.687F), Interpolations.LINEAR),
               new Keyframe(1.58F, KeyframeAnimations.degreeVec(-179.971F, 51.689F, 180.029F), Interpolations.LINEAR),
               new Keyframe(1.62F, KeyframeAnimations.degreeVec(-181.164F, 51.484F, 178.836F), Interpolations.LINEAR),
               new Keyframe(1.67F, KeyframeAnimations.degreeVec(-180.712F, 51.436F, 179.288F), Interpolations.LINEAR),
               new Keyframe(1.71F, KeyframeAnimations.degreeVec(-180.0F, 51.652F, 180.0F), Interpolations.LINEAR),
               new Keyframe(1.75F, KeyframeAnimations.degreeVec(-179.989F, 51.784F, 180.011F), Interpolations.LINEAR),
               new Keyframe(1.83F, KeyframeAnimations.degreeVec(-180.0F, 51.652F, 180.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "CTR_leg_left",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.17F, KeyframeAnimations.posVec(1.409F, -7.188F, -3.527F), Interpolations.LINEAR),
               new Keyframe(0.21F, KeyframeAnimations.posVec(-1.897F, 10.377F, -2.769F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.posVec(-17.6F, 9.477F, 6.359F), Interpolations.LINEAR),
               new Keyframe(0.33F, KeyframeAnimations.posVec(-0.102F, 0.077F, 0.657F), Interpolations.LINEAR),
               new Keyframe(0.38F, KeyframeAnimations.posVec(0.584F, -2.623F, -1.307F), Interpolations.LINEAR),
               new Keyframe(0.42F, KeyframeAnimations.posVec(0.595F, -2.592F, -1.469F), Interpolations.LINEAR),
               new Keyframe(0.5F, KeyframeAnimations.posVec(0.038F, -0.047F, -0.256F), Interpolations.LINEAR),
               new Keyframe(0.54F, KeyframeAnimations.posVec(-0.059F, 0.256F, 0.113F), Interpolations.LINEAR),
               new Keyframe(0.58F, KeyframeAnimations.posVec(-0.043F, 0.142F, 0.152F), Interpolations.LINEAR),
               new Keyframe(0.62F, KeyframeAnimations.posVec(-0.01F, 0.011F, 0.071F), Interpolations.LINEAR),
               new Keyframe(0.71F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.04F, KeyframeAnimations.posVec(0.061F, -0.263F, -0.123F), Interpolations.LINEAR),
               new Keyframe(1.08F, KeyframeAnimations.posVec(0.426F, -1.185F, -0.555F), Interpolations.LINEAR),
               new Keyframe(1.12F, KeyframeAnimations.posVec(1.078F, -2.939F, -1.443F), Interpolations.LINEAR),
               new Keyframe(1.17F, KeyframeAnimations.posVec(1.429F, -4.959F, -2.477F), Interpolations.LINEAR),
               new Keyframe(1.29F, KeyframeAnimations.posVec(1.409F, -7.188F, -3.527F), Interpolations.LINEAR),
               new Keyframe(1.33F, KeyframeAnimations.posVec(1.409F, -7.188F, -3.527F), Interpolations.LINEAR),
               new Keyframe(1.38F, KeyframeAnimations.posVec(1.416F, -7.1F, -3.488F), Interpolations.LINEAR),
               new Keyframe(1.46F, KeyframeAnimations.posVec(1.491F, -4.777F, -2.407F), Interpolations.LINEAR),
               new Keyframe(1.5F, KeyframeAnimations.posVec(1.326F, -3.229F, -1.634F), Interpolations.LINEAR),
               new Keyframe(1.58F, KeyframeAnimations.posVec(0.047F, -0.204F, -0.095F), Interpolations.LINEAR),
               new Keyframe(1.62F, KeyframeAnimations.posVec(-0.407F, 0.357F, 0.172F), Interpolations.LINEAR),
               new Keyframe(1.67F, KeyframeAnimations.posVec(-0.245F, 0.221F, 0.088F), Interpolations.LINEAR),
               new Keyframe(1.71F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.75F, KeyframeAnimations.posVec(0.003F, -0.002F, 0.019F), Interpolations.LINEAR),
               new Keyframe(1.83F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "CTR_leg_right",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(181.507F, 17.536F, 178.492F), Interpolations.LINEAR),
               new Keyframe(0.17F, KeyframeAnimations.degreeVec(180.229F, 13.482F, 179.771F), Interpolations.LINEAR),
               new Keyframe(0.21F, KeyframeAnimations.degreeVec(174.578F, 7.707F, 185.422F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(168.823F, 17.62F, 191.177F), Interpolations.LINEAR),
               new Keyframe(0.33F, KeyframeAnimations.degreeVec(179.996F, 41.883F, 180.005F), Interpolations.LINEAR),
               new Keyframe(0.38F, KeyframeAnimations.degreeVec(180.409F, 38.748F, 179.591F), Interpolations.LINEAR),
               new Keyframe(0.42F, KeyframeAnimations.degreeVec(180.396F, 36.903F, 179.604F), Interpolations.LINEAR),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(180.009F, 36.672F, 179.991F), Interpolations.LINEAR),
               new Keyframe(0.54F, KeyframeAnimations.degreeVec(179.95F, 38.031F, 180.05F), Interpolations.LINEAR),
               new Keyframe(0.58F, KeyframeAnimations.degreeVec(179.971F, 38.65F, 180.029F), Interpolations.LINEAR),
               new Keyframe(0.62F, KeyframeAnimations.degreeVec(179.997F, 38.59F, 180.003F), Interpolations.LINEAR),
               new Keyframe(0.71F, KeyframeAnimations.degreeVec(180.0F, 38.178F, 180.0F), Interpolations.LINEAR),
               new Keyframe(1.0F, KeyframeAnimations.degreeVec(180.0F, 38.178F, 180.0F), Interpolations.LINEAR),
               new Keyframe(1.04F, KeyframeAnimations.degreeVec(180.049F, 38.298F, 179.951F), Interpolations.LINEAR),
               new Keyframe(1.08F, KeyframeAnimations.degreeVec(179.805F, 35.864F, 180.195F), Interpolations.LINEAR),
               new Keyframe(1.12F, KeyframeAnimations.degreeVec(179.027F, 28.391F, 180.973F), Interpolations.LINEAR),
               new Keyframe(1.17F, KeyframeAnimations.degreeVec(178.414F, 21.09F, 181.587F), Interpolations.LINEAR),
               new Keyframe(1.29F, KeyframeAnimations.degreeVec(177.994F, 14.677F, 182.005F), Interpolations.LINEAR),
               new Keyframe(1.33F, KeyframeAnimations.degreeVec(177.986F, 14.776F, 182.013F), Interpolations.LINEAR),
               new Keyframe(1.38F, KeyframeAnimations.degreeVec(177.973F, 15.233F, 182.027F), Interpolations.LINEAR),
               new Keyframe(1.46F, KeyframeAnimations.degreeVec(178.052F, 18.704F, 181.946F), Interpolations.LINEAR),
               new Keyframe(1.5F, KeyframeAnimations.degreeVec(178.333F, 23.107F, 181.667F), Interpolations.LINEAR),
               new Keyframe(1.58F, KeyframeAnimations.degreeVec(180.038F, 38.271F, 179.962F), Interpolations.LINEAR),
               new Keyframe(1.62F, KeyframeAnimations.degreeVec(180.763F, 41.456F, 179.237F), Interpolations.LINEAR),
               new Keyframe(1.67F, KeyframeAnimations.degreeVec(180.471F, 40.032F, 179.529F), Interpolations.LINEAR),
               new Keyframe(1.71F, KeyframeAnimations.degreeVec(180.0F, 38.178F, 180.0F), Interpolations.LINEAR),
               new Keyframe(1.75F, KeyframeAnimations.degreeVec(180.01F, 38.057F, 179.99F), Interpolations.LINEAR),
               new Keyframe(1.83F, KeyframeAnimations.degreeVec(180.0F, 38.178F, 180.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "CTR_leg_right",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.17F, KeyframeAnimations.posVec(-0.396F, -5.152F, -1.787F), Interpolations.LINEAR),
               new Keyframe(0.21F, KeyframeAnimations.posVec(4.044F, 11.119F, -5.151F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.posVec(10.382F, 16.346F, -2.387F), Interpolations.LINEAR),
               new Keyframe(0.33F, KeyframeAnimations.posVec(0.011F, 0.067F, 0.411F), Interpolations.LINEAR),
               new Keyframe(0.38F, KeyframeAnimations.posVec(-0.25F, -2.805F, -0.947F), Interpolations.LINEAR),
               new Keyframe(0.42F, KeyframeAnimations.posVec(-0.246F, -2.735F, -1.009F), Interpolations.LINEAR),
               new Keyframe(0.5F, KeyframeAnimations.posVec(-0.007F, -0.041F, -0.163F), Interpolations.LINEAR),
               new Keyframe(0.54F, KeyframeAnimations.posVec(0.03F, 0.266F, 0.077F), Interpolations.LINEAR),
               new Keyframe(0.58F, KeyframeAnimations.posVec(0.017F, 0.146F, 0.1F), Interpolations.LINEAR),
               new Keyframe(0.62F, KeyframeAnimations.posVec(0.001F, 0.01F, 0.046F), Interpolations.LINEAR),
               new Keyframe(0.71F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.04F, KeyframeAnimations.posVec(-0.03F, -0.276F, -0.084F), Interpolations.LINEAR),
               new Keyframe(1.08F, KeyframeAnimations.posVec(-0.044F, -0.931F, -0.511F), Interpolations.LINEAR),
               new Keyframe(1.12F, KeyframeAnimations.posVec(-0.038F, -1.885F, -1.249F), Interpolations.LINEAR),
               new Keyframe(1.17F, KeyframeAnimations.posVec(-0.128F, -3.103F, -1.715F), Interpolations.LINEAR),
               new Keyframe(1.29F, KeyframeAnimations.posVec(-0.396F, -5.152F, -1.787F), Interpolations.LINEAR),
               new Keyframe(1.33F, KeyframeAnimations.posVec(-0.387F, -5.089F, -1.791F), Interpolations.LINEAR),
               new Keyframe(1.38F, KeyframeAnimations.posVec(-0.298F, -4.482F, -1.832F), Interpolations.LINEAR),
               new Keyframe(1.46F, KeyframeAnimations.posVec(-0.025F, -2.564F, -1.804F), Interpolations.LINEAR),
               new Keyframe(1.5F, KeyframeAnimations.posVec(0.092F, -1.497F, -1.528F), Interpolations.LINEAR),
               new Keyframe(1.58F, KeyframeAnimations.posVec(-0.024F, -0.214F, -0.065F), Interpolations.LINEAR),
               new Keyframe(1.62F, KeyframeAnimations.posVec(-0.151F, -0.226F, 0.243F), Interpolations.LINEAR),
               new Keyframe(1.67F, KeyframeAnimations.posVec(-0.095F, -0.141F, 0.134F), Interpolations.LINEAR),
               new Keyframe(1.71F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.75F, KeyframeAnimations.posVec(-0.003F, -0.006F, -0.014F), Interpolations.LINEAR),
               new Keyframe(1.83F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "CTR_leg_right.002",
         new AnimationChannel(Targets.POSITION, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.posVec(-2.707F, 0.0F, -2.155F), Interpolations.LINEAR)})
      )
      .build();
}
