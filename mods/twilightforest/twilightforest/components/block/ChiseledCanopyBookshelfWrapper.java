package twilightforest.components.block;

import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.wrapper.InvWrapper;
import twilightforest.block.ChiseledCanopyShelfBlock;
import twilightforest.block.entity.bookshelf.ChiseledCanopyShelfBlockEntity;

public class ChiseledCanopyBookshelfWrapper extends InvWrapper {
   public ChiseledCanopyBookshelfWrapper(ChiseledCanopyShelfBlockEntity inv) {
      super(inv);
   }

   public ItemStack extractItem(int slot, int amount, boolean simulate) {
      return ((ChiseledCanopyShelfBlockEntity)this.getInv()).getBlockState().getValue(ChiseledCanopyShelfBlock.SPAWNER)
         ? ItemStack.EMPTY
         : super.extractItem(slot, amount, simulate);
   }
}
