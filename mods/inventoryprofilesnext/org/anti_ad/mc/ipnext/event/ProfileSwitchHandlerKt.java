package org.anti_ad.mc.ipnext.event;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import org.anti_ad.mc.alias.registry.RegistryExKt;
import org.anti_ad.mc.ipnext.item.ComponentUtils;
import org.anti_ad.mc.ipnext.item.ItemStack;
import org.anti_ad.mc.ipnext.item.ItemType;
import org.anti_ad.mc.ipnext.item.NbtUtils;
import org.anti_ad.mc.ipnext.profiles.config.ProfileComponentData;
import org.anti_ad.mc.ipnext.profiles.config.ProfileItemData;

@SourceDebugExtension(
   "SMAP\nProfileSwitchHandler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ProfileSwitchHandler.kt\norg/anti_ad/mc/ipnext/event/ProfileSwitchHandlerKt\n+ 2 ItemTypeExtensions.kt\norg/anti_ad/mc/ipnext/item/ItemTypeExtensionsKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,414:1\n125#2:415\n123#2:416\n106#2:417\n127#2:418\n106#2:419\n131#2:420\n112#2:421\n1869#3:422\n774#3:423\n865#3,2:424\n1870#3:426\n*S KotlinDebug\n*F\n+ 1 ProfileSwitchHandler.kt\norg/anti_ad/mc/ipnext/event/ProfileSwitchHandlerKt\n*L\n373#1:415\n373#1:416\n373#1:417\n373#1:418\n373#1:419\n374#1:420\n374#1:421\n387#1:422\n395#1:423\n395#1:424,2\n387#1:426\n*E\n"
)
public final class ProfileSwitchHandlerKt {
   private static final boolean match(ProfileItemData var0, ItemStack var1) {
      ItemType var3;
      ItemType var2 = var3 = var1.getItemType();
      net.minecraft.world.item.ItemStack var10000 = new net.minecraft.world.item.ItemStack;
      ItemLike var10002 = (ItemLike)var2.getItem();
      PatchedDataComponentMap var10004 = var2.getTag();
      if (var10004 == null) {
         DataComponentMap var12 = DataComponentMap.EMPTY;
         Intrinsics.checkNotNull(DataComponentMap.EMPTY);
         var10004 = (PatchedDataComponentMap)var12;
      }

      var10000./* $VF: Unable to resugar constructor */<init>(var10002, 1, var10004);
      String var8;
      if (var10000.getComponents().get(DataComponents.CUSTOM_NAME) != null) {
         var2 = var3;
         var10000 = new net.minecraft.world.item.ItemStack;
         var10002 = (ItemLike)var2.getItem();
         var10004 = var2.getTag();
         if (var10004 == null) {
            DataComponentMap var14 = DataComponentMap.EMPTY;
            Intrinsics.checkNotNull(DataComponentMap.EMPTY);
            var10004 = (PatchedDataComponentMap)var14;
         }

         var10000./* $VF: Unable to resugar constructor */<init>(var10002, 1, var10004);
         var8 = var10000.getDisplayName().getString();
         Intrinsics.checkNotNullExpressionValue(var8, "");
      } else {
         var8 = "";
      }

      String var5 = var8;
      String var9 = var0.getItemId();
      var3 = var1.getItemType();
      ResourceLocation var10001 = BuiltInRegistries.ITEM.getKey(var3.getItem());
      Intrinsics.checkNotNullExpressionValue(var10001, "");
      String var10 = var10001.toString();
      Intrinsics.checkNotNullExpressionValue(var10, "");
      return Intrinsics.areEqual(var9, var10)
         && (
            !StringsKt.isBlank(var0.getCustomName()) && !StringsKt.isBlank(var5) && Intrinsics.areEqual(var0.getCustomName(), var5)
               || match(var0.getComponents(), var1)
         );
   }

   private static final boolean match(List var0, ItemStack var1) {
      DataComponentPatch var8 = var1.getItemType().getChanges();
      Collection var2;
      if ((var2 = var0) == null || var2.isEmpty()) {
         return true;
      }

      for (ProfileComponentData var10 : var0) {
         Registry var10000 = BuiltInRegistries.DATA_COMPONENT_TYPE;
         Intrinsics.checkNotNullExpressionValue(BuiltInRegistries.DATA_COMPONENT_TYPE, "");
         DataComponentType var3;
         if ((var3 = (DataComponentType)RegistryExKt._get_/* $VF was: (get) */(var10000, var10.getId())) != null) {
            Optional var4;
            if ((var4 = var8.get(var3)) == null || var4.isEmpty()) {
               return false;
            }

            Tag var15 = ComponentUtils.INSTANCE.toFilteredNbtOrNull(var3, var4);
            if (var15 == null) {
               var15 = (Tag)(new CompoundTag());
            }

            Tag var13 = var15;
            Tag var11 = var10.getComponentNbt();
            if (Intrinsics.areEqual(var3, DataComponents.ENCHANTMENTS) && var11 instanceof ListTag && var13 instanceof ListTag) {
               Iterable var6 = (Iterable)var13;
               var var9 = new ArrayList();
               Iterator var7 = var6.iterator();

               while (var7.hasNext()) {
                  Object var12;
                  Tag var14 = (Tag)(var12 = var7.next());
                  if (((ListTag)var11).contains(var14)) {
                     var9.add(var12);
                  }
               }

               if (var9.size() == ((ListTag)var11).size()) {
                  return true;
               }

               return false;
            }

            if (NbtUtils.INSTANCE.compareTo(var11, var13) != 0) {
               return false;
            }
         }
      }

      return true;
   }

   private static final Integer findIn(Iterable var0, List var1, Function2 var2) {
      for (ProfileItemData var3 : var0) {
         Integer var5;
         if ((var5 = (Integer)var2.invoke(var3, var1)) != null) {
            return var5;
         }
      }

      return null;
   }
}
