package com.mongoose.royalvariations.client.render;

import com.mongoose.royalvariations.RoyalVariations;
import com.mongoose.royalvariations.client.render.model.RoyalEndermanModel;
import com.mongoose.royalvariations.common.entities.neutral.RoyalEnderman;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;

public class RoyalEndermanRenderer extends MobRenderer<RoyalEnderman, RoyalEndermanModel<RoyalEnderman>> {
   private static final ResourceLocation TEXTURE = RoyalVariations.location("textures/entity/royal_enderman.png");

   public RoyalEndermanRenderer(Context p_174008_) {
      super(p_174008_, new RoyalEndermanModel(p_174008_.bakeLayer(RVModelLayer.ROYAL_ENDERMAN)), 0.5F);
      this.addLayer(new RoyalEndermanRenderer.GlowLayer(this));
   }

   public ResourceLocation getTextureLocation(RoyalEnderman p_114482_) {
      return TEXTURE;
   }

   public static class GlowLayer extends EyesLayer<RoyalEnderman, RoyalEndermanModel<RoyalEnderman>> {
      private static final ResourceLocation TEXTURE = RoyalVariations.location("textures/entity/royal_enderman_glow.png");

      public GlowLayer(RenderLayerParent<RoyalEnderman, RoyalEndermanModel<RoyalEnderman>> p_i50919_1_) {
         super(p_i50919_1_);
      }

      public RenderType renderType() {
         return RenderType.eyes(TEXTURE);
      }
   }
}
