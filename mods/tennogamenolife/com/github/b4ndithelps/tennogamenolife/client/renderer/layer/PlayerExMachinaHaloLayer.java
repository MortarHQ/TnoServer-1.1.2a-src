package com.github.b4ndithelps.tennogamenolife.client.renderer.layer;

import com.github.b4ndithelps.tennogamenolife.client.model.XiubiHaloModel;
import com.github.b4ndithelps.tennogamenolife.race.ExMachina.ExMachinaDecorativeManager;
import com.github.b4ndithelps.tennogamenolife.race.ExMachina.IExMachinaDecorativeRace;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import io.github.manasmods.manascore.race.api.RaceAPI;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class PlayerExMachinaHaloLayer extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {
   private static final ResourceLocation HALO_TEXTURE = ResourceLocation.fromNamespaceAndPath("tennogamenolife", "textures/entity/xiubi/halo.png");
   private final XiubiHaloModel haloModel;

   public PlayerExMachinaHaloLayer(RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> renderer, XiubiHaloModel haloModel) {
      super(renderer);
      this.haloModel = haloModel;
   }

   public void render(
      PoseStack poseStack,
      MultiBufferSource buffer,
      int packedLight,
      AbstractClientPlayer player,
      float limbSwing,
      float limbSwingAmount,
      float partialTicks,
      float ageInTicks,
      float netHeadYaw,
      float headPitch
   ) {
      if (!player.isInvisible() && this.isExMachinaRace(player) && ExMachinaDecorativeManager.shouldShowDecorative(player)) {
         PlayerModel<AbstractClientPlayer> playerModel = (PlayerModel<AbstractClientPlayer>)this.getParentModel();
         this.haloModel.setupAnim(player, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
         poseStack.pushPose();
         this.applyPlayerHeadTransform(poseStack, playerModel, player, netHeadYaw, headPitch);
         this.haloModel
            .renderToBuffer(poseStack, buffer.getBuffer(RenderType.entityTranslucent(HALO_TEXTURE)), packedLight, OverlayTexture.NO_OVERLAY, -855638017);
         poseStack.popPose();
      }
   }

   private void applyPlayerHeadTransform(
      PoseStack poseStack, PlayerModel<AbstractClientPlayer> playerModel, AbstractClientPlayer player, float netHeadYaw, float headPitch
   ) {
      poseStack.translate(playerModel.head.x / 16.0F, playerModel.head.y / 16.0F, playerModel.head.z / 16.0F);
      poseStack.mulPose(Axis.YP.rotation((float)Math.toRadians(netHeadYaw)));
      poseStack.mulPose(Axis.XP.rotation((float)Math.toRadians(headPitch)));
      float yTranslation = 0.05F * player.getScale();
      if (player.isCrouching()) {
         yTranslation -= 0.1F * player.getScale();
      }

      poseStack.translate(0.0F, yTranslation, 0.0F);
      poseStack.scale(playerModel.head.xScale, playerModel.head.yScale, playerModel.head.zScale);
   }

   private boolean isExMachinaRace(Player player) {
      return RaceAPI.getRaceFrom(player).getRace().map(instance -> instance.getRace() instanceof IExMachinaDecorativeRace).orElse(false);
   }
}
