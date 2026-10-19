package org.anti_ad.mc.ipnext.config;

import org.anti_ad.mc.common.gui.widgets.ConfigButtonClickHandler;
import org.anti_ad.mc.ipnext.debug.GenerateRuleListButtonInfoDelegate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class GenerateRuleListButtonInfo extends DefaultDelegatedConfigButtonInfo {
   @NotNull
   public static final GenerateRuleListButtonInfo INSTANCE = new GenerateRuleListButtonInfo();
   @Nullable
   private static ConfigButtonClickHandler delegate = GenerateRuleListButtonInfoDelegate.INSTANCE;

   private GenerateRuleListButtonInfo() {
   }

   @Nullable
   @Override
   public final ConfigButtonClickHandler getDelegate() {
      return delegate;
   }

   @Override
   public final void setDelegate(@Nullable ConfigButtonClickHandler var1) {
      delegate = var1;
   }

   @NotNull
   public final String getButtonText() {
      return "generate native_rules.txt";
   }
}
