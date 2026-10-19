package org.anti_ad.mc.ipnext.item;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.ChargedProjectiles;
import org.anti_ad.mc.ipnext.ingame.VanillaAccessorsKt$(itemType)$1;

@SourceDebugExtension(
   "SMAP\nComparisons.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Comparisons.kt\nkotlin/comparisons/ComparisonsKt__ComparisonsKt$thenComparator$1\n+ 2 ComponentUtils.kt\norg/anti_ad/mc/ipnext/item/ComponentUtils\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 4 VanillaAccessors.kt\norg/anti_ad/mc/ipnext/ingame/VanillaAccessorsKt\n*L\n1#1,197:1\n145#2:198\n146#2:207\n147#2:216\n148#2,3:219\n151#2:223\n1563#3:199\n1634#3,2:200\n1636#3:206\n1563#3:208\n1634#3,2:209\n1636#3:215\n1878#3,2:217\n1880#3:222\n66#4,4:202\n66#4,4:211\n*S KotlinDebug\n*F\n+ 1 ComponentUtils.kt\norg/anti_ad/mc/ipnext/item/ComponentUtils\n*L\n145#1:199\n145#1:200,2\n145#1:206\n146#1:208\n146#1:209,2\n146#1:215\n147#1:217,2\n147#1:222\n145#1:202,4\n146#1:211,4\n*E\n"
)
public final class ComponentUtils$special$$inlined$thenComparator$2 implements Comparator {
   public ComponentUtils$special$$inlined$thenComparator$2(Comparator var1) {
      this.$this_thenComparator = var1;
   }

   @Override
   public final int compare(Object var1, Object var2) {
      int var3;
      if ((var3 = this.$this_thenComparator.compare(var1, var2)) != 0) {
         return var3;
      }

      ChargedProjectiles var8 = (ChargedProjectiles)var2;
      List var30 = ((ChargedProjectiles)var1).getItems();
      Intrinsics.checkNotNullExpressionValue(var30, "");
      Iterable var13;
      Iterable var17 = var13 = var30;
      var var4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var13, 10));

      for (net.minecraft.world.item.ItemStack var5 : var17) {
         net.minecraft.world.item.ItemStack var6 = var5;
         var13 = var4;
         Intrinsics.checkNotNull(var6);
         var5 = var6;
         Item var10002 = var5.getItem();
         Intrinsics.checkNotNullExpressionValue(var10002, "");
         DataComponentMap var10003 = var5.getComponents();
         Intrinsics.checkNotNull(var10003);
         var13.add(
            new ItemType(
               var10002, (PatchedDataComponentMap)var10003, var5.getComponentsPatch(), new VanillaAccessorsKt$(itemType)$1(var5), false, false, null, 112, null
            )
         );
      }

      List var7 = CollectionsKt.sortedWith((Iterable & List)var4, ComponentUtils.INSTANCE.getItemTypeComparator());
      var30 = var8.getItems();
      Intrinsics.checkNotNullExpressionValue(var30, "");
      Iterable var9;
      var4 = var9 = var30;
      var9 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var9, 10));

      for (Object var28 : var4) {
         net.minecraft.world.item.ItemStack var19 = (net.minecraft.world.item.ItemStack)var28;
         var13 = var9;
         Intrinsics.checkNotNull(var19);
         Item var33 = var19.getItem();
         Intrinsics.checkNotNullExpressionValue(var33, "");
         DataComponentMap var34 = var19.getComponents();
         Intrinsics.checkNotNull(var34);
         var13.add(
            new ItemType(
               var33, (PatchedDataComponentMap)var34, var19.getComponentsPatch(), new VanillaAccessorsKt$(itemType)$1(var19), false, false, null, 112, null
            )
         );
      }

      var13 = CollectionsKt.sortedWith((Iterable & List)var9, ComponentUtils.INSTANCE.getItemTypeComparator());
      var9 = var7;
      int var24 = 0;

      for (Object var20 : var9) {
         int var27;
         if ((var27 = var24++) < 0) {
            CollectionsKt.throwIndexOverflow();
         }

         ItemType var29 = (ItemType)var20;
         var3 = var27;
         int var22;
         if ((var22 = ComponentUtils.INSTANCE.getItemTypeComparator().compare(var29, (ItemType)var13.get(var3))) != 0) {
            return var22;
         }
      }

      return 0;
   }
}
