package com.github.wal_bos.moreskills.client.fx;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

public final class ElfariaGlaciaLastAlbisVfxClient {
   private static final long START_NANOS = System.nanoTime();
   private static final List<ElfariaGlaciaLastAlbisVfxClient.Phoenix> PHOENIXES = new ArrayList<>();
   private static final List<ElfariaGlaciaLastAlbisVfxClient.Burst> BURSTS = new ArrayList<>();
   private static final List<ElfariaGlaciaLastAlbisVfxClient.PendingBurst> PENDING_BURSTS = new ArrayList<>();
   private static final int ICE = ElfariaVfxVertexHelper.rgba(70, 198, 246, 132);
   private static final int ICE_SOFT = ElfariaVfxVertexHelper.rgba(128, 238, 255, 122);
   private static final int ICE_BRIGHT = ElfariaVfxVertexHelper.rgba(246, 255, 255, 232);
   private static final int LINE = ElfariaVfxVertexHelper.rgba(232, 254, 255, 220);
   private static final int DEEP = ElfariaVfxVertexHelper.rgba(30, 126, 220, 88);

   private ElfariaGlaciaLastAlbisVfxClient() {
   }

   private static long clientMillis() {
      return (System.nanoTime() - START_NANOS) / 1000000L;
   }

   public static void spawnPhoenix(Vec3 start, Vec3 target, float radius, long seed, boolean main) {
      long now = clientMillis();

      for (ElfariaGlaciaLastAlbisVfxClient.Phoenix phoenix : PHOENIXES) {
         if (phoenix.start.distanceToSqr(start) < 36.0 && phoenix.target.distanceToSqr(target) < 64.0) {
            phoenix.refresh(seed, radius, main, now);
            return;
         }
      }

      PHOENIXES.add(new ElfariaGlaciaLastAlbisVfxClient.Phoenix(start, target, radius, seed, main, now));

      while (PHOENIXES.size() > 4) {
         PHOENIXES.remove(0);
      }
   }

   public static void spawnBurst(Vec3 center, float radius, long seed) {
      long now = clientMillis();

      for (ElfariaGlaciaLastAlbisVfxClient.Phoenix phoenix : PHOENIXES) {
         if (!phoenix.dead(now) && phoenix.target.distanceToSqr(center) <= Math.max(144.0, radius * radius * 9.0)) {
            queuePendingBurst(center, radius, seed, phoenix.created + phoenix.lifeMs + 80L);
            return;
         }
      }

      addBurstNow(center, radius, seed, now);
   }

   private static void queuePendingBurst(Vec3 center, float radius, long seed, long releaseAt) {
      for (ElfariaGlaciaLastAlbisVfxClient.PendingBurst pending : PENDING_BURSTS) {
         if (pending.center.distanceToSqr(center) <= Math.max(144.0, radius * radius * 9.0)) {
            pending.center = center;
            pending.radius = Math.max(pending.radius, radius);
            pending.seed ^= seed;
            pending.releaseAt = Math.max(pending.releaseAt, releaseAt);
            return;
         }
      }

      PENDING_BURSTS.add(new ElfariaGlaciaLastAlbisVfxClient.PendingBurst(center, radius, seed, releaseAt));

      while (PENDING_BURSTS.size() > 4) {
         PENDING_BURSTS.remove(0);
      }
   }

   private static void addBurstNow(Vec3 center, float radius, long seed, long now) {
      BURSTS.add(new ElfariaGlaciaLastAlbisVfxClient.Burst(center, Math.max(28.0F, radius * 2.05F), seed, now));

      while (BURSTS.size() > 4) {
         BURSTS.remove(0);
      }
   }

   public static void tick() {
      long now = clientMillis();
      PHOENIXES.removeIf(v -> {
         if (!v.dead(now)) {
            return false;
         }

         ElfariaGlaciaLastAlbisVfxClient.PendingBurst pendingx = takePendingBurst(v.target);
         if (pendingx != null) {
            addBurstNow(pendingx.center, pendingx.radius, pendingx.seed, now);
         } else {
            addBurstNow(v.target, Math.max(42.0F, v.radius * (v.main ? 10.5F : 4.5F)), v.seed ^ 7809631433882420805L, now);
         }

         return true;
      });

      for (int i = PENDING_BURSTS.size() - 1; i >= 0; i--) {
         ElfariaGlaciaLastAlbisVfxClient.PendingBurst pending = PENDING_BURSTS.get(i);
         if (now >= pending.releaseAt) {
            addBurstNow(pending.center, pending.radius, pending.seed, now);
            PENDING_BURSTS.remove(i);
         }
      }

      BURSTS.removeIf(v -> v.dead(now));
   }

   private static ElfariaGlaciaLastAlbisVfxClient.PendingBurst takePendingBurst(Vec3 target) {
      for (int i = 0; i < PENDING_BURSTS.size(); i++) {
         ElfariaGlaciaLastAlbisVfxClient.PendingBurst pending = PENDING_BURSTS.get(i);
         if (pending.center.distanceToSqr(target) <= Math.max(144.0, pending.radius * pending.radius * 9.0)) {
            PENDING_BURSTS.remove(i);
            return pending;
         }
      }

      return null;
   }

   public static void render(PoseStack poseStack, float partialTicks) {
      if (!PHOENIXES.isEmpty() || !BURSTS.isEmpty()) {
         long now = clientMillis();
         ElfariaVfxVertexHelper.beginIce();
         Vec3 cam = ElfariaVfxVertexHelper.camera();
         Matrix4f matrix = poseStack.last().pose();
         BufferBuilder quads = ElfariaVfxVertexHelper.beginQuads();

         for (ElfariaGlaciaLastAlbisVfxClient.Phoenix phoenix : PHOENIXES) {
            renderPhoenixQuads(quads, matrix, cam, phoenix, now);
         }

         for (ElfariaGlaciaLastAlbisVfxClient.Burst burst : BURSTS) {
            renderBurstQuads(quads, matrix, cam, burst, now);
         }

         ElfariaVfxVertexHelper.finish(quads);
         BufferBuilder tris = ElfariaVfxVertexHelper.beginTriangles();

         for (ElfariaGlaciaLastAlbisVfxClient.Phoenix phoenix : PHOENIXES) {
            renderPhoenixShards(tris, matrix, cam, phoenix, now);
         }

         for (ElfariaGlaciaLastAlbisVfxClient.Burst burst : BURSTS) {
            renderBurstPrisms(tris, matrix, cam, burst, now);
         }

         ElfariaVfxVertexHelper.finish(tris);
         BufferBuilder lines = ElfariaVfxVertexHelper.beginLines();

         for (ElfariaGlaciaLastAlbisVfxClient.Phoenix phoenix : PHOENIXES) {
            renderPhoenixLines(lines, matrix, cam, phoenix, now);
         }

         for (ElfariaGlaciaLastAlbisVfxClient.Burst burst : BURSTS) {
            renderBurstLines(lines, matrix, cam, burst, now);
         }

         ElfariaVfxVertexHelper.finish(lines);
         ElfariaVfxVertexHelper.endIce();
      }
   }

   private static ElfariaGlaciaLastAlbisVfxClient.Pose pose(ElfariaGlaciaLastAlbisVfxClient.Phoenix phoenix, Vec3 cam, long now) {
      double t = phoenix.t(now);
      double fly = ElfariaVfxVertexHelper.smooth(Math.min(1.0, t / 0.86));
      Vec3 dir = phoenix.target.subtract(phoenix.start);
      if (dir.lengthSqr() < 0.001) {
         dir = new Vec3(0.0, -0.25, 1.0);
      }

      double originalDistance = Math.max(1.0, dir.length());
      dir = dir.normalize();
      Vec3 visualTarget = phoenix.target;
      Vec3 world = phoenix.start.lerp(visualTarget, fly);
      double distance = Math.max(1.0, phoenix.start.distanceTo(visualTarget));
      double arc = Math.sin(fly * Math.PI) * Math.min(42.0, 7.0 + distance * 0.12);
      world = world.add(0.0, arc, 0.0);
      Vec3 forward = new Vec3(dir.x, dir.y * 0.5, dir.z);
      if (forward.lengthSqr() < 0.001) {
         forward = new Vec3(0.0, 0.0, 1.0);
      }

      forward = forward.normalize();
      Vec3 right = forward.cross(new Vec3(0.0, 1.0, 0.0));
      if (right.lengthSqr() < 0.001) {
         right = new Vec3(1.0, 0.0, 0.0);
      }

      right = right.normalize();
      Vec3 up = right.cross(forward).normalize();
      double flap = Math.sin(t * Math.PI * 13.0) * 0.78 + Math.sin(t * Math.PI * 5.0) * 0.28;
      double scale = Math.max(phoenix.main ? 10.0 : 5.5, phoenix.radius * (phoenix.main ? 2.45 : 1.2));
      scale *= 1.0 + Math.min(1.25, distance * 0.01);
      return new ElfariaGlaciaLastAlbisVfxClient.Pose(ElfariaVfxVertexHelper.rel(world, cam), forward, right, up, flap, scale, t, distance);
   }

   private static double live(ElfariaGlaciaLastAlbisVfxClient.Phoenix phoenix, long now) {
      double t = phoenix.t(now);
      double birth = ElfariaVfxVertexHelper.smooth(Math.min(1.0, t / 0.08));
      double fade = 1.0 - ElfariaVfxVertexHelper.smooth(Math.max(0.0, (t - 0.94) / 0.06));
      return birth * fade;
   }

   private static void renderPhoenixQuads(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, ElfariaGlaciaLastAlbisVfxClient.Phoenix phoenix, long now) {
      double live = live(phoenix, now);
      if (!(live <= 0.002)) {
         ElfariaGlaciaLastAlbisVfxClient.Pose p = pose(phoenix, cam, now);
         renderBody(buffer, matrix, p, live);
         renderWings(buffer, matrix, p, live, phoenix.seed);
         renderTail(buffer, matrix, p, live, phoenix.seed);
         renderHalo(buffer, matrix, p, live, phoenix.seed);
         renderTrail(buffer, matrix, p, live, phoenix.seed);
         renderShineMotes(buffer, matrix, p, live, phoenix.seed);
      }
   }

   private static void renderBody(BufferBuilder buffer, Matrix4f matrix, ElfariaGlaciaLastAlbisVfxClient.Pose p, double live) {
      double s = p.scale;
      Vec3 chest = p.pos.add(p.up.scale(s * 0.16));
      Vec3 head = p.pos.add(p.forward.scale(s * 1.42)).add(p.up.scale(s * 0.58));
      Vec3 beak = p.pos.add(p.forward.scale(s * 2.38)).add(p.up.scale(s * 0.4));
      Vec3 belly = p.pos.add(p.forward.scale(-s * 0.86)).add(p.up.scale(-s * 0.14));
      Vec3 back = p.pos.add(p.forward.scale(-s * 0.24)).add(p.up.scale(s * 0.44));
      ElfariaVfxVertexHelper.quad(
         buffer,
         matrix,
         beak,
         chest.add(p.right.scale(s * 0.62)),
         belly,
         chest.add(p.right.scale(-s * 0.62)),
         ElfariaVfxVertexHelper.scaleAlpha(ICE, live * 0.92)
      );
      ElfariaVfxVertexHelper.quad(
         buffer,
         matrix,
         head,
         chest.add(p.right.scale(-s * 0.46)),
         belly.add(p.up.scale(s * 0.28)),
         chest.add(p.right.scale(s * 0.46)),
         ElfariaVfxVertexHelper.scaleAlpha(ICE_BRIGHT, live * 0.56)
      );
      ElfariaVfxVertexHelper.quad(
         buffer,
         matrix,
         head,
         back.add(p.right.scale(s * 0.36)),
         belly,
         back.add(p.right.scale(-s * 0.36)),
         ElfariaVfxVertexHelper.scaleAlpha(ICE_SOFT, live * 0.62)
      );

      for (int i = -3; i <= 3; i++) {
         Vec3 root = head.add(p.right.scale(i * s * 0.1)).add(p.up.scale(s * 0.06));
         Vec3 tip = root.add(p.forward.scale(s * 0.12)).add(p.up.scale(s * (0.44 + Math.abs(i) * 0.06)));
         ElfariaVfxVertexHelper.ribbon(buffer, matrix, root, tip, s * 0.034, 0.0, ElfariaVfxVertexHelper.scaleAlpha(ICE_BRIGHT, live));
      }
   }

   private static void renderWings(BufferBuilder buffer, Matrix4f matrix, ElfariaGlaciaLastAlbisVfxClient.Pose p, double live, long seed) {
      double s = p.scale;

      for (int sign = -1; sign <= 1; sign += 2) {
         Vec3 shoulder = p.pos.add(p.right.scale(sign * s * 0.4)).add(p.up.scale(s * 0.18));
         Vec3 crest = shoulder.add(p.right.scale(sign * s * 2.65)).add(p.forward.scale(-s * 0.1)).add(p.up.scale(s * (1.35 + p.flap * 0.88)));
         Vec3 outer = shoulder.add(p.right.scale(sign * s * 6.95)).add(p.forward.scale(-s * 0.94)).add(p.up.scale(s * (0.7 + p.flap * 1.5)));
         Vec3 lower = shoulder.add(p.right.scale(sign * s * 5.7)).add(p.forward.scale(-s * 2.25)).add(p.up.scale(-s * (0.82 - p.flap * 0.48)));
         ElfariaVfxVertexHelper.quad(buffer, matrix, shoulder, crest, outer, lower, ElfariaVfxVertexHelper.scaleAlpha(ICE, live * 0.78));

         for (int i = 0; i < 24; i++) {
            double f0 = i / 24.0;
            double f1 = (i + 1) / 24.0;
            Vec3 r0 = shoulder.lerp(crest, Math.min(1.0, f0 * 1.08));
            Vec3 r1 = shoulder.lerp(crest, Math.min(1.0, f1 * 1.08));
            double ripple0 = Math.sin(p.t * Math.PI * 12.0 + f0 * 4.0 + sign) * s * 0.1;
            double ripple1 = Math.sin(p.t * Math.PI * 12.0 + f1 * 4.0 + sign) * s * 0.1;
            Vec3 t0 = r0.add(p.right.scale(sign * s * (1.16 + f0 * 4.55)))
               .add(p.forward.scale(-s * (0.34 + f0 * 1.95)))
               .add(p.up.scale(-s * (0.08 + f0 * 1.52 - p.flap * 0.86)))
               .add(p.up.scale(ripple0));
            Vec3 t1 = r1.add(p.right.scale(sign * s * (1.16 + f1 * 4.55)))
               .add(p.forward.scale(-s * (0.34 + f1 * 1.95)))
               .add(p.up.scale(-s * (0.08 + f1 * 1.52 - p.flap * 0.86)))
               .add(p.up.scale(ripple1));
            int color = ElfariaVfxVertexHelper.scaleAlpha((i & 1) == 0 ? ICE_BRIGHT : ICE_SOFT, live * (0.66 + f0 * 0.28));
            ElfariaVfxVertexHelper.quad(buffer, matrix, r0, r1, t1, t0, color);
            if ((i & 2) == 0) {
               Vec3 shine0 = r0.lerp(t0, 0.32);
               Vec3 shine1 = r1.lerp(t1, 0.82);
               ElfariaVfxVertexHelper.ribbon(buffer, matrix, shine0, shine1, s * 0.022, 0.0, ElfariaVfxVertexHelper.scaleAlpha(ICE_BRIGHT, live * 0.46));
            }
         }

         for (int i = 0; i < 12; i++) {
            double h = ElfariaVfxVertexHelper.hash(seed, 44000L + i + sign * 31L);
            Vec3 root = shoulder.add(p.right.scale(sign * s * (1.2 + h * 4.8)))
               .add(p.forward.scale(-s * (0.4 + h * 1.4)))
               .add(p.up.scale(s * (0.35 + p.flap * 0.7 - h * 1.2)));
            Vec3 tip = root.add(p.right.scale(sign * s * (0.9 + h * 1.6))).add(p.forward.scale(-s * (0.4 + h * 0.8))).add(p.up.scale(-s * (0.3 + h)));
            ElfariaVfxVertexHelper.ribbon(buffer, matrix, root, tip, s * 0.034, 0.0, ElfariaVfxVertexHelper.scaleAlpha(ICE_BRIGHT, live * 0.62));
         }
      }
   }

   private static void renderTail(BufferBuilder buffer, Matrix4f matrix, ElfariaGlaciaLastAlbisVfxClient.Pose p, double live, long seed) {
      double s = p.scale;

      for (int i = -7; i <= 7; i++) {
         double h = i / 7.0;
         double wave = Math.sin(p.t * Math.PI * 5.0 + h * 2.0) * s * 0.12;
         Vec3 root = p.pos.add(p.forward.scale(-s * 0.82)).add(p.right.scale(h * s * 0.18)).add(p.up.scale(-s * 0.08));
         Vec3 mid = root.add(p.forward.scale(-s * 1.9)).add(p.right.scale(h * s * 0.66 + wave)).add(p.up.scale(-s * (0.32 + Math.abs(h) * 0.2)));
         Vec3 tip = root.add(p.forward.scale(-s * (5.25 + Math.abs(h) * 1.6)))
            .add(p.right.scale(h * s * 1.32 + wave))
            .add(p.up.scale(-s * (1.05 + Math.abs(h) * 0.72)));
         ElfariaVfxVertexHelper.ribbon(buffer, matrix, root, mid, s * 0.105, s * 0.055, ElfariaVfxVertexHelper.scaleAlpha(ICE, live * 0.7));
         ElfariaVfxVertexHelper.ribbon(
            buffer, matrix, mid, tip, s * 0.055, 0.0, ElfariaVfxVertexHelper.scaleAlpha((i & 1) == 0 ? ICE_BRIGHT : ICE_SOFT, live * 0.92)
         );
      }

      for (int i = 0; i < 18; i++) {
         double h = ElfariaVfxVertexHelper.hash(seed, 47000L + i);
         Vec3 root = p.pos.add(p.forward.scale(-s * (1.0 + h * 3.0))).add(p.right.scale((h - 0.5) * s * 2.2));
         Vec3 tip = root.add(p.forward.scale(-s * (1.3 + h * 2.1))).add(p.up.scale(-s * (0.2 + h * 0.8)));
         ElfariaVfxVertexHelper.ribbon(buffer, matrix, root, tip, s * 0.026, 0.0, ElfariaVfxVertexHelper.scaleAlpha(ICE_BRIGHT, live * 0.42));
      }
   }

   private static void renderHalo(BufferBuilder buffer, Matrix4f matrix, ElfariaGlaciaLastAlbisVfxClient.Pose p, double live, long seed) {
      double s = p.scale;
      Vec3 center = p.pos.add(p.forward.scale(s * 0.92)).add(p.up.scale(s * 0.82));

      for (int i = 0; i < 72; i++) {
         double pulse = 1.0 + Math.sin(p.t * Math.PI * 8.0 + i * 0.18) * 0.055;
         double a0 = i * Math.PI * 2.0 / 72.0;
         double a1 = (i + 1) * Math.PI * 2.0 / 72.0;
         Vec3 p0 = center.add(p.right.scale(Math.cos(a0) * s * 1.16 * pulse)).add(p.up.scale(Math.sin(a0) * s * 1.16 * pulse));
         Vec3 p1 = center.add(p.right.scale(Math.cos(a0) * s * 1.44 * pulse)).add(p.up.scale(Math.sin(a0) * s * 1.44 * pulse));
         Vec3 p2 = center.add(p.right.scale(Math.cos(a1) * s * 1.44 * pulse)).add(p.up.scale(Math.sin(a1) * s * 1.44 * pulse));
         Vec3 p3 = center.add(p.right.scale(Math.cos(a1) * s * 1.16 * pulse)).add(p.up.scale(Math.sin(a1) * s * 1.16 * pulse));
         ElfariaVfxVertexHelper.quad(buffer, matrix, p0, p1, p2, p3, ElfariaVfxVertexHelper.scaleAlpha((i & 1) == 0 ? ICE_BRIGHT : ICE_SOFT, live * 0.42));
      }

      for (int i = 0; i < 20; i++) {
         double h = ElfariaVfxVertexHelper.hash(seed, 50000L + i);
         double a = h * Math.PI * 2.0 + p.t * 0.9;
         Vec3 m = center.add(p.right.scale(Math.cos(a) * s * (1.58 + h * 0.6))).add(p.up.scale(Math.sin(a) * s * (1.58 + h * 0.6)));
         ElfariaVfxVertexHelper.billboardDiamond(
            buffer, matrix, m, s * (0.04 + h * 0.055), ElfariaVfxVertexHelper.scaleAlpha(ICE_BRIGHT, live * (0.45 + h * 0.55))
         );
      }
   }

   private static void renderTrail(BufferBuilder buffer, Matrix4f matrix, ElfariaGlaciaLastAlbisVfxClient.Pose p, double live, long seed) {
      double s = p.scale;
      int count = p.distance > 48.0 ? 104 : 70;

      for (int i = 0; i < count; i++) {
         double f = (double)i / Math.max(1, count - 1);
         double sideWave = Math.sin(f * Math.PI * 4.5 + p.t * Math.PI * 3.0) * s * (0.18 + f * 0.42);
         Vec3 root = p.pos.add(p.forward.scale(-s * (0.68 + f * (8.4 + p.distance * 0.1)))).add(p.right.scale(sideWave)).add(p.up.scale(-s * f * 0.4));
         Vec3 tip = root.add(p.forward.scale(-s * (0.55 + f * 0.46))).add(p.up.scale(-s * (0.12 + f * 0.24)));
         ElfariaVfxVertexHelper.ribbon(
            buffer,
            matrix,
            root,
            tip,
            s * 0.065 * (1.0 - f),
            0.0,
            ElfariaVfxVertexHelper.scaleAlpha((i & 1) == 0 ? ICE_BRIGHT : ICE, live * (0.62 * (1.0 - f)))
         );
      }

      for (int i = 0; i < 132; i++) {
         double h = ElfariaVfxVertexHelper.hash(seed, 9000L + i);
         double f = ElfariaVfxVertexHelper.hash(seed, 9100L + i);
         Vec3 root = p.pos
            .add(p.forward.scale(-s * (1.0 + f * (7.0 + p.distance * 0.08))))
            .add(p.right.scale((h - 0.5) * s * 3.6))
            .add(p.up.scale((ElfariaVfxVertexHelper.hash(seed, 9200L + i) - 0.5) * s * 1.6));
         Vec3 tip = root.add(p.forward.scale(-s * (0.6 + h * 1.0))).add(p.up.scale(-s * (0.2 + h * 0.8)));
         ElfariaVfxVertexHelper.ribbon(buffer, matrix, root, tip, s * 0.03, 0.0, ElfariaVfxVertexHelper.scaleAlpha(ICE_BRIGHT, live * 0.42));
      }
   }

   private static void renderShineMotes(BufferBuilder buffer, Matrix4f matrix, ElfariaGlaciaLastAlbisVfxClient.Pose p, double live, long seed) {
      double s = p.scale;

      for (int i = 0; i < 180; i++) {
         double h = ElfariaVfxVertexHelper.hash(seed, 53000L + i);
         double f = ElfariaVfxVertexHelper.hash(seed, 53100L + i);
         double drift = (h + p.t * (0.18 + f * 0.18)) % 1.0;
         Vec3 center = p.pos
            .add(p.forward.scale(s * (1.4 - drift * (8.0 + p.distance * 0.06))))
            .add(p.right.scale((ElfariaVfxVertexHelper.hash(seed, 53200L + i) - 0.5) * s * 7.0 * drift))
            .add(p.up.scale((ElfariaVfxVertexHelper.hash(seed, 53300L + i) - 0.5) * s * 3.2));
         double blink = 0.25 + 0.75 * Math.abs(Math.sin(p.t * Math.PI * 18.0 + h * 11.0));
         ElfariaVfxVertexHelper.billboardDiamond(buffer, matrix, center, s * (0.018 + f * 0.052), ElfariaVfxVertexHelper.scaleAlpha(ICE_BRIGHT, live * blink));
      }
   }

   private static void renderPhoenixShards(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, ElfariaGlaciaLastAlbisVfxClient.Phoenix phoenix, long now) {
      double live = live(phoenix, now);
      if (!(live <= 0.002)) {
         ElfariaGlaciaLastAlbisVfxClient.Pose p = pose(phoenix, cam, now);
         double s = p.scale;

         for (int i = 0; i < 88; i++) {
            double h = ElfariaVfxVertexHelper.hash(phoenix.seed, 12000L + i);
            Vec3 base = p.pos
               .add(p.forward.scale(-s * (0.25 + h * 3.8)))
               .add(p.right.scale((ElfariaVfxVertexHelper.hash(phoenix.seed, 12100L + i) - 0.5) * s * 8.0))
               .add(p.up.scale((ElfariaVfxVertexHelper.hash(phoenix.seed, 12200L + i) - 0.3) * s * 1.8));
            ElfariaAlbisIceSheetRenderer.prism(buffer, matrix, base, s * (0.22 + h * 0.48), s * 0.022, 5, live * 0.54, phoenix.seed + i);
         }
      }
   }

   private static void renderPhoenixLines(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, ElfariaGlaciaLastAlbisVfxClient.Phoenix phoenix, long now) {
      double live = live(phoenix, now);
      if (!(live <= 0.002)) {
         ElfariaGlaciaLastAlbisVfxClient.Pose p = pose(phoenix, cam, now);
         double s = p.scale;

         for (int sign = -1; sign <= 1; sign += 2) {
            Vec3 shoulder = p.pos.add(p.right.scale(sign * s * 0.42)).add(p.up.scale(s * 0.2));

            for (int i = 0; i < 22; i++) {
               double f = i / 21.0;
               Vec3 tip = shoulder.add(p.right.scale(sign * s * (1.2 + f * 5.5)))
                  .add(p.forward.scale(-s * (0.32 + f * 1.95)))
                  .add(p.up.scale(-s * (0.03 + f * 1.38 - p.flap * 0.92)));
               ElfariaVfxVertexHelper.line(buffer, matrix, shoulder, tip, ElfariaVfxVertexHelper.scaleAlpha(LINE, live * (0.38 + f * 0.55)));
            }
         }

         Vec3 nose = p.pos.add(p.forward.scale(s * 2.25)).add(p.up.scale(s * 0.42));
         Vec3 tail = p.pos.add(p.forward.scale(-s * 5.25)).add(p.up.scale(-s * 0.95));
         ElfariaVfxVertexHelper.line(buffer, matrix, nose, tail, ElfariaVfxVertexHelper.scaleAlpha(LINE, live * 0.74));
      }
   }

   private static void renderBurstQuads(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, ElfariaGlaciaLastAlbisVfxClient.Burst burst, long now) {
      double t = burst.t(now);
      double grow = ElfariaVfxVertexHelper.smooth(Math.min(1.0, t * 2.1));
      double fade = 1.0 - ElfariaVfxVertexHelper.smooth(Math.max(0.0, (t - 0.78) / 0.22));
      Vec3 c = ElfariaVfxVertexHelper.rel(burst.center, cam);
      renderBurstPillar(buffer, matrix, c, burst, grow, fade);
      renderBurstDome(buffer, matrix, c, burst, grow, fade);
      ElfariaAlbisIceSheetRenderer.albisDisc(buffer, matrix, c, burst.radius * (3.6 + grow * 2.8), fade * 0.86, burst.seed, 0.045);
      ElfariaAlbisIceSheetRenderer.roundLines(buffer, matrix, c, burst.radius * (3.8 + grow * 3.1), fade * 0.95, burst.seed + 88L, 0.08);

      for (int i = 0; i < 148; i++) {
         double a = i * Math.PI * 2.0 / 148.0;
         double h = ElfariaVfxVertexHelper.hash(burst.seed, i);
         double len = burst.radius * (2.3 + h * 5.8) * grow;
         Vec3 root = c.add(Math.cos(a) * burst.radius * 0.32, 0.08, Math.sin(a) * burst.radius * 0.32);
         Vec3 tip = c.add(Math.cos(a) * len, 0.16 + ElfariaVfxVertexHelper.hash(burst.seed, 1000L + i) * burst.radius * 0.42, Math.sin(a) * len);
         ElfariaVfxVertexHelper.ribbon(
            buffer, matrix, root, tip, burst.radius * 0.06, 0.0, ElfariaVfxVertexHelper.scaleAlpha((i & 1) == 0 ? ICE_BRIGHT : ICE, fade * 0.88)
         );
      }

      for (int i = 0; i < 180; i++) {
         double h = ElfariaVfxVertexHelper.hash(burst.seed, 61000L + i);
         double a = h * Math.PI * 2.0;
         double d = burst.radius * grow * (0.6 + ElfariaVfxVertexHelper.hash(burst.seed, 61100L + i) * 6.8);
         Vec3 mote = c.add(Math.cos(a) * d, 0.3 + h * burst.radius * 1.4, Math.sin(a) * d);
         ElfariaVfxVertexHelper.billboardDiamond(
            buffer, matrix, mote, burst.radius * (0.02 + h * 0.06), ElfariaVfxVertexHelper.scaleAlpha(ICE_BRIGHT, fade * (0.3 + h * 0.7))
         );
      }
   }

   private static void renderBurstPillar(BufferBuilder buffer, Matrix4f matrix, Vec3 c, ElfariaGlaciaLastAlbisVfxClient.Burst burst, double grow, double fade) {
      double height = burst.radius * (4.0 + grow * 3.4);
      double core = burst.radius * (0.55 + grow * 0.3);

      for (int i = 0; i < 18; i++) {
         double a = i * Math.PI * 2.0 / 18.0 + grow * 0.42;
         Vec3 side = new Vec3(Math.cos(a), 0.0, Math.sin(a));
         Vec3 tangent = new Vec3(-Math.sin(a), 0.0, Math.cos(a));
         double w = core * (0.45 + ElfariaVfxVertexHelper.hash(burst.seed, 70000L + i) * 0.95);
         double h = height * (0.74 + ElfariaVfxVertexHelper.hash(burst.seed, 70100L + i) * 0.44);
         Vec3 base = c.add(side.scale(core * (0.1 + ElfariaVfxVertexHelper.hash(burst.seed, 70200L + i) * 0.32)));
         Vec3 top = base.add(0.0, h, 0.0).add(side.scale(core * (0.45 + grow * 0.8)));
         ElfariaVfxVertexHelper.quad(
            buffer,
            matrix,
            base.add(tangent.scale(w)),
            top.add(tangent.scale(w * 0.18)),
            top.add(tangent.scale(-w * 0.18)),
            base.add(tangent.scale(-w)),
            ElfariaVfxVertexHelper.scaleAlpha((i & 1) == 0 ? ICE_BRIGHT : ICE_SOFT, fade * 0.88)
         );
         ElfariaVfxVertexHelper.ribbon(buffer, matrix, base, top, burst.radius * 0.03, 0.0, ElfariaVfxVertexHelper.scaleAlpha(LINE, fade));
      }

      Vec3 top = c.add(0.0, height * 1.04, 0.0);

      for (int i = 0; i < 56; i++) {
         double h = ElfariaVfxVertexHelper.hash(burst.seed, 71000L + i);
         double a = h * Math.PI * 2.0;
         double d = burst.radius * (0.25 + ElfariaVfxVertexHelper.hash(burst.seed, 71100L + i) * 1.7) * grow;
         Vec3 p0 = top.add(Math.cos(a) * d, -burst.radius * (0.2 + h * 0.9), Math.sin(a) * d);
         Vec3 p1 = p0.add(Math.cos(a) * burst.radius * (0.35 + h * 0.95), -burst.radius * (0.7 + h * 1.4), Math.sin(a) * burst.radius * (0.35 + h * 0.95));
         ElfariaVfxVertexHelper.ribbon(
            buffer, matrix, p0, p1, burst.radius * (0.018 + h * 0.026), 0.0, ElfariaVfxVertexHelper.scaleAlpha(ICE_BRIGHT, fade * (0.45 + h * 0.55))
         );
      }
   }

   private static void renderBurstDome(BufferBuilder buffer, Matrix4f matrix, Vec3 c, ElfariaGlaciaLastAlbisVfxClient.Burst burst, double grow, double fade) {
      double dome = burst.radius * (2.25 + grow * 2.35);

      for (int ring = 1; ring <= 8; ring++) {
         double f0 = (ring - 1) / 8.0;
         double f1 = ring / 8.0;
         double r0 = dome * Math.sin(f0 * Math.PI * 0.5);
         double r1 = dome * Math.sin(f1 * Math.PI * 0.5);
         double y0 = dome * Math.cos(f0 * Math.PI * 0.5) * 0.58;
         double y1 = dome * Math.cos(f1 * Math.PI * 0.5) * 0.58;
         double alpha = fade * (0.28 + ring * 0.035);

         for (int i = 0; i < 96; i++) {
            double a0 = i * Math.PI * 2.0 / 96.0;
            double a1 = (i + 1) * Math.PI * 2.0 / 96.0;
            double n0 = 0.96 + ElfariaVfxVertexHelper.hash(burst.seed, ring * 10000L + i) * 0.08;
            double n1 = 0.96 + ElfariaVfxVertexHelper.hash(burst.seed, ring * 12000L + i) * 0.08;
            Vec3 p0 = c.add(Math.cos(a0) * r0 * n0, y0, Math.sin(a0) * r0 * n0);
            Vec3 p1 = c.add(Math.cos(a1) * r0 * n1, y0, Math.sin(a1) * r0 * n1);
            Vec3 p2 = c.add(Math.cos(a1) * r1 * n1, y1, Math.sin(a1) * r1 * n1);
            Vec3 p3 = c.add(Math.cos(a0) * r1 * n0, y1, Math.sin(a0) * r1 * n0);
            ElfariaVfxVertexHelper.quad(buffer, matrix, p0, p1, p2, p3, ElfariaVfxVertexHelper.scaleAlpha((ring & 1) == 0 ? ICE_SOFT : DEEP, alpha));
         }
      }
   }

   private static void renderBurstPrisms(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, ElfariaGlaciaLastAlbisVfxClient.Burst burst, long now) {
      double t = burst.t(now);
      double grow = ElfariaVfxVertexHelper.smooth(Math.min(1.0, t * 2.5));
      double fade = 1.0 - ElfariaVfxVertexHelper.smooth(Math.max(0.0, (t - 0.74) / 0.26));
      Vec3 c = ElfariaVfxVertexHelper.rel(burst.center, cam);

      for (int i = 0; i < 164; i++) {
         double h = ElfariaVfxVertexHelper.hash(burst.seed, 2000L + i);
         double a = h * Math.PI * 2.0;
         double d = burst.radius * (0.25 + ElfariaVfxVertexHelper.hash(burst.seed, 2100L + i) * 4.7) * grow;
         Vec3 base = c.add(Math.cos(a) * d, 0.08, Math.sin(a) * d);
         ElfariaAlbisIceSheetRenderer.prism(
            buffer,
            matrix,
            base,
            burst.radius * (0.62 + h * 2.35) * (1.0 - t * 0.18),
            burst.radius * 0.07,
            5 + (i & 1),
            fade * (0.58 + h * 0.42),
            burst.seed + i
         );
      }
   }

   private static void renderBurstLines(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, ElfariaGlaciaLastAlbisVfxClient.Burst burst, long now) {
      double t = burst.t(now);
      double grow = ElfariaVfxVertexHelper.smooth(Math.min(1.0, t * 2.2));
      double fade = 1.0 - ElfariaVfxVertexHelper.smooth(Math.max(0.0, (t - 0.78) / 0.22));
      Vec3 c = ElfariaVfxVertexHelper.rel(burst.center, cam);

      for (int i = 0; i < 192; i++) {
         double a = i * Math.PI * 2.0 / 192.0;
         Vec3 p = c.add(Math.cos(a) * burst.radius * (1.0 + grow * 7.2), 0.08, Math.sin(a) * burst.radius * (1.0 + grow * 7.2));
         ElfariaVfxVertexHelper.line(buffer, matrix, c.add(0.0, 0.1, 0.0), p, ElfariaVfxVertexHelper.scaleAlpha(LINE, fade * (1.0 - t * 0.2)));
      }
   }

   private static final class Burst {
      private final Vec3 center;
      private final float radius;
      private final long seed;
      private final long created;
      private final long lifeMs;

      private Burst(Vec3 center, float radius, long seed, long now) {
         this.center = center;
         this.radius = radius;
         this.seed = seed;
         this.created = now;
         this.lifeMs = 13500L;
      }

      private double t(long now) {
         return Math.min(1.0, Math.max(0.0, (double)(now - this.created) / this.lifeMs));
      }

      private boolean dead(long now) {
         return now - this.created > this.lifeMs;
      }
   }

   private static final class PendingBurst {
      private Vec3 center;
      private float radius;
      private long seed;
      private long releaseAt;

      private PendingBurst(Vec3 center, float radius, long seed, long releaseAt) {
         this.center = center;
         this.radius = radius;
         this.seed = seed;
         this.releaseAt = releaseAt;
      }
   }

   private static final class Phoenix {
      private final Vec3 start;
      private final Vec3 target;
      private float radius;
      private long seed;
      private boolean main;
      private final long created;
      private long refreshed;
      private final long lifeMs;

      private Phoenix(Vec3 start, Vec3 target, float radius, long seed, boolean main, long now) {
         this.start = start;
         this.target = target;
         this.radius = radius;
         this.seed = seed;
         this.main = main;
         this.created = now;
         this.refreshed = now;
         double distance = start.distanceTo(target);
         this.lifeMs = (long)Math.min(main ? 11500.0 : 6500.0, Math.max(main ? 5200.0 : 3200.0, 3400.0 + distance * (main ? 26.0 : 24.0)));
      }

      private void refresh(long seed, float radius, boolean main, long now) {
         this.seed ^= seed;
         this.radius = Math.max(this.radius, radius);
         this.main = this.main || main;
         this.refreshed = now;
      }

      private double t(long now) {
         return Math.min(1.0, Math.max(0.0, (double)(now - this.created) / this.lifeMs));
      }

      private boolean dead(long now) {
         return now - this.created > this.lifeMs;
      }
   }

   private static final class Pose {
      private final Vec3 pos;
      private final Vec3 forward;
      private final Vec3 right;
      private final Vec3 up;
      private final double flap;
      private final double scale;
      private final double t;
      private final double distance;

      private Pose(Vec3 pos, Vec3 forward, Vec3 right, Vec3 up, double flap, double scale, double t, double distance) {
         this.pos = pos;
         this.forward = forward;
         this.right = right;
         this.up = up;
         this.flap = flap;
         this.scale = scale;
         this.t = t;
         this.distance = distance;
      }
   }
}
