package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.ESRenderType;
import cn.leolezury.eternalstarlight.common.client.handler.ESClientHandler;
import cn.leolezury.eternalstarlight.common.client.model.entity.OrbModel;
import cn.leolezury.eternalstarlight.common.entity.projectile.BallLightning;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public class BallLightningRenderer extends EntityRenderer<BallLightning> {
   private static final ResourceLocation ENTITY_TEXTURE = EternalStarlight.id("textures/entity/ball_lightning.png");
   private static final ResourceLocation TRAIL_TEXTURE = EternalStarlight.id("textures/entity/trail.png");
   private final OrbModel<BallLightning> model;

   public BallLightningRenderer(Context context) {
      super(context);
      this.model = new OrbModel(context.bakeLayer(OrbModel.LAYER_LOCATION));
   }

   public void render(BallLightning entity, float yaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int light) {
      poseStack.pushPose();
      float yRot = -Mth.rotLerp(partialTicks, entity.yRotO, entity.getYRot());
      float xRot = Mth.lerp(partialTicks, entity.xRotO, entity.getXRot()) - 90.0F;
      float bob = entity.tickCount + partialTicks;
      poseStack.translate(0.0F, entity.getBbHeight() / 2.0F, 0.0F);
      poseStack.scale(-1.0F, -1.0F, 1.0F);
      poseStack.translate(0.0F, -1.5F, 0.0F);
      this.model.prepareMobModel(entity, 0.0F, 0.0F, partialTicks);
      this.model.setupAnim(entity, 0.0F, 0.0F, bob, yRot, xRot);
      this.model.renderToBuffer(poseStack, bufferSource.getBuffer(this.model.renderType(this.getTextureLocation(entity))), 15728880, OverlayTexture.NO_OVERLAY);
      poseStack.popPose();
      Entity target = entity.level().getEntity(entity.getTargetId());
      if (target != null) {
         Vec3 pos = new Vec3(
            Mth.lerp(partialTicks, entity.xo, entity.getX()),
            Mth.lerp(partialTicks, entity.yo, entity.getY()),
            Mth.lerp(partialTicks, entity.zo, entity.getZ())
         );
         Vec3 startPos = new Vec3(
            Mth.lerp(partialTicks, entity.xo, entity.getX()),
            Mth.lerp(partialTicks, entity.yo, entity.getY()) + entity.getBbHeight() / 2.0F,
            Mth.lerp(partialTicks, entity.zo, entity.getZ())
         );
         Vec3 destPos = new Vec3(
            Mth.lerp(partialTicks, target.xo, target.getX()),
            Mth.lerp(partialTicks, target.yo, target.getY()) + target.getBbHeight() / 2.0F,
            Mth.lerp(partialTicks, target.zo, target.getZ())
         );
         List<Vec3> segments = new ArrayList<>();
         int numSegments = Math.round((float)startPos.distanceTo(destPos) / 1.25F * (entity.getRandom().nextFloat() * 0.5F + 1.0F));
         float segmentLength = (float)(destPos.subtract(startPos).length() / numSegments);
         Vec3 increment = destPos.subtract(startPos).scale(1.0 / numSegments);
         segments.add(startPos);

         for (int i = 0; i < numSegments; i++) {
            if (i == numSegments - 1) {
               segments.add(startPos.add(increment.scale(i + 1)));
            } else {
               segments.add(
                  startPos.add(increment.scale(i + 1))
                     .add(
                        new Vec3(entity.getRandom().nextDouble() - 0.5, entity.getRandom().nextDouble() - 0.5, entity.getRandom().nextDouble() - 0.5)
                           .normalize()
                           .scale(segmentLength / 6.0F)
                     )
               );
            }
         }

         segments.add(startPos);
         Vec3 camPos = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
         VertexConsumer vertexConsumer = ESClientHandler.DELAYED_BUFFER_SOURCE.getBuffer(ESRenderType.entityTranslucentGlow(TRAIL_TEXTURE));

         for (int i = 0; i < segments.size() - 1; i++) {
            Vec3 start = segments.get(i);
            Vec3 end = segments.get(i + 1);
            Vec3 offset = end.subtract(start);
            Vec3 sight = camPos.subtract(start).scale(-1.0);
            Vec3 sideOffset = offset.cross(sight).normalize().scale(i == segments.size() - 2 ? 0.035 : 0.03);
            Pose pose = poseStack.last();
            vertexConsumer.addVertex(pose, start.subtract(pos).add(sideOffset).toVector3f())
               .setColor(0.6F, 1.0F, 1.0F, 1.0F)
               .setUv(0.0F, 0.0F)
               .setOverlay(OverlayTexture.NO_OVERLAY)
               .setLight(light)
               .setNormal(pose, 0.0F, 1.0F, 0.0F);
            vertexConsumer.addVertex(pose, start.subtract(pos).add(sideOffset.scale(-1.0)).toVector3f())
               .setColor(0.6F, 1.0F, 1.0F, 1.0F)
               .setUv(0.0F, 1.0F)
               .setOverlay(OverlayTexture.NO_OVERLAY)
               .setLight(light)
               .setNormal(pose, 0.0F, 1.0F, 0.0F);
            vertexConsumer.addVertex(pose, end.subtract(pos).add(sideOffset.scale(-1.0)).toVector3f())
               .setColor(0.6F, 1.0F, 1.0F, 1.0F)
               .setUv(1.0F, 1.0F)
               .setOverlay(OverlayTexture.NO_OVERLAY)
               .setLight(light)
               .setNormal(pose, 0.0F, 1.0F, 0.0F);
            vertexConsumer.addVertex(pose, end.subtract(pos).add(sideOffset).toVector3f())
               .setColor(0.6F, 1.0F, 1.0F, 1.0F)
               .setUv(1.0F, 0.0F)
               .setOverlay(OverlayTexture.NO_OVERLAY)
               .setLight(light)
               .setNormal(pose, 0.0F, 1.0F, 0.0F);
         }
      }

      super.render(entity, yaw, partialTicks, poseStack, bufferSource, light);
   }

   public ResourceLocation getTextureLocation(BallLightning entity) {
      return ENTITY_TEXTURE;
   }
}
