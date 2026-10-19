package com.chronodawn.blocks;

import com.chronodawn.registry.ModBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class TemporalStoneStairs extends StairBlock {
   public TemporalStoneStairs(Properties properties) {
      super(((Block)ModBlocks.TEMPORAL_STONE.get()).defaultBlockState(), properties);
   }

   public static Properties createProperties() {
      return TemporalStoneBlock.createProperties();
   }
}
