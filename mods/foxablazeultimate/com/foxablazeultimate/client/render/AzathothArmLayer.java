package com.foxablazeultimate.client.render;

import com.foxablazeultimate.client.AzathothChargeVisuals;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.entity.HumanoidArm;

public final class AzathothArmLayer extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {
   public AzathothArmLayer(PlayerRenderer renderer) {
      super(renderer);
   }

   public void render(
      PoseStack pose,
      MultiBufferSource buffers,
      int light,
      AbstractClientPlayer player,
      float limbSwing,
      float limbSwingAmount,
      float partialTick,
      float age,
      float yaw,
      float pitch
   ) {
      ModelPart arm = player.getMainArm() == HumanoidArm.RIGHT ? ((PlayerModel)this.getParentModel()).rightArm : ((PlayerModel)this.getParentModel()).leftArm;
      AzathothChargeVisuals.renderArm(player, arm, pose, buffers, partialTick);
   }
}
