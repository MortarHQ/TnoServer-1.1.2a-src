package com.chronodawn.blocks;

import com.chronodawn.registry.ModBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class ClockstoneStairs extends StairBlock {
   public ClockstoneStairs(Properties properties) {
      super(((Block)ModBlocks.CLOCKSTONE_BLOCK.get()).defaultBlockState(), properties);
   }

   public static Properties createProperties() {
      return ClockstoneBlock.createProperties();
   }
}
