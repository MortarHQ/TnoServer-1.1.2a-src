package com.github.wal_bos.moreskills.client.fx.avalon;

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

public final class AvalonDivineDisintegrationVfxClient {
   private static final List<AvalonDivineDisintegrationVfxClient.Cast> CASTS = new ArrayList<>();
   private static final int GOLD_DEEP_R = 255;
   private static final int GOLD_DEEP_G = 105;
   private static final int GOLD_DEEP_B = 4;
   private static final int GOLD_R = 255;
   private static final int GOLD_G = 180;
   private static final int GOLD_B = 32;
   private static final int GOLD_HOT_R = 255;
   private static final int GOLD_HOT_G = 224;
   private static final int GOLD_HOT_B = 98;
   private static final int GOLD_PALE_R = 255;
   private static final int GOLD_PALE_G = 248;
   private static final int GOLD_PALE_B = 208;
   private static boolean initialized;

   private AvalonDivineDisintegrationVfxClient() {
   }

   public static void init() {
      if (!initialized) {
         initialized = true;
         NeoForge.EVENT_BUS.addListener(AvalonDivineDisintegrationVfxClient::render);
      }
   }

   public static void spawn(ArthurAvalonVfxData payload) {
      if (ClientVfxGate.allowed("avalon_divine_disintegration")) {
         if (payload.variant() != 2) {
            init();
            Minecraft minecraft = Minecraft.getInstance();
            if (minecraft.level != null && minecraft.player != null) {
               double maxDistance = 980.0;
               if (!(minecraft.player.distanceToSqr(payload.end()) > maxDistance * maxDistance)
                  || !(minecraft.player.distanceToSqr(payload.start()) > maxDistance * maxDistance)) {
                  CASTS.add(new AvalonDivineDisintegrationVfxClient.Cast(payload, Util.getMillis()));

                  while (CASTS.size() > 12) {
                     CASTS.remove(0);
                  }
               }
            }
         }
      }
   }

   private static void render(RenderLevelStageEvent event) {
      if (!ClientVfxGate.allowed("avalon_divine_disintegration")) {
         CASTS.clear();
      } else if (event.getStage() == Stage.AFTER_PARTICLES) {
         Minecraft minecraft = Minecraft.getInstance();
         if (minecraft.level != null && minecraft.player != null && !CASTS.isEmpty()) {
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
            BufferBuilder buffer = Tesselator.getInstance().begin(Mode.TRIANGLES, DefaultVertexFormat.POSITION_COLOR);
            boolean wrote = false;
            Iterator<AvalonDivineDisintegrationVfxClient.Cast> iterator = CASTS.iterator();

            while (iterator.hasNext()) {
               AvalonDivineDisintegrationVfxClient.Cast cast = iterator.next();
               float age = cast.ageTicks(now);
               if (cast.dead(age)) {
                  iterator.remove();
               } else {
                  cast.updateShake(minecraft, age);
                  wrote |= draw(buffer, matrix, camera, cast, age, now);
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

   private static boolean draw(BufferBuilder buffer, Matrix4f matrix, Vec3 camera, AvalonDivineDisintegrationVfxClient.Cast cast, float age, long now) {
      ArthurAvalonVfxData payload = cast.payload;
      Vec3 center = payload.end();
      Vec3 sky = payload.start();
      if (sky.y < center.y + 64.0) {
         sky = center.add(0.0, 220.0, 0.0);
      }

      float scale = Mth.clamp(payload.radius(), 12.0F, 120.0F);
      float time = (float)now * 0.001F;
      int detail = detailLevel(camera.distanceTo(center));
      return payload.variant() == 0
         ? charge(buffer, matrix, camera, center, sky, scale, age, payload.durationTicks(), time, payload.seed(), detail)
         : strike(buffer, matrix, camera, center, sky, scale, age, payload.durationTicks(), time, payload.seed(), detail);
   }

   private static boolean charge(
      BufferBuilder buffer, Matrix4f matrix, Vec3 camera, Vec3 center, Vec3 sky, float scale, float age, int durationTicks, float time, long seed, int detail
   ) {
      float total = Math.max(1.0F, durationTicks);
      float progress = Mth.clamp(age / total, 0.0F, 1.0F);
      Vec3 top = sky.y > center.y + 48.0 ? sky : center.add(0.0, 220.0, 0.0);
      boolean wrote = ringAscensionTower(buffer, matrix, center, top, scale, progress, acceleratingSpin(age, total), seed, detail, 1.0F);
      float chargeLive = 0.28F + progress * 0.72F;
      wrote |= columnMotes(buffer, matrix, camera, center, top, scale * 0.96F, chargeLive * 0.68F, age, seed ^ 18337L, detail);
      wrote |= convergingSparks(
         buffer,
         matrix,
         camera,
         center.add(0.0, scale * (0.18 + progress * 0.44), 0.0),
         scale * (0.96F + progress * 1.54F),
         chargeLive * 0.84F,
         age,
         seed ^ 18338L,
         detail
      );
      wrote |= softOrb(buffer, matrix, camera, top.add(0.0, -scale * 0.3, 0.0), scale * (0.58F + progress * 1.1F), progress * 0.34F, true);
      return wrote
         | starburst(
            buffer,
            matrix,
            camera,
            top.add(0.0, -scale * 0.26, 0.0),
            scale * (1.16F + progress * 1.48F),
            progress * 0.26F,
            -age * 0.026F,
            seed ^ 18339L,
            detail
         );
   }

   private static boolean ringAscensionTower(
      BufferBuilder buffer, Matrix4f matrix, Vec3 center, Vec3 top, float scale, float progress, float spin, long seed, int detail, float live
   ) {
      if (live <= 0.001F) {
         return false;
      }

      double usableHeight = Math.max(42.0, top.y - center.y - 8.0);
      int ringCount = detail >= 3 ? 7 : (detail == 2 ? 6 : 4);
      boolean wrote = false;

      for (int i = 0; i < ringCount; i++) {
         float f = ringCount <= 1 ? 0.0F : (float)i / (ringCount - 1);
         float threshold = 0.018F + f * 0.6F;
         float appear = smoother(Mth.clamp((progress - threshold) / 0.055F, 0.0F, 1.0F)) * live;
         if (!(appear <= 0.001F)) {
            float sizeCurve = (float)Math.pow(f, 1.14);
            float heightCurve = (float)Math.pow(f, 1.08);
            float radius = scale * (0.14F + sizeCurve * 1.34F);
            double y = center.y + 0.1 + usableHeight * (0.05 + heightCurve * 0.67);
            Vec3 ringCenter = new Vec3(center.x, y, center.z);
            float direction = (i & 1) == 0 ? 1.0F : -1.0F;
            float speed = 0.3F + f * 1.1F + i % 3 * 0.045F;
            float phase = hash(seed ^ 5700357409661599745L, 17000 + i * 41) * (float) (Math.PI * 2);
            int style = i * 2 % 6;
            wrote |= magicCircle(
               buffer,
               matrix,
               ringCenter,
               radius,
               phase + direction * spin * speed,
               appear,
               seed ^ 7146057691288625177L + i * -7046029254386353131L,
               detail,
               style
            );
         }
      }

      int crownCount = detail >= 3 ? 3 : 2;

      for (int i = 0; i < crownCount; i++) {
         float f = crownCount <= 1 ? 0.0F : (float)i / (crownCount - 1);
         float threshold = 0.6F + f * 0.22F;
         float appear = smoother(Mth.clamp((progress - threshold) / 0.065F, 0.0F, 1.0F)) * live;
         if (!(appear <= 0.001F)) {
            float radius = scale * (1.5F + f * 0.78F);
            double y = center.y + usableHeight * (0.76 + f * 0.14);
            Vec3 ringCenter = new Vec3(center.x, y, center.z);
            float direction = (i & 1) == 0 ? -1.0F : 1.0F;
            float phase = hash(seed ^ 3257665815644502181L, 18000 + i * 53) * (float) (Math.PI * 2);
            float speed = 1.16F + i * 0.24F;
            wrote |= magicCircle(
               buffer,
               matrix,
               ringCenter,
               radius,
               phase + direction * spin * speed,
               appear,
               seed ^ -7723592293110705685L + i * -4658895280553007687L,
               detail,
               (i + 1) % 6
            );
         }
      }

      return wrote;
   }

   private static float acceleratingSpin(float age, float total) {
      float p = Mth.clamp(age / Math.max(1.0F, total), 0.0F, 1.0F);
      float p2 = p * p;
      float p4 = p2 * p2;
      float p8 = p4 * p4;
      float p12 = p8 * p4;
      return age * (0.0032F + 0.0036F * p2) + total * (0.012F * p2 + 0.034F * p4 + 0.11F * p8 + 0.18F * p12);
   }

   private static boolean strike(
      BufferBuilder buffer, Matrix4f matrix, Vec3 camera, Vec3 center, Vec3 sky, float scale, float age, int durationTicks, float time, long seed, int detail
   ) {
      float total = Math.max(1.0F, durationTicks);
      float appear = smoother(Mth.clamp(age / 3.0F, 0.0F, 1.0F));
      float fade = 1.0F - smoother(Mth.clamp((age - total * 0.66F) / Math.max(1.0F, total * 0.34F), 0.0F, 1.0F));
      float live = appear * fade;
      float flash = impactEnvelope(age, 1.0F, 2.0F);
      float detonation = impactEnvelope(age, 3.0F, 7.6F);
      float secondary = impactEnvelope(age, 10.2F, 11.2F);
      float blast = Math.max(detonation, secondary * 0.72F);
      float after = 1.0F - smoother(Mth.clamp((age - 32.0F) / Math.max(1.0F, total - 32.0F), 0.0F, 1.0F));
      Vec3 ground = center.add(0.0, 0.04, 0.0);
      Vec3 top = sky.y > center.y ? sky : center.add(0.0, 220.0, 0.0);
      float width = scale * (0.52F + detonation * 0.3F);
      boolean wrote = false;
      wrote |= eternalBeam(buffer, matrix, camera, top, ground, width, live * 1.08F, time, seed ^ 13329L, detail);
      wrote |= beamFlameMantle(buffer, matrix, camera, top, ground, scale, width * 1.08F, live * 1.1F, age, seed ^ 23073L, detail);
      wrote |= beamSparks(buffer, matrix, camera, top, ground, scale, width * 1.02F, live * 1.08F, age, seed ^ 27444L, detail);
      wrote |= columnMotes(buffer, matrix, camera, center, top, scale * 1.02F, live * 0.44F, age, seed ^ 13072L, detail);
      float circleLive = live * (1.0F - smoother(Mth.clamp((age - 24.0F) / 18.0F, 0.0F, 1.0F)));
      wrote |= magicCircle(buffer, matrix, ground, scale * 1.08F, age * 0.028F, circleLive, seed ^ 28945L, detail, 0);
      wrote |= magicCircle(buffer, matrix, center.add(0.0, scale * 0.76, 0.0), scale * 0.84F, -age * 0.04F, circleLive * 0.74F, seed ^ 29218L, detail, 1);
      if (flash > 0.001F) {
         Vec3 focus = center.add(0.0, scale * 0.3, 0.0);
         wrote |= softOrb(buffer, matrix, camera, focus, scale * (3.8F + flash * 4.2F), flash, true);
         wrote |= starburst(buffer, matrix, camera, focus, scale * (2.4F + flash * 3.0F), flash, age * 0.16F, seed ^ 33041L, detail);
      }

      if (blast > 0.001F) {
         wrote |= meguminCore(buffer, matrix, camera, center, scale, age, blast, seed ^ 36865L, detail);
         wrote |= detonationLances(buffer, matrix, camera, center, scale, age, blast, seed ^ 36869L, detail);
         wrote |= explosionPetals(buffer, matrix, camera, center, scale, age, blast, seed ^ 36866L, detail);
         wrote |= explosionCloud(buffer, matrix, camera, center, scale, age, blast, seed ^ 36867L, detail);
         wrote |= explosionSparks(buffer, matrix, camera, center, scale, age, blast, seed ^ 36868L, detail);
         wrote |= softDisk(
            buffer, matrix, ground, scale * (1.1F + smoother(Mth.clamp(age / 16.0F, 0.0F, 1.0F)) * 5.0F), 255, 105, 4, blast * 0.14F, detail >= 2 ? 80 : 56
         );
         wrote |= softDisk(
            buffer,
            matrix,
            ground.add(0.0, 0.02, 0.0),
            scale * (0.72F + smoother(Mth.clamp(age / 14.0F, 0.0F, 1.0F)) * 3.5F),
            255,
            180,
            32,
            blast * 0.2F,
            detail >= 2 ? 76 : 52
         );
         wrote |= softDisk(
            buffer,
            matrix,
            ground.add(0.0, 0.04, 0.0),
            scale * (0.36F + smoother(Mth.clamp(age / 12.0F, 0.0F, 1.0F)) * 1.9F),
            255,
            248,
            208,
            blast * 0.3F,
            detail >= 2 ? 72 : 48
         );
         wrote |= starburst(
            buffer, matrix, camera, center.add(0.0, scale * 0.64, 0.0), scale * (3.4F + blast * 4.8F), blast * 0.84F, age * 0.12F, seed ^ 33042L, detail
         );
      }

      if (after > 0.001F) {
         wrote |= aftermath(buffer, matrix, camera, center, scale, age, after, seed ^ 40961L, detail);
      }

      return wrote;
   }

   private static boolean magicCircle(
      BufferBuilder buffer, Matrix4f matrix, Vec3 center, float radius, float rotation, float live, long seed, int detail, int style
   ) {
      if (!(live <= 0.001F) && !(radius <= 0.001F)) {
         boolean wrote = false;
         int segments = detail >= 3 ? 72 : (detail == 2 ? 56 : 40);
         float thick = radius * 0.01F;
         float rotA = rotation;
         float rotB = -rotation * 0.66F + 0.37F;
         float rotC = rotation * 1.28F - 0.21F;
         float rotD = -rotation * 1.52F + 0.58F;
         wrote |= ringHorizontal(buffer, matrix, center, radius, thick * 2.1F, rotA, 255, 105, 4, live * 0.16F, segments);
         wrote |= ringHorizontal(buffer, matrix, center.add(0.0, 0.004, 0.0), radius * 0.972F, thick * 1.05F, rotA, 255, 248, 208, live * 0.88F, segments);
         wrote |= ringHorizontal(buffer, matrix, center.add(0.0, 0.008, 0.0), radius * 0.895F, thick * 0.72F, rotB, 255, 224, 98, live * 0.58F, segments);
         wrote |= ringHorizontal(buffer, matrix, center.add(0.0, 0.012, 0.0), radius * 0.745F, thick * 0.62F, rotC, 255, 248, 208, live * 0.44F, segments);
         wrote |= ringHorizontal(buffer, matrix, center.add(0.0, 0.016, 0.0), radius * 0.515F, thick * 0.54F, rotD, 255, 224, 98, live * 0.38F, segments);
         int runes = detail >= 3 ? 24 : (detail == 2 ? 18 : 12);
         wrote |= runeBand(buffer, matrix, center.add(0.0, 0.02, 0.0), radius * 0.835F, radius * 0.08F, rotB, runes, live, seed ^ 21930L);
         wrote |= runeBand(
            buffer, matrix, center.add(0.0, 0.024, 0.0), radius * 0.625F, radius * 0.058F, rotD, Math.max(8, runes - 6), live * 0.72F, seed ^ 26299L
         );
         wrote |= arcNotches(
            buffer, matrix, center.add(0.0, 0.028, 0.0), radius * 0.925F, rotC, detail >= 3 ? 32 : (detail == 2 ? 24 : 16), thick * 0.72F, live * 0.64F
         );
         if (style == 0) {
            wrote |= polygon(buffer, matrix, center.add(0.0, 0.032, 0.0), radius * 0.705F, 6, rotB, thick * 0.82F, 255, 224, 98, live * 0.54F);
            wrote |= polygon(
               buffer, matrix, center.add(0.0, 0.036, 0.0), radius * 0.705F, 6, rotB + (float) (Math.PI / 6), thick * 0.54F, 255, 248, 208, live * 0.34F
            );
            wrote |= starPolygon(
               buffer, matrix, center.add(0.0, 0.04, 0.0), radius * 0.505F, radius * 0.225F, 6, rotC, thick * 0.68F, 255, 248, 208, live * 0.74F
            );
         } else if (style == 1) {
            wrote |= polygon(
               buffer, matrix, center.add(0.0, 0.032, 0.0), radius * 0.715F, 4, rotA + (float) (Math.PI / 4), thick * 0.8F, 255, 224, 98, live * 0.56F
            );
            wrote |= polygon(buffer, matrix, center.add(0.0, 0.036, 0.0), radius * 0.565F, 3, rotC, thick * 0.7F, 255, 248, 208, live * 0.72F);
            wrote |= polygon(buffer, matrix, center.add(0.0, 0.04, 0.0), radius * 0.565F, 3, rotC + (float) Math.PI, thick * 0.7F, 255, 248, 208, live * 0.72F);
         } else if (style == 2) {
            wrote |= polygon(buffer, matrix, center.add(0.0, 0.032, 0.0), radius * 0.715F, 8, rotB, thick * 0.7F, 255, 224, 98, live * 0.52F);
            wrote |= starPolygon(
               buffer, matrix, center.add(0.0, 0.036, 0.0), radius * 0.535F, radius * 0.245F, 8, rotC, thick * 0.62F, 255, 248, 208, live * 0.72F
            );
            wrote |= polygon(buffer, matrix, center.add(0.0, 0.04, 0.0), radius * 0.335F, 4, rotD, thick * 0.52F, 255, 224, 98, live * 0.46F);
         } else if (style == 3) {
            wrote |= polygon(buffer, matrix, center.add(0.0, 0.032, 0.0), radius * 0.72F, 12, rotA, thick * 0.68F, 255, 224, 98, live * 0.52F);
            wrote |= starPolygon(
               buffer, matrix, center.add(0.0, 0.036, 0.0), radius * 0.55F, radius * 0.265F, 12, rotB, thick * 0.58F, 255, 248, 208, live * 0.68F
            );
         } else if (style == 4) {
            wrote |= starPolygon(
               buffer, matrix, center.add(0.0, 0.032, 0.0), radius * 0.71F, radius * 0.44F, 10, rotA, thick * 0.68F, 255, 224, 98, live * 0.58F
            );
            wrote |= polygon(buffer, matrix, center.add(0.0, 0.036, 0.0), radius * 0.49F, 5, rotC, thick * 0.6F, 255, 248, 208, live * 0.7F);
            wrote |= polygon(buffer, matrix, center.add(0.0, 0.04, 0.0), radius * 0.31F, 10, rotD, thick * 0.46F, 255, 224, 98, live * 0.4F);
         } else {
            wrote |= polygon(buffer, matrix, center.add(0.0, 0.032, 0.0), radius * 0.72F, 7, rotB, thick * 0.72F, 255, 224, 98, live * 0.54F);
            wrote |= starPolygon(buffer, matrix, center.add(0.0, 0.036, 0.0), radius * 0.54F, radius * 0.2F, 7, rotD, thick * 0.6F, 255, 248, 208, live * 0.72F);
            wrote |= polygon(buffer, matrix, center.add(0.0, 0.04, 0.0), radius * 0.34F, 14, rotA, thick * 0.48F, 255, 224, 98, live * 0.42F);
         }

         return wrote | radialSigil(buffer, matrix, center.add(0.0, 0.044, 0.0), radius, rotA, style, thick, live);
      } else {
         return false;
      }
   }

   private static boolean arcNotches(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float radius, float rotation, int count, float width, float live) {
      boolean wrote = false;

      for (int i = 0; i < count; i++) {
         double a = rotation + i * (float) (Math.PI * 2) / count;
         Vec3 radial = new Vec3(Math.cos(a), 0.0, Math.sin(a));
         Vec3 tangent = new Vec3(-Math.sin(a), 0.0, Math.cos(a));
         Vec3 c = center.add(radial.scale(radius));
         float length = radius * (i % 4 == 0 ? 0.06F : 0.034F);
         wrote |= planarLine(
            buffer, matrix, c.subtract(tangent.scale(length)), c.add(tangent.scale(length)), width, 255, 248, 208, live * (i % 4 == 0 ? 0.76F : 0.46F)
         );
      }

      return wrote;
   }

   private static boolean radialSigil(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float radius, float rotation, int style, float width, float live) {
      int spokes = style == 3 ? 12 : (style == 2 ? 8 : (style == 4 ? 10 : (style == 5 ? 14 : 6)));
      boolean wrote = false;

      for (int i = 0; i < spokes; i++) {
         double a = rotation + i * (float) (Math.PI * 2) / spokes;
         double inner = radius * (0.12 + ((i & 1) == 0 ? 0.02 : 0.07));
         double outer = radius * (0.43 + (i % 3 == 0 ? 0.08 : 0.0));
         Vec3 p0 = center.add(Math.cos(a) * inner, 0.0, Math.sin(a) * inner);
         Vec3 p1 = center.add(Math.cos(a) * outer, 0.0, Math.sin(a) * outer);
         wrote |= planarLine(buffer, matrix, p0, p1, width * 0.48F, 255, 224, 98, live * 0.3F);
      }

      return wrote;
   }

   private static boolean runeBand(
      BufferBuilder buffer, Matrix4f matrix, Vec3 center, float radius, float runeSize, float rotation, int count, float live, long seed
   ) {
      boolean wrote = false;

      for (int i = 0; i < count; i++) {
         float h = hash(seed, i * 31 + 7);
         double a = rotation + i * (float) (Math.PI * 2) / count;
         Vec3 radial = new Vec3(Math.cos(a), 0.0, Math.sin(a));
         Vec3 tangent = new Vec3(-Math.sin(a), 0.0, Math.cos(a));
         Vec3 c = center.add(radial.scale(radius));
         float size = runeSize * (0.8F + h * 0.26F);
         float width = runeSize * 0.092F;
         int glyph = i & 3;
         if (glyph == 0) {
            Vec3 p0 = c.subtract(radial.scale(size * 0.46));
            Vec3 p1 = c.add(radial.scale(size * 0.46));
            Vec3 p2 = c.add(radial.scale(size * 0.1)).add(tangent.scale(size * 0.34));
            Vec3 p3 = c.add(radial.scale(size * 0.1)).subtract(tangent.scale(size * 0.34));
            wrote |= planarLine(buffer, matrix, p0, p1, width, 255, 248, 208, live * 0.68F);
            wrote |= planarLine(buffer, matrix, p2, p1, width * 0.78F, 255, 224, 98, live * 0.52F);
            wrote |= planarLine(buffer, matrix, p3, p1, width * 0.78F, 255, 224, 98, live * 0.52F);
         } else if (glyph == 1) {
            Vec3 p0 = c.subtract(radial.scale(size * 0.4)).subtract(tangent.scale(size * 0.28));
            Vec3 p1 = c.add(radial.scale(size * 0.38));
            Vec3 p2 = c.subtract(radial.scale(size * 0.4)).add(tangent.scale(size * 0.28));
            Vec3 mid = c.subtract(radial.scale(size * 0.06));
            wrote |= planarLine(buffer, matrix, p0, p1, width, 255, 248, 208, live * 0.66F);
            wrote |= planarLine(buffer, matrix, p1, p2, width, 255, 248, 208, live * 0.66F);
            wrote |= planarLine(
               buffer, matrix, mid.subtract(tangent.scale(size * 0.22)), mid.add(tangent.scale(size * 0.22)), width * 0.72F, 255, 224, 98, live * 0.48F
            );
         } else if (glyph == 2) {
            Vec3 p0 = c.subtract(radial.scale(size * 0.43));
            Vec3 p1 = c.add(radial.scale(size * 0.43));
            Vec3 left = c.add(tangent.scale(size * 0.31));
            Vec3 right = c.subtract(tangent.scale(size * 0.31));
            wrote |= planarLine(buffer, matrix, p0, p1, width, 255, 248, 208, live * 0.64F);
            wrote |= planarLine(buffer, matrix, p0, left, width * 0.78F, 255, 224, 98, live * 0.5F);
            wrote |= planarLine(buffer, matrix, left, p1, width * 0.78F, 255, 224, 98, live * 0.5F);
            wrote |= planarLine(buffer, matrix, p0, right, width * 0.78F, 255, 224, 98, live * 0.5F);
            wrote |= planarLine(buffer, matrix, right, p1, width * 0.78F, 255, 224, 98, live * 0.5F);
         } else {
            Vec3 top = c.add(radial.scale(size * 0.4));
            Vec3 bottom = c.subtract(radial.scale(size * 0.4));
            Vec3 left = c.subtract(tangent.scale(size * 0.3));
            Vec3 right = c.add(tangent.scale(size * 0.3));
            wrote |= planarLine(buffer, matrix, top, left, width, 255, 248, 208, live * 0.66F);
            wrote |= planarLine(buffer, matrix, left, bottom, width, 255, 248, 208, live * 0.66F);
            wrote |= planarLine(buffer, matrix, bottom, right, width, 255, 248, 208, live * 0.66F);
            wrote |= planarLine(buffer, matrix, right, top, width, 255, 248, 208, live * 0.66F);
            wrote |= planarLine(buffer, matrix, left, right, width * 0.68F, 255, 224, 98, live * 0.46F);
         }
      }

      return wrote;
   }

   private static boolean polygon(
      BufferBuilder buffer, Matrix4f matrix, Vec3 center, float radius, int sides, float rotation, float width, int r, int g, int b, float alpha
   ) {
      boolean wrote = false;

      for (int i = 0; i < sides; i++) {
         double a0 = rotation + i * (float) (Math.PI * 2) / sides;
         double a1 = rotation + (i + 1) * (float) (Math.PI * 2) / sides;
         Vec3 p0 = center.add(Math.cos(a0) * radius, 0.0, Math.sin(a0) * radius);
         Vec3 p1 = center.add(Math.cos(a1) * radius, 0.0, Math.sin(a1) * radius);
         wrote |= planarLine(buffer, matrix, p0, p1, width, r, g, b, alpha);
      }

      return wrote;
   }

   private static boolean starPolygon(
      BufferBuilder buffer, Matrix4f matrix, Vec3 center, float outer, float inner, int points, float rotation, float width, int r, int g, int b, float alpha
   ) {
      boolean wrote = false;
      int vertices = points * 2;

      for (int i = 0; i < vertices; i++) {
         double a0 = rotation + i * (float) (Math.PI * 2) / vertices;
         double a1 = rotation + (i + 1) * (float) (Math.PI * 2) / vertices;
         double r0 = (i & 1) == 0 ? outer : inner;
         double r1 = (i + 1 & 1) == 0 ? outer : inner;
         Vec3 p0 = center.add(Math.cos(a0) * r0, 0.0, Math.sin(a0) * r0);
         Vec3 p1 = center.add(Math.cos(a1) * r1, 0.0, Math.sin(a1) * r1);
         wrote |= planarLine(buffer, matrix, p0, p1, width, r, g, b, alpha);
      }

      return wrote;
   }

   private static boolean ringHorizontal(
      BufferBuilder buffer, Matrix4f matrix, Vec3 center, float radius, float thickness, float rotation, int r, int g, int b, float alpha, int segments
   ) {
      if (!(alpha <= 0.001F) && !(radius <= 0.001F)) {
         boolean wrote = false;
         float inner = Math.max(0.001F, radius - thickness * 0.5F);
         float outer = radius + thickness * 0.5F;

         for (int i = 0; i < segments; i++) {
            double a0 = rotation + i * (float) (Math.PI * 2) / segments;
            double a1 = rotation + (i + 1) * (float) (Math.PI * 2) / segments;
            Vec3 p0 = center.add(Math.cos(a0) * inner, 0.0, Math.sin(a0) * inner);
            Vec3 p1 = center.add(Math.cos(a1) * inner, 0.0, Math.sin(a1) * inner);
            Vec3 q1 = center.add(Math.cos(a1) * outer, 0.0, Math.sin(a1) * outer);
            Vec3 q0 = center.add(Math.cos(a0) * outer, 0.0, Math.sin(a0) * outer);
            wrote |= quad(buffer, matrix, p0, p1, q1, q0, r, g, b, alpha);
         }

         return wrote;
      } else {
         return false;
      }
   }

   private static boolean pulseWave(
      BufferBuilder buffer, Matrix4f matrix, Vec3 center, float radius, float thickness, int r, int g, int b, float alpha, int segments
   ) {
      return ringHorizontal(buffer, matrix, center, radius, thickness, 0.0F, r, g, b, alpha, segments);
   }

   private static boolean planarLine(BufferBuilder buffer, Matrix4f matrix, Vec3 start, Vec3 end, float width, int r, int g, int b, float alpha) {
      Vec3 dir = end.subtract(start);
      Vec3 side = new Vec3(-dir.z, 0.0, dir.x);
      if (side.lengthSqr() < 1.0E-8) {
         return false;
      }

      side = side.normalize().scale(width * 0.5);
      return quad(buffer, matrix, start.add(side), end.add(side), end.subtract(side), start.subtract(side), r, g, b, alpha);
   }

   private static boolean eternalBeam(
      BufferBuilder buffer, Matrix4f matrix, Vec3 camera, Vec3 top, Vec3 bottom, float width, float live, float time, long seed, int detail
   ) {
      if (live <= 0.001F) {
         return false;
      }

      Vec3 axis = bottom.subtract(top);
      if (axis.lengthSqr() < 1.0E-8) {
         return false;
      }

      Vec3 dir = axis.normalize();
      Vec3 baseA = safeSide(dir);
      Vec3 baseB = dir.cross(baseA).normalize();
      int planes = detail >= 3 ? 12 : (detail == 2 ? 10 : 8);
      boolean wrote = false;

      for (int i = 0; i < planes; i++) {
         double angle = i * Math.PI / planes;
         Vec3 lateral = baseA.scale(Math.cos(angle)).add(baseB.scale(Math.sin(angle))).normalize();
         wrote |= softBand(buffer, matrix, top, bottom, lateral, width * 1.9F, 255, 105, 4, live * 0.07F);
         wrote |= softBand(buffer, matrix, top, bottom, lateral, width * 1.32F, 255, 180, 32, live * 0.18F);
         wrote |= softBand(buffer, matrix, top, bottom, lateral, width * 0.82F, 255, 224, 98, live * 0.46F);
         wrote |= softBand(buffer, matrix, top, bottom, lateral, width * 0.42F, 255, 248, 208, live * 0.86F);
         wrote |= softBand(buffer, matrix, top, bottom, lateral, width * 0.16F, 255, 255, 255, live);
      }

      return wrote | softOrb(buffer, matrix, camera, bottom, width * 1.16F, live * 0.64F, true);
   }

   private static boolean beamFlameMantle(
      BufferBuilder buffer, Matrix4f matrix, Vec3 camera, Vec3 top, Vec3 bottom, float scale, float width, float live, float age, long seed, int detail
   ) {
      Vec3 axis = top.subtract(bottom);
      if (!(axis.lengthSqr() < 1.0E-8) && !(live <= 0.001F)) {
         Vec3 dir = axis.normalize();
         Vec3 side = safeSide(dir);
         Vec3 up = dir.cross(side).normalize();
         int count = detail >= 3 ? 44 : (detail == 2 ? 30 : 18);
         boolean wrote = false;

         for (int i = 0; i < count; i++) {
            float h0 = hash(seed, 6000 + i * 17);
            float h1 = hash(seed, 6100 + i * 19);
            float h2 = hash(seed, 6200 + i * 23);
            double u = 0.02 + h0 * 0.96;
            Vec3 anchor = bottom.lerp(top, u);
            double angle = h1 * (float) (Math.PI * 2) + age * (0.014 + h2 * 0.013);
            Vec3 radial = side.scale(Math.cos(angle)).add(up.scale(Math.sin(angle))).normalize();
            Vec3 tangent = dir.cross(radial).normalize();
            Vec3 origin = anchor.add(radial.scale(width * (0.54 + h2 * 0.32)));
            Vec3 flameDir = dir.scale(0.94).add(radial.scale(0.06 + h2 * 0.12)).normalize();
            wrote |= flameTongue(
               buffer,
               matrix,
               origin,
               flameDir,
               radial,
               tangent,
               scale * (0.56 + h1 * 1.54),
               width * (0.42 + h2 * 0.9),
               width * (0.05F + h1 * 0.07F),
               live * (0.18F + h2 * 0.26F),
               age * 0.04F,
               h0 * (float) (Math.PI * 2),
               detail >= 2 ? 14 : 9,
               i % 5 == 0 ? 255 : 255,
               i % 5 == 0 ? 248 : 180,
               i % 5 == 0 ? 208 : 32
            );
         }

         return wrote;
      } else {
         return false;
      }
   }

   private static boolean beamSparks(
      BufferBuilder buffer, Matrix4f matrix, Vec3 camera, Vec3 top, Vec3 bottom, float scale, float width, float live, float age, long seed, int detail
   ) {
      Vec3 axis = top.subtract(bottom);
      if (!(axis.lengthSqr() < 1.0E-8) && !(live <= 0.001F)) {
         Vec3 dir = axis.normalize();
         Vec3 side = safeSide(dir);
         Vec3 up = dir.cross(side).normalize();
         int count = detail >= 3 ? 220 : (detail == 2 ? 136 : 78);
         boolean wrote = false;

         for (int i = 0; i < count; i++) {
            float h0 = hash(seed, 7000 + i * 17);
            float h1 = hash(seed, 7100 + i * 19);
            float h2 = hash(seed, 7200 + i * 23);
            double u = (h0 + age * (0.006 + h2 * 0.006)) % 1.0;
            double angle = h1 * (float) (Math.PI * 2) + age * (0.01 + h0 * 0.01);
            double radial = width * (0.72 + h2 * 2.2);
            Vec3 pos = bottom.add(dir.scale(axis.length() * u)).add(side.scale(Math.cos(angle) * radial)).add(up.scale(Math.sin(angle) * radial));
            float blink = 0.34F + 0.66F * pulse01(age * (0.18F + h0 * 0.14F) + i * 0.31F);
            wrote |= goldMote(buffer, matrix, camera, pos, scale * (0.008F + h1 * 0.018F), live * blink * (0.36F + h2 * 0.38F));
         }

         return wrote;
      } else {
         return false;
      }
   }

   private static boolean meguminCore(
      BufferBuilder buffer, Matrix4f matrix, Vec3 camera, Vec3 center, float scale, float age, float live, long seed, int detail
   ) {
      float expand = smoother(Mth.clamp((age - 0.6F) / 12.0F, 0.0F, 1.0F));
      boolean wrote = false;
      Vec3 core = center.add(0.0, scale * (0.34 + expand * 0.56), 0.0);
      wrote |= softOrb(buffer, matrix, camera, core, scale * (5.8F + expand * 5.2F), live * 0.12F, false);
      wrote |= softOrb(buffer, matrix, camera, core, scale * (3.8F + expand * 3.8F), live * 0.26F, false);
      wrote |= softOrb(buffer, matrix, camera, core, scale * (2.4F + expand * 2.4F), live * 0.58F, true);
      wrote |= softOrb(buffer, matrix, camera, core, scale * (1.1F + expand * 1.08F), live, true);
      wrote |= starburst(buffer, matrix, camera, core, scale * (3.0F + expand * 3.2F), live * 0.86F, age * 0.08F, seed ^ 4112L, detail);
      return wrote | softDisk(buffer, matrix, center.add(0.0, 0.06, 0.0), scale * (0.56F + expand * 1.42F), 255, 248, 208, live * 0.16F, detail >= 2 ? 48 : 36);
   }

   private static boolean explosionPetals(
      BufferBuilder buffer, Matrix4f matrix, Vec3 camera, Vec3 center, float scale, float age, float live, long seed, int detail
   ) {
      int petals = detail >= 3 ? 26 : (detail == 2 ? 20 : 12);
      boolean wrote = false;
      float expand = smoother(Mth.clamp((age - 1.2F) / 16.0F, 0.0F, 1.0F));

      for (int i = 0; i < petals; i++) {
         float h0 = hash(seed, 8000 + i * 17);
         float h1 = hash(seed, 8100 + i * 19);
         float h2 = hash(seed, 8200 + i * 23);
         double angle = i * (float) (Math.PI * 2) / petals + h0 * 0.18;
         Vec3 radial = new Vec3(Math.cos(angle), 0.22 + h2 * 0.6, Math.sin(angle)).normalize();
         Vec3 side = safeSide(radial);
         Vec3 up = radial.cross(side).normalize();
         Vec3 origin = center.add(0.0, scale * (0.12 + h1 * 0.24), 0.0).add(radial.scale(scale * (0.16 + expand * 0.72)));
         wrote |= flameTongue(
            buffer,
            matrix,
            origin,
            radial,
            side,
            up,
            scale * (2.0 + h1 * 4.6 + expand * 6.2),
            scale * (0.72 + h2 * 1.9 + expand * 1.6),
            scale * (0.07F + h1 * 0.11F),
            live * (0.22F + h2 * 0.34F),
            age * 0.06F,
            h0 * (float) (Math.PI * 2),
            detail >= 2 ? 15 : 10,
            i % 4 == 0 ? 255 : 255,
            i % 4 == 0 ? 248 : 180,
            i % 4 == 0 ? 208 : 32
         );
      }

      return wrote;
   }

   private static boolean explosionCloud(
      BufferBuilder buffer, Matrix4f matrix, Vec3 camera, Vec3 center, float scale, float age, float live, long seed, int detail
   ) {
      int count = detail >= 3 ? 28 : (detail == 2 ? 20 : 14);
      boolean wrote = false;
      float rise = smoother(Mth.clamp((age - 1.8F) / 28.0F, 0.0F, 1.0F));

      for (int i = 0; i < count; i++) {
         float h0 = hash(seed, 9000 + i * 17);
         float h1 = hash(seed, 9100 + i * 19);
         float h2 = hash(seed, 9200 + i * 23);
         double angle = h0 * (float) (Math.PI * 2);
         double radial = scale * (0.12 + h1 * (1.1 + rise * 2.4));
         double y = scale * (0.18 + h2 * (1.3 + rise * 4.6));
         Vec3 p = center.add(Math.cos(angle) * radial, y, Math.sin(angle) * radial);
         float size = scale * (0.76F + h2 * 1.2F + rise * 1.24F);
         float alpha = live * (0.34F + h1 * 0.34F);
         wrote |= softOrb(buffer, matrix, camera, p, size * 2.1F, alpha * 0.08F, false);
         wrote |= softOrb(buffer, matrix, camera, p, size * 1.34F, alpha * 0.18F, false);
         wrote |= softOrb(buffer, matrix, camera, p, size * 0.76F, alpha * 0.4F, true);
      }

      return wrote;
   }

   private static boolean explosionSparks(
      BufferBuilder buffer, Matrix4f matrix, Vec3 camera, Vec3 center, float scale, float age, float live, long seed, int detail
   ) {
      int count = detail >= 3 ? 220 : (detail == 2 ? 140 : 84);
      boolean wrote = false;
      float travel = smoother(Mth.clamp((age - 0.8F) / 24.0F, 0.0F, 1.0F));

      for (int i = 0; i < count; i++) {
         float h0 = hash(seed, 10000 + i * 17);
         float h1 = hash(seed, 10100 + i * 19);
         float h2 = hash(seed, 10200 + i * 23);
         double angle = h0 * (float) (Math.PI * 2);
         double radial = scale * (0.16 + h1 * 8.8) * travel;
         double y = scale * (0.08 + h2 * 6.8) * travel;
         Vec3 p = center.add(Math.cos(angle) * radial, y, Math.sin(angle) * radial);
         float blink = 0.32F + 0.68F * pulse01(age * (0.24F + h1 * 0.16F) + i * 0.33F);
         wrote |= goldMote(buffer, matrix, camera, p, scale * (0.008F + h2 * 0.028F), live * blink * (0.42F + h1 * 0.38F));
      }

      return wrote;
   }

   private static boolean aftermath(BufferBuilder buffer, Matrix4f matrix, Vec3 camera, Vec3 center, float scale, float age, float live, long seed, int detail) {
      boolean wrote = false;
      int count = detail >= 3 ? 72 : (detail == 2 ? 46 : 28);

      for (int i = 0; i < count; i++) {
         float h0 = hash(seed, 11000 + i * 17);
         float h1 = hash(seed, 11100 + i * 19);
         float h2 = hash(seed, 11200 + i * 23);
         double angle = h0 * (float) (Math.PI * 2) + age * (0.008 + h1 * 0.01);
         double radial = scale * (0.7 + h1 * 4.6);
         double y = scale * (0.1 + h2 * 3.0) + age * scale * (0.002 + h2 * 0.0024);
         Vec3 p = center.add(Math.cos(angle) * radial, y, Math.sin(angle) * radial);
         wrote |= goldMote(buffer, matrix, camera, p, scale * (0.01F + h2 * 0.024F), live * (0.22F + h1 * 0.38F));
      }

      Vec3 top = center.add(0.0, scale * 9.0, 0.0);
      return wrote | columnMotes(buffer, matrix, camera, center, top, scale * 0.84F, live * 0.22F, age, seed ^ 40962L, detail);
   }

   private static boolean columnMotes(
      BufferBuilder buffer, Matrix4f matrix, Vec3 camera, Vec3 center, Vec3 top, float scale, float live, float age, long seed, int detail
   ) {
      int count = detail >= 3 ? 110 : (detail == 2 ? 72 : 42);
      boolean wrote = false;
      double height = top.y - center.y;

      for (int i = 0; i < count; i++) {
         float h0 = hash(seed, 12000 + i * 17);
         float h1 = hash(seed, 12100 + i * 19);
         float h2 = hash(seed, 12200 + i * 23);
         double cycle = (h0 + age * (0.004 + h2 * 0.005)) % 1.0;
         double angle = h1 * (float) (Math.PI * 2) + age * (0.012 + h2 * 0.01);
         double radial = scale * (0.6 + h2 * 1.5) * (1.0 - cycle * 0.38);
         Vec3 p = center.add(Math.cos(angle) * radial, cycle * height, Math.sin(angle) * radial);
         wrote |= goldMote(buffer, matrix, camera, p, scale * (0.008F + h2 * 0.018F), live * (0.18F + h1 * 0.42F));
      }

      return wrote;
   }

   private static boolean detonationLances(
      BufferBuilder buffer, Matrix4f matrix, Vec3 camera, Vec3 center, float scale, float age, float live, long seed, int detail
   ) {
      int count = detail >= 3 ? 24 : (detail == 2 ? 16 : 10);
      boolean wrote = false;
      float expand = smoother(Mth.clamp((age - 1.0F) / 12.0F, 0.0F, 1.0F));
      Vec3 origin = center.add(0.0, scale * (0.34 + expand * 0.32), 0.0);

      for (int i = 0; i < count; i++) {
         float h0 = hash(seed, 15000 + i * 17);
         float h1 = hash(seed, 15100 + i * 19);
         float h2 = hash(seed, 15200 + i * 23);
         double angle = i * (float) (Math.PI * 2) / count + h0 * 0.24;
         Vec3 dir = new Vec3(Math.cos(angle), 0.24 + h2 * 0.68, Math.sin(angle)).normalize();
         Vec3 start = origin.add(dir.scale(scale * (0.18 + expand * 0.52)));
         Vec3 end = origin.add(dir.scale(scale * (3.8 + h1 * 6.4 + expand * 8.0))).add(0.0, scale * (0.6 + h2 * 1.8), 0.0);
         double width = scale * (0.12 + h2 * 0.16);
         wrote |= beamQuad(buffer, matrix, camera, start, end, width * 2.1, 255, 105, 4, live * 0.1F);
         wrote |= beamQuad(buffer, matrix, camera, start, end, width * 1.08, 255, 180, 32, live * 0.26F);
         wrote |= beamQuad(buffer, matrix, camera, start, end, width * 0.4, 255, 248, 208, live * 0.84F);
      }

      return wrote;
   }

   private static boolean convergingSparks(
      BufferBuilder buffer, Matrix4f matrix, Vec3 camera, Vec3 focus, float scale, float live, float age, long seed, int detail
   ) {
      int count = detail >= 3 ? 128 : (detail == 2 ? 82 : 48);
      boolean wrote = false;

      for (int i = 0; i < count; i++) {
         float h0 = hash(seed, 13000 + i * 17);
         float h1 = hash(seed, 13100 + i * 19);
         float h2 = hash(seed, 13200 + i * 23);
         double cycle = (h0 + age * (0.01 + h2 * 0.01)) % 1.0;
         float t = smoother((float)cycle);
         double angle = h1 * (float) (Math.PI * 2) + age * (0.01 + h0 * 0.014);
         double radius = scale * (1.6 + h2 * 1.7) * (1.0 - t);
         double y = scale * (h2 * 1.8 - 0.7) * (1.0 - t);
         Vec3 p = focus.add(Math.cos(angle) * radius, y, Math.sin(angle) * radius);
         wrote |= goldMote(buffer, matrix, camera, p, scale * (0.008F + h2 * 0.02F), live * (0.24F + h1 * 0.5F));
      }

      return wrote;
   }

   private static boolean starburst(
      BufferBuilder buffer, Matrix4f matrix, Vec3 camera, Vec3 center, float size, float live, float rotation, long seed, int detail
   ) {
      if (live <= 0.001F) {
         return false;
      }

      int rays = detail >= 3 ? 28 : (detail == 2 ? 20 : 14);
      boolean wrote = false;

      for (int i = 0; i < rays; i++) {
         float h = hash(seed, 14000 + i * 19);
         double a = rotation + i * (float) (Math.PI * 2) / rays;
         Vec3 dir = new Vec3(Math.cos(a), (h - 0.5) * 0.36, Math.sin(a)).normalize();
         Vec3 a0 = center.add(dir.scale(size * 0.12));
         Vec3 b0 = center.add(dir.scale(size * (0.72 + h * 0.72)));
         wrote |= beamQuad(buffer, matrix, camera, a0, b0, size * (0.045F + h * 0.035F), 255, 180, 32, live * 0.18F);
         wrote |= beamQuad(buffer, matrix, camera, a0, b0, size * (0.014F + h * 0.012F), 255, 248, 208, live * 0.78F);
      }

      return wrote;
   }

   private static boolean flameTongue(
      BufferBuilder buffer,
      Matrix4f matrix,
      Vec3 origin,
      Vec3 forward,
      Vec3 radial,
      Vec3 tangent,
      double length,
      double flare,
      float width,
      float live,
      float time,
      double phase,
      int segments,
      int r,
      int g,
      int b
   ) {
      if (live <= 0.001F) {
         return false;
      }

      boolean wrote = false;
      Vec3 previous = origin;

      for (int i = 1; i <= segments; i++) {
         double t = (double)i / segments;
         double taper = 1.0 - t;
         double waveA = Math.sin(phase + t * 8.2 + time * 2.2) * flare * taper * 0.18;
         double waveB = Math.cos(phase * 1.31 + t * 6.4 - time * 1.6) * flare * taper * 0.12;
         Vec3 current = origin.add(forward.scale(length * t)).add(radial.scale(waveA)).add(tangent.scale(waveB));
         float local = live * (float)Math.pow(taper, 0.44);
         wrote |= softBand(buffer, matrix, previous, current, tangent, width * (float)(0.7 + taper * 1.3), r, g, b, local * 0.16F);
         wrote |= softBand(buffer, matrix, previous, current, tangent, width * (float)(0.3 + taper * 0.5), 255, 248, 208, local * 0.54F);
         previous = current;
      }

      return wrote;
   }

   private static boolean softOrb(BufferBuilder buffer, Matrix4f matrix, Vec3 camera, Vec3 center, float radius, float alpha, boolean whiteCore) {
      if (alpha <= 0.001F) {
         return false;
      }

      Vec3 view = camera.subtract(center);
      if (view.lengthSqr() < 1.0E-8) {
         view = new Vec3(0.0, 0.0, 1.0);
      }

      view = view.normalize();
      Vec3 right = safeSide(view);
      Vec3 up = view.cross(right).normalize();
      boolean wrote = false;
      wrote |= billboardDisc(buffer, matrix, center, right, up, radius * 1.85F, 255, 105, 4, alpha * 0.08F, 20);
      wrote |= billboardDisc(buffer, matrix, center, right, up, radius * 1.28F, 255, 180, 32, alpha * 0.18F, 20);
      wrote |= billboardDisc(buffer, matrix, center, right, up, radius * 0.86F, 255, 224, 98, alpha * 0.42F, 18);
      wrote |= billboardDisc(buffer, matrix, center, right, up, radius * 0.52F, 255, 248, 208, alpha * 0.72F, 16);
      if (whiteCore) {
         wrote |= billboardDisc(buffer, matrix, center, right, up, radius * 0.22F, 255, 255, 255, alpha, 14);
      }

      return wrote;
   }

   private static boolean softDisk(BufferBuilder buffer, Matrix4f matrix, Vec3 center, double radius, int r, int g, int b, float alpha, int segments) {
      if (!(alpha <= 0.001F) && !(radius <= 0.001)) {
         boolean wrote = false;
         Vec3 c = center;

         for (int i = 0; i < segments; i++) {
            double a0 = i * (float) (Math.PI * 2) / segments;
            double a1 = (i + 1) * (float) (Math.PI * 2) / segments;
            Vec3 p0 = center.add(Math.cos(a0) * radius, 0.0, Math.sin(a0) * radius);
            Vec3 p1 = center.add(Math.cos(a1) * radius, 0.0, Math.sin(a1) * radius);
            vertex(buffer, matrix, c, r, g, b, alpha);
            vertex(buffer, matrix, p0, r, g, b, alpha * 0.18F);
            vertex(buffer, matrix, p1, r, g, b, alpha * 0.18F);
            wrote = true;
         }

         return wrote;
      } else {
         return false;
      }
   }

   private static boolean billboardDisc(
      BufferBuilder buffer, Matrix4f matrix, Vec3 center, Vec3 right, Vec3 up, float radius, int r, int g, int b, float alpha, int segments
   ) {
      if (!(alpha <= 0.001F) && !(radius <= 0.001F)) {
         boolean wrote = false;
         int count = Math.max(8, segments);

         for (int i = 0; i < count; i++) {
            double a0 = i * (float) (Math.PI * 2) / count;
            double a1 = (i + 1) * (float) (Math.PI * 2) / count;
            Vec3 p0 = center.add(right.scale(Math.cos(a0) * radius)).add(up.scale(Math.sin(a0) * radius));
            Vec3 p1 = center.add(right.scale(Math.cos(a1) * radius)).add(up.scale(Math.sin(a1) * radius));
            vertex(buffer, matrix, center, r, g, b, alpha);
            vertex(buffer, matrix, p0, r, g, b, alpha * 0.12F);
            vertex(buffer, matrix, p1, r, g, b, alpha * 0.12F);
            wrote = true;
         }

         return wrote;
      } else {
         return false;
      }
   }

   private static boolean billboardDiamond(
      BufferBuilder buffer, Matrix4f matrix, Vec3 center, Vec3 right, Vec3 up, float size, int r, int g, int b, float alpha
   ) {
      Vec3 a = center.add(right.scale(size));
      Vec3 b0 = center.add(up.scale(size));
      Vec3 c = center.subtract(right.scale(size));
      Vec3 d = center.subtract(up.scale(size));
      return quad(buffer, matrix, a, b0, c, d, r, g, b, alpha);
   }

   private static boolean goldMote(BufferBuilder buffer, Matrix4f matrix, Vec3 camera, Vec3 pos, float size, float alpha) {
      if (alpha <= 0.001F) {
         return false;
      }

      Vec3 view = camera.subtract(pos);
      if (view.lengthSqr() < 1.0E-8) {
         view = new Vec3(0.0, 0.0, 1.0);
      }

      view = view.normalize();
      Vec3 right = safeSide(view);
      Vec3 up = view.cross(right).normalize();
      boolean wrote = billboardDiamond(buffer, matrix, pos, right, up, size * 2.2F, 255, 180, 32, alpha * 0.24F);
      return wrote | billboardDiamond(buffer, matrix, pos, right, up, size, 255, 248, 208, alpha);
   }

   private static boolean softBand(BufferBuilder buffer, Matrix4f matrix, Vec3 start, Vec3 end, Vec3 lateral, float width, int r, int g, int b, float alpha) {
      if (!(alpha <= 0.001F) && !(lateral.lengthSqr() < 1.0E-8)) {
         Vec3 side = lateral.normalize().scale(width);
         return quad(buffer, matrix, start.add(side), end.add(side), end.subtract(side), start.subtract(side), r, g, b, alpha);
      } else {
         return false;
      }
   }

   private static boolean beamQuad(BufferBuilder buffer, Matrix4f matrix, Vec3 camera, Vec3 start, Vec3 end, double width, int r, int g, int b, float alpha) {
      Vec3 dir = end.subtract(start);
      if (!(dir.lengthSqr() < 1.0E-8) && !(alpha <= 0.001F)) {
         Vec3 mid = start.add(end).scale(0.5);
         Vec3 toCam = camera.subtract(mid);
         Vec3 side = dir.cross(toCam);
         if (side.lengthSqr() < 1.0E-8) {
            side = safeSide(dir);
         }

         side = side.normalize().scale(width);
         return quad(buffer, matrix, start.add(side), end.add(side), end.subtract(side), start.subtract(side), r, g, b, alpha);
      } else {
         return false;
      }
   }

   private static boolean quad(BufferBuilder buffer, Matrix4f matrix, Vec3 a, Vec3 b, Vec3 c, Vec3 d, int r, int g, int bl, float alpha) {
      if (alpha <= 0.001F) {
         return false;
      }

      vertex(buffer, matrix, a, r, g, bl, alpha);
      vertex(buffer, matrix, b, r, g, bl, alpha);
      vertex(buffer, matrix, c, r, g, bl, alpha);
      vertex(buffer, matrix, a, r, g, bl, alpha);
      vertex(buffer, matrix, c, r, g, bl, alpha);
      vertex(buffer, matrix, d, r, g, bl, alpha);
      return true;
   }

   private static void vertex(BufferBuilder buffer, Matrix4f matrix, Vec3 p, int r, int g, int b, float alpha) {
      int a = Mth.clamp((int)(alpha * 255.0F), 0, 255);
      buffer.addVertex(matrix, (float)p.x, (float)p.y, (float)p.z).setColor(r, g, b, a);
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

   private static float smoother(float x) {
      x = Mth.clamp(x, 0.0F, 1.0F);
      return x * x * x * (x * (x * 6.0F - 15.0F) + 10.0F);
   }

   private static float impactEnvelope(float age, float center, float width) {
      float x = Math.abs(age - center) / Math.max(0.001F, width);
      return 1.0F - smoother(Mth.clamp(x, 0.0F, 1.0F));
   }

   private static float pulse01(float x) {
      return 0.5F + 0.5F * Mth.sin(x);
   }

   private static float hash(long seed, int value) {
      long x = seed ^ value * -7046029254386353131L;
      x ^= x >>> 30;
      x *= -4658895280553007687L;
      x ^= x >>> 27;
      x *= -7723592293110705685L;
      x ^= x >>> 31;
      return (float)(x & 16777215L) / 1.6777215E7F;
   }

   private static int detailLevel(double distance) {
      if (distance < 72.0) {
         return 3;
      } else {
         return distance < 160.0 ? 2 : 1;
      }
   }

   private static final class Cast {
      private final ArthurAvalonVfxData payload;
      private final long born;
      private boolean impactShake;
      private boolean afterShake;

      private Cast(ArthurAvalonVfxData payload, long born) {
         this.payload = payload;
         this.born = born;
      }

      private float ageTicks(long now) {
         return (float)(now - this.born) / 50.0F;
      }

      private boolean dead(float age) {
         return age > this.payload.durationTicks() + 8.0F;
      }

      private void updateShake(Minecraft minecraft, float age) {
         if (minecraft.player != null) {
            double distance = minecraft.player.position().distanceTo(this.payload.end());
            float proximity = 1.0F - Mth.clamp((float)(distance / 300.0), 0.0F, 1.0F);
            if (this.payload.variant() != 0) {
               if (this.payload.variant() == 1) {
                  if (!this.impactShake && age >= 1.0F) {
                     this.impactShake = true;
                     PathsClientState.triggerManualShake(44, 12.0F + proximity * 16.0F, 15.0F + proximity * 6.0F);
                  }

                  if (!this.afterShake && age >= 8.0F) {
                     this.afterShake = true;
                     PathsClientState.triggerManualShake(34, 7.0F + proximity * 9.0F, 11.0F + proximity * 4.0F);
                  }
               }
            }
         }
      }
   }
}
