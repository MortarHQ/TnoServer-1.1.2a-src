package com.chronodawn.blocks;

import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class TimeWoodFence extends FenceBlock {
   public TimeWoodFence(Properties properties) {
      super(properties);
   }

   public static Properties createProperties() {
      return TimeWoodPlanks.createProperties();
   }
}
