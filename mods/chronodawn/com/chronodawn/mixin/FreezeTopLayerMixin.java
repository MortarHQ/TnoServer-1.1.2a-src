package com.chronodawn.mixin;

import com.chronodawn.registry.ModBlocks;
import com.chronodawn.registry.ModDimensions;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.QuartPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate.Sampler;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowyDirtBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.SnowAndFreezeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SnowAndFreezeFeature.class)
public abstract class FreezeTopLayerMixin {
   private static final int TRANSITION_RADIUS = 8;
   private static final int SURFACE_BLEND_OUTER_RADIUS = 4;
   private static final int SAMPLE_STEP = 2;
   private static final int COARSE_SAMPLE_STEP = 8;
   private static final int SAMPLE_PADDING = 16;
   private static final int SAMPLE_AREA_SIZE = 48;
   private static final int CHRONO_DAWN_MIN_Y = -64;
   private static final float MAX_ADD_PROBABILITY = 0.7F;
   private static final float MAX_REMOVE_PROBABILITY = 0.8F;
   private static final float MAX_SURFACE_BLEND_PROBABILITY = 0.9F;

   @Inject(method = "place(Lnet/minecraft/world/level/levelgen/feature/FeaturePlaceContext;)Z", at = @At("HEAD"), cancellable = true)
   private void chronodawn$skipUnneededFreezeTopLayer(FeaturePlaceContext<NoneFeatureConfiguration> context, CallbackInfoReturnable<Boolean> cir) {
      WorldGenLevel level = context.level();
      if (level.getLevel().dimension().equals(ModDimensions.CHRONO_DAWN_DIMENSION)) {
         BlockPos origin = context.origin();
         int startX = origin.getX() & -16;
         int startZ = origin.getZ() & -16;
         ServerChunkCache chunkSource = level.getLevel().getChunkSource();
         BiomeSource biomeSource = chunkSource.getGenerator().getBiomeSource();
         Sampler climateSampler = chunkSource.randomState().sampler();
         if (!hasSnowyBiomeNearby(biomeSource, climateSampler, startX, startZ)) {
            cir.setReturnValue(false);
         }
      }
   }

   @Inject(method = "place(Lnet/minecraft/world/level/levelgen/feature/FeaturePlaceContext;)Z", at = @At("RETURN"))
   private void chronodawn$extendSnowTransition(FeaturePlaceContext<NoneFeatureConfiguration> context, CallbackInfoReturnable<Boolean> cir) {
      WorldGenLevel level = context.level();
      if (level.getLevel().dimension().equals(ModDimensions.CHRONO_DAWN_DIMENSION)) {
         BlockPos origin = context.origin();
         int startX = origin.getX() & -16;
         int startZ = origin.getZ() & -16;
         ServerChunkCache chunkSource = level.getLevel().getChunkSource();
         BiomeSource biomeSource = chunkSource.getGenerator().getBiomeSource();
         Sampler climateSampler = chunkSource.randomState().sampler();
         if (hasSnowyBoundaryNearby(biomeSource, climateSampler, startX, startZ)) {
            boolean[][] snowySamples = new boolean[48][48];
            boolean[][] precipitationSamples = new boolean[48][48];
            boolean hasSnowyBiome = false;
            boolean hasWarmBiome = false;

            for (int sampleX = 0; sampleX < 48; sampleX++) {
               for (int sampleZ = 0; sampleZ < 48; sampleZ++) {
                  Biome biome = getNoiseBiome(biomeSource, climateSampler, startX + sampleX - 16, startZ + sampleZ - 16);
                  boolean snowy = biome.getBaseTemperature() < 0.15F;
                  snowySamples[sampleX][sampleZ] = snowy;
                  precipitationSamples[sampleX][sampleZ] = biome.hasPrecipitation();
                  hasSnowyBiome |= snowy;
                  hasWarmBiome |= !snowy;
               }
            }

            if (hasSnowyBiome && hasWarmBiome) {
               MutableBlockPos mutable = new MutableBlockPos();

               for (int localX = 0; localX < 16; localX++) {
                  for (int localZ = 0; localZ < 16; localZ++) {
                     int worldX = startX + localX;
                     int worldZ = startZ + localZ;
                     int sampleX = localX + 16;
                     int sampleZ = localZ + 16;
                     if (!snowySamples[sampleX][sampleZ] && precipitationSamples[sampleX][sampleZ]) {
                        int snowyCount = 0;
                        int minDist = 9;

                        for (int dx = -8; dx <= 8; dx += 2) {
                           for (int dz = -8; dz <= 8; dz += 2) {
                              if (snowySamples[sampleX + dx][sampleZ + dz]) {
                                 snowyCount++;
                                 int dist = Math.max(Math.abs(dx), Math.abs(dz));
                                 minDist = Math.min(minDist, dist);
                              }
                           }
                        }

                        if (snowyCount != 0) {
                           int totalSamples = 81;
                           float snowyRatio = (float)snowyCount / totalSamples;
                           float distFactor = Math.max(0.0F, 1.0F - (minDist - 1) / 8.0F);
                           float probability = 0.7F * snowyRatio * distFactor;
                           if (!(positionHash(worldX, worldZ) >= probability)) {
                              int snowY = level.getHeight(Types.MOTION_BLOCKING, worldX, worldZ);
                              mutable.set(worldX, snowY, worldZ);
                              if (level.getBlockState(mutable).isAir()) {
                                 mutable.setY(snowY - 1);
                                 BlockState belowState = level.getBlockState(mutable);
                                 if (belowState.isFaceSturdy(level, mutable, Direction.UP)) {
                                    mutable.setY(snowY);
                                    level.setBlock(mutable, Blocks.SNOW.defaultBlockState(), 2);
                                 }
                              }
                           }
                        }
                     }
                  }
               }

               for (int localX = 0; localX < 16; localX++) {
                  for (int localZ = 0; localZ < 16; localZ++) {
                     int worldX = startX + localX;
                     int worldZ = startZ + localZ;
                     int sampleX = localX + 16;
                     int sampleZ = localZ + 16;
                     if (snowySamples[sampleX][sampleZ]) {
                        int nonSnowyCount = 0;
                        int minDist = 9;

                        for (int dx = -8; dx <= 8; dx += 2) {
                           for (int dz = -8; dz <= 8; dz += 2) {
                              if ((dx != 0 || dz != 0) && !snowySamples[sampleX + dx][sampleZ + dz]) {
                                 nonSnowyCount++;
                                 int dist = Math.max(Math.abs(dx), Math.abs(dz));
                                 minDist = Math.min(minDist, dist);
                              }
                           }
                        }

                        if (nonSnowyCount != 0) {
                           int totalSamples = 80;
                           float nonSnowyRatio = (float)nonSnowyCount / totalSamples;
                           float distFactor = Math.max(0.0F, 1.0F - (minDist - 1) / 8.0F);
                           float removeProbability = 0.8F * nonSnowyRatio * distFactor;
                           if (!(positionHash(worldX + 32768, worldZ + 32768) >= removeProbability)) {
                              int snowY = level.getHeight(Types.MOTION_BLOCKING, worldX, worldZ);
                              mutable.set(worldX, snowY, worldZ);
                              if (level.getBlockState(mutable).is(Blocks.SNOW)) {
                                 level.setBlock(mutable, Blocks.AIR.defaultBlockState(), 2);
                              } else {
                                 mutable.setY(snowY - 1);
                                 if (level.getBlockState(mutable).is(Blocks.SNOW)) {
                                    level.setBlock(mutable, Blocks.AIR.defaultBlockState(), 2);
                                 }
                              }
                           }
                        }
                     }
                  }
               }

               blendSurfaceBlocks(level, startX, startZ, snowySamples, mutable);
            }
         }
      }
   }

   private static Biome getNoiseBiome(BiomeSource biomeSource, Sampler climateSampler, int x, int z) {
      return (Biome)biomeSource.getNoiseBiome(QuartPos.fromBlock(x), QuartPos.fromBlock(0), QuartPos.fromBlock(z), climateSampler).value();
   }

   private static boolean hasSnowyBoundaryNearby(BiomeSource biomeSource, Sampler climateSampler, int startX, int startZ) {
      boolean hasSnowyBiome = false;
      boolean hasWarmBiome = false;

      for (int sampleX = 0; sampleX < 48; sampleX += 8) {
         for (int sampleZ = 0; sampleZ < 48; sampleZ += 8) {
            Biome biome = getNoiseBiome(biomeSource, climateSampler, startX + sampleX - 16, startZ + sampleZ - 16);
            if (biome.getBaseTemperature() < 0.15F) {
               hasSnowyBiome = true;
            } else {
               hasWarmBiome = true;
            }

            if (hasSnowyBiome && hasWarmBiome) {
               return true;
            }
         }
      }

      return false;
   }

   private static boolean hasSnowyBiomeNearby(BiomeSource biomeSource, Sampler climateSampler, int startX, int startZ) {
      for (int sampleX = 0; sampleX < 48; sampleX += 8) {
         for (int sampleZ = 0; sampleZ < 48; sampleZ += 8) {
            Biome biome = getNoiseBiome(biomeSource, climateSampler, startX + sampleX - 16, startZ + sampleZ - 16);
            if (biome.getBaseTemperature() < 0.15F) {
               return true;
            }
         }
      }

      return false;
   }

   private static void blendSurfaceBlocks(WorldGenLevel level, int startX, int startZ, boolean[][] snowySamples, MutableBlockPos mutable) {
      for (int localX = -4; localX < 20; localX++) {
         for (int localZ = -4; localZ < 20; localZ++) {
            int worldX = startX + localX;
            int worldZ = startZ + localZ;
            int sampleX = localX + 16;
            int sampleZ = localZ + 16;
            boolean snowy = snowySamples[sampleX][sampleZ];
            int oppositeBiomeCount = 0;
            int minDist = 9;

            for (int dx = -8; dx <= 8; dx += 2) {
               for (int dz = -8; dz <= 8; dz += 2) {
                  if ((dx != 0 || dz != 0) && snowySamples[sampleX + dx][sampleZ + dz] != snowy) {
                     oppositeBiomeCount++;
                     int dist = Math.max(Math.abs(dx), Math.abs(dz));
                     minDist = Math.min(minDist, dist);
                  }
               }
            }

            if (oppositeBiomeCount != 0) {
               int totalSamples = 80;
               float oppositeRatio = (float)oppositeBiomeCount / totalSamples;
               float distFactor = Math.max(0.0F, 1.0F - (minDist - 1) / 8.0F);
               float probability = 0.9F * oppositeRatio * distFactor;
               if (!(positionHash(worldX + 65536, worldZ - 65536) >= probability)) {
                  int surfaceY = findSurfaceY(level, worldX, worldZ, mutable);
                  if (surfaceY != Integer.MIN_VALUE) {
                     mutable.set(worldX, surfaceY, worldZ);
                     BlockState surfaceState = level.getBlockState(mutable);
                     if (snowy) {
                        blendSnowySurfaceTowardWarm(level, mutable, surfaceState);
                     } else {
                        blendWarmSurfaceTowardSnowy(level, mutable, surfaceState);
                     }
                  }
               }
            }
         }
      }
   }

   private static int findSurfaceY(WorldGenLevel level, int x, int z, MutableBlockPos mutable) {
      int y = level.getHeight(Types.MOTION_BLOCKING, x, z) - 1;

      for (int minY = -64; y >= minY; y--) {
         mutable.set(x, y, z);
         BlockState state = level.getBlockState(mutable);
         if (!state.isAir() && !state.is(Blocks.SNOW)) {
            return y;
         }
      }

      return Integer.MIN_VALUE;
   }

   private static void blendSnowySurfaceTowardWarm(WorldGenLevel level, MutableBlockPos pos, BlockState surfaceState) {
      if (surfaceState.is(Blocks.ICE)) {
         level.setBlock(pos, Blocks.WATER.defaultBlockState(), 2);
      } else if (isTemporalSoil(surfaceState)) {
         level.setBlock(pos, temporalGrassState(level, pos), 2);
      }
   }

   private static void blendWarmSurfaceTowardSnowy(WorldGenLevel level, MutableBlockPos pos, BlockState surfaceState) {
      if (surfaceState.is(Blocks.WATER)) {
         level.setBlock(pos, Blocks.ICE.defaultBlockState(), 2);
      } else if (isTemporalSoil(surfaceState)) {
         level.setBlock(pos, temporalGrassState(level, pos), 2);
      }
   }

   private static boolean isTemporalSoil(BlockState state) {
      return state.is((Block)ModBlocks.TEMPORAL_GRASS_BLOCK.get())
         || state.is((Block)ModBlocks.TEMPORAL_DIRT.get())
         || state.is((Block)ModBlocks.COARSE_TEMPORAL_DIRT.get());
   }

   private static BlockState temporalGrassState(WorldGenLevel level, BlockPos pos) {
      boolean snowy = level.getBlockState(pos.above()).is(Blocks.SNOW);
      return (BlockState)((Block)ModBlocks.TEMPORAL_GRASS_BLOCK.get()).defaultBlockState().setValue(SnowyDirtBlock.SNOWY, snowy);
   }

   private static float positionHash(int x, int z) {
      long seed = x * 341873128712L + z * 132897987541L;
      seed = (seed ^ seed >>> 13) * 2654435769L;
      seed ^= seed >>> 16;
      return (float)(seed & 16777215L) / 1.6777215E7F;
   }
}
