package com.chronodawn.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.MapColor;

public class TemporalBricksBlock extends Block {
   public TemporalBricksBlock(Properties properties) {
      super(properties);
   }

   public static Properties createProperties() {
      return Properties.of().mapColor(MapColor.COLOR_PURPLE).strength(2.5F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.STONE);
   }
}
