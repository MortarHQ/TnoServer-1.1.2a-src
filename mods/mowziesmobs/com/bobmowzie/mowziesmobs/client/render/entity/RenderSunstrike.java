package com.bobmowzie.mowziesmobs.client.render.entity;

import com.bobmowzie.mowziesmobs.client.model.tools.MathUtils;
import com.bobmowzie.mowziesmobs.client.render.MMRenderType;
import com.bobmowzie.mowziesmobs.server.entity.effects.EntitySunstrike;
import com.ilexiconn.llibrary.client.util.ClientUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class RenderSunstrike extends EntityRenderer<EntitySunstrike> {
   public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("mowziesmobs", "textures/effects/sunstrike.png");
   private static final Random RANDOMIZER = new Random(0L);
   private static final float TEXTURE_WIDTH = 256.0F;
   private static final float TEXTURE_HEIGHT = 32.0F;
   private static final float BEAM_MIN_U = 0.875F;
   private static final float BEAM_MAX_U = 1.0F;
   private static final float PIXEL_SCALE = 0.0625F;
   private static final int MAX_HEIGHT = 256;
   private static final float DRAW_FADE_IN_RATE = 2.0F;
   private static final float DRAW_FADE_IN_POINT = 0.5F;
   private static final float DRAW_OPACITY_MULTIPLER = 0.7F;
   private static final float RING_RADIUS = 1.6F;
   private static final int RING_FRAME_SIZE = 16;
   private static final int RING_FRAME_COUNT = 10;
   private static final int BREAM_FRAME_COUNT = 31;
   private static final float BEAM_DRAW_START_RADIUS = 2.0F;
   private static final float BEAM_DRAW_END_RADIUS = 0.25F;
   private static final float BEAM_STRIKE_RADIUS = 1.0F;
   private static final float LINGER_RADIUS = 1.2F;
   private static final float SCORCH_MIN_U = 0.75F;
   private static final float SCORCH_MAX_U = 0.8125F;
   private static final float SCORCH_MIN_V = 0.5F;
   private static final float SCORCH_MAX_V = 1.0F;

   public RenderSunstrike(Context mgr) {
      super(mgr);
   }

   public ResourceLocation getTextureLocation(EntitySunstrike entity) {
      return TEXTURE;
   }

   public void render(EntitySunstrike sunstrike, float entityYaw, float delta, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
      float maxY = (float)(256.0 - sunstrike.getY());
      if (!(maxY < 0.0F)) {
         RANDOMIZER.setSeed(sunstrike.getVariant());
         boolean isLingering = sunstrike.isLingering(delta);
         boolean isStriking = sunstrike.isStriking(delta);
         matrixStackIn.pushPose();
         VertexConsumer ivertexbuilder = bufferIn.getBuffer(MMRenderType.getGlowingEffect(TEXTURE));
         if (isLingering) {
            this.drawScorch(sunstrike, delta, matrixStackIn, ivertexbuilder, packedLightIn);
         } else if (isStriking) {
            this.drawStrike(sunstrike, maxY, delta, matrixStackIn, ivertexbuilder, packedLightIn);
         }

         matrixStackIn.popPose();
      }
   }

   private void drawScorch(EntitySunstrike sunstrike, float delta, PoseStack matrixStack, VertexConsumer builder, int packedLightIn) {
      Level world = sunstrike.getCommandSenderWorld();
      double ex = sunstrike.xOld + (sunstrike.getX() - sunstrike.xOld) * delta;
      double ey = sunstrike.yOld + (sunstrike.getY() - sunstrike.yOld) * delta;
      double ez = sunstrike.zOld + (sunstrike.getZ() - sunstrike.zOld) * delta;
      int minX = Mth.floor(ex - 1.2F);
      int maxX = Mth.floor(ex + 1.2F);
      int minY = Mth.floor(ey - 1.2F);
      int maxY = Mth.floor(ey);
      int minZ = Mth.floor(ez - 1.2F);
      int maxZ = Mth.floor(ez + 1.2F);
      float opacityMultiplier = (0.6F + RANDOMIZER.nextFloat() * 0.2F) * world.getMaxLocalRawBrightness(new BlockPos((int)ex, (int)ey, (int)ez));
      byte mirrorX = (byte)(RANDOMIZER.nextBoolean() ? -1 : 1);
      byte mirrorZ = (byte)(RANDOMIZER.nextBoolean() ? -1 : 1);

      for (BlockPos pos : BlockPos.betweenClosed(new BlockPos(minX, minY, minZ), new BlockPos(maxX, maxY, maxZ))) {
         BlockState block = world.getBlockState(pos.below());
         if (!block.isAir() && world.getMaxLocalRawBrightness(pos) > 3) {
            this.drawScorchBlock(world, block, pos, ex, ey, ez, opacityMultiplier, mirrorX, mirrorZ, matrixStack, builder, packedLightIn);
         }
      }
   }

   private void drawScorchBlock(
      Level world,
      BlockState block,
      BlockPos pos,
      double ex,
      double ey,
      double ez,
      float opacityMultiplier,
      byte mirrorX,
      byte mirrorZ,
      PoseStack matrixStack,
      VertexConsumer builder,
      int packedLightIn
   ) {
      Pose matrixstack$entry = matrixStack.last();
      Matrix4f matrix4f = matrixstack$entry.pose();
      Matrix3f matrix3f = matrixstack$entry.normal();
      if (block.isRedstoneConductor(world, pos)) {
         int bx = pos.getX();
         int by = pos.getY();
         int bz = pos.getZ();
         float opacity = (float)((1.0 - (ey - by) / 2.0) * opacityMultiplier);
         if (opacity >= 0.0F) {
            if (opacity > 1.0F) {
               opacity = 1.0F;
            }

            VoxelShape shape = block.getBlockSupportShape(world, pos);
            if (!shape.isEmpty()) {
               AABB aabb = shape.bounds();
               float minX = (float)(bx + aabb.minX - ex);
               float maxX = (float)(bx + aabb.maxX - ex);
               float y = (float)(by + aabb.minY - ey + 0.015625);
               float minZ = (float)(bz + aabb.minZ - ez);
               float maxZ = (float)(bz + aabb.maxZ - ez);
               float minU = (mirrorX * minX / 2.0F / 1.2F + 0.5F) * 0.0625F + 0.75F;
               float maxU = (mirrorX * maxX / 2.0F / 1.2F + 0.5F) * 0.0625F + 0.75F;
               float minV = (mirrorZ * minZ / 2.0F / 1.2F + 0.5F) * 0.5F + 0.5F;
               float maxV = (mirrorZ * maxZ / 2.0F / 1.2F + 0.5F) * 0.5F + 0.5F;
               this.drawVertex(matrix4f, matrix3f, builder, minX, y, minZ, minU, minV, opacity, packedLightIn);
               this.drawVertex(matrix4f, matrix3f, builder, minX, y, maxZ, minU, maxV, opacity, packedLightIn);
               this.drawVertex(matrix4f, matrix3f, builder, maxX, y, maxZ, maxU, maxV, opacity, packedLightIn);
               this.drawVertex(matrix4f, matrix3f, builder, maxX, y, minZ, maxU, minV, opacity, packedLightIn);
            }
         }
      }
   }

   private void drawStrike(EntitySunstrike sunstrike, float maxY, float delta, PoseStack matrixStack, VertexConsumer builder, int packedLightIn) {
      float drawTime = sunstrike.getStrikeDrawTime(delta);
      float strikeTime = sunstrike.getStrikeDamageTime(delta);
      boolean drawing = sunstrike.isStrikeDrawing(delta);
      float opacity = drawing && drawTime < 0.5F ? drawTime * 2.0F : 1.0F;
      if (drawing) {
         opacity *= 0.7F;
      }

      this.drawRing(drawing, drawTime, strikeTime, opacity, matrixStack, builder, packedLightIn);
      matrixStack.mulPose(MathUtils.quatFromRotationXYZ(0.0F, -Minecraft.getInstance().gameRenderer.getMainCamera().getYRot(), 0.0F, true));
      this.drawBeam(drawing, drawTime, strikeTime, opacity, maxY, matrixStack, builder, packedLightIn);
   }

   private void drawRing(boolean drawing, float drawTime, float strikeTime, float opacity, PoseStack matrixStack, VertexConsumer builder, int packedLightIn) {
      int frame = (int)((drawing ? drawTime : strikeTime) * 11.0F);
      if (frame > 10) {
         frame = 10;
      }

      float minU = frame * 16 / 256.0F;
      float maxU = minU + 0.0625F;
      float minV = drawing ? 0.0F : 0.5F;
      float maxV = minV + 0.5F;
      float offset = 0.1F * (frame % 2);
      Pose matrixstack$entry = matrixStack.last();
      Matrix4f matrix4f = matrixstack$entry.pose();
      Matrix3f matrix3f = matrixstack$entry.normal();
      this.drawVertex(matrix4f, matrix3f, builder, -1.6F + offset, 0.0F, -1.6F + offset, minU, minV, opacity, packedLightIn);
      this.drawVertex(matrix4f, matrix3f, builder, -1.6F + offset, 0.0F, 1.6F + offset, minU, maxV, opacity, packedLightIn);
      this.drawVertex(matrix4f, matrix3f, builder, 1.6F + offset, 0.0F, 1.6F + offset, maxU, maxV, opacity, packedLightIn);
      this.drawVertex(matrix4f, matrix3f, builder, 1.6F + offset, 0.0F, -1.6F + offset, maxU, minV, opacity, packedLightIn);
   }

   private void drawBeam(
      boolean drawing, float drawTime, float strikeTime, float opacity, float maxY, PoseStack matrixStack, VertexConsumer builder, int packedLightIn
   ) {
      int frame = drawing ? 0 : (int)(strikeTime * 32.0F);
      if (frame > 31) {
         frame = 31;
      }

      float radius = 1.0F;
      if (drawing) {
         radius = -1.75F * drawTime + 2.0F;
      }

      float minV = frame / 32.0F;
      float maxV = (frame + 1) / 32.0F;
      Pose matrixstack$entry = matrixStack.last();
      Matrix4f matrix4f = matrixstack$entry.pose();
      Matrix3f matrix3f = matrixstack$entry.normal();
      this.drawVertex(matrix4f, matrix3f, builder, -radius, 0.0F, 0.0F, 0.875F, minV, opacity, packedLightIn);
      this.drawVertex(matrix4f, matrix3f, builder, -radius, maxY, 0.0F, 0.875F, maxV, opacity, packedLightIn);
      this.drawVertex(matrix4f, matrix3f, builder, radius, maxY, 0.0F, 1.0F, maxV, opacity, packedLightIn);
      this.drawVertex(matrix4f, matrix3f, builder, radius, 0.0F, 0.0F, 1.0F, minV, opacity, packedLightIn);
   }

   public void drawVertex(
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
      VertexConsumer vertex = vertexBuilder.addVertex(matrix, offsetX, offsetY, offsetZ)
         .setColor(1.0F, 1.0F, 1.0F, 1.0F * alpha)
         .setUv(textureX, textureY)
         .setOverlay(OverlayTexture.NO_OVERLAY)
         .setLight(packedLightIn);
      ClientUtils.transformNormals(vertex, normals, 1.0F, 0.0F, 1.0F);
   }
}
