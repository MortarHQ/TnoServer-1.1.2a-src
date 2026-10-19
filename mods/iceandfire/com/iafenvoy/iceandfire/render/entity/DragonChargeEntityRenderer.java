package com.iafenvoy.iceandfire.render.entity;

import com.iafenvoy.iceandfire.registry.IafBlocks;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class DragonChargeEntityRenderer extends EntityRenderer<Fireball> {
   public final boolean isFire;

   public DragonChargeEntityRenderer(Context context, boolean isFire) {
      super(context);
      this.isFire = isFire;
   }

   public ResourceLocation getTexture(Fireball entity) {
      return TextureAtlas.LOCATION_BLOCKS;
   }

   public void render(Fireball entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
      matrixStackIn.pushPose();
      matrixStackIn.translate(0.0, 0.5, 0.0);
      matrixStackIn.mulPose(Axis.YP.rotationDegrees(-90.0F));
      matrixStackIn.translate(-0.5, -0.5, 0.5);
      matrixStackIn.mulPose(Axis.YP.rotationDegrees(90.0F));
      Minecraft.getInstance()
         .getBlockRenderer()
         .renderSingleBlock(
            this.isFire ? Blocks.MAGMA_BLOCK.defaultBlockState() : ((Block)IafBlocks.DRAGON_ICE.get()).defaultBlockState(),
            matrixStackIn,
            bufferIn,
            packedLightIn,
            OverlayTexture.NO_OVERLAY
         );
      matrixStackIn.popPose();
   }
}
