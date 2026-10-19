package com.github.b4ndithelps.tennogamenolife.client.renderer;

import com.github.b4ndithelps.tennogamenolife.client.model.DragonRoarCircleModel;
import com.github.b4ndithelps.tennogamenolife.entity.DragonRoarCircle;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.util.Color;

public class DragonRoarCircleRenderer extends GeoEntityRenderer<DragonRoarCircle> {
   private static final float GODLY_CHARGE_CIRCLE_SCALE = 2.5F;
   private static final double GODLY_CHARGE_CIRCLE_HEIGHT = 2.5;
   private static final double GODLY_CHARGE_TEXTURE_HALF_SIZE = 3.75;
   private static final int BEAM_COUNT = 20;
   private static final int EXPANSION_RAY_COUNT = 36;
   private static final int GODLY_CHARGE_RAY_COUNT = 24;
   private static final int GODLY_FIRE_TONGUE_COUNT = 36;
   private static final int GODLY_TRAIL_COUNT = 20;
   private static final int GODLY_IMPACT_RAY_COUNT = 30;
   private static final int GODLY_FIRE_PARTICLE_COUNT = 120;
   private static final int GODLY_EMBER_COUNT = 96;
   private static final int GODLY_CONVERGENCE_BEAMS = 24;
   private static final int GODLY_WING_PAIRS = 8;
   private static final int RING_SEGMENTS = 64;
   private static final int SPHERE_LONGITUDE_SEGMENTS = 16;
   private static final int SPHERE_LATITUDE_SEGMENTS = 8;
   private static final int FIREBALL_LONGITUDE_SEGMENTS = 48;
   private static final int FIREBALL_LATITUDE_SEGMENTS = 24;
   private static final float[] UNIT_SPHERE_VERTICES = buildUnitSphereVertices();
   private static final float[] UNIT_HEMISPHERE_VERTICES = buildUnitHemisphereVertices();
   private static final float[] TEXTURED_FIREBALL_VERTICES = buildTexturedFireballVertices();
   private static final double GOLDEN_ANGLE = 2.399963229728653;

   public DragonRoarCircleRenderer(Context renderManager) {
      super(renderManager, new DragonRoarCircleModel());
   }

   public void render(DragonRoarCircle animatable, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
      if (animatable.isGodlySmiteChargeVfx()) {
         renderGodlySmiteChargeGlow(animatable, partialTick, poseStack, bufferSource);
         if (bufferSource instanceof BufferSource source) {
            source.endBatch(TenreincarnationRenderTypes.GODLY_SMITE_CHARGE_GLOW);
         }

         super.render(animatable, entityYaw, partialTick, poseStack, bufferSource, 15728880);
      } else if (animatable.isGodlySmiteStrikeVfx()) {
         renderGodlySmiteStrike(animatable, partialTick, poseStack, bufferSource);
      } else if (animatable.isBarrierSphereVfx()) {
         renderBarrierSphere(animatable, partialTick, poseStack, bufferSource);
      } else if (animatable.isExpansionVfx()) {
         renderExplosionExpansion(animatable, partialTick, poseStack, bufferSource);
      } else if (animatable.isStrikeVfx()) {
         renderStrikeBeams(animatable, partialTick, poseStack, bufferSource);
      } else {
         super.render(animatable, entityYaw, partialTick, poseStack, bufferSource, packedLight);
      }
   }

   private static void renderGodlySmiteChargeGlow(DragonRoarCircle entity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource) {
      if (entity.getLife() > 0) {
         float age = Math.max(0.0F, getVfxAge(entity, partialTick));
         float appear = smoothStep(0.0F, 8.0F, age);
         float disappear = smoothStep(0.0F, 8.0F, entity.getLife() + partialTick);
         float pulse = 0.88F + 0.12F * (float)Math.sin(age * 0.18F);
         float intensity = appear * disappear * pulse;
         if (!(intensity <= 0.001F)) {
            poseStack.pushPose();
            float interpolatedYaw = Mth.rotLerp(partialTick, entity.yRotO, entity.getYRot());
            poseStack.translate(0.0, 2.475, 0.0);
            poseStack.mulPose(Axis.YP.rotationDegrees(-interpolatedYaw));
            Matrix4f matrix = poseStack.last().pose();
            VertexConsumer glowConsumer = bufferSource.getBuffer(TenreincarnationRenderTypes.GODLY_SMITE_CHARGE_GLOW);
            drawTexturedHorizontalQuad(matrix, glowConsumer, 3.75, 0.0, 0.0, 1.0F, 1.0F, 1.0F, 0.8F * intensity);
            int copies = 12;

            for (int band = 0; band < 3; band++) {
               double pulseOffset = Math.sin(age * 0.14 + band * 0.8) * 0.008;
               double offsetRadius = 0.075 + band * 0.085 + pulseOffset;
               float alpha = (0.055F - band * 0.014F) * intensity;

               for (int copy = 0; copy < copies; copy++) {
                  double angle = (Math.PI * 2) * copy / copies;
                  drawTexturedHorizontalQuad(
                     matrix, glowConsumer, 3.75, Math.cos(angle) * offsetRadius, Math.sin(angle) * offsetRadius, 0.46F, 0.06F, 1.0F, alpha
                  );
               }
            }

            poseStack.popPose();
         }
      }
   }

   private static void renderBarrierSphere(DragonRoarCircle entity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource) {
      if (entity.getLife() > 0) {
         float age = getVfxAge(entity, partialTick);
         float appearScale = smoothStep(0.0F, 6.0F, age);
         float disappearScale = smoothStep(0.0F, 6.0F, entity.getLife() + partialTick);
         float pulse = 1.0F + 0.008F * (float)Math.sin(age * 0.18F);
         float radius = entity.getSize() * Math.min(appearScale, disappearScale) * pulse;
         if (!(radius <= 0.001F)) {
            VertexConsumer consumer = bufferSource.getBuffer(TenreincarnationRenderTypes.BARRIER_SPHERE);
            Matrix4f matrix = poseStack.last().pose();

            for (int index = 0; index < UNIT_SPHERE_VERTICES.length; index += 3) {
               consumer.addVertex(
                     matrix, UNIT_SPHERE_VERTICES[index] * radius, UNIT_SPHERE_VERTICES[index + 1] * radius, UNIT_SPHERE_VERTICES[index + 2] * radius
                  )
                  .setColor(0.35F, 0.78F, 1.0F, 0.14F);
            }
         }
      }
   }

   private static float[] buildUnitSphereVertices() {
      float[] vertices = new float[2304];
      int index = 0;

      for (int latitude = 0; latitude < 8; latitude++) {
         double phi0 = (-Math.PI / 2) + Math.PI * latitude / 8.0;
         double phi1 = (-Math.PI / 2) + Math.PI * (latitude + 1) / 8.0;
         float y0 = (float)Math.sin(phi0);
         float y1 = (float)Math.sin(phi1);
         float ring0 = (float)Math.cos(phi0);
         float ring1 = (float)Math.cos(phi1);

         for (int longitude = 0; longitude < 16; longitude++) {
            double theta0 = (Math.PI * 2) * longitude / 16.0;
            double theta1 = (Math.PI * 2) * (longitude + 1) / 16.0;
            float x00 = (float)Math.cos(theta0) * ring0;
            float z00 = (float)Math.sin(theta0) * ring0;
            float x01 = (float)Math.cos(theta1) * ring0;
            float z01 = (float)Math.sin(theta1) * ring0;
            float x10 = (float)Math.cos(theta0) * ring1;
            float z10 = (float)Math.sin(theta0) * ring1;
            float x11 = (float)Math.cos(theta1) * ring1;
            float z11 = (float)Math.sin(theta1) * ring1;
            index = writeTriangle(vertices, index, x00, y0, z00, x10, y1, z10, x11, y1, z11);
            index = writeTriangle(vertices, index, x00, y0, z00, x11, y1, z11, x01, y0, z01);
         }
      }

      return vertices;
   }

   private static float[] buildUnitHemisphereVertices() {
      float[] vertices = new float[2304];
      int index = 0;

      for (int latitude = 0; latitude < 8; latitude++) {
         double phi0 = (Math.PI / 2) * latitude / 8.0;
         double phi1 = (Math.PI / 2) * (latitude + 1) / 8.0;
         float y0 = (float)Math.sin(phi0);
         float y1 = (float)Math.sin(phi1);
         float ring0 = (float)Math.cos(phi0);
         float ring1 = (float)Math.cos(phi1);

         for (int longitude = 0; longitude < 16; longitude++) {
            double theta0 = (Math.PI * 2) * longitude / 16.0;
            double theta1 = (Math.PI * 2) * (longitude + 1) / 16.0;
            float x00 = (float)Math.cos(theta0) * ring0;
            float z00 = (float)Math.sin(theta0) * ring0;
            float x01 = (float)Math.cos(theta1) * ring0;
            float z01 = (float)Math.sin(theta1) * ring0;
            float x10 = (float)Math.cos(theta0) * ring1;
            float z10 = (float)Math.sin(theta0) * ring1;
            float x11 = (float)Math.cos(theta1) * ring1;
            float z11 = (float)Math.sin(theta1) * ring1;
            index = writeTriangle(vertices, index, x00, y0, z00, x10, y1, z10, x11, y1, z11);
            index = writeTriangle(vertices, index, x00, y0, z00, x11, y1, z11, x01, y0, z01);
         }
      }

      return vertices;
   }

   private static float[] buildTexturedFireballVertices() {
      float[] vertices = new float[34560];
      int index = 0;

      for (int latitude = 0; latitude < 24; latitude++) {
         double phi0 = (-Math.PI / 2) + Math.PI * latitude / 24.0;
         double phi1 = (-Math.PI / 2) + Math.PI * (latitude + 1) / 24.0;
         float y0 = (float)Math.sin(phi0);
         float y1 = (float)Math.sin(phi1);
         float ring0 = (float)Math.cos(phi0);
         float ring1 = (float)Math.cos(phi1);
         float v0 = 1.0F - latitude / 24.0F;
         float v1 = 1.0F - (latitude + 1) / 24.0F;

         for (int longitude = 0; longitude < 48; longitude++) {
            double theta0 = (Math.PI * 2) * longitude / 48.0;
            double theta1 = (Math.PI * 2) * (longitude + 1) / 48.0;
            float u0 = longitude / 48.0F;
            float u1 = (longitude + 1) / 48.0F;
            float x00 = (float)Math.cos(theta0) * ring0;
            float z00 = (float)Math.sin(theta0) * ring0;
            float x01 = (float)Math.cos(theta1) * ring0;
            float z01 = (float)Math.sin(theta1) * ring0;
            float x10 = (float)Math.cos(theta0) * ring1;
            float z10 = (float)Math.sin(theta0) * ring1;
            float x11 = (float)Math.cos(theta1) * ring1;
            float z11 = (float)Math.sin(theta1) * ring1;
            index = writeTexturedVertex(vertices, index, x00, y0, z00, u0, v0);
            index = writeTexturedVertex(vertices, index, x10, y1, z10, u0, v1);
            index = writeTexturedVertex(vertices, index, x11, y1, z11, u1, v1);
            index = writeTexturedVertex(vertices, index, x00, y0, z00, u0, v0);
            index = writeTexturedVertex(vertices, index, x11, y1, z11, u1, v1);
            index = writeTexturedVertex(vertices, index, x01, y0, z01, u1, v0);
         }
      }

      return vertices;
   }

   private static int writeTexturedVertex(float[] vertices, int index, float x, float y, float z, float u, float v) {
      vertices[index++] = x;
      vertices[index++] = y;
      vertices[index++] = z;
      vertices[index++] = u;
      vertices[index++] = v;
      return index;
   }

   private static int writeTriangle(float[] vertices, int index, float x0, float y0, float z0, float x1, float y1, float z1, float x2, float y2, float z2) {
      vertices[index++] = x0;
      vertices[index++] = y0;
      vertices[index++] = z0;
      vertices[index++] = x1;
      vertices[index++] = y1;
      vertices[index++] = z1;
      vertices[index++] = x2;
      vertices[index++] = y2;
      vertices[index++] = z2;
      return index;
   }

   private static void renderStrikeBeams(DragonRoarCircle entity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource) {
      long startGameTime = entity.getVfxStartGameTime();
      float age = startGameTime > 0L ? (float)entity.level().getGameTime() + partialTick - (float)startGameTime : entity.getVisualAge() + partialTick;
      int strikeDuration = entity.getVfxDuration();
      if (entity.getLife() > 0 && !(age < 0.0F) && !(age >= strikeDuration)) {
         VertexConsumer consumer = bufferSource.getBuffer(TenreincarnationRenderTypes.STRIKE_BEAM);
         Matrix4f matrix = poseStack.last().pose();
         int secondPhaseDuration = entity.getVfxSecondPhaseDuration();
         float secondPhaseStart = strikeDuration - secondPhaseDuration;
         if (secondPhaseDuration > 0 && age >= secondPhaseStart) {
            float phaseAge = age - secondPhaseStart;
            float transition = clamp01(phaseAge / 8.0F);
            if (transition < 1.0F) {
               renderConeStage(entity, age, secondPhaseStart, matrix, consumer, 1.0F - transition);
            }

            renderScatteredStrikeBeams(entity, phaseAge, secondPhaseDuration, matrix, consumer);
         } else {
            float coneStageEnd = secondPhaseDuration > 0 ? secondPhaseStart : strikeDuration;
            renderConeStage(entity, age, coneStageEnd, matrix, consumer, 1.0F);
         }
      }
   }

   private static void renderConeStage(DragonRoarCircle entity, float age, float stageEnd, Matrix4f matrix, VertexConsumer consumer, float alphaScale) {
      RandomSource random = RandomSource.create((long)entity.getVfxSeed() << 32 ^ 25214903917L);
      Vec3 tip = new Vec3(0.0, 0.02, 0.0);
      double rotationOffset = random.nextDouble() * Math.PI * 2.0;
      double seedPhase = random.nextDouble() * Math.PI * 2.0;
      float descentProgress = easeOutCubic(clamp01(age / 20.0F));
      float pulse = 0.92F + 0.08F * (float)Math.sin(age * 0.9F);
      float finalFlash = age >= stageEnd - 4.0F ? 1.35F : 1.0F;

      for (int i = 0; i < 20; i++) {
         double angle = (Math.PI * 2) * i / 20.0 + rotationOffset;
         double contour = 1.0 + 0.045 * Math.sin(i * 2.399963229728653 + seedPhase);
         double radius = 100.0 * contour;
         Vec3 start = new Vec3(Math.cos(angle) * radius, entity.getVfxStartHeight(), Math.sin(angle) * radius);
         Vec3 head = new Vec3(lerp(start.x, tip.x, descentProgress), lerp(start.y, tip.y, descentProgress), lerp(start.z, tip.z, descentProgress));
         drawCrossBeam(matrix, consumer, start, head, 0.42 * finalFlash, 0.72F, 0.9F, 1.0F, 0.13F * pulse * alphaScale);
         drawCrossBeam(matrix, consumer, start, head, 0.085 * finalFlash, 1.0F, 1.0F, 1.0F, 0.92F * pulse * alphaScale);
      }
   }

   private static void renderScatteredStrikeBeams(DragonRoarCircle entity, float phaseAge, int phaseDuration, Matrix4f matrix, VertexConsumer consumer) {
      RandomSource originalRandom = RandomSource.create((long)entity.getVfxSeed() << 32 ^ 25214903917L);
      double rotationOffset = originalRandom.nextDouble() * Math.PI * 2.0;
      double seedPhase = originalRandom.nextDouble() * Math.PI * 2.0;
      RandomSource random = RandomSource.create((long)entity.getVfxSeed() << 32 ^ -3335678366873096957L);
      Vec3 center = new Vec3(0.0, 0.02, 0.0);
      float spread = easeOutCubic(clamp01(phaseAge / 36.0F));
      float fade = 1.0F - smoothStep(phaseDuration - 10.0F, phaseDuration, phaseAge);

      for (int i = 0; i < 20; i++) {
         double originalAngle = (Math.PI * 2) * i / 20.0 + rotationOffset;
         double contour = 1.0 + 0.045 * Math.sin(i * 2.399963229728653 + seedPhase);
         Vec3 originalOuter = new Vec3(Math.cos(originalAngle) * 100.0 * contour, entity.getVfxStartHeight(), Math.sin(originalAngle) * 100.0 * contour);
         Vec3 originalVector = originalOuter.subtract(center);
         Vec3 originalDirection = originalVector.normalize();
         double originalLength = originalVector.length();
         double angle = random.nextDouble() * Math.PI * 2.0;
         double y = random.nextDouble() * 0.85;
         double horizontal = Math.sqrt(Math.max(0.0, 1.0 - y * y));
         Vec3 targetDirection = new Vec3(Math.cos(angle) * horizontal, y, Math.sin(angle) * horizontal).normalize();
         Vec3 direction = originalDirection.scale(1.0F - spread).add(targetDirection.scale(spread)).normalize();
         double targetLength = originalLength + 40.0 + random.nextDouble() * 120.0;
         double currentLength = lerp(originalLength, targetLength, spread);
         Vec3 head = center.add(direction.scale(currentLength));
         drawCrossBeam(matrix, consumer, center, head, 0.5, 0.72F, 0.9F, 1.0F, 0.15F * fade);
         drawCrossBeam(matrix, consumer, center, head, 0.1, 1.0F, 1.0F, 1.0F, 0.94F * fade);
      }
   }

   private static void renderExplosionExpansion(DragonRoarCircle entity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource) {
      float age = getVfxAge(entity, partialTick);
      int duration = entity.getVfxDuration();
      if (entity.getLife() > 0 && !(age < 0.0F) && !(age >= duration)) {
         VertexConsumer consumer = bufferSource.getBuffer(RenderType.lightning());
         Matrix4f matrix = poseStack.last().pose();
         float fade = 1.0F - smoothStep(34.0F, duration, age);
         double seedPhase = Math.floorMod(entity.getVfxSeed(), 4096) / 4096.0 * Math.PI * 2.0;

         for (int i = 0; i < 36; i++) {
            float delay = hash01(entity.getVfxSeed(), i) * 7.0F;
            float progress = clamp01((age - delay) / 28.0F);
            if (!(progress <= 0.0F)) {
               double y = 0.06 + 0.68 * (i + 0.5) / 36.0;
               double horizontal = Math.sqrt(Math.max(0.0, 1.0 - y * y));
               double angle = i * 2.399963229728653 + seedPhase;
               Vec3 direction = new Vec3(Math.cos(angle) * horizontal, y, Math.sin(angle) * horizontal).normalize();
               double visualScale = entity.getSize() / 65.0;
               double maxLength = (38.0 + hash01(entity.getVfxSeed() ^ 1821285621, i) * 42.0) * visualScale;
               float headEase = easeOutCubic(progress);
               float trailProgress = clamp01((progress - 0.24F) / 0.76F);
               float tailEase = easeOutCubic(trailProgress) * 0.92F;
               Vec3 tail = direction.scale(maxLength * tailEase);
               Vec3 head = direction.scale(maxLength * headEase);
               float rayAlpha = 0.92F * fade;
               drawCrossBeam(matrix, consumer, tail, head, 0.34, 1.0F, 0.62F, 0.18F, 0.16F * fade);
               drawCrossBeam(matrix, consumer, tail, head, 0.072, 1.0F, 1.0F, 1.0F, rayAlpha);
            }
         }

         float mainProgress = clamp01((age - 2.0F) / 46.0F);
         if (mainProgress > 0.0F && mainProgress < 1.0F) {
            double radius = entity.getSize() * easeOutCubic(mainProgress);
            double width = lerp(5.5, 1.2, mainProgress);
            float alpha = 0.72F * (float)Math.pow(1.0F - mainProgress, 1.35);
            drawHorizontalRing(matrix, consumer, radius, 0.25 + 0.8 * Math.sin(Math.PI * mainProgress), width, 1.0F, 0.7F, 0.18F, alpha);
         }

         float secondaryProgress = clamp01((age - 10.0F) / 50.0F);
         if (secondaryProgress > 0.0F && secondaryProgress < 1.0F) {
            double radius = entity.getSize() * 1.38 * (1.0 - Math.pow(1.0 - secondaryProgress, 2.4));
            double width = lerp(3.0, 0.8, secondaryProgress);
            float alpha = 0.3F * (float)Math.pow(1.0F - secondaryProgress, 1.15);
            drawHorizontalRing(matrix, consumer, radius, 1.0, width, 1.0F, 0.42F, 0.08F, alpha);
         }
      }
   }

   private static void renderGodlySmiteCharge(DragonRoarCircle entity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource) {
      float age = Math.min(1200.0F, Math.max(0.0F, getVfxAge(entity, partialTick)));
      if (entity.getLife() > 0) {
         Matrix4f matrix = poseStack.last().pose();
         VertexConsumer beamConsumer = bufferSource.getBuffer(TenreincarnationRenderTypes.GODLY_SMITE_STREAK);
         VertexConsumer sphereConsumer = bufferSource.getBuffer(TenreincarnationRenderTypes.GODLY_SMITE_SPHERE);
         VertexConsumer whiteoutConsumer = bufferSource.getBuffer(TenreincarnationRenderTypes.GODLY_SMITE_WHITEOUT);
         float appear = smoothStep(0.0F, 8.0F, age);
         float releaseFade = 1.0F - smoothStep(1182.0F, 1200.0F, age);
         float whiteStrength = appear * releaseFade;
         float pulse = 0.94F + 0.06F * (float)Math.sin(age * 0.38F);
         float expansion = smoothStep(0.0F, 54.0F, age);
         Vec3 whiteCenter = new Vec3(0.0, entity.getVfxStartHeight() * 0.25, 0.0);
         double whiteRadius = lerp(18.0, entity.getSize() * 1.8, expansion);
         drawSphere(matrix, whiteoutConsumer, whiteCenter, whiteRadius, 1.0F, 1.0F, 0.96F, 0.34F * whiteStrength * pulse);
         drawSphere(matrix, whiteoutConsumer, whiteCenter, whiteRadius * 0.68, 1.0F, 0.94F, 0.72F, 0.16F * whiteStrength);

         for (int ring = 0; ring < 3; ring++) {
            float ringProgress = smoothStep(ring * 5.0F, 34.0F + ring * 9.0F, age);
            double radius = entity.getSize() * (0.22 + ring * 0.2) * ringProgress;
            drawHorizontalRing(matrix, beamConsumer, radius, 0.08 + ring * 0.04, 0.75 + ring * 0.28, 1.0F, 0.92F, 0.62F, (0.24F - ring * 0.04F) * whiteStrength);
         }

         float formation = smoothStep(1166.0F, 1196.0F, age);
         if (!(formation <= 0.0F)) {
            Vec3 skyCore = new Vec3(0.0, entity.getVfxStartHeight(), 0.0);
            double radius = 60.0F * formation * (0.97 + 0.03 * Math.sin(age * 0.52));
            drawSphere(matrix, sphereConsumer, skyCore, radius, 1.0F, 0.82F, 0.34F, 0.34F * formation);
            drawSphere(matrix, sphereConsumer, skyCore, radius * 0.78, 1.0F, 0.94F, 0.68F, 0.62F * formation);
            drawSphere(matrix, sphereConsumer, skyCore, radius * 0.38, 1.0F, 1.0F, 1.0F, 0.94F * formation);
            RandomSource random = RandomSource.create((long)entity.getVfxSeed() << 32 ^ 3720518819280179535L);

            for (int ray = 0; ray < 24; ray++) {
               double angle = (Math.PI * 2) * ray / 24.0 + age * 0.014;
               double y = random.nextDouble() * 1.6 - 0.8;
               double horizontal = Math.sqrt(Math.max(0.0, 1.0 - y * y));
               Vec3 direction = new Vec3(Math.cos(angle) * horizontal, y, Math.sin(angle) * horizontal).normalize();
               double length = radius * (1.18 + random.nextDouble() * 0.52);
               Vec3 start = skyCore.add(direction.scale(radius * 0.82));
               Vec3 end = skyCore.add(direction.scale(length));
               drawCrossBeam(matrix, beamConsumer, start, end, 0.18, 1.0F, 0.68F, 0.18F, 0.34F * formation);
               drawCrossBeam(matrix, beamConsumer, start, end, 0.045, 1.0F, 1.0F, 0.88F, 0.82F * formation);
            }
         }
      }
   }

   private static void renderLegacyGodlySmiteCharge(DragonRoarCircle entity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource) {
      float age = Math.min(1200.0F, Math.max(0.0F, getVfxAge(entity, partialTick)));
      if (entity.getLife() > 0) {
         Matrix4f matrix = poseStack.last().pose();
         VertexConsumer beamConsumer = bufferSource.getBuffer(TenreincarnationRenderTypes.STRIKE_BEAM);
         VertexConsumer sphereConsumer = bufferSource.getBuffer(TenreincarnationRenderTypes.GODLY_SMITE_SPHERE);
         Vec3 core = new Vec3(0.0, entity.getVfxStartHeight(), 0.0);
         float warningProgress = smoothStep(0.0F, 20.0F, age);
         float gatherProgress = smoothStep(12.0F, 76.0F, age);
         float formationProgress = smoothStep(68.0F, 1200.0F, age);
         float pulse = 0.92F + 0.08F * (float)Math.sin(age * 0.42F);
         double visualRadius = entity.getSize();

         for (int ring = 0; ring < 3; ring++) {
            double ringRadius = visualRadius * (0.28 + ring * 0.19) * warningProgress;
            float ringAlpha = (0.16F + ring * 0.035F) * pulse;
            drawSegmentedRing(
               matrix,
               beamConsumer,
               new Vec3(0.0, 0.08 + ring * 0.035, 0.0),
               new Vec3(1.0, 0.0, 0.0),
               new Vec3(0.0, 0.0, 1.0),
               ringRadius,
               0.42 + ring * 0.18,
               age * (0.012 + ring * 0.006),
               ring,
               0.86F,
               0.45F,
               1.0F,
               ringAlpha
            );
         }

         renderGodlyConvergence(entity, matrix, beamConsumer, core, gatherProgress, formationProgress, age);
         float coreScale = smoothStep(8.0F, 58.0F, age);
         double outerRadius = (2.0 + 8.5 * coreScale) * (0.96 + 0.04 * pulse);
         drawSphere(matrix, sphereConsumer, core, outerRadius, 0.56F, 0.08F, 0.92F, 0.18F + 0.14F * coreScale);
         drawSphere(matrix, sphereConsumer, core, outerRadius * 0.52, 1.0F, 0.7F, 0.96F, 0.34F + 0.34F * coreScale);
         drawSphere(matrix, sphereConsumer, core, outerRadius * 0.2, 1.0F, 1.0F, 1.0F, 0.92F);
         double ringScale = 0.35 + 0.65 * gatherProgress;
         drawSegmentedRing(
            matrix,
            beamConsumer,
            core,
            new Vec3(1.0, 0.0, 0.0),
            new Vec3(0.0, 0.0, 1.0),
            17.0 * ringScale,
            0.72,
            age * 0.025,
            0,
            1.0F,
            0.72F,
            0.18F,
            0.5F * gatherProgress
         );
         drawSegmentedRing(
            matrix,
            beamConsumer,
            core,
            new Vec3(1.0, 0.0, 0.0),
            new Vec3(0.0, 0.72, 0.69),
            23.0 * ringScale,
            0.58,
            -age * 0.018,
            2,
            0.8F,
            0.3F,
            1.0F,
            0.38F * gatherProgress
         );
         drawSegmentedRing(
            matrix,
            beamConsumer,
            core,
            new Vec3(0.69, 0.72, 0.0),
            new Vec3(0.0, 0.0, 1.0),
            29.0 * ringScale,
            0.46,
            age * 0.014,
            4,
            1.0F,
            0.38F,
            0.86F,
            0.3F * gatherProgress
         );
         renderGodlyWings(matrix, beamConsumer, core, age, gatherProgress * (0.55F + 0.45F * formationProgress));
         if (formationProgress > 0.0F) {
            Vec3 lanceTip = core.add(0.0, -18.0 - formationProgress * 26.0, 0.0);
            drawCrossBeam(matrix, beamConsumer, core, lanceTip, 2.8 * formationProgress, 0.43F, 0.04F, 0.78F, 0.26F * formationProgress);
            drawCrossBeam(matrix, beamConsumer, core, lanceTip, 0.58 * formationProgress, 1.0F, 0.94F, 1.0F, 0.92F * formationProgress);
         }
      }
   }

   private static void renderGodlySmiteStrike(DragonRoarCircle entity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource) {
      float age = getVfxAge(entity, partialTick);
      if (entity.getLife() > 0 && !(age < 0.0F) && !(age >= 184.0F)) {
         Matrix4f matrix = poseStack.last().pose();
         if (age < 20.0F) {
            VertexConsumer whiteoutConsumer = bufferSource.getBuffer(TenreincarnationRenderTypes.GODLY_SMITE_WHITEOUT);
            renderReleaseWhiteout(entity, age, matrix, whiteoutConsumer);
         } else {
            float fireballAge = age - 20.0F;
            if (fireballAge < 100.0F) {
               float fallProgress = clamp01(fireballAge / 100.0F);
               float acceleratedFall = fallProgress * fallProgress * fallProgress;
               Vec3 center = lerp(entity.getVfxOriginOffset(), new Vec3(0.0, 60.0, 0.0), acceleratedFall);
               VertexConsumer fireballConsumer = bufferSource.getBuffer(TenreincarnationRenderTypes.GODLY_SMITE_FIREBALL);
               renderDaystarFireball(matrix, fireballConsumer, center, fireballAge);
            } else {
               VertexConsumer beamConsumer = bufferSource.getBuffer(TenreincarnationRenderTypes.GODLY_SMITE_STREAK);
               VertexConsumer sphereConsumer = bufferSource.getBuffer(TenreincarnationRenderTypes.GODLY_SMITE_SPHERE);
               renderDaystarImpact(entity, age - 120.0F, matrix, beamConsumer, sphereConsumer);
            }
         }
      }
   }

   private static void renderReleaseWhiteout(DragonRoarCircle entity, float age, Matrix4f matrix, VertexConsumer consumer) {
      float appear = smoothStep(0.0F, 2.0F, age);
      float fade = 1.0F - smoothStep(12.0F, 20.0F, age);
      float strength = appear * fade;
      Vec3 center = entity.getVfxOriginOffset();
      double radius = entity.getSize() * 2.0;
      drawSphere(matrix, consumer, center, radius, 1.0F, 1.0F, 0.96F, 0.42F * strength);
      drawSphere(matrix, consumer, center, radius * 0.68, 1.0F, 0.91F, 0.6F, 0.18F * strength);
   }

   private static void renderDaystarFireball(Matrix4f matrix, VertexConsumer consumer, Vec3 center, float age) {
      float appear = smoothStep(0.0F, 6.0F, age);
      drawTexturedSphere(matrix, consumer, center, 60.0, age * 0.0035F, appear);
   }

   private static void renderDaystarImpact(
      DragonRoarCircle entity, float impactAge, Matrix4f matrix, VertexConsumer beamConsumer, VertexConsumer sphereConsumer
   ) {
      float scale = entity.getSize() / 96.0F;
      Vec3 impact = new Vec3(0.0, 0.08F, 0.0);
      float domeProgress = easeOutCubic(clamp01(impactAge / 20.0F));
      float domeFade = 1.0F - smoothStep(9.0F, 26.0F, impactAge);
      if (domeFade > 0.0F) {
         double domeRadius = lerp(1.0, 36.0 * scale, domeProgress);
         drawHemisphere(matrix, sphereConsumer, impact, domeRadius, 1.0F, 0.3F, 0.02F, 0.24F * domeFade);
         drawHemisphere(matrix, sphereConsumer, impact, domeRadius * 0.7, 1.0F, 0.66F, 0.1F, 0.34F * domeFade);
         drawHemisphere(matrix, sphereConsumer, impact, domeRadius * 0.34, 1.0F, 1.0F, 0.84F, 0.78F * domeFade);
      }

      float mainProgress = easeOutCubic(clamp01(impactAge / 40.0F));
      if (mainProgress > 0.0F && mainProgress < 1.0F) {
         double radius = lerp(2.0, 72.5 * scale, mainProgress);
         float alpha = 0.7F * (float)Math.pow(1.0F - mainProgress, 0.82);
         drawHorizontalRing(matrix, beamConsumer, radius, 0.14, 3.4 - 1.4 * mainProgress, 1.0F, 0.4F, 0.04F, alpha);
         drawHorizontalRing(matrix, beamConsumer, radius, 0.16, 0.72, 1.0F, 0.92F, 0.62F, Math.min(0.88F, alpha * 1.32F));
      }

      float secondaryAge = impactAge - 7.0F;
      float secondaryProgress = easeOutCubic(clamp01(secondaryAge / 44.0F));
      if (secondaryProgress > 0.0F && secondaryProgress < 1.0F) {
         double radius = lerp(4.0, 92.5 * scale, secondaryProgress);
         float alpha = 0.58F * (float)Math.pow(1.0F - secondaryProgress, 0.88);
         drawHorizontalRing(matrix, beamConsumer, radius, 0.38, 1.9 - 0.7 * secondaryProgress, 1.0F, 0.18F, 0.02F, alpha);
         drawHorizontalRing(matrix, beamConsumer, radius, 0.4, 0.38, 1.0F, 0.7F, 0.2F, Math.min(0.7F, alpha * 1.16F));
      }

      renderDaystarImpactRays(entity, impactAge, matrix, beamConsumer, impact, scale);
      renderDaystarFireBurst(entity, impactAge, matrix, beamConsumer, impact, scale);
      renderDaystarEmbers(entity, impactAge, matrix, beamConsumer, impact, scale);
   }

   private static void renderDaystarImpactRays(DragonRoarCircle entity, float impactAge, Matrix4f matrix, VertexConsumer consumer, Vec3 impact, float scale) {
      float progress = easeOutCubic(clamp01(impactAge / 32.0F));
      float fade = 1.0F - smoothStep(18.0F, 36.0F, impactAge);
      if (!(progress <= 0.0F) && !(fade <= 0.0F)) {
         RandomSource random = RandomSource.create((long)entity.getVfxSeed() << 32 ^ 3207673265515705950L);

         for (int ray = 0; ray < 30; ray++) {
            double angle = ray * 2.399963229728653 + random.nextDouble() * 0.18;
            double y = 0.05 + random.nextDouble() * 0.3;
            double horizontal = Math.sqrt(Math.max(0.0, 1.0 - y * y));
            Vec3 direction = new Vec3(Math.cos(angle) * horizontal, y, Math.sin(angle) * horizontal).normalize();
            double length = 57.5 * scale * (0.62 + random.nextDouble() * 0.38) * progress;
            Vec3 end = impact.add(direction.scale(length));
            drawCrossBeam(matrix, consumer, impact, end, 0.48, 1.0F, 0.2F, 0.015F, 0.24F * fade);
            drawCrossBeam(matrix, consumer, impact, end, 0.1, 1.0F, 0.88F, 0.48F, 0.86F * fade);
         }
      }
   }

   private static void renderDaystarFireBurst(DragonRoarCircle entity, float impactAge, Matrix4f matrix, VertexConsumer consumer, Vec3 impact, float scale) {
      RandomSource random = RandomSource.create((long)entity.getVfxSeed() << 32 ^ 8406284002445686875L);

      for (int particle = 0; particle < 120; particle++) {
         float delay = random.nextFloat() * 5.0F;
         float progress = clamp01((impactAge - delay) / 48.0F);
         if (!(progress <= 0.0F) && !(progress >= 1.0F)) {
            double angle = particle * 2.399963229728653 + random.nextDouble() * 0.24;
            double y = 0.03 + random.nextDouble() * 0.27;
            double horizontal = Math.sqrt(Math.max(0.0, 1.0 - y * y));
            Vec3 direction = new Vec3(Math.cos(angle) * horizontal, y, Math.sin(angle) * horizontal).normalize();
            double distance = 62.5 * scale * (0.42 + random.nextDouble() * 0.58) * easeOutCubic(progress);
            Vec3 head = impact.add(direction.scale(distance)).add(0.0, Math.sin(Math.PI * progress) * (2.0 + random.nextDouble() * 8.0), 0.0);
            double streakLength = (2.0 + random.nextDouble() * 5.5) * (1.0 - progress * 0.55);
            Vec3 tail = head.subtract(direction.scale(streakLength));
            float fade = (float)Math.pow(1.0F - progress, 0.7);
            drawCrossBeam(matrix, consumer, tail, head, 0.22 + random.nextDouble() * 0.24, 1.0F, 0.16F, 0.01F, 0.36F * fade);
            drawCrossBeam(matrix, consumer, tail, head, 0.055, 1.0F, 0.82F, 0.22F, 0.92F * fade);
         }
      }
   }

   private static void renderDaystarEmbers(DragonRoarCircle entity, float impactAge, Matrix4f matrix, VertexConsumer consumer, Vec3 impact, float scale) {
      float emberAge = impactAge - 5.0F;
      if (!(emberAge <= 0.0F)) {
         RandomSource random = RandomSource.create((long)entity.getVfxSeed() << 32 ^ -6276679107799588801L);

         for (int ember = 0; ember < 96; ember++) {
            float delay = random.nextFloat() * 8.0F;
            float progress = clamp01((emberAge - delay) / 48.0F);
            if (!(progress <= 0.0F) && !(progress >= 1.0F)) {
               double angle = ember * 2.399963229728653 + random.nextDouble() * 0.36;
               double horizontalDistance = 62.5 * scale * (0.18 + random.nextDouble() * 0.82) * easeOutCubic(progress);
               double lift = (8.0 + random.nextDouble() * 30.0) * progress;
               Vec3 head = impact.add(Math.cos(angle) * horizontalDistance, lift, Math.sin(angle) * horizontalDistance);
               Vec3 streakDirection = new Vec3(Math.cos(angle), 0.12 + random.nextDouble() * 0.18, Math.sin(angle)).normalize();
               Vec3 tail = head.subtract(streakDirection.scale(0.8 + random.nextDouble() * 2.6));
               float fade = (float)Math.pow(1.0F - progress, 0.82);
               drawCrossBeam(matrix, consumer, tail, head, 0.09 + random.nextDouble() * 0.1, 0.86F, 0.06F, 0.005F, 0.42F * fade);
               drawCrossBeam(matrix, consumer, tail, head, 0.028, 1.0F, 0.72F, 0.12F, 0.9F * fade);
            }
         }
      }
   }

   private static void drawHemisphere(Matrix4f matrix, VertexConsumer consumer, Vec3 center, double radius, float red, float green, float blue, float alpha) {
      if (!(radius <= 0.001) && !(alpha <= 0.001F)) {
         for (int index = 0; index < UNIT_HEMISPHERE_VERTICES.length; index += 3) {
            consumer.addVertex(
                  matrix,
                  (float)(center.x + UNIT_HEMISPHERE_VERTICES[index] * radius),
                  (float)(center.y + UNIT_HEMISPHERE_VERTICES[index + 1] * radius),
                  (float)(center.z + UNIT_HEMISPHERE_VERTICES[index + 2] * radius)
               )
               .setColor(red, green, blue, alpha);
         }
      }
   }

   private static void renderLegacyGodlySmiteStrike(DragonRoarCircle entity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource) {
      float age = getVfxAge(entity, partialTick);
      if (entity.getLife() > 0 && !(age < 0.0F) && !(age >= 184.0F)) {
         Matrix4f matrix = poseStack.last().pose();
         VertexConsumer beamConsumer = bufferSource.getBuffer(TenreincarnationRenderTypes.STRIKE_BEAM);
         VertexConsumer sphereConsumer = bufferSource.getBuffer(TenreincarnationRenderTypes.GODLY_SMITE_SPHERE);
         Vec3 core = new Vec3(0.0, entity.getVfxStartHeight(), 0.0);
         Vec3 impact = new Vec3(0.0, 0.08, 0.0);
         float compression = smoothStep(0.0F, 12.0F, age);
         float beamProgress = smoothStep(12.0F, 120.0F, age);
         float impactAge = age - 120.0F;
         float coreFade = 1.0F - smoothStep(120.0F, 148.0F, age);
         double compressedRadius = lerp(10.5, 3.0, compression);
         drawSphere(matrix, sphereConsumer, core, compressedRadius, 0.58F, 0.04F, 0.94F, 0.3F * coreFade);
         drawSphere(matrix, sphereConsumer, core, compressedRadius * 0.48, 1.0F, 0.85F, 1.0F, 0.76F * coreFade);
         renderGodlyWings(matrix, beamConsumer, core, age, (1.0F - smoothStep(10.0F, 34.0F, age)) * 0.78F);
         double ringRadius = lerp(30.0, 6.0, compression);
         drawSegmentedRing(
            matrix, beamConsumer, core, new Vec3(1.0, 0.0, 0.0), new Vec3(0.0, 0.0, 1.0), ringRadius, 0.78, age * 0.08, 1, 1.0F, 0.68F, 0.15F, 0.52F * coreFade
         );
         drawSegmentedRing(
            matrix,
            beamConsumer,
            core,
            new Vec3(1.0, 0.0, 0.0),
            new Vec3(0.0, 0.72, 0.69),
            ringRadius * 1.28,
            0.55,
            -age * 0.06,
            3,
            0.82F,
            0.22F,
            1.0F,
            0.38F * coreFade
         );
         if (age < 12.0F) {
            Vec3 lanceTip = core.add(0.0, -lerp(34.0, 72.0, compression), 0.0);
            drawCrossBeam(matrix, beamConsumer, core, lanceTip, 3.6, 0.44F, 0.02F, 0.74F, 0.3F);
            drawCrossBeam(matrix, beamConsumer, core, lanceTip, 0.72, 1.0F, 0.96F, 1.0F, 0.96F);
         }

         if (age >= 12.0F) {
            Vec3 head = new Vec3(lerp(core.x, impact.x, beamProgress), lerp(core.y, impact.y, beamProgress), lerp(core.z, impact.z, beamProgress));
            float beamFade = age < 120.0F ? 1.0F : 1.0F - smoothStep(132.0F, 168.0F, age);
            drawCrossBeam(matrix, beamConsumer, core, head, 8.5, 0.22F, 0.0F, 0.42F, 0.2F * beamFade);
            drawCrossBeam(matrix, beamConsumer, core, head, 4.2, 0.68F, 0.06F, 0.98F, 0.34F * beamFade);
            drawCrossBeam(matrix, beamConsumer, core, head, 1.25, 1.0F, 0.72F, 1.0F, 0.72F * beamFade);
            drawCrossBeam(matrix, beamConsumer, core, head, 0.38, 1.0F, 1.0F, 1.0F, 1.0F * beamFade);
            renderGodlyLightning(entity, matrix, beamConsumer, core, head, beamFade);
            if (beamProgress > 0.02F && beamProgress < 1.0F) {
               double descentRingRadius = 9.0 + 13.0 * beamProgress;
               drawSegmentedRing(
                  matrix, beamConsumer, head, new Vec3(1.0, 0.0, 0.0), new Vec3(0.0, 0.0, 1.0), descentRingRadius, 0.9, age * 0.1, 0, 1.0F, 0.72F, 0.96F, 0.58F
               );
            }
         }

         if (impactAge >= 0.0F) {
            renderGodlyImpact(entity, impactAge, matrix, beamConsumer, sphereConsumer, impact);
         }
      }
   }

   private static void renderGodlyConvergence(
      DragonRoarCircle entity, Matrix4f matrix, VertexConsumer consumer, Vec3 core, float gatherProgress, float formationProgress, float age
   ) {
      if (!(gatherProgress <= 0.0F)) {
         RandomSource random = RandomSource.create((long)entity.getVfxSeed() << 32 ^ 7468308356182824833L);

         for (int i = 0; i < 24; i++) {
            double angle = (Math.PI * 2) * i / 24.0 + random.nextDouble() * 0.14;
            double radius = entity.getSize() * (0.48 + random.nextDouble() * 0.62);
            Vec3 origin = new Vec3(Math.cos(angle) * radius, 5.0 + random.nextDouble() * core.y * 0.62, Math.sin(angle) * radius);
            float delay = i / 24.0F * 0.22F;
            float headProgress = easeOutCubic(clamp01((gatherProgress - delay) / Math.max(0.01F, 1.0F - delay)));
            float tailProgress = easeOutCubic(clamp01((gatherProgress - 0.2F - delay) / Math.max(0.01F, 0.8F - delay)));
            if (!(headProgress <= 0.0F)) {
               Vec3 tail = lerp(origin, core, tailProgress);
               Vec3 head = lerp(origin, core, headProgress);
               float flicker = 0.76F + 0.24F * (float)Math.sin(age * 0.34F + i * 1.7F);
               float alpha = (0.24F + 0.24F * (1.0F - formationProgress)) * flicker;
               drawCrossBeam(matrix, consumer, tail, head, 0.34, 0.48F, 0.04F, 0.86F, alpha * 0.52F);
               drawCrossBeam(matrix, consumer, tail, head, 0.075, 1.0F, 0.82F, 1.0F, alpha);
            }
         }
      }
   }

   private static void renderGodlyWings(Matrix4f matrix, VertexConsumer consumer, Vec3 core, float age, float alphaScale) {
      if (!(alphaScale <= 0.001F)) {
         double phase = age * 0.006;

         for (int pair = 0; pair < 8; pair++) {
            double angle = (Math.PI * 2) * pair / 8.0 + phase;
            Vec3 radial = new Vec3(Math.cos(angle), 0.0, Math.sin(angle));
            Vec3 tangent = new Vec3(-Math.sin(angle), 0.0, Math.cos(angle));
            Vec3 root = core.add(radial.scale(4.5));

            for (int side = -1; side <= 1; side += 2) {
               for (int feather = 0; feather < 3; feather++) {
                  Vec3 tip = root.add(radial.scale(9.0 + feather * 3.8)).add(tangent.scale(side * (6.0 + feather * 3.4))).add(0.0, 3.0 - feather * 3.1, 0.0);
                  drawCrossBeam(matrix, consumer, root, tip, 0.22 - feather * 0.035, 1.0F, 0.76F, 0.96F, alphaScale * (0.34F - feather * 0.055F));
                  drawCrossBeam(matrix, consumer, root, tip, 0.055, 1.0F, 1.0F, 1.0F, alphaScale * (0.76F - feather * 0.12F));
               }
            }
         }
      }
   }

   private static void renderGodlyLightning(DragonRoarCircle entity, Matrix4f matrix, VertexConsumer consumer, Vec3 start, Vec3 end, float alphaScale) {
      if (!(alphaScale <= 0.001F) && !(start.distanceToSqr(end) < 1.0E-6)) {
         Vec3 axis = end.subtract(start);
         Vec3 sideA = axis.cross(new Vec3(0.0, 1.0, 0.0));
         if (sideA.lengthSqr() < 1.0E-8) {
            sideA = new Vec3(1.0, 0.0, 0.0);
         }

         sideA = sideA.normalize();
         Vec3 sideB = axis.normalize().cross(sideA).normalize();

         for (int arc = 0; arc < 4; arc++) {
            RandomSource random = RandomSource.create((long)entity.getVfxSeed() << 32 ^ -7046029254386353131L * (arc + 1));
            Vec3 previous = start;

            for (int segment = 1; segment <= 9; segment++) {
               float progress = segment / 9.0F;
               double envelope = Math.sin(Math.PI * progress);
               Vec3 point = lerp(start, end, progress)
                  .add(sideA.scale((random.nextDouble() - 0.5) * 9.0 * envelope))
                  .add(sideB.scale((random.nextDouble() - 0.5) * 9.0 * envelope));
               drawCrossBeam(matrix, consumer, previous, point, 0.18, 0.2F, 0.0F, 0.34F, 0.48F * alphaScale);
               drawCrossBeam(matrix, consumer, previous, point, 0.045, 0.96F, 0.28F, 1.0F, 0.88F * alphaScale);
               previous = point;
            }
         }
      }
   }

   private static void renderGodlyImpact(
      DragonRoarCircle entity, float impactAge, Matrix4f matrix, VertexConsumer beamConsumer, VertexConsumer sphereConsumer, Vec3 impact
   ) {
      float domeProgress = clamp01(impactAge / 34.0F);
      float domeFade = 1.0F - smoothStep(20.0F, 52.0F, impactAge);
      if (domeFade > 0.0F) {
         double domeRadius = entity.getSize() * 0.46 * easeOutCubic(domeProgress);
         drawSphere(matrix, sphereConsumer, impact, domeRadius, 0.58F, 0.05F, 0.94F, 0.18F * domeFade);
         drawSphere(matrix, sphereConsumer, impact, domeRadius * 0.68, 1.0F, 0.52F, 0.96F, 0.2F * domeFade);
         drawSphere(matrix, sphereConsumer, impact, domeRadius * 0.28, 1.0F, 1.0F, 1.0F, 0.42F * domeFade);
      }

      for (int ring = 0; ring < 3; ring++) {
         float delayedAge = impactAge - ring * 7.0F;
         float progress = clamp01(delayedAge / (34.0F + ring * 8.0F));
         if (!(progress <= 0.0F) && !(progress >= 1.0F)) {
            double radius = entity.getSize() * (0.72 + ring * 0.2) * easeOutCubic(progress);
            float alpha = (0.58F - ring * 0.11F) * (float)Math.pow(1.0F - progress, 1.15);
            drawSegmentedRing(
               matrix,
               beamConsumer,
               impact.add(0.0, 0.1 + ring * 0.04, 0.0),
               new Vec3(1.0, 0.0, 0.0),
               new Vec3(0.0, 0.0, 1.0),
               radius,
               1.0 + ring * 0.34,
               impactAge * (0.04 + ring * 0.015),
               ring,
               ring == 0 ? 1.0F : 0.76F,
               ring == 0 ? 0.78F : 0.2F,
               1.0F,
               alpha
            );
         }
      }

      float crackProgress = easeOutCubic(clamp01(impactAge / 42.0F));
      float crackFade = 1.0F - smoothStep(50.0F, 98.0F, impactAge);
      renderGodlyCracks(entity, matrix, beamConsumer, crackProgress, crackFade);
      renderGodlyImpactRays(entity, matrix, beamConsumer, impactAge);
   }

   private static void renderGodlyCracks(DragonRoarCircle entity, Matrix4f matrix, VertexConsumer consumer, float progress, float fade) {
      if (!(progress <= 0.0F) && !(fade <= 0.0F)) {
         RandomSource random = RandomSource.create((long)entity.getVfxSeed() << 32 ^ -4521708957497675121L);

         for (int ray = 0; ray < 18; ray++) {
            double angle = (Math.PI * 2) * ray / 18.0 + (random.nextDouble() - 0.5) * 0.26;
            Vec3 direction = new Vec3(Math.cos(angle), 0.0, Math.sin(angle));
            Vec3 tangent = new Vec3(-Math.sin(angle), 0.0, Math.cos(angle));
            double length = entity.getSize() * (0.34 + random.nextDouble() * 0.46) * progress;
            Vec3 previous = new Vec3(0.0, 0.1, 0.0);

            for (int segment = 1; segment <= 4; segment++) {
               double segmentProgress = segment / 4.0;
               Vec3 point = direction.scale(length * segmentProgress).add(tangent.scale((random.nextDouble() - 0.5) * 4.0)).add(0.0, 0.1, 0.0);
               drawCrossBeam(matrix, consumer, previous, point, 0.22, 0.34F, 0.0F, 0.56F, 0.3F * fade);
               drawCrossBeam(matrix, consumer, previous, point, 0.052, 1.0F, 0.36F, 1.0F, 0.82F * fade);
               previous = point;
            }
         }
      }
   }

   private static void renderGodlyImpactRays(DragonRoarCircle entity, Matrix4f matrix, VertexConsumer consumer, float impactAge) {
      float progress = easeOutCubic(clamp01(impactAge / 28.0F));
      float fade = 1.0F - smoothStep(18.0F, 56.0F, impactAge);
      if (!(progress <= 0.0F) && !(fade <= 0.0F)) {
         RandomSource random = RandomSource.create((long)entity.getVfxSeed() << 32 ^ -7935046062780286179L);
         Vec3 center = new Vec3(0.0, 0.12, 0.0);

         for (int ray = 0; ray < 28; ray++) {
            double angle = ray * 2.399963229728653;
            double y = 0.05 + random.nextDouble() * 0.72;
            double horizontal = Math.sqrt(Math.max(0.0, 1.0 - y * y));
            Vec3 direction = new Vec3(Math.cos(angle) * horizontal, y, Math.sin(angle) * horizontal).normalize();
            double length = entity.getSize() * (0.35 + random.nextDouble() * 0.65) * progress;
            Vec3 end = center.add(direction.scale(length));
            drawCrossBeam(matrix, consumer, center, end, 0.26, 0.72F, 0.08F, 1.0F, 0.22F * fade);
            drawCrossBeam(matrix, consumer, center, end, 0.058, 1.0F, 0.86F, 1.0F, 0.82F * fade);
         }
      }
   }

   private static void drawTexturedSphere(Matrix4f matrix, VertexConsumer consumer, Vec3 center, double radius, float uOffset, float alpha) {
      if (!(radius <= 0.001) && !(alpha <= 0.001F)) {
         for (int index = 0; index < TEXTURED_FIREBALL_VERTICES.length; index += 5) {
            consumer.addVertex(
                  matrix,
                  (float)(center.x + TEXTURED_FIREBALL_VERTICES[index] * radius),
                  (float)(center.y + TEXTURED_FIREBALL_VERTICES[index + 1] * radius),
                  (float)(center.z + TEXTURED_FIREBALL_VERTICES[index + 2] * radius)
               )
               .setUv(TEXTURED_FIREBALL_VERTICES[index + 3] + uOffset, TEXTURED_FIREBALL_VERTICES[index + 4])
               .setColor(1.0F, 1.0F, 1.0F, alpha);
         }
      }
   }

   private static void drawTexturedHorizontalQuad(
      Matrix4f matrix, VertexConsumer consumer, double halfSize, double offsetX, double offsetZ, float red, float green, float blue, float alpha
   ) {
      float minX = (float)(offsetX - halfSize);
      float maxX = (float)(offsetX + halfSize);
      float minZ = (float)(offsetZ - halfSize);
      float maxZ = (float)(offsetZ + halfSize);
      consumer.addVertex(matrix, minX, 0.0F, minZ).setUv(0.0F, 0.0F).setColor(red, green, blue, alpha);
      consumer.addVertex(matrix, minX, 0.0F, maxZ).setUv(0.0F, 1.0F).setColor(red, green, blue, alpha);
      consumer.addVertex(matrix, maxX, 0.0F, maxZ).setUv(1.0F, 1.0F).setColor(red, green, blue, alpha);
      consumer.addVertex(matrix, maxX, 0.0F, minZ).setUv(1.0F, 0.0F).setColor(red, green, blue, alpha);
   }

   private static void drawSphere(Matrix4f matrix, VertexConsumer consumer, Vec3 center, double radius, float red, float green, float blue, float alpha) {
      if (!(radius <= 0.001) && !(alpha <= 0.001F)) {
         for (int index = 0; index < UNIT_SPHERE_VERTICES.length; index += 3) {
            consumer.addVertex(
                  matrix,
                  (float)(center.x + UNIT_SPHERE_VERTICES[index] * radius),
                  (float)(center.y + UNIT_SPHERE_VERTICES[index + 1] * radius),
                  (float)(center.z + UNIT_SPHERE_VERTICES[index + 2] * radius)
               )
               .setColor(red, green, blue, alpha);
         }
      }
   }

   private static void drawSegmentedRing(
      Matrix4f matrix,
      VertexConsumer consumer,
      Vec3 center,
      Vec3 basisA,
      Vec3 basisB,
      double radius,
      double width,
      double rotation,
      int gapOffset,
      float red,
      float green,
      float blue,
      float alpha
   ) {
      if (!(radius <= 0.001) && !(width <= 0.001) && !(alpha <= 0.001F)) {
         Vec3 axisA = basisA.normalize();
         Vec3 axisB = basisB.normalize();
         double innerRadius = Math.max(0.0, radius - width * 0.5);
         double outerRadius = radius + width * 0.5;
         int animatedOffset = (int)Math.floor(rotation * 2.0) + gapOffset;

         for (int i = 0; i < 64; i++) {
            if (Math.floorMod(i + animatedOffset, 8) < 6) {
               double angleA = (Math.PI * 2) * i / 64.0 + rotation;
               double angleB = (Math.PI * 2) * (i + 1) / 64.0 + rotation;
               Vec3 innerA = ringPoint(center, axisA, axisB, innerRadius, angleA);
               Vec3 innerB = ringPoint(center, axisA, axisB, innerRadius, angleB);
               Vec3 outerB = ringPoint(center, axisA, axisB, outerRadius, angleB);
               Vec3 outerA = ringPoint(center, axisA, axisB, outerRadius, angleA);
               addQuad(matrix, consumer, innerA, innerB, outerB, outerA, red, green, blue, alpha);
            }
         }
      }
   }

   private static Vec3 ringPoint(Vec3 center, Vec3 basisA, Vec3 basisB, double radius, double angle) {
      return center.add(basisA.scale(Math.cos(angle) * radius)).add(basisB.scale(Math.sin(angle) * radius));
   }

   private static void addQuad(Matrix4f matrix, VertexConsumer consumer, Vec3 a, Vec3 b, Vec3 c, Vec3 d, float red, float green, float blue, float alpha) {
      consumer.addVertex(matrix, (float)a.x, (float)a.y, (float)a.z).setColor(red, green, blue, alpha);
      consumer.addVertex(matrix, (float)b.x, (float)b.y, (float)b.z).setColor(red, green, blue, alpha);
      consumer.addVertex(matrix, (float)c.x, (float)c.y, (float)c.z).setColor(red, green, blue, alpha);
      consumer.addVertex(matrix, (float)d.x, (float)d.y, (float)d.z).setColor(red, green, blue, alpha);
   }

   private static Vec3 lerp(Vec3 start, Vec3 end, float progress) {
      return new Vec3(lerp(start.x, end.x, progress), lerp(start.y, end.y, progress), lerp(start.z, end.z, progress));
   }

   private static float getVfxAge(DragonRoarCircle entity, float partialTick) {
      long startGameTime = entity.getVfxStartGameTime();
      return startGameTime > 0L ? (float)entity.level().getGameTime() + partialTick - (float)startGameTime : entity.getVisualAge() + partialTick;
   }

   private static void drawHorizontalRing(
      Matrix4f matrix, VertexConsumer consumer, double radius, double y, double width, float red, float green, float blue, float alpha
   ) {
      if (!(radius <= 0.0) && !(width <= 0.0) && !(alpha <= 0.0F)) {
         double innerRadius = Math.max(0.0, radius - width * 0.5);
         double outerRadius = radius + width * 0.5;

         for (int i = 0; i < 64; i++) {
            double angleA = (Math.PI * 2) * i / 64.0;
            double angleB = (Math.PI * 2) * (i + 1) / 64.0;
            consumer.addVertex(matrix, (float)(Math.cos(angleA) * innerRadius), (float)y, (float)(Math.sin(angleA) * innerRadius))
               .setColor(red, green, blue, alpha);
            consumer.addVertex(matrix, (float)(Math.cos(angleB) * innerRadius), (float)y, (float)(Math.sin(angleB) * innerRadius))
               .setColor(red, green, blue, alpha);
            consumer.addVertex(matrix, (float)(Math.cos(angleB) * outerRadius), (float)y, (float)(Math.sin(angleB) * outerRadius))
               .setColor(red, green, blue, alpha);
            consumer.addVertex(matrix, (float)(Math.cos(angleA) * outerRadius), (float)y, (float)(Math.sin(angleA) * outerRadius))
               .setColor(red, green, blue, alpha);
         }
      }
   }

   private static float hash01(int seed, int index) {
      int value = seed ^ index * -1640531527;
      value ^= value >>> 16;
      value *= 2146121005;
      value ^= value >>> 15;
      value *= -2073254261;
      value ^= value >>> 16;
      return (value & 2147483647) / 2.1474836E9F;
   }

   private static float clamp01(float value) {
      return Math.max(0.0F, Math.min(1.0F, value));
   }

   private static float easeOutCubic(float value) {
      float inverse = 1.0F - clamp01(value);
      return 1.0F - inverse * inverse * inverse;
   }

   private static float smoothStep(float edge0, float edge1, float value) {
      if (edge1 <= edge0) {
         return value >= edge1 ? 1.0F : 0.0F;
      }

      float t = clamp01((value - edge0) / (edge1 - edge0));
      return t * t * (3.0F - 2.0F * t);
   }

   private static double lerp(double start, double end, float progress) {
      return start + (end - start) * progress;
   }

   private static void drawCrossBeam(
      Matrix4f matrix, VertexConsumer consumer, Vec3 start, Vec3 end, double halfWidth, float red, float green, float blue, float alpha
   ) {
      Vec3 axis = end.subtract(start);
      if (!(axis.lengthSqr() < 1.0E-8)) {
         axis = axis.normalize();
         Vec3 sideA = axis.cross(new Vec3(0.0, 1.0, 0.0));
         if (sideA.lengthSqr() < 1.0E-8) {
            sideA = axis.cross(new Vec3(1.0, 0.0, 0.0));
         }

         sideA = sideA.normalize().scale(halfWidth);
         Vec3 sideB = axis.cross(sideA).normalize().scale(halfWidth);
         drawRibbon(matrix, consumer, start, end, sideA, red, green, blue, alpha);
         drawRibbon(matrix, consumer, start, end, sideB, red, green, blue, alpha);
      }
   }

   private static void drawRibbon(Matrix4f matrix, VertexConsumer consumer, Vec3 start, Vec3 end, Vec3 side, float red, float green, float blue, float alpha) {
      Vec3 startLeft = start.subtract(side);
      Vec3 startRight = start.add(side);
      Vec3 endLeft = end.subtract(side);
      Vec3 endRight = end.add(side);
      consumer.addVertex(matrix, (float)startLeft.x, (float)startLeft.y, (float)startLeft.z).setColor(red, green, blue, alpha);
      consumer.addVertex(matrix, (float)endLeft.x, (float)endLeft.y, (float)endLeft.z).setColor(red, green, blue, alpha);
      consumer.addVertex(matrix, (float)endRight.x, (float)endRight.y, (float)endRight.z).setColor(red, green, blue, alpha);
      consumer.addVertex(matrix, (float)startRight.x, (float)startRight.y, (float)startRight.z).setColor(red, green, blue, alpha);
   }

   public void preRender(
      PoseStack poseStack,
      DragonRoarCircle animatable,
      BakedGeoModel model,
      MultiBufferSource bufferSource,
      VertexConsumer buffer,
      boolean isReRender,
      float partialTick,
      int packedLight,
      int packedOverlay,
      int colour
   ) {
      super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, colour);
      float interpolatedYaw = Mth.rotLerp(partialTick, animatable.yRotO, animatable.getYRot());
      if (animatable.isGodlySmiteChargeVfx()) {
         poseStack.translate(0.0, 2.5, 0.0);
         poseStack.mulPose(Axis.YP.rotationDegrees(-interpolatedYaw));
         poseStack.scale(2.5F, 2.5F, 2.5F);
      } else {
         poseStack.mulPose(Axis.YP.rotationDegrees(-interpolatedYaw));
         poseStack.scale(0.3F, 0.3F, 0.3F);
      }
   }

   public RenderType getRenderType(DragonRoarCircle animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
      return animatable.isGodlySmiteChargeVfx() ? RenderType.entityTranslucentEmissive(texture) : RenderType.entityTranslucentEmissive(texture);
   }

   public Color getRenderColor(DragonRoarCircle animatable, float partialTick, int packedLight) {
      Color color = super.getRenderColor(animatable, partialTick, packedLight);
      return animatable.isGodlySmiteChargeVfx() ? Color.ofARGB(1.0F, color.getRedFloat(), color.getGreenFloat(), color.getBlueFloat()) : color;
   }

   protected float getDeathMaxRotation(DragonRoarCircle animatable) {
      return 0.0F;
   }

   public int getPackedOverlay(DragonRoarCircle animatable, float u, float partialTick) {
      return OverlayTexture.NO_OVERLAY;
   }

   protected int getBlockLightLevel(DragonRoarCircle entity, BlockPos pos) {
      return 15;
   }
}
