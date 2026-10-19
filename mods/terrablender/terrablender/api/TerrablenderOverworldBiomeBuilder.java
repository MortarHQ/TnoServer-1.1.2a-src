package terrablender.api;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.OverworldBiomeBuilder;
import net.minecraft.world.level.biome.Climate.Parameter;
import org.jetbrains.annotations.NotNull;

public class TerrablenderOverworldBiomeBuilder extends OverworldBiomeBuilder {
   private final ResourceKey<Biome>[][] beachBiomes;
   private final ResourceKey<Biome>[][] peakBiomes;
   private final ResourceKey<Biome>[][] peakBiomesVariant;
   private final ResourceKey<Biome>[][] slopeBiomes;
   private final ResourceKey<Biome>[][] slopeBiomesVariant;

   public TerrablenderOverworldBiomeBuilder(
      ResourceKey<Biome>[][] oceans,
      ResourceKey<Biome>[][] middleBiomes,
      ResourceKey<Biome>[][] middleBiomesVariant,
      ResourceKey<Biome>[][] plateauBiomes,
      ResourceKey<Biome>[][] plateauBiomesVariant,
      ResourceKey<Biome>[][] shatteredBiomes,
      ResourceKey<Biome>[][] beachBiomes,
      ResourceKey<Biome>[][] peakBiomes,
      ResourceKey<Biome>[][] peakBiomesVariant,
      ResourceKey<Biome>[][] slopeBiomes,
      ResourceKey<Biome>[][] slopeBiomesVariant
   ) {
      this.OCEANS = oceans;
      this.MIDDLE_BIOMES = middleBiomes;
      this.MIDDLE_BIOMES_VARIANT = middleBiomesVariant;
      this.PLATEAU_BIOMES = plateauBiomes;
      this.PLATEAU_BIOMES_VARIANT = plateauBiomesVariant;
      this.SHATTERED_BIOMES = shatteredBiomes;
      this.beachBiomes = beachBiomes;
      this.peakBiomes = peakBiomes;
      this.peakBiomesVariant = peakBiomesVariant;
      this.slopeBiomes = slopeBiomes;
      this.slopeBiomesVariant = slopeBiomesVariant;
   }

   @NotNull
   public ResourceKey<Biome> pickBeachBiome(int temp, int humidity) {
      return this.beachBiomes[temp][humidity];
   }

   @NotNull
   public ResourceKey<Biome> pickPeakBiome(int temp, int humidity, Parameter weirdness) {
      ResourceKey<Biome> peakBiome = this.peakBiomes[temp][humidity];
      if (weirdness.max() < 0L) {
         return peakBiome;
      }

      ResourceKey<Biome> peakVariant = this.peakBiomesVariant[temp][humidity];
      return peakVariant == null ? peakBiome : peakVariant;
   }

   @NotNull
   public ResourceKey<Biome> pickSlopeBiome(int temp, int humidity, Parameter weirdness) {
      ResourceKey<Biome> slopeBiome = this.slopeBiomes[temp][humidity];
      if (weirdness.max() < 0L) {
         return slopeBiome;
      }

      ResourceKey<Biome> slopeVariant = this.slopeBiomesVariant[temp][humidity];
      return slopeVariant == null ? slopeBiome : slopeVariant;
   }
}
