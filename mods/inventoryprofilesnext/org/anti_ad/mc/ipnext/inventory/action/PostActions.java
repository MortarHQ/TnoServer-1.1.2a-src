package org.anti_ad.mc.ipnext.inventory.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.anti_ad.mc.ipnext.item.ItemStack;
import org.anti_ad.mc.ipnext.item.ItemStackExtensionsKt;
import org.anti_ad.mc.ipnext.item.ItemType;
import org.anti_ad.mc.ipnext.item.MutableItemStack;
import org.jetbrains.annotations.NotNull;

@SourceDebugExtension(
   "SMAP\nPostActions.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PostActions.kt\norg/anti_ad/mc/ipnext/inventory/action/PostActions\n+ 2 Maps.kt\nkotlin/collections/MapsKt__MapsKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 4 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,253:1\n465#2:254\n415#2:255\n1252#3,4:256\n1563#3:260\n1634#3,3:261\n1869#3:265\n1878#3,3:266\n1870#3:269\n1#4:264\n*S KotlinDebug\n*F\n+ 1 PostActions.kt\norg/anti_ad/mc/ipnext/inventory/action/PostActions\n*L\n56#1:254\n56#1:255\n56#1:256,4\n61#1:260\n61#1:261,3\n72#1:265\n74#1:266,3\n72#1:269\n*E\n"
)
public final class PostActions {
   @NotNull
   public static final PostActions INSTANCE = new PostActions();

   private PostActions() {
   }

   @NotNull
   public final List groupInRows(@NotNull List var1, int var2, int var3) {
      Intrinsics.checkNotNullParameter(var1, "");
      return this.group(var1, var2, var3, true);
   }

   @NotNull
   public final List groupInColumns(@NotNull List var1, int var2, int var3) {
      Intrinsics.checkNotNullParameter(var1, "");
      return this.group(var1, var2, var3, false);
   }

   private final List group(List var1, int var2, int var3, boolean var4) {
      if (var1.isEmpty()) {
         return var1;
      }

      if (var2 * var3 != var1.size()) {
         throw new IllegalStateException("Area is not rectangular!".toString());
      }

      Map var5;
      Map var6;
      Map var7 = var6 = var5 = PostActionsKt.access$group(var1);
      Map var8 = new LinkedHashMap(MapsKt.mapCapacity(var6.size()));

      for (Object var28 : var7.entrySet()) {
         Entry var9 = (Entry)var28;
         Map var10 = var8;
         Object var10001 = var9.getKey();
         Entry var11 = (Entry)var28;
         Object var42 = var10001;
         Map var37 = var10;
         Integer var29 = ((List)var11.getValue()).size();
         var37.put(var42, var29);
      }

      List var23 = MapsKt.toList(var8);
      List var48;
      if (!var4) {
         var48 = new GroupInColumnsCalculator(var23, var3, var2).calc();
         if (var48 != null) {
            Iterable var35;
            Iterable var24 = var35 = var48;
            var var18 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var35, 10));

            for (Object var38 : var24) {
               Pair var43 = (Pair)var38;
               ArrayList var39 = var18;
               ItemType var45 = (ItemType)var43.component1();
               List var25 = (List)var43.component2();
               var39.add(TuplesKt.to(var45, PostActionsKt.access$asIndicesTranspose(var25, var3, var2)));
            }

            var48 = var18;
         } else {
            var48 = null;
         }
      } else {
         var48 = new GroupInColumnsCalculator(var23, var2, var3).calc();
      }

      if (var48 == null) {
         return var1;
      }

      List var26 = var48;
      int var31 = var2 * var3;
      ArrayList var36 = new ArrayList(var31);

      for (int var13 = 0; var13 < var31; var13++) {
         ArrayList var40 = var36;
         var40.add(ItemStackExtensionsKt.empty(MutableItemStack.Companion));
      }

      var1 = var36;
      Iterator var14 = var26.iterator();

      while (var14.hasNext()) {
         Pair var19;
         ItemType var32 = (ItemType)(var19 = (Pair)var14.next()).component1();
         List var41 = (List)var19.component2();
         Iterable var44 = (Iterable)MapsKt.getValue(var5, var32);
         int var46 = 0;

         for (Object var33 : var44) {
            if ((var3 = var46++) < 0) {
               CollectionsKt.throwIndexOverflow();
            }

            ItemStack var16 = (ItemStack)var33;
            var4 = var3;
            ItemType var34 = var16.component1();
            var3 = var16.component2();
            var4 = ((Number)var41.get(var4)).intValue();
            ((MutableItemStack)var1.get(var4)).setItemType(var34);
            ((MutableItemStack)var1.get(var4)).setCount(var3);
         }
      }

      return var1;
   }
}
