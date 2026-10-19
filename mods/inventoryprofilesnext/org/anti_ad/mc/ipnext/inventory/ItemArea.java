package org.anti_ad.mc.ipnext.inventory;

import java.util.ArrayList;
import java.util.List;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IndexedValue;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.anti_ad.mc.common.math2d.Point;
import org.anti_ad.mc.common.math2d.Size;
import org.jetbrains.annotations.NotNull;

@SourceDebugExtension(
   "SMAP\nAreaTypes.kt\nKotlin\n*S Kotlin\n*F\n+ 1 AreaTypes.kt\norg/anti_ad/mc/ipnext/inventory/ItemArea\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,384:1\n1563#2:385\n1634#2,3:386\n1878#2,3:389\n*S KotlinDebug\n*F\n+ 1 AreaTypes.kt\norg/anti_ad/mc/ipnext/inventory/ItemArea\n*L\n263#1:385\n263#1:386,3\n269#1:389,3\n*E\n"
)
public final class ItemArea {
   @NotNull
   public static final ItemArea$Companion Companion = new ItemArea$Companion(null);
   @NotNull
   private final List fromSlotLocations;
   private boolean orderSensitive;
   private boolean isRectangular;
   private int width;
   private int height;
   @NotNull
   private List slotIndices;

   public ItemArea(@NotNull List var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      super();
      this.fromSlotLocations = var1;
      this.slotIndices = CollectionsKt.emptyList();
   }

   public ItemArea(@NotNull List var1, @NotNull List var2, boolean var3) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      this(var1);
      this.orderSensitive = var3;
      this.slotIndices = var2;
      this.checkRectangular();
   }

   public final boolean getOrderSensitive() {
      return this.orderSensitive;
   }

   public final void setOrderSensitive(boolean var1) {
      this.orderSensitive = var1;
   }

   public final boolean isRectangular() {
      return this.isRectangular;
   }

   public final int getWidth() {
      return this.width;
   }

   public final int getHeight() {
      return this.height;
   }

   @NotNull
   public final List getSlotIndices() {
      return this.slotIndices;
   }

   public final boolean isEmpty() {
      return this.slotIndices.isEmpty();
   }

   @NotNull
   public final ItemArea plus(@NotNull ItemArea var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      return ItemArea$Companion.access$plus(Companion, this, var1);
   }

   @NotNull
   public final ItemArea minus(@NotNull ItemArea var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      return ItemArea$Companion.access$minus(Companion, this, var1);
   }

   private final void checkRectangular() {
      this.isRectangular = false;
      this.width = 0;
      this.height = 0;
      if (!this.orderSensitive) {
         List var1 = CollectionsKt.toMutableList(this.slotIndices);
         Iterable var2;
         Iterable var3 = var2 = this.slotIndices;
         var2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var2, 10));

         for (Object var4 : var3) {
            int var15 = ((Number)var4).intValue();
            ArrayList var5 = var2;
            var5.add((Point)this.fromSlotLocations.get(var15));
         }

         Pair var10000 = AreaTypesKt.access$isRectangular(var2);
         if (var10000 != null) {
            Pair var12 = var10000;
            Size var8 = (Size)var10000.component1();
            var3 = (List)var12.component2();
            this.isRectangular = true;
            this.width = var8.getWidth();
            this.height = var8.getHeight();
            Iterable var16 = var3;
            int var9 = 0;

            for (IndexedValue var17 : var16) {
               int var20;
               if ((var20 = var9++) < 0) {
                  CollectionsKt.throwIndexOverflow();
               }

               var17 = var17;
               var20 = var20;
               int var19 = var17.component1();
               var1.set(var20, this.slotIndices.get(var19));
            }

            this.slotIndices = var1;
         } else {
            int var7;
            ItemArea var11;
            if ((var7 = (var11 = this).slotIndices.size()) % 9 == 0) {
               var11.isRectangular = true;
               var11.width = 9;
               var11.height = var7 / 9;
            }
         }
      }
   }
}
