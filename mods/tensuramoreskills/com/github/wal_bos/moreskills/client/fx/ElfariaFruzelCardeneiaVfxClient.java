package com.github.wal_bos.moreskills.client.fx;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

public final class ElfariaFruzelCardeneiaVfxClient {
   private static final List<ElfariaFruzelCardeneiaVfxClient.Cathedral> CATHEDRALS = new ArrayList<>();
   private static final List<ElfariaFruzelCardeneiaVfxClient.Spike> SPIKES = new ArrayList<>();
   private static final int ICE = ElfariaVfxVertexHelper.rgba(72, 198, 246, 124);
   private static final int ICE_WALL = ElfariaVfxVertexHelper.rgba(92, 216, 255, 142);
   private static final int ICE_SOFT = ElfariaVfxVertexHelper.rgba(138, 236, 255, 116);
   private static final int BRIGHT = ElfariaVfxVertexHelper.rgba(240, 255, 255, 226);
   private static final int STAR = ElfariaVfxVertexHelper.rgba(255, 255, 255, 246);
   private static final int DEEP = ElfariaVfxVertexHelper.rgba(30, 120, 212, 96);
   private static final int DARK = ElfariaVfxVertexHelper.rgba(12, 64, 154, 78);
   private static final int LINE = ElfariaVfxVertexHelper.rgba(232, 254, 255, 230);
   private static final int RUNE = ElfariaVfxVertexHelper.rgba(198, 246, 255, 210);
   private static final int AURA = ElfariaVfxVertexHelper.rgba(112, 228, 255, 150);
   private static final int MIST = ElfariaVfxVertexHelper.rgba(204, 252, 255, 90);
   private static final int CORE = ElfariaVfxVertexHelper.rgba(255, 255, 255, 255);
   private static final int EDGE = ElfariaVfxVertexHelper.rgba(156, 244, 255, 190);
   private static final int SHADOW = ElfariaVfxVertexHelper.rgba(16, 86, 190, 74);
   private static final int VEIN = ElfariaVfxVertexHelper.rgba(206, 252, 255, 198);
   private static final int GLASS = ElfariaVfxVertexHelper.rgba(104, 220, 255, 134);
   private static final int HALO = ElfariaVfxVertexHelper.rgba(232, 255, 255, 184);
   private static final int BLIZZARD = ElfariaVfxVertexHelper.rgba(228, 254, 255, 156);
   private static final int CROWN = ElfariaVfxVertexHelper.rgba(255, 255, 255, 232);
   private static final int DEEP_LINE = ElfariaVfxVertexHelper.rgba(52, 154, 232, 152);
   private static final int PALE_BLUE = ElfariaVfxVertexHelper.rgba(174, 246, 255, 180);
   private static final int WHITE_BLUE = ElfariaVfxVertexHelper.rgba(244, 255, 255, 236);

   private ElfariaFruzelCardeneiaVfxClient() {
   }

   public static void spawnCathedral(Vec3 center, Vec3 caster, float radius, int life, long seed, boolean main) {
      float boostedRadius = main ? Math.max(radius * 1.28F, radius + 2.6F) : Math.max(radius * 1.14F, radius + 1.4F);
      int boostedLife = Math.max(main ? 150 : 118, life + (main ? 42 : 24));

      for (ElfariaFruzelCardeneiaVfxClient.Cathedral cathedral : CATHEDRALS) {
         if (cathedral.center.distanceToSqr(center) <= Math.max(24.0, boostedRadius * boostedRadius * 0.5)) {
            cathedral.refresh(center, caster, boostedRadius, boostedLife, seed, main);
            return;
         }
      }

      CATHEDRALS.add(new ElfariaFruzelCardeneiaVfxClient.Cathedral(center, caster, boostedRadius, boostedLife, seed, main));

      while (CATHEDRALS.size() > 1) {
         CATHEDRALS.remove(0);
      }
   }

   public static void spawnSpike(Vec3 center, float radius, long seed) {
      SPIKES.add(new ElfariaFruzelCardeneiaVfxClient.Spike(center, Math.max(1.2F, radius * 0.95F), 32, seed));

      while (SPIKES.size() > 18) {
         SPIKES.remove(0);
      }
   }

   public static void tick() {
      CATHEDRALS.removeIf(v -> ++v.age >= v.expireAge);
      SPIKES.removeIf(v -> ++v.age >= v.life);
   }

   public static void render(PoseStack poseStack, float partialTicks) {
      if (!CATHEDRALS.isEmpty() || !SPIKES.isEmpty()) {
         ElfariaVfxVertexHelper.beginIce();
         Vec3 cam = ElfariaVfxVertexHelper.camera();
         Matrix4f matrix = poseStack.last().pose();
         BufferBuilder quads = ElfariaVfxVertexHelper.beginQuads();

         for (ElfariaFruzelCardeneiaVfxClient.Cathedral cathedral : CATHEDRALS) {
            renderCathedralSheets(quads, matrix, cam, cathedral, partialTicks);
            renderCathedralHaloSheets(quads, matrix, cam, cathedral, partialTicks);
            renderCathedralWindows(quads, matrix, cam, cathedral, partialTicks);
         }

         for (ElfariaFruzelCardeneiaVfxClient.Spike spike : SPIKES) {
            renderSpikeSheet(quads, matrix, cam, spike, partialTicks);
         }

         ElfariaVfxVertexHelper.finish(quads);
         BufferBuilder tris = ElfariaVfxVertexHelper.beginTriangles();

         for (ElfariaFruzelCardeneiaVfxClient.Cathedral cathedral : CATHEDRALS) {
            renderCathedralSpikes(tris, matrix, cam, cathedral, partialTicks);
         }

         for (ElfariaFruzelCardeneiaVfxClient.Spike spike : SPIKES) {
            renderSpikePrisms(tris, matrix, cam, spike, partialTicks);
         }

         ElfariaVfxVertexHelper.finish(tris);
         BufferBuilder lines = ElfariaVfxVertexHelper.beginLines();

         for (ElfariaFruzelCardeneiaVfxClient.Cathedral cathedral : CATHEDRALS) {
            renderCathedralLines(lines, matrix, cam, cathedral, partialTicks);
         }

         for (ElfariaFruzelCardeneiaVfxClient.Spike spike : SPIKES) {
            renderSpikeLines(lines, matrix, cam, spike, partialTicks);
         }

         ElfariaVfxVertexHelper.finish(lines);
         ElfariaVfxVertexHelper.endIce();
      }
   }

   private static double live(ElfariaFruzelCardeneiaVfxClient.Cathedral c, float partialTicks) {
      double age = c.age + partialTicks;
      double birth = ElfariaVfxVertexHelper.smooth(Math.min(1.0, age / 22.0));
      double remain = c.expireAge - c.age - partialTicks;
      double fade = ElfariaVfxVertexHelper.smooth(Math.min(1.0, remain / 30.0));
      return birth * fade;
   }

   private static double birth(ElfariaFruzelCardeneiaVfxClient.Cathedral c, float partialTicks) {
      return ElfariaVfxVertexHelper.smooth(Math.min(1.0, (c.age + partialTicks) / 24.0));
   }

   private static double finale(ElfariaFruzelCardeneiaVfxClient.Cathedral c, float partialTicks) {
      double t = (c.age + partialTicks) / Math.max(1.0, c.expireAge);
      return ElfariaVfxVertexHelper.smooth(Math.max(0.0, Math.min(1.0, (t - 0.72) / 0.28)));
   }

   private static double pulse(ElfariaFruzelCardeneiaVfxClient.Cathedral c, float partialTicks, double speed, double salt) {
      return 0.55 + 0.45 * Math.abs(Math.sin((c.age + partialTicks) * speed + salt));
   }

   private static ElfariaFruzelCardeneiaVfxClient.Basis basis(ElfariaFruzelCardeneiaVfxClient.Cathedral cathedral, Vec3 cam) {
      Vec3 forward = cathedral.center.subtract(cathedral.caster);
      forward = new Vec3(forward.x, 0.0, forward.z);
      if (forward.lengthSqr() < 0.001) {
         forward = new Vec3(0.0, 0.0, 1.0);
      }

      forward = forward.normalize();
      Vec3 right = new Vec3(forward.z, 0.0, -forward.x).normalize();
      return new ElfariaFruzelCardeneiaVfxClient.Basis(ElfariaVfxVertexHelper.rel(cathedral.center, cam), forward, right);
   }

   private static void renderCathedralSheets(
      BufferBuilder buffer, Matrix4f matrix, Vec3 cam, ElfariaFruzelCardeneiaVfxClient.Cathedral cathedral, float partialTicks
   ) {
      double live = live(cathedral, partialTicks);
      if (!(live <= 0.002)) {
         ElfariaFruzelCardeneiaVfxClient.Basis b = basis(cathedral, cam);
         double r = cathedral.radius;
         double length = r * 3.24;
         double width = r * 1.36;
         double wall = r * 1.88;
         double roof = r * 3.06;
         double birth = birth(cathedral, partialTicks);
         double finalPulse = finale(cathedral, partialTicks);
         double surge = 1.0 + pulse(cathedral, partialTicks, 0.1, cathedral.seed * 0.001) * 0.075;
         ElfariaAlbisIceSheetRenderer.albisRectSheet(
            buffer, matrix, b.center.add(0.0, 0.018, 0.0), b.forward, b.right, length * surge, width * surge, live * 0.92, cathedral.seed, 0.0
         );

         for (int layer = 0; layer < 3; layer++) {
            double f = layer / 2.0;
            double lift = 0.03 + layer * 0.03;
            double scale = 1.0 + f * 0.74 + finalPulse * 0.16;
            double alpha = live * (0.52 - f * 0.045);
            Vec3 center = b.center.add(0.0, lift, 0.0);
            ElfariaAlbisIceSheetRenderer.albisRectSheet(
               buffer, matrix, center, b.forward, b.right, length * scale, width * (1.12 + f * 0.5), alpha, cathedral.seed + 400L + layer, 0.014 + layer * 0.01
            );
         }

         Vec3 leftBase = b.center.add(b.right.scale(width));
         Vec3 rightBase = b.center.add(b.right.scale(-width));
         verticalWall(buffer, matrix, leftBase, b.forward, wall, length, live * 1.04, cathedral.seed + 10L, false);
         verticalWall(buffer, matrix, rightBase, b.forward, wall, length, live * 1.04, cathedral.seed + 20L, true);

         for (int layer = 0; layer < 2; layer++) {
            double inset = r * 0.16 * layer;
            double y = wall * (0.18 + layer * 0.15);
            verticalVeil(
               buffer,
               matrix,
               leftBase.add(b.right.scale(inset)).add(0.0, y, 0.0),
               b.forward,
               wall * (0.38 + layer * 0.08),
               length * (0.92 - layer * 0.035),
               live * (0.34 + layer * 0.08),
               cathedral.seed + 1000L + layer,
               false
            );
            verticalVeil(
               buffer,
               matrix,
               rightBase.add(b.right.scale(-inset)).add(0.0, y, 0.0),
               b.forward,
               wall * (0.38 + layer * 0.08),
               length * (0.92 - layer * 0.035),
               live * (0.34 + layer * 0.08),
               cathedral.seed + 1100L + layer,
               true
            );
         }

         Vec3 ridge = b.center.add(0.0, roof, 0.0);
         roofSheet(buffer, matrix, leftBase.add(0.0, wall, 0.0), ridge, b.forward, length, live * 1.08, cathedral.seed + 30L);
         roofSheet(buffer, matrix, rightBase.add(0.0, wall, 0.0), ridge, b.forward, length, live * 1.08, cathedral.seed + 40L);

         for (int i = 0; i < 2; i++) {
            double off = (i - 2.5) * r * 0.045;
            Vec3 high = b.center.add(0.0, roof + off, 0.0);
            roofSheet(
               buffer,
               matrix,
               leftBase.add(0.0, wall + off * 0.32, 0.0),
               high,
               b.forward,
               length * (0.98 - i * 0.018),
               live * (0.3 + i * 0.035),
               cathedral.seed + 1800L + i
            );
            roofSheet(
               buffer,
               matrix,
               rightBase.add(0.0, wall + off * 0.32, 0.0),
               high,
               b.forward,
               length * (0.98 - i * 0.018),
               live * (0.3 + i * 0.035),
               cathedral.seed + 1900L + i
            );
         }

         endWall(buffer, matrix, b.center.add(b.forward.scale(length)), b.right, wall, roof, width, live, cathedral.seed + 50L);
         endWall(buffer, matrix, b.center.add(b.forward.scale(-length)), b.right, wall, roof, width, live, cathedral.seed + 60L);

         for (int side = -1; side <= 1; side += 2) {
            for (int i = -4; i <= 4; i++) {
               double local = i / 5.0;
               Vec3 base = b.center.add(b.forward.scale(local * length * 0.92)).add(b.right.scale(side * (width + r * 0.36)));
               buttress(buffer, matrix, base, b.right.scale(side), wall * (1.02 + Math.abs(local) * 0.24), r * (0.18 + 0.03 * Math.abs(i)), live);
               Vec3 outer = base.add(b.right.scale(side * r * 0.34));
               buttress(buffer, matrix, outer, b.right.scale(side), wall * (0.6 + Math.abs(local) * 0.12), r * 0.1, live * 0.66);
            }
         }

         Vec3 apse = b.center.add(b.forward.scale(length + r * 0.52));
         ElfariaAlbisIceSheetRenderer.albisDisc(buffer, matrix, apse.add(0.0, wall * 0.84, 0.0), r * 0.88, live * 0.82, cathedral.seed + 70L, 0.0);
         Vec3 rearApse = b.center.add(b.forward.scale(-length - r * 0.52));
         ElfariaAlbisIceSheetRenderer.albisDisc(buffer, matrix, rearApse.add(0.0, wall * 0.84, 0.0), r * 0.88, live * 0.72, cathedral.seed + 80L, 0.0);

         for (int i = 0; i < 5; i++) {
            double f = (i - 5) / 5.0;
            Vec3 nave = b.center.add(b.forward.scale(f * length * 0.78)).add(0.0, wall * 0.62, 0.0);
            double blink = pulse(cathedral, partialTicks, 0.14, i + cathedral.seed * 0.01);
            ElfariaAlbisIceSheetRenderer.albisDisc(
               buffer, matrix, nave, r * (0.52 + 0.12 * (i & 1)), live * (0.26 + blink * 0.2), cathedral.seed + 900L + i, 0.0
            );
         }

         renderLongNaveMist(buffer, matrix, b, cathedral, partialTicks, live, length, width, wall, roof);
         renderGroundDiamondLattice(buffer, matrix, b, cathedral, partialTicks, live, length, width, r);
         renderFloatingGlassPanels(buffer, matrix, b, cathedral, partialTicks, live, length, width, wall, roof);
      }
   }

   private static void renderCathedralAuras(
      BufferBuilder buffer, Matrix4f matrix, Vec3 cam, ElfariaFruzelCardeneiaVfxClient.Cathedral cathedral, float partialTicks
   ) {
      double live = live(cathedral, partialTicks);
      if (!(live <= 0.002)) {
         ElfariaFruzelCardeneiaVfxClient.Basis b = basis(cathedral, cam);
         double r = cathedral.radius;
         double t = cathedral.age + partialTicks;
         double power = cathedral.main ? 1.22 : 1.0;

         for (int i = 0; i < 8; i++) {
            double f = i / 7.0;
            double spin = t * (0.01 + i * 0.003) + ElfariaVfxVertexHelper.hash(cathedral.seed, 22000L + i) * Math.PI * 2.0;
            Vec3 axis = new Vec3(Math.cos(spin), 0.0, Math.sin(spin)).normalize();
            Vec3 side = new Vec3(-axis.z, 0.0, axis.x).normalize();
            Vec3 center = b.center.add(0.0, r * (0.12 + f * 0.38), 0.0);
            double alpha = live * (0.26 + 0.12 * Math.sin(t * 0.055 + i));
            ElfariaAlbisIceSheetRenderer.albisRectSheet(
               buffer,
               matrix,
               center,
               axis,
               side,
               r * power * (2.1 + f * 0.84),
               r * power * (0.1 + f * 0.14),
               alpha,
               cathedral.seed + 22100L + i,
               0.06 + f * 0.028
            );
         }

         for (int i = 0; i < 9; i++) {
            double f = i / 8.0;
            Vec3 c = b.center.add(0.0, r * (0.18 + f * 3.18), 0.0);
            double scale = r * (1.88 + f * 0.98 + pulse(cathedral, partialTicks, 0.09, i) * 0.12);
            ElfariaAlbisIceSheetRenderer.albisDisc(buffer, matrix, c, scale, live * (0.18 + (1.0 - f) * 0.16), cathedral.seed + 23000L + i, 0.01 + f * 0.006);
         }
      }
   }

   private static void renderCathedralHaloSheets(
      BufferBuilder buffer, Matrix4f matrix, Vec3 cam, ElfariaFruzelCardeneiaVfxClient.Cathedral cathedral, float partialTicks
   ) {
      double live = live(cathedral, partialTicks);
      if (!(live <= 0.002)) {
         ElfariaFruzelCardeneiaVfxClient.Basis b = basis(cathedral, cam);
         double r = cathedral.radius;
         double roof = r * 3.06;
         double t = cathedral.age + partialTicks;

         for (int ring = 0; ring < 6; ring++) {
            double f = ring / 5.0;
            double y = roof * (0.56 + f * 0.32);
            double spin = t * (0.018 + ring * 0.006) + ElfariaVfxVertexHelper.hash(cathedral.seed, 24000L + ring) * Math.PI * 2.0;
            Vec3 axis = b.forward.scale(Math.cos(spin)).add(b.right.scale(Math.sin(spin))).normalize();
            Vec3 side = new Vec3(0.0, 1.0, 0.0);
            Vec3 center = b.center.add(0.0, y, 0.0);
            double longRadius = r * (1.4 + f * 1.38);
            double thinRadius = r * (0.045 + f * 0.028);
            ElfariaAlbisIceSheetRenderer.albisRectSheet(
               buffer, matrix, center, axis, side, longRadius, thinRadius, live * (0.54 - f * 0.045), cathedral.seed + 24100L + ring, 0.0
            );
            ElfariaAlbisIceSheetRenderer.albisRectSheet(
               buffer,
               matrix,
               center,
               b.right.scale(Math.cos(spin)).add(b.forward.scale(-Math.sin(spin))).normalize(),
               side,
               longRadius * 0.82,
               thinRadius * 0.82,
               live * (0.36 - f * 0.03),
               cathedral.seed + 24200L + ring,
               0.0
            );
         }
      }
   }

   private static void renderCathedralWindows(
      BufferBuilder buffer, Matrix4f matrix, Vec3 cam, ElfariaFruzelCardeneiaVfxClient.Cathedral cathedral, float partialTicks
   ) {
      double live = live(cathedral, partialTicks);
      if (!(live <= 0.002)) {
         ElfariaFruzelCardeneiaVfxClient.Basis b = basis(cathedral, cam);
         double r = cathedral.radius;
         double length = r * 3.24;
         double width = r * 1.36;
         double wall = r * 1.88;

         for (int sideSign = -1; sideSign <= 1; sideSign += 2) {
            for (int i = -6; i <= 6; i++) {
               double f = i / 6.0;
               Vec3 base = b.center.add(b.forward.scale(f * length * 0.82)).add(b.right.scale(sideSign * (width + r * 0.035))).add(0.0, wall * 0.6, 0.0);
               double blink = pulse(cathedral, partialTicks, 0.19, i * 0.7 + sideSign + cathedral.seed * 0.001);
               ElfariaAlbisIceSheetRenderer.albisRectSheet(
                  buffer,
                  matrix,
                  base,
                  new Vec3(0.0, 1.0, 0.0),
                  b.forward,
                  r * (0.56 + blink * 0.1),
                  r * 0.22,
                  live * (0.48 + blink * 0.24),
                  cathedral.seed + 25000L + i * 10L + sideSign,
                  0.0
               );
               Vec3 upper = base.add(0.0, r * 0.62, 0.0);
               ElfariaAlbisIceSheetRenderer.albisDisc(
                  buffer, matrix, upper, r * (0.2 + blink * 0.04), live * (0.32 + blink * 0.24), cathedral.seed + 25100L + i * 10L + sideSign, 0.0
               );
            }
         }

         for (int end = -1; end <= 1; end += 2) {
            Vec3 face = b.center.add(b.forward.scale(end * length * 1.012)).add(0.0, wall * 0.9, 0.0);

            for (int layer = 0; layer < 5; layer++) {
               double scale = r * (0.54 + layer * 0.16);
               double blink = pulse(cathedral, partialTicks, 0.16, layer + end * 4.0);
               ElfariaAlbisIceSheetRenderer.albisDisc(
                  buffer,
                  matrix,
                  face.add(0.0, layer * r * 0.025, 0.0),
                  scale,
                  live * blink * (0.34 - layer * 0.03),
                  cathedral.seed + 25200L + layer + end * 17L,
                  0.0
               );
            }
         }
      }
   }

   private static void renderCathedralSparkles(
      BufferBuilder buffer, Matrix4f matrix, Vec3 cam, ElfariaFruzelCardeneiaVfxClient.Cathedral cathedral, float partialTicks
   ) {
      double live = live(cathedral, partialTicks);
      if (!(live <= 0.002)) {
         ElfariaFruzelCardeneiaVfxClient.Basis b = basis(cathedral, cam);
         double r = cathedral.radius;
         double length = r * 3.24;
         double width = r * 1.8;
         double roof = r * 3.38;
         int count = cathedral.main ? 340 : 240;

         for (int i = 0; i < count; i++) {
            double h = ElfariaVfxVertexHelper.hash(cathedral.seed, 26000L + i);
            double f = ElfariaVfxVertexHelper.hash(cathedral.seed, 26100L + i);
            double k = ElfariaVfxVertexHelper.hash(cathedral.seed, 26200L + i);
            double sideOff = (h - 0.5) * width * 2.0;
            double forwardOff = (f - 0.5) * length * 2.0;
            double swirl = Math.sin((cathedral.age + partialTicks) * 0.046 + h * 16.0) * r * 0.18;
            double y = 0.26 + k * roof * 1.26 + Math.sin((cathedral.age + partialTicks) * 0.06 + i) * r * 0.05;
            Vec3 mote = b.center.add(b.forward.scale(forwardOff + swirl)).add(b.right.scale(sideOff - swirl * 0.4)).add(0.0, y, 0.0);
            double blink = 0.18 + 0.82 * Math.abs(Math.sin((cathedral.age + partialTicks) * (0.18 + h * 0.05) + k * 24.0));
            double size = r * (0.01 + f * 0.04);
            int color = (i & 9) == 0 ? STAR : ((i & 3) == 0 ? WHITE_BLUE : BRIGHT);
            ElfariaVfxVertexHelper.billboardDiamond(buffer, matrix, mote, size, ElfariaVfxVertexHelper.scaleAlpha(color, live * blink * (0.5 + k * 0.52)));
         }
      }
   }

   private static void renderCathedralBlizzard(
      BufferBuilder buffer, Matrix4f matrix, Vec3 cam, ElfariaFruzelCardeneiaVfxClient.Cathedral cathedral, float partialTicks
   ) {
      double live = live(cathedral, partialTicks);
      if (!(live <= 0.002)) {
         ElfariaFruzelCardeneiaVfxClient.Basis b = basis(cathedral, cam);
         double r = cathedral.radius;
         double length = r * 3.7;
         double width = r * 2.1;
         double roof = r * 3.7;
         int count = cathedral.main ? 220 : 150;
         double time = cathedral.age + partialTicks;

         for (int i = 0; i < count; i++) {
            double h = ElfariaVfxVertexHelper.hash(cathedral.seed, 27000L + i);
            double f = ElfariaVfxVertexHelper.hash(cathedral.seed, 27100L + i);
            double k = ElfariaVfxVertexHelper.hash(cathedral.seed, 27200L + i);
            double localTime = (time * (0.01 + k * 0.018) + h) % 1.0;
            double y = roof * (1.05 - localTime) + r * 0.16;
            double spin = h * Math.PI * 2.0 + time * (0.028 + k * 0.02);
            double sideOff = (f - 0.5) * width * 2.0 + Math.cos(spin) * r * 0.22;
            double forwardOff = (h - 0.5) * length * 2.0 + Math.sin(spin) * r * 0.22;
            Vec3 mote = b.center.add(b.forward.scale(forwardOff)).add(b.right.scale(sideOff)).add(0.0, y, 0.0);
            double size = r * (0.008 + k * 0.024);
            double alpha = live * (0.28 + 0.54 * Math.sin(localTime * Math.PI));
            ElfariaVfxVertexHelper.billboardDiamond(buffer, matrix, mote, size, ElfariaVfxVertexHelper.scaleAlpha(BLIZZARD, alpha));
         }
      }
   }

   private static void verticalWall(
      BufferBuilder buffer, Matrix4f matrix, Vec3 base, Vec3 forward, double height, double halfLength, double live, long seed, boolean flip
   ) {
      Vec3 up = new Vec3(0.0, 1.0, 0.0);
      Vec3 side = forward;
      Vec3 center = base.add(0.0, height * 0.5, 0.0);
      ElfariaAlbisIceSheetRenderer.albisRectSheet(buffer, matrix, center, up, side.scale(flip ? -1.0 : 1.0), height * 0.5, halfLength, live * 0.86, seed, 0.0);
   }

   private static void verticalVeil(
      BufferBuilder buffer, Matrix4f matrix, Vec3 base, Vec3 forward, double height, double halfLength, double live, long seed, boolean flip
   ) {
      Vec3 up = new Vec3(0.0, 1.0, 0.0);
      Vec3 side = forward.scale(flip ? -1.0 : 1.0);
      Vec3 center = base.add(0.0, height * 0.5, 0.0);
      ElfariaAlbisIceSheetRenderer.albisRectSheet(buffer, matrix, center, up, side, height * 0.5, halfLength, live, seed, 0.018);
   }

   private static void roofSheet(BufferBuilder buffer, Matrix4f matrix, Vec3 eave, Vec3 ridgeCenter, Vec3 forward, double halfLength, double live, long seed) {
      Vec3 slope = ridgeCenter.subtract(eave);
      double halfWidth = slope.length();
      if (!(halfWidth < 0.001)) {
         Vec3 axis = slope.normalize();
         Vec3 center = eave.add(ridgeCenter).scale(0.5);
         ElfariaAlbisIceSheetRenderer.albisRectSheet(buffer, matrix, center, axis, forward, halfWidth, halfLength, live, seed, 0.0);
      }
   }

   private static void endWall(BufferBuilder buffer, Matrix4f matrix, Vec3 base, Vec3 right, double wall, double roof, double width, double live, long seed) {
      Vec3 up = new Vec3(0.0, 1.0, 0.0);
      ElfariaAlbisIceSheetRenderer.albisRectSheet(buffer, matrix, base.add(0.0, wall * 0.5, 0.0), up, right, wall * 0.5, width, live * 0.78, seed, 0.0);
      Vec3 top = base.add(0.0, roof, 0.0);
      Vec3 l = base.add(right.scale(width)).add(0.0, wall, 0.0);
      Vec3 r = base.add(right.scale(-width)).add(0.0, wall, 0.0);
      Vec3 m0 = l.lerp(top, 0.5);
      Vec3 m1 = r.lerp(top, 0.5);
      ElfariaVfxVertexHelper.quad(buffer, matrix, l, r, m1, m0, ElfariaVfxVertexHelper.scaleAlpha(ICE_WALL, live * 0.88));
      ElfariaVfxVertexHelper.quad(buffer, matrix, m0, m1, top, top.add(0.0, 0.012, 0.0), ElfariaVfxVertexHelper.scaleAlpha(ICE, live * 0.78));
   }

   private static void buttress(BufferBuilder buffer, Matrix4f matrix, Vec3 base, Vec3 outward, double height, double width, double live) {
      Vec3 side = new Vec3(-outward.z, 0.0, outward.x).normalize();
      Vec3 lowA = base.add(side.scale(width));
      Vec3 lowB = base.add(side.scale(-width));
      Vec3 highA = base.add(side.scale(width * 0.55)).add(outward.scale(width * 1.8)).add(0.0, height, 0.0);
      Vec3 highB = base.add(side.scale(-width * 0.55)).add(outward.scale(width * 1.8)).add(0.0, height, 0.0);
      Vec3 capA = highA.add(outward.scale(width * 0.44)).add(0.0, width * 0.9, 0.0);
      Vec3 capB = highB.add(outward.scale(width * 0.44)).add(0.0, width * 0.9, 0.0);
      ElfariaVfxVertexHelper.quad(buffer, matrix, lowA, lowB, highB, highA, ElfariaVfxVertexHelper.scaleAlpha(DEEP, live * 0.76));
      ElfariaVfxVertexHelper.quad(buffer, matrix, highA, highB, capB, capA, ElfariaVfxVertexHelper.scaleAlpha(ICE_SOFT, live * 0.52));
   }

   private static void renderLongNaveMist(
      BufferBuilder buffer,
      Matrix4f matrix,
      ElfariaFruzelCardeneiaVfxClient.Basis b,
      ElfariaFruzelCardeneiaVfxClient.Cathedral cathedral,
      float partialTicks,
      double live,
      double length,
      double width,
      double wall,
      double roof
   ) {
      double r = cathedral.radius;
      double time = cathedral.age + partialTicks;

      for (int i = 0; i < 38; i++) {
         double h = ElfariaVfxVertexHelper.hash(cathedral.seed, 28000L + i);
         double f = ElfariaVfxVertexHelper.hash(cathedral.seed, 28100L + i);
         double x = (h - 0.5) * length * 1.75;
         double side = (f - 0.5) * width * 1.3;
         double y = wall * (0.18 + 0.6 * ElfariaVfxVertexHelper.hash(cathedral.seed, 28200L + i));
         double drift = Math.sin(time * 0.032 + i * 0.8) * r * 0.12;
         Vec3 center = b.center.add(b.forward.scale(x + drift)).add(b.right.scale(side - drift * 0.4)).add(0.0, y, 0.0);
         Vec3 axis = b.forward.scale(Math.cos(h * Math.PI * 2.0)).add(b.right.scale(Math.sin(h * Math.PI * 2.0))).normalize();
         Vec3 cross = new Vec3(0.0, 1.0, 0.0);
         double alpha = live * (0.12 + 0.18 * pulse(cathedral, partialTicks, 0.04, i));
         ElfariaAlbisIceSheetRenderer.albisRectSheet(
            buffer, matrix, center, axis, cross, r * (0.62 + f * 1.2), r * (0.08 + h * 0.18), alpha, cathedral.seed + 28300L + i, 0.04
         );
      }
   }

   private static void renderGroundDiamondLattice(
      BufferBuilder buffer,
      Matrix4f matrix,
      ElfariaFruzelCardeneiaVfxClient.Basis b,
      ElfariaFruzelCardeneiaVfxClient.Cathedral cathedral,
      float partialTicks,
      double live,
      double length,
      double width,
      double r
   ) {
      for (int lane = -6; lane <= 6; lane++) {
         double f = lane / 6.0;
         Vec3 center = b.center.add(b.right.scale(f * width * 0.98)).add(0.0, 0.082 + Math.abs(f) * 0.012, 0.0);
         double alpha = live * (0.18 + (1.0 - Math.abs(f)) * 0.26);
         ElfariaAlbisIceSheetRenderer.albisRectSheet(
            buffer, matrix, center, b.forward, b.right, length * 0.96, r * 0.018, alpha, cathedral.seed + 29000L + lane, 0.0
         );
      }

      for (int lane = -9; lane <= 9; lane++) {
         double f = lane / 9.0;
         Vec3 center = b.center.add(b.forward.scale(f * length * 0.92)).add(0.0, 0.096 + Math.abs(f) * 0.01, 0.0);
         double alpha = live * (0.16 + (1.0 - Math.abs(f)) * 0.24);
         ElfariaAlbisIceSheetRenderer.albisRectSheet(
            buffer, matrix, center, b.right, b.forward, width * 0.98, r * 0.014, alpha, cathedral.seed + 29100L + lane, 0.0
         );
      }
   }

   private static void renderFloatingGlassPanels(
      BufferBuilder buffer,
      Matrix4f matrix,
      ElfariaFruzelCardeneiaVfxClient.Basis b,
      ElfariaFruzelCardeneiaVfxClient.Cathedral cathedral,
      float partialTicks,
      double live,
      double length,
      double width,
      double wall,
      double roof
   ) {
      double r = cathedral.radius;
      double time = cathedral.age + partialTicks;

      for (int i = 0; i < 34; i++) {
         double h = ElfariaVfxVertexHelper.hash(cathedral.seed, 30000L + i);
         double f = ElfariaVfxVertexHelper.hash(cathedral.seed, 30100L + i);
         int side = (i & 1) == 0 ? 1 : -1;
         double forwardOff = (h - 0.5) * length * 1.72;
         double sideOff = side * (width * (0.86 + f * 0.42));
         double y = wall * (0.28 + ElfariaVfxVertexHelper.hash(cathedral.seed, 30200L + i) * 0.8);
         double wobble = Math.sin(time * 0.045 + h * 18.0) * r * 0.04;
         Vec3 center = b.center.add(b.forward.scale(forwardOff)).add(b.right.scale(sideOff + wobble)).add(0.0, y, 0.0);
         Vec3 up = new Vec3(0.0, 1.0, 0.0);
         Vec3 axis = b.forward.scale(side);
         double alpha = live * (0.28 + pulse(cathedral, partialTicks, 0.11, i) * 0.34);
         ElfariaAlbisIceSheetRenderer.albisRectSheet(
            buffer, matrix, center, up, axis, r * (0.18 + f * 0.3), r * (0.08 + h * 0.22), alpha, cathedral.seed + 30300L + i, 0.0
         );
      }
   }

   private static void renderCathedralSpikes(
      BufferBuilder buffer, Matrix4f matrix, Vec3 cam, ElfariaFruzelCardeneiaVfxClient.Cathedral cathedral, float partialTicks
   ) {
      double live = live(cathedral, partialTicks);
      if (!(live <= 0.002)) {
         ElfariaFruzelCardeneiaVfxClient.Basis b = basis(cathedral, cam);
         double r = cathedral.radius;
         double length = r * 3.24;
         double width = r * 1.36;
         double wall = r * 1.88;
         double roof = r * 3.06;
         Vec3[] towers = new Vec3[]{
            b.center.add(b.forward.scale(length)).add(b.right.scale(width)),
            b.center.add(b.forward.scale(length)).add(b.right.scale(-width)),
            b.center.add(b.forward.scale(-length)).add(b.right.scale(width)),
            b.center.add(b.forward.scale(-length)).add(b.right.scale(-width)),
            b.center.add(b.forward.scale(length * 0.56)).add(b.right.scale(width * 1.1)),
            b.center.add(b.forward.scale(length * 0.56)).add(b.right.scale(-width * 1.1)),
            b.center.add(b.forward.scale(-length * 0.56)).add(b.right.scale(width * 1.1)),
            b.center.add(b.forward.scale(-length * 0.56)).add(b.right.scale(-width * 1.1))
         };

         for (int i = 0; i < towers.length; i++) {
            double height = roof * (0.82 + ElfariaVfxVertexHelper.hash(cathedral.seed, 31000L + i) * 0.46);
            double radius = r * (0.18 + ElfariaVfxVertexHelper.hash(cathedral.seed, 31100L + i) * 0.08);
            ElfariaAlbisIceSheetRenderer.prism(
               buffer, matrix, towers[i].add(0.0, wall * 0.26, 0.0), height, radius, 6 + (i & 1), live, cathedral.seed + 100L + i
            );
            ElfariaAlbisIceSheetRenderer.prism(
               buffer, matrix, towers[i].add(0.0, wall * 0.88 + height * 0.54, 0.0), height * 0.34, radius * 0.42, 5, live * 0.78, cathedral.seed + 180L + i
            );
         }

         ElfariaAlbisIceSheetRenderer.prism(buffer, matrix, b.center.add(0.0, roof * 0.84, 0.0), r * 1.64, r * 0.24, 8, live, cathedral.seed + 140L);
         ElfariaAlbisIceSheetRenderer.prism(buffer, matrix, b.center.add(0.0, roof * 1.28, 0.0), r * 0.96, r * 0.13, 9, live * 0.84, cathedral.seed + 141L);
         int outer = cathedral.main ? 48 : 32;

         for (int i = 0; i < outer; i++) {
            double a = i * Math.PI * 2.0 / outer;
            double noise = ElfariaVfxVertexHelper.hash(cathedral.seed, 32000L + i);
            double rr = r * (1.4 + noise * 3.4);
            Vec3 base = b.center.add(Math.cos(a) * rr, 0.04, Math.sin(a) * rr);
            double h = r * (0.5 + ElfariaVfxVertexHelper.hash(cathedral.seed, 32100L + i) * 2.2);
            double widthSpike = r * (0.045 + ElfariaVfxVertexHelper.hash(cathedral.seed, 32200L + i) * 0.055);
            ElfariaAlbisIceSheetRenderer.prism(buffer, matrix, base, h, widthSpike, 5 + i % 3, live * (0.56 + noise * 0.42), cathedral.seed + i);
         }
      }
   }

   private static void renderCathedralObelisks(
      BufferBuilder buffer, Matrix4f matrix, Vec3 cam, ElfariaFruzelCardeneiaVfxClient.Cathedral cathedral, float partialTicks
   ) {
      double live = live(cathedral, partialTicks);
      if (!(live <= 0.002)) {
         ElfariaFruzelCardeneiaVfxClient.Basis b = basis(cathedral, cam);
         double r = cathedral.radius;
         double length = r * 3.24;
         double width = r * 1.36;

         for (int end = -1; end <= 1; end += 2) {
            for (int side = -1; side <= 1; side += 2) {
               Vec3 base = b.center.add(b.forward.scale(end * length * 1.22)).add(b.right.scale(side * width * 1.24));
               double phase = ElfariaVfxVertexHelper.hash(cathedral.seed, 33000L + end * 19L + side * 7L);
               double grow = 0.72 + pulse(cathedral, partialTicks, 0.1, phase * 8.0) * 0.22;
               ElfariaAlbisIceSheetRenderer.prism(
                  buffer,
                  matrix,
                  base.add(0.0, 0.04, 0.0),
                  r * (2.4 + phase * 1.0) * grow,
                  r * (0.18 + phase * 0.06),
                  7,
                  live * 0.92,
                  cathedral.seed + 33100L + end * 19L + side * 7L
               );
            }
         }

         for (int i = 0; i < 12; i++) {
            double a = i * Math.PI * 2.0 / 12.0 + ElfariaVfxVertexHelper.hash(cathedral.seed, 33200L + i) * 0.12;
            double dist = r * (3.7 + ElfariaVfxVertexHelper.hash(cathedral.seed, 33300L + i) * 0.8);
            Vec3 base = b.center.add(Math.cos(a) * dist, 0.05, Math.sin(a) * dist);
            double height = r * (1.2 + ElfariaVfxVertexHelper.hash(cathedral.seed, 33400L + i) * 1.9);
            double radius = r * (0.08 + ElfariaVfxVertexHelper.hash(cathedral.seed, 33500L + i) * 0.08);
            ElfariaAlbisIceSheetRenderer.prism(buffer, matrix, base, height, radius, 5 + (i & 1), live * 0.74, cathedral.seed + 33600L + i);
         }
      }
   }

   private static void renderCathedralCrown(
      BufferBuilder buffer, Matrix4f matrix, Vec3 cam, ElfariaFruzelCardeneiaVfxClient.Cathedral cathedral, float partialTicks
   ) {
      double live = live(cathedral, partialTicks);
      if (!(live <= 0.002)) {
         ElfariaFruzelCardeneiaVfxClient.Basis b = basis(cathedral, cam);
         double r = cathedral.radius;
         double roof = r * 3.06;
         double time = cathedral.age + partialTicks;

         for (int i = 0; i < 18; i++) {
            double a = i * Math.PI * 2.0 / 18.0 + time * 0.01;
            double dist = r * (0.92 + i % 3 * 0.22);
            Vec3 base = b.center.add(Math.cos(a) * dist, roof * 1.09, Math.sin(a) * dist);
            double h = r * (0.44 + ElfariaVfxVertexHelper.hash(cathedral.seed, 34000L + i) * 0.7);
            double radius = r * (0.04 + ElfariaVfxVertexHelper.hash(cathedral.seed, 34100L + i) * 0.034);
            ElfariaAlbisIceSheetRenderer.prism(
               buffer, matrix, base, h, radius, 4 + (i & 1), live * (0.58 + 0.22 * pulse(cathedral, partialTicks, 0.15, i)), cathedral.seed + 34200L + i
            );
         }
      }
   }

   private static void renderCathedralShardStorm(
      BufferBuilder buffer, Matrix4f matrix, Vec3 cam, ElfariaFruzelCardeneiaVfxClient.Cathedral cathedral, float partialTicks
   ) {
      double live = live(cathedral, partialTicks);
      if (!(live <= 0.002)) {
         ElfariaFruzelCardeneiaVfxClient.Basis b = basis(cathedral, cam);
         double r = cathedral.radius;
         double roof = r * 3.5;
         double time = cathedral.age + partialTicks;
         int count = cathedral.main ? 150 : 96;

         for (int i = 0; i < count; i++) {
            double h = ElfariaVfxVertexHelper.hash(cathedral.seed, 35000L + i);
            double f = ElfariaVfxVertexHelper.hash(cathedral.seed, 35100L + i);
            double k = ElfariaVfxVertexHelper.hash(cathedral.seed, 35200L + i);
            double orbit = r * (1.55 + h * 3.2);
            double spin = h * Math.PI * 2.0 + time * (0.02 + k * 0.02);
            double y = r * 0.4 + f * roof;
            Vec3 base = b.center.add(Math.cos(spin) * orbit, y, Math.sin(spin) * orbit);
            double height = r * (0.1 + k * 0.36);
            double rad = r * (0.016 + f * 0.03);
            ElfariaAlbisIceSheetRenderer.prism(buffer, matrix, base, height, rad, 4, live * (0.34 + h * 0.42), cathedral.seed + 35300L + i);
         }
      }
   }

   private static void renderCathedralFrozenSpears(
      BufferBuilder buffer, Matrix4f matrix, Vec3 cam, ElfariaFruzelCardeneiaVfxClient.Cathedral cathedral, float partialTicks
   ) {
      double live = live(cathedral, partialTicks);
      if (!(live <= 0.002)) {
         ElfariaFruzelCardeneiaVfxClient.Basis b = basis(cathedral, cam);
         double r = cathedral.radius;
         double length = r * 3.24;
         double width = r * 1.36;
         double birth = birth(cathedral, partialTicks);

         for (int lane = -5; lane <= 5; lane++) {
            double f = lane / 5.0;

            for (int i = -4; i <= 4; i++) {
               if ((lane + i & 1) == 0) {
                  double k = ElfariaVfxVertexHelper.hash(cathedral.seed, 36000L + lane * 101L + i);
                  Vec3 base = b.center.add(b.forward.scale(i / 4.0 * length * 0.72)).add(b.right.scale(f * width * 0.74)).add(0.0, 0.04, 0.0);
                  double h = r * (0.28 + k * 0.96) * birth;
                  double rad = r * (0.026 + k * 0.032);
                  ElfariaAlbisIceSheetRenderer.prism(buffer, matrix, base, h, rad, 5, live * (0.3 + k * 0.46), cathedral.seed + 36100L + lane * 101L + i);
               }
            }
         }
      }
   }

   private static void renderCathedralLines(
      BufferBuilder buffer, Matrix4f matrix, Vec3 cam, ElfariaFruzelCardeneiaVfxClient.Cathedral cathedral, float partialTicks
   ) {
      double live = live(cathedral, partialTicks);
      if (!(live <= 0.002)) {
         ElfariaFruzelCardeneiaVfxClient.Basis b = basis(cathedral, cam);
         double r = cathedral.radius;
         double length = r * 3.24;
         double width = r * 1.36;
         double wall = r * 1.88;
         double roof = r * 3.06;
         Vec3 ridgeA = b.center.add(b.forward.scale(-length)).add(0.0, roof, 0.0);
         Vec3 ridgeB = b.center.add(b.forward.scale(length)).add(0.0, roof, 0.0);
         ElfariaVfxVertexHelper.line(buffer, matrix, ridgeA, ridgeB, ElfariaVfxVertexHelper.scaleAlpha(CORE, live));
         ElfariaVfxVertexHelper.line(
            buffer, matrix, ridgeA.add(0.0, r * 0.16, 0.0), ridgeB.add(0.0, r * 0.16, 0.0), ElfariaVfxVertexHelper.scaleAlpha(HALO, live * 0.7)
         );

         for (int side = -1; side <= 1; side += 2) {
            Vec3 base = b.center.add(b.right.scale(side * width));
            ElfariaVfxVertexHelper.line(
               buffer, matrix, base.add(b.forward.scale(-length)), base.add(b.forward.scale(length)), ElfariaVfxVertexHelper.scaleAlpha(LINE, live * 0.94)
            );
            ElfariaVfxVertexHelper.line(
               buffer,
               matrix,
               base.add(b.forward.scale(-length)).add(0.0, wall, 0.0),
               base.add(b.forward.scale(length)).add(0.0, wall, 0.0),
               ElfariaVfxVertexHelper.scaleAlpha(LINE, live * 0.82)
            );

            for (int i = -5; i <= 5; i++) {
               Vec3 archBase = base.add(b.forward.scale(i * length / 5.0));
               arch(buffer, matrix, archBase, b.forward, wall * 0.62, r * 0.34, live);
               arch(buffer, matrix, archBase.add(0.0, wall * 0.3, 0.0), b.forward, wall * 0.44, r * 0.24, live * 0.64);
            }
         }

         for (int end = -1; end <= 1; end += 2) {
            Vec3 face = b.center.add(b.forward.scale(end * length));
            rose(buffer, matrix, face.add(0.0, wall * 0.88, 0.0), b.right, r * 0.55, live);
            rose(buffer, matrix, face.add(0.0, wall * 1.18, 0.0), b.right, r * 0.35, live * 0.72);
         }

         ElfariaAlbisIceSheetRenderer.albisDiscLines(buffer, matrix, b.center, r * 2.2, live * 0.82, cathedral.seed, 0.06);
         ElfariaAlbisIceSheetRenderer.albisDiscLines(buffer, matrix, b.center.add(0.0, r * 0.12, 0.0), r * 3.45, live * 0.54, cathedral.seed + 10L, 0.07);
      }
   }

   private static void renderCathedralRunes(
      BufferBuilder buffer, Matrix4f matrix, Vec3 cam, ElfariaFruzelCardeneiaVfxClient.Cathedral cathedral, float partialTicks
   ) {
      double live = live(cathedral, partialTicks);
      if (!(live <= 0.002)) {
         ElfariaFruzelCardeneiaVfxClient.Basis b = basis(cathedral, cam);
         double r = cathedral.radius;
         double length = r * 3.24;
         double width = r * 1.36;
         double wall = r * 1.88;
         double time = cathedral.age + partialTicks;

         for (int side = -1; side <= 1; side += 2) {
            for (int i = -7; i <= 7; i++) {
               double f = i / 7.0;
               Vec3 center = b.center
                  .add(b.forward.scale(f * length * 0.86))
                  .add(b.right.scale(side * (width + r * 0.055)))
                  .add(0.0, wall * (0.34 + 0.1 * ((i & 1) + 1)), 0.0);
               double size = r * (0.105 + 0.03 * Math.abs(Math.sin(time * 0.08 + i)));
               rune(buffer, matrix, center, b.forward, new Vec3(0.0, 1.0, 0.0), size, live * (0.54 + pulse(cathedral, partialTicks, 0.13, i) * 0.32));
            }
         }

         for (int i = 0; i < 18; i++) {
            double a = i * Math.PI * 2.0 / 18.0 + time * 0.006;
            double dist = r * 2.72;
            Vec3 center = b.center.add(Math.cos(a) * dist, r * 0.1, Math.sin(a) * dist);
            Vec3 dir = new Vec3(Math.cos(a), 0.0, Math.sin(a)).normalize();
            rune(buffer, matrix, center, dir, new Vec3(0.0, 1.0, 0.0), r * 0.12, live * 0.62);
         }
      }
   }

   private static void renderCathedralArcaneWeb(
      BufferBuilder buffer, Matrix4f matrix, Vec3 cam, ElfariaFruzelCardeneiaVfxClient.Cathedral cathedral, float partialTicks
   ) {
      double live = live(cathedral, partialTicks);
      if (!(live <= 0.002)) {
         ElfariaFruzelCardeneiaVfxClient.Basis b = basis(cathedral, cam);
         double r = cathedral.radius;
         double roof = r * 3.06;
         double length = r * 3.24;
         double width = r * 1.36;
         int color = ElfariaVfxVertexHelper.scaleAlpha(VEIN, live * 0.54);

         for (int i = -5; i <= 5; i++) {
            Vec3 p0 = b.center.add(b.forward.scale(i / 5.0 * length)).add(b.right.scale(width)).add(0.0, r * 0.1, 0.0);
            Vec3 p1 = b.center.add(b.forward.scale(-i / 5.0 * length)).add(b.right.scale(-width)).add(0.0, roof * 0.6, 0.0);
            ElfariaVfxVertexHelper.line(buffer, matrix, p0, p1, color);
            Vec3 p2 = b.center.add(b.forward.scale(i / 5.0 * length)).add(b.right.scale(-width)).add(0.0, r * 0.1, 0.0);
            Vec3 p3 = b.center.add(b.forward.scale(-i / 5.0 * length)).add(b.right.scale(width)).add(0.0, roof * 0.6, 0.0);
            ElfariaVfxVertexHelper.line(buffer, matrix, p2, p3, color);
         }

         for (int i = 0; i < 48; i++) {
            double a0 = i * Math.PI * 2.0 / 48.0;
            double a1 = a0 + Math.PI * (0.2 + ElfariaVfxVertexHelper.hash(cathedral.seed, 37000L + i) * 0.24);
            double d0 = r * (1.1 + ElfariaVfxVertexHelper.hash(cathedral.seed, 37100L + i) * 2.5);
            double d1 = r * (1.1 + ElfariaVfxVertexHelper.hash(cathedral.seed, 37200L + i) * 2.5);
            Vec3 p0 = b.center.add(Math.cos(a0) * d0, r * (0.22 + ElfariaVfxVertexHelper.hash(cathedral.seed, 37300L + i) * 2.2), Math.sin(a0) * d0);
            Vec3 p1 = b.center.add(Math.cos(a1) * d1, r * (0.22 + ElfariaVfxVertexHelper.hash(cathedral.seed, 37400L + i) * 2.2), Math.sin(a1) * d1);
            ElfariaVfxVertexHelper.line(
               buffer,
               matrix,
               p0,
               p1,
               ElfariaVfxVertexHelper.scaleAlpha(DEEP_LINE, live * (0.2 + ElfariaVfxVertexHelper.hash(cathedral.seed, 37500L + i) * 0.4))
            );
         }
      }
   }

   private static void renderCathedralLightBeams(
      BufferBuilder buffer, Matrix4f matrix, Vec3 cam, ElfariaFruzelCardeneiaVfxClient.Cathedral cathedral, float partialTicks
   ) {
      double live = live(cathedral, partialTicks);
      if (!(live <= 0.002)) {
         ElfariaFruzelCardeneiaVfxClient.Basis b = basis(cathedral, cam);
         double r = cathedral.radius;
         double roof = r * 3.06;
         double length = r * 3.24;
         int color = ElfariaVfxVertexHelper.scaleAlpha(CORE, live * 0.62);

         for (int i = -4; i <= 4; i++) {
            double f = i / 4.0;
            Vec3 top = b.center.add(b.forward.scale(f * length * 0.72)).add(0.0, roof * 1.52, 0.0);
            Vec3 bottom = b.center.add(b.forward.scale(f * length * 0.72)).add(0.0, r * 0.1, 0.0);
            ElfariaVfxVertexHelper.line(buffer, matrix, top, bottom, color);
            ElfariaVfxVertexHelper.line(
               buffer, matrix, top.add(b.right.scale(r * 0.24)), bottom.add(b.right.scale(-r * 0.12)), ElfariaVfxVertexHelper.scaleAlpha(HALO, live * 0.42)
            );
            ElfariaVfxVertexHelper.line(
               buffer, matrix, top.add(b.right.scale(-r * 0.24)), bottom.add(b.right.scale(r * 0.12)), ElfariaVfxVertexHelper.scaleAlpha(HALO, live * 0.42)
            );
         }
      }
   }

   private static void renderCathedralEdgeCracks(
      BufferBuilder buffer, Matrix4f matrix, Vec3 cam, ElfariaFruzelCardeneiaVfxClient.Cathedral cathedral, float partialTicks
   ) {
      double live = live(cathedral, partialTicks);
      if (!(live <= 0.002)) {
         ElfariaFruzelCardeneiaVfxClient.Basis b = basis(cathedral, cam);
         double r = cathedral.radius;
         int count = cathedral.main ? 180 : 120;

         for (int i = 0; i < count; i++) {
            double h = ElfariaVfxVertexHelper.hash(cathedral.seed, 38000L + i);
            double f = ElfariaVfxVertexHelper.hash(cathedral.seed, 38100L + i);
            double a = h * Math.PI * 2.0;
            double d0 = r * (0.44 + f * 2.9);
            double d1 = d0 + r * (0.12 + ElfariaVfxVertexHelper.hash(cathedral.seed, 38200L + i) * 0.58);
            Vec3 p0 = b.center.add(Math.cos(a) * d0, 0.12 + ElfariaVfxVertexHelper.hash(cathedral.seed, 38300L + i) * r * 0.12, Math.sin(a) * d0);
            Vec3 p1 = b.center.add(Math.cos(a + (ElfariaVfxVertexHelper.hash(cathedral.seed, 38400L + i) - 0.5) * 0.28) * d1, 0.13, Math.sin(a) * d1);
            ElfariaVfxVertexHelper.line(buffer, matrix, p0, p1, ElfariaVfxVertexHelper.scaleAlpha(PALE_BLUE, live * (0.2 + f * 0.44)));
         }
      }
   }

   private static void arch(BufferBuilder buffer, Matrix4f matrix, Vec3 center, Vec3 forward, double height, double width, double live) {
      Vec3 up = new Vec3(0.0, 1.0, 0.0);
      int color = ElfariaVfxVertexHelper.scaleAlpha(LINE, live * 0.86);
      Vec3 left = center.add(forward.scale(-width));
      Vec3 right = center.add(forward.scale(width));
      ElfariaVfxVertexHelper.line(buffer, matrix, left, left.add(up.scale(height * 0.74)), color);
      ElfariaVfxVertexHelper.line(buffer, matrix, right, right.add(up.scale(height * 0.74)), color);
      Vec3 last = left.add(up.scale(height * 0.74));

      for (int i = 1; i <= 18; i++) {
         double a = Math.PI - i * Math.PI / 18.0;
         Vec3 p = center.add(forward.scale(Math.cos(a) * width)).add(up.scale(height * 0.74 + Math.sin(a) * width));
         ElfariaVfxVertexHelper.line(buffer, matrix, last, p, color);
         if ((i & 3) == 0) {
            Vec3 inner = center.add(forward.scale(Math.cos(a) * width * 0.6)).add(up.scale(height * 0.72 + Math.sin(a) * width * 0.6));
            ElfariaVfxVertexHelper.line(buffer, matrix, p, inner, ElfariaVfxVertexHelper.scaleAlpha(BRIGHT, live * 0.42));
         }

         last = p;
      }
   }

   private static void rose(BufferBuilder buffer, Matrix4f matrix, Vec3 center, Vec3 right, double radius, double live) {
      int color = ElfariaVfxVertexHelper.scaleAlpha(BRIGHT, live * 0.92);
      Vec3 up = new Vec3(0.0, 1.0, 0.0);
      Vec3 last = center.add(right.scale(radius));

      for (int i = 1; i <= 48; i++) {
         double a = i * Math.PI * 2.0 / 48.0;
         Vec3 p = center.add(right.scale(Math.cos(a) * radius)).add(up.scale(Math.sin(a) * radius));
         ElfariaVfxVertexHelper.line(buffer, matrix, last, p, color);
         if ((i & 3) == 0) {
            ElfariaVfxVertexHelper.line(buffer, matrix, center, p, ElfariaVfxVertexHelper.scaleAlpha(BRIGHT, live * 0.6));
         }

         if ((i & 7) == 0) {
            Vec3 q = center.add(right.scale(Math.cos(a + 0.22) * radius * 0.66)).add(up.scale(Math.sin(a + 0.22) * radius * 0.66));
            ElfariaVfxVertexHelper.line(buffer, matrix, p, q, ElfariaVfxVertexHelper.scaleAlpha(RUNE, live * 0.48));
         }

         last = p;
      }
   }

   private static void rune(BufferBuilder buffer, Matrix4f matrix, Vec3 center, Vec3 right, Vec3 up, double size, double live) {
      int color = ElfariaVfxVertexHelper.scaleAlpha(RUNE, live);
      Vec3 a = center.add(right.scale(size));
      Vec3 b = center.add(up.scale(size));
      Vec3 c = center.add(right.scale(-size));
      Vec3 d = center.add(up.scale(-size));
      ElfariaVfxVertexHelper.line(buffer, matrix, a, b, color);
      ElfariaVfxVertexHelper.line(buffer, matrix, b, c, color);
      ElfariaVfxVertexHelper.line(buffer, matrix, c, d, color);
      ElfariaVfxVertexHelper.line(buffer, matrix, d, a, color);
      ElfariaVfxVertexHelper.line(buffer, matrix, a, c, ElfariaVfxVertexHelper.scaleAlpha(BRIGHT, live * 0.52));
      ElfariaVfxVertexHelper.line(buffer, matrix, b, d, ElfariaVfxVertexHelper.scaleAlpha(BRIGHT, live * 0.52));
      Vec3 e = center.add(right.scale(size * 0.52)).add(up.scale(size * 0.52));
      Vec3 f = center.add(right.scale(-size * 0.52)).add(up.scale(size * 0.52));
      Vec3 g = center.add(right.scale(-size * 0.52)).add(up.scale(-size * 0.52));
      Vec3 h = center.add(right.scale(size * 0.52)).add(up.scale(-size * 0.52));
      ElfariaVfxVertexHelper.line(buffer, matrix, e, g, ElfariaVfxVertexHelper.scaleAlpha(CORE, live * 0.62));
      ElfariaVfxVertexHelper.line(buffer, matrix, f, h, ElfariaVfxVertexHelper.scaleAlpha(CORE, live * 0.62));
   }

   private static void renderCathedralVerticalAuroras(
      BufferBuilder buffer, Matrix4f matrix, Vec3 cam, ElfariaFruzelCardeneiaVfxClient.Cathedral cathedral, float partialTicks
   ) {
      double live = live(cathedral, partialTicks);
      if (!(live <= 0.002)) {
         ElfariaFruzelCardeneiaVfxClient.Basis b = basis(cathedral, cam);
         double r = cathedral.radius;
         double length = r * 3.24;
         double width = r * 1.36;
         double roof = r * 3.06;
         double time = cathedral.age + partialTicks;

         for (int side = -1; side <= 1; side += 2) {
            for (int i = -6; i <= 6; i++) {
               double h = ElfariaVfxVertexHelper.hash(cathedral.seed, 42000L + side * 37L + i);
               double f = i / 6.0;
               double y = roof * (0.24 + h * 0.28);
               double height = roof * (0.46 + h * 0.34);
               double drift = Math.sin(time * 0.038 + h * 18.0) * r * 0.08;
               Vec3 center = b.center
                  .add(b.forward.scale(f * length * 0.86 + drift))
                  .add(b.right.scale(side * (width + r * (0.2 + h * 0.22))))
                  .add(0.0, y, 0.0);
               Vec3 up = new Vec3(0.0, 1.0, 0.0);
               Vec3 cross = b.forward;
               double alpha = live * (0.2 + pulse(cathedral, partialTicks, 0.074, h * 20.0) * 0.24);
               ElfariaAlbisIceSheetRenderer.albisRectSheet(
                  buffer, matrix, center, up, cross, height * 0.5, r * (0.036 + h * 0.08), alpha, cathedral.seed + 42100L + side * 37L + i, 0.018
               );
            }
         }

         for (int i = 0; i < 10; i++) {
            double h = ElfariaVfxVertexHelper.hash(cathedral.seed, 42200L + i);
            double a = i * Math.PI * 2.0 / 10.0 + time * 0.009;
            Vec3 axis = new Vec3(Math.cos(a), 0.0, Math.sin(a)).normalize();
            Vec3 center = b.center.add(axis.scale(r * (2.0 + h * 1.12))).add(0.0, roof * (0.34 + h * 0.2), 0.0);
            Vec3 up = new Vec3(0.0, 1.0, 0.0);
            double alpha = live * (0.16 + pulse(cathedral, partialTicks, 0.052, i) * 0.22);
            ElfariaAlbisIceSheetRenderer.albisRectSheet(
               buffer, matrix, center, up, axis, roof * (0.36 + h * 0.2), r * (0.04 + h * 0.07), alpha, cathedral.seed + 42300L + i, 0.018
            );
         }
      }
   }

   private static void renderCathedralOrbitingPetals(
      BufferBuilder buffer, Matrix4f matrix, Vec3 cam, ElfariaFruzelCardeneiaVfxClient.Cathedral cathedral, float partialTicks
   ) {
      double live = live(cathedral, partialTicks);
      if (!(live <= 0.002)) {
         ElfariaFruzelCardeneiaVfxClient.Basis b = basis(cathedral, cam);
         double r = cathedral.radius;
         double time = cathedral.age + partialTicks;
         int count = cathedral.main ? 120 : 78;

         for (int i = 0; i < count; i++) {
            double h = ElfariaVfxVertexHelper.hash(cathedral.seed, 43000L + i);
            double f = ElfariaVfxVertexHelper.hash(cathedral.seed, 43100L + i);
            double k = ElfariaVfxVertexHelper.hash(cathedral.seed, 43200L + i);
            double a = h * Math.PI * 2.0 + time * (0.01 + k * 0.018);
            double dist = r * (1.12 + f * 2.9 + Math.sin(time * 0.03 + i) * 0.1);
            double y = r * (0.48 + k * 3.1);
            Vec3 center = b.center.add(Math.cos(a) * dist, y, Math.sin(a) * dist);
            Vec3 axis = new Vec3(Math.cos(a + (Math.PI / 2)), 0.0, Math.sin(a + (Math.PI / 2))).normalize();
            Vec3 up = new Vec3(0.0, 1.0, 0.0);
            double size = r * (0.05 + h * 0.105);
            double alpha = live * (0.2 + pulse(cathedral, partialTicks, 0.11, i * 0.6) * 0.38);
            ElfariaAlbisIceSheetRenderer.albisRectSheet(
               buffer, matrix, center, axis, up, size * (1.2 + f), size * 0.24, alpha, cathedral.seed + 43300L + i, 0.0
            );
            if ((i & 5) == 0) {
               ElfariaVfxVertexHelper.billboardDiamond(
                  buffer, matrix, center.add(0.0, size * 0.28, 0.0), size * 0.2, ElfariaVfxVertexHelper.scaleAlpha(STAR, alpha)
               );
            }
         }
      }
   }

   private static void renderCathedralFrostMandala(
      BufferBuilder buffer, Matrix4f matrix, Vec3 cam, ElfariaFruzelCardeneiaVfxClient.Cathedral cathedral, float partialTicks
   ) {
      double live = live(cathedral, partialTicks);
      if (!(live <= 0.002)) {
         ElfariaFruzelCardeneiaVfxClient.Basis b = basis(cathedral, cam);
         double r = cathedral.radius;
         double time = cathedral.age + partialTicks;
         int rings = 5;

         for (int ring = 1; ring <= rings; ring++) {
            double radius = r * (0.62 + ring * 0.48);
            int segments = 36 + ring * 12;
            Vec3 last = null;

            for (int i = 0; i <= segments; i++) {
               double a = i * Math.PI * 2.0 / segments + time * 0.002 * (ring % 2 == 0 ? 1.0 : -1.0);
               double wobble = Math.sin(a * (3 + ring) + time * 0.03) * r * 0.025;
               Vec3 p = b.center.add(Math.cos(a) * (radius + wobble), 0.115 + ring * 0.018, Math.sin(a) * (radius + wobble));
               if (last != null) {
                  ElfariaVfxVertexHelper.line(buffer, matrix, last, p, ElfariaVfxVertexHelper.scaleAlpha(RUNE, live * (0.24 + ring * 0.055)));
               }

               last = p;
            }
         }

         for (int spoke = 0; spoke < 48; spoke++) {
            double h = ElfariaVfxVertexHelper.hash(cathedral.seed, 44000L + spoke);
            double a = spoke * Math.PI * 2.0 / 48.0 + h * 0.04;
            double inner = r * (0.42 + h * 0.3);
            double outer = r * (2.6 + h * 0.72);
            Vec3 p0 = b.center.add(Math.cos(a) * inner, 0.15, Math.sin(a) * inner);
            Vec3 p1 = b.center.add(Math.cos(a) * outer, 0.18, Math.sin(a) * outer);
            ElfariaVfxVertexHelper.line(buffer, matrix, p0, p1, ElfariaVfxVertexHelper.scaleAlpha(PALE_BLUE, live * (0.22 + h * 0.34)));
         }
      }
   }

   private static void renderCathedralFallingStarTrails(
      BufferBuilder buffer, Matrix4f matrix, Vec3 cam, ElfariaFruzelCardeneiaVfxClient.Cathedral cathedral, float partialTicks
   ) {
      double live = live(cathedral, partialTicks);
      if (!(live <= 0.002)) {
         ElfariaFruzelCardeneiaVfxClient.Basis b = basis(cathedral, cam);
         double r = cathedral.radius;
         double roof = r * 3.9;
         double time = cathedral.age + partialTicks;
         int count = cathedral.main ? 58 : 38;

         for (int i = 0; i < count; i++) {
            double h = ElfariaVfxVertexHelper.hash(cathedral.seed, 45000L + i);
            double f = ElfariaVfxVertexHelper.hash(cathedral.seed, 45100L + i);
            double k = ElfariaVfxVertexHelper.hash(cathedral.seed, 45200L + i);
            double phase = (time * (0.01 + k * 0.012) + h) % 1.0;
            double a = f * Math.PI * 2.0;
            double dist = r * (0.8 + k * 3.4);
            Vec3 p0 = b.center.add(Math.cos(a) * dist, roof * (1.0 - phase) + r * 0.4, Math.sin(a) * dist);
            Vec3 p1 = p0.add(Math.cos(a + 0.6) * r * 0.22, -r * (0.32 + h * 0.7), Math.sin(a + 0.6) * r * 0.22);
            double alpha = live * Math.sin(phase * Math.PI) * (0.34 + h * 0.46);
            ElfariaVfxVertexHelper.line(buffer, matrix, p0, p1, ElfariaVfxVertexHelper.scaleAlpha(CORE, alpha));
            if ((i & 3) == 0) {
               Vec3 p2 = p1.add(0.0, -r * 0.18, 0.0);
               ElfariaVfxVertexHelper.line(buffer, matrix, p1, p2, ElfariaVfxVertexHelper.scaleAlpha(HALO, alpha * 0.72));
            }
         }
      }
   }

   private static void renderSpikeSheet(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, ElfariaFruzelCardeneiaVfxClient.Spike spike, float partialTicks) {
      double t = (spike.age + partialTicks) / spike.life;
      double grow = ElfariaVfxVertexHelper.smooth(Math.min(1.0, t * 2.6));
      double fade = 1.0 - ElfariaVfxVertexHelper.smooth(Math.max(0.0, (t - 0.72) / 0.28));
      Vec3 c = ElfariaVfxVertexHelper.rel(spike.center, cam);
      ElfariaAlbisIceSheetRenderer.albisDisc(buffer, matrix, c, spike.radius * grow * 2.15, fade * 0.72, spike.seed, 0.035);
      ElfariaAlbisIceSheetRenderer.albisDisc(buffer, matrix, c.add(0.0, 0.04, 0.0), spike.radius * grow * 1.18, fade * 0.92, spike.seed + 10L, 0.01);
   }

   private static void renderSpikeGlow(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, ElfariaFruzelCardeneiaVfxClient.Spike spike, float partialTicks) {
      double t = (spike.age + partialTicks) / spike.life;
      double grow = ElfariaVfxVertexHelper.smooth(Math.min(1.0, t * 3.0));
      double fade = 1.0 - ElfariaVfxVertexHelper.smooth(Math.max(0.0, (t - 0.62) / 0.38));
      Vec3 c = ElfariaVfxVertexHelper.rel(spike.center, cam);

      for (int i = 0; i < 30; i++) {
         double h = ElfariaVfxVertexHelper.hash(spike.seed, 39000L + i);
         double f = ElfariaVfxVertexHelper.hash(spike.seed, 39100L + i);
         double a = h * Math.PI * 2.0;
         Vec3 p = c.add(Math.cos(a) * spike.radius * grow * f, 0.12 + f * spike.radius * 1.8, Math.sin(a) * spike.radius * grow * f);
         ElfariaVfxVertexHelper.billboardDiamond(
            buffer, matrix, p, spike.radius * (0.018 + f * 0.04), ElfariaVfxVertexHelper.scaleAlpha(BRIGHT, fade * (0.38 + h * 0.44))
         );
      }
   }

   private static void renderSpikePrisms(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, ElfariaFruzelCardeneiaVfxClient.Spike spike, float partialTicks) {
      double t = (spike.age + partialTicks) / spike.life;
      double grow = ElfariaVfxVertexHelper.smooth(Math.min(1.0, t * 2.4));
      double fade = 1.0 - ElfariaVfxVertexHelper.smooth(Math.max(0.0, (t - 0.72) / 0.28));
      Vec3 c = ElfariaVfxVertexHelper.rel(spike.center, cam);
      ElfariaAlbisIceSheetRenderer.prism(buffer, matrix, c.add(0.0, 0.04, 0.0), spike.radius * (3.0 + grow * 0.9), spike.radius * 0.28, 7, fade, spike.seed);

      for (int i = 0; i < 14; i++) {
         double a = i * Math.PI * 2.0 / 14.0;
         Vec3 base = c.add(Math.cos(a) * spike.radius * (0.48 + grow * 0.36), 0.04, Math.sin(a) * spike.radius * (0.48 + grow * 0.36));
         ElfariaAlbisIceSheetRenderer.prism(buffer, matrix, base, spike.radius * (0.7 + grow * 1.65), spike.radius * 0.075, 5, fade * 0.82, spike.seed + i);
      }
   }

   private static void renderSpikeNeedleBurst(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, ElfariaFruzelCardeneiaVfxClient.Spike spike, float partialTicks) {
      double t = (spike.age + partialTicks) / spike.life;
      double grow = ElfariaVfxVertexHelper.smooth(Math.min(1.0, t * 3.0));
      double fade = 1.0 - ElfariaVfxVertexHelper.smooth(Math.max(0.0, (t - 0.68) / 0.32));
      Vec3 c = ElfariaVfxVertexHelper.rel(spike.center, cam);

      for (int i = 0; i < 18; i++) {
         double h = ElfariaVfxVertexHelper.hash(spike.seed, 40000L + i);
         double a = i * Math.PI * 2.0 / 18.0 + h * 0.18;
         Vec3 base = c.add(Math.cos(a) * spike.radius * (0.64 + h * 0.74), 0.04, Math.sin(a) * spike.radius * (0.64 + h * 0.74));
         double height = spike.radius * (0.72 + h * 1.2) * grow;
         ElfariaAlbisIceSheetRenderer.prism(
            buffer, matrix, base, height, spike.radius * (0.038 + h * 0.04), 4 + (i & 1), fade * (0.46 + h * 0.44), spike.seed + 40100L + i
         );
      }
   }

   private static void renderSpikeLines(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, ElfariaFruzelCardeneiaVfxClient.Spike spike, float partialTicks) {
      double t = (spike.age + partialTicks) / spike.life;
      double grow = ElfariaVfxVertexHelper.smooth(Math.min(1.0, t * 2.5));
      double fade = 1.0 - ElfariaVfxVertexHelper.smooth(t);
      Vec3 c = ElfariaVfxVertexHelper.rel(spike.center, cam);
      ElfariaAlbisIceSheetRenderer.albisDiscLines(buffer, matrix, c, spike.radius * grow * 2.25, fade * 0.78, spike.seed, 0.07);
      ElfariaAlbisIceSheetRenderer.albisDiscLines(buffer, matrix, c.add(0.0, 0.02, 0.0), spike.radius * grow * 1.28, fade * 0.96, spike.seed + 7L, 0.045);
   }

   private static void renderSpikeAfterLines(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, ElfariaFruzelCardeneiaVfxClient.Spike spike, float partialTicks) {
      double t = (spike.age + partialTicks) / spike.life;
      double fade = 1.0 - ElfariaVfxVertexHelper.smooth(Math.min(1.0, t));
      Vec3 c = ElfariaVfxVertexHelper.rel(spike.center, cam);

      for (int i = 0; i < 22; i++) {
         double h = ElfariaVfxVertexHelper.hash(spike.seed, 41000L + i);
         double a = h * Math.PI * 2.0;
         double d0 = spike.radius * (0.2 + ElfariaVfxVertexHelper.hash(spike.seed, 41100L + i) * 0.6);
         double d1 = spike.radius * (1.1 + ElfariaVfxVertexHelper.hash(spike.seed, 41200L + i) * 1.5);
         Vec3 p0 = c.add(Math.cos(a) * d0, 0.08, Math.sin(a) * d0);
         Vec3 p1 = c.add(Math.cos(a) * d1, 0.1 + ElfariaVfxVertexHelper.hash(spike.seed, 41300L + i) * 0.22, Math.sin(a) * d1);
         ElfariaVfxVertexHelper.line(buffer, matrix, p0, p1, ElfariaVfxVertexHelper.scaleAlpha(LINE, fade * (0.36 + h * 0.48)));
      }
   }

   private record Basis(Vec3 center, Vec3 forward, Vec3 right) {
   }

   private static final class Cathedral {
      private Vec3 center;
      private Vec3 caster;
      private float radius;
      private int expireAge;
      private long seed;
      private boolean main;
      private int age;

      private Cathedral(Vec3 center, Vec3 caster, float radius, int life, long seed, boolean main) {
         this.center = center;
         this.caster = caster;
         this.radius = radius;
         this.expireAge = life;
         this.seed = seed;
         this.main = main;
      }

      private void refresh(Vec3 center, Vec3 caster, float radius, int life, long seed, boolean main) {
         this.center = this.center.lerp(center, 0.12);
         this.caster = this.caster.lerp(caster, 0.12);
         this.radius = Math.max(this.radius, radius);
         this.expireAge = Math.max(this.expireAge, this.age + life);
         this.seed = this.seed ^ seed + this.age * 131L;
         this.main = this.main || main;
      }
   }

   private static final class Spike {
      private final Vec3 center;
      private final float radius;
      private final int life;
      private final long seed;
      private int age;

      private Spike(Vec3 center, float radius, int life, long seed) {
         this.center = center;
         this.radius = radius;
         this.life = life;
         this.seed = seed;
      }
   }
}
