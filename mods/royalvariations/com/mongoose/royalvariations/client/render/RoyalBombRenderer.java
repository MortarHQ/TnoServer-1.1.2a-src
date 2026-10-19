package com.mongoose.royalvariations.client.render;

import com.mongoose.royalvariations.RoyalVariations;
import com.mongoose.royalvariations.client.render.model.RoyalBombModel;
import com.mongoose.royalvariations.common.entities.projectile.RoyalBomb;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public class RoyalBombRenderer extends MobRenderer<RoyalBomb, RoyalBombModel<RoyalBomb>> {
   private static final ResourceLocation TEXTURE = RoyalVariations.location("textures/entity/royal_bomb.png");

   public RoyalBombRenderer(Context p_174008_) {
      super(p_174008_, new RoyalBombModel(p_174008_.bakeLayer(RVModelLayer.ROYAL_BOMB)), 0.5F);
      this.addLayer(new RoyalBombRenderer.GlowLayer(this));
   }

   protected int getBlockLightLevel(RoyalBomb p_114496_, BlockPos p_114497_) {
      return 15;
   }

   public ResourceLocation getTextureLocation(RoyalBomb p_114482_) {
      return TEXTURE;
   }

   public static class GlowLayer extends EyesLayer<RoyalBomb, RoyalBombModel<RoyalBomb>> {
      private static final ResourceLocation TEXTURE = RoyalVariations.location("textures/entity/royal_bomb_glow.png");

      public GlowLayer(RenderLayerParent<RoyalBomb, RoyalBombModel<RoyalBomb>> p_i50919_1_) {
         super(p_i50919_1_);
      }

      public RenderType renderType() {
         return RenderType.eyes(TEXTURE);
      }
   }
}
