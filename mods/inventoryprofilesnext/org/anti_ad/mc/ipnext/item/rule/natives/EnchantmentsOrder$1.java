package org.anti_ad.mc.ipnext.item.rule.natives;

import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;
import org.anti_ad.mc.ipnext.item.ItemType;

// $VF: synthetic class
final class EnchantmentsOrder$1 extends FunctionReferenceImpl implements Function2 {
   EnchantmentsOrder$1(Object var1) {
      super(2, var1, EnchantmentsOrder.class, "compareItems", "compareItems(Lorg/anti_ad/mc/ipnext/item/ItemType;Lorg/anti_ad/mc/ipnext/item/ItemType;)I", 0);
   }

   public final Integer invoke(ItemType var1, ItemType var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      return ((EnchantmentsOrder)this.receiver).compareItems(var1, var2);
   }
}
