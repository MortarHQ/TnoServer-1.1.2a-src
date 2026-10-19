package com.github.wal_bos.moreskills.client.renderer;

import com.github.wal_bos.moreskills.world.entity.ArsWeissCloneEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class ArsWeissCloneRenderer extends LivingEntityRenderer<ArsWeissCloneEntity, PlayerModel<ArsWeissCloneEntity>> {
   private static final ResourceLocation FALLBACK_SKIN = ResourceLocation.withDefaultNamespace("textures/entity/player/wide/steve.png");

   public ArsWeissCloneRenderer(Context context) {
      super(context, new PlayerModel(context.bakeLayer(ModelLayers.PLAYER), false), 0.5F);
      HumanoidModel<ArsWeissCloneEntity> innerArmor = new HumanoidModel(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR));
      HumanoidModel<ArsWeissCloneEntity> outerArmor = new HumanoidModel(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR));
      this.addLayer(new HumanoidArmorLayer(this, innerArmor, outerArmor, context.getModelManager()));
      this.addLayer(new ItemInHandLayer(this, context.getItemInHandRenderer()));
      this.addLayer(new ArsWeissCloneRenderer.IceOverlayLayer(this));
   }

   public ResourceLocation getTextureLocation(ArsWeissCloneEntity entity) {
      return getClientOwner(entity) instanceof AbstractClientPlayer clientPlayer ? clientPlayer.getSkin().texture() : FALLBACK_SKIN;
   }

   protected void scale(ArsWeissCloneEntity entity, PoseStack poseStack, float partialTickTime) {
      poseStack.scale(0.98F, 0.98F, 0.98F);
   }

   private static Player getClientOwner(ArsWeissCloneEntity entity) {
      UUID uuid = entity.getOwnerUUIDValue();
      if (uuid == null) {
         return null;
      }

      ClientLevel level = Minecraft.getInstance().level;
      return level == null ? null : level.getPlayerByUUID(uuid);
   }

   private static final class IceOverlayLayer extends RenderLayer<ArsWeissCloneEntity, PlayerModel<ArsWeissCloneEntity>> {
      private static final ResourceLocation ICE = ResourceLocation.withDefaultNamespace("textures/block/ice.png");

      private IceOverlayLayer(ArsWeissCloneRenderer renderer) {
         super(renderer);
      }

      public void render(
         PoseStack poseStack,
         MultiBufferSource buffer,
         int packedLight,
         ArsWeissCloneEntity entity,
         float limbSwing,
         float limbSwingAmount,
         float partialTick,
         float ageInTicks,
         float netHeadYaw,
         float headPitch
      ) {
         ((PlayerModel)this.getParentModel())
            .renderToBuffer(
               poseStack, buffer.getBuffer(RenderType.entityTranslucent(ICE)), packedLight, LivingEntityRenderer.getOverlayCoords(entity, 0.0F), 1220015359
            );
      }
   }
}
