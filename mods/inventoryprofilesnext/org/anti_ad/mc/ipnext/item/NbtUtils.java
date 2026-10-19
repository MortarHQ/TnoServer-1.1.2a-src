package org.anti_ad.mc.ipnext.item;

import com.mojang.brigadier.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.jvm.internal.Ref.ObjectRef;
import net.minecraft.commands.arguments.NbtPathArgument;
import net.minecraft.commands.arguments.NbtPathArgument.NbtPath;
import net.minecraft.core.DefaultedRegistry;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.TagParser;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import org.anti_ad.mc.common.extensions.Kt_commonKt;
import org.anti_ad.mc.ipnext.Log;
import org.anti_ad.mc.ipnext.ingame.VanillaAccessorsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SourceDebugExtension(
   "SMAP\nNbtUtils.kt\nKotlin\n*S Kotlin\n*F\n+ 1 NbtUtils.kt\norg/anti_ad/mc/ipnext/item/NbtUtils\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 common.kt\norg/anti_ad/mc/common/extensions/CommonKt\n+ 4 kt_common.kt\norg/anti_ad/mc/common/extensions/Kt_commonKt\n*L\n1#1,282:1\n1563#2:283\n1634#2,3:284\n1563#2:287\n1634#2,3:288\n1563#2:338\n1634#2,3:339\n70#3,14:291\n70#3,14:305\n42#3,12:320\n37#3:332\n89#3,4:333\n38#3:337\n87#4:319\n*S KotlinDebug\n*F\n+ 1 NbtUtils.kt\norg/anti_ad/mc/ipnext/item/NbtUtils\n*L\n91#1:283\n91#1:284,3\n92#1:287\n92#1:288,3\n71#1:338\n71#1:339,3\n120#1:291,14\n124#1:305,14\n262#1:320,12\n267#1:332\n267#1:333,4\n267#1:337\n153#1:319\n*E\n"
)
public final class NbtUtils {
   @NotNull
   public static final NbtUtils INSTANCE = new NbtUtils();

   private NbtUtils() {
   }

   @Nullable
   public final Item getItemFromId(@NotNull ResourceLocation var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      DefaultedRegistry var10000 = BuiltInRegistries.ITEM;
      Intrinsics.checkNotNullExpressionValue(BuiltInRegistries.ITEM, "");
      return (Item)VanillaAccessorsKt._getByIdentifier_/* $VF was: (getByIdentifier) */(var10000, var1);
   }

   @Nullable
   public final List getTagFromId(@NotNull ResourceLocation var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      ObjectRef var2 = new ObjectRef();
      BuiltInRegistries.ITEM.getTags().forEach(NbtUtils::getTagFromId$lambda$2);
      return (List)var2.element;
   }

   public final int compareNbt(@Nullable CompoundTag var1, @Nullable CompoundTag var2) {
      boolean var3 = var1 == null;
      boolean var4 = var2 == null;
      if (var3 != var4) {
         return var3 ? -1 : 1;
      }

      if (var1 != null && var2 != null) {
         Set var10000 = var1.getAllKeys();
         Intrinsics.checkNotNullExpressionValue(var10000, "");
         List var10 = CollectionsKt.sorted(var10000);
         var10000 = var2.getAllKeys();
         Intrinsics.checkNotNullExpressionValue(var10000, "");
         List var16 = CollectionsKt.sorted(var10000);
         Iterable var11;
         Iterable var5 = var11 = var10;
         var var6 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var11, 10));

         for (Object var18 : var5) {
            String var7 = (String)var18;
            ArrayList var8 = var6;
            var8.add(Kt_commonKt.asComparable(TuplesKt.to(var7, var1.get(var7)), INSTANCE::compareStringTag));
         }

         List var9 = var6;
         Iterable var13;
         Iterable var20 = var13 = var16;
         var var14 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var13, 10));

         for (Object var21 : var20) {
            String var17 = (String)var21;
            ArrayList var22 = var14;
            var22.add(Kt_commonKt.asComparable(TuplesKt.to(var17, var2.get(var17)), INSTANCE::compareStringTag));
         }

         List var15 = var14;
         return Kt_commonKt.compareTo(var9, var15);
      } else {
         return 0;
      }
   }

   private final int compareStringTag(Pair var1, Pair var2) {
      String var3 = (String)var1.component1();
      Tag var5 = (Tag)var1.component2();
      String var4 = (String)var2.component1();
      Tag var6 = (Tag)var2.component2();
      int var7;
      if ((var7 = var3.compareTo(var4)) != 0) {
         return var7;
      } else {
         return var5 != null && var6 != null ? this.compareTo(var5, var6) : 0;
      }
   }

   public final int compareTo(@NotNull Tag var1, @NotNull Tag var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      NbtUtils$WrappedTag var3 = new NbtUtils$WrappedTag(var1);
      NbtUtils$WrappedTag var4 = new NbtUtils$WrappedTag(var2);
      Integer var10000 = var3.isNumber()
         ? (var4.isNumber() ? Double.compare(var3.getAsDouble(), var4.getAsDouble()) : null)
         : (
            var3.isCompound()
               ? (var4.isCompound() ? this.compareNbt(var3.getAsCompound(), var4.getAsCompound()) : null)
               : (var3.isList() ? (var4.isList() ? Kt_commonKt.compareTo(var3.getAsListComparable(), var4.getAsListComparable()) : null) : null)
         );
      return var10000 != null ? var10000 : var3.getAsString().compareTo(var4.getAsString());
   }

   @Nullable
   public final CompoundTag parseNbt(@NotNull String var1) {
      Intrinsics.checkNotNullParameter(var1, "");

      try {
         var3 = TagParser.parseTag(var1);
      } catch (Throwable var2) {
         var2.printStackTrace();
         var3 = null;
      }

      return var3;
   }

   @NotNull
   public final Tag parseNbtOrEmpty(@NotNull String var1) {
      Intrinsics.checkNotNullParameter(var1, "");

      try {
         var3 = new TagParser(new StringReader(var1)).readValue();
      } catch (Throwable var2) {
         var2.printStackTrace();
         var3 = null;
      }

      Tag var10000 = var3;
      if (var3 == null) {
         var10000 = (Tag)(new CompoundTag());
      }

      return var10000;
   }

   public final boolean matchNbtNoExtra(@Nullable Tag var1, @Nullable Tag var2) {
      if (var1 == null) {
         return true;
      }

      if (var2 == null) {
         return false;
      }

      NbtUtils$WrappedTag var3 = new NbtUtils$WrappedTag(var1);
      NbtUtils$WrappedTag var4 = new NbtUtils$WrappedTag(var2);
      if (!var3.sameType(var4)) {
         return false;
      }

      if (var3.isNumber()) {
         return Intrinsics.areEqual(var3.getAsNumber(), var4.getAsNumber());
      }

      if (var3.isString()) {
         return Intrinsics.areEqual(var3.getAsString(), var4.getAsString());
      }

      if (var3.isList()) {
         return ((ListTag)var1).size() == ((ListTag)var2).size() && ((ListTag)var1).containsAll((Collection)var2);
      }

      Tag var10000 = !((CompoundTag)var1).isEmpty() ? var1 : null;
      var1 = var2;
      CompoundTag var7 = (CompoundTag)var2;
      var2 = var10000;
      boolean var8 = var7.isEmpty();
      return Intrinsics.areEqual(var2, !var8 ? var1 : null);
   }

   public final boolean matchNbt(@Nullable Tag var1, @Nullable Tag var2) {
      if (var1 == null) {
         return true;
      } else if (var2 == null) {
         return false;
      } else {
         NbtUtils$WrappedTag var3 = new NbtUtils$WrappedTag(var1);
         NbtUtils$WrappedTag var4 = new NbtUtils$WrappedTag(var2);
         if (!var3.sameType(var4)) {
            return false;
         } else if (var3.isNumber()) {
            return Intrinsics.areEqual(var3.getAsNumber(), var4.getAsNumber());
         } else if (var3.isString()) {
            return Intrinsics.areEqual(var3.getAsString(), var4.getAsString());
         } else if (var3.isList()) {
            return ((ListTag)var2).containsAll((Collection)var1);
         } else if (((CompoundTag)var1).isEmpty() && ((CompoundTag)var2).isEmpty()) {
            return true;
         } else {
            return ((CompoundTag)var2).isEmpty() ? false : this.innerMatchNbt((CompoundTag)var1, (CompoundTag)var2);
         }
      }
   }

   private final boolean innerMatchNbt(CompoundTag var1, CompoundTag var2) {
      return net.minecraft.nbt.NbtUtils.compareNbt((Tag)var1, (Tag)var2, true);
   }

   private final NbtPath getNbtPath(String var1) {
      Log var2 = Log.INSTANCE;

      try {
         var5 = new NbtPathArgument().parse(new StringReader(var1));
      } catch (Throwable var3) {
         var1 = var3.toString();
         var2.warn(var1);
         var5 = null;
      }

      return var5;
   }

   private final List getTagsForPath(NbtPath var1, Tag var2) {
      List var3 = CollectionsKt.emptyList();

      try {
         List var10000 = var1.get(var2);
         Intrinsics.checkNotNullExpressionValue(var10000, "");
         var5 = var10000;
      } catch (Throwable var4) {
         var5 = var3;
      }

      return var5;
   }

   @Nullable
   public final Tag nullIfEmpty(@Nullable Tag var1) {
      if (var1 == null) {
         return null;
      } else {
         return (!(var1 instanceof CompoundTag) || !((CompoundTag)var1).isEmpty()) && (!(var1 instanceof ListTag) || !((ListTag)var1).isEmpty()) ? var1 : null;
      }
   }

   private static final Unit getTagFromId$lambda$1(ResourceLocation var0, ObjectRef var1, com.mojang.datafixers.util.Pair var2) {
      if (Intrinsics.areEqual(((TagKey)var2.getFirst()).location(), var0)) {
         Object var10001 = var2.getSecond();
         Intrinsics.checkNotNullExpressionValue(var10001, "");
         Iterable var5 = (Iterable)var10001;
         ObjectRef var3 = var1;
         Iterable var7 = var5;
         var var6 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var5, 10));

         for (Object var9 : var7) {
            Holder var10 = (Holder)var9;
            ArrayList var4 = var6;
            var4.add((Item)var10.value());
         }

         var3.element = var6;
      }

      return Unit.INSTANCE;
   }

   private static final void getTagFromId$lambda$2(Function1 var0, Object var1) {
      var0.invoke(var1);
   }
}
