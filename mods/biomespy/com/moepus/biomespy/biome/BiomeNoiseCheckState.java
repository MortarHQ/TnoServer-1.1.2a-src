package com.moepus.biomespy.biome;

public class BiomeNoiseCheckState {
   private BiomeNoiseCheckState.NoiseType lastFilteredNoise = BiomeNoiseCheckState.NoiseType.NONE;

   public BiomeNoiseCheckState.NoiseType getLastFilteredNoise() {
      return this.lastFilteredNoise;
   }

   public void setLastFilteredNoise(BiomeNoiseCheckState.NoiseType type) {
      this.lastFilteredNoise = type;
   }

   public void clear() {
      this.lastFilteredNoise = BiomeNoiseCheckState.NoiseType.NONE;
   }

   public enum NoiseType {
      TEMPERATURE,
      HUMIDITY,
      WEIRDNESS,
      CONTINENTALNESS,
      EROSION,
      NONE;
   }
}
