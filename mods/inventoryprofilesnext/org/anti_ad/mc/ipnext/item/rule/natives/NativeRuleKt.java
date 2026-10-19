package org.anti_ad.mc.ipnext.item.rule.natives;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.anti_ad.mc.ipnext.item.rule.parameter.Match;
import org.jetbrains.annotations.NotNull;

public final class NativeRuleKt {
   public static final int compareByMatch(Object var0, Object var1, @NotNull Function1 var2, @NotNull Match var3, @NotNull Function2 var4) {
      Intrinsics.checkNotNullParameter(var2, "");
      Intrinsics.checkNotNullParameter(var3, "");
      Intrinsics.checkNotNullParameter(var4, "");
      boolean var5 = (Boolean)var2.invoke(var0);
      boolean var6 = (Boolean)var2.invoke(var1);
      return var5 == var6 ? ((Number)var4.invoke(var0, var1)).intValue() : var3.getMultiplier() * (var5 ? -1 : 1);
   }

   public static final int compareByMatchSeparate(
      Object var0, Object var1, @NotNull Function1 var2, @NotNull Match var3, @NotNull Function2 var4, @NotNull Function2 var5
   ) {
      Intrinsics.checkNotNullParameter(var2, "");
      Intrinsics.checkNotNullParameter(var3, "");
      Intrinsics.checkNotNullParameter(var4, "");
      Intrinsics.checkNotNullParameter(var5, "");
      return compareByMatchSeparate(var0, var1, (Boolean)var2.invoke(var0), (Boolean)var2.invoke(var1), var3, var4, var5);
   }

   public static final int compareByMatchSeparate(
      Object var0, Object var1, boolean var2, boolean var3, @NotNull Match var4, @NotNull Function2 var5, @NotNull Function2 var6
   ) {
      Intrinsics.checkNotNullParameter(var4, "");
      Intrinsics.checkNotNullParameter(var5, "");
      Intrinsics.checkNotNullParameter(var6, "");
      if (var2 == var3) {
         if (var2) {
            var7 = ((Number)var5.invoke(var0, var1)).intValue();
         } else {
            var7 = ((Number)var6.invoke(var0, var1)).intValue();
         }
      } else {
         var7 = var4.getMultiplier() * (var2 ? -1 : 1);
      }

      return var7;
   }

   private static final int compareByMatchSeparate$lambda$0(Object var0, Object var1) {
      return 0;
   }

   private static final int compareByMatchSeparate$lambda$1(Object var0, Object var1) {
      return 0;
   }

   private static final int compareByMatchSeparate$lambda$2(Object var0, Object var1) {
      return 0;
   }

   private static final int compareByMatchSeparate$lambda$3(Object var0, Object var1) {
      return 0;
   }
}
