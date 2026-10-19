package com.yungnickyoung.minecraft.yungscavebiomes.world.noise;

import org.joml.Vector3f;
import org.joml.Vector4f;

public class OpenSimplex2S {
   private static final long PRIME_X = 5910200641878280303L;
   private static final long PRIME_Y = 6452764530575939509L;
   private static final long PRIME_Z = 6614699811220273867L;
   private static final long PRIME_W = 6254464313819354443L;
   private static final long HASH_MULTIPLIER = 6026932503003350773L;
   private static final long SEED_FLIP_3D = -5968755714895566377L;
   private static final double ROOT3OVER3 = 0.577350269189626;
   private static final double FALLBACK_ROTATE3 = 0.6666666666666666;
   private static final double ROTATE3_ORTHOGONALIZER = -0.21132486540518713;
   private static final float SKEW_4D = 0.309017F;
   private static final float UNSKEW_4D = -0.1381966F;
   private static final int N_GRADS_3D_EXPONENT = 8;
   private static final int N_GRADS_4D_EXPONENT = 9;
   private static final int N_GRADS_3D = 256;
   private static final int N_GRADS_4D = 512;
   private static final int GRAD_HASH_MASK_3D = 1020;
   private static final int GRAD_HASH_MASK_4D = 2044;
   private static final int N_OUTPUT_VEC3 = 256;
   private static final int VEC3_HASH_SHIFT_3D = 8;
   private static final int VEC3_HASH_MASK_3D = 1020;
   private static final double NORMALIZER_3D = 0.2781926117527186;
   private static final double NORMALIZER_4D = 0.11127401889945551;
   private static final float RSQUARED_3D = 0.75F;
   private static final float RSQUARED_4D = 0.8F;
   private static float[] GRADIENTS_3D = new float[1024];
   private static float[] GRADIENTS_4D;
   private static int[] LOOKUP_4D_A;
   private static OpenSimplex2S.LatticeVertex4D[] LOOKUP_4D_B;
   private static final float[] OUTPUT_VEC3;

   public static float noise3_ImproveXY(long seed, double x, double y, double z) {
      double xy = x + y;
      double s2 = xy * -0.21132486540518713;
      double zz = z * 0.577350269189626;
      double xr = x + s2 + zz;
      double yr = y + s2 + zz;
      double zr = xy * -0.577350269189626 + zz;
      return noise3_UnrotatedBase(seed, xr, yr, zr);
   }

   public static float noise3_ImproveXZ(long seed, double x, double y, double z) {
      double xz = x + z;
      double s2 = xz * -0.21132486540518713;
      double yy = y * 0.577350269189626;
      double xr = x + s2 + yy;
      double zr = z + s2 + yy;
      double yr = xz * -0.577350269189626 + yy;
      return noise3_UnrotatedBase(seed, xr, yr, zr);
   }

   public static float noise3_Fallback(long seed, double x, double y, double z) {
      double r = 0.6666666666666666 * (x + y + z);
      double xr = r - x;
      double yr = r - y;
      double zr = r - z;
      return noise3_UnrotatedBase(seed, xr, yr, zr);
   }

   private static float noise3_UnrotatedBase(long seed, double xr, double yr, double zr) {
      int xrb = fastFloor(xr);
      int yrb = fastFloor(yr);
      int zrb = fastFloor(zr);
      float xi = (float)(xr - xrb);
      float yi = (float)(yr - yrb);
      float zi = (float)(zr - zrb);
      long xrbp = xrb * 5910200641878280303L;
      long yrbp = yrb * 6452764530575939509L;
      long zrbp = zrb * 6614699811220273867L;
      long seed2 = seed ^ -5968755714895566377L;
      int xNMask = (int)(-0.5F - xi);
      int yNMask = (int)(-0.5F - yi);
      int zNMask = (int)(-0.5F - zi);
      float x0 = xi + xNMask;
      float y0 = yi + yNMask;
      float z0 = zi + zNMask;
      float a0 = 0.75F - x0 * x0 - y0 * y0 - z0 * z0;
      float value = a0
         * a0
         * (a0 * a0)
         * grad(seed, xrbp + (xNMask & 5910200641878280303L), yrbp + (yNMask & 6452764530575939509L), zrbp + (zNMask & 6614699811220273867L), x0, y0, z0);
      float x1 = xi - 0.5F;
      float y1 = yi - 0.5F;
      float z1 = zi - 0.5F;
      float a1 = 0.75F - x1 * x1 - y1 * y1 - z1 * z1;
      value += a1 * a1 * (a1 * a1) * grad(seed2, xrbp + 5910200641878280303L, yrbp + 6452764530575939509L, zrbp + 6614699811220273867L, x1, y1, z1);
      float xAFlipMask0 = ((xNMask | 1) << 1) * x1;
      float yAFlipMask0 = ((yNMask | 1) << 1) * y1;
      float zAFlipMask0 = ((zNMask | 1) << 1) * z1;
      float xAFlipMask1 = (-2 - (xNMask << 2)) * x1 - 1.0F;
      float yAFlipMask1 = (-2 - (yNMask << 2)) * y1 - 1.0F;
      float zAFlipMask1 = (-2 - (zNMask << 2)) * z1 - 1.0F;
      boolean skip5 = false;
      float a2 = xAFlipMask0 + a0;
      if (a2 > 0.0F) {
         float x2 = x0 - (xNMask | 1);
         float y2 = y0;
         float z2 = z0;
         value += a2
            * a2
            * (a2 * a2)
            * grad(seed, xrbp + (~xNMask & 5910200641878280303L), yrbp + (yNMask & 6452764530575939509L), zrbp + (zNMask & 6614699811220273867L), x2, y2, z2);
      } else {
         float a3 = yAFlipMask0 + zAFlipMask0 + a0;
         if (a3 > 0.0F) {
            float x3 = x0;
            float y3 = y0 - (yNMask | 1);
            float z3 = z0 - (zNMask | 1);
            value += a3
               * a3
               * (a3 * a3)
               * grad(
                  seed, xrbp + (xNMask & 5910200641878280303L), yrbp + (~yNMask & 6452764530575939509L), zrbp + (~zNMask & 6614699811220273867L), x3, y3, z3
               );
         }

         float a4 = xAFlipMask1 + a1;
         if (a4 > 0.0F) {
            float x4 = (xNMask | 1) + x1;
            float y4 = y1;
            float z4 = z1;
            value += a4
               * a4
               * (a4 * a4)
               * grad(seed2, xrbp + (xNMask & -6626342789952991010L), yrbp + 6452764530575939509L, zrbp + 6614699811220273867L, x4, y4, z4);
            skip5 = true;
         }
      }

      boolean skip9 = false;
      float a6 = yAFlipMask0 + a0;
      if (a6 > 0.0F) {
         float x6 = x0;
         float y6 = y0 - (yNMask | 1);
         float z6 = z0;
         value += a6
            * a6
            * (a6 * a6)
            * grad(seed, xrbp + (xNMask & 5910200641878280303L), yrbp + (~yNMask & 6452764530575939509L), zrbp + (zNMask & 6614699811220273867L), x6, y6, z6);
      } else {
         float a7 = xAFlipMask0 + zAFlipMask0 + a0;
         if (a7 > 0.0F) {
            float x7 = x0 - (xNMask | 1);
            float y7 = y0;
            float z7 = z0 - (zNMask | 1);
            value += a7
               * a7
               * (a7 * a7)
               * grad(
                  seed, xrbp + (~xNMask & 5910200641878280303L), yrbp + (yNMask & 6452764530575939509L), zrbp + (~zNMask & 6614699811220273867L), x7, y7, z7
               );
         }

         float a8 = yAFlipMask1 + a1;
         if (a8 > 0.0F) {
            float x8 = x1;
            float y8 = (yNMask | 1) + y1;
            float z8 = z1;
            value += a8
               * a8
               * (a8 * a8)
               * grad(seed2, xrbp + 5910200641878280303L, yrbp + (yNMask & -5541215012557672598L), zrbp + 6614699811220273867L, x8, y8, z8);
            skip9 = true;
         }
      }

      boolean skipD = false;
      float aA = zAFlipMask0 + a0;
      if (aA > 0.0F) {
         float xA = x0;
         float yA = y0;
         float zA = z0 - (zNMask | 1);
         value += aA
            * aA
            * (aA * aA)
            * grad(seed, xrbp + (xNMask & 5910200641878280303L), yrbp + (yNMask & 6452764530575939509L), zrbp + (~zNMask & 6614699811220273867L), xA, yA, zA);
      } else {
         float aB = xAFlipMask0 + yAFlipMask0 + a0;
         if (aB > 0.0F) {
            float xB = x0 - (xNMask | 1);
            float yB = y0 - (yNMask | 1);
            float zB = z0;
            value += aB
               * aB
               * (aB * aB)
               * grad(
                  seed, xrbp + (~xNMask & 5910200641878280303L), yrbp + (~yNMask & 6452764530575939509L), zrbp + (zNMask & 6614699811220273867L), xB, yB, zB
               );
         }

         float aC = zAFlipMask1 + a1;
         if (aC > 0.0F) {
            float xC = x1;
            float yC = y1;
            float zC = (zNMask | 1) + z1;
            value += aC
               * aC
               * (aC * aC)
               * grad(seed2, xrbp + 5910200641878280303L, yrbp + 6452764530575939509L, zrbp + (zNMask & -5217344451269003882L), xC, yC, zC);
            skipD = true;
         }
      }

      if (!skip5) {
         float a5 = yAFlipMask1 + zAFlipMask1 + a1;
         if (a5 > 0.0F) {
            float x5 = x1;
            float y5 = (yNMask | 1) + y1;
            float z5 = (zNMask | 1) + z1;
            value += a5
               * a5
               * (a5 * a5)
               * grad(seed2, xrbp + 5910200641878280303L, yrbp + (yNMask & -5541215012557672598L), zrbp + (zNMask & -5217344451269003882L), x5, y5, z5);
         }
      }

      if (!skip9) {
         float a9 = xAFlipMask1 + zAFlipMask1 + a1;
         if (a9 > 0.0F) {
            float x9 = (xNMask | 1) + x1;
            float y9 = y1;
            float z9 = (zNMask | 1) + z1;
            value += a9
               * a9
               * (a9 * a9)
               * grad(seed2, xrbp + (xNMask & -6626342789952991010L), yrbp + 6452764530575939509L, zrbp + (zNMask & -5217344451269003882L), x9, y9, z9);
         }
      }

      if (!skipD) {
         float aD = xAFlipMask1 + yAFlipMask1 + a1;
         if (aD > 0.0F) {
            float xD = (xNMask | 1) + x1;
            float yD = (yNMask | 1) + y1;
            float zD = z1;
            value += aD
               * aD
               * (aD * aD)
               * grad(seed2, xrbp + (xNMask & -6626342789952991010L), yrbp + (yNMask & -5541215012557672598L), zrbp + 6614699811220273867L, xD, yD, zD);
         }
      }

      return value;
   }

   public static void vec3Noise3_ImproveXZ(long seed, double x, double y, double z, Vector3f output) {
      double xz = x + z;
      double s2 = xz * -0.21132486540518713;
      double yy = y * 0.577350269189626;
      double xr = x + s2 + yy;
      double zr = z + s2 + yy;
      double yr = xz * -0.577350269189626 + yy;
      vec3Noise3_UnrotatedBase(seed, xr, yr, zr, output);
   }

   private static void vec3Noise3_UnrotatedBase(long seed, double xr, double yr, double zr, Vector3f output) {
      int xrb = fastFloor(xr);
      int yrb = fastFloor(yr);
      int zrb = fastFloor(zr);
      float xi = (float)(xr - xrb);
      float yi = (float)(yr - yrb);
      float zi = (float)(zr - zrb);
      long xrbp = xrb * 5910200641878280303L;
      long yrbp = yrb * 6452764530575939509L;
      long zrbp = zrb * 6614699811220273867L;
      long seed2 = seed ^ -5968755714895566377L;
      int xNMask = (int)(-0.5F - xi);
      int yNMask = (int)(-0.5F - yi);
      int zNMask = (int)(-0.5F - zi);
      float x0 = xi + xNMask;
      float y0 = yi + yNMask;
      float z0 = zi + zNMask;
      float a0 = 0.75F - x0 * x0 - y0 * y0 - z0 * z0;
      int h0 = v3Hash(seed, xrbp + (xNMask & 5910200641878280303L), yrbp + (yNMask & 6452764530575939509L), zrbp + (zNMask & 6614699811220273867L));
      float v0 = a0 * a0 * (a0 * a0) * grad(h0, x0, y0, z0);
      int vi0 = h0 >> 8 & 1020;
      float vx = v0 * OUTPUT_VEC3[vi0 | 0];
      float vy = v0 * OUTPUT_VEC3[vi0 | 1];
      float vz = v0 * OUTPUT_VEC3[vi0 | 2];
      float x1 = xi - 0.5F;
      float y1 = yi - 0.5F;
      float z1 = zi - 0.5F;
      float a1 = 0.75F - x1 * x1 - y1 * y1 - z1 * z1;
      int h1 = v3Hash(seed2, xrbp + 5910200641878280303L, yrbp + 6452764530575939509L, zrbp + 6614699811220273867L);
      float v1 = a1 * a1 * (a1 * a1) * grad(h1, x1, y1, z1);
      int vi1 = h1 >> 8 & 1020;
      vx += v1 * OUTPUT_VEC3[vi1 | 0];
      vy += v1 * OUTPUT_VEC3[vi1 | 1];
      vz += v1 * OUTPUT_VEC3[vi1 | 2];
      float xAFlipMask0 = ((xNMask | 1) << 1) * x1;
      float yAFlipMask0 = ((yNMask | 1) << 1) * y1;
      float zAFlipMask0 = ((zNMask | 1) << 1) * z1;
      float xAFlipMask1 = (-2 - (xNMask << 2)) * x1 - 1.0F;
      float yAFlipMask1 = (-2 - (yNMask << 2)) * y1 - 1.0F;
      float zAFlipMask1 = (-2 - (zNMask << 2)) * z1 - 1.0F;
      boolean skip5 = false;
      float a2 = xAFlipMask0 + a0;
      if (a2 > 0.0F) {
         float x2 = x0 - (xNMask | 1);
         float y2 = y0;
         float z2 = z0;
         int h2 = v3Hash(seed, xrbp + (~xNMask & 5910200641878280303L), yrbp + (yNMask & 6452764530575939509L), zrbp + (zNMask & 6614699811220273867L));
         float v2 = a2 * a2 * (a2 * a2) * grad(h2, x2, y2, z2);
         int vi2 = h2 >> 8 & 1020;
         vx += v2 * OUTPUT_VEC3[vi2 | 0];
         vy += v2 * OUTPUT_VEC3[vi2 | 1];
         vz += v2 * OUTPUT_VEC3[vi2 | 2];
      } else {
         float a3 = yAFlipMask0 + zAFlipMask0 + a0;
         if (a3 > 0.0F) {
            float x3 = x0;
            float y3 = y0 - (yNMask | 1);
            float z3 = z0 - (zNMask | 1);
            int h3 = v3Hash(seed, xrbp + (xNMask & 5910200641878280303L), yrbp + (~yNMask & 6452764530575939509L), zrbp + (~zNMask & 6614699811220273867L));
            float v3 = a3 * a3 * (a3 * a3) * grad(h3, x3, y3, z3);
            int vi3 = h3 >> 8 & 1020;
            vx += v3 * OUTPUT_VEC3[vi3 | 0];
            vy += v3 * OUTPUT_VEC3[vi3 | 1];
            vz += v3 * OUTPUT_VEC3[vi3 | 2];
         }

         float a4 = xAFlipMask1 + a1;
         if (a4 > 0.0F) {
            float x4 = (xNMask | 1) + x1;
            float y4 = y1;
            float z4 = z1;
            int h4 = v3Hash(seed2, xrbp + (xNMask & -6626342789952991010L), yrbp + 6452764530575939509L, zrbp + 6614699811220273867L);
            float v4 = a4 * a4 * (a4 * a4) * grad(h4, x4, y4, z4);
            int vi4 = h4 >> 8 & 1020;
            vx += v4 * OUTPUT_VEC3[vi4 | 0];
            vy += v4 * OUTPUT_VEC3[vi4 | 1];
            vz += v4 * OUTPUT_VEC3[vi4 | 2];
            skip5 = true;
         }
      }

      boolean skip9 = false;
      float a6 = yAFlipMask0 + a0;
      if (a6 > 0.0F) {
         float x6 = x0;
         float y6 = y0 - (yNMask | 1);
         float z6 = z0;
         int h6 = v3Hash(seed, xrbp + (xNMask & 5910200641878280303L), yrbp + (~yNMask & 6452764530575939509L), zrbp + (zNMask & 6614699811220273867L));
         float v6 = a6 * a6 * (a6 * a6) * grad(h6, x6, y6, z6);
         int vi6 = h6 >> 8 & 1020;
         vx += v6 * OUTPUT_VEC3[vi6 | 0];
         vy += v6 * OUTPUT_VEC3[vi6 | 1];
         vz += v6 * OUTPUT_VEC3[vi6 | 2];
      } else {
         float a7 = xAFlipMask0 + zAFlipMask0 + a0;
         if (a7 > 0.0F) {
            float x7 = x0 - (xNMask | 1);
            float y7 = y0;
            float z7 = z0 - (zNMask | 1);
            int h7 = v3Hash(seed, xrbp + (~xNMask & 5910200641878280303L), yrbp + (yNMask & 6452764530575939509L), zrbp + (~zNMask & 6614699811220273867L));
            float v7 = a7 * a7 * (a7 * a7) * grad(h7, x7, y7, z7);
            int vi7 = h7 >> 8 & 1020;
            vx += v7 * OUTPUT_VEC3[vi7 | 0];
            vy += v7 * OUTPUT_VEC3[vi7 | 1];
            vz += v7 * OUTPUT_VEC3[vi7 | 2];
         }

         float a8 = yAFlipMask1 + a1;
         if (a8 > 0.0F) {
            float x8 = x1;
            float y8 = (yNMask | 1) + y1;
            float z8 = z1;
            int h8 = v3Hash(seed2, xrbp + 5910200641878280303L, yrbp + (yNMask & -5541215012557672598L), zrbp + 6614699811220273867L);
            float v8 = a8 * a8 * (a8 * a8) * grad(h8, x8, y8, z8);
            int vi8 = h8 >> 8 & 1020;
            vx += v8 * OUTPUT_VEC3[vi8 | 0];
            vy += v8 * OUTPUT_VEC3[vi8 | 1];
            vz += v8 * OUTPUT_VEC3[vi8 | 2];
            skip9 = true;
         }
      }

      boolean skipD = false;
      float aA = zAFlipMask0 + a0;
      if (aA > 0.0F) {
         float xA = x0;
         float yA = y0;
         float zA = z0 - (zNMask | 1);
         int hA = v3Hash(seed, xrbp + (xNMask & 5910200641878280303L), yrbp + (yNMask & 6452764530575939509L), zrbp + (~zNMask & 6614699811220273867L));
         float vA = aA * aA * (aA * aA) * grad(hA, xA, yA, zA);
         int viA = hA >> 8 & 1020;
         vx += vA * OUTPUT_VEC3[viA | 0];
         vy += vA * OUTPUT_VEC3[viA | 1];
         vz += vA * OUTPUT_VEC3[viA | 2];
      } else {
         float aB = xAFlipMask0 + yAFlipMask0 + a0;
         if (aB > 0.0F) {
            float xB = x0 - (xNMask | 1);
            float yB = y0 - (yNMask | 1);
            float zB = z0;
            int hB = v3Hash(seed, xrbp + (~xNMask & 5910200641878280303L), yrbp + (~yNMask & 6452764530575939509L), zrbp + (zNMask & 6614699811220273867L));
            float vB = aB * aB * (aB * aB) * grad(hB, xB, yB, zB);
            int viB = hB >> 8 & 1020;
            vx += vB * OUTPUT_VEC3[viB | 0];
            vy += vB * OUTPUT_VEC3[viB | 1];
            vz += vB * OUTPUT_VEC3[viB | 2];
         }

         float aC = zAFlipMask1 + a1;
         if (aC > 0.0F) {
            float xC = x1;
            float yC = y1;
            float zC = (zNMask | 1) + z1;
            int hC = v3Hash(seed2, xrbp + 5910200641878280303L, yrbp + 6452764530575939509L, zrbp + (zNMask & -5217344451269003882L));
            float vC = aC * aC * (aC * aC) * grad(hC, xC, yC, zC);
            int viC = hC >> 8 & 1020;
            vx += vC * OUTPUT_VEC3[viC | 0];
            vy += vC * OUTPUT_VEC3[viC | 1];
            vz += vC * OUTPUT_VEC3[viC | 2];
            skipD = true;
         }
      }

      if (!skip5) {
         float a5 = yAFlipMask1 + zAFlipMask1 + a1;
         if (a5 > 0.0F) {
            float x5 = x1;
            float y5 = (yNMask | 1) + y1;
            float z5 = (zNMask | 1) + z1;
            int h5 = v3Hash(seed2, xrbp + 5910200641878280303L, yrbp + (yNMask & -5541215012557672598L), zrbp + (zNMask & -5217344451269003882L));
            float v5 = a5 * a5 * (a5 * a5) * grad(h5, x5, y5, z5);
            int vi5 = h5 >> 8 & 1020;
            vx += v5 * OUTPUT_VEC3[vi5 | 0];
            vy += v5 * OUTPUT_VEC3[vi5 | 1];
            vz += v5 * OUTPUT_VEC3[vi5 | 2];
         }
      }

      if (!skip9) {
         float a9 = xAFlipMask1 + zAFlipMask1 + a1;
         if (a9 > 0.0F) {
            float x9 = (xNMask | 1) + x1;
            float y9 = y1;
            float z9 = (zNMask | 1) + z1;
            int h9 = v3Hash(seed2, xrbp + (xNMask & -6626342789952991010L), yrbp + 6452764530575939509L, zrbp + (zNMask & -5217344451269003882L));
            float v9 = a9 * a9 * (a9 * a9) * grad(h9, x9, y9, z9);
            int vi9 = h9 >> 8 & 1020;
            vx += v9 * OUTPUT_VEC3[vi9 | 0];
            vy += v9 * OUTPUT_VEC3[vi9 | 1];
            vz += v9 * OUTPUT_VEC3[vi9 | 2];
         }
      }

      if (!skipD) {
         float aD = xAFlipMask1 + yAFlipMask1 + a1;
         if (aD > 0.0F) {
            float xD = (xNMask | 1) + x1;
            float yD = (yNMask | 1) + y1;
            float zD = z1;
            int hD = v3Hash(seed2, xrbp + (xNMask & -6626342789952991010L), yrbp + (yNMask & -5541215012557672598L), zrbp + 6614699811220273867L);
            float vD = aD * aD * (aD * aD) * grad(hD, xD, yD, zD);
            int viD = hD >> 8 & 1020;
            vx += vD * OUTPUT_VEC3[viD | 0];
            vy += vD * OUTPUT_VEC3[viD | 1];
            vz += vD * OUTPUT_VEC3[viD | 2];
         }
      }

      output.set(vx, vy, vz);
   }

   public static double noise4Grad_ImproveXYZ_ImproveXZ(long seed, double x, double y, double z, double w, Vector4f gradient) {
      double xz = x + z;
      double s2 = xz * -0.211324865405187;
      double yy = y * 0.2886751345948129;
      double ww = w * 1.118033988749894;
      double xr = x + (yy + ww + s2);
      double zr = z + (yy + ww + s2);
      double yr = xz * -0.577350269189626 + (yy + ww);
      double wr = y * -0.866025403784439 + ww;
      double value = noise4Grad_UnskewedBase(seed, xr, yr, zr, wr, gradient);
      float xzd = gradient.x() + gradient.z();
      float xyzd = xzd + gradient.y();
      float s2d = xzd * -0.21132487F;
      float yyd = gradient.y() * 0.57735026F;
      gradient.set(gradient.x() + (s2d - yyd), xyzd * 0.28867513F + gradient.w() * -0.8660254F, gradient.z() + (s2d - yyd), (xyzd + gradient.w()) * 0.5F);
      return value;
   }

   private static float noise4Grad_UnskewedBase(long seed, double xs, double ys, double zs, double ws, Vector4f gradient) {
      int xsb = fastFloor(xs);
      int ysb = fastFloor(ys);
      int zsb = fastFloor(zs);
      int wsb = fastFloor(ws);
      float xsi = (float)(xs - xsb);
      float ysi = (float)(ys - ysb);
      float zsi = (float)(zs - zsb);
      float wsi = (float)(ws - wsb);
      float ssi = (xsi + ysi + zsi + wsi) * -0.1381966F;
      float xi = xsi + ssi;
      float yi = ysi + ssi;
      float zi = zsi + ssi;
      float wi = wsi + ssi;
      long xsvp = xsb * 5910200641878280303L;
      long ysvp = ysb * 6452764530575939509L;
      long zsvp = zsb * 6614699811220273867L;
      long wsvp = wsb * 6254464313819354443L;
      int index = (fastFloor(xs * 4.0) & 3) << 0 | (fastFloor(ys * 4.0) & 3) << 2 | (fastFloor(zs * 4.0) & 3) << 4 | (fastFloor(ws * 4.0) & 3) << 6;
      float value = 0.0F;
      float dVdx = 0.0F;
      float dVdy = 0.0F;
      float dVdz = 0.0F;
      float dVdw = 0.0F;
      int secondaryIndexStartAndStop = LOOKUP_4D_A[index];
      int secondaryIndexStart = secondaryIndexStartAndStop & 65535;
      int secondaryIndexStop = secondaryIndexStartAndStop >> 16;

      for (int i = secondaryIndexStart; i < secondaryIndexStop; i++) {
         OpenSimplex2S.LatticeVertex4D c = LOOKUP_4D_B[i];
         float dx = xi + c.dx;
         float dy = yi + c.dy;
         float dz = zi + c.dz;
         float dw = wi + c.dw;
         float na = dx * dx + dy * dy + (dz * dz + dw * dw);
         if (na < 0.8F) {
            na -= 0.8F;
            float aa = na * na;
            float naaa = aa * na;
            float aaaa = aa * aa;
            int gi = v4GradIndex(seed, xsvp + c.xsvp, ysvp + c.ysvp, zsvp + c.zsvp, wsvp + c.wsvp);
            float gx = GRADIENTS_4D[gi | 0];
            float gy = GRADIENTS_4D[gi | 1];
            float gz = GRADIENTS_4D[gi | 2];
            float gw = GRADIENTS_4D[gi | 3];
            float rampValue = gx * dx + gy * dy + gz * dz + gw * dw;
            value += aa * aa * rampValue;
            dVdx += gx * aaaa + 8.0F * rampValue * naaa * dx;
            dVdy += gy * aaaa + 8.0F * rampValue * naaa * dy;
            dVdz += gz * aaaa + 8.0F * rampValue * naaa * dz;
            dVdw += gw * aaaa + 8.0F * rampValue * naaa * dw;
         }
      }

      gradient.set(dVdx, dVdy, dVdz, dVdw);
      return value;
   }

   private static float grad(long seed, long xrvp, long yrvp, long zrvp, float dx, float dy, float dz) {
      long hash = seed ^ xrvp ^ yrvp ^ zrvp;
      hash *= 6026932503003350773L;
      hash ^= hash >> 54;
      return grad((int)hash, dx, dy, dz);
   }

   private static float grad(int hash, float dx, float dy, float dz) {
      int gi = hash & 1020;
      return GRADIENTS_3D[gi | 0] * dx + GRADIENTS_3D[gi | 1] * dy + GRADIENTS_3D[gi | 2] * dz;
   }

   private static int v3Hash(long seed, long xrvp, long yrvp, long zrvp) {
      long hash = seed ^ xrvp ^ yrvp ^ zrvp;
      hash *= 6026932503003350773L;
      hash ^= hash >> 310;
      return (int)hash;
   }

   private static int v4GradIndex(long seed, long xsvp, long ysvp, long zsvp, long wsvp) {
      long hash = seed ^ xsvp ^ ysvp ^ zsvp ^ wsvp;
      hash *= 6026932503003350773L;
      hash ^= hash >> 53;
      return (int)hash & 2044;
   }

   private static int fastFloor(double x) {
      int xi = (int)x;
      return x < xi ? xi - 1 : xi;
   }

   static {
      float[] grad3 = new float[]{
         2.2247448F,
         2.2247448F,
         -1.0F,
         0.0F,
         2.2247448F,
         2.2247448F,
         1.0F,
         0.0F,
         3.0862665F,
         1.1721513F,
         0.0F,
         0.0F,
         1.1721513F,
         3.0862665F,
         0.0F,
         0.0F,
         -2.2247448F,
         2.2247448F,
         -1.0F,
         0.0F,
         -2.2247448F,
         2.2247448F,
         1.0F,
         0.0F,
         -1.1721513F,
         3.0862665F,
         0.0F,
         0.0F,
         -3.0862665F,
         1.1721513F,
         0.0F,
         0.0F,
         -1.0F,
         -2.2247448F,
         -2.2247448F,
         0.0F,
         1.0F,
         -2.2247448F,
         -2.2247448F,
         0.0F,
         0.0F,
         -3.0862665F,
         -1.1721513F,
         0.0F,
         0.0F,
         -1.1721513F,
         -3.0862665F,
         0.0F,
         -1.0F,
         -2.2247448F,
         2.2247448F,
         0.0F,
         1.0F,
         -2.2247448F,
         2.2247448F,
         0.0F,
         0.0F,
         -1.1721513F,
         3.0862665F,
         0.0F,
         0.0F,
         -3.0862665F,
         1.1721513F,
         0.0F,
         -2.2247448F,
         -2.2247448F,
         -1.0F,
         0.0F,
         -2.2247448F,
         -2.2247448F,
         1.0F,
         0.0F,
         -3.0862665F,
         -1.1721513F,
         0.0F,
         0.0F,
         -1.1721513F,
         -3.0862665F,
         0.0F,
         0.0F,
         -2.2247448F,
         -1.0F,
         -2.2247448F,
         0.0F,
         -2.2247448F,
         1.0F,
         -2.2247448F,
         0.0F,
         -1.1721513F,
         0.0F,
         -3.0862665F,
         0.0F,
         -3.0862665F,
         0.0F,
         -1.1721513F,
         0.0F,
         -2.2247448F,
         -1.0F,
         2.2247448F,
         0.0F,
         -2.2247448F,
         1.0F,
         2.2247448F,
         0.0F,
         -3.0862665F,
         0.0F,
         1.1721513F,
         0.0F,
         -1.1721513F,
         0.0F,
         3.0862665F,
         0.0F,
         -1.0F,
         2.2247448F,
         -2.2247448F,
         0.0F,
         1.0F,
         2.2247448F,
         -2.2247448F,
         0.0F,
         0.0F,
         1.1721513F,
         -3.0862665F,
         0.0F,
         0.0F,
         3.0862665F,
         -1.1721513F,
         0.0F,
         -1.0F,
         2.2247448F,
         2.2247448F,
         0.0F,
         1.0F,
         2.2247448F,
         2.2247448F,
         0.0F,
         0.0F,
         3.0862665F,
         1.1721513F,
         0.0F,
         0.0F,
         1.1721513F,
         3.0862665F,
         0.0F,
         2.2247448F,
         -2.2247448F,
         -1.0F,
         0.0F,
         2.2247448F,
         -2.2247448F,
         1.0F,
         0.0F,
         1.1721513F,
         -3.0862665F,
         0.0F,
         0.0F,
         3.0862665F,
         -1.1721513F,
         0.0F,
         0.0F,
         2.2247448F,
         -1.0F,
         -2.2247448F,
         0.0F,
         2.2247448F,
         1.0F,
         -2.2247448F,
         0.0F,
         3.0862665F,
         0.0F,
         -1.1721513F,
         0.0F,
         1.1721513F,
         0.0F,
         -3.0862665F,
         0.0F,
         2.2247448F,
         -1.0F,
         2.2247448F,
         0.0F,
         2.2247448F,
         1.0F,
         2.2247448F,
         0.0F,
         1.1721513F,
         0.0F,
         3.0862665F,
         0.0F,
         3.0862665F,
         0.0F,
         1.1721513F,
         0.0F
      };

      for (int i = 0; i < grad3.length; i++) {
         grad3[i] = (float)(grad3[i] / 0.2781926117527186);
      }

      int i = 0;

      for (int j = 0; i < GRADIENTS_3D.length; j++) {
         if (j == grad3.length) {
            j = 0;
         }

         GRADIENTS_3D[i] = grad3[j];
         i++;
      }

      GRADIENTS_4D = new float[2048];
      float[] grad4 = new float[]{
         -0.6740059F,
         -0.32398477F,
         -0.32398477F,
         0.5794685F,
         -0.7504884F,
         -0.40046722F,
         0.15296486F,
         0.502986F,
         -0.7504884F,
         0.15296486F,
         -0.40046722F,
         0.502986F,
         -0.8828162F,
         0.08164729F,
         0.08164729F,
         0.4553054F,
         -0.4553054F,
         -0.08164729F,
         -0.08164729F,
         0.8828162F,
         -0.502986F,
         -0.15296486F,
         0.40046722F,
         0.7504884F,
         -0.502986F,
         0.40046722F,
         -0.15296486F,
         0.7504884F,
         -0.5794685F,
         0.32398477F,
         0.32398477F,
         0.6740059F,
         -0.6740059F,
         -0.32398477F,
         0.5794685F,
         -0.32398477F,
         -0.7504884F,
         -0.40046722F,
         0.502986F,
         0.15296486F,
         -0.7504884F,
         0.15296486F,
         0.502986F,
         -0.40046722F,
         -0.8828162F,
         0.08164729F,
         0.4553054F,
         0.08164729F,
         -0.4553054F,
         -0.08164729F,
         0.8828162F,
         -0.08164729F,
         -0.502986F,
         -0.15296486F,
         0.7504884F,
         0.40046722F,
         -0.502986F,
         0.40046722F,
         0.7504884F,
         -0.15296486F,
         -0.5794685F,
         0.32398477F,
         0.6740059F,
         0.32398477F,
         -0.6740059F,
         0.5794685F,
         -0.32398477F,
         -0.32398477F,
         -0.7504884F,
         0.502986F,
         -0.40046722F,
         0.15296486F,
         -0.7504884F,
         0.502986F,
         0.15296486F,
         -0.40046722F,
         -0.8828162F,
         0.4553054F,
         0.08164729F,
         0.08164729F,
         -0.4553054F,
         0.8828162F,
         -0.08164729F,
         -0.08164729F,
         -0.502986F,
         0.7504884F,
         -0.15296486F,
         0.40046722F,
         -0.502986F,
         0.7504884F,
         0.40046722F,
         -0.15296486F,
         -0.5794685F,
         0.6740059F,
         0.32398477F,
         0.32398477F,
         0.5794685F,
         -0.6740059F,
         -0.32398477F,
         -0.32398477F,
         0.502986F,
         -0.7504884F,
         -0.40046722F,
         0.15296486F,
         0.502986F,
         -0.7504884F,
         0.15296486F,
         -0.40046722F,
         0.4553054F,
         -0.8828162F,
         0.08164729F,
         0.08164729F,
         0.8828162F,
         -0.4553054F,
         -0.08164729F,
         -0.08164729F,
         0.7504884F,
         -0.502986F,
         -0.15296486F,
         0.40046722F,
         0.7504884F,
         -0.502986F,
         0.40046722F,
         -0.15296486F,
         0.6740059F,
         -0.5794685F,
         0.32398477F,
         0.32398477F,
         -0.753341F,
         -0.3796829F,
         -0.3796829F,
         -0.3796829F,
         -0.78216845F,
         -0.43214726F,
         -0.43214726F,
         0.121284805F,
         -0.78216845F,
         -0.43214726F,
         0.121284805F,
         -0.43214726F,
         -0.78216845F,
         0.121284805F,
         -0.43214726F,
         -0.43214726F,
         -0.85865086F,
         -0.5086297F,
         0.04480237F,
         0.04480237F,
         -0.85865086F,
         0.04480237F,
         -0.5086297F,
         0.04480237F,
         -0.85865086F,
         0.04480237F,
         0.04480237F,
         -0.5086297F,
         -0.9982829F,
         -0.033819415F,
         -0.033819415F,
         -0.033819415F,
         -0.3796829F,
         -0.753341F,
         -0.3796829F,
         -0.3796829F,
         -0.43214726F,
         -0.78216845F,
         -0.43214726F,
         0.121284805F,
         -0.43214726F,
         -0.78216845F,
         0.121284805F,
         -0.43214726F,
         0.121284805F,
         -0.78216845F,
         -0.43214726F,
         -0.43214726F,
         -0.5086297F,
         -0.85865086F,
         0.04480237F,
         0.04480237F,
         0.04480237F,
         -0.85865086F,
         -0.5086297F,
         0.04480237F,
         0.04480237F,
         -0.85865086F,
         0.04480237F,
         -0.5086297F,
         -0.033819415F,
         -0.9982829F,
         -0.033819415F,
         -0.033819415F,
         -0.3796829F,
         -0.3796829F,
         -0.753341F,
         -0.3796829F,
         -0.43214726F,
         -0.43214726F,
         -0.78216845F,
         0.121284805F,
         -0.43214726F,
         0.121284805F,
         -0.78216845F,
         -0.43214726F,
         0.121284805F,
         -0.43214726F,
         -0.78216845F,
         -0.43214726F,
         -0.5086297F,
         0.04480237F,
         -0.85865086F,
         0.04480237F,
         0.04480237F,
         -0.5086297F,
         -0.85865086F,
         0.04480237F,
         0.04480237F,
         0.04480237F,
         -0.85865086F,
         -0.5086297F,
         -0.033819415F,
         -0.033819415F,
         -0.9982829F,
         -0.033819415F,
         -0.3796829F,
         -0.3796829F,
         -0.3796829F,
         -0.753341F,
         -0.43214726F,
         -0.43214726F,
         0.121284805F,
         -0.78216845F,
         -0.43214726F,
         0.121284805F,
         -0.43214726F,
         -0.78216845F,
         0.121284805F,
         -0.43214726F,
         -0.43214726F,
         -0.78216845F,
         -0.5086297F,
         0.04480237F,
         0.04480237F,
         -0.85865086F,
         0.04480237F,
         -0.5086297F,
         0.04480237F,
         -0.85865086F,
         0.04480237F,
         0.04480237F,
         -0.5086297F,
         -0.85865086F,
         -0.033819415F,
         -0.033819415F,
         -0.033819415F,
         -0.9982829F,
         -0.32398477F,
         -0.6740059F,
         -0.32398477F,
         0.5794685F,
         -0.40046722F,
         -0.7504884F,
         0.15296486F,
         0.502986F,
         0.15296486F,
         -0.7504884F,
         -0.40046722F,
         0.502986F,
         0.08164729F,
         -0.8828162F,
         0.08164729F,
         0.4553054F,
         -0.08164729F,
         -0.4553054F,
         -0.08164729F,
         0.8828162F,
         -0.15296486F,
         -0.502986F,
         0.40046722F,
         0.7504884F,
         0.40046722F,
         -0.502986F,
         -0.15296486F,
         0.7504884F,
         0.32398477F,
         -0.5794685F,
         0.32398477F,
         0.6740059F,
         -0.32398477F,
         -0.32398477F,
         -0.6740059F,
         0.5794685F,
         -0.40046722F,
         0.15296486F,
         -0.7504884F,
         0.502986F,
         0.15296486F,
         -0.40046722F,
         -0.7504884F,
         0.502986F,
         0.08164729F,
         0.08164729F,
         -0.8828162F,
         0.4553054F,
         -0.08164729F,
         -0.08164729F,
         -0.4553054F,
         0.8828162F,
         -0.15296486F,
         0.40046722F,
         -0.502986F,
         0.7504884F,
         0.40046722F,
         -0.15296486F,
         -0.502986F,
         0.7504884F,
         0.32398477F,
         0.32398477F,
         -0.5794685F,
         0.6740059F,
         -0.32398477F,
         -0.6740059F,
         0.5794685F,
         -0.32398477F,
         -0.40046722F,
         -0.7504884F,
         0.502986F,
         0.15296486F,
         0.15296486F,
         -0.7504884F,
         0.502986F,
         -0.40046722F,
         0.08164729F,
         -0.8828162F,
         0.4553054F,
         0.08164729F,
         -0.08164729F,
         -0.4553054F,
         0.8828162F,
         -0.08164729F,
         -0.15296486F,
         -0.502986F,
         0.7504884F,
         0.40046722F,
         0.40046722F,
         -0.502986F,
         0.7504884F,
         -0.15296486F,
         0.32398477F,
         -0.5794685F,
         0.6740059F,
         0.32398477F,
         -0.32398477F,
         -0.32398477F,
         0.5794685F,
         -0.6740059F,
         -0.40046722F,
         0.15296486F,
         0.502986F,
         -0.7504884F,
         0.15296486F,
         -0.40046722F,
         0.502986F,
         -0.7504884F,
         0.08164729F,
         0.08164729F,
         0.4553054F,
         -0.8828162F,
         -0.08164729F,
         -0.08164729F,
         0.8828162F,
         -0.4553054F,
         -0.15296486F,
         0.40046722F,
         0.7504884F,
         -0.502986F,
         0.40046722F,
         -0.15296486F,
         0.7504884F,
         -0.502986F,
         0.32398477F,
         0.32398477F,
         0.6740059F,
         -0.5794685F,
         -0.32398477F,
         0.5794685F,
         -0.6740059F,
         -0.32398477F,
         -0.40046722F,
         0.502986F,
         -0.7504884F,
         0.15296486F,
         0.15296486F,
         0.502986F,
         -0.7504884F,
         -0.40046722F,
         0.08164729F,
         0.4553054F,
         -0.8828162F,
         0.08164729F,
         -0.08164729F,
         0.8828162F,
         -0.4553054F,
         -0.08164729F,
         -0.15296486F,
         0.7504884F,
         -0.502986F,
         0.40046722F,
         0.40046722F,
         0.7504884F,
         -0.502986F,
         -0.15296486F,
         0.32398477F,
         0.6740059F,
         -0.5794685F,
         0.32398477F,
         -0.32398477F,
         0.5794685F,
         -0.32398477F,
         -0.6740059F,
         -0.40046722F,
         0.502986F,
         0.15296486F,
         -0.7504884F,
         0.15296486F,
         0.502986F,
         -0.40046722F,
         -0.7504884F,
         0.08164729F,
         0.4553054F,
         0.08164729F,
         -0.8828162F,
         -0.08164729F,
         0.8828162F,
         -0.08164729F,
         -0.4553054F,
         -0.15296486F,
         0.7504884F,
         0.40046722F,
         -0.502986F,
         0.40046722F,
         0.7504884F,
         -0.15296486F,
         -0.502986F,
         0.32398477F,
         0.6740059F,
         0.32398477F,
         -0.5794685F,
         0.5794685F,
         -0.32398477F,
         -0.6740059F,
         -0.32398477F,
         0.502986F,
         -0.40046722F,
         -0.7504884F,
         0.15296486F,
         0.502986F,
         0.15296486F,
         -0.7504884F,
         -0.40046722F,
         0.4553054F,
         0.08164729F,
         -0.8828162F,
         0.08164729F,
         0.8828162F,
         -0.08164729F,
         -0.4553054F,
         -0.08164729F,
         0.7504884F,
         -0.15296486F,
         -0.502986F,
         0.40046722F,
         0.7504884F,
         0.40046722F,
         -0.502986F,
         -0.15296486F,
         0.6740059F,
         0.32398477F,
         -0.5794685F,
         0.32398477F,
         0.5794685F,
         -0.32398477F,
         -0.32398477F,
         -0.6740059F,
         0.502986F,
         -0.40046722F,
         0.15296486F,
         -0.7504884F,
         0.502986F,
         0.15296486F,
         -0.40046722F,
         -0.7504884F,
         0.4553054F,
         0.08164729F,
         0.08164729F,
         -0.8828162F,
         0.8828162F,
         -0.08164729F,
         -0.08164729F,
         -0.4553054F,
         0.7504884F,
         -0.15296486F,
         0.40046722F,
         -0.502986F,
         0.7504884F,
         0.40046722F,
         -0.15296486F,
         -0.502986F,
         0.6740059F,
         0.32398477F,
         0.32398477F,
         -0.5794685F,
         0.033819415F,
         0.033819415F,
         0.033819415F,
         0.9982829F,
         -0.04480237F,
         -0.04480237F,
         0.5086297F,
         0.85865086F,
         -0.04480237F,
         0.5086297F,
         -0.04480237F,
         0.85865086F,
         -0.121284805F,
         0.43214726F,
         0.43214726F,
         0.78216845F,
         0.5086297F,
         -0.04480237F,
         -0.04480237F,
         0.85865086F,
         0.43214726F,
         -0.121284805F,
         0.43214726F,
         0.78216845F,
         0.43214726F,
         0.43214726F,
         -0.121284805F,
         0.78216845F,
         0.3796829F,
         0.3796829F,
         0.3796829F,
         0.753341F,
         0.033819415F,
         0.033819415F,
         0.9982829F,
         0.033819415F,
         -0.04480237F,
         0.04480237F,
         0.85865086F,
         0.5086297F,
         -0.04480237F,
         0.5086297F,
         0.85865086F,
         -0.04480237F,
         -0.121284805F,
         0.43214726F,
         0.78216845F,
         0.43214726F,
         0.5086297F,
         -0.04480237F,
         0.85865086F,
         -0.04480237F,
         0.43214726F,
         -0.121284805F,
         0.78216845F,
         0.43214726F,
         0.43214726F,
         0.43214726F,
         0.78216845F,
         -0.121284805F,
         0.3796829F,
         0.3796829F,
         0.753341F,
         0.3796829F,
         0.033819415F,
         0.9982829F,
         0.033819415F,
         0.033819415F,
         -0.04480237F,
         0.85865086F,
         -0.04480237F,
         0.5086297F,
         -0.04480237F,
         0.85865086F,
         0.5086297F,
         -0.04480237F,
         -0.121284805F,
         0.78216845F,
         0.43214726F,
         0.43214726F,
         0.5086297F,
         0.85865086F,
         -0.04480237F,
         -0.04480237F,
         0.43214726F,
         0.78216845F,
         -0.121284805F,
         0.43214726F,
         0.43214726F,
         0.78216845F,
         0.43214726F,
         -0.121284805F,
         0.3796829F,
         0.753341F,
         0.3796829F,
         0.3796829F,
         0.9982829F,
         0.033819415F,
         0.033819415F,
         0.033819415F,
         0.85865086F,
         -0.04480237F,
         -0.04480237F,
         0.5086297F,
         0.85865086F,
         -0.04480237F,
         0.5086297F,
         -0.04480237F,
         0.78216845F,
         -0.121284805F,
         0.43214726F,
         0.43214726F,
         0.85865086F,
         0.5086297F,
         -0.04480237F,
         -0.04480237F,
         0.78216845F,
         0.43214726F,
         -0.121284805F,
         0.43214726F,
         0.78216845F,
         0.43214726F,
         0.43214726F,
         -0.121284805F,
         0.753341F,
         0.3796829F,
         0.3796829F,
         0.3796829F
      };

      for (int ix = 0; ix < grad4.length; ix++) {
         grad4[ix] = (float)(grad4[ix] / 0.11127401889945551);
      }

      int ix = 0;

      for (int j = 0; ix < GRADIENTS_4D.length; j++) {
         if (j == grad4.length) {
            j = 0;
         }

         GRADIENTS_4D[ix] = grad4[j];
         ix++;
      }

      int[][] lookup4DVertexCodes = new int[][]{
         {21, 69, 81, 84, 85, 86, 89, 90, 101, 102, 105, 106, 149, 150, 153, 154, 165, 166, 169, 170},
         {21, 69, 81, 85, 86, 89, 90, 101, 102, 106, 149, 150, 154, 166, 170},
         {1, 5, 17, 21, 65, 69, 81, 85, 86, 90, 102, 106, 150, 154, 166, 170},
         {1, 21, 22, 69, 70, 81, 82, 85, 86, 90, 102, 106, 150, 154, 166, 170, 171},
         {21, 69, 84, 85, 86, 89, 90, 101, 105, 106, 149, 153, 154, 169, 170},
         {5, 21, 69, 85, 86, 89, 90, 101, 102, 105, 106, 149, 150, 153, 154, 170},
         {5, 21, 69, 85, 86, 89, 90, 102, 106, 150, 154, 170},
         {5, 21, 22, 69, 70, 85, 86, 89, 90, 102, 106, 150, 154, 170, 171},
         {4, 5, 20, 21, 68, 69, 84, 85, 89, 90, 105, 106, 153, 154, 169, 170},
         {5, 21, 69, 85, 86, 89, 90, 105, 106, 153, 154, 170},
         {5, 21, 69, 85, 86, 89, 90, 106, 154, 170},
         {5, 21, 22, 69, 70, 85, 86, 89, 90, 91, 106, 154, 170, 171},
         {4, 21, 25, 69, 73, 84, 85, 88, 89, 90, 105, 106, 153, 154, 169, 170, 174},
         {5, 21, 25, 69, 73, 85, 86, 89, 90, 105, 106, 153, 154, 170, 174},
         {5, 21, 25, 69, 73, 85, 86, 89, 90, 94, 106, 154, 170, 174},
         {5, 21, 26, 69, 74, 85, 86, 89, 90, 91, 94, 106, 154, 170, 171, 174, 175},
         {21, 81, 84, 85, 86, 89, 101, 102, 105, 106, 149, 165, 166, 169, 170},
         {17, 21, 81, 85, 86, 89, 90, 101, 102, 105, 106, 149, 150, 165, 166, 170},
         {17, 21, 81, 85, 86, 90, 101, 102, 106, 150, 166, 170},
         {17, 21, 22, 81, 82, 85, 86, 90, 101, 102, 106, 150, 166, 170, 171},
         {20, 21, 84, 85, 86, 89, 90, 101, 102, 105, 106, 149, 153, 165, 169, 170},
         {21, 85, 86, 89, 90, 101, 102, 105, 106, 149, 154, 166, 169, 170},
         {21, 85, 86, 89, 90, 101, 102, 105, 106, 150, 154, 166, 170, 171},
         {21, 22, 85, 86, 90, 102, 106, 107, 150, 154, 166, 170, 171},
         {20, 21, 84, 85, 89, 90, 101, 105, 106, 153, 169, 170},
         {21, 85, 86, 89, 90, 101, 102, 105, 106, 153, 154, 169, 170, 174},
         {21, 85, 86, 89, 90, 101, 102, 105, 106, 154, 170},
         {21, 22, 85, 86, 89, 90, 102, 106, 107, 154, 170, 171},
         {20, 21, 25, 84, 85, 88, 89, 90, 101, 105, 106, 153, 169, 170, 174},
         {21, 25, 85, 89, 90, 105, 106, 110, 153, 154, 169, 170, 174},
         {21, 25, 85, 86, 89, 90, 105, 106, 110, 154, 170, 174},
         {21, 26, 85, 86, 89, 90, 106, 107, 110, 154, 170, 171, 174, 175},
         {16, 17, 20, 21, 80, 81, 84, 85, 101, 102, 105, 106, 165, 166, 169, 170},
         {17, 21, 81, 85, 86, 101, 102, 105, 106, 165, 166, 170},
         {17, 21, 81, 85, 86, 101, 102, 106, 166, 170},
         {17, 21, 22, 81, 82, 85, 86, 101, 102, 103, 106, 166, 170, 171},
         {20, 21, 84, 85, 89, 101, 102, 105, 106, 165, 169, 170},
         {21, 85, 86, 89, 90, 101, 102, 105, 106, 165, 166, 169, 170, 186},
         {21, 85, 86, 89, 90, 101, 102, 105, 106, 166, 170},
         {21, 22, 85, 86, 90, 101, 102, 106, 107, 166, 170, 171},
         {20, 21, 84, 85, 89, 101, 105, 106, 169, 170},
         {21, 85, 86, 89, 90, 101, 102, 105, 106, 169, 170},
         {21, 85, 86, 89, 90, 101, 102, 105, 106, 170},
         {21, 22, 85, 86, 89, 90, 101, 102, 105, 106, 107, 170, 171},
         {20, 21, 25, 84, 85, 88, 89, 101, 105, 106, 109, 169, 170, 174},
         {21, 25, 85, 89, 90, 101, 105, 106, 110, 169, 170, 174},
         {21, 25, 85, 86, 89, 90, 101, 102, 105, 106, 110, 170, 174},
         {21, 85, 86, 89, 90, 102, 105, 106, 107, 110, 154, 170, 171, 174, 175},
         {16, 21, 37, 81, 84, 85, 97, 100, 101, 102, 105, 106, 165, 166, 169, 170, 186},
         {17, 21, 37, 81, 85, 86, 97, 101, 102, 105, 106, 165, 166, 170, 186},
         {17, 21, 37, 81, 85, 86, 97, 101, 102, 106, 118, 166, 170, 186},
         {17, 21, 38, 81, 85, 86, 98, 101, 102, 103, 106, 118, 166, 170, 171, 186, 187},
         {20, 21, 37, 84, 85, 89, 100, 101, 102, 105, 106, 165, 169, 170, 186},
         {21, 37, 85, 101, 102, 105, 106, 122, 165, 166, 169, 170, 186},
         {21, 37, 85, 86, 101, 102, 105, 106, 122, 166, 170, 186},
         {21, 38, 85, 86, 101, 102, 106, 107, 122, 166, 170, 171, 186, 187},
         {20, 21, 37, 84, 85, 89, 100, 101, 105, 106, 121, 169, 170, 186},
         {21, 37, 85, 89, 101, 102, 105, 106, 122, 169, 170, 186},
         {21, 37, 85, 86, 89, 90, 101, 102, 105, 106, 122, 170, 186},
         {21, 85, 86, 90, 101, 102, 105, 106, 107, 122, 166, 170, 171, 186, 187},
         {20, 21, 41, 84, 85, 89, 101, 104, 105, 106, 109, 121, 169, 170, 174, 186, 190},
         {21, 41, 85, 89, 101, 105, 106, 110, 122, 169, 170, 174, 186, 190},
         {21, 85, 89, 90, 101, 102, 105, 106, 110, 122, 169, 170, 174, 186, 190},
         {21, 85, 86, 89, 90, 101, 102, 105, 106, 107, 110, 122, 170, 171, 174, 186, 191},
         {69, 81, 84, 85, 86, 89, 101, 149, 150, 153, 154, 165, 166, 169, 170},
         {65, 69, 81, 85, 86, 89, 90, 101, 102, 149, 150, 153, 154, 165, 166, 170},
         {65, 69, 81, 85, 86, 90, 102, 149, 150, 154, 166, 170},
         {65, 69, 70, 81, 82, 85, 86, 90, 102, 149, 150, 154, 166, 170, 171},
         {68, 69, 84, 85, 86, 89, 90, 101, 105, 149, 150, 153, 154, 165, 169, 170},
         {69, 85, 86, 89, 90, 101, 106, 149, 150, 153, 154, 166, 169, 170},
         {69, 85, 86, 89, 90, 102, 106, 149, 150, 153, 154, 166, 170, 171},
         {69, 70, 85, 86, 90, 102, 106, 150, 154, 155, 166, 170, 171},
         {68, 69, 84, 85, 89, 90, 105, 149, 153, 154, 169, 170},
         {69, 85, 86, 89, 90, 105, 106, 149, 150, 153, 154, 169, 170, 174},
         {69, 85, 86, 89, 90, 106, 149, 150, 153, 154, 170},
         {69, 70, 85, 86, 89, 90, 106, 150, 154, 155, 170, 171},
         {68, 69, 73, 84, 85, 88, 89, 90, 105, 149, 153, 154, 169, 170, 174},
         {69, 73, 85, 89, 90, 105, 106, 153, 154, 158, 169, 170, 174},
         {69, 73, 85, 86, 89, 90, 106, 153, 154, 158, 170, 174},
         {69, 74, 85, 86, 89, 90, 106, 154, 155, 158, 170, 171, 174, 175},
         {80, 81, 84, 85, 86, 89, 101, 102, 105, 149, 150, 153, 165, 166, 169, 170},
         {81, 85, 86, 89, 101, 102, 106, 149, 150, 154, 165, 166, 169, 170},
         {81, 85, 86, 90, 101, 102, 106, 149, 150, 154, 165, 166, 170, 171},
         {81, 82, 85, 86, 90, 102, 106, 150, 154, 166, 167, 170, 171},
         {84, 85, 86, 89, 101, 105, 106, 149, 153, 154, 165, 166, 169, 170},
         {85, 86, 89, 90, 101, 102, 105, 106, 149, 150, 153, 154, 165, 166, 169, 170},
         {21, 69, 81, 85, 86, 89, 90, 101, 102, 106, 149, 150, 154, 166, 170, 171},
         {85, 86, 90, 102, 106, 150, 154, 166, 170, 171},
         {84, 85, 89, 90, 101, 105, 106, 149, 153, 154, 165, 169, 170, 174},
         {21, 69, 84, 85, 86, 89, 90, 101, 105, 106, 149, 153, 154, 169, 170, 174},
         {21, 69, 85, 86, 89, 90, 101, 102, 105, 106, 149, 150, 153, 154, 166, 169, 170, 171, 174},
         {85, 86, 89, 90, 102, 106, 150, 154, 166, 170, 171},
         {84, 85, 88, 89, 90, 105, 106, 153, 154, 169, 170, 173, 174},
         {85, 89, 90, 105, 106, 153, 154, 169, 170, 174},
         {85, 86, 89, 90, 105, 106, 153, 154, 169, 170, 174},
         {85, 86, 89, 90, 106, 154, 170, 171, 174, 175},
         {80, 81, 84, 85, 101, 102, 105, 149, 165, 166, 169, 170},
         {81, 85, 86, 101, 102, 105, 106, 149, 150, 165, 166, 169, 170, 186},
         {81, 85, 86, 101, 102, 106, 149, 150, 165, 166, 170},
         {81, 82, 85, 86, 101, 102, 106, 150, 166, 167, 170, 171},
         {84, 85, 89, 101, 102, 105, 106, 149, 153, 165, 166, 169, 170, 186},
         {21, 81, 84, 85, 86, 89, 101, 102, 105, 106, 149, 165, 166, 169, 170, 186},
         {21, 81, 85, 86, 89, 90, 101, 102, 105, 106, 149, 150, 154, 165, 166, 169, 170, 171, 186},
         {85, 86, 90, 101, 102, 106, 150, 154, 166, 170, 171},
         {84, 85, 89, 101, 105, 106, 149, 153, 165, 169, 170},
         {21, 84, 85, 86, 89, 90, 101, 102, 105, 106, 149, 153, 154, 165, 166, 169, 170, 174, 186},
         {21, 85, 86, 89, 90, 101, 102, 105, 106, 154, 166, 169, 170},
         {21, 85, 86, 89, 90, 101, 102, 105, 106, 150, 154, 166, 170, 171},
         {84, 85, 88, 89, 101, 105, 106, 153, 169, 170, 173, 174},
         {85, 89, 90, 101, 105, 106, 153, 154, 169, 170, 174},
         {21, 85, 86, 89, 90, 101, 102, 105, 106, 153, 154, 169, 170, 174},
         {21, 85, 86, 89, 90, 102, 105, 106, 154, 170, 171, 174, 175},
         {80, 81, 84, 85, 97, 100, 101, 102, 105, 149, 165, 166, 169, 170, 186},
         {81, 85, 97, 101, 102, 105, 106, 165, 166, 169, 170, 182, 186},
         {81, 85, 86, 97, 101, 102, 106, 165, 166, 170, 182, 186},
         {81, 85, 86, 98, 101, 102, 106, 166, 167, 170, 171, 182, 186, 187},
         {84, 85, 100, 101, 102, 105, 106, 165, 166, 169, 170, 185, 186},
         {85, 101, 102, 105, 106, 165, 166, 169, 170, 186},
         {85, 86, 101, 102, 105, 106, 165, 166, 169, 170, 186},
         {85, 86, 101, 102, 106, 166, 170, 171, 186, 187},
         {84, 85, 89, 100, 101, 105, 106, 165, 169, 170, 185, 186},
         {85, 89, 101, 102, 105, 106, 165, 166, 169, 170, 186},
         {21, 85, 86, 89, 90, 101, 102, 105, 106, 165, 166, 169, 170, 186},
         {21, 85, 86, 90, 101, 102, 105, 106, 166, 170, 171, 186, 187},
         {84, 85, 89, 101, 104, 105, 106, 169, 170, 173, 174, 185, 186, 190},
         {85, 89, 101, 105, 106, 169, 170, 174, 186, 190},
         {21, 85, 89, 90, 101, 102, 105, 106, 169, 170, 174, 186, 190},
         {85, 86, 89, 90, 101, 102, 105, 106, 170, 171, 174, 186, 191},
         {64, 65, 68, 69, 80, 81, 84, 85, 149, 150, 153, 154, 165, 166, 169, 170},
         {65, 69, 81, 85, 86, 149, 150, 153, 154, 165, 166, 170},
         {65, 69, 81, 85, 86, 149, 150, 154, 166, 170},
         {65, 69, 70, 81, 82, 85, 86, 149, 150, 151, 154, 166, 170, 171},
         {68, 69, 84, 85, 89, 149, 150, 153, 154, 165, 169, 170},
         {69, 85, 86, 89, 90, 149, 150, 153, 154, 165, 166, 169, 170, 234},
         {69, 85, 86, 89, 90, 149, 150, 153, 154, 166, 170},
         {69, 70, 85, 86, 90, 149, 150, 154, 155, 166, 170, 171},
         {68, 69, 84, 85, 89, 149, 153, 154, 169, 170},
         {69, 85, 86, 89, 90, 149, 150, 153, 154, 169, 170},
         {69, 85, 86, 89, 90, 149, 150, 153, 154, 170},
         {69, 70, 85, 86, 89, 90, 149, 150, 153, 154, 155, 170, 171},
         {68, 69, 73, 84, 85, 88, 89, 149, 153, 154, 157, 169, 170, 174},
         {69, 73, 85, 89, 90, 149, 153, 154, 158, 169, 170, 174},
         {69, 73, 85, 86, 89, 90, 149, 150, 153, 154, 158, 170, 174},
         {69, 85, 86, 89, 90, 106, 150, 153, 154, 155, 158, 170, 171, 174, 175},
         {80, 81, 84, 85, 101, 149, 150, 153, 165, 166, 169, 170},
         {81, 85, 86, 101, 102, 149, 150, 153, 154, 165, 166, 169, 170, 234},
         {81, 85, 86, 101, 102, 149, 150, 154, 165, 166, 170},
         {81, 82, 85, 86, 102, 149, 150, 154, 166, 167, 170, 171},
         {84, 85, 89, 101, 105, 149, 150, 153, 154, 165, 166, 169, 170, 234},
         {69, 81, 84, 85, 86, 89, 101, 149, 150, 153, 154, 165, 166, 169, 170, 234},
         {69, 81, 85, 86, 89, 90, 101, 102, 106, 149, 150, 153, 154, 165, 166, 169, 170, 171, 234},
         {85, 86, 90, 102, 106, 149, 150, 154, 166, 170, 171},
         {84, 85, 89, 101, 105, 149, 153, 154, 165, 169, 170},
         {69, 84, 85, 86, 89, 90, 101, 105, 106, 149, 150, 153, 154, 165, 166, 169, 170, 174, 234},
         {69, 85, 86, 89, 90, 106, 149, 150, 153, 154, 166, 169, 170},
         {69, 85, 86, 89, 90, 102, 106, 149, 150, 153, 154, 166, 170, 171},
         {84, 85, 88, 89, 105, 149, 153, 154, 169, 170, 173, 174},
         {85, 89, 90, 105, 106, 149, 153, 154, 169, 170, 174},
         {69, 85, 86, 89, 90, 105, 106, 149, 150, 153, 154, 169, 170, 174},
         {69, 85, 86, 89, 90, 106, 150, 153, 154, 170, 171, 174, 175},
         {80, 81, 84, 85, 101, 149, 165, 166, 169, 170},
         {81, 85, 86, 101, 102, 149, 150, 165, 166, 169, 170},
         {81, 85, 86, 101, 102, 149, 150, 165, 166, 170},
         {81, 82, 85, 86, 101, 102, 149, 150, 165, 166, 167, 170, 171},
         {84, 85, 89, 101, 105, 149, 153, 165, 166, 169, 170},
         {81, 84, 85, 86, 89, 101, 102, 105, 106, 149, 150, 153, 154, 165, 166, 169, 170, 186, 234},
         {81, 85, 86, 101, 102, 106, 149, 150, 154, 165, 166, 169, 170},
         {81, 85, 86, 90, 101, 102, 106, 149, 150, 154, 165, 166, 170, 171},
         {84, 85, 89, 101, 105, 149, 153, 165, 169, 170},
         {84, 85, 89, 101, 105, 106, 149, 153, 154, 165, 166, 169, 170},
         {85, 86, 89, 90, 101, 102, 105, 106, 149, 150, 153, 154, 165, 166, 169, 170},
         {85, 86, 89, 90, 101, 102, 106, 149, 150, 154, 166, 169, 170, 171},
         {84, 85, 88, 89, 101, 105, 149, 153, 165, 169, 170, 173, 174},
         {84, 85, 89, 90, 101, 105, 106, 149, 153, 154, 165, 169, 170, 174},
         {85, 86, 89, 90, 101, 105, 106, 149, 153, 154, 166, 169, 170, 174},
         {85, 86, 89, 90, 102, 105, 106, 150, 153, 154, 166, 169, 170, 171, 174, 175},
         {80, 81, 84, 85, 97, 100, 101, 149, 165, 166, 169, 170, 181, 186},
         {81, 85, 97, 101, 102, 149, 165, 166, 169, 170, 182, 186},
         {81, 85, 86, 97, 101, 102, 149, 150, 165, 166, 170, 182, 186},
         {81, 85, 86, 101, 102, 106, 150, 165, 166, 167, 170, 171, 182, 186, 187},
         {84, 85, 100, 101, 105, 149, 165, 166, 169, 170, 185, 186},
         {85, 101, 102, 105, 106, 149, 165, 166, 169, 170, 186},
         {81, 85, 86, 101, 102, 105, 106, 149, 150, 165, 166, 169, 170, 186},
         {81, 85, 86, 101, 102, 106, 150, 165, 166, 170, 171, 186, 187},
         {84, 85, 89, 100, 101, 105, 149, 153, 165, 169, 170, 185, 186},
         {84, 85, 89, 101, 102, 105, 106, 149, 153, 165, 166, 169, 170, 186},
         {85, 86, 89, 101, 102, 105, 106, 149, 154, 165, 166, 169, 170, 186},
         {85, 86, 90, 101, 102, 105, 106, 150, 154, 165, 166, 169, 170, 171, 186, 187},
         {84, 85, 89, 101, 105, 106, 153, 165, 169, 170, 173, 174, 185, 186, 190},
         {84, 85, 89, 101, 105, 106, 153, 165, 169, 170, 174, 186, 190},
         {85, 89, 90, 101, 102, 105, 106, 153, 154, 165, 166, 169, 170, 174, 186, 190},
         {85, 86, 89, 90, 101, 102, 105, 106, 154, 166, 169, 170, 171, 174, 186},
         {64, 69, 81, 84, 85, 133, 145, 148, 149, 150, 153, 154, 165, 166, 169, 170, 234},
         {65, 69, 81, 85, 86, 133, 145, 149, 150, 153, 154, 165, 166, 170, 234},
         {65, 69, 81, 85, 86, 133, 145, 149, 150, 154, 166, 170, 214, 234},
         {65, 69, 81, 85, 86, 134, 146, 149, 150, 151, 154, 166, 170, 171, 214, 234, 235},
         {68, 69, 84, 85, 89, 133, 148, 149, 150, 153, 154, 165, 169, 170, 234},
         {69, 85, 133, 149, 150, 153, 154, 165, 166, 169, 170, 218, 234},
         {69, 85, 86, 133, 149, 150, 153, 154, 166, 170, 218, 234},
         {69, 85, 86, 134, 149, 150, 154, 155, 166, 170, 171, 218, 234, 235},
         {68, 69, 84, 85, 89, 133, 148, 149, 153, 154, 169, 170, 217, 234},
         {69, 85, 89, 133, 149, 150, 153, 154, 169, 170, 218, 234},
         {69, 85, 86, 89, 90, 133, 149, 150, 153, 154, 170, 218, 234},
         {69, 85, 86, 90, 149, 150, 153, 154, 155, 166, 170, 171, 218, 234, 235},
         {68, 69, 84, 85, 89, 137, 149, 152, 153, 154, 157, 169, 170, 174, 217, 234, 238},
         {69, 85, 89, 137, 149, 153, 154, 158, 169, 170, 174, 218, 234, 238},
         {69, 85, 89, 90, 149, 150, 153, 154, 158, 169, 170, 174, 218, 234, 238},
         {69, 85, 86, 89, 90, 149, 150, 153, 154, 155, 158, 170, 171, 174, 218, 234, 239},
         {80, 81, 84, 85, 101, 145, 148, 149, 150, 153, 165, 166, 169, 170, 234},
         {81, 85, 145, 149, 150, 153, 154, 165, 166, 169, 170, 230, 234},
         {81, 85, 86, 145, 149, 150, 154, 165, 166, 170, 230, 234},
         {81, 85, 86, 146, 149, 150, 154, 166, 167, 170, 171, 230, 234, 235},
         {84, 85, 148, 149, 150, 153, 154, 165, 166, 169, 170, 233, 234},
         {85, 149, 150, 153, 154, 165, 166, 169, 170, 234},
         {85, 86, 149, 150, 153, 154, 165, 166, 169, 170, 234},
         {85, 86, 149, 150, 154, 166, 170, 171, 234, 235},
         {84, 85, 89, 148, 149, 153, 154, 165, 169, 170, 233, 234},
         {85, 89, 149, 150, 153, 154, 165, 166, 169, 170, 234},
         {69, 85, 86, 89, 90, 149, 150, 153, 154, 165, 166, 169, 170, 234},
         {69, 85, 86, 90, 149, 150, 153, 154, 166, 170, 171, 234, 235},
         {84, 85, 89, 149, 152, 153, 154, 169, 170, 173, 174, 233, 234, 238},
         {85, 89, 149, 153, 154, 169, 170, 174, 234, 238},
         {69, 85, 89, 90, 149, 150, 153, 154, 169, 170, 174, 234, 238},
         {85, 86, 89, 90, 149, 150, 153, 154, 170, 171, 174, 234, 239},
         {80, 81, 84, 85, 101, 145, 148, 149, 165, 166, 169, 170, 229, 234},
         {81, 85, 101, 145, 149, 150, 165, 166, 169, 170, 230, 234},
         {81, 85, 86, 101, 102, 145, 149, 150, 165, 166, 170, 230, 234},
         {81, 85, 86, 102, 149, 150, 154, 165, 166, 167, 170, 171, 230, 234, 235},
         {84, 85, 101, 148, 149, 153, 165, 166, 169, 170, 233, 234},
         {85, 101, 149, 150, 153, 154, 165, 166, 169, 170, 234},
         {81, 85, 86, 101, 102, 149, 150, 153, 154, 165, 166, 169, 170, 234},
         {81, 85, 86, 102, 149, 150, 154, 165, 166, 170, 171, 234, 235},
         {84, 85, 89, 101, 105, 148, 149, 153, 165, 169, 170, 233, 234},
         {84, 85, 89, 101, 105, 149, 150, 153, 154, 165, 166, 169, 170, 234},
         {85, 86, 89, 101, 106, 149, 150, 153, 154, 165, 166, 169, 170, 234},
         {85, 86, 90, 102, 106, 149, 150, 153, 154, 165, 166, 169, 170, 171, 234, 235},
         {84, 85, 89, 105, 149, 153, 154, 165, 169, 170, 173, 174, 233, 234, 238},
         {84, 85, 89, 105, 149, 153, 154, 165, 169, 170, 174, 234, 238},
         {85, 89, 90, 105, 106, 149, 150, 153, 154, 165, 166, 169, 170, 174, 234, 238},
         {85, 86, 89, 90, 106, 149, 150, 153, 154, 166, 169, 170, 171, 174, 234},
         {80, 81, 84, 85, 101, 149, 161, 164, 165, 166, 169, 170, 181, 186, 229, 234, 250},
         {81, 85, 101, 149, 161, 165, 166, 169, 170, 182, 186, 230, 234, 250},
         {81, 85, 101, 102, 149, 150, 165, 166, 169, 170, 182, 186, 230, 234, 250},
         {81, 85, 86, 101, 102, 149, 150, 165, 166, 167, 170, 171, 182, 186, 230, 234, 251},
         {84, 85, 101, 149, 164, 165, 166, 169, 170, 185, 186, 233, 234, 250},
         {85, 101, 149, 165, 166, 169, 170, 186, 234, 250},
         {81, 85, 101, 102, 149, 150, 165, 166, 169, 170, 186, 234, 250},
         {85, 86, 101, 102, 149, 150, 165, 166, 170, 171, 186, 234, 251},
         {84, 85, 101, 105, 149, 153, 165, 166, 169, 170, 185, 186, 233, 234, 250},
         {84, 85, 101, 105, 149, 153, 165, 166, 169, 170, 186, 234, 250},
         {85, 101, 102, 105, 106, 149, 150, 153, 154, 165, 166, 169, 170, 186, 234, 250},
         {85, 86, 101, 102, 106, 149, 150, 154, 165, 166, 169, 170, 171, 186, 234},
         {84, 85, 89, 101, 105, 149, 153, 165, 169, 170, 173, 174, 185, 186, 233, 234, 254},
         {85, 89, 101, 105, 149, 153, 165, 169, 170, 174, 186, 234, 254},
         {85, 89, 101, 105, 106, 149, 153, 154, 165, 166, 169, 170, 174, 186, 234},
         {85, 86, 89, 90, 101, 102, 105, 106, 149, 150, 153, 154, 165, 166, 169, 170, 171, 174, 186, 234}
      };
      OpenSimplex2S.LatticeVertex4D[] latticeVerticesByCode = new OpenSimplex2S.LatticeVertex4D[256];

      for (int ixx = 0; ixx < 256; ixx++) {
         int cx = (ixx >> 0 & 3) - 1;
         int cy = (ixx >> 2 & 3) - 1;
         int cz = (ixx >> 4 & 3) - 1;
         int cw = (ixx >> 6 & 3) - 1;
         latticeVerticesByCode[ixx] = new OpenSimplex2S.LatticeVertex4D(cx, cy, cz, cw);
      }

      int nLatticeVerticesTotal = 0;

      for (int ixx = 0; ixx < 256; ixx++) {
         nLatticeVerticesTotal += lookup4DVertexCodes[ixx].length;
      }

      LOOKUP_4D_A = new int[256];
      LOOKUP_4D_B = new OpenSimplex2S.LatticeVertex4D[nLatticeVerticesTotal];
      int ixx = 0;
      int j = 0;

      while (ixx < 256) {
         LOOKUP_4D_A[ixx] = j | j + lookup4DVertexCodes[ixx].length << 16;

         for (int k = 0; k < lookup4DVertexCodes[ixx].length; k++) {
            LOOKUP_4D_B[j++] = latticeVerticesByCode[lookup4DVertexCodes[ixx][k]];
         }

         ixx++;
      }

      OUTPUT_VEC3 = new float[]{
         -0.785581F,
         -0.5120243F,
         0.34740984F,
         0.0F,
         0.44195008F,
         -0.5220253F,
         -0.72949964F,
         0.0F,
         0.6523541F,
         0.15368964F,
         0.7421682F,
         0.0F,
         -0.30872318F,
         0.8803599F,
         -0.36007833F,
         0.0F,
         0.6898583F,
         0.5452765F,
         -0.47620273F,
         0.0F,
         -0.31541684F,
         0.4948706F,
         0.8097007F,
         0.0F,
         0.39922526F,
         -0.87373793F,
         0.2778511F,
         0.0F,
         -0.77366674F,
         -0.16640916F,
         -0.6113491F,
         0.0F,
         0.7639719F,
         0.4777093F,
         0.43375194F,
         0.0F,
         -0.8625241F,
         0.39877898F,
         0.3114924F,
         0.0F,
         0.028345693F,
         -0.9683322F,
         0.24805106F,
         0.0F,
         0.070206486F,
         0.09184391F,
         -0.9932954F,
         0.0F,
         0.17898594F,
         0.27700987F,
         -0.94404954F,
         0.0F,
         0.69886434F,
         -0.6519002F,
         0.29430386F,
         0.0F,
         -0.901297F,
         -0.429545F,
         0.056168288F,
         0.0F,
         0.02344668F,
         0.8044353F,
         0.5935774F,
         0.0F,
         -0.090783685F,
         0.96886706F,
         0.23033662F,
         0.0F,
         -0.82653123F,
         -0.3097547F,
         -0.469998F,
         0.0F,
         0.12608837F,
         -0.53138214F,
         0.8376961F,
         0.0F,
         0.79122657F,
         -0.12773019F,
         -0.5980347F,
         0.0F,
         0.9658556F,
         0.25087538F,
         -0.06468766F,
         0.0F,
         -0.19584262F,
         -0.33726877F,
         0.92081237F,
         0.0F,
         -0.20384087F,
         -0.71605057F,
         -0.66762304F,
         0.0F,
         -0.5661721F,
         0.8024439F,
         -0.1885017F,
         0.0F,
         0.47478813F,
         -0.040413268F,
         -0.8791718F,
         0.0F,
         0.2062869F,
         -0.8235499F,
         0.5284044F,
         0.0F,
         -0.98606974F,
         0.057649244F,
         -0.1560226F,
         0.0F,
         0.3049947F,
         0.80631393F,
         0.5067899F,
         0.0F,
         0.309108F,
         0.71791905F,
         -0.62373435F,
         0.0F,
         -0.99187034F,
         0.060172707F,
         0.11212694F,
         0.0F,
         0.44359803F,
         0.11671667F,
         0.88859326F,
         0.0F,
         0.23916434F,
         -0.8948084F,
         -0.37698588F,
         0.0F,
         0.76432055F,
         -0.3545388F,
         0.5386245F,
         0.0F,
         -0.5579286F,
         -0.76200825F,
         -0.32872343F,
         0.0F,
         0.35318345F,
         0.5156096F,
         -0.78064597F,
         0.0F,
         -0.5595753F,
         0.60093737F,
         0.5707449F,
         0.0F,
         -0.37360692F,
         -0.55274963F,
         0.7449065F,
         0.0F,
         0.5390642F,
         0.75143117F,
         0.38047472F,
         0.0F,
         -0.7496143F,
         0.3715176F,
         -0.54777104F,
         0.0F,
         0.584157F,
         -0.57019913F,
         -0.5776102F,
         0.0F,
         0.07181288F,
         0.93358034F,
         -0.3510992F,
         0.0F,
         -0.95649666F,
         -0.20564815F,
         0.20693718F,
         0.0F,
         0.54712194F,
         -0.08600273F,
         0.832623F,
         0.0F,
         0.33756182F,
         -0.64192945F,
         -0.688461F,
         0.0F,
         -0.3202365F,
         -0.6268258F,
         -0.7103084F,
         0.0F,
         0.57307243F,
         0.7075491F,
         -0.41347587F,
         0.0F,
         0.53328156F,
         -0.5075548F,
         0.67675614F,
         0.0F,
         -0.7861175F,
         0.42683154F,
         0.44702813F,
         0.0F,
         0.99082124F,
         0.097802326F,
         -0.093316466F,
         0.0F,
         -0.21471022F,
         -0.9198782F,
         0.32821283F,
         0.0F,
         -0.43459556F,
         0.14675795F,
         -0.88858813F,
         0.0F,
         -0.34151548F,
         0.6753179F,
         0.65369177F,
         0.0F,
         -0.7339346F,
         0.6310958F,
         -0.2511137F,
         0.0F,
         0.4917497F,
         0.3592956F,
         0.79315126F,
         0.0F,
         0.6327216F,
         -0.09800524F,
         -0.76815253F,
         0.0F,
         -0.39053673F,
         -0.89238614F,
         0.22611502F,
         0.0F,
         0.11219128F,
         0.5066429F,
         -0.8548252F,
         0.0F,
         0.046733536F,
         -0.9815045F,
         -0.18564712F,
         0.0F,
         -0.8875261F,
         0.22087154F,
         0.40436763F,
         0.0F,
         0.7286013F,
         0.2539901F,
         0.6361047F,
         0.0F,
         0.59501386F,
         0.7809812F,
         0.18980747F,
         0.0F,
         -0.49524927F,
         -0.2515845F,
         0.8315247F,
         0.0F,
         0.5538741F,
         -0.7745888F,
         -0.30534527F,
         0.0F,
         -0.65363866F,
         0.24519211F,
         -0.71598697F,
         0.0F,
         -0.41830817F,
         0.12591492F,
         0.8995353F,
         0.0F,
         -0.081837535F,
         0.84589994F,
         -0.5270255F,
         0.0F,
         -0.46637022F,
         -0.7405837F,
         -0.4837712F,
         0.0F,
         0.96651596F,
         -0.23123114F,
         0.11126148F,
         0.0F,
         -0.42655477F,
         -0.826464F,
         0.3674348F,
         0.0F,
         0.12589025F,
         -0.10043331F,
         -0.98694724F,
         0.0F,
         0.8886873F,
         0.13892245F,
         0.43696153F,
         0.0F,
         -0.5880228F,
         0.78797483F,
         0.18255086F,
         0.0F,
         0.23617715F,
         -0.76326156F,
         -0.6013752F,
         0.0F,
         -0.94914985F,
         0.27025643F,
         -0.16148072F,
         0.0F,
         0.60397696F,
         0.77385426F,
         -0.19068679F,
         0.0F,
         0.108995736F,
         -0.28084907F,
         0.9535427F,
         0.0F,
         0.79242766F,
         0.5408602F,
         -0.28200826F,
         0.0F,
         0.23132144F,
         -0.972324F,
         -0.0328092F,
         0.0F,
         -0.7647095F,
         0.18175286F,
         -0.6182114F,
         0.0F,
         -0.25903967F,
         0.24971098F,
         0.9330289F,
         0.0F,
         0.43976018F,
         -0.69979686F,
         0.5629346F,
         0.0F,
         0.28342572F,
         -0.11174767F,
         -0.9524612F,
         0.0F,
         0.27011716F,
         0.90668535F,
         0.3239728F,
         0.0F,
         -0.99330306F,
         -0.09514083F,
         0.06555384F,
         0.0F,
         0.93493223F,
         -0.26454577F,
         -0.23646829F,
         0.0F,
         0.008172545F,
         0.53214574F,
         0.8466133F,
         0.0F,
         -0.5565375F,
         -0.82095534F,
         0.127665F,
         0.0F,
         -0.38656726F,
         0.55335534F,
         -0.73781F,
         0.0F,
         -0.14771666F,
         0.5637174F,
         0.8126515F,
         0.0F,
         -0.81932735F,
         0.01999102F,
         -0.57297736F,
         0.0F,
         0.18972892F,
         -0.94177675F,
         0.2775959F,
         0.0F,
         0.7773151F,
         0.35806832F,
         -0.5172701F,
         0.0F,
         0.9544042F,
         0.15931967F,
         0.25244784F,
         0.0F,
         -0.03845851F,
         -0.61957234F,
         -0.7839969F,
         0.0F,
         -0.43068993F,
         0.8697217F,
         -0.24101941F,
         0.0F,
         -0.48525572F,
         -0.40946904F,
         0.77256846F,
         0.0F,
         0.062257145F,
         0.7156352F,
         -0.69569415F,
         0.0F,
         -0.27099815F,
         -0.8608683F,
         -0.43065745F,
         0.0F,
         -0.68119645F,
         0.25910932F,
         0.6847143F,
         0.0F,
         0.8899375F,
         -0.11387627F,
         0.44163725F,
         0.0F,
         -0.090834185F,
         0.42071554F,
         0.90263367F,
         0.0F,
         0.91705924F,
         -0.3868496F,
         -0.09669412F,
         0.0F,
         -0.1459413F,
         0.6923292F,
         -0.7066692F,
         0.0F,
         -0.6802837F,
         -0.72619516F,
         -0.099270344F,
         0.0F,
         0.7374615F,
         0.6528284F,
         0.1731059F,
         0.0F,
         -0.010589428F,
         -0.6904336F,
         0.7233182F,
         0.0F,
         0.14900921F,
         -0.44473958F,
         -0.88317776F,
         0.0F,
         -0.87588125F,
         0.48234484F,
         -0.013246338F,
         0.0F,
         -0.14426169F,
         -0.32213F,
         -0.93563926F,
         0.0F,
         0.73118335F,
         -0.5322102F,
         0.42675897F,
         0.0F,
         -0.84375817F,
         -0.11198175F,
         0.5249117F,
         0.0F,
         0.25683644F,
         0.9663219F,
         -0.016031396F,
         0.0F,
         -0.5876935F,
         -0.3434663F,
         -0.7325621F,
         0.0F,
         0.2531634F,
         0.94806194F,
         -0.19257954F,
         0.0F,
         0.82601494F,
         -0.5609239F,
         0.05535118F,
         0.0F,
         -0.49148482F,
         -0.04367175F,
         0.8697905F,
         0.0F,
         -0.2992583F,
         0.88667953F,
         0.3524824F,
         0.0F,
         0.9918158F,
         -0.08056652F,
         0.0990474F,
         0.0F,
         -0.24562792F,
         -0.074626446F,
         -0.96648735F,
         0.0F,
         -0.4469296F,
         -0.7314866F,
         0.51495755F,
         0.0F,
         0.16481613F,
         0.3856526F,
         0.9078038F,
         0.0F,
         0.8471119F,
         0.02334587F,
         -0.53090155F,
         0.0F,
         -0.7016312F,
         0.5372623F,
         -0.46804148F,
         0.0F,
         -0.31029677F,
         -0.9462608F,
         0.091139235F,
         0.0F,
         -0.7201842F,
         -0.65839946F,
         0.21873476F,
         0.0F,
         -0.12488519F,
         0.8256613F,
         0.55017F,
         0.0F,
         -0.04757203F,
         0.23585193F,
         -0.9706239F,
         0.0F,
         0.8926414F,
         -0.40311378F,
         0.20171912F,
         0.0F,
         0.97492313F,
         0.19705318F,
         0.10341603F,
         0.0F,
         -0.31364375F,
         0.32785073F,
         -0.8911462F,
         0.0F,
         -0.5120791F,
         0.46185988F,
         0.7241964F,
         0.0F,
         -0.14920028F,
         -0.9867638F,
         0.063533746F,
         0.0F,
         -0.77554274F,
         -0.26363766F,
         0.5736102F,
         0.0F,
         0.36062604F,
         -0.80846596F,
         -0.46511465F,
         0.0F,
         0.7152662F,
         0.4028073F,
         0.5710871F,
         0.0F,
         -0.30034953F,
         0.6692963F,
         -0.67958266F,
         0.0F,
         -0.88113564F,
         -0.42403254F,
         0.20927584F,
         0.0F,
         -0.05903562F,
         0.47561595F,
         -0.8776698F,
         0.0F,
         0.70618635F,
         -0.7062286F,
         -0.050418057F,
         0.0F,
         0.23398492F,
         0.6546452F,
         0.718812F,
         0.0F,
         0.90566903F,
         0.14492719F,
         -0.39844665F,
         0.0F,
         -0.037390787F,
         -0.90575886F,
         0.42214075F,
         0.0F,
         -0.69370157F,
         0.062219433F,
         -0.7175701F,
         0.0F,
         -0.17457666F,
         0.6986122F,
         0.693876F,
         0.0F,
         -0.97131217F,
         -0.19366594F,
         -0.13800786F,
         0.0F,
         0.5137988F,
         -0.27715752F,
         -0.81190795F,
         0.0F,
         0.33180422F,
         -0.50895816F,
         0.7942717F,
         0.0F,
         0.1257092F,
         0.9797817F,
         0.15564412F,
         0.0F,
         0.32922494F,
         -0.06034862F,
         0.94232106F,
         0.0F,
         -0.6831407F,
         0.7270671F,
         -0.0684999F,
         0.0F,
         -0.41280264F,
         -0.8713221F,
         -0.26531455F,
         0.0F,
         0.7667184F,
         0.20460358F,
         -0.6085066F,
         0.0F,
         -0.2783187F,
         -0.95363176F,
         -0.11456518F,
         0.0F,
         0.30026987F,
         0.36764494F,
         -0.88015634F,
         0.0F,
         0.7523949F,
         0.05105669F,
         0.6567306F,
         0.0F,
         -0.7743461F,
         0.5349301F,
         0.33799094F,
         0.0F,
         0.46682087F,
         0.87822413F,
         -0.10392601F,
         0.0F,
         -0.052445807F,
         -0.23687515F,
         0.97012347F,
         0.0F,
         0.5093345F,
         -0.70815456F,
         -0.48897392F,
         0.0F,
         -0.9237096F,
         0.06680554F,
         -0.37722358F,
         0.0F,
         0.8635864F,
         -0.44175482F,
         -0.24304563F,
         0.0F,
         -0.4576867F,
         0.31688565F,
         -0.83072644F,
         0.0F,
         0.18156114F,
         0.7800603F,
         0.5987833F,
         0.0F,
         -0.5874609F,
         -0.6551911F,
         0.47498873F,
         0.0F,
         -0.60157126F,
         0.07741824F,
         0.7950588F,
         0.0F,
         0.6255295F,
         0.77989316F,
         -0.021898665F,
         0.0F,
         -0.55043584F,
         -0.009361596F,
         -0.834825F,
         0.0F,
         0.5264776F,
         -0.84794974F,
         0.061664883F,
         0.0F,
         0.8339385F,
         0.41304004F,
         -0.36598438F,
         0.0F,
         0.23565327F,
         -0.61886406F,
         0.7493162F,
         0.0F,
         -0.46291313F,
         -0.5129438F,
         -0.7229108F,
         0.0F,
         -0.6066786F,
         0.7187678F,
         0.33957896F,
         0.0F,
         -0.18492444F,
         0.9471245F,
         -0.26221782F,
         0.0F,
         0.8104996F,
         -0.32804182F,
         -0.48526177F,
         0.0F,
         -0.78530985F,
         -0.5713098F,
         -0.23852374F,
         0.0F,
         0.1597347F,
         -0.047772843F,
         0.98600334F,
         0.0F,
         -0.5846643F,
         -0.4761198F,
         0.65686953F,
         0.0F,
         0.93853784F,
         0.022778787F,
         0.344424F,
         0.0F,
         -0.3319118F,
         0.93496615F,
         -0.12519144F,
         0.0F,
         -0.021961713F,
         -0.4816251F,
         -0.87610215F,
         0.0F,
         0.07881018F,
         -0.7431176F,
         -0.66450375F,
         0.0F,
         0.47965074F,
         0.8071591F,
         -0.3441356F,
         0.0F,
         0.40674263F,
         -0.284561F,
         0.868093F,
         0.0F,
         -0.9652035F,
         0.22051945F,
         0.14054637F,
         0.0F,
         0.4538839F,
         0.84933543F,
         0.2694787F,
         0.0F,
         -0.93353605F,
         -0.00729983F,
         0.35840917F,
         0.0F,
         0.50515616F,
         -0.7797638F,
         0.36984545F,
         0.0F,
         -0.025503948F,
         -0.062271778F,
         -0.9977333F,
         0.0F,
         -0.40923423F,
         -0.06894318F,
         -0.909821F,
         0.0F,
         -0.7070425F,
         -0.13328275F,
         0.69449735F,
         0.0F,
         0.4116551F,
         0.9043465F,
         0.11268311F,
         0.0F,
         0.7046216F,
         -0.70212054F,
         0.102640495F,
         0.0F,
         -0.04732693F,
         -0.4366091F,
         0.8984056F,
         0.0F,
         -0.22538538F,
         0.97023135F,
         0.08861443F,
         0.0F,
         -0.6520311F,
         -0.4385385F,
         -0.6184977F,
         0.0F,
         0.9247434F,
         -0.09508381F,
         -0.36852232F,
         0.0F,
         0.7148114F,
         -0.64082044F,
         -0.27998897F,
         0.0F,
         0.08817855F,
         0.19141711F,
         0.9775398F,
         0.0F,
         -0.8975827F,
         -0.4018206F,
         -0.18134347F,
         0.0F,
         0.094592795F,
         0.85122395F,
         -0.51620734F,
         0.0F,
         0.3883444F,
         -0.9200928F,
         0.051165212F,
         0.0F,
         0.6681588F,
         0.66306126F,
         0.33751073F,
         0.0F,
         -0.82656866F,
         0.04461183F,
         0.561065F,
         0.0F,
         -0.22993457F,
         0.2124197F,
         -0.949741F,
         0.0F,
         0.015661446F,
         -0.2995014F,
         -0.95396733F,
         0.0F,
         -0.7435326F,
         0.6556702F,
         0.1313613F,
         0.0F,
         0.8715521F,
         0.43441075F,
         0.22734162F,
         0.0F,
         -0.14368093F,
         -0.7905796F,
         0.5952644F,
         0.0F,
         -0.8179541F,
         -0.5576167F,
         0.14147314F,
         0.0F,
         -0.23282142F,
         0.7984288F,
         -0.55525285F,
         0.0F,
         0.6957021F,
         -0.5422551F,
         -0.47112423F,
         0.0F,
         0.35507345F,
         0.301443F,
         0.8849039F,
         0.0F,
         -0.35751346F,
         -0.20022364F,
         0.9121922F,
         0.0F,
         0.037791964F,
         -0.8435244F,
         -0.5357596F,
         0.0F,
         0.91912955F,
         0.3858494F,
         0.07950521F,
         0.0F,
         -0.5994081F,
         0.65789866F,
         -0.45593783F,
         0.0F,
         -0.6551222F,
         -0.277766F,
         0.7026101F,
         0.0F,
         -0.049152214F,
         0.99046654F,
         -0.12868606F,
         0.0F,
         -0.2195843F,
         -0.45926866F,
         -0.86072934F,
         0.0F,
         0.92385876F,
         -0.25343186F,
         0.28680533F,
         0.0F,
         -0.015744451F,
         0.9962814F,
         0.08470869F,
         0.0F,
         0.7017726F,
         -0.26734227F,
         -0.6603358F,
         0.0F,
         -0.8931429F,
         -0.32199132F,
         -0.31403407F,
         0.0F,
         0.20711474F,
         -0.40694776F,
         0.8896612F,
         0.0F,
         -0.8592952F,
         0.1730802F,
         -0.4813055F,
         0.0F,
         -0.14530677F,
         0.09207305F,
         0.9850931F,
         0.0F,
         0.68833655F,
         0.66106874F,
         -0.29863164F,
         0.0F,
         0.3162654F,
         -0.926222F,
         -0.20515598F,
         0.0F,
         0.9231931F,
         0.28944358F,
         -0.2528575F,
         0.0F,
         -0.43748358F,
         0.71738833F,
         0.5421827F,
         0.0F,
         0.050146185F,
         -0.8410951F,
         0.53855765F,
         0.0F,
         -0.5358557F,
         -0.16573682F,
         -0.8278828F,
         0.0F,
         0.55816656F,
         -0.8217994F,
         -0.11443727F,
         0.0F,
         -0.06281051F,
         0.22763938F,
         0.97171766F,
         0.0F,
         -0.91668826F,
         -0.16641024F,
         -0.3633046F,
         0.0F,
         0.42133224F,
         0.7605702F,
         -0.4939758F,
         0.0F,
         -0.6892017F,
         0.42687243F,
         0.58547497F,
         0.0F,
         -0.41265672F,
         -0.24312429F,
         -0.8778411F,
         0.0F,
         0.34967965F,
         -0.82505965F,
         0.44384766F,
         0.0F,
         0.7521788F,
         0.6413115F,
         -0.15148151F,
         0.0F,
         -0.464028F,
         0.83548176F,
         0.29436076F,
         0.0F,
         0.5824682F,
         0.20194194F,
         -0.7873692F,
         0.0F,
         -0.68039966F,
         -0.686639F,
         -0.25609213F,
         0.0F,
         0.56195945F,
         -0.3507847F,
         0.74910057F,
         0.0F,
         -0.9562752F,
         -0.28348097F,
         0.071945876F,
         0.0F,
         0.11484365F,
         0.8968595F,
         0.42714623F,
         0.0F,
         0.25997007F,
         0.054211106F,
         -0.96409374F,
         0.0F,
         0.5814615F,
         -0.66758966F,
         0.4650016F,
         0.0F,
         0.8125787F,
         -0.50303346F,
         0.29440317F,
         0.0F,
         0.2784776F,
         0.81644744F,
         -0.5058298F,
         0.0F,
         -0.5333305F,
         0.27025327F,
         0.8015745F,
         0.0F,
         -0.5577258F,
         -0.5836672F,
         -0.5901479F,
         0.0F,
         -0.8027023F,
         0.4311004F,
         -0.412094F,
         0.0F,
         0.51682884F,
         0.68277705F,
         0.5164334F,
         0.0F,
         0.5794161F,
         -0.38238248F,
         -0.7197643F,
         0.0F,
         -0.29354265F,
         -0.73149496F,
         0.6154249F,
         0.0F
      };
   }

   private static class LatticeVertex4D {
      public final float dx;
      public final float dy;
      public final float dz;
      public final float dw;
      public final long xsvp;
      public final long ysvp;
      public final long zsvp;
      public final long wsvp;

      public LatticeVertex4D(int xsv, int ysv, int zsv, int wsv) {
         this.xsvp = xsv * 5910200641878280303L;
         this.ysvp = ysv * 6452764530575939509L;
         this.zsvp = zsv * 6614699811220273867L;
         this.wsvp = wsv * 6254464313819354443L;
         float ssv = (xsv + ysv + zsv + wsv) * -0.1381966F;
         this.dx = -xsv - ssv;
         this.dy = -ysv - ssv;
         this.dz = -zsv - ssv;
         this.dw = -wsv - ssv;
      }
   }
}
