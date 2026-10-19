package org.anti_ad.mc.ipnext.inventory;

import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

public class AdvancedContainer$AdvancedContainerDsl {
   public AdvancedContainer$AdvancedContainerDsl(AdvancedContainer var1) {
      this.this$0 = var1;
   }

   @NotNull
   public final ItemArea get(@NotNull AreaType var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      return var1.getItemArea(this.this$0.getVanillaContainer(), AdvancedContainer.access$getVanillaSlots(this.this$0));
   }
}
