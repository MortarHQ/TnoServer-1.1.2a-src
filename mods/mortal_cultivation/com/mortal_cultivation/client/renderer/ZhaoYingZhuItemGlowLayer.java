package com.mortal_cultivation.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mortal_cultivation.client.model.ZhaoYingZhuModel;
import com.mortal_cultivation.item.ZhaoYingZhuItem;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;
import software.bernie.geckolib.util.Color;

public final class ZhaoYingZhuItemGlowLayer extends GeoRenderLayer<ZhaoYingZhuItem> {
   private static final float GLOW_STRENGTH = 0.55F;

   public ZhaoYingZhuItemGlowLayer(GeoRenderer<ZhaoYingZhuItem> renderer) {
      super(renderer);
   }

   public void render(
      PoseStack poseStack,
      ZhaoYingZhuItem item,
      BakedGeoModel bakedModel,
      @Nullable RenderType renderType,
      MultiBufferSource bufferSource,
      @Nullable VertexConsumer buffer,
      float partialTick,
      int packedLight,
      int packedOverlay
   ) {
      ItemStack stack = ((ZhaoYingZhuItemRenderer)this.getRenderer()).getCurrentItemStack();
      ResourceLocation texture = stack != null && ZhaoYingZhuItem.hasStoredRunes(stack) ? ZhaoYingZhuModel.STORED_TEXTURE : ZhaoYingZhuModel.BASE_TEXTURE;
      RenderType glowType = RenderType.eyes(texture);
      int color = Color.ofRGBA(0.55F, 0.55F, 0.55F, 1.0F).argbInt();
      this.getRenderer()
         .reRender(
            bakedModel, poseStack, bufferSource, item, glowType, bufferSource.getBuffer(glowType), partialTick, 15728880, OverlayTexture.NO_OVERLAY, color
         );
   }
}
