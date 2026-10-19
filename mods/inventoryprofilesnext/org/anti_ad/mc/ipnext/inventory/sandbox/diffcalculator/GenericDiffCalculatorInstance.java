package org.anti_ad.mc.ipnext.inventory.sandbox.diffcalculator;

import java.util.ArrayList;
import java.util.Iterator;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.IntIterator;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.reflect.KFunction;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.anti_ad.mc.ipnext.config.Debugs;
import org.anti_ad.mc.ipnext.config.DiffCalculatorType;
import org.anti_ad.mc.ipnext.inventory.data.ItemTracker;
import org.anti_ad.mc.ipnext.inventory.data.MutableItemTracker;
import org.anti_ad.mc.ipnext.inventory.sandbox.ContainerSandbox;
import org.anti_ad.mc.ipnext.item.ItemStackExtensionsKt;
import org.anti_ad.mc.ipnext.item.ItemType;
import org.anti_ad.mc.ipnext.item.MutableItemStack;
import org.jetbrains.annotations.NotNull;

@SourceDebugExtension(
   "SMAP\nDiffCalculator.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DiffCalculator.kt\norg/anti_ad/mc/ipnext/inventory/sandbox/diffcalculator/GenericDiffCalculatorInstance\n+ 2 DiffCalculator.kt\norg/anti_ad/mc/ipnext/inventory/sandbox/diffcalculator/DiffCalculatorInstance\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 4 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 5 ItemTypeExtensions.kt\norg/anti_ad/mc/ipnext/item/ItemTypeExtensionsKt\n*L\n1#1,233:1\n156#2:234\n156#2:263\n1617#3,9:235\n1869#3:244\n1870#3:246\n1626#3:247\n2423#3,14:248\n1617#3,9:264\n1869#3:273\n1870#3:284\n1626#3:285\n2423#3,14:286\n1#4:245\n1#4:262\n1#4:283\n1#4:300\n98#5,9:274\n*S KotlinDebug\n*F\n+ 1 DiffCalculator.kt\norg/anti_ad/mc/ipnext/inventory/sandbox/diffcalculator/GenericDiffCalculatorInstance\n*L\n179#1:234\n186#1:263\n179#1:235,9\n179#1:244\n179#1:246\n179#1:247\n180#1:248,14\n186#1:264,9\n186#1:273\n186#1:284\n186#1:285\n187#1:286,14\n179#1:245\n186#1:283\n186#1:274,9\n*E\n"
)
public final class GenericDiffCalculatorInstance extends DiffCalculatorInstance implements DiffCalculatorUtil {
   @NotNull
   private final Lazy intermediateGoalTracker$delegate;
   @NotNull
   private final Lazy slotForEnoughRoom$delegate;

   public GenericDiffCalculatorInstance(@NotNull ContainerSandbox var1, @NotNull ItemTracker var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      super(var1, var2);
      this.intermediateGoalTracker$delegate = LazyKt.lazy(LazyThreadSafetyMode.NONE, GenericDiffCalculatorInstance::intermediateGoalTracker_delegate$lambda$0);
      this.slotForEnoughRoom$delegate = LazyKt.lazy(LazyThreadSafetyMode.NONE, GenericDiffCalculatorInstance::slotForEnoughRoom_delegate$lambda$7);
   }

   @NotNull
   public final ItemTracker getIntermediateGoalTracker() {
      return (ItemTracker)this.intermediateGoalTracker$delegate.getValue();
   }

   public final int getSlotForEnoughRoom() {
      return ((Number)this.slotForEnoughRoom$delegate.getValue()).intValue();
   }

   private final ItemTracker interpretGoal() {
      MutableItemTracker var1;
      MutableItemTracker var2;
      ItemStackExtensionsKt.transferTo(
         (var2 = var1 = this.getGoalTracker().copyAsMutable()).getCursor(), (MutableItemStack)var2.getSlots().get(this.getSlotForEnoughRoom())
      );
      if (!ItemStackExtensionsKt.isEmpty(var2.getCursor())) {
         throw new IllegalStateException("goal cursor still not empty".toString());
      } else {
         return var1;
      }
   }

   @Override
   public final void run() {
      if (!Intrinsics.areEqual(this.getNowTracker(), this.getGoalTracker())) {
         ItemTracker var1 = this.getIntermediateGoalTracker();

         ((SimpleDiffCalculatorInstance)((Function2)(switch ((DiffCalculatorType)Debugs.INSTANCE.getDIFF_CALCULATOR().getValue()) {
            case SIMPLE -> (GenericDiffCalculatorInstance$run$1 & KFunction)GenericDiffCalculatorInstance$run$1.INSTANCE;
            case SCORE_BASED_SINGLE -> (GenericDiffCalculatorInstance$run$2 & KFunction)GenericDiffCalculatorInstance$run$2.INSTANCE;
            case SCORE_BASED_DUAL -> (GenericDiffCalculatorInstance$run$3 & KFunction)GenericDiffCalculatorInstance$run$3.INSTANCE;
            default -> throw new NoWhenBranchMatchedException();
         })).invoke(this.getSandbox(), var1)).run();
         if (!Intrinsics.areEqual(this.getSandbox().getItems(), var1)) {
            throw new IllegalStateException(
               ("ContainerSandbox actual result by " + Debugs.INSTANCE.getDIFF_CALCULATOR().getValue() + " Diff Calculator not same as goal").toString()
            );
         }

         if (!ItemStackExtensionsKt.isEmpty(this.getCursorGoal())) {
            this.postRun();
         }
      }
   }

   public final void postRun() {
      DiffCalculatorInstance$CompareSlotDsl var1;
      if ((var1 = new DiffCalculatorInstance$CompareSlotDsl(this, this.getSlotForEnoughRoom())).getNow().getCount() != var1.getGoal().getCount()) {
         if (this.canRight(var1.getNow().getCount(), var1.getGoal().getCount())) {
            var1.rightClick();
         } else {
            var1.leftClick();
         }

         var1.repeatRightClick(var1.getGoal().getCount() - var1.getNow().getCount());
      }
   }

   @Override
   public final int calcRank(int var1, int var2) {
      return DiffCalculatorUtil$DefaultImpls.calcRank(this, var1, var2);
   }

   @Override
   public final int clickCountLowerBound(int var1, int var2) {
      return DiffCalculatorUtil$DefaultImpls.clickCountLowerBound(this, var1, var2);
   }

   @Override
   public final int clickCountUpperBound(int var1, int var2) {
      return DiffCalculatorUtil$DefaultImpls.clickCountUpperBound(this, var1, var2);
   }

   @Override
   public final int clickCountSingleSlotToLess(int var1, int var2) {
      return DiffCalculatorUtil$DefaultImpls.clickCountSingleSlotToLess(this, var1, var2);
   }

   @Override
   public final boolean canRight(int var1, int var2) {
      return DiffCalculatorUtil$DefaultImpls.canRight(this, var1, var2);
   }

   private static final ItemTracker intermediateGoalTracker_delegate$lambda$0(GenericDiffCalculatorInstance var0, ItemTracker var1) {
      return ItemStackExtensionsKt.isEmpty(var0.getCursorGoal()) ? var1 : var0.interpretGoal();
   }

   private static final int slotForEnoughRoom_delegate$lambda$7(GenericDiffCalculatorInstance var0) {
      DiffCalculatorInstance var1;
      Iterable var3 = (Iterable)(var1 = var0).getIndices();
      var var4 = new ArrayList();
      Iterator var2 = var3.iterator();

      while (var2.hasNext()) {
         int var14 = ((IntIterator)var2).nextInt();
         DiffCalculatorInstance$CompareSlotDsl var5;
         DiffCalculatorInstance$CompareSlotDsl var15;
         DiffCalculatorInstance$CompareSlotDsl var10000 = Intrinsics.areEqual(
                  (var5 = var15 = new DiffCalculatorInstance$CompareSlotDsl(var1, var14)).getNow().getItemType(), var0.getCursorGoal().getItemType()
               )
               && ItemStackExtensionsKt.isEmpty(var5.getGoal())
            ? var15
            : null;
         if (var10000 != null) {
            var5 = var10000;
            var4.add(var5);
         }
      }

      Object var34;
      if (!(var2 = var4.iterator()).hasNext()) {
         var34 = null;
      } else {
         Object var6 = var2.next();
         if (!var2.hasNext()) {
            var34 = (DiffCalculatorInstance$CompareSlotDsl)var6;
         } else {
            DiffCalculatorInstance$CompareSlotDsl var16 = (DiffCalculatorInstance$CompareSlotDsl)var6;
            int var17 = var0.calcRank(var16.getNow().getCount(), var0.getCursorGoal().getCount());

            do {
               Object var24;
               DiffCalculatorInstance$CompareSlotDsl var28 = (DiffCalculatorInstance$CompareSlotDsl)(var24 = var2.next());
               int var29 = var0.calcRank(var28.getNow().getCount(), var0.getCursorGoal().getCount());
               if (var17 > var29) {
                  var6 = var24;
                  var17 = var29;
               }
            } while (var2.hasNext());

            var34 = (DiffCalculatorInstance$CompareSlotDsl)var6;
         }
      }

      DiffCalculatorInstance$CompareSlotDsl var7;
      if ((var7 = var34) != null) {
         return var7.getSlotIndex();
      }

      var3 = (Iterable)(var1 = var0).getIndices();
      var4 = new ArrayList();
      var2 = var3.iterator();

      while (var2.hasNext()) {
         label81: {
            int var19 = ((IntIterator)var2).nextInt();
            DiffCalculatorInstance$CompareSlotDsl var30;
            if (Intrinsics.areEqual(
               (var30 = var20 = new DiffCalculatorInstance$CompareSlotDsl(var1, var19)).getGoal().getItemType(), var0.getCursorGoal().getItemType()
            )) {
               int var35 = var30.getGoal().getCount() + var0.getCursorGoal().getCount();
               ItemType var31 = var30.getGoal().getItemType();
               ItemStack var10001 = new ItemStack;
               ItemLike var10003 = (ItemLike)var31.getItem();
               PatchedDataComponentMap var10005 = var31.getTag();
               if (var10005 == null) {
                  DataComponentMap var39 = DataComponentMap.EMPTY;
                  Intrinsics.checkNotNull(DataComponentMap.EMPTY);
                  var10005 = (PatchedDataComponentMap)var39;
               }

               var10001./* $VF: Unable to resugar constructor */<init>(var10003, 1, var10005);
               if (var35 <= var10001.getMaxStackSize()) {
                  var36 = true;
                  break label81;
               }
            }

            var36 = false;
         }

         var34 = var36 ? var20 : null;
         if ((var36 ? var20 : null) != null) {
            DiffCalculatorInstance$CompareSlotDsl var21 = var34;
            var4.add(var21);
         }
      }

      if (!(var2 = var4.iterator()).hasNext()) {
         var34 = null;
      } else {
         Object var9 = var2.next();
         if (!var2.hasNext()) {
            var34 = (DiffCalculatorInstance$CompareSlotDsl)var9;
         } else {
            DiffCalculatorInstance$CompareSlotDsl var22 = (DiffCalculatorInstance$CompareSlotDsl)var9;
            int var23 = var0.clickCountSingleSlotToLess(var22.getGoal().getCount() + var0.getCursorGoal().getCount(), var22.getGoal().getCount());

            do {
               Object var26;
               DiffCalculatorInstance$CompareSlotDsl var32 = (DiffCalculatorInstance$CompareSlotDsl)(var26 = var2.next());
               int var33 = var0.clickCountSingleSlotToLess(var32.getGoal().getCount() + var0.getCursorGoal().getCount(), var32.getGoal().getCount());
               if (var23 > var33) {
                  var9 = var26;
                  var23 = var33;
               }
            } while (var2.hasNext());

            var34 = (DiffCalculatorInstance$CompareSlotDsl)var9;
         }
      }

      DiffCalculatorInstance$CompareSlotDsl var10;
      if ((var10 = var34) != null) {
         return var10.getSlotIndex();
      } else {
         throw new NoRoomException("No room to arrange items. You may try to clear cursor.");
      }
   }
}
