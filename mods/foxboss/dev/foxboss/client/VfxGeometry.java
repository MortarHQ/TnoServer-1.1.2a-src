package dev.foxboss.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

final class VfxGeometry {
   static final int CYAN = 2736127;
   static final int BLUE = 1338367;
   static final int WHITE = 15269119;
   private static final double TAU = Math.PI * 2;

   static void beam(PoseStack pose, MultiBufferSource source, Vec3 a, Vec3 b, float width, int color, float alpha) {
      beam(pose, source, a, b, width, width, color, alpha);
   }

   private static void beam(PoseStack pose, MultiBufferSource source, Vec3 a, Vec3 b, float startWidth, float endWidth, int color, float alpha) {
      if (!(a.distanceToSqr(b) < 1.0E-10)) {
         Vec3 direction = b.subtract(a).normalize();
         Vec3 side = direction.cross(new Vec3(0.0, 1.0, 0.0));
         if (side.lengthSqr() < 0.001) {
            side = direction.cross(new Vec3(1.0, 0.0, 0.0));
         }

         side = side.normalize();
         Vec3 up = direction.cross(side).normalize();
         Vec3 sideA = side.scale(startWidth);
         Vec3 sideB = side.scale(endWidth);
         Vec3 upA = up.scale(startWidth);
         Vec3 upB = up.scale(endWidth);
         VertexConsumer buffer = source.getBuffer(FoxVfxRenderTypes.GLOW);
         Matrix4f matrix = pose.last().pose();
         quad(buffer, matrix, a.add(sideA), b.add(sideB), b.subtract(sideB), a.subtract(sideA), color, alpha);
         quad(buffer, matrix, a.add(upA), b.add(upB), b.subtract(upB), a.subtract(upA), color, alpha);
      }
   }

   static void glowBeam(PoseStack pose, MultiBufferSource source, Vec3 a, Vec3 b, float width, float alpha) {
      glowBeam(pose, source, a, b, width, width, alpha);
   }

   static void glowBeam(PoseStack pose, MultiBufferSource source, Vec3 a, Vec3 b, float startWidth, float endWidth, float alpha) {
      beam(pose, source, a, b, startWidth * 4.0F, endWidth * 4.0F, 1338367, alpha * 0.12F);
      beam(pose, source, a, b, startWidth * 2.0F, endWidth * 2.0F, 2736127, alpha * 0.34F);
      beam(pose, source, a, b, startWidth * 0.48F, endWidth * 0.48F, 15269119, alpha * 0.92F);
   }

   static void glowSpike(PoseStack pose, MultiBufferSource source, Vec3 base, Vec3 tip, float width, float alpha) {
      width = Math.min(width, (float)base.distanceTo(tip) * 0.07F);
      spike(pose, source, base, tip, width * 2.6F, 1338367, alpha * 0.1F);
      spike(pose, source, base, tip, width * 1.5F, 2736127, alpha * 0.3F);
      spike(pose, source, base, tip, width * 0.48F, 15269119, alpha * 0.92F);
   }

   private static void spike(PoseStack pose, MultiBufferSource source, Vec3 base, Vec3 tip, float width, int color, float alpha) {
      Vec3 direction = tip.subtract(base);
      if (!(direction.lengthSqr() < 1.0E-8)) {
         direction = direction.normalize();
         Vec3 side = direction.cross(new Vec3(0.0, 1.0, 0.0));
         if (side.lengthSqr() < 0.001) {
            side = direction.cross(new Vec3(1.0, 0.0, 0.0));
         }

         side = side.normalize().scale(width);
         Vec3 up = direction.cross(side).normalize().scale(width * 0.72F);
         Matrix4f matrix = pose.last().pose();
         VertexConsumer buffer = source.getBuffer(FoxVfxRenderTypes.GLOW);
         Vec3 middle = base.lerp(tip, 0.58);
         Vec3[] rim = new Vec3[]{middle.add(side), middle.add(up), middle.subtract(side), middle.subtract(up)};

         for (int i = 0; i < 4; i++) {
            Vec3 a = rim[i];
            Vec3 b = rim[(i + 1) % 4];
            shardTriangle(buffer, matrix, a, b, tip, color, alpha, alpha * 0.3F);
            shardTriangle(buffer, matrix, b, a, base, color, alpha, 0.0F);
         }
      }
   }

   static void sphere(PoseStack pose, MultiBufferSource source, Vec3 center, float radius, int color, float alpha) {
      VertexConsumer buffer = source.getBuffer(FoxVfxRenderTypes.GLOW);
      Matrix4f matrix = pose.last().pose();

      for (int y = 0; y < 8; y++) {
         double p0 = (-Math.PI / 2) + Math.PI * y / 8.0;
         double p1 = (-Math.PI / 2) + Math.PI * (y + 1) / 8.0;

         for (int x = 0; x < 16; x++) {
            double a = (Math.PI * 2) * x / 16.0;
            double b = (Math.PI * 2) * (x + 1) / 16.0;
            quad(
               buffer,
               matrix,
               spherical(center, radius, p0, a),
               spherical(center, radius, p1, a),
               spherical(center, radius, p1, b),
               spherical(center, radius, p0, b),
               color,
               alpha
            );
         }
      }
   }

   static void orb(PoseStack pose, MultiBufferSource source, Vec3 center, float radius, float alpha) {
      sphere(pose, source, center, radius * 1.5F, 1338367, alpha * 0.055F);
      sphere(pose, source, center, radius, 2736127, alpha * 0.12F);
      sphere(pose, source, center, radius * 0.6F, 15269119, alpha * 0.7F);
   }

   static void ring(PoseStack pose, MultiBufferSource source, Vec3 center, Vec3 normal, float radius, float width, float spin, float alpha) {
      Vec3 u = normal.cross(new Vec3(0.0, 1.0, 0.0));
      if (u.lengthSqr() < 0.01) {
         u = normal.cross(new Vec3(1.0, 0.0, 0.0));
      }

      u = u.normalize();
      Vec3 v = normal.cross(u).normalize();

      for (int i = 0; i < 48; i++) {
         double a = (Math.PI * 2) * i / 48.0 + spin;
         double b = (Math.PI * 2) * (i + 1) / 48.0 + spin;
         Vec3 p = center.add(u.scale(Math.cos(a) * radius)).add(v.scale(Math.sin(a) * radius));
         Vec3 q = center.add(u.scale(Math.cos(b) * radius)).add(v.scale(Math.sin(b) * radius));
         glowBeam(pose, source, p, q, width, alpha * (0.65F + 0.35F * (float)Math.sin(a * 3.0 + spin)));
      }
   }

   static void star(PoseStack pose, MultiBufferSource source, Vec3 center, Quaternionf camera, float radius, float alpha) {
      pose.pushPose();
      pose.translate(center.x, center.y, center.z);
      pose.mulPose(camera);
      VertexConsumer buffer = source.getBuffer(FoxVfxRenderTypes.GLOW);
      Matrix4f matrix = pose.last().pose();
      quad(
         buffer,
         matrix,
         new Vec3(0.0, radius, 0.0),
         new Vec3(radius * 0.13, 0.0, 0.0),
         new Vec3(0.0, -radius, 0.0),
         new Vec3(-radius * 0.13, 0.0, 0.0),
         15269119,
         alpha
      );
      quad(
         buffer,
         matrix,
         new Vec3(radius * 0.8, 0.0, 0.0),
         new Vec3(0.0, radius * 0.1, 0.0),
         new Vec3(-radius * 0.8, 0.0, 0.0),
         new Vec3(0.0, -radius * 0.1, 0.0),
         15269119,
         alpha
      );
      pose.popPose();
   }

   private static Vec3 spherical(Vec3 c, float r, double latitude, double longitude) {
      return c.add(r * Math.cos(latitude) * Math.cos(longitude), r * Math.sin(latitude), r * Math.cos(latitude) * Math.sin(longitude));
   }

   private static void quad(VertexConsumer b, Matrix4f m, Vec3 a, Vec3 c, Vec3 d, Vec3 e, int rgb, float alpha) {
      vertex(b, m, a, rgb, alpha);
      vertex(b, m, c, rgb, alpha);
      vertex(b, m, d, rgb, alpha);
      vertex(b, m, e, rgb, alpha);
   }

   private static void shardTriangle(VertexConsumer b, Matrix4f m, Vec3 a, Vec3 c, Vec3 tip, int rgb, float alpha, float tipAlpha) {
      vertex(b, m, a, rgb, alpha);
      vertex(b, m, c, rgb, alpha);
      vertex(b, m, tip, rgb, tipAlpha);
      vertex(b, m, tip, rgb, tipAlpha);
   }

   private static void vertex(VertexConsumer b, Matrix4f m, Vec3 v, int rgb, float alpha) {
      b.addVertex(m, (float)v.x, (float)v.y, (float)v.z)
         .setColor(rgb >> 16 & 0xFF, rgb >> 8 & 0xFF, rgb & 0xFF, Math.max(0, Math.min(255, (int)(alpha * 255.0F))));
   }

   private VfxGeometry() {
   }
}
