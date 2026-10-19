package org.anti_ad.mc.ipnext.event;

import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;
import org.anti_ad.mc.common.gui.NativeContext;
import org.anti_ad.mc.common.math2d.Point;

// $VF: synthetic class
final class LockSlotsHandler$drawConfig$2 extends FunctionReferenceImpl implements Function3 {
   LockSlotsHandler$drawConfig$2(Object var1) {
      super(
         3,
         var1,
         LockSlotsHandler.class,
         "drawConfigOpen",
         "drawConfigOpen(Lorg/anti_ad/mc/common/gui/NativeContext;Lorg/anti_ad/mc/common/math2d/Point;Lorg/anti_ad/mc/common/math2d/Point;)V",
         0
      );
   }

   public final void invoke(NativeContext var1, Point var2, Point var3) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      Intrinsics.checkNotNullParameter(var3, "");
      LockSlotsHandler.access$drawConfigOpen((LockSlotsHandler)this.receiver, var1, var2, var3);
   }
}
