package earth.terrarium.handcrafted.common.blocks.trophies;

import com.mojang.serialization.MapCodec;
import earth.terrarium.handcrafted.common.blocks.base.SimpleBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class StatueTrophyBlock extends SimpleBlock {
   public static final MapCodec<StatueTrophyBlock> CODEC = simpleCodec(StatueTrophyBlock::new);
   public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;

   public StatueTrophyBlock(Properties properties) {
      super(properties);
      this.registerDefaultState(
         (BlockState)((BlockState)((BlockState)this.defaultBlockState().setValue(HALF, DoubleBlockHalf.LOWER)).setValue(FACING, Direction.NORTH))
            .setValue(WATERLOGGED, false)
      );
   }

   protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
      return CODEC;
   }

   @Override
   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{FACING, WATERLOGGED, HALF});
   }

   public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
      return Block.canSupportRigidBlock(level, pos.below()) && level.getBlockState(pos.above()).isAir();
   }

   public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
      if (!level.isClientSide() && player.isCreative()) {
         preventCreativeDropFromBottomPart(level, pos, state, player);
      }

      return super.playerWillDestroy(level, pos, state, player);
   }

   @NotNull
   public RenderShape getRenderShape(BlockState state) {
      return state.getValue(HALF) == DoubleBlockHalf.LOWER ? RenderShape.MODEL : RenderShape.INVISIBLE;
   }

   @NotNull
   @Override
   public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
      DoubleBlockHalf half = (DoubleBlockHalf)state.getValue(HALF);
      if (direction.getAxis() == Axis.Y && half == DoubleBlockHalf.LOWER == (direction == Direction.UP)) {
         return neighborState.is(this) && neighborState.getValue(HALF) != half
            ? (BlockState)state.setValue(FACING, (Direction)neighborState.getValue(FACING))
            : Blocks.AIR.defaultBlockState();
      } else {
         return half == DoubleBlockHalf.LOWER && direction == Direction.DOWN && !state.canSurvive(level, pos)
            ? Blocks.AIR.defaultBlockState()
            : super.updateShape(state, direction, neighborState, level, pos, neighborPos);
      }
   }

   @Nullable
   @Override
   public BlockState getStateForPlacement(BlockPlaceContext context) {
      BlockPos pos = context.getClickedPos();
      Level level = context.getLevel();
      return pos.getY() < level.getMaxBuildHeight() - 1 && level.getBlockState(pos.above()).canBeReplaced(context)
         ? (BlockState)this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite())
         : null;
   }

   public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, @NotNull ItemStack stack) {
      level.setBlockAndUpdate(pos.above(), (BlockState)state.setValue(HALF, DoubleBlockHalf.UPPER));
   }

   public long getSeed(BlockState state, BlockPos pos) {
      return Mth.getSeed(pos.getX(), pos.below(state.getValue(HALF) == DoubleBlockHalf.LOWER ? 0 : 1).getY(), pos.getZ());
   }

   public static void preventCreativeDropFromBottomPart(Level level, BlockPos pos, BlockState state, Player player) {
      if (state.getValue(HALF) == DoubleBlockHalf.UPPER) {
         BlockPos belowPos = pos.below();
         BlockState belowState = level.getBlockState(belowPos);
         if (belowState.is(state.getBlock()) && belowState.getValue(HALF) == DoubleBlockHalf.LOWER) {
            BlockState blockState2 = belowState.getFluidState().is(Fluids.WATER) ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState();
            level.setBlock(belowPos, blockState2, 35);
            level.levelEvent(player, 2001, belowPos, Block.getId(belowState));
         }
      }
   }
}
