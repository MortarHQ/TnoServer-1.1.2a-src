package org.anti_ad.mc.ipnext.config;

import java.util.Timer;
import java.util.TimerTask;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import org.anti_ad.mc.common.gui.widgets.ConfigButtonClickHandler;
import org.anti_ad.mc.common.gui.widgets.ConfigButtonInfo;
import org.anti_ad.mc.common.gui.widgets.CustomButtonWidget;
import org.anti_ad.mc.common.vanilla.alias.glue.I18n;
import org.anti_ad.mc.ipnext.parser.ReloadRuleFileButtonInfoDelegate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ReloadRuleFileButtonInfo extends ConfigButtonInfo {
   @NotNull
   public static final ReloadRuleFileButtonInfo INSTANCE = new ReloadRuleFileButtonInfo();
   @Nullable
   private static ConfigButtonClickHandler delegate = ReloadRuleFileButtonInfoDelegate.INSTANCE;

   private ReloadRuleFileButtonInfo() {
   }

   @Nullable
   public final ConfigButtonClickHandler getDelegate() {
      return delegate;
   }

   public final void setDelegate(@Nullable ConfigButtonClickHandler var1) {
      delegate = var1;
   }

   @NotNull
   public final String getButtonText() {
      return I18n.INSTANCE.translate("inventoryprofiles.gui.config.button.reload_rule_files", new Object[0]);
   }

   public final void onClick(@NotNull CustomButtonWidget var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      if (delegate != null) {
         delegate.onClick(ReloadRuleFileButtonInfo::onClick$lambda$1);
      }
   }

   private static final Unit onClick$lambda$1(CustomButtonWidget var0) {
      var0.setActive(false);
      var0.setText(I18n.INSTANCE.translate("inventoryprofiles.gui.config.button.reload_rule_files.reloaded", new Object[0]));
      Timer var1 = new Timer();
      TimerTask var2 = new ReloadRuleFileButtonInfo$onClick$lambda$1$$inlined$schedule$1(var0);
      var1.schedule(var2, 5000L);
      return Unit.INSTANCE;
   }
}
