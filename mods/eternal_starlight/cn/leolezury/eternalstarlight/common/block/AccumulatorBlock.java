package cn.leolezury.eternalstarlight.common.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.jetbrains.annotations.Nullable;

public class AccumulatorBlock extends DirectionalBlock {
   public static final MapCodec<AccumulatorBlock> CODEC = simpleCodec(AccumulatorBlock::new);
   public static final IntegerProperty POWER = BlockStateProperties.POWER;

   public AccumulatorBlock(Properties properties) {
      super(properties);
      this.registerDefaultState((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(FACING, Direction.UP)).setValue(POWER, 0));
   }

   protected MapCodec<? extends AccumulatorBlock> codec() {
      return CODEC;
   }

   @Nullable
   public BlockState getStateForPlacement(BlockPlaceContext context) {
      return (BlockState)this.defaultBlockState().setValue(FACING, context.getNearestLookingDirection().getOpposite());
   }

   protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
      if (!level.isClientSide) {
         int signal = level.getSignal(pos.relative((Direction)state.getValue(FACING)), (Direction)state.getValue(FACING));
         if ((Integer)state.getValue(POWER) != signal) {
            level.setBlockAndUpdate(pos, (BlockState)state.setValue(POWER, signal));
         }
      }
   }

   protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
      if (!level.isClientSide) {
         int signal = level.getSignal(pos.relative((Direction)state.getValue(FACING)), (Direction)state.getValue(FACING));
         if ((Integer)state.getValue(POWER) != signal) {
            level.setBlockAndUpdate(pos, (BlockState)state.setValue(POWER, signal));
         }
      }
   }

   protected boolean isSignalSource(BlockState state) {
      return (Integer)state.getValue(POWER) > 0;
   }

   protected int getSignal(BlockState blockState, BlockGetter blockAccess, BlockPos pos, Direction side) {
      return side != ((Direction)blockState.getValue(FACING)).getOpposite() ? (Integer)blockState.getValue(POWER) : 0;
   }

   protected BlockState rotate(BlockState state, Rotation rotation) {
      return (BlockState)state.setValue(FACING, rotation.rotate((Direction)state.getValue(FACING)));
   }

   protected BlockState mirror(BlockState state, Mirror mirror) {
      return state.rotate(mirror.getRotation((Direction)state.getValue(FACING)));
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{FACING, POWER});
   }
}
