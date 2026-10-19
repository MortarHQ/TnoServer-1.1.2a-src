package com.chronodawn.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.MapColor;

public class CobbledDeepslateTemporalStoneBlock extends Block {
   public CobbledDeepslateTemporalStoneBlock(Properties properties) {
      super(properties);
   }

   public static Properties createProperties() {
      return Properties.of().mapColor(MapColor.DEEPSLATE).strength(3.5F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE);
   }
}
