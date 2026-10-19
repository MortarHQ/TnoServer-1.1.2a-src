package net.regions_unexplored.worldgen.feature;

import com.mojang.serialization.Codec;
import javax.annotation.Nonnull;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.regions_unexplored.worldgen.feature.config.CarvedLimitedPoolFeatureConfig;

public class CarvedLimitedPoolFeature extends Feature<CarvedLimitedPoolFeatureConfig> {
   public CarvedLimitedPoolFeature(Codec<CarvedLimitedPoolFeatureConfig> codec) {
      super(codec);
   }

   public boolean place(@Nonnull FeaturePlaceContext<CarvedLimitedPoolFeatureConfig> context) {
      CarvedLimitedPoolFeatureConfig configuration = (CarvedLimitedPoolFeatureConfig)context.config();
      WorldGenLevel level = context.level();
      BlockPos origin = context.origin();
      MutableBlockPos mut = origin.mutable();
      int maxDepth = 1;

      for (int d = 0; d < maxDepth; d++) {
         boolean increasedDepth = false;
         boolean[][] waterPlacement = new boolean[18][18];

         for (int i = -1; i < 17; i++) {
            for (int j = -1; j < 17; j++) {
               if (d == 0 && level.isWaterAt(level.getHeightmapPos(Types.WORLD_SURFACE, origin.offset(i, 0, j)).below())) {
                  waterPlacement[i + 1][j + 1] = true;
               }

               mut = mut.set(level.getHeightmapPos(Types.OCEAN_FLOOR, origin.offset(i, 0, j)).below());
               if (configuration.pool().test(level, mut) && configuration.wall().test(level, mut.below())) {
                  boolean open = true;

                  for (BlockPos check : BlockPos.betweenClosed(mut.offset(-1, 1, -1), mut.offset(1, 1, 1))) {
                     open = open && (level.isEmptyBlock(check) || level.isWaterAt(check));
                     if (!open) {
                        break;
                     }
                  }

                  if (open) {
                     boolean walled = true;

                     for (BlockPos check : BlockPos.betweenClosed(mut.offset(-1, 0, -1), mut.offset(1, 0, 1))) {
                        walled = walled && (configuration.wall().test(level, check) || level.isWaterAt(check));
                        if (!walled) {
                           break;
                        }
                     }

                     if (walled) {
                        level.setBlock(mut, Blocks.WATER.defaultBlockState(), 3);
                        waterPlacement[i + 1][j + 1] = true;
                        if (!increasedDepth && maxDepth < configuration.depth()) {
                           maxDepth++;
                        }
                     }
                  }
               }
            }
         }

         if (d == 0) {
            for (int i = 0; i < 16; i++) {
               for (int j = 0; j < 16; j++) {
                  boolean water = false;

                  for (int k = -1; k < 2; k++) {
                     for (int l = -1; l < 2; l++) {
                        water = water || waterPlacement[i + 1 + k][j + 1 + l];
                        if (water) {
                           break;
                        }
                     }

                     if (water) {
                        break;
                     }
                  }

                  if (!water) {
                     for (int m = 1; m <= configuration.slopeDepth().sample(context.random()) + 1; m++) {
                        mut = mut.set(level.getHeightmapPos(Types.WORLD_SURFACE, origin.offset(i, 0, j)).below(m));
                        if (!configuration.wall().test(level, mut)) {
                           break;
                        }

                        level.setBlock(
                           mut,
                           m == 1 ? configuration.slopeTop().getState(RandomSource.create(), mut) : configuration.slope().getState(RandomSource.create(), mut),
                           3
                        );
                     }
                  }
               }
            }
         }
      }

      return true;
   }
}
