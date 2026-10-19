package com.yungnickyoung.minecraft.yungscavebiomes.world.terrablender;

import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.Climate.Parameter;
import net.minecraft.world.level.biome.Climate.ParameterPoint;

public class CaveBiomeRegionParameters {
   private static final Parameter FULL_RANGE = span(-1.0F, 1.0F);
   public static final ParameterPoint DRIPSTONE_OLD = new CaveBiomeRegionParameters.ParameterPointBuilder()
      .temperature(FULL_RANGE)
      .humidity(FULL_RANGE)
      .continentalness(span(0.8F, 1.0F))
      .erosion(FULL_RANGE)
      .depth(span(0.2F, 0.9F))
      .weirdness(FULL_RANGE)
      .offset(0.0F)
      .build();
   public static final ParameterPoint DRIPSTONE_NEW = new CaveBiomeRegionParameters.ParameterPointBuilder()
      .temperature(
         span(
            YungsCaveBiomesCommon.CONFIG.other.vanillaBiomeModifications.dripstoneCaves.temperatureMin,
            YungsCaveBiomesCommon.CONFIG.other.vanillaBiomeModifications.dripstoneCaves.temperatureMax
         )
      )
      .humidity(
         span(
            YungsCaveBiomesCommon.CONFIG.other.vanillaBiomeModifications.dripstoneCaves.humidityMin,
            YungsCaveBiomesCommon.CONFIG.other.vanillaBiomeModifications.dripstoneCaves.humidityMax
         )
      )
      .continentalness(
         span(
            YungsCaveBiomesCommon.CONFIG.other.vanillaBiomeModifications.dripstoneCaves.continentalnessMin,
            YungsCaveBiomesCommon.CONFIG.other.vanillaBiomeModifications.dripstoneCaves.continentalnessMax
         )
      )
      .erosion(
         span(
            YungsCaveBiomesCommon.CONFIG.other.vanillaBiomeModifications.dripstoneCaves.erosionMin,
            YungsCaveBiomesCommon.CONFIG.other.vanillaBiomeModifications.dripstoneCaves.erosionMax
         )
      )
      .depth(
         span(
            YungsCaveBiomesCommon.CONFIG.other.vanillaBiomeModifications.dripstoneCaves.depthMin,
            YungsCaveBiomesCommon.CONFIG.other.vanillaBiomeModifications.dripstoneCaves.depthMax
         )
      )
      .weirdness(
         span(
            YungsCaveBiomesCommon.CONFIG.other.vanillaBiomeModifications.dripstoneCaves.weirdnessMin,
            YungsCaveBiomesCommon.CONFIG.other.vanillaBiomeModifications.dripstoneCaves.weirdnessMax
         )
      )
      .offset(YungsCaveBiomesCommon.CONFIG.other.vanillaBiomeModifications.dripstoneCaves.offset)
      .build();
   public static final ParameterPoint FROSTED_CAVES = new CaveBiomeRegionParameters.ParameterPointBuilder()
      .temperature(
         span(
            YungsCaveBiomesCommon.CONFIG.frostedCaves.noiseParameters.temperatureMin, YungsCaveBiomesCommon.CONFIG.frostedCaves.noiseParameters.temperatureMax
         )
      )
      .humidity(
         span(YungsCaveBiomesCommon.CONFIG.frostedCaves.noiseParameters.humidityMin, YungsCaveBiomesCommon.CONFIG.frostedCaves.noiseParameters.humidityMax)
      )
      .continentalness(
         span(
            YungsCaveBiomesCommon.CONFIG.frostedCaves.noiseParameters.continentalnessMin,
            YungsCaveBiomesCommon.CONFIG.frostedCaves.noiseParameters.continentalnessMax
         )
      )
      .erosion(span(YungsCaveBiomesCommon.CONFIG.frostedCaves.noiseParameters.erosionMin, YungsCaveBiomesCommon.CONFIG.frostedCaves.noiseParameters.erosionMax))
      .depth(span(YungsCaveBiomesCommon.CONFIG.frostedCaves.noiseParameters.depthMin, YungsCaveBiomesCommon.CONFIG.frostedCaves.noiseParameters.depthMax))
      .weirdness(
         span(YungsCaveBiomesCommon.CONFIG.frostedCaves.noiseParameters.weirdnessMin, YungsCaveBiomesCommon.CONFIG.frostedCaves.noiseParameters.weirdnessMax)
      )
      .offset(YungsCaveBiomesCommon.CONFIG.frostedCaves.noiseParameters.offset)
      .build();
   public static final ParameterPoint LOST_CAVES = new CaveBiomeRegionParameters.ParameterPointBuilder()
      .temperature(
         span(YungsCaveBiomesCommon.CONFIG.lostCaves.noiseParameters.temperatureMin, YungsCaveBiomesCommon.CONFIG.lostCaves.noiseParameters.temperatureMax)
      )
      .humidity(span(YungsCaveBiomesCommon.CONFIG.lostCaves.noiseParameters.humidityMin, YungsCaveBiomesCommon.CONFIG.lostCaves.noiseParameters.humidityMax))
      .continentalness(
         span(
            YungsCaveBiomesCommon.CONFIG.lostCaves.noiseParameters.continentalnessMin,
            YungsCaveBiomesCommon.CONFIG.lostCaves.noiseParameters.continentalnessMax
         )
      )
      .erosion(span(YungsCaveBiomesCommon.CONFIG.lostCaves.noiseParameters.erosionMin, YungsCaveBiomesCommon.CONFIG.lostCaves.noiseParameters.erosionMax))
      .depth(span(YungsCaveBiomesCommon.CONFIG.lostCaves.noiseParameters.depthMin, YungsCaveBiomesCommon.CONFIG.lostCaves.noiseParameters.depthMax))
      .weirdness(span(YungsCaveBiomesCommon.CONFIG.lostCaves.noiseParameters.weirdnessMin, YungsCaveBiomesCommon.CONFIG.lostCaves.noiseParameters.weirdnessMax))
      .offset(YungsCaveBiomesCommon.CONFIG.lostCaves.noiseParameters.offset)
      .build();

   private static Parameter span(float min, float max) {
      return Parameter.span(min, max);
   }

   private static class ParameterPointBuilder {
      Parameter temperature;
      Parameter humidity;
      Parameter continentalness;
      Parameter erosion;
      Parameter depth;
      Parameter weirdness;
      float offset;

      public CaveBiomeRegionParameters.ParameterPointBuilder temperature(Parameter temperature) {
         this.temperature = temperature;
         return this;
      }

      public CaveBiomeRegionParameters.ParameterPointBuilder humidity(Parameter humidity) {
         this.humidity = humidity;
         return this;
      }

      public CaveBiomeRegionParameters.ParameterPointBuilder continentalness(Parameter continentalness) {
         this.continentalness = continentalness;
         return this;
      }

      public CaveBiomeRegionParameters.ParameterPointBuilder erosion(Parameter erosion) {
         this.erosion = erosion;
         return this;
      }

      public CaveBiomeRegionParameters.ParameterPointBuilder depth(Parameter depth) {
         this.depth = depth;
         return this;
      }

      public CaveBiomeRegionParameters.ParameterPointBuilder weirdness(Parameter weirdness) {
         this.weirdness = weirdness;
         return this;
      }

      public CaveBiomeRegionParameters.ParameterPointBuilder offset(float offset) {
         this.offset = offset;
         return this;
      }

      public ParameterPoint build() {
         return Climate.parameters(this.temperature, this.humidity, this.continentalness, this.erosion, this.depth, this.weirdness, this.offset);
      }
   }
}
