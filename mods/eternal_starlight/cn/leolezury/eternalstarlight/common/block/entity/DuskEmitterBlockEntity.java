package cn.leolezury.eternalstarlight.common.block.entity;

import cn.leolezury.eternalstarlight.common.registry.ESBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

public class DuskEmitterBlockEntity extends AbstractDuskLightBlockEntity {
   public DuskEmitterBlockEntity(BlockPos blockPos, BlockState blockState) {
      super(ESBlockEntities.DUSK_EMITTER.get(), blockPos, blockState);
      this.alwaysActivated = true;
   }

   @Override
   protected boolean isFaceActivated(BlockState state, Direction direction) {
      return (Boolean)state.getValue((Property)PipeBlock.PROPERTY_BY_DIRECTION.get(direction));
   }
}
