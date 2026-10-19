package org.anti_ad.mc.ipnext.inventory.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SourceDebugExtension(
   "SMAP\nPostActions.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PostActions.kt\norg/anti_ad/mc/ipnext/inventory/action/GroupInColumnsCalculator\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,253:1\n2423#2,14:254\n*S KotlinDebug\n*F\n+ 1 PostActions.kt\norg/anti_ad/mc/ipnext/inventory/action/GroupInColumnsCalculator\n*L\n130#1:254,14\n*E\n"
)
final class GroupInColumnsCalculator {
   @NotNull
   private final List slotCountPairList;
   private final int width;
   private final int height;

   public GroupInColumnsCalculator(@NotNull List var1, int var2, int var3) {
      Intrinsics.checkNotNullParameter(var1, "");
      super();
      this.slotCountPairList = var1;
      this.width = var2;
      this.height = var3;
   }

   @NotNull
   public final List getSlotCountPairList() {
      return this.slotCountPairList;
   }

   public final int getWidth() {
      return this.width;
   }

   public final int getHeight() {
      return this.height;
   }

   @Nullable
   public final List calc() {
      int var1;
      if ((var1 = this.slotCountPairList.size()) == 0) {
         return null;
      }

      List var2 = new ArrayList();
      int var3 = 1;
      int var4 = this.width;
      if (this.width > 0) {
         while (true) {
            GroupInColumnsCalculator$ColumnsCandidate var5;
            if (var1 <= this.height * var3
               && (var5 = new GroupInColumnsCalculator$ColumnsCandidate(this.slotCountPairList, MiscKt.distribute(this.width, var3), this.height))
                  .getSucceeded()) {
               if (var5.getBrokenGroups() == 0) {
                  return var5.apply();
               }

               var2.add(var5);
            }

            if (var3 == var4) {
               break;
            }

            var3++;
         }
      }

      Iterator var6;
      Object var10000;
      if (!(var6 = var2.iterator()).hasNext()) {
         var10000 = null;
      } else {
         Object var7 = var6.next();
         if (!var6.hasNext()) {
            var10000 = var7;
         } else {
            var3 = ((GroupInColumnsCalculator$ColumnsCandidate)var7).getBrokenGroups();

            do {
               int var10 = ((GroupInColumnsCalculator$ColumnsCandidate)(var4 = (int)var6.next())).getBrokenGroups();
               if (var3 > var10) {
                  var7 = var4;
                  var3 = var10;
               }
            } while (var6.hasNext());

            var10000 = var7;
         }
      }

      return (GroupInColumnsCalculator$ColumnsCandidate)var10000 != null ? ((GroupInColumnsCalculator$ColumnsCandidate)var10000).apply() : null;
   }
}
