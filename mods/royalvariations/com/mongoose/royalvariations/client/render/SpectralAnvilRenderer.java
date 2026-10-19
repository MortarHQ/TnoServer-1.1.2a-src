package com.mongoose.royalvariations.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.mongoose.royalvariations.RoyalVariations;
import com.mongoose.royalvariations.client.render.model.SpectralAnvilModel;
import com.mongoose.royalvariations.common.entities.projectile.SpectralAnvil;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.FastColor.ARGB32;

public class SpectralAnvilRenderer extends EntityRenderer<SpectralAnvil> {
   private static final ResourceLocation TEXTURE = RoyalVariations.location("textures/entity/projectiles/spectral_anvil.png");
   private final SpectralAnvilModel<SpectralAnvil> model;

   public SpectralAnvilRenderer(Context renderManagerIn) {
      super(renderManagerIn);
      this.model = new SpectralAnvilModel<>(renderManagerIn.bakeLayer(RVModelLayer.SPECTRAL_ANVIL));
   }

   public void render(SpectralAnvil pEntity, float entityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource bufferIn, int packedLightIn) {
      pMatrixStack.pushPose();
      pMatrixStack.mulPose(Axis.YP.rotationDegrees(90.0F - pEntity.getYRot()));
      VertexConsumer ivertexbuilder = bufferIn.getBuffer(RenderType.itemEntityTranslucentCull(this.getTextureLocation(pEntity)));
      pMatrixStack.scale(-1.0F, -1.0F, 1.0F);
      pMatrixStack.translate(0.0, -1.46, 0.0);
      pMatrixStack.scale(1.0F, 1.0F, 1.0F);
      float f7 = this.getBob(pEntity, pPartialTicks);
      this.model.setupAnim(pEntity, 0.0F, 0.0F, f7, 0.0F, 0.0F);
      this.model.renderToBuffer(pMatrixStack, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, ARGB32.color(Mth.floor(76.5F), -1));
      VertexConsumer ivertexbuilder2 = bufferIn.getBuffer(RenderType.eyes(TEXTURE));
      this.model.renderToBuffer(pMatrixStack, ivertexbuilder2, packedLightIn, OverlayTexture.NO_OVERLAY);
      pMatrixStack.popPose();
      super.render(pEntity, entityYaw, pPartialTicks, pMatrixStack, bufferIn, packedLightIn);
   }

   protected float getBob(SpectralAnvil p_115305_, float p_115306_) {
      return p_115305_.tickCount + p_115306_;
   }

   public ResourceLocation getTextureLocation(SpectralAnvil pEntity) {
      return TEXTURE;
   }
}
