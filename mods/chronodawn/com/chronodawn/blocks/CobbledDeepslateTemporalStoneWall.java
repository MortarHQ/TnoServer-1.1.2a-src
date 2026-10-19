package com.chronodawn.blocks;

import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class CobbledDeepslateTemporalStoneWall extends WallBlock {
   public CobbledDeepslateTemporalStoneWall(Properties properties) {
      super(properties);
   }

   public static Properties createProperties() {
      return CobbledDeepslateTemporalStoneBlock.createProperties();
   }
}
