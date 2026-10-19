package noobanidus.mods.lootr.common.api.replacement;

import java.util.function.Function;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public interface ILootrBlockReplacementProvider extends Function<Block, Block> {
   default int getPriority() {
      return 0;
   }

   TagKey<Block> getApplicableTag();

   Block getBlock();

   @Nullable
   default Block apply(Block block) {
      if (block == this.getBlock()) {
         return null;
      }

      BlockState defaultState = block.defaultBlockState();
      return !defaultState.isAir() && defaultState.is(this.getApplicableTag()) ? this.getBlock() : null;
   }

   default BlockState copyTypeSpecificProperties(BlockState from, BlockState to) {
      return to;
   }
}
