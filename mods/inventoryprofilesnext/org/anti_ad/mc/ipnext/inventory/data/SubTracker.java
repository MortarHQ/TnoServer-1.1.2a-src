package org.anti_ad.mc.ipnext.inventory.data;

import java.util.List;
import org.jetbrains.annotations.NotNull;

public interface SubTracker {
   @NotNull
   ItemTracker getMainTracker();

   @NotNull
   List getSlotIndices();

   @NotNull
   List getSlots();

   @NotNull
   List getIndexedSlots();

   @NotNull
   SubTracker plus(@NotNull SubTracker var1);
}
