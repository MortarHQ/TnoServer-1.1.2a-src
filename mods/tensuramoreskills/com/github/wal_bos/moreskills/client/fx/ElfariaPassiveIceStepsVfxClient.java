package com.github.wal_bos.moreskills.client.fx;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

public final class ElfariaPassiveIceStepsVfxClient {
   private static final List<ElfariaPassiveIceStepsVfxClient.Step> STEPS = new ArrayList<>();
   private static final List<ElfariaPassiveIceStepsVfxClient.ReviveAura> REVIVE_AURAS = new ArrayList<>();
   private static final int EDGE = ElfariaVfxVertexHelper.rgba(226, 255, 255, 190);
   private static final int WHITE = ElfariaVfxVertexHelper.rgba(255, 255, 255, 228);
   private static final int ICE = ElfariaVfxVertexHelper.rgba(92, 210, 255, 138);
   private static final int PALE = ElfariaVfxVertexHelper.rgba(190, 248, 255, 170);

   private ElfariaPassiveIceStepsVfxClient() {
   }

   public static void spawnStep(Vec3 pos, Vec3 look, float radius, long seed) {
      Vec3 ground = snapToGround(pos);
      if (ground != null) {
         addStep(ground, look, radius, 120, seed, false);
         trimSteps();
      }
   }

   public static void spawnReviveAura(Vec3 pos, float radius, long seed) {
      Vec3 ground = snapToGround(pos);
      Vec3 origin = ground == null ? pos : ground;
      int entityId = findNearestPlayerId(origin);
      REVIVE_AURAS.add(new ElfariaPassiveIceStepsVfxClient.ReviveAura(origin, entityId, Math.max(1.6F, radius), 2400, seed));

      while (REVIVE_AURAS.size() > 6) {
         REVIVE_AURAS.remove(0);
      }

      trimSteps();
   }

   public static void tick() {
      STEPS.removeIf(v -> ++v.age >= v.life);

      for (int i = REVIVE_AURAS.size() - 1; i >= 0; i--) {
         ElfariaPassiveIceStepsVfxClient.ReviveAura aura = REVIVE_AURAS.get(i);
         aura.age++;
         if (aura.age >= aura.life) {
            REVIVE_AURAS.remove(i);
         } else {
            tickReviveAura(aura);
         }
      }

      trimSteps();
   }

   public static void render(PoseStack poseStack, float partialTicks) {
      if (!STEPS.isEmpty()) {
         ElfariaVfxVertexHelper.beginIce();
         Vec3 cam = ElfariaVfxVertexHelper.camera();
         Matrix4f matrix = poseStack.last().pose();
         BufferBuilder quads = ElfariaVfxVertexHelper.beginQuads();

         for (ElfariaPassiveIceStepsVfxClient.Step step : STEPS) {
            renderStepSheet(quads, matrix, cam, step, partialTicks);
         }

         ElfariaVfxVertexHelper.finish(quads);
         BufferBuilder tris = ElfariaVfxVertexHelper.beginTriangles();

         for (ElfariaPassiveIceStepsVfxClient.Step step : STEPS) {
            renderStepCrystals(tris, matrix, cam, step, partialTicks);
         }

         ElfariaVfxVertexHelper.finish(tris);
         BufferBuilder lines = ElfariaVfxVertexHelper.beginLines();

         for (ElfariaPassiveIceStepsVfxClient.Step step : STEPS) {
            renderStepLines(lines, matrix, cam, step, partialTicks);
         }

         ElfariaVfxVertexHelper.finish(lines);
         ElfariaVfxVertexHelper.endIce();
      }
   }

   private static void tickReviveAura(ElfariaPassiveIceStepsVfxClient.ReviveAura aura) {
      Player player = getTrackedPlayer(aura);
      if (player != null && player.isAlive()) {
         Vec3 rawPos = player.position();
         Vec3 ground = snapToGround(rawPos);
         Vec3 look = new Vec3(player.getLookAngle().x, 0.0, player.getLookAngle().z);
         if (look.lengthSqr() < 0.001) {
            look = new Vec3(0.0, 0.0, 1.0);
         }

         look = look.normalize();
         Vec3 side = new Vec3(look.z, 0.0, -look.x).normalize();
         if (ground == null) {
            tickAirIcyAura(player, aura);
            aura.lastPlayerPos = rawPos;
         } else {
            double moved = rawPos.subtract(aura.lastPlayerPos).horizontalDistance();
            if (!aura.started) {
               aura.started = true;
               aura.lastPlayerPos = rawPos;
               aura.walkAccumulator = 0.0;
               spawnReviveBloom(ground, look, side, aura);
               spawnFootStep(ground, look, side, aura, true, true);
               spawnFootStep(ground, look, side, aura, false, true);
            } else {
               if (moved > 0.018) {
                  aura.walkAccumulator += moved;
                  aura.idleAccumulator = 0;
               } else {
                  aura.idleAccumulator++;
               }

               if (aura.walkAccumulator >= 0.58) {
                  aura.walkAccumulator = 0.0;
                  aura.left = !aura.left;
                  spawnFootStep(ground, look, side, aura, aura.left, false);
                  if (aura.age % 4 == 0) {
                     spawnTrailingFrost(ground, look, side, aura);
                  }
               }

               if (aura.idleAccumulator >= 28) {
                  aura.idleAccumulator = 0;
                  spawnIdleIce(ground, look, side, aura);
               }

               if (aura.age % 100 == 0) {
                  spawnSoftAuraRing(ground, aura);
               }

               aura.lastPlayerPos = rawPos;
            }
         }
      }
   }

   private static void tickAirIcyAura(Player player, ElfariaPassiveIceStepsVfxClient.ReviveAura aura) {
      if (aura.age % 3 == 0) {
         ClientLevel level = Minecraft.getInstance().level;
         if (level != null) {
            for (int i = 0; i < 2; i++) {
               double h = ElfariaVfxVertexHelper.hash(aura.seed, aura.age * 41L + i * 97L);
               double a = h * Math.PI * 2.0 + aura.age * 0.08;
               double r = 0.35 + ElfariaVfxVertexHelper.hash(aura.seed, aura.age * 59L + i * 131L) * 0.55;
               double y = 0.25 + ElfariaVfxVertexHelper.hash(aura.seed, aura.age * 73L + i * 173L) * 1.35;
               double x = player.getX() + Math.cos(a) * r;
               double z = player.getZ() + Math.sin(a) * r;
               double vx = Math.cos(a) * 0.012;
               double vz = Math.sin(a) * 0.012;
               level.addParticle(ParticleTypes.SNOWFLAKE, x, player.getY() + y, z, vx, 0.018, vz);
            }
         }
      }
   }

   private static void spawnReviveBloom(Vec3 pos, Vec3 look, Vec3 side, ElfariaPassiveIceStepsVfxClient.ReviveAura aura) {
      Vec3 left = snapToGround(pos.add(side.scale(0.42)));
      Vec3 right = snapToGround(pos.add(side.scale(-0.42)));
      if (left != null) {
         addStep(left, look, 1.05F, 300, aura.seed ^ 10558693L, true);
      }

      if (right != null) {
         addStep(right, look, 1.05F, 300, aura.seed ^ 485167117L, true);
      }
   }

   private static void spawnFootStep(Vec3 pos, Vec3 look, Vec3 side, ElfariaPassiveIceStepsVfxClient.ReviveAura aura, boolean left, boolean strong) {
      double sideOffset = left ? 0.34 : -0.34;
      double back = strong ? -0.06 : -0.18;
      Vec3 foot = pos.add(side.scale(sideOffset)).add(look.scale(back));
      Vec3 ground = snapToGround(foot);
      if (ground != null) {
         float size = strong ? 0.96F : 0.78F;
         int life = 300;
         addStep(ground, look, size, life, aura.seed + aura.age * 31L + (left ? 3L : 11L), true);
         if (strong) {
            Vec3 front = snapToGround(ground.add(look.scale(0.34)));
            if (front != null) {
               addStep(front, look, size * 0.58F, life, aura.seed ^ aura.age * 61L ^ (left ? 91L : 137L), true);
            }
         }
      }
   }

   private static void spawnTrailingFrost(Vec3 pos, Vec3 look, Vec3 side, ElfariaPassiveIceStepsVfxClient.ReviveAura aura) {
      for (int i = 0; i < 2; i++) {
         double h = ElfariaVfxVertexHelper.hash(aura.seed, aura.age * 97L + i * 31L);
         double s = i == 0 ? -0.34 : 0.34;
         double b = -0.38 - h * 0.42;
         Vec3 p = pos.add(side.scale(s)).add(look.scale(b));
         Vec3 ground = snapToGround(p);
         if (ground != null) {
            addStep(ground, look, 0.42F + (float)h * 0.16F, 300, aura.seed + aura.age * 13L + i * 19L, true);
         }
      }
   }

   private static void spawnIdleIce(Vec3 pos, Vec3 look, Vec3 side, ElfariaPassiveIceStepsVfxClient.ReviveAura aura) {
      for (int i = 0; i < 2; i++) {
         double h = ElfariaVfxVertexHelper.hash(aura.seed, aura.age * 41L + i * 71L);
         double offset = i == 0 ? 0.38 : -0.38;
         Vec3 p = pos.add(side.scale(offset)).add(look.scale((h - 0.5) * 0.32));
         Vec3 ground = snapToGround(p);
         if (ground != null) {
            addStep(ground, look, 0.38F + (float)h * 0.14F, 300, aura.seed + aura.age * 17L + i, true);
         }
      }
   }

   private static void spawnSoftAuraRing(Vec3 pos, ElfariaPassiveIceStepsVfxClient.ReviveAura aura) {
      for (int i = 0; i < 2; i++) {
         double a = aura.age * 0.025 + i * Math.PI;
         Vec3 dir = new Vec3(Math.cos(a), 0.0, Math.sin(a));
         Vec3 ground = snapToGround(pos.add(dir.scale(0.7 + aura.radius * 0.22)));
         if (ground != null) {
            addStep(ground, dir, 0.36F, 300, aura.seed ^ aura.age * 23L ^ i * 83L, true);
         }
      }
   }

   private static void addStep(Vec3 pos, Vec3 look, float radius, int life, long seed, boolean revive) {
      STEPS.add(new ElfariaPassiveIceStepsVfxClient.Step(pos, look, Math.max(0.18F, radius), Math.min(300, Math.max(30, life)), seed, revive));
   }

   private static void trimSteps() {
      while (STEPS.size() > 80) {
         STEPS.remove(0);
      }
   }

   private static Vec3 snapToGround(Vec3 pos) {
      Minecraft minecraft = Minecraft.getInstance();
      ClientLevel level = minecraft.level;
      if (level == null) {
         return null;
      }

      MutableBlockPos blockPos = new MutableBlockPos();
      int x = Mth.floor(pos.x);
      int z = Mth.floor(pos.z);
      int startY = Math.min(level.getMaxBuildHeight() - 2, Mth.floor(pos.y) + 4);
      int endY = Math.max(level.getMinBuildHeight() + 1, Mth.floor(pos.y) - 8);

      for (int y = startY; y >= endY; y--) {
         blockPos.set(x, y, z);
         BlockState state = level.getBlockState(blockPos);
         BlockState above = level.getBlockState(blockPos.above());
         if (!state.isAir() && above.isAir()) {
            return new Vec3(pos.x, y + 1.018, pos.z);
         }
      }

      return null;
   }

   private static int findNearestPlayerId(Vec3 pos) {
      Minecraft minecraft = Minecraft.getInstance();
      ClientLevel level = minecraft.level;
      if (level == null) {
         return -1;
      }

      Player best = null;
      double bestDist = 81.0;

      for (Player player : level.players()) {
         double d = player.distanceToSqr(pos);
         if (d < bestDist) {
            bestDist = d;
            best = player;
         }
      }

      return best == null ? -1 : best.getId();
   }

   private static Player getTrackedPlayer(ElfariaPassiveIceStepsVfxClient.ReviveAura aura) {
      Minecraft minecraft = Minecraft.getInstance();
      ClientLevel level = minecraft.level;
      if (level == null) {
         return null;
      }

      if (aura.entityId != -1 && level.getEntity(aura.entityId) instanceof Player player) {
         return player;
      }

      Player best = null;
      double bestDist = 144.0;

      for (Player player : level.players()) {
         double d = player.distanceToSqr(aura.origin);
         if (d < bestDist) {
            bestDist = d;
            best = player;
         }
      }

      if (best != null) {
         aura.entityId = best.getId();
      }

      return best;
   }

   private static void renderStepSheet(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, ElfariaPassiveIceStepsVfxClient.Step step, float partialTicks) {
      double t = (step.age + partialTicks) / step.life;
      double grow = ElfariaVfxVertexHelper.smooth(Math.min(1.0, t * 4.0));
      double fadeStart = step.revive ? 0.82 : 0.68;
      double fade = 1.0 - ElfariaVfxVertexHelper.smooth(Math.max(0.0, (t - fadeStart) / (1.0 - fadeStart)));
      double live = fade * (step.revive ? 0.92 : 0.76);
      Vec3 center = ElfariaVfxVertexHelper.rel(step.pos, cam);
      Vec3 forward = flat(step.look);
      Vec3 right = new Vec3(forward.z, 0.0, -forward.x).normalize();
      ElfariaAlbisIceSheetRenderer.groundSheet(
         buffer, matrix, center, forward, right, step.radius * 1.58 * grow, step.radius * 0.88 * grow, live, step.seed, 0.018
      );
      int shards = step.revive ? 6 : 4;

      for (int i = 0; i < shards; i++) {
         double h = ElfariaVfxVertexHelper.hash(step.seed, 800L + i);
         double u = -1.0 + h * 2.0;
         double v = -0.78 + ElfariaVfxVertexHelper.hash(step.seed, 900L + i) * 1.56;
         double len = step.radius * (0.2 + ElfariaVfxVertexHelper.hash(step.seed, 1000L + i) * 0.66) * grow;
         double width = step.radius * (0.018 + ElfariaVfxVertexHelper.hash(step.seed, 1100L + i) * 0.045) * grow;
         Vec3 base = center.add(right.scale(u * step.radius * 1.2 * grow)).add(forward.scale(v * step.radius * 0.72 * grow)).add(0.0, 0.034, 0.0);
         Vec3 dir = ElfariaVfxVertexHelper.rotateY(forward, (h - 0.5) * 1.65).normalize();
         Vec3 side = new Vec3(dir.z, 0.0, -dir.x).normalize();
         Vec3 a = base.add(side.scale(width));
         Vec3 b = base.add(side.scale(-width));
         Vec3 c = base.add(dir.scale(len)).add(side.scale(-width * 0.2));
         Vec3 d = base.add(dir.scale(len)).add(side.scale(width * 0.2));
         ElfariaVfxVertexHelper.quad(buffer, matrix, a, b, c, d, ElfariaVfxVertexHelper.scaleAlpha(i % 4 == 0 ? WHITE : PALE, live * (0.35 + h * 0.42)));
      }
   }

   private static void renderStepCrystals(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, ElfariaPassiveIceStepsVfxClient.Step step, float partialTicks) {
      double t = (step.age + partialTicks) / step.life;
      double grow = ElfariaVfxVertexHelper.smooth(Math.min(1.0, t * 3.2));
      double fadeStart = step.revive ? 0.84 : 0.7;
      double fade = 1.0 - ElfariaVfxVertexHelper.smooth(Math.max(0.0, (t - fadeStart) / (1.0 - fadeStart)));
      double live = fade * (step.revive ? 0.92 : 0.7);
      Vec3 center = ElfariaVfxVertexHelper.rel(step.pos, cam);
      Vec3 forward = flat(step.look);
      Vec3 right = new Vec3(forward.z, 0.0, -forward.x).normalize();
      int count = step.revive ? 2 : 1;

      for (int i = 0; i < count; i++) {
         double h = ElfariaVfxVertexHelper.hash(step.seed, 2100L + i);
         double u = -0.82 + h * 1.64;
         double v = -0.52 + ElfariaVfxVertexHelper.hash(step.seed, 2200L + i) * 1.04;
         Vec3 base = center.add(right.scale(u * step.radius * grow)).add(forward.scale(v * step.radius * 0.62 * grow)).add(0.0, 0.045, 0.0);
         double height = step.radius * (0.2 + ElfariaVfxVertexHelper.hash(step.seed, 2300L + i) * 0.44) * grow;
         double width = step.radius * (0.035 + ElfariaVfxVertexHelper.hash(step.seed, 2400L + i) * 0.048) * grow;
         Vec3 dir = ElfariaVfxVertexHelper.rotateY(forward, (h - 0.5) * 1.2).normalize();
         Vec3 side = new Vec3(dir.z, 0.0, -dir.x).normalize();
         Vec3 top = base.add(dir.scale(width * 0.7)).add(0.0, height, 0.0);
         ElfariaVfxVertexHelper.tri(
            buffer,
            matrix,
            base.add(side.scale(width)),
            base.add(side.scale(-width)),
            top,
            ElfariaVfxVertexHelper.scaleAlpha(i % 2 == 0 ? WHITE : ICE, live * 0.78)
         );
      }
   }

   private static void renderStepLines(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, ElfariaPassiveIceStepsVfxClient.Step step, float partialTicks) {
      double t = (step.age + partialTicks) / step.life;
      double grow = ElfariaVfxVertexHelper.smooth(Math.min(1.0, t * 4.0));
      double fadeStart = step.revive ? 0.84 : 0.7;
      double fade = 1.0 - ElfariaVfxVertexHelper.smooth(Math.max(0.0, (t - fadeStart) / (1.0 - fadeStart)));
      double live = fade * (step.revive ? 0.95 : 0.78);
      Vec3 center = ElfariaVfxVertexHelper.rel(step.pos, cam);
      Vec3 forward = flat(step.look);
      Vec3 right = new Vec3(forward.z, 0.0, -forward.x).normalize();
      ElfariaAlbisIceSheetRenderer.sheetLines(
         buffer, matrix, center, forward, right, step.radius * 1.52 * grow, step.radius * 0.86 * grow, live, step.seed, 0.045
      );
      int cracks = step.revive ? 10 : 6;

      for (int i = 0; i < cracks; i++) {
         double h = ElfariaVfxVertexHelper.hash(step.seed, 3000L + i);
         double u = -1.0 + h * 2.0;
         double v = -0.74 + ElfariaVfxVertexHelper.hash(step.seed, 3100L + i) * 1.48;
         double len = step.radius * (0.1 + ElfariaVfxVertexHelper.hash(step.seed, 3200L + i) * 0.62) * grow;
         Vec3 p0 = center.add(right.scale(u * step.radius * 1.25 * grow)).add(forward.scale(v * step.radius * 0.74 * grow)).add(0.0, 0.062, 0.0);
         Vec3 dir = ElfariaVfxVertexHelper.rotateY(forward, (h - 0.5) * 2.4).normalize();
         Vec3 p1 = p0.add(dir.scale(len));
         ElfariaVfxVertexHelper.line(buffer, matrix, p0, p1, ElfariaVfxVertexHelper.scaleAlpha(i % 5 == 0 ? WHITE : EDGE, live * (0.32 + h * 0.48)));
         if (i % 6 == 0) {
            Vec3 branch = p0.add(ElfariaVfxVertexHelper.rotateY(dir, 0.75).scale(len * 0.42));
            ElfariaVfxVertexHelper.line(buffer, matrix, p0.lerp(p1, 0.42), branch, ElfariaVfxVertexHelper.scaleAlpha(PALE, live * 0.35));
         }
      }

      int glints = step.revive ? 3 : 2;

      for (int i = 0; i < glints; i++) {
         double h = ElfariaVfxVertexHelper.hash(step.seed, 5000L + i);
         double u = -0.9 + h * 1.8;
         double v = -0.62 + ElfariaVfxVertexHelper.hash(step.seed, 5100L + i) * 1.24;
         Vec3 p = center.add(right.scale(u * step.radius * grow)).add(forward.scale(v * step.radius * 0.62 * grow)).add(0.0, 0.085, 0.0);
         double s = step.radius * (0.045 + ElfariaVfxVertexHelper.hash(step.seed, 5200L + i) * 0.08) * grow;
         ElfariaVfxVertexHelper.line(buffer, matrix, p.add(right.scale(-s)), p.add(right.scale(s)), ElfariaVfxVertexHelper.scaleAlpha(WHITE, live * 0.68));
         ElfariaVfxVertexHelper.line(
            buffer, matrix, p.add(forward.scale(-s * 0.62)), p.add(forward.scale(s * 0.62)), ElfariaVfxVertexHelper.scaleAlpha(WHITE, live * 0.52)
         );
      }
   }

   private static Vec3 flat(Vec3 look) {
      Vec3 forward = new Vec3(look.x, 0.0, look.z);
      if (forward.lengthSqr() < 0.001) {
         forward = new Vec3(0.0, 0.0, 1.0);
      }

      return forward.normalize();
   }

   private static final class ReviveAura {
      private final Vec3 origin;
      private final float radius;
      private final int life;
      private final long seed;
      private int entityId;
      private int age;
      private Vec3 lastPlayerPos;
      private double walkAccumulator;
      private int idleAccumulator;
      private boolean left;
      private boolean started;

      private ReviveAura(Vec3 origin, int entityId, float radius, int life, long seed) {
         this.origin = origin;
         this.entityId = entityId;
         this.radius = radius;
         this.life = life;
         this.seed = seed;
         this.lastPlayerPos = origin;
      }
   }

   private static final class Step {
      private final Vec3 pos;
      private final Vec3 look;
      private final float radius;
      private final int life;
      private final long seed;
      private final boolean revive;
      private int age;

      private Step(Vec3 pos, Vec3 look, float radius, int life, long seed, boolean revive) {
         this.pos = pos;
         this.look = look;
         this.radius = radius;
         this.life = life;
         this.seed = seed;
         this.revive = revive;
      }
   }
}
