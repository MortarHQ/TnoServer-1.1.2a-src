package com.chronodawn.blocks;

import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class MossyTemporalCobblestoneWall extends WallBlock {
   public MossyTemporalCobblestoneWall(Properties properties) {
      super(properties);
   }

   public static Properties createProperties() {
      return MossyTemporalCobblestoneBlock.createProperties();
   }
}
