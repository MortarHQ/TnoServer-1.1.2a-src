package com.github.wal_bos.moreskills.client.fx;

import com.github.wal_bos.moreskills.world.entity.FeldwayAwakenedEntity;
import com.github.wal_bos.moreskills.world.entity.FeldwayAwakeningEntity;
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
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent.Stage;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

public class PathsAuroraRenderer {
   private static final ResourceLocation AURORA_MASK = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "textures/environment/aurora.png");
   private static final List<PathsAuroraRenderer.AuroraRibbon> RIBBONS = new ArrayList<>();
   private static final int TARGET_MAIN_RIBBONS = 38;
   private static final int TARGET_SECONDARY_RIBBONS = 18;
   private static final int TARGET_RITUAL_RIBBONS = 74;
   private static final double SPAWN_RADIUS = 360.0;
   private static final double KEEP_RADIUS = 470.0;
   private static final double HARD_DESPAWN_RADIUS = 620.0;
   private static final int SEGMENTS = 160;
   private static final int GOLD_RED = 255;
   private static final int GOLD_GREEN = 184;
   private static final int GOLD_BLUE = 38;
   private static final int GOLD_HOT_RED = 255;
   private static final int GOLD_HOT_GREEN = 228;
   private static final int GOLD_HOT_BLUE = 112;
   private static final int GOLD_DEEP_RED = 255;
   private static final int GOLD_DEEP_GREEN = 118;
   private static final int GOLD_DEEP_BLUE = 12;

   public static void renderAurora(RenderLevelStageEvent event) {
      if (event.getStage() == Stage.AFTER_PARTICLES) {
         Minecraft mc = Minecraft.getInstance();
         ClientLevel level = mc.level;
         if (level != null && level.dimension().equals(PathsClientState.PATHS)) {
            Camera camera = event.getCamera();
            Vec3 camPos = camera.getPosition();
            PoseStack poseStack = event.getPoseStack();
            float partialTick = event.getPartialTick().getGameTimeDeltaPartialTick(false);
            float time = (float)level.getGameTime() + partialTick;
            float ambience = PathsClientState.ambience;
            PathsAuroraRenderer.AwakeningAurora awakening = findAwakening(level, camPos);
            FeldwayAwakenedEntity awakened = findAwakened(level, camPos);
            boolean awakenedAlive = awakened != null;
            float ritualPower = awakening == null ? 0.0F : awakening.power;
            float ritualClimax = awakening == null ? 0.0F : awakening.climax;
            float ritualImpact = awakening == null ? 0.0F : awakening.impact;
            float goldPower = awakenedAlive ? 1.0F : 0.0F;
            float baseAlpha = 0.42F + ambience * 0.52F + ritualPower * 1.15F + ritualClimax * 1.45F + ritualImpact * 1.75F + goldPower * 1.05F;
            tickField(level, camPos, time, awakening);
            RenderSystem.enableBlend();
            RenderSystem.disableCull();
            RenderSystem.depthMask(false);
            RenderSystem.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
            if (!awakenedAlive) {
               RenderSystem.setShaderTexture(0, AURORA_MASK);
               ShaderInstance shader = PathsClientFX.AURORA_SHADER;
               if (shader != null) {
                  RenderSystem.setShader(() -> shader);
                  if (shader.getUniform("GameTime") != null) {
                     shader.getUniform("GameTime").set(time * 0.035F + ritualPower * 1.5F + ritualClimax * 4.2F + ritualImpact * 8.0F);
                  }
               } else {
                  RenderSystem.setShader(GameRenderer::getPositionTexShader);
               }
            }

            for (PathsAuroraRenderer.AuroraRibbon ribbon : RIBBONS) {
               float horizontalDist = (float)Math.sqrt((camPos.x - ribbon.x) * (camPos.x - ribbon.x) + (camPos.z - ribbon.z) * (camPos.z - ribbon.z));
               float alphaFade = 1.0F - Mth.clamp((horizontalDist - 40.0F) / 430.0F, 0.0F, 1.0F) * 0.42F;
               float lifeFade = Mth.clamp(ribbon.alpha, 0.0F, 1.0F);
               float obeyBoost = ribbon.obedience * (0.75F + ritualPower * 1.75F + ritualClimax * 2.4F + ritualImpact * 2.9F);
               float ritualBoost = ribbon.ritual ? 2.45F + ritualPower * 2.75F + ritualClimax * 3.55F + ritualImpact * 4.7F : 1.0F + obeyBoost;
               float goldBoost = awakenedAlive ? (ribbon.main ? 2.15F : 1.85F) : 1.0F;
               float alpha = baseAlpha * alphaFade * lifeFade * ribbon.alphaScale * ritualBoost * goldBoost;
               if (!(alpha <= 0.003F)) {
                  poseStack.pushPose();
                  poseStack.translate(ribbon.x - camPos.x, ribbon.yBase - camPos.y, ribbon.z - camPos.z);
                  poseStack.mulPose(new Quaternionf().rotateY((float)Math.toRadians(ribbon.yaw)));
                  poseStack.mulPose(new Quaternionf().rotateZ((float)Math.toRadians(ribbon.roll)));
                  poseStack.mulPose(new Quaternionf().rotateX((float)Math.toRadians(ribbon.pitch)));
                  renderRibbon(poseStack, ribbon, time, alpha, awakening, awakenedAlive);
                  poseStack.popPose();
               }
            }

            if (awakening != null && ritualPower > 0.02F) {
               renderRitualSkyVortex(poseStack, camPos, awakening, time, awakenedAlive);
               renderObedienceShockwaves(poseStack, camPos, awakening, time, awakenedAlive);
               renderJudgementAuroraSpears(poseStack, camPos, awakening, time, awakenedAlive);
               renderSkyCrack(poseStack, camPos, awakening, time, awakenedAlive);
            }

            if (awakenedAlive) {
               renderAwakenedGoldenArena(poseStack, camPos, awakened, time);
            }

            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.depthMask(true);
            RenderSystem.enableCull();
            RenderSystem.disableBlend();
         } else {
            RIBBONS.clear();
         }
      }
   }

   private static void renderRibbon(
      PoseStack poseStack,
      PathsAuroraRenderer.AuroraRibbon ribbon,
      float time,
      float alpha,
      PathsAuroraRenderer.AwakeningAurora awakening,
      boolean awakenedAlive
   ) {
      float width;
      float finalAlpha;
      if (ribbon.main) {
         width = 1.5F;
         finalAlpha = alpha * 1.08F;
      } else {
         width = ribbon.baseWidth;
         finalAlpha = alpha * ribbon.brightness;
      }

      if (awakening != null && ribbon.obedience > 0.0F) {
         float inhale = awakening.power * (1.0F - awakening.climax);
         float pillar = awakening.climax;
         float flare = awakening.impactPulse;
         float collapse = awakening.collapse;
         width *= 1.0F + ribbon.obedience * (inhale * 0.5F + pillar * 2.2F + flare * 4.2F + collapse * 1.3F);
         finalAlpha *= 1.0F + ribbon.obedience * (inhale * 0.7F + pillar * 1.8F + flare * 2.6F + collapse * 1.2F);
      }

      if (ribbon.ritual && awakening != null) {
         width *= 1.4F + awakening.power * 2.5F + awakening.climax * 3.1F + awakening.impact * 3.8F;
         finalAlpha *= 1.0F + awakening.climax * 1.65F + awakening.impact * 2.1F;
      }

      if (awakenedAlive) {
         width *= ribbon.main ? 1.55F : 1.3F;
         finalAlpha *= ribbon.main ? 1.85F : 1.55F;
         drawGoldenAuroraRibbon(poseStack, ribbon, ribbon.height, width, ribbon.waveAmp, ribbon.waveFreq, time, ribbon.seed, finalAlpha, awakening);
      } else {
         RenderSystem.setShaderTexture(0, AURORA_MASK);
         ShaderInstance shader = PathsClientFX.AURORA_SHADER;
         if (shader != null) {
            RenderSystem.setShader(() -> shader);
         } else {
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
         }

         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, finalAlpha);
         drawAuroraRibbon(poseStack, ribbon, ribbon.height, width, ribbon.waveAmp, ribbon.waveFreq, time, ribbon.seed, ribbon.uOffset, awakening, false);
      }
   }

   private static void tickField(ClientLevel level, Vec3 camPos, float time, PathsAuroraRenderer.AwakeningAurora awakening) {
      Iterator<PathsAuroraRenderer.AuroraRibbon> iterator = RIBBONS.iterator();

      while (iterator.hasNext()) {
         PathsAuroraRenderer.AuroraRibbon ribbon = iterator.next();
         double dx = ribbon.x - camPos.x;
         double dz = ribbon.z - camPos.z;
         double distSq = dx * dx + dz * dz;
         ribbon.age++;
         ribbon.yaw = ribbon.yaw + ribbon.yawDrift;
         ribbon.roll = ribbon.baseRoll + (float)Math.sin(time * 3.6E-4F + ribbon.seed * 0.1F) * ribbon.rollSwing;
         ribbon.pitch = ribbon.basePitch + (float)Math.sin(time * 2.5E-4F + ribbon.seed * 0.07F) * ribbon.pitchSwing;
         float targetAlpha = distSq > 220900.0 ? 0.0F : 1.0F;
         if (awakening != null) {
            applyFeldwayObedience(ribbon, awakening, time);
            targetAlpha = Math.max(targetAlpha, awakening.power * (ribbon.ritual ? 1.0F : 0.65F));
         } else if (ribbon.ritual) {
            targetAlpha = 0.0F;
            ribbon.obedience = Mth.lerp(0.08F, ribbon.obedience, 0.0F);
         } else {
            ribbon.obedience = Mth.lerp(0.04F, ribbon.obedience, 0.0F);
         }

         ribbon.alpha = Mth.lerp(0.11F, ribbon.alpha, targetAlpha);
         if (distSq > 384400.0 && ribbon.alpha < 0.03F) {
            iterator.remove();
         }

         if (ribbon.ritual && ribbon.alpha < 0.02F && awakening == null) {
            iterator.remove();
         }
      }

      RandomSource random = level.random;
      int mainCount = 0;
      int secondaryCount = 0;
      int ritualCount = 0;

      for (PathsAuroraRenderer.AuroraRibbon ribbon : RIBBONS) {
         if (ribbon.ritual) {
            ritualCount++;
         } else if (ribbon.main) {
            mainCount++;
         } else {
            secondaryCount++;
         }
      }

      int tries = 0;

      while (mainCount < 38 && tries < 228) {
         tries++;
         PathsAuroraRenderer.AuroraRibbon ribbon = createMainRibbon(random, camPos);
         if (isFarEnoughFromOthers(ribbon)) {
            RIBBONS.add(ribbon);
            mainCount++;
         }
      }

      tries = 0;

      while (secondaryCount < 18 && tries < 180) {
         tries++;
         PathsAuroraRenderer.AuroraRibbon ribbon = createSecondaryRibbon(random, camPos);
         if (isFarEnoughFromOthers(ribbon)) {
            RIBBONS.add(ribbon);
            secondaryCount++;
         }
      }

      if (awakening != null && awakening.power > 0.03F) {
         tries = 0;

         for (int target = 22 + (int)(awakening.power * 74.0F); ritualCount < target && tries < 370; ritualCount++) {
            tries++;
            PathsAuroraRenderer.AuroraRibbon ribbon = createRitualRibbon(random, awakening, ritualCount);
            RIBBONS.add(ribbon);
         }
      }
   }

   private static void applyFeldwayObedience(PathsAuroraRenderer.AuroraRibbon ribbon, PathsAuroraRenderer.AwakeningAurora awakening, float time) {
      double dx = ribbon.x - awakening.center.x;
      double dz = ribbon.z - awakening.center.z;
      double dist = Math.sqrt(dx * dx + dz * dz);
      double angle = Math.atan2(dz, dx);
      if (dist < 0.001) {
         angle = ribbon.birthAngle;
         dist = ribbon.orbitRadius;
      }

      float influenceRange = ribbon.ritual ? 1.0F : Mth.clamp((float)(1.0 - Math.max(0.0, dist - 60.0) / 640.0), 0.0F, 1.0F);
      float targetObedience = awakening.power * influenceRange;
      ribbon.obedience = Mth.lerp(0.075F + awakening.climax * 0.1F + awakening.impact * 0.18F, ribbon.obedience, targetObedience);
      if (!(ribbon.obedience <= 0.005F)) {
         float early = awakening.early;
         float mid = awakening.mid;
         float climax = awakening.climax;
         float impactPulse = awakening.impactPulse;
         float collapse = awakening.collapse;
         double direction = ribbon.orbitDir;
         double lane = ribbon.ritualLane;
         double baseRadius = ribbon.ritual ? 62.0 + lane % 18.0 * 10.0 + lane / 18.0 * 32.0 : 80.0 + ribbon.orbitRadius * 0.42;
         double pullRadius = Mth.lerp(early, ribbon.orbitRadius, baseRadius);
         double orbitRadius = Mth.lerp(mid, pullRadius, 54.0 + lane % 24.0 * 8.0 + ribbon.seed % 42.0F);
         double pillarRadius = Mth.lerp(climax, orbitRadius, 18.0 + lane % 16.0 * 3.0);
         double flareRadius = pillarRadius + impactPulse * (210.0 + ribbon.seed % 100.0F * 2.2);
         double finalRadius = Mth.lerp(collapse, flareRadius, 22.0 + lane % 12.0 * 2.6);
         double spinSpeed = (0.006 + ribbon.seed * 1.2E-5) * direction;
         spinSpeed += mid * (0.036 + lane % 9.0 * 0.0025) * direction;
         spinSpeed += climax * (0.096 + lane % 7.0 * 0.004) * direction;
         spinSpeed += awakening.impact * 0.155 * direction;
         double wantedAngle = angle + spinSpeed + Math.sin(time * 0.018 + ribbon.seed) * 0.01;
         double tx = awakening.center.x + Math.cos(wantedAngle) * finalRadius;
         double tz = awakening.center.z + Math.sin(wantedAngle) * finalRadius;
         double snap = ribbon.ritual ? 0.085 : 0.018;
         snap += early * (ribbon.ritual ? 0.03 : 0.025);
         snap += mid * (ribbon.ritual ? 0.07 : 0.06);
         snap += climax * (ribbon.ritual ? 0.095 : 0.07);
         snap += awakening.impact * (ribbon.ritual ? 0.18 : 0.115);
         ribbon.x = Mth.lerp(snap * ribbon.obedience, ribbon.x, tx);
         ribbon.z = Mth.lerp(snap * ribbon.obedience, ribbon.z, tz);
         float targetY = (float)(awakening.center.y - 76.0 + lane % 20.0 * 3.4);
         targetY -= early * 16.0F;
         targetY -= mid * 28.0F;
         targetY -= climax * 54.0F;
         targetY += impactPulse * 45.0F;
         targetY -= collapse * 68.0F;
         ribbon.yBase = Mth.lerp((float)(0.03 + snap * 0.55), ribbon.yBase, targetY);
         float angleDeg = (float)Math.toDegrees(wantedAngle);
         float orbitYaw = angleDeg + 90.0F;
         float pillarYaw = angleDeg;
         float targetYaw = Mth.lerp(climax, orbitYaw, pillarYaw);
         ribbon.yaw = Mth.rotLerp(0.16F + awakening.impact * 0.25F, ribbon.yaw, targetYaw);
         ribbon.roll = ribbon.roll + ribbon.obedience * (early * 0.65F + mid * 1.4F + climax * 3.2F + awakening.impact * 5.8F) * ribbon.orbitDir;
         ribbon.pitch = Mth.lerp(0.1F + climax * 0.2F, ribbon.pitch, ribbon.basePitch * (1.0F - climax) + 88.0F * climax);
         ribbon.height = Mth.lerp(0.045F + climax * 0.065F, ribbon.height, ribbon.baseHeight * (1.0F + climax * 1.25F + impactPulse * 0.45F + collapse * 0.8F));
         ribbon.waveAmp = Mth.lerp(0.05F, ribbon.waveAmp, ribbon.baseWaveAmp * (1.0F - climax * 0.7F) + impactPulse * 34.0F + collapse * 12.0F);
      }
   }

   private static boolean isFarEnoughFromOthers(PathsAuroraRenderer.AuroraRibbon ribbon) {
      if (ribbon.ritual) {
         return true;
      }

      for (PathsAuroraRenderer.AuroraRibbon other : RIBBONS) {
         if (!other.ritual) {
            double dx = other.x - ribbon.x;
            double dz = other.z - ribbon.z;
            double min;
            if (ribbon.main && other.main) {
               min = 54.0;
            } else if (!ribbon.main && !other.main) {
               min = Math.max(18.0, (ribbon.spacing + other.spacing) * 0.5);
            } else {
               min = 24.0;
            }

            if (dx * dx + dz * dz < min * min) {
               return false;
            }
         }
      }

      return true;
   }

   private static PathsAuroraRenderer.AuroraRibbon createMainRibbon(RandomSource random, Vec3 camPos) {
      double angle = random.nextDouble() * Math.PI * 2.0;
      double radius = 45.0 + random.nextDouble() * 315.0;
      PathsAuroraRenderer.AuroraRibbon ribbon = new PathsAuroraRenderer.AuroraRibbon();
      ribbon.x = camPos.x + Math.cos(angle) * radius;
      ribbon.z = camPos.z + Math.sin(angle) * radius;
      ribbon.yBase = -10.0F + random.nextFloat() * 16.0F;
      ribbon.height = 340.0F + random.nextFloat() * 190.0F;
      ribbon.baseHeight = ribbon.height;
      ribbon.baseWidth = 1.5F;
      ribbon.waveAmp = 1.2F + random.nextFloat() * 1.5F;
      ribbon.baseWaveAmp = ribbon.waveAmp;
      ribbon.waveFreq = 0.009F + random.nextFloat() * 0.007F;
      ribbon.yaw = random.nextFloat() * 360.0F;
      ribbon.baseRoll = -1.5F + random.nextFloat() * 3.0F;
      ribbon.roll = ribbon.baseRoll;
      ribbon.rollSwing = 0.45F + random.nextFloat() * 0.35F;
      ribbon.basePitch = -2.2F + random.nextFloat() * 4.4F;
      ribbon.pitch = ribbon.basePitch;
      ribbon.pitchSwing = 0.12F + random.nextFloat() * 0.14F;
      ribbon.seed = random.nextFloat() * 1000.0F;
      ribbon.yawDrift = -0.005F + random.nextFloat() * 0.01F;
      ribbon.alpha = 0.0F;
      ribbon.alphaScale = 1.0F;
      ribbon.brightness = 1.0F;
      ribbon.spacing = 54.0F;
      ribbon.age = 0;
      ribbon.main = true;
      ribbon.shapeType = 0;
      ribbon.widthProfile = 0;
      ribbon.uOffset = random.nextFloat();
      ribbon.ritual = false;
      ribbon.ritualLane = 0;
      ribbon.birthAngle = angle;
      ribbon.orbitRadius = radius;
      ribbon.orbitDir = random.nextBoolean() ? 1.0F : -1.0F;
      ribbon.obedience = 0.0F;
      return ribbon;
   }

   private static PathsAuroraRenderer.AuroraRibbon createSecondaryRibbon(RandomSource random, Vec3 camPos) {
      double angle = random.nextDouble() * Math.PI * 2.0;
      double radius = 20.0 + random.nextDouble() * 340.0;
      PathsAuroraRenderer.AuroraRibbon ribbon = new PathsAuroraRenderer.AuroraRibbon();
      ribbon.x = camPos.x + Math.cos(angle) * radius;
      ribbon.z = camPos.z + Math.sin(angle) * radius;
      ribbon.yaw = random.nextFloat() * 360.0F;
      ribbon.seed = random.nextFloat() * 1000.0F;
      ribbon.alpha = 0.0F;
      ribbon.age = 0;
      ribbon.main = false;
      ribbon.shapeType = 1 + random.nextInt(4);
      ribbon.widthProfile = random.nextInt(4);
      ribbon.uOffset = random.nextFloat();
      ribbon.ritual = false;
      ribbon.ritualLane = 0;
      ribbon.birthAngle = angle;
      ribbon.orbitRadius = radius;
      ribbon.orbitDir = random.nextBoolean() ? 1.0F : -1.0F;
      ribbon.obedience = 0.0F;
      float tier = random.nextFloat();
      if (tier < 0.34F) {
         ribbon.yBase = -10.0F + random.nextFloat() * 90.0F;
         ribbon.height = 45.0F + random.nextFloat() * 95.0F;
         ribbon.baseWidth = 0.26F + random.nextFloat() * 0.34F;
         ribbon.waveAmp = 0.9F + random.nextFloat() * 1.8F;
         ribbon.waveFreq = 0.02F + random.nextFloat() * 0.03F;
         ribbon.baseRoll = -55.0F + random.nextFloat() * 110.0F;
         ribbon.roll = ribbon.baseRoll;
         ribbon.rollSwing = 0.7F + random.nextFloat() * 0.7F;
         ribbon.basePitch = -65.0F + random.nextFloat() * 130.0F;
         ribbon.pitch = ribbon.basePitch;
         ribbon.pitchSwing = 0.35F + random.nextFloat() * 0.55F;
         ribbon.alphaScale = 0.72F + random.nextFloat() * 0.18F;
         ribbon.brightness = 0.82F + random.nextFloat() * 0.16F;
         ribbon.yawDrift = -0.04F + random.nextFloat() * 0.08F;
         ribbon.spacing = 16.0F;
      } else if (tier < 0.74F) {
         ribbon.yBase = -20.0F + random.nextFloat() * 170.0F;
         ribbon.height = 110.0F + random.nextFloat() * 220.0F;
         ribbon.baseWidth = 0.6F + random.nextFloat() * 0.85F;
         ribbon.waveAmp = 1.8F + random.nextFloat() * 4.2F;
         ribbon.waveFreq = 0.012F + random.nextFloat() * 0.02F;
         ribbon.baseRoll = -34.0F + random.nextFloat() * 68.0F;
         ribbon.roll = ribbon.baseRoll;
         ribbon.rollSwing = 0.8F + random.nextFloat() * 1.1F;
         ribbon.basePitch = -45.0F + random.nextFloat() * 90.0F;
         ribbon.pitch = ribbon.basePitch;
         ribbon.pitchSwing = 0.45F + random.nextFloat() * 0.9F;
         ribbon.alphaScale = 0.85F + random.nextFloat() * 0.25F;
         ribbon.brightness = 0.9F + random.nextFloat() * 0.2F;
         ribbon.yawDrift = -0.03F + random.nextFloat() * 0.06F;
         ribbon.spacing = 22.0F;
      } else {
         ribbon.yBase = -60.0F + random.nextFloat() * 260.0F;
         ribbon.height = 320.0F + random.nextFloat() * 420.0F;
         ribbon.baseWidth = 1.4F + random.nextFloat() * 2.4F;
         ribbon.waveAmp = 4.5F + random.nextFloat() * 8.0F;
         ribbon.waveFreq = 0.006F + random.nextFloat() * 0.01F;
         ribbon.baseRoll = -24.0F + random.nextFloat() * 48.0F;
         ribbon.roll = ribbon.baseRoll;
         ribbon.rollSwing = 1.0F + random.nextFloat() * 1.2F;
         ribbon.basePitch = -28.0F + random.nextFloat() * 56.0F;
         ribbon.pitch = ribbon.basePitch;
         ribbon.pitchSwing = 0.55F + random.nextFloat() * 1.0F;
         ribbon.alphaScale = 0.62F + random.nextFloat() * 0.18F;
         ribbon.brightness = 0.72F + random.nextFloat() * 0.12F;
         ribbon.yawDrift = -0.018F + random.nextFloat() * 0.036F;
         ribbon.spacing = 34.0F;
      }

      ribbon.baseHeight = ribbon.height;
      ribbon.baseWaveAmp = ribbon.waveAmp;
      return ribbon;
   }

   private static PathsAuroraRenderer.AuroraRibbon createRitualRibbon(RandomSource random, PathsAuroraRenderer.AwakeningAurora awakening, int lane) {
      double angle = lane * Math.PI * 2.0 / Math.max(1, 74) + random.nextDouble() * 0.18;
      double radius = 130.0 + lane * 3.8 + random.nextDouble() * 120.0;
      PathsAuroraRenderer.AuroraRibbon ribbon = new PathsAuroraRenderer.AuroraRibbon();
      ribbon.x = awakening.center.x + Math.cos(angle) * radius;
      ribbon.z = awakening.center.z + Math.sin(angle) * radius;
      ribbon.yBase = (float)(awakening.center.y - 110.0 + random.nextFloat() * 54.0F);
      ribbon.height = 520.0F + random.nextFloat() * 720.0F;
      ribbon.baseHeight = ribbon.height;
      ribbon.baseWidth = 2.8F + random.nextFloat() * 6.6F;
      ribbon.waveAmp = 14.0F + random.nextFloat() * 30.0F;
      ribbon.baseWaveAmp = ribbon.waveAmp;
      ribbon.waveFreq = 0.008F + random.nextFloat() * 0.02F;
      ribbon.yaw = (float)Math.toDegrees(angle) + 90.0F;
      ribbon.baseRoll = -35.0F + random.nextFloat() * 70.0F;
      ribbon.roll = ribbon.baseRoll;
      ribbon.rollSwing = 4.0F + random.nextFloat() * 6.5F;
      ribbon.basePitch = -34.0F + random.nextFloat() * 68.0F;
      ribbon.pitch = ribbon.basePitch;
      ribbon.pitchSwing = 3.0F + random.nextFloat() * 5.0F;
      ribbon.seed = random.nextFloat() * 1000.0F;
      ribbon.yawDrift = -0.14F + random.nextFloat() * 0.28F;
      ribbon.alpha = 0.0F;
      ribbon.alphaScale = 1.35F + random.nextFloat() * 1.05F;
      ribbon.brightness = 1.8F + random.nextFloat() * 1.55F;
      ribbon.spacing = 0.0F;
      ribbon.age = 0;
      ribbon.main = false;
      ribbon.shapeType = 4;
      ribbon.widthProfile = random.nextInt(4);
      ribbon.uOffset = random.nextFloat();
      ribbon.ritual = true;
      ribbon.ritualLane = lane;
      ribbon.birthAngle = angle;
      ribbon.orbitRadius = radius;
      ribbon.orbitDir = lane % 2 == 0 ? 1.0F : -1.0F;
      ribbon.obedience = 1.0F;
      return ribbon;
   }

   private static void drawAuroraRibbon(
      PoseStack poseStack,
      PathsAuroraRenderer.AuroraRibbon ribbon,
      float height,
      float width,
      float waveAmp,
      float waveFreq,
      float time,
      float seed,
      float uOffset,
      PathsAuroraRenderer.AwakeningAurora awakening,
      boolean awakenedAlive
   ) {
      Matrix4f mat = poseStack.last().pose();
      BufferBuilder bb = Tesselator.getInstance().begin(Mode.TRIANGLE_STRIP, DefaultVertexFormat.POSITION_TEX);
      float halfW = width * 0.5F;
      float ritualPower = awakening == null ? 0.0F : awakening.power;
      float ritualClimax = awakening == null ? 0.0F : awakening.climax;
      float ritualImpact = awakening == null ? 0.0F : awakening.impact;
      float impactPulse = awakening == null ? 0.0F : awakening.impactPulse;
      float collapse = awakening == null ? 0.0F : awakening.collapse;
      float goldWave = awakenedAlive ? 1.0F : 0.0F;

      for (int i = 0; i <= 160; i++) {
         float t = i / 160.0F;
         float y = t * height;
         float sway = computeSway(ribbon, t, y, waveAmp, waveFreq, time, seed);
         float widthScale = computeWidthScale(ribbon, t);
         if (awakening != null && ribbon.obedience > 0.0F) {
            float middle = Mth.sin(t * (float) Math.PI);
            float upper = t * t;
            float pillar = ritualClimax;
            float spiral = (float)Math.sin(seed * 0.13F + time * (0.045F + pillar * 0.145F + ritualImpact * 0.245F) + t * 42.0F);
            float second = (float)Math.sin(seed * 0.41F - time * 0.085F + t * 83.0F);
            float burst = (float)Math.sin(t * (float) Math.PI * 9.0F + time * 0.19F + seed);
            float obey = ribbon.obedience;
            sway += spiral * middle * obey * (8.0F + ritualPower * 14.0F + pillar * 54.0F + impactPulse * 86.0F);
            sway += second * upper * obey * (4.0F + ritualPower * 12.0F + pillar * 18.0F);
            sway += burst * obey * (impactPulse * 72.0F + collapse * 26.0F);
            y += upper * obey * (pillar * 230.0F + impactPulse * 80.0F + collapse * 180.0F);
            widthScale *= 1.0F + middle * obey * (ritualPower * 0.65F + pillar * 1.75F + impactPulse * 2.65F + collapse * 0.9F);
         }

         if (ribbon.ritual && awakening != null) {
            float ascent = Mth.sin(t * (float) Math.PI);
            float upperPull = t * t;
            float violentWave = (float)Math.sin(seed * 0.13F + time * (0.07F + ritualClimax * 0.15F + ritualImpact * 0.26F) + t * 48.0F)
               * (24.0F + ritualClimax * 58.0F + impactPulse * 104.0F);
            float secondWave = (float)Math.sin(seed * 0.41F - time * 0.105F + t * 91.0F) * (12.0F + ritualPower * 24.0F);
            float collapseWave = (float)Math.sin(t * (float) Math.PI * 11.0F + time * 0.21F) * collapse * 58.0F;
            sway += violentWave * ascent + secondWave * upperPull + collapseWave;
            widthScale *= 1.0F + ascent * ritualPower * 1.35F + ritualClimax * 1.05F + impactPulse * 1.85F;
         }

         if (awakenedAlive) {
            float holy = Mth.sin(t * (float) Math.PI);
            float pulse = (float)Math.sin(time * 0.055F + seed * 0.23F + t * 18.0F) * 0.5F + 0.5F;
            sway = (float)(sway + Math.sin(time * 0.018F + seed + t * 8.0F) * holy * (3.5F + pulse * 5.0F) * goldWave);
            widthScale *= 1.0F + holy * (0.22F + pulse * 0.2F) * goldWave;
         }

         float left = sway - halfW * widthScale;
         float right = sway + halfW * widthScale;
         float uLeft = 0.0F + uOffset;
         float uRight = 1.0F + uOffset;
         float v = 1.0F - t;
         bb.addVertex(mat, left, y, 0.0F).setUv(uLeft, v);
         bb.addVertex(mat, right, y, 0.0F).setUv(uRight, v);
      }

      BufferUploader.drawWithShader(bb.buildOrThrow());
   }

   private static void drawGoldenAuroraRibbon(
      PoseStack poseStack,
      PathsAuroraRenderer.AuroraRibbon ribbon,
      float height,
      float width,
      float waveAmp,
      float waveFreq,
      float time,
      float seed,
      float alpha,
      PathsAuroraRenderer.AwakeningAurora awakening
   ) {
      RenderSystem.setShader(GameRenderer::getPositionColorShader);
      drawGoldenAuroraStrip(poseStack, ribbon, height, width * 1.95F, waveAmp, waveFreq, time, seed, alpha * 0.22F, awakening, 255, 118, 12);
      drawGoldenAuroraStrip(poseStack, ribbon, height, width * 1.35F, waveAmp, waveFreq, time, seed, alpha * 0.4F, awakening, 255, 184, 38);
      drawGoldenAuroraStrip(poseStack, ribbon, height, width * 0.8F, waveAmp, waveFreq, time, seed, alpha * 0.7F, awakening, 255, 228, 112);
   }

   private static void drawGoldenAuroraStrip(
      PoseStack poseStack,
      PathsAuroraRenderer.AuroraRibbon ribbon,
      float height,
      float width,
      float waveAmp,
      float waveFreq,
      float time,
      float seed,
      float alpha,
      PathsAuroraRenderer.AwakeningAurora awakening,
      int red,
      int green,
      int blue
   ) {
      Matrix4f mat = poseStack.last().pose();
      BufferBuilder bb = Tesselator.getInstance().begin(Mode.TRIANGLE_STRIP, DefaultVertexFormat.POSITION_COLOR);
      float halfW = width * 0.5F;
      float ritualPower = awakening == null ? 0.0F : awakening.power;
      float ritualClimax = awakening == null ? 0.0F : awakening.climax;
      float ritualImpact = awakening == null ? 0.0F : awakening.impact;
      float impactPulse = awakening == null ? 0.0F : awakening.impactPulse;
      float collapse = awakening == null ? 0.0F : awakening.collapse;

      for (int i = 0; i <= 160; i++) {
         float t = i / 160.0F;
         float y = t * height;
         float sway = computeSway(ribbon, t, y, waveAmp, waveFreq, time, seed);
         float widthScale = computeWidthScale(ribbon, t);
         if (awakening != null && ribbon.obedience > 0.0F) {
            float middle = Mth.sin(t * (float) Math.PI);
            float upper = t * t;
            float pillar = ritualClimax;
            float spiral = (float)Math.sin(seed * 0.13F + time * (0.045F + pillar * 0.145F + ritualImpact * 0.245F) + t * 42.0F);
            float second = (float)Math.sin(seed * 0.41F - time * 0.085F + t * 83.0F);
            float burst = (float)Math.sin(t * (float) Math.PI * 9.0F + time * 0.19F + seed);
            float obey = ribbon.obedience;
            sway += spiral * middle * obey * (9.0F + ritualPower * 14.0F + pillar * 54.0F + impactPulse * 86.0F);
            sway += second * upper * obey * (5.0F + ritualPower * 13.0F + pillar * 20.0F);
            sway += burst * obey * (impactPulse * 74.0F + collapse * 28.0F);
            y += upper * obey * (pillar * 230.0F + impactPulse * 80.0F + collapse * 180.0F);
            widthScale *= 1.0F + middle * obey * (ritualPower * 0.65F + pillar * 1.75F + impactPulse * 2.65F + collapse * 0.9F);
         }

         if (ribbon.ritual && awakening != null) {
            float ascent = Mth.sin(t * (float) Math.PI);
            float upperPull = t * t;
            float violentWave = (float)Math.sin(seed * 0.13F + time * (0.07F + ritualClimax * 0.15F + ritualImpact * 0.26F) + t * 48.0F)
               * (24.0F + ritualClimax * 58.0F + impactPulse * 104.0F);
            float secondWave = (float)Math.sin(seed * 0.41F - time * 0.105F + t * 91.0F) * (12.0F + ritualPower * 24.0F);
            float collapseWave = (float)Math.sin(t * (float) Math.PI * 11.0F + time * 0.21F) * collapse * 58.0F;
            sway += violentWave * ascent + secondWave * upperPull + collapseWave;
            widthScale *= 1.0F + ascent * ritualPower * 1.35F + ritualClimax * 1.05F + impactPulse * 1.85F;
         }

         float holy = Mth.sin(t * (float) Math.PI);
         float pulse = (float)Math.sin(time * 0.055F + seed * 0.23F + t * 18.0F) * 0.5F + 0.5F;
         sway = (float)(sway + Math.sin(time * 0.018F + seed + t * 8.0F) * holy * (5.5F + pulse * 6.5F));
         widthScale *= 1.0F + holy * (0.3F + pulse * 0.22F);
         float left = sway - halfW * widthScale;
         float right = sway + halfW * widthScale;
         float verticalGlow = 0.4F + Mth.sin(t * (float) Math.PI) * 0.6F;
         int a = Mth.clamp((int)(alpha * verticalGlow * 255.0F), 0, 255);
         bb.addVertex(mat, left, y, 0.0F).setColor(red, green, blue, a);
         bb.addVertex(mat, right, y, 0.0F).setColor(red, green, blue, a);
      }

      BufferUploader.drawWithShader(bb.buildOrThrow());
   }

   private static float computeSway(PathsAuroraRenderer.AuroraRibbon ribbon, float t, float y, float waveAmp, float waveFreq, float time, float seed) {
      float main = (float)Math.sin(seed + time * 0.01F + y * waveFreq) * waveAmp;
      float secondary = (float)Math.sin(seed * 0.37F + time * 0.006F + y * waveFreq * 0.45F) * (waveAmp * 0.16F);
      float tertiary = (float)Math.sin(seed * 0.19F + time * 0.0034F + y * waveFreq * 0.18F) * (waveAmp * 0.08F);
      if (ribbon.shapeType == 1) {
         float bow = Mth.sin(t * (float) Math.PI) * waveAmp * 0.95F;
         return main + secondary + tertiary + bow;
      } else if (ribbon.shapeType == 2) {
         float pinch = (float)Math.sin(t * (float) Math.PI * 2.0F + seed * 0.05F) * waveAmp * 0.72F;
         return main + secondary + tertiary + pinch;
      } else if (ribbon.shapeType == 3) {
         float sweep = (t - 0.5F) * waveAmp * 2.7F;
         return main + secondary + tertiary + sweep;
      } else if (ribbon.shapeType == 4) {
         float corkscrew = (float)Math.sin(t * (float) Math.PI * 3.0F + seed * 0.09F) * waveAmp * 0.55F;
         float lift = Mth.sin(t * (float) Math.PI) * waveAmp * 0.45F;
         return main + secondary + tertiary + corkscrew + lift;
      } else {
         return main + secondary + tertiary;
      }
   }

   private static float computeWidthScale(PathsAuroraRenderer.AuroraRibbon ribbon, float t) {
      float baseBottom = Mth.lerp(Mth.clamp(t / 0.08F, 0.0F, 1.0F), 0.88F, 1.0F);
      float baseTop = 1.0F - Mth.clamp((t - 0.84F) / 0.16F, 0.0F, 1.0F) * 0.2F;
      float scale = baseBottom * baseTop;
      if (ribbon.widthProfile == 1) {
         scale *= 0.78F + Mth.sin(t * (float) Math.PI) * 0.34F;
      } else if (ribbon.widthProfile == 2) {
         scale *= 1.12F - Mth.sin(t * (float) Math.PI) * 0.26F;
      } else if (ribbon.widthProfile == 3) {
         scale *= 0.92F + (float)Math.sin(t * (float) Math.PI * 2.0F) * 0.18F;
      }

      if (!ribbon.main) {
         scale *= 0.9F + Mth.sin(t * (float) Math.PI) * 0.2F;
      }

      if (ribbon.ritual) {
         scale *= 1.0F + Mth.sin(t * (float) Math.PI) * 1.25F;
      }

      return scale;
   }

   private static void renderRitualSkyVortex(PoseStack poseStack, Vec3 camPos, PathsAuroraRenderer.AwakeningAurora awakening, float time, boolean awakenedAlive) {
      RenderSystem.setShader(GameRenderer::getPositionColorShader);
      RenderSystem.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
      poseStack.pushPose();
      poseStack.translate(awakening.center.x - camPos.x, awakening.center.y + 132.0 - camPos.y, awakening.center.z - camPos.z);
      Matrix4f mat = poseStack.last().pose();
      BufferBuilder bb = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
      int rings = 16;
      int segments = 112;

      for (int r = 0; r < rings; r++) {
         double radius = 30.0 + r * 15.5 + awakening.power * 76.0 + awakening.climax * 96.0 + awakening.impactPulse * 155.0 - awakening.collapse * 48.0;
         double y = r * 3.1 + Math.sin(time * 0.035 + r) * (2.4 + awakening.climax * 4.0);
         double spin = time * (0.054 + r * 0.004 + awakening.climax * 0.04 + awakening.impact * 0.065) * (r % 2 == 0 ? 1.0 : -1.0);
         float width = 1.2F + awakening.power * 2.5F + awakening.climax * 5.2F + awakening.impactPulse * 8.0F;
         float alpha = (0.08F + awakening.power * 0.125F + awakening.climax * 0.25F + awakening.impact * 0.3F) * (1.0F - r * 0.052F);
         int red = awakenedAlive ? 255 : (r % 2 == 0 ? 255 : 145);
         int green = awakenedAlive ? 184 : (r % 2 == 0 ? 242 : 70);
         int blue = awakenedAlive ? 38 : (r % 2 == 0 ? 210 : 255);

         for (int i = 0; i < segments; i++) {
            double wave = Math.sin(time * 0.075 + i * 0.17 + r) * (0.025 + awakening.impact * 0.045);
            double a1 = (float) (Math.PI * 2) * i / segments + spin + wave;
            double a2 = (float) (Math.PI * 2) * (i + 1) / segments + spin + wave;
            addRingQuad(bb, mat, y, radius, width, a1, a2, red, green, blue, awakenedAlive ? alpha * 1.55F : alpha);
         }
      }

      BufferUploader.drawWithShader(bb.buildOrThrow());
      poseStack.popPose();
   }

   private static void renderObedienceShockwaves(
      PoseStack poseStack, Vec3 camPos, PathsAuroraRenderer.AwakeningAurora awakening, float time, boolean awakenedAlive
   ) {
      if (!(awakening.power <= 0.08F)) {
         RenderSystem.setShader(GameRenderer::getPositionColorShader);
         RenderSystem.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
         poseStack.pushPose();
         poseStack.translate(awakening.center.x - camPos.x, awakening.center.y + 82.0 - camPos.y, awakening.center.z - camPos.z);
         Matrix4f mat = poseStack.last().pose();
         BufferBuilder bb = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         int rings = 5;
         int segments = 112;

         for (int r = 0; r < rings; r++) {
            float cycle = (time * (0.012F + awakening.climax * 0.018F + awakening.impact * 0.035F) + r * 0.19F) % 1.0F;
            float pulse = 1.0F - Math.abs(cycle * 2.0F - 1.0F);
            pulse *= pulse;
            double radius = 32.0 + cycle * (240.0 + awakening.climax * 210.0 + awakening.impact * 320.0);
            float width = 1.4F + pulse * (3.8F + awakening.impact * 8.0F);
            float alpha = (0.03F + awakening.power * 0.05F + awakening.climax * 0.07F + awakening.impact * 0.12F) * pulse;
            int red = awakenedAlive ? 255 : (r % 2 == 0 ? 255 : 120);
            int green = awakenedAlive ? 228 : (r % 2 == 0 ? 250 : 58);
            int blue = awakenedAlive ? 112 : (r % 2 == 0 ? 220 : 255);

            for (int i = 0; i < segments; i++) {
               double a1 = (float) (Math.PI * 2) * i / segments;
               double a2 = (float) (Math.PI * 2) * (i + 1) / segments;
               addRingQuad(bb, mat, r * 4.0, radius, width, a1, a2, red, green, blue, awakenedAlive ? alpha * 1.65F : alpha);
            }
         }

         BufferUploader.drawWithShader(bb.buildOrThrow());
         poseStack.popPose();
      }
   }

   private static void renderJudgementAuroraSpears(
      PoseStack poseStack, Vec3 camPos, PathsAuroraRenderer.AwakeningAurora awakening, float time, boolean awakenedAlive
   ) {
      RenderSystem.setShader(GameRenderer::getPositionColorShader);
      RenderSystem.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
      poseStack.pushPose();
      poseStack.translate(-camPos.x, -camPos.y, -camPos.z);
      Matrix4f mat = poseStack.last().pose();
      BufferBuilder bb = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
      int spears = 20 + (int)(awakening.climax * 38.0F) + (int)(awakening.impact * 50.0F);

      for (int i = 0; i < spears; i++) {
         float seed = hash(i * 991 + 17);
         float seed2 = hash(i * 701 + 31);
         double angle = seed * (float) (Math.PI * 2) + time * (0.012 + seed2 * 0.026 + awakening.climax * 0.04);
         double radius = 22.0 + seed2 * (145.0 + awakening.climax * 170.0 + awakening.impactPulse * 240.0);
         double x = awakening.center.x + Math.cos(angle) * radius;
         double z = awakening.center.z + Math.sin(angle) * radius;
         double drop = awakening.climax * (90.0 + seed2 * 70.0) + awakening.impact * (180.0 + seed * 100.0);
         double top = awakening.center.y + 290.0 + seed * 150.0 - drop;
         double bottom = awakening.center.y + 34.0 + Math.sin(time * 0.07 + i) * 28.0 - awakening.impact * 34.0;
         float width = 0.8F + awakening.power * 1.45F + awakening.climax * 2.7F + awakening.impact * 4.4F;
         float alpha = 0.045F + awakening.power * 0.06F + awakening.climax * 0.165F + awakening.impact * 0.28F;
         int red = awakenedAlive ? 255 : (i % 2 == 0 ? 255 : 122);
         int green = awakenedAlive ? 228 : (i % 2 == 0 ? 246 : 54);
         int blue = awakenedAlive ? 112 : (i % 2 == 0 ? 214 : 255);
         addVerticalAuroraSpear(bb, mat, x, bottom, z, top - bottom, width, (float)angle, red, green, blue, awakenedAlive ? alpha * 1.65F : alpha);
      }

      BufferUploader.drawWithShader(bb.buildOrThrow());
      poseStack.popPose();
   }

   private static void renderSkyCrack(PoseStack poseStack, Vec3 camPos, PathsAuroraRenderer.AwakeningAurora awakening, float time, boolean awakenedAlive) {
      if (!(awakening.climax <= 0.05F)) {
         RenderSystem.setShader(GameRenderer::getPositionColorShader);
         RenderSystem.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
         poseStack.pushPose();
         poseStack.translate(awakening.center.x - camPos.x, awakening.center.y + 116.0 - camPos.y, awakening.center.z - camPos.z);
         Matrix4f mat = poseStack.last().pose();
         BufferBuilder bb = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         int cracks = 20;

         for (int c = 0; c < cracks; c++) {
            float seed = hash(c * 5107 + 77);
            double baseAngle = seed * (float) (Math.PI * 2) + time * 0.02;
            double lastX = Math.cos(baseAngle) * 8.0;
            double lastZ = Math.sin(baseAngle) * 8.0;
            double lastY = 0.0;
            int segments = 8;

            for (int s = 1; s <= segments; s++) {
               double t = (double)s / segments;
               double wave = Math.sin(time * 0.21 + c * 1.9 + s * 1.2) * (8.0 + awakening.impact * 28.0);
               double radius = 18.0 + t * (160.0 + awakening.climax * 125.0 + awakening.impactPulse * 240.0);
               double angle = baseAngle + wave * 0.018;
               double nextX = Math.cos(angle) * radius;
               double nextZ = Math.sin(angle) * radius;
               double nextY = Math.sin(t * Math.PI) * (38.0 + awakening.climax * 52.0 + awakening.impactPulse * 44.0);
               float width = 1.4F + awakening.climax * 3.0F + awakening.impact * 5.2F;
               float alpha = (0.085F + awakening.climax * 0.21F + awakening.impact * 0.33F) * (float)(1.0 - t * 0.45);
               int red = awakenedAlive ? 255 : (c % 2 == 0 ? 255 : 120);
               int green = awakenedAlive ? 184 : (c % 2 == 0 ? 250 : 42);
               int blue = awakenedAlive ? 38 : (c % 2 == 0 ? 220 : 255);
               addRibbonSegment(bb, mat, lastX, lastY, lastZ, nextX, nextY, nextZ, width, red, green, blue, awakenedAlive ? alpha * 1.55F : alpha);
               lastX = nextX;
               lastY = nextY;
               lastZ = nextZ;
            }
         }

         BufferUploader.drawWithShader(bb.buildOrThrow());
         poseStack.popPose();
      }
   }

   private static void renderAwakenedGoldenArena(PoseStack poseStack, Vec3 camPos, FeldwayAwakenedEntity awakened, float time) {
      RenderSystem.setShader(GameRenderer::getPositionColorShader);
      RenderSystem.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
      double x = awakened.getX();
      double y = awakened.getY();
      double z = awakened.getZ();
      renderArenaFloorSigil(poseStack, camPos, x, y + 0.18, z, time);
      renderArenaBoundary(poseStack, camPos, x, y + 4.0, z, time);
      renderArenaJudgementPillars(poseStack, camPos, x, y, z, time);
      renderArenaHeavenGates(poseStack, camPos, x, y + 54.0, z, time);
      renderArenaHaloStorm(poseStack, camPos, x, y + 78.0, z, time);
      renderArenaFallingScript(poseStack, camPos, x, y, z, time);
   }

   private static void renderArenaFloorSigil(PoseStack poseStack, Vec3 camPos, double x, double y, double z, float time) {
      poseStack.pushPose();
      poseStack.translate(x - camPos.x, y - camPos.y, z - camPos.z);
      Matrix4f mat = poseStack.last().pose();
      BufferBuilder bb = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
      int segments = 144;
      double spinA = time * 0.012;
      double spinB = -time * 0.017;
      double pulse = Math.sin(time * 0.045) * 0.5 + 0.5;

      for (int i = 0; i < segments; i++) {
         double a1 = (float) (Math.PI * 2) * i / segments;
         double a2 = (float) (Math.PI * 2) * (i + 1) / segments;
         addRingQuad(bb, mat, 0.0, 76.0 + pulse * 3.0, 2.6F, a1 + spinA, a2 + spinA, 255, 118, 12, 0.1F);
         addRingQuad(bb, mat, 0.0, 108.0, 1.8F, a1 - spinA * 0.6, a2 - spinA * 0.6, 255, 184, 38, 0.12F);
         addRingQuad(bb, mat, 0.0, 142.0 + pulse * 5.0, 3.0F, a1 + spinB, a2 + spinB, 255, 228, 112, 0.09F);
         addRingQuad(bb, mat, 0.0, 184.0, 2.4F, a1 - spinB * 0.8, a2 - spinB * 0.8, 255, 184, 38, 0.08F);
      }

      for (int i = 0; i < 16; i++) {
         double angle = (float) (Math.PI * 2) * i / 16.0 + spinA;
         double inner = 38.0;
         double outer = 190.0;
         double x1 = Math.cos(angle) * inner;
         double z1 = Math.sin(angle) * inner;
         double x2 = Math.cos(angle) * outer;
         double z2 = Math.sin(angle) * outer;
         float alpha = i % 2 == 0 ? 0.105F : 0.065F;
         addRibbonSegment(bb, mat, x1, 0.0, z1, x2, 0.0, z2, i % 2 == 0 ? 1.7F : 1.0F, 255, 228, 112, alpha);
      }

      for (int i = 0; i < 12; i++) {
         double angle = (float) (Math.PI * 2) * i / 12.0 - spinB;
         double angle2 = angle + (float) (Math.PI / 12);
         double r1 = 92.0;
         double r2 = 128.0;
         double r3 = 164.0;
         addRibbonSegment(
            bb, mat, Math.cos(angle) * r1, 0.0, Math.sin(angle) * r1, Math.cos(angle2) * r2, 0.0, Math.sin(angle2) * r2, 1.35F, 255, 118, 12, 0.1F
         );
         addRibbonSegment(
            bb, mat, Math.cos(angle2) * r2, 0.0, Math.sin(angle2) * r2, Math.cos(angle) * r3, 0.0, Math.sin(angle) * r3, 1.35F, 255, 184, 38, 0.12F
         );
      }

      BufferUploader.drawWithShader(bb.buildOrThrow());
      poseStack.popPose();
   }

   private static void renderArenaBoundary(PoseStack poseStack, Vec3 camPos, double x, double y, double z, float time) {
      poseStack.pushPose();
      poseStack.translate(x - camPos.x, y - camPos.y, z - camPos.z);
      Matrix4f mat = poseStack.last().pose();
      BufferBuilder bb = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
      int segments = 128;
      double spin = time * 0.01;
      double pulse = Math.sin(time * 0.035) * 0.5 + 0.5;

      for (int layer = 0; layer < 4; layer++) {
         double radius = 208.0 + layer * 9.0 + pulse * 5.0;
         float width = 2.0F + layer * 0.65F;
         float alpha = 0.08F - layer * 0.012F;

         for (int i = 0; i < segments; i++) {
            double a1 = (float) (Math.PI * 2) * i / segments + spin * (layer % 2 == 0 ? 1.0 : -1.0);
            double a2 = (float) (Math.PI * 2) * (i + 1) / segments + spin * (layer % 2 == 0 ? 1.0 : -1.0);
            addRingQuad(bb, mat, layer * 10.0, radius, width, a1, a2, 255, 184, 38, alpha);
         }
      }

      for (int i = 0; i < 24; i++) {
         double angle = (float) (Math.PI * 2) * i / 24.0 + spin;
         double radius = 214.0 + Math.sin(time * 0.05 + i) * 5.0;
         double px = Math.cos(angle) * radius;
         double pz = Math.sin(angle) * radius;
         double height = 22.0 + i % 3 * 10.0 + Math.sin(time * 0.04 + i * 0.8) * 5.0;
         addVerticalAuroraSpear(bb, mat, px, 0.0, pz, height, i % 2 == 0 ? 2.2F : 1.4F, (float)angle, 255, 228, 112, 0.1F);
      }

      BufferUploader.drawWithShader(bb.buildOrThrow());
      poseStack.popPose();
   }

   private static void renderArenaJudgementPillars(PoseStack poseStack, Vec3 camPos, double x, double y, double z, float time) {
      poseStack.pushPose();
      poseStack.translate(-camPos.x, -camPos.y, -camPos.z);
      Matrix4f mat = poseStack.last().pose();
      BufferBuilder bb = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
      int pillars = 12;

      for (int i = 0; i < pillars; i++) {
         double baseAngle = (float) (Math.PI * 2) * i / pillars + time * 0.012;
         double radius = 136.0 + i % 2 * 46.0 + Math.sin(time * 0.028 + i) * 8.0;
         double px = x + Math.cos(baseAngle) * radius;
         double pz = z + Math.sin(baseAngle) * radius;
         double bottom = y + 2.0;
         double height = 122.0 + Math.sin(time * 0.035 + i * 0.9) * 24.0;
         float width = 3.0F + i % 3 * 0.75F;
         float alpha = 0.08F + (float)(Math.sin(time * 0.06 + i) * 0.5 + 0.5) * 0.055F;
         addVerticalAuroraSpear(bb, mat, px, bottom, pz, height, width * 3.0F, (float)baseAngle, 255, 118, 12, alpha * 0.4F);
         addVerticalAuroraSpear(bb, mat, px, bottom, pz, height, width * 1.6F, (float)baseAngle, 255, 184, 38, alpha * 0.8F);
         addVerticalAuroraSpear(bb, mat, px, bottom, pz, height, width * 0.65F, (float)baseAngle, 255, 228, 112, alpha * 1.2F);
         double sideAngle = baseAngle + (Math.PI / 2);
         addRibbonSegment(
            bb,
            mat,
            px,
            bottom + height * 0.58,
            pz,
            px + Math.cos(sideAngle) * 24.0,
            bottom + height * 0.78,
            pz + Math.sin(sideAngle) * 24.0,
            1.2F,
            255,
            228,
            112,
            alpha
         );
         addRibbonSegment(
            bb,
            mat,
            px,
            bottom + height * 0.58,
            pz,
            px - Math.cos(sideAngle) * 24.0,
            bottom + height * 0.78,
            pz - Math.sin(sideAngle) * 24.0,
            1.2F,
            255,
            228,
            112,
            alpha
         );
      }

      BufferUploader.drawWithShader(bb.buildOrThrow());
      poseStack.popPose();
   }

   private static void renderArenaHeavenGates(PoseStack poseStack, Vec3 camPos, double x, double y, double z, float time) {
      poseStack.pushPose();
      poseStack.translate(x - camPos.x, y - camPos.y, z - camPos.z);
      Matrix4f mat = poseStack.last().pose();
      BufferBuilder bb = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
      int gates = 8;
      double spin = -time * 0.018;

      for (int i = 0; i < gates; i++) {
         double angle = (float) (Math.PI * 2) * i / gates + spin;
         double radius = 98.0 + Math.sin(time * 0.035 + i) * 10.0;
         double cx = Math.cos(angle) * radius;
         double cz = Math.sin(angle) * radius;
         double tangent = angle + (Math.PI / 2);
         double w = 11.0 + i % 2 * 3.0;
         double h = 48.0 + Math.sin(time * 0.045 + i * 0.7) * 8.0;
         double lx = Math.cos(tangent) * w;
         double lz = Math.sin(tangent) * w;
         double x1 = cx - lx;
         double z1 = cz - lz;
         double x2 = cx + lx;
         double z2 = cz + lz;
         addRibbonSegment(bb, mat, x1, -h * 0.42, z1, x1, h * 0.42, z1, 2.2F, 255, 184, 38, 0.14F);
         addRibbonSegment(bb, mat, x2, -h * 0.42, z2, x2, h * 0.42, z2, 2.2F, 255, 184, 38, 0.14F);
         addRibbonSegment(bb, mat, x1, h * 0.42, z1, x2, h * 0.42, z2, 2.2F, 255, 228, 112, 0.18F);
         addRibbonSegment(bb, mat, x1, -h * 0.42, z1, x2, -h * 0.42, z2, 1.2F, 255, 118, 12, 0.1F);
      }

      BufferUploader.drawWithShader(bb.buildOrThrow());
      poseStack.popPose();
   }

   private static void renderArenaHaloStorm(PoseStack poseStack, Vec3 camPos, double x, double y, double z, float time) {
      poseStack.pushPose();
      poseStack.translate(x - camPos.x, y - camPos.y, z - camPos.z);
      renderArenaHaloRing(poseStack, time, 0.0F, 0.0F, 68.0F, 2.0F, 0.16F, 0.03F, 0.0F);
      renderArenaHaloRing(poseStack, time, -35.0F, 18.0F, 92.0F, 2.3F, 0.12F, -0.024F, 12.0F);
      renderArenaHaloRing(poseStack, time, 28.0F, -24.0F, 118.0F, 2.8F, 0.1F, 0.019F, -8.0F);
      renderArenaHaloRing(poseStack, time, 62.0F, 0.0F, 48.0F, 1.4F, 0.18F, -0.044F, 22.0F);
      renderArenaHaloRing(poseStack, time, -58.0F, 0.0F, 42.0F, 1.2F, 0.16F, 0.052F, -22.0F);
      poseStack.popPose();
   }

   private static void renderArenaHaloRing(
      PoseStack poseStack, float time, float tiltX, float tiltZ, float radius, float width, float alpha, float spinSpeed, float yOffset
   ) {
      poseStack.pushPose();
      poseStack.translate(0.0F, yOffset, 0.0F);
      poseStack.mulPose(new Quaternionf().rotateX((float)Math.toRadians(tiltX)));
      poseStack.mulPose(new Quaternionf().rotateZ((float)Math.toRadians(tiltZ)));
      Matrix4f mat = poseStack.last().pose();
      BufferBuilder bb = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
      int segments = 128;
      double spin = time * spinSpeed;
      double wave = Math.sin(time * 0.04 + radius * 0.07) * 1.5;

      for (int i = 0; i < segments; i++) {
         double a1 = (float) (Math.PI * 2) * i / segments + spin;
         double a2 = (float) (Math.PI * 2) * (i + 1) / segments + spin;
         float pulse = 0.65F + (float)(Math.sin(time * 0.08 + i * 0.17) * 0.5 + 0.5) * 0.35F;
         addRingQuad(bb, mat, wave, radius, width * 3.0F, a1, a2, 255, 118, 12, alpha * 0.28F * pulse);
         addRingQuad(bb, mat, wave, radius, width * 1.4F, a1, a2, 255, 184, 38, alpha * pulse);
         addRingQuad(bb, mat, wave, radius, width * 0.55F, a1, a2, 255, 228, 112, alpha * 1.35F * pulse);
      }

      BufferUploader.drawWithShader(bb.buildOrThrow());
      poseStack.popPose();
   }

   private static void renderArenaFallingScript(PoseStack poseStack, Vec3 camPos, double x, double y, double z, float time) {
      poseStack.pushPose();
      poseStack.translate(-camPos.x, -camPos.y, -camPos.z);
      Matrix4f mat = poseStack.last().pose();
      BufferBuilder bb = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
      int glyphs = 28;

      for (int i = 0; i < glyphs; i++) {
         float h1 = hash(i * 8191 + 9);
         float h2 = hash(i * 6151 + 31);
         float h3 = hash(i * 3571 + 57);
         double angle = h1 * (float) (Math.PI * 2) + time * (0.006 + h2 * 0.012);
         double radius = 52.0 + h2 * 160.0;
         double px = x + Math.cos(angle) * radius;
         double pz = z + Math.sin(angle) * radius;
         double cycle = (time * (0.02 + h3 * 0.008) + h1) % 1.0;
         double py = y + 118.0 - cycle * 110.0;
         double size = 5.0 + h3 * 8.0;
         float alpha = (float)(Math.sin(cycle * Math.PI) * 0.095);
         double angle2 = angle + (Math.PI / 2);
         double sx = Math.cos(angle2) * size;
         double sz = Math.sin(angle2) * size;
         addRibbonSegment(bb, mat, px - sx, py, pz - sz, px + sx, py + size * 0.25, pz + sz, 0.9F, 255, 228, 112, alpha);
         addRibbonSegment(bb, mat, px, py - size * 0.65, pz, px, py + size * 0.65, pz, 0.7F, 255, 184, 38, alpha * 0.8F);
         if (i % 3 == 0) {
            addRibbonSegment(
               bb, mat, px - sx * 0.55, py - size * 0.35, pz - sz * 0.55, px + sx * 0.55, py + size * 0.35, pz + sz * 0.55, 0.55F, 255, 118, 12, alpha
            );
         }
      }

      BufferUploader.drawWithShader(bb.buildOrThrow());
      poseStack.popPose();
   }

   private static void addRingQuad(
      BufferBuilder bb, Matrix4f mat, double y, double radius, float width, double a1, double a2, int red, int green, int blue, float alpha
   ) {
      double inner = radius - width;
      double outer = radius + width;
      int a = Mth.clamp((int)(alpha * 255.0F), 0, 255);
      bb.addVertex(mat, (float)(Math.cos(a1) * inner), (float)y, (float)(Math.sin(a1) * inner)).setColor(red, green, blue, 0);
      bb.addVertex(mat, (float)(Math.cos(a1) * outer), (float)y, (float)(Math.sin(a1) * outer)).setColor(red, green, blue, a);
      bb.addVertex(mat, (float)(Math.cos(a2) * outer), (float)y, (float)(Math.sin(a2) * outer)).setColor(red, green, blue, a);
      bb.addVertex(mat, (float)(Math.cos(a2) * inner), (float)y, (float)(Math.sin(a2) * inner)).setColor(red, green, blue, 0);
   }

   private static void addVerticalAuroraSpear(
      BufferBuilder bb, Matrix4f mat, double x, double y, double z, double height, float width, float angle, int red, int green, int blue, float alpha
   ) {
      double ox = Math.cos(angle) * width;
      double oz = Math.sin(angle) * width;
      int a = Mth.clamp((int)(alpha * 255.0F), 0, 255);
      bb.addVertex(mat, (float)(x - ox), (float)y, (float)(z - oz)).setColor(red, green, blue, 0);
      bb.addVertex(mat, (float)(x + ox), (float)y, (float)(z + oz)).setColor(red, green, blue, a);
      bb.addVertex(mat, (float)(x + ox * 0.35), (float)(y + height), (float)(z + oz * 0.35)).setColor(red, green, blue, 0);
      bb.addVertex(mat, (float)(x - ox * 0.35), (float)(y + height), (float)(z - oz * 0.35)).setColor(red, green, blue, a);
   }

   private static void addRibbonSegment(
      BufferBuilder bb, Matrix4f mat, double x1, double y1, double z1, double x2, double y2, double z2, float width, int red, int green, int blue, float alpha
   ) {
      Vec3 dir = new Vec3(x2 - x1, y2 - y1, z2 - z1);
      Vec3 side = dir.cross(new Vec3(0.0, 1.0, 0.0));
      if (side.lengthSqr() < 1.0E-4) {
         side = new Vec3(1.0, 0.0, 0.0);
      } else {
         side = side.normalize();
      }

      side = side.scale(width);
      int a = Mth.clamp((int)(alpha * 255.0F), 0, 255);
      bb.addVertex(mat, (float)(x1 - side.x), (float)y1, (float)(z1 - side.z)).setColor(red, green, blue, 0);
      bb.addVertex(mat, (float)(x1 + side.x), (float)y1, (float)(z1 + side.z)).setColor(red, green, blue, a);
      bb.addVertex(mat, (float)(x2 + side.x), (float)y2, (float)(z2 + side.z)).setColor(red, green, blue, a);
      bb.addVertex(mat, (float)(x2 - side.x), (float)y2, (float)(z2 - side.z)).setColor(red, green, blue, 0);
   }

   private static PathsAuroraRenderer.AwakeningAurora findAwakening(ClientLevel level, Vec3 camPos) {
      FeldwayAwakeningEntity best = null;
      double bestDistance = Double.MAX_VALUE;

      for (Entity entity : level.entitiesForRendering()) {
         if (entity instanceof FeldwayAwakeningEntity awakening && awakening.isAlive()) {
            double dist = awakening.position().distanceToSqr(camPos);
            if (dist < bestDistance && dist <= 384400.0) {
               bestDistance = dist;
               best = awakening;
            }
         }
      }

      if (best == null) {
         return null;
      }

      float progress = best.getAwakeningProgress();
      float power = smooth(Mth.clamp((progress - 0.08F) / 0.92F, 0.0F, 1.0F));
      float early = smooth(Mth.clamp((progress - 0.1F) / 0.28F, 0.0F, 1.0F));
      float mid = smooth(Mth.clamp((progress - 0.32F) / 0.3F, 0.0F, 1.0F));
      float climax = smooth(Mth.clamp((progress - 0.56F) / 0.36F, 0.0F, 1.0F));
      float impact = smooth(Mth.clamp((progress - 0.82F) / 0.18F, 0.0F, 1.0F));
      float impactPulse = Mth.sin(impact * (float) Math.PI);
      float collapse = smooth(Mth.clamp((impact - 0.55F) / 0.45F, 0.0F, 1.0F));
      return new PathsAuroraRenderer.AwakeningAurora(best.position(), progress, power, early, mid, climax, impact, impactPulse, collapse);
   }

   private static FeldwayAwakenedEntity findAwakened(ClientLevel level, Vec3 camPos) {
      FeldwayAwakenedEntity best = null;
      double bestDistance = Double.MAX_VALUE;

      for (Entity entity : level.entitiesForRendering()) {
         if (entity instanceof FeldwayAwakenedEntity awakened && awakened.isAlive() && !awakened.isRemoved()) {
            double dist = awakened.position().distanceToSqr(camPos);
            if (dist < bestDistance && dist <= 577600.0) {
               bestDistance = dist;
               best = awakened;
            }
         }
      }

      return best;
   }

   private static float smooth(float value) {
      return value * value * (3.0F - 2.0F * value);
   }

   private static float hash(int index) {
      long value = index * 341873128712L;
      value ^= value >>> 33;
      value *= -49064778989728563L;
      value ^= value >>> 33;
      value *= -4265267296055464877L;
      value ^= value >>> 33;
      return (float)(value & 16777215L) / 1.6777215E7F;
   }

   private static class AuroraRibbon {
      double x;
      double z;
      double birthAngle;
      double orbitRadius;
      float yBase;
      float height;
      float baseHeight;
      float baseWidth;
      float waveAmp;
      float baseWaveAmp;
      float waveFreq;
      float yaw;
      float baseRoll;
      float roll;
      float rollSwing;
      float basePitch;
      float pitch;
      float pitchSwing;
      float seed;
      float yawDrift;
      float alpha;
      float alphaScale;
      float brightness;
      float spacing;
      float uOffset;
      float orbitDir;
      float obedience;
      int age;
      boolean main;
      boolean ritual;
      int ritualLane;
      int shapeType;
      int widthProfile;
   }

   private record AwakeningAurora(
      Vec3 center, float progress, float power, float early, float mid, float climax, float impact, float impactPulse, float collapse
   ) {
   }
}
