package org.anti_ad.mc.ipnext.item;

import java.util.Comparator;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.item.CreativeModeTab;
import org.anti_ad.mc.ipnext.config.CreativeMenuSortOrder;
import org.anti_ad.mc.ipnext.config.ModSettings;
import org.anti_ad.mc.ipnext.mixinhelpers.IMixinItemGroup;
import org.jetbrains.annotations.Nullable;

@SourceDebugExtension(
   "SMAP\nItemTypeExtensions.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ItemTypeExtensions.kt\norg/anti_ad/mc/ipnext/item/ItemGroupComparator\n+ 2 ItemTypeExtensions.kt\norg/anti_ad/mc/ipnext/item/ItemTypeExtensionsKt\n*L\n1#1,494:1\n146#2,16:495\n*S KotlinDebug\n*F\n+ 1 ItemTypeExtensions.kt\norg/anti_ad/mc/ipnext/item/ItemGroupComparator\n*L\n199#1:495,16\n*E\n"
)
final class ItemGroupComparator implements Comparator {
   public ItemGroupComparator() {
   }

   public final int compare(@Nullable CreativeModeTab var1, @Nullable CreativeModeTab var2) {
      Function0 var3 = ItemGroupComparator::compare$lambda$0;
      Function0 var4 = ItemGroupComparator::compare$lambda$1;
      return ModSettings.INSTANCE.getCREATIVE_SORT_ORDER_TYPE().getValue() == CreativeMenuSortOrder.SEARCH_TAB
         ? ((Number)var3.invoke()).intValue()
         : ((Number)var4.invoke()).intValue();
   }

   private static final int compare$lambda$0(CreativeModeTab var0, CreativeModeTab var1) {
      if (var0 == null && var1 == null) {
         return 0;
      } else if (var0 == null) {
         return -1;
      } else if (var1 == null) {
         return 1;
      } else {
         return var0.row() == var1.row() ? Intrinsics.compare(var0.column(), var1.column()) : var0.row().compareTo((Enum)var1.row());
      }
   }

   private static final int compare$lambda$1(CreativeModeTab var0, CreativeModeTab var1) {
      if (var0 == null && var1 == null) {
         return 0;
      }

      if (var0 == null) {
         return -1;
      }

      if (var1 == null) {
         return 1;
      }

      int var10000;
      if (var0 instanceof IMixinItemGroup) {
         int var2;
         ComponentContents var3;
         if ((var2 = ((IMixinItemGroup)var0).getIPNPriorityIndex()) == -1 && (var3 = var0.getDisplayName().getContents()) instanceof TranslatableContents) {
            var2 = ItemTypeExtensionsObject.INSTANCE.getTranslationKeysPriorityList().indexOf(((TranslatableContents)var3).getKey());
            ((IMixinItemGroup)var0).setIPNPriorityIndex(var2 == -1 ? Integer.MAX_VALUE : var2);
         }

         var10000 = var2;
      } else {
         var10000 = Integer.MAX_VALUE;
      }

      var0 = var1;
      int var10001;
      if (var1 instanceof IMixinItemGroup) {
         int var5;
         ComponentContents var6;
         if ((var5 = ((IMixinItemGroup)var0).getIPNPriorityIndex()) == -1 && (var6 = var0.getDisplayName().getContents()) instanceof TranslatableContents) {
            var5 = ItemTypeExtensionsObject.INSTANCE.getTranslationKeysPriorityList().indexOf(((TranslatableContents)var6).getKey());
            ((IMixinItemGroup)var0).setIPNPriorityIndex(var5 == -1 ? Integer.MAX_VALUE : var5);
         }

         var10001 = var5;
      } else {
         var10001 = Integer.MAX_VALUE;
      }

      return Intrinsics.compare(var10000, var10001);
   }
}
