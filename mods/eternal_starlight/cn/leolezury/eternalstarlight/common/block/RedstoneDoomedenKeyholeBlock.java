package cn.leolezury.eternalstarlight.common.block;

import com.mojang.serialization.MapCodec;
import java.util.Arrays;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
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
import org.jetbrains.annotations.Nullable;

public class RedstoneDoomedenKeyholeBlock extends HorizontalAxisBlock {
   public static final MapCodec<RedstoneDoomedenKeyholeBlock> CODEC = simpleCodec(RedstoneDoomedenKeyholeBlock::new);
   public static final BooleanProperty LIT = BlockStateProperties.LIT;

   public RedstoneDoomedenKeyholeBlock(Properties properties) {
      super(properties);
      this.registerDefaultState((BlockState)((BlockState)((BlockState)this.getStateDefinition().any()).setValue(LIT, false)).setValue(AXIS, Axis.X));
   }

   @Override
   protected MapCodec<RedstoneDoomedenKeyholeBlock> codec() {
      return CODEC;
   }

   @Nullable
   public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
      return (BlockState)this.defaultBlockState().setValue(AXIS, blockPlaceContext.getHorizontalDirection().getAxis());
   }

   protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
      boolean lit = (Boolean)state.getValue(LIT);
      if (!lit
         && Arrays.stream(Direction.values())
            .filter(direction -> direction.getAxis() == state.getValue(AXIS))
            .anyMatch(direction -> this.hasInputSignal(level, pos, direction))) {
         level.setBlockAndUpdate(pos, (BlockState)state.setValue(LIT, true));
      }

      if (lit
         && Arrays.stream(Direction.values())
            .filter(direction -> direction.getAxis() == state.getValue(AXIS))
            .noneMatch(direction -> this.hasInputSignal(level, pos, direction))) {
         level.scheduleTick(pos, this, 4);
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
      if ((Boolean)blockState.getValue(LIT)
         && Arrays.stream(Direction.values())
            .filter(direction -> direction.getAxis() == blockState.getValue(AXIS))
            .noneMatch(direction -> this.hasInputSignal(serverLevel, blockPos, direction))) {
         serverLevel.setBlockAndUpdate(blockPos, (BlockState)blockState.setValue(LIT, false));
      }
   }

   public boolean isSignalSource(BlockState blockState) {
      return (Boolean)blockState.getValue(LIT);
   }

   public int getSignal(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, Direction direction) {
      return blockState.getValue(LIT) && direction.getAxis() != blockState.getValue(AXIS) ? 15 : super.getSignal(blockState, blockGetter, blockPos, direction);
   }

   @Override
   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      super.createBlockStateDefinition(builder);
      builder.add(new Property[]{LIT});
   }
}
