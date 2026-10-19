package org.anti_ad.mc.ipnext.item;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.CreativeModeTab.Type;
import org.anti_ad.mc.ipnext.config.ModSettings;
import org.anti_ad.mc.ipnext.mixinhelpers.IMixinItemGroup;
import org.jetbrains.annotations.NotNull;

@SourceDebugExtension(
   "SMAP\nItemTypeExtensions.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ItemTypeExtensions.kt\norg/anti_ad/mc/ipnext/item/ItemTypeExtensionsObject\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,494:1\n1869#2,2:495\n1869#2,2:497\n1869#2,2:499\n1#3:501\n*S KotlinDebug\n*F\n+ 1 ItemTypeExtensions.kt\norg/anti_ad/mc/ipnext/item/ItemTypeExtensionsObject\n*L\n443#1:495,2\n446#1:497,2\n464#1:499,2\n*E\n"
)
public final class ItemTypeExtensionsObject {
   @NotNull
   public static final ItemTypeExtensionsObject INSTANCE = new ItemTypeExtensionsObject();
   @NotNull
   private static final List translationKeysPriorityList = new ArrayList();
   @NotNull
   private static final ThreadLocal local;

   private ItemTypeExtensionsObject() {
   }

   @NotNull
   public final List getTranslationKeysPriorityList() {
      return translationKeysPriorityList;
   }

   public final void priorityListChanged() {
      ItemTypeExtensionsKt.access$initGroupIndex();
      translationKeysPriorityList.clear();
      List var10000 = CreativeModeTabs.allTabs();
      Intrinsics.checkNotNullExpressionValue(var10000, "");
      Iterator var1 = var10000.iterator();

      while (var1.hasNext()) {
         CreativeModeTab var2;
         Intrinsics.checkNotNull(var2 = (CreativeModeTab)var1.next());
         ((IMixinItemGroup)var2).setIPNPriorityIndex(-1);
      }

      CharSequence var6 = ModSettings.INSTANCE.getCATEGORY_PRIORITY_LIST().getValue();
      String[] var3;
      (var3 = new String[1])[0] = ",";

      for (String var5 : StringsKt.split$default(var6, var3, false, 0, 6, null)) {
         translationKeysPriorityList.add(StringsKt.trim(var5).toString());
      }
   }

   public final void defaultOrderListChanged() {
      if ((Boolean)local.get()) {
         ItemTypeExtensionsKt.access$initGroupIndex();
         local.set(Boolean.FALSE);
         ModSettings.INSTANCE.getCATEGORY_ORIGINAL_ORDER().setValue(this.makeDefaultList());
         local.set(Boolean.TRUE);
      }
   }

   @NotNull
   public final String makeDefaultList() {
      String var1 = "";
      List var10000 = CreativeModeTabs.allTabs();
      Intrinsics.checkNotNullExpressionValue(var10000, "");
      Iterator var2 = var10000.iterator();

      while (var2.hasNext()) {
         CreativeModeTab var3;
         ComponentContents var4;
         if ((var3 = (CreativeModeTab)var2.next()).getType() == Type.CATEGORY && (var4 = var3.getDisplayName().getContents()) instanceof TranslatableContents) {
            String var5;
            if (!Intrinsics.areEqual(var1, "")) {
               var5 = var1 + ", " + ((TranslatableContents)var4).getKey();
            } else {
               var5 = ((TranslatableContents)var4).getKey();
               Intrinsics.checkNotNullExpressionValue(var5, "");
            }

            var1 = var5;
         }
      }

      return var1;
   }

   static {
      ThreadLocal var0;
      (var0 = new ThreadLocal()).set(Boolean.TRUE);
      local = var0;
   }
}
