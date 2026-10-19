package org.anti_ad.mc.ipnext.config;

import java.util.Locale;
import kotlin.enums.EnumEntries;
import kotlin.jvm.internal.Intrinsics;
import org.anti_ad.mc.common.vanilla.alias.glue.I18n;
import org.jetbrains.annotations.NotNull;

public enum PostAction {
   NONE,
   GROUP_IN_ROWS,
   GROUP_IN_COLUMNS,
   DISTRIBUTE_EVENLY,
   SHUFFLE,
   FILL_ONE,
   REVERSE;

   @NotNull
   @Override
   public final String toString() {
      I18n var10000 = I18n.INSTANCE;
      String var10001 = this.name().toLowerCase(Locale.ROOT);
      Intrinsics.checkNotNullExpressionValue(var10001, "");
      return var10000.translate("inventoryprofiles.enum.post_action." + var10001, new Object[0]);
   }

   @NotNull
   public static EnumEntries getEntries() {
      return $ENTRIES;
   }
}
