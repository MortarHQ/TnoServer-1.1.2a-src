package team.lodestar.lodestone.systems.block;

import java.util.function.Supplier;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import org.jetbrains.annotations.Nullable;

public class LodestoneLogBlock extends RotatedPillarBlock {
   @Nullable
   public final Supplier<Block> stripped;

   public LodestoneLogBlock(Properties properties, @Nullable Supplier<Block> stripped) {
      super(properties);
      this.stripped = stripped;
   }

   public LodestoneLogBlock(Properties properties) {
      this(properties, null);
   }

   @Nullable
   public BlockState getToolModifiedState(BlockState state, UseOnContext context, ItemAbility itemAbility, boolean simulate) {
      return this.stripped != null && itemAbility.equals(ItemAbilities.AXE_STRIP)
         ? (BlockState)this.stripped.get().defaultBlockState().setValue(AXIS, (Axis)state.getValue(AXIS))
         : super.getToolModifiedState(state, context, itemAbility, simulate);
   }
}
