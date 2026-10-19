package org.anti_ad.mc.ipnext.config;

import org.anti_ad.mc.common.gui.widgets.ConfigButtonClickHandler;
import org.anti_ad.mc.ipnext.debug.GenerateTagVanillaTxtButtonInfoDelegate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class GenerateTagVanillaTxtButtonInfo extends DefaultDelegatedConfigButtonInfo {
   @NotNull
   public static final GenerateTagVanillaTxtButtonInfo INSTANCE = new GenerateTagVanillaTxtButtonInfo();
   @Nullable
   private static ConfigButtonClickHandler delegate = GenerateTagVanillaTxtButtonInfoDelegate.INSTANCE;

   private GenerateTagVanillaTxtButtonInfo() {
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
      return "generate tags.*.txt";
   }
}
