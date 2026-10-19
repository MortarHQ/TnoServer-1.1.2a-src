package com.sbjeiindex.emi.transfer;

import java.util.List;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class MenuSlotSource implements EmiInputSource {
   private final Slot slot;

   public MenuSlotSource(Slot slot) {
      this.slot = slot;
   }

   @Override
   public ItemStack getItem() {
      return this.slot.getItem();
   }

   @Override
   public boolean mayPickup(Player player) {
      return this.slot.mayPickup(player);
   }

   @Override
   public boolean isCrafting(List<Slot> crafting) {
      return crafting.contains(this.slot);
   }

   @Override
   public int extract(Player player, int amount, ItemStack current) {
      if (amount > 0 && !current.isEmpty()) {
         ItemStack snapshot = current.copy();
         if (current.getCount() <= amount) {
            int taken = current.getCount();
            this.slot.set(ItemStack.EMPTY);
            this.slot.onTake(player, snapshot);
            return taken;
         } else {
            current.shrink(amount);
            this.slot.onTake(player, snapshot);
            return amount;
         }
      } else {
         return 0;
      }
   }
}
