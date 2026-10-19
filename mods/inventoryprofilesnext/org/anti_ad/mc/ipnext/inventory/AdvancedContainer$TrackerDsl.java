package org.anti_ad.mc.ipnext.inventory;

import kotlin.jvm.internal.Intrinsics;
import org.anti_ad.mc.ipnext.inventory.data.MutableItemTracker;
import org.anti_ad.mc.ipnext.inventory.data.MutableSubTracker;
import org.jetbrains.annotations.NotNull;

public final class AdvancedContainer$TrackerDsl extends AdvancedContainer$AdvancedContainerDsl {
   @NotNull
   private final MutableItemTracker tracker;

   public AdvancedContainer$TrackerDsl(@NotNull AdvancedContainer var1, MutableItemTracker var2) {
      Intrinsics.checkNotNullParameter(var2, "");
      this.this$0 = var1;
      super(var1);
      this.tracker = var2;
   }

   @NotNull
   public final MutableItemTracker getTracker() {
      return this.tracker;
   }

   @NotNull
   public final MutableSubTracker getAsSubTracker(@NotNull ItemArea var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      return this.tracker.subTracker(var1.getSlotIndices());
   }
}
