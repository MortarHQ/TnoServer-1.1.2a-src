package net.regions_unexplored.world.level.block.wood;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.regions_unexplored.world.level.block.state.properties.RuBlockStateProperties;

public class AspenLogBlock extends Block {
   public static final EnumProperty<Axis> AXIS = BlockStateProperties.AXIS;
   public static final BooleanProperty IS_BASE = RuBlockStateProperties.IS_BASE;

   public AspenLogBlock(Properties properties) {
      super(properties);
      this.registerDefaultState((BlockState)((BlockState)this.defaultBlockState().setValue(AXIS, Axis.Y)).setValue(IS_BASE, false));
   }

   protected BlockState updateShape(
      BlockState state, Direction directionToNeighbour, BlockState neighbourState, LevelAccessor level, BlockPos pos, BlockPos neighbourPos
   ) {
      boolean isBase = state.getValue(AXIS) == Axis.Y && level.getBlockState(pos.below()).is(BlockTags.DIRT);
      return (BlockState)super.updateShape(state, directionToNeighbour, neighbourState, level, pos, neighbourPos).setValue(IS_BASE, isBase);
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{AXIS, IS_BASE});
   }

   public BlockState getStateForPlacement(BlockPlaceContext context) {
      boolean isBase = context.getLevel().getBlockState(context.getClickedPos().below()).is(BlockTags.DIRT);
      return (BlockState)((BlockState)this.defaultBlockState().setValue(AXIS, context.getClickedFace().getAxis())).setValue(IS_BASE, isBase);
   }
}
