package com.github.L_Ender.cataclysm.client.render.entity;

import com.github.L_Ender.cataclysm.entity.effect.Abyss_Mark_Entity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Abyss_Mark_Renderer extends EntityRenderer<Abyss_Mark_Entity> {
   public static final ResourceLocation ABYSS_MARK1 = ResourceLocation.fromNamespaceAndPath("cataclysm", "textures/entity/deepling/deepling_warlock_mark1.png");
   public static final ResourceLocation ABYSS_MARK2 = ResourceLocation.fromNamespaceAndPath("cataclysm", "textures/entity/deepling/deepling_warlock_mark2.png");

   public Abyss_Mark_Renderer(Context mgr) {
      super(mgr);
   }

   public ResourceLocation getTextureLocation(Abyss_Mark_Entity entity) {
      float f = entity.getLifespan();
      return f > -1.0F && f / 5.0F % 2.0F == 0.0F ? ABYSS_MARK2 : ABYSS_MARK1;
   }

   public void render(Abyss_Mark_Entity flameStrike, float entityYaw, float delta, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
      matrixStackIn.pushPose();
      VertexConsumer ivertexbuilder = bufferIn.getBuffer(RenderType.entityTranslucentEmissive(this.getTextureLocation(flameStrike)));
      matrixStackIn.scale(1.0F, 1.0F, 1.0F);
      matrixStackIn.translate(0.0, 0.001, 0.0);
      Pose lvt_19_1_ = matrixStackIn.last();
      this.drawVertex(lvt_19_1_, ivertexbuilder, -1, 0, -1, 0.0F, 0.0F, 1, 0, 1, 240);
      this.drawVertex(lvt_19_1_, ivertexbuilder, -1, 0, 1, 0.0F, 1.0F, 1, 0, 1, 240);
      this.drawVertex(lvt_19_1_, ivertexbuilder, 1, 0, 1, 1.0F, 1.0F, 1, 0, 1, 240);
      this.drawVertex(lvt_19_1_, ivertexbuilder, 1, 0, -1, 1.0F, 0.0F, 1, 0, 1, 240);
      matrixStackIn.popPose();
      super.render(flameStrike, entityYaw, delta, matrixStackIn, bufferIn, packedLightIn);
   }

   public void drawVertex(
      Pose p_229039_2_,
      VertexConsumer p_229039_3_,
      int p_229039_4_,
      int p_229039_5_,
      int p_229039_6_,
      float p_229039_7_,
      float p_229039_8_,
      int p_229039_9_,
      int p_229039_10_,
      int p_229039_11_,
      int p_229039_12_
   ) {
      p_229039_3_.addVertex(p_229039_2_, p_229039_4_, p_229039_5_, p_229039_6_)
         .setColor(255, 255, 255, 255)
         .setUv(p_229039_7_, p_229039_8_)
         .setOverlay(OverlayTexture.NO_OVERLAY)
         .setLight(p_229039_12_)
         .setNormal(p_229039_2_, p_229039_9_, p_229039_11_, p_229039_10_);
   }
}
