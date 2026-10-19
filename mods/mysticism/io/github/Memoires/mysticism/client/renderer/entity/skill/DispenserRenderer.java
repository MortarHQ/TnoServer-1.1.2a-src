package io.github.Memoires.mysticism.client.renderer.entity.skill;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.Memoires.mysticism.client.model.DispenserModel;
import io.github.Memoires.mysticism.entity.skill.DispenserEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class DispenserRenderer extends GeoEntityRenderer<DispenserEntity> {
   public DispenserRenderer(Context renderManager) {
      super(renderManager, new DispenserModel());
   }

   public void render(DispenserEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
      poseStack.pushPose();
      poseStack.scale(0.5F, 0.5F, 0.5F);
      super.render(entity, entityYaw, partialTicks, poseStack, bufferSource, packedLight);
      poseStack.popPose();
   }
}
