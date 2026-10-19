package com.github.L_Ender.cataclysm.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Property;

public class FacingPillarBlock extends Block {
   public static final BooleanProperty TOP = BooleanProperty.create("top");
   public static final DirectionProperty FACING = BlockStateProperties.FACING;

   public FacingPillarBlock(Properties p_49046_) {
      super(p_49046_);
      this.registerDefaultState((BlockState)((BlockState)this.defaultBlockState().setValue(TOP, true)).setValue(FACING, Direction.UP));
   }

   public BlockState updateShape(BlockState state, Direction direction, BlockState state1, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos blockPos1) {
      BlockState pillar = super.updateShape(state, direction, state1, levelAccessor, blockPos, blockPos1);
      if (levelAccessor.getBlockState(blockPos.relative((Direction)state.getValue(FACING))).getBlock() instanceof FacingPillarBlock) {
         pillar = (BlockState)pillar.setValue(TOP, false);
      } else {
         pillar = (BlockState)pillar.setValue(TOP, true);
      }

      return pillar;
   }

   public BlockState rotate(BlockState p_49085_, Rotation p_49086_) {
      return (BlockState)p_49085_.setValue(FACING, p_49086_.rotate((Direction)p_49085_.getValue(FACING)));
   }

   public BlockState mirror(BlockState p_49082_, Mirror p_49083_) {
      return p_49082_.rotate(p_49083_.getRotation((Direction)p_49082_.getValue(FACING)));
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> p_49088_) {
      p_49088_.add(new Property[]{TOP, FACING});
   }

   public BlockState getStateForPlacement(BlockPlaceContext context) {
      LevelAccessor levelaccessor = context.getLevel();
      BlockPos blockpos = context.getClickedPos();
      BlockState above = levelaccessor.getBlockState(blockpos.relative(context.getNearestLookingDirection().getOpposite()));
      return (BlockState)((BlockState)this.defaultBlockState().setValue(TOP, !(above.getBlock() instanceof FacingPillarBlock)))
         .setValue(FACING, context.getNearestLookingDirection().getOpposite());
   }
}
