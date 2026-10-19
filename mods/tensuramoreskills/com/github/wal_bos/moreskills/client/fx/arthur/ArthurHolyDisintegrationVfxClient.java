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
import java.util.List;
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

public final class ArthurHolyDisintegrationVfxClient {
   private static final List<ArthurHolyDisintegrationVfxClient.Cast> CASTS = new ArrayList<>();
   private static boolean initialized;

   private ArthurHolyDisintegrationVfxClient() {
   }

   public static void init() {
      if (!initialized) {
         initialized = true;
         NeoForge.EVENT_BUS.addListener(ArthurHolyDisintegrationVfxClient::render);
      }
   }

   public static void spawn(ArthurAvalonVfxData payload) {
      if (ClientVfxGate.allowed("arthur_holy_disintegration")) {
         init();
         Minecraft minecraft = Minecraft.getInstance();
         if (minecraft.level != null && minecraft.player != null) {
            Vec3 focus = payload.end();
            if (!(minecraft.player.distanceToSqr(focus) > 577600.0)) {
               CASTS.add(new ArthurHolyDisintegrationVfxClient.Cast(payload, Util.getMillis()));

               while (CASTS.size() > 4) {
                  CASTS.remove(0);
               }
            }
         }
      }
   }

   private static void render(RenderLevelStageEvent event) {
      if (!ClientVfxGate.allowed("arthur_holy_disintegration")) {
         CASTS.clear();
      } else if (event.getStage() == Stage.AFTER_PARTICLES) {
         Minecraft minecraft = Minecraft.getInstance();
         if (minecraft.level != null && minecraft.player != null) {
            if (!CASTS.isEmpty()) {
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
               Iterator<ArthurHolyDisintegrationVfxClient.Cast> iterator = CASTS.iterator();

               while (iterator.hasNext()) {
                  ArthurHolyDisintegrationVfxClient.Cast cast = iterator.next();
                  float progress = cast.progress(now);
                  if (progress >= 1.0F) {
                     iterator.remove();
                  } else if (cast.payload.variant() == 1) {
                     wrote |= renderStrike(buffer, matrix, cam, cast.payload, progress, now);
                  } else {
                     wrote |= renderCharge(buffer, matrix, cam, cast.payload, progress, now);
                  }
               }

               if (wrote) {
                  try {
                     BufferUploader.drawWithShader(buffer.buildOrThrow());
                  } catch (IllegalStateException var12) {
                  }
               }

               RenderSystem.depthMask(true);
               RenderSystem.enableCull();
               RenderSystem.defaultBlendFunc();
               RenderSystem.disableBlend();
            }
         } else {
            CASTS.clear();
         }
      }
   }

   private static boolean renderCharge(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, ArthurAvalonVfxData payload, float progress, long now) {
      Minecraft minecraft = Minecraft.getInstance();
      Vec3 caster = payload.start();
      Entity source = payload.sourceId() >= 0 && minecraft.level != null ? minecraft.level.getEntity(payload.sourceId()) : null;
      if (source != null) {
         caster = source.position().add(0.0, source.getBbHeight() * 0.62, 0.0);
      }

      Vec3 target = payload.end();
      float gameplayRadius = Mth.clamp(payload.radius(), 4.0F, 48.0F);
      float scale = Mth.clamp(5.0F + gameplayRadius * 0.1F, 5.5F, 9.5F);
      float birth = smooth(Mth.clamp(progress * 10.0F, 0.0F, 1.0F));
      float fade = smooth(Mth.clamp((1.0F - progress) * 18.0F, 0.0F, 1.0F));
      float late = smooth(Mth.clamp((progress - 0.64F) / 0.36F, 0.0F, 1.0F));
      float terminal = smooth(Mth.clamp((progress - 0.84F) / 0.16F, 0.0F, 1.0F));
      float live = birth * fade;
      float time = (float)now * 0.001F;
      float acceleration = progress * progress * progress;
      float spinSpeed = 0.18F + acceleration * 6.8F;
      boolean wrote = false;
      wrote |= chargeRingSequence(buffer, matrix, cam, target, scale, live, progress, late, terminal, time, spinSpeed);
      wrote |= chargeCentralMandala(buffer, matrix, cam, target, scale, live, progress, late, time, spinSpeed);
      wrote |= chargeConvergence(buffer, matrix, cam, target, scale, live, late, terminal, time, spinSpeed);
      return wrote | chargeCasterFocus(buffer, matrix, cam, caster, target, scale, live, progress, terminal, time);
   }

   private static boolean chargeRingSequence(
      BufferBuilder buffer,
      Matrix4f matrix,
      Vec3 cam,
      Vec3 target,
      float scale,
      float live,
      float progress,
      float late,
      float terminal,
      float time,
      float spinSpeed
   ) {
      boolean wrote = false;
      int layers = 11;

      for (int i = 0; i < layers; i++) {
         float delay = i * 0.055F;
         float appear = smooth(Mth.clamp((progress - delay) / 0.18F, 0.0F, 1.0F));
         if (!(appear <= 0.001F)) {
            float u = (float)i / (layers - 1);
            double baseHeight = 0.1 + u * scale * 3.15;
            double collapse = late * scale * 0.82 * u;
            double y = baseHeight - collapse;
            double radius = scale * (1.18 - u * 0.34);
            radius *= 1.0 - terminal * (0.18 + u * 0.12);
            Vec3 center = target.add(0.0, y, 0.0);
            int sign = (i & 1) == 0 ? 1 : -1;
            double spin = time * spinSpeed * sign * (0.72 + i * 0.065) + i * 0.41;
            float alpha = live * appear * (0.46F + u * 0.34F);

            Vec3 normal = switch (i % 4) {
               case 1 -> new Vec3(0.32, 0.92, 0.22).normalize();
               case 2 -> new Vec3(-0.28, 0.9, 0.34).normalize();
               case 3 -> new Vec3(0.24, 0.88, -0.41).normalize();
               default -> new Vec3(0.0, 1.0, 0.0);
            };
            wrote |= segmentedRing(buffer, matrix, cam, center, normal, radius, 0.032F, 96, spin, 255, 174, 18, alpha * 0.42F, 9, 1);
            wrote |= segmentedRing(
               buffer, matrix, cam, center.add(0.0, 0.022, 0.0), normal, radius * 0.72, 0.018F, 84, -spin * 1.26, 255, 225, 108, alpha * 0.78F, 8, 1
            );
            wrote |= segmentedRing(buffer, matrix, cam, center.add(0.0, 0.044, 0.0), normal, radius * 0.43, 0.009F, 72, spin * 1.68, 255, 255, 235, alpha, 7, 1);
            if (i % 2 == 0) {
               wrote |= mandalaSpokes(buffer, matrix, cam, center, normal, radius * 0.94, 12, spin * 0.36, alpha * 0.56F);
            }

            if (i == 3 || i == 7 || i == 10) {
               wrote |= nestedGlyph(buffer, matrix, cam, center, normal, radius * 0.53, spin, alpha * 0.74F);
            }
         }
      }

      return wrote;
   }

   private static boolean chargeCentralMandala(
      BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 target, float scale, float live, float progress, float late, float time, float spinSpeed
   ) {
      boolean wrote = false;
      float appear = smooth(Mth.clamp(progress / 0.22F, 0.0F, 1.0F));
      float alpha = live * appear;
      Vec3 center = target.add(0.0, 0.075, 0.0);
      double radius = scale * (1.35 - late * 0.18);
      double spin = time * spinSpeed * 0.44;
      wrote |= segmentedRing(buffer, matrix, cam, center, new Vec3(0.0, 1.0, 0.0), radius, 0.05F, 112, spin, 255, 159, 4, alpha * 0.44F, 10, 1);
      wrote |= segmentedRing(
         buffer, matrix, cam, center.add(0.0, 0.02, 0.0), new Vec3(0.0, 1.0, 0.0), radius * 0.8, 0.026F, 96, -spin * 1.32, 255, 221, 92, alpha * 0.78F, 8, 1
      );
      wrote |= segmentedRing(
         buffer, matrix, cam, center.add(0.0, 0.04, 0.0), new Vec3(0.0, 1.0, 0.0), radius * 0.58, 0.015F, 84, spin * 1.86, 255, 249, 204, alpha, 7, 1
      );
      wrote |= segmentedRing(
         buffer, matrix, cam, center.add(0.0, 0.06, 0.0), new Vec3(0.0, 1.0, 0.0), radius * 0.31, 0.01F, 64, -spin * 2.35, 255, 255, 244, alpha, 6, 1
      );
      wrote |= mandalaSpokes(buffer, matrix, cam, center.add(0.0, 0.045, 0.0), new Vec3(0.0, 1.0, 0.0), radius * 0.94, 24, spin * 0.22, alpha * 0.68F);
      return wrote | nestedGlyph(buffer, matrix, cam, center.add(0.0, 0.065, 0.0), new Vec3(0.0, 1.0, 0.0), radius * 0.52, -spin * 0.72, alpha * 0.84F);
   }

   private static boolean chargeConvergence(
      BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 target, float scale, float live, float late, float terminal, float time, float spinSpeed
   ) {
      boolean wrote = false;
      if (late <= 0.001F) {
         return false;
      }

      Vec3 focus = target.add(0.0, 1.05 + terminal * 0.5, 0.0);
      int rays = 16;

      for (int i = 0; i < rays; i++) {
         double angle = i * (float) (Math.PI * 2) / rays + time * spinSpeed * 0.18;
         double radius = scale * (1.05 - terminal * 0.28);
         double y = scale * (1.65 + i % 4 * 0.32);
         Vec3 start = target.add(Math.cos(angle) * radius, y, Math.sin(angle) * radius);
         Vec3 bend = target.add(Math.cos(angle + 0.28) * radius * 0.54, y * 0.56, Math.sin(angle + 0.28) * radius * 0.54);
         float alpha = live * late * (0.28F + terminal * 0.52F);
         wrote |= curvedRibbon(buffer, matrix, cam, start, bend, focus, 10, 0.018F, 0.005F, 255, 230, 130, alpha);
      }

      Vec3 relFocus = rel(focus, cam);
      wrote |= glow(buffer, matrix, relFocus, scale * (0.72F + terminal * 0.44F), 255, 149, 0, live * late * 0.045F);
      wrote |= glow(buffer, matrix, relFocus, scale * (0.36F + terminal * 0.22F), 255, 224, 104, live * late * 0.105F);
      return wrote | glow(buffer, matrix, relFocus, scale * (0.15F + terminal * 0.1F), 255, 255, 239, live * late * 0.24F);
   }

   private static boolean chargeCasterFocus(
      BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 caster, Vec3 target, float scale, float live, float progress, float terminal, float time
   ) {
      boolean wrote = false;
      Vec3 chest = rel(caster, cam);
      float alpha = live * (0.42F + progress * 0.58F);
      wrote |= glow(buffer, matrix, chest, scale * 0.34F, 255, 184, 30, alpha * 0.08F);
      wrote |= glow(buffer, matrix, chest, scale * 0.16F, 255, 247, 201, alpha * 0.2F);
      Vec3 start = caster;
      Vec3 end = target.add(0.0, 1.05, 0.0);
      Vec3 dir = end.subtract(start);
      if (dir.lengthSqr() > 1.0E-8) {
         Vec3 side = stablePerpendicular(dir.normalize());
         Vec3 control = start.lerp(end, 0.52).add(side.scale(Mth.sin(time * 2.0F) * 0.28 * (1.0 - terminal)));
         wrote |= curvedRibbon(buffer, matrix, cam, start, control, end, 18, 0.018F, 0.004F, 255, 245, 191, alpha * (0.34F + terminal * 0.52F));
      }

      return wrote;
   }

   private static boolean renderStrike(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, ArthurAvalonVfxData payload, float progress, long now) {
      Vec3 top = payload.start();
      Vec3 target = payload.end();
      if (top.distanceToSqr(target) < 64.0) {
         top = target.add(0.0, 96.0, 0.0);
      }

      float gameplayRadius = Mth.clamp(payload.radius(), 4.0F, 48.0F);
      float scale = Mth.clamp(5.0F + gameplayRadius * 0.1F, 5.5F, 9.5F);
      float birth = smooth(Mth.clamp(progress * 24.0F, 0.0F, 1.0F));
      float fade = smooth(Mth.clamp((1.0F - progress) * 4.2F, 0.0F, 1.0F));
      float expansion = smooth(Mth.clamp(progress / 0.24F, 0.0F, 1.0F));
      float aftermath = smooth(Mth.clamp((progress - 0.24F) / 0.7F, 0.0F, 1.0F));
      float live = birth * fade;
      float time = (float)now * 0.001F;
      boolean wrote = false;
      wrote |= strikeSkyMandala(buffer, matrix, cam, top, target, scale, live, expansion, time);
      wrote |= strikeBeam(buffer, matrix, cam, top, target, scale, live, expansion, time);
      wrote |= strikeBeamCollars(buffer, matrix, cam, top, target, scale, live, expansion, time);
      wrote |= strikeBeamVeils(buffer, matrix, cam, top, target, scale, live, expansion, time);
      return wrote | strikeImpact(buffer, matrix, cam, target, scale, live, expansion, aftermath, time);
   }

   private static boolean strikeSkyMandala(
      BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 top, Vec3 target, float scale, float live, float expansion, float time
   ) {
      Vec3 dir = target.subtract(top);
      if (dir.lengthSqr() < 1.0E-8) {
         return false;
      }

      dir = dir.normalize();
      Vec3 center = top.add(dir.scale(scale * 0.4));
      double radius = scale * (1.18 + expansion * 0.16);
      float alpha = live;
      double spin = time * (2.2 + expansion * 2.8);
      boolean wrote = false;
      wrote |= segmentedRing(buffer, matrix, cam, center, dir, radius, 0.06F, 120, spin, 255, 153, 0, alpha * 0.5F, 10, 1);
      wrote |= segmentedRing(buffer, matrix, cam, center.add(dir.scale(0.06)), dir, radius * 0.76, 0.034F, 108, -spin * 1.3, 255, 222, 95, alpha * 0.84F, 9, 1);
      wrote |= segmentedRing(buffer, matrix, cam, center.add(dir.scale(0.12)), dir, radius * 0.47, 0.018F, 88, spin * 1.75, 255, 255, 238, alpha, 7, 1);
      wrote |= mandalaSpokes(buffer, matrix, cam, center.add(dir.scale(0.1)), dir, radius * 0.94, 24, -spin * 0.28, alpha * 0.72F);
      return wrote | nestedGlyph(buffer, matrix, cam, center.add(dir.scale(0.14)), dir, radius * 0.54, spin * 0.56, alpha * 0.9F);
   }

   private static boolean strikeBeam(
      BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 top, Vec3 target, float scale, float live, float expansion, float time
   ) {
      Vec3 dir = target.subtract(top);
      if (dir.lengthSqr() < 1.0E-8) {
         return false;
      }

      dir = dir.normalize();
      ArthurHolyDisintegrationVfxClient.Basis basis = basis(dir);
      Vec3 a = rel(top, cam);
      Vec3 b = rel(target, cam);
      float pulse = 0.96F + 0.04F * Mth.sin(time * 20.0F);
      float outer = Mth.clamp(scale * (0.3F + expansion * 0.18F), 1.8F, 4.2F);
      boolean wrote = false;

      for (int i = 0; i < 6; i++) {
         double angle = i * (float) Math.PI / 6.0 + time * 0.18;
         Vec3 side = basis.right.scale(Math.cos(angle)).add(basis.up.scale(Math.sin(angle))).normalize();
         wrote |= ribbonFixed(buffer, matrix, a, b, side, outer * 1.55F, outer * 1.55F, 255, 110, 0, live * 0.026F, live * 0.026F);
         wrote |= ribbonFixed(buffer, matrix, a, b, side, outer * 1.0F, outer * 1.0F, 255, 176, 20, live * 0.07F, live * 0.07F);
         wrote |= ribbonFixed(buffer, matrix, a, b, side, outer * 0.58F, outer * 0.58F, 255, 222, 103, live * 0.24F, live * 0.24F);
         wrote |= ribbonFixed(buffer, matrix, a, b, side, outer * 0.27F * pulse, outer * 0.27F * pulse, 255, 251, 219, live * 0.68F, live * 0.68F);
         wrote |= ribbonFixed(buffer, matrix, a, b, side, outer * 0.085F, outer * 0.085F, 255, 255, 255, live, live);
      }

      double length = top.distanceTo(target);
      int nodes = Math.max(7, Math.min(15, (int)(length / 8.0)));

      for (int i = 1; i < nodes; i++) {
         float u = (float)i / nodes;
         Vec3 p = rel(top.lerp(target, u), cam);
         float envelope = Mth.sin(u * (float) Math.PI);
         wrote |= glow(buffer, matrix, p, outer * (1.35F + envelope * 0.55F), 255, 156, 0, live * 0.028F);
         wrote |= glow(buffer, matrix, p, outer * (0.62F + envelope * 0.22F), 255, 233, 132, live * 0.075F);
      }

      return wrote;
   }

   private static boolean strikeBeamCollars(
      BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 top, Vec3 target, float scale, float live, float expansion, float time
   ) {
      Vec3 dir = target.subtract(top);
      if (dir.lengthSqr() < 1.0E-8) {
         return false;
      }

      double length = dir.length();
      dir = dir.normalize();
      boolean wrote = false;
      int collars = 14;

      for (int i = 0; i < collars; i++) {
         float u = (i + 0.5F) / collars;
         Vec3 center = top.add(dir.scale(length * u));
         double radius = scale * (0.54 + 0.1 * Mth.sin(i * 0.84F + time * 2.0F));
         double spin = time * (2.0 + expansion * 3.4) * ((i & 1) == 0 ? 1.0 : -1.0) + i * 0.32;
         float alpha = live * (0.32F + 0.46F * Mth.sin(u * (float) Math.PI));
         wrote |= segmentedRing(buffer, matrix, cam, center, dir, radius, 0.017F, 64, spin, 255, 226, 116, alpha, 8, 1);
         wrote |= segmentedRing(
            buffer, matrix, cam, center.add(dir.scale(0.018)), dir, radius * 0.62, 0.007F, 52, -spin * 1.45, 255, 255, 234, alpha * 0.78F, 7, 1
         );
      }

      return wrote;
   }

   private static boolean strikeBeamVeils(
      BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 top, Vec3 target, float scale, float live, float expansion, float time
   ) {
      Vec3 dir = target.subtract(top);
      if (dir.lengthSqr() < 1.0E-8) {
         return false;
      }

      double length = dir.length();
      dir = dir.normalize();
      ArthurHolyDisintegrationVfxClient.Basis basis = basis(dir);
      boolean wrote = false;
      int lanes = 6;

      for (int lane = 0; lane < lanes; lane++) {
         int sign = (lane & 1) == 0 ? 1 : -1;
         double phase = lane * (float) (Math.PI * 2) / lanes + time * (0.75 + expansion * 1.35) * sign;
         Vec3 previous = null;

         for (int s = 0; s <= 42; s++) {
            float u = s / 42.0F;
            double angle = phase + sign * u * (float) (Math.PI * 2) * 1.35;
            double radius = scale * (0.72 + 0.1 * Mth.sin(u * (float) Math.PI * 2.0F));
            Vec3 radial = basis.right.scale(Math.cos(angle)).add(basis.up.scale(Math.sin(angle)));
            Vec3 point = top.add(dir.scale(length * u)).add(radial.scale(radius));
            if (previous != null) {
               float envelope = Mth.sin(u * (float) Math.PI);
               float alpha = live * envelope * 0.56F;
               wrote |= ribbon(buffer, matrix, rel(previous, cam), rel(point, cam), 0.045F, 0.04F, 255, 158, 6, alpha * 0.1F, alpha * 0.1F);
               wrote |= ribbon(
                  buffer,
                  matrix,
                  rel(previous.add(dir.scale(0.01)), cam),
                  rel(point.add(dir.scale(0.01)), cam),
                  0.014F,
                  0.012F,
                  255,
                  232,
                  132,
                  alpha * 0.64F,
                  alpha * 0.64F
               );
               wrote |= ribbon(
                  buffer, matrix, rel(previous.add(dir.scale(0.018)), cam), rel(point.add(dir.scale(0.018)), cam), 0.004F, 0.003F, 255, 255, 242, alpha, alpha
               );
            }

            previous = point;
         }
      }

      return wrote;
   }

   private static boolean strikeImpact(
      BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 target, float scale, float live, float expansion, float aftermath, float time
   ) {
      boolean wrote = false;
      Vec3 ground = target.add(0.0, 0.09, 0.0);
      double max = scale * 2.55;

      for (int i = 0; i < 7; i++) {
         float phase = Mth.clamp(expansion * 1.35F - i * 0.095F, 0.0F, 1.0F);
         if (!(phase <= 0.001F)) {
            float q = smooth(phase);
            double radius = max * (0.12 + q * (0.3 + i * 0.095));
            float alpha = live * (1.0F - q * 0.58F) * (0.44F - i * 0.042F);
            wrote |= segmentedRing(
               buffer,
               matrix,
               cam,
               ground.add(0.0, i * 0.018, 0.0),
               new Vec3(0.0, 1.0, 0.0),
               radius,
               0.028F,
               96,
               time * (0.25 + i * 0.05) * ((i & 1) == 0 ? 1.0 : -1.0),
               255,
               i < 2 ? 249 : 218,
               i < 2 ? 214 : 92,
               alpha,
               9,
               1
            );
         }
      }

      float mandalaAlpha = live * (0.48F + expansion * 0.52F);
      wrote |= mandalaSpokes(buffer, matrix, cam, ground.add(0.0, 0.05, 0.0), new Vec3(0.0, 1.0, 0.0), scale * 1.75, 32, time * 0.18, mandalaAlpha * 0.68F);
      wrote |= nestedGlyph(buffer, matrix, cam, ground.add(0.0, 0.075, 0.0), new Vec3(0.0, 1.0, 0.0), scale * 1.05, -time * 0.36, mandalaAlpha * 0.82F);
      Vec3 impact = rel(target.add(0.0, scale * 0.22, 0.0), cam);
      wrote |= glow(buffer, matrix, impact, scale * (2.4F + expansion * 0.8F), 255, 120, 0, live * 0.03F);
      wrote |= glow(buffer, matrix, impact, scale * (1.35F + expansion * 0.45F), 255, 205, 58, live * 0.078F);
      wrote |= glow(buffer, matrix, impact, scale * (0.68F + expansion * 0.22F), 255, 246, 195, live * 0.17F);
      if (aftermath > 0.001F) {
         for (int i = 0; i < 3; i++) {
            double radius = scale * (1.15 + aftermath * (1.1 + i * 0.48));
            float alpha = live * (1.0F - aftermath) * (0.28F - i * 0.055F);
            wrote |= segmentedRing(
               buffer,
               matrix,
               cam,
               ground.add(0.0, 0.12 + i * 0.035, 0.0),
               new Vec3(0.0, 1.0, 0.0),
               radius,
               0.014F,
               80,
               -time * (0.18 + i * 0.05),
               255,
               231,
               135,
               alpha,
               10,
               1
            );
         }
      }

      return wrote;
   }

   private static boolean mandalaSpokes(
      BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 center, Vec3 normal, double radius, int spokes, double spin, float alpha
   ) {
      if (alpha <= 0.001F) {
         return false;
      }

      ArthurHolyDisintegrationVfxClient.Basis basis = basis(normal);
      boolean wrote = false;

      for (int i = 0; i < spokes; i++) {
         double angle = i * (float) (Math.PI * 2) / spokes + spin;
         Vec3 radial = basis.right.scale(Math.cos(angle)).add(basis.up.scale(Math.sin(angle)));
         double inner = radius * (i % 4 == 0 ? 0.16 : 0.24);
         double outer = radius * (i % 4 == 0 ? 0.96 : (i % 2 == 0 ? 0.82 : 0.7));
         Vec3 a = center.add(radial.scale(inner));
         Vec3 b = center.add(radial.scale(outer));
         float local = alpha * (i % 4 == 0 ? 0.86F : 0.52F);
         wrote |= ribbon(buffer, matrix, rel(a, cam), rel(b, cam), 0.014F, 0.003F, 255, 239, 166, local, 0.0F);
      }

      return wrote;
   }

   private static boolean nestedGlyph(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 center, Vec3 normal, double radius, double spin, float alpha) {
      ArthurHolyDisintegrationVfxClient.Basis basis = basis(normal);
      boolean wrote = false;
      int points = 8;

      for (int layer = 0; layer < 3; layer++) {
         double r = radius * (1.0 - layer * 0.24);
         double localSpin = spin * (layer % 2 == 0 ? 1.0 : -1.0) + layer * 0.31;
         Vec3 previous = null;
         Vec3 first = null;

         for (int i = 0; i < points; i++) {
            double angle = i * (float) (Math.PI * 2) / points + localSpin;
            Vec3 radial = basis.right.scale(Math.cos(angle)).add(basis.up.scale(Math.sin(angle)));
            Vec3 point = center.add(radial.scale(r));
            if (first == null) {
               first = point;
            }

            if (previous != null) {
               wrote |= ribbon(
                  buffer,
                  matrix,
                  rel(previous, cam),
                  rel(point, cam),
                  0.01F - layer * 0.002F,
                  0.01F - layer * 0.002F,
                  255,
                  layer == 2 ? 255 : 229,
                  layer == 2 ? 236 : 132,
                  alpha * (0.76F - layer * 0.12F),
                  alpha * (0.76F - layer * 0.12F)
               );
            }

            previous = point;
         }

         if (previous != null && first != null) {
            wrote |= ribbon(
               buffer,
               matrix,
               rel(previous, cam),
               rel(first, cam),
               0.01F - layer * 0.002F,
               0.01F - layer * 0.002F,
               255,
               layer == 2 ? 255 : 229,
               layer == 2 ? 236 : 132,
               alpha * (0.76F - layer * 0.12F),
               alpha * (0.76F - layer * 0.12F)
            );
         }
      }

      return wrote;
   }

   private static boolean segmentedRing(
      BufferBuilder buffer,
      Matrix4f matrix,
      Vec3 cam,
      Vec3 center,
      Vec3 normal,
      double radius,
      float width,
      int segments,
      double spin,
      int red,
      int green,
      int blue,
      float alpha,
      int gapEvery,
      int gapSize
   ) {
      if (!(alpha <= 0.001F) && !(radius <= 0.001) && !(width <= 0.001F)) {
         ArthurHolyDisintegrationVfxClient.Basis basis = basis(normal);
         boolean wrote = false;

         for (int i = 0; i < segments; i++) {
            if (gapEvery <= 0 || Math.floorMod(i, gapEvery) >= gapSize) {
               double a0 = i * (float) (Math.PI * 2) / segments + spin;
               double a1 = (i + 0.82) * (float) (Math.PI * 2) / segments + spin;
               Vec3 p0 = center.add(basis.right.scale(Math.cos(a0) * radius)).add(basis.up.scale(Math.sin(a0) * radius));
               Vec3 p1 = center.add(basis.right.scale(Math.cos(a1) * radius)).add(basis.up.scale(Math.sin(a1) * radius));
               float pulse = 0.82F + 0.18F * Mth.sin(i * 0.57F + (float)spin * 1.25F);
               wrote |= ribbon(buffer, matrix, rel(p0, cam), rel(p1, cam), width, width, red, green, blue, alpha * pulse, alpha * pulse);
            }
         }

         return wrote;
      } else {
         return false;
      }
   }

   private static boolean curvedRibbon(
      BufferBuilder buffer,
      Matrix4f matrix,
      Vec3 cam,
      Vec3 start,
      Vec3 control,
      Vec3 end,
      int segments,
      float width,
      float tipWidth,
      int red,
      int green,
      int blue,
      float alpha
   ) {
      if (alpha <= 0.001F) {
         return false;
      }

      boolean wrote = false;
      Vec3 previous = start;

      for (int i = 1; i <= segments; i++) {
         float t = (float)i / segments;
         Vec3 point = quadratic(start, control, end, t);
         float taper = 1.0F - t;
         float w0 = tipWidth + (width - tipWidth) * Math.max(0.0F, taper);
         float w1 = tipWidth + (width - tipWidth) * Math.max(0.0F, taper - 1.0F / segments);
         float a = alpha * Mth.sin(t * (float) Math.PI);
         wrote |= ribbon(buffer, matrix, rel(previous, cam), rel(point, cam), w0, w1, red, green, blue, a, a);
         previous = point;
      }

      return wrote;
   }

   private static Vec3 quadratic(Vec3 a, Vec3 b, Vec3 c, float t) {
      double u = 1.0 - t;
      return a.scale(u * u).add(b.scale(2.0 * u * t)).add(c.scale(t * t));
   }

   private static boolean ribbon(
      BufferBuilder buffer, Matrix4f matrix, Vec3 a, Vec3 b, float widthA, float widthB, int red, int green, int blue, float alphaA, float alphaB
   ) {
      if (alphaA <= 0.001F && alphaB <= 0.001F) {
         return false;
      }

      Vec3 dir = b.subtract(a);
      if (dir.lengthSqr() < 1.0E-8) {
         return false;
      }

      Vec3 midpoint = a.add(b).scale(0.5);
      Vec3 view = midpoint.scale(-1.0);
      if (view.lengthSqr() < 1.0E-8) {
         view = new Vec3(0.0, 0.0, 1.0);
      }

      Vec3 side = dir.cross(view);
      if (side.lengthSqr() < 1.0E-8) {
         side = dir.cross(new Vec3(0.0, 1.0, 0.0));
      }

      if (side.lengthSqr() < 1.0E-8) {
         side = new Vec3(1.0, 0.0, 0.0);
      }

      side = side.normalize();
      Vec3 a0 = a.add(side.scale(widthA));
      Vec3 a1 = a.subtract(side.scale(widthA));
      Vec3 b0 = b.add(side.scale(widthB));
      Vec3 b1 = b.subtract(side.scale(widthB));
      tri(buffer, matrix, a0, a1, b1, red, green, blue, alphaA, alphaA, alphaB);
      tri(buffer, matrix, a0, b1, b0, red, green, blue, alphaA, alphaB, alphaB);
      return true;
   }

   private static boolean ribbonFixed(
      BufferBuilder buffer, Matrix4f matrix, Vec3 a, Vec3 b, Vec3 side, float widthA, float widthB, int red, int green, int blue, float alphaA, float alphaB
   ) {
      if (alphaA <= 0.001F && alphaB <= 0.001F) {
         return false;
      } else if (!(a.distanceToSqr(b) < 1.0E-8) && !(side.lengthSqr() < 1.0E-8)) {
         side = side.normalize();
         Vec3 a0 = a.add(side.scale(widthA));
         Vec3 a1 = a.subtract(side.scale(widthA));
         Vec3 b0 = b.add(side.scale(widthB));
         Vec3 b1 = b.subtract(side.scale(widthB));
         tri(buffer, matrix, a0, a1, b1, red, green, blue, alphaA, alphaA, alphaB);
         tri(buffer, matrix, a0, b1, b0, red, green, blue, alphaA, alphaB, alphaB);
         return true;
      } else {
         return false;
      }
   }

   private static boolean glow(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float size, int red, int green, int blue, float alpha) {
      if (!(alpha <= 0.001F) && !(size <= 0.001F)) {
         ArthurHolyDisintegrationVfxClient.Axes axes = cameraAxes(center);
         Vec3 top = center.add(axes.up.scale(size));
         Vec3 right = center.add(axes.right.scale(size));
         Vec3 bottom = center.subtract(axes.up.scale(size));
         Vec3 left = center.subtract(axes.right.scale(size));
         tri(buffer, matrix, center, top, right, red, green, blue, alpha, 0.0F, 0.0F);
         tri(buffer, matrix, center, right, bottom, red, green, blue, alpha, 0.0F, 0.0F);
         tri(buffer, matrix, center, bottom, left, red, green, blue, alpha, 0.0F, 0.0F);
         tri(buffer, matrix, center, left, top, red, green, blue, alpha, 0.0F, 0.0F);
         return true;
      } else {
         return false;
      }
   }

   private static ArthurHolyDisintegrationVfxClient.Basis basis(Vec3 forward) {
      if (forward.lengthSqr() < 1.0E-8) {
         forward = new Vec3(0.0, 1.0, 0.0);
      }

      forward = forward.normalize();
      Vec3 right = stablePerpendicular(forward);
      Vec3 up = forward.cross(right);
      if (up.lengthSqr() < 1.0E-8) {
         up = new Vec3(0.0, 0.0, 1.0);
      }

      return new ArthurHolyDisintegrationVfxClient.Basis(forward, right.normalize(), up.normalize());
   }

   private static Vec3 stablePerpendicular(Vec3 direction) {
      if (direction.lengthSqr() < 1.0E-8) {
         return new Vec3(1.0, 0.0, 0.0);
      }

      direction = direction.normalize();
      Vec3 reference = Math.abs(direction.y) > 0.88 ? new Vec3(1.0, 0.0, 0.0) : new Vec3(0.0, 1.0, 0.0);
      Vec3 side = direction.cross(reference);
      if (side.lengthSqr() < 1.0E-8) {
         side = new Vec3(1.0, 0.0, 0.0);
      }

      return side.normalize();
   }

   private static ArthurHolyDisintegrationVfxClient.Axes cameraAxes(Vec3 center) {
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
      Vec3 up = view.cross(right);
      if (up.lengthSqr() < 1.0E-8) {
         up = new Vec3(0.0, 1.0, 0.0);
      }

      return new ArthurHolyDisintegrationVfxClient.Axes(right, up.normalize());
   }

   private static Vec3 rel(Vec3 point, Vec3 cam) {
      return point.subtract(cam);
   }

   private static void tri(
      BufferBuilder buffer, Matrix4f matrix, Vec3 a, Vec3 b, Vec3 c, int red, int green, int blue, float alphaA, float alphaB, float alphaC
   ) {
      vertex(buffer, matrix, a, red, green, blue, alphaA);
      vertex(buffer, matrix, b, red, green, blue, alphaB);
      vertex(buffer, matrix, c, red, green, blue, alphaC);
   }

   private static void vertex(BufferBuilder buffer, Matrix4f matrix, Vec3 position, int red, int green, int blue, float alpha) {
      if (Double.isFinite(position.x) && Double.isFinite(position.y) && Double.isFinite(position.z) && Float.isFinite(alpha)) {
         int packedAlpha = Mth.clamp((int)(alpha * 255.0F), 0, 255);
         if (packedAlpha > 0) {
            buffer.addVertex(matrix, (float)position.x, (float)position.y, (float)position.z)
               .setColor(Mth.clamp(red, 0, 255), Mth.clamp(green, 0, 255), Mth.clamp(blue, 0, 255), packedAlpha);
         }
      }
   }

   private static float smooth(float value) {
      value = Mth.clamp(value, 0.0F, 1.0F);
      return value * value * (3.0F - 2.0F * value);
   }

   private record Axes(Vec3 right, Vec3 up) {
   }

   private record Basis(Vec3 forward, Vec3 right, Vec3 up) {
   }

   private record Cast(ArthurAvalonVfxData payload, long created) {
      private float progress(long now) {
         return (float)(now - this.created) / (Math.max(1, this.payload.durationTicks()) * 50.0F);
      }
   }
}
