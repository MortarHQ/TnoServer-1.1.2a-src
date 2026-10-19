package twilightforest.block;

import com.mojang.serialization.MapCodec;
import java.util.Arrays;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import twilightforest.block.entity.CarminiteReactorBlockEntity;
import twilightforest.init.TFBlockEntities;
import twilightforest.init.TFBlocks;

public class CarminiteReactorBlock extends BaseEntityBlock {
   public static final BooleanProperty ACTIVE = BooleanProperty.create("active");
   public static final MapCodec<CarminiteReactorBlock> CODEC = simpleCodec(CarminiteReactorBlock::new);

   public CarminiteReactorBlock(Properties properties) {
      super(properties);
      this.registerDefaultState((BlockState)((BlockState)this.getStateDefinition().any()).setValue(ACTIVE, false));
   }

   protected MapCodec<? extends BaseEntityBlock> codec() {
      return CODEC;
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{ACTIVE});
   }

   public RenderShape getRenderShape(BlockState state) {
      return RenderShape.MODEL;
   }

   public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
      if (!level.isClientSide()) {
         if (!(Boolean)state.getValue(ACTIVE) && this.isReactorReady(level, pos)) {
            level.setBlockAndUpdate(pos, (BlockState)state.setValue(ACTIVE, true));
         }
      }
   }

   private boolean isReactorReady(Level level, BlockPos pos) {
      return Arrays.stream(Direction.values()).allMatch(e -> level.getBlockState(pos.relative(e)).is(Blocks.REDSTONE_BLOCK));
   }

   public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean moving) {
      if (!newState.is(state.getBlock())) {
         for (BlockPos offset : BlockPos.betweenClosed(pos.offset(-1, -1, -1), pos.offset(1, 1, 1))) {
            BlockState checkState = level.getBlockState(offset);
            if (checkState.is(TFBlocks.FAKE_GOLD) || checkState.is(TFBlocks.FAKE_DIAMOND)) {
               level.destroyBlock(offset, false);
            }
         }
      }

      super.onRemove(state, level, pos, newState, moving);
   }

   public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
      return new CarminiteReactorBlockEntity(pos, state);
   }

   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
      return createTickerHelper(type, (BlockEntityType)TFBlockEntities.CARMINITE_REACTOR.get(), CarminiteReactorBlockEntity::tick);
   }
}
