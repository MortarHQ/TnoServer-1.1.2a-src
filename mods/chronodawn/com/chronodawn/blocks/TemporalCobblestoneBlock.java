package com.chronodawn.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.MapColor;

public class TemporalCobblestoneBlock extends Block {
   public TemporalCobblestoneBlock(Properties properties) {
      super(properties);
   }

   public static Properties createProperties() {
      return Properties.of().mapColor(MapColor.STONE).strength(2.0F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.STONE);
   }
}
