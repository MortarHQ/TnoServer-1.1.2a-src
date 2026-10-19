package org.anti_ad.mc.ipnext.inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.world.inventory.AbstractContainerMenu;

final class AreaTypes$itemStorage$1 implements AreaType {
   public static final AreaTypes$itemStorage$1 INSTANCE = new AreaTypes$itemStorage$1();

   @Override
   public final ItemArea getItemArea(AbstractContainerMenu var1, List var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      List var3 = new ArrayList();
      Set var4;
      if ((var4 = ContainerTypes.getTypes$default(ContainerTypes.INSTANCE, var1, null, 2, null)).contains(ContainerType.SORTABLE_STORAGE)
         || var4.contains(ContainerType.NO_SORTING_STORAGE)) {
         AreaTypes.access$fillSlots(AreaTypes.INSTANCE, var4, var2, var3);
      }

      return ItemArea$Companion.invoke$default(ItemArea.Companion, var2, var3, false, 4, null);
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
