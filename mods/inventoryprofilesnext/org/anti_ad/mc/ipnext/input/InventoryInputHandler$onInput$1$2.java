package org.anti_ad.mc.ipnext.input;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.AdaptedFunctionReference;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.anti_ad.mc.ipnext.inventory.GeneralInventoryActions;

// $VF: synthetic class
final class InventoryInputHandler$onInput$1$2 extends AdaptedFunctionReference implements Function1 {
   InventoryInputHandler$onInput$1$2(Object var1) {
      super(1, var1, GeneralInventoryActions.class, "doSortInColumns", "doSortInColumns(Lnet/minecraft/world/inventory/AbstractContainerMenu;ZZ)V", 0);
   }

   public final void invoke(AbstractContainerMenu var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      GeneralInventoryActions.doSortInColumns$default((GeneralInventoryActions)this.receiver, var1, false, false, 6, null);
   }
}
