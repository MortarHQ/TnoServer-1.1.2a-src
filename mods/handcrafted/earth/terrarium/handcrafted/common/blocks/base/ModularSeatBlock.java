package earth.terrarium.handcrafted.common.blocks.base;

import earth.terrarium.handcrafted.common.blocks.base.properties.ModularSeatProperty;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
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
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

public abstract class ModularSeatBlock extends HorizontalDirectionalBlock implements SittableBlock, SimpleWaterloggedBlock {
   public static final EnumProperty<ModularSeatProperty> SHAPE = EnumProperty.create("shape", ModularSeatProperty.class);
   public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

   public ModularSeatBlock(Properties properties) {
      super(properties);
      this.registerDefaultState(
         (BlockState)((BlockState)((BlockState)this.defaultBlockState().setValue(SHAPE, ModularSeatProperty.SINGLE)).setValue(FACING, Direction.NORTH))
            .setValue(WATERLOGGED, false)
      );
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{SHAPE, FACING, WATERLOGGED});
   }

   @NotNull
   public BlockState mirror(BlockState state, Mirror mirror) {
      Direction direction = (Direction)state.getValue(FACING);
      ModularSeatProperty couchShape = (ModularSeatProperty)state.getValue(SHAPE);
      switch (mirror) {
         case LEFT_RIGHT:
            if (direction.getAxis() == Axis.Z) {
               ModularSeatProperty shape = switch (couchShape) {
                  case OUTER_LEFT -> ModularSeatProperty.OUTER_RIGHT;
                  case OUTER_RIGHT -> ModularSeatProperty.OUTER_LEFT;
                  case INNER_LEFT -> ModularSeatProperty.INNER_RIGHT;
                  case INNER_RIGHT -> ModularSeatProperty.INNER_LEFT;
                  default -> couchShape;
               };
               return (BlockState)state.rotate(Rotation.CLOCKWISE_180).setValue(SHAPE, shape);
            }
            break;
         case FRONT_BACK:
            if (direction.getAxis() == Axis.X) {
               ModularSeatProperty shape = switch (couchShape) {
                  case OUTER_LEFT -> ModularSeatProperty.OUTER_RIGHT;
                  case OUTER_RIGHT -> ModularSeatProperty.OUTER_LEFT;
                  case INNER_LEFT -> ModularSeatProperty.INNER_LEFT;
                  case INNER_RIGHT -> ModularSeatProperty.INNER_RIGHT;
                  default -> couchShape;
               };
               return (BlockState)state.rotate(Rotation.CLOCKWISE_180).setValue(SHAPE, shape);
            }
      }

      return state.rotate(mirror.getRotation((Direction)state.getValue(FACING)));
   }

   @NotNull
   public BlockState updateShape(
      BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos currentPos, BlockPos neighborPos
   ) {
      if ((Boolean)state.getValue(WATERLOGGED)) {
         level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
      }

      return direction.getAxis().isHorizontal()
         ? (BlockState)state.setValue(SHAPE, this.getShape(state, level, currentPos))
         : super.updateShape(state, direction, neighborState, level, currentPos, neighborPos);
   }

   protected ItemInteractionResult useItemOn(
      ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult
   ) {
      return this.sitOn(level, pos, player, (Direction)state.getValue(FACING)) ? ItemInteractionResult.CONSUME : ItemInteractionResult.CONSUME_PARTIAL;
   }

   public BlockState getStateForPlacement(BlockPlaceContext context) {
      BlockPos pos = context.getClickedPos();
      FluidState fluidState = context.getLevel().getFluidState(pos);
      BlockState state = (BlockState)((BlockState)this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite()))
         .setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
      return (BlockState)state.setValue(SHAPE, this.getShape(state, context.getLevel(), pos));
   }

   public boolean canTakeShape(BlockState state, BlockGetter level, BlockPos pos, Direction face) {
      BlockState other = level.getBlockState(pos.relative(face));
      return !other.is(this) || other.getValue(FACING) != state.getValue(FACING);
   }

   public ModularSeatProperty getShape(BlockState state, BlockGetter level, BlockPos pos) {
      Direction direction = (Direction)state.getValue(FACING);
      BlockState state1 = level.getBlockState(pos.relative(direction.getOpposite()));
      if (state1.is(this)) {
         Direction dir = (Direction)state1.getValue(FACING);
         if (dir.getAxis() != ((Direction)state.getValue(FACING)).getAxis() && this.canTakeShape(state, level, pos, dir)) {
            return dir == direction.getCounterClockWise() ? ModularSeatProperty.OUTER_LEFT : ModularSeatProperty.OUTER_RIGHT;
         }
      }

      BlockState state2 = level.getBlockState(pos.relative(direction));
      if (state2.is(this)) {
         Direction dir = (Direction)state2.getValue(FACING);
         if (dir.getAxis() != ((Direction)state.getValue(FACING)).getAxis() && this.canTakeShape(state, level, pos, dir.getOpposite())) {
            return dir == direction.getCounterClockWise() ? ModularSeatProperty.INNER_LEFT : ModularSeatProperty.INNER_RIGHT;
         }
      }

      BlockState state3 = level.getBlockState(pos.relative(direction.getClockWise().getOpposite()));
      BlockState state4 = level.getBlockState(pos.relative(direction.getClockWise()));
      if (!state3.is(this) && !state4.is(this)) {
         return ModularSeatProperty.SINGLE;
      } else if (!state3.is(this)) {
         return ModularSeatProperty.RIGHT;
      } else {
         return !state4.is(this) ? ModularSeatProperty.LEFT : ModularSeatProperty.MIDDLE;
      }
   }

   protected boolean isPathfindable(BlockState state, PathComputationType pathComputationType) {
      return false;
   }
}
