package com.bobmowzie.mowziesmobs.client.render.entity;

import com.bobmowzie.mowziesmobs.client.model.tools.dynamics.GeckoDynamicChain;
import com.bobmowzie.mowziesmobs.client.model.tools.geckolib.MowzieGeoBone;
import com.bobmowzie.mowziesmobs.client.model.tools.geckolib.MowzieGeoModel;
import com.bobmowzie.mowziesmobs.server.entity.MowzieGeckoEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.util.RenderUtil;

public abstract class MowzieGeoEntityRenderer<T extends MowzieGeckoEntity> extends GeoEntityRenderer<T> {
   protected MowzieGeoEntityRenderer(Context renderManager, GeoModel<T> modelProvider) {
      super(renderManager, modelProvider);
   }

   public RenderType getRenderType(T animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
      return RenderType.entityCutoutNoCull(texture);
   }

   public MowzieGeoModel<T> getMowzieGeoModel() {
      return (MowzieGeoModel<T>)super.getGeoModel();
   }

   protected float getDeathMaxRotation(T animatable) {
      return 0.0F;
   }

   public void render(T entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
      super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
      float currentTime = entity.tickCount + Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(false);
      if (entity != null && currentTime != entity.lastRenderUpdateTime) {
         this.renderUpdates(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
      }

      entity.lastRenderUpdateTime = currentTime;
   }

   public void renderUpdates(T entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
   }

   public void actuallyRender(
      PoseStack poseStack,
      T animatable,
      BakedGeoModel model,
      RenderType renderType,
      MultiBufferSource bufferSource,
      VertexConsumer buffer,
      boolean isReRender,
      float partialTick,
      int packedLight,
      int packedOverlay,
      int color
   ) {
      if (animatable.dynamicChains != null) {
         for (GeckoDynamicChain chain : animatable.dynamicChains) {
            if (chain.chainOrig != null) {
               for (int i = 0; i < chain.chainOrig.length; i++) {
                  chain.chainOrig[i].setHidden(true);
               }
            }
         }
      }

      super.actuallyRender(poseStack, animatable, model, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, color);
      if (animatable.dynamicChains != null) {
         for (GeckoDynamicChain chain : animatable.dynamicChains) {
            if (!isReRender) {
               chain.setChain();
               chain.updateChain(partialTick, this);
            }

            poseStack.pushPose();
            if (chain.chainDynamic != null) {
               for (GeoBone group : chain.chainDynamic) {
                  this.renderRecursively(
                     poseStack, animatable, group, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, color
                  );
               }
            }

            poseStack.popPose();
            if (chain.chainOrig != null) {
               for (int i = 0; i < chain.chainOrig.length; i++) {
                  chain.chainOrig[i].setHidden(false);
               }
            }
         }
      }
   }

   public void renderRecursively(
      PoseStack poseStack,
      T animatable,
      GeoBone bone,
      RenderType renderType,
      MultiBufferSource bufferSource,
      VertexConsumer buffer,
      boolean isReRender,
      float partialTick,
      int packedLight,
      int packedOverlay,
      int color
   ) {
      if (bone != null) {
         poseStack.pushPose();
         if (bone instanceof MowzieGeoBone mowzieGeoBone && mowzieGeoBone.isForceMatrixTransform()) {
            Pose last = poseStack.last();
            float lerpBodyRot = animatable == null ? 0.0F : Mth.rotLerp(partialTick, animatable.yBodyRotO, animatable.yBodyRot);
            double d0 = Mth.lerp(partialTick, animatable.xOld, animatable.getX());
            double d1 = Mth.lerp(partialTick, animatable.yOld, animatable.getY());
            double d2 = Mth.lerp(partialTick, animatable.zOld, animatable.getZ());
            Matrix4f matrix4f = new Matrix4f();
            matrix4f = matrix4f.translate(0.0F, -0.01F, 0.0F);
            matrix4f = matrix4f.translate((float)(-d0), (float)(-d1), (float)(-d2));
            matrix4f = matrix4f.mul(bone.getWorldSpaceMatrix());
            matrix4f = matrix4f.rotate(Axis.YP.rotationDegrees(-180.0F - lerpBodyRot));
            last.pose().mul(matrix4f);
            last.normal().mul(bone.getWorldSpaceNormal());
            RenderUtil.translateAwayFromPivotPoint(poseStack, bone);
         } else {
            boolean rotOverride = false;
            if (bone instanceof MowzieGeoBone mowzieGeoBone) {
               rotOverride = mowzieGeoBone.rotationOverride != null;
            }

            RenderUtil.translateMatrixToBone(poseStack, bone);
            RenderUtil.translateToPivotPoint(poseStack, bone);
            if (bone instanceof MowzieGeoBone mowzieGeoBone) {
               if (!mowzieGeoBone.inheritRotation && !mowzieGeoBone.inheritTranslation) {
                  poseStack.last().pose().identity();
                  poseStack.last().pose().mul(this.entityRenderTranslations);
               } else if (!mowzieGeoBone.inheritRotation) {
                  Vector4f t = new Vector4f().mul(poseStack.last().pose());
                  poseStack.last().pose().identity();
                  poseStack.translate(t.x, t.y, t.z);
               } else if (!mowzieGeoBone.inheritTranslation) {
                  MowzieGeoBone.removeMatrixTranslation(poseStack.last().pose());
                  poseStack.last().pose().mul(this.entityRenderTranslations);
               }
            }

            if (rotOverride) {
               MowzieGeoBone mowzieGeoBone = (MowzieGeoBone)bone;
               poseStack.last().pose().mul(mowzieGeoBone.rotationOverride);
               poseStack.last().normal().mul(new Matrix3f(mowzieGeoBone.rotationOverride));
            } else {
               RenderUtil.rotateMatrixAroundBone(poseStack, bone);
            }

            RenderUtil.scaleMatrixForBone(poseStack, bone);
            if (bone.isTrackingMatrices()) {
               Matrix4f poseState = new Matrix4f(poseStack.last().pose());
               Matrix4f localMatrix = RenderUtil.invertAndMultiplyMatrices(poseState, this.entityRenderTranslations);
               if (bone instanceof MowzieGeoBone mowzieGeoBone) {
                  mowzieGeoBone.setPose(poseState);
               }

               bone.setModelSpaceMatrix(RenderUtil.invertAndMultiplyMatrices(poseState, this.modelRenderTranslations));
               bone.setLocalSpaceMatrix(RenderUtil.translateMatrix(localMatrix, this.getRenderOffset((MowzieGeckoEntity)this.animatable, 1.0F).toVector3f()));
               double d0 = Mth.lerp(partialTick, animatable.xOld, animatable.getX());
               double d1 = Mth.lerp(partialTick, animatable.yOld, animatable.getY());
               double d2 = Mth.lerp(partialTick, animatable.zOld, animatable.getZ());
               bone.setWorldSpaceMatrix(RenderUtil.translateMatrix(new Matrix4f(localMatrix), new Vector3f((float)d0, (float)d1, (float)d2)));
            }

            RenderUtil.translateAwayFromPivotPoint(poseStack, bone);
         }

         buffer = this.checkAndRefreshBuffer(isReRender, buffer, bufferSource, renderType);
         this.renderCubesOfBone(poseStack, bone, buffer, packedLight, packedOverlay, color);
         if (!isReRender) {
            this.applyRenderLayersForBone(poseStack, animatable, bone, renderType, bufferSource, buffer, partialTick, packedLight, packedOverlay);
         }

         this.renderChildBones(poseStack, animatable, bone, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, color);
         poseStack.popPose();
      }
   }

   public void renderChildBones(
      PoseStack poseStack,
      T animatable,
      GeoBone bone,
      RenderType renderType,
      MultiBufferSource bufferSource,
      VertexConsumer buffer,
      boolean isReRender,
      float partialTick,
      int packedLight,
      int packedOverlay,
      int color
   ) {
      for (GeoBone childBone : bone.getChildBones()) {
         if (!bone.isHidingChildren() || childBone instanceof MowzieGeoBone mowzieGeoBone && mowzieGeoBone.isDynamicJoint()) {
            this.renderRecursively(
               poseStack, animatable, childBone, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, color
            );
         }
      }
   }

   public Matrix4f getModelRenderMatrix() {
      return this.modelRenderTranslations;
   }
}
