package com.github.wal_bos.moreskills.client.fx;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

public final class ElfariaMyrdasFridolieteVfxClient {
   private static final List<ElfariaMyrdasFridolieteVfxClient.Barrier> BARRIERS = new ArrayList<>();
   private static final List<ElfariaMyrdasFridolieteVfxClient.FreezeField> FIELDS = new ArrayList<>();
   private static final int EDGE = ElfariaVfxVertexHelper.rgba(244, 255, 255, 220);

   private ElfariaMyrdasFridolieteVfxClient() {
   }

   public static void spawnBarrier(Vec3 center, float radius, int life, long seed, boolean main) {
      for (ElfariaMyrdasFridolieteVfxClient.Barrier barrier : BARRIERS) {
         if (barrier.center.distanceToSqr(center) <= Math.max(4.0, radius * radius * 0.25)) {
            barrier.refresh(center, radius, Math.max(16, life), seed, main);
            return;
         }
      }

      BARRIERS.add(new ElfariaMyrdasFridolieteVfxClient.Barrier(center, radius, Math.max(16, life), seed, main));

      while (BARRIERS.size() > 8) {
         BARRIERS.remove(0);
      }
   }

   public static void spawnBreak(Vec3 center, float radius, long seed) {
      FIELDS.add(new ElfariaMyrdasFridolieteVfxClient.FreezeField(center, radius * 1.55F, 38, seed, true));

      while (FIELDS.size() > 12) {
         FIELDS.remove(0);
      }
   }

   public static void spawnFieldFreeze(Vec3 center, float radius, long seed) {
      for (ElfariaMyrdasFridolieteVfxClient.FreezeField field : FIELDS) {
         if (!field.breakBurst && field.center.distanceToSqr(center) <= Math.max(9.0, radius * radius * 0.45)) {
            field.refresh(center, radius, seed);
            return;
         }
      }

      FIELDS.add(new ElfariaMyrdasFridolieteVfxClient.FreezeField(center, radius, 52, seed, false));

      while (FIELDS.size() > 12) {
         FIELDS.remove(0);
      }
   }

   public static void tick() {
      BARRIERS.removeIf(v -> ++v.age >= v.expireAge);
      FIELDS.removeIf(v -> ++v.age >= v.expireAge);
   }

   public static void render(PoseStack poseStack, float partialTicks) {
      if (!BARRIERS.isEmpty() || !FIELDS.isEmpty()) {
         ElfariaVfxVertexHelper.beginIce();
         Vec3 cam = ElfariaVfxVertexHelper.camera();
         Matrix4f matrix = poseStack.last().pose();
         BufferBuilder tris = ElfariaVfxVertexHelper.beginTriangles();

         for (ElfariaMyrdasFridolieteVfxClient.Barrier barrier : BARRIERS) {
            renderBarrier(tris, matrix, cam, barrier, partialTicks);
         }

         for (ElfariaMyrdasFridolieteVfxClient.FreezeField field : FIELDS) {
            renderFieldSpikes(tris, matrix, cam, field, partialTicks);
         }

         ElfariaVfxVertexHelper.finish(tris);
         BufferBuilder quads = ElfariaVfxVertexHelper.beginQuads();

         for (ElfariaMyrdasFridolieteVfxClient.FreezeField field : FIELDS) {
            renderFieldSheet(quads, matrix, cam, field, partialTicks);
         }

         ElfariaVfxVertexHelper.finish(quads);
         BufferBuilder lines = ElfariaVfxVertexHelper.beginLines();

         for (ElfariaMyrdasFridolieteVfxClient.Barrier barrier : BARRIERS) {
            renderBarrierEdges(lines, matrix, cam, barrier, partialTicks);
         }

         for (ElfariaMyrdasFridolieteVfxClient.FreezeField field : FIELDS) {
            renderFieldLines(lines, matrix, cam, field, partialTicks);
         }

         ElfariaVfxVertexHelper.finish(lines);
         ElfariaVfxVertexHelper.endIce();
      }
   }

   private static void renderBarrier(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, ElfariaMyrdasFridolieteVfxClient.Barrier barrier, float partialTicks) {
      double birth = ElfariaVfxVertexHelper.smooth(Math.min(1.0, (barrier.age + partialTicks) / 5.0));
      double remain = barrier.expireAge - barrier.age - partialTicks;
      double fade = ElfariaVfxVertexHelper.smooth(Math.min(1.0, remain / 8.0));
      double live = birth * fade;
      if (!(live <= 0.002)) {
         Vec3 c = ElfariaVfxVertexHelper.rel(barrier.center, cam).add(0.0, 0.04, 0.0);
         double r = barrier.radius * (barrier.main ? 1.15 : 1.02);
         double h = barrier.radius * (barrier.main ? 2.55 : 2.18);
         ElfariaAlbisIceSheetRenderer.purePyramid(buffer, matrix, c, r, h, live * 0.88);
      }
   }

   private static void renderBarrierEdges(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, ElfariaMyrdasFridolieteVfxClient.Barrier barrier, float partialTicks) {
      double birth = ElfariaVfxVertexHelper.smooth(Math.min(1.0, (barrier.age + partialTicks) / 5.0));
      double remain = barrier.expireAge - barrier.age - partialTicks;
      double fade = ElfariaVfxVertexHelper.smooth(Math.min(1.0, remain / 8.0));
      double live = birth * fade;
      if (!(live <= 0.002)) {
         Vec3 c = ElfariaVfxVertexHelper.rel(barrier.center, cam).add(0.0, 0.04, 0.0);
         double r = barrier.radius * (barrier.main ? 1.15 : 1.02);
         double h = barrier.radius * (barrier.main ? 2.55 : 2.18);
         ElfariaAlbisIceSheetRenderer.pyramidEdges(buffer, matrix, c, r, h, live);
      }
   }

   private static void renderFieldSheet(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, ElfariaMyrdasFridolieteVfxClient.FreezeField field, float partialTicks) {
      double t = (field.age + partialTicks) / field.expireAge;
      double grow = field.breakBurst ? ElfariaVfxVertexHelper.smooth(Math.min(1.0, t * 2.6)) : 1.0;
      double fade = field.breakBurst
         ? 1.0 - ElfariaVfxVertexHelper.smooth(Math.max(0.0, (t - 0.72) / 0.28))
         : ElfariaVfxVertexHelper.smooth(Math.min(1.0, (field.expireAge - field.age - partialTicks) / 8.0));
      Vec3 c = ElfariaVfxVertexHelper.rel(field.center, cam);
      double radius = field.radius * grow;
      ElfariaAlbisIceSheetRenderer.albisDisc(buffer, matrix, c, radius, fade * 0.7, field.seed, 0.032);
   }

   private static void renderFieldLines(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, ElfariaMyrdasFridolieteVfxClient.FreezeField field, float partialTicks) {
      double t = (field.age + partialTicks) / field.expireAge;
      double grow = field.breakBurst ? ElfariaVfxVertexHelper.smooth(Math.min(1.0, t * 2.6)) : 1.0;
      double fade = field.breakBurst
         ? 1.0 - ElfariaVfxVertexHelper.smooth(t)
         : ElfariaVfxVertexHelper.smooth(Math.min(1.0, (field.expireAge - field.age - partialTicks) / 8.0));
      Vec3 c = ElfariaVfxVertexHelper.rel(field.center, cam);
      ElfariaAlbisIceSheetRenderer.albisDiscLines(buffer, matrix, c, field.radius * grow, fade * 0.75, field.seed, 0.06);
   }

   private static void renderFieldSpikes(
      BufferBuilder buffer, Matrix4f matrix, Vec3 cam, ElfariaMyrdasFridolieteVfxClient.FreezeField field, float partialTicks
   ) {
      double t = (field.age + partialTicks) / field.expireAge;
      double grow = field.breakBurst ? ElfariaVfxVertexHelper.smooth(Math.min(1.0, t * 2.2)) : 1.0;
      double fade = field.breakBurst
         ? 1.0 - ElfariaVfxVertexHelper.smooth(Math.max(0.0, (t - 0.72) / 0.28))
         : ElfariaVfxVertexHelper.smooth(Math.min(1.0, (field.expireAge - field.age - partialTicks) / 8.0));
      Vec3 c = ElfariaVfxVertexHelper.rel(field.center, cam);
      int count = field.breakBurst ? 56 : 42;
      double inner = field.radius * 1.14 * grow;
      double outer = field.radius * 1.62 * grow;

      for (int i = 0; i < count; i++) {
         double a = i * Math.PI * 2.0 / count;
         double h = ElfariaVfxVertexHelper.hash(field.seed, i * 31L);
         double rr = inner + (outer - inner) * h;
         double height = field.radius * (0.28 + ElfariaVfxVertexHelper.hash(field.seed, 400L + i) * 0.72) * fade;
         double width = field.radius * (0.018 + ElfariaVfxVertexHelper.hash(field.seed, 700L + i) * 0.022);
         Vec3 base = c.add(Math.cos(a) * rr, 0.04, Math.sin(a) * rr);
         ElfariaAlbisIceSheetRenderer.prism(buffer, matrix, base, height, width, 5, fade * 0.92, field.seed + i);
      }
   }

   private static final class Barrier {
      private Vec3 center;
      private float radius;
      private int expireAge;
      private long seed;
      private boolean main;
      private int age;

      private Barrier(Vec3 center, float radius, int life, long seed, boolean main) {
         this.center = center;
         this.radius = radius;
         this.expireAge = life;
         this.seed = seed;
         this.main = main;
      }

      private void refresh(Vec3 center, float radius, int life, long seed, boolean main) {
         this.center = this.center.lerp(center, 0.2);
         this.radius = Math.max(this.radius, radius);
         this.expireAge = Math.max(this.expireAge, this.age + life);
         this.seed ^= seed;
         this.main = this.main || main;
      }
   }

   private static final class FreezeField {
      private Vec3 center;
      private float radius;
      private int expireAge;
      private long seed;
      private final boolean breakBurst;
      private int age;

      private FreezeField(Vec3 center, float radius, int life, long seed, boolean breakBurst) {
         this.center = center;
         this.radius = radius;
         this.expireAge = life;
         this.seed = seed;
         this.breakBurst = breakBurst;
      }

      private void refresh(Vec3 center, float radius, long seed) {
         this.center = this.center.lerp(center, 0.18);
         this.radius = Math.max(this.radius, radius);
         this.expireAge = Math.max(this.expireAge, this.age + 52);
         this.seed ^= seed;
      }
   }
}
