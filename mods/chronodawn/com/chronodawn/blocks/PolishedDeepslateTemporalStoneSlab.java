package com.chronodawn.blocks;

import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class PolishedDeepslateTemporalStoneSlab extends SlabBlock {
   public PolishedDeepslateTemporalStoneSlab(Properties properties) {
      super(properties);
   }

   public static Properties createProperties() {
      return PolishedDeepslateTemporalStoneBlock.createProperties();
   }
}
