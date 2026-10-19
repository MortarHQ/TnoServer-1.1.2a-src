package com.chronodawn.blocks;

import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class ClockstoneWall extends WallBlock {
   public ClockstoneWall(Properties properties) {
      super(properties);
   }

   public static Properties createProperties() {
      return ClockstoneBlock.createProperties();
   }
}
