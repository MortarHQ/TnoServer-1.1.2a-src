package com.chronodawn.blocks;

import com.chronodawn.registry.ModBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class DarkTimeWoodStairs extends StairBlock {
   public DarkTimeWoodStairs(Properties properties) {
      super(((Block)ModBlocks.DARK_TIME_WOOD_PLANKS.get()).defaultBlockState(), properties);
   }

   public static Properties createProperties() {
      return DarkTimeWoodPlanks.createProperties();
   }
}
