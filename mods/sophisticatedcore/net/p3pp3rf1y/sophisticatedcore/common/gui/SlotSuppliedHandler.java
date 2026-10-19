package net.p3pp3rf1y.sophisticatedcore.common.gui;

import java.util.function.Supplier;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.SlotItemHandler;
import net.p3pp3rf1y.sophisticatedcore.inventory.ISlotChangeListener;

public class SlotSuppliedHandler extends SlotItemHandler {
   private final Supplier<IItemHandler> itemHandlerSupplier;
   private final int slot;

   public SlotSuppliedHandler(Supplier<IItemHandler> itemHandlerSupplier, int slot, int xPosition, int yPosition) {
      super(itemHandlerSupplier.get(), slot, xPosition, yPosition);
      this.itemHandlerSupplier = itemHandlerSupplier;
      this.slot = slot;
   }

   public IItemHandler getItemHandler() {
      return this.itemHandlerSupplier.get();
   }

   public boolean mayPlace(ItemStack stack) {
      return this.itemHandlerSupplier.get().isItemValid(this.slot, stack);
   }

   public int getMaxStackSize() {
      return this.itemHandlerSupplier.get().getSlotLimit(this.slot);
   }

   public void setChanged() {
      super.setChanged();
      if (this.itemHandlerSupplier.get() instanceof ISlotChangeListener contentsChangeListener) {
         contentsChangeListener.onSlotChanged(this.slot);
      }
   }
}
