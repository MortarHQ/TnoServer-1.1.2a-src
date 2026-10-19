package com.chronodawn.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class DuskLanternBlock extends LanternBlock {
   public static final MapCodec<DuskLanternBlock> CODEC = simpleCodec(DuskLanternBlock::new);

   public DuskLanternBlock(Properties properties) {
      super(properties);
   }

   public MapCodec<LanternBlock> codec() {
      return CODEC;
   }

   public static Properties createProperties() {
      return Properties.of().instabreak().sound(SoundType.LANTERN).lightLevel(state -> 13).noOcclusion();
   }
}
