package com.bobmowzie.mowziesmobs.client.model.tools.geckolib;

import com.bobmowzie.mowziesmobs.client.model.tools.MathUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import javax.annotation.Nullable;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.joml.Vector3d;
import org.joml.Vector4f;
import software.bernie.geckolib.cache.object.GeoBone;

public class MowzieGeoBone extends GeoBone {
   public Matrix4f rotationOverride;
   public boolean inheritRotation = true;
   public boolean inheritTranslation = true;
   protected boolean forceMatrixTransform = false;
   private Matrix4f pose;
   private boolean isDynamicJoint = false;

   public MowzieGeoBone(@Nullable GeoBone parent, String name, Boolean mirror, @Nullable Double inflate, @Nullable Boolean dontRender, @Nullable Boolean reset) {
      super(parent, name, mirror, inflate, dontRender, reset);
      this.rotationOverride = null;
      this.pose = new Matrix4f();
   }

   public MowzieGeoBone(MowzieGeoBone geoBone) {
      super(null, geoBone.getName() + "_chain", geoBone.getMirror(), geoBone.getInflate(), geoBone.shouldNeverRender(), geoBone.getReset());
      this.setPos(geoBone.getPos());
      this.setRot(geoBone.getRot());
      this.setPivotX(geoBone.getPivotX());
      this.setPivotY(geoBone.getPivotY());
      this.setPivotZ(geoBone.getPivotZ());
      this.setScale(geoBone.getScale());
      this.getCubes().addAll(geoBone.getCubes());
      this.saveInitialSnapshot();
      this.getChildBones().addAll(geoBone.getChildBones().stream().filter(b -> !(b instanceof MowzieGeoBone) || !((MowzieGeoBone)b).isDynamicJoint()).toList());
      this.pose = new Matrix4f();
   }

   public MowzieGeoBone getParent() {
      return (MowzieGeoBone)super.getParent();
   }

   public void addPos(Vec3 vec) {
      this.addPos((float)vec.x(), (float)vec.y(), (float)vec.z());
   }

   public void addPos(float x, float y, float z) {
      this.addPosX(x);
      this.addPosY(y);
      this.addPosZ(z);
   }

   public void addPos(double x, double y, double z) {
      this.addPosX(x);
      this.addPosY(y);
      this.addPosZ(z);
   }

   public void addPosX(float x) {
      this.setPosX(this.getPosX() + x);
   }

   public void addPosX(double x) {
      this.setPosX(this.getPosX() + (float)x);
   }

   public void addPosY(float y) {
      this.setPosY(this.getPosY() + y);
   }

   public void addPosY(double y) {
      this.setPosY(this.getPosY() + (float)y);
   }

   public void addPosZ(float z) {
      this.setPosZ(this.getPosZ() + z);
   }

   public void addPosZ(double z) {
      this.setPosZ(this.getPosZ() + (float)z);
   }

   public void setPos(Vec3 vec) {
      this.setPos((float)vec.x(), (float)vec.y(), (float)vec.z());
   }

   public void setPos(float x, float y, float z) {
      this.setPosX(x);
      this.setPosY(y);
      this.setPosZ(z);
   }

   public Vec3 getPos() {
      return new Vec3(this.getPosX(), this.getPosY(), this.getPosZ());
   }

   public void addRot(Vec3 vec) {
      this.addRot((float)vec.x(), (float)vec.y(), (float)vec.z());
   }

   public void addRot(float x, float y, float z) {
      this.addRotX(x);
      this.addRotY(y);
      this.addRotZ(z);
   }

   public void addRotX(float x) {
      this.setRotX(this.getRotX() + x);
   }

   public void addRotY(float y) {
      this.setRotY(this.getRotY() + y);
   }

   public void addRotZ(float z) {
      this.setRotZ(this.getRotZ() + z);
   }

   public void addRot(double x, double y, double z) {
      this.addRotX(x);
      this.addRotY(y);
      this.addRotZ(z);
   }

   public void addRotX(double x) {
      this.setRotX(this.getRotX() + (float)x);
   }

   public void addRotY(double y) {
      this.setRotY(this.getRotY() + (float)y);
   }

   public void addRotZ(double z) {
      this.setRotZ(this.getRotZ() + (float)z);
   }

   public void setRot(Vector3d vec) {
      this.setRot((float)vec.x(), (float)vec.y(), (float)vec.z());
   }

   public void setRot(Vec3 vec) {
      this.setRot((float)vec.x(), (float)vec.y(), (float)vec.z());
   }

   public void setRot(float x, float y, float z) {
      this.setRotX(x);
      this.setRotY(y);
      this.setRotZ(z);
   }

   public Vector3d getRot() {
      return new Vector3d(this.getRotX(), this.getRotY(), this.getRotZ());
   }

   public void multiplyScale(Vec3 vec) {
      this.multiplyScale((float)vec.x(), (float)vec.y(), (float)vec.z());
   }

   public void multiplyScale(float x, float y, float z) {
      this.setScaleX(this.getScaleX() * x);
      this.setScaleY(this.getScaleY() * y);
      this.setScaleZ(this.getScaleZ() * z);
   }

   public void setScale(Vec3 vec) {
      this.setScale((float)vec.x(), (float)vec.y(), (float)vec.z());
   }

   public void setScale(Vector3d vec) {
      this.setScale((float)vec.x(), (float)vec.y(), (float)vec.z());
   }

   public void setScale(float x, float y, float z) {
      this.setScaleX(x);
      this.setScaleY(y);
      this.setScaleZ(z);
   }

   public void setScale(float scale) {
      this.setScale(scale, scale, scale);
   }

   public Vector3d getScale() {
      return new Vector3d(this.getScaleX(), this.getScaleY(), this.getScaleZ());
   }

   public void addRotationOffsetFromBone(MowzieGeoBone source) {
      this.setRotX(this.getRotX() + source.getRotX() - source.getInitialSnapshot().getRotX());
      this.setRotY(this.getRotY() + source.getRotY() - source.getInitialSnapshot().getRotY());
      this.setRotZ(this.getRotZ() + source.getRotZ() - source.getInitialSnapshot().getRotZ());
   }

   public void setForceMatrixTransform(boolean forceMatrixTransform) {
      this.forceMatrixTransform = forceMatrixTransform;
   }

   public boolean isForceMatrixTransform() {
      return this.forceMatrixTransform;
   }

   public Matrix4f getModelRotationMat() {
      Matrix4f matrix = new Matrix4f(this.getModelSpaceMatrix());
      removeMatrixTranslation(matrix);
      return matrix;
   }

   public static void removeMatrixTranslation(Matrix4f matrix) {
      matrix.m30(0.0F);
      matrix.m31(0.0F);
      matrix.m32(0.0F);
   }

   public void setModelXformOverride(Matrix4f mat) {
      this.rotationOverride = mat;
   }

   public void setWorldPos(Entity entity, Vec3 worldPos, float delta) {
      PoseStack matrixStack = new PoseStack();
      float dx = (float)(entity.xOld + (entity.getX() - entity.xOld) * delta);
      float dy = (float)(entity.yOld + (entity.getY() - entity.yOld) * delta);
      float dz = (float)(entity.zOld + (entity.getZ() - entity.zOld) * delta);
      matrixStack.translate(dx, dy, dz);
      float dYaw = Mth.rotLerp(delta, entity.yRotO, entity.getYRot());
      matrixStack.mulPose(MathUtils.quatFromRotationXYZ(0.0F, -dYaw + 180.0F, 0.0F, true));
      matrixStack.scale(-1.0F, -1.0F, 1.0F);
      matrixStack.translate(0.0F, -1.5F, 0.0F);
      Pose matrixEntry = matrixStack.last();
      Matrix4f matrix4f = matrixEntry.pose();
      matrix4f.invert();
      Vector4f vec = new Vector4f((float)worldPos.x(), (float)worldPos.y(), (float)worldPos.z(), 1.0F);
      vec.mul(matrix4f);
      this.setPosX(vec.x() * 16.0F);
      this.setPosY(vec.y() * 16.0F);
      this.setPosZ(vec.z() * 16.0F);
   }

   public void setDynamicJoint(boolean dynamicJoint) {
      this.isDynamicJoint = dynamicJoint;
   }

   public boolean isDynamicJoint() {
      return this.isDynamicJoint;
   }

   public void setPose(Matrix4f pose) {
      this.pose = pose;
   }

   public Matrix4f getPose() {
      this.setTrackingMatrices(true);
      return this.pose;
   }
}
