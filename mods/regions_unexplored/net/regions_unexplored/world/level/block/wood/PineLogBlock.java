package net.regions_unexplored.world.level.block.wood;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.regions_unexplored.registry.RUBlocks;
import net.regions_unexplored.world.level.block.state.properties.RuBlockStateProperties;

public class PineLogBlock extends Block {
   public static final EnumProperty<Axis> AXIS = BlockStateProperties.AXIS;
   public static final BooleanProperty TRANSITION_BLOCK = RuBlockStateProperties.TRANSITION_BLOCK;
   public static final BooleanProperty IS_STRIPPED = RuBlockStateProperties.IS_STRIPPED;

   public PineLogBlock(Properties properties) {
      super(properties);
      this.registerDefaultState(
         (BlockState)((BlockState)((BlockState)this.defaultBlockState().setValue(AXIS, Axis.Y)).setValue(TRANSITION_BLOCK, false)).setValue(IS_STRIPPED, false)
      );
   }

   public BlockState rotate(BlockState state, Rotation rotation) {
      switch (rotation) {
         case COUNTERCLOCKWISE_90:
         case CLOCKWISE_90:
            switch ((Axis)state.getValue(AXIS)) {
               case X:
                  return (BlockState)state.setValue(AXIS, Axis.Z);
               case Z:
                  return (BlockState)state.setValue(AXIS, Axis.X);
               default:
                  return state;
            }
         default:
            return state;
      }
   }

   public BlockState updateShape(BlockState state, Direction direction, BlockState state1, LevelAccessor level, BlockPos pos, BlockPos pos1) {
      boolean isTransition = false;
      if (!(Boolean)state.getValue(IS_STRIPPED)
         && state.getValue(AXIS) == Axis.Y
         && (
            level.getBlockState(pos.below()) == RUBlocks.PINE_WOOD_SET.getStrippedLog().defaultBlockState()
               || level.getBlockState(pos.below()) == RUBlocks.PINE_WOOD_SET.getLog().defaultBlockState().setValue(IS_STRIPPED, true)
         )) {
         isTransition = true;
      }

      return (BlockState)state.setValue(TRANSITION_BLOCK, isTransition);
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> p_55933_) {
      p_55933_.add(new Property[]{AXIS, TRANSITION_BLOCK, IS_STRIPPED});
   }

   public BlockState getStateForPlacement(BlockPlaceContext context) {
      boolean isTransition = false;
      if (!(Boolean)this.defaultBlockState().getValue(IS_STRIPPED)
         && this.defaultBlockState().getValue(AXIS) == Axis.Y
         && (
            context.getLevel().getBlockState(context.getClickedPos().below()) == RUBlocks.PINE_WOOD_SET.getStrippedLog().defaultBlockState()
               || context.getLevel().getBlockState(context.getClickedPos().below())
                  == RUBlocks.PINE_WOOD_SET.getLog().defaultBlockState().setValue(IS_STRIPPED, true)
         )) {
         isTransition = true;
      }

      return (BlockState)((BlockState)this.defaultBlockState().setValue(AXIS, context.getClickedFace().getAxis())).setValue(TRANSITION_BLOCK, isTransition);
   }
}
