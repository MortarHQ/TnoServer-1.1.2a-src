package org.anti_ad.mc.ipnext.item.rule.file;

import kotlin.enums.EnumEntries;
import org.jetbrains.annotations.NotNull;

public enum RuleDefinition$Status {
   LAZY,
   INITIALIZING,
   SUCCESS,
   FAILED;

   @NotNull
   public static EnumEntries getEntries() {
      return $ENTRIES;
   }
}
