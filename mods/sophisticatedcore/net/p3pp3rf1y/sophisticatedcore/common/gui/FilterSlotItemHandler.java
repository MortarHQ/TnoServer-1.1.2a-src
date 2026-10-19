package net.p3pp3rf1y.sophisticatedcore.common.gui;

import java.util.function.Supplier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;

public class FilterSlotItemHandler extends SlotSuppliedHandler implements IFilterSlot {
   public FilterSlotItemHandler(Supplier<IItemHandler> itemHandlerSupplier, int slot, int xPosition, int yPosition) {
      super(itemHandlerSupplier, slot, xPosition, yPosition);
   }

   public boolean mayPickup(Player playerIn) {
      return false;
   }

   public int getMaxStackSize(ItemStack stack) {
      return 1;
   }
}
