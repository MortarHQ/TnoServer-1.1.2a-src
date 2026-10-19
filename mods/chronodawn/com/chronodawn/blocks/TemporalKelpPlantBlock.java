package com.chronodawn.blocks;

import com.chronodawn.registry.ModBlocks;
import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.KelpPlantBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.PushReaction;

public class TemporalKelpPlantBlock extends KelpPlantBlock {
   public static final MapCodec<TemporalKelpPlantBlock> CODEC = simpleCodec(TemporalKelpPlantBlock::new);

   public TemporalKelpPlantBlock(Properties properties) {
      super(properties);
   }

   public MapCodec<KelpPlantBlock> codec() {
      return CODEC;
   }

   protected GrowingPlantHeadBlock getHeadBlock() {
      return (GrowingPlantHeadBlock)ModBlocks.TEMPORAL_KELP.get();
   }

   public static Properties createProperties() {
      return Properties.of().noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY);
   }
}
