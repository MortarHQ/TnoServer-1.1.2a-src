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

public final class AvalonSovereignJudgementVfxClient {
   private static final Map<Integer, AvalonSovereignJudgementVfxClient.LiveBeam> BEAMS = new LinkedHashMap<>();
   private static final Map<Integer, AvalonSovereignJudgementVfxClient.GroundTrail> TRAILS = new LinkedHashMap<>();
   private static final List<AvalonSovereignJudgementVfxClient.EruptionSegment> ERUPTIONS = new ArrayList<>();
   private static final List<AvalonSovereignJudgementVfxClient.TerminalBurst> BURSTS = new ArrayList<>();
   private static final long BEAM_TIMEOUT_MS = 620L;
   private static final long TRAIL_RELEASE_LIFE_MS = 2600L;
   private static final int MAX_TRAIL_SEGMENTS = 60;
   private static final int MAX_ERUPTIONS = 36;
   private static final int MAX_BURSTS = 4;
   private static boolean initialized;

   private AvalonSovereignJudgementVfxClient() {
   }

   public static void init() {
      if (!initialized) {
         initialized = true;
         NeoForge.EVENT_BUS.addListener(AvalonSovereignJudgementVfxClient::render);
      }
   }

   public static void spawn(ArthurAvalonVfxData payload) {
      if (ClientVfxGate.allowed("avalon_sovereign_judgement")) {
         init();
         Minecraft minecraft = Minecraft.getInstance();
         if (minecraft.level != null && minecraft.player != null) {
            double maxDistance = 900.0;
            if (!(minecraft.player.distanceToSqr(payload.start()) > maxDistance * maxDistance)
               || !(minecraft.player.distanceToSqr(payload.end()) > maxDistance * maxDistance)) {
               int source = payload.sourceId() >= 0 ? payload.sourceId() : (int)(payload.seed() ^ payload.seed() >>> 32);
               long now = Util.getMillis();
               if (payload.variant() == 0) {
                  AvalonSovereignJudgementVfxClient.LiveBeam beam = BEAMS.get(source);
                  if (beam == null) {
                     BEAMS.put(source, new AvalonSovereignJudgementVfxClient.LiveBeam(payload, now));
                  } else {
                     beam.refresh(payload, now);
                  }

                  while (BEAMS.size() > 4) {
                     BEAMS.remove(BEAMS.keySet().iterator().next());
                  }
               } else if (payload.variant() == 1) {
                  AvalonSovereignJudgementVfxClient.GroundTrail trail = TRAILS.computeIfAbsent(
                     source, ignored -> new AvalonSovereignJudgementVfxClient.GroundTrail(source)
                  );
                  trail.add(payload.start(), payload.end(), payload.seed(), now);
               } else if (payload.variant() == 2) {
                  ERUPTIONS.add(
                     new AvalonSovereignJudgementVfxClient.EruptionSegment(
                        payload.start(), payload.end(), payload.radius(), payload.seed(), now, Math.max(40, payload.durationTicks())
                     )
                  );

                  while (ERUPTIONS.size() > 36) {
                     ERUPTIONS.remove(0);
                  }
               } else if (payload.variant() == 3) {
                  BEAMS.remove(source);
                  AvalonSovereignJudgementVfxClient.GroundTrail trail = TRAILS.get(source);
                  if (trail != null) {
                     trail.release(now);
                  }
               } else {
                  if (payload.variant() == 4) {
                     BURSTS.add(
                        new AvalonSovereignJudgementVfxClient.TerminalBurst(
                           payload.end(), payload.radius(), payload.seed(), now, Math.max(60, payload.durationTicks())
                        )
                     );

                     while (BURSTS.size() > 4) {
                        BURSTS.remove(0);
                     }

                     double distance = minecraft.player.getEyePosition().distanceTo(payload.end());
                     if (distance < 420.0) {
                        float proximity = 1.0F - Mth.clamp((float)(distance / 420.0), 0.0F, 1.0F);
                        PathsClientState.triggerManualShake(18, 1.2F + proximity * 2.2F, 9.2F + proximity * 1.8F);
                     }
                  }
               }
            }
         }
      }
   }

   private static void render(RenderLevelStageEvent event) {
      if (!ClientVfxGate.allowed("avalon_sovereign_judgement")) {
         BEAMS.clear();
         TRAILS.clear();
         ERUPTIONS.clear();
         BURSTS.clear();
      } else if (event.getStage() == Stage.AFTER_PARTICLES) {
         Minecraft minecraft = Minecraft.getInstance();
         if (minecraft.level != null && minecraft.player != null) {
            if (!BEAMS.isEmpty() || !TRAILS.isEmpty() || !ERUPTIONS.isEmpty() || !BURSTS.isEmpty()) {
               long now = Util.getMillis();
               Camera cameraObj = minecraft.gameRenderer.getMainCamera();
               Vec3 camera = cameraObj.getPosition();
               event.getPoseStack().pushPose();
               event.getPoseStack().translate(-camera.x, -camera.y, -camera.z);
               Matrix4f matrix = event.getPoseStack().last().pose();
               BufferBuilder buffer = begin();
               boolean wrote = false;
               Iterator<Entry<Integer, AvalonSovereignJudgementVfxClient.LiveBeam>> beamIterator = BEAMS.entrySet().iterator();

               while (beamIterator.hasNext()) {
                  AvalonSovereignJudgementVfxClient.LiveBeam beam = beamIterator.next().getValue();
                  if (now - beam.lastUpdate > 620L) {
                     beamIterator.remove();
                  } else {
                     wrote |= beam.render(buffer, matrix, camera, now);
                  }
               }

               Iterator<Entry<Integer, AvalonSovereignJudgementVfxClient.GroundTrail>> trailIterator = TRAILS.entrySet().iterator();

               while (trailIterator.hasNext()) {
                  AvalonSovereignJudgementVfxClient.GroundTrail trail = trailIterator.next().getValue();
                  if (trail.dead(now)) {
                     trailIterator.remove();
                  } else {
                     wrote |= trail.render(buffer, matrix, camera, now);
                  }
               }

               Iterator<AvalonSovereignJudgementVfxClient.EruptionSegment> eruptionIterator = ERUPTIONS.iterator();

               while (eruptionIterator.hasNext()) {
                  AvalonSovereignJudgementVfxClient.EruptionSegment eruption = eruptionIterator.next();
                  if (eruption.dead(now)) {
                     eruptionIterator.remove();
                  } else {
                     wrote |= eruption.render(buffer, matrix, camera, now);
                  }
               }

               Iterator<AvalonSovereignJudgementVfxClient.TerminalBurst> burstIterator = BURSTS.iterator();

               while (burstIterator.hasNext()) {
                  AvalonSovereignJudgementVfxClient.TerminalBurst burst = burstIterator.next();
                  if (burst.dead(now)) {
                     burstIterator.remove();
                  } else {
                     wrote |= burst.render(buffer, matrix, camera, now);
                  }
               }

               finish(buffer, wrote);
               event.getPoseStack().popPose();
            }
         }
      }
   }

   private static boolean royalMuzzle(
      BufferBuilder buffer, Matrix4f matrix, Vec3 start, Vec3 dir, Vec3 side, Vec3 up, float scale, float form, float time, long seed, int detail
   ) {
      boolean wrote = false;
      Vec3 center = start.subtract(dir.scale(scale * 0.42));
      int circles = detail >= 3 ? 4 : (detail == 2 ? 3 : 2);

      for (int i = 0; i < circles; i++) {
         double radius = scale * (1.82 - i * 0.28) * (0.9 + form * 0.1);
         double spin = time * (0.24 + i * 0.16) * (i % 2 == 0 ? 1.0 : -1.0);
         float alpha = form * (0.55F - i * 0.08F);
         wrote |= ring(
            buffer,
            matrix,
            center.add(dir.scale((i - 1.5) * scale * 0.065)),
            side,
            up,
            radius,
            scale * 0.014F,
            detail >= 3 ? 52 : 34,
            spin,
            255,
            i == 0 ? 250 : 221,
            i == 0 ? 216 : 106,
            alpha
         );
      }

      int spokes = detail >= 3 ? 18 : (detail == 2 ? 12 : 8);
      Vec3 focus = start.add(dir.scale(scale * 0.4));

      for (int i = 0; i < spokes; i++) {
         float h = hash(seed, 1200 + i * 17);
         double a = i * (float) (Math.PI * 2) / spokes + time * (0.16 + h * 0.08);
         Vec3 source = center.add(side.scale(Math.cos(a) * scale * 1.5)).add(up.scale(Math.sin(a) * scale * 1.5));
         wrote |= ribbon(buffer, matrix, source, focus, scale * 0.014F, scale * 0.002F, 255, 232, 138, form * (0.18F + h * 0.3F), 0.0F);
      }

      wrote |= orb(buffer, matrix, focus, scale * 0.7F, 255, 250, 212, form * 0.62F);
      return wrote | orb(buffer, matrix, focus, scale * 0.3F, 255, 255, 255, form * 0.96F);
   }

   private static boolean embeddedExcalibur(
      BufferBuilder buffer, Matrix4f matrix, Vec3 start, Vec3 dir, Vec3 side, Vec3 up, float scale, float live, float time
   ) {
      if (live <= 0.001F) {
         return false;
      }

      boolean wrote = false;
      Vec3 pommel = start.subtract(dir.scale(scale * 0.92));
      Vec3 hilt = start.subtract(dir.scale(scale * 0.52));
      Vec3 shoulder = start.add(dir.scale(scale * 2.75));
      Vec3 tip = start.add(dir.scale(scale * 4.1));
      wrote |= beamPlane(buffer, matrix, hilt, shoulder, side, scale * 0.2F, 255, 171, 24, live * 0.28F);
      wrote |= beamPlane(buffer, matrix, hilt, shoulder, up, scale * 0.2F, 255, 171, 24, live * 0.28F);
      wrote |= beamPlane(buffer, matrix, hilt, shoulder, side, scale * 0.115F, 255, 239, 150, live * 0.76F);
      wrote |= beamPlane(buffer, matrix, hilt, shoulder, up, scale * 0.115F, 255, 239, 150, live * 0.76F);
      wrote |= taperedBlade(buffer, matrix, shoulder, tip, side, scale * 0.115F, 255, 253, 225, live * 0.94F);
      wrote |= taperedBlade(buffer, matrix, shoulder, tip, up, scale * 0.115F, 255, 253, 225, live * 0.94F);
      Vec3 guardCenter = hilt.add(dir.scale(scale * 0.06));
      Vec3 guardA = guardCenter.subtract(side.scale(scale * 0.76));
      Vec3 guardB = guardCenter.add(side.scale(scale * 0.76));
      Vec3 guardC = guardCenter.subtract(up.scale(scale * 0.56));
      Vec3 guardD = guardCenter.add(up.scale(scale * 0.56));
      wrote |= ribbon(buffer, matrix, guardA, guardB, scale * 0.085F, scale * 0.04F, 255, 224, 112, live * 0.82F, live * 0.82F);
      wrote |= ribbon(buffer, matrix, guardC, guardD, scale * 0.072F, scale * 0.034F, 255, 246, 191, live * 0.72F, live * 0.72F);
      wrote |= ribbon(buffer, matrix, pommel, hilt, scale * 0.1F, scale * 0.1F, 255, 192, 44, live * 0.66F, live * 0.84F);
      return wrote | orb(buffer, matrix, pommel, scale * (0.15F + 0.02F * pulse01(time * 4.0F)), 255, 255, 238, live * 0.9F);
   }

   private static boolean sovereignCore(BufferBuilder buffer, Matrix4f matrix, Vec3 start, Vec3 end, Vec3 side, Vec3 up, float scale, float surge, int detail) {
      boolean wrote = false;
      int planes = detail >= 3 ? 10 : (detail == 2 ? 7 : 5);

      for (int i = 0; i < planes; i++) {
         double a = i * Math.PI / planes;
         Vec3 axis = side.scale(Math.cos(a)).add(up.scale(Math.sin(a))).normalize();
         wrote |= beamPlane(buffer, matrix, start, end, axis, scale * 3.1F, 255, 112, 0, 0.03F);
         wrote |= beamPlane(buffer, matrix, start, end, axis, scale * 2.25F, 255, 170, 20, 0.09F);
         wrote |= beamPlane(buffer, matrix, start, end, axis, scale * 1.48F, 255, 224, 94, 0.26F);
         wrote |= beamPlane(buffer, matrix, start, end, axis, scale * 0.88F * surge, 255, 250, 211, 0.67F);
         wrote |= beamPlane(buffer, matrix, start, end, axis, scale * 0.31F, 255, 255, 255, 0.98F);
      }

      return wrote;
   }

   private static boolean crownRails(
      BufferBuilder buffer, Matrix4f matrix, Vec3 start, Vec3 end, Vec3 dir, Vec3 side, Vec3 up, float scale, float time, long seed, int detail
   ) {
      int rails = detail >= 3 ? 6 : (detail == 2 ? 4 : 3);
      int segments = detail >= 3 ? 22 : (detail == 2 ? 16 : 11);
      boolean wrote = false;

      for (int lane = 0; lane < rails; lane++) {
         float h = hash(seed, 3100 + lane * 31);
         Vec3 previous = null;

         for (int i = 0; i <= segments; i++) {
            double u = (double)i / segments;
            Vec3 center = start.lerp(end, u);
            double a = lane * (float) (Math.PI * 2) / rails + u * (0.72 + h * 0.42) + time * (0.18 + h * 0.09) * (lane % 2 == 0 ? 1.0 : -1.0);
            double radius = scale * (1.48 + h * 0.34) * (0.92 + Math.sin(u * Math.PI) * 0.1);
            Vec3 point = center.add(side.scale(Math.cos(a) * radius)).add(up.scale(Math.sin(a) * radius));
            if (previous != null) {
               float envelope = (float)(0.38 + Math.sin(u * Math.PI) * 0.62);
               wrote |= ribbon(buffer, matrix, previous, point, scale * 0.04F, scale * 0.014F, 255, 185, 35, envelope * 0.28F, envelope * 0.06F);
               wrote |= ribbon(
                  buffer,
                  matrix,
                  previous.add(dir.scale(0.018)),
                  point.add(dir.scale(0.018)),
                  scale * 0.012F,
                  scale * 0.003F,
                  255,
                  252,
                  222,
                  envelope * 0.72F,
                  0.0F
               );
            }

            previous = point;
         }
      }

      return wrote;
   }

   private static boolean judgementSigils(
      BufferBuilder buffer, Matrix4f matrix, Vec3 start, Vec3 end, Vec3 dir, Vec3 side, Vec3 up, float scale, float time, long seed, int detail
   ) {
      boolean wrote = false;
      double length = start.distanceTo(end);
      int count = detail >= 3 ? 6 : (detail == 2 ? 4 : 3);

      for (int i = 0; i < count; i++) {
         float h = hash(seed, 4300 + i * 41);
         double u = (i + 0.72) / (count + 0.7);
         Vec3 center = start.add(dir.scale(length * u));
         double radius = scale * (1.08 + h * 0.28);
         double spin = time * (0.48 + h * 0.4) * (i % 2 == 0 ? 1.0 : -1.0);
         wrote |= ring(buffer, matrix, center, side, up, radius, scale * 0.01F, detail >= 3 ? 40 : 26, spin, 255, 231, 130, 0.34F);
         int points = detail >= 3 ? 8 : 6;

         for (int p = 0; p < points; p++) {
            double a = p * (float) (Math.PI * 2) / points + spin;
            Vec3 radial = side.scale(Math.cos(a)).add(up.scale(Math.sin(a)));
            Vec3 p0 = center.add(radial.scale(radius * 0.62));
            Vec3 p1 = center.add(radial.scale(radius * 1.06));
            wrote |= ribbon(buffer, matrix, p0, p1, scale * 0.016F, scale * 0.004F, 255, 249, 210, 0.48F, 0.0F);
         }
      }

      return wrote;
   }

   private static boolean beamStardust(
      BufferBuilder buffer, Matrix4f matrix, Vec3 start, Vec3 end, Vec3 side, Vec3 up, float scale, float time, long seed, int detail
   ) {
      boolean wrote = false;
      int count = detail >= 3 ? 86 : (detail == 2 ? 52 : 28);

      for (int i = 0; i < count; i++) {
         float h0 = hash(seed, 7000 + i * 17);
         float h1 = hash(seed, 7100 + i * 19);
         float h2 = hash(seed, 7200 + i * 23);
         double u = (i + h0) / count;
         Vec3 center = start.lerp(end, u);
         double a = h1 * (float) (Math.PI * 2) + time * (0.3 + h2 * 0.32);
         double r = scale * (0.62 + h0 * 1.78);
         Vec3 p = center.add(side.scale(Math.cos(a) * r)).add(up.scale(Math.sin(a) * r));
         float blink = 0.22F + 0.78F * pulse01(time * (6.5F + h1 * 6.0F) + i * 0.43F);
         float size = scale * (0.01F + h2 * 0.024F);
         wrote |= orb(buffer, matrix, p, size * (0.85F + blink * 0.42F), 255, 226 + (int)(h1 * 29.0F), 126 + (int)(h0 * 86.0F), blink * 0.72F);
      }

      return wrote;
   }

   private static boolean judgementImpact(
      BufferBuilder buffer, Matrix4f matrix, Vec3 end, Vec3 dir, Vec3 side, Vec3 up, float scale, float time, long seed, int detail
   ) {
      boolean wrote = false;
      float pulse = 0.9F + 0.1F * pulse01(time * 5.2F);
      wrote |= orb(buffer, matrix, end, scale * 1.56F * pulse, 255, 255, 246, 0.82F);
      wrote |= orb(buffer, matrix, end, scale * 3.1F * pulse, 255, 196, 42, 0.17F);
      int lances = detail >= 3 ? 18 : (detail == 2 ? 12 : 8);

      for (int i = 0; i < lances; i++) {
         float h0 = hash(seed, 9000 + i * 17);
         float h1 = hash(seed, 9100 + i * 19);
         double a = i * (float) (Math.PI * 2) / lances + h0 * 0.18;
         Vec3 radial = side.scale(Math.cos(a)).add(up.scale(Math.sin(a))).normalize();
         Vec3 root = end.add(radial.scale(scale * 0.54));
         Vec3 tip = end.add(radial.scale(scale * (3.2 + h1 * 4.4))).add(dir.scale(scale * (0.18 + h0 * 0.7)));
         wrote |= ribbon(buffer, matrix, root, tip, scale * 0.075F, scale * 0.008F, 255, 208, 69, 0.34F, 0.0F);
         wrote |= ribbon(buffer, matrix, root, tip, scale * 0.024F, scale * 0.002F, 255, 252, 222, 0.82F, 0.0F);
      }

      return wrote;
   }

   private static boolean taperedBlade(BufferBuilder buffer, Matrix4f matrix, Vec3 start, Vec3 tip, Vec3 axis, float width, int r, int g, int b, float alpha) {
      if (alpha <= 0.001F) {
         return false;
      }

      Vec3 n = axis.normalize();
      Vec3 a = start.add(n.scale(width));
      Vec3 c = start.subtract(n.scale(width));
      tri(buffer, matrix, a, c, tip, r, g, b, alpha);
      return true;
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

   private static boolean groundDiamond(
      BufferBuilder buffer, Matrix4f matrix, Vec3 center, double radius, float width, double spin, int r, int g, int b, float alpha
   ) {
      if (!(alpha <= 0.001F) && !(radius <= 0.001)) {
         boolean wrote = false;
         Vec3[] points = new Vec3[4];

         for (int i = 0; i < 4; i++) {
            double a = spin + i * (float) (Math.PI * 2) / 4.0;
            points[i] = center.add(Math.cos(a) * radius, 0.0, Math.sin(a) * radius);
         }

         for (int i = 0; i < 4; i++) {
            Vec3 a = points[i];
            Vec3 c = points[i + 1 & 3];
            Vec3 dir = c.subtract(a);
            Vec3 side = new Vec3(-dir.z, 0.0, dir.x).normalize().scale(width);
            tri(buffer, matrix, a.add(side), a.subtract(side), c.subtract(side), r, g, b, alpha);
            tri(buffer, matrix, a.add(side), c.subtract(side), c.add(side), r, g, b, alpha);
            wrote = true;
         }

         return wrote;
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
         int count = Math.min(52, Math.max(12, segments));
         double inner = Math.max(0.0, radius - width);
         double outer = radius + width;

         for (int i = 0; i < count; i++) {
            double a0 = i * (float) (Math.PI * 2) / count + spin;
            double a1 = (i + 0.88) * (float) (Math.PI * 2) / count + spin;
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
         int segments = 14;

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
      Vec3 d = dir.lengthSqr() < 1.0E-8 ? new Vec3(0.0, 1.0, 0.0) : dir.normalize();
      Vec3 side = d.cross(new Vec3(0.0, 1.0, 0.0));
      if (side.lengthSqr() < 1.0E-8) {
         side = d.cross(new Vec3(1.0, 0.0, 0.0));
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
         return now - this.born > this.duration * 50L + 250L;
      }

      private boolean render(BufferBuilder buffer, Matrix4f matrix, Vec3 camera, long now) {
         float age = (float)(now - this.born) / 50.0F;
         float ignition = AvalonSovereignJudgementVfxClient.smooth(Mth.clamp(age / 5.0F, 0.0F, 1.0F));
         float sustain = 1.0F - AvalonSovereignJudgementVfxClient.smooth(Mth.clamp((age - this.duration + 20.0F) / 20.0F, 0.0F, 1.0F));
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
         double height = 22.0 + this.radius * 2.1;
         boolean wrote = false;
         wrote |= AvalonSovereignJudgementVfxClient.wallLayer(
            buffer, matrix, this.start, this.end, side, height, this.radius * 0.96F, 255, 112, 0, live * 0.07F
         );
         wrote |= AvalonSovereignJudgementVfxClient.wallLayer(
            buffer,
            matrix,
            this.start.add(side.scale(0.04)),
            this.end.add(side.scale(0.04)),
            side,
            height * 0.94,
            this.radius * 0.54F,
            255,
            188,
            27,
            live * 0.18F
         );
         wrote |= AvalonSovereignJudgementVfxClient.wallLayer(
            buffer,
            matrix,
            this.start.add(side.scale(0.08)),
            this.end.add(side.scale(0.08)),
            side,
            height * 0.86,
            this.radius * 0.24F,
            255,
            241,
            166,
            live * 0.44F
         );
         wrote |= AvalonSovereignJudgementVfxClient.wallLayer(
            buffer,
            matrix,
            this.start.add(side.scale(0.12)),
            this.end.add(side.scale(0.12)),
            side,
            height * 0.78,
            this.radius * 0.08F,
            255,
            255,
            250,
            live * 0.86F
         );
         int blades = camera.distanceTo(this.start.lerp(this.end, 0.5)) < 230.0 ? 7 : 4;

         for (int i = 0; i < blades; i++) {
            float h0 = AvalonSovereignJudgementVfxClient.hash(this.seed, 12000 + i * 37);
            float h1 = AvalonSovereignJudgementVfxClient.hash(this.seed, 12100 + i * 41);
            Vec3 base = this.start.lerp(this.end, (i + 0.5) / blades).add(side.scale((h0 - 0.5) * this.radius * 0.48));
            Vec3 top = base.add(side.scale((h1 - 0.5) * this.radius * 0.38)).add(0.0, height * (0.78 + h1 * 0.32), 0.0);
            wrote |= AvalonSovereignJudgementVfxClient.ribbon(buffer, matrix, base, top, 0.28F + h0 * 0.3F, 0.018F, 255, 187, 25, live * 0.22F, 0.0F);
            wrote |= AvalonSovereignJudgementVfxClient.ribbon(
               buffer, matrix, base.add(0.0, 0.03, 0.0), top, 0.085F + h1 * 0.1F, 0.005F, 255, 252, 220, live * 0.76F, 0.0F
            );
         }

         int motes = 6;

         for (int i = 0; i < motes; i++) {
            float h0 = AvalonSovereignJudgementVfxClient.hash(this.seed, 13000 + i * 31);
            float h1 = AvalonSovereignJudgementVfxClient.hash(this.seed, 13100 + i * 37);
            float h2 = AvalonSovereignJudgementVfxClient.hash(this.seed, 13200 + i * 43);
            double cycle = (age * (0.024 + h0 * 0.02) + h1) % 1.0;
            Vec3 base = this.start.lerp(this.end, h0).add(side.scale((h1 - 0.5) * this.radius * 1.3));
            Vec3 p = base.add(0.0, cycle * height * (0.62 + h2 * 0.38), 0.0);
            float blink = 0.26F + 0.74F * AvalonSovereignJudgementVfxClient.pulse01(time * (7.0F + h2 * 7.0F) + i);
            wrote |= AvalonSovereignJudgementVfxClient.orb(
               buffer, matrix, p, 0.07F + h2 * 0.11F, 255, 218 + (int)(h0 * 36.0F), 92 + (int)(h1 * 120.0F), live * blink * (1.0F - (float)cycle * 0.28F)
            );
         }

         return wrote;
      }
   }

   private static final class GroundTrail {
      private final int sourceId;
      private final List<AvalonSovereignJudgementVfxClient.TrailSegment> segments = new ArrayList<>();
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
               this.segments.add(new AvalonSovereignJudgementVfxClient.TrailSegment(start, start.add(0.01, 0.0, 0.01), seed, now));
            }
         } else {
            this.segments.add(new AvalonSovereignJudgementVfxClient.TrailSegment(start, end, seed, now));

            while (this.segments.size() > 60) {
               this.segments.remove(0);
            }
         }
      }

      private void release(long now) {
         this.releasedAt = now;
      }

      private boolean dead(long now) {
         return this.releasedAt != 0L ? now - this.releasedAt > 2600L : now - this.lastTouch > 1600L;
      }

      private boolean render(BufferBuilder buffer, Matrix4f matrix, Vec3 camera, long now) {
         boolean wrote = false;
         float releaseFade = this.releasedAt == 0L
            ? 1.0F
            : 1.0F - AvalonSovereignJudgementVfxClient.smooth(Mth.clamp((float)(now - this.releasedAt) / 2600.0F, 0.0F, 1.0F));
         int step = this.segments.size() > 40 && camera.distanceTo(this.segments.get(this.segments.size() - 1).end) > 200.0 ? 2 : 1;

         for (int i = 0; i < this.segments.size(); i += step) {
            wrote |= this.segments.get(i).render(buffer, matrix, now, releaseFade, i, this.sourceId);
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
         float t = AvalonSovereignJudgementVfxClient.smooth(Mth.clamp((float)(now - this.interpolationStart) / 92.0F, 0.0F, 1.0F));
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

         Vec3 midpoint = start.lerp(end, 0.5);
         double distance = camera.distanceTo(midpoint);
         if (distance > 920.0) {
            return false;
         }

         int detail = distance < 140.0 ? 3 : (distance < 320.0 ? 2 : 1);
         Vec3 dir = direction.normalize();
         Vec3 side = AvalonSovereignJudgementVfxClient.safeSide(dir);
         Vec3 up = dir.cross(side).normalize();
         float scale = Mth.clamp(this.radius, 8.8F, 13.0F);
         float time = (float)now * 0.001F;
         float age = (float)(now - this.born) / 50.0F;
         float form = AvalonSovereignJudgementVfxClient.smooth(Mth.clamp(age / 7.0F, 0.0F, 1.0F));
         float surge = 0.94F + 0.06F * AvalonSovereignJudgementVfxClient.pulse01(time * 5.4F);
         boolean wrote = false;
         wrote |= AvalonSovereignJudgementVfxClient.royalMuzzle(buffer, matrix, start, dir, side, up, scale, form, time, this.seed, detail);
         wrote |= AvalonSovereignJudgementVfxClient.embeddedExcalibur(buffer, matrix, start, dir, side, up, scale, form, time);
         wrote |= AvalonSovereignJudgementVfxClient.sovereignCore(buffer, matrix, start, end, side, up, scale, surge, detail);
         wrote |= AvalonSovereignJudgementVfxClient.crownRails(buffer, matrix, start, end, dir, side, up, scale, time, this.seed, detail);
         wrote |= AvalonSovereignJudgementVfxClient.judgementSigils(buffer, matrix, start, end, dir, side, up, scale, time, this.seed, detail);
         wrote |= AvalonSovereignJudgementVfxClient.beamStardust(buffer, matrix, start, end, side, up, scale, time, this.seed, detail);
         return wrote | AvalonSovereignJudgementVfxClient.judgementImpact(buffer, matrix, end, dir, side, up, scale, time, this.seed, detail);
      }
   }

   private static final class TerminalBurst {
      private final Vec3 center;
      private final float radius;
      private final long seed;
      private final long born;
      private final int duration;

      private TerminalBurst(Vec3 center, float radius, long seed, long born, int duration) {
         this.center = center;
         this.radius = radius;
         this.seed = seed;
         this.born = born;
         this.duration = duration;
      }

      private boolean dead(long now) {
         return now - this.born > this.duration * 50L + 300L;
      }

      private boolean render(BufferBuilder buffer, Matrix4f matrix, Vec3 camera, long now) {
         float age = (float)(now - this.born) / 50.0F;
         float ignition = AvalonSovereignJudgementVfxClient.smooth(Mth.clamp(age / 4.0F, 0.0F, 1.0F));
         float fade = 1.0F
            - AvalonSovereignJudgementVfxClient.smooth(Mth.clamp((age - this.duration * 0.58F) / Math.max(1.0F, this.duration * 0.42F), 0.0F, 1.0F));
         float live = ignition * fade;
         if (live <= 0.001F) {
            return false;
         }

         float scale = Mth.clamp(this.radius, 12.0F, 22.0F);
         float expand = AvalonSovereignJudgementVfxClient.smooth(Mth.clamp(age / 18.0F, 0.0F, 1.0F));
         boolean wrote = false;
         Vec3 core = this.center.add(0.0, scale * (0.12 + expand * 0.34), 0.0);
         wrote |= AvalonSovereignJudgementVfxClient.orb(buffer, matrix, core, scale * (1.4F + expand * 0.92F), 255, 255, 255, live * 0.96F);
         wrote |= AvalonSovereignJudgementVfxClient.orb(buffer, matrix, core, scale * (2.8F + expand * 1.9F), 255, 242, 173, live * 0.42F);
         wrote |= AvalonSovereignJudgementVfxClient.orb(buffer, matrix, core, scale * (4.8F + expand * 3.4F), 255, 154, 12, live * 0.11F);
         int lances = camera.distanceTo(this.center) < 260.0 ? 22 : 14;

         for (int i = 0; i < lances; i++) {
            float h0 = AvalonSovereignJudgementVfxClient.hash(this.seed, 16000 + i * 17);
            float h1 = AvalonSovereignJudgementVfxClient.hash(this.seed, 16100 + i * 19);
            double a = i * (float) (Math.PI * 2) / lances + h0 * 0.22;
            Vec3 radial = new Vec3(Math.cos(a), 0.18 + h1 * 0.82, Math.sin(a)).normalize();
            Vec3 start = core.add(radial.scale(scale * (0.6 + expand * 0.44)));
            Vec3 end = core.add(radial.scale(scale * (4.4 + h0 * 6.8 + expand * 3.6)));
            wrote |= AvalonSovereignJudgementVfxClient.ribbon(buffer, matrix, start, end, scale * 0.055F, scale * 0.006F, 255, 188, 31, live * 0.32F, 0.0F);
            wrote |= AvalonSovereignJudgementVfxClient.ribbon(buffer, matrix, start, end, scale * 0.018F, scale * 0.002F, 255, 252, 222, live * 0.86F, 0.0F);
         }

         Vec3 verticalTop = this.center.add(0.0, scale * (7.0 + expand * 5.0), 0.0);
         wrote |= AvalonSovereignJudgementVfxClient.ribbon(
            buffer, matrix, this.center, verticalTop, scale * 0.66F, scale * 0.08F, 255, 177, 21, live * 0.2F, 0.0F
         );
         wrote |= AvalonSovereignJudgementVfxClient.ribbon(
            buffer, matrix, this.center.add(0.0, 0.04, 0.0), verticalTop, scale * 0.24F, scale * 0.03F, 255, 251, 217, live * 0.72F, 0.0F
         );
         return wrote
            | AvalonSovereignJudgementVfxClient.groundDiamond(
               buffer, matrix, this.center.add(0.0, 0.08, 0.0), scale * (1.3F + expand * 1.8F), scale * 0.04F, now * 4.5E-4, 255, 231, 132, live * 0.28F
            );
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

      private boolean render(BufferBuilder buffer, Matrix4f matrix, long now, float fade, int index, int sourceId) {
         float age = (float)(now - this.born) / 50.0F;
         float appear = AvalonSovereignJudgementVfxClient.smooth(Mth.clamp(age / 4.0F, 0.0F, 1.0F));
         float live = appear * fade;
         if (live <= 0.001F) {
            return false;
         }

         boolean wrote = false;
         Vec3 a = this.start.add(0.0, 0.032, 0.0);
         Vec3 b = this.end.add(0.0, 0.032, 0.0);
         wrote |= AvalonSovereignJudgementVfxClient.groundRibbon(buffer, matrix, a, b, 2.3F, 255, 118, 0, live * 0.08F);
         wrote |= AvalonSovereignJudgementVfxClient.groundRibbon(buffer, matrix, a.add(0.0, 0.01, 0.0), b.add(0.0, 0.01, 0.0), 1.2F, 255, 193, 37, live * 0.24F);
         wrote |= AvalonSovereignJudgementVfxClient.groundRibbon(
            buffer, matrix, a.add(0.0, 0.02, 0.0), b.add(0.0, 0.02, 0.0), 0.38F, 255, 252, 219, live * 0.74F
         );
         if (index % 4 == 0) {
            Vec3 center = a.lerp(b, 0.5).add(0.0, 0.01, 0.0);
            wrote |= AvalonSovereignJudgementVfxClient.groundDiamond(
               buffer, matrix, center, 1.2F, 0.06F, now * 3.4E-4 + index * 0.7, 255, 234, 145, live * 0.34F
            );
         }

         for (int m = 0; m < 2; m++) {
            float h0 = AvalonSovereignJudgementVfxClient.hash(this.seed ^ sourceId * 31L, 8000 + index * 17 + m * 43);
            float h1 = AvalonSovereignJudgementVfxClient.hash(this.seed ^ sourceId * 47L, 9000 + index * 19 + m * 53);
            float h2 = AvalonSovereignJudgementVfxClient.hash(this.seed ^ sourceId * 59L, 10000 + index * 23 + m * 61);
            double cycle = (now * (1.8E-4 + h0 * 1.3E-4) + h1 * 7.0) % 1.0;
            Vec3 base = a.lerp(b, h0);
            Vec3 tangent = b.subtract(a);
            Vec3 side = tangent.lengthSqr() < 1.0E-8 ? new Vec3(1.0, 0.0, 0.0) : new Vec3(-tangent.z, 0.0, tangent.x).normalize();
            Vec3 p = base.add(side.scale((h1 - 0.5) * 2.0)).add(0.0, 0.1 + cycle * (1.8 + h2 * 2.8), 0.0);
            float blink = 0.24F + 0.76F * AvalonSovereignJudgementVfxClient.pulse01((float)now * (0.008F + h2 * 0.008F) + index + m * 2.1F);
            wrote |= AvalonSovereignJudgementVfxClient.orb(
               buffer, matrix, p, 0.045F + h2 * 0.065F, 255, 220 + (int)(h0 * 35.0F), 104 + (int)(h1 * 104.0F), live * blink * (1.0F - (float)cycle * 0.32F)
            );
         }

         return wrote;
      }
   }
}
