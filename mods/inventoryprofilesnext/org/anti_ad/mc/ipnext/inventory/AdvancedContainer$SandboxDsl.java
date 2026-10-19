package org.anti_ad.mc.ipnext.inventory;

import kotlin.jvm.internal.Intrinsics;
import org.anti_ad.mc.ipnext.inventory.data.ItemTracker;
import org.anti_ad.mc.ipnext.inventory.data.SubTracker;
import org.anti_ad.mc.ipnext.inventory.sandbox.ContainerSandbox;
import org.jetbrains.annotations.NotNull;

public final class AdvancedContainer$SandboxDsl extends AdvancedContainer$AdvancedContainerDsl {
   @NotNull
   private final ContainerSandbox sandbox;

   public AdvancedContainer$SandboxDsl(@NotNull AdvancedContainer var1, ContainerSandbox var2) {
      Intrinsics.checkNotNullParameter(var2, "");
      this.this$0 = var1;
      super(var1);
      this.sandbox = var2;
   }

   @NotNull
   public final ContainerSandbox getSandbox() {
      return this.sandbox;
   }

   @NotNull
   public final ItemTracker getSandboxTracker() {
      return this.sandbox.getItems();
   }

   @NotNull
   public final SubTracker getAsSubTracker(@NotNull ItemArea var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      return this.getSandboxTracker().subTracker(var1.getSlotIndices());
   }
}
