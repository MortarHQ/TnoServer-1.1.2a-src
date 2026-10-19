package com.chronodawn.blocks;

import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class TemporalSandstoneSlab extends SlabBlock {
   public TemporalSandstoneSlab(Properties properties) {
      super(properties);
   }

   public static Properties createProperties() {
      return TemporalSandstone.createProperties();
   }
}
