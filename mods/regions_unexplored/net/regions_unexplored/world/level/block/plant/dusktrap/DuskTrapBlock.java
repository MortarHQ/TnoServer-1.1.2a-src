package net.regions_unexplored.world.level.block.plant.dusktrap;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.Property;
import net.regions_unexplored.world.level.block.state.properties.RuBlockStateProperties;

public class DuskTrapBlock extends BaseDuskTrapBlock {
   public static final BooleanProperty POWERED = RuBlockStateProperties.CLOSED;
   private final DuskTrapBlock.Sensitivity sensitivity;

   public DuskTrapBlock(DuskTrapBlock.Sensitivity sensitivity, Properties properties) {
      super(properties);
      this.registerDefaultState(
         (BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(POWERED, false)).setValue(HALF, DoubleBlockHalf.LOWER)
      );
      this.sensitivity = sensitivity;
   }

   @Override
   protected int getSignalForState(BlockState blockState) {
      return blockState.getValue(POWERED) ? 15 : 0;
   }

   @Override
   protected BlockState setSignalForState(BlockState blockState, int signalStrength) {
      return (BlockState)blockState.setValue(POWERED, signalStrength > 0);
   }

   protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
      return state.isSolidRender(level, pos);
   }

   @Override
   protected int getSignalStrength(Level level, BlockPos blockPos) {
      Class oclass = switch (this.sensitivity) {
         case EVERYTHING -> Entity.class;
         case MOBS -> LivingEntity.class;
      };
      return getEntityCount(level, TOUCH_AABB.move(blockPos), oclass) > 0 ? 15 : 0;
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> stateBuilder) {
      stateBuilder.add(new Property[]{POWERED}).add(new Property[]{HALF});
   }

   public enum Sensitivity {
      EVERYTHING,
      MOBS;
   }
}
