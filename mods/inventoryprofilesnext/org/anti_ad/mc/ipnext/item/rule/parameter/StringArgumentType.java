package org.anti_ad.mc.ipnext.item.rule.parameter;

import kotlin.jvm.internal.Intrinsics;
import org.anti_ad.mc.ipnext.item.rule.ArgumentType;
import org.jetbrains.annotations.NotNull;

public final class StringArgumentType implements ArgumentType {
   @NotNull
   public static final StringArgumentType INSTANCE = new StringArgumentType();

   private StringArgumentType() {
   }

   @NotNull
   public final String toString(@NotNull String var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      return var1;
   }

   @NotNull
   public final String parse(@NotNull String var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      return var1;
   }
}
