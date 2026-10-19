package com.github.L_Ender.cataclysm.client.render.entity;

import com.github.L_Ender.cataclysm.client.render.CMRenderTypes;
import com.github.L_Ender.cataclysm.entity.effect.Void_Vortex_Entity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Void_Vortex_Renderer extends EntityRenderer<Void_Vortex_Entity> {
   private static final ResourceLocation TEXTURE_1 = ResourceLocation.fromNamespaceAndPath("cataclysm", "textures/entity/void_vortex/void_vortex_idle1.png");
   private static final ResourceLocation TEXTURE_2 = ResourceLocation.fromNamespaceAndPath("cataclysm", "textures/entity/void_vortex/void_vortex_idle2.png");
   private static final ResourceLocation TEXTURE_3 = ResourceLocation.fromNamespaceAndPath("cataclysm", "textures/entity/void_vortex/void_vortex_idle3.png");
   private static final ResourceLocation TEXTURE_4 = ResourceLocation.fromNamespaceAndPath("cataclysm", "textures/entity/void_vortex/void_vortex_idle4.png");
   private static final ResourceLocation[] TEXTURE_PROGRESS = new ResourceLocation[4];

   public Void_Vortex_Renderer(Context mgr) {
      super(mgr);

      for (int i = 0; i < 4; i++) {
         TEXTURE_PROGRESS[i] = ResourceLocation.fromNamespaceAndPath("cataclysm", "textures/entity/void_vortex/void_vortex_grow_" + i + ".png");
      }
   }

   public void render(Void_Vortex_Entity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
      matrixStackIn.pushPose();
      matrixStackIn.translate(0.0, 0.001, 0.0);
      ResourceLocation tex;
      if (entityIn.getLifespan() < 16) {
         tex = this.getGrowingTexture((int)(entityIn.getLifespan() * 0.5F % 20.0F));
      } else if (entityIn.tickCount < 16) {
         tex = this.getGrowingTexture((int)(entityIn.tickCount * 0.5F % 20.0F));
      } else {
         tex = this.getIdleTexture(entityIn.tickCount % 9);
      }

      matrixStackIn.scale(3.0F, 3.0F, 3.0F);
      this.renderArc(matrixStackIn, bufferIn, tex);
      matrixStackIn.popPose();
      super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
   }

   private void renderArc(PoseStack matrixStackIn, MultiBufferSource bufferIn, ResourceLocation res) {
      matrixStackIn.pushPose();
      VertexConsumer ivertexbuilder = bufferIn.getBuffer(CMRenderTypes.getfullBright(res));
      Pose lvt_19_1_ = matrixStackIn.last();
      this.drawVertex(lvt_19_1_, ivertexbuilder, -1, 0, -1, 0.0F, 0.0F, 1, 0, 1, 240);
      this.drawVertex(lvt_19_1_, ivertexbuilder, -1, 0, 1, 0.0F, 1.0F, 1, 0, 1, 240);
      this.drawVertex(lvt_19_1_, ivertexbuilder, 1, 0, 1, 1.0F, 1.0F, 1, 0, 1, 240);
      this.drawVertex(lvt_19_1_, ivertexbuilder, 1, 0, -1, 1.0F, 0.0F, 1, 0, 1, 240);
      matrixStackIn.popPose();
   }

   public ResourceLocation getTextureLocation(Void_Vortex_Entity entity) {
      return TEXTURE_1;
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

   public ResourceLocation getIdleTexture(int age) {
      if (age < 3) {
         return TEXTURE_1;
      } else if (age < 6) {
         return TEXTURE_2;
      } else {
         return age < 10 ? TEXTURE_3 : TEXTURE_4;
      }
   }

   public ResourceLocation getGrowingTexture(int age) {
      return TEXTURE_PROGRESS[Mth.clamp(age / 2, 0, 3)];
   }
}
