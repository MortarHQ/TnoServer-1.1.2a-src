package org.anti_ad.mc.ipnext.item.rule.natives;

import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;
import org.anti_ad.mc.ipnext.item.ItemType;

// $VF: synthetic class
final class ByNbtRule$1$2 extends FunctionReferenceImpl implements Function2 {
   ByNbtRule$1$2(Object var1) {
      super(2, var1, ByNbtRule.class, "compareByNbtPath", "compareByNbtPath(Lorg/anti_ad/mc/ipnext/item/ItemType;Lorg/anti_ad/mc/ipnext/item/ItemType;)I", 0);
   }

   public final Integer invoke(ItemType var1, ItemType var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      return ((ByNbtRule)this.receiver).compareByNbtPath(var1, var2);
   }
}
