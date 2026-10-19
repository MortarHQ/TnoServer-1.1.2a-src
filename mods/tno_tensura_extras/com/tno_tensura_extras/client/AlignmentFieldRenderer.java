package com.tno_tensura_extras.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.tno_tensura_extras.entity.AlignmentFieldEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.joml.Matrix4f;

public class AlignmentFieldRenderer extends EntityRenderer<AlignmentFieldEntity> {
   private static final ResourceLocation TEXTURE = ResourceLocation.withDefaultNamespace("textures/misc/white.png");
   private static final float RED = 0.98F;
   private static final float GREEN = 0.34F;
   private static final float BLUE = 0.8F;
   private static final int MIN_SEGMENTS = 32;
   private static final int MAX_SEGMENTS = 128;
   private static final float HEIGHT_MULTIPLIER = 1.5F;
   private static final int BANDS = 20;
   private static final int WISP_COUNT = 56;
   private static final float WISP_LENGTH = 0.16F;
   private static final float WISP_WIDTH = 0.09F;
   private static final float WISP_RISE_TICKS = 46.0F;

   public AlignmentFieldRenderer(Context context) {
      super(context);
   }

   public ResourceLocation getTextureLocation(AlignmentFieldEntity entity) {
      return TEXTURE;
   }

   public boolean shouldRender(AlignmentFieldEntity entity, Frustum frustum, double cameraX, double cameraY, double cameraZ) {
      return true;
   }

   public void render(AlignmentFieldEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
      float radius = entity.getRenderRadius(partialTick);
      float alpha = 0.5F * entity.getFadeProgress(partialTick);
      if (!(radius <= 0.01F) && !(alpha <= 0.002F)) {
         float halfHeight = entity.getRenderHalfHeight(partialTick) * 1.5F;
         float time = entity.tickCount + partialTick;
         int segments = Mth.clamp(Mth.ceil(radius * 4.0F), 32, 128);
         VertexConsumer consumer = bufferSource.getBuffer(ExtrasRenderTypes.ALIGNMENT_FIELD);
         Matrix4f matrix = poseStack.last().pose();
         renderWall(matrix, consumer, radius, halfHeight, alpha, time, segments);
         renderWisps(matrix, consumer, radius, halfHeight, alpha, time);
         super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
      }
   }

   private static void renderWall(Matrix4f matrix, VertexConsumer consumer, float radius, float halfHeight, float alpha, float time, int segments) {
      float height = halfHeight * 2.0F;

      for (int band = 0; band < 20; band++) {
         float v0 = band / 20.0F;
         float v1 = (band + 1) / 20.0F;
         float y0 = -halfHeight + height * v0;
         float y1 = -halfHeight + height * v1;
         float alpha0 = wallAlpha(alpha, v0, time);
         float alpha1 = wallAlpha(alpha, v1, time);

         for (int segment = 0; segment < segments; segment++) {
            double theta0 = (Math.PI * 2) * segment / segments;
            double theta1 = (Math.PI * 2) * (segment + 1) / segments;
            float x0 = (float)(Math.cos(theta0) * radius);
            float z0 = (float)(Math.sin(theta0) * radius);
            float x1 = (float)(Math.cos(theta1) * radius);
            float z1 = (float)(Math.sin(theta1) * radius);
            vertex(matrix, consumer, x0, y0, z0, alpha0);
            vertex(matrix, consumer, x0, y1, z0, alpha1);
            vertex(matrix, consumer, x1, y1, z1, alpha1);
            vertex(matrix, consumer, x0, y0, z0, alpha0);
            vertex(matrix, consumer, x1, y1, z1, alpha1);
            vertex(matrix, consumer, x1, y0, z1, alpha0);
         }
      }
   }

   private static float wallAlpha(float alpha, float v, float time) {
      float scroll = time * 0.045F;
      float coarse = Mth.sin((v * 3.0F - scroll) * (float) (Math.PI * 2)) * 0.5F + 0.5F;
      float fine = Mth.sin((v * 8.0F - scroll * 1.7F) * (float) (Math.PI * 2)) * 0.5F + 0.5F;
      float flow = 0.62F + 0.26F * coarse + 0.12F * fine;
      float rim = 1.0F + 0.55F * (float)Math.pow(Math.abs(v * 2.0F - 1.0F), 6.0);
      return alpha * flow * rim;
   }

   private static void renderWisps(Matrix4f matrix, VertexConsumer consumer, float radius, float halfHeight, float alpha, float time) {
      float height = halfHeight * 2.0F;
      float streakHeight = Math.max(0.6F, height * 0.16F);
      float streakWidth = Math.max(0.25F, radius * 0.09F);

      for (int index = 0; index < 56; index++) {
         float lane = fract(index * 0.6180339F);
         float phase = fract(index * 0.7548777F);
         float speed = 0.72F + 0.56F * fract(index * 0.324718F);
         float life = fract(phase + time / (46.0F / speed));
         float visibility = Mth.sin(life * (float) Math.PI);
         if (!(visibility <= 0.01F)) {
            float y0 = -halfHeight + height * life;
            float y1 = Math.min(halfHeight, y0 + streakHeight);
            double theta = (Math.PI * 2) * lane;
            theta += Math.sin(life * Math.PI * 2.0 + index) * 0.05;
            double halfArc = streakWidth / Math.max(radius, 0.001F) * 0.5;
            float xa = (float)(Math.cos(theta - halfArc) * radius);
            float za = (float)(Math.sin(theta - halfArc) * radius);
            float xb = (float)(Math.cos(theta + halfArc) * radius);
            float zb = (float)(Math.sin(theta + halfArc) * radius);
            float head = alpha * visibility * 1.5F;
            float tail = alpha * visibility * 0.15F;
            vertex(matrix, consumer, xa, y0, za, tail);
            vertex(matrix, consumer, xa, y1, za, head);
            vertex(matrix, consumer, xb, y1, zb, head);
            vertex(matrix, consumer, xa, y0, za, tail);
            vertex(matrix, consumer, xb, y1, zb, head);
            vertex(matrix, consumer, xb, y0, zb, tail);
         }
      }
   }

   private static void vertex(Matrix4f matrix, VertexConsumer consumer, float x, float y, float z, float alpha) {
      consumer.addVertex(matrix, x, y, z).setColor(0.98F, 0.34F, 0.8F, Mth.clamp(alpha, 0.0F, 1.0F));
   }

   private static float fract(float value) {
      return value - Mth.floor(value);
   }
}
