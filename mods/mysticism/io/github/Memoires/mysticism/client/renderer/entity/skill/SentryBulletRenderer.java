package io.github.Memoires.mysticism.client.renderer.entity.skill;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import com.mojang.math.Axis;
import io.github.Memoires.mysticism.entity.projectile.SentryBulletProjectile;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;

public class SentryBulletRenderer extends EntityRenderer<SentryBulletProjectile> {
   private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("mysticism", "textures/entity/projectiles/sentry_shot.png");

   public SentryBulletRenderer(Context context) {
      super(context);
   }

   public void render(SentryBulletProjectile entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
      poseStack.pushPose();
      poseStack.translate(0.0, 0.15, 0.0);
      poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
      poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
      VertexConsumer vertexBuilder = buffer.getBuffer(RenderType.entityCutoutNoCull(this.getTextureLocation(entity)));
      Matrix4f posMatrix = poseStack.last().pose();
      Pose pose = poseStack.last();
      vertexBuilder.addVertex(posMatrix, -0.5F, -0.5F, 0.0F)
         .setColor(255, 255, 255, 255)
         .setUv(0.0F, 1.0F)
         .setOverlay(OverlayTexture.NO_OVERLAY)
         .setLight(packedLight)
         .setNormal(pose, 0.0F, 1.0F, 0.0F);
      vertexBuilder.addVertex(posMatrix, 0.5F, -0.5F, 0.0F)
         .setColor(255, 255, 255, 255)
         .setUv(1.0F, 1.0F)
         .setOverlay(OverlayTexture.NO_OVERLAY)
         .setLight(packedLight)
         .setNormal(pose, 0.0F, 1.0F, 0.0F);
      vertexBuilder.addVertex(posMatrix, 0.5F, 0.5F, 0.0F)
         .setColor(255, 255, 255, 255)
         .setUv(1.0F, 0.0F)
         .setOverlay(OverlayTexture.NO_OVERLAY)
         .setLight(packedLight)
         .setNormal(pose, 0.0F, 1.0F, 0.0F);
      vertexBuilder.addVertex(posMatrix, -0.5F, 0.5F, 0.0F)
         .setColor(255, 255, 255, 255)
         .setUv(0.0F, 0.0F)
         .setOverlay(OverlayTexture.NO_OVERLAY)
         .setLight(packedLight)
         .setNormal(pose, 0.0F, 1.0F, 0.0F);
      poseStack.popPose();
      super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
   }

   public ResourceLocation getTextureLocation(SentryBulletProjectile entity) {
      return TEXTURE;
   }
}
