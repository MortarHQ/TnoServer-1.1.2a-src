package org.anti_ad.mc.ipnext.inventory.action;

import kotlin.collections.CollectionsKt;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import org.jetbrains.annotations.NotNull;

public final class GroupInColumnsCalculator$ColumnsCandidate$Cell {
   private final int room;
   private final int rowIndex;
   private final int columnIndex;
   private boolean occupied;

   public GroupInColumnsCalculator$ColumnsCandidate$Cell(GroupInColumnsCalculator$ColumnsCandidate var1, int var2, int var3, int var4) {
      this.this$0 = var1;
      this.room = var2;
      this.rowIndex = var3;
      this.columnIndex = var4;
   }

   public final int getRoom() {
      return this.room;
   }

   public final int getRowIndex() {
      return this.rowIndex;
   }

   public final int getColumnIndex() {
      return this.columnIndex;
   }

   public final boolean getOccupied() {
      return this.occupied;
   }

   public final void setOccupied(boolean var1) {
      this.occupied = var1;
   }

   public final int getIndex() {
      return this.columnIndex * this.this$0.getHeight() + this.rowIndex;
   }

   public final int getSlotX() {
      return CollectionsKt.sumOfInt(CollectionsKt.take(this.this$0.getColumnWidths(), this.columnIndex));
   }

   public final int getSlotY() {
      return this.rowIndex;
   }

   public final int getSlotIndex() {
      return this.getSlotY() * this.this$0.getWidth() + this.getSlotX();
   }

   @NotNull
   public final IntRange getSlotIndices() {
      return RangesKt.until(this.getSlotIndex(), this.getSlotIndex() + this.room);
   }
}
