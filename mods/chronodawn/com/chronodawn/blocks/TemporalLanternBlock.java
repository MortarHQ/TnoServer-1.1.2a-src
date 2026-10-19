package com.chronodawn.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class TemporalLanternBlock extends LanternBlock {
   public static final MapCodec<TemporalLanternBlock> CODEC = simpleCodec(TemporalLanternBlock::new);

   public TemporalLanternBlock(Properties properties) {
      super(properties);
   }

   public MapCodec<LanternBlock> codec() {
      return CODEC;
   }

   public static Properties createProperties() {
      return Properties.of().instabreak().sound(SoundType.LANTERN).lightLevel(state -> 13).noOcclusion();
   }
}
