package com.github.wal_bos.moreskills.client.fx;

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

public final class ArthurAvalonVfxChannel {
   private final String id;
   private final ArthurAvalonVfxChannel.Style style;
   private final int red;
   private final int green;
   private final int blue;
   private final List<ArthurAvalonVfxChannel.Cast> casts = new ArrayList<>();
   private boolean initialized;

   public ArthurAvalonVfxChannel(String id, ArthurAvalonVfxChannel.Style style, int red, int green, int blue) {
      this.id = id;
      this.style = style;
      this.red = red;
      this.green = green;
      this.blue = blue;
   }

   public void init() {
      if (!this.initialized) {
         this.initialized = true;
         NeoForge.EVENT_BUS.addListener(this::render);
      }
   }

   public void spawn(ArthurAvalonVfxData payload) {
      if (ClientVfxGate.allowed(this.id)) {
         this.casts.add(new ArthurAvalonVfxChannel.Cast(payload, Util.getMillis()));

         while (this.casts.size() > 32) {
            this.casts.remove(0);
         }
      }
   }

   private void render(RenderLevelStageEvent event) {
      if (ClientVfxGate.allowed(this.id) && event.getStage() == Stage.AFTER_PARTICLES) {
         Minecraft minecraft = Minecraft.getInstance();
         if (minecraft.level != null && !this.casts.isEmpty()) {
            long now = Util.getMillis();
            Vec3 camera = event.getCamera().getPosition();
            PoseStack poseStack = event.getPoseStack();
            RenderSystem.enableBlend();
            RenderSystem.disableCull();
            RenderSystem.depthMask(false);
            RenderSystem.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
            RenderSystem.setShader(GameRenderer::getPositionColorShader);
            poseStack.pushPose();
            poseStack.translate(-camera.x, -camera.y, -camera.z);
            Matrix4f matrix = poseStack.last().pose();
            BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
            boolean wrote = false;
            Iterator<ArthurAvalonVfxChannel.Cast> iterator = this.casts.iterator();

            while (iterator.hasNext()) {
               ArthurAvalonVfxChannel.Cast cast = iterator.next();
               float progress = cast.progress(now);
               if (progress >= 1.0F) {
                  iterator.remove();
               } else {
                  wrote |= this.draw(buffer, matrix, cast.payload, progress, now);
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

   private boolean draw(BufferBuilder buffer, Matrix4f matrix, ArthurAvalonVfxData payload, float progress, long now) {
      Vec3 start = new Vec3(payload.x(), payload.y(), payload.z());
      Vec3 end = new Vec3(payload.x2(), payload.y2(), payload.z2());
      float fade = Mth.clamp(Math.min(progress * 5.0F, (1.0F - progress) * 4.0F), 0.0F, 1.0F);
      float pulse = 0.72F + 0.28F * Mth.sin((float)now * 0.012F + (float)payload.seed());
      int alpha = Mth.clamp((int)(255.0F * fade * pulse), 0, 255);
      float radius = Math.max(0.6F, payload.radius());

      return switch (this.style) {
         case BEAM -> beam(buffer, matrix, start, end, radius, this.red, this.green, this.blue, alpha);
         case COLUMN -> column(buffer, matrix, start, end, radius, this.red, this.green, this.blue, alpha, progress);
         case RING -> rings(buffer, matrix, start, radius, this.red, this.green, this.blue, alpha, progress, payload.variant());
         case AURA -> aura(buffer, matrix, start, radius, this.red, this.green, this.blue, alpha, progress);
         case CROSS -> cross(buffer, matrix, start, end, radius, this.red, this.green, this.blue, alpha, progress);
         case DOMAIN -> domain(buffer, matrix, start, radius, this.red, this.green, this.blue, alpha, progress);
         case PRISON -> prison(buffer, matrix, start, radius, this.red, this.green, this.blue, alpha, progress);
         case RAIN -> rain(buffer, matrix, start, end, radius, this.red, this.green, this.blue, alpha, payload.seed(), progress);
         case KNIGHTS -> knights(buffer, matrix, start, radius, this.red, this.green, this.blue, alpha, progress);
         case REVERSE -> reverse(buffer, matrix, start, radius, this.red, this.green, this.blue, alpha, progress);
         case SEVER -> sever(buffer, matrix, start, radius, this.red, this.green, this.blue, alpha, progress);
      };
   }

   private static boolean beam(BufferBuilder buffer, Matrix4f matrix, Vec3 start, Vec3 end, float radius, int r, int g, int b, int a) {
      Vec3 direction = end.subtract(start);
      if (direction.lengthSqr() < 1.0E-6) {
         direction = new Vec3(0.0, 1.0, 0.0);
      }

      direction = direction.normalize();
      Vec3 side = direction.cross(new Vec3(0.0, 1.0, 0.0));
      if (side.lengthSqr() < 1.0E-6) {
         side = new Vec3(1.0, 0.0, 0.0);
      }

      side = side.normalize().scale(radius);
      Vec3 up = direction.cross(side).normalize().scale(radius);
      quad(buffer, matrix, start.add(side), end.add(side), end.subtract(side), start.subtract(side), r, g, b, a);
      quad(buffer, matrix, start.add(up), end.add(up), end.subtract(up), start.subtract(up), 255, 255, 235, Math.min(255, a + 35));
      return true;
   }

   private static boolean column(BufferBuilder buffer, Matrix4f matrix, Vec3 start, Vec3 end, float radius, int r, int g, int b, int a, float progress) {
      Vec3 bottom = end.y < start.y ? end : start;
      Vec3 top = end.y < start.y ? start : end;
      if (top.subtract(bottom).lengthSqr() < 4.0) {
         top = bottom.add(0.0, 120.0 * Mth.clamp(progress * 3.0F, 0.2F, 1.0F), 0.0);
      }

      beam(buffer, matrix, bottom, top, radius, r, g, b, a);
      rings(buffer, matrix, bottom, radius * 2.3F, r, g, b, a, progress, 1);
      return true;
   }

   private static boolean rings(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float radius, int r, int g, int b, int a, float progress, int variant) {
      int count = 3 + Math.floorMod(variant, 4);

      for (int ring = 0; ring < count; ring++) {
         float ringRadius = radius * (0.25F + 0.75F * Mth.clamp(progress * 1.8F - ring * 0.08F, 0.0F, 1.0F));
         float width = Math.max(0.08F, radius * 0.025F);
         horizontalRing(buffer, matrix, center.add(0.0, ring * 0.24, 0.0), ringRadius, width, r, g, b, a);
      }

      return true;
   }

   private static boolean aura(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float radius, int r, int g, int b, int a, float progress) {
      for (int y = 0; y < 7; y++) {
         float p = (y + progress * 4.0F) % 7.0F / 7.0F;
         float ringRadius = radius * (0.35F + 0.65F * Mth.sin(p * (float) Math.PI));
         horizontalRing(
            buffer, matrix, center.add(0.0, p * radius * 1.4F, 0.0), ringRadius, Math.max(0.05F, radius * 0.018F), r, g, b, (int)(a * (1.0F - p * 0.5F))
         );
      }

      return true;
   }

   private static boolean cross(BufferBuilder buffer, Matrix4f matrix, Vec3 start, Vec3 end, float radius, int r, int g, int b, int a, float progress) {
      Vec3 center = start.lerp(end, 0.82);
      Vec3 direction = end.subtract(start).normalize();
      Vec3 side = direction.cross(new Vec3(0.0, 1.0, 0.0));
      if (side.lengthSqr() < 1.0E-6) {
         side = new Vec3(1.0, 0.0, 0.0);
      }

      side = side.normalize();
      double length = radius * (0.8 + progress * 1.4);
      beam(buffer, matrix, center.subtract(side.scale(length)), center.add(side.scale(length)), Math.max(0.18F, radius * 0.08F), r, g, b, a);
      Vec3 diagonal = side.add(0.0, 1.0, 0.0).normalize();
      beam(buffer, matrix, center.subtract(diagonal.scale(length)), center.add(diagonal.scale(length)), Math.max(0.18F, radius * 0.08F), 255, 255, 245, a);
      beam(buffer, matrix, start, end, Math.max(0.12F, radius * 0.03F), r, g, b, a / 2);
      return true;
   }

   private static boolean domain(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float radius, int r, int g, int b, int a, float progress) {
      float scale = radius * Mth.clamp(progress * 2.5F, 0.08F, 1.0F);

      for (int y = -4; y <= 4; y++) {
         float latitude = y / 4.0F;
         float rr = scale * Mth.sqrt(Math.max(0.0F, 1.0F - latitude * latitude));
         horizontalRing(buffer, matrix, center.add(0.0, latitude * scale, 0.0), rr, Math.max(0.08F, radius * 0.012F), r, g, b, a / 2);
      }

      horizontalRing(buffer, matrix, center, scale, Math.max(0.12F, radius * 0.02F), 255, 255, 240, a);
      return true;
   }

   private static boolean prison(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float radius, int r, int g, int b, int a, float progress) {
      int pillars = 12;
      float current = radius * Mth.clamp(progress * 2.0F, 0.2F, 1.0F);

      for (int i = 0; i < pillars; i++) {
         double angle = i * Math.PI * 2.0 / pillars;
         Vec3 bottom = center.add(Math.cos(angle) * current, -2.0, Math.sin(angle) * current);
         Vec3 top = bottom.add(0.0, radius * 1.6, 0.0);
         beam(buffer, matrix, bottom, top, Math.max(0.08F, radius * 0.018F), r, g, b, a);
      }

      rings(buffer, matrix, center, current, r, g, b, a, progress, 3);
      return true;
   }

   private static boolean rain(BufferBuilder buffer, Matrix4f matrix, Vec3 start, Vec3 end, float radius, int r, int g, int b, int a, long seed, float progress) {
      Vec3 center = end;
      int lines = 14;

      for (int i = 0; i < lines; i++) {
         double angle = hash(seed, i) * Math.PI * 2.0;
         double distance = radius * Math.sqrt(hash(seed, i + 30));
         Vec3 bottom = center.add(Math.cos(angle) * distance, 0.0, Math.sin(angle) * distance);
         Vec3 top = bottom.add((hash(seed, i + 60) - 0.5) * 6.0, radius * (1.8 + hash(seed, i + 90)), (hash(seed, i + 120) - 0.5) * 6.0);
         beam(buffer, matrix, top, bottom, Math.max(0.05F, radius * 0.008F), i % 3 == 0 ? 255 : r, i % 3 == 0 ? 255 : g, i % 3 == 0 ? 245 : b, a);
      }

      return true;
   }

   private static boolean knights(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float radius, int r, int g, int b, int a, float progress) {
      for (int i = 0; i < 12; i++) {
         double angle = i * Math.PI * 2.0 / 12.0 + progress * 0.8;
         Vec3 point = center.add(Math.cos(angle) * radius * 0.55, 0.25, Math.sin(angle) * radius * 0.55);
         beam(buffer, matrix, point, point.add(0.0, radius * 0.9, 0.0), Math.max(0.08F, radius * 0.015F), r, g, b, a);
         horizontalRing(buffer, matrix, point.add(0.0, radius * 0.9, 0.0), radius * 0.08F, radius * 0.015F, 255, 255, 240, a);
      }

      return true;
   }

   private static boolean reverse(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float radius, int r, int g, int b, int a, float progress) {
      for (int i = 0; i < 5; i++) {
         float rr = radius * (0.22F + i * 0.15F);
         horizontalRing(
            buffer,
            matrix,
            center.add(0.0, i * 0.25, 0.0),
            rr,
            Math.max(0.08F, radius * 0.015F),
            i % 2 == 0 ? r : 255,
            i % 2 == 0 ? g : 255,
            i % 2 == 0 ? b : 245,
            a
         );
      }

      return true;
   }

   private static boolean sever(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float radius, int r, int g, int b, int a, float progress) {
      for (int i = 0; i < 9; i++) {
         double angle = i * Math.PI * 2.0 / 9.0 + progress * 1.6;
         Vec3 first = center.add(Math.cos(angle) * radius, -radius * 0.45, Math.sin(angle) * radius);
         Vec3 second = center.add(-Math.cos(angle) * radius, radius * 0.85, -Math.sin(angle) * radius);
         beam(buffer, matrix, first, second, Math.max(0.06F, radius * 0.012F), r, g, b, a);
      }

      return true;
   }

   private static void horizontalRing(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float radius, float width, int r, int g, int b, int a) {
      int segments = 64;

      for (int i = 0; i < segments; i++) {
         double a1 = i * Math.PI * 2.0 / segments;
         double a2 = (i + 1) * Math.PI * 2.0 / segments;
         Vec3 outer1 = center.add(Math.cos(a1) * (radius + width), 0.0, Math.sin(a1) * (radius + width));
         Vec3 outer2 = center.add(Math.cos(a2) * (radius + width), 0.0, Math.sin(a2) * (radius + width));
         Vec3 inner2 = center.add(Math.cos(a2) * Math.max(0.0F, radius - width), 0.0, Math.sin(a2) * Math.max(0.0F, radius - width));
         Vec3 inner1 = center.add(Math.cos(a1) * Math.max(0.0F, radius - width), 0.0, Math.sin(a1) * Math.max(0.0F, radius - width));
         quad(buffer, matrix, outer1, outer2, inner2, inner1, r, g, b, a);
      }
   }

   private static void quad(BufferBuilder buffer, Matrix4f matrix, Vec3 a, Vec3 b, Vec3 c, Vec3 d, int r, int g, int bl, int alpha) {
      buffer.addVertex(matrix, (float)a.x, (float)a.y, (float)a.z).setColor(r, g, bl, alpha);
      buffer.addVertex(matrix, (float)b.x, (float)b.y, (float)b.z).setColor(r, g, bl, alpha);
      buffer.addVertex(matrix, (float)c.x, (float)c.y, (float)c.z).setColor(r, g, bl, alpha);
      buffer.addVertex(matrix, (float)d.x, (float)d.y, (float)d.z).setColor(r, g, bl, alpha);
   }

   private static double hash(long seed, int index) {
      long value = seed + index * -7046029254386353131L;
      value ^= value >>> 30;
      value *= -4658895280553007687L;
      value ^= value >>> 27;
      value *= -7723592293110705685L;
      value ^= value >>> 31;
      return (value >>> 11) * 1.110223E-16F;
   }

   private record Cast(ArthurAvalonVfxData payload, long created) {
      private float progress(long now) {
         return (float)(now - this.created) / (Math.max(1, this.payload.durationTicks()) * 50.0F);
      }
   }

   public enum Style {
      BEAM,
      COLUMN,
      RING,
      AURA,
      CROSS,
      DOMAIN,
      PRISON,
      RAIN,
      KNIGHTS,
      REVERSE,
      SEVER;
   }
}
