package cn.leolezury.eternalstarlight.common.client.model.animation.definition;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.animation.AnimationChannel.Interpolations;
import net.minecraft.client.animation.AnimationChannel.Targets;
import net.minecraft.client.animation.AnimationDefinition.Builder;

public class TheGatekeeperAnimation {
   public static final AnimationDefinition SIT = Builder.withLength(0.5F)
      .addAnimation(
         "root",
         new AnimationChannel(Targets.POSITION, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -10.0F, 0.0F), Interpolations.LINEAR)})
      )
      .addAnimation(
         "body",
         new AnimationChannel(Targets.ROTATION, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.degreeVec(-12.5F, 0.0F, 0.0F), Interpolations.LINEAR)})
      )
      .addAnimation(
         "body",
         new AnimationChannel(Targets.POSITION, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -1.0F, 0.0F), Interpolations.LINEAR)})
      )
      .addAnimation(
         "left_arm",
         new AnimationChannel(
            Targets.ROTATION, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.degreeVec(14.7064F, 1.811F, -9.6214F), Interpolations.LINEAR)}
         )
      )
      .addAnimation(
         "left_arm",
         new AnimationChannel(Targets.POSITION, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -0.9F, 0.0F), Interpolations.LINEAR)})
      )
      .addAnimation(
         "right_arm",
         new AnimationChannel(
            Targets.ROTATION, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.degreeVec(-79.7278F, -17.2572F, 9.0747F), Interpolations.LINEAR)}
         )
      )
      .addAnimation(
         "right_hand",
         new AnimationChannel(Targets.ROTATION, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.degreeVec(205.0F, 0.0F, 0.0F), Interpolations.LINEAR)})
      )
      .addAnimation(
         "head",
         new AnimationChannel(Targets.ROTATION, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.degreeVec(15.0F, 0.0F, 0.0F), Interpolations.LINEAR)})
      )
      .addAnimation(
         "left_leg",
         new AnimationChannel(
            Targets.ROTATION, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.degreeVec(-89.4959F, -14.4775F, 3.9671F), Interpolations.LINEAR)}
         )
      )
      .addAnimation(
         "left_leg",
         new AnimationChannel(Targets.POSITION, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)})
      )
      .addAnimation(
         "right_leg",
         new AnimationChannel(
            Targets.ROTATION, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.degreeVec(-27.4162F, 1.2988F, 7.3873F), Interpolations.LINEAR)}
         )
      )
      .addAnimation(
         "right_leg",
         new AnimationChannel(Targets.POSITION, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 7.0F, -2.0F), Interpolations.LINEAR)})
      )
      .build();
   public static final AnimationDefinition STAND = Builder.withLength(0.5F)
      .looping()
      .addAnimation(
         "root",
         new AnimationChannel(Targets.ROTATION, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.degreeVec(7.5F, 0.0F, 0.0F), Interpolations.LINEAR)})
      )
      .addAnimation(
         "root",
         new AnimationChannel(Targets.POSITION, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -0.9F, 6.0F), Interpolations.LINEAR)})
      )
      .addAnimation(
         "body",
         new AnimationChannel(Targets.ROTATION, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.degreeVec(-2.5F, 0.0F, 0.0F), Interpolations.LINEAR)})
      )
      .addAnimation(
         "left_arm",
         new AnimationChannel(
            Targets.ROTATION, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.degreeVec(-29.9336F, -0.3803F, 7.7961F), Interpolations.LINEAR)}
         )
      )
      .addAnimation(
         "right_hand",
         new AnimationChannel(Targets.ROTATION, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.degreeVec(82.5F, 0.0F, 0.0F), Interpolations.LINEAR)})
      )
      .addAnimation(
         "head",
         new AnimationChannel(Targets.ROTATION, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.degreeVec(-5.0F, 0.0F, 0.0F), Interpolations.LINEAR)})
      )
      .addAnimation(
         "left_leg",
         new AnimationChannel(Targets.ROTATION, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.degreeVec(20.0F, 0.0F, 0.0F), Interpolations.LINEAR)})
      )
      .addAnimation(
         "left_leg",
         new AnimationChannel(Targets.POSITION, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.posVec(-0.1F, 1.0F, -3.0F), Interpolations.LINEAR)})
      )
      .addAnimation(
         "right_leg",
         new AnimationChannel(Targets.ROTATION, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.degreeVec(-17.5F, 0.0F, 0.0F), Interpolations.LINEAR)})
      )
      .build();
   public static final AnimationDefinition TALK = Builder.withLength(2.0F)
      .addAnimation(
         "left_arm",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.3F, KeyframeAnimations.degreeVec(-65.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.75F, KeyframeAnimations.degreeVec(-61.82F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.5F, KeyframeAnimations.degreeVec(-65.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "head",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.2F, KeyframeAnimations.degreeVec(12.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.4F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.6F, KeyframeAnimations.degreeVec(12.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.9F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.5F, KeyframeAnimations.degreeVec(12.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .build();
   public static final AnimationDefinition RUN = Builder.withLength(0.6F)
      .looping()
      .addAnimation(
         "root",
         new AnimationChannel(Targets.ROTATION, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.degreeVec(2.5F, 0.0F, 0.0F), Interpolations.LINEAR)})
      )
      .addAnimation(
         "root",
         new AnimationChannel(Targets.POSITION, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -0.8F, 0.0F), Interpolations.LINEAR)})
      )
      .addAnimation(
         "body",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(17.5627F, -4.768F, -1.507F), Interpolations.CATMULLROM),
               new Keyframe(0.15F, KeyframeAnimations.degreeVec(20.06F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.3F, KeyframeAnimations.degreeVec(17.5627F, 4.768F, 1.507F), Interpolations.CATMULLROM),
               new Keyframe(0.45F, KeyframeAnimations.degreeVec(20.06F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.6F, KeyframeAnimations.degreeVec(17.5627F, -4.768F, -1.507F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "left_arm",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(37.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.3F, KeyframeAnimations.degreeVec(37.3946F, 3.0414F, -3.9705F), Interpolations.CATMULLROM),
               new Keyframe(0.6F, KeyframeAnimations.degreeVec(37.5F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "right_arm",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(37.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.3F, KeyframeAnimations.degreeVec(37.0772F, -6.0681F, 7.9634F), Interpolations.CATMULLROM),
               new Keyframe(0.6F, KeyframeAnimations.degreeVec(37.5F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "right_hand",
         new AnimationChannel(Targets.ROTATION, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.degreeVec(27.5F, 0.0F, 0.0F), Interpolations.LINEAR)})
      )
      .addAnimation(
         "head",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(-15.0547F, 4.8292F, -1.2972F), Interpolations.CATMULLROM),
               new Keyframe(0.3F, KeyframeAnimations.degreeVec(-15.0547F, -4.8292F, 1.2972F), Interpolations.CATMULLROM),
               new Keyframe(0.6F, KeyframeAnimations.degreeVec(-15.0547F, 4.8292F, -1.2972F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "left_leg",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(25.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.3F, KeyframeAnimations.degreeVec(-7.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.6F, KeyframeAnimations.degreeVec(25.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "left_leg",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 1.0F), Interpolations.CATMULLROM),
               new Keyframe(0.15F, KeyframeAnimations.posVec(0.0F, 2.4F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.3F, KeyframeAnimations.posVec(0.0F, 0.7F, -1.0F), Interpolations.CATMULLROM),
               new Keyframe(0.6F, KeyframeAnimations.posVec(0.0F, 0.0F, 1.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "right_leg",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(-7.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.3F, KeyframeAnimations.degreeVec(25.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.6F, KeyframeAnimations.degreeVec(-7.5F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "right_leg",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.7F, -1.0F), Interpolations.CATMULLROM),
               new Keyframe(0.3F, KeyframeAnimations.posVec(0.0F, 0.0F, 1.0F), Interpolations.CATMULLROM),
               new Keyframe(0.45F, KeyframeAnimations.posVec(0.0F, 2.4F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.6F, KeyframeAnimations.posVec(0.0F, 0.7F, -1.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .build();
   public static final AnimationDefinition WALK = Builder.withLength(1.0F)
      .looping()
      .addAnimation(
         "body",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(2.5F, -5.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(2.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(2.5F, 5.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.75F, KeyframeAnimations.degreeVec(2.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.0F, KeyframeAnimations.degreeVec(2.5F, -5.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "body",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, -0.1F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, -0.1F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "left_arm",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(17.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(-17.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.0F, KeyframeAnimations.degreeVec(17.5F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "right_arm",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(-17.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(17.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.0F, KeyframeAnimations.degreeVec(-17.5F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "head",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 2.5F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, -2.5F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 2.5F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "head",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(-0.1F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.posVec(0.1F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.0F, KeyframeAnimations.posVec(-0.1F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "left_leg",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(-10.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(15.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.75F, KeyframeAnimations.degreeVec(-10.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.0F, KeyframeAnimations.degreeVec(-10.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "left_leg",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, -0.6F), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.6F), Interpolations.CATMULLROM),
               new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, 2.0F, -0.6F), Interpolations.CATMULLROM),
               new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.0F, -0.6F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "right_leg",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(15.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(-10.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(-10.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.0F, KeyframeAnimations.degreeVec(15.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "right_leg",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.6F), Interpolations.CATMULLROM),
               new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, 2.0F, -0.6F), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.0F, -0.6F), Interpolations.CATMULLROM),
               new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.6F), Interpolations.CATMULLROM)
            }
         )
      )
      .build();
   public static final AnimationDefinition STEP_BACK = Builder.withLength(0.85F)
      .addAnimation(
         "root",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.15F, KeyframeAnimations.degreeVec(-35.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.55F, KeyframeAnimations.degreeVec(-35.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.85F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "body",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.15F, KeyframeAnimations.degreeVec(15.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.55F, KeyframeAnimations.degreeVec(15.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.85F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "left_arm",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.15F, KeyframeAnimations.degreeVec(-10.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.55F, KeyframeAnimations.degreeVec(-10.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.85F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "right_arm",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.15F, KeyframeAnimations.degreeVec(-40.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.55F, KeyframeAnimations.degreeVec(-40.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.85F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "right_hand",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.15F, KeyframeAnimations.degreeVec(27.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.55F, KeyframeAnimations.degreeVec(27.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.85F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "head",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.15F, KeyframeAnimations.degreeVec(20.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.55F, KeyframeAnimations.degreeVec(20.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.85F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "left_leg",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.15F, KeyframeAnimations.degreeVec(-30.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.55F, KeyframeAnimations.degreeVec(-30.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.85F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .build();
   public static final AnimationDefinition JUMP_START = Builder.withLength(2.0F)
      .addAnimation(
         "body",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.75F, KeyframeAnimations.degreeVec(75.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.95F, KeyframeAnimations.degreeVec(-17.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(2.0F, KeyframeAnimations.degreeVec(-17.5F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "body",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, -1.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "left_arm",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.4F, KeyframeAnimations.degreeVec(-53.4F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.75F, KeyframeAnimations.degreeVec(-78.4F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.95F, KeyframeAnimations.degreeVec(-115.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(2.0F, KeyframeAnimations.degreeVec(-115.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "left_arm",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.4F, KeyframeAnimations.posVec(0.0F, 0.0F, -3.0F), Interpolations.CATMULLROM),
               new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, 0.0F, -3.0F), Interpolations.CATMULLROM),
               new Keyframe(0.95F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "right_arm",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.4F, KeyframeAnimations.degreeVec(37.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.75F, KeyframeAnimations.degreeVec(37.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.95F, KeyframeAnimations.degreeVec(-162.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(2.0F, KeyframeAnimations.degreeVec(-162.5F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "left_leg",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.4F, KeyframeAnimations.degreeVec(-5.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.75F, KeyframeAnimations.degreeVec(-5.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "left_leg",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.4F, KeyframeAnimations.posVec(0.0F, 0.0F, -4.0F), Interpolations.CATMULLROM),
               new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, 0.0F, -2.0F), Interpolations.CATMULLROM),
               new Keyframe(0.95F, KeyframeAnimations.posVec(0.0F, 3.0F, -1.0F), Interpolations.CATMULLROM),
               new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 3.0F, -1.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "right_leg",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.4F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.75F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.95F, KeyframeAnimations.degreeVec(12.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(2.0F, KeyframeAnimations.degreeVec(12.5F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "right_leg",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.4F, KeyframeAnimations.posVec(0.0F, 0.0F, 1.0F), Interpolations.CATMULLROM),
               new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, 0.0F, 1.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "root",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.95F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.5F, KeyframeAnimations.degreeVec(30.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(2.0F, KeyframeAnimations.degreeVec(30.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "head",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.4F, KeyframeAnimations.degreeVec(-42.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.75F, KeyframeAnimations.degreeVec(-67.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.95F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .build();
   public static final AnimationDefinition JUMP_TRANSITION = Builder.withLength(0.5F)
      .looping()
      .addAnimation(
         "root",
         new AnimationChannel(Targets.ROTATION, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.degreeVec(30.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)})
      )
      .addAnimation(
         "body",
         new AnimationChannel(Targets.ROTATION, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.degreeVec(-17.5F, 0.0F, 0.0F), Interpolations.CATMULLROM)})
      )
      .addAnimation(
         "body",
         new AnimationChannel(Targets.POSITION, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -1.0F, 0.0F), Interpolations.CATMULLROM)})
      )
      .addAnimation(
         "left_arm",
         new AnimationChannel(
            Targets.ROTATION, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.degreeVec(-115.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)}
         )
      )
      .addAnimation(
         "right_arm",
         new AnimationChannel(
            Targets.ROTATION, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.degreeVec(-162.5F, 0.0F, 0.0F), Interpolations.CATMULLROM)}
         )
      )
      .addAnimation(
         "right_hand",
         new AnimationChannel(Targets.ROTATION, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)})
      )
      .addAnimation(
         "left_leg",
         new AnimationChannel(Targets.ROTATION, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.degreeVec(-5.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)})
      )
      .addAnimation(
         "left_leg",
         new AnimationChannel(Targets.POSITION, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 3.0F, -1.0F), Interpolations.CATMULLROM)})
      )
      .addAnimation(
         "right_leg",
         new AnimationChannel(Targets.ROTATION, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.degreeVec(12.5F, 0.0F, 0.0F), Interpolations.CATMULLROM)})
      )
      .addAnimation(
         "right_leg",
         new AnimationChannel(Targets.POSITION, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 1.0F), Interpolations.CATMULLROM)})
      )
      .build();
   public static final AnimationDefinition JUMP_END = Builder.withLength(1.1F)
      .addAnimation(
         "body",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(-17.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.1F, KeyframeAnimations.degreeVec(67.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(67.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.85F, KeyframeAnimations.degreeVec(17.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.1F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "body",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -1.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, -1.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.85F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.1F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "left_arm",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(-115.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.1F, KeyframeAnimations.degreeVec(65.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(65.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.85F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.1F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "right_arm",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(-162.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.1F, KeyframeAnimations.degreeVec(-92.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.2F, KeyframeAnimations.degreeVec(-97.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.3F, KeyframeAnimations.degreeVec(-92.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(-92.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.85F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.1F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "right_hand",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.1F, KeyframeAnimations.degreeVec(45.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.2F, KeyframeAnimations.degreeVec(35.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.3F, KeyframeAnimations.degreeVec(45.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(45.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.85F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.1F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "left_leg",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(-5.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.1F, KeyframeAnimations.degreeVec(-20.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(-20.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.95F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "left_leg",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 3.0F, -1.0F), Interpolations.CATMULLROM),
               new Keyframe(0.1F, KeyframeAnimations.posVec(0.1F, 0.0F, -1.0F), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.posVec(0.1F, 0.0F, -1.0F), Interpolations.CATMULLROM),
               new Keyframe(0.75F, KeyframeAnimations.posVec(0.05F, 2.0F, -0.45F), Interpolations.CATMULLROM),
               new Keyframe(0.95F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "right_leg",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(12.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(12.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.95F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "right_leg",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 1.0F), Interpolations.CATMULLROM),
               new Keyframe(0.1F, KeyframeAnimations.posVec(-0.1F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.posVec(-0.1F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.95F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "root",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(30.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.1F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .build();
   public static final AnimationDefinition GREATSWORD = Builder.withLength(2.35F)
      .addAnimation(
         "body",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.3F, KeyframeAnimations.degreeVec(-7.0137F, 36.7776F, -16.6184F), Interpolations.CATMULLROM),
               new Keyframe(0.6F, KeyframeAnimations.degreeVec(-41.5288F, 59.5626F, -29.6806F), Interpolations.CATMULLROM),
               new Keyframe(0.75F, KeyframeAnimations.degreeVec(18.4596F, -52.378F, -1.2312F), Interpolations.CATMULLROM),
               new Keyframe(1.05F, KeyframeAnimations.degreeVec(18.4596F, -52.378F, -1.2312F), Interpolations.CATMULLROM),
               new Keyframe(1.55F, KeyframeAnimations.degreeVec(-41.5288F, -59.5626F, 29.6806F), Interpolations.CATMULLROM),
               new Keyframe(1.7F, KeyframeAnimations.degreeVec(18.4596F, 52.378F, 1.2312F), Interpolations.CATMULLROM),
               new Keyframe(2.0F, KeyframeAnimations.degreeVec(18.4596F, 52.378F, 1.2312F), Interpolations.CATMULLROM),
               new Keyframe(2.35F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "left_arm",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.6F, KeyframeAnimations.degreeVec(-113.1541F, 40.1148F, -15.4053F), Interpolations.CATMULLROM),
               new Keyframe(0.75F, KeyframeAnimations.degreeVec(-88.3681F, 30.1194F, -15.7758F), Interpolations.CATMULLROM),
               new Keyframe(1.05F, KeyframeAnimations.degreeVec(-88.3681F, 30.1194F, -15.7758F), Interpolations.CATMULLROM),
               new Keyframe(1.55F, KeyframeAnimations.degreeVec(-114.0321F, 42.406F, -16.7363F), Interpolations.CATMULLROM),
               new Keyframe(1.7F, KeyframeAnimations.degreeVec(-70.4008F, 65.1782F, 15.5776F), Interpolations.CATMULLROM),
               new Keyframe(2.0F, KeyframeAnimations.degreeVec(-45.9045F, 69.8231F, 20.4398F), Interpolations.CATMULLROM),
               new Keyframe(2.35F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "right_arm",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.6F, KeyframeAnimations.degreeVec(-71.6888F, -32.1366F, -76.6055F), Interpolations.CATMULLROM),
               new Keyframe(0.75F, KeyframeAnimations.degreeVec(-50.6047F, -23.9906F, -43.7259F), Interpolations.CATMULLROM),
               new Keyframe(1.05F, KeyframeAnimations.degreeVec(-50.6047F, -23.9906F, -43.7259F), Interpolations.CATMULLROM),
               new Keyframe(1.55F, KeyframeAnimations.degreeVec(-104.3108F, 4.6388F, 76.2297F), Interpolations.CATMULLROM),
               new Keyframe(1.7F, KeyframeAnimations.degreeVec(-81.7787F, -10.2737F, 65.7709F), Interpolations.CATMULLROM),
               new Keyframe(2.0F, KeyframeAnimations.degreeVec(-77.0336F, -2.0881F, 23.7739F), Interpolations.CATMULLROM),
               new Keyframe(2.35F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "right_hand",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.6F, KeyframeAnimations.degreeVec(13.98F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.75F, KeyframeAnimations.degreeVec(62.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.05F, KeyframeAnimations.degreeVec(62.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.55F, KeyframeAnimations.degreeVec(13.98F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.7F, KeyframeAnimations.degreeVec(62.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(2.0F, KeyframeAnimations.degreeVec(62.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(2.35F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "head",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.6F, KeyframeAnimations.degreeVec(-20.0F, -42.5F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.75F, KeyframeAnimations.degreeVec(0.0F, -30.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.05F, KeyframeAnimations.degreeVec(22.5F, -30.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.55F, KeyframeAnimations.degreeVec(-20.0F, 42.5F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.7F, KeyframeAnimations.degreeVec(0.0F, 30.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 30.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(2.35F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "left_leg",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.6F, KeyframeAnimations.degreeVec(-49.8924F, -3.8282F, -3.2187F), Interpolations.CATMULLROM),
               new Keyframe(0.75F, KeyframeAnimations.degreeVec(-12.5F, -17.5F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.05F, KeyframeAnimations.degreeVec(-20.0574F, -15.7595F, 3.0583F), Interpolations.CATMULLROM),
               new Keyframe(1.55F, KeyframeAnimations.degreeVec(-49.8924F, 3.8282F, 3.2187F), Interpolations.CATMULLROM),
               new Keyframe(1.7F, KeyframeAnimations.degreeVec(-12.5F, 17.5F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(2.0F, KeyframeAnimations.degreeVec(-12.5F, 17.5F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(2.35F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "left_leg",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.6F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.75F, KeyframeAnimations.posVec(1.1F, 0.0F, -0.9F), Interpolations.CATMULLROM),
               new Keyframe(1.05F, KeyframeAnimations.posVec(1.1F, 0.0F, 0.1F), Interpolations.CATMULLROM),
               new Keyframe(1.55F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.7F, KeyframeAnimations.posVec(1.1F, 0.0F, -0.9F), Interpolations.CATMULLROM),
               new Keyframe(2.0F, KeyframeAnimations.posVec(1.1F, 0.0F, -0.9F), Interpolations.CATMULLROM),
               new Keyframe(2.35F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .build();
   public static final AnimationDefinition HAMMER = Builder.withLength(1.6F)
      .addAnimation(
         "body",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.75F, KeyframeAnimations.degreeVec(-41.5288F, 59.5626F, -29.6806F), Interpolations.CATMULLROM),
               new Keyframe(0.9F, KeyframeAnimations.degreeVec(18.4596F, -52.378F, -1.2312F), Interpolations.CATMULLROM),
               new Keyframe(1.2F, KeyframeAnimations.degreeVec(18.4596F, -52.378F, -1.2312F), Interpolations.CATMULLROM),
               new Keyframe(1.6F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "left_arm",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.75F, KeyframeAnimations.degreeVec(-113.1541F, 40.1148F, -15.4053F), Interpolations.CATMULLROM),
               new Keyframe(0.9F, KeyframeAnimations.degreeVec(-88.3681F, 30.1194F, -15.7758F), Interpolations.CATMULLROM),
               new Keyframe(1.2F, KeyframeAnimations.degreeVec(-88.3681F, 30.1194F, -15.7758F), Interpolations.CATMULLROM),
               new Keyframe(1.6F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "right_arm",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.75F, KeyframeAnimations.degreeVec(-71.6888F, -32.1366F, -76.6055F), Interpolations.CATMULLROM),
               new Keyframe(0.9F, KeyframeAnimations.degreeVec(-50.6047F, -23.9906F, -43.7259F), Interpolations.CATMULLROM),
               new Keyframe(1.2F, KeyframeAnimations.degreeVec(-50.6047F, -23.9906F, -43.7259F), Interpolations.CATMULLROM),
               new Keyframe(1.6F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "right_hand",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.75F, KeyframeAnimations.degreeVec(13.98F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.9F, KeyframeAnimations.degreeVec(62.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.2F, KeyframeAnimations.degreeVec(62.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.6F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "head",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.75F, KeyframeAnimations.degreeVec(-20.0F, -42.5F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.9F, KeyframeAnimations.degreeVec(0.0F, -30.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.2F, KeyframeAnimations.degreeVec(22.5F, -30.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.6F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "left_leg",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.75F, KeyframeAnimations.degreeVec(-49.8924F, -3.8282F, -3.2187F), Interpolations.CATMULLROM),
               new Keyframe(0.9F, KeyframeAnimations.degreeVec(-12.5F, -17.5F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.2F, KeyframeAnimations.degreeVec(-20.0574F, -15.7595F, 3.0583F), Interpolations.CATMULLROM),
               new Keyframe(1.6F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "left_leg",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.9F, KeyframeAnimations.posVec(1.1F, 0.0F, -0.9F), Interpolations.CATMULLROM),
               new Keyframe(1.2F, KeyframeAnimations.posVec(1.1F, 0.0F, 0.1F), Interpolations.CATMULLROM),
               new Keyframe(1.6F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .build();
   public static final AnimationDefinition DASH = Builder.withLength(2.0F)
      .addAnimation(
         "body",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.375F, KeyframeAnimations.degreeVec(6.4987F, 52.4808F, 22.5465F), Interpolations.CATMULLROM),
               new Keyframe(0.7917F, KeyframeAnimations.degreeVec(4.86F, 40.2F, 19.91F), Interpolations.CATMULLROM),
               new Keyframe(0.9167F, KeyframeAnimations.degreeVec(43.0543F, -41.7222F, -33.1288F), Interpolations.CATMULLROM),
               new Keyframe(1.5417F, KeyframeAnimations.degreeVec(43.0543F, -41.7222F, -33.1288F), Interpolations.CATMULLROM),
               new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "body",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.7917F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, -1.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "left_arm",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.375F, KeyframeAnimations.degreeVec(-71.8303F, 56.1973F, 4.814F), Interpolations.CATMULLROM),
               new Keyframe(0.7917F, KeyframeAnimations.degreeVec(-71.8303F, 56.1973F, 4.814F), Interpolations.CATMULLROM),
               new Keyframe(0.9167F, KeyframeAnimations.degreeVec(-15.6085F, 30.6092F, -47.2873F), Interpolations.CATMULLROM),
               new Keyframe(1.5417F, KeyframeAnimations.degreeVec(-15.6085F, 30.6092F, -47.2873F), Interpolations.CATMULLROM),
               new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "right_arm",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-62.1961F, -21.4293F, 54.7305F), Interpolations.CATMULLROM),
               new Keyframe(0.7917F, KeyframeAnimations.degreeVec(-30.7901F, -4.3721F, 81.1577F), Interpolations.CATMULLROM),
               new Keyframe(0.9167F, KeyframeAnimations.degreeVec(-17.183F, 14.6051F, 108.2667F), Interpolations.CATMULLROM),
               new Keyframe(1.4583F, KeyframeAnimations.degreeVec(-17.183F, 14.6051F, 108.2667F), Interpolations.CATMULLROM),
               new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "left_leg",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.7083F, KeyframeAnimations.degreeVec(-7.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.9167F, KeyframeAnimations.degreeVec(27.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.5417F, KeyframeAnimations.degreeVec(27.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "left_leg",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.7083F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.9167F, KeyframeAnimations.posVec(0.0F, 0.0F, 1.0F), Interpolations.CATMULLROM),
               new Keyframe(1.5417F, KeyframeAnimations.posVec(0.0F, 0.0F, 1.0F), Interpolations.CATMULLROM),
               new Keyframe(1.75F, KeyframeAnimations.posVec(0.0F, 2.0F, 0.56F), Interpolations.CATMULLROM),
               new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "right_leg",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.7083F, KeyframeAnimations.degreeVec(29.4335F, 12.1002F, 20.832F), Interpolations.CATMULLROM),
               new Keyframe(0.9167F, KeyframeAnimations.degreeVec(-12.5F, 10.0F, -2.0F), Interpolations.CATMULLROM),
               new Keyframe(1.5417F, KeyframeAnimations.degreeVec(-12.5F, 10.0F, -2.0F), Interpolations.CATMULLROM),
               new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "right_leg",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.7083F, KeyframeAnimations.posVec(1.0F, 1.0F, 1.42F), Interpolations.CATMULLROM),
               new Keyframe(0.9167F, KeyframeAnimations.posVec(0.0F, 0.0F, -1.0F), Interpolations.CATMULLROM),
               new Keyframe(1.5417F, KeyframeAnimations.posVec(0.0F, 0.0F, -1.0F), Interpolations.CATMULLROM),
               new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "head",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.75F, KeyframeAnimations.degreeVec(-0.2906F, 3.3173F, -5.0084F), Interpolations.CATMULLROM),
               new Keyframe(0.9167F, KeyframeAnimations.degreeVec(0.0F, 50.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.5417F, KeyframeAnimations.degreeVec(-20.3449F, 47.0828F, -10.2391F), Interpolations.CATMULLROM),
               new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "right_hand",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.75F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.9167F, KeyframeAnimations.degreeVec(80.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.5417F, KeyframeAnimations.degreeVec(80.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .build();
   public static final AnimationDefinition GREATSWORD_COMBO = Builder.withLength(5.25F)
      .addAnimation(
         "body",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(5.4863F, 36.7776F, -16.6184F), Interpolations.CATMULLROM),
               new Keyframe(1.0F, KeyframeAnimations.degreeVec(-41.5288F, 59.5626F, -29.6806F), Interpolations.CATMULLROM),
               new Keyframe(1.15F, KeyframeAnimations.degreeVec(18.4596F, -52.378F, -1.2312F), Interpolations.CATMULLROM),
               new Keyframe(1.45F, KeyframeAnimations.degreeVec(18.4596F, -52.378F, -1.2312F), Interpolations.CATMULLROM),
               new Keyframe(2.0F, KeyframeAnimations.degreeVec(-41.5288F, -59.5626F, 29.6806F), Interpolations.CATMULLROM),
               new Keyframe(2.15F, KeyframeAnimations.degreeVec(18.4596F, 52.378F, 1.2312F), Interpolations.CATMULLROM),
               new Keyframe(2.45F, KeyframeAnimations.degreeVec(18.4596F, 52.378F, 1.2312F), Interpolations.CATMULLROM),
               new Keyframe(3.0F, KeyframeAnimations.degreeVec(-41.5288F, 59.5626F, -29.6806F), Interpolations.CATMULLROM),
               new Keyframe(3.15F, KeyframeAnimations.degreeVec(18.4596F, -52.378F, -1.2312F), Interpolations.CATMULLROM),
               new Keyframe(3.45F, KeyframeAnimations.degreeVec(18.4596F, -52.378F, -1.2312F), Interpolations.CATMULLROM),
               new Keyframe(4.0F, KeyframeAnimations.degreeVec(-41.5288F, -59.5626F, 29.6806F), Interpolations.CATMULLROM),
               new Keyframe(4.15F, KeyframeAnimations.degreeVec(18.4596F, 52.378F, 1.2312F), Interpolations.CATMULLROM),
               new Keyframe(4.7F, KeyframeAnimations.degreeVec(18.4596F, 52.378F, 1.2312F), Interpolations.CATMULLROM),
               new Keyframe(5.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "left_arm",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.0F, KeyframeAnimations.degreeVec(-113.1541F, 40.1148F, -15.4053F), Interpolations.CATMULLROM),
               new Keyframe(1.15F, KeyframeAnimations.degreeVec(-88.3681F, 30.1194F, -15.7758F), Interpolations.CATMULLROM),
               new Keyframe(1.45F, KeyframeAnimations.degreeVec(-88.3681F, 30.1194F, -15.7758F), Interpolations.CATMULLROM),
               new Keyframe(2.0F, KeyframeAnimations.degreeVec(-114.0321F, 42.406F, -16.7363F), Interpolations.CATMULLROM),
               new Keyframe(2.15F, KeyframeAnimations.degreeVec(-70.4008F, 65.1782F, 15.5776F), Interpolations.CATMULLROM),
               new Keyframe(2.45F, KeyframeAnimations.degreeVec(-45.9045F, 69.8231F, 20.4398F), Interpolations.CATMULLROM),
               new Keyframe(3.0F, KeyframeAnimations.degreeVec(-113.1541F, 40.1148F, -15.4053F), Interpolations.CATMULLROM),
               new Keyframe(3.15F, KeyframeAnimations.degreeVec(-88.3681F, 30.1194F, -15.7758F), Interpolations.CATMULLROM),
               new Keyframe(3.45F, KeyframeAnimations.degreeVec(-88.3681F, 30.1194F, -15.7758F), Interpolations.CATMULLROM),
               new Keyframe(4.0F, KeyframeAnimations.degreeVec(-114.0321F, 42.406F, -16.7363F), Interpolations.CATMULLROM),
               new Keyframe(4.15F, KeyframeAnimations.degreeVec(-70.4008F, 65.1782F, 15.5776F), Interpolations.CATMULLROM),
               new Keyframe(4.7F, KeyframeAnimations.degreeVec(-45.9045F, 69.8231F, 20.4398F), Interpolations.CATMULLROM),
               new Keyframe(5.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "right_arm",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.0F, KeyframeAnimations.degreeVec(-71.6888F, -32.1366F, -76.6055F), Interpolations.CATMULLROM),
               new Keyframe(1.15F, KeyframeAnimations.degreeVec(-50.6047F, -23.9906F, -43.7259F), Interpolations.CATMULLROM),
               new Keyframe(1.45F, KeyframeAnimations.degreeVec(-50.6047F, -23.9906F, -43.7259F), Interpolations.CATMULLROM),
               new Keyframe(2.0F, KeyframeAnimations.degreeVec(-104.3108F, 4.6388F, 76.2297F), Interpolations.CATMULLROM),
               new Keyframe(2.15F, KeyframeAnimations.degreeVec(-81.7787F, -10.2737F, 65.7709F), Interpolations.CATMULLROM),
               new Keyframe(2.45F, KeyframeAnimations.degreeVec(-77.0336F, -2.0881F, 23.7739F), Interpolations.CATMULLROM),
               new Keyframe(3.0F, KeyframeAnimations.degreeVec(-71.6888F, -32.1366F, -76.6055F), Interpolations.CATMULLROM),
               new Keyframe(3.15F, KeyframeAnimations.degreeVec(-50.6047F, -23.9906F, -43.7259F), Interpolations.CATMULLROM),
               new Keyframe(3.45F, KeyframeAnimations.degreeVec(-50.6047F, -23.9906F, -43.7259F), Interpolations.CATMULLROM),
               new Keyframe(4.0F, KeyframeAnimations.degreeVec(-104.3108F, 4.6388F, 76.2297F), Interpolations.CATMULLROM),
               new Keyframe(4.15F, KeyframeAnimations.degreeVec(-81.7787F, -10.2737F, 65.7709F), Interpolations.CATMULLROM),
               new Keyframe(4.7F, KeyframeAnimations.degreeVec(-77.0336F, -2.0881F, 23.7739F), Interpolations.CATMULLROM),
               new Keyframe(5.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "right_hand",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.0F, KeyframeAnimations.degreeVec(13.98F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.15F, KeyframeAnimations.degreeVec(62.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.45F, KeyframeAnimations.degreeVec(62.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(2.0F, KeyframeAnimations.degreeVec(13.98F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(2.15F, KeyframeAnimations.degreeVec(62.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(2.45F, KeyframeAnimations.degreeVec(62.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(3.0F, KeyframeAnimations.degreeVec(13.98F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(3.15F, KeyframeAnimations.degreeVec(62.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(3.45F, KeyframeAnimations.degreeVec(62.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(4.0F, KeyframeAnimations.degreeVec(13.98F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(4.15F, KeyframeAnimations.degreeVec(62.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(4.7F, KeyframeAnimations.degreeVec(62.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(5.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "head",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.0F, KeyframeAnimations.degreeVec(-20.0F, -42.5F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.15F, KeyframeAnimations.degreeVec(0.0F, -30.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.45F, KeyframeAnimations.degreeVec(22.5F, -30.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(2.0F, KeyframeAnimations.degreeVec(-20.0F, 42.5F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(2.15F, KeyframeAnimations.degreeVec(0.0F, 30.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(2.45F, KeyframeAnimations.degreeVec(0.0F, 30.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(3.0F, KeyframeAnimations.degreeVec(-20.0F, -42.5F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(3.15F, KeyframeAnimations.degreeVec(0.0F, -30.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(3.45F, KeyframeAnimations.degreeVec(22.5F, -30.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(4.0F, KeyframeAnimations.degreeVec(-20.0F, 42.5F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(4.15F, KeyframeAnimations.degreeVec(0.0F, 30.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(4.7F, KeyframeAnimations.degreeVec(0.0F, 30.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(5.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "left_leg",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.0F, KeyframeAnimations.degreeVec(-49.8924F, -3.8282F, -3.2187F), Interpolations.CATMULLROM),
               new Keyframe(1.15F, KeyframeAnimations.degreeVec(-12.5F, -17.5F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.45F, KeyframeAnimations.degreeVec(-20.0574F, -15.7595F, 3.0583F), Interpolations.CATMULLROM),
               new Keyframe(2.0F, KeyframeAnimations.degreeVec(15.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(2.45F, KeyframeAnimations.degreeVec(15.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(3.0F, KeyframeAnimations.degreeVec(-49.8924F, -3.8282F, -3.2187F), Interpolations.CATMULLROM),
               new Keyframe(3.15F, KeyframeAnimations.degreeVec(-12.5F, -17.5F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(3.45F, KeyframeAnimations.degreeVec(-20.0574F, -15.7595F, 3.0583F), Interpolations.CATMULLROM),
               new Keyframe(4.0F, KeyframeAnimations.degreeVec(15.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(4.7F, KeyframeAnimations.degreeVec(15.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(5.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "left_leg",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.15F, KeyframeAnimations.posVec(0.3F, 0.0F, -0.9F), Interpolations.CATMULLROM),
               new Keyframe(1.45F, KeyframeAnimations.posVec(0.3F, 0.0F, 0.1F), Interpolations.CATMULLROM),
               new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(2.45F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(3.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(3.15F, KeyframeAnimations.posVec(0.3F, 0.0F, -0.9F), Interpolations.CATMULLROM),
               new Keyframe(3.45F, KeyframeAnimations.posVec(0.3F, 0.0F, 0.1F), Interpolations.CATMULLROM),
               new Keyframe(4.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(4.7F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(5.25F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "right_leg",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.15F, KeyframeAnimations.degreeVec(15.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.45F, KeyframeAnimations.degreeVec(15.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(2.0F, KeyframeAnimations.degreeVec(-49.8924F, 3.8282F, -3.2187F), Interpolations.CATMULLROM),
               new Keyframe(2.15F, KeyframeAnimations.degreeVec(-12.5F, 17.5F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(2.45F, KeyframeAnimations.degreeVec(-12.5F, 17.5F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(3.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(3.15F, KeyframeAnimations.degreeVec(15.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(3.45F, KeyframeAnimations.degreeVec(15.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(4.0F, KeyframeAnimations.degreeVec(-49.8924F, 3.8282F, -3.2187F), Interpolations.CATMULLROM),
               new Keyframe(4.15F, KeyframeAnimations.degreeVec(-12.5F, 17.5F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(4.7F, KeyframeAnimations.degreeVec(-12.5F, 17.5F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(5.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "right_leg",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.15F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.45F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(2.15F, KeyframeAnimations.posVec(-0.3F, 0.0F, -0.9F), Interpolations.CATMULLROM),
               new Keyframe(2.45F, KeyframeAnimations.posVec(-0.3F, 0.0F, -0.9F), Interpolations.CATMULLROM),
               new Keyframe(3.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(3.15F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(3.45F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(4.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(4.15F, KeyframeAnimations.posVec(-0.3F, 0.0F, -0.9F), Interpolations.CATMULLROM),
               new Keyframe(4.7F, KeyframeAnimations.posVec(-0.3F, 0.0F, -0.9F), Interpolations.CATMULLROM),
               new Keyframe(5.05F, KeyframeAnimations.posVec(-0.15F, 2.0F, -0.45F), Interpolations.CATMULLROM),
               new Keyframe(5.25F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .build();
   public static final AnimationDefinition BOW = Builder.withLength(2.35F)
      .addAnimation(
         "body",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.4F, KeyframeAnimations.degreeVec(0.0F, -30.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.3F, KeyframeAnimations.degreeVec(0.0F, -45.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.4F, KeyframeAnimations.degreeVec(-20.0F, -45.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.85F, KeyframeAnimations.degreeVec(-20.0F, -45.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(2.35F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "left_arm",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(-93.389F, 42.45F, -2.2889F), Interpolations.CATMULLROM),
               new Keyframe(1.3F, KeyframeAnimations.degreeVec(-93.8858F, 49.9351F, -2.9758F), Interpolations.CATMULLROM),
               new Keyframe(1.4F, KeyframeAnimations.degreeVec(-178.8858F, 49.9351F, -2.9758F), Interpolations.CATMULLROM),
               new Keyframe(1.55F, KeyframeAnimations.degreeVec(-206.3858F, 49.9351F, -2.9758F), Interpolations.CATMULLROM),
               new Keyframe(2.0F, KeyframeAnimations.degreeVec(-206.3858F, 49.9351F, -2.9758F), Interpolations.CATMULLROM),
               new Keyframe(2.35F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "left_arm",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.3F, KeyframeAnimations.posVec(0.0F, 0.0F, 2.0F), Interpolations.CATMULLROM),
               new Keyframe(1.4F, KeyframeAnimations.posVec(0.0F, 0.0F, -1.0F), Interpolations.CATMULLROM),
               new Keyframe(1.85F, KeyframeAnimations.posVec(0.0F, 0.0F, -1.0F), Interpolations.CATMULLROM),
               new Keyframe(2.35F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "right_arm",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(-82.3201F, -12.3914F, -1.6575F), Interpolations.CATMULLROM),
               new Keyframe(1.3F, KeyframeAnimations.degreeVec(-82.3856F, 9.9136F, 1.3184F), Interpolations.CATMULLROM),
               new Keyframe(1.4F, KeyframeAnimations.degreeVec(-97.3856F, 9.9136F, 1.3184F), Interpolations.CATMULLROM),
               new Keyframe(1.85F, KeyframeAnimations.degreeVec(-97.3856F, 9.9136F, 1.3184F), Interpolations.CATMULLROM),
               new Keyframe(2.35F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "right_hand",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 37.5F), Interpolations.CATMULLROM),
               new Keyframe(1.85F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 37.5F), Interpolations.CATMULLROM),
               new Keyframe(2.35F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "head",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.4F, KeyframeAnimations.degreeVec(-7.3301F, 39.0761F, -18.54F), Interpolations.CATMULLROM),
               new Keyframe(1.3F, KeyframeAnimations.degreeVec(-7.3301F, 39.0761F, -18.54F), Interpolations.CATMULLROM),
               new Keyframe(1.4F, KeyframeAnimations.degreeVec(3.1988F, 49.061F, 5.9363F), Interpolations.CATMULLROM),
               new Keyframe(1.85F, KeyframeAnimations.degreeVec(3.1988F, 49.061F, 5.9363F), Interpolations.CATMULLROM),
               new Keyframe(2.35F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "left_leg",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.3F, KeyframeAnimations.degreeVec(7.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(7.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.85F, KeyframeAnimations.degreeVec(7.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(2.35F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "left_leg",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.15F, KeyframeAnimations.posVec(0.0F, 2.0F, 0.85F), Interpolations.CATMULLROM),
               new Keyframe(0.3F, KeyframeAnimations.posVec(0.0F, 0.0F, 1.3F), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 1.3F), Interpolations.CATMULLROM),
               new Keyframe(1.85F, KeyframeAnimations.posVec(0.0F, 0.0F, 1.3F), Interpolations.CATMULLROM),
               new Keyframe(2.35F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "right_leg",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.4F, KeyframeAnimations.degreeVec(-8.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.85F, KeyframeAnimations.degreeVec(-8.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(2.15F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(2.35F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "right_leg",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.4F, KeyframeAnimations.posVec(0.0F, 0.0F, -1.3F), Interpolations.CATMULLROM),
               new Keyframe(1.85F, KeyframeAnimations.posVec(0.0F, 0.0F, -1.3F), Interpolations.CATMULLROM),
               new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 2.0F, -0.65F), Interpolations.CATMULLROM),
               new Keyframe(2.15F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(2.35F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .build();
   public static final AnimationDefinition BOW_COMBO = Builder.withLength(3.8F)
      .addAnimation(
         "body",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.4F, KeyframeAnimations.degreeVec(0.0F, -30.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.05F, KeyframeAnimations.degreeVec(0.0F, -45.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.15F, KeyframeAnimations.degreeVec(-20.0F, -45.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.35F, KeyframeAnimations.degreeVec(-20.0F, -45.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.7F, KeyframeAnimations.degreeVec(0.0F, -30.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.9F, KeyframeAnimations.degreeVec(0.0F, -45.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(2.0F, KeyframeAnimations.degreeVec(-20.0F, -45.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(2.2F, KeyframeAnimations.degreeVec(-20.0F, -45.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(2.55F, KeyframeAnimations.degreeVec(0.0F, -30.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(2.75F, KeyframeAnimations.degreeVec(0.0F, -45.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(2.85F, KeyframeAnimations.degreeVec(-20.0F, -45.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(3.2F, KeyframeAnimations.degreeVec(-20.0F, -45.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(3.8F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "left_arm",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(-93.389F, 42.45F, -2.2889F), Interpolations.CATMULLROM),
               new Keyframe(1.05F, KeyframeAnimations.degreeVec(-93.8858F, 49.9351F, -2.9758F), Interpolations.CATMULLROM),
               new Keyframe(1.15F, KeyframeAnimations.degreeVec(-178.8858F, 49.9351F, -2.9758F), Interpolations.CATMULLROM),
               new Keyframe(1.35F, KeyframeAnimations.degreeVec(-178.8858F, 49.9351F, -2.9758F), Interpolations.CATMULLROM),
               new Keyframe(1.55F, KeyframeAnimations.degreeVec(-93.389F, 42.45F, -2.2889F), Interpolations.CATMULLROM),
               new Keyframe(1.9F, KeyframeAnimations.degreeVec(-93.8858F, 49.9351F, -2.9758F), Interpolations.CATMULLROM),
               new Keyframe(2.0F, KeyframeAnimations.degreeVec(-178.8858F, 49.9351F, -2.9758F), Interpolations.CATMULLROM),
               new Keyframe(2.2F, KeyframeAnimations.degreeVec(-178.8858F, 49.9351F, -2.9758F), Interpolations.CATMULLROM),
               new Keyframe(2.4F, KeyframeAnimations.degreeVec(-93.389F, 42.45F, -2.2889F), Interpolations.CATMULLROM),
               new Keyframe(2.75F, KeyframeAnimations.degreeVec(-93.8858F, 49.9351F, -2.9758F), Interpolations.CATMULLROM),
               new Keyframe(2.85F, KeyframeAnimations.degreeVec(-178.8858F, 49.9351F, -2.9758F), Interpolations.CATMULLROM),
               new Keyframe(2.95F, KeyframeAnimations.degreeVec(-206.3858F, 49.9351F, -2.9758F), Interpolations.CATMULLROM),
               new Keyframe(3.35F, KeyframeAnimations.degreeVec(-206.3858F, 49.9351F, -2.9758F), Interpolations.CATMULLROM),
               new Keyframe(3.8F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "left_arm",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.05F, KeyframeAnimations.posVec(0.0F, 0.0F, 2.0F), Interpolations.CATMULLROM),
               new Keyframe(1.15F, KeyframeAnimations.posVec(0.0F, 0.0F, -1.0F), Interpolations.CATMULLROM),
               new Keyframe(1.35F, KeyframeAnimations.posVec(0.0F, 0.0F, -1.0F), Interpolations.CATMULLROM),
               new Keyframe(1.55F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.9F, KeyframeAnimations.posVec(0.0F, 0.0F, 2.0F), Interpolations.CATMULLROM),
               new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 0.0F, -1.0F), Interpolations.CATMULLROM),
               new Keyframe(2.2F, KeyframeAnimations.posVec(0.0F, 0.0F, -1.0F), Interpolations.CATMULLROM),
               new Keyframe(2.4F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(2.75F, KeyframeAnimations.posVec(0.0F, 0.0F, 2.0F), Interpolations.CATMULLROM),
               new Keyframe(2.85F, KeyframeAnimations.posVec(0.0F, 0.0F, -1.0F), Interpolations.CATMULLROM),
               new Keyframe(3.2F, KeyframeAnimations.posVec(0.0F, 0.0F, -1.0F), Interpolations.CATMULLROM),
               new Keyframe(3.8F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "right_arm",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(-82.3201F, -12.3914F, -1.6575F), Interpolations.CATMULLROM),
               new Keyframe(1.05F, KeyframeAnimations.degreeVec(-82.3856F, 9.9136F, 1.3184F), Interpolations.CATMULLROM),
               new Keyframe(1.15F, KeyframeAnimations.degreeVec(-97.3856F, 9.9136F, 1.3184F), Interpolations.CATMULLROM),
               new Keyframe(1.35F, KeyframeAnimations.degreeVec(-97.3856F, 9.9136F, 1.3184F), Interpolations.CATMULLROM),
               new Keyframe(1.55F, KeyframeAnimations.degreeVec(-82.3201F, -12.3914F, -1.6575F), Interpolations.CATMULLROM),
               new Keyframe(1.9F, KeyframeAnimations.degreeVec(-82.3856F, 9.9136F, 1.3184F), Interpolations.CATMULLROM),
               new Keyframe(2.0F, KeyframeAnimations.degreeVec(-97.3856F, 9.9136F, 1.3184F), Interpolations.CATMULLROM),
               new Keyframe(2.2F, KeyframeAnimations.degreeVec(-97.3856F, 9.9136F, 1.3184F), Interpolations.CATMULLROM),
               new Keyframe(2.4F, KeyframeAnimations.degreeVec(-82.3201F, -12.3914F, -1.6575F), Interpolations.CATMULLROM),
               new Keyframe(2.75F, KeyframeAnimations.degreeVec(-82.3856F, 9.9136F, 1.3184F), Interpolations.CATMULLROM),
               new Keyframe(2.85F, KeyframeAnimations.degreeVec(-97.3856F, 9.9136F, 1.3184F), Interpolations.CATMULLROM),
               new Keyframe(3.2F, KeyframeAnimations.degreeVec(-97.3856F, 9.9136F, 1.3184F), Interpolations.CATMULLROM),
               new Keyframe(3.8F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "right_hand",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 37.5F), Interpolations.CATMULLROM),
               new Keyframe(1.35F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 37.5F), Interpolations.CATMULLROM),
               new Keyframe(1.55F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 37.5F), Interpolations.CATMULLROM),
               new Keyframe(2.2F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 37.5F), Interpolations.CATMULLROM),
               new Keyframe(2.4F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 37.5F), Interpolations.CATMULLROM),
               new Keyframe(3.2F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 37.5F), Interpolations.CATMULLROM),
               new Keyframe(3.8F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "head",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.4F, KeyframeAnimations.degreeVec(-7.3301F, 39.0761F, -18.54F), Interpolations.CATMULLROM),
               new Keyframe(1.05F, KeyframeAnimations.degreeVec(-7.3301F, 39.0761F, -18.54F), Interpolations.CATMULLROM),
               new Keyframe(1.15F, KeyframeAnimations.degreeVec(3.1988F, 49.061F, 5.9363F), Interpolations.CATMULLROM),
               new Keyframe(1.35F, KeyframeAnimations.degreeVec(3.1988F, 49.061F, 5.9363F), Interpolations.CATMULLROM),
               new Keyframe(1.7F, KeyframeAnimations.degreeVec(-7.3301F, 39.0761F, -18.54F), Interpolations.CATMULLROM),
               new Keyframe(1.9F, KeyframeAnimations.degreeVec(-7.3301F, 39.0761F, -18.54F), Interpolations.CATMULLROM),
               new Keyframe(2.0F, KeyframeAnimations.degreeVec(3.1988F, 49.061F, 5.9363F), Interpolations.CATMULLROM),
               new Keyframe(2.2F, KeyframeAnimations.degreeVec(3.1988F, 49.061F, 5.9363F), Interpolations.CATMULLROM),
               new Keyframe(2.55F, KeyframeAnimations.degreeVec(-7.3301F, 39.0761F, -18.54F), Interpolations.CATMULLROM),
               new Keyframe(2.75F, KeyframeAnimations.degreeVec(-7.3301F, 39.0761F, -18.54F), Interpolations.CATMULLROM),
               new Keyframe(2.85F, KeyframeAnimations.degreeVec(3.1988F, 49.061F, 5.9363F), Interpolations.CATMULLROM),
               new Keyframe(3.2F, KeyframeAnimations.degreeVec(3.1988F, 49.061F, 5.9363F), Interpolations.CATMULLROM),
               new Keyframe(3.8F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "left_leg",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.3F, KeyframeAnimations.degreeVec(7.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(7.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(3.2F, KeyframeAnimations.degreeVec(7.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(3.8F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "left_leg",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.15F, KeyframeAnimations.posVec(0.0F, 2.0F, 0.85F), Interpolations.CATMULLROM),
               new Keyframe(0.3F, KeyframeAnimations.posVec(0.0F, 0.0F, 1.3F), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 1.3F), Interpolations.CATMULLROM),
               new Keyframe(3.2F, KeyframeAnimations.posVec(0.0F, 0.0F, 1.3F), Interpolations.CATMULLROM),
               new Keyframe(3.8F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "right_leg",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.4F, KeyframeAnimations.degreeVec(-8.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(3.2F, KeyframeAnimations.degreeVec(-8.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(3.6F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(3.8F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "right_leg",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.4F, KeyframeAnimations.posVec(0.0F, 0.0F, -1.3F), Interpolations.CATMULLROM),
               new Keyframe(3.2F, KeyframeAnimations.posVec(0.0F, 0.0F, -1.3F), Interpolations.CATMULLROM),
               new Keyframe(3.4F, KeyframeAnimations.posVec(0.0F, 2.0F, -0.65F), Interpolations.CATMULLROM),
               new Keyframe(3.6F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(3.8F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .build();
   public static final AnimationDefinition MAGIC = Builder.withLength(1.85F)
      .addAnimation(
         "body",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.55F, KeyframeAnimations.degreeVec(27.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.8F, KeyframeAnimations.degreeVec(-22.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.3F, KeyframeAnimations.degreeVec(-22.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.85F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "left_arm",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.55F, KeyframeAnimations.degreeVec(-61.9332F, 11.0686F, 5.8448F), Interpolations.CATMULLROM),
               new Keyframe(0.8F, KeyframeAnimations.degreeVec(-206.9332F, 11.0686F, 5.8448F), Interpolations.CATMULLROM),
               new Keyframe(1.3F, KeyframeAnimations.degreeVec(-206.9332F, 11.0686F, 5.8448F), Interpolations.CATMULLROM),
               new Keyframe(1.85F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "right_arm",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.55F, KeyframeAnimations.degreeVec(-61.9332F, -11.0686F, -5.8448F), Interpolations.CATMULLROM),
               new Keyframe(0.8F, KeyframeAnimations.degreeVec(-206.9332F, -11.0686F, -5.8448F), Interpolations.CATMULLROM),
               new Keyframe(1.3F, KeyframeAnimations.degreeVec(-206.9332F, -11.0686F, -5.8448F), Interpolations.CATMULLROM),
               new Keyframe(1.85F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .build();
   public static final AnimationDefinition BLOCK = Builder.withLength(0.5F)
      .addAnimation(
         "body",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.05F, KeyframeAnimations.degreeVec(-15.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(-15.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "left_arm",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.05F, KeyframeAnimations.degreeVec(-105.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(-105.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "right_arm",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.05F, KeyframeAnimations.degreeVec(-82.6507F, -11.5349F, 67.0099F), Interpolations.CATMULLROM),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(-82.6507F, -11.5349F, 67.0099F), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "right_hand",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.05F, KeyframeAnimations.degreeVec(-1.507F, 4.768F, -77.5627F), Interpolations.CATMULLROM),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(-1.507F, 4.768F, -77.5627F), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .build();
   public static final AnimationDefinition EAT = Builder.withLength(2.75F)
      .addAnimation(
         "left_arm",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(-74.449F, 34.9618F, 1.7494F), Interpolations.CATMULLROM),
               new Keyframe(0.4F, KeyframeAnimations.degreeVec(-64.449F, 34.9618F, 1.7494F), Interpolations.CATMULLROM),
               new Keyframe(0.55F, KeyframeAnimations.degreeVec(-74.449F, 34.9618F, 1.7494F), Interpolations.CATMULLROM),
               new Keyframe(0.7F, KeyframeAnimations.degreeVec(-64.449F, 34.9618F, 1.7494F), Interpolations.CATMULLROM),
               new Keyframe(0.85F, KeyframeAnimations.degreeVec(-74.449F, 34.9618F, 1.7494F), Interpolations.CATMULLROM),
               new Keyframe(1.0F, KeyframeAnimations.degreeVec(-64.449F, 34.9618F, 1.7494F), Interpolations.CATMULLROM),
               new Keyframe(1.15F, KeyframeAnimations.degreeVec(-74.449F, 34.9618F, 1.7494F), Interpolations.CATMULLROM),
               new Keyframe(1.3F, KeyframeAnimations.degreeVec(-64.449F, 34.9618F, 1.7494F), Interpolations.CATMULLROM),
               new Keyframe(1.45F, KeyframeAnimations.degreeVec(-74.449F, 34.9618F, 1.7494F), Interpolations.CATMULLROM),
               new Keyframe(1.6F, KeyframeAnimations.degreeVec(-64.449F, 34.9618F, 1.7494F), Interpolations.CATMULLROM),
               new Keyframe(1.75F, KeyframeAnimations.degreeVec(-74.449F, 34.9618F, 1.7494F), Interpolations.CATMULLROM),
               new Keyframe(1.9F, KeyframeAnimations.degreeVec(-64.449F, 34.9618F, 1.7494F), Interpolations.CATMULLROM),
               new Keyframe(2.05F, KeyframeAnimations.degreeVec(-74.449F, 34.9618F, 1.7494F), Interpolations.CATMULLROM),
               new Keyframe(2.2F, KeyframeAnimations.degreeVec(-64.449F, 34.9618F, 1.7494F), Interpolations.CATMULLROM),
               new Keyframe(2.35F, KeyframeAnimations.degreeVec(-74.449F, 34.9618F, 1.7494F), Interpolations.CATMULLROM),
               new Keyframe(2.75F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "head",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.25F, KeyframeAnimations.degreeVec(15.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.0F, KeyframeAnimations.degreeVec(15.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(2.4F, KeyframeAnimations.degreeVec(15.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(2.75F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .build();
   public static final AnimationDefinition EAT_FAIL = Builder.withLength(1.1F)
      .addAnimation(
         "left_arm",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(-74.449F, 34.9618F, 1.7494F), Interpolations.CATMULLROM),
               new Keyframe(0.15F, KeyframeAnimations.degreeVec(-234.449F, 34.9618F, 1.7494F), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(-234.449F, 34.9618F, 1.7494F), Interpolations.CATMULLROM),
               new Keyframe(0.95F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "head",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(15.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.15F, KeyframeAnimations.degreeVec(-40.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(-40.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.8F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.1F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "body",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.15F, KeyframeAnimations.degreeVec(-12.5F, -22.5F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(-12.5F, -22.5F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.8F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "right_arm",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.15F, KeyframeAnimations.degreeVec(-32.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.5F, KeyframeAnimations.degreeVec(-32.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.8F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.1F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .build();
}
