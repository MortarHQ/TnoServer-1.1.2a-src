package fuzs.puzzleslib.api.block.v1.entity;

import java.util.function.Consumer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public interface TickingEntityBlock<T extends BlockEntity & TickingBlockEntity> extends EntityBlock {
   BlockEntityType<? extends T> getBlockEntityType();

   default BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
      return this.getBlockEntityType().create(pos, state);
   }

   @Nullable
   default <BE extends BlockEntity> BlockEntityTicker<BE> getTicker(Level level, BlockState state, BlockEntityType<BE> blockEntityType) {
      if (this.getBlockEntityType().equals(blockEntityType)) {
         Consumer<TickingBlockEntity> ticker = level.isClientSide ? TickingBlockEntity::clientTick : TickingBlockEntity::serverTick;
         return ($, blockPos, blockState, blockEntity) -> ticker.accept((T)blockEntity);
      } else {
         return null;
      }
   }
}
