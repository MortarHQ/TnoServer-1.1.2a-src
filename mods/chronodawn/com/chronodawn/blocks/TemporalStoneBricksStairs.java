package com.chronodawn.blocks;

import com.chronodawn.registry.ModBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class TemporalStoneBricksStairs extends StairBlock {
   public TemporalStoneBricksStairs(Properties properties) {
      super(((Block)ModBlocks.TEMPORAL_STONE_BRICKS.get()).defaultBlockState(), properties);
   }

   public static Properties createProperties() {
      return TemporalStoneBricksBlock.createProperties();
   }
}
