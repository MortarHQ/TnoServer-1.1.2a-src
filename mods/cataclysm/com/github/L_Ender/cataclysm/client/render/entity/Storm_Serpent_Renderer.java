package com.github.L_Ender.cataclysm.client.render.entity;

import com.github.L_Ender.cataclysm.client.model.CMModelLayers;
import com.github.L_Ender.cataclysm.client.model.entity.Storm_Serpent_Model;
import com.github.L_Ender.cataclysm.client.render.CMRenderTypes;
import com.github.L_Ender.cataclysm.entity.projectile.Storm_Serpent_Entity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.FastColor.ARGB32;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Storm_Serpent_Renderer extends EntityRenderer<Storm_Serpent_Entity> {
   private static final ResourceLocation SNAKE = ResourceLocation.fromNamespaceAndPath("cataclysm", "textures/entity/scylla/storm_serpent.png");
   private final Storm_Serpent_Model model;

   public Storm_Serpent_Renderer(Context renderManagerIn) {
      super(renderManagerIn);
      this.model = new Storm_Serpent_Model(renderManagerIn.bakeLayer(CMModelLayers.STORM_SERPENT_MODEL));
   }

   public void render(
      Storm_Serpent_Entity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn
   ) {
      matrixStackIn.pushPose();
      matrixStackIn.mulPose(Axis.YP.rotationDegrees(-90.0F));
      matrixStackIn.translate(0.0, 1.0, 0.0);
      matrixStackIn.scale(-1.0F, -1.0F, 1.0F);
      float f = Mth.rotLerp(partialTicks, entityIn.yRotO, entityIn.getYRot());
      float f1 = Mth.lerp(partialTicks, entityIn.xRotO, entityIn.getXRot());
      this.model.setupAnim(entityIn, 0.0F, 0.0F, entityIn.tickCount + partialTicks, f, f1);
      float alpha = 0.8F;
      int i1 = ARGB32.color((int)(alpha * 255.0F), 255, 255, 255);
      VertexConsumer vertexConsumer = bufferIn.getBuffer(CMRenderTypes.getGhost(this.getTextureLocation(entityIn)));
      this.model.renderToBuffer(matrixStackIn, vertexConsumer, packedLightIn, OverlayTexture.NO_OVERLAY, i1);
      matrixStackIn.popPose();
      super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
   }

   protected int getBlockLightLevel(Storm_Serpent_Entity entityIn, BlockPos pos) {
      return 15;
   }

   public ResourceLocation getTextureLocation(Storm_Serpent_Entity entity) {
      return SNAKE;
   }
}
