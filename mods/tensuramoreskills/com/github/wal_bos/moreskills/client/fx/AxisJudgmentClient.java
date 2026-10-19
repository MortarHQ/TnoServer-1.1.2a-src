package com.github.wal_bos.moreskills.client.fx;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent.Post;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent.Stage;
import org.joml.Matrix4f;

public final class AxisJudgmentClient {
   private static final Map<Integer, AxisJudgmentClient.ActiveAxis> ACTIVE = new ConcurrentHashMap<>();
   private static float time;

   private AxisJudgmentClient() {
   }

   public static void add(int bossId, int targetId, double x, double y, double z, float radius, int duration, int seed) {
      ACTIVE.put(bossId, new AxisJudgmentClient.ActiveAxis(bossId, targetId, new Vec3(x, y, z), Math.max(18.0F, radius), Math.max(156, duration + 38), seed));
   }

   public static void onClientTick(Post event) {
      Minecraft minecraft = Minecraft.getInstance();
      time += 0.16F;
      if (minecraft.level == null) {
         ACTIVE.clear();
      } else {
         Iterator<AxisJudgmentClient.ActiveAxis> iterator = ACTIVE.values().iterator();

         while (iterator.hasNext()) {
            AxisJudgmentClient.ActiveAxis axis = iterator.next();
            axis.age++;
            if (axis.age > axis.duration) {
               iterator.remove();
            }
         }
      }
   }

   public static void render(RenderLevelStageEvent event) {
      if (event.getStage() == Stage.AFTER_PARTICLES && !ACTIVE.isEmpty()) {
         Minecraft minecraft = Minecraft.getInstance();
         if (minecraft.level != null && minecraft.player != null) {
            Camera camera = minecraft.gameRenderer.getMainCamera();
            Vec3 cam = camera.getPosition();
            PoseStack poseStack = event.getPoseStack();
            Matrix4f matrix = poseStack.last().pose();
            float partialTick = event.getPartialTick().getGameTimeDeltaPartialTick(false);
            RenderSystem.setShader(GameRenderer::getPositionColorShader);
            RenderSystem.enableBlend();
            RenderSystem.blendFuncSeparate(770, 1, 1, 0);
            RenderSystem.disableCull();
            RenderSystem.enableDepthTest();
            RenderSystem.depthMask(false);

            for (AxisJudgmentClient.ActiveAxis axis : ACTIVE.values()) {
               renderAxis(matrix, cam, axis, partialTick);
            }

            RenderSystem.defaultBlendFunc();
            RenderSystem.depthMask(true);
            RenderSystem.enableCull();
            RenderSystem.disableBlend();
         }
      }
   }

   private static void renderAxis(Matrix4f matrix, Vec3 cam, AxisJudgmentClient.ActiveAxis axis, float partialTick) {
      float age = axis.age + partialTick;
      float appear = smooth(Mth.clamp(age / 18.0F, 0.0F, 1.0F));
      float fade = smooth(Mth.clamp((axis.duration - age) / 38.0F, 0.0F, 1.0F));
      float alpha = appear * fade;
      renderSealedDomain(matrix, cam, axis, age, alpha);
      renderArenaFoundation(matrix, cam, axis, age, alpha);
      renderHeavenLock(matrix, cam, axis, age, alpha);
      renderOrbitingVerdicts(matrix, cam, axis, age, alpha);
      renderDodgeWarnings(matrix, cam, axis, age, alpha);
      renderCrossWarnings(matrix, cam, axis, age, alpha);
      renderFinalWarningAndBeam(matrix, cam, axis, age, alpha);
      renderAftermath(matrix, cam, axis, age, alpha);
   }

   private static void renderSealedDomain(Matrix4f matrix, Vec3 cam, AxisJudgmentClient.ActiveAxis axis, float age, float alpha) {
      BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
      boolean drew = false;
      Vec3 center = axis.center;
      double radius = axis.radius;
      double ceiling = 36.0;
      float build = smooth(Mth.clamp(age / 22.0F, 0.0F, 1.0F));
      float pulse = 0.74F + 0.26F * Mth.sin(time * 3.6F);
      drew |= addFlatDisk(buffer, matrix, cam, center.add(0.0, -0.26, 0.0), radius, 144, 1.0F, 0.15F, 0.02F, alpha * build * 0.12F);
      drew |= addFlatRing(buffer, matrix, cam, center.add(0.0, -0.2, 0.0), radius, 160, 0.7, 1.0F, 0.38F, 0.04F, alpha * build * 0.95F);
      drew |= addFlatRing(buffer, matrix, cam, center.add(0.0, -0.12, 0.0), radius * 0.78, 132, 0.34, 1.0F, 0.85F, 0.2F, alpha * build * 0.62F);
      drew |= addFlatRing(buffer, matrix, cam, center.add(0.0, -0.04, 0.0), radius * 0.45, 108, 0.2, 1.0F, 1.0F, 0.68F, alpha * build * 0.46F);
      drew |= addFlatDisk(buffer, matrix, cam, center.add(0.0, ceiling, 0.0), radius, 144, 1.0F, 0.2F, 0.02F, alpha * build * 0.08F);
      drew |= addFlatRing(buffer, matrix, cam, center.add(0.0, ceiling, 0.0), radius, 160, 0.58, 1.0F, 0.48F, 0.04F, alpha * build * 0.76F);
      drew |= addFlatRing(buffer, matrix, cam, center.add(0.0, ceiling - 0.12, 0.0), radius * 0.62, 132, 0.28, 1.0F, 1.0F, 0.6F, alpha * build * 0.55F);

      for (int i = 0; i < 72; i++) {
         double angle = i / 72.0 * Math.PI * 2.0 + time * 0.045;
         double wobble = Math.sin(time * 1.5F + i * 0.37) * 0.45;
         Vec3 low = center.add(Math.cos(angle) * radius, -0.2, Math.sin(angle) * radius);
         Vec3 high = center.add(Math.cos(angle + wobble * 0.01) * radius, ceiling, Math.sin(angle + wobble * 0.01) * radius);
         float strong = i % 6 == 0 ? 1.0F : 0.0F;
         drew |= addWorldRibbon(
            buffer, matrix, cam, low, high, strong > 0.5F ? 0.155F : 0.065F, 1.0F, 0.28F, 0.025F, alpha * build * (0.18F + strong * 0.24F) * pulse
         );
         drew |= addWorldRibbon(
            buffer,
            matrix,
            cam,
            low.add(0.0, 0.08, 0.0),
            high.add(0.0, -0.08, 0.0),
            strong > 0.5F ? 0.042F : 0.02F,
            1.0F,
            1.0F,
            0.68F,
            alpha * build * (0.3F + strong * 0.42F)
         );
      }

      for (int band = 0; band < 7; band++) {
         double y = center.y + 3.0 + band * 4.8;
         double spin = time * (0.18 + band * 0.025) * (band % 2 == 0 ? 1.0 : -1.0);

         for (int i = 0; i < 48; i++) {
            if (i % 4 != 1) {
               double a0 = i / 48.0 * Math.PI * 2.0 + spin;
               double a1 = (i + 0.74) / 48.0 * Math.PI * 2.0 + spin;
               Vec3 p0 = new Vec3(center.x + Math.cos(a0) * radius, y, center.z + Math.sin(a0) * radius);
               Vec3 p1 = new Vec3(center.x + Math.cos(a1) * radius, y + Math.sin(time * 1.2F + i) * 0.35, center.z + Math.sin(a1) * radius);
               drew |= addWorldRibbon(buffer, matrix, cam, p0, p1, 0.105F, 1.0F, 0.42F, 0.04F, alpha * build * 0.34F);
               drew |= addWorldRibbon(buffer, matrix, cam, p0.add(0.0, 0.06, 0.0), p1.add(0.0, 0.06, 0.0), 0.026F, 1.0F, 1.0F, 0.72F, alpha * build * 0.68F);
            }
         }
      }

      for (int i = 0; i < 16; i++) {
         double angle = i / 16.0 * Math.PI * 2.0 + time * 0.24;
         Vec3 floor = center.add(Math.cos(angle) * radius * 0.92, 0.12, Math.sin(angle) * radius * 0.92);
         Vec3 roof = center.add(Math.cos(angle + Math.PI) * radius * 0.92, ceiling - 0.12, Math.sin(angle + Math.PI) * radius * 0.92);
         drew |= addWorldRibbon(buffer, matrix, cam, floor, roof, 0.08F, 1.0F, 0.54F, 0.05F, alpha * build * 0.2F);
      }

      if (drew) {
         BufferUploader.drawWithShader(buffer.buildOrThrow());
      }
   }

   private static void renderArenaFoundation(Matrix4f matrix, Vec3 cam, AxisJudgmentClient.ActiveAxis axis, float age, float alpha) {
      BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
      boolean drew = false;
      Vec3 center = axis.center;
      double radius = axis.radius;
      float pulse = 0.72F + 0.28F * Mth.sin(time * 3.4F);
      float build = smooth(Mth.clamp(age / 24.0F, 0.0F, 1.0F));
      drew |= addFlatRing(buffer, matrix, cam, center.add(0.0, 0.08, 0.0), radius, 144, 0.42, 1.0F, 0.42F, 0.04F, alpha * build * 0.72F);
      drew |= addFlatRing(buffer, matrix, cam, center.add(0.0, 0.12, 0.0), radius * 0.72, 120, 0.28, 1.0F, 0.84F, 0.18F, alpha * build * 0.48F);
      drew |= addFlatRing(buffer, matrix, cam, center.add(0.0, 0.16, 0.0), radius * 0.38, 96, 0.18, 1.0F, 1.0F, 0.62F, alpha * build * 0.42F * pulse);

      for (int i = 0; i < 12; i++) {
         double angle = i / 12.0 * Math.PI * 2.0 + time * 0.22;
         Vec3 a = center.add(Math.cos(angle) * radius * 0.18, 0.18, Math.sin(angle) * radius * 0.18);
         Vec3 b = center.add(Math.cos(angle) * radius * 0.96, 0.18, Math.sin(angle) * radius * 0.96);
         drew |= addWorldRibbon(buffer, matrix, cam, a, b, 0.105F, 1.0F, 0.4F, 0.04F, alpha * build * 0.24F);
         drew |= addWorldRibbon(buffer, matrix, cam, a.add(0.0, 0.04, 0.0), b.add(0.0, 0.04, 0.0), 0.035F, 1.0F, 0.95F, 0.44F, alpha * build * 0.56F);
      }

      for (int i = 0; i < 8; i++) {
         double angle = i / 8.0 * Math.PI * 2.0 - time * 0.16;
         double inner = radius * 0.33;
         double outer = radius * 0.86;
         Vec3 a = center.add(Math.cos(angle) * inner, 0.24, Math.sin(angle) * inner);
         Vec3 b = center.add(Math.cos(angle + 0.62) * outer, 0.24, Math.sin(angle + 0.62) * outer);
         drew |= addWorldRibbon(buffer, matrix, cam, a, b, 0.07F, 1.0F, 0.72F, 0.12F, alpha * build * 0.34F);
      }

      if (drew) {
         BufferUploader.drawWithShader(buffer.buildOrThrow());
      }
   }

   private static void renderHeavenLock(Matrix4f matrix, Vec3 cam, AxisJudgmentClient.ActiveAxis axis, float age, float alpha) {
      BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
      boolean drew = false;
      Vec3 center = axis.center;
      double radius = axis.radius;
      float build = smooth(Mth.clamp((age - 4.0F) / 24.0F, 0.0F, 1.0F));
      float pulse = 0.78F + 0.22F * Mth.sin(time * 2.8F);

      for (int i = 0; i < 32; i++) {
         double angle = i / 32.0 * Math.PI * 2.0 + time * 0.12;
         double bladeHeight = 16.0 + 6.0 * hash(axis.seed * 0.03F + i * 7.1F) + Math.sin(time * 1.8F + i) * 1.3;
         float big = i % 4 == 0 ? 1.0F : 0.0F;
         float width = big > 0.5F ? 0.24F : 0.12F;
         Vec3 base = center.add(Math.cos(angle) * radius, 0.18, Math.sin(angle) * radius);
         drew |= addVerticalBlade(buffer, matrix, cam, base, bladeHeight, width, 1.0F, 0.34F, 0.035F, alpha * build * (0.28F + big * 0.28F) * pulse);
         drew |= addVerticalBlade(
            buffer, matrix, cam, base.add(0.0, 0.08, 0.0), bladeHeight * 0.82, width * 0.32F, 1.0F, 1.0F, 0.7F, alpha * build * (0.44F + big * 0.42F)
         );
      }

      for (int layer = 0; layer < 4; layer++) {
         double y = center.y + 5.2 + layer * 4.8;
         double rr = radius * (1.0 - layer * 0.075);
         float a = alpha * build * (0.16F + layer * 0.05F);
         drew |= addFlatRing(buffer, matrix, cam, new Vec3(center.x, y, center.z), rr, 128, 0.18, 1.0F, 0.5F, 0.05F, a);
         drew |= addFlatRing(buffer, matrix, cam, new Vec3(center.x, y + 0.08, center.z), rr * 0.62, 96, 0.1, 1.0F, 1.0F, 0.68F, a * 0.82F);
      }

      for (int i = 0; i < 10; i++) {
         double angle = i / 10.0 * Math.PI * 2.0 + time * 0.32;
         Vec3 a = center.add(Math.cos(angle) * radius, 9.0 + Math.sin(time + i) * 1.4, Math.sin(angle) * radius);
         Vec3 b = center.add(Math.cos(angle + Math.PI) * radius, 12.0 + Math.cos(time + i) * 1.4, Math.sin(angle + Math.PI) * radius);
         drew |= addWorldRibbon(buffer, matrix, cam, a, b, 0.13F, 1.0F, 0.58F, 0.07F, alpha * build * 0.2F);
         drew |= addWorldRibbon(buffer, matrix, cam, a.add(0.0, 0.08, 0.0), b.add(0.0, 0.08, 0.0), 0.035F, 1.0F, 1.0F, 0.72F, alpha * build * 0.44F);
      }

      if (drew) {
         BufferUploader.drawWithShader(buffer.buildOrThrow());
      }
   }

   private static void renderOrbitingVerdicts(Matrix4f matrix, Vec3 cam, AxisJudgmentClient.ActiveAxis axis, float age, float alpha) {
      BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
      boolean drew = false;
      Vec3 center = axis.center;
      float build = smooth(Mth.clamp((age - 10.0F) / 28.0F, 0.0F, 1.0F));

      for (int layer = 0; layer < 5; layer++) {
         int count = 8 + layer * 3;
         double y = center.y + 6.5 + layer * 4.2 + Math.sin(time * 0.75F + layer) * 0.9;
         double radius = axis.radius * (0.26 + layer * 0.13);
         double spin = time * (0.45 + layer * 0.1) * (layer % 2 == 0 ? 1.0 : -1.0);

         for (int i = 0; i < count; i++) {
            float seed = hash(axis.seed * 0.011F + i * 17.0F + layer * 37.0F);
            double angle = (double)i / count * Math.PI * 2.0 + spin + seed * 0.25;
            Vec3 pos = center.add(Math.cos(angle) * radius, y - center.y, Math.sin(angle) * radius);
            double size = 1.0 + seed * 1.5 + build * 0.65;
            drew |= addDiamond(buffer, matrix, cam, pos, size, 1.0F, 0.48F, 0.05F, alpha * build * 0.3F);
            drew |= addDiamond(buffer, matrix, cam, pos.add(0.0, 0.035, 0.0), size * 0.43, 1.0F, 1.0F, 0.7F, alpha * build * 0.58F);
         }
      }

      for (int i = 0; i < 20; i++) {
         float seed = hash(axis.seed * 0.041F + i * 31.0F);
         double angle = i / 20.0 * Math.PI * 2.0 - time * 0.62;
         double radius = axis.radius * (0.2 + seed * 0.74);
         double y = 7.0 + hash(seed * 43.0F) * 25.0;
         Vec3 pos = center.add(Math.cos(angle) * radius, y, Math.sin(angle) * radius);
         Vec3 down = pos.add(0.0, -3.0 - seed * 4.0, 0.0);
         drew |= addWorldRibbon(buffer, matrix, cam, pos, down, 0.08F, 1.0F, 0.82F, 0.16F, alpha * build * 0.35F);
         drew |= addWorldRibbon(buffer, matrix, cam, pos, down, 0.025F, 1.0F, 1.0F, 0.78F, alpha * build * 0.6F);
      }

      if (drew) {
         BufferUploader.drawWithShader(buffer.buildOrThrow());
      }
   }

   private static void renderDodgeWarnings(Matrix4f matrix, Vec3 cam, AxisJudgmentClient.ActiveAxis axis, float age, float alpha) {
      BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
      boolean drew = false;

      for (int i = 0; i < 5; i++) {
         float warnStart = 14.0F + i * 14.0F;
         float strikeTime = 36.0F + i * 14.0F;
         float end = strikeTime + 11.0F;
         if (!(age < warnStart) && !(age > end)) {
            Vec3 pos = getDodgeBeam(axis, i);
            float warn = age < strikeTime ? smooth(Mth.clamp((age - warnStart) / (strikeTime - warnStart), 0.0F, 1.0F)) : 0.0F;
            float hit = age >= strikeTime ? Mth.sin(Mth.clamp((age - strikeTime) / 11.0F, 0.0F, 1.0F) * (float) Math.PI) : 0.0F;
            float heartbeat = 0.78F + 0.22F * Mth.sin(time * 9.0F + i);
            drew |= addFlatRing(buffer, matrix, cam, pos.add(0.0, 0.09, 0.0), 4.15 + warn * 1.3, 112, 0.3, 1.0F, 0.12F, 0.025F, alpha * warn * heartbeat);
            drew |= addFlatRing(buffer, matrix, cam, pos.add(0.0, 0.14, 0.0), 2.0 + warn * 2.2, 88, 0.18, 1.0F, 0.95F, 0.5F, alpha * warn * 0.96F);
            drew |= addFlatRing(buffer, matrix, cam, pos.add(0.0, 0.19, 0.0), 0.9 + warn * 3.6, 80, 0.1, 1.0F, 1.0F, 0.82F, alpha * warn * 0.64F);

            for (int s = 0; s < 6; s++) {
               double angle = s / 6.0 * Math.PI * 2.0 + time * 0.4;
               Vec3 a = pos.add(Math.cos(angle) * (5.8 - warn * 1.8), 0.35 + warn * 2.4, Math.sin(angle) * (5.8 - warn * 1.8));
               Vec3 b = pos.add(Math.cos(angle) * 1.1, 8.0 + warn * 11.0, Math.sin(angle) * 1.1);
               drew |= addWorldRibbon(buffer, matrix, cam, a, b, 0.05F, 1.0F, 0.72F, 0.16F, alpha * warn * 0.42F);
            }

            drew |= addColumn(buffer, matrix, cam, pos, 34.0 + hit * 18.0, 4.0 + hit * 1.1, 1.0F, 0.25F, 0.025F, alpha * hit * 0.34F);
            drew |= addColumn(buffer, matrix, cam, pos, 42.0 + hit * 20.0, 1.25, 1.0F, 0.84F, 0.2F, alpha * hit * 0.72F);
            drew |= addColumn(buffer, matrix, cam, pos, 48.0 + hit * 24.0, 0.32, 1.0F, 1.0F, 0.82F, alpha * hit);
            drew |= addFlatRing(buffer, matrix, cam, pos.add(0.0, 0.24, 0.0), 3.0 + hit * 7.5, 96, 0.3, 1.0F, 0.75F, 0.18F, alpha * hit * 0.62F);
         }
      }

      if (drew) {
         BufferUploader.drawWithShader(buffer.buildOrThrow());
      }
   }

   private static void renderCrossWarnings(Matrix4f matrix, Vec3 cam, AxisJudgmentClient.ActiveAxis axis, float age, float alpha) {
      BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
      boolean drew = false;

      for (int pass = 0; pass < 2; pass++) {
         float warnStart = pass == 0 ? 88.0F : 100.0F;
         float strike = pass == 0 ? 104.0F : 116.0F;
         float end = strike + 10.0F;
         if (!(age < warnStart) && !(age > end)) {
            float warn = age < strike ? smooth(Mth.clamp((age - warnStart) / (strike - warnStart), 0.0F, 1.0F)) : 0.0F;
            float hit = age >= strike ? Mth.sin(Mth.clamp((age - strike) / 10.0F, 0.0F, 1.0F) * (float) Math.PI) : 0.0F;
            double angle = pass * Math.PI * 0.5 + axis.seed * 1.0E-4;
            Vec3 axisA = new Vec3(Math.cos(angle), 0.0, Math.sin(angle)).normalize();
            Vec3 axisB = new Vec3(-axisA.z, 0.0, axisA.x).normalize();
            Vec3 center = axis.center.add(0.0, 1.2 + pass * 0.8, 0.0);
            drew |= addWorldRibbon(
               buffer,
               matrix,
               cam,
               center.subtract(axisA.scale(axis.radius)),
               center.add(axisA.scale(axis.radius)),
               0.44F + warn * 0.44F,
               1.0F,
               0.12F,
               0.025F,
               alpha * warn * 0.44F
            );
            drew |= addWorldRibbon(
               buffer,
               matrix,
               cam,
               center.subtract(axisA.scale(axis.radius)),
               center.add(axisA.scale(axis.radius)),
               0.12F + warn * 0.1F,
               1.0F,
               1.0F,
               0.62F,
               alpha * warn * 0.84F
            );
            drew |= addWorldRibbon(
               buffer,
               matrix,
               cam,
               center.subtract(axisB.scale(axis.radius)),
               center.add(axisB.scale(axis.radius)),
               0.44F + warn * 0.44F,
               1.0F,
               0.12F,
               0.025F,
               alpha * warn * 0.44F
            );
            drew |= addWorldRibbon(
               buffer,
               matrix,
               cam,
               center.subtract(axisB.scale(axis.radius)),
               center.add(axisB.scale(axis.radius)),
               0.12F + warn * 0.1F,
               1.0F,
               1.0F,
               0.62F,
               alpha * warn * 0.84F
            );
            drew |= addWorldRibbon(
               buffer,
               matrix,
               cam,
               center.subtract(axisA.scale(axis.radius)),
               center.add(axisA.scale(axis.radius)),
               0.95F,
               1.0F,
               0.18F,
               0.02F,
               alpha * hit * 0.34F
            );
            drew |= addWorldRibbon(
               buffer,
               matrix,
               cam,
               center.subtract(axisA.scale(axis.radius)),
               center.add(axisA.scale(axis.radius)),
               0.22F,
               1.0F,
               0.9F,
               0.28F,
               alpha * hit * 0.88F
            );
            drew |= addWorldRibbon(
               buffer,
               matrix,
               cam,
               center.subtract(axisB.scale(axis.radius)),
               center.add(axisB.scale(axis.radius)),
               0.95F,
               1.0F,
               0.18F,
               0.02F,
               alpha * hit * 0.3F
            );
            drew |= addWorldRibbon(
               buffer,
               matrix,
               cam,
               center.subtract(axisB.scale(axis.radius)),
               center.add(axisB.scale(axis.radius)),
               0.22F,
               1.0F,
               0.9F,
               0.28F,
               alpha * hit * 0.82F
            );
            drew |= addFlatRing(
               buffer, matrix, cam, axis.center.add(0.0, 0.22, 0.0), axis.radius * (0.22 + hit * 0.62), 128, 0.3, 1.0F, 0.52F, 0.08F, alpha * hit * 0.48F
            );
         }
      }

      if (drew) {
         BufferUploader.drawWithShader(buffer.buildOrThrow());
      }
   }

   private static void renderFinalWarningAndBeam(Matrix4f matrix, Vec3 cam, AxisJudgmentClient.ActiveAxis axis, float age, float alpha) {
      if (!(age < 104.0F) && !(age > 146.0F)) {
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         boolean drew = false;
         Vec3 center = getFinalDodgeBeam(axis);
         float warn = age < 130.0F ? smooth(Mth.clamp((age - 104.0F) / 26.0F, 0.0F, 1.0F)) : 0.0F;
         float hit = age >= 130.0F ? Mth.sin(Mth.clamp((age - 130.0F) / 12.0F, 0.0F, 1.0F) * (float) Math.PI) : 0.0F;
         float quake = hit * (0.85F + 0.15F * Mth.sin(time * 22.0F));
         drew |= addFlatRing(buffer, matrix, cam, center.add(0.0, 0.12, 0.0), 8.25 + warn * 2.4, 152, 0.42, 1.0F, 0.08F, 0.015F, alpha * warn * 0.9F);
         drew |= addFlatRing(buffer, matrix, cam, center.add(0.0, 0.18, 0.0), 4.4 + warn * 3.4, 124, 0.25, 1.0F, 0.9F, 0.38F, alpha * warn);
         drew |= addFlatRing(buffer, matrix, cam, center.add(0.0, 0.26, 0.0), 1.2 + warn * 6.5, 112, 0.12, 1.0F, 1.0F, 0.8F, alpha * warn * 0.7F);

         for (int i = 0; i < 12; i++) {
            double angle = i / 12.0 * Math.PI * 2.0 + time * 0.55;
            Vec3 a = center.add(Math.cos(angle) * (11.0 - warn * 3.0), 0.4 + warn * 3.0, Math.sin(angle) * (11.0 - warn * 3.0));
            Vec3 b = center.add(Math.cos(angle) * 1.5, 14.0 + warn * 18.0, Math.sin(angle) * 1.5);
            drew |= addWorldRibbon(buffer, matrix, cam, a, b, 0.075F, 1.0F, 0.52F, 0.05F, alpha * warn * 0.4F);
            drew |= addWorldRibbon(buffer, matrix, cam, a.add(0.0, 0.06, 0.0), b.add(0.0, 0.06, 0.0), 0.025F, 1.0F, 1.0F, 0.7F, alpha * warn * 0.7F);
         }

         drew |= addColumn(buffer, matrix, cam, center, 66.0 + quake * 18.0, 8.25, 1.0F, 0.12F, 0.015F, alpha * quake * 0.28F);
         drew |= addColumn(buffer, matrix, cam, center, 78.0 + quake * 22.0, 3.2, 1.0F, 0.62F, 0.1F, alpha * quake * 0.68F);
         drew |= addColumn(buffer, matrix, cam, center, 92.0 + quake * 26.0, 0.82, 1.0F, 1.0F, 0.82F, alpha * quake);
         drew |= addFlatRing(buffer, matrix, cam, center.add(0.0, 0.32, 0.0), 4.0 + quake * 20.0, 144, 0.46, 1.0F, 0.68F, 0.12F, alpha * quake * 0.72F);
         drew |= addFlatRing(buffer, matrix, cam, center.add(0.0, 0.42, 0.0), 2.0 + quake * 12.0, 124, 0.22, 1.0F, 1.0F, 0.7F, alpha * quake * 0.9F);

         for (int s = 0; s < 7; s++) {
            Vec3 previous = null;

            for (int i = 0; i <= 48; i++) {
               float p = i / 48.0F;
               double angle = p * Math.PI * 8.0 + time * 3.8 + s * Math.PI * 2.0 / 7.0;
               double radius = axis.radius * (0.08 + p * 0.18) * (1.0 - quake * 0.42);
               Vec3 point = center.add(Math.cos(angle) * radius, p * 60.0, Math.sin(angle) * radius);
               if (previous != null) {
                  drew |= addWorldRibbon(buffer, matrix, cam, previous, point, 0.17F, 1.0F, 0.46F, 0.04F, alpha * quake * (1.0F - p * 0.72F));
                  drew |= addWorldRibbon(buffer, matrix, cam, previous, point, 0.042F, 1.0F, 1.0F, 0.76F, alpha * quake * (1.0F - p * 0.55F));
               }

               previous = point;
            }
         }

         if (drew) {
            BufferUploader.drawWithShader(buffer.buildOrThrow());
         }
      }
   }

   private static void renderAftermath(Matrix4f matrix, Vec3 cam, AxisJudgmentClient.ActiveAxis axis, float age, float alpha) {
      if (!(age < 142.0F)) {
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         boolean drew = false;
         float local = smooth(Mth.clamp((age - 142.0F) / 32.0F, 0.0F, 1.0F));
         float a = alpha * (1.0F - local);
         Vec3 center = axis.center;
         drew |= addFlatRing(buffer, matrix, cam, center.add(0.0, 0.16, 0.0), axis.radius * (0.18 + local * 0.96), 160, 0.38, 1.0F, 0.55F, 0.08F, a * 0.7F);
         drew |= addFlatRing(buffer, matrix, cam, center.add(0.0, 0.24, 0.0), axis.radius * (0.08 + local * 0.58), 128, 0.18, 1.0F, 1.0F, 0.7F, a * 0.8F);

         for (int i = 0; i < 18; i++) {
            double angle = i / 18.0 * Math.PI * 2.0 + time * 0.4;
            Vec3 start = center.add(Math.cos(angle) * axis.radius * 0.18, 2.0 + local * 4.0, Math.sin(angle) * axis.radius * 0.18);
            Vec3 end = center.add(
               Math.cos(angle) * axis.radius * (0.45 + local * 0.58), 1.0 + local * 8.0, Math.sin(angle) * axis.radius * (0.45 + local * 0.58)
            );
            drew |= addWorldRibbon(buffer, matrix, cam, start, end, 0.075F, 1.0F, 0.72F, 0.16F, a * 0.38F);
            drew |= addWorldRibbon(buffer, matrix, cam, start, end, 0.022F, 1.0F, 1.0F, 0.74F, a * 0.62F);
         }

         if (drew) {
            BufferUploader.drawWithShader(buffer.buildOrThrow());
         }
      }
   }

   private static boolean addFlatDisk(
      BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 center, double radius, int segments, float r, float g, float b, float alpha
   ) {
      if (!(alpha <= 0.001F) && !(radius <= 0.001)) {
         boolean drew = false;

         for (int i = 0; i < segments; i++) {
            double a0 = (double)i / segments * Math.PI * 2.0;
            double a1 = (i + 1.0) / segments * Math.PI * 2.0;
            Vec3 p0 = center;
            Vec3 p1 = new Vec3(center.x + Math.cos(a0) * radius, center.y, center.z + Math.sin(a0) * radius);
            Vec3 p2 = new Vec3(center.x + Math.cos(a1) * radius, center.y, center.z + Math.sin(a1) * radius);
            addVertex(buffer, matrix, cam, p0, r, g, b, alpha * 0.2F);
            addVertex(buffer, matrix, cam, p1, r, g, b, alpha);
            addVertex(buffer, matrix, cam, p2, r, g, b, alpha);
            addVertex(buffer, matrix, cam, p0, r, g, b, alpha * 0.2F);
            drew = true;
         }

         return drew;
      } else {
         return false;
      }
   }

   private static boolean addFlatRing(
      BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 center, double radius, int segments, double width, float r, float g, float b, float alpha
   ) {
      if (!(alpha <= 0.001F) && !(radius <= 0.001) && !(width <= 0.001)) {
         boolean drew = false;
         double inner = Math.max(0.01, radius - width);
         double outer = radius + width;
         double spin = time * 0.24 + radius * 0.025;

         for (int i = 0; i < segments; i++) {
            if (i % 7 != 3) {
               double a0 = (double)i / segments * Math.PI * 2.0 + spin;
               double a1 = (i + 0.84) / segments * Math.PI * 2.0 + spin;
               Vec3 p0 = new Vec3(center.x + Math.cos(a0) * inner, center.y, center.z + Math.sin(a0) * inner);
               Vec3 p1 = new Vec3(center.x + Math.cos(a1) * inner, center.y, center.z + Math.sin(a1) * inner);
               Vec3 p2 = new Vec3(center.x + Math.cos(a1) * outer, center.y, center.z + Math.sin(a1) * outer);
               Vec3 p3 = new Vec3(center.x + Math.cos(a0) * outer, center.y, center.z + Math.sin(a0) * outer);
               addVertex(buffer, matrix, cam, p0, r, g, b, alpha * 0.66F);
               addVertex(buffer, matrix, cam, p1, r, g, b, alpha);
               addVertex(buffer, matrix, cam, p2, r, g, b, alpha);
               addVertex(buffer, matrix, cam, p3, r, g, b, alpha * 0.66F);
               drew = true;
            }
         }

         return drew;
      } else {
         return false;
      }
   }

   private static boolean addColumn(
      BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 center, double height, double radius, float r, float g, float b, float alpha
   ) {
      if (!(alpha <= 0.001F) && !(radius <= 0.001) && !(height <= 0.001)) {
         boolean drew = false;
         int segments = 40;
         int cuts = 8;
         double spin = time * 1.1 + radius * 0.13;

         for (int y = 0; y < cuts; y++) {
            double y0 = (double)y / cuts * height;
            double y1 = (double)(y + 1) / cuts * height;
            float fade0 = 1.0F - (float)y / cuts * 0.46F;
            float fade1 = 1.0F - (float)(y + 1) / cuts * 0.46F;

            for (int i = 0; i < segments; i++) {
               if (i % 4 != 1) {
                  double a0 = (double)i / segments * Math.PI * 2.0 + spin + y * 0.12;
                  double a1 = (i + 0.86) / segments * Math.PI * 2.0 + spin + y * 0.12;
                  Vec3 p0 = center.add(Math.cos(a0) * radius, y0, Math.sin(a0) * radius);
                  Vec3 p1 = center.add(Math.cos(a1) * radius, y0, Math.sin(a1) * radius);
                  Vec3 p2 = center.add(Math.cos(a1) * radius, y1, Math.sin(a1) * radius);
                  Vec3 p3 = center.add(Math.cos(a0) * radius, y1, Math.sin(a0) * radius);
                  addVertex(buffer, matrix, cam, p0, r, g, b, alpha * fade0);
                  addVertex(buffer, matrix, cam, p1, r, g, b, alpha * 0.78F * fade0);
                  addVertex(buffer, matrix, cam, p2, r, g, b, alpha * 0.35F * fade1);
                  addVertex(buffer, matrix, cam, p3, r, g, b, alpha * 0.52F * fade1);
                  drew = true;
               }
            }
         }

         return drew;
      } else {
         return false;
      }
   }

   private static boolean addVerticalBlade(
      BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 base, double height, float width, float r, float g, float b, float alpha
   ) {
      if (!(alpha <= 0.001F) && !(width <= 0.001F) && !(height <= 0.001)) {
         Vec3 radial = new Vec3(base.x, 0.0, base.z);
         if (radial.lengthSqr() < 1.0E-6) {
            radial = new Vec3(1.0, 0.0, 0.0);
         }

         Vec3 tangent = new Vec3(-radial.z, 0.0, radial.x);
         if (tangent.lengthSqr() < 1.0E-6) {
            tangent = new Vec3(0.0, 0.0, 1.0);
         }

         tangent = tangent.normalize().scale(width);
         Vec3 p0 = base.subtract(tangent);
         Vec3 p1 = base.add(tangent);
         Vec3 p2 = base.add(tangent.scale(0.28)).add(0.0, height, 0.0);
         Vec3 p3 = base.subtract(tangent.scale(0.28)).add(0.0, height, 0.0);
         addVertex(buffer, matrix, cam, p0, r, g, b, alpha);
         addVertex(buffer, matrix, cam, p1, r, g, b, alpha);
         addVertex(buffer, matrix, cam, p2, r, g, b, alpha * 0.18F);
         addVertex(buffer, matrix, cam, p3, r, g, b, alpha * 0.18F);
         return true;
      } else {
         return false;
      }
   }

   private static boolean addDiamond(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 center, double size, float r, float g, float b, float alpha) {
      if (!(alpha <= 0.001F) && !(size <= 0.001)) {
         double s = size;
         Vec3 top = center.add(0.0, s, 0.0);
         Vec3 bottom = center.add(0.0, -s, 0.0);
         Vec3 east = center.add(s * 0.55, 0.0, 0.0);
         Vec3 west = center.add(-s * 0.55, 0.0, 0.0);
         Vec3 north = center.add(0.0, 0.0, -s * 0.55);
         Vec3 south = center.add(0.0, 0.0, s * 0.55);
         addVertex(buffer, matrix, cam, top, r, g, b, alpha);
         addVertex(buffer, matrix, cam, east, r, g, b, alpha * 0.64F);
         addVertex(buffer, matrix, cam, bottom, r, g, b, alpha * 0.34F);
         addVertex(buffer, matrix, cam, west, r, g, b, alpha * 0.64F);
         addVertex(buffer, matrix, cam, top, r, g, b, alpha);
         addVertex(buffer, matrix, cam, north, r, g, b, alpha * 0.64F);
         addVertex(buffer, matrix, cam, bottom, r, g, b, alpha * 0.34F);
         addVertex(buffer, matrix, cam, south, r, g, b, alpha * 0.64F);
         return true;
      } else {
         return false;
      }
   }

   private static boolean addWorldRibbon(
      BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 a, Vec3 b, float width, float r, float g, float blue, float alpha
   ) {
      if (!(alpha <= 0.001F) && !(width <= 0.001F)) {
         Vec3 dir = b.subtract(a);
         if (dir.lengthSqr() < 4.0E-7) {
            return false;
         }

         Vec3 side = dir.normalize().cross(new Vec3(0.0, 1.0, 0.0));
         if (side.lengthSqr() < 1.0E-6) {
            Vec3 mid = a.add(b).scale(0.5);
            Vec3 toCam = cam.subtract(mid);
            if (toCam.lengthSqr() < 1.0E-6) {
               side = new Vec3(1.0, 0.0, 0.0);
            } else {
               side = dir.normalize().cross(toCam.normalize());
            }
         }

         if (side.lengthSqr() < 1.0E-6) {
            side = new Vec3(1.0, 0.0, 0.0);
         }

         side = side.normalize().scale(width);
         Vec3 a0 = a.subtract(side);
         Vec3 a1 = a.add(side);
         Vec3 b1 = b.add(side);
         Vec3 b0 = b.subtract(side);
         addVertex(buffer, matrix, cam, a0, r, g, blue, alpha);
         addVertex(buffer, matrix, cam, a1, r, g, blue, alpha);
         addVertex(buffer, matrix, cam, b1, r, g, blue, alpha * 0.62F);
         addVertex(buffer, matrix, cam, b0, r, g, blue, alpha * 0.62F);
         return true;
      } else {
         return false;
      }
   }

   private static void addVertex(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 p, float r, float g, float b, float a) {
      buffer.addVertex(matrix, (float)(p.x - cam.x), (float)(p.y - cam.y), (float)(p.z - cam.z)).setColor(r, g, b, a);
   }

   private static Vec3 getDodgeBeam(AxisJudgmentClient.ActiveAxis axis, int index) {
      double[] radii = new double[]{0.0, 0.48, 0.72, 0.55, 0.82};
      double[] angles = new double[]{0.0, 0.2, 2.35, 4.05, 5.35};
      int safe = Math.max(0, Math.min(index, radii.length - 1));
      double radius = axis.radius * radii[safe];
      double angle = angles[safe] + axis.seed * 1.0E-4;
      return axis.center.add(Math.cos(angle) * radius, 0.0, Math.sin(angle) * radius);
   }

   private static Vec3 getFinalDodgeBeam(AxisJudgmentClient.ActiveAxis axis) {
      double angle = 1.1 + axis.seed * 1.3E-4;
      double radius = axis.radius * 0.44;
      return axis.center.add(Math.cos(angle) * radius, 0.0, Math.sin(angle) * radius);
   }

   private static float smooth(float value) {
      return value * value * (3.0F - 2.0F * value);
   }

   private static float hash(float value) {
      return Mth.frac(Mth.sin(value * 12.9898F) * 43758.547F);
   }

   private static final class ActiveAxis {
      private final int bossId;
      private final int targetId;
      private final float radius;
      private final int duration;
      private final int seed;
      private final Vec3 center;
      private int age;

      private ActiveAxis(int bossId, int targetId, Vec3 center, float radius, int duration, int seed) {
         this.bossId = bossId;
         this.targetId = targetId;
         this.center = center;
         this.radius = radius;
         this.duration = duration;
         this.seed = seed;
      }
   }
}
