package cn.leolezury.eternalstarlight.common.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;

public abstract class HorizontalAxisBlock extends Block {
   public static final EnumProperty<Axis> AXIS = BlockStateProperties.HORIZONTAL_AXIS;

   protected HorizontalAxisBlock(Properties properties) {
      super(properties);
   }

   protected abstract MapCodec<? extends HorizontalAxisBlock> codec();

   protected BlockState rotate(BlockState blockState, Rotation rotation) {
      switch (rotation) {
         case COUNTERCLOCKWISE_90:
         case CLOCKWISE_90:
            switch ((Axis)blockState.getValue(AXIS)) {
               case X:
                  return (BlockState)blockState.setValue(AXIS, Axis.Z);
               case Z:
                  return (BlockState)blockState.setValue(AXIS, Axis.X);
               default:
                  return blockState;
            }
         default:
            return blockState;
      }
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{AXIS});
   }
}
