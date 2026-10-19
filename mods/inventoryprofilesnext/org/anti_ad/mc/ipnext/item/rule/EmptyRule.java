package org.anti_ad.mc.ipnext.item.rule;

import kotlin.jvm.internal.Intrinsics;
import org.anti_ad.mc.ipnext.item.ItemType;
import org.jetbrains.annotations.NotNull;

public final class EmptyRule implements Rule {
   @NotNull
   public static final EmptyRule INSTANCE = new EmptyRule();

   private EmptyRule() {
   }

   @NotNull
   @Override
   public final ArgumentMap getArguments() {
      return new ArgumentMap();
   }

   @Override
   public final int compare(@NotNull ItemType var1, @NotNull ItemType var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      return 0;
   }
}
