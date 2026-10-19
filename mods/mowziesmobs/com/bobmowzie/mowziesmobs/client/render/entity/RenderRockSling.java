package com.bobmowzie.mowziesmobs.client.render.entity;

import com.bobmowzie.mowziesmobs.client.model.entity.ModelRockSling;
import com.bobmowzie.mowziesmobs.server.entity.effects.geomancy.EntityRockSling;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class RenderRockSling extends GeoEntityRenderer<EntityRockSling> {
   public RenderRockSling(Context renderManager) {
      super(renderManager, new ModelRockSling());
   }

   public void render(EntityRockSling entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
      poseStack.pushPose();
      float risingAnim = 2.0F * (float)Math.pow(0.6 * (entity.tickCount + partialTick + 1.0F), -3.0);
      poseStack.translate(0.0, 0.25 - risingAnim, 0.0);
      super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
      poseStack.popPose();
   }
}
