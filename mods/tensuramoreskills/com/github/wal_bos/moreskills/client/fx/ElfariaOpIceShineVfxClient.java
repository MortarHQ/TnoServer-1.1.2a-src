package com.github.wal_bos.moreskills.client.fx;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.Util;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

public final class ElfariaOpIceShineVfxClient {
   private static final List<ElfariaOpIceShineVfxClient.Storm> STORMS = new ArrayList<>();
   private static final List<ElfariaOpIceShineVfxClient.Flight> FLIGHTS = new ArrayList<>();
   private static final int WHITE = ElfariaVfxVertexHelper.rgba(255, 255, 255, 245);
   private static final int ICE = ElfariaVfxVertexHelper.rgba(114, 235, 255, 190);
   private static final int DEEP = ElfariaVfxVertexHelper.rgba(34, 160, 255, 140);
   private static final int PALE = ElfariaVfxVertexHelper.rgba(210, 252, 255, 220);

   private ElfariaOpIceShineVfxClient() {
   }

   public static void spawnStorm(Vec3 center, float radius, long seed, int lifeMs, int strength, boolean huge) {
      STORMS.add(
         new ElfariaOpIceShineVfxClient.Storm(center, Math.max(0.8F, radius), seed, Math.max(350, lifeMs), Math.max(1, strength), huge, Util.getMillis())
      );

      while (STORMS.size() > 34) {
         STORMS.remove(0);
      }
   }

   public static void spawnFlight(Vec3 start, Vec3 target, float radius, long seed, boolean main) {
      Vec3 delta = target.subtract(start);
      double distance = delta.length();
      Vec3 end = target;
      if (distance > 0.001) {
         Vec3 dir = delta.normalize();
         end = target.add(dir.scale(Math.min(620.0, Math.max(140.0, distance * (main ? 3.1 : 1.35)))));
      }

      FLIGHTS.add(new ElfariaOpIceShineVfxClient.Flight(start, target, end, Math.max(1.5F, radius), seed, main, Util.getMillis()));

      while (FLIGHTS.size() > 12) {
         FLIGHTS.remove(0);
      }
   }

   public static void tick() {
      long now = Util.getMillis();
      STORMS.removeIf(v -> v.dead(now));
      FLIGHTS.removeIf(v -> v.dead(now));
   }

   public static void render(PoseStack poseStack, float partialTicks) {
      if (!STORMS.isEmpty() || !FLIGHTS.isEmpty()) {
         long now = Util.getMillis();
         Vec3 cam = ElfariaVfxVertexHelper.camera();
         Matrix4f matrix = poseStack.last().pose();
         RenderSystem.blendFuncSeparate(770, 1, 1, 0);
         BufferBuilder quads = ElfariaVfxVertexHelper.beginQuads();

         for (ElfariaOpIceShineVfxClient.Storm storm : STORMS) {
            renderStormQuads(quads, matrix, cam, storm, now);
         }

         for (ElfariaOpIceShineVfxClient.Flight flight : FLIGHTS) {
            renderFlightQuads(quads, matrix, cam, flight, now);
         }

         ElfariaVfxVertexHelper.finish(quads);
         BufferBuilder tris = ElfariaVfxVertexHelper.beginTriangles();

         for (ElfariaOpIceShineVfxClient.Storm storm : STORMS) {
            renderStormShards(tris, matrix, cam, storm, now);
         }

         for (ElfariaOpIceShineVfxClient.Flight flight : FLIGHTS) {
            renderFlightShards(tris, matrix, cam, flight, now);
         }

         ElfariaVfxVertexHelper.finish(tris);
         RenderSystem.defaultBlendFunc();
      }
   }

   private static void renderStormQuads(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, ElfariaOpIceShineVfxClient.Storm storm, long now) {
      double t = storm.t(now);
      double live = storm.live(now);
      if (!(live <= 0.002)) {
         Vec3 c = ElfariaVfxVertexHelper.rel(storm.center, cam);
         double radius = storm.radius * (storm.huge ? 1.8 : 1.15);
         double pulse = 1.0 + Math.sin(t * Math.PI * 18.0) * 0.045;
         int rings = storm.huge ? 5 : 3;

         for (int ring = 0; ring < rings; ring++) {
            double rr = radius * (0.45 + ring * 0.22) * pulse;
            double y = 0.08 + ring * 0.42;
            int segments = storm.huge ? 96 : 64;

            for (int i = 0; i < segments; i++) {
               double a0 = i * Math.PI * 2.0 / segments + t * (0.4 + ring * 0.18);
               double a1 = (i + 1) * Math.PI * 2.0 / segments + t * (0.4 + ring * 0.18);
               Vec3 p0 = c.add(Math.cos(a0) * rr * 0.985, y, Math.sin(a0) * rr * 0.985);
               Vec3 p1 = c.add(Math.cos(a0) * rr * 1.035, y + 0.02, Math.sin(a0) * rr * 1.035);
               Vec3 p2 = c.add(Math.cos(a1) * rr * 1.035, y + 0.02, Math.sin(a1) * rr * 1.035);
               Vec3 p3 = c.add(Math.cos(a1) * rr * 0.985, y, Math.sin(a1) * rr * 0.985);
               ElfariaVfxVertexHelper.quad(
                  buffer, matrix, p0, p1, p2, p3, ElfariaVfxVertexHelper.scaleAlpha((ring & 1) == 0 ? PALE : ICE, live * (0.26 + ring * 0.055))
               );
            }
         }

         int glints = storm.huge ? 170 : 90;

         for (int i = 0; i < glints; i++) {
            double h = ElfariaVfxVertexHelper.hash(storm.seed, 1000L + i);
            double blink = Math.pow(Math.max(0.0, Math.sin(t * Math.PI * (10.0 + h * 8.0) + h * 99.0)), 3.0);
            if (!(blink < 0.08) || (i & 5) == 0) {
               double a = h * Math.PI * 2.0 + t * (0.5 + h);
               double dist = radius
                  * Math.sqrt(ElfariaVfxVertexHelper.hash(storm.seed, 2000L + i))
                  * (0.25 + 0.95 * ElfariaVfxVertexHelper.hash(storm.seed, 3000L + i));
               double y = 0.25 + ElfariaVfxVertexHelper.hash(storm.seed, 4000L + i) * radius * (storm.huge ? 1.6 : 0.85);
               Vec3 p = c.add(Math.cos(a) * dist, y, Math.sin(a) * dist);
               double size = (0.08 + ElfariaVfxVertexHelper.hash(storm.seed, 5000L + i) * 0.34) * (storm.huge ? 1.65 : 1.0);
               renderStar(buffer, matrix, p, size, live * (0.36 + blink * 0.88), a + t * 3.0);
            }
         }

         int sheens = storm.huge ? 72 : 36;

         for (int i = 0; i < sheens; i++) {
            double h = ElfariaVfxVertexHelper.hash(storm.seed, 6000L + i);
            double a = h * Math.PI * 2.0;
            double dist = radius * (0.18 + ElfariaVfxVertexHelper.hash(storm.seed, 7000L + i) * 0.92);
            Vec3 p0 = c.add(Math.cos(a) * dist, 0.2 + h * radius * 0.9, Math.sin(a) * dist);
            Vec3 p1 = p0.add(Math.cos(a + 0.65) * radius * (0.15 + h * 0.35), radius * (0.1 + h * 0.34), Math.sin(a + 0.65) * radius * (0.15 + h * 0.35));
            ElfariaVfxVertexHelper.ribbon(buffer, matrix, p0, p1, 0.018 + h * 0.035, 0.0, ElfariaVfxVertexHelper.scaleAlpha(WHITE, live * (0.38 + h * 0.34)));
         }
      }
   }

   private static void renderStormShards(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, ElfariaOpIceShineVfxClient.Storm storm, long now) {
      double t = storm.t(now);
      double live = storm.live(now);
      if (!(live <= 0.002)) {
         Vec3 c = ElfariaVfxVertexHelper.rel(storm.center, cam);
         double radius = storm.radius * (storm.huge ? 1.65 : 1.0);
         double burst = ElfariaVfxVertexHelper.smooth(Math.min(1.0, t / 0.2));
         int count = storm.huge ? 92 : 46;

         for (int i = 0; i < count; i++) {
            double h = ElfariaVfxVertexHelper.hash(storm.seed, 9000L + i);
            double a = h * Math.PI * 2.0;
            double dist = radius * (0.26 + ElfariaVfxVertexHelper.hash(storm.seed, 9100L + i) * 1.2) * burst;
            double height = (0.55 + ElfariaVfxVertexHelper.hash(storm.seed, 9200L + i) * 4.8) * (storm.huge ? 1.35 : 0.85) * (1.0 - t * 0.22);
            double width = 0.055 + ElfariaVfxVertexHelper.hash(storm.seed, 9300L + i) * 0.14;
            Vec3 base = c.add(Math.cos(a) * dist, 0.04, Math.sin(a) * dist);
            Vec3 radial = new Vec3(Math.cos(a), 0.0, Math.sin(a));
            Vec3 side = new Vec3(-radial.z, 0.0, radial.x);
            Vec3 tip = base.add(radial.scale(width * 1.2)).add(0.0, height, 0.0);
            ElfariaVfxVertexHelper.tri(
               buffer,
               matrix,
               base.add(side.scale(width)),
               base.add(side.scale(-width)),
               tip,
               ElfariaVfxVertexHelper.scaleAlpha((i & 3) == 0 ? WHITE : ICE, live * (0.5 + h * 0.4))
            );
         }
      }
   }

   private static void renderStormLines(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, ElfariaOpIceShineVfxClient.Storm storm, long now) {
      double t = storm.t(now);
      double live = storm.live(now);
      if (!(live <= 0.002)) {
         Vec3 c = ElfariaVfxVertexHelper.rel(storm.center, cam);
         double radius = storm.radius * (storm.huge ? 2.05 : 1.2);
         int count = storm.huge ? 220 : 80;

         for (int i = 0; i < count; i++) {
            double h = ElfariaVfxVertexHelper.hash(storm.seed, 12000L + i);
            double a = h * Math.PI * 2.0 + Math.sin(t * 4.0 + h) * 0.08;
            double start = radius * (0.08 + ElfariaVfxVertexHelper.hash(storm.seed, 12100L + i) * 0.7);
            double len = radius * (0.12 + ElfariaVfxVertexHelper.hash(storm.seed, 12200L + i) * 0.48);
            Vec3 p0 = c.add(Math.cos(a) * start, 0.14 + h * radius * 0.55, Math.sin(a) * start);
            Vec3 p1 = c.add(
               Math.cos(a + (h - 0.5) * 0.35) * Math.min(radius, start + len),
               0.24 + h * radius * 0.85,
               Math.sin(a + (h - 0.5) * 0.35) * Math.min(radius, start + len)
            );
            ElfariaVfxVertexHelper.line(buffer, matrix, p0, p1, ElfariaVfxVertexHelper.scaleAlpha((i & 7) == 0 ? WHITE : PALE, live * (0.44 + h * 0.4)));
         }
      }
   }

   private static void renderFlightQuads(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, ElfariaOpIceShineVfxClient.Flight flight, long now) {
      double live = flight.live(now);
      if (!(live <= 0.002)) {
         double t = flight.t(now);
         Vec3 axis = flight.visualEnd.subtract(flight.start);
         double length = Math.max(1.0, axis.length());
         Vec3 forward = axis.normalize();
         Vec3 up = Math.abs(forward.y) > 0.9 ? new Vec3(1.0, 0.0, 0.0) : new Vec3(0.0, 1.0, 0.0);
         Vec3 side = forward.cross(up).normalize();
         Vec3 normal = side.cross(forward).normalize();
         Vec3 head = flight.start.lerp(flight.visualEnd, ElfariaVfxVertexHelper.smooth(Math.min(1.0, t * 1.04)));
         Vec3 relHead = ElfariaVfxVertexHelper.rel(head, cam);
         double scale = Math.max(flight.main ? 5.5 : 1.8, flight.radius) * (flight.main ? 2.75 : 1.25);

         for (int i = 0; i < 44; i++) {
            double h = ElfariaVfxVertexHelper.hash(flight.seed, 20000L + i);
            double lag = (0.04 + h * 0.62) * Math.min(1.0, t * 2.0);
            Vec3 center = relHead.subtract(forward.scale(lag * length * 0.55))
               .add(side.scale((h - 0.5) * scale * 7.5))
               .add(normal.scale((ElfariaVfxVertexHelper.hash(flight.seed, 20100L + i) - 0.5) * scale * 4.0));
            Vec3 end = center.subtract(forward.scale(scale * (3.2 + h * 7.5))).add(normal.scale(Math.sin(t * 10.0 + h * 20.0) * scale));
            ElfariaVfxVertexHelper.ribbon(
               buffer,
               matrix,
               center,
               end,
               scale * (0.02 + h * 0.042),
               0.0,
               ElfariaVfxVertexHelper.scaleAlpha((i & 3) == 0 ? WHITE : ICE, live * (0.42 + h * 0.4))
            );
         }

         for (int i = 0; i < 95; i++) {
            double h = ElfariaVfxVertexHelper.hash(flight.seed, 21000L + i);
            double along = Math.max(0.0, t - h * 0.62) * length;
            Vec3 p = ElfariaVfxVertexHelper.rel(flight.start.add(forward.scale(along)), cam)
               .add(side.scale((ElfariaVfxVertexHelper.hash(flight.seed, 21100L + i) - 0.5) * scale * 8.4))
               .add(normal.scale((ElfariaVfxVertexHelper.hash(flight.seed, 21200L + i) - 0.5) * scale * 5.4));
            double blink = Math.pow(Math.abs(Math.sin(t * 32.0 + h * 78.0)), 2.0);
            renderStar(buffer, matrix, p, scale * (0.08 + h * 0.22), live * (0.18 + blink * 0.92), h * 6.28);
         }
      }
   }

   private static void renderFlightShards(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, ElfariaOpIceShineVfxClient.Flight flight, long now) {
      double live = flight.live(now);
      if (!(live <= 0.002)) {
         double t = flight.t(now);
         Vec3 axis = flight.visualEnd.subtract(flight.start);
         if (!(axis.lengthSqr() < 0.001)) {
            Vec3 forward = axis.normalize();
            Vec3 up = Math.abs(forward.y) > 0.9 ? new Vec3(1.0, 0.0, 0.0) : new Vec3(0.0, 1.0, 0.0);
            Vec3 side = forward.cross(up).normalize();
            Vec3 normal = side.cross(forward).normalize();
            Vec3 head = flight.start.lerp(flight.visualEnd, ElfariaVfxVertexHelper.smooth(Math.min(1.0, t * 1.04)));
            Vec3 c = ElfariaVfxVertexHelper.rel(head, cam);
            double scale = Math.max(flight.main ? 5.0 : 1.8, flight.radius) * (flight.main ? 2.45 : 1.15);

            for (int i = 0; i < 52; i++) {
               double h = ElfariaVfxVertexHelper.hash(flight.seed, 23000L + i);
               Vec3 base = c.subtract(forward.scale(scale * (0.8 + h * 8.0)))
                  .add(side.scale((h - 0.5) * scale * 7.0))
                  .add(normal.scale((ElfariaVfxVertexHelper.hash(flight.seed, 23100L + i) - 0.5) * scale * 4.2));
               Vec3 tip = base.add(forward.scale(scale * (0.7 + h))).add(normal.scale(scale * (0.3 + h * 0.7)));
               Vec3 s = side.scale(scale * (0.035 + h * 0.07));
               ElfariaVfxVertexHelper.tri(
                  buffer, matrix, base.add(s), base.subtract(s), tip, ElfariaVfxVertexHelper.scaleAlpha((i & 1) == 0 ? WHITE : PALE, live * (0.46 + h * 0.44))
               );
            }
         }
      }
   }

   private static void renderFlightLines(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, ElfariaOpIceShineVfxClient.Flight flight, long now) {
      double live = flight.live(now);
      if (!(live <= 0.002)) {
         double t = flight.t(now);
         Vec3 axis = flight.visualEnd.subtract(flight.start);
         if (!(axis.lengthSqr() < 0.001)) {
            Vec3 forward = axis.normalize();
            Vec3 up = Math.abs(forward.y) > 0.9 ? new Vec3(1.0, 0.0, 0.0) : new Vec3(0.0, 1.0, 0.0);
            Vec3 side = forward.cross(up).normalize();
            Vec3 normal = side.cross(forward).normalize();
            double length = axis.length();
            int count = flight.main ? 70 : 38;

            for (int i = 0; i < count; i++) {
               double h = ElfariaVfxVertexHelper.hash(flight.seed, 24000L + i);
               double a = Math.max(0.0, t - h * 0.55) * length;
               Vec3 p0 = ElfariaVfxVertexHelper.rel(flight.start.add(forward.scale(a)), cam).add(side.scale((h - 0.5) * flight.radius * 8.0));
               Vec3 p1 = p0.subtract(forward.scale(flight.radius * (2.0 + h * 7.0))).add(normal.scale((h - 0.5) * flight.radius * 4.0));
               ElfariaVfxVertexHelper.line(buffer, matrix, p0, p1, ElfariaVfxVertexHelper.scaleAlpha((i & 6) == 0 ? WHITE : ICE, live * (0.36 + h * 0.48)));
            }
         }
      }
   }

   private static void renderStar(BufferBuilder buffer, Matrix4f matrix, Vec3 center, double size, double live, double spin) {
      Vec3 side = ElfariaVfxVertexHelper.cameraSide(center);
      Vec3 camForward = ElfariaVfxVertexHelper.camera().subtract(center);
      if (camForward.lengthSqr() < 1.0E-4) {
         camForward = new Vec3(0.0, 0.0, 1.0);
      }

      camForward = camForward.normalize();
      Vec3 up = side.cross(camForward).normalize();
      Vec3 a = side.scale(Math.cos(spin)).add(up.scale(Math.sin(spin))).normalize();
      Vec3 b = side.scale(Math.cos(spin + (Math.PI / 2))).add(up.scale(Math.sin(spin + (Math.PI / 2)))).normalize();
      ElfariaVfxVertexHelper.quad(
         buffer,
         matrix,
         center.add(a.scale(size * 1.35)),
         center.add(b.scale(size * 0.18)),
         center.add(a.scale(-size * 1.35)),
         center.add(b.scale(-size * 0.18)),
         ElfariaVfxVertexHelper.scaleAlpha(WHITE, live)
      );
      ElfariaVfxVertexHelper.quad(
         buffer,
         matrix,
         center.add(b.scale(size * 0.92)),
         center.add(a.scale(size * 0.14)),
         center.add(b.scale(-size * 0.92)),
         center.add(a.scale(-size * 0.14)),
         ElfariaVfxVertexHelper.scaleAlpha(PALE, live * 0.72)
      );
   }

   private static final class Flight {
      private final Vec3 start;
      private final Vec3 target;
      private final Vec3 visualEnd;
      private final float radius;
      private final long seed;
      private final boolean main;
      private final long created;
      private final long lifeMs;

      private Flight(Vec3 start, Vec3 target, Vec3 visualEnd, float radius, long seed, boolean main, long created) {
         this.start = start;
         this.target = target;
         this.visualEnd = visualEnd;
         this.radius = radius;
         this.seed = seed;
         this.main = main;
         this.created = created;
         this.lifeMs = (long)Math.min(main ? 22000.0 : 9000.0, Math.max(main ? 9000.0 : 3000.0, 2200.0 + start.distanceTo(visualEnd) * (main ? 54.0 : 34.0)));
      }

      private double t(long now) {
         return Math.min(1.0, Math.max(0.0, (double)(now - this.created) / this.lifeMs));
      }

      private double live(long now) {
         double t = this.t(now);
         double birth = ElfariaVfxVertexHelper.smooth(Math.min(1.0, t / 0.06));
         double fade = 1.0 - ElfariaVfxVertexHelper.smooth(Math.max(0.0, (t - 0.88) / 0.12));
         return birth * fade;
      }

      private boolean dead(long now) {
         return now - this.created > this.lifeMs;
      }
   }

   private static final class Storm {
      private final Vec3 center;
      private final float radius;
      private final long seed;
      private final int lifeMs;
      private final int strength;
      private final boolean huge;
      private final long created;

      private Storm(Vec3 center, float radius, long seed, int lifeMs, int strength, boolean huge, long created) {
         this.center = center;
         this.radius = radius;
         this.seed = seed;
         this.lifeMs = lifeMs;
         this.strength = strength;
         this.huge = huge;
         this.created = created;
      }

      private double t(long now) {
         return Math.min(1.0, Math.max(0.0, (double)(now - this.created) / this.lifeMs));
      }

      private double live(long now) {
         double t = this.t(now);
         double birth = ElfariaVfxVertexHelper.smooth(Math.min(1.0, t / 0.08));
         double fade = 1.0 - ElfariaVfxVertexHelper.smooth(Math.max(0.0, (t - 0.78) / 0.22));
         return birth * fade * Math.min(2.0, 0.75 + this.strength * 0.25);
      }

      private boolean dead(long now) {
         return now - this.created > this.lifeMs;
      }
   }
}
