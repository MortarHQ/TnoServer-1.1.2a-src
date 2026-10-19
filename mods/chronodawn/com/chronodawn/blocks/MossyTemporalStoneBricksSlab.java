package com.chronodawn.blocks;

import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class MossyTemporalStoneBricksSlab extends SlabBlock {
   public MossyTemporalStoneBricksSlab(Properties properties) {
      super(properties);
   }

   public static Properties createProperties() {
      return MossyTemporalStoneBricksBlock.createProperties();
   }
}
