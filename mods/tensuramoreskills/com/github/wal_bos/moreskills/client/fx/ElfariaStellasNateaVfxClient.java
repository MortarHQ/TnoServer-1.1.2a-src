package com.github.wal_bos.moreskills.client.fx;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

public final class ElfariaStellasNateaVfxClient {
   private static final List<ElfariaStellasNateaVfxClient.Charge> CHARGES = new ArrayList<>();
   private static final List<ElfariaStellasNateaVfxClient.Strike> STRIKES = new ArrayList<>();
   private static final int ICE = ElfariaVfxVertexHelper.rgba(72, 201, 250, 132);
   private static final int ICE_SOFT = ElfariaVfxVertexHelper.rgba(128, 236, 255, 122);
   private static final int BRIGHT = ElfariaVfxVertexHelper.rgba(246, 255, 255, 232);
   private static final int LINE = ElfariaVfxVertexHelper.rgba(228, 255, 255, 212);
   private static final int DEEP = ElfariaVfxVertexHelper.rgba(24, 118, 218, 90);
   private static final int BLAST = ElfariaVfxVertexHelper.rgba(210, 252, 255, 205);
   private static final int FROST = ElfariaVfxVertexHelper.rgba(178, 238, 255, 112);
   private static final int CORE = ElfariaVfxVertexHelper.rgba(255, 255, 255, 245);

   private ElfariaStellasNateaVfxClient() {
   }

   public static void spawnCharge(Vec3 center, float radius, int count, long seed, boolean main) {
      Vec3 ground = snapToGround(center, Math.max(1.0, radius));

      for (ElfariaStellasNateaVfxClient.Charge charge : CHARGES) {
         if (charge.center.distanceToSqr(ground) <= Math.max(16.0, radius * radius * 0.35)) {
            charge.refresh(radius, count, seed, main);
            return;
         }
      }

      CHARGES.add(new ElfariaStellasNateaVfxClient.Charge(ground, radius, count, seed, main));

      while (CHARGES.size() > 3) {
         CHARGES.remove(0);
      }
   }

   public static void spawnStrike(Vec3 top, Vec3 impact, float radius, long seed, boolean main) {
      Vec3 ground = snapToGround(impact, Math.max(1.0, radius));
      boolean impactOnly = top.distanceToSqr(impact) <= 9.0;
      STRIKES.add(new ElfariaStellasNateaVfxClient.Strike(top, ground, radius, seed, main, impactOnly));

      while (STRIKES.size() > 64) {
         STRIKES.remove(0);
      }
   }

   public static void tick() {
      CHARGES.removeIf(ElfariaStellasNateaVfxClient.Charge::dead);
      STRIKES.removeIf(ElfariaStellasNateaVfxClient.Strike::dead);

      for (ElfariaStellasNateaVfxClient.Charge charge : CHARGES) {
         charge.age++;
      }

      for (ElfariaStellasNateaVfxClient.Strike strike : STRIKES) {
         strike.age++;
      }
   }

   public static void render(PoseStack poseStack, float partialTicks) {
      if (!CHARGES.isEmpty() || !STRIKES.isEmpty()) {
         ElfariaVfxVertexHelper.beginIce();
         Vec3 cam = ElfariaVfxVertexHelper.camera();
         Matrix4f matrix = poseStack.last().pose();
         BufferBuilder quads = ElfariaVfxVertexHelper.beginQuads();

         for (ElfariaStellasNateaVfxClient.Charge charge : CHARGES) {
            renderChargeQuads(quads, matrix, cam, charge, partialTicks);
         }

         for (ElfariaStellasNateaVfxClient.Strike strike : STRIKES) {
            renderStrikeQuads(quads, matrix, cam, strike, partialTicks);
         }

         ElfariaVfxVertexHelper.finish(quads);
         BufferBuilder tris = ElfariaVfxVertexHelper.beginTriangles();

         for (ElfariaStellasNateaVfxClient.Charge charge : CHARGES) {
            renderChargeTriangles(tris, matrix, cam, charge, partialTicks);
         }

         for (ElfariaStellasNateaVfxClient.Strike strike : STRIKES) {
            renderStrikeTriangles(tris, matrix, cam, strike, partialTicks);
         }

         ElfariaVfxVertexHelper.finish(tris);
         BufferBuilder lines = ElfariaVfxVertexHelper.beginLines();

         for (ElfariaStellasNateaVfxClient.Charge charge : CHARGES) {
            renderChargeLines(lines, matrix, cam, charge, partialTicks);
         }

         for (ElfariaStellasNateaVfxClient.Strike strike : STRIKES) {
            renderStrikeLines(lines, matrix, cam, strike, partialTicks);
         }

         ElfariaVfxVertexHelper.finish(lines);
         ElfariaVfxVertexHelper.endIce();
      }
   }

   private static void renderChargeQuads(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, ElfariaStellasNateaVfxClient.Charge charge, float partialTicks) {
      double age = charge.age + partialTicks;
      double appear = ElfariaVfxVertexHelper.smooth(Math.min(1.0, age / 8.0));
      double fall = Math.min(1.0, age / charge.fallTicks);
      double after = age - charge.fallTicks;
      double chargeLive = appear * (fall < 1.0 ? 1.0 : Math.max(0.0, 1.0 - after / 18.0));
      Vec3 c = ElfariaVfxVertexHelper.rel(charge.center, cam);
      if (chargeLive > 0.002) {
         double drop = fallCurve(fall);
         Vec3 halo = c.add(0.0, 20.0 - drop * 19.2, 0.0);
         ElfariaAlbisIceSheetRenderer.roundSheet(buffer, matrix, halo, charge.radius * (1.35 + appear * 0.44), chargeLive * 0.64, charge.seed, 0.0);
         Vec3 axis = new Vec3(1.0, 0.0, 0.0);
         Vec3 side = new Vec3(0.0, 0.0, 1.0);
         ElfariaAlbisIceSheetRenderer.sheetLines(
            buffer, matrix, halo.add(0.0, 0.06, 0.0), axis, side, charge.radius * 1.35, charge.radius * 1.35, chargeLive * 0.74, charge.seed + 88L, 0.0
         );
         int motes = charge.main ? 80 : 48;

         for (int i = 0; i < motes; i++) {
            double h = ElfariaVfxVertexHelper.hash(charge.seed, 35000L + i);
            double a = h * Math.PI * 2.0 + age * 0.018;
            double rr = Math.sqrt(ElfariaVfxVertexHelper.hash(charge.seed, 35100L + i)) * charge.radius * 1.12;
            double y = 13.0
               + ElfariaVfxVertexHelper.hash(charge.seed, 35200L + i) * 9.0
               - drop * (14.5 + ElfariaVfxVertexHelper.hash(charge.seed, 35300L + i) * 8.0);
            Vec3 p = c.add(Math.cos(a) * rr, y, Math.sin(a) * rr);
            double s = charge.radius * (0.018 + h * 0.052);
            ElfariaVfxVertexHelper.billboardDiamond(buffer, matrix, p, s, ElfariaVfxVertexHelper.scaleAlpha(BRIGHT, chargeLive * (0.35 + h * 0.65)));
         }
      }

      if (after >= 0.0) {
         renderChargeImpactQuads(buffer, matrix, c, charge, after);
      }
   }

   private static void renderChargeTriangles(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, ElfariaStellasNateaVfxClient.Charge charge, float partialTicks) {
      double age = charge.age + partialTicks;
      double fall = Math.min(1.0, age / charge.fallTicks);
      double after = age - charge.fallTicks;
      double appear = ElfariaVfxVertexHelper.smooth(Math.min(1.0, age / 8.0));
      double live = appear * (fall < 1.0 ? 1.0 : Math.max(0.0, 1.0 - after / 12.0));
      Vec3 c = ElfariaVfxVertexHelper.rel(charge.center, cam);
      if (live > 0.002 && fall < 1.0) {
         double drop = fallCurve(fall);
         int visible = visible(charge);

         for (int i = 0; i < visible; i++) {
            double a = i * 2.399963229728653;
            double rr = Math.sqrt((i + 0.5) / Math.max(1.0, visible)) * charge.radius;
            double baseY = 17.0 + ElfariaVfxVertexHelper.hash(charge.seed, i * 17L) * 9.0;
            double travel = baseY + 1.2 + ElfariaVfxVertexHelper.hash(charge.seed, 6000L + i) * 2.5;
            double wobble = Math.sin(age * 0.2 + i) * (1.0 - drop) * 0.2;
            Vec3 base = c.add(Math.cos(a) * rr + Math.cos(a + 1.7) * wobble, baseY - drop * travel, Math.sin(a) * rr + Math.sin(a + 1.7) * wobble);
            double size = (0.26 + ElfariaVfxVertexHelper.hash(charge.seed, 1000L + i) * 0.48) * (charge.main && i % 6 == 0 ? 1.28 : 0.94);
            renderSpear(buffer, matrix, base, new Vec3(0.0, -1.0, 0.0), size, 4.4 + size * 3.6, live, charge.seed + i);
            if ((i & 7) == 0) {
               Vec3 shardBase = base.add(0.0, -size * 1.2, 0.0);
               ElfariaAlbisIceSheetRenderer.prism(buffer, matrix, shardBase, size * 1.35, size * 0.075, 5, live * 0.46, charge.seed + 9000L + i);
            }
         }
      }

      if (after >= 0.0) {
         renderChargeImpactTriangles(buffer, matrix, c, charge, after);
      }
   }

   private static void renderChargeLines(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, ElfariaStellasNateaVfxClient.Charge charge, float partialTicks) {
      double age = charge.age + partialTicks;
      double fall = Math.min(1.0, age / charge.fallTicks);
      double after = age - charge.fallTicks;
      double appear = ElfariaVfxVertexHelper.smooth(Math.min(1.0, age / 8.0));
      double live = appear * (fall < 1.0 ? 1.0 : Math.max(0.0, 1.0 - after / 16.0));
      Vec3 c = ElfariaVfxVertexHelper.rel(charge.center, cam);
      if (live > 0.002 && fall < 1.0) {
         double drop = fallCurve(fall);
         int visible = Math.min(visible(charge), charge.main ? 72 : 42);

         for (int i = 0; i < visible; i += 2) {
            double a = i * 2.399963229728653;
            double rr = Math.sqrt((i + 0.5) / Math.max(1.0, visible)) * charge.radius;
            double y = 16.0
               + ElfariaVfxVertexHelper.hash(charge.seed, i * 17L) * 9.0
               - drop * (17.5 + ElfariaVfxVertexHelper.hash(charge.seed, 6000L + i) * 2.8);
            Vec3 p = c.add(Math.cos(a) * rr, y, Math.sin(a) * rr);
            ElfariaVfxVertexHelper.line(
               buffer, matrix, p.add(0.0, 1.4, 0.0), p.add(0.0, -4.7, 0.0), ElfariaVfxVertexHelper.scaleAlpha(LINE, live * (0.42 + drop * 0.48))
            );
         }
      }

      if (after >= 0.0) {
         renderChargeImpactLines(buffer, matrix, c, charge, after);
      }
   }

   private static void renderStrikeQuads(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, ElfariaStellasNateaVfxClient.Strike strike, float partialTicks) {
      double age = strike.age + partialTicks;
      double fall = strike.impactOnly ? 1.0 : Math.min(1.0, age / strike.fallTicks);
      double after = strike.impactOnly ? age : age - strike.fallTicks;
      double live = strike.impactOnly ? 0.0 : Math.max(0.0, 1.0 - Math.max(0.0, after) / 10.0);
      Vec3 impact = ElfariaVfxVertexHelper.rel(strike.impact, cam);
      if (!strike.impactOnly && live > 0.002 && fall < 1.0) {
         double drop = fallCurve(fall);
         Vec3 world = strike.top.lerp(strike.impact, drop);
         Vec3 c = ElfariaVfxVertexHelper.rel(world, cam);
         Vec3 dir = strike.impact.subtract(strike.top);
         if (dir.lengthSqr() < 0.001) {
            dir = new Vec3(0.0, -1.0, 0.0);
         }

         dir = dir.normalize();
         Vec3 side = dir.cross(new Vec3(0.0, 0.0, 1.0));
         if (side.lengthSqr() < 0.001) {
            side = new Vec3(1.0, 0.0, 0.0);
         }

         side = side.normalize();
         double length = Math.max(7.0, strike.top.distanceTo(strike.impact) * 0.4);
         double width = Math.max(0.32, strike.radius * 0.18);
         ElfariaVfxVertexHelper.quad(
            buffer,
            matrix,
            c.add(side.scale(width)),
            c.add(side.scale(-width)),
            c.add(dir.scale(length)).add(side.scale(-width * 0.08)),
            c.add(dir.scale(length)).add(side.scale(width * 0.08)),
            ElfariaVfxVertexHelper.scaleAlpha(BRIGHT, live * 0.88)
         );

         for (int i = 0; i < 24; i++) {
            double h = ElfariaVfxVertexHelper.hash(strike.seed, 23000L + i);
            Vec3 mote = c.add(side.scale((h - 0.5) * strike.radius * 2.4))
               .add(dir.scale((ElfariaVfxVertexHelper.hash(strike.seed, 23100L + i) - 0.5) * length));
            ElfariaVfxVertexHelper.billboardDiamond(
               buffer, matrix, mote, strike.radius * (0.022 + h * 0.045), ElfariaVfxVertexHelper.scaleAlpha(BRIGHT, live * (0.25 + h * 0.52))
            );
         }
      }

      if (after >= 0.0) {
         renderImpactExplosionQuads(buffer, matrix, impact, strike.radius, strike.seed, strike.main, after);
      }
   }

   private static void renderStrikeTriangles(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, ElfariaStellasNateaVfxClient.Strike strike, float partialTicks) {
      double age = strike.age + partialTicks;
      double fall = strike.impactOnly ? 1.0 : Math.min(1.0, age / strike.fallTicks);
      double after = strike.impactOnly ? age : age - strike.fallTicks;
      double live = strike.impactOnly ? 0.0 : Math.max(0.0, 1.0 - Math.max(0.0, after) / 10.0);
      if (!strike.impactOnly && live > 0.002 && fall < 1.0) {
         Vec3 pos = strike.top.lerp(strike.impact, fallCurve(fall));
         Vec3 c = ElfariaVfxVertexHelper.rel(pos, cam);
         Vec3 dir = strike.impact.subtract(strike.top);
         if (dir.lengthSqr() < 0.001) {
            dir = new Vec3(0.0, -1.0, 0.0);
         }

         renderSpear(
            buffer,
            matrix,
            c,
            dir.normalize(),
            Math.max(0.42, strike.radius * 0.24),
            Math.max(6.2, strike.top.distanceTo(strike.impact) * 0.36),
            live,
            strike.seed
         );
      }

      if (after >= 0.0) {
         renderImpactExplosionTriangles(buffer, matrix, ElfariaVfxVertexHelper.rel(strike.impact, cam), strike.radius, strike.seed, strike.main, after);
      }
   }

   private static void renderStrikeLines(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, ElfariaStellasNateaVfxClient.Strike strike, float partialTicks) {
      double age = strike.age + partialTicks;
      double fall = strike.impactOnly ? 1.0 : Math.min(1.0, age / strike.fallTicks);
      double after = strike.impactOnly ? age : age - strike.fallTicks;
      if (!strike.impactOnly && fall < 1.0) {
         double live = Math.max(0.0, 1.0 - Math.max(0.0, after) / 10.0);
         Vec3 top = ElfariaVfxVertexHelper.rel(strike.top, cam);
         Vec3 impact = ElfariaVfxVertexHelper.rel(strike.impact, cam);
         Vec3 pos = top.lerp(impact, fallCurve(fall));
         ElfariaVfxVertexHelper.line(buffer, matrix, top, pos, ElfariaVfxVertexHelper.scaleAlpha(LINE, live * 0.76));
      }

      if (after >= 0.0) {
         renderImpactExplosionLines(buffer, matrix, ElfariaVfxVertexHelper.rel(strike.impact, cam), strike.radius, strike.seed, strike.main, after);
      }
   }

   private static void renderChargeImpactQuads(BufferBuilder buffer, Matrix4f matrix, Vec3 center, ElfariaStellasNateaVfxClient.Charge charge, double after) {
      renderImpactExplosionQuads(buffer, matrix, center, charge.radius * (charge.main ? 1.25F : 1.0F), charge.seed ^ 85856677L, charge.main, after);
   }

   private static void renderChargeImpactTriangles(BufferBuilder buffer, Matrix4f matrix, Vec3 center, ElfariaStellasNateaVfxClient.Charge charge, double after) {
      renderImpactExplosionTriangles(buffer, matrix, center, charge.radius * (charge.main ? 1.25F : 1.0F), charge.seed ^ 85856677L, charge.main, after);
   }

   private static void renderChargeImpactLines(BufferBuilder buffer, Matrix4f matrix, Vec3 center, ElfariaStellasNateaVfxClient.Charge charge, double after) {
      renderImpactExplosionLines(buffer, matrix, center, charge.radius * (charge.main ? 1.25F : 1.0F), charge.seed ^ 85856677L, charge.main, after);
   }

   private static void renderImpactExplosionQuads(BufferBuilder buffer, Matrix4f matrix, Vec3 impact, float radius, long seed, boolean main, double after) {
      double bloom = ElfariaVfxVertexHelper.smooth(Math.min(1.0, after / 10.0));
      double fade = 1.0 - ElfariaVfxVertexHelper.smooth(Math.max(0.0, (after - 34.0) / 24.0));
      if (!(fade <= 0.002)) {
         double mainScale = main ? 1.24 : 1.0;
         double flash = 1.0 - ElfariaVfxVertexHelper.smooth(Math.min(1.0, after / 9.0));
         ElfariaAlbisIceSheetRenderer.albisDisc(
            buffer, matrix, impact.add(0.0, 0.035, 0.0), radius * mainScale * (4.6 + bloom * 8.0), Math.max(flash, fade * 0.45), seed, 0.03
         );
         ElfariaAlbisIceSheetRenderer.roundSheet(
            buffer, matrix, impact.add(0.0, 0.075, 0.0), radius * mainScale * (3.3 + bloom * 6.4), fade * 0.7, seed + 31L, 0.05
         );
         int motes = main ? 58 : 34;

         for (int i = 0; i < motes; i++) {
            double h = ElfariaVfxVertexHelper.hash(seed, 52000L + i);
            double a = h * Math.PI * 2.0;
            double speed = 0.8 + ElfariaVfxVertexHelper.hash(seed, 52100L + i) * 6.2;
            double dist = radius * mainScale * bloom * speed;
            double lift = radius * (0.25 + h * 3.0) * Math.max(0.0, 1.0 - after / 48.0);
            Vec3 mote = impact.add(Math.cos(a) * dist, 0.18 + Math.max(0.0, lift), Math.sin(a) * dist);
            ElfariaVfxVertexHelper.billboardDiamond(
               buffer,
               matrix,
               mote,
               radius * mainScale * (0.042 + h * 0.11),
               ElfariaVfxVertexHelper.scaleAlpha((i & 2) == 0 ? BRIGHT : FROST, fade * (0.35 + h * 0.48))
            );
         }

         int ribbons = main ? 34 : 20;

         for (int i = 0; i < ribbons; i++) {
            double h = ElfariaVfxVertexHelper.hash(seed, 54000L + i);
            double a = h * Math.PI * 2.0;
            double reach = radius * mainScale * (1.8 + bloom * (5.6 + h * 3.2));
            Vec3 root = impact.add(Math.cos(a) * radius * 0.22, 0.14, Math.sin(a) * radius * 0.22);
            Vec3 tip = impact.add(Math.cos(a) * reach, 0.25 + h * radius * mainScale * 1.65, Math.sin(a) * reach);
            ElfariaVfxVertexHelper.ribbon(
               buffer,
               matrix,
               root,
               tip,
               radius * mainScale * (0.025 + h * 0.03),
               0.0,
               ElfariaVfxVertexHelper.scaleAlpha((i & 1) == 0 ? BLAST : ICE, fade * 0.62)
            );
         }
      }
   }

   private static void renderImpactExplosionTriangles(BufferBuilder buffer, Matrix4f matrix, Vec3 impact, float radius, long seed, boolean main, double after) {
      double bloom = ElfariaVfxVertexHelper.smooth(Math.min(1.0, after / 12.0));
      double fade = 1.0 - ElfariaVfxVertexHelper.smooth(Math.max(0.0, (after - 36.0) / 22.0));
      if (!(fade <= 0.002)) {
         double mainScale = main ? 1.2 : 1.0;
         int shards = main ? 74 : 38;

         for (int i = 0; i < shards; i++) {
            double h = ElfariaVfxVertexHelper.hash(seed, 2000L + i);
            double a = h * Math.PI * 2.0;
            double dist = radius * mainScale * bloom * (0.7 + ElfariaVfxVertexHelper.hash(seed, 2100L + i) * 6.6);
            double lift = radius * mainScale * bloom * (0.04 + ElfariaVfxVertexHelper.hash(seed, 2250L + i) * 1.1);
            Vec3 base = impact.add(Math.cos(a) * dist, 0.05 + lift, Math.sin(a) * dist);
            double shardHeight = radius * mainScale * (0.58 + ElfariaVfxVertexHelper.hash(seed, i + 2200L) * 1.85) * Math.max(0.25, 1.0 - bloom * 0.1);
            ElfariaAlbisIceSheetRenderer.prism(
               buffer, matrix, base, shardHeight, radius * mainScale * (0.045 + h * 0.032), 5, fade * (0.54 + h * 0.22), seed + i
            );
         }

         int spears = main ? 14 : 7;

         for (int i = 0; i < spears; i++) {
            double h = ElfariaVfxVertexHelper.hash(seed, 62000L + i);
            double a = h * Math.PI * 2.0;
            double dist = radius * mainScale * (0.9 + bloom * (3.0 + h * 2.6));
            Vec3 base = impact.add(Math.cos(a) * dist, 0.1, Math.sin(a) * dist);
            Vec3 dir = new Vec3(Math.cos(a) * 0.4, 1.0 + h * 0.52, Math.sin(a) * 0.4).normalize();
            renderSpear(
               buffer,
               matrix,
               base.add(dir.scale(radius * mainScale * 1.05)),
               dir,
               radius * mainScale * (0.12 + h * 0.12),
               radius * mainScale * (2.4 + h * 2.8),
               fade * 0.62,
               seed + 62000L + i
            );
         }
      }
   }

   private static void renderImpactExplosionLines(BufferBuilder buffer, Matrix4f matrix, Vec3 impact, float radius, long seed, boolean main, double after) {
      double bloom = ElfariaVfxVertexHelper.smooth(Math.min(1.0, after / 10.0));
      double fade = 1.0 - ElfariaVfxVertexHelper.smooth(Math.max(0.0, (after - 32.0) / 24.0));
      if (!(fade <= 0.002)) {
         double mainScale = main ? 1.24 : 1.0;
         int rays = main ? 72 : 44;

         for (int i = 0; i < rays; i++) {
            double a = i * Math.PI * 2.0 / rays;
            double jitter = (ElfariaVfxVertexHelper.hash(seed, 71000L + i) - 0.5) * 0.16;
            double ca = Math.cos(a + jitter);
            double sa = Math.sin(a + jitter);
            Vec3 p0 = impact.add(ca * radius * mainScale * 0.32, 0.08, sa * radius * mainScale * 0.32);
            Vec3 p1 = impact.add(
               ca * radius * mainScale * (2.0 + bloom * 8.2),
               0.1 + ElfariaVfxVertexHelper.hash(seed, 72000L + i) * radius * 0.22,
               sa * radius * mainScale * (2.0 + bloom * 8.2)
            );
            ElfariaVfxVertexHelper.line(buffer, matrix, p0, p1, ElfariaVfxVertexHelper.scaleAlpha(LINE, fade * bloom * 0.68));
         }

         int arcs = main ? 32 : 18;

         for (int i = 0; i < arcs; i++) {
            double h = ElfariaVfxVertexHelper.hash(seed, 73000L + i);
            double a = h * Math.PI * 2.0;
            Vec3 p0 = impact.add(Math.cos(a) * radius * mainScale * 0.45, 0.16, Math.sin(a) * radius * mainScale * 0.45);
            Vec3 p1 = impact.add(
               Math.cos(a) * radius * mainScale * (2.0 + bloom * 5.2),
               radius * mainScale * (0.65 + h * 3.0) * Math.max(0.0, 1.0 - after / 56.0),
               Math.sin(a) * radius * mainScale * (2.0 + bloom * 5.2)
            );
            ElfariaVfxVertexHelper.line(buffer, matrix, p0, p1, ElfariaVfxVertexHelper.scaleAlpha(BLAST, fade * 0.48));
         }

         ElfariaAlbisIceSheetRenderer.roundLines(
            buffer, matrix, impact.add(0.0, 0.1, 0.0), radius * mainScale * (2.4 + bloom * 7.2), fade * 0.55, seed + 44L, 0.075
         );
      }
   }

   private static void renderSpear(BufferBuilder buffer, Matrix4f matrix, Vec3 center, Vec3 direction, double radius, double length, double live, long seed) {
      if (!(live <= 0.002)) {
         Vec3 down = direction.normalize();
         Vec3 right = down.cross(new Vec3(0.0, 0.0, 1.0));
         if (right.lengthSqr() < 0.001) {
            right = new Vec3(1.0, 0.0, 0.0);
         }

         right = right.normalize();
         Vec3 side = down.cross(right).normalize();
         Vec3 tip = center.add(down.scale(length * 0.62));
         Vec3 tail = center.subtract(down.scale(length * 0.38));
         int sides = 8;

         for (int i = 0; i < sides; i++) {
            double a0 = i * Math.PI * 2.0 / sides;
            double a1 = (i + 1) * Math.PI * 2.0 / sides;
            Vec3 p0 = center.add(right.scale(Math.cos(a0) * radius)).add(side.scale(Math.sin(a0) * radius));
            Vec3 p1 = center.add(right.scale(Math.cos(a1) * radius)).add(side.scale(Math.sin(a1) * radius));
            ElfariaVfxVertexHelper.tri(buffer, matrix, p0, p1, tip, ElfariaVfxVertexHelper.scaleAlpha((i & 1) == 0 ? BRIGHT : ICE, live));
            ElfariaVfxVertexHelper.tri(buffer, matrix, p1, p0, tail, ElfariaVfxVertexHelper.scaleAlpha((i & 1) == 0 ? ICE_SOFT : DEEP, live * 0.66));
            if ((i & 1) == 0) {
               Vec3 edge = p0.lerp(tip, 0.68);
               Vec3 edge2 = p0.lerp(tip, 0.96);
               ElfariaVfxVertexHelper.tri(
                  buffer,
                  matrix,
                  edge.add(right.scale(radius * 0.045)),
                  edge.add(right.scale(-radius * 0.045)),
                  edge2,
                  ElfariaVfxVertexHelper.scaleAlpha(CORE, live * 0.86)
               );
            }
         }
      }
   }

   private static int visible(ElfariaStellasNateaVfxClient.Charge charge) {
      return Math.min(charge.main ? Math.max(charge.count, 72) : Math.max(charge.count, 42), charge.main ? 96 : 54);
   }

   private static double fallCurve(double fall) {
      fall = Math.max(0.0, Math.min(1.0, fall));
      return fall * fall * (3.0 - 2.0 * fall);
   }

   private static Vec3 snapToGround(Vec3 pos, double radius) {
      ClientLevel level = Minecraft.getInstance().level;
      if (level == null) {
         return pos;
      }

      MutableBlockPos block = new MutableBlockPos();
      int x = Mth.floor(pos.x);
      int z = Mth.floor(pos.z);
      int top = Math.min(level.getMaxBuildHeight() - 2, Mth.floor(pos.y) + 18);
      int bottom = Math.max(level.getMinBuildHeight() + 1, Mth.floor(pos.y) - 48);

      for (int y = top; y >= bottom; y--) {
         block.set(x, y, z);
         BlockState state = level.getBlockState(block);
         if (!state.isAir() && state.getFluidState().isEmpty() && level.getBlockState(block.above()).isAir()) {
            return new Vec3(pos.x, y + 1.04, pos.z);
         }
      }

      return pos.add(0.0, Math.max(0.0, radius * 0.05), 0.0);
   }

   private static final class Charge {
      private final Vec3 center;
      private float radius;
      private int count;
      private long seed;
      private boolean main;
      private int age;
      private int life;
      private int fallTicks;

      private Charge(Vec3 center, float radius, int count, long seed, boolean main) {
         this.center = center;
         this.radius = radius;
         this.count = count;
         this.seed = seed;
         this.main = main;
         this.fallTicks = main ? 60 : 52;
         this.life = this.fallTicks + (main ? 62 : 48);
      }

      private void refresh(float radius, int count, long seed, boolean main) {
         this.radius = Math.max(this.radius, radius);
         this.count = Math.max(this.count, count);
         this.seed ^= seed;
         this.main = this.main || main;
         this.fallTicks = this.main ? 60 : 52;
         this.life = Math.max(this.life, this.age + (this.main ? 62 : 48));
      }

      private boolean dead() {
         return this.age > this.life;
      }
   }

   private static final class Strike {
      private final Vec3 top;
      private final Vec3 impact;
      private final float radius;
      private final long seed;
      private final boolean main;
      private final boolean impactOnly;
      private final int fallTicks;
      private final int life;
      private int age;

      private Strike(Vec3 top, Vec3 impact, float radius, long seed, boolean main, boolean impactOnly) {
         this.top = top;
         this.impact = impact;
         this.radius = radius;
         this.seed = seed;
         this.main = main;
         this.impactOnly = impactOnly;
         this.fallTicks = impactOnly ? 0 : 18;
         this.life = impactOnly ? 62 : this.fallTicks + 58;
      }

      private boolean dead() {
         return this.age > this.life;
      }
   }
}
