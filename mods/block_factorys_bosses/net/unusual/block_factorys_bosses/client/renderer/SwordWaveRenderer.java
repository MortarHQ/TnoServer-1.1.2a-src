package net.unusual.block_factorys_bosses.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import com.mojang.math.Axis;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.entity.projectile.SwordWaveEntity;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class SwordWaveRenderer extends EntityRenderer<SwordWaveEntity> {
   private static final ResourceLocation TEXTURE_LOCATION = BossesRise.prefix("textures/particle/soul_shockwave.png");
   private static final RenderType RENDER_TYPE = RenderType.entityTranslucentEmissive(TEXTURE_LOCATION);

   public SwordWaveRenderer(Context context) {
      super(context);
   }

   public void render(SwordWaveEntity wave, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
      VertexConsumer vertexconsumer = buffer.getBuffer(RENDER_TYPE);
      float baseRot = wave.isVertical() ? 0.0F : 90.0F;
      float scale = wave.getScale();
      poseStack.pushPose();
      if (wave.isVertical()) {
         poseStack.translate(0.0F, 1.0F, 0.0F);
      }

      poseStack.mulPose(Axis.YN.rotationDegrees(-wave.getYRot() + 90.0F));
      poseStack.mulPose(Axis.XP.rotationDegrees(baseRot));
      poseStack.mulPose(Axis.ZN.rotationDegrees(-wave.getXRot() + 90.0F));
      poseStack.scale(6.0F * scale, scale, 6.0F * scale);
      Pose pose = poseStack.last();
      vertex(vertexconsumer, pose, 0.0F, 0, 0, 1);
      vertex(vertexconsumer, pose, 1.0F, 0, 1, 1);
      vertex(vertexconsumer, pose, 1.0F, 1, 1, 0);
      vertex(vertexconsumer, pose, 0.0F, 1, 0, 0);
      poseStack.popPose();
      super.render(wave, entityYaw, partialTicks, poseStack, buffer, 15728880);
   }

   private static void vertex(VertexConsumer consumer, Pose pose, float x, int y, int u, int v) {
      consumer.addVertex(pose, x - 0.5F, y - 0.5F, 0.0F)
         .setColor(-1)
         .setUv(u, v)
         .setOverlay(OverlayTexture.NO_OVERLAY)
         .setLight(15728880)
         .setNormal(pose, 0.0F, 1.0F, 0.0F);
   }

   public ResourceLocation getTextureLocation(SwordWaveEntity entity) {
      return TEXTURE_LOCATION;
   }
}
