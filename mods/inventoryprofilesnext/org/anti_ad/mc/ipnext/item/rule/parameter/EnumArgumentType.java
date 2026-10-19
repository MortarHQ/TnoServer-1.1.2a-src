package org.anti_ad.mc.ipnext.item.rule.parameter;

import java.util.Locale;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.anti_ad.mc.ipnext.item.rule.ArgumentType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SourceDebugExtension(
   "SMAP\nNativeArgumentTypes.kt\nKotlin\n*S Kotlin\n*F\n+ 1 NativeArgumentTypes.kt\norg/anti_ad/mc/ipnext/item/rule/parameter/EnumArgumentType\n+ 2 common.kt\norg/anti_ad/mc/common/extensions/CommonKt\n*L\n1#1,83:1\n33#2:84\n89#2,4:85\n34#2:89\n*S KotlinDebug\n*F\n+ 1 NativeArgumentTypes.kt\norg/anti_ad/mc/ipnext/item/rule/parameter/EnumArgumentType\n*L\n53#1:84\n53#1:85,4\n53#1:89\n*E\n"
)
public final class EnumArgumentType implements ArgumentType {
   @NotNull
   private final Class enumClass;

   public EnumArgumentType(@NotNull Class var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      super();
      this.enumClass = var1;
   }

   @NotNull
   public final Class getEnumClass() {
      return this.enumClass;
   }

   @NotNull
   public final String toString(@NotNull Enum var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      String var10000 = var1.name().toLowerCase(Locale.ROOT);
      Intrinsics.checkNotNullExpressionValue(var10000, "");
      return var10000;
   }

   @Nullable
   public final Enum parse(@NotNull String var1) {
      Intrinsics.checkNotNullParameter(var1, "");

      try {
         Class var10000 = this.enumClass;
         String var10001 = var1.toUpperCase(Locale.ROOT);
         Intrinsics.checkNotNullExpressionValue(var10001, "");
         var3 = Enum.valueOf(var10000, var10001);
      } catch (Throwable var2) {
         var3 = null;
      }

      return var3;
   }
}
