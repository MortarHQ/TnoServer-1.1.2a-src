package com.yungnickyoung.minecraft.yungscavebiomes.world.noise;

import com.yungnickyoung.minecraft.yungscavebiomes.world.NoiseSamplerBiomeHolder;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.util.LinearCongruentialGenerator;
import net.minecraft.util.Mth;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.DensityFunction.ContextProvider;
import net.minecraft.world.level.levelgen.DensityFunction.FunctionContext;
import net.minecraft.world.level.levelgen.DensityFunction.Visitor;

public class MarbleCavesInterpolationSlideDensityFunction implements DensityFunction {
   private static final double SQRT3 = Math.sqrt(3.0);
   private static final int SEARCH_RADIUS_INT = Mth.ceil(SQRT3 * 4.0);
   public static final int BLOCK_XYZ_OFFSET = 2;
   private static final int FIDDLE_HASH_BIT_START = 24;
   private static final int FIDDLE_HASH_BIT_COUNT = 10;
   private static final int FIDDLE_HASH_BIT_SHIFTED = 1024;
   private static final int FIDDLE_HASH_BIT_MASK = 1023;
   private static final double FIDDLE_MAGNITUDE = 1.0;
   private final NoiseSamplerBiomeHolder holder;
   private final long biomeZoomSeed;

   public MarbleCavesInterpolationSlideDensityFunction(NoiseSamplerBiomeHolder holder) {
      this.holder = holder;
      this.biomeZoomSeed = BiomeManager.obfuscateSeed(holder.getWorldSeed());
   }

   public double compute(FunctionContext context) {
      BiomeSource source = this.holder.getBiomeSource();
      Registry<Biome> biomes = this.holder.getBiomeRegistry();
      if (source != null && biomes != null) {
         int offsetX = context.blockX() - 2;
         int offsetY = context.blockY() - 2;
         int offsetZ = context.blockZ() - 2;
         int xStart = offsetX - SEARCH_RADIUS_INT >> 2;
         int yStart = offsetY - SEARCH_RADIUS_INT >> 2;
         int zStart = offsetZ - SEARCH_RADIUS_INT >> 2;
         int xEnd = offsetX + SEARCH_RADIUS_INT >> 2;
         int yEnd = offsetY + SEARCH_RADIUS_INT >> 2;
         int zEnd = offsetZ + SEARCH_RADIUS_INT >> 2;
         int xCount = xEnd - xStart + 1;
         int yCount = yEnd - yStart + 1;
         int zCount = zEnd - zStart + 1;
         double xDelta = (offsetX - (xStart << 2)) * 0.25;
         double yDelta = (offsetY - (yStart << 2)) * 0.25;
         double zDelta = (offsetZ - (zStart << 2)) * 0.25;
         double totalWeight = 0.0;
         double marbleWeight = 0.0;
         int cz = 0;
         int cy = 0;
         int cx = 0;

         while (true) {
            double fiddledDistanceSquared = getFiddledDistance(this.biomeZoomSeed, cx + xStart, cy + yStart, cz + zStart, xDelta - cx, yDelta - cy, zDelta - cz);
            if (fiddledDistanceSquared < 3.0) {
               double falloff = 3.0 - fiddledDistanceSquared;
               falloff *= falloff * falloff;
               Holder<Biome> biomeHolderHere = source.getNoiseBiome(cx + xStart, cy + yStart, cz + zStart, this.holder.getClimateSampler());
               totalWeight += falloff;
               if (this.shouldSolidifyForMarbleCaves(biomeHolderHere, cy + yStart)) {
                  marbleWeight += falloff;
               }
            }

            if (++cz >= zCount) {
               cz = 0;
               if (++cy >= yCount) {
                  cy = 0;
                  if (++cx >= xCount) {
                     return marbleWeight / totalWeight;
                  }
               }
            }
         }
      } else {
         return 0.0;
      }
   }

   private boolean shouldSolidifyForMarbleCaves(Holder<Biome> biome, int quartY) {
      return quartY <= 0;
   }

   private static double getFiddledDistance(long seed, int quartX, int quartY, int quartZ, double dx, double dy, double dz) {
      long hash = LinearCongruentialGenerator.next(seed, quartX);
      hash = LinearCongruentialGenerator.next(hash, quartY);
      hash = LinearCongruentialGenerator.next(hash, quartZ);
      hash = LinearCongruentialGenerator.next(hash, quartX);
      hash = LinearCongruentialGenerator.next(hash, quartY);
      hash = LinearCongruentialGenerator.next(hash, quartZ);
      double jz = getFiddle(hash);
      hash = LinearCongruentialGenerator.next(hash, seed);
      double jy = getFiddle(hash);
      hash = LinearCongruentialGenerator.next(hash, seed);
      double jx = getFiddle(hash);
      return Mth.square(dz + jz) + Mth.square(dy + jy) + Mth.square(dx + jx);
   }

   private static double getFiddle(long hash) {
      long hashBits = hash >> 24 & 1023L;
      return hashBits * 9.765625E-4 - 0.5;
   }

   public void fillArray(double[] doubles, ContextProvider contextProvider) {
      contextProvider.fillAllDirectly(doubles, this);
   }

   public DensityFunction mapAll(Visitor visitor) {
      return visitor.apply(new MarbleCavesInterpolationSlideDensityFunction(this.holder));
   }

   public double minValue() {
      return 0.0;
   }

   public double maxValue() {
      return 1.0;
   }

   public KeyDispatchDataCodec<? extends DensityFunction> codec() {
      return null;
   }
}
