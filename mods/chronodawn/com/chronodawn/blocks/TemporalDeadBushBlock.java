package com.chronodawn.blocks;

import com.chronodawn.registry.ModBlocks;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.OffsetType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TemporalDeadBushBlock extends BushBlock {
   public static final MapCodec<TemporalDeadBushBlock> CODEC = simpleCodec(TemporalDeadBushBlock::new);
   private static final VoxelShape SHAPE = Block.box(2.0, 0.0, 2.0, 14.0, 13.0, 14.0);

   public TemporalDeadBushBlock(Properties properties) {
      super(properties);
   }

   protected MapCodec<? extends BushBlock> codec() {
      return CODEC;
   }

   public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      return SHAPE;
   }

   public static Properties createProperties() {
      return Properties.of().replaceable().noCollission().noOcclusion().instabreak().sound(SoundType.GRASS).offsetType(OffsetType.XZ);
   }

   protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
      return state.is(Blocks.SAND)
         || state.is(Blocks.RED_SAND)
         || state.is((Block)ModBlocks.TEMPORAL_SAND.get())
         || state.is((Block)ModBlocks.TEMPORAL_DIRT.get())
         || state.is((Block)ModBlocks.COARSE_TEMPORAL_DIRT.get())
         || state.is((Block)ModBlocks.PARCHED_TEMPORAL_DIRT.get())
         || state.is((Block)ModBlocks.TEMPORAL_GRAVEL.get());
   }
}
