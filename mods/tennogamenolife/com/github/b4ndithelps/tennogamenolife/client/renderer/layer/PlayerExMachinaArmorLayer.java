package com.github.b4ndithelps.tennogamenolife.client.renderer.layer;

import com.github.b4ndithelps.tennogamenolife.client.model.XiubiArmorModel;
import com.github.b4ndithelps.tennogamenolife.race.ExMachina.ExMachinaDecorativeManager;
import com.github.b4ndithelps.tennogamenolife.race.ExMachina.IExMachinaDecorativeRace;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import io.github.manasmods.manascore.race.api.RaceAPI;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class PlayerExMachinaArmorLayer extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {
   private static final ResourceLocation ARMOR_TEXTURE = ResourceLocation.fromNamespaceAndPath("tennogamenolife", "textures/entity/xiubi/armor.png");
   private final XiubiArmorModel armorModel;

   public PlayerExMachinaArmorLayer(RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> renderer, XiubiArmorModel armorModel) {
      super(renderer);
      this.armorModel = armorModel;
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
         ExMachinaDecorativeManager.updateDecorativeState(player);
         PlayerModel<AbstractClientPlayer> playerModel = (PlayerModel<AbstractClientPlayer>)this.getParentModel();
         this.armorModel.setupAnimForPlayer(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
         this.applyArmorSpecificAnimations(player, ageInTicks);
         VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityCutoutNoCull(ARMOR_TEXTURE));
         poseStack.pushPose();
         this.applyPlayerBodyTransform(poseStack, playerModel.body);
         this.armorModel.renderBodyDecorations(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, -1);
         poseStack.popPose();
         poseStack.pushPose();
         this.applyPlayerHeadTransform(poseStack, playerModel, player, netHeadYaw, headPitch);
         this.armorModel.renderHeadDecorations(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, -1);
         poseStack.popPose();
      }
   }

   private void applyArmorSpecificAnimations(Player player, float ageInTicks) {
      ExMachinaDecorativeManager.ArmorAnimationState animState = ExMachinaDecorativeManager.getArmorAnimationState(player);
      int animTimer = ExMachinaDecorativeManager.getAnimationTimer(player);
      switch (animState) {
         case WEAPON:
            this.armorModel.applyWeaponAnimation(animTimer);
            break;
         case CANCEL_WEAPON:
            this.armorModel.applyCancelWeaponAnimation(animTimer);
            break;
         case IDLE:
            this.armorModel.applyIdleAnimation((int)ageInTicks);
      }
   }

   private void applyPlayerBodyTransform(PoseStack poseStack, ModelPart playerBody) {
      poseStack.translate(playerBody.x / 16.0F, playerBody.y / 16.0F, playerBody.z / 16.0F);
      poseStack.mulPose(Axis.XP.rotation(playerBody.xRot));
      poseStack.mulPose(Axis.YP.rotation(playerBody.yRot));
      poseStack.mulPose(Axis.ZP.rotation(playerBody.zRot));
      poseStack.scale(playerBody.xScale, playerBody.yScale, playerBody.zScale);
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
