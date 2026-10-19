package com.chronodawn.blocks;

import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class TemporalSandstoneWall extends WallBlock {
   public TemporalSandstoneWall(Properties properties) {
      super(properties);
   }

   public static Properties createProperties() {
      return TemporalSandstone.createProperties();
   }
}
