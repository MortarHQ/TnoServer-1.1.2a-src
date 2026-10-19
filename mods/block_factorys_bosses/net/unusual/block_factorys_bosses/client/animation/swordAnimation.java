package net.unusual.block_factorys_bosses.client.animation;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.animation.AnimationChannel.Interpolations;
import net.minecraft.client.animation.AnimationChannel.Targets;
import net.minecraft.client.animation.AnimationDefinition.Builder;

public class swordAnimation {
   public static final AnimationDefinition IDLE = Builder.withLength(3.0F)
      .looping()
      .addAnimation(
         "main",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(-3.955F, 2.7772F, 0.6041F), Interpolations.LINEAR),
               new Keyframe(0.0833F, KeyframeAnimations.degreeVec(-3.845F, 3.3811F, 0.5259F), Interpolations.LINEAR),
               new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-3.735F, 3.9916F, 0.452F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(-3.625F, 4.5977F, 0.3825F), Interpolations.LINEAR),
               new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-3.515F, 5.1796F, 0.3217F), Interpolations.LINEAR),
               new Keyframe(0.4167F, KeyframeAnimations.degreeVec(-3.405F, 5.7218F, 0.2739F), Interpolations.LINEAR),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(-3.305F, 6.2354F, 0.2348F), Interpolations.LINEAR),
               new Keyframe(0.5833F, KeyframeAnimations.degreeVec(-3.215F, 6.6586F, 0.2087F), Interpolations.LINEAR),
               new Keyframe(0.6667F, KeyframeAnimations.degreeVec(-3.135F, 6.9738F, 0.2F), Interpolations.LINEAR),
               new Keyframe(0.75F, KeyframeAnimations.degreeVec(-3.075F, 7.214F, 0.2044F), Interpolations.LINEAR),
               new Keyframe(0.8333F, KeyframeAnimations.degreeVec(-3.03F, 7.3419F, 0.2217F), Interpolations.LINEAR),
               new Keyframe(0.9167F, KeyframeAnimations.degreeVec(-3.005F, 7.3419F, 0.2543F), Interpolations.LINEAR),
               new Keyframe(1.0F, KeyframeAnimations.degreeVec(-3.0F, 7.214F, 0.2978F), Interpolations.LINEAR),
               new Keyframe(1.0833F, KeyframeAnimations.degreeVec(-3.01F, 6.9738F, 0.3521F), Interpolations.LINEAR),
               new Keyframe(1.1667F, KeyframeAnimations.degreeVec(-3.05F, 6.6365F, 0.4173F), Interpolations.LINEAR),
               new Keyframe(1.25F, KeyframeAnimations.degreeVec(-3.1F, 6.2134F, 0.4889F), Interpolations.LINEAR),
               new Keyframe(1.3333F, KeyframeAnimations.degreeVec(-3.17F, 5.7218F, 0.565F), Interpolations.LINEAR),
               new Keyframe(1.4167F, KeyframeAnimations.degreeVec(-3.26F, 5.1796F, 0.6454F), Interpolations.LINEAR),
               new Keyframe(1.5F, KeyframeAnimations.degreeVec(-3.35F, 4.5977F, 0.7279F), Interpolations.LINEAR),
               new Keyframe(1.5833F, KeyframeAnimations.degreeVec(-3.46F, 3.9916F, 0.8083F), Interpolations.LINEAR),
               new Keyframe(1.6667F, KeyframeAnimations.degreeVec(-3.57F, 3.3811F, 0.8843F), Interpolations.LINEAR),
               new Keyframe(1.75F, KeyframeAnimations.degreeVec(-3.68F, 2.7772F, 0.956F), Interpolations.LINEAR),
               new Keyframe(1.8333F, KeyframeAnimations.degreeVec(-3.79F, 2.1953F, 1.0212F), Interpolations.LINEAR),
               new Keyframe(1.9167F, KeyframeAnimations.degreeVec(-3.9F, 1.6531F, 1.0777F), Interpolations.LINEAR),
               new Keyframe(2.0F, KeyframeAnimations.degreeVec(-4.01F, 1.1616F, 1.1211F), Interpolations.LINEAR),
               new Keyframe(2.0833F, KeyframeAnimations.degreeVec(-4.1F, 0.7384F, 1.1516F), Interpolations.LINEAR),
               new Keyframe(2.1667F, KeyframeAnimations.degreeVec(-4.19F, 0.3989F, 1.1689F), Interpolations.LINEAR),
               new Keyframe(2.25F, KeyframeAnimations.degreeVec(-4.26F, 0.1587F, 1.1689F), Interpolations.LINEAR),
               new Keyframe(2.3333F, KeyframeAnimations.degreeVec(-4.31F, 0.0331F, 1.1516F), Interpolations.LINEAR),
               new Keyframe(2.4167F, KeyframeAnimations.degreeVec(-4.35F, 0.0F, 1.1211F), Interpolations.LINEAR),
               new Keyframe(2.5F, KeyframeAnimations.degreeVec(-4.36F, 0.0661F, 1.0777F), Interpolations.LINEAR),
               new Keyframe(2.5833F, KeyframeAnimations.degreeVec(-4.35F, 0.2513F, 1.0212F), Interpolations.LINEAR),
               new Keyframe(2.6667F, KeyframeAnimations.degreeVec(-4.31F, 0.5466F, 0.956F), Interpolations.LINEAR),
               new Keyframe(2.75F, KeyframeAnimations.degreeVec(-4.26F, 0.9301F, 0.8843F), Interpolations.LINEAR),
               new Keyframe(2.8333F, KeyframeAnimations.degreeVec(-4.19F, 1.393F, 0.8083F), Interpolations.LINEAR),
               new Keyframe(2.9167F, KeyframeAnimations.degreeVec(-4.1F, 1.9132F, 0.7279F), Interpolations.LINEAR),
               new Keyframe(3.0F, KeyframeAnimations.degreeVec(-3.955F, 2.7772F, 0.6041F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "main",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.045F, 1.7152F, 1.575F), Interpolations.LINEAR),
               new Keyframe(0.0833F, KeyframeAnimations.posVec(0.075F, 1.7085F, 1.59F), Interpolations.LINEAR),
               new Keyframe(0.1667F, KeyframeAnimations.posVec(0.115F, 1.7018F, 1.6F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.posVec(0.165F, 1.6884F, 1.6F), Interpolations.LINEAR),
               new Keyframe(0.3333F, KeyframeAnimations.posVec(0.22F, 1.6683F, 1.595F), Interpolations.LINEAR),
               new Keyframe(0.4167F, KeyframeAnimations.posVec(0.28F, 1.6415F, 1.5825F), Interpolations.LINEAR),
               new Keyframe(0.5F, KeyframeAnimations.posVec(0.34F, 1.6147F, 1.565F), Interpolations.LINEAR),
               new Keyframe(0.5833F, KeyframeAnimations.posVec(0.405F, 1.5879F, 1.54F), Interpolations.LINEAR),
               new Keyframe(0.6667F, KeyframeAnimations.posVec(0.47F, 1.5611F, 1.51F), Interpolations.LINEAR),
               new Keyframe(0.75F, KeyframeAnimations.posVec(0.525F, 1.5276F, 1.4775F), Interpolations.LINEAR),
               new Keyframe(0.8333F, KeyframeAnimations.posVec(0.58F, 1.4941F, 1.4425F), Interpolations.LINEAR),
               new Keyframe(0.9167F, KeyframeAnimations.posVec(0.63F, 1.4673F, 1.4075F), Interpolations.LINEAR),
               new Keyframe(1.0F, KeyframeAnimations.posVec(0.67F, 1.4338F, 1.37F), Interpolations.LINEAR),
               new Keyframe(1.0833F, KeyframeAnimations.posVec(0.705F, 1.407F, 1.33F), Interpolations.LINEAR),
               new Keyframe(1.1667F, KeyframeAnimations.posVec(0.73F, 1.3869F, 1.2925F), Interpolations.LINEAR),
               new Keyframe(1.25F, KeyframeAnimations.posVec(0.745F, 1.3668F, 1.2575F), Interpolations.LINEAR),
               new Keyframe(1.3333F, KeyframeAnimations.posVec(0.745F, 1.3534F, 1.225F), Interpolations.LINEAR),
               new Keyframe(1.4167F, KeyframeAnimations.posVec(0.73F, 1.34F, 1.1975F), Interpolations.LINEAR),
               new Keyframe(1.5F, KeyframeAnimations.posVec(0.705F, 1.34F, 1.1725F), Interpolations.LINEAR),
               new Keyframe(1.5833F, KeyframeAnimations.posVec(0.67F, 1.34F, 1.1525F), Interpolations.LINEAR),
               new Keyframe(1.6667F, KeyframeAnimations.posVec(0.63F, 1.3534F, 1.1425F), Interpolations.LINEAR),
               new Keyframe(1.75F, KeyframeAnimations.posVec(0.58F, 1.3668F, 1.14F), Interpolations.LINEAR),
               new Keyframe(1.8333F, KeyframeAnimations.posVec(0.525F, 1.3869F, 1.1425F), Interpolations.LINEAR),
               new Keyframe(1.9167F, KeyframeAnimations.posVec(0.47F, 1.407F, 1.1525F), Interpolations.LINEAR),
               new Keyframe(2.0F, KeyframeAnimations.posVec(0.405F, 1.4338F, 1.1725F), Interpolations.LINEAR),
               new Keyframe(2.0833F, KeyframeAnimations.posVec(0.34F, 1.4673F, 1.1975F), Interpolations.LINEAR),
               new Keyframe(2.1667F, KeyframeAnimations.posVec(0.28F, 1.4941F, 1.225F), Interpolations.LINEAR),
               new Keyframe(2.25F, KeyframeAnimations.posVec(0.22F, 1.5276F, 1.2575F), Interpolations.LINEAR),
               new Keyframe(2.3333F, KeyframeAnimations.posVec(0.165F, 1.5611F, 1.2925F), Interpolations.LINEAR),
               new Keyframe(2.4167F, KeyframeAnimations.posVec(0.115F, 1.5879F, 1.33F), Interpolations.LINEAR),
               new Keyframe(2.5F, KeyframeAnimations.posVec(0.075F, 1.6147F, 1.37F), Interpolations.LINEAR),
               new Keyframe(2.5833F, KeyframeAnimations.posVec(0.045F, 1.6415F, 1.4075F), Interpolations.LINEAR),
               new Keyframe(2.6667F, KeyframeAnimations.posVec(0.02F, 1.6683F, 1.4425F), Interpolations.LINEAR),
               new Keyframe(2.75F, KeyframeAnimations.posVec(0.005F, 1.6884F, 1.4775F), Interpolations.LINEAR),
               new Keyframe(2.8333F, KeyframeAnimations.posVec(0.0F, 1.7018F, 1.51F), Interpolations.LINEAR),
               new Keyframe(2.9167F, KeyframeAnimations.posVec(0.01F, 1.7085F, 1.54F), Interpolations.LINEAR),
               new Keyframe(3.0F, KeyframeAnimations.posVec(0.03F, 1.7152F, 1.57F), Interpolations.LINEAR)
            }
         )
      )
      .build();
   public static final AnimationDefinition ATTACK_1 = Builder.withLength(0.625F)
      .addAnimation(
         "main",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.0417F, KeyframeAnimations.degreeVec(-7.86F, -6.62F, 27.44F), Interpolations.LINEAR),
               new Keyframe(0.0833F, KeyframeAnimations.degreeVec(-2.6F, -20.04F, 65.79F), Interpolations.LINEAR),
               new Keyframe(0.125F, KeyframeAnimations.degreeVec(19.6F, -29.59F, 92.52F), Interpolations.LINEAR),
               new Keyframe(0.1667F, KeyframeAnimations.degreeVec(30.91F, -30.45F, 94.31F), Interpolations.LINEAR),
               new Keyframe(0.2083F, KeyframeAnimations.degreeVec(22.39F, 16.01F, -48.69F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(6.34F, 19.11F, -63.95F), Interpolations.LINEAR),
               new Keyframe(0.2917F, KeyframeAnimations.degreeVec(-3.68F, 18.56F, -70.78F), Interpolations.LINEAR),
               new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-10.41F, 16.2F, -71.04F), Interpolations.LINEAR),
               new Keyframe(0.375F, KeyframeAnimations.degreeVec(-11.59F, 13.9F, -66.6F), Interpolations.LINEAR),
               new Keyframe(0.4167F, KeyframeAnimations.degreeVec(-10.96F, 12.44F, -59.55F), Interpolations.LINEAR),
               new Keyframe(0.4583F, KeyframeAnimations.degreeVec(-9.7F, 10.45F, -48.02F), Interpolations.LINEAR),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(-8.09F, 8.21F, -34.12F), Interpolations.LINEAR),
               new Keyframe(0.5417F, KeyframeAnimations.degreeVec(-6.4F, 6.0F, -19.97F), Interpolations.LINEAR),
               new Keyframe(0.5833F, KeyframeAnimations.degreeVec(-4.93F, 4.09F, -7.69F), Interpolations.LINEAR),
               new Keyframe(0.625F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "main",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.0417F, KeyframeAnimations.posVec(-2.0798F, 1.6483F, 0.5221F), Interpolations.LINEAR),
               new Keyframe(0.0833F, KeyframeAnimations.posVec(-4.2996F, 1.8667F, -1.5357F), Interpolations.LINEAR),
               new Keyframe(0.125F, KeyframeAnimations.posVec(-1.1494F, 1.765F, -0.2236F), Interpolations.LINEAR),
               new Keyframe(0.1667F, KeyframeAnimations.posVec(-18.4092F, 0.8233F, 4.5186F), Interpolations.LINEAR),
               new Keyframe(0.2083F, KeyframeAnimations.posVec(-22.269F, -0.4583F, 9.6607F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.posVec(-20.6187F, -1.88F, 10.3529F), Interpolations.LINEAR),
               new Keyframe(0.28F, KeyframeAnimations.posVec(-17.5226F, -2.9684F, 11.1248F), Interpolations.LINEAR),
               new Keyframe(0.2917F, KeyframeAnimations.posVec(-16.3185F, -2.8731F, 11.8694F), Interpolations.LINEAR),
               new Keyframe(0.32F, KeyframeAnimations.posVec(-11.4972F, -2.669F, 14.5007F), Interpolations.LINEAR),
               new Keyframe(0.3333F, KeyframeAnimations.posVec(-9.3117F, -2.573F, 13.7389F), Interpolations.LINEAR),
               new Keyframe(0.375F, KeyframeAnimations.posVec(-2.5719F, -2.2828F, 12.3783F), Interpolations.LINEAR),
               new Keyframe(0.4167F, KeyframeAnimations.posVec(1.2179F, -1.9426F, 11.8778F), Interpolations.LINEAR),
               new Keyframe(0.4583F, KeyframeAnimations.posVec(1.1877F, -1.5424F, 11.0572F), Interpolations.LINEAR),
               new Keyframe(0.5F, KeyframeAnimations.posVec(0.4875F, -1.0022F, 9.7467F), Interpolations.LINEAR),
               new Keyframe(0.625F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .build();
   public static final AnimationDefinition ATTACK_LEFT_1 = Builder.withLength(0.625F)
      .addAnimation(
         "main",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.0417F, KeyframeAnimations.degreeVec(7.86F, 6.62F, 27.44F), Interpolations.LINEAR),
               new Keyframe(0.0833F, KeyframeAnimations.degreeVec(2.6F, 20.04F, 65.79F), Interpolations.LINEAR),
               new Keyframe(0.125F, KeyframeAnimations.degreeVec(-19.6F, 29.59F, 92.52F), Interpolations.LINEAR),
               new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-30.91F, 30.45F, 94.31F), Interpolations.LINEAR),
               new Keyframe(0.2083F, KeyframeAnimations.degreeVec(-22.39F, -16.01F, -48.69F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(-6.34F, -19.11F, -63.95F), Interpolations.LINEAR),
               new Keyframe(0.2917F, KeyframeAnimations.degreeVec(3.68F, -18.56F, -70.78F), Interpolations.LINEAR),
               new Keyframe(0.3333F, KeyframeAnimations.degreeVec(10.41F, -16.2F, -71.04F), Interpolations.LINEAR),
               new Keyframe(0.375F, KeyframeAnimations.degreeVec(11.59F, -13.9F, -66.6F), Interpolations.LINEAR),
               new Keyframe(0.4167F, KeyframeAnimations.degreeVec(10.96F, -12.44F, -59.55F), Interpolations.LINEAR),
               new Keyframe(0.4583F, KeyframeAnimations.degreeVec(9.7F, -10.45F, -48.02F), Interpolations.LINEAR),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(8.09F, -8.21F, -34.12F), Interpolations.LINEAR),
               new Keyframe(0.5417F, KeyframeAnimations.degreeVec(6.4F, -6.0F, -19.97F), Interpolations.LINEAR),
               new Keyframe(0.5833F, KeyframeAnimations.degreeVec(4.93F, -4.09F, -7.69F), Interpolations.LINEAR),
               new Keyframe(0.625F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "main",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.0417F, KeyframeAnimations.posVec(-2.0798F, 1.6483F, -0.5221F), Interpolations.LINEAR),
               new Keyframe(0.0833F, KeyframeAnimations.posVec(-4.2996F, 1.8667F, 1.5357F), Interpolations.LINEAR),
               new Keyframe(0.125F, KeyframeAnimations.posVec(-1.1494F, 1.765F, 0.2236F), Interpolations.LINEAR),
               new Keyframe(0.1667F, KeyframeAnimations.posVec(-18.4092F, 0.8233F, -4.5186F), Interpolations.LINEAR),
               new Keyframe(0.2083F, KeyframeAnimations.posVec(-22.269F, -0.4583F, -9.6607F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.posVec(-20.6187F, -1.88F, -10.3529F), Interpolations.LINEAR),
               new Keyframe(0.2917F, KeyframeAnimations.posVec(-17.5226F, -2.9684F, -11.1248F), Interpolations.LINEAR),
               new Keyframe(0.3333F, KeyframeAnimations.posVec(-11.4972F, -2.669F, -14.5007F), Interpolations.LINEAR),
               new Keyframe(0.375F, KeyframeAnimations.posVec(-2.5719F, -2.2828F, -12.3783F), Interpolations.LINEAR),
               new Keyframe(0.4167F, KeyframeAnimations.posVec(1.2179F, -1.9426F, -11.8778F), Interpolations.LINEAR),
               new Keyframe(0.4583F, KeyframeAnimations.posVec(1.1877F, -1.5424F, -11.0572F), Interpolations.LINEAR),
               new Keyframe(0.5F, KeyframeAnimations.posVec(0.4875F, -1.0022F, -9.7467F), Interpolations.LINEAR),
               new Keyframe(0.625F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .build();
   public static final AnimationDefinition ATTACK_2 = Builder.withLength(1.0F)
      .addAnimation(
         "main",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(-3.95F, 2.78F, 0.6F), Interpolations.LINEAR),
               new Keyframe(0.0417F, KeyframeAnimations.degreeVec(11.16F, -17.23F, 21.17F), Interpolations.LINEAR),
               new Keyframe(0.0833F, KeyframeAnimations.degreeVec(32.08F, -42.43F, 49.52F), Interpolations.LINEAR),
               new Keyframe(0.125F, KeyframeAnimations.degreeVec(68.6206F, -43.94F, 70.58F), Interpolations.LINEAR),
               new Keyframe(0.1667F, KeyframeAnimations.degreeVec(124.2625F, 13.37F, 77.28F), Interpolations.LINEAR),
               new Keyframe(0.2083F, KeyframeAnimations.degreeVec(145.1197F, 77.7907F, 75.8164F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(145.9312F, 122.5F, 75.0F), Interpolations.LINEAR),
               new Keyframe(0.2917F, KeyframeAnimations.degreeVec(153.3353F, 161.75F, 82.51F), Interpolations.CATMULLROM),
               new Keyframe(0.9167F, KeyframeAnimations.degreeVec(-3.95F, 2.78F, 0.6F), Interpolations.LINEAR),
               new Keyframe(1.0F, KeyframeAnimations.degreeVec(-3.95F, 2.78F, 0.6F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "main",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.0417F, KeyframeAnimations.posVec(-3.91F, 5.32F, -6.95F), Interpolations.LINEAR),
               new Keyframe(0.0833F, KeyframeAnimations.posVec(-6.11F, 8.35F, -15.13F), Interpolations.LINEAR),
               new Keyframe(0.125F, KeyframeAnimations.posVec(5.1913F, 3.73F, -12.34F), Interpolations.LINEAR),
               new Keyframe(0.1667F, KeyframeAnimations.posVec(-4.421F, 3.23F, -1.54F), Interpolations.LINEAR),
               new Keyframe(0.2083F, KeyframeAnimations.posVec(-9.2564F, 3.86F, 2.92F), Interpolations.LINEAR),
               new Keyframe(0.2917F, KeyframeAnimations.posVec(0.4773F, 3.5F, 17.41F), Interpolations.CATMULLROM),
               new Keyframe(0.9167F, KeyframeAnimations.posVec(0.05F, 1.72F, 1.58F), Interpolations.LINEAR),
               new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .build();
   public static final AnimationDefinition ATTACK_LEFT_2 = Builder.withLength(1.0F)
      .addAnimation(
         "main",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(3.95F, -2.78F, 0.6F), Interpolations.LINEAR),
               new Keyframe(0.0417F, KeyframeAnimations.degreeVec(-11.16F, 17.23F, 21.17F), Interpolations.LINEAR),
               new Keyframe(0.0833F, KeyframeAnimations.degreeVec(-32.08F, 42.43F, 49.52F), Interpolations.LINEAR),
               new Keyframe(0.125F, KeyframeAnimations.degreeVec(-68.6206F, 43.94F, 70.58F), Interpolations.LINEAR),
               new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-124.2625F, -13.37F, 77.28F), Interpolations.LINEAR),
               new Keyframe(0.2083F, KeyframeAnimations.degreeVec(-145.1197F, -77.7907F, 75.8164F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(-145.9312F, -122.5F, 75.0F), Interpolations.LINEAR),
               new Keyframe(0.2917F, KeyframeAnimations.degreeVec(-153.3353F, -161.75F, 82.51F), Interpolations.CATMULLROM),
               new Keyframe(0.9167F, KeyframeAnimations.degreeVec(3.95F, -2.78F, 0.6F), Interpolations.LINEAR),
               new Keyframe(1.0F, KeyframeAnimations.degreeVec(3.95F, -2.78F, 0.6F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "main",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.0417F, KeyframeAnimations.posVec(-3.91F, 5.32F, 6.95F), Interpolations.LINEAR),
               new Keyframe(0.0833F, KeyframeAnimations.posVec(-6.11F, 8.35F, 15.13F), Interpolations.LINEAR),
               new Keyframe(0.125F, KeyframeAnimations.posVec(5.1913F, 3.73F, 12.34F), Interpolations.LINEAR),
               new Keyframe(0.1667F, KeyframeAnimations.posVec(-4.421F, 3.23F, 1.54F), Interpolations.LINEAR),
               new Keyframe(0.2083F, KeyframeAnimations.posVec(-9.2564F, 3.86F, -2.92F), Interpolations.LINEAR),
               new Keyframe(0.2917F, KeyframeAnimations.posVec(0.4773F, 3.5F, -17.41F), Interpolations.CATMULLROM),
               new Keyframe(0.9167F, KeyframeAnimations.posVec(0.05F, 1.72F, -1.58F), Interpolations.LINEAR),
               new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .build();
   public static final AnimationDefinition ATTACK_3 = Builder.withLength(0.75F)
      .addAnimation(
         "main_dagger_left",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(-2.1103F, -1.1925F, 0.5736F), Interpolations.CATMULLROM),
               new Keyframe(0.2083F, KeyframeAnimations.degreeVec(-9.6103F, -1.1925F, 0.5736F), Interpolations.CATMULLROM),
               new Keyframe(0.375F, KeyframeAnimations.degreeVec(0.0326F, -1.1925F, 0.5736F), Interpolations.CATMULLROM),
               new Keyframe(0.75F, KeyframeAnimations.degreeVec(-2.1103F, -1.1925F, 0.5736F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "main_dagger_left",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.351F, -0.2209F, -0.103F), Interpolations.CATMULLROM),
               new Keyframe(0.1667F, KeyframeAnimations.posVec(2.9528F, -4.9937F, 1.977F), Interpolations.CATMULLROM),
               new Keyframe(0.3333F, KeyframeAnimations.posVec(-2.2916F, 4.6552F, -2.183F), Interpolations.CATMULLROM),
               new Keyframe(0.75F, KeyframeAnimations.posVec(0.351F, -0.2209F, -0.103F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "main",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.125F, KeyframeAnimations.degreeVec(-12.0917F, 6.7943F, -47.0301F), Interpolations.CATMULLROM),
               new Keyframe(0.2083F, KeyframeAnimations.degreeVec(-13.5761F, 17.5014F, -99.7943F), Interpolations.CATMULLROM),
               new Keyframe(0.28F, KeyframeAnimations.degreeVec(-14.2766F, 17.4309F, -132.5281F), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(-14.8672F, 12.0014F, -66.8569F), Interpolations.CATMULLROM),
               new Keyframe(0.5833F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "main",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.1667F, KeyframeAnimations.posVec(10.4287F, 11.4505F, -0.5694F), Interpolations.CATMULLROM),
               new Keyframe(0.25F, KeyframeAnimations.posVec(-14.2246F, -8.2561F, 16.0415F), Interpolations.CATMULLROM),
               new Keyframe(0.4583F, KeyframeAnimations.posVec(1.1798F, 5.2309F, 6.3386F), Interpolations.CATMULLROM),
               new Keyframe(0.5833F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .build();
   public static final AnimationDefinition ATTACK_LEFT_3 = Builder.withLength(0.75F)
      .addAnimation(
         "main_dagger_left",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(-2.1103F, -1.1925F, 0.5736F), Interpolations.CATMULLROM),
               new Keyframe(0.2083F, KeyframeAnimations.degreeVec(-9.6103F, -1.1925F, 0.5736F), Interpolations.CATMULLROM),
               new Keyframe(0.375F, KeyframeAnimations.degreeVec(0.0326F, -1.1925F, 0.5736F), Interpolations.CATMULLROM),
               new Keyframe(0.75F, KeyframeAnimations.degreeVec(-2.1103F, -1.1925F, 0.5736F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "main_dagger_left",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.351F, -0.2209F, -0.103F), Interpolations.CATMULLROM),
               new Keyframe(0.1667F, KeyframeAnimations.posVec(2.9528F, -4.9937F, 1.977F), Interpolations.CATMULLROM),
               new Keyframe(0.3333F, KeyframeAnimations.posVec(-2.2916F, 4.6552F, -2.183F), Interpolations.CATMULLROM),
               new Keyframe(0.75F, KeyframeAnimations.posVec(0.351F, -0.2209F, -0.103F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "main",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.125F, KeyframeAnimations.degreeVec(12.0917F, -6.7943F, -47.0301F), Interpolations.CATMULLROM),
               new Keyframe(0.2083F, KeyframeAnimations.degreeVec(13.5761F, -17.5014F, -99.7943F), Interpolations.CATMULLROM),
               new Keyframe(0.2917F, KeyframeAnimations.degreeVec(14.2766F, -17.4309F, -132.5281F), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(14.8672F, -12.0014F, -66.8569F), Interpolations.CATMULLROM),
               new Keyframe(0.5833F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "main",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.1667F, KeyframeAnimations.posVec(10.4287F, 11.4505F, 0.5694F), Interpolations.CATMULLROM),
               new Keyframe(0.25F, KeyframeAnimations.posVec(-14.2246F, -8.2561F, -16.0415F), Interpolations.CATMULLROM),
               new Keyframe(0.4583F, KeyframeAnimations.posVec(1.1798F, 5.2309F, -6.3386F), Interpolations.CATMULLROM),
               new Keyframe(0.5833F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .build();
}
