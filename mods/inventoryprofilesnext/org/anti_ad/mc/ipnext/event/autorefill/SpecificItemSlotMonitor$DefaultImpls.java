package org.anti_ad.mc.ipnext.event.autorefill;

import kotlin.jvm.internal.Intrinsics;
import net.minecraft.core.DefaultedRegistry;
import net.minecraft.core.Holder;
import net.minecraft.tags.TagKey;
import org.jetbrains.annotations.NotNull;

public final class SpecificItemSlotMonitor$DefaultImpls {
   @NotNull
   public static Holder getEntry(@NotNull SpecificItemSlotMonitor var0, @NotNull DefaultedRegistry var1, Object var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      Holder var10000 = var1.wrapAsHolder(var2);
      Intrinsics.checkNotNullExpressionValue(var10000, "");
      return var10000;
   }

   public static boolean isIn(@NotNull SpecificItemSlotMonitor var0, @NotNull Holder var1, @NotNull TagKey var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      return var1.is(var2);
   }
}
