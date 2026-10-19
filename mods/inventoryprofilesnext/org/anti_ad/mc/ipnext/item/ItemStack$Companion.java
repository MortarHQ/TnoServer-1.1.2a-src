package org.anti_ad.mc.ipnext.item;

import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

public final class ItemStack$Companion {
   private ItemStack$Companion() {
   }

   @NotNull
   public final ItemStack invoke(@NotNull ItemType var1, int var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      return new ImmutableItemStack(var1, var2, null, 4, null);
   }
}
