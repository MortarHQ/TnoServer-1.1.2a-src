package com.foxablazeultimate.client.render;

import com.foxablazeultimate.entity.HellSniperBeamEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

public final class HellSniperBeamRenderer extends EntityRenderer<HellSniperBeamEntity> {
   private static final double EPSILON_SQUARED = 1.0E-8;
   private static final double MAX_VALID_FRAME_STEP_SQUARED = 64.0;
   private static final double BODY_REAR = 0.0;
   private static final double BODY_FRONT = 1.15;
   private static final double TIP_FRONT = 1.48;
   private static final double CORE_RADIUS = 0.018;
   private static final double GLOW_RADIUS = 0.052;
   private static final double MIN_TRAIL_LENGTH = 0.45;
   private static final double MAX_TRAIL_LENGTH = 3.0;
   private static final int TRAIL_SEGMENTS = 4;

   public HellSniperBeamRenderer(Context context) {
      super(context);
      this.shadowRadius = 0.0F;
   }

   protected int getBlockLightLevel(HellSniperBeamEntity entity, BlockPos blockPos) {
      return 15;
   }

   public void render(HellSniperBeamEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
      HellSniperBeamRenderer.MotionFrame motion = resolveMotion(entity);
      if (motion != null) {
         Vec3 direction = motion.direction();
         Vec3 reference = Math.abs(direction.y) < 0.9 ? new Vec3(0.0, 1.0, 0.0) : new Vec3(1.0, 0.0, 0.0);
         Vec3 side = direction.cross(reference).normalize();
         Vec3 up = direction.cross(side).normalize();
         Matrix4f matrix = poseStack.last().pose();
         VertexConsumer consumer = buffer.getBuffer(RenderType.lightning());
         Vec3 rear = direction.scale(0.0);
         Vec3 front = direction.scale(1.15);
         Vec3 tip = direction.scale(1.48);
         renderTrail(consumer, matrix, rear, direction, side, up, motion.trailLength());
         emitPrism(consumer, matrix, rear, front, side, up, 0.018, 0.018, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
         emitPrism(consumer, matrix, front, tip, side, up, 0.018, 0.0, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
         emitPrism(consumer, matrix, rear, front, side, up, 0.052, 0.037439999999999994, 1.0F, 1.0F, 1.0F, 0.16F, 0.1F);
         emitPrism(consumer, matrix, front, tip, side, up, 0.037439999999999994, 0.0, 1.0F, 1.0F, 1.0F, 0.1F, 0.0F);
         super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
      }
   }

   private static void renderTrail(VertexConsumer consumer, Matrix4f matrix, Vec3 rear, Vec3 direction, Vec3 side, Vec3 up, double trailLength) {
      for (int segment = 0; segment < 4; segment++) {
         double startProgress = segment / 4.0;
         double endProgress = (segment + 1) / 4.0;
         Vec3 start = rear.subtract(direction.scale(trailLength * startProgress));
         Vec3 end = rear.subtract(direction.scale(trailLength * endProgress));
         double startFade = 1.0 - startProgress;
         double endFade = 1.0 - endProgress;
         double startRadius = 0.03536 * startFade;
         double endRadius = 0.03536 * endFade;
         float startAlpha = (float)(0.18 * startFade * startFade);
         float endAlpha = (float)(0.18 * endFade * endFade);
         emitPrism(consumer, matrix, start, end, side, up, startRadius, endRadius, 1.0F, 1.0F, 1.0F, startAlpha, endAlpha);
      }
   }

   private static HellSniperBeamRenderer.MotionFrame resolveMotion(HellSniperBeamEntity entity) {
      Vec3 current = entity.position();
      Vec3 frameStep = current.subtract(new Vec3(entity.xo, entity.yo, entity.zo));
      Vec3 velocity = entity.getDeltaMovement();
      Vec3 motion = velocity;
      if (!isUsableMotion(motion)) {
         Vec3 collide = new Vec3(entity.getCollideX(), entity.getCollideY(), entity.getCollideZ());
         motion = collide.subtract(current);
      }

      if (!isUsableMotion(motion) && isUsableFrameStep(frameStep)) {
         motion = frameStep;
      }

      if (!isUsableMotion(motion)) {
         motion = entity.getLookAngle();
      }

      if (!isUsableMotion(motion)) {
         return null;
      }

      double actualStep = isUsableMotion(velocity) ? velocity.length() : motion.length();
      double trailLength = Mth.clamp(actualStep, 0.45, 3.0);
      return new HellSniperBeamRenderer.MotionFrame(motion.normalize(), trailLength);
   }

   private static boolean isUsableMotion(Vec3 motion) {
      return motion != null && Double.isFinite(motion.x) && Double.isFinite(motion.y) && Double.isFinite(motion.z) ? motion.lengthSqr() > 1.0E-8 : false;
   }

   private static boolean isUsableFrameStep(Vec3 step) {
      double lengthSquared = step.lengthSqr();
      return lengthSquared > 1.0E-8 && lengthSquared <= 64.0;
   }

   private static void emitPrism(
      VertexConsumer consumer,
      Matrix4f matrix,
      Vec3 start,
      Vec3 end,
      Vec3 side,
      Vec3 up,
      double startRadius,
      double endRadius,
      float red,
      float green,
      float blue,
      float startAlpha,
      float endAlpha
   ) {
      Vec3[] startRing = ring(start, side, up, startRadius);
      Vec3[] endRing = ring(end, side, up, endRadius);

      for (int index = 0; index < startRing.length; index++) {
         int next = (index + 1) % startRing.length;
         emitDoubleSidedQuad(
            consumer, matrix, startRing[index], startRing[next], endRing[next], endRing[index], red, green, blue, startAlpha, startAlpha, endAlpha, endAlpha
         );
      }

      emitDoubleSidedQuad(
         consumer, matrix, startRing[3], startRing[2], startRing[1], startRing[0], red, green, blue, startAlpha, startAlpha, startAlpha, startAlpha
      );
      emitDoubleSidedQuad(consumer, matrix, endRing[0], endRing[1], endRing[2], endRing[3], red, green, blue, endAlpha, endAlpha, endAlpha, endAlpha);
   }

   private static Vec3[] ring(Vec3 centre, Vec3 side, Vec3 up, double radius) {
      return new Vec3[]{centre.add(side.scale(radius)), centre.add(up.scale(radius)), centre.subtract(side.scale(radius)), centre.subtract(up.scale(radius))};
   }

   private static void emitDoubleSidedQuad(
      VertexConsumer consumer,
      Matrix4f matrix,
      Vec3 first,
      Vec3 second,
      Vec3 third,
      Vec3 fourth,
      float red,
      float green,
      float blue,
      float firstAlpha,
      float secondAlpha,
      float thirdAlpha,
      float fourthAlpha
   ) {
      emitVertex(consumer, matrix, first, red, green, blue, firstAlpha);
      emitVertex(consumer, matrix, second, red, green, blue, secondAlpha);
      emitVertex(consumer, matrix, third, red, green, blue, thirdAlpha);
      emitVertex(consumer, matrix, fourth, red, green, blue, fourthAlpha);
      emitVertex(consumer, matrix, fourth, red, green, blue, fourthAlpha);
      emitVertex(consumer, matrix, third, red, green, blue, thirdAlpha);
      emitVertex(consumer, matrix, second, red, green, blue, secondAlpha);
      emitVertex(consumer, matrix, first, red, green, blue, firstAlpha);
   }

   private static void emitVertex(VertexConsumer consumer, Matrix4f matrix, Vec3 position, float red, float green, float blue, float alpha) {
      consumer.addVertex(matrix, (float)position.x, (float)position.y, (float)position.z).setColor(red, green, blue, alpha);
   }

   public boolean shouldRender(HellSniperBeamEntity entity, Frustum frustum, double cameraX, double cameraY, double cameraZ) {
      AABB renderBounds = entity.getBoundingBoxForCulling().inflate(4.532);
      return entity.noCulling || frustum.isVisible(renderBounds);
   }

   public ResourceLocation getTextureLocation(HellSniperBeamEntity entity) {
      return TextureAtlas.LOCATION_BLOCKS;
   }

   private record MotionFrame(Vec3 direction, double trailLength) {
   }
}
