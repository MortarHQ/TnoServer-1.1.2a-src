package org.anti_ad.mc.ipnext.config;

import java.util.List;
import kotlin.collections.CollectionsKt;
import org.anti_ad.mc.common.config.builder.ConfigDeclaration;
import org.jetbrains.annotations.NotNull;

public final class ConfigsKt {
   @NotNull
   private static final String category = "inventoryprofiles.config.category";
   @NotNull
   public static final String FILE_PATH = "inventoryprofiles.json";
   @NotNull
   private static final List Configs;

   @NotNull
   public static final List getConfigs() {
      return Configs;
   }

   static {
      ConfigDeclaration[] var0;
      (var0 = new ConfigDeclaration[10])[0] = ModSettings.INSTANCE;
      var0[1] = GuiSettings.INSTANCE;
      var0[2] = LockedSlotsSettings.INSTANCE;
      var0[3] = AutoRefillSettings.INSTANCE;
      var0[4] = EditProfiles.INSTANCE;
      var0[5] = ScrollSettings.INSTANCE;
      var0[6] = Hotkeys.INSTANCE;
      var0[7] = Tweaks.INSTANCE;
      var0[8] = Debugs.INSTANCE;
      var0[9] = Modpacks.INSTANCE;
      Configs = CollectionsKt.listOf(var0);
   }
}
