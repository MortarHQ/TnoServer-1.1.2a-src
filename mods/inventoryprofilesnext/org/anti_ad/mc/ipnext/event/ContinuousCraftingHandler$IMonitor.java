package org.anti_ad.mc.ipnext.event;

import java.util.List;
import net.minecraft.world.inventory.Slot;
import org.anti_ad.mc.ipnext.item.ItemStack;
import org.jetbrains.annotations.NotNull;

interface ContinuousCraftingHandler$IMonitor {
   @NotNull
   List getContainerSlots();

   @NotNull
   List getIngredientSlots();

   @NotNull
   List getSlotMonitors();

   @NotNull
   List getPlayerSlotIndices();

   boolean shouldHandle(@NotNull ItemStack var1, @NotNull ItemStack var2, @NotNull Slot var3);

   boolean autoRefill();

   void save();
}
