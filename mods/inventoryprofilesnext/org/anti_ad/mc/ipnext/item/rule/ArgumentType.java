package org.anti_ad.mc.ipnext.item.rule;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ArgumentType {
   @NotNull
   String toString(@NotNull Object var1);

   @Nullable
   Object parse(@NotNull String var1);
}
