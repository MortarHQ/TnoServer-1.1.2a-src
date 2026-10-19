package net.jadenxgamer.netherexp.client.rendering.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.core.entity.Stampede;
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
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.entity.layers.SaddleLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.WalkAnimationState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class StampedeRenderer extends MobRenderer<Stampede, StampedeRenderer.StampedeModel<Stampede>> {
   public StampedeRenderer(Context context) {
      super(context, new StampedeRenderer.StampedeModel(context.bakeLayer(StampedeRenderer.StampedeModel.LAYER)), 1.0F);
      this.addLayer(
         new SaddleLayer(
            this,
            new StampedeRenderer.StampedeModel(context.bakeLayer(StampedeRenderer.StampedeModel.SADDLE_LAYER)),
            NetherExp.netherexpPath("textures/entity/stampede/stampede_saddle.png")
         )
      );
      this.addLayer(new StampedeRenderer.StampedeGlowLayer(this));
   }

   @NotNull
   public ResourceLocation getTextureLocation(Stampede entity) {
      return NetherExp.netherexpPath(entity.isPatrick() ? "textures/entity/stampede/patrick.png" : "textures/entity/stampede/stampede.png");
   }

   public void render(Stampede entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
      super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
   }

   @Nullable
   protected RenderType getRenderType(Stampede entity, boolean bodyVisible, boolean translucent, boolean glowing) {
      return RenderType.entityCutout(this.getTextureLocation(entity));
   }

   public static class Animation {
      public static final AnimationDefinition WALK = Builder.withLength(1.579F)
         .looping()
         .addAnimation(
            "stampede",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 2.5F, 0.0F), Interpolations.LINEAR),
                  new Keyframe(0.2632F, KeyframeAnimations.degreeVec(0.0F, 2.5F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.3947F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
                  new Keyframe(0.5263F, KeyframeAnimations.degreeVec(0.0F, 2.5F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.7895F, KeyframeAnimations.degreeVec(0.0F, -2.5F, 0.0F), Interpolations.LINEAR),
                  new Keyframe(1.0526F, KeyframeAnimations.degreeVec(0.0F, -2.5F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.1842F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
                  new Keyframe(1.3158F, KeyframeAnimations.degreeVec(0.0F, -2.5F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.579F, KeyframeAnimations.degreeVec(0.0F, 2.5F, 0.0F), Interpolations.LINEAR)
               }
            )
         )
         .addAnimation(
            "stampede",
            new AnimationChannel(
               Targets.POSITION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
                  new Keyframe(0.3947F, KeyframeAnimations.posVec(0.0F, -2.0F, 0.0F), Interpolations.LINEAR),
                  new Keyframe(0.7895F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
                  new Keyframe(1.1842F, KeyframeAnimations.posVec(0.0F, -2.0F, 0.0F), Interpolations.LINEAR),
                  new Keyframe(1.579F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
               }
            )
         )
         .addAnimation(
            "head",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.2632F, KeyframeAnimations.degreeVec(15.0F, 0.0F, -10.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.3947F, KeyframeAnimations.degreeVec(15.0F, 0.0F, -10.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.6579F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -10.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.0526F, KeyframeAnimations.degreeVec(15.0F, 0.0F, 10.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.1842F, KeyframeAnimations.degreeVec(15.0F, 0.0F, 10.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.4035F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 10.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.579F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "right_ear",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(29.6216F, 4.9809F, -8.6822F), Interpolations.CATMULLROM),
                  new Keyframe(0.2632F, KeyframeAnimations.degreeVec(40.2611F, -5.4355F, 3.2513F), Interpolations.CATMULLROM),
                  new Keyframe(0.3947F, KeyframeAnimations.degreeVec(54.7702F, 0.8996F, -5.7894F), Interpolations.CATMULLROM),
                  new Keyframe(0.6579F, KeyframeAnimations.degreeVec(71.7942F, -8.4276F, -2.9905F), Interpolations.CATMULLROM),
                  new Keyframe(0.8772F, KeyframeAnimations.degreeVec(69.7702F, 0.8996F, -5.7894F), Interpolations.CATMULLROM),
                  new Keyframe(1.1403F, KeyframeAnimations.degreeVec(41.7337F, -7.4833F, 3.5067F), Interpolations.CATMULLROM),
                  new Keyframe(1.4035F, KeyframeAnimations.degreeVec(33.1143F, -9.2527F, 9.1926F), Interpolations.CATMULLROM),
                  new Keyframe(1.579F, KeyframeAnimations.degreeVec(29.6216F, 4.9809F, -8.6822F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "left_ear",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(29.6216F, -4.9809F, 8.6822F), Interpolations.CATMULLROM),
                  new Keyframe(0.1754F, KeyframeAnimations.degreeVec(40.2611F, 5.4355F, -3.2513F), Interpolations.CATMULLROM),
                  new Keyframe(0.3509F, KeyframeAnimations.degreeVec(54.7702F, -0.8996F, 5.7894F), Interpolations.CATMULLROM),
                  new Keyframe(0.5263F, KeyframeAnimations.degreeVec(71.7942F, 8.4276F, 2.9905F), Interpolations.CATMULLROM),
                  new Keyframe(0.7456F, KeyframeAnimations.degreeVec(69.7702F, -0.8996F, 5.7894F), Interpolations.CATMULLROM),
                  new Keyframe(1.0965F, KeyframeAnimations.degreeVec(41.7337F, 7.4833F, -3.5067F), Interpolations.CATMULLROM),
                  new Keyframe(1.3158F, KeyframeAnimations.degreeVec(33.1143F, 9.2527F, -9.1926F), Interpolations.CATMULLROM),
                  new Keyframe(1.579F, KeyframeAnimations.degreeVec(29.6216F, -4.9809F, 8.6822F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "right_leg",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(-32.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.1754F, KeyframeAnimations.degreeVec(-42.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.2632F, KeyframeAnimations.degreeVec(-32.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.5263F, KeyframeAnimations.degreeVec(-32.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.7895F, KeyframeAnimations.degreeVec(-2.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.2281F, KeyframeAnimations.degreeVec(31.56F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.579F, KeyframeAnimations.degreeVec(-32.5F, 0.0F, 0.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "right_leg",
            new AnimationChannel(
               Targets.POSITION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, -2.0F), Interpolations.LINEAR),
                  new Keyframe(0.2632F, KeyframeAnimations.posVec(-2.0F, -4.0F, -1.52F), Interpolations.LINEAR),
                  new Keyframe(0.7895F, KeyframeAnimations.posVec(0.0F, 0.0F, 2.0F), Interpolations.LINEAR),
                  new Keyframe(1.0088F, KeyframeAnimations.posVec(0.0F, -2.0F, 1.54F), Interpolations.LINEAR),
                  new Keyframe(1.2281F, KeyframeAnimations.posVec(0.0F, 3.21F, 0.49F), Interpolations.LINEAR),
                  new Keyframe(1.4035F, KeyframeAnimations.posVec(0.0F, 1.6F, 2.24F), Interpolations.LINEAR),
                  new Keyframe(1.579F, KeyframeAnimations.posVec(0.0F, 0.0F, -2.0F), Interpolations.LINEAR)
               }
            )
         )
         .addAnimation(
            "left_leg",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(-2.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.4386F, KeyframeAnimations.degreeVec(31.56F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.7895F, KeyframeAnimations.degreeVec(-32.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.9649F, KeyframeAnimations.degreeVec(-42.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.0526F, KeyframeAnimations.degreeVec(-32.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.3158F, KeyframeAnimations.degreeVec(-32.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.579F, KeyframeAnimations.degreeVec(-2.5F, 0.0F, 0.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "left_leg",
            new AnimationChannel(
               Targets.POSITION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 2.0F), Interpolations.LINEAR),
                  new Keyframe(0.2193F, KeyframeAnimations.posVec(0.0F, -2.0F, 1.54F), Interpolations.LINEAR),
                  new Keyframe(0.4386F, KeyframeAnimations.posVec(0.0F, 3.21F, 0.49F), Interpolations.LINEAR),
                  new Keyframe(0.614F, KeyframeAnimations.posVec(0.0F, 1.6F, 2.24F), Interpolations.LINEAR),
                  new Keyframe(0.7895F, KeyframeAnimations.posVec(0.0F, 0.0F, -2.0F), Interpolations.LINEAR),
                  new Keyframe(0.9649F, KeyframeAnimations.posVec(2.0F, -3.33F, -1.6F), Interpolations.LINEAR),
                  new Keyframe(1.0526F, KeyframeAnimations.posVec(0.0F, -4.0F, -1.52F), Interpolations.LINEAR),
                  new Keyframe(1.579F, KeyframeAnimations.posVec(0.0F, 0.0F, 2.0F), Interpolations.LINEAR)
               }
            )
         )
         .addAnimation(
            "left_foot",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(4.38F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.4386F, KeyframeAnimations.degreeVec(20.63F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.6579F, KeyframeAnimations.degreeVec(40.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.9211F, KeyframeAnimations.degreeVec(-5.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.1842F, KeyframeAnimations.degreeVec(28.75F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.579F, KeyframeAnimations.degreeVec(4.38F, 0.0F, 0.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "left_foot",
            new AnimationChannel(
               Targets.POSITION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.9211F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.1842F, KeyframeAnimations.posVec(0.0F, 1.0F, -2.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.579F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "right_foot",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(40.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.2632F, KeyframeAnimations.degreeVec(-5.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.5263F, KeyframeAnimations.degreeVec(28.75F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.9211F, KeyframeAnimations.degreeVec(4.38F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.3597F, KeyframeAnimations.degreeVec(20.63F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.579F, KeyframeAnimations.degreeVec(40.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "right_foot",
            new AnimationChannel(
               Targets.POSITION,
               new Keyframe[]{
                  new Keyframe(0.2632F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.5263F, KeyframeAnimations.posVec(0.0F, 1.0F, -2.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.9211F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .build();
      public static final AnimationDefinition STRIDE = Builder.withLength(3.1579F)
         .looping()
         .addAnimation(
            "stampede",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 2.5F, 0.0F), Interpolations.LINEAR),
                  new Keyframe(0.7895F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
                  new Keyframe(1.5789F, KeyframeAnimations.degreeVec(0.0F, -2.5F, 0.0F), Interpolations.LINEAR),
                  new Keyframe(2.3684F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
                  new Keyframe(3.1579F, KeyframeAnimations.degreeVec(0.0F, 2.5F, 0.0F), Interpolations.LINEAR)
               }
            )
         )
         .addAnimation(
            "stampede",
            new AnimationChannel(
               Targets.POSITION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -3.0F, 0.0F), Interpolations.LINEAR),
                  new Keyframe(0.7895F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
                  new Keyframe(1.5789F, KeyframeAnimations.posVec(0.0F, -3.0F, 0.0F), Interpolations.LINEAR),
                  new Keyframe(2.3684F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
                  new Keyframe(3.1579F, KeyframeAnimations.posVec(0.0F, -3.0F, 0.0F), Interpolations.LINEAR)
               }
            )
         )
         .addAnimation(
            "head",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(12.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.5263F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.0526F, KeyframeAnimations.degreeVec(12.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.5789F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(2.1053F, KeyframeAnimations.degreeVec(12.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(2.6316F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(3.1579F, KeyframeAnimations.degreeVec(12.5F, 0.0F, 0.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "head",
            new AnimationChannel(Targets.POSITION, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, -2.0F), Interpolations.CATMULLROM)})
         )
         .addAnimation(
            "right_ear",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(29.6216F, 4.9809F, -8.6822F), Interpolations.CATMULLROM),
                  new Keyframe(0.4386F, KeyframeAnimations.degreeVec(15.2611F, -5.4355F, 3.2513F), Interpolations.CATMULLROM),
                  new Keyframe(0.7018F, KeyframeAnimations.degreeVec(30.4211F, -0.2092F, -7.0663F), Interpolations.CATMULLROM),
                  new Keyframe(1.1404F, KeyframeAnimations.degreeVec(11.7942F, -8.4276F, -2.9905F), Interpolations.CATMULLROM),
                  new Keyframe(1.5789F, KeyframeAnimations.degreeVec(19.7702F, 0.8996F, -5.7894F), Interpolations.CATMULLROM),
                  new Keyframe(2.0175F, KeyframeAnimations.degreeVec(41.7337F, -7.4833F, 3.5067F), Interpolations.CATMULLROM),
                  new Keyframe(2.4561F, KeyframeAnimations.degreeVec(33.1143F, -9.2527F, 9.1926F), Interpolations.CATMULLROM),
                  new Keyframe(3.1579F, KeyframeAnimations.degreeVec(29.6216F, 4.9809F, -8.6822F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "left_ear",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(29.6216F, -4.9809F, 8.6822F), Interpolations.CATMULLROM),
                  new Keyframe(0.3509F, KeyframeAnimations.degreeVec(15.2611F, 5.4355F, -3.2513F), Interpolations.CATMULLROM),
                  new Keyframe(0.7018F, KeyframeAnimations.degreeVec(30.4211F, 0.2092F, 7.0663F), Interpolations.CATMULLROM),
                  new Keyframe(1.0526F, KeyframeAnimations.degreeVec(11.7942F, 8.4276F, 2.9905F), Interpolations.CATMULLROM),
                  new Keyframe(1.4912F, KeyframeAnimations.degreeVec(19.7702F, -0.8996F, 5.7894F), Interpolations.CATMULLROM),
                  new Keyframe(2.193F, KeyframeAnimations.degreeVec(41.7337F, 7.4833F, -3.5067F), Interpolations.CATMULLROM),
                  new Keyframe(2.6316F, KeyframeAnimations.degreeVec(33.1143F, 9.2527F, -9.1926F), Interpolations.CATMULLROM),
                  new Keyframe(3.1579F, KeyframeAnimations.degreeVec(29.6216F, -4.9809F, 8.6822F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "right_leg",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(-5.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.7895F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.8421F, KeyframeAnimations.degreeVec(25.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(2.5439F, KeyframeAnimations.degreeVec(15.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(3.1579F, KeyframeAnimations.degreeVec(-5.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "right_leg",
            new AnimationChannel(
               Targets.POSITION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 2.0F, -6.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.1754F, KeyframeAnimations.posVec(0.0F, 2.0F, -6.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.7895F, KeyframeAnimations.posVec(0.0F, 1.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.8421F, KeyframeAnimations.posVec(0.0F, -3.0F, 7.0F), Interpolations.CATMULLROM),
                  new Keyframe(2.1053F, KeyframeAnimations.posVec(0.0F, -3.91F, 2.24F), Interpolations.CATMULLROM),
                  new Keyframe(2.5439F, KeyframeAnimations.posVec(0.0F, 3.0F, -3.0F), Interpolations.CATMULLROM),
                  new Keyframe(3.1579F, KeyframeAnimations.posVec(0.0F, 2.0F, -6.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "left_leg",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(25.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.7018F, KeyframeAnimations.degreeVec(15.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.3158F, KeyframeAnimations.degreeVec(-5.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(2.1053F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(3.1579F, KeyframeAnimations.degreeVec(25.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "left_leg",
            new AnimationChannel(
               Targets.POSITION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -3.0F, 7.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.2632F, KeyframeAnimations.posVec(0.0F, -3.91F, 2.24F), Interpolations.CATMULLROM),
                  new Keyframe(0.7018F, KeyframeAnimations.posVec(0.0F, 3.0F, -3.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.3158F, KeyframeAnimations.posVec(0.0F, 2.0F, -6.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.4912F, KeyframeAnimations.posVec(0.0F, 2.0F, -6.0F), Interpolations.CATMULLROM),
                  new Keyframe(2.1053F, KeyframeAnimations.posVec(0.0F, 1.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(3.1579F, KeyframeAnimations.posVec(0.0F, -3.0F, 7.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "left_foot",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.2632F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.8772F, KeyframeAnimations.degreeVec(42.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.3158F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "right_foot",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(1.8421F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(2.1053F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(2.7193F, KeyframeAnimations.degreeVec(42.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(3.1579F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .build();
      public static final AnimationDefinition GRIN = Builder.withLength(1.0F)
         .addAnimation(
            "head",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(7.5F, 0.0F, 0.0F), Interpolations.LINEAR),
                  new Keyframe(0.25F, KeyframeAnimations.degreeVec(-12.5F, 0.0F, 0.0F), Interpolations.LINEAR),
                  new Keyframe(0.4583F, KeyframeAnimations.degreeVec(7.5F, 0.0F, 0.0F), Interpolations.LINEAR),
                  new Keyframe(0.6667F, KeyframeAnimations.degreeVec(2.5F, 0.0F, 0.0F), Interpolations.LINEAR)
               }
            )
         )
         .addAnimation(
            "jaw",
            new AnimationChannel(
               Targets.POSITION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, 10.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 6.0F, 0.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "right_ear",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0833F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-25.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.6667F, KeyframeAnimations.degreeVec(17.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.875F, KeyframeAnimations.degreeVec(-2.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "left_ear",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.25F, KeyframeAnimations.degreeVec(-25.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.5833F, KeyframeAnimations.degreeVec(17.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.7917F, KeyframeAnimations.degreeVec(-2.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .build();
      public static final AnimationDefinition CHEW = Builder.withLength(1.0F)
         .looping()
         .addAnimation(
            "jaw",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.75F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "jaw",
            new AnimationChannel(
               Targets.POSITION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 6.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, 13.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 6.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, 13.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 6.0F, 0.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "jaw", new AnimationChannel(Targets.SCALE, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR)})
         )
         .build();
      public static final AnimationDefinition IDLE = Builder.withLength(2.0F)
         .looping()
         .addAnimation(
            "head",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 7.5F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -7.5F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, -7.5F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 7.5F), Interpolations.CATMULLROM),
                  new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 7.5F, 0.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "right_ear",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(2.5F, 22.5F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.3F, KeyframeAnimations.degreeVec(-23.24F, -6.66F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.degreeVec(-2.5F, -22.5F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.7F, KeyframeAnimations.degreeVec(23.6133F, -4.5605F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.degreeVec(2.5F, 22.5F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.3F, KeyframeAnimations.degreeVec(-30.3295F, -1.269F, 10.8616F), Interpolations.CATMULLROM),
                  new Keyframe(1.5F, KeyframeAnimations.degreeVec(-2.5F, -22.5F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.8F, KeyframeAnimations.degreeVec(24.0555F, 5.6462F, 1.7769F), Interpolations.CATMULLROM),
                  new Keyframe(2.0F, KeyframeAnimations.degreeVec(2.5F, 22.5F, 0.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "left_ear",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(2.5F, -22.5F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.2F, KeyframeAnimations.degreeVec(28.24F, -6.66F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.degreeVec(-2.5F, 22.5F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.8F, KeyframeAnimations.degreeVec(-27.9955F, -7.4203F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.degreeVec(2.5F, -22.5F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.2F, KeyframeAnimations.degreeVec(32.5757F, -4.5057F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.5F, KeyframeAnimations.degreeVec(-2.5F, 22.5F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.8F, KeyframeAnimations.degreeVec(-40.4169F, -5.4242F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(2.0F, KeyframeAnimations.degreeVec(2.5F, -22.5F, 0.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "left_leg",
            new AnimationChannel(Targets.ROTATION, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, -12.5F, 0.0F), Interpolations.LINEAR)})
         )
         .addAnimation(
            "right_leg",
            new AnimationChannel(Targets.ROTATION, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 12.5F, 0.0F), Interpolations.LINEAR)})
         )
         .build();
      public static final AnimationDefinition IDLE_BREAK = Builder.withLength(1.2083F)
         .addAnimation(
            "head",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.1667F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.3333F, KeyframeAnimations.degreeVec(0.0F, 22.5F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.5417F, KeyframeAnimations.degreeVec(0.0F, -15.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.7083F, KeyframeAnimations.degreeVec(0.0F, 12.5F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.875F, KeyframeAnimations.degreeVec(0.0F, -7.5F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "jaw",
            new AnimationChannel(
               Targets.POSITION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.1667F, KeyframeAnimations.posVec(0.0F, 2.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 1.06F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, 2.06F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 2.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.1667F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .build();
      public static final AnimationDefinition AIR = Builder.withLength(1.0F)
         .looping()
         .addAnimation(
            "stampede",
            new AnimationChannel(Targets.ROTATION, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.degreeVec(10.0F, 0.0F, 0.0F), Interpolations.LINEAR)})
         )
         .addAnimation(
            "stampede",
            new AnimationChannel(Targets.POSITION, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 2.0F), Interpolations.LINEAR)})
         )
         .addAnimation(
            "head",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(-10.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.25F, KeyframeAnimations.degreeVec(-3.5066F, 0.3066F, 4.9906F), Interpolations.CATMULLROM),
                  new Keyframe(0.4167F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.7083F, KeyframeAnimations.degreeVec(-5.1017F, -0.9372F, -10.5838F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.degreeVec(-10.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "right_ear",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(155.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.25F, KeyframeAnimations.degreeVec(183.092F, -3.4049F, -19.7197F), Interpolations.CATMULLROM),
                  new Keyframe(0.5417F, KeyframeAnimations.degreeVec(182.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.8333F, KeyframeAnimations.degreeVec(151.0392F, -7.3925F, -13.0884F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.degreeVec(155.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "left_ear",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(155.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.25F, KeyframeAnimations.degreeVec(170.592F, 3.4049F, 19.7197F), Interpolations.CATMULLROM),
                  new Keyframe(0.4583F, KeyframeAnimations.degreeVec(182.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.7917F, KeyframeAnimations.degreeVec(153.6104F, 14.3925F, 26.6088F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.degreeVec(155.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "left_leg",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(-35.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.degreeVec(-27.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.degreeVec(-35.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "left_foot",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(85.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.625F, KeyframeAnimations.degreeVec(92.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.degreeVec(85.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "left_foot",
            new AnimationChannel(
               Targets.POSITION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, -1.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.0F, -1.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "right_leg",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(-70.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.degreeVec(-60.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.degreeVec(-70.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "right_foot",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(105.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.4167F, KeyframeAnimations.degreeVec(110.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.degreeVec(105.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "right_foot",
            new AnimationChannel(
               Targets.POSITION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -3.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, -3.0F, 0.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .build();
   }

   public static class StampedeGlowLayer extends EyesLayer<Stampede, StampedeRenderer.StampedeModel<Stampede>> {
      public StampedeGlowLayer(RenderLayerParent<Stampede, StampedeRenderer.StampedeModel<Stampede>> renderLayerParent) {
         super(renderLayerParent);
      }

      @NotNull
      public RenderType renderType() {
         return RenderType.eyes(NetherExp.netherexpPath("textures/entity/stampede/stampede_glow.png"));
      }
   }

   public static class StampedeModel<T extends Stampede> extends HierarchicalModel<T> {
      public static final ModelLayerLocation LAYER = new ModelLayerLocation(NetherExp.netherexpPath("stampede"), "main");
      public static final ModelLayerLocation SADDLE_LAYER = new ModelLayerLocation(NetherExp.netherexpPath("stampede"), "saddle");
      private final ModelPart stampede;
      private final ModelPart head;
      protected final ModelPart jaw;
      protected final ModelPart right_ear;
      protected final ModelPart left_ear;
      protected final ModelPart left_leg;
      protected final ModelPart left_foot;
      protected final ModelPart right_leg;
      protected final ModelPart right_foot;

      public StampedeModel(ModelPart root) {
         this.stampede = root.getChild("stampede");
         this.head = this.stampede.getChild("head");
         this.jaw = this.head.getChild("jaw");
         this.right_ear = this.jaw.getChild("right_ear");
         this.left_ear = this.jaw.getChild("left_ear");
         this.left_leg = this.stampede.getChild("left_leg");
         this.left_foot = this.left_leg.getChild("left_foot");
         this.right_leg = this.stampede.getChild("right_leg");
         this.right_foot = this.right_leg.getChild("right_foot");
      }

      public static LayerDefinition createBodyLayer() {
         MeshDefinition meshdefinition = new MeshDefinition();
         PartDefinition partdefinition = meshdefinition.getRoot();
         PartDefinition stampede = partdefinition.addOrReplaceChild("stampede", CubeListBuilder.create(), PartPose.offset(0.0F, -4.0F, 0.0F));
         PartDefinition head = stampede.addOrReplaceChild(
            "head",
            CubeListBuilder.create()
               .texOffs(0, 37)
               .addBox(-12.0F, -6.5F, -12.0917F, 24.0F, 13.0F, 24.0F, new CubeDeformation(0.0F))
               .texOffs(0, 87)
               .addBox(-12.0F, -6.5F, -11.8417F, 24.0F, 13.0F, 0.0F, new CubeDeformation(0.0F)),
            PartPose.offset(0.0F, -28.5F, 0.0917F)
         );
         PartDefinition jaw = head.addOrReplaceChild(
            "jaw",
            CubeListBuilder.create()
               .texOffs(0, 0)
               .addBox(-12.0F, -6.5F, -6.025F, 24.0F, 13.0F, 24.0F, new CubeDeformation(0.15F))
               .texOffs(0, 74)
               .addBox(-12.0F, -6.5F, -5.975F, 24.0F, 13.0F, 0.0F, new CubeDeformation(0.0F)),
            PartPose.offset(0.0F, 0.0F, -6.0667F)
         );
         PartDefinition right_ear = jaw.addOrReplaceChild(
            "right_ear",
            CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -3.0F, 0.0F, 6.0F, 18.0F, 0.0F, new CubeDeformation(0.0F)),
            PartPose.offset(-12.0F, 0.5F, 5.975F)
         );
         PartDefinition left_ear = jaw.addOrReplaceChild(
            "left_ear",
            CubeListBuilder.create().texOffs(0, 0).mirror().addBox(0.0F, -3.0F, 0.0F, 6.0F, 18.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false),
            PartPose.offset(12.0F, 0.5F, 5.975F)
         );
         PartDefinition left_leg = stampede.addOrReplaceChild(
            "left_leg",
            CubeListBuilder.create().texOffs(48, 74).mirror().addBox(-2.0F, -5.0F, -2.0F, 4.0F, 23.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false),
            PartPose.offset(5.0F, -21.0F, 0.0F)
         );
         PartDefinition left_foot = left_leg.addOrReplaceChild(
            "left_foot",
            CubeListBuilder.create().texOffs(64, 74).mirror().addBox(-4.0F, -0.5F, -4.0F, 8.0F, 31.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false),
            PartPose.offset(0.0F, 18.5F, 0.0F)
         );
         PartDefinition right_leg = stampede.addOrReplaceChild(
            "right_leg",
            CubeListBuilder.create().texOffs(48, 74).addBox(-2.0F, -5.0F, -2.0F, 4.0F, 23.0F, 4.0F, new CubeDeformation(0.0F)),
            PartPose.offset(-5.0F, -21.0F, 0.0F)
         );
         PartDefinition right_foot = right_leg.addOrReplaceChild(
            "right_foot",
            CubeListBuilder.create().texOffs(64, 74).addBox(-4.0F, -0.5F, -4.0F, 8.0F, 31.0F, 8.0F, new CubeDeformation(0.0F)),
            PartPose.offset(0.0F, 18.5F, 0.0F)
         );
         return LayerDefinition.create(meshdefinition, 128, 256);
      }

      public void setupAnim(Stampede entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
         this.root().getAllParts().forEach(ModelPart::resetPose);
         this.applyHeadRotation(netHeadYaw, headPitch);
         this.animateIdle(entity.idleAnimation, StampedeRenderer.Animation.IDLE, ageInTicks, entity.walkAnimation, 0.1F);
         this.animate(entity.idleBreakAnimation, StampedeRenderer.Animation.IDLE_BREAK, ageInTicks);
         this.animate(entity.chewAnimation, StampedeRenderer.Animation.CHEW, ageInTicks);
         this.animate(entity.grinAnimation, StampedeRenderer.Animation.GRIN, ageInTicks);
         this.animateWalkBlended(
            StampedeRenderer.Animation.WALK,
            StampedeRenderer.Animation.STRIDE,
            entity.walkBlendState.get(),
            limbSwing,
            limbSwingAmount,
            entity.getSpeedBoostTicks() > 0 ? 3.0F : 2.2F,
            1.5F
         );
      }

      private void applyHeadRotation(float pNetHeadYaw, float pHeadPitch) {
         pNetHeadYaw = Mth.clamp(pNetHeadYaw, -30.0F, 30.0F);
         pHeadPitch = Mth.clamp(pHeadPitch, -25.0F, 45.0F);
         this.head.yRot = pNetHeadYaw * (float) (Math.PI / 180.0);
         this.head.xRot = pHeadPitch * (float) (Math.PI / 180.0);
      }

      public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
         this.stampede.render(poseStack, buffer, packedLight, packedOverlay, color);
      }

      public ModelPart root() {
         return this.stampede;
      }

      protected void animateIdle(
         AnimationState animationState,
         AnimationDefinition animationDefinition,
         float ageInTicks,
         WalkAnimationState walkAnimation,
         float maxSpeedForZeroWeight
      ) {
         float speed = walkAnimation.speed();
         float weight = 1.0F - Mth.clamp(speed / maxSpeedForZeroWeight, 0.0F, 1.0F);
         animationState.updateTime(ageInTicks, 1.0F);
         animationState.ifStarted(state -> KeyframeAnimations.animate(this, animationDefinition, state.getAccumulatedTime(), weight, ANIMATION_VECTOR_CACHE));
      }

      protected void animateWalkBlended(
         AnimationDefinition walkAnim,
         AnimationDefinition strideAnim,
         float blend,
         float limbSwing,
         float limbSwingAmount,
         float maxAnimationSpeed,
         float animationScaleFactor
      ) {
         long i = (long)(limbSwing * 50.0F * maxAnimationSpeed);
         float f = Math.min(limbSwingAmount * animationScaleFactor, 1.0F);
         KeyframeAnimations.animate(this, walkAnim, i, f * (1.0F - blend), ANIMATION_VECTOR_CACHE);
         KeyframeAnimations.animate(this, strideAnim, i, f * blend, ANIMATION_VECTOR_CACHE);
      }
   }
}
