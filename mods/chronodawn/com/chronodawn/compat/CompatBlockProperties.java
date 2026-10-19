package com.chronodawn.compat;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class CompatBlockProperties {
   public static Properties ofFullCopy(Block block) {
      return Properties.ofFullCopy(block);
   }

   private CompatBlockProperties() {
   }
}
