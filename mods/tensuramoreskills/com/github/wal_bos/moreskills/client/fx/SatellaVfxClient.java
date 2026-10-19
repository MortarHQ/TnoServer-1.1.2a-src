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
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent.Stage;
import net.neoforged.neoforge.common.NeoForge;
import org.joml.Matrix4f;

public final class SatellaVfxClient {
   private static final List<SatellaVfxClient.TendrilSwarm> TENDRILS = new ArrayList<>();
   private static final List<SatellaVfxClient.BlackMiasma> MIASMAS = new ArrayList<>();
   private static boolean initialized;

   private SatellaVfxClient() {
   }

   public static void init() {
      if (!initialized) {
         initialized = true;
         NeoForge.EVENT_BUS.addListener(SatellaVfxClient::render);
      }
   }

   public static void spawnHand(double x, double y, double z, int entityId, int life, boolean mastered, long seed) {
   }

   public static void spawnTendrils(Vec3 start, Vec3 end, int casterId, int targetId, int count, int life, boolean mastered, long seed) {
      Minecraft mc = Minecraft.getInstance();
      if (mc.level != null && mc.player != null) {
         Vec3 center = start.add(end).scale(0.5);
         if (!(mc.player.distanceToSqr(center.x, center.y, center.z) > 144400.0)) {
            TENDRILS.add(
               new SatellaVfxClient.TendrilSwarm(
                  start, end, casterId, targetId, Mth.clamp(count, 1, mastered ? 14 : 10), Math.max(18, life), mastered, seed, Util.getMillis()
               )
            );

            while (TENDRILS.size() > 32) {
               TENDRILS.remove(0);
            }
         }
      }
   }

   public static void spawnTendrils(Vec3 start, Vec3 end, int count, int life, boolean mastered, long seed) {
      spawnTendrils(start, end, -1, -1, count, life, mastered, seed);
   }

   public static void spawnSpread(Vec3 center, float radius, int life, boolean mastered, long seed) {
      Minecraft mc = Minecraft.getInstance();
      if (mc.level != null && mc.player != null) {
         if (!(mc.player.distanceToSqr(center.x, center.y, center.z) > 193600.0)) {
            MIASMAS.add(
               new SatellaVfxClient.BlackMiasma(mc.level, center, Mth.clamp(radius, 1.0F, 25.0F), Math.max(20, life), mastered, seed, Util.getMillis())
            );

            while (MIASMAS.size() > 4) {
               MIASMAS.remove(0);
            }
         }
      }
   }

   private static void render(RenderLevelStageEvent event) {
      if (event.getStage() == Stage.AFTER_TRANSLUCENT_BLOCKS || event.getStage() == Stage.AFTER_PARTICLES) {
         Minecraft mc = Minecraft.getInstance();
         if (mc.level != null && mc.player != null) {
            long now = Util.getMillis();
            PoseStack poseStack = event.getPoseStack();
            Camera camera = event.getCamera();
            Vec3 cam = camera.getPosition();
            Matrix4f matrix = poseStack.last().pose();
            RenderSystem.enableBlend();
            RenderSystem.enableDepthTest();
            RenderSystem.depthFunc(515);
            RenderSystem.disableCull();
            RenderSystem.depthMask(false);
            RenderSystem.setShader(GameRenderer::getPositionColorShader);
            RenderSystem.blendFuncSeparate(770, 771, 1, 0);
            BufferBuilder buffer = Tesselator.getInstance().begin(Mode.TRIANGLES, DefaultVertexFormat.POSITION_COLOR);
            boolean wrote = false;
            Iterator<SatellaVfxClient.BlackMiasma> miasmaIterator = MIASMAS.iterator();

            while (miasmaIterator.hasNext()) {
               SatellaVfxClient.BlackMiasma miasma = miasmaIterator.next();
               if (miasma.dead(now)) {
                  miasmaIterator.remove();
               } else {
                  wrote |= miasma.render(buffer, matrix, cam, now);
               }
            }

            Iterator<SatellaVfxClient.TendrilSwarm> tendrilIterator = TENDRILS.iterator();

            while (tendrilIterator.hasNext()) {
               SatellaVfxClient.TendrilSwarm tendrils = tendrilIterator.next();
               if (tendrils.shouldRemove(mc, now)) {
                  tendrilIterator.remove();
               } else {
                  wrote |= tendrils.render(buffer, matrix, cam, now);
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
            MIASMAS.clear();
         }
      }
   }

   private static boolean renderTendril(
      BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 a, Vec3 b, Vec3 c, Vec3 d, Vec3 e, double width, float alpha, int index
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

   private static boolean surfacePatch(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 center, double rx, double rz, float alpha) {
      Vec3 a = center.add(-rx, 0.0, -rz);
      Vec3 b = center.add(rx, 0.0, -rz);
      Vec3 c = center.add(rx, 0.0, rz);
      Vec3 d = center.add(-rx, 0.0, rz);
      return quad(buffer, matrix, cam, a, b, c, d, 0, 0, 0, alpha);
   }

   private static boolean smokeBillboard(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 base, double width, double height, float alpha) {
      Vec3 toCam = new Vec3(cam.x - base.x, 0.0, cam.z - base.z);
      if (toCam.lengthSqr() < 1.0E-4) {
         toCam = new Vec3(1.0, 0.0, 0.0);
      } else {
         toCam = toCam.normalize();
      }

      Vec3 side = new Vec3(-toCam.z, 0.0, toCam.x).normalize().scale(width * 0.5);
      Vec3 side2 = new Vec3(side.z, 0.0, -side.x).normalize().scale(width * 0.35);
      Vec3 up = new Vec3(0.0, height, 0.0);
      boolean wrote = false;
      wrote |= quad(
         buffer, matrix, cam, base.subtract(side), base.add(side), base.add(side.scale(0.72)).add(up), base.subtract(side.scale(0.72)).add(up), 0, 0, 0, alpha
      );
      return wrote
         | quad(
            buffer,
            matrix,
            cam,
            base.subtract(side2),
            base.add(side2),
            base.add(side2.scale(0.72)).add(up.scale(0.92)),
            base.subtract(side2.scale(0.72)).add(up.scale(0.92)),
            0,
            0,
            0,
            alpha * 0.82F
         );
   }

   private static Vec3 curve(Vec3 a, Vec3 b, Vec3 c, double t) {
      return a.lerp(b, t).lerp(b.lerp(c, t), t);
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

   private static SatellaVfxClient.Basis basis(Vec3 forward, Vec3 rightHint, Vec3 upHint) {
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

      return new SatellaVfxClient.Basis(f, r, u.normalize());
   }

   private static boolean beam(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 start, Vec3 end, double width, int r, int g, int b, float alpha) {
      Vec3 dir = end.subtract(start);
      if (dir.lengthSqr() < 1.0E-4) {
         return false;
      }

      dir = dir.normalize();
      Vec3 ref = Math.abs(dir.y) > 0.92 ? new Vec3(1.0, 0.0, 0.0) : new Vec3(0.0, 1.0, 0.0);
      Vec3 side = dir.cross(ref);
      if (side.lengthSqr() < 1.0E-4) {
         side = new Vec3(1.0, 0.0, 0.0);
      }

      side = side.normalize().scale(width);
      Vec3 side2 = dir.cross(side);
      if (side2.lengthSqr() < 1.0E-4) {
         side2 = new Vec3(0.0, 1.0, 0.0);
      }

      side2 = side2.normalize().scale(width * 0.72);
      boolean wrote = false;
      wrote |= quad(buffer, matrix, cam, start.add(side), end.add(side.scale(0.3)), end.subtract(side.scale(0.3)), start.subtract(side), r, g, b, alpha);
      return wrote
         | quad(
            buffer,
            matrix,
            cam,
            start.add(side2),
            end.add(side2.scale(0.3)),
            end.subtract(side2.scale(0.3)),
            start.subtract(side2),
            Math.min(255, r + 34),
            g,
            Math.min(255, b + 40),
            alpha * 0.7F
         );
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

   private record BlackMiasma(ClientLevel level, Vec3 center, float radius, int life, boolean mastered, long seed, long created) {
      private boolean dead(long now) {
         return now - this.created > this.life * 50L;
      }

      private boolean render(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, long now) {
         double age = now - this.created;
         float t = Mth.clamp((float)(age / (this.life * 50.0)), 0.0F, 1.0F);
         float birth = SatellaVfxClient.smooth(Mth.clamp(t / 0.22F, 0.0F, 1.0F));
         float fade = 1.0F - SatellaVfxClient.smooth(Mth.clamp((t - 0.92F) / 0.08F, 0.0F, 1.0F));
         float live = birth * fade;
         if (live <= 0.002F) {
            return false;
         }

         double outer = this.radius * (0.05 + 0.95 * birth);
         if (outer <= 0.05) {
            return false;
         }

         boolean wrote = false;
         int rings = this.mastered ? 22 : 18;
         int perRing = this.mastered ? 92 : 74;

         for (int ring = 0; ring < rings; ring++) {
            double f = (ring + 0.5) / rings;
            double ringDist = outer * f;
            int count = Math.max(20, (int)(perRing * (0.58 + f * 0.72)));

            for (int i = 0; i < count; i++) {
               float h = SatellaVfxClient.hash(this.seed, ring * 1000L + i * 41L);
               double angle = (Math.PI * 2) * i / count + h * 0.08;
               double dist = ringDist * (0.94 + h * 0.1);
               double x = this.center.x + Math.cos(angle) * dist;
               double z = this.center.z + Math.sin(angle) * dist;
               double y = this.sampleSurfaceY(x, z, this.center.y);
               double edge = dist / Math.max(outer, 0.001);
               double size = 0.72 + h * 1.05 + outer * 0.01;
               float alpha = live * (float)Mth.clamp(0.48 - edge * 0.12 + h * 0.11, 0.13, 0.55);
               wrote |= SatellaVfxClient.surfacePatch(buffer, matrix, cam, new Vec3(x, y + 0.012, z), size, size * (0.78 + h * 0.2), alpha);
               if ((i + ring) % 3 == 0) {
                  float h2 = SatellaVfxClient.hash(this.seed, ring * 3100L + i * 89L);
                  double ox = (h2 - 0.5) * size * 0.5;
                  double oz = (SatellaVfxClient.hash(this.seed, ring * 4100L + i * 97L) - 0.5) * size * 0.5;
                  wrote |= SatellaVfxClient.surfacePatch(buffer, matrix, cam, new Vec3(x + ox, y + 0.018, z + oz), size * 0.62, size * 0.52, alpha * 0.72F);
               }
            }
         }

         int smokeColumns = this.mastered ? 78 : 58;

         for (int i = 0; i < smokeColumns; i++) {
            float h = SatellaVfxClient.hash(this.seed, 50000L + i);
            double angle = h * Math.PI * 2.0;
            double dist = outer * Math.sqrt(SatellaVfxClient.hash(this.seed, 51000L + i));
            double x = this.center.x + Math.cos(angle) * dist;
            double z = this.center.z + Math.sin(angle) * dist;
            double y = this.sampleSurfaceY(x, z, this.center.y);
            double edge = dist / Math.max(outer, 0.001);
            double width = 0.42 + h * 0.95;
            double height = 1.5 + h * 2.5 + birth * 4.8;
            float alpha = live * (float)Mth.clamp(0.3 - edge * 0.1 + h * 0.08, 0.1, 0.34);
            double rise = age * 0.0012 % 1.2;
            Vec3 base = new Vec3(x, y + 0.02 + rise * 0.25, z);
            wrote |= SatellaVfxClient.smokeBillboard(buffer, matrix, cam, base, width, height, alpha);
            wrote |= SatellaVfxClient.smokeBillboard(buffer, matrix, cam, base.add(0.08, height * 0.22, -0.08), width * 0.74, height * 0.78, alpha * 0.75F);
         }

         return wrote;
      }

      private double sampleSurfaceY(double x, double z, double yHint) {
         int ix = Mth.floor(x);
         int iz = Mth.floor(z);
         MutableBlockPos pos = new MutableBlockPos();
         int startY = Mth.floor(yHint) + 14;
         int endY = Mth.floor(yHint) - 18;

         for (int y = startY; y >= endY; y--) {
            pos.set(ix, y, iz);
            BlockState state = this.level.getBlockState(pos);
            if (!state.isAir() && this.level.getBlockState(pos.above()).isAir()) {
               return y + 1.005;
            }
         }

         return yHint + 0.005;
      }
   }

   private static final class TendrilSwarm {
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

      private TendrilSwarm(Vec3 fallbackStart, Vec3 fallbackEnd, int casterId, int targetId, int count, int life, boolean mastered, long seed, long created) {
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
            Entity entity = mc.level.getEntity(this.targetId);
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
         Entity entity = mc.level != null && this.targetId != -1 ? mc.level.getEntity(this.targetId) : null;
         return this.targetId != -1 && (entity == null || !entity.isAlive());
      }

      private boolean render(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, long now) {
         Minecraft mc = Minecraft.getInstance();
         double age = now - this.created;
         float t = Mth.clamp((float)(age / (this.life * 50.0)), 0.0F, 1.0F);
         float birth = SatellaVfxClient.smooth(Mth.clamp(t / 0.075F, 0.0F, 1.0F));
         float fade = 1.0F - SatellaVfxClient.smooth(Mth.clamp((t - 0.82F) / 0.18F, 0.0F, 1.0F));
         float live = birth * fade;
         if (live <= 0.002F) {
            return false;
         }

         Vec3 start = this.fallbackStart;
         Vec3 end = resolveTarget(mc, this.fallbackEnd, this.targetId);
         if (start.distanceToSqr(end) < 0.01) {
            return false;
         }

         SatellaVfxClient.Basis base = SatellaVfxClient.basis(end.subtract(start), new Vec3(1.0, 0.0, 0.0), new Vec3(0.0, 1.0, 0.0));
         boolean wrote = false;
         int tendrils = this.mastered ? Math.max(this.count, 10) : Math.max(this.count, 5);
         double sideSpread = this.mastered ? 14.0 : 10.2;
         double back = this.mastered ? 9.4 : 7.2;

         for (int i = 0; i < tendrils; i++) {
            float h = SatellaVfxClient.hash(this.seed, i * 97L + 11L);
            float local = SatellaVfxClient.smooth(Mth.clamp(birth * 1.72F - h * 0.09F, 0.0F, 1.0F));
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
               Vec3 head = SatellaVfxClient.curve(root, mid, target, local).add(base.right.scale(wave * 0.32));
               Vec3 p1 = SatellaVfxClient.curve(root, mid, target, Math.max(0.0, local - 0.33)).add(base.right.scale(wave * 0.26));
               Vec3 p2 = SatellaVfxClient.curve(root, mid, target, Math.max(0.0, local - 0.2)).add(base.right.scale(wave * 0.48));
               Vec3 p3 = SatellaVfxClient.curve(root, mid, target, Math.max(0.0, local - 0.09)).add(base.right.scale(wave * 0.62));
               double scale = (this.mastered ? 0.84 : 0.66) + h * 0.14;
               float alpha = live * Mth.clamp(local * 1.7F, 0.0F, 1.0F) * (0.78F + h * 0.22F);
               wrote |= SatellaVfxClient.renderTendril(buffer, matrix, cam, root, p1, p2, p3, head, scale, alpha, i);
            }
         }

         return wrote;
      }

      private static Vec3 resolveCaster(Minecraft mc, Vec3 fallback, int id) {
         Entity entity = mc.level != null && id != -1 ? mc.level.getEntity(id) : null;
         return entity != null ? entity.getPosition(1.0F).add(0.0, entity.getBbHeight() * 0.64, 0.0) : fallback;
      }

      private static Vec3 resolveTarget(Minecraft mc, Vec3 fallback, int id) {
         Entity entity = mc.level != null && id != -1 ? mc.level.getEntity(id) : null;
         return entity != null ? entity.getPosition(1.0F).add(0.0, entity.getBbHeight() * 0.62, 0.0) : fallback;
      }
   }
}
