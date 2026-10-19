package com.chronodawn.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.MapColor;

public class ClockworkBlock extends Block {
   public ClockworkBlock(Properties properties) {
      super(properties);
   }

   public static Properties createProperties() {
      return Properties.of().mapColor(MapColor.METAL).strength(3.5F, 5.0F).requiresCorrectToolForDrops().sound(SoundType.METAL);
   }
}
