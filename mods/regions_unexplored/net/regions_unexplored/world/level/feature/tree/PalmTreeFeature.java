package net.regions_unexplored.world.level.feature.tree;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.regions_unexplored.config.RUConfigHandler;
import net.regions_unexplored.registry.RUBlocks;
import net.regions_unexplored.registry.tag.RUBlockTags;
import net.regions_unexplored.world.level.feature.configuration.RUTreeConfiguration;

public class PalmTreeFeature extends Feature<RUTreeConfiguration> {
   public PalmTreeFeature(Codec<RUTreeConfiguration> codec) {
      super(codec);
   }

   public boolean place(FeaturePlaceContext<RUTreeConfiguration> context) {
      RUTreeConfiguration treeConfiguration = (RUTreeConfiguration)context.config();
      BlockPos pos = context.origin();
      RandomSource randomSource = context.random();
      WorldGenLevel level = context.level();
      int height_main = context.random().nextInt(treeConfiguration.sizeVariation()) + treeConfiguration.minimumSize();
      int bendChance = randomSource.nextInt(3);
      Direction direction;
      if (randomSource.nextInt(4) == 0) {
         direction = Direction.NORTH;
      } else if (randomSource.nextInt(4) == 0) {
         direction = Direction.SOUTH;
      } else if (randomSource.nextInt(4) == 0) {
         direction = Direction.EAST;
      } else {
         direction = Direction.WEST;
      }

      int check = 0;
      MutableBlockPos checkPos = pos.mutable();

      while (check <= height_main) {
         if (!this.checkReplaceable(level, checkPos)) {
            return false;
         }

         checkPos.move(Direction.UP);
         if (check % 3 == 0 && bendChance != 0) {
            checkPos.move(direction);
         }

         check++;
      }

      int placeCheck = 0;
      MutableBlockPos placePos = pos.mutable();

      while (placeCheck <= height_main) {
         this.placeLog(level, placePos, randomSource, treeConfiguration, Axis.Y);
         if (placeCheck == 0) {
            this.placeRoot(level, placePos, randomSource, treeConfiguration);
            if (bendChance != 0 && randomSource.nextInt(2) == 0) {
            }
         }

         if (placeCheck == height_main) {
            this.placeTop(level, placePos, randomSource, treeConfiguration);
         }

         placePos.move(Direction.UP);
         if (placeCheck != 0 && placeCheck % 3 == 0 && bendChance != 0) {
            placePos.move(direction);
         }

         placeCheck++;
      }

      return true;
   }

   public void placeTop(LevelAccessor level, BlockPos pos, RandomSource randomSource, RUTreeConfiguration treeConfiguration) {
      if (!RUConfigHandler.COMMON.getBranchMode().cannotPlace()) {
         if (level.getBlockState(pos.north()).canBeReplaced() && !level.isOutsideBuildHeight(pos.north())) {
            level.setBlock(
               pos.north(),
               (BlockState)((BlockState)treeConfiguration.branchProvider().getState(randomSource, pos).trySetValue(BlockStateProperties.AXIS, Axis.Z))
                  .trySetValue(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH),
               2
            );
         }

         if (level.getBlockState(pos.south()).canBeReplaced() && !level.isOutsideBuildHeight(pos.south())) {
            level.setBlock(
               pos.south(),
               (BlockState)((BlockState)treeConfiguration.branchProvider().getState(randomSource, pos).trySetValue(BlockStateProperties.AXIS, Axis.Z))
                  .trySetValue(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH),
               2
            );
         }

         if (level.getBlockState(pos.east()).canBeReplaced() && !level.isOutsideBuildHeight(pos.east())) {
            level.setBlock(
               pos.east(),
               (BlockState)((BlockState)treeConfiguration.branchProvider().getState(randomSource, pos).trySetValue(BlockStateProperties.AXIS, Axis.X))
                  .trySetValue(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST),
               2
            );
         }

         if (level.getBlockState(pos.west()).canBeReplaced() && !level.isOutsideBuildHeight(pos.west())) {
            level.setBlock(
               pos.west(),
               (BlockState)((BlockState)treeConfiguration.branchProvider().getState(randomSource, pos).trySetValue(BlockStateProperties.AXIS, Axis.X))
                  .trySetValue(BlockStateProperties.HORIZONTAL_FACING, Direction.WEST),
               2
            );
         }
      }

      this.placeLeavesBlock(level, pos.north().east(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.north().west(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.south().east(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.south().west(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.above(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.above().north(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.above().south(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.above().east(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.above().west(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.above().north().east(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.above().north().west(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.above().south().east(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.above().south().west(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.above().above().north(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.above().above().south(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.above().above().east(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.above().above().west(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.above().above().north().north(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.above().above().south().south(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.above().above().east().east(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.above().above().west().west(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.above().above().above().north().north(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.above().above().above().south().south(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.above().above().above().east().east(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.above().above().above().west().west(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.above().above().north().north().north(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.above().above().south().south().south(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.above().above().east().east().east(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.above().above().west().west().west(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.above().north().north().north(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.above().south().south().south(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.above().east().east().east(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.above().west().west().west(), randomSource, treeConfiguration);
   }

   public boolean placeLog(LevelAccessor level, BlockPos pos, RandomSource randomSource, RUTreeConfiguration treeConfiguration, Axis axis) {
      new Random();
      if (level.isOutsideBuildHeight(pos)) {
         return true;
      }

      if (level.getBlockState(pos).is(RUBlocks.PEAT_GRASS_BLOCK.get())) {
         level.setBlock(pos, RUBlocks.PEAT_DIRT.get().defaultBlockState(), 2);
      } else if (level.getBlockState(pos).is(RUBlocks.SILT_GRASS_BLOCK.get())) {
         level.setBlock(pos, RUBlocks.SILT_DIRT.get().defaultBlockState(), 2);
      } else if (level.getBlockState(pos).is(RUBlocks.ALPHA_GRASS_BLOCK.get())) {
         level.setBlock(pos, Blocks.DIRT.defaultBlockState(), 2);
      } else if (level.getBlockState(pos).is(Blocks.GRASS_BLOCK)) {
         level.setBlock(pos, Blocks.DIRT.defaultBlockState(), 2);
      } else {
         if (!isReplaceable(level, pos)) {
            return true;
         }

         level.setBlock(pos, (BlockState)treeConfiguration.trunkProvider().getState(randomSource, pos).setValue(RotatedPillarBlock.AXIS, axis), 2);
      }

      if (level.getBlockState(pos.below()).is(RUBlocks.PEAT_GRASS_BLOCK.get())) {
         level.setBlock(pos.below(), RUBlocks.PEAT_DIRT.get().defaultBlockState(), 2);
      } else if (level.getBlockState(pos.below()).is(RUBlocks.SILT_GRASS_BLOCK.get())) {
         level.setBlock(pos.below(), RUBlocks.SILT_DIRT.get().defaultBlockState(), 2);
      } else if (level.getBlockState(pos.below()).is(RUBlocks.ALPHA_GRASS_BLOCK.get())) {
         level.setBlock(pos.below(), Blocks.DIRT.defaultBlockState(), 2);
      } else if (level.getBlockState(pos.below()).is(Blocks.GRASS_BLOCK)) {
         level.setBlock(pos.below(), Blocks.DIRT.defaultBlockState(), 2);
      }

      return true;
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

   public void placeRoot(LevelAccessor level, BlockPos pos, RandomSource randomSource, RUTreeConfiguration treeConfiguration) {
      Random random = new Random();
      int rd = random.nextInt(2) + 4;
      int i = 0;
      MutableBlockPos placePos = pos.mutable();

      while (i <= rd) {
         if (level.getBlockState(placePos).canBeReplaced() && level.getBlockState(placePos.above()).is(BlockTags.DIRT)) {
            level.setBlock(placePos, Blocks.HANGING_ROOTS.defaultBlockState(), 2);
            break;
         }

         if (!level.getBlockState(placePos).is(BlockTags.DIRT)
            && !level.getBlockState(placePos).is(BlockTags.REPLACEABLE_BY_TREES)
            && !level.isEmptyBlock(placePos)) {
            break;
         }

         this.placeLog(level, placePos, randomSource, treeConfiguration, Axis.Y);
         placePos.move(Direction.DOWN);
         i++;
      }
   }

   public boolean checkReplaceable(LevelAccessor level, BlockPos pos) {
      return level.isOutsideBuildHeight(pos) ? false : isReplaceable(level, pos);
   }

   public static boolean isReplaceableDirtBlock(BlockState state) {
      return state.is(RUBlockTags.TREE_GRASS_REPLACEABLES);
   }

   public static boolean isReplaceableDirt(LevelSimulatedReader reader, BlockPos pos) {
      return reader.isStateAtPosition(pos, PalmTreeFeature::isReplaceableDirtBlock);
   }

   public static boolean isReplaceableBlock(BlockState state) {
      return state.is(RUBlockTags.REPLACEABLE_BLOCKS);
   }

   public static boolean isReplaceable(LevelSimulatedReader reader, BlockPos pos) {
      return reader.isStateAtPosition(pos, PalmTreeFeature::isReplaceableBlock);
   }
}
