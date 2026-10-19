package cn.leolezury.eternalstarlight.common.block.entity;

import cn.leolezury.eternalstarlight.common.registry.ESBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ESSkullBlockEntity extends SkullBlockEntity {
   public ESSkullBlockEntity(BlockPos pos, BlockState state) {
      super(pos, state);
   }

   public boolean isValidBlockState(BlockState blockState) {
      return this.getType().isValid(blockState);
   }

   public BlockEntityType<?> getType() {
      return ESBlockEntities.SKULL.get();
   }
}
