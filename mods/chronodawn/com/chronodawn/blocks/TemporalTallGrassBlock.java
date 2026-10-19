package com.chronodawn.blocks;

import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.OffsetType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class TemporalTallGrassBlock extends DoublePlantBlock {
   public TemporalTallGrassBlock(Properties properties) {
      super(properties);
   }

   public static Properties createProperties() {
      return Properties.of().replaceable().noCollission().noOcclusion().instabreak().sound(SoundType.GRASS).offsetType(OffsetType.XZ);
   }
}
