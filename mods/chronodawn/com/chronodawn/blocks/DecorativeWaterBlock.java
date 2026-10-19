package com.chronodawn.blocks;

import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.FlowingFluid;

public class DecorativeWaterBlock extends LiquidBlock {
   public DecorativeWaterBlock(FlowingFluid fluid, Properties properties) {
      super(fluid, properties);
   }
}
