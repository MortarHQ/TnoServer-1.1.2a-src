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
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent.Post;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent.Stage;
import org.joml.Matrix4f;

public final class FeldwayAvatarBeamClient {
   private static final Map<Integer, FeldwayAvatarBeamClient.ActiveBeam> ACTIVE = new ConcurrentHashMap<>();
   private static float time;

   private FeldwayAvatarBeamClient() {
   }

   public static void add(int bossId, int targetId, double startX, double startY, double startZ, double endX, double endY, double endZ, int duration, int seed) {
      Vec3 start = new Vec3(startX, startY, startZ);
      Vec3 end = new Vec3(endX, endY, endZ);
      Vec3 dir = end.subtract(start);
      if (dir.lengthSqr() < 0.001) {
         dir = new Vec3(0.0, 0.0, 1.0);
      }

      double range = Math.max(100.0, dir.length());
      dir = dir.normalize();
      Vec3 fallbackBossCenter = start.subtract(dir.scale(12.0)).add(0.0, -27.0, 0.0);
      ACTIVE.put(bossId, new FeldwayAvatarBeamClient.ActiveBeam(bossId, targetId, start, end, fallbackBossCenter, range, Math.max(144, duration + 46), seed));
      FeldwayAwakeningCameraShake.avatarPulse(2.2F, 50);
   }

   public static void onClientTick(Post event) {
      Minecraft minecraft = Minecraft.getInstance();
      time += 0.16F;
      if (minecraft.level == null) {
         ACTIVE.clear();
      } else {
         Iterator<FeldwayAvatarBeamClient.ActiveBeam> iterator = ACTIVE.values().iterator();

         while (iterator.hasNext()) {
            FeldwayAvatarBeamClient.ActiveBeam beam = iterator.next();
            beam.age++;
            if (beam.age == 18) {
               FeldwayAwakeningCameraShake.avatarPulse(1.8F, 40);
            }

            if (beam.age == 42) {
               FeldwayAwakeningCameraShake.avatarPulse(3.0F, 38);
            }

            if (beam.age == 58) {
               FeldwayAwakeningCameraShake.avatarImpact(3.8F, 18);
            }

            if (beam.age == 62) {
               FeldwayAwakeningCameraShake.avatarImpact(6.6F, 24);
            }

            if (beam.age == 66) {
               FeldwayAwakeningCameraShake.avatarImpact(5.2F, 20);
            }

            if (beam.age == 70) {
               FeldwayAwakeningCameraShake.avatarImpact(5.6F, 20);
            }

            if (beam.age == 76) {
               FeldwayAwakeningCameraShake.avatarImpact(10.2F, 32);
            }

            if (beam.age == 86) {
               FeldwayAwakeningCameraShake.avatarImpact(6.4F, 26);
            }

            if (beam.age == 94) {
               FeldwayAwakeningCameraShake.avatarImpact(7.2F, 28);
            }

            if (beam.age > beam.duration) {
               iterator.remove();
            }
         }
      }
   }

   public static boolean shouldUseAvatarShader(Player player) {
      if (player != null && !ACTIVE.isEmpty()) {
         Minecraft minecraft = Minecraft.getInstance();
         if (minecraft.level == null) {
            return false;
         }

         Vec3 eye = player.getEyePosition();

         for (FeldwayAvatarBeamClient.ActiveBeam beam : ACTIVE.values()) {
            Vec3 end = getCurrentEnd(minecraft.level.getEntity(beam.targetId), beam);
            double distance = distanceToSegment(eye, beam.start, end);
            double sourceDistance = player.position().distanceTo(beam.start);
            double rangeFade = Mth.clamp(1.0 - sourceDistance / 280.0, 0.0, 1.0);
            float age = beam.age;
            float fire = getFire(age);
            float charge = smooth(Mth.clamp(age / 62.0F, 0.0F, 1.0F));
            if (rangeFade > 0.0 && distance <= 90.0 && (charge > 0.24F || fire > 0.0F)) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   public static float getAvatarShaderIntensity(Player player) {
      if (player != null && !ACTIVE.isEmpty()) {
         Minecraft minecraft = Minecraft.getInstance();
         if (minecraft.level == null) {
            return 0.0F;
         }

         Vec3 eye = player.getEyePosition();
         float best = 0.0F;

         for (FeldwayAvatarBeamClient.ActiveBeam beam : ACTIVE.values()) {
            Vec3 end = getCurrentEnd(minecraft.level.getEntity(beam.targetId), beam);
            double distance = distanceToSegment(eye, beam.start, end);
            double proximity = Mth.clamp(1.0 - distance / 90.0, 0.0, 1.0);
            double sourceDistance = player.position().distanceTo(beam.start);
            double rangeFade = Mth.clamp(1.0 - sourceDistance / 280.0, 0.0, 1.0);
            float age = beam.age;
            float charge = smooth(Mth.clamp(age / 62.0F, 0.0F, 1.0F));
            float fire = getFire(age);
            float aftermath = age > 84.0F ? smooth(Mth.clamp((age - 84.0F) / 34.0F, 0.0F, 1.0F)) : 0.0F;
            float intensity = (float)(proximity * rangeFade) * (0.35F + charge * 0.55F + fire * 1.85F + aftermath * 0.65F);
            best = Math.max(best, intensity);
         }

         return Mth.clamp(best, 0.0F, 2.25F);
      } else {
         return 0.0F;
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

            for (FeldwayAvatarBeamClient.ActiveBeam beam : ACTIVE.values()) {
               Entity boss = minecraft.level.getEntity(beam.bossId);
               Entity target = minecraft.level.getEntity(beam.targetId);
               Vec3 end = getCurrentEnd(target, beam);
               renderWorldWarning(matrix, cam, beam, end, partialTick);
               renderRearBloom(matrix, cam, beam, boss, end, partialTick);
               renderMagicCircleFormation(matrix, cam, beam, boss, end, partialTick);
               renderFormationConnectors(matrix, cam, beam, boss, end, partialTick);
               renderOblivionBeam(matrix, cam, beam, end, partialTick);
               renderGroundRupture(matrix, cam, beam, end, partialTick);
            }

            RenderSystem.defaultBlendFunc();
            RenderSystem.depthMask(true);
            RenderSystem.enableCull();
            RenderSystem.disableBlend();
         }
      }
   }

   private static void renderRearBloom(Matrix4f matrix, Vec3 cam, FeldwayAvatarBeamClient.ActiveBeam beam, Entity bossEntity, Vec3 end, float partialTick) {
      float age = beam.age + partialTick;
      float appear = smooth(Mth.clamp(age / 22.0F, 0.0F, 1.0F));
      float fade = smooth(Mth.clamp((beam.duration - age) / 34.0F, 0.0F, 1.0F));
      float charge = smooth(Mth.clamp(age / 62.0F, 0.0F, 1.0F));
      float fire = getFire(age);
      float alpha = appear * fade;
      Vec3 center = getFormationCenter(bossEntity, beam, end);
      Vec3 normal = getFormationNormal(end, beam.start);
      Vec3 side = getFormationSide(normal);
      Vec3 up = new Vec3(0.0, 1.0, 0.0);
      BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
      boolean drew = false;

      for (int i = 0; i < 18; i++) {
         double angle = i / 18.0 * Math.PI * 2.0 + time * 0.34;
         Vec3 from = center.add(side.scale(Math.cos(angle) * 10.0)).add(up.scale(Math.sin(angle) * 10.0));
         Vec3 to = center.add(side.scale(Math.cos(angle) * (34.0 + charge * 20.0))).add(up.scale(Math.sin(angle) * (34.0 + charge * 20.0)));
         drew |= addRibbon(buffer, matrix, cam, from, to, 0.3F, 1.0F, 0.22F, 0.025F, alpha * (0.16F + fire * 0.2F));
         drew |= addRibbon(buffer, matrix, cam, from, to, 0.09F, 1.0F, 1.0F, 0.65F, alpha * (0.24F + fire * 0.24F));
      }

      if (drew) {
         BufferUploader.drawWithShader(buffer.buildOrThrow());
      }
   }

   private static void renderMagicCircleFormation(
      Matrix4f matrix, Vec3 cam, FeldwayAvatarBeamClient.ActiveBeam beam, Entity bossEntity, Vec3 end, float partialTick
   ) {
      float age = beam.age + partialTick;
      float appear = smooth(Mth.clamp(age / 22.0F, 0.0F, 1.0F));
      float fade = smooth(Mth.clamp((beam.duration - age) / 34.0F, 0.0F, 1.0F));
      float charge = smooth(Mth.clamp(age / 62.0F, 0.0F, 1.0F));
      float fire = getFire(age);
      float alpha = appear * fade;
      Vec3 center = getFormationCenter(bossEntity, beam, end);
      Vec3 normal = getFormationNormal(end, beam.start);
      Vec3 side = getFormationSide(normal);
      Vec3 up = new Vec3(0.0, 1.0, 0.0);
      BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
      boolean drew = false;
      double outer = 26.0 + charge * 10.0 + Mth.sin(time * 1.1F) * 0.8;
      double mid = outer * 0.78;
      double inner = outer * 0.52;
      double core = outer * 0.28;
      drew |= addFreeRing(buffer, matrix, cam, center, side, up, outer, 132, 0.28F, 1.0F, 0.18F, 0.02F, alpha * (0.3F + fire * 0.2F));
      drew |= addFreeRing(buffer, matrix, cam, center, side, up, outer * 0.92, 120, 0.09F, 1.0F, 1.0F, 0.72F, alpha * (0.54F + fire * 0.24F));
      drew |= addFreeRing(buffer, matrix, cam, center, side, up, mid, 112, 0.22F, 1.0F, 0.28F, 0.03F, alpha * (0.24F + charge * 0.16F + fire * 0.24F));
      drew |= addFreeRing(buffer, matrix, cam, center, side, up, mid * 0.9, 104, 0.08F, 1.0F, 1.0F, 0.72F, alpha * (0.48F + charge * 0.18F + fire * 0.3F));
      drew |= addFreeRing(buffer, matrix, cam, center, side, up, inner, 96, 0.18F, 1.0F, 0.34F, 0.04F, alpha * (0.24F + charge * 0.18F + fire * 0.28F));
      drew |= addFreeRing(buffer, matrix, cam, center, side, up, core, 72, 0.07F, 1.0F, 1.0F, 0.8F, alpha * (0.58F + charge * 0.24F + fire * 0.36F));
      drew |= addRegularPolygon(buffer, matrix, cam, center, side, up, mid * 0.98, 12, time * 0.32, 0.18F, 1.0F, 0.2F, 0.025F, alpha * 0.34F);
      drew |= addRegularPolygon(buffer, matrix, cam, center, side, up, inner * 0.92, 6, -time * 0.55, 0.16F, 1.0F, 1.0F, 0.7F, alpha * 0.44F);
      drew |= addStarPolygon(buffer, matrix, cam, center, side, up, inner * 0.95, 12, 5, time * 0.42, 0.14F, 1.0F, 0.3F, 0.03F, alpha * 0.38F);
      drew |= addStarPolygon(buffer, matrix, cam, center, side, up, core * 1.65, 8, 3, -time * 0.72, 0.08F, 1.0F, 1.0F, 0.76F, alpha * 0.5F);

      for (int i = 0; i < 12; i++) {
         double a = i / 12.0 * Math.PI * 2.0 + time * 0.16;
         Vec3 outerP = pointOnPlane(center, side, up, a, outer * 0.9);
         Vec3 midP = pointOnPlane(center, side, up, a, inner * 0.92);
         Vec3 coreP = pointOnPlane(center, side, up, a + (Math.PI / 12), core * 1.1);
         drew |= addRibbon(buffer, matrix, cam, outerP, midP, 0.11F, 1.0F, 0.24F, 0.03F, alpha * 0.38F);
         drew |= addRibbon(buffer, matrix, cam, midP, coreP, 0.05F, 1.0F, 1.0F, 0.74F, alpha * 0.54F);
      }

      for (int i = 0; i < 10; i++) {
         double a = i / 10.0 * Math.PI * 2.0 - time * 0.24;
         Vec3 orbitCenter = pointOnPlane(center, side, up, a, outer + 13.0 + Mth.sin(time + i) * 1.2);
         double orbitR = 4.6 + charge * 1.8 + (i % 2 == 0 ? 0.9 : 0.0);
         drew |= addFreeRing(buffer, matrix, cam, orbitCenter, side, up, orbitR, 48, 0.12F, 1.0F, 0.34F, 0.04F, alpha * (0.26F + fire * 0.18F));
         drew |= addFreeRing(buffer, matrix, cam, orbitCenter, side, up, orbitR * 0.48, 36, 0.04F, 1.0F, 1.0F, 0.76F, alpha * (0.48F + fire * 0.24F));
         Vec3 join = pointOnPlane(center, side, up, a, outer * 0.92);
         drew |= addRibbon(buffer, matrix, cam, join, orbitCenter, 0.05F, 1.0F, 0.28F, 0.03F, alpha * 0.34F);
      }

      for (int i = 0; i < 16; i++) {
         double a = i / 16.0 * Math.PI * 2.0 + time * 0.08;
         Vec3 p1 = pointOnPlane(center, side, up, a, outer * 0.5);
         Vec3 p2 = pointOnPlane(center, side, up, a + Math.PI, outer * 0.5);
         drew |= addRibbon(buffer, matrix, cam, p1, p2, 0.025F, 1.0F, 0.26F, 0.03F, alpha * 0.18F);
      }

      if (drew) {
         BufferUploader.drawWithShader(buffer.buildOrThrow());
      }
   }

   private static void renderFormationConnectors(
      Matrix4f matrix, Vec3 cam, FeldwayAvatarBeamClient.ActiveBeam beam, Entity bossEntity, Vec3 end, float partialTick
   ) {
      float age = beam.age + partialTick;
      float appear = smooth(Mth.clamp(age / 24.0F, 0.0F, 1.0F));
      float fade = smooth(Mth.clamp((beam.duration - age) / 34.0F, 0.0F, 1.0F));
      float charge = smooth(Mth.clamp(age / 62.0F, 0.0F, 1.0F));
      float fire = getFire(age);
      float alpha = appear * fade;
      Vec3 center = getFormationCenter(bossEntity, beam, end);
      Vec3 normal = getFormationNormal(end, beam.start);
      Vec3 side = getFormationSide(normal);
      Vec3 up = new Vec3(0.0, 1.0, 0.0);
      BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
      boolean drew = false;

      for (int i = 0; i < 18; i++) {
         double a = i / 18.0 * Math.PI * 2.0 + time * 0.48;
         Vec3 rim = pointOnPlane(center, side, up, a, 23.0 + charge * 7.0);
         Vec3 to = beam.start.add(normal.scale(-2.0 + Math.sin(time + i) * 0.8));
         drew |= addRibbon(buffer, matrix, cam, rim, to, 0.18F, 1.0F, 0.28F, 0.03F, alpha * (0.18F + charge * 0.22F));
         drew |= addRibbon(buffer, matrix, cam, rim, to, 0.05F, 1.0F, 1.0F, 0.76F, alpha * (0.32F + fire * 0.26F));
      }

      for (int i = 0; i < 8; i++) {
         double a = i / 8.0 * Math.PI * 2.0 - time * 0.85;
         Vec3 from = pointOnPlane(center, side, up, a, 10.0 + charge * 2.0);
         Vec3 arcMid = beam.start.add(side.scale(Math.cos(a) * (7.0 + charge * 2.5))).add(up.scale(Math.sin(a) * 5.0));
         drew |= addRibbon(buffer, matrix, cam, from, arcMid, 0.12F, 1.0F, 0.22F, 0.02F, alpha * 0.22F);
         drew |= addRibbon(buffer, matrix, cam, arcMid, beam.start, 0.08F, 1.0F, 1.0F, 0.72F, alpha * 0.34F);
      }

      if (drew) {
         BufferUploader.drawWithShader(buffer.buildOrThrow());
      }
   }

   private static void renderWorldWarning(Matrix4f matrix, Vec3 cam, FeldwayAvatarBeamClient.ActiveBeam beam, Vec3 end, float partialTick) {
      float age = beam.age + partialTick;
      float appear = smooth(Mth.clamp(age / 18.0F, 0.0F, 1.0F));
      float fade = smooth(Mth.clamp((beam.duration - age) / 34.0F, 0.0F, 1.0F));
      float charge = smooth(Mth.clamp(age / 62.0F, 0.0F, 1.0F));
      float fire = getFire(age);
      float alpha = appear * fade;
      BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
      boolean drew = false;
      Vec3 start = beam.start;
      Vec3 dir = end.subtract(start);
      if (!(dir.lengthSqr() < 0.001)) {
         dir = dir.normalize();
         float pulse = 0.75F + 0.25F * Mth.sin(time * 7.0F);
         drew |= addBeamLane(buffer, matrix, cam, start, end, 19.0F + charge * 8.0F, 1.0F, 0.05F, 0.01F, alpha * charge * 0.32F * pulse);
         drew |= addBeamLane(buffer, matrix, cam, start, end, 12.0F + charge * 5.0F, 1.0F, 0.38F, 0.035F, alpha * charge * 0.52F);
         drew |= addBeamLane(buffer, matrix, cam, start, end, 5.2F + charge * 2.2F, 1.0F, 0.95F, 0.48F, alpha * charge * 0.76F);

         for (int i = 0; i < 16; i++) {
            float p = i / 15.0F;
            Vec3 pos = start.lerp(end, p);
            double radius = 8.0 + charge * 18.0 + Mth.sin(time * 2.4F + i) * 1.3;
            drew |= addRingAroundLine(buffer, matrix, cam, pos, dir, radius, 88, 0.22F, 1.0F, 0.34F, 0.035F, alpha * charge * (0.3F + p * 0.16F));
            drew |= addRingAroundLine(buffer, matrix, cam, pos, dir, radius * 0.42, 68, 0.07F, 1.0F, 1.0F, 0.7F, alpha * charge * 0.66F);
         }

         for (int s = 0; s < 16; s++) {
            Vec3 previous = null;

            for (int i = 0; i <= 72; i++) {
               float p = i / 72.0F;
               Vec3 base = start.lerp(end, p);
               double angle = p * Math.PI * 14.0 + time * (3.8 + fire * 2.6) + s * Math.PI * 2.0 / 16.0;
               double radius = 6.0 + charge * 22.0 * (1.0 - p * 0.25);
               Vec3 sideA = perpendicularA(dir);
               Vec3 sideB = perpendicularB(dir, sideA);
               Vec3 point = base.add(sideA.scale(Math.cos(angle) * radius)).add(sideB.scale(Math.sin(angle) * radius));
               if (previous != null) {
                  drew |= addRibbon(buffer, matrix, cam, previous, point, 0.22F, 1.0F, 0.24F, 0.03F, alpha * charge * (1.0F - p * 0.7F));
                  drew |= addRibbon(buffer, matrix, cam, previous, point, 0.06F, 1.0F, 1.0F, 0.74F, alpha * charge * (1.0F - p * 0.52F));
               }

               previous = point;
            }
         }

         if (drew) {
            BufferUploader.drawWithShader(buffer.buildOrThrow());
         }
      }
   }

   private static void renderOblivionBeam(Matrix4f matrix, Vec3 cam, FeldwayAvatarBeamClient.ActiveBeam beam, Vec3 end, float partialTick) {
      float age = beam.age + partialTick;
      float fire = getFire(age);
      float aftermath = age > 84.0F ? smooth(Mth.clamp((age - 84.0F) / 36.0F, 0.0F, 1.0F)) : 0.0F;
      if (!(fire <= 0.001F) || !(aftermath <= 0.001F)) {
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         boolean drew = false;
         Vec3 start = beam.start;
         Vec3 dir = end.subtract(start);
         if (!(dir.lengthSqr() < 0.001)) {
            dir = dir.normalize();
            float pulse = 0.78F + 0.22F * Mth.sin(time * 34.0F);
            float beamAlpha = fire * pulse;
            if (fire > 0.001F) {
               drew |= addBeamLane(buffer, matrix, cam, start, end, 42.0F + fire * 10.0F, 1.0F, 0.01F, 0.002F, beamAlpha * 0.2F);
               drew |= addBeamLane(buffer, matrix, cam, start, end, 32.0F + fire * 8.0F, 1.0F, 0.04F, 0.006F, beamAlpha * 0.34F);
               drew |= addBeamLane(buffer, matrix, cam, start, end, 22.0F + fire * 6.0F, 1.0F, 0.22F, 0.018F, beamAlpha * 0.62F);
               drew |= addBeamLane(buffer, matrix, cam, start, end, 12.0F + fire * 3.0F, 1.0F, 0.76F, 0.12F, beamAlpha * 0.92F);
               drew |= addBeamLane(buffer, matrix, cam, start, end, 3.8F + fire * 1.4F, 1.0F, 1.0F, 0.88F, beamAlpha);

               for (int i = 0; i < 34; i++) {
                  float p = i / 33.0F;
                  Vec3 pos = start.lerp(end, p);
                  double radius = 11.0 + fire * 34.0 + p * 18.0;
                  drew |= addShockRing(buffer, matrix, cam, pos, dir, radius, beamAlpha * (1.0F - p * 0.25F));
               }

               for (int i = 0; i < 42; i++) {
                  float p = i / 41.0F;
                  Vec3 base = start.lerp(end, p);
                  Vec3 sideA = perpendicularA(dir);
                  Vec3 sideB = perpendicularB(dir, sideA);
                  float seed = hash(beam.seed * 0.021F + i * 11.0F);
                  double angle = seed * Math.PI * 2.0 + time * 5.8;
                  double dist = 10.0 + seed * 34.0;
                  Vec3 from = base.add(sideA.scale(Math.cos(angle) * dist)).add(sideB.scale(Math.sin(angle) * dist));
                  Vec3 to = base.add(sideA.scale(Math.cos(angle + 0.9) * 3.4)).add(sideB.scale(Math.sin(angle + 0.9) * 3.4));
                  drew |= addRibbon(buffer, matrix, cam, from, to, 0.26F, 1.0F, 0.42F, 0.045F, beamAlpha * 0.62F);
                  drew |= addRibbon(buffer, matrix, cam, from, to, 0.07F, 1.0F, 1.0F, 0.78F, beamAlpha * 0.95F);
               }
            }

            if (aftermath > 0.001F) {
               float a = 1.0F - aftermath;
               drew |= addBeamLane(buffer, matrix, cam, start, end, 18.0F + aftermath * 26.0F, 1.0F, 0.22F, 0.025F, a * 0.38F);
               drew |= addBeamLane(buffer, matrix, cam, start, end, 4.0F + aftermath * 8.0F, 1.0F, 1.0F, 0.62F, a * 0.68F);

               for (int i = 0; i < 30; i++) {
                  float p = i / 29.0F;
                  Vec3 pos = start.lerp(end, p).add(0.0, 0.4 + Mth.sin(time * 2.0F + i) * 0.4, 0.0);
                  drew |= addVerticalSpark(buffer, matrix, cam, pos, 10.0 + aftermath * 22.0, 0.14F, 1.0F, 0.6F, 0.1F, a * 0.76F);
                  drew |= addVerticalSpark(buffer, matrix, cam, pos.add(0.0, 0.2, 0.0), 6.0 + aftermath * 12.0, 0.045F, 1.0F, 1.0F, 0.72F, a * 0.95F);
               }
            }

            if (drew) {
               BufferUploader.drawWithShader(buffer.buildOrThrow());
            }
         }
      }
   }

   private static void renderGroundRupture(Matrix4f matrix, Vec3 cam, FeldwayAvatarBeamClient.ActiveBeam beam, Vec3 end, float partialTick) {
      float age = beam.age + partialTick;
      float fire = getFire(age);
      float aftermath = age > 84.0F ? smooth(Mth.clamp((age - 84.0F) / 38.0F, 0.0F, 1.0F)) : 0.0F;
      if (!(fire <= 0.001F) || !(aftermath <= 0.001F)) {
         Vec3 start = beam.start;
         Vec3 dir = end.subtract(start);
         if (!(dir.lengthSqr() < 0.001)) {
            dir = dir.normalize();
            Vec3 side = perpendicularA(dir);
            BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
            boolean drew = false;
            float alpha = fire > 0.001F ? fire : 1.0F - aftermath;

            for (int i = 0; i < 18; i++) {
               float p = i / 17.0F;
               Vec3 base = start.lerp(end, p);
               double spread = 8.0 + p * 24.0;
               double offset = (hash(beam.seed * 0.04F + i * 17.0F) - 0.5) * spread;
               Vec3 root = base.add(side.scale(offset)).add(0.0, -1.2, 0.0);
               Vec3 burst = root.add(side.scale(Math.signum(offset == 0.0 ? 1.0 : offset) * (8.0 + hash(i * 31.0F) * 18.0)))
                  .add(0.0, 4.0 + hash(i * 57.0F) * 12.0, 0.0);
               drew |= addRibbon(buffer, matrix, cam, root, burst, 0.18F, 1.0F, 0.35F, 0.04F, alpha * 0.46F);
               drew |= addRibbon(buffer, matrix, cam, root.add(0.0, 0.1, 0.0), burst.add(0.0, 0.1, 0.0), 0.055F, 1.0F, 1.0F, 0.7F, alpha * 0.78F);
            }

            if (drew) {
               BufferUploader.drawWithShader(buffer.buildOrThrow());
            }
         }
      }
   }

   private static Vec3 getCurrentEnd(Entity target, FeldwayAvatarBeamClient.ActiveBeam beam) {
      if (target instanceof LivingEntity living && living.isAlive()) {
         Vec3 targetCenter = living.position().add(0.0, living.getBbHeight() * 0.5, 0.0);
         Vec3 dir = targetCenter.subtract(beam.start);
         if (dir.lengthSqr() > 0.001) {
            return beam.start.add(dir.normalize().scale(beam.range));
         }
      }

      return beam.end;
   }

   private static Vec3 getBossAnchor(Entity bossEntity, FeldwayAvatarBeamClient.ActiveBeam beam) {
      return bossEntity instanceof LivingEntity living && living.isAlive()
         ? living.position().add(0.0, living.getBbHeight() * 0.7, 0.0)
         : beam.fallbackBossCenter;
   }

   private static Vec3 getFormationNormal(Vec3 end, Vec3 start) {
      Vec3 dir = end.subtract(start);
      dir = new Vec3(dir.x, 0.0, dir.z);
      if (dir.lengthSqr() < 0.001) {
         dir = new Vec3(0.0, 0.0, 1.0);
      }

      return dir.normalize();
   }

   private static Vec3 getFormationSide(Vec3 normal) {
      Vec3 side = new Vec3(-normal.z, 0.0, normal.x);
      if (side.lengthSqr() < 0.001) {
         side = new Vec3(1.0, 0.0, 0.0);
      }

      return side.normalize();
   }

   private static Vec3 getFormationCenter(Entity bossEntity, FeldwayAvatarBeamClient.ActiveBeam beam, Vec3 end) {
      Vec3 bossAnchor = getBossAnchor(bossEntity, beam);
      Vec3 normal = getFormationNormal(end, beam.start);
      return bossAnchor.add(normal.scale(-16.0)).add(0.0, 10.5, 0.0);
   }

   private static Vec3 pointOnPlane(Vec3 center, Vec3 side, Vec3 up, double angle, double radius) {
      return center.add(side.scale(Math.cos(angle) * radius)).add(up.scale(Math.sin(angle) * radius));
   }

   private static boolean addRegularPolygon(
      BufferBuilder buffer,
      Matrix4f matrix,
      Vec3 cam,
      Vec3 center,
      Vec3 side,
      Vec3 up,
      double radius,
      int corners,
      double rotation,
      float width,
      float r,
      float g,
      float b,
      float alpha
   ) {
      if (corners >= 3 && !(radius <= 0.001) && !(alpha <= 0.001F)) {
         boolean drew = false;
         Vec3 first = null;
         Vec3 previous = null;

         for (int i = 0; i <= corners; i++) {
            double a = rotation + (double)i / corners * Math.PI * 2.0;
            Vec3 p = pointOnPlane(center, side, up, a, radius);
            if (i == 0) {
               first = p;
            }

            if (previous != null) {
               drew |= addRibbon(buffer, matrix, cam, previous, p, width, r, g, b, alpha);
            }

            previous = p;
         }

         if (first != null && previous != null && previous.distanceToSqr(first) > 1.0E-4) {
            drew |= addRibbon(buffer, matrix, cam, previous, first, width, r, g, b, alpha);
         }

         return drew;
      } else {
         return false;
      }
   }

   private static boolean addStarPolygon(
      BufferBuilder buffer,
      Matrix4f matrix,
      Vec3 cam,
      Vec3 center,
      Vec3 side,
      Vec3 up,
      double radius,
      int points,
      int step,
      double rotation,
      float width,
      float r,
      float g,
      float b,
      float alpha
   ) {
      if (points >= 5 && step > 1 && !(alpha <= 0.001F)) {
         Vec3[] verts = new Vec3[points];

         for (int i = 0; i < points; i++) {
            double a = rotation + (double)i / points * Math.PI * 2.0;
            verts[i] = pointOnPlane(center, side, up, a, radius);
         }

         boolean drew = false;

         for (int i = 0; i < points; i++) {
            Vec3 a = verts[i];
            Vec3 bP = verts[(i + step) % points];
            drew |= addRibbon(buffer, matrix, cam, a, bP, width, r, g, b, alpha);
         }

         return drew;
      } else {
         return false;
      }
   }

   private static float getFire(float age) {
      if (!(age < 58.0F) && !(age > 98.0F)) {
         float in = smooth(Mth.clamp((age - 58.0F) / 7.0F, 0.0F, 1.0F));
         float out = smooth(Mth.clamp((98.0F - age) / 20.0F, 0.0F, 1.0F));
         return in * out;
      } else {
         return 0.0F;
      }
   }

   private static boolean addBeamLane(
      BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 start, Vec3 end, float width, float r, float g, float b, float alpha
   ) {
      if (!(alpha <= 0.001F) && !(width <= 0.001F)) {
         Vec3 dir = end.subtract(start);
         if (dir.lengthSqr() < 1.0E-6) {
            return false;
         }

         dir = dir.normalize();
         Vec3 side = perpendicularA(dir).scale(width);
         Vec3 up = perpendicularB(dir, side.normalize()).scale(width * 0.55);
         addVertex(buffer, matrix, cam, start.subtract(side), r, g, b, alpha * 0.78F);
         addVertex(buffer, matrix, cam, start.add(side), r, g, b, alpha);
         addVertex(buffer, matrix, cam, end.add(side), r, g, b, alpha * 0.65F);
         addVertex(buffer, matrix, cam, end.subtract(side), r, g, b, alpha * 0.48F);
         addVertex(buffer, matrix, cam, start.subtract(up), r, g, b, alpha * 0.56F);
         addVertex(buffer, matrix, cam, start.add(up), r, g, b, alpha * 0.86F);
         addVertex(buffer, matrix, cam, end.add(up), r, g, b, alpha * 0.56F);
         addVertex(buffer, matrix, cam, end.subtract(up), r, g, b, alpha * 0.38F);
         return true;
      } else {
         return false;
      }
   }

   private static boolean addRingAroundLine(
      BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 center, Vec3 dir, double radius, int segments, float width, float r, float g, float b, float alpha
   ) {
      Vec3 sideA = perpendicularA(dir);
      Vec3 sideB = perpendicularB(dir, sideA);
      return addFreeRing(buffer, matrix, cam, center, sideA, sideB, radius, segments, width, r, g, b, alpha);
   }

   private static boolean addFreeRing(
      BufferBuilder buffer,
      Matrix4f matrix,
      Vec3 cam,
      Vec3 center,
      Vec3 sideA,
      Vec3 sideB,
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
         double spin = time * 0.95 + radius * 0.035;

         for (int i = 0; i < segments; i++) {
            if (i % 5 != 2) {
               double a0 = (double)i / segments * Math.PI * 2.0 + spin;
               double a1 = (i + 0.78) / segments * Math.PI * 2.0 + spin;
               Vec3 p0 = center.add(sideA.scale(Math.cos(a0) * radius)).add(sideB.scale(Math.sin(a0) * radius));
               Vec3 p1 = center.add(sideA.scale(Math.cos(a1) * radius)).add(sideB.scale(Math.sin(a1) * radius));
               drew |= addRibbon(buffer, matrix, cam, p0, p1, width, r, g, b, alpha);
            }
         }

         return drew;
      } else {
         return false;
      }
   }

   private static boolean addShockRing(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 center, Vec3 dir, double radius, float alpha) {
      return addRingAroundLine(buffer, matrix, cam, center, dir, radius, 96, 0.3F, 1.0F, 0.66F, 0.12F, alpha);
   }

   private static boolean addVerticalSpark(
      BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 base, double height, float width, float r, float g, float b, float alpha
   ) {
      return addRibbon(buffer, matrix, cam, base, base.add(0.0, height, 0.0), width, r, g, b, alpha);
   }

   private static boolean addRibbon(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 a, Vec3 b, float width, float r, float g, float blue, float alpha) {
      if (!(alpha <= 0.001F) && !(width <= 0.001F)) {
         Vec3 dir = b.subtract(a);
         if (dir.lengthSqr() < 4.0E-7) {
            return false;
         }

         Vec3 mid = a.add(b).scale(0.5);
         Vec3 toCam = cam.subtract(mid);
         if (toCam.lengthSqr() < 1.0E-6) {
            toCam = new Vec3(0.0, 1.0, 0.0);
         }

         Vec3 side = dir.normalize().cross(toCam.normalize());
         if (side.lengthSqr() < 1.0E-6) {
            side = perpendicularA(dir.normalize());
         }

         side = side.normalize().scale(width);
         Vec3 a0 = a.subtract(side);
         Vec3 a1 = a.add(side);
         Vec3 b1 = b.add(side);
         Vec3 b0 = b.subtract(side);
         addVertex(buffer, matrix, cam, a0, r, g, blue, alpha);
         addVertex(buffer, matrix, cam, a1, r, g, blue, alpha);
         addVertex(buffer, matrix, cam, b1, r, g, blue, alpha * 0.64F);
         addVertex(buffer, matrix, cam, b0, r, g, blue, alpha * 0.64F);
         return true;
      } else {
         return false;
      }
   }

   private static void addVertex(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 p, float r, float g, float b, float a) {
      buffer.addVertex(matrix, (float)(p.x - cam.x), (float)(p.y - cam.y), (float)(p.z - cam.z)).setColor(r, g, b, a);
   }

   private static Vec3 perpendicularA(Vec3 dir) {
      Vec3 side = dir.cross(new Vec3(0.0, 1.0, 0.0));
      if (side.lengthSqr() < 1.0E-6) {
         side = dir.cross(new Vec3(1.0, 0.0, 0.0));
      }

      if (side.lengthSqr() < 1.0E-6) {
         side = new Vec3(1.0, 0.0, 0.0);
      }

      return side.normalize();
   }

   private static Vec3 perpendicularB(Vec3 dir, Vec3 sideA) {
      Vec3 sideB = dir.cross(sideA);
      if (sideB.lengthSqr() < 1.0E-6) {
         sideB = new Vec3(0.0, 1.0, 0.0);
      }

      return sideB.normalize();
   }

   private static double distanceToSegment(Vec3 point, Vec3 start, Vec3 end) {
      Vec3 line = end.subtract(start);
      double len = line.lengthSqr();
      if (len <= 1.0E-6) {
         return point.distanceTo(start);
      }

      double t = point.subtract(start).dot(line) / len;
      t = Math.max(0.0, Math.min(1.0, t));
      Vec3 closest = start.add(line.scale(t));
      return point.distanceTo(closest);
   }

   private static float smooth(float value) {
      return value * value * (3.0F - 2.0F * value);
   }

   private static float hash(float value) {
      return Mth.frac(Mth.sin(value * 12.9898F) * 43758.547F);
   }

   private static final class ActiveBeam {
      private final int bossId;
      private final int targetId;
      private final Vec3 start;
      private final Vec3 end;
      private final Vec3 fallbackBossCenter;
      private final double range;
      private final int duration;
      private final int seed;
      private int age;

      private ActiveBeam(int bossId, int targetId, Vec3 start, Vec3 end, Vec3 fallbackBossCenter, double range, int duration, int seed) {
         this.bossId = bossId;
         this.targetId = targetId;
         this.start = start;
         this.end = end;
         this.fallbackBossCenter = fallbackBossCenter;
         this.range = range;
         this.duration = duration;
         this.seed = seed;
      }
   }
}
