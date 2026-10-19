package org.anti_ad.mc.ipnext.event;

import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.properties.ObservableProperty;
import kotlin.reflect.KProperty;
import org.anti_ad.mc.ipnext.parser.LockSlotsLoader;

@SourceDebugExtension(
   "SMAP\nDelegates.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Delegates.kt\nkotlin/properties/Delegates$observable$1\n+ 2 PropertyDelegates.kt\norg/anti_ad/mc/common/extensions/PropertyDelegatesKt\n+ 3 LockSlotsHandler.kt\norg/anti_ad/mc/ipnext/event/LockSlotsHandler\n*L\n1#1,34:1\n36#2,2:35\n38#2:39\n100#3,2:37\n*E\n"
)
public final class LockSlotsHandler$special$$inlined$detectable$1 extends ObservableProperty {
   public LockSlotsHandler$special$$inlined$detectable$1(Object var1) {
      super(var1);
   }

   protected final void afterChange(KProperty var1, Object var2, Object var3) {
      Intrinsics.checkNotNullParameter(var1, "");
      Object var4 = var3;
      var2 = var2;
      if (!Intrinsics.areEqual(var2, var4)) {
         boolean var5 = (Boolean)var4;
         (Boolean)var2;
         if (!var5) {
            LockSlotsLoader.INSTANCE.save();
         }
      }
   }
}
