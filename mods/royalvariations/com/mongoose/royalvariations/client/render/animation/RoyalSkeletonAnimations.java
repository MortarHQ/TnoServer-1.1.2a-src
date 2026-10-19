package com.mongoose.royalvariations.client.render.animation;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.animation.AnimationChannel.Interpolations;
import net.minecraft.client.animation.AnimationChannel.Targets;
import net.minecraft.client.animation.AnimationDefinition.Builder;

public class RoyalSkeletonAnimations {
   public static final AnimationDefinition IDLE = Builder.withLength(3.0F)
      .looping()
      .addAnimation(
         "main",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(3.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "main",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(3.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "main",
         new AnimationChannel(
            Targets.SCALE,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR),
               new Keyframe(3.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "right_leg",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(-7.1547F, 12.2748F, 1.4938F), Interpolations.LINEAR),
               new Keyframe(3.0F, KeyframeAnimations.degreeVec(-7.1547F, 12.2748F, 1.4938F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "right_leg",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.0258F, -0.3234F, 0.1574F), Interpolations.LINEAR),
               new Keyframe(3.0F, KeyframeAnimations.posVec(0.0258F, -0.3234F, 0.1574F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "right_leg",
         new AnimationChannel(
            Targets.SCALE,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR),
               new Keyframe(3.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "left_leg",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(4.9988F, -0.1392F, -3.0097F), Interpolations.LINEAR),
               new Keyframe(3.0F, KeyframeAnimations.degreeVec(4.9988F, -0.1392F, -3.0097F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "left_leg",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(-0.0928F, -0.1952F, 0.4285F), Interpolations.LINEAR),
               new Keyframe(3.0F, KeyframeAnimations.posVec(-0.0928F, -0.1952F, 0.4285F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "left_leg",
         new AnimationChannel(
            Targets.SCALE,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR),
               new Keyframe(3.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "right_arm",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.8568F, -16.8431F, -2.3836F), Interpolations.LINEAR),
               new Keyframe(1.625F, KeyframeAnimations.degreeVec(0.5943F, -16.9155F, -1.3523F), Interpolations.CATMULLROM),
               new Keyframe(3.0F, KeyframeAnimations.degreeVec(0.8568F, -16.8431F, -2.3836F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "right_arm",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.0534F, -0.0562F, -0.8879F), Interpolations.LINEAR),
               new Keyframe(1.625F, KeyframeAnimations.posVec(0.05F, -0.8522F, -0.7787F), Interpolations.CATMULLROM),
               new Keyframe(3.0F, KeyframeAnimations.posVec(0.0534F, -0.0562F, -0.8879F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "right_arm",
         new AnimationChannel(
            Targets.SCALE,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR),
               new Keyframe(3.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "bow",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(3.9811F, -0.4352F, 4.9811F), Interpolations.LINEAR),
               new Keyframe(3.0F, KeyframeAnimations.degreeVec(3.9811F, -0.4352F, 4.9811F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "bow",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.1601F, 0.6935F, 0.8564F), Interpolations.LINEAR),
               new Keyframe(3.0F, KeyframeAnimations.posVec(0.1601F, 0.6935F, 0.8564F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "bow",
         new AnimationChannel(
            Targets.SCALE,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR),
               new Keyframe(3.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "arrow",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(3.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "arrow",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 1.4F), Interpolations.LINEAR),
               new Keyframe(3.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 1.4F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "arrow",
         new AnimationChannel(
            Targets.SCALE,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.scaleVec(0.0, 0.0, 0.0), Interpolations.LINEAR),
               new Keyframe(3.0F, KeyframeAnimations.scaleVec(0.0, 0.0, 0.0), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "bowstring",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(3.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "bowstring",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(3.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "bowstring",
         new AnimationChannel(
            Targets.SCALE,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR),
               new Keyframe(3.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "rib_1",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(3.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "rib_1",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(3.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "rib_1",
         new AnimationChannel(
            Targets.SCALE,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR),
               new Keyframe(3.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "rib_2",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(3.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "rib_2",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(3.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "rib_2",
         new AnimationChannel(
            Targets.SCALE,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR),
               new Keyframe(3.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "crown",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(3.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "crown",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(3.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "crown",
         new AnimationChannel(
            Targets.SCALE,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR),
               new Keyframe(3.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "body_main",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(3.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "body_main",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.5F, KeyframeAnimations.posVec(0.0F, -0.3F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(3.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "body_main",
         new AnimationChannel(
            Targets.SCALE,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR),
               new Keyframe(1.5F, KeyframeAnimations.scaleVec(1.01F, 1.0, 1.0), Interpolations.CATMULLROM),
               new Keyframe(3.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "body",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, -9.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(3.0F, KeyframeAnimations.degreeVec(0.0F, -9.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "body",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.5F, KeyframeAnimations.posVec(0.0F, -0.2971F, 0.0418F), Interpolations.CATMULLROM),
               new Keyframe(3.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "body",
         new AnimationChannel(
            Targets.SCALE,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR),
               new Keyframe(3.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "mainhead",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(3.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "mainhead",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(3.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "mainhead",
         new AnimationChannel(
            Targets.SCALE,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR),
               new Keyframe(3.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "head",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.25F, KeyframeAnimations.degreeVec(3.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(3.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "head",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(1.3333F, KeyframeAnimations.posVec(0.0083F, -0.5285F, -0.2461F), Interpolations.CATMULLROM),
               new Keyframe(3.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "head",
         new AnimationChannel(
            Targets.SCALE,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR),
               new Keyframe(3.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "left_arm",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 1.0F), Interpolations.LINEAR),
               new Keyframe(1.625F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -1.0F), Interpolations.CATMULLROM),
               new Keyframe(3.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 1.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "left_arm",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(-0.0297F, 0.118F, 0.8973F), Interpolations.LINEAR),
               new Keyframe(1.625F, KeyframeAnimations.posVec(-0.03F, -0.4742F, 0.9835F), Interpolations.CATMULLROM),
               new Keyframe(3.0F, KeyframeAnimations.posVec(-0.0297F, 0.118F, 0.8973F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "left_arm",
         new AnimationChannel(
            Targets.SCALE,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR),
               new Keyframe(3.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR)
            }
         )
      )
      .build();
   public static final AnimationDefinition CROWN = Builder.withLength(3.0F)
      .looping()
      .addAnimation(
         "crown",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(3.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "crown",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, 0.5F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(2.25F, KeyframeAnimations.posVec(0.0F, 0.5F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(3.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "crown",
         new AnimationChannel(
            Targets.SCALE,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR),
               new Keyframe(3.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR)
            }
         )
      )
      .build();
   public static final AnimationDefinition TO_IDLE = Builder.withLength(0.25F)
      .addAnimation(
         "right_leg",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(-7.1547F, 12.2748F, 1.4938F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "right_leg",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.posVec(0.0258F, -0.3234F, 0.1574F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "left_leg",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(4.9988F, -0.1392F, -3.0097F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "left_leg",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.posVec(-0.0928F, -0.1952F, 0.4285F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "right_arm",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(0.8568F, -16.8431F, -2.3836F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "right_arm",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.posVec(0.0534F, -0.0562F, -0.8879F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "bow",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(3.9811F, -0.4352F, 4.9811F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "bow",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.posVec(0.1601F, 0.6935F, 0.8564F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "arrow",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, 0.0F, 1.4F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "arrow",
         new AnimationChannel(
            Targets.SCALE,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.scaleVec(0.0, 0.0, 0.0), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.scaleVec(0.0, 0.0, 0.0), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "body",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(0.0F, -9.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "left_arm",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 1.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "left_arm",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
               new Keyframe(0.25F, KeyframeAnimations.posVec(-0.0297F, 0.118F, 0.8973F), Interpolations.LINEAR)
            }
         )
      )
      .build();
   public static final AnimationDefinition WALK = Builder.withLength(1.0F)
      .looping()
      .addAnimation(
         "main",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "main",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, -0.6F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, -0.6F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "main",
         new AnimationChannel(
            Targets.SCALE,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0, 1.01F, 1.0), Interpolations.CATMULLROM),
               new Keyframe(0.25F, KeyframeAnimations.scaleVec(1.01F, 1.0, 1.0), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.scaleVec(1.0, 1.01F, 1.0), Interpolations.CATMULLROM),
               new Keyframe(0.75F, KeyframeAnimations.scaleVec(1.01F, 1.0, 1.0), Interpolations.CATMULLROM),
               new Keyframe(1.0F, KeyframeAnimations.scaleVec(1.0, 1.01F, 1.0), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "right_leg",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(3.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(22.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(14.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.75F, KeyframeAnimations.degreeVec(-14.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.0F, KeyframeAnimations.degreeVec(3.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "right_leg",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -0.4F, 0.5F), Interpolations.CATMULLROM),
               new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, 1.3F, -0.1F), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.7F, -2.0F), Interpolations.CATMULLROM),
               new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, 0.3F, -1.3F), Interpolations.CATMULLROM),
               new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, -0.4F, 0.5F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "right_leg",
         new AnimationChannel(
            Targets.SCALE,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
               new Keyframe(1.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "left_leg",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(14.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(-14.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(3.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.75F, KeyframeAnimations.degreeVec(22.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.0F, KeyframeAnimations.degreeVec(14.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "left_leg",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.7F, -2.0F), Interpolations.CATMULLROM),
               new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, 0.2F, -1.3F), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, -0.3F, 0.5F), Interpolations.CATMULLROM),
               new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, 1.3F, -0.1F), Interpolations.CATMULLROM),
               new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.7F, -2.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "left_leg",
         new AnimationChannel(
            Targets.SCALE,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
               new Keyframe(1.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "right_arm",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(-6.0671F, -5.9545F, 1.0012F), Interpolations.CATMULLROM),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(-24.2587F, -3.0841F, 3.4377F), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(6.0898F, -0.8428F, 3.8958F), Interpolations.CATMULLROM),
               new Keyframe(0.75F, KeyframeAnimations.degreeVec(18.155F, -1.6879F, 8.8009F), Interpolations.CATMULLROM),
               new Keyframe(1.0F, KeyframeAnimations.degreeVec(-6.0671F, -5.9545F, 1.0012F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "right_arm",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0436F, 0.4981F), Interpolations.CATMULLROM),
               new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, -0.7748F, 1.0576F), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.2727F, -0.325F), Interpolations.CATMULLROM),
               new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, -0.4004F, 0.1179F), Interpolations.CATMULLROM),
               new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.0436F, 0.4981F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "right_arm",
         new AnimationChannel(
            Targets.SCALE,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
               new Keyframe(1.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "crown",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "crown",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "crown",
         new AnimationChannel(
            Targets.SCALE,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
               new Keyframe(1.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "bow",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(2.9915F, -3.3411F, 4.7791F), Interpolations.CATMULLROM),
               new Keyframe(0.2917F, KeyframeAnimations.degreeVec(-3.0085F, -3.3411F, 4.7791F), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(2.9915F, -3.3411F, 4.7791F), Interpolations.CATMULLROM),
               new Keyframe(0.7917F, KeyframeAnimations.degreeVec(4.9915F, -3.3411F, 4.7791F), Interpolations.CATMULLROM),
               new Keyframe(1.0F, KeyframeAnimations.degreeVec(2.9915F, -3.3411F, 4.7791F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "bow",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(-0.0409F, 0.6618F, 0.7486F), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.posVec(-0.0409F, 0.6618F, 0.7486F), Interpolations.CATMULLROM),
               new Keyframe(1.0F, KeyframeAnimations.posVec(-0.0409F, 0.6618F, 0.7486F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "bow",
         new AnimationChannel(
            Targets.SCALE,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
               new Keyframe(1.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "arrow",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "arrow",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(-0.0623F, 0.0565F, 1.0968F), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.posVec(0.0623F, 0.0565F, 1.0968F), Interpolations.CATMULLROM),
               new Keyframe(1.0F, KeyframeAnimations.posVec(-0.0623F, 0.0565F, 1.0968F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "arrow",
         new AnimationChannel(
            Targets.SCALE,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.scaleVec(0.0, 0.0, 0.0), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.scaleVec(0.0, 0.0, 0.0), Interpolations.CATMULLROM),
               new Keyframe(1.0F, KeyframeAnimations.scaleVec(0.0, 0.0, 0.0), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "bowstring",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "bowstring",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "bowstring",
         new AnimationChannel(
            Targets.SCALE,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
               new Keyframe(1.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "rib_1",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "rib_1",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "rib_1",
         new AnimationChannel(
            Targets.SCALE,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
               new Keyframe(1.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "rib_2",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "rib_2",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "rib_2",
         new AnimationChannel(
            Targets.SCALE,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
               new Keyframe(1.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "body_main",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(3.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(4.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(3.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.75F, KeyframeAnimations.degreeVec(4.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.0F, KeyframeAnimations.degreeVec(3.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "body_main",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.2F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.2917F, KeyframeAnimations.posVec(0.0F, -0.3F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.2F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.7917F, KeyframeAnimations.posVec(0.0F, -0.3F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.2F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "body_main",
         new AnimationChannel(
            Targets.SCALE,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
               new Keyframe(1.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "body",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 3.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(-1.9939F, 4.0012F, 0.07F), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, -3.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.75F, KeyframeAnimations.degreeVec(-1.9939F, -4.0012F, -0.07F), Interpolations.CATMULLROM),
               new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 3.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "body",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, -0.5985F, 0.0419F), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, -0.5985F, 0.0419F), Interpolations.CATMULLROM),
               new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "body",
         new AnimationChannel(
            Targets.SCALE,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
               new Keyframe(1.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "mainhead",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "mainhead",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "mainhead",
         new AnimationChannel(
            Targets.SCALE,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
               new Keyframe(1.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "head",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(-1.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(-1.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.75F, KeyframeAnimations.degreeVec(-1.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "head",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.2F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.2083F, KeyframeAnimations.posVec(0.0F, -0.7F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.2F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.7083F, KeyframeAnimations.posVec(0.0F, -0.7F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.2F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "head",
         new AnimationChannel(
            Targets.SCALE,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
               new Keyframe(1.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "left_arm",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(6.0898F, 0.8428F, -3.8958F), Interpolations.CATMULLROM),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(18.1286F, 0.9526F, -6.9408F), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(-6.4667F, 5.5177F, -4.9973F), Interpolations.CATMULLROM),
               new Keyframe(0.75F, KeyframeAnimations.degreeVec(-25.2304F, 0.8122F, -5.5881F), Interpolations.CATMULLROM),
               new Keyframe(1.0F, KeyframeAnimations.degreeVec(6.0898F, 0.8428F, -3.8958F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "left_arm",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.2727F, -0.325F), Interpolations.CATMULLROM),
               new Keyframe(0.2917F, KeyframeAnimations.posVec(0.0F, -0.4664F, -0.2672F), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.0436F, 0.4981F), Interpolations.CATMULLROM),
               new Keyframe(0.7917F, KeyframeAnimations.posVec(0.0F, -0.5237F, 1.0406F), Interpolations.CATMULLROM),
               new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.2727F, -0.325F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "left_arm",
         new AnimationChannel(
            Targets.SCALE,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
               new Keyframe(1.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM)
            }
         )
      )
      .build();
   public static final AnimationDefinition PREPARE = Builder.withLength(2.5F)
      .addAnimation(
         "right_arm",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(-96.4253F, 23.6933F, -10.427F), Interpolations.CATMULLROM),
               new Keyframe(0.0417F, KeyframeAnimations.degreeVec(-96.4253F, 23.6933F, -10.427F), Interpolations.CATMULLROM),
               new Keyframe(0.0833F, KeyframeAnimations.degreeVec(-87.6839F, 21.3945F, -5.5933F), Interpolations.CATMULLROM),
               new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-48.6108F, -13.268F, 16.9797F), Interpolations.CATMULLROM),
               new Keyframe(0.9167F, KeyframeAnimations.degreeVec(-41.2529F, -42.9415F, 15.0163F), Interpolations.CATMULLROM),
               new Keyframe(1.2917F, KeyframeAnimations.degreeVec(-76.7996F, 21.4244F, 15.1659F), Interpolations.CATMULLROM),
               new Keyframe(1.625F, KeyframeAnimations.degreeVec(-87.4253F, 23.6933F, -10.427F), Interpolations.CATMULLROM),
               new Keyframe(2.0833F, KeyframeAnimations.degreeVec(-96.4253F, 23.6933F, -10.427F), Interpolations.CATMULLROM),
               new Keyframe(2.2083F, KeyframeAnimations.degreeVec(-95.4253F, 23.6933F, -10.427F), Interpolations.CATMULLROM),
               new Keyframe(2.2917F, KeyframeAnimations.degreeVec(-96.4253F, 23.6933F, -10.427F), Interpolations.CATMULLROM),
               new Keyframe(2.4167F, KeyframeAnimations.degreeVec(-95.4253F, 23.6933F, -10.427F), Interpolations.CATMULLROM),
               new Keyframe(2.5F, KeyframeAnimations.degreeVec(-96.4253F, 23.6933F, -10.427F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "right_arm",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.3709F, -1.1079F, -1.0987F), Interpolations.CATMULLROM),
               new Keyframe(0.0417F, KeyframeAnimations.posVec(0.3709F, -1.1079F, -1.0987F), Interpolations.CATMULLROM),
               new Keyframe(0.3333F, KeyframeAnimations.posVec(0.0F, -0.8908F, -0.9997F), Interpolations.CATMULLROM),
               new Keyframe(0.9167F, KeyframeAnimations.posVec(0.0F, -0.6952F, 0.3864F), Interpolations.CATMULLROM),
               new Keyframe(1.625F, KeyframeAnimations.posVec(0.3709F, -1.1079F, -1.0987F), Interpolations.CATMULLROM),
               new Keyframe(2.0833F, KeyframeAnimations.posVec(0.3709F, -1.1079F, -1.0987F), Interpolations.CATMULLROM),
               new Keyframe(2.2917F, KeyframeAnimations.posVec(0.3709F, -1.1079F, -1.0987F), Interpolations.CATMULLROM),
               new Keyframe(2.5F, KeyframeAnimations.posVec(0.3709F, -1.1079F, -1.0987F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "arrow",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.3333F, KeyframeAnimations.degreeVec(60.415F, 42.0191F, 76.1227F), Interpolations.CATMULLROM),
               new Keyframe(0.4167F, KeyframeAnimations.degreeVec(48.0184F, 40.1141F, 83.3628F), Interpolations.CATMULLROM),
               new Keyframe(0.4583F, KeyframeAnimations.degreeVec(51.6219F, 38.209F, 90.6029F), Interpolations.CATMULLROM),
               new Keyframe(0.5417F, KeyframeAnimations.degreeVec(60.4039F, -29.6754F, -72.4965F), Interpolations.CATMULLROM),
               new Keyframe(0.625F, KeyframeAnimations.degreeVec(4.8192F, 10.2697F, 7.1351F), Interpolations.CATMULLROM),
               new Keyframe(0.7083F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "arrow",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.7F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.0417F, KeyframeAnimations.posVec(0.3736F, -10.4898F, 0.2402F), Interpolations.CATMULLROM),
               new Keyframe(0.3333F, KeyframeAnimations.posVec(7.17F, 12.11F, 6.34F), Interpolations.CATMULLROM),
               new Keyframe(0.4167F, KeyframeAnimations.posVec(11.38F, 14.96F, 2.5F), Interpolations.CATMULLROM),
               new Keyframe(0.4583F, KeyframeAnimations.posVec(11.98F, 13.81F, 0.06F), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.posVec(14.79F, 10.81F, -3.08F), Interpolations.CATMULLROM),
               new Keyframe(0.5417F, KeyframeAnimations.posVec(13.8F, 7.81F, -6.23F), Interpolations.CATMULLROM),
               new Keyframe(0.625F, KeyframeAnimations.posVec(9.44F, 3.36F, -8.51F), Interpolations.CATMULLROM),
               new Keyframe(0.7083F, KeyframeAnimations.posVec(2.88F, -0.59F, -6.99F), Interpolations.CATMULLROM),
               new Keyframe(0.9167F, KeyframeAnimations.posVec(0.0F, -0.79F, -0.7F), Interpolations.CATMULLROM),
               new Keyframe(1.625F, KeyframeAnimations.posVec(0.1465F, 2.097F, 0.0014F), Interpolations.CATMULLROM),
               new Keyframe(2.0833F, KeyframeAnimations.posVec(0.1465F, 2.097F, 0.0014F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "arrow",
         new AnimationChannel(
            Targets.SCALE,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.scaleVec(0.0, 0.0, 0.0), Interpolations.CATMULLROM),
               new Keyframe(0.0417F, KeyframeAnimations.scaleVec(0.0, 0.0, 0.0), Interpolations.CATMULLROM),
               new Keyframe(0.2917F, KeyframeAnimations.scaleVec(0.0, 0.0, 0.0), Interpolations.CATMULLROM),
               new Keyframe(0.3333F, KeyframeAnimations.scaleVec(0.36F, 0.49F, 0.44F), Interpolations.CATMULLROM),
               new Keyframe(0.4167F, KeyframeAnimations.scaleVec(0.66F, 0.725F, 0.59F), Interpolations.CATMULLROM),
               new Keyframe(0.4583F, KeyframeAnimations.scaleVec(0.96F, 0.96F, 0.96F), Interpolations.CATMULLROM),
               new Keyframe(0.5417F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
               new Keyframe(0.9167F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "rib_1",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(-23.9854F, -0.4284F, -0.4521F), Interpolations.CATMULLROM),
               new Keyframe(0.0833F, KeyframeAnimations.degreeVec(19.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.2917F, KeyframeAnimations.degreeVec(18.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.625F, KeyframeAnimations.degreeVec(-6.9854F, -0.4284F, -0.4521F), Interpolations.CATMULLROM),
               new Keyframe(2.0833F, KeyframeAnimations.degreeVec(-14.9854F, -0.4284F, -0.4521F), Interpolations.CATMULLROM),
               new Keyframe(2.2083F, KeyframeAnimations.degreeVec(-15.9854F, -0.4284F, -0.4521F), Interpolations.CATMULLROM),
               new Keyframe(2.2917F, KeyframeAnimations.degreeVec(-14.9854F, -0.4284F, -0.4521F), Interpolations.CATMULLROM),
               new Keyframe(2.4167F, KeyframeAnimations.degreeVec(-15.9854F, -0.4284F, -0.4521F), Interpolations.CATMULLROM),
               new Keyframe(2.5F, KeyframeAnimations.degreeVec(-14.9854F, -0.4284F, -0.4521F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "rib_2",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(19.2028F, 0.2144F, -0.2236F), Interpolations.CATMULLROM),
               new Keyframe(0.0833F, KeyframeAnimations.degreeVec(-18.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.2917F, KeyframeAnimations.degreeVec(-18.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.625F, KeyframeAnimations.degreeVec(4.2028F, 0.2144F, -0.2236F), Interpolations.CATMULLROM),
               new Keyframe(2.0833F, KeyframeAnimations.degreeVec(10.2028F, 0.2144F, -0.2236F), Interpolations.CATMULLROM),
               new Keyframe(2.2083F, KeyframeAnimations.degreeVec(11.2028F, 0.2144F, -0.2236F), Interpolations.CATMULLROM),
               new Keyframe(2.2917F, KeyframeAnimations.degreeVec(10.2028F, 0.2144F, -0.2236F), Interpolations.CATMULLROM),
               new Keyframe(2.4167F, KeyframeAnimations.degreeVec(11.2028F, 0.2144F, -0.2236F), Interpolations.CATMULLROM),
               new Keyframe(2.5F, KeyframeAnimations.degreeVec(10.2028F, 0.2144F, -0.2236F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "bowstring",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.049F, 2.0994F, 0.0979F), Interpolations.CATMULLROM),
               new Keyframe(0.0417F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.0833F, KeyframeAnimations.posVec(0.0F, -2.09F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.2917F, KeyframeAnimations.posVec(0.07F, -2.0F, 0.05F), Interpolations.CATMULLROM),
               new Keyframe(1.625F, KeyframeAnimations.posVec(0.0511F, 0.9996F, 0.1137F), Interpolations.CATMULLROM),
               new Keyframe(2.0833F, KeyframeAnimations.posVec(0.049F, 1.6994F, 0.0979F), Interpolations.CATMULLROM),
               new Keyframe(2.2917F, KeyframeAnimations.posVec(0.049F, 1.6994F, 0.0979F), Interpolations.CATMULLROM),
               new Keyframe(2.5F, KeyframeAnimations.posVec(0.049F, 1.6994F, 0.0979F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "bowstring",
         new AnimationChannel(
            Targets.SCALE,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 0.89F), Interpolations.CATMULLROM),
               new Keyframe(0.0417F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.1F), Interpolations.CATMULLROM),
               new Keyframe(0.0833F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.2088F), Interpolations.CATMULLROM),
               new Keyframe(0.25F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
               new Keyframe(1.2917F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.28F), Interpolations.CATMULLROM),
               new Keyframe(1.625F, KeyframeAnimations.scaleVec(1.0, 1.0, 0.99F), Interpolations.CATMULLROM),
               new Keyframe(2.0833F, KeyframeAnimations.scaleVec(1.0, 1.0, 0.89F), Interpolations.CATMULLROM),
               new Keyframe(2.2917F, KeyframeAnimations.scaleVec(1.0, 1.0, 0.89F), Interpolations.CATMULLROM),
               new Keyframe(2.5F, KeyframeAnimations.scaleVec(1.0, 1.0, 0.89F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "bow",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(4.4463F, -64.9068F, -4.1779F), Interpolations.CATMULLROM),
               new Keyframe(0.0417F, KeyframeAnimations.degreeVec(4.4463F, -64.9068F, -4.1779F), Interpolations.CATMULLROM),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(1.1679F, -0.8918F, 8.6303F), Interpolations.CATMULLROM),
               new Keyframe(1.2917F, KeyframeAnimations.degreeVec(4.4463F, -64.9068F, -4.1779F), Interpolations.CATMULLROM),
               new Keyframe(1.625F, KeyframeAnimations.degreeVec(4.4463F, -64.9068F, -4.1779F), Interpolations.CATMULLROM),
               new Keyframe(2.0833F, KeyframeAnimations.degreeVec(4.4463F, -64.9068F, -4.1779F), Interpolations.CATMULLROM),
               new Keyframe(2.2917F, KeyframeAnimations.degreeVec(4.4463F, -64.9068F, -4.1779F), Interpolations.CATMULLROM),
               new Keyframe(2.5F, KeyframeAnimations.degreeVec(4.4463F, -64.9068F, -4.1779F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "bow",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.9356F, -0.2896F, 0.8299F), Interpolations.CATMULLROM),
               new Keyframe(0.0417F, KeyframeAnimations.posVec(0.9356F, -0.2896F, 0.8299F), Interpolations.CATMULLROM),
               new Keyframe(0.25F, KeyframeAnimations.posVec(-0.02F, 0.51F, 0.78F), Interpolations.CATMULLROM),
               new Keyframe(1.2917F, KeyframeAnimations.posVec(0.3356F, -0.2896F, 0.2299F), Interpolations.CATMULLROM),
               new Keyframe(1.625F, KeyframeAnimations.posVec(0.9356F, -0.2896F, 0.8299F), Interpolations.CATMULLROM),
               new Keyframe(2.0833F, KeyframeAnimations.posVec(0.9356F, -0.2896F, 0.8299F), Interpolations.CATMULLROM),
               new Keyframe(2.2917F, KeyframeAnimations.posVec(0.9356F, -0.2896F, 0.8299F), Interpolations.CATMULLROM),
               new Keyframe(2.5F, KeyframeAnimations.posVec(0.9356F, -0.2896F, 0.8299F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "bow",
         new AnimationChannel(
            Targets.SCALE,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0, 1.09F, 1.0), Interpolations.LINEAR),
               new Keyframe(0.0417F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR),
               new Keyframe(0.0833F, KeyframeAnimations.scaleVec(1.0, 0.84F, 1.09F), Interpolations.LINEAR),
               new Keyframe(0.1667F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "body_main",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, -40.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.0417F, KeyframeAnimations.degreeVec(-3.9137F, -39.9341F, 2.5145F), Interpolations.CATMULLROM),
               new Keyframe(0.0833F, KeyframeAnimations.degreeVec(-7.8058F, -39.7375F, 5.0083F), Interpolations.CATMULLROM),
               new Keyframe(0.1667F, KeyframeAnimations.degreeVec(0.0F, -40.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.625F, KeyframeAnimations.degreeVec(0.0F, -40.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(2.0833F, KeyframeAnimations.degreeVec(0.0F, -40.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(2.2917F, KeyframeAnimations.degreeVec(0.0F, -40.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(2.5F, KeyframeAnimations.degreeVec(0.0F, -40.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "body",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0302F, 4.9512F, 0.6976F), Interpolations.CATMULLROM),
               new Keyframe(0.0417F, KeyframeAnimations.degreeVec(0.0308F, 12.9512F, 0.7019F), Interpolations.CATMULLROM),
               new Keyframe(0.3333F, KeyframeAnimations.degreeVec(0.4201F, -5.9853F, -4.022F), Interpolations.CATMULLROM),
               new Keyframe(1.2917F, KeyframeAnimations.degreeVec(0.0108F, 2.9708F, 0.4179F), Interpolations.CATMULLROM),
               new Keyframe(1.625F, KeyframeAnimations.degreeVec(0.0302F, 4.9512F, 0.6976F), Interpolations.CATMULLROM),
               new Keyframe(2.0833F, KeyframeAnimations.degreeVec(0.0302F, 4.9512F, 0.6976F), Interpolations.CATMULLROM),
               new Keyframe(2.25F, KeyframeAnimations.degreeVec(0.0301F, 3.9512F, 0.6971F), Interpolations.CATMULLROM),
               new Keyframe(2.4167F, KeyframeAnimations.degreeVec(0.0302F, 4.9512F, 0.6976F), Interpolations.CATMULLROM),
               new Keyframe(2.5F, KeyframeAnimations.degreeVec(0.0301F, 3.9512F, 0.6971F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "body",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(-0.1286F, -0.0213F, -0.1517F), Interpolations.CATMULLROM),
               new Keyframe(0.0417F, KeyframeAnimations.posVec(-0.1286F, -0.0213F, -0.1517F), Interpolations.CATMULLROM),
               new Keyframe(1.625F, KeyframeAnimations.posVec(-0.1286F, -0.0213F, -0.1517F), Interpolations.CATMULLROM),
               new Keyframe(2.0833F, KeyframeAnimations.posVec(-0.1286F, -0.0213F, -0.1517F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "head",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 40.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.0417F, KeyframeAnimations.degreeVec(-4.0F, 40.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(0.0F, 40.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.625F, KeyframeAnimations.degreeVec(8.4386F, 13.4826F, -2.0127F), Interpolations.CATMULLROM),
               new Keyframe(1.1667F, KeyframeAnimations.degreeVec(0.0F, 40.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.625F, KeyframeAnimations.degreeVec(0.0F, 40.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(2.0833F, KeyframeAnimations.degreeVec(0.0F, 40.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(2.2917F, KeyframeAnimations.degreeVec(0.0F, 40.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(2.5F, KeyframeAnimations.degreeVec(0.0F, 40.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "left_arm",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(-138.8743F, 71.4908F, -47.6439F), Interpolations.CATMULLROM),
               new Keyframe(0.0417F, KeyframeAnimations.degreeVec(-119.4666F, 31.9095F, -36.6325F), Interpolations.CATMULLROM),
               new Keyframe(0.0833F, KeyframeAnimations.degreeVec(-111.0714F, 15.293F, -28.073F), Interpolations.CATMULLROM),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(7.4947F, -4.05F, -8.5374F), Interpolations.CATMULLROM),
               new Keyframe(0.3333F, KeyframeAnimations.degreeVec(36.9038F, -18.6333F, -6.6683F), Interpolations.CATMULLROM),
               new Keyframe(0.4167F, KeyframeAnimations.degreeVec(27.6197F, -8.1539F, -22.7485F), Interpolations.CATMULLROM),
               new Keyframe(0.4583F, KeyframeAnimations.degreeVec(19.687F, 0.4093F, -35.258F), Interpolations.CATMULLROM),
               new Keyframe(0.5417F, KeyframeAnimations.degreeVec(-10.6225F, 14.949F, -38.2557F), Interpolations.CATMULLROM),
               new Keyframe(0.625F, KeyframeAnimations.degreeVec(-23.9271F, 34.7699F, -29.8561F), Interpolations.CATMULLROM),
               new Keyframe(0.75F, KeyframeAnimations.degreeVec(-76.29F, 62.4857F, -54.0087F), Interpolations.CATMULLROM),
               new Keyframe(0.9167F, KeyframeAnimations.degreeVec(-84.1646F, 83.1882F, -32.5051F), Interpolations.CATMULLROM),
               new Keyframe(1.2917F, KeyframeAnimations.degreeVec(-147.3965F, 65.2197F, -61.9833F), Interpolations.CATMULLROM),
               new Keyframe(1.625F, KeyframeAnimations.degreeVec(-128.3212F, 70.2012F, -42.574F), Interpolations.CATMULLROM),
               new Keyframe(2.0833F, KeyframeAnimations.degreeVec(-129.6528F, 81.0972F, -37.2025F), Interpolations.CATMULLROM),
               new Keyframe(2.2083F, KeyframeAnimations.degreeVec(-128.6528F, 81.0972F, -37.2025F), Interpolations.CATMULLROM),
               new Keyframe(2.2917F, KeyframeAnimations.degreeVec(-129.6528F, 81.0972F, -37.2025F), Interpolations.CATMULLROM),
               new Keyframe(2.4167F, KeyframeAnimations.degreeVec(-128.6528F, 81.0972F, -37.2025F), Interpolations.CATMULLROM),
               new Keyframe(2.5F, KeyframeAnimations.degreeVec(-129.6528F, 81.0972F, -37.2025F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "left_arm",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.2551F, -0.1645F, -1.2346F), Interpolations.CATMULLROM),
               new Keyframe(0.0417F, KeyframeAnimations.posVec(-0.0168F, -0.0645F, -1.5805F), Interpolations.CATMULLROM),
               new Keyframe(0.0833F, KeyframeAnimations.posVec(-0.06F, 0.19F, -1.44F), Interpolations.CATMULLROM),
               new Keyframe(0.25F, KeyframeAnimations.posVec(-0.31F, 0.49F, -0.7F), Interpolations.CATMULLROM),
               new Keyframe(0.3333F, KeyframeAnimations.posVec(-0.5F, 0.7999F, -1.0607F), Interpolations.CATMULLROM),
               new Keyframe(0.4167F, KeyframeAnimations.posVec(-1.0F, 0.79F, 0.34F), Interpolations.CATMULLROM),
               new Keyframe(0.4583F, KeyframeAnimations.posVec(-1.1F, 0.48F, 0.64F), Interpolations.CATMULLROM),
               new Keyframe(0.5417F, KeyframeAnimations.posVec(-0.5F, 0.1646F, 0.0355F), Interpolations.CATMULLROM),
               new Keyframe(0.625F, KeyframeAnimations.posVec(-0.32F, -0.23F, -0.27F), Interpolations.CATMULLROM),
               new Keyframe(0.9167F, KeyframeAnimations.posVec(0.0F, 0.2816F, -1.6538F), Interpolations.CATMULLROM),
               new Keyframe(1.2917F, KeyframeAnimations.posVec(-2.5F, 0.18F, -2.95F), Interpolations.CATMULLROM),
               new Keyframe(1.625F, KeyframeAnimations.posVec(-0.981F, -0.2245F, -1.8184F), Interpolations.CATMULLROM),
               new Keyframe(2.0833F, KeyframeAnimations.posVec(-0.0168F, -0.2645F, -1.5805F), Interpolations.CATMULLROM),
               new Keyframe(2.2083F, KeyframeAnimations.posVec(0.0943F, -0.27F, -1.4934F), Interpolations.CATMULLROM),
               new Keyframe(2.2917F, KeyframeAnimations.posVec(-0.0168F, -0.2645F, -1.5805F), Interpolations.CATMULLROM),
               new Keyframe(2.4167F, KeyframeAnimations.posVec(0.0943F, -0.27F, -1.4934F), Interpolations.CATMULLROM),
               new Keyframe(2.5F, KeyframeAnimations.posVec(-0.0168F, -0.2645F, -1.5805F), Interpolations.CATMULLROM)
            }
         )
      )
      .build();
   public static final AnimationDefinition AGGRESSION = Builder.withLength(0.5F)
      .looping()
      .addAnimation(
         "right_arm",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(-96.4253F, 23.6933F, -10.427F), Interpolations.CATMULLROM),
               new Keyframe(0.0833F, KeyframeAnimations.degreeVec(-97.4253F, 23.6933F, -10.427F), Interpolations.CATMULLROM),
               new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-96.4253F, 23.6933F, -10.427F), Interpolations.CATMULLROM),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(-97.4253F, 23.6933F, -10.427F), Interpolations.CATMULLROM),
               new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-96.4253F, 23.6933F, -10.427F), Interpolations.CATMULLROM),
               new Keyframe(0.4167F, KeyframeAnimations.degreeVec(-97.4253F, 23.6933F, -10.427F), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(-96.4253F, 23.6933F, -10.427F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "right_arm",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.3709F, -1.1079F, -1.0987F), Interpolations.CATMULLROM),
               new Keyframe(0.1667F, KeyframeAnimations.posVec(0.3709F, -1.1079F, -1.0987F), Interpolations.CATMULLROM),
               new Keyframe(0.3333F, KeyframeAnimations.posVec(0.3709F, -1.1079F, -1.0987F), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.posVec(0.3709F, -1.1079F, -1.0987F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "bow",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(4.4463F, -64.9068F, -4.1779F), Interpolations.CATMULLROM),
               new Keyframe(0.0833F, KeyframeAnimations.degreeVec(4.4463F, -64.9068F, -4.1779F), Interpolations.CATMULLROM),
               new Keyframe(0.1667F, KeyframeAnimations.degreeVec(4.4463F, -64.9068F, -4.1779F), Interpolations.CATMULLROM),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(4.4463F, -64.9068F, -4.1779F), Interpolations.CATMULLROM),
               new Keyframe(0.3333F, KeyframeAnimations.degreeVec(4.4463F, -64.9068F, -4.1779F), Interpolations.CATMULLROM),
               new Keyframe(0.4167F, KeyframeAnimations.degreeVec(4.4463F, -64.9068F, -4.1779F), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(4.4463F, -64.9068F, -4.1779F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "bow",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.9356F, -0.2896F, 0.8299F), Interpolations.CATMULLROM),
               new Keyframe(0.0833F, KeyframeAnimations.posVec(0.9356F, -0.2896F, 0.8299F), Interpolations.CATMULLROM),
               new Keyframe(0.1667F, KeyframeAnimations.posVec(0.9356F, -0.2896F, 0.8299F), Interpolations.CATMULLROM),
               new Keyframe(0.25F, KeyframeAnimations.posVec(0.9356F, -0.2896F, 0.8299F), Interpolations.CATMULLROM),
               new Keyframe(0.3333F, KeyframeAnimations.posVec(0.9356F, -0.2896F, 0.8299F), Interpolations.CATMULLROM),
               new Keyframe(0.4167F, KeyframeAnimations.posVec(0.9356F, -0.2896F, 0.8299F), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.posVec(0.9356F, -0.2896F, 0.8299F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "arrow",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.1465F, 2.097F, 0.0014F), Interpolations.CATMULLROM),
               new Keyframe(0.0833F, KeyframeAnimations.posVec(0.1413F, 2.1968F, -8.0E-4F), Interpolations.CATMULLROM),
               new Keyframe(0.1667F, KeyframeAnimations.posVec(0.1465F, 2.097F, 0.0014F), Interpolations.CATMULLROM),
               new Keyframe(0.25F, KeyframeAnimations.posVec(0.1413F, 2.1968F, -8.0E-4F), Interpolations.CATMULLROM),
               new Keyframe(0.3333F, KeyframeAnimations.posVec(0.1465F, 2.097F, 0.0014F), Interpolations.CATMULLROM),
               new Keyframe(0.4167F, KeyframeAnimations.posVec(0.1413F, 2.1968F, -8.0E-4F), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.posVec(0.1465F, 2.097F, 0.0014F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "bowstring",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.049F, 1.6994F, 0.0979F), Interpolations.CATMULLROM),
               new Keyframe(0.0833F, KeyframeAnimations.posVec(0.0416F, 1.8992F, 0.0951F), Interpolations.CATMULLROM),
               new Keyframe(0.1667F, KeyframeAnimations.posVec(0.049F, 1.6994F, 0.0979F), Interpolations.CATMULLROM),
               new Keyframe(0.25F, KeyframeAnimations.posVec(0.0416F, 1.8992F, 0.0951F), Interpolations.CATMULLROM),
               new Keyframe(0.3333F, KeyframeAnimations.posVec(0.049F, 1.6994F, 0.0979F), Interpolations.CATMULLROM),
               new Keyframe(0.4167F, KeyframeAnimations.posVec(0.0416F, 1.8992F, 0.0951F), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.posVec(0.049F, 1.6994F, 0.0979F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "bowstring",
         new AnimationChannel(
            Targets.SCALE,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 0.89F), Interpolations.CATMULLROM),
               new Keyframe(0.0833F, KeyframeAnimations.scaleVec(1.0, 1.0, 0.89F), Interpolations.CATMULLROM),
               new Keyframe(0.1667F, KeyframeAnimations.scaleVec(1.0, 1.0, 0.89F), Interpolations.CATMULLROM),
               new Keyframe(0.25F, KeyframeAnimations.scaleVec(1.0, 1.0, 0.89F), Interpolations.CATMULLROM),
               new Keyframe(0.3333F, KeyframeAnimations.scaleVec(1.0, 1.0, 0.89F), Interpolations.CATMULLROM),
               new Keyframe(0.4167F, KeyframeAnimations.scaleVec(1.0, 1.0, 0.89F), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.scaleVec(1.0, 1.0, 0.89F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "rib_1",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(-14.9854F, -0.4284F, -0.4521F), Interpolations.CATMULLROM),
               new Keyframe(0.0833F, KeyframeAnimations.degreeVec(-15.9854F, -0.4284F, -0.4521F), Interpolations.CATMULLROM),
               new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-14.9854F, -0.4284F, -0.4521F), Interpolations.CATMULLROM),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(-15.9854F, -0.4284F, -0.4521F), Interpolations.CATMULLROM),
               new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-14.9854F, -0.4284F, -0.4521F), Interpolations.CATMULLROM),
               new Keyframe(0.4167F, KeyframeAnimations.degreeVec(-15.9854F, -0.4284F, -0.4521F), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(-14.9854F, -0.4284F, -0.4521F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "rib_2",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(10.2028F, 0.2144F, -0.2236F), Interpolations.CATMULLROM),
               new Keyframe(0.0833F, KeyframeAnimations.degreeVec(11.2028F, 0.2144F, -0.2236F), Interpolations.CATMULLROM),
               new Keyframe(0.1667F, KeyframeAnimations.degreeVec(10.2028F, 0.2144F, -0.2236F), Interpolations.CATMULLROM),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(11.2028F, 0.2144F, -0.2236F), Interpolations.CATMULLROM),
               new Keyframe(0.3333F, KeyframeAnimations.degreeVec(10.2028F, 0.2144F, -0.2236F), Interpolations.CATMULLROM),
               new Keyframe(0.4167F, KeyframeAnimations.degreeVec(11.2028F, 0.2144F, -0.2236F), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(10.2028F, 0.2144F, -0.2236F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "body_main",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, -40.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.0833F, KeyframeAnimations.degreeVec(0.0F, -40.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.1667F, KeyframeAnimations.degreeVec(0.0F, -40.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(0.0F, -40.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.3333F, KeyframeAnimations.degreeVec(0.0F, -40.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.4167F, KeyframeAnimations.degreeVec(0.0F, -40.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, -40.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "body",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0301F, 3.9512F, 0.6971F), Interpolations.CATMULLROM),
               new Keyframe(0.125F, KeyframeAnimations.degreeVec(0.0301F, 2.9512F, 0.6965F), Interpolations.CATMULLROM),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(0.0301F, 3.9512F, 0.6971F), Interpolations.CATMULLROM),
               new Keyframe(0.375F, KeyframeAnimations.degreeVec(0.0301F, 2.9512F, 0.6965F), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0301F, 3.9512F, 0.6971F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "body",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(-0.1286F, -0.0213F, -0.1517F), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.posVec(-0.1286F, -0.0213F, -0.1517F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "head",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 40.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.0833F, KeyframeAnimations.degreeVec(0.0F, 40.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.1667F, KeyframeAnimations.degreeVec(0.0F, 40.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(0.0F, 40.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.3333F, KeyframeAnimations.degreeVec(0.0F, 40.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.4167F, KeyframeAnimations.degreeVec(0.0F, 40.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 40.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "left_arm",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(-129.6528F, 81.0972F, -37.2025F), Interpolations.CATMULLROM),
               new Keyframe(0.0833F, KeyframeAnimations.degreeVec(-128.6528F, 81.0972F, -37.2025F), Interpolations.CATMULLROM),
               new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-129.6528F, 81.0972F, -37.2025F), Interpolations.CATMULLROM),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(-128.6528F, 81.0972F, -37.2025F), Interpolations.CATMULLROM),
               new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-129.6528F, 81.0972F, -37.2025F), Interpolations.CATMULLROM),
               new Keyframe(0.4167F, KeyframeAnimations.degreeVec(-128.6528F, 81.0972F, -37.2025F), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(-129.6528F, 81.0972F, -37.2025F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "left_arm",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(-0.0168F, -0.2645F, -1.5805F), Interpolations.CATMULLROM),
               new Keyframe(0.0833F, KeyframeAnimations.posVec(0.0475F, -0.2645F, -1.5039F), Interpolations.CATMULLROM),
               new Keyframe(0.1667F, KeyframeAnimations.posVec(-0.0168F, -0.2645F, -1.5805F), Interpolations.CATMULLROM),
               new Keyframe(0.25F, KeyframeAnimations.posVec(0.0475F, -0.2645F, -1.5039F), Interpolations.CATMULLROM),
               new Keyframe(0.3333F, KeyframeAnimations.posVec(-0.0168F, -0.2645F, -1.5805F), Interpolations.CATMULLROM),
               new Keyframe(0.4167F, KeyframeAnimations.posVec(0.0475F, -0.2645F, -1.5039F), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.posVec(-0.0168F, -0.2645F, -1.5805F), Interpolations.CATMULLROM)
            }
         )
      )
      .build();
   public static final AnimationDefinition STAND_DOWN = Builder.withLength(2.0F)
      .addAnimation(
         "right_arm",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(-96.4253F, 23.6933F, -10.427F), Interpolations.CATMULLROM),
               new Keyframe(0.4583F, KeyframeAnimations.degreeVec(-87.4253F, 23.6933F, -10.427F), Interpolations.CATMULLROM),
               new Keyframe(0.7917F, KeyframeAnimations.degreeVec(-76.7996F, 21.4244F, 15.1659F), Interpolations.CATMULLROM),
               new Keyframe(1.1667F, KeyframeAnimations.degreeVec(-41.2529F, -42.9415F, 15.0163F), Interpolations.CATMULLROM),
               new Keyframe(1.75F, KeyframeAnimations.degreeVec(-48.6108F, -13.268F, 16.9797F), Interpolations.CATMULLROM),
               new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.8568F, -16.8431F, -2.3836F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "right_arm",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.3709F, -1.1079F, -1.0987F), Interpolations.CATMULLROM),
               new Keyframe(0.4583F, KeyframeAnimations.posVec(0.3709F, -1.1079F, -1.0987F), Interpolations.CATMULLROM),
               new Keyframe(1.1667F, KeyframeAnimations.posVec(0.0F, -0.6952F, 0.3864F), Interpolations.CATMULLROM),
               new Keyframe(1.75F, KeyframeAnimations.posVec(0.0F, -0.8908F, -0.9997F), Interpolations.CATMULLROM),
               new Keyframe(2.0F, KeyframeAnimations.posVec(0.0534F, -0.0562F, -0.8879F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "right_arm",
         new AnimationChannel(Targets.SCALE, new Keyframe[]{new Keyframe(2.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR)})
      )
      .addAnimation(
         "arrow",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.375F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.4583F, KeyframeAnimations.degreeVec(4.8192F, 10.2697F, 7.1351F), Interpolations.CATMULLROM),
               new Keyframe(1.5417F, KeyframeAnimations.degreeVec(60.4039F, -29.6754F, -72.4965F), Interpolations.CATMULLROM),
               new Keyframe(1.625F, KeyframeAnimations.degreeVec(51.6219F, 38.209F, 90.6029F), Interpolations.CATMULLROM),
               new Keyframe(1.6667F, KeyframeAnimations.degreeVec(48.0184F, 40.1141F, 83.3628F), Interpolations.CATMULLROM),
               new Keyframe(1.75F, KeyframeAnimations.degreeVec(60.415F, 42.0191F, 76.1227F), Interpolations.CATMULLROM),
               new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "arrow",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.1465F, 2.097F, 0.0014F), Interpolations.CATMULLROM),
               new Keyframe(0.4583F, KeyframeAnimations.posVec(0.1465F, 2.097F, 0.0014F), Interpolations.CATMULLROM),
               new Keyframe(1.1667F, KeyframeAnimations.posVec(0.0F, -0.79F, -0.7F), Interpolations.CATMULLROM),
               new Keyframe(1.375F, KeyframeAnimations.posVec(2.88F, -0.59F, -6.99F), Interpolations.CATMULLROM),
               new Keyframe(1.4583F, KeyframeAnimations.posVec(9.44F, 3.36F, -8.51F), Interpolations.CATMULLROM),
               new Keyframe(1.5417F, KeyframeAnimations.posVec(13.8F, 7.81F, -6.23F), Interpolations.CATMULLROM),
               new Keyframe(1.5833F, KeyframeAnimations.posVec(14.79F, 10.81F, -3.08F), Interpolations.CATMULLROM),
               new Keyframe(1.625F, KeyframeAnimations.posVec(11.98F, 13.81F, 0.06F), Interpolations.CATMULLROM),
               new Keyframe(1.6667F, KeyframeAnimations.posVec(11.38F, 14.96F, 2.5F), Interpolations.CATMULLROM),
               new Keyframe(1.75F, KeyframeAnimations.posVec(7.17F, 12.11F, 6.34F), Interpolations.CATMULLROM),
               new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 1.4F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "arrow",
         new AnimationChannel(
            Targets.SCALE,
            new Keyframe[]{
               new Keyframe(1.1667F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
               new Keyframe(1.5417F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
               new Keyframe(1.625F, KeyframeAnimations.scaleVec(0.96F, 0.96F, 0.96F), Interpolations.CATMULLROM),
               new Keyframe(1.6667F, KeyframeAnimations.scaleVec(0.66F, 0.725F, 0.59F), Interpolations.CATMULLROM),
               new Keyframe(1.75F, KeyframeAnimations.scaleVec(0.36F, 0.49F, 0.44F), Interpolations.CATMULLROM),
               new Keyframe(1.7917F, KeyframeAnimations.scaleVec(0.0, 0.0, 0.0), Interpolations.CATMULLROM),
               new Keyframe(2.0F, KeyframeAnimations.scaleVec(0.0, 0.0, 0.0), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "rib_1",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(-14.9854F, -0.4284F, -0.4521F), Interpolations.CATMULLROM),
               new Keyframe(0.4583F, KeyframeAnimations.degreeVec(-6.9854F, -0.4284F, -0.4521F), Interpolations.CATMULLROM),
               new Keyframe(0.7917F, KeyframeAnimations.degreeVec(18.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.8333F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "rib_1",
         new AnimationChannel(Targets.POSITION, new Keyframe[]{new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)})
      )
      .addAnimation(
         "rib_1", new AnimationChannel(Targets.SCALE, new Keyframe[]{new Keyframe(2.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR)})
      )
      .addAnimation(
         "rib_2",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(10.2028F, 0.2144F, -0.2236F), Interpolations.CATMULLROM),
               new Keyframe(0.4583F, KeyframeAnimations.degreeVec(4.2028F, 0.2144F, -0.2236F), Interpolations.CATMULLROM),
               new Keyframe(0.7917F, KeyframeAnimations.degreeVec(-18.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.8333F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "rib_2",
         new AnimationChannel(Targets.POSITION, new Keyframe[]{new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)})
      )
      .addAnimation(
         "rib_2", new AnimationChannel(Targets.SCALE, new Keyframe[]{new Keyframe(2.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR)})
      )
      .addAnimation(
         "bowstring",
         new AnimationChannel(Targets.ROTATION, new Keyframe[]{new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)})
      )
      .addAnimation(
         "bowstring",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.049F, 1.6994F, 0.0979F), Interpolations.CATMULLROM),
               new Keyframe(0.4583F, KeyframeAnimations.posVec(0.0511F, 0.9996F, 0.1137F), Interpolations.CATMULLROM),
               new Keyframe(0.7917F, KeyframeAnimations.posVec(0.07F, -2.0F, 0.05F), Interpolations.CATMULLROM),
               new Keyframe(1.8333F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "bowstring",
         new AnimationChannel(
            Targets.SCALE,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 0.89F), Interpolations.CATMULLROM),
               new Keyframe(0.4583F, KeyframeAnimations.scaleVec(1.0, 1.0, 0.99F), Interpolations.CATMULLROM),
               new Keyframe(0.7917F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.28F), Interpolations.CATMULLROM),
               new Keyframe(1.8333F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
               new Keyframe(2.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "bow",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(4.4463F, -64.9068F, -4.1779F), Interpolations.CATMULLROM),
               new Keyframe(0.4583F, KeyframeAnimations.degreeVec(4.4463F, -64.9068F, -4.1779F), Interpolations.CATMULLROM),
               new Keyframe(0.7917F, KeyframeAnimations.degreeVec(4.4463F, -64.9068F, -4.1779F), Interpolations.CATMULLROM),
               new Keyframe(1.8333F, KeyframeAnimations.degreeVec(1.1679F, -0.8918F, 8.6303F), Interpolations.CATMULLROM),
               new Keyframe(2.0F, KeyframeAnimations.degreeVec(3.9811F, -0.4352F, 4.9811F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "bow",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.9356F, -0.2896F, 0.8299F), Interpolations.CATMULLROM),
               new Keyframe(0.4583F, KeyframeAnimations.posVec(0.9356F, -0.2896F, 0.8299F), Interpolations.CATMULLROM),
               new Keyframe(0.7917F, KeyframeAnimations.posVec(0.3356F, -0.2896F, 0.2299F), Interpolations.CATMULLROM),
               new Keyframe(1.8333F, KeyframeAnimations.posVec(-0.02F, 0.51F, 0.78F), Interpolations.CATMULLROM),
               new Keyframe(2.0F, KeyframeAnimations.posVec(0.1601F, 0.6935F, 0.8564F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "bow",
         new AnimationChannel(
            Targets.SCALE,
            new Keyframe[]{
               new Keyframe(1.9167F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR),
               new Keyframe(2.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "main",
         new AnimationChannel(Targets.ROTATION, new Keyframe[]{new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)})
      )
      .addAnimation(
         "main", new AnimationChannel(Targets.POSITION, new Keyframe[]{new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)})
      )
      .addAnimation(
         "main", new AnimationChannel(Targets.SCALE, new Keyframe[]{new Keyframe(2.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR)})
      )
      .addAnimation(
         "right_leg",
         new AnimationChannel(
            Targets.ROTATION, new Keyframe[]{new Keyframe(2.0F, KeyframeAnimations.degreeVec(-7.1547F, 12.2748F, 1.4938F), Interpolations.LINEAR)}
         )
      )
      .addAnimation(
         "right_leg",
         new AnimationChannel(
            Targets.POSITION, new Keyframe[]{new Keyframe(2.0F, KeyframeAnimations.posVec(0.0258F, -0.3234F, 0.1574F), Interpolations.LINEAR)}
         )
      )
      .addAnimation(
         "right_leg",
         new AnimationChannel(Targets.SCALE, new Keyframe[]{new Keyframe(2.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR)})
      )
      .addAnimation(
         "left_leg",
         new AnimationChannel(
            Targets.ROTATION, new Keyframe[]{new Keyframe(2.0F, KeyframeAnimations.degreeVec(4.9988F, -0.1392F, -3.0097F), Interpolations.LINEAR)}
         )
      )
      .addAnimation(
         "left_leg",
         new AnimationChannel(
            Targets.POSITION, new Keyframe[]{new Keyframe(2.0F, KeyframeAnimations.posVec(-0.0928F, -0.1952F, 0.4285F), Interpolations.LINEAR)}
         )
      )
      .addAnimation(
         "left_leg", new AnimationChannel(Targets.SCALE, new Keyframe[]{new Keyframe(2.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR)})
      )
      .addAnimation(
         "crown",
         new AnimationChannel(Targets.ROTATION, new Keyframe[]{new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)})
      )
      .addAnimation(
         "crown",
         new AnimationChannel(Targets.POSITION, new Keyframe[]{new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)})
      )
      .addAnimation(
         "crown", new AnimationChannel(Targets.SCALE, new Keyframe[]{new Keyframe(2.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR)})
      )
      .addAnimation(
         "body_main",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, -40.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.4583F, KeyframeAnimations.degreeVec(0.0F, -40.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.75F, KeyframeAnimations.degreeVec(0.0F, -40.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "body_main",
         new AnimationChannel(Targets.POSITION, new Keyframe[]{new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)})
      )
      .addAnimation(
         "body_main",
         new AnimationChannel(Targets.SCALE, new Keyframe[]{new Keyframe(2.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR)})
      )
      .addAnimation(
         "body",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0301F, 3.9512F, 0.6971F), Interpolations.CATMULLROM),
               new Keyframe(0.4583F, KeyframeAnimations.degreeVec(0.0302F, 4.9512F, 0.6976F), Interpolations.CATMULLROM),
               new Keyframe(0.7917F, KeyframeAnimations.degreeVec(0.0108F, 2.9708F, 0.4179F), Interpolations.CATMULLROM),
               new Keyframe(1.75F, KeyframeAnimations.degreeVec(0.4201F, -5.9853F, -4.022F), Interpolations.CATMULLROM),
               new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, -9.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "body",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(-0.1286F, -0.0213F, -0.1517F), Interpolations.CATMULLROM),
               new Keyframe(0.4583F, KeyframeAnimations.posVec(-0.1286F, -0.0213F, -0.1517F), Interpolations.CATMULLROM),
               new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "body", new AnimationChannel(Targets.SCALE, new Keyframe[]{new Keyframe(2.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR)})
      )
      .addAnimation(
         "mainhead",
         new AnimationChannel(Targets.ROTATION, new Keyframe[]{new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)})
      )
      .addAnimation(
         "mainhead",
         new AnimationChannel(Targets.POSITION, new Keyframe[]{new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)})
      )
      .addAnimation(
         "mainhead", new AnimationChannel(Targets.SCALE, new Keyframe[]{new Keyframe(2.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR)})
      )
      .addAnimation(
         "head",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 40.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.4583F, KeyframeAnimations.degreeVec(3.3365F, 39.5642F, 5.1747F), Interpolations.CATMULLROM),
               new Keyframe(1.0833F, KeyframeAnimations.degreeVec(3.3365F, 39.5642F, 5.1747F), Interpolations.CATMULLROM),
               new Keyframe(1.5833F, KeyframeAnimations.degreeVec(9.8083F, 12.5303F, 4.0675F), Interpolations.CATMULLROM),
               new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "head", new AnimationChannel(Targets.POSITION, new Keyframe[]{new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)})
      )
      .addAnimation(
         "head", new AnimationChannel(Targets.SCALE, new Keyframe[]{new Keyframe(2.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR)})
      )
      .addAnimation(
         "left_arm",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(-129.6528F, 81.0972F, -37.2025F), Interpolations.CATMULLROM),
               new Keyframe(0.4583F, KeyframeAnimations.degreeVec(-27.6857F, 45.5573F, 88.1056F), Interpolations.CATMULLROM),
               new Keyframe(0.7917F, KeyframeAnimations.degreeVec(-37.4837F, 55.7631F, 59.1241F), Interpolations.CATMULLROM),
               new Keyframe(1.1667F, KeyframeAnimations.degreeVec(-159.9042F, 85.7246F, -105.5338F), Interpolations.CATMULLROM),
               new Keyframe(1.3333F, KeyframeAnimations.degreeVec(-89.02F, 69.2251F, -49.3377F), Interpolations.CATMULLROM),
               new Keyframe(1.4583F, KeyframeAnimations.degreeVec(-38.9271F, 34.7699F, -29.8561F), Interpolations.CATMULLROM),
               new Keyframe(1.625F, KeyframeAnimations.degreeVec(11.687F, 0.4093F, -35.258F), Interpolations.CATMULLROM),
               new Keyframe(1.6667F, KeyframeAnimations.degreeVec(27.6197F, -8.1539F, -22.7485F), Interpolations.CATMULLROM),
               new Keyframe(1.75F, KeyframeAnimations.degreeVec(36.9038F, -18.6333F, -6.6683F), Interpolations.CATMULLROM),
               new Keyframe(1.8333F, KeyframeAnimations.degreeVec(7.4947F, -4.05F, -8.5374F), Interpolations.CATMULLROM),
               new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 1.0F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "left_arm",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(-0.0168F, -0.2645F, -1.5805F), Interpolations.CATMULLROM),
               new Keyframe(0.4583F, KeyframeAnimations.posVec(-0.981F, -0.7245F, -2.7184F), Interpolations.CATMULLROM),
               new Keyframe(0.7917F, KeyframeAnimations.posVec(-2.5F, 0.18F, -2.95F), Interpolations.CATMULLROM),
               new Keyframe(1.1667F, KeyframeAnimations.posVec(0.0F, 0.2816F, -1.6538F), Interpolations.CATMULLROM),
               new Keyframe(1.5417F, KeyframeAnimations.posVec(0.0F, 0.1646F, 0.5355F), Interpolations.CATMULLROM),
               new Keyframe(1.625F, KeyframeAnimations.posVec(0.0F, 0.98F, -0.56F), Interpolations.CATMULLROM),
               new Keyframe(1.6667F, KeyframeAnimations.posVec(0.0F, 1.59F, -1.66F), Interpolations.CATMULLROM),
               new Keyframe(1.75F, KeyframeAnimations.posVec(0.0F, 0.5999F, -2.7607F), Interpolations.CATMULLROM),
               new Keyframe(1.8333F, KeyframeAnimations.posVec(-0.01F, 0.09F, -0.7F), Interpolations.CATMULLROM),
               new Keyframe(2.0F, KeyframeAnimations.posVec(-0.0297F, 0.118F, 0.8973F), Interpolations.LINEAR)
            }
         )
      )
      .addAnimation(
         "left_arm", new AnimationChannel(Targets.SCALE, new Keyframe[]{new Keyframe(2.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR)})
      )
      .build();
}
