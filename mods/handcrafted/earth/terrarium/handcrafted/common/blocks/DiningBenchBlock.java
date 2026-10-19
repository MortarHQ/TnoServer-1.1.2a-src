package earth.terrarium.handcrafted.common.blocks;

import com.mojang.serialization.MapCodec;
import earth.terrarium.handcrafted.common.blocks.base.SittableBlock;
import earth.terrarium.handcrafted.common.blocks.base.properties.DirectionalBlockProperty;
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
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class DiningBenchBlock extends HorizontalDirectionalBlock implements SittableBlock, SimpleWaterloggedBlock {
   public static final MapCodec<DiningBenchBlock> CODEC = simpleCodec(DiningBenchBlock::new);
   public static final EnumProperty<DirectionalBlockProperty> SHAPE = EnumProperty.create("shape", DirectionalBlockProperty.class);
   public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
   public static final VoxelShape X_AXIS_SHAPE = Block.box(3.0, 0.0, 0.0, 13.0, 9.0, 16.0);
   public static final VoxelShape Z_AXIS_SHAPE = Block.box(0.0, 0.0, 3.0, 16.0, 9.0, 13.0);
   public static final AABB SEAT = new AABB(0.0, 0.0, 0.0, 1.0, 0.5, 1.0);

   public DiningBenchBlock(Properties properties) {
      super(properties);
      this.registerDefaultState(
         (BlockState)((BlockState)((BlockState)this.defaultBlockState().setValue(SHAPE, DirectionalBlockProperty.SINGLE)).setValue(FACING, Direction.NORTH))
            .setValue(WATERLOGGED, false)
      );
   }

   protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
      return CODEC;
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{SHAPE, FACING, WATERLOGGED});
   }

   protected ItemInteractionResult useItemOn(
      ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult
   ) {
      return this.sitOn(level, pos, player, null) ? ItemInteractionResult.CONSUME : ItemInteractionResult.CONSUME_PARTIAL;
   }

   @NotNull
   public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      return ((Direction)state.getValue(FACING)).getAxis() == Axis.X ? X_AXIS_SHAPE : Z_AXIS_SHAPE;
   }

   @Override
   public AABB getSeatSize(BlockState state) {
      return SEAT;
   }

   @NotNull
   public BlockState updateShape(
      BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos currentPos, BlockPos neighborPos
   ) {
      if ((Boolean)state.getValue(WATERLOGGED)) {
         level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
      }

      return direction.getAxis().isHorizontal()
         ? (BlockState)state.setValue(SHAPE, getShape(this, (Direction)state.getValue(FACING), level, currentPos))
         : super.updateShape(state, direction, neighborState, level, currentPos, neighborPos);
   }

   public BlockState getStateForPlacement(BlockPlaceContext context) {
      BlockPos pos = context.getClickedPos();
      FluidState fluidState = context.getLevel().getFluidState(pos);
      BlockState state = (BlockState)((BlockState)this.defaultBlockState().setValue(FACING, context.getHorizontalDirection()))
         .setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
      return (BlockState)state.setValue(SHAPE, getShape(this, (Direction)state.getValue(FACING), context.getLevel(), pos));
   }

   public static DirectionalBlockProperty getShape(Block block, Direction direction, BlockGetter level, BlockPos pos) {
      BlockState state1 = level.getBlockState(pos.relative(direction.getClockWise().getOpposite()));
      BlockState state2 = level.getBlockState(pos.relative(direction.getClockWise()));
      boolean check1 = state1.is(block) && state1.getValue(FACING) != direction;
      boolean check2 = state2.is(block) && state2.getValue(FACING) != direction;
      if (!state1.is(block) && !state2.is(block)) {
         return DirectionalBlockProperty.SINGLE;
      } else if (check1 && state2.is(block)) {
         return DirectionalBlockProperty.RIGHT;
      } else if (check2 && state1.is(block)) {
         return DirectionalBlockProperty.LEFT;
      } else if (check1) {
         return DirectionalBlockProperty.SINGLE;
      } else if (check2) {
         return DirectionalBlockProperty.SINGLE;
      } else if (!state1.is(block)) {
         return DirectionalBlockProperty.RIGHT;
      } else {
         return !state2.is(block) ? DirectionalBlockProperty.LEFT : DirectionalBlockProperty.MIDDLE;
      }
   }

   protected boolean isPathfindable(BlockState state, PathComputationType pathComputationType) {
      return false;
   }
}
