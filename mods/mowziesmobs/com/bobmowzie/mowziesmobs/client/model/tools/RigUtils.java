package com.bobmowzie.mowziesmobs.client.model.tools;

import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import software.bernie.geckolib.cache.object.GeoBone;

public class RigUtils {
   public static Vec3 lerp(Vec3 v, Vec3 u, float alpha) {
      return new Vec3(Mth.lerp(alpha, (float)v.x(), (float)u.x()), Mth.lerp(alpha, (float)v.y(), (float)u.y()), Mth.lerp(alpha, (float)v.z(), (float)u.z()));
   }

   public static Vec3 lerpAngles(Vec3 v, Vec3 u, float alpha) {
      return new Vec3(
         Math.toRadians(Mth.rotLerp(alpha, (float)Math.toDegrees(v.x()), (float)Math.toDegrees(u.x()))),
         Math.toRadians(Mth.rotLerp(alpha, (float)Math.toDegrees(v.y()), (float)Math.toDegrees(u.y()))),
         Math.toRadians(Mth.rotLerp(alpha, (float)Math.toDegrees(v.z()), (float)Math.toDegrees(u.z())))
      );
   }

   public static Vec3 blendAngles(Vec3 v, Vec3 u, float alpha) {
      return new Vec3(
         Math.toRadians(Mth.wrapDegrees(Math.toDegrees(v.x()) * alpha + Math.toDegrees(u.x()))),
         Math.toRadians(Mth.wrapDegrees(Math.toDegrees(v.y()) * alpha + Math.toDegrees(u.y()))),
         Math.toRadians(Mth.wrapDegrees(Math.toDegrees(v.z()) * alpha + Math.toDegrees(u.z())))
      );
   }

   public static Quaternionf matrixToQuaternion(Matrix3f matrix) {
      double tr = matrix.m00() + matrix.m11() + matrix.m22();
      double qw = 0.0;
      double qx = 0.0;
      double qy = 0.0;
      double qz = 0.0;
      if (tr > 0.0) {
         double S = Math.sqrt(tr + 1.0) * 2.0;
         qw = 0.25 * S;
         qx = (matrix.m21() - matrix.m12()) / S;
         qy = (matrix.m02() - matrix.m20()) / S;
         qz = (matrix.m10() - matrix.m01()) / S;
      } else if (matrix.m00() > matrix.m11() & matrix.m00() > matrix.m22()) {
         double S = Math.sqrt(1.0 + matrix.m00() - matrix.m11() - matrix.m22()) * 2.0;
         qw = (matrix.m21() - matrix.m12()) / S;
         qx = 0.25 * S;
         qy = (matrix.m01() + matrix.m10()) / S;
         qz = (matrix.m02() + matrix.m20()) / S;
      } else if (matrix.m11() > matrix.m22()) {
         double S = Math.sqrt(1.0 + matrix.m11() - matrix.m00() - matrix.m22()) * 2.0;
         qw = (matrix.m02() - matrix.m20()) / S;
         qx = (matrix.m01() + matrix.m10()) / S;
         qy = 0.25 * S;
         qz = (matrix.m12() + matrix.m21()) / S;
      } else {
         double S = Math.sqrt(1.0 + matrix.m22() - matrix.m00() - matrix.m11()) * 2.0;
         qw = (matrix.m10() - matrix.m01()) / S;
         qx = (matrix.m02() + matrix.m20()) / S;
         qy = (matrix.m12() + matrix.m21()) / S;
         qz = 0.25 * S;
      }

      return new Quaternionf((float)qw, (float)qx, (float)qy, (float)qz);
   }

   public static void removeMatrixRotation(Matrix4f matrix) {
      matrix.m00(1.0F);
      matrix.m11(1.0F);
      matrix.m22(1.0F);
      matrix.m01(0.0F);
      matrix.m02(0.0F);
      matrix.m10(0.0F);
      matrix.m12(0.0F);
      matrix.m20(0.0F);
      matrix.m21(0.0F);
   }

   public static void removeMatrixTranslation(Matrix4f matrix) {
      matrix.m03(0.0F);
      matrix.m13(0.0F);
      matrix.m23(0.0F);
   }

   public static Quaternionf betweenVectors(Vec3 u, Vec3 v) {
      Vec3 a = u.cross(v);
      float w = (float)(Math.sqrt(u.lengthSqr() * v.lengthSqr()) + u.dot(v));
      Quaternionf q = new Quaternionf((float)a.x(), -((float)a.y()), -((float)a.z()), w);
      q.normalize();
      return q;
   }

   public static Vector3f translationFromMatrix(Matrix4f matrix4f) {
      return new Vector3f(matrix4f.m03(), matrix4f.m13(), matrix4f.m23());
   }

   public static Vector3f eulerAnglesZYXFromMatrix(Matrix4f matrix4f) {
      float thetaZ;
      float thetaY;
      float thetaX;
      if (matrix4f.m20() < 1.0F) {
         if (matrix4f.m20() > -1.0F) {
            thetaY = (float)Math.asin(-matrix4f.m20());
            thetaZ = (float)Math.atan2(matrix4f.m10(), matrix4f.m00());
            thetaX = (float)Math.atan2(matrix4f.m21(), matrix4f.m22());
         } else {
            thetaY = (float) (Math.PI / 2);
            thetaZ = -((float)Math.atan2(-matrix4f.m12(), matrix4f.m11()));
            thetaX = 0.0F;
         }
      } else {
         thetaY = (float) (-Math.PI / 2);
         thetaZ = (float)Math.atan2(-matrix4f.m12(), matrix4f.m11());
         thetaX = 0.0F;
      }

      return new Vector3f(thetaX, thetaY, thetaZ);
   }

   public static Vector3f eulerAnglesXYZFromMatrix(Matrix4f matrix4f) {
      float thetaZ;
      float thetaY;
      float thetaX;
      if (matrix4f.m20() < 1.0F) {
         if (matrix4f.m20() > -1.0F) {
            thetaY = (float)Math.asin(matrix4f.m02());
            thetaX = (float)Math.atan2(-matrix4f.m12(), matrix4f.m22());
            thetaZ = (float)Math.atan2(-matrix4f.m01(), matrix4f.m00());
         } else {
            thetaY = (float) (-Math.PI / 2);
            thetaX = -((float)Math.atan2(matrix4f.m10(), matrix4f.m11()));
            thetaZ = 0.0F;
         }
      } else {
         thetaY = (float) (Math.PI / 2);
         thetaX = (float)Math.atan2(matrix4f.m10(), matrix4f.m11());
         thetaZ = 0.0F;
      }

      return new Vector3f(thetaX, thetaY, thetaZ);
   }

   public static class BlendShape3D {
      private final RigUtils.BlendShape3DEntry[] entries;

      public BlendShape3D(RigUtils.BlendShape3DEntry[] entries) {
         this.entries = entries;
      }

      public void evaluate(GeoBone bone, Vec3 dir) {
         this.evaluate(bone, dir, false);
      }

      private double[] getWeights(Vec3 dir) {
         double[] weights = new double[this.entries.length];
         double[] dotProducts = new double[this.entries.length];
         double totalDotProduct = 0.0;

         for (int i = 0; i < this.entries.length; i++) {
            RigUtils.BlendShape3DEntry entry = this.entries[i];
            double dot = 1.0 - entry.getWeight(dir);
            if (!(dot > 0.0)) {
               weights[i] = 1.0;
               return weights;
            }

            totalDotProduct += 1.0 / dot;
            dotProducts[i] = dot;
         }

         for (int i = 0; i < this.entries.length; i++) {
            double dot_prod = totalDotProduct * dotProducts[i];
            if (dot_prod > 0.0) {
               weights[i] = 1.0 / dot_prod;
            } else {
               weights[i] = 0.0;
            }
         }

         return weights;
      }

      private double[] getWeightsGradientBand(Vec3 dir) {
         double[] weights = new double[this.entries.length];
         double[] sqrdDistances = new double[this.entries.length];
         double[] angularDistances = new double[this.entries.length];
         double totalSqrdDistance = 0.0;
         double totalAngularDistance = 0.0;

         for (int i = 0; i < this.entries.length; i++) {
            RigUtils.BlendShape3DEntry entry = this.entries[i];
            double sqrdDistance = dir.subtract(entry.direction).dot(dir.subtract(entry.direction));
            if (!(sqrdDistance > 0.0)) {
               weights[i] = 1.0;
               return weights;
            }

            double angularDistance = -(Mth.clamp(dir.dot(entry.direction), -1.0, 1.0) - 1.0) * 0.5;
            totalSqrdDistance += 1.0 / sqrdDistance;
            if (angularDistance > 0.0) {
               totalAngularDistance += 1.0 / angularDistance;
            }

            sqrdDistances[i] = sqrdDistance;
            angularDistances[i] = angularDistance;
         }

         for (int i = 0; i < this.entries.length; i++) {
            double sqrdDistance = totalSqrdDistance * sqrdDistances[i];
            double angularDistance = totalAngularDistance * angularDistances[i];
            if (sqrdDistance > 0.0 && angularDistance > 0.0) {
               weights[i] = 1.0 / sqrdDistance * 0.5 + 1.0 / angularDistance * 0.5;
            } else if (sqrdDistance > 0.0) {
               weights[i] = 1.0 / sqrdDistance * 0.5 + 0.5;
            } else {
               weights[i] = 0.0;
            }
         }

         return weights;
      }

      public void evaluate(GeoBone bone, Vec3 d, boolean mirrorX) {
         Vec3 dir = mirrorX ? d.multiply(-1.0, 1.0, 1.0) : d;
         dir = dir.normalize();
         double[] weights = this.getWeights(dir);
         RigUtils.BoneTransform transform = new RigUtils.BoneTransform(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);

         for (int i = 0; i < this.entries.length; i++) {
            RigUtils.BlendShape3DEntry entry = this.entries[i];
            transform = entry.blend(transform, (float)Mth.clamp(weights[i], 0.0, 1.0));
         }

         transform.apply(bone, mirrorX);
      }
   }

   public static class BlendShape3DEntry {
      private RigUtils.BoneTransform transform;
      private Vec3 direction;
      private float power;

      public BlendShape3DEntry(RigUtils.BoneTransform transform, Vec3 direction, float power) {
         this.transform = transform;
         this.direction = direction.normalize();
         this.power = power;
      }

      public double getWeight(Vec3 dir) {
         double dot = dir.normalize().dot(this.direction.normalize());
         dot = Math.max(dot, 0.0);
         return Math.pow(dot, 0.01 * this.power);
      }

      public RigUtils.BoneTransform blend(RigUtils.BoneTransform other, float alpha) {
         return this.transform.blend(other, alpha);
      }
   }

   public static class BoneTransform {
      private final Vec3 translation;
      private final Vec3 rotation;
      private final Vec3 scale;

      public BoneTransform(double tx, double ty, double tz, double rx, double ry, double rz, double sx, double sy, double sz) {
         this.translation = new Vec3(tx, ty, tz);
         this.rotation = new Vec3(rx, ry, rz);
         this.scale = new Vec3(sx, sy, sz);
      }

      public BoneTransform(Vec3 t, Vec3 r, Vec3 s) {
         this.translation = t;
         this.rotation = r;
         this.scale = s;
      }

      public RigUtils.BoneTransform blend(RigUtils.BoneTransform other, float alpha) {
         return new RigUtils.BoneTransform(
            this.translation.scale(alpha).add(other.translation),
            RigUtils.blendAngles(this.rotation, other.rotation, alpha),
            this.scale.scale(alpha).add(other.scale)
         );
      }

      public void apply(GeoBone bone) {
         this.apply(bone, false);
      }

      public void apply(GeoBone bone, boolean mirrorX) {
         float mirror = mirrorX ? -1.0F : 1.0F;
         bone.setPosX(bone.getPosX() + mirror * (float)this.translation.x());
         bone.setPosY(bone.getPosY() + (float)this.translation.y());
         bone.setPosZ(bone.getPosZ() + (float)this.translation.z());
         bone.setRotX(bone.getRotX() + (float)this.rotation.x());
         bone.setRotY(bone.getRotY() + mirror * (float)this.rotation.y());
         bone.setRotZ(bone.getRotZ() + mirror * (float)this.rotation.z());
         bone.setScaleX(bone.getScaleX() * (float)this.scale.x());
         bone.setScaleY(bone.getScaleY() * (float)this.scale.y());
         bone.setScaleZ(bone.getScaleZ() * (float)this.scale.z());
      }
   }
}
