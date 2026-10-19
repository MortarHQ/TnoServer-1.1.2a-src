package org.anti_ad.mc.ipnext.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.anti_ad.mc.ipnext.item.rule.CountSink;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SourceDebugExtension(
   "SMAP\nBucket.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Bucket.kt\norg/anti_ad/mc/ipnext/util/MutableBucket\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,152:1\n1#2:153\n1563#3:154\n1634#3,3:155\n1761#3,3:158\n1740#3,3:161\n1563#3:164\n1634#3,3:165\n1761#3,3:168\n*S KotlinDebug\n*F\n+ 1 Bucket.kt\norg/anti_ad/mc/ipnext/util/MutableBucket\n*L\n89#1:154\n89#1:155,3\n92#1:158,3\n96#1:161,3\n103#1:164\n103#1:165,3\n106#1:168,3\n*E\n"
)
public class MutableBucket implements Bucket {
   @NotNull
   private final Map innerMap;

   protected MutableBucket(@NotNull Map var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      super();
      this.innerMap = MapsKt.toMutableMap(var1);
   }

   public MutableBucket() {
      this(MapsKt.emptyMap());
   }

   protected boolean validateEmpty(@NotNull CountSink var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      return false;
   }

   @Override
   public int getSize() {
      Iterable var1 = this.innerMap.entrySet();
      int var2 = 0;

      for (Object var3 : var1) {
         Entry var5 = (Entry)var3;
         int var7 = var2;
         var2 = ((Number)var5.getValue()).intValue();
         var2 = var7 + var2;
      }

      return var2;
   }

   @Override
   public int count(@NotNull CountSink var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      return this.innerMap.getOrDefault(var1, 0).intValue();
   }

   public final boolean add(@NotNull CountSink var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      return this.add(var1, 1);
   }

   public final boolean add(@NotNull CountSink var1, int var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      if (!this.validateEmpty(var1) && var2 > 0) {
         var1.setCountSource(this);
         this.innerMap.put(var1, this.count(var1) + var2);
         return true;
      } else {
         return false;
      }
   }

   @Override
   public boolean contains(@NotNull CountSink var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      return this.contains(var1, 1);
   }

   @Override
   public boolean contains(@NotNull CountSink var1, int var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      return this.validateEmpty(var1) || this.count(var1) >= var2;
   }

   public final boolean remove(@NotNull CountSink var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      return this.remove(var1, 1);
   }

   public final boolean remove(@NotNull CountSink var1, int var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      if (this.validateEmpty(var1) || var2 <= 0) {
         return false;
      }

      if (this.count(var1) <= 0) {
         return false;
      }

      this.innerMap.put(var1, this.count(var1) - var2);
      if (this.count(var1) <= 0) {
         this.innerMap.remove(var1);
      }

      return true;
   }

   public final boolean addAll(@NotNull Bucket var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      Iterable var5;
      Iterable var2 = var5 = var1.getEntrySet();
      var var6 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var5, 10));

      for (Entry var3 : var2) {
         var3 = var3;
         ArrayList var4 = var6;
         var4.add(this.add((CountSink)var3.getKey(), ((Number)var3.getValue()).intValue()));
      }

      Iterable var7;
      if (!((Collection)(var7 = var6)).isEmpty()) {
         Iterator var9 = var7.iterator();

         while (var9.hasNext()) {
            if ((Boolean)var9.next()) {
               return true;
            }
         }
      }

      return false;
   }

   @Override
   public boolean containsAll(@NotNull Bucket var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      Iterable var3;
      if (!((var3 = var1.getEntrySet()) instanceof Collection) || !((Collection)var3).isEmpty()) {
         for (Entry var2 : var3) {
            if (!this.contains((CountSink)var2.getKey(), ((Number)var2.getValue()).intValue())) {
               return false;
            }
         }
      }

      return true;
   }

   public final boolean removeAll(@NotNull Bucket var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      Iterable var5;
      Iterable var2 = var5 = var1.getEntrySet();
      var var6 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var5, 10));

      for (Entry var3 : var2) {
         var3 = var3;
         ArrayList var4 = var6;
         var4.add(this.remove((CountSink)var3.getKey(), ((Number)var3.getValue()).intValue()));
      }

      Iterable var7;
      if (!((Collection)(var7 = var6)).isEmpty()) {
         Iterator var9 = var7.iterator();

         while (var9.hasNext()) {
            if ((Boolean)var9.next()) {
               return true;
            }
         }
      }

      return false;
   }

   @NotNull
   @Override
   public Set getElementSet() {
      return this.innerMap.keySet();
   }

   @NotNull
   @Override
   public Set getEntrySet() {
      return this.innerMap.entrySet();
   }

   @NotNull
   @Override
   public Map getAsMap() {
      return this.innerMap;
   }

   @Override
   public boolean isEmpty() {
      return this.innerMap.isEmpty();
   }

   public final void clear() {
      this.innerMap.clear();
   }

   @NotNull
   public MutableBucket copyAsMutable() {
      return new MutableBucket(this.innerMap);
   }

   @Override
   public final boolean equals(@Nullable Object var1) {
      if (this == var1) {
         return true;
      } else {
         return !(var1 instanceof MutableBucket) ? false : Intrinsics.areEqual(this.innerMap, ((MutableBucket)var1).innerMap);
      }
   }

   @Override
   public final int hashCode() {
      return this.innerMap.hashCode();
   }
}
