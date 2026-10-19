package com.github.L_Ender.cataclysm.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Property;

public class FacingBlock extends HorizontalDirectionalBlock {
   public static final MapCodec<FacingBlock> CODEC = simpleCodec(FacingBlock::new);
   public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

   public MapCodec<FacingBlock> codec() {
      return CODEC;
   }

   public FacingBlock(Properties p_49046_) {
      super(p_49046_);
      this.registerDefaultState((BlockState)this.defaultBlockState().setValue(FACING, Direction.NORTH));
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> p_49088_) {
      p_49088_.add(new Property[]{FACING});
   }

   public BlockState getStateForPlacement(BlockPlaceContext context) {
      return (BlockState)this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
   }
}
