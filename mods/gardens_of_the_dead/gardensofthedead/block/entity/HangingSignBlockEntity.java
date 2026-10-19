package gardensofthedead.block.entity;

import gardensofthedead.registry.ModBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class HangingSignBlockEntity extends net.minecraft.world.level.block.entity.HangingSignBlockEntity {
   public HangingSignBlockEntity(BlockPos pos, BlockState blockState) {
      super(pos, blockState);
   }

   public BlockEntityType<?> getType() {
      return (BlockEntityType<?>)ModBlockEntityTypes.HANGING_SIGN.get();
   }

   public boolean isValidBlockState(BlockState blockState) {
      return this.getType().isValid(blockState);
   }
}
