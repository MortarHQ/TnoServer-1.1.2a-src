package com.github.wal_bos.moreskills.client.fx;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent.Stage;
import org.joml.Matrix4f;

public final class BerettaVfxRenderHelper {
   private BerettaVfxRenderHelper() {
   }

   public static BerettaVfxRenderHelper.Frame begin(RenderLevelStageEvent event) {
      if (event.getStage() != Stage.AFTER_PARTICLES) {
         return null;
      } else {
         Minecraft minecraft = Minecraft.getInstance();
         if (minecraft.level != null && minecraft.player != null) {
            PoseStack poseStack = event.getPoseStack();
            Matrix4f matrix = poseStack.last().pose();
            Vec3 camera = event.getCamera().getPosition();
            float partial = event.getPartialTick().getGameTimeDeltaPartialTick(false);
            long gameTime = minecraft.level.getGameTime();
            RenderSystem.enableBlend();
            RenderSystem.blendFuncSeparate(770, 1, 1, 0);
            RenderSystem.disableCull();
            RenderSystem.enableDepthTest();
            RenderSystem.depthFunc(515);
            RenderSystem.depthMask(false);
            RenderSystem.setShader(GameRenderer::getPositionColorShader);
            BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
            return new BerettaVfxRenderHelper.Frame(buffer, matrix, camera, gameTime, partial);
         } else {
            return null;
         }
      }
   }

   public static void end(BerettaVfxRenderHelper.Frame frame, boolean wrote) {
      if (frame != null) {
         if (wrote) {
            try {
               BufferUploader.drawWithShader(frame.buffer.buildOrThrow());
            } catch (IllegalStateException var3) {
            }
         }

         RenderSystem.depthMask(true);
         RenderSystem.enableDepthTest();
         RenderSystem.enableCull();
         RenderSystem.disableBlend();
         RenderSystem.defaultBlendFunc();
      }
   }

   public static float age(BerettaVfxRenderHelper.Frame frame, long startTick) {
      return Math.max(0.0F, (float)(frame.gameTime - startTick) + frame.partial);
   }

   public static boolean lane(BerettaVfxRenderHelper.Frame frame, Vec3 start, Vec3 end, double halfWidth, float r, float g, float b, float a) {
      Vec3 direction = end.subtract(start);
      if (!(direction.lengthSqr() < 1.0E-4) && !(a <= 0.001F)) {
         Vec3 side = side(direction).scale(halfWidth);
         quad(frame, start.subtract(side), start.add(side), end.add(side), end.subtract(side), r, g, b, a);
         return true;
      } else {
         return false;
      }
   }

   public static boolean beam(BerettaVfxRenderHelper.Frame frame, Vec3 start, Vec3 end, double radius, float r, float g, float b, float a) {
      Vec3 direction = end.subtract(start);
      if (!(direction.lengthSqr() < 1.0E-4) && !(a <= 0.001F)) {
         direction = direction.normalize();
         Vec3 side = side(direction).scale(radius);
         Vec3 up = direction.cross(side);
         if (up.lengthSqr() < 1.0E-4) {
            up = new Vec3(0.0, radius, 0.0);
         } else {
            up = up.normalize().scale(radius);
         }

         quad(frame, start.subtract(side), start.add(side), end.add(side), end.subtract(side), r, g, b, a);
         quad(frame, start.subtract(up), start.add(up), end.add(up), end.subtract(up), r, g, b, a * 0.88F);
         Vec3 diagonalA = side.add(up).normalize().scale(radius * 0.78);
         Vec3 diagonalB = side.subtract(up).normalize().scale(radius * 0.78);
         quad(frame, start.subtract(diagonalA), start.add(diagonalA), end.add(diagonalA), end.subtract(diagonalA), r, g, b, a * 0.62F);
         quad(frame, start.subtract(diagonalB), start.add(diagonalB), end.add(diagonalB), end.subtract(diagonalB), r, g, b, a * 0.62F);
         return true;
      } else {
         return false;
      }
   }

   public static boolean pillar(BerettaVfxRenderHelper.Frame frame, Vec3 base, double height, double radius, float r, float g, float b, float a) {
      if (!(height <= 0.01) && !(a <= 0.001F)) {
         Vec3 top = base.add(0.0, height, 0.0);
         Vec3 x = new Vec3(radius, 0.0, 0.0);
         Vec3 z = new Vec3(0.0, 0.0, radius);
         Vec3 d1 = new Vec3(radius * 0.72, 0.0, radius * 0.72);
         Vec3 d2 = new Vec3(radius * 0.72, 0.0, -radius * 0.72);
         quad(frame, base.subtract(x), base.add(x), top.add(x), top.subtract(x), r, g, b, a);
         quad(frame, base.subtract(z), base.add(z), top.add(z), top.subtract(z), r, g, b, a * 0.9F);
         quad(frame, base.subtract(d1), base.add(d1), top.add(d1), top.subtract(d1), r, g, b, a * 0.66F);
         quad(frame, base.subtract(d2), base.add(d2), top.add(d2), top.subtract(d2), r, g, b, a * 0.66F);
         return true;
      } else {
         return false;
      }
   }

   public static boolean orb(BerettaVfxRenderHelper.Frame frame, Vec3 center, double radius, float r, float g, float b, float a) {
      if (!(radius <= 0.01) && !(a <= 0.001F)) {
         Vec3 x = new Vec3(1.0, 0.0, 0.0);
         Vec3 y = new Vec3(0.0, 1.0, 0.0);
         Vec3 z = new Vec3(0.0, 0.0, 1.0);
         boolean wrote = false;
         wrote |= freeRing(frame, center, x, y, radius, radius * 0.052, 36, r, g, b, a);
         wrote |= freeRing(frame, center, z, y, radius, radius * 0.052, 36, r, g, b, a * 0.88F);
         wrote |= freeRing(frame, center, x, z, radius, radius * 0.052, 36, r, g, b, a * 0.76F);
         return wrote | freeRing(frame, center, new Vec3(1.0, 1.0, 0.0), new Vec3(0.0, 0.0, 1.0), radius, radius * 0.032, 30, r, g, b, a * 0.52F);
      } else {
         return false;
      }
   }

   public static boolean horizontalRing(
      BerettaVfxRenderHelper.Frame frame, Vec3 center, double radius, double width, int segments, float r, float g, float b, float a
   ) {
      return freeRing(frame, center, new Vec3(1.0, 0.0, 0.0), new Vec3(0.0, 0.0, 1.0), radius, width, segments, r, g, b, a);
   }

   public static boolean freeRing(
      BerettaVfxRenderHelper.Frame frame, Vec3 center, Vec3 axisA, Vec3 axisB, double radius, double width, int segments, float r, float g, float b, float a
   ) {
      if (!(radius <= 0.01) && !(width <= 0.001) && !(a <= 0.001F)) {
         axisA = axisA.normalize();
         axisB = axisB.normalize();
         double inner = Math.max(0.0, radius - width);
         double outer = radius + width;

         for (int i = 0; i < segments; i++) {
            double a0 = (Math.PI * 2) * i / segments;
            double a1 = (Math.PI * 2) * (i + 1) / segments;
            float pulse0 = 0.84F + 0.16F * Mth.sin((float)a0 * 5.0F);
            float pulse1 = 0.84F + 0.16F * Mth.sin((float)a1 * 5.0F);
            Vec3 p0i = center.add(axisA.scale(Math.cos(a0) * inner)).add(axisB.scale(Math.sin(a0) * inner));
            Vec3 p0o = center.add(axisA.scale(Math.cos(a0) * outer)).add(axisB.scale(Math.sin(a0) * outer));
            Vec3 p1o = center.add(axisA.scale(Math.cos(a1) * outer)).add(axisB.scale(Math.sin(a1) * outer));
            Vec3 p1i = center.add(axisA.scale(Math.cos(a1) * inner)).add(axisB.scale(Math.sin(a1) * inner));
            quadGradient(frame, p0i, p0o, p1o, p1i, r, g, b, a * 0.42F * pulse0, a * pulse0, a * pulse1, a * 0.42F * pulse1);
         }

         return true;
      } else {
         return false;
      }
   }

   public static boolean disk(BerettaVfxRenderHelper.Frame frame, Vec3 center, double inner, double outer, int segments, float r, float g, float b, float a) {
      if (!(outer <= inner) && !(a <= 0.001F)) {
         for (int i = 0; i < segments; i++) {
            double a0 = (Math.PI * 2) * i / segments;
            double a1 = (Math.PI * 2) * (i + 1) / segments;
            Vec3 p0i = center.add(Math.cos(a0) * inner, 0.0, Math.sin(a0) * inner);
            Vec3 p0o = center.add(Math.cos(a0) * outer, 0.0, Math.sin(a0) * outer);
            Vec3 p1o = center.add(Math.cos(a1) * outer, 0.0, Math.sin(a1) * outer);
            Vec3 p1i = center.add(Math.cos(a1) * inner, 0.0, Math.sin(a1) * inner);
            quadGradient(frame, p0i, p0o, p1o, p1i, r, g, b, a * 0.16F, a, a, a * 0.16F);
         }

         return true;
      } else {
         return false;
      }
   }

   public static boolean shard(BerettaVfxRenderHelper.Frame frame, Vec3 center, Vec3 direction, double length, double width, float r, float g, float b, float a) {
      Vec3 dir = direction.lengthSqr() < 1.0E-4 ? new Vec3(0.0, 1.0, 0.0) : direction.normalize();
      Vec3 side = side3D(dir).scale(width);
      Vec3 tip = center.add(dir.scale(length));
      Vec3 back = center.subtract(dir.scale(length * 0.35));
      quadGradient(frame, back.subtract(side), back.add(side), tip, tip, r, g, b, a * 0.18F, a * 0.72F, a, a);
      return a > 0.001F;
   }

   public static boolean runeRing(
      BerettaVfxRenderHelper.Frame frame, Vec3 center, double radius, double rotation, int marks, float r, float g, float b, float a
   ) {
      if (a <= 0.001F) {
         return false;
      }

      boolean wrote = false;

      for (int i = 0; i < marks; i++) {
         double angle = rotation + (Math.PI * 2) * i / marks;
         double next = angle + 0.1 + i % 3 * 0.022;
         Vec3 start = center.add(Math.cos(angle) * radius, 0.0, Math.sin(angle) * radius);
         Vec3 end = center.add(Math.cos(next) * radius, 0.0, Math.sin(next) * radius);
         wrote |= lane(frame, start, end, 0.055, r, g, b, a);
         Vec3 radial = new Vec3(Math.cos(angle), 0.0, Math.sin(angle));
         wrote |= lane(frame, start.subtract(radial.scale(0.24)), start.add(radial.scale(0.24)), 0.032, r, g, b, a * 0.78F);
      }

      return wrote;
   }

   public static boolean floorSquare(BerettaVfxRenderHelper.Frame frame, Vec3 center, Vec3 forward, double halfSize, float r, float g, float b, float a) {
      Vec3 f = flat(forward);
      Vec3 s = side(f);
      Vec3 a0 = center.add(f.scale(halfSize)).add(s.scale(halfSize));
      Vec3 b0 = center.add(f.scale(halfSize)).subtract(s.scale(halfSize));
      Vec3 c0 = center.subtract(f.scale(halfSize)).subtract(s.scale(halfSize));
      Vec3 d0 = center.subtract(f.scale(halfSize)).add(s.scale(halfSize));
      quad(frame, a0, b0, c0, d0, r, g, b, a);
      return a > 0.001F;
   }

   public static boolean warningSquare(BerettaVfxRenderHelper.Frame frame, Vec3 center, Vec3 forward, double halfSize, float age, float alpha) {
      if (alpha <= 0.001F) {
         return false;
      }

      boolean wrote = floorSquare(frame, center, forward, halfSize, 0.68F, 0.08F, 1.0F, alpha * 0.18F);
      Vec3 f = flat(forward);
      Vec3 s = side(f);
      double inner = halfSize * 0.86;
      Vec3 a = center.add(f.scale(inner)).add(s.scale(inner));
      Vec3 b = center.add(f.scale(inner)).subtract(s.scale(inner));
      Vec3 c = center.subtract(f.scale(inner)).subtract(s.scale(inner));
      Vec3 d = center.subtract(f.scale(inner)).add(s.scale(inner));
      wrote |= lane(frame, a, b, 0.075, 0.93F, 0.28F, 1.0F, alpha);
      wrote |= lane(frame, b, c, 0.075, 0.93F, 0.28F, 1.0F, alpha);
      wrote |= lane(frame, c, d, 0.075, 0.93F, 0.28F, 1.0F, alpha);
      wrote |= lane(frame, d, a, 0.075, 0.93F, 0.28F, 1.0F, alpha);
      double sweep = age * 0.15 % 1.0;
      Vec3 sweepCenter = center.add(f.scale((sweep * 2.0 - 1.0) * halfSize * 0.82));
      return wrote
         | lane(frame, sweepCenter.subtract(s.scale(halfSize * 0.82)), sweepCenter.add(s.scale(halfSize * 0.82)), 0.045, 1.0F, 0.66F, 1.0F, alpha * 0.92F);
   }

   public static boolean warningLane(BerettaVfxRenderHelper.Frame frame, Vec3 start, Vec3 end, double halfWidth, float age, float alpha) {
      if (alpha <= 0.001F) {
         return false;
      }

      Vec3 direction = end.subtract(start);
      double length = direction.length();
      if (length <= 0.01) {
         return false;
      }

      Vec3 normalized = direction.scale(1.0 / length);
      Vec3 lateral = side(normalized);
      float pulse = 0.76F + 0.24F * Mth.sin(age * 0.42F);
      boolean wrote = lane(frame, start, end, halfWidth, 0.5F, 0.0F, 1.0F, alpha * 0.17F);
      wrote |= lane(frame, start, end, halfWidth * 0.58, 0.74F, 0.04F, 1.0F, alpha * 0.1F);
      wrote |= lane(frame, start.add(lateral.scale(halfWidth)), end.add(lateral.scale(halfWidth)), 0.075, 1.0F, 0.4F, 1.0F, alpha * pulse);
      wrote |= lane(frame, start.subtract(lateral.scale(halfWidth)), end.subtract(lateral.scale(halfWidth)), 0.075, 1.0F, 0.4F, 1.0F, alpha * pulse);
      wrote |= lane(frame, start, end, 0.04, 1.0F, 0.78F, 1.0F, alpha * 0.82F);
      int marks = Math.min(20, Math.max(5, (int)(length / 3.6)));

      for (int i = 1; i < marks; i++) {
         double phase = (i + age * 0.3) % marks;
         Vec3 center = start.add(normalized.scale(length * phase / marks));
         Vec3 back = center.subtract(normalized.scale(0.72));
         Vec3 left = back.add(lateral.scale(Math.min(halfWidth * 0.48, 0.82)));
         Vec3 right = back.subtract(lateral.scale(Math.min(halfWidth * 0.48, 0.82)));
         wrote |= lane(frame, left, center, 0.032, 1.0F, 0.58F, 1.0F, alpha * 0.7F);
         wrote |= lane(frame, right, center, 0.032, 1.0F, 0.58F, 1.0F, alpha * 0.7F);
      }

      return wrote;
   }

   public static boolean warningCircle(BerettaVfxRenderHelper.Frame frame, Vec3 center, double radius, float age, float alpha) {
      if (alpha <= 0.001F) {
         return false;
      }

      boolean wrote = disk(frame, center, Math.max(0.0, radius - 0.6), radius + 0.6, 56, 0.6F, 0.04F, 1.0F, alpha * 0.14F);
      wrote |= horizontalRing(frame, center.add(0.0, 0.018, 0.0), radius, 0.09, 72, 0.98F, 0.34F, 1.0F, alpha);
      wrote |= horizontalRing(frame, center.add(0.0, 0.032, 0.0), radius * (0.42 + 0.1 * Math.sin(age * 0.42)), 0.045, 52, 1.0F, 0.64F, 1.0F, alpha * 0.82F);

      for (int i = 0; i < 8; i++) {
         double angle = (Math.PI * 2) * i / 8.0 + age * 0.035;
         Vec3 radial = new Vec3(Math.cos(angle), 0.0, Math.sin(angle));
         Vec3 p = center.add(radial.scale(radius));
         wrote |= lane(frame, p.subtract(radial.scale(0.5)), p.add(radial.scale(0.5)), 0.045, 1.0F, 0.52F, 1.0F, alpha * 0.88F);
      }

      return wrote;
   }

   public static boolean targetSeal(BerettaVfxRenderHelper.Frame frame, Vec3 center, double radius, float age, float alpha) {
      if (alpha <= 0.001F) {
         return false;
      }

      boolean wrote = warningCircle(frame, center, radius, age, alpha);
      wrote |= runeRing(frame, center.add(0.0, 0.04, 0.0), radius * 1.35, age * 0.08, 16, 1.0F, 0.42F, 1.0F, alpha * 0.86F);
      wrote |= runeRing(frame, center.add(0.0, 0.06, 0.0), radius * 0.64, -age * 0.11, 12, 1.0F, 0.72F, 1.0F, alpha * 0.92F);
      Vec3 x = new Vec3(radius * 1.65, 0.0, 0.0);
      Vec3 z = new Vec3(0.0, 0.0, radius * 1.65);
      wrote |= lane(frame, center.subtract(x), center.add(x), 0.045, 1.0F, 0.52F, 1.0F, alpha * 0.78F);
      return wrote | lane(frame, center.subtract(z), center.add(z), 0.045, 1.0F, 0.52F, 1.0F, alpha * 0.78F);
   }

   public static boolean shockwaveBloom(BerettaVfxRenderHelper.Frame frame, Vec3 center, double radius, float age, int seed, float alpha) {
      if (alpha <= 0.001F) {
         return false;
      }

      boolean wrote = disk(frame, center.add(0.0, 0.025, 0.0), Math.max(0.0, radius - 1.2), radius + 1.2, 72, 0.38F, 0.0F, 1.0F, alpha * 0.12F);
      wrote |= horizontalRing(frame, center.add(0.0, 0.08, 0.0), radius, 0.2, 88, 1.0F, 0.34F, 1.0F, alpha);
      wrote |= horizontalRing(frame, center.add(0.0, 0.16, 0.0), radius * 0.96, 0.08, 88, 1.0F, 0.9F, 1.0F, alpha);
      wrote |= horizontalRing(frame, center.add(0.0, 0.28, 0.0), radius * 1.08, 0.12, 88, 0.48F, 0.0F, 1.0F, alpha * 0.5F);
      int shards = 14;

      for (int i = 0; i < shards; i++) {
         double angle = (Math.PI * 2) * i / shards + age * 0.035 + hash(seed, i) * 0.24;
         Vec3 radial = new Vec3(Math.cos(angle), 0.0, Math.sin(angle));
         Vec3 point = center.add(radial.scale(radius)).add(0.0, 0.12, 0.0);
         wrote |= shard(frame, point, radial.add(0.0, 0.3, 0.0), 0.75 + hash(seed, i + 40) * 0.85, 0.06, 1.0F, 0.44F, 1.0F, alpha * 0.72F);
      }

      return wrote;
   }

   public static boolean safeSquare(BerettaVfxRenderHelper.Frame frame, Vec3 center, Vec3 forward, double halfSize, float age, float alpha) {
      if (alpha <= 0.001F) {
         return false;
      }

      boolean wrote = floorSquare(frame, center, forward, halfSize, 0.72F, 0.32F, 1.0F, alpha * 0.055F);
      Vec3 f = flat(forward);
      Vec3 s = side(f);
      double inner = halfSize * 0.84;
      Vec3 a = center.add(f.scale(inner)).add(s.scale(inner));
      Vec3 b = center.add(f.scale(inner)).subtract(s.scale(inner));
      Vec3 c = center.subtract(f.scale(inner)).subtract(s.scale(inner));
      Vec3 d = center.subtract(f.scale(inner)).add(s.scale(inner));
      wrote |= lane(frame, a, b, 0.045, 0.9F, 0.72F, 1.0F, alpha * 0.72F);
      wrote |= lane(frame, b, c, 0.045, 0.9F, 0.72F, 1.0F, alpha * 0.72F);
      wrote |= lane(frame, c, d, 0.045, 0.9F, 0.72F, 1.0F, alpha * 0.72F);
      wrote |= lane(frame, d, a, 0.045, 0.9F, 0.72F, 1.0F, alpha * 0.72F);
      double pulse = 0.36 + 0.1 * Math.sin(age * 0.34);
      return wrote | horizontalRing(frame, center.add(0.0, 0.03, 0.0), halfSize * pulse, 0.045, 32, 1.0F, 0.84F, 1.0F, alpha * 0.74F);
   }

   public static boolean crossGlow(BerettaVfxRenderHelper.Frame frame, Vec3 center, double radius, float r, float g, float b, float a) {
      if (!(a <= 0.001F) && !(radius <= 0.01)) {
         Vec3 x = new Vec3(radius, 0.0, 0.0);
         Vec3 y = new Vec3(0.0, radius, 0.0);
         Vec3 z = new Vec3(0.0, 0.0, radius);
         quadGradient(
            frame,
            center.subtract(x).subtract(y),
            center.add(x).subtract(y),
            center.add(x).add(y),
            center.subtract(x).add(y),
            r,
            g,
            b,
            0.0F,
            a * 0.35F,
            0.0F,
            a * 0.35F
         );
         quadGradient(
            frame,
            center.subtract(z).subtract(y),
            center.add(z).subtract(y),
            center.add(z).add(y),
            center.subtract(z).add(y),
            r,
            g,
            b,
            0.0F,
            a * 0.35F,
            0.0F,
            a * 0.35F
         );
         quadGradient(
            frame,
            center.subtract(x).subtract(z),
            center.add(x).subtract(z),
            center.add(x).add(z),
            center.subtract(x).add(z),
            r,
            g,
            b,
            0.0F,
            a * 0.26F,
            0.0F,
            a * 0.26F
         );
         return true;
      } else {
         return false;
      }
   }

   public static boolean bloomOrb(BerettaVfxRenderHelper.Frame frame, Vec3 center, double radius, float age, int seed, float alpha) {
      if (alpha <= 0.001F) {
         return false;
      }

      float pulse = 0.88F + 0.12F * Mth.sin(age * 0.58F + seed * 0.001F);
      float flicker = 0.82F + 0.18F * Mth.sin(age * 1.17F + seed * 0.003F);
      boolean wrote = crossGlow(frame, center, radius * 3.25 * pulse, 0.24F, 0.0F, 0.72F, alpha * 0.14F);
      wrote |= crossGlow(frame, center, radius * 2.35 * pulse, 0.48F, 0.0F, 1.0F, alpha * 0.22F);
      wrote |= crossGlow(frame, center, radius * 1.48 * pulse, 0.92F, 0.1F, 1.0F, alpha * 0.4F);
      wrote |= orb(frame, center, radius * 1.22 * pulse, 0.18F, 0.0F, 0.42F, alpha * 0.76F);
      wrote |= orb(frame, center, radius * 0.82 * pulse, 0.88F, 0.1F, 1.0F, alpha);
      wrote |= orb(frame, center, radius * 0.34 * flicker, 1.0F, 0.88F, 1.0F, alpha);
      wrote |= crossGlow(frame, center, radius * 0.52 * flicker, 1.0F, 0.82F, 1.0F, alpha);

      for (int i = 0; i < 10; i++) {
         double angle = age * (0.052 + i % 3 * 0.009) + (Math.PI * 2) * i / 10.0 + hash(seed, i) * 0.52;
         double orbit = radius * (1.28 + hash(seed, i + 50) * 0.28);
         Vec3 point = center.add(Math.cos(angle) * orbit, Math.sin(age * 0.12 + i) * radius * 0.48, Math.sin(angle) * orbit);
         wrote |= shard(frame, point, point.subtract(center), radius * (0.46 + hash(seed, i + 20) * 0.42), radius * 0.04, 0.96F, 0.3F, 1.0F, alpha * 0.78F);
         if ((i & 1) == 0) {
            wrote |= crossGlow(frame, point, radius * 0.22, 1.0F, 0.54F, 1.0F, alpha * 0.38F);
         }
      }

      return wrote;
   }

   public static boolean bloomBeam(BerettaVfxRenderHelper.Frame frame, Vec3 start, Vec3 end, double radius, float age, int seed, float alpha) {
      if (alpha <= 0.001F) {
         return false;
      }

      float pulse = 0.88F + 0.12F * Mth.sin(age * 0.72F + seed * 0.002F);
      boolean wrote = beam(frame, start, end, radius * 3.65, 0.05F, 0.0F, 0.18F, alpha * 0.12F);
      wrote |= beam(frame, start, end, radius * 2.65, 0.18F, 0.0F, 0.56F, alpha * 0.2F);
      wrote |= beam(frame, start, end, radius * 1.82, 0.52F, 0.0F, 1.0F, alpha * 0.38F);
      wrote |= beam(frame, start, end, radius * pulse, 0.9F, 0.1F, 1.0F, alpha * 0.94F);
      wrote |= beam(frame, start, end, radius * 0.38, 1.0F, 0.76F, 1.0F, alpha);
      wrote |= beam(frame, start, end, radius * 0.11, 1.0F, 0.98F, 1.0F, alpha);
      Vec3 direction = end.subtract(start);
      double length = direction.length();
      if (length > 0.01) {
         Vec3 normalized = direction.scale(1.0 / length);
         Vec3 lateral = side3D(normalized);
         int sparks = Math.min(22, Math.max(7, (int)(length / 3.6)));

         for (int i = 0; i < sparks; i++) {
            double progress = (i + 0.5 + age * 0.28) % sparks / sparks;
            Vec3 point = start.add(normalized.scale(length * progress));
            double sideOffset = (hash(seed, i) - 0.5) * radius * 2.8;
            point = point.add(lateral.scale(sideOffset));
            double size = radius * (0.18 + hash(seed, i + 90) * 0.26);
            wrote |= crossGlow(frame, point, size * 2.4, 0.42F, 0.0F, 1.0F, alpha * 0.18F);
            wrote |= crossGlow(frame, point, size, 1.0F, 0.42F, 1.0F, alpha * 0.46F);
         }
      }

      return wrote;
   }

   public static boolean verticalRay(BerettaVfxRenderHelper.Frame frame, Vec3 ground, double height, double radius, float age, int seed, float alpha) {
      Vec3 top = ground.add(0.0, height, 0.0);
      boolean wrote = bloomBeam(frame, top, ground, radius, age, seed, alpha);
      return wrote | bloomOrb(frame, ground.add(0.0, 0.25, 0.0), radius * 1.5, age, seed + 19, alpha * 0.72F);
   }

   public static boolean impactBloom(BerettaVfxRenderHelper.Frame frame, Vec3 center, double radius, float age, int seed, float alpha) {
      if (alpha <= 0.001F) {
         return false;
      }

      double expansion = radius * (0.65 + age * 0.2);
      boolean wrote = disk(frame, center.add(0.0, 0.02, 0.0), 0.0, expansion * 0.55, 64, 0.48F, 0.0F, 1.0F, alpha * 0.2F);
      wrote |= horizontalRing(frame, center.add(0.0, 0.04, 0.0), expansion, 0.12, 72, 0.92F, 0.22F, 1.0F, alpha);
      wrote |= horizontalRing(frame, center.add(0.0, 0.08, 0.0), expansion * 1.45, 0.08, 72, 0.62F, 0.04F, 1.0F, alpha * 0.58F);
      wrote |= pillar(frame, center, radius * (2.8 + age * 0.28), radius * 0.42, 0.72F, 0.06F, 1.0F, alpha * 0.48F);
      wrote |= bloomOrb(frame, center.add(0.0, radius * 0.52, 0.0), radius * 0.72, age, seed, alpha * 0.78F);

      for (int i = 0; i < 16; i++) {
         double angle = (Math.PI * 2) * i / 16.0 + hash(seed, i) * 0.34;
         Vec3 direction = new Vec3(Math.cos(angle), 0.45 + hash(seed, i + 40) * 1.15, Math.sin(angle));
         wrote |= shard(
            frame, center.add(0.0, 0.12, 0.0), direction, radius * (0.8 + hash(seed, i + 80) * 1.2), radius * 0.045, 0.88F, 0.14F, 1.0F, alpha * 0.82F
         );
      }

      return wrote;
   }

   public static boolean sparkField(BerettaVfxRenderHelper.Frame frame, Vec3 center, double radius, float age, int seed, int count, float alpha) {
      if (alpha <= 0.001F) {
         return false;
      }

      boolean wrote = false;

      for (int i = 0; i < count; i++) {
         double angle = hash(seed, i * 7 + 1) * Math.PI * 2.0 + age * (0.018 + hash(seed, i * 7 + 2) * 0.032);
         double distance = radius * (0.18 + hash(seed, i * 7 + 3) * 0.88);
         double y = center.y + (hash(seed, i * 7 + 4) - 0.35) * radius * 1.3 + Math.sin(age * 0.08 + i) * 0.22;
         Vec3 point = new Vec3(center.x + Math.cos(angle) * distance, y, center.z + Math.sin(angle) * distance);
         double size = radius * (0.025 + hash(seed, i * 7 + 5) * 0.035);
         wrote |= crossGlow(frame, point, size * 3.0, 0.68F, 0.04F, 1.0F, alpha * 0.18F);
         wrote |= shard(frame, point, new Vec3(Math.cos(angle), 0.8, Math.sin(angle)), size * 2.0, size * 0.18, 1.0F, 0.52F, 1.0F, alpha * 0.78F);
      }

      return wrote;
   }

   public static Vec3 side(Vec3 direction) {
      Vec3 flat = new Vec3(direction.x, 0.0, direction.z);
      if (flat.lengthSqr() < 1.0E-4) {
         return new Vec3(1.0, 0.0, 0.0);
      }

      flat = flat.normalize();
      return new Vec3(-flat.z, 0.0, flat.x);
   }

   public static Vec3 flat(Vec3 direction) {
      Vec3 value = new Vec3(direction.x, 0.0, direction.z);
      return value.lengthSqr() < 1.0E-4 ? new Vec3(0.0, 0.0, 1.0) : value.normalize();
   }

   public static Vec3 rotate(Vec3 direction, double angle) {
      Vec3 value = flat(direction);
      double cos = Math.cos(angle);
      double sin = Math.sin(angle);
      return new Vec3(value.x * cos - value.z * sin, 0.0, value.x * sin + value.z * cos).normalize();
   }

   public static float smooth(float value) {
      value = Mth.clamp(value, 0.0F, 1.0F);
      return value * value * (3.0F - 2.0F * value);
   }

   public static float envelope(float age, float start, float end, float edge) {
      float in = smooth((age - start) / Math.max(0.001F, edge));
      float out = smooth((end - age) / Math.max(0.001F, edge));
      return in * out;
   }

   public static float pulse(float age, float center, float width) {
      float distance = Math.abs(age - center);
      if (distance >= width) {
         return 0.0F;
      }

      float x = 1.0F - distance / width;
      return x * x;
   }

   public static double hash(int seed, int index) {
      int value = seed ^ index * -1640531527;
      value ^= value >>> 16;
      value *= 2146121005;
      value ^= value >>> 15;
      value *= -2073254261;
      value ^= value >>> 16;
      return (value & 2147483647) / 2.147483647E9;
   }

   private static Vec3 side3D(Vec3 direction) {
      Vec3 reference = Math.abs(direction.y) > 0.92 ? new Vec3(1.0, 0.0, 0.0) : new Vec3(0.0, 1.0, 0.0);
      Vec3 side = direction.cross(reference);
      if (side.lengthSqr() < 1.0E-4) {
         side = new Vec3(1.0, 0.0, 0.0);
      }

      return side.normalize();
   }

   private static void quad(BerettaVfxRenderHelper.Frame frame, Vec3 a, Vec3 b, Vec3 c, Vec3 d, float r, float g, float blue, float alpha) {
      addVertex(frame, a, r, g, blue, alpha);
      addVertex(frame, b, r, g, blue, alpha);
      addVertex(frame, c, r, g, blue, alpha);
      addVertex(frame, d, r, g, blue, alpha);
   }

   private static void quadGradient(
      BerettaVfxRenderHelper.Frame frame, Vec3 a, Vec3 b, Vec3 c, Vec3 d, float r, float g, float blue, float aa, float ab, float ac, float ad
   ) {
      addVertex(frame, a, r, g, blue, aa);
      addVertex(frame, b, r, g, blue, ab);
      addVertex(frame, c, r, g, blue, ac);
      addVertex(frame, d, r, g, blue, ad);
   }

   private static void addVertex(BerettaVfxRenderHelper.Frame frame, Vec3 point, float r, float g, float b, float a) {
      frame.buffer
         .addVertex(frame.matrix, (float)(point.x - frame.camera.x), (float)(point.y - frame.camera.y), (float)(point.z - frame.camera.z))
         .setColor(Mth.clamp(r, 0.0F, 1.0F), Mth.clamp(g, 0.0F, 1.0F), Mth.clamp(b, 0.0F, 1.0F), Mth.clamp(a, 0.0F, 1.0F));
   }

   public record Frame(BufferBuilder buffer, Matrix4f matrix, Vec3 camera, long gameTime, float partial) {
   }
}
