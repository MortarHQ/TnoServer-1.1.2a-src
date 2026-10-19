package org.anti_ad.mc.ipnext.inventory.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.SourceDebugExtension;
import org.anti_ad.mc.ipnext.item.ItemStack;
import org.anti_ad.mc.ipnext.item.ItemStackExtensionsKt;
import org.anti_ad.mc.ipnext.item.ItemType;

@SourceDebugExtension(
   "SMAP\nPostActions.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PostActions.kt\norg/anti_ad/mc/ipnext/inventory/action/PostActionsKt\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 kt_common.kt\norg/anti_ad/mc/common/extensions/Kt_commonKt\n+ 4 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 5 Maps.kt\nkotlin/collections/MapsKt__MapsKt\n*L\n1#1,253:1\n1617#2,9:254\n1869#2:263\n1870#2:266\n1626#2:267\n1491#2:268\n1516#2,3:269\n1519#2,3:279\n1563#2:282\n1634#2,3:283\n87#3:264\n1#4:265\n1#4:286\n384#5,7:272\n*S KotlinDebug\n*F\n+ 1 PostActions.kt\norg/anti_ad/mc/ipnext/inventory/action/PostActionsKt\n*L\n94#1:254,9\n94#1:263\n94#1:266\n94#1:267\n94#1:268\n94#1:269,3\n94#1:279,3\n103#1:282\n103#1:283,3\n94#1:264\n94#1:265\n94#1:272,7\n*E\n"
)
public final class PostActionsKt {
   private static final Map group(List var0) {
      Iterable var6 = var0;
      var var1 = new ArrayList();
      Iterator var3 = var6.iterator();

      while (var3.hasNext()) {
         ItemStack var5;
         ItemStack var10000 = !ItemStackExtensionsKt.isEmpty(var5 = (ItemStack)var3.next()) ? var5 : null;
         if (var10000 != null) {
            ItemStack var7 = var10000;
            var1.add(var7);
         }
      }

      var6 = var1;
      Map var10 = new LinkedHashMap();
      Iterator var9 = var6.iterator();

      while (var9.hasNext()) {
         Object var2;
         ItemType var4 = ((ItemStack)(var2 = var9.next())).getItemType();
         Map var11 = var10;
         Object var12;
         if ((var12 = var10.get(var4)) == null) {
            var12 = new ArrayList();
            var11.put(var4, var12);
         }

         ((List)var12).add(var2);
      }

      return var10;
   }

   private static final int transposedIndex(int var0, int var1, int var2) {
      return var2 % var0 * var1 + var2 / var0;
   }

   private static final List asIndicesTranspose(List var0, int var1, int var2) {
      Iterable var6;
      Iterable var3 = var6 = var0;
      var0 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var6, 10));

      for (Object var4 : var3) {
         int var9 = ((Number)var4).intValue();
         ArrayList var5 = var0;
         var5.add(transposedIndex(var1, var2, var9));
      }

      return var0;
   }

   private static final boolean connected(Collection var0) {
      if (var0.isEmpty()) {
         return true;
      }

      var0 = CollectionsKt.toMutableSet(var0);
      Pair[] var2;
      ((Object[])(var2 = new Pair[1]))[0] = (Pair)CollectionsKt.first(var0);
      Set var1 = SetsKt.mutableSetOf(var2);

      while (!var1.isEmpty()) {
         Pair var3 = var2 = (Pair)CollectionsKt.first(var1);
         var1.remove(var3);
         var2 = var2;
         if (var0.contains(var2)) {
            var0.remove(var2);
            var1.add(Pair.copy$default(var2, ((Number)var2.getFirst()).intValue() - 1, null, 2, null));
            var1.add(Pair.copy$default(var2, ((Number)var2.getFirst()).intValue() + 1, null, 2, null));
            var1.add(Pair.copy$default(var2, null, ((Number)var2.getSecond()).intValue() - 1, 1, null));
            var1.add(Pair.copy$default(var2, null, ((Number)var2.getSecond()).intValue() + 1, 1, null));
         }
      }

      return var0.isEmpty();
   }
}
