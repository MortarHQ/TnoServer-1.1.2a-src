package twilightforest.block;

import java.util.List;
import java.util.Locale;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.IronBarsBlock;
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
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class WroughtIronFenceBlock extends Block implements SimpleWaterloggedBlock {
   public static final EnumProperty<WroughtIronFenceBlock.PostState> POST = EnumProperty.create("post", WroughtIronFenceBlock.PostState.class);
   public static final EnumProperty<WroughtIronFenceBlock.FenceSide> EAST_FENCE = EnumProperty.create("east_fence", WroughtIronFenceBlock.FenceSide.class);
   public static final EnumProperty<WroughtIronFenceBlock.FenceSide> NORTH_FENCE = EnumProperty.create("north_fence", WroughtIronFenceBlock.FenceSide.class);
   public static final EnumProperty<WroughtIronFenceBlock.FenceSide> SOUTH_FENCE = EnumProperty.create("south_fence", WroughtIronFenceBlock.FenceSide.class);
   public static final EnumProperty<WroughtIronFenceBlock.FenceSide> WEST_FENCE = EnumProperty.create("west_fence", WroughtIronFenceBlock.FenceSide.class);
   public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
   private static final VoxelShape FINIAL_HEAD_SHAPE = Block.box(6.5, 17.0, 6.5, 9.5, 20.0, 9.5);
   private static final VoxelShape FINIAL_NECK_SHAPE = Block.box(7.5, 16.0, 7.5, 8.5, 17.0, 8.5);
   private static final VoxelShape FINIAL_SHAPE = Shapes.or(FINIAL_HEAD_SHAPE, FINIAL_NECK_SHAPE);
   private static final VoxelShape POST_SHAPE = Block.box(7.0, 0.0, 7.0, 9.0, 16.0, 9.0);
   private static final VoxelShape NORTH_SHAPE = Block.box(7.0, 0.0, 0.0, 9.0, 16.0, 8.0);
   private static final VoxelShape SOUTH_SHAPE = Block.box(7.0, 0.0, 8.0, 9.0, 16.0, 16.0);
   private static final VoxelShape EAST_SHAPE = Block.box(8.0, 0.0, 7.0, 16.0, 16.0, 9.0);
   private static final VoxelShape WEST_SHAPE = Block.box(0.0, 0.0, 7.0, 8.0, 16.0, 9.0);

   public WroughtIronFenceBlock(Properties properties) {
      super(properties);
      this.registerDefaultState(
         (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.getStateDefinition().any())
                           .setValue(POST, WroughtIronFenceBlock.PostState.POST))
                        .setValue(EAST_FENCE, WroughtIronFenceBlock.FenceSide.NONE))
                     .setValue(NORTH_FENCE, WroughtIronFenceBlock.FenceSide.NONE))
                  .setValue(SOUTH_FENCE, WroughtIronFenceBlock.FenceSide.NONE))
               .setValue(WEST_FENCE, WroughtIronFenceBlock.FenceSide.NONE))
            .setValue(WATERLOGGED, false)
      );
   }

   private static boolean isConnected(BlockState state, Property<WroughtIronFenceBlock.FenceSide> side) {
      return state.getValue(side) != WroughtIronFenceBlock.FenceSide.NONE;
   }

   public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
      VoxelShape finalShape = Shapes.empty();
      if (state.getValue(POST) != WroughtIronFenceBlock.PostState.NONE) {
         finalShape = POST_SHAPE;
         if (state.getValue(POST) == WroughtIronFenceBlock.PostState.CAPPED) {
            finalShape = Shapes.or(finalShape, FINIAL_SHAPE);
         }
      }

      if (state.getValue(NORTH_FENCE) != WroughtIronFenceBlock.FenceSide.NONE) {
         finalShape = Shapes.or(finalShape, NORTH_SHAPE);
      }

      if (state.getValue(SOUTH_FENCE) != WroughtIronFenceBlock.FenceSide.NONE) {
         finalShape = Shapes.or(finalShape, SOUTH_SHAPE);
      }

      if (state.getValue(WEST_FENCE) != WroughtIronFenceBlock.FenceSide.NONE) {
         finalShape = Shapes.or(finalShape, WEST_SHAPE);
      }

      if (state.getValue(EAST_FENCE) != WroughtIronFenceBlock.FenceSide.NONE) {
         finalShape = Shapes.or(finalShape, EAST_SHAPE);
      }

      if (finalShape.isEmpty()) {
         finalShape = Shapes.block();
      }

      return finalShape;
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
      tooltip.add(Component.translatable("block.twilightforest.wrought_iron_fence.cap").withStyle(ChatFormatting.GRAY));
   }

   protected boolean isPathfindable(BlockState state, PathComputationType computationType) {
      return false;
   }

   public BlockState getStateForPlacement(BlockPlaceContext context) {
      LevelReader level = context.getLevel();
      BlockPos pos = context.getClickedPos();
      BlockState north = level.getBlockState(pos.north());
      BlockState east = level.getBlockState(pos.east());
      BlockState south = level.getBlockState(pos.south());
      BlockState west = level.getBlockState(pos.west());
      BlockState above = level.getBlockState(pos.above());
      boolean northFace = this.connectsTo(north, north.isFaceSturdy(level, pos.north(), Direction.SOUTH));
      boolean eastFace = this.connectsTo(east, east.isFaceSturdy(level, pos.east(), Direction.WEST));
      boolean southFace = this.connectsTo(south, south.isFaceSturdy(level, pos.south(), Direction.NORTH));
      boolean westFace = this.connectsTo(west, west.isFaceSturdy(level, pos.west(), Direction.EAST));
      FluidState fluid = context.getLevel().getFluidState(context.getClickedPos());
      BlockState state = (BlockState)this.defaultBlockState().setValue(WATERLOGGED, fluid.getType() == Fluids.WATER);
      return this.fenceShape(level, state, pos.above(), northFace, eastFace, southFace, westFace);
   }

   private boolean connectsTo(BlockState state, boolean solid) {
      return state.is(BlockTags.WALLS) || !isExceptionForConnection(state) && solid || state.getBlock() instanceof IronBarsBlock;
   }

   public BlockState updateShape(BlockState state, Direction direction, BlockState neighbor, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
      if ((Boolean)state.getValue(WATERLOGGED)) {
         level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
      }

      return direction.getAxis() == Axis.Y ? this.updateTop(level, state, pos) : this.updateSide(level, pos, state, neighborPos, neighbor, direction);
   }

   private BlockState updateSide(LevelReader level, BlockPos pos, BlockState firstState, BlockPos secondPos, BlockState secondState, Direction direction) {
      Direction opposite = direction.getOpposite();
      boolean north = direction == Direction.NORTH
         ? this.connectsTo(secondState, secondState.isFaceSturdy(level, secondPos, opposite))
         : isConnected(firstState, NORTH_FENCE);
      boolean east = direction == Direction.EAST
         ? this.connectsTo(secondState, secondState.isFaceSturdy(level, secondPos, opposite))
         : isConnected(firstState, EAST_FENCE);
      boolean south = direction == Direction.SOUTH
         ? this.connectsTo(secondState, secondState.isFaceSturdy(level, secondPos, opposite))
         : isConnected(firstState, SOUTH_FENCE);
      boolean west = direction == Direction.WEST
         ? this.connectsTo(secondState, secondState.isFaceSturdy(level, secondPos, opposite))
         : isConnected(firstState, WEST_FENCE);
      return this.fenceShape(level, firstState, pos, north, east, south, west);
   }

   private BlockState updateTop(LevelReader level, BlockState state, BlockPos pos) {
      boolean north = isConnected(state, NORTH_FENCE);
      boolean east = isConnected(state, EAST_FENCE);
      boolean south = isConnected(state, SOUTH_FENCE);
      boolean west = isConnected(state, WEST_FENCE);
      return this.fenceShape(level, state, pos, north, east, south, west);
   }

   private BlockState fenceShape(LevelReader level, BlockState state, BlockPos pos, boolean north, boolean east, boolean south, boolean west) {
      BlockState blockstate = this.updateSides(level, state, pos, north, east, south, west);
      return (BlockState)blockstate.setValue(POST, makePost(level, blockstate, pos, true));
   }

   private BlockState updateSides(LevelReader level, BlockState state, BlockPos pos, boolean north, boolean east, boolean south, boolean west) {
      return (BlockState)((BlockState)((BlockState)((BlockState)state.setValue(
                  NORTH_FENCE, north ? this.makeFenceState(Direction.NORTH, level, pos) : WroughtIronFenceBlock.FenceSide.NONE
               ))
               .setValue(EAST_FENCE, east ? this.makeFenceState(Direction.EAST, level, pos) : WroughtIronFenceBlock.FenceSide.NONE))
            .setValue(SOUTH_FENCE, south ? this.makeFenceState(Direction.SOUTH, level, pos) : WroughtIronFenceBlock.FenceSide.NONE))
         .setValue(WEST_FENCE, west ? this.makeFenceState(Direction.WEST, level, pos) : WroughtIronFenceBlock.FenceSide.NONE);
   }

   private WroughtIronFenceBlock.FenceSide makeFenceState(Direction direction, LevelReader level, BlockPos pos) {
      BlockPos up = pos.above();
      BlockState upState = level.getBlockState(up.relative(direction));
      BlockPos down = pos.below();
      BlockState downState = level.getBlockState(down.relative(direction));
      boolean above = level.getBlockState(up).is(this)
         && (upState.is(this) || this.connectsTo(upState, upState.isFaceSturdy(level, up.relative(direction), direction.getOpposite())));
      boolean below = level.getBlockState(down).is(this)
         && (downState.is(this) || this.connectsTo(downState, downState.isFaceSturdy(level, down.relative(direction), direction.getOpposite())));
      if (above && below) {
         return WroughtIronFenceBlock.FenceSide.MIDDLE;
      } else if (!above && below) {
         return WroughtIronFenceBlock.FenceSide.TOP;
      } else {
         return above ? WroughtIronFenceBlock.FenceSide.BOTTOM : WroughtIronFenceBlock.FenceSide.FULL;
      }
   }

   public static WroughtIronFenceBlock.PostState makePost(LevelReader level, BlockState state, BlockPos pos, boolean allowCap) {
      BlockState above = level.getBlockState(pos.above());
      boolean shouldAnyBePost = shouldAnyBePost(level, state, pos);
      if (allowCap && state.getValue(POST) == WroughtIronFenceBlock.PostState.CAPPED && above.canBeReplaced()) {
         return WroughtIronFenceBlock.PostState.CAPPED;
      } else if (shouldAnyBePost) {
         return WroughtIronFenceBlock.PostState.POST;
      } else {
         return shouldBePost(state) ? WroughtIronFenceBlock.PostState.POST : WroughtIronFenceBlock.PostState.NONE;
      }
   }

   private static boolean shouldAnyBePost(LevelReader level, BlockState state, BlockPos pos) {
      MutableBlockPos mutable = pos.above().mutable();

      BlockState checkState;
      do {
         checkState = level.getBlockState(mutable);
         if (!checkState.is(state.getBlock())) {
            mutable.set(pos.below());

            do {
               checkState = level.getBlockState(mutable);
               if (!checkState.is(state.getBlock())) {
                  return false;
               }

               mutable.move(Direction.DOWN);
            } while (!shouldBePost(checkState));

            return true;
         }

         mutable.move(Direction.UP);
      } while (!shouldBePost(checkState) && checkState.getValue(POST) != WroughtIronFenceBlock.PostState.CAPPED);

      return true;
   }

   private static boolean shouldBePost(BlockState state) {
      WroughtIronFenceBlock.FenceSide nSide = (WroughtIronFenceBlock.FenceSide)state.getValue(NORTH_FENCE);
      WroughtIronFenceBlock.FenceSide sSide = (WroughtIronFenceBlock.FenceSide)state.getValue(SOUTH_FENCE);
      WroughtIronFenceBlock.FenceSide eSide = (WroughtIronFenceBlock.FenceSide)state.getValue(EAST_FENCE);
      WroughtIronFenceBlock.FenceSide wSide = (WroughtIronFenceBlock.FenceSide)state.getValue(WEST_FENCE);
      boolean north = nSide == WroughtIronFenceBlock.FenceSide.NONE;
      boolean south = sSide == WroughtIronFenceBlock.FenceSide.NONE;
      boolean east = eSide == WroughtIronFenceBlock.FenceSide.NONE;
      boolean west = wSide == WroughtIronFenceBlock.FenceSide.NONE;
      return north && south && east && west || north != south || east != west || !north && !east;
   }

   public FluidState getFluidState(BlockState state) {
      return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
   }

   public boolean propagatesSkylightDown(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
      return !(Boolean)pState.getValue(WATERLOGGED);
   }

   public BlockState rotate(BlockState state, Rotation rotation) {
      return switch (rotation) {
         case CLOCKWISE_180 -> (BlockState)((BlockState)((BlockState)((BlockState)state.setValue(
                     NORTH_FENCE, (WroughtIronFenceBlock.FenceSide)state.getValue(SOUTH_FENCE)
                  ))
                  .setValue(EAST_FENCE, (WroughtIronFenceBlock.FenceSide)state.getValue(WEST_FENCE)))
               .setValue(SOUTH_FENCE, (WroughtIronFenceBlock.FenceSide)state.getValue(NORTH_FENCE)))
            .setValue(WEST_FENCE, (WroughtIronFenceBlock.FenceSide)state.getValue(EAST_FENCE));
         case COUNTERCLOCKWISE_90 -> (BlockState)((BlockState)((BlockState)((BlockState)state.setValue(
                     NORTH_FENCE, (WroughtIronFenceBlock.FenceSide)state.getValue(EAST_FENCE)
                  ))
                  .setValue(EAST_FENCE, (WroughtIronFenceBlock.FenceSide)state.getValue(SOUTH_FENCE)))
               .setValue(SOUTH_FENCE, (WroughtIronFenceBlock.FenceSide)state.getValue(WEST_FENCE)))
            .setValue(WEST_FENCE, (WroughtIronFenceBlock.FenceSide)state.getValue(NORTH_FENCE));
         case CLOCKWISE_90 -> (BlockState)((BlockState)((BlockState)((BlockState)state.setValue(
                     NORTH_FENCE, (WroughtIronFenceBlock.FenceSide)state.getValue(WEST_FENCE)
                  ))
                  .setValue(EAST_FENCE, (WroughtIronFenceBlock.FenceSide)state.getValue(NORTH_FENCE)))
               .setValue(SOUTH_FENCE, (WroughtIronFenceBlock.FenceSide)state.getValue(EAST_FENCE)))
            .setValue(WEST_FENCE, (WroughtIronFenceBlock.FenceSide)state.getValue(SOUTH_FENCE));
         default -> state;
      };
   }

   public BlockState mirror(BlockState state, Mirror mirror) {
      return switch (mirror) {
         case LEFT_RIGHT -> (BlockState)((BlockState)state.setValue(NORTH_FENCE, (WroughtIronFenceBlock.FenceSide)state.getValue(SOUTH_FENCE)))
            .setValue(SOUTH_FENCE, (WroughtIronFenceBlock.FenceSide)state.getValue(NORTH_FENCE));
         case FRONT_BACK -> (BlockState)((BlockState)state.setValue(EAST_FENCE, (WroughtIronFenceBlock.FenceSide)state.getValue(WEST_FENCE)))
            .setValue(WEST_FENCE, (WroughtIronFenceBlock.FenceSide)state.getValue(EAST_FENCE));
         default -> super.mirror(state, mirror);
      };
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{POST, NORTH_FENCE, EAST_FENCE, WEST_FENCE, SOUTH_FENCE, WATERLOGGED});
   }

   public enum FenceSide implements StringRepresentable {
      NONE,
      MIDDLE,
      BOTTOM,
      TOP,
      FULL;

      public String getSerializedName() {
         return this.name().toLowerCase(Locale.ROOT);
      }
   }

   public enum PostState implements StringRepresentable {
      NONE,
      POST,
      CAPPED;

      public String getSerializedName() {
         return this.name().toLowerCase(Locale.ROOT);
      }
   }
}
