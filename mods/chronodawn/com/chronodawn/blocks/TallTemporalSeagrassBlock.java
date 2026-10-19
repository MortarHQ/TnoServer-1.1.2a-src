package com.chronodawn.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.TallSeagrassBlock;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.PushReaction;

public class TallTemporalSeagrassBlock extends TallSeagrassBlock {
   public static final MapCodec<TallTemporalSeagrassBlock> CODEC = simpleCodec(TallTemporalSeagrassBlock::new);

   public TallTemporalSeagrassBlock(Properties properties) {
      super(properties);
   }

   public MapCodec<TallSeagrassBlock> codec() {
      return CODEC;
   }

   public static Properties createProperties() {
      return Properties.of().noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY);
   }
}
