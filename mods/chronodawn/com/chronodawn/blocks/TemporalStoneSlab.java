package com.chronodawn.blocks;

import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class TemporalStoneSlab extends SlabBlock {
   public TemporalStoneSlab(Properties properties) {
      super(properties);
   }

   public static Properties createProperties() {
      return TemporalStoneBlock.createProperties();
   }
}
