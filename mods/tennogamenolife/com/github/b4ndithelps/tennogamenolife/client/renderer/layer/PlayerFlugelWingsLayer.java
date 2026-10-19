package com.github.b4ndithelps.tennogamenolife.client.renderer.layer;

import com.github.b4ndithelps.tennogamenolife.client.GodlySmiteAnimationState;
import com.github.b4ndithelps.tennogamenolife.client.model.AngelHalo;
import com.github.b4ndithelps.tennogamenolife.client.model.AngelWings;
import com.github.b4ndithelps.tennogamenolife.client.renderer.AngelHaloRenderer;
import com.github.b4ndithelps.tennogamenolife.client.renderer.AngelWingsRenderer;
import com.github.b4ndithelps.tennogamenolife.race.Flugel.IFlugelRace;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import io.github.manasmods.manascore.race.api.RaceAPI;
import java.util.Map;
import java.util.WeakHashMap;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.player.Player;

public final class PlayerFlugelWingsLayer extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {
   private static final float WING_Y = 0.0F;
   private static final float WING_Z = -2.1F;
   private static final float HALO_X = 7.0F;
   private static final float HALO_Y = -15.36F;
   private static final float HALO_Z = 2.1F;
   private static final float HALO_TILT_Z = 35.0F;
   private final AngelWingsRenderer wingsRenderer = new AngelWingsRenderer();
   private final AngelHaloRenderer haloRenderer = new AngelHaloRenderer();
   private final Map<AbstractClientPlayer, AngelWings> playerWings = new WeakHashMap<>();
   private final Map<AbstractClientPlayer, AngelHalo> playerHalos = new WeakHashMap<>();

   public PlayerFlugelWingsLayer(RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> renderer) {
      super(renderer);
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
      if (!player.isInvisible() && this.isFlugelRace(player)) {
         PlayerModel<AbstractClientPlayer> playerModel = (PlayerModel<AbstractClientPlayer>)this.getParentModel();
         poseStack.pushPose();
         this.applyPlayerBodyTransform(poseStack, playerModel.body);
         poseStack.translate(0.0F, 0.0F, -0.13125F);
         AngelWings wings = this.playerWings
            .computeIfAbsent(player, ignored -> new AngelWings(player.getUUID().getMostSignificantBits() ^ player.getUUID().getLeastSignificantBits()));
         wings.setGodlySmiteChargeProgress(GodlySmiteAnimationState.getWingChargeProgress(player, partialTicks));
         this.wingsRenderer.render(poseStack, wings, buffer, null, null, packedLight, partialTicks);
         poseStack.popPose();
         poseStack.pushPose();
         this.applyPlayerHeadTransform(poseStack, playerModel, player, netHeadYaw, headPitch);
         poseStack.translate(0.4375F, -0.96F, 0.13125F);
         poseStack.mulPose(Axis.ZP.rotationDegrees(35.0F));
         AngelHalo halo = this.playerHalos
            .computeIfAbsent(player, ignored -> new AngelHalo(player.getUUID().getMostSignificantBits() ^ player.getUUID().getLeastSignificantBits()));
         this.haloRenderer.render(poseStack, halo, buffer, null, null, 15728880, partialTicks);
         poseStack.popPose();
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

   private boolean isFlugelRace(Player player) {
      return RaceAPI.getRaceFrom(player).getRace().map(instance -> instance.getRace() instanceof IFlugelRace).orElse(false);
   }
}
