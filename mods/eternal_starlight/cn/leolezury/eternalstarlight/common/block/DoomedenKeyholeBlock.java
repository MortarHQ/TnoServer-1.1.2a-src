package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.util.ESTags;
import com.mojang.serialization.MapCodec;
import java.util.Arrays;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RedStoneWireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class DoomedenKeyholeBlock extends HorizontalAxisBlock {
   public static final MapCodec<DoomedenKeyholeBlock> CODEC = simpleCodec(DoomedenKeyholeBlock::new);
   public static final BooleanProperty LIT = BlockStateProperties.LIT;

   public DoomedenKeyholeBlock(Properties properties) {
      super(properties);
      this.registerDefaultState((BlockState)((BlockState)((BlockState)this.getStateDefinition().any()).setValue(LIT, false)).setValue(AXIS, Axis.X));
   }

   @Override
   protected MapCodec<DoomedenKeyholeBlock> codec() {
      return CODEC;
   }

   @Nullable
   public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
      return (BlockState)this.defaultBlockState().setValue(AXIS, blockPlaceContext.getHorizontalDirection().getAxis());
   }

   protected ItemInteractionResult useItemOn(
      ItemStack itemStack, BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult
   ) {
      if (itemStack.is(ESTags.Items.DOOMEDEN_KEYS) && !(Boolean)blockState.getValue(LIT)) {
         level.setBlockAndUpdate(blockPos, (BlockState)blockState.setValue(LIT, true));
         level.scheduleTick(blockPos, this, 15);
         return ItemInteractionResult.sidedSuccess(level.isClientSide);
      } else {
         return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
      }
   }

   protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
      if (level.getBlockState(fromPos).getBlock() instanceof RedstoneDoomedenKeyholeBlock
         && !(Boolean)state.getValue(LIT)
         && Arrays.stream(Direction.values()).anyMatch(direction -> this.hasInputSignal(level, pos, direction))) {
         level.setBlockAndUpdate(pos, (BlockState)state.setValue(LIT, true));
         level.scheduleTick(pos, this, 15);
      }
   }

   protected boolean hasInputSignal(Level level, BlockPos pos, Direction direction) {
      BlockPos blockPos = pos.relative(direction);
      int i = level.getSignal(blockPos, direction);
      if (i >= 15) {
         return true;
      }

      BlockState blockState = level.getBlockState(blockPos);
      return Math.max(i, blockState.is(Blocks.REDSTONE_WIRE) ? (Integer)blockState.getValue(RedStoneWireBlock.POWER) : 0) > 0;
   }

   protected void tick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
      if ((Boolean)blockState.getValue(LIT)) {
         if (blockState.getValue(AXIS) == Axis.X) {
            for (int i = -1; i <= 1; i++) {
               for (int j = -1; j <= 1; j++) {
                  if (serverLevel.getBlockState(blockPos.offset(0, i, j)).is(ESTags.Blocks.DOOMEDEN_KEYHOLE_DESTROYABLES)) {
                     serverLevel.destroyBlock(blockPos.offset(0, i, j), false, null);
                  }
               }
            }
         } else {
            for (int i = -1; i <= 1; i++) {
               for (int j = -1; j <= 1; j++) {
                  if (serverLevel.getBlockState(blockPos.offset(i, j, 0)).is(ESTags.Blocks.DOOMEDEN_KEYHOLE_DESTROYABLES)) {
                     serverLevel.destroyBlock(blockPos.offset(i, j, 0), false, null);
                  }
               }
            }
         }

         serverLevel.destroyBlock(blockPos, false, null);
      }
   }

   public boolean isSignalSource(BlockState blockState) {
      return (Boolean)blockState.getValue(LIT);
   }

   public int getSignal(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, Direction direction) {
      return blockState.getValue(LIT) ? 15 : super.getSignal(blockState, blockGetter, blockPos, direction);
   }

   @Override
   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      super.createBlockStateDefinition(builder);
      builder.add(new Property[]{LIT});
   }
}
