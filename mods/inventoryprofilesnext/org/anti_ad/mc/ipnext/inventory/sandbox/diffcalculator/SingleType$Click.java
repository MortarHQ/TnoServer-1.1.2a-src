package org.anti_ad.mc.ipnext.inventory.sandbox.diffcalculator;

import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class SingleType$Click {
   private final int clickIndex;
   @NotNull
   private final SingleType$Slot slot;
   @NotNull
   private final SingleType$Button button;
   @Nullable
   private final SingleType$Click previousClick;

   public SingleType$Click(int var1, @NotNull SingleType$Slot var2, @NotNull SingleType$Button var3, @Nullable SingleType$Click var4) {
      Intrinsics.checkNotNullParameter(var2, "");
      Intrinsics.checkNotNullParameter(var3, "");
      super();
      this.clickIndex = var1;
      this.slot = var2;
      this.button = var3;
      this.previousClick = var4;
   }

   public final int getClickIndex() {
      return this.clickIndex;
   }

   @NotNull
   public final SingleType$Slot getSlot() {
      return this.slot;
   }

   @NotNull
   public final SingleType$Button getButton() {
      return this.button;
   }

   @Nullable
   public final SingleType$Click getPreviousClick() {
      return this.previousClick;
   }

   public final int component1() {
      return this.clickIndex;
   }

   @NotNull
   public final SingleType$Slot component2() {
      return this.slot;
   }

   @NotNull
   public final SingleType$Button component3() {
      return this.button;
   }

   @Nullable
   public final SingleType$Click component4() {
      return this.previousClick;
   }

   @NotNull
   public final SingleType$Click copy(int var1, @NotNull SingleType$Slot var2, @NotNull SingleType$Button var3, @Nullable SingleType$Click var4) {
      Intrinsics.checkNotNullParameter(var2, "");
      Intrinsics.checkNotNullParameter(var3, "");
      return new SingleType$Click(var1, var2, var3, var4);
   }

   @NotNull
   @Override
   public final String toString() {
      return "Click(clickIndex=" + this.clickIndex + ", slot=" + this.slot + ", button=" + this.button + ", previousClick=" + this.previousClick + ")";
   }

   @Override
   public final int hashCode() {
      return ((Integer.hashCode(this.clickIndex) * 31 + this.slot.hashCode()) * 31 + this.button.hashCode()) * 31
         + (this.previousClick == null ? 0 : this.previousClick.hashCode());
   }

   @Override
   public final boolean equals(@Nullable Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof SingleType$Click var2)) {
         return false;
      } else if (this.clickIndex != var2.clickIndex) {
         return false;
      } else if (!Intrinsics.areEqual(this.slot, var2.slot)) {
         return false;
      } else {
         return this.button != var2.button ? false : Intrinsics.areEqual(this.previousClick, var2.previousClick);
      }
   }
}
