package com.chronodawn.blocks;

import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class ClockstoneSlab extends SlabBlock {
   public ClockstoneSlab(Properties properties) {
      super(properties);
   }

   public static Properties createProperties() {
      return ClockstoneBlock.createProperties();
   }
}
