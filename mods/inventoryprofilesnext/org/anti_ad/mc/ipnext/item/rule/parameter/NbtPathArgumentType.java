package org.anti_ad.mc.ipnext.item.rule.parameter;

import kotlin.jvm.internal.Intrinsics;
import org.anti_ad.mc.ipnext.item.NbtUtils$NbtPath;
import org.anti_ad.mc.ipnext.item.rule.ArgumentType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class NbtPathArgumentType implements ArgumentType {
   @NotNull
   public static final NbtPathArgumentType INSTANCE = new NbtPathArgumentType();

   private NbtPathArgumentType() {
   }

   @NotNull
   public final String toString(@NotNull NbtUtils$NbtPath var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      return var1.toString();
   }

   @Nullable
   public final NbtUtils$NbtPath parse(@NotNull String var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      return NbtUtils$NbtPath.Companion.of(var1);
   }
}
