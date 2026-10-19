package org.anti_ad.mc.ipnext.input;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.AdaptedFunctionReference;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.anti_ad.mc.ipnext.inventory.GeneralInventoryActions;

// $VF: synthetic class
final class InventoryInputHandler$onInput$1$3 extends AdaptedFunctionReference implements Function1 {
   InventoryInputHandler$onInput$1$3(Object var1) {
      super(1, var1, GeneralInventoryActions.class, "doSortInRows", "doSortInRows(Lnet/minecraft/world/inventory/AbstractContainerMenu;ZZ)V", 0);
   }

   public final void invoke(AbstractContainerMenu var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      GeneralInventoryActions.doSortInRows$default((GeneralInventoryActions)this.receiver, var1, false, false, 6, null);
   }
}
