package com.chronodawn.blocks;

import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class MossyTemporalCobblestoneSlab extends SlabBlock {
   public MossyTemporalCobblestoneSlab(Properties properties) {
      super(properties);
   }

   public static Properties createProperties() {
      return MossyTemporalCobblestoneBlock.createProperties();
   }
}
