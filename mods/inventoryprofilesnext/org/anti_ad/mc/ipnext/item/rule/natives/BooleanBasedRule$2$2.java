package org.anti_ad.mc.ipnext.item.rule.natives;

import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;
import org.anti_ad.mc.ipnext.item.ItemType;
import org.anti_ad.mc.ipnext.item.rule.Rule;

// $VF: synthetic class
final class BooleanBasedRule$2$2 extends FunctionReferenceImpl implements Function2 {
   BooleanBasedRule$2$2(Object var1) {
      super(2, var1, Rule.class, "compare", "compare(Lorg/anti_ad/mc/ipnext/item/ItemType;Lorg/anti_ad/mc/ipnext/item/ItemType;)I", 0);
   }

   public final Integer invoke(ItemType var1, ItemType var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      return ((Rule)this.receiver).compare(var1, var2);
   }
}
