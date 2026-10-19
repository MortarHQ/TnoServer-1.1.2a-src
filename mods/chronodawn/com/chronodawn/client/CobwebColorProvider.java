package com.chronodawn.client;

import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;

public final class CobwebColorProvider {
   private static final int WHITE = 16777215;
   private static final float BLEND = 0.6F;

   private CobwebColorProvider() {
   }

   public static int blockTint(BlockAndTintGetter world, BlockPos pos, int tintIndex) {
      if (tintIndex != 0) {
         return -1;
      } else if (world != null && pos != null) {
         int biome = BiomeColors.getAverageGrassColor(world, pos);
         int r = blendChannel(biome >> 16 & 0xFF);
         int g = blendChannel(biome >> 8 & 0xFF);
         int b = blendChannel(biome & 0xFF);
         return r << 16 | g << 8 | b;
      } else {
         return 16777215;
      }
   }

   static int blendChannel(int biomeC) {
      float tint = 255.0F + 0.6F * (biomeC - 255.0F);
      return Math.max(0, Math.min(255, Math.round(tint)));
   }
}
