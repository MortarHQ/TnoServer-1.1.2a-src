package net.regions_unexplored.world.level.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.material.Fluids;
import net.regions_unexplored.registry.RUBlocks;
import net.regions_unexplored.world.level.block.plant.aquatic.GiantLilyBlock;

public class GiantLilyPadFeature extends Feature<NoneFeatureConfiguration> {
   public GiantLilyPadFeature(Codec<NoneFeatureConfiguration> codec) {
      super(codec);
   }

   public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
      BlockPos pos = context.origin();
      WorldGenLevel level = context.level();
      if (level.getFluidState(pos.below()).is(Fluids.WATER)
         && level.getBlockState(pos).isAir()
         && level.getFluidState(pos.below().south()).is(Fluids.WATER)
         && level.getBlockState(pos.south()).isAir()
         && level.getFluidState(pos.below().south().west()).is(Fluids.WATER)
         && level.getBlockState(pos.south().west()).isAir()
         && level.getFluidState(pos.below().west()).is(Fluids.WATER)
         && level.getBlockState(pos.west()).isAir()) {
         level.setBlock(pos, (BlockState)RUBlocks.GIANT_LILY_PAD.get().defaultBlockState().setValue(GiantLilyBlock.FACING, Direction.NORTH), 2);
         level.setBlock(pos.south(), (BlockState)RUBlocks.GIANT_LILY_PAD.get().defaultBlockState().setValue(GiantLilyBlock.FACING, Direction.EAST), 2);
         level.setBlock(pos.south().west(), (BlockState)RUBlocks.GIANT_LILY_PAD.get().defaultBlockState().setValue(GiantLilyBlock.FACING, Direction.SOUTH), 2);
         level.setBlock(pos.west(), (BlockState)RUBlocks.GIANT_LILY_PAD.get().defaultBlockState().setValue(GiantLilyBlock.FACING, Direction.WEST), 2);
      }

      return false;
   }
}
