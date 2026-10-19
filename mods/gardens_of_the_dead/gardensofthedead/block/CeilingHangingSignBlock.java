package gardensofthedead.block;

import gardensofthedead.registry.ModBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.WoodType;

public class CeilingHangingSignBlock extends net.minecraft.world.level.block.CeilingHangingSignBlock {
   public CeilingHangingSignBlock(WoodType woodType, Properties properties) {
      super(woodType, properties);
   }

   public BlockEntity newBlockEntity(BlockPos pos, BlockState blockState) {
      return ((BlockEntityType)ModBlockEntityTypes.HANGING_SIGN.get()).create(pos, blockState);
   }
}
