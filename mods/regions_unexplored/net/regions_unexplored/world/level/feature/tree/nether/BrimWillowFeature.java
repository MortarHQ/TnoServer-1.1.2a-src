package net.regions_unexplored.world.level.feature.tree.nether;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.regions_unexplored.registry.RUBlocks;
import net.regions_unexplored.registry.tag.RUBlockTags;
import net.regions_unexplored.world.level.block.wood.MagmaLogBlock;

public class BrimWillowFeature extends Feature<NoneFeatureConfiguration> {
   public BrimWillowFeature(Codec<NoneFeatureConfiguration> codec) {
      super(codec);
   }

   public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
      BlockPos pos = context.origin();
      WorldGenLevel level = context.level();
      int height = context.random().nextInt(4) + 8;
      if (level.getBlockState(pos.north()).getBlock() == RUBlocks.BRIMWOOD_WOOD_SET.getLogMagma()) {
         return false;
      }

      if (level.getBlockState(pos.north().north()).getBlock() == RUBlocks.BRIMWOOD_WOOD_SET.getLogMagma()) {
         return false;
      }

      if (level.getBlockState(pos.west()).getBlock() == RUBlocks.BRIMWOOD_WOOD_SET.getLogMagma()) {
         return false;
      }

      if (level.getBlockState(pos.west().west()).getBlock() == RUBlocks.BRIMWOOD_WOOD_SET.getLogMagma()) {
         return false;
      }

      if (level.getBlockState(pos.east()).getBlock() == RUBlocks.BRIMWOOD_WOOD_SET.getLogMagma()) {
         return false;
      }

      if (level.getBlockState(pos.east().east()).getBlock() == RUBlocks.BRIMWOOD_WOOD_SET.getLogMagma()) {
         return false;
      }

      if (level.getBlockState(pos.south()).getBlock() == RUBlocks.BRIMWOOD_WOOD_SET.getLogMagma()) {
         return false;
      }

      if (level.getBlockState(pos.south().south()).getBlock() == RUBlocks.BRIMWOOD_WOOD_SET.getLogMagma()) {
         return false;
      }

      if (level.getBlockState(pos.south().west()).getBlock() == RUBlocks.BRIMWOOD_WOOD_SET.getLogMagma()) {
         return false;
      }

      if (level.getBlockState(pos.south().east()).getBlock() == RUBlocks.BRIMWOOD_WOOD_SET.getLogMagma()) {
         return false;
      }

      if (level.getBlockState(pos.north().west()).getBlock() == RUBlocks.BRIMWOOD_WOOD_SET.getLogMagma()) {
         return false;
      }

      if (level.getBlockState(pos.north().east()).getBlock() == RUBlocks.BRIMWOOD_WOOD_SET.getLogMagma()) {
         return false;
      }

      if (level.getBlockState(pos.below()).getBlock() != Blocks.SCULK
         && level.getBlockState(pos.below()).getBlock() != RUBlocks.BRIMSPROUT_NYLIUM
         && !level.getBlockState(pos.below()).is(BlockTags.NYLIUM)
         && !level.getBlockState(pos.below()).is(BlockTags.DIRT)) {
         return false;
      }

      if (!level.isEmptyBlock(pos)) {
         return false;
      }

      MutableBlockPos placePos = pos.mutable();

      for (int i = 0; i <= height; i++) {
         if (i == height - 3) {
            this.placeBranches(level, placePos);
         }

         if (i == height - 5) {
            this.placeSpine(level, placePos);
         }

         if (isReplaceable(level, placePos)) {
            if (level.isOutsideBuildHeight(placePos)) {
               break;
            }

            if (i == 2) {
               level.setBlock(
                  placePos, (BlockState)RUBlocks.BRIMWOOD_WOOD_SET.getLogMagma().defaultBlockState().setValue(MagmaLogBlock.TRANSITION_BLOCK, true), 2
               );
            } else if (i < 2) {
               level.setBlock(
                  placePos, (BlockState)RUBlocks.BRIMWOOD_WOOD_SET.getLogMagma().defaultBlockState().setValue(MagmaLogBlock.TRANSITION_BLOCK, false), 2
               );
            } else if (i > 2) {
               level.setBlock(placePos, (BlockState)RUBlocks.BRIMWOOD_WOOD_SET.getLog().defaultBlockState().setValue(RotatedPillarBlock.AXIS, Axis.Y), 2);
            }
         }

         if (i == height) {
            this.placeLeavesUpsideDown(level, placePos);
         }

         placePos.move(Direction.UP);
      }

      return true;
   }

   private void placeBranches(LevelAccessor level, BlockPos pos) {
      if (isReplaceable(level, pos.north())) {
         if (isReplaceable(level, pos.north())) {
            this.placeBrimwoodLog(level, pos.north(), Axis.Z);
         }

         if (isReplaceable(level, pos.north().above().north())) {
            this.placeBrimwoodLog(level, pos.north().above().north(), Axis.Z);
         }

         if (isReplaceable(level, pos.north().above().north().north())) {
            this.placeBrimwoodLog(level, pos.north().above().north().north(), Axis.Z);
         }

         if (isReplaceable(level, pos.north().above().north().north().north())) {
            this.placeBrimwoodLog(level, pos.north().above().north().north().north(), Axis.Z);
            this.placeLeavesUpsideDown(level, pos.north().above().north().north().north());
         }
      }

      if (isReplaceable(level, pos.south())) {
         if (isReplaceable(level, pos.south())) {
            this.placeBrimwoodLog(level, pos.south(), Axis.Z);
         }

         if (isReplaceable(level, pos.south().above().south())) {
            this.placeBrimwoodLog(level, pos.south().above().south(), Axis.Z);
         }

         if (isReplaceable(level, pos.south().above().south().south())) {
            this.placeBrimwoodLog(level, pos.south().above().south().south(), Axis.Z);
         }

         if (isReplaceable(level, pos.south().above().south().south().south())) {
            this.placeBrimwoodLog(level, pos.south().above().south().south().south(), Axis.Z);
            this.placeLeavesUpsideDown(level, pos.south().above().south().south().south());
         }
      }

      if (isReplaceable(level, pos.east())) {
         if (isReplaceable(level, pos.east())) {
            this.placeBrimwoodLog(level, pos.east(), Axis.X);
         }

         if (isReplaceable(level, pos.east().above().east())) {
            this.placeBrimwoodLog(level, pos.east().above().east(), Axis.X);
         }

         if (isReplaceable(level, pos.east().above().east().east())) {
            this.placeBrimwoodLog(level, pos.east().above().east().east(), Axis.X);
         }

         if (isReplaceable(level, pos.east().above().east().east().east())) {
            this.placeBrimwoodLog(level, pos.east().above().east().east().east(), Axis.X);
            this.placeLeavesUpsideDown(level, pos.east().above().east().east().east());
         }
      }

      if (isReplaceable(level, pos.west())) {
         if (isReplaceable(level, pos.west())) {
            this.placeBrimwoodLog(level, pos.west(), Axis.X);
         }

         if (isReplaceable(level, pos.west().above().west())) {
            this.placeBrimwoodLog(level, pos.west().above().west(), Axis.X);
         }

         if (isReplaceable(level, pos.west().above().west().west())) {
            this.placeBrimwoodLog(level, pos.west().above().west().west(), Axis.X);
         }

         if (isReplaceable(level, pos.west().above().west().west().west())) {
            this.placeBrimwoodLog(level, pos.west().above().west().west().west(), Axis.X);
            this.placeLeavesUpsideDown(level, pos.west().above().west().west().west());
         }
      }
   }

   private void placeSpine(LevelAccessor level, BlockPos pos) {
      if (isReplaceable(level, pos.north())) {
         this.placeBrimwoodLog(level, pos.north(), Axis.Z);
      }

      if (isReplaceable(level, pos.south())) {
         this.placeBrimwoodLog(level, pos.south(), Axis.Z);
      }

      if (isReplaceable(level, pos.east())) {
         this.placeBrimwoodLog(level, pos.east(), Axis.X);
      }

      if (isReplaceable(level, pos.west())) {
         this.placeBrimwoodLog(level, pos.west(), Axis.X);
      }
   }

   private void placeBrimwoodLog(LevelAccessor level, BlockPos pos, Axis axis) {
      if (!level.isOutsideBuildHeight(pos)) {
         if (isReplaceable(level, pos)) {
            level.setBlock(pos, (BlockState)RUBlocks.BRIMWOOD_WOOD_SET.getLog().defaultBlockState().setValue(RotatedPillarBlock.AXIS, axis), 2);
         }
      }
   }

   private void placeBrimwoodLeaves(LevelAccessor level, BlockPos pos) {
      if (!level.isOutsideBuildHeight(pos)) {
         if (isReplaceable(level, pos)) {
            level.setBlock(pos, (BlockState)RUBlocks.BRIMWOOD_NATURAL_SET.getLeaves().defaultBlockState().setValue(LeavesBlock.PERSISTENT, true), 2);
         }
      }
   }

   private void placeLeavesUpsideDown(LevelAccessor level, BlockPos pos) {
      if (isReplaceable(level, pos)) {
         this.placeBrimwoodLeaves(level, pos);
      }

      if (isReplaceable(level, pos.above())) {
         this.placeBrimwoodLeaves(level, pos.above());
      }

      if (isReplaceable(level, pos.above().north())) {
         this.placeBrimwoodLeaves(level, pos.above().north());
      }

      if (isReplaceable(level, pos.above().north().east())) {
         this.placeBrimwoodLeaves(level, pos.above().north().east());
      }

      if (isReplaceable(level, pos.above().north().west())) {
         this.placeBrimwoodLeaves(level, pos.above().north().west());
      }

      if (isReplaceable(level, pos.above().south())) {
         this.placeBrimwoodLeaves(level, pos.above().south());
      }

      if (isReplaceable(level, pos.above().south().east())) {
         this.placeBrimwoodLeaves(level, pos.above().south().east());
      }

      if (isReplaceable(level, pos.above().south().west())) {
         this.placeBrimwoodLeaves(level, pos.above().south().west());
      }

      if (isReplaceable(level, pos.above().east())) {
         this.placeBrimwoodLeaves(level, pos.above().east());
      }

      if (isReplaceable(level, pos.above().west())) {
         this.placeBrimwoodLeaves(level, pos.above().west());
      }

      if (isReplaceable(level, pos.north())) {
         this.placeBrimwoodLeaves(level, pos.north());
      }

      if (isReplaceable(level, pos.north().north().above())) {
         this.placeBrimwoodLeaves(level, pos.north().north().above());
         this.addVine(level, pos.north().north().above());
      }

      if (isReplaceable(level, pos.north().west())) {
         this.placeBrimwoodLeaves(level, pos.north().west());
      }

      if (isReplaceable(level, pos.north().north().west().above())) {
         this.placeBrimwoodLeaves(level, pos.north().north().west().above());
         this.addVine(level, pos.north().north().west().above());
      }

      if (isReplaceable(level, pos.north().west().west().above())) {
         this.placeBrimwoodLeaves(level, pos.north().west().west().above());
         this.addVine(level, pos.north().west().west().above());
      }

      if (isReplaceable(level, pos.west())) {
         this.placeBrimwoodLeaves(level, pos.west());
      }

      if (isReplaceable(level, pos.west().west().above())) {
         this.placeBrimwoodLeaves(level, pos.west().west().above());
         this.addVine(level, pos.west().west().above());
      }

      if (isReplaceable(level, pos.south().west())) {
         this.placeBrimwoodLeaves(level, pos.south().west());
      }

      if (isReplaceable(level, pos.south().south().west().above())) {
         this.placeBrimwoodLeaves(level, pos.south().south().west().above());
         this.addVine(level, pos.south().south().west().above());
      }

      if (isReplaceable(level, pos.south().west().west().above())) {
         this.placeBrimwoodLeaves(level, pos.south().west().west().above());
         this.addVine(level, pos.south().west().west().above());
      }

      if (isReplaceable(level, pos.south())) {
         this.placeBrimwoodLeaves(level, pos.south());
      }

      if (isReplaceable(level, pos.south().south().above())) {
         this.placeBrimwoodLeaves(level, pos.south().south().above());
         this.addVine(level, pos.south().south().above());
      }

      if (isReplaceable(level, pos.south().east())) {
         this.placeBrimwoodLeaves(level, pos.south().east());
      }

      if (isReplaceable(level, pos.south().south().east().above())) {
         this.placeBrimwoodLeaves(level, pos.south().south().east().above());
         this.addVine(level, pos.south().south().east().above());
      }

      if (isReplaceable(level, pos.south().east().east().above())) {
         this.placeBrimwoodLeaves(level, pos.south().east().east().above());
         this.addVine(level, pos.south().east().east().above());
      }

      if (isReplaceable(level, pos.east())) {
         this.placeBrimwoodLeaves(level, pos.east());
      }

      if (isReplaceable(level, pos.east().east().above())) {
         this.placeBrimwoodLeaves(level, pos.east().east().above());
         this.addVine(level, pos.east().east().above());
      }

      if (isReplaceable(level, pos.north().east())) {
         this.placeBrimwoodLeaves(level, pos.north().east());
      }

      if (isReplaceable(level, pos.north().north().east().above())) {
         this.placeBrimwoodLeaves(level, pos.north().north().east().above());
         this.addVine(level, pos.north().north().east().above());
      }

      if (isReplaceable(level, pos.north().east().east().above())) {
         this.placeBrimwoodLeaves(level, pos.north().east().east().above());
         this.addVine(level, pos.north().east().east().above());
      }
   }

   private void addVine(LevelAccessor level, BlockPos pos) {
      Random random = new Random();
      int size = random.nextInt(3);
      BlockPos place = pos.above();

      for (int i = 0; i <= size; i++) {
         if (!isAir(level, place)) {
            if (i != 0 && !level.isOutsideBuildHeight(place)) {
               level.setBlock(
                  place.below(), (BlockState)RUBlocks.BRIMWOOD_NATURAL_SET.getLeaves().defaultBlockState().setValue(LeavesBlock.PERSISTENT, true), 2
               );
            }
            break;
         }

         if (i == size) {
            if (level.isOutsideBuildHeight(place)) {
               break;
            }

            level.setBlock(place, (BlockState)RUBlocks.BRIMWOOD_NATURAL_SET.getLeaves().defaultBlockState().setValue(LeavesBlock.PERSISTENT, true), 2);
         } else {
            if (level.isOutsideBuildHeight(place)) {
               break;
            }

            level.setBlock(place, (BlockState)RUBlocks.BRIMWOOD_NATURAL_SET.getLeaves().defaultBlockState().setValue(LeavesBlock.PERSISTENT, true), 2);
         }

         place = place.above();
      }
   }

   public static boolean isAirBlock(BlockState state) {
      return state.is(Blocks.AIR) || state.is(Blocks.CAVE_AIR) || state.is(Blocks.VOID_AIR);
   }

   public static boolean isAir(LevelSimulatedReader reader, BlockPos pos) {
      return reader.isStateAtPosition(pos, BrimWillowFeature::isAirBlock);
   }

   public static boolean isReplaceableBlock(BlockState state) {
      return state.is(RUBlockTags.REPLACEABLE_BLOCKS);
   }

   public static boolean isReplaceable(LevelSimulatedReader reader, BlockPos pos) {
      return reader.isStateAtPosition(pos, BrimWillowFeature::isReplaceableBlock);
   }
}
