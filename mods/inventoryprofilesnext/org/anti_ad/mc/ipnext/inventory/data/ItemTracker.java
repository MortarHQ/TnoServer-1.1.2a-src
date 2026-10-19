package org.anti_ad.mc.ipnext.inventory.data;

import java.util.List;
import org.anti_ad.mc.ipnext.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface ItemTracker {
   @NotNull
   ItemStack getCursor();

   @NotNull
   List getSlots();

   @NotNull
   ItemBucket getThrownItems();

   @NotNull
   MutableItemTracker copyAsMutable();

   @NotNull
   SubTracker subTracker();

   @NotNull
   SubTracker subTracker(@NotNull List var1);
}
