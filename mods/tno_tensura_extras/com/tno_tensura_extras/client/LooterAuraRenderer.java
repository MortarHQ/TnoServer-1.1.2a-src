package com.tno_tensura_extras.client;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import com.mojang.math.Axis;
import io.github.manasmods.tensura.particle.option.SimpleAuraParticleOptions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderType.CompositeState;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

public final class LooterAuraRenderer {
   private static final RenderType AURA = RenderType.create(
      "tno_looter_dark_aura",
      DefaultVertexFormat.POSITION_COLOR,
      Mode.QUADS,
      8192,
      false,
      true,
      CompositeState.builder()
         .setShaderState(RenderStateShard.POSITION_COLOR_SHADER)
         .setTransparencyState(RenderStateShard.TRANSLUCENT_TRANSPARENCY)
         .setDepthTestState(RenderStateShard.LEQUAL_DEPTH_TEST)
         .setCullState(RenderStateShard.NO_CULL)
         .setWriteMaskState(RenderStateShard.COLOR_WRITE)
         .createCompositeState(false)
   );
   private static final float TAU = (float) (Math.PI * 2);
   private static final SimpleAuraParticleOptions DARK_AURA = new SimpleAuraParticleOptions(0.17F, 0.03F, 0.26F, 0.7F, 3.1F, -0.52F, 32);
   private static final SimpleAuraParticleOptions DARK_AURA_DEEP = new SimpleAuraParticleOptions(0.11F, 0.016F, 0.19F, 0.62F, 3.65F, -0.46F, 38);
   private static final SimpleAuraParticleOptions DARK_AURA_PURPLE = new SimpleAuraParticleOptions(0.34F, 0.075F, 0.52F, 0.48F, 1.9F, -0.58F, 25);

   private LooterAuraRenderer() {
   }

   public static void render(PoseStack poseStack, MultiBufferSource buffer, LivingEntity entity, float partialTick, int packedLight, int packedOverlay) {
      if (!entity.isInvisible()) {
         float time = entity.tickCount + partialTick;
         float height = Mth.clamp(entity.getBbHeight(), 0.65F, 4.0F);
         float width = Mth.clamp(entity.getBbWidth(), 0.3F, 2.5F);
         float radius = Math.max(0.23F, width * 0.62F);
         VertexConsumer consumer = buffer.getBuffer(AURA);
         poseStack.pushPose();
         drawRibbons(poseStack.last().pose(), consumer, time, height, radius);
         drawMotes(poseStack, consumer, time, height, radius);
         drawPlume(poseStack, consumer, time, height, radius);
         poseStack.popPose();
      }
   }

   private static void drawRibbons(Matrix4f matrix, VertexConsumer consumer, float time, float height, float radius) {
      int ribbonCount = 6;
      int segments = 6;

      for (int ribbon = 0; ribbon < 6; ribbon++) {
         float direction = (ribbon & 1) == 0 ? 1.0F : -1.0F;
         float phase = ribbon * (float) (Math.PI * 2) / 6.0F + time * (0.075F + ribbon % 3 * 0.013F) * direction;

         for (int segment = 0; segment < 6; segment++) {
            float t0 = segment / 6.0F;
            float t1 = (segment + 1) / 6.0F;
            float y0 = height * (0.14F + t0 * 0.8F);
            float y1 = height * (0.14F + t1 * 0.8F);
            float wobble0 = Mth.sin(time * 0.11F + ribbon * 1.71F + t0 * 4.2F) * 0.12F;
            float wobble1 = Mth.sin(time * 0.11F + ribbon * 1.71F + t1 * 4.2F) * 0.12F;
            float angle0 = phase + t0 * (2.45F + ribbon * 0.08F) + wobble0;
            float angle1 = phase + t1 * (2.45F + ribbon * 0.08F) + wobble1;
            float r0 = radius * (1.02F + Mth.sin(time * 0.17F + ribbon * 2.4F + t0 * 3.0F) * 0.13F);
            float r1 = radius * (1.02F + Mth.sin(time * 0.17F + ribbon * 2.4F + t1 * 3.0F) * 0.13F);
            float x0 = Mth.cos(angle0) * r0;
            float z0 = Mth.sin(angle0) * r0;
            float x1 = Mth.cos(angle1) * r1;
            float z1 = Mth.sin(angle1) * r1;
            float nx0 = -Mth.sin(angle0);
            float nz0 = Mth.cos(angle0);
            float nx1 = -Mth.sin(angle1);
            float nz1 = Mth.cos(angle1);
            float halfWidth = radius * (0.16F + ribbon % 3 * 0.024F);
            float segmentFade = Mth.sin(t0 * (float) Math.PI);
            float alpha0 = (0.15F + ribbon % 3 * 0.026F) * (0.55F + segmentFade * 0.45F);
            float alpha1 = (0.15F + ribbon % 3 * 0.026F) * (0.55F + Mth.sin(t1 * (float) Math.PI) * 0.45F);
            addRibbonQuad(
               matrix,
               consumer,
               x0 - nx0 * halfWidth,
               y0,
               z0 - nz0 * halfWidth,
               x0 + nx0 * halfWidth,
               y0,
               z0 + nz0 * halfWidth,
               x1 + nx1 * halfWidth,
               y1,
               z1 + nz1 * halfWidth,
               x1 - nx1 * halfWidth,
               y1,
               z1 - nz1 * halfWidth,
               alpha0,
               alpha1
            );
         }
      }
   }

   private static void drawMotes(PoseStack poseStack, VertexConsumer consumer, float time, float height, float radius) {
      Quaternionf cameraOrientation = Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation();
      int moteCount = 7;

      for (int mote = 0; mote < 7; mote++) {
         float seed = mote * 2.173F;
         float phase = seed + time * (0.1F + mote % 3 * 0.018F);
         float y = height * (0.16F + 0.76F * (0.5F + 0.5F * Mth.sin(time * 0.075F + seed)));
         float r = radius * (1.08F + 0.24F * Mth.sin(time * 0.13F + seed * 1.7F));
         float x = Mth.cos(phase) * r;
         float z = Mth.sin(phase) * r;
         float pulse = 0.72F + 0.28F * Mth.sin(time * 0.19F + seed * 2.0F);
         float size = radius * (0.11F + mote % 3 * 0.026F) * pulse;
         float alpha = (0.19F + mote % 3 * 0.026F) * pulse;
         poseStack.pushPose();
         poseStack.translate(x, y, z);
         poseStack.mulPose(cameraOrientation);
         poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
         Matrix4f matrix = poseStack.last().pose();
         addBillboard(matrix, consumer, size, alpha);
         poseStack.popPose();
      }
   }

   private static void drawPlume(PoseStack poseStack, VertexConsumer consumer, float time, float height, float radius) {
      Quaternionf cameraOrientation = Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation();
      int plumeCount = 4;

      for (int plume = 0; plume < 4; plume++) {
         float seed = plume * 2.71F;
         float phase = seed + time * (0.045F + plume * 0.009F);
         float y = height * (0.76F + 0.38F * (0.5F + 0.5F * Mth.sin(time * 0.07F + seed)));
         float r = radius * (0.25F + 0.3F * (0.5F + 0.5F * Mth.sin(seed * 1.7F)));
         float x = Mth.cos(phase) * r;
         float z = Mth.sin(phase) * r;
         float size = radius * (0.38F + plume % 3 * 0.085F);
         float alpha = 0.14F + plume % 3 * 0.026F;
         poseStack.pushPose();
         poseStack.translate(x, y, z);
         poseStack.mulPose(cameraOrientation);
         poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
         addBillboard(poseStack.last().pose(), consumer, size, alpha, 0.075F, 0.016F, 0.115F, 0.16F, 0.036F, 0.25F);
         poseStack.popPose();
      }
   }

   public static void tickParticles(LivingEntity entity) {
      if (entity.level().isClientSide() && !entity.isInvisible() && LooterClientEvents.isShadowClient(entity)) {
         int tick = entity.tickCount;
         if (Math.floorMod(tick + entity.getId(), 4) == 0) {
            float height = Mth.clamp(entity.getBbHeight(), 0.65F, 4.0F);
            float radius = Math.max(0.28F, Mth.clamp(entity.getBbWidth(), 0.3F, 2.5F) * 0.68F);
            RandomSource random = entity.getRandom();
            int count = tick % 16 == 0 ? 2 : 1;

            for (int i = 0; i < count; i++) {
               float angle = random.nextFloat() * (float) (Math.PI * 2) + tick * 0.075F;
               double ring = radius * (0.55 + random.nextDouble() * 0.8);
               double y = entity.getY() + height * (0.22 + random.nextDouble() * 0.62);
               double x = entity.getX() + Mth.cos(angle) * ring;
               double z = entity.getZ() + Mth.sin(angle) * ring;
               double tangent = 0.018 + random.nextDouble() * 0.018;
               double vx = -Mth.sin(angle) * tangent + (random.nextDouble() - 0.5) * 0.008;
               double vy = 0.018 + random.nextDouble() * 0.018;
               double vz = Mth.cos(angle) * tangent + (random.nextDouble() - 0.5) * 0.008;
               entity.level().addParticle(i == 0 && tick % 6 == 0 ? DARK_AURA_DEEP : DARK_AURA, x, y, z, vx, vy, vz);
            }

            if ((tick + entity.getId()) % 16 == 0) {
               double angle = random.nextFloat() * (float) (Math.PI * 2);
               double ring = radius * (random.nextDouble() * 0.55);
               double x = entity.getX() + Mth.cos((float)angle) * ring;
               double z = entity.getZ() + Mth.sin((float)angle) * ring;
               double y = entity.getY() + height * (0.78 + random.nextDouble() * 0.24);
               entity.level().addParticle(DARK_AURA_DEEP, x, y, z, (random.nextDouble() - 0.5) * 0.012, 0.024, (random.nextDouble() - 0.5) * 0.012);
            }

            if ((tick + entity.getId()) % 24 == 0) {
               double angle = random.nextFloat() * (float) (Math.PI * 2);
               double ring = radius * (0.75 + random.nextDouble() * 0.85);
               double x = entity.getX() + Mth.cos((float)angle) * ring;
               double z = entity.getZ() + Mth.sin((float)angle) * ring;
               double y = entity.getY() + height * (0.3 + random.nextDouble() * 0.62);
               entity.level().addParticle(DARK_AURA_PURPLE, x, y, z, -Mth.sin((float)angle) * 0.025, 0.026, Mth.cos((float)angle) * 0.025);
            }
         }
      }
   }

   private static void addRibbonQuad(
      Matrix4f matrix,
      VertexConsumer consumer,
      float x0,
      float y0,
      float z0,
      float x1,
      float y1,
      float z1,
      float x2,
      float y2,
      float z2,
      float x3,
      float y3,
      float z3,
      float alpha0,
      float alpha1
   ) {
      consumer.addVertex(matrix, x0, y0, z0).setColor(0.1F, 0.022F, 0.15F, alpha0);
      consumer.addVertex(matrix, x1, y0, z1).setColor(0.18F, 0.04F, 0.27F, alpha0 * 0.92F);
      consumer.addVertex(matrix, x2, y1, z2).setColor(0.18F, 0.04F, 0.27F, alpha1 * 0.92F);
      consumer.addVertex(matrix, x3, y1, z3).setColor(0.1F, 0.022F, 0.15F, alpha1);
   }

   private static void addBillboard(Matrix4f matrix, VertexConsumer consumer, float size, float alpha) {
      addBillboard(matrix, consumer, size, alpha, 0.07F, 0.015F, 0.1F, 0.15F, 0.034F, 0.22F);
   }

   private static void addBillboard(
      Matrix4f matrix,
      VertexConsumer consumer,
      float size,
      float alpha,
      float outerRed,
      float outerGreen,
      float outerBlue,
      float innerRed,
      float innerGreen,
      float innerBlue
   ) {
      float edge = size;
      float middle = size * 0.72F;
      float inner = size * 0.42F;
      consumer.addVertex(matrix, -edge, -edge, 0.0F).setColor(outerRed, outerGreen, outerBlue, alpha * 0.3F);
      consumer.addVertex(matrix, edge, -edge, 0.0F).setColor(outerRed, outerGreen, outerBlue, alpha * 0.3F);
      consumer.addVertex(matrix, edge, edge, 0.0F).setColor(outerRed, outerGreen, outerBlue, alpha * 0.3F);
      consumer.addVertex(matrix, -edge, edge, 0.0F).setColor(outerRed, outerGreen, outerBlue, alpha * 0.3F);
      consumer.addVertex(matrix, -middle, -middle, -0.001F).setColor(outerRed * 1.12F, outerGreen * 1.08F, outerBlue * 1.12F, alpha * 0.52F);
      consumer.addVertex(matrix, middle, -middle, -0.001F).setColor(outerRed * 1.12F, outerGreen * 1.08F, outerBlue * 1.12F, alpha * 0.52F);
      consumer.addVertex(matrix, middle, middle, -0.001F).setColor(outerRed * 1.12F, outerGreen * 1.08F, outerBlue * 1.12F, alpha * 0.52F);
      consumer.addVertex(matrix, -middle, middle, -0.001F).setColor(outerRed * 1.12F, outerGreen * 1.08F, outerBlue * 1.12F, alpha * 0.52F);
      consumer.addVertex(matrix, -inner, -inner, -0.002F).setColor(innerRed, innerGreen, innerBlue, alpha);
      consumer.addVertex(matrix, inner, -inner, -0.002F).setColor(innerRed, innerGreen, innerBlue, alpha);
      consumer.addVertex(matrix, inner, inner, -0.002F).setColor(innerRed, innerGreen, innerBlue, alpha);
      consumer.addVertex(matrix, -inner, inner, -0.002F).setColor(innerRed, innerGreen, innerBlue, alpha);
   }
}
