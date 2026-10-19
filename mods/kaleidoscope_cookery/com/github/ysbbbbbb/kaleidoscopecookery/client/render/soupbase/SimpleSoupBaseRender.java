package com.github.ysbbbbbb.kaleidoscopecookery.client.render.soupbase;

import com.github.ysbbbbbb.kaleidoscopecookery.api.client.render.ISoupBaseRender;
import com.github.ysbbbbbb.kaleidoscopecookery.blockentity.kitchen.StockpotBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.function.Function;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;

public class SimpleSoupBaseRender implements ISoupBaseRender {
   private final ResourceLocation soupBaseTexture;

   public SimpleSoupBaseRender(ResourceLocation soupBaseTexture) {
      this.soupBaseTexture = soupBaseTexture;
   }

   @Override
   public void renderWhenPutIngredient(
      StockpotBlockEntity stockpot, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay, float soupHeight
   ) {
      ISoupBaseRender.renderSurface(this.getSprite(), -1, poseStack, buffer, packedLight, soupHeight);
   }

   @Override
   public void renderWhenCooking(
      StockpotBlockEntity stockpot,
      float partialTick,
      PoseStack poseStack,
      MultiBufferSource buffer,
      int packedLight,
      int packedOverlay,
      ResourceLocation cookingTexture,
      float soupHeight
   ) {
      Function<ResourceLocation, TextureAtlasSprite> atlas = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS);
      TextureAtlasSprite sprite = atlas.apply(cookingTexture);
      ISoupBaseRender.renderSurface(sprite, -1, poseStack, buffer, packedLight, soupHeight);
   }

   @Override
   public void renderWhenFinished(
      StockpotBlockEntity stockpot,
      float partialTick,
      PoseStack poseStack,
      MultiBufferSource buffer,
      int packedLight,
      int packedOverlay,
      ResourceLocation finishedTexture,
      float soupHeight
   ) {
      Function<ResourceLocation, TextureAtlasSprite> atlas = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS);
      TextureAtlasSprite sprite = atlas.apply(finishedTexture);
      ISoupBaseRender.renderSurface(sprite, -1, poseStack, buffer, packedLight, soupHeight);
   }

   private TextureAtlasSprite getSprite() {
      return (TextureAtlasSprite)Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(this.soupBaseTexture);
   }
}
