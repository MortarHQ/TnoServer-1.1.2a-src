package net.jadenxgamer.netherexp.client.rendering.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.client.rendering.JNERenderType;
import net.jadenxgamer.netherexp.core.entity.Wisp;
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

public class WispRenderer extends MobRenderer<Wisp, WispRenderer.WispModel<Wisp>> {
   public WispRenderer(Context context) {
      super(context, new WispRenderer.WispModel(context.bakeLayer(WispRenderer.WispModel.LAYER)), 0.2F);
   }

   @NotNull
   public ResourceLocation getTextureLocation(Wisp entity) {
      return NetherExp.netherexpPath("textures/entity/wisp.png");
   }

   @Nullable
   protected RenderType getRenderType(Wisp entity, boolean bodyVisible, boolean translucent, boolean glowing) {
      return JNERenderType.noShadeEntityCutoutNoCull(this.getTextureLocation(entity));
   }

   protected int getBlockLightLevel(Wisp entity, BlockPos pos) {
      return 15;
   }

   private static class Animation {
      public static final AnimationDefinition IDLE = Builder.withLength(4.0F)
         .looping()
         .addAnimation(
            "body",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(10.0F, 0.0F, -10.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.degreeVec(-10.0F, 0.0F, -10.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.degreeVec(-10.0F, 0.0F, 10.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.5F, KeyframeAnimations.degreeVec(10.0F, 0.0F, 10.0F), Interpolations.CATMULLROM),
                  new Keyframe(2.0F, KeyframeAnimations.degreeVec(10.0F, 0.0F, -10.0F), Interpolations.CATMULLROM),
                  new Keyframe(2.5F, KeyframeAnimations.degreeVec(-10.0F, 0.0F, -10.0F), Interpolations.CATMULLROM),
                  new Keyframe(3.0F, KeyframeAnimations.degreeVec(-10.0F, 0.0F, 10.0F), Interpolations.CATMULLROM),
                  new Keyframe(3.5F, KeyframeAnimations.degreeVec(10.0F, 0.0F, 10.0F), Interpolations.CATMULLROM),
                  new Keyframe(4.0F, KeyframeAnimations.degreeVec(10.0F, 0.0F, -10.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "body",
            new AnimationChannel(
               Targets.SCALE,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.1F, 0.9F, 1.1F), Interpolations.CATMULLROM),
                  new Keyframe(0.25F, KeyframeAnimations.scaleVec(0.9F, 1.0, 0.9F), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.scaleVec(1.1F, 0.9F, 1.1F), Interpolations.CATMULLROM),
                  new Keyframe(0.75F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.scaleVec(1.1F, 0.9F, 1.1F), Interpolations.CATMULLROM),
                  new Keyframe(1.25F, KeyframeAnimations.scaleVec(0.9F, 1.0, 0.9F), Interpolations.CATMULLROM),
                  new Keyframe(1.5F, KeyframeAnimations.scaleVec(1.1F, 0.9F, 1.1F), Interpolations.CATMULLROM),
                  new Keyframe(1.75F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
                  new Keyframe(2.0F, KeyframeAnimations.scaleVec(1.1F, 0.9F, 1.1F), Interpolations.CATMULLROM),
                  new Keyframe(2.25F, KeyframeAnimations.scaleVec(0.9F, 1.0, 0.9F), Interpolations.CATMULLROM),
                  new Keyframe(2.5F, KeyframeAnimations.scaleVec(1.1F, 0.9F, 1.1F), Interpolations.CATMULLROM),
                  new Keyframe(2.75F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
                  new Keyframe(3.0F, KeyframeAnimations.scaleVec(1.1F, 0.9F, 1.1F), Interpolations.CATMULLROM),
                  new Keyframe(3.25F, KeyframeAnimations.scaleVec(0.9F, 1.0, 0.9F), Interpolations.CATMULLROM),
                  new Keyframe(3.5F, KeyframeAnimations.scaleVec(1.1F, 0.9F, 1.1F), Interpolations.CATMULLROM),
                  new Keyframe(3.75F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
                  new Keyframe(4.0F, KeyframeAnimations.scaleVec(1.1F, 0.9F, 1.1F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "tail",
            new AnimationChannel(
               Targets.SCALE,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0, 0.8F, 1.0), Interpolations.CATMULLROM),
                  new Keyframe(0.25F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.scaleVec(1.0, 0.8F, 1.0), Interpolations.CATMULLROM),
                  new Keyframe(0.75F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.scaleVec(1.0, 0.8F, 1.0), Interpolations.CATMULLROM),
                  new Keyframe(1.25F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
                  new Keyframe(1.5F, KeyframeAnimations.scaleVec(1.0, 0.8F, 1.0), Interpolations.CATMULLROM),
                  new Keyframe(1.75F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
                  new Keyframe(2.0F, KeyframeAnimations.scaleVec(1.0, 0.8F, 1.0), Interpolations.CATMULLROM),
                  new Keyframe(2.25F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
                  new Keyframe(2.5F, KeyframeAnimations.scaleVec(1.0, 0.8F, 1.0), Interpolations.CATMULLROM),
                  new Keyframe(2.75F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
                  new Keyframe(3.0F, KeyframeAnimations.scaleVec(1.0, 0.8F, 1.0), Interpolations.CATMULLROM),
                  new Keyframe(3.25F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
                  new Keyframe(3.5F, KeyframeAnimations.scaleVec(1.0, 0.8F, 1.0), Interpolations.CATMULLROM),
                  new Keyframe(3.75F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
                  new Keyframe(4.0F, KeyframeAnimations.scaleVec(1.0, 0.8F, 1.0), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "eyes",
            new AnimationChannel(
               Targets.SCALE,
               new Keyframe[]{
                  new Keyframe(0.08333F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
                  new Keyframe(0.25F, KeyframeAnimations.scaleVec(1.2F, 0.8F, 1.0), Interpolations.CATMULLROM),
                  new Keyframe(0.41667F, KeyframeAnimations.scaleVec(0.9F, 1.2F, 1.0), Interpolations.CATMULLROM),
                  new Keyframe(0.58333F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
                  new Keyframe(1.16667F, KeyframeAnimations.scaleVec(1.2F, 0.8F, 1.0), Interpolations.CATMULLROM),
                  new Keyframe(1.33333F, KeyframeAnimations.scaleVec(0.9F, 1.2F, 1.0), Interpolations.CATMULLROM),
                  new Keyframe(1.5F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
                  new Keyframe(2.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
                  new Keyframe(2.16667F, KeyframeAnimations.scaleVec(1.2F, 0.8F, 1.0), Interpolations.CATMULLROM),
                  new Keyframe(2.33333F, KeyframeAnimations.scaleVec(0.9F, 1.2F, 1.0), Interpolations.CATMULLROM),
                  new Keyframe(2.5F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
                  new Keyframe(3.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
                  new Keyframe(3.16667F, KeyframeAnimations.scaleVec(1.2F, 0.8F, 1.0), Interpolations.CATMULLROM),
                  new Keyframe(3.33333F, KeyframeAnimations.scaleVec(0.9F, 1.2F, 1.0), Interpolations.CATMULLROM),
                  new Keyframe(3.5F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM)
               }
            )
         )
         .build();
      public static final AnimationDefinition MOVE = Builder.withLength(2.0F)
         .looping()
         .addAnimation(
            "wisp",
            new AnimationChannel(
               Targets.POSITION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 1.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 1.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 1.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.25F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.5F, KeyframeAnimations.posVec(0.0F, 1.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.75F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 1.0F, 0.0F), Interpolations.LINEAR)
               }
            )
         )
         .addAnimation(
            "wisp",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(15.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.degreeVec(25.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.degreeVec(15.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.5F, KeyframeAnimations.degreeVec(25.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(2.0F, KeyframeAnimations.degreeVec(15.0F, 0.0F, 0.0F), Interpolations.LINEAR)
               }
            )
         )
         .addAnimation(
            "body",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(12.5F, 0.0F, 12.5F), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.degreeVec(-12.5F, 0.0F, 12.5F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.degreeVec(12.5F, 0.0F, -12.5F), Interpolations.CATMULLROM),
                  new Keyframe(1.5F, KeyframeAnimations.degreeVec(-12.5F, 0.0F, -12.5F), Interpolations.CATMULLROM),
                  new Keyframe(2.0F, KeyframeAnimations.degreeVec(12.5F, 0.0F, 12.5F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "body",
            new AnimationChannel(
               Targets.SCALE,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.1F, 0.7F, 1.1F), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.scaleVec(1.1F, 0.7F, 1.1F), Interpolations.CATMULLROM),
                  new Keyframe(1.5F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
                  new Keyframe(2.0F, KeyframeAnimations.scaleVec(1.1F, 0.7F, 1.1F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "tail",
            new AnimationChannel(
               Targets.SCALE,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0, 0.8F, 1.0), Interpolations.CATMULLROM),
                  new Keyframe(0.25F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.scaleVec(1.0, 0.8F, 1.0), Interpolations.CATMULLROM),
                  new Keyframe(0.75F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.scaleVec(1.0, 0.8F, 1.0), Interpolations.CATMULLROM),
                  new Keyframe(1.25F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
                  new Keyframe(1.5F, KeyframeAnimations.scaleVec(1.0, 0.8F, 1.0), Interpolations.CATMULLROM),
                  new Keyframe(1.75F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
                  new Keyframe(2.0F, KeyframeAnimations.scaleVec(1.0, 0.8F, 1.0), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "eyes",
            new AnimationChannel(
               Targets.SCALE,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
                  new Keyframe(0.16667F, KeyframeAnimations.scaleVec(1.2F, 0.8F, 1.0), Interpolations.CATMULLROM),
                  new Keyframe(0.33333F, KeyframeAnimations.scaleVec(0.9F, 1.2F, 1.0), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
                  new Keyframe(1.16667F, KeyframeAnimations.scaleVec(1.2F, 0.8F, 1.0), Interpolations.CATMULLROM),
                  new Keyframe(1.33333F, KeyframeAnimations.scaleVec(0.9F, 1.2F, 1.0), Interpolations.CATMULLROM),
                  new Keyframe(1.5F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM)
               }
            )
         )
         .build();
   }

   public static class WispModel<T extends Wisp> extends HierarchicalModel<T> {
      public static final ModelLayerLocation LAYER = new ModelLayerLocation(NetherExp.netherexpPath("wisp"), "main");
      private final ModelPart wisp;
      private final ModelPart body;
      private final ModelPart eyes;
      private final ModelPart tail;

      public WispModel(ModelPart root) {
         this.wisp = root.getChild("wisp");
         this.body = this.wisp.getChild("body");
         this.eyes = this.body.getChild("eyes");
         this.tail = this.body.getChild("tail");
      }

      public static LayerDefinition createBodyLayer() {
         MeshDefinition meshdefinition = new MeshDefinition();
         PartDefinition partdefinition = meshdefinition.getRoot();
         PartDefinition wisp = partdefinition.addOrReplaceChild("wisp", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
         PartDefinition body = wisp.addOrReplaceChild(
            "body",
            CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -4.5F, -4.0F, 8.0F, 9.0F, 8.0F, new CubeDeformation(0.0F)),
            PartPose.offset(0.0F, -7.5F, 0.0F)
         );
         PartDefinition eyes = body.addOrReplaceChild(
            "eyes",
            CubeListBuilder.create().texOffs(0, 17).addBox(-3.0F, -2.5F, 0.0F, 6.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)),
            PartPose.offset(0.0F, 2.0F, -4.1F)
         );
         PartDefinition tail = body.addOrReplaceChild(
            "tail",
            CubeListBuilder.create().texOffs(0, 17).addBox(-4.0F, 0.0F, -4.0F, 8.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)),
            PartPose.offset(0.0F, 4.5F, 0.0F)
         );
         return LayerDefinition.create(meshdefinition, 32, 32);
      }

      public void setupAnim(Wisp entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
         this.root().getAllParts().forEach(ModelPart::resetPose);
         this.animate(entity.idleAnimation, WispRenderer.Animation.IDLE, ageInTicks);
         this.animateWalk(WispRenderer.Animation.MOVE, limbSwing, limbSwingAmount, 2.0F, 2.5F);
      }

      public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
         this.wisp.render(poseStack, buffer, packedLight, packedOverlay, color);
      }

      public ModelPart root() {
         return this.wisp;
      }

      public ModelPart body() {
         return this.body;
      }

      public ModelPart eyes() {
         return this.eyes;
      }

      public ModelPart tail() {
         return this.tail;
      }
   }
}
