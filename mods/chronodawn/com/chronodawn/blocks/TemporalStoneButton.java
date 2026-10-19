package com.chronodawn.blocks;

import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.material.MapColor;

public class TemporalStoneButton extends ButtonBlock {
   public TemporalStoneButton(Properties properties) {
      super(BlockSetType.STONE, 20, properties);
   }

   public static Properties createProperties() {
      return Properties.of().mapColor(MapColor.STONE).noCollission().strength(0.5F).sound(SoundType.STONE);
   }
}
