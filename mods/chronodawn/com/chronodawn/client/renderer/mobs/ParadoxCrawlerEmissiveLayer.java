package com.chronodawn.client.renderer.mobs;

import com.chronodawn.client.model.ParadoxCrawlerModel;
import com.chronodawn.compat.CompatResourceLocation;
import com.chronodawn.entities.mobs.ParadoxCrawlerEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;

public class ParadoxCrawlerEmissiveLayer extends RenderLayer<ParadoxCrawlerEntity, ParadoxCrawlerModel> {
   private static final RenderType EMISSIVE = RenderType.eyes(CompatResourceLocation.create("chronodawn", "textures/entity/mobs/paradox_crawler_emissive.png"));

   public ParadoxCrawlerEmissiveLayer(RenderLayerParent<ParadoxCrawlerEntity, ParadoxCrawlerModel> parent) {
      super(parent);
   }

   public void render(
      PoseStack poseStack,
      MultiBufferSource buffer,
      int packedLight,
      ParadoxCrawlerEntity entity,
      float limbSwing,
      float limbSwingAmount,
      float partialTick,
      float ageInTicks,
      float netHeadYaw,
      float headPitch
   ) {
      VertexConsumer vertexConsumer = buffer.getBuffer(EMISSIVE);
      ((ParadoxCrawlerModel)this.getParentModel()).renderToBuffer(poseStack, vertexConsumer, 15728880, OverlayTexture.NO_OVERLAY, -1);
   }
}
