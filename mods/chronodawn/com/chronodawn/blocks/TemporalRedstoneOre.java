package com.chronodawn.blocks;

import net.minecraft.world.level.block.RedStoneOreBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.MapColor;

public class TemporalRedstoneOre extends RedStoneOreBlock {
   public TemporalRedstoneOre(Properties properties) {
      super(properties);
   }

   public static Properties createProperties() {
      return Properties.of()
         .mapColor(MapColor.STONE)
         .strength(3.0F, 3.0F)
         .requiresCorrectToolForDrops()
         .sound(SoundType.STONE)
         .randomTicks()
         .lightLevel(state -> state.getValue(RedStoneOreBlock.LIT) ? 9 : 0)
         .isRedstoneConductor((state, getter, pos) -> false);
   }
}
