package org.anti_ad.mc.ipnext.config;

import kotlin.jvm.internal.Intrinsics;
import org.anti_ad.mc.common.TellPlayer;
import org.anti_ad.mc.common.gui.widgets.ConfigButtonInfo;
import org.anti_ad.mc.common.gui.widgets.CustomButtonWidget;
import org.anti_ad.mc.ipnext.integration.HintsManagerNG;
import org.anti_ad.mc.ipnext.integration.MergePriority;
import org.jetbrains.annotations.NotNull;

public final class ExportHints extends ConfigButtonInfo {
   private final boolean asInternal;

   public ExportHints(boolean var1) {
      this.asInternal = var1;
   }

   public final boolean getAsInternal() {
      return this.asInternal;
   }

   @NotNull
   public final String getButtonText() {
      return "Export Hints Data " + (this.asInternal ? "for integration" : "as separate files");
   }

   public final void onClick(@NotNull CustomButtonWidget var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      if (this.asInternal) {
         TellPlayer.INSTANCE.chat("Generating ModIntegrationExport.json");
         HintsManagerNG.INSTANCE.saveAllAsIntegrated((MergePriority)Modpacks.INSTANCE.getDIFF_CALCULATOR_PRIORITY().getValue());
      } else {
         HintsManagerNG.INSTANCE.saveAllAsSeparate((MergePriority)Modpacks.INSTANCE.getDIFF_CALCULATOR_PRIORITY().getValue());
      }
   }
}
