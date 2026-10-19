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
import net.regions_unexplored.world.level.block.plant.other.SpanishMossBlock;
import net.regions_unexplored.world.level.feature.configuration.RUTreeConfiguration;

public class KapokTreeFeature extends Feature<RUTreeConfiguration> {
   public KapokTreeFeature(Codec<RUTreeConfiguration> codec) {
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
         if (placeCheck > 5) {
            this.placeBranchDecorator(level, placePos, randomSource, treeConfiguration);
         }

         if (placeCheck == 0) {
            this.placeRoot(level, placePos, randomSource, treeConfiguration);
            this.placeRoot(level, placePos.north(), randomSource, treeConfiguration);
            this.placeRoot(level, placePos.south(), randomSource, treeConfiguration);
            this.placeRoot(level, placePos.east(), randomSource, treeConfiguration);
            this.placeRoot(level, placePos.west(), randomSource, treeConfiguration);
            this.placeRoot(level, placePos.north().above(), randomSource, treeConfiguration);
            this.placeRoot(level, placePos.south().above(), randomSource, treeConfiguration);
            this.placeRoot(level, placePos.east().above(), randomSource, treeConfiguration);
            this.placeRoot(level, placePos.west().above(), randomSource, treeConfiguration);
            this.placeRoot(level, placePos.north().above(2), randomSource, treeConfiguration);
            this.placeRoot(level, placePos.south().above(2), randomSource, treeConfiguration);
            this.placeRoot(level, placePos.east().above(2), randomSource, treeConfiguration);
            this.placeRoot(level, placePos.west().above(2), randomSource, treeConfiguration);
            this.placeRoot(level, placePos.north().above(3), randomSource, treeConfiguration);
            this.placeRoot(level, placePos.south().above(3), randomSource, treeConfiguration);
            this.placeRoot(level, placePos.east().above(3), randomSource, treeConfiguration);
            this.placeRoot(level, placePos.west().above(3), randomSource, treeConfiguration);
            this.placeRoot(level, placePos.north().above(4), randomSource, treeConfiguration);
            this.placeRoot(level, placePos.south().above(4), randomSource, treeConfiguration);
            this.placeRoot(level, placePos.east().above(4), randomSource, treeConfiguration);
            this.placeRoot(level, placePos.west().above(4), randomSource, treeConfiguration);
            if (randomSource.nextInt(3) == 0) {
               this.placeRoot(level, placePos.north().above(5), randomSource, treeConfiguration);
            }

            if (randomSource.nextInt(3) == 0) {
               this.placeRoot(level, placePos.south().above(5), randomSource, treeConfiguration);
            }

            if (randomSource.nextInt(3) == 0) {
               this.placeRoot(level, placePos.east().above(5), randomSource, treeConfiguration);
            }

            if (randomSource.nextInt(3) == 0) {
               this.placeRoot(level, placePos.west().above(5), randomSource, treeConfiguration);
            }

            this.placeRoot(level, placePos.north(2), randomSource, treeConfiguration);
            this.placeRoot(level, placePos.south(2), randomSource, treeConfiguration);
            this.placeRoot(level, placePos.east(2), randomSource, treeConfiguration);
            this.placeRoot(level, placePos.west(2), randomSource, treeConfiguration);
            this.placeRoot(level, placePos.north(2).above(), randomSource, treeConfiguration);
            this.placeRoot(level, placePos.south(2).above(), randomSource, treeConfiguration);
            this.placeRoot(level, placePos.east(2).above(), randomSource, treeConfiguration);
            this.placeRoot(level, placePos.west(2).above(), randomSource, treeConfiguration);
            this.placeRoot(level, placePos.north(2).above(2), randomSource, treeConfiguration);
            this.placeRoot(level, placePos.south(2).above(2), randomSource, treeConfiguration);
            this.placeRoot(level, placePos.east(2).above(2), randomSource, treeConfiguration);
            this.placeRoot(level, placePos.west(2).above(2), randomSource, treeConfiguration);
            if (level.getBlockState(placePos.north(3).below(2)).is(BlockTags.DIRT)) {
               this.placeRoot(level, placePos.north(3), randomSource, treeConfiguration);
            }

            if (level.getBlockState(placePos.south(3).below(2)).is(BlockTags.DIRT)) {
               this.placeRoot(level, placePos.south(3), randomSource, treeConfiguration);
            }

            if (level.getBlockState(placePos.east(3).below(2)).is(BlockTags.DIRT)) {
               this.placeRoot(level, placePos.east(3), randomSource, treeConfiguration);
            }

            if (level.getBlockState(placePos.east(3).below(2)).is(BlockTags.DIRT)) {
               this.placeRoot(level, placePos.east(3), randomSource, treeConfiguration);
            }
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
      MutableBlockPos placePos = new MutableBlockPos(pos.getX(), pos.getY() - 5, pos.getZ());
      int n = randomSource.nextInt(4);
      int s = randomSource.nextInt(4);
      int e = randomSource.nextInt(4);
      int w = randomSource.nextInt(4);
      this.placeLimb(level, placePos.above(n), randomSource, treeConfiguration, Direction.NORTH);
      this.placeLimb(level, placePos.above(s), randomSource, treeConfiguration, Direction.SOUTH);
      this.placeLimb(level, placePos.above(e), randomSource, treeConfiguration, Direction.EAST);
      this.placeLimb(level, placePos.above(w), randomSource, treeConfiguration, Direction.WEST);
      this.placeLeavesBlob(level, pos, randomSource, treeConfiguration);
   }

   public void placeLimb(LevelAccessor level, BlockPos pos1, RandomSource randomSource, RUTreeConfiguration treeConfiguration, Direction direction) {
      int type = randomSource.nextInt(8);
      MutableBlockPos pos = pos1.mutable();
      if (type == 0) {
         pos.move(direction);
         this.placeLogWithVines(level, pos, randomSource, treeConfiguration, direction.getAxis());
         pos.move(direction);
         this.placeLogWithVines(level, pos, randomSource, treeConfiguration, direction.getAxis());
         pos.move(direction);
         pos.move(Direction.UP);
         this.placeLogWithVines(level, pos, randomSource, treeConfiguration, direction.getAxis());
         pos.move(direction);
         this.placeLogWithVines(level, pos, randomSource, treeConfiguration, direction.getAxis());
         this.placeLeavesBlob(level, pos, randomSource, treeConfiguration);
      } else if (type == 1) {
         pos.move(direction);
         this.placeLogWithVines(level, pos, randomSource, treeConfiguration, direction.getAxis());
         pos.move(direction);
         pos.move(Direction.UP);
         this.placeLogWithVines(level, pos, randomSource, treeConfiguration, direction.getAxis());
         pos.move(direction);
         this.placeLogWithVines(level, pos, randomSource, treeConfiguration, direction.getAxis());
         pos.move(direction);
         pos.move(Direction.UP);
         this.placeLogWithVines(level, pos, randomSource, treeConfiguration, direction.getAxis());
         this.placeLeavesBlob(level, pos, randomSource, treeConfiguration);
      } else if (type == 2) {
         pos.move(direction);
         this.placeLog(level, pos, randomSource, treeConfiguration, direction.getAxis());
         this.placeLogWithVines(level, pos.below(), randomSource, treeConfiguration, Axis.Y);
         pos.move(direction);
         this.placeLogWithVines(level, pos, randomSource, treeConfiguration, direction.getAxis());
         pos.move(direction);
         this.placeLogWithVines(level, pos, randomSource, treeConfiguration, direction.getAxis());
         pos.move(direction);
         pos.move(Direction.UP);
         this.placeLogWithVines(level, pos, randomSource, treeConfiguration, Axis.Y);
         pos.move(Direction.UP);
         this.placeLog(level, pos, randomSource, treeConfiguration, Axis.Y);
         this.placeLeavesBlob(level, pos, randomSource, treeConfiguration);
      } else if (type == 3) {
         pos.move(direction);
         this.placeLogWithVines(level, pos, randomSource, treeConfiguration, direction.getAxis());
         pos.move(direction);
         this.placeLogWithVines(level, pos, randomSource, treeConfiguration, direction.getAxis());
         pos.move(direction);
         pos.move(Direction.UP);
         this.placeLogWithVines(level, pos, randomSource, treeConfiguration, direction.getAxis());
         pos.move(direction);
         pos.move(Direction.UP);
         this.placeLogWithVines(level, pos, randomSource, treeConfiguration, Axis.Y);
         pos.move(Direction.UP);
         this.placeLog(level, pos, randomSource, treeConfiguration, Axis.Y);
         this.placeLeavesBlob(level, pos, randomSource, treeConfiguration);
      } else if (type == 4) {
         pos.move(direction);
         this.placeLog(level, pos, randomSource, treeConfiguration, direction.getAxis());
         this.placeLogWithVines(level, pos.below(2), randomSource, treeConfiguration, direction.getAxis());
         pos.move(direction);
         this.placeLog(level, pos, randomSource, treeConfiguration, direction.getAxis());
         this.placeLogWithVines(level, pos.below(), randomSource, treeConfiguration, Axis.Y);
         pos.move(direction);
         this.placeLogWithVines(level, pos, randomSource, treeConfiguration, direction.getAxis());
         pos.move(direction);
         pos.move(Direction.UP);
         this.placeLogWithVines(level, pos, randomSource, treeConfiguration, Axis.Y);
         pos.move(Direction.UP);
         this.placeLog(level, pos, randomSource, treeConfiguration, Axis.Y);
         this.placeLeavesBlob(level, pos, randomSource, treeConfiguration);
      } else if (type == 5) {
         pos.move(direction);
         this.placeLog(level, pos, randomSource, treeConfiguration, direction.getAxis());
         this.placeLogWithVines(level, pos.below(3), randomSource, treeConfiguration, direction.getAxis());
         pos.move(direction);
         this.placeLog(level, pos, randomSource, treeConfiguration, direction.getAxis());
         this.placeLog(level, pos.below(), randomSource, treeConfiguration, Axis.Y);
         this.placeLogWithVines(level, pos.below(2), randomSource, treeConfiguration, Axis.Y);
         pos.move(direction);
         this.placeLogWithVines(level, pos, randomSource, treeConfiguration, direction.getAxis());
         this.placeLog(level, pos.above(), randomSource, treeConfiguration, Axis.Y);
         pos.move(direction);
         pos.move(Direction.UP);
         this.placeLogWithVines(level, pos, randomSource, treeConfiguration, Axis.Y);
         pos.move(Direction.UP);
         this.placeLog(level, pos, randomSource, treeConfiguration, Axis.Y);
         this.placeLeavesBlob(level, pos, randomSource, treeConfiguration);
      } else if (type == 6) {
         pos.move(direction);
         this.placeLog(level, pos, randomSource, treeConfiguration, direction.getAxis());
         this.placeLogWithVines(level, pos.below(2), randomSource, treeConfiguration, direction.getAxis());
         pos.move(direction);
         this.placeLog(level, pos, randomSource, treeConfiguration, Axis.Y);
         this.placeLogWithVines(level, pos.below(), randomSource, treeConfiguration, Axis.Y);
         pos.move(direction);
         pos.move(Direction.UP);
         this.placeLogWithVines(level, pos, randomSource, treeConfiguration, direction.getAxis());
         pos.move(direction);
         pos.move(Direction.UP);
         this.placeLogWithVines(level, pos, randomSource, treeConfiguration, Axis.Y);
         this.placeLeavesBlob(level, pos, randomSource, treeConfiguration);
      } else if (type == 7) {
         pos.move(direction);
         this.placeLog(level, pos, randomSource, treeConfiguration, direction.getAxis());
         this.placeLogWithVines(level, pos.below(3), randomSource, treeConfiguration, direction.getAxis());
         pos.move(direction);
         this.placeLog(level, pos, randomSource, treeConfiguration, Axis.Y);
         this.placeLog(level, pos.below(), randomSource, treeConfiguration, Axis.Y);
         this.placeLogWithVines(level, pos.below(2), randomSource, treeConfiguration, Axis.Y);
         pos.move(direction);
         pos.move(Direction.UP);
         this.placeLogWithVines(level, pos, randomSource, treeConfiguration, direction.getAxis());
         pos.move(direction);
         this.placeLogWithVines(level, pos, randomSource, treeConfiguration, direction.getAxis());
         pos.move(Direction.UP);
         this.placeLogWithVines(level, pos, randomSource, treeConfiguration, Axis.Y);
         this.placeLeavesBlob(level, pos, randomSource, treeConfiguration);
      }
   }

   public boolean placeLeavesBlob(LevelAccessor level, BlockPos pos1, RandomSource randomSource, RUTreeConfiguration treeConfiguration) {
      MutableBlockPos pos = pos1.mutable();
      new Random();
      this.placeLeavesBlock(level, pos.below(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.below().north(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.below().south(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.below().east(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.below().west(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos, randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.north(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.north().east(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.north().west(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.south(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.south().east(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.south().west(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.east(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.west(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.north(2), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.north(2).east(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.north(2).west(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.south(2), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.south(2).east(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.south(2).west(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.east(2), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.east(2).north(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.east(2).south(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.west(2), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.west(2).north(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.west(2).south(), randomSource, treeConfiguration);
      this.placeLeavesBlockWithVines(level, pos.north(3), randomSource, treeConfiguration);
      this.placeLeavesBlockWithVines(level, pos.north(3).east(), randomSource, treeConfiguration);
      this.placeLeavesBlockWithVines(level, pos.north(3).west(), randomSource, treeConfiguration);
      this.placeLeavesBlockWithVines(level, pos.south(3), randomSource, treeConfiguration);
      this.placeLeavesBlockWithVines(level, pos.south(3).east(), randomSource, treeConfiguration);
      this.placeLeavesBlockWithVines(level, pos.south(3).west(), randomSource, treeConfiguration);
      this.placeLeavesBlockWithVines(level, pos.east(3), randomSource, treeConfiguration);
      this.placeLeavesBlockWithVines(level, pos.east(3).north(), randomSource, treeConfiguration);
      this.placeLeavesBlockWithVines(level, pos.east(3).south(), randomSource, treeConfiguration);
      this.placeLeavesBlockWithVines(level, pos.west(3), randomSource, treeConfiguration);
      this.placeLeavesBlockWithVines(level, pos.west(3).north(), randomSource, treeConfiguration);
      this.placeLeavesBlockWithVines(level, pos.west(3).south(), randomSource, treeConfiguration);
      this.placeLeavesBlockWithVines(level, pos.north(2).east(2), randomSource, treeConfiguration);
      this.placeLeavesBlockWithVines(level, pos.north(2).west(2), randomSource, treeConfiguration);
      this.placeLeavesBlockWithVines(level, pos.south(2).east(2), randomSource, treeConfiguration);
      this.placeLeavesBlockWithVines(level, pos.south(2).west(2), randomSource, treeConfiguration);
      pos.move(Direction.UP);
      this.placeLeavesBlock(level, pos, randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.north(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.north().east(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.north().west(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.south(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.south().east(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.south().west(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.east(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.west(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.north(2), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.north(2).east(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.north(2).west(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.south(2), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.south(2).east(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.south(2).west(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.east(2), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.east(2).north(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.east(2).south(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.west(2), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.west(2).north(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.west(2).south(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.north(3), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.south(3), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.east(3), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.west(3), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.north(2).east(2), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.north(2).west(2), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.south(2).east(2), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.south(2).west(2), randomSource, treeConfiguration);
      pos.move(Direction.UP);
      this.placeLeavesBlock(level, pos, randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.north(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.north().east(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.north().west(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.south(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.south().east(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.south().west(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.east(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.west(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.north(2), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.north(2).east(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.north(2).west(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.south(2), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.south(2).east(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.south(2).west(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.east(2), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.east(2).north(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.east(2).south(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.west(2), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.west(2).north(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.west(2).south(), randomSource, treeConfiguration);
      pos.move(Direction.UP);
      this.placeLeavesBlock(level, pos, randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.north(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.south(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.east(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.west(), randomSource, treeConfiguration);
      return true;
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

   public boolean placeLogWithVines(LevelAccessor level, BlockPos pos, RandomSource randomSource, RUTreeConfiguration treeConfiguration, Axis axis) {
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
         if (randomSource.nextInt(3) != 0) {
            placeVine(level, pos);
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

   private static void placeVine(LevelAccessor level, BlockPos pos) {
      Random random = new Random();
      int size = random.nextInt(10);
      BlockPos place = pos.below();

      for (int i = 0; i <= size; i++) {
         if (!level.getBlockState(place).isAir()) {
            if (i != 0) {
               level.setBlock(place.above(), (BlockState)RUBlocks.KAPOK_VINES.get().defaultBlockState().setValue(SpanishMossBlock.AGE, 25), 2);
            }
            break;
         }

         if (i == size) {
            level.setBlock(place, (BlockState)RUBlocks.KAPOK_VINES.get().defaultBlockState().setValue(SpanishMossBlock.AGE, 25), 2);
         } else {
            level.setBlock(place, RUBlocks.KAPOK_VINES_PLANT.get().defaultBlockState(), 2);
         }

         place = place.below();
      }
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

   public boolean placeLeavesBlockWithVines(LevelAccessor level, BlockPos pos, RandomSource randomSource, RUTreeConfiguration treeConfiguration) {
      new Random();
      if (level.isOutsideBuildHeight(pos)) {
         return true;
      }

      if (level.getBlockState(pos).canBeReplaced()) {
         level.setBlock(pos, (BlockState)treeConfiguration.foliageProvider().getState(randomSource, pos).setValue(LeavesBlock.DISTANCE, 1), 2);
         if (randomSource.nextInt(3) == 0) {
            placeVine(level, pos);
         }
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
      return reader.isStateAtPosition(pos, KapokTreeFeature::isReplaceableDirtBlock);
   }

   public static boolean isReplaceableBlock(BlockState state) {
      return state.is(RUBlockTags.REPLACEABLE_BLOCKS);
   }

   public static boolean isReplaceable(LevelSimulatedReader reader, BlockPos pos) {
      return reader.isStateAtPosition(pos, KapokTreeFeature::isReplaceableBlock);
   }
}
