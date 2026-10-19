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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
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

public final class ArthurLightDominationVfxClient {
   private static final Map<Integer, ArthurLightDominationVfxClient.LiveBeam> BEAMS = new LinkedHashMap<>();
   private static final Map<Integer, ArthurLightDominationVfxClient.GroundTrail> TRAILS = new LinkedHashMap<>();
   private static final List<ArthurLightDominationVfxClient.EruptionSegment> ERUPTIONS = new ArrayList<>();
   private static final long BEAM_TIMEOUT_MS = 520L;
   private static final long TRAIL_RELEASE_LIFE_MS = 2200L;
   private static final int MAX_TRAIL_SEGMENTS = 72;
   private static final int MAX_ERUPTIONS = 72;
   private static boolean initialized;

   private ArthurLightDominationVfxClient() {
   }

   public static void init() {
      if (!initialized) {
         initialized = true;
         NeoForge.EVENT_BUS.addListener(ArthurLightDominationVfxClient::render);
      }
   }

   public static void spawn(ArthurAvalonVfxData payload) {
      if (ClientVfxGate.allowed("arthur_light_domination")) {
         init();
         Minecraft mc = Minecraft.getInstance();
         if (mc.level != null && mc.player != null) {
            int source = payload.sourceId();
            long now = Util.getMillis();
            if (payload.variant() == 0) {
               ArthurLightDominationVfxClient.LiveBeam beam = BEAMS.get(source);
               if (beam == null) {
                  BEAMS.put(source, new ArthurLightDominationVfxClient.LiveBeam(payload, now));
               } else {
                  beam.refresh(payload, now);
               }

               while (BEAMS.size() > 4) {
                  BEAMS.remove(BEAMS.keySet().iterator().next());
               }
            } else if (payload.variant() == 1) {
               ArthurLightDominationVfxClient.GroundTrail trail = TRAILS.computeIfAbsent(
                  source, ignored -> new ArthurLightDominationVfxClient.GroundTrail(source)
               );
               trail.add(payload.start(), payload.end(), payload.seed(), now);
            } else if (payload.variant() != 2) {
               if (payload.variant() == 3) {
                  BEAMS.remove(source);
                  ArthurLightDominationVfxClient.GroundTrail trail = TRAILS.get(source);
                  if (trail != null) {
                     trail.release(now);
                  }
               }
            } else {
               ERUPTIONS.add(
                  new ArthurLightDominationVfxClient.EruptionSegment(
                     payload.start(), payload.end(), payload.radius(), payload.seed(), now, Math.max(24, payload.durationTicks())
                  )
               );

               while (ERUPTIONS.size() > 72) {
                  ERUPTIONS.remove(0);
               }
            }
         }
      }
   }

   private static void render(RenderLevelStageEvent event) {
      if (!ClientVfxGate.allowed("arthur_light_domination")) {
         BEAMS.clear();
         TRAILS.clear();
         ERUPTIONS.clear();
      } else if (event.getStage() == Stage.AFTER_PARTICLES) {
         Minecraft mc = Minecraft.getInstance();
         if (mc.level != null && mc.player != null) {
            if (!BEAMS.isEmpty() || !TRAILS.isEmpty() || !ERUPTIONS.isEmpty()) {
               long now = Util.getMillis();
               Camera cameraObj = mc.gameRenderer.getMainCamera();
               Vec3 camera = cameraObj.getPosition();
               event.getPoseStack().pushPose();
               event.getPoseStack().translate(-camera.x, -camera.y, -camera.z);
               Matrix4f matrix = event.getPoseStack().last().pose();
               BufferBuilder buffer = begin();
               boolean wrote = false;
               Iterator<Entry<Integer, ArthurLightDominationVfxClient.LiveBeam>> beamIterator = BEAMS.entrySet().iterator();

               while (beamIterator.hasNext()) {
                  ArthurLightDominationVfxClient.LiveBeam beam = beamIterator.next().getValue();
                  if (now - beam.lastUpdate > 520L) {
                     beamIterator.remove();
                  } else {
                     wrote |= beam.render(buffer, matrix, camera, now);
                  }
               }

               Iterator<Entry<Integer, ArthurLightDominationVfxClient.GroundTrail>> trailIterator = TRAILS.entrySet().iterator();

               while (trailIterator.hasNext()) {
                  ArthurLightDominationVfxClient.GroundTrail trail = trailIterator.next().getValue();
                  if (trail.dead(now)) {
                     trailIterator.remove();
                  } else {
                     wrote |= trail.render(buffer, matrix, camera, now);
                  }
               }

               Iterator<ArthurLightDominationVfxClient.EruptionSegment> eruptionIterator = ERUPTIONS.iterator();

               while (eruptionIterator.hasNext()) {
                  ArthurLightDominationVfxClient.EruptionSegment eruption = eruptionIterator.next();
                  if (eruption.dead(now)) {
                     eruptionIterator.remove();
                  } else {
                     wrote |= eruption.render(buffer, matrix, camera, now);
                  }
               }

               finish(buffer, wrote);
               event.getPoseStack().popPose();
            }
         }
      }
   }

   private static boolean muzzle(
      BufferBuilder buffer, Matrix4f matrix, Vec3 start, Vec3 dir, Vec3 side, Vec3 up, float scale, float form, float time, long seed, int detail
   ) {
      boolean wrote = false;
      Vec3 center = start.subtract(dir.scale(scale * 0.55));
      int rings = detail >= 3 ? 7 : (detail == 2 ? 5 : 3);

      for (int i = 0; i < rings; i++) {
         double radius = scale * (1.95 - i * 0.19) * (0.88 + form * 0.12);
         double spin = time * (0.34 + i * 0.18) * (i % 2 == 0 ? 1.0 : -1.0);
         float alpha = form * (0.46F - i * 0.045F);
         wrote |= ring(
            buffer,
            matrix,
            center.add(dir.scale((i - rings * 0.5) * scale * 0.08)),
            side,
            up,
            radius,
            scale * 0.015F,
            detail >= 3 ? 48 : 32,
            spin,
            255,
            i < 2 ? 250 : 219,
            i < 2 ? 215 : 92,
            alpha
         );
      }

      int spokes = detail >= 3 ? 24 : (detail == 2 ? 16 : 10);
      Vec3 focus = start.add(dir.scale(scale * 0.34));

      for (int i = 0; i < spokes; i++) {
         float h = hash(seed, 1200 + i * 17);
         double a = i * (float) (Math.PI * 2) / spokes + time * (0.34 + h * 0.16);
         Vec3 source = center.add(side.scale(Math.cos(a) * scale * 1.55)).add(up.scale(Math.sin(a) * scale * 1.55));
         wrote |= ribbon(buffer, matrix, source, focus, scale * 0.01F, scale * 0.002F, 255, 231, 132, form * (0.16F + h * 0.28F), 0.0F);
      }

      wrote |= orb(buffer, matrix, focus, scale * 0.48F, 255, 255, 242, form * 0.72F);
      return wrote | orb(buffer, matrix, focus, scale * 0.78F, 255, 188, 36, form * 0.18F);
   }

   private static boolean denseCore(
      BufferBuilder buffer, Matrix4f matrix, Vec3 start, Vec3 end, Vec3 dir, Vec3 side, Vec3 up, float scale, float pulse, float time, int detail
   ) {
      boolean wrote = false;
      int planes = detail >= 3 ? 7 : (detail == 2 ? 5 : 3);

      for (int i = 0; i < planes; i++) {
         double a = i * Math.PI / planes + time * 0.11;
         Vec3 axis = side.scale(Math.cos(a)).add(up.scale(Math.sin(a))).normalize();
         wrote |= beamPlane(buffer, matrix, start, end, axis, scale * 2.15F, 255, 117, 0, 0.035F);
         wrote |= beamPlane(buffer, matrix, start, end, axis, scale * 1.55F, 255, 166, 17, 0.095F);
         wrote |= beamPlane(buffer, matrix, start, end, axis, scale * 1.02F, 255, 218, 84, 0.26F);
         wrote |= beamPlane(buffer, matrix, start, end, axis, scale * 0.58F * pulse, 255, 250, 205, 0.66F);
         wrote |= beamPlane(buffer, matrix, start, end, axis, scale * 0.2F, 255, 255, 255, 0.96F);
      }

      int cuts = detail >= 3 ? 10 : (detail == 2 ? 6 : 3);
      double length = start.distanceTo(end);

      for (int i = 0; i < cuts; i++) {
         double u = (i + 0.5) / cuts;
         Vec3 c = start.add(dir.scale(length * u));
         float p = 0.65F + 0.35F * pulse01(time * (5.0F + i * 0.33F));
         wrote |= orb(buffer, matrix, c, scale * (0.26F + p * 0.15F), 255, 255, 231, 0.18F * p);
      }

      return wrote;
   }

   private static boolean beamCollars(
      BufferBuilder buffer, Matrix4f matrix, Vec3 start, Vec3 end, Vec3 dir, Vec3 side, Vec3 up, float scale, float time, long seed, int detail
   ) {
      boolean wrote = false;
      double length = start.distanceTo(end);
      int count = detail >= 3 ? 14 : (detail == 2 ? 9 : 5);

      for (int i = 0; i < count; i++) {
         float h = hash(seed, 3000 + i * 31);
         double u = (i + 0.42) / count;
         Vec3 center = start.add(dir.scale(length * u));
         double radius = scale * (0.86 + h * 0.34 + Math.sin(time * 2.4 + i) * 0.045);
         double spin = time * (1.0 + h * 1.6) * (i % 2 == 0 ? 1.0 : -1.0);
         float blink = 0.55F + 0.45F * pulse01(time * (4.6F + h * 4.0F) + i);
         wrote |= ring(buffer, matrix, center, side, up, radius, scale * 0.01F, detail >= 3 ? 34 : 24, spin, 255, 220, 96, blink * 0.48F);
         wrote |= ring(
            buffer,
            matrix,
            center.add(dir.scale(0.02)),
            side,
            up,
            radius * 0.53,
            scale * 0.0045F,
            detail >= 3 ? 26 : 18,
            -spin * 1.55,
            255,
            255,
            231,
            blink * 0.66F
         );
      }

      return wrote;
   }

   private static boolean beamHelices(
      BufferBuilder buffer, Matrix4f matrix, Vec3 start, Vec3 end, Vec3 dir, Vec3 side, Vec3 up, float scale, float time, long seed, int detail
   ) {
      boolean wrote = false;
      int lanes = detail >= 3 ? 5 : (detail == 2 ? 3 : 2);
      int segments = detail >= 3 ? 30 : (detail == 2 ? 22 : 14);

      for (int lane = 0; lane < lanes; lane++) {
         float h = hash(seed, 5000 + lane * 53);
         Vec3 previous = null;

         for (int i = 0; i <= segments; i++) {
            double u = (double)i / segments;
            Vec3 center = start.lerp(end, u);
            double a = lane * (float) (Math.PI * 2) / lanes + (lane % 2 == 0 ? 1.0 : -1.0) * (u * (float) (Math.PI * 2) * (2.0 + h * 1.2) + time * (1.5 + h));
            double radius = scale * (0.84 + h * 0.22) * (0.82 + Math.sin(u * Math.PI) * 0.22);
            Vec3 point = center.add(side.scale(Math.cos(a) * radius)).add(up.scale(Math.sin(a) * radius));
            if (previous != null) {
               float envelope = (float)(0.3 + Math.sin(u * Math.PI) * 0.7);
               wrote |= ribbon(buffer, matrix, previous, point, scale * 0.01F, scale * 0.003F, 255, 196, 43, envelope * 0.33F, envelope * 0.08F);
               wrote |= ribbon(
                  buffer,
                  matrix,
                  previous.add(dir.scale(0.012)),
                  point.add(dir.scale(0.012)),
                  scale * 0.0035F,
                  scale * 0.001F,
                  255,
                  251,
                  218,
                  envelope * 0.74F,
                  0.0F
               );
            }

            previous = point;
         }
      }

      return wrote;
   }

   private static boolean beamStardust(
      BufferBuilder buffer, Matrix4f matrix, Vec3 start, Vec3 end, Vec3 dir, Vec3 side, Vec3 up, float scale, float time, long seed, int detail
   ) {
      boolean wrote = false;
      int count = detail >= 3 ? 64 : (detail == 2 ? 38 : 20);

      for (int i = 0; i < count; i++) {
         float h0 = hash(seed, 7000 + i * 17);
         float h1 = hash(seed, 7100 + i * 19);
         float h2 = hash(seed, 7200 + i * 23);
         double u = (i + h0 * 0.7) / count;
         Vec3 center = start.lerp(end, u);
         double a = h1 * (float) (Math.PI * 2) + time * (0.45 + h2 * 0.65);
         double r = scale * (0.52 + h0 * 1.2);
         Vec3 p = center.add(side.scale(Math.cos(a) * r)).add(up.scale(Math.sin(a) * r));
         float blink = 0.18F + 0.82F * pulse01(time * (7.0F + h1 * 7.0F) + i * 0.37F);
         float size = scale * (0.014F + h2 * 0.034F);
         wrote |= orb(buffer, matrix, p, size * (1.0F + blink * 0.36F), 255, 226 + (int)(h1 * 29.0F), 116 + (int)(h0 * 92.0F), blink * 0.72F);
      }

      return wrote;
   }

   private static boolean beamImpact(
      BufferBuilder buffer, Matrix4f matrix, Vec3 end, Vec3 dir, Vec3 side, Vec3 up, float scale, float time, long seed, int detail
   ) {
      boolean wrote = false;
      float pulse = 0.74F + 0.26F * pulse01(time * 7.0F);
      wrote |= orb(buffer, matrix, end, scale * 1.1F * pulse, 255, 255, 243, 0.72F);
      wrote |= orb(buffer, matrix, end, scale * 2.25F * pulse, 255, 173, 18, 0.13F);
      int rings = detail >= 3 ? 5 : 3;

      for (int i = 0; i < rings; i++) {
         double radius = scale * (0.45 + i * 0.34 + pulse * 0.18);
         wrote |= ring(
            buffer,
            matrix,
            end.add(dir.scale(i * 0.025)),
            side,
            up,
            radius,
            scale * 0.008F,
            detail >= 3 ? 36 : 24,
            time * (0.6 + i * 0.2),
            255,
            232,
            133,
            0.36F - i * 0.055F
         );
      }

      return wrote;
   }

   private static boolean wallLayer(
      BufferBuilder buffer, Matrix4f matrix, Vec3 start, Vec3 end, Vec3 side, double height, float width, int r, int g, int b, float alpha
   ) {
      if (alpha <= 0.001F) {
         return false;
      }

      Vec3 offset = side.scale(width);
      Vec3 a0 = start.subtract(offset);
      Vec3 a1 = end.subtract(offset);
      Vec3 b1 = end.add(offset).add(0.0, height, 0.0);
      Vec3 b0 = start.add(offset).add(0.0, height, 0.0);
      triGradient(buffer, matrix, a0, a1, b1, r, g, b, alpha * 0.42F, alpha * 0.42F, alpha * 0.02F);
      triGradient(buffer, matrix, a0, b1, b0, r, g, b, alpha * 0.42F, alpha * 0.02F, alpha * 0.02F);
      Vec3 c0 = start.add(offset);
      Vec3 c1 = end.add(offset);
      Vec3 d1 = end.subtract(offset).add(0.0, height, 0.0);
      Vec3 d0 = start.subtract(offset).add(0.0, height, 0.0);
      triGradient(buffer, matrix, c0, c1, d1, r, g, b, alpha * 0.38F, alpha * 0.38F, alpha * 0.015F);
      triGradient(buffer, matrix, c0, d1, d0, r, g, b, alpha * 0.38F, alpha * 0.015F, alpha * 0.015F);
      return true;
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
         int count = Math.min(36, Math.max(12, segments));

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

   private static boolean ring(
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
      float alpha
   ) {
      if (!(alpha <= 0.001F) && !(radius <= 0.001)) {
         int count = Math.min(48, Math.max(12, segments));
         double inner = Math.max(0.0, radius - width);
         double outer = radius + width;

         for (int i = 0; i < count; i++) {
            double a0 = i * (float) (Math.PI * 2) / count + spin;
            double a1 = (i + 0.82) * (float) (Math.PI * 2) / count + spin;
            Vec3 d0 = axisA.scale(Math.cos(a0)).add(axisB.scale(Math.sin(a0)));
            Vec3 d1 = axisA.scale(Math.cos(a1)).add(axisB.scale(Math.sin(a1)));
            Vec3 o0 = center.add(d0.scale(outer));
            Vec3 o1 = center.add(d1.scale(outer));
            Vec3 i1 = center.add(d1.scale(inner));
            Vec3 i0 = center.add(d0.scale(inner));
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
         int segments = 12;

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

   private static boolean beamPlane(BufferBuilder buffer, Matrix4f matrix, Vec3 start, Vec3 end, Vec3 axis, float width, int r, int g, int b, float alpha) {
      if (alpha <= 0.001F) {
         return false;
      }

      Vec3 offset = axis.normalize().scale(width);
      Vec3 a = start.subtract(offset);
      Vec3 b0 = start.add(offset);
      Vec3 c = end.add(offset);
      Vec3 d = end.subtract(offset);
      tri(buffer, matrix, a, b0, c, r, g, b, alpha);
      tri(buffer, matrix, a, c, d, r, g, b, alpha);
      return true;
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

   private static final class EruptionSegment {
      private final Vec3 start;
      private final Vec3 end;
      private final float radius;
      private final long seed;
      private final long born;
      private final int duration;

      private EruptionSegment(Vec3 start, Vec3 end, float radius, long seed, long born, int duration) {
         this.start = start;
         this.end = end;
         this.radius = radius;
         this.seed = seed;
         this.born = born;
         this.duration = duration;
      }

      private boolean dead(long now) {
         return now - this.born > this.duration * 50L + 200L;
      }

      private boolean render(BufferBuilder buffer, Matrix4f matrix, Vec3 camera, long now) {
         float age = (float)(now - this.born) / 50.0F;
         float ignition = ArthurLightDominationVfxClient.smooth(Mth.clamp(age / 8.0F, 0.0F, 1.0F));
         float sustain = 1.0F - ArthurLightDominationVfxClient.smooth(Mth.clamp((age - this.duration + 18.0F) / 18.0F, 0.0F, 1.0F));
         float live = ignition * sustain;
         if (live <= 0.001F) {
            return false;
         }

         Vec3 segment = this.end.subtract(this.start);
         if (segment.lengthSqr() < 1.0E-8) {
            return false;
         }

         Vec3 horizontal = new Vec3(segment.x, 0.0, segment.z);
         if (horizontal.lengthSqr() < 1.0E-8) {
            horizontal = new Vec3(1.0, 0.0, 0.0);
         }

         Vec3 side = new Vec3(-horizontal.z, 0.0, horizontal.x).normalize();
         float time = (float)now * 0.001F;
         float pulse = 0.84F + 0.16F * ArthurLightDominationVfxClient.pulse01(time * 7.0F + ArthurLightDominationVfxClient.hash(this.seed, 44) * 5.0F);
         double height = 11.0 + this.radius * 1.45 + pulse * 2.0;
         boolean wrote = false;
         wrote |= ArthurLightDominationVfxClient.wallLayer(buffer, matrix, this.start, this.end, side, height, this.radius * 0.86F, 255, 128, 0, live * 0.1F);
         wrote |= ArthurLightDominationVfxClient.wallLayer(
            buffer,
            matrix,
            this.start.add(side.scale(0.03)),
            this.end.add(side.scale(0.03)),
            side,
            height * 0.94,
            this.radius * 0.52F,
            255,
            194,
            28,
            live * 0.24F
         );
         wrote |= ArthurLightDominationVfxClient.wallLayer(
            buffer,
            matrix,
            this.start.add(side.scale(0.06)),
            this.end.add(side.scale(0.06)),
            side,
            height * 0.88,
            this.radius * 0.24F * pulse,
            255,
            244,
            178,
            live * 0.54F
         );
         wrote |= ArthurLightDominationVfxClient.wallLayer(
            buffer,
            matrix,
            this.start.add(side.scale(0.09)),
            this.end.add(side.scale(0.09)),
            side,
            height * 0.8,
            this.radius * 0.085F,
            255,
            255,
            250,
            live * 0.88F
         );
         int strands = 5;

         for (int i = 0; i < strands; i++) {
            float h0 = ArthurLightDominationVfxClient.hash(this.seed, 12000 + i * 37);
            float h1 = ArthurLightDominationVfxClient.hash(this.seed, 12100 + i * 41);
            Vec3 base = this.start.lerp(this.end, (i + 0.5) / strands);
            Vec3 top = base.add(side.scale((h0 - 0.5) * this.radius * 0.72)).add(0.0, height * (0.7 + h1 * 0.34), 0.0);
            wrote |= ArthurLightDominationVfxClient.ribbon(
               buffer, matrix, base, top, 0.12F + h0 * 0.18F, 0.015F, 255, 229, 121, live * (0.18F + h1 * 0.32F), 0.0F
            );
         }

         int motes = 5;

         for (int i = 0; i < motes; i++) {
            float h0 = ArthurLightDominationVfxClient.hash(this.seed, 13000 + i * 31);
            float h1 = ArthurLightDominationVfxClient.hash(this.seed, 13100 + i * 37);
            float h2 = ArthurLightDominationVfxClient.hash(this.seed, 13200 + i * 43);
            double cycle = (age * (0.028 + h0 * 0.025) + h1) % 1.0;
            Vec3 base = this.start.lerp(this.end, h0).add(side.scale((h1 - 0.5) * this.radius * 1.4));
            Vec3 p = base.add(0.0, cycle * height * (0.65 + h2 * 0.35), 0.0);
            float blink = 0.22F + 0.78F * ArthurLightDominationVfxClient.pulse01(time * (8.0F + h2 * 8.0F) + i);
            wrote |= ArthurLightDominationVfxClient.orb(
               buffer, matrix, p, 0.055F + h2 * 0.09F, 255, 220 + (int)(h0 * 34.0F), 100 + (int)(h1 * 110.0F), live * blink * (1.0F - (float)cycle * 0.28F)
            );
         }

         return wrote;
      }
   }

   private static final class GroundTrail {
      private final int sourceId;
      private final List<ArthurLightDominationVfxClient.TrailSegment> segments = new ArrayList<>();
      private long lastTouch;
      private long releasedAt;

      private GroundTrail(int sourceId) {
         this.sourceId = sourceId;
      }

      private void add(Vec3 start, Vec3 end, long seed, long now) {
         this.lastTouch = now;
         this.releasedAt = 0L;
         if (start.distanceToSqr(end) < 0.015) {
            if (this.segments.isEmpty()) {
               this.segments.add(new ArthurLightDominationVfxClient.TrailSegment(start, start.add(0.01, 0.0, 0.01), seed, now));
            }
         } else {
            ArthurLightDominationVfxClient.TrailSegment segment = new ArthurLightDominationVfxClient.TrailSegment(start, end, seed, now);
            this.segments.add(segment);

            while (this.segments.size() > 72) {
               this.segments.remove(0);
            }
         }
      }

      private void release(long now) {
         this.releasedAt = now;
      }

      private boolean dead(long now) {
         return this.releasedAt != 0L ? now - this.releasedAt > 2200L : now - this.lastTouch > 1400L;
      }

      private boolean render(BufferBuilder buffer, Matrix4f matrix, Vec3 camera, long now) {
         boolean wrote = false;
         float releaseFade = this.releasedAt == 0L
            ? 1.0F
            : 1.0F - ArthurLightDominationVfxClient.smooth(Mth.clamp((float)(now - this.releasedAt) / 2200.0F, 0.0F, 1.0F));
         int step = camera.distanceTo(this.segments.isEmpty() ? Vec3.ZERO : this.segments.get(this.segments.size() - 1).end) > 180.0 ? 2 : 1;

         for (int i = 0; i < this.segments.size(); i += step) {
            wrote |= this.segments.get(i).render(buffer, matrix, camera, now, releaseFade, i, this.sourceId);
         }

         return wrote;
      }
   }

   private static final class LiveBeam {
      private Vec3 fromStart;
      private Vec3 fromEnd;
      private Vec3 targetStart;
      private Vec3 targetEnd;
      private final long born;
      private long interpolationStart;
      private long lastUpdate;
      private long seed;
      private float radius;

      private LiveBeam(ArthurAvalonVfxData payload, long now) {
         this.fromStart = payload.start();
         this.fromEnd = payload.end();
         this.targetStart = payload.start();
         this.targetEnd = payload.end();
         this.radius = payload.radius();
         this.seed = payload.seed();
         this.born = now;
         this.interpolationStart = now;
         this.lastUpdate = now;
      }

      private void refresh(ArthurAvalonVfxData payload, long now) {
         Vec3[] current = this.sample(now);
         this.fromStart = current[0];
         this.fromEnd = current[1];
         this.targetStart = payload.start();
         this.targetEnd = payload.end();
         this.radius = payload.radius();
         this.seed = payload.seed();
         this.interpolationStart = now;
         this.lastUpdate = now;
      }

      private Vec3[] sample(long now) {
         float t = ArthurLightDominationVfxClient.smooth(Mth.clamp((float)(now - this.interpolationStart) / 115.0F, 0.0F, 1.0F));
         return new Vec3[]{this.fromStart.lerp(this.targetStart, t), this.fromEnd.lerp(this.targetEnd, t)};
      }

      private boolean render(BufferBuilder buffer, Matrix4f matrix, Vec3 camera, long now) {
         Vec3[] sampled = this.sample(now);
         Vec3 start = sampled[0];
         Vec3 end = sampled[1];
         Vec3 direction = end.subtract(start);
         if (direction.lengthSqr() < 1.0E-8) {
            return false;
         }

         double distance = camera.distanceTo(start);
         int detail = distance < 110.0 ? 3 : (distance < 240.0 ? 2 : 1);
         Vec3 dir = direction.normalize();
         Vec3 side = ArthurLightDominationVfxClient.safeSide(dir);
         Vec3 up = dir.cross(side).normalize();
         float scale = Mth.clamp(this.radius, 4.6F, 7.2F);
         float time = (float)now * 0.001F;
         float age = (float)(now - this.born) / 50.0F;
         float form = ArthurLightDominationVfxClient.smooth(Mth.clamp(age / 9.0F, 0.0F, 1.0F));
         float pulse = 0.82F + 0.18F * ArthurLightDominationVfxClient.pulse01(time * 8.5F);
         boolean wrote = false;
         wrote |= ArthurLightDominationVfxClient.muzzle(buffer, matrix, start, dir, side, up, scale, form, time, this.seed, detail);
         wrote |= ArthurLightDominationVfxClient.denseCore(buffer, matrix, start, end, dir, side, up, scale, pulse, time, detail);
         wrote |= ArthurLightDominationVfxClient.beamCollars(buffer, matrix, start, end, dir, side, up, scale, time, this.seed, detail);
         wrote |= ArthurLightDominationVfxClient.beamHelices(buffer, matrix, start, end, dir, side, up, scale, time, this.seed, detail);
         wrote |= ArthurLightDominationVfxClient.beamStardust(buffer, matrix, start, end, dir, side, up, scale, time, this.seed, detail);
         return wrote | ArthurLightDominationVfxClient.beamImpact(buffer, matrix, end, dir, side, up, scale, time, this.seed, detail);
      }
   }

   private static final class TrailSegment {
      private final Vec3 start;
      private final Vec3 end;
      private final long seed;
      private final long born;

      private TrailSegment(Vec3 start, Vec3 end, long seed, long born) {
         this.start = start;
         this.end = end;
         this.seed = seed;
         this.born = born;
      }

      private boolean render(BufferBuilder buffer, Matrix4f matrix, Vec3 camera, long now, float fade, int index, int sourceId) {
         float age = (float)(now - this.born) / 50.0F;
         float appear = ArthurLightDominationVfxClient.smooth(Mth.clamp(age / 5.0F, 0.0F, 1.0F));
         float pulse = 0.72F + 0.28F * ArthurLightDominationVfxClient.pulse01((float)now * 0.006F + index * 0.61F);
         float live = appear * fade;
         if (live <= 0.001F) {
            return false;
         }

         boolean wrote = false;
         Vec3 a = this.start.add(0.0, 0.028, 0.0);
         Vec3 b = this.end.add(0.0, 0.028, 0.0);
         wrote |= ArthurLightDominationVfxClient.groundRibbon(buffer, matrix, a, b, 1.18F, 255, 145, 0, live * 0.1F);
         wrote |= ArthurLightDominationVfxClient.groundRibbon(buffer, matrix, a.add(0.0, 0.008, 0.0), b.add(0.0, 0.008, 0.0), 0.66F, 255, 205, 46, live * 0.28F);
         wrote |= ArthurLightDominationVfxClient.groundRibbon(
            buffer, matrix, a.add(0.0, 0.016, 0.0), b.add(0.0, 0.016, 0.0), 0.22F * pulse, 255, 253, 219, live * 0.72F
         );
         if ((index & 2) == 0) {
            Vec3 center = a.lerp(b, 0.5);
            wrote |= ArthurLightDominationVfxClient.groundRing(
               buffer, matrix, center, 0.58 + pulse * 0.16, 0.035F, 24, now * 0.0012 + index, 255, 224, 108, live * 0.24F
            );
         }

         int motes = 2;

         for (int m = 0; m < motes; m++) {
            float h0 = ArthurLightDominationVfxClient.hash(this.seed ^ sourceId * 31L, 8000 + index * 17 + m * 43);
            float h1 = ArthurLightDominationVfxClient.hash(this.seed ^ sourceId * 47L, 9000 + index * 19 + m * 53);
            float h2 = ArthurLightDominationVfxClient.hash(this.seed ^ sourceId * 59L, 10000 + index * 23 + m * 61);
            double cycle = (now * (2.2E-4 + h0 * 1.6E-4) + h1 * 7.0) % 1.0;
            Vec3 base = a.lerp(b, h0);
            Vec3 tangent = b.subtract(a);
            Vec3 side = tangent.lengthSqr() < 1.0E-8 ? new Vec3(1.0, 0.0, 0.0) : new Vec3(-tangent.z, 0.0, tangent.x).normalize();
            Vec3 p = base.add(side.scale((h1 - 0.5) * 1.25)).add(0.0, 0.08 + cycle * (1.4 + h2 * 2.2), 0.0);
            float blink = 0.18F + 0.82F * ArthurLightDominationVfxClient.pulse01((float)now * (0.01F + h2 * 0.009F) + index + m * 2.1F);
            float size = 0.032F + h2 * 0.05F;
            wrote |= ArthurLightDominationVfxClient.orb(
               buffer,
               matrix,
               p,
               size * (0.8F + blink * 0.55F),
               255,
               218 + (int)(h0 * 36.0F),
               95 + (int)(h1 * 95.0F),
               live * blink * (1.0F - (float)cycle * 0.35F)
            );
         }

         return wrote;
      }
   }
}
