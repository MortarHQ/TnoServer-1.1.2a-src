package org.anti_ad.mc.ipnext.gui;

import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import org.anti_ad.mc.common.gui.widgets.Page;
import org.anti_ad.mc.common.gui.widgets.Widget;

public final class DebugScreen$addContent$1 extends Page {
   DebugScreen$addContent$1(Page var1, Function1 var2, String var3) {
      super(var3);
      this.$page = var1;
      this.$additionalContent = var2;
   }

   public final List getContent() {
      return CollectionsKt.plus(this.$page.getContent(), (Iterable)this.$additionalContent.invoke(this.$page));
   }

   public final void preRender(int var1, int var2, float var3) {
      this.$page.preRender(var1, var2, var3);
   }

   public final Widget getWidget() {
      return this.$page.getWidget();
   }
}
