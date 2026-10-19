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

public class EucalyptusTreeFeature extends Feature<RUTreeConfiguration> {
   public EucalyptusTreeFeature(Codec<RUTreeConfiguration> codec) {
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
         this.placeLog(level, placePos, randomSource, treeConfiguration, Axis.Y);
         if (placeCheck > 3) {
            this.placeBranchDecorator(level, placePos, randomSource, treeConfiguration);
         }

         if (placeCheck == 0) {
            if (randomSource.nextInt(2) == 0) {
               this.placeRoot(level, placePos.below().north(), randomSource, treeConfiguration);
               this.placeLog(level, placePos.north(), randomSource, treeConfiguration, Axis.Y);
               if (randomSource.nextInt(2) == 0) {
                  this.placeLog(level, placePos.above().north(), randomSource, treeConfiguration, Axis.Y);
               }
            }

            if (randomSource.nextInt(2) == 0) {
               this.placeRoot(level, placePos.below().south(), randomSource, treeConfiguration);
               this.placeLog(level, placePos.south(), randomSource, treeConfiguration, Axis.Y);
               if (randomSource.nextInt(2) == 0) {
                  this.placeLog(level, placePos.above().south(), randomSource, treeConfiguration, Axis.Y);
               }
            }

            if (randomSource.nextInt(2) == 0) {
               this.placeRoot(level, placePos.below().east(), randomSource, treeConfiguration);
               this.placeLog(level, placePos.east(), randomSource, treeConfiguration, Axis.Y);
               if (randomSource.nextInt(2) == 0) {
                  this.placeLog(level, placePos.above().east(), randomSource, treeConfiguration, Axis.Y);
               }
            }

            if (randomSource.nextInt(2) == 0) {
               this.placeRoot(level, placePos.below().west(), randomSource, treeConfiguration);
               this.placeLog(level, placePos.west(), randomSource, treeConfiguration, Axis.Y);
               if (randomSource.nextInt(2) == 0) {
                  this.placeLog(level, placePos.above().west(), randomSource, treeConfiguration, Axis.Y);
               }
            }

            this.placeRoot(level, placePos, randomSource, treeConfiguration);
         }

         if (placeCheck == height_main) {
            this.placeTop(level, placePos, randomSource, treeConfiguration);
         }

         placePos.move(Direction.UP);
         placeCheck++;
      }

      return true;
   }

   public void placeTop(LevelAccessor level, BlockPos pos, RandomSource randomSource, RUTreeConfiguration treeConfiguration) {
      MutableBlockPos placePos = new MutableBlockPos(pos.getX(), pos.getY() + 3, pos.getZ());
      this.placeLeavesBlock(level, placePos, randomSource, treeConfiguration);
      placePos.move(Direction.DOWN);
      this.placeLeavesBlock(level, placePos, randomSource, treeConfiguration);
      this.placeLeavesBlock(level, placePos.north(), randomSource, treeConfiguration);
      if (randomSource.nextInt(2) == 0) {
         this.placeLeavesBlock(level, placePos.north().east(), randomSource, treeConfiguration);
      }

      if (randomSource.nextInt(2) == 0) {
         this.placeLeavesBlock(level, placePos.north().west(), randomSource, treeConfiguration);
      }

      this.placeLeavesBlock(level, placePos.south(), randomSource, treeConfiguration);
      if (randomSource.nextInt(2) == 0) {
         this.placeLeavesBlock(level, placePos.south().east(), randomSource, treeConfiguration);
      }

      if (randomSource.nextInt(2) == 0) {
         this.placeLeavesBlock(level, placePos.south().west(), randomSource, treeConfiguration);
      }

      this.placeLeavesBlock(level, placePos.east(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, placePos.west(), randomSource, treeConfiguration);
      placePos.move(Direction.DOWN);
      this.placeLeavesBlobMed(level, placePos, randomSource, treeConfiguration);
      placePos.move(Direction.DOWN);
      this.placeLeavesBlobLarge(level, placePos, randomSource, treeConfiguration);
      placePos.move(Direction.DOWN);
      this.placeLeavesBlobMed(level, placePos, randomSource, treeConfiguration);
      placePos.move(Direction.DOWN);
      this.placeLeavesBlobSmall(level, placePos, randomSource, treeConfiguration);
      placePos.move(Direction.DOWN);
      this.placeLeavesBlock(level, placePos.north(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, placePos.south(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, placePos.east(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, placePos.west(), randomSource, treeConfiguration);
      placePos.move(Direction.DOWN);
      this.placeLeavesBlock(level, placePos.north(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, placePos.south(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, placePos.east(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, placePos.west(), randomSource, treeConfiguration);
      placePos.move(Direction.DOWN);
      if (randomSource.nextInt(2) == 0) {
         this.placeLeavesBlock(level, placePos.north(), randomSource, treeConfiguration);
      }

      if (randomSource.nextInt(2) == 0) {
         this.placeLeavesBlock(level, placePos.south(), randomSource, treeConfiguration);
      }

      if (randomSource.nextInt(2) == 0) {
         this.placeLeavesBlock(level, placePos.east(), randomSource, treeConfiguration);
      }

      if (randomSource.nextInt(2) == 0) {
         this.placeLeavesBlock(level, placePos.west(), randomSource, treeConfiguration);
      }

      placePos.move(Direction.DOWN);
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

   public void placeBranchDecorator(LevelAccessor level, BlockPos pos, RandomSource randomSource, RUTreeConfiguration treeConfiguration) {
      if (!RUConfigHandler.COMMON.getBranchMode().cannotPlace()) {
         Random random = new Random();
         if (randomSource.nextInt(10) == 0) {
            int rd = random.nextInt(4);
            if (rd == 0) {
               this.placeNorthBranch(level, pos, randomSource, treeConfiguration);
            } else if (rd == 1) {
               this.placeSouthBranch(level, pos, randomSource, treeConfiguration);
            } else if (rd == 2) {
               this.placeEastBranch(level, pos, randomSource, treeConfiguration);
            } else {
               this.placeWestBranch(level, pos, randomSource, treeConfiguration);
            }
         }
      }
   }

   public void placeNorthBranch(LevelAccessor level, BlockPos pos, RandomSource randomSource, RUTreeConfiguration treeConfiguration) {
      if (level.getBlockState(pos.north()).canBeReplaced() && !level.isOutsideBuildHeight(pos.north())) {
         level.setBlock(
            pos.north(),
            (BlockState)((BlockState)treeConfiguration.branchProvider().getState(randomSource, pos).trySetValue(BlockStateProperties.AXIS, Axis.Z))
               .trySetValue(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH),
            2
         );
      }

      if (level.getBlockState(pos.north().above()).canBeReplaced() && !level.isOutsideBuildHeight(pos.north().above())) {
         level.setBlock(pos.north().above(), (BlockState)treeConfiguration.foliageProvider().getState(randomSource, pos).setValue(LeavesBlock.DISTANCE, 1), 2);
      }

      if (level.getBlockState(pos.north().north()).canBeReplaced() && !level.isOutsideBuildHeight(pos.north().north())) {
         level.setBlock(pos.north().north(), (BlockState)treeConfiguration.foliageProvider().getState(randomSource, pos).setValue(LeavesBlock.DISTANCE, 1), 2);
      }

      if (level.getBlockState(pos.north().east()).canBeReplaced() && !level.isOutsideBuildHeight(pos.north().east())) {
         level.setBlock(pos.north().east(), (BlockState)treeConfiguration.foliageProvider().getState(randomSource, pos).setValue(LeavesBlock.DISTANCE, 1), 2);
      }

      if (level.getBlockState(pos.north().west()).canBeReplaced() && !level.isOutsideBuildHeight(pos.north().west())) {
         level.setBlock(pos.north().west(), (BlockState)treeConfiguration.foliageProvider().getState(randomSource, pos).setValue(LeavesBlock.DISTANCE, 1), 2);
      }
   }

   public void placeSouthBranch(LevelAccessor level, BlockPos pos, RandomSource randomSource, RUTreeConfiguration treeConfiguration) {
      if (level.getBlockState(pos.south()).canBeReplaced() && !level.isOutsideBuildHeight(pos.south())) {
         level.setBlock(
            pos.south(),
            (BlockState)((BlockState)treeConfiguration.branchProvider().getState(randomSource, pos).trySetValue(BlockStateProperties.AXIS, Axis.Z))
               .trySetValue(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH),
            2
         );
      }

      if (level.getBlockState(pos.south().above()).canBeReplaced() && !level.isOutsideBuildHeight(pos.south().above())) {
         level.setBlock(pos.south().above(), (BlockState)treeConfiguration.foliageProvider().getState(randomSource, pos).setValue(LeavesBlock.DISTANCE, 1), 2);
      }

      if (level.getBlockState(pos.south().south()).canBeReplaced() && !level.isOutsideBuildHeight(pos.south().south())) {
         level.setBlock(pos.south().south(), (BlockState)treeConfiguration.foliageProvider().getState(randomSource, pos).setValue(LeavesBlock.DISTANCE, 1), 2);
      }

      if (level.getBlockState(pos.south().east()).canBeReplaced() && !level.isOutsideBuildHeight(pos.south().east())) {
         level.setBlock(pos.south().east(), (BlockState)treeConfiguration.foliageProvider().getState(randomSource, pos).setValue(LeavesBlock.DISTANCE, 1), 2);
      }

      if (level.getBlockState(pos.south().west()).canBeReplaced() && !level.isOutsideBuildHeight(pos.south().west())) {
         level.setBlock(pos.south().west(), (BlockState)treeConfiguration.foliageProvider().getState(randomSource, pos).setValue(LeavesBlock.DISTANCE, 1), 2);
      }
   }

   public void placeEastBranch(LevelAccessor level, BlockPos pos, RandomSource randomSource, RUTreeConfiguration treeConfiguration) {
      if (level.getBlockState(pos.east()).canBeReplaced() && !level.isOutsideBuildHeight(pos.east())) {
         level.setBlock(
            pos.east(),
            (BlockState)((BlockState)treeConfiguration.branchProvider().getState(randomSource, pos).trySetValue(BlockStateProperties.AXIS, Axis.X))
               .trySetValue(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST),
            2
         );
      }

      if (level.getBlockState(pos.east().above()).canBeReplaced() && !level.isOutsideBuildHeight(pos.east().above())) {
         level.setBlock(pos.east().above(), (BlockState)treeConfiguration.foliageProvider().getState(randomSource, pos).setValue(LeavesBlock.DISTANCE, 1), 2);
      }

      if (level.getBlockState(pos.east().east()).canBeReplaced() && !level.isOutsideBuildHeight(pos.east().east())) {
         level.setBlock(pos.east().east(), (BlockState)treeConfiguration.foliageProvider().getState(randomSource, pos).setValue(LeavesBlock.DISTANCE, 1), 2);
      }

      if (level.getBlockState(pos.east().south()).canBeReplaced() && !level.isOutsideBuildHeight(pos.east().south())) {
         level.setBlock(pos.east().south(), (BlockState)treeConfiguration.foliageProvider().getState(randomSource, pos).setValue(LeavesBlock.DISTANCE, 1), 2);
      }

      if (level.getBlockState(pos.east().north()).canBeReplaced() && !level.isOutsideBuildHeight(pos.east().north())) {
         level.setBlock(pos.east().north(), (BlockState)treeConfiguration.foliageProvider().getState(randomSource, pos).setValue(LeavesBlock.DISTANCE, 1), 2);
      }
   }

   public void placeWestBranch(LevelAccessor level, BlockPos pos, RandomSource randomSource, RUTreeConfiguration treeConfiguration) {
      if (level.getBlockState(pos.west()).canBeReplaced() && !level.isOutsideBuildHeight(pos.west())) {
         level.setBlock(
            pos.west(),
            (BlockState)((BlockState)treeConfiguration.branchProvider().getState(randomSource, pos).trySetValue(BlockStateProperties.AXIS, Axis.X))
               .trySetValue(BlockStateProperties.HORIZONTAL_FACING, Direction.WEST),
            2
         );
      }

      if (level.getBlockState(pos.west().above()).canBeReplaced() && !level.isOutsideBuildHeight(pos.west().above())) {
         level.setBlock(pos.west().above(), (BlockState)treeConfiguration.foliageProvider().getState(randomSource, pos).setValue(LeavesBlock.DISTANCE, 1), 2);
      }

      if (level.getBlockState(pos.west().west()).canBeReplaced() && !level.isOutsideBuildHeight(pos.west().west())) {
         level.setBlock(pos.west().west(), (BlockState)treeConfiguration.foliageProvider().getState(randomSource, pos).setValue(LeavesBlock.DISTANCE, 1), 2);
      }

      if (level.getBlockState(pos.west().south()).canBeReplaced() && !level.isOutsideBuildHeight(pos.west().south())) {
         level.setBlock(pos.west().south(), (BlockState)treeConfiguration.foliageProvider().getState(randomSource, pos).setValue(LeavesBlock.DISTANCE, 1), 2);
      }

      if (level.getBlockState(pos.west().north()).canBeReplaced() && !level.isOutsideBuildHeight(pos.west().north())) {
         level.setBlock(pos.west().north(), (BlockState)treeConfiguration.foliageProvider().getState(randomSource, pos).setValue(LeavesBlock.DISTANCE, 1), 2);
      }
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

         this.placeLog(level, placePos, randomSource, treeConfiguration, Axis.Y);
         placePos.move(Direction.DOWN);
         i++;
      }
   }

   public boolean placeLeavesBlobLarge(LevelAccessor level, BlockPos pos, RandomSource randomSource, RUTreeConfiguration treeConfiguration) {
      Random random = new Random();
      int n = random.nextInt(3);
      this.placeLeavesBlock(level, pos, randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos, randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.north(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.south(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.east(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.west(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.north().north(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.south().south(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.east().east(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.west().west(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.north().east(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.north().north().east(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.north().east().east(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.north().north().east().east(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.north().west(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.north().north().west(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.north().west().west(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.north().north().west().west(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.south().east(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.south().south().east(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.south().east().east(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.south().south().east().east(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.south().west(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.south().south().west(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.south().west().west(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.south().south().west().west(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.north().north().north(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.north().north().north().east(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.north().north().north().east().east(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.north().north().north().west(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.north().north().north().west().west(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.south().south().south(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.south().south().south().east(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.south().south().south().east().east(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.south().south().south().west(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.south().south().south().west().west(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.east().east().east(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.east().east().east().north(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.east().east().east().north().north(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.east().east().east().south(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.east().east().east().south().south(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.west().west().west(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.west().west().west().north(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.west().west().west().north().north(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.west().west().west().south(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.west().west().west().south().south(), randomSource, treeConfiguration);
      return true;
   }

   public boolean placeLeavesBlobMed(LevelAccessor level, BlockPos pos, RandomSource randomSource, RUTreeConfiguration treeConfiguration) {
      Random random = new Random();
      int n = random.nextInt(3);
      this.placeLeavesBlock(level, pos, randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos, randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.north(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.south(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.east(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.west(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.north().north(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.south().south(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.east().east(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.west().west(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.north().east(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.north().north().east(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.north().east().east(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.north().west(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.north().north().west(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.north().west().west(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.south().east(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.south().south().east(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.south().east().east(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.south().west(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.south().south().west(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.south().west().west(), randomSource, treeConfiguration);
      return true;
   }

   public boolean placeLeavesBlobSmall(LevelAccessor level, BlockPos pos, RandomSource randomSource, RUTreeConfiguration treeConfiguration) {
      Random random = new Random();
      int n = random.nextInt(3);
      this.placeLeavesBlock(level, pos, randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos, randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.north(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.south(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.east(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.west(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.north().north(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.south().south(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.east().east(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.west().west(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.north().east(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.north().west(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.south().east(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.south().west(), randomSource, treeConfiguration);
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
      return reader.isStateAtPosition(pos, EucalyptusTreeFeature::isReplaceableDirtBlock);
   }

   public static boolean isReplaceableBlock(BlockState state) {
      return state.is(RUBlockTags.REPLACEABLE_BLOCKS);
   }

   public static boolean isReplaceable(LevelSimulatedReader reader, BlockPos pos) {
      return reader.isStateAtPosition(pos, EucalyptusTreeFeature::isReplaceableBlock);
   }
}
