package com.chronodawn.blocks;

import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.material.MapColor;

public class TemporalStonePressurePlate extends PressurePlateBlock {
   public TemporalStonePressurePlate(Properties properties) {
      super(BlockSetType.STONE, properties);
   }

   public static Properties createProperties() {
      return Properties.of().mapColor(MapColor.STONE).noCollission().strength(0.5F).sound(SoundType.STONE);
   }
}
