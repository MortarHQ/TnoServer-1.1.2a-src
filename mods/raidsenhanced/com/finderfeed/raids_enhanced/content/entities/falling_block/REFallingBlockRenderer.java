package com.finderfeed.raids_enhanced.content.entities.falling_block;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.data.ModelData;

public class REFallingBlockRenderer extends EntityRenderer<REFallingBlock> {
   public REFallingBlockRenderer(Context p_174008_) {
      super(p_174008_);
   }

   public void render(REFallingBlock entity, float idk, float partialTicks, PoseStack matrices, MultiBufferSource src, int light) {
      matrices.pushPose();
      BlockState state = entity.getBlockState();
      BlockRenderDispatcher renderer = Minecraft.getInstance().getBlockRenderer();
      matrices.translate(-0.5, -0.5, -0.5);
      matrices.translate(0.5, 0.5, 0.5);
      matrices.translate(-0.5, -0.5, -0.5);
      renderer.renderSingleBlock(state, matrices, src, light, OverlayTexture.NO_OVERLAY, ModelData.EMPTY, null);
      matrices.popPose();
   }

   public ResourceLocation getTextureLocation(REFallingBlock p_114482_) {
      return TextureAtlas.LOCATION_BLOCKS;
   }
}
