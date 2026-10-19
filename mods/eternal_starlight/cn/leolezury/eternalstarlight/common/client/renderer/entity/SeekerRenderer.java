package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.client.model.entity.SeekerModel;
import cn.leolezury.eternalstarlight.common.client.renderer.layer.SeekerGlowLayer;
import cn.leolezury.eternalstarlight.common.entity.living.monster.Seeker;
import cn.leolezury.eternalstarlight.common.entity.living.monster.SeekerVariant;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import com.mojang.math.Axis;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public class SeekerRenderer<T extends Seeker> extends MobRenderer<T, SeekerModel<T>> {
   public SeekerRenderer(Context context) {
      super(context, new SeekerModel(context.bakeLayer(SeekerModel.LAYER_LOCATION)), 0.5F);
      this.addLayer(new SeekerGlowLayer(this));
   }

   protected void setupRotations(T entity, PoseStack poseStack, float bob, float yBodyRot, float partialTick, float scale) {
      poseStack.mulPose(Axis.YP.rotationDegrees(-entity.getSeekerYRot(partialTick) - 90.0F));
      if (entity.deathTime > 0) {
         float deathProgress = (entity.deathTime + partialTick - 1.0F) / 20.0F * 1.6F;
         deathProgress = Mth.sqrt(deathProgress);
         if (deathProgress > 1.0F) {
            deathProgress = 1.0F;
         }

         poseStack.mulPose(Axis.ZP.rotationDegrees(deathProgress * this.getFlipDegrees(entity)));
      }

      poseStack.translate(0.0F, entity.getBbHeight() / 2.0F, 0.0F);
      poseStack.mulPose(Axis.XP.rotationDegrees(entity.getSeekerXRot(partialTick) - 90.0F));
      poseStack.translate(0.0F, -entity.getBbHeight() / 2.0F, 0.0F);
   }

   public void render(T entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
      super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
      if (this.isBodyVisible(entity) && entity.isAlive() && entity.attackAnimationState.isStarted()) {
         float length = 5.0F * Mth.clamp(1.0F - Mth.abs((float)entity.attackAnimationState.getAccumulatedTime() - 1000.0F) / 120.0F, 0.0F, 1.0F);
         Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
         Vec3 sight = camera.getPosition().subtract(entity.position().add(0.0, entity.getBbHeight() / 2.0F, 0.0));
         Vec3 start = new Vec3(0.0, entity.getBbHeight() / 2.0F, 0.0);
         Vec3 end = ESMathUtil.rotationToPosition(start, length, -entity.getSeekerXRot(partialTicks), entity.getSeekerYRot(partialTicks) + 180.0F);
         Vec3 diff = end.subtract(start);
         Vec3 bodyEnd = start.add(diff.normalize().scale(diff.length() - 0.3125));
         Vec3 sideOffset = diff.cross(sight).normalize().scale(0.125);
         Pose pose = poseStack.last();
         VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityCutoutNoCull(((SeekerVariant)entity.getVariant().value()).tentacleTextureFull()));
         vertexConsumer.addVertex(pose, start.add(sideOffset).toVector3f())
            .setColor(-1)
            .setUv(-length * 16.0F / 5.0F, 0.0F)
            .setOverlay(OverlayTexture.NO_OVERLAY)
            .setLight(15728880)
            .setNormal(pose, 0.0F, 1.0F, 0.0F);
         vertexConsumer.addVertex(pose, start.add(sideOffset.scale(-1.0)).toVector3f())
            .setColor(-1)
            .setUv(-length * 16.0F / 5.0F, 1.0F)
            .setOverlay(OverlayTexture.NO_OVERLAY)
            .setLight(15728880)
            .setNormal(pose, 0.0F, 1.0F, 0.0F);
         vertexConsumer.addVertex(pose, bodyEnd.add(sideOffset.scale(-1.0)).toVector3f())
            .setColor(-1)
            .setUv(0.0F, 1.0F)
            .setOverlay(OverlayTexture.NO_OVERLAY)
            .setLight(15728880)
            .setNormal(pose, 0.0F, 1.0F, 0.0F);
         vertexConsumer.addVertex(pose, bodyEnd.add(sideOffset).toVector3f())
            .setColor(-1)
            .setUv(0.0F, 0.0F)
            .setOverlay(OverlayTexture.NO_OVERLAY)
            .setLight(15728880)
            .setNormal(pose, 0.0F, 1.0F, 0.0F);
         vertexConsumer = buffer.getBuffer(RenderType.entityCutoutNoCull(((SeekerVariant)entity.getVariant().value()).tentacleEndTextureFull()));
         vertexConsumer.addVertex(pose, bodyEnd.add(sideOffset).toVector3f())
            .setColor(-1)
            .setUv(0.0F, 0.0F)
            .setOverlay(OverlayTexture.NO_OVERLAY)
            .setLight(15728880)
            .setNormal(pose, 0.0F, 1.0F, 0.0F);
         vertexConsumer.addVertex(pose, bodyEnd.add(sideOffset.scale(-1.0)).toVector3f())
            .setColor(-1)
            .setUv(0.0F, 1.0F)
            .setOverlay(OverlayTexture.NO_OVERLAY)
            .setLight(15728880)
            .setNormal(pose, 0.0F, 1.0F, 0.0F);
         vertexConsumer.addVertex(pose, end.add(sideOffset.scale(-1.0)).toVector3f())
            .setColor(-1)
            .setUv(1.0F, 1.0F)
            .setOverlay(OverlayTexture.NO_OVERLAY)
            .setLight(15728880)
            .setNormal(pose, 0.0F, 1.0F, 0.0F);
         vertexConsumer.addVertex(pose, end.add(sideOffset).toVector3f())
            .setColor(-1)
            .setUv(1.0F, 0.0F)
            .setOverlay(OverlayTexture.NO_OVERLAY)
            .setLight(15728880)
            .setNormal(pose, 0.0F, 1.0F, 0.0F);
      }
   }

   public ResourceLocation getTextureLocation(T entity) {
      return ((SeekerVariant)entity.getVariant().value()).textureFull();
   }
}
