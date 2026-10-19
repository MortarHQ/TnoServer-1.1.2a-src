package gardensofthedead.block.entity;

import gardensofthedead.registry.ModBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class SignBlockEntity extends net.minecraft.world.level.block.entity.SignBlockEntity {
   public SignBlockEntity(BlockPos pos, BlockState blockState) {
      super(pos, blockState);
   }

   public BlockEntityType<?> getType() {
      return (BlockEntityType<?>)ModBlockEntityTypes.SIGN.get();
   }

   public boolean isValidBlockState(BlockState blockState) {
      return this.getType().isValid(blockState);
   }
}
