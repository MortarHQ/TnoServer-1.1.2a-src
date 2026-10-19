package com.bobmowzie.mowziesmobs.client.render.entity;

import com.bobmowzie.mowziesmobs.client.model.tools.MathUtils;
import com.bobmowzie.mowziesmobs.server.entity.effects.EntityFallingBlock;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class RenderFallingBlock extends EntityRenderer<EntityFallingBlock> {
   public RenderFallingBlock(Context mgr) {
      super(mgr);
   }

   public void render(EntityFallingBlock entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
      BlockRenderDispatcher dispatcher = Minecraft.getInstance().getBlockRenderer();
      matrixStackIn.pushPose();
      matrixStackIn.translate(0.0F, 0.5F, 0.0F);
      if (entityIn.getMode() == EntityFallingBlock.EnumFallingBlockMode.MOBILE) {
         matrixStackIn.mulPose(MathUtils.quatFromRotationXYZ(0.0F, Mth.lerp(partialTicks, entityIn.yRotO, entityIn.getYRot()), 0.0F, true));
         matrixStackIn.mulPose(MathUtils.quatFromRotationXYZ(Mth.lerp(partialTicks, entityIn.xRotO, entityIn.getXRot()), 0.0F, 0.0F, true));
      } else {
         matrixStackIn.translate(0.0F, Mth.lerp(partialTicks, entityIn.prevAnimY, entityIn.animY), 0.0F);
         matrixStackIn.translate(0.0F, -1.0F, 0.0F);
      }

      matrixStackIn.translate(-0.5F, -0.5F, -0.5F);
      dispatcher.renderSingleBlock(entityIn.getBlock(), matrixStackIn, bufferIn, packedLightIn, OverlayTexture.NO_OVERLAY);
      matrixStackIn.popPose();
   }

   public ResourceLocation getTextureLocation(EntityFallingBlock entity) {
      return null;
   }
}
