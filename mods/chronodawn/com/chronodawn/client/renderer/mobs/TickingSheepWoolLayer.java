package com.chronodawn.client.renderer.mobs;

import com.chronodawn.client.model.TickingSheepBodyModel;
import com.chronodawn.client.model.TickingSheepWoolModel;
import com.chronodawn.compat.CompatResourceLocation;
import com.chronodawn.entities.mobs.TickingSheepEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class TickingSheepWoolLayer extends RenderLayer<TickingSheepEntity, TickingSheepBodyModel> {
   private static final ResourceLocation WOOL_TEXTURE = CompatResourceLocation.create("chronodawn", "textures/entity/mobs/ticking_sheep.png");
   private final TickingSheepWoolModel woolModel;

   public TickingSheepWoolLayer(RenderLayerParent<TickingSheepEntity, TickingSheepBodyModel> parent, TickingSheepWoolModel woolModel) {
      super(parent);
      this.woolModel = woolModel;
   }

   public void render(
      PoseStack poseStack,
      MultiBufferSource buffer,
      int packedLight,
      TickingSheepEntity entity,
      float limbSwing,
      float limbSwingAmount,
      float partialTick,
      float ageInTicks,
      float netHeadYaw,
      float headPitch
   ) {
      if (!entity.isInvisible() && !entity.isSheared()) {
         this.woolModel.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
         VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityCutoutNoCull(WOOL_TEXTURE));
         this.woolModel.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, -1);
      }
   }
}
