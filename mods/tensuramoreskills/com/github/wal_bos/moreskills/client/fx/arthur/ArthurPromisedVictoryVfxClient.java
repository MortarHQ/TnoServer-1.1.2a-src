package com.github.wal_bos.moreskills.client.fx.arthur;

import com.github.wal_bos.moreskills.client.fx.ClientVfxGate;
import com.github.wal_bos.moreskills.client.fx.PathsClientState;
import com.github.wal_bos.moreskills.net.msg.ArthurAvalonVfxData;
import com.mojang.blaze3d.platform.GlStateManager.DestFactor;
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor;
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
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent.Stage;
import net.neoforged.neoforge.common.NeoForge;
import org.joml.Matrix4f;

public final class ArthurPromisedVictoryVfxClient {
   private static final List<ArthurPromisedVictoryVfxClient.Cast> CASTS = new ArrayList<>();
   private static final float DRAW_A_END = 32.0F;
   private static final float DRAW_B_START = 14.0F;
   private static final float DRAW_B_END = 46.0F;
   private static final float LOCK_START = 44.0F;
   private static final float LAUNCH_START = 68.0F;
   private static final float TRAVEL_END = 156.0F;
   private static final float CROSS_PHASE_END = 184.0F;
   private static final float ORB_PHASE_START = 170.0F;
   private static final float ORB_EJECTION_START = 202.0F;
   private static final float PILLAR_START = 220.0F;
   private static final float PILLAR_SHAKE_TICK = 222.0F;
   private static final float PILLAR_PEAK = 254.0F;
   private static final float AFTERGLOW_START = 272.0F;
   private static final float MAIN_END = 334.0F;
   private static boolean initialized;

   private ArthurPromisedVictoryVfxClient() {
   }

   public static void init() {
      if (!initialized) {
         initialized = true;
         NeoForge.EVENT_BUS.addListener(ArthurPromisedVictoryVfxClient::render);
      }
   }

   public static void spawn(ArthurAvalonVfxData payload) {
      if (ClientVfxGate.allowed("arthur_promised_victory")) {
         init();
         Minecraft minecraft = Minecraft.getInstance();
         if (minecraft.level != null) {
            if (minecraft.player != null) {
               double maxSpawnDistance = 900.0;
               if (!(minecraft.player.distanceToSqr(payload.start()) > maxSpawnDistance * maxSpawnDistance)) {
                  CASTS.add(new ArthurPromisedVictoryVfxClient.Cast(payload, Util.getMillis()));

                  while (CASTS.size() > 6) {
                     CASTS.remove(0);
                  }
               }
            }
         }
      }
   }

   private static void render(RenderLevelStageEvent event) {
      if (!ClientVfxGate.allowed("arthur_promised_victory")) {
         CASTS.clear();
      } else if (event.getStage() == Stage.AFTER_PARTICLES) {
         Minecraft minecraft = Minecraft.getInstance();
         if (minecraft.level != null) {
            if (minecraft.player != null) {
               if (!CASTS.isEmpty()) {
                  long now = Util.getMillis();
                  Vec3 camera = event.getCamera().getPosition();
                  PoseStack poseStack = event.getPoseStack();
                  RenderSystem.enableBlend();
                  RenderSystem.disableCull();
                  RenderSystem.enableDepthTest();
                  RenderSystem.depthMask(false);
                  RenderSystem.blendFuncSeparate(SourceFactor.SRC_ALPHA, DestFactor.ONE, SourceFactor.ONE, DestFactor.ONE_MINUS_SRC_ALPHA);
                  RenderSystem.setShader(GameRenderer::getPositionColorShader);
                  poseStack.pushPose();
                  poseStack.translate(-camera.x, -camera.y, -camera.z);
                  Matrix4f matrix = poseStack.last().pose();
                  BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
                  boolean wrote = false;
                  Iterator<ArthurPromisedVictoryVfxClient.Cast> iterator = CASTS.iterator();

                  while (iterator.hasNext()) {
                     ArthurPromisedVictoryVfxClient.Cast cast = iterator.next();
                     float age = cast.ageTicks(now);
                     if (cast.dead(age)) {
                        iterator.remove();
                     } else {
                        cast.updateShake(minecraft, age);
                        wrote |= draw(buffer, matrix, cast, age, now, camera);
                     }
                  }

                  if (wrote) {
                     try {
                        BufferUploader.drawWithShader(buffer.buildOrThrow());
                     } catch (IllegalStateException var12) {
                     }
                  }

                  poseStack.popPose();
                  RenderSystem.depthMask(true);
                  RenderSystem.enableCull();
                  RenderSystem.defaultBlendFunc();
                  RenderSystem.disableBlend();
               }
            }
         }
      }
   }

   private static boolean draw(BufferBuilder buffer, Matrix4f matrix, ArthurPromisedVictoryVfxClient.Cast cast, float age, long now, Vec3 camera) {
      ArthurAvalonVfxData payload = cast.payload;
      Vec3 start = payload.start();
      Vec3 end = payload.end();
      Vec3 direction = end.subtract(start);
      if (direction.lengthSqr() < 1.0E-8) {
         direction = new Vec3(0.0, 0.0, 1.0);
      }

      double length = Math.max(1.0, direction.length());
      Vec3 dir = direction.normalize();
      Vec3 side = safeSide(dir);
      Vec3 up = dir.cross(side).normalize();
      float scale = Mth.clamp(Math.max(7.5F, payload.radius()) * 0.96F, 7.5F, 15.5F);
      float seconds = (float)now * 0.001F;
      int detail = detailLevel(camera, end);
      if (payload.variant() == 1) {
         return hitBurst(buffer, matrix, end, dir, side, up, scale, age, cast.durationTicks, seconds, payload.seed(), detail);
      }

      Vec3 formationCenter = start.add(dir.scale(Math.min(4.2, length * 0.08)));
      float drawA = smoother(Mth.clamp(age / 32.0F, 0.0F, 1.0F));
      float drawB = smoother(Mth.clamp((age - 14.0F) / 32.0F, 0.0F, 1.0F));
      float lock = smoother(Mth.clamp((age - 44.0F) / 24.0F, 0.0F, 1.0F));
      float launch = smoother(Mth.clamp((age - 68.0F) / 88.0F, 0.0F, 1.0F));
      Vec3 head = formationCenter.lerp(end, launch);
      boolean wrote = false;
      if (age < 73.0F) {
         wrote |= formation(buffer, matrix, formationCenter, dir, side, up, scale, drawA, drawB, lock, launch, seconds, payload.seed(), detail);
      }

      if (age >= 68.0F && age <= 160.0F) {
         wrote |= travelingCross(buffer, matrix, formationCenter, head, end, dir, side, up, scale, launch, seconds, payload.seed(), detail);
      }

      if (age >= 154.0F && age <= 186.0F) {
         wrote |= crossArrivalPhase(buffer, matrix, end, dir, side, up, scale, age, seconds, payload.seed(), detail);
      }

      if (age >= 170.0F && age < 222.0F) {
         wrote |= orbPhase(buffer, matrix, end, dir, side, up, scale, age, seconds, payload.seed(), detail);
      }

      if (age >= 220.0F) {
         wrote |= pillarPhase(buffer, matrix, end, dir, side, up, scale, age, seconds, payload.seed(), detail);
      }

      return wrote;
   }

   private static int detailLevel(Vec3 camera, Vec3 center) {
      double distance = camera.distanceTo(center);
      if (distance < 180.0) {
         return 3;
      } else {
         return distance < 360.0 ? 2 : 1;
      }
   }

   private static boolean formation(
      BufferBuilder buffer,
      Matrix4f matrix,
      Vec3 center,
      Vec3 dir,
      Vec3 side,
      Vec3 up,
      float scale,
      float drawA,
      float drawB,
      float lock,
      float launch,
      float time,
      long seed,
      int detail
   ) {
      boolean wrote = false;
      float live = (0.24F + 0.76F * Math.max(drawA, drawB)) * (1.0F - launch * 0.9F);
      Vec3 diagonalA = side.add(up).normalize();
      Vec3 diagonalB = side.subtract(up).normalize();
      double reach = scale * 2.55;
      double thickness = scale * (0.078 + lock * 0.052);
      if (drawA > 0.001F) {
         wrote |= drawnSlashBlade(buffer, matrix, center, diagonalA, dir, reach, thickness, drawA, 255, 133, 0, live * 0.3F, 255, 255, 240, live);
         Vec3 tip = center.subtract(diagonalA.scale(reach)).lerp(center.add(diagonalA.scale(reach)), drawA).add(dir.scale(0.09));
         wrote |= penTipCore(buffer, matrix, tip, side, up, scale, live, time);
         wrote |= penSparkTrail(buffer, matrix, center, diagonalA, dir, side, up, reach, drawA, scale, live, time, seed, 1000, detail);
      }

      if (drawB > 0.001F) {
         Vec3 secondCenter = center.add(dir.scale(0.07));
         wrote |= drawnSlashBlade(buffer, matrix, secondCenter, diagonalB, dir, reach, thickness * 0.94, drawB, 255, 194, 32, live * 0.34F, 255, 255, 246, live);
         Vec3 tip = secondCenter.subtract(diagonalB.scale(reach)).lerp(secondCenter.add(diagonalB.scale(reach)), drawB).add(dir.scale(0.11));
         wrote |= penTipCore(buffer, matrix, tip, side, up, scale * 0.94F, live, time + 0.35F);
         wrote |= penSparkTrail(buffer, matrix, secondCenter, diagonalB, dir, side, up, reach, drawB, scale, live, time, seed, 2000, detail);
      }

      float both = Math.min(drawA, drawB);
      if (both > 0.001F) {
         float innerLive = live * smoother(Mth.clamp((both - 0.28F) / 0.72F, 0.0F, 1.0F));
         wrote |= drawnSlashBlade(
            buffer,
            matrix,
            center.subtract(dir.scale(scale * 0.075)),
            diagonalA,
            dir,
            reach * 1.018,
            thickness * 0.34,
            drawA,
            255,
            218,
            96,
            innerLive * 0.24F,
            255,
            252,
            223,
            innerLive * 0.52F
         );
         wrote |= drawnSlashBlade(
            buffer,
            matrix,
            center.subtract(dir.scale(scale * 0.095)),
            diagonalB,
            dir,
            reach * 1.018,
            thickness * 0.32,
            drawB,
            255,
            232,
            133,
            innerLive * 0.22F,
            255,
            255,
            235,
            innerLive * 0.48F
         );
      }

      if (lock > 0.001F) {
         wrote |= lockGeometry(buffer, matrix, center, dir, side, up, scale, live, lock, time, seed, detail);
      }

      return wrote;
   }

   private static boolean penTipCore(BufferBuilder buffer, Matrix4f matrix, Vec3 tip, Vec3 side, Vec3 up, float scale, float live, float time) {
      boolean wrote = false;
      float pulse = 0.78F + 0.22F * Mth.sin(time * 11.0F);
      wrote |= layeredOrbFlare(buffer, matrix, tip, side, up, scale * 0.18F, live * pulse);
      return wrote
         | planeRing(buffer, matrix, tip, side, up, scale * 0.22, Math.max(0.008F, scale * 0.0045F), 28, time * 4.0, 255, 232, 125, live * 0.62F, 7, 1);
   }

   private static boolean penSparkTrail(
      BufferBuilder buffer,
      Matrix4f matrix,
      Vec3 center,
      Vec3 axis,
      Vec3 forward,
      Vec3 side,
      Vec3 up,
      double reach,
      float progress,
      float scale,
      float live,
      float time,
      long seed,
      int salt,
      int detail
   ) {
      if (progress <= 0.001F) {
         return false;
      }

      if (live <= 0.001F) {
         return false;
      }

      boolean wrote = false;
      Vec3 fullStart = center.subtract(axis.scale(reach));
      Vec3 fullEnd = center.add(axis.scale(reach));
      int count = detail >= 3 ? 18 : (detail == 2 ? 12 : 8);
      float window = 0.42F;

      for (int i = 0; i < count; i++) {
         float back = (float)i / Math.max(1, count - 1);
         float localProgress = Mth.clamp(progress - back * window, 0.0F, 1.0F);
         if (!(localProgress <= 0.001F)) {
            float h0 = hash(seed, salt + i);
            float h1 = hash(seed, salt + 100 + i);
            Vec3 p = fullStart.lerp(fullEnd, localProgress)
               .add(forward.scale((h0 - 0.5) * scale * 0.09))
               .add(side.scale(Mth.sin(time * (5.2F + h1 * 2.8F) + i) * scale * 0.022));
            float trail = 1.0F - back;
            float blink = 0.48F + 0.52F * (0.5F + 0.5F * Mth.sin(time * (8.0F + h0 * 6.0F) + i * 0.81F));
            wrote |= goldenMote(buffer, matrix, p, side, up, scale * (0.018F + h1 * 0.035F), live * trail * blink * 0.72F);
         }
      }

      Vec3 tip = fullStart.lerp(fullEnd, progress);
      int spokes = detail >= 2 ? 8 : 4;

      for (int i = 0; i < spokes; i++) {
         float h = hash(seed, salt + 400 + i);
         double angle = i * (float) (Math.PI * 2) / spokes + time * (2.3 + h);
         Vec3 p = tip.add(side.scale(Math.cos(angle) * scale * (0.09 + h * 0.09))).add(up.scale(Math.sin(angle) * scale * (0.09 + h * 0.09)));
         wrote |= ribbon(buffer, matrix, p, tip, Math.max(0.006F, scale * 0.0034F), 255, 227, 111, live * (0.24F + h * 0.32F));
      }

      return wrote;
   }

   private static boolean lockGeometry(
      BufferBuilder buffer, Matrix4f matrix, Vec3 center, Vec3 dir, Vec3 side, Vec3 up, float scale, float live, float lock, float time, long seed, int detail
   ) {
      boolean wrote = false;
      int ringLayers = detail >= 3 ? 9 : (detail == 2 ? 7 : 5);

      for (int layer = 0; layer < ringLayers; layer++) {
         double radius = scale * (0.42 + layer * 0.22) * (0.86 + lock * 0.14);
         double spin = time * (0.38 + lock * (1.9 + layer * 0.22)) * (layer % 2 == 0 ? 1.0 : -1.0) + layer * 0.48;
         float alpha = live * lock * (0.58F - layer * 0.048F);
         wrote |= planeRing(
            buffer,
            matrix,
            center.subtract(dir.scale(0.14 + layer * 0.045)),
            side,
            up,
            radius,
            Math.max(0.011F, scale * (0.0084F - layer * 4.5E-4F)),
            72 - layer * 3,
            spin,
            255,
            layer < 2 ? 255 : 225,
            layer < 2 ? 240 : 102,
            alpha,
            9,
            1
         );
      }

      int rays = detail >= 3 ? 28 : (detail == 2 ? 20 : 12);

      for (int i = 0; i < rays; i++) {
         float h = hash(seed, 3200 + i);
         double angle = i * (float) (Math.PI * 2) / rays + time * (0.3 + lock * 0.85);
         double inner = scale * (0.19 + h * 0.06);
         double outer = scale * (1.04 + h * 0.58);
         Vec3 p0 = center.add(side.scale(Math.cos(angle) * inner)).add(up.scale(Math.sin(angle) * inner)).subtract(dir.scale(0.2));
         Vec3 p1 = center.add(side.scale(Math.cos(angle) * outer)).add(up.scale(Math.sin(angle) * outer)).subtract(dir.scale(0.2));
         wrote |= ribbon(
            buffer,
            matrix,
            p0,
            p1,
            Math.max(0.008F, scale * 0.0048F),
            255,
            i % 7 == 0 ? 255 : 223,
            i % 7 == 0 ? 242 : 99,
            live * lock * (i % 7 == 0 ? 0.56F : 0.22F)
         );
      }

      int satellites = detail >= 2 ? 14 : 8;

      for (int i = 0; i < satellites; i++) {
         float h0 = hash(seed, 3500 + i);
         float h1 = hash(seed, 3600 + i);
         double angle = i * (float) (Math.PI * 2) / satellites - time * (0.52 + lock * 1.04 + h0 * 0.3);
         double radius = scale * (1.32 + h1 * 0.42);
         Vec3 satellite = center.add(side.scale(Math.cos(angle) * radius))
            .add(up.scale(Math.sin(angle) * radius))
            .add(dir.scale(Mth.sin(time * 1.7F + i) * scale * 0.07));
         float pulse = 0.45F + 0.55F * (0.5F + 0.5F * Mth.sin(time * (6.5F + h0 * 4.5F) + i));
         wrote |= goldenMote(buffer, matrix, satellite, side, up, scale * (0.048F + h1 * 0.042F), live * lock * pulse * 0.72F);
      }

      return wrote | layeredOrbFlare(buffer, matrix, center, side, up, scale * (0.52F + lock * 0.42F), live * lock);
   }

   private static boolean travelingCross(
      BufferBuilder buffer,
      Matrix4f matrix,
      Vec3 start,
      Vec3 head,
      Vec3 end,
      Vec3 dir,
      Vec3 side,
      Vec3 up,
      float scale,
      float launch,
      float time,
      long seed,
      int detail
   ) {
      if (launch <= 0.001F) {
         return false;
      }

      boolean wrote = false;
      float live = smoother(Mth.clamp(launch / 0.1F, 0.0F, 1.0F));
      float travelTailFade = 1.0F - smoother(Mth.clamp((launch - 0.92F) / 0.08F, 0.0F, 1.0F));
      live *= 0.38F + travelTailFade * 0.62F;
      Vec3 diagonalA = side.add(up).normalize();
      Vec3 diagonalB = side.subtract(up).normalize();
      double pulse = 1.0 + 0.035 * Mth.sin(time * 4.0F);
      double reach = scale * 1.92 * pulse;
      double thickness = scale * 0.148;
      wrote |= slashBlade(buffer, matrix, head, diagonalA, dir, reach, thickness * 1.16, 255, 112, 0, live * 0.24F, 255, 253, 225, live);
      wrote |= slashBlade(buffer, matrix, head.add(dir.scale(0.085)), diagonalB, dir, reach, thickness * 1.08, 255, 184, 29, live * 0.3F, 255, 255, 243, live);
      if (launch > 0.15F) {
         double echoDistance = Math.min(start.distanceTo(head), scale * 2.4);
         Vec3 echoCenter = head.subtract(dir.scale(echoDistance));
         float echoLive = live * 0.12F;
         wrote |= slashBlade(buffer, matrix, echoCenter, diagonalA, dir, reach * 0.9, thickness * 0.26, 255, 202, 71, echoLive * 0.62F, 255, 247, 202, echoLive);
         wrote |= slashBlade(
            buffer,
            matrix,
            echoCenter.add(dir.scale(0.03)),
            diagonalB,
            dir,
            reach * 0.9,
            thickness * 0.24,
            255,
            229,
            127,
            echoLive * 0.56F,
            255,
            253,
            229,
            echoLive * 0.92F
         );
      }

      int collars = detail >= 3 ? 7 : (detail == 2 ? 5 : 3);

      for (int ring = 0; ring < collars; ring++) {
         double radius = scale * (0.34 + ring * 0.18);
         double spin = time * (2.1 + ring * 0.52) * (ring % 2 == 0 ? 1.0 : -1.0);
         Vec3 ringCenter = head.subtract(dir.scale(0.14 + ring * 0.075));
         wrote |= planeRing(
            buffer,
            matrix,
            ringCenter,
            side,
            up,
            radius,
            Math.max(0.008F, scale * 0.005F),
            48 - ring * 3,
            spin,
            255,
            ring < 2 ? 255 : 221,
            ring < 2 ? 238 : 88,
            live * (0.64F - ring * 0.075F),
            8,
            1
         );
      }

      wrote |= restrainedWake(buffer, matrix, start, head, dir, side, up, scale, live, time, seed, detail);
      wrote |= travelMotes(buffer, matrix, head, dir, side, up, scale, live, time, seed, detail);
      float remaining = (float)Math.min(1.0, head.distanceTo(end) / Math.max(1.0, start.distanceTo(end)));
      if (remaining < 0.3F) {
         float pressure = smoother(1.0F - remaining / 0.3F);
         int converge = detail >= 2 ? 18 : 10;

         for (int i = 0; i < converge; i++) {
            float h = hash(seed, 6000 + i);
            double angle = i * (float) (Math.PI * 2) / converge - time * (1.2 + h * 0.6);
            double radius = scale * (2.25 - pressure * 1.2);
            Vec3 outer = end.add(side.scale(Math.cos(angle) * radius)).add(up.scale(Math.sin(angle) * radius));
            wrote |= ribbon(buffer, matrix, outer, end, Math.max(0.008F, scale * 0.0044F), 255, 237, 147, live * pressure * 0.42F);
         }
      }

      return wrote;
   }

   private static boolean restrainedWake(
      BufferBuilder buffer, Matrix4f matrix, Vec3 start, Vec3 head, Vec3 dir, Vec3 side, Vec3 up, float scale, float live, float time, long seed, int detail
   ) {
      double distance = start.distanceTo(head);
      if (distance < 0.4) {
         return false;
      }

      boolean wrote = false;
      int rails = detail >= 3 ? 8 : (detail == 2 ? 6 : 4);

      for (int rail = 0; rail < rails; rail++) {
         float h0 = hash(seed, 4300 + rail);
         float h1 = hash(seed, 4400 + rail);
         double angle = rail * (float) (Math.PI * 2) / rails + time * (0.3 + h0 * 0.18);
         double radius = scale * (0.38 + h1 * 0.2);
         Vec3 offset = side.scale(Math.cos(angle) * radius).add(up.scale(Math.sin(angle) * radius));
         Vec3 wakeStart = head.subtract(dir.scale(Math.min(distance, scale * (3.8 + h0 * 2.0)))).add(offset.scale(0.34));
         Vec3 wakeEnd = head.subtract(dir.scale(scale * 0.3)).add(offset);
         wrote |= ribbon(
            buffer,
            matrix,
            wakeStart,
            wakeEnd,
            Math.max(0.008F, scale * 0.0042F),
            255,
            rail % 2 == 0 ? 214 : 245,
            rail % 2 == 0 ? 61 : 175,
            live * (rail % 2 == 0 ? 0.18F : 0.27F)
         );
      }

      return wrote;
   }

   private static boolean travelMotes(
      BufferBuilder buffer, Matrix4f matrix, Vec3 head, Vec3 dir, Vec3 side, Vec3 up, float scale, float live, float time, long seed, int detail
   ) {
      boolean wrote = false;
      int count = detail >= 3 ? 34 : (detail == 2 ? 22 : 12);

      for (int i = 0; i < count; i++) {
         float h0 = hash(seed, 10000 + i);
         float h1 = hash(seed, 10100 + i);
         float h2 = hash(seed, 10200 + i);
         double angle = i * (float) (Math.PI * 2) / count + time * (0.72 + h1 * 0.82);
         double radius = scale * (0.26 + h0 * 0.94);
         Vec3 p = head.add(side.scale(Math.cos(angle) * radius)).add(up.scale(Math.sin(angle) * radius)).subtract(dir.scale(scale * (0.1 + h2 * 1.48)));
         float blink = pulse01(time * (6.0F + h1 * 6.0F) + i * 0.73F);
         wrote |= goldenMote(buffer, matrix, p, side, up, scale * (0.017F + h2 * 0.043F), live * blink * (0.22F + h2 * 0.38F));
      }

      return wrote;
   }

   private static boolean crossArrivalPhase(
      BufferBuilder buffer, Matrix4f matrix, Vec3 center, Vec3 dir, Vec3 side, Vec3 up, float scale, float age, float time, long seed, int detail
   ) {
      float local = Mth.clamp((age - 154.0F) / 30.0F, 0.0F, 1.0F);
      float appear = smoother(Mth.clamp(local / 0.18F, 0.0F, 1.0F));
      float hold = 1.0F - smoother(Mth.clamp((local - 0.74F) / 0.26F, 0.0F, 1.0F));
      float live = appear * hold;
      if (live <= 0.001F) {
         return false;
      }

      boolean wrote = false;
      float punch = smoother(Mth.clamp(local / 0.34F, 0.0F, 1.0F));
      float compress = smoother(Mth.clamp((local - 0.55F) / 0.45F, 0.0F, 1.0F));
      float breathing = 0.94F + 0.06F * pulse01(time * 4.2F);
      float crossScale = (1.0F + punch * 1.25F) * (1.0F - compress * 0.58F) * breathing;
      Vec3 diagonalA = side.add(up).normalize();
      Vec3 diagonalB = side.subtract(up).normalize();
      double reach = scale * 2.15 * crossScale;
      double width = scale * (0.16 + punch * 0.1) * (1.0 - compress * 0.42);
      wrote |= slashBlade(buffer, matrix, center, diagonalA, dir, reach, width * 1.1, 255, 120, 0, live * 0.26F, 255, 255, 244, live);
      wrote |= slashBlade(buffer, matrix, center.add(dir.scale(0.09)), diagonalB, dir, reach, width, 255, 194, 28, live * 0.34F, 255, 255, 248, live);
      float chroma = live * (0.12F + punch * 0.24F);
      wrote |= slashBlade(
         buffer,
         matrix,
         center.subtract(dir.scale(scale * 0.08)),
         diagonalA,
         dir,
         reach * 1.028,
         width * 0.25,
         255,
         68,
         188,
         chroma * 0.3F,
         255,
         184,
         255,
         chroma * 0.42F
      );
      wrote |= slashBlade(
         buffer,
         matrix,
         center.subtract(dir.scale(scale * 0.12)),
         diagonalB,
         dir,
         reach * 1.035,
         width * 0.23,
         88,
         196,
         255,
         chroma * 0.24F,
         210,
         245,
         255,
         chroma * 0.36F
      );
      wrote |= pvCrossMandala(buffer, matrix, center, dir, side, up, scale, live, punch, compress, time, seed, detail);
      wrote |= pvCrossAirShock(buffer, matrix, center, dir, side, up, scale, live, punch, local, time, detail);
      wrote |= pvCrossCoronaSheets(buffer, matrix, center, dir, side, up, scale, live, punch, compress, time, seed, detail);
      wrote |= pvCrossBloom(buffer, matrix, center, dir, side, up, scale, live, punch, compress, time, detail);
      return wrote | pvCrossSatelliteCuts(buffer, matrix, center, dir, side, up, scale, live, punch, time, seed, detail);
   }

   private static boolean pvCrossMandala(
      BufferBuilder buffer,
      Matrix4f matrix,
      Vec3 center,
      Vec3 dir,
      Vec3 side,
      Vec3 up,
      float scale,
      float live,
      float punch,
      float compress,
      float time,
      long seed,
      int detail
   ) {
      boolean wrote = false;
      int layers = detail >= 3 ? 12 : (detail == 2 ? 9 : 6);

      for (int layer = 0; layer < layers; layer++) {
         float h = hash(seed, 21000 + layer * 71);
         double radius = scale * (0.58 + layer * 0.29) * (0.82 + punch * 0.42) * (1.0 - compress * (0.08 + layer * 0.01));
         double spin = time * (0.72 + layer * 0.18 + h * 0.24) * (layer % 2 == 0 ? 1.0 : -1.0) + layer * 0.43;
         float alpha = live * (0.72F - layer * 0.043F);
         int r = 255;
         int g = layer % 4 == 0 ? 255 : (layer % 4 == 1 ? 218 : (layer % 4 == 2 ? 174 : 132));
         int b = layer % 4 == 0 ? 242 : (layer % 4 == 1 ? 124 : (layer % 4 == 2 ? 56 : 208));
         wrote |= pvWobbleRing(
            buffer,
            matrix,
            center.subtract(dir.scale(scale * (0.13 + layer * 0.035))),
            dir,
            side,
            up,
            radius,
            Math.max(0.01F, scale * (0.0075F - layer * 2.5E-4F)),
            72 - Math.min(24, layer * 2),
            spin,
            0.02 + h * 0.035,
            r,
            g,
            b,
            alpha,
            time,
            seed,
            21100 + layer * 211
         );
      }

      int spokes = detail >= 3 ? 48 : (detail == 2 ? 32 : 20);

      for (int i = 0; i < spokes; i++) {
         float h0 = hash(seed, 22000 + i * 17);
         float h1 = hash(seed, 22100 + i * 23);
         double a = i * (float) (Math.PI * 2) / spokes + time * (0.18 + h0 * 0.2);
         double inner = scale * (0.28 + h1 * 0.1);
         double outer = scale * (2.1 + h0 * 1.8) * (0.76 + punch * 0.34);
         Vec3 p0 = center.add(side.scale(Math.cos(a) * inner)).add(up.scale(Math.sin(a) * inner)).subtract(dir.scale(scale * 0.2));
         Vec3 p1 = center.add(side.scale(Math.cos(a) * outer)).add(up.scale(Math.sin(a) * outer)).subtract(dir.scale(scale * 0.22));
         float alpha = live * (i % 8 == 0 ? 0.64F : 0.2F) * (0.68F + h1 * 0.32F);
         wrote |= pvTaperedRibbon(
            buffer,
            matrix,
            p0,
            p1,
            Math.max(0.008F, scale * (i % 8 == 0 ? 0.0062F : 0.0034F)),
            0.001F,
            255,
            i % 8 == 0 ? 250 : 210,
            i % 8 == 0 ? 204 : 74,
            alpha,
            0.0F
         );
      }

      int satellites = detail >= 3 ? 22 : (detail == 2 ? 14 : 8);

      for (int i = 0; i < satellites; i++) {
         float h0 = hash(seed, 22200 + i * 29);
         float h1 = hash(seed, 22300 + i * 31);
         double a = i * (float) (Math.PI * 2) / satellites - time * (0.76 + h0 * 0.84);
         double radius = scale * (2.35 + h1 * 1.25);
         Vec3 p = center.add(side.scale(Math.cos(a) * radius)).add(up.scale(Math.sin(a) * radius)).add(dir.scale((h0 - 0.5) * scale * 0.4));
         float blink = 0.28F + 0.72F * pulse01(time * (7.0F + h1 * 5.0F) + i * 0.88F);
         int r = pvPrismR(h0 + time * 0.02F);
         int g = pvPrismG(h0 + time * 0.02F);
         int b = pvPrismB(h0 + time * 0.02F);
         wrote |= pvStarMote(buffer, matrix, p, side, up, scale * (0.035F + h1 * 0.065F), r, g, b, live * blink * 0.66F);
      }

      return wrote;
   }

   private static boolean pvCrossAirShock(
      BufferBuilder buffer,
      Matrix4f matrix,
      Vec3 center,
      Vec3 dir,
      Vec3 side,
      Vec3 up,
      float scale,
      float live,
      float punch,
      float local,
      float time,
      int detail
   ) {
      boolean wrote = false;
      Vec3 worldUp = new Vec3(0.0, 1.0, 0.0);
      Vec3 worldSide = safeSide(worldUp);
      int waves = detail >= 3 ? 10 : (detail == 2 ? 7 : 5);

      for (int i = 0; i < waves; i++) {
         float delay = i * 0.055F;
         float q = smoother(Mth.clamp((local - delay) / 0.64F, 0.0F, 1.0F));
         if (!(q <= 0.001F)) {
            double radius = scale * (0.75 + q * (2.4 + i * 0.85));
            float alpha = live * (0.36F - i * 0.026F) * (1.0F - q * 0.68F);
            wrote |= planeRing(
               buffer,
               matrix,
               center.add(worldUp.scale(scale * (0.02 + i * 0.035))),
               worldSide,
               safeSide(worldSide),
               radius,
               Math.max(0.01F, scale * 0.0048F),
               72,
               time * (0.08 + i * 0.02),
               235,
               246,
               255,
               alpha,
               0,
               0
            );
         }
      }

      int lens = detail >= 3 ? 6 : 4;

      for (int i = 0; i < lens; i++) {
         float q = smoother(Mth.clamp((local - 0.1F - i * 0.07F) / 0.52F, 0.0F, 1.0F));
         if (!(q <= 0.001F)) {
            double radius = scale * (1.0 + q * (1.5 + i * 0.6));
            Vec3 c = center.subtract(dir.scale(scale * (0.15 + i * 0.12)));
            wrote |= planeRing(
               buffer,
               matrix,
               c,
               side,
               up,
               radius,
               Math.max(0.008F, scale * 0.0035F),
               80,
               -time * (0.18 + i * 0.03),
               255,
               242,
               202,
               live * q * (0.22F - i * 0.02F),
               10,
               1
            );
         }
      }

      return wrote;
   }

   private static boolean pvCrossCoronaSheets(
      BufferBuilder buffer,
      Matrix4f matrix,
      Vec3 center,
      Vec3 dir,
      Vec3 side,
      Vec3 up,
      float scale,
      float live,
      float punch,
      float compress,
      float time,
      long seed,
      int detail
   ) {
      boolean wrote = false;
      int sheets = detail >= 3 ? 36 : (detail == 2 ? 24 : 14);

      for (int i = 0; i < sheets; i++) {
         float h0 = hash(seed, 23000 + i * 41);
         float h1 = hash(seed, 23100 + i * 43);
         float h2 = hash(seed, 23200 + i * 47);
         double a = h0 * (float) (Math.PI * 2) + time * (0.08 + h1 * 0.08);
         Vec3 radial = side.scale(Math.cos(a)).add(up.scale(Math.sin(a))).normalize();
         Vec3 tangent = side.scale(-Math.sin(a)).add(up.scale(Math.cos(a))).normalize();
         double r0 = scale * (0.4 + h1 * 0.22);
         double r1 = scale * (2.2 + h2 * 2.6) * (0.75 + punch * 0.36);
         Vec3 p0 = center.add(radial.scale(r0)).subtract(dir.scale(scale * 0.1));
         Vec3 p1 = center.add(radial.scale((r0 + r1) * 0.52)).add(tangent.scale(scale * (h0 - 0.5) * 1.1)).add(dir.scale(scale * (0.1 + h1 * 0.2)));
         Vec3 p2 = center.add(radial.scale(r1)).add(tangent.scale(scale * (h2 - 0.5) * 1.6)).add(dir.scale(scale * (0.25 + h0 * 0.35)));
         int r = 255;
         int g = i % 5 == 0 ? 250 : 176 + (int)(h1 * 62.0F);
         int b = i % 5 == 0 ? 224 : 44 + (int)(h2 * 142.0F);
         wrote |= pvBezierRibbon(
            buffer, matrix, p0, p1, p2, Math.max(0.008F, scale * (0.0038F + h2 * 0.003F)), r, g, b, live * (0.18F + h1 * 0.3F) * (1.0F - compress * 0.55F)
         );
      }

      return wrote;
   }

   private static boolean pvCrossBloom(
      BufferBuilder buffer,
      Matrix4f matrix,
      Vec3 center,
      Vec3 dir,
      Vec3 side,
      Vec3 up,
      float scale,
      float live,
      float punch,
      float compress,
      float time,
      int detail
   ) {
      boolean wrote = false;
      float bloom = live * (0.5F + punch * 0.5F) * (1.0F - compress * 0.42F);
      wrote |= pvSoftDisk(
         buffer,
         matrix,
         center.add(dir.scale(0.08)),
         side,
         up,
         scale * (0.95 + punch * 1.2),
         255,
         255,
         244,
         bloom * 0.28F,
         detail >= 3 ? 7 : 5,
         detail >= 3 ? 48 : 32
      );
      wrote |= pvSoftDisk(
         buffer, matrix, center, side, up, scale * (0.48 + punch * 0.62), 255, 196, 52, bloom * 0.34F, detail >= 3 ? 6 : 4, detail >= 3 ? 44 : 28
      );
      wrote |= crossFlare(buffer, matrix, center.add(dir.scale(0.14)), side, up, scale * (0.82F + punch * 1.1F), 255, 255, 249, bloom);
      return wrote | crossFlare(buffer, matrix, center.subtract(dir.scale(0.05)), side, up, scale * (0.48F + punch * 0.66F), 255, 210, 84, bloom * 0.76F);
   }

   private static boolean pvCrossSatelliteCuts(
      BufferBuilder buffer, Matrix4f matrix, Vec3 center, Vec3 dir, Vec3 side, Vec3 up, float scale, float live, float punch, float time, long seed, int detail
   ) {
      boolean wrote = false;
      int cuts = detail >= 3 ? 44 : (detail == 2 ? 28 : 16);

      for (int i = 0; i < cuts; i++) {
         float h0 = hash(seed, 24000 + i * 17);
         float h1 = hash(seed, 24100 + i * 19);
         double a = h0 * (float) (Math.PI * 2) + time * (0.22 + h1 * 0.1);
         double r = scale * (1.4 + h1 * 2.9) * (0.7 + punch * 0.4);
         Vec3 p = center.add(side.scale(Math.cos(a) * r)).add(up.scale(Math.sin(a) * r)).add(dir.scale((h0 - 0.5) * scale * 1.2));
         Vec3 tangent = side.scale(-Math.sin(a)).add(up.scale(Math.cos(a))).normalize();
         Vec3 p0 = p.subtract(tangent.scale(scale * (0.08 + h1 * 0.16)));
         Vec3 p1 = p.add(tangent.scale(scale * (0.18 + h0 * 0.36)));
         wrote |= pvTaperedRibbon(
            buffer,
            matrix,
            p0,
            p1,
            Math.max(0.006F, scale * 0.004F),
            0.001F,
            255,
            224 + (int)(h1 * 28.0F),
            116 + (int)(h0 * 116.0F),
            live * (0.24F + h1 * 0.46F),
            0.0F
         );
      }

      return wrote;
   }

   private static boolean orbPhase(
      BufferBuilder buffer, Matrix4f matrix, Vec3 center, Vec3 dir, Vec3 side, Vec3 up, float scale, float age, float time, long seed, int detail
   ) {
      float phase = Mth.clamp((age - 170.0F) / 50.0F, 0.0F, 1.0F);
      float appear = smoother(Mth.clamp(phase / 0.18F, 0.0F, 1.0F));
      float rupture = smoother(Mth.clamp((age - 206.0F) / 14.0F, 0.0F, 1.0F));
      float live = appear * (1.0F - rupture * 0.34F);
      if (live <= 0.001F) {
         return false;
      }

      boolean wrote = false;
      float crossFold = 1.0F - smoother(Mth.clamp((phase - 0.02F) / 0.44F, 0.0F, 1.0F));
      float orbGrow = smoother(Mth.clamp((phase - 0.05F) / 0.44F, 0.0F, 1.0F));
      float charge = smoother(Mth.clamp((phase - 0.3F) / 0.58F, 0.0F, 1.0F));
      float ejection = smoother(Mth.clamp((age - 202.0F) / 18.0F, 0.0F, 1.0F));
      Vec3 orbCenter = center.add(0.0, scale * 0.08, 0.0);
      if (crossFold > 0.001F) {
         Vec3 diagonalA = side.add(up).normalize();
         Vec3 diagonalB = side.subtract(up).normalize();
         double reach = scale * (3.15 - orbGrow * 2.15);
         double width = scale * (0.18 - orbGrow * 0.07);
         wrote |= slashBlade(buffer, matrix, orbCenter, diagonalA, dir, reach, width, 255, 152, 8, live * crossFold * 0.24F, 255, 255, 244, live * crossFold);
         wrote |= slashBlade(
            buffer,
            matrix,
            orbCenter.add(dir.scale(0.06)),
            diagonalB,
            dir,
            reach,
            width * 0.92,
            255,
            205,
            58,
            live * crossFold * 0.3F,
            255,
            255,
            249,
            live * crossFold
         );
      }

      wrote |= pvContainmentDome(buffer, matrix, orbCenter, dir, side, up, scale, live, orbGrow, charge, rupture, time, seed, detail);
      wrote |= pvOrbVolume(buffer, matrix, orbCenter, dir, side, up, scale, live, orbGrow, charge, rupture, time, detail);
      wrote |= pvOrbHaloSystem(buffer, matrix, orbCenter, dir, side, up, scale, live, orbGrow, charge, time, seed, detail);
      wrote |= pvOrbMoteField(buffer, matrix, orbCenter, dir, side, up, scale, live, orbGrow, charge, ejection, time, seed, detail);
      wrote |= pvOrbConvergence(buffer, matrix, orbCenter, dir, side, up, scale, live, charge, ejection, time, seed, detail);
      wrote |= pvDomeLightning(buffer, matrix, orbCenter, dir, side, up, scale, live, charge, rupture, time, seed, detail);
      return wrote | pvBottomRupture(buffer, matrix, orbCenter, dir, side, up, scale, live, rupture, time, seed, detail);
   }

   private static boolean pvContainmentDome(
      BufferBuilder buffer,
      Matrix4f matrix,
      Vec3 center,
      Vec3 dir,
      Vec3 side,
      Vec3 up,
      float scale,
      float live,
      float orbGrow,
      float charge,
      float rupture,
      float time,
      long seed,
      int detail
   ) {
      boolean wrote = false;
      double domeRadius = scale * (4.1 + orbGrow * 1.95 + 0.2 * pulse01(time * 1.9F));
      float domeLive = live * orbGrow * (1.1F - rupture * 0.6F);
      if (domeLive <= 0.001F) {
         return false;
      }

      int latitudes = detail >= 3 ? 17 : (detail == 2 ? 12 : 8);

      for (int i = 0; i < latitudes; i++) {
         double v = (i + 1.0) / (latitudes + 1.0);
         double phi = -1.5079644737231006 + v * Math.PI * 0.96;
         double rr = Math.cos(phi) * domeRadius;
         double y = Math.sin(phi) * domeRadius * 0.82;
         float edge = (float)Math.max(0.0, Math.cos(phi));
         int r = i % 5 == 0 ? 198 : 255;
         int g = i % 5 == 0 ? 124 : 210;
         int b = i % 5 == 0 ? 255 : 96;
         wrote |= pvWobbleRing(
            buffer,
            matrix,
            center.add(0.0, y, 0.0),
            new Vec3(0.0, 1.0, 0.0),
            side,
            dir,
            Math.max(0.01, rr),
            Math.max(0.007F, scale * 0.0038F),
            detail >= 3 ? 72 : 48,
            time * (0.1 + i * 0.012) * (i % 2 == 0 ? 1.0 : -1.0),
            0.012 + i * 0.0018,
            r,
            g,
            b,
            domeLive * edge * (0.12F + (i % 4 == 0 ? 0.1F : 0.0F)),
            time,
            seed,
            30000 + i * 97
         );
      }

      int meridians = detail >= 3 ? 16 : (detail == 2 ? 11 : 7);

      for (int i = 0; i < meridians; i++) {
         double a = i * (float) (Math.PI * 2) / meridians + time * 0.035;
         Vec3 axisA = side.scale(Math.cos(a)).add(dir.scale(Math.sin(a))).normalize();
         Vec3 axisB = new Vec3(0.0, 1.0, 0.0);
         int r = i % 3 == 0 ? 126 : 255;
         int g = i % 3 == 0 ? 201 : 229;
         int b = i % 3 == 0 ? 255 : 149;
         wrote |= pvWobbleRing(
            buffer,
            matrix,
            center,
            axisA.cross(axisB).normalize(),
            axisA,
            axisB,
            domeRadius,
            Math.max(0.007F, scale * 0.0033F),
            detail >= 3 ? 72 : 48,
            time * (0.06 + i * 0.012),
            0.016,
            r,
            g,
            b,
            domeLive * 0.12F,
            time,
            seed,
            31000 + i * 109
         );
      }

      wrote |= pvSoftDisk(buffer, matrix, center, side, dir, domeRadius * 0.94, 118, 54, 255, domeLive * 0.03F, detail >= 3 ? 6 : 4, detail >= 3 ? 64 : 40);
      wrote |= pvSoftDisk(
         buffer,
         matrix,
         center.add(0.0, domeRadius * 0.1, 0.0),
         side,
         dir,
         domeRadius * 0.72,
         255,
         118,
         225,
         domeLive * 0.028F,
         detail >= 3 ? 5 : 3,
         detail >= 3 ? 56 : 36
      );
      int airBands = detail >= 3 ? 11 : 7;

      for (int i = 0; i < airBands; i++) {
         double radius = domeRadius * (0.86 + i * 0.09);
         float alpha = domeLive * (0.15F - i * 0.014F);
         wrote |= planeRing(
            buffer,
            matrix,
            center.add(0.0, scale * (0.02 + i * 0.09), 0.0),
            side,
            dir,
            radius,
            Math.max(0.009F, scale * 0.004F),
            84,
            -time * (0.13 + i * 0.02),
            236,
            246,
            255,
            alpha,
            0,
            0
         );
      }

      return wrote;
   }

   private static boolean pvOrbVolume(
      BufferBuilder buffer,
      Matrix4f matrix,
      Vec3 center,
      Vec3 dir,
      Vec3 side,
      Vec3 up,
      float scale,
      float live,
      float orbGrow,
      float charge,
      float rupture,
      float time,
      int detail
   ) {
      boolean wrote = false;
      float pulse = 0.9F + 0.16F * pulse01(time * (5.0F + charge * 4.2F));
      double radius = scale * (0.62 + orbGrow * 1.62 + charge * 0.46) * pulse * (1.0 - rupture * 0.1);
      float alpha = live * orbGrow * (1.1F + charge * 0.28F);
      wrote |= pvSoftOrb(buffer, matrix, center, dir, side, up, radius * 2.1, 255, 132, 0, alpha * 0.095F, detail);
      wrote |= pvSoftOrb(buffer, matrix, center.add(dir.scale(0.03)), dir, side, up, radius * 1.62, 255, 186, 48, alpha * 0.12F, detail);
      wrote |= pvSoftOrb(buffer, matrix, center.add(dir.scale(0.06)), dir, side, up, radius * 1.28, 255, 224, 110, alpha * 0.17F, detail);
      wrote |= pvSoftOrb(buffer, matrix, center.add(dir.scale(0.1)), dir, side, up, radius * 0.94, 255, 246, 192, alpha * 0.26F, detail);
      wrote |= pvSoftOrb(buffer, matrix, center.add(dir.scale(0.15)), dir, side, up, radius * 0.66, 255, 255, 248, alpha * 0.42F, detail);
      wrote |= pvSoftOrb(buffer, matrix, center.add(dir.scale(0.19)), dir, side, up, radius * 0.38, 255, 255, 255, alpha * 0.6F, detail);
      wrote |= crossFlare(buffer, matrix, center.add(dir.scale(0.2)), side, up, (float)(radius * 0.72), 255, 255, 254, alpha * (0.76F + charge * 0.34F));
      wrote |= crossFlare(buffer, matrix, center, side, up, (float)(radius * 0.44), 255, 198, 42, alpha * 0.96F);
      return wrote
         | crossFlare(buffer, matrix, center.add(up.scale(radius * 0.1)), side, dir, (float)(radius * 0.56), 255, 234, 148, alpha * (0.4F + charge * 0.3F));
   }

   private static boolean pvOrbHaloSystem(
      BufferBuilder buffer,
      Matrix4f matrix,
      Vec3 center,
      Vec3 dir,
      Vec3 side,
      Vec3 up,
      float scale,
      float live,
      float orbGrow,
      float charge,
      float time,
      long seed,
      int detail
   ) {
      boolean wrote = false;
      int layers = detail >= 3 ? 19 : (detail == 2 ? 13 : 9);

      for (int i = 0; i < layers; i++) {
         float h = hash(seed, 33000 + i * 31);
         double radius = scale * (0.78 + i * 0.23) * (0.86 + orbGrow * 0.36);
         double spin = time * (0.72 + charge * (1.5 + i * 0.12) + h * 0.2) * (i % 2 == 0 ? 1.0 : -1.0);
         int r = 255;
         int g = i % 5 == 0 ? 255 : (i % 5 == 1 ? 227 : (i % 5 == 2 ? 188 : (i % 5 == 3 ? 132 : 200)));
         int b = i % 5 == 0 ? 242 : (i % 5 == 1 ? 135 : (i % 5 == 2 ? 62 : (i % 5 == 3 ? 244 : 255)));
         float alpha = live * orbGrow * (0.48F - i * 0.025F) * (0.72F + charge * 0.28F);
         wrote |= pvWobbleRing(
            buffer,
            matrix,
            center.add(dir.scale((h - 0.5) * scale * 0.18)),
            dir,
            side,
            up,
            radius,
            Math.max(0.008F, scale * (0.0055F - i * 1.8E-4F)),
            72 - Math.min(20, i),
            spin,
            0.014 + h * 0.024,
            r,
            g,
            b,
            alpha,
            time,
            seed,
            33100 + i * 113
         );
         if (i < 7) {
            wrote |= pvWobbleRing(
               buffer,
               matrix,
               center,
               side,
               dir,
               up,
               radius * (0.74 + i * 0.025),
               Math.max(0.007F, scale * 0.0038F),
               60,
               -spin * 0.66,
               0.012,
               255,
               218,
               126,
               alpha * 0.32F,
               time,
               seed,
               33200 + i * 127
            );
         }
      }

      return wrote;
   }

   private static boolean pvOrbMoteField(
      BufferBuilder buffer,
      Matrix4f matrix,
      Vec3 center,
      Vec3 dir,
      Vec3 side,
      Vec3 up,
      float scale,
      float live,
      float orbGrow,
      float charge,
      float ejection,
      float time,
      long seed,
      int detail
   ) {
      boolean wrote = false;
      int count = detail >= 3 ? 240 : (detail == 2 ? 156 : 92);
      double shell = scale * (2.35 + orbGrow * 2.6);

      for (int i = 0; i < count; i++) {
         float h0 = hash(seed, 34000 + i * 17);
         float h1 = hash(seed, 34100 + i * 19);
         float h2 = hash(seed, 34200 + i * 23);
         float h3 = hash(seed, 34300 + i * 29);
         double yaw = h0 * (float) (Math.PI * 2) + time * (0.16 + h3 * 0.42) * (i % 2 == 0 ? 1.0 : -1.0);
         double pitch = (h1 - 0.5) * Math.PI * 0.86;
         Vec3 radial = side.scale(Math.cos(yaw) * Math.cos(pitch)).add(up.scale(Math.sin(pitch))).add(dir.scale(Math.sin(yaw) * Math.cos(pitch)));
         if (radial.lengthSqr() < 1.0E-8) {
            radial = side;
         }

         radial = radial.normalize();
         double orbitRadius = shell * (0.26 + Math.sqrt(h2) * 0.78);
         double hover = scale * 0.1 * Math.sin(time * (0.8 + h3) + i * 0.7);
         Vec3 orbitPos = center.add(radial.scale(orbitRadius + hover));
         double burstDistance = scale * (2.0 + h3 * 10.0) * ejection;
         Vec3 pos = orbitPos.add(radial.scale(burstDistance));
         if (ejection > 0.001F) {
            Vec3 tangent = side.scale(-Math.sin(yaw)).add(dir.scale(Math.cos(yaw))).normalize();
            pos = pos.add(tangent.scale(Math.sin(ejection * Math.PI) * scale * (h0 - 0.5) * 1.6));
         }

         float blink = 0.22F + 0.78F * pulse01(time * (5.8F + h1 * 8.0F) + i * 0.73F);
         float size = scale * (0.02F + h2 * 0.06F + (i % 29 == 0 ? 0.065F : 0.0F));
         int r = i % 7 == 0 ? pvPrismR(h0 + time * 0.014F) : 255;
         int g = i % 7 == 0 ? pvPrismG(h0 + time * 0.014F) : 212 + (int)(h1 * 43.0F);
         int b = i % 7 == 0 ? pvPrismB(h0 + time * 0.014F) : 88 + (int)(h0 * 156.0F);
         float alpha = live * orbGrow * blink * (0.24F + charge * 0.48F);
         wrote |= pvStarMote(buffer, matrix, pos, side, up, size, r, g, b, alpha);
         if (ejection > 0.08F && h3 > 0.22F) {
            Vec3 tail = pos.subtract(radial.scale(scale * (0.28 + h2 * 1.05) * (0.4 + ejection)));
            wrote |= pvTaperedRibbon(buffer, matrix, tail, pos, Math.max(0.004F, size * 0.34F), 0.001F, r, g, b, alpha * 0.58F, 0.0F);
         }
      }

      return wrote;
   }

   private static boolean pvOrbConvergence(
      BufferBuilder buffer,
      Matrix4f matrix,
      Vec3 center,
      Vec3 dir,
      Vec3 side,
      Vec3 up,
      float scale,
      float live,
      float charge,
      float ejection,
      float time,
      long seed,
      int detail
   ) {
      boolean wrote = false;
      float inhale = charge * (1.0F - ejection);
      if (inhale <= 0.001F) {
         return false;
      }

      int streams = detail >= 3 ? 46 : (detail == 2 ? 30 : 18);

      for (int i = 0; i < streams; i++) {
         float h0 = hash(seed, 35000 + i * 31);
         float h1 = hash(seed, 35100 + i * 37);
         double a = h0 * (float) (Math.PI * 2) + time * (0.22 + h1 * 0.22);
         Vec3 radial = side.scale(Math.cos(a)).add(up.scale(Math.sin(a))).normalize();
         Vec3 tangent = side.scale(-Math.sin(a)).add(up.scale(Math.cos(a))).normalize();
         double outer = scale * (3.0 + h1 * 3.2);
         Vec3 p0 = center.add(radial.scale(outer)).add(dir.scale((h0 - 0.5) * scale * 1.0));
         Vec3 p1 = center.add(radial.scale(outer * 0.46)).add(tangent.scale(scale * (h1 - 0.5) * 1.5));
         Vec3 p2 = center.add(radial.scale(scale * (0.22 + h0 * 0.16)));
         wrote |= pvBezierRibbon(
            buffer,
            matrix,
            p0,
            p1,
            p2,
            Math.max(0.006F, scale * (0.0032F + h1 * 0.0025F)),
            255,
            213 + (int)(h0 * 35.0F),
            82 + (int)(h1 * 135.0F),
            live * inhale * (0.18F + h0 * 0.42F)
         );
      }

      return wrote;
   }

   private static boolean pvDomeLightning(
      BufferBuilder buffer,
      Matrix4f matrix,
      Vec3 center,
      Vec3 dir,
      Vec3 side,
      Vec3 up,
      float scale,
      float live,
      float charge,
      float rupture,
      float time,
      long seed,
      int detail
   ) {
      float lightningLive = live * smoother(Mth.clamp((charge - 0.18F) / 0.72F, 0.0F, 1.0F));
      if (lightningLive <= 0.001F) {
         return false;
      }

      boolean wrote = false;
      int arcs = detail >= 3 ? 24 : (detail == 2 ? 15 : 9);
      double radius = scale * (4.0 + charge * 1.5);

      for (int i = 0; i < arcs; i++) {
         float h0 = hash(seed, 36000 + i * 43);
         float h1 = hash(seed, 36100 + i * 47);
         float h2 = hash(seed, 36200 + i * 53);
         double startA = h0 * (float) (Math.PI * 2) + time * 0.055;
         double span = 0.36 + h1 * 0.72;
         double pitch = (h2 - 0.5) * 1.0;
         Vec3 last = null;
         int segments = detail >= 3 ? 9 : 6;

         for (int s = 0; s <= segments; s++) {
            double u = (double)s / segments;
            double a = startA + span * u * (i % 2 == 0 ? 1.0 : -1.0);
            double jitter = (hash(seed, 36300 + i * 131 + s * 17) - 0.5) * scale * 0.24;
            Vec3 radial = side.scale(Math.cos(a) * Math.cos(pitch)).add(up.scale(Math.sin(pitch))).add(dir.scale(Math.sin(a) * Math.cos(pitch))).normalize();
            Vec3 tangent = side.scale(-Math.sin(a)).add(dir.scale(Math.cos(a))).normalize();
            Vec3 p = center.add(radial.scale(radius + jitter)).add(tangent.scale(jitter * 0.5));
            if (last != null) {
               float alpha = lightningLive * (0.22F + 0.78F * pulse01(time * (11.0F + h0 * 7.0F) + i));
               wrote |= pvTaperedRibbon(
                  buffer,
                  matrix,
                  last,
                  p,
                  Math.max(0.007F, scale * 0.0044F),
                  Math.max(0.003F, scale * 0.0018F),
                  255,
                  250,
                  220,
                  alpha * (1.0F - rupture * 0.25F),
                  alpha * 0.36F
               );
            }

            last = p;
         }
      }

      return wrote;
   }

   private static boolean pvBottomRupture(
      BufferBuilder buffer,
      Matrix4f matrix,
      Vec3 center,
      Vec3 dir,
      Vec3 side,
      Vec3 up,
      float scale,
      float live,
      float rupture,
      float time,
      long seed,
      int detail
   ) {
      if (rupture <= 0.001F) {
         return false;
      }

      boolean wrote = false;
      Vec3 worldUp = new Vec3(0.0, 1.0, 0.0);
      Vec3 groundCenter = center.add(0.0, -scale * 0.62, 0.0);
      float burst = smoother(Mth.clamp(rupture / 0.72F, 0.0F, 1.0F));
      wrote |= pvSoftDisk(
         buffer,
         matrix,
         groundCenter,
         side,
         dir,
         scale * (1.6 + burst * 6.2),
         255,
         161,
         0,
         live * (1.0F - rupture * 0.34F) * 0.24F,
         detail >= 3 ? 9 : 6,
         detail >= 3 ? 88 : 56
      );
      wrote |= pvSoftDisk(
         buffer,
         matrix,
         groundCenter.add(0.0, 0.04, 0.0),
         side,
         dir,
         scale * (1.0 + burst * 3.8),
         255,
         221,
         110,
         live * 0.2F,
         detail >= 3 ? 8 : 5,
         detail >= 3 ? 72 : 44
      );
      wrote |= pvSoftDisk(
         buffer,
         matrix,
         groundCenter.add(0.0, 0.08, 0.0),
         side,
         dir,
         scale * (0.72 + burst * 2.5),
         255,
         255,
         244,
         live * 0.26F,
         detail >= 3 ? 7 : 4,
         detail >= 3 ? 64 : 40
      );
      int shards = detail >= 3 ? 92 : (detail == 2 ? 58 : 34);

      for (int i = 0; i < shards; i++) {
         float h0 = hash(seed, 37000 + i * 17);
         float h1 = hash(seed, 37100 + i * 19);
         float h2 = hash(seed, 37200 + i * 23);
         double a = h0 * (float) (Math.PI * 2);
         Vec3 radial = side.scale(Math.cos(a)).add(dir.scale(Math.sin(a))).normalize();
         double startR = scale * (1.5 + h1 * 2.3);
         double travel = scale * (2.0 + h2 * 8.0) * rupture;
         Vec3 p0 = groundCenter.add(radial.scale(startR)).add(0.0, scale * (0.12 + h2 * 0.3), 0.0);
         Vec3 p1 = p0.add(radial.scale(travel * 0.48)).add(0.0, scale * (0.65 + h1 * 1.4) * rupture, 0.0);
         Vec3 p2 = p0.add(radial.scale(travel)).add(0.0, scale * (1.1 + h2 * 3.8) * rupture, 0.0);
         int r = i % 8 == 0 ? pvPrismR(h0 + time * 0.02F) : 255;
         int g = i % 8 == 0 ? pvPrismG(h0 + time * 0.02F) : 182 + (int)(h1 * 68.0F);
         int b = i % 8 == 0 ? pvPrismB(h0 + time * 0.02F) : 38 + (int)(h2 * 170.0F);
         wrote |= pvBezierRibbon(buffer, matrix, p0, p1, p2, Math.max(0.007F, scale * (0.0036F + h2 * 0.004F)), r, g, b, live * rupture * (0.25F + h1 * 0.48F));
      }

      int rings = detail >= 3 ? 12 : 7;

      for (int i = 0; i < rings; i++) {
         float q = smoother(Mth.clamp((rupture - i * 0.07F) / 0.6F, 0.0F, 1.0F));
         if (!(q <= 0.001F)) {
            double radius = scale * (0.9 + q * (2.2 + i * 0.95));
            wrote |= planeRing(
               buffer,
               matrix,
               groundCenter.add(worldUp.scale(scale * i * 0.08)),
               side,
               dir,
               radius,
               Math.max(0.01F, scale * 0.0055F),
               72,
               time * (0.12 + i * 0.03),
               242,
               250,
               255,
               live * (0.42F - i * 0.035F) * (1.0F - q * 0.58F),
               0,
               0
            );
         }
      }

      return wrote;
   }

   private static boolean pillarPhase(
      BufferBuilder buffer, Matrix4f matrix, Vec3 center, Vec3 dir, Vec3 side, Vec3 up, float scale, float age, float time, long seed, int detail
   ) {
      float phase = Mth.clamp((age - 220.0F) / 114.0F, 0.0F, 1.0F);
      float ignition = smoother(Mth.clamp((age - 220.0F) / 8.0F, 0.0F, 1.0F));
      float expansion = smoother(Mth.clamp((age - 220.0F) / 34.0F, 0.0F, 1.0F));
      float sustain = 1.0F - smoother(Mth.clamp((age - 272.0F) / 62.0F, 0.0F, 1.0F));
      float afterglow = smoother(Mth.clamp((age - 272.0F) / 20.0F, 0.0F, 1.0F)) * sustain;
      float live = ignition * sustain;
      if (live <= 0.001F) {
         return false;
      }

      boolean wrote = false;
      Vec3 base = center.add(0.0, -scale * 0.62, 0.0);
      Vec3 worldUp = new Vec3(0.0, 1.0, 0.0);
      Vec3 worldSide = safeSide(worldUp);
      Vec3 worldForward = worldUp.cross(worldSide).normalize();
      double beamRadius = scale * (0.52 + expansion * 2.65) * (0.96 + 0.04 * pulse01(time * 3.7F));
      double height = scale * (5.0 + expansion * 28.0);
      wrote |= pvPillarCore(buffer, matrix, base, worldUp, worldSide, worldForward, beamRadius, height, scale, live, expansion, time, seed, detail);
      wrote |= pvPillarMantle(buffer, matrix, base, worldUp, worldSide, worldForward, beamRadius, height, scale, live, expansion, time, seed, detail);
      wrote |= pvPillarHelix(buffer, matrix, base, worldUp, worldSide, worldForward, beamRadius, height, scale, live, expansion, time, seed, detail);
      wrote |= pvPillarCollars(buffer, matrix, base, worldUp, worldSide, worldForward, beamRadius, height, scale, live, expansion, time, seed, detail);
      wrote |= pvPillarShockwaves(buffer, matrix, base, worldUp, worldSide, worldForward, beamRadius, scale, live, phase, expansion, time, seed, detail);
      wrote |= pvPillarWindSheets(buffer, matrix, base, worldUp, worldSide, worldForward, beamRadius, height, scale, live, expansion, time, seed, detail);
      wrote |= pvPillarMoteEruption(buffer, matrix, base, worldUp, worldSide, worldForward, beamRadius, height, scale, live, expansion, time, seed, detail);
      wrote |= pvWorldReaction(buffer, matrix, base, worldUp, worldSide, worldForward, beamRadius, height, scale, live, phase, expansion, time, seed, detail);
      wrote |= pvPillarLightning(buffer, matrix, base, worldUp, worldSide, worldForward, beamRadius, height, scale, live, expansion, time, seed, detail);
      wrote |= pvPillarDomeRemnants(buffer, matrix, base, worldUp, worldSide, worldForward, beamRadius, scale, live, expansion, time, seed, detail);
      wrote |= pvPillarBloom(buffer, matrix, base, worldUp, worldSide, worldForward, beamRadius, scale, live, ignition, expansion, detail);
      return wrote | pvPillarAfterglow(buffer, matrix, base, worldUp, worldSide, worldForward, beamRadius, height, scale, afterglow, time, seed, detail);
   }

   private static boolean pvPillarCore(
      BufferBuilder buffer,
      Matrix4f matrix,
      Vec3 base,
      Vec3 vertical,
      Vec3 side,
      Vec3 forward,
      double beamRadius,
      double height,
      float scale,
      float live,
      float expansion,
      float time,
      long seed,
      int detail
   ) {
      boolean wrote = false;
      int slices = detail >= 3 ? 48 : (detail == 2 ? 34 : 24);
      int heightSegments = detail >= 3 ? 18 : (detail == 2 ? 13 : 9);
      wrote |= pvNoisyCylinder(
         buffer,
         matrix,
         base,
         vertical,
         side,
         forward,
         beamRadius * 0.3,
         height,
         slices,
         heightSegments,
         time,
         seed,
         40000,
         0.05 + expansion * 0.08,
         255,
         255,
         254,
         live * 0.88F
      );
      wrote |= pvNoisyCylinder(
         buffer,
         matrix,
         base.add(vertical.scale(scale * 0.04)),
         vertical,
         side,
         forward,
         beamRadius * 0.56,
         height * 0.995,
         slices,
         heightSegments,
         time + 3.1F,
         seed,
         41000,
         0.08 + expansion * 0.12,
         255,
         238,
         196,
         live * 0.58F
      );
      wrote |= pvNoisyCylinder(
         buffer,
         matrix,
         base.add(vertical.scale(scale * 0.08)),
         vertical,
         side,
         forward,
         beamRadius * 0.9,
         height * 0.98,
         slices,
         heightSegments,
         time + 7.7F,
         seed,
         42000,
         0.12 + expansion * 0.18,
         255,
         143,
         18,
         live * 0.2F
      );
      int corePlanes = detail >= 3 ? 6 : 4;

      for (int i = 0; i < corePlanes; i++) {
         double a = i * Math.PI / corePlanes + time * 0.15;
         Vec3 axis = side.scale(Math.cos(a)).add(forward.scale(Math.sin(a))).normalize();
         float alpha = live * (0.46F - i * 0.045F);
         Vec3 p0 = base.subtract(axis.scale(beamRadius * 0.34));
         Vec3 p1 = base.add(vertical.scale(height)).add(axis.scale(beamRadius * 0.34));
         wrote |= pvTaperedRibbon(buffer, matrix, p0, p1, (float)(beamRadius * 0.36), (float)(beamRadius * 0.08), 255, 255, 250, alpha, alpha * 0.32F);
      }

      return wrote;
   }

   private static boolean pvPillarMantle(
      BufferBuilder buffer,
      Matrix4f matrix,
      Vec3 base,
      Vec3 vertical,
      Vec3 side,
      Vec3 forward,
      double beamRadius,
      double height,
      float scale,
      float live,
      float expansion,
      float time,
      long seed,
      int detail
   ) {
      boolean wrote = false;
      int streams = detail >= 3 ? 34 : (detail == 2 ? 22 : 14);
      int segments = detail >= 3 ? 18 : 12;

      for (int i = 0; i < streams; i++) {
         float h0 = hash(seed, 43000 + i * 31);
         float h1 = hash(seed, 43100 + i * 37);
         float h2 = hash(seed, 43200 + i * 41);
         Vec3 last = null;

         for (int s = 0; s <= segments; s++) {
            double u = (double)s / segments;
            double angle = i * (float) (Math.PI * 2) / streams + time * (0.34 + h0 * 0.48) + u * (2.5 + h1 * 5.0) * (i % 2 == 0 ? 1.0 : -1.0);
            double taper = 1.0 - u * 0.42;
            double radius = beamRadius * (0.86 + h1 * 0.88) * taper + Math.sin(time * 1.4 + u * 18.0 + i) * scale * 0.18;
            Vec3 p = base.add(vertical.scale(height * u)).add(side.scale(Math.cos(angle) * radius)).add(forward.scale(Math.sin(angle) * radius));
            if (last != null) {
               int r = 255;
               int g = i % 9 == 0 ? pvPrismG(h0 + (float)u) : 118 + (int)(h1 * 118.0F);
               int b = i % 9 == 0 ? pvPrismB(h0 + (float)u) : 18 + (int)(h2 * 88.0F);
               float alpha = live * (0.12F + h2 * 0.3F) * (float)(0.55 + taper * 0.45);
               wrote |= pvTaperedRibbon(
                  buffer, matrix, last, p, Math.max(0.008F, scale * (0.0045F + h1 * 0.006F)), Math.max(0.003F, scale * 0.0015F), r, g, b, alpha, alpha * 0.2F
               );
            }

            last = p;
         }
      }

      return wrote;
   }

   private static boolean pvPillarHelix(
      BufferBuilder buffer,
      Matrix4f matrix,
      Vec3 base,
      Vec3 vertical,
      Vec3 side,
      Vec3 forward,
      double beamRadius,
      double height,
      float scale,
      float live,
      float expansion,
      float time,
      long seed,
      int detail
   ) {
      boolean wrote = false;
      int helices = detail >= 3 ? 12 : (detail == 2 ? 8 : 5);
      int segments = detail >= 3 ? 42 : (detail == 2 ? 28 : 18);

      for (int h = 0; h < helices; h++) {
         float variant = hash(seed, 44000 + h * 71);
         Vec3 last = null;

         for (int s = 0; s <= segments; s++) {
            double u = (double)s / segments;
            double a = h * (float) (Math.PI * 2) / helices
               + time * (0.78 + variant * 0.6)
               + u * (float) (Math.PI * 2) * (2.4 + variant * 2.8) * (h % 2 == 0 ? 1.0 : -1.0);
            double radius = beamRadius * (1.22 + variant * 0.55) * (0.45 + u * 0.7);
            Vec3 p = base.add(vertical.scale(height * u)).add(side.scale(Math.cos(a) * radius)).add(forward.scale(Math.sin(a) * radius));
            if (last != null) {
               int r = h % 4 == 0 ? 208 : 255;
               int g = h % 4 == 0 ? 178 : 206;
               int b = h % 4 == 0 ? 255 : 78;
               float alpha = live * (0.2F + variant * 0.3F) * (float)(1.0 - u * 0.22);
               wrote |= pvTaperedRibbon(
                  buffer, matrix, last, p, Math.max(0.007F, scale * 0.0046F), Math.max(0.003F, scale * 0.002F), r, g, b, alpha, alpha * 0.2F
               );
            }

            last = p;
         }
      }

      return wrote;
   }

   private static boolean pvPillarCollars(
      BufferBuilder buffer,
      Matrix4f matrix,
      Vec3 base,
      Vec3 vertical,
      Vec3 side,
      Vec3 forward,
      double beamRadius,
      double height,
      float scale,
      float live,
      float expansion,
      float time,
      long seed,
      int detail
   ) {
      boolean wrote = false;
      int collars = detail >= 3 ? 19 : (detail == 2 ? 13 : 8);

      for (int i = 0; i < collars; i++) {
         float h = hash(seed, 45000 + i * 43);
         double u = (i + 0.6) / collars;
         double radius = beamRadius * (1.35 + h * 2.1) * (0.78 + 0.28 * Math.sin(time * 0.7 + i));
         Vec3 c = base.add(vertical.scale(height * u));
         double spin = time * (0.55 + i * 0.07 + h * 0.22) * (i % 2 == 0 ? 1.0 : -1.0);
         int r = i % 5 == 0 ? 245 : 255;
         int g = i % 5 == 0 ? 252 : 216;
         int b = i % 5 == 0 ? 255 : 102;
         float alpha = live * (0.38F - i * 0.009F) * (0.56F + expansion * 0.44F);
         wrote |= pvWobbleRing(
            buffer,
            matrix,
            c,
            vertical,
            side,
            forward,
            radius,
            Math.max(0.01F, scale * (0.0055F + h * 0.0025F)),
            detail >= 3 ? 84 : 56,
            spin,
            0.018 + h * 0.03,
            r,
            g,
            b,
            alpha,
            time,
            seed,
            45100 + i * 137
         );
      }

      return wrote;
   }

   private static boolean pvPillarShockwaves(
      BufferBuilder buffer,
      Matrix4f matrix,
      Vec3 base,
      Vec3 vertical,
      Vec3 side,
      Vec3 forward,
      double beamRadius,
      float scale,
      float live,
      float phase,
      float expansion,
      float time,
      long seed,
      int detail
   ) {
      boolean wrote = false;
      int waves = detail >= 3 ? 22 : (detail == 2 ? 14 : 8);
      float blastPhase = Mth.clamp((phase - 0.01F) / 0.46F, 0.0F, 1.0F);

      for (int i = 0; i < waves; i++) {
         float delay = i * 0.035F;
         float q = smoother(Mth.clamp((blastPhase - delay) / 0.54F, 0.0F, 1.0F));
         if (!(q <= 0.001F)) {
            double radius = scale * (1.4 + q * (4.8 + i * 2.0));
            float alpha = live * (0.58F - i * 0.026F) * (1.0F - q * 0.7F);
            wrote |= planeRing(
               buffer,
               matrix,
               base.add(vertical.scale(scale * (0.04 + i * 0.035))),
               side,
               forward,
               radius,
               Math.max(0.01F, scale * 0.0058F),
               detail >= 3 ? 96 : 64,
               time * (0.1 + i * 0.018),
               238,
               247,
               255,
               alpha,
               0,
               0
            );
            if (i % 3 == 0) {
               wrote |= planeRing(
                  buffer,
                  matrix,
                  base.add(vertical.scale(scale * (0.06 + i * 0.038))),
                  side,
                  forward,
                  radius * 0.86,
                  Math.max(0.008F, scale * 0.004F),
                  detail >= 3 ? 88 : 56,
                  -time * (0.14 + i * 0.016),
                  255,
                  188,
                  54,
                  alpha * 0.54F,
                  11,
                  1
               );
            }
         }
      }

      int rays = detail >= 3 ? 120 : (detail == 2 ? 76 : 44);

      for (int i = 0; i < rays; i++) {
         float h0 = hash(seed, 46000 + i * 17);
         float h1 = hash(seed, 46100 + i * 19);
         double a = h0 * (float) (Math.PI * 2);
         Vec3 radial = side.scale(Math.cos(a)).add(forward.scale(Math.sin(a))).normalize();
         double inner = beamRadius * (0.2 + h1 * 0.38);
         double outer = scale * (4.0 + h1 * 12.0) * expansion;
         Vec3 p0 = base.add(radial.scale(inner)).add(vertical.scale(scale * 0.08));
         Vec3 p1 = base.add(radial.scale(outer)).add(vertical.scale(scale * (0.16 + h0 * 1.8) * expansion));
         int r = 255;
         int g = i % 10 == 0 ? pvPrismG(h0 + time * 0.01F) : 173 + (int)(h1 * 76.0F);
         int b = i % 10 == 0 ? pvPrismB(h0 + time * 0.01F) : 26 + (int)(h0 * 136.0F);
         wrote |= pvTaperedRibbon(buffer, matrix, p0, p1, Math.max(0.007F, scale * (0.004F + h1 * 0.0038F)), 0.001F, r, g, b, live * (0.2F + h1 * 0.42F), 0.0F);
      }

      return wrote;
   }

   private static boolean pvPillarWindSheets(
      BufferBuilder buffer,
      Matrix4f matrix,
      Vec3 base,
      Vec3 vertical,
      Vec3 side,
      Vec3 forward,
      double beamRadius,
      double height,
      float scale,
      float live,
      float expansion,
      float time,
      long seed,
      int detail
   ) {
      boolean wrote = false;
      int sheets = detail >= 3 ? 46 : (detail == 2 ? 28 : 16);

      for (int i = 0; i < sheets; i++) {
         float h0 = hash(seed, 47000 + i * 31);
         float h1 = hash(seed, 47100 + i * 37);
         double a = h0 * (float) (Math.PI * 2) + time * (0.08 + h1 * 0.1);
         Vec3 radial = side.scale(Math.cos(a)).add(forward.scale(Math.sin(a))).normalize();
         Vec3 tangent = side.scale(-Math.sin(a)).add(forward.scale(Math.cos(a))).normalize();
         double y = height * (0.06 + h1 * 0.46);
         Vec3 c = base.add(vertical.scale(y));
         double r0 = beamRadius * (0.95 + h0 * 0.55);
         double r2 = beamRadius * (4.6 + h1 * 7.8) * (0.84 + expansion * 0.7);
         Vec3 p0 = c.add(radial.scale(r0));
         Vec3 p1 = c.add(radial.scale((r0 + r2) * 0.52)).add(tangent.scale(scale * (h0 - 0.5) * 2.5)).add(vertical.scale(scale * (0.5 + h1 * 2.0)));
         Vec3 p2 = c.add(radial.scale(r2)).add(tangent.scale(scale * (h1 - 0.5) * 3.2)).add(vertical.scale(scale * (0.8 + h0 * 4.0)));
         wrote |= pvBezierRibbon(buffer, matrix, p0, p1, p2, Math.max(0.009F, scale * (0.004F + h1 * 0.005F)), 232, 246, 255, live * (0.12F + h0 * 0.22F));
      }

      return wrote;
   }

   private static boolean pvPillarMoteEruption(
      BufferBuilder buffer,
      Matrix4f matrix,
      Vec3 base,
      Vec3 vertical,
      Vec3 side,
      Vec3 forward,
      double beamRadius,
      double height,
      float scale,
      float live,
      float expansion,
      float time,
      long seed,
      int detail
   ) {
      boolean wrote = false;
      int motes = detail >= 3 ? 420 : (detail == 2 ? 260 : 144);

      for (int i = 0; i < motes; i++) {
         float h0 = hash(seed, 48000 + i * 17);
         float h1 = hash(seed, 48100 + i * 19);
         float h2 = hash(seed, 48200 + i * 23);
         float h3 = hash(seed, 48300 + i * 29);
         double yaw = h0 * (float) (Math.PI * 2);
         Vec3 radial = side.scale(Math.cos(yaw)).add(forward.scale(Math.sin(yaw))).normalize();
         double outward = scale * (0.9 + h1 * 12.0) * expansion;
         double lift = height * (0.02 + h2 * 0.52) * expansion + scale * h3 * 2.0;
         Vec3 pos = base.add(radial.scale(outward)).add(vertical.scale(lift));
         float blink = 0.18F + 0.82F * pulse01(time * (6.0F + h2 * 9.0F) + i * 0.61F);
         float size = scale * (0.018F + h3 * 0.07F + (i % 41 == 0 ? 0.08F : 0.0F));
         int r = i % 6 == 0 ? pvPrismR(h0 + time * 0.016F) : 255;
         int g = i % 6 == 0 ? pvPrismG(h0 + time * 0.016F) : 206 + (int)(h1 * 49.0F);
         int b = i % 6 == 0 ? pvPrismB(h0 + time * 0.016F) : 78 + (int)(h2 * 168.0F);
         float alpha = live * blink * (0.24F + h3 * 0.56F);
         wrote |= pvStarMote(buffer, matrix, pos, side, vertical, size, r, g, b, alpha);
         if (h3 > 0.36F) {
            Vec3 tail = pos.subtract(radial.scale(scale * (0.35 + h2 * 1.5))).subtract(vertical.scale(scale * (0.18 + h1 * 0.8)));
            wrote |= pvTaperedRibbon(buffer, matrix, tail, pos, Math.max(0.004F, size * 0.28F), 0.001F, r, g, b, alpha * 0.46F, 0.0F);
         }
      }

      int ascenders = detail >= 3 ? 190 : (detail == 2 ? 118 : 64);

      for (int i = 0; i < ascenders; i++) {
         float h0 = hash(seed, 49000 + i * 31);
         float h1 = hash(seed, 49100 + i * 37);
         double a = h0 * (float) (Math.PI * 2) + time * (0.18 + h1 * 0.18);
         double radius = beamRadius * (1.0 + h1 * 3.8);
         double cycle = (time * (0.16 + h0 * 0.14) + h1 * 17.0) % 1.0;
         Vec3 pos = base.add(side.scale(Math.cos(a) * radius)).add(forward.scale(Math.sin(a) * radius)).add(vertical.scale(height * cycle * 0.78));
         float alpha = live * (1.0F - (float)cycle) * (0.18F + h1 * 0.34F);
         wrote |= pvStarMote(buffer, matrix, pos, side, vertical, scale * (0.015F + h0 * 0.04F), 255, 224, 136, alpha);
      }

      return wrote;
   }

   private static boolean pvPillarLightning(
      BufferBuilder buffer,
      Matrix4f matrix,
      Vec3 base,
      Vec3 vertical,
      Vec3 side,
      Vec3 forward,
      double beamRadius,
      double height,
      float scale,
      float live,
      float expansion,
      float time,
      long seed,
      int detail
   ) {
      boolean wrote = false;
      int bolts = detail >= 3 ? 42 : (detail == 2 ? 26 : 14);

      for (int i = 0; i < bolts; i++) {
         float h0 = hash(seed, 50000 + i * 43);
         float h1 = hash(seed, 50100 + i * 47);
         double a = h0 * (float) (Math.PI * 2) + time * 0.2;
         Vec3 radial = side.scale(Math.cos(a)).add(forward.scale(Math.sin(a))).normalize();
         Vec3 tangent = side.scale(-Math.sin(a)).add(forward.scale(Math.cos(a))).normalize();
         Vec3 last = null;
         int segments = detail >= 3 ? 11 : 7;

         for (int s = 0; s <= segments; s++) {
            double u = (double)s / segments;
            double jitter = (hash(seed, 50200 + i * 173 + s * 23) - 0.5) * beamRadius * 0.45;
            double radius = beamRadius * (1.0 + h1 * 0.85) + jitter;
            Vec3 p = base.add(vertical.scale(height * u * (0.35 + h0 * 0.55))).add(radial.scale(radius)).add(tangent.scale(jitter * 0.35));
            if (last != null) {
               float blink = 0.2F + 0.8F * pulse01(time * (10.0F + h0 * 7.0F) + i);
               wrote |= pvTaperedRibbon(
                  buffer,
                  matrix,
                  last,
                  p,
                  Math.max(0.006F, scale * 0.004F),
                  Math.max(0.002F, scale * 0.0016F),
                  255,
                  252,
                  232,
                  live * blink * 0.42F,
                  live * blink * 0.12F
               );
            }

            last = p;
         }
      }

      return wrote;
   }

   private static boolean pvPillarDomeRemnants(
      BufferBuilder buffer,
      Matrix4f matrix,
      Vec3 base,
      Vec3 vertical,
      Vec3 side,
      Vec3 forward,
      double beamRadius,
      float scale,
      float live,
      float expansion,
      float time,
      long seed,
      int detail
   ) {
      boolean wrote = false;
      double radius = scale * (5.2 + expansion * 9.4);
      int shells = detail >= 3 ? 12 : (detail == 2 ? 8 : 5);

      for (int i = 0; i < shells; i++) {
         double r = radius * (0.74 + i * 0.11);
         float alpha = live * (0.16F - i * 0.014F) * (1.0F - expansion * 0.24F);
         wrote |= pvWobbleRing(
            buffer,
            matrix,
            base.add(vertical.scale(scale * (0.6 + i * 0.32))),
            vertical,
            side,
            forward,
            r,
            Math.max(0.008F, scale * 0.0036F),
            detail >= 3 ? 84 : 56,
            time * (0.08 + i * 0.02),
            0.018,
            i % 3 == 0 ? 160 : 245,
            i % 3 == 0 ? 180 : 232,
            255,
            alpha,
            time,
            seed,
            51000 + i * 107
         );
         Vec3 axisA = side.scale(Math.cos(i * 0.7)).add(forward.scale(Math.sin(i * 0.7))).normalize();
         wrote |= pvWobbleRing(
            buffer,
            matrix,
            base.add(vertical.scale(scale * (1.8 + i * 0.26))),
            axisA.cross(vertical).normalize(),
            axisA,
            vertical,
            r * 0.88,
            Math.max(0.007F, scale * 0.0031F),
            detail >= 3 ? 72 : 48,
            -time * (0.06 + i * 0.018),
            0.014,
            255,
            208,
            118,
            alpha * 0.78F,
            time,
            seed,
            51100 + i * 127
         );
      }

      return wrote;
   }

   private static boolean pvPillarBloom(
      BufferBuilder buffer,
      Matrix4f matrix,
      Vec3 base,
      Vec3 vertical,
      Vec3 side,
      Vec3 forward,
      double beamRadius,
      float scale,
      float live,
      float ignition,
      float expansion,
      int detail
   ) {
      boolean wrote = false;
      float flash = 1.0F - smoother(Mth.clamp((ignition - 0.32F) / 0.68F, 0.0F, 1.0F));
      double radius = scale * (1.8 + expansion * 7.8);
      wrote |= pvSoftDisk(
         buffer,
         matrix,
         base.add(vertical.scale(scale * 0.18)),
         side,
         forward,
         radius * 1.18,
         255,
         145,
         0,
         live * (0.14F + flash * 0.28F),
         detail >= 3 ? 9 : 6,
         detail >= 3 ? 84 : 56
      );
      wrote |= pvSoftDisk(
         buffer,
         matrix,
         base.add(vertical.scale(scale * 0.35)),
         side,
         forward,
         radius * 0.86,
         255,
         218,
         86,
         live * (0.14F + flash * 0.22F),
         detail >= 3 ? 8 : 5,
         detail >= 3 ? 76 : 48
      );
      wrote |= pvSoftDisk(
         buffer,
         matrix,
         base.add(vertical.scale(scale * 0.48)),
         side,
         forward,
         radius * 0.58,
         255,
         255,
         238,
         live * (0.16F + flash * 0.4F),
         detail >= 3 ? 7 : 5,
         detail >= 3 ? 64 : 40
      );
      wrote |= crossFlare(
         buffer, matrix, base.add(vertical.scale(scale * 0.58)), side, vertical, scale * (1.3F + flash * 6.0F), 255, 255, 252, live * (0.42F + flash * 0.72F)
      );
      return wrote
         | crossFlare(
            buffer,
            matrix,
            base.add(vertical.scale(scale * 0.82)),
            forward,
            vertical,
            scale * (0.9F + flash * 4.0F),
            255,
            191,
            42,
            live * (0.34F + flash * 0.64F)
         );
   }

   private static boolean pvWorldReaction(
      BufferBuilder buffer,
      Matrix4f matrix,
      Vec3 base,
      Vec3 vertical,
      Vec3 side,
      Vec3 forward,
      double beamRadius,
      double height,
      float scale,
      float live,
      float phase,
      float expansion,
      float time,
      long seed,
      int detail
   ) {
      boolean wrote = false;
      float worldPhase = smoother(Mth.clamp((phase - 0.02F) / 0.6F, 0.0F, 1.0F));
      if (worldPhase <= 0.001F) {
         return false;
      }

      int horizonBands = detail >= 3 ? 14 : (detail == 2 ? 9 : 5);

      for (int i = 0; i < horizonBands; i++) {
         double radius = scale * (8.0 + worldPhase * (8.0 + i * 4.6));
         float alpha = live * (0.18F - i * 0.01F) * (1.0F - worldPhase * 0.58F);
         wrote |= planeRing(
            buffer,
            matrix,
            base.add(vertical.scale(scale * (0.05 + i * 0.08))),
            side,
            forward,
            radius,
            Math.max(0.012F, scale * 0.006F),
            detail >= 3 ? 112 : 72,
            time * (0.08 + i * 0.015),
            240,
            248,
            255,
            alpha,
            0,
            0
         );
      }

      int gusts = detail >= 3 ? 56 : (detail == 2 ? 34 : 18);

      for (int i = 0; i < gusts; i++) {
         float h0 = hash(seed, 54000 + i * 19);
         float h1 = hash(seed, 54100 + i * 23);
         float h2 = hash(seed, 54200 + i * 29);
         double a = h0 * (float) (Math.PI * 2) + time * (0.04 + h1 * 0.08);
         Vec3 radial = side.scale(Math.cos(a)).add(forward.scale(Math.sin(a))).normalize();
         Vec3 tangent = side.scale(-Math.sin(a)).add(forward.scale(Math.cos(a))).normalize();
         double inner = beamRadius * (1.0 + h1 * 1.1);
         double outer = scale * (10.0 + h2 * 30.0) * (0.6 + expansion * 0.8);
         Vec3 p0 = base.add(radial.scale(inner)).add(vertical.scale(scale * (0.1 + h1 * 0.4)));
         Vec3 p1 = base.add(radial.scale((inner + outer) * 0.48)).add(tangent.scale(scale * (h0 - 0.5) * 8.0)).add(vertical.scale(scale * (0.9 + h2 * 2.0)));
         Vec3 p2 = base.add(radial.scale(outer)).add(tangent.scale(scale * (h1 - 0.5) * 12.0)).add(vertical.scale(scale * (1.5 + h0 * 5.0)));
         wrote |= pvBezierRibbon(
            buffer, matrix, p0, p1, p2, Math.max(0.01F, scale * (0.005F + h2 * 0.006F)), 232, 244, 255, live * worldPhase * (0.08F + h0 * 0.18F)
         );
      }

      int starbursts = detail >= 3 ? 240 : (detail == 2 ? 150 : 84);

      for (int i = 0; i < starbursts; i++) {
         float h0 = hash(seed, 54300 + i * 17);
         float h1 = hash(seed, 54400 + i * 19);
         float h2 = hash(seed, 54500 + i * 23);
         double a = h0 * (float) (Math.PI * 2);
         Vec3 radial = side.scale(Math.cos(a)).add(forward.scale(Math.sin(a))).normalize();
         double dist = scale * (4.0 + h1 * 36.0) * worldPhase;
         double lift = scale * (0.2 + h2 * 10.0) * worldPhase;
         Vec3 pos = base.add(radial.scale(dist)).add(vertical.scale(lift));
         float blink = 0.2F + 0.8F * pulse01(time * (7.0F + h2 * 8.0F) + i * 0.37F);
         int r = i % 7 == 0 ? pvPrismR(h0 + time * 0.02F) : 255;
         int g = i % 7 == 0 ? pvPrismG(h0 + time * 0.02F) : 214 + (int)(h1 * 40.0F);
         int b = i % 7 == 0 ? pvPrismB(h0 + time * 0.02F) : 96 + (int)(h2 * 148.0F);
         float size = scale * (0.018F + h2 * 0.08F);
         float alpha = live * worldPhase * blink * (0.14F + h1 * 0.3F);
         wrote |= pvStarMote(buffer, matrix, pos, side, vertical, size, r, g, b, alpha);
         if (h2 > 0.4F) {
            Vec3 tail = pos.subtract(radial.scale(scale * (0.4 + h1 * 2.6))).subtract(vertical.scale(scale * (0.18 + h2 * 0.9)));
            wrote |= pvTaperedRibbon(buffer, matrix, tail, pos, Math.max(0.004F, size * 0.28F), 0.001F, r, g, b, alpha * 0.42F, 0.0F);
         }
      }

      return wrote;
   }

   private static boolean pvPillarAfterglow(
      BufferBuilder buffer,
      Matrix4f matrix,
      Vec3 base,
      Vec3 vertical,
      Vec3 side,
      Vec3 forward,
      double beamRadius,
      double height,
      float scale,
      float live,
      float time,
      long seed,
      int detail
   ) {
      if (live <= 0.001F) {
         return false;
      }

      boolean wrote = false;
      wrote |= pvNoisyCylinder(
         buffer,
         matrix,
         base,
         vertical,
         side,
         forward,
         beamRadius * 0.48,
         height * 0.92,
         detail >= 3 ? 32 : 22,
         detail >= 3 ? 12 : 8,
         time,
         seed,
         52000,
         0.05,
         255,
         219,
         128,
         live * 0.13F
      );
      int embers = detail >= 3 ? 90 : (detail == 2 ? 58 : 32);

      for (int i = 0; i < embers; i++) {
         float h0 = hash(seed, 53000 + i * 17);
         float h1 = hash(seed, 53100 + i * 19);
         double a = h0 * (float) (Math.PI * 2) + time * 0.1;
         double radius = beamRadius * (0.8 + h1 * 4.2);
         double cycle = (time * (0.08 + h0 * 0.1) + h1 * 11.0) % 1.0;
         Vec3 pos = base.add(side.scale(Math.cos(a) * radius)).add(forward.scale(Math.sin(a) * radius)).add(vertical.scale(height * cycle * 0.6));
         wrote |= pvStarMote(buffer, matrix, pos, side, vertical, scale * (0.012F + h1 * 0.032F), 255, 217, 118, live * (1.0F - (float)cycle) * 0.42F);
      }

      return wrote;
   }

   private static boolean pvSoftOrb(
      BufferBuilder buffer, Matrix4f matrix, Vec3 center, Vec3 normal, Vec3 side, Vec3 up, double radius, int r, int g, int b, float alpha, int detail
   ) {
      if (!(alpha <= 0.001F) && !(radius <= 0.01)) {
         boolean wrote = false;
         int slices = detail >= 3 ? 9 : (detail == 2 ? 7 : 5);
         int bands = detail >= 3 ? 6 : 4;
         int segments = detail >= 3 ? 44 : (detail == 2 ? 34 : 26);

         for (int i = 0; i < slices; i++) {
            double k = (double)i / Math.max(1, slices - 1);
            double off = (k - 0.5) * radius * 1.7;
            double normalized = off / (radius * 0.85);
            if (!(Math.abs(normalized) >= 1.0)) {
               double rr = radius * Math.sqrt(Math.max(0.0, 1.0 - normalized * normalized));
               float local = alpha * (float)(0.08 + 0.18 * (1.0 - Math.abs(k - 0.5) * 2.0));
               wrote |= pvSoftDisk(buffer, matrix, center.add(normal.scale(off)), side, up, rr, r, g, b, local, bands, segments);
            }
         }

         int crossSlices = detail >= 3 ? 5 : 3;

         for (int i = 0; i < crossSlices; i++) {
            double k = (double)i / Math.max(1, crossSlices - 1);
            double off = (k - 0.5) * radius * 1.34;
            double normalized = off / (radius * 0.72);
            if (!(Math.abs(normalized) >= 1.0)) {
               double rr = radius * 0.86 * Math.sqrt(Math.max(0.0, 1.0 - normalized * normalized));
               wrote |= pvSoftDisk(
                  buffer, matrix, center.add(side.scale(off)), normal, up, rr, r, g, b, alpha * 0.045F, Math.max(3, bands - 1), Math.max(22, segments - 10)
               );
            }
         }

         return wrote;
      } else {
         return false;
      }
   }

   private static boolean pvSoftDisk(
      BufferBuilder buffer, Matrix4f matrix, Vec3 center, Vec3 axisA, Vec3 axisB, double radius, int r, int g, int b, float alpha, int bands, int segments
   ) {
      if (!(alpha <= 0.001F) && !(radius <= 0.001) && bands > 0 && segments >= 3) {
         boolean wrote = false;

         for (int band = 0; band < bands; band++) {
            double t0 = (double)band / bands;
            double t1 = (band + 1.0) / bands;
            double inner = radius * t0;
            double outer = radius * t1;
            float bandAlpha = alpha * (float)Math.pow(1.0 - t0, 1.65) * (0.54F + 0.46F * (1.0F - (float)t1));

            for (int i = 0; i < segments; i++) {
               double a0 = i * (float) (Math.PI * 2) / segments;
               double a1 = (i + 1.0) * (float) (Math.PI * 2) / segments;
               Vec3 d0 = axisA.scale(Math.cos(a0)).add(axisB.scale(Math.sin(a0)));
               Vec3 d1 = axisA.scale(Math.cos(a1)).add(axisB.scale(Math.sin(a1)));
               Vec3 p0 = center.add(d0.scale(inner));
               Vec3 p1 = center.add(d1.scale(inner));
               Vec3 p2 = center.add(d1.scale(outer));
               Vec3 p3 = center.add(d0.scale(outer));
               quad(buffer, matrix, p0, p1, p2, p3, r, g, b, bandAlpha);
               wrote = true;
            }
         }

         return wrote;
      } else {
         return false;
      }
   }

   private static boolean pvWobbleRing(
      BufferBuilder buffer,
      Matrix4f matrix,
      Vec3 center,
      Vec3 normal,
      Vec3 side,
      Vec3 up,
      double radius,
      float width,
      int segments,
      double spin,
      double wobble,
      int r,
      int g,
      int b,
      float alpha,
      float time,
      long seed,
      int salt
   ) {
      if (!(alpha <= 0.001F) && !(radius <= 0.001) && !(width <= 0.001F) && segments >= 3) {
         boolean wrote = false;
         Vec3 last = null;

         for (int i = 0; i <= segments; i++) {
            int s = i % segments;
            float h = hash(seed, salt + s * 17);
            double a = s * (float) (Math.PI * 2) / segments + spin;
            double localRadius = radius * (1.0 + Math.sin(time * (0.32 + h * 0.24) + s * 0.72) * wobble);
            Vec3 p = center.add(side.scale(Math.cos(a) * localRadius)).add(up.scale(Math.sin(a) * localRadius)).add(normal.scale((h - 0.5) * width * 2.8));
            if (last != null) {
               wrote |= ribbon(buffer, matrix, last, p, width, r, g, b, alpha * (0.72F + h * 0.28F));
            }

            last = p;
         }

         return wrote;
      } else {
         return false;
      }
   }

   private static boolean pvNoisyCylinder(
      BufferBuilder buffer,
      Matrix4f matrix,
      Vec3 base,
      Vec3 axis,
      Vec3 side,
      Vec3 forward,
      double radius,
      double height,
      int slices,
      int heightSegments,
      float time,
      long seed,
      int salt,
      double noise,
      int r,
      int g,
      int b,
      float alpha
   ) {
      if (!(alpha <= 0.001F) && !(radius <= 0.001) && !(height <= 0.001)) {
         boolean wrote = false;

         for (int y = 0; y < heightSegments; y++) {
            double v0 = (double)y / heightSegments;
            double v1 = (y + 1.0) / heightSegments;
            float verticalFade0 = (float)(0.58 + 0.42 * Math.sin(Math.PI * Math.min(1.0, v0 * 1.04)));
            float verticalFade1 = (float)(0.58 + 0.42 * Math.sin(Math.PI * Math.min(1.0, v1 * 1.04)));

            for (int i = 0; i < slices; i++) {
               float h0 = hash(seed, salt + y * 1009 + i * 17);
               float h1 = hash(seed, salt + y * 1013 + i * 19 + 1);
               double a0 = i * (float) (Math.PI * 2) / slices + time * 0.12;
               double a1 = (i + 1.0) * (float) (Math.PI * 2) / slices + time * 0.12;
               double n0 = 1.0 + Math.sin(time * (1.0 + h0 * 1.4) + i * 0.77 + y * 0.41) * noise;
               double n1 = 1.0 + Math.sin(time * (1.0 + h1 * 1.4) + (i + 1) * 0.77 + y * 0.41) * noise;
               Vec3 o00 = side.scale(Math.cos(a0) * radius * n0).add(forward.scale(Math.sin(a0) * radius * n0));
               Vec3 o01 = side.scale(Math.cos(a1) * radius * n1).add(forward.scale(Math.sin(a1) * radius * n1));
               Vec3 p0 = base.add(axis.scale(height * v0)).add(o00);
               Vec3 p1 = base.add(axis.scale(height * v0)).add(o01);
               Vec3 p2 = base.add(axis.scale(height * v1)).add(o01);
               Vec3 p3 = base.add(axis.scale(height * v1)).add(o00);
               float local = alpha * (0.72F + h0 * 0.28F) * (verticalFade0 + verticalFade1) * 0.5F;
               quad(buffer, matrix, p0, p1, p2, p3, r, g, b, local);
               wrote = true;
            }
         }

         return wrote;
      } else {
         return false;
      }
   }

   private static boolean pvBezierRibbon(BufferBuilder buffer, Matrix4f matrix, Vec3 a, Vec3 b, Vec3 c, float width, int r, int g, int bl, float alpha) {
      if (!(alpha <= 0.001F) && !(width <= 0.001F)) {
         boolean wrote = false;
         Vec3 last = a;
         int segments = 10;

         for (int i = 1; i <= segments; i++) {
            double t = (double)i / segments;
            double inv = 1.0 - t;
            Vec3 p = a.scale(inv * inv).add(b.scale(2.0 * inv * t)).add(c.scale(t * t));
            float envelope = (float)Math.sin(t * Math.PI);
            float localWidth = width * (0.35F + envelope * 0.92F);
            float localAlpha = alpha * (0.18F + envelope * 0.82F);
            wrote |= pvTaperedRibbon(buffer, matrix, last, p, localWidth, localWidth * 0.7F, r, g, bl, localAlpha, localAlpha * 0.65F);
            last = p;
         }

         return wrote;
      } else {
         return false;
      }
   }

   private static boolean pvTaperedRibbon(
      BufferBuilder buffer, Matrix4f matrix, Vec3 start, Vec3 end, float startWidth, float endWidth, int r, int g, int b, float startAlpha, float endAlpha
   ) {
      if ((!(startAlpha <= 0.001F) || !(endAlpha <= 0.001F)) && !(start.distanceToSqr(end) <= 1.0E-8)) {
         Vec3 direction = end.subtract(start).normalize();
         Vec3 side = safeSide(direction);
         Vec3 s0 = side.scale(startWidth);
         Vec3 s1 = side.scale(endWidth);
         int a0 = Mth.clamp((int)(startAlpha * 255.0F), 0, 255);
         int a1 = Mth.clamp((int)(endAlpha * 255.0F), 0, 255);
         if (a0 <= 0 && a1 <= 0) {
            return false;
         }

         buffer.addVertex(matrix, (float)(start.x - s0.x), (float)(start.y - s0.y), (float)(start.z - s0.z))
            .setColor(Mth.clamp(r, 0, 255), Mth.clamp(g, 0, 255), Mth.clamp(b, 0, 255), a0);
         buffer.addVertex(matrix, (float)(start.x + s0.x), (float)(start.y + s0.y), (float)(start.z + s0.z))
            .setColor(Mth.clamp(r, 0, 255), Mth.clamp(g, 0, 255), Mth.clamp(b, 0, 255), a0);
         buffer.addVertex(matrix, (float)(end.x + s1.x), (float)(end.y + s1.y), (float)(end.z + s1.z))
            .setColor(Mth.clamp(r, 0, 255), Mth.clamp(g, 0, 255), Mth.clamp(b, 0, 255), a1);
         buffer.addVertex(matrix, (float)(end.x - s1.x), (float)(end.y - s1.y), (float)(end.z - s1.z))
            .setColor(Mth.clamp(r, 0, 255), Mth.clamp(g, 0, 255), Mth.clamp(b, 0, 255), a1);
         return true;
      } else {
         return false;
      }
   }

   private static boolean pvStarMote(BufferBuilder buffer, Matrix4f matrix, Vec3 center, Vec3 side, Vec3 up, float size, int r, int g, int b, float alpha) {
      if (!(alpha <= 0.001F) && !(size <= 0.001F)) {
         boolean wrote = false;
         wrote |= crossFlare(buffer, matrix, center, side, up, size, r, g, b, alpha);
         return wrote | crossFlare(buffer, matrix, center.add(side.scale(size * 0.04)), side, up, size * 0.42F, 255, 255, 248, alpha * 0.42F);
      } else {
         return false;
      }
   }

   private static int pvPrismR(float hue) {
      return Mth.clamp((int)(238.0F + 17.0F * Mth.sin(hue * (float) (Math.PI * 2) + 0.15F)), 0, 255);
   }

   private static int pvPrismG(float hue) {
      return Mth.clamp((int)(178.0F + 68.0F * Mth.sin(hue * (float) (Math.PI * 2) + 1.85F)), 0, 255);
   }

   private static int pvPrismB(float hue) {
      return Mth.clamp((int)(118.0F + 118.0F * Mth.sin(hue * (float) (Math.PI * 2) + 3.95F)), 0, 255);
   }

   private static boolean hitBurst(
      BufferBuilder buffer,
      Matrix4f matrix,
      Vec3 center,
      Vec3 dir,
      Vec3 side,
      Vec3 up,
      float scale,
      float age,
      float durationTicks,
      float time,
      long seed,
      int detail
   ) {
      float born = smoother(Mth.clamp(age / 3.0F, 0.0F, 1.0F));
      float fade = 1.0F - smoother(Mth.clamp((age - durationTicks + 6.0F) / 6.0F, 0.0F, 1.0F));
      float live = born * fade;
      if (live <= 0.001F) {
         return false;
      }

      boolean wrote = false;
      float shock = smoother(Mth.clamp(age / 9.0F, 0.0F, 1.0F));
      Vec3 diagonalA = side.add(up).normalize();
      Vec3 diagonalB = side.subtract(up).normalize();
      double reach = scale * (0.56 + shock * 1.25);
      wrote |= slashBlade(buffer, matrix, center, diagonalA, dir, reach, scale * 0.09, 255, 158, 0, live * 0.22F, 255, 255, 240, live);
      wrote |= slashBlade(buffer, matrix, center.add(dir.scale(0.05)), diagonalB, dir, reach, scale * 0.08, 255, 207, 58, live * 0.25F, 255, 255, 247, live);
      int rings = detail >= 2 ? 6 : 4;

      for (int ring = 0; ring < rings; ring++) {
         float q = smoother(Mth.clamp(shock * 1.28F - ring * 0.1F, 0.0F, 1.0F));
         if (!(q <= 0.001F)) {
            double radius = scale * (0.16 + q * (0.34 + ring * 0.2));
            float alpha = live * (0.58F - ring * 0.07F) * (1.0F - q * 0.34F);
            wrote |= planeRing(
               buffer,
               matrix,
               center,
               side,
               up,
               radius,
               Math.max(0.007F, scale * 0.0046F),
               48 - ring * 3,
               time * (1.0 + ring * 0.28) * (ring % 2 == 0 ? 1.0 : -1.0),
               255,
               ring < 2 ? 255 : 228,
               ring < 2 ? 241 : 113,
               alpha,
               8,
               1
            );
         }
      }

      int motes = detail >= 3 ? 20 : 12;

      for (int i = 0; i < motes; i++) {
         float h0 = hash(seed, 27000 + i);
         float h1 = hash(seed, 27100 + i);
         double angle = i * (float) (Math.PI * 2) / motes + time * (1.1 + h0 * 0.8);
         double radius = scale * (0.18 + shock * (0.3 + h1 * 0.82));
         Vec3 p = center.add(side.scale(Math.cos(angle) * radius)).add(up.scale(Math.sin(angle) * radius)).add(dir.scale((h0 - 0.5) * scale * 0.28));
         wrote |= goldenMote(buffer, matrix, p, side, up, scale * (0.018F + h1 * 0.036F), live * pulse01(time * (8.0F + h0 * 4.0F) + i) * 0.58F);
      }

      return wrote | layeredOrbFlare(buffer, matrix, center, side, up, scale * (0.36F + shock * 0.48F), live);
   }

   private static boolean goldenMote(BufferBuilder buffer, Matrix4f matrix, Vec3 center, Vec3 side, Vec3 up, float size, float alpha) {
      if (alpha <= 0.001F) {
         return false;
      }

      boolean wrote = false;
      wrote |= crossFlare(buffer, matrix, center, side, up, size, 255, 239, 156, alpha);
      return wrote | planeRing(buffer, matrix, center, side, up, size * 0.88, Math.max(0.004F, size * 0.1F), 20, 0.0, 255, 206, 58, alpha * 0.44F, 0, 0);
   }

   private static boolean layeredOrbFlare(BufferBuilder buffer, Matrix4f matrix, Vec3 center, Vec3 side, Vec3 up, float size, float alpha) {
      if (alpha <= 0.001F) {
         return false;
      }

      boolean wrote = false;
      wrote |= crossFlare(buffer, matrix, center, side, up, size * 1.75F, 255, 168, 20, alpha * 0.18F);
      wrote |= crossFlare(buffer, matrix, center, side, up, size * 1.18F, 255, 216, 91, alpha * 0.34F);
      wrote |= crossFlare(buffer, matrix, center, side, up, size * 0.72F, 255, 249, 210, alpha * 0.72F);
      return wrote | crossFlare(buffer, matrix, center, side, up, size * 0.34F, 255, 255, 252, alpha);
   }

   private static boolean drawnSlashBlade(
      BufferBuilder buffer,
      Matrix4f matrix,
      Vec3 center,
      Vec3 axis,
      Vec3 forward,
      double reach,
      double width,
      float progress,
      int outerR,
      int outerG,
      int outerB,
      float outerAlpha,
      int innerR,
      int innerG,
      int innerB,
      float innerAlpha
   ) {
      progress = Mth.clamp(progress, 0.0F, 1.0F);
      if (progress <= 0.001F) {
         return false;
      }

      Vec3 normal = forward.cross(axis);
      if (normal.lengthSqr() < 1.0E-8) {
         normal = safeSide(forward);
      }

      normal = normal.normalize();
      Vec3 fullStart = center.subtract(axis.scale(reach));
      Vec3 fullEnd = center.add(axis.scale(reach));
      Vec3 currentEnd = fullStart.lerp(fullEnd, progress);
      boolean wrote = false;
      wrote |= bladeQuad(buffer, matrix, fullStart, currentEnd, normal, width * 2.7, outerR, outerG, outerB, outerAlpha);
      wrote |= bladeQuad(
         buffer,
         matrix,
         fullStart.add(forward.scale(0.015)),
         currentEnd.add(forward.scale(0.015)),
         normal,
         width * 1.22,
         255,
         Math.min(255, outerG + 44),
         Math.min(255, outerB + 72),
         Math.max(outerAlpha, innerAlpha * 0.5F)
      );
      wrote |= bladeQuad(
         buffer, matrix, fullStart.add(forward.scale(0.03)), currentEnd.add(forward.scale(0.03)), normal, width * 0.38, innerR, innerG, innerB, innerAlpha
      );
      Vec3 tipTail = currentEnd.subtract(axis.scale(Math.min(reach * 0.26, width * 6.0)));
      return wrote
         | ribbon(
            buffer,
            matrix,
            tipTail.add(forward.scale(0.043)),
            currentEnd.add(forward.scale(0.043)),
            Math.max(0.012F, (float)(width * 0.17)),
            255,
            255,
            251,
            innerAlpha
         );
   }

   private static boolean slashBlade(
      BufferBuilder buffer,
      Matrix4f matrix,
      Vec3 center,
      Vec3 axis,
      Vec3 forward,
      double reach,
      double width,
      int outerR,
      int outerG,
      int outerB,
      float outerAlpha,
      int innerR,
      int innerG,
      int innerB,
      float innerAlpha
   ) {
      if (innerAlpha <= 0.001F && outerAlpha <= 0.001F) {
         return false;
      }

      Vec3 normal = forward.cross(axis);
      if (normal.lengthSqr() < 1.0E-8) {
         normal = safeSide(forward);
      }

      normal = normal.normalize();
      Vec3 start = center.subtract(axis.scale(reach));
      Vec3 end = center.add(axis.scale(reach));
      boolean wrote = false;
      wrote |= bladeQuad(buffer, matrix, start, end, normal, width * 2.8, outerR, outerG, outerB, outerAlpha);
      wrote |= bladeQuad(
         buffer,
         matrix,
         start.add(forward.scale(0.015)),
         end.add(forward.scale(0.015)),
         normal,
         width * 1.28,
         255,
         Math.min(255, outerG + 42),
         Math.min(255, outerB + 70),
         Math.max(outerAlpha, innerAlpha * 0.52F)
      );
      return wrote
         | bladeQuad(buffer, matrix, start.add(forward.scale(0.03)), end.add(forward.scale(0.03)), normal, width * 0.4, innerR, innerG, innerB, innerAlpha);
   }

   private static boolean bladeQuad(BufferBuilder buffer, Matrix4f matrix, Vec3 start, Vec3 end, Vec3 normal, double width, int r, int g, int b, float alpha) {
      if (alpha <= 0.001F) {
         return false;
      }

      Vec3 offset = normal.scale(width);
      Vec3 direction = end.subtract(start);
      Vec3 taper = direction.lengthSqr() < 1.0E-8 ? Vec3.ZERO : direction.normalize().scale(width * 0.16);
      quad(
         buffer,
         matrix,
         start.subtract(offset).add(taper),
         start.add(offset).add(taper),
         end.add(offset).subtract(taper),
         end.subtract(offset).subtract(taper),
         r,
         g,
         b,
         alpha
      );
      return true;
   }

   private static boolean verticalRibbon(
      BufferBuilder buffer, Matrix4f matrix, Vec3 bottom, Vec3 top, Vec3 widthAxis, float width, int r, int g, int b, float alpha
   ) {
      if (alpha <= 0.001F) {
         return false;
      }

      Vec3 axis = widthAxis.lengthSqr() < 1.0E-8 ? new Vec3(1.0, 0.0, 0.0) : widthAxis.normalize();
      Vec3 offset = axis.scale(width);
      quad(buffer, matrix, bottom.subtract(offset), bottom.add(offset), top.add(offset), top.subtract(offset), r, g, b, alpha);
      return true;
   }

   private static boolean planeRing(
      BufferBuilder buffer,
      Matrix4f matrix,
      Vec3 center,
      Vec3 axisA,
      Vec3 axisB,
      double radius,
      float width,
      int segments,
      double spin,
      int r,
      int g,
      int b,
      float alpha,
      int gapEvery,
      int gapSize
   ) {
      if (alpha <= 0.001F) {
         return false;
      }

      if (radius <= 0.001) {
         return false;
      }

      if (width <= 0.001F) {
         return false;
      }

      if (segments < 3) {
         return false;
      }

      boolean wrote = false;
      double inner = Math.max(0.0, radius - width);
      double outer = radius + width;

      for (int i = 0; i < segments; i++) {
         if (gapEvery <= 0 || Math.floorMod(i, gapEvery) >= gapSize) {
            double a0 = i * (float) (Math.PI * 2) / segments + spin;
            double a1 = (i + 0.82) * (float) (Math.PI * 2) / segments + spin;
            Vec3 d0 = axisA.scale(Math.cos(a0)).add(axisB.scale(Math.sin(a0)));
            Vec3 d1 = axisA.scale(Math.cos(a1)).add(axisB.scale(Math.sin(a1)));
            quad(
               buffer,
               matrix,
               center.add(d0.scale(outer)),
               center.add(d1.scale(outer)),
               center.add(d1.scale(inner)),
               center.add(d0.scale(inner)),
               r,
               g,
               b,
               alpha
            );
            wrote = true;
         }
      }

      return wrote;
   }

   private static boolean crossFlare(BufferBuilder buffer, Matrix4f matrix, Vec3 center, Vec3 side, Vec3 up, float size, int r, int g, int b, float alpha) {
      if (alpha <= 0.001F) {
         return false;
      }

      boolean wrote = false;
      wrote |= ribbon(buffer, matrix, center.subtract(side.scale(size)), center.add(side.scale(size)), Math.max(0.008F, size * 0.032F), r, g, b, alpha);
      wrote |= ribbon(buffer, matrix, center.subtract(up.scale(size)), center.add(up.scale(size)), Math.max(0.008F, size * 0.032F), r, g, b, alpha);
      Vec3 diagonalA = side.add(up).normalize();
      Vec3 diagonalB = side.subtract(up).normalize();
      wrote |= ribbon(
         buffer,
         matrix,
         center.subtract(diagonalA.scale(size * 0.72)),
         center.add(diagonalA.scale(size * 0.72)),
         Math.max(0.006F, size * 0.017F),
         255,
         222,
         105,
         alpha * 0.64F
      );
      return wrote
         | ribbon(
            buffer,
            matrix,
            center.subtract(diagonalB.scale(size * 0.72)),
            center.add(diagonalB.scale(size * 0.72)),
            Math.max(0.006F, size * 0.017F),
            255,
            222,
            105,
            alpha * 0.64F
         );
   }

   private static boolean ribbon(BufferBuilder buffer, Matrix4f matrix, Vec3 start, Vec3 end, float width, int r, int g, int b, float alpha) {
      if (alpha <= 0.001F) {
         return false;
      }

      if (width <= 0.001F) {
         return false;
      }

      Vec3 direction = end.subtract(start);
      if (direction.lengthSqr() < 1.0E-8) {
         return false;
      }

      Vec3 side = safeSide(direction.normalize()).scale(width);
      quad(buffer, matrix, start.subtract(side), start.add(side), end.add(side), end.subtract(side), r, g, b, alpha);
      return true;
   }

   private static Vec3 safeSide(Vec3 dir) {
      Vec3 side = dir.cross(new Vec3(0.0, 1.0, 0.0));
      if (side.lengthSqr() < 1.0E-8) {
         side = dir.cross(new Vec3(1.0, 0.0, 0.0));
      }

      if (side.lengthSqr() < 1.0E-8) {
         side = new Vec3(1.0, 0.0, 0.0);
      }

      return side.normalize();
   }

   private static void quad(BufferBuilder buffer, Matrix4f matrix, Vec3 a, Vec3 b, Vec3 c, Vec3 d, int r, int g, int bl, float alpha) {
      int packedAlpha = Mth.clamp((int)(alpha * 255.0F), 0, 255);
      if (packedAlpha > 0) {
         buffer.addVertex(matrix, (float)a.x, (float)a.y, (float)a.z).setColor(Mth.clamp(r, 0, 255), Mth.clamp(g, 0, 255), Mth.clamp(bl, 0, 255), packedAlpha);
         buffer.addVertex(matrix, (float)b.x, (float)b.y, (float)b.z).setColor(Mth.clamp(r, 0, 255), Mth.clamp(g, 0, 255), Mth.clamp(bl, 0, 255), packedAlpha);
         buffer.addVertex(matrix, (float)c.x, (float)c.y, (float)c.z).setColor(Mth.clamp(r, 0, 255), Mth.clamp(g, 0, 255), Mth.clamp(bl, 0, 255), packedAlpha);
         buffer.addVertex(matrix, (float)d.x, (float)d.y, (float)d.z).setColor(Mth.clamp(r, 0, 255), Mth.clamp(g, 0, 255), Mth.clamp(bl, 0, 255), packedAlpha);
      }
   }

   private static float smoother(float value) {
      value = Mth.clamp(value, 0.0F, 1.0F);
      return value * value * value * (value * (value * 6.0F - 15.0F) + 10.0F);
   }

   private static float pulse01(float phase) {
      return 0.5F + 0.5F * Mth.sin(phase);
   }

   private static float hash(long seed, int salt) {
      long value = seed + salt * -7046029254386353131L;
      value ^= value >>> 30;
      value *= -4658895280553007687L;
      value ^= value >>> 27;
      value *= -7723592293110705685L;
      value ^= value >>> 31;
      return (float)(value & 16777215L) / 1.6777216E7F;
   }

   private static void triggerShake(int durationTicks, float strength, float frequency) {
      PathsClientState.triggerManualShake(durationTicks, strength, frequency);
   }

   private static final class Cast {
      private final ArthurAvalonVfxData payload;
      private final long created;
      private final float durationTicks;
      private boolean finalShake;

      private Cast(ArthurAvalonVfxData payload, long created) {
         this.payload = payload;
         this.created = created;
         this.durationTicks = payload.variant() == 1 ? Math.max(18.0F, payload.durationTicks()) : Math.max(334.0F, payload.durationTicks());
      }

      private float ageTicks(long now) {
         return Math.max(0.0F, (float)(now - this.created) / 50.0F);
      }

      private boolean dead(float age) {
         return age > this.durationTicks + (this.payload.variant() == 1 ? 8.0F : 18.0F);
      }

      private void updateShake(Minecraft minecraft, float age) {
         if (this.payload.variant() != 1) {
            if (!this.finalShake) {
               if (!(age < 222.0F)) {
                  if (minecraft.player != null) {
                     this.finalShake = true;
                     double distance = minecraft.player.getEyePosition().distanceTo(this.payload.end());
                     if (!(distance > 340.0)) {
                        float proximity = 1.0F - Mth.clamp((float)(distance / 340.0), 0.0F, 1.0F);
                        ArthurPromisedVictoryVfxClient.triggerShake(18, 7.0F + proximity * 8.0F, 10.0F + proximity * 2.5F);
                     }
                  }
               }
            }
         }
      }
   }
}
