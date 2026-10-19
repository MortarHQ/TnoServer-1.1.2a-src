package org.anti_ad.mc.ipnext.gui.inject;

import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.properties.ObservableProperty;
import kotlin.reflect.KProperty;

@SourceDebugExtension(
   "SMAP\nDelegates.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Delegates.kt\nkotlin/properties/Delegates$observable$1\n+ 2 PropertyDelegates.kt\norg/anti_ad/mc/common/extensions/PropertyDelegatesKt\n+ 3 SortingButtonCollectionWidget.kt\norg/anti_ad/mc/ipnext/gui/inject/SortingButtonCollectionWidget$InitWidgets\n*L\n1#1,34:1\n36#2,2:35\n38#2:39\n380#3,2:37\n*E\n"
)
public final class SortingButtonCollectionWidget$InitWidgets$special$$inlined$detectable$2 extends ObservableProperty {
   public SortingButtonCollectionWidget$InitWidgets$special$$inlined$detectable$2(Object var1, SortingButtonCollectionWidget$InitWidgets var2) {
      super(var1);
      this.this$0 = var2;
   }

   protected final void afterChange(KProperty var1, Object var2, Object var3) {
      Intrinsics.checkNotNullParameter(var1, "");
      Object var4 = var3;
      var2 = var2;
      if (!Intrinsics.areEqual(var2, var4)) {
         boolean var5 = (Boolean)var4;
         (Boolean)var2;
         SortingButtonCollectionWidget$InitWidgets.access$updateFastRenameConfigValue(this.this$0, var5);
      }
   }
}
