package net.jadenxgamer.netherexp.client.rendering.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.core.entity.Vessel;
import net.jadenxgamer.netherexp.util.ParticleHelper;
import net.minecraft.client.Minecraft;
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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class VesselRenderer extends MobRenderer<Vessel, VesselRenderer.VesselModel<Vessel>> {
   public VesselRenderer(Context context) {
      super(context, new VesselRenderer.VesselModel(context.bakeLayer(VesselRenderer.VesselModel.LAYER)), 0.5F);
      this.addLayer(new VesselRenderer.VesselGlowLayer(this));
   }

   public void render(Vessel entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
      poseStack.pushPose();
      if (entity.isDoom()) {
         Vec3 cameraPos = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
         double dx = cameraPos.x - entity.getX();
         double dz = cameraPos.z - entity.getZ();
         float yRot = (float)(Math.atan2(dx, dz) * 180.0 / Math.PI);
         poseStack.mulPose(Axis.YP.rotationDegrees(yRot));
         poseStack.scale(1.0F, 1.0F, 0.01F);
      }

      super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
      poseStack.popPose();
      if (entity.armSmoke && !entity.isDoom()) {
         this.renderSmokeParticles(entity, partialTicks);
      }

      if (entity.armFlash && !entity.isDoom()) {
         this.renderFlashParticles(entity, partialTicks);
      }
   }

   private void renderSmokeParticles(Vessel entity, float partialTicks) {
      if (entity.tickCount % 2 == 0) {
         VesselRenderer.VesselModel<Vessel> model = (VesselRenderer.VesselModel<Vessel>)this.getModel();
         Vec3 leftPos = ParticleHelper.calculateBoneWorldPosition(
            entity, partialTicks, model.vessel, model.body, model.waist, model.left_arm, model.left_arm_anchor
         );
         Vessel.Client.cooldownParticle(entity.level(), entity.getRandom(), leftPos.x, leftPos.y, leftPos.z);
         Vec3 rightPos = ParticleHelper.calculateBoneWorldPosition(
            entity, partialTicks, model.vessel, model.body, model.waist, model.right_arm, model.right_arm_anchor
         );
         Vessel.Client.cooldownParticle(entity.level(), entity.getRandom(), rightPos.x, rightPos.y, rightPos.z);
      }
   }

   private void renderFlashParticles(Vessel entity, float partialTicks) {
      VesselRenderer.VesselModel<Vessel> model = (VesselRenderer.VesselModel<Vessel>)this.getModel();
      Vec3 leftPos = ParticleHelper.calculateBoneWorldPosition(
         entity, partialTicks, model.vessel, model.body, model.waist, model.left_arm, model.left_arm_anchor
      );
      Vessel.Client.shotgunFlashParticle(entity.level(), entity.getRandom(), leftPos.x, leftPos.y, leftPos.z);
      Vec3 rightPos = ParticleHelper.calculateBoneWorldPosition(
         entity, partialTicks, model.vessel, model.body, model.waist, model.right_arm, model.right_arm_anchor
      );
      Vessel.Client.shotgunFlashParticle(entity.level(), entity.getRandom(), rightPos.x, rightPos.y, rightPos.z);
      entity.armFlash = false;
   }

   @NotNull
   public ResourceLocation getTextureLocation(Vessel entity) {
      return NetherExp.netherexpPath("textures/entity/vessel.png");
   }

   private static class Animation {
      public static final AnimationDefinition PREPARE_AIM = Builder.withLength(1.0F)
         .addAnimation(
            "head",
            new AnimationChannel(
               Targets.POSITION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.125F, KeyframeAnimations.posVec(0.0F, 0.0F, 3.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.2916767F, KeyframeAnimations.posVec(0.0F, 1.0F, -5.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.4583433F, KeyframeAnimations.posVec(0.0F, -4.0F, -12.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.625F, KeyframeAnimations.posVec(0.0F, -0.03F, -1.87F), Interpolations.CATMULLROM),
                  new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, -2.0F, 6.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "head",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.16766666F, KeyframeAnimations.degreeVec(-41.25F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.2916767F, KeyframeAnimations.degreeVec(-51.25F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.4583433F, KeyframeAnimations.degreeVec(63.75F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.75F, KeyframeAnimations.degreeVec(-57.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.875F, KeyframeAnimations.degreeVec(-75.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.degreeVec(-57.5F, 0.0F, 0.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "waist",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.16766666F, KeyframeAnimations.degreeVec(-7.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.4167667F, KeyframeAnimations.degreeVec(35.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.75F, KeyframeAnimations.degreeVec(-15.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "left_arm",
            new AnimationChannel(
               Targets.POSITION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.2916767F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.71F), Interpolations.CATMULLROM),
                  new Keyframe(0.5834334F, KeyframeAnimations.posVec(0.0F, 0.0F, -5.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.0F, -5.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "left_arm",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.16766666F, KeyframeAnimations.degreeVec(25.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.375F, KeyframeAnimations.degreeVec(50.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.5834334F, KeyframeAnimations.degreeVec(-127.92F, -7.92F, 6.13F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.degreeVec(-69.81F, -9.76F, -2.19F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "right_arm",
            new AnimationChannel(
               Targets.POSITION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.3433333F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.71F), Interpolations.CATMULLROM),
                  new Keyframe(0.7083434F, KeyframeAnimations.posVec(0.0F, 0.0F, -5.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.0F, -5.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "right_arm",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.20834334F, KeyframeAnimations.degreeVec(25.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.4167667F, KeyframeAnimations.degreeVec(50.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.7083434F, KeyframeAnimations.degreeVec(-127.92F, 7.92F, -6.13F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.degreeVec(-67.31F, 9.76F, 2.19F), Interpolations.CATMULLROM)
               }
            )
         )
         .build();
      public static final AnimationDefinition AIM = Builder.withLength(1.0F)
         .looping()
         .addAnimation(
            "head",
            new AnimationChannel(Targets.POSITION, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -2.0F, 6.0F), Interpolations.CATMULLROM)})
         )
         .addAnimation(
            "head",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(-57.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.25F, KeyframeAnimations.degreeVec(-57.1F, -8.42F, -5.41F), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.degreeVec(-57.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.75F, KeyframeAnimations.degreeVec(-57.1F, 8.42F, 5.41F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.degreeVec(-57.5F, 0.0F, 0.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "waist",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(-15.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.25F, KeyframeAnimations.degreeVec(-15.01F, 2.41F, -0.65F), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.degreeVec(-15.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.75F, KeyframeAnimations.degreeVec(-15.01F, -2.41F, 0.65F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.degreeVec(-15.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "left_arm",
            new AnimationChannel(
               Targets.POSITION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, -5.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.2916767F, KeyframeAnimations.posVec(0.0F, -0.5F, -5.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.5416766F, KeyframeAnimations.posVec(0.0F, 0.25F, -5.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, -0.5F, -5.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.0F, -5.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "left_arm",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(-69.81F, -9.76F, -2.19F), Interpolations.CATMULLROM),
                  new Keyframe(0.25F, KeyframeAnimations.degreeVec(-64.81F, -9.76F, -2.19F), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.degreeVec(-64.36F, -14.28F, -4.38F), Interpolations.CATMULLROM),
                  new Keyframe(0.75F, KeyframeAnimations.degreeVec(-69.36F, -14.28F, -4.38F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.degreeVec(-69.81F, -9.76F, -2.19F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "right_arm",
            new AnimationChannel(
               Targets.POSITION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -0.5F, -5.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.20834334F, KeyframeAnimations.posVec(0.0F, 0.25F, -5.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.4583433F, KeyframeAnimations.posVec(0.0F, 0.0F, -5.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.7083434F, KeyframeAnimations.posVec(0.0F, -0.5F, -5.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, -0.5F, -5.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "right_arm",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(-69.36F, 14.28F, 4.38F), Interpolations.CATMULLROM),
                  new Keyframe(0.25F, KeyframeAnimations.degreeVec(-69.81F, 9.76F, 2.19F), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.degreeVec(-69.81F, 9.76F, 2.19F), Interpolations.CATMULLROM),
                  new Keyframe(0.75F, KeyframeAnimations.degreeVec(-64.81F, 9.76F, 2.19F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.degreeVec(-69.36F, 14.28F, 4.38F), Interpolations.CATMULLROM)
               }
            )
         )
         .build();
      public static final AnimationDefinition SHOOT = Builder.withLength(2.0F)
         .addAnimation(
            "head",
            new AnimationChannel(
               Targets.POSITION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -2.0F, 6.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.125F, KeyframeAnimations.posVec(0.0F, -6.0F, 15.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.2916767F, KeyframeAnimations.posVec(0.0F, 1.0F, 8.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.4583433F, KeyframeAnimations.posVec(0.0F, -1.0F, 6.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.625F, KeyframeAnimations.posVec(0.0F, -1.0F, -8.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, -6.0F, -15.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, -10.0F, -17.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.4167666F, KeyframeAnimations.posVec(0.0F, -10.0F, -17.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.625F, KeyframeAnimations.posVec(1.0F, 1.0F, 2.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.75F, KeyframeAnimations.posVec(1.0F, 0.0F, 5.0F), Interpolations.CATMULLROM),
                  new Keyframe(2.0F, KeyframeAnimations.posVec(1.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "head",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(-57.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.125F, KeyframeAnimations.degreeVec(-125.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.2916767F, KeyframeAnimations.degreeVec(-125.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.4583433F, KeyframeAnimations.degreeVec(-82.42F, 7.37F, -4.77F), Interpolations.CATMULLROM),
                  new Keyframe(0.625F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.75F, KeyframeAnimations.degreeVec(65.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.degreeVec(105.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.4167666F, KeyframeAnimations.degreeVec(105.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.625F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 12.5F), Interpolations.CATMULLROM),
                  new Keyframe(1.75F, KeyframeAnimations.degreeVec(-32.5F, 0.0F, 12.5F), Interpolations.CATMULLROM),
                  new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 12.5F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "waist",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(-15.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.125F, KeyframeAnimations.degreeVec(-42.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.2916767F, KeyframeAnimations.degreeVec(-15.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.4583433F, KeyframeAnimations.degreeVec(-15.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.75F, KeyframeAnimations.degreeVec(45.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.4167666F, KeyframeAnimations.degreeVec(45.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.625F, KeyframeAnimations.degreeVec(-10.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.75F, KeyframeAnimations.degreeVec(-15.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "left_arm",
            new AnimationChannel(
               Targets.POSITION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, -5.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.8343334F, KeyframeAnimations.posVec(0.0F, 0.0F, -5.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.5F, KeyframeAnimations.posVec(0.0F, 0.0F, -5.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.8343333F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "left_arm",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(-69.81F, -9.76F, -2.19F), Interpolations.CATMULLROM),
                  new Keyframe(0.16766666F, KeyframeAnimations.degreeVec(-136.86F, 14.28F, 4.38F), Interpolations.CATMULLROM),
                  new Keyframe(0.4167667F, KeyframeAnimations.degreeVec(-130.79F, 15.63F, 4.75F), Interpolations.CATMULLROM),
                  new Keyframe(0.625F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.8343334F, KeyframeAnimations.degreeVec(-28.11F, 5.04F, -0.93F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.degreeVec(-59.96F, 5.27F, -0.97F), Interpolations.CATMULLROM),
                  new Keyframe(1.1676667F, KeyframeAnimations.degreeVec(-47.7F, 5.03F, -0.91F), Interpolations.CATMULLROM),
                  new Keyframe(1.3433332F, KeyframeAnimations.degreeVec(-42.46F, 5.27F, -0.97F), Interpolations.CATMULLROM),
                  new Keyframe(1.5F, KeyframeAnimations.degreeVec(-47.7F, 5.03F, -0.91F), Interpolations.CATMULLROM),
                  new Keyframe(1.8343333F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -10.0F), Interpolations.CATMULLROM),
                  new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "right_arm",
            new AnimationChannel(
               Targets.POSITION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, -5.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.875F, KeyframeAnimations.posVec(0.0F, 0.0F, -5.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.5416766F, KeyframeAnimations.posVec(0.0F, 0.0F, -5.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.8343333F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "right_arm",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(-69.81F, -9.76F, -2.19F), Interpolations.CATMULLROM),
                  new Keyframe(0.20834334F, KeyframeAnimations.degreeVec(-136.86F, 14.28F, 4.38F), Interpolations.CATMULLROM),
                  new Keyframe(0.4583433F, KeyframeAnimations.degreeVec(-130.79F, 15.63F, 4.75F), Interpolations.CATMULLROM),
                  new Keyframe(0.6766666F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.875F, KeyframeAnimations.degreeVec(-28.11F, 5.04F, -0.93F), Interpolations.CATMULLROM),
                  new Keyframe(1.0416766F, KeyframeAnimations.degreeVec(-59.96F, 5.27F, -0.97F), Interpolations.CATMULLROM),
                  new Keyframe(1.2083433F, KeyframeAnimations.degreeVec(-47.7F, 5.03F, -0.91F), Interpolations.CATMULLROM),
                  new Keyframe(1.375F, KeyframeAnimations.degreeVec(-42.46F, 5.27F, -0.97F), Interpolations.CATMULLROM),
                  new Keyframe(1.5416766F, KeyframeAnimations.degreeVec(-47.7F, 5.03F, -0.91F), Interpolations.CATMULLROM),
                  new Keyframe(1.8343333F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -10.0F), Interpolations.CATMULLROM),
                  new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "eyes",
            new AnimationChannel(
               Targets.POSITION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, -1.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.6766667F, KeyframeAnimations.posVec(0.0F, -1.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.75F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "eyes",
            new AnimationChannel(
               Targets.SCALE,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
                  new Keyframe(0.75F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.scaleVec(1.2F, 0.3F, 1.0), Interpolations.CATMULLROM),
                  new Keyframe(1.6766667F, KeyframeAnimations.scaleVec(1.2F, 0.3F, 1.0), Interpolations.CATMULLROM),
                  new Keyframe(1.75F, KeyframeAnimations.scaleVec(1.0, 1.5, 1.0), Interpolations.CATMULLROM),
                  new Keyframe(2.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM)
               }
            )
         )
         .build();
      public static final AnimationDefinition MOVE = Builder.withLength(1.0F)
         .looping()
         .addAnimation(
            "vessel",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 5.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, -5.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 5.0F, 0.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "head",
            new AnimationChannel(
               Targets.POSITION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.posVec(1.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.16766666F, KeyframeAnimations.posVec(1.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.posVec(-1.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.6766666F, KeyframeAnimations.posVec(-1.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.posVec(1.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "head",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 12.5F), Interpolations.CATMULLROM),
                  new Keyframe(0.16766666F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 17.5F), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -12.5F), Interpolations.CATMULLROM),
                  new Keyframe(0.6766666F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -17.5F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 12.5F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "body",
            new AnimationChannel(
               Targets.POSITION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.posVec(1.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.posVec(-1.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.posVec(1.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "body",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 5.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -5.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 5.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "left_arm",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(12.5F, 0.0F, -10.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.25F, KeyframeAnimations.degreeVec(21.62F, 0.0F, -7.4F), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -5.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.7083434F, KeyframeAnimations.degreeVec(-5.93F, 0.0F, -6.63F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.degreeVec(12.5F, 0.0F, -10.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "right_arm",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(12.5F, 0.0F, 10.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.25F, KeyframeAnimations.degreeVec(21.62F, 0.0F, 7.4F), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 5.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.7083434F, KeyframeAnimations.degreeVec(-5.93F, 0.0F, 6.63F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.degreeVec(12.5F, 0.0F, 10.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "left_leg",
            new AnimationChannel(
               Targets.POSITION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.43F, -0.3F), Interpolations.CATMULLROM),
                  new Keyframe(0.16766666F, KeyframeAnimations.posVec(0.0F, -0.6F, 0.03F), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, -1.0F, 1.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, 0.69F, 0.41F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.43F, -0.3F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "left_leg",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(-27.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.degreeVec(12.46F, 0.4F, 0.52F), Interpolations.CATMULLROM),
                  new Keyframe(0.75F, KeyframeAnimations.degreeVec(19.04F, 0.23F, 0.29F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.degreeVec(-27.5F, 0.0F, 0.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "right_leg",
            new AnimationChannel(
               Targets.POSITION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -1.0F, 1.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, 0.69F, 0.41F), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.43F, -0.3F), Interpolations.CATMULLROM),
                  new Keyframe(0.6766666F, KeyframeAnimations.posVec(0.0F, -0.6F, 0.03F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, -1.0F, 1.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "right_leg",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(12.46F, 0.4F, 0.52F), Interpolations.CATMULLROM),
                  new Keyframe(0.25F, KeyframeAnimations.degreeVec(19.04F, 0.23F, 0.29F), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.degreeVec(-27.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.degreeVec(12.46F, 0.4F, 0.52F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "waist",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 12.5F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.25F, KeyframeAnimations.degreeVec(4.96F, 10.01F, -0.22F), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, -12.5F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.75F, KeyframeAnimations.degreeVec(4.96F, -10.01F, 0.22F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 12.5F, 0.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .build();
      public static final AnimationDefinition IDLE = Builder.withLength(2.0F)
         .looping()
         .addAnimation(
            "head",
            new AnimationChannel(
               Targets.POSITION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.posVec(1.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.25F, KeyframeAnimations.posVec(1.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.625F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.posVec(-1.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.25F, KeyframeAnimations.posVec(-1.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.625F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(2.0F, KeyframeAnimations.posVec(1.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "head",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 12.5F), Interpolations.CATMULLROM),
                  new Keyframe(0.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 17.5F), Interpolations.CATMULLROM),
                  new Keyframe(0.625F, KeyframeAnimations.degreeVec(0.0F, -10.0F, 15.63F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -12.5F), Interpolations.CATMULLROM),
                  new Keyframe(1.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -17.5F), Interpolations.CATMULLROM),
                  new Keyframe(1.625F, KeyframeAnimations.degreeVec(0.0F, 7.5F, -20.31F), Interpolations.CATMULLROM),
                  new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 12.5F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "body",
            new AnimationChannel(
               Targets.POSITION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.posVec(1.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.posVec(-1.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(2.0F, KeyframeAnimations.posVec(1.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "body",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 5.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -5.0F), Interpolations.CATMULLROM),
                  new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 5.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "left_arm",
            new AnimationChannel(
               Targets.POSITION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.4583433F, KeyframeAnimations.posVec(0.0F, -0.5F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.9583434F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.5F, KeyframeAnimations.posVec(0.0F, 0.5F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "left_arm",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -10.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.degreeVec(14.98F, 0.44F, -12.17F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -5.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.5416766F, KeyframeAnimations.degreeVec(-15.1F, 0.43F, -11.88F), Interpolations.CATMULLROM),
                  new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -10.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "right_arm",
            new AnimationChannel(
               Targets.POSITION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.5F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.4583433F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.9583434F, KeyframeAnimations.posVec(0.0F, -0.5F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.4583433F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 0.5F, 0.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "right_arm",
            new AnimationChannel(
               Targets.ROTATION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(-15.1F, -0.43F, 11.88F), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 10.0F), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.degreeVec(4.98F, -0.44F, 12.17F), Interpolations.CATMULLROM),
                  new Keyframe(1.5F, KeyframeAnimations.degreeVec(14.98F, -0.44F, 12.17F), Interpolations.CATMULLROM),
                  new Keyframe(2.0F, KeyframeAnimations.degreeVec(-15.1F, -0.43F, 11.88F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "left_leg",
            new AnimationChannel(
               Targets.POSITION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
                  new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
               }
            )
         )
         .build();
      public static final AnimationDefinition BLINK = Builder.withLength(0.5F)
         .addAnimation(
            "eyes",
            new AnimationChannel(
               Targets.POSITION,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.125F, KeyframeAnimations.posVec(0.0F, -1.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.20834334F, KeyframeAnimations.posVec(0.0F, -1.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.3433333F, KeyframeAnimations.posVec(0.0F, 1.0F, 0.0F), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
               }
            )
         )
         .addAnimation(
            "eyes",
            new AnimationChannel(
               Targets.SCALE,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
                  new Keyframe(0.125F, KeyframeAnimations.scaleVec(1.2F, 0.3F, 1.0), Interpolations.CATMULLROM),
                  new Keyframe(0.20834334F, KeyframeAnimations.scaleVec(1.2F, 0.3F, 1.0), Interpolations.CATMULLROM),
                  new Keyframe(0.3433333F, KeyframeAnimations.scaleVec(0.9F, 1.6F, 1.0), Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM)
               }
            )
         )
         .build();
   }

   public static class VesselGlowLayer extends EyesLayer<Vessel, VesselRenderer.VesselModel<Vessel>> {
      public VesselGlowLayer(RenderLayerParent<Vessel, VesselRenderer.VesselModel<Vessel>> renderLayerParent) {
         super(renderLayerParent);
      }

      @NotNull
      public RenderType renderType() {
         return RenderType.eyes(NetherExp.netherexpPath("textures/entity/vessel_glow.png"));
      }
   }

   public static class VesselModel<T extends Vessel> extends HierarchicalModel<T> {
      public static final ModelLayerLocation LAYER = new ModelLayerLocation(NetherExp.netherexpPath("vessel"), "main");
      private final ModelPart vessel;
      private final ModelPart head;
      private final ModelPart body;
      private final ModelPart waist;
      private final ModelPart collar;
      private final ModelPart eyes;
      private final ModelPart left_arm;
      private final ModelPart left_arm_anchor;
      private final ModelPart right_arm;
      private final ModelPart right_arm_anchor;
      private final ModelPart left_leg;
      private final ModelPart right_leg;

      public VesselModel(ModelPart root) {
         this.vessel = root.getChild("vessel");
         this.head = this.vessel.getChild("head");
         this.body = this.vessel.getChild("body");
         this.waist = this.body.getChild("waist");
         this.collar = this.waist.getChild("collar");
         this.eyes = this.collar.getChild("eyes");
         this.left_arm = this.waist.getChild("left_arm");
         this.left_arm_anchor = this.left_arm.getChild("left_arm_anchor");
         this.right_arm = this.waist.getChild("right_arm");
         this.right_arm_anchor = this.right_arm.getChild("right_arm_anchor");
         this.left_leg = this.vessel.getChild("left_leg");
         this.right_leg = this.vessel.getChild("right_leg");
      }

      public static LayerDefinition createBodyLayer() {
         MeshDefinition meshdefinition = new MeshDefinition();
         PartDefinition partdefinition = meshdefinition.getRoot();
         PartDefinition vessel = partdefinition.addOrReplaceChild("vessel", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
         PartDefinition head = vessel.addOrReplaceChild(
            "head",
            CubeListBuilder.create()
               .texOffs(0, 0)
               .addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
               .texOffs(0, 16)
               .addBox(0.0F, -9.0F, -5.0F, 5.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)),
            PartPose.offset(0.0F, -35.0F, 0.0F)
         );
         PartDefinition body = vessel.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, -35.25F, 0.0F));
         PartDefinition waist = body.addOrReplaceChild(
            "waist",
            CubeListBuilder.create()
               .texOffs(0, 47)
               .addBox(-3.0F, -13.0F, -2.0F, 6.0F, 9.0F, 4.0F, new CubeDeformation(0.0F))
               .texOffs(0, 27)
               .addBox(-4.0F, -13.0F, -3.0F, 8.0F, 14.0F, 6.0F, new CubeDeformation(-0.01F)),
            PartPose.offset(0.0F, 19.25F, 0.0F)
         );
         PartDefinition collar = waist.addOrReplaceChild(
            "collar",
            CubeListBuilder.create().texOffs(22, 8).addBox(-8.0F, -3.0F, -5.0F, 16.0F, 6.0F, 10.0F, new CubeDeformation(0.0F)),
            PartPose.offset(0.0F, -16.0F, 0.0F)
         );
         PartDefinition eyes = collar.addOrReplaceChild(
            "eyes",
            CubeListBuilder.create().texOffs(24, 0).addBox(-6.0F, -2.0F, 0.0F, 12.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)),
            PartPose.offset(0.0F, 0.0F, -5.1F)
         );
         PartDefinition left_arm = waist.addOrReplaceChild(
            "left_arm",
            CubeListBuilder.create()
               .texOffs(44, 35)
               .addBox(-1.0F, 6.0F, -2.0F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.0F))
               .texOffs(36, 27)
               .addBox(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)),
            PartPose.offset(5.0F, -13.0F, 0.0F)
         );
         PartDefinition left_arm_anchor = left_arm.addOrReplaceChild("left_arm_anchor", CubeListBuilder.create(), PartPose.offset(1.0F, 17.0F, 0.0F));
         PartDefinition right_arm = waist.addOrReplaceChild(
            "right_arm",
            CubeListBuilder.create()
               .texOffs(28, 35)
               .addBox(-3.0F, 6.0F, -2.0F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.0F))
               .texOffs(28, 27)
               .addBox(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)),
            PartPose.offset(-5.0F, -13.0F, 0.0F)
         );
         PartDefinition right_arm_anchor = right_arm.addOrReplaceChild("right_arm_anchor", CubeListBuilder.create(), PartPose.offset(-1.0F, 17.0F, 0.0F));
         PartDefinition left_leg = vessel.addOrReplaceChild(
            "left_leg",
            CubeListBuilder.create().texOffs(8, 60).addBox(-1.0F, -0.5F, -1.0F, 2.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)),
            PartPose.offset(2.0F, -14.5F, 0.0F)
         );
         PartDefinition right_leg = vessel.addOrReplaceChild(
            "right_leg",
            CubeListBuilder.create().texOffs(0, 60).addBox(-1.0F, -0.5F, -1.0F, 2.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)),
            PartPose.offset(-2.0F, -14.5F, 0.0F)
         );
         return LayerDefinition.create(meshdefinition, 128, 128);
      }

      public void setupAnim(Vessel entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
         this.root().getAllParts().forEach(ModelPart::resetPose);
         this.applyHeadRotation(netHeadYaw, headPitch);
         this.animate(entity.idleAnimation, VesselRenderer.Animation.IDLE, ageInTicks);
         this.animate(entity.prepareAimAnimation, VesselRenderer.Animation.PREPARE_AIM, ageInTicks);
         this.animate(entity.aimAnimation, VesselRenderer.Animation.AIM, ageInTicks);
         this.animate(entity.shootAnimation, VesselRenderer.Animation.SHOOT, ageInTicks);
         this.animate(entity.blinkAnimation, VesselRenderer.Animation.BLINK, ageInTicks);
         this.animateWalk(VesselRenderer.Animation.MOVE, limbSwing, limbSwingAmount, 2.0F, 2.5F);
      }

      private void applyHeadRotation(float pNetHeadYaw, float pHeadPitch) {
         pNetHeadYaw = Mth.clamp(pNetHeadYaw, -30.0F, 30.0F);
         pHeadPitch = Mth.clamp(pHeadPitch, -25.0F, 45.0F);
         this.head.yRot = pNetHeadYaw * (float) (Math.PI / 180.0);
         this.head.xRot = pHeadPitch * (float) (Math.PI / 180.0);
      }

      public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
         this.vessel.render(poseStack, buffer, packedLight, packedOverlay, color);
      }

      public ModelPart root() {
         return this.vessel;
      }
   }
}
