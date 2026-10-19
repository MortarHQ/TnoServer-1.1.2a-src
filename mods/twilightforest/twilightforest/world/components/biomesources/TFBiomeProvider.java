package twilightforest.world.components.biomesources;

import com.mojang.serialization.MapCodec;
import java.util.List;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate.Sampler;
import twilightforest.TFRegistries;
import twilightforest.world.components.layer.BiomeDensitySource;

public class TFBiomeProvider extends BiomeSource {
   public static final MapCodec<TFBiomeProvider> TF_CODEC = RegistryFileCodec.create(TFRegistries.Keys.BIOME_TERRAIN_DATA, BiomeDensitySource.CODEC, false)
      .xmap(TFBiomeProvider::new, TFBiomeProvider::getBiomeConfig)
      .fieldOf("terrain_data");
   private final Holder<BiomeDensitySource> biomeTerrainDataHolder;

   public TFBiomeProvider(Holder<BiomeDensitySource> biomeTerrainDataHolder) {
      this.biomeTerrainDataHolder = biomeTerrainDataHolder;
   }

   private Holder<BiomeDensitySource> getBiomeConfig() {
      return this.biomeTerrainDataHolder;
   }

   protected Stream<Holder<Biome>> collectPossibleBiomes() {
      return ((BiomeDensitySource)this.biomeTerrainDataHolder.value()).collectPossibleBiomes();
   }

   protected MapCodec<? extends BiomeSource> codec() {
      return TF_CODEC;
   }

   public Holder<Biome> getNoiseBiome(int biomeX, int biomeY, int biomeZ, Sampler sampler) {
      return ((BiomeDensitySource)this.biomeTerrainDataHolder.value()).getNoiseBiome(biomeX, biomeY, biomeZ);
   }

   public Holder<Biome> getMainBiome(int biomeX, int biomeZ) {
      return ((BiomeDensitySource)this.biomeTerrainDataHolder.value()).getBiomeColumnKey(biomeX, biomeZ);
   }

   @Deprecated
   public BiomeDensitySource getBiomeTerrain() {
      return (BiomeDensitySource)this.biomeTerrainDataHolder.value();
   }

   public void addDebugInfo(List<String> info, BlockPos cameraPos, Sampler sampler) {
      super.addDebugInfo(info, cameraPos, sampler);
      ((BiomeDensitySource)this.biomeTerrainDataHolder.value()).addDebugInfo(info, cameraPos);
   }
}
