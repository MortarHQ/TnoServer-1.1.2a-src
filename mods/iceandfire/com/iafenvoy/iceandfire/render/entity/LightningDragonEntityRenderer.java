package com.iafenvoy.iceandfire.render.entity;

import com.iafenvoy.iceandfire.entity.LightningDragonEntity;
import com.iafenvoy.iceandfire.render.misc.LightningBoltData;
import com.iafenvoy.iceandfire.render.misc.LightningRenderer;
import com.iafenvoy.uranus.client.model.TabulaModel;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class LightningDragonEntityRenderer extends DragonBaseEntityRenderer<LightningDragonEntity> {
   private final LightningRenderer lightningRenderer = new LightningRenderer();

   public LightningDragonEntityRenderer(Context context, TabulaModel<LightningDragonEntity> modelSupplier) {
      super(context, modelSupplier);
   }

   private static float getBoundedScale(float scale) {
      return 0.5F + scale * 1.5F;
   }

   public boolean shouldRender(LightningDragonEntity livingEntityIn, Frustum camera, double camX, double camY, double camZ) {
      if (super.shouldRender(livingEntityIn, camera, camX, camY, camZ)) {
         return true;
      } else if (livingEntityIn.hasLightningTarget()) {
         Vec3 head = livingEntityIn.getHeadPosition();
         Vec3 target = new Vec3(livingEntityIn.getLightningTargetX(), livingEntityIn.getLightningTargetY(), livingEntityIn.getLightningTargetZ());
         return camera.isVisible(new AABB(head.x, head.y, head.z, target.x, target.y, target.z));
      } else {
         return false;
      }
   }

   public void render(
      LightningDragonEntity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn
   ) {
      super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
      matrixStackIn.pushPose();
      if (entityIn.hasLightningTarget()) {
         Minecraft client = Minecraft.getInstance();
         assert client.player != null;
         double dist = client.player.distanceTo(entityIn);
         if (dist <= Math.max(256.0F, ((Integer)client.options.renderDistance().get()).intValue() * 16.0F)) {
            Vec3 Vector3d1 = entityIn.getHeadPosition();
            Vec3 Vector3d = new Vec3(entityIn.getLightningTargetX(), entityIn.getLightningTargetY(), entityIn.getLightningTargetZ());
            float energyScale = 0.4F * entityIn.getAgeScale();
            LightningBoltData bolt = new LightningBoltData(LightningBoltData.BoltRenderInfo.ELECTRICITY, Vector3d1, Vector3d, 15)
               .size(0.05F * getBoundedScale(energyScale))
               .lifespan(4)
               .spawn(LightningBoltData.SpawnFunction.NO_DELAY);
            this.lightningRenderer.update(null, bolt, partialTicks);
            matrixStackIn.translate(-entityIn.getX(), -entityIn.getY(), -entityIn.getZ());
            this.lightningRenderer.render(partialTicks, matrixStackIn, bufferIn);
         }
      }

      matrixStackIn.popPose();
   }
}
