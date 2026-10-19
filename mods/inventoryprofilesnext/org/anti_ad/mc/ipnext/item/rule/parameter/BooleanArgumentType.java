package org.anti_ad.mc.ipnext.item.rule.parameter;

import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.anti_ad.mc.ipnext.item.rule.ArgumentType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SourceDebugExtension(
   "SMAP\nNativeArgumentTypes.kt\nKotlin\n*S Kotlin\n*F\n+ 1 NativeArgumentTypes.kt\norg/anti_ad/mc/ipnext/item/rule/parameter/BooleanArgumentType\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,83:1\n1#2:84\n*E\n"
)
public final class BooleanArgumentType implements ArgumentType {
   @NotNull
   public static final BooleanArgumentType INSTANCE = new BooleanArgumentType();

   private BooleanArgumentType() {
   }

   @NotNull
   public final String toString(boolean var1) {
      return String.valueOf(var1);
   }

   @Nullable
   public final Boolean parse(@NotNull String var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      String var2 = var1;
      String var10000 = Intrinsics.areEqual(var1, "true") || Intrinsics.areEqual(var2, "false") ? var1 : null;
      return var10000 != null ? Boolean.parseBoolean(var10000) : null;
   }
}
