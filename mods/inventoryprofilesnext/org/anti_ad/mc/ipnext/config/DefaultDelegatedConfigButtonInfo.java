package org.anti_ad.mc.ipnext.config;

import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import org.anti_ad.mc.common.gui.widgets.ConfigButtonClickHandler;
import org.anti_ad.mc.common.gui.widgets.ConfigButtonInfo;
import org.anti_ad.mc.common.gui.widgets.CustomButtonWidget;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DefaultDelegatedConfigButtonInfo extends ConfigButtonInfo {
   @Nullable
   private ConfigButtonClickHandler delegate;

   @Nullable
   public ConfigButtonClickHandler getDelegate() {
      return this.delegate;
   }

   public void setDelegate(@Nullable ConfigButtonClickHandler var1) {
      this.delegate = var1;
   }

   public void onClick(@NotNull CustomButtonWidget var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      ConfigButtonClickHandler var10000 = this.getDelegate();
      if (var10000 != null) {
         var10000.onClick(DefaultDelegatedConfigButtonInfo::onClick$lambda$0);
      }
   }

   private static final Unit onClick$lambda$0() {
      return Unit.INSTANCE;
   }
}
