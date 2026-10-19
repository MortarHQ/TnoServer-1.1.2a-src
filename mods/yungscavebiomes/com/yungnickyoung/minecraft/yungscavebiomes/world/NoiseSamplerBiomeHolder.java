package com.yungnickyoung.minecraft.yungscavebiomes.world;

import net.minecraft.core.Registry;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate.Sampler;

public interface NoiseSamplerBiomeHolder {
   BiomeSource getBiomeSource();

   Registry<Biome> getBiomeRegistry();

   Sampler getClimateSampler();

   long getWorldSeed();

   void setBiomeSource(BiomeSource var1);

   void setBiomeRegistry(Registry<Biome> var1);

   void setClimateSampler(Sampler var1);

   void setWorldSeed(long var1);
}
