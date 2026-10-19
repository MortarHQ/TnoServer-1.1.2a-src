package com.chronodawn.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.MapColor;

public class DarkTimeWoodPlanks extends Block {
   public DarkTimeWoodPlanks(Properties properties) {
      super(properties);
   }

   public static Properties createProperties() {
      return Properties.of().mapColor(MapColor.PODZOL).strength(2.0F, 3.0F).sound(SoundType.WOOD);
   }
}
