package com.faboslav.friendsandfoes.common.client.render.entity.renderer;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.client.render.entity.model.IceologerIceChunkModel;
import com.faboslav.friendsandfoes.common.entity.IceologerIceChunkEntity;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public final class IceologerIceChunkRenderer extends EntityRenderer<IceologerIceChunkEntity> {
   private static final ResourceLocation TEXTURE = FriendsAndFoes.makeID("textures/entity/illager/ice_chunk.png");
   private final IceologerIceChunkModel<IceologerIceChunkEntity> model;

   public IceologerIceChunkRenderer(Context context) {
      super(context);
      this.model = new IceologerIceChunkModel<>(context.bakeLayer(FriendsAndFoesEntityModelLayers.ICEOLOGER_ICE_CHUNK_LAYER));
   }

   public void render(IceologerIceChunkEntity iceChunk, float f, float tickDelta, PoseStack poseStack, MultiBufferSource multiBufferSource, int i) {
      float ageInTicks = Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(true);
      float animationProgress = iceChunk.getSummonAnimationProgress();
      if (animationProgress != 0.0F) {
         float summonAnimationProgress = Mth.lerp(ageInTicks, iceChunk.getLastSummonAnimationProgress(), iceChunk.getSummonAnimationProgress());
         poseStack.pushPose();
         poseStack.mulPose(Axis.YP.rotationDegrees(-90.0F - iceChunk.getYRot()));
         this.model.setupAnim(iceChunk, 0.0F, 0.0F, 0.0F, iceChunk.getYRot(), iceChunk.getXRot());
         poseStack.scale(summonAnimationProgress, summonAnimationProgress, summonAnimationProgress);
         VertexConsumer vertexConsumer = multiBufferSource.getBuffer(this.model.renderType(TEXTURE));
         this.model.setupAnim(iceChunk, 0.0F, 0.0F, 0.0F, iceChunk.getYRot(), iceChunk.getXRot());
         this.model.prepareMobModel(iceChunk, 0.0F, 0.0F, tickDelta);
         this.model.renderToBuffer(poseStack, vertexConsumer, i, OverlayTexture.NO_OVERLAY, -1);
         poseStack.popPose();
         super.render(iceChunk, f, tickDelta, poseStack, multiBufferSource, i);
      }
   }

   public ResourceLocation getTextureLocation(IceologerIceChunkEntity iceologerIceChunk) {
      return TEXTURE;
   }
}
