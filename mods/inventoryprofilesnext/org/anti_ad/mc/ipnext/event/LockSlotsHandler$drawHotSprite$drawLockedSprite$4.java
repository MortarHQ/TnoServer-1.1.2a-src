package org.anti_ad.mc.ipnext.event;

import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;
import org.anti_ad.mc.common.gui.NativeContext;
import org.anti_ad.mc.common.math2d.Point;

// $VF: synthetic class
final class LockSlotsHandler$drawHotSprite$drawLockedSprite$4 extends FunctionReferenceImpl implements Function3 {
   LockSlotsHandler$drawHotSprite$drawLockedSprite$4(Object var1) {
      super(
         3,
         var1,
         LockSlotsHandler.class,
         "drawHotOutline",
         "drawHotOutline(Lorg/anti_ad/mc/common/gui/NativeContext;Lorg/anti_ad/mc/common/math2d/Point;I)V",
         0
      );
   }

   public final void invoke(NativeContext var1, Point var2, int var3) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      LockSlotsHandler.access$drawHotOutline((LockSlotsHandler)this.receiver, var1, var2, var3);
   }
}
