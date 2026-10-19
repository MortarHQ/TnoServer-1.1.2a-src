package org.anti_ad.mc.ipnext.item.rule.file;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import net.minecraft.resources.ResourceLocation;
import org.anti_ad.a.a.a.a.a;
import org.anti_ad.mc.alias.registry.RegistryExKt;
import org.anti_ad.mc.ipnext.Log;
import org.jetbrains.annotations.NotNull;

@SourceDebugExtension(
   "SMAP\nRuleFilePreprocessManager.kt\nKotlin\n*S Kotlin\n*F\n+ 1 RuleFilePreprocessManager.kt\norg/anti_ad/mc/ipnext/item/rule/file/RuleFilePreprocessManagerKt\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 _Strings.kt\nkotlin/text/StringsKt___StringsKt\n*L\n1#1,159:1\n1869#2,2:160\n1869#2:164\n1869#2,2:165\n1870#2:167\n1563#2:168\n1634#2,3:169\n1869#2,2:172\n1869#2,2:174\n1869#2,2:176\n1869#2,2:178\n1869#2,2:180\n1179#3,2:162\n*S KotlinDebug\n*F\n+ 1 RuleFilePreprocessManager.kt\norg/anti_ad/mc/ipnext/item/rule/file/RuleFilePreprocessManagerKt\n*L\n36#1:160,2\n62#1:164\n71#1:165,2\n62#1:167\n83#1:168\n83#1:169,3\n112#1:172,2\n127#1:174,2\n148#1:176,2\n151#1:178,2\n154#1:180,2\n47#1:162,2\n*E\n"
)
public final class RuleFilePreprocessManagerKt {
   @NotNull
   private static final Regex WHITESPACE = new Regex("\\s+");
   @NotNull
   private static final SortedSet itemSet = getAllItemsSet();

   @NotNull
   public static final List preprocessRules(@NotNull List var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      List var1 = new ArrayList();

      for (String var2 : var0) {
         var1.addAll(processWildcards(var2, itemSet));
      }

      Log.INSTANCE.trace(RuleFilePreprocessManagerKt::preprocessRules$lambda$1);
      return var1;
   }

   @NotNull
   public static final String getIndent(@NotNull String var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      String var1 = "";
      CharSequence var4 = var0;

      for (int var2 = 0; var2 < var4.length(); var2++) {
         char var3;
         switch (var3 = var4.charAt(var2)) {
            case '\t':
            case ' ':
            case '!':
               var1 = var1 + var3;
               break;
         }
      }

      return var1;
   }

   @NotNull
   public static final List processWildcards(@NotNull String var0, @NotNull SortedSet var1) {
      Intrinsics.checkNotNullParameter(var0, "");
      Intrinsics.checkNotNullParameter(var1, "");
      List var23;
      if (!StringsKt.contains$default(var0, '*', false, 2, null) && !StringsKt.contains$default(var0, '?', false, 2, null)) {
         String[] var11;
         (var11 = new String[1])[0] = var0;
         var23 = CollectionsKt.mutableListOf(var11);
      } else {
         String var2 = getIndent(var0);
         ArrayList var3 = new ArrayList();
         List var4 = new ArrayList();
         CharSequence var10000 = StringsKt.removePrefix(var0, var2);
         String[] var5;
         (var5 = new String[2])[0] = " ";
         var5[1] = "\t";
         Iterator var6 = StringsKt.split$default(var10000, var5, false, 0, 6, null).iterator();

         while (var6.hasNext()) {
            String var16;
            if (StringsKt.contains$default(var16 = (String)var6.next(), '*', false, 2, null) || StringsKt.contains$default(var16, '?', false, 2, null)) {
               String var7 = !StringsKt.contains$default(var16, ':', false, 2, null) ? "minecraft:" + var16 : var16;
               List var8 = new ArrayList();

               for (String var9 : var1) {
                  if (a.a(var7, var9)) {
                     Intrinsics.checkNotNull(var9);
                     var8.add(var9);
                  }
               }

               var4.add(var8);
            } else if (!StringsKt.isBlank(var16)) {
               var4.add(var16);
            }
         }

         var4 = collapseStrings(var4);
         explode("", var4, var3);
         Iterable var18;
         Iterable var20 = var18 = var3;
         var var19 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var18, 10));

         for (Object var21 : var20) {
            String var22 = (String)var21;
            var3 = var19;
            var3.add(var2 + var22);
         }

         var23 = var19;
      }

      Collection var12;
      if ((var12 = var23).isEmpty()) {
         String[] var15;
         (var15 = new String[1])[0] = StringsKt.replace$default(StringsKt.replace$default(var0, "*", "", false, 4, null), "?", "", false, 4, null);
         var23 = CollectionsKt.mutableListOf(var15);
      } else {
         var23 = var12;
      }

      return var23;
   }

   private static final String concatRule(String var0, String var1) {
      return !StringsKt.isBlank(var0) ? var0 + " " + var1 : var1;
   }

   private static final void explode(String var0, List var1, List var2) {
      while (!var1.isEmpty()) {
         if (var1.get(0) instanceof String) {
            String var5 = concatRule(var0, var1.get(0).toString());
            if (var1.size() > 1) {
               var1 = var1.subList(1, var1.size());
               var0 = var5;
               continue;
            }

            var2.add(var5);
            return;
         }

         Object var10000 = var1.get(0);
         Intrinsics.checkNotNull(var10000);

         for (String var4 : (List)var10000) {
            var4 = concatRule(var0, String.valueOf(var4));
            if (var1.size() > 1) {
               explode(var4, var1.subList(1, var1.size()), var2);
            } else {
               var2.add(var4);
            }
         }
         break;
      }
   }

   private static final List collapseStrings(List var0) {
      List var1 = new ArrayList();
      String var2 = "";
      Iterator var4 = var0.iterator();

      while (var4.hasNext()) {
         Object var3;
         String var10000;
         if ((var3 = var4.next()) instanceof String) {
            var10000 = var2 + " " + var3;
         } else {
            var1.add(var2);
            var1.add(var3);
            var10000 = "";
         }

         var2 = var10000;
      }

      if (!StringsKt.isBlank(var2)) {
         var1.add(var2);
      }

      return var1;
   }

   @NotNull
   public static final SortedSet getAllItemsSet() {
      Set var0 = new LinkedHashSet();
      Set var10000 = RegistryExKt.get_REGISTRIES_BLOCK_IDS_/* $VF was: get(REGISTRIES-BLOCK-IDS) */();
      Intrinsics.checkNotNullExpressionValue(var10000, "");

      for (ResourceLocation var2 : var10000) {
         var0.add(var2.getNamespace() + ":" + var2.getPath());
      }

      var10000 = RegistryExKt.get_REGISTRIES_ITEM_IDS_/* $VF was: get(REGISTRIES-ITEM-IDS) */();
      Intrinsics.checkNotNullExpressionValue(var10000, "");

      for (ResourceLocation var5 : var10000) {
         var0.add(var5.getNamespace() + ":" + var5.getPath());
      }

      var10000 = RegistryExKt.get_REGISTRIES_BLOCK_ENTITY_TYPES_IDS_/* $VF was: get(REGISTRIES-BLOCK_ENTITY_TYPES-IDS) */();
      Intrinsics.checkNotNullExpressionValue(var10000, "");

      for (ResourceLocation var6 : var10000) {
         var0.add(var6.getNamespace() + ":" + var6.getPath());
      }

      return CollectionsKt.toSortedSet(var0);
   }

   private static final String preprocessRules$lambda$1(List var0) {
      return "Expanded Rules:\n\t|" + CollectionsKt.joinToString$default(var0, "\n\t|", null, null, 0, null, null, 62, null);
   }
}
