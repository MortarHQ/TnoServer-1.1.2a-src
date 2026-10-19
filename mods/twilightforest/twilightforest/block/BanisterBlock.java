package twilightforest.block;

import com.mojang.serialization.MapCodec;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.TooltipContext;
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
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.ItemAbilities;
import org.jetbrains.annotations.Nullable;
import twilightforest.data.tags.BlockTagGenerator;
import twilightforest.enums.BanisterShape;

public class BanisterBlock extends HorizontalDirectionalBlock implements SimpleWaterloggedBlock {
   public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
   public static final EnumProperty<BanisterShape> SHAPE = EnumProperty.create("shape", BanisterShape.class);
   public static final BooleanProperty EXTENDED = BooleanProperty.create("extended");
   public static final MapCodec<BanisterBlock> CODEC = simpleCodec(BanisterBlock::new);
   private static final VoxelShape NORTH_SUPPORTS_TALL = Shapes.or(Block.box(2.5, 0.0, 0.0, 5.5, 16.0, 3.0), Block.box(10.5, 0.0, 0.0, 13.5, 16.0, 3.0));
   private static final VoxelShape EAST_SUPPORTS_TALL = Shapes.or(Block.box(13.0, 0.0, 2.5, 16.0, 16.0, 5.5), Block.box(13.0, 0.0, 10.5, 16.0, 16.0, 13.5));
   private static final VoxelShape SOUTH_SUPPORTS_TALL = Shapes.or(Block.box(2.5, 0.0, 13.0, 5.5, 16.0, 16.0), Block.box(10.5, 0.0, 13.0, 13.5, 16.0, 16.0));
   private static final VoxelShape WEST_SUPPORTS_TALL = Shapes.or(Block.box(0.0, 0.0, 2.5, 3.0, 16.0, 5.5), Block.box(0.0, 0.0, 10.5, 3.0, 16.0, 13.5));
   private static final VoxelShape NORTH_SUPPORTS_SHORT = Shapes.or(Block.box(2.5, 0.0, 0.0, 5.5, 4.0, 3.0), Block.box(10.5, 0.0, 0.0, 13.5, 4.0, 3.0));
   private static final VoxelShape EAST_SUPPORTS_SHORT = Shapes.or(Block.box(13.0, 0.0, 2.5, 16.0, 4.0, 5.5), Block.box(13.0, 0.0, 10.5, 16.0, 4.0, 13.5));
   private static final VoxelShape SOUTH_SUPPORTS_SHORT = Shapes.or(Block.box(2.5, 0.0, 13.0, 5.5, 4.0, 16.0), Block.box(10.5, 0.0, 13.0, 13.5, 4.0, 16.0));
   private static final VoxelShape WEST_SUPPORTS_SHORT = Shapes.or(Block.box(0.0, 0.0, 2.5, 3.0, 4.0, 5.5), Block.box(0.0, 0.0, 10.5, 3.0, 4.0, 13.5));
   private static final VoxelShape NORTH_EXTENSION = Shapes.or(Block.box(2.5, -8.0, 0.0, 5.5, 0.0, 3.0), Block.box(10.5, -8.0, 0.0, 13.5, 0.0, 3.0));
   private static final VoxelShape EAST_EXTENSION = Shapes.or(Block.box(13.0, -8.0, 2.5, 16.0, 0.0, 5.5), Block.box(13.0, -8.0, 10.5, 16.0, 0.0, 13.5));
   private static final VoxelShape SOUTH_EXTENSION = Shapes.or(Block.box(2.5, -8.0, 13.0, 5.5, 0.0, 16.0), Block.box(10.5, -8.0, 13.0, 13.5, 0.0, 16.0));
   private static final VoxelShape WEST_EXTENSION = Shapes.or(Block.box(0.0, -8.0, 2.5, 3.0, 0.0, 5.5), Block.box(0.0, -8.0, 10.5, 3.0, 0.0, 13.5));
   private static final VoxelShape NORTH_TALL = Shapes.or(Block.box(0.0, 12.0, 0.0, 16.0, 16.0, 4.0), NORTH_SUPPORTS_TALL);
   private static final VoxelShape NORTH_TALL_EXTENDED = Shapes.or(NORTH_TALL, NORTH_EXTENSION);
   private static final VoxelShape EAST_TALL = Shapes.or(Block.box(12.0, 12.0, 0.0, 16.0, 16.0, 16.0), EAST_SUPPORTS_TALL);
   private static final VoxelShape EAST_TALL_EXTENDED = Shapes.or(EAST_TALL, EAST_EXTENSION);
   private static final VoxelShape SOUTH_TALL = Shapes.or(Block.box(0.0, 12.0, 12.0, 16.0, 16.0, 16.0), SOUTH_SUPPORTS_TALL);
   private static final VoxelShape SOUTH_TALL_EXTENDED = Shapes.or(SOUTH_TALL, SOUTH_EXTENSION);
   private static final VoxelShape WEST_TALL = Shapes.or(Block.box(0.0, 12.0, 0.0, 4.0, 16.0, 16.0), WEST_SUPPORTS_TALL);
   private static final VoxelShape WEST_TALL_EXTENDED = Shapes.or(WEST_TALL, WEST_EXTENSION);
   private static final VoxelShape NORTH_SHORT = Shapes.or(Block.box(0.0, 4.0, 0.0, 16.0, 8.0, 4.0), NORTH_SUPPORTS_SHORT);
   private static final VoxelShape NORTH_SHORT_EXTENDED = Shapes.or(NORTH_SHORT, NORTH_EXTENSION);
   private static final VoxelShape EAST_SHORT = Shapes.or(Block.box(12.0, 4.0, 0.0, 16.0, 8.0, 16.0), EAST_SUPPORTS_SHORT);
   private static final VoxelShape EAST_SHORT_EXTENDED = Shapes.or(EAST_SHORT, EAST_EXTENSION);
   private static final VoxelShape SOUTH_SHORT = Shapes.or(Block.box(0.0, 4.0, 12.0, 16.0, 8.0, 16.0), SOUTH_SUPPORTS_SHORT);
   private static final VoxelShape SOUTH_SHORT_EXTENDED = Shapes.or(SOUTH_SHORT, SOUTH_EXTENSION);
   private static final VoxelShape WEST_SHORT = Shapes.or(Block.box(0.0, 4.0, 0.0, 4.0, 8.0, 16.0), WEST_SUPPORTS_SHORT);
   private static final VoxelShape WEST_SHORT_EXTENDED = Shapes.or(WEST_SHORT, WEST_EXTENSION);
   private static final VoxelShape NORTH_CONNECTED_EXTENDED = Shapes.or(NORTH_SUPPORTS_TALL, NORTH_EXTENSION);
   private static final VoxelShape EAST_CONNECTED_EXTENDED = Shapes.or(EAST_SUPPORTS_TALL, EAST_EXTENSION);
   private static final VoxelShape SOUTH_CONNECTED_EXTENDED = Shapes.or(SOUTH_SUPPORTS_TALL, SOUTH_EXTENSION);
   private static final VoxelShape WEST_CONNECTED_EXTENDED = Shapes.or(WEST_SUPPORTS_TALL, WEST_EXTENSION);

   public BanisterBlock(Properties properties) {
      super(properties);
      this.registerDefaultState(
         (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.getStateDefinition().any()).setValue(SHAPE, BanisterShape.TALL))
                  .setValue(EXTENDED, false))
               .setValue(FACING, Direction.NORTH))
            .setValue(WATERLOGGED, false)
      );
   }

   protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
      return CODEC;
   }

   public BlockState getStateForPlacement(BlockPlaceContext context) {
      BlockPos pos = context.getClickedPos();
      return (BlockState)((BlockState)((BlockState)this.defaultBlockState().setValue(FACING, context.getHorizontalDirection()))
            .setValue(SHAPE, context.getLevel().getBlockState(pos.above()).is(BlockTagGenerator.BANISTERS) ? BanisterShape.CONNECTED : BanisterShape.TALL))
         .setValue(WATERLOGGED, context.getLevel().getFluidState(pos).getType() == Fluids.WATER);
   }

   public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
      boolean extended = (Boolean)state.getValue(EXTENDED);

      return switch ((BanisterShape)state.getValue(SHAPE)) {
         case SHORT -> {
            switch ((Direction)state.getValue(FACING)) {
               case NORTH:
                  yield extended ? NORTH_SHORT_EXTENDED : NORTH_SHORT;
               case WEST:
                  yield extended ? WEST_SHORT_EXTENDED : WEST_SHORT;
               case EAST:
                  yield extended ? EAST_SHORT_EXTENDED : EAST_SHORT;
               default:
                  yield extended ? SOUTH_SHORT_EXTENDED : SOUTH_SHORT;
            }
         }
         case CONNECTED -> {
            switch ((Direction)state.getValue(FACING)) {
               case NORTH:
                  yield extended ? NORTH_CONNECTED_EXTENDED : NORTH_SUPPORTS_TALL;
               case WEST:
                  yield extended ? WEST_CONNECTED_EXTENDED : WEST_SUPPORTS_TALL;
               case EAST:
                  yield extended ? EAST_CONNECTED_EXTENDED : EAST_SUPPORTS_TALL;
               default:
                  yield extended ? SOUTH_CONNECTED_EXTENDED : SOUTH_SUPPORTS_TALL;
            }
         }
         case TALL -> {
            switch ((Direction)state.getValue(FACING)) {
               case NORTH:
                  yield extended ? NORTH_TALL_EXTENDED : NORTH_TALL;
               case WEST:
                  yield extended ? WEST_TALL_EXTENDED : WEST_TALL;
               case EAST:
                  yield extended ? EAST_TALL_EXTENDED : EAST_TALL;
               default:
                  yield extended ? SOUTH_TALL_EXTENDED : SOUTH_TALL;
            }
         }
      };
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
      tooltip.add(Component.translatable("block.twilightforest.banister.cycle").withStyle(ChatFormatting.GRAY));
   }

   @Nullable
   public PathType getBlockPathType(BlockState state, BlockGetter level, BlockPos pos, @Nullable Mob mob) {
      return PathType.FENCE;
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> blockStateBuilder) {
      blockStateBuilder.add(new Property[]{SHAPE, EXTENDED, FACING, WATERLOGGED});
   }

   public FluidState getFluidState(BlockState state) {
      return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
   }

   protected ItemInteractionResult useItemOn(
      ItemStack held, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result
   ) {
      if (!held.canPerformAction(ItemAbilities.AXE_WAX_OFF)) {
         return super.useItemOn(held, state, level, pos, player, hand, result);
      }

      BlockState newState = (BlockState)state.cycle(SHAPE);
      BlockState belowState = level.getBlockState(pos.below());
      level.playSound(null, pos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);
      if (belowState.getBlock() instanceof BanisterBlock && belowState.getValue(SHAPE) != BanisterShape.SHORT) {
         level.setBlock(pos, (BlockState)newState.setValue(EXTENDED, false), 3);
      } else {
         level.setBlock(pos, newState.getValue(SHAPE) == BanisterShape.TALL ? (BlockState)newState.cycle(EXTENDED) : newState, 3);
      }

      return ItemInteractionResult.SUCCESS;
   }

   protected BlockState updateShape(
      BlockState thisState, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos thisPos, BlockPos neighborPos
   ) {
      return direction == Direction.DOWN
            && neighborState.getBlock() instanceof BanisterBlock
            && neighborState.getValue(SHAPE) != BanisterShape.SHORT
            && thisState.getValue(EXTENDED)
         ? (BlockState)thisState.setValue(EXTENDED, false)
         : super.updateShape(thisState, direction, neighborState, level, thisPos, neighborPos);
   }
}
