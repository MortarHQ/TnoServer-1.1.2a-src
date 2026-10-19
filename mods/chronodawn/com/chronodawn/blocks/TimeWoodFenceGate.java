package com.chronodawn.blocks;

import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;

public class TimeWoodFenceGate extends FenceGateBlock {
   public TimeWoodFenceGate(Properties properties) {
      super(WoodType.OAK, properties);
   }

   public static Properties createProperties() {
      return Properties.of().mapColor(MapColor.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD);
   }
}
