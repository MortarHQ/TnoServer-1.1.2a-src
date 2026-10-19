package xaero.map.region.texture;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import xaero.map.palette.FastPalette;
import xaero.map.palette.Paletted2DFastBitArrayIntStorage;

public class RegionTextureBiomes {
   protected final Paletted2DFastBitArrayIntStorage biomeIndexStorage;
   protected final FastPalette<ResourceKey<Biome>> regionBiomePalette;

   public RegionTextureBiomes(Paletted2DFastBitArrayIntStorage biomeIndexStorage, FastPalette<ResourceKey<Biome>> regionBiomePalette) {
      this.biomeIndexStorage = biomeIndexStorage;
      this.regionBiomePalette = regionBiomePalette;
   }

   public Paletted2DFastBitArrayIntStorage getBiomeIndexStorage() {
      return this.biomeIndexStorage;
   }

   public FastPalette<ResourceKey<Biome>> getRegionBiomePalette() {
      return this.regionBiomePalette;
   }
}
