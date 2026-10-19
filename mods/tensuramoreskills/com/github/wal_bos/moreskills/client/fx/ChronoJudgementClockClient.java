package com.github.wal_bos.moreskills.client.fx;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent.Post;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent.Stage;
import org.joml.Matrix4f;

public final class ChronoJudgementClockClient {
   private static final Map<Integer, ChronoJudgementClockClient.ActiveClock> ACTIVE = new ConcurrentHashMap<>();
   private static final int OUT_START = 20;
   private static final int OUT_DURATION = 240;
   private static final int MIDNIGHT = 260;
   private static final int REWIND_DURATION = 240;
   private static final int TOTAL_DURATION = 520;
   private static final int ORB_COUNT = 420;
   private static float time;

   private ChronoJudgementClockClient() {
   }

   public static void add(int bossId, int targetId, double x, double y, double z, int duration, int seed) {
      ACTIVE.put(bossId, new ChronoJudgementClockClient.ActiveClock(bossId, targetId, new Vec3(x, y, z), Math.max(520, duration), seed));
      FeldwayAwakeningCameraShake.avatarPulse(3.8F, 56);
   }

   public static void onClientTick(Post event) {
      Minecraft minecraft = Minecraft.getInstance();
      time += 0.16F;
      if (minecraft.level == null) {
         ACTIVE.clear();
      } else {
         Iterator<ChronoJudgementClockClient.ActiveClock> iterator = ACTIVE.values().iterator();

         while (iterator.hasNext()) {
            ChronoJudgementClockClient.ActiveClock clock = iterator.next();
            clock.age++;
            if (clock.age == 20 || clock.age == 80 || clock.age == 140 || clock.age == 200) {
               FeldwayAwakeningCameraShake.avatarPulse(1.45F, 16);
            }

            if (clock.age == 260) {
               FeldwayAwakeningCameraShake.avatarImpact(13.0F, 48);
            }

            if (clock.age == 308 || clock.age == 356 || clock.age == 404 || clock.age == 452) {
               FeldwayAwakeningCameraShake.avatarImpact(3.8F, 18);
            }

            if (clock.age > clock.duration) {
               iterator.remove();
            }
         }
      }
   }

   public static void render(RenderLevelStageEvent event) {
      if (event.getStage() == Stage.AFTER_PARTICLES && !ACTIVE.isEmpty()) {
         Minecraft minecraft = Minecraft.getInstance();
         if (minecraft.level != null && minecraft.player != null) {
            Camera camera = minecraft.gameRenderer.getMainCamera();
            Vec3 cam = camera.getPosition();
            PoseStack poseStack = event.getPoseStack();
            Matrix4f matrix = poseStack.last().pose();
            float partialTick = event.getPartialTick().getGameTimeDeltaPartialTick(false);
            RenderSystem.setShader(GameRenderer::getPositionColorShader);
            RenderSystem.enableBlend();
            RenderSystem.blendFuncSeparate(770, 1, 1, 0);
            RenderSystem.disableCull();
            RenderSystem.enableDepthTest();
            RenderSystem.depthMask(false);

            for (ChronoJudgementClockClient.ActiveClock clock : ACTIVE.values()) {
               updateCenter(minecraft, clock, partialTick);
               renderClock(matrix, cam, clock, partialTick);
            }

            RenderSystem.defaultBlendFunc();
            RenderSystem.depthMask(true);
            RenderSystem.enableCull();
            RenderSystem.disableBlend();
         }
      }
   }

   private static void updateCenter(Minecraft minecraft, ChronoJudgementClockClient.ActiveClock clock, float partialTick) {
      if (minecraft.level != null) {
         Entity boss = minecraft.level.getEntity(clock.bossId);
         if (boss != null && boss.isAlive()) {
            double x = Mth.lerp(partialTick, boss.xOld, boss.getX());
            double y = Mth.lerp(partialTick, boss.yOld, boss.getY());
            double z = Mth.lerp(partialTick, boss.zOld, boss.getZ());
            clock.center = new Vec3(x, y + boss.getBbHeight() + 18.0, z);
         }
      }
   }

   private static void renderClock(Matrix4f matrix, Vec3 cam, ChronoJudgementClockClient.ActiveClock clock, float partialTick) {
      float age = clock.age + partialTick;
      float appear = smooth(Mth.clamp(age / 22.0F, 0.0F, 1.0F));
      float fade = smooth(Mth.clamp((clock.duration - age) / 28.0F, 0.0F, 1.0F));
      float alpha = appear * fade;
      renderClockFace(matrix, cam, clock, age, alpha);
      renderClockHands(matrix, cam, clock, age, alpha);
      renderTrueSphereOrbField(matrix, cam, clock, age, alpha);
      renderMidnightEffect(matrix, cam, clock, age, alpha);
   }

   private static void renderClockFace(Matrix4f matrix, Vec3 cam, ChronoJudgementClockClient.ActiveClock clock, float age, float alpha) {
      BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
      boolean drew = false;
      Vec3 center = clock.center;
      Vec3 xAxis = new Vec3(1.0, 0.0, 0.0);
      Vec3 yAxis = new Vec3(0.0, 1.0, 0.0);
      Vec3 zAxis = new Vec3(0.0, 0.0, 1.0);
      float open = smooth(Mth.clamp(age / 38.0F, 0.0F, 1.0F));
      float pulse = 0.72F + 0.28F * Mth.sin(time * 5.0F);
      float midnightPulse = age >= 236.0F && age <= 294.0F ? Mth.sin(Mth.clamp((age - 236.0F) / 58.0F, 0.0F, 1.0F) * (float) Math.PI) : 0.0F;

      for (int i = 0; i < 7; i++) {
         double radius = 24.0 + i * 10.0 + open * 12.0 + midnightPulse * 18.0;
         float a = alpha * pulse * (0.42F - i * 0.042F);
         drew |= addRing(buffer, matrix, cam, center.add(0.0, i * 0.08, 0.0), xAxis, zAxis, radius, 144, 0.2F, 1.0F, 0.9F, 0.42F, a);
         drew |= addRing(buffer, matrix, cam, center, xAxis, yAxis, radius * 0.68, 112, 0.075F, 1.0F, 1.0F, 0.86F, a * 0.62F);
         drew |= addRing(buffer, matrix, cam, center, zAxis, yAxis, radius * 0.68, 112, 0.075F, 1.0F, 1.0F, 0.86F, a * 0.62F);
      }

      for (int i = 0; i < 12; i++) {
         double a = i / 12.0 * Math.PI * 2.0 - (Math.PI / 2);
         Vec3 inner = center.add(Math.cos(a) * 43.0, 0.0, Math.sin(a) * 43.0);
         Vec3 outer = center.add(Math.cos(a) * 65.0, 0.0, Math.sin(a) * 65.0);
         drew |= addBeam(buffer, matrix, cam, inner, outer, i % 3 == 0 ? 0.88F : 0.38F, 1.0F, 0.88F, 0.36F, alpha * 0.78F);
         drew |= addBeam(
            buffer, matrix, cam, inner.add(0.0, 0.06, 0.0), outer.add(0.0, 0.06, 0.0), i % 3 == 0 ? 0.18F : 0.085F, 1.0F, 1.0F, 0.92F, alpha * 0.88F
         );
      }

      for (int i = 0; i < 160; i++) {
         Vec3 dir = sphereDirection(i, 160, clock.seed + 991);
         float seed = hash(clock.seed * 0.021F + i * 8.17F);
         Vec3 start = center.add(dir.scale(16.0 + seed * 12.0));
         Vec3 end = center.add(dir.scale(54.0 + seed * 34.0));
         drew |= addBeam(buffer, matrix, cam, start, end, 0.055F, 1.0F, 0.78F, 0.18F, alpha * 0.18F);
      }

      if (drew) {
         BufferUploader.drawWithShader(buffer.buildOrThrow());
      }
   }

   private static void renderClockHands(Matrix4f matrix, Vec3 cam, ChronoJudgementClockClient.ActiveClock clock, float age, float alpha) {
      BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
      boolean drew = false;
      Vec3 center = clock.center.add(0.0, 0.32, 0.0);
      float progress = Mth.clamp(age / 260.0F, 0.0F, 1.0F);
      float snap = age >= 248.0F && age <= 276.0F ? Mth.sin(Mth.clamp((age - 248.0F) / 28.0F, 0.0F, 1.0F) * (float) Math.PI) : 0.0F;
      double hourAngle = (-Math.PI / 2) + progress * Math.PI * 2.0;
      double minuteAngle = (-Math.PI / 2) + progress * Math.PI * 8.0;
      Vec3 hour = center.add(Math.cos(hourAngle) * 34.0, 0.0, Math.sin(hourAngle) * 34.0);
      Vec3 minute = center.add(Math.cos(minuteAngle) * 56.0, 0.0, Math.sin(minuteAngle) * 56.0);
      drew |= addBeam(buffer, matrix, cam, center, hour, 1.02F + snap * 0.85F, 1.0F, 0.78F, 0.18F, alpha * 0.88F);
      drew |= addBeam(buffer, matrix, cam, center, minute, 0.74F + snap * 0.58F, 1.0F, 0.92F, 0.46F, alpha * 0.84F);
      drew |= addTrueOrb(buffer, matrix, cam, center, 1.95 + snap * 2.1, 1.0F, 1.0F, 0.86F, alpha);
      if (drew) {
         BufferUploader.drawWithShader(buffer.buildOrThrow());
      }
   }

   private static void renderTrueSphereOrbField(Matrix4f matrix, Vec3 cam, ChronoJudgementClockClient.ActiveClock clock, float age, float alpha) {
      if (!(age < 16.0F)) {
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         boolean drew = false;
         Vec3 center = clock.center;
         float birth = smooth(Mth.clamp((age - 16.0F) / 16.0F, 0.0F, 1.0F));
         float outward = smooth(Mth.clamp((age - 20.0F) / 240.0F, 0.0F, 1.0F));
         float rewind = smooth(Mth.clamp((age - 260.0F) / 240.0F, 0.0F, 1.0F));
         boolean rewinding = age >= 260.0F;

         for (int i = 0; i < 420; i++) {
            Vec3 dir = sphereDirection(i, 420, clock.seed);
            Vec3 axisA = stablePerpendicular(dir);
            Vec3 axisB = dir.cross(axisA).normalize();
            float seedA = hash(clock.seed * 0.013F + i * 5.31F);
            float seedB = hash(clock.seed * 0.019F + i * 11.77F);
            float seedC = hash(clock.seed * 0.029F + i * 17.11F);
            float seedD = hash(clock.seed * 0.037F + i * 19.73F);
            double socketRadius = 18.0 + seedA * 20.0;
            double endRadius = 92.0 + seedB * 155.0;
            double curl = 10.0 + seedC * 34.0;
            Vec3 socket = center.add(dir.scale(socketRadius));
            Vec3 far = center.add(dir.scale(endRadius));
            double waveA = Math.sin(time * 0.34 + i * 0.61);
            double waveB = Math.cos(time * 0.31 + i * 0.77);
            Vec3 apexOut = center.add(dir.scale((socketRadius + endRadius) * 0.52)).add(axisA.scale(waveA * curl)).add(axisB.scale(waveB * curl));
            Vec3 apexBack = center.add(dir.scale((socketRadius + endRadius) * 0.54))
               .add(axisA.scale(Math.cos(time * 0.42 + i * 0.67) * curl * 0.82))
               .add(axisB.scale(Math.sin(time * 0.39 + i * 0.91) * curl * 0.82));
            Vec3 orbPos = rewinding ? bezier(far, apexBack, socket, rewind) : bezier(socket, apexOut, far, outward);
            float pulse = 0.68F + 0.32F * Mth.sin(time * 7.2F + i * 0.57F);
            float orbAlpha = alpha * birth * pulse;
            if (rewinding && rewind > 0.985F) {
               orbAlpha *= smooth(Mth.clamp((clock.duration - age) / 24.0F, 0.0F, 1.0F));
            }

            double orbSize = 0.7 + seedC * 0.82 + seedD * 0.3;
            Vec3 trailStart = rewinding ? far : socket;
            drew |= addBeam(buffer, matrix, cam, trailStart, orbPos, 0.05F, 1.0F, 0.68F, 0.14F, orbAlpha * 0.2F);
            drew |= addBeam(buffer, matrix, cam, trailStart.add(axisA.scale(0.1)), orbPos.add(axisA.scale(0.1)), 0.02F, 1.0F, 1.0F, 0.86F, orbAlpha * 0.32F);
            drew |= addTrueOrb(buffer, matrix, cam, orbPos, orbSize, 1.0F, 0.82F, 0.22F, orbAlpha);
            if ((i & 1) == 0) {
               drew |= addMiniRing(buffer, matrix, cam, orbPos, axisA, axisB, orbSize * 1.5, 16, 0.018F, 1.0F, 1.0F, 0.8F, orbAlpha * 0.22F);
            }
         }

         if (drew) {
            BufferUploader.drawWithShader(buffer.buildOrThrow());
         }
      }
   }

   private static void renderMidnightEffect(Matrix4f matrix, Vec3 cam, ChronoJudgementClockClient.ActiveClock clock, float age, float alpha) {
      if (!(age < 242.0F) && !(age > 356.0F)) {
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         boolean drew = false;
         Vec3 center = clock.center;
         float snap = age >= 252.0F && age <= 274.0F ? Mth.sin(Mth.clamp((age - 252.0F) / 22.0F, 0.0F, 1.0F) * (float) Math.PI) : 0.0F;
         float burst = Mth.clamp((age - 260.0F) / 48.0F, 0.0F, 1.0F);
         float burstShape = Mth.sin(burst * (float) Math.PI);

         for (int i = 0; i < 10; i++) {
            double radius = 12.0 + i * 13.0 + burst * 115.0;
            float a = alpha * burstShape * (0.42F - i * 0.033F);
            drew |= addRing(buffer, matrix, cam, center, new Vec3(1.0, 0.0, 0.0), new Vec3(0.0, 0.0, 1.0), radius, 132, 0.15F, 1.0F, 0.78F, 0.18F, a);
            drew |= addRing(
               buffer, matrix, cam, center, new Vec3(1.0, 0.0, 0.0), new Vec3(0.0, 1.0, 0.0), radius * 0.72, 104, 0.06F, 1.0F, 1.0F, 0.86F, a * 0.6F
            );
            drew |= addRing(
               buffer, matrix, cam, center, new Vec3(0.0, 0.0, 1.0), new Vec3(0.0, 1.0, 0.0), radius * 0.72, 104, 0.06F, 1.0F, 1.0F, 0.86F, a * 0.6F
            );
         }

         for (int i = 0; i < 220; i++) {
            Vec3 dir = sphereDirection(i, 220, clock.seed + 7777);
            float seed = hash(clock.seed * 0.067F + i * 2.1F);
            Vec3 start = center.add(dir.scale(4.0 + snap * 5.0));
            Vec3 end = center.add(dir.scale(42.0 + burst * 140.0 + seed * 42.0));
            drew |= addBeam(buffer, matrix, cam, start, end, 0.07F, 1.0F, 0.68F, 0.12F, alpha * burstShape * 0.32F);
         }

         drew |= addTrueOrb(buffer, matrix, cam, center, 3.4 + snap * 3.4 + burst * 2.3, 1.0F, 1.0F, 0.82F, alpha);
         if (drew) {
            BufferUploader.drawWithShader(buffer.buildOrThrow());
         }
      }
   }

   private static boolean addTrueOrb(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 center, double size, float r, float g, float b, float alpha) {
      if (!(alpha <= 0.001F) && !(size <= 0.001)) {
         boolean drew = false;
         Vec3 x = new Vec3(size, 0.0, 0.0);
         Vec3 y = new Vec3(0.0, size, 0.0);
         Vec3 z = new Vec3(0.0, 0.0, size);
         drew |= addDiamond(buffer, matrix, cam, center, x.scale(1.9), y.scale(1.9), r, g * 0.7F, b * 0.42F, alpha * 0.12F);
         drew |= addDiamond(buffer, matrix, cam, center, y.scale(1.9), z.scale(1.9), r, g * 0.7F, b * 0.42F, alpha * 0.12F);
         drew |= addDiamond(buffer, matrix, cam, center, z.scale(1.9), x.scale(1.9), r, g * 0.7F, b * 0.42F, alpha * 0.12F);
         drew |= addDiamond(buffer, matrix, cam, center, x.scale(1.18), y.scale(1.18), r, g * 0.84F, b * 0.58F, alpha * 0.28F);
         drew |= addDiamond(buffer, matrix, cam, center, y.scale(1.18), z.scale(1.18), r, g * 0.84F, b * 0.58F, alpha * 0.28F);
         drew |= addDiamond(buffer, matrix, cam, center, z.scale(1.18), x.scale(1.18), r, g * 0.84F, b * 0.58F, alpha * 0.28F);
         drew |= addDiamond(buffer, matrix, cam, center, x, y, r, g, b, alpha * 0.68F);
         drew |= addDiamond(buffer, matrix, cam, center, y, z, r, g, b, alpha * 0.68F);
         drew |= addDiamond(buffer, matrix, cam, center, z, x, r, g, b, alpha * 0.68F);
         drew |= addBeam(buffer, matrix, cam, center.subtract(x.scale(0.78)), center.add(x.scale(0.78)), 0.02F, 1.0F, 1.0F, 0.94F, alpha * 0.42F);
         drew |= addBeam(buffer, matrix, cam, center.subtract(y.scale(0.78)), center.add(y.scale(0.78)), 0.02F, 1.0F, 1.0F, 0.94F, alpha * 0.42F);
         return drew | addBeam(buffer, matrix, cam, center.subtract(z.scale(0.78)), center.add(z.scale(0.78)), 0.02F, 1.0F, 1.0F, 0.94F, alpha * 0.42F);
      } else {
         return false;
      }
   }

   private static boolean addDiamond(
      BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 center, Vec3 axisA, Vec3 axisB, float r, float g, float b, float alpha
   ) {
      if (alpha <= 0.001F) {
         return false;
      }

      Vec3 p0 = center.add(axisA);
      Vec3 p1 = center.add(axisB);
      Vec3 p2 = center.subtract(axisA);
      Vec3 p3 = center.subtract(axisB);
      addVertex(buffer, matrix, cam, p0, r, g, b, alpha * 0.38F);
      addVertex(buffer, matrix, cam, p1, r, g, b, alpha);
      addVertex(buffer, matrix, cam, p2, r, g, b, alpha * 0.38F);
      addVertex(buffer, matrix, cam, p3, r, g, b, alpha);
      return true;
   }

   private static boolean addRing(
      BufferBuilder buffer,
      Matrix4f matrix,
      Vec3 cam,
      Vec3 center,
      Vec3 axisA,
      Vec3 axisB,
      double radius,
      int segments,
      float width,
      float r,
      float g,
      float b,
      float alpha
   ) {
      if (!(alpha <= 0.001F) && !(radius <= 0.001) && !(width <= 0.001F)) {
         boolean drew = false;
         double spin = time * 0.18 + radius * 0.015;

         for (int i = 0; i < segments; i++) {
            if (i % 9 != 4) {
               double a0 = (double)i / segments * Math.PI * 2.0 + spin;
               double a1 = (i + 0.8) / segments * Math.PI * 2.0 + spin;
               Vec3 p0 = center.add(axisA.scale(Math.cos(a0) * radius)).add(axisB.scale(Math.sin(a0) * radius));
               Vec3 p1 = center.add(axisA.scale(Math.cos(a1) * radius)).add(axisB.scale(Math.sin(a1) * radius));
               drew |= addBeam(buffer, matrix, cam, p0, p1, width, r, g, b, alpha);
            }
         }

         return drew;
      } else {
         return false;
      }
   }

   private static boolean addMiniRing(
      BufferBuilder buffer,
      Matrix4f matrix,
      Vec3 cam,
      Vec3 center,
      Vec3 axisA,
      Vec3 axisB,
      double radius,
      int segments,
      float width,
      float r,
      float g,
      float b,
      float alpha
   ) {
      if (!(alpha <= 0.001F) && !(radius <= 0.001) && !(width <= 0.001F)) {
         boolean drew = false;
         double spin = time * 1.15 + radius;

         for (int i = 0; i < segments; i++) {
            double a0 = (double)i / segments * Math.PI * 2.0 + spin;
            double a1 = (i + 0.7) / segments * Math.PI * 2.0 + spin;
            Vec3 p0 = center.add(axisA.scale(Math.cos(a0) * radius)).add(axisB.scale(Math.sin(a0) * radius));
            Vec3 p1 = center.add(axisA.scale(Math.cos(a1) * radius)).add(axisB.scale(Math.sin(a1) * radius));
            drew |= addBeam(buffer, matrix, cam, p0, p1, width, r, g, b, alpha);
         }

         return drew;
      } else {
         return false;
      }
   }

   private static boolean addBeam(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 a, Vec3 b, float width, float r, float g, float blue, float alpha) {
      if (!(alpha <= 0.001F) && !(width <= 0.001F)) {
         Vec3 dir = b.subtract(a);
         if (dir.lengthSqr() < 4.0E-7) {
            return false;
         }

         Vec3 side = stablePerpendicular(dir.normalize()).scale(width);
         Vec3 side2 = dir.normalize().cross(side.normalize()).normalize().scale(width * 0.58);
         Vec3 p0 = a.subtract(side);
         Vec3 p1 = a.add(side);
         Vec3 p2 = b.add(side);
         Vec3 p3 = b.subtract(side);
         addVertex(buffer, matrix, cam, p0, r, g, blue, alpha);
         addVertex(buffer, matrix, cam, p1, r, g, blue, alpha);
         addVertex(buffer, matrix, cam, p2, r, g, blue, alpha * 0.58F);
         addVertex(buffer, matrix, cam, p3, r, g, blue, alpha * 0.58F);
         Vec3 q0 = a.subtract(side2);
         Vec3 q1 = a.add(side2);
         Vec3 q2 = b.add(side2);
         Vec3 q3 = b.subtract(side2);
         addVertex(buffer, matrix, cam, q0, r, g, blue, alpha * 0.58F);
         addVertex(buffer, matrix, cam, q1, r, g, blue, alpha * 0.58F);
         addVertex(buffer, matrix, cam, q2, r, g, blue, alpha * 0.34F);
         addVertex(buffer, matrix, cam, q3, r, g, blue, alpha * 0.34F);
         return true;
      } else {
         return false;
      }
   }

   private static void addVertex(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 p, float r, float g, float b, float a) {
      buffer.addVertex(matrix, (float)(p.x - cam.x), (float)(p.y - cam.y), (float)(p.z - cam.z)).setColor(r, g, b, a);
   }

   private static Vec3 bezier(Vec3 a, Vec3 b, Vec3 c, float t) {
      Vec3 ab = lerp(a, b, t);
      Vec3 bc = lerp(b, c, t);
      return lerp(ab, bc, t);
   }

   private static Vec3 lerp(Vec3 a, Vec3 b, float t) {
      return new Vec3(Mth.lerp(t, a.x, b.x), Mth.lerp(t, a.y, b.y), Mth.lerp(t, a.z, b.z));
   }

   private static Vec3 stablePerpendicular(Vec3 dir) {
      Vec3 base = Math.abs(dir.y) < 0.82 ? new Vec3(0.0, 1.0, 0.0) : new Vec3(1.0, 0.0, 0.0);
      Vec3 side = dir.cross(base);
      if (side.lengthSqr() < 0.001) {
         side = new Vec3(0.0, 0.0, 1.0);
      }

      return side.normalize();
   }

   private static Vec3 sphereDirection(int index, int count, int seed) {
      double fi = index + 0.5;
      double y = 1.0 - 2.0 * fi / Math.max(1.0, count);
      double radius = Math.sqrt(Math.max(0.0, 1.0 - y * y));
      double theta = index * 2.399963229728653 + hash(seed * 0.017F + index * 0.733F) * Math.PI * 2.0;
      Vec3 dir = new Vec3(Math.cos(theta) * radius, y, Math.sin(theta) * radius);
      double tiltA = (hash(seed * 0.031F + index * 1.73F) - 0.5) * 0.18;
      double tiltB = (hash(seed * 0.047F + index * 2.91F) - 0.5) * 0.18;
      dir = dir.add(tiltA, tiltB, -tiltA * 0.65);
      return dir.lengthSqr() < 0.001 ? new Vec3(1.0, 0.0, 0.0) : dir.normalize();
   }

   private static float smooth(float value) {
      return value * value * (3.0F - 2.0F * value);
   }

   private static float hash(float value) {
      return Mth.frac(Mth.sin(value * 12.9898F) * 43758.547F);
   }

   private static final class ActiveClock {
      private final int bossId;
      private final int targetId;
      private final int duration;
      private final int seed;
      private Vec3 center;
      private int age;

      private ActiveClock(int bossId, int targetId, Vec3 center, int duration, int seed) {
         this.bossId = bossId;
         this.targetId = targetId;
         this.center = center;
         this.duration = duration;
         this.seed = seed;
      }
   }
}
