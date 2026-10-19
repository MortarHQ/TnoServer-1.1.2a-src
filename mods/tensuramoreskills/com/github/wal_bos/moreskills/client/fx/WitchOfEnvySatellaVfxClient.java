package com.github.wal_bos.moreskills.client.fx;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.Util;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent.Stage;
import net.neoforged.neoforge.common.NeoForge;
import org.joml.Matrix4f;

public final class WitchOfEnvySatellaVfxClient {
   private static final List<WitchOfEnvySatellaVfxClient.TendrilEffect> TENDRILS = new ArrayList<>();
   private static final List<WitchOfEnvySatellaVfxClient.RingEffect> RINGS = new ArrayList<>();
   private static boolean initialized;

   private WitchOfEnvySatellaVfxClient() {
   }

   public static void init() {
      if (!initialized) {
         initialized = true;
         NeoForge.EVENT_BUS.addListener(WitchOfEnvySatellaVfxClient::render);
      }
   }

   public static void spawnTendrils(Vec3 start, Vec3 end, int casterId, int targetId, int count, int life, boolean mastered, long seed) {
      spawn(0, start, end, casterId, targetId, count, life, mastered, seed);
   }

   public static void spawnShadowHand(Vec3 start, Vec3 end, int casterId, int targetId, int life, boolean mastered, long seed) {
      spawn(1, start, end, casterId, targetId, 1, life, mastered, seed);
   }

   public static void spawnShadowHands(Vec3 start, Vec3 end, int casterId, int targetId, int count, int life, boolean mastered, long seed) {
      spawn(2, start, end, casterId, targetId, count, life, mastered, seed);
   }

   public static void spawnAuthority(Vec3 start, Vec3 end, int casterId, int targetId, int life, boolean mastered, long seed) {
      spawn(3, start, end, casterId, targetId, 8, life, mastered, seed);
   }

   public static void spawnCheckpoint(Vec3 center, int targetId, int life, boolean mastered, long seed) {
      Minecraft mc = Minecraft.getInstance();
      if (mc.level != null && mc.player != null) {
         RINGS.add(new WitchOfEnvySatellaVfxClient.RingEffect(center, targetId, Math.max(20, life), mastered, seed, Util.getMillis()));

         while (RINGS.size() > 24) {
            RINGS.remove(0);
         }
      }
   }

   private static void spawn(int kind, Vec3 start, Vec3 end, int casterId, int targetId, int count, int life, boolean mastered, long seed) {
      Minecraft mc = Minecraft.getInstance();
      if (mc.level != null && mc.player != null) {
         Vec3 center = start.add(end).scale(0.5);
         if (!(mc.player.distanceToSqr(center.x, center.y, center.z) > 176400.0)) {
            int max = kind == 0 ? 64 : (kind == 2 ? 42 : 16);
            int minLife = kind != 1 && kind != 2 ? 18 : 60;
            TENDRILS.add(
               new WitchOfEnvySatellaVfxClient.TendrilEffect(
                  kind, start, end, casterId, targetId, Mth.clamp(count, 1, max), Math.max(minLife, life), mastered, seed, Util.getMillis()
               )
            );

            while (TENDRILS.size() > 56) {
               TENDRILS.remove(0);
            }
         }
      }
   }

   private static void render(RenderLevelStageEvent event) {
      if (event.getStage() == Stage.AFTER_PARTICLES) {
         Minecraft mc = Minecraft.getInstance();
         if (mc.level != null && mc.player != null) {
            long now = Util.getMillis();
            PoseStack poseStack = event.getPoseStack();
            Camera camera = event.getCamera();
            Vec3 cam = camera.getPosition();
            Matrix4f matrix = poseStack.last().pose();
            RenderSystem.enableBlend();
            RenderSystem.enableDepthTest();
            RenderSystem.disableCull();
            RenderSystem.depthMask(false);
            RenderSystem.setShader(GameRenderer::getPositionColorShader);
            RenderSystem.blendFuncSeparate(770, 771, 1, 0);
            BufferBuilder buffer = Tesselator.getInstance().begin(Mode.TRIANGLES, DefaultVertexFormat.POSITION_COLOR);
            boolean wrote = false;
            Iterator<WitchOfEnvySatellaVfxClient.RingEffect> ringIterator = RINGS.iterator();

            while (ringIterator.hasNext()) {
               WitchOfEnvySatellaVfxClient.RingEffect ring = ringIterator.next();
               if (ring.dead(now)) {
                  ringIterator.remove();
               } else {
                  wrote |= ring.render(buffer, matrix, cam, now);
               }
            }

            Iterator<WitchOfEnvySatellaVfxClient.TendrilEffect> tendrilIterator = TENDRILS.iterator();

            while (tendrilIterator.hasNext()) {
               WitchOfEnvySatellaVfxClient.TendrilEffect tendril = tendrilIterator.next();
               if (tendril.shouldRemove(mc, now)) {
                  tendrilIterator.remove();
               } else {
                  wrote |= tendril.render(buffer, matrix, cam, now);
               }
            }

            if (wrote) {
               BufferUploader.drawWithShader(buffer.buildOrThrow());
            }

            RenderSystem.depthMask(true);
            RenderSystem.enableCull();
            RenderSystem.disableBlend();
            RenderSystem.defaultBlendFunc();
         } else {
            TENDRILS.clear();
            RINGS.clear();
         }
      }
   }

   private static boolean renderArmHand(
      BufferBuilder buffer,
      Matrix4f matrix,
      Vec3 cam,
      Vec3 a,
      Vec3 b,
      Vec3 c,
      Vec3 d,
      Vec3 palm,
      WitchOfEnvySatellaVfxClient.Basis basis,
      double width,
      float alpha,
      double age,
      long seed,
      boolean giant
   ) {
      boolean wrote = renderTendril(buffer, matrix, cam, a, b, c, d, palm, width * (giant ? 1.16 : 0.94), alpha, (int)(seed & 31L), false);
      Vec3 dir = palm.subtract(d);
      if (dir.lengthSqr() < 0.001) {
         dir = basis.forward;
      }

      dir = dir.normalize();
      Vec3 right = basis.right;
      Vec3 up = basis.up;
      double palmLength = width * (giant ? 2.55 : 2.1);
      double palmHalfWidth = width * (giant ? 1.15 : 0.95);
      double palmHalfHeight = width * (giant ? 0.88 : 0.76);
      Vec3 palmCenter = palm.subtract(dir.scale(palmLength * 0.28));
      wrote |= renderPalmBlock(
         buffer, matrix, cam, palmCenter, new WitchOfEnvySatellaVfxClient.Basis(dir, right, up), palmLength, palmHalfWidth, palmHalfHeight, alpha
      );
      int fingers = 5;

      for (int f = 0; f < fingers; f++) {
         double side = (f - 2.0) * palmHalfWidth * 0.78;
         double upOffset = f != 0 && f != 4 ? palmHalfHeight * 0.08 : -palmHalfHeight * 0.25;
         double reach = palmLength * (f == 2 ? 1.75 : (f != 1 && f != 3 ? 1.28 : 1.55)) * (giant ? 1.1 : 1.0);
         double curl = Math.sin(age * 0.008 + f * 0.9 + seed * 0.01) * palmHalfWidth * 0.1;
         Vec3 fa = palmCenter.add(right.scale(side * 0.52)).add(up.scale(upOffset));
         Vec3 fb = fa.add(dir.scale(reach * 0.34)).add(right.scale(side * 0.1)).add(up.scale(palmHalfHeight * 0.24));
         Vec3 fc = fa.add(dir.scale(reach * 0.7)).add(right.scale(curl)).add(up.scale(palmHalfHeight * 0.03));
         Vec3 fd = fa.add(dir.scale(reach)).add(right.scale(curl * 1.15)).subtract(up.scale(palmHalfHeight * 0.12));
         double fw = width * (f == 2 ? 0.48 : 0.42) * (giant ? 1.05 : 0.94);
         wrote |= renderFinger(buffer, matrix, cam, fa, fb, fc, fd, fw, alpha * 0.92F, f);
      }

      Vec3 thumbBase = palmCenter.subtract(right.scale(palmHalfWidth * 0.95)).subtract(up.scale(palmHalfHeight * 0.1));
      Vec3 thumbMidA = thumbBase.add(dir.scale(palmLength * 0.32)).subtract(right.scale(palmHalfWidth * 0.24));
      Vec3 thumbMidB = thumbBase.add(dir.scale(palmLength * 0.66)).subtract(right.scale(palmHalfWidth * 0.44));
      Vec3 thumbEnd = thumbBase.add(dir.scale(palmLength * 0.98)).subtract(right.scale(palmHalfWidth * 0.58)).subtract(up.scale(palmHalfHeight * 0.15));
      return wrote | renderFinger(buffer, matrix, cam, thumbBase, thumbMidA, thumbMidB, thumbEnd, width * 0.44, alpha * 0.88F, 6);
   }

   private static boolean renderFinger(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 a, Vec3 b, Vec3 c, Vec3 d, double width, float alpha, int index) {
      int segments = 14;
      Vec3[] points = new Vec3[segments + 1];
      double[] radii = new double[segments + 1];

      for (int i = 0; i <= segments; i++) {
         double p = (double)i / segments;
         points[i] = cubic(a, b, c, d, p);
         radii[i] = width * Math.pow(Math.max(0.0, 1.0 - p), 1.45);
      }

      return renderTube(buffer, matrix, cam, points, radii, alpha * 0.95F, 9, 104, 0, 144, 9, 0, 22, 0.82);
   }

   private static boolean renderTendril(
      BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 a, Vec3 b, Vec3 c, Vec3 d, Vec3 e, double width, float alpha, int index, boolean authority
   ) {
      return renderSmoothTendrilTube(buffer, matrix, cam, a, b, c, d, e, width, alpha, 38, 1.18);
   }

   private static boolean renderSmoothTendrilTube(
      BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 a, Vec3 b, Vec3 c, Vec3 d, Vec3 e, double width, float alpha, int segments, double taperPower
   ) {
      Vec3[] points = new Vec3[segments + 1];
      double[] radii = new double[segments + 1];

      for (int i = 0; i <= segments; i++) {
         double p = (double)i / segments;
         points[i] = quintic(a, b, c, d, e, p);
         radii[i] = tendrilRadius(width, p, taperPower);
      }

      return renderTube(buffer, matrix, cam, points, radii, alpha, 10, 104, 0, 144, 9, 0, 22, 0.82);
   }

   private static double tendrilRadius(double width, double p, double taperPower) {
      double taper = Math.pow(Math.max(0.0, 1.0 - p), taperPower);
      return width * taper;
   }

   private static boolean renderTube(
      BufferBuilder buffer,
      Matrix4f matrix,
      Vec3 cam,
      Vec3[] points,
      double[] radii,
      float alpha,
      int sides,
      int outlineR,
      int outlineG,
      int outlineB,
      int bodyR,
      int bodyG,
      int bodyB,
      double bodyScale
   ) {
      boolean wrote = false;
      wrote |= tubeSurface(buffer, matrix, cam, points, radii, sides, outlineR, outlineG, outlineB, alpha * 0.8F, 1.0);
      return wrote | tubeSurface(buffer, matrix, cam, points, radii, sides, bodyR, bodyG, bodyB, alpha * 0.99F, bodyScale);
   }

   private static boolean tubeSurface(
      BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3[] points, double[] radii, int sides, int r, int g, int b, float alpha, double scale
   ) {
      if (points.length < 2) {
         return false;
      }

      Vec3 forward = points[points.length - 1].subtract(points[0]);
      if (forward.lengthSqr() < 1.0E-4) {
         forward = points[1].subtract(points[0]);
      }

      if (forward.lengthSqr() < 1.0E-4) {
         return false;
      }

      forward = forward.normalize();
      Vec3 mid = points[points.length / 2];
      Vec3 toCam = cam.subtract(mid);
      Vec3 right = forward.cross(toCam);
      if (right.lengthSqr() < 1.0E-4) {
         right = forward.cross(new Vec3(0.0, 1.0, 0.0));
      }

      if (right.lengthSqr() < 1.0E-4) {
         right = new Vec3(1.0, 0.0, 0.0);
      }

      right = right.normalize();
      Vec3 up = right.cross(forward);
      if (up.lengthSqr() < 1.0E-4) {
         up = new Vec3(0.0, 1.0, 0.0);
      }

      up = up.normalize();
      Vec3[][] rings = new Vec3[points.length][sides];

      for (int i = 0; i < points.length; i++) {
         double camDistance = Math.sqrt(points[i].distanceToSqr(cam));
         double nearScale = 1.0;
         if (camDistance < 0.72) {
            nearScale = 0.0;
         } else if (camDistance < 3.25) {
            nearScale = Mth.clamp((camDistance - 0.72) / 2.53, 0.0, 1.0);
         }

         double radius = radii[i] * scale * (0.55 + nearScale * 0.45);

         for (int s = 0; s < sides; s++) {
            double angle = (Math.PI * 2) * s / sides;
            rings[i][s] = points[i].add(right.scale(Math.cos(angle) * radius)).add(up.scale(Math.sin(angle) * radius));
         }
      }

      boolean wrote = false;

      for (int i = 0; i < points.length - 1; i++) {
         float localAlpha = alpha;
         double d0 = Math.sqrt(points[i].distanceToSqr(cam));
         double d1 = Math.sqrt(points[i + 1].distanceToSqr(cam));
         double near = Math.min(d0, d1);
         if (!(near < 0.72)) {
            if (near < 3.25) {
               localAlpha *= (float)Mth.clamp((near - 0.72) / 2.53, 0.0, 1.0);
            }

            for (int s = 0; s < sides; s++) {
               int next = (s + 1) % sides;
               wrote |= quad(buffer, matrix, cam, rings[i][s], rings[i][next], rings[i + 1][next], rings[i + 1][s], r, g, b, localAlpha);
            }
         }
      }

      return wrote;
   }

   private static boolean auraBurst(
      BufferBuilder buffer,
      Matrix4f matrix,
      Vec3 cam,
      Vec3 center,
      WitchOfEnvySatellaVfxClient.Basis basis,
      float live,
      double age,
      boolean mastered,
      double scale
   ) {
      boolean wrote = false;
      int ribbons = mastered ? 10 : 7;

      for (int i = 0; i < ribbons; i++) {
         double a = i * Math.PI * 2.0 / ribbons + age * 0.0024;
         double r0 = (0.45 + Math.sin(age * 0.006 + i) * 0.08) * scale;
         double r1 = (1.55 + i * 0.035) * scale;
         Vec3 p0 = center.add(basis.right.scale(Math.cos(a) * r0)).add(basis.up.scale(Math.sin(a) * r0));
         Vec3 p1 = center.add(basis.right.scale(Math.cos(a + 0.25) * r1)).add(basis.up.scale(Math.sin(a + 0.25) * r1));
         wrote |= beam(buffer, matrix, cam, p0, p1, 0.035 * scale, 255, 20, 235, live * 0.25F);
      }

      return wrote;
   }

   private static boolean renderClawTip(
      BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 tip, WitchOfEnvySatellaVfxClient.Basis basis, double size, float alpha, double age, long seed
   ) {
      Vec3 forward = basis.forward;
      Vec3 right = basis.right;
      Vec3 up = basis.up;
      Vec3 center = tip.subtract(forward.scale(size * 0.24));
      boolean wrote = orb(buffer, matrix, cam, center, right, up, size * 0.78, 244, 194, 255, alpha * 0.28F, 14);
      wrote |= orb(buffer, matrix, cam, center, right, up, size * 0.52, 34, 0, 52, alpha * 0.95F, 14);
      wrote |= tri(
         buffer,
         matrix,
         cam,
         tip.add(forward.scale(size * 0.6)),
         center.add(right.scale(size * 0.22)),
         center.subtract(right.scale(size * 0.22)),
         34,
         0,
         52,
         alpha * 0.95F
      );
      return wrote
         | tri(
            buffer,
            matrix,
            cam,
            tip.add(forward.scale(size * 0.76)),
            center.add(right.scale(size * 0.1)),
            center.subtract(right.scale(size * 0.1)),
            244,
            194,
            255,
            alpha * 0.26F
         );
   }

   private static boolean renderPalmBlock(
      BufferBuilder buffer,
      Matrix4f matrix,
      Vec3 cam,
      Vec3 center,
      WitchOfEnvySatellaVfxClient.Basis basis,
      double length,
      double halfWidth,
      double halfHeight,
      float alpha
   ) {
      boolean wrote = false;
      wrote |= orientedBox(
         buffer, matrix, cam, center, basis.forward, basis.right, basis.up, length * 0.56, halfWidth * 1.12, halfHeight * 1.12, 118, 22, 156, alpha * 0.66F
      );
      wrote |= orientedBox(
         buffer, matrix, cam, center, basis.forward, basis.right, basis.up, length * 0.46, halfWidth * 0.92, halfHeight * 0.92, 15, 0, 26, alpha * 0.98F
      );
      return wrote
         | orientedBox(
            buffer, matrix, cam, center, basis.forward, basis.right, basis.up, length * 0.24, halfWidth * 0.44, halfHeight * 0.44, 68, 10, 104, alpha * 0.18F
         );
   }

   private static boolean orientedBox(
      BufferBuilder buffer,
      Matrix4f matrix,
      Vec3 cam,
      Vec3 center,
      Vec3 forward,
      Vec3 right,
      Vec3 up,
      double halfLength,
      double halfWidth,
      double halfHeight,
      int r,
      int g,
      int b,
      float alpha
   ) {
      Vec3 f = forward.normalize().scale(halfLength);
      Vec3 ri = right.normalize().scale(halfWidth);
      Vec3 u = up.normalize().scale(halfHeight);
      Vec3 p000 = center.subtract(f).subtract(ri).subtract(u);
      Vec3 p001 = center.subtract(f).subtract(ri).add(u);
      Vec3 p010 = center.subtract(f).add(ri).subtract(u);
      Vec3 p011 = center.subtract(f).add(ri).add(u);
      Vec3 p100 = center.add(f).subtract(ri).subtract(u);
      Vec3 p101 = center.add(f).subtract(ri).add(u);
      Vec3 p110 = center.add(f).add(ri).subtract(u);
      Vec3 p111 = center.add(f).add(ri).add(u);
      boolean wrote = false;
      wrote |= quad(buffer, matrix, cam, p101, p111, p110, p100, r, g, b, alpha);
      wrote |= quad(buffer, matrix, cam, p001, p000, p010, p011, r, g, b, alpha * 0.92F);
      wrote |= quad(buffer, matrix, cam, p011, p111, p110, p010, r, g, b, alpha * 0.88F);
      wrote |= quad(buffer, matrix, cam, p001, p101, p100, p000, r, g, b, alpha * 0.88F);
      wrote |= quad(buffer, matrix, cam, p001, p011, p111, p101, r, g, b, alpha * 0.8F);
      return wrote | quad(buffer, matrix, cam, p000, p100, p110, p010, r, g, b, alpha * 0.8F);
   }

   private static boolean orb(
      BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 center, Vec3 right, Vec3 up, double radius, int r, int g, int b, float alpha, int parts
   ) {
      boolean wrote = false;

      for (int i = 0; i < parts; i++) {
         double a0 = i * Math.PI * 2.0 / parts;
         double a1 = (i + 1) * Math.PI * 2.0 / parts;
         Vec3 p0 = center.add(right.scale(Math.cos(a0) * radius)).add(up.scale(Math.sin(a0) * radius));
         Vec3 p1 = center.add(right.scale(Math.cos(a1) * radius)).add(up.scale(Math.sin(a1) * radius));
         wrote |= tri(buffer, matrix, cam, center, p0, p1, r, g, b, alpha);
      }

      return wrote;
   }

   private static boolean ring(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 center, double radius, double width, int r, int g, int b, float alpha) {
      boolean wrote = false;
      int parts = 64;

      for (int i = 0; i < parts; i++) {
         double a0 = i * Math.PI * 2.0 / parts;
         double a1 = (i + 1) * Math.PI * 2.0 / parts;
         Vec3 p0 = center.add(Math.cos(a0) * radius, 0.0, Math.sin(a0) * radius);
         Vec3 p1 = center.add(Math.cos(a1) * radius, 0.0, Math.sin(a1) * radius);
         Vec3 q1 = center.add(Math.cos(a1) * (radius + width), 0.0, Math.sin(a1) * (radius + width));
         Vec3 q0 = center.add(Math.cos(a0) * (radius + width), 0.0, Math.sin(a0) * (radius + width));
         wrote |= quad(buffer, matrix, cam, p0, p1, q1, q0, r, g, b, alpha);
      }

      return wrote;
   }

   private static Vec3 curve(Vec3 a, Vec3 b, Vec3 c, double t) {
      return a.lerp(b, t).lerp(b.lerp(c, t), t);
   }

   private static Vec3 cubic(Vec3 a, Vec3 b, Vec3 c, Vec3 d, double t) {
      Vec3 ab = a.lerp(b, t);
      Vec3 bc = b.lerp(c, t);
      Vec3 cd = c.lerp(d, t);
      return ab.lerp(bc, t).lerp(bc.lerp(cd, t), t);
   }

   private static Vec3 quintic(Vec3 a, Vec3 b, Vec3 c, Vec3 d, Vec3 e, double t) {
      Vec3 ab = a.lerp(b, t);
      Vec3 bc = b.lerp(c, t);
      Vec3 cd = c.lerp(d, t);
      Vec3 de = d.lerp(e, t);
      Vec3 abc = ab.lerp(bc, t);
      Vec3 bcd = bc.lerp(cd, t);
      Vec3 cde = cd.lerp(de, t);
      return abc.lerp(bcd, t).lerp(bcd.lerp(cde, t), t);
   }

   private static WitchOfEnvySatellaVfxClient.Basis basis(Vec3 forward, Vec3 rightHint, Vec3 upHint) {
      Vec3 f = forward.lengthSqr() < 0.001 ? new Vec3(0.0, 0.0, 1.0) : forward.normalize();
      Vec3 r = rightHint.subtract(f.scale(rightHint.dot(f)));
      if (r.lengthSqr() < 0.001) {
         Vec3 worldUp = Math.abs(f.y) > 0.92 ? new Vec3(1.0, 0.0, 0.0) : new Vec3(0.0, 1.0, 0.0);
         r = f.cross(worldUp);
      }

      if (r.lengthSqr() < 0.001) {
         r = new Vec3(1.0, 0.0, 0.0);
      }

      r = r.normalize();
      Vec3 u = r.cross(f);
      if (u.lengthSqr() < 0.001) {
         u = upHint;
      }

      if (u.lengthSqr() < 0.001) {
         u = new Vec3(0.0, 1.0, 0.0);
      }

      return new WitchOfEnvySatellaVfxClient.Basis(f, r, u.normalize());
   }

   private static boolean beam(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 start, Vec3 end, double width, int r, int g, int b, float alpha) {
      Vec3 dir = end.subtract(start);
      if (dir.lengthSqr() < 1.0E-4) {
         return false;
      }

      Vec3 mid = start.add(end).scale(0.5);
      double camDistance = Math.sqrt(mid.distanceToSqr(cam));
      if (camDistance < 1.1) {
         return false;
      }

      if (camDistance < 4.75) {
         float near = Mth.clamp((float)((camDistance - 1.1) / 3.65), 0.0F, 1.0F);
         alpha *= near * near;
         width *= 0.34 + near * 0.66;
      }

      Vec3 toCam = cam.subtract(mid);
      Vec3 side = dir.cross(toCam);
      if (side.lengthSqr() < 1.0E-4) {
         side = dir.cross(new Vec3(0.0, 1.0, 0.0));
      }

      if (side.lengthSqr() < 1.0E-4) {
         side = new Vec3(1.0, 0.0, 0.0);
      }

      side = side.normalize().scale(width);
      return quad(buffer, matrix, cam, start.add(side), end.add(side), end.subtract(side), start.subtract(side), r, g, b, alpha);
   }

   private static boolean quad(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 a, Vec3 b, Vec3 c, Vec3 d, int r, int g, int bl, float alpha) {
      int ia = Mth.clamp((int)(alpha * 255.0F), 0, 255);
      if (ia <= 0) {
         return false;
      }

      vertex(buffer, matrix, cam, a, r, g, bl, ia);
      vertex(buffer, matrix, cam, b, r, g, bl, ia);
      vertex(buffer, matrix, cam, c, r, g, bl, ia);
      vertex(buffer, matrix, cam, a, r, g, bl, ia);
      vertex(buffer, matrix, cam, c, r, g, bl, ia);
      vertex(buffer, matrix, cam, d, r, g, bl, ia);
      return true;
   }

   private static boolean tri(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 a, Vec3 b, Vec3 c, int r, int g, int bl, float alpha) {
      int ia = Mth.clamp((int)(alpha * 255.0F), 0, 255);
      if (ia <= 0) {
         return false;
      }

      vertex(buffer, matrix, cam, a, r, g, bl, ia);
      vertex(buffer, matrix, cam, b, r, g, bl, ia);
      vertex(buffer, matrix, cam, c, r, g, bl, ia);
      return true;
   }

   private static void vertex(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 p, int r, int g, int b, int a) {
      buffer.addVertex(matrix, (float)(p.x - cam.x), (float)(p.y - cam.y), (float)(p.z - cam.z))
         .setColor(Mth.clamp(r, 0, 255), Mth.clamp(g, 0, 255), Mth.clamp(b, 0, 255), a);
   }

   private static float smooth(float value) {
      value = Mth.clamp(value, 0.0F, 1.0F);
      return value * value * (3.0F - 2.0F * value);
   }

   private static float hash(long seed, long index) {
      long x = seed + index * -7046029254386353131L;
      x ^= x >>> 30;
      x *= -4658895280553007687L;
      x ^= x >>> 27;
      x *= -7723592293110705685L;
      x ^= x >>> 31;
      return (float)(x >>> 40 & 16777215L) / 1.6777215E7F;
   }

   private record Basis(Vec3 forward, Vec3 right, Vec3 up) {
   }

   private record RingEffect(Vec3 fallback, int targetId, int life, boolean mastered, long seed, long created) {
      private boolean dead(long now) {
         return now - this.created > this.life * 50L;
      }

      private boolean render(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, long now) {
         Minecraft mc = Minecraft.getInstance();
         Vec3 center = WitchOfEnvySatellaVfxClient.TendrilEffect.resolve(mc, this.fallback, this.targetId, 0.1);
         double age = now - this.created;
         float t = Mth.clamp((float)(age / (this.life * 50.0)), 0.0F, 1.0F);
         float live = WitchOfEnvySatellaVfxClient.smooth(Mth.clamp(t / 0.14F, 0.0F, 1.0F))
            * (1.0F - WitchOfEnvySatellaVfxClient.smooth(Mth.clamp((t - 0.72F) / 0.28F, 0.0F, 1.0F)));
         boolean wrote = false;
         int rings = this.mastered ? 5 : 3;

         for (int r = 0; r < rings; r++) {
            double spin = age * 0.0026 + r * 0.72;
            double radius = 0.9 + r * 0.5 + Math.sin(age * 0.007 + r) * 0.07;
            wrote |= WitchOfEnvySatellaVfxClient.ring(
               buffer, matrix, cam, center.add(0.0, 0.04 + r * 0.03, 0.0), radius, 0.06, 20, 0, 28, live * (0.55F - r * 0.07F)
            );
            wrote |= WitchOfEnvySatellaVfxClient.ring(
               buffer,
               matrix,
               cam,
               center.add(Math.cos(spin) * 0.03, 0.055 + r * 0.03, Math.sin(spin) * 0.03),
               radius * 0.72,
               0.03,
               255,
               36,
               230,
               live * (0.31F - r * 0.035F)
            );
         }

         return wrote;
      }
   }

   private static final class TendrilEffect {
      private final int kind;
      private final Vec3 fallbackStart;
      private final Vec3 fallbackEnd;
      private final int casterId;
      private final int targetId;
      private final int count;
      private final int life;
      private final boolean mastered;
      private final long seed;
      private final long created;
      private long targetGoneSince = -1L;

      private TendrilEffect(
         int kind, Vec3 fallbackStart, Vec3 fallbackEnd, int casterId, int targetId, int count, int life, boolean mastered, long seed, long created
      ) {
         this.kind = kind;
         this.fallbackStart = fallbackStart;
         this.fallbackEnd = fallbackEnd;
         this.casterId = casterId;
         this.targetId = targetId;
         this.count = count;
         this.life = life;
         this.mastered = mastered;
         this.seed = seed;
         this.created = created;
      }

      private boolean dead(long now) {
         return now - this.created > this.life * 50L;
      }

      private boolean shouldRemove(Minecraft mc, long now) {
         if (this.dead(now)) {
            return true;
         }

         if (this.targetId != -1 && mc.level != null) {
            Entity entity = entity(mc, this.targetId);
            boolean gone = entity == null || !entity.isAlive();
            if (!gone) {
               this.targetGoneSince = -1L;
               return false;
            }

            if (this.targetGoneSince < 0L) {
               this.targetGoneSince = now;
            }

            return now - this.targetGoneSince >= 1000L;
         } else {
            return false;
         }
      }

      private boolean targetDead(Minecraft mc) {
         Entity entity = entity(mc, this.targetId);
         return this.targetId != -1 && this.kind != 4 && (entity == null || !entity.isAlive());
      }

      private boolean renderSatellaTendrils(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, long now) {
         Minecraft mc = Minecraft.getInstance();
         double age = now - this.created;
         float t = Mth.clamp((float)(age / (this.life * 50.0)), 0.0F, 1.0F);
         float birth = WitchOfEnvySatellaVfxClient.smooth(Mth.clamp(t / 0.075F, 0.0F, 1.0F));
         float fade = 1.0F - WitchOfEnvySatellaVfxClient.smooth(Mth.clamp((t - 0.82F) / 0.18F, 0.0F, 1.0F));
         float live = birth * fade;
         if (live <= 0.002F) {
            return false;
         }

         Vec3 start = this.fallbackStart;
         Vec3 end = resolve(mc, this.fallbackEnd, this.targetId, 0.62);
         if (start.distanceToSqr(end) < 0.01) {
            return false;
         }

         WitchOfEnvySatellaVfxClient.Basis base = WitchOfEnvySatellaVfxClient.basis(end.subtract(start), new Vec3(1.0, 0.0, 0.0), new Vec3(0.0, 1.0, 0.0));
         boolean wrote = false;
         int tendrils = this.mastered ? Math.max(this.count, 10) : Math.max(this.count, 5);
         double sideSpread = this.mastered ? 14.0 : 10.2;
         double back = this.mastered ? 9.4 : 7.2;

         for (int i = 0; i < tendrils; i++) {
            float h = WitchOfEnvySatellaVfxClient.hash(this.seed, i * 97L + 11L);
            float local = WitchOfEnvySatellaVfxClient.smooth(Mth.clamp(birth * 1.72F - h * 0.09F, 0.0F, 1.0F));
            if (!(local <= 0.001F)) {
               double lane = (i - (tendrils - 1) * 0.5) * (sideSpread / Math.max(1.0, tendrils - 1.0));

               double streamHeight = switch (i % 5) {
                  case 0 -> this.mastered ? 2.6 : 1.8;
                  case 1 -> this.mastered ? 0.4 : 0.2;
                  case 2 -> this.mastered ? 3.7 : 2.6;
                  case 3 -> this.mastered ? -0.2 : -0.1;
                  default -> this.mastered ? 1.7 : 1.1;
               };
               Vec3 root = start.subtract(base.forward.scale(back + h * 2.1)).add(base.right.scale(lane)).add(base.up.scale(streamHeight));
               Vec3 target = end.add(base.right.scale(lane * 0.16)).add(base.up.scale(streamHeight * 0.1 + (h - 0.5) * 1.1));
               Vec3 mid = start.lerp(end, 0.46)
                  .add(base.right.scale(lane * 0.54))
                  .add(base.up.scale(streamHeight + Math.sin(local * Math.PI) * (this.mastered ? 3.2 : 2.3)));
               double wave = Math.sin(age * 0.0068 + h * 8.0) * Math.sin(local * Math.PI);
               Vec3 head = WitchOfEnvySatellaVfxClient.curve(root, mid, target, local).add(base.right.scale(wave * 0.32));
               Vec3 p1 = WitchOfEnvySatellaVfxClient.curve(root, mid, target, Math.max(0.0, local - 0.33)).add(base.right.scale(wave * 0.26));
               Vec3 p2 = WitchOfEnvySatellaVfxClient.curve(root, mid, target, Math.max(0.0, local - 0.2)).add(base.right.scale(wave * 0.48));
               Vec3 p3 = WitchOfEnvySatellaVfxClient.curve(root, mid, target, Math.max(0.0, local - 0.09)).add(base.right.scale(wave * 0.62));
               double scale = (this.mastered ? 0.84 : 0.66) + h * 0.14;
               float alpha = live * Mth.clamp(local * 1.7F, 0.0F, 1.0F) * (0.78F + h * 0.22F);
               wrote |= WitchOfEnvySatellaVfxClient.renderTendril(buffer, matrix, cam, root, p1, p2, p3, head, scale, alpha, i, false);
            }
         }

         return wrote;
      }

      private boolean render(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, long now) {
         if (this.kind == 0) {
            return this.renderSatellaTendrils(buffer, matrix, cam, now);
         }

         double age = now - this.created;
         float t = Mth.clamp((float)(age / (this.life * 50.0)), 0.0F, 1.0F);
         float birth = WitchOfEnvySatellaVfxClient.smooth(Mth.clamp(t / (this.kind == 1 ? 0.56F : (this.kind == 2 ? 0.2F : 0.13F)), 0.0F, 1.0F));
         float fade = 1.0F - WitchOfEnvySatellaVfxClient.smooth(Mth.clamp((t - 0.84F) / 0.16F, 0.0F, 1.0F));
         float live = birth * fade;
         if (live <= 0.002F) {
            return false;
         }

         Minecraft mc = Minecraft.getInstance();
         Entity casterEntity = entity(mc, this.casterId);
         Vec3 start = resolve(mc, this.fallbackStart, this.casterId, 0.62);
         Vec3 end = resolve(mc, this.fallbackEnd, this.targetId, 0.56);
         if (start.distanceToSqr(end) < 0.01) {
            return false;
         }

         WitchOfEnvySatellaVfxClient.Basis chaseBasis = WitchOfEnvySatellaVfxClient.basis(end.subtract(start), new Vec3(1.0, 0.0, 0.0), new Vec3(0.0, 1.0, 0.0));
         WitchOfEnvySatellaVfxClient.Basis casterBasis = casterBasis(casterEntity, chaseBasis);
         boolean ownFirstPerson = casterEntity == mc.player && mc.options.getCameraType().isFirstPerson();
         boolean wrote = false;
         int total = this.kind == 1 ? 1 : this.count;
         double spread = this.kind == 1 ? 0.0 : (this.kind == 3 ? 8.6 : (this.kind == 2 ? (this.mastered ? 16.0 : 12.6) : (this.mastered ? 18.0 : 12.5)));
         double speed = this.kind == 1 ? 0.64 : (this.kind == 2 ? 1.62 : 1.94);

         for (int i = 0; i < total; i++) {
            float h = WitchOfEnvySatellaVfxClient.hash(this.seed, i * 97L + 11L);
            float h2 = WitchOfEnvySatellaVfxClient.hash(this.seed, i * 131L + 43L);
            float local = WitchOfEnvySatellaVfxClient.smooth(Mth.clamp(birth * (float)speed - h * 0.14F, 0.0F, 1.0F));
            if (!(local <= 0.001F)) {
               double lane = total <= 1 ? 0.0 : (i - (total - 1) * 0.5) * (spread / Math.max(1.0, total - 1.0));
               double laneNorm = spread <= 1.0E-4 ? 0.0 : lane / spread;
               double socketAngle = this.kind == 1 ? Math.PI * (0.24 + h * 0.18) : i * 2.399963229728653 + h * Math.PI * 2.0 + age * 7.0E-4;
               double sideScale = ownFirstPerson
                  ? (this.kind == 1 ? 3.15 : (this.kind == 2 ? 4.7 : (this.kind == 3 ? 5.4 : 4.85)))
                  : (this.kind == 1 ? 1.3 : (this.kind == 2 ? 2.55 : (this.kind == 3 ? 3.05 : 2.7)));
               double upScale = ownFirstPerson
                  ? (this.kind == 1 ? 1.15 : (this.kind == 2 ? 2.25 : 2.75))
                  : (this.kind == 1 ? 0.55 : (this.kind == 2 ? 1.2 : 1.45));
               Vec3 radial = casterBasis.right.scale(Math.cos(socketAngle)).add(casterBasis.up.scale(Math.sin(socketAngle) * 0.82)).normalize();
               Vec3 backCenter = start.subtract(casterBasis.forward.scale(ownFirstPerson ? (this.kind == 1 ? 2.35 : 3.15) : (this.kind == 1 ? 1.15 : 1.65)))
                  .add(casterBasis.up.scale(ownFirstPerson ? (this.kind == 1 ? -0.28 : -0.18) : (this.kind == 1 ? 0.02 : 0.12)));
               Vec3 root = backCenter.add(radial.scale(sideScale + h * (this.kind == 1 ? 0.12 : 0.22)))
                  .add(casterBasis.right.scale(laneNorm * (this.kind == 1 ? 0.18 : (this.kind == 2 ? 0.66 : 0.82))))
                  .add(casterBasis.up.scale((h2 - 0.5) * upScale));
               double distance = Math.sqrt(root.distanceToSqr(end));
               Vec3 target = end.add(chaseBasis.right.scale((h2 - 0.5) * (this.kind == 1 ? 0.22 : 0.56) + laneNorm * 0.22))
                  .add(chaseBasis.up.scale((h - 0.5) * (this.kind == 1 ? 0.3 : 0.66)));
               double curveOut = (
                     ownFirstPerson
                        ? (this.kind == 1 ? 5.6 : (this.kind == 2 ? 8.2 : (this.kind == 3 ? 9.35 : 8.6)))
                        : (this.kind == 1 ? 2.45 : (this.kind == 2 ? 4.6 : (this.kind == 3 ? 5.35 : 4.95)))
                  )
                  + h * (ownFirstPerson ? (this.kind == 1 ? 1.5 : 2.9) : (this.kind == 1 ? 0.75 : 1.65));
               double liftArc = (
                     ownFirstPerson ? (this.kind == 1 ? 1.55 : (this.kind == 2 ? 2.85 : 3.35)) : (this.kind == 1 ? 0.8 : (this.kind == 2 ? 1.6 : 1.95))
                  )
                  + Math.abs(Math.sin(socketAngle)) * (ownFirstPerson ? (this.kind == 1 ? 0.85 : 1.9) : (this.kind == 1 ? 0.38 : 1.1))
                  + (this.mastered ? 0.3 : 0.0);
               Vec3 mid1 = root.add(radial.scale(curveOut))
                  .subtract(casterBasis.forward.scale(this.kind == 1 ? 0.1 : 0.18))
                  .add(chaseBasis.forward.scale(distance * (ownFirstPerson ? (this.kind == 1 ? 0.08 : 0.1) : (this.kind == 1 ? 0.16 : 0.18))))
                  .add(casterBasis.up.scale(liftArc));
               Vec3 mid2 = start.add(radial.scale(curveOut * (ownFirstPerson ? (this.kind == 1 ? 1.08 : 1.22) : (this.kind == 1 ? 0.86 : 0.98))))
                  .add(chaseBasis.forward.scale(distance * (ownFirstPerson ? (this.kind == 1 ? 0.44 : 0.38) : (this.kind == 1 ? 0.56 : 0.5))))
                  .add(chaseBasis.right.scale(laneNorm * (this.kind == 1 ? 0.1 : (this.kind == 2 ? 0.95 : 1.08))))
                  .add(chaseBasis.up.scale((this.kind == 1 ? 0.18 : 0.44) + Math.sin(age * 0.0045 + h * 10.0) * 0.22));
               double pulse = Math.sin(age * 0.013 + h * 19.0) * 0.5 + 0.5;
               double wave = Math.sin(age * 0.0078 + h * 11.0) * Math.sin(local * Math.PI);
               Vec3 waveOffset = radial.scale(wave * (ownFirstPerson ? (this.kind == 1 ? 0.42 : 0.95) : (this.kind == 1 ? 0.24 : 0.56)))
                  .add(chaseBasis.up.scale(Math.sin(age * 0.005 + i) * (ownFirstPerson ? 0.28 : 0.14)));
               Vec3 head = WitchOfEnvySatellaVfxClient.cubic(root, mid1, mid2, target, local).add(waveOffset);
               Vec3 p1 = WitchOfEnvySatellaVfxClient.cubic(root, mid1, mid2, target, Math.max(0.0, local - 0.4)).add(radial.scale(wave * 0.1));
               Vec3 p2 = WitchOfEnvySatellaVfxClient.cubic(root, mid1, mid2, target, Math.max(0.0, local - 0.25)).add(radial.scale(wave * 0.18));
               Vec3 p3 = WitchOfEnvySatellaVfxClient.cubic(root, mid1, mid2, target, Math.max(0.0, local - 0.11)).add(radial.scale(wave * 0.26));
               WitchOfEnvySatellaVfxClient.Basis segmentBasis = WitchOfEnvySatellaVfxClient.basis(head.subtract(p3), radial, casterBasis.up);
               double width = ((this.kind == 1 ? 1.16 : (this.kind == 2 ? 0.86 : (this.mastered ? 0.82 : 0.66))) + h * 0.2 + pulse * 0.1)
                  * (ownFirstPerson ? 0.58 : 0.82);
               float alpha = live * Mth.clamp(local * 1.75F, 0.0F, 1.0F) * (0.78F + h * 0.22F);
               if (this.kind == 1) {
                  wrote |= WitchOfEnvySatellaVfxClient.renderArmHand(
                     buffer, matrix, cam, root, p1, p2, p3, head, segmentBasis, width, alpha, age, this.seed + i * 71L, true
                  );
               } else if (this.kind == 2) {
                  wrote |= WitchOfEnvySatellaVfxClient.renderArmHand(
                     buffer, matrix, cam, root, p1, p2, p3, head, segmentBasis, width * 0.82, alpha * 0.94F, age, this.seed + i * 71L, false
                  );
               } else {
                  wrote |= WitchOfEnvySatellaVfxClient.renderTendril(buffer, matrix, cam, root, p1, p2, p3, head, width, alpha, i, this.kind == 3);
               }
            }
         }

         if ((this.kind == 1 || this.kind == 2) && live > 0.05F) {
            wrote |= WitchOfEnvySatellaVfxClient.auraBurst(buffer, matrix, cam, end, chaseBasis, live, age, this.mastered, this.kind == 1 ? 1.45 : 1.05);
         }

         return wrote;
      }

      private static Vec3 resolve(Minecraft mc, Vec3 fallback, int id, double height) {
         Entity entity = entity(mc, id);
         return entity != null ? entity.getPosition(1.0F).add(0.0, entity.getBbHeight() * height, 0.0) : fallback;
      }

      private static Entity entity(Minecraft mc, int id) {
         return mc.level != null && id != -1 ? mc.level.getEntity(id) : null;
      }

      private static WitchOfEnvySatellaVfxClient.Basis casterBasis(Entity entity, WitchOfEnvySatellaVfxClient.Basis fallback) {
         if (entity == null) {
            return fallback;
         }

         Vec3 look = entity.getViewVector(1.0F);
         return look.lengthSqr() < 1.0E-4 ? fallback : WitchOfEnvySatellaVfxClient.basis(look.normalize(), new Vec3(1.0, 0.0, 0.0), new Vec3(0.0, 1.0, 0.0));
      }
   }
}
