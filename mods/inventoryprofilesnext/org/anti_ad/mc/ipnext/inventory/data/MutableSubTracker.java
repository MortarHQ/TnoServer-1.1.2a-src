package org.anti_ad.mc.ipnext.inventory.data;

import java.util.ArrayList;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IndexedValue;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.anti_ad.mc.ipnext.item.MutableItemStack;
import org.jetbrains.annotations.NotNull;

@SourceDebugExtension(
   "SMAP\nSubTracker.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SubTracker.kt\norg/anti_ad/mc/ipnext/inventory/data/MutableSubTracker\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,52:1\n1#2:53\n1563#3:54\n1634#3,3:55\n1563#3:58\n1634#3,3:59\n*S KotlinDebug\n*F\n+ 1 SubTracker.kt\norg/anti_ad/mc/ipnext/inventory/data/MutableSubTracker\n*L\n39#1:54\n39#1:55,3\n43#1:58\n43#1:59,3\n*E\n"
)
public final class MutableSubTracker implements SubTracker {
   @NotNull
   private final MutableItemTracker mainTracker;
   @NotNull
   private final List slotIndices;
   @NotNull
   private final Lazy slots$delegate;
   @NotNull
   private final Lazy indexedSlots$delegate;

   public MutableSubTracker(@NotNull MutableItemTracker var1, @NotNull List var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      super();
      this.mainTracker = var1;
      this.slotIndices = var2;
      this.slots$delegate = LazyKt.lazy(LazyThreadSafetyMode.NONE, MutableSubTracker::slots_delegate$lambda$1);
      this.indexedSlots$delegate = LazyKt.lazy(LazyThreadSafetyMode.NONE, MutableSubTracker::indexedSlots_delegate$lambda$3);
   }

   @NotNull
   public final MutableItemTracker getMainTracker() {
      return this.mainTracker;
   }

   @NotNull
   @Override
   public final List getSlotIndices() {
      return this.slotIndices;
   }

   @NotNull
   @Override
   public final List getSlots() {
      return (List)this.slots$delegate.getValue();
   }

   @NotNull
   @Override
   public final List getIndexedSlots() {
      return (List)this.indexedSlots$delegate.getValue();
   }

   @NotNull
   public final MutableSubTracker plus(@NotNull SubTracker var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      List var2 = CollectionsKt.distinct(CollectionsKt.plus(this.getSlotIndices(), var1.getSlotIndices()));
      return this.getMainTracker().subTracker(var2);
   }

   private static final List slots_delegate$lambda$1(MutableSubTracker var0) {
      Iterable var1;
      Iterable var2 = var1 = var0.getSlotIndices();
      var1 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var1, 10));

      for (Object var3 : var2) {
         int var7 = ((Number)var3).intValue();
         ArrayList var4 = var1;
         var4.add((MutableItemStack)var0.getMainTracker().getSlots().get(var7));
      }

      return var1;
   }

   private static final List indexedSlots_delegate$lambda$3(MutableSubTracker var0) {
      Iterable var1;
      Iterable var2 = var1 = var0.getSlotIndices();
      var1 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var1, 10));

      for (Object var3 : var2) {
         int var7 = ((Number)var3).intValue();
         ArrayList var4 = var1;
         var4.add(new IndexedValue(var7, var0.getMainTracker().getSlots().get(var7)));
      }

      return var1;
   }
}
