package org.anti_ad.mc.ipnext.item.rule.natives;

import java.util.ArrayList;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import org.anti_ad.mc.alias.registry.RegistryExKt;
import org.anti_ad.mc.common.extensions.Kt_commonKt;
import org.anti_ad.mc.ipnext.Log;
import org.anti_ad.mc.ipnext.item.ItemType;
import org.anti_ad.mc.ipnext.item.NbtUtils;
import org.anti_ad.mc.ipnext.item.NbtUtils$NbtPath;
import org.anti_ad.mc.ipnext.item.NbtUtils$WrappedTag;
import org.anti_ad.mc.ipnext.item.rule.parameter.Match;
import org.anti_ad.mc.ipnext.item.rule.parameter.NativeParametersKt;
import org.jetbrains.annotations.NotNull;

@SourceDebugExtension(
   "SMAP\nNativeRule.kt\nKotlin\n*S Kotlin\n*F\n+ 1 NativeRule.kt\norg/anti_ad/mc/ipnext/item/rule/natives/ByNbtRule$ByNbtPathComparator\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,456:1\n1563#2:457\n1634#2,3:458\n1563#2:461\n1634#2,3:462\n*S KotlinDebug\n*F\n+ 1 NativeRule.kt\norg/anti_ad/mc/ipnext/item/rule/natives/ByNbtRule$ByNbtPathComparator\n*L\n369#1:457\n369#1:458,3\n370#1:461\n370#1:462,3\n*E\n"
)
public final class ByNbtRule$ByNbtPathComparator {
   @NotNull
   private final ItemType itemType1;
   @NotNull
   private final ItemType itemType2;

   public ByNbtRule$ByNbtPathComparator(@NotNull ByNbtRule var1, @NotNull ItemType var2, ItemType var3) {
      Intrinsics.checkNotNullParameter(var2, "");
      Intrinsics.checkNotNullParameter(var3, "");
      this.this$0 = var1;
      super();
      this.itemType1 = var2;
      this.itemType2 = var3;
   }

   @NotNull
   public final ItemType getItemType1() {
      return this.itemType1;
   }

   @NotNull
   public final ItemType getItemType2() {
      return this.itemType2;
   }

   public final int compare() {
      Registry var10000 = BuiltInRegistries.DATA_COMPONENT_TYPE;
      Intrinsics.checkNotNullExpressionValue(BuiltInRegistries.DATA_COMPONENT_TYPE, "");
      DataComponentType var1 = (DataComponentType)RegistryExKt._get_/* $VF was: (get) */(
         var10000, (ResourceLocation)this.this$0.getArguments().get(NativeParametersKt.getComponent_id())
      );
      List var2 = ((NbtUtils$NbtPath)this.this$0.getArguments().get(NativeParametersKt.getNbt_path())).getTags(this.itemType1, var1);
      List var5 = ((NbtUtils$NbtPath)this.this$0.getArguments().get(NativeParametersKt.getNbt_path())).getTags(this.itemType2, var1);
      if (var2.size() > 1 || var5.size() > 1) {
         Log.INSTANCE.warn("given nbt path produce more than one result. currently support only the first result");
      }

      NbtUtils$WrappedTag var8 = (NbtUtils$WrappedTag)CollectionsKt.firstOrNull(var2);
      NbtUtils$WrappedTag var7 = var8 != null ? var8.copy() : null;
      NbtUtils$WrappedTag var9 = (NbtUtils$WrappedTag)CollectionsKt.firstOrNull(var5);
      NbtUtils$WrappedTag var6 = var9 != null ? var9.copy() : null;
      boolean var3 = var7 == null;
      boolean var4 = var6 == null;
      if (var3 != var4) {
         return ((Match)this.this$0.getArguments().get(NativeParametersKt.getNot_found())).getMultiplier() * (var3 ? -1 : 1);
      } else {
         return var7 != null && var6 != null ? this.compareTag(var7, var6) : 0;
      }
   }

   public final int compareTag(@NotNull NbtUtils$WrappedTag var1, @NotNull NbtUtils$WrappedTag var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      Integer var10000 = var1.isNumber()
         ? (var2.isNumber() ? this.compareAsNumber(var1, var2) : null)
         : (
            var1.isCompound()
               ? (var2.isCompound() ? this.compareAsCompound(var1, var2) : null)
               : (var1.isList() ? (var2.isList() ? this.compareAsList(var1, var2) : null) : null)
         );
      return var10000 != null ? var10000 : this.compareAsString(var1, var2);
   }

   public final int compareAsString(@NotNull NbtUtils$WrappedTag var1, @NotNull NbtUtils$WrappedTag var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      return this.this$0.getDummyStringRule().compareString(var1.getAsString(), var2.getAsString());
   }

   public final int compareAsNumber(@NotNull NbtUtils$WrappedTag var1, @NotNull NbtUtils$WrappedTag var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      return this.this$0.getDummyNumberRule().compareNumber(var1.getAsNumber(), var2.getAsNumber());
   }

   public final int compareAsCompound(@NotNull NbtUtils$WrappedTag var1, @NotNull NbtUtils$WrappedTag var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      return NbtUtils.INSTANCE.compareNbt(var1.getAsCompound(), var2.getAsCompound());
   }

   public final int compareAsList(@NotNull NbtUtils$WrappedTag var1, @NotNull NbtUtils$WrappedTag var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      Iterable var3;
      Iterable var7 = var3 = var1.getAsList();
      var3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var3, 10));

      for (Object var4 : var7) {
         NbtUtils$WrappedTag var5 = (NbtUtils$WrappedTag)var4;
         ArrayList var6 = var3;
         var6.add(Kt_commonKt.asComparable(var5, this::compareTag));
      }

      List var9 = var3;
      Iterable var10;
      var3 = var10 = var2.getAsList();
      var var11 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var10, 10));

      for (Object var17 : var3) {
         NbtUtils$WrappedTag var14 = (NbtUtils$WrappedTag)var17;
         ArrayList var18 = var11;
         var18.add(Kt_commonKt.asComparable(var14, this::compareTag));
      }

      var3 = var11;
      return Kt_commonKt.compareTo(var9, var3);
   }
}
