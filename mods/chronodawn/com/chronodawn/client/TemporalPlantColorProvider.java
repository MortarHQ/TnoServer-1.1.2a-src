package com.chronodawn.client;

import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;

public final class TemporalPlantColorProvider {
   public static final int BASELINE = 5999300;
   public static final int FADED_PLAINS_TINT = 16754768;
   private static final float BLEND = 0.8F;

   private TemporalPlantColorProvider() {
   }

   public static int blockTint(BlockAndTintGetter world, BlockPos pos, int tintIndex) {
      if (tintIndex != 0) {
         return -1;
      }

      if (world == null || pos == null) {
         return 16777215;
      }

      if (TemporalGrassEdgeTint.isInFadedPlains(pos)) {
         return 16754768;
      }

      int biome = BiomeColors.getAverageGrassColor(world, pos);
      int rTint = blendChannel(biome >> 16 & 0xFF, 91);
      int gTint = blendChannel(biome >> 8 & 0xFF, 138);
      int bTint = blendChannel(biome & 0xFF, 196);
      return rTint << 16 | gTint << 8 | bTint;
   }

   public static int itemTint(int tintIndex) {
      return -1;
   }

   static int blendChannel(int biomeC, int baseC) {
      float ratio = baseC == 0 ? 1.0F : (float)biomeC / baseC;
      float scaled = Math.min(ratio * 255.0F, 255.0F);
      float tint = 255.0F + 0.8F * (scaled - 255.0F);
      return Math.max(0, Math.min(255, Math.round(tint)));
   }
}
