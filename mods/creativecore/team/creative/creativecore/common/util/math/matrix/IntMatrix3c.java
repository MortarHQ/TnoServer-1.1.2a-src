package team.creative.creativecore.common.util.math.matrix;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.phys.Vec3;
import team.creative.creativecore.common.util.math.vec.Vec3d;
import team.creative.creativecore.common.util.math.vec.Vec3f;

public interface IntMatrix3c {
   IntMatrix3 IDENTIY = new IntMatrix3(1, 0, 0, 0, 1, 0, 0, 0, 1);

   int m00();

   int m01();

   int m02();

   int m10();

   int m11();

   int m12();

   int m20();

   int m21();

   int m22();

   int getX(int[] var1);

   int getX(Vec3i var1);

   int getX(int var1, int var2, int var3);

   long getX(long var1, long var3, long var5);

   int getY(int[] var1);

   int getY(Vec3i var1);

   int getY(int var1, int var2, int var3);

   long getY(long var1, long var3, long var5);

   int getZ(int[] var1);

   int getZ(Vec3i var1);

   int getZ(int var1, int var2, int var3);

   long getZ(long var1, long var3, long var5);

   BlockPos transform(BlockPos var1);

   Vec3i transform(Vec3i var1);

   Vec3 transform(Vec3 var1);

   void transform(Vec3d var1);

   void transform(Vec3f var1);

   boolean invertedX();

   <T> T getX(T var1, T var2, T var3);

   boolean invertedY();

   <T> T getY(T var1, T var2, T var3);

   boolean invertedZ();

   <T> T getZ(T var1, T var2, T var3);

   int[] getAsArray();

   boolean isIdentity();
}
