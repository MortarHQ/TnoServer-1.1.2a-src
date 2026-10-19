package org.anti_ad.mc.ipnext.item.rule.file;

import java.util.List;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;
import org.anti_ad.mc.common.extensions.Kt_commonKt;
import org.anti_ad.mc.ipnext.item.ItemType;

// $VF: synthetic class
final class CustomRule$1 extends FunctionReferenceImpl implements Function2 {
   CustomRule$1(Object var1) {
      super(2, var1, Kt_commonKt.class, "compare", "compare(Ljava/util/List;Ljava/lang/Object;Ljava/lang/Object;)I", 1);
   }

   public final Integer invoke(ItemType var1, ItemType var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      return Kt_commonKt.compare((List)this.receiver, var1, var2);
   }
}
