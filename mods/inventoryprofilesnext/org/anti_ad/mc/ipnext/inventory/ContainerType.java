package org.anti_ad.mc.ipnext.inventory;

import kotlin.enums.EnumEntries;
import org.jetbrains.annotations.NotNull;

public enum ContainerType {
   PLAYER,
   CREATIVE,
   PURE_BACKPACK,
   TEMP_SLOTS,
   NO_SORTING_STORAGE,
   SORTABLE_STORAGE,
   HORSE_STORAGE,
   CRAFTING,
   STONECUTTER,
   TRADER,
   ANVIL,
   RECTANGULAR,
   WIDTH_9,
   HEIGHT_3,
   CUSTOM;

   @NotNull
   public static EnumEntries getEntries() {
      return $ENTRIES;
   }
}
