package com.moepus.biomespy.biome;

import net.minecraft.core.Holder;
import net.minecraft.core.QuartPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.Climate.ParameterList;
import net.minecraft.world.level.biome.Climate.Sampler;
import net.minecraft.world.level.biome.Climate.TargetPoint;
import net.minecraft.world.level.levelgen.DensityFunction.SinglePointContext;

public final class LazyBiomeNoiseChecker {
   private final BiomeEnvelopeSelector envelopeSelector;
   private final SinglePointContext ctx;
   private final ParameterList<Holder<Biome>> parameters;
   private Long temperature;
   private Long humidity;
   private Long continentalness;
   private Long erosion;
   private Long weirdness;

   public LazyBiomeNoiseChecker(BiomeEnvelopeSelector envelopeSelector, ParameterList<Holder<Biome>> parameters, int x, int z) {
      this.envelopeSelector = envelopeSelector;
      this.parameters = parameters;
      this.ctx = new SinglePointContext(x, 0, z);
   }

   private long getTemperature(Sampler sampler) {
      if (this.temperature == null) {
         this.temperature = Climate.quantizeCoord((float)sampler.temperature().compute(this.ctx));
      }

      return this.temperature;
   }

   private long getHumidity(Sampler sampler) {
      if (this.humidity == null) {
         this.humidity = Climate.quantizeCoord((float)sampler.humidity().compute(this.ctx));
      }

      return this.humidity;
   }

   private long getContinentalness(Sampler sampler) {
      if (this.continentalness == null) {
         this.continentalness = Climate.quantizeCoord((float)sampler.continentalness().compute(this.ctx));
      }

      return this.continentalness;
   }

   private long getErosion(Sampler sampler) {
      if (this.erosion == null) {
         this.erosion = Climate.quantizeCoord((float)sampler.erosion().compute(this.ctx));
      }

      return this.erosion;
   }

   private long getWeirdness(Sampler sampler) {
      if (this.weirdness == null) {
         this.weirdness = Climate.quantizeCoord((float)sampler.weirdness().compute(this.ctx));
      }

      return this.weirdness;
   }

   private long computeDepth(Sampler sampler, int y) {
      SinglePointContext depthCtx = new SinglePointContext(this.ctx.blockX(), y, this.ctx.blockZ());
      return Climate.quantizeCoord((float)sampler.depth().compute(depthCtx));
   }

   public boolean matches(Sampler sampler) {
      BiomeEnvelope env = this.envelopeSelector.getEnvelope(this.parameters, QuartPos.fromBlock(this.ctx.blockX()), 0, QuartPos.fromBlock(this.ctx.blockZ()));
      if (env.impossible) {
         return false;
      }

      for (BiomeNoiseCheckState.NoiseType type : BiomeNoiseCheckState.NoiseType.values()) {
         if (!this.checkSingleNoise(sampler, env, type)) {
            return false;
         }
      }

      return true;
   }

   public boolean matches(Sampler sampler, BiomeNoiseCheckState state, int y) {
      BiomeEnvelope env = this.envelopeSelector
         .getEnvelope(this.parameters, QuartPos.fromBlock(this.ctx.blockX()), QuartPos.fromBlock(y), QuartPos.fromBlock(this.ctx.blockZ()));
      if (env.impossible) {
         return false;
      }

      BiomeNoiseCheckState.NoiseType priority = state.getLastFilteredNoise();
      if (priority != BiomeNoiseCheckState.NoiseType.NONE && !this.checkSingleNoise(sampler, env, priority)) {
         state.setLastFilteredNoise(priority);
         return false;
      }

      for (BiomeNoiseCheckState.NoiseType type : BiomeNoiseCheckState.NoiseType.values()) {
         if (type != priority && !this.checkSingleNoise(sampler, env, type)) {
            state.setLastFilteredNoise(type);
            return false;
         }
      }

      return true;
   }

   private boolean checkSingleNoise(Sampler sampler, BiomeEnvelope env, BiomeNoiseCheckState.NoiseType type) {
      switch (type) {
         case TEMPERATURE:
            if (env.tMin <= env.tMax && env.tMin + 20000L > env.tMax) {
               long v = this.getTemperature(sampler);
               if (v < env.tMin || v > env.tMax) {
                  return false;
               }
            }
            break;
         case HUMIDITY:
            if (env.hMin <= env.hMax && env.hMin + 20000L > env.hMax) {
               long v = this.getHumidity(sampler);
               if (v < env.hMin || v > env.hMax) {
                  return false;
               }
            }
            break;
         case CONTINENTALNESS:
            if (env.cMin <= env.cMax && env.cMin + 20000L > env.cMax) {
               long v = this.getContinentalness(sampler);
               if (v < env.cMin || v > env.cMax) {
                  return false;
               }
            }
            break;
         case EROSION:
            if (env.eMin <= env.eMax && env.eMin + 20000L > env.eMax) {
               long v = this.getErosion(sampler);
               if (v < env.eMin || v > env.eMax) {
                  return false;
               }
            }
            break;
         case WEIRDNESS:
            if (env.wMin <= env.wMax && env.wMin + 20000L > env.wMax) {
               long v = this.getWeirdness(sampler);
               if (v < env.wMin || v > env.wMax) {
                  return false;
               }
            }
      }

      return true;
   }

   public TargetPoint toTargetPoint(Sampler sampler, int y) {
      return new TargetPoint(
         this.getTemperature(sampler),
         this.getHumidity(sampler),
         this.getContinentalness(sampler),
         this.getErosion(sampler),
         this.computeDepth(sampler, y),
         this.getWeirdness(sampler)
      );
   }
}
