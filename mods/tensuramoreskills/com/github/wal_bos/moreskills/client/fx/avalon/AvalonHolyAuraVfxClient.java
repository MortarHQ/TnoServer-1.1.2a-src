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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import net.minecraft.Util;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent.Stage;
import net.neoforged.neoforge.common.NeoForge;
import org.joml.Matrix4f;

public final class AvalonHolyAuraVfxClient {
   private static final Map<Integer, AvalonHolyAuraVfxClient.AuraState> ACTIVE = new HashMap<>();
   private static boolean initialized;

   private AvalonHolyAuraVfxClient() {
   }

   public static void init() {
      if (!initialized) {
         initialized = true;
         NeoForge.EVENT_BUS.addListener(AvalonHolyAuraVfxClient::render);
      }
   }

   public static void spawn(ArthurAvalonVfxData payload) {
      if (ClientVfxGate.allowed("avalon_holy_aura")) {
         init();
         Minecraft minecraft = Minecraft.getInstance();
         if (minecraft.level != null && minecraft.player != null) {
            if (!(minecraft.player.distanceToSqr(payload.start()) > 193600.0)) {
               long now = Util.getMillis();
               int key = payload.sourceId() >= 0 ? payload.sourceId() : (int)(payload.seed() ^ payload.seed() >>> 32);
               AvalonHolyAuraVfxClient.AuraState state = ACTIVE.computeIfAbsent(key, ignored -> new AvalonHolyAuraVfxClient.AuraState(payload, now));
               if (payload.variant() < 0) {
                  state.release(payload, now);
               } else {
                  state.refresh(payload, now);
               }

               while (ACTIVE.size() > 24) {
                  ACTIVE.remove(ACTIVE.keySet().iterator().next());
               }
            }
         }
      }
   }

   private static void render(RenderLevelStageEvent event) {
      if (!ClientVfxGate.allowed("avalon_holy_aura")) {
         ACTIVE.clear();
      } else if (event.getStage() == Stage.AFTER_PARTICLES) {
         Minecraft minecraft = Minecraft.getInstance();
         if (minecraft.level != null && minecraft.player != null && !ACTIVE.isEmpty()) {
            long now = Util.getMillis();
            Camera camera = minecraft.gameRenderer.getMainCamera();
            Vec3 cam = camera.getPosition();
            Matrix4f matrix = event.getPoseStack().last().pose();
            RenderSystem.enableBlend();
            RenderSystem.blendFuncSeparate(SourceFactor.SRC_ALPHA, DestFactor.ONE, SourceFactor.ONE, DestFactor.ONE_MINUS_SRC_ALPHA);
            RenderSystem.disableCull();
            RenderSystem.enableDepthTest();
            RenderSystem.depthMask(false);
            RenderSystem.setShader(GameRenderer::getPositionColorShader);
            BufferBuilder buffer = Tesselator.getInstance().begin(Mode.TRIANGLES, DefaultVertexFormat.POSITION_COLOR);
            boolean wrote = false;
            Iterator<AvalonHolyAuraVfxClient.AuraState> iterator = ACTIVE.values().iterator();

            while (iterator.hasNext()) {
               AvalonHolyAuraVfxClient.AuraState state = iterator.next();
               if (state.dead(now)) {
                  iterator.remove();
               } else {
                  wrote |= state.draw(buffer, matrix, cam, now);
               }
            }

            if (wrote) {
               BufferUploader.drawWithShader(buffer.buildOrThrow());
            }

            RenderSystem.depthMask(true);
            RenderSystem.enableCull();
            RenderSystem.defaultBlendFunc();
            RenderSystem.disableBlend();
         }
      }
   }

   private static boolean royalSeal(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 base, float scale, float live, float time, long seed, int detail) {
      Vec3 center = rel(base.add(0.0, 0.025, 0.0), cam);
      float rotation = time * 0.075F;
      boolean wrote = false;
      wrote |= ringBand(buffer, matrix, center, scale * 0.72F, scale * 0.024F, rotation, 255, 186, 38, live * 0.24F, detail >= 2 ? 64 : 40);
      wrote |= ringBand(
         buffer, matrix, center.add(0.0, 0.004, 0.0), scale * 0.69F, scale * 0.01F, -rotation * 0.82F, 255, 243, 176, live * 0.72F, detail >= 2 ? 64 : 40
      );
      wrote |= ringBand(
         buffer, matrix, center.add(0.0, 0.008, 0.0), scale * 0.39F, scale * 0.01F, rotation * 1.18F, 255, 229, 118, live * 0.52F, detail >= 2 ? 52 : 34
      );
      int spokes = 8;

      for (int i = 0; i < spokes; i++) {
         double angle = rotation * 0.42 + i * (float) (Math.PI * 2) / spokes;
         Vec3 radial = new Vec3(Math.cos(angle), 0.0, Math.sin(angle));
         Vec3 tangent = new Vec3(-Math.sin(angle), 0.0, Math.cos(angle));
         Vec3 p0 = center.add(radial.scale(scale * 0.22));
         Vec3 p1 = center.add(radial.scale(scale * 0.53));
         wrote |= lineRibbon(buffer, matrix, p0, p1, scale * 0.0075F, 255, 226, 104, live * 0.34F);
         Vec3 runeCenter = center.add(radial.scale(scale * 0.6)).add(0.0, 0.006, 0.0);
         float rune = scale * 0.045F;
         Vec3 tip = runeCenter.add(radial.scale(rune));
         Vec3 left = runeCenter.subtract(radial.scale(rune * 0.52)).add(tangent.scale(rune * 0.62));
         Vec3 right = runeCenter.subtract(radial.scale(rune * 0.52)).subtract(tangent.scale(rune * 0.62));
         wrote |= lineRibbon(buffer, matrix, left, tip, scale * 0.006F, 255, 247, 203, live * 0.54F);
         wrote |= lineRibbon(buffer, matrix, right, tip, scale * 0.006F, 255, 247, 203, live * 0.54F);
      }

      if (detail >= 2) {
         float innerRotation = -rotation * 0.72F + 0.35F;

         for (int i = 0; i < 4; i++) {
            double a0 = innerRotation + i * (float) Math.PI * 0.5;
            double a1 = innerRotation + (i + 1) * (float) Math.PI * 0.5;
            Vec3 p0 = center.add(Math.cos(a0) * scale * 0.27, 0.012, Math.sin(a0) * scale * 0.27);
            Vec3 p1 = center.add(Math.cos(a1) * scale * 0.27, 0.012, Math.sin(a1) * scale * 0.27);
            wrote |= lineRibbon(buffer, matrix, p0, p1, scale * 0.006F, 255, 248, 210, live * 0.4F);
         }
      }

      return wrote;
   }

   private static boolean bodyRadiance(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 center, float live, float breath, float time, int detail) {
      boolean wrote = false;
      Vec3 core = rel(center.add(0.0, 0.16, 0.0), cam);
      Vec3 heart = rel(center.add(0.0, 0.38, 0.0), cam);
      Vec3 head = rel(center.add(0.0, 1.02, 0.0), cam);
      wrote |= glow(buffer, matrix, core, 1.28F * breath, 255, 173, 30, live * 0.04F, 22);
      wrote |= glow(buffer, matrix, core, 0.78F * breath, 255, 225, 109, live * 0.105F, 20);
      wrote |= glow(buffer, matrix, heart, 0.34F * breath, 255, 250, 216, live * 0.26F, 18);
      wrote |= star(buffer, matrix, heart, 0.17F * breath, time * 0.1F, 255, 255, 240, live * 0.62F);
      wrote |= glow(buffer, matrix, head, 0.32F, 255, 239, 165, live * 0.1F, 16);
      if (detail >= 2) {
         Vec3 lower = rel(center.add(0.0, -0.38, 0.0), cam);
         Vec3 upper = rel(center.add(0.0, 1.4, 0.0), cam);
         wrote |= taperedRay(buffer, matrix, lower, upper, 0.05F, 255, 210, 72, live * 0.1F);
         wrote |= taperedRay(buffer, matrix, lower, upper, 0.018F, 255, 252, 222, live * 0.2F);
      }

      return wrote;
   }

   private static boolean royalCrown(
      BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 center, float live, float breath, float time, Vec3 right, Vec3 forward, int detail
   ) {
      Vec3 crownWorld = center.add(0.0, 1.7, 0.0);
      Vec3 crown = rel(crownWorld, cam);
      boolean wrote = false;
      wrote |= ringVertical(
         buffer, matrix, crown, right, new Vec3(0.0, 1.0, 0.0), 0.5F * breath, 0.018F, time * 0.055F, 255, 236, 149, live * 0.46F, detail >= 2 ? 48 : 30
      );
      wrote |= ringVertical(
         buffer,
         matrix,
         crown.add(0.0, 0.0, -0.004),
         right,
         new Vec3(0.0, 1.0, 0.0),
         0.42F * breath,
         0.008F,
         -time * 0.04F,
         255,
         252,
         220,
         live * 0.46F,
         detail >= 2 ? 44 : 28
      );
      int spikes = 5;

      for (int i = 0; i < spikes; i++) {
         float normalized = (float)i / (spikes - 1);
         float x = (normalized - 0.5F) * 0.7F;
         float height = 0.4F + (1.0F - Math.abs(x) / 0.36F) * 0.34F;
         Vec3 foot = crownWorld.add(right.scale(x)).add(forward.scale(-0.03));
         Vec3 tip = foot.add(0.0, height, 0.0);
         wrote |= taperedRay(buffer, matrix, rel(foot, cam), rel(tip, cam), 0.038F, 255, 245, 191, live * 0.54F);
      }

      return wrote | glow(buffer, matrix, crown, 0.12F, 255, 255, 236, live * 0.38F, 12);
   }

   private static boolean royalMantle(
      BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 center, float live, float time, Vec3 right, Vec3 forward, int detail
   ) {
      if (detail == 1) {
         return false;
      }

      boolean wrote = false;
      int blades = detail >= 3 ? 4 : 3;
      float motion = Mth.sin(time * 0.62F) * 0.025F;

      for (int sideSign = -1; sideSign <= 1; sideSign += 2) {
         for (int i = 0; i < blades; i++) {
            float u = blades <= 1 ? 0.0F : (float)i / (blades - 1);
            Vec3 root = center.add(right.scale(sideSign * (0.3 + u * 0.04))).add(0.0, 0.58 - u * 0.1, 0.0).add(forward.scale(-0.18));
            Vec3 control = center.add(right.scale(sideSign * (0.72 + u * 0.42))).add(0.0, 0.58 - u * 0.18, 0.0).add(forward.scale(-0.3 - u * 0.1));
            Vec3 tip = center.add(right.scale(sideSign * (1.18 + u * 0.52 + motion * sideSign)))
               .add(0.0, 0.44 - u * 0.74, 0.0)
               .add(forward.scale(-0.42 - u * 0.16));
            float width = 0.05F - u * 0.01F;
            wrote |= mantleBlade(buffer, matrix, rel(root, cam), rel(control, cam), rel(tip, cam), width, 255, 223, 105, live * (0.28F - u * 0.045F));
            wrote |= mantleBlade(buffer, matrix, rel(root, cam), rel(control, cam), rel(tip, cam), width * 0.42F, 255, 250, 211, live * (0.3F - u * 0.045F));
         }
      }

      return wrote;
   }

   private static boolean sacredMotes(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 center, float live, float time, long seed, int detail) {
      int count = detail >= 3 ? 14 : (detail == 2 ? 9 : 5);
      boolean wrote = false;

      for (int i = 0; i < count; i++) {
         float h0 = hash(seed, 4100 + i * 3);
         float h1 = hash(seed, 4101 + i * 3);
         float h2 = hash(seed, 4102 + i * 3);
         double angle = i * 2.399963229728653 + time * (0.15 + h0 * 0.06);
         double rr = 0.72 + h1 * 0.72;
         double y = -0.52 + h2 * 2.34 + 0.035 * Math.sin(time * 0.9 + i);
         Vec3 p = rel(center.add(Math.cos(angle) * rr, y, Math.sin(angle) * rr), cam);
         float size = 0.03F + h1 * 0.04F;
         wrote |= glow(buffer, matrix, p, size * 2.3F, 255, 184, 38, live * 0.1F, 8);
         wrote |= glow(buffer, matrix, p, size, 255, 249, 207, live * 0.62F, 8);
      }

      return wrote;
   }

   private static boolean ascendingLight(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 base, float field, float live, float time, long seed, int detail) {
      int count = detail >= 3 ? 12 : (detail == 2 ? 8 : 5);
      boolean wrote = false;

      for (int i = 0; i < count; i++) {
         float h0 = hash(seed, 5200 + i * 4);
         float h1 = hash(seed, 5201 + i * 4);
         float h2 = hash(seed, 5202 + i * 4);
         float h3 = hash(seed, 5203 + i * 4);
         double angle = h0 * (float) (Math.PI * 2);
         double rr = Math.sqrt(h1) * field * 0.62;
         double cycle = (time * (0.18 + h2 * 0.12) + h3 * 4.0) % 1.0;
         double y = 0.08 + cycle * (2.15 + h2 * 1.55);
         Vec3 p0 = base.add(Math.cos(angle) * rr, y, Math.sin(angle) * rr);
         Vec3 p1 = p0.add(0.0, 0.22 + h2 * 0.34, 0.0);
         float fade = (float)Math.sin(cycle * Math.PI);
         wrote |= taperedRay(buffer, matrix, rel(p0, cam), rel(p1, cam), 0.012F + h3 * 0.011F, 255, 222, 105, live * fade * 0.34F);
      }

      return wrote;
   }

   private static boolean sanctuaryPillars(
      BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 base, float field, float live, float time, long seed, int detail
   ) {
      if (detail == 1) {
         return false;
      }

      boolean wrote = false;
      int count = 4;
      float rotation = time * 0.035F;

      for (int i = 0; i < count; i++) {
         double angle = rotation + i * (float) (Math.PI * 2) / count;
         double rr = field * 0.34;
         Vec3 foot = base.add(Math.cos(angle) * rr, 0.06, Math.sin(angle) * rr);
         Vec3 tip = foot.add(0.0, 2.45 + hash(seed, 7000 + i) * 0.5, 0.0);
         wrote |= taperedRay(buffer, matrix, rel(foot, cam), rel(tip, cam), 0.03F, 255, 193, 48, live * 0.08F);
         wrote |= taperedRay(buffer, matrix, rel(foot, cam), rel(tip, cam), 0.01F, 255, 247, 201, live * 0.15F);
      }

      return wrote;
   }

   private static boolean ringBand(
      BufferBuilder buffer, Matrix4f matrix, Vec3 center, float radius, float thickness, float rotation, int r, int g, int b, float alpha, int segments
   ) {
      if (alpha <= 0.001F) {
         return false;
      }

      float inner = Math.max(0.001F, radius - thickness);
      float outer = radius + thickness;

      for (int i = 0; i < segments; i++) {
         double a0 = rotation + i * (float) (Math.PI * 2) / segments;
         double a1 = rotation + (i + 1) * (float) (Math.PI * 2) / segments;
         Vec3 i0 = center.add(Math.cos(a0) * inner, 0.0, Math.sin(a0) * inner);
         Vec3 o0 = center.add(Math.cos(a0) * outer, 0.0, Math.sin(a0) * outer);
         Vec3 i1 = center.add(Math.cos(a1) * inner, 0.0, Math.sin(a1) * inner);
         Vec3 o1 = center.add(Math.cos(a1) * outer, 0.0, Math.sin(a1) * outer);
         tri(buffer, matrix, i0, o0, o1, r, g, b, alpha);
         tri(buffer, matrix, i0, o1, i1, r, g, b, alpha);
      }

      return true;
   }

   private static boolean ringVertical(
      BufferBuilder buffer,
      Matrix4f matrix,
      Vec3 center,
      Vec3 axisA,
      Vec3 axisB,
      float radius,
      float thickness,
      float rotation,
      int r,
      int g,
      int b,
      float alpha,
      int segments
   ) {
      if (alpha <= 0.001F) {
         return false;
      }

      float inner = Math.max(0.001F, radius - thickness);
      float outer = radius + thickness;

      for (int i = 0; i < segments; i++) {
         double a0 = rotation + i * (float) (Math.PI * 2) / segments;
         double a1 = rotation + (i + 1) * (float) (Math.PI * 2) / segments;
         Vec3 d0 = axisA.scale(Math.cos(a0)).add(axisB.scale(Math.sin(a0)));
         Vec3 d1 = axisA.scale(Math.cos(a1)).add(axisB.scale(Math.sin(a1)));
         Vec3 i0 = center.add(d0.scale(inner));
         Vec3 o0 = center.add(d0.scale(outer));
         Vec3 i1 = center.add(d1.scale(inner));
         Vec3 o1 = center.add(d1.scale(outer));
         tri(buffer, matrix, i0, o0, o1, r, g, b, alpha);
         tri(buffer, matrix, i0, o1, i1, r, g, b, alpha);
      }

      return true;
   }

   private static boolean glow(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float radius, int r, int g, int b, float alpha, int segments) {
      if (!(alpha <= 0.001F) && !(radius <= 0.001F)) {
         Vec3 look = cameraLook();
         Vec3 right = new Vec3(look.z, 0.0, -look.x);
         if (right.lengthSqr() < 1.0E-8) {
            right = new Vec3(1.0, 0.0, 0.0);
         }

         right = right.normalize();
         Vec3 up = look.cross(right).normalize();

         for (int i = 0; i < segments; i++) {
            double a0 = i * (float) (Math.PI * 2) / segments;
            double a1 = (i + 1) * (float) (Math.PI * 2) / segments;
            Vec3 p0 = center.add(right.scale(Math.cos(a0) * radius)).add(up.scale(Math.sin(a0) * radius));
            Vec3 p1 = center.add(right.scale(Math.cos(a1) * radius)).add(up.scale(Math.sin(a1) * radius));
            vertex(buffer, matrix, center, r, g, b, alpha);
            vertex(buffer, matrix, p0, r, g, b, 0.0F);
            vertex(buffer, matrix, p1, r, g, b, 0.0F);
         }

         return true;
      } else {
         return false;
      }
   }

   private static boolean star(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float radius, float rotation, int r, int g, int b, float alpha) {
      if (alpha <= 0.001F) {
         return false;
      }

      Vec3 look = cameraLook();
      Vec3 right = new Vec3(look.z, 0.0, -look.x);
      if (right.lengthSqr() < 1.0E-8) {
         right = new Vec3(1.0, 0.0, 0.0);
      }

      right = right.normalize();
      Vec3 up = look.cross(right).normalize();

      for (int i = 0; i < 8; i++) {
         double a = rotation + i * (float) (Math.PI * 2) / 8.0;
         Vec3 dir = right.scale(Math.cos(a)).add(up.scale(Math.sin(a)));
         Vec3 side = look.cross(dir).normalize();
         float len = radius * (i % 2 == 0 ? 2.4F : 1.45F);
         Vec3 tip = center.add(dir.scale(len));
         Vec3 left = center.add(side.scale(radius * 0.12F));
         Vec3 rightPoint = center.add(side.scale(-radius * 0.12F));
         tri(buffer, matrix, left, rightPoint, tip, r, g, b, alpha);
      }

      return true;
   }

   private static boolean taperedRay(BufferBuilder buffer, Matrix4f matrix, Vec3 start, Vec3 end, float width, int r, int g, int b, float alpha) {
      if (alpha <= 0.001F) {
         return false;
      }

      Vec3 dir = end.subtract(start);
      if (dir.lengthSqr() < 1.0E-8) {
         return false;
      }

      Vec3 cameraDir = cameraLook();
      Vec3 side = dir.cross(cameraDir);
      if (side.lengthSqr() < 1.0E-8) {
         side = new Vec3(1.0, 0.0, 0.0);
      }

      side = side.normalize().scale(width);
      Vec3 a = start.add(side);
      Vec3 c = start.subtract(side);
      tri(buffer, matrix, a, c, end, r, g, b, alpha);
      return true;
   }

   private static boolean mantleBlade(BufferBuilder buffer, Matrix4f matrix, Vec3 start, Vec3 control, Vec3 end, float width, int r, int g, int b, float alpha) {
      if (alpha <= 0.001F) {
         return false;
      }

      Vec3 dirA = control.subtract(start);
      Vec3 dirB = end.subtract(control);
      if (!(dirA.lengthSqr() < 1.0E-8) && !(dirB.lengthSqr() < 1.0E-8)) {
         Vec3 look = cameraLook();
         Vec3 sideA = dirA.cross(look);
         Vec3 sideB = dirB.cross(look);
         if (sideA.lengthSqr() < 1.0E-8) {
            sideA = new Vec3(1.0, 0.0, 0.0);
         }

         if (sideB.lengthSqr() < 1.0E-8) {
            sideB = sideA;
         }

         sideA = sideA.normalize().scale(width);
         sideB = sideB.normalize().scale(width * 0.66F);
         Vec3 a0 = start.add(sideA);
         Vec3 a1 = start.subtract(sideA);
         Vec3 b0 = control.add(sideB);
         Vec3 b1 = control.subtract(sideB);
         tri(buffer, matrix, a0, a1, b1, r, g, b, alpha);
         tri(buffer, matrix, a0, b1, b0, r, g, b, alpha);
         tri(buffer, matrix, b0, b1, end, r, g, b, alpha * 0.88F);
         return true;
      } else {
         return false;
      }
   }

   private static boolean lineRibbon(BufferBuilder buffer, Matrix4f matrix, Vec3 start, Vec3 end, float width, int r, int g, int b, float alpha) {
      Vec3 dir = end.subtract(start);
      if (!(dir.lengthSqr() < 1.0E-8) && !(alpha <= 0.001F)) {
         Vec3 side = new Vec3(-dir.z, 0.0, dir.x);
         if (side.lengthSqr() < 1.0E-8) {
            side = new Vec3(1.0, 0.0, 0.0);
         }

         side = side.normalize().scale(width);
         Vec3 a = start.add(side);
         Vec3 b0 = start.subtract(side);
         Vec3 c = end.subtract(side);
         Vec3 d = end.add(side);
         tri(buffer, matrix, a, b0, c, r, g, b, alpha);
         tri(buffer, matrix, a, c, d, r, g, b, alpha);
         return true;
      } else {
         return false;
      }
   }

   private static Vec3 cameraLook() {
      Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
      float yaw = camera.getYRot() * (float) (Math.PI / 180.0);
      float pitch = camera.getXRot() * (float) (Math.PI / 180.0);
      float cp = Mth.cos(pitch);
      Vec3 look = new Vec3(-Mth.sin(yaw) * cp, -Mth.sin(pitch), Mth.cos(yaw) * cp);
      return look.lengthSqr() < 1.0E-8 ? new Vec3(0.0, 0.0, 1.0) : look.normalize();
   }

   private static Vec3 rel(Vec3 world, Vec3 cam) {
      return world.subtract(cam);
   }

   private static void tri(BufferBuilder buffer, Matrix4f matrix, Vec3 a, Vec3 b, Vec3 c, int r, int g, int bl, float alpha) {
      vertex(buffer, matrix, a, r, g, bl, alpha);
      vertex(buffer, matrix, b, r, g, bl, alpha);
      vertex(buffer, matrix, c, r, g, bl, alpha);
   }

   private static void vertex(BufferBuilder buffer, Matrix4f matrix, Vec3 p, int r, int g, int b, float alpha) {
      int a = Mth.clamp((int)(alpha * 255.0F), 0, 255);
      buffer.addVertex(matrix, (float)p.x, (float)p.y, (float)p.z).setColor(Mth.clamp(r, 0, 255), Mth.clamp(g, 0, 255), Mth.clamp(b, 0, 255), a);
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

   private static final class AuraState {
      private ArthurAvalonVfxData payload;
      private final long created;
      private long lastRefresh;
      private long releaseTime;
      private boolean releasing;

      private AuraState(ArthurAvalonVfxData payload, long now) {
         this.payload = payload;
         this.created = now;
         this.lastRefresh = now;
      }

      private void refresh(ArthurAvalonVfxData payload, long now) {
         this.payload = payload;
         this.lastRefresh = now;
         this.releasing = false;
      }

      private void release(ArthurAvalonVfxData payload, long now) {
         this.payload = payload;
         this.lastRefresh = now;
         this.releaseTime = now;
         this.releasing = true;
      }

      private boolean dead(long now) {
         return this.releasing ? now - this.releaseTime > 700L : now - this.lastRefresh > 1900L;
      }

      private boolean draw(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, long now) {
         Minecraft minecraft = Minecraft.getInstance();
         Entity source = this.payload.sourceId() >= 0 && minecraft.level != null ? minecraft.level.getEntity(this.payload.sourceId()) : null;
         Vec3 center = source == null ? this.payload.start() : source.position().add(0.0, 1.0, 0.0);
         double distance = cam.distanceTo(center);
         if (distance > 460.0) {
            return false;
         }

         int detail = distance < 70.0 ? 3 : (distance < 180.0 ? 2 : 1);
         float intro = AvalonHolyAuraVfxClient.smoother(Mth.clamp((float)(now - this.created) / 460.0F, 0.0F, 1.0F));
         float stale = AvalonHolyAuraVfxClient.smoother(Mth.clamp((float)(now - this.lastRefresh - 1050L) / 700.0F, 0.0F, 1.0F));
         float live = intro * (1.0F - stale);
         if (this.releasing) {
            live *= 1.0F - AvalonHolyAuraVfxClient.smoother(Mth.clamp((float)(now - this.releaseTime) / 620.0F, 0.0F, 1.0F));
         }

         if (live <= 0.002F) {
            return false;
         }

         float time = (float)now * 0.001F;
         float breath = 0.985F + 0.015F * Mth.sin(time * 1.15F);
         float radius = Mth.clamp(this.payload.radius(), 3.0F, 40.0F);
         float field = Mth.clamp(3.1F + radius * 0.075F, 3.4F, 5.8F);
         long seed = this.payload.seed();
         float yaw = source == null ? 0.0F : source.getYRot() * (float) (Math.PI / 180.0);
         Vec3 forward = new Vec3(-Mth.sin(yaw), 0.0, Mth.cos(yaw));
         if (forward.lengthSqr() < 1.0E-8) {
            forward = new Vec3(0.0, 0.0, 1.0);
         }

         forward = forward.normalize();
         Vec3 right = new Vec3(forward.z, 0.0, -forward.x).normalize();
         Vec3 base = center.add(0.0, -1.0, 0.0);
         boolean wrote = false;
         wrote |= AvalonHolyAuraVfxClient.royalSeal(buffer, matrix, cam, base, field, live, time, seed, detail);
         wrote |= AvalonHolyAuraVfxClient.bodyRadiance(buffer, matrix, cam, center, live, breath, time, detail);
         wrote |= AvalonHolyAuraVfxClient.royalCrown(buffer, matrix, cam, center, live, breath, time, right, forward, detail);
         wrote |= AvalonHolyAuraVfxClient.royalMantle(buffer, matrix, cam, center, live, time, right, forward, detail);
         wrote |= AvalonHolyAuraVfxClient.sacredMotes(buffer, matrix, cam, center, live, time, seed, detail);
         wrote |= AvalonHolyAuraVfxClient.ascendingLight(buffer, matrix, cam, base, field, live, time, seed, detail);
         return wrote | AvalonHolyAuraVfxClient.sanctuaryPillars(buffer, matrix, cam, base, field, live, time, seed, detail);
      }
   }
}
