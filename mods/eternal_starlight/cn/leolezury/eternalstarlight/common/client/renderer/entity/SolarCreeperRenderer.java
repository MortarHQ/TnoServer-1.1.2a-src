package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.entity.OrbModel;
import cn.leolezury.eternalstarlight.common.client.model.entity.SolarCreeperModel;
import cn.leolezury.eternalstarlight.common.entity.living.boss.creeper.SolarCreeper;
import cn.leolezury.eternalstarlight.common.entity.living.boss.creeper.SolarCreeperIntroPhase;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.FastColor.ARGB32;
import net.minecraft.world.entity.Entity;
import org.joml.Quaternionf;

public class SolarCreeperRenderer<T extends SolarCreeper> extends MobRenderer<T, SolarCreeperModel<T>> {
   private static final ResourceLocation ENTITY_TEXTURE = EternalStarlight.id("textures/entity/solar_creeper/solar_creeper.png");
   private static final ResourceLocation SUN_TEXTURE = EternalStarlight.id("textures/entity/solar_creeper/sun.png");
   private final OrbModel<Entity> sunModel;

   public SolarCreeperRenderer(Context context) {
      super(context, new SolarCreeperModel(context.bakeLayer(SolarCreeperModel.LAYER_LOCATION)), 0.5F);
      this.sunModel = new OrbModel<>(context.bakeLayer(OrbModel.LAYER_LOCATION));
   }

   public void render(T entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
      if (entity.tickCount >= 3) {
         int state = entity.getBehaviorState();
         float animationTicks = entity.getAnimationTicks(partialTicks);
         float bodyScale = 1.0F;
         float sunScale = 0.0F;
         float shineScale = 0.0F;
         if (state == 1) {
            bodyScale = SolarCreeperIntroPhase.BODY_SCALE.calculate(animationTicks / 120.0F);
            sunScale = 2.0F * SolarCreeperIntroPhase.SUN_SCALE.calculate(animationTicks / 120.0F);
            shineScale = SolarCreeperIntroPhase.SHINE_SCALE.calculate(animationTicks / 120.0F);
         }

         if (bodyScale > 0.0F) {
            poseStack.pushPose();
            poseStack.translate(0.0F, entity.getBbHeight() / 2.0F, 0.0F);
            poseStack.scale(bodyScale, bodyScale, bodyScale);
            poseStack.translate(0.0F, -entity.getBbHeight() / 2.0F, 0.0F);
            super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
            poseStack.popPose();
         }

         if (sunScale > 0.0F) {
            poseStack.pushPose();
            poseStack.translate(0.0F, entity.getBbHeight() / 2.0F, 0.0F);
            poseStack.scale(sunScale, sunScale, sunScale);
            poseStack.scale(-1.0F, -1.0F, 1.0F);
            poseStack.translate(0.0F, -1.5F, 0.0F);
            RenderType renderType = this.sunModel.renderType(SUN_TEXTURE);
            VertexConsumer vertexConsumer = buffer.getBuffer(renderType);
            this.sunModel.setupAnim(entity, 0.0F, 0.0F, this.getBob(entity, partialTicks), 0.0F, 0.0F);
            this.sunModel.renderToBuffer(poseStack, vertexConsumer, 15728880, OverlayTexture.NO_OVERLAY);
            poseStack.popPose();
         }

         if (shineScale > 0.0F) {
            poseStack.pushPose();
            poseStack.translate(0.0F, entity.getBbHeight() / 2.0F, 0.0F);
            poseStack.scale(shineScale, shineScale, shineScale);
            poseStack.mulPose(new Quaternionf(this.entityRenderDispatcher.cameraOrientation()).rotateY((float) Math.PI));
            Pose pose = poseStack.last();
            VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.dragonRays());

            for (int i = 0; i < 5; i++) {
               vertexConsumer.addVertex(pose, 0.0F, 0.0F, 0.0F).setColor(ARGB32.color(255, 255, 213, 74));
               float angle = i * (float) (Math.PI * 2) / 5.0F + animationTicks / 120.0F * (float) Math.PI * 1.5F;
               vertexConsumer.addVertex(pose, Mth.sin(angle) * entity.getBbHeight() * 3.0F, Mth.cos(angle) * entity.getBbHeight() * 3.0F, 0.0F)
                  .setColor(ARGB32.color(0, 255, 213, 74));
               float largerAngle = angle + (float) (Math.PI / 6);
               vertexConsumer.addVertex(pose, Mth.sin(largerAngle) * entity.getBbHeight() * 3.0F, Mth.cos(largerAngle) * entity.getBbHeight() * 3.0F, 0.0F)
                  .setColor(ARGB32.color(0, 255, 213, 74));
            }

            poseStack.popPose();
         }
      }
   }

   protected float getFlipDegrees(T entity) {
      return 0.0F;
   }

   protected float getWhiteOverlayProgress(T entity, float partialTicks) {
      int state = entity.getBehaviorState();
      float animationTicks = entity.getAnimationTicks(partialTicks);
      float shineScale = 0.0F;
      if (state == 1) {
         shineScale = SolarCreeperIntroPhase.SHINE_SCALE.calculate(animationTicks / 120.0F);
      }

      return shineScale;
   }

   protected float getShadowRadius(T mob) {
      return mob.getBehaviorState() == 1 ? 0.0F : super.getShadowRadius(mob);
   }

   public ResourceLocation getTextureLocation(T entity) {
      return ENTITY_TEXTURE;
   }
}
