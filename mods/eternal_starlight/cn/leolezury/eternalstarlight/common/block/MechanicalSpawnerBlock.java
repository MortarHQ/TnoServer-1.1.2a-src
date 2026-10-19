package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.block.entity.MechanicalSpawnerBlockEntity;
import cn.leolezury.eternalstarlight.common.registry.ESBlockEntities;
import com.mojang.serialization.MapCodec;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.Spawner;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.jetbrains.annotations.Nullable;

public class MechanicalSpawnerBlock extends BaseEntityBlock {
   public static final MapCodec<MechanicalSpawnerBlock> CODEC = simpleCodec(MechanicalSpawnerBlock::new);
   public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
   public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
   public static final IntegerProperty POWER = BlockStateProperties.POWER;

   public MechanicalSpawnerBlock(Properties properties) {
      super(properties);
      this.registerDefaultState(
         (BlockState)((BlockState)((BlockState)((BlockState)this.getStateDefinition().any()).setValue(FACING, Direction.NORTH))
               .setValue(HALF, DoubleBlockHalf.LOWER))
            .setValue(POWER, 0)
      );
   }

   public MapCodec<MechanicalSpawnerBlock> codec() {
      return CODEC;
   }

   protected BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
      DoubleBlockHalf half = (DoubleBlockHalf)state.getValue(HALF);
      if (facing.getAxis() != Axis.Y || half == DoubleBlockHalf.LOWER != (facing == Direction.UP) || facingState.is(this) && facingState.getValue(HALF) != half
         )
       {
         return half == DoubleBlockHalf.LOWER && facing == Direction.DOWN && !state.canSurvive(level, currentPos)
            ? Blocks.AIR.defaultBlockState()
            : super.updateShape(state, facing, facingState, level, currentPos, facingPos);
      } else {
         return Blocks.AIR.defaultBlockState();
      }
   }

   protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
      if (!level.isClientSide) {
         int signal = Math.max(
            level.getBestNeighborSignal(pos), level.getBestNeighborSignal(pos.relative(((DoubleBlockHalf)state.getValue(HALF)).getDirectionToOther()))
         );
         if ((Integer)state.getValue(POWER) != signal) {
            level.setBlockAndUpdate(pos, (BlockState)state.setValue(POWER, signal));
         }
      }
   }

   public BlockState getStateForPlacement(BlockPlaceContext context) {
      BlockPos pos = context.getClickedPos();
      Level level = context.getLevel();
      int signal = Math.max(level.getBestNeighborSignal(pos), level.getBestNeighborSignal(pos.above()));
      return pos.getY() < level.getMaxBuildHeight() - 1 && level.getBlockState(pos.above()).canBeReplaced(context)
         ? (BlockState)((BlockState)this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite())).setValue(POWER, signal)
         : null;
   }

   public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
      BlockPos above = pos.above();
      level.setBlock(
         above,
         DoublePlantBlock.copyWaterloggedFrom(
            level,
            above,
            (BlockState)((BlockState)this.defaultBlockState().setValue(FACING, (Direction)state.getValue(FACING))).setValue(HALF, DoubleBlockHalf.UPPER)
         ),
         3
      );
   }

   protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
      if (state.getValue(HALF) != DoubleBlockHalf.UPPER) {
         return super.canSurvive(state, level, pos);
      }

      BlockState belowState = level.getBlockState(pos.below());
      return state.getBlock() != this ? super.canSurvive(state, level, pos) : belowState.is(this) && belowState.getValue(HALF) == DoubleBlockHalf.LOWER;
   }

   public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
      if (!level.isClientSide) {
         if (player.isCreative()) {
            DoublePlantBlock.preventDropFromBottomPart(level, pos, state, player);
         } else {
            dropResources(state, level, pos, null, player, player.getMainHandItem());
         }
      }

      return super.playerWillDestroy(level, pos, state, player);
   }

   public void playerDestroy(Level level, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity te, ItemStack stack) {
      super.playerDestroy(level, player, pos, Blocks.AIR.defaultBlockState(), te, stack);
   }

   protected BlockState rotate(BlockState state, Rotation rot) {
      return (BlockState)state.setValue(FACING, rot.rotate((Direction)state.getValue(FACING)));
   }

   protected BlockState mirror(BlockState state, Mirror mirror) {
      return state.rotate(mirror.getRotation((Direction)state.getValue(FACING)));
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{FACING, HALF, POWER});
   }

   protected long getSeed(BlockState state, BlockPos pos) {
      return Mth.getSeed(pos.getX(), pos.below(state.getValue(HALF) == DoubleBlockHalf.LOWER ? 0 : 1).getY(), pos.getZ());
   }

   public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
      return state.getValue(HALF) == DoubleBlockHalf.UPPER ? new MechanicalSpawnerBlockEntity(pos, state) : null;
   }

   @Nullable
   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
      return state.getValue(HALF) == DoubleBlockHalf.UPPER
         ? createTickerHelper(
            blockEntityType,
            ESBlockEntities.MECHANICAL_SPAWNER.get(),
            level.isClientSide ? MechanicalSpawnerBlockEntity::clientTick : MechanicalSpawnerBlockEntity::serverTick
         )
         : null;
   }

   public RenderShape getRenderShape(BlockState state) {
      return RenderShape.ENTITYBLOCK_ANIMATED;
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
      super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
      Spawner.appendHoverText(stack, tooltipComponents, "spawn_data");
   }
}
