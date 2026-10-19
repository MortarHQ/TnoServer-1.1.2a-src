package com.iafenvoy.iceandfire.render.entity;

import com.iafenvoy.iceandfire.entity.GhostSwordEntity;
import com.iafenvoy.iceandfire.registry.IafItems;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class GhostSwordEntityRenderer extends EntityRenderer<GhostSwordEntity> {
   public GhostSwordEntityRenderer(Context context) {
      super(context);
   }

   public ResourceLocation getTexture(GhostSwordEntity entity) {
      return TextureAtlas.LOCATION_BLOCKS;
   }

   public void render(GhostSwordEntity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
      matrixStackIn.pushPose();
      matrixStackIn.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTicks, entityIn.yRotO, entityIn.getYRot()) - 90.0F));
      matrixStackIn.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(partialTicks, entityIn.xRotO, entityIn.getXRot())));
      matrixStackIn.translate(0.0F, 0.5F, 0.0F);
      matrixStackIn.scale(2.0F, 2.0F, 2.0F);
      matrixStackIn.mulPose(Axis.YP.rotationDegrees(0.0F));
      matrixStackIn.mulPose(Axis.ZN.rotationDegrees((entityIn.tickCount + partialTicks) * 30.0F));
      matrixStackIn.translate(0.0F, -0.15F, 0.0F);
      Minecraft.getInstance()
         .getItemRenderer()
         .renderStatic(
            new ItemStack((ItemLike)IafItems.GHOST_SWORD.get()),
            ItemDisplayContext.GROUND,
            240,
            OverlayTexture.NO_OVERLAY,
            matrixStackIn,
            bufferIn,
            Minecraft.getInstance().level,
            0
         );
      matrixStackIn.popPose();
   }
}
