package org.anti_ad.mc.ipnext.inventory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IndexedValue;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.world.inventory.Slot;
import org.anti_ad.mc.common.extensions.Kt_collectionKt;
import org.anti_ad.mc.common.math2d.Point;
import org.anti_ad.mc.common.math2d.Size;

@SourceDebugExtension(
   "SMAP\nAreaTypes.kt\nKotlin\n*S Kotlin\n*F\n+ 1 AreaTypes.kt\norg/anti_ad/mc/ipnext/inventory/AreaTypesKt\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 VanillaAccessors.kt\norg/anti_ad/mc/ipnext/ingame/VanillaAccessorsKt\n+ 4 Maps.kt\nkotlin/collections/MapsKt__MapsKt\n*L\n1#1,384:1\n1563#2:385\n1634#2,2:386\n1636#2:395\n1491#2:396\n1516#2,3:397\n1519#2,3:407\n1761#2,3:410\n1056#2:413\n1563#2:414\n1634#2,2:415\n1056#2:417\n1636#2:418\n1761#2,3:419\n103#3:388\n99#3,6:389\n384#4,7:400\n*S KotlinDebug\n*F\n+ 1 AreaTypes.kt\norg/anti_ad/mc/ipnext/inventory/AreaTypesKt\n*L\n223#1:385\n223#1:386,2\n223#1:395\n306#1:396\n306#1:397,3\n306#1:407,3\n312#1:410,3\n315#1:413\n315#1:414\n315#1:415,2\n315#1:417\n315#1:418\n319#1:419,3\n223#1:388\n223#1:389,6\n306#1:400,7\n*E\n"
)
public final class AreaTypesKt {
   private static final List toPointList(List var0) {
      Iterable var4;
      Iterable var1 = var4 = var0;
      var0 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var4, 10));

      for (Slot var2 : var1) {
         var2 = var2;
         ArrayList var3 = var0;
         var3.add(new Point(var2.x, var2.y));
      }

      return var0;
   }

   private static final Pair isRectangular(List var0) {
      if (var0.isEmpty()) {
         return null;
      }

      Iterable var2 = Kt_collectionKt.indexed(var0);
      Map var3 = new LinkedHashMap();
      Iterator var4 = var2.iterator();

      while (var4.hasNext()) {
         Object var5;
         Integer var7 = ((Point)((IndexedValue)(var5 = var4.next())).getValue()).getY();
         Map var8 = var3;
         Object var1;
         if ((var1 = var3.get(var7)) == null) {
            var1 = new ArrayList();
            var8.put(var7, var1);
         }

         ((List)var1).add(var5);
      }

      Map var10 = var3;
      int var13 = var0.size();
      int var9 = var10.keySet().size();
      if (var13 % var9 != 0) {
         return null;
      }

      int var14;
      if ((var14 = var13 / var9) != 1 && var9 != 1) {
         boolean var10000;
         label115: {
            Iterable var15;
            if (!((var15 = var10.values()) instanceof Collection) || !((Collection)var15).isEmpty()) {
               var4 = var15.iterator();

               while (var4.hasNext()) {
                  if (((List)var4.next()).size() != var14) {
                     var10000 = true;
                     break label115;
                  }
               }
            }

            var10000 = false;
         }

         if (var10000) {
            return null;
         }

         Iterable var11;
         Iterable var20 = var11 = CollectionsKt.sortedWith(MapsKt.toList(var10), new AreaTypesKt$isRectangular$$inlined$sortedBy$1());
         var var6 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var11, 10));

         for (Pair var25 : var20) {
            var25 = var25;
            ArrayList var16 = var6;
            var16.add(CollectionsKt.sortedWith((Iterable & List)var25.component2(), new AreaTypesKt$isRectangular$lambda$5$$inlined$sortedBy$1()));
         }

         List var17;
         List var12 = (List)CollectionsKt.first(var17 = var6);
         int var19 = 0;

         for (int var21 = var12.size(); var19 < var21; var19++) {
            label67: {
               Iterable var22;
               if (!((Collection)(var22 = var17)).isEmpty()) {
                  Iterator var24 = var22.iterator();

                  while (var24.hasNext()) {
                     if (((Point)((IndexedValue)((List)var24.next()).get(var19)).getValue()).getX()
                        != ((Point)((IndexedValue)var12.get(var19)).getValue()).getX()) {
                        var10000 = true;
                        break label67;
                     }
                  }
               }

               var10000 = false;
            }

            if (var10000) {
               return null;
            }
         }

         return TuplesKt.to(new Size(var14, var9), CollectionsKt.flatten(var17));
      } else {
         return null;
      }
   }
}
