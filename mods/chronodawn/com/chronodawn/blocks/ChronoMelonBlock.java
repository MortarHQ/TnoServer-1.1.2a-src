package com.chronodawn.blocks;

import com.chronodawn.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.AttachedStemBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StemBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class ChronoMelonBlock extends Block {
   public ChronoMelonBlock(Properties properties) {
      super(properties);
   }

   public StemBlock getStem() {
      return (StemBlock)ModBlocks.CHRONO_MELON_STEM.get();
   }

   public AttachedStemBlock getAttachedStem() {
      return (AttachedStemBlock)ModBlocks.ATTACHED_CHRONO_MELON_STEM.get();
   }

   public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
      BlockPos belowPos = pos.below();
      BlockState belowState = level.getBlockState(belowPos);
      if (!belowState.is(Blocks.GRASS_BLOCK)
         && !belowState.is(Blocks.DIRT)
         && !belowState.is(Blocks.PODZOL)
         && !belowState.is(Blocks.COARSE_DIRT)
         && !belowState.is((Block)ModBlocks.TEMPORAL_GRASS_BLOCK.get())
         && !belowState.is((Block)ModBlocks.TEMPORAL_DIRT.get())
         && !belowState.is((Block)ModBlocks.COARSE_TEMPORAL_DIRT.get())) {
         return false;
      }

      if (belowState.is(BlockTags.LOGS)) {
         return false;
      }

      BlockPos abovePos = pos.above();
      return !level.getBlockState(abovePos).is(BlockTags.LOGS);
   }
}
