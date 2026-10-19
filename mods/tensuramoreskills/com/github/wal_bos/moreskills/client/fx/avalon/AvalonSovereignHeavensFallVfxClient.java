package com.github.wal_bos.moreskills.client.fx.avalon;

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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent.Post;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent.Stage;
import net.neoforged.neoforge.common.NeoForge;
import org.joml.Matrix4f;

public final class AvalonSovereignHeavensFallVfxClient {
   private static final Map<Integer, AvalonSovereignHeavensFallVfxClient.Opening> OPENINGS = new LinkedHashMap<>();
   private static final List<AvalonSovereignHeavensFallVfxClient.Strike> STRIKES = new ArrayList<>();
   private static final int FIRE_START = 3;
   private static final int BEAM_LIFE = 86;
   private static final int TRAIL_POINTS = 34;
   private static final int MAX_OPENINGS = 4;
   private static final int MAX_STRIKES = 96;
   private static final int DEEP_R = 255;
   private static final int DEEP_G = 112;
   private static final int DEEP_B = 5;
   private static final int GOLD_R = 255;
   private static final int GOLD_G = 190;
   private static final int GOLD_B = 31;
   private static final int HOT_R = 255;
   private static final int HOT_G = 232;
   private static final int HOT_B = 112;
   private static final int PALE_R = 255;
   private static final int PALE_G = 250;
   private static final int PALE_B = 218;
   private static boolean initialized;

   private AvalonSovereignHeavensFallVfxClient() {
   }

   public static void init() {
      if (!initialized) {
         initialized = true;
         NeoForge.EVENT_BUS.addListener(AvalonSovereignHeavensFallVfxClient::clientTick);
         NeoForge.EVENT_BUS.addListener(AvalonSovereignHeavensFallVfxClient::render);
      }
   }

   public static void spawn(ArthurAvalonVfxData payload) {
      if (ClientVfxGate.allowed("avalon_sovereign_heavens_fall")) {
         init();
         Minecraft minecraft = Minecraft.getInstance();
         if (minecraft.level != null && minecraft.player != null) {
            double maxDistance = 760.0;
            if (!(minecraft.player.distanceToSqr(payload.start()) > maxDistance * maxDistance)
               || !(minecraft.player.distanceToSqr(payload.end()) > maxDistance * maxDistance)) {
               long now = Util.getMillis();
               if (payload.variant() < 0) {
                  int key = payload.sourceId() >= 0 ? payload.sourceId() : (int)(payload.seed() ^ payload.seed() >>> 32);
                  OPENINGS.put(key, new AvalonSovereignHeavensFallVfxClient.Opening(payload, now));

                  while (OPENINGS.size() > 4) {
                     OPENINGS.remove(OPENINGS.keySet().iterator().next());
                  }
               } else {
                  STRIKES.add(new AvalonSovereignHeavensFallVfxClient.Strike(payload));

                  while (STRIKES.size() > 96) {
                     STRIKES.remove(0);
                  }
               }
            }
         }
      }
   }

   private static void clientTick(Post event) {
      if (!ClientVfxGate.allowed("avalon_sovereign_heavens_fall")) {
         OPENINGS.clear();
         STRIKES.clear();
      } else {
         Minecraft minecraft = Minecraft.getInstance();
         if (minecraft.level == null) {
            OPENINGS.clear();
            STRIKES.clear();
         } else {
            Iterator<AvalonSovereignHeavensFallVfxClient.Strike> iterator = STRIKES.iterator();

            while (iterator.hasNext()) {
               AvalonSovereignHeavensFallVfxClient.Strike strike = iterator.next();
               strike.tick(minecraft);
               if (strike.dead()) {
                  iterator.remove();
               }
            }
         }
      }
   }

   private static void render(RenderLevelStageEvent event) {
      if (!ClientVfxGate.allowed("avalon_sovereign_heavens_fall")) {
         OPENINGS.clear();
         STRIKES.clear();
      } else if (event.getStage() == Stage.AFTER_PARTICLES) {
         Minecraft minecraft = Minecraft.getInstance();
         if (minecraft.level != null && minecraft.player != null) {
            if (!OPENINGS.isEmpty() || !STRIKES.isEmpty()) {
               long now = Util.getMillis();
               Camera cameraObject = minecraft.gameRenderer.getMainCamera();
               Vec3 camera = cameraObject.getPosition();
               event.getPoseStack().pushPose();
               event.getPoseStack().translate(-camera.x, -camera.y, -camera.z);
               Matrix4f matrix = event.getPoseStack().last().pose();
               RenderSystem.enableBlend();
               RenderSystem.blendFuncSeparate(SourceFactor.SRC_ALPHA, DestFactor.ONE, SourceFactor.ONE, DestFactor.ONE_MINUS_SRC_ALPHA);
               RenderSystem.disableCull();
               RenderSystem.enableDepthTest();
               RenderSystem.depthMask(false);
               RenderSystem.setShader(GameRenderer::getPositionColorShader);
               BufferBuilder buffer = Tesselator.getInstance().begin(Mode.TRIANGLES, DefaultVertexFormat.POSITION_COLOR);
               boolean wrote = false;
               Iterator<Entry<Integer, AvalonSovereignHeavensFallVfxClient.Opening>> openingIterator = OPENINGS.entrySet().iterator();

               while (openingIterator.hasNext()) {
                  AvalonSovereignHeavensFallVfxClient.Opening opening = openingIterator.next().getValue();
                  if (opening.dead(now)) {
                     openingIterator.remove();
                  } else {
                     wrote |= opening.render(buffer, matrix, camera, now);
                  }
               }

               float partial = event.getPartialTick().getGameTimeDeltaPartialTick(false);
               int crowdDetail = STRIKES.size() >= 48 ? 1 : (STRIKES.size() >= 24 ? 2 : 3);

               for (AvalonSovereignHeavensFallVfxClient.Strike strike : STRIKES) {
                  wrote |= strike.render(buffer, matrix, camera, partial, now, crowdDetail);
               }

               if (wrote) {
                  try {
                     BufferUploader.drawWithShader(buffer.buildOrThrow());
                  } catch (IllegalStateException var14) {
                  }
               }

               event.getPoseStack().popPose();
               RenderSystem.depthMask(true);
               RenderSystem.enableCull();
               RenderSystem.defaultBlendFunc();
               RenderSystem.disableBlend();
            }
         }
      }
   }

   private static boolean chargeSocket(
      BufferBuilder buffer, Matrix4f matrix, Vec3 camera, Vec3 center, Vec3 outward, float scale, float live, float time, long seed, int index
   ) {
      Vec3 start = center.subtract(outward.scale(scale * 0.34));
      Vec3 end = center.add(outward.scale(scale * 1.48));
      boolean wrote = false;
      wrote |= beamQuad(buffer, matrix, camera, start, end, scale * 0.64, 255, 112, 5, live * 0.08F);
      wrote |= beamQuad(buffer, matrix, camera, start, end, scale * 0.34, 255, 190, 31, live * 0.22F);
      wrote |= beamQuad(buffer, matrix, camera, start, end, scale * 0.15, 255, 250, 218, live * 0.72F);
      wrote |= beamQuad(buffer, matrix, camera, start, end, scale * 0.052, 255, 255, 255, live);
      float pulse = 0.94F + 0.06F * Mth.sin(time * 4.0F + index);
      wrote |= softOrb(buffer, matrix, camera, center, scale * 0.9 * pulse, 255, 190, 31, live * 0.18F, 12);
      wrote |= softOrb(buffer, matrix, camera, center, scale * 0.42 * pulse, 255, 250, 218, live * 0.66F, 12);
      return wrote | softOrb(buffer, matrix, camera, center, scale * 0.15 * pulse, 255, 255, 255, live, 10);
   }

   private static boolean lanceHead(
      BufferBuilder buffer, Matrix4f matrix, Vec3 camera, Vec3 center, Vec3 target, float scale, float live, float time, long seed, int index, int detail
   ) {
      Vec3 forward = target.subtract(center);
      if (forward.lengthSqr() < 1.0E-8) {
         forward = new Vec3(0.0, 0.0, 1.0);
      }

      forward = forward.normalize();
      Vec3 back = center.subtract(forward.scale(scale * 0.5));
      Vec3 tip = center.add(forward.scale(scale * 1.35));
      boolean wrote = false;
      wrote |= beamQuad(buffer, matrix, camera, back, tip, scale * 0.66, 255, 112, 5, live * 0.1F);
      wrote |= beamQuad(buffer, matrix, camera, back, tip, scale * 0.34, 255, 190, 31, live * 0.3F);
      wrote |= beamQuad(buffer, matrix, camera, back, tip, scale * 0.15, 255, 250, 218, live * 0.82F);
      wrote |= beamQuad(buffer, matrix, camera, back, tip, scale * 0.05, 255, 255, 255, live);
      float pulse = 0.96F + 0.04F * Mth.sin(time * 5.4F + index * 0.37F);
      wrote |= softOrb(buffer, matrix, camera, center, scale * 1.2 * pulse, 255, 112, 5, live * 0.055F, 14);
      wrote |= softOrb(buffer, matrix, camera, center, scale * 0.7 * pulse, 255, 190, 31, live * 0.16F, 14);
      wrote |= softOrb(buffer, matrix, camera, center, scale * 0.38 * pulse, 255, 250, 218, live * 0.54F, 12);
      wrote |= softOrb(buffer, matrix, camera, center, scale * 0.14 * pulse, 255, 255, 255, live, 10);
      if (detail >= 2) {
         Vec3 side = safeSide(forward);
         Vec3 up = forward.cross(side).normalize();
         int fins = detail >= 3 ? 6 : 4;

         for (int i = 0; i < fins; i++) {
            float h = hash(seed, 5000 + index * 97 + i * 31);
            double a = i * (float) (Math.PI * 2) / fins + time * 0.45;
            Vec3 radial = side.scale(Math.cos(a)).add(up.scale(Math.sin(a))).normalize();
            Vec3 a0 = center.subtract(forward.scale(scale * 0.18)).add(radial.scale(scale * 0.1));
            Vec3 b0 = center.subtract(forward.scale(scale * (0.62 + h * 0.35))).add(radial.scale(scale * (0.38 + h * 0.35)));
            wrote |= beamQuad(buffer, matrix, camera, a0, b0, scale * 0.055, 255, 232, 112, live * 0.38F);
            wrote |= beamQuad(buffer, matrix, camera, a0, b0, scale * 0.018, 255, 250, 218, live * 0.82F);
         }
      }

      return wrote;
   }

   private static boolean impact(
      BufferBuilder buffer, Matrix4f matrix, Vec3 camera, Vec3 center, float scale, float live, float burstAge, float time, long seed, int index, int detail
   ) {
      float envelope = 1.0F - smoother(Mth.clamp((burstAge - 1.0F) / 18.0F, 0.0F, 1.0F));
      if (envelope <= 0.001F) {
         return false;
      }

      float flash = 1.0F - smoother(Mth.clamp((burstAge - 1.0F) / 7.0F, 0.0F, 1.0F));
      boolean wrote = false;
      wrote |= softOrb(buffer, matrix, camera, center, scale * (2.8 + flash * 1.8), 255, 112, 5, envelope * 0.08F, 18);
      wrote |= softOrb(buffer, matrix, camera, center, scale * (1.75 + flash * 1.15), 255, 190, 31, envelope * 0.22F, 18);
      wrote |= softOrb(buffer, matrix, camera, center, scale * (0.92 + flash * 0.72), 255, 250, 218, envelope * 0.64F, 16);
      wrote |= softOrb(buffer, matrix, camera, center, scale * (0.32 + flash * 0.28), 255, 255, 255, envelope, 14);
      int rays = detail >= 3 ? 16 : (detail == 2 ? 11 : 7);

      for (int i = 0; i < rays; i++) {
         float h0 = hash(seed, 7000 + index * 113 + i * 17);
         float h1 = hash(seed, 7100 + index * 127 + i * 19);
         double yaw = i * (float) (Math.PI * 2) / rays + h0 * 0.3;
         Vec3 dir = new Vec3(Math.cos(yaw), -0.12 + h1 * 0.86, Math.sin(yaw)).normalize();
         Vec3 a = center.add(dir.scale(scale * 0.12));
         Vec3 b = center.add(dir.scale(scale * (1.7 + h0 * 3.3)));
         wrote |= beamQuad(buffer, matrix, camera, a, b, scale * (0.1 + h1 * 0.06), 255, 190, 31, envelope * 0.24F);
         wrote |= beamQuad(buffer, matrix, camera, a, b, scale * (0.03 + h0 * 0.02), 255, 250, 218, envelope * 0.82F);
      }

      if (detail >= 2) {
         int motes = detail >= 3 ? 18 : 10;

         for (int i = 0; i < motes; i++) {
            float h0 = hash(seed, 8000 + index * 139 + i * 23);
            float h1 = hash(seed, 8100 + index * 149 + i * 29);
            float h2 = hash(seed, 8200 + index * 151 + i * 31);
            double a = h0 * (float) (Math.PI * 2) + time * 0.18;
            double rr = scale * (0.3 + h1 * 2.7) * (1.0 - envelope * 0.28);
            Vec3 p = center.add(Math.cos(a) * rr, scale * (h2 - 0.15) * 1.6, Math.sin(a) * rr);
            wrote |= goldMote(buffer, matrix, camera, p, scale * (0.018F + h2 * 0.032F), envelope * (0.38F + h1 * 0.46F));
         }
      }

      return wrote;
   }

   private static long trajectorySeed(int sourceId, int targetId) {
      return (long)sourceId << 32 ^ targetId & 4294967295L ^ 7640891576956012809L;
   }

   private static Vec3 socket(Vec3 caster, AvalonSovereignHeavensFallVfxClient.Basis basis, double t, int index, long seed) {
      double angle = index * 2.399963229728653 + seed * 3.1E-4 + t * 0.03;
      double ring = 1.95 + index % 7 * 0.3 + hash(seed, index + 4) * 0.42;
      double wave = Math.sin(t * 0.12 + index) * 0.3;
      return caster.add(basis.right.scale(Math.cos(angle) * ring)).add(basis.up.scale(Math.sin(angle) * ring + wave)).subtract(basis.forward.scale(0.26));
   }

   private static Vec3 radialOut(AvalonSovereignHeavensFallVfxClient.Basis basis, int index, double t, long seed) {
      double angle = index * 2.399963229728653 + seed * 3.1E-4 + t * 0.014;
      return basis.right.scale(Math.cos(angle)).add(basis.up.scale(Math.sin(angle))).normalize();
   }

   private static AvalonSovereignHeavensFallVfxClient.Basis basis(Vec3 caster, Vec3 target) {
      Vec3 forward = target.subtract(caster);
      if (forward.lengthSqr() < 1.0E-4) {
         forward = new Vec3(0.0, 0.0, 1.0);
      } else {
         forward = forward.normalize();
      }

      Vec3 right = forward.cross(new Vec3(0.0, 1.0, 0.0));
      if (right.lengthSqr() < 1.0E-4) {
         right = new Vec3(1.0, 0.0, 0.0);
      } else {
         right = right.normalize();
      }

      Vec3 up = right.cross(forward).normalize();
      return new AvalonSovereignHeavensFallVfxClient.Basis(forward, right, up);
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
         int count = Math.max(12, segments);
         double inner = Math.max(0.001, radius - width);
         double outer = radius + width;

         for (int i = 0; i < count; i++) {
            double a0 = spin + i * (float) (Math.PI * 2) / count;
            double a1 = spin + (i + 1.0) * (float) (Math.PI * 2) / count;
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

   private static boolean goldMote(BufferBuilder buffer, Matrix4f matrix, Vec3 camera, Vec3 center, float size, float alpha) {
      boolean wrote = false;
      wrote |= softOrb(buffer, matrix, camera, center, size * 3.0, 255, 112, 5, alpha * 0.09F, 9);
      wrote |= softOrb(buffer, matrix, camera, center, size * 1.45, 255, 232, 112, alpha * 0.48F, 9);
      return wrote | softOrb(buffer, matrix, camera, center, size * 0.48, 255, 255, 245, alpha, 9);
   }

   private static boolean softOrb(
      BufferBuilder buffer, Matrix4f matrix, Vec3 camera, Vec3 center, double radius, int r, int g, int b, float alpha, int segments
   ) {
      if (!(alpha <= 0.001F) && !(radius <= 0.001)) {
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
         int count = Math.max(8, segments);

         for (int i = 0; i < count; i++) {
            double a0 = i * (float) (Math.PI * 2) / count;
            double a1 = (i + 1.0) * (float) (Math.PI * 2) / count;
            Vec3 p0 = center.add(right.scale(Math.cos(a0) * radius)).add(up.scale(Math.sin(a0) * radius));
            Vec3 p1 = center.add(right.scale(Math.cos(a1) * radius)).add(up.scale(Math.sin(a1) * radius));
            triGradient(buffer, matrix, center, p0, p1, r, g, b, alpha, 0.0F, 0.0F);
         }

         return true;
      } else {
         return false;
      }
   }

   private static boolean beamQuad(BufferBuilder buffer, Matrix4f matrix, Vec3 camera, Vec3 start, Vec3 end, double width, int r, int g, int b, float alpha) {
      if (alpha <= 0.001F) {
         return false;
      }

      Vec3 dir = end.subtract(start);
      if (dir.lengthSqr() < 1.0E-8) {
         return false;
      }

      Vec3 mid = start.add(end).scale(0.5);
      Vec3 side = dir.cross(camera.subtract(mid));
      if (side.lengthSqr() < 1.0E-8) {
         side = safeSide(dir);
      } else {
         side = side.normalize();
      }

      side = side.scale(width);
      Vec3 a = start.add(side);
      Vec3 b0 = end.add(side);
      Vec3 c = end.subtract(side);
      Vec3 d = start.subtract(side);
      tri(buffer, matrix, a, b0, c, r, g, b, alpha);
      tri(buffer, matrix, a, c, d, r, g, b, alpha);
      return true;
   }

   private static Vec3 safeSide(Vec3 dir) {
      Vec3 d = dir.lengthSqr() < 1.0E-8 ? new Vec3(0.0, 0.0, 1.0) : dir.normalize();
      Vec3 side = d.cross(new Vec3(0.0, 1.0, 0.0));
      if (side.lengthSqr() < 1.0E-8) {
         side = d.cross(new Vec3(1.0, 0.0, 0.0));
      }

      if (side.lengthSqr() < 1.0E-8) {
         side = new Vec3(1.0, 0.0, 0.0);
      }

      return side.normalize();
   }

   private static int detailLevel(double distance) {
      if (distance < 100.0) {
         return 3;
      } else {
         return distance < 240.0 ? 2 : 1;
      }
   }

   private static float smoother(float value) {
      value = Mth.clamp(value, 0.0F, 1.0F);
      return value * value * value * (value * (value * 6.0F - 15.0F) + 10.0F);
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

   private record Basis(Vec3 forward, Vec3 right, Vec3 up) {
   }

   private static final class Opening {
      private final ArthurAvalonVfxData payload;
      private final long born;

      private Opening(ArthurAvalonVfxData payload, long born) {
         this.payload = payload;
         this.born = born;
      }

      private boolean dead(long now) {
         return now - this.born > Math.max(58, this.payload.durationTicks()) * 50L + 250L;
      }

      private boolean render(BufferBuilder buffer, Matrix4f matrix, Vec3 camera, long now) {
         Minecraft minecraft = Minecraft.getInstance();
         Entity source = minecraft.level != null && this.payload.sourceId() >= 0 ? minecraft.level.getEntity(this.payload.sourceId()) : null;
         Vec3 center = source == null ? this.payload.start().add(0.0, 1.0, 0.0) : source.position().add(0.0, source.getBbHeight() * 0.64, 0.0);
         float age = (float)(now - this.born) / 50.0F;
         float life = Math.max(58.0F, this.payload.durationTicks());
         float intro = AvalonSovereignHeavensFallVfxClient.smoother(Mth.clamp(age / 4.0F, 0.0F, 1.0F));
         float outro = 1.0F - AvalonSovereignHeavensFallVfxClient.smoother(Mth.clamp((age - life + 14.0F) / 14.0F, 0.0F, 1.0F));
         float live = intro * outro;
         if (live <= 0.001F) {
            return false;
         }

         double distance = camera.distanceTo(center);
         int detail = AvalonSovereignHeavensFallVfxClient.detailLevel(distance);
         float time = (float)now * 0.001F;
         float radius = Mth.clamp(this.payload.radius(), 8.0F, 72.0F);
         Vec3 forward = source == null ? new Vec3(0.0, 0.0, 1.0) : source.getLookAngle();
         if (forward.lengthSqr() < 1.0E-8) {
            forward = new Vec3(0.0, 0.0, 1.0);
         }

         forward = forward.normalize();
         Vec3 right = AvalonSovereignHeavensFallVfxClient.safeSide(forward);
         Vec3 up = forward.cross(right).normalize();
         boolean wrote = false;
         float settle = 0.97F + 0.03F * Mth.sin(time * 2.1F);
         wrote |= AvalonSovereignHeavensFallVfxClient.softOrb(buffer, matrix, camera, center, 4.8 * settle, 255, 112, 5, live * 0.045F, 18);
         wrote |= AvalonSovereignHeavensFallVfxClient.softOrb(buffer, matrix, camera, center, 2.8 * settle, 255, 190, 31, live * 0.12F, 18);
         wrote |= AvalonSovereignHeavensFallVfxClient.softOrb(buffer, matrix, camera, center, 1.42 * settle, 255, 250, 218, live * 0.46F, 18);
         wrote |= AvalonSovereignHeavensFallVfxClient.softOrb(buffer, matrix, camera, center, 0.52 * settle, 255, 255, 255, live * 0.92F, 16);
         double baseRadius = 2.3 + Math.min(2.2, radius * 0.035);
         wrote |= AvalonSovereignHeavensFallVfxClient.ring(
            buffer,
            matrix,
            center.subtract(forward.scale(0.16)),
            right,
            up,
            baseRadius * 1.42,
            0.055F,
            detail >= 2 ? 52 : 32,
            time * 0.22,
            255,
            190,
            31,
            live * 0.18F
         );
         wrote |= AvalonSovereignHeavensFallVfxClient.ring(
            buffer,
            matrix,
            center.subtract(forward.scale(0.1)),
            right,
            up,
            baseRadius,
            0.038F,
            detail >= 2 ? 48 : 30,
            -time * 0.34,
            255,
            232,
            112,
            live * 0.36F
         );
         wrote |= AvalonSovereignHeavensFallVfxClient.ring(
            buffer,
            matrix,
            center.subtract(forward.scale(0.04)),
            right,
            up,
            baseRadius * 0.56,
            0.026F,
            detail >= 2 ? 40 : 26,
            time * 0.48,
            255,
            250,
            218,
            live * 0.62F
         );
         int rays = detail >= 3 ? 18 : (detail == 2 ? 12 : 8);

         for (int i = 0; i < rays; i++) {
            float h = AvalonSovereignHeavensFallVfxClient.hash(this.payload.seed(), 1000 + i * 31);
            double a = i * (float) (Math.PI * 2) / rays + time * 0.09;
            Vec3 radial = right.scale(Math.cos(a)).add(up.scale(Math.sin(a))).normalize();
            Vec3 a0 = center.add(radial.scale(baseRadius * 0.35));
            Vec3 b0 = center.add(radial.scale(baseRadius * (1.75 + h * 0.55))).subtract(forward.scale(0.35 + h * 0.45));
            wrote |= AvalonSovereignHeavensFallVfxClient.beamQuad(buffer, matrix, camera, a0, b0, 0.1 + h * 0.08, 255, 190, 31, live * (0.1F + h * 0.1F));
            wrote |= AvalonSovereignHeavensFallVfxClient.beamQuad(buffer, matrix, camera, a0, b0, 0.025 + h * 0.02, 255, 250, 218, live * (0.46F + h * 0.24F));
         }

         int motes = detail >= 3 ? 54 : (detail == 2 ? 34 : 18);

         for (int i = 0; i < motes; i++) {
            float h0 = AvalonSovereignHeavensFallVfxClient.hash(this.payload.seed(), 2000 + i * 17);
            float h1 = AvalonSovereignHeavensFallVfxClient.hash(this.payload.seed(), 2100 + i * 19);
            float h2 = AvalonSovereignHeavensFallVfxClient.hash(this.payload.seed(), 2200 + i * 23);
            double a = h0 * (float) (Math.PI * 2) + time * (0.16 + h2 * 0.08);
            double rr = 0.7 + h1 * (2.3 + radius * 0.025);
            double y = (h2 - 0.32) * 3.2 + Mth.sin(time * 1.8F + i) * 0.12;
            Vec3 p = center.add(Math.cos(a) * rr, y, Math.sin(a) * rr);
            wrote |= AvalonSovereignHeavensFallVfxClient.goldMote(buffer, matrix, camera, p, 0.028F + h2 * 0.052F, live * (0.3F + h1 * 0.44F));
         }

         return wrote;
      }
   }

   private static final class Strike {
      private final ArthurAvalonVfxData payload;
      private final int index;
      private final int sourceId;
      private final int targetId;
      private final long seed;
      private final long trajectorySeed;
      private final Vec3 fallbackCaster;
      private final Vec3 fallbackTarget;
      private final Vec3[] trail = new Vec3[34];
      private int age;
      private int beamAge;
      private int burstAge;
      private boolean active;
      private boolean missed;
      private Vec3 pos = Vec3.ZERO;
      private Vec3 prevPos = Vec3.ZERO;
      private Vec3 vel = Vec3.ZERO;
      private Vec3 smoothTarget = Vec3.ZERO;
      private Vec3 prevSmoothTarget = Vec3.ZERO;
      private Vec3 impactPos;

      private Strike(ArthurAvalonVfxData payload) {
         this.payload = payload;
         this.index = Math.max(0, payload.variant());
         this.sourceId = payload.sourceId();
         this.targetId = payload.targetId();
         this.seed = payload.seed();
         this.trajectorySeed = AvalonSovereignHeavensFallVfxClient.trajectorySeed(this.sourceId, this.targetId);
         this.fallbackCaster = payload.start();
         this.fallbackTarget = payload.end();
      }

      private void tick(Minecraft minecraft) {
         this.age++;
         Vec3 caster = this.resolveCaster(minecraft, 1.0F);
         Vec3 target = this.resolveTarget(minecraft, 1.0F);
         AvalonSovereignHeavensFallVfxClient.Basis basis = AvalonSovereignHeavensFallVfxClient.basis(caster, target);
         if (this.age >= 3) {
            Vec3 socket = AvalonSovereignHeavensFallVfxClient.socket(caster, basis, this.age, this.index, this.trajectorySeed);
            if (this.active) {
               this.beamAge++;
               this.prevPos = this.pos;
               this.prevSmoothTarget = this.smoothTarget;
               double targetLerp = this.beamAge < 14 ? 0.16 : 0.34;
               this.smoothTarget = this.smoothTarget.lerp(target, targetLerp);
               if (this.burstAge > 0) {
                  this.burstAge++;
                  this.pos = this.pos.add(this.vel.scale(0.08));
                  this.vel = this.vel.scale(0.64);
                  this.pushTrail(this.pos);
               } else if (this.beamAge > 86) {
                  this.burstAge = 1;
                  this.missed = true;
               } else {
                  Vec3 desired = this.smoothTarget.subtract(this.pos);
                  double dist = desired.length();
                  if (dist < 0.9) {
                     this.burstAge = 1;
                     this.impactPos = this.smoothTarget;
                  } else {
                     double curve = Mth.clamp((this.beamAge - 4.0) / 24.0, 0.0, 1.0);
                     double hardTrack = Mth.clamp((this.beamAge - 22.0) / 22.0, 0.0, 1.0);
                     Vec3 desiredDir = desired.normalize();
                     Vec3 currentDir = this.vel.lengthSqr() > 1.0E-4 ? this.vel.normalize() : desiredDir;
                     Vec3 steerDir = currentDir.lerp(desiredDir, 0.18 + hardTrack * 0.42).normalize();
                     Vec3 accel = steerDir.scale(0.07 + curve * 0.135 + hardTrack * 0.08);
                     this.vel = this.vel.scale(0.905).add(accel);
                     double speed = this.vel.length();
                     double maxSpeed = 1.52 + Math.min(0.95, this.beamAge * 0.014);
                     if (speed > maxSpeed) {
                        this.vel = this.vel.normalize().scale(maxSpeed);
                     }

                     this.pos = this.pos.add(this.vel);
                     this.pushTrail(this.pos);
                  }
               }
            } else {
               this.active = true;
               this.beamAge = 0;
               this.pos = socket;
               this.prevPos = socket;
               this.smoothTarget = target;
               this.prevSmoothTarget = target;
               Vec3 outward = AvalonSovereignHeavensFallVfxClient.radialOut(basis, this.index, this.age, this.trajectorySeed)
                  .scale(2.35 + this.index % 5 * 0.045);
               this.vel = outward.add(basis.forward.scale(0.38));

               for (int i = 0; i < this.trail.length; i++) {
                  this.trail[i] = socket;
               }
            }
         }
      }

      private boolean dead() {
         int life = Math.max(96, this.payload.durationTicks());
         return this.age > life || this.burstAge > 22;
      }

      private boolean render(BufferBuilder buffer, Matrix4f matrix, Vec3 camera, float partial, long now, int crowdDetail) {
         Minecraft minecraft = Minecraft.getInstance();
         Vec3 caster = this.resolveCaster(minecraft, partial);
         Vec3 target = this.resolveTarget(minecraft, partial);
         AvalonSovereignHeavensFallVfxClient.Basis basis = AvalonSovereignHeavensFallVfxClient.basis(caster, target);
         float time = (float)now * 0.001F;
         float warmup = AvalonSovereignHeavensFallVfxClient.smoother(Mth.clamp(this.age / 5.0F, 0.0F, 1.0F));
         int life = Math.max(96, this.payload.durationTicks());
         float fade = Mth.clamp((life - this.age) / 20.0F, 0.0F, 1.0F);
         float live = warmup * fade;
         if (live <= 0.001F) {
            return false;
         }

         int detail = Math.min(AvalonSovereignHeavensFallVfxClient.detailLevel(camera.distanceTo(this.pos.equals(Vec3.ZERO) ? caster : this.pos)), crowdDetail);
         float scale = Mth.clamp(this.payload.radius(), 2.2F, 5.2F);
         boolean wrote = false;
         if (!this.active) {
            Vec3 socket = AvalonSovereignHeavensFallVfxClient.socket(caster, basis, this.age + partial, this.index, this.trajectorySeed);
            Vec3 outward = AvalonSovereignHeavensFallVfxClient.radialOut(basis, this.index, this.age + partial, this.trajectorySeed);
            return wrote | AvalonSovereignHeavensFallVfxClient.chargeSocket(buffer, matrix, camera, socket, outward, scale, live, time, this.seed, this.index);
         }

         float beamLive = live;
         if (this.burstAge > 0) {
            beamLive *= Mth.clamp(1.0F - this.burstAge / 22.0F, 0.0F, 1.0F);
         }

         Vec3 current = this.prevPos.lerp(this.pos, partial);
         Vec3 targetNow = this.prevSmoothTarget.lerp(this.smoothTarget, partial);
         wrote |= this.trail(buffer, matrix, camera, current, scale, beamLive, detail);
         wrote |= AvalonSovereignHeavensFallVfxClient.lanceHead(
            buffer, matrix, camera, current, targetNow, scale, beamLive, time, this.seed, this.index, detail
         );
         if (this.burstAge > 0 && this.impactPos != null && !this.missed) {
            wrote |= AvalonSovereignHeavensFallVfxClient.impact(
               buffer, matrix, camera, this.impactPos, scale, beamLive, this.burstAge + partial, time, this.seed, this.index, detail
            );
         }

         return wrote;
      }

      private Vec3 resolveCaster(Minecraft minecraft, float partial) {
         if (minecraft.level != null && this.sourceId >= 0) {
            Entity entity = minecraft.level.getEntity(this.sourceId);
            if (entity != null) {
               return entity.getPosition(partial).add(0.0, entity.getBbHeight() * 0.64, 0.0);
            }
         }

         return this.fallbackCaster;
      }

      private Vec3 resolveTarget(Minecraft minecraft, float partial) {
         if (minecraft.level != null && this.targetId >= 0) {
            Entity entity = minecraft.level.getEntity(this.targetId);
            if (entity != null) {
               return entity.getPosition(partial).add(0.0, entity.getBbHeight() * 0.55, 0.0);
            }
         }

         return this.fallbackTarget;
      }

      private void pushTrail(Vec3 point) {
         for (int i = this.trail.length - 1; i > 0; i--) {
            this.trail[i] = this.trail[i - 1];
         }

         this.trail[0] = point;
      }

      private boolean trail(BufferBuilder buffer, Matrix4f matrix, Vec3 camera, Vec3 current, float scale, float live, int detail) {
         boolean wrote = false;
         Vec3 previous = current;
         int step = detail >= 3 ? 1 : (detail == 2 ? 2 : 3);

         for (int i = 0; i < this.trail.length; i += step) {
            Vec3 point = this.trail[i];
            if (point != null) {
               float f = Mth.clamp(1.0F - (float)i / this.trail.length, 0.0F, 1.0F);
               float alpha = live * f;
               double width = scale * 0.24 * Mth.clamp(1.0F - i / 40.0F, 0.12F, 1.0F);
               wrote |= AvalonSovereignHeavensFallVfxClient.beamQuad(buffer, matrix, camera, previous, point, width * 4.4, 255, 112, 5, alpha * 0.03F);
               wrote |= AvalonSovereignHeavensFallVfxClient.beamQuad(buffer, matrix, camera, previous, point, width * 2.8, 255, 190, 31, alpha * 0.1F);
               wrote |= AvalonSovereignHeavensFallVfxClient.beamQuad(buffer, matrix, camera, previous, point, width * 1.55, 255, 232, 112, alpha * 0.28F);
               wrote |= AvalonSovereignHeavensFallVfxClient.beamQuad(buffer, matrix, camera, previous, point, width * 0.74, 255, 250, 218, alpha * 0.68F);
               wrote |= AvalonSovereignHeavensFallVfxClient.beamQuad(buffer, matrix, camera, previous, point, width * 0.24, 255, 255, 255, alpha);
               previous = point;
            }
         }

         return wrote;
      }
   }
}
