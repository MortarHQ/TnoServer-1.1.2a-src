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

public final class ArthurTrueExcaliburVfxClient {
   private static final List<ArthurTrueExcaliburVfxClient.Cast> CASTS = new ArrayList<>();
   private static boolean initialized;

   private ArthurTrueExcaliburVfxClient() {
   }

   public static void init() {
      if (!initialized) {
         initialized = true;
         NeoForge.EVENT_BUS.addListener(ArthurTrueExcaliburVfxClient::render);
      }
   }

   public static void spawn(ArthurAvalonVfxData payload) {
      if (ClientVfxGate.allowed("arthur_true_excalibur")) {
         Minecraft minecraft = Minecraft.getInstance();
         if (minecraft.level != null && minecraft.player != null) {
            if (!(minecraft.player.distanceToSqr(payload.start()) > 176400.0)) {
               CASTS.add(new ArthurTrueExcaliburVfxClient.Cast(payload, Util.getMillis()));

               while (CASTS.size() > 6) {
                  CASTS.remove(0);
               }
            }
         }
      }
   }

   private static void render(RenderLevelStageEvent event) {
      if (ClientVfxGate.allowed("arthur_true_excalibur") && event.getStage() == Stage.AFTER_PARTICLES) {
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
            Iterator<ArthurTrueExcaliburVfxClient.Cast> iterator = CASTS.iterator();

            while (iterator.hasNext()) {
               ArthurTrueExcaliburVfxClient.Cast cast = iterator.next();
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
      Vec3 base = payload.start();
      Vec3 top = payload.end();
      if (top.subtract(base).lengthSqr() < 1.0) {
         top = base.add(0.0, 8.0, 0.0);
      }

      float radius = Math.max(4.0F, payload.radius());
      float birth = smooth(Mth.clamp(p * 4.0F, 0.0F, 1.0F));
      float fade = smooth(Mth.clamp((1.0F - p) * 3.0F, 0.0F, 1.0F));
      float live = birth * fade;
      float surge = smooth(Mth.clamp((p - 0.52F) / 0.2F, 0.0F, 1.0F)) * (1.0F - smooth(Mth.clamp((p - 0.86F) / 0.14F, 0.0F, 1.0F)));
      float pulse = 0.88F + 0.12F * Mth.sin((float)now * 0.016F);
      int white = alpha(255.0F * live * pulse);
      int gold = alpha(190.0F * live);
      int soft = alpha(82.0F * live);
      float phase = p * 2.4F;
      beam(buffer, matrix, base, top.add(0.0, radius * 1.15, 0.0), radius * (0.2F + surge * 0.1F), 255, 208, 71, soft);
      beam(buffer, matrix, base.add(0.0, 0.1, 0.0), top.add(0.0, radius * 1.05, 0.0), radius * (0.09F + surge * 0.05F), 255, 250, 225, white);

      for (int i = 0; i < 9; i++) {
         float q = i / 8.0F;
         float rr = radius * (0.36F + 0.44F * Mth.sin(q * (float) Math.PI)) * (0.92F + surge * 0.2F);
         Vec3 c = base.lerp(top, q).add(0.0, q * radius * 0.4, 0.0);
         ring(
            buffer,
            matrix,
            c,
            rr,
            Math.max(0.03F, radius * 0.012F),
            i % 2 == 0 ? 255 : 255,
            i % 2 == 0 ? 246 : 216,
            i % 2 == 0 ? 211 : 91,
            i % 2 == 0 ? white : gold
         );
      }

      floorRune(buffer, matrix, base.add(0.0, 0.05, 0.0), radius * 0.84F, phase, white, gold);
      swordCrown(buffer, matrix, base.add(0.0, radius * 0.18, 0.0), radius, phase, white, gold);
      ascensionWings(buffer, matrix, base.add(0.0, radius * 0.72, 0.0), radius, phase, white, gold);

      for (int i = 0; i < 14; i++) {
         double a = hash(payload.seed(), i) * Math.PI * 2.0 + phase;
         double rr = radius * (0.34 + hash(payload.seed(), i + 40) * 0.5);
         double y = radius * (0.22 + hash(payload.seed(), i + 80) * 1.55);
         Vec3 s = base.add(Math.cos(a) * rr, y, Math.sin(a) * rr);
         Vec3 e = s.add(0.0, radius * (0.18 + hash(payload.seed(), i + 120) * 0.22), 0.0);
         beam(buffer, matrix, s, e, Math.max(0.018F, radius * 0.009F), 255, 231, 139, soft);
      }
   }

   private static void floorRune(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float radius, float phase, int white, int gold) {
      ring(buffer, matrix, center, radius, radius * 0.018F, 255, 214, 91, gold);
      ring(buffer, matrix, center, radius * 0.58F, radius * 0.01F, 255, 250, 225, white);

      for (int i = 0; i < 12; i++) {
         double a = i * Math.PI * 2.0 / 12.0 + phase * 0.21;
         Vec3 s = center.add(Math.cos(a) * radius * 0.18, 0.0, Math.sin(a) * radius * 0.18);
         Vec3 e = center.add(Math.cos(a) * radius * 0.9, 0.0, Math.sin(a) * radius * 0.9);
         beam(buffer, matrix, s, e, Math.max(0.016F, radius * 0.007F), 255, 234, 148, gold);
      }

      for (int i = 0; i < 4; i++) {
         double a = i * Math.PI * 0.5 + phase * 0.11;
         Vec3 p1 = center.add(Math.cos(a) * radius * 0.48, 0.0, Math.sin(a) * radius * 0.48);
         Vec3 p2 = center.add(Math.cos(a + (Math.PI / 2)) * radius * 0.48, 0.0, Math.sin(a + (Math.PI / 2)) * radius * 0.48);
         beam(buffer, matrix, p1, p2, Math.max(0.018F, radius * 0.01F), 255, 250, 226, white);
      }
   }

   private static void swordCrown(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float radius, float phase, int white, int gold) {
      for (int i = 0; i < 8; i++) {
         double a = i * Math.PI * 2.0 / 8.0 + phase * 0.35;
         Vec3 root = center.add(Math.cos(a) * radius * 0.62, radius * 0.06, Math.sin(a) * radius * 0.62);
         Vec3 tip = center.add(Math.cos(a) * radius * 0.82, radius * 0.86, Math.sin(a) * radius * 0.82);
         beam(buffer, matrix, root, tip, radius * 0.026F, 255, 218, 103, gold);
         Vec3 guardA = root.add(-Math.sin(a) * radius * 0.11, radius * 0.14, Math.cos(a) * radius * 0.11);
         Vec3 guardB = root.add(Math.sin(a) * radius * 0.11, radius * 0.14, -Math.cos(a) * radius * 0.11);
         beam(buffer, matrix, guardA, guardB, radius * 0.02F, 255, 250, 226, white);
      }
   }

   private static void ascensionWings(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float radius, float phase, int white, int gold) {
      Vec3 right = new Vec3(1.0, 0.0, 0.0);
      Vec3 back = new Vec3(0.0, 0.0, -1.0);

      for (int side = -1; side <= 1; side += 2) {
         for (int i = 0; i < 6; i++) {
            double t = i / 5.0;
            Vec3 root = center.add(right.scale(side * radius * 0.08));
            Vec3 tip = center.add(right.scale(side * radius * (0.3 + t * 0.7)))
               .add(0.0, radius * (0.3 + t * 0.32), 0.0)
               .add(back.scale(radius * (0.08 + t * 0.2)));
            beam(buffer, matrix, root, tip, radius * (0.026F - i * 0.0024F), 255, i < 2 ? 250 : 220, i < 2 ? 225 : 114, i < 2 ? white : gold);
         }
      }
   }

   private static void ring(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float radius, float width, int r, int g, int b, int a) {
      int segments = 46;
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
