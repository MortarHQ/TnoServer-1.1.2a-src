package org.anti_ad.mc.ipnext;

import kotlin.jvm.internal.Intrinsics;
import org.anti_ad.mc.common.gui.widgets.ConfigButtonInfo;
import org.anti_ad.mc.common.gui.widgets.CustomButtonWidget;
import org.anti_ad.mc.common.vanilla.alias.glue.I18n;
import org.jetbrains.annotations.NotNull;

public final class IPNInfoManager$DoVersionCheckButtonInfo extends ConfigButtonInfo {
   @NotNull
   public static final IPNInfoManager$DoVersionCheckButtonInfo INSTANCE = new IPNInfoManager$DoVersionCheckButtonInfo();

   private IPNInfoManager$DoVersionCheckButtonInfo() {
   }

   @NotNull
   public final String getButtonText() {
      return I18n.INSTANCE.translate("inventoryprofiles.gui.config.button.do_version_check", new Object[0]);
   }

   public final void onClick(@NotNull CustomButtonWidget var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      IPNInfoManager.INSTANCE.doCheckVersion();
   }
}
