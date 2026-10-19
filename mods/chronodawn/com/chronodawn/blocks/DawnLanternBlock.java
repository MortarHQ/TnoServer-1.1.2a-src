package com.chronodawn.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class DawnLanternBlock extends LanternBlock {
   public static final MapCodec<DawnLanternBlock> CODEC = simpleCodec(DawnLanternBlock::new);

   public DawnLanternBlock(Properties properties) {
      super(properties);
   }

   public MapCodec<LanternBlock> codec() {
      return CODEC;
   }

   public static Properties createProperties() {
      return Properties.of().instabreak().sound(SoundType.LANTERN).lightLevel(state -> 13).noOcclusion();
   }
}
