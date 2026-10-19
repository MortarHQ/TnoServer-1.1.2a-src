package com.tensura_tno.client.compat;

import com.mojang.blaze3d.vertex.BufferBuilder;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

public final class MoreSkillsElfariaIceMeshRenderer {
   private static final int SHEET = rgba(62, 188, 238, 118);
   private static final int SHEET_SOFT = rgba(132, 236, 255, 142);
   private static final MoreSkillsElfariaIceMeshRenderer.CircleTable NEAR_CIRCLE = new MoreSkillsElfariaIceMeshRenderer.CircleTable(96);
   private static final MoreSkillsElfariaIceMeshRenderer.CircleTable MID_CIRCLE = new MoreSkillsElfariaIceMeshRenderer.CircleTable(64);
   private static final MoreSkillsElfariaIceMeshRenderer.CircleTable FAR_CIRCLE = new MoreSkillsElfariaIceMeshRenderer.CircleTable(36);
   private static final ThreadLocal<MoreSkillsElfariaIceMeshRenderer.DiscScratch> DISC_SCRATCH = ThreadLocal.withInitial(
      MoreSkillsElfariaIceMeshRenderer.DiscScratch::new
   );

   private MoreSkillsElfariaIceMeshRenderer() {
   }

   public static boolean renderDisc(BufferBuilder buffer, Matrix4f matrix, Vec3 center, double radius, double live, long seed, double yLift) {
      if (MoreSkillsElfariaVfxOptimizer.shouldOptimizeIceMesh()
         && isFinite(center)
         && Double.isFinite(radius)
         && Double.isFinite(live)
         && Double.isFinite(yLift)) {
         if (!(live <= 0.002) && MoreSkillsElfariaVfxOptimizer.isGeometryVisible(center, Math.abs(radius) + 0.25)) {
            int rings = MoreSkillsElfariaVfxOptimizer.discRings();
            int segments = MoreSkillsElfariaVfxOptimizer.discSegments();
            MoreSkillsElfariaIceMeshRenderer.CircleTable circle = circleFor(segments);
            MoreSkillsElfariaIceMeshRenderer.DiscScratch scratch = DISC_SCRATCH.get();
            scratch.prepare(circle, seed);
            double centerX = center.x;
            double centerY = center.y + yLift;
            double centerZ = center.z;

            for (int ring = 0; ring < rings; ring++) {
               double f0 = (double)ring / rings;
               double f1 = (double)(ring + 1) / rings;
               double edgeAlpha = 1.0 - f0 * 0.25;
               int color = scaleAlpha((ring & 1) == 0 ? SHEET : SHEET_SOFT, live * edgeAlpha);

               for (int segment = 0; segment < segments; segment++) {
                  vertex(
                     buffer,
                     matrix,
                     centerX + radius * f0 * scratch.unitX[0][segment],
                     centerY + scratch.yOffset[0][segment],
                     centerZ + radius * f0 * scratch.unitZ[0][segment],
                     color
                  );
                  vertex(
                     buffer,
                     matrix,
                     centerX + radius * f1 * scratch.unitX[1][segment],
                     centerY + scratch.yOffset[1][segment],
                     centerZ + radius * f1 * scratch.unitZ[1][segment],
                     color
                  );
                  vertex(
                     buffer,
                     matrix,
                     centerX + radius * f1 * scratch.unitX[2][segment],
                     centerY + scratch.yOffset[2][segment],
                     centerZ + radius * f1 * scratch.unitZ[2][segment],
                     color
                  );
                  vertex(
                     buffer,
                     matrix,
                     centerX + radius * f0 * scratch.unitX[3][segment],
                     centerY + scratch.yOffset[3][segment],
                     centerZ + radius * f0 * scratch.unitZ[3][segment],
                     color
                  );
               }
            }

            return true;
         } else {
            return true;
         }
      } else {
         return false;
      }
   }

   public static boolean renderRectSheet(
      BufferBuilder buffer, Matrix4f matrix, Vec3 center, Vec3 axis, Vec3 side, double halfLength, double halfWidth, double live, long seed, double yLift
   ) {
      if (MoreSkillsElfariaVfxOptimizer.shouldOptimizeIceMesh()
         && isFinite(center)
         && isFinite(axis)
         && isFinite(side)
         && Double.isFinite(halfLength)
         && Double.isFinite(halfWidth)
         && Double.isFinite(live)
         && Double.isFinite(yLift)) {
         double extent = Math.abs(halfLength) * Math.sqrt(axis.lengthSqr()) + Math.abs(halfWidth) * Math.sqrt(side.lengthSqr()) + 0.25;
         if (!(live <= 0.002) && MoreSkillsElfariaVfxOptimizer.isGeometryVisible(center, extent)) {
            int uSteps = MoreSkillsElfariaVfxOptimizer.rectUSteps();
            int vSteps = MoreSkillsElfariaVfxOptimizer.rectVSteps();
            double centerX = center.x;
            double centerY = center.y + yLift;
            double centerZ = center.z;

            for (int u = 0; u < uSteps; u++) {
               double u0 = -1.0 + 2.0 * u / uSteps;
               double u1 = -1.0 + 2.0 * (u + 1) / uSteps;

               for (int v = 0; v < vSteps; v++) {
                  double v0 = -1.0 + 2.0 * v / vSteps;
                  double v1 = -1.0 + 2.0 * (v + 1) / vSteps;
                  long salt = u * 511L + v;
                  double edgeAlpha = 1.0 - Math.max(Math.abs(u0), Math.abs(v0)) * 0.12;
                  int color = scaleAlpha((u + v & 3) == 0 ? SHEET_SOFT : SHEET, live * edgeAlpha);
                  sheetVertex(buffer, matrix, centerX, centerY, centerZ, axis, side, halfLength, halfWidth, u0, v0, seed, salt, color);
                  sheetVertex(buffer, matrix, centerX, centerY, centerZ, axis, side, halfLength, halfWidth, u0, v1, seed, salt + 80L, color);
                  sheetVertex(buffer, matrix, centerX, centerY, centerZ, axis, side, halfLength, halfWidth, u1, v1, seed, salt + 160L, color);
                  sheetVertex(buffer, matrix, centerX, centerY, centerZ, axis, side, halfLength, halfWidth, u1, v0, seed, salt + 240L, color);
               }
            }

            return true;
         } else {
            return true;
         }
      } else {
         return false;
      }
   }

   private static void sheetVertex(
      BufferBuilder buffer,
      Matrix4f matrix,
      double centerX,
      double centerY,
      double centerZ,
      Vec3 axis,
      Vec3 side,
      double halfLength,
      double halfWidth,
      double u,
      double v,
      long seed,
      long salt,
      int color
   ) {
      double noise = hash(seed, salt) - 0.5;
      double edge = !(Math.abs(u) > 0.96) && !(Math.abs(v) > 0.96) ? noise * 0.018 : noise * 0.13;
      double along = (u + edge * 0.12) * halfLength;
      double across = (v + edge * 0.12) * halfWidth;
      vertex(
         buffer,
         matrix,
         centerX + axis.x * along + side.x * across,
         centerY + axis.y * along + side.y * across + noise * 0.01,
         centerZ + axis.z * along + side.z * across,
         color
      );
   }

   private static MoreSkillsElfariaIceMeshRenderer.CircleTable circleFor(int segments) {
      return switch (segments) {
         case 36 -> FAR_CIRCLE;
         case 64 -> MID_CIRCLE;
         case 96 -> NEAR_CIRCLE;
         default -> throw new IllegalStateException("Unexpected Elfaria disc segment count: " + segments);
      };
   }

   private static void vertex(BufferBuilder buffer, Matrix4f matrix, double x, double y, double z, int color) {
      int alpha = color >>> 24 & 0xFF;
      int red = color >>> 16 & 0xFF;
      int green = color >>> 8 & 0xFF;
      int blue = color & 0xFF;
      buffer.addVertex(matrix, (float)x, (float)y, (float)z).setColor(red, green, blue, alpha);
   }

   private static int rgba(int red, int green, int blue, int alpha) {
      return (alpha & 0xFF) << 24 | (red & 0xFF) << 16 | (green & 0xFF) << 8 | blue & 0xFF;
   }

   private static int scaleAlpha(int color, double scale) {
      int alpha = (int)Math.max(0.0, Math.min(255.0, (color >>> 24 & 0xFF) * scale));
      return color & 16777215 | alpha << 24;
   }

   private static boolean isFinite(Vec3 value) {
      return value != null && Double.isFinite(value.x) && Double.isFinite(value.y) && Double.isFinite(value.z);
   }

   private static double hash(long seed, long salt) {
      long value = seed + salt * -7046029254386353131L;
      value ^= value >>> 30;
      value *= -4658895280553007687L;
      value ^= value >>> 27;
      value *= -7723592293110705685L;
      value ^= value >>> 31;
      return (value & 16777215L) / 1.6777216E7;
   }

   private static final class CircleTable {
      private final int segments;
      private final double[] cosine;
      private final double[] sine;
      private final double[] angleTimesNine;

      private CircleTable(int segments) {
         this.segments = segments;
         this.cosine = new double[segments + 1];
         this.sine = new double[segments + 1];
         this.angleTimesNine = new double[segments + 1];

         for (int index = 0; index <= segments; index++) {
            double angle = index * Math.PI * 2.0 / segments;
            this.cosine[index] = Math.cos(angle);
            this.sine[index] = Math.sin(angle);
            this.angleTimesNine[index] = angle * 9.0;
         }
      }
   }

   private static final class DiscScratch {
      private static final int CORNERS = 4;
      private static final int MAX_SEGMENTS = 96;
      private final double[][] unitX = new double[4][96];
      private final double[][] unitZ = new double[4][96];
      private final double[][] yOffset = new double[4][96];

      private void prepare(MoreSkillsElfariaIceMeshRenderer.CircleTable circle, long seed) {
         for (int segment = 0; segment < circle.segments; segment++) {
            this.prepareCorner(circle, seed, segment, 0, segment, segment, 0.01);
            this.prepareCorner(circle, seed, segment, 1, segment, segment + 117L, 0.011);
            this.prepareCorner(circle, seed, segment, 2, segment + 1, segment + 233L, 0.011);
            this.prepareCorner(circle, seed, segment, 3, segment + 1, segment + 349L, 0.01);
         }
      }

      private void prepareCorner(
         MoreSkillsElfariaIceMeshRenderer.CircleTable circle, long seed, int segment, int corner, int angleIndex, long salt, double lift
      ) {
         double edge = 0.97
            + Math.sin(circle.angleTimesNine[angleIndex] + MoreSkillsElfariaIceMeshRenderer.hash(seed, salt) * 6.0) * 0.025
            + (MoreSkillsElfariaIceMeshRenderer.hash(seed, salt + 900L) - 0.5) * 0.02;
         this.unitX[corner][segment] = circle.cosine[angleIndex] * edge;
         this.unitZ[corner][segment] = circle.sine[angleIndex] * edge;
         this.yOffset[corner][segment] = lift + (MoreSkillsElfariaIceMeshRenderer.hash(seed, salt + 1200L) - 0.5) * 0.01;
      }
   }
}
