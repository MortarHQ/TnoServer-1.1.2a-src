package team.creative.creativecore.common.util.math.collision;

import java.util.HashMap;
import team.creative.creativecore.common.util.math.base.Axis;
import team.creative.creativecore.common.util.math.box.ABB;
import team.creative.creativecore.common.util.math.matrix.IVecOrigin;
import team.creative.creativecore.common.util.math.matrix.Matrix3;
import team.creative.creativecore.common.util.math.matrix.Matrix4;
import team.creative.creativecore.common.util.math.utils.BooleanUtils;
import team.creative.creativecore.common.util.math.vec.Vec3d;

public class CollisionCoordinator {
   private final HashMap<Double, Matrix4> table = new HashMap<>();
   private final HashMap<Double, Matrix4> invertedTable = new HashMap<>();
   private final double rotXRadians;
   private final double rotYRadians;
   private final double rotZRadians;
   public final double rotX;
   public final double rotY;
   public final double rotZ;
   public final double offX;
   public final double offY;
   public final double offZ;
   public Matrix3 rotationX;
   public Matrix3 rotationY;
   public Matrix3 rotationZ;
   public Matrix3 rotationXInv;
   public Matrix3 rotationYInv;
   public Matrix3 rotationZInv;
   public Vec3d translation;
   public final boolean hasRotX;
   public final boolean hasRotY;
   public final boolean hasRotZ;
   public final boolean hasOffX;
   public final boolean hasOffY;
   public final boolean hasOffZ;
   public final boolean hasTranslation;
   public final boolean hasOneRotation;
   public final boolean hasRotation;
   public final boolean isSimple;
   private final Vec3d rotationCenter;
   private final IVecOrigin moved;
   private final IVecOrigin original;
   private final double originalOffX;
   private final double originalOffY;
   private final double originalOffZ;
   private final double originalRotX;
   private final double originalRotY;
   private final double originalRotZ;

   public CollisionCoordinator(double offX, double offY, double offZ, double rotX, double rotY, double rotZ, IVecOrigin origin) {
      this.offX = offX;
      this.offY = offY;
      this.offZ = offZ;
      this.rotX = rotX;
      this.rotY = rotY;
      this.rotZ = rotZ;
      this.rotXRadians = Math.toRadians(rotX);
      this.rotYRadians = Math.toRadians(rotY);
      this.rotZRadians = Math.toRadians(rotZ);
      this.hasRotX = rotX != 0.0;
      this.hasRotY = rotY != 0.0;
      this.hasRotZ = rotZ != 0.0;
      this.hasOffX = offX != 0.0;
      this.hasOffY = offY != 0.0;
      this.hasOffZ = offZ != 0.0;
      this.hasTranslation = this.hasOffX || this.hasOffY || this.hasOffZ;
      this.hasOneRotation = BooleanUtils.explicitOneTrue(this.hasRotX, this.hasRotY, this.hasRotZ);
      this.hasRotation = this.hasRotX || this.hasRotY || this.hasRotZ;
      this.isSimple = this.isSimple();
      this.original = origin;
      this.rotationCenter = new Vec3d(origin.center());
      if (origin.getParent() != null) {
         origin.getParent().transformPointToWorld(this.rotationCenter);
      }

      this.originalOffX = origin.offX();
      this.originalOffY = origin.offY();
      this.originalOffZ = origin.offZ();
      this.originalRotX = origin.rotX();
      this.originalRotY = origin.rotY();
      this.originalRotZ = origin.rotZ();
      this.moved = this.original.copy();
      this.moved.off(this.originalOffX + offX, this.originalOffY + offY, this.originalOffZ + offZ);
      this.moved.rot(this.originalRotX + rotX, this.originalRotY + rotY, this.originalRotZ + rotZ);
      this.translation = this.hasTranslation ? new Vec3d(offX, offY, offZ) : null;
   }

   public boolean hasOnlyTranslation() {
      return this.hasTranslation && !this.hasRotX && !this.hasRotY && !this.hasRotZ;
   }

   public double getRotationDegree(Axis axis) {
      return switch (axis) {
         case X -> this.rotX;
         case Y -> this.rotY;
         case Z -> this.rotZ;
         default -> 0.0;
      };
   }

   public Matrix3 getRotationMatrix(Axis axis) {
      return switch (axis) {
         case X -> {
            if (this.rotationX == null && this.hasRotX) {
               this.rotationX = MatrixUtils.createRotationMatrixXRadians(this.rotXRadians);
            }

            yield this.rotationX;
         }
         case Y -> {
            if (this.rotationY == null && this.hasRotY) {
               this.rotationY = MatrixUtils.createRotationMatrixYRadians(this.rotYRadians);
            }

            yield this.rotationY;
         }
         case Z -> {
            if (this.rotationZ == null && this.hasRotZ) {
               this.rotationZ = MatrixUtils.createRotationMatrixZRadians(this.rotZRadians);
            }

            yield this.rotationZ;
         }
         default -> null;
      };
   }

   public Matrix3 getRotationMatrixInv(Axis axis) {
      return switch (axis) {
         case X -> {
            if (this.rotationXInv == null && this.hasRotX) {
               this.rotationXInv = MatrixUtils.createRotationMatrixXRadians(-this.rotXRadians);
            }

            yield this.rotationXInv;
         }
         case Y -> {
            if (this.rotationYInv == null && this.hasRotY) {
               this.rotationYInv = MatrixUtils.createRotationMatrixYRadians(-this.rotYRadians);
            }

            yield this.rotationYInv;
         }
         case Z -> {
            if (this.rotationZInv == null && this.hasRotZ) {
               this.rotationZInv = MatrixUtils.createRotationMatrixZRadians(-this.rotZRadians);
            }

            yield this.rotationZInv;
         }
         default -> null;
      };
   }

   public IVecOrigin original() {
      return this.original;
   }

   public IVecOrigin moved() {
      return this.moved;
   }

   public ABB computeSurroundingBox(ABB box) {
      return box.createRotatedSurrounding(this);
   }

   public ABB computeInverseSurroundingBoxInternal(ABB box) {
      return box.createRotatedSurroundingInverseInternal(this);
   }

   private boolean isSimple() {
      if (BooleanUtils.explicitOneTrue(this.hasRotX, this.hasRotY, this.hasRotZ, this.hasTranslation)) {
         if (this.hasRotX) {
            return Math.abs(this.rotX) <= 180.0;
         } else if (this.hasRotY) {
            return Math.abs(this.rotY) <= 180.0;
         } else {
            return this.hasRotZ ? Math.abs(this.rotZ) <= 180.0 : true;
         }
      } else {
         return false;
      }
   }

   public int getNumberOfHalfRotations() {
      int halfRotations = 0;
      if (this.hasRotX) {
         halfRotations += (int)Math.ceil(Math.abs(this.rotX) / 180.0);
      }

      if (this.hasRotY) {
         halfRotations += (int)Math.ceil(Math.abs(this.rotY) / 180.0);
      }

      if (this.hasRotZ) {
         halfRotations += (int)Math.ceil(Math.abs(this.rotZ) / 180.0);
      }

      return halfRotations;
   }

   public Matrix4 getInverted(Double delta) {
      Matrix4 matrix = this.invertedTable.get(delta);
      if (matrix != null) {
         return matrix;
      }

      matrix = this.table.get(delta);
      if (matrix != null) {
         matrix = new Matrix4(matrix);
         matrix.invert();
      } else {
         matrix = MatrixUtils.createRotationMatrixAndTranslationRadians(
            -this.offX * delta, -this.offY * delta, -this.offZ * delta, -this.rotXRadians * delta, -this.rotYRadians * delta, -this.rotZRadians * delta
         );
      }

      this.invertedTable.put(delta, matrix);
      return matrix;
   }

   public Matrix4 get(Double delta) {
      Matrix4 matrix = this.table.get(delta);
      if (matrix != null) {
         return matrix;
      }

      matrix = this.invertedTable.get(delta);
      if (matrix != null) {
         matrix = new Matrix4(matrix);
         matrix.invert();
      } else {
         matrix = MatrixUtils.createRotationMatrixAndTranslationRadians(
            this.offX * delta, this.offY * delta, this.offZ * delta, this.rotXRadians * delta, this.rotYRadians * delta, this.rotZRadians * delta
         );
      }

      this.table.put(delta, matrix);
      return matrix;
   }

   public void transform(Vec3d vec, Double delta) {
      if (!(delta <= 0.0)) {
         vec.sub(this.rotationCenter);
         Matrix4 matrix = this.get(delta);
         double x = matrix.m00 * vec.x + matrix.m01 * vec.y + matrix.m02 * vec.z + matrix.m03;
         double y = matrix.m10 * vec.x + matrix.m11 * vec.y + matrix.m12 * vec.z + matrix.m13;
         vec.z = matrix.m20 * vec.x + matrix.m21 * vec.y + matrix.m22 * vec.z + matrix.m23;
         vec.x = x;
         vec.y = y;
         vec.add(this.rotationCenter);
      }
   }

   public void transformInverted(Vec3d vec, Double delta) {
      if (!(delta <= 0.0)) {
         vec.sub(this.rotationCenter);
         Matrix4 matrix = this.getInverted(delta);
         double x = matrix.m00 * vec.x + matrix.m01 * vec.y + matrix.m02 * vec.z + matrix.m03;
         double y = matrix.m10 * vec.x + matrix.m11 * vec.y + matrix.m12 * vec.z + matrix.m13;
         vec.z = matrix.m20 * vec.x + matrix.m21 * vec.y + matrix.m22 * vec.z + matrix.m23;
         vec.x = x;
         vec.y = y;
         vec.add(this.rotationCenter);
      }
   }

   public void transform(Matrix4 matrix, Vec3d vec) {
      vec.sub(this.rotationCenter);
      double x = matrix.m00 * vec.x + matrix.m01 * vec.y + matrix.m02 * vec.z + matrix.m03;
      double y = matrix.m10 * vec.x + matrix.m11 * vec.y + matrix.m12 * vec.z + matrix.m13;
      vec.z = matrix.m20 * vec.x + matrix.m21 * vec.y + matrix.m22 * vec.z + matrix.m23;
      vec.x = x;
      vec.y = y;
      vec.add(this.rotationCenter);
   }

   public void finish() {
      this.original.off(this.originalOffX + this.offX, this.originalOffY + this.offY, this.originalOffZ + this.offZ);
      this.original.rot(this.originalRotX + this.rotX, this.originalRotY + this.rotY, this.originalRotZ + this.rotZ);
   }
}
