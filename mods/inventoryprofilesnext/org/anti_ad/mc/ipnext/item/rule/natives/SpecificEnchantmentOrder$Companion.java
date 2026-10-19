package org.anti_ad.mc.ipnext.item.rule.natives;

import java.util.List;
import java.util.Set;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

final class SpecificEnchantmentOrder$Companion {
   private SpecificEnchantmentOrder$Companion() {
   }

   @NotNull
   public final List sortByListOrder(@NotNull Set var1, @NotNull List var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      List var3;
      CollectionsKt.sortWith(
         var3 = CollectionsKt.toMutableList(var1), new SpecificEnchantmentOrder$Companion$sortByListOrder$lambda$1$$inlined$compareBy$1(var2)
      );
      return var3;
   }
}
