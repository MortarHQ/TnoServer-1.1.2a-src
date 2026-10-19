package twilightforest.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import twilightforest.init.TFBlockEntities;

public class RedThreadBlockEntity extends BlockEntity {
   public RedThreadBlockEntity(BlockPos pos, BlockState state) {
      super((BlockEntityType)TFBlockEntities.RED_THREAD.get(), pos, state);
   }
}
