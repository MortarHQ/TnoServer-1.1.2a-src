package com.github.wal_bos.moreskills.client.fx;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

public final class ElfariaVfxVertexHelper {
   private ElfariaVfxVertexHelper() {
   }

   public static void beginIce() {
      RenderSystem.enableBlend();
      RenderSystem.enableDepthTest();
      RenderSystem.depthFunc(515);
      RenderSystem.disableCull();
      RenderSystem.depthMask(false);
      RenderSystem.setShader(GameRenderer::getPositionColorShader);
      RenderSystem.blendFuncSeparate(770, 771, 1, 0);
   }

   public static void endIce() {
      RenderSystem.depthMask(true);
      RenderSystem.enableCull();
      RenderSystem.disableBlend();
      RenderSystem.defaultBlendFunc();
   }

   public static BufferBuilder beginQuads() {
      return Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
   }

   public static BufferBuilder beginTriangles() {
      return Tesselator.getInstance().begin(Mode.TRIANGLES, DefaultVertexFormat.POSITION_COLOR);
   }

   public static BufferBuilder beginLines() {
      return Tesselator.getInstance().begin(Mode.DEBUG_LINES, DefaultVertexFormat.POSITION_COLOR);
   }

   public static void finish(BufferBuilder buffer) {
      try {
         BufferUploader.drawWithShader(buffer.buildOrThrow());
      } catch (IllegalStateException var2) {
      }
   }

   public static Vec3 camera() {
      Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
      return camera.getPosition();
   }

   public static Vec3 rel(Vec3 world, Vec3 cam) {
      return world.subtract(cam);
   }

   public static void quad(BufferBuilder buffer, Matrix4f matrix, Vec3 a, Vec3 b, Vec3 c, Vec3 d, int color) {
      vertex(buffer, matrix, a, color);
      vertex(buffer, matrix, b, color);
      vertex(buffer, matrix, c, color);
      vertex(buffer, matrix, d, color);
   }

   public static void tri(BufferBuilder buffer, Matrix4f matrix, Vec3 a, Vec3 b, Vec3 c, int color) {
      vertex(buffer, matrix, a, color);
      vertex(buffer, matrix, b, color);
      vertex(buffer, matrix, c, color);
   }

   public static void line(BufferBuilder buffer, Matrix4f matrix, Vec3 a, Vec3 b, int color) {
      vertex(buffer, matrix, a, color);
      vertex(buffer, matrix, b, color);
   }

   public static void vertex(BufferBuilder buffer, Matrix4f matrix, Vec3 p, int color) {
      int a = color >>> 24 & 0xFF;
      int r = color >>> 16 & 0xFF;
      int g = color >>> 8 & 0xFF;
      int b = color & 0xFF;
      buffer.addVertex(matrix, (float)p.x, (float)p.y, (float)p.z).setColor(r, g, b, a);
   }

   public static void ribbon(BufferBuilder buffer, Matrix4f matrix, Vec3 a, Vec3 b, double widthA, double widthB, int color) {
      Vec3 side = tangentSide(a, b);
      quad(buffer, matrix, a.add(side.scale(widthA)), a.add(side.scale(-widthA)), b.add(side.scale(-widthB)), b.add(side.scale(widthB)), color);
   }

   public static Vec3 tangentSide(Vec3 a, Vec3 b) {
      Vec3 d = b.subtract(a);
      Vec3 side = new Vec3(-d.z, 0.0, d.x);
      if (side.lengthSqr() < 1.0E-6) {
         side = new Vec3(1.0, 0.0, 0.0);
      }

      return side.normalize();
   }

   public static Vec3 cameraSide(Vec3 center) {
      Vec3 cam = camera();
      Vec3 forward = cam.subtract(center);
      if (forward.lengthSqr() < 1.0E-4) {
         forward = new Vec3(0.0, 0.0, 1.0);
      }

      forward = forward.normalize();
      Vec3 up = Math.abs(forward.y) > 0.95 ? new Vec3(1.0, 0.0, 0.0) : new Vec3(0.0, 1.0, 0.0);
      return forward.cross(up).normalize();
   }

   public static void billboardDiamond(BufferBuilder buffer, Matrix4f matrix, Vec3 center, double size, int color) {
      Vec3 side = cameraSide(center);
      Vec3 forward = camera().subtract(center);
      if (forward.lengthSqr() < 1.0E-4) {
         forward = new Vec3(0.0, 0.0, 1.0);
      }

      forward = forward.normalize();
      Vec3 vertical = side.cross(forward).normalize();
      quad(
         buffer,
         matrix,
         center.add(vertical.scale(size)),
         center.add(side.scale(size)),
         center.add(vertical.scale(-size)),
         center.add(side.scale(-size)),
         color
      );
   }

   public static int rgba(int r, int g, int b, int a) {
      return (a & 0xFF) << 24 | (r & 0xFF) << 16 | (g & 0xFF) << 8 | b & 0xFF;
   }

   public static int scaleAlpha(int color, double scale) {
      int a = (int)Math.max(0.0, Math.min(255.0, (color >>> 24 & 0xFF) * scale));
      return color & 16777215 | a << 24;
   }

   public static double smooth(double x) {
      x = Math.max(0.0, Math.min(1.0, x));
      return x * x * (3.0 - 2.0 * x);
   }

   public static double hash(long seed, long salt) {
      long x = seed + salt * -7046029254386353131L;
      x ^= x >>> 30;
      x *= -4658895280553007687L;
      x ^= x >>> 27;
      x *= -7723592293110705685L;
      x ^= x >>> 31;
      return (x & 16777215L) / 1.6777216E7;
   }

   public static Vec3 rotateY(Vec3 v, double angle) {
      double c = Math.cos(angle);
      double s = Math.sin(angle);
      return new Vec3(v.x * c - v.z * s, v.y, v.x * s + v.z * c);
   }

   public static double clamp(double value, double min, double max) {
      return Mth.clamp(value, min, max);
   }
}
