package com.github.L_Ender.cataclysm.client.render.layer;

import com.github.L_Ender.cataclysm.client.model.entity.Lionfish_Model;
import com.github.L_Ender.cataclysm.client.render.CMRenderTypes;
import com.github.L_Ender.cataclysm.client.render.entity.Lionfish_Renderer;
import com.github.L_Ender.cataclysm.entity.Deepling.Lionfish_Entity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.FastColor.ARGB32;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LionFish_Layer extends RenderLayer<Lionfish_Entity, Lionfish_Model> {
   private static final ResourceLocation LION_LAYER_TEXTURES = ResourceLocation.fromNamespaceAndPath("cataclysm", "textures/entity/deepling/lionfish_layer.png");

   public LionFish_Layer(Lionfish_Renderer renderIn) {
      super(renderIn);
   }

   public void render(
      PoseStack matrixStackIn,
      MultiBufferSource bufferIn,
      int packedLightIn,
      Lionfish_Entity entity,
      float limbSwing,
      float limbSwingAmount,
      float partialTicks,
      float ageInTicks,
      float netHeadYaw,
      float headPitch
   ) {
      RenderType eyes = CMRenderTypes.CMEyes(LION_LAYER_TEXTURES);
      VertexConsumer VertexConsumer = bufferIn.getBuffer(eyes);
      float strength = 0.5F + Mth.clamp((float)Math.cos((entity.LayerTicks + partialTicks) * 0.1F) - 0.5F, -0.5F, 0.5F);
      strength += Mth.lerp(partialTicks, entity.oLayerBrightness, entity.LayerBrightness) * 1.0F * (float) Math.PI;
      strength = Mth.clamp(strength, 0.1F, 1.0F) * 255.0F;
      int i = ARGB32.color(255, (int)strength, (int)strength, (int)strength);
      ((Lionfish_Model)this.getParentModel()).renderToBuffer(matrixStackIn, VertexConsumer, 15728640, OverlayTexture.NO_OVERLAY, i);
   }
}
