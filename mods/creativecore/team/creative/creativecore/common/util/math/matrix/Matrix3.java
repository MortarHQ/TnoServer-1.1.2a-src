package team.creative.creativecore.common.util.math.matrix;

import team.creative.creativecore.common.util.math.vec.Vec3d;
import team.creative.creativecore.common.util.math.vec.Vec3f;

public class Matrix3 {
   public double m00;
   public double m01;
   public double m02;
   public double m10;
   public double m11;
   public double m12;
   public double m20;
   public double m21;
   public double m22;
   private static final double EPS = 1.110223024E-16;

   public Matrix3() {
      this(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
   }

   public Matrix3(double m00, double m01, double m02, double m10, double m11, double m12, double m20, double m21, double m22) {
      this.m00 = m00;
      this.m01 = m01;
      this.m02 = m02;
      this.m10 = m10;
      this.m11 = m11;
      this.m12 = m12;
      this.m20 = m20;
      this.m21 = m21;
      this.m22 = m22;
   }

   public Matrix3(Matrix3 matrix) {
      this.set(matrix);
   }

   @Override
   public String toString() {
      return this.m00
         + ", "
         + this.m01
         + ", "
         + this.m02
         + "\n"
         + this.m10
         + ", "
         + this.m11
         + ", "
         + this.m12
         + "\n"
         + this.m20
         + ", "
         + this.m21
         + ", "
         + this.m22
         + "\n";
   }

   public final void setIdentity() {
      this.m00 = 1.0;
      this.m01 = 0.0;
      this.m02 = 0.0;
      this.m10 = 0.0;
      this.m11 = 1.0;
      this.m12 = 0.0;
      this.m20 = 0.0;
      this.m21 = 0.0;
      this.m22 = 1.0;
   }

   public final void setScale(double scale) {
      double[] tmp_rot = new double[9];
      double[] tmp_scale = new double[3];
      this.getScaleRotate(tmp_scale, tmp_rot);
      this.m00 = tmp_rot[0] * scale;
      this.m01 = tmp_rot[1] * scale;
      this.m02 = tmp_rot[2] * scale;
      this.m10 = tmp_rot[3] * scale;
      this.m11 = tmp_rot[4] * scale;
      this.m12 = tmp_rot[5] * scale;
      this.m20 = tmp_rot[6] * scale;
      this.m21 = tmp_rot[7] * scale;
      this.m22 = tmp_rot[8] * scale;
   }

   public final void setElement(int row, int column, double value) {
      switch (row) {
         case 0:
            switch (column) {
               case 0:
                  this.m00 = value;
                  return;
               case 1:
                  this.m01 = value;
                  return;
               case 2:
                  this.m02 = value;
                  return;
               default:
                  throw new ArrayIndexOutOfBoundsException();
            }
         case 1:
            switch (column) {
               case 0:
                  this.m10 = value;
                  return;
               case 1:
                  this.m11 = value;
                  return;
               case 2:
                  this.m12 = value;
                  return;
               default:
                  throw new ArrayIndexOutOfBoundsException();
            }
         case 2:
            switch (column) {
               case 0:
                  this.m20 = value;
                  return;
               case 1:
                  this.m21 = value;
                  return;
               case 2:
                  this.m22 = value;
                  return;
               default:
                  throw new ArrayIndexOutOfBoundsException();
            }
         default:
            throw new ArrayIndexOutOfBoundsException();
      }
   }

   public final double getElement(int row, int column) {
      switch (row) {
         case 0:
            switch (column) {
               case 0:
                  return this.m00;
               case 1:
                  return this.m01;
               case 2:
                  return this.m02;
               default:
                  throw new ArrayIndexOutOfBoundsException();
            }
         case 1:
            switch (column) {
               case 0:
                  return this.m10;
               case 1:
                  return this.m11;
               case 2:
                  return this.m12;
               default:
                  throw new ArrayIndexOutOfBoundsException();
            }
         case 2:
            switch (column) {
               case 0:
                  return this.m20;
               case 1:
                  return this.m21;
               case 2:
                  return this.m22;
            }
      }

      throw new ArrayIndexOutOfBoundsException();
   }

   public final double getScale() {
      double[] tmp_scale = new double[3];
      double[] tmp_rot = new double[9];
      this.getScaleRotate(tmp_scale, tmp_rot);
      return max3(tmp_scale);
   }

   public final void add(double scalar) {
      this.m00 += scalar;
      this.m01 += scalar;
      this.m02 += scalar;
      this.m10 += scalar;
      this.m11 += scalar;
      this.m12 += scalar;
      this.m20 += scalar;
      this.m21 += scalar;
      this.m22 += scalar;
   }

   public final void add(Matrix3 m1) {
      this.m00 = this.m00 + m1.m00;
      this.m01 = this.m01 + m1.m01;
      this.m02 = this.m02 + m1.m02;
      this.m10 = this.m10 + m1.m10;
      this.m11 = this.m11 + m1.m11;
      this.m12 = this.m12 + m1.m12;
      this.m20 = this.m20 + m1.m20;
      this.m21 = this.m21 + m1.m21;
      this.m22 = this.m22 + m1.m22;
   }

   public final void sub(Matrix3 m1) {
      this.m00 = this.m00 - m1.m00;
      this.m01 = this.m01 - m1.m01;
      this.m02 = this.m02 - m1.m02;
      this.m10 = this.m10 - m1.m10;
      this.m11 = this.m11 - m1.m11;
      this.m12 = this.m12 - m1.m12;
      this.m20 = this.m20 - m1.m20;
      this.m21 = this.m21 - m1.m21;
      this.m22 = this.m22 - m1.m22;
   }

   public final void transpose() {
      double temp = this.m10;
      this.m10 = this.m01;
      this.m01 = temp;
      temp = this.m20;
      this.m20 = this.m02;
      this.m02 = temp;
      temp = this.m21;
      this.m21 = this.m12;
      this.m12 = temp;
   }

   public final void set(Matrix3 m1) {
      this.m00 = m1.m00;
      this.m01 = m1.m01;
      this.m02 = m1.m02;
      this.m10 = m1.m10;
      this.m11 = m1.m11;
      this.m12 = m1.m12;
      this.m20 = m1.m20;
      this.m21 = m1.m21;
      this.m22 = m1.m22;
   }

   public final void invert() {
      double[] result = new double[9];
      int[] row_perm = new int[3];
      double[] tmp = new double[]{this.m00, this.m01, this.m02, this.m10, this.m11, this.m12, this.m20, this.m21, this.m22};
      if (!luDecomposition(tmp, row_perm)) {
         throw new RuntimeException("Cannot be reverted");
      }

      for (int i = 0; i < 9; i++) {
         result[i] = 0.0;
      }

      result[0] = 1.0;
      result[4] = 1.0;
      result[8] = 1.0;
      luBacksubstitution(tmp, row_perm, result);
      this.m00 = result[0];
      this.m01 = result[1];
      this.m02 = result[2];
      this.m10 = result[3];
      this.m11 = result[4];
      this.m12 = result[5];
      this.m20 = result[6];
      this.m21 = result[7];
      this.m22 = result[8];
   }

   public final void invert(Matrix3 m) {
      double[] result = new double[9];
      int[] row_perm = new int[3];
      double[] tmp = new double[]{m.m00, m.m01, m.m02, m.m10, m.m11, m.m12, m.m20, m.m21, m.m22};
      if (!luDecomposition(tmp, row_perm)) {
         throw new RuntimeException("Cannot be reverted");
      }

      for (int i = 0; i < 9; i++) {
         result[i] = 0.0;
      }

      result[0] = 1.0;
      result[4] = 1.0;
      result[8] = 1.0;
      luBacksubstitution(tmp, row_perm, result);
      this.m00 = result[0];
      this.m01 = result[1];
      this.m02 = result[2];
      this.m10 = result[3];
      this.m11 = result[4];
      this.m12 = result[5];
      this.m20 = result[6];
      this.m21 = result[7];
      this.m22 = result[8];
   }

   static boolean luDecomposition(double[] matrix0, int[] row_perm) {
      double[] row_scale = new double[3];
      int ptr = 0;
      int rs = 0;
      int i = 3;

      while (i-- != 0) {
         double big = 0.0;
         int j = 3;

         while (j-- != 0) {
            double temp = matrix0[ptr++];
            temp = Math.abs(temp);
            if (temp > big) {
               big = temp;
            }
         }

         if (big == 0.0) {
            return false;
         }

         row_scale[rs++] = 1.0 / big;
      }

      int mtx = 0;

      for (int j = 0; j < 3; j++) {
         for (int ix = 0; ix < j; ix++) {
            int target = mtx + 3 * ix + j;
            double sum = matrix0[target];
            int k = ix;
            int p1 = mtx + 3 * ix;

            for (int p2 = mtx + j; k-- != 0; p2 += 3) {
               sum -= matrix0[p1] * matrix0[p2];
               p1++;
            }

            matrix0[target] = sum;
         }

         double big = 0.0;
         rs = -1;

         for (int var20 = j; var20 < 3; var20++) {
            int target = mtx + 3 * var20 + j;
            double sum = matrix0[target];
            int k = j;
            int p1 = mtx + 3 * var20;

            for (int p2 = mtx + j; k-- != 0; p2 += 3) {
               sum -= matrix0[p1] * matrix0[p2];
               p1++;
            }

            matrix0[target] = sum;
            double temp;
            if ((temp = row_scale[var20] * Math.abs(sum)) >= big) {
               big = temp;
               rs = var20;
            }
         }

         if (rs < 0) {
            throw new RuntimeException();
         }

         if (j != rs) {
            int k = 3;
            int p1 = mtx + 3 * rs;
            int p2 = mtx + 3 * j;

            while (k-- != 0) {
               double temp = matrix0[p1];
               matrix0[p1++] = matrix0[p2];
               matrix0[p2++] = temp;
            }

            row_scale[rs] = row_scale[j];
         }

         row_perm[j] = rs;
         if (matrix0[mtx + 3 * j + j] == 0.0) {
            return false;
         }

         if (j != 2) {
            double temp = 1.0 / matrix0[mtx + 3 * j + j];
            int target = mtx + 3 * (j + 1) + j;

            for (int var21 = 2 - j; var21-- != 0; target += 3) {
               matrix0[target] *= temp;
            }
         }
      }

      return true;
   }

   static void luBacksubstitution(double[] matrix1, int[] row_perm, double[] matrix2) {
      int rp = 0;

      for (int k = 0; k < 3; k++) {
         int cv = k;
         int ii = -1;

         for (int i = 0; i < 3; i++) {
            int ip = row_perm[rp + i];
            double sum = matrix2[cv + 3 * ip];
            matrix2[cv + 3 * ip] = matrix2[cv + 3 * i];
            if (ii >= 0) {
               int rv = i * 3;

               for (int j = ii; j <= i - 1; j++) {
                  sum -= matrix1[rv + j] * matrix2[cv + 3 * j];
               }
            } else if (sum != 0.0) {
               ii = i;
            }

            matrix2[cv + 3 * i] = sum;
         }

         int rv = 6;
         matrix2[cv + 6] = matrix2[cv + 6] / matrix1[rv + 2];
         rv -= 3;
         matrix2[cv + 3] = (matrix2[cv + 3] - matrix1[rv + 2] * matrix2[cv + 6]) / matrix1[rv + 1];
         rv -= 3;
         matrix2[cv] = (matrix2[cv] - matrix1[rv + 1] * matrix2[cv + 3] - matrix1[rv + 2] * matrix2[cv + 6]) / matrix1[rv];
      }
   }

   public final double determinant() {
      return this.m00 * (this.m11 * this.m22 - this.m12 * this.m21)
         + this.m01 * (this.m12 * this.m20 - this.m10 * this.m22)
         + this.m02 * (this.m10 * this.m21 - this.m11 * this.m20);
   }

   public final void set(double scale) {
      this.m00 = scale;
      this.m01 = 0.0;
      this.m02 = 0.0;
      this.m10 = 0.0;
      this.m11 = scale;
      this.m12 = 0.0;
      this.m20 = 0.0;
      this.m21 = 0.0;
      this.m22 = scale;
   }

   public final void rotX(double angle) {
      double sinAngle = Math.sin(angle);
      double cosAngle = Math.cos(angle);
      this.m00 = 1.0;
      this.m01 = 0.0;
      this.m02 = 0.0;
      this.m10 = 0.0;
      this.m11 = cosAngle;
      this.m12 = -sinAngle;
      this.m20 = 0.0;
      this.m21 = sinAngle;
      this.m22 = cosAngle;
   }

   public final void rotY(double angle) {
      double sinAngle = Math.sin(angle);
      double cosAngle = Math.cos(angle);
      this.m00 = cosAngle;
      this.m01 = 0.0;
      this.m02 = sinAngle;
      this.m10 = 0.0;
      this.m11 = 1.0;
      this.m12 = 0.0;
      this.m20 = -sinAngle;
      this.m21 = 0.0;
      this.m22 = cosAngle;
   }

   public final void rotZ(double angle) {
      double sinAngle = Math.sin(angle);
      double cosAngle = Math.cos(angle);
      this.m00 = cosAngle;
      this.m01 = -sinAngle;
      this.m02 = 0.0;
      this.m10 = sinAngle;
      this.m11 = cosAngle;
      this.m12 = 0.0;
      this.m20 = 0.0;
      this.m21 = 0.0;
      this.m22 = 1.0;
   }

   public final void mul(double scalar) {
      this.m00 *= scalar;
      this.m01 *= scalar;
      this.m02 *= scalar;
      this.m10 *= scalar;
      this.m11 *= scalar;
      this.m12 *= scalar;
      this.m20 *= scalar;
      this.m21 *= scalar;
      this.m22 *= scalar;
   }

   public final void mul(Matrix3 m1) {
      double m00 = this.m00 * m1.m00 + this.m01 * m1.m10 + this.m02 * m1.m20;
      double m01 = this.m00 * m1.m01 + this.m01 * m1.m11 + this.m02 * m1.m21;
      double m02 = this.m00 * m1.m02 + this.m01 * m1.m12 + this.m02 * m1.m22;
      double m10 = this.m10 * m1.m00 + this.m11 * m1.m10 + this.m12 * m1.m20;
      double m11 = this.m10 * m1.m01 + this.m11 * m1.m11 + this.m12 * m1.m21;
      double m12 = this.m10 * m1.m02 + this.m11 * m1.m12 + this.m12 * m1.m22;
      double m20 = this.m20 * m1.m00 + this.m21 * m1.m10 + this.m22 * m1.m20;
      double m21 = this.m20 * m1.m01 + this.m21 * m1.m11 + this.m22 * m1.m21;
      double m22 = this.m20 * m1.m02 + this.m21 * m1.m12 + this.m22 * m1.m22;
      this.m00 = m00;
      this.m01 = m01;
      this.m02 = m02;
      this.m10 = m10;
      this.m11 = m11;
      this.m12 = m12;
      this.m20 = m20;
      this.m21 = m21;
      this.m22 = m22;
   }

   public final void mulNormalize(Matrix3 m1) {
      double[] tmp = new double[9];
      double[] tmp_rot = new double[9];
      double[] tmp_scale = new double[3];
      tmp[0] = this.m00 * m1.m00 + this.m01 * m1.m10 + this.m02 * m1.m20;
      tmp[1] = this.m00 * m1.m01 + this.m01 * m1.m11 + this.m02 * m1.m21;
      tmp[2] = this.m00 * m1.m02 + this.m01 * m1.m12 + this.m02 * m1.m22;
      tmp[3] = this.m10 * m1.m00 + this.m11 * m1.m10 + this.m12 * m1.m20;
      tmp[4] = this.m10 * m1.m01 + this.m11 * m1.m11 + this.m12 * m1.m21;
      tmp[5] = this.m10 * m1.m02 + this.m11 * m1.m12 + this.m12 * m1.m22;
      tmp[6] = this.m20 * m1.m00 + this.m21 * m1.m10 + this.m22 * m1.m20;
      tmp[7] = this.m20 * m1.m01 + this.m21 * m1.m11 + this.m22 * m1.m21;
      tmp[8] = this.m20 * m1.m02 + this.m21 * m1.m12 + this.m22 * m1.m22;
      compute_svd(tmp, tmp_scale, tmp_rot);
      this.m00 = tmp_rot[0];
      this.m01 = tmp_rot[1];
      this.m02 = tmp_rot[2];
      this.m10 = tmp_rot[3];
      this.m11 = tmp_rot[4];
      this.m12 = tmp_rot[5];
      this.m20 = tmp_rot[6];
      this.m21 = tmp_rot[7];
      this.m22 = tmp_rot[8];
   }

   public final void normalize() {
      double[] tmp_rot = new double[9];
      double[] tmp_scale = new double[3];
      this.getScaleRotate(tmp_scale, tmp_rot);
      this.m00 = tmp_rot[0];
      this.m01 = tmp_rot[1];
      this.m02 = tmp_rot[2];
      this.m10 = tmp_rot[3];
      this.m11 = tmp_rot[4];
      this.m12 = tmp_rot[5];
      this.m20 = tmp_rot[6];
      this.m21 = tmp_rot[7];
      this.m22 = tmp_rot[8];
   }

   public final void normalizeCP() {
      double mag = 1.0 / Math.sqrt(this.m00 * this.m00 + this.m10 * this.m10 + this.m20 * this.m20);
      this.m00 *= mag;
      this.m10 *= mag;
      this.m20 *= mag;
      mag = 1.0 / Math.sqrt(this.m01 * this.m01 + this.m11 * this.m11 + this.m21 * this.m21);
      this.m01 *= mag;
      this.m11 *= mag;
      this.m21 *= mag;
      this.m02 = this.m10 * this.m21 - this.m11 * this.m20;
      this.m12 = this.m01 * this.m20 - this.m00 * this.m21;
      this.m22 = this.m00 * this.m11 - this.m01 * this.m10;
   }

   public boolean equals(Matrix3 m1) {
      try {
         return this.m00 == m1.m00
            && this.m01 == m1.m01
            && this.m02 == m1.m02
            && this.m10 == m1.m10
            && this.m11 == m1.m11
            && this.m12 == m1.m12
            && this.m20 == m1.m20
            && this.m21 == m1.m21
            && this.m22 == m1.m22;
      } catch (NullPointerException e2) {
         return false;
      }
   }

   @Override
   public boolean equals(Object t1) {
      try {
         Matrix3 m2 = (Matrix3)t1;
         return this.m00 == m2.m00
            && this.m01 == m2.m01
            && this.m02 == m2.m02
            && this.m10 == m2.m10
            && this.m11 == m2.m11
            && this.m12 == m2.m12
            && this.m20 == m2.m20
            && this.m21 == m2.m21
            && this.m22 == m2.m22;
      } catch (ClassCastException | NullPointerException e1) {
         return false;
      }
   }

   public final void transform(Vec3d t) {
      double x = this.m00 * t.x + this.m01 * t.y + this.m02 * t.z;
      double y = this.m10 * t.x + this.m11 * t.y + this.m12 * t.z;
      double z = this.m20 * t.x + this.m21 * t.y + this.m22 * t.z;
      t.set(x, y, z);
   }

   public final void transform(Vec3f t) {
      float x = (float)(this.m00 * t.x + this.m01 * t.y + this.m02 * t.z);
      float y = (float)(this.m10 * t.x + this.m11 * t.y + this.m12 * t.z);
      float z = (float)(this.m20 * t.x + this.m21 * t.y + this.m22 * t.z);
      t.set(x, y, z);
   }

   final void getScaleRotate(double[] scales, double[] rots) {
      double[] tmp = new double[]{this.m00, this.m01, this.m02, this.m10, this.m11, this.m12, this.m20, this.m21, this.m22};
      compute_svd(tmp, scales, rots);
   }

   static void compute_svd(double[] m, double[] outScale, double[] outRot) {
      double[] u1 = new double[9];
      double[] v1 = new double[9];
      double[] t1 = new double[9];
      double[] t2 = new double[9];
      double[] tmp = t1;
      double[] single_values = t2;
      double[] rot = new double[9];
      double[] e = new double[3];
      double[] scales = new double[3];
      int negCnt = 0;

      for (int i = 0; i < 9; i++) {
         rot[i] = m[i];
      }

      if (m[3] * m[3] < 1.110223024E-16) {
         u1[0] = 1.0;
         u1[1] = 0.0;
         u1[2] = 0.0;
         u1[3] = 0.0;
         u1[4] = 1.0;
         u1[5] = 0.0;
         u1[6] = 0.0;
         u1[7] = 0.0;
         u1[8] = 1.0;
      } else if (m[0] * m[0] < 1.110223024E-16) {
         tmp[0] = m[0];
         tmp[1] = m[1];
         tmp[2] = m[2];
         m[0] = m[3];
         m[1] = m[4];
         m[2] = m[5];
         m[3] = -tmp[0];
         m[4] = -tmp[1];
         m[5] = -tmp[2];
         u1[0] = 0.0;
         u1[1] = 1.0;
         u1[2] = 0.0;
         u1[3] = -1.0;
         u1[4] = 0.0;
         u1[5] = 0.0;
         u1[6] = 0.0;
         u1[7] = 0.0;
         u1[8] = 1.0;
      } else {
         double g = 1.0 / Math.sqrt(m[0] * m[0] + m[3] * m[3]);
         double c1 = m[0] * g;
         double s1 = m[3] * g;
         tmp[0] = c1 * m[0] + s1 * m[3];
         tmp[1] = c1 * m[1] + s1 * m[4];
         tmp[2] = c1 * m[2] + s1 * m[5];
         m[3] = -s1 * m[0] + c1 * m[3];
         m[4] = -s1 * m[1] + c1 * m[4];
         m[5] = -s1 * m[2] + c1 * m[5];
         m[0] = tmp[0];
         m[1] = tmp[1];
         m[2] = tmp[2];
         u1[0] = c1;
         u1[1] = s1;
         u1[2] = 0.0;
         u1[3] = -s1;
         u1[4] = c1;
         u1[5] = 0.0;
         u1[6] = 0.0;
         u1[7] = 0.0;
         u1[8] = 1.0;
      }

      if (!(m[6] * m[6] < 1.110223024E-16)) {
         if (m[0] * m[0] < 1.110223024E-16) {
            tmp[0] = m[0];
            tmp[1] = m[1];
            tmp[2] = m[2];
            m[0] = m[6];
            m[1] = m[7];
            m[2] = m[8];
            m[6] = -tmp[0];
            m[7] = -tmp[1];
            m[8] = -tmp[2];
            tmp[0] = u1[0];
            tmp[1] = u1[1];
            tmp[2] = u1[2];
            u1[0] = u1[6];
            u1[1] = u1[7];
            u1[2] = u1[8];
            u1[6] = -tmp[0];
            u1[7] = -tmp[1];
            u1[8] = -tmp[2];
         } else {
            double g = 1.0 / Math.sqrt(m[0] * m[0] + m[6] * m[6]);
            double c2 = m[0] * g;
            double s2 = m[6] * g;
            tmp[0] = c2 * m[0] + s2 * m[6];
            tmp[1] = c2 * m[1] + s2 * m[7];
            tmp[2] = c2 * m[2] + s2 * m[8];
            m[6] = -s2 * m[0] + c2 * m[6];
            m[7] = -s2 * m[1] + c2 * m[7];
            m[8] = -s2 * m[2] + c2 * m[8];
            m[0] = tmp[0];
            m[1] = tmp[1];
            m[2] = tmp[2];
            tmp[0] = c2 * u1[0];
            tmp[1] = c2 * u1[1];
            u1[2] = s2;
            tmp[6] = -u1[0] * s2;
            tmp[7] = -u1[1] * s2;
            u1[8] = c2;
            u1[0] = tmp[0];
            u1[1] = tmp[1];
            u1[6] = tmp[6];
            u1[7] = tmp[7];
         }
      }

      if (m[2] * m[2] < 1.110223024E-16) {
         v1[0] = 1.0;
         v1[1] = 0.0;
         v1[2] = 0.0;
         v1[3] = 0.0;
         v1[4] = 1.0;
         v1[5] = 0.0;
         v1[6] = 0.0;
         v1[7] = 0.0;
         v1[8] = 1.0;
      } else if (m[1] * m[1] < 1.110223024E-16) {
         tmp[2] = m[2];
         tmp[5] = m[5];
         tmp[8] = m[8];
         m[2] = -m[1];
         m[5] = -m[4];
         m[8] = -m[7];
         m[1] = tmp[2];
         m[4] = tmp[5];
         m[7] = tmp[8];
         v1[0] = 1.0;
         v1[1] = 0.0;
         v1[2] = 0.0;
         v1[3] = 0.0;
         v1[4] = 0.0;
         v1[5] = -1.0;
         v1[6] = 0.0;
         v1[7] = 1.0;
         v1[8] = 0.0;
      } else {
         double g = 1.0 / Math.sqrt(m[1] * m[1] + m[2] * m[2]);
         double c3 = m[1] * g;
         double s3 = m[2] * g;
         tmp[1] = c3 * m[1] + s3 * m[2];
         m[2] = -s3 * m[1] + c3 * m[2];
         m[1] = tmp[1];
         tmp[4] = c3 * m[4] + s3 * m[5];
         m[5] = -s3 * m[4] + c3 * m[5];
         m[4] = tmp[4];
         tmp[7] = c3 * m[7] + s3 * m[8];
         m[8] = -s3 * m[7] + c3 * m[8];
         m[7] = tmp[7];
         v1[0] = 1.0;
         v1[1] = 0.0;
         v1[2] = 0.0;
         v1[3] = 0.0;
         v1[4] = c3;
         v1[5] = -s3;
         v1[6] = 0.0;
         v1[7] = s3;
         v1[8] = c3;
      }

      if (!(m[7] * m[7] < 1.110223024E-16)) {
         if (m[4] * m[4] < 1.110223024E-16) {
            tmp[3] = m[3];
            tmp[4] = m[4];
            tmp[5] = m[5];
            m[3] = m[6];
            m[4] = m[7];
            m[5] = m[8];
            m[6] = -tmp[3];
            m[7] = -tmp[4];
            m[8] = -tmp[5];
            tmp[3] = u1[3];
            tmp[4] = u1[4];
            tmp[5] = u1[5];
            u1[3] = u1[6];
            u1[4] = u1[7];
            u1[5] = u1[8];
            u1[6] = -tmp[3];
            u1[7] = -tmp[4];
            u1[8] = -tmp[5];
         } else {
            double g = 1.0 / Math.sqrt(m[4] * m[4] + m[7] * m[7]);
            double c4 = m[4] * g;
            double s4 = m[7] * g;
            tmp[3] = c4 * m[3] + s4 * m[6];
            m[6] = -s4 * m[3] + c4 * m[6];
            m[3] = tmp[3];
            tmp[4] = c4 * m[4] + s4 * m[7];
            m[7] = -s4 * m[4] + c4 * m[7];
            m[4] = tmp[4];
            tmp[5] = c4 * m[5] + s4 * m[8];
            m[8] = -s4 * m[5] + c4 * m[8];
            m[5] = tmp[5];
            tmp[3] = c4 * u1[3] + s4 * u1[6];
            u1[6] = -s4 * u1[3] + c4 * u1[6];
            u1[3] = tmp[3];
            tmp[4] = c4 * u1[4] + s4 * u1[7];
            u1[7] = -s4 * u1[4] + c4 * u1[7];
            u1[4] = tmp[4];
            tmp[5] = c4 * u1[5] + s4 * u1[8];
            u1[8] = -s4 * u1[5] + c4 * u1[8];
            u1[5] = tmp[5];
         }
      }

      single_values[0] = m[0];
      single_values[1] = m[4];
      single_values[2] = m[8];
      e[0] = m[1];
      e[1] = m[5];
      if (!(e[0] * e[0] < 1.110223024E-16) || !(e[1] * e[1] < 1.110223024E-16)) {
         compute_qr(single_values, e, u1, v1);
      }

      scales[0] = single_values[0];
      scales[1] = single_values[1];
      scales[2] = single_values[2];
      if (almostEqual(Math.abs(scales[0]), 1.0) && almostEqual(Math.abs(scales[1]), 1.0) && almostEqual(Math.abs(scales[2]), 1.0)) {
         for (int var32 = 0; var32 < 3; var32++) {
            if (scales[var32] < 0.0) {
               negCnt++;
            }
         }

         if (negCnt == 0 || negCnt == 2) {
            outScale[0] = outScale[1] = outScale[2] = 1.0;

            for (int var33 = 0; var33 < 9; var33++) {
               outRot[var33] = rot[var33];
            }

            return;
         }
      }

      transpose_mat(u1, t1);
      transpose_mat(v1, t2);
      svdReorder(m, t1, t2, scales, outRot, outScale);
   }

   static void svdReorder(double[] m, double[] t1, double[] t2, double[] scales, double[] outRot, double[] outScale) {
      int[] out = new int[3];
      double[] mag = new double[3];
      double[] rot = new double[9];
      if (scales[0] < 0.0) {
         scales[0] = -scales[0];
         t2[0] = -t2[0];
         t2[1] = -t2[1];
         t2[2] = -t2[2];
      }

      if (scales[1] < 0.0) {
         scales[1] = -scales[1];
         t2[3] = -t2[3];
         t2[4] = -t2[4];
         t2[5] = -t2[5];
      }

      if (scales[2] < 0.0) {
         scales[2] = -scales[2];
         t2[6] = -t2[6];
         t2[7] = -t2[7];
         t2[8] = -t2[8];
      }

      mat_mul(t1, t2, rot);
      if (almostEqual(Math.abs(scales[0]), Math.abs(scales[1])) && almostEqual(Math.abs(scales[1]), Math.abs(scales[2]))) {
         for (int i = 0; i < 9; i++) {
            outRot[i] = rot[i];
         }

         for (int var25 = 0; var25 < 3; var25++) {
            outScale[var25] = scales[var25];
         }
      } else {
         if (scales[0] > scales[1]) {
            if (scales[0] > scales[2]) {
               if (scales[2] > scales[1]) {
                  out[0] = 0;
                  out[1] = 2;
                  out[2] = 1;
               } else {
                  out[0] = 0;
                  out[1] = 1;
                  out[2] = 2;
               }
            } else {
               out[0] = 2;
               out[1] = 0;
               out[2] = 1;
            }
         } else if (scales[1] > scales[2]) {
            if (scales[2] > scales[0]) {
               out[0] = 1;
               out[1] = 2;
               out[2] = 0;
            } else {
               out[0] = 1;
               out[1] = 0;
               out[2] = 2;
            }
         } else {
            out[0] = 2;
            out[1] = 1;
            out[2] = 0;
         }

         mag[0] = m[0] * m[0] + m[1] * m[1] + m[2] * m[2];
         mag[1] = m[3] * m[3] + m[4] * m[4] + m[5] * m[5];
         mag[2] = m[6] * m[6] + m[7] * m[7] + m[8] * m[8];
         int in0;
         int in1;
         int in2;
         if (mag[0] > mag[1]) {
            if (mag[0] > mag[2]) {
               if (mag[2] > mag[1]) {
                  in0 = 0;
                  in2 = 1;
                  in1 = 2;
               } else {
                  in0 = 0;
                  in1 = 1;
                  in2 = 2;
               }
            } else {
               in2 = 0;
               in0 = 1;
               in1 = 2;
            }
         } else if (mag[1] > mag[2]) {
            if (mag[2] > mag[0]) {
               in1 = 0;
               in2 = 1;
               in0 = 2;
            } else {
               in1 = 0;
               in0 = 1;
               in2 = 2;
            }
         } else {
            in2 = 0;
            in1 = 1;
            in0 = 2;
         }

         int index = out[in0];
         outScale[0] = scales[index];
         index = out[in1];
         outScale[1] = scales[index];
         index = out[in2];
         outScale[2] = scales[index];
         index = out[in0];
         outRot[0] = rot[index];
         index = out[in0] + 3;
         outRot[3] = rot[index];
         index = out[in0] + 6;
         outRot[6] = rot[index];
         index = out[in1];
         outRot[1] = rot[index];
         index = out[in1] + 3;
         outRot[4] = rot[index];
         index = out[in1] + 6;
         outRot[7] = rot[index];
         index = out[in2];
         outRot[2] = rot[index];
         index = out[in2] + 3;
         outRot[5] = rot[index];
         index = out[in2] + 6;
         outRot[8] = rot[index];
      }
   }

   static int compute_qr(double[] s, double[] e, double[] u, double[] v) {
      double[] cosl = new double[2];
      double[] cosr = new double[2];
      double[] sinl = new double[2];
      double[] sinr = new double[2];
      double[] m = new double[9];
      int MAX_INTERATIONS = 10;
      double CONVERGE_TOL = 4.89E-15;
      double c_b48 = 1.0;
      boolean converged = false;
      int first = 1;
      if (Math.abs(e[1]) < 4.89E-15 || Math.abs(e[0]) < 4.89E-15) {
         converged = true;
      }

      for (int k = 0; k < 10 && !converged; k++) {
         double shift = compute_shift(s[1], e[1], s[2]);
         double f = (Math.abs(s[0]) - shift) * (d_sign(c_b48, s[0]) + shift / s[0]);
         double g = e[0];
         double r = compute_rot(f, g, sinr, cosr, 0, first);
         f = cosr[0] * s[0] + sinr[0] * e[0];
         e[0] = cosr[0] * e[0] - sinr[0] * s[0];
         g = sinr[0] * s[1];
         s[1] = cosr[0] * s[1];
         r = compute_rot(f, g, sinl, cosl, 0, first);
         first = 0;
         s[0] = r;
         f = cosl[0] * e[0] + sinl[0] * s[1];
         s[1] = cosl[0] * s[1] - sinl[0] * e[0];
         g = sinl[0] * e[1];
         e[1] = cosl[0] * e[1];
         r = compute_rot(f, g, sinr, cosr, 1, first);
         e[0] = r;
         f = cosr[1] * s[1] + sinr[1] * e[1];
         e[1] = cosr[1] * e[1] - sinr[1] * s[1];
         g = sinr[1] * s[2];
         s[2] = cosr[1] * s[2];
         r = compute_rot(f, g, sinl, cosl, 1, first);
         s[1] = r;
         f = cosl[1] * e[1] + sinl[1] * s[2];
         s[2] = cosl[1] * s[2] - sinl[1] * e[1];
         e[1] = f;
         double utemp = u[0];
         u[0] = cosl[0] * utemp + sinl[0] * u[3];
         u[3] = -sinl[0] * utemp + cosl[0] * u[3];
         utemp = u[1];
         u[1] = cosl[0] * utemp + sinl[0] * u[4];
         u[4] = -sinl[0] * utemp + cosl[0] * u[4];
         utemp = u[2];
         u[2] = cosl[0] * utemp + sinl[0] * u[5];
         u[5] = -sinl[0] * utemp + cosl[0] * u[5];
         utemp = u[3];
         u[3] = cosl[1] * utemp + sinl[1] * u[6];
         u[6] = -sinl[1] * utemp + cosl[1] * u[6];
         utemp = u[4];
         u[4] = cosl[1] * utemp + sinl[1] * u[7];
         u[7] = -sinl[1] * utemp + cosl[1] * u[7];
         utemp = u[5];
         u[5] = cosl[1] * utemp + sinl[1] * u[8];
         u[8] = -sinl[1] * utemp + cosl[1] * u[8];
         double vtemp = v[0];
         v[0] = cosr[0] * vtemp + sinr[0] * v[1];
         v[1] = -sinr[0] * vtemp + cosr[0] * v[1];
         vtemp = v[3];
         v[3] = cosr[0] * vtemp + sinr[0] * v[4];
         v[4] = -sinr[0] * vtemp + cosr[0] * v[4];
         vtemp = v[6];
         v[6] = cosr[0] * vtemp + sinr[0] * v[7];
         v[7] = -sinr[0] * vtemp + cosr[0] * v[7];
         vtemp = v[1];
         v[1] = cosr[1] * vtemp + sinr[1] * v[2];
         v[2] = -sinr[1] * vtemp + cosr[1] * v[2];
         vtemp = v[4];
         v[4] = cosr[1] * vtemp + sinr[1] * v[5];
         v[5] = -sinr[1] * vtemp + cosr[1] * v[5];
         vtemp = v[7];
         v[7] = cosr[1] * vtemp + sinr[1] * v[8];
         v[8] = -sinr[1] * vtemp + cosr[1] * v[8];
         m[0] = s[0];
         m[1] = e[0];
         m[2] = 0.0;
         m[3] = 0.0;
         m[4] = s[1];
         m[5] = e[1];
         m[6] = 0.0;
         m[7] = 0.0;
         m[8] = s[2];
         if (Math.abs(e[1]) < 4.89E-15 || Math.abs(e[0]) < 4.89E-15) {
            converged = true;
         }
      }

      if (Math.abs(e[1]) < 4.89E-15) {
         compute_2X2(s[0], e[0], s[1], s, sinl, cosl, sinr, cosr, 0);
         double utemp = u[0];
         u[0] = cosl[0] * utemp + sinl[0] * u[3];
         u[3] = -sinl[0] * utemp + cosl[0] * u[3];
         utemp = u[1];
         u[1] = cosl[0] * utemp + sinl[0] * u[4];
         u[4] = -sinl[0] * utemp + cosl[0] * u[4];
         utemp = u[2];
         u[2] = cosl[0] * utemp + sinl[0] * u[5];
         u[5] = -sinl[0] * utemp + cosl[0] * u[5];
         double vtemp = v[0];
         v[0] = cosr[0] * vtemp + sinr[0] * v[1];
         v[1] = -sinr[0] * vtemp + cosr[0] * v[1];
         vtemp = v[3];
         v[3] = cosr[0] * vtemp + sinr[0] * v[4];
         v[4] = -sinr[0] * vtemp + cosr[0] * v[4];
         vtemp = v[6];
         v[6] = cosr[0] * vtemp + sinr[0] * v[7];
         v[7] = -sinr[0] * vtemp + cosr[0] * v[7];
      } else {
         compute_2X2(s[1], e[1], s[2], s, sinl, cosl, sinr, cosr, 1);
         double utemp = u[3];
         u[3] = cosl[0] * utemp + sinl[0] * u[6];
         u[6] = -sinl[0] * utemp + cosl[0] * u[6];
         utemp = u[4];
         u[4] = cosl[0] * utemp + sinl[0] * u[7];
         u[7] = -sinl[0] * utemp + cosl[0] * u[7];
         utemp = u[5];
         u[5] = cosl[0] * utemp + sinl[0] * u[8];
         u[8] = -sinl[0] * utemp + cosl[0] * u[8];
         double vtemp = v[1];
         v[1] = cosr[0] * vtemp + sinr[0] * v[2];
         v[2] = -sinr[0] * vtemp + cosr[0] * v[2];
         vtemp = v[4];
         v[4] = cosr[0] * vtemp + sinr[0] * v[5];
         v[5] = -sinr[0] * vtemp + cosr[0] * v[5];
         vtemp = v[7];
         v[7] = cosr[0] * vtemp + sinr[0] * v[8];
         v[8] = -sinr[0] * vtemp + cosr[0] * v[8];
      }

      return 0;
   }

   static double max(double a, double b) {
      return a > b ? a : b;
   }

   static double min(double a, double b) {
      return a < b ? a : b;
   }

   static double d_sign(double a, double b) {
      double x = a >= 0.0 ? a : -a;
      return b >= 0.0 ? x : -x;
   }

   static double compute_shift(double f, double g, double h) {
      double fa = Math.abs(f);
      double ga = Math.abs(g);
      double ha = Math.abs(h);
      double fhmn = min(fa, ha);
      double fhmx = max(fa, ha);
      double ssmin;
      if (fhmn == 0.0) {
         ssmin = 0.0;
         if (fhmx != 0.0) {
            double d__1 = min(fhmx, ga) / max(fhmx, ga);
         }
      } else if (ga < fhmx) {
         double as = fhmn / fhmx + 1.0;
         double at = (fhmx - fhmn) / fhmx;
         double d__1 = ga / fhmx;
         double au = d__1 * d__1;
         double c = 2.0 / (Math.sqrt(as * as + au) + Math.sqrt(at * at + au));
         ssmin = fhmn * c;
      } else {
         double au = fhmx / ga;
         if (au == 0.0) {
            ssmin = fhmn * fhmx / ga;
         } else {
            double as = fhmn / fhmx + 1.0;
            double at = (fhmx - fhmn) / fhmx;
            double d__1 = as * au;
            double d__2 = at * au;
            double c = 1.0 / (Math.sqrt(d__1 * d__1 + 1.0) + Math.sqrt(d__2 * d__2 + 1.0));
            ssmin = fhmn * c * au;
            ssmin += ssmin;
         }
      }

      return ssmin;
   }

   static int compute_2X2(double f, double g, double h, double[] single_values, double[] snl, double[] csl, double[] snr, double[] csr, int index) {
      double c_b3 = 2.0;
      double c_b4 = 1.0;
      double ssmax = single_values[0];
      double ssmin = single_values[1];
      double clt = 0.0;
      double crt = 0.0;
      double slt = 0.0;
      double srt = 0.0;
      double tsign = 0.0;
      double ft = f;
      double fa = Math.abs(ft);
      double ht = h;
      double ha = Math.abs(h);
      int pmax = 1;
      boolean swap = ha > fa;
      if (swap) {
         pmax = 3;
         double temp = ft;
         ft = ht;
         ht = temp;
         temp = fa;
         fa = ha;
         ha = temp;
      }

      double gt = g;
      double ga = Math.abs(gt);
      if (ga == 0.0) {
         single_values[1] = ha;
         single_values[0] = fa;
         clt = 1.0;
         crt = 1.0;
         slt = 0.0;
         srt = 0.0;
      } else {
         boolean gasmal = true;
         if (ga > fa) {
            pmax = 2;
            if (fa / ga < 1.110223024E-16) {
               gasmal = false;
               ssmax = ga;
               if (ha > 1.0) {
                  ssmin = fa / (ga / ha);
               } else {
                  ssmin = fa / ga * ha;
               }

               clt = 1.0;
               slt = ht / gt;
               srt = 1.0;
               crt = ft / gt;
            }
         }

         if (gasmal) {
            double d = fa - ha;
            double l;
            if (d == fa) {
               l = 1.0;
            } else {
               l = d / fa;
            }

            double m = gt / ft;
            double t = 2.0 - l;
            double mm = m * m;
            double tt = t * t;
            double s = Math.sqrt(tt + mm);
            double r;
            if (l == 0.0) {
               r = Math.abs(m);
            } else {
               r = Math.sqrt(l * l + mm);
            }

            double a = (s + r) * 0.5;
            if (ga > fa) {
               pmax = 2;
               if (fa / ga < 1.110223024E-16) {
                  gasmal = false;
                  ssmax = ga;
                  if (ha > 1.0) {
                     ssmin = fa / (ga / ha);
                  } else {
                     ssmin = fa / ga * ha;
                  }

                  clt = 1.0;
                  slt = ht / gt;
                  srt = 1.0;
                  crt = ft / gt;
               }
            }

            if (gasmal) {
               d = fa - ha;
               if (d == fa) {
                  l = 1.0;
               } else {
                  l = d / fa;
               }

               m = gt / ft;
               t = 2.0 - l;
               mm = m * m;
               tt = t * t;
               s = Math.sqrt(tt + mm);
               if (l == 0.0) {
                  r = Math.abs(m);
               } else {
                  r = Math.sqrt(l * l + mm);
               }

               a = (s + r) * 0.5;
               ssmin = ha / a;
               ssmax = fa * a;
               if (mm == 0.0) {
                  if (l == 0.0) {
                     t = d_sign(c_b3, ft) * d_sign(c_b4, gt);
                  } else {
                     t = gt / d_sign(d, ft) + m / t;
                  }
               } else {
                  t = (m / (s + t) + m / (r + l)) * (a + 1.0);
               }

               l = Math.sqrt(t * t + 4.0);
               crt = 2.0 / l;
               srt = t / l;
               clt = (crt + srt * m) / a;
               slt = ht / ft * srt / a;
            }
         }

         if (swap) {
            csl[0] = srt;
            snl[0] = crt;
            csr[0] = slt;
            snr[0] = clt;
         } else {
            csl[0] = clt;
            snl[0] = slt;
            csr[0] = crt;
            snr[0] = srt;
         }

         if (pmax == 1) {
            tsign = d_sign(c_b4, csr[0]) * d_sign(c_b4, csl[0]) * d_sign(c_b4, f);
         }

         if (pmax == 2) {
            tsign = d_sign(c_b4, snr[0]) * d_sign(c_b4, csl[0]) * d_sign(c_b4, g);
         }

         if (pmax == 3) {
            tsign = d_sign(c_b4, snr[0]) * d_sign(c_b4, snl[0]) * d_sign(c_b4, h);
         }

         single_values[index] = d_sign(ssmax, tsign);
         double d__1 = tsign * d_sign(c_b4, f) * d_sign(c_b4, h);
         single_values[index + 1] = d_sign(ssmin, d__1);
      }

      return 0;
   }

   static double compute_rot(double f, double g, double[] sin, double[] cos, int index, int first) {
      double safmn2 = 2.002083095183101E-146;
      double safmx2 = 4.994797680505588E145;
      double cs;
      double sn;
      double r;
      if (g == 0.0) {
         cs = 1.0;
         sn = 0.0;
         r = f;
      } else if (f == 0.0) {
         cs = 0.0;
         sn = 1.0;
         r = g;
      } else {
         double f1 = f;
         double g1 = g;
         double scale = max(Math.abs(f1), Math.abs(g1));
         if (scale >= 4.994797680505588E145) {
            int count = 0;

            while (scale >= 4.994797680505588E145) {
               count++;
               f1 *= 2.002083095183101E-146;
               g1 *= 2.002083095183101E-146;
               scale = max(Math.abs(f1), Math.abs(g1));
            }

            r = Math.sqrt(f1 * f1 + g1 * g1);
            cs = f1 / r;
            sn = g1 / r;

            for (int i = 1; i <= count; i++) {
               r *= 4.994797680505588E145;
            }
         } else if (!(scale <= 2.002083095183101E-146)) {
            r = Math.sqrt(f1 * f1 + g1 * g1);
            cs = f1 / r;
            sn = g1 / r;
         } else {
            int count = 0;

            while (scale <= 2.002083095183101E-146) {
               count++;
               f1 *= 4.994797680505588E145;
               g1 *= 4.994797680505588E145;
               scale = max(Math.abs(f1), Math.abs(g1));
            }

            r = Math.sqrt(f1 * f1 + g1 * g1);
            cs = f1 / r;
            sn = g1 / r;

            for (int i = 1; i <= count; i++) {
               r *= 2.002083095183101E-146;
            }
         }

         if (Math.abs(f) > Math.abs(g) && cs < 0.0) {
            cs = -cs;
            sn = -sn;
            r = -r;
         }
      }

      sin[index] = sn;
      cos[index] = cs;
      return r;
   }

   static void print_mat(double[] mat) {
      for (int i = 0; i < 3; i++) {
         System.out.println(mat[i * 3] + " " + mat[i * 3 + 1] + " " + mat[i * 3 + 2] + "\n");
      }
   }

   static void print_det(double[] mat) {
      double det = mat[0] * mat[4] * mat[8]
         + mat[1] * mat[5] * mat[6]
         + mat[2] * mat[3] * mat[7]
         - mat[2] * mat[4] * mat[6]
         - mat[0] * mat[5] * mat[7]
         - mat[1] * mat[3] * mat[8];
      System.out.println("det= " + det);
   }

   static void mat_mul(double[] m1, double[] m2, double[] m3) {
      double[] tmp = new double[]{
         m1[0] * m2[0] + m1[1] * m2[3] + m1[2] * m2[6],
         m1[0] * m2[1] + m1[1] * m2[4] + m1[2] * m2[7],
         m1[0] * m2[2] + m1[1] * m2[5] + m1[2] * m2[8],
         m1[3] * m2[0] + m1[4] * m2[3] + m1[5] * m2[6],
         m1[3] * m2[1] + m1[4] * m2[4] + m1[5] * m2[7],
         m1[3] * m2[2] + m1[4] * m2[5] + m1[5] * m2[8],
         m1[6] * m2[0] + m1[7] * m2[3] + m1[8] * m2[6],
         m1[6] * m2[1] + m1[7] * m2[4] + m1[8] * m2[7],
         m1[6] * m2[2] + m1[7] * m2[5] + m1[8] * m2[8]
      };

      for (int i = 0; i < 9; i++) {
         m3[i] = tmp[i];
      }
   }

   static void transpose_mat(double[] in, double[] out) {
      out[0] = in[0];
      out[1] = in[3];
      out[2] = in[6];
      out[3] = in[1];
      out[4] = in[4];
      out[5] = in[7];
      out[6] = in[2];
      out[7] = in[5];
      out[8] = in[8];
   }

   static double max3(double[] values) {
      if (values[0] > values[1]) {
         return values[0] > values[2] ? values[0] : values[2];
      } else {
         return values[1] > values[2] ? values[1] : values[2];
      }
   }

   private static boolean almostEqual(double a, double b) {
      if (a == b) {
         return true;
      }

      double EPSILON_ABSOLUTE = 1.0E-6;
      double EPSILON_RELATIVE = 1.0E-4;
      double diff = Math.abs(a - b);
      double absA = Math.abs(a);
      double absB = Math.abs(b);
      double max = absA >= absB ? absA : absB;
      return diff < 1.0E-6 ? true : diff / max < 1.0E-4;
   }
}
