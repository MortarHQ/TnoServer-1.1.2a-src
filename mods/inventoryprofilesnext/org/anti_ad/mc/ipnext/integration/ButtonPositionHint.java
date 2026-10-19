package org.anti_ad.mc.ipnext.integration;

import kotlinx.serialization.Serializable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Serializable
public final class ButtonPositionHint {
   @NotNull
   public static final ButtonPositionHint$Companion Companion = new ButtonPositionHint$Companion(null);
   private int horizontalOffset;
   private int top;
   private int bottom;
   private boolean hide;
   private boolean dirty;

   public ButtonPositionHint(int var1, int var2, int var3, boolean var4) {
      this.horizontalOffset = var1;
      this.top = var2;
      this.bottom = var3;
      this.hide = var4;
   }

   public final int getHorizontalOffset() {
      return this.horizontalOffset;
   }

   public final void setHorizontalOffset(int var1) {
      this.horizontalOffset = var1;
   }

   public final int getTop() {
      return this.top;
   }

   public final void setTop(int var1) {
      this.top = var1;
   }

   public final int getBottom() {
      return this.bottom;
   }

   public final void setBottom(int var1) {
      this.bottom = var1;
   }

   public final boolean getHide() {
      return this.hide;
   }

   public final void setHide(boolean var1) {
      this.hide = var1;
   }

   public final boolean getDirty() {
      return this.dirty;
   }

   public final void setDirty(boolean var1) {
      this.dirty = var1;
   }

   public final int component1() {
      return this.horizontalOffset;
   }

   public final int component2() {
      return this.top;
   }

   public final int component3() {
      return this.bottom;
   }

   public final boolean component4() {
      return this.hide;
   }

   @NotNull
   public final ButtonPositionHint copy(int var1, int var2, int var3, boolean var4) {
      return new ButtonPositionHint(var1, var2, var3, var4);
   }

   @NotNull
   @Override
   public final String toString() {
      return "ButtonPositionHint(horizontalOffset=" + this.horizontalOffset + ", top=" + this.top + ", bottom=" + this.bottom + ", hide=" + this.hide + ")";
   }

   @Override
   public final int hashCode() {
      return ((Integer.hashCode(this.horizontalOffset) * 31 + Integer.hashCode(this.top)) * 31 + Integer.hashCode(this.bottom)) * 31
         + Boolean.hashCode(this.hide);
   }

   @Override
   public final boolean equals(@Nullable Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof ButtonPositionHint var2)) {
         return false;
      } else if (this.horizontalOffset != var2.horizontalOffset) {
         return false;
      } else if (this.top != var2.top) {
         return false;
      } else {
         return this.bottom != var2.bottom ? false : this.hide == var2.hide;
      }
   }

   public ButtonPositionHint() {
      this(0, 0, 0, false, 15, null);
   }
}
