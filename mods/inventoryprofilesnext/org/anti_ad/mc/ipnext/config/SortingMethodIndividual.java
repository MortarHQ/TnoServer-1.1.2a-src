package org.anti_ad.mc.ipnext.config;

import java.util.Locale;
import kotlin.enums.EnumEntries;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.anti_ad.mc.common.vanilla.alias.glue.I18n;
import org.jetbrains.annotations.NotNull;

public enum SortingMethodIndividual {
   GLOBAL,
   DEFAULT,
   ITEM_NAME,
   ITEM_ID,
   RAW_ID,
   CUSTOM;

   @NotNull
   @Override
   public final String toString() {
      if (this == GLOBAL) {
         String var1 = ((SortingMethod)ModSettings.INSTANCE.getSORT_ORDER().getValue()).toString();
         I18n var3 = I18n.INSTANCE;
         Object[] var2;
         (var2 = new Object[1])[0] = StringsKt.trim(StringsKt.substringBefore$default(var1, '(', null, 2, null)).toString();
         return var3.translate("inventoryprofiles.enum.sorting_method.global", var2);
      } else {
         I18n var10000 = I18n.INSTANCE;
         String var10001 = this.name().toLowerCase(Locale.ROOT);
         Intrinsics.checkNotNullExpressionValue(var10001, "");
         return var10000.translate("inventoryprofiles.enum.sorting_method." + var10001, new Object[0]);
      }
   }

   @NotNull
   public static EnumEntries getEntries() {
      return $ENTRIES;
   }
}
