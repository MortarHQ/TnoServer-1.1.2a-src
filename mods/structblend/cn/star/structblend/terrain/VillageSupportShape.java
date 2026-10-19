package cn.star.structblend.terrain;

import java.util.Arrays;
import java.util.List;
import java.util.function.IntBinaryOperator;
import java.util.function.LongBinaryOperator;

public final class VillageSupportShape {
   public static final int RADIUS = 6;
   private static final int MAX_DEPTH = 48;
   private static final int MAX_SHORE_DEPTH = 8;
   private static final int NO_EFFECT_FRINGE_DISTANCE_SQUARED = 32;
   private static final int SAFE_FRINGE_TOP_LIMIT = 1048576;
   private final int minX;
   private final int minZ;
   private final float[] tops = new float[256];
   private final int[] bottoms = new int[256];
   private final float[] strengths = new float[256];

   private VillageSupportShape(int minX, int minZ) {
      this.minX = minX;
      this.minZ = minZ;
      Arrays.fill(this.tops, Float.NaN);
   }

   public static VillageSupportShape build(
      int minX, int minZ, int minY, List<VillageSupportShape.Patch> patches, List<VillageSupportShape.Protection> protections, IntBinaryOperator groundHeight
   ) {
      return buildWithHeights(minX, minZ, minY, patches, protections, (x, z) -> {
         int height = groundHeight.applyAsInt((int)x, (int)z);
         return pack(height, height);
      });
   }

   public static VillageSupportShape buildWithHeights(
      int minX, int minZ, int minY, List<VillageSupportShape.Patch> patches, List<VillageSupportShape.Protection> protections, LongBinaryOperator heights
   ) {
      VillageSupportShape shape = new VillageSupportShape(minX, minZ);
      int[] caps = new int[256];
      long[] sampled = new long[256];
      Arrays.fill(caps, Integer.MAX_VALUE);
      boolean[] queried = new boolean[256];

      for (VillageSupportShape.Protection box : protections) {
         for (int z = Math.max(minZ, box.minZ); z <= Math.min(minZ + 15, box.maxZ); z++) {
            for (int x = Math.max(minX, box.minX); x <= Math.min(minX + 15, box.maxX); x++) {
               int i = z - minZ << 4 | x - minX;
               caps[i] = Math.min(caps[i], box.minY - 1);
            }
         }
      }

      boolean any = false;

      for (VillageSupportShape.Patch patch : patches) {
         for (int z = Math.max(minZ, patch.minZ - 6); z <= Math.min(minZ + 15, patch.maxZ + 6); z++) {
            int dz = Math.max(patch.minZ - z, Math.max(0, z - patch.maxZ));

            for (int x = Math.max(minX, patch.minX - 6); x <= Math.min(minX + 15, patch.maxX + 6); x++) {
               int dx = Math.max(patch.minX - x, Math.max(0, x - patch.maxX));
               int distance = dx * dx + dz * dz;
               if (distance < 36) {
                  int i = z - minZ << 4 | x - minX;
                  int top = Math.min(patch.topY, caps[i]);
                  if (top >= minY && (distance < 32 || top < -1048576 || top > 1048576)) {
                     if (!queried[i]) {
                        sampled[i] = heights.applyAsLong(x, z);
                        queried[i] = true;
                     }

                     int natural = floor(sampled[i]);
                     int gap = top - natural;
                     int water = surface(sampled[i]) - natural;
                     boolean shore = water > 0 && top >= surface(sampled[i]) && gap >= 1 && gap <= 8;
                     if (natural >= minY && gap <= 48 && (shore || water <= 2 && gap > 2)) {
                        double t = 1.0 - Math.sqrt(distance) / 6.0;
                        double weight = SlopeVariation.fillWeight(t, x, z);
                        float desired;
                        if (shore) {
                           desired = (float)Math.min(top, natural + Math.floor(gap * weight + 0.5));
                           if (desired < natural + 1) {
                              continue;
                           }
                        } else {
                           desired = (float)(natural + gap * weight);
                           if (desired - natural < 0.75) {
                              continue;
                           }
                        }

                        if (Float.isNaN(shape.tops[i]) || desired > shape.tops[i] || desired == shape.tops[i] && (float)weight > shape.strengths[i]) {
                           shape.tops[i] = desired;
                           shape.bottoms[i] = Math.max(minY, natural - 2);
                           shape.strengths[i] = (float)weight;
                           any = true;
                        }
                     }
                  }
               }
            }
         }
      }

      return any ? shape : null;
   }

   private static long pack(int surface, int floor) {
      return (long)surface << 32 | floor & 4294967295L;
   }

   private static int surface(long pair) {
      return (int)(pair >> 32);
   }

   private static int floor(long pair) {
      return (int)pair;
   }

   public double apply(double original, int x, int y, int z) {
      if (x >= this.minX && x < this.minX + 16 && z >= this.minZ && z < this.minZ + 16) {
         int i = z - this.minZ << 4 | x - this.minX;
         float top = this.tops[i];
         if (!Float.isNaN(top) && !(y > top) && y >= this.bottoms[i]) {
            double fill = 4.0 * this.strengths[i] * clamp((top + 1.0 - y) / 2.0) * clamp((y - this.bottoms[i]) / 2.0);
            return Math.max(original, fill);
         } else {
            return original;
         }
      } else {
         return original;
      }
   }

   private static double clamp(double value) {
      return Math.max(0.0, Math.min(1.0, value));
   }

   public record Patch(int minX, int maxX, int minZ, int maxZ, int topY) {
   }

   public record Protection(int minX, int maxX, int minZ, int maxZ, int minY) {
   }
}
