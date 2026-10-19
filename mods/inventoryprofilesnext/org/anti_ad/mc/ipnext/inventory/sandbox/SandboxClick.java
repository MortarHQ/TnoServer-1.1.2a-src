package org.anti_ad.mc.ipnext.inventory.sandbox;

import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class SandboxClick {
   private final int clickIndex;
   private final int slotIndex;
   private final int button;
   @Nullable
   private final SandboxClick previousClick;

   public SandboxClick(int var1, int var2, int var3, @Nullable SandboxClick var4) {
      this.clickIndex = var1;
      this.slotIndex = var2;
      this.button = var3;
      this.previousClick = var4;
   }

   public final int getClickIndex() {
      return this.clickIndex;
   }

   public final int getSlotIndex() {
      return this.slotIndex;
   }

   public final int getButton() {
      return this.button;
   }

   @Nullable
   public final SandboxClick getPreviousClick() {
      return this.previousClick;
   }

   public final int component1() {
      return this.clickIndex;
   }

   public final int component2() {
      return this.slotIndex;
   }

   public final int component3() {
      return this.button;
   }

   @Nullable
   public final SandboxClick component4() {
      return this.previousClick;
   }

   @NotNull
   public final SandboxClick copy(int var1, int var2, int var3, @Nullable SandboxClick var4) {
      return new SandboxClick(var1, var2, var3, var4);
   }

   @NotNull
   @Override
   public final String toString() {
      return "SandboxClick(clickIndex="
         + this.clickIndex
         + ", slotIndex="
         + this.slotIndex
         + ", button="
         + this.button
         + ", previousClick="
         + this.previousClick
         + ")";
   }

   @Override
   public final int hashCode() {
      return ((Integer.hashCode(this.clickIndex) * 31 + Integer.hashCode(this.slotIndex)) * 31 + Integer.hashCode(this.button)) * 31
         + (this.previousClick == null ? 0 : this.previousClick.hashCode());
   }

   @Override
   public final boolean equals(@Nullable Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof SandboxClick var2)) {
         return false;
      } else if (this.clickIndex != var2.clickIndex) {
         return false;
      } else if (this.slotIndex != var2.slotIndex) {
         return false;
      } else {
         return this.button != var2.button ? false : Intrinsics.areEqual(this.previousClick, var2.previousClick);
      }
   }
}
