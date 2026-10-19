package org.anti_ad.mc.ipnext.inventory;

import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

public final class ItemArea$Companion {
   private ItemArea$Companion() {
   }

   private final ItemArea plus(ItemArea var1, ItemArea var2) {
      return new ItemArea(
         ItemArea.access$getFromSlotLocations$p(var1),
         CollectionsKt.distinct(CollectionsKt.plus(var1.getSlotIndices(), var2.getSlotIndices())),
         var1.getOrderSensitive() || var2.getOrderSensitive()
      );
   }

   private final ItemArea minus(ItemArea var1, ItemArea var2) {
      return new ItemArea(
         ItemArea.access$getFromSlotLocations$p(var1),
         CollectionsKt.distinct(CollectionsKt.minus(var1.getSlotIndices(), var2.getSlotIndices())),
         var1.getOrderSensitive() || var2.getOrderSensitive()
      );
   }

   @NotNull
   public final ItemArea invoke(@NotNull List var1, @NotNull List var2, boolean var3) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      return new ItemArea(AreaTypesKt.access$toPointList(var1), var2, var3);
   }
}
