package com.chronodawn.blocks;

import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.material.MapColor;

public class TimeWoodButton extends ButtonBlock {
   public TimeWoodButton(Properties properties) {
      super(BlockSetType.OAK, 30, properties);
   }

   public static Properties createProperties() {
      return Properties.of().mapColor(MapColor.WOOD).noCollission().strength(0.5F).sound(SoundType.WOOD);
   }
}
