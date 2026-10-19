package cn.leolezury.eternalstarlight.common.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseTorchBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;

public class DoomedenRedstoneTorchBlock extends BaseTorchBlock {
   public static final MapCodec<DoomedenRedstoneTorchBlock> CODEC = simpleCodec(DoomedenRedstoneTorchBlock::new);
   public static final BooleanProperty LIT = BlockStateProperties.LIT;

   public DoomedenRedstoneTorchBlock(Properties properties) {
      super(properties);
      this.registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue(LIT, true));
   }

   protected MapCodec<? extends DoomedenRedstoneTorchBlock> codec() {
      return CODEC;
   }

   public void onPlace(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {
      for (Direction direction : Direction.values()) {
         level.updateNeighborsAt(blockPos.relative(direction), this);
      }
   }

   public void onRemove(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {
      if (!bl) {
         for (Direction direction : Direction.values()) {
            level.updateNeighborsAt(blockPos.relative(direction), this);
         }
      }
   }

   public int getSignal(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, Direction direction) {
      return blockState.getValue(LIT) && Direction.UP != direction ? 15 : 0;
   }

   protected boolean hasNeighborSignal(Level level, BlockPos blockPos) {
      return level.hasSignal(blockPos.below(), Direction.DOWN);
   }

   protected InteractionResult useWithoutItem(BlockState blockState, Level level, BlockPos blockPos, Player player, BlockHitResult blockHitResult) {
      level.setBlockAndUpdate(blockPos, (BlockState)blockState.setValue(LIT, !(Boolean)blockState.getValue(LIT)));
      return InteractionResult.sidedSuccess(level.isClientSide);
   }

   public void neighborChanged(BlockState blockState, Level level, BlockPos blockPos, Block block, BlockPos blockPos2, boolean bl) {
      if ((Boolean)blockState.getValue(LIT) == this.hasNeighborSignal(level, blockPos) && !level.getBlockTicks().willTickThisTick(blockPos, this)) {
         level.scheduleTick(blockPos, this, 2);
      }
   }

   public int getDirectSignal(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, Direction direction) {
      return direction == Direction.DOWN ? blockState.getSignal(blockGetter, blockPos, direction) : 0;
   }

   public boolean isSignalSource(BlockState blockState) {
      return true;
   }

   public void animateTick(BlockState blockState, Level level, BlockPos blockPos, RandomSource randomSource) {
      if ((Boolean)blockState.getValue(LIT)) {
         double d = blockPos.getX() + 0.5 + (randomSource.nextDouble() - 0.5) * 0.2;
         double e = blockPos.getY() + 0.7 + (randomSource.nextDouble() - 0.5) * 0.2;
         double f = blockPos.getZ() + 0.5 + (randomSource.nextDouble() - 0.5) * 0.2;
         level.addParticle(DustParticleOptions.REDSTONE, d, e, f, 0.0, 0.0, 0.0);
      }
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{LIT});
   }
}
