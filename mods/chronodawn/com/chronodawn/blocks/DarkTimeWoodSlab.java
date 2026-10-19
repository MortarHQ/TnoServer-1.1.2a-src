package com.chronodawn.blocks;

import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class DarkTimeWoodSlab extends SlabBlock {
   public DarkTimeWoodSlab(Properties properties) {
      super(properties);
   }

   public static Properties createProperties() {
      return DarkTimeWoodPlanks.createProperties();
   }
}
