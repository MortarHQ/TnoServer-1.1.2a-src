package com.chronodawn.blocks;

import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class MossyTemporalStoneBricksWall extends WallBlock {
   public MossyTemporalStoneBricksWall(Properties properties) {
      super(properties);
   }

   public static Properties createProperties() {
      return MossyTemporalStoneBricksBlock.createProperties();
   }
}
