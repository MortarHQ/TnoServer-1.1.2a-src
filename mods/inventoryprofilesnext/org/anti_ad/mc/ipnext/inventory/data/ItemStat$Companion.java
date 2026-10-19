package org.anti_ad.mc.ipnext.inventory.data;

import java.util.List;
import kotlin.jvm.internal.Intrinsics;
import org.anti_ad.mc.common.extensions.Kt_collectionKt;
import org.jetbrains.annotations.NotNull;

public final class ItemStat$Companion {
   private ItemStat$Companion() {
   }

   @NotNull
   public final ItemStat invoke(@NotNull List var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      return new ItemStat(Kt_collectionKt.indexed(var1));
   }
}
