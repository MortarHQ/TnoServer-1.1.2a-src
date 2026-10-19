package com.chronodawn.client.renderer;

import com.chronodawn.compat.CompatResourceLocation;
import com.chronodawn.entities.projectiles.TimeBlastEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class TimeBlastRenderer extends EntityRenderer<TimeBlastEntity> {
   private static final ResourceLocation TEXTURE_LOCATION = CompatResourceLocation.create("chronodawn", "textures/entity/projectiles/time_blast.png");
   private static final RenderType RENDER_TYPE = RenderType.entityCutoutNoCull(TEXTURE_LOCATION);

   public TimeBlastRenderer(Context context) {
      super(context);
   }

   public void render(TimeBlastEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
      poseStack.pushPose();
      poseStack.scale(0.3F, 0.3F, 0.3F);
      poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
      poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
      Pose pose = poseStack.last();
      Matrix4f matrix4f = pose.pose();
      Matrix3f matrix3f = pose.normal();
      VertexConsumer vertexConsumer = buffer.getBuffer(RENDER_TYPE);
      this.vertex(vertexConsumer, matrix4f, matrix3f, packedLight, -0.5F, -0.5F, 0.0F, 0.0F, 1.0F);
      this.vertex(vertexConsumer, matrix4f, matrix3f, packedLight, -0.5F, 0.5F, 0.0F, 0.0F, 0.0F);
      this.vertex(vertexConsumer, matrix4f, matrix3f, packedLight, 0.5F, 0.5F, 0.0F, 1.0F, 0.0F);
      this.vertex(vertexConsumer, matrix4f, matrix3f, packedLight, 0.5F, -0.5F, 0.0F, 1.0F, 1.0F);
      poseStack.popPose();
      super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
   }

   private void vertex(VertexConsumer consumer, Matrix4f matrix4f, Matrix3f matrix3f, int packedLight, float x, float y, float z, float u, float v) {
      consumer.addVertex(matrix4f, x, y, z)
         .setColor(255, 255, 255, 255)
         .setUv(u, v)
         .setOverlay(OverlayTexture.NO_OVERLAY)
         .setLight(packedLight)
         .setNormal(0.0F, 1.0F, 0.0F);
   }

   public ResourceLocation getTextureLocation(TimeBlastEntity entity) {
      return TEXTURE_LOCATION;
   }
}
