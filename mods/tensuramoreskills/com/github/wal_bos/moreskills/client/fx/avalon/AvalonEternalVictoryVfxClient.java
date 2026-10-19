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

public final class AvalonEternalVictoryVfxClient {
   private static final List<AvalonEternalVictoryVfxClient.Cast> CASTS = new ArrayList<>();
   private static final float CHARGE_END = 96.0F;
   private static final float CHARGE_SHINE_START = 82.0F;
   private static final float CHARGE_HOLD_END = 126.0F;
   private static final float BEAM_FLASH = 108.0F;
   private static final float SKY_START = 108.0F;
   private static final float SKY_END = 240.0F;
   private static final float SLAM_START = 240.0F;
   private static final float SLAM_END = 286.0F;
   private static final float ORB_START = 294.0F;
   private static final float ORB_FULL = 336.0F;
   private static final float ORB_BURST = 366.0F;
   private static final float FINAL_BEAM_START = 378.0F;
   private static final float PILLAR_PEAK = 452.0F;
   private static final float AFTERGLOW = 524.0F;
   private static final float MAIN_END = 584.0F;
   private static final float LINGER_END = 2000.0F;
   private static final int GOLD_DEEP_R = 255;
   private static final int GOLD_DEEP_G = 118;
   private static final int GOLD_DEEP_B = 12;
   private static final int GOLD_R = 255;
   private static final int GOLD_G = 184;
   private static final int GOLD_B = 38;
   private static final int GOLD_HOT_R = 255;
   private static final int GOLD_HOT_G = 228;
   private static final int GOLD_HOT_B = 112;
   private static final int GOLD_PALE_R = 255;
   private static final int GOLD_PALE_G = 248;
   private static final int GOLD_PALE_B = 210;
   private static boolean initialized;

   private AvalonEternalVictoryVfxClient() {
   }

   public static void init() {
      if (!initialized) {
         initialized = true;
         NeoForge.EVENT_BUS.addListener(AvalonEternalVictoryVfxClient::render);
      }
   }

   public static void spawn(ArthurAvalonVfxData payload) {
      if (ClientVfxGate.allowed("avalon_eternal_victory")) {
         init();
         Minecraft minecraft = Minecraft.getInstance();
         if (minecraft.level != null && minecraft.player != null) {
            double maxDistance = 920.0;
            if (!(minecraft.player.distanceToSqr(payload.start()) > maxDistance * maxDistance)
               || !(minecraft.player.distanceToSqr(payload.end()) > maxDistance * maxDistance)) {
               CASTS.add(new AvalonEternalVictoryVfxClient.Cast(payload, Util.getMillis()));

               while (CASTS.size() > 3) {
                  CASTS.remove(0);
               }
            }
         }
      }
   }

   private static void render(RenderLevelStageEvent event) {
      if (!ClientVfxGate.allowed("avalon_eternal_victory")) {
         CASTS.clear();
      } else if (event.getStage() == Stage.AFTER_PARTICLES) {
         Minecraft minecraft = Minecraft.getInstance();
         if (minecraft.level != null && minecraft.player != null && !CASTS.isEmpty()) {
            long now = Util.getMillis();
            Vec3 camera = event.getCamera().getPosition();
            PoseStack poseStack = event.getPoseStack();
            RenderSystem.enableBlend();
            RenderSystem.disableCull();
            RenderSystem.enableDepthTest();
            RenderSystem.depthMask(false);
            RenderSystem.blendFuncSeparate(SourceFactor.SRC_ALPHA, DestFactor.ONE, SourceFactor.ONE, DestFactor.ONE_MINUS_SRC_ALPHA);
            RenderSystem.setShader(GameRenderer::getPositionColorShader);
            poseStack.pushPose();
            poseStack.translate(-camera.x, -camera.y, -camera.z);
            Matrix4f matrix = poseStack.last().pose();
            BufferBuilder buffer = Tesselator.getInstance().begin(Mode.TRIANGLES, DefaultVertexFormat.POSITION_COLOR);
            boolean wrote = false;
            Iterator<AvalonEternalVictoryVfxClient.Cast> iterator = CASTS.iterator();

            while (iterator.hasNext()) {
               AvalonEternalVictoryVfxClient.Cast cast = iterator.next();
               float age = cast.ageTicks(now);
               if (cast.dead(age)) {
                  iterator.remove();
               } else {
                  cast.updateShake(minecraft, age);
                  wrote |= draw(buffer, matrix, cast, age, now, camera);
               }
            }

            if (wrote) {
               try {
                  BufferUploader.drawWithShader(buffer.buildOrThrow());
               } catch (IllegalStateException var12) {
               }
            }

            poseStack.popPose();
            RenderSystem.depthMask(true);
            RenderSystem.enableCull();
            RenderSystem.defaultBlendFunc();
            RenderSystem.disableBlend();
         }
      }
   }

   private static boolean draw(BufferBuilder buffer, Matrix4f matrix, AvalonEternalVictoryVfxClient.Cast cast, float age, long now, Vec3 camera) {
      ArthurAvalonVfxData payload = cast.payload;
      Vec3 start = payload.start();
      Vec3 end = payload.end();
      Vec3 attack = end.subtract(start);
      if (attack.lengthSqr() < 1.0E-8) {
         attack = new Vec3(0.0, -0.18, 1.0);
      }

      Vec3 attackDir = attack.normalize();
      Vec3 side = safeSide(attackDir);
      Vec3 up = attackDir.cross(side).normalize();
      float scale = Mth.clamp(Math.max(10.0F, payload.radius()), 10.0F, 18.0F);
      float time = (float)now * 0.001F;
      Vec3 sword = start.add(0.0, scale * 0.16, 0.0).add(attackDir.scale(scale * 0.035));
      int detail = detailLevel(camera, camera.distanceTo(start) < camera.distanceTo(end) ? start : end);
      boolean wrote = false;
      if (age >= 375.0F && age < 378.0F) {
         return false;
      }

      if (age <= 131.0F) {
         wrote |= gathering(buffer, matrix, camera, start, scale, age, time, payload.seed(), detail);
      }

      if (age >= 108.0F && age < 240.0F) {
         wrote |= skyBeam(buffer, matrix, camera, sword, scale, age, time, payload.seed(), detail);
      }

      if (age >= 240.0F && age <= 286.8F) {
         wrote |= slamBeam(buffer, matrix, camera, sword, end, attackDir, scale, age, time, payload.seed(), detail);
      }

      if (age > 286.8F && age < 375.0F) {
         wrote |= orbPhase(buffer, matrix, camera, end, attackDir, side, up, scale, age, time, payload.seed(), detail);
      }

      if (age >= 378.0F && age <= 589.0F) {
         wrote |= finalPillar(buffer, matrix, camera, end, scale, age, time, payload.seed(), detail);
      }

      if (age >= 580.0F && age <= Math.min(cast.durationTicks, 2000.0F)) {
         wrote |= lingeringGroundOrbs(buffer, matrix, camera, end, scale, age, Math.min(cast.durationTicks, 2000.0F), time, payload.seed(), detail);
      }

      return wrote | impactFrames(buffer, matrix, camera, start, sword, end, scale, age, time, payload.seed(), detail);
   }

   private static boolean gathering(BufferBuilder buffer, Matrix4f matrix, Vec3 camera, Vec3 start, float scale, float age, float time, long seed, int detail) {
      float chargeRaw = Mth.clamp(age / 96.0F, 0.0F, 1.0F);
      float charge = smoother(chargeRaw);
      float postCharge = Mth.clamp((age - 96.0F) / Math.max(1.0F, 30.0F), 0.0F, 1.0F);
      float gatherFade = age <= 96.0F ? 1.0F : 1.0F - smoother(postCharge);
      float shine = smoother(Mth.clamp((age - 82.0F) / Math.max(1.0F, 14.0F), 0.0F, 1.0F));
      float shineHold = age <= 126.0F ? shine : shine * (1.0F - smoother(Mth.clamp((age - 126.0F) / 5.0F, 0.0F, 1.0F)));
      Vec3 core = start.add(0.0, -0.48, 0.0);
      Vec3 ground = start.add(0.0, -1.62, 0.0);
      boolean wrote = false;
      if (charge > 0.18F) {
         float diskLive = smoother(Mth.clamp((charge - 0.18F) / 0.82F, 0.0F, 1.0F)) * gatherFade;
         double pulse = 0.97 + 0.03 * pulse01(time * 1.35F);
         wrote |= softDisk(
            buffer,
            matrix,
            ground.add(0.0, 0.035, 0.0),
            new Vec3(1.0, 0.0, 0.0),
            new Vec3(0.0, 0.0, 1.0),
            scale * 0.44 * pulse,
            255,
            118,
            12,
            diskLive * 0.026F,
            42
         );
         wrote |= softDisk(
            buffer,
            matrix,
            ground.add(0.0, 0.05, 0.0),
            new Vec3(1.0, 0.0, 0.0),
            new Vec3(0.0, 0.0, 1.0),
            scale * 0.27 * pulse,
            255,
            184,
            38,
            diskLive * 0.035F,
            38
         );
      }

      int count = detail >= 3 ? 260 : (detail == 2 ? 172 : 102);
      float density = 0.12F + charge * 0.88F;

      for (int i = 0; i < count; i++) {
         float h0 = hash(seed, 2000 + i * 17);
         float h1 = hash(seed, 2100 + i * 19);
         float h2 = hash(seed, 2200 + i * 23);
         float h3 = hash(seed, 2300 + i * 29);
         if (!(h3 > density)) {
            double angle = h0 * (float) (Math.PI * 2) + time * (0.025 + h2 * 0.025);
            double vertical = h2 * 2.0 - 1.0;
            double flat = Math.sqrt(Math.max(0.0, 1.0 - vertical * vertical));
            double shellRadius = scale * (0.24 + Math.sqrt(h1) * 0.64);
            Vec3 from = core.add(Math.cos(angle) * flat * shellRadius, vertical * shellRadius * 0.96, Math.sin(angle) * flat * shellRadius);
            double cycle = (time * (0.115 + h1 * 0.075) + h2 * 8.0 + chargeRaw * (0.2 + h0 * 0.26)) % 1.0;
            double travel = smoother((float)cycle);
            double swirl = Math.sin(time * (0.32 + h2 * 0.24) + i * 0.73) * scale * (0.01 + h3 * 0.016) * (1.0 - travel);
            double lift = Math.cos(time * (0.28 + h1 * 0.18) + i * 0.61) * scale * (0.01 + h0 * 0.014) * (1.0 - travel);
            Vec3 to = core.add(Math.cos(angle + (Math.PI / 2)) * swirl, lift, Math.sin(angle + (Math.PI / 2)) * swirl);
            Vec3 p = from.lerp(to, travel);
            float arrivalFade = 1.0F - smoother(Mth.clamp(((float)cycle - 0.8F) / 0.2F, 0.0F, 1.0F));
            float blink = 0.38F + 0.62F * pulse01(time * (4.2F + h0 * 3.6F) + i * 0.47F);
            float size = scale * (0.01F + h2 * 0.018F) * (0.8F + charge * 0.26F);
            float alpha = gatherFade * density * arrivalFade * blink * (0.3F + charge * 0.48F);
            wrote |= goldMote(buffer, matrix, camera, p, size, alpha, -1.0F);
            if (detail >= 2 && cycle > 0.16) {
               int trail = detail >= 3 ? 4 : 2;

               for (int j = 1; j <= trail; j++) {
                  double back = Math.max(0.0, cycle - j * 0.045);
                  double bt = smoother((float)back);
                  Vec3 bead = from.lerp(to, bt);
                  float trailAlpha = alpha * (1.0F - (float)j / (trail + 1)) * 0.34F;
                  wrote |= goldMote(buffer, matrix, camera, bead, size * (0.45F - j * 0.045F), trailAlpha, -1.0F);
               }
            }
         }
      }

      if (shineHold > 0.001F) {
         float pulse = 0.93F + 0.07F * pulse01(time * 2.8F);
         wrote |= bloomOrb(buffer, matrix, camera, core, scale * (0.12F + shineHold * 0.22F) * pulse, shineHold * 0.86F, true);
         int closeMotes = detail >= 3 ? 34 : (detail == 2 ? 22 : 12);

         for (int i = 0; i < closeMotes; i++) {
            float h0 = hash(seed, 3000 + i * 17);
            float h1 = hash(seed, 3100 + i * 19);
            float h2 = hash(seed, 3200 + i * 23);
            double a = h0 * (float) (Math.PI * 2) + time * (0.06 + h2 * 0.05);
            double r = scale * (0.03 + h1 * 0.12) * (1.0 - shineHold * 0.28);
            Vec3 p = core.add(Math.cos(a) * r, (h2 - 0.5) * scale * 0.1, Math.sin(a) * r);
            float blink = 0.42F + 0.58F * pulse01(time * (5.0F + h1 * 4.0F) + i * 0.51F);
            wrote |= goldMote(buffer, matrix, camera, p, scale * (0.007F + h2 * 0.012F), shineHold * blink * 0.56F, -1.0F);
         }
      }

      return wrote;
   }

   private static boolean skyBeam(BufferBuilder buffer, Matrix4f matrix, Vec3 camera, Vec3 sword, float scale, float age, float time, long seed, int detail) {
      float form = smoother(Mth.clamp((age - 108.0F) / 12.0F, 0.0F, 1.0F));
      float condense = smoother(Mth.clamp((age - 202.0F) / 38.0F, 0.0F, 1.0F));
      float live = form;
      if (live <= 0.001F) {
         return false;
      }

      Vec3 top = sword.add(0.0, scale * (34.0 + form * 14.0), 0.0);
      float coreWidth = scale * (1.02F + form * 0.48F - condense * 0.18F);
      float flameReach = scale * (7.45F + form * 4.55F) * (1.0F - condense * 0.28F);
      float haloLive = live * (0.86F + condense * 0.22F);
      boolean wrote = taperedBeamCore(buffer, matrix, sword, top, coreWidth, live * (1.18F + condense * 0.26F), time, detail);
      wrote |= swordFlameMass(
         buffer, matrix, camera, sword, top, scale * 1.12F, flameReach * 0.88F, live * 1.48F, Math.min(1.0F, condense * 1.06F), time, seed ^ 27159L, detail
      );
      wrote |= swordFillFlames(
         buffer,
         matrix,
         camera,
         sword,
         top,
         scale * 1.08F,
         flameReach * 0.82F,
         live * 1.42F,
         Math.min(1.0F, condense * 1.02F),
         time + 0.16F,
         seed ^ 8058L,
         detail
      );
      wrote |= swordFillFlames(buffer, matrix, camera, sword, top, scale * 1.02F, flameReach * 0.66F, live * 1.2F, 1.0F, time + 0.31F, seed ^ 8049L, detail);
      wrote |= swordFillFlames(buffer, matrix, camera, sword, top, scale * 0.94F, flameReach * 0.52F, live * 0.96F, 1.0F, time + 0.47F, seed ^ 8050L, detail);
      wrote |= condenseVeins(
         buffer, matrix, camera, sword, top, scale * 1.1F, flameReach * 0.84F, live * 1.26F, Math.min(1.0F, condense * 1.12F), time, seed ^ 17569L, detail
      );
      wrote |= bloomOrb(buffer, matrix, camera, sword, scale * (0.74F + form * 0.66F + condense * 0.28F), haloLive, true);
      wrote |= bloomOrb(buffer, matrix, camera, top, scale * (0.6F + form * 0.42F), live * (0.28F + condense * 0.2F), true);
      return wrote | cleanBeamMotes(buffer, matrix, camera, sword, top, scale * 1.12F, live * 0.76F, time, seed ^ 20964L, detail, 0.54F);
   }

   private static boolean taperedBeamCore(BufferBuilder buffer, Matrix4f matrix, Vec3 start, Vec3 end, float width, float live, float time, int detail) {
      Vec3 axis = end.subtract(start);
      if (!(axis.lengthSqr() < 1.0E-8) && !(live <= 0.001F)) {
         Vec3 dir = axis.normalize();
         Vec3 baseA = safeSide(dir);
         Vec3 baseB = dir.cross(baseA).normalize();
         int planes = detail >= 3 ? 10 : (detail == 2 ? 8 : 6);
         int segments = detail >= 3 ? 12 : (detail == 2 ? 10 : 8);
         boolean wrote = false;

         for (int plane = 0; plane < planes; plane++) {
            double a = plane * Math.PI / planes + time * 0.018;
            Vec3 lateral = baseA.scale(Math.cos(a)).add(baseB.scale(Math.sin(a))).normalize();

            for (int i = 1; i <= segments; i++) {
               double u0 = (double)(i - 1) / segments;
               double u1 = (double)i / segments;
               Vec3 p0 = start.lerp(end, u0);
               Vec3 p1 = start.lerp(end, u1);
               float root0 = smoother(Mth.clamp((float)u0 / 0.1F, 0.0F, 1.0F));
               float root1 = smoother(Mth.clamp((float)u1 / 0.1F, 0.0F, 1.0F));
               float breathe0 = 0.94F + 0.06F * Mth.sin(time * 1.8F + (float)u0 * 7.0F + plane * 0.48F);
               float breathe1 = 0.94F + 0.06F * Mth.sin(time * 1.8F + (float)u1 * 7.0F + plane * 0.48F);
               float w0 = width * root0 * breathe0;
               float w1 = width * root1 * breathe1;
               wrote |= softBand(buffer, matrix, p0, p1, lateral, w0 * 1.8F, w1 * 1.8F, 255, 118, 12, live * 0.065F, live * 0.065F);
               wrote |= softBand(buffer, matrix, p0, p1, lateral, w0 * 1.14F, w1 * 1.14F, 255, 184, 38, live * 0.18F, live * 0.18F);
               wrote |= softBand(buffer, matrix, p0, p1, lateral, w0 * 0.64F, w1 * 0.64F, 255, 228, 112, live * 0.42F, live * 0.42F);
               wrote |= softBand(buffer, matrix, p0, p1, lateral, w0 * 0.28F, w1 * 0.28F, 255, 248, 210, live * 0.82F, live * 0.82F);
               wrote |= softBand(buffer, matrix, p0, p1, lateral, w0 * 0.095F, w1 * 0.095F, 255, 255, 255, live, live);
            }
         }

         return wrote;
      } else {
         return false;
      }
   }

   private static boolean swordFlameMass(
      BufferBuilder buffer,
      Matrix4f matrix,
      Vec3 camera,
      Vec3 sword,
      Vec3 top,
      float scale,
      float reach,
      float live,
      float condense,
      float time,
      long seed,
      int detail
   ) {
      Vec3 axis = top.subtract(sword);
      if (axis.lengthSqr() < 1.0E-8) {
         return false;
      }

      Vec3 dir = axis.normalize();
      Vec3 side = safeSide(dir);
      Vec3 up = dir.cross(side).normalize();
      int tongues = detail >= 3 ? 34 : (detail == 2 ? 24 : 16);
      int segments = detail >= 3 ? 22 : (detail == 2 ? 18 : 14);
      boolean wrote = false;

      for (int i = 0; i < tongues; i++) {
         float h0 = hash(seed, 24000 + i * 17);
         float h1 = hash(seed, 24100 + i * 19);
         float h2 = hash(seed, 24200 + i * 23);
         float h3 = hash(seed, 24300 + i * 29);
         double angle = i * (float) (Math.PI * 2) / tongues + h0 * 0.46 + Math.sin(time * 0.34 + i) * 0.08;
         Vec3 radial = side.scale(Math.cos(angle)).add(up.scale(Math.sin(angle))).normalize();
         Vec3 tangent = dir.cross(radial);
         if (tangent.lengthSqr() < 1.0E-8) {
            tangent = safeSide(dir);
         } else {
            tangent = tangent.normalize();
         }

         double height = scale * (11.0 + h1 * 22.0);
         double spread = reach * (0.54 + h2 * 0.82);
         float tongueWidth = scale * (0.48F + h3 * 0.62F) * (1.0F - condense * 0.42F);
         float phase = h0 * (float) (Math.PI * 2) + i * 0.51F;
         float alpha = live * (0.42F + h2 * 0.36F);
         wrote |= flameTongue(
            buffer, matrix, sword, dir, radial, tangent, height, spread, tongueWidth * 1.92F, alpha * 0.2F, time, phase, condense, segments, 76, 34, 2
         );
         wrote |= flameTongue(
            buffer,
            matrix,
            sword,
            dir,
            radial,
            tangent,
            height * 0.99,
            spread * 0.92,
            tongueWidth * 1.28F,
            alpha * 0.48F,
            time + 0.07F,
            phase + 0.36F,
            condense,
            segments,
            255,
            118,
            12
         );
         wrote |= flameTongue(
            buffer,
            matrix,
            sword,
            dir,
            radial,
            tangent,
            height * 0.96,
            spread * 0.78,
            tongueWidth * 0.76F,
            alpha * 0.84F,
            time + 0.13F,
            phase + 0.74F,
            condense,
            segments,
            255,
            184,
            38
         );
         wrote |= flameTongue(
            buffer,
            matrix,
            sword,
            dir,
            radial,
            tangent,
            height * 0.91,
            spread * 0.58,
            tongueWidth * 0.38F,
            alpha,
            time + 0.2F,
            phase + 1.12F,
            condense,
            segments,
            255,
            228,
            112
         );
         if (i % 3 == 0) {
            wrote |= flameTongue(
               buffer,
               matrix,
               sword,
               dir,
               radial,
               tangent,
               height * 0.82,
               spread * 0.38,
               tongueWidth * 0.16F,
               alpha * 0.92F,
               time + 0.27F,
               phase + 1.48F,
               condense,
               segments,
               255,
               253,
               232
            );
         }
      }

      return wrote;
   }

   private static boolean swordFillFlames(
      BufferBuilder buffer,
      Matrix4f matrix,
      Vec3 camera,
      Vec3 sword,
      Vec3 top,
      float scale,
      float reach,
      float live,
      float condense,
      float time,
      long seed,
      int detail
   ) {
      Vec3 axis = top.subtract(sword);
      if (axis.lengthSqr() < 1.0E-8) {
         return false;
      }

      Vec3 dir = axis.normalize();
      Vec3 side = safeSide(dir);
      Vec3 up = dir.cross(side).normalize();
      int tongues = detail >= 3 ? 20 : (detail == 2 ? 14 : 9);
      int segments = detail >= 3 ? 18 : (detail == 2 ? 15 : 11);
      boolean wrote = false;

      for (int i = 0; i < tongues; i++) {
         float h0 = hash(seed, 25000 + i * 17);
         float h1 = hash(seed, 25100 + i * 19);
         float h2 = hash(seed, 25200 + i * 23);
         double angle = (i + 0.5) * (float) (Math.PI * 2) / tongues + h0 * 0.3 - time * (0.055 + h2 * 0.025);
         Vec3 radial = side.scale(Math.cos(angle)).add(up.scale(Math.sin(angle))).normalize();
         Vec3 tangent = dir.cross(radial);
         if (tangent.lengthSqr() < 1.0E-8) {
            tangent = safeSide(dir);
         } else {
            tangent = tangent.normalize();
         }

         double height = scale * (7.0 + h1 * 13.0);
         double spread = reach * (0.3 + h2 * 0.5);
         float width = scale * (0.36F + h1 * 0.38F) * (1.0F - condense * 0.55F);
         float alpha = live * (0.3F + h2 * 0.28F);
         float phase = h0 * (float) (Math.PI * 2) + i * 0.77F;
         wrote |= flameTongue(
            buffer, matrix, sword, dir, radial, tangent, height, spread, width * 1.34F, alpha * 0.44F, time + 0.11F, phase, condense, segments, 255, 184, 38
         );
         wrote |= flameTongue(
            buffer,
            matrix,
            sword,
            dir,
            radial,
            tangent,
            height * 0.94,
            spread * 0.68,
            width * 0.54F,
            alpha * 0.9F,
            time + 0.18F,
            phase + 0.68F,
            condense,
            segments,
            255,
            228,
            112
         );
      }

      return wrote;
   }

   private static boolean condenseVeins(
      BufferBuilder buffer,
      Matrix4f matrix,
      Vec3 camera,
      Vec3 sword,
      Vec3 top,
      float scale,
      float reach,
      float live,
      float condense,
      float time,
      long seed,
      int detail
   ) {
      if (condense <= 0.015F) {
         return false;
      }

      Vec3 axis = top.subtract(sword);
      if (axis.lengthSqr() < 1.0E-8) {
         return false;
      }

      Vec3 dir = axis.normalize();
      Vec3 side = safeSide(dir);
      Vec3 up = dir.cross(side).normalize();
      int count = detail >= 3 ? 24 : (detail == 2 ? 17 : 10);
      boolean wrote = false;

      for (int i = 0; i < count; i++) {
         float h0 = hash(seed, 27000 + i * 17);
         float h1 = hash(seed, 27100 + i * 19);
         float h2 = hash(seed, 27200 + i * 23);
         double cycle = (time * (0.48 + h2 * 0.28) + h0 * 5.0) % 1.0;
         float travel = smoother((float)cycle);
         double angle = h0 * (float) (Math.PI * 2) + time * (0.08 + h1 * 0.05);
         Vec3 radial = side.scale(Math.cos(angle)).add(up.scale(Math.sin(angle))).normalize();
         double y = scale * (4.0 + h1 * 22.0);
         double outer = reach * (0.48 + h2 * 0.46) * (1.0 - condense * 0.48);
         Vec3 from = sword.add(dir.scale(y)).add(radial.scale(outer));
         Vec3 to = sword.add(dir.scale(y * (0.82 + h2 * 0.12))).add(radial.scale(scale * (0.08 + h0 * 0.12)));
         Vec3 mid = from.lerp(to, 0.5).add(dir.scale(scale * (0.8 + h1 * 1.8))).add(radial.scale(scale * 0.22 * Math.sin(time * 1.8 + i)));
         Vec3 a = from.lerp(mid, travel);
         Vec3 b = mid.lerp(to, travel);
         Vec3 lateral = safeSide(b.subtract(a));
         float envelope = (1.0F - smoother(Mth.clamp(((float)cycle - 0.72F) / 0.28F, 0.0F, 1.0F))) * condense * live;
         float width = scale * (0.05F + h2 * 0.08F) * (1.0F - travel * 0.62F);
         wrote |= softBand(buffer, matrix, a, b, lateral, width * 2.2F, width * 0.75F, 255, 118, 12, envelope * 0.18F, envelope * 0.04F);
         wrote |= softBand(buffer, matrix, a, b, lateral, width, width * 0.34F, 255, 228, 112, envelope * 0.76F, envelope * 0.16F);
         wrote |= softBand(buffer, matrix, a, b, lateral, width * 0.28F, 0.0F, 255, 255, 244, envelope, 0.0F);
      }

      return wrote;
   }

   private static boolean flameTongue(
      BufferBuilder buffer,
      Matrix4f matrix,
      Vec3 origin,
      Vec3 dir,
      Vec3 radial,
      Vec3 tangent,
      double height,
      double spread,
      float width,
      float alpha,
      float time,
      float phase,
      float condense,
      int segments,
      int r,
      int g,
      int b
   ) {
      if (!(alpha <= 0.001F) && !(width <= 0.001F)) {
         Vec3[] left = new Vec3[segments + 1];
         Vec3[] right = new Vec3[segments + 1];
         float[] alphas = new float[segments + 1];
         float collapse = condense * condense;

         for (int i = 0; i <= segments; i++) {
            double u = (double)i / segments;
            float uf = (float)u;
            float root = smoother(Mth.clamp(uf / 0.12F, 0.0F, 1.0F));
            float body = (float)Math.pow(Math.max(0.0, Math.sin(Math.PI * u)), 0.62);
            float tip = 1.0F - smoother(Mth.clamp((uf - 0.72F) / 0.28F, 0.0F, 1.0F));
            double burst = Math.pow(u, 0.42) * Math.pow(Math.max(0.0, 1.0 - u), 0.24);
            double outward = spread * root * body * burst * (1.0 - collapse * 0.84);
            double curl = Math.sin(time * (2.45 + condense * 1.4) + phase + u * (7.8 + phase * 0.22)) * spread * 0.16 * root * tip * (1.0 - collapse * 0.55);
            double lick = Math.sin(time * (4.1 + condense * 2.2) + phase * 1.6 + u * 14.0) * spread * 0.052 * root * body;
            double inward = -spread * collapse * 0.12 * root * Math.pow(u, 1.4);
            Vec3 center = origin.add(dir.scale(height * u * (1.0 - collapse * 0.08))).add(radial.scale(outward + lick + inward)).add(tangent.scale(curl));
            float edge = body;
            float localWidth = width * edge * (0.76F + 0.24F * Mth.sin(time * (3.0F + condense * 1.8F) + phase + uf * 10.5F));
            if (i == 0 || i == segments) {
               localWidth = 0.0F;
            }

            left[i] = center.subtract(tangent.scale(localWidth));
            right[i] = center.add(tangent.scale(localWidth));
            alphas[i] = alpha * edge * (0.72F + 0.28F * Mth.sin(time * 3.4F + phase + uf * 9.0F));
         }

         for (int i = 1; i <= segments; i++) {
            triGradient(buffer, matrix, left[i - 1], right[i - 1], right[i], r, g, b, alphas[i - 1] * 0.72F, alphas[i - 1], alphas[i]);
            triGradient(buffer, matrix, left[i - 1], right[i], left[i], r, g, b, alphas[i - 1] * 0.72F, alphas[i], alphas[i] * 0.72F);
         }

         return true;
      } else {
         return false;
      }
   }

   private static boolean slamBeam(
      BufferBuilder buffer, Matrix4f matrix, Vec3 camera, Vec3 sword, Vec3 end, Vec3 attackDir, float scale, float age, float time, long seed, int detail
   ) {
      float raw = Mth.clamp((age - 240.0F) / 46.0F, 0.0F, 1.0F);
      float q = smoother(raw);
      float fade = 1.0F - smoother(Mth.clamp((age - 286.0F) / 6.0F, 0.0F, 1.0F));
      float live = fade;
      Vec3 targetVector = end.subtract(sword);
      double targetDistance = targetVector.length();
      Vec3 targetDir = targetDistance < 1.0E-6 ? attackDir : targetVector.scale(1.0 / targetDistance);
      Vec3 vertical = new Vec3(0.0, 1.0, 0.0);
      Vec3 beamDir = slerpDirection(vertical, targetDir, q);
      double heldLength = scale * 48.0;
      double targetLength = Math.max(scale * 4.0, targetDistance);
      float lengthBlend = smoother(Mth.clamp((raw - 0.08F) / 0.92F, 0.0F, 1.0F));
      double beamLength = heldLength + (targetLength - heldLength) * lengthBlend;
      Vec3 beamTip = sword.add(beamDir.scale(beamLength));
      float velocity = Mth.sin(raw * (float) Math.PI);
      float coreWidth = scale * (1.04F + q * 0.2F + velocity * 0.22F);
      float flameReach = scale * (2.72F + q * 0.4F + velocity * 2.1F);
      float slamCondense = Mth.clamp(1.0F - velocity * 0.56F, 0.38F, 1.0F);
      float transition = smoother(Mth.clamp(raw / 0.18F, 0.0F, 1.0F));
      float condenseCarry = 1.0F - smoother(Mth.clamp(raw / 0.26F, 0.0F, 1.0F));
      boolean wrote = taperedBeamCore(buffer, matrix, sword, beamTip, coreWidth, live * 1.1F, time + q * 0.14F, detail);
      wrote |= swordFlameMass(
         buffer, matrix, camera, sword, beamTip, scale * 1.04F, flameReach * 0.94F, live * 1.12F, slamCondense, time + q * 0.08F, seed ^ 27159L, detail
      );
      wrote |= swordFillFlames(
         buffer, matrix, camera, sword, beamTip, scale * 1.0F, flameReach * 0.86F, live * 1.08F, slamCondense, time + q * 0.1F, seed ^ 8058L, detail
      );
      if (condenseCarry > 0.001F) {
         wrote |= condenseVeins(buffer, matrix, camera, sword, beamTip, scale, flameReach * 0.88F, live * condenseCarry, 1.0F, time, seed ^ 17569L, detail);
      }

      wrote |= bloomOrb(buffer, matrix, camera, sword, scale * (1.18F + velocity * 0.18F), live * (0.92F - transition * 0.12F), true);
      if (transition < 0.999F) {
         wrote |= cleanBeamMotes(buffer, matrix, camera, sword, beamTip, scale * 0.96F, live * (1.0F - transition) * 0.62F, time, seed ^ 20964L, detail, 0.3F);
      }

      wrote |= slamBeamFlameEnvelope(buffer, matrix, camera, sword, beamTip, scale, coreWidth, live * 0.88F, q, velocity, time, seed ^ 10769L, detail);
      wrote |= slamBeamMotes(buffer, matrix, camera, sword, beamTip, scale, coreWidth, live * transition * 0.68F, velocity, time, seed ^ 14603L, detail);
      if (q > 0.7F) {
         float impact = smoother((q - 0.7F) / 0.3F);
         wrote |= bloomOrb(buffer, matrix, camera, end, scale * (0.7F + impact * 1.42F), live * impact, true);
         wrote |= slamImpactFlames(buffer, matrix, end, scale * 0.92F, live * impact * 0.74F, time, seed ^ 28956L, detail);
         Vec3 ground = end.add(0.0, 0.04, 0.0);
         wrote |= softDisk(
            buffer, matrix, ground, new Vec3(1.0, 0.0, 0.0), new Vec3(0.0, 0.0, 1.0), scale * (0.92 + impact * 2.7), 255, 118, 12, live * impact * 0.06F, 64
         );
         wrote |= softDisk(
            buffer,
            matrix,
            ground.add(0.0, 0.02, 0.0),
            new Vec3(1.0, 0.0, 0.0),
            new Vec3(0.0, 0.0, 1.0),
            scale * (0.5 + impact * 1.72),
            255,
            228,
            112,
            live * impact * 0.09F,
            60
         );
      }

      return wrote;
   }

   private static boolean slamBeamFlameEnvelope(
      BufferBuilder buffer,
      Matrix4f matrix,
      Vec3 camera,
      Vec3 start,
      Vec3 end,
      float scale,
      float beamWidth,
      float live,
      float q,
      float velocity,
      float time,
      long seed,
      int detail
   ) {
      Vec3 axis = end.subtract(start);
      if (!(axis.lengthSqr() < 1.0E-8) && !(live <= 0.001F)) {
         Vec3 dir = axis.normalize();
         Vec3 side = safeSide(dir);
         Vec3 up = dir.cross(side).normalize();
         double length = axis.length();
         int count = detail >= 3 ? 56 : (detail == 2 ? 38 : 22);
         int segments = detail >= 3 ? 16 : (detail == 2 ? 13 : 10);
         boolean wrote = false;
         float motionLive = live * smoother(Mth.clamp(q / 0.1F, 0.0F, 1.0F)) * (0.42F + velocity * 0.58F);

         for (int i = 0; i < count; i++) {
            float h0 = hash(seed, 40000 + i * 17);
            float h1 = hash(seed, 40100 + i * 19);
            float h2 = hash(seed, 40200 + i * 23);
            float h3 = hash(seed, 40300 + i * 29);
            double anchorU = 0.07 + h0 * 0.8;
            Vec3 axisPoint = start.add(dir.scale(length * anchorU));
            double angle = i * (float) (Math.PI * 2) / count + h1 * 0.4 + time * (0.055 + h2 * 0.03);
            Vec3 radial = side.scale(Math.cos(angle)).add(up.scale(Math.sin(angle))).normalize();
            Vec3 tangent = dir.cross(radial);
            if (tangent.lengthSqr() < 1.0E-8) {
               tangent = side;
            } else {
               tangent = tangent.normalize();
            }

            Vec3 anchor = axisPoint.add(radial.scale(beamWidth * (0.58 + h3 * 0.22)));
            Vec3 flameDir = dir.scale(0.82).add(radial.scale(0.18 + velocity * (0.24 + h2 * 0.18)));
            if (flameDir.lengthSqr() < 1.0E-8) {
               flameDir = dir;
            } else {
               flameDir = flameDir.normalize();
            }

            double height = scale * (2.8 + h1 * 6.4) * (0.84 + velocity * 0.42);
            double spread = beamWidth * (0.48 + h2 * 0.82) * (0.8 + velocity * 0.58);
            float flameWidth = beamWidth * (0.1F + h3 * 0.13F);
            float alpha = motionLive * (0.3F + h2 * 0.34F);
            float phase = h0 * (float) (Math.PI * 2) + i * 0.57F;
            wrote |= flameTongue(
               buffer, matrix, anchor, flameDir, radial, tangent, height, spread, flameWidth * 1.72F, alpha * 0.24F, time, phase, 0.0F, segments, 76, 34, 2
            );
            wrote |= flameTongue(
               buffer,
               matrix,
               anchor,
               flameDir,
               radial,
               tangent,
               height * 0.96,
               spread * 0.8,
               flameWidth,
               alpha * 0.64F,
               time + 0.1F,
               phase + 0.58F,
               0.0F,
               segments,
               255,
               184,
               38
            );
            wrote |= flameTongue(
               buffer,
               matrix,
               anchor,
               flameDir,
               radial,
               tangent,
               height * 0.88,
               spread * 0.54,
               flameWidth * 0.42F,
               alpha,
               time + 0.18F,
               phase + 1.08F,
               0.0F,
               segments,
               255,
               228,
               112
            );
         }

         return wrote;
      } else {
         return false;
      }
   }

   private static boolean slamBeamMotes(
      BufferBuilder buffer,
      Matrix4f matrix,
      Vec3 camera,
      Vec3 start,
      Vec3 end,
      float scale,
      float beamWidth,
      float live,
      float velocity,
      float time,
      long seed,
      int detail
   ) {
      Vec3 axis = end.subtract(start);
      if (axis.lengthSqr() < 1.0E-8) {
         return false;
      }

      Vec3 dir = axis.normalize();
      Vec3 side = safeSide(dir);
      Vec3 up = dir.cross(side).normalize();
      int count = detail >= 3 ? 92 : (detail == 2 ? 58 : 34);
      boolean wrote = false;

      for (int i = 0; i < count; i++) {
         float h0 = hash(seed, 41000 + i * 17);
         float h1 = hash(seed, 41100 + i * 19);
         float h2 = hash(seed, 41200 + i * 23);
         double u = (i + h0) / count;
         Vec3 center = start.lerp(end, u);
         double angle = h1 * (float) (Math.PI * 2) + time * (0.18 + h2 * 0.14);
         double radial = beamWidth * (0.22 + h0 * (0.58 + velocity * 0.32));
         Vec3 mote = center.add(side.scale(Math.cos(angle) * radial)).add(up.scale(Math.sin(angle) * radial));
         float blink = 0.32F + 0.68F * pulse01(time * (5.6F + h1 * 4.8F) + i * 0.47F);
         wrote |= goldMote(buffer, matrix, camera, mote, scale * (0.009F + h2 * 0.024F), live * blink * (0.42F + velocity * 0.24F), -1.0F);
      }

      return wrote;
   }

   private static boolean slamImpactFlames(BufferBuilder buffer, Matrix4f matrix, Vec3 end, float scale, float live, float time, long seed, int detail) {
      if (live <= 0.001F) {
         return false;
      }

      int count = detail >= 3 ? 24 : (detail == 2 ? 17 : 11);
      int segments = detail >= 3 ? 14 : (detail == 2 ? 12 : 9);
      boolean wrote = false;
      Vec3 vertical = new Vec3(0.0, 1.0, 0.0);

      for (int i = 0; i < count; i++) {
         float h0 = hash(seed, 42000 + i * 17);
         float h1 = hash(seed, 42100 + i * 19);
         float h2 = hash(seed, 42200 + i * 23);
         double angle = i * (float) (Math.PI * 2) / count + h0 * 0.36;
         Vec3 radial = new Vec3(Math.cos(angle), 0.0, Math.sin(angle));
         Vec3 flameDir = radial.scale(0.78 + h1 * 0.3).add(vertical.scale(0.48 + h2 * 0.46)).normalize();
         Vec3 tangent = flameDir.cross(radial);
         if (tangent.lengthSqr() < 1.0E-8) {
            tangent = safeSide(flameDir);
         } else {
            tangent = tangent.normalize();
         }

         double height = scale * (2.0 + h1 * 5.2);
         double spread = scale * (0.55 + h2 * 1.25);
         float width = scale * (0.18F + h0 * 0.28F);
         float alpha = live * (0.34F + h2 * 0.38F);
         float phase = h0 * (float) (Math.PI * 2) + i * 0.49F;
         wrote |= flameTongue(
            buffer, matrix, end, flameDir, radial, tangent, height, spread, width * 1.7F, alpha * 0.24F, time, phase, 0.0F, segments, 76, 34, 2
         );
         wrote |= flameTongue(
            buffer,
            matrix,
            end,
            flameDir,
            radial,
            tangent,
            height * 0.94,
            spread * 0.76,
            width,
            alpha * 0.64F,
            time + 0.1F,
            phase + 0.5F,
            0.0F,
            segments,
            255,
            184,
            38
         );
         wrote |= flameTongue(
            buffer,
            matrix,
            end,
            flameDir,
            radial,
            tangent,
            height * 0.86,
            spread * 0.48,
            width * 0.42F,
            alpha,
            time + 0.18F,
            phase + 1.0F,
            0.0F,
            segments,
            255,
            228,
            112
         );
      }

      return wrote;
   }

   private static Vec3 slerpDirection(Vec3 from, Vec3 to, double t) {
      Vec3 a = from.lengthSqr() < 1.0E-8 ? new Vec3(0.0, 1.0, 0.0) : from.normalize();
      Vec3 b = to.lengthSqr() < 1.0E-8 ? a : to.normalize();
      double dot = Mth.clamp(a.dot(b), -1.0, 1.0);
      if (dot > 0.9995) {
         Vec3 mixed = a.scale(1.0 - t).add(b.scale(t));
         return mixed.lengthSqr() < 1.0E-8 ? b : mixed.normalize();
      } else if (dot < -0.9995) {
         Vec3 axis = safeSide(a);
         double angle = Math.PI * t;
         Vec3 mixed = a.scale(Math.cos(angle)).add(axis.scale(Math.sin(angle)));
         return mixed.lengthSqr() < 1.0E-8 ? b : mixed.normalize();
      } else {
         double angle = Math.acos(dot);
         double sin = Math.sin(angle);
         double wa = Math.sin((1.0 - t) * angle) / sin;
         double wb = Math.sin(t * angle) / sin;
         Vec3 mixed = a.scale(wa).add(b.scale(wb));
         return mixed.lengthSqr() < 1.0E-8 ? b : mixed.normalize();
      }
   }

   private static boolean orbPhase(
      BufferBuilder buffer,
      Matrix4f matrix,
      Vec3 camera,
      Vec3 end,
      Vec3 attackDir,
      Vec3 side,
      Vec3 up,
      float scale,
      float age,
      float time,
      long seed,
      int detail
   ) {
      float snap = smoother(Mth.clamp((age - 286.9F) / 2.2F, 0.0F, 1.0F));
      float settle = smoother(Mth.clamp((age - 294.0F) / Math.max(1.0F, 42.0F), 0.0F, 1.0F));
      float rupture = smoother(Mth.clamp((age - 352.0F) / 14.0F, 0.0F, 1.0F));
      float postBurst = smoother(Mth.clamp((age - 366.0F) / 4.0F, 0.0F, 1.0F));
      float live = snap * (1.0F - postBurst);
      if (live <= 0.001F) {
         return false;
      }

      float shellRead = 0.84F + settle * 0.16F;
      float birthFlash = impactEnvelope(age, 287.0F, 3.4F);
      Vec3 center = sunCenter(end, scale);
      double pulse = 0.95 + 0.05 * pulse01(time * (2.0F + rupture * 2.2F));
      double radius = scale * (4.02 + shellRead * 0.54 + rupture * 1.06) * pulse;
      boolean wrote = false;
      wrote |= softDisk(
         buffer,
         matrix,
         end.add(0.0, 0.04, 0.0),
         new Vec3(1.0, 0.0, 0.0),
         new Vec3(0.0, 0.0, 1.0),
         scale * (1.12 + shellRead * 1.74 + rupture * 0.86),
         255,
         118,
         12,
         live * 0.04F,
         68
      );
      wrote |= softOrb(buffer, matrix, camera, center, radius * 2.06, 255, 118, 12, live * 0.055F, 24);
      wrote |= softOrb(buffer, matrix, camera, center, radius * 1.36, 255, 184, 38, live * 0.1F, 24);
      wrote |= sunShell(buffer, matrix, camera, center, attackDir, side, up, radius, scale, live, shellRead, rupture, time, seed, detail);
      wrote |= burstRays(
         buffer,
         matrix,
         center,
         scale * 0.96F,
         Math.max(live * (0.18F + rupture * 0.72F), birthFlash * 0.68F),
         time + 0.22F,
         seed ^ 29489L,
         Math.max(detail, 2)
      );
      wrote |= spinningStarBurst(
         buffer,
         matrix,
         camera,
         center.add(0.0, scale * 0.08, 0.0),
         scale * (4.5F + rupture * 1.6F),
         Math.max(live * 0.22F, birthFlash * 0.64F),
         time * 0.46F,
         seed ^ 29490L
      );
      int spit = detail >= 3 ? 64 : (detail == 2 ? 44 : 26);

      for (int i = 0; i < spit; i++) {
         float h0 = hash(seed, 33000 + i * 17);
         float h1 = hash(seed, 33100 + i * 19);
         float h2 = hash(seed, 33200 + i * 23);
         double a = h0 * (float) (Math.PI * 2) + time * (0.1 + h2 * 0.06);
         double radial = radius * (0.36 + h1 * 0.54);
         double rise = scale * (0.08 + h2 * 0.84);
         Vec3 p = center.add(Math.cos(a) * radial, (h2 - 0.1) * rise, Math.sin(a) * radial);
         float flicker = 0.38F + 0.62F * pulse01(time * (7.0F + h1 * 4.4F) + i * 0.73F);
         wrote |= goldMote(buffer, matrix, camera, p, scale * (0.022F + h2 * 0.05F), live * flicker * (0.34F + rupture * 0.46F), -1.0F);
      }

      int lashes = detail >= 3 ? 28 : (detail == 2 ? 20 : 12);

      for (int i = 0; i < lashes; i++) {
         float h0 = hash(seed, 34000 + i * 17);
         float h1 = hash(seed, 34100 + i * 19);
         float h2 = hash(seed, 34200 + i * 23);
         double yaw = h0 * (float) (Math.PI * 2) + time * (0.16 + h2 * 0.07);
         double z = h1 * 1.4 - 0.7;
         double xy = Math.sqrt(Math.max(0.0, 1.0 - Math.min(1.0, z * z)));
         Vec3 dir = side.scale(Math.cos(yaw) * xy).add(up.scale(z)).add(attackDir.scale(Math.sin(yaw) * xy));
         if (dir.lengthSqr() < 1.0E-8) {
            dir = up;
         }

         dir = dir.normalize();
         Vec3 a = center.add(dir.scale(radius * (0.88 + h2 * 0.1)));
         Vec3 b = center.add(dir.scale(radius * (1.24 + h1 * (0.54 + rupture * 1.1))));
         Vec3 lateral = safeSide(dir);
         float alpha = live * (0.06F + h2 * 0.12F) * (0.62F + rupture * 0.82F);
         wrote |= softBand(buffer, matrix, a, b, lateral, scale * (0.075F + h1 * 0.09F), 255, 184, 38, alpha * 0.42F, 0.0F);
         wrote |= softBand(buffer, matrix, a, b, lateral, scale * (0.024F + h2 * 0.04F), 255, 248, 210, alpha, 0.0F);
      }

      return wrote;
   }

   private static boolean sunShell(
      BufferBuilder buffer,
      Matrix4f matrix,
      Vec3 camera,
      Vec3 center,
      Vec3 attackDir,
      Vec3 side,
      Vec3 up,
      double radius,
      float scale,
      float live,
      float grow,
      float rupture,
      float time,
      long seed,
      int detail
   ) {
      boolean wrote = false;
      wrote |= softOrb(buffer, matrix, camera, center, radius * 1.92, 255, 115, 12, live * 0.045F, 22);
      wrote |= softOrb(buffer, matrix, camera, center, radius * 1.6, 255, 118, 12, live * 0.09F, 22);
      wrote |= softOrb(buffer, matrix, camera, center, radius * 1.28, 255, 184, 38, live * 0.16F, 22);
      wrote |= softOrb(buffer, matrix, camera, center, radius * 0.98, 255, 228, 112, live * 0.28F, 22);
      wrote |= softOrb(buffer, matrix, camera, center, radius * 0.7, 255, 248, 210, live * 0.44F, 22);
      wrote |= softOrb(buffer, matrix, camera, center, radius * 0.34, 255, 255, 250, live * 0.86F, 22);
      int cells = detail >= 3 ? 72 : (detail == 2 ? 46 : 26);

      for (int i = 0; i < cells; i++) {
         float h0 = hash(seed, 5000 + i * 17);
         float h1 = hash(seed, 5100 + i * 19);
         float h2 = hash(seed, 5200 + i * 23);
         float h3 = hash(seed, 5300 + i * 29);
         double yaw = h0 * (float) (Math.PI * 2) + time * (0.016 + h3 * 0.014);
         double z = h1 * 2.0 - 1.0;
         double xy = Math.sqrt(Math.max(0.0, 1.0 - z * z));
         Vec3 radial = side.scale(Math.cos(yaw) * xy).add(up.scale(z)).add(attackDir.scale(Math.sin(yaw) * xy));
         if (radial.lengthSqr() < 1.0E-8) {
            radial = side;
         }

         radial = radial.normalize();
         double shell = radius * (0.76 + h2 * 0.18);
         Vec3 p = center.add(radial.scale(shell));
         float solarPulse = 0.36F + 0.64F * pulse01(time * (2.0F + h1 * 2.6F) + i * 0.57F);
         float size = scale * (0.04F + h3 * 0.07F) * (0.82F + rupture * 0.44F);
         float alpha = live * solarPulse * (0.24F + grow * 0.36F);
         wrote |= goldMote(buffer, matrix, camera, p, size, alpha, -1.0F);
      }

      int corona = detail >= 3 ? 36 : (detail == 2 ? 24 : 14);

      for (int i = 0; i < corona; i++) {
         float h0 = hash(seed, 6000 + i * 17);
         float h1 = hash(seed, 6100 + i * 19);
         float h2 = hash(seed, 6200 + i * 23);
         double yaw = h0 * (float) (Math.PI * 2) + time * (0.008 + h2 * 0.01);
         double z = h1 * 1.5 - 0.75;
         double xy = Math.sqrt(Math.max(0.0, 1.0 - Math.min(1.0, z * z)));
         Vec3 radial = side.scale(Math.cos(yaw) * xy).add(up.scale(z)).add(attackDir.scale(Math.sin(yaw) * xy));
         if (radial.lengthSqr() < 1.0E-8) {
            radial = up;
         }

         radial = radial.normalize();
         Vec3 a = center.add(radial.scale(radius * (0.94 + h2 * 0.05)));
         Vec3 b = center.add(radial.scale(radius * (1.18 + h1 * (0.32 + rupture * 0.42))));
         Vec3 lateral = safeSide(radial);
         float alpha = live * (0.05F + h2 * 0.085F) * (0.78F + rupture * 0.42F);
         wrote |= softBand(buffer, matrix, a, b, lateral, scale * (0.04F + h1 * 0.05F), 255, 184, 38, alpha * 0.42F, 0.0F);
         wrote |= softBand(buffer, matrix, a, b, lateral, scale * (0.015F + h1 * 0.022F), 255, 248, 210, alpha, 0.0F);
      }

      return wrote;
   }

   private static boolean finalPillar(BufferBuilder buffer, Matrix4f matrix, Vec3 camera, Vec3 end, float scale, float age, float time, long seed, int detail) {
      float erupt = smoother(Mth.clamp((age - 378.0F) / 5.0F, 0.0F, 1.0F));
      float peak = smoother(Mth.clamp((age - 378.0F) / 74.0F, 0.0F, 1.0F));
      float fade = 1.0F - smoother(Mth.clamp((age - 524.0F) / 60.0F, 0.0F, 1.0F));
      float live = erupt * fade;
      if (live <= 0.001F) {
         return false;
      }

      float blastAge = age - 378.0F;
      float blastGrow = smoother(Mth.clamp((blastAge + 1.0F) / 4.0F, 0.0F, 1.0F));
      float blastFade = 1.0F - smoother(Mth.clamp((blastAge - 18.0F) / 30.0F, 0.0F, 1.0F));
      float blastLive = blastGrow * blastFade * fade;
      float starFade = 1.0F - smoother(Mth.clamp((blastAge - 8.0F) / 18.0F, 0.0F, 1.0F));
      float starLive = blastGrow * starFade * fade;
      Vec3 origin = sunCenter(end, scale);
      Vec3 top = origin.add(0.0, scale * (54.0 + peak * 28.0), 0.0);
      float breathe = 0.94F + 0.06F * pulse01(time * 2.15F);
      float width = scale * (4.68F + peak * 3.34F) * breathe;
      boolean wrote = animatedPillarCore(buffer, matrix, origin, top, width, live * 1.36F, time, detail);
      wrote |= pillarFlameMantle(buffer, matrix, camera, origin, top, scale * 1.18F, width * 1.14F, live * 1.26F, peak, time, seed ^ 48879L, detail);
      wrote |= pillarVeins(buffer, matrix, camera, origin, top, scale * 1.16F, width * 1.1F, live * 1.22F, time, seed ^ 31249L, detail);
      wrote |= cleanBeamMotes(buffer, matrix, camera, origin, top, scale * 1.86F, live * 1.34F, time, seed ^ 43981L, detail, 1.06F);
      wrote |= finalExplosionCloud(buffer, matrix, camera, origin, scale * 1.28F, blastAge, blastLive, time, seed ^ 27511L, detail);
      wrote |= finalDetonationMotes(buffer, matrix, camera, origin, scale * 1.28F, blastAge, blastLive, time, seed ^ 39473L, detail);
      wrote |= spinningStarBurst(buffer, matrix, camera, origin.add(0.0, scale * 0.18, 0.0), scale * (9.8F + peak * 3.6F), starLive, time, seed ^ 49374L);
      wrote |= spinningStarBurst(
         buffer, matrix, camera, origin.add(0.0, scale * 0.34, 0.0), scale * (5.8F + peak * 1.8F), starLive * 0.6F, -time * 0.84F, seed ^ 49361L
      );
      if (blastLive > 0.001F) {
         float ringProgress = smoother(Mth.clamp((blastAge + 0.5F) / 18.0F, 0.0F, 1.0F));
         wrote |= orbShockwave(
            buffer, matrix, camera, origin, scale * (3.0 + ringProgress * 20.8), scale, blastLive, time + 0.28F, seed ^ 30498L, Math.max(detail, 2)
         );
         wrote |= burstRays(buffer, matrix, origin, scale * 2.42F, blastLive, time + 0.41F, seed ^ 4471L, Math.max(detail, 2));
         wrote |= burstRays(buffer, matrix, origin, scale * 1.78F, blastLive * 0.66F, time + 0.74F, seed ^ 4472L, Math.max(detail, 2));
      }

      wrote |= softOrb(buffer, matrix, camera, origin.add(0.0, scale * 18.0, 0.0), scale * (56.0 + peak * 30.0), 255, 118, 12, live * 0.12F, 24);
      wrote |= softOrb(buffer, matrix, camera, origin.add(0.0, scale * 16.0, 0.0), scale * (44.0 + peak * 24.0), 255, 184, 38, live * 0.17F, 24);
      wrote |= softOrb(buffer, matrix, camera, origin.add(0.0, scale * 14.0, 0.0), scale * (31.0 + peak * 18.0), 255, 248, 210, live * 0.15F, 22);
      wrote |= softOrb(buffer, matrix, camera, origin.add(0.0, scale * 7.8, 0.0), scale * (40.0 + peak * 24.0), 255, 118, 12, live * 0.2F, 22);
      wrote |= softOrb(buffer, matrix, camera, origin.add(0.0, scale * 9.6, 0.0), scale * (30.0 + peak * 18.0), 255, 184, 38, live * 0.28F, 22);
      wrote |= softOrb(buffer, matrix, camera, origin.add(0.0, scale * 11.8, 0.0), scale * (18.6 + peak * 12.8), 255, 248, 210, live * 0.26F, 20);
      wrote |= softOrb(buffer, matrix, camera, origin.add(0.0, scale * 13.4, 0.0), scale * (10.8 + peak * 8.4), 255, 251, 232, live * 0.18F, 18);
      Vec3 ground = end.add(0.0, 0.06, 0.0);
      float baseRadius = scale * (4.4F + peak * 7.2F);
      wrote |= softDisk(buffer, matrix, ground, new Vec3(1.0, 0.0, 0.0), new Vec3(0.0, 0.0, 1.0), baseRadius * 4.1, 255, 118, 12, live * 0.2F, 98);
      wrote |= softDisk(
         buffer, matrix, ground.add(0.0, 0.02, 0.0), new Vec3(1.0, 0.0, 0.0), new Vec3(0.0, 0.0, 1.0), baseRadius * 2.7, 255, 184, 38, live * 0.3F, 94
      );
      wrote |= softDisk(
         buffer, matrix, ground.add(0.0, 0.04, 0.0), new Vec3(1.0, 0.0, 0.0), new Vec3(0.0, 0.0, 1.0), baseRadius * 1.52, 255, 248, 210, live * 0.4F, 90
      );
      int eruption = detail >= 3 ? 332 : (detail == 2 ? 220 : 128);

      for (int i = 0; i < eruption; i++) {
         float h0 = hash(seed, 10000 + i * 17);
         float h1 = hash(seed, 10100 + i * 19);
         float h2 = hash(seed, 10200 + i * 23);
         double a = h0 * (float) (Math.PI * 2) + time * (0.052 + h2 * 0.03);
         double radial = scale * (0.14 + h1 * 5.2) * (0.74 + peak * 1.0);
         double rise = scale * (0.42 + h2 * 15.0) * (0.96 + peak * 0.92);
         Vec3 mote = origin.add(Math.cos(a) * radial, rise, Math.sin(a) * radial);
         float blink = 0.3F + 0.7F * pulse01(time * (6.2F + h1 * 5.6F) + i * 0.49F);
         wrote |= goldMote(buffer, matrix, camera, mote, scale * (0.012F + h2 * 0.036F), live * blink, -1.0F);
      }

      int haloOrbs = detail >= 3 ? 112 : (detail == 2 ? 72 : 40);

      for (int i = 0; i < haloOrbs; i++) {
         float h0 = hash(seed, 10800 + i * 17);
         float h1 = hash(seed, 10900 + i * 19);
         float h2 = hash(seed, 11000 + i * 23);
         double a = h0 * (float) (Math.PI * 2) + time * (0.06 + h2 * 0.04);
         double radial = scale * (2.8 + h1 * (7.2 + peak * 6.6));
         double rise = scale * (0.2 + h2 * 9.2);
         Vec3 p = origin.add(Math.cos(a) * radial, rise, Math.sin(a) * radial);
         float blink = 0.4F + 0.6F * pulse01(time * (5.6F + h1 * 4.8F) + i * 0.58F);
         wrote |= goldMote(buffer, matrix, camera, p, scale * (0.026F + h2 * 0.07F), live * blink * 0.98F, -1.0F);
      }

      return wrote;
   }

   private static boolean animatedPillarCore(BufferBuilder buffer, Matrix4f matrix, Vec3 start, Vec3 end, float width, float live, float time, int detail) {
      Vec3 axis = end.subtract(start);
      if (!(axis.lengthSqr() < 1.0E-8) && !(live <= 0.001F)) {
         Vec3 dir = axis.normalize();
         Vec3 baseA = safeSide(dir);
         Vec3 baseB = dir.cross(baseA).normalize();
         int planes = detail >= 3 ? 12 : (detail == 2 ? 10 : 8);
         int segments = detail >= 3 ? 20 : (detail == 2 ? 16 : 12);
         boolean wrote = false;

         for (int plane = 0; plane < planes; plane++) {
            double baseAngle = plane * Math.PI / planes + time * 0.012;
            Vec3 lateral = baseA.scale(Math.cos(baseAngle)).add(baseB.scale(Math.sin(baseAngle))).normalize();

            for (int i = 1; i <= segments; i++) {
               float u0 = (float)(i - 1) / segments;
               float u1 = (float)i / segments;
               Vec3 a = start.lerp(end, u0);
               Vec3 b = start.lerp(end, u1);
               float source0 = 0.82F + 0.18F * smoother(Mth.clamp(u0 / 0.08F, 0.0F, 1.0F));
               float source1 = 0.82F + 0.18F * smoother(Mth.clamp(u1 / 0.08F, 0.0F, 1.0F));
               float pulse0 = 0.91F + 0.09F * Mth.sin(time * 2.6F + u0 * 12.0F + plane * 0.51F);
               float pulse1 = 0.91F + 0.09F * Mth.sin(time * 2.6F + u1 * 12.0F + plane * 0.51F);
               float w0 = width * source0 * pulse0;
               float w1 = width * source1 * pulse1;
               wrote |= softBand(buffer, matrix, a, b, lateral, w0 * 1.48F, w1 * 1.48F, 255, 118, 12, live * 0.085F, live * 0.085F);
               wrote |= softBand(buffer, matrix, a, b, lateral, w0, w1, 255, 184, 38, live * 0.22F, live * 0.22F);
               wrote |= softBand(buffer, matrix, a, b, lateral, w0 * 0.58F, w1 * 0.58F, 255, 228, 112, live * 0.52F, live * 0.52F);
               wrote |= softBand(buffer, matrix, a, b, lateral, w0 * 0.25F, w1 * 0.25F, 255, 248, 210, live * 0.9F, live * 0.9F);
               wrote |= softBand(buffer, matrix, a, b, lateral, w0 * 0.075F, w1 * 0.075F, 255, 255, 255, live, live);
            }
         }

         return wrote;
      } else {
         return false;
      }
   }

   private static boolean pillarFlameMantle(
      BufferBuilder buffer,
      Matrix4f matrix,
      Vec3 camera,
      Vec3 start,
      Vec3 end,
      float scale,
      float beamWidth,
      float live,
      float peak,
      float time,
      long seed,
      int detail
   ) {
      Vec3 axis = end.subtract(start);
      if (axis.lengthSqr() < 1.0E-8) {
         return false;
      }

      Vec3 dir = axis.normalize();
      Vec3 side = safeSide(dir);
      Vec3 up = dir.cross(side).normalize();
      double length = axis.length();
      int count = detail >= 3 ? 18 : (detail == 2 ? 13 : 8);
      int segments = detail >= 3 ? 16 : (detail == 2 ? 13 : 10);
      boolean wrote = false;

      for (int i = 0; i < count; i++) {
         float h0 = hash(seed, 36000 + i * 17);
         float h1 = hash(seed, 36100 + i * 19);
         float h2 = hash(seed, 36200 + i * 23);
         float h3 = hash(seed, 36300 + i * 29);
         double anchorU = 0.03 + h0 * 0.72;
         Vec3 axisPoint = start.add(dir.scale(length * anchorU));
         double angle = i * (float) (Math.PI * 2) / count + h1 * 0.36 + time * (0.04 + h2 * 0.018);
         Vec3 radial = side.scale(Math.cos(angle)).add(up.scale(Math.sin(angle))).normalize();
         Vec3 tangent = dir.cross(radial);
         if (tangent.lengthSqr() < 1.0E-8) {
            tangent = side;
         } else {
            tangent = tangent.normalize();
         }

         Vec3 anchor = axisPoint.add(radial.scale(beamWidth * (0.56 + h3 * 0.16)));
         double height = scale * (5.0 + h2 * 10.0);
         double spread = beamWidth * (0.18 + h3 * 0.26) * (0.86 + peak * 0.2);
         float flameWidth = beamWidth * (0.065F + h1 * 0.065F);
         float alpha = live * (0.26F + h2 * 0.3F);
         float phase = h0 * (float) (Math.PI * 2) + i * 0.61F;
         wrote |= flameTongue(
            buffer, matrix, anchor, dir, radial, tangent, height, spread, flameWidth * 1.5F, alpha * 0.26F, time, phase, 0.0F, segments, 86, 38, 2
         );
         wrote |= flameTongue(
            buffer,
            matrix,
            anchor,
            dir,
            radial,
            tangent,
            height * 0.95,
            spread * 0.76,
            flameWidth,
            alpha * 0.64F,
            time + 0.1F,
            phase + 0.52F,
            0.0F,
            segments,
            255,
            184,
            38
         );
         wrote |= flameTongue(
            buffer,
            matrix,
            anchor,
            dir,
            radial,
            tangent,
            height * 0.88,
            spread * 0.48,
            flameWidth * 0.42F,
            alpha,
            time + 0.18F,
            phase + 1.02F,
            0.0F,
            segments,
            255,
            228,
            112
         );
      }

      return wrote;
   }

   private static boolean pillarVeins(
      BufferBuilder buffer, Matrix4f matrix, Vec3 camera, Vec3 start, Vec3 end, float scale, float beamWidth, float live, float time, long seed, int detail
   ) {
      Vec3 axis = end.subtract(start);
      if (axis.lengthSqr() < 1.0E-8) {
         return false;
      }

      Vec3 dir = axis.normalize();
      Vec3 side = safeSide(dir);
      Vec3 up = dir.cross(side).normalize();
      double length = axis.length();
      int count = detail >= 3 ? 22 : (detail == 2 ? 16 : 10);
      boolean wrote = false;

      for (int i = 0; i < count; i++) {
         float h0 = hash(seed, 37000 + i * 17);
         float h1 = hash(seed, 37100 + i * 19);
         float h2 = hash(seed, 37200 + i * 23);
         double cycle = (time * (0.22 + h2 * 0.16) + h0 * 7.0) % 1.0;
         double lengthU = 0.08 + h1 * 0.13;
         double u0 = Math.max(0.0, cycle - lengthU);
         double u1 = Math.min(1.0, cycle);
         double angle = h0 * (float) (Math.PI * 2) + time * (0.05 + h1 * 0.03);
         double radius = beamWidth * (0.08 + h2 * 0.3);
         Vec3 radial = side.scale(Math.cos(angle)).add(up.scale(Math.sin(angle))).normalize();
         Vec3 a = start.add(dir.scale(length * u0)).add(radial.scale(radius));
         Vec3 b = start.add(dir.scale(length * u1)).add(radial.scale(radius * 0.86));
         Vec3 lateral = safeSide(b.subtract(a));
         float fade = (1.0F - smoother(Mth.clamp(((float)cycle - 0.9F) / 0.1F, 0.0F, 1.0F))) * live;
         float width = scale * (0.025F + h2 * 0.045F);
         wrote |= softBand(buffer, matrix, a, b, lateral, width * 2.2F, width * 0.7F, 255, 228, 112, fade * 0.32F, fade * 0.04F);
         wrote |= softBand(buffer, matrix, a, b, lateral, width * 0.72F, 0.0F, 255, 255, 255, fade * 0.92F, 0.0F);
      }

      return wrote;
   }

   private static boolean cleanBeam(BufferBuilder buffer, Matrix4f matrix, Vec3 start, Vec3 end, float width, float live, float time, int detail) {
      Vec3 axis = end.subtract(start);
      if (!(axis.lengthSqr() < 1.0E-8) && !(live <= 0.001F)) {
         Vec3 dir = axis.normalize();
         Vec3 baseA = safeSide(dir);
         Vec3 baseB = dir.cross(baseA).normalize();
         int planes = detail >= 3 ? 10 : (detail == 2 ? 8 : 6);
         boolean wrote = false;

         for (int i = 0; i < planes; i++) {
            double a = i * Math.PI / planes + time * 0.016;
            Vec3 lateral = baseA.scale(Math.cos(a)).add(baseB.scale(Math.sin(a))).normalize();
            wrote |= softBand(buffer, matrix, start, end, lateral, width * 1.32F, 255, 118, 12, live * 0.05F, live * 0.05F);
            wrote |= softBand(
               buffer, matrix, start.add(dir.scale(0.02)), end.add(dir.scale(0.02)), lateral, width * 0.92F, 255, 184, 38, live * 0.12F, live * 0.12F
            );
            wrote |= softBand(
               buffer, matrix, start.add(dir.scale(0.04)), end.add(dir.scale(0.04)), lateral, width * 0.54F, 255, 228, 112, live * 0.28F, live * 0.28F
            );
            wrote |= softBand(
               buffer, matrix, start.add(dir.scale(0.06)), end.add(dir.scale(0.06)), lateral, width * 0.22F, 255, 248, 210, live * 0.68F, live * 0.68F
            );
            wrote |= softBand(
               buffer, matrix, start.add(dir.scale(0.08)), end.add(dir.scale(0.08)), lateral, width * 0.085F, 255, 255, 255, live * 0.94F, live * 0.94F
            );
         }

         return wrote;
      } else {
         return false;
      }
   }

   private static boolean cleanBeamMotes(
      BufferBuilder buffer, Matrix4f matrix, Vec3 camera, Vec3 start, Vec3 end, float scale, float live, float time, long seed, int detail, float radialScale
   ) {
      Vec3 axis = end.subtract(start);
      if (axis.lengthSqr() < 1.0E-8) {
         return false;
      }

      Vec3 dir = axis.normalize();
      Vec3 side = safeSide(dir);
      Vec3 up = dir.cross(side).normalize();
      int count = detail >= 3 ? 72 : (detail == 2 ? 46 : 26);
      boolean wrote = false;

      for (int i = 0; i < count; i++) {
         float h0 = hash(seed, 15000 + i * 17);
         float h1 = hash(seed, 15100 + i * 19);
         float h2 = hash(seed, 15200 + i * 23);
         double u = (i + h0 * 0.84) / count;
         Vec3 c = start.lerp(end, u);
         double a = h1 * (float) (Math.PI * 2) + time * (0.08 + h2 * 0.08);
         double radial = scale * (0.08 + h0 * radialScale);
         Vec3 p = c.add(side.scale(Math.cos(a) * radial)).add(up.scale(Math.sin(a) * radial));
         float blink = 0.34F + 0.66F * pulse01(time * (4.2F + h1 * 3.8F) + i * 0.57F);
         wrote |= goldMote(buffer, matrix, camera, p, scale * (0.008F + h2 * 0.022F), live * blink * 0.42F, -1.0F);
      }

      return wrote;
   }

   private static boolean slowSolarRibbons(
      BufferBuilder buffer, Matrix4f matrix, Vec3 camera, Vec3 start, Vec3 end, float beamWidth, float live, float time, long seed, int detail
   ) {
      Vec3 axis = end.subtract(start);
      if (axis.lengthSqr() < 1.0E-8) {
         return false;
      }

      Vec3 dir = axis.normalize();
      Vec3 baseA = safeSide(dir);
      Vec3 baseB = dir.cross(baseA).normalize();
      int ribbons = detail >= 3 ? 10 : (detail == 2 ? 8 : 6);
      int segments = detail >= 3 ? 78 : (detail == 2 ? 60 : 44);
      boolean wrote = false;

      for (int ribbon = 0; ribbon < ribbons; ribbon++) {
         float h0 = hash(seed, 17000 + ribbon * 31);
         float h1 = hash(seed, 17100 + ribbon * 37);
         double direction = ribbon % 2 == 0 ? 1.0 : -1.0;
         double turns = 1.45 + h0 * 0.72;
         double base = ribbon * (float) (Math.PI * 2) / ribbons + h1 * 0.34;
         Vec3[] points = new Vec3[segments + 1];

         for (int i = 0; i <= segments; i++) {
            double u = (double)i / segments;
            double envelope = Math.sin(u * Math.PI);
            double angle = base + direction * u * (float) (Math.PI * 2) * turns + direction * time * (0.07 + h0 * 0.024);
            double breathe = 0.92 + 0.08 * Math.sin(time * 0.46 + ribbon * 1.7 + u * (float) (Math.PI * 2) * 1.2);
            double radius = beamWidth * (0.84 + h1 * 0.34) * breathe * (0.76 + envelope * 0.34);
            Vec3 radial = baseA.scale(Math.cos(angle)).add(baseB.scale(Math.sin(angle))).normalize();
            points[i] = start.lerp(end, u).add(radial.scale(radius));
         }

         float alpha = live * (0.42F + h0 * 0.3F);
         float ribbonWidth = beamWidth * (0.056F + h1 * 0.042F);
         wrote |= drawSmoothRibbon(buffer, matrix, camera, points, ribbonWidth * 2.8F, 255, 118, 12, alpha * 0.18F);
         wrote |= drawSmoothRibbon(buffer, matrix, camera, points, ribbonWidth * 1.8F, 255, 184, 38, alpha * 0.38F);
         wrote |= drawSmoothRibbon(buffer, matrix, camera, points, ribbonWidth * 1.0F, 255, 228, 112, alpha * 0.78F);
         wrote |= drawSmoothRibbon(buffer, matrix, camera, points, ribbonWidth * 0.38F, 255, 252, 226, alpha);
      }

      return wrote;
   }

   private static boolean drawSmoothRibbon(BufferBuilder buffer, Matrix4f matrix, Vec3 camera, Vec3[] points, float width, int r, int g, int b, float alpha) {
      if (!(alpha <= 0.001F) && points.length >= 2) {
         Vec3[] left = new Vec3[points.length];
         Vec3[] right = new Vec3[points.length];
         float[] alphas = new float[points.length];

         for (int i = 0; i < points.length; i++) {
            Vec3 prev = points[Math.max(0, i - 1)];
            Vec3 next = points[Math.min(points.length - 1, i + 1)];
            Vec3 tangent = next.subtract(prev);
            if (tangent.lengthSqr() < 1.0E-8) {
               tangent = new Vec3(0.0, 1.0, 0.0);
            }

            tangent = tangent.normalize();
            Vec3 view = camera.subtract(points[i]);
            if (view.lengthSqr() < 1.0E-8) {
               view = new Vec3(0.0, 0.0, 1.0);
            }

            Vec3 lateral = tangent.cross(view.normalize());
            if (lateral.lengthSqr() < 1.0E-8) {
               lateral = safeSide(tangent);
            } else {
               lateral = lateral.normalize();
            }

            float u = (float)i / (points.length - 1);
            float taper = 0.18F + 0.82F * (float)Math.sin(u * Math.PI);
            left[i] = points[i].subtract(lateral.scale(width * taper));
            right[i] = points[i].add(lateral.scale(width * taper));
            alphas[i] = alpha * taper;
         }

         for (int i = 1; i < points.length; i++) {
            vertex(buffer, matrix, left[i - 1], r, g, b, alphas[i - 1]);
            vertex(buffer, matrix, right[i - 1], r, g, b, alphas[i - 1]);
            vertex(buffer, matrix, right[i], r, g, b, alphas[i]);
            vertex(buffer, matrix, left[i - 1], r, g, b, alphas[i - 1]);
            vertex(buffer, matrix, right[i], r, g, b, alphas[i]);
            vertex(buffer, matrix, left[i], r, g, b, alphas[i]);
         }

         return true;
      } else {
         return false;
      }
   }

   private static boolean lingeringGroundOrbs(
      BufferBuilder buffer, Matrix4f matrix, Vec3 camera, Vec3 end, float scale, float age, float endAge, float time, long seed, int detail
   ) {
      float intro = smoother(Mth.clamp((age - 580.0F) / 18.0F, 0.0F, 1.0F));
      float outro = 1.0F - smoother(Mth.clamp((age - (endAge - 80.0F)) / 80.0F, 0.0F, 1.0F));
      float live = intro * outro;
      if (live <= 0.001F) {
         return false;
      }

      boolean wrote = false;
      Vec3 ground = end.add(0.0, 0.045, 0.0);
      wrote |= softDisk(buffer, matrix, ground, new Vec3(1.0, 0.0, 0.0), new Vec3(0.0, 0.0, 1.0), scale * 5.1, 255, 118, 12, live * 0.017F, 64);
      wrote |= softDisk(
         buffer, matrix, ground.add(0.0, 0.02, 0.0), new Vec3(1.0, 0.0, 0.0), new Vec3(0.0, 0.0, 1.0), scale * 3.4, 255, 184, 38, live * 0.021F, 58
      );
      int count = detail >= 3 ? 62 : (detail == 2 ? 40 : 24);

      for (int i = 0; i < count; i++) {
         float h0 = hash(seed, 19000 + i * 17);
         float h1 = hash(seed, 19100 + i * 19);
         float h2 = hash(seed, 19200 + i * 23);
         float h3 = hash(seed, 19300 + i * 29);
         double angle = h0 * (float) (Math.PI * 2) + time * (0.006 + h3 * 0.007);
         double radial = scale * (0.25 + Math.sqrt(h1) * 4.65);
         double cycle = (time * (0.03 + h2 * 0.022) + h0 * 9.0) % 1.0;
         double rise = scale * (0.015 + cycle * (0.32 + h2 * 0.78));
         double sway = Math.sin(time * (0.18 + h1 * 0.14) + i * 0.63) * scale * (0.008 + h3 * 0.018);
         Vec3 p = ground.add(
            Math.cos(angle) * radial + Math.cos(angle + (Math.PI / 2)) * sway, rise, Math.sin(angle) * radial + Math.sin(angle + (Math.PI / 2)) * sway
         );
         float fade = 1.0F - smoother(Mth.clamp(((float)cycle - 0.74F) / 0.26F, 0.0F, 1.0F));
         float blink = 0.3F + 0.7F * pulse01(time * (3.4F + h1 * 3.8F) + i * 0.41F);
         wrote |= goldMote(buffer, matrix, camera, p, scale * (0.009F + h3 * 0.019F), live * fade * blink * 0.44F, -1.0F);
      }

      return wrote;
   }

   private static boolean impactFrames(
      BufferBuilder buffer, Matrix4f matrix, Vec3 camera, Vec3 start, Vec3 sword, Vec3 end, float scale, float age, float time, long seed, int detail
   ) {
      boolean wrote = false;
      float first = impactEnvelope(age, 108.0F, 3.2F);
      if (first > 0.001F) {
         Vec3 center = sword;
         wrote |= softOrb(buffer, matrix, camera, center, scale * (11.2 + first * 9.0), 255, 118, 12, first * 0.26F, 24);
         wrote |= softOrb(buffer, matrix, camera, center, scale * (7.2 + first * 6.0), 255, 228, 112, first * 0.72F, 24);
         wrote |= softOrb(buffer, matrix, camera, center, scale * (3.2 + first * 2.9), 255, 255, 255, first, 24);
         wrote |= burstRays(buffer, matrix, center, scale * 0.86F, first * 0.9F, time + 0.14F, seed ^ 4353L, Math.max(2, detail));
      }

      float release = impactEnvelope(age, 241.1F, 4.4F);
      if (release > 0.001F) {
         wrote |= softOrb(buffer, matrix, camera, sword, scale * (7.8 + release * 6.0), 255, 118, 12, release * 0.2F, 22);
         wrote |= softOrb(buffer, matrix, camera, sword, scale * (4.5 + release * 3.8), 255, 228, 112, release * 0.5F, 22);
         wrote |= softOrb(buffer, matrix, camera, sword, scale * (2.0 + release * 1.8), 255, 255, 255, release * 0.96F, 22);
      }

      float hit = impactEnvelope(age, 286.4F, 3.8F);
      if (hit > 0.001F) {
         float hitProgress = smoother(Mth.clamp((age - 285.0F) / 4.6F, 0.0F, 1.0F));
         wrote |= softOrb(buffer, matrix, camera, end, scale * (10.0 + hit * 8.6), 255, 118, 12, hit * 0.12F, 24);
         wrote |= softOrb(buffer, matrix, camera, end, scale * (6.0 + hit * 5.0), 255, 184, 38, hit * 0.34F, 24);
         wrote |= softOrb(buffer, matrix, camera, end, scale * (2.8 + hit * 2.4), 255, 255, 248, hit * 0.82F, 24);
         wrote |= orbShockwave(
            buffer,
            matrix,
            camera,
            end.add(0.0, 0.08, 0.0),
            scale * (1.0 + hitProgress * 5.4),
            scale * 0.84F,
            hit * 0.42F,
            time,
            seed ^ 17412L,
            Math.max(2, detail)
         );
      }

      Vec3 orb = sunCenter(end, scale);
      float sunBirth = impactEnvelope(age, 287.05F, 4.2F);
      if (sunBirth > 0.001F) {
         wrote |= softOrb(buffer, matrix, camera, orb, scale * (18.0 + sunBirth * 16.0), 255, 118, 12, sunBirth * 0.12F, 28);
         wrote |= softOrb(buffer, matrix, camera, orb, scale * (11.8 + sunBirth * 10.8), 255, 184, 38, sunBirth * 0.28F, 28);
         wrote |= softOrb(buffer, matrix, camera, orb, scale * (6.4 + sunBirth * 5.8), 255, 228, 112, sunBirth * 0.64F, 28);
         wrote |= softOrb(buffer, matrix, camera, orb, scale * (3.0 + sunBirth * 2.8), 255, 255, 255, sunBirth, 28);
         wrote |= spinningStarBurst(
            buffer, matrix, camera, orb.add(0.0, scale * 0.08, 0.0), scale * (5.0F + sunBirth * 2.0F), sunBirth * 0.82F, time * 0.44F, seed ^ 8789L
         );
         wrote |= burstRays(buffer, matrix, orb, scale * 1.06F, sunBirth * 0.7F, time + 0.18F, seed ^ 8790L, Math.max(2, detail));
      }

      float orbBurst = impactEnvelope(age, 366.5F, 6.2F);
      float orbTail = impactEnvelope(age, 368.1F, 8.2F) * 0.48F;
      float orbWhite = impactEnvelope(age, 366.35F, 3.4F);
      float burst = Math.max(orbBurst, orbTail);
      if (burst > 0.001F) {
         wrote |= softOrb(buffer, matrix, camera, orb, scale * (20.2 + burst * 18.8), 255, 118, 12, burst * 0.12F, 30);
         wrote |= softOrb(buffer, matrix, camera, orb, scale * (14.2 + burst * 13.0), 255, 184, 38, burst * 0.24F, 30);
         wrote |= softOrb(buffer, matrix, camera, orb, scale * (8.6 + burst * 8.0), 255, 228, 112, burst * 0.58F, 30);
         wrote |= softOrb(buffer, matrix, camera, orb, scale * (4.1 + orbWhite * 4.1), 255, 255, 255, orbWhite, 30);
         wrote |= violentBurstPetals(buffer, matrix, orb, scale * 1.16F, burst, time, seed, detail);
         wrote |= burstRays(buffer, matrix, orb, scale * 1.38F, burst, time + 0.12F, seed ^ 8772L, Math.max(2, detail));
         wrote |= spinningStarBurst(buffer, matrix, camera, orb.add(0.0, scale * 0.12, 0.0), scale * (6.4F + burst * 2.2F), burst * 0.72F, time, seed ^ 8773L);
      }

      float finalIgnition = impactEnvelope(age, 378.8F, 6.4F);
      float finalWhiteFrame = impactEnvelope(age, 378.2F, 2.1F);
      if (finalWhiteFrame > 0.001F) {
         wrote |= softOrb(buffer, matrix, camera, orb, scale * (38.0 + finalWhiteFrame * 42.0), 255, 255, 255, finalWhiteFrame, 30);
      }

      if (finalIgnition > 0.001F) {
         Vec3 top = orb.add(0.0, scale * 70.0, 0.0);
         float frameWidth = scale * (5.8F + finalIgnition * 4.2F);
         float ringProgress = smoother(Mth.clamp((age - 378.0F + 0.8F) / 8.4F, 0.0F, 1.0F));
         wrote |= animatedPillarCore(buffer, matrix, orb, top, frameWidth, finalIgnition, time + 0.31F, Math.max(2, detail));
         wrote |= pillarFlameMantle(
            buffer, matrix, camera, orb, top, scale * 1.12F, frameWidth, finalIgnition * 1.04F, finalIgnition, time + 0.21F, seed ^ 24337L, Math.max(2, detail)
         );
         wrote |= softOrb(buffer, matrix, camera, orb, scale * (32.0 + finalIgnition * 32.0), 255, 118, 12, finalIgnition * 0.28F, 30);
         wrote |= softOrb(buffer, matrix, camera, orb, scale * (23.0 + finalIgnition * 24.0), 255, 184, 38, finalIgnition * 0.58F, 30);
         wrote |= softOrb(buffer, matrix, camera, orb, scale * (14.4 + finalIgnition * 15.6), 255, 228, 112, finalIgnition * 0.98F, 30);
         wrote |= softOrb(buffer, matrix, camera, orb, scale * (6.8 + finalIgnition * 7.4), 255, 255, 255, finalIgnition, 30);
         wrote |= burstRays(buffer, matrix, orb, scale * 2.24F, finalIgnition, time + 0.48F, seed ^ 34986L, Math.max(2, detail));
         wrote |= orbShockwave(
            buffer, matrix, camera, orb, scale * (2.7 + ringProgress * 17.8), scale * 1.02F, finalIgnition, time + 0.33F, seed ^ 39355L, Math.max(2, detail)
         );
         wrote |= spinningStarBurst(
            buffer, matrix, camera, orb.add(0.0, scale * 0.16, 0.0), scale * (9.0F + finalIgnition * 3.2F), finalIgnition, time, seed ^ 43605L
         );
      }

      return wrote;
   }

   private static boolean finalExplosionCloud(
      BufferBuilder buffer, Matrix4f matrix, Vec3 camera, Vec3 origin, float scale, float blastAge, float live, float time, long seed, int detail
   ) {
      if (live <= 0.001F) {
         return false;
      }

      float spread = smoother(Mth.clamp((blastAge + 1.0F) / 22.0F, 0.0F, 1.0F));
      int count = detail >= 3 ? 64 : (detail == 2 ? 42 : 24);
      boolean wrote = false;

      for (int i = 0; i < count; i++) {
         float h0 = hash(seed, 45000 + i * 17);
         float h1 = hash(seed, 45100 + i * 19);
         float h2 = hash(seed, 45200 + i * 23);
         float h3 = hash(seed, 45300 + i * 29);
         double angle = i * (float) (Math.PI * 2) / count + h0 * 0.78 + time * (0.01 + h3 * 0.012);
         double radial = scale * (0.36 + h1 * 7.0) * (0.28 + spread * 1.02);
         double rise = scale * (0.18 + h2 * 7.6) * (0.46 + spread * 1.02);
         Vec3 center = origin.add(Math.cos(angle) * radial, rise, Math.sin(angle) * radial);
         float pulse = 0.9F + 0.1F * pulse01(time * (2.8F + h2 * 2.4F) + i * 0.43F);
         float size = scale * (0.62F + h2 * 1.26F) * (0.76F + spread * 0.64F) * pulse;
         float alpha = live * (0.62F + h1 * 0.44F);
         wrote |= softOrb(buffer, matrix, camera, center, size * 2.5, 118, 48, 2, alpha * 0.1F, 16);
         wrote |= softOrb(buffer, matrix, camera, center, size * 1.72, 255, 118, 12, alpha * 0.24F, 16);
         wrote |= softOrb(buffer, matrix, camera, center, size * 1.12, 255, 184, 38, alpha * 0.5F, 16);
         wrote |= softOrb(buffer, matrix, camera, center, size * 0.64, 255, 228, 112, alpha * 0.86F, 16);
         if (i % 2 == 0) {
            wrote |= softOrb(buffer, matrix, camera, center, size * 0.24, 255, 255, 245, alpha, 14);
         }
      }

      return wrote;
   }

   private static boolean finalDetonationMotes(
      BufferBuilder buffer, Matrix4f matrix, Vec3 camera, Vec3 origin, float scale, float blastAge, float live, float time, long seed, int detail
   ) {
      if (live <= 0.001F) {
         return false;
      }

      float travel = smoother(Mth.clamp((blastAge + 0.8F) / 28.0F, 0.0F, 1.0F));
      int count = detail >= 3 ? 320 : (detail == 2 ? 210 : 126);
      boolean wrote = false;

      for (int i = 0; i < count; i++) {
         float h0 = hash(seed, 46000 + i * 17);
         float h1 = hash(seed, 46100 + i * 19);
         float h2 = hash(seed, 46200 + i * 23);
         float h3 = hash(seed, 46300 + i * 29);
         double angle = h0 * (float) (Math.PI * 2) + time * (0.08 + h2 * 0.05);
         double radial = scale * (0.24 + h1 * 11.4) * (0.16 + travel * 1.04);
         double rise = scale * (0.2 + h2 * 11.6) * (0.18 + travel * 1.04);
         double sideKick = Math.sin(time * (0.9 + h1 * 0.8) + i * 0.61) * scale * (0.03 + h3 * 0.1) * travel;
         Vec3 p = origin.add(
            Math.cos(angle) * radial + Math.cos(angle + (Math.PI / 2)) * sideKick, rise, Math.sin(angle) * radial + Math.sin(angle + (Math.PI / 2)) * sideKick
         );
         float blink = 0.34F + 0.66F * pulse01(time * (7.4F + h1 * 5.8F) + i * 0.67F);
         float size = scale * (0.01F + h3 * 0.028F) * (1.1F - travel * 0.14F);
         wrote |= goldMote(buffer, matrix, camera, p, size, live * blink * (0.82F + h2 * 0.24F), -1.0F);
      }

      return wrote;
   }

   private static boolean spinningStarBurst(BufferBuilder buffer, Matrix4f matrix, Vec3 camera, Vec3 center, float size, float live, float time, long seed) {
      if (!(live <= 0.001F) && !(size <= 0.001F)) {
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
         double angle = time * 2.85 + hash(seed, 47000) * (float) (Math.PI * 2);
         Vec3 axisA = right.scale(Math.cos(angle)).add(up.scale(Math.sin(angle))).normalize();
         Vec3 axisB = right.scale(-Math.sin(angle)).add(up.scale(Math.cos(angle))).normalize();
         double diagonalAngle = angle + (Math.PI / 4);
         Vec3 axisC = right.scale(Math.cos(diagonalAngle)).add(up.scale(Math.sin(diagonalAngle))).normalize();
         Vec3 axisD = right.scale(-Math.sin(diagonalAngle)).add(up.scale(Math.cos(diagonalAngle))).normalize();
         float pulse = 0.86F + 0.14F * pulse01(time * 7.8F);
         boolean wrote = false;
         wrote |= starArm(buffer, matrix, center, view, axisA, size * 1.42F * pulse, size * 0.08F, live);
         wrote |= starArm(buffer, matrix, center, view, axisA.scale(-1.0), size * 1.42F * pulse, size * 0.08F, live);
         wrote |= starArm(buffer, matrix, center, view, axisB, size * 0.92F * pulse, size * 0.072F, live * 0.94F);
         wrote |= starArm(buffer, matrix, center, view, axisB.scale(-1.0), size * 0.92F * pulse, size * 0.072F, live * 0.94F);
         wrote |= starArm(buffer, matrix, center, view, axisC, size * 0.54F * pulse, size * 0.052F, live * 0.72F);
         wrote |= starArm(buffer, matrix, center, view, axisC.scale(-1.0), size * 0.54F * pulse, size * 0.052F, live * 0.72F);
         wrote |= starArm(buffer, matrix, center, view, axisD, size * 0.54F * pulse, size * 0.052F, live * 0.72F);
         wrote |= starArm(buffer, matrix, center, view, axisD.scale(-1.0), size * 0.54F * pulse, size * 0.052F, live * 0.72F);
         wrote |= softOrb(buffer, matrix, camera, center, size * 0.58, 255, 118, 12, live * 0.26F, 18);
         wrote |= softOrb(buffer, matrix, camera, center, size * 0.28, 255, 228, 112, live * 0.74F, 18);
         return wrote | softOrb(buffer, matrix, camera, center, size * 0.095, 255, 255, 255, live, 18);
      } else {
         return false;
      }
   }

   private static boolean starArm(BufferBuilder buffer, Matrix4f matrix, Vec3 center, Vec3 view, Vec3 direction, float length, float width, float live) {
      if (live <= 0.001F) {
         return false;
      }

      Vec3 dir = direction.lengthSqr() < 1.0E-8 ? new Vec3(1.0, 0.0, 0.0) : direction.normalize();
      Vec3 lateral = view.cross(dir);
      if (lateral.lengthSqr() < 1.0E-8) {
         lateral = safeSide(dir);
      } else {
         lateral = lateral.normalize();
      }

      Vec3 tip = center.add(dir.scale(length));
      boolean wrote = false;
      wrote |= softBand(buffer, matrix, center, tip, lateral, width * 3.3F, 0.0F, 255, 118, 12, live * 0.24F, 0.0F);
      wrote |= softBand(buffer, matrix, center, tip, lateral, width * 1.75F, 0.0F, 255, 228, 112, live * 0.68F, 0.0F);
      return wrote | softBand(buffer, matrix, center, tip, lateral, width * 0.62F, 0.0F, 255, 255, 248, live, 0.0F);
   }

   private static boolean violentBurstPetals(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float scale, float live, float time, long seed, int detail) {
      int petals = detail >= 3 ? 54 : (detail == 2 ? 38 : 24);
      boolean wrote = false;

      for (int i = 0; i < petals; i++) {
         float h0 = hash(seed, 38000 + i * 17);
         float h1 = hash(seed, 38100 + i * 19);
         float h2 = hash(seed, 38200 + i * 23);
         double yaw = i * (float) (Math.PI * 2) / petals + h0 * 0.44 + time * 0.035;
         double y = h1 * 1.5 - 0.38;
         Vec3 dir = new Vec3(Math.cos(yaw), y, Math.sin(yaw));
         if (dir.lengthSqr() < 1.0E-8) {
            dir = new Vec3(0.0, 1.0, 0.0);
         }

         dir = dir.normalize();
         Vec3 side = safeSide(dir);
         double near = scale * (0.5 + h2 * 0.85);
         double far = scale * (8.5 + h1 * 14.5) * (0.74 + live * 0.88);
         float width = scale * (0.28F + h2 * 0.48F) * live;
         Vec3 root = center.add(dir.scale(near));
         Vec3 tip = center.add(dir.scale(far));
         Vec3 l = root.subtract(side.scale(width));
         Vec3 r = root.add(side.scale(width));
         triGradient(buffer, matrix, l, r, tip, 108, 52, 4, live * 0.2F, live * 0.2F, 0.0F);
         Vec3 il = root.subtract(side.scale(width * 0.48F));
         Vec3 ir = root.add(side.scale(width * 0.48F));
         triGradient(buffer, matrix, il, ir, tip, 255, 184, 38, live * 0.52F, live * 0.52F, 0.0F);
         Vec3 wl = root.subtract(side.scale(width * 0.16F));
         Vec3 wr = root.add(side.scale(width * 0.16F));
         triGradient(buffer, matrix, wl, wr, tip, 255, 252, 226, live * 0.98F, live * 0.98F, 0.0F);
      }

      return wrote;
   }

   private static boolean burstRays(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float scale, float live, float time, long seed, int detail) {
      int count = detail >= 3 ? 42 : (detail == 2 ? 30 : 18);
      boolean wrote = false;

      for (int i = 0; i < count; i++) {
         float h0 = hash(seed, 21000 + i * 17);
         float h1 = hash(seed, 21100 + i * 19);
         float h2 = hash(seed, 21200 + i * 23);
         double yaw = h0 * (float) (Math.PI * 2) + time * 0.02;
         double z = h1 * 2.0 - 1.0;
         double xy = Math.sqrt(Math.max(0.0, 1.0 - z * z));
         Vec3 dir = new Vec3(Math.cos(yaw) * xy, z, Math.sin(yaw) * xy).normalize();
         Vec3 a = center.add(dir.scale(scale * (1.8 + h2 * 1.5)));
         Vec3 b = center.add(dir.scale(scale * (6.4 + h1 * 10.5) * (0.66 + live * 0.82)));
         Vec3 lateral = safeSide(dir);
         float alpha = live * (0.28F + h2 * 0.4F);
         wrote |= softBand(buffer, matrix, a, b, lateral, scale * (0.12F + h1 * 0.12F), 255, 184, 38, alpha * 0.38F, 0.0F);
         wrote |= softBand(buffer, matrix, a, b, lateral, scale * (0.044F + h2 * 0.06F), 255, 248, 210, alpha, 0.0F);
      }

      return wrote;
   }

   private static Vec3 sunCenter(Vec3 end, float scale) {
      return end.add(0.0, scale * 0.12, 0.0);
   }

   private static boolean auroraBeam(
      BufferBuilder buffer,
      Matrix4f matrix,
      Vec3 camera,
      Vec3 start,
      Vec3 end,
      float width,
      float live,
      float time,
      long seed,
      int detail,
      float waveStrength,
      float spinStrength,
      boolean violent
   ) {
      Vec3 axis = end.subtract(start);
      if (!(axis.lengthSqr() < 1.0E-8) && !(live <= 0.001F)) {
         double length = axis.length();
         Vec3 dir = axis.normalize();
         Vec3 baseA = safeSide(dir);
         Vec3 baseB = dir.cross(baseA).normalize();
         boolean wrote = false;
         int corePlanes = detail >= 3 ? 8 : (detail == 2 ? 6 : 4);

         for (int i = 0; i < corePlanes; i++) {
            double a = i * Math.PI / corePlanes + time * 0.08;
            Vec3 lateral = baseA.scale(Math.cos(a)).add(baseB.scale(Math.sin(a))).normalize();
            wrote |= softBand(buffer, matrix, start, end, lateral, width * 1.35F, 255, 118, 12, live * 0.055F, live * 0.055F);
            wrote |= softBand(
               buffer, matrix, start.add(dir.scale(0.02)), end.add(dir.scale(0.02)), lateral, width * 0.86F, 255, 184, 38, live * 0.12F, live * 0.12F
            );
            wrote |= softBand(
               buffer, matrix, start.add(dir.scale(0.04)), end.add(dir.scale(0.04)), lateral, width * 0.48F, 255, 228, 112, live * 0.28F, live * 0.28F
            );
            wrote |= softBand(
               buffer, matrix, start.add(dir.scale(0.06)), end.add(dir.scale(0.06)), lateral, width * 0.16F, 255, 255, 250, live * 0.82F, live * 0.82F
            );
         }

         int sheets = detail >= 3 ? (violent ? 28 : 22) : (detail == 2 ? (violent ? 20 : 15) : (violent ? 12 : 9));
         int segments = detail >= 3 ? (violent ? 34 : 28) : (detail == 2 ? 22 : 15);

         for (int sheet = 0; sheet < sheets; sheet++) {
            float h0 = hash(seed, 12000 + sheet * 31);
            float h1 = hash(seed, 12100 + sheet * 37);
            float h2 = hash(seed, 12200 + sheet * 41);
            double baseAngle = sheet * (float) (Math.PI * 2) / sheets + h0 * 0.34;
            double orbit = width * (0.1 + h1 * (violent ? 0.72 : 0.52));
            float localWidth = width * (0.08F + h2 * (violent ? 0.15F : 0.11F));
            float alpha = live * (0.12F + h1 * 0.24F);
            wrote |= auroraBeamSheet(
               buffer,
               matrix,
               camera,
               start,
               dir,
               baseA,
               baseB,
               length,
               baseAngle,
               orbit,
               localWidth,
               alpha,
               segments,
               time,
               seed ^ sheet * 40503L,
               waveStrength,
               spinStrength,
               sheet % 9 == 0
            );
         }

         return wrote;
      } else {
         return false;
      }
   }

   private static boolean auroraBeamSheet(
      BufferBuilder buffer,
      Matrix4f matrix,
      Vec3 camera,
      Vec3 start,
      Vec3 dir,
      Vec3 baseA,
      Vec3 baseB,
      double length,
      double baseAngle,
      double orbit,
      float width,
      float alpha,
      int segments,
      float time,
      long seed,
      float waveStrength,
      float spinStrength,
      boolean prism
   ) {
      boolean wrote = false;
      Vec3[] points = new Vec3[segments + 1];

      for (int i = 0; i <= segments; i++) {
         double u = (double)i / segments;
         float h = hash(seed, 13000 + i * 43);
         double envelope = Math.sin(u * Math.PI);
         double spin = baseAngle
            + u * (float) (Math.PI * 2) * (0.55 + spinStrength * 0.72)
            + time * (0.55 + spinStrength * 0.55) * (hash(seed, 13100) > 0.5F ? 1.0 : -1.0);
         Vec3 radial = baseA.scale(Math.cos(spin)).add(baseB.scale(Math.sin(spin))).normalize();
         Vec3 tangent = baseA.scale(-Math.sin(spin)).add(baseB.scale(Math.cos(spin))).normalize();
         double sway = Math.sin(time * (1.6 + waveStrength * 0.8) + u * (12.0 + waveStrength * 9.0) + seed * 7.0E-5) * orbit * waveStrength * 0.42 * envelope;
         double micro = (h - 0.5) * orbit * 0.16 * envelope;
         points[i] = start.add(dir.scale(length * u)).add(radial.scale(orbit + sway)).add(tangent.scale(micro));
      }

      int[] c = prism ? prismColor(hash(seed, 13200) + time * 0.025F) : new int[]{255, 184, 38};
      wrote |= drawSoftPolyline(buffer, matrix, camera, points, width * 1.95F, 255, 118, 12, alpha * 0.22F);
      wrote |= drawSoftPolyline(buffer, matrix, camera, points, width * 1.35F, c[0], prism ? c[1] : 184, prism ? c[2] : 38, alpha * 0.4F);
      wrote |= drawSoftPolyline(buffer, matrix, camera, points, width * 0.8F, 255, 228, 112, alpha * 0.7F);
      return wrote | drawSoftPolyline(buffer, matrix, camera, points, width * 0.3F, 255, 248, 210, alpha * 0.78F);
   }

   private static boolean goldenAuroraCurve(
      BufferBuilder buffer,
      Matrix4f matrix,
      Vec3 camera,
      Vec3 p0,
      Vec3 p1,
      Vec3 p2,
      Vec3 p3,
      float width,
      float alpha,
      int segments,
      float time,
      long seed,
      boolean prism
   ) {
      if (alpha <= 0.001F) {
         return false;
      }

      Vec3[] points = new Vec3[segments + 1];

      for (int i = 0; i <= segments; i++) {
         double t = (double)i / segments;
         double inv = 1.0 - t;
         Vec3 p = p0.scale(inv * inv * inv).add(p1.scale(3.0 * inv * inv * t)).add(p2.scale(3.0 * inv * t * t)).add(p3.scale(t * t * t));
         double wave = Math.sin(time * (1.8 + hash(seed, 14000) * 1.4) + t * (10.0 + hash(seed, 14100) * 8.0) + seed * 1.0E-4);
         Vec3 tangent = safeSide(p3.subtract(p0));
         p = p.add(tangent.scale(wave * width * 0.42 * Math.sin(t * Math.PI)));
         points[i] = p;
      }

      int[] c = prism ? prismColor(hash(seed, 14200) + time * 0.025F) : new int[]{255, 184, 38};
      boolean wrote = false;
      wrote |= drawSoftPolyline(buffer, matrix, camera, points, width * 1.95F, 255, 118, 12, alpha * 0.22F);
      wrote |= drawSoftPolyline(buffer, matrix, camera, points, width * 1.35F, c[0], prism ? c[1] : 184, prism ? c[2] : 38, alpha * 0.4F);
      wrote |= drawSoftPolyline(buffer, matrix, camera, points, width * 0.8F, 255, 228, 112, alpha * 0.7F);
      return wrote | drawSoftPolyline(buffer, matrix, camera, points, width * 0.3F, 255, 248, 210, alpha * 0.82F);
   }

   private static boolean drawSoftPolyline(BufferBuilder buffer, Matrix4f matrix, Vec3 camera, Vec3[] points, float width, int r, int g, int b, float alpha) {
      if (!(alpha <= 0.001F) && points.length >= 2) {
         boolean wrote = false;

         for (int i = 1; i < points.length; i++) {
            float u0 = (float)(i - 1) / (points.length - 1);
            float u1 = (float)i / (points.length - 1);
            float e0 = (float)Math.sin(u0 * Math.PI);
            float e1 = (float)Math.sin(u1 * Math.PI);
            Vec3 a = points[i - 1];
            Vec3 c = points[i];
            Vec3 dir = c.subtract(a);
            if (!(dir.lengthSqr() < 1.0E-8)) {
               Vec3 view = camera.subtract(a.add(c).scale(0.5));
               Vec3 lateral = dir.cross(view);
               if (lateral.lengthSqr() < 1.0E-8) {
                  lateral = safeSide(dir.normalize());
               } else {
                  lateral = lateral.normalize();
               }

               wrote |= softBand(buffer, matrix, a, c, lateral, width * (0.5F + e0 * 0.5F), width * (0.5F + e1 * 0.5F), r, g, b, alpha * e0, alpha * e1);
            }
         }

         return wrote;
      } else {
         return false;
      }
   }

   private static boolean beamMotes(
      BufferBuilder buffer, Matrix4f matrix, Vec3 camera, Vec3 start, Vec3 end, float scale, float live, float time, long seed, int detail, boolean violent
   ) {
      Vec3 axis = end.subtract(start);
      if (axis.lengthSqr() < 1.0E-8) {
         return false;
      }

      Vec3 dir = axis.normalize();
      Vec3 side = safeSide(dir);
      Vec3 up = dir.cross(side).normalize();
      int count = detail >= 3 ? (violent ? 150 : 96) : (detail == 2 ? (violent ? 96 : 62) : (violent ? 54 : 34));
      boolean wrote = false;

      for (int i = 0; i < count; i++) {
         float h0 = hash(seed, 15000 + i * 17);
         float h1 = hash(seed, 15100 + i * 19);
         float h2 = hash(seed, 15200 + i * 23);
         double u = (i + h0 * 0.84) / count;
         Vec3 c = start.lerp(end, u);
         double a = h1 * (float) (Math.PI * 2) + time * (0.5 + h2 * (violent ? 1.6 : 0.8));
         double radial = scale * (0.15 + h0 * (violent ? 2.2 : 1.2));
         Vec3 p = c.add(side.scale(Math.cos(a) * radial)).add(up.scale(Math.sin(a) * radial));
         float blink = 0.22F + 0.78F * pulse01(time * (6.5F + h1 * 7.0F) + i * 0.57F);
         wrote |= goldMote(buffer, matrix, camera, p, scale * (0.01F + h2 * 0.03F), live * blink * (violent ? 0.6F : 0.48F), i % 23 == 0 ? h0 : -1.0F);
      }

      return wrote;
   }

   private static boolean orbShockwave(
      BufferBuilder buffer, Matrix4f matrix, Vec3 camera, Vec3 center, double radius, float scale, float live, float time, long seed, int detail
   ) {
      int count = detail >= 3 ? 72 : (detail == 2 ? 48 : 30);
      boolean wrote = false;

      for (int i = 0; i < count; i++) {
         float h = hash(seed, 16000 + i * 29);
         double a = i * (float) (Math.PI * 2) / count + (h - 0.5) * 0.045;
         Vec3 p = center.add(Math.cos(a) * radius, Math.sin(time * 2.2 + i) * scale * 0.025, Math.sin(a) * radius);
         float size = scale * (0.018F + h * 0.025F);
         wrote |= softOrb(buffer, matrix, camera, p, size * 3.2, 255, 118, 12, live * 0.12F, 10);
         wrote |= softOrb(buffer, matrix, camera, p, size * 1.5, 255, 228, 112, live * 0.55F, 10);
      }

      return wrote;
   }

   private static boolean bloomOrb(BufferBuilder buffer, Matrix4f matrix, Vec3 camera, Vec3 center, float size, float alpha, boolean whiteCore) {
      boolean wrote = false;
      wrote |= softOrb(buffer, matrix, camera, center, size * 3.1, 255, 118, 12, alpha * 0.08F, 16);
      wrote |= softOrb(buffer, matrix, camera, center, size * 2.0, 255, 184, 38, alpha * 0.16F, 16);
      wrote |= softOrb(buffer, matrix, camera, center, size * 1.15, 255, 228, 112, alpha * 0.34F, 16);
      wrote |= softOrb(buffer, matrix, camera, center, size * 0.58, 255, 248, 210, alpha * 0.66F, 16);
      if (whiteCore) {
         wrote |= softOrb(buffer, matrix, camera, center, size * 0.24, 255, 255, 255, alpha, 16);
      }

      return wrote;
   }

   private static boolean goldMote(BufferBuilder buffer, Matrix4f matrix, Vec3 camera, Vec3 center, float size, float alpha, float prism) {
      boolean wrote = false;
      int r = 255;
      int g = 228;
      int b = 112;
      if (prism >= 0.0F) {
         int[] c = prismColor(prism);
         r = c[0];
         g = c[1];
         b = c[2];
      }

      wrote |= softOrb(buffer, matrix, camera, center, size * 3.0, 255, 118, 12, alpha * 0.12F, 10);
      wrote |= softOrb(buffer, matrix, camera, center, size * 1.45, r, g, b, alpha * 0.58F, 10);
      return wrote | softOrb(buffer, matrix, camera, center, size * 0.52, 255, 255, 242, alpha, 10);
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
         return softDisk(buffer, matrix, center, right, up, radius, r, g, b, alpha, segments);
      } else {
         return false;
      }
   }

   private static boolean softDisk(
      BufferBuilder buffer, Matrix4f matrix, Vec3 center, Vec3 axisA, Vec3 axisB, double radius, int r, int g, int b, float alpha, int segments
   ) {
      if (!(alpha <= 0.001F) && !(radius <= 0.001)) {
         int count = Math.max(10, segments);

         for (int i = 0; i < count; i++) {
            double a0 = i * (float) (Math.PI * 2) / count;
            double a1 = (i + 1.0) * (float) (Math.PI * 2) / count;
            Vec3 p0 = center.add(axisA.scale(Math.cos(a0) * radius)).add(axisB.scale(Math.sin(a0) * radius));
            Vec3 p1 = center.add(axisA.scale(Math.cos(a1) * radius)).add(axisB.scale(Math.sin(a1) * radius));
            triGradient(buffer, matrix, center, p0, p1, r, g, b, alpha, 0.0F, 0.0F);
         }

         return true;
      } else {
         return false;
      }
   }

   private static boolean softBand(
      BufferBuilder buffer, Matrix4f matrix, Vec3 start, Vec3 end, Vec3 lateral, float width, int r, int g, int b, float startAlpha, float endAlpha
   ) {
      return softBand(buffer, matrix, start, end, lateral, width, width, r, g, b, startAlpha, endAlpha);
   }

   private static boolean softBand(
      BufferBuilder buffer,
      Matrix4f matrix,
      Vec3 start,
      Vec3 end,
      Vec3 lateral,
      float startWidth,
      float endWidth,
      int r,
      int g,
      int b,
      float startAlpha,
      float endAlpha
   ) {
      if (startAlpha <= 0.001F && endAlpha <= 0.001F) {
         return false;
      }

      Vec3 n = lateral.lengthSqr() < 1.0E-8 ? safeSide(end.subtract(start).normalize()) : lateral.normalize();
      Vec3 sl = start.subtract(n.scale(startWidth));
      Vec3 sr = start.add(n.scale(startWidth));
      Vec3 el = end.subtract(n.scale(endWidth));
      Vec3 er = end.add(n.scale(endWidth));
      triGradient(buffer, matrix, sl, start, end, r, g, b, 0.0F, startAlpha, endAlpha);
      triGradient(buffer, matrix, sl, end, el, r, g, b, 0.0F, endAlpha, 0.0F);
      triGradient(buffer, matrix, start, sr, er, r, g, b, startAlpha, 0.0F, 0.0F);
      triGradient(buffer, matrix, start, er, end, r, g, b, startAlpha, 0.0F, endAlpha);
      return true;
   }

   private static int detailLevel(Vec3 camera, Vec3 center) {
      double distance = camera.distanceTo(center);
      if (distance < 150.0) {
         return 3;
      } else {
         return distance < 310.0 ? 2 : 1;
      }
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

   private static float impactEnvelope(float age, float center, float width) {
      float d = Math.abs(age - center) / Math.max(0.001F, width);
      return d >= 1.0F ? 0.0F : smoother(1.0F - d);
   }

   private static float smoother(float value) {
      value = Mth.clamp(value, 0.0F, 1.0F);
      return value * value * value * (value * (value * 6.0F - 15.0F) + 10.0F);
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

   private static int[] prismColor(float phase) {
      float p = phase - Mth.floor(phase);
      if (p < 0.1667F) {
         return new int[]{255, 92, 178};
      } else if (p < 0.3334F) {
         return new int[]{255, 148, 76};
      } else if (p < 0.5001F) {
         return new int[]{255, 230, 116};
      } else if (p < 0.6668F) {
         return new int[]{132, 226, 255};
      } else {
         return p < 0.8335F ? new int[]{146, 116, 255} : new int[]{255, 102, 228};
      }
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

   private static final class Cast {
      private final ArthurAvalonVfxData payload;
      private final long created;
      private final float durationTicks;
      private boolean beamShake;
      private boolean slamSweepShake;
      private boolean impactShake;
      private boolean burstShake;
      private boolean burstAftershockShake;
      private boolean finalBeamShake;

      private Cast(ArthurAvalonVfxData payload, long created) {
         this.payload = payload;
         this.created = created;
         this.durationTicks = Math.max(2000.0F, payload.durationTicks());
      }

      private float ageTicks(long now) {
         return Math.max(0.0F, (float)(now - this.created) / 50.0F);
      }

      private boolean dead(float age) {
         return age > this.durationTicks + 14.0F;
      }

      private void updateShake(Minecraft minecraft, float age) {
         if (minecraft.player != null) {
            double distance = Math.min(
               minecraft.player.getEyePosition().distanceTo(this.payload.start()), minecraft.player.getEyePosition().distanceTo(this.payload.end())
            );
            if (!(distance > 430.0)) {
               float proximity = 1.0F - Mth.clamp((float)(distance / 430.0), 0.0F, 1.0F);
               if (!this.beamShake && age >= 108.0F) {
                  this.beamShake = true;
                  PathsClientState.triggerManualShake(26, 6.8F + proximity * 8.6F, 12.4F + proximity * 5.2F);
               }

               if (!this.slamSweepShake && age >= 241.0F) {
                  this.slamSweepShake = true;
                  PathsClientState.triggerManualShake(26, 7.4F + proximity * 8.8F, 12.8F + proximity * 4.6F);
               }

               if (!this.impactShake && age >= 285.2F) {
                  this.impactShake = true;
                  PathsClientState.triggerManualShake(34, 8.6F + proximity * 10.4F, 13.8F + proximity * 5.4F);
               }

               if (!this.burstShake && age >= 365.4F) {
                  this.burstShake = true;
                  PathsClientState.triggerManualShake(60, 13.2F + proximity * 16.0F, 14.56F + proximity * 6.72F);
               }

               if (!this.burstAftershockShake && age >= 377.2F) {
                  this.burstAftershockShake = true;
                  PathsClientState.triggerManualShake(32, 9.4F + proximity * 10.8F, 13.8F + proximity * 5.2F);
               }

               if (!this.finalBeamShake && age >= 378.8F) {
                  this.finalBeamShake = true;
                  PathsClientState.triggerManualShake(44, 12.4F + proximity * 14.8F, 15.2F + proximity * 6.4F);
               }
            }
         }
      }
   }
}
