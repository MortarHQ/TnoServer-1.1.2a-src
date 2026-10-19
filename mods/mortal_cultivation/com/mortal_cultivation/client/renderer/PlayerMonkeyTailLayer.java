package com.mortal_cultivation.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mortal_cultivation.client.model.PlayerMonkeyTail;
import com.mortal_cultivation.client.model.PlayerMonkeyTailModel;
import com.mortal_cultivation.race.monkey.MonkeyRaceAppearance;
import java.util.Map;
import java.util.WeakHashMap;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;

public final class PlayerMonkeyTailLayer extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {
   private final PlayerMonkeyTailRenderer tailRenderer = new PlayerMonkeyTailRenderer();
   private final Map<AbstractClientPlayer, PlayerMonkeyTail> tails = new WeakHashMap<>();

   public PlayerMonkeyTailLayer(RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> parent) {
      super(parent);
   }

   public void render(
      PoseStack poseStack,
      MultiBufferSource buffers,
      int packedLight,
      AbstractClientPlayer player,
      float limbSwing,
      float limbSwingAmount,
      float partialTick,
      float ageInTicks,
      float netHeadYaw,
      float headPitch
   ) {
      if (!MonkeyRaceAppearance.hasTail(player)) {
         this.tails.remove(player);
      } else if (!player.isInvisible() && !player.isSpectator() && ((PlayerModel)this.getParentModel()).body.visible) {
         PlayerMonkeyTail tail = this.tails.computeIfAbsent(player, owner -> new PlayerMonkeyTail(owner.getId()));
         tail.updateMovement(limbSwingAmount > 0.03F && !player.isSleeping(), player.isSprinting());
         RenderType renderType = RenderType.entityCutoutNoCull(PlayerMonkeyTailModel.TEXTURE);
         poseStack.pushPose();

         try {
            ((PlayerModel)this.getParentModel()).body.translateAndRotate(poseStack);
            poseStack.translate(0.0F, 0.65625F, 0.125F);
            poseStack.scale(-1.0F, -1.0F, 1.0F);
            this.tailRenderer.render(poseStack, tail, buffers, renderType, buffers.getBuffer(renderType), packedLight, partialTick);
         } finally {
            poseStack.popPose();
         }
      }
   }
}
