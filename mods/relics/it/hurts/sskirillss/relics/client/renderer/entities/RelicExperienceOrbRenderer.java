package it.hurts.sskirillss.relics.client.renderer.entities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import it.hurts.sskirillss.relics.entities.RelicExperienceOrbEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RelicExperienceOrbRenderer extends EntityRenderer<RelicExperienceOrbEntity> {
   public RelicExperienceOrbRenderer(Context context) {
      super(context);
      this.shadowRadius = 0.0F;
      this.shadowStrength = 0.75F;
   }

   protected int getBlockLightLevel(RelicExperienceOrbEntity entity, BlockPos pos) {
      return Mth.clamp(super.getBlockLightLevel(entity, pos) + 7, 0, 15);
   }

   public void render(RelicExperienceOrbEntity entity, float yaw, float pitch, PoseStack poseStack, MultiBufferSource buffer, int light) {
      this.shadowRadius = 0.075F + entity.getStage() * 0.025F;
      poseStack.pushPose();
      VertexConsumer consumer = buffer.getBuffer(RenderType.itemEntityTranslucentCull(this.getTextureLocation(entity)));
      Pose pose = poseStack.last();
      float scale = (float)(0.5 + Math.sin(entity.tickCount * 0.1F) * 0.05F);
      poseStack.scale(scale, scale, scale);
      poseStack.translate(0.0F, 0.2F + entity.getStage() * 0.05F, 0.0F);
      poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
      int alpha = (int)Math.min(255.0, 255.0 * (0.75 + Math.sin(entity.tickCount * 0.25F) * 0.1F));
      vertex(consumer, pose, -0.5F, -0.5F, alpha, 0.0F, 1.0F);
      vertex(consumer, pose, 0.5F, -0.5F, alpha, 1.0F, 1.0F);
      vertex(consumer, pose, 0.5F, 0.5F, alpha, 1.0F, 0.0F);
      vertex(consumer, pose, -0.5F, 0.5F, alpha, 0.0F, 0.0F);
      poseStack.popPose();
      super.render(entity, yaw, pitch, poseStack, buffer, light);
   }

   private static void vertex(VertexConsumer consumer, Pose pose, float x, float y, int alpha, float u, float v) {
      consumer.addVertex(pose, x, y, 0.0F)
         .setColor(255, 255, 255, alpha)
         .setUv(u, v)
         .setOverlay(OverlayTexture.NO_OVERLAY)
         .setLight(15728880)
         .setNormal(pose, 0.0F, 1.0F, 0.0F);
   }

   public ResourceLocation getTextureLocation(RelicExperienceOrbEntity entity) {
      return ResourceLocation.fromNamespaceAndPath("relics", "textures/entities/experience/relics/relic_experience_" + entity.getStage() + ".png");
   }
}
