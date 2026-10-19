package com.chronodawn.client.renderer.mobs;

import com.chronodawn.client.model.PulseHogModel;
import com.chronodawn.compat.CompatResourceLocation;
import com.chronodawn.entities.mobs.PulseHogEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;

public class PulseHogEmissiveLayer extends RenderLayer<PulseHogEntity, PulseHogModel> {
   private static final RenderType EMISSIVE = RenderType.eyes(CompatResourceLocation.create("chronodawn", "textures/entity/mobs/pulse_hog_emissive.png"));

   public PulseHogEmissiveLayer(RenderLayerParent<PulseHogEntity, PulseHogModel> parent) {
      super(parent);
   }

   public void render(
      PoseStack poseStack,
      MultiBufferSource buffer,
      int packedLight,
      PulseHogEntity entity,
      float limbSwing,
      float limbSwingAmount,
      float partialTick,
      float ageInTicks,
      float netHeadYaw,
      float headPitch
   ) {
      VertexConsumer vertexConsumer = buffer.getBuffer(EMISSIVE);
      ((PulseHogModel)this.getParentModel()).renderToBuffer(poseStack, vertexConsumer, 15728880, OverlayTexture.NO_OVERLAY, -1);
   }
}
