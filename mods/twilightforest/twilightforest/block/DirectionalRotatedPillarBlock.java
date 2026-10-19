package twilightforest.block;

import net.minecraft.core.Direction.Axis;
import net.minecraft.core.Direction.AxisDirection;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;

public class DirectionalRotatedPillarBlock extends RotatedPillarBlock {
   public static final BooleanProperty REVERSED = BooleanProperty.create("reversed");

   public DirectionalRotatedPillarBlock(Properties properties) {
      super(properties);
      this.registerDefaultState((BlockState)((BlockState)((BlockState)this.getStateDefinition().any()).setValue(AXIS, Axis.Y)).setValue(REVERSED, false));
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      super.createBlockStateDefinition(builder.add(new Property[]{REVERSED}));
   }

   public BlockState getStateForPlacement(BlockPlaceContext context) {
      return (BlockState)super.getStateForPlacement(context).setValue(REVERSED, context.getClickedFace().getAxisDirection() == AxisDirection.NEGATIVE);
   }

   public BlockState mirror(BlockState state, Mirror mirror) {
      if (mirror != Mirror.NONE) {
         Axis axis = (Axis)state.getValue(AXIS);
         if (axis == Axis.Y || mirror == Mirror.LEFT_RIGHT && axis == Axis.Z || mirror == Mirror.FRONT_BACK && axis == Axis.X) {
            return (BlockState)state.cycle(REVERSED);
         }
      }

      return super.mirror(state, mirror);
   }
}
