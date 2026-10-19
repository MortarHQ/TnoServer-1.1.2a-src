package org.anti_ad.mc.ipnext.item.rule.parameter;

import java.util.Comparator;
import kotlin.comparisons.ComparisonsKt;
import kotlin.enums.EnumEntries;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

public enum NumberOrder implements Comparator {
   ASCENDING(ComparisonsKt.naturalOrder()),
   DESCENDING(ComparisonsKt.reverseOrder());

   @NotNull
   private final Comparator comparator;

   NumberOrder(Comparator var3) {
      this.comparator = var3;
   }

   public final int compare(@NotNull Number var1, @NotNull Number var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      return this.comparator.compare(var1.doubleValue(), var2.doubleValue());
   }

   @NotNull
   public static EnumEntries getEntries() {
      return $ENTRIES;
   }
}
