package org.anti_ad.mc.ipnext.item.rule.parameter;

import java.util.Comparator;
import kotlin.comparisons.ComparisonsKt;
import kotlin.enums.EnumEntries;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public enum StringCompare {
   UNICODE(ComparisonsKt.naturalOrder()),
   IGNORE_CASE(StringsKt.getCASE_INSENSITIVE_ORDER(StringCompanionObject.INSTANCE)),
   LOCALE(null);

   @Nullable
   private final Comparator comparator;

   StringCompare(Comparator var3) {
      this.comparator = var3;
   }

   @Nullable
   public final Comparator getComparator() {
      return this.comparator;
   }

   @NotNull
   public static EnumEntries getEntries() {
      return $ENTRIES;
   }
}
