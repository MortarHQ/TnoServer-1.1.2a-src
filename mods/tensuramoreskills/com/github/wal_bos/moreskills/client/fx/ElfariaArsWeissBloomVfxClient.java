package com.github.wal_bos.moreskills.client.fx;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

public final class ElfariaArsWeissBloomVfxClient {
   private static final List<ElfariaArsWeissBloomVfxClient.Bloom> BLOOMS = new ArrayList<>();
   private static final int ICE = ElfariaVfxVertexHelper.rgba(88, 204, 246, 112);
   private static final int EDGE = ElfariaVfxVertexHelper.rgba(232, 255, 255, 210);

   private ElfariaArsWeissBloomVfxClient() {
   }

   public static void spawnBloom(Vec3 center, float radius, long seed) {
      BLOOMS.add(new ElfariaArsWeissBloomVfxClient.Bloom(center, radius, 50, seed));
   }

   public static void tick() {
      BLOOMS.removeIf(v -> ++v.age >= v.life);
   }

   public static void render(PoseStack poseStack, float partialTicks) {
      if (!BLOOMS.isEmpty()) {
         ElfariaVfxVertexHelper.beginIce();
         Vec3 cam = ElfariaVfxVertexHelper.camera();
         Matrix4f matrix = poseStack.last().pose();
         BufferBuilder quads = ElfariaVfxVertexHelper.beginQuads();

         for (ElfariaArsWeissBloomVfxClient.Bloom bloom : BLOOMS) {
            renderBloom(quads, matrix, cam, bloom, partialTicks);
         }

         ElfariaVfxVertexHelper.finish(quads);
         BufferBuilder tris = ElfariaVfxVertexHelper.beginTriangles();

         for (ElfariaArsWeissBloomVfxClient.Bloom bloom : BLOOMS) {
            renderBloomSpikes(tris, matrix, cam, bloom, partialTicks);
         }

         ElfariaVfxVertexHelper.finish(tris);
         BufferBuilder lines = ElfariaVfxVertexHelper.beginLines();

         for (ElfariaArsWeissBloomVfxClient.Bloom bloom : BLOOMS) {
            renderLines(lines, matrix, cam, bloom, partialTicks);
         }

         ElfariaVfxVertexHelper.finish(lines);
         ElfariaVfxVertexHelper.endIce();
      }
   }

   private static void renderBloom(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, ElfariaArsWeissBloomVfxClient.Bloom bloom, float partialTicks) {
      double t = (bloom.age + partialTicks) / bloom.life;
      double open = ElfariaVfxVertexHelper.smooth(Math.min(1.0, t * 2.1));
      double fade = 1.0 - ElfariaVfxVertexHelper.smooth(Math.max(0.0, (t - 0.68) / 0.32));
      Vec3 c = ElfariaVfxVertexHelper.rel(bloom.center, cam).add(0.0, 0.08, 0.0);
      ElfariaAlbisIceSheetRenderer.roundSheet(buffer, matrix, c, bloom.radius * 0.92 * open, fade * 0.74, bloom.seed, 0.02);

      for (int i = 0; i < 24; i++) {
         double a = i * Math.PI * 2.0 / 24.0;
         double w = 0.1 + ElfariaVfxVertexHelper.hash(bloom.seed, i) * 0.06;
         Vec3 dir = new Vec3(Math.cos(a), 0.0, Math.sin(a));
         Vec3 side = new Vec3(-dir.z, 0.0, dir.x);
         Vec3 inner = c.add(dir.scale(bloom.radius * 0.18 * open));
         Vec3 mid = c.add(dir.scale(bloom.radius * 0.82 * open)).add(0.0, bloom.radius * 0.24 * open, 0.0);
         Vec3 tip = c.add(dir.scale(bloom.radius * 1.38 * open))
            .add(0.0, bloom.radius * (0.52 + ElfariaVfxVertexHelper.hash(bloom.seed, 100L + i) * 0.4) * open, 0.0);
         ElfariaVfxVertexHelper.quad(
            buffer,
            matrix,
            inner.add(side.scale(w)),
            mid.add(side.scale(w * 0.5)),
            tip,
            mid.add(side.scale(-w * 0.5)),
            ElfariaVfxVertexHelper.scaleAlpha(ICE, fade)
         );
      }
   }

   private static void renderBloomSpikes(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, ElfariaArsWeissBloomVfxClient.Bloom bloom, float partialTicks) {
      double t = (bloom.age + partialTicks) / bloom.life;
      double open = ElfariaVfxVertexHelper.smooth(Math.min(1.0, t * 2.0));
      double fade = 1.0 - ElfariaVfxVertexHelper.smooth(Math.max(0.0, (t - 0.68) / 0.32));
      Vec3 c = ElfariaVfxVertexHelper.rel(bloom.center, cam);

      for (int i = 0; i < 14; i++) {
         double a = i * Math.PI * 2.0 / 14.0;
         Vec3 base = c.add(Math.cos(a) * bloom.radius * 0.55 * open, 0.04, Math.sin(a) * bloom.radius * 0.55 * open);
         ElfariaAlbisIceSheetRenderer.prism(
            buffer,
            matrix,
            base,
            bloom.radius * (0.28 + ElfariaVfxVertexHelper.hash(bloom.seed, i) * 0.52) * open,
            bloom.radius * 0.045,
            5,
            fade,
            bloom.seed + i
         );
      }
   }

   private static void renderLines(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, ElfariaArsWeissBloomVfxClient.Bloom bloom, float partialTicks) {
      double t = (bloom.age + partialTicks) / bloom.life;
      double open = ElfariaVfxVertexHelper.smooth(Math.min(1.0, t * 2.0));
      double fade = 1.0 - ElfariaVfxVertexHelper.smooth(t);
      Vec3 c = ElfariaVfxVertexHelper.rel(bloom.center, cam).add(0.0, 0.1, 0.0);
      ElfariaAlbisIceSheetRenderer.roundLines(buffer, matrix, c, bloom.radius * 1.25 * open, fade, bloom.seed, 0.045);

      for (int i = 0; i < 24; i++) {
         double a = i * Math.PI * 2.0 / 24.0;
         Vec3 tip = c.add(Math.cos(a) * bloom.radius * 1.38 * open, bloom.radius * 0.7 * open, Math.sin(a) * bloom.radius * 1.38 * open);
         ElfariaVfxVertexHelper.line(buffer, matrix, c, tip, ElfariaVfxVertexHelper.scaleAlpha(EDGE, fade));
      }
   }

   private static final class Bloom {
      private final Vec3 center;
      private final float radius;
      private final int life;
      private final long seed;
      private int age;

      private Bloom(Vec3 center, float radius, int life, long seed) {
         this.center = center;
         this.radius = radius;
         this.life = life;
         this.seed = seed;
      }
   }
}
