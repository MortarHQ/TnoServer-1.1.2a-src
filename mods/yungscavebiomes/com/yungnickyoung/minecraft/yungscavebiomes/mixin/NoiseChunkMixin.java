package com.yungnickyoung.minecraft.yungscavebiomes.mixin;

import com.yungnickyoung.minecraft.yungscavebiomes.world.NoiseSamplerBiomeHolder;
import net.minecraft.core.Registry;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate.Sampler;
import net.minecraft.world.level.levelgen.NoiseChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(NoiseChunk.class)
public abstract class NoiseChunkMixin implements NoiseSamplerBiomeHolder {
   @Unique
   private BiomeSource ycbBiomeSource;
   @Unique
   private Registry<Biome> ycbBiomeRegistry;
   @Unique
   private Sampler ycbClimateSampler;
   @Unique
   private long ycbWorldSeed;

   @Unique
   @Override
   public BiomeSource getBiomeSource() {
      return this.ycbBiomeSource;
   }

   @Unique
   @Override
   public Registry<Biome> getBiomeRegistry() {
      return this.ycbBiomeRegistry;
   }

   @Unique
   @Override
   public void setBiomeSource(BiomeSource source) {
      this.ycbBiomeSource = source;
   }

   @Unique
   @Override
   public void setBiomeRegistry(Registry<Biome> registry) {
      this.ycbBiomeRegistry = registry;
   }

   @Unique
   @Override
   public Sampler getClimateSampler() {
      return this.ycbClimateSampler;
   }

   @Unique
   @Override
   public void setClimateSampler(Sampler sampler) {
      this.ycbClimateSampler = sampler;
   }

   @Unique
   @Override
   public long getWorldSeed() {
      return this.ycbWorldSeed;
   }

   @Unique
   @Override
   public void setWorldSeed(long worldSeed) {
      this.ycbWorldSeed = worldSeed;
   }
}
