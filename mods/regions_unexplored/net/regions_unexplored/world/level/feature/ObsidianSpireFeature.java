package net.regions_unexplored.world.level.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.regions_unexplored.registry.RUBlocks;

public class ObsidianSpireFeature extends Feature<NoneFeatureConfiguration> {
   public ObsidianSpireFeature(Codec<NoneFeatureConfiguration> codec) {
      super(codec);
   }

   public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
      BlockPos pos = context.origin();
      WorldGenLevel level = context.level();
      if (level.getBlockState(pos.below()).getBlock() != RUBlocks.COBALT_NYLIUM) {
         return false;
      }

      if (level.isEmptyBlock(pos) && !level.isEmptyBlock(pos.below())) {
         for (MutableBlockPos placePos = pos.mutable();
            level.isEmptyBlock(placePos)
               || level.getBlockState(placePos).is(RUBlocks.COBALT_OBSIDIAN.get())
               || level.getBlockState(placePos).is(Blocks.OBSIDIAN);
            placePos.move(Direction.UP)
         ) {
            if (level.isOutsideBuildHeight(placePos)) {
               return true;
            }

            int north = context.random().nextInt(4);
            int south = context.random().nextInt(4);
            int east = context.random().nextInt(4);
            int west = context.random().nextInt(4);
            level.setBlock(placePos, Blocks.OBSIDIAN.defaultBlockState(), 2);
            if (north == 0) {
               level.setBlock(placePos.north(), RUBlocks.COBALT_OBSIDIAN.get().defaultBlockState(), 2);
               if (context.random().nextInt(3) == 0) {
                  this.placeBlob(level, placePos, context.random());
               }
            }

            if (south == 0) {
               level.setBlock(placePos.south(), RUBlocks.COBALT_OBSIDIAN.get().defaultBlockState(), 2);
               if (context.random().nextInt(3) == 0) {
                  this.placeBlob(level, placePos, context.random());
               }
            }

            if (east == 0) {
               level.setBlock(placePos.east(), RUBlocks.COBALT_OBSIDIAN.get().defaultBlockState(), 2);
               int east2 = context.random().nextInt(3);
               if (context.random().nextInt(3) == 0) {
                  this.placeBlob(level, placePos, context.random());
               }
            }

            if (west == 0) {
               level.setBlock(placePos.west(), RUBlocks.COBALT_OBSIDIAN.get().defaultBlockState(), 2);
               int west2 = context.random().nextInt(3);
               if (context.random().nextInt(3) == 0) {
                  this.placeBlob(level, placePos, context.random());
               }
            }
         }

         return true;
      } else {
         return false;
      }
   }

   private void placeBlob(WorldGenLevel level, BlockPos pos, RandomSource randomSource) {
      int chance = randomSource.nextInt(8);
      if (chance == 0) {
         this.placeSmallBlob(level, pos, randomSource);
      } else if (chance == 1) {
         this.placeSmallBlob(level, pos.south(), randomSource);
      } else if (chance == 2) {
         this.placeSmallBlob(level, pos.west(), randomSource);
      } else if (chance == 3) {
         this.placeSmallBlob(level, pos.south().west(), randomSource);
      } else if (chance == 4) {
         this.placeLargeBlob(level, pos.north().east(), randomSource);
      } else if (chance == 5) {
         this.placeLargeBlob(level, pos.north().west(), randomSource);
      } else if (chance == 6) {
         this.placeLargeBlob(level, pos.south().east(), randomSource);
      } else {
         this.placeLargeBlob(level, pos.south().west(), randomSource);
      }
   }

   private void placeSmallBlob(WorldGenLevel level, BlockPos pos, RandomSource randomSource) {
      this.placeBlock(level, pos, randomSource);
      this.placeBlock(level, pos.north(), randomSource);
      this.placeBlock(level, pos.east(), randomSource);
      this.placeBlock(level, pos.north().east(), randomSource);
      this.placeBlock(level, pos.above(), randomSource);
      this.placeBlock(level, pos.above().north(), randomSource);
      this.placeBlock(level, pos.above().east(), randomSource);
      this.placeBlock(level, pos.above().north().east(), randomSource);
   }

   private void placeLargeBlob(WorldGenLevel level, BlockPos pos, RandomSource randomSource) {
      this.placeBlock(level, pos, randomSource);
      this.placeBlock(level, pos.above(), randomSource);
      this.placeBlock(level, pos.below(), randomSource);
      this.placeBlock(level, pos.north(), randomSource);
      this.placeBlock(level, pos.above().north(), randomSource);
      this.placeBlock(level, pos.below().north(), randomSource);
      this.placeBlock(level, pos.south(), randomSource);
      this.placeBlock(level, pos.above().south(), randomSource);
      this.placeBlock(level, pos.below().south(), randomSource);
      this.placeBlock(level, pos.east(), randomSource);
      this.placeBlock(level, pos.above().east(), randomSource);
      this.placeBlock(level, pos.below().east(), randomSource);
      this.placeBlock(level, pos.west(), randomSource);
      this.placeBlock(level, pos.above().west(), randomSource);
      this.placeBlock(level, pos.below().west(), randomSource);
      this.placeBlock(level, pos.north().east(), randomSource);
      this.placeBlock(level, pos.north().west(), randomSource);
      this.placeBlock(level, pos.south().east(), randomSource);
      this.placeBlock(level, pos.south().west(), randomSource);
   }

   private void placeBlock(WorldGenLevel level, BlockPos pos, RandomSource randomSource) {
      if (level.getBlockState(pos).isAir()) {
         if (randomSource.nextInt(10) == 0) {
            level.setBlock(pos, Blocks.OBSIDIAN.defaultBlockState(), 2);
         } else {
            level.setBlock(pos, RUBlocks.COBALT_OBSIDIAN.get().defaultBlockState(), 2);
         }
      }
   }
}
