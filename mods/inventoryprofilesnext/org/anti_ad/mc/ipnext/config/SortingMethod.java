package org.anti_ad.mc.ipnext.config;

import java.util.Locale;
import kotlin.enums.EnumEntries;
import kotlin.jvm.internal.Intrinsics;
import org.anti_ad.mc.common.vanilla.alias.glue.I18n;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public enum SortingMethod {
   DEFAULT("default"),
   ITEM_NAME("item_name"),
   ITEM_ID("item_id"),
   RAW_ID("raw_id"),
   ACCUMULATED_COUNT_DESCENDING("accumulated_count_descending"),
   ACCUMULATED_COUNT_ASCENDING("accumulated_count_ascending"),
   CUSTOM(null);

   @Nullable
   private final String ruleName;

   SortingMethod(String var3) {
      this.ruleName = var3;
   }

   @Nullable
   public final String getRuleName() {
      return this.ruleName;
   }

   @NotNull
   @Override
   public final String toString() {
      I18n var10000 = I18n.INSTANCE;
      String var10001 = this.name().toLowerCase(Locale.ROOT);
      Intrinsics.checkNotNullExpressionValue(var10001, "");
      return var10000.translate("inventoryprofiles.enum.sorting_method." + var10001, new Object[0]);
   }

   @NotNull
   public static EnumEntries getEntries() {
      return $ENTRIES;
   }
}
