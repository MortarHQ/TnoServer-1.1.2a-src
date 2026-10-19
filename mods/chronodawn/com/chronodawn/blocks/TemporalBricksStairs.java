package com.chronodawn.blocks;

import com.chronodawn.registry.ModBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class TemporalBricksStairs extends StairBlock {
   public TemporalBricksStairs(Properties properties) {
      super(((Block)ModBlocks.TEMPORAL_BRICKS.get()).defaultBlockState(), properties);
   }

   public static Properties createProperties() {
      return TemporalBricksBlock.createProperties();
   }
}
