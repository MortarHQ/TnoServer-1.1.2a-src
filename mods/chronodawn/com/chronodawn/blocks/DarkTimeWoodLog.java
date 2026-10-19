package com.chronodawn.blocks;

import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.MapColor;

public class DarkTimeWoodLog extends RotatedPillarBlock {
   public DarkTimeWoodLog(Properties properties) {
      super(properties);
   }

   public static Properties createProperties() {
      return Properties.of().mapColor(MapColor.PODZOL).strength(2.0F, 2.0F).sound(SoundType.WOOD);
   }
}
