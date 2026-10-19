package org.anti_ad.mc.ipnext.inventory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;

@SourceDebugExtension(
   "SMAP\nAreaTypes.kt\nKotlin\n*S Kotlin\n*F\n+ 1 AreaTypes.kt\norg/anti_ad/mc/ipnext/inventory/AreaType$Companion$playerInvSlots$2\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 VanillaAccessors.kt\norg/anti_ad/mc/ipnext/ingame/VanillaAccessorsKt\n+ 4 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,384:1\n1878#2,2:385\n1880#2:389\n1617#2,9:390\n1869#2:399\n1870#2:401\n1626#2:402\n97#3:387\n86#3:388\n1#4:400\n*S KotlinDebug\n*F\n+ 1 AreaTypes.kt\norg/anti_ad/mc/ipnext/inventory/AreaType$Companion$playerInvSlots$2\n*L\n194#1:385,2\n194#1:389\n198#1:390,9\n198#1:399\n198#1:401\n198#1:402\n195#1:387\n195#1:388\n198#1:400\n*E\n"
)
final class AreaType$Companion$playerInvSlots$2 implements AreaType {
   AreaType$Companion$playerInvSlots$2(Function0 var1) {
      this.$invSlots = var1;
   }

   @Override
   public final ItemArea getItemArea(AbstractContainerMenu var1, List var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      Map var9 = new LinkedHashMap();
      ArrayList var3 = var2;
      int var4 = 0;

      for (Slot var5 : var3) {
         int var6;
         if ((var6 = var4++) < 0) {
            CollectionsKt.throwIndexOverflow();
         }

         var5 = var5;
         var6 = var6;
         if (var5.container instanceof Inventory) {
            Map var7 = var9;
            Integer var17 = var5.getSlotIndex();
            Integer var8 = var6;
            var7.put(var17, var8);
         }
      }

      ItemArea$Companion var23 = ItemArea.Companion;
      var3 = (Iterable)this.$invSlots.invoke();
      List var21 = var2;
      ItemArea$Companion var18 = var23;
      Iterable var15 = var3;
      var3 = new ArrayList();
      Iterator var20 = var15.iterator();

      while (var20.hasNext()) {
         int var22 = ((Number)var20.next()).intValue();
         Integer var24 = (Integer)var9.get(var22);
         if (var24 != null) {
            Integer var11 = var24;
            var3.add(var11);
         }
      }

      List var10 = var3;
      return ItemArea$Companion.invoke$default(var18, var21, var10, false, 4, null);
   }

   @Override
   public final AreaType plus(AreaType var1) {
      return AreaType$DefaultImpls.plus(this, var1);
   }

   @Override
   public final AreaType minus(AreaType var1) {
      return AreaType$DefaultImpls.minus(this, var1);
   }
}
