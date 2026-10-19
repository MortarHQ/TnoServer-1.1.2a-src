package com.chronodawn.blocks;

import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class TemporalStoneWall extends WallBlock {
   public TemporalStoneWall(Properties properties) {
      super(properties);
   }

   public static Properties createProperties() {
      return TemporalStoneBlock.createProperties();
   }
}
