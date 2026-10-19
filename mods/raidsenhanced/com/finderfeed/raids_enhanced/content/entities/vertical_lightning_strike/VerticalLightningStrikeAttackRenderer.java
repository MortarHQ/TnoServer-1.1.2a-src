package com.finderfeed.raids_enhanced.content.entities.vertical_lightning_strike;

import com.finderfeed.fdlib.util.rendering.renderers.QuadRenderer;
import com.finderfeed.raids_enhanced.RaidsEnhanced;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;

public class VerticalLightningStrikeAttackRenderer extends EntityRenderer<VerticalLightningStrikeAttack> {
   public static final ResourceLocation PREPARATOR = RaidsEnhanced.location("textures/entities/lightning_preparator.png");

   public VerticalLightningStrikeAttackRenderer(Context p_174008_) {
      super(p_174008_);
   }

   public void render(VerticalLightningStrikeAttack attack, float pticks, float p_114487_, PoseStack matrices, MultiBufferSource src, int p_114490_) {
      int time = attack.tickCount;
      float p = (time + pticks) / 10.0F;
      if (p <= 1.0F) {
         int frame = Math.round(p * 4.0F);
         QuadRenderer.start(src.getBuffer(RenderType.text(PREPARATOR)))
            .setAnimated(frame, 5)
            .offsetOnDirection(0.01F)
            .size(1.0F)
            .pose(matrices)
            .renderBack()
            .render();
      }
   }

   public ResourceLocation getTextureLocation(VerticalLightningStrikeAttack p_114482_) {
      return TextureAtlas.LOCATION_BLOCKS;
   }
}
