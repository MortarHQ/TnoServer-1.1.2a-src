package net.regions_unexplored.world.level.feature.tree;

import com.mojang.serialization.Codec;
import java.util.HashSet;
import java.util.Random;
import java.util.function.BiConsumer;
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
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator.Context;
import net.regions_unexplored.config.RUConfigHandler;
import net.regions_unexplored.registry.RUBlocks;
import net.regions_unexplored.registry.tag.RUBlockTags;
import net.regions_unexplored.world.level.block.wood.AspenLogBlock;
import net.regions_unexplored.world.level.feature.configuration.RUTreeConfiguration;

public class AspenTreeFeature extends Feature<RUTreeConfiguration> {
   public AspenTreeFeature(Codec<RUTreeConfiguration> codec) {
      super(codec);
   }

   public boolean place(FeaturePlaceContext<RUTreeConfiguration> context) {
      RUTreeConfiguration config = (RUTreeConfiguration)context.config();
      BlockPos origin = context.origin();
      RandomSource randomSource = context.random();
      WorldGenLevel level = context.level();
      int height_main = context.random().nextInt(config.sizeVariation()) + config.minimumSize();
      int check = 0;
      MutableBlockPos checkPos = origin.mutable();

      while (check <= height_main) {
         if (!this.checkReplaceable(level, checkPos)) {
            return false;
         }

         checkPos.move(Direction.UP);
         check++;
      }

      int placeCheck = 0;
      MutableBlockPos placePos = origin.mutable();
      HashSet<BlockPos> logPositions = new HashSet<>();
      HashSet<BlockPos> leafPositions = new HashSet<>();

      while (placeCheck <= height_main) {
         this.placeLog(level, logPositions, placePos, randomSource, config, Axis.Y);
         if (placeCheck == 0) {
            this.placeRoot(level, logPositions, placePos, randomSource, config);
         }

         if (placeCheck == height_main - 2) {
            this.placeStem(level, logPositions, leafPositions, placePos, randomSource, config);
         }

         if (placeCheck == height_main) {
            this.placeBranches(level, logPositions, leafPositions, placePos, randomSource, config);
         }

         placePos.move(Direction.UP);
         placeCheck++;
      }

      BiConsumer<BlockPos, BlockState> decorationSetter = (pos, state) -> level.setBlock(pos, state, 19);
      if (!config.decorators().isEmpty()) {
         Context decoratorContext = new Context(level, decorationSetter, randomSource, logPositions, leafPositions, new HashSet());

         for (TreeDecorator decorator : config.decorators()) {
            decorator.place(decoratorContext);
         }
      }

      return true;
   }

   public boolean placeStem(
      LevelAccessor level,
      HashSet<BlockPos> logPositions,
      HashSet<BlockPos> leafPositions,
      BlockPos pos,
      RandomSource randomSource,
      RUTreeConfiguration treeConfiguration
   ) {
      int move = randomSource.nextInt(3) - 3;
      int rd = randomSource.nextInt(4);
      if (rd == 0 && isReplaceable(level, pos.north()) && !level.isOutsideBuildHeight(pos.north())) {
         if (randomSource.nextInt(3) != 0) {
            this.placeLog(level, logPositions, pos.north(), randomSource, treeConfiguration, Axis.Z);
         } else {
            if (RUConfigHandler.COMMON.getBranchMode().cannotPlace()) {
               return true;
            }

            level.setBlock(
               pos.north(),
               (BlockState)((BlockState)treeConfiguration.branchProvider().getState(randomSource, pos).trySetValue(BlockStateProperties.AXIS, Axis.Z))
                  .trySetValue(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH),
               2
            );
            if (randomSource.nextInt(2) == 0) {
               this.placeLeavesBlock(level, leafPositions, pos.north().above(), randomSource, treeConfiguration);
               this.placeLeavesBlock(level, leafPositions, pos.north().north(), randomSource, treeConfiguration);
               this.placeLeavesBlock(level, leafPositions, pos.north().east(), randomSource, treeConfiguration);
               this.placeLeavesBlock(level, leafPositions, pos.north().west(), randomSource, treeConfiguration);
            }
         }
      }

      if (rd == 1 && isReplaceable(level, pos.south()) && !level.isOutsideBuildHeight(pos.south())) {
         if (randomSource.nextInt(3) != 0) {
            this.placeLog(level, logPositions, pos.south(), randomSource, treeConfiguration, Axis.Z);
         } else {
            if (RUConfigHandler.COMMON.getBranchMode().cannotPlace()) {
               return true;
            }

            level.setBlock(
               pos.south(),
               (BlockState)((BlockState)treeConfiguration.branchProvider().getState(randomSource, pos).trySetValue(BlockStateProperties.AXIS, Axis.Z))
                  .trySetValue(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH),
               2
            );
            if (randomSource.nextInt(2) == 0) {
               this.placeLeavesBlock(level, leafPositions, pos.south().above(), randomSource, treeConfiguration);
               this.placeLeavesBlock(level, leafPositions, pos.south().south(), randomSource, treeConfiguration);
               this.placeLeavesBlock(level, leafPositions, pos.south().east(), randomSource, treeConfiguration);
               this.placeLeavesBlock(level, leafPositions, pos.south().west(), randomSource, treeConfiguration);
            }
         }
      }

      if (rd == 2 && isReplaceable(level, pos.east()) && !level.isOutsideBuildHeight(pos.east())) {
         if (randomSource.nextInt(3) != 0) {
            this.placeLog(level, logPositions, pos.east(), randomSource, treeConfiguration, Axis.X);
         } else {
            if (RUConfigHandler.COMMON.getBranchMode().cannotPlace()) {
               return true;
            }

            level.setBlock(
               pos.east(),
               (BlockState)((BlockState)treeConfiguration.branchProvider().getState(randomSource, pos).trySetValue(BlockStateProperties.AXIS, Axis.X))
                  .trySetValue(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST),
               2
            );
            if (randomSource.nextInt(2) == 0) {
               this.placeLeavesBlock(level, leafPositions, pos.east().above(), randomSource, treeConfiguration);
               this.placeLeavesBlock(level, leafPositions, pos.east().east(), randomSource, treeConfiguration);
               this.placeLeavesBlock(level, leafPositions, pos.east().north(), randomSource, treeConfiguration);
               this.placeLeavesBlock(level, leafPositions, pos.east().south(), randomSource, treeConfiguration);
            }
         }
      }

      if (rd == 3 && isReplaceable(level, pos.west()) && !level.isOutsideBuildHeight(pos.west())) {
         if (randomSource.nextInt(3) != 0) {
            this.placeLog(level, logPositions, pos.west(), randomSource, treeConfiguration, Axis.X);
         } else {
            if (RUConfigHandler.COMMON.getBranchMode().cannotPlace()) {
               return true;
            }

            level.setBlock(
               pos.west(),
               (BlockState)((BlockState)treeConfiguration.branchProvider().getState(randomSource, pos).trySetValue(BlockStateProperties.AXIS, Axis.X))
                  .trySetValue(BlockStateProperties.HORIZONTAL_FACING, Direction.WEST),
               2
            );
            if (randomSource.nextInt(2) == 0) {
               this.placeLeavesBlock(level, leafPositions, pos.west().above(), randomSource, treeConfiguration);
               this.placeLeavesBlock(level, leafPositions, pos.west().west(), randomSource, treeConfiguration);
               this.placeLeavesBlock(level, leafPositions, pos.west().north(), randomSource, treeConfiguration);
               this.placeLeavesBlock(level, leafPositions, pos.west().south(), randomSource, treeConfiguration);
            }
         }
      }

      return true;
   }

   public boolean placeBranches(
      LevelAccessor level,
      HashSet<BlockPos> logPositions,
      HashSet<BlockPos> leafPositions,
      BlockPos pos,
      RandomSource randomSource,
      RUTreeConfiguration treeConfiguration
   ) {
      int type = randomSource.nextInt(3);
      MutableBlockPos placePos = pos.mutable();
      MutableBlockPos placePos2 = pos.mutable();
      int pickDirection = randomSource.nextInt(4);
      Direction direction;
      Axis axis;
      if (pickDirection == 0) {
         direction = Direction.NORTH;
         axis = Axis.Z;
      } else if (pickDirection == 1) {
         direction = Direction.SOUTH;
         axis = Axis.Z;
      } else if (pickDirection == 2) {
         direction = Direction.EAST;
         axis = Axis.X;
      } else {
         direction = Direction.WEST;
         axis = Axis.X;
      }

      if (type == 0) {
         int s = randomSource.nextInt(2);
         placePos2.move(direction.getOpposite());
         this.placeLog(level, logPositions, placePos2, randomSource, treeConfiguration, axis);
         this.placeLeavesBlob(level, leafPositions, placePos2, randomSource, treeConfiguration);
         placePos.move(direction);
         placePos.move(Direction.UP);
         this.placeLog(level, logPositions, placePos, randomSource, treeConfiguration, Axis.Y);
         placePos.move(Direction.UP);
         this.placeLog(level, logPositions, placePos, randomSource, treeConfiguration, Axis.Y);
         if (s != 0) {
            this.placeLeavesBlob(level, leafPositions, placePos, randomSource, treeConfiguration);
         } else {
            placePos.move(Direction.UP);
            this.placeLog(level, logPositions, placePos, randomSource, treeConfiguration, Axis.Y);
            this.placeLeavesBlob(level, leafPositions, placePos, randomSource, treeConfiguration);
         }
      } else if (type == 1) {
         placePos.move(direction);
         placePos.move(Direction.UP);
         this.placeLog(level, logPositions, placePos, randomSource, treeConfiguration, Axis.Y);
         placePos.move(Direction.UP);
         this.placeLog(level, logPositions, placePos, randomSource, treeConfiguration, Axis.Y);
         this.placeLeavesBlob(level, leafPositions, placePos, randomSource, treeConfiguration);
      } else {
         this.placeLeavesBlob(level, leafPositions, pos, randomSource, treeConfiguration);
      }

      return true;
   }

   public void placeRoot(LevelAccessor level, HashSet<BlockPos> logPositions, BlockPos pos, RandomSource randomSource, RUTreeConfiguration treeConfiguration) {
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

         this.placeLog(level, logPositions, placePos, randomSource, treeConfiguration, Axis.Y);
         placePos.move(Direction.DOWN);
         i++;
      }
   }

   public boolean placeLeavesBlob(
      LevelAccessor level, HashSet<BlockPos> leafPositions, BlockPos pos, RandomSource randomSource, RUTreeConfiguration treeConfiguration
   ) {
      Random random = new Random();
      this.placeLeavesBlock(level, leafPositions, pos, randomSource, treeConfiguration);
      this.placeLeavesBlock(level, leafPositions, pos.north(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, leafPositions, pos.north().north(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, leafPositions, pos.south(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, leafPositions, pos.south().south(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, leafPositions, pos.east(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, leafPositions, pos.east().east(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, leafPositions, pos.west(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, leafPositions, pos.west().west(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, leafPositions, pos.north().east(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, leafPositions, pos.north().west(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, leafPositions, pos.south().east(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, leafPositions, pos.south().west(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, leafPositions, pos.above(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, leafPositions, pos.above().north(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, leafPositions, pos.above().south(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, leafPositions, pos.above().east(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, leafPositions, pos.above().west(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, leafPositions, pos.above().north().east(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, leafPositions, pos.above().north().west(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, leafPositions, pos.above().south().east(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, leafPositions, pos.above().south().west(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, leafPositions, pos.above().above(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, leafPositions, pos.above().above().north(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, leafPositions, pos.above().above().south(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, leafPositions, pos.above().above().east(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, leafPositions, pos.above().above().west(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, leafPositions, pos.above().above().above(), randomSource, treeConfiguration);
      if (random.nextInt(2) == 0) {
         this.placeLeavesBlock(level, leafPositions, pos.above().above().above().north(), randomSource, treeConfiguration);
      }

      if (random.nextInt(2) == 0) {
         this.placeLeavesBlock(level, leafPositions, pos.above().above().above().south(), randomSource, treeConfiguration);
      }

      if (random.nextInt(2) == 0) {
         this.placeLeavesBlock(level, leafPositions, pos.above().above().above().east(), randomSource, treeConfiguration);
      }

      if (random.nextInt(2) == 0) {
         this.placeLeavesBlock(level, leafPositions, pos.above().above().above().west(), randomSource, treeConfiguration);
      }

      return true;
   }

   public boolean placeLog(
      LevelAccessor level, HashSet<BlockPos> logPositions, BlockPos pos, RandomSource randomSource, RUTreeConfiguration treeConfiguration, Axis axis
   ) {
      boolean isBase = false;
      if (level.getBlockState(pos.below()).is(BlockTags.DIRT)) {
         isBase = true;
      }

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

         if (treeConfiguration.trunkProvider().getState(randomSource, pos).getBlock() instanceof AspenLogBlock) {
            logPositions.add(pos.immutable());
            level.setBlock(
               pos,
               (BlockState)((BlockState)treeConfiguration.trunkProvider().getState(randomSource, pos).setValue(RotatedPillarBlock.AXIS, axis))
                  .setValue(AspenLogBlock.IS_BASE, isBase),
               2
            );
         } else {
            logPositions.add(pos.immutable());
            level.setBlock(pos, (BlockState)treeConfiguration.trunkProvider().getState(randomSource, pos).setValue(RotatedPillarBlock.AXIS, axis), 2);
         }
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

   public boolean placeLeavesBlock(
      LevelAccessor level, HashSet<BlockPos> leafPositions, BlockPos pos, RandomSource randomSource, RUTreeConfiguration treeConfiguration
   ) {
      new Random();
      if (level.isOutsideBuildHeight(pos)) {
         return true;
      }

      if (level.getBlockState(pos).canBeReplaced()) {
         leafPositions.add(pos);
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
      return reader.isStateAtPosition(pos, AspenTreeFeature::isReplaceableDirtBlock);
   }

   public static boolean isReplaceableBlock(BlockState state) {
      return state.is(RUBlockTags.REPLACEABLE_BLOCKS);
   }

   public static boolean isReplaceable(LevelSimulatedReader reader, BlockPos pos) {
      return reader.isStateAtPosition(pos, AspenTreeFeature::isReplaceableBlock);
   }
}
