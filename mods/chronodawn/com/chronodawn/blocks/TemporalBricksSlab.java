package com.chronodawn.blocks;

import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class TemporalBricksSlab extends SlabBlock {
   public TemporalBricksSlab(Properties properties) {
      super(properties);
   }

   public static Properties createProperties() {
      return TemporalBricksBlock.createProperties();
   }
}
