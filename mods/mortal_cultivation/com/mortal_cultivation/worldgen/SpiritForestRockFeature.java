package com.mortal_cultivation.worldgen;

import java.util.HashSet;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.core.Direction.Plane;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public final class SpiritForestRockFeature extends Feature<NoneFeatureConfiguration> {
   public SpiritForestRockFeature() {
      super(NoneFeatureConfiguration.CODEC);
   }

   public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
      WorldGenLevel level = context.level();
      RandomSource random = context.random();
      BlockPos origin = context.origin();
      if (!QilinBambooGroveStructure.inClearing(level, origin) && Feature.isDirt(level.getBlockState(origin.below())) && level.isEmptyBlock(origin)) {
         Direction lean = Plane.HORIZONTAL.getRandomDirection(random);
         int height = 2 + random.nextInt(3);
         double radiusX = 1.5 + random.nextDouble() * 0.7;
         double radiusZ = 1.3 + random.nextDouble() * 0.7;
         Set<BlockPos> rock = new HashSet<>();

         for (int x = -3; x <= 3; x++) {
            for (int z = -3; z <= 3; z++) {
               int surface = level.getHeight(Types.OCEAN_FLOOR_WG, origin.getX() + x, origin.getZ() + z);
               if (Math.abs(surface - origin.getY()) <= 1) {
                  BlockPos ground = new BlockPos(origin.getX() + x, surface - 1, origin.getZ() + z);
                  if (Feature.isDirt(level.getBlockState(ground)) && level.getFluidState(ground.above()).isEmpty()) {
                     for (int y = -1; y < height; y++) {
                        double taper = 1.0 - Math.max(0, y) * 0.16;
                        double drift = Math.max(0, y) * 0.2;
                        double dx = (x - lean.getStepX() * drift) / (radiusX * taper);
                        double dz = (z - lean.getStepZ() * drift) / (radiusZ * taper);
                        if (!(dx * dx + dz * dz > 1.0) && y >= surface - origin.getY() - 1) {
                           BlockPos pos = origin.offset(x, y, z);
                           BlockState state = level.getBlockState(pos);
                           if (state.getFluidState().isEmpty() && (state.isAir() || state.is(BlockTags.DIRT) || state.is(BlockTags.REPLACEABLE_BY_TREES))) {
                              rock.add(pos);
                           }
                        }
                     }
                  }
               }
            }
         }

         if (rock.isEmpty()) {
            return false;
         }

         for (BlockPos pos : rock.stream().sorted(Vec3i::compareTo).toList()) {
            boolean top = !rock.contains(pos.above());
            BlockState state = top && random.nextFloat() < 0.35F
               ? Blocks.MOSS_BLOCK.defaultBlockState()
               : (random.nextFloat() < 0.24F ? Blocks.MOSSY_COBBLESTONE.defaultBlockState() : Blocks.ANDESITE.defaultBlockState());
            level.setBlock(pos, state, 2);
         }

         for (BlockPos pos : rock.stream().sorted(Vec3i::compareTo).toList()) {
            if (!rock.contains(pos.above()) && level.isEmptyBlock(pos.above())) {
               BlockState cover = random.nextFloat() < 0.18F ? Blocks.FERN.defaultBlockState() : Blocks.MOSS_CARPET.defaultBlockState();
               if (random.nextFloat() < 0.55F && cover.canSurvive(level, pos.above())) {
                  level.setBlock(pos.above(), cover, 2);
               }
            }
         }

         return true;
      } else {
         return false;
      }
   }
}
