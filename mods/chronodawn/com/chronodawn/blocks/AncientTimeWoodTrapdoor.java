package com.chronodawn.blocks;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.material.MapColor;

public class AncientTimeWoodTrapdoor extends TrapDoorBlock {
   public AncientTimeWoodTrapdoor(Properties properties) {
      super(BlockSetType.OAK, properties);
   }

   public static Properties createProperties() {
      return Properties.of().mapColor(MapColor.WOOD).strength(3.0F, 3.0F).sound(SoundType.WOOD).noOcclusion();
   }
}
