package com.chronodawn.blocks;

import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.OffsetType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class DawnBellBlock extends DoublePlantBlock {
   public DawnBellBlock(Properties properties) {
      super(properties);
   }

   public static Properties createProperties() {
      return Properties.of().replaceable().noCollission().noOcclusion().instabreak().sound(SoundType.GRASS).lightLevel(state -> 4).offsetType(OffsetType.XZ);
   }
}
