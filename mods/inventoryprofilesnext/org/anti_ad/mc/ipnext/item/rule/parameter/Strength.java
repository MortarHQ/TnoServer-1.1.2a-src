package org.anti_ad.mc.ipnext.item.rule.parameter;

import kotlin.enums.EnumEntries;
import org.jetbrains.annotations.NotNull;

public enum Strength {
   PRIMARY(0),
   SECONDARY(1),
   TERTIARY(2),
   IDENTICAL(3);

   private final int value;

   Strength(int var3) {
      this.value = var3;
   }

   public final int getValue() {
      return this.value;
   }

   @NotNull
   public static EnumEntries getEntries() {
      return $ENTRIES;
   }
}
