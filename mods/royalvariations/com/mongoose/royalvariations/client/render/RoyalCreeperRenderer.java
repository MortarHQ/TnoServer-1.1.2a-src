package com.mongoose.royalvariations.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mongoose.royalvariations.RoyalVariations;
import com.mongoose.royalvariations.client.render.model.RoyalCreeperModel;
import com.mongoose.royalvariations.common.entities.hostile.RoyalCreeper;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.layers.EnergySwirlLayer;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class RoyalCreeperRenderer extends MobRenderer<RoyalCreeper, RoyalCreeperModel<RoyalCreeper>> {
   private static final ResourceLocation TEXTURE = RoyalVariations.location("textures/entity/royal_creeper.png");

   public RoyalCreeperRenderer(Context p_174008_) {
      super(p_174008_, new RoyalCreeperModel(p_174008_.bakeLayer(RVModelLayer.ROYAL_CREEPER)), 0.5F);
      this.addLayer(new RoyalCreeperRenderer.GlowLayer(this));
      this.addLayer(new RoyalCreeperRenderer.RoyalCreeperPowerLayer(this, p_174008_.getModelSet()));
   }

   protected void scale(RoyalCreeper p_114046_, PoseStack p_114047_, float p_114048_) {
      float f = p_114046_.getSwelling(p_114048_);
      float f1 = 1.0F + Mth.sin(f * 100.0F) * f * 0.01F;
      f = Mth.clamp(f, 0.0F, 1.0F);
      f *= f;
      f *= f;
      float f2 = (1.0F + f * 0.4F) * f1;
      float f3 = (1.0F + f * 0.1F) / f1;
      p_114047_.scale(f2, f3, f2);
   }

   protected float getWhiteOverlayProgress(RoyalCreeper p_114043_, float p_114044_) {
      float f = p_114043_.getSwelling(p_114044_);
      return (int)(f * 10.0F) % 2 == 0 ? 0.0F : Mth.clamp(f, 0.5F, 1.0F);
   }

   public ResourceLocation getTextureLocation(RoyalCreeper p_114482_) {
      return TEXTURE;
   }

   public static class GlowLayer extends EyesLayer<RoyalCreeper, RoyalCreeperModel<RoyalCreeper>> {
      private static final ResourceLocation TEXTURE = RoyalVariations.location("textures/entity/royal_creeper_glow.png");

      public GlowLayer(RenderLayerParent<RoyalCreeper, RoyalCreeperModel<RoyalCreeper>> p_i50919_1_) {
         super(p_i50919_1_);
      }

      public void render(
         PoseStack p_116983_,
         MultiBufferSource p_116984_,
         int p_116985_,
         RoyalCreeper p_116986_,
         float p_116987_,
         float p_116988_,
         float p_116989_,
         float p_116990_,
         float p_116991_,
         float p_116992_
      ) {
         VertexConsumer vertexconsumer = p_116984_.getBuffer(this.renderType());
         ((RoyalCreeperModel)this.getParentModel()).renderToBuffer(p_116983_, vertexconsumer, 15728640, OverlayTexture.NO_OVERLAY, -8355712);
      }

      public RenderType renderType() {
         return RenderType.eyes(TEXTURE);
      }
   }

   public static class RoyalCreeperPowerLayer extends EnergySwirlLayer<RoyalCreeper, RoyalCreeperModel<RoyalCreeper>> {
      private static final ResourceLocation TEXTURE = RoyalVariations.location("textures/entity/royal_creeper_armor.png");
      private final RoyalCreeperModel<RoyalCreeper> model;

      public RoyalCreeperPowerLayer(RenderLayerParent<RoyalCreeper, RoyalCreeperModel<RoyalCreeper>> p_174471_, EntityModelSet p_174472_) {
         super(p_174471_);
         this.model = new RoyalCreeperModel(p_174472_.bakeLayer(RVModelLayer.ROYAL_CREEPER_ARMOR));
      }

      protected float xOffset(float p_116683_) {
         return p_116683_ * 0.01F;
      }

      protected ResourceLocation getTextureLocation() {
         return TEXTURE;
      }

      protected EntityModel<RoyalCreeper> model() {
         return this.model;
      }
   }
}
