package com.chronodawn.blocks;

import com.chronodawn.registry.ModBlocks;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.SeagrassBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.PushReaction;

public class TemporalSeagrassBlock extends SeagrassBlock {
   public static final MapCodec<TemporalSeagrassBlock> CODEC = simpleCodec(TemporalSeagrassBlock::new);

   public TemporalSeagrassBlock(Properties properties) {
      super(properties);
   }

   public MapCodec<SeagrassBlock> codec() {
      return CODEC;
   }

   public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
      BlockState aboveState = level.getBlockState(pos.above());
      if (aboveState.is(Blocks.WATER) && aboveState.getFluidState().getAmount() == 8) {
         DoublePlantBlock.placeAt(level, ((Block)ModBlocks.TALL_TEMPORAL_SEAGRASS.get()).defaultBlockState(), pos, 2);
      }
   }

   public static Properties createProperties() {
      return Properties.of().noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY);
   }
}
