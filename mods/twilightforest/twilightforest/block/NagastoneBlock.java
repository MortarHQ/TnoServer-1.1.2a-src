package twilightforest.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import twilightforest.enums.NagastoneVariant;
import twilightforest.init.TFBlocks;

public class NagastoneBlock extends Block {
   public static final EnumProperty<NagastoneVariant> VARIANT = EnumProperty.create("variant", NagastoneVariant.class);

   public NagastoneBlock(Properties properties) {
      super(properties);
      this.registerDefaultState((BlockState)((BlockState)this.getStateDefinition().any()).setValue(VARIANT, NagastoneVariant.SOLID));
   }

   public BlockState updateShape(
      BlockState state, Direction directionToNeighbor, BlockState neighborState, LevelAccessor accessor, BlockPos pos, BlockPos neighborPos
   ) {
      return this.getVariant(accessor, pos);
   }

   public BlockState getStateForPlacement(BlockPlaceContext context) {
      return this.getVariant(context.getLevel(), context.getClickedPos());
   }

   private BlockState getVariant(LevelAccessor accessor, BlockPos pos) {
      int connectionCount = 0;
      Direction[] facings = new Direction[2];

      for (Direction side : Direction.values()) {
         BlockState neighborState = accessor.getBlockState(pos.relative(side));
         if (neighborState.getBlock() == this
            || neighborState.getBlock() == TFBlocks.NAGASTONE_HEAD.get() && side == neighborState.getValue(TFHorizontalBlock.FACING)) {
            facings[connectionCount++] = side;
            if (connectionCount >= 2) {
               break;
            }
         }
      }

      BlockState stateOut;
      switch (connectionCount) {
         case 1:
            facings[1] = facings[0];
         case 2:
            stateOut = (BlockState)this.defaultBlockState().setValue(VARIANT, NagastoneVariant.getVariantFromDoubleFacing(facings[0], facings[1]));
            break;
         default:
            stateOut = this.defaultBlockState();
      }

      return stateOut;
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{VARIANT});
   }
}
