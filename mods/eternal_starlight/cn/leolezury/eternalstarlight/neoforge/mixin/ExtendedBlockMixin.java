package cn.leolezury.eternalstarlight.neoforge.mixin;

import cn.leolezury.eternalstarlight.common.block.ExtendedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.extensions.IBlockExtension;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ExtendedBlock.class)
public interface ExtendedBlockMixin extends IBlockExtension {
   @Shadow
   boolean isSticky(BlockState var1);

   @Shadow
   boolean canStickToEachOther(BlockState var1, BlockState var2);

   default boolean isStickyBlock(BlockState state) {
      return this.isSticky(state);
   }

   default boolean canStickTo(BlockState state, BlockState other) {
      return this.canStickToEachOther(state, other);
   }
}
