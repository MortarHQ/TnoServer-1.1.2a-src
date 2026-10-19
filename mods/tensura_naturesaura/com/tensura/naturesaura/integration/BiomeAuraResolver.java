package com.tensura.naturesaura.integration;

import com.tensura.naturesaura.registry.ModBiomeTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;

public final class BiomeAuraResolver {
   public static final int RICH_AURA_BASE = 1350000;
   public static final int DRY_AURA_BASE = 850000;
   public static final int BARREN_AURA_BASE = 750000;
   public static final int TAINTED_AURA_BASE = 700000;
   public static final int UNDERWORLD_SPIKES_AURA_BASE = 800000;
   public static final int UNDERWORLD_RED_SANDS_AURA_BASE = 900000;

   private BiomeAuraResolver() {
   }

   public static int adjustAura(Level level, BlockPos pos, int aura) {
      if (!level.isLoaded(pos)) {
         return aura;
      }

      int biomeBaseAura = getBiomeBaseAura(level, pos);
      if (biomeBaseAura == 1000000) {
         return aura;
      }

      long adjustedAura = (long)aura + biomeBaseAura - 1000000L;
      return (int)Math.max(-2147483648L, Math.min(2147483647L, adjustedAura));
   }

   private static int getBiomeBaseAura(Level level, BlockPos pos) {
      Holder<Biome> biome = level.getBiome(pos);
      if (biome.is(ModBiomeTags.AURA_RICH)) {
         return 1350000;
      } else if (biome.is(ModBiomeTags.AURA_DRY)) {
         return 850000;
      } else if (biome.is(ModBiomeTags.AURA_BARREN)) {
         return 750000;
      } else if (biome.is(ModBiomeTags.AURA_TAINTED)) {
         return 700000;
      } else if (biome.is(ModBiomeTags.AURA_UNDERWORLD_SPIKES)) {
         return 800000;
      } else {
         return biome.is(ModBiomeTags.AURA_UNDERWORLD_RED_SANDS) ? 900000 : 1000000;
      }
   }
}
