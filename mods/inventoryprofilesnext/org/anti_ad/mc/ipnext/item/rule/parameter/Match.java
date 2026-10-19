package org.anti_ad.mc.ipnext.item.rule.parameter;

import kotlin.enums.EnumEntries;
import org.jetbrains.annotations.NotNull;

public enum Match {
   FIRST(1),
   LAST(-1);

   private final int multiplier;

   Match(int var3) {
      this.multiplier = var3;
   }

   public final int getMultiplier() {
      return this.multiplier;
   }

   @NotNull
   public static EnumEntries getEntries() {
      return $ENTRIES;
   }
}
