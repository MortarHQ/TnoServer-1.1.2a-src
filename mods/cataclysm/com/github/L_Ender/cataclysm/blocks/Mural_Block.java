package com.github.L_Ender.cataclysm.blocks;

import com.mojang.serialization.MapCodec;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FaceAttachedHorizontalDirectionalBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class Mural_Block extends FaceAttachedHorizontalDirectionalBlock implements SimpleWaterloggedBlock {
   public static final MapCodec<Mural_Block> CODEC = simpleCodec(Mural_Block::new);
   public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
   protected static final VoxelShape CEILING_AABB = Block.box(0.0, 8.0, 0.0, 16.0, 16.0, 16.0);
   protected static final VoxelShape FLOOR_AABB = Block.box(0.0, 0.0, 0.0, 16.0, 8.0, 16.0);
   protected static final VoxelShape NORTH_AABB = Block.box(0.0, 0.0, 8.0, 16.0, 16.0, 16.0);
   protected static final VoxelShape SOUTH_AABB = Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 8.0);
   protected static final VoxelShape WEST_AABB = Block.box(8.0, 0.0, 0.0, 16.0, 16.0, 16.0);
   protected static final VoxelShape EAST_AABB = Block.box(0.0, 0.0, 0.0, 8.0, 16.0, 16.0);

   public MapCodec<Mural_Block> codec() {
      return CODEC;
   }

   public Mural_Block(Properties properties) {
      super(properties);
      this.registerDefaultState(
         (BlockState)((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(FACING, Direction.NORTH)).setValue(FACE, AttachFace.WALL))
            .setValue(WATERLOGGED, Boolean.FALSE)
      );
   }

   protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
      return true;
   }

   protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      Direction direction = (Direction)state.getValue(FACING);
      switch ((AttachFace)state.getValue(FACE)) {
         case FLOOR:
            return FLOOR_AABB;
         case WALL:
            return switch (direction) {
               case EAST -> EAST_AABB;
               case WEST -> WEST_AABB;
               case SOUTH -> SOUTH_AABB;
               case NORTH, UP, DOWN -> NORTH_AABB;
               default -> throw new MatchException((String)null, (Throwable)null);
            };
         case CEILING:
         default:
            return CEILING_AABB;
      }
   }

   protected boolean isSignalSource(BlockState state) {
      return true;
   }

   @Nullable
   public BlockState getStateForPlacement(BlockPlaceContext context) {
      FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());

      for (Direction direction : context.getNearestLookingDirections()) {
         BlockState blockstate;
         if (direction.getAxis() == Axis.Y) {
            blockstate = (BlockState)((BlockState)((BlockState)this.defaultBlockState()
                     .setValue(FACE, direction == Direction.UP ? AttachFace.CEILING : AttachFace.FLOOR))
                  .setValue(FACING, context.getHorizontalDirection()))
               .setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
         } else {
            blockstate = (BlockState)((BlockState)((BlockState)this.defaultBlockState().setValue(FACE, AttachFace.WALL))
                  .setValue(FACING, direction.getOpposite()))
               .setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
         }

         if (blockstate.canSurvive(context.getLevel(), context.getClickedPos())) {
            return blockstate;
         }
      }

      return null;
   }

   public FluidState getFluidState(BlockState p_51581_) {
      return p_51581_.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(p_51581_);
   }

   public BlockState updateShape(BlockState p_51555_, Direction p_51556_, BlockState p_51557_, LevelAccessor p_51558_, BlockPos p_51559_, BlockPos p_51560_) {
      if ((Boolean)p_51555_.getValue(WATERLOGGED)) {
         p_51558_.scheduleTick(p_51559_, Fluids.WATER, Fluids.WATER.getTickDelay(p_51558_));
      }

      return super.updateShape(p_51555_, p_51556_, p_51557_, p_51558_, p_51559_, p_51560_);
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{FACING, FACE, WATERLOGGED});
   }
}
