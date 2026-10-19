package com.cerbon.bosses_of_mass_destruction.util;

import com.cerbon.cerbons_api.api.static_utilities.MathUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

public class VanillaCopies {
   public static void renderBillboard(
      PoseStack poseStack, MultiBufferSource buffer, int i, EntityRenderDispatcher dispatcher, RenderType type, Quaternionf rotation
   ) {
      poseStack.pushPose();
      poseStack.mulPose(dispatcher.cameraOrientation());
      poseStack.mulPose(new Quaternionf().rotateY((float)Math.toRadians(180.0)));
      poseStack.mulPose(rotation);
      Pose pose = poseStack.last();
      Matrix4f matrix4f = pose.pose();
      VertexConsumer vertexConsumer = buffer.getBuffer(type);
      produceVertex(vertexConsumer, matrix4f, pose, i, 0.0F, 0, 0, 1);
      produceVertex(vertexConsumer, matrix4f, pose, i, 1.0F, 0, 1, 1);
      produceVertex(vertexConsumer, matrix4f, pose, i, 1.0F, 1, 1, 0);
      produceVertex(vertexConsumer, matrix4f, pose, i, 0.0F, 1, 0, 0);
      poseStack.popPose();
   }

   public static void produceVertex(
      VertexConsumer vertexConsumer, Matrix4f modelMatrix, Pose normalMatrix, int light, float x, int y, int textureU, int textureV
   ) {
      vertexConsumer.addVertex(modelMatrix, x - 0.5F, y - 0.25F, 0.0F)
         .setColor(255, 255, 255, 255)
         .setUv(textureU, textureV)
         .setOverlay(OverlayTexture.NO_OVERLAY)
         .setLight(light)
         .setNormal(normalMatrix, 0.0F, 1.0F, 0.0F);
   }

   public static void renderBeam(
      LivingEntity actor, Vec3 target, Vec3 prevTarget, float partialTicks, Vec3 color, PoseStack poseStack, MultiBufferSource buffer, RenderType renderType
   ) {
      float j = (float)actor.level().getGameTime() + partialTicks;
      float k = j % 1.0F;
      float l = actor.getEyeHeight() - 1.7F;
      poseStack.pushPose();
      poseStack.translate(0.0, l, 0.0);
      Vec3 vec3 = MathUtils.lerpVec(partialTicks, prevTarget, target);
      Vec3 vec32 = fromLerpedPosition(actor, l, partialTicks);
      Vec3 vec33 = vec3.subtract(vec32);
      float m = (float)vec33.length();
      vec33 = vec33.normalize();
      float n = (float)Math.acos(vec33.y);
      float o = (float)Math.atan2(vec33.z, vec33.x);
      poseStack.mulPose(Axis.YP.rotationDegrees(((float) (Math.PI / 2) - o) * (180.0F / (float)Math.PI)));
      poseStack.mulPose(Axis.XP.rotationDegrees(n * (180.0F / (float)Math.PI)));
      float q = j * 0.05F * -1.5F;
      int red = (int)(color.x() * 255.0);
      int green = (int)(color.y() * 255.0);
      int blue = (int)(color.z() * 255.0);
      float x = Mth.cos(q + (float) (Math.PI * 3.0 / 4.0)) * 0.282F;
      float y = Mth.sin(q + (float) (Math.PI * 3.0 / 4.0)) * 0.282F;
      float z = Mth.cos(q + (float) (Math.PI / 4)) * 0.282F;
      float aa = Mth.sin(q + (float) (Math.PI / 4)) * 0.282F;
      float ab = Mth.cos(q + ((float) Math.PI * 5.0F / 4.0F)) * 0.282F;
      float ac = Mth.sin(q + ((float) Math.PI * 5.0F / 4.0F)) * 0.282F;
      float ad = Mth.cos(q + ((float) Math.PI * 7.0F / 4.0F)) * 0.282F;
      float ae = Mth.sin(q + ((float) Math.PI * 7.0F / 4.0F)) * 0.282F;
      float af = Mth.cos(q + (float) Math.PI) * 0.2F;
      float ag = Mth.sin(q + (float) Math.PI) * 0.2F;
      float ah = Mth.cos(q + 0.0F) * 0.2F;
      float ai = Mth.sin(q + 0.0F) * 0.2F;
      float aj = Mth.cos(q + (float) (Math.PI / 2)) * 0.2F;
      float ak = Mth.sin(q + (float) (Math.PI / 2)) * 0.2F;
      float al = Mth.cos(q + (float) (Math.PI * 3.0 / 2.0)) * 0.2F;
      float am = Mth.sin(q + (float) (Math.PI * 3.0 / 2.0)) * 0.2F;
      float aq = -1.0F - k;
      float ar = m * 2.5F + aq;
      VertexConsumer vertexConsumer = buffer.getBuffer(renderType);
      Pose pose = poseStack.last();
      Matrix4f matrix4f = pose.pose();
      vertex(vertexConsumer, matrix4f, pose, af, m, ag, red, green, blue, 0.4999F, ar);
      vertex(vertexConsumer, matrix4f, pose, af, 0.0F, ag, red, green, blue, 0.4999F, aq);
      vertex(vertexConsumer, matrix4f, pose, ah, 0.0F, ai, red, green, blue, 0.0F, aq);
      vertex(vertexConsumer, matrix4f, pose, ah, m, ai, red, green, blue, 0.0F, ar);
      vertex(vertexConsumer, matrix4f, pose, aj, m, ak, red, green, blue, 0.4999F, ar);
      vertex(vertexConsumer, matrix4f, pose, aj, 0.0F, ak, red, green, blue, 0.4999F, aq);
      vertex(vertexConsumer, matrix4f, pose, al, 0.0F, am, red, green, blue, 0.0F, aq);
      vertex(vertexConsumer, matrix4f, pose, al, m, am, red, green, blue, 0.0F, ar);
      float as = 0.0F;
      if (actor.tickCount % 2 == 0) {
         as = 0.5F;
      }

      vertex(vertexConsumer, matrix4f, pose, x, m, y, red, green, blue, 0.5F, as + 0.5F);
      vertex(vertexConsumer, matrix4f, pose, z, m, aa, red, green, blue, 1.0F, as + 0.5F);
      vertex(vertexConsumer, matrix4f, pose, ad, m, ae, red, green, blue, 1.0F, as);
      vertex(vertexConsumer, matrix4f, pose, ab, m, ac, red, green, blue, 0.5F, as);
      poseStack.popPose();
   }

   public static void vertex(VertexConsumer vertexConsumer, Matrix4f matrix4f, Pose pose, float f, float g, float h, int i, int j, int k, float l, float m) {
      vertexConsumer.addVertex(matrix4f, f, g, h)
         .setColor(i, j, k, 255)
         .setUv(l, m)
         .setOverlay(OverlayTexture.NO_OVERLAY)
         .setLight(15728800)
         .setNormal(pose, 0.0F, 0.0F, -1.0F);
   }

   public static Vec3 fromLerpedPosition(LivingEntity entity, double yOffset, float delta) {
      double d = Mth.lerp(delta, entity.xOld, entity.getX());
      double e = Mth.lerp(delta, entity.yOld, entity.getY()) + yOffset;
      double f = Mth.lerp(delta, entity.zOld, entity.getZ());
      return new Vec3(d, e, f);
   }
}
