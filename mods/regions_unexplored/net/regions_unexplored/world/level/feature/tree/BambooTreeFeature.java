package net.regions_unexplored.world.level.feature.tree;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.regions_unexplored.registry.RUBlocks;
import net.regions_unexplored.registry.tag.RUBlockTags;
import net.regions_unexplored.world.level.feature.configuration.RUTreeConfiguration;

public class BambooTreeFeature extends Feature<RUTreeConfiguration> {
   public BambooTreeFeature(Codec<RUTreeConfiguration> codec) {
      super(codec);
   }

   public boolean place(FeaturePlaceContext<RUTreeConfiguration> context) {
      RUTreeConfiguration treeConfiguration = (RUTreeConfiguration)context.config();
      BlockPos pos = context.origin();
      RandomSource randomSource = context.random();
      WorldGenLevel level = context.level();
      int height_main = context.random().nextInt(treeConfiguration.sizeVariation()) + treeConfiguration.minimumSize();
      int check = 0;
      MutableBlockPos checkPos = pos.mutable();

      while (check <= height_main) {
         if (!this.checkReplaceable(level, checkPos)) {
            return false;
         }

         checkPos.move(Direction.UP);
         check++;
      }

      int placeCheck = 0;
      MutableBlockPos placePos = pos.mutable();

      while (placeCheck <= height_main) {
         this.placeLog(level, placePos, randomSource, treeConfiguration);
         if (placeCheck == height_main) {
            this.placeLeavesBlob(level, placePos, randomSource, treeConfiguration);
         }

         placePos.move(Direction.UP);
         placeCheck++;
      }

      return true;
   }

   public void placeLog(LevelAccessor level, BlockPos pos, RandomSource randomSource, RUTreeConfiguration treeConfiguration) {
      new Random();
      if (!level.isOutsideBuildHeight(pos)) {
         if (level.getBlockState(pos).is(RUBlocks.PEAT_GRASS_BLOCK.get())) {
            level.setBlock(pos, RUBlocks.PEAT_PODZOL.get().defaultBlockState(), 2);
         } else if (level.getBlockState(pos).is(RUBlocks.SILT_GRASS_BLOCK.get())) {
            level.setBlock(pos, RUBlocks.SILT_PODZOL.get().defaultBlockState(), 2);
         } else if (level.getBlockState(pos).is(RUBlocks.ALPHA_GRASS_BLOCK.get())) {
            level.setBlock(pos, Blocks.PODZOL.defaultBlockState(), 2);
         } else if (level.getBlockState(pos).is(Blocks.GRASS_BLOCK)) {
            level.setBlock(pos, Blocks.PODZOL.defaultBlockState(), 2);
         } else if (isReplaceable(level, pos)) {
            level.setBlock(pos, treeConfiguration.trunkProvider().getState(randomSource, pos), 2);
         }

         if (level.getBlockState(pos.below()).is(RUBlocks.PEAT_GRASS_BLOCK.get())) {
            level.setBlock(pos.below(), RUBlocks.PEAT_PODZOL.get().defaultBlockState(), 2);
         } else if (level.getBlockState(pos.below()).is(RUBlocks.SILT_GRASS_BLOCK.get())) {
            level.setBlock(pos.below(), RUBlocks.SILT_PODZOL.get().defaultBlockState(), 2);
         } else if (level.getBlockState(pos.below()).is(RUBlocks.ALPHA_GRASS_BLOCK.get())) {
            level.setBlock(pos.below(), Blocks.PODZOL.defaultBlockState(), 2);
         } else if (level.getBlockState(pos.below()).is(Blocks.GRASS_BLOCK)) {
            level.setBlock(pos.below(), Blocks.PODZOL.defaultBlockState(), 2);
         }
      }
   }

   public void placeLeavesBlob(LevelAccessor level, BlockPos pos, RandomSource randomSource, RUTreeConfiguration treeConfiguration) {
      MutableBlockPos pos1 = pos.below(3).mutable();
      this.placeLeavesBlock(level, pos1, randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos1.north(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos1.south(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos1.east(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos1.west(), randomSource, treeConfiguration);
      pos1.move(Direction.UP);
      this.placeLeavesBlock(level, pos1, randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos1.north(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos1.south(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos1.east(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos1.west(), randomSource, treeConfiguration);
      if (randomSource.nextInt(3) == 0) {
         this.placeLeavesBlock(level, pos1.north().east(), randomSource, treeConfiguration);
      }

      if (randomSource.nextInt(3) == 0) {
         this.placeLeavesBlock(level, pos1.north().west(), randomSource, treeConfiguration);
      }

      if (randomSource.nextInt(3) == 0) {
         this.placeLeavesBlock(level, pos1.south().east(), randomSource, treeConfiguration);
      }

      if (randomSource.nextInt(3) == 0) {
         this.placeLeavesBlock(level, pos1.south().west(), randomSource, treeConfiguration);
      }

      pos1.move(Direction.UP);
      this.placeLeavesBlock(level, pos1, randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos1.north(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos1.south(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos1.east(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos1.west(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos1.north().east(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos1.north().west(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos1.south().east(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos1.south().west(), randomSource, treeConfiguration);
      pos1.move(Direction.UP);
      this.placeLeavesBlock(level, pos1, randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos1.north(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos1.south(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos1.east(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos1.west(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos1.north().east(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos1.north().west(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos1.south().east(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos1.south().west(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos1.north(2), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos1.north(2).east(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos1.north(2).west(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos1.south(2), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos1.south(2).east(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos1.south(2).west(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos1.east(2), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos1.east(2).north(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos1.east(2).south(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos1.west(2), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos1.west(2).north(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos1.west(2).south(), randomSource, treeConfiguration);
      pos1.move(Direction.UP);
      this.placeLeavesBlock(level, pos1, randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos1.north(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos1.south(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos1.east(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos1.west(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos1.north().east(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos1.north().west(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos1.south().east(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos1.south().west(), randomSource, treeConfiguration);
      pos1.move(Direction.UP);
      this.placeLeavesBlock(level, pos1, randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos1.north(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos1.south(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos1.east(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos1.west(), randomSource, treeConfiguration);
      if (randomSource.nextInt(3) == 0) {
         this.placeLeavesBlock(level, pos1.north().east(), randomSource, treeConfiguration);
      }

      if (randomSource.nextInt(3) == 0) {
         this.placeLeavesBlock(level, pos1.north().west(), randomSource, treeConfiguration);
      }

      if (randomSource.nextInt(3) == 0) {
         this.placeLeavesBlock(level, pos1.south().east(), randomSource, treeConfiguration);
      }

      if (randomSource.nextInt(3) == 0) {
         this.placeLeavesBlock(level, pos1.south().west(), randomSource, treeConfiguration);
      }

      pos1.move(Direction.UP);
      this.placeLeavesBlock(level, pos1, randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos1.north(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos1.south(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos1.east(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos1.west(), randomSource, treeConfiguration);
      pos1.move(Direction.UP);
      this.placeLeavesBlock(level, pos1, randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos1.north(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos1.south(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos1.east(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos1.west(), randomSource, treeConfiguration);
      pos1.move(Direction.UP);
      this.placeLeavesBlock(level, pos1, randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos1.above(), randomSource, treeConfiguration);
   }

   public boolean placeLeavesBlock(LevelAccessor level, BlockPos pos, RandomSource randomSource, RUTreeConfiguration treeConfiguration) {
      new Random();
      if (level.isOutsideBuildHeight(pos)) {
         return true;
      }

      if (level.getBlockState(pos).canBeReplaced()) {
         level.setBlock(pos, (BlockState)treeConfiguration.foliageProvider().getState(randomSource, pos).setValue(LeavesBlock.DISTANCE, 1), 2);
      }

      return true;
   }

   public boolean checkReplaceable(LevelAccessor level, BlockPos pos) {
      return level.isOutsideBuildHeight(pos) ? false : isReplaceable(level, pos);
   }

   public static boolean isReplaceableDirtBlock(BlockState state) {
      return state.is(RUBlockTags.TREE_GRASS_REPLACEABLES);
   }

   public static boolean isReplaceableDirt(LevelSimulatedReader reader, BlockPos pos) {
      return reader.isStateAtPosition(pos, BambooTreeFeature::isReplaceableDirtBlock);
   }

   public static boolean isReplaceableBlock(BlockState state) {
      return state.is(RUBlockTags.REPLACEABLE_BLOCKS);
   }

   public static boolean isReplaceable(LevelSimulatedReader reader, BlockPos pos) {
      return reader.isStateAtPosition(pos, BambooTreeFeature::isReplaceableBlock);
   }
}
