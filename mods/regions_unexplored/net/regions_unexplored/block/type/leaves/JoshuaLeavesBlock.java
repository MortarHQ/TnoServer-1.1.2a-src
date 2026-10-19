package net.regions_unexplored.block.type.leaves;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.regions_unexplored.world.level.block.state.properties.RuBlockStateProperties;
import org.jetbrains.annotations.Nullable;

public class JoshuaLeavesBlock extends DoublePlantBlock implements SimpleWaterloggedBlock {
   public static final BooleanProperty NATURAL = RuBlockStateProperties.NATURAL;
   public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

   public JoshuaLeavesBlock(Properties properties) {
      super(properties);
      this.registerDefaultState(
         (BlockState)((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(NATURAL, false)).setValue(WATERLOGGED, false))
            .setValue(HALF, DoubleBlockHalf.LOWER)
      );
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> p_52901_) {
      p_52901_.add(new Property[]{NATURAL, WATERLOGGED, HALF});
   }

   public BlockState updateShape(
      BlockState state, Direction directionToNeighbour, BlockState neighbourState, LevelAccessor level, BlockPos pos, BlockPos neighbourPos
   ) {
      if ((Boolean)state.getValue(WATERLOGGED)) {
         level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
      }

      DoubleBlockHalf half = (DoubleBlockHalf)state.getValue(HALF);
      if (directionToNeighbour.getAxis() != Axis.Y
         || half == DoubleBlockHalf.LOWER != (directionToNeighbour == Direction.UP)
         || neighbourState.is(this) && neighbourState.getValue(HALF) != half) {
         return half == DoubleBlockHalf.LOWER && directionToNeighbour == Direction.DOWN && !state.canSurvive(level, pos)
            ? Blocks.AIR.defaultBlockState()
            : super.updateShape(state, directionToNeighbour, neighbourState, level, pos, neighbourPos);
      } else {
         return Blocks.AIR.defaultBlockState();
      }
   }

   public FluidState getFluidState(BlockState state) {
      return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
   }

   public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
      BlockPos blockpos = pos.below();
      BlockState groundState = level.getBlockState(blockpos);
      if (state.getValue(HALF) != DoubleBlockHalf.UPPER) {
         return state.getValue(NATURAL) ? groundState.is(BlockTags.LOGS) : groundState.isFaceSturdy(level, blockpos, Direction.UP);
      } else {
         return groundState.is(this) && groundState.getValue(HALF) == DoubleBlockHalf.LOWER;
      }
   }

   public boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
      return false;
   }

   @Nullable
   public BlockState getStateForPlacement(BlockPlaceContext context) {
      BlockPos blockpos = context.getClickedPos();
      FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
      Level level = context.getLevel();
      return blockpos.getY() < level.getMaxBuildHeight() - 1 && level.getBlockState(blockpos.above()).canBeReplaced(context)
         ? (BlockState)((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(NATURAL, Boolean.FALSE))
               .setValue(HALF, DoubleBlockHalf.LOWER))
            .setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER)
         : super.getStateForPlacement(context);
   }
}
