package org.anti_ad.mc.ipnext.gui;

import kotlin.jvm.internal.Intrinsics;
import org.anti_ad.mc.common.gui.NativeContext;
import org.anti_ad.mc.common.gui.layout.WidgetExtensionsKt;
import org.anti_ad.mc.common.gui.widgets.Widget;
import org.anti_ad.mc.common.vanilla.render.ColorsKt;
import org.anti_ad.mc.common.vanilla.render.glue.RectKt;

public final class GUIDEEditorScreen$7 extends Widget {
   GUIDEEditorScreen$7() {
      this.setZIndex(0);
   }

   public final void render(NativeContext var1, int var2, int var3, float var4) {
      Intrinsics.checkNotNullParameter(var1, "");
      WidgetExtensionsKt.fillParent(this);
      int var5 = ColorsKt.getCOLOR_WHITE();
      RectKt.rDrawVerticalLine(var1, var2, 1, this.getHeight() - 2, var5);
      RectKt.rDrawHorizontalLine(var1, 1, this.getWidth() - 2, var3, var5);
   }
}
