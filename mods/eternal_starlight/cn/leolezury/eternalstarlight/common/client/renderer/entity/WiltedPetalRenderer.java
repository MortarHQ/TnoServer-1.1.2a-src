package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.entity.projectile.WiltedPetal;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class WiltedPetalRenderer extends EntityRenderer<WiltedPetal> {
   private static final ResourceLocation TEXTURE_LOCATION = EternalStarlight.id("textures/entity/wilted_petal.png");
   private static final RenderType RENDER_TYPE = RenderType.entityCutoutNoCull(TEXTURE_LOCATION);

   public WiltedPetalRenderer(Context context) {
      super(context);
   }

   public void render(WiltedPetal entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
      poseStack.pushPose();
      poseStack.translate(0.0F, entity.getBbHeight() / 2.0F, 0.0F);
      poseStack.scale(0.6F, 0.6F, 0.6F);
      poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
      Pose pose = poseStack.last();
      VertexConsumer vertexConsumer = buffer.getBuffer(RENDER_TYPE);
      vertex(
         vertexConsumer,
         pose,
         packedLight,
         0.5F * Mth.SQRT_OF_TWO * Mth.sin(Mth.lerp(partialTicks, entity.oSpin, entity.spin)),
         0.5F * Mth.SQRT_OF_TWO * Mth.cos(Mth.lerp(partialTicks, entity.oSpin, entity.spin)),
         0.0F,
         1.0F
      );
      vertex(
         vertexConsumer,
         pose,
         packedLight,
         0.5F * Mth.SQRT_OF_TWO * Mth.sin(Mth.lerp(partialTicks, entity.oSpin, entity.spin) + (float) (Math.PI / 2)),
         0.5F * Mth.SQRT_OF_TWO * Mth.cos(Mth.lerp(partialTicks, entity.oSpin, entity.spin) + (float) (Math.PI / 2)),
         1.0F,
         1.0F
      );
      vertex(
         vertexConsumer,
         pose,
         packedLight,
         0.5F * Mth.SQRT_OF_TWO * Mth.sin(Mth.lerp(partialTicks, entity.oSpin, entity.spin) + (float) Math.PI),
         0.5F * Mth.SQRT_OF_TWO * Mth.cos(Mth.lerp(partialTicks, entity.oSpin, entity.spin) + (float) Math.PI),
         1.0F,
         0.0F
      );
      vertex(
         vertexConsumer,
         pose,
         packedLight,
         0.5F * Mth.SQRT_OF_TWO * Mth.sin(Mth.lerp(partialTicks, entity.oSpin, entity.spin) + (float) (Math.PI * 3.0 / 2.0)),
         0.5F * Mth.SQRT_OF_TWO * Mth.cos(Mth.lerp(partialTicks, entity.oSpin, entity.spin) + (float) (Math.PI * 3.0 / 2.0)),
         0.0F,
         0.0F
      );
      poseStack.popPose();
      super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
   }

   private static void vertex(VertexConsumer consumer, Pose pose, int packedLight, float x, float y, float u, float v) {
      consumer.addVertex(pose, x, y, 0.0F)
         .setColor(-1)
         .setUv(u, v)
         .setOverlay(OverlayTexture.NO_OVERLAY)
         .setLight(packedLight)
         .setNormal(pose, 0.0F, 1.0F, 0.0F);
   }

   public ResourceLocation getTextureLocation(WiltedPetal entity) {
      return TEXTURE_LOCATION;
   }
}
