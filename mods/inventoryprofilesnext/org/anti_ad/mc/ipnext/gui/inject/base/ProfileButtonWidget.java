package org.anti_ad.mc.ipnext.gui.inject.base;

import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

public class ProfileButtonWidget extends SortButtonWidget {
   public ProfileButtonWidget(@NotNull Function1 var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      super(var1);
   }

   public ProfileButtonWidget(@NotNull Function0 var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      super(var1);
   }

   public ProfileButtonWidget() {
   }

   @Override
   public boolean mouseClicked(int var1, int var2, int var3) {
      return super.mouseClicked(var1, var2, var3) && this.getVisible();
   }
}
