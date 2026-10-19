package org.anti_ad.mc.ipnext.inventory.data;

import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

public final class ExtensionsKt {
   @NotNull
   public static final ItemBucket collect(@NotNull ItemTracker var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      MutableItemBucket var1;
      MutableItemBucket var2;
      (var2 = var1 = var0.getThrownItems().copyAsMutable()).add(var0.getCursor());
      var2.addAll(var0.getSlots());
      return var1;
   }
}
