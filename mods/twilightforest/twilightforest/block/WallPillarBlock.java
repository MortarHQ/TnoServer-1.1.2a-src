package twilightforest.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class WallPillarBlock extends ConnectableRotatedPillarBlock implements SimpleWaterloggedBlock {
   public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
   protected static final VoxelShape BASE_SHAPE = Block.box(2.0, 2.0, 2.0, 14.0, 14.0, 14.0);
   protected static final VoxelShape WEST_SHAPE = Block.box(0.0, 2.0, 2.0, 2.0, 14.0, 14.0);
   protected static final VoxelShape EAST_SHAPE = Block.box(14.0, 2.0, 2.0, 16.0, 14.0, 14.0);
   protected static final VoxelShape DOWN_SHAPE = Block.box(2.0, 0.0, 2.0, 14.0, 2.0, 14.0);
   protected static final VoxelShape UP_SHAPE = Block.box(2.0, 14.0, 2.0, 14.0, 16.0, 14.0);
   protected static final VoxelShape NORTH_SHAPE = Block.box(2.0, 2.0, 0.0, 14.0, 14.0, 2.0);
   protected static final VoxelShape SOUTH_SHAPE = Block.box(2.0, 2.0, 14.0, 14.0, 14.0, 16.0);
   private static final VoxelShape WEST_FLAT = Block.box(0.0, 0.0, 0.0, 3.0, 16.0, 16.0);
   private static final VoxelShape EAST_FLAT = Block.box(13.0, 0.0, 0.0, 16.0, 16.0, 16.0);
   private static final VoxelShape DOWN_FLAT = Block.box(0.0, 0.0, 0.0, 16.0, 3.0, 16.0);
   private static final VoxelShape UP_FLAT = Block.box(0.0, 13.0, 0.0, 16.0, 16.0, 16.0);
   private static final VoxelShape NORTH_FLAT = Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 3.0);
   private static final VoxelShape SOUTH_FLAT = Block.box(0.0, 0.0, 13.0, 16.0, 16.0, 16.0);

   public WallPillarBlock(Properties properties, double width, double height) {
      super(properties, width);
      this.registerDefaultState((BlockState)this.defaultBlockState().setValue(WATERLOGGED, false));
   }

   @Override
   public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
      VoxelShape shape = BASE_SHAPE;
      Axis axis = (Axis)state.getValue(AXIS);
      if ((Boolean)state.getValue(DOWN)) {
         shape = Shapes.or(shape, DOWN_SHAPE);
      } else if (axis.equals(Axis.Y)) {
         shape = Shapes.or(shape, DOWN_FLAT);
      }

      if ((Boolean)state.getValue(UP)) {
         shape = Shapes.or(shape, UP_SHAPE);
      } else if (axis.equals(Axis.Y)) {
         shape = Shapes.or(shape, UP_FLAT);
      }

      if ((Boolean)state.getValue(NORTH)) {
         shape = Shapes.or(shape, NORTH_SHAPE);
      } else if (axis.equals(Axis.Z)) {
         shape = Shapes.or(shape, NORTH_FLAT);
      }

      if ((Boolean)state.getValue(SOUTH)) {
         shape = Shapes.or(shape, SOUTH_SHAPE);
      } else if (axis.equals(Axis.Z)) {
         shape = Shapes.or(shape, SOUTH_FLAT);
      }

      if ((Boolean)state.getValue(WEST)) {
         shape = Shapes.or(shape, WEST_SHAPE);
      } else if (axis.equals(Axis.X)) {
         shape = Shapes.or(shape, WEST_FLAT);
      }

      if ((Boolean)state.getValue(EAST)) {
         shape = Shapes.or(shape, EAST_SHAPE);
      } else if (axis.equals(Axis.X)) {
         shape = Shapes.or(shape, EAST_FLAT);
      }

      return shape;
   }

   @Override
   public boolean canConnectTo(Axis thisAxis, Direction facing, BlockState facingState, boolean solidSide) {
      return facingState.getBlock() instanceof WallPillarBlock && (facing.getAxis() == thisAxis || facing.getAxis() == facingState.getValue(AXIS));
   }

   public VoxelShape getBlockSupportShape(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
      return super.getBlockSupportShape(pState, pLevel, pPos);
   }

   public FluidState getFluidState(BlockState state) {
      return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
   }

   @Override
   public BlockState getStateForPlacement(BlockPlaceContext context) {
      FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
      boolean flag = fluidstate.getType() == Fluids.WATER;
      return (BlockState)super.getStateForPlacement(context).setValue(WATERLOGGED, flag);
   }

   @Override
   public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor accessor, BlockPos currentPos, BlockPos facingPos) {
      if ((Boolean)state.getValue(WATERLOGGED)) {
         accessor.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(accessor));
      }

      return super.updateShape(state, facing, facingState, accessor, currentPos, facingPos);
   }

   @Override
   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      super.createBlockStateDefinition(builder.add(new Property[]{WATERLOGGED}));
   }
}
