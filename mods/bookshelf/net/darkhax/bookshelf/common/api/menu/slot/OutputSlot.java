package net.darkhax.bookshelf.common.api.menu.slot;

import java.util.function.BiConsumer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class OutputSlot extends Slot {
   @Nullable
   private final BiConsumer<Player, ItemStack> takeFunc;

   public OutputSlot(Container potContainer, int slot, int x, int y) {
      this(potContainer, slot, x, y, null);
   }

   public OutputSlot(Container potContainer, int slot, int x, int y, @Nullable BiConsumer<Player, ItemStack> takeFunc) {
      super(potContainer, slot, x, y);
      this.takeFunc = takeFunc;
   }

   public boolean mayPlace(@NotNull ItemStack stack) {
      return false;
   }

   public void onTake(@NotNull Player player, @NotNull ItemStack stack) {
      super.onTake(player, stack);
      if (this.takeFunc != null) {
         this.takeFunc.accept(player, stack);
      }
   }
}
