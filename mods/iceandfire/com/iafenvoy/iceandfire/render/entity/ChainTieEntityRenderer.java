package com.iafenvoy.iceandfire.render.entity;

import com.iafenvoy.iceandfire.entity.ChainTieEntity;
import com.iafenvoy.iceandfire.render.model.ChainTieModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class ChainTieEntityRenderer extends EntityRenderer<ChainTieEntity> {
   private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("iceandfire", "textures/entity/misc/chain_tie.png");
   private final ChainTieModel leashKnotModel = new ChainTieModel();

   public ChainTieEntityRenderer(Context context) {
      super(context);
   }

   public void render(ChainTieEntity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
      matrixStackIn.pushPose();
      matrixStackIn.translate(0.0F, 0.5F, 0.0F);
      matrixStackIn.scale(-1.0F, -1.0F, 1.0F);
      this.leashKnotModel.setAngles(entityIn, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
      VertexConsumer ivertexbuilder = bufferIn.getBuffer(RenderType.entityCutoutNoCull(TEXTURE));
      this.leashKnotModel.renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, -1);
      matrixStackIn.popPose();
   }

   public ResourceLocation getTexture(ChainTieEntity entity) {
      return TEXTURE;
   }
}
