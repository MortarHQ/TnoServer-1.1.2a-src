package com.github.wal_bos.moreskills.client.fx;

import com.github.wal_bos.moreskills.world.entity.FeldwayAwakeningEntity;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent.Stage;
import net.neoforged.neoforge.common.NeoForge;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

public final class FeldwayAwakeningCoronationBeam {
   private static final Map<UUID, FeldwayAwakeningCoronationBeam.BeamVisual> BEAMS = new HashMap<>();
   private static boolean initialized = false;

   private FeldwayAwakeningCoronationBeam() {
   }

   public static void init() {
      if (!initialized) {
         initialized = true;
         NeoForge.EVENT_BUS.addListener(FeldwayAwakeningCoronationBeam::render);
      }
   }

   private static void render(RenderLevelStageEvent event) {
      if (event.getStage() == Stage.AFTER_TRANSLUCENT_BLOCKS) {
         Minecraft minecraft = Minecraft.getInstance();
         if (minecraft.level != null && minecraft.player != null) {
            float partialTick = event.getPartialTick().getGameTimeDeltaPartialTick(false);
            double gameTime = (float)minecraft.level.getGameTime() + partialTick;

            for (Entity entity : minecraft.level.entitiesForRendering()) {
               if (entity instanceof FeldwayAwakeningEntity awakening && awakening.isAlive() && awakening.distanceToSqr(minecraft.player) <= 144400.0) {
                  FeldwayAwakeningCoronationBeam.BeamVisual visual = BEAMS.computeIfAbsent(
                     awakening.getUUID(), uuid -> new FeldwayAwakeningCoronationBeam.BeamVisual(uuid, awakening.position(), gameTime)
                  );
                  visual.update(awakening);
               }
            }

            PoseStack poseStack = event.getPoseStack();
            Vec3 camera = event.getCamera().getPosition();
            Iterator<Entry<UUID, FeldwayAwakeningCoronationBeam.BeamVisual>> iterator = BEAMS.entrySet().iterator();

            while (iterator.hasNext()) {
               FeldwayAwakeningCoronationBeam.BeamVisual visual = iterator.next().getValue();
               if (visual.isDead(gameTime)) {
                  iterator.remove();
               } else {
                  visual.render(minecraft, poseStack, camera, gameTime);
               }
            }
         } else {
            BEAMS.clear();
         }
      }
   }

   private static final class BeamVisual {
      private static final int DEATH_TICKS = 130;
      private static final int DEBRIS_BLOCKS = 220;
      private final UUID owner;
      private final Vec3 origin;
      private final double createdGameTime;
      private final float[] debrisSeeds = new float[220];
      private final float[] debrisSizes = new float[220];
      private final BlockState[] debrisStates = new BlockState[220];
      private int age;
      private int duration = 520;
      private int unseenTicks = 0;
      private boolean ending = false;
      private double endingStart = -1.0;
      private double clipCheckedAt = -999.0;
      private double portalY;
      private double impactY;

      private BeamVisual(UUID owner, Vec3 origin, double gameTime) {
         this.owner = owner;
         this.origin = new Vec3(origin.x, origin.y, origin.z);
         this.createdGameTime = gameTime;
         this.portalY = origin.y + 176.0;
         this.impactY = origin.y;

         for (int i = 0; i < 220; i++) {
            this.debrisSeeds[i] = hash(owner, 40000 + i) * (float) (Math.PI * 2);
            this.debrisSizes[i] = 0.4F + hash(owner, 41000 + i) * 3.15F;
            this.debrisStates[i] = pickDebrisState(i);
         }
      }

      private void update(FeldwayAwakeningEntity entity) {
         this.age = entity.getAwakeningAge();
         this.duration = Math.max(1, entity.getAwakeningDuration());
         this.unseenTicks = 0;
         if (this.age >= this.duration - 5 && !this.ending) {
            this.ending = true;
            Minecraft minecraft = Minecraft.getInstance();
            this.endingStart = minecraft.level == null ? this.createdGameTime : minecraft.level.getGameTime();
         }
      }

      private boolean isDead(double gameTime) {
         this.unseenTicks++;
         if (!this.ending && this.unseenTicks > 80) {
            this.ending = true;
            this.endingStart = gameTime;
         }

         if (!this.ending && this.age >= this.duration) {
            this.ending = true;
            this.endingStart = gameTime;
         }

         return this.ending && gameTime - this.endingStart > 130.0;
      }

      private void render(Minecraft minecraft, PoseStack poseStack, Vec3 camera, double gameTime) {
         this.resolveBeamClip(minecraft, gameTime);
         float progress = Mth.clamp((float)this.age / this.duration, 0.0F, 1.0F);
         float clouds = smooth(Mth.clamp((progress - 0.5F) / 0.2F, 0.0F, 1.0F));
         float opening = smooth(Mth.clamp((progress - 0.66F) / 0.11F, 0.0F, 1.0F));
         float beamWake = smooth(Mth.clamp((progress - 0.715F) / 0.1F, 0.0F, 1.0F));
         float descent = pow3(Mth.clamp((progress - 0.775F) / 0.092F, 0.0F, 1.0F));
         float impact = smooth(Mth.clamp((progress - 0.862F) / 0.034F, 0.0F, 1.0F));
         float dominion = smooth(Mth.clamp((progress - 0.888F) / 0.075F, 0.0F, 1.0F));
         float coronation = smooth(Mth.clamp((progress - 0.925F) / 0.075F, 0.0F, 1.0F));
         if (this.ending) {
            double deathAge = Math.max(0.0, gameTime - this.endingStart);
            float deathProgress = Mth.clamp((float)(deathAge / 130.0), 0.0F, 1.0F);
            float fade = 1.0F - deathProgress;
            clouds *= fade;
            opening *= fade;
            beamWake *= fade;
            descent = Math.max(descent, fade);
            impact *= fade;
            dominion *= fade;
            coronation *= fade;
         }

         if (!(clouds <= 0.01F) || !(beamWake <= 0.01F)) {
            double time = gameTime - this.createdGameTime;
            double centerX = this.origin.x;
            double centerZ = this.origin.z;
            double beamTop = this.portalY;
            double beamTip = Mth.lerp(descent, this.portalY - 2.5, this.impactY);
            double beamBottom = Math.min(beamTip, beamTop - 1.0);
            double beamHeight = beamTop - beamBottom;
            RenderSystem.enableBlend();
            RenderSystem.enableDepthTest();
            RenderSystem.depthFunc(515);
            RenderSystem.disableCull();
            RenderSystem.depthMask(false);
            RenderSystem.blendFuncSeparate(770, 1, 1, 0);
            RenderSystem.setShader(GameRenderer::getPositionColorShader);
            poseStack.pushPose();
            poseStack.translate(-camera.x, -camera.y, -camera.z);
            Matrix4f matrix = poseStack.last().pose();
            this.renderPortalCloudVolume(matrix, centerX, beamTop + 11.0, centerZ, time, clouds, opening, dominion);
            this.renderPortalIrisVolume(matrix, centerX, beamTop + 1.2, centerZ, time, clouds, opening, beamWake, dominion);
            if (beamWake > 0.01F && beamHeight > 1.0) {
               this.renderSmoothBeamVolume(matrix, centerX, beamBottom, centerZ, beamHeight, time, beamWake, descent, dominion, coronation);
               this.renderBeamHotCore(matrix, centerX, beamBottom, centerZ, beamHeight, time, beamWake, descent, coronation);
               this.renderBeamAccretionRibbons(matrix, centerX, beamBottom, centerZ, beamHeight, time, beamWake, descent, dominion, coronation);
               this.renderBeamHeadOrb(matrix, centerX, beamBottom, centerZ, time, beamWake, descent, dominion, coronation);
               this.renderCleanLightningThreads(matrix, centerX, beamBottom, centerZ, beamHeight, time, beamWake, descent, dominion, coronation);
               this.renderFallingHolyDust(matrix, centerX, beamBottom, centerZ, beamTop, time, beamWake, descent, coronation);
            }

            if (impact > 0.01F) {
               this.renderImpactBloomSphere(matrix, centerX, this.impactY + 2.0, centerZ, time, impact, dominion, coronation);
               this.renderImpactDomeVolume(matrix, centerX, this.impactY, centerZ, time, impact, dominion, coronation);
               this.renderPressureRingVolume(matrix, centerX, this.impactY + 0.2, centerZ, time, impact, dominion, coronation);
               this.renderImpactAccretionBands(matrix, centerX, this.impactY + 2.0, centerZ, time, impact, dominion, coronation);
               this.renderGroundEnergyCrawl(matrix, centerX, this.impactY + 0.25, centerZ, time, impact, dominion, coronation);
            }

            poseStack.popPose();
            if (impact > 0.01F) {
               this.renderDebrisBlocks(minecraft, poseStack, camera, centerX, this.impactY, centerZ, time, impact, dominion, coronation);
            }

            RenderSystem.depthMask(true);
            RenderSystem.enableCull();
            RenderSystem.disableBlend();
            RenderSystem.defaultBlendFunc();
         }
      }

      private void resolveBeamClip(Minecraft minecraft, double gameTime) {
         if (minecraft.level != null) {
            if (!(gameTime - this.clipCheckedAt < 3.0)) {
               this.clipCheckedAt = gameTime;
               Level level = minecraft.level;
               double skyStart = level.getMaxBuildHeight() - 3.0;
               double scanBottom = level.getMinBuildHeight() + 2.0;
               BlockHitResult hit = level.clip(
                  new ClipContext(
                     new Vec3(this.origin.x, skyStart, this.origin.z),
                     new Vec3(this.origin.x, scanBottom, this.origin.z),
                     Block.COLLIDER,
                     Fluid.NONE,
                     CollisionContext.empty()
                  )
               );
               if (hit.getType() == Type.BLOCK) {
                  this.impactY = hit.getLocation().y + 0.1;
                  this.portalY = Math.min(skyStart - 2.0, Math.max(this.impactY + 90.0, this.origin.y + 140.0));
               } else {
                  this.impactY = this.origin.y;
                  this.portalY = Math.min(skyStart - 2.0, this.origin.y + 176.0);
               }

               if (this.portalY < this.impactY + 50.0) {
                  this.portalY = this.impactY + 50.0;
               }
            }
         }
      }

      private void renderPortalCloudVolume(Matrix4f matrix, double x, double y, double z, double time, float clouds, float opening, float dominion) {
         if (!(clouds <= 0.01F)) {
            renderGlowSphere(matrix, x, y, z, 42.0F + clouds * 18.0F + dominion * 6.0F, 7.0F, 238, 232, 255, 0.045F * clouds, 10, 56, time * 0.012);
            renderGlowSphere(
               matrix, x, y - opening * 5.0, z, 34.0F + opening * 18.0F + dominion * 5.0F, 4.8F, 120, 42, 255, 0.06F * clouds * opening, 9, 54, -time * 0.018
            );
            renderFlatRingVolume(
               matrix, x, y + 1.0, z, 18.0 + opening * 8.0, 52.0 + opening * 22.0, 2.4, time * 0.032, 255, 246, 210, 0.135F * clouds * opening, 96
            );
            renderFlatRingVolume(matrix, x, y + 3.0, z, 28.0 + opening * 10.0, 72.0 + opening * 26.0, 2.0, -time * 0.022, 120, 42, 255, 0.075F * clouds, 96);
         }
      }

      private void renderPortalIrisVolume(
         Matrix4f matrix, double x, double y, double z, double time, float clouds, float opening, float beamWake, float dominion
      ) {
         if (!(clouds <= 0.01F)) {
            int blades = 24;
            BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
            float alpha = (0.11F + opening * 0.2F + beamWake * 0.16F + dominion * 0.08F) * clouds;

            for (int i = 0; i < blades; i++) {
               float seed = hash(this.owner, 8500 + i);
               double a0 = (float) (Math.PI * 2) * i / blades + time * 0.036;
               double a1 = a0 + 0.18 + Math.sin(time * 0.08 + i) * 0.08;
               double inner = 5.0 + opening * 4.0;
               double outer = 25.0 + opening * 21.0 + dominion * 8.0 + seed * 4.0;
               addIrisBlade(buffer, matrix, x, y + Math.sin(time * 0.04 + i) * 0.45, z, inner, outer, a0, a1, 255, 246, 210, alpha);
               addIrisBlade(buffer, matrix, x, y + 0.35, z, inner * 0.7, outer * 0.78, a0 + 0.08, a1 + 0.08, 118, 38, 255, alpha * 0.42F);
            }

            BufferUploader.drawWithShader(buffer.buildOrThrow());
         }
      }

      private void renderSmoothBeamVolume(
         Matrix4f matrix, double x, double bottom, double z, double height, double time, float beamWake, float descent, float dominion, float coronation
      ) {
         float pulse = 0.5F + 0.5F * Mth.sin((float)time * 0.22F);
         float radius = 10.0F + beamWake * 15.0F + descent * 15.5F + dominion * 7.0F + coronation * 8.5F + pulse * 2.0F;
         renderBeamCylinder(
            matrix,
            x,
            bottom,
            z,
            height,
            radius * 1.35F,
            255,
            248,
            218,
            0.05F + beamWake * 0.075F + descent * 0.09F + dominion * 0.04F,
            96,
            18,
            time * 0.01,
            0.035
         );
         renderBeamCylinder(
            matrix,
            x,
            bottom,
            z,
            height,
            radius * 0.92F,
            255,
            250,
            226,
            0.11F + beamWake * 0.095F + descent * 0.09F + coronation * 0.04F,
            88,
            16,
            -time * 0.018,
            0.055
         );
         renderBeamCylinder(
            matrix, x, bottom, z, height, radius * 0.55F, 118, 34, 255, 0.055F + descent * 0.065F + dominion * 0.04F, 76, 14, time * 0.026, 0.075
         );
      }

      private void renderBeamHotCore(
         Matrix4f matrix, double x, double bottom, double z, double height, double time, float beamWake, float descent, float coronation
      ) {
         float radius = 3.0F + beamWake * 4.0F + descent * 5.2F + coronation * 4.0F;
         renderBeamCylinder(matrix, x, bottom, z, height, radius, 255, 255, 255, 0.34F + descent * 0.28F + coronation * 0.14F, 72, 12, -time * 0.055, 0.025);
         renderBeamCylinder(matrix, x, bottom, z, height, radius * 0.46F, 255, 250, 210, 0.42F + descent * 0.28F, 64, 10, time * 0.084, 0.018);
      }

      private void renderBeamHeadOrb(
         Matrix4f matrix, double x, double y, double z, double time, float beamWake, float descent, float dominion, float coronation
      ) {
         float live = beamWake * descent;
         if (!(live <= 0.01F)) {
            float radius = 18.0F + live * 24.0F + dominion * 10.0F + coronation * 7.0F;
            renderGlowSphere(matrix, x, y + 2.0, z, radius, radius * 0.42F, 255, 250, 225, 0.23F * live + 0.12F * dominion, 9, 48, time * 0.05);
            renderGlowSphere(matrix, x, y + 2.0, z, radius * 0.62F, radius * 0.7F, 120, 34, 255, 0.1F * live + 0.07F * dominion, 8, 42, -time * 0.06);
         }
      }

      private void renderBeamAccretionRibbons(
         Matrix4f matrix, double x, double bottom, double z, double height, double time, float beamWake, float descent, float dominion, float coronation
      ) {
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         boolean drew = false;
         int layers = 8;

         for (int layer = 0; layer < layers; layer++) {
            float p = (float)layer / Math.max(1, layers - 1);
            double radius = 8.0 + p * 23.0 + descent * 8.0 + dominion * 4.0;
            float width = 0.9F + p * 1.2F + coronation * 0.75F;
            float alpha = (0.045F + descent * 0.05F + dominion * 0.02F) * beamWake * (1.0F - p * 0.48F);
            int segments = 36;
            double phase = layer * 1.31 + time * (0.022 + p * 0.026) * (layer % 2 == 0 ? 1.0 : -1.0);

            for (int i = 0; i < segments; i++) {
               double t0 = (double)i / segments;
               double t1 = (double)(i + 1) / segments;
               double y0 = bottom + height * t0;
               double y1 = bottom + height * t1;
               double twist0 = phase + t0 * (float) (Math.PI * 2) * (0.75 + coronation * 0.42);
               double twist1 = phase + t1 * (float) (Math.PI * 2) * (0.75 + coronation * 0.42);
               double wave0 = Math.sin(time * 0.11 + layer * 2.0 + i * 0.35) * (1.8 + dominion * 1.4);
               double wave1 = Math.sin(time * 0.11 + layer * 2.0 + (i + 1) * 0.35) * (1.8 + dominion * 1.4);
               drew |= addRibbonSegment(
                  buffer,
                  matrix,
                  x + Math.cos(twist0) * (radius + wave0),
                  y0,
                  z + Math.sin(twist0) * (radius + wave0),
                  x + Math.cos(twist1) * (radius + wave1),
                  y1,
                  z + Math.sin(twist1) * (radius + wave1),
                  width,
                  layer % 2 == 0 ? 255 : 122,
                  layer % 2 == 0 ? 238 : 42,
                  layer % 2 == 0 ? 190 : 255,
                  alpha
               );
            }
         }

         if (drew) {
            BufferUploader.drawWithShader(buffer.buildOrThrow());
         }
      }

      private void renderCleanLightningThreads(
         Matrix4f matrix, double x, double bottom, double z, double height, double time, float beamWake, float descent, float dominion, float coronation
      ) {
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         boolean drew = false;
         int bolts = 4;
         int segments = 7;
         float strength = beamWake * (0.22F + descent * 0.32F + dominion * 0.18F + coronation * 0.12F);

         for (int b = 0; b < bolts; b++) {
            float seed = hash(this.owner, 12000 + b);
            double base = seed * (float) (Math.PI * 2) + time * (0.026 + seed * 0.02) * (b % 2 == 0 ? 1.0 : -1.0);
            double radius = 13.0 + hash(this.owner, 12100 + b) * 10.0 + descent * 4.0;
            double phase = time * (0.7 + seed * 0.45) + b * 4.0;

            for (int s = 0; s < segments; s++) {
               double t0 = (double)s / segments;
               double t1 = (double)(s + 1) / segments;
               double jitter0 = Math.sin(phase + s * 1.4) * (1.0 + dominion);
               double jitter1 = Math.sin(phase + (s + 1) * 1.4) * (1.0 + dominion);
               double a0 = base + t0 * (float) (Math.PI * 2) * (1.05 + coronation * 0.45) + jitter0 * 0.02;
               double a1 = base + t1 * (float) (Math.PI * 2) * (1.05 + coronation * 0.45) + jitter1 * 0.02;
               double y0 = bottom + height * t0;
               double y1 = bottom + height * t1;
               float width = 0.16F + strength * 0.28F;
               drew |= addRibbonSegment(
                  buffer,
                  matrix,
                  x + Math.cos(a0) * (radius + jitter0),
                  y0,
                  z + Math.sin(a0) * (radius + jitter0),
                  x + Math.cos(a1) * (radius + jitter1),
                  y1,
                  z + Math.sin(a1) * (radius + jitter1),
                  width * 1.7F,
                  14,
                  0,
                  34,
                  0.16F * strength
               );
               drew |= addRibbonSegment(
                  buffer,
                  matrix,
                  x + Math.cos(a0) * (radius + jitter0),
                  y0,
                  z + Math.sin(a0) * (radius + jitter0),
                  x + Math.cos(a1) * (radius + jitter1),
                  y1,
                  z + Math.sin(a1) * (radius + jitter1),
                  width,
                  150,
                  44,
                  255,
                  0.36F * strength
               );
               drew |= addRibbonSegment(
                  buffer,
                  matrix,
                  x + Math.cos(a0) * (radius + jitter0),
                  y0,
                  z + Math.sin(a0) * (radius + jitter0),
                  x + Math.cos(a1) * (radius + jitter1),
                  y1,
                  z + Math.sin(a1) * (radius + jitter1),
                  width * 0.34F,
                  245,
                  225,
                  255,
                  0.34F * strength
               );
            }
         }

         if (drew) {
            BufferUploader.drawWithShader(buffer.buildOrThrow());
         }
      }

      private void renderFallingHolyDust(
         Matrix4f matrix, double x, double bottom, double z, double top, double time, float beamWake, float descent, float coronation
      ) {
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         int count = 95;
         double span = Math.max(16.0, top - bottom);

         for (int i = 0; i < count; i++) {
            float seed = hash(this.owner, 17000 + i);
            float seed2 = hash(this.owner, 18000 + i);
            double angle = seed * (float) (Math.PI * 2) + time * (0.0045 + seed2 * 0.009);
            double radius = 28.0 + seed2 * 100.0 + coronation * 22.0;
            double fall = (time * (1.75 + seed * 2.55) + i * 7.3) % span;
            double sx = x + Math.cos(angle) * radius;
            double sz = z + Math.sin(angle) * radius;
            double sy = top - fall;
            if (!(sy < bottom) && !(sy > top + 2.0)) {
               float length = 5.0F + seed * 13.0F + coronation * 10.0F;
               float width = 0.1F + seed2 * 0.18F;
               float alpha = (0.11F + seed * 0.16F + coronation * 0.07F) * beamWake * descent;
               addFallingStreak(buffer, matrix, sx, sy, sz, length, width, (float)angle, 255, 246, 194, alpha);
            }
         }

         BufferUploader.drawWithShader(buffer.buildOrThrow());
      }

      private void renderImpactBloomSphere(Matrix4f matrix, double x, double y, double z, double time, float impact, float dominion, float coronation) {
         float radius = 22.0F + impact * 42.0F + dominion * 24.0F + coronation * 16.0F;
         renderGlowSphere(matrix, x, y, z, radius, radius * 0.34F, 255, 250, 225, 0.26F * impact + 0.12F * dominion, 10, 64, time * 0.05);
         renderGlowSphere(matrix, x, y + 0.5, z, radius * 0.7F, radius * 0.64F, 124, 38, 255, 0.12F * impact + 0.08F * coronation, 9, 56, -time * 0.06);
      }

      private void renderImpactDomeVolume(Matrix4f matrix, double x, double y, double z, double time, float impact, float dominion, float coronation) {
         int rings = 13;
         int slices = 72;
         double domeRadius = 14.0 + impact * 58.0 + dominion * 38.0 + coronation * 28.0;
         double domeHeight = 8.0 + impact * 34.0 + dominion * 24.0 + coronation * 14.0;
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);

         for (int r = 0; r < rings; r++) {
            double v0 = (double)r / rings;
            double v1 = (double)(r + 1) / rings;
            double theta0 = v0 * (float) (Math.PI / 2);
            double theta1 = v1 * (float) (Math.PI / 2);
            double radius0 = Math.sin(theta0) * domeRadius;
            double radius1 = Math.sin(theta1) * domeRadius;
            double y0 = y + Math.cos(theta0) * domeHeight;
            double y1 = y + Math.cos(theta1) * domeHeight;
            float alpha = (0.09F + impact * 0.165F + dominion * 0.075F + coronation * 0.05F) * (float)(1.0 - v0 * 0.62);

            for (int s = 0; s < slices; s++) {
               double wave = Math.sin(time * 0.07 + r * 0.55 + s * 0.2) * 0.045;
               double a0 = (float) (Math.PI * 2) * s / slices + time * 0.04 + wave;
               double a1 = (float) (Math.PI * 2) * (s + 1) / slices + time * 0.04 + wave;
               int red = r % 2 == 0 ? 255 : 124;
               int green = r % 2 == 0 ? 242 : 42;
               int blue = r % 2 == 0 ? 210 : 255;
               addShellQuad(buffer, matrix, x, z, y0, y1, radius0, radius1, a0, a1, red, green, blue, alpha);
            }
         }

         BufferUploader.drawWithShader(buffer.buildOrThrow());
      }

      private void renderPressureRingVolume(Matrix4f matrix, double x, double y, double z, double time, float impact, float dominion, float coronation) {
         for (int layer = 0; layer < 4; layer++) {
            double inner = 18.0 + impact * 48.0 + dominion * 30.0 + layer * 8.0;
            double outer = inner + 10.0 + coronation * 6.0;
            float alpha = (0.085F + impact * 0.13F + dominion * 0.055F) * (1.0F - layer * 0.18F);
            renderFlatRingVolume(
               matrix,
               x,
               y + layer * 0.55,
               z,
               inner,
               outer,
               4.0 + impact * 10.0 - layer,
               time * (0.034 + layer * 0.005),
               layer % 2 == 0 ? 255 : 122,
               layer % 2 == 0 ? 238 : 42,
               layer % 2 == 0 ? 190 : 255,
               alpha,
               108
            );
         }
      }

      private void renderImpactAccretionBands(Matrix4f matrix, double x, double y, double z, double time, float impact, float dominion, float coronation) {
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         boolean drew = false;

         for (int band = 0; band < 9; band++) {
            int segments = 82;
            double shell = 18.0 + impact * 58.0 + dominion * 30.0 + coronation * 20.0;
            double radius = shell * (0.2 + band * 0.092);
            double bandY = y + 2.5 + Math.sin(band * 0.65 + impact * 2.1) * (9.0 + dominion * 6.0) + band * 1.85;
            double spin = time * (0.074 + band * 0.006) * (band % 2 == 0 ? 1.0 : -1.0);
            float width = 0.52F + impact * 0.9F + coronation * 0.44F;
            float alpha = (0.095F + impact * 0.12F + coronation * 0.055F) * (1.0F - band * 0.085F);

            for (int i = 0; i < segments; i++) {
               double a0 = (float) (Math.PI * 2) * i / segments + spin;
               double a1 = (float) (Math.PI * 2) * (i + 1) / segments + spin;
               drew |= addRingSegment(
                  buffer, matrix, x, bandY, z, radius, width, a0, a1, band % 2 == 0 ? 255 : 128, band % 2 == 0 ? 238 : 45, band % 2 == 0 ? 190 : 255, alpha
               );
            }
         }

         if (drew) {
            BufferUploader.drawWithShader(buffer.buildOrThrow());
         }
      }

      private void renderGroundEnergyCrawl(Matrix4f matrix, double x, double y, double z, double time, float impact, float dominion, float coronation) {
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         boolean drew = false;
         int bolts = 7;

         for (int b = 0; b < bolts; b++) {
            float seed = hash(this.owner, 23000 + b);
            double angle = seed * (float) (Math.PI * 2) + time * 0.01;
            double length = 32.0 + seed * 72.0 + impact * 30.0 + dominion * 22.0;
            int segments = 5;
            double lastX = x + Math.cos(angle) * 9.0;
            double lastZ = z + Math.sin(angle) * 9.0;
            double lastY = y + 0.45;

            for (int s = 1; s <= segments; s++) {
               double t = (double)s / segments;
               double jitter = Math.sin(time * 0.9 + b * 3.0 + s * 2.0) * (1.1 + t * 4.0);
               double local = angle + jitter * 0.022;
               double nextX = x + Math.cos(local) * length * t;
               double nextZ = z + Math.sin(local) * length * t;
               double nextY = y + 0.55 + Math.sin(time * 0.08 + s + b) * 0.45 + t * 0.55;
               drew |= addRibbonSegment(buffer, matrix, lastX, lastY, lastZ, nextX, nextY, nextZ, 0.36F + impact * 0.28F, 18, 0, 38, 0.18F * impact);
               drew |= addRibbonSegment(buffer, matrix, lastX, lastY, lastZ, nextX, nextY, nextZ, 0.15F + impact * 0.15F, 145, 44, 255, 0.35F * impact);
               drew |= addRibbonSegment(buffer, matrix, lastX, lastY, lastZ, nextX, nextY, nextZ, 0.06F + impact * 0.07F, 255, 240, 255, 0.26F * impact);
               lastX = nextX;
               lastY = nextY;
               lastZ = nextZ;
            }
         }

         if (drew) {
            BufferUploader.drawWithShader(buffer.buildOrThrow());
         }
      }

      private void renderDebrisBlocks(
         Minecraft minecraft, PoseStack poseStack, Vec3 camera, double x, double y, double z, double time, float impact, float dominion, float coronation
      ) {
         BufferSource buffer = minecraft.renderBuffers().bufferSource();
         int active = Math.min(220, 70 + (int)(impact * 150.0F));

         for (int i = 0; i < active; i++) {
            float seed = this.debrisSeeds[i];
            float seed2 = hash(this.owner, 50000 + i);
            double angle = seed + i * 0.21 + dominion * 0.25;
            double blast = impact * impact;
            double dist = (10.0 + seed2 * 84.0) * blast + dominion * (12.0 + seed2 * 34.0);
            double height = Math.sin(blast * Math.PI) * (12.0 + seed2 * 52.0) + coronation * (8.0 + seed2 * 22.0);
            double sx = x + Math.cos(angle) * dist;
            double sz = z + Math.sin(angle) * dist;
            double sy = y + 0.8 + height - impact * impact * seed2 * 14.0;
            float spin = (float)(time * (0.08 + seed2 * 0.13));
            float scale = this.debrisSizes[i] * (0.32F + impact * 0.94F);
            renderBlock(
               minecraft, poseStack, buffer, camera, this.debrisStates[i], sx, sy, sz, seed + spin, seed * 0.7F + spin * 0.8F, seed * 1.3F - spin, scale
            );
         }

         buffer.endBatch();
      }

      private static void renderBeamCylinder(
         Matrix4f matrix,
         double x,
         double bottom,
         double z,
         double height,
         float radius,
         int red,
         int green,
         int blue,
         float alpha,
         int slices,
         int stacks,
         double rotation,
         double twist
      ) {
         if (!(alpha <= 5.0E-4F)) {
            BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);

            for (int stack = 0; stack < stacks; stack++) {
               double t0 = (double)stack / stacks;
               double t1 = (double)(stack + 1) / stacks;
               double y0 = bottom + height * t0;
               double y1 = bottom + height * t1;
               double r0 = radius * (0.92 + Math.sin(t0 * Math.PI) * 0.08);
               double r1 = radius * (0.92 + Math.sin(t1 * Math.PI) * 0.08);

               for (int slice = 0; slice < slices; slice++) {
                  double u0 = (double)slice / slices;
                  double u1 = (double)(slice + 1) / slices;
                  double a0 = u0 * (float) (Math.PI * 2) + rotation + t0 * (float) (Math.PI * 2) * twist;
                  double a1 = u1 * (float) (Math.PI * 2) + rotation + t1 * (float) (Math.PI * 2) * twist;
                  addBeamCylinderVertex(buffer, matrix, x, z, y0, r0, a0, red, green, blue, alpha, u0, t0);
                  addBeamCylinderVertex(buffer, matrix, x, z, y1, r1, a0, red, green, blue, alpha, u0, t1);
                  addBeamCylinderVertex(buffer, matrix, x, z, y1, r1, a1, red, green, blue, alpha, u1, t1);
                  addBeamCylinderVertex(buffer, matrix, x, z, y0, r0, a1, red, green, blue, alpha, u1, t0);
               }
            }

            BufferUploader.drawWithShader(buffer.buildOrThrow());
         }
      }

      private static void addBeamCylinderVertex(
         BufferBuilder buffer,
         Matrix4f matrix,
         double cx,
         double cz,
         double y,
         double radius,
         double angle,
         int red,
         int green,
         int blue,
         float alpha,
         double u,
         double t
      ) {
         double wave = Math.sin(u * (float) (Math.PI * 2) * 9.0 + t * 8.0 + angle * 1.7) * 0.5 + 0.5;
         double rim = 0.58 + 0.42 * wave;
         float a = (float)(alpha * rim * (0.76 + Math.sin(t * Math.PI) * 0.24));
         float x = (float)(cx + Math.cos(angle) * radius);
         float z = (float)(cz + Math.sin(angle) * radius);
         buffer.addVertex(matrix, x, (float)y, z).setColor(red, green, blue, alpha(a));
      }

      private static void renderGlowSphere(
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
         double rotation
      ) {
         if (!(alpha <= 5.0E-4F)) {
            BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);

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
                  addGlowSphereVertex(buffer, matrix, x, y, z, radius, height, theta0, phi0, red, green, blue, alpha, rotation);
                  addGlowSphereVertex(buffer, matrix, x, y, z, radius, height, theta0, phi1, red, green, blue, alpha, rotation);
                  addGlowSphereVertex(buffer, matrix, x, y, z, radius, height, theta1, phi1, red, green, blue, alpha, rotation);
                  addGlowSphereVertex(buffer, matrix, x, y, z, radius, height, theta1, phi0, red, green, blue, alpha, rotation);
               }
            }

            BufferUploader.drawWithShader(buffer.buildOrThrow());
         }
      }

      private static void addGlowSphereVertex(
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
         double rotation
      ) {
         float px = (float)(Math.cos(phi) * Math.cos(theta)) * radius;
         float py = (float)Math.sin(phi) * height;
         float pz = (float)(Math.cos(phi) * Math.sin(theta)) * radius;
         float rim = Mth.clamp((float)Math.sqrt(px * px + pz * pz) / Math.max(0.001F, radius), 0.0F, 1.0F);
         float wave = 0.62F + 0.38F * (float)Math.sin(theta * 9.0 + phi * 5.0 + rotation * 8.0);
         float a = alpha * rim * rim * wave;
         buffer.addVertex(matrix, (float)cx + px, (float)cy + py, (float)cz + pz).setColor(red, green, blue, alpha(a));
      }

      private static void renderFlatRingVolume(
         Matrix4f matrix,
         double x,
         double y,
         double z,
         double inner,
         double outer,
         double height,
         double rotation,
         int red,
         int green,
         int blue,
         float alpha,
         int slices
      ) {
         if (!(alpha <= 5.0E-4F)) {
            BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);

            for (int i = 0; i < slices; i++) {
               double u0 = (double)i / slices;
               double u1 = (double)(i + 1) / slices;
               double a0 = u0 * (float) (Math.PI * 2) + rotation;
               double a1 = u1 * (float) (Math.PI * 2) + rotation;
               double wave0 = Math.sin(a0 * 5.0 + rotation * 3.0) * height;
               double wave1 = Math.sin(a1 * 5.0 + rotation * 3.0) * height;
               addRingVolumeVertex(buffer, matrix, x, y + wave0, z, inner, a0, red, green, blue, alpha);
               addRingVolumeVertex(buffer, matrix, x, y + wave1, z, inner, a1, red, green, blue, alpha);
               addRingVolumeVertex(buffer, matrix, x, y + wave1, z, outer, a1, red, green, blue, alpha * 0.16F);
               addRingVolumeVertex(buffer, matrix, x, y + wave0, z, outer, a0, red, green, blue, alpha * 0.16F);
            }

            BufferUploader.drawWithShader(buffer.buildOrThrow());
         }
      }

      private static void addRingVolumeVertex(
         BufferBuilder buffer, Matrix4f matrix, double x, double y, double z, double radius, double angle, int red, int green, int blue, float alpha
      ) {
         buffer.addVertex(matrix, (float)(x + Math.cos(angle) * radius), (float)y, (float)(z + Math.sin(angle) * radius))
            .setColor(red, green, blue, alpha(alpha));
      }

      private static boolean addRibbonSegment(
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
         if (alpha <= 5.0E-4F) {
            return false;
         }

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
      }

      private static void addFallingStreak(
         BufferBuilder buffer, Matrix4f matrix, double x, double y, double z, float length, float width, float angle, int red, int green, int blue, float alpha
      ) {
         double ox = Math.cos(angle) * width;
         double oz = Math.sin(angle) * width;
         int a = alpha(alpha);
         buffer.addVertex(matrix, (float)(x - ox), (float)(y - length), (float)(z - oz)).setColor(red, green, blue, 0);
         buffer.addVertex(matrix, (float)(x + ox), (float)(y - length), (float)(z + oz)).setColor(red, green, blue, 0);
         buffer.addVertex(matrix, (float)(x + ox), (float)y, (float)(z + oz)).setColor(red, green, blue, a);
         buffer.addVertex(matrix, (float)(x - ox), (float)y, (float)(z - oz)).setColor(red, green, blue, a);
      }

      private static boolean addRingSegment(
         BufferBuilder buffer,
         Matrix4f matrix,
         double x,
         double y,
         double z,
         double radius,
         float width,
         double a0,
         double a1,
         int red,
         int green,
         int blue,
         float alpha
      ) {
         if (alpha <= 5.0E-4F) {
            return false;
         }

         double inner = radius - width;
         double outer = radius + width;
         int a = alpha(alpha);
         buffer.addVertex(matrix, (float)(x + Math.cos(a0) * inner), (float)y, (float)(z + Math.sin(a0) * inner)).setColor(red, green, blue, 0);
         buffer.addVertex(matrix, (float)(x + Math.cos(a0) * outer), (float)y, (float)(z + Math.sin(a0) * outer)).setColor(red, green, blue, a);
         buffer.addVertex(matrix, (float)(x + Math.cos(a1) * outer), (float)y, (float)(z + Math.sin(a1) * outer)).setColor(red, green, blue, a);
         buffer.addVertex(matrix, (float)(x + Math.cos(a1) * inner), (float)y, (float)(z + Math.sin(a1) * inner)).setColor(red, green, blue, 0);
         return true;
      }

      private static void addShellQuad(
         BufferBuilder buffer,
         Matrix4f matrix,
         double x,
         double z,
         double y0,
         double y1,
         double r0,
         double r1,
         double a0,
         double a1,
         int red,
         int green,
         int blue,
         float alpha
      ) {
         int a = alpha(alpha);
         buffer.addVertex(matrix, (float)(x + Math.cos(a0) * r0), (float)y0, (float)(z + Math.sin(a0) * r0)).setColor(red, green, blue, 0);
         buffer.addVertex(matrix, (float)(x + Math.cos(a1) * r0), (float)y0, (float)(z + Math.sin(a1) * r0)).setColor(red, green, blue, a);
         buffer.addVertex(matrix, (float)(x + Math.cos(a1) * r1), (float)y1, (float)(z + Math.sin(a1) * r1)).setColor(red, green, blue, a);
         buffer.addVertex(matrix, (float)(x + Math.cos(a0) * r1), (float)y1, (float)(z + Math.sin(a0) * r1)).setColor(red, green, blue, 0);
      }

      private static void addIrisBlade(
         BufferBuilder buffer,
         Matrix4f matrix,
         double x,
         double y,
         double z,
         double inner,
         double outer,
         double a0,
         double a1,
         int red,
         int green,
         int blue,
         float alpha
      ) {
         int a = alpha(alpha);
         buffer.addVertex(matrix, (float)(x + Math.cos(a0) * inner), (float)y, (float)(z + Math.sin(a0) * inner)).setColor(red, green, blue, 0);
         buffer.addVertex(matrix, (float)(x + Math.cos(a1) * inner), (float)y, (float)(z + Math.sin(a1) * inner)).setColor(red, green, blue, a);
         buffer.addVertex(matrix, (float)(x + Math.cos(a1) * outer), (float)y, (float)(z + Math.sin(a1) * outer)).setColor(red, green, blue, a);
         buffer.addVertex(matrix, (float)(x + Math.cos(a0) * outer), (float)y, (float)(z + Math.sin(a0) * outer)).setColor(red, green, blue, 0);
      }

      private static void renderBlock(
         Minecraft minecraft,
         PoseStack poseStack,
         BufferSource buffer,
         Vec3 camera,
         BlockState state,
         double x,
         double y,
         double z,
         float yaw,
         float pitch,
         float roll,
         float scale
      ) {
         poseStack.pushPose();
         poseStack.translate(x - camera.x, y - camera.y, z - camera.z);
         poseStack.mulPose(new Quaternionf().rotateY(yaw).rotateX(pitch).rotateZ(roll));
         poseStack.scale(scale, scale, scale);
         poseStack.translate(-0.5, -0.5, -0.5);
         minecraft.getBlockRenderer().renderSingleBlock(state, poseStack, buffer, 15728880, OverlayTexture.NO_OVERLAY);
         poseStack.popPose();
      }

      private static BlockState pickDebrisState(int i) {
         return switch (i % 12) {
            case 0, 1 -> Blocks.OBSIDIAN.defaultBlockState();
            case 2, 3 -> Blocks.CRYING_OBSIDIAN.defaultBlockState();
            case 4 -> Blocks.BLACKSTONE.defaultBlockState();
            case 5 -> Blocks.DEEPSLATE.defaultBlockState();
            case 6 -> Blocks.COBBLED_DEEPSLATE.defaultBlockState();
            case 7 -> Blocks.SMOOTH_BASALT.defaultBlockState();
            case 8 -> Blocks.TUFF.defaultBlockState();
            case 9 -> Blocks.END_STONE.defaultBlockState();
            case 10 -> Blocks.TINTED_GLASS.defaultBlockState();
            default -> Blocks.AMETHYST_BLOCK.defaultBlockState();
         };
      }

      private static int alpha(float alpha) {
         return Mth.clamp((int)(alpha * 255.0F), 0, 255);
      }

      private static float smooth(float value) {
         return value * value * (3.0F - 2.0F * value);
      }

      private static float pow3(float value) {
         float inverse = 1.0F - value;
         return 1.0F - inverse * inverse * inverse;
      }

      private static float hash(UUID owner, int index) {
         long value = owner.getMostSignificantBits() ^ owner.getLeastSignificantBits() ^ index * 341873128712L;
         value ^= value >>> 33;
         value *= -49064778989728563L;
         value ^= value >>> 33;
         value *= -4265267296055464877L;
         value ^= value >>> 33;
         return (float)(value & 16777215L) / 1.6777215E7F;
      }
   }
}
