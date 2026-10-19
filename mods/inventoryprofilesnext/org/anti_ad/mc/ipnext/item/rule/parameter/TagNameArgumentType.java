package org.anti_ad.mc.ipnext.item.rule.parameter;

import kotlin.jvm.internal.Intrinsics;
import org.anti_ad.mc.ipnext.item.rule.ArgumentType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class TagNameArgumentType implements ArgumentType {
   @NotNull
   public static final TagNameArgumentType INSTANCE = new TagNameArgumentType();

   private TagNameArgumentType() {
   }

   @NotNull
   public final String toString(@NotNull ItemTypeMatcher var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      return var1.toString();
   }

   @Nullable
   public final ItemTypeMatcher parse(@NotNull String var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      return ItemTypeMatcher.Companion.forTag(var1);
   }
}
