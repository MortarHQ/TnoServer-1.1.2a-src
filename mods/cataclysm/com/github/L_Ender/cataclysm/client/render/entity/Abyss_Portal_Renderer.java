package com.github.L_Ender.cataclysm.client.render.entity;

import com.github.L_Ender.cataclysm.client.render.CMRenderTypes;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.The_Leviathan.Abyss_Portal_Entity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class Abyss_Portal_Renderer extends EntityRenderer<Abyss_Portal_Entity> {
   private static final ResourceLocation TEXTURE_0 = ResourceLocation.fromNamespaceAndPath(
      "cataclysm", "textures/entity/leviathan/portal/abyss_portal_idle_0.png"
   );
   private static final ResourceLocation TEXTURE_1 = ResourceLocation.fromNamespaceAndPath(
      "cataclysm", "textures/entity/leviathan/portal/abyss_portal_idle_1.png"
   );
   private static final ResourceLocation TEXTURE_2 = ResourceLocation.fromNamespaceAndPath(
      "cataclysm", "textures/entity/leviathan/portal/abyss_portal_idle_2.png"
   );
   private static final ResourceLocation TEXTURE_3 = ResourceLocation.fromNamespaceAndPath(
      "cataclysm", "textures/entity/leviathan/portal/abyss_portal_idle_3.png"
   );
   private static final ResourceLocation[] TEXTURE_PROGRESS = new ResourceLocation[8];
   private static final ResourceLocation[] TEXTURE_IDLE = new ResourceLocation[4];

   public Abyss_Portal_Renderer(Context renderManagerIn) {
      super(renderManagerIn);

      for (int i = 0; i < 8; i++) {
         TEXTURE_PROGRESS[i] = ResourceLocation.fromNamespaceAndPath("cataclysm", "textures/entity/leviathan/portal/abyss_portal_grow_" + i + ".png");
      }

      for (int i = 0; i < 4; i++) {
         TEXTURE_IDLE[i] = ResourceLocation.fromNamespaceAndPath("cataclysm", "textures/entity/leviathan/portal/abyss_portal_idle_" + i + ".png");
      }
   }

   public void render(Abyss_Portal_Entity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
      matrixStackIn.pushPose();
      matrixStackIn.translate(0.0F, 0.01F, 0.0F);
      matrixStackIn.scale(4.0F, 4.0F, 4.0F);
      this.renderPortal(entityIn, matrixStackIn, bufferIn, false);
      matrixStackIn.popPose();
      super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
   }

   private void renderPortal(Abyss_Portal_Entity entityIn, PoseStack matrixStackIn, MultiBufferSource bufferIn, boolean shattered) {
      ResourceLocation tex;
      if (entityIn.getLifespan() < 20) {
         tex = this.getGrowingTexture((int)(entityIn.getLifespan() * 0.5F % 20.0F));
      } else if (entityIn.tickCount < 20) {
         tex = this.getGrowingTexture((int)(entityIn.tickCount * 0.5F % 20.0F));
      } else {
         tex = this.getIdleTexture((int)(entityIn.tickCount * 0.35F % 3.0F));
      }

      VertexConsumer ivertexbuilder = bufferIn.getBuffer(CMRenderTypes.getfullBright(tex));
      this.renderArc(matrixStackIn, ivertexbuilder);
   }

   private void renderArc(PoseStack matrixStackIn, VertexConsumer ivertexbuilder) {
      matrixStackIn.pushPose();
      Pose lvt_19_1_ = matrixStackIn.last();
      this.drawVertex(lvt_19_1_, ivertexbuilder, -1, 0, -1, 0.0F, 0.0F, 1, 0, 1, 240);
      this.drawVertex(lvt_19_1_, ivertexbuilder, -1, 0, 1, 0.0F, 1.0F, 1, 0, 1, 240);
      this.drawVertex(lvt_19_1_, ivertexbuilder, 1, 0, 1, 1.0F, 1.0F, 1, 0, 1, 240);
      this.drawVertex(lvt_19_1_, ivertexbuilder, 1, 0, -1, 1.0F, 0.0F, 1, 0, 1, 240);
      matrixStackIn.popPose();
   }

   public ResourceLocation getTextureLocation(Abyss_Portal_Entity entity) {
      return TEXTURE_0;
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
      return TEXTURE_IDLE[Mth.clamp(age, 0, 3)];
   }

   public ResourceLocation getGrowingTexture(int age) {
      return TEXTURE_PROGRESS[Mth.clamp(age, 0, 7)];
   }
}
