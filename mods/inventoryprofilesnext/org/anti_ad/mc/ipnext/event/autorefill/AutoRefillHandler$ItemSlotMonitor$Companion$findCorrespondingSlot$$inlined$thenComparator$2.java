package org.anti_ad.mc.ipnext.event.autorefill;

import java.util.Comparator;
import kotlin.collections.IndexedValue;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.food.FoodProperties;
import org.anti_ad.mc.ipnext.item.ItemStack;
import org.anti_ad.mc.ipnext.item.ItemType;

@SourceDebugExtension(
   "SMAP\nComparisons.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Comparisons.kt\nkotlin/comparisons/ComparisonsKt__ComparisonsKt$thenComparator$1\n+ 2 AutoRefillHandler.kt\norg/anti_ad/mc/ipnext/event/autorefill/AutoRefillHandler$ItemSlotMonitor$Companion\n+ 3 ItemTypeExtensions.kt\norg/anti_ad/mc/ipnext/item/ItemTypeExtensionsKt\n*L\n1#1,197:1\n674#2,3:198\n677#2,2:202\n679#2,4:208\n683#2,10:216\n276#3:201\n269#3:204\n303#3:205\n269#3:206\n303#3:207\n269#3:212\n303#3:213\n269#3:214\n303#3:215\n*S KotlinDebug\n*F\n+ 1 AutoRefillHandler.kt\norg/anti_ad/mc/ipnext/event/autorefill/AutoRefillHandler$ItemSlotMonitor$Companion\n*L\n676#1:201\n678#1:204\n678#1:205\n678#1:206\n678#1:207\n682#1:212\n682#1:213\n682#1:214\n682#1:215\n*E\n"
)
public final class AutoRefillHandler$ItemSlotMonitor$Companion$findCorrespondingSlot$$inlined$thenComparator$2 implements Comparator {
   public AutoRefillHandler$ItemSlotMonitor$Companion$findCorrespondingSlot$$inlined$thenComparator$2(Comparator var1) {
      this.$this_thenComparator = var1;
   }

   @Override
   public final int compare(Object var1, Object var2) {
      int var3;
      if ((var3 = this.$this_thenComparator.compare(var1, var2)) != 0) {
         return var3;
      }

      IndexedValue var4 = (IndexedValue)var2;
      ItemType var6 = ((ItemStack)((IndexedValue)var1).getValue()).getItemType();
      ItemType var5 = ((ItemStack)var4.getValue()).getItemType();
      if (var6.getItem().components().get(DataComponents.FOOD) != null && var5.getItem().components().get(DataComponents.FOOD) != null) {
         FoodProperties var7 = (FoodProperties)var5.getItem().components().get(DataComponents.FOOD);
         if (var7 == null) {
            throw new IllegalStateException("this shouldn't happen".toString());
         }

         float var8 = var7.saturation();
         FoodProperties var10001 = (FoodProperties)var6.getItem().components().get(DataComponents.FOOD);
         if (var10001 == null) {
            throw new IllegalStateException("this shouldn't happen".toString());
         }

         if (var8 != var10001.saturation()) {
            var7 = (FoodProperties)var5.getItem().components().get(DataComponents.FOOD);
            if (var7 == null) {
               throw new IllegalStateException("this shouldn't happen".toString());
            }

            float var10 = var7.saturation();
            var10001 = (FoodProperties)var6.getItem().components().get(DataComponents.FOOD);
            if (var10001 == null) {
               throw new IllegalStateException("this shouldn't happen".toString());
            }

            if (var10 > var10001.saturation()) {
               return 1;
            }

            return -1;
         }
      }

      return 0;
   }
}
