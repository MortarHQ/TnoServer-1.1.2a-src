package earth.terrarium.handcrafted.common.blocks;

import earth.terrarium.handcrafted.common.blocks.base.properties.OptionalColorProperty;
import earth.terrarium.handcrafted.common.blocks.base.properties.TableProperty;
import earth.terrarium.handcrafted.common.constants.ConstantComponents;
import earth.terrarium.handcrafted.common.tags.ModBlockTags;
import earth.terrarium.handcrafted.common.utils.InteractionUtils;
import earth.terrarium.handcrafted.common.utils.TooltipUtils;
import java.util.List;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
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
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class TableBlock extends Block implements SimpleWaterloggedBlock {
   public static final EnumProperty<TableProperty> SHAPE = EnumProperty.create("shape", TableProperty.class);
   public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
   public static final EnumProperty<OptionalColorProperty> COLOR = EnumProperty.create("color", OptionalColorProperty.class);
   public static final VoxelShape SINGLE_SHAPE = Stream.of(
         Block.box(0.0, 12.0, 0.0, 16.0, 16.0, 16.0),
         Block.box(12.0, 0.0, 12.0, 15.0, 12.0, 15.0),
         Block.box(12.0, 0.0, 1.0, 15.0, 12.0, 4.0),
         Block.box(1.0, 0.0, 1.0, 4.0, 12.0, 4.0),
         Block.box(1.0, 0.0, 12.0, 4.0, 12.0, 15.0)
      )
      .reduce(Shapes::or)
      .get();
   public static final VoxelShape CENTER_SHAPE = Block.box(0.0, 12.0, 0.0, 16.0, 16.0, 16.0);
   public static final VoxelShape NORTH_EAST_CORNER_SHAPE = Shapes.or(Block.box(0.0, 12.0, 0.0, 16.0, 16.0, 16.0), Block.box(12.0, 0.0, 1.0, 15.0, 12.0, 4.0));
   public static final VoxelShape NORTH_WEST_CORNER_SHAPE = Shapes.or(Block.box(0.0, 12.0, 0.0, 16.0, 16.0, 16.0), Block.box(1.0, 0.0, 1.0, 4.0, 12.0, 4.0));
   public static final VoxelShape SOUTH_EAST_CORNER_SHAPE = Shapes.or(Block.box(0.0, 12.0, 0.0, 16.0, 16.0, 16.0), Block.box(12.0, 0.0, 12.0, 15.0, 12.0, 15.0));
   public static final VoxelShape SOUTH_WEST_CORNER_SHAPE = Shapes.or(Block.box(0.0, 12.0, 0.0, 16.0, 16.0, 16.0), Block.box(1.0, 0.0, 12.0, 4.0, 12.0, 15.0));
   public static final VoxelShape NORTH_SIDE_SHAPE = Stream.of(
         Block.box(0.0, 12.0, 0.0, 16.0, 16.0, 16.0), Block.box(12.0, 0.0, 12.0, 15.0, 12.0, 15.0), Block.box(1.0, 0.0, 12.0, 4.0, 12.0, 15.0)
      )
      .reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR))
      .get();
   public static final VoxelShape EAST_SIDE_SHAPE = Stream.of(
         Block.box(0.0, 12.0, 0.0, 16.0, 16.0, 16.0), Block.box(1.0, 0.0, 1.0, 4.0, 12.0, 4.0), Block.box(1.0, 0.0, 12.0, 4.0, 12.0, 15.0)
      )
      .reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR))
      .get();
   public static final VoxelShape SOUTH_SIDE_SHAPE = Stream.of(
         Block.box(0.0, 12.0, 0.0, 16.0, 16.0, 16.0), Block.box(12.0, 0.0, 1.0, 15.0, 12.0, 4.0), Block.box(1.0, 0.0, 1.0, 4.0, 12.0, 4.0)
      )
      .reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR))
      .get();
   public static final VoxelShape WEST_SIDE_SHAPE = Stream.of(
         Block.box(0.0, 12.0, 0.0, 16.0, 16.0, 16.0), Block.box(12.0, 0.0, 12.0, 15.0, 12.0, 15.0), Block.box(12.0, 0.0, 1.0, 15.0, 12.0, 4.0)
      )
      .reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR))
      .get();

   public TableBlock(Properties properties) {
      super(properties);
      this.registerDefaultState(
         (BlockState)((BlockState)((BlockState)this.defaultBlockState().setValue(SHAPE, TableProperty.SINGLE)).setValue(WATERLOGGED, false))
            .setValue(COLOR, OptionalColorProperty.NONE)
      );
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{SHAPE, WATERLOGGED, COLOR});
   }

   public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean moved) {
      if (!level.isClientSide() && state.getBlock() != newState.getBlock()) {
         Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), ((OptionalColorProperty)state.getValue(COLOR)).toSheet());
      }

      super.onRemove(state, level, pos, newState, moved);
   }

   protected ItemInteractionResult useItemOn(
      ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult
   ) {
      return InteractionUtils.interactOptionalSheet(state, level, pos, player, stack, COLOR);
   }

   @NotNull
   public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      return switch ((TableProperty)state.getValue(SHAPE)) {
         case SINGLE -> SINGLE_SHAPE;
         case NORTH_EAST_CORNER -> NORTH_EAST_CORNER_SHAPE;
         case NORTH_WEST_CORNER -> NORTH_WEST_CORNER_SHAPE;
         case SOUTH_EAST_CORNER -> SOUTH_EAST_CORNER_SHAPE;
         case SOUTH_WEST_CORNER -> SOUTH_WEST_CORNER_SHAPE;
         case NORTH_SIDE -> NORTH_SIDE_SHAPE;
         case EAST_SIDE -> EAST_SIDE_SHAPE;
         case SOUTH_SIDE -> SOUTH_SIDE_SHAPE;
         case WEST_SIDE -> WEST_SIDE_SHAPE;
         default -> CENTER_SHAPE;
      };
   }

   @NotNull
   public FluidState getFluidState(BlockState state) {
      return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
   }

   @NotNull
   public BlockState updateShape(
      BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos currentPos, BlockPos neighborPos
   ) {
      if ((Boolean)state.getValue(WATERLOGGED)) {
         level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
      }

      return direction.getAxis().isHorizontal()
         ? (BlockState)state.setValue(SHAPE, this.getShape(level, currentPos))
         : super.updateShape(state, direction, neighborState, level, currentPos, neighborPos);
   }

   public BlockState getStateForPlacement(BlockPlaceContext context) {
      BlockPos pos = context.getClickedPos();
      FluidState fluidState = context.getLevel().getFluidState(pos);
      BlockState state = (BlockState)this.defaultBlockState().setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
      return (BlockState)state.setValue(SHAPE, this.getShape(context.getLevel(), pos));
   }

   public TableProperty getShape(BlockGetter level, BlockPos pos) {
      boolean northPresent = level.getBlockState(pos.relative(Direction.NORTH)).is(ModBlockTags.TABLE_CONNECTABLE);
      boolean eastPresent = level.getBlockState(pos.relative(Direction.EAST)).is(ModBlockTags.TABLE_CONNECTABLE);
      boolean southPresent = level.getBlockState(pos.relative(Direction.SOUTH)).is(ModBlockTags.TABLE_CONNECTABLE);
      boolean westPresent = level.getBlockState(pos.relative(Direction.WEST)).is(ModBlockTags.TABLE_CONNECTABLE);
      if (northPresent && eastPresent && southPresent && westPresent) {
         return TableProperty.CENTER;
      } else if (northPresent && eastPresent && southPresent) {
         return TableProperty.WEST_CENTER;
      } else if (northPresent && eastPresent && westPresent) {
         return TableProperty.SOUTH_CENTER;
      } else if (northPresent && southPresent && westPresent) {
         return TableProperty.EAST_CENTER;
      } else if (eastPresent && southPresent && westPresent) {
         return TableProperty.NORTH_CENTER;
      } else if (northPresent && southPresent) {
         return TableProperty.EAST_WEST_CENTER;
      } else if (eastPresent && westPresent) {
         return TableProperty.NORTH_SOUTH_CENTER;
      } else if (northPresent && eastPresent) {
         return TableProperty.SOUTH_WEST_CORNER;
      } else if (eastPresent && southPresent) {
         return TableProperty.NORTH_WEST_CORNER;
      } else if (southPresent && westPresent) {
         return TableProperty.NORTH_EAST_CORNER;
      } else if (westPresent && northPresent) {
         return TableProperty.SOUTH_EAST_CORNER;
      } else if (northPresent) {
         return TableProperty.NORTH_SIDE;
      } else if (eastPresent) {
         return TableProperty.EAST_SIDE;
      } else if (southPresent) {
         return TableProperty.SOUTH_SIDE;
      } else {
         return westPresent ? TableProperty.WEST_SIDE : TableProperty.SINGLE;
      }
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
      TooltipUtils.addDescriptionComponent(tooltipComponents, ConstantComponents.SHEET);
   }

   protected boolean isPathfindable(BlockState state, PathComputationType pathComputationType) {
      return false;
   }
}
