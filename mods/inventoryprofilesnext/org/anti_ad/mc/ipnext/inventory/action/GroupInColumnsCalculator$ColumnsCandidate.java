package org.anti_ad.mc.ipnext.inventory.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.anti_ad.mc.ipnext.item.ItemType;
import org.jetbrains.annotations.NotNull;

@SourceDebugExtension(
   "SMAP\nPostActions.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PostActions.kt\norg/anti_ad/mc/ipnext/inventory/action/GroupInColumnsCalculator$ColumnsCandidate\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,253:1\n1573#2:254\n1604#2,4:255\n1740#2,3:259\n1869#2,2:262\n1573#2:264\n1604#2,3:265\n1374#2:268\n1460#2,5:269\n1607#2:274\n1563#2:275\n1634#2,3:276\n*S KotlinDebug\n*F\n+ 1 PostActions.kt\norg/anti_ad/mc/ipnext/inventory/action/GroupInColumnsCalculator$ColumnsCandidate\n*L\n141#1:254\n141#1:255,4\n155#1:259,3\n161#1:262,2\n213#1:264\n213#1:265,3\n214#1:268\n214#1:269,5\n213#1:274\n233#1:275\n233#1:276,3\n*E\n"
)
public final class GroupInColumnsCalculator$ColumnsCandidate {
   @NotNull
   private final List slotCountPairList;
   @NotNull
   private final List columnWidths;
   private final int height;
   private final int width;
   private int brokenGroups;
   private final boolean succeeded;
   @NotNull
   private final List cells;
   private int cellIndex;
   private boolean allowBroken;
   @NotNull
   private final List eachCellsList;

   public GroupInColumnsCalculator$ColumnsCandidate(@NotNull List var1, @NotNull List var2, int var3) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      super();
      this.slotCountPairList = var1;
      this.columnWidths = var2;
      this.height = var3;
      this.width = CollectionsKt.sumOfInt(this.columnWidths);
      Iterable var13 = this.columnWidths;
      GroupInColumnsCalculator$ColumnsCandidate var11 = this;
      Iterable var17 = var13;
      var13 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var13, 10));
      var3 = 0;

      for (Object var4 : var17) {
         int var5;
         if ((var5 = var3++) < 0) {
            CollectionsKt.throwIndexOverflow();
         }

         int var21 = ((Number)var4).intValue();
         var5 = var5;
         ArrayList var12 = var13;
         int var6 = this.height;
         ArrayList var7 = new ArrayList(var6);

         for (int var8 = 0; var8 < var6; var8++) {
            int var9 = var8;
            ArrayList var10 = var7;
            var10.add(new GroupInColumnsCalculator$ColumnsCandidate$Cell(this, var21, var9, var5));
         }

         var12.add(var7);
      }

      boolean var10000;
      label48: {
         var11.cells = CollectionsKt.flatten((Iterable & List)var13);
         this.eachCellsList = new ArrayList();
         var13 = (Iterable)CollectionsKt.getIndices(this.slotCountPairList);
         var11 = this;
         if (!(var13 instanceof Collection) || !((Collection)var13).isEmpty()) {
            Iterator var19 = var13.iterator();

            while (var19.hasNext()) {
               int var16 = ((IntIterator)var19).nextInt();
               if (!this.addCellsForIndex(var16)) {
                  var10000 = false;
                  break label48;
               }
            }
         }

         var10000 = true;
      }

      boolean var24 = var10000;
      var11.succeeded = var24;
   }

   @NotNull
   public final List getSlotCountPairList() {
      return this.slotCountPairList;
   }

   @NotNull
   public final List getColumnWidths() {
      return this.columnWidths;
   }

   public final int getHeight() {
      return this.height;
   }

   public final int getWidth() {
      return this.width;
   }

   public final int getBrokenGroups() {
      return this.brokenGroups;
   }

   public final void setBrokenGroups(int var1) {
      this.brokenGroups = var1;
   }

   public final boolean getSucceeded() {
      return this.succeeded;
   }

   @NotNull
   public final List getCells() {
      return this.cells;
   }

   public final int getCellIndex() {
      return this.cellIndex;
   }

   public final void setCellIndex(int var1) {
      this.cellIndex = var1;
   }

   public final boolean getAllowBroken() {
      return this.allowBroken;
   }

   public final void setAllowBroken(boolean var1) {
      this.allowBroken = var1;
   }

   @NotNull
   public final List getEachCellsList() {
      return this.eachCellsList;
   }

   public final boolean addCellsForIndex(int var1) {
      List var3;
      if ((var3 = this.findCellsForRoom(((Number)((Pair)this.slotCountPairList.get(var1)).getSecond()).intValue())).isEmpty()) {
         return false;
      }

      Iterator var2 = var3.iterator();

      while (var2.hasNext()) {
         ((GroupInColumnsCalculator$ColumnsCandidate$Cell)var2.next()).setOccupied(true);
      }

      this.eachCellsList.add(var3);
      if (!this.connected(var3)) {
         int var4 = this.brokenGroups++;
      }

      return true;
   }

   @NotNull
   public final List findCellsForRoom(int var1) {
      if (!this.findEmptyCell()) {
         return CollectionsKt.emptyList();
      }

      if (!this.allowBroken) {
         int var2 = ((GroupInColumnsCalculator$ColumnsCandidate$Cell)this.cells.get(this.cellIndex)).getColumnIndex();
         int var3 = 0;
         int var4 = 0;
         int var5 = this.cellIndex;

         for (int var6 = this.cells.size(); var5 < var6; var5++) {
            var3 += ((GroupInColumnsCalculator$ColumnsCandidate$Cell)this.cells.get(var5)).getRoom();
            var4++;
            if (var3 >= var1) {
               if (var4 > this.height || ((GroupInColumnsCalculator$ColumnsCandidate$Cell)this.cells.get(var5)).getColumnIndex() == var2) {
                  return this.cells.subList(this.cellIndex, this.cellIndex + var4);
               }

               this.cellIndex = (var2 + 1) * this.height;
               break;
            }
         }
      }

      List var7 = new ArrayList();
      int var8 = var1;

      while (var8 > 0) {
         if (!this.findEmptyCell()) {
            return CollectionsKt.emptyList();
         }

         GroupInColumnsCalculator$ColumnsCandidate$Cell var9 = (GroupInColumnsCalculator$ColumnsCandidate$Cell)this.cells.get(this.cellIndex);
         var8 -= var9.getRoom();
         var7.add(var9);
         var9.setOccupied(true);
      }

      return var7;
   }

   public final boolean findEmptyCell() {
      while (true) {
         if (this.cellIndex < this.cells.size()) {
            if (!((GroupInColumnsCalculator$ColumnsCandidate$Cell)this.cells.get(this.cellIndex)).getOccupied()) {
               return true;
            }

            int var1 = this.cellIndex++;
         } else {
            if (this.allowBroken) {
               return false;
            }

            this.allowBroken = true;
            this.cellIndex = 0;
         }
      }
   }

   @NotNull
   public final List apply() {
      Iterable var1;
      Iterable var2 = var1 = this.slotCountPairList;
      var1 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var1, 10));
      int var3 = 0;

      for (Pair var4 : var2) {
         int var5;
         if ((var5 = var3++) < 0) {
            CollectionsKt.throwIndexOverflow();
         }

         var4 = var4;
         var5 = var5;
         ArrayList var8 = var1;
         ItemType var10000 = (ItemType)var4.component1();
         Iterable var12 = (Iterable)this.eachCellsList.get(var5);
         ItemType var15 = var10000;
         var var6 = new ArrayList();
         Iterator var13 = var12.iterator();

         while (var13.hasNext()) {
            Iterable var7 = (Iterable)((GroupInColumnsCalculator$ColumnsCandidate$Cell)var13.next()).getSlotIndices();
            CollectionsKt.addAll(var6, var7);
         }

         var8.add(TuplesKt.to(var15, var6));
      }

      return var1;
   }

   public final boolean connected(@NotNull List var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      Iterable var5;
      Iterable var2 = var5 = var1;
      var var6 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var5, 10));

      for (GroupInColumnsCalculator$ColumnsCandidate$Cell var3 : var2) {
         var3 = var3;
         ArrayList var4 = var6;
         var4.add(TuplesKt.to(var3.getRowIndex(), var3.getColumnIndex()));
      }

      return PostActionsKt.access$connected(var6);
   }
}
