package net.jadenxgamer.netherexp.client.rendering.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.client.rendering.JNERenderType;
import net.jadenxgamer.netherexp.core.entity.Apparition;
import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.animation.AnimationChannel.Interpolations;
import net.minecraft.client.animation.AnimationChannel.Targets;
import net.minecraft.client.animation.AnimationDefinition.Builder;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ApparitionRenderer extends MobRenderer<Apparition, ApparitionRenderer.ApparitionModel<Apparition>> {
   public ApparitionRenderer(Context context) {
      super(context, new ApparitionRenderer.ApparitionModel(context.bakeLayer(ApparitionRenderer.ApparitionModel.LAYER)), 0.8F);
   }

   @NotNull
   public ResourceLocation getTextureLocation(Apparition entity) {
      return NetherExp.netherexpPath("textures/entity/apparition.png");
   }

   @Nullable
   protected RenderType getRenderType(Apparition entity, boolean bodyVisible, boolean translucent, boolean glowing) {
      return JNERenderType.noShadeEntityCutoutNoCull(this.getTextureLocation(entity));
   }

   protected int getBlockLightLevel(Apparition entity, BlockPos pos) {
      return 15;
   }

   private static class Animation {
      public static final AnimationDefinition IDLE1 = Builder.withLength(2.0F)
         .looping()
         .addAnimation(
            "body",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(7.5F, 0.0F, -7.5F), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.degreeVec(-7.5F, 0.0F, -7.5F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.degreeVec(-7.5F, 0.0F, 7.5F), Interpolations.CATMULLROM),
                  new Keyframe(1.5F, KeyframeAnimations.degreeVec(7.5F, 0.0F, 7.5F), Interpolations.CATMULLROM),
                  new Keyframe(2.0F, KeyframeAnimations.degreeVec(7.5F, 0.0F, -7.5F), Interpolations.LINEAR)
               }
            )
         )
         .addAnimation(
            "left_arm",
            new AnimationChannel(
               Targets.POSITION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.posVec(2.0F, -3.0F, -7.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.posVec(2.0F, 0.0F, -3.0F), Interpolations.CATMULLROM),
                  new Keyframe(2.0F, KeyframeAnimations.posVec(2.0F, -3.0F, -7.0F), Interpolations.LINEAR)
               }
            )
         )
         .addAnimation(
            "left_arm",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(41.67F, 65.8F, 46.66F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.degreeVec(-32.82F, 59.81F, -36.73F), Interpolations.CATMULLROM),
                  new Keyframe(2.0F, KeyframeAnimations.degreeVec(41.67F, 65.8F, 46.66F), Interpolations.LINEAR)
               }
            )
         )
         .addAnimation(
            "right_arm",
            new AnimationChannel(
               Targets.POSITION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.posVec(-2.0F, 0.0F, -3.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.posVec(-2.0F, -3.0F, -7.0F), Interpolations.CATMULLROM),
                  new Keyframe(2.0F, KeyframeAnimations.posVec(-2.0F, 0.0F, -3.0F), Interpolations.LINEAR)
               }
            )
         )
         .addAnimation(
            "right_arm",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(-32.82F, -59.81F, 36.73F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.degreeVec(41.67F, -65.8F, -46.66F), Interpolations.CATMULLROM),
                  new Keyframe(2.0F, KeyframeAnimations.degreeVec(-32.82F, -59.81F, 36.73F), Interpolations.LINEAR)
               }
            )
         )
         .addAnimation(
            "main",
            new AnimationChannel(
               Targets.POSITION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 3.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 1.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 3.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.41667F, KeyframeAnimations.posVec(0.0F, 1.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 3.0F, 0.0F), Interpolations.LINEAR)
               }
            )
         )
         .addAnimation(
            "flame",
            new AnimationChannel(
               Targets.SCALE,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR),
                  new Keyframe(0.5F, KeyframeAnimations.scaleVec(1.0, 1.4F, 1.0), Interpolations.LINEAR),
                  new Keyframe(1.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR),
                  new Keyframe(1.5F, KeyframeAnimations.scaleVec(1.0, 1.4F, 1.0), Interpolations.LINEAR),
                  new Keyframe(2.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR)
               }
            )
         )
         .addAnimation(
            "leg",
            new AnimationChannel(
               Targets.SCALE,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR),
                  new Keyframe(0.5F, KeyframeAnimations.scaleVec(1.0, 1.2F, 1.0), Interpolations.LINEAR),
                  new Keyframe(1.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR),
                  new Keyframe(1.5F, KeyframeAnimations.scaleVec(1.0, 1.2F, 1.0), Interpolations.LINEAR),
                  new Keyframe(2.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR)
               }
            )
         )
         .addAnimation(
            "left_chain",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 32.5F), Interpolations.LINEAR),
                  new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 10.0F), Interpolations.LINEAR),
                  new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -7.5F), Interpolations.LINEAR),
                  new Keyframe(1.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -7.5F), Interpolations.LINEAR),
                  new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 32.5F), Interpolations.LINEAR)
               }
            )
         )
         .addAnimation(
            "lower_left_chain",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 37.5F), Interpolations.LINEAR),
                  new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -15.0F), Interpolations.LINEAR),
                  new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -47.5F), Interpolations.LINEAR),
                  new Keyframe(1.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -46.25F), Interpolations.LINEAR),
                  new Keyframe(1.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -35.0F), Interpolations.LINEAR),
                  new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 37.5F), Interpolations.LINEAR)
               }
            )
         )
         .addAnimation(
            "right_chain",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 32.5F), Interpolations.LINEAR),
                  new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 10.0F), Interpolations.LINEAR),
                  new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -7.5F), Interpolations.LINEAR),
                  new Keyframe(1.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -7.5F), Interpolations.LINEAR),
                  new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 32.5F), Interpolations.LINEAR)
               }
            )
         )
         .addAnimation(
            "lower_right_chain",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 37.5F), Interpolations.LINEAR),
                  new Keyframe(0.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 29.38F), Interpolations.LINEAR),
                  new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -15.0F), Interpolations.LINEAR),
                  new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -47.5F), Interpolations.LINEAR),
                  new Keyframe(1.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -35.0F), Interpolations.LINEAR),
                  new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 37.5F), Interpolations.LINEAR)
               }
            )
         )
         .build();
      public static final AnimationDefinition MOVE = Builder.withLength(2.0F)
         .looping()
         .addAnimation(
            "body",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(7.5F, 0.0F, -7.5F), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.degreeVec(-7.5F, 0.0F, -7.5F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.degreeVec(-7.5F, 0.0F, 7.5F), Interpolations.CATMULLROM),
                  new Keyframe(1.5F, KeyframeAnimations.degreeVec(7.5F, 0.0F, 7.5F), Interpolations.CATMULLROM),
                  new Keyframe(2.0F, KeyframeAnimations.degreeVec(7.5F, 0.0F, -7.5F), Interpolations.LINEAR)
               }
            )
         )
         .addAnimation(
            "main",
            new AnimationChannel(
               Targets.POSITION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 3.0F, 2.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 5.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 3.0F, 2.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.4167666F, KeyframeAnimations.posVec(0.0F, 5.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 3.0F, 2.0F), Interpolations.LINEAR)
               }
            )
         )
         .addAnimation(
            "main",
            new AnimationChannel(Targets.ROTATION, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.degreeVec(15.0F, 0.0F, 0.0F), Interpolations.LINEAR)})
         )
         .addAnimation(
            "flame",
            new AnimationChannel(
               Targets.SCALE,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR),
                  new Keyframe(0.5F, KeyframeAnimations.scaleVec(1.0, 1.4F, 1.0), Interpolations.LINEAR),
                  new Keyframe(1.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR),
                  new Keyframe(1.5F, KeyframeAnimations.scaleVec(1.0, 1.4F, 1.0), Interpolations.LINEAR),
                  new Keyframe(2.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR)
               }
            )
         )
         .addAnimation(
            "leg",
            new AnimationChannel(
               Targets.SCALE,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR),
                  new Keyframe(0.5F, KeyframeAnimations.scaleVec(1.0, 1.2F, 1.0), Interpolations.LINEAR),
                  new Keyframe(1.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR),
                  new Keyframe(1.5F, KeyframeAnimations.scaleVec(1.0, 1.2F, 1.0), Interpolations.LINEAR),
                  new Keyframe(2.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR)
               }
            )
         )
         .build();
      public static final AnimationDefinition IDLE2 = Builder.withLength(2.0F)
         .looping()
         .addAnimation(
            "main",
            new AnimationChannel(
               Targets.POSITION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 5.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 3.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 5.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.41667F, KeyframeAnimations.posVec(0.0F, 3.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 5.0F, 0.0F), Interpolations.LINEAR)
               }
            )
         )
         .addAnimation(
            "main",
            new AnimationChannel(Targets.ROTATION, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.degreeVec(7.5F, 0.0F, 0.0F), Interpolations.LINEAR)})
         )
         .addAnimation(
            "right_arm",
            new AnimationChannel(
               Targets.POSITION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.posVec(-2.0F, 0.0F, -6.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.posVec(-2.0F, -2.0F, -6.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.posVec(-2.0F, 0.0F, -6.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.5F, KeyframeAnimations.posVec(-2.0F, -2.0F, -6.0F), Interpolations.CATMULLROM),
                  new Keyframe(2.0F, KeyframeAnimations.posVec(-2.0F, 0.0F, -6.0F), Interpolations.LINEAR)
               }
            )
         )
         .addAnimation(
            "right_arm",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(90.0F, -55.0F, -90.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.degreeVec(63.97F, -50.33F, -57.6F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.degreeVec(90.0F, -55.0F, -90.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.5F, KeyframeAnimations.degreeVec(116.03F, -50.33F, -122.4F), Interpolations.CATMULLROM),
                  new Keyframe(2.0F, KeyframeAnimations.degreeVec(90.0F, -55.0F, -90.0F), Interpolations.LINEAR)
               }
            )
         )
         .addAnimation(
            "left_arm",
            new AnimationChannel(
               Targets.POSITION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.posVec(2.0F, 0.0F, -6.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.posVec(2.0F, -2.0F, -6.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.posVec(2.0F, 0.0F, -6.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.5F, KeyframeAnimations.posVec(2.0F, -2.0F, -6.0F), Interpolations.CATMULLROM),
                  new Keyframe(2.0F, KeyframeAnimations.posVec(2.0F, 0.0F, -6.0F), Interpolations.LINEAR)
               }
            )
         )
         .addAnimation(
            "left_arm",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(90.0F, 55.0F, 90.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.degreeVec(116.03F, 50.33F, 122.4F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.degreeVec(90.0F, 55.0F, 90.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.5F, KeyframeAnimations.degreeVec(63.97F, 50.33F, 57.6F), Interpolations.CATMULLROM),
                  new Keyframe(2.0F, KeyframeAnimations.degreeVec(90.0F, 55.0F, 90.0F), Interpolations.LINEAR)
               }
            )
         )
         .addAnimation(
            "body",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(7.5F, 0.0F, -7.5F), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.degreeVec(-7.5F, 0.0F, -7.5F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.degreeVec(-7.5F, 0.0F, 7.5F), Interpolations.CATMULLROM),
                  new Keyframe(1.5F, KeyframeAnimations.degreeVec(7.5F, 0.0F, 7.5F), Interpolations.CATMULLROM),
                  new Keyframe(2.0F, KeyframeAnimations.degreeVec(7.5F, 0.0F, -7.5F), Interpolations.LINEAR)
               }
            )
         )
         .addAnimation(
            "flame",
            new AnimationChannel(
               Targets.SCALE,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR),
                  new Keyframe(0.5F, KeyframeAnimations.scaleVec(1.0, 1.4F, 1.0), Interpolations.LINEAR),
                  new Keyframe(1.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR),
                  new Keyframe(1.5F, KeyframeAnimations.scaleVec(1.0, 1.4F, 1.0), Interpolations.LINEAR),
                  new Keyframe(2.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR)
               }
            )
         )
         .addAnimation(
            "leg",
            new AnimationChannel(
               Targets.SCALE,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR),
                  new Keyframe(0.5F, KeyframeAnimations.scaleVec(1.0, 1.2F, 1.0), Interpolations.LINEAR),
                  new Keyframe(1.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR),
                  new Keyframe(1.5F, KeyframeAnimations.scaleVec(1.0, 1.2F, 1.0), Interpolations.LINEAR),
                  new Keyframe(2.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR)
               }
            )
         )
         .addAnimation(
            "right_chain",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.54167F, KeyframeAnimations.degreeVec(-22.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.04167F, KeyframeAnimations.degreeVec(-5.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.54167F, KeyframeAnimations.degreeVec(22.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
               }
            )
         )
         .addAnimation(
            "lower_right_chain",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(9.28F, -8.41F, 41.82F), Interpolations.CATMULLROM),
                  new Keyframe(0.58333F, KeyframeAnimations.degreeVec(-36.55F, -9.07F, -12.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.08333F, KeyframeAnimations.degreeVec(-3.97F, 3.04F, 37.39F), Interpolations.CATMULLROM),
                  new Keyframe(1.58333F, KeyframeAnimations.degreeVec(36.55F, 9.07F, -12.0F), Interpolations.CATMULLROM),
                  new Keyframe(2.0F, KeyframeAnimations.degreeVec(9.28F, -8.41F, 41.82F), Interpolations.LINEAR)
               }
            )
         )
         .addAnimation(
            "left_chain",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.54167F, KeyframeAnimations.degreeVec(22.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.04167F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.54167F, KeyframeAnimations.degreeVec(-22.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
               }
            )
         )
         .addAnimation(
            "lower_left_chain",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(9.28F, 8.41F, -41.82F), Interpolations.CATMULLROM),
                  new Keyframe(0.625F, KeyframeAnimations.degreeVec(36.55F, -9.07F, 12.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.08333F, KeyframeAnimations.degreeVec(3.97F, 3.04F, -37.39F), Interpolations.CATMULLROM),
                  new Keyframe(1.58333F, KeyframeAnimations.degreeVec(-36.55F, 9.07F, 12.0F), Interpolations.CATMULLROM),
                  new Keyframe(2.0F, KeyframeAnimations.degreeVec(9.28F, 8.41F, -41.82F), Interpolations.LINEAR)
               }
            )
         )
         .build();
      public static final AnimationDefinition IDLE3 = Builder.withLength(2.0F)
         .looping()
         .addAnimation(
            "main",
            new AnimationChannel(
               Targets.POSITION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 3.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 1.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 3.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.41667F, KeyframeAnimations.posVec(0.0F, 1.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 3.0F, 0.0F), Interpolations.LINEAR)
               }
            )
         )
         .addAnimation(
            "body",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(7.5F, 0.0F, -7.5F), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.degreeVec(-7.5F, 0.0F, -7.5F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.degreeVec(-7.5F, 0.0F, 7.5F), Interpolations.CATMULLROM),
                  new Keyframe(1.5F, KeyframeAnimations.degreeVec(7.5F, 0.0F, 7.5F), Interpolations.CATMULLROM),
                  new Keyframe(2.0F, KeyframeAnimations.degreeVec(7.5F, 0.0F, -7.5F), Interpolations.LINEAR)
               }
            )
         )
         .addAnimation(
            "flame",
            new AnimationChannel(
               Targets.SCALE,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR),
                  new Keyframe(0.5F, KeyframeAnimations.scaleVec(1.0, 1.4F, 1.0), Interpolations.LINEAR),
                  new Keyframe(1.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR),
                  new Keyframe(1.5F, KeyframeAnimations.scaleVec(1.0, 1.4F, 1.0), Interpolations.LINEAR),
                  new Keyframe(2.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR)
               }
            )
         )
         .addAnimation(
            "leg",
            new AnimationChannel(
               Targets.SCALE,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR),
                  new Keyframe(0.5F, KeyframeAnimations.scaleVec(1.0, 1.2F, 1.0), Interpolations.LINEAR),
                  new Keyframe(1.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR),
                  new Keyframe(1.5F, KeyframeAnimations.scaleVec(1.0, 1.2F, 1.0), Interpolations.LINEAR),
                  new Keyframe(2.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR)
               }
            )
         )
         .addAnimation(
            "right_arm",
            new AnimationChannel(Targets.POSITION, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.posVec(-1.0F, 0.0F, -7.0F), Interpolations.LINEAR)})
         )
         .addAnimation(
            "right_arm",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, -57.5F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.degreeVec(-45.49F, -63.09F, 54.75F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, -57.5F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.5F, KeyframeAnimations.degreeVec(-45.49F, -63.09F, 54.75F), Interpolations.CATMULLROM),
                  new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, -57.5F, 0.0F), Interpolations.LINEAR)
               }
            )
         )
         .addAnimation(
            "left_arm",
            new AnimationChannel(Targets.POSITION, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.posVec(1.0F, 0.0F, -7.0F), Interpolations.LINEAR)})
         )
         .addAnimation(
            "left_arm",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 57.5F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.degreeVec(-45.49F, 63.09F, -54.75F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 57.5F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.5F, KeyframeAnimations.degreeVec(-45.49F, 63.09F, -54.75F), Interpolations.CATMULLROM),
                  new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 57.5F, 0.0F), Interpolations.LINEAR)
               }
            )
         )
         .addAnimation(
            "right_chain",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.degreeVec(17.5F, 0.0F, -17.5F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.degreeVec(15.27F, 8.65F, -46.34F), Interpolations.CATMULLROM),
                  new Keyframe(1.5F, KeyframeAnimations.degreeVec(17.5F, 0.0F, -17.5F), Interpolations.CATMULLROM),
                  new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
               }
            )
         )
         .addAnimation(
            "lower_right_chain",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -40.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.58333F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 52.5F), Interpolations.CATMULLROM),
                  new Keyframe(1.08333F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -40.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.58333F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 52.5F), Interpolations.CATMULLROM),
                  new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -40.0F), Interpolations.LINEAR)
               }
            )
         )
         .addAnimation(
            "left_chain",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.degreeVec(17.5F, 0.0F, 17.5F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.degreeVec(15.27F, -8.65F, 46.34F), Interpolations.CATMULLROM),
                  new Keyframe(1.5F, KeyframeAnimations.degreeVec(17.5F, 0.0F, 17.5F), Interpolations.CATMULLROM),
                  new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
               }
            )
         )
         .addAnimation(
            "lower_left_chain",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 40.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.58333F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -52.5F), Interpolations.CATMULLROM),
                  new Keyframe(1.08333F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 40.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.58333F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -52.5F), Interpolations.CATMULLROM),
                  new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 40.0F), Interpolations.LINEAR)
               }
            )
         )
         .build();
      public static final AnimationDefinition IDLE4 = Builder.withLength(2.0F)
         .looping()
         .addAnimation(
            "main",
            new AnimationChannel(
               Targets.POSITION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 8.0F, 4.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 5.0F, 4.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 8.0F, 4.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.41667F, KeyframeAnimations.posVec(0.0F, 5.0F, 4.0F), Interpolations.CATMULLROM),
                  new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 8.0F, 4.0F), Interpolations.LINEAR)
               }
            )
         )
         .addAnimation(
            "main",
            new AnimationChannel(Targets.ROTATION, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.degreeVec(35.0F, 0.0F, 0.0F), Interpolations.LINEAR)})
         )
         .addAnimation(
            "body",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(7.5F, 0.0F, -7.5F), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.degreeVec(-7.5F, 0.0F, -7.5F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.degreeVec(-7.5F, 0.0F, 7.5F), Interpolations.CATMULLROM),
                  new Keyframe(1.5F, KeyframeAnimations.degreeVec(7.5F, 0.0F, 7.5F), Interpolations.CATMULLROM),
                  new Keyframe(2.0F, KeyframeAnimations.degreeVec(7.5F, 0.0F, -7.5F), Interpolations.LINEAR)
               }
            )
         )
         .addAnimation(
            "flame",
            new AnimationChannel(
               Targets.SCALE,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR),
                  new Keyframe(0.5F, KeyframeAnimations.scaleVec(1.0, 1.4F, 1.0), Interpolations.LINEAR),
                  new Keyframe(1.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR),
                  new Keyframe(1.5F, KeyframeAnimations.scaleVec(1.0, 1.4F, 1.0), Interpolations.LINEAR),
                  new Keyframe(2.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR)
               }
            )
         )
         .addAnimation(
            "leg",
            new AnimationChannel(
               Targets.SCALE,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR),
                  new Keyframe(0.5F, KeyframeAnimations.scaleVec(1.0, 1.2F, 1.0), Interpolations.LINEAR),
                  new Keyframe(1.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR),
                  new Keyframe(1.5F, KeyframeAnimations.scaleVec(1.0, 1.2F, 1.0), Interpolations.LINEAR),
                  new Keyframe(2.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR)
               }
            )
         )
         .addAnimation(
            "right_arm",
            new AnimationChannel(Targets.POSITION, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.posVec(-3.0F, 0.0F, -3.0F), Interpolations.LINEAR)})
         )
         .addAnimation(
            "right_arm",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(15.41F, -40.11F, -73.15F), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.degreeVec(24.62F, -35.81F, -88.06F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.degreeVec(15.41F, -40.11F, -73.15F), Interpolations.CATMULLROM),
                  new Keyframe(1.5F, KeyframeAnimations.degreeVec(24.62F, -35.81F, -88.06F), Interpolations.CATMULLROM),
                  new Keyframe(2.0F, KeyframeAnimations.degreeVec(15.41F, -40.11F, -73.15F), Interpolations.LINEAR)
               }
            )
         )
         .addAnimation(
            "left_arm",
            new AnimationChannel(Targets.POSITION, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.posVec(3.0F, 0.0F, -3.0F), Interpolations.LINEAR)})
         )
         .addAnimation(
            "left_arm",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(15.41F, 40.11F, 73.15F), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.degreeVec(24.62F, 35.81F, 88.06F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.degreeVec(15.41F, 40.11F, 73.15F), Interpolations.CATMULLROM),
                  new Keyframe(1.5F, KeyframeAnimations.degreeVec(24.62F, 35.81F, 88.06F), Interpolations.CATMULLROM),
                  new Keyframe(2.0F, KeyframeAnimations.degreeVec(15.41F, 40.11F, 73.15F), Interpolations.LINEAR)
               }
            )
         )
         .addAnimation(
            "right_chain",
            new AnimationChannel(Targets.POSITION, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.posVec(1.0F, 2.0F, 2.0F), Interpolations.LINEAR)})
         )
         .addAnimation(
            "right_chain",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(90.0F, -45.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.degreeVec(90.0F, -22.5F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.degreeVec(90.0F, -45.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.5F, KeyframeAnimations.degreeVec(90.0F, -22.5F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(2.0F, KeyframeAnimations.degreeVec(90.0F, -45.0F, 0.0F), Interpolations.LINEAR)
               }
            )
         )
         .addAnimation(
            "lower_right_chain",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -27.5F), Interpolations.CATMULLROM),
                  new Keyframe(0.58333F, KeyframeAnimations.degreeVec(-24.25F, -6.28F, 31.35F), Interpolations.CATMULLROM),
                  new Keyframe(1.08333F, KeyframeAnimations.degreeVec(17.5F, 0.0F, -27.5F), Interpolations.CATMULLROM),
                  new Keyframe(1.58333F, KeyframeAnimations.degreeVec(-24.25F, -6.28F, 31.35F), Interpolations.CATMULLROM),
                  new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -27.5F), Interpolations.LINEAR)
               }
            )
         )
         .addAnimation(
            "left_chain",
            new AnimationChannel(Targets.POSITION, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.posVec(-1.0F, 2.0F, 2.0F), Interpolations.LINEAR)})
         )
         .addAnimation(
            "left_chain",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(90.0F, 45.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.degreeVec(90.0F, 22.5F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.degreeVec(90.0F, 45.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.5F, KeyframeAnimations.degreeVec(90.0F, 22.5F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(2.0F, KeyframeAnimations.degreeVec(90.0F, 45.0F, 0.0F), Interpolations.LINEAR)
               }
            )
         )
         .addAnimation(
            "lower_left_chain",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 27.5F), Interpolations.CATMULLROM),
                  new Keyframe(0.58333F, KeyframeAnimations.degreeVec(-24.25F, 6.28F, -31.35F), Interpolations.CATMULLROM),
                  new Keyframe(1.08333F, KeyframeAnimations.degreeVec(17.5F, 0.0F, 27.5F), Interpolations.CATMULLROM),
                  new Keyframe(1.58333F, KeyframeAnimations.degreeVec(-24.25F, 6.28F, -31.35F), Interpolations.CATMULLROM),
                  new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 27.5F), Interpolations.LINEAR)
               }
            )
         )
         .build();
      public static final AnimationDefinition ATTACK = Builder.withLength(0.45045F)
         .addAnimation(
            "right_arm",
            new AnimationChannel(
               Targets.POSITION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.11261F, KeyframeAnimations.posVec(0.0F, -3.0F, 7.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.22523F, KeyframeAnimations.posVec(0.0F, -3.0F, 7.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.33784F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "right_arm",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.05631F, KeyframeAnimations.degreeVec(12.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.16892F, KeyframeAnimations.degreeVec(-19.72F, -3.4F, -9.41F), Interpolations.CATMULLROM),
                  new Keyframe(0.22523F, KeyframeAnimations.degreeVec(-17.71F, 4.8F, 14.42F), Interpolations.CATMULLROM),
                  new Keyframe(0.33784F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "left_arm",
            new AnimationChannel(
               Targets.POSITION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.11261F, KeyframeAnimations.posVec(0.0F, -3.0F, 7.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.22523F, KeyframeAnimations.posVec(0.0F, -3.0F, 7.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.33784F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "left_arm",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.05631F, KeyframeAnimations.degreeVec(12.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.16892F, KeyframeAnimations.degreeVec(-19.72F, 3.4F, 9.41F), Interpolations.CATMULLROM),
                  new Keyframe(0.22523F, KeyframeAnimations.degreeVec(-17.71F, -4.8F, -14.42F), Interpolations.CATMULLROM),
                  new Keyframe(0.33784F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "body",
            new AnimationChannel(
               Targets.POSITION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.11261F, KeyframeAnimations.posVec(0.0F, 3.0F, -3.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.22523F, KeyframeAnimations.posVec(0.0F, 3.0F, -3.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.45045F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "body",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.05631F, KeyframeAnimations.degreeVec(12.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.16892F, KeyframeAnimations.degreeVec(-19.72F, 3.4F, 9.41F), Interpolations.CATMULLROM),
                  new Keyframe(0.22523F, KeyframeAnimations.degreeVec(-17.71F, -4.8F, -14.42F), Interpolations.CATMULLROM),
                  new Keyframe(0.33784F, KeyframeAnimations.degreeVec(-9.1F, -1.36F, 1.19F), Interpolations.CATMULLROM),
                  new Keyframe(0.45045F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .build();
   }

   public static class ApparitionModel<T extends Apparition> extends HierarchicalModel<T> {
      public static final ModelLayerLocation LAYER = new ModelLayerLocation(NetherExp.netherexpPath("apparition"), "main");
      private final ModelPart main;
      private final ModelPart right_arm;
      private final ModelPart right_chain;
      private final ModelPart lower_right_chain;
      private final ModelPart left_arm;
      private final ModelPart left_chain;
      private final ModelPart lower_left_chain;
      private final ModelPart head;
      private final ModelPart body;
      private final ModelPart flame;
      private final ModelPart leg;

      public ApparitionModel(ModelPart root) {
         this.main = root.getChild("main");
         this.right_arm = this.main.getChild("right_arm");
         this.right_chain = this.right_arm.getChild("right_chain");
         this.lower_right_chain = this.right_chain.getChild("lower_right_chain");
         this.left_arm = this.main.getChild("left_arm");
         this.left_chain = this.left_arm.getChild("left_chain");
         this.lower_left_chain = this.left_chain.getChild("lower_left_chain");
         this.head = this.main.getChild("head");
         this.body = this.head.getChild("body");
         this.flame = this.body.getChild("flame");
         this.leg = this.body.getChild("leg");
      }

      public static LayerDefinition createBodyLayer() {
         MeshDefinition meshdefinition = new MeshDefinition();
         PartDefinition partdefinition = meshdefinition.getRoot();
         PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
         PartDefinition right_arm = main.addOrReplaceChild(
            "right_arm",
            CubeListBuilder.create()
               .texOffs(56, 39)
               .addBox(-13.25F, -3.0F, -3.0F, 8.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
               .texOffs(56, 23)
               .addBox(-18.25F, -2.0F, -2.0F, 19.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)),
            PartPose.offset(-8.75F, -21.0F, 0.0F)
         );
         PartDefinition right_chain = right_arm.addOrReplaceChild(
            "right_chain",
            CubeListBuilder.create().texOffs(84, 36).addBox(0.0F, 0.0F, -1.5F, 0.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)),
            PartPose.offset(-14.25F, 2.0F, 0.0F)
         );
         PartDefinition lower_right_chain = right_chain.addOrReplaceChild(
            "lower_right_chain",
            CubeListBuilder.create().texOffs(84, 44).addBox(0.0F, 0.0F, -1.5F, 0.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)),
            PartPose.offset(0.0F, 8.0F, 0.0F)
         );
         PartDefinition left_arm = main.addOrReplaceChild(
            "left_arm",
            CubeListBuilder.create()
               .texOffs(56, 51)
               .mirror()
               .addBox(5.25F, -3.0F, -3.0F, 8.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
               .mirror(false)
               .texOffs(56, 31)
               .mirror()
               .addBox(-0.75F, -2.0F, -2.0F, 19.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
               .mirror(false),
            PartPose.offset(8.75F, -21.0F, 0.0F)
         );
         PartDefinition left_chain = left_arm.addOrReplaceChild(
            "left_chain",
            CubeListBuilder.create().texOffs(84, 36).mirror().addBox(0.0F, 0.0F, -1.5F, 0.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false),
            PartPose.offset(14.25F, 2.0F, 0.0F)
         );
         PartDefinition lower_left_chain = left_chain.addOrReplaceChild(
            "lower_left_chain",
            CubeListBuilder.create().texOffs(84, 44).mirror().addBox(0.0F, 0.0F, -1.5F, 0.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false),
            PartPose.offset(0.0F, 8.0F, 0.0F)
         );
         PartDefinition head = main.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, -19.5F, 0.0F));
         PartDefinition body = head.addOrReplaceChild(
            "body",
            CubeListBuilder.create().texOffs(0, 0).addBox(-7.0F, -11.5F, -7.0F, 14.0F, 23.0F, 14.0F, new CubeDeformation(0.0F)),
            PartPose.offset(0.0F, 0.0F, 0.0F)
         );
         PartDefinition flame = body.addOrReplaceChild(
            "flame",
            CubeListBuilder.create().texOffs(56, 0).addBox(-7.0F, -8.5F, -7.0F, 14.0F, 9.0F, 14.0F, new CubeDeformation(0.0F)),
            PartPose.offset(0.0F, -12.0F, 0.0F)
         );
         PartDefinition leg = body.addOrReplaceChild(
            "leg",
            CubeListBuilder.create().texOffs(0, 37).addBox(-7.0F, 0.0F, -7.0F, 14.0F, 8.0F, 14.0F, new CubeDeformation(0.0F)),
            PartPose.offset(0.0F, 11.5F, 0.0F)
         );
         return LayerDefinition.create(meshdefinition, 128, 128);
      }

      public void setupAnim(Apparition entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
         this.root().getAllParts().forEach(ModelPart::resetPose);
         this.animate(entity.attackAnimation, ApparitionRenderer.Animation.ATTACK, ageInTicks);
         this.animate(entity.idleAnimation, animationForPersonality(entity), ageInTicks);
         this.animateWalk(ApparitionRenderer.Animation.MOVE, limbSwing, limbSwingAmount, 2.0F, 2.5F);
      }

      public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
         this.main.render(poseStack, buffer, packedLight, packedOverlay, color);
      }

      public ModelPart root() {
         return this.main;
      }

      public static AnimationDefinition animationForPersonality(Apparition entity) {
         return switch (entity.getPersonality()) {
            case 2 -> ApparitionRenderer.Animation.IDLE2;
            case 3 -> ApparitionRenderer.Animation.IDLE3;
            case 4 -> ApparitionRenderer.Animation.IDLE4;
            default -> ApparitionRenderer.Animation.IDLE1;
         };
      }
   }
}
