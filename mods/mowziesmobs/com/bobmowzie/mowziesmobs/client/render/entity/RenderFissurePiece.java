package com.bobmowzie.mowziesmobs.client.render.entity;

import com.bobmowzie.mowziesmobs.MMCommon;
import com.bobmowzie.mowziesmobs.server.entity.effects.geomancy.EntityFissure;
import com.bobmowzie.mowziesmobs.server.entity.effects.geomancy.EntityFissurePiece;
import com.ilexiconn.llibrary.client.util.ClientUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import java.util.Arrays;
import java.util.Optional;
import java.util.OptionalDouble;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class RenderFissurePiece extends EntityRenderer<EntityFissurePiece> {
   private static final ResourceLocation TEXTURE0 = MMCommon.resource("textures/particle/crack_0.png");
   private static final ResourceLocation TEXTURE1 = MMCommon.resource("textures/particle/crack_1.png");
   private static final ResourceLocation TEXTURE2 = MMCommon.resource("textures/particle/crack_2.png");
   private static final ResourceLocation TEXTURE3 = MMCommon.resource("textures/particle/crack_3.png");
   private static final ResourceLocation TEXTURE4 = MMCommon.resource("textures/particle/crack_4.png");
   private static final ResourceLocation TEXTURE5 = MMCommon.resource("textures/particle/crack_5.png");
   private static final ResourceLocation[] TEXTURES = new ResourceLocation[]{TEXTURE0, TEXTURE1, TEXTURE2, TEXTURE3, TEXTURE4, TEXTURE5};
   private static final float SPRITE_SCALE = 2.0F;
   private static final double DISTANCE_THRESHOLD = 1.0E-4;

   public RenderFissurePiece(Context mgr) {
      super(mgr);
   }

   public ResourceLocation getTextureLocation(EntityFissurePiece entity) {
      int fullGrownTick = EntityFissure.TICKS_PER_PIECE;
      if (entity.getGrowTick() < fullGrownTick) {
         int whichTex = (int)(5.0 * entity.getGrowTick() / fullGrownTick);
         return TEXTURES[whichTex];
      } else {
         return TEXTURE5;
      }
   }

   private static OptionalDouble max(double... v) {
      return Arrays.stream(v).max();
   }

   public void render(EntityFissurePiece entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
      Vec3 corner0 = new Vec3(-1.0, 0.0, -1.0).yRot(entityYaw);
      Vec3 corner1 = new Vec3(1.0, 0.0, 1.0).yRot(entityYaw);
      double extent = max(corner0.x(), corner1.x(), corner0.z(), corner1.z()).orElse(1.0);
      Vec3 minCorner = new Vec3(-extent, -1.0, -extent).add(entityIn.getX(), entityIn.getY(), entityIn.getZ());
      Vec3 maxCorner = new Vec3(extent, 1.0, extent).add(entityIn.getX(), entityIn.getY(), entityIn.getZ());
      matrixStackIn.pushPose();
      VertexConsumer ivertexbuilder = bufferIn.getBuffer(RenderType.entityTranslucent(this.getTextureLocation(entityIn)));

      for (BlockPos blockpos : BlockPos.betweenClosed(BlockPos.containing(minCorner), BlockPos.containing(maxCorner))) {
         BlockState block = entityIn.level().getBlockState(blockpos.below());
         renderBlockDecal(
            entityIn, entityIn.level(), block, blockpos, entityIn.getX(), entityIn.getY(), entityIn.getZ(), matrixStackIn, ivertexbuilder, packedLightIn
         );
      }

      matrixStackIn.popPose();
   }

   private static Vec2 rotateVec2(Vec2 v, float angle) {
      return new Vec2(v.x * (float)Math.cos(angle) - v.y * (float)Math.sin(angle), v.x * (float)Math.sin(angle) + v.y * (float)Math.cos(angle));
   }

   public static Vec3 Vec3WithAxis(double axis1, double axis2, Axis axis1Dir) {
      Vec3 v = new Vec3(0.0, 0.0, 0.0);
      if (axis1Dir == Axis.X) {
         v.add(axis1, 0.0, axis2);
      } else {
         v.add(axis2, 0.0, axis1);
      }

      return v;
   }

   public static Optional<Vec2> getLineSegmentIntersection(BlockPos pos, Direction whichBorder, Vec3 segmentP, Vec3 segmentQ) {
      if (whichBorder.getAxis().isVertical()) {
         return Optional.empty();
      }

      Axis axis = whichBorder.getClockWise().getAxis();
      Vec3 segmentVec = segmentQ.subtract(segmentP);
      Axis perp = whichBorder.getAxis();
      if (segmentVec.get(perp) == 0.0) {
         return Optional.empty();
      }

      whichBorder.step();
      Vec3 colliderCenter = pos.getCenter().add(whichBorder.getStepX() / 2.0, whichBorder.getStepY() / 2.0, whichBorder.getStepZ() / 2.0);
      double segmentPos = colliderCenter.get(perp);
      Vec3 intersect;
      if (segmentVec.get(axis) == 0.0) {
         intersect = Vec3WithAxis(segmentP.get(axis), segmentPos, axis);
      } else {
         double slope = segmentVec.get(perp) / segmentVec.get(axis);
         double intercept = segmentP.get(perp) - slope * segmentP.get(axis);
         double intersectAxis = (segmentPos - intercept) / slope;
         intersect = Vec3WithAxis(intersectAxis, segmentPos, axis);
      }

      double distanceFromBall = intersect.subtract(segmentP).length();
      if (distanceFromBall <= 1.0E-4) {
         return Optional.empty();
      }

      if (Math.abs(intersect.get(axis) - colliderCenter.get(axis)) > 0.5) {
         return Optional.empty();
      }

      double dot = intersect.subtract(segmentP).dot(segmentVec);
      return !(dot < 0.0) && !(dot > segmentVec.dot(segmentVec)) ? Optional.of(new Vec2((float)intersect.x, (float)intersect.z)) : Optional.empty();
   }

   public static Vec2 getRelativeCornerPos(Vec2 corner, float rotation) {
      return rotateVec2(corner, (float)(-Math.toRadians(rotation + 180.0F)));
   }

   public static Vec2 getRelativeUVs(Vec2 corner, float rotation) {
      Vec2 relativeCorner = rotateVec2(corner, (float)(-Math.toRadians(rotation + 180.0F)));
      return new Vec2(relativeCorner.x / 4.0F + 0.5F, relativeCorner.y / 4.0F + 0.5F);
   }

   private static void renderBlockDecal(
      EntityFissurePiece entity,
      Level level,
      BlockState blockstate,
      BlockPos blockpos,
      double x,
      double y,
      double z,
      PoseStack matrixStack,
      VertexConsumer builder,
      int packedLightIn
   ) {
      Pose matrixstack$entry = matrixStack.last();
      Matrix4f matrix4f = matrixstack$entry.pose();
      Matrix3f matrix3f = matrixstack$entry.normal();
      new Vec2((float)x, (float)z);
      double ex = entity.xOld + (entity.getX() - entity.xOld);
      double ey = entity.yOld + (entity.getY() - entity.yOld);
      double ez = entity.zOld + (entity.getZ() - entity.zOld);
      if (blockstate.getRenderShape() != RenderShape.INVISIBLE
         && blockstate.isCollisionShapeFullBlock(level, blockpos)
         && !level.getBlockState(blockpos).isCollisionShapeFullBlock(level, blockpos)) {
         VoxelShape voxelshape = blockstate.getShape(level, blockpos);
         if (!voxelshape.isEmpty()) {
            float alpha = 1.0F;
            if (alpha >= 0.0F) {
               if (alpha > 1.0F) {
                  alpha = 1.0F;
               }

               double rad2 = Math.sqrt(1.0);
               double minX = -2.0 * rad2;
               double minZ = -2.0 * rad2;
               double maxX = 2.0 * rad2;
               double maxZ = 2.0 * rad2;
               AABB aabb = voxelshape.bounds();
               float d0 = blockpos.getX() + (float)(aabb.minX - ex);
               float d1 = blockpos.getX() + (float)(aabb.maxX - ex);
               float d2 = blockpos.getY() + (float)(aabb.minY - ey) + 0.0015625F;
               float d3 = blockpos.getZ() + (float)(aabb.minZ - ez);
               float d4 = blockpos.getZ() + (float)(aabb.maxZ - ez);
               if (d0 < minX) {
                  d0 = (float)minX;
               }

               if (d1 > maxX) {
                  d1 = (float)maxX;
               }

               if (d3 < minZ) {
                  d3 = (float)minZ;
               }

               if (d4 > maxZ) {
                  d4 = (float)maxZ;
               }

               Vec2[] corners = new Vec2[]{new Vec2(d0, d3), new Vec2(d1, d3), new Vec2(d1, d4), new Vec2(d0, d4)};

               for (Vec2 corner : corners) {
                  Vec2 uv = getRelativeUVs(corner, entity.getYRot());
                  drawVertex(matrix4f, matrix3f, builder, corner.x, d2, corner.y, uv.x, uv.y, 0.65F, packedLightIn);
               }

               Direction[] directions = new Direction[]{Direction.WEST, Direction.NORTH, Direction.EAST, Direction.SOUTH};

               for (int i = 0; i < corners.length; i++) {
                  Direction direction = directions[i];
                  BlockPos overAndDown = blockpos.relative(direction).below(2);
                  if (level.getBlockState(overAndDown).isCollisionShapeFullBlock(level, overAndDown)) {
                     Vec2 corner = corners[i];
                     Vec2 uv = getRelativeUVs(corner, entity.getYRot());
                     int prevIndex = i == 0 ? 3 : i - 1;
                     Vec2 prevCorner = corners[prevIndex];
                     Vec2 prevUv = getRelativeUVs(prevCorner, entity.getYRot());
                     Vector3f offset = direction.step().mul(0.0015625F);
                     drawVertex(matrix4f, matrix3f, builder, prevCorner.x + offset.x(), d2, prevCorner.y + offset.z(), prevUv.x, prevUv.y, 0.65F, packedLightIn);
                     drawVertex(matrix4f, matrix3f, builder, corner.x + offset.x(), d2, corner.y + offset.z(), uv.x, uv.y, 0.65F, packedLightIn);
                     drawVertex(matrix4f, matrix3f, builder, corner.x + offset.x(), d2 - 1.0F, corner.y + offset.z(), uv.x, uv.y, 0.65F, packedLightIn);
                     drawVertex(
                        matrix4f, matrix3f, builder, prevCorner.x + offset.x(), d2 - 1.0F, prevCorner.y + offset.z(), prevUv.x, prevUv.y, 0.65F, packedLightIn
                     );
                  }
               }
            }
         }
      }
   }

   public static void drawVertex(
      Matrix4f matrix,
      Matrix3f normals,
      VertexConsumer vertexBuilder,
      float offsetX,
      float offsetY,
      float offsetZ,
      float textureX,
      float textureY,
      float alpha,
      int packedLightIn
   ) {
      VertexConsumer vertexConsumer = vertexBuilder.addVertex(matrix, offsetX, offsetY, offsetZ)
         .setColor(0.0F, 0.0F, 0.0F, 1.0F * alpha)
         .setUv(textureX, textureY)
         .setOverlay(OverlayTexture.NO_OVERLAY)
         .setLight(packedLightIn);
      ClientUtils.transformNormals(vertexConsumer, normals, 0.0F, 1.0F, 0.0F);
   }
}
