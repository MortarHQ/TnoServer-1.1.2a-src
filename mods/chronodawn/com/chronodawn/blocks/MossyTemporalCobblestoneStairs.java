package com.chronodawn.blocks;

import com.chronodawn.registry.ModBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class MossyTemporalCobblestoneStairs extends StairBlock {
   public MossyTemporalCobblestoneStairs(Properties properties) {
      super(((Block)ModBlocks.MOSSY_TEMPORAL_COBBLESTONE.get()).defaultBlockState(), properties);
   }

   public static Properties createProperties() {
      return MossyTemporalCobblestoneBlock.createProperties();
   }
}
