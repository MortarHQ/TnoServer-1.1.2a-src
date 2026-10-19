package io.github.Memoires.mysticism.client.renderer.entity.skill;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.Memoires.mysticism.client.model.GateEntityModel;
import io.github.Memoires.mysticism.entity.skill.GateEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class GateEntityRenderer extends GeoEntityRenderer<GateEntity> {
   public GateEntityRenderer(Context renderManager) {
      super(renderManager, new GateEntityModel());
   }

   public void render(GateEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
      poseStack.pushPose();
      poseStack.scale(0.4F, 0.4F, 0.4F);
      super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
      poseStack.popPose();
   }
}
