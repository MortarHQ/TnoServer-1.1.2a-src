package com.mongoose.royalvariations.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.mongoose.royalvariations.RoyalVariations;
import com.mongoose.royalvariations.client.render.model.NetModel;
import com.mongoose.royalvariations.common.entities.projectile.NetEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class NetRenderer<T extends NetEntity> extends EntityRenderer<T> {
   private static final ResourceLocation TEXTURE_LOCATION = RoyalVariations.location("textures/entity/projectiles/net.png");
   private final NetModel<T> model;

   public NetRenderer(Context p_i47208_1_) {
      super(p_i47208_1_);
      this.model = new NetModel<>(p_i47208_1_.bakeLayer(RVModelLayer.NET));
      this.shadowRadius = 0.0F;
   }

   public void render(T pEntity, float entityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource bufferIn, int packedLightIn) {
      pMatrixStack.pushPose();
      pMatrixStack.mulPose(Axis.YP.rotationDegrees(90.0F - pEntity.getYRot()));
      pMatrixStack.scale(-1.0F, -1.0F, 1.0F);
      pMatrixStack.translate(0.0, -1.45, 0.0);
      VertexConsumer ivertexbuilder = bufferIn.getBuffer(this.model.renderType(this.getTextureLocation(pEntity)));
      this.model.setupAnim(pEntity, 0.0F, 0.0F, pEntity.tickCount + pPartialTicks, 0.0F, 0.0F);
      this.model.renderToBuffer(pMatrixStack, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY);
      pMatrixStack.popPose();
      super.render(pEntity, entityYaw, pPartialTicks, pMatrixStack, bufferIn, packedLightIn);
   }

   public ResourceLocation getTextureLocation(T pEntity) {
      return TEXTURE_LOCATION;
   }
}
