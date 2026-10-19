package com.mongoose.royalvariations.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mongoose.royalvariations.RoyalVariations;
import com.mongoose.royalvariations.client.render.model.RoyalZombieModel;
import com.mongoose.royalvariations.common.entities.hostile.RoyalZombie;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class RoyalZombieRenderer extends MobRenderer<RoyalZombie, RoyalZombieModel<RoyalZombie>> {
   private static final ResourceLocation TEXTURE = RoyalVariations.location("textures/entity/royal_zombie.png");

   public RoyalZombieRenderer(Context p_174008_) {
      super(p_174008_, new RoyalZombieModel(p_174008_.bakeLayer(RVModelLayer.ROYAL_ZOMBIE)), 0.5F);
      this.addLayer(new RoyalZombieRenderer.GlowLayer(this));
   }

   public ResourceLocation getTextureLocation(RoyalZombie p_114482_) {
      return TEXTURE;
   }

   public static class GlowLayer extends EyesLayer<RoyalZombie, RoyalZombieModel<RoyalZombie>> {
      private static final ResourceLocation TEXTURE = RoyalVariations.location("textures/entity/royal_zombie_glow.png");

      public GlowLayer(RenderLayerParent<RoyalZombie, RoyalZombieModel<RoyalZombie>> p_i50919_1_) {
         super(p_i50919_1_);
      }

      public void render(
         PoseStack p_116983_,
         MultiBufferSource p_116984_,
         int p_116985_,
         RoyalZombie p_116986_,
         float p_116987_,
         float p_116988_,
         float p_116989_,
         float p_116990_,
         float p_116991_,
         float p_116992_
      ) {
         VertexConsumer vertexconsumer = p_116984_.getBuffer(this.renderType());
         ((RoyalZombieModel)this.getParentModel()).renderToBuffer(p_116983_, vertexconsumer, 15728640, OverlayTexture.NO_OVERLAY, -8355712);
      }

      public RenderType renderType() {
         return RenderType.eyes(TEXTURE);
      }
   }
}
