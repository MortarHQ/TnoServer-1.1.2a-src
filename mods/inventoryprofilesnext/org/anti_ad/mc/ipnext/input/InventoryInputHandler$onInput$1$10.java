package org.anti_ad.mc.ipnext.input;

import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;
import org.anti_ad.mc.ipnext.inventory.GeneralInventoryActions;

// $VF: synthetic class
final class InventoryInputHandler$onInput$1$10 extends FunctionReferenceImpl implements Function0 {
   InventoryInputHandler$onInput$1$10(Object var1) {
      super(0, var1, GeneralInventoryActions.class, "scrollDown", "scrollDown()V", 0);
   }

   public final void invoke() {
      ((GeneralInventoryActions)this.receiver).scrollDown();
   }
}
