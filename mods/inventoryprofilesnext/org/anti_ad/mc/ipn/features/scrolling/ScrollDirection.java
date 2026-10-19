package org.anti_ad.mc.ipn.features.scrolling;

import kotlin.enums.EnumEntries;
import org.jetbrains.annotations.NotNull;

public enum ScrollDirection {
   TO_CHEST,
   TO_PLAYER,
   BOTH;

   @NotNull
   public static EnumEntries getEntries() {
      return $ENTRIES;
   }
}
