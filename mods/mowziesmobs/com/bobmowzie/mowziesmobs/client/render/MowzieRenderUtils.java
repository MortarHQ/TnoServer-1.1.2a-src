package com.bobmowzie.mowziesmobs.client.render;

import com.bobmowzie.mowziesmobs.client.model.tools.MathUtils;
import com.bobmowzie.mowziesmobs.client.model.tools.ModelPartMatrix;
import com.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import com.mojang.math.Axis;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.joml.Vector4f;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.cache.object.GeoCube;

public class MowzieRenderUtils {
   public static void matrixStackFromModel(PoseStack matrixStack, AdvancedModelRenderer modelRenderer) {
      AdvancedModelRenderer parent = modelRenderer.getParent();
      if (parent != null) {
         matrixStackFromModel(matrixStack, parent);
      }

      modelRenderer.translateRotate(matrixStack);
   }

   public static Vec3 getWorldPosFromModel(Entity entity, float entityYaw, AdvancedModelRenderer modelRenderer) {
      PoseStack matrixStack = new PoseStack();
      matrixStack.translate(entity.getX(), entity.getY(), entity.getZ());
      matrixStack.mulPose(MathUtils.quatFromRotationXYZ(0.0F, -entityYaw + 180.0F, 0.0F, true));
      matrixStack.scale(-1.0F, -1.0F, 1.0F);
      matrixStack.translate(0.0F, -1.5F, 0.0F);
      matrixStackFromModel(matrixStack, modelRenderer);
      Pose matrixEntry = matrixStack.last();
      Matrix4f matrix4f = matrixEntry.pose();
      Vector4f vec = new Vector4f(0.0F, 0.0F, 0.0F, 1.0F);
      vec.mul(matrix4f);
      return new Vec3(vec.x(), vec.y(), vec.z());
   }

   public static void translateRotateGeckolib(GeoBone bone, PoseStack matrixStackIn) {
      matrixStackIn.translate(bone.getPivotX() / 16.0F, bone.getPivotY() / 16.0F, bone.getPivotZ() / 16.0F);
      if (bone.getRotZ() != 0.0F) {
         matrixStackIn.mulPose(Axis.ZP.rotation(bone.getRotZ()));
      }

      if (bone.getRotY() != 0.0F) {
         matrixStackIn.mulPose(Axis.YP.rotation(bone.getRotY()));
      }

      if (bone.getRotX() != 0.0F) {
         matrixStackIn.mulPose(Axis.XP.rotation(bone.getRotX()));
      }

      matrixStackIn.scale(bone.getScaleX(), bone.getScaleY(), bone.getScaleZ());
   }

   public static void matrixStackFromModel(PoseStack matrixStack, GeoBone geoBone) {
      GeoBone parent = geoBone.getParent();
      if (parent != null) {
         matrixStackFromModel(matrixStack, parent);
      }

      translateRotateGeckolib(geoBone, matrixStack);
   }

   public static Vec3 getWorldPosFromModel(Entity entity, float entityYaw, GeoBone geoBone) {
      PoseStack matrixStack = new PoseStack();
      matrixStack.translate(entity.getX(), entity.getY(), entity.getZ());
      matrixStack.mulPose(MathUtils.quatFromRotationXYZ(0.0F, -entityYaw + 180.0F, 0.0F, true));
      matrixStack.scale(-1.0F, -1.0F, 1.0F);
      matrixStack.translate(0.0F, -1.5F, 0.0F);
      matrixStackFromModel(matrixStack, geoBone);
      Pose matrixEntry = matrixStack.last();
      Matrix4f matrix4f = matrixEntry.pose();
      Vector4f vec = new Vector4f(0.0F, 0.0F, 0.0F, 1.0F);
      vec.mul(matrix4f);
      return new Vec3(vec.x(), vec.y(), vec.z());
   }

   public static void moveToPivotMirror(PoseStack stack, GeoCube cube) {
      Vec3 pivot = cube.pivot();
      stack.translate(-pivot.x() / 16.0, pivot.y() / 16.0, pivot.z() / 16.0);
   }

   public static void translateAwayFromPivotPointMirror(PoseStack stack, GeoCube cube) {
      Vec3 pivot = cube.pivot();
      stack.translate(pivot.x() / 16.0, -pivot.y() / 16.0, -pivot.z() / 16.0);
   }

   public static void moveToPivotMirror(PoseStack stack, GeoBone bone) {
      stack.translate(-bone.getPivotX() / 16.0F, bone.getPivotY() / 16.0F, bone.getPivotZ() / 16.0F);
   }

   public static void translateAwayFromPivotPointMirror(PoseStack stack, GeoBone bone) {
      stack.translate(bone.getPivotX() / 16.0F, -bone.getPivotY() / 16.0F, -bone.getPivotZ() / 16.0F);
   }

   public static void translateMirror(PoseStack stack, GeoBone bone) {
      stack.translate(bone.getPosX() / 16.0F, bone.getPosY() / 16.0F, bone.getPosZ() / 16.0F);
   }

   public static void rotateMirror(PoseStack stack, GeoBone bone) {
      if (bone.getRotZ() != 0.0F) {
         stack.mulPose(Axis.ZP.rotation(-bone.getRotZ()));
      }

      if (bone.getRotY() != 0.0F) {
         stack.mulPose(Axis.YP.rotation(-bone.getRotY()));
      }

      if (bone.getRotX() != 0.0F) {
         stack.mulPose(Axis.XP.rotation(bone.getRotX()));
      }
   }

   public static void transformStackToModelPart(PoseStack stack, ModelPartMatrix part) {
      stack.last().pose().identity();
      stack.last().normal().identity();
      stack.pushPose();
      stack.last().pose().mul(part.getWorldXform());
      stack.last().normal().mul(part.getWorldNormal());
   }
}
