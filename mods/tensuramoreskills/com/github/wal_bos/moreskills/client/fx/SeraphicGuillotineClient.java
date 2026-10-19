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
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent.Post;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent.Stage;
import org.joml.Matrix4f;

public final class SeraphicGuillotineClient {
   private static final Map<Integer, SeraphicGuillotineClient.ActiveGuillotine> ACTIVE = new ConcurrentHashMap<>();
   private static float time;

   private SeraphicGuillotineClient() {
   }

   public static void add(int bossId, int targetId, double centerX, double centerY, double centerZ, double dirX, double dirZ, int duration, int seed) {
      Vec3 dir = new Vec3(dirX, 0.0, dirZ);
      if (dir.lengthSqr() < 0.001) {
         dir = new Vec3(0.0, 0.0, 1.0);
      }

      ACTIVE.put(
         bossId,
         new SeraphicGuillotineClient.ActiveGuillotine(
            bossId, targetId, new Vec3(centerX, centerY, centerZ), dir.normalize(), Math.max(132, duration + 38), seed
         )
      );
      FeldwayAwakeningCameraShake.avatarPulse(2.6F, 46);
   }

   public static void onClientTick(Post event) {
      Minecraft minecraft = Minecraft.getInstance();
      time += 0.16F;
      if (minecraft.level == null) {
         ACTIVE.clear();
      } else {
         Iterator<SeraphicGuillotineClient.ActiveGuillotine> iterator = ACTIVE.values().iterator();

         while (iterator.hasNext()) {
            SeraphicGuillotineClient.ActiveGuillotine guillotine = iterator.next();
            guillotine.age++;
            if (guillotine.age == 18 || guillotine.age == 30 || guillotine.age == 42) {
               FeldwayAwakeningCameraShake.avatarPulse(1.7F, 16);
            }

            if (guillotine.age == 58) {
               FeldwayAwakeningCameraShake.avatarImpact(4.8F, 22);
            }

            if (guillotine.age == 70 || guillotine.age == 76 || guillotine.age == 82) {
               FeldwayAwakeningCameraShake.avatarImpact(3.0F, 14);
            }

            if (guillotine.age == 92) {
               FeldwayAwakeningCameraShake.avatarImpact(10.5F, 34);
            }

            if (guillotine.age == 104) {
               FeldwayAwakeningCameraShake.avatarImpact(4.0F, 24);
            }

            if (guillotine.age > guillotine.duration) {
               iterator.remove();
            }
         }
      }
   }

   public static boolean shouldUseSeraphicShader(Player player) {
      return player != null && !ACTIVE.isEmpty() ? getSeraphicShaderIntensity(player) > 0.05F : false;
   }

   public static float getSeraphicShaderIntensity(Player player) {
      if (player != null && !ACTIVE.isEmpty()) {
         Vec3 eye = player.getEyePosition();
         float best = 0.0F;

         for (SeraphicGuillotineClient.ActiveGuillotine guillotine : ACTIVE.values()) {
            Vec3 side = side(guillotine.dir);
            Vec3 rel = eye.subtract(guillotine.center);
            double forward = Math.abs(rel.dot(guillotine.dir));
            double along = Math.abs(rel.dot(side));
            double vertical = Math.abs(rel.y);
            double plane = Mth.clamp(1.0 - forward / 54.0, 0.0, 1.0);
            double length = Mth.clamp(1.0 - along / 120.0, 0.0, 1.0);
            double height = Mth.clamp(1.0 - vertical / 128.0, 0.0, 1.0);
            float age = guillotine.age;
            float heaven = smooth(Mth.clamp(age / 46.0F, 0.0F, 1.0F));
            float descent = smooth(Mth.clamp((age - 44.0F) / 48.0F, 0.0F, 1.0F));
            float impact = getImpact(age);
            float intensity = (float)(plane * length * height) * (0.26F + heaven * 0.86F + descent * 0.42F + impact * 1.7F);
            best = Math.max(best, intensity);
         }

         return Mth.clamp(best, 0.0F, 2.4F);
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

            for (SeraphicGuillotineClient.ActiveGuillotine guillotine : ACTIVE.values()) {
               updateTargetCenter(minecraft, guillotine, partialTick);
               renderGuillotine(matrix, cam, guillotine, partialTick);
            }

            RenderSystem.defaultBlendFunc();
            RenderSystem.depthMask(true);
            RenderSystem.enableCull();
            RenderSystem.disableBlend();
         }
      }
   }

   private static void updateTargetCenter(Minecraft minecraft, SeraphicGuillotineClient.ActiveGuillotine guillotine, float partialTick) {
      if (minecraft.level != null && guillotine.targetId >= 0) {
         if (minecraft.level.getEntity(guillotine.targetId) instanceof LivingEntity living && living.isAlive() && guillotine.age < 18) {
            double x = Mth.lerp(partialTick, living.xOld, living.getX());
            double y = Mth.lerp(partialTick, living.yOld, living.getY());
            double z = Mth.lerp(partialTick, living.zOld, living.getZ());
            guillotine.center = new Vec3(x, y + living.getBbHeight() * 0.52, z);
         }
      }
   }

   private static void renderGuillotine(Matrix4f matrix, Vec3 cam, SeraphicGuillotineClient.ActiveGuillotine guillotine, float partialTick) {
      float age = guillotine.age + partialTick;
      float appear = smooth(Mth.clamp(age / 16.0F, 0.0F, 1.0F));
      float fade = smooth(Mth.clamp((guillotine.duration - age) / 34.0F, 0.0F, 1.0F));
      float alpha = appear * fade;
      renderHeavenRift(matrix, cam, guillotine, age, alpha);
      renderAngelicChains(matrix, cam, guillotine, age, alpha);
      renderDescendingSword(matrix, cam, guillotine, age, alpha);
      renderJudgementSeal(matrix, cam, guillotine, age, alpha);
      renderExecutionFlashes(matrix, cam, guillotine, age, alpha);
      renderFinalJudgement(matrix, cam, guillotine, age, alpha);
      renderHolyAfterimage(matrix, cam, guillotine, age, alpha);
   }

   private static void renderHeavenRift(Matrix4f matrix, Vec3 cam, SeraphicGuillotineClient.ActiveGuillotine guillotine, float age, float alpha) {
      BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
      boolean drew = false;
      Vec3 center = guillotine.center.add(0.0, 118.0, 0.0);
      Vec3 side = side(guillotine.dir);
      float open = smooth(Mth.clamp(age / 42.0F, 0.0F, 1.0F));
      float pulse = 0.78F + 0.22F * Mth.sin(time * 5.4F);

      for (int i = 0; i < 6; i++) {
         double radius = 20.0 + i * 10.5 + open * 18.0 + Math.sin(time * 0.9F + i) * 1.8;
         double y = center.y + i * 1.35;
         float ringAlpha = alpha * open * pulse * (0.42F - i * 0.035F);
         drew |= addFreeRing(
            buffer, matrix, cam, new Vec3(center.x, y, center.z), side, guillotine.dir, radius, 112, 0.34F + i * 0.018F, 1.0F, 0.94F, 0.62F, ringAlpha
         );
         drew |= addFreeRing(
            buffer, matrix, cam, new Vec3(center.x, y + 0.12, center.z), side, guillotine.dir, radius * 0.72, 96, 0.085F, 1.0F, 1.0F, 0.92F, ringAlpha * 1.45F
         );
      }

      for (int i = 0; i < 28; i++) {
         float seed = hash(guillotine.seed * 0.018F + i * 19.17F);
         double a = i / 28.0 * Math.PI * 2.0 + time * (0.18 + seed * 0.12);
         double r0 = 20.0 + seed * 18.0;
         double r1 = 58.0 + seed * 38.0;
         Vec3 a0 = center.add(side.scale(Math.cos(a) * r0)).add(guillotine.dir.scale(Math.sin(a) * r0 * 0.54));
         Vec3 b0 = center.add(side.scale(Math.cos(a + 0.26) * r1)).add(guillotine.dir.scale(Math.sin(a + 0.26) * r1 * 0.54)).add(0.0, -12.0 - seed * 30.0, 0.0);
         drew |= addRibbon(buffer, matrix, cam, a0, b0, 0.2F + seed * 0.1F, 1.0F, 0.92F, 0.52F, alpha * open * 0.34F);
         drew |= addRibbon(buffer, matrix, cam, a0.add(0.0, 0.08, 0.0), b0.add(0.0, 0.08, 0.0), 0.052F, 1.0F, 1.0F, 0.88F, alpha * open * 0.62F);
      }

      if (drew) {
         BufferUploader.drawWithShader(buffer.buildOrThrow());
      }
   }

   private static void renderAngelicChains(Matrix4f matrix, Vec3 cam, SeraphicGuillotineClient.ActiveGuillotine guillotine, float age, float alpha) {
      BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
      boolean drew = false;
      Vec3 center = guillotine.center;
      Vec3 side = side(guillotine.dir);
      float charge = smooth(Mth.clamp(age / 58.0F, 0.0F, 1.0F));
      float descend = smooth(Mth.clamp((age - 36.0F) / 50.0F, 0.0F, 1.0F));

      for (int i = 0; i < 34; i++) {
         float seed = hash(guillotine.seed * 0.024F + i * 8.91F);
         double angle = i / 34.0 * Math.PI * 2.0 + time * (0.34 + seed * 0.18);
         double outer = 88.0 + seed * 34.0 - descend * 28.0;
         double inner = 12.0 + seed * 8.0;
         double topY = 112.0 + seed * 34.0 - descend * 18.0;
         double lowY = 6.0 + seed * 18.0;
         Vec3 high = center.add(side.scale(Math.cos(angle) * outer)).add(guillotine.dir.scale(Math.sin(angle) * outer * 0.42)).add(0.0, topY, 0.0);
         Vec3 low = center.add(side.scale(Math.cos(angle + 0.42) * inner)).add(guillotine.dir.scale(Math.sin(angle + 0.42) * inner * 0.42)).add(0.0, lowY, 0.0);
         drew |= addRibbon(buffer, matrix, cam, high, low, 0.18F, 1.0F, 0.84F, 0.38F, alpha * charge * 0.34F);
         drew |= addRibbon(buffer, matrix, cam, high.add(0.0, 0.1, 0.0), low.add(0.0, 0.1, 0.0), 0.047F, 1.0F, 1.0F, 0.88F, alpha * charge * 0.72F);
      }

      for (int i = 0; i < 8; i++) {
         double a = i / 8.0 * Math.PI * 2.0 - time * 0.22;
         double r = 42.0 + Mth.sin(time * 0.8F + i) * 4.0;
         Vec3 p = center.add(side.scale(Math.cos(a) * r)).add(guillotine.dir.scale(Math.sin(a) * r * 0.36)).add(0.0, 38.0 + i * 4.0, 0.0);
         Vec3 q = center.add(side.scale(Math.cos(a + Math.PI) * r * 0.44)).add(guillotine.dir.scale(Math.sin(a + Math.PI) * r * 0.2)).add(0.0, 12.0, 0.0);
         drew |= addRibbon(buffer, matrix, cam, p, q, 0.28F, 1.0F, 0.88F, 0.44F, alpha * charge * 0.26F);
      }

      if (drew) {
         BufferUploader.drawWithShader(buffer.buildOrThrow());
      }
   }

   private static void renderDescendingSword(Matrix4f matrix, Vec3 cam, SeraphicGuillotineClient.ActiveGuillotine guillotine, float age, float alpha) {
      if (!(age < 10.0F) && !(age > 112.0F)) {
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         boolean drew = false;
         Vec3 side = side(guillotine.dir);
         Vec3 forward = guillotine.dir;
         float summon = smooth(Mth.clamp((age - 10.0F) / 34.0F, 0.0F, 1.0F));
         float drop = smooth(Mth.clamp((age - 48.0F) / 44.0F, 0.0F, 1.0F));
         float impact = getImpact(age);
         float glow = 0.82F + 0.18F * Mth.sin(time * 8.0F);
         double swordTopY = 190.0 - drop * 112.0;
         double swordLength = 154.0 + summon * 52.0 + impact * 26.0;
         double bladeHalf = 13.0 + summon * 11.0 + impact * 7.0;
         double guardHalf = 48.0 + summon * 28.0 + impact * 18.0;
         Vec3 hiltTop = guillotine.center.add(0.0, swordTopY + 34.0, 0.0);
         Vec3 guard = guillotine.center.add(0.0, swordTopY, 0.0);
         Vec3 shoulder = guillotine.center.add(0.0, swordTopY - 18.0, 0.0);
         Vec3 tip = guillotine.center.add(0.0, swordTopY - swordLength, 0.0);
         drew |= addBladeFace(
            buffer, matrix, cam, side, forward, shoulder, tip, bladeHalf * 1.18, bladeHalf * 0.38, 1.0F, 0.92F, 0.52F, alpha * summon * 0.52F * glow
         );
         drew |= addBladeFace(
            buffer,
            matrix,
            cam,
            forward,
            side,
            shoulder.add(0.0, 0.08, 0.0),
            tip.add(0.0, 0.08, 0.0),
            bladeHalf * 0.62,
            bladeHalf * 0.16,
            1.0F,
            1.0F,
            0.92F,
            alpha * summon * 0.82F
         );
         drew |= addBladeCore(buffer, matrix, cam, side, shoulder.add(0.0, 8.0, 0.0), tip, bladeHalf * 0.2, 1.0F, 1.0F, 0.98F, alpha * summon * 1.15F);
         drew |= addBladeCore(buffer, matrix, cam, forward, shoulder.add(0.0, 8.0, 0.0), tip, bladeHalf * 0.11, 1.0F, 1.0F, 1.0F, alpha * summon);
         Vec3 leftGuard = guard.subtract(side.scale(guardHalf));
         Vec3 rightGuard = guard.add(side.scale(guardHalf));
         Vec3 leftGuard2 = guard.subtract(side.scale(guardHalf * 0.64)).add(0.0, -10.0, 0.0);
         Vec3 rightGuard2 = guard.add(side.scale(guardHalf * 0.64)).add(0.0, -10.0, 0.0);
         drew |= addRibbon(buffer, matrix, cam, leftGuard, rightGuard, 2.4F, 1.0F, 0.8F, 0.28F, alpha * summon * 0.62F);
         drew |= addRibbon(buffer, matrix, cam, leftGuard2, rightGuard2, 1.1F, 1.0F, 1.0F, 0.84F, alpha * summon * 0.88F);
         drew |= addRibbon(buffer, matrix, cam, hiltTop, guard.add(0.0, -6.0, 0.0), 2.0F, 1.0F, 0.86F, 0.36F, alpha * summon * 0.72F);
         drew |= addRibbon(buffer, matrix, cam, hiltTop.add(0.0, 4.0, 0.0), guard.add(0.0, -10.0, 0.0), 0.58F, 1.0F, 1.0F, 0.94F, alpha * summon);

         for (int i = 0; i < 16; i++) {
            float seed = hash(guillotine.seed * 0.061F + i * 11.0F);
            double x = (seed - 0.5) * bladeHalf * 2.6;
            double y0 = swordTopY + 12.0 - seed * 34.0;
            double y1 = swordTopY - swordLength + 24.0 + seed * 62.0;
            Vec3 a = guillotine.center.add(side.scale(x)).add(forward.scale((seed - 0.5) * 1.8)).add(0.0, y0, 0.0);
            Vec3 b = guillotine.center.add(side.scale(x * 0.12)).add(0.0, y1, 0.0);
            drew |= addRibbon(buffer, matrix, cam, a, b, 0.1F + seed * 0.05F, 1.0F, 0.93F, 0.6F, alpha * summon * 0.42F);
         }

         if (drew) {
            BufferUploader.drawWithShader(buffer.buildOrThrow());
         }
      }
   }

   private static void renderJudgementSeal(Matrix4f matrix, Vec3 cam, SeraphicGuillotineClient.ActiveGuillotine guillotine, float age, float alpha) {
      BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
      boolean drew = false;
      Vec3 center = guillotine.center.add(0.0, 1.0, 0.0);
      Vec3 side = side(guillotine.dir);
      float charge = smooth(Mth.clamp(age / 58.0F, 0.0F, 1.0F));
      float impact = getImpact(age);
      float pulse = 0.72F + 0.28F * Mth.sin(time * 7.0F);

      for (int i = 0; i < 5; i++) {
         double radius = 14.0 + i * 9.5 + charge * 10.0 + impact * 22.0;
         float a = alpha * charge * pulse * (0.42F - i * 0.045F);
         drew |= addFreeRing(buffer, matrix, cam, center.add(0.0, 0.18 + i * 0.05, 0.0), side, guillotine.dir, radius, 100, 0.18F, 1.0F, 0.9F, 0.46F, a);
         drew |= addFreeRing(
            buffer, matrix, cam, center.add(0.0, 0.3 + i * 0.05, 0.0), side, guillotine.dir, radius * 0.54, 78, 0.05F, 1.0F, 1.0F, 0.92F, a * 1.28F
         );
      }

      for (int i = 0; i < 12; i++) {
         double a = i / 12.0 * Math.PI * 2.0 + time * 0.16;
         Vec3 inner = center.add(side.scale(Math.cos(a) * 7.0)).add(guillotine.dir.scale(Math.sin(a) * 7.0));
         Vec3 outer = center.add(side.scale(Math.cos(a) * (48.0 + impact * 24.0))).add(guillotine.dir.scale(Math.sin(a) * (48.0 + impact * 24.0)));
         drew |= addRibbon(buffer, matrix, cam, inner, outer, 0.2F, 1.0F, 0.88F, 0.38F, alpha * charge * 0.35F);
         drew |= addRibbon(buffer, matrix, cam, inner.add(0.0, 0.06, 0.0), outer.add(0.0, 0.06, 0.0), 0.045F, 1.0F, 1.0F, 0.88F, alpha * charge * 0.7F);
      }

      Vec3 top = center.add(0.0, 92.0, 0.0);
      Vec3 bottom = center.add(0.0, -26.0, 0.0);
      drew |= addPlane(
         buffer,
         matrix,
         cam,
         bottom.subtract(side.scale(5.4)),
         bottom.add(side.scale(5.4)),
         top.add(side.scale(5.4)),
         top.subtract(side.scale(5.4)),
         1.0F,
         0.94F,
         0.62F,
         alpha * charge * 0.24F
      );
      drew |= addPlane(
         buffer,
         matrix,
         cam,
         bottom.subtract(side.scale(1.2)),
         bottom.add(side.scale(1.2)),
         top.add(side.scale(1.2)),
         top.subtract(side.scale(1.2)),
         1.0F,
         1.0F,
         0.92F,
         alpha * charge * 0.78F
      );
      if (drew) {
         BufferUploader.drawWithShader(buffer.buildOrThrow());
      }
   }

   private static void renderExecutionFlashes(Matrix4f matrix, Vec3 cam, SeraphicGuillotineClient.ActiveGuillotine guillotine, float age, float alpha) {
      BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
      boolean drew = false;

      for (int pass = 0; pass < 3; pass++) {
         float start = 70.0F + pass * 6.0F;
         float end = start + 13.0F;
         if (!(age < start) && !(age > end)) {
            float local = Mth.clamp((age - start) / (end - start), 0.0F, 1.0F);
            float slash = Mth.sin(local * (float) Math.PI);
            double angle = pass == 0 ? 0.5654866776461628 : (pass == 1 ? -0.5654866776461628 : Math.PI / 2);
            Vec3 dir = rotateFlat(guillotine.dir, angle);
            Vec3 side = side(dir);
            Vec3 center = guillotine.center.add(0.0, 8.0 + pass * 4.0, 0.0);
            Vec3 a = center.subtract(side.scale(78.0)).add(dir.scale(-5.0)).add(0.0, 18.0, 0.0);
            Vec3 b = center.add(side.scale(78.0)).add(dir.scale(5.0)).add(0.0, -12.0, 0.0);
            drew |= addRibbon(buffer, matrix, cam, a, b, 2.8F, 1.0F, 0.74F, 0.18F, alpha * slash * 0.4F);
            drew |= addRibbon(buffer, matrix, cam, a, b, 0.72F, 1.0F, 0.94F, 0.56F, alpha * slash * 0.82F);
            drew |= addRibbon(buffer, matrix, cam, a, b, 0.14F, 1.0F, 1.0F, 0.95F, alpha * slash * 1.15F);
         }
      }

      if (drew) {
         BufferUploader.drawWithShader(buffer.buildOrThrow());
      }
   }

   private static void renderFinalJudgement(Matrix4f matrix, Vec3 cam, SeraphicGuillotineClient.ActiveGuillotine guillotine, float age, float alpha) {
      if (!(age < 88.0F) && !(age > 112.0F)) {
         float local = Mth.clamp((age - 88.0F) / 24.0F, 0.0F, 1.0F);
         float blast = Mth.sin(local * (float) Math.PI);
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         boolean drew = false;
         Vec3 center = guillotine.center.add(0.0, 3.0, 0.0);
         Vec3 side = side(guillotine.dir);
         drew |= addPlane(
            buffer,
            matrix,
            cam,
            center.subtract(side.scale(18.0)).add(0.0, -36.0, 0.0),
            center.add(side.scale(18.0)).add(0.0, -36.0, 0.0),
            center.add(side.scale(18.0)).add(0.0, 128.0, 0.0),
            center.subtract(side.scale(18.0)).add(0.0, 128.0, 0.0),
            1.0F,
            0.82F,
            0.28F,
            alpha * blast * 0.44F
         );
         drew |= addRibbon(buffer, matrix, cam, center.add(0.0, -40.0, 0.0), center.add(0.0, 142.0, 0.0), 4.2F, 1.0F, 0.92F, 0.48F, alpha * blast * 0.82F);
         drew |= addRibbon(buffer, matrix, cam, center.add(0.0, -46.0, 0.0), center.add(0.0, 156.0, 0.0), 0.78F, 1.0F, 1.0F, 0.96F, alpha * blast * 1.25F);

         for (int i = 0; i < 36; i++) {
            double a = i / 36.0 * Math.PI * 2.0 + time * 1.45;
            double r0 = 8.0 + blast * 12.0;
            double r1 = 48.0 + blast * 72.0 + hash(guillotine.seed * 0.08F + i) * 20.0;
            Vec3 start = center.add(side.scale(Math.cos(a) * r0)).add(guillotine.dir.scale(Math.sin(a) * r0 * 0.55)).add(0.0, 2.0, 0.0);
            Vec3 end = center.add(side.scale(Math.cos(a) * r1)).add(guillotine.dir.scale(Math.sin(a) * r1 * 0.55)).add(0.0, 12.0 + blast * 42.0, 0.0);
            drew |= addRibbon(buffer, matrix, cam, start, end, 0.22F, 1.0F, 0.78F, 0.24F, alpha * blast * 0.48F);
            drew |= addRibbon(buffer, matrix, cam, start, end, 0.054F, 1.0F, 1.0F, 0.92F, alpha * blast * 0.84F);
         }

         for (int i = 0; i < 7; i++) {
            double radius = 22.0 + i * 12.0 + blast * 60.0;
            drew |= addFreeRing(
               buffer,
               matrix,
               cam,
               center.add(0.0, 0.2 + i * 0.08, 0.0),
               side,
               guillotine.dir,
               radius,
               112,
               0.26F,
               1.0F,
               0.88F,
               0.38F,
               alpha * blast * (0.34F - i * 0.026F)
            );
         }

         if (drew) {
            BufferUploader.drawWithShader(buffer.buildOrThrow());
         }
      }
   }

   private static void renderHolyAfterimage(Matrix4f matrix, Vec3 cam, SeraphicGuillotineClient.ActiveGuillotine guillotine, float age, float alpha) {
      if (!(age < 104.0F)) {
         float local = smooth(Mth.clamp((age - 104.0F) / 36.0F, 0.0F, 1.0F));
         float a = alpha * (1.0F - local);
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         boolean drew = false;
         Vec3 side = side(guillotine.dir);
         Vec3 center = guillotine.center.add(0.0, 1.0, 0.0);

         for (int i = 0; i < 26; i++) {
            double p = i / 25.0;
            double offset = -72.0 + p * 144.0;
            float seed = hash(guillotine.seed * 0.09F + i * 3.7F);
            Vec3 start = center.add(side.scale(offset)).add(guillotine.dir.scale((seed - 0.5) * 8.0)).add(0.0, 0.5, 0.0);
            Vec3 end = start.add(0.0, 14.0 + local * 30.0 + seed * 18.0, 0.0);
            drew |= addRibbon(buffer, matrix, cam, start, end, 0.13F, 1.0F, 0.82F, 0.32F, a * 0.36F);
            drew |= addRibbon(buffer, matrix, cam, start.add(0.0, 0.08, 0.0), end.add(0.0, 0.08, 0.0), 0.032F, 1.0F, 1.0F, 0.92F, a * 0.68F);
         }

         for (int i = 0; i < 4; i++) {
            drew |= addFreeRing(
               buffer,
               matrix,
               cam,
               center.add(0.0, 0.16 + i * 0.05, 0.0),
               side,
               guillotine.dir,
               36.0 + i * 22.0 + local * 38.0,
               96,
               0.13F,
               1.0F,
               0.92F,
               0.55F,
               a * (0.3F - i * 0.042F)
            );
         }

         if (drew) {
            BufferUploader.drawWithShader(buffer.buildOrThrow());
         }
      }
   }

   private static boolean addBladeFace(
      BufferBuilder buffer,
      Matrix4f matrix,
      Vec3 cam,
      Vec3 widthAxis,
      Vec3 depthAxis,
      Vec3 shoulder,
      Vec3 tip,
      double shoulderHalf,
      double tipHalf,
      float r,
      float g,
      float b,
      float a
   ) {
      if (a <= 0.001F) {
         return false;
      }

      Vec3 leftShoulder = shoulder.subtract(widthAxis.scale(shoulderHalf)).add(depthAxis.scale(0.18));
      Vec3 rightShoulder = shoulder.add(widthAxis.scale(shoulderHalf)).add(depthAxis.scale(0.18));
      Vec3 rightTip = tip.add(widthAxis.scale(tipHalf));
      Vec3 leftTip = tip.subtract(widthAxis.scale(tipHalf));
      addVertex(buffer, matrix, cam, leftShoulder, r, g, b, a * 0.42F);
      addVertex(buffer, matrix, cam, rightShoulder, r, g, b, a);
      addVertex(buffer, matrix, cam, rightTip, r, g, b, a * 0.85F);
      addVertex(buffer, matrix, cam, leftTip, r, g, b, a * 0.42F);
      return true;
   }

   private static boolean addBladeCore(
      BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 axis, Vec3 top, Vec3 tip, double width, float r, float g, float b, float a
   ) {
      if (a <= 0.001F) {
         return false;
      }

      Vec3 p0 = top.subtract(axis.scale(width));
      Vec3 p1 = top.add(axis.scale(width));
      Vec3 p2 = tip.add(axis.scale(width * 0.08));
      Vec3 p3 = tip.subtract(axis.scale(width * 0.08));
      addVertex(buffer, matrix, cam, p0, r, g, b, a * 0.72F);
      addVertex(buffer, matrix, cam, p1, r, g, b, a);
      addVertex(buffer, matrix, cam, p2, r, g, b, a);
      addVertex(buffer, matrix, cam, p3, r, g, b, a * 0.72F);
      return true;
   }

   private static boolean addPlane(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 p0, Vec3 p1, Vec3 p2, Vec3 p3, float r, float g, float b, float a) {
      if (a <= 0.001F) {
         return false;
      }

      addVertex(buffer, matrix, cam, p0, r, g, b, a * 0.56F);
      addVertex(buffer, matrix, cam, p1, r, g, b, a);
      addVertex(buffer, matrix, cam, p2, r, g, b, a);
      addVertex(buffer, matrix, cam, p3, r, g, b, a * 0.56F);
      return true;
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
         double spin = time * 0.62 + radius * 0.017;

         for (int i = 0; i < segments; i++) {
            if (i % 7 != 3) {
               double a0 = (double)i / segments * Math.PI * 2.0 + spin;
               double a1 = (i + 0.82) / segments * Math.PI * 2.0 + spin;
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
            side = side(dir.normalize());
         }

         side = side.normalize().scale(width);
         Vec3 p0 = a.subtract(side);
         Vec3 p1 = a.add(side);
         Vec3 p2 = b.add(side);
         Vec3 p3 = b.subtract(side);
         addVertex(buffer, matrix, cam, p0, r, g, blue, alpha);
         addVertex(buffer, matrix, cam, p1, r, g, blue, alpha);
         addVertex(buffer, matrix, cam, p2, r, g, blue, alpha * 0.66F);
         addVertex(buffer, matrix, cam, p3, r, g, blue, alpha * 0.66F);
         return true;
      } else {
         return false;
      }
   }

   private static void addVertex(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 p, float r, float g, float b, float a) {
      buffer.addVertex(matrix, (float)(p.x - cam.x), (float)(p.y - cam.y), (float)(p.z - cam.z)).setColor(r, g, b, a);
   }

   private static Vec3 side(Vec3 dir) {
      Vec3 side = new Vec3(-dir.z, 0.0, dir.x);
      if (side.lengthSqr() < 0.001) {
         side = new Vec3(1.0, 0.0, 0.0);
      }

      return side.normalize();
   }

   private static Vec3 rotateFlat(Vec3 dir, double angle) {
      double cos = Math.cos(angle);
      double sin = Math.sin(angle);
      Vec3 rotated = new Vec3(dir.x * cos - dir.z * sin, 0.0, dir.x * sin + dir.z * cos);
      return rotated.lengthSqr() < 0.001 ? dir : rotated.normalize();
   }

   private static float getImpact(float age) {
      float first = age >= 58.0F && age <= 72.0F ? Mth.sin(Mth.clamp((age - 58.0F) / 14.0F, 0.0F, 1.0F) * (float) Math.PI) : 0.0F;
      float finisher = age >= 88.0F && age <= 112.0F ? Mth.sin(Mth.clamp((age - 88.0F) / 24.0F, 0.0F, 1.0F) * (float) Math.PI) : 0.0F;
      return Math.max(first * 0.72F, finisher);
   }

   private static float smooth(float value) {
      return value * value * (3.0F - 2.0F * value);
   }

   private static float hash(float value) {
      return Mth.frac(Mth.sin(value * 12.9898F) * 43758.547F);
   }

   private static final class ActiveGuillotine {
      private final int bossId;
      private final int targetId;
      private final Vec3 dir;
      private final int duration;
      private final int seed;
      private Vec3 center;
      private int age;

      private ActiveGuillotine(int bossId, int targetId, Vec3 center, Vec3 dir, int duration, int seed) {
         this.bossId = bossId;
         this.targetId = targetId;
         this.center = center;
         this.dir = dir;
         this.duration = duration;
         this.seed = seed;
      }
   }
}
