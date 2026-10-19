package com.yungnickyoung.minecraft.yungscavebiomes.world.noise;

import net.minecraft.util.Mth;

public class CellularNoise {
   private static final long PRIME_X = 5910200641878280303L;
   private static final long PRIME_Y = 6452764530575939509L;
   private static final long PRIME_Z = 6614699811220273867L;
   private static final long HASH_MULTIPLIER = 6026932503003350773L;
   private static final int N_JITTER_HASH_BITS_PER_AXIS = 21;
   private static final int JITTER_HASH_MASK = 2097151;
   private static final float JITTER_HASH_RESCALE = 4.7683716E-7F;
   private static final double ROOT3OVER3 = 0.577350269189626;
   private static final double ROTATE3_ORTHOGONALIZER = -0.21132486540518713;
   public static final float MAX_DISTANCE_SQUARED_TO_CLOSEST = 3.0F;
   public static final float MAX_DISTANCE_TO_CLOSEST = Mth.sqrt(3.0F);

   public static float sampleDistance2Sub_ImproveXZ(long seed, double x, double y, double z) {
      double xz = x + z;
      double s2 = xz * -0.21132486540518713;
      double yy = y * 0.577350269189626;
      double xr = x + s2 + yy;
      double zr = z + s2 + yy;
      double yr = xz * -0.577350269189626 + yy;
      return sampleDistance2Sub(seed, xr, yr, zr);
   }

   public static float sampleDistance2Sub(long seed, double x, double y, double z) {
      int xBaseStart = Mth.floor(x - MAX_DISTANCE_TO_CLOSEST);
      int yBaseStart = Mth.floor(y - MAX_DISTANCE_TO_CLOSEST);
      int zBaseStart = Mth.floor(z - MAX_DISTANCE_TO_CLOSEST);
      int xRange = Mth.floor(x + MAX_DISTANCE_TO_CLOSEST) - xBaseStart;
      int yRange = Mth.floor(y + MAX_DISTANCE_TO_CLOSEST) - yBaseStart;
      int zRange = Mth.floor(z + MAX_DISTANCE_TO_CLOSEST) - zBaseStart;
      float xiStart = (float)(x - xBaseStart);
      float yiStart = (float)(y - yBaseStart);
      float ziStart = (float)(z - zBaseStart);
      long xStartPrimed = xBaseStart * 5910200641878280303L;
      long yStartPrimed = yBaseStart * 6452764530575939509L;
      long zStartPrimed = zBaseStart * 6614699811220273867L;
      float distSq1 = 3.0F;
      float distSq2 = 3.0F;
      int ix = 0;
      int iy = 0;
      int iz = 0;
      float xi = xiStart;
      float yi = yiStart;
      float zi = ziStart;
      long xPrimed = xStartPrimed;
      long yPrimed = yStartPrimed;
      long zPrimed = zStartPrimed;

      while (true) {
         float distSqHere = calculateDistanceSquared(seed, xPrimed, yPrimed, zPrimed, xi, yi, zi);
         if (distSqHere < distSq2) {
            distSq2 = distSqHere;
            if (distSqHere < distSq1) {
               distSq2 = distSq1;
               distSq1 = distSqHere;
            }
         }

         ix++;
         xi--;
         xPrimed += 5910200641878280303L;
         if (ix > xRange) {
            ix = 0;
            xi = xiStart;
            xPrimed = xStartPrimed;
            iy++;
            yi--;
            yPrimed += 6452764530575939509L;
            if (iy > yRange) {
               iy = 0;
               yi = yiStart;
               yPrimed = yStartPrimed;
               iz++;
               zi--;
               zPrimed += 6614699811220273867L;
               if (iz > zRange) {
                  return Mth.sqrt(distSq2) - Mth.sqrt(distSq1);
               }
            }
         }
      }
   }

   private static float calculateDistanceSquared(long seed, long xPrimed, long yPrimed, long zPrimed, float xi, float yi, float zi) {
      long hash = hash(seed, xPrimed, yPrimed, zPrimed);
      float jx = (float)((hash >> 43 ^ hash) & 2097151L) * 4.7683716E-7F;
      float jy = (float)((hash >> 43 ^ hash >> 22) & 2097151L) * 4.7683716E-7F;
      float jz = (float)(hash >> 43 & 2097151L) * 4.7683716E-7F;
      return Mth.square(xi - jx) + Mth.square(yi - jy) + Mth.square(zi - jz);
   }

   private static long hash(long seed, long xPrimed, long yPrimed, long zPrimed) {
      long hash = seed ^ xPrimed ^ yPrimed ^ zPrimed;
      return hash * 6026932503003350773L;
   }
}
