package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.ESRenderType;
import cn.leolezury.eternalstarlight.common.client.model.ESModelUtil;
import cn.leolezury.eternalstarlight.common.client.model.entity.PermafrostModel;
import cn.leolezury.eternalstarlight.common.entity.living.boss.golem.Permafrost;
import cn.leolezury.eternalstarlight.common.util.ModelPartPose;
import cn.leolezury.eternalstarlight.common.util.ModelSnapshot;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
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
import net.minecraft.world.phys.Vec3;

public class PermafrostRenderer<T extends Permafrost> extends MobRenderer<T, PermafrostModel<T>> {
   private static final int SNAPSHOT_LIFESPAN = 8;
   private static final ResourceLocation ENTITY_TEXTURE = EternalStarlight.id("textures/entity/permafrost.png");

   public PermafrostRenderer(Context context) {
      super(context, new PermafrostModel(context.bakeLayer(PermafrostModel.LAYER_LOCATION)), 0.3F);
   }

   public void render(T entity, float yaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int light) {
      super.render(entity, yaw, partialTicks, poseStack, bufferSource, light);
      if (entity.isAlive()) {
         double currentX = Mth.lerp(partialTicks, entity.xo, entity.getX());
         double currentY = Mth.lerp(partialTicks, entity.yo, entity.getY());
         double currentZ = Mth.lerp(partialTicks, entity.zo, entity.getZ());
         float currentTick = this.getBob(entity, partialTicks);
         if (entity.trailSnapshots.isEmpty() || currentTick - entity.lastTrailTick > 1.5) {
            if (entity.shouldAddTrailSnapshot()) {
               Map<String, ModelPartPose> snapshot = ESModelUtil.saveModelSnapshot(
                  ((PermafrostModel)this.getModel()).allPartNames, ((PermafrostModel)this.getModel())::getAnyDescendantWithName
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

            entity.trailSnapshots.removeIf(p -> currentTick - ((ModelSnapshot)p.getSecond()).timestamp() > 8.0F);

            while (entity.trailSnapshots.size() > 32) {
               entity.trailSnapshots.removeLast();
            }
         }

         for (int i = 0; i < entity.trailSnapshots.size(); i++) {
            poseStack.pushPose();
            Vec3 trailPos = (Vec3)entity.trailSnapshots.get(i).getFirst();
            ModelSnapshot snapshot = (ModelSnapshot)entity.trailSnapshots.get(i).getSecond();
            ESModelUtil.loadPoseFromSnapshot(snapshot.poses(), ((PermafrostModel)this.getModel())::getAnyDescendantWithName);
            poseStack.translate(trailPos.x - currentX, trailPos.y - currentY, trailPos.z - currentZ);
            ((PermafrostModel)this.getModel()).alphaFactor = (1.0F - Mth.clamp(currentTick - snapshot.timestamp(), 0.0F, 8.0F) / 8.0F) * 0.3F;
            poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - snapshot.yRot()));
            poseStack.scale(-1.0F, -1.0F, 1.0F);
            this.scale(entity, poseStack, partialTicks);
            poseStack.translate(0.0F, -1.5F, 0.0F);
            RenderType renderType = ESRenderType.entityTranslucentNoDepth(this.getTextureLocation(entity));
            VertexConsumer vertexConsumer = bufferSource.getBuffer(renderType);
            ((PermafrostModel)this.getModel()).renderToBuffer(poseStack, vertexConsumer, light, OverlayTexture.NO_OVERLAY);
            poseStack.popPose();
         }

         ((PermafrostModel)this.getModel()).alphaFactor = 1.0F;
      }
   }

   protected float getFlipDegrees(T entity) {
      return 0.0F;
   }

   public ResourceLocation getTextureLocation(T entity) {
      return ENTITY_TEXTURE;
   }
}
