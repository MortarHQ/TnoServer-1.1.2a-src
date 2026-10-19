package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.ESRenderType;
import cn.leolezury.eternalstarlight.common.client.model.ESModelUtil;
import cn.leolezury.eternalstarlight.common.client.model.entity.StarlightGolemModel;
import cn.leolezury.eternalstarlight.common.client.renderer.layer.StarlightGolemEyesLayer;
import cn.leolezury.eternalstarlight.common.client.renderer.layer.StarlightGolemGlowLayer;
import cn.leolezury.eternalstarlight.common.client.renderer.layer.StarlightGolemHalloweenLayer;
import cn.leolezury.eternalstarlight.common.entity.living.boss.golem.StarlightGolem;
import cn.leolezury.eternalstarlight.common.util.Easing;
import cn.leolezury.eternalstarlight.common.util.ModelPartPose;
import cn.leolezury.eternalstarlight.common.util.ModelSnapshot;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import com.mojang.datafixers.util.Pair;
import com.mojang.math.Axis;
import java.util.Map;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.FastColor.ARGB32;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;

public class StarlightGolemRenderer<T extends StarlightGolem> extends MobRenderer<T, StarlightGolemModel<T>> {
   private static final int SNAPSHOT_LIFESPAN = 15;
   private static final ResourceLocation ENTITY_TEXTURE = EternalStarlight.id("textures/entity/starlight_golem/starlight_golem.png");
   private static final ResourceLocation CRACKED_TEXTURE = EternalStarlight.id("textures/entity/starlight_golem/starlight_golem_cracked.png");

   public StarlightGolemRenderer(Context context) {
      super(context, new StarlightGolemModel(context.bakeLayer(StarlightGolemModel.LAYER_LOCATION)), 0.5F);
      this.addLayer(new StarlightGolemHalloweenLayer(this, context.getModelSet()));
      this.addLayer(new StarlightGolemGlowLayer(this, context.getModelSet()));
      this.addLayer(new StarlightGolemEyesLayer(this));
   }

   public void render(T entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
      float deathProgress = Mth.clamp(Mth.lerp(partialTicks, entity.oldDeathAnimationTime, entity.deathAnimationTime) / 110.0F, 0.0F, 1.0F);
      if (deathProgress > 0.0F) {
         poseStack.pushPose();
         poseStack.translate(0.0F, entity.getBbHeight() / 4.0F, 0.0F);
         poseStack.scale(
            deathProgress + Mth.cos((entity.tickCount + partialTicks) * 4.0F) * 0.2F * deathProgress,
            deathProgress + Mth.cos((entity.tickCount + partialTicks) * 4.0F) * 0.2F * deathProgress,
            deathProgress + Mth.cos((entity.tickCount + partialTicks) * 4.0F) * 0.2F * deathProgress
         );
         poseStack.mulPose(new Quaternionf(this.entityRenderDispatcher.cameraOrientation()).rotateY((float) Math.PI));
         Pose pose = poseStack.last();
         VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.dragonRays());

         for (int i = 0; i < 5; i++) {
            vertexConsumer.addVertex(pose, 0.0F, 0.0F, 0.0F)
               .setColor(ARGB32.colorFromFloat(Easing.IN_OUT_SINE.interpolate(deathProgress, 0.5F, 1.0F), 1.0F, 1.0F, 1.0F));
            float angle = i * (float) (Math.PI * 2) / 5.0F + deathProgress * (float) Math.PI * 1.5F;
            vertexConsumer.addVertex(pose, Mth.sin(angle) * entity.getBbHeight() * 3.0F, Mth.cos(angle) * entity.getBbHeight() * 3.0F, 0.0F)
               .setColor(ARGB32.color(0, 64, 106, 125));
            float largerAngle = angle + deathProgress * (float) (Math.PI * 2) / 8.0F;
            vertexConsumer.addVertex(pose, Mth.sin(largerAngle) * entity.getBbHeight() * 3.0F, Mth.cos(largerAngle) * entity.getBbHeight() * 3.0F, 0.0F)
               .setColor(ARGB32.color(0, 64, 106, 125));
         }

         poseStack.popPose();
      }

      super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
      if (entity.isAlive()) {
         double currentX = Mth.lerp(partialTicks, entity.xo, entity.getX());
         double currentY = Mth.lerp(partialTicks, entity.yo, entity.getY());
         double currentZ = Mth.lerp(partialTicks, entity.zo, entity.getZ());
         float currentTick = this.getBob(entity, partialTicks);
         if (entity.trailSnapshots.isEmpty() || currentTick - entity.lastTrailTick > 4.0F) {
            if (entity.shouldAddTrailSnapshot()) {
               Map<String, ModelPartPose> snapshot = ESModelUtil.saveModelSnapshot(
                  ((StarlightGolemModel)this.getModel()).allPartNames, ((StarlightGolemModel)this.getModel())::getAnyDescendantWithName
               );
               entity.trailSnapshots
                  .addFirst(
                     Pair.of(
                        new Vec3(currentX, currentY, currentZ),
                        new ModelSnapshot(0.0F, Mth.rotLerp(partialTicks, entity.yBodyRotO, entity.yBodyRot), currentTick, snapshot)
                     )
                  );
               entity.lastTrailTick = currentTick;
            }

            entity.trailSnapshots.removeIf(p -> currentTick - ((ModelSnapshot)p.getSecond()).timestamp() > 15.0F);

            while (entity.trailSnapshots.size() > 32) {
               entity.trailSnapshots.removeLast();
            }
         }

         for (int i = 0; i < entity.trailSnapshots.size(); i++) {
            poseStack.pushPose();
            Vec3 trailPos = (Vec3)entity.trailSnapshots.get(i).getFirst();
            ModelSnapshot snapshot = (ModelSnapshot)entity.trailSnapshots.get(i).getSecond();
            ESModelUtil.loadPoseFromSnapshot(snapshot.poses(), ((StarlightGolemModel)this.getModel())::getAnyDescendantWithName);
            poseStack.translate(trailPos.x - currentX, trailPos.y - currentY, trailPos.z - currentZ);
            ((StarlightGolemModel)this.getModel()).alphaFactor = (1.0F - Mth.clamp(currentTick - snapshot.timestamp(), 0.0F, 15.0F) / 15.0F) * 0.3F;
            poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - snapshot.yRot()));
            poseStack.scale(-1.0F, -1.0F, 1.0F);
            this.scale(entity, poseStack, partialTicks);
            poseStack.translate(0.0F, -1.5F, 0.0F);
            RenderType renderType = ESRenderType.entityTranslucentNoDepth(this.getTextureLocation(entity));
            VertexConsumer vertexConsumer = buffer.getBuffer(renderType);
            ((StarlightGolemModel)this.getModel()).renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY);
            poseStack.popPose();
         }

         ((StarlightGolemModel)this.getModel()).alphaFactor = 1.0F;
      }
   }

   protected float getFlipDegrees(T entity) {
      return 0.0F;
   }

   protected float getWhiteOverlayProgress(T entity, float partialTicks) {
      float deathProgress = Mth.lerp(partialTicks, entity.oldDeathAnimationTime, entity.deathAnimationTime) / 100.0F;
      if (deathProgress <= 0.0F) {
         return 0.0F;
      } else {
         return (int)(deathProgress * 45.0) % 2 == 0 ? 0.0F : Mth.clamp(deathProgress, 0.2F, 1.0F);
      }
   }

   public ResourceLocation getTextureLocation(T entity) {
      return entity.getPhase() == 0 ? ENTITY_TEXTURE : CRACKED_TEXTURE;
   }
}
