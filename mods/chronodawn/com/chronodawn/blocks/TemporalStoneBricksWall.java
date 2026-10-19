package com.chronodawn.blocks;

import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class TemporalStoneBricksWall extends WallBlock {
   public TemporalStoneBricksWall(Properties properties) {
      super(properties);
   }

   public static Properties createProperties() {
      return TemporalStoneBricksBlock.createProperties();
   }
}
