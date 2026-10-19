package com.chronodawn.blocks;

import com.chronodawn.registry.ModBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class PolishedDeepslateTemporalStoneStairs extends StairBlock {
   public PolishedDeepslateTemporalStoneStairs(Properties properties) {
      super(((Block)ModBlocks.POLISHED_DEEPSLATE_TEMPORAL_STONE.get()).defaultBlockState(), properties);
   }

   public static Properties createProperties() {
      return PolishedDeepslateTemporalStoneBlock.createProperties();
   }
}
