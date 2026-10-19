package com.chronodawn.blocks;

import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class AncientTimeWoodSlab extends SlabBlock {
   public AncientTimeWoodSlab(Properties properties) {
      super(properties);
   }

   public static Properties createProperties() {
      return AncientTimeWoodPlanks.createProperties();
   }
}
