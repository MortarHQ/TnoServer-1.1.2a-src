package com.bobmowzie.mowziesmobs.client.model.tools;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import net.minecraft.client.model.geom.ModelPart;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class ModelPartMatrix extends ModelPart {
   private Matrix4f worldXform;
   private Matrix3f worldNormal;
   private boolean resetUseMatrixMode;
   private boolean useMatrixMode;
   private String name;

   public ModelPartMatrix(ModelPart original) {
      this(original, true);
   }

   public ModelPartMatrix(ModelPart original, boolean resetUseMatrixMode) {
      super(original.cubes, original.children);
      this.copyFrom(original);
      this.worldNormal = new Matrix3f();
      this.worldNormal.identity();
      this.worldXform = new Matrix4f();
      this.worldXform.identity();
      this.useMatrixMode = true;
      this.resetUseMatrixMode = resetUseMatrixMode;
   }

   public void setName(String name) {
      this.name = name;
   }

   public void translateAndRotate(PoseStack matrixStackIn) {
      if (this.useMatrixMode && this.getWorldNormal() != null && this.getWorldXform() != null) {
         Pose last = matrixStackIn.last();
         last.pose().identity();
         last.normal().identity();
         last.pose().mul(this.getWorldXform());
         last.normal().mul(this.getWorldNormal());
      } else {
         super.translateAndRotate(matrixStackIn);
      }

      if (this.resetUseMatrixMode) {
         this.useMatrixMode = false;
      }
   }

   public void copyFrom(ModelPart modelRendererIn) {
      if (modelRendererIn instanceof ModelPartMatrix other) {
         this.setWorldNormal(other.getWorldNormal());
         this.setWorldXform(other.getWorldXform());
      }

      super.copyFrom(modelRendererIn);
   }

   public Matrix3f getWorldNormal() {
      return this.worldNormal;
   }

   public void setWorldNormal(Matrix3f worldNormal) {
      this.worldNormal = worldNormal;
   }

   public Matrix4f getWorldXform() {
      return this.worldXform;
   }

   public void setWorldXform(Matrix4f worldXform) {
      this.worldXform = worldXform;
      this.eulerFromMatrix(worldXform);
   }

   public void setUseMatrixMode(boolean useMatrixMode) {
      this.useMatrixMode = useMatrixMode;
   }

   public boolean isUseMatrixMode() {
      return this.useMatrixMode;
   }

   private void eulerFromMatrix(Matrix4f worldXform) {
      Vector3f vec = RigUtils.eulerAnglesXYZFromMatrix(worldXform);
      this.xRot = -vec.x();
      this.yRot = -vec.y();
      this.zRot = vec.z();
      this.x = worldXform.m30();
      this.y = worldXform.m31();
      this.z = worldXform.m32();
   }
}
