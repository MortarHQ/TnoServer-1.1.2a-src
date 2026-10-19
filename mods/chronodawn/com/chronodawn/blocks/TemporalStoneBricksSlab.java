package com.chronodawn.blocks;

import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class TemporalStoneBricksSlab extends SlabBlock {
   public TemporalStoneBricksSlab(Properties properties) {
      super(properties);
   }

   public static Properties createProperties() {
      return TemporalStoneBricksBlock.createProperties();
   }
}
