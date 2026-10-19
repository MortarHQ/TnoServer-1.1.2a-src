package com.chronodawn.blocks;

import com.chronodawn.registry.ModBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class MossyTemporalStoneBricksStairs extends StairBlock {
   public MossyTemporalStoneBricksStairs(Properties properties) {
      super(((Block)ModBlocks.MOSSY_TEMPORAL_STONE_BRICKS.get()).defaultBlockState(), properties);
   }

   public static Properties createProperties() {
      return MossyTemporalStoneBricksBlock.createProperties();
   }
}
