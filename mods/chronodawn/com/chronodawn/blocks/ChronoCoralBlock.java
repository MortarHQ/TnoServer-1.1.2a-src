package com.chronodawn.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.SeagrassBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.PushReaction;

public class ChronoCoralBlock extends SeagrassBlock {
   public static final MapCodec<ChronoCoralBlock> CODEC = simpleCodec(ChronoCoralBlock::new);

   public ChronoCoralBlock(Properties properties) {
      super(properties);
   }

   public MapCodec<SeagrassBlock> codec() {
      return CODEC;
   }

   public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
   }

   public static Properties createProperties() {
      return Properties.of().noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY);
   }
}
