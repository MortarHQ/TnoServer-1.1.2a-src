package org.anti_ad.mc.ipnext.inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;

@SourceDebugExtension(
   "SMAP\nAreaTypes.kt\nKotlin\n*S Kotlin\n*F\n+ 1 AreaTypes.kt\norg/anti_ad/mc/ipnext/inventory/AreaType$Companion$inSlots$1\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,384:1\n1583#2,11:385\n1878#2,2:396\n1880#2:399\n1594#2:400\n1#3:398\n*S KotlinDebug\n*F\n+ 1 AreaTypes.kt\norg/anti_ad/mc/ipnext/inventory/AreaType$Companion$inSlots$1\n*L\n172#1:385,11\n172#1:396,2\n172#1:399\n172#1:400\n172#1:398\n*E\n"
)
final class AreaType$Companion$inSlots$1 implements AreaType {
   AreaType$Companion$inSlots$1(Function0 var1, boolean var2) {
      this.$elements = var1;
      this.$orderSensitive = var2;
   }

   @Override
   public final ItemArea getItemArea(AbstractContainerMenu var1, List var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      Set var8 = CollectionsKt.toSet((Iterable)this.$elements.invoke());
      Iterable var3 = var2;
      var var4 = new ArrayList();
      int var5 = 0;

      for (Object var6 : var3) {
         int var7;
         if ((var7 = var5++) < 0) {
            CollectionsKt.throwIndexOverflow();
         }

         Slot var11 = (Slot)var6;
         var7 = var7;
         Integer var14 = var8.contains(var11) ? var7 : null;
         if (var14 != null) {
            Integer var12 = var14;
            var4.add(var12);
         }
      }

      List var9 = var4;
      return ItemArea.Companion.invoke(var2, var9, this.$orderSensitive);
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
