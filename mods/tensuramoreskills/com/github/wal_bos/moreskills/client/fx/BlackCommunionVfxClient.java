package com.github.wal_bos.moreskills.client.fx;

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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent.Stage;
import net.neoforged.neoforge.common.NeoForge;
import org.joml.Matrix4f;

public final class BlackCommunionVfxClient {
   private static final float CONVERGENCE_END = 60.0F;
   private static final float PROGENITOR_BEAM_FADE_IN = 12.0F;
   private static final float AMBIENT_FADE_IN = 18.0F;
   private static final float RITUAL_END = 210.0F;
   private static final float COLLAPSE_START = 190.0F;
   private static final List<BlackCommunionVfxClient.Ritual> RITUALS = new ArrayList<>();
   private static boolean initialized = false;

   private BlackCommunionVfxClient() {
   }

   public static void init() {
      if (!initialized) {
         initialized = true;
         NeoForge.EVENT_BUS.addListener(BlackCommunionVfxClient::render);
      }
   }

   public static void spawn(int progenitorId, float radius, long seed, int[] linkedIds) {
      Minecraft minecraft = Minecraft.getInstance();
      if (minecraft.level != null && minecraft.player != null) {
         Entity entity = minecraft.level.getEntity(progenitorId);
         if (entity instanceof LivingEntity) {
            if (!(minecraft.player.distanceToSqr(entity) > 230400.0)) {
               long now = Util.getMillis();
               float fixedRadius = Math.max(30.0F, radius);

               for (BlackCommunionVfxClient.Ritual ritual : RITUALS) {
                  if (ritual.progenitorId == progenitorId) {
                     ritual.refresh(fixedRadius, linkedIds, now);
                     return;
                  }
               }

               RITUALS.add(new BlackCommunionVfxClient.Ritual(progenitorId, fixedRadius, seed, linkedIds, now));
               if (RITUALS.size() > 8) {
                  RITUALS.remove(0);
               }
            }
         }
      }
   }

   private static void render(RenderLevelStageEvent event) {
      if (event.getStage() == Stage.AFTER_TRANSLUCENT_BLOCKS) {
         Minecraft minecraft = Minecraft.getInstance();
         if (minecraft.level != null && minecraft.player != null) {
            long now = Util.getMillis();
            Vec3 camera = event.getCamera().getPosition();
            PoseStack poseStack = event.getPoseStack();
            float partialTick = event.getPartialTick().getGameTimeDeltaTicks();
            RenderSystem.enableBlend();
            RenderSystem.enableDepthTest();
            RenderSystem.depthFunc(515);
            RenderSystem.disableCull();
            RenderSystem.depthMask(false);
            RenderSystem.setShader(GameRenderer::getPositionColorShader);
            poseStack.pushPose();
            poseStack.translate(-camera.x, -camera.y, -camera.z);
            Matrix4f matrix = poseStack.last().pose();
            Iterator<BlackCommunionVfxClient.Ritual> iterator = RITUALS.iterator();

            while (iterator.hasNext()) {
               BlackCommunionVfxClient.Ritual ritual = iterator.next();
               if (ritual.dead(now, minecraft)) {
                  iterator.remove();
               } else {
                  ritual.render(minecraft, matrix, partialTick, now);
               }
            }

            poseStack.popPose();
            RenderSystem.depthMask(true);
            RenderSystem.enableCull();
            RenderSystem.disableBlend();
            RenderSystem.defaultBlendFunc();
         } else {
            RITUALS.clear();
         }
      }
   }

   private static boolean addOrientedRing(
      BufferBuilder buffer,
      Matrix4f matrix,
      Vec3 center,
      Vec3 axisA,
      Vec3 axisB,
      double inner,
      double outer,
      double rotation,
      int red,
      int green,
      int blue,
      float alpha,
      int segments
   ) {
      if (!(alpha <= 5.0E-4F) && !(inner <= 0.0) && !(outer <= 0.0)) {
         int aa = alpha(alpha);

         for (int i = 0; i < segments; i++) {
            double a0 = rotation + i * (float) (Math.PI * 2) / segments;
            double a1 = rotation + (i + 1) * (float) (Math.PI * 2) / segments;
            Vec3 p0 = center.add(axisA.scale(Math.cos(a0) * inner)).add(axisB.scale(Math.sin(a0) * inner));
            Vec3 p1 = center.add(axisA.scale(Math.cos(a0) * outer)).add(axisB.scale(Math.sin(a0) * outer));
            Vec3 p2 = center.add(axisA.scale(Math.cos(a1) * outer)).add(axisB.scale(Math.sin(a1) * outer));
            Vec3 p3 = center.add(axisA.scale(Math.cos(a1) * inner)).add(axisB.scale(Math.sin(a1) * inner));
            buffer.addVertex(matrix, (float)p0.x, (float)p0.y, (float)p0.z).setColor(red, green, blue, 0);
            buffer.addVertex(matrix, (float)p1.x, (float)p1.y, (float)p1.z).setColor(red, green, blue, aa);
            buffer.addVertex(matrix, (float)p2.x, (float)p2.y, (float)p2.z).setColor(red, green, blue, aa);
            buffer.addVertex(matrix, (float)p3.x, (float)p3.y, (float)p3.z).setColor(red, green, blue, 0);
         }

         return true;
      } else {
         return false;
      }
   }

   private static boolean addArcBolt(
      BufferBuilder buffer,
      Matrix4f matrix,
      Vec3 start,
      Vec3 end,
      Vec3 sideHint,
      Vec3 upHint,
      long now,
      int seed,
      double width,
      int red,
      int green,
      int blue,
      float alpha
   ) {
      if (alpha <= 5.0E-4F) {
         return false;
      }

      boolean drew = false;
      Vec3 delta = end.subtract(start);
      Vec3 side = normalizeSafe(delta.cross(upHint), sideHint);
      Vec3 up = normalizeSafe(side.cross(delta), upHint);
      Vec3 previous = start;
      int segments = 7;

      for (int i = 1; i <= segments; i++) {
         double t = (double)i / segments;
         double jitter = Math.sin(now * 0.011 + seed * 0.71 + i * 2.1) * width * 10.0 * (1.0 - Math.abs(t - 0.5));
         double jitter2 = Math.cos(now * 0.009 + seed * 0.53 - i * 1.7) * width * 7.0 * (1.0 - Math.abs(t - 0.5));
         Vec3 next = start.lerp(end, t).add(side.scale(jitter)).add(up.scale(jitter2));
         float fade = alpha * (0.92F - (float)t * 0.2F);
         drew |= addTubeSegment(buffer, matrix, previous, next, sideHint, upHint, (float)width, red, green, blue, fade);
         previous = next;
      }

      return drew;
   }

   private static Vec3 cubic(Vec3 p0, Vec3 p1, Vec3 p2, Vec3 p3, double t) {
      double it = 1.0 - t;
      return p0.scale(it * it * it).add(p1.scale(3.0 * it * it * t)).add(p2.scale(3.0 * it * t * t)).add(p3.scale(t * t * t));
   }

   private static boolean addTubeSegment(
      BufferBuilder buffer, Matrix4f matrix, Vec3 a, Vec3 b, Vec3 sideHint, Vec3 upHint, float width, int red, int green, int blue, float alpha
   ) {
      if (alpha <= 5.0E-4F) {
         return false;
      }

      Vec3 dir = b.subtract(a);
      if (dir.lengthSqr() < 1.0E-6) {
         return false;
      }

      dir = dir.normalize();
      Vec3 side = dir.cross(upHint);
      if (side.lengthSqr() < 1.0E-4) {
         side = dir.cross(sideHint);
      }

      if (side.lengthSqr() < 1.0E-4) {
         side = sideHint;
      } else {
         side = side.normalize();
      }

      Vec3 up = side.cross(dir);
      if (up.lengthSqr() < 1.0E-4) {
         up = upHint;
      } else {
         up = up.normalize();
      }

      int aa = alpha(alpha);
      int slices = 8;

      for (int i = 0; i < slices; i++) {
         double a0 = i * (float) (Math.PI * 2) / slices;
         double a1 = (i + 1) * (float) (Math.PI * 2) / slices;
         Vec3 r0 = side.scale(Math.cos(a0) * width).add(up.scale(Math.sin(a0) * width));
         Vec3 r1 = side.scale(Math.cos(a1) * width).add(up.scale(Math.sin(a1) * width));
         Vec3 p0 = a.add(r0);
         Vec3 p1 = b.add(r0);
         Vec3 p2 = b.add(r1);
         Vec3 p3 = a.add(r1);
         buffer.addVertex(matrix, (float)p0.x, (float)p0.y, (float)p0.z).setColor(red, green, blue, aa);
         buffer.addVertex(matrix, (float)p1.x, (float)p1.y, (float)p1.z).setColor(red, green, blue, aa);
         buffer.addVertex(matrix, (float)p2.x, (float)p2.y, (float)p2.z).setColor(red, green, blue, aa);
         buffer.addVertex(matrix, (float)p3.x, (float)p3.y, (float)p3.z).setColor(red, green, blue, aa);
      }

      return true;
   }

   private static boolean addDiamond(BufferBuilder buffer, Matrix4f matrix, Vec3 pos, double length, double width, int red, int green, int blue, float alpha) {
      if (alpha <= 5.0E-4F) {
         return false;
      }

      double angle = pos.x * 0.27 + pos.y * 0.13 + pos.z * 0.31;
      double fx = Math.cos(angle) * length;
      double fz = Math.sin(angle) * length;
      double sx = Math.cos(angle + (float) (Math.PI / 2)) * width;
      double sz = Math.sin(angle + (float) (Math.PI / 2)) * width;
      int aa = alpha(alpha);
      buffer.addVertex(matrix, (float)(pos.x - fx), (float)pos.y, (float)(pos.z - fz)).setColor(red, green, blue, 0);
      buffer.addVertex(matrix, (float)(pos.x + sx), (float)(pos.y + width), (float)(pos.z + sz)).setColor(red, green, blue, aa);
      buffer.addVertex(matrix, (float)(pos.x + fx), (float)pos.y, (float)(pos.z + fz)).setColor(red, green, blue, aa);
      buffer.addVertex(matrix, (float)(pos.x - sx), (float)(pos.y - width), (float)(pos.z - sz)).setColor(red, green, blue, 0);
      return true;
   }

   private static void renderRing(
      Matrix4f matrix, double x, double y, double z, double inner, double outer, double rotation, int red, int green, int blue, float alpha, int segments
   ) {
      if (!(alpha <= 5.0E-4F)) {
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         int aa = alpha(alpha);

         for (int i = 0; i < segments; i++) {
            double a0 = rotation + i * (float) (Math.PI * 2) / segments;
            double a1 = rotation + (i + 1) * (float) (Math.PI * 2) / segments;
            buffer.addVertex(matrix, (float)(x + Math.cos(a0) * inner), (float)y, (float)(z + Math.sin(a0) * inner)).setColor(red, green, blue, 0);
            buffer.addVertex(matrix, (float)(x + Math.cos(a0) * outer), (float)y, (float)(z + Math.sin(a0) * outer)).setColor(red, green, blue, aa);
            buffer.addVertex(matrix, (float)(x + Math.cos(a1) * outer), (float)y, (float)(z + Math.sin(a1) * outer)).setColor(red, green, blue, aa);
            buffer.addVertex(matrix, (float)(x + Math.cos(a1) * inner), (float)y, (float)(z + Math.sin(a1) * inner)).setColor(red, green, blue, 0);
         }

         BufferUploader.drawWithShader(buffer.buildOrThrow());
      }
   }

   private static Vec3 clampLength(Vec3 value, double max) {
      if (value.lengthSqr() <= max * max) {
         return value;
      } else {
         return value.lengthSqr() < 1.0E-4 ? Vec3.ZERO : value.normalize().scale(max);
      }
   }

   private static Vec3 normalizeSafe(Vec3 value, Vec3 fallback) {
      return value.lengthSqr() < 1.0E-4 ? fallback : value.normalize();
   }

   private static int alpha(float alpha) {
      return Mth.clamp((int)(alpha * 255.0F), 0, 255);
   }

   private static float smooth(float value) {
      return value * value * (3.0F - 2.0F * value);
   }

   private static float phase(float age, float start, float end) {
      float fadeIn = smooth(Mth.clamp((age - start) / 10.0F, 0.0F, 1.0F));
      float fadeOut = 1.0F - smooth(Mth.clamp((age - end) / 18.0F, 0.0F, 1.0F));
      return Mth.clamp(fadeIn * fadeOut, 0.0F, 1.0F);
   }

   private static float hash(long seed, int index) {
      long value = seed ^ index * 341873128712L;
      value ^= value >>> 33;
      value *= -49064778989728563L;
      value ^= value >>> 33;
      value *= -4265267296055464877L;
      value ^= value >>> 33;
      return (float)(value & 16777215L) / 1.6777215E7F;
   }

   private static final class Ritual {
      private final int progenitorId;
      private float radius;
      private final long seed;
      private int[] linkedIds;
      private final long created;
      private long lastSeen;

      private Ritual(int progenitorId, float radius, long seed, int[] linkedIds, long created) {
         this.progenitorId = progenitorId;
         this.radius = radius;
         this.seed = seed;
         this.linkedIds = linkedIds;
         this.created = created;
         this.lastSeen = created;
      }

      private void refresh(float radius, int[] linkedIds, long now) {
         this.radius = Math.max(this.radius, radius);
         this.linkedIds = linkedIds;
         this.lastSeen = now;
      }

      private boolean dead(long now, Minecraft minecraft) {
         float age = (float)(now - this.created) / 50.0F;
         return age > 210.0F && now - this.lastSeen > 900L
            ? true
            : !(minecraft.level.getEntity(this.progenitorId) instanceof LivingEntity living && living.isAlive());
      }

      private void render(Minecraft minecraft, Matrix4f matrix, float partialTick, long now) {
         if (minecraft.level.getEntity(this.progenitorId) instanceof LivingEntity progenitor) {
            double px = Mth.lerp(partialTick, progenitor.xOld, progenitor.getX());
            double py = Mth.lerp(partialTick, progenitor.yOld, progenitor.getY()) + progenitor.getBbHeight() * 0.7;
            double pz = Mth.lerp(partialTick, progenitor.zOld, progenitor.getZ());
            float age = (float)(now - this.created) / 50.0F;
            float grow = BlackCommunionVfxClient.smooth(Mth.clamp(age / 6.0F, 0.0F, 1.0F));
            float convergence = BlackCommunionVfxClient.smooth(Mth.clamp(age / 8.0F, 0.0F, 1.0F))
               * (1.0F - BlackCommunionVfxClient.smooth(Mth.clamp((age - 60.0F) / 6.0F, 0.0F, 1.0F)));
            float fire = BlackCommunionVfxClient.smooth(Mth.clamp((age - 60.0F) / 12.0F, 0.0F, 1.0F));
            float ambient = BlackCommunionVfxClient.smooth(Mth.clamp((age - 60.0F) / 18.0F, 0.0F, 1.0F));
            float collapse = 1.0F - BlackCommunionVfxClient.smooth(Mth.clamp((age - 190.0F) / 18.0F, 0.0F, 1.0F));
            float live = grow * collapse;
            if (!(live <= 0.002F)) {
               RenderSystem.blendFuncSeparate(770, 1, 1, 0);
               this.renderBloodfiendConvergence(minecraft, matrix, partialTick, age, px, py, pz, convergence, live);
               this.renderCoreBurst(matrix, age, px, py, pz, convergence, fire, ambient, live);
               this.renderHeavenPiercer(matrix, age, px, py, pz, fire, live);
               this.renderSkyIgnition(matrix, age, px, py, pz, ambient, fire, live);
               this.renderAirCrackle(matrix, age, px, py, pz, ambient, live);
               this.renderGroundCorona(matrix, age, px, py, pz, convergence, fire, ambient, live);
            }
         }
      }

      private void renderBloodfiendConvergence(
         Minecraft minecraft, Matrix4f matrix, float partialTick, float age, double px, double py, double pz, float convergence, float live
      ) {
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         boolean drew = false;
         float power = convergence * live;
         if (!(power <= 0.001F)) {
            for (int i = 0; i < this.linkedIds.length; i++) {
               if (minecraft.level.getEntity(this.linkedIds[i]) instanceof LivingEntity linked && linked.isAlive()) {
                  double lx = Mth.lerp(partialTick, linked.xOld, linked.getX());
                  double ly = Mth.lerp(partialTick, linked.yOld, linked.getY()) + linked.getBbHeight() * 0.62;
                  double lz = Mth.lerp(partialTick, linked.zOld, linked.getZ());
                  float h = BlackCommunionVfxClient.hash(this.seed, 1100 + i);
                  double phaseOffset = h * (float) (Math.PI * 2);
                  double swirl = Math.sin(age * 0.23 + phaseOffset) * this.radius * 0.22;
                  double lift = Math.cos(age * 0.16 + phaseOffset) * this.radius * 0.1;
                  Vec3 from = new Vec3(lx, ly, lz);
                  Vec3 to = new Vec3(
                     px + Math.cos(age * 0.07 + phaseOffset) * swirl,
                     py + lift + Math.sin(age * 0.18 + phaseOffset) * this.radius * 0.06,
                     pz + Math.sin(age * 0.07 + phaseOffset) * swirl
                  );
                  int samples = 14;
                  Vec3 prev = from;

                  for (int s = 1; s <= samples; s++) {
                     double t = (double)s / samples;
                     Vec3 base = from.lerp(to, t);
                     double wave = Math.sin(t * Math.PI * 4.0 + age * 0.2 + phaseOffset) * this.radius * 0.03 * (1.0 - Math.abs(t - 0.5));
                     Vec3 side = BlackCommunionVfxClient.normalizeSafe(to.subtract(from).cross(new Vec3(0.0, 1.0, 0.0)), new Vec3(1.0, 0.0, 0.0));
                     Vec3 next = base.add(0.0, wave, 0.0).add(side.scale(Math.sin(age * 0.14 + phaseOffset + t * 5.0) * this.radius * 0.014));
                     float width = this.radius * (0.008F + h * 0.006F) * (1.0F - (float)t * 0.18F);
                     float alpha = power * (0.38F + h * 0.32F);
                     drew |= BlackCommunionVfxClient.addTubeSegment(
                        buffer, matrix, prev, next, new Vec3(1.0, 0.0, 0.0), new Vec3(0.0, 1.0, 0.0), width, 255, 0, 34, alpha
                     );
                     drew |= BlackCommunionVfxClient.addTubeSegment(
                        buffer, matrix, prev, next, new Vec3(1.0, 0.0, 0.0), new Vec3(0.0, 1.0, 0.0), width * 0.34F, 255, 64, 92, alpha * 0.52F
                     );
                     prev = next;
                  }

                  for (int s = 0; s < 7; s++) {
                     double p = (age * (0.03 + s * 0.007) + h + s * 0.17) % 1.0;
                     Vec3 pos = from.lerp(to, p);
                     double size = this.radius * (0.008 + s * 0.002);
                     drew |= BlackCommunionVfxClient.addDiamond(buffer, matrix, pos, size, size * 0.36, 255, 18, 46, power * (0.28F + s * 0.055F));
                  }
               }
            }

            if (drew) {
               BufferUploader.drawWithShader(buffer.buildOrThrow());
            }
         }
      }

      private void renderCoreBurst(Matrix4f matrix, float age, double px, double py, double pz, float convergence, float fire, float ambient, float live) {
         float burst = Math.max(Math.max(convergence * 0.82F, fire), ambient * 0.55F) * live;
         float pulse = 0.72F + 0.28F * (float)Math.sin(age * 0.54F);
         BlackCommunionVfxClient.renderRing(
            matrix, px, py - 0.52, pz, this.radius * 0.54, this.radius * 0.68, age * 0.085, 255, 0, 32, burst * 0.32F * pulse, 192
         );
         BlackCommunionVfxClient.renderRing(
            matrix, px, py - 0.42, pz, this.radius * 0.9, this.radius * 1.03, -age * 0.07, 255, 0, 46, burst * 0.24F * pulse, 224
         );
         BlackCommunionVfxClient.renderRing(matrix, px, py + 0.18, pz, this.radius * 0.3, this.radius * 0.4, age * 0.13, 255, 48, 76, burst * 0.42F, 160);
         BlackCommunionVfxClient.renderRing(matrix, px, py + 0.58, pz, this.radius * 0.16, this.radius * 0.24, -age * 0.18, 255, 98, 118, burst * 0.28F, 128);
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         boolean drew = false;

         for (int i = 0; i < 210; i++) {
            float h = BlackCommunionVfxClient.hash(this.seed, 2000 + i);
            float h2 = BlackCommunionVfxClient.hash(this.seed, 2400 + i);
            double angle = h * (float) (Math.PI * 2) + age * (0.075 + h2 * 0.08);
            double dist = this.radius * (0.1 + h2 * 0.86);
            double y = py + Math.sin(age * 0.15 + i) * this.radius * 0.18 + this.radius * 0.12;
            Vec3 pos = new Vec3(px + Math.cos(angle) * dist, y, pz + Math.sin(angle) * dist);
            double size = this.radius * (0.006 + h * 0.016);
            drew |= BlackCommunionVfxClient.addDiamond(buffer, matrix, pos, size, size * 0.33, 255, 18, 50, burst * (0.12F + h2 * 0.22F));
         }

         for (int i = 0; i < 18; i++) {
            float h = BlackCommunionVfxClient.hash(this.seed, 2600 + i);
            double angle = h * (float) (Math.PI * 2) + age * 0.11;
            Vec3 a = new Vec3(px + Math.cos(angle) * this.radius * 0.15, py + this.radius * 0.1, pz + Math.sin(angle) * this.radius * 0.15);
            Vec3 b = new Vec3(
               px + Math.cos(angle + 0.3) * this.radius * (1.1 + h), py + this.radius * (0.1 + h * 0.25), pz + Math.sin(angle + 0.3) * this.radius * (1.1 + h)
            );
            drew |= BlackCommunionVfxClient.addArcBolt(
               buffer,
               matrix,
               a,
               b,
               new Vec3(1.0, 0.0, 0.0),
               new Vec3(0.0, 1.0, 0.0),
               (long)(age * 50.0F),
               9000 + i,
               this.radius * 0.01,
               255,
               0,
               38,
               burst * (0.12F + h * 0.18F)
            );
         }

         if (drew) {
            BufferUploader.drawWithShader(buffer.buildOrThrow());
         }
      }

      private void renderHeavenPiercer(Matrix4f matrix, float age, double px, double py, double pz, float fire, float live) {
         if (!(fire <= 0.001F) && !(live <= 0.001F)) {
            BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
            boolean drew = false;
            float beamAlpha = fire * live;
            double baseY = py - 1.45;
            double topY = py + 720.0 + this.radius * 10.0;

            for (int i = 0; i < 86; i++) {
               float h = BlackCommunionVfxClient.hash(this.seed, 3000 + i);
               float h2 = BlackCommunionVfxClient.hash(this.seed, 3300 + i);
               double angle = h * (float) (Math.PI * 2) + age * (0.03 + i * 8.5E-4);
               double sway = this.radius * (0.045 + h * 0.28);
               double ox = Math.cos(angle) * sway;
               double oz = Math.sin(angle) * sway;
               double midY = baseY + (topY - baseY) * (0.26 + h2 * 0.46);
               Vec3 a = new Vec3(px + ox, baseY, pz + oz);
               Vec3 b = new Vec3(px + Math.cos(angle + 1.7) * sway * 0.5, midY, pz + Math.sin(angle + 1.7) * sway * 0.5);
               Vec3 c = new Vec3(px - ox * 0.32, topY, pz - oz * 0.32);
               float width = this.radius * (0.026F + h * 0.052F + fire * 0.05F);
               drew |= BlackCommunionVfxClient.addTubeSegment(
                  buffer,
                  matrix,
                  a,
                  b,
                  new Vec3(1.0, 0.0, 0.0),
                  new Vec3(0.0, 1.0, 0.0),
                  width,
                  255,
                  i % 3 == 0 ? 30 : 0,
                  i % 3 == 0 ? 74 : 34,
                  beamAlpha * (0.24F + h * 0.4F)
               );
               drew |= BlackCommunionVfxClient.addTubeSegment(
                  buffer,
                  matrix,
                  b,
                  c,
                  new Vec3(1.0, 0.0, 0.0),
                  new Vec3(0.0, 1.0, 0.0),
                  width * 0.72F,
                  255,
                  i % 3 == 0 ? 20 : 0,
                  i % 3 == 0 ? 62 : 28,
                  beamAlpha * (0.18F + h2 * 0.28F)
               );
            }

            for (int i = 0; i < 32; i++) {
               float h = BlackCommunionVfxClient.hash(this.seed, 3500 + i);
               float width = this.radius * (0.11F + h * 0.1F + fire * 0.075F);
               drew |= BlackCommunionVfxClient.addTubeSegment(
                  buffer,
                  matrix,
                  new Vec3(px, baseY, pz),
                  new Vec3(px, topY, pz),
                  new Vec3(1.0, 0.0, 0.0),
                  new Vec3(0.0, 1.0, 0.0),
                  width,
                  255,
                  48,
                  82,
                  beamAlpha * (0.06F + h * 0.08F)
               );
            }

            for (int i = 0; i < 360; i++) {
               float h = BlackCommunionVfxClient.hash(this.seed, 3800 + i);
               float h2 = BlackCommunionVfxClient.hash(this.seed, 4100 + i);
               double angle = h * (float) (Math.PI * 2) + age * (0.14 + h2 * 0.11);
               double dist = this.radius * (0.18 + h2 * 2.85);
               double rise = (age * (0.11 + h * 0.075) + h2) % 1.0;
               double y1 = baseY + rise * (topY - baseY);
               double y2 = y1 + this.radius * (0.06 + h * 0.26);
               double x = px + Math.cos(angle) * dist;
               double z = pz + Math.sin(angle) * dist;
               drew |= BlackCommunionVfxClient.addTubeSegment(
                  buffer,
                  matrix,
                  new Vec3(x, y1, z),
                  new Vec3(x + Math.cos(angle + 1.4) * this.radius * 0.05, y2, z + Math.sin(angle + 1.4) * this.radius * 0.05),
                  new Vec3(1.0, 0.0, 0.0),
                  new Vec3(0.0, 1.0, 0.0),
                  this.radius * 0.0065F,
                  255,
                  10,
                  42,
                  beamAlpha * (0.09F + h * 0.18F)
               );
            }

            for (int i = 0; i < 58; i++) {
               float h = BlackCommunionVfxClient.hash(this.seed, 4500 + i);
               float h2 = BlackCommunionVfxClient.hash(this.seed, 4700 + i);
               double angle = h * (float) (Math.PI * 2) + age * (0.034 + h2 * 0.014);
               double dist = this.radius * (1.8 + h2 * 6.8);
               double bottom = py + this.radius * (1.4 + h * 8.0);
               double top = topY - this.radius * (6.0 + h2 * 15.0);
               Vec3 a = new Vec3(px + Math.cos(angle) * dist, top, pz + Math.sin(angle) * dist);
               Vec3 b = new Vec3(px + Math.cos(angle + 0.24) * dist * 0.48, bottom, pz + Math.sin(angle + 0.24) * dist * 0.48);
               drew |= BlackCommunionVfxClient.addArcBolt(
                  buffer,
                  matrix,
                  a,
                  b,
                  new Vec3(1.0, 0.0, 0.0),
                  new Vec3(0.0, 1.0, 0.0),
                  (long)(age * 50.0F),
                  12000 + i,
                  this.radius * (0.01 + h * 0.012),
                  255,
                  0,
                  32,
                  beamAlpha * (0.09F + h2 * 0.15F)
               );
            }

            if (drew) {
               BufferUploader.drawWithShader(buffer.buildOrThrow());
            }
         }
      }

      private void renderSkyIgnition(Matrix4f matrix, float age, double px, double py, double pz, float sky, float fire, float live) {
         if (!(sky <= 0.001F) && !(live <= 0.001F)) {
            float power = sky * live;
            double skyY = py + 96.0 + this.radius * 2.5;

            for (int i = 0; i < 24; i++) {
               double y = skyY + i * (5.8 + this.radius * 0.08);
               double inner = this.radius * (2.5 + i * 0.58);
               double outer = inner + this.radius * (0.24 + i * 0.04);
               float alpha = power * (0.32F - i * 0.012F);
               BlackCommunionVfxClient.renderRing(matrix, px, y, pz, inner, outer, age * (0.03 + i * 0.002), 255, 0, 34, alpha, 320);
            }

            for (int i = 0; i < 17; i++) {
               double y = skyY + 26.0 + i * 10.5;
               double inner = this.radius * (6.0 + i * 1.06);
               double outer = inner + this.radius * (0.34 + i * 0.044);
               float alpha = power * (0.18F - i * 0.0075F);
               BlackCommunionVfxClient.renderRing(matrix, px, y, pz, inner, outer, -age * (0.018 + i * 0.002), 145, 0, 22, alpha, 380);
            }

            BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
            boolean drew = false;

            for (int i = 0; i < 210; i++) {
               float h = BlackCommunionVfxClient.hash(this.seed, 5000 + i);
               float h2 = BlackCommunionVfxClient.hash(this.seed, 5400 + i);
               double angle = h * (float) (Math.PI * 2) + age * 0.018;
               double inner = this.radius * (0.7 + h2 * 1.7);
               double outer = this.radius * (9.0 + h2 * 8.5);
               Vec3 a = new Vec3(px + Math.cos(angle) * inner, skyY, pz + Math.sin(angle) * inner);
               Vec3 b = new Vec3(px + Math.cos(angle) * outer, skyY + Math.sin(age * 0.06 + i) * 4.0 + h2 * 13.0, pz + Math.sin(angle) * outer);
               drew |= BlackCommunionVfxClient.addTubeSegment(
                  buffer,
                  matrix,
                  a,
                  b,
                  new Vec3(1.0, 0.0, 0.0),
                  new Vec3(0.0, 1.0, 0.0),
                  this.radius * (0.009F + h * 0.01F),
                  255,
                  0,
                  30,
                  power * (0.14F + h * 0.18F)
               );
            }

            for (int i = 0; i < 96; i++) {
               float h = BlackCommunionVfxClient.hash(this.seed, 6000 + i);
               float h2 = BlackCommunionVfxClient.hash(this.seed, 6200 + i);
               double angle = h * (float) (Math.PI * 2) + age * 0.026;
               double dist = this.radius * (2.4 + h2 * 12.0);
               double x = px + Math.cos(angle) * dist;
               double z = pz + Math.sin(angle) * dist;
               double top = skyY + 42.0 + h * 48.0;
               double bottom = skyY - 26.0 - h2 * 34.0;
               drew |= BlackCommunionVfxClient.addTubeSegment(
                  buffer,
                  matrix,
                  new Vec3(x, bottom, z),
                  new Vec3(x, top, z),
                  new Vec3(1.0, 0.0, 0.0),
                  new Vec3(0.0, 1.0, 0.0),
                  this.radius * (0.008F + h * 0.014F),
                  255,
                  12,
                  44,
                  power * (0.08F + h2 * 0.14F)
               );
            }

            if (drew) {
               BufferUploader.drawWithShader(buffer.buildOrThrow());
            }

            BlackCommunionVfxClient.renderRing(matrix, px, skyY + 4.0, pz, this.radius * 10.0, this.radius * 10.8, age * 0.01, 255, 0, 28, power * 0.18F, 460);
            BlackCommunionVfxClient.renderRing(
               matrix, px, skyY + 24.0, pz, this.radius * 13.0, this.radius * 13.8, -age * 0.008, 130, 0, 18, power * 0.13F, 500
            );
            BlackCommunionVfxClient.renderRing(matrix, px, skyY + 48.0, pz, this.radius * 16.5, this.radius * 17.4, age * 0.006, 90, 0, 14, power * 0.09F, 540);
         }
      }

      private void renderAirCrackle(Matrix4f matrix, float age, double px, double py, double pz, float ambient, float live) {
         if (!(ambient <= 0.001F) && !(live <= 0.001F)) {
            float power = ambient * live;
            BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
            boolean drew = false;

            for (int i = 0; i < 95; i++) {
               float h = BlackCommunionVfxClient.hash(this.seed, 7000 + i);
               float h2 = BlackCommunionVfxClient.hash(this.seed, 7300 + i);
               double angle = h * (float) (Math.PI * 2) + age * (0.035 + h2 * 0.055);
               double dist = this.radius * (0.35 + h2 * 3.8);
               double fall = (age * (0.022 + h * 0.03) + h2) % 1.0;
               double y = py + this.radius * (1.85 - fall * 2.8) + Math.sin(age * 0.09 + i) * this.radius * 0.12;
               Vec3 pos = new Vec3(px + Math.cos(angle) * dist, y, pz + Math.sin(angle) * dist);
               double size = this.radius * (0.006 + h * 0.01);
               drew |= BlackCommunionVfxClient.addDiamond(buffer, matrix, pos, size, size * 0.45, 255, 0, 42, power * (0.12F + h * 0.28F));
            }

            for (int i = 0; i < 34; i++) {
               float h = BlackCommunionVfxClient.hash(this.seed, 7600 + i);
               float h2 = BlackCommunionVfxClient.hash(this.seed, 7900 + i);
               double angle = h * (float) (Math.PI * 2) + age * (0.025 + h2 * 0.018);
               double dist = this.radius * (0.75 + h2 * 4.6);
               double y0 = py + this.radius * (0.35 + h * 2.2);
               Vec3 a = new Vec3(px + Math.cos(angle) * dist, y0, pz + Math.sin(angle) * dist);
               Vec3 b = new Vec3(
                  px + Math.cos(angle + 0.35 + h * 0.7) * dist * (0.78 + h2 * 0.22),
                  y0 + this.radius * (0.18 + h2 * 0.7),
                  pz + Math.sin(angle + 0.35 + h * 0.7) * dist * (0.78 + h2 * 0.22)
               );
               drew |= BlackCommunionVfxClient.addArcBolt(
                  buffer,
                  matrix,
                  a,
                  b,
                  new Vec3(1.0, 0.0, 0.0),
                  new Vec3(0.0, 1.0, 0.0),
                  (long)(age * 50.0F),
                  16000 + i,
                  this.radius * (0.006 + h * 0.009),
                  255,
                  0,
                  34,
                  power * (0.09F + h2 * 0.17F)
               );
            }

            if (drew) {
               BufferUploader.drawWithShader(buffer.buildOrThrow());
            }
         }
      }

      private void renderGroundCorona(Matrix4f matrix, float age, double px, double py, double pz, float convergence, float fire, float ambient, float live) {
         float power = Math.max(Math.max(convergence, fire), ambient * 0.7F) * live;
         double y = py - 1.18;
         BlackCommunionVfxClient.renderRing(matrix, px, y, pz, this.radius * 1.2, this.radius * 1.35, age * 0.078, 255, 0, 28, power * 0.24F, 260);
         BlackCommunionVfxClient.renderRing(matrix, px, y + 0.04, pz, this.radius * 1.76, this.radius * 1.94, -age * 0.062, 255, 8, 44, power * 0.19F, 300);
         BlackCommunionVfxClient.renderRing(matrix, px, y + 0.08, pz, this.radius * 2.48, this.radius * 2.66, age * 0.044, 160, 0, 20, power * 0.13F, 340);
         BlackCommunionVfxClient.renderRing(matrix, px, y + 0.12, pz, this.radius * 3.18, this.radius * 3.32, -age * 0.032, 95, 0, 14, power * 0.08F, 380);
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         boolean drew = false;

         for (int i = 0; i < 58; i++) {
            float h = BlackCommunionVfxClient.hash(this.seed, 8000 + i);
            double angle = h * (float) (Math.PI * 2) + age * (0.04 + h * 0.02);
            double inner = this.radius * (0.55 + h * 0.7);
            double outer = this.radius * (2.2 + h * 1.8);
            Vec3 a = new Vec3(px + Math.cos(angle) * inner, y + 0.1, pz + Math.sin(angle) * inner);
            Vec3 b = new Vec3(px + Math.cos(angle + 0.06) * outer, y + 0.16, pz + Math.sin(angle + 0.06) * outer);
            drew |= BlackCommunionVfxClient.addTubeSegment(
               buffer,
               matrix,
               a,
               b,
               new Vec3(1.0, 0.0, 0.0),
               new Vec3(0.0, 1.0, 0.0),
               this.radius * (0.006F + h * 0.006F),
               255,
               0,
               36,
               power * (0.07F + h * 0.12F)
            );
         }

         if (drew) {
            BufferUploader.drawWithShader(buffer.buildOrThrow());
         }
      }
   }
}
