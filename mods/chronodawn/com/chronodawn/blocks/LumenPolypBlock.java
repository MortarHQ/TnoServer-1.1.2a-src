package com.chronodawn.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.SeaPickleBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.PushReaction;

public class LumenPolypBlock extends SeaPickleBlock {
   public static final MapCodec<LumenPolypBlock> CODEC = simpleCodec(LumenPolypBlock::new);

   public LumenPolypBlock(Properties properties) {
      super(properties);
   }

   public MapCodec<SeaPickleBlock> codec() {
      return CODEC;
   }

   public static Properties createProperties() {
      return Properties.of().lightLevel(state -> {
         int pickles = (Integer)state.getValue(SeaPickleBlock.PICKLES);
         return state.getValue(SeaPickleBlock.WATERLOGGED) ? 3 + 3 * pickles : 0;
      }).sound(SoundType.SLIME_BLOCK).noOcclusion().pushReaction(PushReaction.DESTROY);
   }
}
