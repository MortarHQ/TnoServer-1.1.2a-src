package org.anti_ad.mc.ipnext.inventory.data;

import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.anti_ad.mc.ipnext.item.MutableItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class MutableItemTracker implements ItemTracker {
   @NotNull
   private final MutableItemStack cursor;
   @NotNull
   private final List slots;
   @NotNull
   private final MutableItemBucket thrownItems;

   public MutableItemTracker(@NotNull MutableItemStack var1, @NotNull List var2, @NotNull MutableItemBucket var3) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      Intrinsics.checkNotNullParameter(var3, "");
      super();
      this.cursor = var1;
      this.slots = var2;
      this.thrownItems = var3;
   }

   @NotNull
   public final MutableItemStack getCursor() {
      return this.cursor;
   }

   @NotNull
   @Override
   public final List getSlots() {
      return this.slots;
   }

   @NotNull
   public final MutableItemBucket getThrownItems() {
      return this.thrownItems;
   }

   @NotNull
   @Override
   public final MutableItemTracker copyAsMutable() {
      return new MutableItemTracker(
         this.getCursor().copyAsMutable(), ItemStackListExtensionsKt.copyAsMutable(this.getSlots()), this.getThrownItems().copyAsMutable()
      );
   }

   @NotNull
   @Override
   public final String toString() {
      return "cursor: " + this.getCursor() + ", slots: " + this.getSlots() + ", thrownItems: " + this.getThrownItems();
   }

   @Override
   public final boolean equals(@Nullable Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof ItemTracker)) {
         return false;
      } else if (!Intrinsics.areEqual(this.getCursor(), ((ItemTracker)var1).getCursor())) {
         return false;
      } else {
         return !Intrinsics.areEqual(this.getSlots(), ((ItemTracker)var1).getSlots())
            ? false
            : Intrinsics.areEqual(this.getThrownItems(), ((ItemTracker)var1).getThrownItems());
      }
   }

   @Override
   public final int hashCode() {
      return (this.getCursor().hashCode() * 31 + this.getSlots().hashCode()) * 31 + this.getThrownItems().hashCode();
   }

   @NotNull
   public final MutableSubTracker subTracker() {
      return this.subTracker(CollectionsKt.toList((Iterable)CollectionsKt.getIndices(this.getSlots())));
   }

   @NotNull
   public final MutableSubTracker subTracker(@NotNull List var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      return new MutableSubTracker(this, var1);
   }
}
