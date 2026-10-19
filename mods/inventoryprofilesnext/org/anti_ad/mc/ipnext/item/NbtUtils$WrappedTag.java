package org.anti_ad.mc.ipnext.item;

import java.util.ArrayList;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.nbt.CollectionTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NumericTag;
import net.minecraft.nbt.Tag;
import org.anti_ad.mc.common.extensions.Kt_commonKt;
import org.jetbrains.annotations.NotNull;

@SourceDebugExtension(
   "SMAP\nNbtUtils.kt\nKotlin\n*S Kotlin\n*F\n+ 1 NbtUtils.kt\norg/anti_ad/mc/ipnext/item/NbtUtils$WrappedTag\n+ 2 VanillaAccessors.kt\norg/anti_ad/mc/ipnext/ingame/VanillaAccessorsKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,282:1\n166#2:283\n166#2:284\n166#2:285\n166#2:286\n168#2:287\n1563#3:288\n1634#3,3:289\n1563#3:292\n1634#3,3:293\n*S KotlinDebug\n*F\n+ 1 NbtUtils.kt\norg/anti_ad/mc/ipnext/item/NbtUtils$WrappedTag\n*L\n211#1:283\n213#1:284\n215#1:285\n217#1:286\n222#1:287\n230#1:288\n230#1:289,3\n234#1:292\n234#1:293,3\n*E\n"
)
public final class NbtUtils$WrappedTag {
   @NotNull
   private final Tag value;

   public NbtUtils$WrappedTag(@NotNull Tag var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      super();
      this.value = var1;
   }

   @NotNull
   public final Tag getValue() {
      return this.value;
   }

   public final boolean isString() {
      return this.value.getId() == 8;
   }

   public final boolean isNumber() {
      byte var1;
      return (var1 = this.value.getId()) > 0 ? var1 < 7 : false;
   }

   public final boolean isCompound() {
      return this.value.getId() == 10;
   }

   public final boolean isList() {
      Integer[] var1;
      (var1 = new Integer[4])[0] = 7;
      var1[1] = 9;
      var1[2] = 11;
      var1[3] = 12;
      return CollectionsKt.listOf(var1).contains(Integer.valueOf(this.value.getId()));
   }

   @NotNull
   public final String getAsString() {
      String var10000 = this.value.getAsString();
      Intrinsics.checkNotNullExpressionValue(var10000, "");
      return var10000;
   }

   @NotNull
   public final Number getAsNumber() {
      Tag var1 = this.value;
      return (this.value instanceof NumericTag ? (NumericTag)var1 : null) != null
         ? (this.value instanceof NumericTag ? (NumericTag)var1 : null).getAsDouble()
         : 0;
   }

   public final double getAsDouble() {
      Tag var1 = this.value;
      return (this.value instanceof NumericTag ? (NumericTag)var1 : null) != null
         ? (this.value instanceof NumericTag ? (NumericTag)var1 : null).getAsDouble()
         : 0.0;
   }

   @NotNull
   public final CompoundTag getAsCompound() {
      Tag var1 = this.value;
      CompoundTag var10000 = this.value instanceof CompoundTag ? (CompoundTag)var1 : null;
      if ((this.value instanceof CompoundTag ? (CompoundTag)var1 : null) == null) {
         var10000 = new CompoundTag();
      }

      return var10000;
   }

   @NotNull
   public final List getAsList() {
      Tag var1 = this.value;
      CollectionTag var10000 = this.value instanceof CollectionTag ? (CollectionTag)var1 : null;
      if ((this.value instanceof CollectionTag ? (CollectionTag)var1 : null) == null) {
         return CollectionsKt.emptyList();
      }

      Iterable var5;
      Iterable var2 = var5 = (Iterable)var10000;
      var var6 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var5, 10));

      for (Tag var3 : var2) {
         var3 = var3;
         ArrayList var4 = var6;
         Intrinsics.checkNotNull(var3);
         var4.add(new NbtUtils$WrappedTag(var3));
      }

      return var6;
   }

   @NotNull
   public final List getAsListUnwrapped() {
      Tag var1 = this.value;
      CollectionTag var10000 = this.value instanceof CollectionTag ? (CollectionTag)var1 : null;
      if ((this.value instanceof CollectionTag ? (CollectionTag)var1 : null) != null) {
         List var2 = CollectionsKt.toList((Iterable)var10000);
         if (var2 != null) {
            return var2;
         }
      }

      return CollectionsKt.emptyList();
   }

   @NotNull
   public final List getAsListComparable() {
      Iterable var1;
      Iterable var2 = var1 = this.getAsListUnwrapped();
      var1 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var1, 10));

      for (Object var3 : var2) {
         Tag var7 = (Tag)var3;
         ArrayList var4 = var1;
         var4.add(Kt_commonKt.asComparable(var7, NbtUtils$WrappedTag::_get_asListComparable_$lambda$2$lambda$1));
      }

      return var1;
   }

   public final boolean sameType(@NotNull NbtUtils$WrappedTag var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      return this.isList() && var1.isList()
         || this.isString() && var1.isString()
         || this.isCompound() && var1.isCompound()
         || this.isNumber() && var1.isNumber();
   }

   @NotNull
   public final NbtUtils$WrappedTag copy() {
      Tag var10002 = this.value.copy();
      Intrinsics.checkNotNullExpressionValue(var10002, "");
      return new NbtUtils$WrappedTag(var10002);
   }

   private static final int _get_asListComparable_$lambda$2$lambda$1(Tag var0, Tag var1) {
      NbtUtils var10000 = NbtUtils.INSTANCE;
      Intrinsics.checkNotNull(var0);
      Intrinsics.checkNotNull(var1);
      return var10000.compareTo(var0, var1);
   }
}
