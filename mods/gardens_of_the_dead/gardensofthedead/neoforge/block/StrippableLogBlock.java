package gardensofthedead.neoforge.block;

import java.util.function.Supplier;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;

public class StrippableLogBlock extends RotatedPillarBlock {
   private final Supplier<? extends Block> strippedLogBlock;

   public StrippableLogBlock(Properties properties, Supplier<? extends Block> strippedLogBlock) {
      super(properties);
      this.strippedLogBlock = strippedLogBlock;
   }

   public BlockState getToolModifiedState(BlockState state, UseOnContext context, ItemAbility toolAction, boolean simulate) {
      return context.getItemInHand().canPerformAction(toolAction) && ItemAbilities.AXE_STRIP.equals(toolAction)
         ? (BlockState)this.strippedLogBlock.get().defaultBlockState().setValue(AXIS, (Axis)state.getValue(AXIS))
         : null;
   }
}
