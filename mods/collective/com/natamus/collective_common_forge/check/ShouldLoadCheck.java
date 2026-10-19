package com.natamus.collective_common_forge.check;

import com.natamus.collective_common_forge.services.Services;

public class ShouldLoadCheck {
   public static boolean shouldLoad(String modId) {
      return Services.MODLOADER.isJarJard(modId) ? Services.MODLOADER.isBundleModEnabled(modId) : true;
   }
}
