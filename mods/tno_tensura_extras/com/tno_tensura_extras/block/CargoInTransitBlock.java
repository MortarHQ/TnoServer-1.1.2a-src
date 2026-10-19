package com.tno_tensura_extras.block;

import com.mojang.serialization.MapCodec;
import com.tno_tensura_extras.block.entity.CargoInTransitBlockEntity;
import com.tno_tensura_extras.registry.ExtrasBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class CargoInTransitBlock extends BaseEntityBlock {
   public static final MapCodec<CargoInTransitBlock> CODEC = simpleCodec(CargoInTransitBlock::new);

   public CargoInTransitBlock(Properties properties) {
      super(properties);
   }

   protected MapCodec<CargoInTransitBlock> codec() {
      return CODEC;
   }

   public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
      return new CargoInTransitBlockEntity(pos, state);
   }

   @Nullable
   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
      return createTickerHelper(type, ExtrasBlocks.CARGO_IN_TRANSIT_ENTITY.get(), CargoInTransitBlockEntity::tick);
   }

   protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      return Shapes.empty();
   }

   protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
      if (!state.is(newState.getBlock()) && level.getBlockEntity(pos) instanceof CargoInTransitBlockEntity cargo) {
         cargo.dropUndelivered();
      }

      super.onRemove(state, level, pos, newState, movedByPiston);
   }
}
