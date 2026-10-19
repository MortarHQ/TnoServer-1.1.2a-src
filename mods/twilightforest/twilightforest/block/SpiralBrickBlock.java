package twilightforest.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import twilightforest.enums.Diagonals;

public class SpiralBrickBlock extends Block implements SimpleWaterloggedBlock {
   public static final EnumProperty<Diagonals> DIAGONAL = EnumProperty.create("diagonal", Diagonals.class);
   public static final EnumProperty<Axis> AXIS_FACING = EnumProperty.create("axis", Axis.class);
   private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

   public SpiralBrickBlock(Properties properties) {
      super(properties);
      this.registerDefaultState(
         (BlockState)((BlockState)((BlockState)((BlockState)this.getStateDefinition().any()).setValue(DIAGONAL, Diagonals.BOTTOM_RIGHT))
               .setValue(AXIS_FACING, Axis.X))
            .setValue(WATERLOGGED, false)
      );
   }

   private static Diagonals convertVerticalDirectionToDiagonal(Direction facing) {
      return switch (facing) {
         case SOUTH -> Diagonals.BOTTOM_LEFT;
         case EAST -> Diagonals.TOP_LEFT;
         case WEST -> Diagonals.BOTTOM_RIGHT;
         default -> Diagonals.TOP_RIGHT;
      };
   }

   private static Diagonals getHorizontalDiagonalFromPlayerPlacement(LivingEntity placer, Direction facing, boolean upper) {
      return switch (facing) {
         case SOUTH, WEST -> Diagonals.getDiagonalFromUpDownLeftRight(placer.getDirection() != facing, upper);
         case EAST, NORTH -> Diagonals.getDiagonalFromUpDownLeftRight(placer.getDirection() == facing, upper);
         default -> Diagonals.BOTTOM_RIGHT;
      };
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{AXIS_FACING, DIAGONAL, WATERLOGGED});
   }

   public BlockState getStateForPlacement(BlockPlaceContext context) {
      return context.isSecondaryUseActive()
         ? (BlockState)((BlockState)((BlockState)this.defaultBlockState().setValue(AXIS_FACING, Axis.Y))
               .setValue(DIAGONAL, convertVerticalDirectionToDiagonal(context.getHorizontalDirection())))
            .setValue(WATERLOGGED, context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER)
         : (BlockState)((BlockState)((BlockState)this.defaultBlockState().setValue(AXIS_FACING, context.getHorizontalDirection().getAxis()))
               .setValue(
                  DIAGONAL,
                  getHorizontalDiagonalFromPlayerPlacement(
                     context.getPlayer(), context.getHorizontalDirection(), context.getClickLocation().y - context.getClickedPos().getY() > 0.5
                  )
               ))
            .setValue(WATERLOGGED, context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER);
   }

   public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor accessor, BlockPos currentPos, BlockPos facingPos) {
      if ((Boolean)state.getValue(WATERLOGGED)) {
         accessor.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(accessor));
      }

      return super.updateShape(state, facing, facingState, accessor, currentPos, facingPos);
   }

   public BlockState rotate(BlockState state, Rotation rot) {
      Axis axis = (Axis)state.getValue(AXIS_FACING);
      if (rot == Rotation.CLOCKWISE_180 || axis == Axis.X && rot == Rotation.CLOCKWISE_90 || axis == Axis.Z && rot == Rotation.COUNTERCLOCKWISE_90) {
         state = (BlockState)state.setValue(DIAGONAL, Diagonals.mirror((Diagonals)state.getValue(DIAGONAL), Mirror.LEFT_RIGHT));
      }

      switch (rot) {
         case COUNTERCLOCKWISE_90:
         case CLOCKWISE_90:
            return switch ((Axis)state.getValue(AXIS_FACING)) {
               case X -> (BlockState)state.setValue(AXIS_FACING, Axis.Z);
               case Z -> (BlockState)state.setValue(AXIS_FACING, Axis.X);
               default -> state;
            };
         default:
            return state;
      }
   }

   public FluidState getFluidState(BlockState state) {
      return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
   }

   public BlockState mirror(BlockState state, Mirror mirror) {
      return (BlockState)state.setValue(DIAGONAL, Diagonals.mirrorOn((Axis)state.getValue(AXIS_FACING), (Diagonals)state.getValue(DIAGONAL), mirror));
   }
}
