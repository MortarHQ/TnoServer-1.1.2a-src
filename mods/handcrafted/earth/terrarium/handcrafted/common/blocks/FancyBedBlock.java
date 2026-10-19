package earth.terrarium.handcrafted.common.blocks;

import earth.terrarium.handcrafted.common.blocks.base.properties.ColorProperty;
import earth.terrarium.handcrafted.common.blocks.base.properties.DirectionalBlockProperty;
import earth.terrarium.handcrafted.common.constants.ConstantComponents;
import earth.terrarium.handcrafted.common.utils.InteractionUtils;
import earth.terrarium.handcrafted.common.utils.TooltipUtils;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FancyBedBlock extends BedBlock {
   public static final EnumProperty<BedPart> PART = BlockStateProperties.BED_PART;
   public static final BooleanProperty OCCUPIED = BlockStateProperties.OCCUPIED;
   public static final EnumProperty<DirectionalBlockProperty> SHAPE = EnumProperty.create("shape", DirectionalBlockProperty.class);
   public static final EnumProperty<ColorProperty> COLOR = EnumProperty.create("color", ColorProperty.class);
   public static final VoxelShape VOXEL_SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 9.0, 16.0);

   public FancyBedBlock(Properties properties) {
      super(DyeColor.WHITE, properties);
      this.registerDefaultState(
         (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.defaultBlockState().setValue(FACING, Direction.NORTH))
                     .setValue(PART, BedPart.FOOT))
                  .setValue(OCCUPIED, false))
               .setValue(SHAPE, DirectionalBlockProperty.SINGLE))
            .setValue(COLOR, ColorProperty.WHITE)
      );
   }

   public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean moved) {
      if (!level.isClientSide() && state.getBlock() != newState.getBlock() && state.getValue(COLOR) != ColorProperty.WHITE) {
         Containers.dropItemStack(
            level,
            pos.getX(),
            pos.getY(),
            pos.getZ(),
            state.getValue(PART) == BedPart.FOOT ? ((ColorProperty)state.getValue(COLOR)).toSheet() : ((ColorProperty)state.getValue(COLOR)).toCushion()
         );
      }

      super.onRemove(state, level, pos, newState, moved);
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{FACING, PART, OCCUPIED, SHAPE, COLOR});
   }

   public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
      return null;
   }

   @NotNull
   public RenderShape getRenderShape(BlockState state) {
      return RenderShape.MODEL;
   }

   @NotNull
   public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      return VOXEL_SHAPE;
   }

   @NotNull
   public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
      if (direction != this.getNeighbourDirection((BedPart)state.getValue(PART), (Direction)state.getValue(FACING))) {
         return super.updateShape(
            (BlockState)state.setValue(SHAPE, getShape(this, (Direction)state.getValue(FACING), level, pos)), direction, neighborState, level, pos, neighborPos
         );
      } else {
         return neighborState.is(this) && neighborState.getValue(PART) != state.getValue(PART)
            ? (BlockState)((BlockState)state.setValue(OCCUPIED, (Boolean)neighborState.getValue(OCCUPIED)))
               .setValue(SHAPE, getShape(this, (Direction)state.getValue(FACING), level, pos))
            : Blocks.AIR.defaultBlockState();
      }
   }

   @Nullable
   public BlockState getStateForPlacement(BlockPlaceContext context) {
      Direction direction = context.getHorizontalDirection();
      BlockPos pos = context.getClickedPos();
      BlockPos relativePos = pos.relative(direction);
      Level level = context.getLevel();
      return level.getBlockState(relativePos).canBeReplaced(context) && level.getWorldBorder().isWithinBounds(relativePos)
         ? (BlockState)((BlockState)this.defaultBlockState().setValue(FACING, direction)).setValue(SHAPE, getShape(this, direction, level, pos))
         : null;
   }

   private Direction getNeighbourDirection(BedPart part, Direction direction) {
      return part == BedPart.FOOT ? direction : direction.getOpposite();
   }

   protected ItemInteractionResult useItemOn(
      ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult
   ) {
      ItemInteractionResult result = state.getValue(PART) == BedPart.HEAD
         ? InteractionUtils.interactCushion(state, level, pos, player, stack, COLOR)
         : InteractionUtils.interactSheet(state, level, pos, player, stack, COLOR);
      return result != ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION ? result : super.useItemOn(stack, state, level, pos, player, hand, hitResult);
   }

   public static DirectionalBlockProperty getShape(Block block, Direction direction, BlockGetter level, BlockPos pos) {
      BlockState state1 = level.getBlockState(pos.relative(direction.getClockWise()));
      BlockState state2 = level.getBlockState(pos.relative(direction.getClockWise().getOpposite()));
      boolean check1 = state1.is(block) && state1.getValue(DiningBenchBlock.FACING) != direction;
      boolean check2 = state2.is(block) && state2.getValue(DiningBenchBlock.FACING) != direction;
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

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
      TooltipUtils.addDescriptionComponent(tooltipComponents, ConstantComponents.BED_PILLOW, ConstantComponents.BED_SHEET);
   }
}
