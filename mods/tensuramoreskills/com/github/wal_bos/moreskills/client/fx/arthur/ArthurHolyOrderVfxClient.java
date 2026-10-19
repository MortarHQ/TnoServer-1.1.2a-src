package com.github.wal_bos.moreskills.client.fx.arthur;

import com.github.wal_bos.moreskills.client.fx.ClientVfxGate;
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

public final class ArthurHolyOrderVfxClient {
   private static final List<ArthurHolyOrderVfxClient.Cast> CASTS = new ArrayList<>();
   private static boolean initialized;

   private ArthurHolyOrderVfxClient() {
   }

   public static void init() {
      if (!initialized) {
         initialized = true;
         NeoForge.EVENT_BUS.addListener(ArthurHolyOrderVfxClient::render);
      }
   }

   public static void spawn(ArthurAvalonVfxData payload) {
      if (ClientVfxGate.allowed("arthur_holy_order")) {
         Minecraft minecraft = Minecraft.getInstance();
         if (minecraft.level != null && minecraft.player != null) {
            if (!(minecraft.player.distanceToSqr(payload.start()) > 129600.0)) {
               CASTS.add(new ArthurHolyOrderVfxClient.Cast(payload, Util.getMillis()));

               while (CASTS.size() > 20) {
                  CASTS.remove(0);
               }
            }
         }
      }
   }

   private static void render(RenderLevelStageEvent event) {
      if (ClientVfxGate.allowed("arthur_holy_order") && event.getStage() == Stage.AFTER_PARTICLES) {
         Minecraft minecraft = Minecraft.getInstance();
         if (minecraft.level != null && !CASTS.isEmpty()) {
            long now = Util.getMillis();
            Vec3 camera = event.getCamera().getPosition();
            PoseStack poseStack = event.getPoseStack();
            RenderSystem.enableBlend();
            RenderSystem.disableCull();
            RenderSystem.enableDepthTest();
            RenderSystem.depthMask(false);
            RenderSystem.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
            RenderSystem.setShader(GameRenderer::getPositionColorShader);
            poseStack.pushPose();
            poseStack.translate(-camera.x, -camera.y, -camera.z);
            Matrix4f matrix = poseStack.last().pose();
            BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
            boolean wrote = false;
            Iterator<ArthurHolyOrderVfxClient.Cast> iterator = CASTS.iterator();

            while (iterator.hasNext()) {
               ArthurHolyOrderVfxClient.Cast cast = iterator.next();
               float p = cast.progress(now);
               if (p >= 1.0F) {
                  iterator.remove();
               } else {
                  draw(buffer, matrix, cast.payload(), p, now);
                  wrote = true;
               }
            }

            if (wrote) {
               BufferUploader.drawWithShader(buffer.buildOrThrow());
            }

            poseStack.popPose();
            RenderSystem.depthMask(true);
            RenderSystem.enableCull();
            RenderSystem.disableBlend();
            RenderSystem.defaultBlendFunc();
         }
      }
   }

   private static void draw(BufferBuilder buffer, Matrix4f matrix, ArthurAvalonVfxData payload, float p, long now) {
      Vec3 center = payload.start();
      float radius = Math.max(4.0F, payload.radius());
      float birth = smooth(Mth.clamp(p * 7.0F, 0.0F, 1.0F));
      float fade = smooth(Mth.clamp((1.0F - p) * 5.0F, 0.0F, 1.0F));
      float live = birth * fade;
      float pulse = 0.84F + 0.16F * Mth.sin((float)now * 0.018F + payload.variant() * 0.5F);
      int bright = alpha(250.0F * live * pulse);
      int strong = alpha(180.0F * live);
      int soft = alpha(92.0F * live);
      float phase = payload.variant() * 0.23F + p * 2.0F;
      pressureShell(buffer, matrix, center.add(0.0, radius * 0.48, 0.0), radius * 0.96F, phase, strong, soft);

      for (int i = 0; i < 5; i++) {
         float q = Mth.clamp(p * 2.2F - i * 0.075F, 0.0F, 1.0F);
         float rr = radius * (0.25F + q * (0.85F + i * 0.07F));
         ring(
            buffer,
            matrix,
            center.add(0.0, 0.04 + i * 0.018, 0.0),
            rr,
            Math.max(0.04F, radius * 0.01F),
            i % 2 == 0 ? 255 : 255,
            i % 2 == 0 ? 224 : 248,
            i % 2 == 0 ? 112 : 211,
            i == 0 ? bright : strong
         );
      }

      floorSeal(buffer, matrix, center.add(0.0, 0.06, 0.0), radius * 0.72F, phase, bright, strong);
      pressureSpikes(buffer, matrix, center, radius, phase, payload.seed(), strong, soft);
      kingCrown(buffer, matrix, center.add(0.0, radius * 1.18, 0.0), radius * 0.36F, radius * 0.28F, phase, bright);

      for (int i = 0; i < 8; i++) {
         double a = i * Math.PI * 2.0 / 8.0 + phase * 0.5;
         Vec3 outer = center.add(Math.cos(a) * radius * 0.9, radius * 0.44, Math.sin(a) * radius * 0.9);
         Vec3 inner = center.add(Math.cos(a) * radius * 0.23, radius * 0.72, Math.sin(a) * radius * 0.23);
         beam(buffer, matrix, outer, inner, Math.max(0.022F, radius * 0.012F), 255, 239, 173, soft);
      }
   }

   private static void pressureShell(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float radius, float phase, int strong, int soft) {
      for (int i = -5; i <= 5; i++) {
         float lat = i / 5.0F;
         float rr = radius * Mth.sqrt(Math.max(0.0F, 1.0F - lat * lat));
         ring(
            buffer,
            matrix,
            center.add(0.0, lat * radius * 0.72, 0.0),
            rr,
            Math.max(0.03F, radius * 0.006F),
            i == 0 ? 255 : 255,
            i == 0 ? 245 : 216,
            i == 0 ? 205 : 103,
            i == 0 ? strong : soft
         );
      }

      for (int i = 0; i < 12; i++) {
         double a = i * Math.PI * 2.0 / 12.0 + phase;
         Vec3 low = center.add(Math.cos(a) * radius * 0.84, -radius * 0.6, Math.sin(a) * radius * 0.84);
         Vec3 high = center.add(Math.cos(a + 0.24) * radius * 0.84, radius * 0.6, Math.sin(a + 0.24) * radius * 0.84);
         beam(buffer, matrix, low, high, Math.max(0.018F, radius * 0.007F), 255, 224, 125, soft);
      }
   }

   private static void floorSeal(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float radius, float phase, int bright, int strong) {
      ring(buffer, matrix, center, radius, radius * 0.02F, 255, 220, 103, bright);
      ring(buffer, matrix, center, radius * 0.54F, radius * 0.01F, 255, 249, 218, strong);

      for (int i = 0; i < 16; i++) {
         double a = i * Math.PI * 2.0 / 16.0 + phase * 0.17;
         Vec3 s = center.add(Math.cos(a) * radius * 0.3, 0.0, Math.sin(a) * radius * 0.3);
         Vec3 e = center.add(Math.cos(a) * radius * 0.91, 0.0, Math.sin(a) * radius * 0.91);
         beam(buffer, matrix, s, e, Math.max(0.016F, radius * 0.007F), 255, 235, 154, strong);
      }

      for (int i = 0; i < 6; i++) {
         double a = i * Math.PI / 3.0 + phase * 0.09;
         Vec3 a1 = center.add(Math.cos(a) * radius * 0.44, 0.0, Math.sin(a) * radius * 0.44);
         Vec3 a2 = center.add(Math.cos(a + (Math.PI * 2.0 / 3.0)) * radius * 0.44, 0.0, Math.sin(a + (Math.PI * 2.0 / 3.0)) * radius * 0.44);
         beam(buffer, matrix, a1, a2, Math.max(0.016F, radius * 0.008F), 255, 249, 224, bright);
      }
   }

   private static void pressureSpikes(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float radius, float phase, long seed, int strong, int soft) {
      for (int i = 0; i < 18; i++) {
         double a = hash(seed, i) * Math.PI * 2.0 + phase * 0.42;
         double rr = radius * (0.46 + hash(seed, i + 40) * 0.44);
         double h = radius * (0.2 + hash(seed, i + 80) * 0.52);
         Vec3 base = center.add(Math.cos(a) * rr, 0.06, Math.sin(a) * rr);
         Vec3 tip = base.add(-Math.cos(a) * radius * 0.12, h, -Math.sin(a) * radius * 0.12);
         beam(
            buffer,
            matrix,
            base,
            tip,
            Math.max(0.018F, radius * 0.008F),
            i % 4 == 0 ? 255 : 255,
            i % 4 == 0 ? 251 : 218,
            i % 4 == 0 ? 222 : 106,
            i % 4 == 0 ? strong : soft
         );
      }
   }

   private static void kingCrown(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float radius, float height, float phase, int alpha) {
      ring(buffer, matrix, center, radius, Math.max(0.025F, radius * 0.05F), 255, 249, 221, alpha);

      for (int i = 0; i < 8; i++) {
         double a = i * Math.PI * 2.0 / 8.0 + phase * 0.3;
         Vec3 b = center.add(Math.cos(a) * radius, 0.0, Math.sin(a) * radius);
         Vec3 t = b.add(Math.cos(a) * radius * 0.22, height * (i % 2 == 0 ? 1.0 : 0.65), Math.sin(a) * radius * 0.22);
         beam(buffer, matrix, b, t, Math.max(0.02F, radius * 0.07F), 255, 245, 198, alpha);
      }
   }

   private static void ring(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float radius, float width, int r, int g, int b, int a) {
      int segments = 48;
      double inner = Math.max(0.0, radius - width);
      double outer = radius + width;

      for (int i = 0; i < segments; i++) {
         double a1 = i * Math.PI * 2.0 / segments;
         double a2 = (i + 1) * Math.PI * 2.0 / segments;
         Vec3 o1 = center.add(Math.cos(a1) * outer, 0.0, Math.sin(a1) * outer);
         Vec3 o2 = center.add(Math.cos(a2) * outer, 0.0, Math.sin(a2) * outer);
         Vec3 i2 = center.add(Math.cos(a2) * inner, 0.0, Math.sin(a2) * inner);
         Vec3 i1 = center.add(Math.cos(a1) * inner, 0.0, Math.sin(a1) * inner);
         quad(buffer, matrix, o1, o2, i2, i1, r, g, b, a);
      }
   }

   private static void beam(BufferBuilder buffer, Matrix4f matrix, Vec3 start, Vec3 end, float width, int r, int g, int b, int a) {
      Vec3 dir = end.subtract(start);
      if (!(dir.lengthSqr() < 1.0E-7)) {
         dir = dir.normalize();
         Vec3 side = dir.cross(new Vec3(0.0, 1.0, 0.0));
         if (side.lengthSqr() < 1.0E-7) {
            side = dir.cross(new Vec3(1.0, 0.0, 0.0));
         }

         side = side.normalize().scale(width);
         Vec3 up = dir.cross(side).normalize().scale(width);
         quad(buffer, matrix, start.add(side), end.add(side), end.subtract(side), start.subtract(side), r, g, b, a);
         quad(buffer, matrix, start.add(up), end.add(up), end.subtract(up), start.subtract(up), r, g, b, a);
      }
   }

   private static void quad(BufferBuilder buffer, Matrix4f matrix, Vec3 a, Vec3 b, Vec3 c, Vec3 d, int r, int g, int bl, int alpha) {
      buffer.addVertex(matrix, (float)a.x, (float)a.y, (float)a.z).setColor(r, g, bl, alpha);
      buffer.addVertex(matrix, (float)b.x, (float)b.y, (float)b.z).setColor(r, g, bl, alpha);
      buffer.addVertex(matrix, (float)c.x, (float)c.y, (float)c.z).setColor(r, g, bl, alpha);
      buffer.addVertex(matrix, (float)d.x, (float)d.y, (float)d.z).setColor(r, g, bl, alpha);
   }

   private static float smooth(float x) {
      return x * x * (3.0F - 2.0F * x);
   }

   private static int alpha(float value) {
      return Mth.clamp((int)value, 0, 255);
   }

   private static double hash(long seed, int index) {
      long v = seed + index * -7046029254386353131L;
      v ^= v >>> 30;
      v *= -4658895280553007687L;
      v ^= v >>> 27;
      v *= -7723592293110705685L;
      v ^= v >>> 31;
      return (v >>> 11) * 1.110223E-16F;
   }

   private record Cast(ArthurAvalonVfxData payload, long created) {
      private float progress(long now) {
         return (float)(now - this.created) / (Math.max(1, this.payload.durationTicks()) * 50.0F);
      }
   }
}
