package com.github.L_Ender.cataclysm.client.render.layer;

import com.github.L_Ender.cataclysm.client.render.CMRenderTypes;
import com.github.L_Ender.cataclysm.entity.Deepling.AbstractDeepling;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.FastColor.ARGB32;

public class AbstractDeepling_Layer<T extends AbstractDeepling> extends RenderLayer<T, EntityModel<T>> {
   private final ResourceLocation texture;
   private final RenderType renderType;

   public AbstractDeepling_Layer(RenderLayerParent<T, EntityModel<T>> renderer, ResourceLocation texture) {
      super(renderer);
      this.texture = texture;
      this.renderType = CMRenderTypes.CMEyes(texture);
   }

   public void render(
      PoseStack matrixStackIn,
      MultiBufferSource bufferIn,
      int packedLightIn,
      T entitylivingbaseIn,
      float limbSwing,
      float limbSwingAmount,
      float partialTicks,
      float ageInTicks,
      float netHeadYaw,
      float headPitch
   ) {
      VertexConsumer VertexConsumer = bufferIn.getBuffer(this.renderType);
      float strength = 0.5F + Mth.clamp((float)Math.cos((entitylivingbaseIn.LayerTicks + partialTicks) * 0.1F) - 0.5F, -0.5F, 0.5F);
      strength += Mth.lerp(partialTicks, entitylivingbaseIn.oLayerBrightness, entitylivingbaseIn.LayerBrightness) * 1.0F * (float) Math.PI;
      strength = Mth.clamp(strength, 0.1F, 1.0F) * 255.0F;
      int i = ARGB32.color(255, (int)strength, (int)strength, (int)strength);
      this.getParentModel().renderToBuffer(matrixStackIn, VertexConsumer, 15728640, OverlayTexture.NO_OVERLAY, i);
   }
}
