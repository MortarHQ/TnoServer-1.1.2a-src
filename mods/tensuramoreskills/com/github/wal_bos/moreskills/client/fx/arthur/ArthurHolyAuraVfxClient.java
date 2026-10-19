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

public final class ArthurHolyAuraVfxClient {
   private static final Map<Integer, ArthurHolyAuraVfxClient.AuraState> ACTIVE = new HashMap<>();
   private static boolean initialized;

   private ArthurHolyAuraVfxClient() {
   }

   public static void init() {
      if (!initialized) {
         initialized = true;
         NeoForge.EVENT_BUS.addListener(ArthurHolyAuraVfxClient::render);
      }
   }

   public static void spawn(ArthurAvalonVfxData payload) {
      if (ClientVfxGate.allowed("arthur_holy_aura")) {
         init();
         Minecraft minecraft = Minecraft.getInstance();
         if (minecraft.level != null && minecraft.player != null) {
            Vec3 focus = payload.start();
            if (!(minecraft.player.distanceToSqr(focus) > 176400.0)) {
               long now = Util.getMillis();
               int key = payload.sourceId() >= 0 ? payload.sourceId() : (int)(payload.seed() ^ payload.seed() >>> 32);
               ArthurHolyAuraVfxClient.AuraState state = ACTIVE.computeIfAbsent(key, ignored -> new ArthurHolyAuraVfxClient.AuraState(payload, now));
               if (payload.variant() < 0) {
                  state.release(payload, now);
               } else {
                  state.refresh(payload, now);
               }

               while (ACTIVE.size() > 24) {
                  Integer first = ACTIVE.keySet().iterator().next();
                  ACTIVE.remove(first);
               }
            }
         }
      }
   }

   private static void render(RenderLevelStageEvent event) {
      if (!ClientVfxGate.allowed("arthur_holy_aura")) {
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
            Iterator<ArthurHolyAuraVfxClient.AuraState> iterator = ACTIVE.values().iterator();

            while (iterator.hasNext()) {
               ArthurHolyAuraVfxClient.AuraState state = iterator.next();
               if (state.dead(now)) {
                  iterator.remove();
               } else {
                  wrote |= state.render(buffer, matrix, cam, now);
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

   private static boolean bodyBloom(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 center, float radius, float live, float pulse, float beat) {
      boolean wrote = false;
      Vec3 low = rel(center.add(0.0, -0.22, 0.0), cam);
      Vec3 chest = rel(center.add(0.0, 0.38, 0.0), cam);
      Vec3 high = rel(center.add(0.0, 1.02, 0.0), cam);
      float scale = Mth.clamp(radius * 0.11F, 0.55F, 1.85F);
      wrote |= glow(buffer, matrix, low, scale * 1.95F, 255, 176, 24, live * 0.05F);
      wrote |= glow(buffer, matrix, low, scale * 1.32F, 255, 213, 75, live * 0.105F);
      wrote |= glow(buffer, matrix, chest, scale * 1.24F * pulse, 255, 228, 118, live * 0.15F);
      wrote |= glow(buffer, matrix, chest, scale * 0.78F * pulse, 255, 251, 220, live * 0.245F);
      wrote |= glow(buffer, matrix, high, scale * 0.72F * (0.92F + beat * 0.15F), 255, 238, 159, live * 0.125F);
      return wrote | starBurst(buffer, matrix, chest, scale * 0.23F * pulse, 0.0F, 255, 252, 229, live * 0.86F);
   }

   private static boolean mantle(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 center, float radius, float live, float time, long seed) {
      boolean wrote = false;
      float rr = Mth.clamp(radius * 0.1F, 0.58F, 1.7F);

      for (int layer = 0; layer < 4; layer++) {
         int strands = 18 + layer * 4;

         for (int i = 0; i < strands; i++) {
            float h = hash(seed, 1000 + layer * 100 + i);
            float q = hash(seed, 2000 + layer * 100 + i);
            double angle = i * (float) (Math.PI * 2) / strands + time * (0.12 + layer * 0.025) * (layer % 2 == 0 ? 1.0 : -1.0) + h * 0.25;
            double ring = rr * (0.58 + layer * 0.18 + q * 0.14);
            double y0 = -0.58 + h * 1.18;
            double rise = 0.48 + q * 0.78;
            Vec3 start = center.add(Math.cos(angle) * ring, y0, Math.sin(angle) * ring);
            Vec3 end = center.add(
               Math.cos(angle + 0.22 + q * 0.18) * ring * (0.76 + h * 0.18), y0 + rise, Math.sin(angle + 0.22 + q * 0.18) * ring * (0.76 + h * 0.18)
            );
            float width = 0.01F + q * 0.012F;
            float alpha = live * (0.18F + q * 0.3F);
            wrote |= ribbon(buffer, matrix, rel(start, cam), rel(end, cam), width * 4.6F, width * 1.1F, 255, 177, 28, alpha * 0.18F, 0.0F);
            wrote |= ribbon(
               buffer,
               matrix,
               rel(start.add(0.0, 0.012, 0.0), cam),
               rel(end.add(0.0, 0.012, 0.0), cam),
               width * 1.8F,
               width * 0.55F,
               255,
               225,
               111,
               alpha * 0.58F,
               0.0F
            );
            wrote |= ribbon(
               buffer,
               matrix,
               rel(start.add(0.0, 0.024, 0.0), cam),
               rel(end.add(0.0, 0.024, 0.0), cam),
               width * 0.55F,
               width * 0.16F,
               255,
               252,
               226,
               alpha,
               0.0F
            );
         }
      }

      return wrote;
   }

   private static boolean royalFiligree(
      BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 center, float radius, float live, float time, long seed, Vec3 forward, Vec3 right
   ) {
      boolean wrote = false;
      float scale = Mth.clamp(radius * 0.52F, 1.5F, 2.35F);
      Vec3 chest = center.add(0.0, 0.34, 0.0);

      for (int layer = 0; layer < 3; layer++) {
         double orbit = scale * (0.42 + layer * 0.18);
         double y = -0.08 + layer * 0.34;
         int nodes = 12 + layer * 4;

         for (int i = 0; i < nodes; i++) {
            double angle0 = i * (float) (Math.PI * 2) / nodes + time * (0.22 + layer * 0.07) * (layer % 2 == 0 ? 1.0 : -1.0);
            double angle1 = angle0 + (float) (Math.PI * 2) / nodes * 0.66;
            Vec3 radial0 = right.scale(Math.cos(angle0)).add(forward.scale(Math.sin(angle0)));
            Vec3 radial1 = right.scale(Math.cos(angle1)).add(forward.scale(Math.sin(angle1)));
            Vec3 p0 = chest.add(radial0.scale(orbit)).add(0.0, y + Math.sin(angle0 * 2.0 + time) * 0.055, 0.0);
            Vec3 p1 = chest.add(radial1.scale(orbit * (0.93 + 0.04 * Math.sin(time * 1.4 + i))))
               .add(0.0, y + 0.11 + Math.sin(angle1 * 2.0 + time) * 0.055, 0.0);
            float alpha = live * (0.22F + layer * 0.08F);
            wrote |= ribbon(buffer, matrix, rel(p0, cam), rel(p1, cam), 0.034F, 0.018F, 255, 178, 24, alpha * 0.13F, alpha * 0.08F);
            wrote |= ribbon(
               buffer, matrix, rel(p0.add(0.0, 0.008, 0.0), cam), rel(p1.add(0.0, 0.008, 0.0), cam), 0.01F, 0.006F, 255, 244, 193, alpha * 0.92F, alpha * 0.58F
            );
         }
      }

      for (int i = 0; i < 10; i++) {
         float h = hash(seed, 3500 + i);
         double a = i * (float) (Math.PI * 2) / 10.0 - time * 0.34;
         Vec3 radial = right.scale(Math.cos(a)).add(forward.scale(Math.sin(a)));
         Vec3 p = chest.add(radial.scale(scale * (0.24 + h * 0.16))).add(0.0, 0.42 + Math.sin(time * 2.1 + i) * 0.06, 0.0);
         wrote |= glow(buffer, matrix, rel(p, cam), 0.1F + h * 0.045F, 255, 188, 30, live * 0.07F);
         wrote |= star(buffer, matrix, rel(p, cam), 0.026F + h * 0.014F, (float)a, 255, 255, 235, live * 0.74F);
      }

      return wrote;
   }

   private static boolean crown(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 center, float radius, float live, float time, long seed) {
      boolean wrote = false;
      float r = Mth.clamp(radius * 0.085F, 0.52F, 1.42F);
      Vec3 halo = center.add(0.0, 1.25 + r * 0.18, 0.0);
      wrote |= horizontalRing(buffer, matrix, cam, halo, r * 0.52, r * 0.56, 54, 255, 250, 221, live * 0.92F);
      wrote |= horizontalRing(buffer, matrix, cam, halo.add(0.0, 0.028, 0.0), r * 0.72, r * 0.74, 54, 255, 205, 54, live * 0.42F);
      wrote |= horizontalRing(buffer, matrix, cam, halo.add(0.0, 0.055, 0.0), r * 0.92, r * 0.94, 54, 255, 235, 145, live * 0.2F);
      int points = 12;

      for (int i = 0; i < points; i++) {
         double angle = i * (float) (Math.PI * 2) / points + time * 0.22;
         double inner = r * 0.56;
         double outer = r * (0.75 + (i % 3 == 0 ? 0.18 : 0.07));
         Vec3 a = halo.add(Math.cos(angle) * inner, 0.0, Math.sin(angle) * inner);
         Vec3 b = halo.add(Math.cos(angle) * outer, r * (i % 3 == 0 ? 0.36 : 0.19), Math.sin(angle) * outer);
         float alpha = live * (i % 3 == 0 ? 0.88F : 0.56F);
         wrote |= ribbon(buffer, matrix, rel(a, cam), rel(b, cam), 0.034F, 0.01F, 255, 235, 139, alpha, 0.0F);
         wrote |= star(buffer, matrix, rel(b, cam), 0.055F + hash(seed, 3000 + i) * 0.028F, (float)angle, 255, 252, 225, alpha * 0.86F);
      }

      return wrote;
   }

   private static boolean wings(
      BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 center, float radius, float live, float time, long seed, Vec3 forward, Vec3 right
   ) {
      boolean wrote = false;
      float scale = Mth.clamp(radius * 0.58F, 1.65F, 2.65F);
      Vec3 wingRoot = center.add(forward.scale(-0.16)).add(0.0, 0.34, 0.0);

      for (int sideSign = -1; sideSign <= 1; sideSign += 2) {
         for (int row = 0; row < 4; row++) {
            int feathers = 8 - row;

            for (int i = 0; i < feathers; i++) {
               float u = (float)i / Math.max(1, feathers - 1);
               float h0 = hash(seed, 4100 + (sideSign + 2) * 900 + row * 100 + i * 3);
               float h1 = hash(seed, 4101 + (sideSign + 2) * 900 + row * 100 + i * 3);
               double reach = scale * (0.58 + u * 0.92 - row * 0.055);
               double lift = scale * (0.4 - u * 0.16 + row * 0.11);
               double sweep = scale * (0.12 + u * 0.3 + row * 0.055);
               double flutter = Math.sin(time * (1.55 + h0 * 0.32) + i * 0.52 + row) * 0.055;
               Vec3 root = wingRoot.add(right.scale(sideSign * scale * (0.04 + row * 0.035))).add(0.0, row * 0.09 - u * 0.045, 0.0);
               Vec3 control = root.add(right.scale(sideSign * reach * 0.54)).add(0.0, lift * 0.78 + flutter, 0.0).add(forward.scale(-sweep * 0.38));
               Vec3 tip = wingRoot.add(right.scale(sideSign * reach)).add(0.0, lift + flutter * 0.65, 0.0).add(forward.scale(-sweep));
               float baseAlpha = live * (0.46F + (1.0F - u) * 0.28F) * (1.0F - row * 0.11F);
               float width = 0.026F + (1.0F - u) * 0.014F;
               Vec3 previous = root;
               int segments = 5;

               for (int s = 1; s <= segments; s++) {
                  float q = (float)s / segments;
                  Vec3 point = quadratic(root, control, tip, q);
                  float taper = 1.0F - q * 0.78F;
                  float a = baseAlpha * (0.8F + taper * 0.2F);
                  wrote |= ribbon(
                     buffer,
                     matrix,
                     rel(previous, cam),
                     rel(point, cam),
                     width * 5.2F * taper,
                     width * 3.4F * Math.max(0.12F, taper),
                     255,
                     168,
                     18,
                     a * 0.11F,
                     a * 0.07F
                  );
                  wrote |= ribbon(
                     buffer,
                     matrix,
                     rel(previous.add(0.0, 0.009, 0.0), cam),
                     rel(point.add(0.0, 0.009, 0.0), cam),
                     width * 1.7F * taper,
                     width * 1.05F * Math.max(0.12F, taper),
                     255,
                     224,
                     106,
                     a * 0.52F,
                     a * 0.32F
                  );
                  wrote |= ribbon(
                     buffer,
                     matrix,
                     rel(previous.add(0.0, 0.018, 0.0), cam),
                     rel(point.add(0.0, 0.018, 0.0), cam),
                     width * 0.46F * taper,
                     width * 0.25F * Math.max(0.12F, taper),
                     255,
                     254,
                     234,
                     a,
                     a * 0.55F
                  );
                  previous = point;
               }

               if (i % 2 == 0) {
                  wrote |= glow(buffer, matrix, rel(tip, cam), 0.08F + h1 * 0.045F, 255, 213, 77, baseAlpha * 0.1F);
                  wrote |= diamond(buffer, matrix, rel(tip, cam), 0.025F + h1 * 0.018F, (float)(time * 0.7 + i), 255, 252, 225, baseAlpha * 0.82F);
               }
            }
         }
      }

      return wrote;
   }

   private static boolean floorSeal(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 center, float radius, float live, float time, long seed) {
      boolean wrote = false;
      float r = Mth.clamp(radius * 0.36F, 1.8F, 6.4F);
      wrote |= horizontalRing(buffer, matrix, cam, center, r * 0.96, r, 72, 255, 197, 40, live * 0.38F);
      wrote |= horizontalRing(buffer, matrix, cam, center.add(0.0, 0.014, 0.0), r * 0.72, r * 0.735, 72, 255, 242, 177, live * 0.68F);
      wrote |= horizontalRing(buffer, matrix, cam, center.add(0.0, 0.028, 0.0), r * 0.42, r * 0.435, 72, 255, 252, 225, live * 0.82F);
      wrote |= horizontalRing(buffer, matrix, cam, center.add(0.0, 0.042, 0.0), r * 0.19, r * 0.205, 48, 255, 211, 72, live * 0.66F);
      int spokes = 16;

      for (int i = 0; i < spokes; i++) {
         double angle = i * (float) (Math.PI * 2) / spokes + time * 0.055;
         Vec3 a = center.add(Math.cos(angle) * r * 0.26, 0.046, Math.sin(angle) * r * 0.26);
         Vec3 b = center.add(Math.cos(angle) * r * (i % 4 == 0 ? 0.91 : 0.78), 0.046, Math.sin(angle) * r * (i % 4 == 0 ? 0.91 : 0.78));
         float alpha = live * (i % 4 == 0 ? 0.78F : 0.34F);
         wrote |= ribbonFixed(buffer, matrix, rel(a, cam), rel(b, cam), new Vec3(0.0, 1.0, 0.0), 0.024F, 0.008F, 255, 229, 126, alpha, alpha * 0.45F);
      }

      for (int i = 0; i < 8; i++) {
         double angle = i * (float) (Math.PI * 2) / 8.0 - time * 0.09;
         double angle2 = angle + (float) (Math.PI / 4);
         Vec3 p0 = center.add(Math.cos(angle) * r * 0.55, 0.056, Math.sin(angle) * r * 0.55);
         Vec3 p1 = center.add(Math.cos(angle2) * r * 0.55, 0.056, Math.sin(angle2) * r * 0.55);
         wrote |= ribbonFixed(buffer, matrix, rel(p0, cam), rel(p1, cam), new Vec3(0.0, 1.0, 0.0), 0.018F, 0.018F, 255, 247, 206, live * 0.5F, live * 0.5F);
      }

      return wrote;
   }

   private static boolean ascenders(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 base, float radius, float live, float time, long seed) {
      boolean wrote = false;
      float spread = Mth.clamp(radius * 0.34F, 1.8F, 6.0F);

      for (int i = 0; i < 44; i++) {
         float h0 = hash(seed, 5000 + i * 3);
         float h1 = hash(seed, 5001 + i * 3);
         float h2 = hash(seed, 5002 + i * 3);
         double angle = h0 * (float) (Math.PI * 2) + time * (0.05 + h2 * 0.06);
         double dist = Math.sqrt(h1) * spread;
         double cycle = (time * (0.2 + h2 * 0.16) + h0) % 1.0;
         double y0 = 0.12 + cycle * (1.2 + h2 * 2.6);
         double len = 0.16 + h1 * 0.48;
         Vec3 start = base.add(Math.cos(angle) * dist, y0, Math.sin(angle) * dist);
         Vec3 end = start.add(Math.cos(angle + 0.8) * 0.04, len, Math.sin(angle + 0.8) * 0.04);
         float fade = 1.0F - smooth(Mth.clamp((float)cycle, 0.0F, 1.0F));
         float alpha = live * fade * (0.22F + h2 * 0.52F);
         float width = 0.008F + h1 * 0.009F;
         wrote |= ribbon(buffer, matrix, rel(start, cam), rel(end, cam), width * 4.0F, width * 1.2F, 255, 186, 30, alpha * 0.16F, 0.0F);
         wrote |= ribbon(
            buffer, matrix, rel(start.add(0.0, 0.01, 0.0), cam), rel(end.add(0.0, 0.01, 0.0), cam), width, width * 0.28F, 255, 249, 220, alpha, 0.0F
         );
      }

      return wrote;
   }

   private static boolean orbitMotes(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 center, float radius, float live, float time, long seed) {
      boolean wrote = false;
      float spread = Mth.clamp(radius * 0.28F, 1.5F, 5.0F);

      for (int i = 0; i < 36; i++) {
         float h0 = hash(seed, 6000 + i * 4);
         float h1 = hash(seed, 6001 + i * 4);
         float h2 = hash(seed, 6002 + i * 4);
         float h3 = hash(seed, 6003 + i * 4);
         double angle = h0 * (float) (Math.PI * 2) + time * (0.18 + h1 * 0.2) * (i % 2 == 0 ? 1.0 : -1.0);
         double dist = spread * (0.35 + h1 * 0.88);
         double y = -0.45 + h2 * 2.35 + Math.sin(time * 2.0 + i) * 0.08;
         Vec3 point = rel(center.add(Math.cos(angle) * dist, y, Math.sin(angle) * dist), cam);
         float flicker = 0.48F + 0.52F * Mth.sin(time * (4.0F + h3 * 5.0F) + i * 0.9F);
         float alpha = live * smooth(Mth.clamp(flicker, 0.0F, 1.0F)) * (0.3F + h3 * 0.54F);
         float size = 0.035F + h1 * 0.05F;
         wrote |= glow(buffer, matrix, point, size * 3.8F, 255, 188, 38, alpha * 0.12F);
         if (i % 3 == 0) {
            wrote |= starBurst(buffer, matrix, point, size, (float)angle, 255, 250, 222, alpha);
         } else if ((i & 1) == 0) {
            wrote |= diamond(buffer, matrix, point, size * 0.72F, (float)(-angle), 255, 224, 112, alpha * 0.92F);
         } else {
            wrote |= star(buffer, matrix, point, size * 0.82F, (float)angle, 255, 239, 174, alpha * 0.86F);
         }
      }

      return wrote;
   }

   private static boolean featherStorm(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 center, float radius, float live, float time, long seed) {
      boolean wrote = false;
      float spread = Mth.clamp(radius * 0.25F, 1.4F, 4.6F);

      for (int i = 0; i < 18; i++) {
         float h0 = hash(seed, 7000 + i * 5);
         float h1 = hash(seed, 7001 + i * 5);
         float h2 = hash(seed, 7002 + i * 5);
         float h3 = hash(seed, 7003 + i * 5);
         float h4 = hash(seed, 7004 + i * 5);
         double cycle = (time * (0.1 + h1 * 0.1) + h0) % 1.0;
         double angle = h2 * (float) (Math.PI * 2) + time * 0.11;
         double dist = spread * (0.26 + h3 * 0.9);
         double y = -0.62 + cycle * 2.75;
         Vec3 point = center.add(Math.cos(angle) * dist, y, Math.sin(angle) * dist);
         Vec3 dir = new Vec3(Math.cos(angle + 1.35) * 0.14, 0.3 + h4 * 0.22, Math.sin(angle + 1.35) * 0.14).normalize();
         Vec3 tail = point.subtract(dir.scale(0.18 + h1 * 0.22));
         float fade = Mth.sin((float)cycle * (float) Math.PI);
         float alpha = live * fade * (0.25F + h4 * 0.45F);
         float width = 0.01F + h0 * 0.012F;
         wrote |= ribbon(buffer, matrix, rel(tail, cam), rel(point, cam), width * 3.4F, width * 0.5F, 255, 198, 49, alpha * 0.18F, 0.0F);
         wrote |= ribbon(
            buffer, matrix, rel(tail.add(0.0, 0.01, 0.0), cam), rel(point.add(0.0, 0.01, 0.0), cam), width, width * 0.12F, 255, 250, 221, alpha, 0.0F
         );
      }

      return wrote;
   }

   private static boolean pulseRings(
      BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 base, float radius, float live, float time, int pulseIndex, float opening, float releaseBurst
   ) {
      boolean wrote = false;
      float maxR = Mth.clamp(radius * 0.42F, 2.2F, 7.8F);
      float cycle = (time * 0.55F + pulseIndex * 0.17F) % 1.0F;

      for (int i = 0; i < 3; i++) {
         float q = (cycle + i / 3.0F) % 1.0F;
         float rr = maxR * (0.22F + q * 0.8F);
         float alpha = live * (1.0F - smooth(q)) * 0.28F;
         wrote |= horizontalRing(buffer, matrix, cam, base.add(0.0, 0.08 + i * 0.025, 0.0), rr * 0.98, rr, 64, 255, 224, 116, alpha);
      }

      if (opening > 0.001F) {
         float rr = maxR * (0.38F + (1.0F - opening) * 0.95F);
         wrote |= horizontalRing(buffer, matrix, cam, base.add(0.0, 0.12, 0.0), rr * 0.96, rr, 72, 255, 250, 220, opening * live * 0.84F);
      }

      if (releaseBurst > 0.001F) {
         float rr = maxR * (0.45F + releaseBurst * 0.95F);
         wrote |= horizontalRing(buffer, matrix, cam, base.add(0.0, 0.14, 0.0), rr * 0.95, rr, 72, 255, 198, 48, releaseBurst * 0.78F);
      }

      return wrote;
   }

   private static Vec3 quadratic(Vec3 a, Vec3 b, Vec3 c, float t) {
      double u = 1.0 - t;
      return a.scale(u * u).add(b.scale(2.0 * u * t)).add(c.scale(t * t));
   }

   private static boolean horizontalRing(
      BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 center, double inner, double outer, int segments, int red, int green, int blue, float alpha
   ) {
      if (!(alpha <= 0.001F) && !(outer <= inner)) {
         Vec3 c = rel(center, cam);

         for (int i = 0; i < segments; i++) {
            double a0 = i * (float) (Math.PI * 2) / segments;
            double a1 = (i + 1) * (float) (Math.PI * 2) / segments;
            Vec3 p0 = c.add(Math.cos(a0) * inner, 0.0, Math.sin(a0) * inner);
            Vec3 p1 = c.add(Math.cos(a0) * outer, 0.0, Math.sin(a0) * outer);
            Vec3 p2 = c.add(Math.cos(a1) * outer, 0.0, Math.sin(a1) * outer);
            Vec3 p3 = c.add(Math.cos(a1) * inner, 0.0, Math.sin(a1) * inner);
            tri(buffer, matrix, p0, p1, p2, red, green, blue, alpha * 0.72F, alpha, alpha);
            tri(buffer, matrix, p0, p2, p3, red, green, blue, alpha * 0.72F, alpha, alpha * 0.72F);
         }

         return true;
      } else {
         return false;
      }
   }

   private static boolean glow(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float size, int red, int green, int blue, float alpha) {
      if (!(alpha <= 0.001F) && !(size <= 0.0F)) {
         ArthurHolyAuraVfxClient.Axes axes = axes(center, 0.0F);
         Vec3 right = axes.right.scale(size);
         Vec3 up = axes.up.scale(size);
         Vec3 top = center.add(up);
         Vec3 rightPoint = center.add(right);
         Vec3 bottom = center.subtract(up);
         Vec3 left = center.subtract(right);
         tri(buffer, matrix, center, top, rightPoint, red, green, blue, alpha, 0.0F, 0.0F);
         tri(buffer, matrix, center, rightPoint, bottom, red, green, blue, alpha, 0.0F, 0.0F);
         tri(buffer, matrix, center, bottom, left, red, green, blue, alpha, 0.0F, 0.0F);
         tri(buffer, matrix, center, left, top, red, green, blue, alpha, 0.0F, 0.0F);
         return true;
      } else {
         return false;
      }
   }

   private static boolean star(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float size, float rotation, int red, int green, int blue, float alpha) {
      if (!(alpha <= 0.001F) && !(size <= 0.0F)) {
         ArthurHolyAuraVfxClient.Axes axes = axes(center, rotation);
         Vec3 right = axes.right;
         Vec3 up = axes.up;
         Vec3 top = center.add(up.scale(size * 1.35));
         Vec3 bottom = center.subtract(up.scale(size * 1.35));
         Vec3 left = center.subtract(right.scale(size * 0.82));
         Vec3 rightPoint = center.add(right.scale(size * 0.82));
         Vec3 innerTop = center.add(up.scale(size * 0.08));
         Vec3 innerBottom = center.subtract(up.scale(size * 0.08));
         Vec3 innerLeft = center.subtract(right.scale(size * 0.07));
         Vec3 innerRight = center.add(right.scale(size * 0.07));
         tri(buffer, matrix, top, innerRight, innerLeft, red, green, blue, alpha, alpha, alpha);
         tri(buffer, matrix, rightPoint, innerBottom, innerTop, red, green, blue, alpha, alpha, alpha);
         tri(buffer, matrix, bottom, innerLeft, innerRight, red, green, blue, alpha, alpha, alpha);
         tri(buffer, matrix, left, innerTop, innerBottom, red, green, blue, alpha, alpha, alpha);
         return true;
      } else {
         return false;
      }
   }

   private static boolean starBurst(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float size, float rotation, int red, int green, int blue, float alpha) {
      boolean wrote = star(buffer, matrix, center, size, rotation, red, green, blue, alpha);
      wrote |= star(
         buffer, matrix, center, size * 0.58F, rotation + (float) (Math.PI / 4), 255, Math.min(255, green + 16), Math.min(255, blue + 20), alpha * 0.74F
      );
      return wrote | diamond(buffer, matrix, center, size * 0.24F, rotation, 255, 255, 242, alpha * 0.94F);
   }

   private static boolean diamond(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float size, float rotation, int red, int green, int blue, float alpha) {
      if (!(alpha <= 0.001F) && !(size <= 0.0F)) {
         ArthurHolyAuraVfxClient.Axes axes = axes(center, rotation);
         Vec3 right = axes.right.scale(size * 0.62);
         Vec3 up = axes.up.scale(size);
         Vec3 top = center.add(up);
         Vec3 rp = center.add(right);
         Vec3 bottom = center.subtract(up);
         Vec3 left = center.subtract(right);
         tri(buffer, matrix, top, rp, center, red, green, blue, alpha, alpha, alpha);
         tri(buffer, matrix, rp, bottom, center, red, green, blue, alpha, alpha, alpha);
         tri(buffer, matrix, bottom, left, center, red, green, blue, alpha, alpha, alpha);
         tri(buffer, matrix, left, top, center, red, green, blue, alpha, alpha, alpha);
         return true;
      } else {
         return false;
      }
   }

   private static boolean ribbon(
      BufferBuilder buffer,
      Matrix4f matrix,
      Vec3 first,
      Vec3 second,
      float firstWidth,
      float secondWidth,
      int red,
      int green,
      int blue,
      float firstAlpha,
      float secondAlpha
   ) {
      if (firstAlpha <= 0.001F && secondAlpha <= 0.001F) {
         return false;
      }

      Vec3 direction = second.subtract(first);
      if (direction.lengthSqr() < 1.0E-8) {
         return false;
      }

      Vec3 midpoint = first.add(second).scale(0.5);
      Vec3 view = midpoint.scale(-1.0);
      if (view.lengthSqr() < 1.0E-8) {
         view = new Vec3(0.0, 0.0, 1.0);
      }

      Vec3 side = direction.cross(view);
      if (side.lengthSqr() < 1.0E-8) {
         side = direction.cross(new Vec3(0.0, 1.0, 0.0));
      }

      if (side.lengthSqr() < 1.0E-8) {
         side = new Vec3(1.0, 0.0, 0.0);
      }

      side = side.normalize();
      Vec3 a = first.add(side.scale(firstWidth));
      Vec3 b = first.subtract(side.scale(firstWidth));
      Vec3 c = second.subtract(side.scale(secondWidth));
      Vec3 d = second.add(side.scale(secondWidth));
      tri(buffer, matrix, a, b, c, red, green, blue, firstAlpha, firstAlpha, secondAlpha);
      tri(buffer, matrix, a, c, d, red, green, blue, firstAlpha, secondAlpha, secondAlpha);
      return true;
   }

   private static boolean ribbonFixed(
      BufferBuilder buffer,
      Matrix4f matrix,
      Vec3 first,
      Vec3 second,
      Vec3 side,
      float firstWidth,
      float secondWidth,
      int red,
      int green,
      int blue,
      float firstAlpha,
      float secondAlpha
   ) {
      if (firstAlpha <= 0.001F && secondAlpha <= 0.001F) {
         return false;
      } else if (!(first.distanceToSqr(second) < 1.0E-8) && !(side.lengthSqr() < 1.0E-8)) {
         Vec3 normal = side.normalize();
         Vec3 a = first.add(normal.scale(firstWidth));
         Vec3 b = first.subtract(normal.scale(firstWidth));
         Vec3 c = second.subtract(normal.scale(secondWidth));
         Vec3 d = second.add(normal.scale(secondWidth));
         tri(buffer, matrix, a, b, c, red, green, blue, firstAlpha, firstAlpha, secondAlpha);
         tri(buffer, matrix, a, c, d, red, green, blue, firstAlpha, secondAlpha, secondAlpha);
         return true;
      } else {
         return false;
      }
   }

   private static ArthurHolyAuraVfxClient.Axes axes(Vec3 center, float rotation) {
      Vec3 view = center.scale(-1.0);
      if (view.lengthSqr() < 1.0E-8) {
         view = new Vec3(0.0, 0.0, 1.0);
      }

      view = view.normalize();
      Vec3 reference = Math.abs(view.y) > 0.94 ? new Vec3(1.0, 0.0, 0.0) : new Vec3(0.0, 1.0, 0.0);
      Vec3 right = reference.cross(view);
      if (right.lengthSqr() < 1.0E-8) {
         right = new Vec3(1.0, 0.0, 0.0);
      }

      right = right.normalize();
      Vec3 up = view.cross(right).normalize();
      double c = Mth.cos(rotation);
      double s = Mth.sin(rotation);
      return new ArthurHolyAuraVfxClient.Axes(right.scale(c).add(up.scale(s)), up.scale(c).subtract(right.scale(s)));
   }

   private static Vec3 rel(Vec3 point, Vec3 camera) {
      return point.subtract(camera);
   }

   private static void tri(BufferBuilder buffer, Matrix4f matrix, Vec3 a, Vec3 b, Vec3 c, int red, int green, int blue, float aa, float ab, float ac) {
      vertex(buffer, matrix, a, red, green, blue, aa);
      vertex(buffer, matrix, b, red, green, blue, ab);
      vertex(buffer, matrix, c, red, green, blue, ac);
   }

   private static void vertex(BufferBuilder buffer, Matrix4f matrix, Vec3 position, int red, int green, int blue, float alpha) {
      buffer.addVertex(matrix, (float)position.x, (float)position.y, (float)position.z)
         .setColor(Mth.clamp(red, 0, 255), Mth.clamp(green, 0, 255), Mth.clamp(blue, 0, 255), Mth.clamp((int)(alpha * 255.0F), 0, 255));
   }

   private static float smooth(float value) {
      value = Mth.clamp(value, 0.0F, 1.0F);
      return value * value * (3.0F - 2.0F * value);
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
      private int pulseIndex;

      private AuraState(ArthurAvalonVfxData payload, long now) {
         this.payload = payload;
         this.created = now;
         this.lastRefresh = now;
         this.pulseIndex = Math.max(0, payload.variant());
      }

      private void refresh(ArthurAvalonVfxData payload, long now) {
         this.payload = payload;
         this.lastRefresh = now;
         this.releasing = false;
         this.pulseIndex = Math.max(this.pulseIndex, payload.variant());
      }

      private void release(ArthurAvalonVfxData payload, long now) {
         this.payload = payload;
         this.lastRefresh = now;
         this.releaseTime = now;
         this.releasing = true;
      }

      private boolean dead(long now) {
         return this.releasing ? now - this.releaseTime > 850L : now - this.lastRefresh > 2400L;
      }

      private boolean render(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, long now) {
         Minecraft minecraft = Minecraft.getInstance();
         Vec3 center = this.payload.start();
         Entity source = this.payload.sourceId() >= 0 && minecraft.level != null ? minecraft.level.getEntity(this.payload.sourceId()) : null;
         if (source != null) {
            center = source.position().add(0.0, 1.0, 0.0);
         }

         float gameplayRadius = Mth.clamp(this.payload.radius(), 2.2F, 32.0F);
         float bodyScale = Mth.clamp(2.75F + gameplayRadius * 0.045F, 2.9F, 4.15F);
         float fieldScale = Mth.clamp(3.8F + gameplayRadius * 0.12F, 4.2F, 7.4F);
         double elapsedMillis = Math.max(0L, now - this.created);
         float ageTicks = (float)(elapsedMillis / 50.0);
         float birth = ArthurHolyAuraVfxClient.smooth(Mth.clamp((float)(elapsedMillis / 420.0), 0.0F, 1.0F));
         float stale = Mth.clamp((float)(now - this.lastRefresh - 1350L) / 900.0F, 0.0F, 1.0F);
         float live = birth * (1.0F - ArthurHolyAuraVfxClient.smooth(stale));
         if (this.releasing) {
            live *= 1.0F - ArthurHolyAuraVfxClient.smooth(Mth.clamp((float)(now - this.releaseTime) / 760.0F, 0.0F, 1.0F));
         }

         if (live <= 0.001F) {
            return false;
         }

         float t = (float)(now * 0.001);
         float pulse = 0.88F + 0.12F * Mth.sin((float)(now * 0.0064) + this.pulseIndex * 0.31F);
         float beat = 0.5F + 0.5F * Mth.sin((float)(now * 0.0031) + (float)this.payload.seed() * 1.3E-5F);
         float opening = 1.0F - ArthurHolyAuraVfxClient.smooth(Mth.clamp((float)(elapsedMillis / 900.0), 0.0F, 1.0F));
         float releaseBurst = this.releasing ? Mth.sin(Mth.clamp((float)(now - this.releaseTime) / 760.0F, 0.0F, 1.0F) * (float) Math.PI) : 0.0F;
         float power = Mth.clamp(live * (0.94F + pulse * 0.12F), 0.0F, 1.0F);
         long seed = this.payload.seed();
         Vec3 base = center.add(0.0, -1.0, 0.0);
         float yaw = source == null ? 0.0F : source.getYRot() * (float) (Math.PI / 180.0);
         Vec3 forward = new Vec3(-Mth.sin(yaw), 0.0, Mth.cos(yaw));
         if (forward.lengthSqr() < 1.0E-8) {
            forward = new Vec3(0.0, 0.0, 1.0);
         }

         forward = forward.normalize();
         Vec3 right = new Vec3(forward.z, 0.0, -forward.x).normalize();
         boolean wrote = false;
         wrote |= ArthurHolyAuraVfxClient.bodyBloom(buffer, matrix, cam, center, bodyScale, power, pulse, beat);
         wrote |= ArthurHolyAuraVfxClient.mantle(buffer, matrix, cam, center, bodyScale, power, t, seed);
         wrote |= ArthurHolyAuraVfxClient.royalFiligree(buffer, matrix, cam, center, bodyScale, power, t, seed, forward, right);
         wrote |= ArthurHolyAuraVfxClient.crown(buffer, matrix, cam, center, bodyScale, power, t, seed);
         wrote |= ArthurHolyAuraVfxClient.wings(buffer, matrix, cam, center, bodyScale, power, t, seed, forward, right);
         wrote |= ArthurHolyAuraVfxClient.floorSeal(buffer, matrix, cam, base.add(0.0, 0.035, 0.0), fieldScale, power, t, seed);
         wrote |= ArthurHolyAuraVfxClient.ascenders(buffer, matrix, cam, base, fieldScale, power, t, seed);
         wrote |= ArthurHolyAuraVfxClient.orbitMotes(buffer, matrix, cam, center, bodyScale, power, t, seed);
         wrote |= ArthurHolyAuraVfxClient.featherStorm(buffer, matrix, cam, center, fieldScale, power, t, seed);
         return wrote | ArthurHolyAuraVfxClient.pulseRings(buffer, matrix, cam, base, fieldScale, power, t, this.pulseIndex, opening, releaseBurst);
      }
   }

   private record Axes(Vec3 right, Vec3 up) {
   }
}
