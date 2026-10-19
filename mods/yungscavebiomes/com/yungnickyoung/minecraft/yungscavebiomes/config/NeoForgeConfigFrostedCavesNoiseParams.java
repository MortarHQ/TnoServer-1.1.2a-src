package com.yungnickyoung.minecraft.yungscavebiomes.config;

import net.neoforged.neoforge.common.ModConfigSpec.Builder;
import net.neoforged.neoforge.common.ModConfigSpec.DoubleValue;

public class NeoForgeConfigFrostedCavesNoiseParams {
   public final DoubleValue temperatureMin;
   public final DoubleValue temperatureMax;
   public final DoubleValue humidityMin;
   public final DoubleValue humidityMax;
   public final DoubleValue continentalnessMin;
   public final DoubleValue continentalnessMax;
   public final DoubleValue erosionMin;
   public final DoubleValue erosionMax;
   public final DoubleValue depthMin;
   public final DoubleValue depthMax;
   public final DoubleValue weirdnessMin;
   public final DoubleValue weirdnessMax;
   public final DoubleValue offset;

   public NeoForgeConfigFrostedCavesNoiseParams(Builder BUILDER) {
      BUILDER.push("Biome Noise Parameters");
      this.temperatureMin = BUILDER.worldRestart().defineInRange("Temperature Min", -1.0, -1.0, 1.0);
      this.temperatureMax = BUILDER.worldRestart().defineInRange("Temperature Max", -0.7, -1.0, 1.0);
      this.humidityMin = BUILDER.worldRestart().defineInRange("Humidity Min", -1.0, -1.0, 1.0);
      this.humidityMax = BUILDER.worldRestart().defineInRange("Humidity Max", 1.0, -1.0, 1.0);
      this.continentalnessMin = BUILDER.worldRestart().defineInRange("Continentalness Min", -0.19, -1.0, 1.0);
      this.continentalnessMax = BUILDER.worldRestart().defineInRange("Continentalness Max", 1.0, -1.0, 1.0);
      this.erosionMin = BUILDER.worldRestart().defineInRange("Erosion Min", -1.0, -1.0, 1.0);
      this.erosionMax = BUILDER.worldRestart().defineInRange("Erosion Max", 1.0, -1.0, 1.0);
      this.depthMin = BUILDER.worldRestart().defineInRange("Depth Min", 0.2, 0.0, 1.0);
      this.depthMax = BUILDER.worldRestart().defineInRange("Depth Max", 0.9, 0.0, 1.0);
      this.weirdnessMin = BUILDER.worldRestart().defineInRange("Weirdness Min", -1.0, -1.0, 1.0);
      this.weirdnessMax = BUILDER.worldRestart().defineInRange("Weirdness Max", 1.0, -1.0, 1.0);
      this.offset = BUILDER.worldRestart().defineInRange("Offset", 0.0, -1.0, 1.0);
      BUILDER.pop();
   }
}
