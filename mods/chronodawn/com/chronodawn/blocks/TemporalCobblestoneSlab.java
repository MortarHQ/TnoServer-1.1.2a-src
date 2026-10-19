package com.chronodawn.blocks;

import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class TemporalCobblestoneSlab extends SlabBlock {
   public TemporalCobblestoneSlab(Properties properties) {
      super(properties);
   }

   public static Properties createProperties() {
      return TemporalCobblestoneBlock.createProperties();
   }
}
