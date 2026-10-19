package io.github.Memoires.mysticism.menu.slot;

import java.util.function.Predicate;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class FixedItemSlot extends Slot {
   private final Predicate<ItemStack> predicate;

   public FixedItemSlot(Container container, int index, int x, int y, Predicate<ItemStack> predicate) {
      super(container, index, x, y);
      this.predicate = predicate;
   }

   public boolean mayPlace(@NotNull ItemStack stack) {
      return this.predicate.test(stack);
   }

   public void setChanged() {
      boolean wasEmpty = this.getItem().isEmpty();
      super.setChanged();
      if (wasEmpty && !this.getItem().isEmpty()) {
      }
   }

   public int getMaxStackSize() {
      return 1;
   }
}
