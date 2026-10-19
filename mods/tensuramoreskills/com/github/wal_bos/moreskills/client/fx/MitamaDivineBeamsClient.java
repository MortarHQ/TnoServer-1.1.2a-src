package com.github.wal_bos.moreskills.client.fx;

import com.mojang.blaze3d.platform.GlStateManager.DestFactor;
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
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

public final class MitamaDivineBeamsClient {
   private static final Map<Integer, MitamaDivineBeamsClient.Volley> ACTIVE = new ConcurrentHashMap<>();
   private static boolean initialized;

   private MitamaDivineBeamsClient() {
   }

   public static void init() {
      if (!initialized) {
         initialized = true;
         NeoForge.EVENT_BUS.addListener(MitamaDivineBeamsClient::clientTick);
         NeoForge.EVENT_BUS.addListener(MitamaDivineBeamsClient::renderLevel);
      }
   }

   public static void play(
      int id,
      int casterId,
      int targetId,
      double casterX,
      double casterY,
      double casterZ,
      double targetX,
      double targetY,
      double targetZ,
      int beams,
      int seed,
      int duration
   ) {
      init();
      ACTIVE.put(
         id,
         new MitamaDivineBeamsClient.Volley(
            casterId,
            targetId,
            new Vec3(casterX, casterY, casterZ),
            new Vec3(targetX, targetY, targetZ),
            Mth.clamp(beams, 1, 128),
            seed,
            Mth.clamp(duration, 90, 190)
         )
      );
   }

   private static void clientTick(Post event) {
      Minecraft mc = Minecraft.getInstance();
      if (mc.level == null) {
         ACTIVE.clear();
      } else {
         Iterator<Entry<Integer, MitamaDivineBeamsClient.Volley>> iterator = ACTIVE.entrySet().iterator();

         while (iterator.hasNext()) {
            MitamaDivineBeamsClient.Volley volley = iterator.next().getValue();
            volley.tick(mc);
            if (volley.age > volley.life) {
               iterator.remove();
            }
         }
      }
   }

   private static void renderLevel(RenderLevelStageEvent event) {
      if (event.getStage() == Stage.AFTER_PARTICLES) {
         Minecraft mc = Minecraft.getInstance();
         if (mc.level != null && !ACTIVE.isEmpty()) {
            PoseStack poseStack = event.getPoseStack();
            Camera camera = mc.gameRenderer.getMainCamera();
            Vec3 cam = camera.getPosition();
            Matrix4f matrix = poseStack.last().pose();
            float partial = event.getPartialTick().getGameTimeDeltaPartialTick(false);
            RenderSystem.enableBlend();
            RenderSystem.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
            RenderSystem.disableCull();
            RenderSystem.depthMask(false);
            RenderSystem.setShader(GameRenderer::getPositionColorShader);
            BufferBuilder buffer = Tesselator.getInstance().begin(Mode.TRIANGLES, DefaultVertexFormat.POSITION_COLOR);
            boolean wrote = false;

            for (MitamaDivineBeamsClient.Volley volley : ACTIVE.values()) {
               wrote |= volley.render(buffer, matrix, cam, partial);
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

   private static final class Volley {
      private static final int FIRE_START = 3;
      private static final int FIRE_SPACING = 0;
      private static final int TRAIL_POINTS = 46;
      private static final int BEAM_LIFE = 86;
      private final int casterId;
      private final int targetId;
      private final Vec3 fallbackCaster;
      private final Vec3 fallbackTarget;
      private final int beams;
      private final int seed;
      private final int life;
      private final MitamaDivineBeamsClient.Volley.Beam[] beamArray;
      private int age;

      private Volley(int casterId, int targetId, Vec3 fallbackCaster, Vec3 fallbackTarget, int beams, int seed, int life) {
         this.casterId = casterId;
         this.targetId = targetId;
         this.fallbackCaster = fallbackCaster;
         this.fallbackTarget = fallbackTarget;
         this.beams = beams;
         this.seed = seed;
         this.life = life;
         this.beamArray = new MitamaDivineBeamsClient.Volley.Beam[beams];

         for (int i = 0; i < beams; i++) {
            this.beamArray[i] = new MitamaDivineBeamsClient.Volley.Beam();
         }
      }

      private void tick(Minecraft mc) {
         this.age++;
         Vec3 caster = this.resolveCaster(mc, 1.0F);
         Vec3 target = this.resolveTarget(mc, 1.0F);
         MitamaDivineBeamsClient.Volley.Basis basis = this.basis(caster, target);

         for (int i = 0; i < this.beams; i++) {
            MitamaDivineBeamsClient.Volley.Beam beam = this.beamArray[i];
            int fireStart = 3 + i * 0;
            if (this.age >= fireStart) {
               Vec3 socket = this.socket(caster, basis, this.age, i);
               Vec3 trueTarget = target.add(basis.right.scale((this.hash(this.seed, i + 20) - 0.5F) * 0.34))
                  .add(basis.up.scale((this.hash(this.seed, i + 50) - 0.5F) * 0.3));
               if (!beam.active) {
                  beam.active = true;
                  beam.age = 0;
                  beam.pos = socket;
                  beam.prevPos = socket;
                  beam.smoothTarget = trueTarget;
                  beam.prevSmoothTarget = trueTarget;
                  double spreadPower = this.spreadPower();
                  Vec3 outward = this.radialOut(basis, i, this.age).scale(2.35 + spreadPower * 0.26);
                  beam.vel = outward.add(basis.forward.scale(0.38));

                  for (int j = 0; j < 46; j++) {
                     beam.trail[j] = socket;
                  }
               } else {
                  beam.age++;
                  beam.prevPos = beam.pos;
                  beam.prevSmoothTarget = beam.smoothTarget;
                  double targetLerp = beam.age < 14 ? 0.16 : 0.34;
                  beam.smoothTarget = beam.smoothTarget.lerp(trueTarget, targetLerp);
                  if (beam.burstAge > 0) {
                     beam.burstAge++;
                     beam.pos = beam.pos.add(beam.vel.scale(0.08));
                     beam.vel = beam.vel.scale(0.64);
                     beam.pushTrail(beam.pos);
                  } else if (beam.age > 86) {
                     beam.burstAge = 1;
                     beam.missed = true;
                  } else {
                     Vec3 desired = beam.smoothTarget.subtract(beam.pos);
                     double dist = desired.length();
                     if (dist < 0.8) {
                        beam.burstAge = 1;
                        beam.impactPos = beam.smoothTarget;
                     } else {
                        double curve = Mth.clamp((beam.age - 4.0) / 24.0, 0.0, 1.0);
                        double hardTrack = Mth.clamp((beam.age - 22.0) / 22.0, 0.0, 1.0);
                        Vec3 desiredDir = desired.normalize();
                        Vec3 currentDir = beam.vel.lengthSqr() > 1.0E-4 ? beam.vel.normalize() : desiredDir;
                        Vec3 steerDir = currentDir.lerp(desiredDir, 0.18 + hardTrack * 0.42).normalize();
                        Vec3 accel = steerDir.scale(0.07 + curve * 0.135 + hardTrack * 0.08);
                        beam.vel = beam.vel.scale(0.905).add(accel);
                        double speed = beam.vel.length();
                        double maxSpeed = 1.52 + Math.min(0.95, beam.age * 0.014);
                        if (speed > maxSpeed) {
                           beam.vel = beam.vel.normalize().scale(maxSpeed);
                        }

                        beam.pos = beam.pos.add(beam.vel);
                        beam.pushTrail(beam.pos);
                     }
                  }
               }
            }
         }
      }

      private boolean render(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, float partial) {
         Vec3 caster = this.resolveCaster(Minecraft.getInstance(), partial);
         Vec3 target = this.resolveTarget(Minecraft.getInstance(), partial);
         MitamaDivineBeamsClient.Volley.Basis basis = this.basis(caster, target);
         boolean wrote = false;
         float warmup = Mth.clamp(this.age / 6.0F, 0.0F, 1.0F);
         float fade = Mth.clamp((this.life - this.age) / 24.0F, 0.0F, 1.0F);
         float alpha = warmup * fade;
         double spreadPower = this.spreadPower();
         wrote |= this.renderBlindingCasterBloom(buffer, matrix, cam, caster, basis, alpha);
         wrote |= this.renderHalo(buffer, matrix, cam, caster, basis, 2.55 + spreadPower * 0.17, alpha);

         for (int i = 0; i < this.beams; i++) {
            MitamaDivineBeamsClient.Volley.Beam beam = this.beamArray[i];
            if (!beam.active) {
               Vec3 socket = this.socket(caster, basis, this.age + partial, i);
               wrote |= this.renderCharge(buffer, matrix, cam, socket, this.radialOut(basis, i, this.age + partial), 0.68 + spreadPower * 0.024, alpha);
            } else {
               float beamAlpha = alpha;
               if (beam.burstAge > 0) {
                  beamAlpha *= Mth.clamp(1.0F - beam.burstAge / 22.0F, 0.0F, 1.0F);
               }

               Vec3 current = beam.prevPos.lerp(beam.pos, partial);
               Vec3 smoothTarget = beam.prevSmoothTarget.lerp(beam.smoothTarget, partial);
               wrote |= this.renderTrail(buffer, matrix, cam, beam, current, beamAlpha);
               wrote |= this.renderBeamHead(buffer, matrix, cam, current, smoothTarget, basis, 0.9 + spreadPower * 0.014, beamAlpha);
               wrote |= this.renderTargetTetherGlow(buffer, matrix, cam, current, smoothTarget, beamAlpha * 0.12F);
               if (beam.burstAge > 0 && beam.impactPos != null && !beam.missed) {
                  wrote |= this.renderImpact(buffer, matrix, cam, beam.impactPos, basis, beamAlpha);
               }
            }
         }

         return wrote;
      }

      private Vec3 resolveCaster(Minecraft mc, float partial) {
         if (mc.level != null) {
            Entity entity = mc.level.getEntity(this.casterId);
            if (entity != null) {
               return entity.getPosition(partial).add(0.0, entity.getBbHeight() * 0.64, 0.0);
            }
         }

         return this.fallbackCaster;
      }

      private Vec3 resolveTarget(Minecraft mc, float partial) {
         if (mc.level != null) {
            Entity entity = mc.level.getEntity(this.targetId);
            if (entity != null) {
               return entity.getPosition(partial).add(0.0, entity.getBbHeight() * 0.55, 0.0);
            }
         }

         return this.fallbackTarget;
      }

      private double spreadPower() {
         return Math.sqrt(Math.max(1.0, this.beams));
      }

      private Vec3 socket(Vec3 caster, MitamaDivineBeamsClient.Volley.Basis basis, double t, int index) {
         double spreadPower = this.spreadPower();
         double angle = index * 2.399963229728653 + this.seed * 3.1E-4 + t * 0.03;
         double ring = 1.9 + spreadPower * 0.22 + index % 7 * 0.34 + this.hash(this.seed, index + 4) * 0.44;
         double wave = Math.sin(t * 0.12 + index) * (0.26 + spreadPower * 0.02);
         return caster.add(basis.right.scale(Math.cos(angle) * ring)).add(basis.up.scale(Math.sin(angle) * ring + wave)).subtract(basis.forward.scale(0.26));
      }

      private Vec3 radialOut(MitamaDivineBeamsClient.Volley.Basis basis, int index, double t) {
         double angle = index * 2.399963229728653 + this.seed * 3.1E-4 + t * 0.014;
         return basis.right.scale(Math.cos(angle)).add(basis.up.scale(Math.sin(angle))).normalize();
      }

      private boolean renderBlindingCasterBloom(
         BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 center, MitamaDivineBeamsClient.Volley.Basis basis, float alpha
      ) {
         boolean wrote = false;
         double pulse = 1.0 + Math.sin(this.age * 0.4) * 0.1;
         wrote |= this.glowDiamond(buffer, matrix, cam, center, basis, 11.0 * pulse, 255, 132, 0, alpha * 0.035F);
         wrote |= this.glowDiamond(buffer, matrix, cam, center, basis, 8.2 * pulse, 255, 168, 0, alpha * 0.06F);
         wrote |= this.glowDiamond(buffer, matrix, cam, center, basis, 5.8 * pulse, 255, 205, 35, alpha * 0.105F);
         wrote |= this.glowDiamond(buffer, matrix, cam, center, basis, 3.7 * pulse, 255, 238, 105, alpha * 0.205F);
         wrote |= this.glowDiamond(buffer, matrix, cam, center, basis, 2.2 * pulse, 255, 255, 226, alpha * 0.42F);
         return wrote | this.glowDiamond(buffer, matrix, cam, center, basis, 1.0 * pulse, 255, 255, 255, alpha * 0.92F);
      }

      private boolean renderHalo(
         BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 center, MitamaDivineBeamsClient.Volley.Basis basis, double radius, float alpha
      ) {
         boolean wrote = false;
         wrote |= this.ring(buffer, matrix, cam, center, basis, radius * 1.95, 0.7, 255, 120, 0, alpha * 0.052F);
         wrote |= this.ring(buffer, matrix, cam, center, basis, radius * 1.62, 0.54, 255, 162, 0, alpha * 0.085F);
         wrote |= this.ring(buffer, matrix, cam, center, basis, radius * 1.34, 0.42, 255, 202, 24, alpha * 0.15F);
         wrote |= this.ring(buffer, matrix, cam, center, basis, radius * 1.04, 0.3, 255, 230, 74, alpha * 0.285F);
         wrote |= this.ring(buffer, matrix, cam, center, basis, radius * 0.74, 0.18, 255, 255, 210, alpha * 0.51F);
         wrote |= this.ring(buffer, matrix, cam, center, basis, radius * 0.42, 0.11, 255, 255, 255, alpha * 0.86F);

         for (int i = 0; i < 42; i++) {
            double a = i * Math.PI * 2.0 / 42.0 + this.age * 0.068;
            Vec3 p0 = center.add(basis.right.scale(Math.cos(a) * radius * 0.18)).add(basis.up.scale(Math.sin(a) * radius * 0.18));
            Vec3 p1 = center.add(basis.right.scale(Math.cos(a) * radius * 2.35)).add(basis.up.scale(Math.sin(a) * radius * 2.35));
            wrote |= this.beamQuad(buffer, matrix, cam, p0, p1, 0.26, 255, 160, 0, alpha * 0.08F);
            wrote |= this.beamQuad(buffer, matrix, cam, p0, p1, 0.14, 255, 216, 42, alpha * 0.23F);
            wrote |= this.beamQuad(buffer, matrix, cam, p0, p1, 0.055, 255, 255, 230, alpha * 0.62F);
         }

         return wrote;
      }

      private boolean ring(
         BufferBuilder buffer,
         Matrix4f matrix,
         Vec3 cam,
         Vec3 center,
         MitamaDivineBeamsClient.Volley.Basis basis,
         double radius,
         double thickness,
         int r,
         int g,
         int b,
         float alpha
      ) {
         boolean wrote = false;
         int segments = 112;

         for (int i = 0; i < segments; i++) {
            double a0 = i * Math.PI * 2.0 / segments;
            double a1 = (i + 1) * Math.PI * 2.0 / segments;
            Vec3 p0 = center.add(basis.right.scale(Math.cos(a0) * radius)).add(basis.up.scale(Math.sin(a0) * radius));
            Vec3 p1 = center.add(basis.right.scale(Math.cos(a1) * radius)).add(basis.up.scale(Math.sin(a1) * radius));
            Vec3 q0 = center.add(basis.right.scale(Math.cos(a0) * (radius + thickness))).add(basis.up.scale(Math.sin(a0) * (radius + thickness)));
            Vec3 q1 = center.add(basis.right.scale(Math.cos(a1) * (radius + thickness))).add(basis.up.scale(Math.sin(a1) * (radius + thickness)));
            wrote |= this.quad(buffer, matrix, cam, p0, p1, q1, q0, r, g, b, alpha);
         }

         return wrote;
      }

      private boolean renderCharge(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 center, Vec3 outward, double size, float alpha) {
         Vec3 start = center.subtract(outward.scale(size * 1.4));
         Vec3 end = center.add(outward.scale(size * 4.4));
         boolean wrote = this.beamQuad(buffer, matrix, cam, start, end, size * 2.4, 255, 110, 0, alpha * 0.035F);
         wrote |= this.beamQuad(buffer, matrix, cam, start, end, size * 1.72, 255, 155, 0, alpha * 0.07F);
         wrote |= this.beamQuad(buffer, matrix, cam, start, end, size * 1.12, 255, 205, 20, alpha * 0.17F);
         wrote |= this.beamQuad(buffer, matrix, cam, start, end, size * 0.66, 255, 240, 96, alpha * 0.38F);
         wrote |= this.beamQuad(buffer, matrix, cam, start, end, size * 0.3, 255, 255, 224, alpha * 0.8F);
         return wrote | this.beamQuad(buffer, matrix, cam, start, end, size * 0.11, 255, 255, 255, alpha);
      }

      private boolean renderTrail(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, MitamaDivineBeamsClient.Volley.Beam beam, Vec3 current, float alpha) {
         boolean wrote = false;
         Vec3 previous = current;
         double spreadPower = this.spreadPower();

         for (int i = 0; i < beam.trail.length; i++) {
            Vec3 point = beam.trail[i];
            if (point != null) {
               float f = Mth.clamp(1.0F - (float)i / beam.trail.length, 0.0F, 1.0F);
               float localAlpha = alpha * f;
               double width = (0.82 + spreadPower * 0.012) * Mth.clamp(1.0F - i / 50.0F, 0.14F, 1.0F);
               wrote |= this.beamQuad(buffer, matrix, cam, previous, point, width * 7.1, 255, 92, 0, localAlpha * 0.012F);
               wrote |= this.beamQuad(buffer, matrix, cam, previous, point, width * 5.35, 255, 125, 0, localAlpha * 0.024F);
               wrote |= this.beamQuad(buffer, matrix, cam, previous, point, width * 3.8, 255, 165, 0, localAlpha * 0.05F);
               wrote |= this.beamQuad(buffer, matrix, cam, previous, point, width * 2.55, 255, 206, 25, localAlpha * 0.115F);
               wrote |= this.beamQuad(buffer, matrix, cam, previous, point, width * 1.55, 255, 234, 82, localAlpha * 0.28F);
               wrote |= this.beamQuad(buffer, matrix, cam, previous, point, width * 0.82, 255, 252, 176, localAlpha * 0.63F);
               wrote |= this.beamQuad(buffer, matrix, cam, previous, point, width * 0.32, 255, 255, 255, localAlpha);
               previous = point;
            }
         }

         return wrote;
      }

      private boolean renderBeamHead(
         BufferBuilder buffer,
         Matrix4f matrix,
         Vec3 cam,
         Vec3 center,
         Vec3 target,
         MitamaDivineBeamsClient.Volley.Basis fallbackBasis,
         double size,
         float alpha
      ) {
         Vec3 forward = target.subtract(center);
         MitamaDivineBeamsClient.Volley.Basis basis = forward.lengthSqr() > 1.0E-4 ? this.basis(center, target) : fallbackBasis;
         boolean wrote = this.glowDiamond(buffer, matrix, cam, center, basis, size * 7.4, 255, 96, 0, alpha * 0.026F);
         wrote |= this.glowDiamond(buffer, matrix, cam, center, basis, size * 5.2, 255, 138, 0, alpha * 0.054F);
         wrote |= this.glowDiamond(buffer, matrix, cam, center, basis, size * 3.5, 255, 188, 12, alpha * 0.12F);
         wrote |= this.glowDiamond(buffer, matrix, cam, center, basis, size * 2.2, 255, 228, 62, alpha * 0.29F);
         wrote |= this.glowDiamond(buffer, matrix, cam, center, basis, size * 1.2, 255, 255, 185, alpha * 0.72F);
         wrote |= this.glowDiamond(buffer, matrix, cam, center, basis, size * 0.46, 255, 255, 255, alpha);
         Vec3 tip = center.add(basis.forward.scale(size * 1.65));
         Vec3 back = center.subtract(basis.forward.scale(size * 0.85));
         wrote |= this.beamQuad(buffer, matrix, cam, back, tip, size * 1.25, 255, 230, 68, alpha * 0.3F);
         return wrote | this.beamQuad(buffer, matrix, cam, back, tip, size * 0.38, 255, 255, 255, alpha);
      }

      private boolean renderTargetTetherGlow(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 current, Vec3 target, float alpha) {
         double dist = current.distanceTo(target);
         if (dist < 2.0) {
            return false;
         }

         Vec3 end = current.lerp(target, 0.34);
         boolean wrote = this.beamQuad(buffer, matrix, cam, current, end, 0.58, 255, 190, 0, alpha * 0.16F);
         return wrote | this.beamQuad(buffer, matrix, cam, current, end, 0.22, 255, 255, 230, alpha * 0.55F);
      }

      private boolean glowDiamond(
         BufferBuilder buffer,
         Matrix4f matrix,
         Vec3 cam,
         Vec3 center,
         MitamaDivineBeamsClient.Volley.Basis basis,
         double size,
         int r,
         int g,
         int b,
         float alpha
      ) {
         Vec3 a = center.add(basis.right.scale(size));
         Vec3 b0 = center.add(basis.up.scale(size));
         Vec3 c = center.subtract(basis.right.scale(size));
         Vec3 d = center.subtract(basis.up.scale(size));
         return this.quad(buffer, matrix, cam, a, b0, c, d, r, g, b, alpha);
      }

      private boolean renderImpact(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 center, MitamaDivineBeamsClient.Volley.Basis basis, float alpha) {
         boolean wrote = false;
         wrote |= this.glowDiamond(buffer, matrix, cam, center, basis, 6.2, 255, 100, 0, alpha * 0.042F);
         wrote |= this.glowDiamond(buffer, matrix, cam, center, basis, 4.2, 255, 166, 0, alpha * 0.085F);
         wrote |= this.glowDiamond(buffer, matrix, cam, center, basis, 2.8, 255, 218, 36, alpha * 0.2F);
         wrote |= this.glowDiamond(buffer, matrix, cam, center, basis, 1.55, 255, 255, 205, alpha * 0.76F);
         wrote |= this.glowDiamond(buffer, matrix, cam, center, basis, 0.7, 255, 255, 255, alpha);

         for (int i = 0; i < 28; i++) {
            double a = i * Math.PI * 2.0 / 28.0 + this.age * 0.08;
            Vec3 dir = basis.right.scale(Math.cos(a)).add(basis.up.scale(Math.sin(a))).normalize();
            Vec3 p0 = center.add(dir.scale(0.05));
            Vec3 p1 = center.add(dir.scale(4.1));
            wrote |= this.beamQuad(buffer, matrix, cam, p0, p1, 0.66, 255, 115, 0, alpha * 0.052F);
            wrote |= this.beamQuad(buffer, matrix, cam, p0, p1, 0.38, 255, 180, 0, alpha * 0.145F);
            wrote |= this.beamQuad(buffer, matrix, cam, p0, p1, 0.2, 255, 225, 65, alpha * 0.43F);
            wrote |= this.beamQuad(buffer, matrix, cam, p0, p1, 0.07, 255, 255, 245, alpha);
         }

         return wrote;
      }

      private boolean beamQuad(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 start, Vec3 end, double width, int r, int g, int b, float alpha) {
         Vec3 dir = end.subtract(start);
         if (dir.lengthSqr() < 1.0E-4) {
            return false;
         }

         Vec3 mid = start.add(end).scale(0.5);
         Vec3 toCam = cam.subtract(mid);
         Vec3 side = dir.cross(toCam);
         if (side.lengthSqr() < 1.0E-4) {
            side = dir.cross(new Vec3(0.0, 1.0, 0.0));
         }

         if (side.lengthSqr() < 1.0E-4) {
            side = new Vec3(1.0, 0.0, 0.0);
         }

         side = side.normalize().scale(width);
         Vec3 a = start.add(side);
         Vec3 b0 = end.add(side);
         Vec3 c = end.subtract(side);
         Vec3 d = start.subtract(side);
         return this.quad(buffer, matrix, cam, a, b0, c, d, r, g, b, alpha);
      }

      private boolean quad(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 a, Vec3 b, Vec3 c, Vec3 d, int r, int g, int bl, float alpha) {
         int ia = Mth.clamp((int)(alpha * 255.0F), 0, 255);
         if (ia <= 0) {
            return false;
         }

         this.vertex(buffer, matrix, cam, a, r, g, bl, ia);
         this.vertex(buffer, matrix, cam, b, r, g, bl, ia);
         this.vertex(buffer, matrix, cam, c, r, g, bl, ia);
         this.vertex(buffer, matrix, cam, a, r, g, bl, ia);
         this.vertex(buffer, matrix, cam, c, r, g, bl, ia);
         this.vertex(buffer, matrix, cam, d, r, g, bl, ia);
         return true;
      }

      private void vertex(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 p, int r, int g, int b, int a) {
         buffer.addVertex(matrix, (float)(p.x - cam.x), (float)(p.y - cam.y), (float)(p.z - cam.z)).setColor(r, g, b, a);
      }

      private MitamaDivineBeamsClient.Volley.Basis basis(Vec3 caster, Vec3 target) {
         Vec3 forward = target.subtract(caster);
         if (forward.lengthSqr() < 1.0E-4) {
            forward = new Vec3(0.0, 0.0, 1.0);
         } else {
            forward = forward.normalize();
         }

         Vec3 worldUp = new Vec3(0.0, 1.0, 0.0);
         Vec3 right = forward.cross(worldUp);
         if (right.lengthSqr() < 1.0E-4) {
            right = new Vec3(1.0, 0.0, 0.0);
         } else {
            right = right.normalize();
         }

         Vec3 up = right.cross(forward).normalize();
         return new MitamaDivineBeamsClient.Volley.Basis(forward, right, up);
      }

      private float hash(int seed, int value) {
         int x = seed ^ value * 73244475;
         x = (x ^ x >>> 16) * 73244475;
         x = (x ^ x >>> 16) * 73244475;
         x ^= x >>> 16;
         return (x & 65535) / 65535.0F;
      }

      private record Basis(Vec3 forward, Vec3 right, Vec3 up) {
      }

      private static final class Beam {
         private boolean active;
         private boolean missed;
         private int age;
         private int burstAge;
         private Vec3 pos = Vec3.ZERO;
         private Vec3 prevPos = Vec3.ZERO;
         private Vec3 vel = Vec3.ZERO;
         private Vec3 smoothTarget = Vec3.ZERO;
         private Vec3 prevSmoothTarget = Vec3.ZERO;
         private Vec3 impactPos;
         private final Vec3[] trail = new Vec3[46];

         private void pushTrail(Vec3 point) {
            for (int i = this.trail.length - 1; i > 0; i--) {
               this.trail[i] = this.trail[i - 1];
            }

            this.trail[0] = point;
         }
      }
   }
}
