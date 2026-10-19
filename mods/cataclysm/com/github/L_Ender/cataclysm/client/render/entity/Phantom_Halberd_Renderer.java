package com.github.L_Ender.cataclysm.client.render.entity;

import com.github.L_Ender.cataclysm.client.model.entity.Phantom_Halberd_Model;
import com.github.L_Ender.cataclysm.client.render.CMRenderTypes;
import com.github.L_Ender.cataclysm.entity.projectile.Phantom_Halberd_Entity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
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
public class Phantom_Halberd_Renderer extends EntityRenderer<Phantom_Halberd_Entity> {
   private static final ResourceLocation PHANTOM_HALBERD = ResourceLocation.fromNamespaceAndPath("cataclysm", "textures/entity/maledictus/phantom_halberd.png");
   private static final ResourceLocation PHANTOM_HALBERD_DISCARD = ResourceLocation.fromNamespaceAndPath(
      "cataclysm", "textures/entity/maledictus/phantom_halberd_discard.png"
   );
   private final Phantom_Halberd_Model model = new Phantom_Halberd_Model();
   private static final RenderType DECAL = RenderType.entityDecal(PHANTOM_HALBERD);
   private static final RenderType RENDER_TYPE = RenderType.entityCutoutNoCull(PHANTOM_HALBERD);

   public Phantom_Halberd_Renderer(Context renderManagerIn) {
      super(renderManagerIn);
   }

   public void render(
      Phantom_Halberd_Entity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn
   ) {
      matrixStackIn.pushPose();
      matrixStackIn.mulPose(Axis.YP.rotationDegrees(90.0F - entityIn.getYRot()));
      matrixStackIn.translate(0.0, 1.0, 0.0);
      matrixStackIn.scale(-0.8F, -0.8F, 0.8F);
      VertexConsumer vertexConsumer = bufferIn.getBuffer(CMRenderTypes.getGhost(this.getTextureLocation(entityIn)));
      this.model.setupAnim(entityIn, 0.0F, 0.0F, entityIn.tickCount + partialTicks, 0.0F, 0.0F);
      if (entityIn.lifeTicks > 0) {
         float f2 = entityIn.lifeTicks / 70.0F;
         int i = ARGB32.color(Mth.floor(f2 * 255.0F), -1);
         VertexConsumer vertexconsumer = bufferIn.getBuffer(RenderType.dragonExplosionAlpha(PHANTOM_HALBERD_DISCARD));
         this.model.renderToBuffer(matrixStackIn, vertexconsumer, packedLightIn, OverlayTexture.NO_OVERLAY, i);
         VertexConsumer vertexconsumer1 = bufferIn.getBuffer(DECAL);
         this.model.renderToBuffer(matrixStackIn, vertexconsumer1, packedLightIn, OverlayTexture.NO_OVERLAY);
      } else {
         VertexConsumer vertexconsumer3 = bufferIn.getBuffer(RENDER_TYPE);
         this.model.renderToBuffer(matrixStackIn, vertexconsumer3, packedLightIn, OverlayTexture.NO_OVERLAY);
      }

      matrixStackIn.popPose();
      super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
   }

   protected int getBlockLightLevel(Phantom_Halberd_Entity entityIn, BlockPos pos) {
      return 15;
   }

   public ResourceLocation getTextureLocation(Phantom_Halberd_Entity entity) {
      return PHANTOM_HALBERD;
   }
}
