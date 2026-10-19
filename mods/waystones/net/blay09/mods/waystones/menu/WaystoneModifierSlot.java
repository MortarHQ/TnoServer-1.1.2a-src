package net.blay09.mods.waystones.menu;

import net.blay09.mods.waystones.item.ModItems;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class WaystoneModifierSlot extends Slot {
   public WaystoneModifierSlot(Container container, int slot, int x, int y) {
      super(container, slot, x, y);
   }

   public int getMaxStackSize(ItemStack itemStack) {
      return itemStack.is(ModItems.dormantShard) ? 1 : super.getMaxStackSize(itemStack);
   }

   public boolean mayPlace(ItemStack itemStack) {
      return itemStack.is(ModItems.dormantShard) ? this.getContainerSlot() == 0 : super.mayPlace(itemStack);
   }
}
