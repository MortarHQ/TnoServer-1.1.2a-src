package com.chronodawn.blocks;

import com.chronodawn.compat.CompatBlockProperties;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class TemporalSandstone extends Block {
   public TemporalSandstone(Properties properties) {
      super(properties);
   }

   public static Properties createProperties() {
      return CompatBlockProperties.ofFullCopy(Blocks.SANDSTONE);
   }
}
