package org.anti_ad.mc.ipnext.config;

import java.net.URI;
import java.net.URL;
import kotlin.jvm.internal.Intrinsics;
import org.anti_ad.mc.common.gui.widgets.ConfigButtonInfo;
import org.anti_ad.mc.common.gui.widgets.CustomButtonWidget;
import org.anti_ad.mc.common.vanilla.VanillaUtil;
import org.anti_ad.mc.common.vanilla.alias.glue.I18n;
import org.jetbrains.annotations.NotNull;

public final class OpenProfilesHelpButtonInfo extends ConfigButtonInfo {
   @NotNull
   public static final OpenProfilesHelpButtonInfo INSTANCE = new OpenProfilesHelpButtonInfo();

   private OpenProfilesHelpButtonInfo() {
   }

   @NotNull
   public final String getButtonText() {
      return I18n.INSTANCE.translate("inventoryprofiles.gui.config.profiles_help_button", new Object[0]);
   }

   public final void onClick(@NotNull CustomButtonWidget var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      VanillaUtil var10000 = VanillaUtil.INSTANCE;
      URL var10001 = new URI("https://inventory-profiles-next.github.io/profiles/").toURL();
      Intrinsics.checkNotNullExpressionValue(var10001, "");
      var10000.open(var10001);
   }
}
