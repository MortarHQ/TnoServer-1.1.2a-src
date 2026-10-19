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
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent.Stage;
import net.neoforged.neoforge.common.NeoForge;
import org.joml.Matrix4f;

public final class MitamaAtomicEdictVfxClient {
   private static final List<MitamaAtomicEdictVfxClient.AtomicCast> CASTS = new ArrayList<>();
   private static boolean initialized;

   private MitamaAtomicEdictVfxClient() {
   }

   public static void init() {
      if (!initialized) {
         initialized = true;
         NeoForge.EVENT_BUS.addListener(MitamaAtomicEdictVfxClient::render);
      }
   }

   public static void spawn(int casterId, double x, double y, double z, float radius, long seed, int durationTicks, int releaseTick) {
      Minecraft minecraft = Minecraft.getInstance();
      if (minecraft.level != null && minecraft.player != null) {
         if (!(minecraft.player.distanceToSqr(x, y, z) > 577600.0)) {
            int duration = Math.max(700, durationTicks);
            int release = Mth.clamp(releaseTick, 360, duration - 220);
            CASTS.add(new MitamaAtomicEdictVfxClient.AtomicCast(new Vec3(x, y, z), Mth.clamp(radius, 14.0F, 190.0F), seed, duration, release, Util.getMillis()));
            if (CASTS.size() > 2) {
               CASTS.remove(0);
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
            RenderSystem.enableBlend();
            RenderSystem.disableCull();
            RenderSystem.enableDepthTest();
            RenderSystem.depthFunc(515);
            RenderSystem.depthMask(false);
            RenderSystem.setShader(GameRenderer::getPositionColorShader);
            poseStack.pushPose();
            poseStack.translate(-camera.x, -camera.y, -camera.z);
            Matrix4f matrix = poseStack.last().pose();
            Iterator<MitamaAtomicEdictVfxClient.AtomicCast> iterator = CASTS.iterator();

            while (iterator.hasNext()) {
               MitamaAtomicEdictVfxClient.AtomicCast cast = iterator.next();
               if (cast.dead(now)) {
                  iterator.remove();
               } else {
                  cast.render(matrix, now);
               }
            }

            poseStack.popPose();
            RenderSystem.depthMask(true);
            RenderSystem.enableDepthTest();
            RenderSystem.enableCull();
            RenderSystem.disableBlend();
            RenderSystem.defaultBlendFunc();
         } else {
            CASTS.clear();
         }
      }
   }

   private static final class AtomicCast {
      private static final int RADIAL_STRANDS = 54;
      private static final int ORBIT_STRANDS = 6;
      private static final int RAIN_LINES = 96;
      private static final int SPARKS = 260;
      private static final int CIRCUITS = 44;
      private static final int ASCENT_THREADS = 124;
      private static final int FLOATING_SQUARES = 72;
      private final Vec3 origin;
      private final float radius;
      private final long seed;
      private final int durationTicks;
      private final int releaseTick;
      private final long created;
      private final MitamaAtomicEdictVfxClient.AtomicCast.RadialStrand[] radialStrands = new MitamaAtomicEdictVfxClient.AtomicCast.RadialStrand[54];
      private final MitamaAtomicEdictVfxClient.AtomicCast.OrbitStrand[] orbitStrands = new MitamaAtomicEdictVfxClient.AtomicCast.OrbitStrand[6];
      private final MitamaAtomicEdictVfxClient.AtomicCast.RainLine[] rainLines = new MitamaAtomicEdictVfxClient.AtomicCast.RainLine[96];
      private final float[] sparks = new float[260];
      private final MitamaAtomicEdictVfxClient.AtomicCast.Circuit[] circuits = new MitamaAtomicEdictVfxClient.AtomicCast.Circuit[44];
      private final MitamaAtomicEdictVfxClient.AtomicCast.AscentThread[] ascentThreads = new MitamaAtomicEdictVfxClient.AtomicCast.AscentThread[124];
      private final MitamaAtomicEdictVfxClient.AtomicCast.FloatingSquare[] floatingSquares = new MitamaAtomicEdictVfxClient.AtomicCast.FloatingSquare[72];
      private boolean releaseTriggered;

      private AtomicCast(Vec3 origin, float radius, long seed, int durationTicks, int releaseTick, long created) {
         this.origin = origin;
         this.radius = radius;
         this.seed = seed;
         this.durationTicks = durationTicks;
         this.releaseTick = releaseTick;
         this.created = created;

         for (int i = 0; i < 54; i++) {
            float angle = i * (float) (Math.PI * 2) / 54.0F + (hash(seed, 1000 + i) - 0.5F) * 0.04F;
            float length = radius * (0.96F + (hash(seed, 2000 + i) - 0.5F) * 0.1F);
            float height = 0.42F + hash(seed, 3000 + i) * 1.55F;
            float delay = hash(seed, 4000 + i) * 0.24F;
            float kink = hash(seed, 5000 + i);
            this.radialStrands[i] = new MitamaAtomicEdictVfxClient.AtomicCast.RadialStrand(angle, length, height, delay, kink);
         }

         for (int i = 0; i < 6; i++) {
            float angle = i * (float) (Math.PI * 2) / 6.0F + hash(seed, 6000 + i) * 0.42F;
            float height = 0.55F + hash(seed, 7000 + i) * 1.85F;
            float speed = 0.012F + hash(seed, 8000 + i) * 0.007F;
            float size = 0.64F + hash(seed, 9000 + i) * 0.2F;
            this.orbitStrands[i] = new MitamaAtomicEdictVfxClient.AtomicCast.OrbitStrand(angle, height, speed, size, hash(seed, 10000 + i));
         }

         for (int i = 0; i < 96; i++) {
            float angle = i * (float) (Math.PI * 2) / 96.0F + (hash(seed, 11000 + i) - 0.5F) * 0.25F;
            float dist = radius * (0.34F + hash(seed, 12000 + i) * 0.98F);
            float height = radius * (1.25F + hash(seed, 13000 + i) * 2.1F);
            float delay = hash(seed, 14000 + i) * 0.66F;
            this.rainLines[i] = new MitamaAtomicEdictVfxClient.AtomicCast.RainLine(angle, dist, height, delay, hash(seed, 15000 + i));
         }

         for (int i = 0; i < 260; i++) {
            this.sparks[i] = hash(seed, 16000 + i);
         }

         for (int i = 0; i < 124; i++) {
            float angle = hash(seed, 20000 + i) * (float) (Math.PI * 2);
            float dist = radius * (0.18F + hash(seed, 21000 + i) * 0.72F);
            float height = 2.8F + hash(seed, 22000 + i) * 5.8F;
            float speed = 0.006F + hash(seed, 23000 + i) * 0.012F;
            this.ascentThreads[i] = new MitamaAtomicEdictVfxClient.AtomicCast.AscentThread(angle, dist, height, speed, hash(seed, 24000 + i));
         }

         for (int i = 0; i < 72; i++) {
            float angle = hash(seed, 25000 + i) * (float) (Math.PI * 2);
            float dist = radius * (0.18F + hash(seed, 26000 + i) * 0.72F);
            float height = 1.4F + hash(seed, 27000 + i) * 6.8F;
            float speed = 0.0045F + hash(seed, 28000 + i) * 0.008F;
            float size = 0.14F + hash(seed, 29000 + i) * 0.42F;
            this.floatingSquares[i] = new MitamaAtomicEdictVfxClient.AtomicCast.FloatingSquare(angle, dist, height, speed, size, hash(seed, 30000 + i));
         }

         for (int i = 0; i < 44; i++) {
            float angle = i * (float) (Math.PI * 2) / 44.0F + hash(seed, 17000 + i) * 0.19F;
            float dist = radius * (0.62F + hash(seed, 18000 + i) * 0.96F);
            this.circuits[i] = new MitamaAtomicEdictVfxClient.AtomicCast.Circuit(angle, dist, hash(seed, 19000 + i));
         }
      }

      private boolean dead(long now) {
         return this.ageTicks(now) > this.durationTicks + 160.0;
      }

      private double ageTicks(long now) {
         return Math.max(0.0, (now - this.created) / 50.0);
      }

      private void render(Matrix4f matrix, long now) {
         double age = this.ageTicks(now);
         float releaseAge = this.releaseTick;
         float suckStart = releaseAge - 24.0F;
         float calmStart = releaseAge - 10.0F;
         float calmEnd = releaseAge;
         float charge = Mth.clamp((float)(age / Math.max(1.0F, suckStart)), 0.0F, 1.0F);
         float preSuck = smooth(Mth.clamp((float)((age - suckStart) / Math.max(1.0F, calmStart - suckStart)), 0.0F, 1.0F));
         float release = Mth.clamp((float)((age - releaseAge) / 54.0), 0.0F, 1.0F);
         float hold = 1.0F - smooth(Mth.clamp((float)((age - releaseAge - 42.0) / 190.0), 0.0F, 1.0F));
         float fade = 1.0F - smooth(Mth.clamp((float)((age - this.durationTicks + 80.0) / 80.0), 0.0F, 1.0F));
         if (!this.releaseTriggered && age >= releaseAge) {
            this.releaseTriggered = true;
            PathsClientState.triggerManualShake(260, 10.5F, 18.0F);
         }

         if (!(age >= calmStart) || !(age < calmEnd)) {
            double x = this.origin.x;
            double y = this.origin.y;
            double z = this.origin.z;
            float chargeLive = age < calmStart ? smooth(charge) * (1.0F - preSuck * 0.9F) : 0.0F;
            float releaseLive = smooth(release) * hold * fade;
            float aftermath = smooth(Mth.clamp((float)((age - releaseAge) / 220.0), 0.0F, 1.0F)) * fade;
            RenderSystem.blendFuncSeparate(770, 771, 1, 771);
            this.renderVioletPressure(matrix, x, y, z, age, chargeLive, aftermath);
            this.renderGroundBloom(matrix, x, y, z, age, chargeLive, releaseLive, aftermath);
            this.renderCasterPressureBody(matrix, x, y, z, age, chargeLive, releaseLive, aftermath);
            RenderSystem.blendFuncSeparate(770, 1, 1, 0);
            this.renderCasterChargeCrown(matrix, x, y, z, age, chargeLive, releaseLive);
            this.renderCasterFramingVeil(matrix, x, y, z, age, chargeLive, releaseLive);
            this.renderRadialStrands(matrix, x, y, z, age, chargeLive);
            this.renderOrbitStrands(matrix, x, y, z, age, chargeLive);
            this.renderFallingDomain(matrix, x, y, z, age, chargeLive);
            this.renderAscentThreads(matrix, x, y, z, age, chargeLive);
            this.renderFloatingSquares(matrix, x, y, z, age, chargeLive);
            this.renderCircuitScars(matrix, x, y, z, age, chargeLive);
            this.renderCircuitGlyphBursts(matrix, x, y, z, age, chargeLive);
            this.renderSparks(matrix, x, y, z, age, chargeLive, releaseLive, aftermath);
            if (releaseLive > 0.001F) {
               RenderSystem.blendFuncSeparate(770, 771, 1, 771);
               RenderSystem.depthMask(true);
               this.renderExpandingBeamCore(matrix, x, y, z, age, releaseLive);
               RenderSystem.depthMask(false);
               RenderSystem.blendFuncSeparate(770, 1, 1, 0);
               this.renderExpandingBeamGlow(matrix, x, y, z, age, releaseLive);
               this.renderBeamInteriorStorm(matrix, x, y, z, age, releaseLive);
               this.renderImpactRings(matrix, x, y, z, age, releaseLive, aftermath);
            }

            RenderSystem.blendFuncSeparate(770, 1, 1, 0);
            this.renderReleaseBlip(matrix, x, y, z, age, releaseLive);
            this.renderFinalCasterHalo(matrix, x, y, z, age);
            this.renderVanishingApotheosis(matrix, x, y, z, age);
         }
      }

      private void renderVioletPressure(Matrix4f matrix, double x, double y, double z, double time, float charge, float aftermath) {
         float alpha = charge * (0.42F + 0.14F * (float)Math.sin(time * 0.055)) + aftermath * 0.44F;
         if (!(alpha <= 0.001F)) {
            this.renderSphere(
               matrix,
               x,
               y + this.radius * 0.34,
               z,
               this.radius * (0.78F + charge * 0.24F),
               this.radius * (0.46F + charge * 0.14F),
               138,
               0,
               255,
               alpha * 0.86F,
               10,
               96,
               time * 0.006,
               1.0F
            );
            this.renderSphere(
               matrix,
               x,
               y + this.radius * 0.4,
               z,
               this.radius * (1.04F + charge * 0.3F),
               this.radius * (0.58F + charge * 0.18F),
               96,
               0,
               255,
               alpha * 0.34F,
               10,
               96,
               -time * 0.004,
               1.4F
            );
            this.renderSphere(
               matrix,
               x,
               y + this.radius * 0.26,
               z,
               this.radius * (0.48F + charge * 0.2F),
               this.radius * (0.28F + charge * 0.1F),
               255,
               110,
               255,
               alpha * 0.42F,
               8,
               72,
               time * 0.012,
               0.9F
            );
         }
      }

      private void renderGroundBloom(Matrix4f matrix, double x, double y, double z, double time, float charge, float release, float aftermath) {
         float live = Mth.clamp(charge * 0.8F + release * 1.65F + aftermath * 0.52F, 0.0F, 1.0F);
         if (!(live <= 0.001F)) {
            double expansion = this.radius * (0.22 + smooth((float)Math.min(1.0, time / this.releaseTick)) * 0.36 + release * 0.88);
            this.renderFlatDisk(matrix, x, y + 0.025, z, expansion * 0.06, expansion * 0.54, 151, 35, 255, live * 0.28F, 96);
            this.renderFlatDisk(matrix, x, y + 0.03, z, expansion * 0.22, expansion * 0.76, 255, 82, 245, live * 0.18F, 96);
         }
      }

      private void renderRadialStrands(Matrix4f matrix, double x, double y, double z, double time, float charge) {
         if (!(charge <= 0.001F)) {
            BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
            boolean wrote = false;
            float pulse = 0.8F + 0.2F * (float)Math.sin(time * 0.92);
            float live = Mth.clamp(charge * 2.25F, 0.0F, 1.0F);
            double casterGround = this.terrainY(x, y, z) + 0.06;

            for (int i = 0; i < this.radialStrands.length; i++) {
               MitamaAtomicEdictVfxClient.AtomicCast.RadialStrand strand = this.radialStrands[i];
               float grow = smooth(Mth.clamp((charge - strand.delay * 0.42F) / 0.32F, 0.0F, 1.0F));
               if (!(grow <= 0.001F)) {
                  double angle = strand.angle + Math.sin(time * 0.01 + strand.kink * 9.0) * 0.022;
                  double sideAngle = angle + (strand.kink > 0.5F ? (float) (Math.PI / 2) : (float) (-Math.PI / 2));
                  double maxLen = Math.min(strand.length, this.radius * 1.02);
                  double startR = 0.17;
                  double endR = startR + (maxLen - startR) * grow;
                  if (!(endR <= startR + 0.05)) {
                     MitamaAtomicEdictVfxClient.AtomicCast.Color color = rainbow(time, strand.kink + i * 0.037, 1.0F, 5.2F, 0.001F);
                     float alpha = Mth.clamp(live * pulse * (0.88F + strand.kink * 0.48F), 0.0F, 1.0F);
                     float width = 0.0068F + strand.kink * 0.0068F;
                     double lastX = x + Math.cos(angle) * startR;
                     double lastZ = z + Math.sin(angle) * startR;
                     double lastY = casterGround + 0.02;
                     int segments = 8;

                     for (int s = 1; s <= segments; s++) {
                        double f = (double)s / segments;
                        double pathR = startR + (endR - startR) * f;
                        double chipUnit = this.radius * (0.0048 + strand.kink * 0.0082);
                        double stepSide = (s % 3 - 1.0) * chipUnit;
                        if ((s & 1) == 0) {
                           stepSide *= -1.0;
                        }

                        double px = x + Math.cos(angle) * pathR + Math.cos(sideAngle) * stepSide;
                        double pz = z + Math.sin(angle) * pathR + Math.sin(sideAngle) * stepSide;
                        double terrain = this.terrainY(px, y, pz) + 0.065;
                        double lift = Math.max(0.0, f - 0.78) * (0.74 + strand.height * 0.52) * grow;
                        double targetY = terrain + lift;
                        if (Math.abs(targetY - lastY) > 0.42) {
                           wrote |= addCircuitRibbon(buffer, matrix, lastX, lastY, lastZ, lastX, targetY, lastZ, width, 160, 0, 255, color, alpha, 1.1F);
                           wrote |= addCircuitNode(buffer, matrix, lastX, targetY, lastZ, width * 1.85F, color, alpha * 0.8F);
                        }

                        wrote |= addCircuitRibbon(buffer, matrix, lastX, targetY, lastZ, px, targetY, pz, width, 255, 72, 255, color, alpha, 1.18F);
                        if ((s & 1) == 0) {
                           wrote |= addCircuitNode(buffer, matrix, px, targetY, pz, width * 1.05F, color, alpha * 0.42F);
                        }

                        if ((i + s) % 4 == 0 && f > 0.18 && f < 0.92) {
                           double branchDir = (i + s & 1) == 0 ? 1.0 : -1.0;
                           double branchLen = this.radius * (0.018 + strand.kink * 0.032) * grow;
                           double bx = px + Math.cos(sideAngle) * branchLen * branchDir;
                           double bz = pz + Math.sin(sideAngle) * branchLen * branchDir;
                           double by = this.terrainY(bx, y, bz) + 0.075 + lift * 0.48;
                           wrote |= addCircuitRibbon(buffer, matrix, px, targetY, pz, bx, by, bz, width * 0.62F, 255, 176, 255, color, alpha * 0.78F, 0.94F);
                           wrote |= addCircuitNode(buffer, matrix, bx, by, bz, width * 1.24F, color, alpha * 0.62F);
                        }

                        if (s == segments || s == segments - 1 && strand.kink > 0.58F) {
                           double tower = 0.62 + strand.height * 0.92;
                           wrote |= addCircuitRibbon(
                              buffer, matrix, px, targetY, pz, px, targetY + tower, pz, width * 0.92F, 255, 208, 255, color, alpha * 0.88F, 1.08F
                           );
                           wrote |= addCircuitNode(buffer, matrix, px, targetY + tower, pz, width * 1.95F, color, alpha * 0.72F);
                        }

                        lastX = px;
                        lastZ = pz;
                        lastY = targetY;
                     }

                     if (charge > 0.58F) {
                        double pulseR = endR * (0.92 + 0.08 * Math.sin(time * 0.16 + i));
                        double px = x + Math.cos(angle) * pulseR;
                        double pz = z + Math.sin(angle) * pulseR;
                        double py = this.terrainY(px, y, pz) + 0.12;
                        wrote |= addCameraSquare(buffer, matrix, px, py + 0.1, pz, 0.032F + strand.kink * 0.046F, color.r, color.g, color.b, alpha * 0.88F);
                     }
                  }
               }
            }

            safeDraw(buffer, wrote);
         }
      }

      private void renderOrbitStrands(Matrix4f matrix, double x, double y, double z, double time, float charge) {
         if (!(charge <= 0.001F)) {
            BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
            boolean wrote = false;
            float live = Mth.clamp(charge * 1.9F, 0.0F, 1.0F);
            float pulse = 0.76F + 0.24F * (float)Math.sin(time * 0.6);

            for (int i = 0; i < this.orbitStrands.length; i++) {
               MitamaAtomicEdictVfxClient.AtomicCast.OrbitStrand strand = this.orbitStrands[i];
               MitamaAtomicEdictVfxClient.AtomicCast.Color color = rainbow(time, strand.variant + i * 0.052, 1.0F, 2.85F, 0.012F);
               double cycle = (time * strand.speed * 0.94 + (double)i / this.orbitStrands.length + strand.variant * 0.18) % 1.0;
               double bottom = y - 0.18;
               double topLimit = y + 3.46;
               double baseY = bottom + cycle * 3.64;
               double r = 0.44 + strand.size * 0.22 + 0.04 * Math.sin(time * 0.14 + i);
               double phase = strand.angle + time * 0.108 + i * 0.78;
               double lastX = 0.0;
               double lastY = 0.0;
               double lastZ = 0.0;
               boolean hasLast = false;

               for (int s = 0; s <= 38; s++) {
                  double u = s / 38.0;
                  double py = baseY + u * 1.82;
                  double fadeLow = Mth.clamp((float)((py - bottom) / 0.54), 0.0F, 1.0F);
                  double fadeHigh = Mth.clamp((float)((topLimit - py) / 0.66), 0.0F, 1.0F);
                  double fade = smooth((float)Math.min(fadeLow, fadeHigh));
                  if (fade <= 0.001) {
                     hasLast = false;
                  } else {
                     double a = phase + u * (float) (Math.PI * 2) * 1.72 * ((i & 1) == 0 ? 1.0 : -1.0);
                     double wobble = 0.034 * Math.sin(time * 0.22 + u * 12.0 + strand.variant * 7.0);
                     double px = x + Math.cos(a) * (r + wobble);
                     double pz = z + Math.sin(a) * (r + wobble);
                     if (hasLast) {
                        float width = 0.0085F + strand.variant * 0.005F;
                        float alpha = Mth.clamp((float)(live * fade * pulse * 1.36), 0.0F, 1.0F);
                        wrote |= addRibbon(buffer, matrix, lastX, lastY, lastZ, px, py, pz, width * 6.0F, 112, 8, 255, alpha * 0.44F);
                        wrote |= addRibbon(buffer, matrix, lastX, lastY + 0.011, lastZ, px, py + 0.011, pz, width * 1.95F, 255, 225, 255, alpha);
                        wrote |= addRibbon(buffer, matrix, lastX, lastY + 0.022, lastZ, px, py + 0.022, pz, width * 0.72F, color.r, color.g, color.b, alpha);
                     }

                     lastX = px;
                     lastY = py;
                     lastZ = pz;
                     hasLast = true;
                     if (s % 16 == 7) {
                        double branchA = a + (i % 2 == 0 ? (float) (Math.PI / 2) : (float) (-Math.PI / 2));
                        double bx = px + Math.cos(branchA) * (0.1 + strand.variant * 0.07);
                        double bz = pz + Math.sin(branchA) * (0.1 + strand.variant * 0.07);
                        float branchAlpha = Mth.clamp((float)(live * fade * 0.98F), 0.0F, 1.0F);
                        wrote |= addRibbon(buffer, matrix, px, py, pz, bx, py + 0.026, bz, 0.0055F, 255, 205, 255, branchAlpha);
                     }
                  }
               }
            }

            safeDraw(buffer, wrote);
         }
      }

      private void renderFallingDomain(Matrix4f matrix, double x, double y, double z, double time, float charge) {
         if (!(charge <= 0.001F)) {
            BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
            boolean wrote = false;
            float live = smooth(Mth.clamp((charge - 0.14F) / 0.82F, 0.0F, 1.0F));
            float pulse = 0.84F + 0.16F * (float)Math.sin(time * 0.48);

            for (int i = 0; i < this.radialStrands.length; i++) {
               MitamaAtomicEdictVfxClient.AtomicCast.RadialStrand strand = this.radialStrands[i];
               float grow = smooth(Mth.clamp((charge - strand.delay * 0.4F) / 0.32F, 0.0F, 1.0F));
               if (!(grow <= 0.001F)) {
                  double baseA = strand.angle + Math.sin(time * 0.01 + strand.kink * 9.0) * 0.018;
                  double endR = (0.17 + (Math.min(strand.length, this.radius * 1.02) - 0.17) * grow) * (0.96 + 0.035 * Math.sin(time * 0.055 + i));

                  for (int lane = 0; lane < 2; lane++) {
                     double laneHash = hash(this.seed, 410000 + i * 3 + lane);
                     double a = baseA + (lane - 0.5) * (0.02 + laneHash * 0.03);
                     double side = a + (float) (Math.PI / 2);
                     double px = x + Math.cos(a) * endR + Math.cos(side) * this.radius * (laneHash - 0.5) * 0.032;
                     double pz = z + Math.sin(a) * endR + Math.sin(side) * this.radius * (laneHash - 0.5) * 0.032;
                     double ground = this.terrainY(px, y, pz);
                     double bottom = ground + 0.1;
                     double top = y + this.radius * (1.38 + laneHash * 1.88);
                     double height = Math.max(1.0, top - bottom);
                     double cycle = (time * (0.006 + laneHash * 0.006) + laneHash) % 1.0;
                     double riseCut = smooth(Mth.clamp((float)(cycle / 0.34), 0.0F, 1.0F));
                     double y0 = bottom + height * 0.04 * riseCut;
                     double y1 = bottom + height * (0.3 + laneHash * 0.1) * riseCut;
                     double y2 = bottom + height * (0.66 + laneHash * 0.12) * riseCut;
                     double y3 = bottom + height * (0.94 + laneHash * 0.1) * riseCut;
                     MitamaAtomicEdictVfxClient.AtomicCast.Color c = rainbow(time, strand.kink + laneHash + i * 0.011, 1.0F, 4.4F, 0.004F);
                     float width = 0.011F + strand.kink * 0.018F;
                     float alpha = Mth.clamp(live * pulse * grow * (1.15F + (float)laneHash * 0.5F), 0.0F, 1.0F);
                     wrote |= addRibbon(
                        buffer,
                        matrix,
                        px,
                        y0,
                        pz,
                        px + Math.cos(side) * this.radius * 0.012,
                        y1,
                        pz + Math.sin(side) * this.radius * 0.012,
                        width * 9.0F,
                        118,
                        0,
                        255,
                        alpha * 0.58F
                     );
                     wrote |= addRibbon(
                        buffer,
                        matrix,
                        px + Math.cos(side) * this.radius * 0.012,
                        y1,
                        pz + Math.sin(side) * this.radius * 0.012,
                        px - Math.cos(side) * this.radius * 0.016,
                        y2,
                        pz - Math.sin(side) * this.radius * 0.016,
                        width * 8.0F,
                        118,
                        0,
                        255,
                        alpha * 0.54F
                     );
                     wrote |= addRibbon(
                        buffer,
                        matrix,
                        px - Math.cos(side) * this.radius * 0.016,
                        y2,
                        pz - Math.sin(side) * this.radius * 0.016,
                        px + Math.cos(a) * this.radius * 0.014,
                        y3,
                        pz + Math.sin(a) * this.radius * 0.014,
                        width * 7.2F,
                        118,
                        0,
                        255,
                        alpha * 0.5F
                     );
                     wrote |= addRibbon(
                        buffer,
                        matrix,
                        px,
                        y0 + 0.018,
                        pz,
                        px + Math.cos(side) * this.radius * 0.012,
                        y1 + 0.018,
                        pz + Math.sin(side) * this.radius * 0.012,
                        width * 1.7F,
                        255,
                        240,
                        255,
                        alpha
                     );
                     wrote |= addRibbon(
                        buffer,
                        matrix,
                        px + Math.cos(side) * this.radius * 0.012,
                        y1 + 0.032,
                        pz + Math.sin(side) * this.radius * 0.012,
                        px - Math.cos(side) * this.radius * 0.016,
                        y2 + 0.032,
                        pz - Math.sin(side) * this.radius * 0.016,
                        width * 1.45F,
                        c.r,
                        c.g,
                        c.b,
                        alpha
                     );
                     wrote |= addRibbon(
                        buffer,
                        matrix,
                        px - Math.cos(side) * this.radius * 0.016,
                        y2 + 0.046,
                        pz - Math.sin(side) * this.radius * 0.016,
                        px + Math.cos(a) * this.radius * 0.014,
                        y3 + 0.046,
                        pz + Math.sin(a) * this.radius * 0.014,
                        width * 1.18F,
                        255,
                        190,
                        255,
                        alpha * 0.96F
                     );
                  }
               }
            }

            for (int i = 0; i < this.rainLines.length; i++) {
               MitamaAtomicEdictVfxClient.AtomicCast.RainLine line = this.rainLines[i];
               float appear = smooth(Mth.clamp((live - line.delay * 0.26F) / 0.34F, 0.0F, 1.0F));
               if (!(appear <= 0.001F)) {
                  double a = line.angle + Math.sin(time * 0.012 + line.variant * 9.0) * 0.03;
                  double px = x + Math.cos(a) * line.dist;
                  double pz = z + Math.sin(a) * line.dist;
                  double ground = this.terrainY(px, y, pz) + 0.08;
                  double cycle = (time * (0.01 + line.variant * 0.008) + line.delay) % 1.0;
                  double y0 = ground + cycle * Math.min(line.height * 0.42, 7.0);
                  double y1 = Math.min(y + Math.min(this.radius * 2.5, 8.8), y0 + 0.55 + line.variant * 1.25);
                  double fade = smooth(Mth.clamp((float)Math.min((y0 - ground) / 0.75, (y + Math.min(this.radius * 2.5, 8.8) - y1) / 1.45), 0.0F, 1.0F));
                  if (!(fade <= 0.001)) {
                     MitamaAtomicEdictVfxClient.AtomicCast.Color c = rainbow(time, line.variant + i * 0.013, 1.0F, 4.1F, 0.006F);
                     float width = 0.0048F + line.variant * 0.0068F;
                     float alpha = Mth.clamp((float)(appear * pulse * fade * 2.1), 0.0F, 1.0F);
                     wrote |= addRibbon(buffer, matrix, px, y0, pz, px, y1, pz, width * 6.4F, 118, 0, 255, alpha * 0.4F);
                     wrote |= addRibbon(buffer, matrix, px, y0 + 0.012, pz, px, y1 + 0.012, pz, width * 1.35F, 255, 238, 255, alpha);
                     wrote |= addRibbon(buffer, matrix, px, y0 + 0.024, pz, px, y1 + 0.024, pz, width * 0.55F, c.r, c.g, c.b, alpha);
                  }
               }
            }

            safeDraw(buffer, wrote);
         }
      }

      private void renderCircuitScars(Matrix4f matrix, double x, double y, double z, double time, float charge) {
         if (!(charge <= 0.001F)) {
            BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
            boolean wrote = false;
            float live = smooth(Mth.clamp((charge - 0.24F) / 0.72F, 0.0F, 1.0F));

            for (int i = 0; i < this.circuits.length; i++) {
               MitamaAtomicEdictVfxClient.AtomicCast.Circuit c = this.circuits[i];
               MitamaAtomicEdictVfxClient.AtomicCast.Color color = rainbow(time, c.variant + 0.2, 0.8F, 1.8F, 0.3F);
               double a = c.angle;
               double r0 = c.dist * 0.24;
               double r1 = c.dist * 0.62;
               double r2 = c.dist;
               double bend = (c.variant - 0.5) * 0.54;
               double x0 = x + Math.cos(a) * r0;
               double z0 = z + Math.sin(a) * r0;
               double x1 = x + Math.cos(a + bend * 0.55) * r1;
               double z1 = z + Math.sin(a + bend * 0.55) * r1;
               double x2 = x + Math.cos(a + bend) * r2;
               double z2 = z + Math.sin(a + bend) * r2;
               double yy = y + 0.075 + i % 4 * 0.011;
               float width = 0.015F + c.variant * 0.02F;
               wrote |= addRibbon(buffer, matrix, x0, yy, z0, x1, yy, z1, width * 3.0F, 128, 20, 255, live * 0.34F);
               wrote |= addRibbon(buffer, matrix, x1, yy, z1, x2, yy, z2, width * 3.0F, 128, 20, 255, live * 0.34F);
               wrote |= addRibbon(buffer, matrix, x0, yy + 0.01, z0, x1, yy + 0.01, z1, width, color.r, color.g, color.b, live);
               wrote |= addRibbon(buffer, matrix, x1, yy + 0.01, z1, x2, yy + 0.01, z2, width, 255, 155, 255, live * 0.86F);
               wrote |= addCircuitNode(buffer, matrix, x1, yy + 0.012, z1, width * 1.55F, color, live * 0.56F);
               if ((i & 1) == 0) {
                  double by = yy + 0.22 + c.variant * 0.26;
                  wrote |= addRibbon(buffer, matrix, x2, yy + 0.006, z2, x2, by, z2, width * 0.62F, 255, 205, 255, live * 0.74F);
                  wrote |= addCircuitNode(buffer, matrix, x2, by, z2, width * 1.16F, color, live * 0.54F);
               }
            }

            safeDraw(buffer, wrote);
         }
      }

      private void renderAscentThreads(Matrix4f matrix, double x, double y, double z, double time, float charge) {
         if (!(charge <= 0.001F)) {
            BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
            boolean wrote = false;
            float live = smooth(Mth.clamp((charge - 0.035F) / 0.88F, 0.0F, 1.0F));
            float pulse = 0.88F + 0.12F * (float)Math.sin(time * 0.72);

            for (int i = 0; i < this.ascentThreads.length; i++) {
               MitamaAtomicEdictVfxClient.AtomicCast.AscentThread thread = this.ascentThreads[i];
               MitamaAtomicEdictVfxClient.AtomicCast.RadialStrand base = this.radialStrands[i % this.radialStrands.length];
               float baseGrow = smooth(Mth.clamp((charge - base.delay * 0.36F) / 0.3F, 0.0F, 1.0F));
               if (!(baseGrow <= 0.001F)) {
                  double baseAngle = base.angle + Math.sin(time * 0.01 + base.kink * 9.0) * 0.018;
                  double maxLen = Math.min(base.length, this.radius * 1.02);
                  double startR = 0.17;
                  double endR = startR + (maxLen - startR) * baseGrow;
                  double linePos = 0.12 + thread.variant * 0.84;
                  double pathR = startR + (endR - startR) * linePos;
                  double sideAngle = baseAngle + (float) (Math.PI / 2);
                  double sideSpread = this.radius * (thread.variant - 0.5) * 0.09;
                  double px = x + Math.cos(baseAngle) * pathR + Math.cos(sideAngle) * sideSpread;
                  double pz = z + Math.sin(baseAngle) * pathR + Math.sin(sideAngle) * sideSpread;
                  double cycle = (time * (thread.speed * 0.72) + thread.variant) % 1.0;
                  double ground = this.terrainY(px, y, pz) + 0.065;
                  double topLimit = y + Math.min(this.radius * 2.72, 7.15);
                  double y0 = ground + cycle * Math.min(thread.height, 6.45);
                  double y1 = Math.min(topLimit, y0 + 0.88 + thread.variant * 1.92);
                  double fadeLow = Mth.clamp((float)((y0 - ground) / 0.52), 0.0F, 1.0F);
                  double fadeHigh = Mth.clamp((float)((topLimit - y1) / 1.55), 0.0F, 1.0F);
                  float alpha = Mth.clamp((float)(smooth((float)Math.min(fadeLow, fadeHigh)) * live * pulse * 2.8), 0.0F, 1.0F);
                  if (!(alpha <= 0.001F)) {
                     MitamaAtomicEdictVfxClient.AtomicCast.Color color = rainbow(time, thread.variant + i * 0.021, 1.0F, 4.2F, 0.003F);
                     float width = 0.0055F + thread.variant * 0.0074F;
                     wrote |= addRibbon(buffer, matrix, px, y0, pz, px, y1, pz, width * 12.4F, 126, 0, 255, alpha * 0.62F);
                     wrote |= addRibbon(buffer, matrix, px, y0 + 0.01, pz, px, y1 + 0.01, pz, width * 2.55F, 255, 244, 255, alpha);
                     wrote |= addRibbon(buffer, matrix, px, y0 + 0.02, pz, px, y1 + 0.02, pz, width * 0.72F, color.r, color.g, color.b, alpha);
                     if ((i & 1) == 0) {
                        double tinyOffset = this.radius * (0.006 + thread.variant * 0.02);
                        double bx = px + Math.cos(sideAngle) * tinyOffset;
                        double bz = pz + Math.sin(sideAngle) * tinyOffset;
                        wrote |= addRibbon(
                           buffer,
                           matrix,
                           bx,
                           y0 + 0.1,
                           bz,
                           bx,
                           Math.min(topLimit, y0 + 0.48 + thread.variant * 0.88),
                           bz,
                           width * 0.66F,
                           255,
                           225,
                           255,
                           alpha * 0.88F
                        );
                     }
                  }
               }
            }

            safeDraw(buffer, wrote);
         }
      }

      private void renderFloatingSquares(Matrix4f matrix, double x, double y, double z, double time, float charge) {
         if (!(charge <= 0.001F)) {
            BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
            boolean wrote = false;
            float live = smooth(Mth.clamp((charge - 0.07F) / 0.8F, 0.0F, 1.0F));
            float pulse = 0.86F + 0.14F * (float)Math.sin(time * 0.58);

            for (int i = 0; i < this.floatingSquares.length; i++) {
               MitamaAtomicEdictVfxClient.AtomicCast.FloatingSquare square = this.floatingSquares[i];
               MitamaAtomicEdictVfxClient.AtomicCast.RadialStrand base = this.radialStrands[i % this.radialStrands.length];
               float baseGrow = smooth(Mth.clamp((charge - base.delay * 0.36F) / 0.3F, 0.0F, 1.0F));
               if (!(baseGrow <= 0.001F)) {
                  double cycle = (time * (square.speed * 0.72) + square.variant) % 1.0;
                  double fadeIn = Mth.clamp((float)(cycle / 0.14), 0.0F, 1.0F);
                  double fadeOut = Mth.clamp((float)((1.0 - cycle) / 0.24), 0.0F, 1.0F);
                  float alpha = smooth((float)Math.min(fadeIn, fadeOut)) * live;
                  if (!(alpha <= 0.001F)) {
                     double baseAngle = base.angle + Math.sin(time * 0.01 + base.kink * 9.0) * 0.018;
                     double maxLen = Math.min(base.length, this.radius * 1.02);
                     double startR = 0.17;
                     double endR = startR + (maxLen - startR) * baseGrow;
                     double linePos = 0.14 + square.variant * 0.8;
                     double pathR = startR + (endR - startR) * linePos;
                     double sideAngle = baseAngle + (float) (Math.PI / 2);
                     double sideDrift = this.radius * (square.variant - 0.5) * 0.105 + Math.sin(time * 0.02 + i) * this.radius * 0.01;
                     double anchorX = x + Math.cos(baseAngle) * pathR + Math.cos(sideAngle) * sideDrift;
                     double anchorZ = z + Math.sin(baseAngle) * pathR + Math.sin(sideAngle) * sideDrift;
                     double ground = this.terrainY(anchorX, y, anchorZ) + 0.07;
                     double rise = smooth((float)cycle);
                     double px = anchorX + Math.cos(baseAngle + rise * 0.45) * this.radius * (0.005 + square.variant * 0.018);
                     double pz = anchorZ + Math.sin(baseAngle + rise * 0.45) * this.radius * (0.005 + square.variant * 0.018);
                     double py = ground + rise * Math.min(square.height * 0.82 + 1.85, 5.95);
                     double topLimit = y + 6.25;
                     if (!(py > topLimit)) {
                        float heightFade = smooth(Mth.clamp((float)((topLimit - py) / 2.1), 0.0F, 1.0F));
                        float finalAlpha = Mth.clamp(alpha * heightFade * pulse * 3.25F, 0.0F, 1.0F);
                        if (!(finalAlpha <= 0.001F)) {
                           float size = square.size * (0.82F + 0.12F * (float)Math.sin(time * 0.1 + square.variant * 8.0));
                           double yaw = time * (0.038 + square.variant * 0.014) + square.angle + rise * 0.5;
                           double pitch = time * (0.03 + square.variant * 0.012) + square.variant * (float) (Math.PI * 2) + rise * 0.42;
                           double roll = -time * (0.032 + square.variant * 0.012) + square.angle * 0.42;
                           wrote |= addRibbon(buffer, matrix, anchorX, ground + 0.02, anchorZ, px, py - 0.04, pz, 0.0062F, 255, 236, 255, finalAlpha * 0.92F);
                           wrote |= addRibbon(
                              buffer,
                              matrix,
                              px,
                              Math.max(ground + 0.03, py - 0.52),
                              pz,
                              px,
                              py - 0.04,
                              pz,
                              0.02F + square.variant * 0.01F,
                              112,
                              0,
                              255,
                              finalAlpha * 0.24F
                           );
                           wrote |= addAtomicCube(buffer, matrix, px, py, pz, size, yaw, pitch, roll, finalAlpha, i);
                        }
                     }
                  }
               }
            }

            safeDraw(buffer, wrote);
         }
      }

      private void renderCircuitGlyphBursts(Matrix4f matrix, double x, double y, double z, double time, float charge) {
         if (!(charge <= 0.001F)) {
            BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
            boolean wrote = false;
            float live = smooth(Mth.clamp((charge - 0.2F) / 0.72F, 0.0F, 1.0F));

            for (int i = 0; i < 44; i++) {
               float h = hash(this.seed, 31000 + i);
               float q = hash(this.seed, 32000 + i);
               double a = h * (float) (Math.PI * 2) + time * 0.006;
               double d = this.radius * (0.18 + q * 1.52);
               double px = x + Math.cos(a) * d;
               double pz = z + Math.sin(a) * d;
               double py = y + 0.15 + (time * (0.02 + q * 0.018) + h) % 1.0 * this.radius * 2.2;
               float alpha = live * smooth(Mth.clamp((float)((y + this.radius * 2.45 - py) / 4.6), 0.0F, 1.0F)) * 0.78F;
               if (!(alpha <= 0.001F)) {
                  double side = a + (float) (Math.PI / 2);
                  double len = this.radius * (0.035 + h * 0.05);
                  double x1 = px + Math.cos(side) * len;
                  double z1 = pz + Math.sin(side) * len;
                  double x2 = x1 + Math.cos(a) * len * (0.5 + q);
                  double z2 = z1 + Math.sin(a) * len * (0.5 + q);
                  wrote |= addRibbon(buffer, matrix, px, py, pz, x1, py, z1, 0.009F, 255, 208, 255, alpha);
                  wrote |= addRibbon(buffer, matrix, x1, py, z1, x2, py + 0.035, z2, 0.008F, 178, 48, 255, alpha * 0.92F);
                  if ((i & 1) == 0) {
                     wrote |= addRibbon(buffer, matrix, x2, py + 0.035, z2, x2, py + 0.22, z2, 0.006F, 255, 235, 255, alpha * 0.75F);
                  }
               }
            }

            safeDraw(buffer, wrote);
         }
      }

      private void renderSparks(Matrix4f matrix, double x, double y, double z, double time, float charge, float release, float aftermath) {
         float live = Mth.clamp(charge + release + aftermath * 0.55F, 0.0F, 1.0F);
         if (!(live <= 0.001F)) {
            BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
            boolean wrote = false;

            for (int i = 0; i + 2 < this.sparks.length; i += 3) {
               float h0 = this.sparks[i];
               float h1 = this.sparks[i + 1];
               float h2 = this.sparks[i + 2];
               double a = h0 * (float) (Math.PI * 2) + time * (0.01 + h1 * 0.014);
               double dist = this.radius * (0.16 + h1 * 1.82) * (0.42 + live * 0.58);
               double riseCycle = (time * (0.01 + h2 * 0.012) + h0) % 1.0;
               double px = x + Math.cos(a) * dist;
               double pz = z + Math.sin(a) * dist;
               double py = y + 0.18 + riseCycle * this.radius * (1.2 + charge * 2.15) + release * h1 * this.radius * 1.55;
               float fade = smooth(Mth.clamp((float)((1.0 - riseCycle) / 0.34), 0.0F, 1.0F));
               float size = 0.028F + h2 * 0.07F;
               MitamaAtomicEdictVfxClient.AtomicCast.Color color = rainbow(time, h0, 1.0F, 2.1F, 0.12F);
               float alpha = Mth.clamp(live * fade * 0.95F, 0.0F, 1.0F);
               if (i % 12 != 0) {
                  wrote |= addCameraSquare(buffer, matrix, px, py, pz, size * 2.3F, 136, 24, 255, alpha * 0.2F);
                  wrote |= addCameraSquare(buffer, matrix, px, py, pz, size, color.r, color.g, color.b, alpha * 0.82F);
               }

               if ((i & 6) == 0) {
                  double lift = 0.72 + h2 * 1.75 + release * this.radius * 0.028;
                  double sway = 0.12 + h1 * 0.24;
                  wrote |= addRibbon(
                     buffer,
                     matrix,
                     px,
                     py,
                     pz,
                     px + Math.cos(a + (float) (Math.PI / 2)) * sway,
                     py + lift,
                     pz + Math.sin(a + (float) (Math.PI / 2)) * sway,
                     0.01F + h0 * 0.012F,
                     color.r,
                     color.g,
                     color.b,
                     alpha * 0.86F
                  );
               }

               if (i % 18 == 0 && charge > 0.18F) {
                  float squareLive = Mth.clamp(alpha * (1.0F - release * 0.9F), 0.0F, 1.0F);
                  float squareSize = 0.1F + h1 * 0.3F;
                  double spin = time * (0.018 + h2 * 0.03) + h0 * (float) (Math.PI * 2);
                  wrote |= addOrientedSquareOutline(buffer, matrix, px, py + 0.08, pz, squareSize * 1.55F, spin, 118, 12, 255, squareLive * 0.35F);
                  wrote |= addOrientedSquareOutline(buffer, matrix, px, py + 0.08, pz, squareSize, spin + 0.12, 255, 128, 255, squareLive * 0.95F);
               }
            }

            safeDraw(buffer, wrote);
         }
      }

      private void renderCasterPressureBody(Matrix4f matrix, double x, double y, double z, double time, float charge, float release, float aftermath) {
         float live = Mth.clamp(charge * 1.4F + release * 1.35F + aftermath * 0.78F, 0.0F, 1.0F);
         if (!(live <= 0.001F)) {
            float pulse = 0.76F + 0.24F * (float)Math.sin(time * 0.74);
            float alpha = Mth.clamp(live * pulse * 1.7F, 0.0F, 1.0F);
            this.renderSphere(matrix, x, y + 1.18, z, 0.76F + charge * 0.24F, 1.34F + charge * 0.28F, 132, 0, 255, alpha * 0.32F, 8, 64, time * 0.018, 0.7F);
            this.renderSphere(matrix, x, y + 1.24, z, 1.08F + charge * 0.28F, 1.56F + charge * 0.3F, 82, 0, 255, alpha * 0.16F, 8, 64, -time * 0.011, 1.2F);
            this.renderSphere(matrix, x, y + 0.94, z, 0.42F + release * 0.34F, 0.4F + release * 0.24F, 255, 80, 255, alpha * 0.28F, 8, 54, time * 0.03, 0.85F);
            this.renderFlatDisk(matrix, x, y + 0.045, z, 0.18, 0.9 + charge * 0.42 + release * 0.82, 190, 22, 255, alpha * 0.2F, 96);
         }
      }

      private void renderCasterChargeCrown(Matrix4f matrix, double x, double y, double z, double time, float charge, float release) {
         if (!(release > 0.001F)) {
            ;
         }
      }

      private void renderCasterFramingVeil(Matrix4f matrix, double x, double y, double z, double time, float charge, float release) {
         if (!(release > 0.001F)) {
            ;
         }
      }

      private void renderReleaseBlip(Matrix4f matrix, double x, double y, double z, double time, float releaseLive) {
         float t = Mth.clamp((float)((time - this.releaseTick) / 28.0), 0.0F, 1.0F);
         float live = (float)Math.sin(t * Math.PI) * Mth.clamp(releaseLive * 1.8F, 0.0F, 1.0F);
         if (!(live <= 0.001F)) {
            BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
            boolean wrote = false;
            double ring = 0.55 + t * 5.25;
            double yHalo = y + 2.7 + t * 0.48;
            wrote |= addHorizontalRing(buffer, matrix, x, yHalo, z, ring * 0.82, ring, 0.07F + t * 0.045F, 255, 226, 255, live);
            wrote |= addHorizontalRing(buffer, matrix, x, yHalo + 0.035, z, ring * 0.92, ring * 1.12, 0.14F + t * 0.075F, 170, 24, 255, live * 0.54F);

            for (int i = 0; i < 42; i++) {
               float h = hash(this.seed, 90600 + i);
               double a = i * (float) (Math.PI * 2) / 42.0 + time * 0.024;
               double r = ring * (0.18 + h * 0.92);
               double px = x + Math.cos(a) * r;
               double pz = z + Math.sin(a) * r;
               wrote |= addRibbon(buffer, matrix, px, y + 0.18, pz, px, y + 3.3 + h * 2.0, pz, 0.022F + h * 0.018F, 255, 185, 255, live * (0.42F + h * 0.45F));
            }

            safeDraw(buffer, wrote);
         }
      }

      private void renderFinalCasterHalo(Matrix4f matrix, double x, double y, double z, double time) {
         float start = this.durationTicks - 72.0F;
         float in = smooth(Mth.clamp((float)((time - start) / 18.0), 0.0F, 1.0F));
         float out = 1.0F - smooth(Mth.clamp((float)((time - this.durationTicks - 36.0) / 58.0), 0.0F, 1.0F));
         float live = Mth.clamp(in * out, 0.0F, 1.0F);
         if (!(live <= 0.001F)) {
            float blip = (float)Math.sin(Mth.clamp((float)((time - start) / 104.0), 0.0F, 1.0F) * Math.PI);
            float alpha = Mth.clamp((0.76F + blip * 1.8F) * live, 0.0F, 1.0F);
            BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
            boolean wrote = false;
            double wobble = Math.sin(time * 0.08) * 0.035;
            double yHalo = y + 3.12 + blip * 0.22;
            double outer = 1.74 + blip * 0.62;
            double inner = outer - 0.105;
            wrote |= addHorizontalRing(buffer, matrix, x, yHalo, z, inner - 0.28, outer + 0.28, 0.115F, 128, 0, 255, alpha * 0.56F);
            wrote |= addHorizontalRing(buffer, matrix, x, yHalo + 0.02, z, inner, outer, 0.042F, 255, 232, 255, alpha);
            wrote |= addHorizontalRing(buffer, matrix, x, yHalo + 0.045, z, inner + wobble, outer + wobble, 0.02F, 255, 85, 255, alpha * 0.98F);

            for (int i = 0; i < 10; i++) {
               float h = hash(this.seed, 90800 + i);
               double a = i * (float) (Math.PI * 2) / 10.0 + time * 0.018;
               double px = x + Math.cos(a) * (0.86 + h * 1.52);
               double pz = z + Math.sin(a) * (0.86 + h * 1.52);
               double y0 = y + 0.35 + h * 0.45;
               double y1 = yHalo - 0.12 + h * 0.22;
               wrote |= addRibbon(buffer, matrix, px, y0, pz, px, y1, pz, 0.04F, 120, 0, 255, alpha * 0.34F);
               wrote |= addRibbon(buffer, matrix, px, y0 + 0.018, pz, px, y1 + 0.018, pz, 0.01F, 255, 225, 255, alpha * 0.82F);
            }

            safeDraw(buffer, wrote);
         }
      }

      private void renderVanishingApotheosis(Matrix4f matrix, double x, double y, double z, double time) {
         float start = this.releaseTick - 40.0F;
         float end = this.releaseTick - 10.0F;
         float t = Mth.clamp((float)((time - start) / Math.max(1.0F, end - start)), 0.0F, 1.0F);
         if (!(t <= 0.001F) && !(t >= 0.999F)) {
            float pull = smooth(t);
            float flash = Mth.clamp((float)Math.sin(t * Math.PI) * 1.55F, 0.0F, 1.0F);
            double haloY = y + 2.46 - pull * 1.26;
            double outer = Mth.lerp(pull, 2.55, 0.1);
            double inner = Math.max(0.035, outer - 0.18);
            BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
            boolean wrote = false;
            wrote |= addHorizontalRing(buffer, matrix, x, haloY, z, Math.max(0.035, inner - 0.46), outer + 0.72, 0.18F, 130, 0, 255, flash * 0.86F);
            wrote |= addHorizontalRing(buffer, matrix, x, haloY + 0.034, z, inner, outer, 0.06F, 255, 238, 255, Mth.clamp(flash * 2.25F, 0.0F, 1.0F));
            this.renderSphere(matrix, x, y + 1.22, z, 0.76F + pull * 0.42F, 1.12F + pull * 0.32F, 255, 110, 255, flash * 0.34F, 8, 54, time * 0.028, 0.88F);
            wrote |= addCameraSquare(buffer, matrix, x, y + 1.22, z, (0.26F + pull * 0.48F) * flash, 255, 238, 255, Mth.clamp(flash * 1.9F, 0.0F, 1.0F));
            int total = 132;

            for (int i = 0; i < total; i++) {
               float h = hash(this.seed, 91000 + i);
               float q = hash(this.seed, 91100 + i);
               MitamaAtomicEdictVfxClient.AtomicCast.RadialStrand base = this.radialStrands[i % this.radialStrands.length];
               double baseAngle = base.angle + Math.sin(time * 0.01 + base.kink * 9.0) * 0.018;
               double maxLen = Math.min(base.length, this.radius * 1.02);
               double startR = 0.17;
               double pathR = startR + (maxLen - startR) * (0.1 + q * 0.88);
               double sideAngle = baseAngle + (float) (Math.PI / 2);
               double sideOffset = this.radius * (q - 0.5) * 0.11;
               double sx = x + Math.cos(baseAngle) * pathR + Math.cos(sideAngle) * sideOffset;
               double sz = z + Math.sin(baseAngle) * pathR + Math.sin(sideAngle) * sideOffset;
               double sy = this.terrainY(sx, y, sz) + 0.08 + h * 3.35;
               double ex = Mth.lerp(pull, sx, x + Math.cos(baseAngle + pull * 0.35) * (0.02 + q * 0.05));
               double ez = Mth.lerp(pull, sz, z + Math.sin(baseAngle + pull * 0.35) * (0.02 + q * 0.05));
               double ey = Mth.lerp(pull, sy, y + 1.08 + h * 0.14);
               MitamaAtomicEdictVfxClient.AtomicCast.Color c = rainbow(time, h + q, 1.0F, 7.2F, 0.001F);
               float alpha = Mth.clamp(flash * (0.96F + pull * 0.78F), 0.0F, 1.0F);
               wrote |= addRibbon(buffer, matrix, sx, sy, sz, ex, ey, ez, 0.06F + h * 0.03F, 125, 0, 255, alpha * 0.66F);
               wrote |= addRibbon(buffer, matrix, sx, sy + 0.018, sz, ex, ey + 0.018, ez, 0.015F + h * 0.009F, 255, 235, 255, alpha);
               wrote |= addRibbon(buffer, matrix, sx, sy + 0.034, sz, ex, ey + 0.034, ez, 0.0072F + q * 0.007F, c.r, c.g, c.b, alpha * 0.94F);
               if ((i & 7) == 0) {
                  wrote |= add3DSquare(
                     buffer,
                     matrix,
                     sx,
                     sy,
                     sz,
                     0.042F + q * 0.046F,
                     time * (0.074 + h * 0.042),
                     time * (0.048 + h * 0.032),
                     -time * (0.04 + q * 0.03),
                     alpha * 0.82F
                  );
               }
            }

            safeDraw(buffer, wrote);
         }
      }

      private static boolean addHorizontalRing(
         BufferBuilder buffer,
         Matrix4f matrix,
         double x,
         double y,
         double z,
         double inner,
         double outer,
         float thickness,
         int red,
         int green,
         int blue,
         float alpha
      ) {
         if (!(alpha <= 5.0E-4F) && !(outer <= inner)) {
            boolean wrote = false;
            int slices = 128;

            for (int i = 0; i < slices; i++) {
               double a0 = i * (float) (Math.PI * 2) / slices;
               double a1 = (i + 1) * (float) (Math.PI * 2) / slices;
               float pulse0 = 0.8F + 0.2F * (float)Math.sin(i * 0.38);
               float pulse1 = 0.8F + 0.2F * (float)Math.sin((i + 1) * 0.38);
               diskVertex(buffer, matrix, x, y - thickness * 0.35, z, inner, a0, red, green, blue, alpha * 0.26F * pulse0);
               diskVertex(buffer, matrix, x, y + thickness * 0.35, z, outer, a0, red, green, blue, alpha * pulse0);
               diskVertex(buffer, matrix, x, y + thickness * 0.35, z, outer, a1, red, green, blue, alpha * pulse1);
               diskVertex(buffer, matrix, x, y - thickness * 0.35, z, inner, a1, red, green, blue, alpha * 0.26F * pulse1);
               wrote = true;
            }

            return wrote;
         } else {
            return false;
         }
      }

      private void renderExpandingBeamCore(Matrix4f matrix, double x, double y, double z, double time, float live) {
         float expansion = smooth(Mth.clamp((float)((time - this.releaseTick) / 54.0), 0.0F, 1.0F));
         float blastPulse = 0.9F + 0.1F * (float)Math.sin(time * 1.2);
         double beamRadius = this.radius * (0.46 + expansion * 3.42) * blastPulse;
         double height = this.radius * (30.0 + expansion * 24.0);
         double baseY = y - Math.max(96.0, this.radius * 4.25);
         float alpha = Mth.clamp(live * (3.65F + expansion * 1.45F), 0.0F, 1.0F);
         this.renderBeamSaturatingPurpleCore(matrix, x, baseY, z, beamRadius * 0.92, height, alpha, time);
         this.renderBeamFilledColumn(matrix, x, baseY - 0.08, z, beamRadius * 0.92, height, alpha, time);
         this.renderBeamFilledColumn(matrix, x, baseY - 0.22, z, beamRadius * 1.18, height * 0.98, alpha, time + 31.0);
         this.renderBeamCorePlanes(matrix, x, baseY - 0.2, z, beamRadius * 1.55, height * 1.04, alpha, time);
         this.renderNoisyCylinder(matrix, x, baseY, z, 0.0, beamRadius * 1.34, height, 255, 230, 255, alpha, 256, time, 0.035 + expansion * 0.075);
         this.renderNoisyCylinder(
            matrix,
            x,
            baseY + 0.2,
            z,
            beamRadius * 0.16,
            beamRadius * 1.92,
            height * 0.98,
            220,
            70,
            255,
            alpha * 0.96F,
            256,
            time + 17.0,
            0.18 + expansion * 0.26
         );
         this.renderBeamShockCloud(matrix, x, y, z, beamRadius, height, alpha, time);
         this.renderFlatDisk(matrix, x, y + 0.018, z, 0.0, beamRadius * 1.28, 255, 238, 255, alpha, 256);
         this.renderFlatDisk(matrix, x, y + 0.032, z, beamRadius * 0.3, beamRadius * 2.15, 190, 32, 255, alpha * 0.88F, 256);
         this.renderFlatDisk(matrix, x, y + 0.05, z, beamRadius * 1.1, beamRadius * 3.35, 115, 0, 255, alpha * 0.74F, 256);
         this.renderFlatDisk(matrix, x, y + 0.068, z, beamRadius * 2.1, beamRadius * 4.75, 100, 0, 255, alpha * 0.34F, 256);
      }

      private void renderExpandingBeamGlow(Matrix4f matrix, double x, double y, double z, double time, float live) {
         float expansion = smooth(Mth.clamp((float)((time - this.releaseTick) / 76.0), 0.0F, 1.0F));
         double beamRadius = this.radius * (0.74 + expansion * 4.2);
         double height = this.radius * (32.0 + expansion * 26.0);
         double baseY = y - Math.max(112.0, this.radius * 4.8);
         float alpha = Mth.clamp(live * 4.6F, 0.0F, 1.0F);
         this.renderEventHorizonBeamWall(matrix, x, baseY, z, beamRadius * 0.9, height, alpha, time, 0);
         this.renderEventHorizonBeamWall(matrix, x, baseY + 0.2, z, beamRadius * 1.24, height * 0.96, alpha, time + 17.0, 1);
         this.renderEventHorizonBeamWall(matrix, x, baseY + 0.6, z, beamRadius * 1.74, height * 0.9, alpha * 0.86F, time + 41.0, 2);
         this.renderSmokeColumn(matrix, x, baseY, z, beamRadius * 1.18, height, alpha * 0.95F, time, 0);
         this.renderSmokeColumn(matrix, x, baseY + 0.5, z, beamRadius * 1.62, height * 0.92, alpha * 0.78F, time + 31.0, 1);
         this.renderSmokeColumn(matrix, x, baseY + 1.0, z, beamRadius * 2.18, height * 0.8, alpha * 0.58F, time + 67.0, 2);
         this.renderBeamFatSmokeSheet(matrix, x, baseY, z, beamRadius * 1.1, height, alpha, time, 0.0);
         this.renderBeamFatSmokeSheet(matrix, x, baseY + 0.35, z, beamRadius * 1.46, height * 0.88, alpha * 0.74F, time + 12.0, 0.55);
         this.renderBeamFatSmokeSheet(matrix, x, baseY + 0.8, z, beamRadius * 1.98, height * 0.76, alpha * 0.52F, time + 29.0, 1.1);
         this.renderBeamLightningVeins(matrix, x, baseY, z, beamRadius * 1.42, height * 0.82, alpha, time);
         this.renderBeamRadialBlades(matrix, x, baseY, z, beamRadius * 1.52, height * 0.72, alpha, time);
         this.renderBeamDenseVerticalRain(matrix, x, baseY, z, beamRadius * 1.74, height * 0.62, alpha, time);
         this.renderBeamPurpleOverpressure(matrix, x, baseY, z, beamRadius * 1.34, height * 0.96, alpha, time);
         this.renderBeamCircuitStorm(matrix, x, baseY, z, beamRadius * 1.5, height * 0.84, alpha, time);
         this.renderBeamRisingAsh(matrix, x, y, z, beamRadius * 1.85, height * 0.46, alpha, time);
         this.renderBeamOrbitalSquareStorm(matrix, x, y, z, beamRadius * 1.75, height * 0.48, alpha, time);
         this.renderBeamOrbitalSquareStorm(matrix, x, y + 0.65, z, beamRadius * 2.1, height * 0.58, alpha * 0.86F, time + 33.0);
         this.renderWorldEndingEdgeAscenders(matrix, x, baseY, z, beamRadius * 2.28, height * 0.86, alpha, time);
         this.renderFlatDisk(matrix, x, y + 0.04, z, beamRadius * 0.8, beamRadius * 2.9, 140, 0, 255, alpha * 0.34F, 192);
         this.renderFlatDisk(matrix, x, y + 0.06, z, beamRadius * 1.8, beamRadius * 4.9, 96, 0, 255, alpha * 0.28F, 192);
         this.renderFlatDisk(matrix, x, y + 0.082, z, beamRadius * 3.1, beamRadius * 6.4, 82, 0, 255, alpha * 0.15F, 192);
         this.renderPurpleScreenSaturationCore(matrix, x, baseY, z, beamRadius * 1.22, height * 1.02, alpha, time);
         this.renderApocalypticBeamRifts(matrix, x, baseY, z, beamRadius * 2.42, height * 0.72, alpha, time);
         this.renderBeamAuraLayer0(matrix, x, baseY, z, beamRadius, height, alpha * 0.74F, time + 0.0);
         this.renderBeamAuraLayer1(matrix, x, baseY, z, beamRadius, height, alpha * 0.752F, time + 0.37);
         this.renderBeamAuraLayer2(matrix, x, baseY, z, beamRadius, height, alpha * 0.764F, time + 0.74);
         this.renderBeamAuraLayer3(matrix, x, baseY, z, beamRadius, height, alpha * 0.776F, time + 1.11);
         this.renderBeamAuraLayer4(matrix, x, baseY, z, beamRadius, height, alpha * 0.788F, time + 1.48);
         this.renderBeamAuraLayer5(matrix, x, baseY, z, beamRadius, height, alpha * 0.8F, time + 1.85);
         this.renderBeamAuraLayer6(matrix, x, baseY, z, beamRadius, height, alpha * 0.812F, time + 2.22);
         this.renderBeamAuraLayer7(matrix, x, baseY, z, beamRadius, height, alpha * 0.824F, time + 2.59);
         this.renderBeamAuraLayer8(matrix, x, baseY, z, beamRadius, height, alpha * 0.836F, time + 2.96);
         this.renderBeamAuraLayer9(matrix, x, baseY, z, beamRadius, height, alpha * 0.848F, time + 3.33);
         this.renderBeamAuraLayer10(matrix, x, baseY, z, beamRadius, height, alpha * 0.86F, time + 3.7);
         this.renderBeamAuraLayer11(matrix, x, baseY, z, beamRadius, height, alpha * 0.872F, time + 4.07);
         this.renderBeamAuraLayer12(matrix, x, baseY, z, beamRadius, height, alpha * 0.884F, time + 4.44);
         this.renderBeamAuraLayer13(matrix, x, baseY, z, beamRadius, height, alpha * 0.896F, time + 4.81);
         this.renderBeamAuraLayer14(matrix, x, baseY, z, beamRadius, height, alpha * 0.908F, time + 5.18);
         this.renderBeamAuraLayer15(matrix, x, baseY, z, beamRadius, height, alpha * 0.92F, time + 5.55);
         this.renderBeamAuraLayer16(matrix, x, baseY, z, beamRadius, height, alpha * 0.932F, time + 5.92);
         this.renderBeamAuraLayer17(matrix, x, baseY, z, beamRadius, height, alpha * 0.74F, time + 6.29);
         this.renderBeamAuraLayer18(matrix, x, baseY, z, beamRadius, height, alpha * 0.752F, time + 6.66);
         this.renderBeamAuraLayer19(matrix, x, baseY, z, beamRadius, height, alpha * 0.764F, time + 7.03);
         this.renderBeamAuraLayer20(matrix, x, baseY, z, beamRadius, height, alpha * 0.776F, time + 7.4);
         this.renderBeamAuraLayer21(matrix, x, baseY, z, beamRadius, height, alpha * 0.788F, time + 7.77);
         this.renderBeamAuraLayer22(matrix, x, baseY, z, beamRadius, height, alpha * 0.8F, time + 8.14);
         this.renderBeamAuraLayer23(matrix, x, baseY, z, beamRadius, height, alpha * 0.812F, time + 8.51);
         this.renderBeamAuraLayer24(matrix, x, baseY, z, beamRadius, height, alpha * 0.824F, time + 8.88);
         this.renderBeamAuraLayer25(matrix, x, baseY, z, beamRadius, height, alpha * 0.836F, time + 9.25);
         this.renderBeamAuraLayer26(matrix, x, baseY, z, beamRadius, height, alpha * 0.848F, time + 9.62);
         this.renderBeamAuraLayer27(matrix, x, baseY, z, beamRadius, height, alpha * 0.86F, time + 9.99);
         this.renderBeamAuraLayer28(matrix, x, baseY, z, beamRadius, height, alpha * 0.872F, time + 10.36);
         this.renderBeamAuraLayer29(matrix, x, baseY, z, beamRadius, height, alpha * 0.884F, time + 10.73);
         this.renderBeamAuraLayer30(matrix, x, baseY, z, beamRadius, height, alpha * 0.896F, time + 11.1);
         this.renderBeamAuraLayer31(matrix, x, baseY, z, beamRadius, height, alpha * 0.908F, time + 11.47);
         this.renderBeamAuraLayer32(matrix, x, baseY, z, beamRadius, height, alpha * 0.92F, time + 11.84);
         this.renderBeamAuraLayer33(matrix, x, baseY, z, beamRadius, height, alpha * 0.932F, time + 12.21);
         this.renderBeamAuraLayer34(matrix, x, baseY, z, beamRadius, height, alpha * 0.74F, time + 12.58);
         this.renderBeamAuraLayer35(matrix, x, baseY, z, beamRadius, height, alpha * 0.752F, time + 12.95);
         this.renderBeamAuraLayer36(matrix, x, baseY, z, beamRadius, height, alpha * 0.764F, time + 13.32);
         this.renderBeamAuraLayer37(matrix, x, baseY, z, beamRadius, height, alpha * 0.776F, time + 13.69);
         this.renderBeamAuraLayer38(matrix, x, baseY, z, beamRadius, height, alpha * 0.788F, time + 14.06);
         this.renderBeamAuraLayer39(matrix, x, baseY, z, beamRadius, height, alpha * 0.8F, time + 14.43);
         this.renderBeamAuraLayer40(matrix, x, baseY, z, beamRadius, height, alpha * 0.812F, time + 14.8);
         this.renderBeamAuraLayer41(matrix, x, baseY, z, beamRadius, height, alpha * 0.824F, time + 15.17);
         this.renderBeamAuraLayer42(matrix, x, baseY, z, beamRadius, height, alpha * 0.836F, time + 15.54);
         this.renderBeamAuraLayer43(matrix, x, baseY, z, beamRadius, height, alpha * 0.848F, time + 15.91);
         this.renderBeamAuraLayer44(matrix, x, baseY, z, beamRadius, height, alpha * 0.86F, time + 16.28);
         this.renderBeamAuraLayer45(matrix, x, baseY, z, beamRadius, height, alpha * 0.872F, time + 16.65);
         this.renderBeamAuraLayer46(matrix, x, baseY, z, beamRadius, height, alpha * 0.884F, time + 17.02);
         this.renderBeamAuraLayer47(matrix, x, baseY, z, beamRadius, height, alpha * 0.896F, time + 17.39);
         this.renderBeamAuraLayer48(matrix, x, baseY, z, beamRadius, height, alpha * 0.908F, time + 17.76);
         this.renderBeamAuraLayer49(matrix, x, baseY, z, beamRadius, height, alpha * 0.92F, time + 18.13);
         this.renderBeamAuraLayer50(matrix, x, baseY, z, beamRadius, height, alpha * 0.932F, time + 18.5);
         this.renderBeamAuraLayer51(matrix, x, baseY, z, beamRadius, height, alpha * 0.74F, time + 18.87);
         this.renderBeamAuraLayer52(matrix, x, baseY, z, beamRadius, height, alpha * 0.752F, time + 19.24);
         this.renderBeamAuraLayer53(matrix, x, baseY, z, beamRadius, height, alpha * 0.764F, time + 19.61);
         this.renderBeamAuraLayer54(matrix, x, baseY, z, beamRadius, height, alpha * 0.776F, time + 19.98);
         this.renderBeamAuraLayer55(matrix, x, baseY, z, beamRadius, height, alpha * 0.788F, time + 20.35);
         this.renderBeamAuraLayer56(matrix, x, baseY, z, beamRadius, height, alpha * 0.8F, time + 20.72);
         this.renderBeamAuraLayer57(matrix, x, baseY, z, beamRadius, height, alpha * 0.812F, time + 21.09);
         this.renderBeamAuraLayer58(matrix, x, baseY, z, beamRadius, height, alpha * 0.824F, time + 21.46);
         this.renderBeamAuraLayer59(matrix, x, baseY, z, beamRadius, height, alpha * 0.836F, time + 21.83);
         this.renderBeamAuraLayer60(matrix, x, baseY, z, beamRadius, height, alpha * 0.848F, time + 22.2);
         this.renderBeamAuraLayer61(matrix, x, baseY, z, beamRadius, height, alpha * 0.86F, time + 22.57);
         this.renderBeamAuraLayer62(matrix, x, baseY, z, beamRadius, height, alpha * 0.872F, time + 22.94);
         this.renderBeamAuraLayer63(matrix, x, baseY, z, beamRadius, height, alpha * 0.884F, time + 23.31);
         this.renderBeamAuraLayer64(matrix, x, baseY, z, beamRadius, height, alpha * 0.896F, time + 23.68);
         this.renderBeamAuraLayer65(matrix, x, baseY, z, beamRadius, height, alpha * 0.908F, time + 24.05);
         this.renderBeamAuraLayer66(matrix, x, baseY, z, beamRadius, height, alpha * 0.92F, time + 24.42);
         this.renderBeamAuraLayer67(matrix, x, baseY, z, beamRadius, height, alpha * 0.932F, time + 24.79);
         this.renderBeamAuraLayer68(matrix, x, baseY, z, beamRadius, height, alpha * 0.74F, time + 25.16);
         this.renderBeamAuraLayer69(matrix, x, baseY, z, beamRadius, height, alpha * 0.752F, time + 25.53);
         this.renderBeamAuraLayer70(matrix, x, baseY, z, beamRadius, height, alpha * 0.764F, time + 25.9);
         this.renderBeamAuraLayer71(matrix, x, baseY, z, beamRadius, height, alpha * 0.776F, time + 26.27);
         this.renderBeamAuraLayer72(matrix, x, baseY, z, beamRadius, height, alpha * 0.788F, time + 26.64);
         this.renderBeamAuraLayer73(matrix, x, baseY, z, beamRadius, height, alpha * 0.8F, time + 27.01);
         this.renderBeamAuraLayer74(matrix, x, baseY, z, beamRadius, height, alpha * 0.812F, time + 27.38);
         this.renderBeamAuraLayer75(matrix, x, baseY, z, beamRadius, height, alpha * 0.824F, time + 27.75);
         this.renderBeamAuraLayer76(matrix, x, baseY, z, beamRadius, height, alpha * 0.836F, time + 28.12);
         this.renderBeamAuraLayer77(matrix, x, baseY, z, beamRadius, height, alpha * 0.848F, time + 28.49);
         this.renderBeamAuraLayer78(matrix, x, baseY, z, beamRadius, height, alpha * 0.86F, time + 28.86);
         this.renderBeamAuraLayer79(matrix, x, baseY, z, beamRadius, height, alpha * 0.872F, time + 29.23);
         this.renderBeamAuraLayer80(matrix, x, baseY, z, beamRadius, height, alpha * 0.884F, time + 29.6);
         this.renderBeamAuraLayer81(matrix, x, baseY, z, beamRadius, height, alpha * 0.896F, time + 29.97);
         this.renderBeamAuraLayer82(matrix, x, baseY, z, beamRadius, height, alpha * 0.908F, time + 30.34);
         this.renderBeamAuraLayer83(matrix, x, baseY, z, beamRadius, height, alpha * 0.92F, time + 30.71);
         this.renderBeamAuraLayer84(matrix, x, baseY, z, beamRadius, height, alpha * 0.932F, time + 31.08);
         this.renderBeamAuraLayer85(matrix, x, baseY, z, beamRadius, height, alpha * 0.74F, time + 31.45);
         this.renderBeamAuraLayer86(matrix, x, baseY, z, beamRadius, height, alpha * 0.752F, time + 31.82);
         this.renderBeamAuraLayer87(matrix, x, baseY, z, beamRadius, height, alpha * 0.764F, time + 32.19);
         this.renderBeamAuraLayer88(matrix, x, baseY, z, beamRadius, height, alpha * 0.776F, time + 32.56);
         this.renderBeamAuraLayer89(matrix, x, baseY, z, beamRadius, height, alpha * 0.788F, time + 32.93);
         this.renderBeamAuraLayer90(matrix, x, baseY, z, beamRadius, height, alpha * 0.8F, time + 33.3);
         this.renderBeamAuraLayer91(matrix, x, baseY, z, beamRadius, height, alpha * 0.812F, time + 33.67);
         this.renderBeamAuraLayer92(matrix, x, baseY, z, beamRadius, height, alpha * 0.824F, time + 34.04);
         this.renderBeamAuraLayer93(matrix, x, baseY, z, beamRadius, height, alpha * 0.836F, time + 34.41);
         this.renderBeamAuraLayer94(matrix, x, baseY, z, beamRadius, height, alpha * 0.848F, time + 34.78);
         this.renderBeamAuraLayer95(matrix, x, baseY, z, beamRadius, height, alpha * 0.86F, time + 35.15);
         this.renderBeamAuraLayer96(matrix, x, baseY, z, beamRadius, height, alpha * 0.872F, time + 35.52);
         this.renderBeamAuraLayer97(matrix, x, baseY, z, beamRadius, height, alpha * 0.884F, time + 35.89);
         this.renderBeamAuraLayer98(matrix, x, baseY, z, beamRadius, height, alpha * 0.896F, time + 36.26);
         this.renderBeamAuraLayer99(matrix, x, baseY, z, beamRadius, height, alpha * 0.908F, time + 36.63);
         this.renderBeamAuraLayer100(matrix, x, baseY, z, beamRadius, height, alpha * 0.92F, time + 37.0);
         this.renderBeamAuraLayer101(matrix, x, baseY, z, beamRadius, height, alpha * 0.932F, time + 37.37);
         this.renderBeamAuraLayer102(matrix, x, baseY, z, beamRadius, height, alpha * 0.74F, time + 37.74);
         this.renderBeamAuraLayer103(matrix, x, baseY, z, beamRadius, height, alpha * 0.752F, time + 38.11);
         this.renderBeamAuraLayer104(matrix, x, baseY, z, beamRadius, height, alpha * 0.764F, time + 38.48);
         this.renderBeamAuraLayer105(matrix, x, baseY, z, beamRadius, height, alpha * 0.776F, time + 38.85);
         this.renderBeamAuraLayer106(matrix, x, baseY, z, beamRadius, height, alpha * 0.788F, time + 39.22);
         this.renderBeamAuraLayer107(matrix, x, baseY, z, beamRadius, height, alpha * 0.8F, time + 39.59);
         this.renderBeamAuraLayer108(matrix, x, baseY, z, beamRadius, height, alpha * 0.812F, time + 39.96);
         this.renderBeamAuraLayer109(matrix, x, baseY, z, beamRadius, height, alpha * 0.824F, time + 40.33);
         this.renderBeamAuraLayer110(matrix, x, baseY, z, beamRadius, height, alpha * 0.836F, time + 40.7);
         this.renderBeamAuraLayer111(matrix, x, baseY, z, beamRadius, height, alpha * 0.848F, time + 41.07);
         this.renderBeamAuraLayer112(matrix, x, baseY, z, beamRadius, height, alpha * 0.86F, time + 41.44);
         this.renderBeamAuraLayer113(matrix, x, baseY, z, beamRadius, height, alpha * 0.872F, time + 41.81);
         this.renderBeamAuraLayer114(matrix, x, baseY, z, beamRadius, height, alpha * 0.884F, time + 42.18);
         this.renderBeamAuraLayer115(matrix, x, baseY, z, beamRadius, height, alpha * 0.896F, time + 42.55);
         this.renderBeamAuraLayer116(matrix, x, baseY, z, beamRadius, height, alpha * 0.908F, time + 42.92);
         this.renderBeamAuraLayer117(matrix, x, baseY, z, beamRadius, height, alpha * 0.92F, time + 43.29);
         this.renderBeamAuraLayer118(matrix, x, baseY, z, beamRadius, height, alpha * 0.932F, time + 43.66);
         this.renderBeamAuraLayer119(matrix, x, baseY, z, beamRadius, height, alpha * 0.74F, time + 44.03);
         this.renderBeamAuraLayer120(matrix, x, baseY, z, beamRadius, height, alpha * 0.752F, time + 44.4);
         this.renderBeamAuraLayer121(matrix, x, baseY, z, beamRadius, height, alpha * 0.764F, time + 44.77);
         this.renderBeamAuraLayer122(matrix, x, baseY, z, beamRadius, height, alpha * 0.776F, time + 45.14);
         this.renderBeamAuraLayer123(matrix, x, baseY, z, beamRadius, height, alpha * 0.788F, time + 45.51);
         this.renderBeamAuraLayer124(matrix, x, baseY, z, beamRadius, height, alpha * 0.8F, time + 45.88);
         this.renderBeamAuraLayer125(matrix, x, baseY, z, beamRadius, height, alpha * 0.812F, time + 46.25);
         this.renderBeamAuraLayer126(matrix, x, baseY, z, beamRadius, height, alpha * 0.824F, time + 46.62);
         this.renderBeamAuraLayer127(matrix, x, baseY, z, beamRadius, height, alpha * 0.836F, time + 46.99);
         this.renderBeamAuraLayer128(matrix, x, baseY, z, beamRadius, height, alpha * 0.848F, time + 47.36);
         this.renderBeamAuraLayer129(matrix, x, baseY, z, beamRadius, height, alpha * 0.86F, time + 47.73);
         this.renderBeamAuraLayer130(matrix, x, baseY, z, beamRadius, height, alpha * 0.872F, time + 48.1);
         this.renderBeamAuraLayer131(matrix, x, baseY, z, beamRadius, height, alpha * 0.884F, time + 48.47);
         this.renderBeamAuraLayer132(matrix, x, baseY, z, beamRadius, height, alpha * 0.896F, time + 48.84);
         this.renderBeamAuraLayer133(matrix, x, baseY, z, beamRadius, height, alpha * 0.908F, time + 49.21);
         this.renderBeamAuraLayer134(matrix, x, baseY, z, beamRadius, height, alpha * 0.92F, time + 49.58);
         this.renderBeamAuraLayer135(matrix, x, baseY, z, beamRadius, height, alpha * 0.932F, time + 49.95);
         this.renderBeamAuraLayer136(matrix, x, baseY, z, beamRadius, height, alpha * 0.74F, time + 50.32);
         this.renderBeamAuraLayer137(matrix, x, baseY, z, beamRadius, height, alpha * 0.752F, time + 50.69);
         this.renderBeamAuraLayer138(matrix, x, baseY, z, beamRadius, height, alpha * 0.764F, time + 51.06);
         this.renderBeamAuraLayer139(matrix, x, baseY, z, beamRadius, height, alpha * 0.776F, time + 51.43);
         this.renderBeamAuraLayer140(matrix, x, baseY, z, beamRadius, height, alpha * 0.788F, time + 51.8);
         this.renderBeamAuraLayer141(matrix, x, baseY, z, beamRadius, height, alpha * 0.8F, time + 52.17);
         this.renderBeamAuraLayer142(matrix, x, baseY, z, beamRadius, height, alpha * 0.812F, time + 52.54);
         this.renderBeamAuraLayer143(matrix, x, baseY, z, beamRadius, height, alpha * 0.824F, time + 52.91);
         this.renderBeamAuraLayer144(matrix, x, baseY, z, beamRadius, height, alpha * 0.836F, time + 53.28);
         this.renderBeamAuraLayer145(matrix, x, baseY, z, beamRadius, height, alpha * 0.848F, time + 53.65);
         this.renderBeamAuraLayer146(matrix, x, baseY, z, beamRadius, height, alpha * 0.86F, time + 54.02);
         this.renderBeamAuraLayer147(matrix, x, baseY, z, beamRadius, height, alpha * 0.872F, time + 54.39);
         this.renderBeamAuraLayer148(matrix, x, baseY, z, beamRadius, height, alpha * 0.884F, time + 54.76);
         this.renderBeamAuraLayer149(matrix, x, baseY, z, beamRadius, height, alpha * 0.896F, time + 55.13);
         this.renderBeamAuraLayer150(matrix, x, baseY, z, beamRadius, height, alpha * 0.908F, time + 55.5);
         this.renderBeamAuraLayer151(matrix, x, baseY, z, beamRadius, height, alpha * 0.92F, time + 55.87);
         this.renderBeamAuraLayer152(matrix, x, baseY, z, beamRadius, height, alpha * 0.932F, time + 56.24);
         this.renderBeamAuraLayer153(matrix, x, baseY, z, beamRadius, height, alpha * 0.74F, time + 56.61);
         this.renderBeamAuraLayer154(matrix, x, baseY, z, beamRadius, height, alpha * 0.752F, time + 56.98);
         this.renderBeamAuraLayer155(matrix, x, baseY, z, beamRadius, height, alpha * 0.764F, time + 57.35);
         this.renderBeamAuraLayer156(matrix, x, baseY, z, beamRadius, height, alpha * 0.776F, time + 57.72);
         this.renderBeamAuraLayer157(matrix, x, baseY, z, beamRadius, height, alpha * 0.788F, time + 58.09);
         this.renderBeamAuraLayer158(matrix, x, baseY, z, beamRadius, height, alpha * 0.8F, time + 58.46);
         this.renderBeamAuraLayer159(matrix, x, baseY, z, beamRadius, height, alpha * 0.812F, time + 58.83);
         this.renderBeamAuraLayer160(matrix, x, baseY, z, beamRadius, height, alpha * 0.824F, time + 59.2);
         this.renderBeamAuraLayer161(matrix, x, baseY, z, beamRadius, height, alpha * 0.836F, time + 59.57);
         this.renderBeamAuraLayer162(matrix, x, baseY, z, beamRadius, height, alpha * 0.848F, time + 59.94);
         this.renderBeamAuraLayer163(matrix, x, baseY, z, beamRadius, height, alpha * 0.86F, time + 60.31);
         this.renderBeamAuraLayer164(matrix, x, baseY, z, beamRadius, height, alpha * 0.872F, time + 60.68);
         this.renderBeamAuraLayer165(matrix, x, baseY, z, beamRadius, height, alpha * 0.884F, time + 61.05);
         this.renderBeamAuraLayer166(matrix, x, baseY, z, beamRadius, height, alpha * 0.896F, time + 61.42);
         this.renderBeamAuraLayer167(matrix, x, baseY, z, beamRadius, height, alpha * 0.908F, time + 61.79);
         this.renderBeamAuraLayer168(matrix, x, baseY, z, beamRadius, height, alpha * 0.92F, time + 62.16);
         this.renderBeamAuraLayer169(matrix, x, baseY, z, beamRadius, height, alpha * 0.932F, time + 62.53);
         this.renderBeamAuraLayer170(matrix, x, baseY, z, beamRadius, height, alpha * 0.74F, time + 62.9);
         this.renderBeamAuraLayer171(matrix, x, baseY, z, beamRadius, height, alpha * 0.752F, time + 63.27);
         this.renderBeamAuraLayer172(matrix, x, baseY, z, beamRadius, height, alpha * 0.764F, time + 63.64);
         this.renderBeamAuraLayer173(matrix, x, baseY, z, beamRadius, height, alpha * 0.776F, time + 64.01);
         this.renderBeamAuraLayer174(matrix, x, baseY, z, beamRadius, height, alpha * 0.788F, time + 64.38);
         this.renderBeamAuraLayer175(matrix, x, baseY, z, beamRadius, height, alpha * 0.8F, time + 64.75);
         this.renderBeamAuraLayer176(matrix, x, baseY, z, beamRadius, height, alpha * 0.812F, time + 65.12);
         this.renderBeamAuraLayer177(matrix, x, baseY, z, beamRadius, height, alpha * 0.824F, time + 65.49);
         this.renderBeamAuraLayer178(matrix, x, baseY, z, beamRadius, height, alpha * 0.836F, time + 65.86);
         this.renderBeamAuraLayer179(matrix, x, baseY, z, beamRadius, height, alpha * 0.848F, time + 66.23);
         this.renderBeamAuraLayer180(matrix, x, baseY, z, beamRadius, height, alpha * 0.86F, time + 66.6);
         this.renderBeamAuraLayer181(matrix, x, baseY, z, beamRadius, height, alpha * 0.872F, time + 66.97);
         this.renderBeamAuraLayer182(matrix, x, baseY, z, beamRadius, height, alpha * 0.884F, time + 67.34);
         this.renderBeamAuraLayer183(matrix, x, baseY, z, beamRadius, height, alpha * 0.896F, time + 67.71);
         this.renderBeamAuraLayer184(matrix, x, baseY, z, beamRadius, height, alpha * 0.908F, time + 68.08);
         this.renderBeamAuraLayer185(matrix, x, baseY, z, beamRadius, height, alpha * 0.92F, time + 68.45);
         this.renderBeamAuraLayer186(matrix, x, baseY, z, beamRadius, height, alpha * 0.932F, time + 68.82);
         this.renderBeamAuraLayer187(matrix, x, baseY, z, beamRadius, height, alpha * 0.74F, time + 69.19);
         this.renderBeamAuraLayer188(matrix, x, baseY, z, beamRadius, height, alpha * 0.752F, time + 69.56);
         this.renderBeamAuraLayer189(matrix, x, baseY, z, beamRadius, height, alpha * 0.764F, time + 69.93);
         this.renderBeamAuraLayer190(matrix, x, baseY, z, beamRadius, height, alpha * 0.776F, time + 70.3);
         this.renderBeamAuraLayer191(matrix, x, baseY, z, beamRadius, height, alpha * 0.788F, time + 70.67);
         this.renderBeamAuraLayer192(matrix, x, baseY, z, beamRadius, height, alpha * 0.8F, time + 71.04);
         this.renderBeamAuraLayer193(matrix, x, baseY, z, beamRadius, height, alpha * 0.812F, time + 71.41);
         this.renderBeamAuraLayer194(matrix, x, baseY, z, beamRadius, height, alpha * 0.824F, time + 71.78);
         this.renderBeamAuraLayer195(matrix, x, baseY, z, beamRadius, height, alpha * 0.836F, time + 72.15);
         this.renderBeamAuraLayer196(matrix, x, baseY, z, beamRadius, height, alpha * 0.848F, time + 72.52);
         this.renderBeamAuraLayer197(matrix, x, baseY, z, beamRadius, height, alpha * 0.86F, time + 72.89);
         this.renderBeamAuraLayer198(matrix, x, baseY, z, beamRadius, height, alpha * 0.872F, time + 73.26);
         this.renderBeamAuraLayer199(matrix, x, baseY, z, beamRadius, height, alpha * 0.884F, time + 73.63);
         this.renderBeamAuraLayer200(matrix, x, baseY, z, beamRadius, height, alpha * 0.896F, time + 74.0);
         this.renderBeamAuraLayer201(matrix, x, baseY, z, beamRadius, height, alpha * 0.908F, time + 74.37);
         this.renderBeamAuraLayer202(matrix, x, baseY, z, beamRadius, height, alpha * 0.92F, time + 74.74);
         this.renderBeamAuraLayer203(matrix, x, baseY, z, beamRadius, height, alpha * 0.932F, time + 75.11);
         this.renderBeamAuraLayer204(matrix, x, baseY, z, beamRadius, height, alpha * 0.74F, time + 75.48);
         this.renderBeamAuraLayer205(matrix, x, baseY, z, beamRadius, height, alpha * 0.752F, time + 75.85);
         this.renderBeamAuraLayer206(matrix, x, baseY, z, beamRadius, height, alpha * 0.764F, time + 76.22);
         this.renderBeamAuraLayer207(matrix, x, baseY, z, beamRadius, height, alpha * 0.776F, time + 76.59);
         this.renderBeamAuraLayer208(matrix, x, baseY, z, beamRadius, height, alpha * 0.788F, time + 76.96);
         this.renderBeamAuraLayer209(matrix, x, baseY, z, beamRadius, height, alpha * 0.8F, time + 77.33);
         this.renderBeamAuraLayer210(matrix, x, baseY, z, beamRadius, height, alpha * 0.812F, time + 77.7);
         this.renderBeamAuraLayer211(matrix, x, baseY, z, beamRadius, height, alpha * 0.824F, time + 78.07);
         this.renderBeamAuraLayer212(matrix, x, baseY, z, beamRadius, height, alpha * 0.836F, time + 78.44);
         this.renderBeamAuraLayer213(matrix, x, baseY, z, beamRadius, height, alpha * 0.848F, time + 78.81);
         this.renderBeamAuraLayer214(matrix, x, baseY, z, beamRadius, height, alpha * 0.86F, time + 79.18);
         this.renderBeamAuraLayer215(matrix, x, baseY, z, beamRadius, height, alpha * 0.872F, time + 79.55);
         this.renderBeamAuraLayer216(matrix, x, baseY, z, beamRadius, height, alpha * 0.884F, time + 79.92);
         this.renderBeamAuraLayer217(matrix, x, baseY, z, beamRadius, height, alpha * 0.896F, time + 80.29);
         this.renderBeamAuraLayer218(matrix, x, baseY, z, beamRadius, height, alpha * 0.908F, time + 80.66);
         this.renderBeamAuraLayer219(matrix, x, baseY, z, beamRadius, height, alpha * 0.92F, time + 81.03);
         this.renderBeamAuraLayer220(matrix, x, baseY, z, beamRadius, height, alpha * 0.932F, time + 81.4);
         this.renderBeamAuraLayer221(matrix, x, baseY, z, beamRadius, height, alpha * 0.74F, time + 81.77);
         this.renderBeamAuraLayer222(matrix, x, baseY, z, beamRadius, height, alpha * 0.752F, time + 82.14);
         this.renderBeamAuraLayer223(matrix, x, baseY, z, beamRadius, height, alpha * 0.764F, time + 82.51);
         this.renderBeamAuraLayer224(matrix, x, baseY, z, beamRadius, height, alpha * 0.776F, time + 82.88);
         this.renderBeamAuraLayer225(matrix, x, baseY, z, beamRadius, height, alpha * 0.788F, time + 83.25);
         this.renderBeamAuraLayer226(matrix, x, baseY, z, beamRadius, height, alpha * 0.8F, time + 83.62);
         this.renderBeamAuraLayer227(matrix, x, baseY, z, beamRadius, height, alpha * 0.812F, time + 83.99);
         this.renderBeamAuraLayer228(matrix, x, baseY, z, beamRadius, height, alpha * 0.824F, time + 84.36);
         this.renderBeamAuraLayer229(matrix, x, baseY, z, beamRadius, height, alpha * 0.836F, time + 84.73);
         this.renderBeamAuraLayer230(matrix, x, baseY, z, beamRadius, height, alpha * 0.848F, time + 85.1);
         this.renderBeamAuraLayer231(matrix, x, baseY, z, beamRadius, height, alpha * 0.86F, time + 85.47);
         this.renderBeamAuraLayer232(matrix, x, baseY, z, beamRadius, height, alpha * 0.872F, time + 85.84);
         this.renderBeamAuraLayer233(matrix, x, baseY, z, beamRadius, height, alpha * 0.884F, time + 86.21);
         this.renderBeamAuraLayer234(matrix, x, baseY, z, beamRadius, height, alpha * 0.896F, time + 86.58);
         this.renderBeamAuraLayer235(matrix, x, baseY, z, beamRadius, height, alpha * 0.908F, time + 86.95);
         this.renderBeamAuraLayer236(matrix, x, baseY, z, beamRadius, height, alpha * 0.92F, time + 87.32);
         this.renderBeamAuraLayer237(matrix, x, baseY, z, beamRadius, height, alpha * 0.932F, time + 87.69);
         this.renderBeamAuraLayer238(matrix, x, baseY, z, beamRadius, height, alpha * 0.74F, time + 88.06);
         this.renderBeamAuraLayer239(matrix, x, baseY, z, beamRadius, height, alpha * 0.752F, time + 88.43);
         this.renderBeamAuraLayer240(matrix, x, baseY, z, beamRadius, height, alpha * 0.764F, time + 88.8);
         this.renderBeamAuraLayer241(matrix, x, baseY, z, beamRadius, height, alpha * 0.776F, time + 89.17);
         this.renderBeamAuraLayer242(matrix, x, baseY, z, beamRadius, height, alpha * 0.788F, time + 89.54);
         this.renderBeamAuraLayer243(matrix, x, baseY, z, beamRadius, height, alpha * 0.8F, time + 89.91);
         this.renderBeamAuraLayer244(matrix, x, baseY, z, beamRadius, height, alpha * 0.812F, time + 90.28);
         this.renderBeamAuraLayer245(matrix, x, baseY, z, beamRadius, height, alpha * 0.824F, time + 90.65);
         this.renderBeamAuraLayer246(matrix, x, baseY, z, beamRadius, height, alpha * 0.836F, time + 91.02);
         this.renderBeamAuraLayer247(matrix, x, baseY, z, beamRadius, height, alpha * 0.848F, time + 91.39);
         this.renderBeamAuraLayer248(matrix, x, baseY, z, beamRadius, height, alpha * 0.86F, time + 91.76);
         this.renderBeamAuraLayer249(matrix, x, baseY, z, beamRadius, height, alpha * 0.872F, time + 92.13);
         this.renderBeamAuraLayer250(matrix, x, baseY, z, beamRadius, height, alpha * 0.884F, time + 92.5);
         this.renderBeamAuraLayer251(matrix, x, baseY, z, beamRadius, height, alpha * 0.896F, time + 92.87);
         this.renderBeamAuraLayer252(matrix, x, baseY, z, beamRadius, height, alpha * 0.908F, time + 93.24);
         this.renderBeamAuraLayer253(matrix, x, baseY, z, beamRadius, height, alpha * 0.92F, time + 93.61);
         this.renderBeamAuraLayer254(matrix, x, baseY, z, beamRadius, height, alpha * 0.932F, time + 93.98);
         this.renderBeamAuraLayer255(matrix, x, baseY, z, beamRadius, height, alpha * 0.74F, time + 94.35);
         this.renderBeamAuraLayer256(matrix, x, baseY, z, beamRadius, height, alpha * 0.752F, time + 94.72);
         this.renderBeamAuraLayer257(matrix, x, baseY, z, beamRadius, height, alpha * 0.764F, time + 95.09);
         this.renderBeamAuraLayer258(matrix, x, baseY, z, beamRadius, height, alpha * 0.776F, time + 95.46);
         this.renderBeamAuraLayer259(matrix, x, baseY, z, beamRadius, height, alpha * 0.788F, time + 95.83);
         this.renderBeamAuraLayer260(matrix, x, baseY, z, beamRadius, height, alpha * 0.8F, time + 96.2);
         this.renderBeamAuraLayer261(matrix, x, baseY, z, beamRadius, height, alpha * 0.812F, time + 96.57);
         this.renderBeamAuraLayer262(matrix, x, baseY, z, beamRadius, height, alpha * 0.824F, time + 96.94);
         this.renderBeamAuraLayer263(matrix, x, baseY, z, beamRadius, height, alpha * 0.836F, time + 97.31);
         this.renderBeamAuraLayer264(matrix, x, baseY, z, beamRadius, height, alpha * 0.848F, time + 97.68);
         this.renderBeamAuraLayer265(matrix, x, baseY, z, beamRadius, height, alpha * 0.86F, time + 98.05);
         this.renderBeamAuraLayer266(matrix, x, baseY, z, beamRadius, height, alpha * 0.872F, time + 98.42);
         this.renderBeamAuraLayer267(matrix, x, baseY, z, beamRadius, height, alpha * 0.884F, time + 98.79);
         this.renderBeamAuraLayer268(matrix, x, baseY, z, beamRadius, height, alpha * 0.896F, time + 99.16);
         this.renderBeamAuraLayer269(matrix, x, baseY, z, beamRadius, height, alpha * 0.908F, time + 99.53);
         this.renderBeamAuraLayer270(matrix, x, baseY, z, beamRadius, height, alpha * 0.92F, time + 99.9);
         this.renderBeamAuraLayer271(matrix, x, baseY, z, beamRadius, height, alpha * 0.932F, time + 100.27);
         this.renderBeamAuraLayer272(matrix, x, baseY, z, beamRadius, height, alpha * 0.74F, time + 100.64);
         this.renderBeamAuraLayer273(matrix, x, baseY, z, beamRadius, height, alpha * 0.752F, time + 101.01);
         this.renderBeamAuraLayer274(matrix, x, baseY, z, beamRadius, height, alpha * 0.764F, time + 101.38);
         this.renderBeamAuraLayer275(matrix, x, baseY, z, beamRadius, height, alpha * 0.776F, time + 101.75);
         this.renderBeamAuraLayer276(matrix, x, baseY, z, beamRadius, height, alpha * 0.788F, time + 102.12);
         this.renderBeamAuraLayer277(matrix, x, baseY, z, beamRadius, height, alpha * 0.8F, time + 102.49);
         this.renderBeamAuraLayer278(matrix, x, baseY, z, beamRadius, height, alpha * 0.812F, time + 102.86);
         this.renderBeamAuraLayer279(matrix, x, baseY, z, beamRadius, height, alpha * 0.824F, time + 103.23);
         this.renderBeamAuraLayer280(matrix, x, baseY, z, beamRadius, height, alpha * 0.836F, time + 103.6);
         this.renderBeamAuraLayer281(matrix, x, baseY, z, beamRadius, height, alpha * 0.848F, time + 103.97);
         this.renderBeamAuraLayer282(matrix, x, baseY, z, beamRadius, height, alpha * 0.86F, time + 104.34);
         this.renderBeamAuraLayer283(matrix, x, baseY, z, beamRadius, height, alpha * 0.872F, time + 104.71);
         this.renderBeamAuraLayer284(matrix, x, baseY, z, beamRadius, height, alpha * 0.884F, time + 105.08);
         this.renderBeamAuraLayer285(matrix, x, baseY, z, beamRadius, height, alpha * 0.896F, time + 105.45);
         this.renderBeamAuraLayer286(matrix, x, baseY, z, beamRadius, height, alpha * 0.908F, time + 105.82);
         this.renderBeamAuraLayer287(matrix, x, baseY, z, beamRadius, height, alpha * 0.92F, time + 106.19);
         this.renderBeamAuraLayer288(matrix, x, baseY, z, beamRadius, height, alpha * 0.932F, time + 106.56);
         this.renderBeamAuraLayer289(matrix, x, baseY, z, beamRadius, height, alpha * 0.74F, time + 106.93);
         this.renderBeamAuraLayer290(matrix, x, baseY, z, beamRadius, height, alpha * 0.752F, time + 107.3);
         this.renderBeamAuraLayer291(matrix, x, baseY, z, beamRadius, height, alpha * 0.764F, time + 107.67);
         this.renderBeamAuraLayer292(matrix, x, baseY, z, beamRadius, height, alpha * 0.776F, time + 108.04);
         this.renderBeamAuraLayer293(matrix, x, baseY, z, beamRadius, height, alpha * 0.788F, time + 108.41);
         this.renderBeamAuraLayer294(matrix, x, baseY, z, beamRadius, height, alpha * 0.8F, time + 108.78);
         this.renderBeamAuraLayer295(matrix, x, baseY, z, beamRadius, height, alpha * 0.812F, time + 109.15);
         this.renderBeamAuraLayer296(matrix, x, baseY, z, beamRadius, height, alpha * 0.824F, time + 109.52);
         this.renderBeamAuraLayer297(matrix, x, baseY, z, beamRadius, height, alpha * 0.836F, time + 109.89);
         this.renderBeamAuraLayer298(matrix, x, baseY, z, beamRadius, height, alpha * 0.848F, time + 110.26);
         this.renderBeamAuraLayer299(matrix, x, baseY, z, beamRadius, height, alpha * 0.86F, time + 110.63);
         this.renderBeamAuraLayer300(matrix, x, baseY, z, beamRadius, height, alpha * 0.872F, time + 111.0);
         this.renderBeamAuraLayer301(matrix, x, baseY, z, beamRadius, height, alpha * 0.884F, time + 111.37);
         this.renderBeamAuraLayer302(matrix, x, baseY, z, beamRadius, height, alpha * 0.896F, time + 111.74);
         this.renderBeamAuraLayer303(matrix, x, baseY, z, beamRadius, height, alpha * 0.908F, time + 112.11);
         this.renderBeamAuraLayer304(matrix, x, baseY, z, beamRadius, height, alpha * 0.92F, time + 112.48);
         this.renderBeamAuraLayer305(matrix, x, baseY, z, beamRadius, height, alpha * 0.932F, time + 112.85);
         this.renderBeamAuraLayer306(matrix, x, baseY, z, beamRadius, height, alpha * 0.74F, time + 113.22);
         this.renderBeamAuraLayer307(matrix, x, baseY, z, beamRadius, height, alpha * 0.752F, time + 113.59);
         this.renderBeamAuraLayer308(matrix, x, baseY, z, beamRadius, height, alpha * 0.764F, time + 113.96);
         this.renderBeamAuraLayer309(matrix, x, baseY, z, beamRadius, height, alpha * 0.776F, time + 114.33);
         this.renderBeamAuraLayer310(matrix, x, baseY, z, beamRadius, height, alpha * 0.788F, time + 114.7);
         this.renderBeamAuraLayer311(matrix, x, baseY, z, beamRadius, height, alpha * 0.8F, time + 115.07);
         this.renderBeamAuraLayer312(matrix, x, baseY, z, beamRadius, height, alpha * 0.812F, time + 115.44);
         this.renderBeamAuraLayer313(matrix, x, baseY, z, beamRadius, height, alpha * 0.824F, time + 115.81);
         this.renderBeamAuraLayer314(matrix, x, baseY, z, beamRadius, height, alpha * 0.836F, time + 116.18);
         this.renderBeamAuraLayer315(matrix, x, baseY, z, beamRadius, height, alpha * 0.848F, time + 116.55);
         this.renderBeamAuraLayer316(matrix, x, baseY, z, beamRadius, height, alpha * 0.86F, time + 116.92);
         this.renderBeamAuraLayer317(matrix, x, baseY, z, beamRadius, height, alpha * 0.872F, time + 117.29);
         this.renderBeamAuraLayer318(matrix, x, baseY, z, beamRadius, height, alpha * 0.884F, time + 117.66);
         this.renderBeamAuraLayer319(matrix, x, baseY, z, beamRadius, height, alpha * 0.896F, time + 118.03);
         this.renderBeamAuraLayer320(matrix, x, baseY, z, beamRadius, height, alpha * 0.908F, time + 118.4);
         this.renderBeamAuraLayer321(matrix, x, baseY, z, beamRadius, height, alpha * 0.92F, time + 118.77);
         this.renderBeamAuraLayer322(matrix, x, baseY, z, beamRadius, height, alpha * 0.932F, time + 119.14);
         this.renderBeamAuraLayer323(matrix, x, baseY, z, beamRadius, height, alpha * 0.74F, time + 119.51);
         this.renderBeamAuraLayer324(matrix, x, baseY, z, beamRadius, height, alpha * 0.752F, time + 119.88);
         this.renderBeamAuraLayer325(matrix, x, baseY, z, beamRadius, height, alpha * 0.764F, time + 120.25);
         this.renderBeamAuraLayer326(matrix, x, baseY, z, beamRadius, height, alpha * 0.776F, time + 120.62);
         this.renderBeamAuraLayer327(matrix, x, baseY, z, beamRadius, height, alpha * 0.788F, time + 120.99);
         this.renderBeamAuraLayer328(matrix, x, baseY, z, beamRadius, height, alpha * 0.8F, time + 121.36);
         this.renderBeamAuraLayer329(matrix, x, baseY, z, beamRadius, height, alpha * 0.812F, time + 121.73);
         this.renderBeamAuraLayer330(matrix, x, baseY, z, beamRadius, height, alpha * 0.824F, time + 122.1);
         this.renderBeamAuraLayer331(matrix, x, baseY, z, beamRadius, height, alpha * 0.836F, time + 122.47);
         this.renderBeamAuraLayer332(matrix, x, baseY, z, beamRadius, height, alpha * 0.848F, time + 122.84);
         this.renderBeamAuraLayer333(matrix, x, baseY, z, beamRadius, height, alpha * 0.86F, time + 123.21);
         this.renderBeamAuraLayer334(matrix, x, baseY, z, beamRadius, height, alpha * 0.872F, time + 123.58);
         this.renderBeamAuraLayer335(matrix, x, baseY, z, beamRadius, height, alpha * 0.884F, time + 123.95);
         this.renderBeamAuraLayer336(matrix, x, baseY, z, beamRadius, height, alpha * 0.896F, time + 124.32);
         this.renderBeamAuraLayer337(matrix, x, baseY, z, beamRadius, height, alpha * 0.908F, time + 124.69);
         this.renderBeamAuraLayer338(matrix, x, baseY, z, beamRadius, height, alpha * 0.92F, time + 125.06);
         this.renderBeamAuraLayer339(matrix, x, baseY, z, beamRadius, height, alpha * 0.932F, time + 125.43);
      }

      private void renderWorldEndingEdgeAscenders(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         boolean wrote = false;

         for (int i = 0; i < 180; i++) {
            float h = hash(this.seed, 62000 + i);
            float q = hash(this.seed, 63000 + i);
            double a = h * (float) (Math.PI * 2) + Math.sin(time * 0.01 + q * 18.0) * 0.2;
            double edge = beamRadius * (0.78 + q * 0.36);
            double side = a + (i % 2 == 0 ? (float) (Math.PI / 2) : (float) (-Math.PI / 2));
            double cycle = (time * (0.02 + q * 0.038) + h) % 1.0;
            double y0 = y + cycle * height * 0.88;
            double y1 = y0 + height * (0.055 + h * 0.07);
            double x0 = x + Math.cos(a) * edge;
            double z0 = z + Math.sin(a) * edge;
            double x1 = x0 + Math.cos(side) * beamRadius * (0.022 + h * 0.038);
            double z1 = z0 + Math.sin(side) * beamRadius * (0.022 + h * 0.038);
            double x2 = x1 + Math.cos(a) * beamRadius * (0.02 + q * 0.05);
            double z2 = z1 + Math.sin(a) * beamRadius * (0.02 + q * 0.05);
            float fade = smooth(Mth.clamp((float)((height - (y1 - y)) / (height * 0.22)), 0.0F, 1.0F));
            float pulse = 0.74F + 0.26F * (float)Math.sin(time * 0.75 + i * 0.47);
            float aFinal = Mth.clamp(alpha * fade * pulse * (0.62F + q * 0.46F), 0.0F, 1.0F);
            MitamaAtomicEdictVfxClient.AtomicCast.Color c = rainbow(time, h + q, 1.0F, 3.8F, 0.004F);
            wrote |= addRibbon(buffer, matrix, x0, y0, z0, x1, y0 + (y1 - y0) * 0.46, z1, 0.052F + h * 0.04F, 112, 0, 255, aFinal * 0.7F);
            wrote |= addRibbon(buffer, matrix, x1, y0 + (y1 - y0) * 0.46, z1, x2, y1, z2, 0.05F + h * 0.038F, 112, 0, 255, aFinal * 0.7F);
            wrote |= addRibbon(buffer, matrix, x0, y0 + 0.018, z0, x1, y0 + (y1 - y0) * 0.46 + 0.018, z1, 0.014F + h * 0.012F, 255, 235, 255, aFinal);
            wrote |= addRibbon(buffer, matrix, x1, y0 + (y1 - y0) * 0.46 + 0.018, z1, x2, y1 + 0.018, z2, 0.012F + h * 0.012F, c.r, c.g, c.b, aFinal);
            if ((i & 3) == 0) {
               double bx = x2 + Math.cos(side) * beamRadius * (0.035 + q * 0.055);
               double bz = z2 + Math.sin(side) * beamRadius * (0.035 + q * 0.055);
               wrote |= addRibbon(buffer, matrix, x2, y1, z2, bx, y1 + height * 0.018, bz, 0.01F, 255, 205, 255, aFinal * 0.84F);
            }
         }

         safeDraw(buffer, wrote);
      }

      private void renderPurpleScreenSaturationCore(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         boolean wrote = false;

         for (int i = 0; i < 128; i++) {
            double a = i * Math.PI / 128.0 + time * 0.008;
            double wobble = 1.0 + Math.sin(i * 2.13 + time * 0.075) * 0.035;
            double dx = Math.cos(a) * beamRadius * wobble;
            double dz = Math.sin(a) * beamRadius * wobble;
            float band = 0.5F + 0.36F * (float)Math.sin(i * 0.41 + time * 0.65);
            int ap = alpha(alpha * band);
            int ap2 = alpha(alpha * 0.74F);
            buffer.addVertex(matrix, (float)(x - dx), (float)y, (float)(z - dz)).setColor(106, 0, 255, ap2);
            buffer.addVertex(matrix, (float)(x + dx), (float)y, (float)(z + dz)).setColor(255, 226, 255, ap);
            buffer.addVertex(matrix, (float)(x + dx), (float)(y + height), (float)(z + dz)).setColor(255, 226, 255, ap);
            buffer.addVertex(matrix, (float)(x - dx), (float)(y + height), (float)(z - dz)).setColor(106, 0, 255, ap2);
            wrote = true;
         }

         safeDraw(buffer, wrote);
      }

      private void renderApocalypticBeamRifts(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         boolean wrote = false;

         for (int i = 0; i < 96; i++) {
            float h = hash(this.seed, 65000 + i);
            float q = hash(this.seed, 66000 + i);
            double a = h * (float) (Math.PI * 2) + time * (0.006 + q * 0.012);
            double inner = beamRadius * (0.16 + q * 0.42);
            double outer = beamRadius * (0.82 + q * 0.5);
            double y0 = y + (time * (0.018 + q * 0.03) + h) % 1.0 * height * 0.75;
            double y1 = y0 + height * (0.03 + h * 0.06);
            double x0 = x + Math.cos(a) * inner;
            double z0 = z + Math.sin(a) * inner;
            double x1 = x + Math.cos(a) * outer;
            double z1 = z + Math.sin(a) * outer;
            float fade = smooth(Mth.clamp((float)((height - (y1 - y)) / (height * 0.3)), 0.0F, 1.0F));
            float aFinal = Mth.clamp(alpha * fade * (0.7F + h * 0.42F), 0.0F, 1.0F);
            wrote |= addRibbon(buffer, matrix, x0, y0, z0, x1, y1, z1, 0.16F + q * 0.18F, 72, 0, 150, aFinal * 0.44F);
            wrote |= addRibbon(buffer, matrix, x0, y0 + 0.025, z0, x1, y1 + 0.025, z1, 0.04F + q * 0.06F, 190, 35, 255, aFinal * 0.88F);
            wrote |= addRibbon(buffer, matrix, x0, y0 + 0.05, z0, x1, y1 + 0.05, z1, 0.014F + q * 0.018F, 255, 235, 255, aFinal);
         }

         safeDraw(buffer, wrote);
      }

      private void renderBeamInteriorStorm(Matrix4f matrix, double x, double y, double z, double time, float live) {
         float expansion = smooth(Mth.clamp((float)((time - this.releaseTick) / 72.0), 0.0F, 1.0F));
         double beamRadius = this.radius * (0.28 + expansion * 2.15);
         double height = this.radius * (9.5 + expansion * 9.8);
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         boolean wrote = false;

         for (int i = 0; i < 190; i++) {
            float h = hash(this.seed, 35000 + i);
            float q = hash(this.seed, 36000 + i);
            double a = h * (float) (Math.PI * 2) + Math.sin(time * 0.018 + q * 17.0) * 0.28;
            double d = beamRadius * (0.05 + q * 0.98);
            double cycle = (time * (0.042 + q * 0.07) + h) % 1.0;
            double py0 = y - Math.max(8.0, this.radius * 0.65) + cycle * height;
            double py1 = py0 + 1.15 + h * 3.6;
            double drift = Math.sin(time * 0.052 + h * 15.0) * beamRadius * 0.03;
            double px0 = x + Math.cos(a) * d + Math.cos(a + (float) (Math.PI / 2)) * drift;
            double pz0 = z + Math.sin(a) * d + Math.sin(a + (float) (Math.PI / 2)) * drift;
            double px1 = px0 + Math.cos(a + (float) (Math.PI / 2)) * beamRadius * (0.006 + h * 0.014);
            double pz1 = pz0 + Math.sin(a + (float) (Math.PI / 2)) * beamRadius * (0.006 + h * 0.014);
            float fade = smooth(Mth.clamp((float)((height - (py1 - y)) / (height * 0.32)), 0.0F, 1.0F));
            float alpha = Mth.clamp(live * fade * (0.78F + q * 0.72F), 0.0F, 1.0F);
            MitamaAtomicEdictVfxClient.AtomicCast.Color c = rainbow(time, h, 1.0F, 3.1F, 0.008F);
            wrote |= addRibbon(buffer, matrix, px0, py0, pz0, px1, py1, pz1, 0.028F + h * 0.026F, 255, 248, 255, alpha);
            wrote |= addRibbon(buffer, matrix, px0, py0 + 0.018, pz0, px1, py1 + 0.018, pz1, 0.076F + h * 0.058F, c.r, c.g, c.b, alpha * 0.46F);
            if ((i & 3) == 0) {
               double branchA = a + (h > 0.5F ? (float) (Math.PI / 2) : (float) (-Math.PI / 2));
               double bx = px1 + Math.cos(branchA) * beamRadius * (0.035 + q * 0.055);
               double bz = pz1 + Math.sin(branchA) * beamRadius * (0.035 + q * 0.055);
               wrote |= addRibbon(
                  buffer, matrix, px1, py1 * 0.985 + py0 * 0.015, pz1, bx, py1 + 0.3 + q * 0.62, bz, 0.018F + h * 0.014F, 255, 190, 255, alpha * 0.8F
               );
            }
         }

         safeDraw(buffer, wrote);
      }

      private void renderBeamFilledColumn(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         if (!(alpha <= 5.0E-4F)) {
            BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
            boolean wrote = false;

            for (int i = 0; i < 96; i++) {
               double a = i * Math.PI / 96.0 + time * 0.004;
               double wobble = 1.0 + Math.sin(i * 1.713 + time * 0.033) * 0.018;
               double dx = Math.cos(a) * beamRadius * wobble;
               double dz = Math.sin(a) * beamRadius * wobble;
               int aa = alpha(alpha * (0.62F + i % 7 * 0.034F));
               int mid = alpha(alpha * (0.82F + i % 3 * 0.045F));
               buffer.addVertex(matrix, (float)(x - dx), (float)y, (float)(z - dz)).setColor(178, 35, 255, mid);
               buffer.addVertex(matrix, (float)(x + dx), (float)y, (float)(z + dz)).setColor(255, 236, 255, aa);
               buffer.addVertex(matrix, (float)(x + dx), (float)(y + height), (float)(z + dz)).setColor(255, 236, 255, aa);
               buffer.addVertex(matrix, (float)(x - dx), (float)(y + height), (float)(z - dz)).setColor(178, 35, 255, mid);
               wrote = true;
            }

            safeDraw(buffer, wrote);
         }
      }

      private void renderSmokeColumn(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time, int layer) {
         int slices = 160;
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         boolean wrote = false;

         for (int i = 0; i < slices; i++) {
            double a0 = i * (float) (Math.PI * 2) / slices;
            double a1 = (i + 1) * (float) (Math.PI * 2) / slices;
            double n0 = this.smokeNoise(a0, time, layer);
            double n1 = this.smokeNoise(a1, time, layer);
            double r0 = beamRadius * n0;
            double r1 = beamRadius * n1;
            double topShift0 = Math.sin(a0 * 3.0 + time * 0.01) * this.radius * 0.4;
            double topShift1 = Math.sin(a1 * 3.0 + time * 0.01) * this.radius * 0.4;
            int red = layer == 0 ? 128 : (layer == 1 ? 92 : 54);
            int green = layer == 0 ? 20 : (layer == 1 ? 8 : 0);
            int blue = 255;
            float rimPulse = 0.6F + 0.4F * (float)Math.sin(i * 0.37 + time * 0.03 + layer);
            float a = alpha * (layer == 0 ? 0.36F : (layer == 1 ? 0.24F : 0.18F)) * rimPulse;
            cylinderVertex(buffer, matrix, x, y, z, r0, a0, red, green, blue, a * 0.45F);
            cylinderVertex(buffer, matrix, x, y + height + topShift0, z, r0 * (0.88 + layer * 0.07), a0, red, green, blue, a);
            cylinderVertex(buffer, matrix, x, y + height + topShift1, z, r1 * (0.88 + layer * 0.07), a1, red, green, blue, a);
            cylinderVertex(buffer, matrix, x, y, z, r1, a1, red, green, blue, a * 0.45F);
            wrote = true;
         }

         safeDraw(buffer, wrote);
      }

      private void renderBeamLightningVeins(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         boolean wrote = false;

         for (int i = 0; i < 90; i++) {
            float h = hash(this.seed, 37000 + i);
            double a = h * (float) (Math.PI * 2) + Math.sin(time * 0.012 + h * 11.0) * 0.2;
            double r = beamRadius * (0.78 + hash(this.seed, 38000 + i) * 0.36);
            double x0 = x + Math.cos(a) * r;
            double z0 = z + Math.sin(a) * r;
            double y0 = y + (time * (0.018 + h * 0.02) + h) % 1.0 * height;
            double step = height * (0.02 + hash(this.seed, 39000 + i) * 0.04);
            double side = a + (h > 0.5F ? (float) (Math.PI / 2) : (float) (-Math.PI / 2));
            double x1 = x0 + Math.cos(side) * beamRadius * (0.03 + h * 0.06);
            double z1 = z0 + Math.sin(side) * beamRadius * (0.03 + h * 0.06);
            double y1 = y0 + step;
            double x2 = x1 + Math.cos(a) * beamRadius * (0.028 + h * 0.04);
            double z2 = z1 + Math.sin(a) * beamRadius * (0.028 + h * 0.04);
            double y2 = y1 + step * 0.65;
            float fade = smooth(Mth.clamp((float)((height - (y2 - y)) / (height * 0.25)), 0.0F, 1.0F));
            float veinAlpha = alpha * fade * (0.46F + h * 0.38F);
            wrote |= addRibbon(buffer, matrix, x0, y0, z0, x1, y1, z1, 0.022F, 255, 222, 255, veinAlpha);
            wrote |= addRibbon(buffer, matrix, x1, y1, z1, x2, y2, z2, 0.018F, 178, 44, 255, veinAlpha * 0.86F);
         }

         safeDraw(buffer, wrote);
      }

      private void renderBeamRisingAsh(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         boolean wrote = false;

         for (int i = 0; i < 58; i++) {
            float h = hash(this.seed, 40000 + i);
            float q = hash(this.seed, 41000 + i);
            double a = h * (float) (Math.PI * 2) + time * (0.012 + q * 0.02);
            double d = beamRadius * (0.2 + q * 0.82);
            double py = y + 0.5 + (time * (0.018 + h * 0.028) + q) % 1.0 * height;
            float fade = smooth(Mth.clamp((float)((height - (py - y)) / (height * 0.28)), 0.0F, 1.0F));
            float aFinal = alpha * fade * 0.72F;
            if (!(aFinal <= 0.001F)) {
               double px = x + Math.cos(a) * d;
               double pz = z + Math.sin(a) * d;
               float size = 0.18F + h * 0.55F;
               double spin = time * (0.035 + h * 0.042) + a;
               wrote |= addOrientedSquareFill(buffer, matrix, px, py, pz, size * 0.86F, spin, aFinal * 0.48F);
               wrote |= addOrientedSquareOutline(buffer, matrix, px, py + 0.016, pz, size, spin, 180, 48, 255, aFinal);
            }
         }

         safeDraw(buffer, wrote);
      }

      private double smokeNoise(double a, double time, int layer) {
         return 1.0
            + Math.sin(a * 3.0 + time * (0.02 + layer * 0.004)) * (0.1 + layer * 0.035)
            + Math.sin(a * 7.0 - time * (0.017 + layer * 0.003)) * (0.08 + layer * 0.03)
            + Math.sin(a * 13.0 + time * (0.031 + layer * 0.002)) * (0.055 + layer * 0.02);
      }

      private void renderEventHorizonBeamWall(
         Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time, int layer
      ) {
         int slices = 192;
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         boolean wrote = false;

         for (int i = 0; i < slices; i++) {
            double u0 = (double)i / slices;
            double u1 = (double)(i + 1) / slices;
            double a0 = u0 * (float) (Math.PI * 2);
            double a1 = u1 * (float) (Math.PI * 2);
            double n0 = this.beamWallNoise(u0, time, layer);
            double n1 = this.beamWallNoise(u1, time, layer);
            double r0 = beamRadius * n0;
            double r1 = beamRadius * n1;
            double top0 = height * (0.92 + 0.04 * Math.sin(a0 * 4.0 + time * 0.026 + layer));
            double top1 = height * (0.92 + 0.04 * Math.sin(a1 * 4.0 + time * 0.026 + layer));
            float streak0 = 0.55F + 0.45F * (float)Math.sin(u0 * 420.0 + time * 0.22 + layer * 1.7);
            float streak1 = 0.55F + 0.45F * (float)Math.sin(u1 * 420.0 + time * 0.22 + layer * 1.7);
            int red = layer == 0 ? 210 : (layer == 1 ? 150 : 92);
            int green = layer == 0 ? 80 : (layer == 1 ? 28 : 4);
            int blue = 255;
            float aBase = alpha * (layer == 0 ? 0.34F : (layer == 1 ? 0.25F : 0.17F));
            cylinderVertex(buffer, matrix, x, y, z, r0, a0, red, green, blue, aBase * 0.18F);
            cylinderVertex(buffer, matrix, x, y + top0, z, r0 * (0.9 + layer * 0.05), a0, 255, 210, 255, aBase * streak0);
            cylinderVertex(buffer, matrix, x, y + top1, z, r1 * (0.9 + layer * 0.05), a1, 255, 210, 255, aBase * streak1);
            cylinderVertex(buffer, matrix, x, y, z, r1, a1, red, green, blue, aBase * 0.18F);
            wrote = true;
         }

         safeDraw(buffer, wrote);
      }

      private void renderBeamFatSmokeSheet(
         Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time, double phase
      ) {
         int strips = 72;
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         boolean wrote = false;

         for (int i = 0; i < strips; i++) {
            double a = i * (float) (Math.PI * 2) / strips + phase;
            double side = a + (float) (Math.PI / 2);
            double r = beamRadius * (0.82 + 0.24 * Math.sin(i * 0.88 + time * 0.032 + phase));
            double width = beamRadius * (0.025 + 0.035 * hash(this.seed, 43000 + i));
            double x0 = x + Math.cos(a) * r;
            double z0 = z + Math.sin(a) * r;
            double x1 = x0 + Math.cos(side) * width;
            double z1 = z0 + Math.sin(side) * width;
            float line = 0.42F + 0.58F * (float)Math.sin(time * 0.1 + i * 1.91);
            float a0 = alpha * 0.34F * line;
            wrote |= addRibbon(buffer, matrix, x0, y, z0, x1, y + height, z1, (float)(beamRadius * 0.01), 170, 24, 255, a0);
            wrote |= addRibbon(buffer, matrix, x0, y + 0.025, z0, x1, y + height * 0.96, z1, (float)(beamRadius * 0.0035), 255, 230, 255, a0 * 0.92F);
         }

         safeDraw(buffer, wrote);
      }

      private void renderBeamRadialBlades(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         boolean wrote = false;

         for (int i = 0; i < 64; i++) {
            float h = hash(this.seed, 44000 + i);
            double a = h * (float) (Math.PI * 2) + time * (0.008 + h * 0.006);
            double inner = beamRadius * (0.28 + hash(this.seed, 45000 + i) * 0.42);
            double outer = beamRadius * (0.9 + hash(this.seed, 46000 + i) * 0.6);
            double py0 = y + (time * (0.026 + h * 0.018) + h) % 1.0 * height;
            double py1 = py0 + height * (0.04 + hash(this.seed, 47000 + i) * 0.06);
            double x0 = x + Math.cos(a) * inner;
            double z0 = z + Math.sin(a) * inner;
            double x1 = x + Math.cos(a) * outer;
            double z1 = z + Math.sin(a) * outer;
            float fade = smooth(Mth.clamp((float)((height - (py1 - y)) / (height * 0.26)), 0.0F, 1.0F));
            float aFinal = alpha * fade * (0.3F + h * 0.34F);
            MitamaAtomicEdictVfxClient.AtomicCast.Color c = rainbow(time, h, 1.0F, 2.5F, 0.03F);
            wrote |= addRibbon(buffer, matrix, x0, py0, z0, x1, py1, z1, 0.05F + h * 0.05F, c.r, c.g, c.b, aFinal * 0.52F);
            wrote |= addRibbon(buffer, matrix, x0, py0 + 0.012, z0, x1, py1 + 0.012, z1, 0.018F + h * 0.014F, 255, 240, 255, aFinal);
         }

         safeDraw(buffer, wrote);
      }

      private void renderBeamDenseVerticalRain(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         boolean wrote = false;

         for (int i = 0; i < 260; i++) {
            float h = hash(this.seed, 48000 + i);
            float q = hash(this.seed, 49000 + i);
            double a = h * (float) (Math.PI * 2);
            double d = beamRadius * (0.1 + q * 0.92);
            double x0 = x + Math.cos(a) * d;
            double z0 = z + Math.sin(a) * d;
            double cycle = (time * (0.038 + q * 0.042) + h) % 1.0;
            double y0 = y + cycle * height;
            double y1 = y0 + height * (0.018 + h * 0.028);
            float fade = smooth(Mth.clamp((float)((height - (y1 - y)) / (height * 0.22)), 0.0F, 1.0F));
            float aFinal = alpha * fade * (0.24F + h * 0.46F);
            int rr = h > 0.62F ? 255 : 170;
            int gg = h > 0.62F ? 235 : 35;
            int bb = 255;
            wrote |= addRibbon(buffer, matrix, x0, y0, z0, x0, y1, z0, 0.012F + h * 0.018F, rr, gg, bb, aFinal);
         }

         safeDraw(buffer, wrote);
      }

      private void renderBeamCorePlanes(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         boolean wrote = false;

         for (int i = 0; i < 72; i++) {
            double a = i * Math.PI / 72.0 + time * 0.006;
            double wobble = 1.0 + Math.sin(i * 0.97 + time * 0.052) * 0.032;
            double dx = Math.cos(a) * beamRadius * wobble;
            double dz = Math.sin(a) * beamRadius * wobble;
            float band = 0.22F + 0.12F * (i % 4);
            int aa = alpha(alpha * band);
            buffer.addVertex(matrix, (float)(x - dx), (float)y, (float)(z - dz)).setColor(255, 255, 255, 0);
            buffer.addVertex(matrix, (float)(x + dx), (float)y, (float)(z + dz)).setColor(255, 255, 255, aa);
            buffer.addVertex(matrix, (float)(x + dx), (float)(y + height), (float)(z + dz)).setColor(255, 255, 255, aa);
            buffer.addVertex(matrix, (float)(x - dx), (float)(y + height), (float)(z - dz)).setColor(255, 255, 255, 0);
            wrote = true;
         }

         safeDraw(buffer, wrote);
      }

      private void renderBeamOrbitalSquareStorm(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         boolean wrote = false;

         for (int i = 0; i < 128; i++) {
            float h = hash(this.seed, 50000 + i);
            float q = hash(this.seed, 51000 + i);
            double cycle = (time * (0.018 + h * 0.03) + q) % 1.0;
            double py = y + 0.25 + cycle * height;
            float fade = smooth(Mth.clamp((float)((1.0 - cycle) / 0.28), 0.0F, 1.0F));
            float finalAlpha = Mth.clamp(alpha * fade * (0.88F + q * 0.85F), 0.0F, 1.0F);
            double a = h * (float) (Math.PI * 2) + time * (0.02 + q * 0.028);
            double d = beamRadius * (0.3 + q * 0.86);
            double px = x + Math.cos(a) * d;
            double pz = z + Math.sin(a) * d;
            float size = 0.34F + h * 0.92F;
            double spin = time * (0.06 + q * 0.035) + a;
            wrote |= addOrientedSquareFill(buffer, matrix, px, py, pz, size * 0.74F, spin, finalAlpha * 0.42F);
            wrote |= addOrientedSquareOutline(buffer, matrix, px, py + 0.012, pz, size, spin, 180, 35, 255, finalAlpha);
            wrote |= addOrientedSquareOutline(buffer, matrix, px, py + 0.026, pz, size * 0.68F, -spin * 0.72, 255, 192, 255, finalAlpha * 0.78F);
         }

         safeDraw(buffer, wrote);
      }

      private void renderBeamShockCloud(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderSphere(
            matrix,
            x,
            y + this.radius * 0.7,
            z,
            (float)(beamRadius * 1.72),
            (float)(this.radius * 0.95),
            170,
            38,
            255,
            alpha * 0.28F,
            12,
            80,
            time * 0.014,
            1.2F
         );
         this.renderSphere(
            matrix, x, y + this.radius * 0.95, z, (float)(beamRadius * 2.2), (float)(this.radius * 1.2), 95, 0, 255, alpha * 0.16F, 12, 80, -time * 0.01, 1.8F
         );
      }

      private double beamWallNoise(double u, double time, int layer) {
         return 1.0
            + Math.sin(u * (float) (Math.PI * 2) * 3.0 + time * (0.032 + layer * 0.006)) * (0.09 + layer * 0.03)
            + Math.sin(u * (float) (Math.PI * 2) * 9.0 - time * (0.026 + layer * 0.004)) * (0.07 + layer * 0.024)
            + Math.sin(u * (float) (Math.PI * 2) * 21.0 + time * (0.048 + layer * 0.003)) * (0.045 + layer * 0.016);
      }

      private void renderBeamSmokeBand(
         Matrix4f matrix,
         double x,
         double y,
         double z,
         double beamRadius,
         double height,
         float alpha,
         double time,
         int band,
         double scale,
         double speed,
         double twist,
         float redBias,
         float greenBias,
         float blueBias
      ) {
         int slices = 96;
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         boolean wrote = false;

         for (int i = 0; i < slices; i++) {
            double u0 = (double)i / slices;
            double u1 = (double)(i + 1) / slices;
            double a0 = u0 * (float) (Math.PI * 2) + twist;
            double a1 = u1 * (float) (Math.PI * 2) + twist;
            double wave0 = 1.0
               + Math.sin(u0 * (float) (Math.PI * 2) * (5.0 + band % 7) + time * speed) * 0.12
               + Math.sin(u0 * (float) (Math.PI * 2) * (13.0 + band % 11) - time * speed * 0.73) * 0.07;
            double wave1 = 1.0
               + Math.sin(u1 * (float) (Math.PI * 2) * (5.0 + band % 7) + time * speed) * 0.12
               + Math.sin(u1 * (float) (Math.PI * 2) * (13.0 + band % 11) - time * speed * 0.73) * 0.07;
            double r0 = beamRadius * scale * wave0;
            double r1 = beamRadius * scale * wave1;
            double bottom = y + height * (0.01 * (band % 5));
            double top0 = y + height * (0.72 + 0.2 * Math.sin(u0 * (float) (Math.PI * 2) * 2.0 + time * 0.018 + band));
            double top1 = y + height * (0.72 + 0.2 * Math.sin(u1 * (float) (Math.PI * 2) * 2.0 + time * 0.018 + band));
            float pulse0 = 0.55F + 0.45F * (float)Math.sin(u0 * 300.0 + time * speed * 3.0 + band);
            float pulse1 = 0.55F + 0.45F * (float)Math.sin(u1 * 300.0 + time * speed * 3.0 + band);
            int rr = Mth.clamp((int)(255.0F * redBias), 0, 255);
            int gg = Mth.clamp((int)(255.0F * greenBias), 0, 255);
            int bb = Mth.clamp((int)(255.0F * blueBias), 0, 255);
            float aBase = Mth.clamp(alpha * (0.09F + band % 9 * 0.01F), 0.0F, 1.0F);
            cylinderVertex(buffer, matrix, x, bottom, z, r0, a0, rr, gg, bb, aBase * 0.1F);
            cylinderVertex(buffer, matrix, x, top0, z, r0 * (0.88 + scale * 0.03), a0, rr, gg, bb, aBase * pulse0);
            cylinderVertex(buffer, matrix, x, top1, z, r1 * (0.88 + scale * 0.03), a1, rr, gg, bb, aBase * pulse1);
            cylinderVertex(buffer, matrix, x, bottom, z, r1, a1, rr, gg, bb, aBase * 0.1F);
            wrote = true;
         }

         safeDraw(buffer, wrote);
      }

      private void renderBeamAuraLayer0(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 0, 0.82, 0.016, 0.0, 0.45F, 0.02F, 1.0F);
      }

      private void renderBeamAuraLayer1(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 1, 0.855, 0.019, 0.217, 0.53F, 0.032F, 1.0F);
      }

      private void renderBeamAuraLayer2(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 2, 0.89, 0.022, 0.434, 0.61F, 0.044F, 1.0F);
      }

      private void renderBeamAuraLayer3(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 3, 0.925, 0.025, 0.651, 0.69F, 0.056F, 1.0F);
      }

      private void renderBeamAuraLayer4(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 4, 0.96, 0.028, 0.868, 0.77F, 0.068F, 1.0F);
      }

      private void renderBeamAuraLayer5(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 5, 0.995, 0.031, 1.085, 0.45F, 0.08F, 1.0F);
      }

      private void renderBeamAuraLayer6(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 6, 1.03, 0.034, 1.302, 0.53F, 0.092F, 1.0F);
      }

      private void renderBeamAuraLayer7(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 7, 1.065, 0.037, 1.519, 0.61F, 0.02F, 1.0F);
      }

      private void renderBeamAuraLayer8(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 8, 1.1, 0.04, 1.736, 0.69F, 0.032F, 1.0F);
      }

      private void renderBeamAuraLayer9(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 9, 1.135, 0.043, 1.953, 0.77F, 0.044F, 1.0F);
      }

      private void renderBeamAuraLayer10(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 10, 1.17, 0.046, 2.17, 0.45F, 0.056F, 1.0F);
      }

      private void renderBeamAuraLayer11(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 11, 1.205, 0.049, 2.387, 0.53F, 0.068F, 1.0F);
      }

      private void renderBeamAuraLayer12(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 12, 1.24, 0.052, 2.604, 0.61F, 0.08F, 1.0F);
      }

      private void renderBeamAuraLayer13(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 13, 1.275, 0.055, 2.821, 0.69F, 0.092F, 1.0F);
      }

      private void renderBeamAuraLayer14(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 14, 1.31, 0.058, 3.038, 0.77F, 0.02F, 1.0F);
      }

      private void renderBeamAuraLayer15(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 15, 1.345, 0.061, 3.255, 0.45F, 0.032F, 1.0F);
      }

      private void renderBeamAuraLayer16(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 16, 1.38, 0.064, 3.472, 0.53F, 0.044F, 1.0F);
      }

      private void renderBeamAuraLayer17(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 17, 1.415, 0.016, 3.689, 0.61F, 0.056F, 1.0F);
      }

      private void renderBeamAuraLayer18(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 18, 1.45, 0.019, 3.906, 0.69F, 0.068F, 1.0F);
      }

      private void renderBeamAuraLayer19(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 19, 1.485, 0.022, 4.123, 0.77F, 0.08F, 1.0F);
      }

      private void renderBeamAuraLayer20(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 20, 1.52, 0.025, 4.34, 0.45F, 0.092F, 1.0F);
      }

      private void renderBeamAuraLayer21(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 21, 1.555, 0.028, 4.557, 0.53F, 0.02F, 1.0F);
      }

      private void renderBeamAuraLayer22(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 22, 1.59, 0.031, 4.774, 0.61F, 0.032F, 1.0F);
      }

      private void renderBeamAuraLayer23(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 23, 1.625, 0.034, 4.991, 0.69F, 0.044F, 1.0F);
      }

      private void renderBeamAuraLayer24(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 24, 1.66, 0.037, 5.208, 0.77F, 0.056F, 1.0F);
      }

      private void renderBeamAuraLayer25(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 25, 1.695, 0.04, 5.425, 0.45F, 0.068F, 1.0F);
      }

      private void renderBeamAuraLayer26(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 26, 1.73, 0.043, 5.642, 0.53F, 0.08F, 1.0F);
      }

      private void renderBeamAuraLayer27(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 27, 1.765, 0.046, 5.859, 0.61F, 0.092F, 1.0F);
      }

      private void renderBeamAuraLayer28(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 28, 1.8, 0.049, 6.076, 0.69F, 0.02F, 1.0F);
      }

      private void renderBeamAuraLayer29(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 29, 1.835, 0.052, 0.01, 0.77F, 0.032F, 1.0F);
      }

      private void renderBeamAuraLayer30(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 30, 0.82, 0.055, 0.227, 0.45F, 0.044F, 1.0F);
      }

      private void renderBeamAuraLayer31(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 31, 0.855, 0.058, 0.444, 0.53F, 0.056F, 1.0F);
      }

      private void renderBeamAuraLayer32(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 32, 0.89, 0.061, 0.661, 0.61F, 0.068F, 1.0F);
      }

      private void renderBeamAuraLayer33(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 33, 0.925, 0.064, 0.878, 0.69F, 0.08F, 1.0F);
      }

      private void renderBeamAuraLayer34(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 34, 0.96, 0.016, 1.095, 0.77F, 0.092F, 1.0F);
      }

      private void renderBeamAuraLayer35(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 35, 0.995, 0.019, 1.312, 0.45F, 0.02F, 1.0F);
      }

      private void renderBeamAuraLayer36(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 36, 1.03, 0.022, 1.529, 0.53F, 0.032F, 1.0F);
      }

      private void renderBeamAuraLayer37(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 37, 1.065, 0.025, 1.746, 0.61F, 0.044F, 1.0F);
      }

      private void renderBeamAuraLayer38(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 38, 1.1, 0.028, 1.963, 0.69F, 0.056F, 1.0F);
      }

      private void renderBeamAuraLayer39(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 39, 1.135, 0.031, 2.18, 0.77F, 0.068F, 1.0F);
      }

      private void renderBeamAuraLayer40(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 40, 1.17, 0.034, 2.397, 0.45F, 0.08F, 1.0F);
      }

      private void renderBeamAuraLayer41(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 41, 1.205, 0.037, 2.614, 0.53F, 0.092F, 1.0F);
      }

      private void renderBeamAuraLayer42(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 42, 1.24, 0.04, 2.831, 0.61F, 0.02F, 1.0F);
      }

      private void renderBeamAuraLayer43(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 43, 1.275, 0.043, 3.048, 0.69F, 0.032F, 1.0F);
      }

      private void renderBeamAuraLayer44(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 44, 1.31, 0.046, 3.265, 0.77F, 0.044F, 1.0F);
      }

      private void renderBeamAuraLayer45(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 45, 1.345, 0.049, 3.482, 0.45F, 0.056F, 1.0F);
      }

      private void renderBeamAuraLayer46(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 46, 1.38, 0.052, 3.699, 0.53F, 0.068F, 1.0F);
      }

      private void renderBeamAuraLayer47(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 47, 1.415, 0.055, 3.916, 0.61F, 0.08F, 1.0F);
      }

      private void renderBeamAuraLayer48(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 48, 1.45, 0.058, 4.133, 0.69F, 0.092F, 1.0F);
      }

      private void renderBeamAuraLayer49(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 49, 1.485, 0.061, 4.35, 0.77F, 0.02F, 1.0F);
      }

      private void renderBeamAuraLayer50(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 50, 1.52, 0.064, 4.567, 0.45F, 0.032F, 1.0F);
      }

      private void renderBeamAuraLayer51(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 51, 1.555, 0.016, 4.784, 0.53F, 0.044F, 1.0F);
      }

      private void renderBeamAuraLayer52(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 52, 1.59, 0.019, 5.001, 0.61F, 0.056F, 1.0F);
      }

      private void renderBeamAuraLayer53(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 53, 1.625, 0.022, 5.218, 0.69F, 0.068F, 1.0F);
      }

      private void renderBeamAuraLayer54(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 54, 1.66, 0.025, 5.435, 0.77F, 0.08F, 1.0F);
      }

      private void renderBeamAuraLayer55(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 55, 1.695, 0.028, 5.652, 0.45F, 0.092F, 1.0F);
      }

      private void renderBeamAuraLayer56(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 56, 1.73, 0.031, 5.869, 0.53F, 0.02F, 1.0F);
      }

      private void renderBeamAuraLayer57(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 57, 1.765, 0.034, 6.086, 0.61F, 0.032F, 1.0F);
      }

      private void renderBeamAuraLayer58(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 58, 1.8, 0.037, 0.02, 0.69F, 0.044F, 1.0F);
      }

      private void renderBeamAuraLayer59(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 59, 1.835, 0.04, 0.237, 0.77F, 0.056F, 1.0F);
      }

      private void renderBeamAuraLayer60(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 60, 0.82, 0.043, 0.454, 0.45F, 0.068F, 1.0F);
      }

      private void renderBeamAuraLayer61(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 61, 0.855, 0.046, 0.671, 0.53F, 0.08F, 1.0F);
      }

      private void renderBeamAuraLayer62(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 62, 0.89, 0.049, 0.888, 0.61F, 0.092F, 1.0F);
      }

      private void renderBeamAuraLayer63(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 63, 0.925, 0.052, 1.105, 0.69F, 0.02F, 1.0F);
      }

      private void renderBeamAuraLayer64(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 64, 0.96, 0.055, 1.322, 0.77F, 0.032F, 1.0F);
      }

      private void renderBeamAuraLayer65(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 65, 0.995, 0.058, 1.539, 0.45F, 0.044F, 1.0F);
      }

      private void renderBeamAuraLayer66(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 66, 1.03, 0.061, 1.756, 0.53F, 0.056F, 1.0F);
      }

      private void renderBeamAuraLayer67(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 67, 1.065, 0.064, 1.973, 0.61F, 0.068F, 1.0F);
      }

      private void renderBeamAuraLayer68(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 68, 1.1, 0.016, 2.19, 0.69F, 0.08F, 1.0F);
      }

      private void renderBeamAuraLayer69(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 69, 1.135, 0.019, 2.407, 0.77F, 0.092F, 1.0F);
      }

      private void renderBeamAuraLayer70(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 70, 1.17, 0.022, 2.624, 0.45F, 0.02F, 1.0F);
      }

      private void renderBeamAuraLayer71(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 71, 1.205, 0.025, 2.841, 0.53F, 0.032F, 1.0F);
      }

      private void renderBeamAuraLayer72(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 72, 1.24, 0.028, 3.058, 0.61F, 0.044F, 1.0F);
      }

      private void renderBeamAuraLayer73(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 73, 1.275, 0.031, 3.275, 0.69F, 0.056F, 1.0F);
      }

      private void renderBeamAuraLayer74(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 74, 1.31, 0.034, 3.492, 0.77F, 0.068F, 1.0F);
      }

      private void renderBeamAuraLayer75(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 75, 1.345, 0.037, 3.709, 0.45F, 0.08F, 1.0F);
      }

      private void renderBeamAuraLayer76(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 76, 1.38, 0.04, 3.926, 0.53F, 0.092F, 1.0F);
      }

      private void renderBeamAuraLayer77(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 77, 1.415, 0.043, 4.143, 0.61F, 0.02F, 1.0F);
      }

      private void renderBeamAuraLayer78(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 78, 1.45, 0.046, 4.36, 0.69F, 0.032F, 1.0F);
      }

      private void renderBeamAuraLayer79(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 79, 1.485, 0.049, 4.577, 0.77F, 0.044F, 1.0F);
      }

      private void renderBeamAuraLayer80(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 80, 1.52, 0.052, 4.794, 0.45F, 0.056F, 1.0F);
      }

      private void renderBeamAuraLayer81(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 81, 1.555, 0.055, 5.011, 0.53F, 0.068F, 1.0F);
      }

      private void renderBeamAuraLayer82(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 82, 1.59, 0.058, 5.228, 0.61F, 0.08F, 1.0F);
      }

      private void renderBeamAuraLayer83(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 83, 1.625, 0.061, 5.445, 0.69F, 0.092F, 1.0F);
      }

      private void renderBeamAuraLayer84(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 84, 1.66, 0.064, 5.662, 0.77F, 0.02F, 1.0F);
      }

      private void renderBeamAuraLayer85(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 85, 1.695, 0.016, 5.879, 0.45F, 0.032F, 1.0F);
      }

      private void renderBeamAuraLayer86(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 86, 1.73, 0.019, 6.096, 0.53F, 0.044F, 1.0F);
      }

      private void renderBeamAuraLayer87(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 87, 1.765, 0.022, 0.03, 0.61F, 0.056F, 1.0F);
      }

      private void renderBeamAuraLayer88(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 88, 1.8, 0.025, 0.247, 0.69F, 0.068F, 1.0F);
      }

      private void renderBeamAuraLayer89(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 89, 1.835, 0.028, 0.464, 0.77F, 0.08F, 1.0F);
      }

      private void renderBeamAuraLayer90(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 90, 0.82, 0.031, 0.681, 0.45F, 0.092F, 1.0F);
      }

      private void renderBeamAuraLayer91(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 91, 0.855, 0.034, 0.898, 0.53F, 0.02F, 1.0F);
      }

      private void renderBeamAuraLayer92(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 92, 0.89, 0.037, 1.115, 0.61F, 0.032F, 1.0F);
      }

      private void renderBeamAuraLayer93(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 93, 0.925, 0.04, 1.332, 0.69F, 0.044F, 1.0F);
      }

      private void renderBeamAuraLayer94(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 94, 0.96, 0.043, 1.549, 0.77F, 0.056F, 1.0F);
      }

      private void renderBeamAuraLayer95(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 95, 0.995, 0.046, 1.766, 0.45F, 0.068F, 1.0F);
      }

      private void renderBeamAuraLayer96(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 96, 1.03, 0.049, 1.983, 0.53F, 0.08F, 1.0F);
      }

      private void renderBeamAuraLayer97(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 97, 1.065, 0.052, 2.2, 0.61F, 0.092F, 1.0F);
      }

      private void renderBeamAuraLayer98(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 98, 1.1, 0.055, 2.417, 0.69F, 0.02F, 1.0F);
      }

      private void renderBeamAuraLayer99(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 99, 1.135, 0.058, 2.634, 0.77F, 0.032F, 1.0F);
      }

      private void renderBeamAuraLayer100(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 100, 1.17, 0.061, 2.851, 0.45F, 0.044F, 1.0F);
      }

      private void renderBeamAuraLayer101(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 101, 1.205, 0.064, 3.068, 0.53F, 0.056F, 1.0F);
      }

      private void renderBeamAuraLayer102(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 102, 1.24, 0.016, 3.285, 0.61F, 0.068F, 1.0F);
      }

      private void renderBeamAuraLayer103(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 103, 1.275, 0.019, 3.502, 0.69F, 0.08F, 1.0F);
      }

      private void renderBeamAuraLayer104(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 104, 1.31, 0.022, 3.719, 0.77F, 0.092F, 1.0F);
      }

      private void renderBeamAuraLayer105(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 105, 1.345, 0.025, 3.936, 0.45F, 0.02F, 1.0F);
      }

      private void renderBeamAuraLayer106(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 106, 1.38, 0.028, 4.153, 0.53F, 0.032F, 1.0F);
      }

      private void renderBeamAuraLayer107(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 107, 1.415, 0.031, 4.37, 0.61F, 0.044F, 1.0F);
      }

      private void renderBeamAuraLayer108(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 108, 1.45, 0.034, 4.587, 0.69F, 0.056F, 1.0F);
      }

      private void renderBeamAuraLayer109(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 109, 1.485, 0.037, 4.804, 0.77F, 0.068F, 1.0F);
      }

      private void renderBeamAuraLayer110(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 110, 1.52, 0.04, 5.021, 0.45F, 0.08F, 1.0F);
      }

      private void renderBeamAuraLayer111(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 111, 1.555, 0.043, 5.238, 0.53F, 0.092F, 1.0F);
      }

      private void renderBeamAuraLayer112(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 112, 1.59, 0.046, 5.455, 0.61F, 0.02F, 1.0F);
      }

      private void renderBeamAuraLayer113(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 113, 1.625, 0.049, 5.672, 0.69F, 0.032F, 1.0F);
      }

      private void renderBeamAuraLayer114(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 114, 1.66, 0.052, 5.889, 0.77F, 0.044F, 1.0F);
      }

      private void renderBeamAuraLayer115(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 115, 1.695, 0.055, 6.106, 0.45F, 0.056F, 1.0F);
      }

      private void renderBeamAuraLayer116(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 116, 1.73, 0.058, 0.04, 0.53F, 0.068F, 1.0F);
      }

      private void renderBeamAuraLayer117(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 117, 1.765, 0.061, 0.257, 0.61F, 0.08F, 1.0F);
      }

      private void renderBeamAuraLayer118(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 118, 1.8, 0.064, 0.474, 0.69F, 0.092F, 1.0F);
      }

      private void renderBeamAuraLayer119(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 119, 1.835, 0.016, 0.691, 0.77F, 0.02F, 1.0F);
      }

      private void renderBeamAuraLayer120(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 120, 0.82, 0.019, 0.908, 0.45F, 0.032F, 1.0F);
      }

      private void renderBeamAuraLayer121(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 121, 0.855, 0.022, 1.125, 0.53F, 0.044F, 1.0F);
      }

      private void renderBeamAuraLayer122(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 122, 0.89, 0.025, 1.342, 0.61F, 0.056F, 1.0F);
      }

      private void renderBeamAuraLayer123(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 123, 0.925, 0.028, 1.559, 0.69F, 0.068F, 1.0F);
      }

      private void renderBeamAuraLayer124(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 124, 0.96, 0.031, 1.776, 0.77F, 0.08F, 1.0F);
      }

      private void renderBeamAuraLayer125(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 125, 0.995, 0.034, 1.993, 0.45F, 0.092F, 1.0F);
      }

      private void renderBeamAuraLayer126(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 126, 1.03, 0.037, 2.21, 0.53F, 0.02F, 1.0F);
      }

      private void renderBeamAuraLayer127(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 127, 1.065, 0.04, 2.427, 0.61F, 0.032F, 1.0F);
      }

      private void renderBeamAuraLayer128(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 128, 1.1, 0.043, 2.644, 0.69F, 0.044F, 1.0F);
      }

      private void renderBeamAuraLayer129(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 129, 1.135, 0.046, 2.861, 0.77F, 0.056F, 1.0F);
      }

      private void renderBeamAuraLayer130(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 130, 1.17, 0.049, 3.078, 0.45F, 0.068F, 1.0F);
      }

      private void renderBeamAuraLayer131(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 131, 1.205, 0.052, 3.295, 0.53F, 0.08F, 1.0F);
      }

      private void renderBeamAuraLayer132(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 132, 1.24, 0.055, 3.512, 0.61F, 0.092F, 1.0F);
      }

      private void renderBeamAuraLayer133(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 133, 1.275, 0.058, 3.729, 0.69F, 0.02F, 1.0F);
      }

      private void renderBeamAuraLayer134(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 134, 1.31, 0.061, 3.946, 0.77F, 0.032F, 1.0F);
      }

      private void renderBeamAuraLayer135(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 135, 1.345, 0.064, 4.163, 0.45F, 0.044F, 1.0F);
      }

      private void renderBeamAuraLayer136(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 136, 1.38, 0.016, 4.38, 0.53F, 0.056F, 1.0F);
      }

      private void renderBeamAuraLayer137(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 137, 1.415, 0.019, 4.597, 0.61F, 0.068F, 1.0F);
      }

      private void renderBeamAuraLayer138(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 138, 1.45, 0.022, 4.814, 0.69F, 0.08F, 1.0F);
      }

      private void renderBeamAuraLayer139(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 139, 1.485, 0.025, 5.031, 0.77F, 0.092F, 1.0F);
      }

      private void renderBeamAuraLayer140(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 140, 1.52, 0.028, 5.248, 0.45F, 0.02F, 1.0F);
      }

      private void renderBeamAuraLayer141(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 141, 1.555, 0.031, 5.465, 0.53F, 0.032F, 1.0F);
      }

      private void renderBeamAuraLayer142(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 142, 1.59, 0.034, 5.682, 0.61F, 0.044F, 1.0F);
      }

      private void renderBeamAuraLayer143(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 143, 1.625, 0.037, 5.899, 0.69F, 0.056F, 1.0F);
      }

      private void renderBeamAuraLayer144(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 144, 1.66, 0.04, 6.116, 0.77F, 0.068F, 1.0F);
      }

      private void renderBeamAuraLayer145(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 145, 1.695, 0.043, 0.05, 0.45F, 0.08F, 1.0F);
      }

      private void renderBeamAuraLayer146(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 146, 1.73, 0.046, 0.267, 0.53F, 0.092F, 1.0F);
      }

      private void renderBeamAuraLayer147(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 147, 1.765, 0.049, 0.484, 0.61F, 0.02F, 1.0F);
      }

      private void renderBeamAuraLayer148(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 148, 1.8, 0.052, 0.701, 0.69F, 0.032F, 1.0F);
      }

      private void renderBeamAuraLayer149(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 149, 1.835, 0.055, 0.918, 0.77F, 0.044F, 1.0F);
      }

      private void renderBeamAuraLayer150(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 150, 0.82, 0.058, 1.135, 0.45F, 0.056F, 1.0F);
      }

      private void renderBeamAuraLayer151(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 151, 0.855, 0.061, 1.352, 0.53F, 0.068F, 1.0F);
      }

      private void renderBeamAuraLayer152(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 152, 0.89, 0.064, 1.569, 0.61F, 0.08F, 1.0F);
      }

      private void renderBeamAuraLayer153(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 153, 0.925, 0.016, 1.786, 0.69F, 0.092F, 1.0F);
      }

      private void renderBeamAuraLayer154(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 154, 0.96, 0.019, 2.003, 0.77F, 0.02F, 1.0F);
      }

      private void renderBeamAuraLayer155(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 155, 0.995, 0.022, 2.22, 0.45F, 0.032F, 1.0F);
      }

      private void renderBeamAuraLayer156(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 156, 1.03, 0.025, 2.437, 0.53F, 0.044F, 1.0F);
      }

      private void renderBeamAuraLayer157(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 157, 1.065, 0.028, 2.654, 0.61F, 0.056F, 1.0F);
      }

      private void renderBeamAuraLayer158(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 158, 1.1, 0.031, 2.871, 0.69F, 0.068F, 1.0F);
      }

      private void renderBeamAuraLayer159(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 159, 1.135, 0.034, 3.088, 0.77F, 0.08F, 1.0F);
      }

      private void renderBeamAuraLayer160(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 160, 1.17, 0.037, 3.305, 0.45F, 0.092F, 1.0F);
      }

      private void renderBeamAuraLayer161(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 161, 1.205, 0.04, 3.522, 0.53F, 0.02F, 1.0F);
      }

      private void renderBeamAuraLayer162(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 162, 1.24, 0.043, 3.739, 0.61F, 0.032F, 1.0F);
      }

      private void renderBeamAuraLayer163(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 163, 1.275, 0.046, 3.956, 0.69F, 0.044F, 1.0F);
      }

      private void renderBeamAuraLayer164(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 164, 1.31, 0.049, 4.173, 0.77F, 0.056F, 1.0F);
      }

      private void renderBeamAuraLayer165(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 165, 1.345, 0.052, 4.39, 0.45F, 0.068F, 1.0F);
      }

      private void renderBeamAuraLayer166(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 166, 1.38, 0.055, 4.607, 0.53F, 0.08F, 1.0F);
      }

      private void renderBeamAuraLayer167(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 167, 1.415, 0.058, 4.824, 0.61F, 0.092F, 1.0F);
      }

      private void renderBeamAuraLayer168(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 168, 1.45, 0.061, 5.041, 0.69F, 0.02F, 1.0F);
      }

      private void renderBeamAuraLayer169(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 169, 1.485, 0.064, 5.258, 0.77F, 0.032F, 1.0F);
      }

      private void renderBeamAuraLayer170(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 170, 1.52, 0.016, 5.475, 0.45F, 0.044F, 1.0F);
      }

      private void renderBeamAuraLayer171(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 171, 1.555, 0.019, 5.692, 0.53F, 0.056F, 1.0F);
      }

      private void renderBeamAuraLayer172(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 172, 1.59, 0.022, 5.909, 0.61F, 0.068F, 1.0F);
      }

      private void renderBeamAuraLayer173(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 173, 1.625, 0.025, 6.126, 0.69F, 0.08F, 1.0F);
      }

      private void renderBeamAuraLayer174(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 174, 1.66, 0.028, 0.06, 0.77F, 0.092F, 1.0F);
      }

      private void renderBeamAuraLayer175(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 175, 1.695, 0.031, 0.277, 0.45F, 0.02F, 1.0F);
      }

      private void renderBeamAuraLayer176(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 176, 1.73, 0.034, 0.494, 0.53F, 0.032F, 1.0F);
      }

      private void renderBeamAuraLayer177(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 177, 1.765, 0.037, 0.711, 0.61F, 0.044F, 1.0F);
      }

      private void renderBeamAuraLayer178(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 178, 1.8, 0.04, 0.928, 0.69F, 0.056F, 1.0F);
      }

      private void renderBeamAuraLayer179(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 179, 1.835, 0.043, 1.145, 0.77F, 0.068F, 1.0F);
      }

      private void renderBeamAuraLayer180(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 180, 0.82, 0.046, 1.362, 0.45F, 0.08F, 1.0F);
      }

      private void renderBeamAuraLayer181(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 181, 0.855, 0.049, 1.579, 0.53F, 0.092F, 1.0F);
      }

      private void renderBeamAuraLayer182(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 182, 0.89, 0.052, 1.796, 0.61F, 0.02F, 1.0F);
      }

      private void renderBeamAuraLayer183(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 183, 0.925, 0.055, 2.013, 0.69F, 0.032F, 1.0F);
      }

      private void renderBeamAuraLayer184(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 184, 0.96, 0.058, 2.23, 0.77F, 0.044F, 1.0F);
      }

      private void renderBeamAuraLayer185(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 185, 0.995, 0.061, 2.447, 0.45F, 0.056F, 1.0F);
      }

      private void renderBeamAuraLayer186(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 186, 1.03, 0.064, 2.664, 0.53F, 0.068F, 1.0F);
      }

      private void renderBeamAuraLayer187(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 187, 1.065, 0.016, 2.881, 0.61F, 0.08F, 1.0F);
      }

      private void renderBeamAuraLayer188(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 188, 1.1, 0.019, 3.098, 0.69F, 0.092F, 1.0F);
      }

      private void renderBeamAuraLayer189(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 189, 1.135, 0.022, 3.315, 0.77F, 0.02F, 1.0F);
      }

      private void renderBeamAuraLayer190(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 190, 1.17, 0.025, 3.532, 0.45F, 0.032F, 1.0F);
      }

      private void renderBeamAuraLayer191(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 191, 1.205, 0.028, 3.749, 0.53F, 0.044F, 1.0F);
      }

      private void renderBeamAuraLayer192(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 192, 1.24, 0.031, 3.966, 0.61F, 0.056F, 1.0F);
      }

      private void renderBeamAuraLayer193(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 193, 1.275, 0.034, 4.183, 0.69F, 0.068F, 1.0F);
      }

      private void renderBeamAuraLayer194(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 194, 1.31, 0.037, 4.4, 0.77F, 0.08F, 1.0F);
      }

      private void renderBeamAuraLayer195(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 195, 1.345, 0.04, 4.617, 0.45F, 0.092F, 1.0F);
      }

      private void renderBeamAuraLayer196(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 196, 1.38, 0.043, 4.834, 0.53F, 0.02F, 1.0F);
      }

      private void renderBeamAuraLayer197(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 197, 1.415, 0.046, 5.051, 0.61F, 0.032F, 1.0F);
      }

      private void renderBeamAuraLayer198(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 198, 1.45, 0.049, 5.268, 0.69F, 0.044F, 1.0F);
      }

      private void renderBeamAuraLayer199(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 199, 1.485, 0.052, 5.485, 0.77F, 0.056F, 1.0F);
      }

      private void renderBeamAuraLayer200(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 200, 1.52, 0.055, 5.702, 0.45F, 0.068F, 1.0F);
      }

      private void renderBeamAuraLayer201(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 201, 1.555, 0.058, 5.919, 0.53F, 0.08F, 1.0F);
      }

      private void renderBeamAuraLayer202(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 202, 1.59, 0.061, 6.136, 0.61F, 0.092F, 1.0F);
      }

      private void renderBeamAuraLayer203(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 203, 1.625, 0.064, 0.07, 0.69F, 0.02F, 1.0F);
      }

      private void renderBeamAuraLayer204(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 204, 1.66, 0.016, 0.287, 0.77F, 0.032F, 1.0F);
      }

      private void renderBeamAuraLayer205(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 205, 1.695, 0.019, 0.504, 0.45F, 0.044F, 1.0F);
      }

      private void renderBeamAuraLayer206(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 206, 1.73, 0.022, 0.721, 0.53F, 0.056F, 1.0F);
      }

      private void renderBeamAuraLayer207(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 207, 1.765, 0.025, 0.938, 0.61F, 0.068F, 1.0F);
      }

      private void renderBeamAuraLayer208(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 208, 1.8, 0.028, 1.155, 0.69F, 0.08F, 1.0F);
      }

      private void renderBeamAuraLayer209(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 209, 1.835, 0.031, 1.372, 0.77F, 0.092F, 1.0F);
      }

      private void renderBeamAuraLayer210(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 210, 0.82, 0.034, 1.589, 0.45F, 0.02F, 1.0F);
      }

      private void renderBeamAuraLayer211(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 211, 0.855, 0.037, 1.806, 0.53F, 0.032F, 1.0F);
      }

      private void renderBeamAuraLayer212(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 212, 0.89, 0.04, 2.023, 0.61F, 0.044F, 1.0F);
      }

      private void renderBeamAuraLayer213(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 213, 0.925, 0.043, 2.24, 0.69F, 0.056F, 1.0F);
      }

      private void renderBeamAuraLayer214(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 214, 0.96, 0.046, 2.457, 0.77F, 0.068F, 1.0F);
      }

      private void renderBeamAuraLayer215(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 215, 0.995, 0.049, 2.674, 0.45F, 0.08F, 1.0F);
      }

      private void renderBeamAuraLayer216(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 216, 1.03, 0.052, 2.891, 0.53F, 0.092F, 1.0F);
      }

      private void renderBeamAuraLayer217(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 217, 1.065, 0.055, 3.108, 0.61F, 0.02F, 1.0F);
      }

      private void renderBeamAuraLayer218(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 218, 1.1, 0.058, 3.325, 0.69F, 0.032F, 1.0F);
      }

      private void renderBeamAuraLayer219(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 219, 1.135, 0.061, 3.542, 0.77F, 0.044F, 1.0F);
      }

      private void renderBeamAuraLayer220(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 220, 1.17, 0.064, 3.759, 0.45F, 0.056F, 1.0F);
      }

      private void renderBeamAuraLayer221(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 221, 1.205, 0.016, 3.976, 0.53F, 0.068F, 1.0F);
      }

      private void renderBeamAuraLayer222(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 222, 1.24, 0.019, 4.193, 0.61F, 0.08F, 1.0F);
      }

      private void renderBeamAuraLayer223(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 223, 1.275, 0.022, 4.41, 0.69F, 0.092F, 1.0F);
      }

      private void renderBeamAuraLayer224(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 224, 1.31, 0.025, 4.627, 0.77F, 0.02F, 1.0F);
      }

      private void renderBeamAuraLayer225(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 225, 1.345, 0.028, 4.844, 0.45F, 0.032F, 1.0F);
      }

      private void renderBeamAuraLayer226(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 226, 1.38, 0.031, 5.061, 0.53F, 0.044F, 1.0F);
      }

      private void renderBeamAuraLayer227(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 227, 1.415, 0.034, 5.278, 0.61F, 0.056F, 1.0F);
      }

      private void renderBeamAuraLayer228(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 228, 1.45, 0.037, 5.495, 0.69F, 0.068F, 1.0F);
      }

      private void renderBeamAuraLayer229(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 229, 1.485, 0.04, 5.712, 0.77F, 0.08F, 1.0F);
      }

      private void renderBeamAuraLayer230(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 230, 1.52, 0.043, 5.929, 0.45F, 0.092F, 1.0F);
      }

      private void renderBeamAuraLayer231(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 231, 1.555, 0.046, 6.146, 0.53F, 0.02F, 1.0F);
      }

      private void renderBeamAuraLayer232(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 232, 1.59, 0.049, 0.08, 0.61F, 0.032F, 1.0F);
      }

      private void renderBeamAuraLayer233(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 233, 1.625, 0.052, 0.297, 0.69F, 0.044F, 1.0F);
      }

      private void renderBeamAuraLayer234(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 234, 1.66, 0.055, 0.514, 0.77F, 0.056F, 1.0F);
      }

      private void renderBeamAuraLayer235(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 235, 1.695, 0.058, 0.731, 0.45F, 0.068F, 1.0F);
      }

      private void renderBeamAuraLayer236(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 236, 1.73, 0.061, 0.948, 0.53F, 0.08F, 1.0F);
      }

      private void renderBeamAuraLayer237(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 237, 1.765, 0.064, 1.165, 0.61F, 0.092F, 1.0F);
      }

      private void renderBeamAuraLayer238(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 238, 1.8, 0.016, 1.382, 0.69F, 0.02F, 1.0F);
      }

      private void renderBeamAuraLayer239(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 239, 1.835, 0.019, 1.599, 0.77F, 0.032F, 1.0F);
      }

      private void renderBeamAuraLayer240(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 240, 0.82, 0.022, 1.816, 0.45F, 0.044F, 1.0F);
      }

      private void renderBeamAuraLayer241(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 241, 0.855, 0.025, 2.033, 0.53F, 0.056F, 1.0F);
      }

      private void renderBeamAuraLayer242(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 242, 0.89, 0.028, 2.25, 0.61F, 0.068F, 1.0F);
      }

      private void renderBeamAuraLayer243(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 243, 0.925, 0.031, 2.467, 0.69F, 0.08F, 1.0F);
      }

      private void renderBeamAuraLayer244(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 244, 0.96, 0.034, 2.684, 0.77F, 0.092F, 1.0F);
      }

      private void renderBeamAuraLayer245(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 245, 0.995, 0.037, 2.901, 0.45F, 0.02F, 1.0F);
      }

      private void renderBeamAuraLayer246(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 246, 1.03, 0.04, 3.118, 0.53F, 0.032F, 1.0F);
      }

      private void renderBeamAuraLayer247(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 247, 1.065, 0.043, 3.335, 0.61F, 0.044F, 1.0F);
      }

      private void renderBeamAuraLayer248(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 248, 1.1, 0.046, 3.552, 0.69F, 0.056F, 1.0F);
      }

      private void renderBeamAuraLayer249(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 249, 1.135, 0.049, 3.769, 0.77F, 0.068F, 1.0F);
      }

      private void renderBeamAuraLayer250(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 250, 1.17, 0.052, 3.986, 0.45F, 0.08F, 1.0F);
      }

      private void renderBeamAuraLayer251(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 251, 1.205, 0.055, 4.203, 0.53F, 0.092F, 1.0F);
      }

      private void renderBeamAuraLayer252(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 252, 1.24, 0.058, 4.42, 0.61F, 0.02F, 1.0F);
      }

      private void renderBeamAuraLayer253(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 253, 1.275, 0.061, 4.637, 0.69F, 0.032F, 1.0F);
      }

      private void renderBeamAuraLayer254(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 254, 1.31, 0.064, 4.854, 0.77F, 0.044F, 1.0F);
      }

      private void renderBeamAuraLayer255(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 255, 1.345, 0.016, 5.071, 0.45F, 0.056F, 1.0F);
      }

      private void renderBeamAuraLayer256(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 256, 1.38, 0.019, 5.288, 0.53F, 0.068F, 1.0F);
      }

      private void renderBeamAuraLayer257(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 257, 1.415, 0.022, 5.505, 0.61F, 0.08F, 1.0F);
      }

      private void renderBeamAuraLayer258(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 258, 1.45, 0.025, 5.722, 0.69F, 0.092F, 1.0F);
      }

      private void renderBeamAuraLayer259(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 259, 1.485, 0.028, 5.939, 0.77F, 0.02F, 1.0F);
      }

      private void renderBeamAuraLayer260(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 260, 1.52, 0.031, 6.156, 0.45F, 0.032F, 1.0F);
      }

      private void renderBeamAuraLayer261(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 261, 1.555, 0.034, 0.09, 0.53F, 0.044F, 1.0F);
      }

      private void renderBeamAuraLayer262(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 262, 1.59, 0.037, 0.307, 0.61F, 0.056F, 1.0F);
      }

      private void renderBeamAuraLayer263(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 263, 1.625, 0.04, 0.524, 0.69F, 0.068F, 1.0F);
      }

      private void renderBeamAuraLayer264(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 264, 1.66, 0.043, 0.741, 0.77F, 0.08F, 1.0F);
      }

      private void renderBeamAuraLayer265(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 265, 1.695, 0.046, 0.958, 0.45F, 0.092F, 1.0F);
      }

      private void renderBeamAuraLayer266(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 266, 1.73, 0.049, 1.175, 0.53F, 0.02F, 1.0F);
      }

      private void renderBeamAuraLayer267(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 267, 1.765, 0.052, 1.392, 0.61F, 0.032F, 1.0F);
      }

      private void renderBeamAuraLayer268(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 268, 1.8, 0.055, 1.609, 0.69F, 0.044F, 1.0F);
      }

      private void renderBeamAuraLayer269(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 269, 1.835, 0.058, 1.826, 0.77F, 0.056F, 1.0F);
      }

      private void renderBeamAuraLayer270(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 270, 0.82, 0.061, 2.043, 0.45F, 0.068F, 1.0F);
      }

      private void renderBeamAuraLayer271(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 271, 0.855, 0.064, 2.26, 0.53F, 0.08F, 1.0F);
      }

      private void renderBeamAuraLayer272(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 272, 0.89, 0.016, 2.477, 0.61F, 0.092F, 1.0F);
      }

      private void renderBeamAuraLayer273(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 273, 0.925, 0.019, 2.694, 0.69F, 0.02F, 1.0F);
      }

      private void renderBeamAuraLayer274(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 274, 0.96, 0.022, 2.911, 0.77F, 0.032F, 1.0F);
      }

      private void renderBeamAuraLayer275(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 275, 0.995, 0.025, 3.128, 0.45F, 0.044F, 1.0F);
      }

      private void renderBeamAuraLayer276(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 276, 1.03, 0.028, 3.345, 0.53F, 0.056F, 1.0F);
      }

      private void renderBeamAuraLayer277(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 277, 1.065, 0.031, 3.562, 0.61F, 0.068F, 1.0F);
      }

      private void renderBeamAuraLayer278(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 278, 1.1, 0.034, 3.779, 0.69F, 0.08F, 1.0F);
      }

      private void renderBeamAuraLayer279(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 279, 1.135, 0.037, 3.996, 0.77F, 0.092F, 1.0F);
      }

      private void renderBeamAuraLayer280(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 280, 1.17, 0.04, 4.213, 0.45F, 0.02F, 1.0F);
      }

      private void renderBeamAuraLayer281(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 281, 1.205, 0.043, 4.43, 0.53F, 0.032F, 1.0F);
      }

      private void renderBeamAuraLayer282(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 282, 1.24, 0.046, 4.647, 0.61F, 0.044F, 1.0F);
      }

      private void renderBeamAuraLayer283(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 283, 1.275, 0.049, 4.864, 0.69F, 0.056F, 1.0F);
      }

      private void renderBeamAuraLayer284(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 284, 1.31, 0.052, 5.081, 0.77F, 0.068F, 1.0F);
      }

      private void renderBeamAuraLayer285(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 285, 1.345, 0.055, 5.298, 0.45F, 0.08F, 1.0F);
      }

      private void renderBeamAuraLayer286(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 286, 1.38, 0.058, 5.515, 0.53F, 0.092F, 1.0F);
      }

      private void renderBeamAuraLayer287(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 287, 1.415, 0.061, 5.732, 0.61F, 0.02F, 1.0F);
      }

      private void renderBeamAuraLayer288(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 288, 1.45, 0.064, 5.949, 0.69F, 0.032F, 1.0F);
      }

      private void renderBeamAuraLayer289(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 289, 1.485, 0.016, 6.166, 0.77F, 0.044F, 1.0F);
      }

      private void renderBeamAuraLayer290(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 290, 1.52, 0.019, 0.1, 0.45F, 0.056F, 1.0F);
      }

      private void renderBeamAuraLayer291(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 291, 1.555, 0.022, 0.317, 0.53F, 0.068F, 1.0F);
      }

      private void renderBeamAuraLayer292(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 292, 1.59, 0.025, 0.534, 0.61F, 0.08F, 1.0F);
      }

      private void renderBeamAuraLayer293(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 293, 1.625, 0.028, 0.751, 0.69F, 0.092F, 1.0F);
      }

      private void renderBeamAuraLayer294(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 294, 1.66, 0.031, 0.968, 0.77F, 0.02F, 1.0F);
      }

      private void renderBeamAuraLayer295(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 295, 1.695, 0.034, 1.185, 0.45F, 0.032F, 1.0F);
      }

      private void renderBeamAuraLayer296(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 296, 1.73, 0.037, 1.402, 0.53F, 0.044F, 1.0F);
      }

      private void renderBeamAuraLayer297(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 297, 1.765, 0.04, 1.619, 0.61F, 0.056F, 1.0F);
      }

      private void renderBeamAuraLayer298(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 298, 1.8, 0.043, 1.836, 0.69F, 0.068F, 1.0F);
      }

      private void renderBeamAuraLayer299(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 299, 1.835, 0.046, 2.053, 0.77F, 0.08F, 1.0F);
      }

      private void renderBeamAuraLayer300(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 300, 0.82, 0.049, 2.27, 0.45F, 0.092F, 1.0F);
      }

      private void renderBeamAuraLayer301(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 301, 0.855, 0.052, 2.487, 0.53F, 0.02F, 1.0F);
      }

      private void renderBeamAuraLayer302(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 302, 0.89, 0.055, 2.704, 0.61F, 0.032F, 1.0F);
      }

      private void renderBeamAuraLayer303(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 303, 0.925, 0.058, 2.921, 0.69F, 0.044F, 1.0F);
      }

      private void renderBeamAuraLayer304(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 304, 0.96, 0.061, 3.138, 0.77F, 0.056F, 1.0F);
      }

      private void renderBeamAuraLayer305(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 305, 0.995, 0.064, 3.355, 0.45F, 0.068F, 1.0F);
      }

      private void renderBeamAuraLayer306(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 306, 1.03, 0.016, 3.572, 0.53F, 0.08F, 1.0F);
      }

      private void renderBeamAuraLayer307(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 307, 1.065, 0.019, 3.789, 0.61F, 0.092F, 1.0F);
      }

      private void renderBeamAuraLayer308(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 308, 1.1, 0.022, 4.006, 0.69F, 0.02F, 1.0F);
      }

      private void renderBeamAuraLayer309(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 309, 1.135, 0.025, 4.223, 0.77F, 0.032F, 1.0F);
      }

      private void renderBeamAuraLayer310(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 310, 1.17, 0.028, 4.44, 0.45F, 0.044F, 1.0F);
      }

      private void renderBeamAuraLayer311(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 311, 1.205, 0.031, 4.657, 0.53F, 0.056F, 1.0F);
      }

      private void renderBeamAuraLayer312(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 312, 1.24, 0.034, 4.874, 0.61F, 0.068F, 1.0F);
      }

      private void renderBeamAuraLayer313(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 313, 1.275, 0.037, 5.091, 0.69F, 0.08F, 1.0F);
      }

      private void renderBeamAuraLayer314(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 314, 1.31, 0.04, 5.308, 0.77F, 0.092F, 1.0F);
      }

      private void renderBeamAuraLayer315(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 315, 1.345, 0.043, 5.525, 0.45F, 0.02F, 1.0F);
      }

      private void renderBeamAuraLayer316(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 316, 1.38, 0.046, 5.742, 0.53F, 0.032F, 1.0F);
      }

      private void renderBeamAuraLayer317(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 317, 1.415, 0.049, 5.959, 0.61F, 0.044F, 1.0F);
      }

      private void renderBeamAuraLayer318(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 318, 1.45, 0.052, 6.176, 0.69F, 0.056F, 1.0F);
      }

      private void renderBeamAuraLayer319(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 319, 1.485, 0.055, 0.11, 0.77F, 0.068F, 1.0F);
      }

      private void renderBeamAuraLayer320(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 320, 1.52, 0.058, 0.327, 0.45F, 0.08F, 1.0F);
      }

      private void renderBeamAuraLayer321(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 321, 1.555, 0.061, 0.544, 0.53F, 0.092F, 1.0F);
      }

      private void renderBeamAuraLayer322(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 322, 1.59, 0.064, 0.761, 0.61F, 0.02F, 1.0F);
      }

      private void renderBeamAuraLayer323(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 323, 1.625, 0.016, 0.978, 0.69F, 0.032F, 1.0F);
      }

      private void renderBeamAuraLayer324(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 324, 1.66, 0.019, 1.195, 0.77F, 0.044F, 1.0F);
      }

      private void renderBeamAuraLayer325(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 325, 1.695, 0.022, 1.412, 0.45F, 0.056F, 1.0F);
      }

      private void renderBeamAuraLayer326(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 326, 1.73, 0.025, 1.629, 0.53F, 0.068F, 1.0F);
      }

      private void renderBeamAuraLayer327(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 327, 1.765, 0.028, 1.846, 0.61F, 0.08F, 1.0F);
      }

      private void renderBeamAuraLayer328(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 328, 1.8, 0.031, 2.063, 0.69F, 0.092F, 1.0F);
      }

      private void renderBeamAuraLayer329(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 329, 1.835, 0.034, 2.28, 0.77F, 0.02F, 1.0F);
      }

      private void renderBeamAuraLayer330(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 330, 0.82, 0.037, 2.497, 0.45F, 0.032F, 1.0F);
      }

      private void renderBeamAuraLayer331(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 331, 0.855, 0.04, 2.714, 0.53F, 0.044F, 1.0F);
      }

      private void renderBeamAuraLayer332(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 332, 0.89, 0.043, 2.931, 0.61F, 0.056F, 1.0F);
      }

      private void renderBeamAuraLayer333(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 333, 0.925, 0.046, 3.148, 0.69F, 0.068F, 1.0F);
      }

      private void renderBeamAuraLayer334(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 334, 0.96, 0.049, 3.365, 0.77F, 0.08F, 1.0F);
      }

      private void renderBeamAuraLayer335(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 335, 0.995, 0.052, 3.582, 0.45F, 0.092F, 1.0F);
      }

      private void renderBeamAuraLayer336(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 336, 1.03, 0.055, 3.799, 0.53F, 0.02F, 1.0F);
      }

      private void renderBeamAuraLayer337(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 337, 1.065, 0.058, 4.016, 0.61F, 0.032F, 1.0F);
      }

      private void renderBeamAuraLayer338(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 338, 1.1, 0.061, 4.233, 0.69F, 0.044F, 1.0F);
      }

      private void renderBeamAuraLayer339(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         this.renderBeamSmokeBand(matrix, x, y, z, beamRadius, height, alpha, time, 339, 1.135, 0.064, 4.45, 0.77F, 0.056F, 1.0F);
      }

      private void renderBeamSaturatingPurpleCore(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         boolean wrote = false;
         int slices = 224;

         for (int i = 0; i < slices; i++) {
            double a0 = i * (float) (Math.PI * 2) / slices;
            double a1 = (i + 1) * (float) (Math.PI * 2) / slices;
            double r0 = beamRadius * (0.96 + 0.025 * Math.sin(a0 * 17.0 + time * 0.09));
            double r1 = beamRadius * (0.96 + 0.025 * Math.sin(a1 * 17.0 + time * 0.09));
            cylinderVertex(buffer, matrix, x, y, z, r0, a0, 168, 20, 255, alpha * 0.96F);
            cylinderVertex(buffer, matrix, x, y + height, z, r0, a0, 255, 228, 255, alpha);
            cylinderVertex(buffer, matrix, x, y + height, z, r1, a1, 255, 228, 255, alpha);
            cylinderVertex(buffer, matrix, x, y, z, r1, a1, 168, 20, 255, alpha * 0.96F);
            wrote = true;
         }

         safeDraw(buffer, wrote);
      }

      private void renderBeamPurpleOverpressure(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         boolean wrote = false;
         int bands = 128;

         for (int i = 0; i < bands; i++) {
            double u = (double)i / bands;
            double a = u * (float) (Math.PI * 2) + time * 0.01;
            double side = a + (float) (Math.PI / 2);
            double r = beamRadius * (0.42 + hash(this.seed, 61000 + i) * 1.08);
            double x0 = x + Math.cos(a) * r;
            double z0 = z + Math.sin(a) * r;
            double cycle = (time * (0.018 + hash(this.seed, 62000 + i) * 0.032) + hash(this.seed, 63000 + i)) % 1.0;
            double y0 = y + cycle * height;
            double y1 = y0 + height * (0.055 + hash(this.seed, 64000 + i) * 0.085);
            double x1 = x0 + Math.cos(side) * beamRadius * (0.01 + hash(this.seed, 65000 + i) * 0.022);
            double z1 = z0 + Math.sin(side) * beamRadius * (0.01 + hash(this.seed, 65000 + i) * 0.022);
            float fade = smooth(Mth.clamp((float)((1.0 - cycle) / 0.26), 0.0F, 1.0F));
            float aFinal = Mth.clamp(alpha * fade * 1.1F, 0.0F, 1.0F);
            wrote |= addRibbon(buffer, matrix, x0, y0, z0, x1, y1, z1, 0.08F + hash(this.seed, 66000 + i) * 0.08F, 120, 0, 255, aFinal * 0.7F);
            wrote |= addRibbon(buffer, matrix, x0, y0 + 0.014, z0, x1, y1 + 0.014, z1, 0.024F + hash(this.seed, 67000 + i) * 0.03F, 255, 232, 255, aFinal);
         }

         safeDraw(buffer, wrote);
      }

      private void renderBeamCircuitStorm(Matrix4f matrix, double x, double y, double z, double beamRadius, double height, float alpha, double time) {
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         boolean wrote = false;

         for (int i = 0; i < 96; i++) {
            float h = hash(this.seed, 68000 + i);
            float q = hash(this.seed, 69000 + i);
            double a = h * (float) (Math.PI * 2);
            double r = beamRadius * (0.72 + q * 0.72);
            double px = x + Math.cos(a) * r;
            double pz = z + Math.sin(a) * r;
            double py = y + (time * (0.02 + q * 0.038) + h) % 1.0 * height;
            double side = a + (h > 0.5F ? (float) (Math.PI / 2) : (float) (-Math.PI / 2));
            double midX = px + Math.cos(side) * beamRadius * (0.034 + q * 0.055);
            double midZ = pz + Math.sin(side) * beamRadius * (0.034 + q * 0.055);
            double endY = py + height * (0.018 + h * 0.03);
            float fade = smooth(Mth.clamp((float)((height - (endY - y)) / (height * 0.24)), 0.0F, 1.0F));
            float aFinal = alpha * fade * (0.72F + h * 0.5F);
            wrote |= addRibbon(buffer, matrix, px, py, pz, midX, py + 0.05, midZ, 0.02F, 255, 220, 255, aFinal);
            wrote |= addRibbon(buffer, matrix, midX, py + 0.05, midZ, midX, endY, midZ, 0.018F, 170, 32, 255, aFinal * 0.92F);
         }

         safeDraw(buffer, wrote);
      }

      private void renderImpactRings(Matrix4f matrix, double x, double y, double z, double time, float release, float aftermath) {
         float beamAge = Mth.clamp((float)((time - this.releaseTick) / 145.0), 0.0F, 1.0F);
         double expansion = this.radius * (0.28 + smooth(beamAge) * 2.7);
         this.renderFlatDisk(matrix, x, y + 0.06, z, expansion * 0.86, expansion * 1.08, 255, 128, 255, release * 0.56F, 128);
         this.renderFlatDisk(matrix, x, y + 0.07, z, expansion * 0.18, expansion * 1.34, 120, 30, 255, release * 0.44F + aftermath * 0.2F, 128);
         this.renderFlatDisk(matrix, x, y + 0.085, z, expansion * 1.02, expansion * 1.96, 88, 0, 255, release * 0.26F + aftermath * 0.16F, 128);
      }

      private void renderSphere(
         Matrix4f matrix,
         double x,
         double y,
         double z,
         float radius,
         float height,
         int red,
         int green,
         int blue,
         float alpha,
         int stacks,
         int slices,
         double rotation,
         float rimPower
      ) {
         if (!(alpha <= 5.0E-4F)) {
            BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
            boolean wrote = false;

            for (int stack = 0; stack < stacks; stack++) {
               double v0 = (double)stack / stacks;
               double v1 = (double)(stack + 1) / stacks;
               double phi0 = (float) (-Math.PI / 2) + Math.PI * v0;
               double phi1 = (float) (-Math.PI / 2) + Math.PI * v1;

               for (int slice = 0; slice < slices; slice++) {
                  double u0 = (double)slice / slices;
                  double u1 = (double)(slice + 1) / slices;
                  double theta0 = (float) (Math.PI * 2) * u0 + rotation;
                  double theta1 = (float) (Math.PI * 2) * u1 + rotation;
                  sphereVertex(buffer, matrix, x, y, z, radius, height, theta0, phi0, red, green, blue, alpha, rimPower, rotation);
                  sphereVertex(buffer, matrix, x, y, z, radius, height, theta0, phi1, red, green, blue, alpha, rimPower, rotation);
                  sphereVertex(buffer, matrix, x, y, z, radius, height, theta1, phi1, red, green, blue, alpha, rimPower, rotation);
                  sphereVertex(buffer, matrix, x, y, z, radius, height, theta1, phi0, red, green, blue, alpha, rimPower, rotation);
                  wrote = true;
               }
            }

            safeDraw(buffer, wrote);
         }
      }

      private void renderFlatDisk(
         Matrix4f matrix, double x, double y, double z, double inner, double outer, int red, int green, int blue, float alpha, int slices
      ) {
         if (!(alpha <= 5.0E-4F) && !(outer <= inner)) {
            BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
            boolean wrote = false;

            for (int i = 0; i < slices; i++) {
               double a0 = i * (float) (Math.PI * 2) / slices;
               double a1 = (i + 1) * (float) (Math.PI * 2) / slices;
               diskVertex(buffer, matrix, x, y, z, inner, a0, red, green, blue, 0.0F);
               diskVertex(buffer, matrix, x, y, z, inner, a1, red, green, blue, alpha * 0.72F);
               diskVertex(buffer, matrix, x, y, z, outer, a1, red, green, blue, alpha);
               diskVertex(buffer, matrix, x, y, z, outer, a0, red, green, blue, 0.0F);
               wrote = true;
            }

            safeDraw(buffer, wrote);
         }
      }

      private void renderCylinder(
         Matrix4f matrix, double x, double y, double z, double inner, double outer, double height, int red, int green, int blue, float alpha, int slices
      ) {
         if (!(alpha <= 5.0E-4F) && !(outer <= inner)) {
            BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
            boolean wrote = false;
            double top = y + height;

            for (int i = 0; i < slices; i++) {
               double a0 = i * (float) (Math.PI * 2) / slices;
               double a1 = (i + 1) * (float) (Math.PI * 2) / slices;
               double wave0 = 0.94 + 0.06 * Math.sin(a0 * 13.0 + height * 0.012);
               double wave1 = 0.94 + 0.06 * Math.sin(a1 * 13.0 + height * 0.012);
               cylinderVertex(buffer, matrix, x, y, z, outer * wave0, a0, red, green, blue, alpha * 0.88F);
               cylinderVertex(buffer, matrix, x, top, z, outer * wave0, a0, red, green, blue, alpha);
               cylinderVertex(buffer, matrix, x, top, z, outer * wave1, a1, red, green, blue, alpha);
               cylinderVertex(buffer, matrix, x, y, z, outer * wave1, a1, red, green, blue, alpha * 0.88F);
               cylinderVertex(buffer, matrix, x, y + height * 0.02, z, inner, a0, red, green, blue, 0.0F);
               cylinderVertex(buffer, matrix, x, y + height * 0.98, z, inner, a0, red, green, blue, alpha * 0.78F);
               cylinderVertex(buffer, matrix, x, y + height * 0.98, z, inner, a1, red, green, blue, alpha * 0.78F);
               cylinderVertex(buffer, matrix, x, y + height * 0.02, z, inner, a1, red, green, blue, 0.0F);
               wrote = true;
            }

            safeDraw(buffer, wrote);
         }
      }

      private void renderNoisyCylinder(
         Matrix4f matrix,
         double x,
         double y,
         double z,
         double inner,
         double outer,
         double height,
         int red,
         int green,
         int blue,
         float alpha,
         int slices,
         double time,
         double turbulence
      ) {
         if (!(alpha <= 5.0E-4F) && !(outer <= inner)) {
            BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
            boolean wrote = false;
            double top = y + height;

            for (int i = 0; i < slices; i++) {
               double a0 = i * (float) (Math.PI * 2) / slices;
               double a1 = (i + 1) * (float) (Math.PI * 2) / slices;
               double n0 = 1.0 + turbulence * (Math.sin(a0 * 9.0 + time * 0.05) * 0.55 + Math.sin(a0 * 17.0 - time * 0.036) * 0.45);
               double n1 = 1.0 + turbulence * (Math.sin(a1 * 9.0 + time * 0.05) * 0.55 + Math.sin(a1 * 17.0 - time * 0.036) * 0.45);
               double topN0 = 1.0 + turbulence * (Math.sin(a0 * 11.0 + time * 0.041) * 0.6 + Math.sin(a0 * 23.0 + time * 0.028) * 0.4);
               double topN1 = 1.0 + turbulence * (Math.sin(a1 * 11.0 + time * 0.041) * 0.6 + Math.sin(a1 * 23.0 + time * 0.028) * 0.4);
               cylinderVertex(buffer, matrix, x, y, z, outer * n0, a0, red, green, blue, alpha * 0.82F);
               cylinderVertex(buffer, matrix, x, top, z, outer * topN0, a0, red, green, blue, alpha);
               cylinderVertex(buffer, matrix, x, top, z, outer * topN1, a1, red, green, blue, alpha);
               cylinderVertex(buffer, matrix, x, y, z, outer * n1, a1, red, green, blue, alpha * 0.82F);
               if (inner > 0.001) {
                  cylinderVertex(buffer, matrix, x, y + height * 0.02, z, inner, a0, red, green, blue, alpha * 0.3F);
                  cylinderVertex(buffer, matrix, x, y + height * 0.98, z, inner, a0, red, green, blue, alpha * 0.82F);
                  cylinderVertex(buffer, matrix, x, y + height * 0.98, z, inner, a1, red, green, blue, alpha * 0.82F);
                  cylinderVertex(buffer, matrix, x, y + height * 0.02, z, inner, a1, red, green, blue, alpha * 0.3F);
               }

               wrote = true;
            }

            safeDraw(buffer, wrote);
         }
      }

      private void renderCrossCore(Matrix4f matrix, double x, double y, double z, double radius, double height, float alpha, double time) {
         if (!(alpha <= 5.0E-4F)) {
            BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
            boolean wrote = false;

            for (int i = 0; i < 10; i++) {
               double a = i * Math.PI / 10.0 + time * 0.006;
               double dx = Math.cos(a) * radius;
               double dz = Math.sin(a) * radius;
               int r = i % 2 == 0 ? 255 : 236;
               int g = i % 2 == 0 ? 255 : 206;
               int b = 255;
               int aa = alpha(alpha * (0.3F + i % 3 * 0.055F));
               buffer.addVertex(matrix, (float)(x - dx), (float)y, (float)(z - dz)).setColor(r, g, b, 0);
               buffer.addVertex(matrix, (float)(x + dx), (float)y, (float)(z + dz)).setColor(r, g, b, aa);
               buffer.addVertex(matrix, (float)(x + dx), (float)(y + height), (float)(z + dz)).setColor(r, g, b, aa);
               buffer.addVertex(matrix, (float)(x - dx), (float)(y + height), (float)(z - dz)).setColor(r, g, b, 0);
               wrote = true;
            }

            safeDraw(buffer, wrote);
         }
      }

      private static boolean addCircuitRibbon(
         BufferBuilder buffer,
         Matrix4f matrix,
         double x1,
         double y1,
         double z1,
         double x2,
         double y2,
         double z2,
         float width,
         int red,
         int green,
         int blue,
         MitamaAtomicEdictVfxClient.AtomicCast.Color color,
         float alpha,
         float shine
      ) {
         boolean wrote = false;
         float hot = Mth.clamp(alpha * shine, 0.0F, 1.0F);
         wrote |= addRibbon(buffer, matrix, x1, y1, z1, x2, y2, z2, width * 12.6F, 132, 0, 255, hot * 0.7F);
         wrote |= addRibbon(buffer, matrix, x1, y1 + 0.012, z1, x2, y2 + 0.012, z2, width * 4.2F, red, green, blue, hot * 0.86F);
         wrote |= addRibbon(buffer, matrix, x1, y1 + 0.025, z1, x2, y2 + 0.025, z2, width * 1.55F, 255, 248, 255, hot);
         return wrote | addRibbon(buffer, matrix, x1, y1 + 0.04, z1, x2, y2 + 0.04, z2, width * 0.78F, color.r, color.g, color.b, hot);
      }

      private static boolean addCircuitNode(
         BufferBuilder buffer, Matrix4f matrix, double x, double y, double z, float size, MitamaAtomicEdictVfxClient.AtomicCast.Color color, float alpha
      ) {
         boolean wrote = false;
         float a = Mth.clamp(alpha, 0.0F, 1.0F);
         wrote |= addCameraSquare(buffer, matrix, x, y + 0.018, z, size * 3.2F, 146, 0, 255, a * 0.5F);
         wrote |= addCameraSquare(buffer, matrix, x, y + 0.03, z, size * 1.04F, 255, 252, 255, a * 0.92F);
         return wrote | addCameraSquare(buffer, matrix, x, y + 0.044, z, size * 0.62F, color.r, color.g, color.b, a);
      }

      private double terrainY(double px, double fallbackY, double pz) {
         Minecraft minecraft = Minecraft.getInstance();
         if (minecraft.level == null) {
            return fallbackY + 0.04;
         }

         MutableBlockPos pos = new MutableBlockPos();
         int ix = Mth.floor(px);
         int iz = Mth.floor(pz);
         int top = Mth.floor(fallbackY + 5.0);
         int bottom = Mth.floor(fallbackY - 8.0);

         for (int yy = top; yy >= bottom; yy--) {
            pos.set(ix, yy, iz);
            if (!minecraft.level.getBlockState(pos).isAir()) {
               return yy + 1.035;
            }
         }

         return fallbackY + 0.055;
      }

      private static void sphereVertex(
         BufferBuilder buffer,
         Matrix4f matrix,
         double cx,
         double cy,
         double cz,
         float radius,
         float height,
         double theta,
         double phi,
         int red,
         int green,
         int blue,
         float alpha,
         float rimPower,
         double rotation
      ) {
         float px = (float)(Math.cos(phi) * Math.cos(theta)) * radius;
         float py = (float)Math.sin(phi) * height;
         float pz = (float)(Math.cos(phi) * Math.sin(theta)) * radius;
         float rim = Mth.clamp((float)Math.sqrt(px * px + pz * pz) / Math.max(0.001F, radius), 0.0F, 1.0F);
         float wave = 0.72F + 0.28F * (float)Math.sin(theta * 8.0 + phi * 5.0 + rotation * 4.0);
         float a = alpha * Mth.clamp((float)Math.pow(rim, rimPower), 0.0F, 1.0F) * wave;
         buffer.addVertex(matrix, (float)cx + px, (float)cy + py, (float)cz + pz).setColor(red, green, blue, alpha(a));
      }

      private static void diskVertex(
         BufferBuilder buffer, Matrix4f matrix, double x, double y, double z, double r, double a, int red, int green, int blue, float alpha
      ) {
         buffer.addVertex(matrix, (float)(x + Math.cos(a) * r), (float)y, (float)(z + Math.sin(a) * r)).setColor(red, green, blue, alpha(alpha));
      }

      private static void cylinderVertex(
         BufferBuilder buffer, Matrix4f matrix, double x, double y, double z, double r, double a, int red, int green, int blue, float alpha
      ) {
         buffer.addVertex(matrix, (float)(x + Math.cos(a) * r), (float)y, (float)(z + Math.sin(a) * r)).setColor(red, green, blue, alpha(alpha));
      }

      private static boolean addRibbon(
         BufferBuilder buffer,
         Matrix4f matrix,
         double x1,
         double y1,
         double z1,
         double x2,
         double y2,
         double z2,
         float width,
         int red,
         int green,
         int blue,
         float alpha
      ) {
         if (!(alpha <= 5.0E-4F) && !(width <= 1.0E-4F)) {
            Vec3 dir = new Vec3(x2 - x1, y2 - y1, z2 - z1);
            Vec3 side = dir.cross(new Vec3(0.0, 1.0, 0.0));
            if (side.lengthSqr() < 1.0E-4) {
               side = new Vec3(1.0, 0.0, 0.0);
            } else {
               side = side.normalize();
            }

            side = side.scale(width);
            int a = alpha(alpha);
            buffer.addVertex(matrix, (float)(x1 - side.x), (float)y1, (float)(z1 - side.z)).setColor(red, green, blue, 0);
            buffer.addVertex(matrix, (float)(x1 + side.x), (float)y1, (float)(z1 + side.z)).setColor(red, green, blue, a);
            buffer.addVertex(matrix, (float)(x2 + side.x), (float)y2, (float)(z2 + side.z)).setColor(red, green, blue, a);
            buffer.addVertex(matrix, (float)(x2 - side.x), (float)y2, (float)(z2 - side.z)).setColor(red, green, blue, 0);
            return true;
         } else {
            return false;
         }
      }

      private static boolean addAtomicCube(
         BufferBuilder buffer, Matrix4f matrix, double x, double y, double z, float size, double yaw, double pitch, double roll, float alpha, int index
      ) {
         if (!(alpha <= 5.0E-4F) && !(size <= 1.0E-4F)) {
            float s = size * 0.58F;
            Vec3 p000 = rotateLocal(-s, -s, -s, yaw, pitch, roll);
            Vec3 p001 = rotateLocal(-s, -s, s, yaw, pitch, roll);
            Vec3 p010 = rotateLocal(-s, s, -s, yaw, pitch, roll);
            Vec3 p011 = rotateLocal(-s, s, s, yaw, pitch, roll);
            Vec3 p100 = rotateLocal(s, -s, -s, yaw, pitch, roll);
            Vec3 p101 = rotateLocal(s, -s, s, yaw, pitch, roll);
            Vec3 p110 = rotateLocal(s, s, -s, yaw, pitch, roll);
            Vec3 p111 = rotateLocal(s, s, s, yaw, pitch, roll);
            int pulse = (int)(Math.sin(index * 0.63 + yaw * 2.0) * 18.0);
            boolean wrote = false;
            wrote |= addCubeFace(buffer, matrix, x, y, z, p001, p101, p111, p011, 10 + pulse / 4, 0, 28 + pulse, alpha * 0.74F);
            wrote |= addCubeFace(buffer, matrix, x, y, z, p100, p000, p010, p110, 6 + pulse / 5, 0, 18 + pulse / 2, alpha * 0.58F);
            wrote |= addCubeFace(buffer, matrix, x, y, z, p000, p001, p011, p010, 8 + pulse / 5, 0, 34 + pulse, alpha * 0.66F);
            wrote |= addCubeFace(buffer, matrix, x, y, z, p101, p100, p110, p111, 20 + pulse / 3, 0, 52 + pulse, alpha * 0.72F);
            wrote |= addCubeFace(buffer, matrix, x, y, z, p010, p011, p111, p110, 28 + pulse / 2, 0, 68 + pulse, alpha * 0.78F);
            wrote |= addCubeFace(buffer, matrix, x, y, z, p000, p100, p101, p001, 4 + pulse / 6, 0, 14 + pulse / 3, alpha * 0.5F);
            float glow = Math.max(0.008F, size * 0.085F);
            wrote |= addCubeEdge(buffer, matrix, x, y, z, p000, p001, glow, alpha);
            wrote |= addCubeEdge(buffer, matrix, x, y, z, p010, p011, glow, alpha);
            wrote |= addCubeEdge(buffer, matrix, x, y, z, p100, p101, glow, alpha);
            wrote |= addCubeEdge(buffer, matrix, x, y, z, p110, p111, glow, alpha);
            wrote |= addCubeEdge(buffer, matrix, x, y, z, p000, p010, glow, alpha);
            wrote |= addCubeEdge(buffer, matrix, x, y, z, p001, p011, glow, alpha);
            wrote |= addCubeEdge(buffer, matrix, x, y, z, p100, p110, glow, alpha);
            wrote |= addCubeEdge(buffer, matrix, x, y, z, p101, p111, glow, alpha);
            wrote |= addCubeEdge(buffer, matrix, x, y, z, p000, p100, glow, alpha);
            wrote |= addCubeEdge(buffer, matrix, x, y, z, p001, p101, glow, alpha);
            wrote |= addCubeEdge(buffer, matrix, x, y, z, p010, p110, glow, alpha);
            wrote |= addCubeEdge(buffer, matrix, x, y, z, p011, p111, glow, alpha);
            Vec3 centerForward = rotateLocal(0.0, 0.0, s * 1.025, yaw, pitch, roll);
            Vec3 right = rotateLocal(s * 0.24, 0.0, s * 1.035, yaw, pitch, roll);
            Vec3 left = rotateLocal(-s * 0.24, 0.0, s * 1.035, yaw, pitch, roll);
            Vec3 up = rotateLocal(0.0, s * 0.24, s * 1.035, yaw, pitch, roll);
            Vec3 down = rotateLocal(0.0, -s * 0.24, s * 1.035, yaw, pitch, roll);
            wrote |= addRibbon(
               buffer, matrix, x + left.x, y + left.y, z + left.z, x + right.x, y + right.y, z + right.z, glow * 0.6F, 255, 76, 255, alpha * 0.88F
            );
            wrote |= addRibbon(buffer, matrix, x + down.x, y + down.y, z + down.z, x + up.x, y + up.y, z + up.z, glow * 0.6F, 255, 180, 255, alpha * 0.76F);
            return wrote
               | addCameraSquare(buffer, matrix, x + centerForward.x, y + centerForward.y, z + centerForward.z, glow * 1.8F, 255, 70, 255, alpha * 0.7F);
         } else {
            return false;
         }
      }

      private static boolean addCubeFace(
         BufferBuilder buffer, Matrix4f matrix, double x, double y, double z, Vec3 a, Vec3 b, Vec3 c, Vec3 d, int red, int green, int blue, float alpha
      ) {
         if (alpha <= 5.0E-4F) {
            return false;
         }

         int aa = alpha(alpha);
         buffer.addVertex(matrix, (float)(x + a.x), (float)(y + a.y), (float)(z + a.z)).setColor(red, green, blue, aa);
         buffer.addVertex(matrix, (float)(x + b.x), (float)(y + b.y), (float)(z + b.z)).setColor(red, green, blue, aa);
         buffer.addVertex(matrix, (float)(x + c.x), (float)(y + c.y), (float)(z + c.z)).setColor(red, green, blue, aa);
         buffer.addVertex(matrix, (float)(x + d.x), (float)(y + d.y), (float)(z + d.z)).setColor(red, green, blue, aa);
         return true;
      }

      private static boolean addCubeEdge(BufferBuilder buffer, Matrix4f matrix, double x, double y, double z, Vec3 a, Vec3 b, float width, float alpha) {
         boolean wrote = false;
         wrote |= addRibbon(buffer, matrix, x + a.x, y + a.y, z + a.z, x + b.x, y + b.y, z + b.z, width * 3.8F, 126, 0, 255, alpha * 0.56F);
         return wrote | addRibbon(buffer, matrix, x + a.x, y + a.y + 0.006, z + a.z, x + b.x, y + b.y + 0.006, z + b.z, width, 255, 96, 255, alpha);
      }

      private static boolean add3DSquare(
         BufferBuilder buffer, Matrix4f matrix, double x, double y, double z, float size, double yaw, double pitch, double roll, float alpha
      ) {
         if (!(alpha <= 5.0E-4F) && !(size <= 1.0E-4F)) {
            Vec3 p0 = rotateLocal(-size, -size, 0.0, yaw, pitch, roll);
            Vec3 p1 = rotateLocal(size, -size, 0.0, yaw, pitch, roll);
            Vec3 p2 = rotateLocal(size, size, 0.0, yaw, pitch, roll);
            Vec3 p3 = rotateLocal(-size, size, 0.0, yaw, pitch, roll);
            int fill = alpha(alpha * 0.76F);
            buffer.addVertex(matrix, (float)(x + p0.x), (float)(y + p0.y), (float)(z + p0.z)).setColor(0, 0, 0, fill);
            buffer.addVertex(matrix, (float)(x + p1.x), (float)(y + p1.y), (float)(z + p1.z)).setColor(0, 0, 0, fill);
            buffer.addVertex(matrix, (float)(x + p2.x), (float)(y + p2.y), (float)(z + p2.z)).setColor(0, 0, 0, fill);
            buffer.addVertex(matrix, (float)(x + p3.x), (float)(y + p3.y), (float)(z + p3.z)).setColor(0, 0, 0, fill);
            boolean wrote = true;
            float glow = Math.max(0.008F, size * 0.105F);
            wrote |= addRibbon(buffer, matrix, x + p0.x, y + p0.y, z + p0.z, x + p1.x, y + p1.y, z + p1.z, glow * 3.6F, 120, 0, 255, alpha * 0.56F);
            wrote |= addRibbon(buffer, matrix, x + p1.x, y + p1.y, z + p1.z, x + p2.x, y + p2.y, z + p2.z, glow * 3.6F, 120, 0, 255, alpha * 0.56F);
            wrote |= addRibbon(buffer, matrix, x + p2.x, y + p2.y, z + p2.z, x + p3.x, y + p3.y, z + p3.z, glow * 3.6F, 120, 0, 255, alpha * 0.56F);
            wrote |= addRibbon(buffer, matrix, x + p3.x, y + p3.y, z + p3.z, x + p0.x, y + p0.y, z + p0.z, glow * 3.6F, 120, 0, 255, alpha * 0.56F);
            wrote |= addRibbon(buffer, matrix, x + p0.x, y + p0.y + 0.01, z + p0.z, x + p1.x, y + p1.y + 0.01, z + p1.z, glow, 255, 225, 255, alpha);
            wrote |= addRibbon(buffer, matrix, x + p1.x, y + p1.y + 0.01, z + p1.z, x + p2.x, y + p2.y + 0.01, z + p2.z, glow, 255, 120, 255, alpha);
            wrote |= addRibbon(buffer, matrix, x + p2.x, y + p2.y + 0.01, z + p2.z, x + p3.x, y + p3.y + 0.01, z + p3.z, glow, 255, 225, 255, alpha);
            return wrote | addRibbon(buffer, matrix, x + p3.x, y + p3.y + 0.01, z + p3.z, x + p0.x, y + p0.y + 0.01, z + p0.z, glow, 255, 120, 255, alpha);
         } else {
            return false;
         }
      }

      private static Vec3 rotateLocal(double x, double y, double z, double yaw, double pitch, double roll) {
         double cr = Math.cos(roll);
         double sr = Math.sin(roll);
         double xr = x * cr - y * sr;
         double yr = x * sr + y * cr;
         double zr = z;
         double cp = Math.cos(pitch);
         double sp = Math.sin(pitch);
         double xp = xr;
         double yp = yr * cp - zr * sp;
         double zp = yr * sp + zr * cp;
         double cy = Math.cos(yaw);
         double sy = Math.sin(yaw);
         double xy = xp * cy + zp * sy;
         double zy = -xp * sy + zp * cy;
         return new Vec3(xy, yp, zy);
      }

      private static boolean addOrientedSquareFill(BufferBuilder buffer, Matrix4f matrix, double x, double y, double z, float size, double spin, float alpha) {
         if (!(alpha <= 5.0E-4F) && !(size <= 1.0E-4F)) {
            double ux = Math.cos(spin) * size;
            double uz = Math.sin(spin) * size;
            double vx = -Math.sin(spin) * size;
            double vz = Math.cos(spin) * size;
            double yv = size * 0.18 * Math.sin(spin * 1.7);
            int a = alpha(alpha);
            buffer.addVertex(matrix, (float)(x - ux - vx), (float)(y - yv), (float)(z - uz - vz)).setColor(0, 0, 0, a);
            buffer.addVertex(matrix, (float)(x + ux - vx), (float)(y + yv), (float)(z + uz - vz)).setColor(0, 0, 0, a);
            buffer.addVertex(matrix, (float)(x + ux + vx), (float)(y + yv), (float)(z + uz + vz)).setColor(0, 0, 0, a);
            buffer.addVertex(matrix, (float)(x - ux + vx), (float)(y - yv), (float)(z - uz + vz)).setColor(0, 0, 0, a);
            return true;
         } else {
            return false;
         }
      }

      private static boolean addOrientedSquareOutline(
         BufferBuilder buffer, Matrix4f matrix, double x, double y, double z, float size, double spin, int red, int green, int blue, float alpha
      ) {
         if (!(alpha <= 5.0E-4F) && !(size <= 1.0E-4F)) {
            double ux = Math.cos(spin) * size;
            double uz = Math.sin(spin) * size;
            double vx = -Math.sin(spin) * size;
            double vz = Math.cos(spin) * size;
            double yv = size * 0.18 * Math.sin(spin * 1.7);
            double x0 = x - ux - vx;
            double y0 = y - yv;
            double z0 = z - uz - vz;
            double x1 = x + ux - vx;
            double y1 = y + yv;
            double z1 = z + uz - vz;
            double x2 = x + ux + vx;
            double y2 = y + yv;
            double z2 = z + uz + vz;
            double x3 = x - ux + vx;
            double y3 = y - yv;
            double z3 = z - uz + vz;
            float width = Math.max(0.004F, size * 0.06F);
            boolean wrote = false;
            wrote |= addRibbon(buffer, matrix, x0, y0, z0, x1, y1, z1, width, red, green, blue, alpha);
            wrote |= addRibbon(buffer, matrix, x1, y1, z1, x2, y2, z2, width, red, green, blue, alpha);
            wrote |= addRibbon(buffer, matrix, x2, y2, z2, x3, y3, z3, width, red, green, blue, alpha);
            return wrote | addRibbon(buffer, matrix, x3, y3, z3, x0, y0, z0, width, red, green, blue, alpha);
         } else {
            return false;
         }
      }

      private static boolean addCameraSquare(
         BufferBuilder buffer, Matrix4f matrix, double x, double y, double z, float size, int red, int green, int blue, float alpha
      ) {
         if (!(alpha <= 5.0E-4F) && !(size <= 1.0E-4F)) {
            int a = alpha(alpha);
            buffer.addVertex(matrix, (float)(x - size), (float)(y - size), (float)z).setColor(red, green, blue, 0);
            buffer.addVertex(matrix, (float)(x - size), (float)(y + size), (float)z).setColor(red, green, blue, a);
            buffer.addVertex(matrix, (float)(x + size), (float)(y + size), (float)z).setColor(red, green, blue, a);
            buffer.addVertex(matrix, (float)(x + size), (float)(y - size), (float)z).setColor(red, green, blue, 0);
            return true;
         } else {
            return false;
         }
      }

      private static void safeDraw(BufferBuilder buffer, boolean wrote) {
         if (wrote) {
            try {
               BufferUploader.drawWithShader(buffer.buildOrThrow());
            } catch (IllegalStateException var3) {
            }
         }
      }

      private static float smooth(float value) {
         return value * value * (3.0F - 2.0F * value);
      }

      private static int alpha(float alpha) {
         return Mth.clamp((int)(alpha * 255.0F), 0, 255);
      }

      private static MitamaAtomicEdictVfxClient.AtomicCast.Color rainbow(double time, double offset, float saturation, float brightness, float mixPurple) {
         double h = time * 0.012 + offset;
         float r = 0.5F + 0.5F * (float)Math.sin(h * (float) (Math.PI * 2));
         float g = 0.5F + 0.5F * (float)Math.sin(h * (float) (Math.PI * 2) + (Math.PI * 2.0 / 3.0));
         float b = 0.5F + 0.5F * (float)Math.sin(h * (float) (Math.PI * 2) + (Math.PI * 4.0 / 3.0));
         r = 0.5F + (r - 0.5F) * saturation;
         g = 0.5F + (g - 0.5F) * saturation;
         b = 0.5F + (b - 0.5F) * saturation;
         r *= brightness;
         g *= brightness;
         b *= brightness;
         r = Mth.lerp(mixPurple, r, 1.0F);
         g = Mth.lerp(mixPurple, g, 0.18F);
         b = Mth.lerp(mixPurple, b, 1.0F);
         return new MitamaAtomicEdictVfxClient.AtomicCast.Color(
            Mth.clamp((int)(r * 255.0F), 0, 255), Mth.clamp((int)(g * 255.0F), 0, 255), Mth.clamp((int)(b * 255.0F), 0, 255)
         );
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

      private record AscentThread(float angle, float dist, float height, float speed, float variant) {
      }

      private record Circuit(float angle, float dist, float variant) {
      }

      private record Color(int r, int g, int b) {
      }

      private record FloatingSquare(float angle, float dist, float height, float speed, float size, float variant) {
      }

      private record OrbitStrand(float angle, float height, float speed, float size, float variant) {
      }

      private record RadialStrand(float angle, float length, float height, float delay, float kink) {
      }

      private record RainLine(float angle, float dist, float height, float delay, float variant) {
      }
   }
}
