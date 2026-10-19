package com.tno_tensura_extras.client;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import com.mojang.math.Axis;
import com.tno_tensura_extras.entity.LooterBlackHoleEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderType.CompositeState;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.joml.Matrix4f;

public final class LooterBlackHoleRenderer extends EntityRenderer<LooterBlackHoleEntity> {
   private static final ResourceLocation WHITE_TEXTURE = ResourceLocation.fromNamespaceAndPath("minecraft", "textures/misc/white.png");
   private static final RenderType DISK = RenderType.create(
      "tno_looter_black_hole_disk",
      DefaultVertexFormat.POSITION_COLOR,
      Mode.TRIANGLES,
      4096,
      false,
      false,
      CompositeState.builder()
         .setShaderState(RenderStateShard.POSITION_COLOR_SHADER)
         .setTransparencyState(RenderStateShard.TRANSLUCENT_TRANSPARENCY)
         .setDepthTestState(RenderStateShard.LEQUAL_DEPTH_TEST)
         .setCullState(RenderStateShard.NO_CULL)
         .setWriteMaskState(RenderStateShard.COLOR_WRITE)
         .createCompositeState(false)
   );
   private static final RenderType RING = RenderType.create(
      "tno_looter_black_hole_ring",
      DefaultVertexFormat.POSITION_COLOR,
      Mode.QUADS,
      4096,
      false,
      false,
      CompositeState.builder()
         .setShaderState(RenderStateShard.POSITION_COLOR_SHADER)
         .setTransparencyState(RenderStateShard.TRANSLUCENT_TRANSPARENCY)
         .setDepthTestState(RenderStateShard.LEQUAL_DEPTH_TEST)
         .setCullState(RenderStateShard.NO_CULL)
         .setWriteMaskState(RenderStateShard.COLOR_WRITE)
         .createCompositeState(false)
   );
   private static final RenderType STREAK = RenderType.create(
      "tno_looter_black_hole_streak",
      DefaultVertexFormat.POSITION_COLOR,
      Mode.QUADS,
      2048,
      false,
      false,
      CompositeState.builder()
         .setShaderState(RenderStateShard.POSITION_COLOR_SHADER)
         .setTransparencyState(RenderStateShard.TRANSLUCENT_TRANSPARENCY)
         .setDepthTestState(RenderStateShard.LEQUAL_DEPTH_TEST)
         .setCullState(RenderStateShard.NO_CULL)
         .setWriteMaskState(RenderStateShard.COLOR_WRITE)
         .createCompositeState(false)
   );

   public LooterBlackHoleRenderer(Context context) {
      super(context);
      this.shadowRadius = 0.0F;
   }

   public void render(LooterBlackHoleEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
      float progress = entity.getAnimationProgress(partialTick);
      if (!(progress <= 0.001F)) {
         float time = entity.tickCount + partialTick;
         float pulse = 0.94F + Mth.sin(time * 0.16F) * 0.06F;
         float radius = entity.getRadius() * progress * pulse;
         poseStack.pushPose();
         poseStack.translate(0.0, 0.025, 0.0);
         poseStack.mulPose(Axis.YP.rotationDegrees(time * 4.5F));
         poseStack.mulPose(Axis.XP.rotationDegrees(-90.0F));
         VertexConsumer disk = buffer.getBuffer(DISK);
         drawGradientDisk(poseStack, disk, radius * 1.12F, 64, 0.24F * progress, 0.03F * progress, 0.1F, 0.01F, 0.16F);
         drawGradientDisk(poseStack, disk, radius, 64, 0.97F * progress, 0.82F * progress, 0.005F, 0.002F, 0.012F);
         drawGradientDisk(poseStack, disk, radius * 0.74F, 48, 0.99F * progress, 0.94F * progress, 0.0F, 0.0F, 0.002F);
         VertexConsumer rings = buffer.getBuffer(RING);
         drawRing(poseStack, rings, radius * 1.09F, 96, time * 0.075F, 0.72F * progress, 0.2F, 0.015F, 0.3F, 0.009F);
         drawRing(poseStack, rings, radius * 0.88F, 80, -time * 0.12F, 0.46F * progress, 0.34F, 0.02F, 0.46F, 0.007F);
         drawArc(poseStack, rings, radius * 0.98F, 72, time * 0.2F, 0.42F * progress, 0.46F, 0.025F, 0.58F, 0.012F);
         drawArc(poseStack, rings, radius * 0.66F, 56, -time * 0.26F, 0.34F * progress, 0.25F, 0.01F, 0.38F, 0.014F);
         VertexConsumer streaks = buffer.getBuffer(STREAK);
         drawStreaks(poseStack, streaks, radius, time, progress);
         poseStack.popPose();
      }
   }

   private static void drawGradientDisk(
      PoseStack poseStack,
      VertexConsumer consumer,
      float radius,
      int segments,
      float centerAlpha,
      float edgeAlpha,
      float edgeRed,
      float edgeGreen,
      float edgeBlue
   ) {
      Matrix4f matrix = poseStack.last().pose();

      for (int i = 0; i < segments; i++) {
         double angle0 = (Math.PI * 2) * i / segments;
         double angle1 = (Math.PI * 2) * (i + 1) / segments;
         float x0 = (float)Math.cos(angle0) * radius;
         float y0 = (float)Math.sin(angle0) * radius;
         float x1 = (float)Math.cos(angle1) * radius;
         float y1 = (float)Math.sin(angle1) * radius;
         consumer.addVertex(matrix, 0.0F, 0.0F, 0.0F).setColor(0.0F, 0.0F, 0.0F, centerAlpha);
         consumer.addVertex(matrix, x0, y0, 0.0F).setColor(edgeRed, edgeGreen, edgeBlue, edgeAlpha);
         consumer.addVertex(matrix, x1, y1, 0.0F).setColor(edgeRed, edgeGreen, edgeBlue, edgeAlpha);
      }
   }

   private static void drawRing(
      PoseStack poseStack, VertexConsumer consumer, float radius, int segments, float rotation, float alpha, float red, float green, float blue, float z
   ) {
      Matrix4f matrix = poseStack.last().pose();
      float inner = radius * 0.91F;

      for (int i = 0; i < segments; i++) {
         double a0 = rotation + (Math.PI * 2) * i / segments;
         double a1 = rotation + (Math.PI * 2) * (i + 1) / segments;
         addQuad(
            matrix,
            consumer,
            (float)Math.cos(a0) * inner,
            (float)Math.sin(a0) * inner,
            (float)Math.cos(a0) * radius,
            (float)Math.sin(a0) * radius,
            (float)Math.cos(a1) * radius,
            (float)Math.sin(a1) * radius,
            (float)Math.cos(a1) * inner,
            (float)Math.sin(a1) * inner,
            z,
            red,
            green,
            blue,
            alpha
         );
      }
   }

   private static void drawArc(
      PoseStack poseStack, VertexConsumer consumer, float radius, int segments, float rotation, float alpha, float red, float green, float blue, float z
   ) {
      Matrix4f matrix = poseStack.last().pose();
      float inner = radius * 0.9F;

      for (int i = 0; i < segments; i++) {
         if (i / 4 % 2 != 1) {
            double a0 = rotation + (Math.PI * 2) * i / segments;
            double a1 = rotation + (Math.PI * 2) * (i + 1) / segments;
            addQuad(
               matrix,
               consumer,
               (float)Math.cos(a0) * inner,
               (float)Math.sin(a0) * inner,
               (float)Math.cos(a0) * radius,
               (float)Math.sin(a0) * radius,
               (float)Math.cos(a1) * radius,
               (float)Math.sin(a1) * radius,
               (float)Math.cos(a1) * inner,
               (float)Math.sin(a1) * inner,
               z,
               red,
               green,
               blue,
               alpha
            );
         }
      }
   }

   private static void drawStreaks(PoseStack poseStack, VertexConsumer consumer, float radius, float time, float progress) {
      Matrix4f matrix = poseStack.last().pose();

      for (int i = 0; i < 12; i++) {
         double angle = time * 0.09 + (Math.PI * 2) * i / 12.0;
         float inner = radius * (0.78F + i % 3 * 0.035F);
         float outer = radius * (1.02F + i % 2 * 0.08F);
         float width = radius * (0.012F + i % 3 * 0.004F);
         float x0 = (float)Math.cos(angle) * inner;
         float y0 = (float)Math.sin(angle) * inner;
         float x1 = (float)Math.cos(angle) * outer;
         float y1 = (float)Math.sin(angle) * outer;
         float nx = (float)(-Math.sin(angle)) * width;
         float ny = (float)Math.cos(angle) * width;
         float alpha = (0.2F + i % 4 * 0.035F) * progress;
         addQuad(matrix, consumer, x0 - nx, y0 - ny, x0 + nx, y0 + ny, x1 + nx, y1 + ny, x1 - nx, y1 - ny, -0.006F, 0.22F, 0.015F, 0.34F, alpha);
      }
   }

   private static void addQuad(
      Matrix4f matrix,
      VertexConsumer consumer,
      float x0,
      float y0,
      float x1,
      float y1,
      float x2,
      float y2,
      float x3,
      float y3,
      float z,
      float red,
      float green,
      float blue,
      float alpha
   ) {
      consumer.addVertex(matrix, x0, y0, z).setColor(red, green, blue, alpha);
      consumer.addVertex(matrix, x1, y1, z).setColor(red, green, blue, alpha);
      consumer.addVertex(matrix, x2, y2, z).setColor(red, green, blue, alpha);
      consumer.addVertex(matrix, x3, y3, z).setColor(red, green, blue, alpha);
   }

   public ResourceLocation getTextureLocation(LooterBlackHoleEntity entity) {
      return WHITE_TEXTURE;
   }
}
