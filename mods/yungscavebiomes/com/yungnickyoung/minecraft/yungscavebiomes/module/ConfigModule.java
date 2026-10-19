package com.yungnickyoung.minecraft.yungscavebiomes.module;

public class ConfigModule {
   public ConfigModule.FrostedCaves frostedCaves = new ConfigModule.FrostedCaves();
   public ConfigModule.LostCaves lostCaves = new ConfigModule.LostCaves();
   public ConfigModule.Other other = new ConfigModule.Other();

   public static class FrostedCaves {
      public ConfigModule.FrostedCaves.BiomeNoiseParameters noiseParameters = new ConfigModule.FrostedCaves.BiomeNoiseParameters();

      public static class BiomeNoiseParameters {
         public float temperatureMin = -1.0F;
         public float temperatureMax = -0.7F;
         public float humidityMin = -1.0F;
         public float humidityMax = 1.0F;
         public float continentalnessMin = -0.19F;
         public float continentalnessMax = 1.0F;
         public float erosionMin = -1.0F;
         public float erosionMax = 1.0F;
         public float depthMin = 0.2F;
         public float depthMax = 0.9F;
         public float weirdnessMin = -1.0F;
         public float weirdnessMax = 1.0F;
         public float offset = 0.0F;
      }
   }

   public static class LostCaves {
      public boolean enableSandstorms = true;
      public boolean extraSandstormParticles = true;
      public int minSandstormDuration = 120;
      public int maxSandstormDuration = 800;
      public int minTimeBetweenSandstorms = 1200;
      public int maxTimeBetweenSandstorms = 2400;
      public ConfigModule.LostCaves.BiomeNoiseParameters noiseParameters = new ConfigModule.LostCaves.BiomeNoiseParameters();

      public static class BiomeNoiseParameters {
         public float temperatureMin = 0.55F;
         public float temperatureMax = 1.0F;
         public float humidityMin = -1.0F;
         public float humidityMax = 0.6F;
         public float continentalnessMin = 0.3F;
         public float continentalnessMax = 1.0F;
         public float erosionMin = -1.0F;
         public float erosionMax = 1.0F;
         public float depthMin = 0.2F;
         public float depthMax = 0.9F;
         public float weirdnessMin = -1.0F;
         public float weirdnessMax = 1.0F;
         public float offset = 0.0F;
      }
   }

   public static class Other {
      public ConfigModule.Other.VanillaBiomeModifications vanillaBiomeModifications = new ConfigModule.Other.VanillaBiomeModifications();

      public static class VanillaBiomeModifications {
         public ConfigModule.Other.VanillaBiomeModifications.DripstoneCaves dripstoneCaves = new ConfigModule.Other.VanillaBiomeModifications.DripstoneCaves();

         public static class DripstoneCaves {
            public float temperatureMin = -0.7F;
            public float temperatureMax = 1.0F;
            public float humidityMin = -1.0F;
            public float humidityMax = 1.0F;
            public float continentalnessMin = 0.8F;
            public float continentalnessMax = 1.0F;
            public float erosionMin = -1.0F;
            public float erosionMax = 1.0F;
            public float depthMin = 0.2F;
            public float depthMax = 0.9F;
            public float weirdnessMin = -1.0F;
            public float weirdnessMax = 1.0F;
            public float offset = 0.0F;
         }
      }
   }
}
