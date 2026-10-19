package com.foxablazeultimate.client.render;

import com.foxablazeultimate.entity.YogTimeStopWaveEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

public final class YogTimeStopWaveRenderer extends EntityRenderer<YogTimeStopWaveEntity> {
   private static final int LATITUDE_SEGMENTS = 16;
   private static final int LONGITUDE_SEGMENTS = 24;
   private static final float RED = 0.19F;
   private static final float GREEN = 0.02F;
   private static final float BLUE = 0.36F;
   private static final float MAX_ALPHA = 0.7F;

   public YogTimeStopWaveRenderer(Context context) {
      super(context);
      this.shadowRadius = 0.0F;
   }

   public void render(YogTimeStopWaveEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
      float progress = Mth.clamp((entity.tickCount + partialTick) / 10.0F, 0.0F, 1.0F);
      float eased = progress * progress * (3.0F - 2.0F * progress);
      float radius = 32.0F * (entity.isContracting() ? 1.0F - eased : eased);
      float alpha = 0.7F * (1.0F - eased);
      if (radius > 0.01F && alpha > 0.001F) {
         renderSphere(poseStack.last().pose(), buffer.getBuffer(RenderType.debugQuads()), radius, alpha);
      }

      super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
   }

   private static void renderSphere(Matrix4f matrix, VertexConsumer consumer, float radius, float alpha) {
      for (int latitude = 0; latitude < 16; latitude++) {
         double lower = (-Math.PI / 2) + Math.PI * latitude / 16.0;
         double upper = (-Math.PI / 2) + Math.PI * (latitude + 1) / 16.0;

         for (int longitude = 0; longitude < 24; longitude++) {
            double start = (Math.PI * 2) * longitude / 24.0;
            double end = (Math.PI * 2) * (longitude + 1) / 24.0;
            Vec3 lowerStart = spherePoint(radius, lower, start);
            Vec3 lowerEnd = spherePoint(radius, lower, end);
            Vec3 upperEnd = spherePoint(radius, upper, end);
            Vec3 upperStart = spherePoint(radius, upper, start);
            vertex(consumer, matrix, lowerStart, alpha);
            vertex(consumer, matrix, lowerEnd, alpha);
            vertex(consumer, matrix, upperEnd, alpha);
            vertex(consumer, matrix, upperStart, alpha);
         }
      }
   }

   private static Vec3 spherePoint(float radius, double latitude, double longitude) {
      double horizontal = Math.cos(latitude) * radius;
      return new Vec3(horizontal * Math.cos(longitude), Math.sin(latitude) * radius, horizontal * Math.sin(longitude));
   }

   private static void vertex(VertexConsumer consumer, Matrix4f matrix, Vec3 position, float alpha) {
      consumer.addVertex(matrix, (float)position.x, (float)position.y, (float)position.z).setColor(0.19F, 0.02F, 0.36F, alpha);
   }

   public ResourceLocation getTextureLocation(YogTimeStopWaveEntity entity) {
      return TextureAtlas.LOCATION_BLOCKS;
   }
}
