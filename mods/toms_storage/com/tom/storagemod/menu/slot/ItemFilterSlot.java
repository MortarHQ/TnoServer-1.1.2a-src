package com.tom.storagemod.menu.slot;

import com.tom.storagemod.item.IItemFilter;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;

public class ItemFilterSlot extends Slot {
   public ItemFilterSlot(Container inv, int slotIndex, int posX, int posY) {
      super(inv, slotIndex, posX, posY);
   }

   public boolean mayPickup(Player p_82869_1_) {
      return this.getItem().getItem() instanceof IItemFilter;
   }

   public int getMaxStackSize() {
      return 1;
   }
}
