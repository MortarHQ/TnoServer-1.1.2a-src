package org.anti_ad.mc.ipnext.inventory.sandbox.diffcalculator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.RangesKt;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.anti_ad.mc.ipnext.inventory.data.ItemTracker;
import org.anti_ad.mc.ipnext.inventory.data.MutableItemBucket;
import org.anti_ad.mc.ipnext.inventory.sandbox.ContainerSandbox;
import org.anti_ad.mc.ipnext.item.ItemStackExtensionsKt;
import org.anti_ad.mc.ipnext.item.ItemType;
import org.jetbrains.annotations.NotNull;

@SourceDebugExtension(
   "SMAP\nSimpleDiffCalculatorInstance.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SimpleDiffCalculatorInstance.kt\norg/anti_ad/mc/ipnext/inventory/sandbox/diffcalculator/SimpleDiffCalculatorInstance\n+ 2 DiffCalculator.kt\norg/anti_ad/mc/ipnext/inventory/sandbox/diffcalculator/DiffCalculatorInstance\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 4 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 5 kt_common.kt\norg/anti_ad/mc/common/extensions/Kt_commonKt\n+ 6 ItemTypeExtensions.kt\norg/anti_ad/mc/ipnext/item/ItemTypeExtensionsKt\n*L\n1#1,196:1\n82#1,5:231\n87#1,5:238\n82#1,5:245\n87#1,5:252\n82#1,5:290\n87#1,5:297\n82#1,5:317\n87#1,5:324\n82#1,5:353\n87#1,5:360\n82#1,5:366\n87#1,5:373\n82#1,5:393\n87#1,5:400\n82#1,5:420\n87#1,5:427\n82#1,5:447\n87#1,5:454\n156#2:197\n156#2:211\n1617#3,9:198\n1869#3:207\n1870#3:209\n1626#3:210\n1617#3,9:212\n1869#3:221\n1870#3:223\n1626#3:224\n774#3:225\n865#3,2:226\n774#3:228\n865#3,2:229\n774#3:236\n865#3:237\n866#3:243\n774#3:250\n865#3:251\n866#3:257\n1999#3,14:258\n774#3:273\n865#3,2:274\n2423#3,14:276\n774#3:295\n865#3:296\n866#3:302\n2423#3,14:303\n774#3:322\n865#3:323\n866#3:329\n2423#3,14:330\n774#3:358\n865#3:359\n866#3:365\n774#3:371\n865#3:372\n866#3:378\n2423#3,14:379\n774#3:398\n865#3:399\n866#3:405\n2423#3,14:406\n774#3:425\n865#3:426\n866#3:432\n1999#3,14:433\n774#3:452\n865#3:453\n866#3:459\n2423#3,14:460\n1#4:208\n1#4:222\n1#4:272\n93#5:244\n98#6,9:344\n*S KotlinDebug\n*F\n+ 1 SimpleDiffCalculatorInstance.kt\norg/anti_ad/mc/ipnext/inventory/sandbox/diffcalculator/SimpleDiffCalculatorInstance\n*L\n107#1:231,5\n107#1:238,5\n108#1:245,5\n108#1:252,5\n118#1:290,5\n118#1:297,5\n124#1:317,5\n124#1:324,5\n172#1:353,5\n172#1:360,5\n173#1:366,5\n173#1:373,5\n178#1:393,5\n178#1:400,5\n182#1:420,5\n182#1:427,5\n187#1:447,5\n187#1:454,5\n44#1:197\n40#1:211\n44#1:198,9\n44#1:207\n44#1:209\n44#1:210\n40#1:212,9\n40#1:221\n40#1:223\n40#1:224\n86#1:225\n86#1:226,2\n86#1:228\n86#1:229,2\n107#1:236\n107#1:237\n107#1:243\n108#1:250\n108#1:251\n108#1:257\n109#1:258,14\n111#1:273\n111#1:274,2\n112#1:276,14\n118#1:295\n118#1:296\n118#1:302\n119#1:303,14\n124#1:322\n124#1:323\n124#1:329\n125#1:330,14\n172#1:358\n172#1:359\n172#1:365\n173#1:371\n173#1:372\n173#1:378\n176#1:379,14\n178#1:398\n178#1:399\n178#1:405\n179#1:406,14\n182#1:425\n182#1:426\n182#1:432\n183#1:433,14\n187#1:452\n187#1:453\n187#1:459\n187#1:460,14\n44#1:208\n40#1:222\n108#1:244\n168#1:344,9\n*E\n"
)
public class SimpleDiffCalculatorInstance extends DiffCalculatorInstance implements DiffCalculatorUtil {
   private boolean untilEqualsTypeOnly;
   @NotNull
   private final MutableItemBucket toBeThrown;
   @NotNull
   private final Set nonEquals;

   public SimpleDiffCalculatorInstance(@NotNull ContainerSandbox var1, @NotNull ItemTracker var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      super(var1, var2);
      if (!ItemStackExtensionsKt.isEmpty(this.getCursorGoal())) {
         throw new IllegalStateException("non empty goal cursor is not supported".toString());
      }

      this.toBeThrown = var2.getThrownItems().minus(this.getNowTracker().getThrownItems()).copyAsMutable();
      DiffCalculatorInstance var6 = this;
      SimpleDiffCalculatorInstance var5 = this;
      Iterable var7 = (Iterable)var6.getIndices();
      var var3 = new ArrayList();
      Iterator var8 = var7.iterator();

      while (var8.hasNext()) {
         int var4 = ((IntIterator)var8).nextInt();
         DiffCalculatorInstance$CompareSlotDsl var9;
         DiffCalculatorInstance$CompareSlotDsl var10000 = !(var9 = new DiffCalculatorInstance$CompareSlotDsl(var6, var4)).getEquals() ? var9 : null;
         if (var10000 != null) {
            DiffCalculatorInstance$CompareSlotDsl var10 = var10000;
            var3.add(var10);
         }
      }

      var5.nonEquals = CollectionsKt.toMutableSet(var3);
   }

   public final boolean getUntilEqualsTypeOnly() {
      return this.untilEqualsTypeOnly;
   }

   public final void setUntilEqualsTypeOnly(boolean var1) {
      this.untilEqualsTypeOnly = var1;
   }

   public final boolean getShouldStop() {
      if (this.untilEqualsTypeOnly) {
         DiffCalculatorInstance var1;
         Iterable var2 = (Iterable)(var1 = this).getIndices();
         var var3 = new ArrayList();
         Iterator var6 = var2.iterator();

         while (var6.hasNext()) {
            int var4 = ((IntIterator)var6).nextInt();
            DiffCalculatorInstance$CompareSlotDsl var5;
            DiffCalculatorInstance$CompareSlotDsl var7;
            DiffCalculatorInstance$CompareSlotDsl var10000 = !(var5 = var7 = new DiffCalculatorInstance$CompareSlotDsl(var1, var4)).getEqualsType()
                  && !ItemStackExtensionsKt.isEmpty(var5.getNow())
               ? var7
               : null;
            if (var10000 != null) {
               DiffCalculatorInstance$CompareSlotDsl var8 = var10000;
               var3.add(var8);
            }
         }

         if (var3.isEmpty() && this.toBeThrown.isEmpty()) {
            return true;
         }
      }

      return false;
   }

   @NotNull
   public final Set getNonEquals() {
      return this.nonEquals;
   }

   @Override
   public void run() {
      while (!this.nonEquals.isEmpty() || !Intrinsics.areEqual(this.getNowTracker(), this.getGoalTracker())) {
         if (this.getShouldStop()) {
            return;
         }

         this.increaseLoopCount();
         if (ItemStackExtensionsKt.isEmpty(this.getCursorNow())) {
            this.grabAnything();
         } else {
            this.handleCursor();
         }

         CollectionsKt.removeAll(this.nonEquals, SimpleDiffCalculatorInstance::run$lambda$2);
      }
   }

   private final boolean getCanThrowAll(DiffCalculatorInstance$CompareSlotDsl var1) {
      return !ItemStackExtensionsKt.isEmpty(var1.getNow()) && this.toBeThrown.contains(var1.getNow());
   }

   private final boolean getCanThrowType(DiffCalculatorInstance$CompareSlotDsl var1) {
      return !ItemStackExtensionsKt.isEmpty(var1.getNow()) && this.toBeThrown.contains(var1.getNow().getItemType());
   }

   private final int getToBeThrownCount(DiffCalculatorInstance$CompareSlotDsl var1) {
      return RangesKt.coerceAtMost(this.toBeThrown.count(var1.getNow().getItemType()), var1.getNow().getCount());
   }

   private final boolean getCanThrowSplit(DiffCalculatorInstance$CompareSlotDsl var1) {
      return !ItemStackExtensionsKt.isEmpty(var1.getNow()) && var1.getNow().getCount() - var1.getNow().getCount() / 2 <= this.getToBeThrownCount(var1);
   }

   private final int getCanThrowCount(DiffCalculatorInstance$CompareSlotDsl var1) {
      return this.getCanThrowAll(var1) ? var1.getNow().getCount() : var1.getNow().getCount() - var1.getNow().getCount() / 2;
   }

   private final boolean getNowLessThanGoal(DiffCalculatorInstance$CompareSlotDsl var1) {
      return var1.getEqualsType() && var1.getNow().getCount() < var1.getGoal().getCount();
   }

   private final boolean getNowLessThanEqualGoal(DiffCalculatorInstance$CompareSlotDsl var1) {
      return var1.getEqualsType() && var1.getNow().getCount() <= var1.getGoal().getCount();
   }

   private final boolean getNowMoreThanGoal(DiffCalculatorInstance$CompareSlotDsl var1) {
      return var1.getEqualsType() && var1.getNow().getCount() > var1.getGoal().getCount();
   }

   private final boolean getNowMoreThanEqualGoal(DiffCalculatorInstance$CompareSlotDsl var1) {
      return var1.getEqualsType() && var1.getNow().getCount() >= var1.getGoal().getCount();
   }

   private final List filtered(Iterable var1, boolean var2, boolean var3, boolean var4, Function1 var5) {
      var var6 = new ArrayList();
      Iterator var10 = var1.iterator();

      while (var10.hasNext()) {
         Object var7;
         DiffCalculatorInstance$CompareSlotDsl var8;
         DiffCalculatorInstance$CompareSlotDsl var9 = var8 = (DiffCalculatorInstance$CompareSlotDsl)(var7 = var10.next());
         if ((!var2 || !var9.getEquals())
            && (!var3 || !ItemStackExtensionsKt.isEmpty(var9.getNow()))
            && (!var4 || !ItemStackExtensionsKt.isEmpty(var9.getGoal()))
            && (Boolean)var5.invoke(var8)) {
            var6.add(var7);
         }
      }

      return var6;
   }

   public final void grabAnything() {
      if (!this.toBeThrown.isEmpty()) {
         this.grabToBeThrown();
      } else {
         this.grabOtherThing();
      }
   }

   public final void grabToBeThrown() {
      Iterable var2 = this.nonEquals;
      var var1 = new ArrayList();
      Iterator var13 = var2.iterator();

      while (var13.hasNext()) {
         Object var3;
         DiffCalculatorInstance$CompareSlotDsl var4;
         if (!(var4 = (DiffCalculatorInstance$CompareSlotDsl)(var3 = var13.next())).getEquals()
            && (this.getCanThrowAll(var4) || this.getCanThrowSplit(var4))
            && !this.getNowLessThanEqualGoal(var4)) {
            var1.add(var3);
         }
      }

      List var6;
      List var10000;
      if ((var6 = var1).isEmpty()) {
         Iterable var19 = this.nonEquals;
         var var23 = new ArrayList();
         Iterator var5 = var19.iterator();

         while (var5.hasNext()) {
            Object var7;
            DiffCalculatorInstance$CompareSlotDsl var14;
            if (!(var14 = (DiffCalculatorInstance$CompareSlotDsl)(var7 = var5.next())).getEquals()
               && (this.getCanThrowAll(var14) || this.getCanThrowSplit(var14))) {
               var23.add(var7);
            }
         }

         var10000 = var23;
      } else {
         var10000 = var6;
      }

      Iterator var15;
      Object var31;
      if (!(var15 = var10000.iterator()).hasNext()) {
         var31 = null;
      } else {
         Object var8 = var15.next();
         if (!var15.hasNext()) {
            var31 = var8;
         } else {
            DiffCalculatorInstance$CompareSlotDsl var20 = (DiffCalculatorInstance$CompareSlotDsl)var8;
            int var21 = this.getCanThrowCount(var20);

            do {
               Object var24;
               DiffCalculatorInstance$CompareSlotDsl var27 = (DiffCalculatorInstance$CompareSlotDsl)(var24 = var15.next());
               int var28 = this.getCanThrowCount(var27);
               if (var21 < var28) {
                  var8 = var24;
                  var21 = var28;
               }
            } while (var15.hasNext());

            var31 = var8;
         }
      }

      DiffCalculatorInstance$CompareSlotDsl var9;
      if ((var9 = (DiffCalculatorInstance$CompareSlotDsl)var31) != null) {
         if (this.getCanThrowAll(var9)) {
            var9.leftClick();
         } else {
            var9.rightClick();
         }
      } else {
         var2 = this.nonEquals;
         var6 = new ArrayList();
         Iterator var25 = var2.iterator();

         while (var25.hasNext()) {
            Object var29;
            DiffCalculatorInstance$CompareSlotDsl var17 = (DiffCalculatorInstance$CompareSlotDsl)(var29 = var25.next());
            if (this.getCanThrowType(var17)) {
               var6.add(var29);
            }
         }

         Iterator var18;
         if (!(var18 = var6.iterator()).hasNext()) {
            var31 = null;
         } else {
            Object var11 = var18.next();
            if (!var18.hasNext()) {
               var31 = var11;
            } else {
               int var22 = ((DiffCalculatorInstance$CompareSlotDsl)var11).getNow().getCount();

               do {
                  Object var26;
                  int var30 = ((DiffCalculatorInstance$CompareSlotDsl)(var26 = var18.next())).getNow().getCount();
                  if (var22 > var30) {
                     var11 = var26;
                     var22 = var30;
                  }
               } while (var18.hasNext());

               var31 = var11;
            }
         }

         DiffCalculatorInstance$CompareSlotDsl var12;
         if ((var12 = (DiffCalculatorInstance$CompareSlotDsl)var31) != null) {
            var12.rightClick();
         } else {
            throw new IllegalStateException("should not reach here".toString());
         }
      }
   }

   public final void grabOtherThing() {
      Iterable var1 = this.nonEquals;
      var var2 = new ArrayList();
      Iterator var6 = var1.iterator();

      while (var6.hasNext()) {
         Object var3;
         DiffCalculatorInstance$CompareSlotDsl var4;
         DiffCalculatorInstance$CompareSlotDsl var5;
         if (!(var5 = var4 = (DiffCalculatorInstance$CompareSlotDsl)(var3 = var6.next())).getEquals()
            && !ItemStackExtensionsKt.isEmpty(var5.getNow())
            && !var4.getEqualsType()) {
            var2.add(var3);
         }
      }

      Iterator var7;
      Object var10000;
      if (!(var7 = var2.iterator()).hasNext()) {
         var10000 = null;
      } else {
         Object var13 = var7.next();
         if (!var7.hasNext()) {
            var10000 = var13;
         } else {
            int var16 = ((DiffCalculatorInstance$CompareSlotDsl)var13).getNow().getCount();

            do {
               Object var20;
               int var23 = ((DiffCalculatorInstance$CompareSlotDsl)(var20 = var7.next())).getNow().getCount();
               if (var16 > var23) {
                  var13 = var20;
                  var16 = var23;
               }
            } while (var7.hasNext());

            var10000 = var13;
         }
      }

      DiffCalculatorInstance$CompareSlotDsl var8;
      if ((var8 = (DiffCalculatorInstance$CompareSlotDsl)var10000) != null) {
         var8.leftClick();
      } else {
         if (this.untilEqualsTypeOnly) {
            throw new IllegalStateException("until equals type only".toString());
         }

         var1 = this.nonEquals;
         var2 = new ArrayList();
         Iterator var10 = var1.iterator();

         while (var10.hasNext()) {
            Object var17;
            DiffCalculatorInstance$CompareSlotDsl var21;
            DiffCalculatorInstance$CompareSlotDsl var24;
            if (!(var24 = var21 = (DiffCalculatorInstance$CompareSlotDsl)(var17 = var10.next())).getEquals()
               && !ItemStackExtensionsKt.isEmpty(var24.getNow())
               && this.getNowMoreThanGoal(var21)) {
               var2.add(var17);
            }
         }

         Iterator var11;
         if (!(var11 = var2.iterator()).hasNext()) {
            var10000 = null;
         } else {
            Object var15 = var11.next();
            if (!var11.hasNext()) {
               var10000 = var15;
            } else {
               DiffCalculatorInstance$CompareSlotDsl var18 = (DiffCalculatorInstance$CompareSlotDsl)var15;
               int var19 = this.clickCountSingleSlotToLess(var18.getNow().getCount(), var18.getGoal().getCount());

               do {
                  Object var22;
                  DiffCalculatorInstance$CompareSlotDsl var25 = (DiffCalculatorInstance$CompareSlotDsl)(var22 = var11.next());
                  int var26 = this.clickCountSingleSlotToLess(var25.getNow().getCount(), var25.getGoal().getCount());
                  if (var19 > var26) {
                     var15 = var22;
                     var19 = var26;
                  }
               } while (var11.hasNext());

               var10000 = var15;
            }
         }

         DiffCalculatorInstance$CompareSlotDsl var12;
         if ((var12 = (DiffCalculatorInstance$CompareSlotDsl)var10000) == null) {
            throw new IllegalStateException("should not reach here".toString());
         }

         if (this.canRight(var12.getNow().getCount(), var12.getGoal().getCount())) {
            var12.rightClick();
         } else {
            var12.leftClick();
         }
      }
   }

   public final void handleCursor() {
      if (!this.toBeThrown.isEmpty()) {
         this.throwCursor();
      }

      if (!ItemStackExtensionsKt.isEmpty(this.getCursorNow())) {
         this.handleCursorNoNeedThrow();
      }
   }

   public final void throwCursor() {
      if (this.toBeThrown.contains(this.getCursorNow())) {
         this.toBeThrown.remove(this.getCursorNow());
         this.getSandbox().leftClickOutside();
      } else {
         if (this.toBeThrown.contains(this.getCursorNow().getItemType())) {
            int var1 = this.toBeThrown.count(this.getCursorNow().getItemType());
            this.toBeThrown.remove(this.getCursorNow());

            for (int var2 = 0; var2 < var1; var2++) {
               this.getSandbox().rightClickOutside();
            }
         }
      }
   }

   public final int getNowGoalCount(@NotNull DiffCalculatorInstance$CompareSlotDsl var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      return var1.getEqualsType() ? var1.getNow().getCount() : 0;
   }

   public final int getNowGoalRemaining(@NotNull DiffCalculatorInstance$CompareSlotDsl var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      return var1.getGoal().getCount() - this.getNowGoalCount(var1);
   }

   public final int getWithCursorNowCount(@NotNull DiffCalculatorInstance$CompareSlotDsl var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      if (Intrinsics.areEqual(this.getCursorNow().getItemType(), var1.getGoal().getItemType())) {
         int var10000 = this.getNowGoalCount(var1) + this.getCursorNow().getCount();
         ItemType var2 = var1.getGoal().getItemType();
         ItemStack var10001 = new ItemStack;
         ItemLike var10003 = (ItemLike)var2.getItem();
         PatchedDataComponentMap var10005 = var2.getTag();
         if (var10005 == null) {
            DataComponentMap var3 = DataComponentMap.EMPTY;
            Intrinsics.checkNotNull(DataComponentMap.EMPTY);
            var10005 = (PatchedDataComponentMap)var3;
         }

         var10001./* $VF: Unable to resugar constructor */<init>(var10003, 1, var10005);
         return RangesKt.coerceAtMost(var10000, var10001.getMaxStackSize());
      } else {
         return 0;
      }
   }

   public final void handleCursorNoNeedThrow() {
      Iterable var6 = this.nonEquals;
      var var2 = new ArrayList();
      Iterator var3 = var6.iterator();

      while (var3.hasNext()) {
         Object var4;
         DiffCalculatorInstance$CompareSlotDsl var5;
         DiffCalculatorInstance$CompareSlotDsl var48;
         if (!(var48 = var5 = (DiffCalculatorInstance$CompareSlotDsl)(var4 = var3.next())).getEquals()
            && !ItemStackExtensionsKt.isEmpty(var48.getGoal())
            && Intrinsics.areEqual(this.getCursorNow().getItemType(), var5.getGoal().getItemType())) {
            var2.add(var4);
         }
      }

      List var1;
      var6 = var1 = var2;
      var2 = new ArrayList();
      var3 = var6.iterator();

      while (var3.hasNext()) {
         Object var28;
         DiffCalculatorInstance$CompareSlotDsl var40;
         if (!(var40 = (DiffCalculatorInstance$CompareSlotDsl)(var28 = var3.next())).getEquals()
            && this.getWithCursorNowCount(var40) <= var40.getGoal().getCount()) {
            var2.add(var28);
         }
      }

      Iterator var9;
      Object var10000;
      if (!(var9 = var2.iterator()).hasNext()) {
         var10000 = null;
      } else {
         Object var21 = var9.next();
         if (!var9.hasNext()) {
            var10000 = var21;
         } else {
            DiffCalculatorInstance$CompareSlotDsl var29;
            int var30 = (var29 = (DiffCalculatorInstance$CompareSlotDsl)var21).getGoal().getCount() - this.getWithCursorNowCount(var29);

            do {
               Object var41;
               DiffCalculatorInstance$CompareSlotDsl var50;
               int var51 = (var50 = (DiffCalculatorInstance$CompareSlotDsl)(var41 = var9.next())).getGoal().getCount() - this.getWithCursorNowCount(var50);
               if (var30 > var51) {
                  var21 = var41;
                  var30 = var51;
               }
            } while (var9.hasNext());

            var10000 = var21;
         }
      }

      DiffCalculatorInstance$CompareSlotDsl var10;
      if ((var10 = (DiffCalculatorInstance$CompareSlotDsl)var10000) != null) {
         var10.leftClick();
      } else {
         var6 = var1;
         var2 = new ArrayList();
         var3 = var6.iterator();

         while (var3.hasNext()) {
            Object var31;
            DiffCalculatorInstance$CompareSlotDsl var42;
            if (!(var42 = (DiffCalculatorInstance$CompareSlotDsl)(var31 = var3.next())).getEquals()
               && !var42.getEqualsType()
               && !ItemStackExtensionsKt.isEmpty(var42.getNow())) {
               var2.add(var31);
            }
         }

         Iterator var12;
         if (!(var12 = var2.iterator()).hasNext()) {
            var10000 = null;
         } else {
            Object var23 = var12.next();
            if (!var12.hasNext()) {
               var10000 = var23;
            } else {
               DiffCalculatorInstance$CompareSlotDsl var32;
               int var33 = (var32 = (DiffCalculatorInstance$CompareSlotDsl)var23).getGoal().getCount() - this.getWithCursorNowCount(var32);

               do {
                  Object var43;
                  DiffCalculatorInstance$CompareSlotDsl var53;
                  int var54 = (var53 = (DiffCalculatorInstance$CompareSlotDsl)(var43 = var12.next())).getGoal().getCount() - this.getWithCursorNowCount(var53);
                  if (var33 > var54) {
                     var23 = var43;
                     var33 = var54;
                  }
               } while (var12.hasNext());

               var10000 = var23;
            }
         }

         DiffCalculatorInstance$CompareSlotDsl var13;
         if ((var13 = (DiffCalculatorInstance$CompareSlotDsl)var10000) != null) {
            var13.leftClick();
         } else if (this.untilEqualsTypeOnly) {
            var6 = var1;
            var2 = new ArrayList();
            var3 = var6.iterator();

            while (var3.hasNext()) {
               Object var37;
               DiffCalculatorInstance$CompareSlotDsl var46;
               if (!(var46 = (DiffCalculatorInstance$CompareSlotDsl)(var37 = var3.next())).getEquals() && this.getNowGoalRemaining(var46) > 0) {
                  var2.add(var37);
               }
            }

            Iterator var18;
            if (!(var18 = var2.iterator()).hasNext()) {
               var10000 = null;
            } else {
               Object var27 = var18.next();
               if (!var18.hasNext()) {
                  var10000 = var27;
               } else {
                  DiffCalculatorInstance$CompareSlotDsl var38 = (DiffCalculatorInstance$CompareSlotDsl)var27;
                  int var39 = this.getNowGoalRemaining(var38);

                  do {
                     Object var47;
                     DiffCalculatorInstance$CompareSlotDsl var59 = (DiffCalculatorInstance$CompareSlotDsl)(var47 = var18.next());
                     int var60 = this.getNowGoalRemaining(var59);
                     if (var39 < var60) {
                        var27 = var47;
                        var39 = var60;
                     }
                  } while (var18.hasNext());

                  var10000 = var27;
               }
            }

            DiffCalculatorInstance$CompareSlotDsl var19;
            if ((var19 = (DiffCalculatorInstance$CompareSlotDsl)var10000) != null) {
               var19.leftClick();
            } else {
               throw new IllegalStateException("should not reach here".toString());
            }
         } else {
            var6 = var1;
            var2 = new ArrayList();
            var3 = var6.iterator();

            while (var3.hasNext()) {
               Object var34;
               DiffCalculatorInstance$CompareSlotDsl var44;
               if (!(var44 = (DiffCalculatorInstance$CompareSlotDsl)(var34 = var3.next())).getEquals() && this.getNowGoalRemaining(var44) > 0) {
                  var2.add(var34);
               }
            }

            Iterator var15;
            if (!(var15 = var2.iterator()).hasNext()) {
               var10000 = null;
            } else {
               Object var25 = var15.next();
               if (!var15.hasNext()) {
                  var10000 = var25;
               } else {
                  DiffCalculatorInstance$CompareSlotDsl var35 = (DiffCalculatorInstance$CompareSlotDsl)var25;
                  int var36 = this.getNowGoalRemaining(var35);

                  do {
                     Object var45;
                     DiffCalculatorInstance$CompareSlotDsl var56 = (DiffCalculatorInstance$CompareSlotDsl)(var45 = var15.next());
                     int var57 = this.getNowGoalRemaining(var56);
                     if (var36 > var57) {
                        var25 = var45;
                        var36 = var57;
                     }
                  } while (var15.hasNext());

                  var10000 = var25;
               }
            }

            DiffCalculatorInstance$CompareSlotDsl var16;
            if ((var16 = (DiffCalculatorInstance$CompareSlotDsl)var10000) != null) {
               DiffCalculatorInstance$CompareSlotDsl var7 = var16;
               if (this.getNowGoalRemaining(var7) >= this.getCursorNow().getCount()) {
                  throw new IllegalStateException("Check failed.");
               }

               var7.repeatRightClick(this.getNowGoalRemaining(var7));
            } else {
               throw new IllegalStateException("should not reach here".toString());
            }
         }
      }
   }

   @Override
   public int calcRank(int var1, int var2) {
      return DiffCalculatorUtil$DefaultImpls.calcRank(this, var1, var2);
   }

   @Override
   public int clickCountLowerBound(int var1, int var2) {
      return DiffCalculatorUtil$DefaultImpls.clickCountLowerBound(this, var1, var2);
   }

   @Override
   public int clickCountUpperBound(int var1, int var2) {
      return DiffCalculatorUtil$DefaultImpls.clickCountUpperBound(this, var1, var2);
   }

   @Override
   public int clickCountSingleSlotToLess(int var1, int var2) {
      return DiffCalculatorUtil$DefaultImpls.clickCountSingleSlotToLess(this, var1, var2);
   }

   @Override
   public boolean canRight(int var1, int var2) {
      return DiffCalculatorUtil$DefaultImpls.canRight(this, var1, var2);
   }

   private static final boolean run$lambda$2(DiffCalculatorInstance$CompareSlotDsl var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      return var0.getEquals();
   }
}
