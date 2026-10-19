package org.anti_ad.mc.ipnext.gui.inject;

import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.properties.ObservableProperty;
import kotlin.reflect.KProperty;
import org.anti_ad.mc.common.input.GlobalScreenEventListener;
import org.anti_ad.mc.common.math2d.Size;

@SourceDebugExtension(
   "SMAP\nDelegates.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Delegates.kt\nkotlin/properties/Delegates$observable$1\n+ 2 PropertyDelegates.kt\norg/anti_ad/mc/common/extensions/PropertyDelegatesKt\n+ 3 ScreenEventHandler.kt\norg/anti_ad/mc/ipnext/gui/inject/ScreenEventHandler\n*L\n1#1,34:1\n36#2,2:35\n38#2:39\n48#3,2:37\n*E\n"
)
public final class ScreenEventHandler$special$$inlined$detectable$1 extends ObservableProperty {
   public ScreenEventHandler$special$$inlined$detectable$1(Object var1) {
      super(var1);
   }

   protected final void afterChange(KProperty var1, Object var2, Object var3) {
      Intrinsics.checkNotNullParameter(var1, "");
      Size var4 = (Size)var3;
      if (!Intrinsics.areEqual(var2, var4)) {
         var4 = var4;
         var2 = var4.component1();
         int var6 = var4.component2();
         GlobalScreenEventListener.INSTANCE.onResize(var2, var6);
      }
   }
}
