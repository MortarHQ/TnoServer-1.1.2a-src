package org.anti_ad.mc.ipnext.event;

import net.minecraft.world.inventory.Slot;
import org.anti_ad.mc.ipnext.item.ItemStack;
import org.jetbrains.annotations.NotNull;

interface ContinuousCraftingHandler$IItemSlotMonitor {
   @NotNull
   Slot getSlot();

   @NotNull
   ItemStack getStoredItem();

   void setStoredItem(@NotNull ItemStack var1);

   void save();
}
