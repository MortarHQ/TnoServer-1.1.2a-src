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
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.regions_unexplored.registry.RUBlocks;
import net.regions_unexplored.registry.tag.RUBlockTags;

public class YellowBioshroomShrubFeature extends Feature<NoneFeatureConfiguration> {
   public YellowBioshroomShrubFeature(Codec<NoneFeatureConfiguration> codec) {
      super(codec);
   }

   public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
      BlockPos pos = context.origin();
      RandomSource randomSource = context.random();
      WorldGenLevel level = context.level();
      if (!this.checkReplaceable(level, pos)) {
         return false;
      }

      this.placeShrub(level, pos, randomSource);
      return true;
   }

   public void placeShrub(LevelAccessor level, BlockPos pos, RandomSource randomSource) {
      MutableBlockPos placePos = pos.mutable();
      this.placeLogBlock(level, placePos, randomSource, Axis.Y);
      placePos.move(Direction.UP);
      this.placeLogBlock(level, placePos, randomSource, Axis.Y);
      placePos.move(Direction.UP);
      if (randomSource.nextInt(3) == 0) {
         this.placeLogBlock(level, placePos, randomSource, Axis.Y);
         placePos.move(Direction.UP);
      }

      this.placeTop(level, placePos, randomSource);
   }

   public void placeTop(LevelAccessor level, BlockPos pos, RandomSource randomSource) {
      this.placeLeavesBlock(level, pos, randomSource);
      this.placeLeavesBlock(level, pos.north(), randomSource);
      this.placeLeavesBlock(level, pos.south(), randomSource);
      this.placeLeavesBlock(level, pos.east(), randomSource);
      this.placeLeavesBlock(level, pos.west(), randomSource);
      this.placeLeavesBlock(level, pos.north().east(), randomSource);
      this.placeLeavesBlock(level, pos.north().west(), randomSource);
      this.placeLeavesBlock(level, pos.south().east(), randomSource);
      this.placeLeavesBlock(level, pos.south().west(), randomSource);
   }

   public void placeLogBlock(LevelAccessor level, BlockPos pos, RandomSource randomSource, Axis axis) {
      boolean isBase = false;
      if (level.getBlockState(pos.below()).is(BlockTags.DIRT)) {
         isBase = true;
      }

      new Random();
      if (!level.isOutsideBuildHeight(pos)) {
         if (level.getBlockState(pos).is(RUBlocks.GLISTERING_NYLIUM.get())
            || level.getBlockState(pos).is(RUBlocks.MYCOTOXIC_NYLIUM.get())
            || level.getBlockState(pos).is(RUBlocks.BRIMSPROUT_NYLIUM.get())) {
            level.setBlock(pos, Blocks.NETHERRACK.defaultBlockState(), 2);
         }

         if (level.getBlockState(pos).is(RUBlocks.COBALT_NYLIUM.get())) {
            level.setBlock(pos, Blocks.BLACKSTONE.defaultBlockState(), 2);
         } else {
            if (!isReplaceable(level, pos)) {
               return;
            }

            level.setBlock(pos, (BlockState)RUBlocks.YELLOW_BIOSHROOM_WOOD_SET.getLog().defaultBlockState().setValue(RotatedPillarBlock.AXIS, axis), 2);
         }

         if (level.getBlockState(pos.below()).is(RUBlocks.GLISTERING_NYLIUM.get())
            || level.getBlockState(pos.below()).is(RUBlocks.MYCOTOXIC_NYLIUM.get())
            || level.getBlockState(pos.below()).is(RUBlocks.BRIMSPROUT_NYLIUM.get())) {
            level.setBlock(pos.below(), Blocks.NETHERRACK.defaultBlockState(), 2);
         }

         if (level.getBlockState(pos.below()).is(RUBlocks.COBALT_NYLIUM.get())) {
            level.setBlock(pos.below(), Blocks.BLACKSTONE.defaultBlockState(), 2);
         } else if (isReplaceable(level, pos.below())) {
            level.setBlock(pos.below(), (BlockState)RUBlocks.YELLOW_BIOSHROOM_WOOD_SET.getLog().defaultBlockState().setValue(RotatedPillarBlock.AXIS, axis), 2);
         }
      }
   }

   public void placeLeavesBlock(LevelAccessor level, BlockPos pos, RandomSource randomSource) {
      new Random();
      if (!level.isOutsideBuildHeight(pos)) {
         if (level.getBlockState(pos).canBeReplaced()) {
            level.setBlock(pos, RUBlocks.YELLOW_BIOSHROOM_BLOCK.get().defaultBlockState(), 2);
         }
      }
   }

   public boolean checkReplaceable(LevelAccessor level, BlockPos pos) {
      return level.isOutsideBuildHeight(pos) ? false : isReplaceable(level, pos);
   }

   public static boolean isReplaceableDirtBlock(BlockState state) {
      return state.is(RUBlockTags.TREE_GRASS_REPLACEABLES);
   }

   public static boolean isReplaceableDirt(LevelSimulatedReader reader, BlockPos pos) {
      return reader.isStateAtPosition(pos, YellowBioshroomShrubFeature::isReplaceableDirtBlock);
   }

   public static boolean isReplaceableBlock(BlockState state) {
      return state.is(RUBlockTags.REPLACEABLE_BLOCKS);
   }

   public static boolean isReplaceable(LevelSimulatedReader reader, BlockPos pos) {
      return reader.isStateAtPosition(pos, YellowBioshroomShrubFeature::isReplaceableBlock);
   }
}
