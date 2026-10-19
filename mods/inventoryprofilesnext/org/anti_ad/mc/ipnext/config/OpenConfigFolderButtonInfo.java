package org.anti_ad.mc.ipnext.config;

import java.io.File;
import kotlin.jvm.internal.Intrinsics;
import org.anti_ad.mc.common.gui.widgets.ConfigButtonInfo;
import org.anti_ad.mc.common.gui.widgets.CustomButtonWidget;
import org.anti_ad.mc.common.vanilla.VanillaUtil;
import org.anti_ad.mc.common.vanilla.alias.glue.I18n;
import org.jetbrains.annotations.NotNull;

public final class OpenConfigFolderButtonInfo extends ConfigButtonInfo {
   @NotNull
   public static final OpenConfigFolderButtonInfo INSTANCE = new OpenConfigFolderButtonInfo();

   private OpenConfigFolderButtonInfo() {
   }

   @NotNull
   public final String getButtonText() {
      return I18n.INSTANCE.translate("inventoryprofiles.gui.config.button.open_config_folder", new Object[0]);
   }

   public final void onClick(@NotNull CustomButtonWidget var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      VanillaUtil var10000 = VanillaUtil.INSTANCE;
      File var10001 = CustomConfigButtonKt.access$getConfigFolder$p().toFile();
      Intrinsics.checkNotNullExpressionValue(var10001, "");
      var10000.open(var10001);
   }
}
