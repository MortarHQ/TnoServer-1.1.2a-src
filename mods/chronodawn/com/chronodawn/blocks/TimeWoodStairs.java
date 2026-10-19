package com.chronodawn.blocks;

import com.chronodawn.registry.ModBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class TimeWoodStairs extends StairBlock {
   public TimeWoodStairs(Properties properties) {
      super(((Block)ModBlocks.TIME_WOOD_PLANKS.get()).defaultBlockState(), properties);
   }

   public static Properties createProperties() {
      return TimeWoodPlanks.createProperties();
   }
}
