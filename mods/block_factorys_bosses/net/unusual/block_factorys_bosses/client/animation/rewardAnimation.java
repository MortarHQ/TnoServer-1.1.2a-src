package net.unusual.block_factorys_bosses.client.animation;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.animation.AnimationChannel.Interpolations;
import net.minecraft.client.animation.AnimationChannel.Targets;
import net.minecraft.client.animation.AnimationDefinition.Builder;

public class rewardAnimation {
   public static final AnimationDefinition IDLE = Builder.withLength(15.0F)
      .looping()
      .addAnimation(
         "main",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(-3.955F, -2.7772F, -0.6041F), Interpolations.LINEAR),
               new Keyframe(0.4F, KeyframeAnimations.degreeVec(-3.845F, -3.3811F, -0.5259F), Interpolations.LINEAR),
               new Keyframe(0.8F, KeyframeAnimations.degreeVec(-3.735F, -3.9916F, -0.452F), Interpolations.LINEAR),
               new Keyframe(1.2F, KeyframeAnimations.degreeVec(-3.625F, -4.5977F, -0.3825F), Interpolations.LINEAR),
               new Keyframe(1.6F, KeyframeAnimations.degreeVec(-3.515F, -5.1796F, -0.3217F), Interpolations.LINEAR),
               new Keyframe(2.0F, KeyframeAnimations.degreeVec(-3.405F, -5.7218F, -0.2739F), Interpolations.LINEAR),
               new Keyframe(2.6F, KeyframeAnimations.degreeVec(-3.305F, -6.2354F, -0.2348F), Interpolations.LINEAR),
               new Keyframe(3.0F, KeyframeAnimations.degreeVec(-3.215F, -6.6586F, -0.2087F), Interpolations.LINEAR),
               new Keyframe(3.4F, KeyframeAnimations.degreeVec(-3.135F, -6.9738F, -0.2F), Interpolations.LINEAR),
               new Keyframe(3.8F, KeyframeAnimations.degreeVec(-3.075F, -7.214F, -0.2044F), Interpolations.LINEAR),
               new Keyframe(4.2F, KeyframeAnimations.degreeVec(-3.03F, -7.3419F, -0.2217F), Interpolations.LINEAR),
               new Keyframe(4.6F, KeyframeAnimations.degreeVec(-3.005F, -7.3419F, -0.2543F), Interpolations.LINEAR),
               new Keyframe(5.0F, KeyframeAnimations.degreeVec(-3.0F, -7.214F, -0.2978F), Interpolations.LINEAR),
               new Keyframe(5.4F, KeyframeAnimations.degreeVec(-3.01F, -6.9738F, -0.3521F), Interpolations.LINEAR),
               new Keyframe(5.8F, KeyframeAnimations.degreeVec(-3.05F, -6.6365F, -0.4173F), Interpolations.LINEAR),
               new Keyframe(6.2F, KeyframeAnimations.degreeVec(-3.1F, -6.2134F, -0.4889F), Interpolations.LINEAR),
               new Keyframe(6.6F, KeyframeAnimations.degreeVec(-3.17F, -5.7218F, -0.565F), Interpolations.LINEAR),
               new Keyframe(7.0F, KeyframeAnimations.degreeVec(-3.26F, -5.1796F, -0.6454F), Interpolations.LINEAR),
               new Keyframe(7.6F, KeyframeAnimations.degreeVec(-3.35F, -4.5977F, -0.7279F), Interpolations.LINEAR),
               new Keyframe(8.0F, KeyframeAnimations.degreeVec(-3.46F, -3.9916F, -0.8083F), Interpolations.LINEAR),
               new Keyframe(8.4F, KeyframeAnimations.degreeVec(-3.57F, -3.3811F, -0.8843F), Interpolations.LINEAR),
               new Keyframe(8.8F, KeyframeAnimations.degreeVec(-3.68F, -2.7772F, -0.956F), Interpolations.LINEAR),
               new Keyframe(9.2F, KeyframeAnimations.degreeVec(-3.79F, -2.1953F, -1.0212F), Interpolations.LINEAR),
               new Keyframe(9.6F, KeyframeAnimations.degreeVec(-3.9F, -1.6531F, -1.0777F), Interpolations.LINEAR),
               new Keyframe(10.0F, KeyframeAnimations.degreeVec(-4.01F, -1.1616F, -1.1211F), Interpolations.LINEAR),
               new Keyframe(10.4F, KeyframeAnimations.degreeVec(-4.1F, -0.7384F, -1.1516F), Interpolations.LINEAR),
               new Keyframe(10.8F, KeyframeAnimations.degreeVec(-4.19F, -0.3989F, -1.1689F), Interpolations.LINEAR),
               new Keyframe(11.2F, KeyframeAnimations.degreeVec(-4.26F, -0.1587F, -1.1689F), Interpolations.LINEAR),
               new Keyframe(11.6F, KeyframeAnimations.degreeVec(-4.31F, -0.0331F, -1.1516F), Interpolations.LINEAR),
               new Keyframe(12.0F, KeyframeAnimations.degreeVec(-4.35F, 0.0F, -1.1211F), Interpolations.LINEAR),
               new Keyframe(12.6F, KeyframeAnimations.degreeVec(-4.36F, -0.0661F, -1.0777F), Interpolations.LINEAR),
               new Keyframe(13.0F, KeyframeAnimations.degreeVec(-4.35F, -0.2513F, -1.0212F), Interpolations.LINEAR),
               new Keyframe(13.4F, KeyframeAnimations.degreeVec(-4.31F, -0.5466F, -0.956F), Interpolations.LINEAR),
               new Keyframe(13.8F, KeyframeAnimations.degreeVec(-4.26F, -0.9301F, -0.8843F), Interpolations.LINEAR),
               new Keyframe(14.2F, KeyframeAnimations.degreeVec(-4.19F, -1.393F, -0.8083F), Interpolations.LINEAR),
               new Keyframe(14.6F, KeyframeAnimations.degreeVec(-4.1F, -1.9132F, -0.7279F), Interpolations.LINEAR),
               new Keyframe(15.0F, KeyframeAnimations.degreeVec(-3.955F, -2.7772F, -0.6041F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "main",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(-0.045F, 1.7152F, 1.575F), Interpolations.LINEAR),
               new Keyframe(0.4F, KeyframeAnimations.posVec(-0.075F, 1.7085F, 1.59F), Interpolations.LINEAR),
               new Keyframe(0.8F, KeyframeAnimations.posVec(-0.115F, 1.7018F, 1.6F), Interpolations.LINEAR),
               new Keyframe(1.2F, KeyframeAnimations.posVec(-0.165F, 1.6884F, 1.6F), Interpolations.LINEAR),
               new Keyframe(1.6F, KeyframeAnimations.posVec(-0.22F, 1.6683F, 1.595F), Interpolations.LINEAR),
               new Keyframe(2.0F, KeyframeAnimations.posVec(-0.28F, 1.6415F, 1.5825F), Interpolations.LINEAR),
               new Keyframe(2.6F, KeyframeAnimations.posVec(-0.34F, 1.6147F, 1.565F), Interpolations.LINEAR),
               new Keyframe(3.0F, KeyframeAnimations.posVec(-0.405F, 1.5879F, 1.54F), Interpolations.LINEAR),
               new Keyframe(3.4F, KeyframeAnimations.posVec(-0.47F, 1.5611F, 1.51F), Interpolations.LINEAR),
               new Keyframe(3.8F, KeyframeAnimations.posVec(-0.525F, 1.5276F, 1.4775F), Interpolations.LINEAR),
               new Keyframe(4.2F, KeyframeAnimations.posVec(-0.58F, 1.4941F, 1.4425F), Interpolations.LINEAR),
               new Keyframe(4.6F, KeyframeAnimations.posVec(-0.63F, 1.4673F, 1.4075F), Interpolations.LINEAR),
               new Keyframe(5.0F, KeyframeAnimations.posVec(-0.67F, 1.4338F, 1.37F), Interpolations.LINEAR),
               new Keyframe(5.4F, KeyframeAnimations.posVec(-0.705F, 1.407F, 1.33F), Interpolations.LINEAR),
               new Keyframe(5.8F, KeyframeAnimations.posVec(-0.73F, 1.3869F, 1.2925F), Interpolations.LINEAR),
               new Keyframe(6.2F, KeyframeAnimations.posVec(-0.745F, 1.3668F, 1.2575F), Interpolations.LINEAR),
               new Keyframe(6.6F, KeyframeAnimations.posVec(-0.745F, 1.3534F, 1.225F), Interpolations.LINEAR),
               new Keyframe(7.0F, KeyframeAnimations.posVec(-0.73F, 1.34F, 1.1975F), Interpolations.LINEAR),
               new Keyframe(7.6F, KeyframeAnimations.posVec(-0.705F, 1.34F, 1.1725F), Interpolations.LINEAR),
               new Keyframe(8.0F, KeyframeAnimations.posVec(-0.67F, 1.34F, 1.1525F), Interpolations.LINEAR),
               new Keyframe(8.4F, KeyframeAnimations.posVec(-0.63F, 1.3534F, 1.1425F), Interpolations.LINEAR),
               new Keyframe(8.8F, KeyframeAnimations.posVec(-0.58F, 1.3668F, 1.14F), Interpolations.LINEAR),
               new Keyframe(9.2F, KeyframeAnimations.posVec(-0.525F, 1.3869F, 1.1425F), Interpolations.LINEAR),
               new Keyframe(9.6F, KeyframeAnimations.posVec(-0.47F, 1.407F, 1.1525F), Interpolations.LINEAR),
               new Keyframe(10.0F, KeyframeAnimations.posVec(-0.405F, 1.4338F, 1.1725F), Interpolations.LINEAR),
               new Keyframe(10.4F, KeyframeAnimations.posVec(-0.34F, 1.4673F, 1.1975F), Interpolations.LINEAR),
               new Keyframe(10.8F, KeyframeAnimations.posVec(-0.28F, 1.4941F, 1.225F), Interpolations.LINEAR),
               new Keyframe(11.2F, KeyframeAnimations.posVec(-0.22F, 1.5276F, 1.2575F), Interpolations.LINEAR),
               new Keyframe(11.6F, KeyframeAnimations.posVec(-0.165F, 1.5611F, 1.2925F), Interpolations.LINEAR),
               new Keyframe(12.0F, KeyframeAnimations.posVec(-0.115F, 1.5879F, 1.33F), Interpolations.LINEAR),
               new Keyframe(12.6F, KeyframeAnimations.posVec(-0.075F, 1.6147F, 1.37F), Interpolations.LINEAR),
               new Keyframe(13.0F, KeyframeAnimations.posVec(-0.045F, 1.6415F, 1.4075F), Interpolations.LINEAR),
               new Keyframe(13.4F, KeyframeAnimations.posVec(-0.02F, 1.6683F, 1.4425F), Interpolations.LINEAR),
               new Keyframe(13.8F, KeyframeAnimations.posVec(-0.005F, 1.6884F, 1.4775F), Interpolations.LINEAR),
               new Keyframe(14.2F, KeyframeAnimations.posVec(0.0F, 1.7018F, 1.51F), Interpolations.LINEAR),
               new Keyframe(14.6F, KeyframeAnimations.posVec(-0.01F, 1.7085F, 1.54F), Interpolations.LINEAR),
               new Keyframe(15.0F, KeyframeAnimations.posVec(-0.03F, 1.7152F, 1.57F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "bone",
         new AnimationChannel(Targets.ROTATION, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 30.0F, 122.5F), Interpolations.LINEAR)})
      )
      .addAnimation(
         "bone",
         new AnimationChannel(Targets.POSITION, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.posVec(-4.0F, 5.0F, 5.0F), Interpolations.LINEAR)})
      )
      .build();
   public static final AnimationDefinition IDLE2 = Builder.withLength(15.0F)
      .looping()
      .addAnimation(
         "main",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(-3.955F, -2.7772F, -0.6041F), Interpolations.LINEAR),
               new Keyframe(0.4F, KeyframeAnimations.degreeVec(-3.845F, -3.3811F, -0.5259F), Interpolations.LINEAR),
               new Keyframe(0.8F, KeyframeAnimations.degreeVec(-3.735F, -3.9916F, -0.452F), Interpolations.LINEAR),
               new Keyframe(1.2F, KeyframeAnimations.degreeVec(-3.625F, -4.5977F, -0.3825F), Interpolations.LINEAR),
               new Keyframe(1.6F, KeyframeAnimations.degreeVec(-3.515F, -5.1796F, -0.3217F), Interpolations.LINEAR),
               new Keyframe(2.0F, KeyframeAnimations.degreeVec(-3.405F, -5.7218F, -0.2739F), Interpolations.LINEAR),
               new Keyframe(2.56F, KeyframeAnimations.degreeVec(-3.305F, -6.2354F, -0.2348F), Interpolations.LINEAR),
               new Keyframe(2.96F, KeyframeAnimations.degreeVec(-3.215F, -6.6586F, -0.2087F), Interpolations.LINEAR),
               new Keyframe(3.4F, KeyframeAnimations.degreeVec(-3.135F, -6.9738F, -0.2F), Interpolations.LINEAR),
               new Keyframe(3.8F, KeyframeAnimations.degreeVec(-3.075F, -7.214F, -0.2044F), Interpolations.LINEAR),
               new Keyframe(4.16F, KeyframeAnimations.degreeVec(-3.03F, -7.3419F, -0.2217F), Interpolations.LINEAR),
               new Keyframe(4.56F, KeyframeAnimations.degreeVec(-3.005F, -7.3419F, -0.2543F), Interpolations.LINEAR),
               new Keyframe(4.96F, KeyframeAnimations.degreeVec(-3.0F, -7.214F, -0.2978F), Interpolations.LINEAR),
               new Keyframe(5.4F, KeyframeAnimations.degreeVec(-3.01F, -6.9738F, -0.3521F), Interpolations.LINEAR),
               new Keyframe(5.8F, KeyframeAnimations.degreeVec(-3.05F, -6.6365F, -0.4173F), Interpolations.LINEAR),
               new Keyframe(6.16F, KeyframeAnimations.degreeVec(-3.1F, -6.2134F, -0.4889F), Interpolations.LINEAR),
               new Keyframe(6.56F, KeyframeAnimations.degreeVec(-3.17F, -5.7218F, -0.565F), Interpolations.LINEAR),
               new Keyframe(6.96F, KeyframeAnimations.degreeVec(-3.26F, -5.1796F, -0.6454F), Interpolations.LINEAR),
               new Keyframe(7.56F, KeyframeAnimations.degreeVec(-3.35F, -4.5977F, -0.7279F), Interpolations.LINEAR),
               new Keyframe(7.96F, KeyframeAnimations.degreeVec(-3.46F, -3.9916F, -0.8083F), Interpolations.LINEAR),
               new Keyframe(8.36F, KeyframeAnimations.degreeVec(-3.57F, -3.3811F, -0.8843F), Interpolations.LINEAR),
               new Keyframe(8.76F, KeyframeAnimations.degreeVec(-3.68F, -2.7772F, -0.956F), Interpolations.LINEAR),
               new Keyframe(9.12F, KeyframeAnimations.degreeVec(-3.79F, -2.1953F, -1.0212F), Interpolations.LINEAR),
               new Keyframe(9.56F, KeyframeAnimations.degreeVec(-3.9F, -1.6531F, -1.0777F), Interpolations.LINEAR),
               new Keyframe(9.96F, KeyframeAnimations.degreeVec(-4.01F, -1.1616F, -1.1211F), Interpolations.LINEAR),
               new Keyframe(10.36F, KeyframeAnimations.degreeVec(-4.1F, -0.7384F, -1.1516F), Interpolations.LINEAR),
               new Keyframe(10.76F, KeyframeAnimations.degreeVec(-4.19F, -0.3989F, -1.1689F), Interpolations.LINEAR),
               new Keyframe(11.12F, KeyframeAnimations.degreeVec(-4.26F, -0.1587F, -1.1689F), Interpolations.LINEAR),
               new Keyframe(11.56F, KeyframeAnimations.degreeVec(-4.31F, -0.0331F, -1.1516F), Interpolations.LINEAR),
               new Keyframe(11.96F, KeyframeAnimations.degreeVec(-4.35F, 0.0F, -1.1211F), Interpolations.LINEAR),
               new Keyframe(12.52F, KeyframeAnimations.degreeVec(-4.36F, -0.0661F, -1.0777F), Interpolations.LINEAR),
               new Keyframe(12.92F, KeyframeAnimations.degreeVec(-4.35F, -0.2513F, -1.0212F), Interpolations.LINEAR),
               new Keyframe(13.36F, KeyframeAnimations.degreeVec(-4.31F, -0.5466F, -0.956F), Interpolations.LINEAR),
               new Keyframe(13.76F, KeyframeAnimations.degreeVec(-4.26F, -0.9301F, -0.8843F), Interpolations.LINEAR),
               new Keyframe(14.12F, KeyframeAnimations.degreeVec(-4.19F, -1.393F, -0.8083F), Interpolations.LINEAR),
               new Keyframe(14.52F, KeyframeAnimations.degreeVec(-4.1F, -1.9132F, -0.7279F), Interpolations.LINEAR),
               new Keyframe(15.0F, KeyframeAnimations.degreeVec(-3.955F, -2.7772F, -0.6041F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "main",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(-0.045F, 1.7152F, 1.575F), Interpolations.LINEAR),
               new Keyframe(0.4F, KeyframeAnimations.posVec(-0.075F, 1.7085F, 1.59F), Interpolations.LINEAR),
               new Keyframe(0.8F, KeyframeAnimations.posVec(-0.115F, 1.7018F, 1.6F), Interpolations.LINEAR),
               new Keyframe(1.2F, KeyframeAnimations.posVec(-0.165F, 1.6884F, 1.6F), Interpolations.LINEAR),
               new Keyframe(1.6F, KeyframeAnimations.posVec(-0.22F, 1.6683F, 1.595F), Interpolations.LINEAR),
               new Keyframe(2.0F, KeyframeAnimations.posVec(-0.28F, 1.6415F, 1.5825F), Interpolations.LINEAR),
               new Keyframe(2.56F, KeyframeAnimations.posVec(-0.34F, 1.6147F, 1.565F), Interpolations.LINEAR),
               new Keyframe(2.96F, KeyframeAnimations.posVec(-0.405F, 1.5879F, 1.54F), Interpolations.LINEAR),
               new Keyframe(3.4F, KeyframeAnimations.posVec(-0.47F, 1.5611F, 1.51F), Interpolations.LINEAR),
               new Keyframe(3.8F, KeyframeAnimations.posVec(-0.525F, 1.5276F, 1.4775F), Interpolations.LINEAR),
               new Keyframe(4.16F, KeyframeAnimations.posVec(-0.58F, 1.4941F, 1.4425F), Interpolations.LINEAR),
               new Keyframe(4.56F, KeyframeAnimations.posVec(-0.63F, 1.4673F, 1.4075F), Interpolations.LINEAR),
               new Keyframe(4.96F, KeyframeAnimations.posVec(-0.67F, 1.4338F, 1.37F), Interpolations.LINEAR),
               new Keyframe(5.4F, KeyframeAnimations.posVec(-0.705F, 1.407F, 1.33F), Interpolations.LINEAR),
               new Keyframe(5.8F, KeyframeAnimations.posVec(-0.73F, 1.3869F, 1.2925F), Interpolations.LINEAR),
               new Keyframe(6.16F, KeyframeAnimations.posVec(-0.745F, 1.3668F, 1.2575F), Interpolations.LINEAR),
               new Keyframe(6.56F, KeyframeAnimations.posVec(-0.745F, 1.3534F, 1.225F), Interpolations.LINEAR),
               new Keyframe(6.96F, KeyframeAnimations.posVec(-0.73F, 1.34F, 1.1975F), Interpolations.LINEAR),
               new Keyframe(7.56F, KeyframeAnimations.posVec(-0.705F, 1.34F, 1.1725F), Interpolations.LINEAR),
               new Keyframe(7.96F, KeyframeAnimations.posVec(-0.67F, 1.34F, 1.1525F), Interpolations.LINEAR),
               new Keyframe(8.36F, KeyframeAnimations.posVec(-0.63F, 1.3534F, 1.1425F), Interpolations.LINEAR),
               new Keyframe(8.76F, KeyframeAnimations.posVec(-0.58F, 1.3668F, 1.14F), Interpolations.LINEAR),
               new Keyframe(9.12F, KeyframeAnimations.posVec(-0.525F, 1.3869F, 1.1425F), Interpolations.LINEAR),
               new Keyframe(9.56F, KeyframeAnimations.posVec(-0.47F, 1.407F, 1.1525F), Interpolations.LINEAR),
               new Keyframe(9.96F, KeyframeAnimations.posVec(-0.405F, 1.4338F, 1.1725F), Interpolations.LINEAR),
               new Keyframe(10.36F, KeyframeAnimations.posVec(-0.34F, 1.4673F, 1.1975F), Interpolations.LINEAR),
               new Keyframe(10.76F, KeyframeAnimations.posVec(-0.28F, 1.4941F, 1.225F), Interpolations.LINEAR),
               new Keyframe(11.12F, KeyframeAnimations.posVec(-0.22F, 1.5276F, 1.2575F), Interpolations.LINEAR),
               new Keyframe(11.56F, KeyframeAnimations.posVec(-0.165F, 1.5611F, 1.2925F), Interpolations.LINEAR),
               new Keyframe(11.96F, KeyframeAnimations.posVec(-0.115F, 1.5879F, 1.33F), Interpolations.LINEAR),
               new Keyframe(12.52F, KeyframeAnimations.posVec(-0.075F, 1.6147F, 1.37F), Interpolations.LINEAR),
               new Keyframe(12.92F, KeyframeAnimations.posVec(-0.045F, 1.6415F, 1.4075F), Interpolations.LINEAR),
               new Keyframe(13.36F, KeyframeAnimations.posVec(-0.02F, 1.6683F, 1.4425F), Interpolations.LINEAR),
               new Keyframe(13.76F, KeyframeAnimations.posVec(-0.005F, 1.6884F, 1.4775F), Interpolations.LINEAR),
               new Keyframe(14.12F, KeyframeAnimations.posVec(0.0F, 1.7018F, 1.51F), Interpolations.LINEAR),
               new Keyframe(14.52F, KeyframeAnimations.posVec(-0.01F, 1.7085F, 1.54F), Interpolations.LINEAR),
               new Keyframe(15.0F, KeyframeAnimations.posVec(-0.03F, 1.7152F, 1.57F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "bone",
         new AnimationChannel(Targets.ROTATION, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 30.0F, 122.5F), Interpolations.LINEAR)})
      )
      .addAnimation(
         "bone",
         new AnimationChannel(Targets.POSITION, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.posVec(-4.0F, 5.0F, 5.0F), Interpolations.LINEAR)})
      )
      .addAnimation(
         "bone", new AnimationChannel(Targets.SCALE, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.scaleVec(-1.0, 1.0, 1.0), Interpolations.LINEAR)})
      )
      .build();
   public static final AnimationDefinition IDLE_LEFT = Builder.withLength(15.0F)
      .looping()
      .addAnimation(
         "main",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(-3.955F, 2.7772F, 0.6041F), Interpolations.LINEAR),
               new Keyframe(0.4F, KeyframeAnimations.degreeVec(-3.845F, 3.3811F, 0.5259F), Interpolations.LINEAR),
               new Keyframe(0.8F, KeyframeAnimations.degreeVec(-3.735F, 3.9916F, 0.452F), Interpolations.LINEAR),
               new Keyframe(1.2F, KeyframeAnimations.degreeVec(-3.625F, 4.5977F, 0.3825F), Interpolations.LINEAR),
               new Keyframe(1.6F, KeyframeAnimations.degreeVec(-3.515F, 5.1796F, 0.3217F), Interpolations.LINEAR),
               new Keyframe(2.0F, KeyframeAnimations.degreeVec(-3.405F, 5.7218F, 0.2739F), Interpolations.LINEAR),
               new Keyframe(2.6F, KeyframeAnimations.degreeVec(-3.305F, 6.2354F, 0.2348F), Interpolations.LINEAR),
               new Keyframe(3.0F, KeyframeAnimations.degreeVec(-3.215F, 6.6586F, 0.2087F), Interpolations.LINEAR),
               new Keyframe(3.4F, KeyframeAnimations.degreeVec(-3.135F, 6.9738F, 0.2F), Interpolations.LINEAR),
               new Keyframe(3.8F, KeyframeAnimations.degreeVec(-3.075F, 7.214F, 0.2044F), Interpolations.LINEAR),
               new Keyframe(4.2F, KeyframeAnimations.degreeVec(-3.03F, 7.3419F, 0.2217F), Interpolations.LINEAR),
               new Keyframe(4.6F, KeyframeAnimations.degreeVec(-3.005F, 7.3419F, 0.2543F), Interpolations.LINEAR),
               new Keyframe(5.0F, KeyframeAnimations.degreeVec(-3.0F, 7.214F, 0.2978F), Interpolations.LINEAR),
               new Keyframe(5.4F, KeyframeAnimations.degreeVec(-3.01F, 6.9738F, 0.3521F), Interpolations.LINEAR),
               new Keyframe(5.8F, KeyframeAnimations.degreeVec(-3.05F, 6.6365F, 0.4173F), Interpolations.LINEAR),
               new Keyframe(6.2F, KeyframeAnimations.degreeVec(-3.1F, 6.2134F, 0.4889F), Interpolations.LINEAR),
               new Keyframe(6.6F, KeyframeAnimations.degreeVec(-3.17F, 5.7218F, 0.565F), Interpolations.LINEAR),
               new Keyframe(7.0F, KeyframeAnimations.degreeVec(-3.26F, 5.1796F, 0.6454F), Interpolations.LINEAR),
               new Keyframe(7.6F, KeyframeAnimations.degreeVec(-3.35F, 4.5977F, 0.7279F), Interpolations.LINEAR),
               new Keyframe(8.0F, KeyframeAnimations.degreeVec(-3.46F, 3.9916F, 0.8083F), Interpolations.LINEAR),
               new Keyframe(8.4F, KeyframeAnimations.degreeVec(-3.57F, 3.3811F, 0.8843F), Interpolations.LINEAR),
               new Keyframe(8.8F, KeyframeAnimations.degreeVec(-3.68F, 2.7772F, 0.956F), Interpolations.LINEAR),
               new Keyframe(9.2F, KeyframeAnimations.degreeVec(-3.79F, 2.1953F, 1.0212F), Interpolations.LINEAR),
               new Keyframe(9.6F, KeyframeAnimations.degreeVec(-3.9F, 1.6531F, 1.0777F), Interpolations.LINEAR),
               new Keyframe(10.0F, KeyframeAnimations.degreeVec(-4.01F, 1.1616F, 1.1211F), Interpolations.LINEAR),
               new Keyframe(10.4F, KeyframeAnimations.degreeVec(-4.1F, 0.7384F, 1.1516F), Interpolations.LINEAR),
               new Keyframe(10.8F, KeyframeAnimations.degreeVec(-4.19F, 0.3989F, 1.1689F), Interpolations.LINEAR),
               new Keyframe(11.2F, KeyframeAnimations.degreeVec(-4.26F, 0.1587F, 1.1689F), Interpolations.LINEAR),
               new Keyframe(11.6F, KeyframeAnimations.degreeVec(-4.31F, 0.0331F, 1.1516F), Interpolations.LINEAR),
               new Keyframe(12.0F, KeyframeAnimations.degreeVec(-4.35F, 0.0F, 1.1211F), Interpolations.LINEAR),
               new Keyframe(12.6F, KeyframeAnimations.degreeVec(-4.36F, 0.0661F, 1.0777F), Interpolations.LINEAR),
               new Keyframe(13.0F, KeyframeAnimations.degreeVec(-4.35F, 0.2513F, 1.0212F), Interpolations.LINEAR),
               new Keyframe(13.4F, KeyframeAnimations.degreeVec(-4.31F, 0.5466F, 0.956F), Interpolations.LINEAR),
               new Keyframe(13.8F, KeyframeAnimations.degreeVec(-4.26F, 0.9301F, 0.8843F), Interpolations.LINEAR),
               new Keyframe(14.2F, KeyframeAnimations.degreeVec(-4.19F, 1.393F, 0.8083F), Interpolations.LINEAR),
               new Keyframe(14.6F, KeyframeAnimations.degreeVec(-4.1F, 1.9132F, 0.7279F), Interpolations.LINEAR),
               new Keyframe(15.0F, KeyframeAnimations.degreeVec(-3.955F, 2.7772F, 0.6041F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "main",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.045F, 1.7152F, 1.575F), Interpolations.LINEAR),
               new Keyframe(0.4F, KeyframeAnimations.posVec(0.075F, 1.7085F, 1.59F), Interpolations.LINEAR),
               new Keyframe(0.8F, KeyframeAnimations.posVec(0.115F, 1.7018F, 1.6F), Interpolations.LINEAR),
               new Keyframe(1.2F, KeyframeAnimations.posVec(0.165F, 1.6884F, 1.6F), Interpolations.LINEAR),
               new Keyframe(1.6F, KeyframeAnimations.posVec(0.22F, 1.6683F, 1.595F), Interpolations.LINEAR),
               new Keyframe(2.0F, KeyframeAnimations.posVec(0.28F, 1.6415F, 1.5825F), Interpolations.LINEAR),
               new Keyframe(2.6F, KeyframeAnimations.posVec(0.34F, 1.6147F, 1.565F), Interpolations.LINEAR),
               new Keyframe(3.0F, KeyframeAnimations.posVec(0.405F, 1.5879F, 1.54F), Interpolations.LINEAR),
               new Keyframe(3.4F, KeyframeAnimations.posVec(0.47F, 1.5611F, 1.51F), Interpolations.LINEAR),
               new Keyframe(3.8F, KeyframeAnimations.posVec(0.525F, 1.5276F, 1.4775F), Interpolations.LINEAR),
               new Keyframe(4.2F, KeyframeAnimations.posVec(0.58F, 1.4941F, 1.4425F), Interpolations.LINEAR),
               new Keyframe(4.6F, KeyframeAnimations.posVec(0.63F, 1.4673F, 1.4075F), Interpolations.LINEAR),
               new Keyframe(5.0F, KeyframeAnimations.posVec(0.67F, 1.4338F, 1.37F), Interpolations.LINEAR),
               new Keyframe(5.4F, KeyframeAnimations.posVec(0.705F, 1.407F, 1.33F), Interpolations.LINEAR),
               new Keyframe(5.8F, KeyframeAnimations.posVec(0.73F, 1.3869F, 1.2925F), Interpolations.LINEAR),
               new Keyframe(6.2F, KeyframeAnimations.posVec(0.745F, 1.3668F, 1.2575F), Interpolations.LINEAR),
               new Keyframe(6.6F, KeyframeAnimations.posVec(0.745F, 1.3534F, 1.225F), Interpolations.LINEAR),
               new Keyframe(7.0F, KeyframeAnimations.posVec(0.73F, 1.34F, 1.1975F), Interpolations.LINEAR),
               new Keyframe(7.6F, KeyframeAnimations.posVec(0.705F, 1.34F, 1.1725F), Interpolations.LINEAR),
               new Keyframe(8.0F, KeyframeAnimations.posVec(0.67F, 1.34F, 1.1525F), Interpolations.LINEAR),
               new Keyframe(8.4F, KeyframeAnimations.posVec(0.63F, 1.3534F, 1.1425F), Interpolations.LINEAR),
               new Keyframe(8.8F, KeyframeAnimations.posVec(0.58F, 1.3668F, 1.14F), Interpolations.LINEAR),
               new Keyframe(9.2F, KeyframeAnimations.posVec(0.525F, 1.3869F, 1.1425F), Interpolations.LINEAR),
               new Keyframe(9.6F, KeyframeAnimations.posVec(0.47F, 1.407F, 1.1525F), Interpolations.LINEAR),
               new Keyframe(10.0F, KeyframeAnimations.posVec(0.405F, 1.4338F, 1.1725F), Interpolations.LINEAR),
               new Keyframe(10.4F, KeyframeAnimations.posVec(0.34F, 1.4673F, 1.1975F), Interpolations.LINEAR),
               new Keyframe(10.8F, KeyframeAnimations.posVec(0.28F, 1.4941F, 1.225F), Interpolations.LINEAR),
               new Keyframe(11.2F, KeyframeAnimations.posVec(0.22F, 1.5276F, 1.2575F), Interpolations.LINEAR),
               new Keyframe(11.6F, KeyframeAnimations.posVec(0.165F, 1.5611F, 1.2925F), Interpolations.LINEAR),
               new Keyframe(12.0F, KeyframeAnimations.posVec(0.115F, 1.5879F, 1.33F), Interpolations.LINEAR),
               new Keyframe(12.6F, KeyframeAnimations.posVec(0.075F, 1.6147F, 1.37F), Interpolations.LINEAR),
               new Keyframe(13.0F, KeyframeAnimations.posVec(0.045F, 1.6415F, 1.4075F), Interpolations.LINEAR),
               new Keyframe(13.4F, KeyframeAnimations.posVec(0.02F, 1.6683F, 1.4425F), Interpolations.LINEAR),
               new Keyframe(13.8F, KeyframeAnimations.posVec(0.005F, 1.6884F, 1.4775F), Interpolations.LINEAR),
               new Keyframe(14.2F, KeyframeAnimations.posVec(0.0F, 1.7018F, 1.51F), Interpolations.LINEAR),
               new Keyframe(14.6F, KeyframeAnimations.posVec(0.01F, 1.7085F, 1.54F), Interpolations.LINEAR),
               new Keyframe(15.0F, KeyframeAnimations.posVec(0.03F, 1.7152F, 1.57F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "bone",
         new AnimationChannel(Targets.ROTATION, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, -30.0F, -122.5F), Interpolations.LINEAR)})
      )
      .addAnimation(
         "bone", new AnimationChannel(Targets.POSITION, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.posVec(4.0F, 5.0F, 5.0F), Interpolations.LINEAR)})
      )
      .build();
   public static final AnimationDefinition ATTACK = Builder.withLength(0.64F)
      .addAnimation(
         "main",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(-3.955F, -2.7772F, -0.6041F), Interpolations.LINEAR),
               new Keyframe(0.04F, KeyframeAnimations.degreeVec(-3.95F, -21.78F, -0.6F), Interpolations.LINEAR),
               new Keyframe(0.12F, KeyframeAnimations.degreeVec(-3.95F, -36.18F, -0.6F), Interpolations.LINEAR),
               new Keyframe(0.28F, KeyframeAnimations.degreeVec(-3.95F, -41.78F, -0.6F), Interpolations.LINEAR),
               new Keyframe(0.44F, KeyframeAnimations.degreeVec(-3.955F, -2.7772F, -0.6041F), Interpolations.LINEAR),
               new Keyframe(0.52F, KeyframeAnimations.degreeVec(-3.95F, -0.08F, -0.6F), Interpolations.LINEAR),
               new Keyframe(0.64F, KeyframeAnimations.degreeVec(-3.95F, -2.78F, -0.6F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "main",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(-0.045F, 1.7152F, 1.575F), Interpolations.LINEAR),
               new Keyframe(0.04F, KeyframeAnimations.posVec(2.1861F, 2.143F, -3.7549F), Interpolations.LINEAR),
               new Keyframe(0.12F, KeyframeAnimations.posVec(-3.5981F, 1.218F, 7.3093F), Interpolations.LINEAR),
               new Keyframe(0.28F, KeyframeAnimations.posVec(-3.2234F, 1.3166F, 5.8609F), Interpolations.LINEAR),
               new Keyframe(0.44F, KeyframeAnimations.posVec(-0.045F, 1.7152F, 1.575F), Interpolations.LINEAR),
               new Keyframe(0.52F, KeyframeAnimations.posVec(0.15F, 1.82F, 1.18F), Interpolations.LINEAR),
               new Keyframe(0.64F, KeyframeAnimations.posVec(-0.05F, 1.72F, 1.58F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "main",
         new AnimationChannel(
            Targets.SCALE,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR),
               new Keyframe(0.04F, KeyframeAnimations.scaleVec(0.84F, 0.84F, 1.18F), Interpolations.LINEAR),
               new Keyframe(0.12F, KeyframeAnimations.scaleVec(0.71F, 0.73F, 1.4F), Interpolations.LINEAR),
               new Keyframe(0.28F, KeyframeAnimations.scaleVec(1.05F, 1.07F, 0.92F), Interpolations.LINEAR),
               new Keyframe(0.44F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "bone",
         new AnimationChannel(Targets.ROTATION, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 30.0F, 122.5F), Interpolations.LINEAR)})
      )
      .addAnimation(
         "bone",
         new AnimationChannel(Targets.POSITION, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.posVec(-4.0F, 5.0F, 5.0F), Interpolations.LINEAR)})
      )
      .addAnimation(
         "mouth1",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.08F, KeyframeAnimations.degreeVec(-10.7F, 18.0F, 67.0F), Interpolations.LINEAR),
               new Keyframe(0.28F, KeyframeAnimations.degreeVec(-15.9F, -29.0F, 22.0F), Interpolations.LINEAR),
               new Keyframe(0.48F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.56F, KeyframeAnimations.degreeVec(-2.5F, 4.2F, 4.7F), Interpolations.LINEAR),
               new Keyframe(0.64F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "mouth2",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.08F, KeyframeAnimations.degreeVec(-49.0F, -38.0F, -2.8F), Interpolations.LINEAR),
               new Keyframe(0.28F, KeyframeAnimations.degreeVec(-6.8F, -25.2F, 18.0F), Interpolations.LINEAR),
               new Keyframe(0.48F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.56F, KeyframeAnimations.degreeVec(2.0F, 2.7F, -1.6F), Interpolations.LINEAR),
               new Keyframe(0.64F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "mouth3",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.08F, KeyframeAnimations.degreeVec(21.0F, 19.0F, -25.0F), Interpolations.LINEAR),
               new Keyframe(0.28F, KeyframeAnimations.degreeVec(-18.0F, 4.9F, 4.0F), Interpolations.LINEAR),
               new Keyframe(0.48F, KeyframeAnimations.degreeVec(0.0F, -0.8F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.56F, KeyframeAnimations.degreeVec(3.1F, -1.4F, -8.6F), Interpolations.LINEAR),
               new Keyframe(0.64F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "mouth4",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.08F, KeyframeAnimations.degreeVec(31.0F, -14.0F, 56.0F), Interpolations.LINEAR),
               new Keyframe(0.28F, KeyframeAnimations.degreeVec(3.8F, 37.0F, 6.4F), Interpolations.LINEAR),
               new Keyframe(0.48F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.56F, KeyframeAnimations.degreeVec(-1.4F, -4.0F, 5.4F), Interpolations.LINEAR),
               new Keyframe(0.64F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .build();
   public static final AnimationDefinition ATTACK_LEFT = Builder.withLength(0.64F)
      .addAnimation(
         "main",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(-3.955F, 2.7772F, 0.6041F), Interpolations.LINEAR),
               new Keyframe(0.04F, KeyframeAnimations.degreeVec(-3.95F, 21.78F, 0.6F), Interpolations.LINEAR),
               new Keyframe(0.12F, KeyframeAnimations.degreeVec(-3.95F, 36.18F, 0.6F), Interpolations.LINEAR),
               new Keyframe(0.28F, KeyframeAnimations.degreeVec(-3.95F, 41.78F, 0.6F), Interpolations.LINEAR),
               new Keyframe(0.44F, KeyframeAnimations.degreeVec(-3.955F, 2.7772F, 0.6041F), Interpolations.LINEAR),
               new Keyframe(0.52F, KeyframeAnimations.degreeVec(-3.95F, 0.08F, 0.6F), Interpolations.LINEAR),
               new Keyframe(0.64F, KeyframeAnimations.degreeVec(-3.95F, 2.78F, 0.6F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "main",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.045F, 1.7152F, 1.575F), Interpolations.LINEAR),
               new Keyframe(0.04F, KeyframeAnimations.posVec(-2.1861F, 2.143F, -3.7549F), Interpolations.LINEAR),
               new Keyframe(0.12F, KeyframeAnimations.posVec(3.5981F, 1.218F, 7.3093F), Interpolations.LINEAR),
               new Keyframe(0.28F, KeyframeAnimations.posVec(3.2234F, 1.3166F, 5.8609F), Interpolations.LINEAR),
               new Keyframe(0.44F, KeyframeAnimations.posVec(0.045F, 1.7152F, 1.575F), Interpolations.LINEAR),
               new Keyframe(0.52F, KeyframeAnimations.posVec(-0.15F, 1.82F, 1.18F), Interpolations.LINEAR),
               new Keyframe(0.64F, KeyframeAnimations.posVec(0.05F, 1.72F, 1.58F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "main",
         new AnimationChannel(
            Targets.SCALE,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR),
               new Keyframe(0.04F, KeyframeAnimations.scaleVec(0.84F, 0.84F, 1.18F), Interpolations.LINEAR),
               new Keyframe(0.12F, KeyframeAnimations.scaleVec(0.71F, 0.73F, 1.4F), Interpolations.LINEAR),
               new Keyframe(0.28F, KeyframeAnimations.scaleVec(1.05F, 1.07F, 0.92F), Interpolations.LINEAR),
               new Keyframe(0.44F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "bone",
         new AnimationChannel(Targets.ROTATION, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, -30.0F, -122.5F), Interpolations.LINEAR)})
      )
      .addAnimation(
         "bone", new AnimationChannel(Targets.POSITION, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.posVec(4.0F, 5.0F, 5.0F), Interpolations.LINEAR)})
      )
      .addAnimation(
         "mouth1",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.08F, KeyframeAnimations.degreeVec(-10.7F, -18.0F, -67.0F), Interpolations.LINEAR),
               new Keyframe(0.28F, KeyframeAnimations.degreeVec(-15.9F, 29.0F, -22.0F), Interpolations.LINEAR),
               new Keyframe(0.48F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.56F, KeyframeAnimations.degreeVec(-2.5F, -4.2F, -4.7F), Interpolations.LINEAR),
               new Keyframe(0.64F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "mouth2",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.08F, KeyframeAnimations.degreeVec(-49.0F, 38.0F, 2.8F), Interpolations.LINEAR),
               new Keyframe(0.28F, KeyframeAnimations.degreeVec(-6.8F, 25.2F, -18.0F), Interpolations.LINEAR),
               new Keyframe(0.48F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.56F, KeyframeAnimations.degreeVec(2.0F, -2.7F, 1.6F), Interpolations.LINEAR),
               new Keyframe(0.64F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "mouth3",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.08F, KeyframeAnimations.degreeVec(21.0F, -19.0F, 25.0F), Interpolations.LINEAR),
               new Keyframe(0.28F, KeyframeAnimations.degreeVec(-18.0F, -4.9F, -4.0F), Interpolations.LINEAR),
               new Keyframe(0.48F, KeyframeAnimations.degreeVec(0.0F, 0.8F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.56F, KeyframeAnimations.degreeVec(3.1F, 1.4F, 8.6F), Interpolations.LINEAR),
               new Keyframe(0.64F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "mouth4",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.08F, KeyframeAnimations.degreeVec(31.0F, 14.0F, -56.0F), Interpolations.LINEAR),
               new Keyframe(0.28F, KeyframeAnimations.degreeVec(3.8F, -37.0F, -6.4F), Interpolations.LINEAR),
               new Keyframe(0.48F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.56F, KeyframeAnimations.degreeVec(-1.4F, 4.0F, -5.4F), Interpolations.LINEAR),
               new Keyframe(0.64F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .build();
   public static final AnimationDefinition RELOAD = Builder.withLength(0.44F)
      .addAnimation(
         "main",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(-3.955F, -2.7772F, -0.6041F), Interpolations.LINEAR),
               new Keyframe(0.2F, KeyframeAnimations.degreeVec(-3.95F, 6.22F, -0.6F), Interpolations.LINEAR),
               new Keyframe(0.44F, KeyframeAnimations.degreeVec(-3.955F, -2.7772F, -0.6041F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "main",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(-0.045F, 1.7152F, 1.575F), Interpolations.LINEAR),
               new Keyframe(0.2F, KeyframeAnimations.posVec(-0.0643F, 1.6992F, 1.8789F), Interpolations.LINEAR),
               new Keyframe(0.44F, KeyframeAnimations.posVec(-0.045F, 1.7152F, 1.575F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "bone",
         new AnimationChannel(Targets.ROTATION, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 30.0F, 122.5F), Interpolations.LINEAR)})
      )
      .addAnimation(
         "bone",
         new AnimationChannel(Targets.POSITION, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.posVec(-4.0F, 5.0F, 5.0F), Interpolations.LINEAR)})
      )
      .addAnimation(
         "mouth1",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.12F, KeyframeAnimations.degreeVec(-10.0F, 1.0F, 5.0F), Interpolations.LINEAR),
               new Keyframe(0.4F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "mouth2",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.12F, KeyframeAnimations.degreeVec(-7.0F, 0.0F, -11.0F), Interpolations.LINEAR),
               new Keyframe(0.4F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "mouth3",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.16F, KeyframeAnimations.degreeVec(16.0F, 0.0F, -20.0F), Interpolations.LINEAR),
               new Keyframe(0.4F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "mouth4",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.2F, KeyframeAnimations.degreeVec(8.0F, 0.0F, 17.0F), Interpolations.LINEAR),
               new Keyframe(0.4F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "front",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.04F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.12F, KeyframeAnimations.degreeVec(0.0F, -11.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.28F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "front",
         new AnimationChannel(
            Targets.SCALE,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR),
               new Keyframe(0.08F, KeyframeAnimations.scaleVec(1.2F, 1.2F, 1.2F), Interpolations.LINEAR),
               new Keyframe(0.24F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "middle",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.12F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.2F, KeyframeAnimations.degreeVec(0.0F, -11.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.36F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "middle",
         new AnimationChannel(
            Targets.SCALE,
            new Keyframe[]{
               new Keyframe(0.08F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR),
               new Keyframe(0.16F, KeyframeAnimations.scaleVec(1.2F, 1.2F, 1.2F), Interpolations.LINEAR),
               new Keyframe(0.32F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "back",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.2F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.28F, KeyframeAnimations.degreeVec(0.0F, -11.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.44F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "back",
         new AnimationChannel(
            Targets.SCALE,
            new Keyframe[]{
               new Keyframe(0.16F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR),
               new Keyframe(0.24F, KeyframeAnimations.scaleVec(1.2F, 1.2F, 1.2F), Interpolations.LINEAR),
               new Keyframe(0.4F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR)
            }
         )
      )
      .build();
   public static final AnimationDefinition RELOAD_LEFT = Builder.withLength(0.44F)
      .addAnimation(
         "main",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(-3.955F, 2.7772F, 0.6041F), Interpolations.LINEAR),
               new Keyframe(0.2F, KeyframeAnimations.degreeVec(-3.95F, -6.22F, 0.6F), Interpolations.LINEAR),
               new Keyframe(0.44F, KeyframeAnimations.degreeVec(-3.955F, 2.7772F, 0.6041F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "main",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.045F, 1.7152F, 1.575F), Interpolations.LINEAR),
               new Keyframe(0.2F, KeyframeAnimations.posVec(0.0643F, 1.6992F, 1.8789F), Interpolations.LINEAR),
               new Keyframe(0.44F, KeyframeAnimations.posVec(0.045F, 1.7152F, 1.575F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "bone",
         new AnimationChannel(Targets.ROTATION, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, -30.0F, -122.5F), Interpolations.LINEAR)})
      )
      .addAnimation(
         "bone", new AnimationChannel(Targets.POSITION, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.posVec(4.0F, 5.0F, 5.0F), Interpolations.LINEAR)})
      )
      .addAnimation(
         "mouth1",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.12F, KeyframeAnimations.degreeVec(-10.0F, -1.0F, -5.0F), Interpolations.LINEAR),
               new Keyframe(0.4F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "mouth2",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.12F, KeyframeAnimations.degreeVec(-7.0F, 0.0F, 11.0F), Interpolations.LINEAR),
               new Keyframe(0.4F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "mouth3",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.16F, KeyframeAnimations.degreeVec(16.0F, 0.0F, 20.0F), Interpolations.LINEAR),
               new Keyframe(0.4F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "mouth4",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.2F, KeyframeAnimations.degreeVec(8.0F, 0.0F, -17.0F), Interpolations.LINEAR),
               new Keyframe(0.4F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "front",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.04F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.12F, KeyframeAnimations.degreeVec(0.0F, 11.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.28F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "front",
         new AnimationChannel(
            Targets.SCALE,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR),
               new Keyframe(0.08F, KeyframeAnimations.scaleVec(1.2F, 1.2F, 1.2F), Interpolations.LINEAR),
               new Keyframe(0.24F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "middle",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.12F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.2F, KeyframeAnimations.degreeVec(0.0F, 11.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.36F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "middle",
         new AnimationChannel(
            Targets.SCALE,
            new Keyframe[]{
               new Keyframe(0.08F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR),
               new Keyframe(0.16F, KeyframeAnimations.scaleVec(1.2F, 1.2F, 1.2F), Interpolations.LINEAR),
               new Keyframe(0.32F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "back",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.2F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.28F, KeyframeAnimations.degreeVec(0.0F, 11.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.44F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "back",
         new AnimationChannel(
            Targets.SCALE,
            new Keyframe[]{
               new Keyframe(0.16F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR),
               new Keyframe(0.24F, KeyframeAnimations.scaleVec(1.2F, 1.2F, 1.2F), Interpolations.LINEAR),
               new Keyframe(0.4F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR)
            }
         )
      )
      .build();
   public static final AnimationDefinition ATTACK_YETI = Builder.withLength(0.8F)
      .addAnimation(
         "main",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(-3.955F, 2.7772F, 0.6041F), Interpolations.LINEAR),
               new Keyframe(0.04F, KeyframeAnimations.degreeVec(26.55F, -22.22F, 2.6F), Interpolations.LINEAR),
               new Keyframe(0.12F, KeyframeAnimations.degreeVec(4.3F, 13.93F, 11.85F), Interpolations.LINEAR),
               new Keyframe(0.2F, KeyframeAnimations.degreeVec(-2.2F, 27.28F, 10.6F), Interpolations.LINEAR),
               new Keyframe(0.28F, KeyframeAnimations.degreeVec(-2.65F, 28.88F, 10.15F), Interpolations.LINEAR),
               new Keyframe(0.36F, KeyframeAnimations.degreeVec(-1.45F, 34.655F, 10.6F), Interpolations.LINEAR),
               new Keyframe(0.48F, KeyframeAnimations.degreeVec(0.22F, 39.115F, 5.6F), Interpolations.LINEAR),
               new Keyframe(0.6F, KeyframeAnimations.degreeVec(-2.955F, 8.6772F, 0.6041F), Interpolations.LINEAR),
               new Keyframe(0.68F, KeyframeAnimations.degreeVec(-5.355F, 1.7522F, -0.1959F), Interpolations.LINEAR),
               new Keyframe(0.76F, KeyframeAnimations.degreeVec(-3.955F, 2.7772F, 0.6041F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "main",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.045F, 1.7152F, 1.575F), Interpolations.LINEAR),
               new Keyframe(0.04F, KeyframeAnimations.posVec(8.7622F, 2.1957F, 11.1782F), Interpolations.LINEAR),
               new Keyframe(0.12F, KeyframeAnimations.posVec(2.4034F, -2.1557F, -2.6854F), Interpolations.LINEAR),
               new Keyframe(0.2F, KeyframeAnimations.posVec(-3.1276F, -3.0791F, -8.7988F), Interpolations.LINEAR),
               new Keyframe(0.28F, KeyframeAnimations.posVec(-3.93F, -3.38F, -9.5F), Interpolations.LINEAR),
               new Keyframe(0.36F, KeyframeAnimations.posVec(-3.33F, -3.08F, -8.8F), Interpolations.LINEAR),
               new Keyframe(0.6F, KeyframeAnimations.posVec(0.045F, 1.7152F, 1.575F), Interpolations.LINEAR),
               new Keyframe(0.68F, KeyframeAnimations.posVec(0.645F, 2.0152F, 2.475F), Interpolations.LINEAR),
               new Keyframe(0.76F, KeyframeAnimations.posVec(0.045F, 1.7152F, 1.575F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "main",
         new AnimationChannel(
            Targets.SCALE,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR),
               new Keyframe(0.04F, KeyframeAnimations.scaleVec(1.55F, 1.0, 1.0), Interpolations.LINEAR),
               new Keyframe(0.12F, KeyframeAnimations.scaleVec(1.1F, 1.32F, 1.0), Interpolations.LINEAR),
               new Keyframe(0.2F, KeyframeAnimations.scaleVec(1.0, 0.83F, 1.0), Interpolations.LINEAR),
               new Keyframe(0.28F, KeyframeAnimations.scaleVec(1.0, 0.96F, 1.0), Interpolations.LINEAR),
               new Keyframe(0.36F, KeyframeAnimations.scaleVec(1.0, 0.83F, 1.0), Interpolations.LINEAR),
               new Keyframe(0.44F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR),
               new Keyframe(0.52F, KeyframeAnimations.scaleVec(1.0, 1.1F, 1.0), Interpolations.LINEAR),
               new Keyframe(0.64F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "bone",
         new AnimationChannel(Targets.ROTATION, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 30.0F, 122.5F), Interpolations.LINEAR)})
      )
      .addAnimation(
         "bone",
         new AnimationChannel(Targets.POSITION, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.posVec(-4.0F, 5.0F, 5.0F), Interpolations.LINEAR)})
      )
      .addAnimation(
         "bone", new AnimationChannel(Targets.SCALE, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.scaleVec(-1.0, 1.0, 1.0), Interpolations.LINEAR)})
      )
      .build();
   public static final AnimationDefinition ATTACK_YETI_LEFT = Builder.withLength(0.8F)
      .addAnimation(
         "main",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(-3.955F, 2.7772F, 0.6041F), Interpolations.LINEAR),
               new Keyframe(0.04F, KeyframeAnimations.degreeVec(26.55F, -22.22F, 2.6F), Interpolations.LINEAR),
               new Keyframe(0.12F, KeyframeAnimations.degreeVec(4.3F, 13.93F, 11.85F), Interpolations.LINEAR),
               new Keyframe(0.2F, KeyframeAnimations.degreeVec(-2.2F, 27.28F, 10.6F), Interpolations.LINEAR),
               new Keyframe(0.28F, KeyframeAnimations.degreeVec(-2.65F, 28.88F, 10.15F), Interpolations.LINEAR),
               new Keyframe(0.36F, KeyframeAnimations.degreeVec(-1.45F, 34.655F, 10.6F), Interpolations.LINEAR),
               new Keyframe(0.48F, KeyframeAnimations.degreeVec(0.22F, 39.115F, 5.6F), Interpolations.LINEAR),
               new Keyframe(0.6F, KeyframeAnimations.degreeVec(-2.955F, 8.6772F, 0.6041F), Interpolations.LINEAR),
               new Keyframe(0.68F, KeyframeAnimations.degreeVec(-5.355F, 1.7522F, -0.1959F), Interpolations.LINEAR),
               new Keyframe(0.76F, KeyframeAnimations.degreeVec(-3.955F, 2.7772F, 0.6041F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "main",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.045F, 1.7152F, 1.575F), Interpolations.LINEAR),
               new Keyframe(0.04F, KeyframeAnimations.posVec(8.7622F, 2.1957F, 11.1782F), Interpolations.LINEAR),
               new Keyframe(0.12F, KeyframeAnimations.posVec(2.4034F, -2.1557F, -2.6854F), Interpolations.LINEAR),
               new Keyframe(0.2F, KeyframeAnimations.posVec(-3.1276F, -3.0791F, -8.7988F), Interpolations.LINEAR),
               new Keyframe(0.28F, KeyframeAnimations.posVec(-3.93F, -3.38F, -9.5F), Interpolations.LINEAR),
               new Keyframe(0.36F, KeyframeAnimations.posVec(-3.33F, -3.08F, -8.8F), Interpolations.LINEAR),
               new Keyframe(0.6F, KeyframeAnimations.posVec(0.045F, 1.7152F, 1.575F), Interpolations.LINEAR),
               new Keyframe(0.68F, KeyframeAnimations.posVec(0.645F, 2.0152F, 2.475F), Interpolations.LINEAR),
               new Keyframe(0.76F, KeyframeAnimations.posVec(0.045F, 1.7152F, 1.575F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "main",
         new AnimationChannel(
            Targets.SCALE,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR),
               new Keyframe(0.04F, KeyframeAnimations.scaleVec(1.55F, 1.0, 1.0), Interpolations.LINEAR),
               new Keyframe(0.12F, KeyframeAnimations.scaleVec(1.1F, 1.32F, 1.0), Interpolations.LINEAR),
               new Keyframe(0.2F, KeyframeAnimations.scaleVec(1.0, 0.83F, 1.0), Interpolations.LINEAR),
               new Keyframe(0.28F, KeyframeAnimations.scaleVec(1.0, 0.96F, 1.0), Interpolations.LINEAR),
               new Keyframe(0.36F, KeyframeAnimations.scaleVec(1.0, 0.83F, 1.0), Interpolations.LINEAR),
               new Keyframe(0.44F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR),
               new Keyframe(0.52F, KeyframeAnimations.scaleVec(1.0, 1.1F, 1.0), Interpolations.LINEAR),
               new Keyframe(0.64F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "bone",
         new AnimationChannel(Targets.ROTATION, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, -30.0F, -122.5F), Interpolations.LINEAR)})
      )
      .addAnimation(
         "bone", new AnimationChannel(Targets.POSITION, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.posVec(4.0F, 5.0F, 5.0F), Interpolations.LINEAR)})
      )
      .build();
}
