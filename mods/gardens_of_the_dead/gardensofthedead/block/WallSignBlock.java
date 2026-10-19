package gardensofthedead.block;

import gardensofthedead.registry.ModBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.WoodType;

public class WallSignBlock extends net.minecraft.world.level.block.WallSignBlock {
   public WallSignBlock(WoodType woodType, Properties properties) {
      super(woodType, properties);
   }

   public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
      return ((BlockEntityType)ModBlockEntityTypes.SIGN.get()).create(pos, state);
   }
}
