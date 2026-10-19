package org.anti_ad.mc.ipnext.item.rule;

import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

public final class Parameter {
   @NotNull
   private final String name;
   @NotNull
   private final ArgumentType argumentType;

   public Parameter(@NotNull String var1, @NotNull ArgumentType var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      super();
      this.name = var1;
      this.argumentType = var2;
   }

   @NotNull
   public final String getName() {
      return this.name;
   }

   @NotNull
   public final ArgumentType getArgumentType() {
      return this.argumentType;
   }
}
