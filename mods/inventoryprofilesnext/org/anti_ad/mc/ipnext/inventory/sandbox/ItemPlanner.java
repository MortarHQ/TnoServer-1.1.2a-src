package org.anti_ad.mc.ipnext.inventory.sandbox;

import java.util.List;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.anti_ad.mc.common.annotation.MayThrow;
import org.anti_ad.mc.ipnext.inventory.data.ExtensionsKt;
import org.anti_ad.mc.ipnext.inventory.data.ItemTracker;
import org.anti_ad.mc.ipnext.inventory.data.MutableItemTracker;
import org.anti_ad.mc.ipnext.inventory.sandbox.diffcalculator.DiffCalculator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ItemPlanner {
   @NotNull
   private final ContainerSandbox innerSandbox;
   @Nullable
   private ItemTracker trackingItems;

   public ItemPlanner(@NotNull MutableItemTracker var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      super();
      this.innerSandbox = new ContainerSandbox(var1, null, 2, null);
   }

   @MayThrow
   private final void innerSync() {
      if (this.trackingItems != null) {
         ItemTracker var1 = this.trackingItems;
         DiffCalculator.INSTANCE.apply(this.innerSandbox, var1);
         if (!Intrinsics.areEqual(this.innerSandbox.getItems(), var1)) {
            throw new IllegalStateException("ContainerSandbox actual result not same as goal".toString());
         }

         this.trackingItems = null;
      }
   }

   private final ItemTracker getItemTracker() {
      ItemTracker var10000 = this.trackingItems;
      if (this.trackingItems == null) {
         var10000 = this.innerSandbox.getItems();
      }

      return var10000;
   }

   @MayThrow
   public final void sandbox(@NotNull Function1 var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      this.innerSync();
      var1.invoke(this.innerSandbox);
   }

   @MayThrow
   public final void tracker(@NotNull Function1 var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      int var2 = this.innerSandbox.getClickCount();
      ItemTracker var3 = this.getItemTracker();
      MutableItemTracker var4 = this.getItemTracker().copyAsMutable();
      var1.invoke(var4);
      MutableItemTracker var5 = var4;
      if (var2 != this.innerSandbox.getClickCount()) {
         throw new IllegalStateException(("ContainerSandbox out of sync expected " + var2 + " current " + this.innerSandbox.getClickCount()).toString());
      }

      if (!Intrinsics.areEqual(ExtensionsKt.collect(var3), ExtensionsKt.collect(var5))) {
         throw new IllegalStateException("Unequal before and after item counts".toString());
      }

      this.trackingItems = var5;
   }

   @MayThrow
   @NotNull
   public final List getClicks() {
      this.innerSync();
      return ContainerSandboxKt.toList(this.innerSandbox.getClickNode());
   }
}
