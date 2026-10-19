package com.github.wal_bos.moreskills.client.fx.arthur;

import com.github.wal_bos.moreskills.client.fx.ClientVfxGate;
import com.github.wal_bos.moreskills.net.msg.ArthurAvalonVfxData;
import com.mojang.blaze3d.platform.GlStateManager.DestFactor;
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.Util;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent.Stage;
import net.neoforged.neoforge.common.NeoForge;
import org.joml.Matrix4f;

public final class ArthurKingsBattleVfxClient {
   private static final Map<Integer, ArthurKingsBattleVfxClient.Arena> ARENAS = new LinkedHashMap<>();
   private static final long ARENA_LIFETIME_MS = 30000L;
   private static final long REFRESH_SMOOTH_MS = 180L;
   private static final int MAX_ARENAS = 4;
   private static boolean initialized;

   private ArthurKingsBattleVfxClient() {
   }

   public static void init() {
      if (!initialized) {
         initialized = true;
         NeoForge.EVENT_BUS.addListener(ArthurKingsBattleVfxClient::render);
      }
   }

   public static void spawn(ArthurAvalonVfxData payload) {
      if (ClientVfxGate.allowed("arthur_kings_battle")) {
         init();
         Minecraft mc = Minecraft.getInstance();
         if (mc.level != null && mc.player != null) {
            if (!(mc.player.distanceToSqr(payload.start()) > 176400.0)) {
               long now = Util.getMillis();
               int sourceId = payload.sourceId();
               ArthurKingsBattleVfxClient.Arena arena = ARENAS.get(sourceId);
               if (arena == null) {
                  ARENAS.put(sourceId, new ArthurKingsBattleVfxClient.Arena(payload, now));
               } else {
                  arena.refresh(payload, now);
               }

               while (ARENAS.size() > 4) {
                  ARENAS.remove(ARENAS.keySet().iterator().next());
               }
            }
         }
      }
   }

   private static void render(RenderLevelStageEvent event) {
      if (!ClientVfxGate.allowed("arthur_kings_battle")) {
         ARENAS.clear();
      } else if (event.getStage() == Stage.AFTER_PARTICLES) {
         Minecraft mc = Minecraft.getInstance();
         if (mc.level != null && mc.player != null && !ARENAS.isEmpty()) {
            long now = Util.getMillis();
            Camera cameraObject = mc.gameRenderer.getMainCamera();
            Vec3 camera = cameraObject.getPosition();
            event.getPoseStack().pushPose();
            event.getPoseStack().translate(-camera.x, -camera.y, -camera.z);
            Matrix4f matrix = event.getPoseStack().last().pose();
            BufferBuilder buffer = begin();
            boolean wrote = false;
            Iterator<Entry<Integer, ArthurKingsBattleVfxClient.Arena>> iterator = ARENAS.entrySet().iterator();

            while (iterator.hasNext()) {
               ArthurKingsBattleVfxClient.Arena arena = iterator.next().getValue();
               if (arena.dead(now)) {
                  iterator.remove();
               } else {
                  wrote |= arena.render(buffer, matrix, camera, now);
               }
            }

            finish(buffer, wrote);
            event.getPoseStack().popPose();
         }
      }
   }

   private static boolean royalGroundSeal(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float radius, float live, float pulse, float time, int detail) {
      boolean wrote = false;
      wrote |= groundRing(buffer, matrix, center, radius, Math.max(0.045F, radius * 0.008F), detail >= 3 ? 42 : 30, time * 0.06, 255, 247, 210, live * 0.74F);
      wrote |= groundRing(
         buffer,
         matrix,
         center.add(0.0, 0.012, 0.0),
         radius * 0.91,
         Math.max(0.03F, radius * 0.005F),
         detail >= 3 ? 38 : 28,
         -time * 0.09,
         255,
         218,
         84,
         live * 0.42F
      );
      wrote |= groundRing(
         buffer,
         matrix,
         center.add(0.0, 0.022, 0.0),
         radius * 0.63,
         Math.max(0.025F, radius * 0.004F),
         detail >= 3 ? 34 : 24,
         time * 0.11,
         255,
         237,
         158,
         live * 0.3F
      );
      wrote |= groundRing(
         buffer,
         matrix,
         center.add(0.0, 0.03, 0.0),
         radius * 0.33,
         Math.max(0.02F, radius * 0.0035F),
         detail >= 3 ? 30 : 20,
         -time * 0.15,
         255,
         252,
         231,
         live * 0.46F
      );
      int spokes = detail >= 3 ? 12 : (detail == 2 ? 8 : 6);

      for (int i = 0; i < spokes; i++) {
         double angle = i * (float) (Math.PI * 2) / spokes + time * 0.025;
         Vec3 inner = center.add(Math.cos(angle + Math.PI / spokes) * radius * 0.18, 0.036, Math.sin(angle + Math.PI / spokes) * radius * 0.18);
         Vec3 outer = center.add(Math.cos(angle) * radius * 0.87, 0.036, Math.sin(angle) * radius * 0.87);
         wrote |= groundRibbon(buffer, matrix, inner, outer, Math.max(0.028F, radius * 0.006F), 255, 226, 116, live * 0.25F);
         wrote |= groundRibbon(
            buffer, matrix, inner.add(0.0, 0.006, 0.0), outer.add(0.0, 0.006, 0.0), Math.max(0.012F, radius * 0.0025F), 255, 252, 230, live * 0.52F
         );
      }

      float breathing = 0.92F + pulse * 0.08F;
      return wrote | groundDisk(buffer, matrix, center.add(0.0, 0.012, 0.0), radius * 0.99 * breathing, detail >= 3 ? 40 : 28, 255, 185, 22, live * 0.035F);
   }

   private static boolean radiantBoundary(
      BufferBuilder buffer, Matrix4f matrix, Vec3 center, float radius, float baseScale, float live, float pulse, float time, int detail
   ) {
      boolean wrote = false;
      int pillars = detail >= 3 ? 16 : (detail == 2 ? 12 : 8);
      double height = baseScale * (0.86 + pulse * 0.14);

      for (int i = 0; i < pillars; i++) {
         double angle = i * (float) (Math.PI * 2) / pillars + time * 0.018;
         Vec3 radial = new Vec3(Math.cos(angle), 0.0, Math.sin(angle));
         Vec3 base = center.add(radial.scale(radius));
         Vec3 top = base.add(0.0, height * (0.76 + i % 4 * 0.055), 0.0);
         float localPulse = 0.72F + 0.28F * pulse01(time * (2.8F + i % 5 * 0.3F) + i * 0.41F);
         wrote |= ribbon(
            buffer,
            matrix,
            base,
            top,
            Math.max(0.035F, baseScale * 0.01F),
            Math.max(0.01F, baseScale * 0.003F),
            255,
            226,
            114,
            live * localPulse * 0.24F,
            live * 0.025F
         );
         wrote |= ribbon(
            buffer,
            matrix,
            base.add(radial.scale(-0.025)),
            top,
            Math.max(0.012F, baseScale * 0.0035F),
            Math.max(0.003F, baseScale * 0.001F),
            255,
            253,
            231,
            live * localPulse * 0.48F,
            live * 0.02F
         );
      }

      int bands = detail >= 3 ? 4 : (detail == 2 ? 3 : 2);

      for (int i = 0; i < bands; i++) {
         double y = height * (0.22 + i * 0.19);
         double bandRadius = radius * (0.97 - i * 0.012);
         wrote |= horizontalRing(
            buffer,
            matrix,
            center.add(0.0, y, 0.0),
            bandRadius,
            Math.max(0.022F, baseScale * 0.004F),
            detail >= 3 ? 38 : 26,
            time * (0.05 + i * 0.025) * (i % 2 == 0 ? 1.0 : -1.0),
            255,
            220 + i * 7,
            95 + i * 24,
            live * (0.13F + i * 0.035F)
         );
      }

      return wrote;
   }

   private static boolean risingLightOrbs(
      BufferBuilder buffer, Matrix4f matrix, Vec3 center, float arenaRadius, float scale, float live, float time, long seed, int detail
   ) {
      boolean wrote = false;
      int count = detail >= 3 ? 58 : (detail == 2 ? 36 : 20);

      for (int i = 0; i < count; i++) {
         float h0 = hash(seed, 14000 + i * 17);
         float h1 = hash(seed, 14100 + i * 19);
         float h2 = hash(seed, 14200 + i * 23);
         float h3 = hash(seed, 14300 + i * 29);
         double angle = h0 * (float) (Math.PI * 2) + time * (0.016 + h2 * 0.018);
         double radial = arenaRadius * Math.sqrt(0.08 + h1 * 0.86);
         double cycle = (time * (0.085 + h0 * 0.055) + h2 * 8.0) % 1.0;
         double rise = scale * (0.12 + h3 * 0.28) + cycle * scale * (0.42 + h2 * 0.66);
         Vec3 p = center.add(Math.cos(angle) * radial, rise, Math.sin(angle) * radial);
         float blink = 0.18F + 0.82F * pulse01(time * (7.0F + h1 * 8.0F) + i * 0.37F);
         float fade = 1.0F - (float)cycle * 0.3F;
         float size = scale * (0.01F + h3 * 0.025F) * (0.82F + blink * 0.5F);
         wrote |= orb(buffer, matrix, p, size * 1.75F, 255, 171, 16, live * blink * fade * 0.11F);
         wrote |= orb(buffer, matrix, p, size, 255, 224 + (int)(h0 * 28.0F), 102 + (int)(h1 * 92.0F), live * blink * fade * 0.76F);
         if (h2 > 0.42F) {
            Vec3 lower = p.add(0.0, -scale * (0.08 + h3 * 0.16), 0.0);
            wrote |= ribbon(
               buffer, matrix, lower, p, Math.max(0.006F, size * 0.24F), Math.max(0.0015F, size * 0.05F), 255, 211, 72, live * blink * fade * 0.2F, 0.0F
            );
         }
      }

      return wrote;
   }

   private static boolean holyGroundAura(
      BufferBuilder buffer, Matrix4f matrix, Vec3 center, float arenaRadius, float scale, float live, float time, long seed, int detail
   ) {
      boolean wrote = false;
      int motes = detail >= 3 ? 34 : (detail == 2 ? 22 : 12);

      for (int i = 0; i < motes; i++) {
         float h0 = hash(seed, 16000 + i * 31);
         float h1 = hash(seed, 16100 + i * 37);
         float h2 = hash(seed, 16200 + i * 41);
         double angle = h0 * (float) (Math.PI * 2) + time * (0.035 + h1 * 0.025);
         double radial = arenaRadius * (0.17 + h1 * 0.76);
         double cycle = (time * (0.045 + h2 * 0.035) + h0 * 9.0) % 1.0;
         Vec3 base = center.add(Math.cos(angle) * radial, 0.05, Math.sin(angle) * radial);
         Vec3 p = base.add(0.0, scale * (0.05 + cycle * (0.22 + h2 * 0.24)), 0.0);
         float blink = 0.28F + 0.72F * pulse01(time * (5.5F + h0 * 5.0F) + i);
         float size = scale * (0.008F + h1 * 0.015F);
         wrote |= orb(buffer, matrix, p, size, 255, 238, 165, live * blink * 0.54F);
      }

      return wrote;
   }

   private static boolean verticalGoldWisps(
      BufferBuilder buffer, Matrix4f matrix, Vec3 center, float arenaRadius, float scale, float live, float pulse, float time, long seed, int detail
   ) {
      boolean wrote = false;
      int wisps = detail >= 3 ? 18 : (detail == 2 ? 12 : 7);

      for (int i = 0; i < wisps; i++) {
         float h0 = hash(seed, 18000 + i * 23);
         float h1 = hash(seed, 18100 + i * 31);
         float h2 = hash(seed, 18200 + i * 37);
         double angle = i * (float) (Math.PI * 2) / wisps + h0 * 0.4 + time * (0.015 + h1 * 0.02);
         double radial = arenaRadius * (0.42 + h1 * 0.48);
         Vec3 base = center.add(Math.cos(angle) * radial, 0.08, Math.sin(angle) * radial);
         double drift = Math.sin(time * (0.8 + h2 * 0.9) + i) * scale * 0.12;
         Vec3 top = base.add(Math.cos(angle + (Math.PI / 2)) * drift, scale * (0.54 + h2 * 0.56) * pulse, Math.sin(angle + (Math.PI / 2)) * drift);
         wrote |= ribbon(
            buffer,
            matrix,
            base,
            top,
            Math.max(0.018F, scale * (0.005F + h0 * 0.003F)),
            Math.max(0.002F, scale * 8.0E-4F),
            255,
            207 + (int)(h2 * 36.0F),
            60 + (int)(h1 * 80.0F),
            live * (0.08F + h2 * 0.15F),
            0.0F
         );
      }

      return wrote;
   }

   private static boolean floatingArenaCrowns(
      BufferBuilder buffer, Matrix4f matrix, Vec3 center, float arenaRadius, float scale, float live, float pulse, float time, int detail
   ) {
      boolean wrote = false;
      int crowns = detail >= 3 ? 3 : 2;

      for (int i = 0; i < crowns; i++) {
         double y = scale * (0.88 + i * 0.19);
         double r = arenaRadius * (0.42 + i * 0.16);
         double spin = time * (0.16 + i * 0.07) * (i % 2 == 0 ? 1.0 : -1.0);
         wrote |= horizontalRing(
            buffer, matrix, center.add(0.0, y, 0.0), r, Math.max(0.022F, scale * 0.004F), detail >= 3 ? 36 : 24, spin, 255, 241, 183, live * (0.2F + i * 0.06F)
         );
         int points = detail >= 3 ? 10 : 8;

         for (int p = 0; p < points; p++) {
            double a = p * (float) (Math.PI * 2) / points + spin;
            Vec3 base = center.add(Math.cos(a) * r, y, Math.sin(a) * r);
            Vec3 tip = base.add(Math.cos(a) * scale * 0.09, scale * (0.15 + p % 2 * 0.06) * pulse, Math.sin(a) * scale * 0.09);
            wrote |= ribbon(buffer, matrix, base, tip, Math.max(0.012F, scale * 0.003F), Math.max(0.002F, scale * 7.0E-4F), 255, 232, 144, live * 0.3F, 0.0F);
         }
      }

      return wrote;
   }

   private static boolean centerSovereignMark(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float scale, float live, float pulse, float time, int detail) {
      boolean wrote = false;
      Vec3 orbCenter = center.add(0.0, scale * 0.1, 0.0);
      wrote |= orb(buffer, matrix, orbCenter, scale * 0.08F * pulse, 255, 255, 245, live * 0.9F);
      wrote |= orb(buffer, matrix, orbCenter, scale * 0.16F * pulse, 255, 214, 74, live * 0.2F);
      int rays = detail >= 3 ? 8 : 6;

      for (int i = 0; i < rays; i++) {
         double a = i * (float) (Math.PI * 2) / rays - time * 0.12;
         Vec3 a0 = center.add(Math.cos(a) * scale * 0.08, 0.06, Math.sin(a) * scale * 0.08);
         Vec3 a1 = center.add(Math.cos(a) * scale * 0.36, 0.06, Math.sin(a) * scale * 0.36);
         wrote |= groundRibbon(buffer, matrix, a0, a1, Math.max(0.012F, scale * 0.0025F), 255, 244, 196, live * 0.42F);
      }

      return wrote;
   }

   private static boolean groundDisk(BufferBuilder buffer, Matrix4f matrix, Vec3 center, double radius, int segments, int r, int g, int b, float alpha) {
      if (!(alpha <= 0.001F) && !(radius <= 0.001)) {
         int count = Math.min(42, Math.max(16, segments));

         for (int i = 0; i < count; i++) {
            double a0 = i * (float) (Math.PI * 2) / count;
            double a1 = (i + 1.0) * (float) (Math.PI * 2) / count;
            Vec3 p0 = center.add(Math.cos(a0) * radius, 0.0, Math.sin(a0) * radius);
            Vec3 p1 = center.add(Math.cos(a1) * radius, 0.0, Math.sin(a1) * radius);
            triGradient(buffer, matrix, center, p0, p1, r, g, b, alpha, 0.0F, 0.0F);
         }

         return true;
      } else {
         return false;
      }
   }

   private static boolean horizontalRing(
      BufferBuilder buffer, Matrix4f matrix, Vec3 center, double radius, float width, int segments, double spin, int r, int g, int b, float alpha
   ) {
      return groundRing(buffer, matrix, center, radius, width, segments, spin, r, g, b, alpha);
   }

   private static boolean groundRibbon(BufferBuilder buffer, Matrix4f matrix, Vec3 start, Vec3 end, float width, int r, int g, int b, float alpha) {
      if (alpha <= 0.001F) {
         return false;
      }

      Vec3 d = end.subtract(start);
      Vec3 flat = new Vec3(d.x, 0.0, d.z);
      if (flat.lengthSqr() < 1.0E-8) {
         return false;
      }

      Vec3 side = new Vec3(-flat.z, 0.0, flat.x).normalize().scale(width);
      Vec3 a = start.subtract(side);
      Vec3 b0 = start.add(side);
      Vec3 c = end.add(side);
      Vec3 d0 = end.subtract(side);
      tri(buffer, matrix, a, b0, c, r, g, b, alpha);
      tri(buffer, matrix, a, c, d0, r, g, b, alpha);
      return true;
   }

   private static boolean groundRing(
      BufferBuilder buffer, Matrix4f matrix, Vec3 center, double radius, float width, int segments, double spin, int r, int g, int b, float alpha
   ) {
      if (!(alpha <= 0.001F) && !(radius <= 0.001)) {
         double inner = Math.max(0.0, radius - width);
         double outer = radius + width;
         int count = Math.min(42, Math.max(12, segments));

         for (int i = 0; i < count; i++) {
            double a0 = i * (float) (Math.PI * 2) / count + spin;
            double a1 = (i + 1.0) * (float) (Math.PI * 2) / count + spin;
            Vec3 o0 = center.add(Math.cos(a0) * outer, 0.0, Math.sin(a0) * outer);
            Vec3 o1 = center.add(Math.cos(a1) * outer, 0.0, Math.sin(a1) * outer);
            Vec3 i1 = center.add(Math.cos(a1) * inner, 0.0, Math.sin(a1) * inner);
            Vec3 i0 = center.add(Math.cos(a0) * inner, 0.0, Math.sin(a0) * inner);
            tri(buffer, matrix, o0, o1, i1, r, g, b, alpha);
            tri(buffer, matrix, o0, i1, i0, r, g, b, alpha);
         }

         return true;
      } else {
         return false;
      }
   }

   private static boolean orb(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float size, int r, int g, int b, float alpha) {
      if (!(alpha <= 0.001F) && !(size <= 0.001F)) {
         Vec3 camera = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
         Vec3 view = camera.subtract(center);
         if (view.lengthSqr() < 1.0E-8) {
            view = new Vec3(0.0, 0.0, 1.0);
         }

         view = view.normalize();
         Vec3 right = view.cross(new Vec3(0.0, 1.0, 0.0));
         if (right.lengthSqr() < 1.0E-8) {
            right = new Vec3(1.0, 0.0, 0.0);
         }

         right = right.normalize();
         Vec3 up = right.cross(view).normalize();
         int segments = 10;

         for (int i = 0; i < segments; i++) {
            double a0 = i * (float) (Math.PI * 2) / segments;
            double a1 = (i + 1.0) * (float) (Math.PI * 2) / segments;
            Vec3 p0 = center.add(right.scale(Math.cos(a0) * size)).add(up.scale(Math.sin(a0) * size));
            Vec3 p1 = center.add(right.scale(Math.cos(a1) * size)).add(up.scale(Math.sin(a1) * size));
            triGradient(buffer, matrix, center, p0, p1, r, g, b, alpha, 0.0F, 0.0F);
         }

         return true;
      } else {
         return false;
      }
   }

   private static boolean ribbon(
      BufferBuilder buffer,
      Matrix4f matrix,
      Vec3 start,
      Vec3 end,
      float firstWidth,
      float secondWidth,
      int r,
      int g,
      int b,
      float firstAlpha,
      float secondAlpha
   ) {
      if (firstAlpha <= 0.001F && secondAlpha <= 0.001F) {
         return false;
      }

      Vec3 direction = end.subtract(start);
      if (direction.lengthSqr() < 1.0E-8) {
         return false;
      }

      Vec3 camera = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
      Vec3 midpoint = start.add(end).scale(0.5);
      Vec3 view = camera.subtract(midpoint);
      if (view.lengthSqr() < 1.0E-8) {
         view = new Vec3(0.0, 0.0, 1.0);
      }

      Vec3 side = direction.cross(view);
      if (side.lengthSqr() < 1.0E-8) {
         side = safeSide(direction.normalize());
      } else {
         side = side.normalize();
      }

      Vec3 a = start.add(side.scale(firstWidth));
      Vec3 b0 = start.subtract(side.scale(firstWidth));
      Vec3 c = end.subtract(side.scale(secondWidth));
      Vec3 d = end.add(side.scale(secondWidth));
      triGradient(buffer, matrix, a, b0, c, r, g, b, firstAlpha, firstAlpha, secondAlpha);
      triGradient(buffer, matrix, a, c, d, r, g, b, firstAlpha, secondAlpha, secondAlpha);
      return true;
   }

   private static BufferBuilder begin() {
      RenderSystem.enableBlend();
      RenderSystem.blendFuncSeparate(SourceFactor.SRC_ALPHA, DestFactor.ONE, SourceFactor.ONE, DestFactor.ONE_MINUS_SRC_ALPHA);
      RenderSystem.disableCull();
      RenderSystem.enableDepthTest();
      RenderSystem.depthMask(false);
      RenderSystem.setShader(GameRenderer::getPositionColorShader);
      return Tesselator.getInstance().begin(Mode.TRIANGLES, DefaultVertexFormat.POSITION_COLOR);
   }

   private static void finish(BufferBuilder buffer, boolean wrote) {
      if (wrote) {
         try {
            BufferUploader.drawWithShader(buffer.buildOrThrow());
         } catch (IllegalStateException var3) {
         }
      }

      RenderSystem.depthMask(true);
      RenderSystem.enableCull();
      RenderSystem.defaultBlendFunc();
      RenderSystem.disableBlend();
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

   private static float smooth(float value) {
      value = Mth.clamp(value, 0.0F, 1.0F);
      return value * value * (3.0F - 2.0F * value);
   }

   private static float pulse01(float value) {
      return 0.5F + 0.5F * Mth.sin(value);
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

   private static void tri(BufferBuilder buffer, Matrix4f matrix, Vec3 a, Vec3 b, Vec3 c, int r, int g, int bl, float alpha) {
      triGradient(buffer, matrix, a, b, c, r, g, bl, alpha, alpha, alpha);
   }

   private static void triGradient(
      BufferBuilder buffer, Matrix4f matrix, Vec3 a, Vec3 b, Vec3 c, int r, int g, int bl, float alphaA, float alphaB, float alphaC
   ) {
      vertex(buffer, matrix, a, r, g, bl, alphaA);
      vertex(buffer, matrix, b, r, g, bl, alphaB);
      vertex(buffer, matrix, c, r, g, bl, alphaC);
   }

   private static void vertex(BufferBuilder buffer, Matrix4f matrix, Vec3 p, int r, int g, int b, float alpha) {
      int a = Mth.clamp((int)(alpha * 255.0F), 0, 255);
      buffer.addVertex(matrix, (float)p.x, (float)p.y, (float)p.z).setColor(Mth.clamp(r, 0, 255), Mth.clamp(g, 0, 255), Mth.clamp(b, 0, 255), a);
   }

   private static final class Arena {
      private Vec3 fromCenter;
      private Vec3 targetCenter;
      private float fromRadius;
      private float targetRadius;
      private long interpolationStart;
      private long refreshedAt;
      private final long born;
      private long seed;

      private Arena(ArthurAvalonVfxData payload, long now) {
         this.fromCenter = payload.start();
         this.targetCenter = payload.start();
         this.fromRadius = Math.max(7.0F, payload.radius());
         this.targetRadius = Math.max(7.0F, payload.radius());
         this.interpolationStart = now;
         this.refreshedAt = now;
         this.born = now;
         this.seed = payload.seed();
      }

      private void refresh(ArthurAvalonVfxData payload, long now) {
         ArthurKingsBattleVfxClient.Sample sample = this.sample(now);
         this.fromCenter = sample.center;
         this.targetCenter = payload.start();
         this.fromRadius = sample.radius;
         this.targetRadius = Math.max(7.0F, payload.radius());
         this.interpolationStart = now;
         this.refreshedAt = now;
         this.seed = payload.seed();
      }

      private ArthurKingsBattleVfxClient.Sample sample(long now) {
         float t = ArthurKingsBattleVfxClient.smooth(Mth.clamp((float)(now - this.interpolationStart) / 180.0F, 0.0F, 1.0F));
         return new ArthurKingsBattleVfxClient.Sample(this.fromCenter.lerp(this.targetCenter, t), Mth.lerp(t, this.fromRadius, this.targetRadius));
      }

      private boolean dead(long now) {
         return now - this.refreshedAt > 30000L;
      }

      private boolean render(BufferBuilder buffer, Matrix4f matrix, Vec3 camera, long now) {
         ArthurKingsBattleVfxClient.Sample sample = this.sample(now);
         Vec3 center = sample.center.add(0.0, 0.035, 0.0);
         float radius = sample.radius;
         double distance = camera.distanceTo(center);
         int detail = distance < 96.0 ? 3 : (distance < 190.0 ? 2 : 1);
         float age = (float)(now - this.born) / 50.0F;
         float sinceRefresh = (float)(now - this.refreshedAt) / 50.0F;
         float form = ArthurKingsBattleVfxClient.smooth(Mth.clamp(age / 14.0F, 0.0F, 1.0F));
         float expiration = 1.0F - ArthurKingsBattleVfxClient.smooth(Mth.clamp((sinceRefresh - 570.0F) / 30.0F, 0.0F, 1.0F));
         float live = form * expiration;
         if (live <= 0.001F) {
            return false;
         }

         float time = (float)now * 0.001F;
         float pulse = 0.82F + 0.18F * ArthurKingsBattleVfxClient.pulse01(time * 3.7F);
         float deepPulse = 0.72F + 0.28F * ArthurKingsBattleVfxClient.pulse01(time * 1.85F + 1.4F);
         float arenaRadius = radius * (0.96F + 0.025F * ArthurKingsBattleVfxClient.pulse01(time * 1.25F));
         boolean wrote = false;
         wrote |= ArthurKingsBattleVfxClient.royalGroundSeal(buffer, matrix, center, arenaRadius, live, pulse, time, detail);
         wrote |= ArthurKingsBattleVfxClient.radiantBoundary(buffer, matrix, center, arenaRadius, radius, live, pulse, time, detail);
         wrote |= ArthurKingsBattleVfxClient.risingLightOrbs(buffer, matrix, center, arenaRadius, radius, live, time, this.seed, detail);
         wrote |= ArthurKingsBattleVfxClient.holyGroundAura(buffer, matrix, center, arenaRadius, radius, live, time, this.seed, detail);
         wrote |= ArthurKingsBattleVfxClient.verticalGoldWisps(buffer, matrix, center, arenaRadius, radius, live, deepPulse, time, this.seed, detail);
         wrote |= ArthurKingsBattleVfxClient.floatingArenaCrowns(buffer, matrix, center, arenaRadius, radius, live, pulse, time, detail);
         return wrote | ArthurKingsBattleVfxClient.centerSovereignMark(buffer, matrix, center, radius, live, pulse, time, detail);
      }
   }

   private record Sample(Vec3 center, float radius) {
   }
}
