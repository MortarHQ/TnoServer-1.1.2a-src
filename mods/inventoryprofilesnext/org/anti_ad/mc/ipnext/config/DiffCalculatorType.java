package org.anti_ad.mc.ipnext.config;

import kotlin.enums.EnumEntries;
import org.jetbrains.annotations.NotNull;

public enum DiffCalculatorType {
   SIMPLE,
   SCORE_BASED_SINGLE,
   SCORE_BASED_DUAL;

   @NotNull
   public static EnumEntries getEntries() {
      return $ENTRIES;
   }
}
