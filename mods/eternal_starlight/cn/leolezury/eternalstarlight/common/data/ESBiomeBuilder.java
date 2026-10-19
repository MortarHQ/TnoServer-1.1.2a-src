package cn.leolezury.eternalstarlight.common.data;

import cn.leolezury.eternalstarlight.common.world.gen.biome.BiomeData;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.Climate.Parameter;
import net.minecraft.world.level.biome.Climate.ParameterPoint;

public final class ESBiomeBuilder {
   private final Parameter FULL_RANGE = Parameter.span(-1.0F, 1.0F);
   private final Parameter[] temperatures = new Parameter[]{
      Parameter.span(-1.0F, -0.45F), Parameter.span(-0.45F, -0.15F), Parameter.span(-0.15F, 0.2F), Parameter.span(0.2F, 0.55F), Parameter.span(0.55F, 1.0F)
   };
   private final Parameter[] humidities = new Parameter[]{
      Parameter.span(-1.0F, -0.35F), Parameter.span(-0.35F, -0.1F), Parameter.span(-0.1F, 0.1F), Parameter.span(0.1F, 0.3F), Parameter.span(0.3F, 1.0F)
   };
   private final Parameter[] erosions = new Parameter[]{
      Parameter.span(-1.0F, -0.78F),
      Parameter.span(-0.78F, -0.375F),
      Parameter.span(-0.375F, -0.2225F),
      Parameter.span(-0.2225F, 0.05F),
      Parameter.span(0.05F, 0.45F),
      Parameter.span(0.45F, 0.55F),
      Parameter.span(0.55F, 1.0F)
   };
   private final Parameter FROZEN_RANGE = this.temperatures[0];
   private final Parameter UNFROZEN_RANGE = Parameter.span(this.temperatures[1], this.temperatures[4]);
   private final Parameter mushroomFieldsContinentalness = Parameter.span(-1.2F, -1.05F);
   private final Parameter deepOceanContinentalness = Parameter.span(-1.05F, -0.455F);
   private final Parameter oceanContinentalness = Parameter.span(-0.455F, -0.19F);
   private final Parameter coastContinentalness = Parameter.span(-0.19F, -0.11F);
   private final Parameter inlandContinentalness = Parameter.span(-0.11F, 0.55F);
   private final Parameter nearInlandContinentalness = Parameter.span(-0.11F, 0.03F);
   private final Parameter midInlandContinentalness = Parameter.span(0.03F, 0.3F);
   private final Parameter farInlandContinentalness = Parameter.span(0.3F, 1.0F);
   private final ResourceKey<BiomeData>[][] OCEANS = new ResourceKey[][]{
      {ESBiomeData.STARLIT_SEA, ESBiomeData.SPIRAL_KELP_FOREST, ESBiomeData.SPIRAL_KELP_FOREST, ESBiomeData.STARLIT_SEA, ESBiomeData.STARLIT_SEA},
      {ESBiomeData.ICY_SEA, ESBiomeData.SPIRAL_KELP_FOREST, ESBiomeData.STARLIT_SEA, ESBiomeData.LUSH_SHALLOW_SEA, ESBiomeData.LUSH_SHALLOW_SEA}
   };
   private final ResourceKey<BiomeData>[][] MIDDLE_BIOMES = new ResourceKey[][]{
      {
            ESBiomeData.STARLIGHT_PERMAFROST_FOREST,
            ESBiomeData.STARLIGHT_PERMAFROST_FOREST,
            ESBiomeData.STARLIGHT_PERMAFROST_FOREST,
            ESBiomeData.STARLIGHT_TAIGA,
            ESBiomeData.SCARLET_FOREST
      },
      {ESBiomeData.UMBRAL_PLAINS, ESBiomeData.UMBRAL_PLAINS, ESBiomeData.STARLIGHT_FOREST, ESBiomeData.STARLIGHT_TAIGA, ESBiomeData.SCARLET_FOREST},
      {ESBiomeData.UMBRAL_PLAINS, ESBiomeData.STARLIGHT_FOREST, ESBiomeData.DARK_SWAMP, ESBiomeData.SCARLET_FOREST, ESBiomeData.STARLIGHT_DENSE_FOREST},
      {ESBiomeData.GLIMMER_SCRUBLAND, ESBiomeData.STARLIGHT_FOREST, ESBiomeData.STARLIGHT_FOREST, ESBiomeData.DARK_SWAMP, ESBiomeData.DARK_SWAMP},
      {
            ESBiomeData.CRYSTALLIZED_DESERT,
            ESBiomeData.CRYSTALLIZED_DESERT,
            ESBiomeData.CRYSTALLIZED_DESERT,
            ESBiomeData.CRYSTALLIZED_DESERT,
            ESBiomeData.DARK_SWAMP
      }
   };
   private final ResourceKey<BiomeData>[][] MIDDLE_BIOMES_VARIANT = new ResourceKey[][]{
      {null, null, ESBiomeData.STARLIGHT_TAIGA, null, null},
      {null, ESBiomeData.GLIMMER_SCRUBLAND, null, null, null},
      {ESBiomeData.GLIMMER_SCRUBLAND, null, null, null, null},
      {null, null, ESBiomeData.DARK_SWAMP, null, null},
      {null, null, null, null, null}
   };
   private final ResourceKey<BiomeData>[][] PLATEAU_BIOMES = new ResourceKey[][]{
      {
            ESBiomeData.PERMAFROST_PEAKS,
            ESBiomeData.PERMAFROST_PEAKS,
            ESBiomeData.STARLIGHT_PERMAFROST_FOREST,
            ESBiomeData.STARLIGHT_TAIGA,
            ESBiomeData.SCARLET_FOREST
      },
      {ESBiomeData.UMBRAL_PLAINS, ESBiomeData.GLIMMER_SCRUBLAND, ESBiomeData.STARLIGHT_FOREST, ESBiomeData.STARLIGHT_TAIGA, ESBiomeData.SCARLET_FOREST},
      {ESBiomeData.GLIMMER_SCRUBLAND, ESBiomeData.STARLIGHT_FOREST, ESBiomeData.DARK_SWAMP, ESBiomeData.SCARLET_FOREST, ESBiomeData.STARLIGHT_DENSE_FOREST},
      {ESBiomeData.GLIMMER_SCRUBLAND, ESBiomeData.STARLIGHT_FOREST, ESBiomeData.STARLIGHT_FOREST, ESBiomeData.DARK_SWAMP, ESBiomeData.DARK_SWAMP},
      {
            ESBiomeData.CRYSTALLIZED_DESERT,
            ESBiomeData.CRYSTALLIZED_DESERT,
            ESBiomeData.CRYSTALLIZED_DESERT,
            ESBiomeData.CRYSTALLIZED_DESERT,
            ESBiomeData.DARK_SWAMP
      }
   };
   private final ResourceKey<BiomeData>[][] PLATEAU_BIOMES_VARIANT = new ResourceKey[][]{
      {null, null, null, null, null},
      {null, null, null, null, null},
      {null, ESBiomeData.STARLIGHT_DENSE_FOREST, ESBiomeData.STARLIGHT_DENSE_FOREST, null, ESBiomeData.SCARLET_FOREST},
      {null, null, ESBiomeData.STARLIGHT_DENSE_FOREST, null, null},
      {null, null, null, null, null}
   };
   private final ResourceKey<BiomeData>[][] SHATTERED_BIOMES = new ResourceKey[][]{
      {null, null, ESBiomeData.GLIMMER_SCRUBLAND, ESBiomeData.GLIMMER_SCRUBLAND, ESBiomeData.GLIMMER_SCRUBLAND},
      {null, null, ESBiomeData.GLIMMER_SCRUBLAND, ESBiomeData.GLIMMER_SCRUBLAND, ESBiomeData.GLIMMER_SCRUBLAND},
      {null, null, null, ESBiomeData.GLIMMER_SCRUBLAND, ESBiomeData.GLIMMER_SCRUBLAND},
      {null, null, null, null, null},
      {null, null, null, null, null}
   };

   public List<ParameterPoint> spawnTarget() {
      Parameter surfaceDepth = Parameter.point(0.0F);
      return List.of(
         new ParameterPoint(
            this.FULL_RANGE,
            this.FULL_RANGE,
            Parameter.span(this.inlandContinentalness, this.FULL_RANGE),
            this.FULL_RANGE,
            surfaceDepth,
            Parameter.span(-1.0F, -0.16F),
            0L
         ),
         new ParameterPoint(
            this.FULL_RANGE,
            this.FULL_RANGE,
            Parameter.span(this.inlandContinentalness, this.FULL_RANGE),
            this.FULL_RANGE,
            surfaceDepth,
            Parameter.span(0.16F, 1.0F),
            0L
         )
      );
   }

   public void addBiomes(Consumer<Pair<ParameterPoint, ResourceKey<BiomeData>>> biomes) {
      this.addOffCoastBiomes(biomes);
      this.addInlandBiomes(biomes);
      this.addUndergroundBiomes(biomes);
   }

   private void addOffCoastBiomes(Consumer<Pair<ParameterPoint, ResourceKey<BiomeData>>> biomes) {
      this.addSurfaceBiome(
         biomes, this.FULL_RANGE, this.FULL_RANGE, this.mushroomFieldsContinentalness, this.FULL_RANGE, this.FULL_RANGE, 0.0F, ESBiomeData.LUCENT_MYCELIUM_ISLE
      );

      for (int temperatureIndex = 0; temperatureIndex < this.temperatures.length; temperatureIndex++) {
         Parameter temperature = this.temperatures[temperatureIndex];
         this.addSurfaceBiome(
            biomes, temperature, this.FULL_RANGE, this.deepOceanContinentalness, this.FULL_RANGE, this.FULL_RANGE, 0.0F, this.OCEANS[0][temperatureIndex]
         );
         this.addSurfaceBiome(
            biomes, temperature, this.FULL_RANGE, this.oceanContinentalness, this.FULL_RANGE, this.FULL_RANGE, 0.0F, this.OCEANS[1][temperatureIndex]
         );
      }
   }

   private void addInlandBiomes(Consumer<Pair<ParameterPoint, ResourceKey<BiomeData>>> biomes) {
      this.addMidSlice(biomes, Parameter.span(-1.0F, -0.93333334F));
      this.addHighSlice(biomes, Parameter.span(-0.93333334F, -0.7666667F));
      this.addPeaks(biomes, Parameter.span(-0.7666667F, -0.56666666F));
      this.addHighSlice(biomes, Parameter.span(-0.56666666F, -0.4F));
      this.addMidSlice(biomes, Parameter.span(-0.4F, -0.26666668F));
      this.addLowSlice(biomes, Parameter.span(-0.26666668F, -0.05F));
      this.addValleys(biomes, Parameter.span(-0.05F, 0.05F));
      this.addLowSlice(biomes, Parameter.span(0.05F, 0.26666668F));
      this.addMidSlice(biomes, Parameter.span(0.26666668F, 0.4F));
      this.addHighSlice(biomes, Parameter.span(0.4F, 0.56666666F));
      this.addPeaks(biomes, Parameter.span(0.56666666F, 0.7666667F));
      this.addHighSlice(biomes, Parameter.span(0.7666667F, 0.93333334F));
      this.addMidSlice(biomes, Parameter.span(0.93333334F, 1.0F));
   }

   private void addPeaks(Consumer<Pair<ParameterPoint, ResourceKey<BiomeData>>> biomes, Parameter weirdness) {
      for (int temperatureIndex = 0; temperatureIndex < this.temperatures.length; temperatureIndex++) {
         Parameter temperature = this.temperatures[temperatureIndex];

         for (int humidityIndex = 0; humidityIndex < this.humidities.length; humidityIndex++) {
            Parameter humidity = this.humidities[humidityIndex];
            ResourceKey<BiomeData> middleBiomeData = this.pickMiddleBiome(temperatureIndex, humidityIndex, weirdness);
            ResourceKey<BiomeData> plateauBiomeData = this.pickPlateauBiome(temperatureIndex, humidityIndex, weirdness);
            ResourceKey<BiomeData> shatteredBiomeData = this.pickShatteredBiome(temperatureIndex, humidityIndex, weirdness);
            this.addSurfaceBiome(
               biomes,
               temperature,
               humidity,
               Parameter.span(this.coastContinentalness, this.farInlandContinentalness),
               this.erosions[0],
               weirdness,
               0.0F,
               plateauBiomeData
            );
            this.addSurfaceBiome(
               biomes,
               temperature,
               humidity,
               Parameter.span(this.coastContinentalness, this.nearInlandContinentalness),
               this.erosions[1],
               weirdness,
               0.0F,
               middleBiomeData
            );
            this.addSurfaceBiome(
               biomes,
               temperature,
               humidity,
               Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness),
               this.erosions[1],
               weirdness,
               0.0F,
               plateauBiomeData
            );
            this.addSurfaceBiome(
               biomes,
               temperature,
               humidity,
               Parameter.span(this.coastContinentalness, this.nearInlandContinentalness),
               Parameter.span(this.erosions[2], this.erosions[3]),
               weirdness,
               0.0F,
               middleBiomeData
            );
            this.addSurfaceBiome(
               biomes,
               temperature,
               humidity,
               Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness),
               this.erosions[2],
               weirdness,
               0.0F,
               plateauBiomeData
            );
            this.addSurfaceBiome(biomes, temperature, humidity, this.midInlandContinentalness, this.erosions[3], weirdness, 0.0F, middleBiomeData);
            this.addSurfaceBiome(biomes, temperature, humidity, this.farInlandContinentalness, this.erosions[3], weirdness, 0.0F, plateauBiomeData);
            this.addSurfaceBiome(
               biomes,
               temperature,
               humidity,
               Parameter.span(this.coastContinentalness, this.farInlandContinentalness),
               this.erosions[4],
               weirdness,
               0.0F,
               middleBiomeData
            );
            this.addSurfaceBiome(
               biomes,
               temperature,
               humidity,
               Parameter.span(this.coastContinentalness, this.nearInlandContinentalness),
               this.erosions[5],
               weirdness,
               0.0F,
               shatteredBiomeData
            );
            this.addSurfaceBiome(
               biomes,
               temperature,
               humidity,
               Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness),
               this.erosions[5],
               weirdness,
               0.0F,
               shatteredBiomeData
            );
            this.addSurfaceBiome(
               biomes,
               temperature,
               humidity,
               Parameter.span(this.coastContinentalness, this.farInlandContinentalness),
               this.erosions[6],
               weirdness,
               0.0F,
               middleBiomeData
            );
         }
      }
   }

   private void addHighSlice(Consumer<Pair<ParameterPoint, ResourceKey<BiomeData>>> biomes, Parameter weirdness) {
      for (int temperatureIndex = 0; temperatureIndex < this.temperatures.length; temperatureIndex++) {
         Parameter temperature = this.temperatures[temperatureIndex];

         for (int humidityIndex = 0; humidityIndex < this.humidities.length; humidityIndex++) {
            Parameter humidity = this.humidities[humidityIndex];
            ResourceKey<BiomeData> middleBiomeData = this.pickMiddleBiome(temperatureIndex, humidityIndex, weirdness);
            ResourceKey<BiomeData> plateauBiomeData = this.pickPlateauBiome(temperatureIndex, humidityIndex, weirdness);
            ResourceKey<BiomeData> shatteredBiomeData = this.pickShatteredBiome(temperatureIndex, humidityIndex, weirdness);
            this.addSurfaceBiome(
               biomes, temperature, humidity, this.coastContinentalness, Parameter.span(this.erosions[0], this.erosions[1]), weirdness, 0.0F, middleBiomeData
            );
            this.addSurfaceBiome(biomes, temperature, humidity, this.nearInlandContinentalness, this.erosions[0], weirdness, 0.0F, plateauBiomeData);
            this.addSurfaceBiome(
               biomes,
               temperature,
               humidity,
               Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness),
               this.erosions[0],
               weirdness,
               0.0F,
               plateauBiomeData
            );
            this.addSurfaceBiome(biomes, temperature, humidity, this.nearInlandContinentalness, this.erosions[1], weirdness, 0.0F, middleBiomeData);
            this.addSurfaceBiome(
               biomes,
               temperature,
               humidity,
               Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness),
               this.erosions[1],
               weirdness,
               0.0F,
               plateauBiomeData
            );
            this.addSurfaceBiome(
               biomes,
               temperature,
               humidity,
               Parameter.span(this.coastContinentalness, this.nearInlandContinentalness),
               Parameter.span(this.erosions[2], this.erosions[3]),
               weirdness,
               0.0F,
               middleBiomeData
            );
            this.addSurfaceBiome(
               biomes,
               temperature,
               humidity,
               Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness),
               this.erosions[2],
               weirdness,
               0.0F,
               plateauBiomeData
            );
            this.addSurfaceBiome(biomes, temperature, humidity, this.midInlandContinentalness, this.erosions[3], weirdness, 0.0F, middleBiomeData);
            this.addSurfaceBiome(biomes, temperature, humidity, this.farInlandContinentalness, this.erosions[3], weirdness, 0.0F, plateauBiomeData);
            this.addSurfaceBiome(
               biomes,
               temperature,
               humidity,
               Parameter.span(this.coastContinentalness, this.farInlandContinentalness),
               this.erosions[4],
               weirdness,
               0.0F,
               middleBiomeData
            );
            this.addSurfaceBiome(
               biomes,
               temperature,
               humidity,
               Parameter.span(this.coastContinentalness, this.nearInlandContinentalness),
               this.erosions[5],
               weirdness,
               0.0F,
               middleBiomeData
            );
            this.addSurfaceBiome(
               biomes,
               temperature,
               humidity,
               Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness),
               this.erosions[5],
               weirdness,
               0.0F,
               shatteredBiomeData
            );
            this.addSurfaceBiome(
               biomes,
               temperature,
               humidity,
               Parameter.span(this.coastContinentalness, this.farInlandContinentalness),
               this.erosions[6],
               weirdness,
               0.0F,
               middleBiomeData
            );
         }
      }
   }

   private void addMidSlice(Consumer<Pair<ParameterPoint, ResourceKey<BiomeData>>> biomes, Parameter weirdness) {
      this.addSurfaceBiome(
         biomes,
         this.FULL_RANGE,
         this.FULL_RANGE,
         this.coastContinentalness,
         Parameter.span(this.erosions[0], this.erosions[2]),
         weirdness,
         0.0F,
         ESBiomeData.GRIM_SHORE
      );
      this.addSurfaceBiome(
         biomes,
         Parameter.span(this.temperatures[1], this.temperatures[2]),
         this.FULL_RANGE,
         Parameter.span(this.nearInlandContinentalness, this.farInlandContinentalness),
         this.erosions[6],
         weirdness,
         0.0F,
         ESBiomeData.DARK_SWAMP
      );
      this.addSurfaceBiome(
         biomes,
         Parameter.span(this.temperatures[3], this.temperatures[4]),
         this.FULL_RANGE,
         Parameter.span(this.nearInlandContinentalness, this.farInlandContinentalness),
         this.erosions[6],
         weirdness,
         0.0F,
         ESBiomeData.DARK_SWAMP
      );

      for (int temperatureIndex = 0; temperatureIndex < this.temperatures.length; temperatureIndex++) {
         Parameter temperature = this.temperatures[temperatureIndex];

         for (int humidityIndex = 0; humidityIndex < this.humidities.length; humidityIndex++) {
            Parameter humidity = this.humidities[humidityIndex];
            ResourceKey<BiomeData> middleBiomeData = this.pickMiddleBiome(temperatureIndex, humidityIndex, weirdness);
            ResourceKey<BiomeData> shatteredBiomeData = this.pickShatteredBiome(temperatureIndex, humidityIndex, weirdness);
            ResourceKey<BiomeData> plateauBiomeData = this.pickPlateauBiome(temperatureIndex, humidityIndex, weirdness);
            ResourceKey<BiomeData> beachBiomeData = this.pickBeachBiome(temperatureIndex);
            ResourceKey<BiomeData> shatteredCoastBiomeData = this.pickShatteredCoastBiome(temperatureIndex, humidityIndex, weirdness);
            this.addSurfaceBiome(
               biomes,
               temperature,
               humidity,
               Parameter.span(this.nearInlandContinentalness, this.farInlandContinentalness),
               this.erosions[0],
               weirdness,
               0.0F,
               middleBiomeData
            );
            this.addSurfaceBiome(
               biomes,
               temperature,
               humidity,
               Parameter.span(this.nearInlandContinentalness, this.midInlandContinentalness),
               this.erosions[1],
               weirdness,
               0.0F,
               middleBiomeData
            );
            this.addSurfaceBiome(
               biomes,
               temperature,
               humidity,
               this.farInlandContinentalness,
               this.erosions[1],
               weirdness,
               0.0F,
               temperatureIndex == 0 ? middleBiomeData : plateauBiomeData
            );
            this.addSurfaceBiome(biomes, temperature, humidity, this.nearInlandContinentalness, this.erosions[2], weirdness, 0.0F, middleBiomeData);
            this.addSurfaceBiome(biomes, temperature, humidity, this.midInlandContinentalness, this.erosions[2], weirdness, 0.0F, middleBiomeData);
            this.addSurfaceBiome(biomes, temperature, humidity, this.farInlandContinentalness, this.erosions[2], weirdness, 0.0F, plateauBiomeData);
            this.addSurfaceBiome(
               biomes,
               temperature,
               humidity,
               Parameter.span(this.coastContinentalness, this.nearInlandContinentalness),
               this.erosions[3],
               weirdness,
               0.0F,
               middleBiomeData
            );
            this.addSurfaceBiome(
               biomes,
               temperature,
               humidity,
               Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness),
               this.erosions[3],
               weirdness,
               0.0F,
               middleBiomeData
            );
            if (weirdness.max() < 0L) {
               this.addSurfaceBiome(biomes, temperature, humidity, this.coastContinentalness, this.erosions[4], weirdness, 0.0F, beachBiomeData);
               this.addSurfaceBiome(
                  biomes,
                  temperature,
                  humidity,
                  Parameter.span(this.nearInlandContinentalness, this.farInlandContinentalness),
                  this.erosions[4],
                  weirdness,
                  0.0F,
                  middleBiomeData
               );
            } else {
               this.addSurfaceBiome(
                  biomes,
                  temperature,
                  humidity,
                  Parameter.span(this.coastContinentalness, this.farInlandContinentalness),
                  this.erosions[4],
                  weirdness,
                  0.0F,
                  middleBiomeData
               );
            }

            this.addSurfaceBiome(biomes, temperature, humidity, this.coastContinentalness, this.erosions[5], weirdness, 0.0F, shatteredCoastBiomeData);
            this.addSurfaceBiome(biomes, temperature, humidity, this.nearInlandContinentalness, this.erosions[5], weirdness, 0.0F, middleBiomeData);
            this.addSurfaceBiome(
               biomes,
               temperature,
               humidity,
               Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness),
               this.erosions[5],
               weirdness,
               0.0F,
               shatteredBiomeData
            );
            if (weirdness.max() < 0L) {
               this.addSurfaceBiome(biomes, temperature, humidity, this.coastContinentalness, this.erosions[6], weirdness, 0.0F, beachBiomeData);
            } else {
               this.addSurfaceBiome(biomes, temperature, humidity, this.coastContinentalness, this.erosions[6], weirdness, 0.0F, middleBiomeData);
            }

            if (temperatureIndex == 0) {
               this.addSurfaceBiome(
                  biomes,
                  temperature,
                  humidity,
                  Parameter.span(this.nearInlandContinentalness, this.farInlandContinentalness),
                  this.erosions[6],
                  weirdness,
                  0.0F,
                  middleBiomeData
               );
            }
         }
      }
   }

   private void addLowSlice(Consumer<Pair<ParameterPoint, ResourceKey<BiomeData>>> biomes, Parameter weirdness) {
      this.addSurfaceBiome(
         biomes,
         this.FULL_RANGE,
         this.FULL_RANGE,
         this.coastContinentalness,
         Parameter.span(this.erosions[0], this.erosions[2]),
         weirdness,
         0.0F,
         ESBiomeData.GRIM_SHORE
      );
      this.addSurfaceBiome(
         biomes,
         Parameter.span(this.temperatures[1], this.temperatures[2]),
         this.FULL_RANGE,
         Parameter.span(this.nearInlandContinentalness, this.farInlandContinentalness),
         this.erosions[6],
         weirdness,
         0.0F,
         ESBiomeData.DARK_SWAMP
      );
      this.addSurfaceBiome(
         biomes,
         Parameter.span(this.temperatures[3], this.temperatures[4]),
         this.FULL_RANGE,
         Parameter.span(this.nearInlandContinentalness, this.farInlandContinentalness),
         this.erosions[6],
         weirdness,
         0.0F,
         ESBiomeData.DARK_SWAMP
      );

      for (int temperatureIndex = 0; temperatureIndex < this.temperatures.length; temperatureIndex++) {
         Parameter temperature = this.temperatures[temperatureIndex];

         for (int humidityIndex = 0; humidityIndex < this.humidities.length; humidityIndex++) {
            Parameter humidity = this.humidities[humidityIndex];
            ResourceKey<BiomeData> middleBiomeData = this.pickMiddleBiome(temperatureIndex, humidityIndex, weirdness);
            ResourceKey<BiomeData> beachBiomeData = this.pickBeachBiome(temperatureIndex);
            ResourceKey<BiomeData> shatteredCoastBiomeData = this.pickShatteredCoastBiome(temperatureIndex, humidityIndex, weirdness);
            this.addSurfaceBiome(
               biomes,
               temperature,
               humidity,
               this.nearInlandContinentalness,
               Parameter.span(this.erosions[0], this.erosions[1]),
               weirdness,
               0.0F,
               middleBiomeData
            );
            this.addSurfaceBiome(
               biomes,
               temperature,
               humidity,
               Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness),
               Parameter.span(this.erosions[0], this.erosions[1]),
               weirdness,
               0.0F,
               middleBiomeData
            );
            this.addSurfaceBiome(
               biomes,
               temperature,
               humidity,
               this.nearInlandContinentalness,
               Parameter.span(this.erosions[2], this.erosions[3]),
               weirdness,
               0.0F,
               middleBiomeData
            );
            this.addSurfaceBiome(
               biomes,
               temperature,
               humidity,
               Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness),
               Parameter.span(this.erosions[2], this.erosions[3]),
               weirdness,
               0.0F,
               middleBiomeData
            );
            this.addSurfaceBiome(
               biomes, temperature, humidity, this.coastContinentalness, Parameter.span(this.erosions[3], this.erosions[4]), weirdness, 0.0F, beachBiomeData
            );
            this.addSurfaceBiome(
               biomes,
               temperature,
               humidity,
               Parameter.span(this.nearInlandContinentalness, this.farInlandContinentalness),
               this.erosions[4],
               weirdness,
               0.0F,
               middleBiomeData
            );
            this.addSurfaceBiome(biomes, temperature, humidity, this.coastContinentalness, this.erosions[5], weirdness, 0.0F, shatteredCoastBiomeData);
            this.addSurfaceBiome(biomes, temperature, humidity, this.nearInlandContinentalness, this.erosions[5], weirdness, 0.0F, middleBiomeData);
            this.addSurfaceBiome(
               biomes,
               temperature,
               humidity,
               Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness),
               this.erosions[5],
               weirdness,
               0.0F,
               middleBiomeData
            );
            this.addSurfaceBiome(biomes, temperature, humidity, this.coastContinentalness, this.erosions[6], weirdness, 0.0F, beachBiomeData);
            if (temperatureIndex == 0) {
               this.addSurfaceBiome(
                  biomes,
                  temperature,
                  humidity,
                  Parameter.span(this.nearInlandContinentalness, this.farInlandContinentalness),
                  this.erosions[6],
                  weirdness,
                  0.0F,
                  middleBiomeData
               );
            }
         }
      }
   }

   private void addValleys(Consumer<Pair<ParameterPoint, ResourceKey<BiomeData>>> biomes, Parameter weirdness) {
      this.addSurfaceBiome(
         biomes,
         this.FROZEN_RANGE,
         this.FULL_RANGE,
         this.coastContinentalness,
         Parameter.span(this.erosions[0], this.erosions[1]),
         weirdness,
         0.0F,
         ESBiomeData.GRIM_SHORE
      );
      this.addSurfaceBiome(
         biomes,
         this.UNFROZEN_RANGE,
         this.FULL_RANGE,
         this.coastContinentalness,
         Parameter.span(this.erosions[0], this.erosions[1]),
         weirdness,
         0.0F,
         ESBiomeData.GRIM_SHORE
      );
      this.addSurfaceBiome(
         biomes,
         Parameter.span(this.temperatures[1], this.temperatures[2]),
         this.FULL_RANGE,
         Parameter.span(this.inlandContinentalness, this.farInlandContinentalness),
         this.erosions[6],
         weirdness,
         0.0F,
         ESBiomeData.DARK_SWAMP
      );
      this.addSurfaceBiome(
         biomes,
         Parameter.span(this.temperatures[3], this.temperatures[4]),
         this.FULL_RANGE,
         Parameter.span(this.inlandContinentalness, this.farInlandContinentalness),
         this.erosions[6],
         weirdness,
         0.0F,
         ESBiomeData.DARK_SWAMP
      );

      for (int temperatureIndex = 0; temperatureIndex < this.temperatures.length; temperatureIndex++) {
         Parameter temperature = this.temperatures[temperatureIndex];

         for (int humidityIndex = 0; humidityIndex < this.humidities.length; humidityIndex++) {
            Parameter humidity = this.humidities[humidityIndex];
            ResourceKey<BiomeData> middleBiomeData = this.pickMiddleBiome(temperatureIndex, humidityIndex, weirdness);
            this.addSurfaceBiome(
               biomes,
               temperature,
               humidity,
               Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness),
               Parameter.span(this.erosions[0], this.erosions[1]),
               weirdness,
               0.0F,
               middleBiomeData
            );
         }
      }
   }

   private void addUndergroundBiomes(Consumer<Pair<ParameterPoint, ResourceKey<BiomeData>>> biomes) {
   }

   private ResourceKey<BiomeData> pickMiddleBiome(int temperatureIndex, int humidityIndex, Parameter weirdness) {
      if (weirdness.max() < 0L) {
         return this.MIDDLE_BIOMES[temperatureIndex][humidityIndex];
      }

      ResourceKey<BiomeData> variant = this.MIDDLE_BIOMES_VARIANT[temperatureIndex][humidityIndex];
      return variant == null ? this.MIDDLE_BIOMES[temperatureIndex][humidityIndex] : variant;
   }

   private ResourceKey<BiomeData> pickShatteredCoastBiome(int temperatureIndex, int humidityIndex, Parameter weirdness) {
      return weirdness.max() >= 0L ? this.pickMiddleBiome(temperatureIndex, humidityIndex, weirdness) : this.pickBeachBiome(temperatureIndex);
   }

   private ResourceKey<BiomeData> pickBeachBiome(int temperatureIndex) {
      return temperatureIndex == 4 ? ESBiomeData.CRYSTALLIZED_DESERT : ESBiomeData.WARM_SHORE;
   }

   private ResourceKey<BiomeData> pickPlateauBiome(int temperatureIndex, int humidityIndex, Parameter weirdness) {
      if (weirdness.max() >= 0L) {
         ResourceKey<BiomeData> variant = this.PLATEAU_BIOMES_VARIANT[temperatureIndex][humidityIndex];
         if (variant != null) {
            return variant;
         }
      }

      return this.PLATEAU_BIOMES[temperatureIndex][humidityIndex];
   }

   private ResourceKey<BiomeData> pickShatteredBiome(int temperatureIndex, int humidityIndex, Parameter weirdness) {
      ResourceKey<BiomeData> biome = this.SHATTERED_BIOMES[temperatureIndex][humidityIndex];
      return biome == null ? this.pickMiddleBiome(temperatureIndex, humidityIndex, weirdness) : biome;
   }

   private void addSurfaceBiome(
      Consumer<Pair<ParameterPoint, ResourceKey<BiomeData>>> biomes,
      Parameter temperature,
      Parameter humidity,
      Parameter continentalness,
      Parameter erosion,
      Parameter weirdness,
      float offset,
      ResourceKey<BiomeData> second
   ) {
      biomes.accept(Pair.of(Climate.parameters(temperature, humidity, continentalness, erosion, Parameter.point(0.0F), weirdness, offset), second));
      biomes.accept(Pair.of(Climate.parameters(temperature, humidity, continentalness, erosion, Parameter.point(1.0F), weirdness, offset), second));
   }

   private void addUndergroundBiome(
      Consumer<Pair<ParameterPoint, ResourceKey<BiomeData>>> biomes,
      Parameter temperature,
      Parameter humidity,
      Parameter continentalness,
      Parameter erosion,
      Parameter weirdness,
      float offset,
      ResourceKey<BiomeData> biome
   ) {
      biomes.accept(Pair.of(Climate.parameters(temperature, humidity, continentalness, erosion, Parameter.span(0.2F, 0.9F), weirdness, offset), biome));
   }

   private void addBottomBiome(
      Consumer<Pair<ParameterPoint, ResourceKey<BiomeData>>> biomes,
      Parameter temperature,
      Parameter humidity,
      Parameter continentalness,
      Parameter erosion,
      Parameter weirdness,
      float offset,
      ResourceKey<BiomeData> biome
   ) {
      biomes.accept(Pair.of(Climate.parameters(temperature, humidity, continentalness, erosion, Parameter.point(1.1F), weirdness, offset), biome));
   }
}
