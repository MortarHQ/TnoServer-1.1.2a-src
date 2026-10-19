package org.anti_ad.mc.ipnext.config;

import java.nio.file.Path;
import org.anti_ad.mc.common.vanilla.VanillaUtil;
import org.anti_ad.mc.ipnext.parser.ProfilesLoader;
import org.jetbrains.annotations.NotNull;

public final class CustomConfigButtonKt {
   @NotNull
   private static final Path configFolder = VanillaUtil.INSTANCE.configDirectory("inventoryprofilesnext");

   @NotNull
   public static final Path getProfileFilePath() {
      return ProfilesLoader.INSTANCE.getFile();
   }
}
