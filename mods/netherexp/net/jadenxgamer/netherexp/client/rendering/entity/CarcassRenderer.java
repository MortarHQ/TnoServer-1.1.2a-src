package net.jadenxgamer.netherexp.client.rendering.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.core.entity.Carcass;
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
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class CarcassRenderer extends MobRenderer<Carcass, CarcassRenderer.CarcassModel<Carcass>> {
   public CarcassRenderer(Context context) {
      super(context, new CarcassRenderer.CarcassModel(context.bakeLayer(CarcassRenderer.CarcassModel.LAYER)), 0.5F);
      this.addLayer(new CarcassRenderer.CarcassGlowLayer(this));
   }

   @NotNull
   public ResourceLocation getTextureLocation(Carcass entity) {
      return entity.isReanimated()
         ? NetherExp.netherexpPath("textures/entity/carcass/carcass_reanimated.png")
         : NetherExp.netherexpPath("textures/entity/carcass/carcass.png");
   }

   private static class Animation {
      public static final AnimationDefinition CARCASS_IDLE = Builder.withLength(2.0F)
         .looping()
         .addAnimation(
            "carcass",
            new AnimationChannel(Targets.POSITION, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -5.0F, 5.0F), Interpolations.LINEAR)})
         )
         .addAnimation(
            "carcass",
            new AnimationChannel(Targets.ROTATION, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.degreeVec(20.0F, 0.0F, 0.0F), Interpolations.LINEAR)})
         )
         .addAnimation(
            "head",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(-12.45F, -1.08F, -4.88F), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.degreeVec(-7.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.degreeVec(-12.45F, 1.08F, 4.88F), Interpolations.CATMULLROM),
                  new Keyframe(1.5F, KeyframeAnimations.degreeVec(-7.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(2.0F, KeyframeAnimations.degreeVec(-12.45F, -1.08F, -4.88F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "body",
            new AnimationChannel(
               Targets.POSITION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.5834334F, KeyframeAnimations.posVec(0.0F, 1.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.0834333F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.5834333F, KeyframeAnimations.posVec(0.0F, 1.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "body",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(20.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.5834334F, KeyframeAnimations.degreeVec(12.45F, 1.08F, -4.88F), Interpolations.CATMULLROM),
                  new Keyframe(1.0834333F, KeyframeAnimations.degreeVec(20.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.5834333F, KeyframeAnimations.degreeVec(12.45F, -1.08F, 4.88F), Interpolations.CATMULLROM),
                  new Keyframe(2.0F, KeyframeAnimations.degreeVec(20.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "left_arm",
            new AnimationChannel(
               Targets.POSITION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 1.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.5F, KeyframeAnimations.posVec(0.0F, 1.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "left_arm",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(14.84F, 58.71F, 19.23F), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.degreeVec(22.41F, 57.11F, 28.15F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.degreeVec(14.84F, 58.71F, 19.23F), Interpolations.CATMULLROM),
                  new Keyframe(1.5F, KeyframeAnimations.degreeVec(22.41F, 57.11F, 28.15F), Interpolations.CATMULLROM),
                  new Keyframe(2.0F, KeyframeAnimations.degreeVec(14.84F, 58.71F, 19.23F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "right_arm",
            new AnimationChannel(
               Targets.POSITION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 1.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.5F, KeyframeAnimations.posVec(0.0F, 1.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "right_arm",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(14.84F, -58.71F, -19.23F), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.degreeVec(22.41F, -57.11F, -28.15F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.degreeVec(14.84F, -58.71F, -19.23F), Interpolations.CATMULLROM),
                  new Keyframe(1.5F, KeyframeAnimations.degreeVec(22.41F, -57.11F, -28.15F), Interpolations.CATMULLROM),
                  new Keyframe(2.0F, KeyframeAnimations.degreeVec(14.84F, -58.71F, -19.23F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "fire",
            new AnimationChannel(
               Targets.POSITION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "fire",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 10.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -13.44F), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -5.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.75F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 13.97F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 10.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -13.44F), Interpolations.CATMULLROM),
                  new Keyframe(1.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -5.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.75F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 13.97F), Interpolations.CATMULLROM),
                  new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 10.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "fire",
            new AnimationChannel(
               Targets.SCALE,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.4F, 1.3F, 1.2F), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.scaleVec(1.4F, 0.9F, 1.0), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.scaleVec(1.0, 1.2F, 1.0), Interpolations.CATMULLROM),
                  new Keyframe(1.5F, KeyframeAnimations.scaleVec(1.4F, 0.9F, 1.0), Interpolations.CATMULLROM),
                  new Keyframe(2.0F, KeyframeAnimations.scaleVec(1.4F, 1.3F, 1.2F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "eyes",
            new AnimationChannel(
               Targets.POSITION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
                  new Keyframe(0.08343333F, KeyframeAnimations.posVec(0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
                  new Keyframe(0.16766666F, KeyframeAnimations.posVec(-0.25F, -0.25F, 0.0F), Interpolations.LINEAR),
                  new Keyframe(0.25F, KeyframeAnimations.posVec(0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
                  new Keyframe(0.3433333F, KeyframeAnimations.posVec(-0.25F, 0.25F, 0.0F), Interpolations.LINEAR),
                  new Keyframe(0.4167667F, KeyframeAnimations.posVec(-0.25F, -0.25F, 0.0F), Interpolations.LINEAR),
                  new Keyframe(0.5F, KeyframeAnimations.posVec(0.25F, 0.25F, 0.0F), Interpolations.LINEAR),
                  new Keyframe(0.5834334F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
                  new Keyframe(0.6766666F, KeyframeAnimations.posVec(0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
                  new Keyframe(0.75F, KeyframeAnimations.posVec(-0.25F, -0.25F, 0.0F), Interpolations.LINEAR),
                  new Keyframe(0.8343334F, KeyframeAnimations.posVec(0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
                  new Keyframe(0.9167666F, KeyframeAnimations.posVec(-0.25F, 0.25F, 0.0F), Interpolations.LINEAR),
                  new Keyframe(1.0F, KeyframeAnimations.posVec(-0.25F, -0.25F, 0.0F), Interpolations.LINEAR),
                  new Keyframe(1.0834333F, KeyframeAnimations.posVec(0.25F, 0.25F, 0.0F), Interpolations.LINEAR),
                  new Keyframe(1.1676667F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
                  new Keyframe(1.25F, KeyframeAnimations.posVec(0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
                  new Keyframe(1.3433332F, KeyframeAnimations.posVec(-0.25F, -0.25F, 0.0F), Interpolations.LINEAR),
                  new Keyframe(1.4167666F, KeyframeAnimations.posVec(0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
                  new Keyframe(1.5F, KeyframeAnimations.posVec(-0.25F, 0.25F, 0.0F), Interpolations.LINEAR),
                  new Keyframe(1.5834333F, KeyframeAnimations.posVec(-0.25F, -0.25F, 0.0F), Interpolations.LINEAR),
                  new Keyframe(1.6766667F, KeyframeAnimations.posVec(0.25F, 0.25F, 0.0F), Interpolations.LINEAR),
                  new Keyframe(1.75F, KeyframeAnimations.posVec(-0.25F, 0.25F, 0.0F), Interpolations.LINEAR),
                  new Keyframe(1.8343333F, KeyframeAnimations.posVec(-0.25F, -0.25F, 0.0F), Interpolations.LINEAR),
                  new Keyframe(1.9167666F, KeyframeAnimations.posVec(0.25F, 0.25F, 0.0F), Interpolations.LINEAR),
                  new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
               }
            )
         )
         .build();
      public static final AnimationDefinition CARCASS_DEACTIVATE = Builder.withLength(1.25F)
         .addAnimation(
            "carcass",
            new AnimationChannel(
               Targets.POSITION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -5.0F, 5.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, -2.0F, 5.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "carcass",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(20.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.degreeVec(47.5F, 0.0F, 0.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "head",
            new AnimationChannel(
               Targets.POSITION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.2916767F, KeyframeAnimations.posVec(0.0F, 0.29F, 4.37F), Interpolations.CATMULLROM),
                  new Keyframe(0.4167667F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.0834333F, KeyframeAnimations.posVec(0.0F, -4.0F, -5.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "head",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(-12.45F, -1.08F, -4.88F), Interpolations.CATMULLROM),
                  new Keyframe(0.25F, KeyframeAnimations.degreeVec(-57.06F, 15.55F, 9.09F), Interpolations.CATMULLROM),
                  new Keyframe(0.4167667F, KeyframeAnimations.degreeVec(-69.95F, -1.08F, -4.88F), Interpolations.CATMULLROM),
                  new Keyframe(0.5834334F, KeyframeAnimations.degreeVec(-13.3F, -6.81F, 19.07F), Interpolations.CATMULLROM),
                  new Keyframe(0.75F, KeyframeAnimations.degreeVec(62.69F, -36.72F, -57.49F), Interpolations.CATMULLROM),
                  new Keyframe(0.875F, KeyframeAnimations.degreeVec(49.02F, -70.68F, -55.66F), Interpolations.CATMULLROM),
                  new Keyframe(1.0834333F, KeyframeAnimations.degreeVec(69.32F, -55.7F, -86.43F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "body",
            new AnimationChannel(
               Targets.POSITION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, 0.19F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, -4.0F, 0.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "body",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(20.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.25F, KeyframeAnimations.degreeVec(-2.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.2916767F, KeyframeAnimations.degreeVec(-17.83F, 2.45F, 0.46F), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.degreeVec(6.83F, 18.56F, 3.49F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.degreeVec(20.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "left_arm",
            new AnimationChannel(
               Targets.POSITION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, 4.19F, -1.02F), Interpolations.CATMULLROM),
                  new Keyframe(0.4167667F, KeyframeAnimations.posVec(0.0F, 5.12F, -2.8F), Interpolations.CATMULLROM),
                  new Keyframe(0.6766666F, KeyframeAnimations.posVec(0.0F, -4.0F, -5.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "left_arm",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(14.84F, 58.71F, 19.23F), Interpolations.CATMULLROM),
                  new Keyframe(0.25F, KeyframeAnimations.degreeVec(29.27F, 47.45F, 44.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.6766666F, KeyframeAnimations.degreeVec(-63.79F, 34.74F, -49.17F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "right_arm",
            new AnimationChannel(
               Targets.POSITION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.16766666F, KeyframeAnimations.posVec(0.0F, 3.91F, -0.44F), Interpolations.CATMULLROM),
                  new Keyframe(0.375F, KeyframeAnimations.posVec(0.0F, 4.19F, -1.02F), Interpolations.CATMULLROM),
                  new Keyframe(0.6766666F, KeyframeAnimations.posVec(0.0F, 2.19F, -1.02F), Interpolations.CATMULLROM),
                  new Keyframe(0.8343334F, KeyframeAnimations.posVec(0.0F, -1.19F, -1.02F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, -4.0F, -5.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "right_arm",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(14.84F, -58.71F, -19.23F), Interpolations.CATMULLROM),
                  new Keyframe(0.375F, KeyframeAnimations.degreeVec(29.27F, -47.45F, -44.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.6766666F, KeyframeAnimations.degreeVec(5.69F, -53.64F, -13.78F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.degreeVec(-63.79F, -34.74F, 49.17F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "fire",
            new AnimationChannel(Targets.POSITION, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)})
         )
         .addAnimation(
            "fire",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 10.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.125F, KeyframeAnimations.degreeVec(32.5F, 0.0F, -9.38F), Interpolations.CATMULLROM),
                  new Keyframe(0.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -22.5F), Interpolations.CATMULLROM),
                  new Keyframe(0.4167667F, KeyframeAnimations.degreeVec(-26.2F, 0.0F, 10.16F), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 27.5F), Interpolations.CATMULLROM),
                  new Keyframe(0.6766666F, KeyframeAnimations.degreeVec(0.95F, -0.21F, 59.99F), Interpolations.CATMULLROM),
                  new Keyframe(0.75F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 55.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 55.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "fire",
            new AnimationChannel(
               Targets.SCALE,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.4F, 1.3F, 1.2F), Interpolations.CATMULLROM),
                  new Keyframe(0.25F, KeyframeAnimations.scaleVec(1.25, 1.84F, 1.2F), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.scaleVec(1.9F, 1.27F, 1.2F), Interpolations.CATMULLROM),
                  new Keyframe(0.75F, KeyframeAnimations.scaleVec(1.4F, 1.3F, 1.2F), Interpolations.CATMULLROM),
                  new Keyframe(1.25F, KeyframeAnimations.scaleVec(0.0, 0.2F, 1.2F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "eyes",
            new AnimationChannel(
               Targets.POSITION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.125F, KeyframeAnimations.posVec(0.0F, -0.64F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, 0.77F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.3433333F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.4167667F, KeyframeAnimations.posVec(0.0F, 1.36F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.5416766F, KeyframeAnimations.posVec(0.0F, -1.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.6766666F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.75F, KeyframeAnimations.posVec(-1.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.875F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "eyes",
            new AnimationChannel(
               Targets.SCALE,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
                  new Keyframe(0.3433333F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
                  new Keyframe(0.4167667F, KeyframeAnimations.scaleVec(1.0, 4.3F, 1.0), Interpolations.CATMULLROM),
                  new Keyframe(0.5416766F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
                  new Keyframe(0.75F, KeyframeAnimations.scaleVec(1.0, 1.3F, 1.0), Interpolations.CATMULLROM),
                  new Keyframe(1.25F, KeyframeAnimations.scaleVec(1.0, 0.0, 1.0), Interpolations.CATMULLROM)
               }
            )
         )
         .build();
      public static final AnimationDefinition CARCASS_REANIMATE = Builder.withLength(1.0834333F)
         .addAnimation(
            "carcass",
            new AnimationChannel(
               Targets.POSITION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -2.0F, 5.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, -5.0F, 5.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "carcass",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(47.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.degreeVec(20.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "head",
            new AnimationChannel(
               Targets.POSITION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -4.0F, -5.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.0416766F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "head",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(69.32F, -55.7F, -86.43F), Interpolations.CATMULLROM),
                  new Keyframe(0.25F, KeyframeAnimations.degreeVec(82.94F, -26.27F, -36.04F), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.degreeVec(57.79F, 2.69F, 12.21F), Interpolations.CATMULLROM),
                  new Keyframe(0.6766666F, KeyframeAnimations.degreeVec(37.26F, 2.22F, 5.41F), Interpolations.CATMULLROM),
                  new Keyframe(0.8343334F, KeyframeAnimations.degreeVec(24.76F, 2.22F, 5.41F), Interpolations.CATMULLROM),
                  new Keyframe(1.0416766F, KeyframeAnimations.degreeVec(-12.45F, -1.08F, -4.88F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "eyes",
            new AnimationChannel(
               Targets.SCALE,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.scaleVec(0.0, 0.0, 0.0), Interpolations.LINEAR),
                  new Keyframe(0.75F, KeyframeAnimations.scaleVec(0.0, 0.0, 0.0), Interpolations.LINEAR),
                  new Keyframe(0.9167666F, KeyframeAnimations.scaleVec(1.0, 2.6F, 1.0), Interpolations.LINEAR),
                  new Keyframe(1.0F, KeyframeAnimations.scaleVec(1.06F, 0.46F, 1.06F), Interpolations.LINEAR),
                  new Keyframe(1.0834333F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR)
               }
            )
         )
         .addAnimation(
            "fire",
            new AnimationChannel(
               Targets.POSITION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 2.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, 1.13F, -1.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "fire",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.25F, KeyframeAnimations.degreeVec(-106.41F, -0.34F, 29.58F), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.degreeVec(-47.5F, 0.0F, 27.5F), Interpolations.CATMULLROM),
                  new Keyframe(0.75F, KeyframeAnimations.degreeVec(10.01F, 5.41F, 21.67F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 10.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "fire",
            new AnimationChannel(
               Targets.SCALE,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.scaleVec(0.0, 0.0, 0.0), Interpolations.CATMULLROM),
                  new Keyframe(0.25F, KeyframeAnimations.scaleVec(0.7F, 0.3F, 1.0), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.scaleVec(1.1F, 1.0, 1.0), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.scaleVec(1.4F, 1.3F, 1.2F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "body",
            new AnimationChannel(
               Targets.POSITION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -4.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "body",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(20.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.degreeVec(20.45F, -11.74F, -4.34F), Interpolations.CATMULLROM),
                  new Keyframe(0.75F, KeyframeAnimations.degreeVec(21.5F, 21.08F, 8.06F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.degreeVec(20.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "left_arm",
            new AnimationChannel(
               Targets.POSITION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -4.0F, -5.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, 4.19F, 1.98F), Interpolations.CATMULLROM),
                  new Keyframe(0.375F, KeyframeAnimations.posVec(0.0F, 4.19F, 1.98F), Interpolations.CATMULLROM),
                  new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "left_arm",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(-63.79F, 34.74F, -49.17F), Interpolations.CATMULLROM),
                  new Keyframe(0.25F, KeyframeAnimations.degreeVec(24.21F, 49.7F, 37.25F), Interpolations.CATMULLROM),
                  new Keyframe(0.375F, KeyframeAnimations.degreeVec(24.21F, 49.7F, 37.25F), Interpolations.CATMULLROM),
                  new Keyframe(0.75F, KeyframeAnimations.degreeVec(14.84F, 58.71F, 19.23F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "right_arm",
            new AnimationChannel(
               Targets.POSITION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -4.0F, -5.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 4.19F, 1.98F), Interpolations.CATMULLROM),
                  new Keyframe(0.625F, KeyframeAnimations.posVec(0.0F, 4.19F, 1.98F), Interpolations.CATMULLROM),
                  new Keyframe(0.875F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "right_arm",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(-63.79F, -34.74F, 49.17F), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.degreeVec(24.21F, -49.7F, -37.25F), Interpolations.CATMULLROM),
                  new Keyframe(0.625F, KeyframeAnimations.degreeVec(24.21F, -49.7F, -37.25F), Interpolations.CATMULLROM),
                  new Keyframe(0.875F, KeyframeAnimations.degreeVec(14.84F, -58.71F, -19.23F), Interpolations.CATMULLROM)
               }
            )
         )
         .build();
      public static final AnimationDefinition CARCASS_MOVE = Builder.withLength(1.0F)
         .looping()
         .addAnimation(
            "carcass",
            new AnimationChannel(Targets.POSITION, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -5.0F, 5.0F), Interpolations.CATMULLROM)})
         )
         .addAnimation(
            "carcass",
            new AnimationChannel(
               Targets.ROTATION, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.degreeVec(20.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)}
            )
         )
         .addAnimation(
            "head",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(5.41F, -3.21F, -14.66F), Interpolations.CATMULLROM),
                  new Keyframe(0.25F, KeyframeAnimations.degreeVec(-12.09F, -3.21F, -14.66F), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.degreeVec(5.41F, 3.21F, 14.66F), Interpolations.CATMULLROM),
                  new Keyframe(0.75F, KeyframeAnimations.degreeVec(-12.09F, 3.21F, 14.66F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.degreeVec(5.41F, -3.21F, -14.66F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "eyes",
            new AnimationChannel(
               Targets.POSITION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
                  new Keyframe(0.08343333F, KeyframeAnimations.posVec(0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
                  new Keyframe(0.16766666F, KeyframeAnimations.posVec(-0.25F, -0.25F, 0.0F), Interpolations.LINEAR),
                  new Keyframe(0.25F, KeyframeAnimations.posVec(0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
                  new Keyframe(0.3433333F, KeyframeAnimations.posVec(-0.25F, 0.25F, 0.0F), Interpolations.LINEAR),
                  new Keyframe(0.4167667F, KeyframeAnimations.posVec(-0.25F, -0.25F, 0.0F), Interpolations.LINEAR),
                  new Keyframe(0.5F, KeyframeAnimations.posVec(0.25F, 0.25F, 0.0F), Interpolations.LINEAR),
                  new Keyframe(0.5834334F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
                  new Keyframe(0.6766666F, KeyframeAnimations.posVec(0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
                  new Keyframe(0.75F, KeyframeAnimations.posVec(-0.25F, -0.25F, 0.0F), Interpolations.LINEAR),
                  new Keyframe(0.8343334F, KeyframeAnimations.posVec(0.25F, 0.0F, 0.0F), Interpolations.LINEAR),
                  new Keyframe(0.9167666F, KeyframeAnimations.posVec(-0.25F, 0.25F, 0.0F), Interpolations.LINEAR),
                  new Keyframe(1.0F, KeyframeAnimations.posVec(-0.25F, -0.25F, 0.0F), Interpolations.LINEAR)
               }
            )
         )
         .addAnimation(
            "fire",
            new AnimationChannel(
               Targets.POSITION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "fire",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 25.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -22.5F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 25.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "fire",
            new AnimationChannel(
               Targets.SCALE,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.4F, 1.3F, 1.2F), Interpolations.CATMULLROM),
                  new Keyframe(0.25F, KeyframeAnimations.scaleVec(1.6F, 0.9F, 1.2F), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.scaleVec(1.4F, 1.3F, 1.2F), Interpolations.CATMULLROM),
                  new Keyframe(0.75F, KeyframeAnimations.scaleVec(1.0, 1.5, 1.2F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.scaleVec(1.4F, 1.3F, 1.2F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "body",
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
            "body",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(21.5F, 21.08F, 8.06F), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.degreeVec(21.5F, -21.08F, -8.06F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.degreeVec(21.5F, 21.08F, 8.06F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "left_arm",
            new AnimationChannel(
               Targets.POSITION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, -3.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 1.0F, 3.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, 0.44F, -3.37F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.0F, -3.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "left_arm",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(6.59F, 59.64F, 9.63F), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.degreeVec(24.8F, 48.24F, 30.74F), Interpolations.CATMULLROM),
                  new Keyframe(0.75F, KeyframeAnimations.degreeVec(-20.82F, 53.2F, -24.2F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.degreeVec(6.59F, 59.64F, 9.63F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "right_arm",
            new AnimationChannel(
               Targets.POSITION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 1.0F, 3.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, 0.44F, -3.37F), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.0F, -3.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 1.0F, 3.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "right_arm",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(24.8F, -48.24F, -30.74F), Interpolations.CATMULLROM),
                  new Keyframe(0.25F, KeyframeAnimations.degreeVec(-20.82F, -53.2F, 24.2F), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.degreeVec(6.59F, -59.64F, -9.63F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.degreeVec(24.8F, -48.24F, -30.74F), Interpolations.CATMULLROM)
               }
            )
         )
         .build();
      public static final AnimationDefinition CARCASS_ATTACK = Builder.withLength(1.0F)
         .addAnimation(
            "head",
            new AnimationChannel(
               Targets.POSITION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.125F, KeyframeAnimations.posVec(0.0F, 0.0F, -4.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.2916767F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "head",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.125F, KeyframeAnimations.degreeVec(10.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.20834334F, KeyframeAnimations.degreeVec(36.74F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.2916767F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.4167667F, KeyframeAnimations.degreeVec(-10.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "fire",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.08343333F, KeyframeAnimations.degreeVec(-20.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.20834334F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.3433333F, KeyframeAnimations.degreeVec(30.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.4167667F, KeyframeAnimations.degreeVec(-7.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "body",
            new AnimationChannel(
               Targets.POSITION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.125F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.20834334F, KeyframeAnimations.posVec(0.0F, 0.0F, -3.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.2916767F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "left_arm",
            new AnimationChannel(
               Targets.POSITION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.25F, KeyframeAnimations.posVec(3.0F, 0.0F, -7.52F), Interpolations.CATMULLROM),
                  new Keyframe(0.375F, KeyframeAnimations.posVec(0.0F, 0.0F, -4.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "left_arm",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.08343333F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -75.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.20834334F, KeyframeAnimations.degreeVec(0.0F, 67.5F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.3433333F, KeyframeAnimations.degreeVec(0.0F, 67.5F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "right_arm",
            new AnimationChannel(
               Targets.POSITION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.25F, KeyframeAnimations.posVec(-3.0F, 0.0F, -7.52F), Interpolations.CATMULLROM),
                  new Keyframe(0.4167667F, KeyframeAnimations.posVec(0.0F, 0.0F, -4.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "right_arm",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.125F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 75.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.20834334F, KeyframeAnimations.degreeVec(0.0F, -67.5F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.375F, KeyframeAnimations.degreeVec(0.0F, -67.5F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .build();
   }

   private static class CarcassGlowLayer<T extends Carcass> extends RenderLayer<T, CarcassRenderer.CarcassModel<T>> {
      public CarcassGlowLayer(RenderLayerParent<T, CarcassRenderer.CarcassModel<T>> renderer) {
         super(renderer);
      }

      public void render(
         PoseStack poseStack,
         MultiBufferSource buffer,
         int packedLight,
         T entity,
         float limbSwing,
         float limbSwingAmount,
         float partialTick,
         float ageInTicks,
         float netHeadYaw,
         float headPitch
      ) {
         if (entity.isReanimated()) {
            VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.eyes(NetherExp.netherexpPath("textures/entity/carcass/carcass_glow.png")));
            ((CarcassRenderer.CarcassModel)this.getParentModel()).renderToBuffer(poseStack, vertexConsumer, 15728640, OverlayTexture.NO_OVERLAY);
         }
      }
   }

   public static class CarcassModel<T extends Carcass> extends HierarchicalModel<T> {
      public static final ModelLayerLocation LAYER = new ModelLayerLocation(NetherExp.netherexpPath("carcass"), "main");
      private final ModelPart main;
      private final ModelPart head;

      public CarcassModel(ModelPart root) {
         this.main = root.getChild("main");
         ModelPart carcass = this.main.getChild("carcass");
         this.head = carcass.getChild("head");
      }

      public static LayerDefinition createBodyLayer() {
         MeshDefinition meshdefinition = new MeshDefinition();
         PartDefinition partdefinition = meshdefinition.getRoot();
         PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
         PartDefinition carcass = main.addOrReplaceChild("carcass", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
         PartDefinition head = carcass.addOrReplaceChild(
            "head",
            CubeListBuilder.create()
               .texOffs(0, 0)
               .addBox(-5.0F, -12.0F, -5.0F, 10.0F, 12.0F, 10.0F, new CubeDeformation(0.0F))
               .texOffs(0, 0)
               .addBox(-2.0F, -16.0F, 0.0F, 4.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)),
            PartPose.offset(0.0F, -19.0F, 0.0F)
         );
         PartDefinition eyes = head.addOrReplaceChild(
            "eyes",
            CubeListBuilder.create().texOffs(30, 9).addBox(-3.0F, -0.5F, 0.0F, 6.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)),
            PartPose.offset(0.0F, -7.5F, -5.25F)
         );
         PartDefinition fire = head.addOrReplaceChild(
            "fire",
            CubeListBuilder.create().texOffs(46, 0).addBox(-4.0F, -11.0F, -0.5F, 8.0F, 12.0F, 1.0F, new CubeDeformation(-0.4F)),
            PartPose.offset(0.0F, -14.0F, 0.0F)
         );
         PartDefinition body = carcass.addOrReplaceChild(
            "body",
            CubeListBuilder.create()
               .texOffs(0, 22)
               .addBox(-5.0F, -0.5F, -5.0F, 10.0F, 7.0F, 10.0F, new CubeDeformation(0.0F))
               .texOffs(0, 39)
               .addBox(-3.0F, 6.5F, -3.0F, 6.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)),
            PartPose.offset(0.0F, -16.5F, 0.0F)
         );
         PartDefinition left_arm = carcass.addOrReplaceChild(
            "left_arm",
            CubeListBuilder.create()
               .texOffs(0, 50)
               .addBox(0.25F, -2.5F, -2.0F, 3.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
               .texOffs(0, 60)
               .addBox(3.25F, -0.5F, -1.0F, 14.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
            PartPose.offset(4.75F, -14.5F, 0.0F)
         );
         PartDefinition right_arm = carcass.addOrReplaceChild(
            "right_arm",
            CubeListBuilder.create()
               .texOffs(0, 50)
               .mirror()
               .addBox(-3.25F, -2.5F, -2.0F, 3.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
               .mirror(false)
               .texOffs(0, 60)
               .mirror()
               .addBox(-17.25F, -0.5F, -1.0F, 14.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
               .mirror(false),
            PartPose.offset(-4.75F, -14.5F, 0.0F)
         );
         return LayerDefinition.create(meshdefinition, 64, 64);
      }

      public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
         this.root().getAllParts().forEach(ModelPart::resetPose);
         this.applyHeadRotation(netHeadYaw, headPitch);
         this.animate(entity.idleAnimationState, CarcassRenderer.Animation.CARCASS_IDLE, ageInTicks);
         this.animate(entity.moveAnimationState, CarcassRenderer.Animation.CARCASS_MOVE, ageInTicks);
         this.animate(entity.reanimateAnimationState, CarcassRenderer.Animation.CARCASS_REANIMATE, ageInTicks);
         this.animate(entity.deactivateAnimationState, CarcassRenderer.Animation.CARCASS_DEACTIVATE, ageInTicks);
         this.animate(entity.attackAnimationState, CarcassRenderer.Animation.CARCASS_ATTACK, ageInTicks);
      }

      private void applyHeadRotation(float pNetHeadYaw, float pHeadPitch) {
         pNetHeadYaw = Mth.clamp(pNetHeadYaw, -30.0F, 30.0F);
         pHeadPitch = Mth.clamp(pHeadPitch, -25.0F, 45.0F);
         this.head.yRot = pNetHeadYaw * (float) (Math.PI / 180.0);
         this.head.xRot = pHeadPitch * (float) (Math.PI / 180.0);
      }

      public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
         this.main.render(poseStack, buffer, packedLight, packedOverlay, color);
      }

      @NotNull
      public ModelPart root() {
         return this.main;
      }
   }
}
