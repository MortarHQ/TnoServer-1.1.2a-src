package com.chronodawn.blocks;

import com.chronodawn.registry.ModBlocks;
import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.KelpBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.PushReaction;

public class TemporalKelpBlock extends KelpBlock {
   public static final MapCodec<TemporalKelpBlock> CODEC = simpleCodec(TemporalKelpBlock::new);

   public TemporalKelpBlock(Properties properties) {
      super(properties);
   }

   public MapCodec<KelpBlock> codec() {
      return CODEC;
   }

   protected Block getBodyBlock() {
      return (Block)ModBlocks.TEMPORAL_KELP_PLANT.get();
   }

   public static Properties createProperties() {
      return Properties.of().noCollission().randomTicks().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY);
   }
}
