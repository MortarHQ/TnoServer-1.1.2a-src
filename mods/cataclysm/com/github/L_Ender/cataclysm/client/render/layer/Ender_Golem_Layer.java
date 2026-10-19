package com.github.L_Ender.cataclysm.client.render.layer;

import com.github.L_Ender.cataclysm.client.model.entity.Ender_Golem_Model;
import com.github.L_Ender.cataclysm.client.render.entity.Ender_Golem_Renderer;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.Ender_Golem_Entity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor.ARGB32;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Ender_Golem_Layer extends RenderLayer<Ender_Golem_Entity, Ender_Golem_Model> {
   private static final ResourceLocation ENDER_GOLEM_LAYER_TEXTURES = ResourceLocation.fromNamespaceAndPath(
      "cataclysm", "textures/entity/ender_golem_layer.png"
   );

   public Ender_Golem_Layer(Ender_Golem_Renderer renderIn) {
      super(renderIn);
   }

   public void render(
      PoseStack matrixStackIn,
      MultiBufferSource bufferIn,
      int packedLightIn,
      Ender_Golem_Entity entity,
      float limbSwing,
      float limbSwingAmount,
      float partialTicks,
      float ageInTicks,
      float netHeadYaw,
      float headPitch
   ) {
      if (entity.deathTime <= 45) {
         int f = (int)(255.0F - entity.deactivateProgress * 255.0F / 30.0F);
         RenderType eyes = RenderType.eyes(ENDER_GOLEM_LAYER_TEXTURES);
         VertexConsumer VertexConsumer = bufferIn.getBuffer(eyes);
         int i = ARGB32.color(f, f, f);
         ((Ender_Golem_Model)this.getParentModel()).renderToBuffer(matrixStackIn, VertexConsumer, packedLightIn, OverlayTexture.NO_OVERLAY, i);
      }
   }
}
