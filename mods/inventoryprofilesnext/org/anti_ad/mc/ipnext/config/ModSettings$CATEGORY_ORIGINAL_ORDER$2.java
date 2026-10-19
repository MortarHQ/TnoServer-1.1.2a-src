package org.anti_ad.mc.ipnext.config;

import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;
import org.anti_ad.mc.ipnext.item.ItemTypeExtensionsObject;

// $VF: synthetic class
final class ModSettings$CATEGORY_ORIGINAL_ORDER$2 extends FunctionReferenceImpl implements Function0 {
   ModSettings$CATEGORY_ORIGINAL_ORDER$2(Object var1) {
      super(0, var1, ItemTypeExtensionsObject.class, "defaultOrderListChanged", "defaultOrderListChanged()V", 0);
   }

   public final void invoke() {
      ((ItemTypeExtensionsObject)this.receiver).defaultOrderListChanged();
   }
}
