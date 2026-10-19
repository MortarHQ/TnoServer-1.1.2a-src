package com.github.wal_bos.moreskills.client.renderer;

import com.github.wal_bos.moreskills.entity.michael.MichaelMeteorEntity;
import com.mojang.blaze3d.platform.GlStateManager.DestFactor;
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

public class MichaelMeteorRenderer extends EntityRenderer<MichaelMeteorEntity> {
   public MichaelMeteorRenderer(Context context) {
      super(context);
      this.shadowRadius = 0.0F;
   }

   public void render(MichaelMeteorEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
      float time = entity.tickCount + partialTick;
      this.renderStarCore(entity, poseStack, time);
      this.renderTrail(entity, poseStack, time);
      super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
   }

   private void renderStarCore(MichaelMeteorEntity entity, PoseStack poseStack, float time) {
      Vec3 motion = entity.getDeltaMovement();
      Vec3 forward = motion.lengthSqr() < 1.0E-4 ? new Vec3(0.0, -1.0, 0.0) : motion.normalize();
      Vec3 ref = Math.abs(forward.y) > 0.92 ? new Vec3(1.0, 0.0, 0.0) : new Vec3(0.0, 1.0, 0.0);
      Vec3 right = forward.cross(ref);
      if (right.lengthSqr() < 1.0E-4) {
         right = new Vec3(1.0, 0.0, 0.0);
      }

      right = right.normalize();
      Vec3 up = right.cross(forward).normalize();
      float size = entity.isBigMeteor() ? 54.0F : 3.2F;
      float pulse = 0.88F + 0.12F * Mth.sin(time * 0.28F);
      RenderSystem.enableBlend();
      RenderSystem.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
      RenderSystem.disableCull();
      RenderSystem.enableDepthTest();
      RenderSystem.depthMask(true);
      RenderSystem.setShader(GameRenderer::getPositionColorShader);
      Matrix4f matrix = poseStack.last().pose();
      BufferBuilder builder = Tesselator.getInstance().begin(Mode.TRIANGLES, DefaultVertexFormat.POSITION_COLOR);
      star(builder, matrix, Vec3.ZERO, right, up, forward, size * 1.6F * pulse, 255, 132, 255, 0.98F);
      star(builder, matrix, Vec3.ZERO, right, up, forward, size * 1.02F * pulse, 255, 188, 255, 0.95F);
      star(builder, matrix, Vec3.ZERO.add(forward.scale(0.18 * size)), right, up, forward, size * 0.72F * pulse, 136, 156, 255, 0.9F);
      disk(builder, matrix, Vec3.ZERO, right, up, size * 0.42, 255, 176, 255, 0.4F, 48);

      try {
         BufferUploader.drawWithShader(builder.buildOrThrow());
      } catch (IllegalStateException var14) {
      }

      RenderSystem.depthMask(true);
      RenderSystem.enableCull();
      RenderSystem.defaultBlendFunc();
      RenderSystem.disableBlend();
   }

   private void renderTrail(MichaelMeteorEntity entity, PoseStack poseStack, float time) {
      Vec3 motion = entity.getDeltaMovement();
      if (!(motion.lengthSqr() < 1.0E-4)) {
         Vec3 dir = motion.normalize().scale(-1.0);
         Vec3 ref = Math.abs(dir.y) > 0.92 ? new Vec3(1.0, 0.0, 0.0) : new Vec3(0.0, 1.0, 0.0);
         Vec3 right = dir.cross(ref);
         if (right.lengthSqr() < 0.001) {
            right = new Vec3(1.0, 0.0, 0.0);
         }

         right = right.normalize();
         Vec3 up = right.cross(dir).normalize();
         float size = entity.isBigMeteor() ? 38.0F : 5.0F;
         float length = entity.isBigMeteor() ? 980.0F : 300.0F;
         RenderSystem.enableBlend();
         RenderSystem.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
         RenderSystem.disableCull();
         RenderSystem.enableDepthTest();
         RenderSystem.depthMask(true);
         RenderSystem.setShader(GameRenderer::getPositionColorShader);
         Matrix4f matrix = poseStack.last().pose();
         BufferBuilder builder = Tesselator.getInstance().begin(Mode.TRIANGLES, DefaultVertexFormat.POSITION_COLOR);
         int pieces = entity.isBigMeteor() ? 124 : 68;

         for (int i = 0; i < pieces; i++) {
            float p0 = (float)i / pieces;
            float p1 = (float)(i + 1) / pieces;
            double spin0 = time * 0.1 + i * 0.44;
            double spin1 = time * 0.1 + (i + 1) * 0.44;
            float width0 = size * (1.0F - p0) * 0.14F;
            float width1 = size * (1.0F - p0) * 0.07F;
            Vec3 a = dir.scale(length * p0)
               .add(right.scale(Math.cos(spin0) * size * (1.0F - p0) * 0.24F))
               .add(up.scale(Math.sin(spin0) * size * (1.0F - p0) * 0.24F));
            Vec3 b = dir.scale(length * p1)
               .add(right.scale(Math.cos(spin1) * size * (1.0F - p1) * 0.24F))
               .add(up.scale(Math.sin(spin1) * size * (1.0F - p1) * 0.24F));
            float alpha = (1.0F - p0) * (entity.isBigMeteor() ? 0.96F : 0.88F);
            ribbon(builder, matrix, a, b, right, width0, 255, 124, 255, alpha * 0.94F);
            ribbon(builder, matrix, a, b, up, width1, 128, 156, 255, alpha * 0.92F);
            if (i % 7 == 0) {
               Vec3 tip = a.lerp(b, 0.5);
               star(builder, matrix, tip, right, up, dir.scale(-1.0), size * (0.08F + (1.0F - p0) * 0.12F), 255, 184, 255, alpha * 0.42F);
            }
         }

         try {
            BufferUploader.drawWithShader(builder.buildOrThrow());
         } catch (IllegalStateException var27) {
         }

         RenderSystem.depthMask(true);
         RenderSystem.enableCull();
         RenderSystem.defaultBlendFunc();
         RenderSystem.disableBlend();
      }
   }

   private static void disk(
      BufferBuilder builder, Matrix4f matrix, Vec3 center, Vec3 right, Vec3 up, double radius, int r, int g, int b, float alpha, int segments
   ) {
      for (int i = 0; i < segments; i++) {
         double a0 = i * Math.PI * 2.0 / segments;
         double a1 = (i + 1) * Math.PI * 2.0 / segments;
         Vec3 p0 = center.add(right.scale(Math.cos(a0) * radius)).add(up.scale(Math.sin(a0) * radius));
         Vec3 p1 = center.add(right.scale(Math.cos(a1) * radius)).add(up.scale(Math.sin(a1) * radius));
         tri(builder, matrix, center, p0, p1, r, g, b, alpha);
      }
   }

   private static void star(
      BufferBuilder builder, Matrix4f matrix, Vec3 center, Vec3 right, Vec3 up, Vec3 forward, float size, int r, int g, int b, float alpha
   ) {
      Vec3 rightLong = right.scale(size * 1.35);
      Vec3 upLong = up.scale(size * 1.35);
      Vec3 rightShort = right.scale(size * 0.26);
      Vec3 upShort = up.scale(size * 0.26);
      Vec3 face = forward.scale(size * 0.22);
      tri(builder, matrix, center.add(upLong), center.add(rightShort), center.add(face), r, g, b, alpha);
      tri(builder, matrix, center.add(rightLong), center.add(upShort.scale(-1.0)), center.add(face), r, g, b, alpha);
      tri(builder, matrix, center.add(upLong.scale(-1.0)), center.add(rightShort.scale(-1.0)), center.add(face), r, g, b, alpha);
      tri(builder, matrix, center.add(rightLong.scale(-1.0)), center.add(upShort), center.add(face), r, g, b, alpha);
      tri(builder, matrix, center.add(up.scale(size * 0.58)), center.add(right.scale(size * 0.58)), center.add(up.scale(-size * 0.58)), r, g, b, alpha * 0.28F);
      tri(builder, matrix, center.add(up.scale(-size * 0.58)), center.add(right.scale(-size * 0.58)), center.add(up.scale(size * 0.58)), r, g, b, alpha * 0.28F);
   }

   private static void ribbon(BufferBuilder builder, Matrix4f matrix, Vec3 a, Vec3 b, Vec3 side, float width, int r, int g, int bl, float alpha) {
      Vec3 s = side.normalize().scale(width);
      tri(builder, matrix, a.add(s), b.add(s), b.subtract(s), r, g, bl, alpha);
      tri(builder, matrix, b.subtract(s), a.subtract(s), a.add(s), r, g, bl, alpha);
   }

   private static void tri(BufferBuilder builder, Matrix4f matrix, Vec3 a, Vec3 b, Vec3 c, int r, int g, int bl, float alpha) {
      int aa = Mth.clamp((int)(alpha * 255.0F), 0, 255);
      if (aa > 0) {
         builder.addVertex(matrix, (float)a.x, (float)a.y, (float)a.z).setColor(r, g, bl, aa);
         builder.addVertex(matrix, (float)b.x, (float)b.y, (float)b.z).setColor(r, g, bl, aa);
         builder.addVertex(matrix, (float)c.x, (float)c.y, (float)c.z).setColor(r, g, bl, aa);
      }
   }

   private static double hash(long x) {
      x ^= x >>> 33;
      x *= -49064778989728563L;
      x ^= x >>> 33;
      x *= -4265267296055464877L;
      x ^= x >>> 33;
      return (x & 16777215L) / 1.6777216E7;
   }

   public boolean shouldRender(MichaelMeteorEntity entity, Frustum camera, double camX, double camY, double camZ) {
      return true;
   }

   public ResourceLocation getTextureLocation(MichaelMeteorEntity entity) {
      return TextureAtlas.LOCATION_BLOCKS;
   }
}
