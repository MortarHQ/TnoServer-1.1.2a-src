package com.github.wal_bos.moreskills.client.fx;

import com.mojang.blaze3d.vertex.BufferBuilder;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

public final class ElfariaAlbisIceSheetRenderer {
   private static final int SHEET = ElfariaVfxVertexHelper.rgba(62, 188, 238, 118);
   private static final int SHEET_SOFT = ElfariaVfxVertexHelper.rgba(132, 236, 255, 142);
   private static final int SHINE = ElfariaVfxVertexHelper.rgba(244, 255, 255, 224);
   private static final int DEEP = ElfariaVfxVertexHelper.rgba(22, 114, 205, 86);
   private static final int LINE = ElfariaVfxVertexHelper.rgba(226, 252, 255, 186);

   private ElfariaAlbisIceSheetRenderer() {
   }

   public static void albisDisc(BufferBuilder buffer, Matrix4f matrix, Vec3 center, double radius, double live, long seed, double yLift) {
      int rings = 18;
      int seg = 144;
      Vec3 c = center.add(0.0, yLift, 0.0);

      for (int r = 0; r < rings; r++) {
         double f0 = (double)r / rings;
         double f1 = (double)(r + 1) / rings;

         for (int i = 0; i < seg; i++) {
            double a0 = i * Math.PI * 2.0 / seg;
            double a1 = (i + 1) * Math.PI * 2.0 / seg;
            Vec3 p00 = radial(c, radius, a0, f0, seed, i, 0.01);
            Vec3 p01 = radial(c, radius, a0, f1, seed, i + 117L, 0.011);
            Vec3 p11 = radial(c, radius, a1, f1, seed, i + 233L, 0.011);
            Vec3 p10 = radial(c, radius, a1, f0, seed, i + 349L, 0.01);
            double edge = 1.0 - f0 * 0.25;
            int color = ElfariaVfxVertexHelper.scaleAlpha((r & 1) == 0 ? SHEET : SHEET_SOFT, live * edge);
            ElfariaVfxVertexHelper.quad(buffer, matrix, p00, p01, p11, p10, color);
         }
      }
   }

   public static void albisDiscLines(BufferBuilder buffer, Matrix4f matrix, Vec3 center, double radius, double live, long seed, double yLift) {
      Vec3 c = center.add(0.0, yLift, 0.0);

      for (int i = 0; i < 280; i++) {
         double h = ElfariaVfxVertexHelper.hash(seed, 5000L + i);
         double a = h * Math.PI * 2.0;
         double r = radius * Math.sqrt(ElfariaVfxVertexHelper.hash(seed, 5300L + i)) * 0.96;
         double len = radius * (0.035 + ElfariaVfxVertexHelper.hash(seed, 5600L + i) * 0.23);
         double bend = (ElfariaVfxVertexHelper.hash(seed, 5900L + i) - 0.5) * 0.5;
         Vec3 p0 = c.add(Math.cos(a) * r, 0.035, Math.sin(a) * r);
         Vec3 p1 = c.add(Math.cos(a + bend) * Math.min(radius, r + len), 0.04, Math.sin(a + bend) * Math.min(radius, r + len));
         ElfariaVfxVertexHelper.line(buffer, matrix, p0, p1, ElfariaVfxVertexHelper.scaleAlpha(LINE, live * (0.24 + h * 0.76)));
      }
   }

   public static void albisSheen(
      BufferBuilder buffer, Matrix4f matrix, Vec3 center, Vec3 axis, Vec3 side, double halfLength, double halfWidth, double live, long seed, double yLift
   ) {
      Vec3 c = center.add(0.0, yLift, 0.0);

      for (int i = 0; i < 96; i++) {
         double h = ElfariaVfxVertexHelper.hash(seed, 7000L + i);
         double u = -0.86 + ElfariaVfxVertexHelper.hash(seed, 7300L + i) * 1.72;
         double v = -0.86 + h * 1.72;
         double len = 0.12 + ElfariaVfxVertexHelper.hash(seed, 7600L + i) * 0.38;
         Vec3 p0 = c.add(axis.scale(u * halfLength)).add(side.scale(v * halfWidth));
         Vec3 p1 = p0.add(axis.scale(len * halfLength)).add(side.scale((ElfariaVfxVertexHelper.hash(seed, 7900L + i) - 0.5) * halfWidth * 0.25));
         ElfariaVfxVertexHelper.ribbon(
            buffer, matrix, p0, p1, 0.012 + halfWidth * 0.005, 0.0, ElfariaVfxVertexHelper.scaleAlpha(SHINE, live * (0.22 + h * 0.58))
         );
      }
   }

   public static void albisRectSheet(
      BufferBuilder buffer, Matrix4f matrix, Vec3 center, Vec3 axis, Vec3 side, double halfLength, double halfWidth, double live, long seed, double yLift
   ) {
      int uSteps = 18;
      int vSteps = 26;
      Vec3 c = center.add(0.0, yLift, 0.0);

      for (int u = 0; u < uSteps; u++) {
         double u0 = -1.0 + 2.0 * u / uSteps;
         double u1 = -1.0 + 2.0 * (u + 1) / uSteps;

         for (int v = 0; v < vSteps; v++) {
            double v0 = -1.0 + 2.0 * v / vSteps;
            double v1 = -1.0 + 2.0 * (v + 1) / vSteps;
            Vec3 p00 = sheetPoint(c, axis, side, halfLength, halfWidth, u0, v0, seed, u * 511L + v);
            Vec3 p01 = sheetPoint(c, axis, side, halfLength, halfWidth, u0, v1, seed, u * 511L + v + 80L);
            Vec3 p11 = sheetPoint(c, axis, side, halfLength, halfWidth, u1, v1, seed, u * 511L + v + 160L);
            Vec3 p10 = sheetPoint(c, axis, side, halfLength, halfWidth, u1, v0, seed, u * 511L + v + 240L);
            double edge = 1.0 - Math.max(Math.abs(u0), Math.abs(v0)) * 0.12;
            ElfariaVfxVertexHelper.quad(
               buffer, matrix, p00, p01, p11, p10, ElfariaVfxVertexHelper.scaleAlpha((u + v & 3) == 0 ? SHEET_SOFT : SHEET, live * edge)
            );
         }
      }
   }

   public static void purePyramid(BufferBuilder buffer, Matrix4f matrix, Vec3 center, double radius, double height, double live) {
      Vec3 top = center.add(0.0, height, 0.0);
      Vec3 base = center.add(0.0, 0.02, 0.0);
      Vec3 p0 = base.add(radius, 0.0, radius);
      Vec3 p1 = base.add(-radius, 0.0, radius);
      Vec3 p2 = base.add(-radius, 0.0, -radius);
      Vec3 p3 = base.add(radius, 0.0, -radius);
      int color = ElfariaVfxVertexHelper.scaleAlpha(SHEET_SOFT, live * 0.72);
      ElfariaVfxVertexHelper.tri(buffer, matrix, p0, p1, top, color);
      ElfariaVfxVertexHelper.tri(buffer, matrix, p1, p2, top, color);
      ElfariaVfxVertexHelper.tri(buffer, matrix, p2, p3, top, color);
      ElfariaVfxVertexHelper.tri(buffer, matrix, p3, p0, top, color);
   }

   public static void pyramidEdges(BufferBuilder buffer, Matrix4f matrix, Vec3 center, double radius, double height, double live) {
      Vec3 top = center.add(0.0, height, 0.0);
      Vec3 base = center.add(0.0, 0.03, 0.0);
      Vec3[] p = new Vec3[]{base.add(radius, 0.0, radius), base.add(-radius, 0.0, radius), base.add(-radius, 0.0, -radius), base.add(radius, 0.0, -radius)};
      int color = ElfariaVfxVertexHelper.scaleAlpha(SHINE, live);

      for (int i = 0; i < 4; i++) {
         ElfariaVfxVertexHelper.line(buffer, matrix, p[i], p[i + 1 & 3], color);
         ElfariaVfxVertexHelper.line(buffer, matrix, p[i], top, color);
      }
   }

   public static void prism(BufferBuilder buffer, Matrix4f matrix, Vec3 base, double height, double radius, int sides, double live, long seed) {
      if (!(height <= 0.01) && !(radius <= 0.01)) {
         Vec3 mid = base.add(0.0, height * 0.42, 0.0);
         Vec3 tip = base.add(0.0, height, 0.0);

         for (int i = 0; i < sides; i++) {
            double a0 = i * Math.PI * 2.0 / sides;
            double a1 = (i + 1) * Math.PI * 2.0 / sides;
            double r0 = radius * (0.88 + ElfariaVfxVertexHelper.hash(seed, i * 11L) * 0.18);
            double r1 = radius * (0.88 + ElfariaVfxVertexHelper.hash(seed, i * 17L) * 0.18);
            Vec3 p0 = base.add(Math.cos(a0) * r0, 0.0, Math.sin(a0) * r0);
            Vec3 p1 = base.add(Math.cos(a1) * r1, 0.0, Math.sin(a1) * r1);
            Vec3 m0 = mid.add(Math.cos(a0) * r0 * 0.5, 0.0, Math.sin(a0) * r0 * 0.5);
            Vec3 m1 = mid.add(Math.cos(a1) * r1 * 0.5, 0.0, Math.sin(a1) * r1 * 0.5);
            int colorA = ElfariaVfxVertexHelper.scaleAlpha((i & 1) == 0 ? SHINE : SHEET_SOFT, live);
            int colorB = ElfariaVfxVertexHelper.scaleAlpha((i & 1) == 0 ? SHEET_SOFT : DEEP, live * 0.86);
            ElfariaVfxVertexHelper.tri(buffer, matrix, p0, p1, m1, colorB);
            ElfariaVfxVertexHelper.tri(buffer, matrix, p0, m1, m0, colorB);
            ElfariaVfxVertexHelper.tri(buffer, matrix, m0, m1, tip, colorA);
         }
      }
   }

   public static void starShard(BufferBuilder buffer, Matrix4f matrix, Vec3 center, Vec3 dir, double length, double width, double live) {
      Vec3 d = dir.lengthSqr() < 0.001 ? new Vec3(0.0, 1.0, 0.0) : dir.normalize();
      Vec3 side = d.cross(new Vec3(0.0, 1.0, 0.0));
      if (side.lengthSqr() < 0.001) {
         side = new Vec3(1.0, 0.0, 0.0);
      }

      side = side.normalize();
      Vec3 a = center.add(d.scale(length));
      Vec3 b = center.add(side.scale(width));
      Vec3 c = center.add(d.scale(-length * 0.38));
      Vec3 e = center.add(side.scale(-width));
      ElfariaVfxVertexHelper.quad(buffer, matrix, a, b, c, e, ElfariaVfxVertexHelper.scaleAlpha(SHINE, live));
   }

   public static void groundSheet(
      BufferBuilder buffer, Matrix4f matrix, Vec3 center, Vec3 axis, Vec3 side, double halfLength, double halfWidth, double live, long seed, double yLift
   ) {
      albisRectSheet(buffer, matrix, center, axis, side, halfLength, halfWidth, live, seed, yLift);
   }

   public static void roundSheet(BufferBuilder buffer, Matrix4f matrix, Vec3 center, double radius, double live, long seed, double yLift) {
      albisDisc(buffer, matrix, center, radius, live, seed, yLift);
   }

   public static void sheetLines(
      BufferBuilder buffer, Matrix4f matrix, Vec3 center, Vec3 axis, Vec3 side, double halfLength, double halfWidth, double live, long seed, double yLift
   ) {
      albisSheen(buffer, matrix, center, axis, side, halfLength, halfWidth, live, seed, yLift);
   }

   public static void roundLines(BufferBuilder buffer, Matrix4f matrix, Vec3 center, double radius, double live, long seed, double yLift) {
      albisDiscLines(buffer, matrix, center, radius, live, seed, yLift);
   }

   private static Vec3 sheetPoint(Vec3 c, Vec3 axis, Vec3 side, double halfLength, double halfWidth, double u, double v, long seed, long salt) {
      double n = ElfariaVfxVertexHelper.hash(seed, salt) - 0.5;
      double edge = !(Math.abs(u) > 0.96) && !(Math.abs(v) > 0.96) ? n * 0.018 : n * 0.13;
      return c.add(axis.scale((u + edge * 0.12) * halfLength)).add(side.scale((v + edge * 0.12) * halfWidth)).add(0.0, n * 0.01, 0.0);
   }

   private static Vec3 radial(Vec3 c, double radius, double angle, double f, long seed, long salt, double lift) {
      double edge = 0.97
         + Math.sin(angle * 9.0 + ElfariaVfxVertexHelper.hash(seed, salt) * 6.0) * 0.025
         + (ElfariaVfxVertexHelper.hash(seed, salt + 900L) - 0.5) * 0.02;
      double r = radius * f * edge;
      return c.add(Math.cos(angle) * r, lift + (ElfariaVfxVertexHelper.hash(seed, salt + 1200L) - 0.5) * 0.01, Math.sin(angle) * r);
   }
}
