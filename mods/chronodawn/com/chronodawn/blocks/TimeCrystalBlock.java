package com.chronodawn.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.MapColor;

public class TimeCrystalBlock extends Block {
   public TimeCrystalBlock(Properties properties) {
      super(properties);
   }

   public static Properties createProperties() {
      return Properties.of()
         .mapColor(MapColor.COLOR_LIGHT_BLUE)
         .strength(3.0F, 3.0F)
         .requiresCorrectToolForDrops()
         .sound(SoundType.GLASS)
         .lightLevel(state -> 10)
         .noOcclusion();
   }
}
