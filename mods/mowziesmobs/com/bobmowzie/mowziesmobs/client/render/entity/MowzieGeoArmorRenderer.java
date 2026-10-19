package com.bobmowzie.mowziesmobs.client.render.entity;

import com.bobmowzie.mowziesmobs.client.model.tools.MathUtils;
import com.bobmowzie.mowziesmobs.client.model.tools.ModelPartMatrix;
import com.bobmowzie.mowziesmobs.client.model.tools.geckolib.MowzieGeoBone;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.ArmorItem;
import org.joml.Matrix4f;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.util.RenderUtil;

public class MowzieGeoArmorRenderer<T extends ArmorItem & GeoItem> extends GeoArmorRenderer<T> {
   public boolean usingCustomPlayerAnimations = false;

   public MowzieGeoArmorRenderer(GeoModel<T> modelProvider) {
      super(modelProvider);
   }

   public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
      if (this.currentEntity != null) {
         super.renderToBuffer(poseStack, buffer, packedLight, packedOverlay, color);
         this.usingCustomPlayerAnimations = false;
      }
   }

   public void renderRecursively(
      PoseStack poseStack,
      T animatable,
      GeoBone bone,
      RenderType renderType,
      MultiBufferSource bufferIn,
      VertexConsumer buffer,
      boolean isReRender,
      float partialTick,
      int packedLight,
      int packedOverlay,
      int color
   ) {
      poseStack.pushPose();
      if (this.usingCustomPlayerAnimations && bone instanceof MowzieGeoBone && ((MowzieGeoBone)bone).isForceMatrixTransform()) {
         Pose last = poseStack.last();
         last.pose().identity();
         last.normal().identity();
         Matrix4f matrix4f = bone.getWorldSpaceMatrix();
         last.pose().mul(matrix4f);
         last.normal().mul(bone.getWorldSpaceNormal());
         poseStack.mulPose(MathUtils.quatFromRotationXYZ(0.0F, 0.0F, 180.0F, true));
         poseStack.translate(0.0, -1.5, 0.0);
      } else {
         RenderUtil.prepMatrixForBone(poseStack, bone);
      }

      this.renderCubesOfBone(poseStack, bone, buffer, packedLight, packedOverlay, color);
      this.renderChildBones(poseStack, animatable, bone, renderType, bufferIn, buffer, isReRender, partialTick, packedLight, packedOverlay, color);
      poseStack.popPose();
   }

   public void copyFrom(ModelPart modelPart, GeoBone geoBone, float offsetX, float offsetY, float offsetZ) {
      if (this.usingCustomPlayerAnimations && modelPart instanceof ModelPartMatrix other && geoBone instanceof MowzieGeoBone thisBone) {
         thisBone.setWorldSpaceNormal(other.getWorldNormal());
         Matrix4f newMatrix = new Matrix4f();
         newMatrix.translate(offsetX / 16.0F, -offsetY / 16.0F, offsetZ / 16.0F);
         Matrix4f worldXform = new Matrix4f(other.getWorldXform());
         Matrix4f offsetMatrix = worldXform.mul(newMatrix);
         thisBone.setWorldSpaceMatrix(offsetMatrix);
         thisBone.setForceMatrixTransform(true);
      } else {
         RenderUtil.matchModelPartRot(modelPart, geoBone);
         geoBone.updatePosition(modelPart.x + offsetX, -modelPart.y + offsetY, modelPart.z + offsetZ);
      }
   }

   protected void applyBaseTransformations(HumanoidModel<?> baseModel) {
      if (this.getHeadBone(this.model) != null) {
         ModelPart headPart = baseModel.head;
         this.copyFrom(headPart, this.getHeadBone(this.model), 0.0F, 0.0F, 0.0F);
      }

      if (this.getBodyBone(this.model) != null) {
         ModelPart bodyPart = baseModel.body;
         this.copyFrom(bodyPart, this.getBodyBone(this.model), 0.0F, 0.0F, 0.0F);
      }

      if (this.getRightArmBone(this.model) != null) {
         ModelPart rightArmPart = baseModel.rightArm;
         this.copyFrom(rightArmPart, this.getRightArmBone(this.model), 5.0F, 2.0F, 0.0F);
      }

      if (this.getLeftArmBone(this.model) != null) {
         ModelPart leftArmPart = baseModel.leftArm;
         this.copyFrom(leftArmPart, this.getLeftArmBone(this.model), -5.0F, 2.0F, 0.0F);
      }

      if (this.getRightLegBone(this.model) != null) {
         ModelPart rightLegPart = baseModel.rightLeg;
         this.copyFrom(rightLegPart, this.getRightLegBone(this.model), 2.0F, 12.0F, 0.0F);
         if (this.getRightBootBone(this.model) != null) {
            this.copyFrom(rightLegPart, this.getRightBootBone(this.model), 2.0F, 12.0F, 0.0F);
         }
      }

      if (this.getLeftLegBone(this.model) != null) {
         ModelPart leftLegPart = baseModel.leftLeg;
         this.copyFrom(leftLegPart, this.getLeftLegBone(this.model), -2.0F, 12.0F, 0.0F);
         if (this.getLeftBootBone(this.model) != null) {
            this.copyFrom(leftLegPart, this.getLeftBootBone(this.model), -2.0F, 12.0F, 0.0F);
         }
      }
   }
}
