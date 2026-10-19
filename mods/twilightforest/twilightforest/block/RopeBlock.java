package twilightforest.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SupportType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class RopeBlock extends Block implements SimpleWaterloggedBlock {
   public static final BooleanProperty X = BooleanProperty.create("x");
   public static final BooleanProperty Y = BooleanProperty.create("y");
   public static final BooleanProperty Z = BooleanProperty.create("z");
   protected static final VoxelShape X_SHAPE = Block.box(0.0, 6.5, 6.5, 16.0, 9.5, 9.5);
   protected static final VoxelShape Y_SHAPE = Block.box(6.5, 0.0, 6.5, 9.5, 16.0, 9.5);
   protected static final VoxelShape Z_SHAPE = Block.box(6.5, 6.5, 0.0, 9.5, 9.5, 16.0);
   private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

   public RopeBlock(Properties properties) {
      super(properties);
      this.registerDefaultState(
         (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.getStateDefinition().any()).setValue(WATERLOGGED, false)).setValue(Y, true))
               .setValue(X, false))
            .setValue(Z, false)
      );
   }

   public static boolean canConnectTo(BlockState state, Direction dir, LevelReader level, BlockPos pos) {
      return dir == Direction.DOWN
         ? false
         : state.getBlock() instanceof LeavesBlock
            || state.getBlock() instanceof RopeBlock && hasAxis(state, dir.getAxis())
            || state.isFaceSturdy(level, pos, dir.getOpposite(), SupportType.CENTER);
   }

   public static boolean hasAxis(BlockState state, Axis axis) {
      return switch (axis) {
         case X -> state.getValue(X);
         case Y -> state.getValue(Y);
         case Z -> state.getValue(Z);
         default -> throw new MatchException(null, null);
      };
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{WATERLOGGED, Y, X, Z});
   }

   public boolean propagatesSkylightDown(BlockState state, BlockGetter getter, BlockPos pos) {
      return true;
   }

   protected boolean isPathfindable(BlockState state, PathComputationType type) {
      return false;
   }

   public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
      VoxelShape shape = Shapes.empty();
      if ((Boolean)state.getValue(X)) {
         shape = Shapes.or(shape, X_SHAPE);
      }

      if ((Boolean)state.getValue(Y)) {
         shape = Shapes.or(shape, Y_SHAPE);
      }

      if ((Boolean)state.getValue(Z)) {
         shape = Shapes.or(shape, Z_SHAPE);
      }

      return shape;
   }

   public VoxelShape getCollisionShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
      VoxelShape shape = Shapes.empty();
      if ((Boolean)state.getValue(X) && context.isAbove(X_SHAPE, pos, false) && !context.isDescending()) {
         shape = Shapes.or(shape, X_SHAPE);
      }

      if ((Boolean)state.getValue(Z) && context.isAbove(Z_SHAPE, pos, false) && !context.isDescending()) {
         shape = Shapes.or(shape, Z_SHAPE);
      }

      return shape;
   }

   public boolean isScaffolding(BlockState state, LevelReader level, BlockPos pos, LivingEntity entity) {
      return true;
   }

   public boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
      return context.getItemInHand().is(this.asItem());
   }

   public BlockState getStateForPlacement(BlockPlaceContext context) {
      BlockPos blockpos = context.getClickedPos();
      Level level = context.getLevel();
      Direction direction = context.getClickedFace();
      return (BlockState)((BlockState)((BlockState)((BlockState)this.defaultBlockState()
                  .setValue(WATERLOGGED, level.getFluidState(blockpos).getType() == Fluids.WATER))
               .setValue(X, direction.getAxis() == Axis.X))
            .setValue(Y, direction.getAxis() == Axis.Y))
         .setValue(Z, direction.getAxis() == Axis.Z);
   }

   public void onPlace(BlockState state, Level level, BlockPos pos, BlockState otherState, boolean isMoving) {
      if (!level.isClientSide) {
         level.scheduleTick(pos, this, 1);
      }
   }

   public BlockState updateShape(BlockState state, Direction direction, BlockState otherState, LevelAccessor level, BlockPos pos, BlockPos otherPos) {
      if ((Boolean)state.getValue(WATERLOGGED)) {
         level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
      }

      if (!level.isClientSide()) {
         level.scheduleTick(pos, this, 1);
      }

      return state;
   }

   public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
      boolean flag = true;
      int drops = 0;
      BlockState newState = state;
      if ((Boolean)state.getValue(X)) {
         if (!this.checkConnection(level, pos, Direction.WEST) && !this.checkConnection(level, pos, Direction.EAST)) {
            newState = (BlockState)newState.setValue(X, false);
            drops++;
         } else {
            flag = false;
         }
      }

      if ((Boolean)state.getValue(Y)) {
         if (!this.checkConnection(level, pos, Direction.UP)) {
            newState = (BlockState)newState.setValue(Y, false);
            drops++;
         } else {
            flag = false;
         }
      }

      if ((Boolean)state.getValue(Z)) {
         if (!this.checkConnection(level, pos, Direction.NORTH) && !this.checkConnection(level, pos, Direction.SOUTH)) {
            newState = (BlockState)newState.setValue(Z, false);
            drops++;
         } else {
            flag = false;
         }
      }

      if (flag) {
         level.destroyBlock(pos, true);
      } else if (drops > 0) {
         level.setBlockAndUpdate(pos, newState);

         for (int i = 0; i < drops; i++) {
            dropResources(this.defaultBlockState(), level, pos);
         }
      }
   }

   public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
      if ((Boolean)state.getValue(X)) {
         if (canConnectTo(level.getBlockState(pos.relative(Direction.WEST)), Direction.WEST, level, pos)) {
            return true;
         }

         if (canConnectTo(level.getBlockState(pos.relative(Direction.EAST)), Direction.EAST, level, pos)) {
            return true;
         }
      }

      if ((Boolean)state.getValue(Y) && canConnectTo(level.getBlockState(pos.relative(Direction.UP)), Direction.UP, level, pos)) {
         return true;
      } else if ((Boolean)state.getValue(Z)) {
         return canConnectTo(level.getBlockState(pos.relative(Direction.NORTH)), Direction.NORTH, level, pos)
            ? true
            : canConnectTo(level.getBlockState(pos.relative(Direction.SOUTH)), Direction.SOUTH, level, pos);
      } else {
         return false;
      }
   }

   protected boolean checkConnection(LevelReader level, BlockPos pos, Direction dir) {
      MutableBlockPos mutable = pos.mutable();

      while (level.getBlockState(mutable).is(this)) {
         mutable.move(dir);
         if (!canConnectTo(level.getBlockState(mutable), dir, level, mutable)) {
            return false;
         }
      }

      return true;
   }

   public FluidState getFluidState(BlockState state) {
      return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
   }

   public BlockState rotate(BlockState state, Rotation rotation) {
      return rotation != Rotation.CLOCKWISE_90 && rotation != Rotation.COUNTERCLOCKWISE_90
         ? state
         : (BlockState)((BlockState)state.setValue(X, (Boolean)state.getValue(Z))).setValue(Z, (Boolean)state.getValue(X));
   }
}
