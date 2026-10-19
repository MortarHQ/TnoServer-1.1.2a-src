package com.finderfeed.fdlib.systems.bedrock.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class FDModelPart {
   public float x;
   public float y;
   public float z;
   private float xRot;
   private float yRot;
   private float zRot;
   public Vector3f initRotation;
   public boolean isVisible = true;
   public float xScale = 1.0F;
   public float yScale = 1.0F;
   public float zScale = 1.0F;
   public final Vector3f pivot;
   public final List<FDCube> cubes;
   protected final Map<String, FDModelPart> children;
   public String name;
   public FDModelPart parent = null;

   public FDModelPart(String name, FDModelPart parent, List<FDCube> cubes, Vector3f pivot, Vector3f initRotation) {
      this.pivot = new Vector3f(pivot);
      this.x = pivot.x;
      this.y = pivot.y;
      this.z = pivot.z;
      this.name = name;
      this.cubes = Collections.unmodifiableList(cubes);
      this.children = new HashMap<>();
      this.xRot = initRotation.x;
      this.yRot = initRotation.y;
      this.zRot = initRotation.z;
      this.initRotation = new Vector3f(initRotation);
      this.parent = parent;
   }

   public void render(PoseStack matrices, VertexConsumer vertex, int light, int overlay, float r, float g, float b, float a) {
      matrices.pushPose();
      if (this.isVisible) {
         this.transform(matrices);

         for (FDCube cube : this.cubes) {
            cube.render(matrices, vertex, light, overlay, r, g, b, a);
         }

         for (FDModelPart child : this.children.values()) {
            child.render(matrices, vertex, light, overlay, r, g, b, a);
         }
      }

      matrices.popPose();
   }

   public void transform(Matrix4f matrix) {
      float px = this.x / 16.0F;
      float py = this.y / 16.0F;
      float pz = this.z / 16.0F;
      matrix.translate(px, py, pz);
      if (this.xRot != 0.0F || this.yRot != 0.0F || this.zRot != 0.0F) {
         Quaternionf q = new Quaternionf().rotationZYX((float)Math.toRadians(this.zRot), (float)Math.toRadians(this.yRot), (float)Math.toRadians(this.xRot));
         Matrix4f mat = q.get(new Matrix4f());
         matrix.mul(mat);
      }

      matrix.scale(this.xScale, this.yScale, this.zScale);
   }

   @OnlyIn(Dist.CLIENT)
   private void transform(PoseStack matrices) {
      double px = this.x / 16.0F;
      double py = this.y / 16.0F;
      double pz = this.z / 16.0F;
      matrices.translate(px, py, pz);
      if (this.xRot != 0.0F || this.yRot != 0.0F || this.zRot != 0.0F) {
         matrices.mulPose(new Quaternionf().rotationZYX((float)Math.toRadians(this.zRot), (float)Math.toRadians(this.yRot), (float)Math.toRadians(this.xRot)));
      }

      matrices.scale(this.xScale, this.yScale, this.zScale);
   }

   public void reset() {
      this.x = this.pivot.x;
      this.y = this.pivot.y;
      this.z = this.pivot.z;
      this.xRot = this.initRotation.x;
      this.yRot = this.initRotation.y;
      this.zRot = this.initRotation.z;
      this.xScale = 1.0F;
      this.yScale = 1.0F;
      this.zScale = 1.0F;

      for (FDModelPart part : this.children.values()) {
         part.reset();
      }
   }

   public void setVisible(boolean visible) {
      this.isVisible = visible;
   }

   public Map<String, FDModelPart> getChildren() {
      return Collections.unmodifiableMap(this.children);
   }

   public FDModelPart getParent() {
      return this.parent;
   }

   public void addXRot(float value) {
      this.xRot = (this.xRot + value) % 360.0F;
   }

   public void addYRot(float value) {
      this.yRot += value;
   }

   public void addZRot(float value) {
      this.zRot = (this.zRot + value) % 360.0F;
   }

   public void setXRot(float xRot) {
      this.xRot = xRot % 360.0F;
   }

   public void setYRot(float yRot) {
      this.yRot = yRot;
   }

   public void setZRot(float zRot) {
      this.zRot = zRot % 360.0F;
   }

   public float getXScale() {
      return this.xScale;
   }

   public float getYScale() {
      return this.yScale;
   }

   public float getZScale() {
      return this.zScale;
   }

   public float getX() {
      return this.x;
   }

   public float getY() {
      return this.y;
   }

   public float getZ() {
      return this.z;
   }

   public float getXRot() {
      return this.xRot;
   }

   public float getYRot() {
      return this.yRot;
   }

   public float getZRot() {
      return this.zRot;
   }
}
