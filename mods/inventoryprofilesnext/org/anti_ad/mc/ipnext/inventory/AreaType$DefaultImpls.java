package org.anti_ad.mc.ipnext.inventory;

import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

public final class AreaType$DefaultImpls {
   @NotNull
   public static AreaType plus(@NotNull AreaType var0, @NotNull AreaType var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      return new AreaType$plus$1(var0, var1);
   }

   @NotNull
   public static AreaType minus(@NotNull AreaType var0, @NotNull AreaType var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      return new AreaType$minus$1(var0, var1);
   }
}
