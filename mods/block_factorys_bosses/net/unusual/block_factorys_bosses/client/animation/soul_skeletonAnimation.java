package net.unusual.block_factorys_bosses.client.animation;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.animation.AnimationChannel.Interpolations;
import net.minecraft.client.animation.AnimationChannel.Targets;
import net.minecraft.client.animation.AnimationDefinition.Builder;

public class soul_skeletonAnimation {
   public static final AnimationDefinition IDLE = Builder.withLength(3.5F)
      .looping()
      .addAnimation(
         "all",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 12.5F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.875F, KeyframeAnimations.degreeVec(0.0F, 12.5F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(2.6667F, KeyframeAnimations.degreeVec(0.0F, 12.5F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(3.5F, KeyframeAnimations.degreeVec(0.0F, 12.5F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "all",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -3.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.875F, KeyframeAnimations.posVec(0.0F, -3.79F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.7917F, KeyframeAnimations.posVec(0.0F, -4.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(2.6667F, KeyframeAnimations.posVec(0.0F, -3.25F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(3.5F, KeyframeAnimations.posVec(0.0F, -3.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "right_leg",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(10.0F, 20.0F, 10.0F), Interpolations.LINEAR),
               new Keyframe(0.875F, KeyframeAnimations.degreeVec(13.44F, 19.81F, 10.82F), Interpolations.CATMULLROM),
               new Keyframe(1.7917F, KeyframeAnimations.degreeVec(14.3506F, 19.7551F, 11.031F), Interpolations.CATMULLROM),
               new Keyframe(2.6667F, KeyframeAnimations.degreeVec(11.09F, 19.94F, 10.26F), Interpolations.CATMULLROM),
               new Keyframe(3.5F, KeyframeAnimations.degreeVec(10.0F, 20.0F, 10.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "right_leg",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 2.4F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.875F, KeyframeAnimations.posVec(0.0F, 2.95F, -0.63F), Interpolations.CATMULLROM),
               new Keyframe(1.7917F, KeyframeAnimations.posVec(0.0F, 3.1F, -0.8F), Interpolations.CATMULLROM),
               new Keyframe(2.6667F, KeyframeAnimations.posVec(0.0F, 2.58F, -0.2F), Interpolations.CATMULLROM),
               new Keyframe(3.5F, KeyframeAnimations.posVec(0.0F, 2.4F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "left_leg",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(-0.7619F, -20.4004F, -4.8295F), Interpolations.LINEAR),
               new Keyframe(0.875F, KeyframeAnimations.degreeVec(2.6F, -20.31F, -6.52F), Interpolations.CATMULLROM),
               new Keyframe(1.7917F, KeyframeAnimations.degreeVec(3.4787F, -20.2919F, -6.9594F), Interpolations.CATMULLROM),
               new Keyframe(2.6667F, KeyframeAnimations.degreeVec(0.3F, -20.37F, -5.36F), Interpolations.CATMULLROM),
               new Keyframe(3.5F, KeyframeAnimations.degreeVec(-0.7619F, -20.4004F, -4.8295F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "left_leg",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 2.0F, -2.0F), Interpolations.LINEAR),
               new Keyframe(0.875F, KeyframeAnimations.posVec(0.0F, 2.79F, -2.79F), Interpolations.CATMULLROM),
               new Keyframe(1.7917F, KeyframeAnimations.posVec(0.0F, 3.0F, -3.0F), Interpolations.CATMULLROM),
               new Keyframe(2.6667F, KeyframeAnimations.posVec(0.0F, 2.25F, -2.25F), Interpolations.CATMULLROM),
               new Keyframe(3.5F, KeyframeAnimations.posVec(0.0F, 2.0F, -2.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "torso",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(15.3488F, -17.3259F, -1.354F), Interpolations.LINEAR),
               new Keyframe(0.875F, KeyframeAnimations.degreeVec(21.29F, -17.33F, -1.35F), Interpolations.CATMULLROM),
               new Keyframe(1.7917F, KeyframeAnimations.degreeVec(22.8488F, -17.3259F, -1.354F), Interpolations.CATMULLROM),
               new Keyframe(2.6667F, KeyframeAnimations.degreeVec(17.22F, -17.33F, -1.35F), Interpolations.CATMULLROM),
               new Keyframe(3.5F, KeyframeAnimations.degreeVec(15.3488F, -17.3259F, -1.354F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "head",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(-7.5F, 10.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.875F, KeyframeAnimations.degreeVec(-16.94F, 10.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.7917F, KeyframeAnimations.degreeVec(-15.0F, 10.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(2.6667F, KeyframeAnimations.degreeVec(-2.87F, 10.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(3.5F, KeyframeAnimations.degreeVec(-7.5F, 10.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "left_arm",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(-12.5F, 10.0F, -20.0F), Interpolations.LINEAR),
               new Keyframe(0.875F, KeyframeAnimations.degreeVec(-22.4F, 10.0F, -20.0F), Interpolations.CATMULLROM),
               new Keyframe(1.7917F, KeyframeAnimations.degreeVec(-25.0F, 10.0F, -20.0F), Interpolations.CATMULLROM),
               new Keyframe(2.6667F, KeyframeAnimations.degreeVec(-15.62F, 10.0F, -20.0F), Interpolations.CATMULLROM),
               new Keyframe(3.5F, KeyframeAnimations.degreeVec(-12.5F, 10.0F, -20.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "right_arm",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(-48.9455F, 16.7432F, 4.7001F), Interpolations.LINEAR),
               new Keyframe(0.875F, KeyframeAnimations.degreeVec(-54.88F, 16.74F, 4.7F), Interpolations.CATMULLROM),
               new Keyframe(1.7917F, KeyframeAnimations.degreeVec(-56.4455F, 16.7432F, 4.7001F), Interpolations.CATMULLROM),
               new Keyframe(2.6667F, KeyframeAnimations.degreeVec(-50.82F, 16.74F, 4.7F), Interpolations.CATMULLROM),
               new Keyframe(3.5F, KeyframeAnimations.degreeVec(-48.9455F, 16.7432F, 4.7001F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "sword",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 27.5F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.875F, KeyframeAnimations.degreeVec(0.0F, 27.5F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(2.6667F, KeyframeAnimations.degreeVec(0.0F, 27.5F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(3.5F, KeyframeAnimations.degreeVec(0.0F, 27.5F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .build();
   public static final AnimationDefinition WALK = Builder.withLength(1.0F)
      .looping()
      .addAnimation(
         "all",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, -12.5F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.125F, KeyframeAnimations.degreeVec(0.0F, -12.5F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(0.0F, -12.5F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.375F, KeyframeAnimations.degreeVec(0.0F, -12.5F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, -12.5F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.625F, KeyframeAnimations.degreeVec(0.0F, -12.5F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.75F, KeyframeAnimations.degreeVec(0.0F, -12.5F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.875F, KeyframeAnimations.degreeVec(0.0F, -12.5F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, -12.5F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "all",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 3.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.125F, KeyframeAnimations.posVec(-0.37F, 2.5F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.25F, KeyframeAnimations.posVec(-0.75F, 3.05F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.375F, KeyframeAnimations.posVec(-0.37F, 2.95F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 3.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.625F, KeyframeAnimations.posVec(0.37F, 2.5F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.75F, KeyframeAnimations.posVec(0.75F, 3.05F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.875F, KeyframeAnimations.posVec(0.37F, 2.95F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 3.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "right_leg",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(-20.0F, -20.0F, -5.0F), Interpolations.LINEAR),
               new Keyframe(0.125F, KeyframeAnimations.degreeVec(-15.16F, -19.91F, -6.24F), Interpolations.CATMULLROM),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(-10.3232F, -19.8184F, -7.4729F), Interpolations.CATMULLROM),
               new Keyframe(0.375F, KeyframeAnimations.degreeVec(-5.16F, -19.91F, -6.24F), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, -20.0F, -5.0F), Interpolations.CATMULLROM),
               new Keyframe(0.625F, KeyframeAnimations.degreeVec(-2.5F, -20.0F, -5.0F), Interpolations.CATMULLROM),
               new Keyframe(0.75F, KeyframeAnimations.degreeVec(-5.0F, -20.0F, -5.0F), Interpolations.CATMULLROM),
               new Keyframe(0.875F, KeyframeAnimations.degreeVec(-17.5F, -20.0F, -5.0F), Interpolations.CATMULLROM),
               new Keyframe(1.0F, KeyframeAnimations.degreeVec(-20.0F, -20.0F, -5.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "right_leg",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -2.4F, -2.0F), Interpolations.LINEAR),
               new Keyframe(0.125F, KeyframeAnimations.posVec(0.0F, -2.4F, -1.5F), Interpolations.CATMULLROM),
               new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, -2.4F, -1.0F), Interpolations.CATMULLROM),
               new Keyframe(0.375F, KeyframeAnimations.posVec(0.0F, -2.4F, -0.5F), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, -2.4F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.625F, KeyframeAnimations.posVec(0.0F, -2.4F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.75F, KeyframeAnimations.posVec(0.2615F, 0.5886F, -3.0F), Interpolations.CATMULLROM),
               new Keyframe(0.875F, KeyframeAnimations.posVec(0.2171F, 0.0852F, -2.4564F), Interpolations.CATMULLROM),
               new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, -2.4F, -2.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "left_leg",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(10.7619F, 20.4004F, -0.1705F), Interpolations.LINEAR),
               new Keyframe(0.125F, KeyframeAnimations.degreeVec(8.2619F, 20.4004F, -0.1705F), Interpolations.CATMULLROM),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(5.7619F, 20.4004F, -0.1705F), Interpolations.CATMULLROM),
               new Keyframe(0.375F, KeyframeAnimations.degreeVec(-6.7381F, 20.4004F, -0.1705F), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(-9.2381F, 20.4004F, -0.1705F), Interpolations.CATMULLROM),
               new Keyframe(0.625F, KeyframeAnimations.degreeVec(-4.3981F, 20.3104F, 1.0695F), Interpolations.CATMULLROM),
               new Keyframe(0.75F, KeyframeAnimations.degreeVec(0.4387F, 20.2188F, 2.3024F), Interpolations.CATMULLROM),
               new Keyframe(0.875F, KeyframeAnimations.degreeVec(5.6019F, 20.3104F, 1.0695F), Interpolations.CATMULLROM),
               new Keyframe(1.0F, KeyframeAnimations.degreeVec(10.7619F, 20.4004F, -0.1705F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "left_leg",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -2.0F, 2.0F), Interpolations.LINEAR),
               new Keyframe(0.125F, KeyframeAnimations.posVec(0.0F, -2.0F, 2.0F), Interpolations.CATMULLROM),
               new Keyframe(0.25F, KeyframeAnimations.posVec(-0.2615F, 0.9886F, -1.0F), Interpolations.CATMULLROM),
               new Keyframe(0.375F, KeyframeAnimations.posVec(-0.2171F, 0.4852F, -0.4564F), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, -2.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.625F, KeyframeAnimations.posVec(0.0F, -2.0F, 0.5F), Interpolations.CATMULLROM),
               new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, -2.0F, 1.0F), Interpolations.CATMULLROM),
               new Keyframe(0.875F, KeyframeAnimations.posVec(0.0F, -2.0F, 1.5F), Interpolations.CATMULLROM),
               new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, -2.0F, 2.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "torso",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(-0.0016F, 29.3934F, 4.638F), Interpolations.LINEAR),
               new Keyframe(0.125F, KeyframeAnimations.degreeVec(-0.0088F, 23.0959F, 3.964F), Interpolations.CATMULLROM),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(-0.0077F, 16.7966F, 3.2827F), Interpolations.CATMULLROM),
               new Keyframe(0.375F, KeyframeAnimations.degreeVec(-0.0088F, 11.0259F, 0.674F), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(-0.0016F, 5.2584F, -1.93F), Interpolations.CATMULLROM),
               new Keyframe(0.625F, KeyframeAnimations.degreeVec(-0.0088F, 11.5559F, -1.256F), Interpolations.CATMULLROM),
               new Keyframe(0.75F, KeyframeAnimations.degreeVec(-0.0077F, 17.8552F, -0.5747F), Interpolations.CATMULLROM),
               new Keyframe(0.875F, KeyframeAnimations.degreeVec(-0.0088F, 23.6259F, 2.034F), Interpolations.CATMULLROM),
               new Keyframe(1.0F, KeyframeAnimations.degreeVec(-0.0016F, 29.3934F, 4.638F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "head",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(7.5F, -20.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.125F, KeyframeAnimations.degreeVec(7.5F, -15.0F, -1.25F), Interpolations.CATMULLROM),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(7.5F, -10.0F, -2.5F), Interpolations.CATMULLROM),
               new Keyframe(0.375F, KeyframeAnimations.degreeVec(7.5F, -5.0F, -1.25F), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(7.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.625F, KeyframeAnimations.degreeVec(7.5F, -5.0F, 1.25F), Interpolations.CATMULLROM),
               new Keyframe(0.75F, KeyframeAnimations.degreeVec(7.5F, -10.0F, 2.5F), Interpolations.CATMULLROM),
               new Keyframe(0.875F, KeyframeAnimations.degreeVec(7.5F, -15.0F, 1.25F), Interpolations.CATMULLROM),
               new Keyframe(1.0F, KeyframeAnimations.degreeVec(7.5F, -20.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "left_arm",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(22.5008F, 9.9584F, 11.0095F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(24.5502F, -24.3687F, 2.8155F), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(28.3689F, -30.2053F, -5.8877F), Interpolations.CATMULLROM),
               new Keyframe(0.75F, KeyframeAnimations.degreeVec(24.6971F, -13.9627F, 2.6978F), Interpolations.CATMULLROM),
               new Keyframe(1.0F, KeyframeAnimations.degreeVec(22.5008F, 9.9584F, 11.0095F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "right_arm",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(58.7745F, 0.2766F, 10.6381F), Interpolations.LINEAR),
               new Keyframe(0.125F, KeyframeAnimations.degreeVec(60.2855F, 1.0668F, 13.2699F), Interpolations.CATMULLROM),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(61.7955F, 1.8668F, 15.9099F), Interpolations.CATMULLROM),
               new Keyframe(0.375F, KeyframeAnimations.degreeVec(63.3055F, 2.6668F, 18.5499F), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(64.8144F, 3.4621F, 21.1876F), Interpolations.CATMULLROM),
               new Keyframe(0.625F, KeyframeAnimations.degreeVec(63.3055F, 2.6668F, 18.5499F), Interpolations.CATMULLROM),
               new Keyframe(0.75F, KeyframeAnimations.degreeVec(61.7955F, 1.8668F, 15.9099F), Interpolations.CATMULLROM),
               new Keyframe(0.875F, KeyframeAnimations.degreeVec(60.2855F, 1.0668F, 13.2699F), Interpolations.CATMULLROM),
               new Keyframe(1.0F, KeyframeAnimations.degreeVec(58.7745F, 0.2766F, 10.6381F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "sword",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(12.5F, -27.5F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.125F, KeyframeAnimations.degreeVec(12.5F, -27.5F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(12.5F, -27.5F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.375F, KeyframeAnimations.degreeVec(12.5F, -27.5F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(12.5F, -27.5F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.625F, KeyframeAnimations.degreeVec(12.5F, -27.5F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.75F, KeyframeAnimations.degreeVec(12.5F, -27.5F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.875F, KeyframeAnimations.degreeVec(12.5F, -27.5F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.0F, KeyframeAnimations.degreeVec(12.5F, -27.5F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .build();
   public static final AnimationDefinition ATTACK = Builder.withLength(1.0F)
      .addAnimation(
         "all",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.375F, KeyframeAnimations.degreeVec(0.0F, -22.5F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.5833F, KeyframeAnimations.degreeVec(0.0F, -22.5F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "all",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.posVec(-1.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.375F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.5833F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "right_leg",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(-1.4627F, 0.6446F, -4.2099F), Interpolations.CATMULLROM),
               new Keyframe(0.375F, KeyframeAnimations.degreeVec(6.9237F, -0.0648F, -1.654F), Interpolations.CATMULLROM),
               new Keyframe(0.5833F, KeyframeAnimations.degreeVec(6.92F, -0.06F, -1.65F), Interpolations.CATMULLROM),
               new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "right_leg",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.5833F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "left_leg",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(1.6742F, 0.0059F, -4.8012F), Interpolations.CATMULLROM),
               new Keyframe(0.375F, KeyframeAnimations.degreeVec(-5.2664F, 0.3684F, 7.982F), Interpolations.CATMULLROM),
               new Keyframe(0.5833F, KeyframeAnimations.degreeVec(-5.2681F, 0.3704F, 7.9795F), Interpolations.CATMULLROM),
               new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "left_leg",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.5833F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "torso",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(-0.7032F, 19.3322F, 5.1973F), Interpolations.CATMULLROM),
               new Keyframe(0.375F, KeyframeAnimations.degreeVec(0.4823F, -4.8163F, -1.4272F), Interpolations.CATMULLROM),
               new Keyframe(0.5833F, KeyframeAnimations.degreeVec(3.2181F, -12.939F, -4.2107F), Interpolations.CATMULLROM),
               new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "head",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(0.0039F, -19.8324F, 2.5969F), Interpolations.CATMULLROM),
               new Keyframe(0.375F, KeyframeAnimations.degreeVec(-0.8201F, 17.3365F, -2.5324F), Interpolations.CATMULLROM),
               new Keyframe(0.5833F, KeyframeAnimations.degreeVec(-0.82F, 17.34F, -2.53F), Interpolations.CATMULLROM),
               new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "left_arm",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(-4.0434F, 31.5327F, -8.9372F), Interpolations.CATMULLROM),
               new Keyframe(0.375F, KeyframeAnimations.degreeVec(10.98F, -44.2621F, -13.7547F), Interpolations.CATMULLROM),
               new Keyframe(0.5833F, KeyframeAnimations.degreeVec(10.8093F, -52.007F, -13.4742F), Interpolations.CATMULLROM),
               new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "right_arm",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.125F, KeyframeAnimations.degreeVec(-117.0515F, -14.3665F, 110.1711F), Interpolations.CATMULLROM),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(-116.5668F, -5.2853F, 115.086F), Interpolations.CATMULLROM),
               new Keyframe(0.375F, KeyframeAnimations.degreeVec(25.5016F, -68.6986F, -48.9966F), Interpolations.CATMULLROM),
               new Keyframe(0.5833F, KeyframeAnimations.degreeVec(28.3996F, -62.4414F, -52.8385F), Interpolations.CATMULLROM),
               new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "sword",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(0.0F, -35.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.375F, KeyframeAnimations.degreeVec(0.0F, 15.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.5833F, KeyframeAnimations.degreeVec(0.0F, 20.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .build();
}
