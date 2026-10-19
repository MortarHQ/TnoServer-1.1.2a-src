package com.natamus.collective_common_neoforge.check;

import com.natamus.collective_common_neoforge.services.Services;

public class ShouldLoadCheck {
   public static boolean shouldLoad(String modId) {
      return Services.MODLOADER.isJarJard(modId) ? Services.MODLOADER.isBundleModEnabled(modId) : true;
   }
}
