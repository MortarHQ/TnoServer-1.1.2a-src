package team.creative.creativecore.common.util.math.matrix;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.phys.Vec3;
import team.creative.creativecore.common.util.math.vec.Vec3d;
import team.creative.creativecore.common.util.math.vec.Vec3f;

public class IntMatrix3 implements IntMatrix3c {
   private int m00;
   private int m01;
   private int m02;
   private int m10;
   private int m11;
   private int m12;
   private int m20;
   private int m21;
   private int m22;

   private static int fma(int a, int b, int c) {
      return a * b + c;
   }

   public IntMatrix3() {
      this(1, 0, 0, 0, 1, 0, 0, 0, 1);
   }

   public IntMatrix3(int[] array) {
      if (array.length != 9) {
         throw new IllegalArgumentException();
      }

      this.m00 = array[0];
      this.m01 = array[1];
      this.m02 = array[2];
      this.m10 = array[3];
      this.m11 = array[4];
      this.m12 = array[5];
      this.m20 = array[6];
      this.m21 = array[7];
      this.m22 = array[8];
   }

   public IntMatrix3(int m00, int m01, int m02, int m10, int m11, int m12, int m20, int m21, int m22) {
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

   public IntMatrix3(IntMatrix3c matrix) {
      this.m00 = matrix.m00();
      this.m01 = matrix.m01();
      this.m02 = matrix.m02();
      this.m10 = matrix.m10();
      this.m11 = matrix.m11();
      this.m12 = matrix.m12();
      this.m20 = matrix.m20();
      this.m21 = matrix.m21();
      this.m22 = matrix.m22();
   }

   public IntMatrix3(IntMatrix3c m1, IntMatrix3c m2) {
      this.m00 = m1.m00() * m2.m00() + m1.m01() * m2.m10() + m1.m02() * m2.m20();
      this.m01 = m1.m00() * m2.m01() + m1.m01() * m2.m11() + m1.m02() * m2.m21();
      this.m02 = m1.m00() * m2.m02() + m1.m01() * m2.m12() + m1.m02() * m2.m22();
      this.m10 = m1.m10() * m2.m00() + m1.m11() * m2.m10() + m1.m12() * m2.m20();
      this.m11 = m1.m10() * m2.m01() + m1.m11() * m2.m11() + m1.m12() * m2.m21();
      this.m12 = m1.m10() * m2.m02() + m1.m11() * m2.m12() + m1.m12() * m2.m22();
      this.m20 = m1.m20() * m2.m00() + m1.m21() * m2.m10() + m1.m22() * m2.m20();
      this.m21 = m1.m20() * m2.m01() + m1.m21() * m2.m11() + m1.m22() * m2.m21();
      this.m22 = m1.m20() * m2.m02() + m1.m21() * m2.m12() + m1.m22() * m2.m22();
   }

   @Override
   public int getX(int[] vec) {
      return this.getX(vec[0], vec[1], vec[2]);
   }

   @Override
   public int getX(Vec3i vec) {
      return this.getX(vec.getX(), vec.getY(), vec.getZ());
   }

   @Override
   public int getX(int x, int y, int z) {
      return x * this.m00 + y * this.m01 + z * this.m02;
   }

   @Override
   public long getX(long x, long y, long z) {
      return x * this.m00 + y * this.m01 + z * this.m02;
   }

   @Override
   public int getY(int[] vec) {
      return this.getY(vec[0], vec[1], vec[2]);
   }

   @Override
   public int getY(Vec3i vec) {
      return this.getY(vec.getX(), vec.getY(), vec.getZ());
   }

   @Override
   public int getY(int x, int y, int z) {
      return x * this.m10 + y * this.m11 + z * this.m12;
   }

   @Override
   public long getY(long x, long y, long z) {
      return x * this.m10 + y * this.m11 + z * this.m12;
   }

   @Override
   public int getZ(int[] vec) {
      return this.getZ(vec[0], vec[1], vec[2]);
   }

   @Override
   public int getZ(Vec3i vec) {
      return this.getZ(vec.getX(), vec.getY(), vec.getZ());
   }

   @Override
   public int getZ(int x, int y, int z) {
      return x * this.m20 + y * this.m21 + z * this.m22;
   }

   @Override
   public long getZ(long x, long y, long z) {
      return x * this.m20 + y * this.m21 + z * this.m22;
   }

   @Override
   public BlockPos transform(BlockPos vec) {
      int x = vec.getX() * this.m00 + vec.getY() * this.m01 + vec.getZ() * this.m02;
      int y = vec.getX() * this.m10 + vec.getY() * this.m11 + vec.getZ() * this.m12;
      int z = vec.getX() * this.m20 + vec.getY() * this.m21 + vec.getZ() * this.m22;
      return new BlockPos(x, y, z);
   }

   @Override
   public Vec3i transform(Vec3i vec) {
      int x = vec.getX() * this.m00 + vec.getY() * this.m01 + vec.getZ() * this.m02;
      int y = vec.getX() * this.m10 + vec.getY() * this.m11 + vec.getZ() * this.m12;
      int z = vec.getX() * this.m20 + vec.getY() * this.m21 + vec.getZ() * this.m22;
      return new Vec3i(x, y, z);
   }

   @Override
   public Vec3 transform(Vec3 vec) {
      double x = vec.x * this.m00 + vec.y * this.m01 + vec.z * this.m02;
      double y = vec.x * this.m10 + vec.y * this.m11 + vec.z * this.m12;
      double z = vec.x * this.m20 + vec.y * this.m21 + vec.z * this.m22;
      return new Vec3(x, y, z);
   }

   @Override
   public void transform(Vec3d triple) {
      double x = triple.x * this.m00 + triple.y * this.m01 + triple.z * this.m02;
      double y = triple.x * this.m10 + triple.y * this.m11 + triple.z * this.m12;
      double z = triple.x * this.m20 + triple.y * this.m21 + triple.z * this.m22;
      triple.set(x, y, z);
   }

   @Override
   public void transform(Vec3f triple) {
      float x = triple.x * this.m00 + triple.y * this.m01 + triple.z * this.m02;
      float y = triple.x * this.m10 + triple.y * this.m11 + triple.z * this.m12;
      float z = triple.x * this.m20 + triple.y * this.m21 + triple.z * this.m22;
      triple.set(x, y, z);
   }

   public IntMatrix3c mul(IntMatrix3c right) {
      return this.mul(right, this);
   }

   public IntMatrix3c mul(IntMatrix3c right, IntMatrix3 dest) {
      int nm00 = fma(this.m00, right.m00(), fma(this.m10, right.m01(), this.m20 * right.m02()));
      int nm01 = fma(this.m01, right.m00(), fma(this.m11, right.m01(), this.m21 * right.m02()));
      int nm02 = fma(this.m02, right.m00(), fma(this.m12, right.m01(), this.m22 * right.m02()));
      int nm10 = fma(this.m00, right.m10(), fma(this.m10, right.m11(), this.m20 * right.m12()));
      int nm11 = fma(this.m01, right.m10(), fma(this.m11, right.m11(), this.m21 * right.m12()));
      int nm12 = fma(this.m02, right.m10(), fma(this.m12, right.m11(), this.m22 * right.m12()));
      int nm20 = fma(this.m00, right.m20(), fma(this.m10, right.m21(), this.m20 * right.m22()));
      int nm21 = fma(this.m01, right.m20(), fma(this.m11, right.m21(), this.m21 * right.m22()));
      int nm22 = fma(this.m02, right.m20(), fma(this.m12, right.m21(), this.m22 * right.m22()));
      dest.m00 = nm00;
      dest.m01 = nm01;
      dest.m02 = nm02;
      dest.m10 = nm10;
      dest.m11 = nm11;
      dest.m12 = nm12;
      dest.m20 = nm20;
      dest.m21 = nm21;
      dest.m22 = nm22;
      return dest;
   }

   @Override
   public boolean equals(Object object) {
      return !(object instanceof IntMatrix3 m1)
         ? false
         : this.m00 == m1.m00
            && this.m01 == m1.m01
            && this.m02 == m1.m02
            && this.m10 == m1.m10
            && this.m11 == m1.m11
            && this.m12 == m1.m12
            && this.m20 == m1.m20
            && this.m21 == m1.m21
            && this.m22 == m1.m22;
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

   @Override
   public int m00() {
      return this.m00;
   }

   @Override
   public int m01() {
      return this.m01;
   }

   @Override
   public int m02() {
      return this.m02;
   }

   @Override
   public int m10() {
      return this.m10;
   }

   @Override
   public int m11() {
      return this.m11;
   }

   @Override
   public int m12() {
      return this.m12;
   }

   @Override
   public int m20() {
      return this.m20;
   }

   @Override
   public int m21() {
      return this.m21;
   }

   @Override
   public int m22() {
      return this.m22;
   }

   @Override
   public boolean invertedX() {
      return this.m00 != 0 ? this.m00 < 0 : (this.m01 != 0 ? this.m01 < 0 : this.m02 < 0);
   }

   @Override
   public <T> T getX(T x, T y, T z) {
      return this.m00 != 0 ? x : (this.m01 != 0 ? y : z);
   }

   @Override
   public boolean invertedY() {
      return this.m10 != 0 ? this.m10 < 0 : (this.m11 != 0 ? this.m11 < 0 : this.m12 < 0);
   }

   @Override
   public <T> T getY(T x, T y, T z) {
      return this.m10 != 0 ? x : (this.m11 != 0 ? y : z);
   }

   @Override
   public boolean invertedZ() {
      return this.m20 != 0 ? this.m20 < 0 : (this.m21 != 0 ? this.m21 < 0 : this.m22 < 0);
   }

   @Override
   public <T> T getZ(T x, T y, T z) {
      return this.m20 != 0 ? x : (this.m21 != 0 ? y : z);
   }

   @Override
   public int[] getAsArray() {
      return new int[]{this.m00, this.m01, this.m02, this.m10, this.m11, this.m12, this.m20, this.m21, this.m22};
   }

   @Override
   public boolean isIdentity() {
      return this.m00 == 1
         && this.m01 == 0
         && this.m02 == 0
         && this.m10 == 0
         && this.m11 == 1
         && this.m12 == 0
         && this.m20 == 0
         && this.m21 == 0
         && this.m22 == 1;
   }
}
