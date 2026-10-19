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
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.regions_unexplored.registry.RUBlocks;
import net.regions_unexplored.registry.tag.RUBlockTags;
import net.regions_unexplored.world.level.feature.configuration.RUTreeConfiguration;

public class AshenTreeFeature extends Feature<RUTreeConfiguration> {
   public AshenTreeFeature(Codec<RUTreeConfiguration> codec) {
      super(codec);
   }

   public boolean place(FeaturePlaceContext<RUTreeConfiguration> context) {
      RUTreeConfiguration treeConfiguration = (RUTreeConfiguration)context.config();
      BlockPos pos = context.origin();
      RandomSource randomSource = context.random();
      WorldGenLevel level = context.level();
      int height_main = context.random().nextInt(treeConfiguration.sizeVariation()) + treeConfiguration.minimumSize();
      int min_leaves = randomSource.nextInt(2) + 3;
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
         if (placeCheck >= min_leaves) {
            this.placeRandomChanceLeaves(level, placePos.north(), randomSource, treeConfiguration);
            this.placeRandomChanceLeaves(level, placePos.south(), randomSource, treeConfiguration);
            this.placeRandomChanceLeaves(level, placePos.east(), randomSource, treeConfiguration);
            this.placeRandomChanceLeaves(level, placePos.west(), randomSource, treeConfiguration);
         }

         if (placeCheck == height_main) {
            this.placeLeavesTop(level, placePos, randomSource, treeConfiguration);
            this.placeBranches(level, placePos, randomSource, treeConfiguration, height_main, min_leaves);
         }

         placePos.move(Direction.UP);
         placeCheck++;
      }

      return true;
   }

   public boolean placeLogX(LevelAccessor level, BlockPos pos, RandomSource randomSource, RUTreeConfiguration treeConfiguration) {
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

         level.setBlock(pos, (BlockState)treeConfiguration.trunkProvider().getState(randomSource, pos).setValue(RotatedPillarBlock.AXIS, Axis.X), 2);
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

   public boolean placeLogZ(LevelAccessor level, BlockPos pos, RandomSource randomSource, RUTreeConfiguration treeConfiguration) {
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

         level.setBlock(pos, (BlockState)treeConfiguration.trunkProvider().getState(randomSource, pos).setValue(RotatedPillarBlock.AXIS, Axis.Z), 2);
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

   public boolean placeLog(LevelAccessor level, BlockPos pos, RandomSource randomSource, RUTreeConfiguration treeConfiguration) {
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

         level.setBlock(pos, treeConfiguration.trunkProvider().getState(randomSource, pos), 2);
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

   public boolean placeBranches(
      LevelAccessor level, BlockPos pos, RandomSource randomSource, RUTreeConfiguration treeConfiguration, int height_main, int min_leaves
   ) {
      int n = randomSource.nextInt(height_main - min_leaves);
      int s = randomSource.nextInt(height_main - min_leaves);
      int e = randomSource.nextInt(height_main - min_leaves);
      int w = randomSource.nextInt(height_main - min_leaves);
      BlockPos n_pos = new BlockPos(pos.getX(), pos.getY() - n, pos.getZ());
      BlockPos s_pos = new BlockPos(pos.getX(), pos.getY() - s, pos.getZ());
      BlockPos e_pos = new BlockPos(pos.getX(), pos.getY() - e, pos.getZ());
      BlockPos w_pos = new BlockPos(pos.getX(), pos.getY() - w, pos.getZ());
      this.placeLogZ(level, n_pos.north(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, n_pos.north().north(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, n_pos.north().east(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, n_pos.north().west(), randomSource, treeConfiguration);
      this.placeLogZ(level, s_pos.south(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, s_pos.south().south(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, s_pos.south().east(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, s_pos.south().west(), randomSource, treeConfiguration);
      this.placeLogX(level, e_pos.east(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, e_pos.east().east(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, e_pos.east().north(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, e_pos.east().south(), randomSource, treeConfiguration);
      this.placeLogX(level, w_pos.west(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, w_pos.west().west(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, w_pos.west().north(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, w_pos.west().south(), randomSource, treeConfiguration);
      return true;
   }

   public void placeRoot(LevelAccessor level, BlockPos pos, RandomSource randomSource, RUTreeConfiguration treeConfiguration) {
      Random random = new Random();
      int rd = random.nextInt(2) + 2;
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

         this.placeLog(level, placePos, randomSource, treeConfiguration);
         placePos.move(Direction.DOWN);
         i++;
      }
   }

   public boolean placeRandomChanceLeaves(LevelAccessor level, BlockPos pos, RandomSource randomSource, RUTreeConfiguration treeConfiguration) {
      Random random = new Random();
      if (random.nextInt(3) == 0) {
         this.placeLeavesBlock(level, pos, randomSource, treeConfiguration);
      }

      return true;
   }

   public boolean placeLeavesTop(LevelAccessor level, BlockPos pos, RandomSource randomSource, RUTreeConfiguration treeConfiguration) {
      new Random();
      this.placeLeavesBlock(level, pos, randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.above(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.above().above(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.north(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.south(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.east(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.west(), randomSource, treeConfiguration);
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

   public boolean checkReplaceable(LevelAccessor level, BlockPos pos) {
      return level.isOutsideBuildHeight(pos) ? false : isReplaceable(level, pos);
   }

   public static boolean isReplaceableDirtBlock(BlockState state) {
      return state.is(RUBlockTags.TREE_GRASS_REPLACEABLES);
   }

   public static boolean isReplaceableDirt(LevelSimulatedReader reader, BlockPos pos) {
      return reader.isStateAtPosition(pos, AshenTreeFeature::isReplaceableDirtBlock);
   }

   public static boolean isReplaceableBlock(BlockState state) {
      return state.is(RUBlockTags.REPLACEABLE_BLOCKS);
   }

   public static boolean isReplaceable(LevelSimulatedReader reader, BlockPos pos) {
      return reader.isStateAtPosition(pos, AshenTreeFeature::isReplaceableBlock);
   }
}
